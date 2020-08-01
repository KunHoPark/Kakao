package com.leo.kakao.callback

import android.view.View
import com.leo.kakao.data.local.SearchResultData

/**
 * 리스트의 Callback
 * @author LeoPark
 */
interface OnItemClickListener {
    fun onItemClick(item: SearchResultData, view: View, position: Int)
}