/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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
package javax.print.attribute.standard;

import javax.print.attribute.Attribute;
import javax.print.attribute.SetOfIntegerSyntax;
import javax.print.attribute.SupportedValuesAttribute;

/**
 * Class NumberUpSupported is a printing attribute class, a set of integers,
 * that gives the supported values for a {@link NumberUp NumberUp} attribute.
 * <P>
 * <B>IPP Compatibility:</B> The NumberUpSupported attribute's canonical array
 * form gives the lower and upper bound for each range of number-up to be
 * included in an IPP "number-up-supported" attribute. See class {@link
 * javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax} for an
 * explanation of canonical array form. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  NumberUpSupported类是一个打印属性类,一组整数,为{@link NumberUp NumberUp}属性提供受支持的值。
 * <P>
 *  <B> IPP兼容性：</B> NumberUpSupported属性的规范数组形式给出了每个向上范围的下限和上限,以包含在IPP"编号向上支持"属性中。
 * 有关规范数组形式的解释,请参阅{@link javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax}类。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class NumberUpSupported    extends SetOfIntegerSyntax
        implements SupportedValuesAttribute {

     private static final long serialVersionUID = -1041573395759141805L;


    /**
     * Construct a new number up supported attribute with the given members.
     * The supported values for NumberUp are specified in "array form;" see
     * class
     * {@link javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax}
     * for an explanation of array form.
     *
     * <p>
     *  使用给定成员构造支持的新数字上限属性。
     *  NumberUp支持的值在"数组形式"中指定;有关数组形式的说明,请参阅类{@link javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax}
     * 。
     *  使用给定成员构造支持的新数字上限属性。
     * 
     * 
     * @param  members  Set members in array form.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>members</CODE> is null or
     *     any element of <CODE>members</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if any element of
     *   <CODE>members</CODE> is not a length-one or length-two array. Also
     *    thrown if <CODE>members</CODE> is a zero-length array or if any
     *    member of the set is less than 1.
     */
    public NumberUpSupported(int[][] members) {
        super (members);
        if (members == null) {
            throw new NullPointerException("members is null");
        }
        int[][] myMembers = getMembers();
        int n = myMembers.length;
        if (n == 0) {
            throw new IllegalArgumentException("members is zero-length");
        }
        int i;
        for (i = 0; i < n; ++ i) {
            if (myMembers[i][0] < 1) {
                throw new IllegalArgumentException
                    ("Number up value must be > 0");
            }
        }
    }

    /**
     * Construct a new number up supported attribute containing a single
     * integer. That is, only the one value of NumberUp is supported.
     *
     * <p>
     *  构造包含单个整数的支持的新数字。也就是说,只支持NumberUp的一个值。
     * 
     * 
     * @param  member  Set member.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if <CODE>member</CODE> is less than
     *     1.
     */
    public NumberUpSupported(int member) {
        super (member);
        if (member < 1) {
            throw new IllegalArgumentException("Number up value must be > 0");
        }
    }

    /**
     * Construct a new number up supported attribute containing a single range
     * of integers. That is, only those values of NumberUp in the one range are
     * supported.
     *
     * <p>
     *  构造包含单个整数范围的支持的新数字上限属性。也就是说,只支持在一个范围内的NumberUp的值。
     * 
     * 
     * @param  lowerBound  Lower bound of the range.
     * @param  upperBound  Upper bound of the range.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if a null range is specified or if a
     *     non-null range is specified with <CODE>lowerBound</CODE> less than
     *     1.
     */
    public NumberUpSupported(int lowerBound, int upperBound) {
        super (lowerBound, upperBound);
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Null range specified");
        } else if (lowerBound < 1) {
            throw new IllegalArgumentException
                ("Number up value must be > 0");
        }
    }

    /**
     * Returns whether this number up supported attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class NumberUpSupported.
     * <LI>
     * This number up supported attribute's members and <CODE>object</CODE>'s
     * members are the same.
     * </OL>
     *
     * <p>
     *  返回此数字是否支持的属性等于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是NumberUpSupported类的实例。
     * <LI>
     * 此数字支持属性的成员和<CODE>对象</CODE>的成员是相同的。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this number up
     *          supported attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof NumberUpSupported);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class NumberUpSupported, the
     * category is class NumberUpSupported itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return NumberUpSupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class NumberUpSupported, the
     * category name is <CODE>"number-up-supported"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于NumberUpSupported类,类别是NumberUpSupported类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "number-up-supported";
    }

}
