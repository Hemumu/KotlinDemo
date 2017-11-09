package com.helin.kotlindemo.model.service

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.ViewGroup
import com.google.gson.Gson
import com.helin.kotlindemo.R
import com.helin.kotlindemo.commen.Const.Companion.buildUrl
import com.helin.kotlindemo.model.bean.Joke
import com.helin.kotlindemo.model.bean.JokeResult
import java.net.URL

/**
 * Created by qyhl2 on 2017/11/9.
 */

    class JokeService {
        companion object {
            val baseUrl = "http://route.showapi.com/341-1"

            fun buildBaseUrl(page: Int, maxResult: Int): String {
                return buildUrl("$baseUrl?page=$page&maxResult=$maxResult")
            }

            fun getData(page: Int, maxResult: Int = 10): List<Joke>? {
                Log.e("result","get data")
                var forecastJsonStr: String? = null
                try {
                    forecastJsonStr = URL(buildBaseUrl(page, maxResult)).readText()
                    Log.e("result","data = $forecastJsonStr")
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }
                val data = Gson().fromJson(forecastJsonStr, JokeResult::class.java)
                val jokes: List<Joke> = data.showapi_res_body.contentlist
                return if (jokes.isNotEmpty()) jokes else null
            }


            fun showSnackbar(viewGroup: ViewGroup, text: String, duration: Int = 1000) {
                val snack = Snackbar.make(viewGroup, text, duration)
                snack.view.setBackgroundColor(ContextCompat.getColor(viewGroup.context, R.color.colorPrimary))
                snack.show()
            }
        }


}