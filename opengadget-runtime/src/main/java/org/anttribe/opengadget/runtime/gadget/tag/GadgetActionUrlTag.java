/*
 * 文  件   名: GadgetActionUrlTag.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月4日
 */
package org.anttribe.opengadget.runtime.gadget.tag;

import org.anttribe.opengadget.runtime.render.RequestType;

/**
 * Gadget中动作处理url
 * 
 * @author zhaoyong
 * @version 2015年2月4日
 */
public class GadgetActionUrlTag extends AbstractUrlTag
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -3253371242363303775L;
    
    @Override
    public String getParamName()
    {
        return RequestType.Action.getRequestParamName();
    }
}