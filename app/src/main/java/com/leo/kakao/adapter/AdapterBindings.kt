package com.leo.kakao.adapter

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.leo.kakao.R
import com.leo.kakao.data.local.UserData
import com.leo.kakao.extension.isEnglish
import com.leo.kakao.extension.toResColor
import com.leo.kakao.util.GlideUtil

object AdapterBindings {

    /**
     * 리스트의 이미지 로
     */
    @BindingAdapter("loadImage")
    @JvmStatic
    fun setLoadImage(imageView: ImageView, url: String) {
        GlideUtil.loadImage(imageView.context, url, imageView)
    }



}