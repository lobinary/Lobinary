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

// SAX Attribute List Interface.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: AttributeList.java,v 1.3 2004/11/03 22:44:51 jsuttor Exp $

package org.xml.sax;

/**
 * Interface for an element's attribute specifications.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This is the original SAX1 interface for reporting an element's
 * attributes.  Unlike the new {@link org.xml.sax.Attributes Attributes}
 * interface, it does not support Namespace-related information.</p>
 *
 * <p>When an attribute list is supplied as part of a
 * {@link org.xml.sax.DocumentHandler#startElement startElement}
 * event, the list will return valid results only during the
 * scope of the event; once the event handler returns control
 * to the parser, the attribute list is invalid.  To save a
 * persistent copy of the attribute list, use the SAX1
 * {@link org.xml.sax.helpers.AttributeListImpl AttributeListImpl}
 * helper class.</p>
 *
 * <p>An attribute list includes only attributes that have been
 * specified or defaulted: #IMPLIED attributes will not be included.</p>
 *
 * <p>There are two ways for the SAX application to obtain information
 * from the AttributeList.  First, it can iterate through the entire
 * list:</p>
 *
 * <pre>
 * public void startElement (String name, AttributeList atts) {
 *   for (int i = 0; i < atts.getLength(); i++) {
 *     String name = atts.getName(i);
 *     String type = atts.getType(i);
 *     String value = atts.getValue(i);
 *     [...]
 *   }
 * }
 * </pre>
 *
 * <p>(Note that the result of getLength() will be zero if there
 * are no attributes.)
 *
 * <p>As an alternative, the application can request the value or
 * type of specific attributes:</p>
 *
 * <pre>
 * public void startElement (String name, AttributeList atts) {
 *   String identifier = atts.getValue("id");
 *   String label = atts.getValue("label");
 *   [...]
 * }
 * </pre>
 *
 * <p>
 *  元素属性规范的接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>这是用于报告元素属性的原始SAX1接口。与新的{@link org.xml.sax.Attributes Attributes}接口不同,它不支持命名空间相关的信息。</p>
 * 
 *  <p>当属性列表作为{@link org.xml.sax.DocumentHandler#startElement startElement}事件的一部分提供时,列表将仅在事件范围内返回有效的结果;一
 * 旦事件处理程序返回控制给解析器,属性列表就无效。
 * 要保存属性列表的持久副本,请使用SAX1 {@link org.xml.sax.helpers.AttributeListImpl AttributeListImpl}帮助器类。</p>。
 * 
 *  <p>属性列表仅包含已指定或默认的属性：#IMPLIED属性将不包含。</p>
 * 
 *  <p> SAX应用程序有两种从AttributeList获取信息的方法。首先,它可以遍历整个列表：</p>
 * 
 * <pre>
 * public void startElement(String name,AttributeList atts){for(int i = 0; i <atts.getLength(); i ++){String name = atts.getName(i); String type = atts.getType(i); String value = atts.getValue(i); [...]}}。
 * </pre>
 * 
 *  <p>(请注意,如果没有属性,getLength()的结果将为零。)
 * 
 *  <p>或者,应用程序可以请求特定属性的值或类型：</p>
 * 
 * <pre>
 *  public void startElement(String name,AttributeList atts){String identifier = atts.getValue("id"); String label = atts.getValue("label"); [...]。
 * </pre>
 * 
 * 
 * @deprecated This interface has been replaced by the SAX2
 *             {@link org.xml.sax.Attributes Attributes}
 *             interface, which includes Namespace support.
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.DocumentHandler#startElement startElement
 * @see org.xml.sax.helpers.AttributeListImpl AttributeListImpl
 */
public interface AttributeList {


    ////////////////////////////////////////////////////////////////////
    // Iteration methods.
    ////////////////////////////////////////////////////////////////////


    /**
     * Return the number of attributes in this list.
     *
     * <p>The SAX parser may provide attributes in any
     * arbitrary order, regardless of the order in which they were
     * declared or specified.  The number of attributes may be
     * zero.</p>
     *
     * <p>
     *  返回此列表中的属性数。
     * 
     *  <p> SAX解析器可以以任何任意顺序提供属性,无论它们被声明或指定的顺序如何。属性数量可以为零。</p>
     * 
     * 
     * @return The number of attributes in the list.
     */
    public abstract int getLength ();


    /**
     * Return the name of an attribute in this list (by position).
     *
     * <p>The names must be unique: the SAX parser shall not include the
     * same attribute twice.  Attributes without values (those declared
     * #IMPLIED without a value specified in the start tag) will be
     * omitted from the list.</p>
     *
     * <p>If the attribute name has a namespace prefix, the prefix
     * will still be attached.</p>
     *
     * <p>
     *  返回此列表中的属性的名称(按位置)。
     * 
     *  <p>名称必须是唯一的：SAX解析器不应包含相同的属性两次。没有值的属性(那些声明为#IMPLIED而没有在开始标记中指定值的属性)将从列表中省略。</p>
     * 
     *  <p>如果属性名称有名称空间前缀,则前缀仍将附加。</p>
     * 
     * 
     * @param i The index of the attribute in the list (starting at 0).
     * @return The name of the indexed attribute, or null
     *         if the index is out of range.
     * @see #getLength
     */
    public abstract String getName (int i);


    /**
     * Return the type of an attribute in the list (by position).
     *
     * <p>The attribute type is one of the strings "CDATA", "ID",
     * "IDREF", "IDREFS", "NMTOKEN", "NMTOKENS", "ENTITY", "ENTITIES",
     * or "NOTATION" (always in upper case).</p>
     *
     * <p>If the parser has not read a declaration for the attribute,
     * or if the parser does not report attribute types, then it must
     * return the value "CDATA" as stated in the XML 1.0 Recommentation
     * (clause 3.3.3, "Attribute-Value Normalization").</p>
     *
     * <p>For an enumerated attribute that is not a notation, the
     * parser will report the type as "NMTOKEN".</p>
     *
     * <p>
     *  返回列表中属性的类型(按位置)。
     * 
     *  <p>属性类型是字符串"CDATA","ID","IDREF","IDREFS","NMTOKEN","NMTOKENS","ENTITY","ENTITIES"或"注释"大写)。</p>
     * 
     * <p>如果解析器没有读取属性的声明,或者解析器没有报告属性类型,则它必须返回值"CDATA",如XML 1.0 Recommentation中所述(第3.3.3节"属性 - 价值规范化")。</p>
     * 
     *  <p>对于不是符号的枚举属性,解析器将报告类型为"NMTOKEN"。</p>
     * 
     * 
     * @param i The index of the attribute in the list (starting at 0).
     * @return The attribute type as a string, or
     *         null if the index is out of range.
     * @see #getLength
     * @see #getType(java.lang.String)
     */
    public abstract String getType (int i);


    /**
     * Return the value of an attribute in the list (by position).
     *
     * <p>If the attribute value is a list of tokens (IDREFS,
     * ENTITIES, or NMTOKENS), the tokens will be concatenated
     * into a single string separated by whitespace.</p>
     *
     * <p>
     *  返回列表中属性的值(按位置)。
     * 
     *  <p>如果属性值是令牌列表(IDREFS,ENTITIES或NMTOKENS),则令牌将连接到由空格分隔的单个字符串中。</p>
     * 
     * 
     * @param i The index of the attribute in the list (starting at 0).
     * @return The attribute value as a string, or
     *         null if the index is out of range.
     * @see #getLength
     * @see #getValue(java.lang.String)
     */
    public abstract String getValue (int i);



    ////////////////////////////////////////////////////////////////////
    // Lookup methods.
    ////////////////////////////////////////////////////////////////////


    /**
     * Return the type of an attribute in the list (by name).
     *
     * <p>The return value is the same as the return value for
     * getType(int).</p>
     *
     * <p>If the attribute name has a namespace prefix in the document,
     * the application must include the prefix here.</p>
     *
     * <p>
     *  返回列表中属性的类型(按名称)。
     * 
     *  <p>返回值与getType(int)的返回值相同。</p>
     * 
     *  <p>如果属性名称在文档中具有命名空间前缀,则应用程序必须在此包含前缀。</p>
     * 
     * 
     * @param name The name of the attribute.
     * @return The attribute type as a string, or null if no
     *         such attribute exists.
     * @see #getType(int)
     */
    public abstract String getType (String name);


    /**
     * Return the value of an attribute in the list (by name).
     *
     * <p>The return value is the same as the return value for
     * getValue(int).</p>
     *
     * <p>If the attribute name has a namespace prefix in the document,
     * the application must include the prefix here.</p>
     *
     * <p>
     *  返回列表中属性的值(按名称)。
     * 
     *  <p>返回值与getValue(int)的返回值相同。</p>
     * 
     * @param name the name of the attribute to return
     * @return The attribute value as a string, or null if
     *         no such attribute exists.
     * @see #getValue(int)
     */
    public abstract String getValue (String name);

}

// end of AttributeList.java
