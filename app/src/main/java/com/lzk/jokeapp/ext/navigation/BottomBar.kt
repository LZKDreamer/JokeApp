package com.lzk.jokeapp.ext.navigation

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/7 16:42
 * Description:
 */

/**
 * activeColor : #333333
 * inActiveColor : #666666
 * tabs : [{"size":24,"enable":true,"index":0,"pageUrl":"main/tabs/home","title":"首页"},{"size":24,"enable":true,"index":1,"pageUrl":"main/tabs/sofa","title":"沙发"},{"size":40,"enable":true,"index":2,"tintColor":"#ff678f","pageUrl":"main/tabs/publish","title":""},{"size":24,"enable":true,"index":3,"pageUrl":"main/tabs/find","title":"发现"},{"size":24,"enable":true,"index":4,"pageUrl":"main/tabs/my","title":"我的"}]
 */
data class BottomBar (
    var activeColor: String,
    var inActiveColor: String,
    var tabs: List<Tab>,
    var selectTab: Int = 0//底部导航栏默认选中项
)

data class Tab(
    /**
     * size : 24
     * enable : true
     * index : 0
     * pageUrl : main/tabs/home
     * title : 首页
     * tintColor : #ff678f
     */
    var size: Int,
    var enable: Boolean,
    var index: Int,
    var pageUrl: String,
    var title: String,
    var tintColor: String
)