package com.leo.kakao.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable

/**
 * DB 쿼리
 * @author LeoPark
 */
@Dao
interface SearchLocalDao {

    @Query("SELECT * FROM bookmark_search_table WHERE id = (:id)")
    fun getUserDataById(id: Int): SearchLocalData

    @Query("SELECT * FROM bookmark_search_table WHERE id = (:id)")
    fun getUserDataRxByCoinId(id: Int): Flowable<SearchLocalData>

    @Query("SELECT * FROM bookmark_search_table WHERE id = (:id)")
    fun getLiveUserDataByCoinId(id: Int): LiveData<SearchLocalData>

    @RawQuery(observedEntities = [SearchLocalData::class])
    fun searchLiveUserDataRaw(query: SupportSQLiteQuery): LiveData<List<SearchLocalData>>

    @Insert
    fun insert(userData: SearchLocalData)

    @Insert
    fun insertAll(userData: List<SearchLocalData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceAll(userData: List<SearchLocalData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replace(userData: SearchLocalData)

    @Delete
    fun delete(userData: SearchLocalData)

    @Query("DELETE FROM bookmark_search_table")
    fun deleteAll()

    @Update
    fun update(userData: SearchLocalData)

}
