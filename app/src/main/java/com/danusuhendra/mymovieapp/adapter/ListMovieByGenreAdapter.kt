package com.danusuhendra.mymovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danusuhendra.mymovieapp.databinding.ItemMovieByGenreBinding
import com.danusuhendra.mymovieapp.model.Result

class ListMovieByGenreAdapter: RecyclerView.Adapter<ListMovieByGenreAdapter.ViewHolder>() {

    private val list = ArrayList<Result?>()
    private var itemCallback: OnItemClickCallback? = null

    fun setClickCallback(ItemClickCallback: OnItemClickCallback) {
        this.itemCallback = ItemClickCallback
    }

    fun setList(result: List<Result?>) {
        list.clear()
        list.addAll(result)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemMovieByGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            binding.root.setOnClickListener {
                itemCallback?.onItemClicked(result)
            }
            binding.apply {
                tvTitle.text = result.title
                tvDesc.text = result.overview
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500" + result.posterPath)
                    .into(ivImageList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieByGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position]?.let { holder.bind(it) }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Result)
    }
}