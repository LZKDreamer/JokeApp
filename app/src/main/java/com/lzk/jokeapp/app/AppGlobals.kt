package com.lzk.jokeapp.app

import android.app.Application
import java.lang.reflect.Method

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
                val method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication")
                return method.invoke(null,null) as Application
            }catch (e: Exception){

            }

        }
        return mApplication!!
    }
}