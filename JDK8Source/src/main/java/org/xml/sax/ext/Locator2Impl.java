/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
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

// Locator2Impl.java - extended LocatorImpl
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: Locator2Impl.java,v 1.2 2004/11/03 22:49:08 jsuttor Exp $

package org.xml.sax.ext;

import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;


/**
 * SAX2 extension helper for holding additional Entity information,
 * implementing the {@link Locator2} interface.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 *
 * <p> This is not part of core-only SAX2 distributions.</p>
 *
 * <p>
 *  SAX2扩展助手用于保存附加实体信息,实现{@link Locator2}接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)位于公共域中,并且随附<strong>无保修</strong>。</em>
 * </blockquote>
 * 
 *  <p>这不是仅核心SAX2分发的一部分。</p>
 * 
 * 
 * @since SAX 2.0.2
 * @author David Brownell
 */
public class Locator2Impl extends LocatorImpl implements Locator2
{
    private String      encoding;
    private String      version;


    /**
     * Construct a new, empty Locator2Impl object.
     * This will not normally be useful, since the main purpose
     * of this class is to make a snapshot of an existing Locator.
     * <p>
     *  构造一个新的,空的Locator2Impl对象。这通常不会有用,因为此类的主要目的是创建现有定位器的快照。
     * 
     */
    public Locator2Impl () { }

    /**
     * Copy an existing Locator or Locator2 object.
     * If the object implements Locator2, values of the
     * <em>encoding</em> and <em>version</em>strings are copied,
     * otherwise they set to <em>null</em>.
     *
     * <p>
     *  复制现有的定位器或定位器2对象。如果对象实现Locator2,则会复制<em>编码</em>和<em>版本</em>字符串的值,否则它们会设置为<em> null </em>。
     * 
     * 
     * @param locator The existing Locator object.
     */
    public Locator2Impl (Locator locator)
    {
        super (locator);
        if (locator instanceof Locator2) {
            Locator2    l2 = (Locator2) locator;

            version = l2.getXMLVersion ();
            encoding = l2.getEncoding ();
        }
    }

    ////////////////////////////////////////////////////////////////////
    // Locator2 method implementations
    ////////////////////////////////////////////////////////////////////

    /**
     * Returns the current value of the version property.
     *
     * <p>
     *  返回版本属性的当前值。
     * 
     * 
     * @see #setXMLVersion
     */
    public String getXMLVersion ()
        { return version; }

    /**
     * Returns the current value of the encoding property.
     *
     * <p>
     *  返回encoding属性的当前值。
     * 
     * 
     * @see #setEncoding
     */
    public String getEncoding ()
        { return encoding; }


    ////////////////////////////////////////////////////////////////////
    // Setters
    ////////////////////////////////////////////////////////////////////

    /**
     * Assigns the current value of the version property.
     *
     * <p>
     *  分配版本属性的当前值。
     * 
     * 
     * @param version the new "version" value
     * @see #getXMLVersion
     */
    public void setXMLVersion (String version)
        { this.version = version; }

    /**
     * Assigns the current value of the encoding property.
     *
     * <p>
     *  分配编码属性的当前值。
     * 
     * @param encoding the new "encoding" value
     * @see #getEncoding
     */
    public void setEncoding (String encoding)
        { this.encoding = encoding; }
}
