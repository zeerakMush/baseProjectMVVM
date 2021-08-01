package com.zk.base_project.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zk.base_project.R


object DialogUtils {

    private fun createDialog(context: Context, layoutId: Int): Pair<AlertDialog, View> {

        var builder = AlertDialog.Builder(context)
        var view = LayoutInflater.from(context).inflate(layoutId, null)

        builder.setView(view)
        builder.setCancelable(false)
        var dialog = builder.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return Pair(dialog, view)
    }

    fun showLogoutConfirmationDialog(
        context: Context,
        onYesClick: ((AlertDialog, View) -> Unit)?,
        onNoClick: ((AlertDialog, View) -> Unit)?,
    ): AlertDialog {

        val (dialog, view) = createDialog(context, R.layout.dialog_logout_confirmation)

        val mYes = view.findViewById<TextView>(R.id.dialog_yes)
        val mNo = view.findViewById<TextView>(R.id.dialog_no)

        mYes.setOnClickListener() { view ->
            onYesClick?.invoke(dialog, view)
            dialog.dismiss()
        }

        mNo.setOnClickListener() { view ->
            onNoClick?.invoke(dialog, view)
            dialog.dismiss()
        }

        dialog.show()

        return dialog
    }


}