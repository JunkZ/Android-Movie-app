package com.ltu.m7019edemoapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ReviewsAndTrailersViewModelFactory(private val application: Application, private val movieID: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsAndTrailersViewModel::class.java)) {
            return ReviewsAndTrailersViewModel(application,movieID) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}