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

// LexicalHandler.java - optional handler for lexical parse events.
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: LexicalHandler.java,v 1.2 2004/11/03 22:49:08 jsuttor Exp $

package org.xml.sax.ext;

import org.xml.sax.SAXException;

/**
 * SAX2 extension handler for lexical events.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This is an optional extension handler for SAX2 to provide
 * lexical information about an XML document, such as comments
 * and CDATA section boundaries.
 * XML readers are not required to recognize this handler, and it
 * is not part of core-only SAX2 distributions.</p>
 *
 * <p>The events in the lexical handler apply to the entire document,
 * not just to the document element, and all lexical handler events
 * must appear between the content handler's startDocument and
 * endDocument events.</p>
 *
 * <p>To set the LexicalHandler for an XML reader, use the
 * {@link org.xml.sax.XMLReader#setProperty setProperty} method
 * with the property name
 * <code>http://xml.org/sax/properties/lexical-handler</code>
 * and an object implementing this interface (or null) as the value.
 * If the reader does not report lexical events, it will throw a
 * {@link org.xml.sax.SAXNotRecognizedException SAXNotRecognizedException}
 * when you attempt to register the handler.</p>
 *
 * <p>
 *  用于词汇事件的SAX2扩展处理程序。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>这是一个可选的扩展处理程序,用于SAX2提供有关XML文档的词法信息,例如注释和CDATA段边界。 XML阅读器不需要识别此处理程序,它不是仅核心SAX2分发的一部分。</p>
 * 
 *  <p>词汇处理程序中的事件适用于整个文档,而不仅仅适用于文档元素,所有词法处理程序事件必须出现在内容处理程序的startDocument和endDocument事件之间。</p>
 * 
 *  <p>要为XML阅读器设置LexicalHandler,请使用{@link org.xml.sax.XMLReader#setProperty setProperty}方法,其属性名为<code> h
 * ttp://xml.org/sax/properties/lexical-处理程序</code>和实现此接口的对象(或null)作为值。
 * 如果读者不报告词法事件,当您尝试注册处理程序时,它将抛出一个{@link org.xml.sax.SAXNotRecognizedException SAXNotRecognizedException}
 * 。
 * </p>。
 * 
 * 
 * @since SAX 2.0 (extensions 1.0)
 * @author David Megginson
 */
public interface LexicalHandler
{

    /**
     * Report the start of DTD declarations, if any.
     *
     * <p>This method is intended to report the beginning of the
     * DOCTYPE declaration; if the document has no DOCTYPE declaration,
     * this method will not be invoked.</p>
     *
     * <p>All declarations reported through
     * {@link org.xml.sax.DTDHandler DTDHandler} or
     * {@link org.xml.sax.ext.DeclHandler DeclHandler} events must appear
     * between the startDTD and {@link #endDTD endDTD} events.
     * Declarations are assumed to belong to the internal DTD subset
     * unless they appear between {@link #startEntity startEntity}
     * and {@link #endEntity endEntity} events.  Comments and
     * processing instructions from the DTD should also be reported
     * between the startDTD and endDTD events, in their original
     * order of (logical) occurrence; they are not required to
     * appear in their correct locations relative to DTDHandler
     * or DeclHandler events, however.</p>
     *
     * <p>Note that the start/endDTD events will appear within
     * the start/endDocument events from ContentHandler and
     * before the first
     * {@link org.xml.sax.ContentHandler#startElement startElement}
     * event.</p>
     *
     * <p>
     *  报告DTD声明的开始(如果有)。
     * 
     * <p>此方法旨在报告DOCTYPE声明的开始;如果文档没有DOCTYPE声明,则不会调用此方法。</p>
     * 
     *  <p>通过{@link org.xml.sax.DTDHandler DTDHandler}或{@link org.xml.sax.ext.DeclHandler DeclHandler}事件报告的所
     * 有声明必须出现在startDTD和{@link #endDTD endDTD}事件之间。
     * 声明假定属于内部DTD子集,除非它们出现在{@link #startEntity startEntity}和{@link #endEntity endEntity}事件之间。
     * 来自DTD的注释和处理指令也应以startDTD和endDTD事件之间的原始顺序(逻辑)出现来报告;它们不需要出现在相对于DTDHandler或DeclHandler事件的正确位置。</p>。
     * 
     *  <p>请注意,start / endDTD事件将出现在ContentHandler的start / endDocument事件中,并出现在第一个{@link org.xml.sax.ContentHandler#startElement startElement}
     * 事件之前。
     * </p>。
     * 
     * 
     * @param name The document type name.
     * @param publicId The declared public identifier for the
     *        external DTD subset, or null if none was declared.
     * @param systemId The declared system identifier for the
     *        external DTD subset, or null if none was declared.
     *        (Note that this is not resolved against the document
     *        base URI.)
     * @exception SAXException The application may raise an
     *            exception.
     * @see #endDTD
     * @see #startEntity
     */
    public abstract void startDTD (String name, String publicId,
                                   String systemId)
        throws SAXException;


    /**
     * Report the end of DTD declarations.
     *
     * <p>This method is intended to report the end of the
     * DOCTYPE declaration; if the document has no DOCTYPE declaration,
     * this method will not be invoked.</p>
     *
     * <p>
     *  报告DTD声明的结束。
     * 
     *  <p>此方法旨在报告DOCTYPE声明的结束;如果文档没有DOCTYPE声明,将不会调用此方法。</p>
     * 
     * 
     * @exception SAXException The application may raise an exception.
     * @see #startDTD
     */
    public abstract void endDTD ()
        throws SAXException;


    /**
     * Report the beginning of some internal and external XML entities.
     *
     * <p>The reporting of parameter entities (including
     * the external DTD subset) is optional, and SAX2 drivers that
     * report LexicalHandler events may not implement it; you can use the
     * <code
     * >http://xml.org/sax/features/lexical-handler/parameter-entities</code>
     * feature to query or control the reporting of parameter entities.</p>
     *
     * <p>General entities are reported with their regular names,
     * parameter entities have '%' prepended to their names, and
     * the external DTD subset has the pseudo-entity name "[dtd]".</p>
     *
     * <p>When a SAX2 driver is providing these events, all other
     * events must be properly nested within start/end entity
     * events.  There is no additional requirement that events from
     * {@link org.xml.sax.ext.DeclHandler DeclHandler} or
     * {@link org.xml.sax.DTDHandler DTDHandler} be properly ordered.</p>
     *
     * <p>Note that skipped entities will be reported through the
     * {@link org.xml.sax.ContentHandler#skippedEntity skippedEntity}
     * event, which is part of the ContentHandler interface.</p>
     *
     * <p>Because of the streaming event model that SAX uses, some
     * entity boundaries cannot be reported under any
     * circumstances:</p>
     *
     * <ul>
     * <li>general entities within attribute values</li>
     * <li>parameter entities within declarations</li>
     * </ul>
     *
     * <p>These will be silently expanded, with no indication of where
     * the original entity boundaries were.</p>
     *
     * <p>Note also that the boundaries of character references (which
     * are not really entities anyway) are not reported.</p>
     *
     * <p>All start/endEntity events must be properly nested.
     *
     * <p>
     *  报告一些内部和外部XML实体的开始。
     * 
     * <p>参数实体(包括外部DTD子集)的报告是可选的,报告LexicalHandler事件的SAX2驱动程序可能无法实现;您可以使用<code> http://xml.org/sax/features/l
     * exical-handler/parameter-entities </code>功能来查询或控制参数实体的报告。
     * </p>。
     * 
     *  <p>一般实体以其常规名称报告,参数实体在其名称前添加'％',外部DTD子集具有伪实体名称"[dtd]"。</p>
     * 
     *  <p>当SAX2驱动程序提供这些事件时,所有其他事件必须正确嵌套在开始/结束实体事件中。
     * 没有其他要求,来自{@link org.xml.sax.ext.DeclHandler DeclHandler}或{@link org.xml.sax.DTDHandler DTDHandler}的事件
     * 可以正确排序。
     *  <p>当SAX2驱动程序提供这些事件时,所有其他事件必须正确嵌套在开始/结束实体事件中。</p>。
     * 
     *  <p>请注意,跳过的实体将通过{@link org.xml.sax.ContentHandler#skippedEntity skippedEntity}事件(属于ContentHandler界面的一
     * 部分)进行报告。
     * </p>。
     * 
     *  <p>由于SAX使用的流式事件模型,某些实体边界无法在任何情况下报告：</p>
     * 
     * <ul>
     *  <li>属性值中的一般实体</li> <li>声明中的参数实体</li>
     * </ul>
     * 
     * 
     * @param name The name of the entity.  If it is a parameter
     *        entity, the name will begin with '%', and if it is the
     *        external DTD subset, it will be "[dtd]".
     * @exception SAXException The application may raise an exception.
     * @see #endEntity
     * @see org.xml.sax.ext.DeclHandler#internalEntityDecl
     * @see org.xml.sax.ext.DeclHandler#externalEntityDecl
     */
    public abstract void startEntity (String name)
        throws SAXException;


    /**
     * Report the end of an entity.
     *
     * <p>
     *  <p>这些会默默展开,而不会显示原始实体边界的位置。</p>
     * 
     *  <p>请注意,字符引用(实际上不是实体)的边界不会报告。</p>
     * 
     *  <p>所有start / endEntity事件都必须正确嵌套。
     * 
     * 
     * @param name The name of the entity that is ending.
     * @exception SAXException The application may raise an exception.
     * @see #startEntity
     */
    public abstract void endEntity (String name)
        throws SAXException;


    /**
     * Report the start of a CDATA section.
     *
     * <p>The contents of the CDATA section will be reported through
     * the regular {@link org.xml.sax.ContentHandler#characters
     * characters} event; this event is intended only to report
     * the boundary.</p>
     *
     * <p>
     * 报告实体的结束。
     * 
     * 
     * @exception SAXException The application may raise an exception.
     * @see #endCDATA
     */
    public abstract void startCDATA ()
        throws SAXException;


    /**
     * Report the end of a CDATA section.
     *
     * <p>
     *  报告CDATA部分的开始。
     * 
     *  <p> CDATA部分的内容将通过常规{@link org.xml.sax.ContentHandler#characters characters}事件报告;此事件仅用于报告边界。</p>
     * 
     * 
     * @exception SAXException The application may raise an exception.
     * @see #startCDATA
     */
    public abstract void endCDATA ()
        throws SAXException;


    /**
     * Report an XML comment anywhere in the document.
     *
     * <p>This callback will be used for comments inside or outside the
     * document element, including comments in the external DTD
     * subset (if read).  Comments in the DTD must be properly
     * nested inside start/endDTD and start/endEntity events (if
     * used).</p>
     *
     * <p>
     *  报告CDATA部分的结尾。
     * 
     * 
     * @param ch An array holding the characters in the comment.
     * @param start The starting position in the array.
     * @param length The number of characters to use from the array.
     * @exception SAXException The application may raise an exception.
     */
    public abstract void comment (char ch[], int start, int length)
        throws SAXException;

}

// end of LexicalHandler.java
