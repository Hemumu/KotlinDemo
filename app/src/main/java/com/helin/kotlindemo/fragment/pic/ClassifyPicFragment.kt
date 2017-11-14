package com.helin.kotlindemo.fragment.pic

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helin.kotlindemo.R
import com.helin.kotlindemo.base.notNullSingleValue
import com.helin.kotlindemo.model.bean.Huaban
import com.helin.kotlindemo.model.service.HuabanService
import com.helin.kotlindemo.model.service.JokeService.Companion.showSnackbar
import kotlinx.android.synthetic.main.fragment_joke.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.properties.Delegates

/**
 * Created by qyhl2 on 2017/11/9.
 */
class ClassifyPicFragment : Fragment() {

    private var mType: Int by notNullSingleValue()
    private var mData: MutableList<Huaban> = ArrayList()
    private var mPage: Int = 1
    private var mIsInited: Boolean = false
    private var mIsPrepared: Boolean = false
    private var mLoading by Delegates.observable(true) { _, _, new ->
        mSwipeRefreshLayout.isRefreshing = new
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_pic_classify, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mType = arguments.getInt(EXTRA_TYPE)
        initView()
        initEvent()
        mIsPrepared = true
        lazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            lazyLoad()
    }

    fun  lazyLoad(){
        if (getUserVisibleHint() && mIsPrepared && !mIsInited) {
            //异步初始化，在初始化后显示正常UI
            loadData()
        }
    }

    private fun initView() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        (mRecyclerView.layoutManager as StaggeredGridLayoutManager).gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
    }

    private fun initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener {
            mPage = 1
            loadData()
        }
        mRecyclerView.setOnTouchListener { _, _ ->
            if (!mLoading && !mRecyclerView.canScrollVertically(1)) {
                mPage++
                loadData()
            }
            false
        }
    }

    private fun loadData() {
        mLoading = true
        doAsync {
            val data = HuabanService.getData(mType, mPage)
            uiThread {
                mLoading = false
                if (data == null) {
                    showSnackbar(view as ViewGroup, "加载失败")
                    return@uiThread
                }
                if (mRecyclerView.adapter == null) {
                    mData.addAll(data)
                    initAdapter()
                } else if (mPage > 1) {
                    val pos = mData.size
                    mData.addAll(data)
                    mRecyclerView.adapter.notifyItemRangeInserted(pos, data.size)
                } else {
                    mData.clear()
                    mData.addAll(data)
                    mRecyclerView.adapter.notifyDataSetChanged()
                }
                mIsInited = true
            }
        }
    }

    private fun initAdapter() {
        mRecyclerView.adapter = PicPageAdapter(mData)
        (mRecyclerView.adapter as PicPageAdapter).setOnItemCLick(object : PicPageAdapter.OnItemClickListener {
            override fun onClick(view: View, url: String) {
//                BigPicActivity.launch(this@ClassifyPicFragment.activity as AppCompatActivity, view, url)
            }
        })

    }


    companion object {
        private val EXTRA_TYPE: String = "extra_type"
        fun newInstance(type: Int): ClassifyPicFragment {
            val mFragment = ClassifyPicFragment()
            val bundle = Bundle()
            bundle.putInt(EXTRA_TYPE, type)
            mFragment.arguments = bundle
            return mFragment
        }
    }
}