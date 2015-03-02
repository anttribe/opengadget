/*
 * 文  件   名: GadgetRequest.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月28日
 */
package org.anttribe.opengadget.runtime.render.engine.gadget;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.anttribe.opengadget.runtime.constants.Keys;
import org.anttribe.opengadget.runtime.render.RequestType;
import org.apache.commons.collections.MapUtils;

/**
 * 封装单个Gadget请求
 * 
 * @author zhaoyong
 * @version 2015年1月30日
 */
public class GadgetRequest extends HttpServletRequestWrapper
{
    /**
     * requestType
     */
    private RequestType requestType;
    
    /**
     * attributes
     */
    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    /**
     * <构造器>
     * 
     * @param request
     */
    public GadgetRequest(HttpServletRequest request, RequestType requestType)
    {
        super(request);
        this.requestType = requestType;
        
        this.populateAttributes(request);
    }
    
    /**
     * 构造请求参数
     * 
     * @param request
     */
    @SuppressWarnings("unchecked")
    private void populateAttributes(HttpServletRequest request)
    {
        Map<String, Object> redirectAttributes =
            (Map<String, Object>)request.getAttribute(Keys.SESSION_REDIRECTATTRIBUTES);
        if (!MapUtils.isEmpty(redirectAttributes))
        {
            this.setAttributes(redirectAttributes);
        }
    }
    
    /**
     * 批量设置参数
     * 
     * @param attributes
     */
    private void setAttributes(Map<String, Object> attributes)
    {
        this.attributes = attributes;
    }
    
    @Override
    public Object getAttribute(String name)
    {
        Object value = super.getAttribute(name);
        if (null == value && !MapUtils.isEmpty(this.attributes))
        {
            value = this.attributes.get(name);
        }
        return value;
    }
    
    public RequestType getRequestType()
    {
        return requestType;
    }
}