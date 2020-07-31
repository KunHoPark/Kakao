package com.leo.kakao.extension

import androidx.lifecycle.Lifecycle
import com.leo.kakao.util.AutoClearedDisposable

operator fun Lifecycle.plusAssign(observer: AutoClearedDisposable)
        = this.addObserver(observer)
