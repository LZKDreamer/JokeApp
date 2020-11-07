package com.lzk.libnavannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Author: LiaoZhongKai
 * Date: 2020/11/1 15:37
 * Description:
 */
@Target(ElementType.TYPE)
public @interface FragmentDestination {
    String pageUrl();
    boolean needLogin() default false;
    boolean asStarter() default false;
}
