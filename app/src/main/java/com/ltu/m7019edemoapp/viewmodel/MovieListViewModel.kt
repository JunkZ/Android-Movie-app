package com.ltu.m7019edemoapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019edemoapp.MovieResponseRepository
import com.ltu.m7019edemoapp.database.Movies
import com.ltu.m7019edemoapp.database.getDatabase
import com.ltu.m7019edemoapp.domain.MovieModel
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.network.DataFetchStatus
import com.ltu.m7019edemoapp.network.MovieResponse
import com.ltu.m7019edemoapp.network.TMDBApi
import kotlinx.coroutines.launch
import java.io.IOException


class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    private val _movieList = MutableLiveData<List<Movie>>()
    var _dataFetchStatus = MutableLiveData<DataFetchStatus>() //ye    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val moviesRepository = MovieResponseRepository(getDatabase(application))


    /**
     * A playlist of videos displayed on the screen.
     */
    val playlist = moviesRepository.videos

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown



    val movieList: LiveData<List<Movie>>
        get() {
            return _movieList
        }

    init {
        //_movieList.postValue(Movies().list)
        //refreshDataFromRepository()
        getPopularMovies()
    }


    fun refreshDataFromRepository(Popular:Boolean) {
        viewModelScope.launch {
            try {
                moviesRepository.refreshVideos(Popular)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }



    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                _movieList.value = arrayListOf()

                //val movieResponse: MovieResponse =
                //    TMDBApi.movieListRetrofitService.getPopularMovies()
                //_movieList.value = movieResponse.results
                moviesRepository.refreshVideos(true)
                playlist.observeForever {response ->
                    _movieList.value=response
                }

                _dataFetchStatus.value = DataFetchStatus.DONE

            } catch (e: Exception) {
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieList.value = arrayListOf()
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                Log.i("ViewModel","Getting top rated")
                _movieList.value = arrayListOf()
//                val movieResponse: MovieResponse =
//                    TMDBApi.movieListRetrofitService.getTopRatedMovies()
//                _movieList.value = movieResponse.results
                moviesRepository.refreshVideos(false)
                playlist.observeForever {response ->
                    _movieList.value=response
                }
                _dataFetchStatus.value = DataFetchStatus.DONE
            } catch (e: Exception) {
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieList.value = arrayListOf()
            }
        }
    }

    private val _ToMovieDetail = MutableLiveData<Movie>()
    val ToMovieDetail: LiveData<Movie>
        get() {
            return _ToMovieDetail
        }

    fun movieClicked(movie: Movie) {
        _ToMovieDetail.value = movie
    }
    fun gotoDetails() {
        _ToMovieDetail.value = null
    }
}