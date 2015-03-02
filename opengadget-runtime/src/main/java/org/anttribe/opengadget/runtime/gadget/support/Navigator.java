/*
 * 文  件   名: Navigator.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月4日
 */
package org.anttribe.opengadget.runtime.gadget.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 导向处理
 * 
 * @author zhaoyong
 * @version 2015年2月4日
 */
public class Navigator
{
    /**
     * navigatorLocal
     */
    private static ThreadLocal<Navigator> navigatorLocal = new ThreadLocal<Navigator>()
    {
        protected Navigator initialValue()
        {
            return new Navigator();
        }
    };
    
    /**
     * 导向到的页面
     */
    private String navigateTo;
    
    /**
     * attributes
     */
    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    /**
     * <默认构造器>
     */
    private Navigator()
    {
    }
    
    /**
     * @return
     */
    public static Navigator get()
    {
        return navigatorLocal.get();
    }
    
    /**
     * 重置
     * 
     * @return Navigator
     */
    public static Navigator reset()
    {
        navigatorLocal.remove();
        return navigatorLocal.get();
    }
    
    /**
     * 导向带参数
     * 
     * @param name 参数名
     * @param value 参数值
     */
    public Navigator withAttribute(String name, Object value)
    {
        attributes.put(name, value);
        return this;
    }
    
    /**
     * 导向到指定页面
     * 
     * @param navigateTo String
     */
    public void navigateTo(String navigateTo)
    {
        this.navigateTo = navigateTo;
    }
    
    public String getNavigateTo()
    {
        return navigateTo;
    }
    
    public Map<String, Object> getAttributes()
    {
        return attributes;
    }
}