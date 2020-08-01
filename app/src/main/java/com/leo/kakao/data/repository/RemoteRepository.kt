package com.leo.kakao.data.repository

import androidx.lifecycle.LiveData
import com.leo.kakao.data.local.SearchData
import com.leo.kakao.data.local.SearchLocalDao
import com.leo.kakao.data.local.SearchLocalData
import com.leo.kakao.data.remote.api.RemoteApi
import com.leo.kakao.util.NetworkUtil
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*

/**
 * Github 서버 & DB의 Repository.
 * @author LeoPark
 */
class RemoteRepository(private val remoteApi: RemoteApi, private val userDao: SearchLocalDao) {


    fun loadKakaoImage(searchValue: String, page: Int=1, size: Int=80): Flowable<SearchData> {
        return if (NetworkUtil.isNetworkAvailAble()) {
            remoteApi.getKakaoImage(searchValue, page, size).subscribeOn(Schedulers.io())
        }else{
            Flowable.error {throw IOException("Network connection fail")}
        }
    }

}