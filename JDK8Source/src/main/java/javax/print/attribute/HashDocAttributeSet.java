/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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


package javax.print.attribute;

import java.io.Serializable;

/**
 * Class HashDocAttributeSet provides an attribute set which
 * inherits its implementation from class {@link HashAttributeSet
 * HashAttributeSet} and enforces the semantic restrictions of interface {@link
 * DocAttributeSet DocAttributeSet}.
 * <P>
 *
 * <p>
 *  类HashDocAttributeSet提供了一个属性集,它从类{@link HashAttributeSet HashAttributeSet}继承其实现,并强制实现接口{@link DocAttributeSet DocAttributeSet}
 * 的语义限制。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class HashDocAttributeSet extends HashAttributeSet
    implements DocAttributeSet, Serializable {

    private static final long serialVersionUID = -1128534486061432528L;

    /**
     * Construct a new, empty hash doc attribute set.
     * <p>
     *  构造一个新的,空的哈希文档属性集。
     * 
     */
    public HashDocAttributeSet() {
        super (DocAttribute.class);
    }

    /**
     * Construct a new hash doc attribute set,
     * initially populated with the given value.
     *
     * <p>
     *  构造新的哈希doc属性集,最初使用给定值填充。
     * 
     * 
     * @param  attribute  Attribute value to add to the set.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>attribute</CODE> is null.
     */
    public HashDocAttributeSet(DocAttribute attribute) {
        super (attribute, DocAttribute.class);
    }

    /**
     * Construct a new hash doc attribute set,
     * initially populated with the values from the given array.
     * The new attribute set is populated by
     * adding the elements of <CODE>attributes</CODE> array to the set in
     * sequence, starting at index 0. Thus, later array elements may replace
     * earlier array elements if the array contains duplicate attribute
     * values or attribute categories.
     *
     * <p>
     *  构造一个新的哈希文档属性集,最初使用给定数组中的值填充。通过从索引0开始将<CODE> attributes </CODE>数组的元素添加到序列中的集合来填充新的属性集。
     * 因此,如果数组包含重复的属性值或属性,则稍后的数组元素可以替换先前的数组元素类别。
     * 
     * 
     * @param  attributes  Array of attribute values to add to the set.
     *                     If null, an empty attribute set is constructed.
     *
     * @exception  NullPointerException
     *  (unchecked exception)
     * Thrown if any element of <CODE>attributes</CODE> is null.
     */
    public HashDocAttributeSet(DocAttribute[] attributes) {
        super (attributes, DocAttribute.class);
    }

    /**
     * Construct a new attribute set, initially populated with the
     * values from the  given set where the members of the attribute set
     * are restricted to the <code>DocAttribute</code> interface.
     *
     * <p>
     * 
     * @param  attributes set of attribute values to initialise the set. If
     *                    null, an empty attribute set is constructed.
     *
     * @exception  ClassCastException
     *     (unchecked exception) Thrown if any element of
     * <CODE>attributes</CODE> is not an instance of
     * <CODE>DocAttribute</CODE>.
     */
    public HashDocAttributeSet(DocAttributeSet attributes) {
        super(attributes, DocAttribute.class);
    }

}
