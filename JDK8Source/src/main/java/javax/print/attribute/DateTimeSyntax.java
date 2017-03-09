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

import java.util.Date;

/**
 * Class DateTimeSyntax is an abstract base class providing the common
 * implementation of all attributes whose value is a date and time.
 * <P>
 * Under the hood, a date-time attribute is stored as a value of class <code>
 * java.util.Date</code>. You can get a date-time attribute's Date value by
 * calling {@link #getValue() getValue()}. A date-time attribute's
 * Date value is established when it is constructed (see {@link
 * #DateTimeSyntax(Date) DateTimeSyntax(Date)}). Once
 * constructed, a date-time attribute's value is immutable.
 * <P>
 * To construct a date-time attribute from separate values of the year, month,
 * day, hour, minute, and so on, use a <code>java.util.Calendar</code>
 * object to construct a <code>java.util.Date</code> object, then use the
 * <code>java.util.Date</code> object to construct the date-time attribute.
 * To convert
 * a date-time attribute to separate values of the year, month, day, hour,
 * minute, and so on, create a <code>java.util.Calendar</code> object and
 * set it to the <code>java.util.Date</code> from the date-time attribute. Class
 * DateTimeSyntax stores its value in the form of a <code>java.util.Date
 * </code>
 * rather than a <code>java.util.Calendar</code> because it typically takes
 * less memory to store and less time to compare a <code>java.util.Date</code>
 * than a <code>java.util.Calendar</code>.
 * <P>
 *
 * <p>
 *  类DateTimeSyntax是一个抽象基类,提供其值为日期和时间的所有属性的通用实现。
 * <P>
 *  在引擎盖下,日期时间属性被存储为<code> java.util.Date </code>类的值。
 * 您可以通过调用{@link #getValue()getValue()}获取日期时间属性的Date值。
 * 日期时间属性的Date值在构造时建立(请参阅{@link #DateTimeSyntax(Date)DateTimeSyntax(Date)})。一旦构造,日期时间属性的值是不可变的。
 * <P>
 *  要使用年,月,日,小时,分钟等的不同值来构造日期时间属性,请使用<code> java.util.Calendar </code>对象构造一个<code> java.util .Date </code>
 * 对象,然后使用<code> java.util.Date </code>对象来构造日期时间属性。
 * 要将日期时间属性转换为年,月,日,小时,分钟等的不同值,请创建一个<code> java.util.Calendar </code>对象并将其设置为<code> java .util.Date </code>
 * 。
 * 类DateTimeSyntax以一个<code> java.util.Date的形式存储它的值。
 * </code>
 *  而不是一个<code> java.util.Calendar </code>,因为它通常比一个<code> java.util需要更少的内存和更少的时间来比较<code> java.util.Date
 *  </code>。
 * 日历</code>。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public abstract class DateTimeSyntax implements Serializable, Cloneable {

    private static final long serialVersionUID = -1400819079791208582L;

    // Hidden data members.

    /**
     * This date-time attribute's<code>java.util.Date</code> value.
     * <p>
     * 此日期时间属性的<code> java.util.Date </code>值。
     * 
     * 
     * @serial
     */
    private Date value;

    // Hidden constructors.

    /**
     * Construct a new date-time attribute with the given
     * <code>java.util.Date </code> value.
     *
     * <p>
     *  使用给定的<code> java.util.Date </code>值构造一个新的日期时间属性。
     * 
     * 
     * @param  value   <code>java.util.Date</code> value.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>theValue</CODE> is null.
     */
    protected DateTimeSyntax(Date value) {
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        this.value = value;
    }

    // Exported operations.

    /**
     * Returns this date-time attribute's <code>java.util.Date</code>
     * value.
     * <p>
     *  返回此日期时间属性的<code> java.util.Date </code>值。
     * 
     * 
     * @return the Date.
     */
    public Date getValue() {
        return new Date (value.getTime());
    }

    // Exported operations inherited and overridden from class Object.

    /**
     * Returns whether this date-time attribute is equivalent to the passed in
     * object. To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class DateTimeSyntax.
     * <LI>
     * This date-time attribute's <code>java.util.Date</code> value and
     * <CODE>object</CODE>'s <code>java.util.Date</code> value are
     * equal. </OL>
     *
     * <p>
     *  返回此日期时间属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE> object </CODE>是DateTimeSyntax类的实例。
     * <LI>
     *  此日期时间属性的<code> java.util.Date </code>值和<CODE>对象</CODE>的<code> java.util.Date </code>值相等。 </OL>
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this date-time
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (object != null &&
                object instanceof DateTimeSyntax &&
                value.equals(((DateTimeSyntax) object).value));
    }

    /**
     * Returns a hash code value for this date-time attribute. The hashcode is
     * that of this attribute's <code>java.util.Date</code> value.
     * <p>
     * 
     */
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns a string value corresponding to this date-time attribute.
     * The string value is just this attribute's
     * <code>java.util.Date</code>  value
     * converted to a string.
     * <p>
     *  返回此日期时间属性的哈希码值。哈希码是该属性的<code> java.util.Date </code>值。
     * 
     */
    public String toString() {
        return "" + value;
    }

}
