/*
 * 文  件   名: WebUtils.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月10日
 */
package org.anttribe.opengadget.runtime.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    
    /**
     * 设置Cookie
     * @param response
     * @param domain 设置cookie所在域
     * @param path 设置cookie所在路径
     * @param isHttpOnly 是否只读
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response, String domain, String path, boolean isHttpOnly,
        String name, String value, int maxAge)
    {
        Cookie cookie = new Cookie(name, value);
        
        // 所在域：比如a1.4bu4.com 和 a2.4bu4.com 共享cookie
        if (null != domain && !domain.isEmpty())
        {
            cookie.setDomain(domain);
        }
        
        // 设置cookie所在路径
        cookie.setPath("/");
        if (null != path && !path.isEmpty())
        {
            cookie.setPath(path);
        }
        
        // 是否只读
        // cookie.setHttpOnly(isHttpOnly);
        
        // 设置cookie的过期时间
        if (maxAge > 0)
        {
            cookie.setMaxAge(maxAge);
        }
        
        // 添加cookie
        response.addCookie(cookie);
    }
}