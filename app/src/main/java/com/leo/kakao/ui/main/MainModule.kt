package com.leo.kakao.ui.main

import com.leo.kakao.data.repository.RemoteRepository
import com.leo.kakao.di.scope.ActivityScoped
import com.leo.kakao.di.scope.FragmentScoped
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * MainActivity의 subComponet.
 * @author LeoPark
 **/
@Module
abstract class MainModule {

    /**
     * ContributesAndroidInjector로 FragmentSubcomponent를 생성한다.
     */
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    /**
     * ViewModelFactory 타입의 의존성 객체를 생성 한다. ViwModel를 생성 해 준다.
     */
    @Module
    companion object {
        @Provides
        @ActivityScoped
        @JvmStatic fun provideViewModelFactory(remoteRepository: RemoteRepository) : MainViewModelFactory
                = MainViewModelFactory(remoteRepository)
    }

}
