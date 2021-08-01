package com.zk.base_project.views

import androidx.databinding.ViewDataBinding

abstract class BaseFragmentWithEvent<T : ViewDataBinding, E> : BaseFragment<T>() {

    fun setBaseViewModelEvent(viewModel:BaseViewModel<E>) {
        super.setBaseViewModel(viewModel)
        viewModel.events.observe(this,::onViewModelEvent)
    }

    protected open fun onViewModelEvent(event: E) {

    }
}