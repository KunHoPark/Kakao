package com.leo.kakao.application

import android.content.Context
import android.content.res.Resources
import com.facebook.stetho.Stetho
import com.kakao.auth.IApplicationConfig
import com.kakao.auth.KakaoAdapter
import com.kakao.auth.KakaoSDK
import com.leo.kakao.di.component.AppComponent
import com.leo.kakao.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import timber.log.Timber


/**
 * DaggerApplication를 상속받고, AppComponent에서 정의한 Builder를 활용하여 Component와 연결 한다.
 * @author LeoPark
 */
class MyApplication : MultidexDaggerApplication() {
    companion object {
        private var instance: MyApplication? = null
        @JvmStatic val context: Context by lazy { instance!!.applicationContext }
        @JvmStatic val application by lazy { instance!! }
        @JvmStatic val resource : Resources by lazy { instance!!.applicationContext.resources }
    }

    var appComponent: AppComponent?=null

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        if (appComponent==null) {
            appComponent = DaggerAppComponent.builder()
                .application(instance!!)
                .build()
        }

        initialize()
    }

    override fun applicationInjector(): AndroidInjector<out MultidexDaggerApplication> {
        if (appComponent==null) {
            appComponent = DaggerAppComponent.builder()
                .application(instance!!)
                .build()
        }

        return appComponent!!
    }

    private fun initialize() {
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

        Stetho.initializeWithDefaults(this)

        // SDK 초기화
        KakaoSDK.init(object : KakaoAdapter() {
            override fun getApplicationConfig(): IApplicationConfig {
                return IApplicationConfig { this@MyApplication }
            }
        })
    }

}
