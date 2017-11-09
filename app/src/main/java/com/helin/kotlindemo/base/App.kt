package com.helin.kotlindemo.base

import android.app.Application

/**
 * Created by qyhl2 on 2017/11/9.
 */

class App : Application() {


    companion object {
        var instance: App by notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}