/*
 * 文  件   名: GadgetRenderUrlTag.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月4日
 */
package org.anttribe.opengadget.runtime.gadget.tag;

import org.anttribe.opengadget.runtime.render.RequestType;

/**
 * Gadget的render请求
 * 
 * @author zhaoyong
 * @version 2015年2月4日
 */
public class GadgetRenderUrlTag extends AbstractUrlTag
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 9122583592580444557L;
    
    @Override
    public String getParamName()
    {
        return RequestType.Render.getRequestParamName();
    }
    
}