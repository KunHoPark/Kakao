package com.leo.kakao.ui.main

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jaredrummler.materialspinner.MaterialSpinner
import com.leo.kakao.adapter.MainAdapter
import com.leo.kakao.callback.OnItemClickListener
import com.leo.kakao.data.local.SearchResultData
import com.leo.kakao.util.InfiniteScrollListener


/**
 * Main화면의 DataBinding을 처리 한다.
 * @author LeoPark
 **/
object MainBindingAdapter {


    @BindingAdapter("onItemListener", "onScollListener","listData", requireAll = false)
    @JvmStatic
    fun setMainAdapter(view: RecyclerView, listener: OnItemClickListener, scrollListener: InfiniteScrollListener, data: ArrayList<SearchResultData>?) {
        view.adapter?.run {
            if (this is MainAdapter) {
                data?.let {
                    addItems(it)
                }
            }
        } ?: run {
            MainAdapter()
                .apply {
                    view.adapter = this
                    setOnItemClickListener(listener)
                    scrollListener.layoutManager = view.layoutManager as LinearLayoutManager
                    view.addOnScrollListener(scrollListener)
                }
        }

    }

    @BindingAdapter("setSpinnerData")
    @JvmStatic
    fun setSpinnerData(view: MaterialSpinner, data: ArrayList<String>) {
        data.let {
            if (data.size<=0){
                view.setItems(arrayListOf("All"))
            } else {
                view.setItems(it)
            }
        }
    }

    @BindingAdapter("setSpinnerOnItemSelectedListener")
    @JvmStatic
    fun setSpinnerOnItemSelectedListener(view: MaterialSpinner, listener: MaterialSpinner.OnItemSelectedListener<*>){
        view.setOnItemSelectedListener(listener)
    }

    @BindingAdapter("onEditorActionListener")
    @JvmStatic
    fun setOnEditorActionListener(view: EditText, listener: TextView.OnEditorActionListener) {
        view.setOnEditorActionListener(listener)
    }

    @BindingAdapter("list_searchWord")
    @JvmStatic
    fun setSearchWord(recyclerView: RecyclerView, searchWord: String) {
        recyclerView.adapter?.let {
            (it as MainAdapter).apply {
                setSearchWord(searchWord)
            }
        }
    }

    @BindingAdapter("list_reloadGithub")
    @JvmStatic
    fun reloadGithubList(recyclerView: RecyclerView, isReload: Boolean) {
        if (isReload) {
            recyclerView.adapter?.let {
                it.notifyDataSetChanged()
            }
        }
    }

    @BindingAdapter("bind_editText")
    @JvmStatic
    fun onBindEditText(view: EditText, func: (EditText)->Unit) {
        func(view)
    }

}