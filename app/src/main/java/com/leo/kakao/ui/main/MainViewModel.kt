package com.leo.kakao.ui.main


import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.jaredrummler.materialspinner.MaterialSpinner
import com.leo.kakao.R
import com.leo.kakao.callback.OnItemClickListener
import com.leo.kakao.data.local.SearchData
import com.leo.kakao.data.local.SearchResultData
import com.leo.kakao.data.repository.RemoteRepository
import com.leo.kakao.event.SingleLiveEvent
import com.leo.kakao.exception.SearchStopException
import com.leo.kakao.ui.base.BaseViewModel
import com.leo.kakao.util.InfiniteScrollListener
import com.leo.kakao.util.LeoLog
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

    private val ALL = "All"
    val hideKeyboardLiveEvent = SingleLiveEvent<Unit>()
    val searchingLiveEvent = SingleLiveEvent<Pair<String, (List<SearchResultData>)->Unit>>()

    val searchingText = ObservableField<String>("태희")
    val listData = ObservableArrayList<SearchResultData>()
    private val searchResultHashMap = HashMap<String, ArrayList<SearchResultData>>()
    val collectionData = ObservableArrayList<String>()
    val infiniteScrollListener = InfiniteScrollListener({searchingMore(getCollectionData)}, {getCollectionData()})

    @Volatile
    var isNewSearching = false
        @Synchronized get
        @Synchronized set

    fun searching(searchWord: String, funcGetCollectionData:(List<SearchResultData>)->Unit) {
        Observable.just(searchWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                isNewSearching = true
                collectionData.apply {
                    clear()
                }
                searchResultHashMap?.let { hashMap ->
                    hashMap.clear()
                }
                listData.clear()
                infiniteScrollListener.init()
            }
            .flatMap {
                remoteRepository.loadKakaoImage(searchWord).toObservable()
            }
            .subscribe(
                {
                    synchronized(this) {
                        searchResultHashMap[ALL] = it.searchResultData
                        listData.addAll(it.searchResultData)

                        isNewSearching = false
                        funcGetCollectionData(it.searchResultData)
                    }
                },
                { throwable ->
                    LeoLog.e(tag, "message=${throwable.message}")
                }
            ).apply { viewDisposables.add(this) }

    }

    private fun searchingMore(funcGetCollectionData:(List<SearchResultData>)->Unit) {
        remoteRepository.loadKakaoImage(searchingText.get().toString(), (listData.size/80)+1).toObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { searchData ->
                    synchronized(this) {
                        searchResultHashMap[ALL]?.let {
                            it.addAll(searchData.searchResultData)
                        }
                        listData.addAll(searchData.searchResultData)
                        funcGetCollectionData(searchData.searchResultData)
                    }
                },
                { throwable ->
                    LeoLog.e(tag, "message=${throwable.message}")
                }
            ).apply { viewDisposables.add(this) }
    }

    private fun getCollectionData(): (List<SearchResultData>)-> Unit = getCollectionData
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
                    collectionData.clear()
                }
                arrayListOf()
            }
            .takeLast(1)
            .subscribe(
                {
                    collectionData.clear()
                    if (it.isEmpty()) {
                        collectionData.add(ALL)
                    } else {
                        collectionData.addAll(it)
                    }
                },
                { throwable ->
                    LeoLog.e(tag, "message=${throwable.message}")
                }
            ).apply { viewDisposables.add(this) }

    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btnSearch -> {
                searchingText.get()?.let {
                    hideKeyboardLiveEvent.call()
                    listData.clear()
                    searchingLiveEvent.value =searchingText.get().toString() to getCollectionData
                }
            }
        }
    }

    val onEditorActionListener= TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchingText.get()?.let {
                hideKeyboardLiveEvent.call()
                listData.clear()
                searchingLiveEvent.value =searchingText.get().toString() to getCollectionData
            }
            true
        }
        false
    }

    val onSpinnerItemSelectedListenr = MaterialSpinner.OnItemSelectedListener<String> { _, position, _, _ ->
        if (collectionData.size>position) {
            collectionData[position].let { collection ->
                searchResultHashMap[collection]?.let { searchData ->
                    listData.clear()
                    listData.addAll(searchData)
                    infiniteScrollListener.isMoreAble = collection==ALL
                }
            }
        }
    }

    val onItemClickListener= object : OnItemClickListener {
        override fun onItemClick(item: SearchResultData, view: View, position: Int) {
        }
    }
}
