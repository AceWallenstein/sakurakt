package com.blankspace.sakura.scan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankspace.sakura.R
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.common.utils.permission
import com.blankspace.sakura.common.utils.toPath
import com.blankspace.sakura.common.utils.toast
import com.blankspace.sakura.data.BookRepository
import com.blankspace.sakura.data.room.AppDataBase
import com.blankspace.sakura.databinding.FragmentScannerBinding
import com.blankspace.sakura.ext.isBook
import com.blankspace.sakura.scan.viewmodel.ScannerViewModel
import com.blankspace.sakura.scan.viewmodel.ScannerViewModelFactory
import kotlinx.coroutines.launch
import java.io.File

/**
 * created  by will on 2020/11/22 16:59
 */
class ScannerFragment : BaseFragment<FragmentScannerBinding>() {
    private val viewModel: ScannerViewModel by viewModels {
        ScannerViewModelFactory(
            BookRepository.getInstance(
                AppDataBase.getInstance(requireContext()).getBookDao()
            )
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentScannerBinding =
        FragmentScannerBinding.inflate(layoutInflater)


    override fun initView(binding: FragmentScannerBinding) {
        super.initView(vb)
        //toolbar
        setHasOptionsMenu(true)
        val parent = activity as AppCompatActivity
        parent.setSupportActionBar(binding.fragmentScannerToolbar)
        parent.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.fragmentScannerToolbar.setNavigationOnClickListener { parent.onBackPressed() }
        //recycler
        val adapter = ScannerAdapter {
            viewModel.changeSelectState(it)
        }
        binding.fragmentScannerRecycler.adapter = adapter
        binding.fragmentScannerRecycler.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        //fab
        binding.fragmentScannerFab.setOnClickListener {
            viewModel.saveSelectedFile()
        }

        viewModel.getList().observe(viewLifecycleOwner) { list ->
            //在这版recyclerview依赖当中submitList()貌似有点问题，直接return了相同对象的提交，所以这里提交一个不同的
            adapter.submitList(list.toMutableList()) {
            }
        }
        viewModel.getCursor().observe(viewLifecycleOwner) {
            if (it == FileScanner.CURSOR_FINISHED) {
                binding.fragmentScannerCursor.visibility = View.GONE
            } else {
                binding.fragmentScannerCursor.text = ("搜索文件夹：$it")
            }
        }
        viewModel.hasPermission().observe(viewLifecycleOwner) {
            binding.fragmentScannerNoPermissionMsg.visibility = if (it) View.GONE else View.VISIBLE
        }

        lifecycleScope.launch {
            val result = permission(Manifest.permission.READ_EXTERNAL_STORAGE)
            if(result){
                viewModel.setHasPermission(true)
                viewModel.scan()
            }else{
                viewModel.setHasPermission(false)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.scanner, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private val pickFileCode = 32767
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_scanner_open) {
            lifecycleScope.launch {
                val result = permission(Manifest.permission.READ_EXTERNAL_STORAGE)
                if(result){
                    viewModel.setHasPermission(true)
                    val intent = Intent(Intent.ACTION_PICK).apply {
                        type = "text/plain"
                        action = Intent.ACTION_GET_CONTENT
                    }
                    startActivityForResult(intent, pickFileCode)
                }else{
                    viewModel.setHasPermission(false)
                    toast(requireContext(),"权限拒绝")
                }
            }


            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == pickFileCode) {
            if (resultCode == Activity.RESULT_OK) {
                val path = data?.data?.toPath(requireContext()) ?: ""
                if (path.isNotEmpty()) {
                    val file = File(path)
                    if (!file.exists()) {
                        toast(requireContext(), "添加失败，文件 ${file.path}不存在")
                        return
                    }
                    if (!file.isBook()) {
                        toast(requireContext(), "添加失败，文件 ${file.path}不是txt文件")
                        return
                    }
                    viewModel.save(file)
                } else {
                    toast(requireContext(), "添加失败，文件读取错误")
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}