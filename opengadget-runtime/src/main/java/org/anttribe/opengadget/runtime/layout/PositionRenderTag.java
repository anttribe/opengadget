/*
 * 文  件   名: PositionRenderTag.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.layout;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.anttribe.opengadget.runtime.core.Gadget;
import org.anttribe.opengadget.runtime.core.Position;
import org.anttribe.opengadget.runtime.render.engine.gadget.GadgetResponse;
import org.apache.commons.collections.MapUtils;

/**
 * Position渲染标签处理
 * 
 * @author zhaoyong
 * @version 2015年1月30日
 */
public class PositionRenderTag extends BodyTagSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 731304808251971208L;
    
    @SuppressWarnings("unchecked")
    @Override
    public int doEndTag()
        throws JspException
    {
        PositionTag positionTag = (PositionTag)this.getParent();
        if (null == positionTag || null == positionTag.getPosition())
        {
            return SKIP_PAGE;
        }
        
        Position position = positionTag.getPosition();
        Map<String, Gadget> gadgets = position.getGadgets();
        if (MapUtils.isEmpty(gadgets))
        {
            return SKIP_PAGE;
        }
        
        // 从响应中获取每一个Gadget的响应
        ServletRequest request = this.pageContext.getRequest();
        Map<String, GadgetResponse> gadgetResponseMap =
            (Map<String, GadgetResponse>)request.getAttribute("gadgetResponseMap");
        if (MapUtils.isEmpty(gadgetResponseMap))
        {
            return SKIP_PAGE;
        }
        
        try
        {
            JspWriter out = this.pageContext.getOut();
            // 遍历position中的每一个gagdet，输出响应
            for (Map.Entry<String, Gadget> entry : gadgets.entrySet())
            {
                Gadget gadget = entry.getValue();
                if (null == gadget)
                {
                    continue;
                }
                
                GadgetResponse gadgetResponse = gadgetResponseMap.get(gadget.getGadgetId());
                if (null == gadgetResponse)
                {
                    continue;
                }
                
                out.write(gadgetResponse.toString());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return super.doEndTag();
    }
    
    @Override
    public int doAfterBody()
        throws JspException
    {
        return super.doAfterBody();
    }
}