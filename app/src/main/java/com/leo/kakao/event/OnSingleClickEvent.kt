package com.leo.kakao.event

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

class OnSingleClickEvent(private val clickListener: View.OnClickListener, private val intervalMs: Long = 500) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.onClick(v)
            }
        }
    }
}