package com.leo.kakao.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.leo.kakao.BR
import com.leo.kakao.callback.OnItemClickListener
import com.leo.kakao.data.local.SearchResultData
import com.leo.kakao.data.local.UserData
import com.leo.kakao.databinding.ItemMainViewHolderBinding

/**
 * 리스트의 viewholder
 * @author LeoPark
 */
class MainBindingViewHolder(private var binding: ItemMainViewHolderBinding, private val listener: OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
    internal val tag = this.javaClass.simpleName

    fun onBind(any: Any, position: Int, searchWord: String?) {
        val item = any as SearchResultData
        binding.apply{

            this.setVariable(BR.searchResultData, item)
            this.setVariable(BR.searchword, searchWord)
            this.executePendingBindings()

            listener?.let {
                this.root.setOnClickListener {
                    listener.onItemClick(item, it, position)
                }
            }
        }
    }
}