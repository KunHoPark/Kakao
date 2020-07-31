package com.leo.kakao.data.local

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 서버를 통한 검색 결과 데이타형식
 * @author LeoPark
 */
data class SearchData(

        @SerializedName("meta")
        var searchMetaData: SearchMetaData,

        @SerializedName("documents")
        var searchResultData: ArrayList<SearchResultData>

) : Serializable






