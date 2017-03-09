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

package javax.print.attribute;

import java.io.Serializable;

/**
 * Class IntegerSyntax is an abstract base class providing the common
 * implementation of all attributes with integer values.
 * <P>
 * Under the hood, an integer attribute is just an integer. You can get an
 * integer attribute's integer value by calling {@link #getValue()
 * getValue()}. An integer attribute's integer value is
 * established when it is constructed (see {@link #IntegerSyntax(int)
 * IntegerSyntax(int)}). Once constructed, an integer attribute's
 * value is immutable.
 * <P>
 *
 * <p>
 *  Class IntegerSyntax是一个抽象基类,提供了所有带有整数值的属性的通用实现。
 * <P>
 *  在引擎盖下,整数属性只是一个整数。您可以通过调用{@link #getValue()getValue()}获取整数属性的整数值。
 * 整数属性的整数值在构造时建立(参见{@link #IntegerSyntax(int)IntegerSyntax(int)})。一旦构造,整数属性的值是不可变的。
 * <P>
 * 
 * 
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public abstract class IntegerSyntax implements Serializable, Cloneable {

    private static final long serialVersionUID = 3644574816328081943L;

    /**
     * This integer attribute's integer value.
     * <p>
     *  此整数属性的整数值。
     * 
     * 
     * @serial
     */
    private int value;

    /**
     * Construct a new integer attribute with the given integer value.
     *
     * <p>
     *  构造具有给定整数值的新整数属性。
     * 
     * 
     * @param  value  Integer value.
     */
    protected IntegerSyntax(int value) {
        this.value = value;
    }

    /**
     * Construct a new integer attribute with the given integer value, which
     * must lie within the given range.
     *
     * <p>
     *  构造具有给定整数值的新整数属性,该整数值必须在给定范围内。
     * 
     * 
     * @param  value       Integer value.
     * @param  lowerBound  Lower bound.
     * @param  upperBound  Upper bound.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>value</CODE> is less than
     *     <CODE>lowerBound</CODE> or greater than
     *     <CODE>upperBound</CODE>.
     */
    protected IntegerSyntax(int value, int lowerBound, int upperBound) {
        if (lowerBound > value || value > upperBound) {
            throw new IllegalArgumentException("Value " + value +
                                               " not in range " + lowerBound +
                                               ".." + upperBound);
        }
        this.value = value;
    }

    /**
     * Returns this integer attribute's integer value.
     * <p>
     *  返回此整数属性的整数值。
     * 
     * 
     * @return the integer value
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns whether this integer attribute is equivalent to the passed in
     * object. To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class IntegerSyntax.
     * <LI>
     * This integer attribute's value and <CODE>object</CODE>'s value are
     * equal.
     * </OL>
     *
     * <p>
     *  返回此整数属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是类IntegerSyntax的一个实例。
     * <LI>
     *  此整数属性的值和<CODE>对象</CODE>的值相等。
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this integer
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {

        return (object != null && object instanceof IntegerSyntax &&
                value == ((IntegerSyntax) object).value);
    }

    /**
     * Returns a hash code value for this integer attribute. The hash code is
     * just this integer attribute's integer value.
     * <p>
     * </OL>
     * 
     */
    public int hashCode() {
        return value;
    }

    /**
     * Returns a string value corresponding to this integer attribute. The
     * string value is just this integer attribute's integer value converted to
     * a string.
     * <p>
     *  返回此整数属性的哈希码值。哈希码只是这个整数属性的整数值。
     * 
     */
    public String toString() {
        return "" + value;
    }
}
