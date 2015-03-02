/*
 * 文  件   名: Theme.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.core;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 抽象皮肤实体数据
 * 
 * @author zhaoyong
 * @version 2015年1月26日
 */
public class Theme
{
    /**
     * 主题名称
     */
    private String name;
    
    /**
     * 标题(显示)
     */
    private String title;
    
    /**
     * 主题路径
     */
    private String themePath;
    
    /**
     * 预览图片
     */
    private String preview;
    
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
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getThemePath()
    {
        return themePath;
    }
    
    public void setThemePath(String themePath)
    {
        this.themePath = themePath;
    }
    
    public String getPreview()
    {
        return preview;
    }
    
    public void setPreview(String preview)
    {
        this.preview = preview;
    }
}