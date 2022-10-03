package com.danusuhendra.mymovieapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.danusuhendra.mymovieapp.databinding.ActivityDetailBinding
import com.danusuhendra.mymovieapp.model.DetailResponse
import com.danusuhendra.mymovieapp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    private var movieID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("movie_id", 0)
        movieID = id

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        viewModel.getDetail(movieID)

        viewModel.dataSate.observe(this) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    displayLoading(false)
                    appendDetail(dataState.data)
                }
                is DataState.Error -> {
                    displayLoading(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
            }
        }
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayLoading(loading: Boolean) {
        if (loading) {
            binding.loadingDetail.visibility = View.VISIBLE
        } else {
            binding.loadingDetail.visibility = View.GONE
        }
    }

    private fun appendDetail(data: DetailResponse) {
        binding.apply {
            Glide.with(baseContext)
                .load("https://image.tmdb.org/t/p/w500" + data.posterPath)
                .into(ivDetailPoster)
            tvTitleDetail.text = data.title
            tvDescDetail.text = data.overview
            tvRating.text = data.voteAverage.toString() + " (" + data.voteCount.toString() + ")"
        }
    }
}