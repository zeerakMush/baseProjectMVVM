package com.zk.base_project.views.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.zk.base_project.R
import com.zk.base_project.data.SpinnerItem
import com.zk.base_project.views.customviews.CustomSpinner
import com.google.android.material.imageview.ShapeableImageView
import java.util.*


object DataBindingAdapter {

    @BindingAdapter(value = ["selectedItemAttrChanged"])
    @JvmStatic
    fun setSelecteditemListener(customSpinner: CustomSpinner, listener: InverseBindingListener) {

    }

    @BindingAdapter(value = ["selectedItem"])
    @JvmStatic
    fun setSelectedItem(customSpinner: CustomSpinner, selectedItem: SpinnerItem?) {
        customSpinner.setSelectedItem(selectedItem)
    }


    @BindingAdapter(value = ["spinnerItemsArraylist"])
    @JvmStatic
    fun setSpinnerItemsArraylist(customSpinner: CustomSpinner, items: List<SpinnerItem>?){
        customSpinner.setItems(items?.toCollection(ArrayList()))
    }

    @InverseBindingAdapter(attribute = "selectedItem")
    @JvmStatic
    fun getSelectedItem(customSpinner: CustomSpinner): SpinnerItem {
        return customSpinner.getSelectedItem()
    }

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun setImageUrl(imageView: ShapeableImageView, imageUrl: String?) {
        Glide.with(imageView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_profile_placeholder)
                .dontAnimate()
                .into(imageView)

    }

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun setImageUrl(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_profile_placeholder)
                .dontAnimate()
                .into(imageView)
    }

}