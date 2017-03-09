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

// Attributes2Impl.java - extended AttributesImpl
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: Attributes2Impl.java,v 1.3 2005/02/24 11:20:18 gg156739 Exp $

package org.xml.sax.ext;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;


/**
 * SAX2 extension helper for additional Attributes information,
 * implementing the {@link Attributes2} interface.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 *
 * <p>This is not part of core-only SAX2 distributions.</p>
 *
 * <p>The <em>specified</em> flag for each attribute will always
 * be true, unless it has been set to false in the copy constructor
 * or using {@link #setSpecified}.
 * Similarly, the <em>declared</em> flag for each attribute will
 * always be false, except for defaulted attributes (<em>specified</em>
 * is false), non-CDATA attributes, or when it is set to true using
 * {@link #setDeclared}.
 * If you change an attribute's type by hand, you may need to modify
 * its <em>declared</em> flag to match.
 * </p>
 *
 * <p>
 *  SAX2扩展助手用于附加属性信息,实现{@link Attributes2}接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)位于公共域中,并且随附<strong>无保修</strong>。</em>
 * </blockquote>
 * 
 *  <p>这不是仅核心SAX2分发的一部分。</p>
 * 
 *  <p>每个属性的<em>指定</em>标志将始终为true,除非已在复制构造函数中使用或使用{@link #setSpecified}设置为false。
 * 类似地,每个属性的<em>声明的</em>标志将始终为false,除了默认属性(指定的<em> </em>为false),非CDATA属性, {@link #setDeclared}。
 * 如果您手动更改属性的类型,则可能需要修改其<em>声明的标记</em>以匹配。
 * </p>
 * 
 * 
 * @since SAX 2.0 (extensions 1.1 alpha)
 * @author David Brownell
 */
public class Attributes2Impl extends AttributesImpl implements Attributes2
{
    private boolean     declared [];
    private boolean     specified [];


    /**
     * Construct a new, empty Attributes2Impl object.
     * <p>
     *  构造一个新的,空的Attributes2Impl对象。
     * 
     */
    public Attributes2Impl () {
        specified = null;
        declared = null;
    }


    /**
     * Copy an existing Attributes or Attributes2 object.
     * If the object implements Attributes2, values of the
     * <em>specified</em> and <em>declared</em> flags for each
     * attribute are copied.
     * Otherwise the flag values are defaulted to assume no DTD was used,
     * unless there is evidence to the contrary (such as attributes with
     * type other than CDATA, which must have been <em>declared</em>).
     *
     * <p>This constructor is especially useful inside a
     * {@link org.xml.sax.ContentHandler#startElement startElement} event.</p>
     *
     * <p>
     *  复制现有的Attributes或Attributes2对象。如果对象实现Attributes2,则复制每个属性的<em>指定</em>和<em>声明的</em>标志的值。
     * 否则,标志值默认为假定没有使用DTD,除非有相反的证据(例如,除了CDATA之外的属性,必须已经被声明为</em>)。
     * 
     *  <p>此构造函数在{@link org.xml.sax.ContentHandler#startElement startElement}事件中特别有用。</p>
     * 
     * 
     * @param atts The existing Attributes object.
     */
    public Attributes2Impl (Attributes atts)
    {
        super (atts);
    }


    ////////////////////////////////////////////////////////////////////
    // Implementation of Attributes2
    ////////////////////////////////////////////////////////////////////


    /**
     * Returns the current value of the attribute's "declared" flag.
     * <p>
     * 返回属性的"已声明"标志的当前值。
     * 
     */
    // javadoc mostly from interface
    public boolean isDeclared (int index)
    {
        if (index < 0 || index >= getLength ())
            throw new ArrayIndexOutOfBoundsException (
                "No attribute at index: " + index);
        return declared [index];
    }


    /**
     * Returns the current value of the attribute's "declared" flag.
     * <p>
     *  返回属性的"已声明"标志的当前值。
     * 
     */
    // javadoc mostly from interface
    public boolean isDeclared (String uri, String localName)
    {
        int index = getIndex (uri, localName);

        if (index < 0)
            throw new IllegalArgumentException (
                "No such attribute: local=" + localName
                + ", namespace=" + uri);
        return declared [index];
    }


    /**
     * Returns the current value of the attribute's "declared" flag.
     * <p>
     *  返回属性的"已声明"标志的当前值。
     * 
     */
    // javadoc mostly from interface
    public boolean isDeclared (String qName)
    {
        int index = getIndex (qName);

        if (index < 0)
            throw new IllegalArgumentException (
                "No such attribute: " + qName);
        return declared [index];
    }


    /**
     * Returns the current value of an attribute's "specified" flag.
     *
     * <p>
     *  返回属性的"指定"标志的当前值。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return current flag value
     * @exception java.lang.ArrayIndexOutOfBoundsException When the
     *            supplied index does not identify an attribute.
     */
    public boolean isSpecified (int index)
    {
        if (index < 0 || index >= getLength ())
            throw new ArrayIndexOutOfBoundsException (
                "No attribute at index: " + index);
        return specified [index];
    }


    /**
     * Returns the current value of an attribute's "specified" flag.
     *
     * <p>
     *  返回属性的"指定"标志的当前值。
     * 
     * 
     * @param uri The Namespace URI, or the empty string if
     *        the name has no Namespace URI.
     * @param localName The attribute's local name.
     * @return current flag value
     * @exception java.lang.IllegalArgumentException When the
     *            supplied names do not identify an attribute.
     */
    public boolean isSpecified (String uri, String localName)
    {
        int index = getIndex (uri, localName);

        if (index < 0)
            throw new IllegalArgumentException (
                "No such attribute: local=" + localName
                + ", namespace=" + uri);
        return specified [index];
    }


    /**
     * Returns the current value of an attribute's "specified" flag.
     *
     * <p>
     *  返回属性的"指定"标志的当前值。
     * 
     * 
     * @param qName The XML qualified (prefixed) name.
     * @return current flag value
     * @exception java.lang.IllegalArgumentException When the
     *            supplied name does not identify an attribute.
     */
    public boolean isSpecified (String qName)
    {
        int index = getIndex (qName);

        if (index < 0)
            throw new IllegalArgumentException (
                "No such attribute: " + qName);
        return specified [index];
    }


    ////////////////////////////////////////////////////////////////////
    // Manipulators
    ////////////////////////////////////////////////////////////////////


    /**
     * Copy an entire Attributes object.  The "specified" flags are
     * assigned as true, and "declared" flags as false (except when
     * an attribute's type is not CDATA),
     * unless the object is an Attributes2 object.
     * In that case those flag values are all copied.
     *
     * <p>
     *  复制整个Attributes对象。 "指定"标志分配为true,"宣告"标志为false(除非属性的类型不是CDATA),除非对象是Attributes2对象。在这种情况下,这些标志值都被复制。
     * 
     * 
     * @see AttributesImpl#setAttributes
     */
    public void setAttributes (Attributes atts)
    {
        int length = atts.getLength ();

        super.setAttributes (atts);
        declared = new boolean [length];
        specified = new boolean [length];

        if (atts instanceof Attributes2) {
            Attributes2 a2 = (Attributes2) atts;
            for (int i = 0; i < length; i++) {
                declared [i] = a2.isDeclared (i);
                specified [i] = a2.isSpecified (i);
            }
        } else {
            for (int i = 0; i < length; i++) {
                declared [i] = !"CDATA".equals (atts.getType (i));
                specified [i] = true;
            }
        }
    }


    /**
     * Add an attribute to the end of the list, setting its
     * "specified" flag to true.  To set that flag's value
     * to false, use {@link #setSpecified}.
     *
     * <p>Unless the attribute <em>type</em> is CDATA, this attribute
     * is marked as being declared in the DTD.  To set that flag's value
     * to true for CDATA attributes, use {@link #setDeclared}.
     *
     * <p>
     *  将属性添加到列表的末尾,将其"指定"标志设置为true。要将该标记的值设置为false,请使用{@link #setSpecified}。
     * 
     *  <p>除非属性<em> </em>是CDATA,所以此属性被标记为在DTD中声明。要为CDATA属性将该标志的值设置为true,请使用{@link #setDeclared}。
     * 
     * 
     * @see AttributesImpl#addAttribute
     */
    public void addAttribute (String uri, String localName, String qName,
                              String type, String value)
    {
        super.addAttribute (uri, localName, qName, type, value);


        int length = getLength ();
        if(specified==null)
        {
            specified = new boolean[length];
            declared = new boolean[length];
        } else if (length > specified.length) {
            boolean     newFlags [];

            newFlags = new boolean [length];
            System.arraycopy (declared, 0, newFlags, 0, declared.length);
            declared = newFlags;

            newFlags = new boolean [length];
            System.arraycopy (specified, 0, newFlags, 0, specified.length);
            specified = newFlags;
        }

        specified [length - 1] = true;
        declared [length - 1] = !"CDATA".equals (type);
    }


    // javadoc entirely from superclass
    public void removeAttribute (int index)
    {
        int origMax = getLength () - 1;

        super.removeAttribute (index);
        if (index != origMax) {
            System.arraycopy (declared, index + 1, declared, index,
                    origMax - index);
            System.arraycopy (specified, index + 1, specified, index,
                    origMax - index);
        }
    }


    /**
     * Assign a value to the "declared" flag of a specific attribute.
     * This is normally needed only for attributes of type CDATA,
     * including attributes whose type is changed to or from CDATA.
     *
     * <p>
     *  为特定属性的"已声明"标志分配一个值。这通常仅对类型为CDATA的属性需要,包括类型更改为或来自CDATA的属性。
     * 
     * 
     * @param index The index of the attribute (zero-based).
     * @param value The desired flag value.
     * @exception java.lang.ArrayIndexOutOfBoundsException When the
     *            supplied index does not identify an attribute.
     * @see #setType
     */
    public void setDeclared (int index, boolean value)
    {
        if (index < 0 || index >= getLength ())
            throw new ArrayIndexOutOfBoundsException (
                "No attribute at index: " + index);
        declared [index] = value;
    }


    /**
     * Assign a value to the "specified" flag of a specific attribute.
     * This is the only way this flag can be cleared, except clearing
     * by initialization with the copy constructor.
     *
     * <p>
     *  为特定属性的"指定"标志分配值。这是该标志可以被清除的唯一方式,除了通过用复制构造函数初始化来清除。
     * 
     * @param index The index of the attribute (zero-based).
     * @param value The desired flag value.
     * @exception java.lang.ArrayIndexOutOfBoundsException When the
     *            supplied index does not identify an attribute.
     */
    public void setSpecified (int index, boolean value)
    {
        if (index < 0 || index >= getLength ())
            throw new ArrayIndexOutOfBoundsException (
                "No attribute at index: " + index);
        specified [index] = value;
    }
}
