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
import javax.print.attribute.PrintJobAttribute;

/**
 * Class JobMediaSheetsCompleted is an integer valued printing attribute class
 * that specifies the number of media sheets which have completed marking and
 * stacking for the entire job so far, whether those sheets have been processed
 * on one side or on both.
 * <P>
 * The JobMediaSheetsCompleted attribute describes the progress of the job. This
 * attribute is intended to be a counter. That is, the JobMediaSheetsCompleted
 * value for a job that has not started processing must be 0. When the job's
 * {@link JobState JobState} is PROCESSING or PROCESSING_STOPPED, the
 * JobMediaSheetsCompleted value is intended to increase as the job is
 * processed; it indicates the amount of the job that has been processed at the
 * time the Print Job's attribute set is queried or at the time a print job
 * event is reported. When the job enters the COMPLETED, CANCELED, or ABORTED
 * states, the JobMediaSheetsCompleted value is the final value for the job.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  JobMediaSheetsCompleted类是一个整数值打印属性类,它指定到目前为止已完成整个作业的标记和堆叠的介质页数,无论这些页面是在一面还是在两面上处理。
 * <P>
 *  JobMediaSheetsCompleted属性描述作业的进度。此属性旨在作为计数器。
 * 也就是说,尚未开始处理的作业的JobMediaSheetsCompleted值必须为0.当作业的{@link JobState JobState}为PROCESSING或PROCESSING_STOPP
 * ED时,JobMediaSheetsCompleted值将随着作业处理而增加;它指示在查询打印作业属性集时或在报告打印作业事件时已处理的作业量。
 *  JobMediaSheetsCompleted属性描述作业的进度。此属性旨在作为计数器。当作业进入完成,取消或停止状态时,JobMediaSheetsCompleted值是作业的最终值。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @see JobMediaSheets
 * @see JobMediaSheetsSupported
 * @see JobKOctetsProcessed
 * @see JobImpressionsCompleted
 *
 * @author  Alan Kaminsky
 */
public final class JobMediaSheetsCompleted extends IntegerSyntax
        implements PrintJobAttribute {


    private static final long serialVersionUID = 1739595973810840475L;

    /**
     * Construct a new job media sheets completed attribute with the given
     * integer value.
     *
     * <p>
     *  使用给定的整数值构造新作业介质工作表完成属性。
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if <CODE>value</CODE> is less than 0.
     */
    public JobMediaSheetsCompleted(int value) {
        super (value, 0, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this job media sheets completed attribute is equivalent
     * to the passed in object. To be equivalent, all of the following
     * conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobMediaSheetsCompleted.
     * <LI>
     * This job media sheets completed attribute's value and
     * <CODE>object</CODE>'s value are equal.
     * </OL>
     *
     * <p>
     *  返回此作业媒体工作表完成属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     * <CODE>对象</CODE>是JobMediaSheetsCompleted类的实例。
     * <LI>
     *  此作业介质页完成属性值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job media
     *          sheets completed attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals (object) &&
                object instanceof JobMediaSheetsCompleted);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobMediaSheetsCompleted, the category is class
     * JobMediaSheetsCompleted itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobMediaSheetsCompleted.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobMediaSheetsCompleted, the category name is
     * <CODE>"job-media-sheets-completed"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobMediaSheetsCompleted类,类别是JobMediaSheetsCompleted类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-media-sheets-completed";
    }
}
