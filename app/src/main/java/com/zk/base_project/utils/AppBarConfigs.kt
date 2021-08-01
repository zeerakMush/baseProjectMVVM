package com.zk.base_project.utils

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.zk.base_project.R
import com.zk.base_project.utils.Extensions.visible
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar


class AppBarConfigs(
        private var appBarLayout: AppBarLayout,
        private var materialToolbar: MaterialToolbar,
) {
    private var mTitleTv: TextView = materialToolbar.findViewById(R.id.title)
    private var mAddBtn: TextView = materialToolbar.findViewById(R.id.main_add)
    private var mProgressBar: ProgressBar = materialToolbar.findViewById(R.id.pb_wait)

    fun resetAppBar() {
        setAppBarVisibility(View.VISIBLE)
        mTitleTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
        mTitleTv.setOnClickListener(null)
        showAddButton(false)
    }

    fun setAppBarVisibility(visibility: Int) {
        appBarLayout.visibility = visibility
        materialToolbar.visibility = visibility
    }

    fun setTitle(title: String) {
        mTitleTv.text = title
    }

    fun setTitleAsSpinner(title: String, clickListener: (View) -> Unit) {
        mTitleTv.text = title
        val drawable = ContextCompat.getDrawable(mTitleTv.context, R.drawable.ic_down)
        drawable?.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(mTitleTv.context, android.R.color.white), PorterDuff.Mode.SRC_IN)
        mTitleTv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        mTitleTv.setOnClickListener(clickListener)
    }

    fun showProgressBar(isVisible: Boolean) {
        mProgressBar.isVisible = isVisible
    }

    fun showAddButton(isShow: Boolean = true) {
        mAddBtn.visible = isShow
    }
}