/*
 * 文  件   名: PageEngineException.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.render.engine;

/**
 * @author zhaoyong
 * @version 2014年8月31日
 */
public class PageEngineException extends RuntimeException
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5880627517948355223L;
    
    /**
     * @param message
     */
    public PageEngineException(String message)
    {
        super(message);
    }
    
    /**
     * @param cause
     */
    public PageEngineException(Throwable cause)
    {
        super(cause);
    }
    
    /**
     * @param message
     * @param cause
     */
    public PageEngineException(String message, Throwable cause)
    {
        super(message, cause);
    }
}