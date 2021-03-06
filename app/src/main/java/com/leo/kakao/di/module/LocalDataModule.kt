package com.leo.kakao.di.module;

import android.app.Application
import androidx.room.Room
import com.leo.kakao.data.local.BookmarkRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
* LocalDataModule
* AppComponent에 연결 된다.
* @author LeoPark
**/
@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideBookmarkDatabase(application : Application): BookmarkRoomDatabase
            = Room.databaseBuilder(application, BookmarkRoomDatabase::class.java, "bookmark_search.db")
                .allowMainThreadQueries()
                .build()
}
