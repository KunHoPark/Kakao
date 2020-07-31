package com.leo.kakao.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.leo.kakao.event.OnSingleClickEvent

object ViewBindingAdapter {

    @BindingAdapter("toVisibleOrGone")
    @JvmStatic
    fun toVisibleOrGone(view: View, isVisible: Boolean) {
        view.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.GONE
        }

    }

    @BindingAdapter("toVisibleOrInvisible")
    @JvmStatic
    fun toVisibleOrInvisible(view: View, isVisible: Boolean) {
        view.visibility = when (isVisible) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }

    }

    @BindingAdapter("onSingleClick")
    @JvmStatic
    fun setOnSigleClickListener(view: View, func: (View) -> Unit) {
        view.setOnClickListener(
            OnSingleClickEvent(
                View.OnClickListener {
                    func(view)
                },
                intervalMs = 1000
            )
        )
    }
}



