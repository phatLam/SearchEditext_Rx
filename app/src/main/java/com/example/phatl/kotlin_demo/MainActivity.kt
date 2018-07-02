package com.example.phatl.kotlin_demo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.body_layout.*
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(){
    private val TAG = MainActivity::class.java.canonicalName
//    private val mGLSurfaceView by lazy { ZoomSurfaceView(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxTextView.textChanges(editText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map{it.toString()}
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .subscribeOn(Schedulers.computation())
                .flatMap { getSearch(it) }
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    Log.i(TAG, it)
                },{
                    it.printStackTrace()
                })
    }
    private fun getSearch(query: String): Observable<String>{
        return Observable.just(query).map { query +" haha" }
    }
}
