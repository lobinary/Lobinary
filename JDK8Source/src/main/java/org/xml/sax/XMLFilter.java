/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// XMLFilter.java - filter SAX2 events.
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the Public Domain.
// $Id: XMLFilter.java,v 1.2 2004/11/03 22:55:32 jsuttor Exp $

package org.xml.sax;


/**
 * Interface for an XML filter.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>An XML filter is like an XML reader, except that it obtains its
 * events from another XML reader rather than a primary source like
 * an XML document or database.  Filters can modify a stream of
 * events as they pass on to the final application.</p>
 *
 * <p>The XMLFilterImpl helper class provides a convenient base
 * for creating SAX2 filters, by passing on all {@link org.xml.sax.EntityResolver
 * EntityResolver}, {@link org.xml.sax.DTDHandler DTDHandler},
 * {@link org.xml.sax.ContentHandler ContentHandler} and {@link org.xml.sax.ErrorHandler
 * ErrorHandler} events automatically.</p>
 *
 * <p>
 *  XML过滤器的接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p> XML过滤器类似于XML阅读器,除了它从另一个XML阅读器而不是像XML文档或数据库这样的主要来源获取事件。过滤器可以在事件流传递到最终应用程序时修改事件流。</p>
 * 
 *  <p> XMLFilterImpl帮助程序类通过传递所有{@link org.xml.sax.EntityResolver EntityResolver},{@link org.xml.sax.DTDHandler DTDHandler}
 * ,{@link org}提供了创建SAX2过滤器的方便基础.xml.sax.ContentHandler ContentHandler}和{@link org.xml.sax.ErrorHandler ErrorHandler}
 * 事件。
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.helpers.XMLFilterImpl
 */
public interface XMLFilter extends XMLReader
{

    /**
     * Set the parent reader.
     *
     * <p>This method allows the application to link the filter to
     * a parent reader (which may be another filter).  The argument
     * may not be null.</p>
     *
     * <p>
     * </p>。
     * 
     * 
     * @param parent The parent reader.
     */
    public abstract void setParent (XMLReader parent);


    /**
     * Get the parent reader.
     *
     * <p>This method allows the application to query the parent
     * reader (which may be another filter).  It is generally a
     * bad idea to perform any operations on the parent reader
     * directly: they should all pass through this filter.</p>
     *
     * <p>
     *  设置父读取器。
     * 
     *  <p>此方法允许应用程序将过滤器链接到父阅读器(可能是另一个过滤器)。参数不能为空。</p>
     * 
     * 
     * @return The parent filter, or null if none has been set.
     */
    public abstract XMLReader getParent ();

}

// end of XMLFilter.java
