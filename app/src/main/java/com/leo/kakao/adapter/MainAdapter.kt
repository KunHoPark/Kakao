package com.leo.kakao.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leo.kakao.adapter.viewholder.MainBindingViewHolder
import com.leo.kakao.callback.OnItemClickListener
import com.leo.kakao.data.local.SearchResultData
import com.leo.kakao.databinding.ItemMainViewHolderBinding

/**
 * 리스트의 Adapter
 * @author LeoPark
 */
class MainAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    internal val tag = this.javaClass.simpleName
    private var listener: OnItemClickListener? = null
    private var listData: List<SearchResultData>? = null
    private var searchWord: String= ""               // 검색 단어

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBindingViewHolder {
        val binding = ItemMainViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainBindingViewHolder(binding, listener)
    }

    val setOnItemClickListener: (OnItemClickListener) -> Unit = { this.listener = it }
    val getOnItemClickListener: () -> OnItemClickListener? = { this.listener }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listData?.let {
            if (it.size>position){
                it[position].let { item ->
                    (holder as MainBindingViewHolder).onBind(item, position, searchWord)
                }
            }
        }
    }

    fun addItems(items: List<SearchResultData>) {
        listData = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listData?.let {
            it.size
        }?:0
    }

    val setSearchWord: (String) -> Unit= {
        this.searchWord = it
    }
}