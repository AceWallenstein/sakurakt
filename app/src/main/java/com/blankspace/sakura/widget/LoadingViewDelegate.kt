package com.blankspace.sakura.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankspace.sakura.R
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType

class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {
  
  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
    inflater.inflate(R.layout.layout_loading, parent, false)
}