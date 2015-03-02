/*
 * 文  件   名: ParamTag.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月4日
 */
package org.anttribe.opengadget.runtime.gadget.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

/**
 * 参数标签
 * 
 * @author zhaoyong
 * @version 2015年2月4日
 */
public class ParamTag extends BodyTagSupport
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6948053520359575995L;
    
    /**
     * 参数名称
     */
    private String name;
    
    /**
     * 参数值
     */
    private String value;
    
    @Override
    public int doEndTag()
        throws JspException
    {
        AbstractUrlTag abstractUrlTag = (AbstractUrlTag)findAncestorWithClass(this, ParamAware.class);
        if (null == abstractUrlTag)
        {
            return SKIP_PAGE;
        }
        
        // 处理参数
        if (!StringUtils.isEmpty(this.getName()))
        {
            value = (String)ExpressionUtil.evalNotNull("ParamTag", "value", value, String.class, this, pageContext);
            abstractUrlTag.addParamAttribute(name, value);
            return super.doStartTag();
        }
        
        return SKIP_BODY;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getValue()
    {
        return value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
}