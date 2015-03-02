/*
 * 文  件   名: RequestType.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月28日
 */
package org.anttribe.opengadget.runtime.render;

/**
 * 请求类型
 * 
 * @author zhaoyong
 * @version 2015年1月28日
 */
public enum RequestType
{
    Render(".r"), Action(".a"), Resource(".rs");
    
    /**
     * 请求参数名
     */
    private String requestParamName;
    
    /**
     * <默认构造器>
     */
    private RequestType()
    {
    }
    
    /**
     * <默认构造器>
     * 
     * @param requestParamName
     */
    private RequestType(String requestParamName)
    {
        this.requestParamName = requestParamName;
    }
    
    public String getRequestParamName()
    {
        return requestParamName;
    }
}