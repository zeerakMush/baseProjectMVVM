package com.zk.base_project.utils

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

object RxHelper {

    fun setClick(view: View, listener: View.OnClickListener): Disposable {

        return RxView.clicks(view)
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { listener.onClick(view) }
    }

    fun setClick(vararg views: View, listener: Consumer<Any>): Disposable {

        val list = mutableListOf<Observable<Any>>()

        for (view in views) {
            list.add(RxView.clicks(view))
        }

        return Observable.merge(list)
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(listener)
    }
}
