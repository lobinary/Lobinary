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
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobHoldUntil is a printing attribute class, a date-time attribute, that
 * specifies the exact date and time at which the job must become a candidate
 * for printing.
 * <P>
 * If the value of this attribute specifies a date-time that is in the future,
 * the printer should add the {@link JobStateReason JobStateReason} value of
 * JOB_HOLD_UNTIL_SPECIFIED to the job's {@link JobStateReasons JobStateReasons}
 * attribute, must move the job to the PENDING_HELD state, and must not schedule
 * the job for printing until the specified date-time arrives.
 * <P>
 * When the specified date-time arrives, the printer must remove the {@link
 * JobStateReason JobStateReason} value of JOB_HOLD_UNTIL_SPECIFIED from the
 * job's {@link JobStateReasons JobStateReasons} attribute, if present. If there
 * are no other job state reasons that keep the job in the PENDING_HELD state,
 * the printer must consider the job as a candidate for processing by moving the
 * job to the PENDING state.
 * <P>
 * If the specified date-time has already passed, the job must be a candidate
 * for processing immediately. Thus, one way to make the job immediately become
 * a candidate for processing is to specify a JobHoldUntil attribute constructed
 * like this (denoting a date-time of January 1, 1970, 00:00:00 GMT):
 * <PRE>
 *     JobHoldUntil immediately = new JobHoldUntil (new Date (0L));
 * </PRE>
 * <P>
 * If the client does not supply this attribute in a Print Request and the
 * printer supports this attribute, the printer must use its
 * (implementation-dependent) default JobHoldUntil value at job submission time
 * (unlike most job template attributes that are used if necessary at job
 * processing time).
 * <P>
 * To construct a JobHoldUntil attribute from separate values of the year,
 * month, day, hour, minute, and so on, use a {@link java.util.Calendar
 * Calendar} object to construct a {@link java.util.Date Date} object, then use
 * the {@link java.util.Date Date} object to construct the JobHoldUntil
 * attribute. To convert a JobHoldUntil attribute to separate values of the
 * year, month, day, hour, minute, and so on, create a {@link java.util.Calendar
 * Calendar} object and set it to the {@link java.util.Date Date} from the
 * JobHoldUntil attribute.
 * <P>
 * <B>IPP Compatibility:</B> Although IPP supports a "job-hold-until" attribute
 * specified as a keyword, IPP does not at this time support a "job-hold-until"
 * attribute specified as a date and time. However, the date and time can be
 * converted to one of the standard IPP keywords with some loss of precision;
 * for example, a JobHoldUntil value with today's date and 9:00pm local time
 * might be converted to the standard IPP keyword "night". The category name
 * returned by <CODE>getName()</CODE> gives the IPP attribute name.
 * <P>
 *
 * <p>
 *  JobHoldUntil类是一个打印属性类,一个日期时间属性,它指定作业必须成为打印候选项的确切日期和时间。
 * <P>
 *  如果此属性的值指定了将来的日期时间,则打印机应将JOB_HOLD_UNTIL_SPECIFIED的{@link JobStateReason JobStateReason}值添加到作业的{@link JobStateReasons JobStateReasons}
 * 属性,必须将作业移动到PENDING_HELD状态,并且必须在指定的日期时间到达之前不调度作业进行打印。
 * <P>
 *  当指定的日期时间到达时,打印机必须从作业的{@link JobStateReasons JobStateReasons}属性(如果存在)中删除JOB_HOLD_UNTIL_SPECIFIED的{@link JobStateReason JobStateReason}
 * 值。
 * 如果没有将作业保持在PENDING_HELD状态的其他作业状态原因,则打印机必须将作业移动到PENDING状态,以将该作业视为候选处理。
 * <P>
 *  如果指定的日期时间已过,则作业必须是立即处理的候选。
 * 因此,使作业立即成为处理候选的一种方式是指定如此构造的JobHoldUntil属性(表示1970年1月1日,00:00:00 GMT的日期 - 时间)：。
 * <PRE>
 *  JobHoldUntil immediately = new JobHoldUntil(new Date(0L));
 * </PRE>
 * <P>
 * 如果客户端在打印请求中不提供此属性,并且打印机支持此属性,则打印机必须在作业提交时使用其(实现相关的)默认JobHoldUntil值(与在作业处理时根据需要使用的大多数作业模板属性不同)时间)。
 * <P>
 *  要使用年,月,日,小时,分钟等的不同值来构造JobHoldUntil属性,请使用{@link java.util.Calendar Calendar}对象构造一个{@link java.util.Date Date}
 * 对象,然后使用{@link java.util.Date Date}对象构造JobHoldUntil属性。
 * 要将JobHoldUntil属性转换为年,月,日,小时,分钟等的不同值,请创建{@link java.util.Calendar Calendar}对象,并将其设置为{@link java.util.Date Date}
 * 从JobHoldUntil属性。
 * <P>
 *  <B> IPP兼容性：</B>虽然IPP支持指定为关键字的"job-hold-until"属性,但IPP目前不支持指定为日期和时间的"job-hold-until"属性。
 * 但是,日期和时间可以转换为一个标准的IPP关键字,有一定的精度损失;例如,具有今天日期和当地时间下午9:00的JobHoldUntil值可以被转换为标准IPP关键字"night"。
 * 由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * @author  Alan Kaminsky
 */
public final class JobHoldUntil extends DateTimeSyntax
        implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -1664471048860415024L;


    /**
     * Construct a new job hold until date-time attribute with the given
     * {@link java.util.Date Date} value.
     *
     * <p>
     * 
     * 
     * @param  dateTime  {@link java.util.Date Date} value.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>dateTime</CODE> is null.
     */
    public JobHoldUntil(Date dateTime) {
        super (dateTime);
    }

    /**
     * Returns whether this job hold until attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobHoldUntil.
     * <LI>
     * This job hold until attribute's {@link java.util.Date Date} value and
     * <CODE>object</CODE>'s {@link java.util.Date Date} value are equal.
     * </OL>
     *
     * <p>
     *  使用给定的{@link java.util.Date Date}值构造一个新的作业暂停直到date-time属性。
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job hold
     *          until attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof JobHoldUntil);
    }


    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobHoldUntil, the category is class JobHoldUntil itself.
     *
     * <p>
     * 返回此作业是否保持,直到属性等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobHoldUntil类的实例。
     * <LI>
     *  此作业保持到属性的{@link java.util.Date Date}值和<CODE>对象</CODE>的{@link java.util.Date Date}值相等。
     * </OL>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobHoldUntil.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobHoldUntil, the category name is <CODE>"job-hold-until"</CODE>.
     *
     * <p>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-hold-until";
    }

}
