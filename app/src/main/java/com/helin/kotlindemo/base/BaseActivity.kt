package com.helin.kotlindemo.base

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by qyhl2 on 2017/10/26.
 */
open class BaseActivity : AppCompatActivity (){


    fun  Context.showToast( text:String,time:Int = Toast.LENGTH_SHORT ){
        Toast.makeText(this,text,time).show()
    }

}