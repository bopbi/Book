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

class MainAdapter(private val movies: MutableList<Movie> = mutableListOf()): RecyclerView.Adapter<MainAdapter.MovieViewHolder>() {

    fun setItems(newData: List<Movie>) {
        movies.clear()
        movies.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
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
