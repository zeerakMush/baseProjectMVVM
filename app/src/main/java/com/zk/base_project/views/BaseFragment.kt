package com.zk.base_project.views

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zk.base_project.data.enums.ColorTheme
import com.zk.base_project.utils.Extensions.hideKeyboard

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    abstract fun inflateBinding(): T
    protected lateinit var binding: T

    lateinit var mainActivity: MainActivity
    var colorTheme: ColorTheme = ColorTheme.BLUE
    protected val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = inflateBinding()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    protected fun<E> setBaseViewModel(viewModel: BaseViewModel<E>) {
        viewModel.commonEvents.observe(viewLifecycleOwner, ::handleCommonEvents)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity = requireActivity() as MainActivity
    }

    private fun handleCommonEvents(event: BaseViewModel.CommonEvents) {
        when (event) {
            is BaseViewModel.CommonEvents.Message -> showMessage(event.message)
            is BaseViewModel.CommonEvents.HideKeyboard -> hideKeyboard()
        }
    }

    protected fun showMessage(message: String) {
        mainActivity.showSnackBar(message)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        setAppbarColorTheme()
    }

    private fun setAppbarColorTheme() {
        if (colorTheme != ColorTheme.NONE) {
            mainActivity.supportActionBar?.setBackgroundDrawable(
                    ColorDrawable(resources.getColor(colorTheme.colorId, null)))
            mainActivity.window.statusBarColor = resources.getColor(colorTheme.colorId, null)
        }
    }
}