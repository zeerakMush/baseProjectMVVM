package com.zk.base_project.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt


object Extensions {

    var View.visible:Boolean
        get() = visible
        set(value){
            visibility = if (value)
                View.VISIBLE
            else
                View.GONE
        }

    fun Int.toDp() :Int {
        return (this / Resources.getSystem().displayMetrics.density).roundToInt()
    }

    fun Int.toPx() :Int {
        return (this * Resources.getSystem().displayMetrics.density).roundToInt()
    }

    fun Fragment.hideKeyboard() {
        requireActivity().currentFocus?.hideKeyboard()
    }

    fun View.hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    fun View.showSnackBar(
            message: String,
            length: Int = Snackbar.LENGTH_LONG
    ) {
        Snackbar.make(this, message, length).show()
    }

    /**
     * Send email.
     *
     * @param context the context
     * @param email the email
     * @param subject the subject
     * @param text the text
     */
    fun String.sendEmail(context: Context, text: String="", subject: String?="") {
        val emailIntent = Intent(
                Intent.ACTION_SEND)
        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(this))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
    }

    /**
     * Dial.
     *
     * @param context the context
     * @param number the number
     */
    fun String.dial(context: Context) {
        val dialUri: Uri = Uri.parse("tel:$this")
        val dialIntent = Intent(Intent.ACTION_DIAL, dialUri)
        context.startActivity(dialIntent)
    }

    fun String.openBrowser(context: Context){
        val i = Intent(Intent.ACTION_VIEW)
        var url = this
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        i.data = Uri.parse(url)
        try {
            context.startActivity(i)
        } catch (e: Exception) {
        }
    }


}