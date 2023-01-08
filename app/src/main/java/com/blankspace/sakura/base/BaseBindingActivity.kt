package com.blankspace.sakura.base

import androidx.viewbinding.ViewBinding
import com.dylanc.loadingstateview.Decorative
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateDelegate
import com.dylanc.loadingstateview.OnReloadListener

abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity<VB>(),
  LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative
   {

       override fun setContentViewWithBinding() {
           super.setContentViewWithBinding()
           vb.root.decorate(this,this)
       }
}
