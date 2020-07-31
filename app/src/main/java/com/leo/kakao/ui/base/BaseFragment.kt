package com.leo.kakao.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.leo.kakao.event.OnActivityListener
import com.leo.kakao.extension.plusAssign
import com.leo.kakao.util.AutoClearedDisposable
import com.leo.kakao.util.LeoLog
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

/**
 * Fragment의 Base class.
 * @author LeoPark
 **/
open abstract class BaseFragment: DaggerFragment() {
    lateinit var viewDisposables : AutoClearedDisposable
    protected var onActivityList: OnActivityListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onActivityList = (context as BaseActivity).onActivityListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDisposables = AutoClearedDisposable(lifecycleOwner = activity as AppCompatActivity, alwaysClearOnStop = false)
        lifecycle += viewDisposables
    }

    abstract fun subscribe()


    protected fun showToast(message: String) {
        Toast.makeText(activity!!.applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}