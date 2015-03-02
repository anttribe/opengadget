/*
 * 文  件   名: PageFilter.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.render;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.opengadget.runtime.core.Page;
import org.anttribe.opengadget.runtime.core.Site;
import org.anttribe.opengadget.runtime.render.engine.PageEngine;
import org.anttribe.opengadget.runtime.render.engine.PageEngineException;
import org.anttribe.opengadget.runtime.render.factory.SiteFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 页面Filter
 * 
 * @author zhaoyong
 * @version 2014年8月24日
 */
public class PageFilter extends OncePerRequestFilter
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PageFilter.class);
    
    /**
     * 默认排除后缀列表, 用户配置的排除列表采用追加的形式
     */
    private static final String[] DEFAULT_EXCLUDES = new String[] {".css", ".js", ".jsp", ".vm", ".ftl", ".doc",
        ".xls", ".ppt", ".docx", ".xlsx", ".pptx", ".zip", ".rar", ".swf", ".jpg", ".png", ".jpeg", ".gif", ".bmp",
        ".eot", ".svg", ".tff", ".woff", ".map", ".woff2"};
    
    /**
     * 排除后缀列表
     */
    private String[] excludes = DEFAULT_EXCLUDES;
    
    /**
     * 默认layout路径
     */
    private static final String DEFAULT_LAYOUT_LOCATION = "layout.jsp";
    
    /**
     * 布局文件路径
     */
    private String layoutLocation = DEFAULT_LAYOUT_LOCATION;
    
    /**
     * siteFactory
     */
    private SiteFactory siteFactory;
    
    /**
     * pageEngine 页面处理引擎
     */
    private PageEngine pageEngine;
    
    @Override
    protected void initFilterBean()
        throws ServletException
    {
        super.initFilterBean();
        FilterConfig config = this.getFilterConfig();
        String excludesValue = config.getInitParameter("excludes");
        if (!StringUtils.isEmpty(excludesValue))
        {
            String[] excludesSubfixs = StringUtils.split(excludesValue, ",");
            excludes = ArrayUtils.addAll(excludes, excludesSubfixs);
        }
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException
    {
        // 排除请求, 不进行过滤处理
        String requestUri = request.getRequestURI();
        if (!ArrayUtils.isEmpty(excludes))
        {
            if(StringUtils.endsWithAny(requestUri, excludes))
            {
                filterChain.doFilter(request, response);
                return;
            }
        }
        
        // 封装请求和响应
        PageFilterRequest pageFilterRequest = new PageFilterRequest(request, siteFactory, layoutLocation);
        PageFilterResponse pageFilterResponse = new PageFilterResponse(response);
        
        // 根据请求类型调用不同处理逻辑
        RequestType requestType = pageFilterRequest.getRequestType();
        try
        {
            if (requestType == RequestType.Render)
            {
                // 从请求中获取Site和Page
                Site site = pageFilterRequest.getCurrentSite();
                Page page = pageFilterRequest.getCurrentPage();
                if (null == site || null == page)
                {
                    LOGGER.warn("Can not find site or page from request. site: {}, page: {}", site, page);
                    // 跳转到404
                    response.sendError(404);
                    return;
                }
                pageEngine.doRender(pageFilterRequest, pageFilterResponse);
                return;
            }
            else if (requestType == RequestType.Action)
            {
                pageEngine.doAction(pageFilterRequest, pageFilterResponse);
                return;
            }
            else if (requestType == RequestType.Resource)
            {
                pageEngine.doResource(pageFilterRequest, pageFilterResponse);
                return;
            }
        }
        catch (PageEngineException e)
        {
            LOGGER.error("Page loading error {}.", e);
            response.sendError(500);
        }
        
        filterChain.doFilter(request, response);
    }
    
    @Override
    public void afterPropertiesSet()
        throws ServletException
    {
    }
    
    public void setSiteFactory(SiteFactory siteFactory)
    {
        this.siteFactory = siteFactory;
    }
    
    public void setPageEngine(PageEngine pageEngine)
    {
        this.pageEngine = pageEngine;
    }
}