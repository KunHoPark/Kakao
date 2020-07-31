package com.leo.kakao.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jaredrummler.materialspinner.MaterialSpinner
import com.leo.kakao.databinding.MainFragmentBinding
import com.leo.kakao.di.scope.ActivityScoped
import com.leo.kakao.extension.hideKeyboard
import com.leo.kakao.extension.isKeyboardShow
import com.leo.kakao.ui.base.BaseFragment
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

    private val ANDROID_VERSIONS = arrayListOf(
        "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Nougat", "Oreo",
        "Cupcake2", "Donut2", "Eclair2", "Froyo2", "Gingerbread2", "Honeycomb2", "Ice Cream Sandwich2", "Jelly Bean2", "KitKat2", "Lollipop2", "Marshmallow2", "Nougat2", "Oreo2",
        "Cupcake3", "Donut3", "Eclair3", "Froyo3", "Gingerbread3", "Honeycomb3", "Ice Cream Sandwich3", "Jelly Bean3", "KitKat3", "Lollipop3", "Marshmallow3", "Nougat3", "Oreo3"
    )
    private val ANDROID_VERSIONS_1 = arrayListOf(
        "All"
    )

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


        spinner.setItems(ANDROID_VERSIONS)
        spinner.setOnItemSelectedListener(object: MaterialSpinner.OnItemSelectedListener<String> {
            override fun onItemSelected( view: MaterialSpinner?, position: Int, id: Long, item: String?) {
                spinner.setItems(ANDROID_VERSIONS_1)
            }
        })

    }

    override fun subscribe() {
        viewModel?.run {
            hideKeyboardLiveEvent.observe(this@MainFragment, Observer {
                svSearch.hideKeyboard()
            })
        }

    }
}
