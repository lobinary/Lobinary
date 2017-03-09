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

// Attributes.java - attribute list with Namespace support
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the public domain.
// $Id: Attributes.java,v 1.2 2004/11/03 22:44:51 jsuttor Exp $

package org.xml.sax;


/**
 * Interface for a list of XML attributes.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This interface allows access to a list of attributes in
 * three different ways:</p>
 *
 * <ol>
 * <li>by attribute index;</li>
 * <li>by Namespace-qualified name; or</li>
 * <li>by qualified (prefixed) name.</li>
 * </ol>
 *
 * <p>The list will not contain attributes that were declared
 * #IMPLIED but not specified in the start tag.  It will also not
 * contain attributes used as Namespace declarations (xmlns*) unless
 * the <code>http://xml.org/sax/features/namespace-prefixes</code>
 * feature is set to <var>true</var> (it is <var>false</var> by
 * default).
 * Because SAX2 conforms to the original "Namespaces in XML"
 * recommendation, it normally does not
 * give namespace declaration attributes a namespace URI.
 * </p>
 *
 * <p>Some SAX2 parsers may support using an optional feature flag
 * (<code>http://xml.org/sax/features/xmlns-uris</code>) to request
 * that those attributes be given URIs, conforming to a later
 * backwards-incompatible revision of that recommendation.  (The
 * attribute's "local name" will be the prefix, or "xmlns" when
 * defining a default element namespace.)  For portability, handler
 * code should always resolve that conflict, rather than requiring
 * parsers that can change the setting of that feature flag.  </p>
 *
 * <p>If the namespace-prefixes feature (see above) is
 * <var>false</var>, access by qualified name may not be available; if
 * the <code>http://xml.org/sax/features/namespaces</code> feature is
 * <var>false</var>, access by Namespace-qualified names may not be
 * available.</p>
 *
 * <p>This interface replaces the now-deprecated SAX1 {@link
 * org.xml.sax.AttributeList AttributeList} interface, which does not
 * contain Namespace support.  In addition to Namespace support, it
 * adds the <var>getIndex</var> methods (below).</p>
 *
 * <p>The order of attributes in the list is unspecified, and will
 * vary from implementation to implementation.</p>
 *
 * <p>
 *  用于XML属性列表的接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保修</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此界面允许以三种不同的方式访问属性列表：</p>
 * 
 * <ol>
 *  <li>按属性索引; </li> <li>按名称空间限定名称;或</li> <li>以限定(前缀)名称。</li>
 * </ol>
 * 
 *  <p>此列表不包含已声明为#IMPLIED但未在开始标记中指定的属性。
 * 除非将<code> http://xml.org/sax/features/namespace-prefixes </code>特性设置为<var> true </var>,否则它不会包含用作命名空间声
 * 明(xmlns * (默认情况下为<var> false </var>)。
 *  <p>此列表不包含已声明为#IMPLIED但未在开始标记中指定的属性。因为SAX2符合原始的"XML中的命名空间"建议,所以它通常不给命名空间声明属性命名空间URI。
 * </p>
 * 
 * <p>一些SAX2解析器可能支持使用可选的功能标志(<code> http://xml.org/sax/features/xmlns-uris </code>)来请求这些属性被赋予URI,符合以后的向后
 * 不相容的修订。
 *  (当定义默认元素命名空间时,属性的"本地名称"将是前缀,或者"xmlns"。)为了可移植性,处理程序代码应该总是解决冲突,而不是需要可以更改该功能标记设置的解析器。 </p>。
 * 
 *  <p>如果命名空间前缀功能(见上文)为<var> false </var>,则可能无法使用限定名称访问;如果<code> http://xml.org/sax/features/namespaces 
 * </code>功能是<var> false </var>,则可能无法使用命名空间限定名称。
 * </p>。
 * 
 *  <p>此接口替换了现在已弃用的SAX1 {@link org.xml.sax.AttributeList AttributeList}接口,该接口不包含命名空间支持。
 * 除了支持命名空间之外,它还添加了<var> getIndex </var>方法(下面)。</p>。
 * 
 *  <p>列表中属性的顺序未指定,因实施方式而异。</p>
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.helpers.AttributesImpl
 * @see org.xml.sax.ext.DeclHandler#attributeDecl
 */
public interface Attributes
{


    ////////////////////////////////////////////////////////////////////
    // Indexed access.
    ////////////////////////////////////////////////////////////////////


    /**
     * Return the number of attributes in the list.
     *
     * <p>Once you know the number of attributes, you can iterate
     * through the list.</p>
     *
     * <p>
     *  返回列表中的属性数。
     * 
     *  <p>一旦知道属性的数量,就可以遍历列表。</p>
     * 
     * 
     * @return The number of attributes in the list.
     * @see #getURI(int)
     * @see #getLocalName(int)
     * @see #getQName(int)
     * @see #getType(int)
     * @see #getValue(int)
     */
    public abstract int getLength ();


    /**
     * Look up an attribute's Namespace URI by index.
     *
     * <p>
     *  按索引查找属性的命名空间URI。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The Namespace URI, or the empty string if none
     *         is available, or null if the index is out of
     *         range.
     * @see #getLength
     */
    public abstract String getURI (int index);


    /**
     * Look up an attribute's local name by index.
     *
     * <p>
     *  按索引查找属性的本地名称。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The local name, or the empty string if Namespace
     *         processing is not being performed, or null
     *         if the index is out of range.
     * @see #getLength
     */
    public abstract String getLocalName (int index);


    /**
     * Look up an attribute's XML qualified (prefixed) name by index.
     *
     * <p>
     *  通过索引查找属性的XML限定(前缀)名称。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The XML qualified name, or the empty string
     *         if none is available, or null if the index
     *         is out of range.
     * @see #getLength
     */
    public abstract String getQName (int index);


    /**
     * Look up an attribute's type by index.
     *
     * <p>The attribute type is one of the strings "CDATA", "ID",
     * "IDREF", "IDREFS", "NMTOKEN", "NMTOKENS", "ENTITY", "ENTITIES",
     * or "NOTATION" (always in upper case).</p>
     *
     * <p>If the parser has not read a declaration for the attribute,
     * or if the parser does not report attribute types, then it must
     * return the value "CDATA" as stated in the XML 1.0 Recommendation
     * (clause 3.3.3, "Attribute-Value Normalization").</p>
     *
     * <p>For an enumerated attribute that is not a notation, the
     * parser will report the type as "NMTOKEN".</p>
     *
     * <p>
     *  按索引查找属性的类型。
     * 
     * <p>属性类型是字符串"CDATA","ID","IDREF","IDREFS","NMTOKEN","NMTOKENS","ENTITY","ENTITIES"或"注释"大写)。</p>
     * 
     *  <p>如果解析器没有读取属性的声明,或者解析器没有报告属性类型,那么它必须返回值"CDATA",如XML 1.0建议书(第3.3.3节"属性 - 价值规范化")。</p>
     * 
     *  <p>对于不是符号的枚举属性,解析器将报告类型为"NMTOKEN"。</p>
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The attribute's type as a string, or null if the
     *         index is out of range.
     * @see #getLength
     */
    public abstract String getType (int index);


    /**
     * Look up an attribute's value by index.
     *
     * <p>If the attribute value is a list of tokens (IDREFS,
     * ENTITIES, or NMTOKENS), the tokens will be concatenated
     * into a single string with each token separated by a
     * single space.</p>
     *
     * <p>
     *  按索引查找属性的值。
     * 
     *  <p>如果属性值是令牌列表(IDREFS,ENTITIES或NMTOKENS),则令牌将被并置为单个字符串,每个令牌由单个空格分隔。</p>
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The attribute's value as a string, or null if the
     *         index is out of range.
     * @see #getLength
     */
    public abstract String getValue (int index);



    ////////////////////////////////////////////////////////////////////
    // Name-based query.
    ////////////////////////////////////////////////////////////////////


    /**
     * Look up the index of an attribute by Namespace name.
     *
     * <p>
     *  按名称空间名称查找属性的索引。
     * 
     * 
     * @param uri The Namespace URI, or the empty string if
     *        the name has no Namespace URI.
     * @param localName The attribute's local name.
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndex (String uri, String localName);


    /**
     * Look up the index of an attribute by XML qualified (prefixed) name.
     *
     * <p>
     *  通过XML限定(前缀)名称查找属性的索引。
     * 
     * 
     * @param qName The qualified (prefixed) name.
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndex (String qName);


    /**
     * Look up an attribute's type by Namespace name.
     *
     * <p>See {@link #getType(int) getType(int)} for a description
     * of the possible types.</p>
     *
     * <p>
     *  按命名空间名称查找属性的类型。
     * 
     *  <p>有关可能类型的说明,请参阅{@link #getType(int)getType(int)}。</p>
     * 
     * 
     * @param uri The Namespace URI, or the empty String if the
     *        name has no Namespace URI.
     * @param localName The local name of the attribute.
     * @return The attribute type as a string, or null if the
     *         attribute is not in the list or if Namespace
     *         processing is not being performed.
     */
    public abstract String getType (String uri, String localName);


    /**
     * Look up an attribute's type by XML qualified (prefixed) name.
     *
     * <p>See {@link #getType(int) getType(int)} for a description
     * of the possible types.</p>
     *
     * <p>
     *  通过XML限定(前缀)名称查找属性的类型。
     * 
     *  <p>有关可能类型的说明,请参阅{@link #getType(int)getType(int)}。</p>
     * 
     * 
     * @param qName The XML qualified name.
     * @return The attribute type as a string, or null if the
     *         attribute is not in the list or if qualified names
     *         are not available.
     */
    public abstract String getType (String qName);


    /**
     * Look up an attribute's value by Namespace name.
     *
     * <p>See {@link #getValue(int) getValue(int)} for a description
     * of the possible values.</p>
     *
     * <p>
     *  按命名空间名称查找属性的值。
     * 
     *  <p>有关可能值的说明,请参阅{@link #getValue(int)getValue(int)}。</p>
     * 
     * 
     * @param uri The Namespace URI, or the empty String if the
     *        name has no Namespace URI.
     * @param localName The local name of the attribute.
     * @return The attribute value as a string, or null if the
     *         attribute is not in the list.
     */
    public abstract String getValue (String uri, String localName);


    /**
     * Look up an attribute's value by XML qualified (prefixed) name.
     *
     * <p>See {@link #getValue(int) getValue(int)} for a description
     * of the possible values.</p>
     *
     * <p>
     *  通过XML限定(前缀)名称查找属性的值。
     * 
     * 
     * @param qName The XML qualified name.
     * @return The attribute value as a string, or null if the
     *         attribute is not in the list or if qualified names
     *         are not available.
     */
    public abstract String getValue (String qName);

}

// end of Attributes.java
