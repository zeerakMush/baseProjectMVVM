package com.zk.base_project.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver

object PermissionUtil {
    fun requestForPermissions(
            fragment: Fragment,
            permissions: List<String>,
            requestCode: Int
    ) {
        fragment.requestPermissions(permissions.toTypedArray(), requestCode)
    }

    fun requestForPermissions(
            activity: Activity,
            permissions: List<String>,
            requestCode: Int
    ) {
        ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), requestCode)
    }

    fun hasPermission(context: Context, permissions: List<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                            context,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) return false
        }
        return true
    }
}


class PermissionHelper(
    private val caller: ActivityResultCaller,
) : DefaultLifecycleObserver {
    private var permissionLauncher : ActivityResultLauncher<Array<String>>

    private var onGranted:(()->Unit)? = null

    init {
        permissionLauncher = caller.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("d", "PermissionHelperLogData->onCreate(): ${it.key} = ${it.value}")
            }
            if (permissions.all { it.value == true})
                onGranted?.invoke()
        }
    }

//    override fun onCreate(owner: LifecycleOwner) {
//    }

    fun check(list: List<String>,onGranted:()->Unit){
        this.onGranted = onGranted
        permissionLauncher.launch(list.toTypedArray())
    }
}