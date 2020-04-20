package com.quipper.book

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.quipper.book.di.DaggerMovieComponent
import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.domain.GetPopularUseCaseImpl
import com.quipper.book.network.RetrofitClient
import com.quipper.book.repository.PopularRepository
import com.quipper.book.repository.PopularRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var useCase: GetPopularUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerMovieComponent.builder().build().inject(this)

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
        useCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(">>> RESPONSE", it.toString())
                },{

                }
            )

    }
}
