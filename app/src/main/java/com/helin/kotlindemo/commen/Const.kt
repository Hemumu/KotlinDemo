package com.helin.kotlindemo.commen

/**
 * Created by qyhl2 on 2017/11/9.
 */
class Const {
    companion object {

        private val yiyuan_appid = "49634"
        private val yiyuan_secret = "d3d1bc0a3cda4cd69de7d373b1138f32"
        private val yiyuanAuth = "&showapi_sign=$yiyuan_secret&showapi_appid=$yiyuan_appid"

        fun buildUrl(url: String): String {
            return "$url&$yiyuanAuth"
        }


    }
}