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
 * Class JobImpressionsSupported is a printing attribute class, a set of
 * integers, that gives the supported values for a {@link JobImpressions
 * JobImpressions} attribute. It is restricted to a single contiguous range of
 * integers; multiple non-overlapping ranges are not allowed. This gives the
 * lower and upper bounds of the total sizes of print jobs in number of
 * impressions that the printer will accept.
 * <P>
 * <B>IPP Compatibility:</B> The JobImpressionsSupported attribute's canonical
 * array form gives the lower and upper bound for the range of values to be
 * included in an IPP "job-impressions-supported" attribute. See class {@link
 * javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax} for an
 * explanation of canonical array form. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  JobImpressionsSupported类是一个打印属性类,一组整数,给出{@link JobImpressions JobImpressions}属性支持的值。
 * 它限于单个连续的整数范围;不允许多个不重叠范围。这给出了打印机将接受的打印数量的打印作业的总大小的下限和上限。
 * <P>
 *  <B> IPP兼容性：</B> JobImpressionsSupported属性的规范数组形式给出了要包含在IPP"job-impressions-supported"属性中的值范围的下限和上限。
 * 有关规范数组形式的解释,请参阅{@link javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax}类。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class JobImpressionsSupported extends SetOfIntegerSyntax
        implements SupportedValuesAttribute {

    private static final long serialVersionUID = -4887354803843173692L;


    /**
     * Construct a new job impressions supported attribute containing a single
     * range of integers. That is, only those values of JobImpressions in the
     * one range are supported.
     *
     * <p>
     *  构造包含单个整数范围的新作业展示支持属性。也就是说,只支持一个范围中的JobImpressions的那些值。
     * 
     * 
     * @param  lowerBound  Lower bound of the range.
     * @param  upperBound  Upper bound of the range.
     *
     * @exception  IllegalArgumentException
     *     (Unchecked exception) Thrown if a null range is specified or if a
     *     non-null range is specified with <CODE>lowerBound</CODE> less than
     *     0.
     */
    public JobImpressionsSupported(int lowerBound, int upperBound) {
        super (lowerBound, upperBound);
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Null range specified");
        } else if (lowerBound < 0) {
            throw new IllegalArgumentException(
                                         "Job K octets value < 0 specified");
        }
    }


    /**
     * Returns whether this job impressions supported attribute is equivalent
     * to the passed in object. To be equivalent, all of the following
     * conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobImpressionsSupported.
     * <LI>
     * This job impressions supported attribute's members and
     * <CODE>object</CODE>'s members are the same.
     * </OL>
     *
     * <p>
     *  返回此作业展示支持的属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobImpressionsSupported类的实例。
     * <LI>
     *  此作业展示支持的属性的成员和<CODE>对象</CODE>的成员相同。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job
     *          impressions supported attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof JobImpressionsSupported);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobImpressionsSupported, the category is class
     * JobImpressionsSupported itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobImpressionsSupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobImpressionsSupported, the category name is
     * <CODE>"job-impressions-supported"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobImpressionsSupported类,类别是JobImpressionsSupported类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-impressions-supported";
    }

}
