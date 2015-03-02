/*
 * 文  件   名: PageFilterResponse.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.render;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 封装页面响应
 * 
 * @author zhaoyong
 * @version 2014年8月31日
 */
public class PageFilterResponse extends HttpServletResponseWrapper
{
    /**
     * @param response
     */
    public PageFilterResponse(HttpServletResponse response)
    {
        super(response);
    }
}