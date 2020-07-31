package com.leo.kakao.di.module;


import com.leo.kakao.data.local.BookmarkRoomDatabase
import com.leo.kakao.data.remote.api.RemoteApi
import com.leo.kakao.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * RepositoryModule
 * AppComponent에 연결 된다.
 * @author LeoPark
 **/
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository( @Named("authorized") authRestAdapter: Retrofit, bookmarkRoomDatabase: BookmarkRoomDatabase): RemoteRepository =
            RemoteRepository(authRestAdapter.create(RemoteApi::class.java), bookmarkRoomDatabase.userDao())

}
