package com.leo.kakao.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leo.kakao.databinding.MainFragmentBinding
import com.leo.kakao.di.scope.ActivityScoped
import com.leo.kakao.extension.hideKeyboard
import com.leo.kakao.ui.base.BaseFragment
import com.leo.kakao.util.InfiniteScrollListener
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

/**
 * Main화면의 MainFragment.
 * TabLayout을 사용하여 TabFragment(GithubTabFragment, BookmarkTabFragment)를 추가해준다.
 * ViewFactory 타입의 의존성은 FragmentModule이 없으므로 부모인 ActivitySubComponent를 검색하여 MainModule에서 생성한다.
 * @author LeoPark
 **/
@ActivityScoped
class MainFragment @Inject constructor() : BaseFragment() {
    internal val tag = this.javaClass.simpleName

    private lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding: MainFragmentBinding
    @Inject lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewDataBinding = MainFragmentBinding.inflate(inflater, container, false)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.viewDisposables = viewDisposables
        viewDataBinding.also {
            it.viewModel = viewModel
            it.lifecycleOwner = activity
        }

        subscribe()
    }

    override fun subscribe() {
        viewModel.run {
            hideKeyboardLiveEvent.observe(this@MainFragment, Observer {
                svSearch.hideKeyboard()
            })
            searchingLiveEvent.observe(this@MainFragment, Observer {
                recycler.adapter?.let { adapter ->
                    adapter.notifyDataSetChanged()
                }
                viewModel.searching(it.first, it.second)
            })
        }

    }
}
