/*
 * 文  件   名: SiteFactory.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.render.factory;

import org.anttribe.opengadget.runtime.core.Site;

/**
 * 站点工厂类
 * 
 * @author zhaoyong
 * @version 2015年1月26日
 */
public interface SiteFactory
{
    /**
     * 默认站点的名称
     */
    String DERFAULT_SITE_NAME = "defaultSite";
    
    /**
     * 根据siteName获取站点对象
     * 
     * @param siteName String
     * @return Site
     */
    Site loopup(String siteName);
    
    /**
     * 获取默认站点
     * 
     * @return Site
     */
    Site getDefaultSite();
}