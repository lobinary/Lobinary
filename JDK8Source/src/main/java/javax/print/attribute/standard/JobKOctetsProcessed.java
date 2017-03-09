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
 * Class JobKOctetsProcessed is an integer valued printing attribute class that
 * specifies the total number of print data octets processed so far in K octets,
 * i.e., in units of 1024 octets. The value must be rounded up, so that a job
 * between 1 and 1024 octets inclusive must be indicated as being 1K octets,
 * 1025 to 2048 inclusive must be 2K, etc. For a multidoc print job (a job with
 * multiple documents), the JobKOctetsProcessed value is computed by adding up
 * the individual documents' number of octets processed so far, then rounding up
 * to the next K octets value.
 * <P>
 * The JobKOctetsProcessed attribute describes the progress of the job. This
 * attribute is intended to be a counter. That is, the JobKOctetsProcessed value
 * for a job that has not started processing must be 0. When the job's {@link
 * JobState JobState} is PROCESSING or PROCESSING_STOPPED, the
 * JobKOctetsProcessed value is intended to increase as the job is processed; it
 * indicates the amount of the job that has been processed at the time the Print
 * Job's attribute set is queried or at the time a print job event is reported.
 * When the job enters the COMPLETED, CANCELED, or ABORTED states, the
 * JobKOctetsProcessed value is the final value for the job.
 * <P>
 * For implementations where multiple copies are produced by the interpreter
 * with only a single pass over the data, the final value of the
 * JobKOctetsProcessed attribute must be equal to the value of the {@link
 * JobKOctets JobKOctets} attribute. For implementations where multiple copies
 * are produced by the interpreter by processing the data for each copy, the
 * final value must be a multiple of the value of the {@link JobKOctets
 * JobKOctets} attribute.
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  类JobKOctetsProcessed是整数值打印属性类,其指定迄今为止在K个八位字节中处理的打印数据八位字节的总数,即以1024个八位字节为单位。
 * 该值必须向上取整,以便1至1024个字节(含)之间的作业必须指示为1K字节,1025至2048(含)必须为2K等。
 * 对于多标题打印作业(具有多个文档的作业),JobKOctetsProcessed值通过将各个文档的迄今为止处理的八位字节的数量相加,然后向上舍入到下一个K字节值来计算。
 * <P>
 *  JobKOctetsProcessed属性描述作业的进度。此属性旨在作为计数器。
 * 也就是说,尚未开始处理的作业的JobKOctetsProcessed值必须为0.当作业的{@link JobState JobState}为PROCESSING或PROCESSING_STOPPED时,
 * JobKOctetsProcessed值将在处理作业时增加;它指示在查询打印作业属性集时或在报告打印作业事件时已处理的作业量。
 *  JobKOctetsProcessed属性描述作业的进度。此属性旨在作为计数器。当作业进入完成,取消或停止状态时,JobKOctetsProcessed值是作业的最终值。
 * <P>
 * 对于由解释器仅通过数据的单次传递产生多个副本的实现,JobKOctetsProcessed属性的最终值必须等于{@link JobKOctets JobKOctets}属性的值。
 * 对于解释器通过处理每个副本的数据生成多个副本的实现,最终值必须是{@link JobKOctets JobKOctets}属性值的倍数。
 * <P>
 *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @see JobKOctets
 * @see JobKOctetsSupported
 * @see JobImpressionsCompleted
 * @see JobMediaSheetsCompleted
 *
 * @author  Alan Kaminsky
 */
public final class JobKOctetsProcessed extends IntegerSyntax
        implements PrintJobAttribute {

    private static final long serialVersionUID = -6265238509657881806L;

    /**
     * Construct a new job K octets processed attribute with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的作业K个八位字节处理的属性。
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *  (Unchecked exception) Thrown if <CODE>value</CODE> is less than 0.
     */
    public JobKOctetsProcessed(int value) {
        super (value, 0, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this job K octets processed attribute is equivalent to
     * the passed in object. To be equivalent, all of the following conditions
     * must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class JobKOctetsProcessed.
     * <LI>
     * This job K octets processed attribute's value and
     * <CODE>object</CODE>'s value are equal.
     * </OL>
     *
     * <p>
     *  返回此作业K个字节处理的属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是JobKOctetsProcessed类的实例。
     * <LI>
     *  此作业K个字节处理的属性值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this job K
     *          octets processed attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return(super.equals (object) &&
               object instanceof JobKOctetsProcessed);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobKOctetsProcessed, the category is class
     * JobKOctetsProcessed itself.
     *
     * <p>
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobKOctetsProcessed.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobKOctetsProcessed, the category name is
     * <CODE>"job-k-octets-processed"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobKOctetsProcessed类,类别是JobKOctetsProcessed类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-k-octets-processed";
    }

}
