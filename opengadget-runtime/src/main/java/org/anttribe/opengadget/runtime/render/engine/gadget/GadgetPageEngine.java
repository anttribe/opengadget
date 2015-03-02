/*
 * 文  件   名: GadgetPageEngine.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月28日
 */
package org.anttribe.opengadget.runtime.render.engine.gadget;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.opengadget.runtime.constants.Keys;
import org.anttribe.opengadget.runtime.core.Gadget;
import org.anttribe.opengadget.runtime.core.Page;
import org.anttribe.opengadget.runtime.core.Position;
import org.anttribe.opengadget.runtime.core.Site;
import org.anttribe.opengadget.runtime.gadget.support.Navigator;
import org.anttribe.opengadget.runtime.render.PageFilterRequest;
import org.anttribe.opengadget.runtime.render.RequestType;
import org.anttribe.opengadget.runtime.render.engine.PageEngine;
import org.anttribe.opengadget.runtime.render.engine.PageEngineException;
import org.anttribe.opengadget.runtime.render.theme.ThemeFactory;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationObjectSupport;

/**
 * @author zhaoyong
 * @version 2015年1月28日
 */
public class GadgetPageEngine extends WebApplicationObjectSupport implements PageEngine
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GadgetPageEngine.class);
    
    /**
     * themeFactory
     */
    private ThemeFactory themeFactory;
    
    @Override
    public void doRender(HttpServletRequest request, HttpServletResponse response)
        throws PageEngineException
    {
        PageFilterRequest pageFilterRequest = (PageFilterRequest)request;
        Site site = pageFilterRequest.getCurrentSite();
        // 设置默认皮肤
        if (null == site.getTheme())
        {
            site.setTheme(themeFactory.getDefaultTheme());
            request.setAttribute(Keys.KEY_CURRENTSITE, site);
        }
        
        // 页面中Gadget请求和响应集合
        Map<String, GadgetRequest> gadgetRequestMap = new HashMap<String, GadgetRequest>();
        Map<String, GadgetResponse> gadgetResponseMap = new HashMap<String, GadgetResponse>();
        
        // 从请求中获取Page
        Page page = pageFilterRequest.getCurrentPage();
        Map<String, Position> positions = page.getPositions();
        if (!MapUtils.isEmpty(positions))
        {
            for (Map.Entry<String, Position> entry : positions.entrySet())
            {
                Position position = entry.getValue();
                if (null == position)
                {
                    continue;
                }
                // 遍历每一个位置上的gadget，并处理gadget请求
                Map<String, Gadget> gadgets = position.getGadgets();
                if (!MapUtils.isEmpty(gadgets))
                {
                    ServletContext context = this.getServletContext();
                    for (Map.Entry<String, Gadget> gadgetEntry : gadgets.entrySet())
                    {
                        Gadget gadget = gadgetEntry.getValue();
                        if (null == gadget)
                        {
                            continue;
                        }
                        
                        // 处理widget请求
                        GadgetRequest gadgetRequest = new GadgetRequest(request, RequestType.Render);
                        GadgetResponse gadgetResponse = new GadgetResponse(response);
                        String gadgetPath = "/gadget" + gadget.getAction();
                        try
                        {
                            context.getRequestDispatcher(gadgetPath).include(gadgetRequest, gadgetResponse);
                            
                            gadgetRequestMap.put(gadget.getGadgetId(), gadgetRequest);
                            gadgetResponseMap.put(gadget.getGadgetId(), gadgetResponse);
                        }
                        catch (Exception e)
                        {
                            LOGGER.error("", e);
                            throw new PageEngineException(e);
                        }
                    }
                }
            }
        }
        
        request.setAttribute("gadgetRequestMap", gadgetRequestMap);
        request.setAttribute("gadgetResponseMap", gadgetResponseMap);
        
        // 如果已经提交了响应
        if (response.isCommitted())
        {
            LOGGER.warn("Response has bean commited to client.");
            return;
        }
        
        this.dispatchToPage(request, response);
    }
    
    /**
     * 请求分派
     * 
     * @param request
     * @param response
     */
    private void dispatchToPage(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            PageFilterRequest pageFilterRequest = (PageFilterRequest)request;
            Site site = pageFilterRequest.getCurrentSite();
            Page page = pageFilterRequest.getCurrentPage();
            // 页面布局文件
            StringBuilder layout =
                new StringBuilder("").append(this.themeFactory.getThemeDirectory())
                    .append('/')
                    .append(site.getTheme().getName())
                    .append('/')
                    .append(!StringUtils.isEmpty(page.getLayout()) ? page.getLayout()
                        : pageFilterRequest.getLayoutLocation());
            request.getRequestDispatcher(layout.toString()).forward(request, response);
        }
        catch (Exception e)
        {
            throw new PageEngineException(e);
        }
    }
    
    @Override
    public void doAction(HttpServletRequest request, HttpServletResponse response)
        throws PageEngineException
    {
        // Gadget的请求和响应
        GadgetRequest gadgetRequest = new GadgetRequest(request, RequestType.Action);
        GadgetResponse gadgetResponse = new GadgetResponse(response);
        
        ServletContext context = this.getServletContext();
        // 获取action参数
        String gadgetActionPath = "/gadget" + (String)request.getAttribute(RequestType.Action.getRequestParamName());
        try
        {
            Navigator navigator = Navigator.reset();
            context.getRequestDispatcher(gadgetActionPath).forward(gadgetRequest, gadgetResponse);
            
            // 处理导向
            String navigatorTo = navigator.getNavigateTo();
            if (StringUtils.isEmpty(navigatorTo))
            {
                gadgetResponse.flushBuffer();
                return;
            }
            
            if (navigatorTo.indexOf("/") != 0)
            {
                navigatorTo = "/" + navigatorTo;
            }
            
            // 获取当前Site
            Site site = (Site)request.getAttribute(Keys.KEY_CURRENTSITE);
            if (null != site)
            {
                navigatorTo = "/" + site.getName() + navigatorTo;
            }
            
            request.getSession().setAttribute(Keys.SESSION_REDIRECTATTRIBUTES, navigator.getAttributes());
            // 重定向
            StringBuffer redirectUrl = new StringBuffer(request.getContextPath()).append(navigatorTo);
            response.sendRedirect(redirectUrl.toString());
        }
        catch (Exception e)
        {
            LOGGER.error("", e);
            throw new PageEngineException(e);
        }
        finally
        {
            Navigator.reset();
        }
    }
    
    @Override
    public void doResource(HttpServletRequest request, HttpServletResponse response)
    {
    }
    
    public void setThemeFactory(ThemeFactory themeFactory)
    {
        this.themeFactory = themeFactory;
    }
    
}