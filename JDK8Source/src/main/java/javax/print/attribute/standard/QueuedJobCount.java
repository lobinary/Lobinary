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
import javax.print.attribute.IntegerSyntax;
import javax.print.attribute.PrintServiceAttribute;

/**
 * Class QueuedJobCount is an integer valued printing attribute that indicates
 * the number of jobs in the printer whose {@link JobState JobState} is either
 * PENDING, PENDING_HELD, PROCESSING, or PROCESSING_STOPPED.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value.
 * The category name returned by <CODE>getName()</CODE> gives the IPP
 * attribute name.
 * <P>
 *
 * <p>
 *  类QueuedJobCount是整数值打印属性,表示打印机中{@link JobState JobState}为PENDING,PENDING_HELD,PROCESSING或PROCESSING_S
 * TOPPED的作业数。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public final class QueuedJobCount extends IntegerSyntax
    implements PrintServiceAttribute {

    private static final long serialVersionUID = 7499723077864047742L;

    /**
     * Construct a new queued job count attribute with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的排队作业计数属性。
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if <CODE>value</CODE> is less than 0.
     */
    public QueuedJobCount(int value) {
        super (value, 0, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this queued job count attribute is equivalent to the
     * passed in object. To be equivalent, all of the following conditions
     * mus  be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class QueuedJobCount.
     * <LI>
     * This queued job count attribute's value and <CODE>object</CODE>'s
     * value are equal.
     * </OL>
     *
     * <p>
     *  返回此排队作业计数属性是否等同于传入的对象。为了等效,所有以下条件都成立：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE> object </CODE>是QueuedJobCount类的实例。
     * <LI>
     *  此排队作业计数属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this queued job
     *          count attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
               object instanceof QueuedJobCount);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class QueuedJobCount, the category is class QueuedJobCount itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return QueuedJobCount.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class QueuedJobCount, the
     * category name is <CODE>"queued-job-count"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于QueuedJobCount类,类别是QueuedJobCount类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "queued-job-count";
    }

}
