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
 * Class HashPrintRequestAttributeSet inherits its implementation from
 * class {@link HashAttributeSet HashAttributeSet} and enforces the
 * semantic restrictions of interface
 * {@link PrintRequestAttributeSet PrintRequestAttributeSet}.
 * <P>
 *
 * <p>
 *  类HashPrintRequestAttributeSet从类{@link HashAttributeSet HashAttributeSet}继承其实现,并强制执行接口{@link PrintRequestAttributeSet PrintRequestAttributeSet}
 * 的语义限制。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class HashPrintRequestAttributeSet extends HashAttributeSet
    implements PrintRequestAttributeSet, Serializable {

    private static final long serialVersionUID = 2364756266107751933L;

    /**
     * Construct a new, empty print request attribute set.
     * <p>
     *  构造一个新的,空的打印请求属性集。
     * 
     */
    public HashPrintRequestAttributeSet() {
        super (PrintRequestAttribute.class);
    }

    /**
     * Construct a new print request attribute set,
     * initially populated with the given value.
     *
     * <p>
     *  构造新的打印请求属性集,最初使用给定值填充。
     * 
     * 
     * @param  attribute  Attribute value to add to the set.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>attribute</CODE> is null.
     */
    public HashPrintRequestAttributeSet(PrintRequestAttribute attribute) {
        super (attribute, PrintRequestAttribute.class);
    }

    /**
     * Construct a new print request attribute set, initially populated with
     * the values from the given array. The new attribute set is populated
     * by adding the elements of <CODE>attributes</CODE> array to the set in
     * sequence, starting at index 0. Thus, later array elements may replace
     * earlier array elements if the array contains duplicate attribute
     * values or attribute categories.
     *
     * <p>
     *  构造新的打印请求属性集,最初使用给定数组中的值填充。通过从索引0开始将<CODE> attributes </CODE>数组的元素添加到序列中的集合来填充新的属性集。
     * 因此,如果数组包含重复的属性值或属性,则稍后的数组元素可以替换先前的数组元素类别。
     * 
     * 
     * @param  attributes  Array of attribute values to add to the set.
     *                     If null, an empty attribute set is constructed.
     *
     * @exception  NullPointerException
     *     (unchecked exception)
     * Thrown if any element of <CODE>attributes</CODE> is null.
     */
    public HashPrintRequestAttributeSet(PrintRequestAttribute[] attributes) {
        super (attributes, PrintRequestAttribute.class);
    }


    /**
     * Construct a new attribute set, initially populated with the
     * values from the  given set where the members of the attribute set
     * are restricted to the <code>(PrintRequestAttributeSe</code> interface.
     *
     * <p>
     * 
     * @param  attributes set of attribute values to initialise the set. If
     *                    null, an empty attribute set is constructed.
     *
     * @exception  ClassCastException
     *     (unchecked exception) Thrown if any element of
     * <CODE>attributes</CODE> is not an instance of
     * <CODE>(PrintRequestAttributeSe</CODE>.
     */
    public HashPrintRequestAttributeSet(PrintRequestAttributeSet attributes)
    {
        super(attributes, PrintRequestAttribute.class);
    }


}
