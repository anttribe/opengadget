/*
 * 文  件   名: ApplicationContextLoaderListener.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月3日
 */
package org.anttribe.opengadget.runtime.listener;

import javax.servlet.ServletContextEvent;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.ioc.spring.factory.SpringInstanceProvider;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author zhaoyong
 * @version 2015年2月3日
 */
public class ApplicationContextLoaderListener extends ContextLoaderListener
{
    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        super.contextInitialized(event);
        WebApplicationContext applicationContext =
            WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        SpringInstanceProvider springProvider = new SpringInstanceProvider(applicationContext);
        InstanceFactory.setInstanceProvider(springProvider);
    }
}