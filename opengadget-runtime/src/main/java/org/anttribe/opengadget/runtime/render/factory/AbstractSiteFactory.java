/*
 * 文  件   名: AbstractSiteFactory.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月28日
 */
package org.anttribe.opengadget.runtime.render.factory;

import java.util.HashMap;
import java.util.Map;

import org.anttribe.opengadget.runtime.core.Site;
import org.apache.commons.collections.MapUtils;

/**
 * 站点工厂的抽象实现
 * 
 * @author zhaoyong
 * @version 2015年1月28日
 */
public abstract class AbstractSiteFactory implements SiteFactory
{
    /**
     * Site配置缓存
     */
    protected Map<String, Site> cache = new HashMap<String, Site>();
    
    @Override
    public Site loopup(String siteName)
    {
        if (MapUtils.isEmpty(cache))
        {
            this.loadSiteConfigs();
        }
        return cache.get(siteName);
    }
    
    @Override
    public Site getDefaultSite()
    {
        if (MapUtils.isEmpty(cache))
        {
            this.loadSiteConfigs();
        }
        return cache.get(DERFAULT_SITE_NAME);
    }
    
    /**
     * 加载所有的配置站点
     */
    protected abstract void loadSiteConfigs();
}