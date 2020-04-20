package com.quipper.book.model

import com.quipper.book.model.Movie

data class Popular(
    val total_results: Int,
    val results: List<Movie>
)
