package com.quipper.book.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quipper.book.R
import com.quipper.book.model.Movie
import kotlinx.android.synthetic.main.item_adapter.view.*

class MainAdapter(private val data: List<Movie>): RecyclerView.Adapter<MainAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(movie: Movie){
            itemView.title.text = movie.poster_path
            val gambar = "http://image.tmdb.org/t/p/w185${movie.poster_path}"
            Log.d("GAMBAR", gambar)
            Glide.with(itemView.context).load(gambar).into(itemView.imageView)
        }
    }
}