package com.helin.kotlindemo.fragment.text.joke

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helin.kotlindemo.R
import com.helin.kotlindemo.model.bean.Joke
import com.helin.kotlindemo.model.service.JokeService.Companion.getData
import com.helin.kotlindemo.model.service.JokeService.Companion.showSnackbar
import kotlinx.android.synthetic.main.fragment_joke.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.properties.Delegates

/**
 * 笑话Fragment
 * by helin
 */
class JokeFragment : Fragment() {


    private var mPage: Int =1
    private var mData : MutableList<Joke> = ArrayList()
    //委托监听mLoading变化
    private var mLoading by Delegates.observable(true){
        _,old,new ->
        mSwipeRefreshLayout.isRefreshing=new
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_joke, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
        loadData()
    }

    fun  initView (){
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initEvent (){
        mSwipeRefreshLayout.setOnRefreshListener {
            mPage=1
            loadData()
        }
        //上滑加载更多
        mRecyclerView.setOnTouchListener { view, motionEvent ->
            if (!mLoading && !mRecyclerView.canScrollVertically(1)) {
                mPage++
                loadData()
            }
            false
        }
    }


    /**
     * 加载数据
     */
    private fun loadData(){
        mLoading= true
        doAsync {
            val data = getData(mPage)
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
            }
        }
    }

    private fun initAdapter() {
        mRecyclerView.adapter = JokeAdapter(mData)
    }

}
