package com.blankspace.sakura.calendar

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.blankspace.sakura.R
import com.blankspace.sakura.ext.onClick
import kotlinx.coroutines.launch

class StateFragment : Fragment(R.layout.fragment_state) {
    lateinit var textView: TextView
    lateinit var add: Button
    lateinit var reduce: Button
    private val viewModel: StateFlowViewModel by lazy(LazyThreadSafetyMode.NONE) { StateFlowViewModel() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById<TextView>(R.id.textView)
        add = view.findViewById<Button>(R.id.add)
        reduce = view.findViewById<Button>(R.id.reduce)
        lifecycleScope.launch{
            viewModel.stateFlow.observe(viewLifecycleOwner){
                textView.text = "$it"
            }
        }

        onClick(add,reduce){
            when(it){
                add->{
                    viewModel.add(add)
                }
                reduce->{
                    viewModel.reduce(reduce)

                }
            }
        }

    }

}