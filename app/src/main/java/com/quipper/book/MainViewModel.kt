package com.quipper.book

import android.util.Log
import androidx.lifecycle.ViewModel
import com.quipper.book.domain.GetPopularUseCase
import com.quipper.book.model.Popular
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val useCase: GetPopularUseCase): ViewModel() {

    fun getPopular(): Single<Popular>{
        return useCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}