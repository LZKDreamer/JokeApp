package com.lzk.jokeapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.MenuItem
import androidx.core.view.isEmpty
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_LABELED
import com.lzk.jokeapp.R
import com.lzk.jokeapp.app.AppConfig

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/8 13:48
 * Description:
 */
class AppBottomBar : BottomNavigationView {

    private val mIcons = intArrayOf(
        R.drawable.icon_tab_home,
        R.drawable.icon_tab_sofa,
        R.drawable.icon_tab_publish,
        R.drawable.icon_tab_find,
        R.drawable.icon_tab_mine
    )


    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    @SuppressLint("RestrictedApi")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        val config = AppConfig.getBottomBarConfig()
        val tabs = config.tabs

        val state = Array(2){IntArray(2)}
        state[0] = intArrayOf(android.R.attr.state_selected)
        state[1] = intArrayOf()
        val color = intArrayOf(Color.parseColor(config.activeColor),Color.parseColor(config.inActiveColor))
        val colorStateList = ColorStateList(state,color)
        itemIconTintList = colorStateList
        itemTextColor = colorStateList
        labelVisibilityMode = LABEL_VISIBILITY_LABELED
        selectedItemId = config.selectTab

        tabs.forEach {item ->
            if (!item.enable){
                return@forEach
            }
            val itemId = getItemId(item.pageUrl)
            if (itemId < 0){
                return@forEach
            }

            val menuItem: MenuItem = menu.add(0,itemId,item.index,item.title)
            menuItem.setIcon(mIcons[item.index])
        }

        tabs.forEach {item ->
            if (!item.enable){
                return@forEach
            }
            val itemId = getItemId(item.pageUrl)
            if (itemId < 0){
                return@forEach
            }

            val iconSize = dp2px(item.size)
            val menuView = getChildAt(0) as BottomNavigationMenuView
            val itemView = menuView.getChildAt(item.index) as BottomNavigationItemView
            itemView.apply {
                setIconSize(iconSize)
                if (item.title.isNullOrEmpty()){
                    val tintColor: Int = if (item.tintColor.isNullOrEmpty()) Color.parseColor("#ff678f") else Color.parseColor(item.tintColor)
                    setIconTintList(ColorStateList.valueOf(tintColor))
                    setShifting(false)
                }
            }

        }
    }

    private fun getItemId(pageUrl: String): Int{
        val destination = AppConfig.getDestConfig()[pageUrl]
        return destination?.id ?: -1
    }

    private fun dp2px(dpValue: Int): Int{
        return (resources.displayMetrics.density*dpValue+0.5f).toInt()
    }
}