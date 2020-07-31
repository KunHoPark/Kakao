package com.leo.kakao.extension

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.leo.kakao.application.MyApplication



inline val Int.toResString: String get() = MyApplication.context.resources.getString(this)

inline val Int.toDrawable: Drawable? get() = AppCompatResources.getDrawable(MyApplication.context, this)

fun Int.toResColor(view: TextView) {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.setTextColor(MyApplication.context.getColor(this))
    } else {
        view.setTextColor(ContextCompat.getColor(MyApplication.context, this))
    }
}

inline val Int.toResColor: Int
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MyApplication.context.getColor(this)
        } else {
            ContextCompat.getColor(MyApplication.context, this)
        }
    }

fun Int.toResStyle(view: TextView) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.setTextAppearance(this)
    } else {
        @Suppress("DEPRECATION")
        view.setTextAppearance(MyApplication.context, this)
    }
}

fun Int.toResStyle(view: Button) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        view.setTextAppearance(this)
    } else {
        @Suppress("DEPRECATION")
        view.setTextAppearance(MyApplication.context, this)
    }
}

inline val Int.toDimensionPixelOffset: Int get() = MyApplication.context.resources.getDimensionPixelOffset(this)

