package com.leo.kakao.data.remote.api


import com.leo.kakao.data.local.SearchData
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Github APIs
 * @author LeoPark
 */
interface RemoteApi {

    @GET("v2/search/image")
    fun getKakaoImage(@Query("query")query: String, @Query("page")page: Int, @Query("size")size: Int ): Flowable<SearchData>

}