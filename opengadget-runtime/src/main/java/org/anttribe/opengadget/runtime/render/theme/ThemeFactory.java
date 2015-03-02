/*
 * 文  件   名: ThemeFactory.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月30日
 */
package org.anttribe.opengadget.runtime.render.theme;

import java.util.List;

import org.anttribe.opengadget.runtime.core.Theme;

/**
 * 主题解析
 * 
 * @author zhaoyong
 * @version 2015年1月30日
 */
public interface ThemeFactory
{
    /**
     * 获取皮肤存放目录
     * 
     * @return String
     */
    String getThemeDirectory();
    
    /**
     * 获取默认皮肤
     * 
     * @return Theme
     */
    Theme getDefaultTheme();
    
    /**
     * 根据皮肤名获取皮肤
     * 
     * @param themeName String
     * @return Theme
     */
    Theme getThemeByName(String themeName);
    
    /**
     * 获取所有的皮肤
     * 
     * @return
     */
    List<Theme> getAllThemes();
}