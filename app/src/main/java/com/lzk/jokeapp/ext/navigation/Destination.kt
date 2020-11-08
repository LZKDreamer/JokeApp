package com.lzk.jokeapp.ext.navigation

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/7 15:23
 * Description:
 */
data class Destination(
    var asStarter: Boolean,
    var clazzName: String,
    var id: Int,
    var isFragment: Boolean,
    var needLogin: Boolean,
    var pageUrl: String
)