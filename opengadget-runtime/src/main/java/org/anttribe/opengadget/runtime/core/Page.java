/*
 * 文  件   名: Page.java
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象页面实体对象
 * 
 * @author zhaoyong
 * @version 2015年1月26日
 */
public class Page implements Serializable, Cloneable
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6966510259097932600L;
    
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Page.class);
    
    /**
     * 页面名称, 在使用者层面标志一个Page
     */
    private String name;
    
    /**
     * 标题, 作为菜单处理时的展现标题
     */
    private String title;
    
    /**
     * icon对应的class
     */
    private String iconClass;
    
    /**
     * 布局
     */
    private String layout;
    
    /**
     * 所有子页面对象的集合
     */
    private Map<String, Page> childPages;
    
    /**
     * 父页面
     */
    private Page parent;
    
    /**
     * 权重, 权重越小，页面靠前
     */
    private int weight;
    
    /**
     * 页面位置数据集合
     */
    private Map<String, Position> positions;
    
    /**
     * Page集合对象的temp
     */
    private List<Page> tempPages;
    
    /**
     * <默认构造器>
     */
    public Page()
    {
        childPages = new HashMap<String, Page>();
        positions = new HashMap<String, Position>();
    }
    
    /**
     * 增加子页面
     * 
     * @param page
     */
    public void addChildPage(Page page)
    {
        if (StringUtils.isEmpty(page.getName()))
        {
            LOGGER.warn("While adding child page to page {}, the page name is null.", this.getName());
            return;
        }
        
        page.setParent(this);
        if (page.getWeight() == 0)
        {
            page.setWeight(childPages.size());
        }
        this.childPages.put(page.getName(), page);
        
        List<Map.Entry<String, Page>> temp = new ArrayList<Map.Entry<String, Page>>(childPages.entrySet());
        Collections.sort(temp, new Comparator<Map.Entry<String, Page>>()
        {
            @Override
            public int compare(Entry<String, Page> o1, Entry<String, Page> o2)
            {
                return o1.getValue().getWeight() - o2.getValue().getWeight();
            }
        });
        this.childPages = new LinkedHashMap<String, Page>();
        for (Map.Entry<String, Page> entry : temp)
        {
            this.childPages.put(entry.getKey(), entry.getValue());
        }
    }
    
    /**
     * 添加位置
     * 
     * @param position
     */
    public void addPosition(Position position)
    {
        if (StringUtils.isEmpty(position.getName()))
        {
            LOGGER.warn("While adding position to page {}, the position name is null.", this.getName());
            return;
        }
        
        this.positions.put(position.getName(), position);
    }
    
    /**
     * 查找子页面
     * 
     * @param pageName 页面名称
     * @return Page
     */
    public Page lookupChildPage(String pageName)
    {
        if (!StringUtils.isEmpty(pageName) && !MapUtils.isEmpty(childPages))
        {
            String tempPageName = pageName;
            if (StringUtils.contains(pageName, "/"))
            {
                tempPageName = StringUtils.split(pageName, "/")[0];
            }
            Page page = childPages.get(tempPageName);
            if (null != page && !pageName.equals(tempPageName))
            {
                Page childPage = page.lookupChildPage(pageName.substring(tempPageName.length() + 1));
                if (null != childPage)
                {
                    return childPage;
                }
                
                page = getIndexPageFromPage(page);
            }
            return page;
        }
        
        return null;
    }
    
    /**
     * 获取页面下的首页
     * 
     * @param page
     * @return
     */
    public static Page getIndexPageFromPage(Page page)
    {
        if (MapUtils.isEmpty(page.getPositions()))
        {
            page = page.getIndexPage();
            if (null != page)
            {
                page = getIndexPageFromPage(page);
            }
        }
        return page;
    }
    
    /**
     * 获取首页， 即页面权重最大的Page
     * 
     * @return
     */
    public Page getIndexPage()
    {
        if (!MapUtils.isEmpty(this.childPages))
        {
            if (CollectionUtils.isEmpty(tempPages))
            {
                tempPages = new ArrayList<Page>();
                tempPages.addAll(childPages.values());
                Collections.sort(tempPages, new Comparator<Page>()
                {
                    @Override
                    public int compare(Page o1, Page o2)
                    {
                        return o1.getWeight() - o2.getWeight();
                    }
                });
            }
            return tempPages.get(0);
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
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getIconClass()
    {
        return iconClass;
    }
    
    public void setIconClass(String iconClass)
    {
        this.iconClass = iconClass;
    }
    
    public String getLayout()
    {
        return layout;
    }
    
    public void setLayout(String layout)
    {
        this.layout = layout;
    }
    
    public Map<String, Page> getChildPages()
    {
        return childPages;
    }
    
    public void setChildPages(Map<String, Page> childPages)
    {
        this.childPages = childPages;
    }
    
    public Page getParent()
    {
        return parent;
    }
    
    public void setParent(Page parent)
    {
        this.parent = parent;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public void setWeight(int weight)
    {
        this.weight = weight;
    }
    
    public Map<String, Position> getPositions()
    {
        return positions;
    }
    
    public void setPositions(Map<String, Position> positions)
    {
        this.positions = positions;
    }
    
    public List<Page> getTempPages()
    {
        return tempPages;
    }
}