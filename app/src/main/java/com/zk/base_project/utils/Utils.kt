package com.zk.base_project.utils

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.zk.base_project.R
import com.zk.base_project.App
import com.zk.base_project.network.URL
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


object Utils {

  /*  inline fun <reified T> getMoshiAdapterForList(): JsonAdapter<List<T>> {
       *//* val listMyData = Types.newParameterizedType(MutableList::class.java, T::class.java)
        return DataTypeConverter.moshi.adapter(listMyData)*//*
    }*/

    fun getItemDivider(context: Context, orientation: Int = DividerItemDecoration.VERTICAL): DividerItemDecoration {
        return object :DividerItemDecoration(context, orientation) {
            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                val drawable: Drawable =
                    ContextCompat.getDrawable(context!!, R.drawable.shape_divider)!!
                val left = parent.paddingLeft
                val right = parent.width - parent.paddingRight

                val childCount = parent.adapter!!.itemCount
                for (i in 0 until childCount - 1) {
                    val child = parent.getChildAt(i)
                    if (child != null) {
                        val params = child.layoutParams as RecyclerView.LayoutParams
                        val top = child.bottom + params.bottomMargin
                        val bottom = top + drawable.intrinsicHeight
                        drawable.setBounds(left, top, right, bottom)
                        drawable.draw(c)
                    }
                }
            }
        }
    }

    fun visibility(visible: Boolean):Int{

        return if (visible)
            View.VISIBLE
        else
            View.GONE
    }

    fun getParentNavController(fragment: Fragment):NavController{
        return fragment.requireParentFragment().requireParentFragment().findNavController()
    }

    fun scaleDrawable(drawable: Drawable, avgSize: Int): Drawable {

        var bitmap = (drawable as (BitmapDrawable)).bitmap
        var widthFactor: Float

        widthFactor = if (bitmap.width > bitmap.height) {
            avgSize.toFloat() / bitmap.width.toFloat()
        }
        else {
            avgSize.toFloat() / bitmap.height.toFloat()
        }

        var width = bitmap.width * widthFactor
        var height = bitmap.height * widthFactor

        return BitmapDrawable(App.context.resources, Bitmap.createScaledBitmap(
                bitmap, width.roundToInt(), height.roundToInt(), true))
    }

    fun scaleDrawable(drawableId: Int, avgSize: Int): Drawable {

        val bitmap = getBitmap(drawableId)

//        val bitmap = BitmapFactory.decodeResource(SmartConnectApp.context.resources, drawableId)
//        var bitmap = (drawable as (BitmapDrawable)).bitmap
        var widthFactor: Float

        widthFactor = if (bitmap.width > bitmap.height) {
            avgSize.toFloat() / bitmap.width.toFloat()
        }
        else {
            avgSize.toFloat() / bitmap.height.toFloat()
        }

        var width = bitmap.width * widthFactor
        var height = bitmap.height * widthFactor

        return BitmapDrawable(App.context.resources, Bitmap.createScaledBitmap(
                bitmap, width.roundToInt(), height.roundToInt(), true))
    }

    fun getBitmap(drawableId: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(App.context, drawableId)
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    fun showDatePicker(context: Context, maxDateMillis: Long? = Calendar.getInstance().timeInMillis, onDateSelection: (String) -> Unit) {
        val myCalendar = Calendar.getInstance()
        DatePickerDialog(
            context,
                { _, year, monthOfYear, dayOfMonth ->
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    onDateSelection.invoke(
                        SimpleDateFormat(
                        "MM/dd/YYYY",
                        Locale.getDefault()
                    ).format(myCalendar.time))
                }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            if (maxDateMillis != null) {
                datePicker.maxDate = maxDateMillis
            }
            show()
        }
    }

    fun getImageCaptureFile(): File {
        val file = File(App.context.filesDir,"img_${Date().time}.png")
        if (!file.exists())
            file.createNewFile()
        return file
    }

    fun getFileUri(file: File): Uri {
        return FileProvider.getUriForFile(App.context,
            App.context.packageName.toString() + ".provider", file)
    }

    fun createDownloadableUrl(fileName:String,accessToken:String):String{
        return "${URL.URL_DEVELOPMENT}downloadAttachment/${fileName}?access_token=${accessToken}"
    }

}