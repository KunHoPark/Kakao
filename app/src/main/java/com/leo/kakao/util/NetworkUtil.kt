package com.leo.kakao.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.leo.kakao.application.MyApplication

object NetworkUtil {

    fun isNetworkAvailAble(): Boolean {
        val cm = MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnected == true
    }
}