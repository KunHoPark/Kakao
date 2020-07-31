package com.leo.kakao.ui.base

import android.view.View
import androidx.lifecycle.ViewModel
import com.leo.kakao.util.AutoClearedDisposable
import io.reactivex.subjects.PublishSubject

/**
 * ViwModel의 Base class.
 * @author LeoPark
 **/
open abstract class BaseViewModel: ViewModel() {

   lateinit var viewDisposables: AutoClearedDisposable
   val message: PublishSubject<String> = PublishSubject.create()

   val onSingleClick: (view: View)->Unit = fun(view: View) {
      onClick(view)
   }

   abstract fun onClick(view: View)

}