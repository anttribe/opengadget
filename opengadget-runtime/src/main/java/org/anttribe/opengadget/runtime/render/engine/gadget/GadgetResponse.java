/*
 * 文  件   名: GadgetResponse.java
 * 版         本: opengadget-runtime(Anttribe). All rights reserved
 * 描         述: <描述>
 * 修   改  人: zhaoyong
 * 修改时 间: 2015年1月28日
 */
package org.anttribe.opengadget.runtime.render.engine.gadget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装单个Gadget响应
 * 
 * @author zhaoyong
 * @version 2015年1月30日
 */
public class GadgetResponse extends HttpServletResponseWrapper
{
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GadgetResponse.class);
    
    /**
     * writer
     */
    private PrintWriter writer;
    
    /**
     * content
     */
    private ByteArrayOutputStream content = new ByteArrayOutputStream();
    
    /**
     * @param response
     */
    public GadgetResponse(HttpServletResponse response)
    {
        super(response);
    }
    
    @Override
    public String toString()
    {
        return this.getResponseAsString();
    }
    
    private String getResponseAsString()
    {
        String characterEncoding = this.getCharacterEncoding();
        try
        {
            if (!StringUtils.isEmpty(characterEncoding))
            {
                return this.content.toString(characterEncoding);
            }
            return this.content.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.error("UnsupportedEncoding exception, characterEncoding {}", characterEncoding);
            return "";
        }
    }
    
    @Override
    public PrintWriter getWriter()
        throws IOException
    {
        if (writer == null)
        {
            this.writer = new ResponsePrintWriter(this.getCharacterEncoding());
        }
        return writer;
    }
    
    /**
     * ResponsePrintWriter
     * 
     * @author zhaoyong
     * @version 2014年4月24日
     */
    private class ResponsePrintWriter extends PrintWriter
    {
        private ResponsePrintWriter(String characterEncoding)
            throws UnsupportedEncodingException
        {
            super(new OutputStreamWriter(content, characterEncoding));
        }
        
        @Override
        public void write(char buf[], int off, int len)
        {
            super.write(buf, off, len);
            super.flush();
        }
        
        @Override
        public void write(String s, int off, int len)
        {
            super.write(s, off, len);
            super.flush();
        }
        
        @Override
        public void write(int c)
        {
            super.write(c);
            super.flush();
        }
    }
}