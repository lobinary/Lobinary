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

package com.sun.org.apache.xerces.internal.util;

import com.sun.xml.internal.stream.XMLBufferListener;
import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.XMLString;
/**
 * The XMLAttributesImpl class is an implementation of the XMLAttributes
 * interface which defines a collection of attributes for an element.
 * In the parser, the document source would scan the entire start element
 * and collect the attributes. The attributes are communicated to the
 * document handler in the startElement method.
 * <p>
 * The attributes are read-write so that subsequent stages in the document
 * pipeline can modify the values or change the attributes that are
 * propogated to the next stage.
 *
 * <p>
 *  XMLAttributesImpl类是XMLAttributes接口的一个实现,它定义了一个元素的属性集合。在解析器中,文档源将扫描整个start元素并收集属性。
 * 属性在startElement方法中传递给文档处理程序。
 * <p>
 * 属性是读写的,以便文档管道中的后续阶段可以修改值或更改传播到下一个阶段的属性。
 * 
 * 
 * @see com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler#startElement
 *
 * @author Andy Clark, IBM
 * @author Elena Litani, IBM
 * @author Michael Glavassevich, IBM
 *
 * @version $Id: XMLAttributesImpl.java,v 1.7 2010/05/07 20:13:09 joehw Exp $
 */
public class XMLAttributesImpl
implements XMLAttributes, XMLBufferListener {

    //
    // Constants
    //

    /** Default table size. */
    protected static final int TABLE_SIZE = 101;

    /**
     * Threshold at which an instance is treated
     * as a large attribute list.
     * <p>
     *  将实例视为大属性列表的阈值。
     * 
     */
    protected static final int SIZE_LIMIT = 20;

    //
    // Data
    //

    // features

    /** Namespaces. */
    protected boolean fNamespaces = true;

    // data

    /**
     * Usage count for the attribute table view.
     * Incremented each time all attributes are removed
     * when the attribute table view is in use.
     * <p>
     *  属性表视图的使用计数。在使用属性表视图时每次删除所有属性时增加。
     * 
     */
    protected int fLargeCount = 1;

    /** Attribute count. */
    protected int fLength;

    /** Attribute information. */
    protected Attribute[] fAttributes = new Attribute[4];

    /**
     * Hashtable of attribute information.
     * Provides an alternate view of the attribute specification.
     * <p>
     *  属性信息的哈希表。提供属性规范的备用视图。
     * 
     */
    protected Attribute[] fAttributeTableView;

    /**
     * Tracks whether each chain in the hash table is stale
     * with respect to the current state of this object.
     * A chain is stale if its state is not the same as the number
     * of times the attribute table view has been used.
     * <p>
     *  跟踪散列表中的每个链是否相对于此对象的当前状态过时。如果链的状态与属性表视图的使用次数不同,则链将失效。
     * 
     */
    protected int[] fAttributeTableViewChainState;

    /**
     * Actual number of buckets in the table view.
     * <p>
     *  表视图中的实际桶数。
     * 
     */
    protected int fTableViewBuckets;

    /**
     * Indicates whether the table view contains consistent data.
     * <p>
     *  指示表视图是否包含一致的数据。
     * 
     */
    protected boolean fIsTableViewConsistent;

    //
    // Constructors
    //

    /** Default constructor. */
    public XMLAttributesImpl() {
        this(TABLE_SIZE);
    }

    /**
    /* <p>
    /* 
     * @param tableSize initial size of table view
     */
    public XMLAttributesImpl(int tableSize) {
        fTableViewBuckets = tableSize;
        for (int i = 0; i < fAttributes.length; i++) {
            fAttributes[i] = new Attribute();
        }
    } // <init>()

    //
    // Public methods
    //

    /**
     * Sets whether namespace processing is being performed. This state
     * is needed to return the correct value from the getLocalName method.
     *
     * <p>
     *  设置是否正在执行命名空间处理。需要此状态才能从getLocalName方法返回正确的值。
     * 
     * 
     * @param namespaces True if namespace processing is turned on.
     *
     * @see #getLocalName
     */
    public void setNamespaces(boolean namespaces) {
        fNamespaces = namespaces;
    } // setNamespaces(boolean)

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
     * @param name  The attribute name.
     * @param type  The attribute type. The type name is determined by
     *                  the type specified for this attribute in the DTD.
     *                  For example: "CDATA", "ID", "NMTOKEN", etc. However,
     *                  attributes of type enumeration will have the type
     *                  value specified as the pipe ('|') separated list of
     *                  the enumeration values prefixed by an open
     *                  parenthesis and suffixed by a close parenthesis.
     *                  For example: "(true|false)".
     * @param value The attribute value.
     *
     * @return Returns the attribute index.
     *
     * @see #setNonNormalizedValue
     * @see #setSpecified
     */
    public int addAttribute(QName name, String type, String value) {
      return addAttribute(name,type,value,null);
    }
    public int addAttribute(QName name, String type, String value,XMLString valueCache) {

        int index;
        if (fLength < SIZE_LIMIT) {
            index = name.uri != null && !name.uri.equals("")
                ? getIndexFast(name.uri, name.localpart)
                : getIndexFast(name.rawname);

            if (index == -1) {
                index = fLength;
                if (fLength++ == fAttributes.length) {
                    Attribute[] attributes = new Attribute[fAttributes.length + 4];
                    System.arraycopy(fAttributes, 0, attributes, 0, fAttributes.length);
                    for (int i = fAttributes.length; i < attributes.length; i++) {
                        attributes[i] = new Attribute();
                    }
                    fAttributes = attributes;
                }
            }
        }
        else if (name.uri == null ||
            name.uri.length() == 0 ||
            (index = getIndexFast(name.uri, name.localpart)) == -1) {

            /**
             * If attributes were removed from the list after the table
             * becomes in use this isn't reflected in the table view. It's
             * assumed that once a user starts removing attributes they're
             * not likely to add more. We only make the view consistent if
             * the user of this class adds attributes, removes them, and
             * then adds more.
             * <p>
             * 如果在表使用后从列表中删除属性,则这不会反映在表视图中。假设一旦用户开始删除属性,他们不可能添加更多。如果此类的用户添加属性,删除它们,然后添加更多,我们只会使视图一致。
             * 
             */
            if (!fIsTableViewConsistent || fLength == SIZE_LIMIT) {
                prepareAndPopulateTableView();
                fIsTableViewConsistent = true;
            }

            int bucket = getTableViewBucket(name.rawname);

            // The chain is stale.
            // This must be a unique attribute.
            if (fAttributeTableViewChainState[bucket] != fLargeCount) {
                index = fLength;
                if (fLength++ == fAttributes.length) {
                    Attribute[] attributes = new Attribute[fAttributes.length << 1];
                    System.arraycopy(fAttributes, 0, attributes, 0, fAttributes.length);
                    for (int i = fAttributes.length; i < attributes.length; i++) {
                        attributes[i] = new Attribute();
                    }
                    fAttributes = attributes;
                }

                // Update table view.
                fAttributeTableViewChainState[bucket] = fLargeCount;
                fAttributes[index].next = null;
                fAttributeTableView[bucket] = fAttributes[index];
            }
            // This chain is active.
            // We need to check if any of the attributes has the same rawname.
            else {
                // Search the table.
                Attribute found = fAttributeTableView[bucket];
                while (found != null) {
                    if (found.name.rawname == name.rawname) {
                        break;
                    }
                    found = found.next;
                }
                // This attribute is unique.
                if (found == null) {
                    index = fLength;
                    if (fLength++ == fAttributes.length) {
                        Attribute[] attributes = new Attribute[fAttributes.length << 1];
                        System.arraycopy(fAttributes, 0, attributes, 0, fAttributes.length);
                        for (int i = fAttributes.length; i < attributes.length; i++) {
                            attributes[i] = new Attribute();
                        }
                        fAttributes = attributes;
                    }

                    // Update table view
                    fAttributes[index].next = fAttributeTableView[bucket];
                    fAttributeTableView[bucket] = fAttributes[index];
                }
                // Duplicate. We still need to find the index.
                else {
                    index = getIndexFast(name.rawname);
                }
            }
        }

        // set values
        Attribute attribute = fAttributes[index];
        attribute.name.setValues(name);
        attribute.type = type;
        attribute.value = value;
        attribute.xmlValue = valueCache;
        attribute.nonNormalizedValue = value;
        attribute.specified = false;

        // clear augmentations
        if(attribute.augs != null)
            attribute.augs.removeAllItems();

        return index;

    } // addAttribute(QName,String,XMLString)

    /**
     * Removes all of the attributes. This method will also remove all
     * entities associated to the attributes.
     * <p>
     *  删除所有属性。此方法还将删除与属性关联的所有实体。
     * 
     */
    public void removeAllAttributes() {
        fLength = 0;
    } // removeAllAttributes()

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
    public void removeAttributeAt(int attrIndex) {
        fIsTableViewConsistent = false;
        if (attrIndex < fLength - 1) {
            Attribute removedAttr = fAttributes[attrIndex];
            System.arraycopy(fAttributes, attrIndex + 1,
                             fAttributes, attrIndex, fLength - attrIndex - 1);
            // Make the discarded Attribute object available for re-use
            // by tucking it after the Attributes that are still in use
            fAttributes[fLength-1] = removedAttr;
        }
        fLength--;
    } // removeAttributeAt(int)

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
    public void setName(int attrIndex, QName attrName) {
        fAttributes[attrIndex].name.setValues(attrName);
    } // setName(int,QName)

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
    public void getName(int attrIndex, QName attrName) {
        attrName.setValues(fAttributes[attrIndex].name);
    } // getName(int,QName)

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
    public void setType(int attrIndex, String attrType) {
        fAttributes[attrIndex].type = attrType;
    } // setType(int,String)

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
    public void setValue(int attrIndex, String attrValue) {
        setValue(attrIndex,attrValue,null);
    }

    public void setValue(int attrIndex, String attrValue,XMLString value) {
        Attribute attribute = fAttributes[attrIndex];
        attribute.value = attrValue;
        attribute.nonNormalizedValue = attrValue;
        attribute.xmlValue = value;
    } // setValue(int,String)

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
    public void setNonNormalizedValue(int attrIndex, String attrValue) {
        if (attrValue == null) {
            attrValue = fAttributes[attrIndex].value;
        }
        fAttributes[attrIndex].nonNormalizedValue = attrValue;
    } // setNonNormalizedValue(int,String)

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
    public String getNonNormalizedValue(int attrIndex) {
        String value = fAttributes[attrIndex].nonNormalizedValue;
        return value;
    } // getNonNormalizedValue(int):String

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
    public void setSpecified(int attrIndex, boolean specified) {
        fAttributes[attrIndex].specified = specified;
    } // setSpecified(int,boolean)

    /**
     * Returns true if the attribute is specified in the instance document.
     *
     * <p>
     *  如果在实例文档中指定了属性,则返回true。
     * 
     * 
     * @param attrIndex The attribute index.
     */
    public boolean isSpecified(int attrIndex) {
        return fAttributes[attrIndex].specified;
    } // isSpecified(int):boolean

    //
    // AttributeList and Attributes methods
    //

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
     */
    public int getLength() {
        return fLength;
    } // getLength():int

    /**
     * Look up an attribute's type by index.
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
     * 按索引查找属性的类型。
     * 
     *  <p>属性类型是字符串"CDATA","ID","IDREF","IDREFS","NMTOKEN","NMTOKENS","ENTITY","ENTITIES"或"注释"大写)。</p>
     * 
     *  <p>如果解析器没有读取属性的声明,或者解析器没有报告属性类型,则它必须返回值"CDATA",如XML 1.0 Recommentation中所述(第3.3.3节"属性 - 价值规范化")。
     * </p>。
     * 
     *  <p>对于不是符号的枚举属性,解析器将报告类型为"NMTOKEN"。</p>
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The attribute's type as a string, or null if the
     *         index is out of range.
     * @see #getLength
     */
    public String getType(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        return getReportableType(fAttributes[index].type);
    } // getType(int):String

    /**
     * Look up an attribute's type by XML 1.0 qualified name.
     *
     * <p>See {@link #getType(int) getType(int)} for a description
     * of the possible types.</p>
     *
     * <p>
     *  通过XML 1.0限定名称查找属性的类型。
     * 
     *  <p>有关可能类型的说明,请参阅{@link #getType(int)getType(int)}。</p>
     * 
     * 
     * @param qname The XML 1.0 qualified name.
     * @return The attribute type as a string, or null if the
     *         attribute is not in the list or if qualified names
     *         are not available.
     */
    public String getType(String qname) {
        int index = getIndex(qname);
        return index != -1 ? getReportableType(fAttributes[index].type) : null;
    } // getType(String):String

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
    public String getValue(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        if(fAttributes[index].value == null && fAttributes[index].xmlValue != null)
            fAttributes[index].value = fAttributes[index].xmlValue.toString();
        return fAttributes[index].value;
    } // getValue(int):String

    /**
     * Look up an attribute's value by XML 1.0 qualified name.
     *
     * <p>See {@link #getValue(int) getValue(int)} for a description
     * of the possible values.</p>
     *
     * <p>
     *  通过XML 1.0限定名查找属性的值。
     * 
     *  <p>有关可能值的说明,请参阅{@link #getValue(int)getValue(int)}。</p>
     * 
     * 
     * @param qname The XML 1.0 qualified name.
     * @return The attribute value as a string, or null if the
     *         attribute is not in the list or if qualified names
     *         are not available.
     */
    public String getValue(String qname) {
        int index = getIndex(qname);
        if(index == -1 )
            return null;
        if(fAttributes[index].value == null)
            fAttributes[index].value = fAttributes[index].xmlValue.toString();
        return fAttributes[index].value;
    } // getValue(String):String

    //
    // AttributeList methods
    //

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
    public String getName(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        return fAttributes[index].name.rawname;
    } // getName(int):String

    //
    // Attributes methods
    //

    /**
     * Look up the index of an attribute by XML 1.0 qualified name.
     *
     * <p>
     * 通过XML 1.0限定名查找属性的索引。
     * 
     * 
     * @param qName The qualified (prefixed) name.
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndex(String qName) {
        for (int i = 0; i < fLength; i++) {
            Attribute attribute = fAttributes[i];
            if (attribute.name.rawname != null &&
                attribute.name.rawname.equals(qName)) {
                return i;
            }
        }
        return -1;
    } // getIndex(String):int

    /**
     * Look up the index of an attribute by Namespace name.
     *
     * <p>
     *  按名称空间名称查找属性的索引。
     * 
     * 
     * @param uri The Namespace URI, or null if
     *        the name has no Namespace URI.
     * @param localName The attribute's local name.
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndex(String uri, String localPart) {
        for (int i = 0; i < fLength; i++) {
            Attribute attribute = fAttributes[i];
            if (attribute.name.localpart != null &&
                attribute.name.localpart.equals(localPart) &&
                ((uri==attribute.name.uri) ||
            (uri!=null && attribute.name.uri!=null && attribute.name.uri.equals(uri)))) {
                return i;
            }
        }
        return -1;
    } // getIndex(String,String):int

    /**
     * Look up the index of an attribute by local name only,
     * ignoring its namespace.
     *
     * <p>
     *  仅通过本地名称查找属性的索引,忽略其命名空间。
     * 
     * 
     * @param localName The attribute's local name.
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndexByLocalName(String localPart) {
        for (int i = 0; i < fLength; i++) {
            Attribute attribute = fAttributes[i];
            if (attribute.name.localpart != null &&
                attribute.name.localpart.equals(localPart)) {
                return i;
            }
        }
        return -1;
    } // getIndex(String):int

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
    public String getLocalName(int index) {
        if (!fNamespaces) {
            return "";
        }
        if (index < 0 || index >= fLength) {
            return null;
        }
        return fAttributes[index].name.localpart;
    } // getLocalName(int):String

    /**
     * Look up an attribute's XML 1.0 qualified name by index.
     *
     * <p>
     *  通过索引查找属性的XML 1.0限定名称。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The XML 1.0 qualified name, or the empty string
     *         if none is available, or null if the index
     *         is out of range.
     * @see #getLength
     */
    public String getQName(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        String rawname = fAttributes[index].name.rawname;
        return rawname != null ? rawname : "";
    } // getQName(int):String

    public QName getQualifiedName(int index){
        if (index < 0 || index >= fLength) {
            return null;
        }
        return fAttributes[index].name;
    }

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
     * @param uri The Namespace URI, or null if the
     *        name has no Namespace URI.
     * @param localName The local name of the attribute.
     * @return The attribute type as a string, or null if the
     *         attribute is not in the list or if Namespace
     *         processing is not being performed.
     */
    public String getType(String uri, String localName) {
        if (!fNamespaces) {
            return null;
        }
        int index = getIndex(uri, localName);
        return index != -1 ? getType(index) : null;
    } // getType(String,String):String
    /**
     * Look up the index of an attribute by XML 1.0 qualified name.
     * <p>
     * <strong>Note:</strong>
     * This method uses reference comparison, and thus should
     * only be used internally. We cannot use this method in any
     * code exposed to users as they may not pass in unique strings.
     *
     * <p>
     *  通过XML 1.0限定名查找属性的索引。
     * <p>
     *  <strong>注意：</strong>此方法使用参考比较,因此只应在内部使用。我们不能在任何暴露给用户的代码中使用此方法,因为它们不能传入唯一字符串。
     * 
     * 
     * @param qName The qualified (prefixed) name.
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndexFast(String qName) {
        for (int i = 0; i < fLength; ++i) {
            Attribute attribute = fAttributes[i];
            if (attribute.name.rawname == qName) {
                return i;
            }
        }
        return -1;
    } // getIndexFast(String):int

    /**
     * Adds an attribute. The attribute's non-normalized value of the
     * attribute will have the same value as the attribute value until
     * set using the <code>setNonNormalizedValue</code> method. Also,
     * the added attribute will be marked as specified in the XML instance
     * document unless set otherwise using the <code>setSpecified</code>
     * method.
     * <p>
     * This method differs from <code>addAttribute</code> in that it
     * does not check if an attribute of the same name already exists
     * in the list before adding it. In order to improve performance
     * of namespace processing, this method allows uniqueness checks
     * to be deferred until all the namespace information is available
     * after the entire attribute specification has been read.
     * <p>
     * <strong>Caution:</strong> If this method is called it should
     * not be mixed with calls to <code>addAttribute</code> unless
     * it has been determined that all the attribute names are unique.
     *
     * <p>
     *  添加属性。属性的非标准化属性值将与属性值具有相同的值,直到使用<code> setNonNormalizedValue </code>方法设置。
     * 此外,添加的属性将被标记为在XML实例文档中指定,除非使用<code> setSpecified </code>方法设置。
     * <p>
     *  此方法不同于<code> addAttribute </code>,因为它不会检查在添加之前列表中是否存在同名的属性。
     * 为了提高命名空间处理的性能,此方法允许延迟唯一性检查,直到在读取整个属性规范之后所有命名空间信息可用。
     * <p>
     * <strong>警告：</strong>如果调用此方法,则除非已确定所有属性名称都是唯一的,否则不应与调用<code> addAttribute </code>混淆。
     * 
     * 
     * @param name the attribute name
     * @param type the attribute type
     * @param value the attribute value
     *
     * @see #setNonNormalizedValue
     * @see #setSpecified
     * @see #checkDuplicatesNS
     */
    public void addAttributeNS(QName name, String type, String value) {
        int index = fLength;
        if (fLength++ == fAttributes.length) {
            Attribute[] attributes;
            if (fLength < SIZE_LIMIT) {
                attributes = new Attribute[fAttributes.length + 4];
            }
            else {
                attributes = new Attribute[fAttributes.length << 1];
            }
            System.arraycopy(fAttributes, 0, attributes, 0, fAttributes.length);
            for (int i = fAttributes.length; i < attributes.length; i++) {
                attributes[i] = new Attribute();
            }
            fAttributes = attributes;
        }

        // set values
        Attribute attribute = fAttributes[index];
        attribute.name.setValues(name);
        attribute.type = type;
        attribute.value = value;
        attribute.nonNormalizedValue = value;
        attribute.specified = false;

        // clear augmentations
        attribute.augs.removeAllItems();
    }

    /**
     * Checks for duplicate expanded names (local part and namespace name
     * pairs) in the attribute specification. If a duplicate is found its
     * name is returned.
     * <p>
     * This should be called once all the in-scope namespaces for the element
     * enclosing these attributes is known, and after all the attributes
     * have gone through namespace binding.
     *
     * <p>
     *  检查属性规范中重复的扩展名称(本地部分和命名空间名称对)。如果发现重复,则返回其名称。
     * <p>
     *  一旦知道包含这些属性的元素的所有范围内命名空间,并且所有属性都通过命名空间绑定后,应调用此方法。
     * 
     * 
     * @return the name of a duplicate attribute found in the search,
     * otherwise null.
     */
    public QName checkDuplicatesNS() {
        // If the list is small check for duplicates using pairwise comparison.
        if (fLength <= SIZE_LIMIT) {
            for (int i = 0; i < fLength - 1; ++i) {
                Attribute att1 = fAttributes[i];
                for (int j = i + 1; j < fLength; ++j) {
                    Attribute att2 = fAttributes[j];
                    if (att1.name.localpart == att2.name.localpart &&
                        att1.name.uri == att2.name.uri) {
                        return att2.name;
                    }
                }
            }
        }
        // If the list is large check duplicates using a hash table.
        else {
            // We don't want this table view to be read if someone calls
            // addAttribute so we invalidate it up front.
            fIsTableViewConsistent = false;

            prepareTableView();

            Attribute attr;
            int bucket;

            for (int i = fLength - 1; i >= 0; --i) {
                attr = fAttributes[i];
                bucket = getTableViewBucket(attr.name.localpart, attr.name.uri);

                // The chain is stale.
                // This must be a unique attribute.
                if (fAttributeTableViewChainState[bucket] != fLargeCount) {
                    fAttributeTableViewChainState[bucket] = fLargeCount;
                    attr.next = null;
                    fAttributeTableView[bucket] = attr;
                }
                // This chain is active.
                // We need to check if any of the attributes has the same name.
                else {
                    // Search the table.
                    Attribute found = fAttributeTableView[bucket];
                    while (found != null) {
                        if (found.name.localpart == attr.name.localpart &&
                            found.name.uri == attr.name.uri) {
                            return attr.name;
                        }
                        found = found.next;
                    }

                    // Update table view
                    attr.next = fAttributeTableView[bucket];
                    fAttributeTableView[bucket] = attr;
                }
            }
        }
        return null;
    }

    /**
     * Look up the index of an attribute by Namespace name.
     * <p>
     * <strong>Note:</strong>
     * This method uses reference comparison, and thus should
     * only be used internally. We cannot use this method in any
     * code exposed to users as they may not pass in unique strings.
     *
     * <p>
     *  按名称空间名称查找属性的索引。
     * <p>
     *  <strong>注意：</strong>此方法使用参考比较,因此只应在内部使用。我们不能在任何暴露给用户的代码中使用此方法,因为它们不能传入唯一字符串。
     * 
     * 
     * @param uri The Namespace URI, or null if
     *        the name has no Namespace URI.
     * @param localName The attribute's local name.
     * @return The index of the attribute, or -1 if it does not
     *         appear in the list.
     */
    public int getIndexFast(String uri, String localPart) {
        for (int i = 0; i < fLength; ++i) {
            Attribute attribute = fAttributes[i];
            if (attribute.name.localpart == localPart &&
                attribute.name.uri == uri) {
                return i;
            }
        }
        return -1;
    } // getIndexFast(String,String):int

    /**
     * Returns the value passed in or NMTOKEN if it's an enumerated type.
     *
     * <p>
     *  返回传入的值或NMTOKEN,如果它是枚举类型。
     * 
     * 
     * @param type attribute type
     * @return the value passed in or NMTOKEN if it's an enumerated type.
     */
    private String getReportableType(String type) {

        if (type.charAt(0) == '(') {
            return "NMTOKEN";
        }
        return type;
    }

    /**
     * Returns the position in the table view
     * where the given attribute name would be hashed.
     *
     * <p>
     *  返回在表视图中给定属性名称将被哈希处的位置。
     * 
     * 
     * @param qname the attribute name
     * @return the position in the table view where the given attribute
     * would be hashed
     */
    protected int getTableViewBucket(String qname) {
        return (qname.hashCode() & 0x7FFFFFFF) % fTableViewBuckets;
    }

    /**
     * Returns the position in the table view
     * where the given attribute name would be hashed.
     *
     * <p>
     *  返回在表视图中给定属性名称将被哈希处的位置。
     * 
     * 
     * @param localpart the local part of the attribute
     * @param uri the namespace name of the attribute
     * @return the position in the table view where the given attribute
     * would be hashed
     */
    protected int getTableViewBucket(String localpart, String uri) {
        if (uri == null) {
            return (localpart.hashCode() & 0x7FFFFFFF) % fTableViewBuckets;
        }
        else {
            return ((localpart.hashCode() + uri.hashCode())
               & 0x7FFFFFFF) % fTableViewBuckets;
        }
    }

    /**
     * Purges all elements from the table view.
     * <p>
     *  清除表视图中的所有元素。
     * 
     */
    protected void cleanTableView() {
        if (++fLargeCount < 0) {
            // Overflow. We actually need to visit the chain state array.
            if (fAttributeTableViewChainState != null) {
                for (int i = fTableViewBuckets - 1; i >= 0; --i) {
                    fAttributeTableViewChainState[i] = 0;
                }
            }
            fLargeCount = 1;
        }
    }

    /**
     * Prepares the table view of the attributes list for use.
     * <p>
     *  准备要使用的属性列表的表视图。
     * 
     */
    protected void prepareTableView() {
        if (fAttributeTableView == null) {
            fAttributeTableView = new Attribute[fTableViewBuckets];
            fAttributeTableViewChainState = new int[fTableViewBuckets];
        }
        else {
            cleanTableView();
        }
    }

    /**
     * Prepares the table view of the attributes list for use,
     * and populates it with the attributes which have been
     * previously read.
     * <p>
     *  准备要使用的属性列表的表视图,并使用先前读取的属性填充它。
     * 
     */
    protected void prepareAndPopulateTableView() {
        prepareTableView();
        // Need to populate the hash table with the attributes we've scanned so far.
        Attribute attr;
        int bucket;
        for (int i = 0; i < fLength; ++i) {
            attr = fAttributes[i];
            bucket = getTableViewBucket(attr.name.rawname);
            if (fAttributeTableViewChainState[bucket] != fLargeCount) {
                fAttributeTableViewChainState[bucket] = fLargeCount;
                attr.next = null;
                fAttributeTableView[bucket] = attr;
            }
            else {
                // Update table view
                attr.next = fAttributeTableView[bucket];
                fAttributeTableView[bucket] = attr;
            }
        }
    }


    /**
     * Returns the prefix of the attribute at the specified index.
     *
     * <p>
     *  返回指定索引处的属性的前缀。
     * 
     * 
     * @param index The index of the attribute.
     */
    public String getPrefix(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        String prefix = fAttributes[index].name.prefix;
        // REVISIT: The empty string is not entered in the symbol table!
        return prefix != null ? prefix : "";
    } // getPrefix(int):String

    /**
     * Look up an attribute's Namespace URI by index.
     *
     * <p>
     *  按索引查找属性的命名空间URI。
     * 
     * 
     * @param index The attribute index (zero-based).
     * @return The Namespace URI
     * @see #getLength
     */
    public String getURI(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        String uri = fAttributes[index].name.uri;
        return uri;
    } // getURI(int):String

    /**
     * Look up an attribute's value by Namespace name and
     * Local name. If Namespace is null, ignore namespace
     * comparison. If Namespace is "", treat the name as
     * having no Namespace URI.
     *
     * <p>See {@link #getValue(int) getValue(int)} for a description
     * of the possible values.</p>
     *
     * <p>
     * 通过命名空间名称和本地名称查找属性的值。如果Namespace为null,则忽略命名空间比较。如果Namespace是"",将该名称视为没有命名空间URI。
     * 
     *  <p>有关可能值的说明,请参阅{@link #getValue(int)getValue(int)}。</p>
     * 
     * 
     * @param uri The Namespace URI, or null namespaces are ignored.
     * @param localName The local name of the attribute.
     * @return The attribute value as a string, or null if the
     *         attribute is not in the list.
     */
    public String getValue(String uri, String localName) {
        int index = getIndex(uri, localName);
        return index != -1 ? getValue(index) : null;
    } // getValue(String,String):String

    /**
     * Look up an augmentations by Namespace name.
     *
     * <p>
     *  按命名空间名称查找增强。
     * 
     * 
     * @param uri The Namespace URI, or null if the
     * @param localName The local name of the attribute.
     * @return Augmentations
     */
    public Augmentations getAugmentations (String uri, String localName) {
        int index = getIndex(uri, localName);
        return index != -1 ? fAttributes[index].augs : null;
    }

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
    public Augmentations getAugmentations(String qName){
        int index = getIndex(qName);
        return index != -1 ? fAttributes[index].augs : null;
    }



    /**
     * Look up an augmentations by attributes index.
     *
     * <p>
     *  通过属性索引查找增强。
     * 
     * 
     * @param attributeIndex The attribute index.
     * @return Augmentations
     */
    public Augmentations getAugmentations (int attributeIndex){
        if (attributeIndex < 0 || attributeIndex >= fLength) {
            return null;
        }
        return fAttributes[attributeIndex].augs;
    }

    /**
     * Sets the augmentations of the attribute at the specified index.
     *
     * <p>
     *  设置指定索引处属性的扩充。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param augs      The augmentations.
     */
    public void setAugmentations(int attrIndex, Augmentations augs) {
        fAttributes[attrIndex].augs = augs;
    }

    /**
     * Sets the uri of the attribute at the specified index.
     *
     * <p>
     *  设置指定索引处的属性的uri。
     * 
     * 
     * @param attrIndex The attribute index.
     * @param uri       Namespace uri
     */
    public void setURI(int attrIndex, String uri) {
        fAttributes[attrIndex].name.uri = uri;
    } // getURI(int,QName)

    // Implementation methods
    public void setSchemaId(int attrIndex, boolean schemaId) {
        fAttributes[attrIndex].schemaId = schemaId;
    }

    public boolean getSchemaId(int index) {
        if (index < 0 || index >= fLength) {
            return false;
        }
        return fAttributes[index].schemaId;
    }

    public boolean getSchemaId(String qname) {
        int index = getIndex(qname);
        return index != -1 ? fAttributes[index].schemaId : false;
    } // getType(String):String

    public boolean getSchemaId(String uri, String localName) {
        if (!fNamespaces) {
            return false;
        }
        int index = getIndex(uri, localName);
        return index != -1 ? fAttributes[index].schemaId : false;
    } // getType(String,String):String

    //XMLBufferListener methods
    /**
     * This method will be invoked by XMLEntityReader before ScannedEntities buffer
     * is reloaded.
     * <p>
     *  在重新加载ScannedEntities缓冲区之前,此方法将由XMLEntityReader调用。
     * 
     */
    public void refresh() {
        if(fLength > 0){
            for(int i = 0 ; i < fLength ; i++){
                getValue(i);
            }
        }
    }
    public void refresh(int pos) {
        }

    //
    // Classes
    //

    /**
     * Attribute information.
     *
     * <p>
     *  属性信息。
     * 
     * 
     * @author Andy Clark, IBM
     */
    static class Attribute {

        //
        // Data
        //

        // basic info

        /** Name. */
        public QName name = new QName();

        /** Type. */
        public String type;

        /** Value. */
        public String value;

        /** This will point to the ScannedEntities buffer.*/
        public XMLString xmlValue;

        /** Non-normalized value. */
        public String nonNormalizedValue;

        /** Specified. */
        public boolean specified;

        /** Schema ID type. */
        public boolean schemaId;

        /**
         * Augmentations information for this attribute.
         * XMLAttributes has no knowledge if any augmentations
         * were attached to Augmentations.
         * <p>
         *  此属性的增强信息。 XMLAttributes不知道是否有任何增强附加到增强。
         */
        public Augmentations augs = new AugmentationsImpl();

        // Additional data for attribute table view

        /** Pointer to the next attribute in the chain. **/
        public Attribute next;

    } // class Attribute

} // class XMLAttributesImpl
