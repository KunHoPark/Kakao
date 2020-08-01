package com.leo.kakao.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leo.kakao.data.local.SearchResultData


/**
 * 리스트의 더보기를 처리한다.
 * 1번 째 인자 값(고차함수)를 통해 더보기시 실행할 함수를 호출한다.
 * @author LeoPark
 **/
class InfiniteScrollListener(val func: ((List<SearchResultData>) -> Unit) -> Unit, private val funcGetCollectionData:(List<SearchResultData>)-> Unit) : RecyclerView.OnScrollListener() {
  internal val tag = this.javaClass.simpleName
  var isMoreAble = true
  var layoutManager: LinearLayoutManager?=null
  var previousTotal = 0
  private var loading = true
  private var visibleThreshold = 2
  private var firstVisibleItem = 0
  private var visibleItemCount = 0
  private var totalItemCount = 0

  fun init() {
    isMoreAble = true
    previousTotal=0
    loading = true
  }

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)

    if (dy > 0 && isMoreAble) {
      visibleItemCount = recyclerView.childCount
      layoutManager?.let {
        totalItemCount = it.itemCount
        firstVisibleItem = it.findFirstVisibleItemPosition()
      }

      if (loading) {
        if (totalItemCount > previousTotal) {
          LeoLog.i(tag, "onScrolled totalItemCount=$totalItemCount, previousTotal=$previousTotal")
          loading = false
          previousTotal = totalItemCount
        }
      }
      if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
        // End has been reached

        LeoLog.i(tag, "onScrolled totalItemCount1=$totalItemCount, previousTotal1=$previousTotal")
        func(funcGetCollectionData)
        loading = true
      }
    }
  }
}