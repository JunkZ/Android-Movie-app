package com.ltu.m7019edemoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ltu.m7019edemoapp.adapter.MovieReviewsAdapter
import com.ltu.m7019edemoapp.adapter.MovieTrailerClickListener
import com.ltu.m7019edemoapp.adapter.MovieTrailersAdapter

import com.ltu.m7019edemoapp.databinding.MovieTrailerItemBinding
import com.ltu.m7019edemoapp.databinding.ReviewsAndTrailersBinding
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.viewmodel.ReviewsAndTrailersViewModel
import com.ltu.m7019edemoapp.viewmodel.ReviewsAndTrailersViewModelFactory

class ReviewsAndTrailers : Fragment() {

    private var _binding: ReviewsAndTrailersBinding? = null
    private var _bindingTrailer: MovieTrailerItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReviewsAndTrailersViewModel
    private lateinit var viewModelFactory: ReviewsAndTrailersViewModelFactory
    private lateinit var movie: Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = ReviewsAndTrailersBinding.inflate(inflater, container, false)
        movie = ReviewsAndTrailersArgs.fromBundle(requireArguments()).movieThird
        val application = requireNotNull(this.activity).application
        viewModelFactory = ReviewsAndTrailersViewModelFactory(application,movie.id)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ReviewsAndTrailersViewModel::class.java)




        val movieReviewsAdapter = MovieReviewsAdapter()
        binding.thirdReviewsRv.adapter = movieReviewsAdapter
        viewModel.reviewList.observe(viewLifecycleOwner, { reviewList ->
            reviewList?.let {
                movieReviewsAdapter.submitList(reviewList)
            }
        })

        val movieTrailersAdapter = MovieTrailersAdapter(
            MovieTrailerClickListener {})
        binding.thirdTrailerRv.adapter = movieTrailersAdapter
        viewModel.trailerList.observe(viewLifecycleOwner, { trailerList ->
            trailerList?.let {
                movieTrailersAdapter.submitList(trailerList)
            }
        })


        //_bindingTrailer.trailerThumbnail.setOnClickListener()
        //movie = MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        //val application = requireNotNull(this.activity).application
        //viewModelFactory = thirdFragmentViewModelFactory(application)
        //viewModel = ViewModelProvider(this,viewModelFactory).get(thirdFragmentViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}