package com.ltu.m7019edemoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019edemoapp.databinding.MovieReviewItemBinding
import com.ltu.m7019edemoapp.model.MovieReview

class MovieReviewsAdapter : ListAdapter<MovieReview, MovieReviewsAdapter.ViewHolder>(MovieReviewDiffCallback()) {
    class ViewHolder(private var binding: MovieReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieReview:MovieReview) {
            binding.movieReview = movieReview
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieReviewItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

}

class MovieReviewDiffCallback : DiffUtil.ItemCallback<MovieReview>() {
    override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
        return oldItem.review_id == newItem.review_id
    }

    override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
        return oldItem == newItem
    }

}