/*
 * 文  件   名: DefaultThemeFactory.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月30日
 */
package org.anttribe.opengadget.runtime.render.theme;

import java.util.ArrayList;
import java.util.List;

import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.anttribe.opengadget.runtime.core.Theme;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

/**
 * ThemeFactory的默认实现
 * 
 * @author zhaoyong
 * @version 2015年1月30日
 */
public class DefaultThemeFactory extends DefaultResourceLoader implements ThemeFactory, ResourceLoaderAware
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultThemeFactory.class);
    
    /**
     * 默认主题配置文件的后缀
     */
    private static final String DEFAULT_THEME_CONFIGFILE_SUBFIX = ".properties";
    
    /**
     * DEFAULT_THEME_NAME
     */
    private static final String DEFAULT_THEME_NAME = "defaut";
    
    /**
     * resourceLoader
     */
    private ResourceLoader resourceLoader;
    
    /**
     * 皮肤目录
     */
    private String themeDirectory;
    
    /**
     * 默认站点名
     */
    private String defaultThemeName = DEFAULT_THEME_NAME;
    
    /**
     * 默认皮肤
     */
    private Theme defaultTheme;
    
    /**
     * 所有的theme集合
     */
    private List<Theme> themes = new ArrayList<Theme>();
    
    /**
     * dataParser
     */
    private DataParser dataParser = DataParser.getDataParser(Parser.Properties);
    
    @Override
    public Theme getDefaultTheme()
    {
        if (null != defaultTheme)
        {
            return defaultTheme;
        }
        
        if (StringUtils.isEmpty(defaultThemeName))
        {
            LOGGER.warn("While getting the default theme, the defaultThemeName is null.");
            return null;
        }
        
        defaultTheme = this.getThemeByName(defaultThemeName);
        if (null == defaultTheme)
        {
            LOGGER.warn("While getting the default theme, the defaultTheme is null.");
        }
        
        return defaultTheme;
    }
    
    @Override
    public Theme getThemeByName(String themeName)
    {
        if (StringUtils.isEmpty(themeName))
        {
            return this.getDefaultTheme();
        }
        
        if (CollectionUtils.isEmpty(themes))
        {
            this.loadThemeConfigs();
        }
        if (!CollectionUtils.isEmpty(themes))
        {
            for (Theme theme : themes)
            {
                if (themeName.equals(theme.getName()))
                {
                    return theme;
                }
            }
        }
        return null;
    }
    
    @Override
    public List<Theme> getAllThemes()
    {
        if (CollectionUtils.isEmpty(themes))
        {
            this.loadThemeConfigs();
        }
        return themes;
    }
    
    /**
     * 加载皮肤的配置信息
     */
    private void loadThemeConfigs()
    {
        if (StringUtils.isEmpty(this.themeDirectory))
        {
            LOGGER.warn("While loading themes, the themeDirectory is null.");
            return;
        }
        
        try
        {
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
            // theme配置文件路径
            String themesLocation =
                new StringBuffer(this.themeDirectory).append("/**")
                    .append("/")
                    .append("*-theme")
                    .append(DEFAULT_THEME_CONFIGFILE_SUBFIX)
                    .toString();
            Resource[] resources = resolver.getResources(themesLocation);
            for (Resource resource : resources)
            {
                Theme theme = (Theme)dataParser.parseToObject(resource.getInputStream(), Theme.class);
                if (null != theme && !StringUtils.isEmpty(theme.getName()))
                {
                    themes.add(theme);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error("Failed to load themes from location {0}, cause: {1}", themeDirectory, e);
        }
    }
    
    public void setDefaultThemeName(String defaultThemeName)
    {
        this.defaultThemeName = defaultThemeName;
    }
    
    public void setThemeDirectory(String themeDirectory)
    {
        if (StringUtils.isEmpty(themeDirectory))
        {
            LOGGER.warn("While setting themeDirectory, the param is null.");
            return;
        }
        
        if (!StringUtils.startsWith(themeDirectory, "/"))
        {
            themeDirectory = "/" + themeDirectory;
        }
        this.themeDirectory = themeDirectory;
    }
    
    @Override
    public String getThemeDirectory()
    {
        return this.themeDirectory;
    }
    
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;
    }
}