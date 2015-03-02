/*
 * 文  件   名: Site.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象站点实体
 * 
 * @author zhaoyong
 * @version 2015年1月26日
 */
public class Site implements Serializable, Cloneable
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1239503389638859278L;
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Site.class);
    
    /**
     * 站点名称
     */
    private String name;
    
    /**
     * 站点的描述信息
     */
    private String description;
    
    /**
     * 所有页面对象的集合
     */
    private Map<String, Page> pages;
    
    /**
     * 站点皮肤
     */
    private Theme theme;
    
    /**
     * Page集合对象的temp
     */
    private List<Page> tempPages;
    
    /**
     * 默认构造器
     */
    public Site()
    {
        pages = new HashMap<String, Page>();
    }
    
    /**
     * 增加页面
     * 
     * @param page Page
     */
    public void addPage(Page page)
    {
        if (StringUtils.isEmpty(page.getName()))
        {
            LOGGER.warn("While adding page to site {}, the page name is null.", this.getName());
            return;
        }
        this.pages.put(page.getName(), page);
    }
    
    /**
     * 获取首页， 即页面权重最大的Page
     * 
     * @return Page
     */
    public Page getIndexPage()
    {
        if (!MapUtils.isEmpty(pages))
        {
            if (CollectionUtils.isEmpty(tempPages))
            {
                tempPages = new ArrayList<Page>();
                tempPages.addAll(pages.values());
                Collections.sort(tempPages, new Comparator<Page>()
                {
                    @Override
                    public int compare(Page o1, Page o2)
                    {
                        return o2.getWeight() - o1.getWeight();
                    }
                });
            }
            return tempPages.get(0);
        }
        return null;
    }
    
    /**
     * 根据页面名获取唯一的Page
     * 
     * @param pageName String
     * @return Page
     */
    public Page loopupPage(String pageName)
    {
        if (StringUtils.isEmpty(pageName))
        {
            return this.getIndexPage();
        }
        if (!MapUtils.isEmpty(pages))
        {
            String tempPageName = pageName;
            if (StringUtils.contains(pageName, "/"))
            {
                tempPageName = StringUtils.split(pageName, "/")[0];
            }
            Page page = pages.get(tempPageName);
            if (null != page)
            {
                Page childPage = page.lookupChildPage(pageName.substring(tempPageName.length() + 1));
                if (null != childPage)
                {
                    return childPage;
                }
                
                page = Page.getIndexPageFromPage(page);
            }
            return page;
        }
        return null;
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
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public Map<String, Page> getPages()
    {
        return pages;
    }
    
    public void setPages(Map<String, Page> pages)
    {
        this.pages = pages;
    }
    
    public Theme getTheme()
    {
        return theme;
    }
    
    public void setTheme(Theme theme)
    {
        this.theme = theme;
    }
}