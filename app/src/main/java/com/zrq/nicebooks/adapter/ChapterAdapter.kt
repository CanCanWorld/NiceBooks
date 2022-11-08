package com.zrq.nicebooks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zrq.nicebooks.bean.ChapterListItem
import com.zrq.nicebooks.databinding.ItemChapterBinding
import com.zrq.nicebooks.interfaces.OnItemClickListener

class ChapterAdapter(
    private val context: Context,
    private val list: List<ChapterListItem> = ArrayList<ChapterListItem>(),
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<VH<ItemChapterBinding>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH<ItemChapterBinding> {
        val mBinding = ItemChapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(mBinding)
    }

    override fun onBindViewHolder(holder: VH<ItemChapterBinding>, position: Int) {
        val data = list[position]
        holder.binding.apply {
            tvNumber.text = position.toString()
            tvTitle.text = data.title
            root.setOnClickListener {
                onItemClickListener.onItemClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}