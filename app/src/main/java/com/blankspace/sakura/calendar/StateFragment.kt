package com.blankspace.sakura.calendar

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.sakura.R
import com.blankspace.sakura.adapter.TextAdapter
import com.blankspace.sakura.ext.onClick
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StateFragment : Fragment(R.layout.fragment_state) {
    lateinit var textView: TextView
    lateinit var add: Button
    lateinit var reduce: Button
    lateinit var recyclerView: RecyclerView
    private val textAdapter by lazy(LazyThreadSafetyMode.NONE) {
        TextAdapter()
    }


    private val viewModel: StateFlowViewModel by lazy(LazyThreadSafetyMode.NONE) { StateFlowViewModel() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById<TextView>(R.id.textView)
        add = view.findViewById<Button>(R.id.add)
        reduce = view.findViewById<Button>(R.id.reduce)
        recyclerView = view.findViewById(R.id.recycler_view)
        lifecycleScope.launch {
            viewModel.stateFlow.observe(viewLifecycleOwner) {
                textView.text = "$it"
            }
        }
        recyclerView.run {
            adapter = textAdapter
            layoutManager = LinearLayoutManager(context)
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                (1..5).asFlow().onEach {
                    delay(1000)
                    if (it == 3) {
                        throw Exception("num!!")
                    }
                }.catch { Log.d("flow", "error: $it") }
                    .retry (2){
                        if (it is RuntimeException) {
                            return@retry true
                        }
                        false
                    }
                    .flowOn(Dispatchers.Main)
                    .transform<Int, Int> { emit(it * it) }
                    .collect {
                        Log.d("flow", "onViewCreated: $it")
                        textAdapter.addData(it)
                    }
            }

        }

        onClick(add, reduce) {
            when (it) {
                add -> {
                    viewModel.add(add)
                    textAdapter.addData(1)
                }
                reduce -> {
                    viewModel.reduce(reduce)

                }
            }
        }

    }

}