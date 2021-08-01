package com.zk.base_project.views.adapters

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.ListPopupWindow
import com.zk.base_project.data.SpinnerItem
import com.zk.base_project.databinding.ItemSpinnerPopupBinding
import com.zk.base_project.views.customviews.CustomSpinner


class SpinnerAdapter(private var dataList: List<SpinnerItem>,
                     private var dropDownType: CustomSpinner.Type = CustomSpinner.Type.TEXT,
                     private var popupWindow: ListPopupWindow) : ListAdapter {

    var listener: ((Int, SpinnerItem) -> Unit)? = null

    override fun registerDataSetObserver(observer: DataSetObserver?) {
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var binding = ItemSpinnerPopupBinding.inflate(
                LayoutInflater.from(parent?.context), parent, false)

        binding.root.setOnClickListener() {

            listener?.invoke(position, dataList[position])
            popupWindow.dismiss()
        }

        when (dropDownType) {

            CustomSpinner.Type.IMAGE -> {

                if (dataList[position].iconId > 0) {
                    binding.itemPopupImage.visibility = View.VISIBLE
                    binding.itemPopupImage.setImageResource(dataList[position].iconId)
                }
            }

            CustomSpinner.Type.TEXT -> {
                binding.itemPopupText.visibility = View.VISIBLE
                binding.itemPopupText.text = dataList[position].textEng
            }

            CustomSpinner.Type.IMAGE_TEXT -> {
                binding.itemPopupText.visibility = View.VISIBLE
                binding.itemPopupText.text = dataList[position].textEng

                if (dataList[position].iconId > 0) {
                    binding.itemPopupImage.visibility = View.VISIBLE
                    binding.itemPopupImage.setImageResource(dataList[position].iconId)
                }
            }
        }


        return binding.root
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun isEmpty(): Boolean {
        return false
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }

    override fun isEnabled(position: Int): Boolean {
        return true
    }

    fun setOnItemSelectListener(listener: ((Int, SpinnerItem) -> Unit)?) {

        this.listener = listener
    }


}