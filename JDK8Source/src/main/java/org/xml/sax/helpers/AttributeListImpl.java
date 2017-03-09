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

// SAX default implementation for AttributeList.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: AttributeListImpl.java,v 1.2 2004/11/03 22:53:08 jsuttor Exp $

package org.xml.sax.helpers;

import org.xml.sax.AttributeList;

import java.util.Vector;


/**
 * Default implementation for AttributeList.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>AttributeList implements the deprecated SAX1 {@link
 * org.xml.sax.AttributeList AttributeList} interface, and has been
 * replaced by the new SAX2 {@link org.xml.sax.helpers.AttributesImpl
 * AttributesImpl} interface.</p>
 *
 * <p>This class provides a convenience implementation of the SAX
 * {@link org.xml.sax.AttributeList AttributeList} interface.  This
 * implementation is useful both for SAX parser writers, who can use
 * it to provide attributes to the application, and for SAX application
 * writers, who can use it to create a persistent copy of an element's
 * attribute specifications:</p>
 *
 * <pre>
 * private AttributeList myatts;
 *
 * public void startElement (String name, AttributeList atts)
 * {
 *              // create a persistent copy of the attribute list
 *              // for use outside this method
 *   myatts = new AttributeListImpl(atts);
 *   [...]
 * }
 * </pre>
 *
 * <p>Please note that SAX parsers are not required to use this
 * class to provide an implementation of AttributeList; it is
 * supplied only as an optional convenience.  In particular,
 * parser writers are encouraged to invent more efficient
 * implementations.</p>
 *
 * <p>
 *  AttributeList的默认实现。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p> AttributeList实现了已弃用的SAX1 {@link org.xml.sax.AttributeList AttributeList}接口,并已被新的SAX2 {@link org.xml.sax.helpers.AttributesImpl AttributesImpl}
 * 接口替代。
 * </p>。
 * 
 *  <p>此类提供了SAX {@link org.xml.sax.AttributeList AttributeList}接口的方便实现。
 * 此实现对于SAX解析器写入程序(可以使用它为应用程序提供属性)和SAX应用程序写入程序(可以使用它来创建元素的属性规范的永久副本)有用：</p>。
 * 
 * <pre>
 *  private AttributeList myatts;
 * 
 *  public void startElement(String name,AttributeList atts){//创建属性列表的持久副本//用于此方法之外myatts = new AttributeListImpl(atts); [...]。
 * </pre>
 * 
 *  <p>请注意,SAX解析器不需要使用此类来提供AttributeList的实现;它仅作为可选的方便提供。特别地,鼓励解析器作者发明更有效的实现。</p>
 * 
 * 
 * @deprecated This class implements a deprecated interface,
 *             {@link org.xml.sax.AttributeList AttributeList};
 *             that interface has been replaced by
 *             {@link org.xml.sax.Attributes Attributes},
 *             which is implemented in the
 *             {@link org.xml.sax.helpers.AttributesImpl
 *            AttributesImpl} helper class.
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.AttributeList
 * @see org.xml.sax.DocumentHandler#startElement
 */
public class AttributeListImpl implements AttributeList
{

    /**
     * Create an empty attribute list.
     *
     * <p>This constructor is most useful for parser writers, who
     * will use it to create a single, reusable attribute list that
     * can be reset with the clear method between elements.</p>
     *
     * <p>
     *  创建一个空的属性列表。
     * 
     * <p>此构造函数对于解析器编写者最有用,他将使用它来创建一个可重用的属性列表,可以使用元素之间的clear方法重置。</p>
     * 
     * 
     * @see #addAttribute
     * @see #clear
     */
    public AttributeListImpl ()
    {
    }


    /**
     * Construct a persistent copy of an existing attribute list.
     *
     * <p>This constructor is most useful for application writers,
     * who will use it to create a persistent copy of an existing
     * attribute list.</p>
     *
     * <p>
     *  构造现有属性列表的永久副本。
     * 
     *  <p>此构造函数对于应用程序编写者最有用,他们将使用它来创建现有属性列表的永久副本。</p>
     * 
     * 
     * @param atts The attribute list to copy
     * @see org.xml.sax.DocumentHandler#startElement
     */
    public AttributeListImpl (AttributeList atts)
    {
        setAttributeList(atts);
    }



    ////////////////////////////////////////////////////////////////////
    // Methods specific to this class.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set the attribute list, discarding previous contents.
     *
     * <p>This method allows an application writer to reuse an
     * attribute list easily.</p>
     *
     * <p>
     *  设置属性列表,丢弃以前的内容。
     * 
     *  <p>此方法允许应用程序编写器轻松重用属性列表。</p>
     * 
     * 
     * @param atts The attribute list to copy.
     */
    public void setAttributeList (AttributeList atts)
    {
        int count = atts.getLength();

        clear();

        for (int i = 0; i < count; i++) {
            addAttribute(atts.getName(i), atts.getType(i), atts.getValue(i));
        }
    }


    /**
     * Add an attribute to an attribute list.
     *
     * <p>This method is provided for SAX parser writers, to allow them
     * to build up an attribute list incrementally before delivering
     * it to the application.</p>
     *
     * <p>
     *  将属性添加到属性列表。
     * 
     *  <p>为SAX解析器写入程序提供此方法,以允许它们在将其传递到应用程序之前增量地构建属性列表。</p>
     * 
     * 
     * @param name The attribute name.
     * @param type The attribute type ("NMTOKEN" for an enumeration).
     * @param value The attribute value (must not be null).
     * @see #removeAttribute
     * @see org.xml.sax.DocumentHandler#startElement
     */
    public void addAttribute (String name, String type, String value)
    {
        names.addElement(name);
        types.addElement(type);
        values.addElement(value);
    }


    /**
     * Remove an attribute from the list.
     *
     * <p>SAX application writers can use this method to filter an
     * attribute out of an AttributeList.  Note that invoking this
     * method will change the length of the attribute list and
     * some of the attribute's indices.</p>
     *
     * <p>If the requested attribute is not in the list, this is
     * a no-op.</p>
     *
     * <p>
     *  从列表中删除属性。
     * 
     *  <p> SAX应用程序编写器可以使用此方法从AttributeList中过滤属性。请注意,调用此方法将会更改属性列表的长度和某些属性的索引。</p>
     * 
     *  <p>如果请求的属性不在列表中,则这是一个无操作。</p>
     * 
     * 
     * @param name The attribute name.
     * @see #addAttribute
     */
    public void removeAttribute (String name)
    {
        int i = names.indexOf(name);

        if (i >= 0) {
            names.removeElementAt(i);
            types.removeElementAt(i);
            values.removeElementAt(i);
        }
    }


    /**
     * Clear the attribute list.
     *
     * <p>SAX parser writers can use this method to reset the attribute
     * list between DocumentHandler.startElement events.  Normally,
     * it will make sense to reuse the same AttributeListImpl object
     * rather than allocating a new one each time.</p>
     *
     * <p>
     *  清除属性列表。
     * 
     *  <p> SAX解析器写入程序可以使用此方法在DocumentHandler.startElement事件之间重置属性列表。
     * 通常,重用同一个AttributeListImpl对象而不是每次都分配一个新对象是有意义的。</p>。
     * 
     * 
     * @see org.xml.sax.DocumentHandler#startElement
     */
    public void clear ()
    {
        names.removeAllElements();
        types.removeAllElements();
        values.removeAllElements();
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.AttributeList
    ////////////////////////////////////////////////////////////////////


    /**
     * Return the number of attributes in the list.
     *
     * <p>
     *  返回列表中的属性数。
     * 
     * 
     * @return The number of attributes in the list.
     * @see org.xml.sax.AttributeList#getLength
     */
    public int getLength ()
    {
        return names.size();
    }


    /**
     * Get the name of an attribute (by position).
     *
     * <p>
     *  获取属性的名称(按位置)。
     * 
     * 
     * @param i The position of the attribute in the list.
     * @return The attribute name as a string, or null if there
     *         is no attribute at that position.
     * @see org.xml.sax.AttributeList#getName(int)
     */
    public String getName (int i)
    {
        if (i < 0) {
            return null;
        }
        try {
            return (String)names.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * Get the type of an attribute (by position).
     *
     * <p>
     *  获取属性的类型(按位置)。
     * 
     * 
     * @param i The position of the attribute in the list.
     * @return The attribute type as a string ("NMTOKEN" for an
     *         enumeration, and "CDATA" if no declaration was
     *         read), or null if there is no attribute at
     *         that position.
     * @see org.xml.sax.AttributeList#getType(int)
     */
    public String getType (int i)
    {
        if (i < 0) {
            return null;
        }
        try {
            return (String)types.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * Get the value of an attribute (by position).
     *
     * <p>
     *  获取属性的值(按位置)。
     * 
     * 
     * @param i The position of the attribute in the list.
     * @return The attribute value as a string, or null if
     *         there is no attribute at that position.
     * @see org.xml.sax.AttributeList#getValue(int)
     */
    public String getValue (int i)
    {
        if (i < 0) {
            return null;
        }
        try {
            return (String)values.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * Get the type of an attribute (by name).
     *
     * <p>
     * 获取属性的类型(按名称)。
     * 
     * 
     * @param name The attribute name.
     * @return The attribute type as a string ("NMTOKEN" for an
     *         enumeration, and "CDATA" if no declaration was
     *         read).
     * @see org.xml.sax.AttributeList#getType(java.lang.String)
     */
    public String getType (String name)
    {
        return getType(names.indexOf(name));
    }


    /**
     * Get the value of an attribute (by name).
     *
     * <p>
     *  获取属性的值(按名称)。
     * 
     * @param name The attribute name.
     * @see org.xml.sax.AttributeList#getValue(java.lang.String)
     */
    public String getValue (String name)
    {
        return getValue(names.indexOf(name));
    }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    Vector names = new Vector();
    Vector types = new Vector();
    Vector values = new Vector();

}

// end of AttributeListImpl.java
