/*
 * 文  件   名: WebUtils.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月10日
 */
package org.anttribe.opengadget.runtime.common;

import javax.servlet.http.HttpServletRequest;

import org.anttribe.opengadget.runtime.constants.Keys;
import org.anttribe.opengadget.runtime.core.Page;

/**
 * @author zhaoyong
 * @version 2015年2月10日
 */
public class WebUtils
{
    /**
     * 得到当前访问的页面完整路径
     * 
     * @param request
     * @return
     */
    public static String getCurrentPagePath(HttpServletRequest request)
    {
        Page currentPage = (Page)request.getAttribute(Keys.KEY_CURRENTPAGE);
        if (null != currentPage)
        {
            return new StringBuffer("").append(getParentPagePath(currentPage))
                .append("/")
                .append(currentPage.getName())
                .toString();
        }
        return "";
    }
    
    /**
     * 得到父页面的页面路径
     * 
     * @param page
     * @return
     */
    private static String getParentPagePath(Page page)
    {
        if (null != page && null != page.getParent())
        {
            return new StringBuffer(getParentPagePath(page.getParent())).append("/")
                .append(page.getParent().getName())
                .toString();
        }
        return "";
    }
    
    /**
     * 得到当前访问的页面
     * 
     * @param request
     * @return
     */
    public static Page getCurrentPage(HttpServletRequest request)
    {
        return (Page)request.getAttribute(Keys.KEY_CURRENTPAGE);
    }
}