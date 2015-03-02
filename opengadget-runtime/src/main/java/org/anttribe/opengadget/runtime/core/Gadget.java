/*
 * 文  件   名: Gadget.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.core;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 抽象每一个小组件，页面的最小组成单元，即页面中最小的、独立逻辑单元的小块内容
 * 
 * @author zhaoyong
 * @version 2015年1月26日
 */
public class Gadget implements Serializable, Cloneable
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2758008094683698244L;
    
    /**
     * 标志，唯一标志一个gadget
     */
    private String gadgetId;
    
    /**
     * 标题，在进行配置站点时，用于显示gadget
     */
    private String title;
    
    /**
     * 实际处理的请求或操作
     */
    private String action;
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
    public String getGadgetId()
    {
        return gadgetId;
    }
    
    public void setGadgetId(String gadgetId)
    {
        this.gadgetId = gadgetId;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getAction()
    {
        return action;
    }
    
    public void setAction(String action)
    {
        this.action = action;
    }
}