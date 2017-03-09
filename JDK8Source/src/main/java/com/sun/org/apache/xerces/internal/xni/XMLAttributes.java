/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2000-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)2000-2002 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得将"Xerces"和"Apache Software Foundation"名称用于支持或推广从本软件衍生的产品。如需书面许可,请联系apache@apache.org。
 * 
 *  未经Apache软件基金会事先书面许可,从本软件派生的产品可能不会被称为"Apache",也不可能出现在他们的名字中。
 * 
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款,最初是基于软件版权(c)1999,国际商业机器公司,http://www.apache.org。
 * 有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

package com.sun.org.apache.xerces.internal.xni;

/**
 * The XMLAttributes interface defines a collection of attributes for
 * an element. In the parser, the document source would scan the entire
 * start element and collect the attributes. The attributes are
 * communicated to the document handler in the startElement method.
 * <p>
 * The attributes are read-write so that subsequent stages in the document
 * pipeline can modify the values or change the attributes that are
 * propogated to the next stage.
 *
 * <p>
 *  XMLAttributes接口定义元素的属性集合。在解析器中,文档源将扫描整个start元素并收集属性。属性在startElement方法中传递给文档处理程序。
 * <p>
 * 属性是读写的,以便文档管道中的后续阶段可以修改值或更改传播到下一个阶段的属性。
 * 
 * 
 * @see XMLDocumentHandler#startElement
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLAttributes {

    //
    // XMLAttributes methods
    //

    /**
     * Adds an attribute. The attribute's non-normalized value of the
     * attribute will have the same value as the attribute value until
     * set using the <code>setNonNormalizedValue</code> method. Also,
     * the added attribute will be marked as specified in the XML instance
     * document unless set otherwise using the <code>setSpecified</code>
     * method.
     * <p>
     * <strong>Note:</strong> If an attribute of the same name already
     * exists, the old values for the attribute are replaced by the new
     * values.
     *
     * <p>
     *  添加属性。属性的非标准化属性值将与属性值具有相同的值,直到使用<code> setNonNormalizedValue </code>方法设置。
     * 此外,添加的属性将被标记为在XML实例文档中指定,除非使用<code> setSpecified </code>方法设置。
     * <p>
     *  <strong>注意：</strong>如果同名的属性已存在,则该属性的旧值将替换为新值。
     * 
     * 
     * @param attrName  The attribute name.
     * @param attrType  The attribute type. The type name is determined by
     *                  the type specified for this attribute in the DTD.
     *                  For example: "CDATA", "ID", "NMTOKEN", etc. However,
     *                  attributes of type enumeration will have the type
     *                  value specified as the pipe ('|') separated list of
     *                  the enumeration values prefixed by an open
     *                  parenthesis and suffixed by a close parenthesis.
     *                  For example: "(true|false)".
     * @param attrValue The attribute value.
     *
     * @return Returns the attribute index.
     *
     * @see #setNonNormalizedValue
     * @see #setSpecified
     */
    public int addAttribute(QName attrName, String attrType, String attrValue);

    /**
     * Removes all of the attributes. This method will also remove all
     * entities associated to the attributes.
     * <p>
     *  删除所有属性。此方法还将删除与属性关联的所有实体。
     * 
     */
    public void removeAllAttributes();

    /**
     * Removes the attribute at the specified index.
     * <p>
     * <strong>Note:</strong> This operation changes the indexes of all
     * attributes following the attribute at the specified index.
     *
     * <p>
     *  删除指定索引处的属性。
     * <p>
     *  <strong>注意</strong>：此操作将更改指定索引处属性后面的所有属性的索引。
     * 
     * 
     * @param attrIndex The attribute index.
     */
    public void removeAttributeAt(int attrIndex);

    /**
     * Returns the number of attributes in the list.
     * <p>
     * Once you know the number of attributes, you can iterate
     * through the list.
     *
     * <p>
     *  返回列表中的属性数。
     * <p>
     *  一旦知道属性的数量,就可以遍历列表。
     * 
     * 
     * @see #getURI(int)
     * @see #getLocalName(int)
     * @see #getQName(int)
     * @see #getType(int)
     * @see #getValue(int)
     */
    public int getLength();

    /**
     * Look up the index of an attribute by XML 1.0 qualified name.
     *
     * <p>
     *  通过XML 1.0限定名查找属性的索引。
     * 
     * 
     * @param qName The qualified (prefixed) name.
     *
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndex(String qName);

    /**
     * Look up the index of an attribute by Namespace name.
     *
     * <p>
     *  按名称空间名称查找属性的索引。
     * 
     * 
     * @param uri       The Namespace URI, or the empty string if
     *                  the name has no Namespace URI.
     * @param localName The attribute's local name.
     *
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndex(String uri, String localPart);

    /**
     * Sets the name of the attribute at the specified index.
     *
     * <p>
     *  设置指定索引处的属性的名称。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param attrName  The new attribute name.
     */
    public void setName(int attrIndex, QName attrName);

    /**
     * Sets the fields in the given QName structure with the values
     * of the attribute name at the specified index.
     *
     * <p>
     *  使用指定索引处的属性名称的值设置给定QName结构中的字段。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param attrName  The attribute name structure to fill in.
     */
    public void getName(int attrIndex, QName attrName);

    /**
     * Returns the prefix of the attribute at the specified index.
     *
     * <p>
     *  返回指定索引处的属性的前缀。
     * 
     * 
     * @param index The index of the attribute.
     */
    public String getPrefix(int index);

    /**
     * Look up an attribute's Namespace URI by index.
     *
     * <p>
     *  按索引查找属性的命名空间URI。
     * 
     * 
     * @param index The attribute index (zero-based).
     *
     * @return The Namespace URI, or the empty string if none
     *         is available, or null if the index is out of
     *         range.
     *
     * @see #getLength
     */
    public String getURI(int index);

    /**
     * Look up an attribute's local name by index.
     *
     * <p>
     *  按索引查找属性的本地名称。
     * 
     * 
     * @param index The attribute index (zero-based).
     *
     * @return The local name, or the empty string if Namespace
     *         processing is not being performed, or null
     *         if the index is out of range.
     *
     * @see #getLength
     */
    public String getLocalName(int index);

    /**
     * Look up an attribute's XML 1.0 qualified name by index.
     *
     * <p>
     * 通过索引查找属性的XML 1.0限定名称。
     * 
     * 
     * @param index The attribute index (zero-based).
     *
     * @return The XML 1.0 qualified name, or the empty string
     *         if none is available, or null if the index
     *         is out of range.
     *
     * @see #getLength
     */
    public String getQName(int index);

    //why the above method doens't return QName ?
    public QName getQualifiedName(int index);


    /**
     * Sets the type of the attribute at the specified index.
     *
     * <p>
     *  设置指定索引处的属性类型。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param attrType  The attribute type. The type name is determined by
     *                  the type specified for this attribute in the DTD.
     *                  For example: "CDATA", "ID", "NMTOKEN", etc. However,
     *                  attributes of type enumeration will have the type
     *                  value specified as the pipe ('|') separated list of
     *                  the enumeration values prefixed by an open
     *                  parenthesis and suffixed by a close parenthesis.
     *                  For example: "(true|false)".
     */
    public void setType(int attrIndex, String attrType);

    /**
     * Look up an attribute's type by index.
     * <p>
     * The attribute type is one of the strings "CDATA", "ID",
     * "IDREF", "IDREFS", "NMTOKEN", "NMTOKENS", "ENTITY", "ENTITIES",
     * or "NOTATION" (always in upper case).
     * <p>
     * If the parser has not read a declaration for the attribute,
     * or if the parser does not report attribute types, then it must
     * return the value "CDATA" as stated in the XML 1.0 Recommentation
     * (clause 3.3.3, "Attribute-Value Normalization").
     * <p>
     * For an enumerated attribute that is not a notation, the
     * parser will report the type as "NMTOKEN".
     *
     * <p>
     *  按索引查找属性的类型。
     * <p>
     *  属性类型是字符串"CDATA","ID","IDREF","IDREFS","NMTOKEN","NMTOKENS","ENTITY","ENTITIES"或"注释"(总是大写) 。
     * <p>
     *  如果解析器没有读取属性的声明,或者解析器没有报告属性类型,那么它必须返回值"CDATA",如XML 1.0 Recommentation中所述(第3.3.3节"属性值规范化" )。
     * <p>
     *  对于不是符号的枚举属性,解析器将报告类型为"NMTOKEN"。
     * 
     * 
     * @param index The attribute index (zero-based).
     *
     * @return The attribute's type as a string, or null if the
     *         index is out of range.
     *
     * @see #getLength
     */
    public String getType(int index);

    /**
     * Look up an attribute's type by XML 1.0 qualified name.
     * <p>
     * See {@link #getType(int) getType(int)} for a description
     * of the possible types.
     *
     * <p>
     *  通过XML 1.0限定名称查找属性的类型。
     * <p>
     *  有关可能类型的说明,请参阅{@link #getType(int)getType(int)}。
     * 
     * 
     * @param qName The XML 1.0 qualified name.
     *
     * @return The attribute type as a string, or null if the
     *         attribute is not in the list or if qualified names
     *         are not available.
     */
    public String getType(String qName);

    /**
     * Look up an attribute's type by Namespace name.
     * <p>
     * See {@link #getType(int) getType(int)} for a description
     * of the possible types.
     *
     * <p>
     *  按命名空间名称查找属性的类型。
     * <p>
     *  有关可能类型的说明,请参阅{@link #getType(int)getType(int)}。
     * 
     * 
     * @param uri       The Namespace URI, or the empty String if the
     *                  name has no Namespace URI.
     * @param localName The local name of the attribute.
     *
     * @return The attribute type as a string, or null if the
     *         attribute is not in the list or if Namespace
     *         processing is not being performed.
     */
    public String getType(String uri, String localName);

    /**
     * Sets the value of the attribute at the specified index. This
     * method will overwrite the non-normalized value of the attribute.
     *
     * <p>
     *  设置指定索引处属性的值。此方法将覆盖属性的非标准化值。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param attrValue The new attribute value.
     *
     * @see #setNonNormalizedValue
     */
    public void setValue(int attrIndex, String attrValue);

    public void setValue(int attrIndex, String attrValue, XMLString value);

    /**
     * Look up an attribute's value by index.
     * <p>
     * If the attribute value is a list of tokens (IDREFS,
     * ENTITIES, or NMTOKENS), the tokens will be concatenated
     * into a single string with each token separated by a
     * single space.
     *
     * <p>
     *  按索引查找属性的值。
     * <p>
     *  如果属性值是令牌列表(IDREFS,ENTITIES或NMTOKENS),那么令牌将被并置为单个字符串,每个令牌由单个空格分隔。
     * 
     * 
     * @param index The attribute index (zero-based).
     *
     * @return The attribute's value as a string, or null if the
     *         index is out of range.
     *
     * @see #getLength
     */
    public String getValue(int index);

    /**
     * Look up an attribute's value by XML 1.0 qualified name.
     * <p>
     * See {@link #getValue(int) getValue(int)} for a description
     * of the possible values.
     *
     * <p>
     *  通过XML 1.0限定名查找属性的值。
     * <p>
     *  有关可能值的描述,请参阅{@link #getValue(int)getValue(int)}。
     * 
     * 
     * @param qName The XML 1.0 qualified name.
     *
     * @return The attribute value as a string, or null if the
     *         attribute is not in the list or if qualified names
     *         are not available.
     */
    public String getValue(String qName);

    /**
     * Look up an attribute's value by Namespace name.
     * <p>
     * See {@link #getValue(int) getValue(int)} for a description
     * of the possible values.
     *
     * <p>
     *  按命名空间名称查找属性的值。
     * <p>
     * 有关可能值的描述,请参阅{@link #getValue(int)getValue(int)}。
     * 
     * 
     * @param uri       The Namespace URI, or the empty String if the
     *                  name has no Namespace URI.
     * @param localName The local name of the attribute.
     *
     * @return The attribute value as a string, or null if the
     *         attribute is not in the list.
     */
    public String getValue(String uri, String localName);

    /**
     * Sets the non-normalized value of the attribute at the specified
     * index.
     *
     * <p>
     *  设置指定索引处的属性的非标准化值。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param attrValue The new non-normalized attribute value.
     */
    public void setNonNormalizedValue(int attrIndex, String attrValue);

    /**
     * Returns the non-normalized value of the attribute at the specified
     * index. If no non-normalized value is set, this method will return
     * the same value as the <code>getValue(int)</code> method.
     *
     * <p>
     *  返回指定索引处的属性的非标准化值。如果未设置非标准化值,则此方法将返回与<code> getValue(int)</code>方法相同的值。
     * 
     * 
     * @param attrIndex The attribute index.
     */
    public String getNonNormalizedValue(int attrIndex);

    /**
     * Sets whether an attribute is specified in the instance document
     * or not.
     *
     * <p>
     *  设置是否在实例文档中指定了属性。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param specified True if the attribute is specified in the instance
     *                  document.
     */
    public void setSpecified(int attrIndex, boolean specified);

    /**
     * Returns true if the attribute is specified in the instance document.
     *
     * <p>
     *  如果在实例文档中指定了属性,则返回true。
     * 
     * 
     * @param attrIndex The attribute index.
     */
    public boolean isSpecified(int attrIndex);


    /**
     * Look up an augmentation by attribute's index.
     *
     * <p>
     *  通过属性索引查找扩充。
     * 
     * 
     * @param attributeIndex The attribute index.
     * @return Augmentations
     */
    public Augmentations getAugmentations (int attributeIndex);

    /**
     * Look up an augmentation by namespace name.
     *
     * <p>
     *  通过命名空间名称查找扩充。
     * 
     * 
     * @param uri       The Namespace URI, or the empty string if
     *                  the name has no Namespace URI.
     * @param localPart
     * @return Augmentations
     */
    public Augmentations getAugmentations (String uri, String localPart);


    /**
     * Look up an augmentation by XML 1.0 qualified name.
     * <p>
     *
     * <p>
     *  查找通过XML 1.0限定名称的扩充。
     * <p>
     * 
     * 
     * @param qName The XML 1.0 qualified name.
     *
     * @return Augmentations
     *
     */
    public Augmentations getAugmentations(String qName);

    /**
     * Sets the augmentations of the attribute at the specified index.
     *
     * <p>
     * 
     * @param attrIndex The attribute index.
     * @param augs      The augmentations.
     */
    public void setAugmentations(int attrIndex, Augmentations augs);




} // interface XMLAttributes
