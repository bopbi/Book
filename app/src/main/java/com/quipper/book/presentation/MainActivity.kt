package com.quipper.book.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.quipper.book.R
import com.quipper.book.di.DaggerMovieComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMovieComponent.builder().build().inject(this)
        val vm = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]
        initRecycleView()

        vm.getPopular().subscribe(
            {
                Log.d(">>> RESPONSE", it.toString())
                adapter = MainAdapter(it.results)
                rv_list.adapter = adapter
            },{
                Log.d(">>> ERROR", it.message)
            }
        )


        //viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // tugas
        // viewmodel.getPopular.subcribe....
//        viewModelStore.getPopular()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    Log.d(">>> RESPONSE", it.toString())
//                },{
//
//                }
//            )

        // layer UI
        /*useCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(">>> RESPONSE", it.toString())
                },{

                }
            )*/
    }

    private fun initRecycleView() {
        with(rv_list){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        }
    }
}
