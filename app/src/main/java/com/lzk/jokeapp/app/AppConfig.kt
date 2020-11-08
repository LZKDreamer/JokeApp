package com.lzk.jokeapp.app

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.lzk.jokeapp.ext.navigation.BottomBar
import com.lzk.jokeapp.ext.navigation.Destination
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/7 15:26
 * Description:
 */
object AppConfig {

    private var mDestConfig: HashMap<String, Destination>? = null
    private var mBottomBar: BottomBar? = null

    fun getDestConfig(): HashMap<String, Destination>{
        if (mDestConfig == null){
            val content = parseFile("destination.json")
            val type = object :TypeReference<HashMap<String, Destination>>(){}.type
            mDestConfig = JSON.parseObject(content,type)
        }
        return mDestConfig!!
    }

    fun getBottomBar(): BottomBar{
        if (mBottomBar == null){
            val content = parseFile("main_tabs_config.json")
            mBottomBar = JSON.parseObject(content,BottomBar::class.java)
        }

        return mBottomBar!!
    }

    private fun parseFile(fileName: String): String{
        val assetManager = AppGlobals.getApplication().resources.assets
        var inputStream: InputStream? = null
        var reader: BufferedReader? = null
        val strBuilder = StringBuilder()
        try {
            inputStream = assetManager.open(fileName)
            reader = BufferedReader(InputStreamReader(inputStream))
            var line: String? = null
            while ( reader.readLine().also { line = it } != null){
                strBuilder.append(line)
            }

        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            try{
                inputStream?.close()
                reader?.close()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        return strBuilder.toString()
    }
}