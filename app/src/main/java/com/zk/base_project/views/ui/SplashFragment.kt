package com.zk.base_project.views.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.zk.base_project.R
import com.zk.base_project.databinding.FragmentSplashBinding
import com.zk.base_project.views.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun inflateBinding() = FragmentSplashBinding.inflate(layoutInflater)
    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            navController.navigate(
                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
    }
}
