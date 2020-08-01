package com.leo.kakao.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
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