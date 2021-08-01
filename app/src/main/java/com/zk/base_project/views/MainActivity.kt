package com.zk.base_project.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.View.*
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.zk.base_project.R
import com.zk.base_project.databinding.ActivityMainBinding
import com.zk.base_project.utils.AppBarConfigs
import com.zk.base_project.utils.Extensions.showSnackBar
import com.zk.base_project.utils.PermissionHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    companion object {
        private const val REQUEST_READ_CONTACTS_PERMISSION = 0x01
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    var permissionHelper:PermissionHelper = PermissionHelper(this)

    lateinit var appBarConfigs: AppBarConfigs
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        appBarConfigs = AppBarConfigs(binding.mainAppBar, binding.mainToolBar)
        viewModel.events.observe(this, ::handleEvent)

        setNavigationUi()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_CONTACTS_PERMISSION) {
            if (permissions[0] == Manifest.permission.READ_CONTACTS
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    fun showSnackBar(message: String) {
        binding.clRootView.showSnackBar(message = message)
    }

    private fun handleEvent(event: MainViewModel.Event) {
        when (event) {
            is MainViewModel.Event.NavigateTo -> navController.navigate(event.destinationId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_appbar, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        if (navController.currentDestination?.label == getString(R.string.verify_user_fragment_title)) {
            viewModel.logOutUser()
        }
        if (!NavigationUI.navigateUp(navController, appBarConfiguration)) finish()
        return true
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.label == getString(R.string.verify_user_fragment_title)) {
            viewModel.logOutUser()
        }
        super.onBackPressed()
    }

    private fun setNavigationUi() {
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        NavigationUI.setupActionBarWithNavController(
                this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(
                binding.bottomViewMain, navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            appBarConfigs.setTitle(destination.label?.toString() ?: "")
            when (destination.id) {
                R.id.loginFragment -> appBarConfigs.setAppBarVisibility(GONE)
                else -> appBarConfigs.resetAppBar()
            }
            when (destination.id) {
                else -> binding.bottomViewMain.visibility = GONE
            }
        }
    }
}