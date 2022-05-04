package com.ltu.m7019edemoapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ltu.m7019edemoapp.R
import com.ltu.m7019edemoapp.databinding.MovieTrailerItemBinding
import com.ltu.m7019edemoapp.model.MovieTrailer

class MovieTrailersAdapter(private val trailerClickListener: MovieTrailerClickListener) : ListAdapter<MovieTrailer,MovieTrailersAdapter.ViewHolder>(MovieTrailerDiffCallback()) {
    class ViewHolder(private var binding: MovieTrailerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieTrailer: MovieTrailer, trailerClickListener: MovieTrailerClickListener) {
            binding.movieTrailer = movieTrailer
            binding.clickListener = trailerClickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieTrailerItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        return holder.bind(getItem(position), trailerClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


}

class MovieTrailerDiffCallback : DiffUtil.ItemCallback<MovieTrailer>() {
    override fun areItemsTheSame(oldItem: MovieTrailer, newItem: MovieTrailer): Boolean {
        return oldItem.trailer_id == newItem.trailer_id
    }

    override fun areContentsTheSame(oldItem: MovieTrailer, newItem: MovieTrailer): Boolean {
        return oldItem == newItem
    }

}

class MovieTrailersClickListener(val clickListener: (movie: MovieTrailer) -> Unit) {
    fun onClick(movie: MovieTrailer) = clickListener(movie)
}

class MovieTrailerClickListener(val clickListener: (movieTrailer: MovieTrailer) -> Unit) {
    fun onClick(view:View ,movieTrailer: MovieTrailer){
        Log.i("Adapter","MOVIE TRAILER CLICKED")

            //val args = GameWonFragmentArgs.fromBundle(requireArguments())
            Log.i("Trailer","SHAREINTENT WORKED!!!")

            val url = "https://youtube.com/watch?v="+movieTrailer.key
            val shareIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            view.context.startActivity(shareIntent)

        }

}



