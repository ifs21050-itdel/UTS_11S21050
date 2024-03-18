package com.ifs21050.dinopedia

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ifs21050.dinopedia.databinding.ItemRowFamilyBinding

class ListFamilyAdapter(private val listFamily: List<Family>) :
    RecyclerView.Adapter<ListFamilyAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowFamilyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val family = listFamily[position]
        holder.binding.dinoImg.setImageResource(family.icon)
        holder.binding.dinogroup.text = family.name
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(family)
        }
    }

    override fun getItemCount(): Int = listFamily.size

    inner class ListViewHolder(val binding: ItemRowFamilyBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Family)
    }
}
