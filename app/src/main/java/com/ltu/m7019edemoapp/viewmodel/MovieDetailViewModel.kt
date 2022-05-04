package com.ltu.m7019e.m7019e_moviedbapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.model.MovieGenre
import com.ltu.m7019edemoapp.network.DataFetchStatus
import com.ltu.m7019edemoapp.network.MovieDetailResponse
import com.ltu.m7019edemoapp.network.TMDBApi
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailViewModel(application: Application, movieID: Long, movie: Movie) : AndroidViewModel(application) {

    var id = movieID

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean>
        get() {
            return _isFavourite
        }

    private val _movieGenres = MutableLiveData<List<MovieGenre>>()
    val movieGenres: LiveData<List<MovieGenre>>
        get() {
            return _movieGenres
        }

    private val _movieHomepage = MutableLiveData<String>()
    val movieHomepage: LiveData<String>
        get() {
            return _movieHomepage
        }

    private val _movieImdbId = MutableLiveData<String>()
    val movieImdbId: LiveData<String>
        get() {
            return _movieImdbId
        }

    init {
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            try {
                val movieDetailResponse: MovieDetailResponse = TMDBApi.movieListRetrofitService.getMovieDetails("$id")
                _movieGenres.value = movieDetailResponse.genres
                _movieHomepage.value = movieDetailResponse.homepage
                _movieImdbId.value = movieDetailResponse.imdb_id
                _dataFetchStatus.value = DataFetchStatus.DONE
            }
            catch (e: Exception) {
                _movieGenres.value = arrayListOf()
                _movieHomepage.value = ""
                _movieImdbId.value = ""
                _dataFetchStatus.value = DataFetchStatus.ERROR
            }
        }
    }
    }
