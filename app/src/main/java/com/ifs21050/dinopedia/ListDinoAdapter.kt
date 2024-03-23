package com.ifs21050.dinopedia

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ifs21050.dinopedia.databinding.ItemRowDinoBinding

class ListDinoAdapter(private val originalDinoList: ArrayList<Dinosaurus>) :
    RecyclerView.Adapter<ListDinoAdapter.ListViewHolder>() {

    private var filteredDinoList = ArrayList<Dinosaurus>(originalDinoList)
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun submitList(newList: List<Dinosaurus>) {
        originalDinoList.clear()
        originalDinoList.addAll(newList)
        filter("") // Menjalankan filter dengan string kosong untuk mengembalikan daftar asli
    }

    fun searchByGroup(group: String) {
        filter(group)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowDinoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dinosaurus = filteredDinoList[position]
        holder.binding.dinoGambar.setImageResource(dinosaurus.gambar)
        holder.binding.dinoNama.text = dinosaurus.name
        holder.binding.dinoGroup.text = dinosaurus.group // Menampilkan group di TextView
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(filteredDinoList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = filteredDinoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredDinoList.clear()
        if (query.isEmpty()) {
            filteredDinoList.addAll(originalDinoList)
        } else {
            val filteredList = originalDinoList.filter {
                it.group.contains(query, ignoreCase = true) // Memeriksa pencarian berdasarkan group
            }
            filteredDinoList.addAll(filteredList)
        }
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: ItemRowDinoBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: Dinosaurus)
    }
}
