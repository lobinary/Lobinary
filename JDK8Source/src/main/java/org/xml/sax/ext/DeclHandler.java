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

// DeclHandler.java - Optional handler for DTD declaration events.
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: DeclHandler.java,v 1.2 2004/11/03 22:49:08 jsuttor Exp $

package org.xml.sax.ext;

import org.xml.sax.SAXException;


/**
 * SAX2 extension handler for DTD declaration events.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This is an optional extension handler for SAX2 to provide more
 * complete information about DTD declarations in an XML document.
 * XML readers are not required to recognize this handler, and it
 * is not part of core-only SAX2 distributions.</p>
 *
 * <p>Note that data-related DTD declarations (unparsed entities and
 * notations) are already reported through the {@link
 * org.xml.sax.DTDHandler DTDHandler} interface.</p>
 *
 * <p>If you are using the declaration handler together with a lexical
 * handler, all of the events will occur between the
 * {@link org.xml.sax.ext.LexicalHandler#startDTD startDTD} and the
 * {@link org.xml.sax.ext.LexicalHandler#endDTD endDTD} events.</p>
 *
 * <p>To set the DeclHandler for an XML reader, use the
 * {@link org.xml.sax.XMLReader#setProperty setProperty} method
 * with the property name
 * <code>http://xml.org/sax/properties/declaration-handler</code>
 * and an object implementing this interface (or null) as the value.
 * If the reader does not report declaration events, it will throw a
 * {@link org.xml.sax.SAXNotRecognizedException SAXNotRecognizedException}
 * when you attempt to register the handler.</p>
 *
 * <p>
 *  用于DTD声明事件的SAX2扩展处理程序。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>这是一个可选的扩展处理程序,用于SAX2在XML文档中提供有关DTD声明的更完整信息。 XML阅读器不需要识别此处理程序,它不是仅核心SAX2分发的一部分。</p>
 * 
 *  <p>请注意,与数据相关的DTD声明(未解析的实体和符号)已通过{@link org.xml.sax.DTDHandler DTDHandler}界面进行报告。</p>
 * 
 *  <p>如果您将声明处理程序与词法处理程序一起使用,则所有事件将发生在{@link org.xml.sax.ext.LexicalHandler#startDTD startDTD}和{@link org.xml.sax .ext.LexicalHandler#endDTD endDTD}
 *  events。
 * </p>。
 * 
 * <p>要为XML阅读器设置DeclHandler,请使用{@link org.xml.sax.XMLReader#setProperty setProperty}方法,其属性名为<code> http:
 * //xml.org/sax/properties/declaration-处理程序</code>和实现此接口的对象(或null)作为值。
 * 如果读者没有报告声明事件,当您尝试注册处理程序时,它将抛出{@link org.xml.sax.SAXNotRecognizedException SAXNotRecognizedException}。
 * </p>。
 * 
 * 
 * @since SAX 2.0 (extensions 1.0)
 * @author David Megginson
 */
public interface DeclHandler
{

    /**
     * Report an element type declaration.
     *
     * <p>The content model will consist of the string "EMPTY", the
     * string "ANY", or a parenthesised group, optionally followed
     * by an occurrence indicator.  The model will be normalized so
     * that all parameter entities are fully resolved and all whitespace
     * is removed,and will include the enclosing parentheses.  Other
     * normalization (such as removing redundant parentheses or
     * simplifying occurrence indicators) is at the discretion of the
     * parser.</p>
     *
     * <p>
     *  报告元素类型声明。
     * 
     *  <p>内容模型将包含字符串"EMPTY",字符串"ANY"或括号组,可选后跟一个事件指示符。模型将被规范化,以便所有参数实体完全解析,并删除所有空格,并将包括括号括号。
     * 其他规范化(例如删除冗余括号或简化发生指示符)由解析器自行决定。</p>。
     * 
     * 
     * @param name The element type name.
     * @param model The content model as a normalized string.
     * @exception SAXException The application may raise an exception.
     */
    public abstract void elementDecl (String name, String model)
        throws SAXException;


    /**
     * Report an attribute type declaration.
     *
     * <p>Only the effective (first) declaration for an attribute will
     * be reported.  The type will be one of the strings "CDATA",
     * "ID", "IDREF", "IDREFS", "NMTOKEN", "NMTOKENS", "ENTITY",
     * "ENTITIES", a parenthesized token group with
     * the separator "|" and all whitespace removed, or the word
     * "NOTATION" followed by a space followed by a parenthesized
     * token group with all whitespace removed.</p>
     *
     * <p>The value will be the value as reported to applications,
     * appropriately normalized and with entity and character
     * references expanded.  </p>
     *
     * <p>
     *  报告属性类型声明。
     * 
     *  <p>只会报告属性的有效(第一)声明。
     * 类型将是字符串"CDATA","ID","IDREF","IDREFS","NMTOKEN","NMTOKENS","ENTITY","ENTITIES",带分隔符"|"并删除所有空格,或单词"注释"后
     * 跟一个空格,后面跟着一个括号中的标记组,删除所有空格。
     *  <p>只会报告属性的有效(第一)声明。</p>。
     * 
     * <p>该值将是报告给应用程序的值,适当规范化,并扩展实体和字符引用。 </p>
     * 
     * 
     * @param eName The name of the associated element.
     * @param aName The name of the attribute.
     * @param type A string representing the attribute type.
     * @param mode A string representing the attribute defaulting mode
     *        ("#IMPLIED", "#REQUIRED", or "#FIXED") or null if
     *        none of these applies.
     * @param value A string representing the attribute's default value,
     *        or null if there is none.
     * @exception SAXException The application may raise an exception.
     */
    public abstract void attributeDecl (String eName,
                                        String aName,
                                        String type,
                                        String mode,
                                        String value)
        throws SAXException;


    /**
     * Report an internal entity declaration.
     *
     * <p>Only the effective (first) declaration for each entity
     * will be reported.  All parameter entities in the value
     * will be expanded, but general entities will not.</p>
     *
     * <p>
     *  报告内部实体声明。
     * 
     *  <p>只会报告每个实体的有效(第一)声明。值中的所有参数实体都将展开,但一般实体不会展开。</p>
     * 
     * 
     * @param name The name of the entity.  If it is a parameter
     *        entity, the name will begin with '%'.
     * @param value The replacement text of the entity.
     * @exception SAXException The application may raise an exception.
     * @see #externalEntityDecl
     * @see org.xml.sax.DTDHandler#unparsedEntityDecl
     */
    public abstract void internalEntityDecl (String name, String value)
        throws SAXException;


    /**
     * Report a parsed external entity declaration.
     *
     * <p>Only the effective (first) declaration for each entity
     * will be reported.</p>
     *
     * <p>If the system identifier is a URL, the parser must resolve it
     * fully before passing it to the application.</p>
     *
     * <p>
     *  报告解析的外部实体声明。
     * 
     *  <p>只会报告每个实体的有效(第一)声明。</p>
     * 
     * @param name The name of the entity.  If it is a parameter
     *        entity, the name will begin with '%'.
     * @param publicId The entity's public identifier, or null if none
     *        was given.
     * @param systemId The entity's system identifier.
     * @exception SAXException The application may raise an exception.
     * @see #internalEntityDecl
     * @see org.xml.sax.DTDHandler#unparsedEntityDecl
     */
    public abstract void externalEntityDecl (String name, String publicId,
                                             String systemId)
        throws SAXException;

}

// end of DeclHandler.java
