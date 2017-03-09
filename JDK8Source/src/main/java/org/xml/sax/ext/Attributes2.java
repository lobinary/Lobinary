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

// Attributes2.java - extended Attributes
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: Attributes2.java,v 1.2 2004/11/03 22:49:07 jsuttor Exp $

package org.xml.sax.ext;

import org.xml.sax.Attributes;


/**
 * SAX2 extension to augment the per-attribute information
 * provided though {@link Attributes}.
 * If an implementation supports this extension, the attributes
 * provided in {@link org.xml.sax.ContentHandler#startElement
 * ContentHandler.startElement() } will implement this interface,
 * and the <em>http://xml.org/sax/features/use-attributes2</em>
 * feature flag will have the value <em>true</em>.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 *
 * <p> XMLReader implementations are not required to support this
 * information, and it is not part of core-only SAX2 distributions.</p>
 *
 * <p>Note that if an attribute was defaulted (<em>!isSpecified()</em>)
 * it will of necessity also have been declared (<em>isDeclared()</em>)
 * in the DTD.
 * Similarly if an attribute's type is anything except CDATA, then it
 * must have been declared.
 * </p>
 *
 * <p>
 *  SAX2扩展来扩充通过{@link Attributes}提供的每属性信息。
 * 如果实现支持此扩展,{@link org.xml.sax.ContentHandler#startElement ContentHandler.startElement()}中提供的属性将实现此接口,并
 * 且<em> http://xml.org/sax/features / use-attributes2 </em> feature标志的值将为<em> true </em>。
 *  SAX2扩展来扩充通过{@link Attributes}提供的每属性信息。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)位于公共域中,并且随附<strong>无保修</strong>。</em>
 * </blockquote>
 * 
 *  <p> XMLReader实现不需要支持此信息,它不是仅核心SAX2分发的一部分。</p>
 * 
 *  <p>请注意,如果某个属性为默认属性(<em>！isSpecified()</em>),则必须在DTD中声明(<em> isDeclared()</em>)。
 * 类似地,如果属性的类型是除CDATA之外的任何类型,那么它必须已被声明。
 * </p>
 * 
 * 
 * @since SAX 2.0 (extensions 1.1 alpha)
 * @author David Brownell
 */
public interface Attributes2 extends Attributes
{
    /**
     * Returns false unless the attribute was declared in the DTD.
     * This helps distinguish two kinds of attributes that SAX reports
     * as CDATA:  ones that were declared (and hence are usually valid),
     * and those that were not (and which are never valid).
     *
     * <p>
     *  返回false,除非在DTD中声明了该属性。这有助于区分SAX报告为CDATA的两种属性：已声明(因此通常有效)的属性,以及不属于(且永远无效)的属性。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return true if the attribute was declared in the DTD,
     *          false otherwise.
     * @exception java.lang.ArrayIndexOutOfBoundsException When the
     *            supplied index does not identify an attribute.
     */
    public boolean isDeclared (int index);

    /**
     * Returns false unless the attribute was declared in the DTD.
     * This helps distinguish two kinds of attributes that SAX reports
     * as CDATA:  ones that were declared (and hence are usually valid),
     * and those that were not (and which are never valid).
     *
     * <p>
     * 返回false,除非在DTD中声明了该属性。这有助于区分SAX报告为CDATA的两种属性：已声明(因此通常有效)的属性,以及不属于(且永远无效)的属性。
     * 
     * 
     * @param qName The XML qualified (prefixed) name.
     * @return true if the attribute was declared in the DTD,
     *          false otherwise.
     * @exception java.lang.IllegalArgumentException When the
     *            supplied name does not identify an attribute.
     */
    public boolean isDeclared (String qName);

    /**
     * Returns false unless the attribute was declared in the DTD.
     * This helps distinguish two kinds of attributes that SAX reports
     * as CDATA:  ones that were declared (and hence are usually valid),
     * and those that were not (and which are never valid).
     *
     * <p>Remember that since DTDs do not "understand" namespaces, the
     * namespace URI associated with an attribute may not have come from
     * the DTD.  The declaration will have applied to the attribute's
     * <em>qName</em>.
     *
     * <p>
     *  返回false,除非在DTD中声明了该属性。这有助于区分SAX报告为CDATA的两种属性：已声明(因此通常有效)的属性,以及不属于(且永远无效)的属性。
     * 
     *  <p>请记住,由于DTD不"理解"名称空间,与属性关联的命名空间URI可能不是来自DTD。声明将应用于属性的<em> qName </em>。
     * 
     * 
     * @param uri The Namespace URI, or the empty string if
     *        the name has no Namespace URI.
     * @param localName The attribute's local name.
     * @return true if the attribute was declared in the DTD,
     *          false otherwise.
     * @exception java.lang.IllegalArgumentException When the
     *            supplied names do not identify an attribute.
     */
    public boolean isDeclared (String uri, String localName);

    /**
     * Returns true unless the attribute value was provided
     * by DTD defaulting.
     *
     * <p>
     *  返回true,除非属性值由DTD默认值提供。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return true if the value was found in the XML text,
     *          false if the value was provided by DTD defaulting.
     * @exception java.lang.ArrayIndexOutOfBoundsException When the
     *            supplied index does not identify an attribute.
     */
    public boolean isSpecified (int index);

    /**
     * Returns true unless the attribute value was provided
     * by DTD defaulting.
     *
     * <p>Remember that since DTDs do not "understand" namespaces, the
     * namespace URI associated with an attribute may not have come from
     * the DTD.  The declaration will have applied to the attribute's
     * <em>qName</em>.
     *
     * <p>
     *  返回true,除非属性值由DTD默认值提供。
     * 
     *  <p>请记住,由于DTD不"理解"名称空间,与属性关联的命名空间URI可能不是来自DTD。声明将应用于属性的<em> qName </em>。
     * 
     * 
     * @param uri The Namespace URI, or the empty string if
     *        the name has no Namespace URI.
     * @param localName The attribute's local name.
     * @return true if the value was found in the XML text,
     *          false if the value was provided by DTD defaulting.
     * @exception java.lang.IllegalArgumentException When the
     *            supplied names do not identify an attribute.
     */
    public boolean isSpecified (String uri, String localName);

    /**
     * Returns true unless the attribute value was provided
     * by DTD defaulting.
     *
     * <p>
     * 
     * @param qName The XML qualified (prefixed) name.
     * @return true if the value was found in the XML text,
     *          false if the value was provided by DTD defaulting.
     * @exception java.lang.IllegalArgumentException When the
     *            supplied name does not identify an attribute.
     */
    public boolean isSpecified (String qName);
}
