package com.zk.base_project.views.ui.registration.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.zk.base_project.databinding.FragmentLoginBinding
import com.zk.base_project.views.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override fun inflateBinding() = FragmentLoginBinding.inflate(layoutInflater)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setBaseViewModel(viewModel)
        viewModel.events.observe(viewLifecycleOwner, ::onViewModelEvent)
    }

    private fun onViewModelEvent(event: LoginViewModel.Event) {
        when (event) {

        }
    }

}