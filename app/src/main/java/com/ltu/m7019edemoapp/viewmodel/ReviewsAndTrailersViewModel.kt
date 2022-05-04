package com.ltu.m7019edemoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.model.MovieReview
import com.ltu.m7019edemoapp.model.MovieTrailer
import com.ltu.m7019edemoapp.network.MovieReviewResponse
import com.ltu.m7019edemoapp.network.MovieTrailerResponse
import com.ltu.m7019edemoapp.network.TMDBApi
import kotlinx.coroutines.launch


class ReviewsAndTrailersViewModel(application: Application, movieID: Long) : AndroidViewModel(application) {
    private val _reviewList = MutableLiveData<List<MovieReview>>()
    var id = movieID
    val reviewList: LiveData<List<MovieReview>>
        get() {
            return _reviewList
        }

    private val _trailerList = MutableLiveData<List<MovieTrailer>>()
    val trailerList: LiveData<List<MovieTrailer>>
        get() {
            return _trailerList
        }

    private val _trailerLink = MutableLiveData<MovieTrailer>()
    val trailerLink: LiveData<MovieTrailer>
        get() {
            return _trailerLink
        }

    fun getMovieReviews() {
        viewModelScope.launch {
            _reviewList.value = TMDBApi.movieListRetrofitService.getMovieReviews("$id/reviews").results
        }

    }
    fun getMovieTrailers() {
        viewModelScope.launch {
            _trailerList.value = TMDBApi.movieListRetrofitService.getMovieTrailers("$id/videos").results
        }

    }

    fun trailerClick(movieTrailer: MovieTrailer) {
        _trailerLink.value = movieTrailer
    }

    private val _ToThirdFrag = MutableLiveData<Movie>()
    val ToThirdFrag: LiveData<Movie>
        get() {
            return _ToThirdFrag
        }

    fun thirdFragmentClicked(movie: Movie) {
        _ToThirdFrag.value = movie
    }

    init {
        getMovieReviews()
        getMovieTrailers()
        //_movieList.postValue(Movies().list)
    }

    //Addmovie? from lecture 05.30
}