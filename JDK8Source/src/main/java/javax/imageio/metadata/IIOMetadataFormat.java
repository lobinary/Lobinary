/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.metadata;

import java.util.Locale;
import javax.imageio.ImageTypeSpecifier;

/**
 * An object describing the structure of metadata documents returned
 * from <code>IIOMetadata.getAsTree</code> and passed to
 * <code>IIOMetadata.setFromTree</code> and <code>mergeTree</code>.
 * Document structures are described by a set of constraints on the
 * type and number of child elements that may belong to a given parent
 * element type, the names, types, and values of attributes that may
 * belong to an element, and the type and values of
 * <code>Object</code> reference that may be stored at a node.
 *
 * <p> N.B: classes that implement this interface should contain a
 * method declared as <code>public static getInstance()</code> which
 * returns an instance of the class.  Commonly, an implementation will
 * construct only a single instance and cache it for future
 * invocations of <code>getInstance</code>.
 *
 * <p> The structures that may be described by this class are a subset
 * of those expressible using XML document type definitions (DTDs),
 * with the addition of some basic information on the datatypes of
 * attributes and the ability to store an <code>Object</code>
 * reference within a node.  In the future, XML Schemas could be used
 * to represent these structures, and many others.
 *
 * <p> The differences between
 * <code>IIOMetadataFormat</code>-described structures and DTDs are as
 * follows:
 *
 * <ul>
 * <li> Elements may not contain text or mix text with embedded
 * tags.
 *
 * <li> The children of an element must conform to one of a few simple
 * patterns, described in the documentation for the
 * <code>CHILD_*</code> constants;
 *
 * <li> The in-memory representation of an elements may contain a
 * reference to an <code>Object</code>.  There is no provision for
 * representing such objects textually.
 * </ul>
 *
 * <p>
 *  描述从<code> IIOMetadata.getAsTree </code>返回的元数据文档结构并传递给<code> IIOMetadata.setFromTree </code>和<code> m
 * ergeTree </code>的对象。
 * 文档结构由关于可以属于给定父元素类型的子元素的类型和数量的一组约束,可以属于元素的属性的名称,类型和值,以及属性的类型和值来描述。代码>对象</code>引用,可以存储在节点。
 * 
 *  <p> N.B：实现此接口的类应包含一个声明为<code> public static getInstance()</code>的方法,该方法返回类的实例。
 * 通常,实现将仅构建单个实例并且对其进行高速缓存以用于<code> getInstance </code>的未来调用。
 * 
 *  <p>可以由此类描述的结构是使用XML文档类型定义(DTD)可表达的结构的子集,其中添加了关于属性的数据类型的一些基本信息,以及存储<code> / code>引用。
 * 在将来,XML模式可以用于表示这些结构,以及许多其他结构。
 * 
 *  <p> IIOMetadataFormat </code>描述的结构和DTD之间的差异如下：
 * 
 * <ul>
 *  <li>元素不能包含文本或混合包含嵌入标记的文本。
 * 
 * <li>元素的子元素必须符合<code> CHILD _ * </code>常量的文档中描述的几个简单模式之一;
 * 
 *  <li>元素的内存中表示形式可能包含对<code> Object </code>的引用。没有规定以文本方式表示这些对象。
 * </ul>
 * 
 */
public interface IIOMetadataFormat {

    // Child policies

    /**
     * A constant returned by <code>getChildPolicy</code> to indicate
     * that an element may not have any children.  In other words, it
     * is required to be a leaf node.
     * <p>
     *  <code> getChildPolicy </code>返回的常量,表示元素可能没有任何子元素。换句话说,它需要是叶节点。
     * 
     */
    int CHILD_POLICY_EMPTY = 0;

    /**
     * A constant returned by <code>getChildPolicy</code> to indicate
     * that an element must have a single instance of each of its
     * legal child elements, in order.  In DTD terms, the contents of
     * the element are defined by a sequence <code>a,b,c,d,...</code>.
     * <p>
     *  <code> getChildPolicy </code>返回的常量,表示元素必须按顺序具有每个其法定子元素的单个实例。
     * 在DTD术语中,元素的内容由序列<code> a,b,c,d,... </code>定义。
     * 
     */
    int CHILD_POLICY_ALL = 1;

    /**
     * A constant returned by <code>getChildPolicy</code> to indicate
     * that an element must have zero or one instance of each of its
     * legal child elements, in order.  In DTD terms, the contents of
     * the element are defined by a sequence
     * <code>a?,b?,c?,d?,...</code>.
     * <p>
     *  <code> getChildPolicy </code>返回的常量,用于指示元素必须按顺序具有每个其合法子元素的零个或一个实例。
     * 在DTD术语中,元素的内容由序列<code> a,b,c',d',... </code>定义。
     * 
     */
    int CHILD_POLICY_SOME = 2;

    /**
     * A constant returned by <code>getChildPolicy</code> to indicate
     * that an element must have zero or one children, selected from
     * among its legal child elements.  In DTD terms, the contents of
     * the element are defined by a selection
     * <code>a|b|c|d|...</code>.
     * <p>
     *  <code> getChildPolicy </code>返回的常量,表示元素必须有零个或一个子元素,从其合法的子元素中选择。
     * 在DTD术语中,元素的内容由选择<code> a | b | c | d | ... </code>定义。
     * 
     */
    int CHILD_POLICY_CHOICE = 3;

    /**
     * A constant returned by <code>getChildPolicy</code> to indicate
     * that an element must have a sequence of instances of any of its
     * legal child elements.  In DTD terms, the contents of the
     * element are defined by a sequence <code>(a|b|c|d|...)*</code>.
     * <p>
     *  <code> getChildPolicy </code>返回的常量,表示元素必须具有其任何合法子元素的实例序列。
     * 在DTD术语中,元素的内容由序列<code>(a | b | c | d | ...)* </code>定义。
     * 
     */
    int CHILD_POLICY_SEQUENCE = 4;

    /**
     * A constant returned by <code>getChildPolicy</code> to indicate
     * that an element must have zero or more instances of its unique
     * legal child element.  In DTD terms, the contents of the element
     * are defined by a starred expression <code>a*</code>.
     * <p>
     * <code> getChildPolicy </code>返回的常量,用于指示元素必须具有零个或多个其唯一法定子元素的实例。
     * 在DTD术语中,元素的内容由星号表达式<code> a * </code>定义。
     * 
     */
    int CHILD_POLICY_REPEAT = 5;

    /**
     * The largest valid <code>CHILD_POLICY_*</code> constant,
     * to be used for range checks.
     * <p>
     *  最大的有效<code> CHILD_POLICY _ * </code>常量,用于范围检查。
     * 
     */
    int CHILD_POLICY_MAX = CHILD_POLICY_REPEAT;

    /**
     * A constant returned by <code>getObjectValueType</code> to
     * indicate the absence of a user object.
     * <p>
     *  由<code> getObjectValueType </code>返回的常量,用于指示缺少用户对象。
     * 
     */
    int VALUE_NONE = 0;

    /**
     * A constant returned by <code>getAttributeValueType</code> and
     * <code>getObjectValueType</code> to indicate that the attribute
     * or user object may be set a single, arbitrary value.
     * <p>
     *  由<code> getAttributeValueType </code>和<code> getObjectValueType </code>返回的常量,用于指示属性或用户对象可以设置单个任意值。
     * 
     */
    int VALUE_ARBITRARY = 1;

    /**
     * A constant returned by <code>getAttributeValueType</code> and
     * <code>getObjectValueType</code> to indicate that the attribute
     * or user object may be set a range of values.  Both the minimum
     * and maximum values of the range are exclusive.  It is
     * recommended that ranges of integers be inclusive on both ends,
     * and that exclusive ranges be used only for floating-point data.
     *
     * <p>
     *  由<code> getAttributeValueType </code>和<code> getObjectValueType </code>返回的常量,用于指示属性或用户对象可以设置值的范围。
     * 范围的最小值和最大值都是排斥的。建议整数范围在两端包含,并且排除范围仅用于浮点数据。
     * 
     * 
     * @see #VALUE_RANGE_MIN_MAX_INCLUSIVE
     */
    int VALUE_RANGE = 2;

    /**
     * A value that may be or'ed with <code>VALUE_RANGE</code> to
     * obtain <code>VALUE_RANGE_MIN_INCLUSIVE</code>, and with
     * <code>VALUE_RANGE_MAX_INCLUSIVE</code> to obtain
     * <code>VALUE_RANGE_MIN_MAX_INCLUSIVE</code>.
     *
     * <p> Similarly, the value may be and'ed with the value of
     * <code>getAttributeValueType</code>or
     * <code>getObjectValueType</code> to determine if the minimum
     * value of the range is inclusive.
     * <p>
     *  可以通过<code> VALUE_RANGE </code>进行or'ed获取<code> VALUE_RANGE_MIN_INCLUSIVE </code>和<code> VALUE_RANGE_M
     * AX_INCLUSIVE </code>的值,以获取<code> VALUE_RANGE_MIN_MAX_INCLUSIVE </code>。
     * 
     *  <p>类似地,该值可以与<code> getAttributeValueType </code>或<code> getObjectValueType </code>的值进行and运算,以确定该范围的最
     * 小值是否包括在内。
     * 
     */
    int VALUE_RANGE_MIN_INCLUSIVE_MASK = 4;

    /**
     * A value that may be or'ed with <code>VALUE_RANGE</code> to
     * obtain <code>VALUE_RANGE_MAX_INCLUSIVE</code>, and with
     * <code>VALUE_RANGE_MIN_INCLUSIVE</code> to obtain
     * <code>VALUE_RANGE_MIN_MAX_INCLUSIVE</code>.
     *
     * <p> Similarly, the value may be and'ed with the value of
     * <code>getAttributeValueType</code>or
     * <code>getObjectValueType</code> to determine if the maximum
     * value of the range is inclusive.
     * <p>
     * 可以通过<code> VALUE_RANGE </code>进行or'ed获得<code> VALUE_RANGE_MAX_INCLUSIVE </code>和<code> VALUE_RANGE_MI
     * N_INCLUSIVE </code>的值,以获取<code> VALUE_RANGE_MIN_MAX_INCLUSIVE </code>。
     * 
     *  <p>类似地,该值可以与<code> getAttributeValueType </code>或<code> getObjectValueType </code>的值进行and运算,以确定该范围的最
     * 大值是否包含。
     * 
     */
    int VALUE_RANGE_MAX_INCLUSIVE_MASK = 8;

    /**
     * A constant returned by <code>getAttributeValueType</code> and
     * <code>getObjectValueType</code> to indicate that the attribute
     * or user object may be set to a range of values.  The minimum
     * (but not the maximum) value of the range is inclusive.
     * <p>
     *  由<code> getAttributeValueType </code>和<code> getObjectValueType </code>返回的常量,用于指示属性或用户对象可以设置为值的范围。
     * 范围的最小(但不是最大)值包括在内。
     * 
     */
    int VALUE_RANGE_MIN_INCLUSIVE = VALUE_RANGE |
        VALUE_RANGE_MIN_INCLUSIVE_MASK;

    /**
     * A constant returned by <code>getAttributeValueType</code> and
     * <code>getObjectValueType</code> to indicate that the attribute
     * or user object may be set to a range of values.  The maximum
     * (but not the minimum) value of the range is inclusive.
     * <p>
     *  由<code> getAttributeValueType </code>和<code> getObjectValueType </code>返回的常量,用于指示属性或用户对象可以设置为值的范围。
     * 范围的最大(但不是最小)值包括在内。
     * 
     */
    int VALUE_RANGE_MAX_INCLUSIVE = VALUE_RANGE |
        VALUE_RANGE_MAX_INCLUSIVE_MASK;

    /**
     * A constant returned by <code>getAttributeValueType</code> and
     * <code>getObjectValueType</code> to indicate that the attribute
     * or user object may be set a range of values.  Both the minimum
     * and maximum values of the range are inclusive.  It is
     * recommended that ranges of integers be inclusive on both ends,
     * and that exclusive ranges be used only for floating-point data.
     * <p>
     *  由<code> getAttributeValueType </code>和<code> getObjectValueType </code>返回的常量,用于指示属性或用户对象可以设置值的范围。
     * 范围的最小值和最大值都包括在内。建议整数范围在两端包含,并且排除范围仅用于浮点数据。
     * 
     */
    int VALUE_RANGE_MIN_MAX_INCLUSIVE =
        VALUE_RANGE |
        VALUE_RANGE_MIN_INCLUSIVE_MASK |
        VALUE_RANGE_MAX_INCLUSIVE_MASK;

    /**
     * A constant returned by <code>getAttributeValueType</code> and
     * <code>getObjectValueType</code> to indicate that the attribute
     * or user object may be set one of a number of enumerated values.
     * In the case of attributes, these values are
     * <code>String</code>s; for objects, they are
     * <code>Object</code>s implementing a given class or interface.
     *
     * <p> Attribute values of type <code>DATATYPE_BOOLEAN</code>
     * should be marked as enumerations.
     * <p>
     * 由<code> getAttributeValueType </code>和<code> getObjectValueType </code>返回的常量,用于指示属性或用户对象可以设置多个枚举值中的一个
     * 。
     * 在属性的情况下,这些值是<code> String </code> s;对于对象,它们是实现给定类或接口的<code> Object </code>。
     * 
     *  <p>类型<code> DATATYPE_BOOLEAN </code>的属性值应标记为枚举。
     * 
     */
    int VALUE_ENUMERATION = 16;

    /**
     * A constant returned by <code>getAttributeValueType</code> and
     * <code>getObjectValueType</code> to indicate that the attribute
     * or user object may be set to a list or array of values.  In the
     * case of attributes, the list will consist of
     * whitespace-separated values within a <code>String</code>; for
     * objects, an array will be used.
     * <p>
     *  由<code> getAttributeValueType </code>和<code> getObjectValueType </code>返回的常量,用于指示属性或用户对象可以设置为值的列表或数组
     * 。
     * 在属性的情况下,列表将由<code> String </code>中的空格分隔值组成;对于对象,将使用数组。
     * 
     */
    int VALUE_LIST = 32;

    /**
     * A constant returned by <code>getAttributeDataType</code>
     * indicating that the value of an attribute is a general Unicode
     * string.
     * <p>
     *  由<code> getAttributeDataType </code>返回的常量,表示属性的值是一般的Unicode字符串。
     * 
     */
    int DATATYPE_STRING = 0;

    /**
     * A constant returned by <code>getAttributeDataType</code>
     * indicating that the value of an attribute is one of the boolean
     * values 'true' or 'false'.
     * Attribute values of type DATATYPE_BOOLEAN should be marked as
     * enumerations, and the permitted values should be the string
     * literal values "TRUE" or "FALSE", although a plugin may also
     * recognise lower or mixed case equivalents.
     * <p>
     *  由<code> getAttributeDataType </code>返回的常量,表示属性的值是布尔值"true"或"false"之一。
     * 类型为DATATYPE_BOOLEAN的属性值应标记为枚举,允许的值应为字符串字面值"TRUE"或"FALSE",虽然插件也可能识别较低或混合大小写的等效值。
     * 
     */
    int DATATYPE_BOOLEAN = 1;

    /**
     * A constant returned by <code>getAttributeDataType</code>
     * indicating that the value of an attribute is a string
     * representation of an integer.
     * <p>
     *  由<code> getAttributeDataType </code>返回的常量,表示属性的值是整数的字符串表示形式。
     * 
     */
    int DATATYPE_INTEGER = 2;

    /**
     * A constant returned by <code>getAttributeDataType</code>
     * indicating that the value of an attribute is a string
     * representation of a decimal floating-point number.
     * <p>
     * 由<code> getAttributeDataType </code>返回的常量,表示属性的值是十进制浮点数的字符串表示形式。
     * 
     */
    int DATATYPE_FLOAT = 3;

    /**
     * A constant returned by <code>getAttributeDataType</code>
     * indicating that the value of an attribute is a string
     * representation of a double-precision decimal floating-point
     * number.
     * <p>
     *  由<code> getAttributeDataType </code>返回的常量,表示属性的值是双精度十进制浮点数的字符串表示形式。
     * 
     */
    int DATATYPE_DOUBLE = 4;

    // Root

    /**
     * Returns the name of the root element of the format.
     *
     * <p>
     *  返回格式的根元素的名称。
     * 
     * 
     * @return a <code>String</code>.
     */
    String getRootName();

    // Multiplicity

    /**
     * Returns <code>true</code> if the element (and the subtree below
     * it) is allowed to appear in a metadata document for an image of
     * the given type, defined by an <code>ImageTypeSpecifier</code>.
     * For example, a metadata document format might contain an
     * element that describes the primary colors of the image, which
     * would not be allowed when writing a grayscale image.
     *
     * <p>
     *  如果元素(及其下面的子树)被允许出现在由<code> ImageTypeSpecifier </code>定义的给定类型的图像的元数据文档中,则返回<code> true </code>。
     * 例如,元数据文档格式可以包含描述图像的原色的元素,其在写入灰度图像时将不被允许。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param imageType an <code>ImageTypeSpecifier</code> indicating
     * the type of the image that will be associated with the
     * metadata.
     *
     * @return <code>true</code> if the node is meaningful for images
     * of the given type.
     */
    boolean canNodeAppear(String elementName, ImageTypeSpecifier imageType);

    /**
     * Returns the minimum number of children of the named element
     * with child policy <code>CHILD_POLICY_REPEAT</code>.  For
     * example, an element representing color primary information
     * might be required to have at least 3 children, one for each
     * primary.
     *
     * <p>
     *  返回具有子策略<code> CHILD_POLICY_REPEAT </code>的已命名元素的最小子数。例如,表示颜色主要信息的元素可能需要至少有3个子元素,每个子元素有一个子元素。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return an <code>int</code>.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element does
     * not have a child policy of <code>CHILD_POLICY_REPEAT</code>.
     */
    int getElementMinChildren(String elementName);

    /**
     * Returns the maximum number of children of the named element
     * with child policy <code>CHILD_POLICY_REPEAT</code>.  For
     * example, an element representing an entry in an 8-bit color
     * palette might be allowed to repeat up to 256 times.  A value of
     * <code>Integer.MAX_VALUE</code> may be used to specify that
     * there is no upper bound.
     *
     * <p>
     *  返回具有子策略<code> CHILD_POLICY_REPEAT </code>的已命名元素的最大子数。例如,表示8位调色板中的条目的元素可以允许重复多达256次。
     * 值<code> Integer.MAX_VALUE </code>可用于指定没有上限。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return an <code>int</code>.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element does
     * not have a child policy of <code>CHILD_POLICY_REPEAT</code>.
     */
    int getElementMaxChildren(String elementName);

    /**
     * Returns a <code>String</code> containing a description of the
     * named element, or <code>null</code>.  The description will be
     * localized for the supplied <code>Locale</code> if possible.
     *
     * <p> If <code>locale</code> is <code>null</code>, the current
     * default <code>Locale</code> returned by <code>Locale.getLocale</code>
     * will be used.
     *
     * <p>
     * 返回包含指定元素或<code> null </code>的描述的<code> String </code>。如果可能,将对提供的<code> Locale </code>进行本地化描述。
     * 
     *  <p>如果<code> locale </code>是<code> null </code>,则会使用<code> Locale.getLocale </code>返回的当前默认<code> Loca
     * le </code>。
     * 
     * 
     * @param elementName the name of the element.
     * @param locale the <code>Locale</code> for which localization
     * will be attempted.
     *
     * @return the element description.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code>, or is not a legal element name for this format.
     */
    String getElementDescription(String elementName, Locale locale);

    // Children

    /**
     * Returns one of the constants starting with
     * <code>CHILD_POLICY_</code>, indicating the legal pattern of
     * children for the named element.
     *
     * <p>
     *  返回以<code> CHILD_POLICY _ </code>开头的常量,指示指定元素的子元素的法律模式。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return one of the <code>CHILD_POLICY_*</code> constants.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     */
    int getChildPolicy(String elementName);

    /**
     * Returns an array of <code>String</code>s indicating the names
     * of the element which are allowed to be children of the named
     * element, in the order in which they should appear.  If the
     * element cannot have children, <code>null</code> is returned.
     *
     * <p>
     *  返回一个<code> String </code> s数组,表示允许作为指定元素的子元素的元素的名称,按照它们应该出现的顺序。如果元素不能有子元素,则返回<code> null </code>。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return an array of <code>String</code>s, or null.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     */
    String[] getChildNames(String elementName);

    // Attributes

    /**
     * Returns an array of <code>String</code>s listing the names of
     * the attributes that may be associated with the named element.
     *
     * <p>
     *  返回一个<code> String </code>数组,列出可能与指定元素相关联的属性的名称。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return an array of <code>String</code>s.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     */
    String[] getAttributeNames(String elementName);

    /**
     * Returns one of the constants starting with <code>VALUE_</code>,
     * indicating whether the values of the given attribute within the
     * named element are arbitrary, constrained to lie within a
     * specified range, constrained to be one of a set of enumerated
     * values, or are a whitespace-separated list of arbitrary values.
     *
     * <p>
     *  返回以<code> VALUE _ </code>开头的常量,指示指定元素中给定属性的值是否是任意的,被限制在指定范围内,被限制为枚举值集合中的一个,或者是任意值的空格分隔的列表。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return one of the <code>VALUE_*</code> constants.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     */
    int getAttributeValueType(String elementName, String attrName);

    /**
     * Returns one of the constants starting with
     * <code>DATATYPE_</code>, indicating the format and
     * interpretation of the value of the given attribute within the
     * named element.  If <code>getAttributeValueType</code> returns
     * <code>VALUE_LIST</code>, then the legal value is a
     * whitespace-spearated list of values of the returned datatype.
     *
     * <p>
     *  返回以<code> DATATYPE _ </code>开头的常量,指示指定元素中给定属性的值的格式和解释。
     * 如果<code> getAttributeValueType </code>返回<code> VALUE_LIST </code>,则合法值是返回的数据类型的值的空白格式列表。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return one of the <code>DATATYPE_*</code> constants.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     */
    int getAttributeDataType(String elementName, String attrName);

    /**
     * Returns <code>true</code> if the named attribute must be
     * present within the named element.
     *
     * <p>
     * 如果命名的属性必须存在于命名的元素中,则返回<code> true </code>。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return <code>true</code> if the attribute must be present.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     */
    boolean isAttributeRequired(String elementName, String attrName);

    /**
     * Returns the default value of the named attribute, if it is not
     * explicitly present within the named element, as a
     * <code>String</code>, or <code>null</code> if no default value
     * is available.
     *
     * <p>
     *  如果没有默认值可用,则返回命名属性的默认值,如果它没有显式地出现在命名元素中,则为<code> String </code>或<code> null </code>。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return a <code>String</code> containing the default value, or
     * <code>null</code>.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     */
    String getAttributeDefaultValue(String elementName, String attrName);

    /**
     * Returns an array of <code>String</code>s containing the legal
     * enumerated values for the given attribute within the named
     * element.  This method should only be called if
     * <code>getAttributeValueType</code> returns
     * <code>VALUE_ENUMERATION</code>.
     *
     * <p>
     *  返回包含指定元素中给定属性的合法枚举值的<code> String </code> s数组。
     * 此方法只应在<code> getAttributeValueType </code>返回<code> VALUE_ENUMERATION </code>时调用。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return an array of <code>String</code>s.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     * @exception IllegalArgumentException if the given attribute is
     * not defined as an enumeration.
     */
    String[] getAttributeEnumerations(String elementName, String attrName);

    /**
     * Returns the minimum legal value for the attribute.  Whether
     * this value is inclusive or exclusive may be determined by the
     * value of <code>getAttributeValueType</code>.  The value is
     * returned as a <code>String</code>; its interpretation is
     * dependent on the value of <code>getAttributeDataType</code>.
     * This method should only be called if
     * <code>getAttributeValueType</code> returns
     * <code>VALUE_RANGE_*</code>.
     *
     * <p>
     *  返回属性的最小合法值。该值是包含还是排它可以由<code> getAttributeValueType </code>的值确定。
     * 该值作为<code> String </code>返回;其解释取决于<code> getAttributeDataType </code>的值。
     * 仅当<code> getAttributeValueType </code>返回<code> VALUE_RANGE _ * </code>时,才应调用此方法。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return a <code>String</code> containing the smallest legal
     * value for the attribute.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     * @exception IllegalArgumentException if the given attribute is
     * not defined as a range.
     */
    String getAttributeMinValue(String elementName, String attrName);

    /**
     * Returns the maximum legal value for the attribute.  Whether
     * this value is inclusive or exclusive may be determined by the
     * value of <code>getAttributeValueType</code>.  The value is
     * returned as a <code>String</code>; its interpretation is
     * dependent on the value of <code>getAttributeDataType</code>.
     * This method should only be called if
     * <code>getAttributeValueType</code> returns
     * <code>VALUE_RANGE_*</code>.
     *
     * <p>
     *  返回属性的最大合法值。该值是包含还是排它可以由<code> getAttributeValueType </code>的值确定。
     * 该值作为<code> String </code>返回;其解释取决于<code> getAttributeDataType </code>的值。
     * 仅当<code> getAttributeValueType </code>返回<code> VALUE_RANGE _ * </code>时,才应调用此方法。
     * 
     * 
     * @param elementName the name of the element being queried, as a
     * <code>String</code>.
     * @param attrName the name of the attribute being queried.
     *
     * @return a <code>String</code> containing the largest legal
     * value for the attribute.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     * @exception IllegalArgumentException if the given attribute is
     * not defined as a range.
     */
    String getAttributeMaxValue(String elementName, String attrName);

    /**
     * Returns the minimum number of list items that may be used to
     * define this attribute.  The attribute itself is defined as a
     * <code>String</code> containing multiple whitespace-separated
     * items.  This method should only be called if
     * <code>getAttributeValueType</code> returns
     * <code>VALUE_LIST</code>.
     *
     * <p>
     * 返回可用于定义此属性的列表项的最小数量。属性本身定义为包含多个以空格分隔的项目的<code> String </code>。
     * 仅当<code> getAttributeValueType </code>返回<code> VALUE_LIST </code>时,才应调用此方法。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return the smallest legal number of list items for the
     * attribute.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     * @exception IllegalArgumentException if the given attribute is
     * not defined as a list.
     */
    int getAttributeListMinLength(String elementName, String attrName);

    /**
     * Returns the maximum number of list items that may be used to
     * define this attribute.  A value of
     * <code>Integer.MAX_VALUE</code> may be used to specify that
     * there is no upper bound.  The attribute itself is defined as a
     * <code>String</code> containing multiple whitespace-separated
     * items.  This method should only be called if
     * <code>getAttributeValueType</code> returns
     * <code>VALUE_LIST</code>.
     *
     * <p>
     *  返回可用于定义此属性的列表项的最大数量。值<code> Integer.MAX_VALUE </code>可用于指定没有上限。
     * 属性本身定义为包含多个以空格分隔的项目的<code> String </code>。
     * 仅当<code> getAttributeValueType </code>返回<code> VALUE_LIST </code>时,才应调用此方法。
     * 
     * 
     * @param elementName the name of the element being queried.
     * @param attrName the name of the attribute being queried.
     *
     * @return the largest legal number of list items for the
     * attribute.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     * @exception IllegalArgumentException if the given attribute is
     * not defined as a list.
     */
    int getAttributeListMaxLength(String elementName, String attrName);

    /**
     * Returns a <code>String</code> containing a description of the
     * named attribute, or <code>null</code>.  The description will be
     * localized for the supplied <code>Locale</code> if possible.
     *
     * <p> If <code>locale</code> is <code>null</code>, the current
     * default <code>Locale</code> returned by <code>Locale.getLocale</code>
     * will be used.
     *
     * <p>
     *  返回包含指定属性或<code> null </code>的描述的<code> String </code>。如果可能,将对提供的<code> Locale </code>进行本地化描述。
     * 
     *  <p>如果<code> locale </code>是<code> null </code>,则会使用<code> Locale.getLocale </code>返回的当前默认<code> Loca
     * le </code>。
     * 
     * 
     * @param elementName the name of the element.
     * @param attrName the name of the attribute.
     * @param locale the <code>Locale</code> for which localization
     * will be attempted.
     *
     * @return the attribute description.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code>, or is not a legal element name for this format.
     * @exception IllegalArgumentException if <code>attrName</code> is
     * <code>null</code> or is not a legal attribute name for this
     * element.
     */
    String getAttributeDescription(String elementName, String attrName,
                                   Locale locale);

    // Object value

    /**
     * Returns one of the enumerated values starting with
     * <code>VALUE_</code>, indicating the type of values
     * (enumeration, range, or array) that are allowed for the
     * <code>Object</code> reference.  If no object value can be
     * stored within the given element, the result of this method will
     * be <code>VALUE_NONE</code>.
     *
     * <p> <code>Object</code> references whose legal values are
     * defined as a range must implement the <code>Comparable</code>
     * interface.
     *
     * <p>
     *  返回以<code> VALUE _ </code>开头的枚举值中的一个,表示对于<code> Object </code>引用允许的值的类型(枚举,范围或数组)。
     * 如果给定元素中没有对象值,则此方法的结果为<code> VALUE_NONE </code>。
     * 
     *  其合法值定义为范围的<p> <code> Object </code>引用必须实现<code> Comparable </code>接口。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return one of the <code>VALUE_*</code> constants.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     *
     * @see Comparable
     */
    int getObjectValueType(String elementName);

    /**
     * Returns the <code>Class</code> type of the <code>Object</code>
     * reference stored within the element.  If this element may not
     * contain an <code>Object</code> reference, an
     * <code>IllegalArgumentException</code> will be thrown.  If the
     * class type is an array, this field indicates the underlying
     * class type (<i>e.g</i>, for an array of <code>int</code>s, this
     * method would return <code>int.class</code>).
     *
     * <p> <code>Object</code> references whose legal values are
     * defined as a range must implement the <code>Comparable</code>
     * interface.
     *
     * <p>
     * 返回存储在元素中的<code> Object </code>引用的<code> Class </code>类型。
     * 如果此元素可能不包含<code> Object </code>引用,将抛出<code> IllegalArgumentException </code>。
     * 如果类类型是数组,该字段表示<code> int </code> s的数组的底层类类型(<i> eg </i>),该方法将返回<code> int.class < / code>)。
     * 
     *  其合法值定义为范围的<p> <code> Object </code>引用必须实现<code> Comparable </code>接口。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return a <code>Class</code> object.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element cannot
     * contain an object value (<i>i.e.</i>, if
     * <code>getObjectValueType(elementName) == VALUE_NONE</code>).
     */
    Class<?> getObjectClass(String elementName);

    /**
     * Returns an <code>Object</code>s containing the default
     * value for the <code>Object</code> reference within
     * the named element.
     *
     * <p>
     *  返回包含指定元素中<code> Object </code>引用的默认值的<code> Object </code>。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return an <code>Object</code>.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element cannot
     * contain an object value (<i>i.e.</i>, if
     * <code>getObjectValueType(elementName) == VALUE_NONE</code>).
     */
    Object getObjectDefaultValue(String elementName);

    /**
     * Returns an array of <code>Object</code>s containing the legal
     * enumerated values for the <code>Object</code> reference within
     * the named element.  This method should only be called if
     * <code>getObjectValueType</code> returns
     * <code>VALUE_ENUMERATION</code>.
     *
     * <p> The <code>Object</code> associated with a node that accepts
     * enumerated values must be equal to one of the values returned by
     * this method, as defined by the <code>==</code> operator (as
     * opposed to the <code>Object.equals</code> method).
     *
     * <p>
     *  返回一个包含指定元素中<code> Object </code>引用的合法枚举值的<code> Object </code>数组。
     * 此方法只应在<code> getObjectValueType </code>返回<code> VALUE_ENUMERATION </code>时调用。
     * 
     *  <p>与接受枚举值的节点相关联的<code> Object </code>必须等于由此方法返回的值之一,由<code> == </code>运算符定义到<code> Object.equals </code>
     * 方法)。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return an array of <code>Object</code>s.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element cannot
     * contain an object value (<i>i.e.</i>, if
     * <code>getObjectValueType(elementName) == VALUE_NONE</code>).
     * @exception IllegalArgumentException if the <code>Object</code>
     * is not defined as an enumeration.
     */
    Object[] getObjectEnumerations(String elementName);

    /**
     * Returns the minimum legal value for the <code>Object</code>
     * reference within the named element.  Whether this value is
     * inclusive or exclusive may be determined by the value of
     * <code>getObjectValueType</code>.  This method should only be
     * called if <code>getObjectValueType</code> returns one of the
     * constants starting with <code>VALUE_RANGE</code>.
     *
     * <p>
     * 返回指定元素中<code> Object </code>引用的最小合法值。该值是包含还是排斥可以由<code> getObjectValueType </code>的值确定。
     * 仅当<code> getObjectValueType </code>返回以<code> VALUE_RANGE </code>开头的常量之一时,才应调用此方法。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return the smallest legal value for the attribute.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element cannot
     * contain an object value (<i>i.e.</i>, if
     * <code>getObjectValueType(elementName) == VALUE_NONE</code>).
     * @exception IllegalArgumentException if the <code>Object</code>
     * is not defined as a range.
     */
    Comparable<?> getObjectMinValue(String elementName);

    /**
     * Returns the maximum legal value for the <code>Object</code>
     * reference within the named element.  Whether this value is
     * inclusive or exclusive may be determined by the value of
     * <code>getObjectValueType</code>.  This method should only be
     * called if <code>getObjectValueType</code> returns one of the
     * constants starting with <code>VALUE_RANGE</code>.
     *
     * <p>
     *  返回指定元素中<code> Object </code>引用的最大合法值。该值是包含还是排斥可以由<code> getObjectValueType </code>的值确定。
     * 仅当<code> getObjectValueType </code>返回以<code> VALUE_RANGE </code>开头的常量之一时,才应调用此方法。
     * 
     * 
     * @return the smallest legal value for the attribute.
     *
     * @param elementName the name of the element being queried.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element cannot
     * contain an object value (<i>i.e.</i>, if
     * <code>getObjectValueType(elementName) == VALUE_NONE</code>).
     * @exception IllegalArgumentException if the <code>Object</code>
     * is not defined as a range.
     */
    Comparable<?> getObjectMaxValue(String elementName);

    /**
     * Returns the minimum number of array elements that may be used
     * to define the <code>Object</code> reference within the named
     * element.  This method should only be called if
     * <code>getObjectValueType</code> returns
     * <code>VALUE_LIST</code>.
     *
     * <p>
     *  返回可用于在指定元素中定义<code> Object </code>引用的数组元素的最小数量。
     * 仅当<code> getObjectValueType </code>返回<code> VALUE_LIST </code>时,才应调用此方法。
     * 
     * 
     * @param elementName the name of the element being queried.
     *
     * @return the smallest valid array length for the
     * <code>Object</code> reference.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element cannot
     * contain an object value (<i>i.e.</i>, if
     * <code>getObjectValueType(elementName) == VALUE_NONE</code>).
     * @exception IllegalArgumentException if the <code>Object</code> is not
     * an array.
     */
    int getObjectArrayMinLength(String elementName);

    /**
     * Returns the maximum number of array elements that may be used
     * to define the <code>Object</code> reference within the named
     * element.  A value of <code>Integer.MAX_VALUE</code> may be used
     * to specify that there is no upper bound.  This method should
     * only be called if <code>getObjectValueType</code> returns
     * <code>VALUE_LIST</code>.
     *
     * <p>
     *  返回可用于在命名元素中定义<code> Object </code>引用的数组元素的最大数目。值<code> Integer.MAX_VALUE </code>可用于指定没有上限。
     * 仅当<code> getObjectValueType </code>返回<code> VALUE_LIST </code>时,才应调用此方法。
     * 
     * @param elementName the name of the element being queried.
     *
     * @return the largest valid array length for the
     * <code>Object</code> reference.
     *
     * @exception IllegalArgumentException if <code>elementName</code>
     * is <code>null</code> or is not a legal element name for this
     * format.
     * @exception IllegalArgumentException if the named element cannot
     * contain an object value (<i>i.e.</i>, if
     * <code>getObjectValueType(elementName) == VALUE_NONE</code>).
     * @exception IllegalArgumentException if the <code>Object</code> is not
     * an array.
     */
    int getObjectArrayMaxLength(String elementName);
}
