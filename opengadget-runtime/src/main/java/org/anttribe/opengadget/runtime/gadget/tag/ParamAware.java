/*
 * 文  件   名: ParamAware.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月9日
 */
package org.anttribe.opengadget.runtime.gadget.tag;

/**
 * @author zhaoyong
 * @version 2015年2月9日
 */
public interface ParamAware
{
    /**
     * 添加参数
     * 
     * @param name
     * @param value
     */
    void addParamAttribute(String name, String value);
}