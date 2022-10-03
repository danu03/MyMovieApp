package com.danusuhendra.mymovieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danusuhendra.mymovieapp.adapter.GenreAdapter
import com.danusuhendra.mymovieapp.adapter.ListMovieByGenreAdapter
import com.danusuhendra.mymovieapp.databinding.ActivityMainBinding
import com.danusuhendra.mymovieapp.model.Genre
import com.danusuhendra.mymovieapp.model.Result
import com.danusuhendra.mymovieapp.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GenreAdapter
    private lateinit var adapterListMovie: ListMovieByGenreAdapter

    private val viewModel: MainViewModel by viewModels()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var layoutManagerListMovie: LinearLayoutManager

    private var genreId: Int = 28

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        layoutManagerListMovie = LinearLayoutManager(this)
        setUpRecyclerView()
        adapter.setClickCallback(object : GenreAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Genre) {
                data.id?.let { viewModel.getListMovieByGenre(it) }
            }

        })

        adapterListMovie.setClickCallback(object : ListMovieByGenreAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Result) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("movie_id", data.id)
                startActivity(intent)
            }

        })

        viewModel.getGenre()
        viewModel.getListMovieByGenre(genreId)

        viewModel.dataStateGenre.observe(this) { genre ->
            when (genre) {
                is DataState.Success -> {
                    displayLoadingGenre(false)
                    genre.data.genres?.let { appendGenre(it) }
                }
                is DataState.Error -> {
                    displayLoadingGenre(false)
                    displayError(genre.exception.message)
                }
                is DataState.Loading -> {
                    displayLoadingGenre(true)
                }
            }
        }

        viewModel.dataStateListMovie.observe(this) { listMovie ->
            when(listMovie) {
                is DataState.Success -> {
                    displayLoading(false)
                    listMovie.data.results?.let { appendListMovie(it) }
                }
                is DataState.Error -> {
                    displayLoading(false)
                    displayError(listMovie.exception.message)
                    Log.d("TAG", listMovie.exception.message.toString())
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
            }
        }

    }

    private fun setUpRecyclerView() {
        adapter = GenreAdapter()
        adapterListMovie = ListMovieByGenreAdapter()
        binding.apply {
            rvGenre.layoutManager = layoutManager
            rvGenre.setHasFixedSize(true)
            rvGenre.adapter = adapter

            rvListGenreSelected.layoutManager = layoutManagerListMovie
            rvListGenreSelected.setHasFixedSize(true)
            rvListGenreSelected.adapter = adapterListMovie
        }
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayLoading(loading: Boolean) {
        if (loading) {
            binding.pbListMovie.visibility = View.VISIBLE
        } else {
            binding.pbListMovie.visibility = View.GONE
        }
    }

    private fun displayLoadingGenre(loading: Boolean) {
        if (loading) {
            binding.pbGenre.visibility = View.VISIBLE
        } else {
            binding.pbGenre.visibility = View.GONE
        }
    }

    private fun appendGenre(genre: List<Genre?>) {
        adapter.setList(genre)
    }

    private fun appendListMovie(listMovie: List<Result?>) {
        adapterListMovie.setList(listMovie)
    }
}