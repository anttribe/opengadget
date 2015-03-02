/*
 * 文  件   名: GadgetDispatcherServlet.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月4日
 */
package org.anttribe.opengadget.runtime.gadget;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.anttribe.opengadget.runtime.gadget.support.Navigator;
import org.anttribe.opengadget.runtime.render.RequestType;
import org.anttribe.opengadget.runtime.render.engine.gadget.GadgetRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhaoyong
 * @version 2015年2月4日
 */
public class GadgetDispatcherServlet extends DispatcherServlet
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 9162966379653654352L;
    
    /**
     * DEFAULT_CONTEXT_CONFIG_LOCATION
     */
    private static final String DEFAULT_CONTEXT_CONFIG_LOCATION = "dispatcherContext-gadget.xml";
    
    @Override
    public String getContextConfigLocation()
    {
        String contextConfigLocation = super.getContextConfigLocation();
        if (StringUtils.isEmpty(contextConfigLocation))
        {
            contextConfigLocation = DEFAULT_CONTEXT_CONFIG_LOCATION;
        }
        return contextConfigLocation;
    }
    
    @Override
    protected void render(ModelAndView mv, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        GadgetRequest gadgetRequest = (GadgetRequest)request;
        if (RequestType.Action == gadgetRequest.getRequestType())
        {
            Navigator navigator = Navigator.get();
            navigator.navigateTo(mv.getViewName());
            // 参数
            Map<String, Object> modelMap = mv.getModel();
            for (Map.Entry<String, Object> entry : modelMap.entrySet())
            {
                navigator = navigator.withAttribute(entry.getKey(), entry.getValue());
            }
            return;
        }
        super.render(mv, request, response);
    }
}