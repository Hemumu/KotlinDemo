package com.helin.kotlindemo.model.bean

import com.google.gson.JsonObject

/**
 * Created by qyhl2 on 2017/11/9.
 */
data class HuabanResult(val showapi_res_code: String,
                        val showapi_res_error: String,
                        val showapi_res_body: JsonObject)

data class Huaban(val title: String,
                  val thumb: String,
                  val url: String)