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

// SAX default implementation for Locator.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: LocatorImpl.java,v 1.2 2004/11/03 22:53:09 jsuttor Exp $

package org.xml.sax.helpers;

import org.xml.sax.Locator;


/**
 * Provide an optional convenience implementation of Locator.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class is available mainly for application writers, who
 * can use it to make a persistent snapshot of a locator at any
 * point during a document parse:</p>
 *
 * <pre>
 * Locator locator;
 * Locator startloc;
 *
 * public void setLocator (Locator locator)
 * {
 *         // note the locator
 *   this.locator = locator;
 * }
 *
 * public void startDocument ()
 * {
 *         // save the location of the start of the document
 *         // for future use.
 *   Locator startloc = new LocatorImpl(locator);
 * }
 *</pre>
 *
 * <p>Normally, parser writers will not use this class, since it
 * is more efficient to provide location information only when
 * requested, rather than constantly updating a Locator object.</p>
 *
 * <p>
 *  提供可选的方便实现定位器。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此类主要适用于应用程序编写者,他们可以使用它在文档解析期间的任何时间创建定位器的持久性快照：</p>
 * 
 * <pre>
 *  定位器定位器;定位器startloc;
 * 
 *  public void setLocator(Locator locator){//注意定位器this.locator = locator; }}
 * 
 *  public void startDocument(){//保存文档开始的位置//以供将来使用。 Locator startloc = new LocatorImpl(locator); }}
 * /pre>
 * 
 *  <p>通常,解析器写入程序不会使用此类,因为只有在请求时才提供位置信息更有效,而不是不断更新定位器对象。</p>
 * 
 * 
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.Locator Locator
 */
public class LocatorImpl implements Locator
{


    /**
     * Zero-argument constructor.
     *
     * <p>This will not normally be useful, since the main purpose
     * of this class is to make a snapshot of an existing Locator.</p>
     * <p>
     *  零参数构造函数。
     * 
     *  <p>这通常不会有用,因为此类的主要目的是制作现有定位器的快照。</p>
     * 
     */
    public LocatorImpl ()
    {
    }


    /**
     * Copy constructor.
     *
     * <p>Create a persistent copy of the current state of a locator.
     * When the original locator changes, this copy will still keep
     * the original values (and it can be used outside the scope of
     * DocumentHandler methods).</p>
     *
     * <p>
     *  复制构造函数。
     * 
     *  <p>创建定位器当前状态的永久副本。当原始定位器更改时,此副本仍将保留原始值(并且可以在DocumentHandler方法范围之外使用)。</p>
     * 
     * 
     * @param locator The locator to copy.
     */
    public LocatorImpl (Locator locator)
    {
        setPublicId(locator.getPublicId());
        setSystemId(locator.getSystemId());
        setLineNumber(locator.getLineNumber());
        setColumnNumber(locator.getColumnNumber());
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.Locator
    ////////////////////////////////////////////////////////////////////


    /**
     * Return the saved public identifier.
     *
     * <p>
     *  返回保存的公共标识符。
     * 
     * 
     * @return The public identifier as a string, or null if none
     *         is available.
     * @see org.xml.sax.Locator#getPublicId
     * @see #setPublicId
     */
    public String getPublicId ()
    {
        return publicId;
    }


    /**
     * Return the saved system identifier.
     *
     * <p>
     * 返回保存的系统标识符。
     * 
     * 
     * @return The system identifier as a string, or null if none
     *         is available.
     * @see org.xml.sax.Locator#getSystemId
     * @see #setSystemId
     */
    public String getSystemId ()
    {
        return systemId;
    }


    /**
     * Return the saved line number (1-based).
     *
     * <p>
     *  返回保存的行号(从1开始)。
     * 
     * 
     * @return The line number as an integer, or -1 if none is available.
     * @see org.xml.sax.Locator#getLineNumber
     * @see #setLineNumber
     */
    public int getLineNumber ()
    {
        return lineNumber;
    }


    /**
     * Return the saved column number (1-based).
     *
     * <p>
     *  返回保存的列编号(从1开始)。
     * 
     * 
     * @return The column number as an integer, or -1 if none is available.
     * @see org.xml.sax.Locator#getColumnNumber
     * @see #setColumnNumber
     */
    public int getColumnNumber ()
    {
        return columnNumber;
    }



    ////////////////////////////////////////////////////////////////////
    // Setters for the properties (not in org.xml.sax.Locator)
    ////////////////////////////////////////////////////////////////////


    /**
     * Set the public identifier for this locator.
     *
     * <p>
     *  设置此定位器的公共标识符。
     * 
     * 
     * @param publicId The new public identifier, or null
     *        if none is available.
     * @see #getPublicId
     */
    public void setPublicId (String publicId)
    {
        this.publicId = publicId;
    }


    /**
     * Set the system identifier for this locator.
     *
     * <p>
     *  设置此定位器的系统标识符。
     * 
     * 
     * @param systemId The new system identifier, or null
     *        if none is available.
     * @see #getSystemId
     */
    public void setSystemId (String systemId)
    {
        this.systemId = systemId;
    }


    /**
     * Set the line number for this locator (1-based).
     *
     * <p>
     *  设置此定位器的行号(从1开始)。
     * 
     * 
     * @param lineNumber The line number, or -1 if none is available.
     * @see #getLineNumber
     */
    public void setLineNumber (int lineNumber)
    {
        this.lineNumber = lineNumber;
    }


    /**
     * Set the column number for this locator (1-based).
     *
     * <p>
     *  设置此定位器的列号(从1开始)。
     * 
     * @param columnNumber The column number, or -1 if none is available.
     * @see #getColumnNumber
     */
    public void setColumnNumber (int columnNumber)
    {
        this.columnNumber = columnNumber;
    }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    private String publicId;
    private String systemId;
    private int lineNumber;
    private int columnNumber;

}

// end of LocatorImpl.java
