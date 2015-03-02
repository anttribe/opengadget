/*
 * 文  件   名: GadgetResourceUrlTag.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月4日
 */
package org.anttribe.opengadget.runtime.gadget.tag;

import org.anttribe.opengadget.runtime.render.RequestType;

/**
 * Gadget中Resource资源类型处理url
 * 
 * @author zhaoyong
 * @version 2015年2月4日
 */
public class GadgetResourceUrlTag extends AbstractUrlTag
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4923591945107230909L;
    
    @Override
    public String getParamName()
    {
        return RequestType.Resource.getRequestParamName();
    }
    
}