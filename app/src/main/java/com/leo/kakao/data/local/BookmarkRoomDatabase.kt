package com.leo.kakao.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [
    UserData::class
]
        , version = 1)

abstract class BookmarkRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}
