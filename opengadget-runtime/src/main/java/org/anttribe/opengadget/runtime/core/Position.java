/*
 * 文  件   名: Position.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象位置实体对象，代表在一个页面中的位置
 * 
 * @author zhaoyong
 * @version 2015年1月26日
 */
public class Position implements Serializable, Cloneable
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3831253785437274181L;
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Position.class);
    
    /**
     * 同一个页面内唯一
     */
    private String name;
    
    /**
     * gadgets, Gadget集合数据
     */
    private Map<String, Gadget> gadgets;
    
    /**
     * <默认构造器>
     */
    public Position()
    {
        gadgets = new HashMap<String, Gadget>();
    }
    
    /**
     * 添加Gadget
     * 
     * @param gadget Gadget
     */
    public void addGadget(Gadget gadget)
    {
        if (StringUtils.isEmpty(gadget.getGadgetId()))
        {
            LOGGER.warn("While adding gadget to position {}, the gadget id is null.", this.getName());
            return;
        }
        
        this.gadgets.put(gadget.getGadgetId(), gadget);
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Map<String, Gadget> getGadgets()
    {
        return gadgets;
    }
    
    public void setGadgets(Map<String, Gadget> gadgets)
    {
        this.gadgets = gadgets;
    }
}