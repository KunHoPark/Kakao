package com.leo.kakao.data.local

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 서버를 통한 검색 결과 데이타형식
 * @author LeoPark
 */
data class SearchResultData(

        @SerializedName("collection")
        var collection:String,

        @SerializedName("thumbnail_url")
        var thumbnailUrl:String,

        @SerializedName("image_url")
        var imageUrl:String,

        @SerializedName("width")
        var width:Int,

        @SerializedName("height")
        var height:Int,

        @SerializedName("display_sitename")
        var displaySitename:String,

        @SerializedName("doc_url")
        var docUrl:String,

        @SerializedName("datetime")
        var datetime:String

) : Serializable




