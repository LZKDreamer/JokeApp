package com.lzk.jokeapp.ext.navigation

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.*
import androidx.navigation.fragment.FragmentNavigator
import com.lzk.jokeapp.app.AppConfig
import com.lzk.jokeapp.app.AppGlobals

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/7 16:18
 * Description:
 */
object NavGraphBuilder {

    fun build(activity: FragmentActivity,navController: NavController,childFragmentManager: FragmentManager,containerId: Int){
        val provider = navController.navigatorProvider
        val fragmentNavigator = FixFragmentNavigator(activity,childFragmentManager,containerId)
        provider.addNavigator(fragmentNavigator)
        val activityNavigator = provider.getNavigator(ActivityNavigator::class.java)
        val destConfig = AppConfig.getDestConfig()
        val navGraph = NavGraph(NavGraphNavigator(provider))

        destConfig.values.forEach {value ->
            if (value.isFragment){
                val fragmentDest = fragmentNavigator.createDestination()
                fragmentDest.className = value.clazzName
                fragmentDest.id = value.id
                fragmentDest.addDeepLink(value.pageUrl)
                navGraph.addDestination(fragmentDest)
            }else{
                val activityDest = activityNavigator.createDestination()
                activityDest.setComponentName(ComponentName(AppGlobals.getApplication().packageName,value.clazzName))
                activityDest.id = value.id
                activityDest.addDeepLink(value.pageUrl)
                navGraph.addDestination(activityDest)
            }

            if (value.asStarter){
                navGraph.startDestination = value.id
            }
        }
        navController.graph = navGraph
    }
}