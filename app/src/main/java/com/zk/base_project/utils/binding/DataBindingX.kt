package com.zk.base_project.utils.binding

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.annotation.ColorInt
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.zk.base_project.data.enums.FormState
import com.zk.base_project.views.customviews.CustomSpinner
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


@BindingAdapter("app:isEnabled")
fun setIsEnabled(view: View, isEnabled: Boolean) {
    view.isEnabled = isEnabled
}

@BindingAdapter("app:isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter("app:isInVisible")
fun setIsInVisible(view: View, isInvisible: Boolean) {
    view.isInvisible = isInvisible
}

@BindingAdapter("app:state")
fun setState(view: View, state: FormState?) {
    view.isVisible = FormState.EDITING == state
}


@BindingAdapter("app:errorEnabled", "app:error", requireAll = true)
fun setError(editText: TextInputEditText, errorEnabled: Boolean, error: String?) {
    editText.error = if (errorEnabled && error != null) error else null
}

@BindingAdapter("app:icon")
fun MaterialButton.setMaterialIcon(res: Drawable) {
    this.icon = res
}

@BindingAdapter("app:progress")
fun ProgressBar.setProgress(progress: Double) {
    this.progress = progress.toInt()
}

@BindingAdapter("app:backgroundTint")
fun View.setBackgroundTint(@ColorInt color: Int) {
    backgroundTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("app:spinner_is_enabled")
fun CustomSpinner.setIsEnabled(isEnabled: Boolean){
    isSpinnerEnabled = isEnabled
}

@BindingAdapter(
    "app:imgUrl",
    "app:imgPlaceHolder",
    "app:imgError",
    "app:imgCorners",
    requireAll = false
)
fun ImageView.loadImage(
    url: String?,
    placeholder: Drawable? = null,
    error: Drawable?,
    corners: Float? = null
) {
    val base = Glide.with(this)
            .load(url)
            .placeholder(placeholder)
    if (corners != null)
        base.transform(CenterCrop(), RoundedCorners(corners.toInt())).into(this)
    else
        base.into(this)
}

@BindingAdapter("app:isSelected")
fun View.setIsViewSelected(isSelected: Boolean) {
    this.isSelected = isSelected
}


//EditText

@BindingAdapter("app:onKeyboardSendClicked")
fun EditText.onKeyboardSENDAction(editorAction: OnEditorAction?) {
    if (editorAction == null)
        setOnEditorActionListener(null)
    else setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_SEND)
            true.also { editorAction.action(v.editableText.toString()) }
        else false
    }
}

@BindingAdapter("onOkInSoftKeyboard") // I like it to match the listener method name
fun EditText.setOnOkInSoftKeyboardListener(
    listener: OnOkInSoftKeyboardListener?
) {
    if (listener == null) {
        setOnEditorActionListener(null)
    } else {
        setOnEditorActionListener(OnEditorActionListener { v, actionId, event -> // ... solution to receiving event
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                listener.onOkInSoftKeyboard()
                return@OnEditorActionListener  true
            }
            false
        })
    }
}

interface OnOkInSoftKeyboardListener {
    fun onOkInSoftKeyboard()
}

interface OnEditorAction {
    fun action(text: String)
}


@BindingAdapter("app:htmlText")
fun TextView.htmlText(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(text)
    }
}
//WebView

@BindingAdapter("app:loadHtml")
fun WebView.loadHtml(text: String?) {
    if (text != null) {
        loadDataWithBaseURL("", text, "text/html", "UTF-8", "")
    }
}

@BindingAdapter("app:loadArticleHtml")
fun WebView.loadArticleHtml(text: String?) {
    val textToRemove =
            "<p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>"

    loadHtml(htmlBody { text?.replace(textToRemove, "") ?: "" })
}

private fun htmlBody(block: () -> String): String {
    return """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        </head>
        <body style="padding:16px;">
        ${block.invoke()}
        </body>
        </html>
        """
}
