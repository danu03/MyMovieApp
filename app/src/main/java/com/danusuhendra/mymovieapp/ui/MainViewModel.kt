package com.danusuhendra.mymovieapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danusuhendra.mymovieapp.model.Genre
import com.danusuhendra.mymovieapp.model.GenreResponse
import com.danusuhendra.mymovieapp.model.ListMovieByGenreResponse
import com.danusuhendra.mymovieapp.repository.MainRepository
import com.danusuhendra.mymovieapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _dataStateGenre: MutableLiveData<DataState<GenreResponse>> = MutableLiveData()
    private val _dataStateListMovie: MutableLiveData<DataState<ListMovieByGenreResponse>> = MutableLiveData()

    val dataStateGenre: LiveData<DataState<GenreResponse>>
        get() = _dataStateGenre
    val dataStateListMovie: LiveData<DataState<ListMovieByGenreResponse>>
        get() = _dataStateListMovie

    fun getGenre() {
        viewModelScope.launch {
            repository.getGenre()
                .onEach {
                    _dataStateGenre.value = it
                }
                .launchIn(viewModelScope)
        }
    }

    fun getListMovieByGenre(genreId: Int) {
        viewModelScope.launch {
            repository.getListMovieByGenre(genreId)
                .onEach {
                    _dataStateListMovie.value = it
                }
                .launchIn(viewModelScope)
        }
    }

}