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

import java.util.Date;
import javax.print.attribute.Attribute;
import javax.print.attribute.DateTimeSyntax;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class DateTimeAtCompleted is a printing attribute class, a date-time
 * attribute, that indicates the date and time at which the Print Job completed
 * (or was canceled or aborted).
 * <P>
 * To construct a DateTimeAtCompleted attribute from separate values of the
 * year, month, day, hour, minute, and so on, use a {@link java.util.Calendar
 * Calendar} object to construct a {@link java.util.Date Date} object, then use
 * the {@link java.util.Date Date} object to construct the DateTimeAtCompleted
 * attribute. To convert a DateTimeAtCompleted attribute to separate values of
 * the year, month, day, hour, minute, and so on, create a {@link
 * java.util.Calendar Calendar} object and set it to the {@link java.util.Date
 * Date} from the DateTimeAtCompleted attribute.
 * <P>
 * <B>IPP Compatibility:</B> The information needed to construct an IPP
 * "date-time-at-completed" attribute can be obtained as described above. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  类DateTimeAtCompleted是打印属性类,日期时间属性,指示打印作业完成(或已取消或中止)的日期和时间。
 * <P>
 *  要从年,月,日,小时,分钟等不同的值构造DateTimeAtCompleted属性,请使用{@link java.util.Calendar Calendar}对象构造一个{@link java.util.Date Date}
 * 对象,然后使用{@link java.util.Date Date}对象构造DateTimeAtCompleted属性。
 * 要将DateTimeAtCompleted属性转换为年,月,日,小时,分钟等的单独值,请创建{@link java.util.Calendar Calendar}对象,并将其设置为{@link java.util.Date DateTime从DateTimeAtCompleted属性。
 * <P>
 *  <B> IPP兼容性：</B>可以如上所述获得构建IPP"日期时间完成"属性所需的信息。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class DateTimeAtCompleted extends DateTimeSyntax
    implements PrintJobAttribute {

    private static final long serialVersionUID = 6497399708058490000L;

    /**
     * Construct a new date-time at completed attribute with the given {@link
     * java.util.Date Date} value.
     *
     * <p>
     *  使用给定的{@link java.util.Date Date}值在completed属性处构造新的日期时间。
     * 
     * 
     * @param  dateTime  {@link java.util.Date Date} value.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>dateTime</CODE> is null.
     */
    public DateTimeAtCompleted(Date dateTime) {
        super (dateTime);
    }

    /**
     * Returns whether this date-time at completed attribute is equivalent to
     * the passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class DateTimeAtCompleted.
     * <LI>
     * This date-time at completed attribute's {@link java.util.Date Date} value
     * and <CODE>object</CODE>'s {@link java.util.Date Date} value are equal.
     * </OL>
     *
     * <p>
     *  返回此完成属性的日期时间是否等于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是类DateTimeAtCompleted的实例。
     * <LI>
     * 完成属性的{@link java.util.Date Date}值和<CODE>对象</CODE>的{@link java.util.Date Date}值的日期时间是相等的。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this date-time
     *          at completed attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return(super.equals (object) &&
               object instanceof DateTimeAtCompleted);
    }

// Exported operations inherited and implemented from interface Attribute.

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
         * <P>
         * For class DateTimeAtCompleted, the category is class
         * DateTimeAtCompleted itself.
         *
         * <p>
         * 
         * @return  Printing attribute class (category), an instance of class
         *          {@link java.lang.Class java.lang.Class}.
         */
    public final Class<? extends Attribute> getCategory() {
        return DateTimeAtCompleted.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class DateTimeAtCompleted, the category name is
     * <CODE>"date-time-at-completed"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类DateTimeAtCompleted,类别是类DateTimeAtCompleted本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "date-time-at-completed";
    }

}
