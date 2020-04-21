package com.quipper.book.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.quipper.book.R
import com.quipper.book.di.DaggerMovieComponent
import com.quipper.book.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: MainAdapter

    private val intentSubject: PublishSubject<MainIntent> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerMovieComponent.builder().build().inject(this)

        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        initRecycleView()

        adapter = MainAdapter()
        rv_list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.getState()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(">>> RESPONSE", it.toString())
                    render(it)
                },{
                    Log.d(">>> ERROR", it.message)
                }
            )

        viewModel.processIntent(intentSubject)
        intentSubject.onNext(MainIntent.LoadPopularMovieIntent(RetrofitClient.API_KEY))


    }

    private fun render(state: MainState) {
        if (state.isLoading && state.movies.isEmpty() && !state.isError) {
            Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
        } else if (!state.isLoading && state.movies.isEmpty() && state.isError) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        } else if (!state.isLoading && state.movies.isNotEmpty() && state.isError) {
            adapter.setItems(state.movies)
        } else if (state.isLoading && state.movies.isNotEmpty() && state.isError) {
            adapter.setItems(state.movies)
        } else if (!state.isLoading && state.movies.isNotEmpty() && !state.isError) {
            adapter.setItems(state.movies)
        }
    }

    private fun initRecycleView() {
        with(rv_list){
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }
}
