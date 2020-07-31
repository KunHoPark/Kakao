package com.leo.kakao.callback

import android.view.View
import com.leo.kakao.data.local.SearchResultData
import com.leo.kakao.data.local.UserData

/**
 * 리스트의 Callback
 * @author LeoPark
 */
interface OnItemClickListener {
    fun onItemClick(item: SearchResultData, view: View, position: Int)
}