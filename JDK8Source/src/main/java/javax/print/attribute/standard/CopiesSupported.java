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
 * Class CopiesSupported is a printing attribute class, a set of integers, that
 * gives the supported values for a {@link Copies Copies} attribute. It is
 * restricted to a single contiguous range of integers; multiple non-overlapping
 * ranges are not allowed.
 * <P>
 * <B>IPP Compatibility:</B> The CopiesSupported attribute's canonical array
 * form gives the lower and upper bound for the range of copies to be included
 * in an IPP "copies-supported" attribute. See class {@link
 * javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax} for an
 * explanation of canonical array form. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  类CopiesSupported是一个打印属性类,一组整数,为{@link Copies Copies}属性提供受支持的值。它限于单个连续的整数范围;不允许多个不重叠范围。
 * <P>
 *  <B> IPP兼容性：</B> CopiesSupported属性的规范数组形式给出了包含在IPP"copies-supported"属性中的副本范围的下限和上限。
 * 有关规范数组形式的解释,请参阅{@link javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax}类。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class CopiesSupported extends SetOfIntegerSyntax
        implements SupportedValuesAttribute {

    private static final long serialVersionUID = 6927711687034846001L;

    /**
     * Construct a new copies supported attribute containing a single integer.
     * That is, only the one value of Copies is supported.
     *
     * <p>
     *  构造包含单个整数的支持新副本的属性。也就是说,只支持Copies的一个值。
     * 
     * 
     * @param  member  Set member.
     *
     * @exception  IllegalArgumentException
     *  (Unchecked exception) Thrown if <CODE>member</CODE> is less than 1.
     */
    public CopiesSupported(int member) {
        super (member);
        if (member < 1) {
            throw new IllegalArgumentException("Copies value < 1 specified");
        }
    }

    /**
     * Construct a new copies supported attribute containing a single range of
     * integers. That is, only those values of Copies in the one range are
     * supported.
     *
     * <p>
     *  构造包含单个整数范围的新副本支持属性。也就是说,只支持一个范围中的副本的那些值。
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
    public CopiesSupported(int lowerBound, int upperBound) {
        super(lowerBound, upperBound);

        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Null range specified");
        } else if (lowerBound < 1) {
            throw new IllegalArgumentException("Copies value < 1 specified");
        }
    }

    /**
     * Returns whether this copies supported attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions must
     * be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class CopiesSupported.
     * <LI>
     * This copies supported attribute's members and <CODE>object</CODE>'s
     * members are the same.
     * </OL>
     *
     * <p>
     *  返回此副本支持的属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是类CopiesSupported的一个实例。
     * <LI>
     *  这会复制受支持属性的成员,并且<CODE>对象</CODE>的成员相同。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this copies
     *          supported attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return super.equals (object) && object instanceof CopiesSupported;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class CopiesSupported, the category
     * is class CopiesSupported itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return CopiesSupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class CopiesSupported, the category
     * name is <CODE>"copies-supported"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类CopiesSupported,类别是类CopiesSupported本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "copies-supported";
    }

}
