package com.danusuhendra.mymovieapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danusuhendra.mymovieapp.model.DetailResponse
import com.danusuhendra.mymovieapp.repository.DetailRepository
import com.danusuhendra.mymovieapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: DetailRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<DetailResponse>> = MutableLiveData()

    val dataSate: LiveData<DataState<DetailResponse>>
        get() = _dataState

    fun getDetail(movieId: Int) {
        viewModelScope.launch {
            repository.getDetail(movieId)
                .onEach {
                    _dataState.value = it
                }
                .launchIn(viewModelScope)
        }
    }
}