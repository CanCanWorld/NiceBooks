package com.zrq.nicebooks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zrq.nicebooks.databinding.ItemContentBinding

class ContentAdapter(
    private val context: Context,
    private val list: List<String> = ArrayList<String>(),
) : RecyclerView.Adapter<VH<ItemContentBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ItemContentBinding> {
        val mBinding = ItemContentBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(mBinding)
    }

    override fun onBindViewHolder(holder: VH<ItemContentBinding>, position: Int) {
        val data = list[position]
        holder.binding.apply {
            tvContent.text = data
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}