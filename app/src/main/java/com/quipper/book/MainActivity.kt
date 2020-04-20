package com.quipper.book

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.quipper.book.di.DaggerMovieComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    //lateinit var useCase: GetPopularUseCase

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMovieComponent.builder().build().inject(this)
        val vm = ViewModelProviders.of(this, viewModelFactory)[MainViewModel::class.java]

        vm.getPopular().subscribe(
                {
                    Log.d(">>> RESPONSE", it.toString())
                },{

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
}
