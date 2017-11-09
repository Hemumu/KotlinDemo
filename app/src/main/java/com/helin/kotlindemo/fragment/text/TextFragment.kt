package com.helin.kotlindemo.fragment.text

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.helin.kotlindemo.R
import com.helin.kotlindemo.fragment.text.joke.JokeFragment
import com.helin.kotlindemo.fragment.text.rhesis.RhesisFragment
import kotlinx.android.synthetic.main.fragment_text.*

/**
 * Created by qyhl2 on 2017/11/8.
 */
class  TextFragment : Fragment() {

    val tabs: Array<String> = arrayOf("搞笑段子", "励志鸡汤")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_text, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewPager.offscreenPageLimit =tabs.size
        mViewPager.adapter = PageAdapter(childFragmentManager)
        mTabLayout.tabMode =TabLayout.MODE_FIXED
        mTabLayout.tabGravity= TabLayout.GRAVITY_FILL
        mTabLayout.setupWithViewPager(mViewPager)

    }

    /**
     * viewPaged的适配器
     */
    private inner  class  PageAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return  when( position ){
                0 -> JokeFragment()
                1 -> RhesisFragment()
                else -> RhesisFragment()
            }
        }

        override fun getCount(): Int {
            return tabs.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabs[position]
        }

    }

}