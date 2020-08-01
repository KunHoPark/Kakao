package com.leo.kakao.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Nullable

/**
 * 북마크된 데이타를 저장하기 위한 형식
 * @author LeoPark
 */
@Entity(tableName = "bookmark_search_table")
data class SearchLocalData(

        @PrimaryKey @ColumnInfo(name = "id")
        var id:String


) : Serializable






