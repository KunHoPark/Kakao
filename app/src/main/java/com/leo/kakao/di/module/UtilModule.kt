package com.leo.kakao.di.module


import android.content.Context
import com.leo.kakao.util.LeoSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * RepositoryModule
 * AppComponent에 연결 된다.
 * @author parkkh
 **/
@Module
class UtilModule {

    @Provides
    @Singleton
    fun provideLeoSharedPreferences( context: Context): LeoSharedPreferences =
        LeoSharedPreferences(context)


}
