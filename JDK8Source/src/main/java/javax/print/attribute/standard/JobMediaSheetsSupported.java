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
 * Class JobMediaSheetsSupported is a printing attribute class, a set of
 * integers, that gives the supported values for a {@link JobMediaSheets
 * JobMediaSheets} attribute. It is restricted to a single contiguous range of
 * integers; multiple non-overlapping ranges are not allowed. This gives the
 * lower and upper bounds of the total sizes of print jobs in number of media
 * sheets that the printer will accept.
 * <P>
 * <B>IPP Compatibility:</B> The JobMediaSheetsSupported attribute's canonical
 * array form gives the lower and upper bound for the range of values to be
 * included in an IPP "job-media-sheets-supported" attribute. See class {@link
 * javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax} for an
 * explanation of canonical array form. The category name returned by
 * <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  JobMediaSheetsSupported类是一个打印属性类,一组整数,为{@link JobMediaSheets JobMediaSheets}属性提供受支持的值。
 * 它限于单个连续的整数范围;不允许多个不重叠范围。这给出了打印机将接受的打印作业数量的打印作业总大小的下限和上限。
 * <P>
 *  <B> IPP兼容性：</B> JobMediaSheetsSupported属性的规范数组形式给出了要包含在IPP"job-media-sheets-supported"属性中的值范围的下限和上限。
 * 有关规范数组形式的解释,请参阅{@link javax.print.attribute.SetOfIntegerSyntax SetOfIntegerSyntax}类。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class JobMediaSheetsSupported extends SetOfIntegerSyntax
        implements SupportedValuesAttribute {

    private static final long serialVersionUID = 2953685470388672940L;

    /**
     * Construct a new job media sheets supported attribute containing a single
     * range of integers. That is, only those values of JobMediaSheets in the
     * one range are supported.
     *
     * <p>
     *  构造包含单个整数范围的新作业媒体工作表支持属性。也就是说,只支持一个范围中的JobMediaSheets的值。
     * 
     * 
     * @param  lowerBound  Lower bound of the range.
     * @param  upperBound  Upper bound of the range.
     *
     * @exception  IllegalArgumentException
     *  (Unchecked exception) Thrown if a null range is specified or if a
     *   non-null range is specified with <CODE>lowerBound</CODE> less than
     *    0.
     */
    public JobMediaSheetsSupported(int lowerBound, int upperBound) {
        super (lowerBound, upperBound);
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Null range specified");
        } else if (lowerBound < 0) {
            throw new IllegalArgumentException
                                ("Job K octets value < 0 specified");
        }
    }

    /**
     * Returns whether this job media sheets supported attribute is equivalent
     * to the passed in object. To be equivalent, all of the following
     * conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobMediaSheetsSupported.
     * <LI>
     * This job media sheets supported attribute's members and
     * <CODE>object</CODE>'s members are the same.
     * </OL>
     *
     * <p>
     *  返回此作业媒体工作表支持的属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE> object </CODE>是JobMediaSheetsSupported类的实例。
     * <LI>
     *  此作业介质表支持属性的成员和<CODE>对象</CODE>的成员是相同的。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job media
     *          sheets supported attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof JobMediaSheetsSupported);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobMediaSheetsSupported, the
     * category is class JobMediaSheetsSupported itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobMediaSheetsSupported.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobMediaSheetsSupported, the
     * category name is <CODE>"job-media-sheets-supported"</CODE>.
     *
     * <p>
     * 获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobMediaSheetsSupported类,类别是JobMediaSheetsSupported类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-media-sheets-supported";
    }

}
