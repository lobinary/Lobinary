/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999-2002 The Apache Software Foundation.  All rights
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
 *  版权所有(c)1999-2002 Apache软件基金会。版权所有。
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

package com.sun.org.apache.xerces.internal.impl.dtd;

/**
 * ContentSpec really exists to aid the parser classes in implementing
 * access to the grammar.
 * <p>
 * This class is used by the DTD scanner and the validator classes,
 * allowing them to be used separately or together.  This "struct"
 * class is used to build content models for validation, where it
 * is more efficient to fetch all of the information for each of
 * these content model "fragments" than to fetch each field one at
 * a time.  Since configurations are allowed to have validators
 * without a DTD scanner (i.e. a schema validator) and a DTD scanner
 * without a validator (non-validating processor), this class can be
 * used by each without requiring the presence of the other.
 * <p>
 * When processing element declarations, the DTD scanner will build
 * up a representation of the content model using the node types that
 * are defined here.  Since a non-validating processor only needs to
 * remember the type of content model declared (i.e. ANY, EMPTY, MIXED,
 * or CHILDREN), it is free to discard the specific details of the
 * MIXED and CHILDREN content models described using this class.
 * <p>
 * In the typical case of a validating processor reading the grammar
 * of the document from a DTD, the information about the content model
 * declared will be preserved and later "compiled" into an efficient
 * form for use during element validation.  Each content spec node
 * that is saved is assigned a unique index that is used as a handle
 * for the "value" or "otherValue" fields of other content spec nodes.
 * A leaf node has a "value" that is either an index in the string
 * pool of the element type of that leaf, or a value of -1 to indicate
 * the special "#PCDATA" leaf type used in a mixed content model.
 * <p>
 * For a mixed content model, the content spec will be made up of
 * leaf and choice content spec nodes, with an optional "zero or more"
 * node.  For example, the mixed content declaration "(#PCDATA)" would
 * contain a single leaf node with a node value of -1.  A mixed content
 * declaration of "(#PCDATA|foo)*" would have a content spec consisting
 * of two leaf nodes, for the "#PCDATA" and "foo" choices, a choice node
 * with the "value" set to the index of the "#PCDATA" leaf node and the
 * "otherValue" set to the index of the "foo" leaf node, and a "zero or
 * more" node with the "value" set to the index of the choice node.  If
 * the content model has more choices, for example "(#PCDATA|a|b)*", then
 * there will be more corresponding choice and leaf nodes, the choice
 * nodes will be chained together through the "value" field with each
 * leaf node referenced by the "otherValue" field.
 * <p>
 * For element content models, there are sequence nodes and also "zero or
 * one" and "one or more" nodes.  The leaf nodes would always have a valid
 * string pool index, as the "#PCDATA" leaf is not used in the declarations
 * for element content models.
 *
 * @xerces.internal
 *
 * <p>
 *  ContentSpec真正存在,以帮助解析器类实现对语法的访问。
 * <p>
 * 此类由DTD扫描器和验证器类使用,允许它们单独使用或一起使用。这个"struct"类用于构建用于验证的内容模型,其中获取这些内容模型"片段"中的每一个的所有信息比一次获取一个字段更有效。
 * 由于允许配置具有没有DTD扫描器(即模式验证器)和没有验证器(非验证处理器)的DTD扫描器的验证器,所以这个类可以被每个使用而不需要另一个的存在。
 * <p>
 *  处理元素声明时,DTD扫描器将使用此处定义的节点类型构建内容模型的表示。
 * 由于非验证处理器仅需要记住所声明的内容模型的类型(即,ANY,EMPTY,MIXED或CHILDREN),因此可以自由地丢弃使用该类描述的MIXED和CHILDREN内容模型的特定细节。
 * <p>
 * 在验证处理器从DTD读取文档的语法的典型情况下,关于所声明的内容模型的信息将被保留并且稍后被"编译"成有效形式以在元素验证期间使用。
 * 保存的每个内容规范节点被分配唯一索引,该唯一索引用作其他内容规范节点的"value"或"otherValue"字段的句柄。
 * 叶节点具有"值",该值是该叶的元素类型的字符串池中的索引,或值-1,以指示在混合内容模型中使用的特殊"#PCDATA"叶类型。
 * <p>
 *  对于混合内容模型,内容规范将由叶和选择内容规范节点组成,具有可选的"零个或多个"节点。例如,混合内容声明"(#PCDATA)"将包含节点值为-1的单个叶节点。
 *  "(#PCDATA | foo)*"的混合内容声明将具有由两个叶节点组成的内容规范,对于"#PCDATA"和"foo"选择,具有设置为索引的索引的选择节点设置为"foo"叶节点的索引的"#PCDATA
 * "叶节点和"otherValue",以及具有设置为选择节点的索引的"零个或多个"节点。
 *  对于混合内容模型,内容规范将由叶和选择内容规范节点组成,具有可选的"零个或多个"节点。例如,混合内容声明"(#PCDATA)"将包含节点值为-1的单个叶节点。
 * 如果内容模型有更多的选择,例如"(#PCDATA | a | b)*",则将有更多的相应选择和叶节点,选择节点将通过"值"字段链接在一起,由"otherValue"字段引用。
 * <p>
 * 对于元素内容模型,存在序列节点以及"零个或一个"和"一个或多个"节点。叶节点将总是具有有效的字符串池索引,因为在元素内容模型的声明中不使用"#PCDATA"叶。
 * 
 *  @ xerces.internal
 * 
 */
public class XMLContentSpec {

    //
    // Constants
    //

    /**
     * Name or #PCDATA. Leaf nodes that represent parsed character
     * data (#PCDATA) have values of -1.
     * <p>
     *  名称或#PCDATA。表示解析的字符数据(#PCDATA)的叶节点的值为-1。
     * 
     */
    public static final short CONTENTSPECNODE_LEAF = 0;

    /** Represents a zero or one occurence count, '?'. */
    public static final short CONTENTSPECNODE_ZERO_OR_ONE = 1;

    /** Represents a zero or more occurence count, '*'. */
    public static final short CONTENTSPECNODE_ZERO_OR_MORE = 2;

    /** Represents a one or more occurence count, '+'. */
    public static final short CONTENTSPECNODE_ONE_OR_MORE = 3;

    /** Represents choice, '|'. */
    public static final short CONTENTSPECNODE_CHOICE = 4;

    /** Represents sequence, ','. */
    public static final short CONTENTSPECNODE_SEQ = 5;

    /**
     * Represents any namespace specified namespace. When the element
     * found in the document must belong to a specific namespace,
     * <code>otherValue</code> will contain the name of the namespace.
     * If <code>otherValue</code> is <code>-1</code> then the element
     * can be from any namespace.
     * <p>
     * Lists of valid namespaces are created from choice content spec
     * nodes that have any content spec nodes as children.
     * <p>
     *  表示任何命名空间指定的命名空间。当在文档中找到的元素必须属于特定的命名空间时,<code> otherValue </code>将包含命名空间的名称。
     * 如果<code> otherValue </code>是<code> -1 </code>,那么元素可以来自任何命名空间。
     * <p>
     *  有效命名空间的列表是从具有任何内容规范节点的选项内容规范节点创建的。
     * 
     */
    public static final short CONTENTSPECNODE_ANY = 6;

    /**
     * Represents any other namespace (XML Schema: ##other).
     * <p>
     * When the content spec node type is set to CONTENTSPECNODE_ANY_OTHER,
     * <code>value</code> will contain the namespace that <em>cannot</em>
     * occur.
     * <p>
     *  表示任何其他命名空间(XML模式：## other)。
     * <p>
     *  当内容规范节点类型设置为CONTENTSPECNODE_ANY_OTHER时,<code> value </code>将包含<em>不能</em>发生的命名空间。
     * 
     */
    public static final short CONTENTSPECNODE_ANY_OTHER = 7;

    /** Represents any local element (XML Schema: ##local). */
    public static final short CONTENTSPECNODE_ANY_LOCAL = 8;

    /** prcessContent is 'lax' **/
    public static final short CONTENTSPECNODE_ANY_LAX = 22;

    public static final short CONTENTSPECNODE_ANY_OTHER_LAX = 23;

    public static final short CONTENTSPECNODE_ANY_LOCAL_LAX = 24;

    /** processContent is 'skip' **/

    public static final short CONTENTSPECNODE_ANY_SKIP = 38;

    public static final short CONTENTSPECNODE_ANY_OTHER_SKIP = 39;

    public static final short CONTENTSPECNODE_ANY_LOCAL_SKIP = 40;
    //
    // Data
    //

    /**
     * The content spec node type.
     *
     * <p>
     *  内容规范节点类型。
     * 
     * 
     * @see #CONTENTSPECNODE_LEAF
     * @see #CONTENTSPECNODE_ZERO_OR_ONE
     * @see #CONTENTSPECNODE_ZERO_OR_MORE
     * @see #CONTENTSPECNODE_ONE_OR_MORE
     * @see #CONTENTSPECNODE_CHOICE
     * @see #CONTENTSPECNODE_SEQ
     */
    public short type;

    /**
     * The "left hand" value object of the content spec node.
     * leaf name.localpart, single child for unary ops, left child for binary ops.
     * <p>
     *  内容规范节点的"左手"值对象。 leaf name.localpart,单个子元素用于一元操作,左子元素用于二元操作。
     * 
     */
    public Object value;

    /**
     * The "right hand" value of the content spec node.
     *  leaf name.uri, right child for binary ops
     * <p>
     *  内容规范节点的"右手"值。叶名称。二进制操作的右孩子
     * 
     */
    public Object otherValue;

    //
    // Constructors
    //

    /** Default constructor. */
    public XMLContentSpec() {
        clear();
    }

    /** Constructs a content spec with the specified values. */
    public XMLContentSpec(short type, Object value, Object otherValue) {
        setValues(type, value, otherValue);
    }

    /**
     * Constructs a content spec from the values in the specified content spec.
     * <p>
     *  根据指定的内容规范中的值构造内容规范。
     * 
     */
    public XMLContentSpec(XMLContentSpec contentSpec) {
        setValues(contentSpec);
    }

    /**
     * Constructs a content spec from the values specified by the given
     * content spec provider and identifier.
     * <p>
     *  根据给定内容规范提供程序和标识符指定的值构造内容规范。
     * 
     */
    public XMLContentSpec(XMLContentSpec.Provider provider,
                          int contentSpecIndex) {
        setValues(provider, contentSpecIndex);
    }

    //
    // Public methods
    //

    /** Clears the values. */
    public void clear() {
        type = -1;
        value = null;
        otherValue = null;
    }

    /** Sets the values. */
    public void setValues(short type, Object value, Object otherValue) {
        this.type = type;
        this.value = value;
        this.otherValue = otherValue;
    }

    /** Sets the values of the specified content spec. */
    public void setValues(XMLContentSpec contentSpec) {
        type = contentSpec.type;
        value = contentSpec.value;
        otherValue = contentSpec.otherValue;
    }

    /**
     * Sets the values from the values specified by the given content spec
     * provider and identifier. If the specified content spec cannot be
     * provided, the values of this content spec are cleared.
     * <p>
     * 根据给定的内容规范提供程序和标识符指定的值设置值。如果无法提供指定的内容规范,则会清除此内容规范的值。
     * 
     */
    public void setValues(XMLContentSpec.Provider provider,
                          int contentSpecIndex) {
        if (!provider.getContentSpec(contentSpecIndex, this)) {
            clear();
        }
    }


    //
    // Object methods
    //

    /** Returns a hash code for this node. */
    public int hashCode() {
        return type << 16 |
               value.hashCode() << 8 |
               otherValue.hashCode();
    }

    /** Returns true if the two objects are equal. */
    public boolean equals(Object object) {
        if (object != null && object instanceof XMLContentSpec) {
            XMLContentSpec contentSpec = (XMLContentSpec)object;
            return type == contentSpec.type &&
                   value == contentSpec.value &&
                   otherValue == contentSpec.otherValue;
        }
        return false;
    }


    //
    // Interfaces
    //

    /**
     * Provides a means for walking the structure built out of
     * content spec "nodes". The user of this provider interface is
     * responsible for knowing what the content spec node values
     * "mean". If those values refer to content spec identifiers,
     * then the user can call back into the provider to get the
     * next content spec node in the structure.
     *
     * @xerces.internal
     * <p>
     *  提供了一种步行由内容规范"节点"构建的结构的方法。此提供者接口的用户负责知道内容规范节点值"意味着什么"。如果这些值涉及内容规范标识符,则用户可以回调到提供者中以获得结构中的下一个内容规范节点。
     * 
     *  @ xerces.internal
     * 
     */
    public interface Provider {

        //
        // XMLContentSpec.Provider methods
        //

        /**
         * Fills in the provided content spec structure with content spec
         * information for a unique identifier.
         *
         * <p>
         * 
         * @param contentSpecIndex The content spec identifier. All content
         *                         spec "nodes" have a unique identifier.
         * @param contentSpec      The content spec struct to fill in with
         *                         the information.
         *
         * @return Returns true if the contentSpecIndex was found.
         */
        public boolean getContentSpec(int contentSpecIndex, XMLContentSpec contentSpec);

    } // interface Provider

} // class XMLContentSpec
