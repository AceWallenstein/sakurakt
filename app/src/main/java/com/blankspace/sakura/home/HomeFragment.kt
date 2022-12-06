package com.blankspace.sakura.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.blankspace.sakura.MainActivity
import com.blankspace.sakura.base.BaseFragment
import com.blankspace.sakura.databinding.FragmentHomeBinding
import com.blankspace.sakura.download.DownloadActivity
import com.blankspace.sakura.ext.onClick
import com.blankspace.sakura.net.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView(vb: FragmentHomeBinding) {
        with(vb) {
            onClick(bt1, bt2, bt3)
            {
                when (it) {
                    bt1 -> {
//                       getArticle(1)
                        startActivity(Intent(activity,DownloadActivity::class.java))
                    }
                    bt2 -> {
                        image.load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg3.yimasm.com%2Fpic%2F2021%2F04%2F17%2F30af71151cb680c9b9d05035be445922.jpg&refer=http%3A%2F%2Fimg3.yimasm.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653807025&t=2eded8b88d1de899563b1666c346bda1") {
                            transformations(CircleCropTransformation())
                            crossfade(true)

                        }
                    }
                    bt3 -> {
                        val url =
                            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2Ff3a9a5abd557d1349c6d98eaf2b1d34a50c6fcfa.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1653807336&t=25049fbbbd4ade99704d8124b4198807"
                        image.load(url) {
                            transformations(CircleCropTransformation())
                        }

                    }

                }
            }

        }
    }
    fun getArticle(page:Int){
        (activity as? MainActivity)?.showProgress()
        CoroutineScope(lifecycleScope.coroutineContext).launch {
            val result = RetrofitClient.apiService.getArticle(page)
            vb.tvContent.text = result
            (activity as? MainActivity)?.closeProgress()
        }
    }


}