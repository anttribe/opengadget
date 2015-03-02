/*
 * 文  件   名: PageFilterRequest.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.render;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.anttribe.opengadget.runtime.constants.Constants;
import org.anttribe.opengadget.runtime.constants.Keys;
import org.anttribe.opengadget.runtime.core.Page;
import org.anttribe.opengadget.runtime.core.Site;
import org.anttribe.opengadget.runtime.render.factory.SiteFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

/**
 * 封装请求
 * 
 * @author zhaoyong
 * @version 2015年1月26日
 */
public class PageFilterRequest extends HttpServletRequestWrapper
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PageFilterRequest.class);
    
    /**
     * urlPathHelper
     */
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    
    /**
     * 请求类型
     */
    private RequestType requestType;
    
    /**
     * 当前站点
     */
    private Site currentSite;
    
    /**
     * 当前访问页面
     */
    private Page currentPage;
    
    /**
     * siteFactory
     */
    private SiteFactory siteFactory;
    
    /**
     * 布局文件
     */
    private String layoutLocation;
    
    /**
     * @param request
     */
    public PageFilterRequest(HttpServletRequest request, SiteFactory siteFactory, String layoutLocation)
    {
        super(request);
        this.siteFactory = siteFactory;
        this.layoutLocation = layoutLocation;
        
        // 默认是render请求
        this.requestType = RequestType.Render;
        // 解析请求
        this.parseRequest();
    }
    
    /**
     * 解析请求
     */
    private void parseRequest()
    {
        // 处理当前访问的站点和页面
        this.parseCurrentSiteAndPage();
        
        // 处理请求参数
        this.parseRequestParam();
    }
    
    /**
     * 从请求中解析当前访问的站点和页面
     */
    private void parseCurrentSiteAndPage()
    {
        // 解析请求地址
        String lookupPath = urlPathHelper.getLookupPathForRequest(this);
        LOGGER.debug("While parsing request, siteAndPage: {}.", lookupPath);
        
        String currentSiteName = "";
        String currentPageName = "";
        // 得到请求的站点和页面
        if (!StringUtils.isEmpty(lookupPath) && lookupPath.startsWith("/"))
        {
            String[] subStrings = StringUtils.split(lookupPath, "\\/");
            if (!ArrayUtils.isEmpty(subStrings))
            {
                if (subStrings.length > 1)
                {
                    currentSiteName = subStrings[0];
                }
                currentPageName = StringUtils.substring(lookupPath, currentSiteName.length() + 1);
                if (!StringUtils.isEmpty(currentPageName) && currentPageName.indexOf(".") != -1)
                {
                    // 去掉后缀
                    currentPageName = StringUtils.substring(currentPageName, 0, currentPageName.indexOf("."));
                }
            }
        }
        LOGGER.debug("While parsing request, currentSiteName: {}, currentPageName: {}",
            currentSiteName,
            currentPageName);
        
        // 不输入站点，去默认站点和默认首页
        if (StringUtils.isEmpty(currentSiteName))
        {
            currentSite = siteFactory.getDefaultSite();
            if (null != currentSite)
            {
                currentPage = currentSite.getIndexPage();
            }
        }
        else
        {
            // 获取站点, 不存在的情况下，跳转默认站点
            currentSite = siteFactory.loopup(currentSiteName);
            if (null != currentSite)
            {
                currentPage = currentSite.loopupPage(currentPageName);
            }
        }
        this.setAttributes();
        
        LOGGER.debug("While parsing request, currentSite: {}, currentPage: {}.", currentSite, currentPage);
    }
    
    /**
     * 设置属性
     */
    private void setAttributes()
    {
        super.setAttribute(Keys.KEY_CONTEXTPATH, urlPathHelper.getContextPath(this));
        super.setAttribute(Keys.KEY_CURRENTSITE, currentSite);
        super.setAttribute(Keys.KEY_CURRENTPAGE, currentPage);
    }
    
    /**
     * 处理请求参数
     */
    @SuppressWarnings("unchecked")
    private void parseRequestParam()
    {
        // 参数处理
        String paramsString = super.getParameter(Constants.REQUEST_PARAM_KEY);
        if (!StringUtils.isEmpty(paramsString))
        {
            if (Base64.isBase64(paramsString))
            {
                // 参数解码
                paramsString = new String(Base64.decodeBase64(paramsString));
            }
            
            if (!StringUtils.isEmpty(paramsString))
            {
                String[] params = StringUtils.split(paramsString, "&");
                if (!ArrayUtils.isEmpty(params))
                {
                    for (String param : params)
                    {
                        String[] nameAndValue = StringUtils.split(param, "=");
                        if (ArrayUtils.isEmpty(nameAndValue))
                        {
                            continue;
                        }
                        
                        super.setAttribute(nameAndValue[0], null);
                        if (nameAndValue.length == 2 && !StringUtils.isEmpty(nameAndValue[1]))
                        {
                            super.setAttribute(nameAndValue[0], nameAndValue[1]);
                            
                            // 请求类型
                            if (nameAndValue[0].equals(RequestType.Action.getRequestParamName()))
                            {
                                this.requestType = RequestType.Action;
                            }
                            else if (nameAndValue[0].equals(RequestType.Resource.getRequestParamName()))
                            {
                                this.requestType = RequestType.Resource;
                            }
                        }
                    }
                }
            }
        }
        
        // 跳转参数
        Map<String, Object> redirectAttributes =
            (Map<String, Object>)super.getSession().getAttribute(Keys.SESSION_REDIRECTATTRIBUTES);
        super.getSession().removeAttribute(Keys.SESSION_REDIRECTATTRIBUTES);
        if (!MapUtils.isEmpty(redirectAttributes))
        {
            super.setAttribute(Keys.SESSION_REDIRECTATTRIBUTES, redirectAttributes);
        }
    }
    
    public RequestType getRequestType()
    {
        return requestType;
    }
    
    public Site getCurrentSite()
    {
        return currentSite;
    }
    
    public Page getCurrentPage()
    {
        return currentPage;
    }
    
    public String getLayoutLocation()
    {
        return layoutLocation;
    }
}