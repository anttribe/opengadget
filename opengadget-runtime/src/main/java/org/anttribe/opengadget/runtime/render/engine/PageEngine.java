/*
 * 文  件   名: PageEngine.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.render.engine;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 页面处理引擎
 * 
 * @author zhaoyong
 * @version 2014年8月31日
 */
public interface PageEngine
{
    /**
     * 渲染页面
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws PageEngineException
     */
    void doRender(HttpServletRequest request, HttpServletResponse response)
        throws PageEngineException;
    
    /**
     * 处理action请求
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws PageEngineException
     */
    void doAction(HttpServletRequest request, HttpServletResponse response)
        throws PageEngineException;
    
    /**
     * 处理resource请求
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    void doResource(HttpServletRequest request, HttpServletResponse response);
}