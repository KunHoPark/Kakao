package com.leo.kakao.ui.main

import android.os.Bundle
import com.leo.kakao.R
import com.leo.kakao.ui.base.BaseActivity
import dagger.Lazy
import javax.inject.Inject

/**
 * Main화면의 Activity
 * Main 화면에 attached된 Fragment들을 생성한다.
 * @author LeoPark
 **/
class MainActivity : BaseActivity() {
    internal val tag = this.javaClass.simpleName

    /**
     * ActivitySubComponent(MainModule) 의존성 요청
     */
    @Inject
    lateinit var mainFragmentProvider: Lazy<MainFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mainFragmentProvider.get())
                    .commitNow()
        }
    }


    override fun onBackPressed() {
        backButtonClickSource.onNext(true)
    }

}
