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

// SAX DTD handler.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: DTDHandler.java,v 1.2 2004/11/03 22:44:51 jsuttor Exp $

package org.xml.sax;

/**
 * Receive notification of basic DTD-related events.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>If a SAX application needs information about notations and
 * unparsed entities, then the application implements this
 * interface and registers an instance with the SAX parser using
 * the parser's setDTDHandler method.  The parser uses the
 * instance to report notation and unparsed entity declarations to
 * the application.</p>
 *
 * <p>Note that this interface includes only those DTD events that
 * the XML recommendation <em>requires</em> processors to report:
 * notation and unparsed entity declarations.</p>
 *
 * <p>The SAX parser may report these events in any order, regardless
 * of the order in which the notations and unparsed entities were
 * declared; however, all DTD events must be reported after the
 * document handler's startDocument event, and before the first
 * startElement event.
 * (If the {@link org.xml.sax.ext.LexicalHandler LexicalHandler} is
 * used, these events must also be reported before the endDTD event.)
 * </p>
 *
 * <p>It is up to the application to store the information for
 * future use (perhaps in a hash table or object tree).
 * If the application encounters attributes of type "NOTATION",
 * "ENTITY", or "ENTITIES", it can use the information that it
 * obtained through this interface to find the entity and/or
 * notation corresponding with the attribute value.</p>
 *
 * <p>
 *  接收与DTD相关的基本事件的通知。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>如果SAX应用程序需要关于记法和未解析实体的信息,则应用程序实现此接口,并使用解析器的setDTDHandler方法向SAX解析器注册实例。解析器使用实例向应用程序报告签名和未解析的实体声明。
 * </p>。
 * 
 *  <p>请注意,此界面仅包括XML建议<em>需要</em>处理器报告的DTD事件：符号和未解析的实体声明。</p>
 * 
 *  <p> SAX解析器可以以任何顺序报告这些事件,无论声明符号和未解析实体的顺序如何;但是,所有DTD事件都必须在文档处理程序的startDocument事件之后和第一个startElement事件之前
 * 进行报告。
 *  (如果使用{@link org.xml.sax.ext.LexicalHandler LexicalHandler},这些事件也必须在endDTD事件之前报告。)。
 * </p>
 * 
 * <p>由应用程序存储信息以供将来使用(可能在散列表或对象树中)。
 * 如果应用程序遇到类型为"NOTATION","ENTITY"或"ENTITIES"的属性,则它可以使用其通过此接口获得的信息来查找与属性值相对应的实体和/或符号。</p>。
 * 
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.XMLReader#setDTDHandler
 */
public interface DTDHandler {


    /**
     * Receive notification of a notation declaration event.
     *
     * <p>It is up to the application to record the notation for later
     * reference, if necessary;
     * notations may appear as attribute values and in unparsed entity
     * declarations, and are sometime used with processing instruction
     * target names.</p>
     *
     * <p>At least one of publicId and systemId must be non-null.
     * If a system identifier is present, and it is a URL, the SAX
     * parser must resolve it fully before passing it to the
     * application through this event.</p>
     *
     * <p>There is no guarantee that the notation declaration will be
     * reported before any unparsed entities that use it.</p>
     *
     * <p>
     * 
     * 
     * @param name The notation name.
     * @param publicId The notation's public identifier, or null if
     *        none was given.
     * @param systemId The notation's system identifier, or null if
     *        none was given.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see #unparsedEntityDecl
     * @see org.xml.sax.Attributes
     */
    public abstract void notationDecl (String name,
                                       String publicId,
                                       String systemId)
        throws SAXException;


    /**
     * Receive notification of an unparsed entity declaration event.
     *
     * <p>Note that the notation name corresponds to a notation
     * reported by the {@link #notationDecl notationDecl} event.
     * It is up to the application to record the entity for later
     * reference, if necessary;
     * unparsed entities may appear as attribute values.
     * </p>
     *
     * <p>If the system identifier is a URL, the parser must resolve it
     * fully before passing it to the application.</p>
     *
     * <p>
     *  接收符号声明事件的通知。
     * 
     *  <p>如果需要,由应用程序记录符号供以后参考;符号可以作为属性值和未解析的实体声明出现,并且有时用于处理指令目标名称。</p>
     * 
     *  <p> publicId和systemId中的至少一个必须为非null。如果存在系统标识符,并且它是一个URL,则SAX解析器必须完全解析它,然后通过此事件将其传递给应用程序。</p>
     * 
     *  <p>不保证在使用它的任何未分析​​实体之前报告符号声明。</p>
     * 
     * 
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @param name The unparsed entity's name.
     * @param publicId The entity's public identifier, or null if none
     *        was given.
     * @param systemId The entity's system identifier.
     * @param notationName The name of the associated notation.
     * @see #notationDecl
     * @see org.xml.sax.Attributes
     */
    public abstract void unparsedEntityDecl (String name,
                                             String publicId,
                                             String systemId,
                                             String notationName)
        throws SAXException;

}

// end of DTDHandler.java
