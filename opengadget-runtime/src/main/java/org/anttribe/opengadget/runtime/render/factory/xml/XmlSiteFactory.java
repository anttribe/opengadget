/*
 * 文  件   名: XmlSiteFactory.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月28日
 */
package org.anttribe.opengadget.runtime.render.factory.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.anttribe.opengadget.runtime.core.Site;
import org.anttribe.opengadget.runtime.render.factory.AbstractSiteFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 * XML实现的站点工厂类
 * 
 * @author zhaoyong
 * @version 2015年1月28日
 */
public class XmlSiteFactory extends AbstractSiteFactory implements ResourceLoaderAware
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlSiteFactory.class);
    
    /**
     * 资源加载器
     */
    protected ResourceLoader resourceLoader;
    
    /**
     * 站点配置文件地址
     */
    private String[] siteConfigLocations;
    
    /**
     * dataParser
     */
    private DataParser dataParser = DataParser.getDataParser(Parser.XML);
    
    @SuppressWarnings("unchecked")
    @Override
    protected void loadSiteConfigs()
    {
        List<Site> sites = new ArrayList<Site>();
        
        String[] configLocations = this.siteConfigLocations;
        if (null != configLocations)
        {
            // resolver
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
            for (String configLocation : configLocations)
            {
                try
                {
                    Resource[] resources = resolver.getResources(configLocation);
                    for (Resource resource : resources)
                    {
                        List<Site> tempSiteList =
                            (List<Site>)dataParser.parseToObject(resource.getInputStream(), Site.class);
                        if (null != tempSiteList)
                        {
                            sites.addAll(tempSiteList);
                        }
                    }
                }
                catch (IOException e)
                {
                    LOGGER.error("Can not load Site from location: {}, cause: {}", configLocation, e);
                    continue;
                }
            }
        }
        
        if (!CollectionUtils.isEmpty(sites))
        {
            for (Site site : sites)
            {
                if (StringUtils.isEmpty(site.getName()))
                {
                    LOGGER.warn("While loading site from XML definition, site name is null. This site will be the default.");
                    site.setName(DERFAULT_SITE_NAME);
                }
                this.cache.put(site.getName(), site);
            }
        }
    }
    
    public void setSiteConfigLocations(String[] siteConfigLocations)
    {
        this.siteConfigLocations = siteConfigLocations;
    }
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
}