package com.zrq.nicebooks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zrq.nicebooks.bean.DataItem
import com.zrq.nicebooks.databinding.ItemSearchBooksBinding
import com.zrq.nicebooks.interfaces.OnItemClickListener

class SearchBooksAdapter(
    private val context: Context,
    private var list: MutableList<DataItem> = mutableListOf<DataItem>(),
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VH<ItemSearchBooksBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ItemSearchBooksBinding> {
        val mBinding = ItemSearchBooksBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(mBinding)
    }

    override fun onBindViewHolder(holder: VH<ItemSearchBooksBinding>, position: Int) {
        val data = list[position]
        holder.binding.apply {
            tvTitle.text = data.title
            tvAuthor.text = data.author
            tvDesc.text = data.descs
            tvTag.text = data.fictionType
            Glide.with(context).load(data.cover).into(ivAlbum)
            root.setOnClickListener {
                onItemClickListener.onItemClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}