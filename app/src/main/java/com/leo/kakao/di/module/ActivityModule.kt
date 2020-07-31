package com.leo.kakao.di.module;

import com.leo.kakao.di.scope.ActivityScoped
import com.leo.kakao.ui.main.MainActivity
import com.leo.kakao.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * RepositoryModule
 * AppComponent에 연결 된다.
 * @author LeoPark
 **/
@Module
abstract class ActivityModule {

    // ContributesAndroidInjector 어노테이션을 달고 반환 타입을 통해 해당 Activity의 Subcomponent를 생성 한다.
    @ActivityScoped
    @ContributesAndroidInjector(modules = [(MainModule::class)])
    abstract fun mainActivity(): MainActivity

}
