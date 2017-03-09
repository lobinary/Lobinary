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
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.PrintJobAttribute;

/**
 * JobState is a printing attribute class, an enumeration, that identifies
 * the current state of a print job. Class JobState defines standard job state
 * values. A  Print Service implementation only needs to report those job
 * states which are appropriate for the particular implementation; it does not
 * have to report every defined job state. The {@link JobStateReasons
 * JobStateReasons} attribute augments the JobState attribute to give more
 * detailed information about the job in the given job state.
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  JobState是一个打印属性类,枚举,用于标识打印作业的当前状态。 JobState类定义标准作业状态值。打印服务实现仅需要报告适合于特定实现的那些作业状态;它不必报告每个定义的作业状态。
 *  {@link JobStateReasons JobStateReasons}属性可扩充JobState属性,以提供有关给定作业状态中作业的更详细信息。
 * <P>
 *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */

public class JobState extends EnumSyntax implements PrintJobAttribute {

    private static final long serialVersionUID = 400465010094018920L;

    /**
     * The job state is unknown.
     * <p>
     *  作业状态未知。
     * 
     */
    public static final JobState UNKNOWN = new JobState(0);

    /**
     * The job is a candidate to start processing, but is not yet processing.
     * <p>
     *  作业是开始处理的候选对象,但尚未处理。
     * 
     */
    public static final JobState PENDING = new JobState(3);

    /**
     * The job is not a candidate for processing for any number of reasons but
     * will return to the PENDING state as soon as the reasons are no longer
     * present. The job's {@link JobStateReasons JobStateReasons} attribute must
     * indicate why the job is no longer a candidate for processing.
     * <p>
     *  该作业不是处理任何原因的候选人,但一旦原因不再存在,将返回到PENDING状态。
     * 作业的{@link JobStateReasons JobStateReasons}属性必须指示作业为什么不再是处理的候选对象。
     * 
     */
    public static final JobState PENDING_HELD = new JobState(4);

    /**
     * The job is processing. One or more of the following activities is
     * occurring:
     * <OL TYPE=1>
     * <LI>
     * The job is using, or is attempting to use, one or more purely software
     * processes that are analyzing, creating, or interpreting a PDL, etc.
     * <P>
     * <LI>
     * The job is using, or is attempting to use, one or more hardware
     * devices that are interpreting a PDL, making marks on a medium, and/or
     * performing finishing, such as stapling, etc.
     * <P>
     * <LI>
     * The printer has made the job ready for printing, but the output
     * device is not yet printing it, either because the job hasn't reached the
     * output device or because the job is queued in the output device or some
     * other spooler, awaiting the output device to print it.
     * </OL>
     * <P>
     * When the job is in the PROCESSING state, the entire job state includes
     * the detailed status represented in the printer's {@link PrinterState
     * PrinterState} and {@link PrinterStateReasons PrinterStateReasons}
     * attributes.
     * <P>
     * Implementations may, though they need not, include additional values in
     * the job's {@link JobStateReasons JobStateReasons} attribute to indicate
     * the progress of the job, such as adding the JOB_PRINTING value to
     * indicate when the output device is actually making marks on paper and/or
     * the PROCESSING_TO_STOP_POINT value to indicate that the printer is in the
     * process of canceling or aborting the job.
     * <p>
     *  作业正在处理。正在进行以下一个或多个活动：
     * <OL TYPE=1>
     * <LI>
     *  作业正在使用或正在尝试使用正在分析,创建或解释PDL等的一个或多个纯粹的软件进程。
     * <P>
     * <LI>
     * 作业正在使用或试图使用正在解释PDL,在介质上做出标记和/或执行整理(诸如装订等)的一个或多个硬件设备。
     * <P>
     * <LI>
     *  打印机已准备好打印作业,但输出设备尚未打印,因为作业尚未到达输出设备,或者因为作业在输出设备或其他假脱机程序中排队等待输出设备打印。
     * </OL>
     * <P>
     *  当作业处于PROCESSING状态时,整个作业状态包括打印机的{@link PrinterState PrinterState}和{@link PrinterStateReasons PrinterStateReasons}
     * 属性中所表示的详细状态。
     * <P>
     *  尽管它们不需要,但是实现可以在作业的{@link JobStateReasons JobStateReasons}属性中包括附加值,以指示作业的进度,例如添加JOB_PRINTING值以指示输出设备何
     * 时实际在纸上形成标记和/或PROCESSING_TO_STOP_POINT值,以指示打印机正在取消或中止作业。
     * 
     */
    public static final JobState PROCESSING = new JobState (5);

    /**
     * The job has stopped while processing for any number of reasons and will
     * return to the PROCESSING state as soon as the reasons are no longer
     * present.
     * <P>
     * The job's {@link JobStateReasons JobStateReasons} attribute may indicate
     * why the job has stopped processing. For example, if the output device is
     * stopped, the PRINTER_STOPPED value may be included in the job's {@link
     * JobStateReasons JobStateReasons} attribute.
     * <P>
     * <I>Note:</I> When an output device is stopped, the device usually
     * indicates its condition in human readable form locally at the device. A
     * client can obtain more complete device status remotely by querying the
     * printer's {@link PrinterState PrinterState} and {@link
     * PrinterStateReasons PrinterStateReasons} attributes.
     * <p>
     *  作业在处理任何数量的原因时已停止,并会在原因不再存在时立即返回到PROCESSING状态。
     * <P>
     *  作业的{@link JobStateReasons JobStateReasons}属性可能指示作业为什么停止处理。
     * 例如,如果输出设备已停止,则PRINTER_STOPPED值可能包含在作业的{@link JobStateReasons JobStateReasons}属性中。
     * <P>
     * <I>注意：</I>当输出设备停止时,设备通常在设备本地以人工可读的形式指示其状态。
     * 客户端可以通过查询打印机的{@link PrinterState PrinterState}和{@link PrinterStateReasons PrinterStateReasons}属性来远程获取
     * 更完整的设备状态。
     * <I>注意：</I>当输出设备停止时,设备通常在设备本地以人工可读的形式指示其状态。
     * 
     */
    public static final JobState PROCESSING_STOPPED = new JobState (6);

    /**
     * The job has been canceled by some human agency, the printer has completed
     * canceling the job, and all job status attributes have reached their final
     * values for the job. While the printer is canceling the job, the job
     * remains in its current state, but the job's {@link JobStateReasons
     * JobStateReasons} attribute should contain the PROCESSING_TO_STOP_POINT
     * value and one of the CANCELED_BY_USER, CANCELED_BY_OPERATOR, or
     * CANCELED_AT_DEVICE values. When the job moves to the CANCELED state, the
     * PROCESSING_TO_STOP_POINT value, if present, must be removed, but the
     * CANCELED_BY_<I>xxx</I> value, if present, must remain.
     * <p>
     *  作业已被某些人工代理取消,打印机已完成取消作业,并且所有作业状态属性已达到作业的最终值。
     * 当打印机取消作业时,作业将保持当前状态,但作业的{@link JobStateReasons JobStateReasons}属性应包含PROCESSING_TO_STOP_POINT值以及CANCEL
     * ED_BY_USER,CANCELED_BY_OPERATOR或CANCELED_AT_DEVICE值之一。
     *  作业已被某些人工代理取消,打印机已完成取消作业,并且所有作业状态属性已达到作业的最终值。
     * 当作业移动到CANCELED状态时,必须删除PROCESSING_TO_STOP_POINT值(如果存在),但必须保留CANCELED_BY_ <I> xxx </I>值(如果存在)。
     * 
     */
    public static final JobState CANCELED = new JobState (7);

    /**
     * The job has been aborted by the system (usually while the job was in the
     * PROCESSING or PROCESSING_STOPPED state), the printer has completed
     * aborting the job, and all job status attributes have reached their final
     * values for the job. While the printer is aborting the job, the job
     * remains in its current state, but the job's {@link JobStateReasons
     * JobStateReasons} attribute should contain the PROCESSING_TO_STOP_POINT
     * and ABORTED_BY_SYSTEM values. When the job moves to the ABORTED state,
     * the PROCESSING_TO_STOP_POINT value, if present, must be removed, but the
     * ABORTED_BY_SYSTEM value, if present, must remain.
     * <p>
     * 作业已被系统中止(通常在作业处于PROCESSING或PROCESSING_STOPPED状态时),打印机已完成中止作业,并且所有作业状态属性已达到作业的最终值。
     * 当打印机中止作业时,作业将保持其当前状态,但作业的{@link JobStateReasons JobStateReasons}属性应包含PROCESSING_TO_STOP_POINT和ABORTED
     * _BY_SYSTEM值。
     * 作业已被系统中止(通常在作业处于PROCESSING或PROCESSING_STOPPED状态时),打印机已完成中止作业,并且所有作业状态属性已达到作业的最终值。
     * 当作业移动到ABORTED状态时,必须删除PROCESSING_TO_STOP_POINT值(如果存在),但必须保留ABORTED_BY_SYSTEM值(如果存在)。
     * 
     */
    public static final JobState ABORTED = new JobState (8);

    /**
     * The job has completed successfully or with warnings or errors after
     * processing, all of the job media sheets have been successfully stacked in
     * the appropriate output bin(s), and all job status attributes have reached
     * their final values for the job. The job's {@link JobStateReasons
     * JobStateReasons} attribute should contain one of these values:
     * COMPLETED_SUCCESSFULLY, COMPLETED_WITH_WARNINGS, or
     * COMPLETED_WITH_ERRORS.
     * <p>
     *  作业已成功完成或在处理后出现警告或错误,所有作业介质页已成功堆叠在适当的出纸槽中,并且所有作业状态属性已达到作业的最终值。
     * 作业的{@link JobStateReasons JobStateReasons}属性应包含以下值之一：COMPLETED_SUCCESSFULLY,COMPLETED_WITH_WARNINGS或C
     * OMPLETED_WITH_ERRORS。
     *  作业已成功完成或在处理后出现警告或错误,所有作业介质页已成功堆叠在适当的出纸槽中,并且所有作业状态属性已达到作业的最终值。
     * 
     */
    public static final JobState COMPLETED = new JobState (9);

    // Hidden constructors.

    /**
     * Construct a new job state enumeration value with the given integer value.
     *
     * <p>
     *  使用给定的整数值构造新的作业状态枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected JobState(int value) {
        super (value);
    }

    private static final String[] myStringTable =
    {"unknown",
     null,
     null,
     "pending",
     "pending-held",
     "processing",
     "processing-stopped",
     "canceled",
     "aborted",
     "completed"};

    private static final JobState[] myEnumValueTable =
    {UNKNOWN,
     null,
     null,
     PENDING,
     PENDING_HELD,
     PROCESSING,
     PROCESSING_STOPPED,
     CANCELED,
     ABORTED,
     COMPLETED};

    /**
     * Returns the string table for class JobState.
     * <p>
     *  返回JobState类的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class JobState.
     * <p>
     *  返回JobState类的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobState and any vendor-defined subclasses, the category is
     * class JobState itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobState类和任何供应商定义的子类,类别是JobState类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobState.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobState and any vendor-defined subclasses, the category
     * name is <CODE>"job-state"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-state";
    }

}
