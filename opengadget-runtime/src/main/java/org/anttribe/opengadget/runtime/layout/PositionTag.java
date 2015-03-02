/*
 * 文  件   名: PositionTag.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月26日
 */
package org.anttribe.opengadget.runtime.layout;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.anttribe.opengadget.runtime.constants.Keys;
import org.anttribe.opengadget.runtime.core.Page;
import org.anttribe.opengadget.runtime.core.Position;
import org.apache.commons.collections.MapUtils;

/**
 * Layout布局 页面针对模块位置的处理标签
 * 
 * @author zhaoyong
 * @version 2015年1月30日
 */
public class PositionTag extends TagSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -5433925922010335010L;
    
    /**
     * 指向PositionConfig的name
     */
    private String name;
    
    /**
     * position
     */
    private Position position;
    
    @Override
    public int doStartTag()
        throws JspException
    {
        ServletRequest request = this.pageContext.getRequest();
        Page page = (Page)request.getAttribute(Keys.KEY_CURRENTPAGE);
        if (null == page)
        {
            return Tag.SKIP_BODY;
        }
        
        Map<String, Position> positions = page.getPositions();
        if (MapUtils.isEmpty(positions))
        {
            return Tag.SKIP_BODY;
        }
        
        this.position = positions.get(this.name);
        if (null == this.position)
        {
            return Tag.SKIP_BODY;
        }
        
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    @Override
    public int doEndTag()
        throws JspException
    {
        return super.doEndTag();
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Position getPosition()
    {
        return position;
    }
    
    public void setPosition(Position position)
    {
        this.position = position;
    }
    
    public String getName()
    {
        return name;
    }
}