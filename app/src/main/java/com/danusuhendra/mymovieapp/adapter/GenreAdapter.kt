package com.danusuhendra.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danusuhendra.mymovieapp.databinding.ItemGenreBinding
import com.danusuhendra.mymovieapp.model.Genre

class GenreAdapter: RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private val list = ArrayList<Genre?>()
    private var itemCallback: OnItemClickCallback? = null

    fun setClickCallback(ItemClickCallback: OnItemClickCallback) {
        this.itemCallback = ItemClickCallback
    }

    fun setList(product: List<Genre?>) {
        list.clear()
        list.addAll(product)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.root.setOnClickListener {
                itemCallback?.onItemClicked(genre)
            }
            binding.tvTitleGenre.text = genre.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Genre)
    }
}