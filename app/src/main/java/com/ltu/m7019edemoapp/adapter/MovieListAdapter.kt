package com.ltu.m7019edemoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ltu.m7019edemoapp.model.Movie
import com.ltu.m7019edemoapp.databinding.MovieListItemSmallBinding


class MovieListAdapter(private val movieClickListener: MovieListClickListener) : ListAdapter<Movie,MovieListAdapter.ViewHolder>(MovieListDiffCallback()) {
    class ViewHolder(private var binding: MovieListItemSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, movieClickListener: MovieListClickListener) {
            binding.movie = movie
            binding.clickListener = movieClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemSmallBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.ViewHolder, position: Int) {
        return holder.bind(getItem(position),movieClickListener)
    }
}

class MovieListDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}

class MovieListClickListener(val clickListener: (movie: Movie) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie)
}