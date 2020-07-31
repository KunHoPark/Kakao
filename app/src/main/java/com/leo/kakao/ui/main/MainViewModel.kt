package com.leo.kakao.ui.main


import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.Transformations.map
import com.leo.kakao.R
import com.leo.kakao.callback.OnItemClickListener
import com.leo.kakao.data.local.SearchResultData
import com.leo.kakao.data.repository.RemoteRepository
import com.leo.kakao.event.SingleLiveEvent
import com.leo.kakao.exception.SearchStopException
import com.leo.kakao.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author LeoPark
 **/
class MainViewModel
@Inject constructor(private val remoteRepository: RemoteRepository) : BaseViewModel() {
    internal val tag = this.javaClass.simpleName

    val hideKeyboardLiveEvent = SingleLiveEvent<Unit>()

    val searchingText = ObservableField<String>()
    val searchResultData = ObservableArrayList<SearchResultData>()
    val searchCollectionData = ObservableArrayList<String>()
    private val searchResultHashMap = HashMap<String, ArrayList<SearchResultData>>()

    @Volatile
    var isNewSearching = false
        @Synchronized get
        @Synchronized set

    @Synchronized
    private fun searching(search: String, funcGetCollectionData:(List<SearchResultData>)->Unit) {
        searchCollectionData.clear()
        searchResultHashMap?.let {
            it.clear()
        }

        remoteRepository.loadKakaoImage(search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    synchronized(this) {
                        searchResultData.clear()
                        searchResultData.addAll(it.searchResultData)

                        funcGetCollectionData(it.searchResultData)
                    }
                },
                {

                }
            ).apply { viewDisposables.add(this) }

    }

    private val getCollectionData:(List<SearchResultData>)-> Unit = { searchData->

        Observable.fromIterable(searchData)
            .map { data ->
                when(isNewSearching) {
                    true -> {
                        throw SearchStopException()
                    }
                    false -> {
                        searchResultHashMap[data.collection]?.let {
                            it.add(data)
                        }?:let {
                            searchResultHashMap[data.collection] = arrayListOf(data)
                        }
                    }
                }
                searchResultHashMap.keys.toList()
            }
            .onErrorReturn {
                if (it is SearchStopException) {
                    searchCollectionData.clear()
                }
                arrayListOf("All")
            }
            .subscribe(
                {


                },
                {

                }
            ).apply { viewDisposables.add(this) }

    }

    val onItemClickListener= object : OnItemClickListener {
        override fun onItemClick(item: SearchResultData, view: View, position: Int) {
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btnSearch -> {
                searchingText.get()?.let {
                    hideKeyboardLiveEvent.call()
                    searching(searchingText.get().toString(), getCollectionData)
                }
            }
        }
    }

    val onEditorActionListener= TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchingText.get()?.let {
                hideKeyboardLiveEvent.call()
                searching(searchingText.get().toString(), getCollectionData)
            }
            true
        }
        false
    }

}
