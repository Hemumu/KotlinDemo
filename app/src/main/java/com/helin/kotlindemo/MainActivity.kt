package com.helin.kotlindemo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.helin.kotlindemo.base.BaseActivity
import com.helin.kotlindemo.base.Preference
import com.helin.kotlindemo.fragment.gif.GifFragment
import com.helin.kotlindemo.fragment.pic.PicFragment
import com.helin.kotlindemo.fragment.text.TextFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates

/**
 *   create by helin
 */
class MainActivity : BaseActivity() {
    lateinit var mTextView: TextView

    val mFragments: Array<Fragment> = arrayOf(TextFragment(), PicFragment(), GifFragment())

    val mPrexsfs: String = "zhufenzhi"

    val te:String ="he lin fenzhi "


    val  msdf:Int = 321
    var lastTime :Int by Preference(this@MainActivity, "sp_key_default_fragment", 0)

    var mCurrentIndex: Int by Delegates.observable(0) {
        _, _, new ->
        navigationView.setCheckedItem(when (new) {
            0 -> R.id.nav_text
            1 -> R.id.nav_pic
            2 -> R.id.nav_gif
            else -> R.id.nav_text
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_text -> switchFragment(0, item.title.toString(), item)
                R.id.nav_pic -> switchFragment(1, item.title.toString(), item)
                R.id.nav_gif -> switchFragment(2, item.title.toString(), item)
                R.id.nav_clear -> clearCache(item)
                R.id.nav_about -> {
                    true
                }
                else -> false
            }
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
            }

            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
            }
        })


        supportFragmentManager.beginTransaction()
                .replace(R.id.content, mFragments[mCurrentIndex])
                .commit()
        supportFragmentManager.beginTransaction().replace(R.id.content, mFragments[0]).commit()
    }

    //跳转界面
    private fun switchFragment(index: Int, title: String, item: MenuItem?): Boolean {
        if( index!= mCurrentIndex){
            val trx = supportFragmentManager.beginTransaction()
            trx.hide(mFragments[mCurrentIndex])
            if (!mFragments[index].isAdded) {
                trx.add(R.id.content, mFragments[index])
            }
            trx.show(mFragments[index]).commit()
            item?.isChecked = true
            toolbar.title = title
            mCurrentIndex = index
        }

        drawerLayout.closeDrawers()
        return true
    }

    //清理缓存
    fun clearCache(item: MenuItem): Boolean {
        showToast("清理缓存")
        drawerLayout.closeDrawers()
        return true
    }


}

