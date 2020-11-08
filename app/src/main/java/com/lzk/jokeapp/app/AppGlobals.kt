package com.lzk.jokeapp.app

import android.app.Application

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/7 15:31
 * Description:
 */
object AppGlobals {

    private var mApplication: Application? = null

    fun getApplication(): Application{
        if (mApplication == null){
            try {
                val method = Class.forName("android.app.ActivityThread").getMethod("currentApplication")
                mApplication = method.invoke(null) as Application
                return mApplication!!
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        return mApplication!!
    }
}