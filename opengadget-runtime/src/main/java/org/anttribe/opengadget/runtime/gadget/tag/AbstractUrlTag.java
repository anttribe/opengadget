/*
 * 文  件   名: AbstractUrlTag.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年2月4日
 */
package org.anttribe.opengadget.runtime.gadget.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.anttribe.opengadget.runtime.constants.Constants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * url地址处理标签
 * 
 * @author zhaoyong
 * @version 2015年2月4日
 */
public abstract class AbstractUrlTag extends BodyTagSupport implements ParamAware
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4329265263546937258L;
    
    /**
     * 模式
     */
    private String mode;
    
    /**
     * 参数
     */
    private Map<String, String> params = new HashMap<String, String>();
    
    @Override
    public int doStartTag()
        throws JspException
    {
        return EVAL_BODY_BUFFERED;
    }
    
    @Override
    public int doEndTag()
        throws JspException
    {
        if (StringUtils.isEmpty(mode))
        {
            return SKIP_BODY;
        }
        
        StringBuffer paramsStr = new StringBuffer("");
        paramsStr.append(this.getParamName()).append("=").append(this.getMode());
        // 构造参数, 并且Base64编码
        if (!MapUtils.isEmpty(params))
        {
            for (Map.Entry<String, String> param : params.entrySet())
            {
                paramsStr.append("&").append(param.getKey()).append("=").append(param.getValue());
            }
        }
        StringBuffer path =
            new StringBuffer("").append("?")
                .append(Constants.REQUEST_PARAM_KEY)
                .append("=")
                .append(Base64.encodeBase64String(paramsStr.toString().getBytes()).trim().replaceAll("\r\n", ""));
        try
        {
            JspWriter out = this.pageContext.getOut();
            out.write(path.toString().trim());
            out.flush();
        }
        catch (IOException e)
        {
            throw new JspException("AbstractUrlTag Exception: cannot write to the output writer. url is: " + path);
        }
        
        return super.doEndTag();
    }
    
    /**
     * 获取请求参数名
     * 
     * @return
     */
    public abstract String getParamName();
    
    /**
     * 添加参数
     * 
     * @param name
     * @param value
     */
    @Override
    public void addParamAttribute(String name, String value)
    {
        if (!StringUtils.isEmpty(name))
        {
            this.params.put(name, value);
        }
    }
    
    public String getMode()
    {
        return mode;
    }
    
    public void setMode(String mode)
    {
        this.mode = mode;
    }
}