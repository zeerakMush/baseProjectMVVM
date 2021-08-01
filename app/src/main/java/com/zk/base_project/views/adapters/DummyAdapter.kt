package com.zk.base_project.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zk.base_project.databinding.ItemDummyBinding

class DummyAdapter(
    private var dataList: MutableList<String>
) : RecyclerView.Adapter<DummyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyAdapter.ViewHolder {
        return ViewHolder(
            ItemDummyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DummyAdapter.ViewHolder, position: Int) {
        holder.bindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    inner class ViewHolder(private var binding: ItemDummyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: String) {
        }
    }
}