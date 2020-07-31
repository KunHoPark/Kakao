package com.leo.kakao.data.local

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 서버를 통한 검색 결과 데이타형식
 * @author LeoPark
 */
data class SearchMetaData(

        @SerializedName("total_count")
        var totalCount: Double,

        @SerializedName("pageable_count")
        var pageableCount: Double,

        @SerializedName("is_end")
        var isEnd: Boolean

) : Serializable






