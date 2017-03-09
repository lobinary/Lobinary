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

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.Attribute;

/**
 * Class JobStateReason is a printing attribute class, an enumeration, that
 * provides additional information about the job's current state, i.e.,
 * information that augments the value of the job's {@link JobState JobState}
 * attribute. Class JobStateReason defines standard job state reason values. A
 * Print Service implementation only needs to report those job state
 * reasons which are appropriate for the particular implementation; it does not
 * have to report every defined job state reason.
 * <P>
 * Instances of JobStateReason do not appear in a Print Job's attribute set
 * directly. Rather, a {@link JobStateReasons JobStateReasons} attribute appears
 * in the Print Job's attribute set. The {@link JobStateReasons JobStateReasons}
 * attribute contains zero, one, or more than one JobStateReason objects which
 * pertain to the Print Job's status. The printer adds a JobStateReason object
 * to the Print Job's {@link JobStateReasons JobStateReasons} attribute when the
 * corresponding condition becomes true of the Print Job, and the printer
 * removes the JobStateReason object again when the corresponding condition
 * becomes false, regardless of whether the Print Job's overall {@link JobState
 * JobState} also changed.
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  JobStateReason类是一个打印属性类,枚举,提供有关作业当前状态的附加信息,即增加作业{@link JobState JobState}属性值的信息。
 *  JobStateReason类定义标准作业状态原因值。打印服务实现仅需要报告适合于特定实现的那些作业状态原因;它不必报告每个定义的工作状态原因。
 * <P>
 *  JobStateReason的实例不会直接显示在打印作业的属性集中。相反,{@link JobStateReasons JobStateReasons}属性显示在打印作业的属性集中。
 *  {@link JobStateReasons JobStateReasons}属性包含零个,一个或多个与打印作业状态相关的JobStateReason对象。
 * 当相应条件成为打印作业的相应条件时,打印机向打印作业的{@link JobStateReasons JobStateReasons}属性添加一个JobStateReason对象,并且当相应条件为假时,打
 * 印机再次删除JobStateReason对象,而不管打印作业的整体{@link JobState JobState}也已更改。
 *  {@link JobStateReasons JobStateReasons}属性包含零个,一个或多个与打印作业状态相关的JobStateReason对象。
 * <P>
 * <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
 *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class JobStateReason extends EnumSyntax implements Attribute {

    private static final long serialVersionUID = -8765894420449009168L;

    /**
     * The printer has created the Print Job, but the printer has not finished
     * accessing or accepting all the print data yet.
     * <p>
     *  打印机已创建打印作业,但打印机尚未完成访问或接受所有打印数据。
     * 
     */
    public static final JobStateReason
        JOB_INCOMING = new JobStateReason(0);

    /**
     * The printer has created the Print Job, but the printer is expecting
     * additional print data before it can move the job into the PROCESSING
     * state. If a printer starts processing before it has received all data,
     * the printer removes the JOB_DATA_INSUFFICIENT reason, but the
     * JOB_INCOMING reason remains. If a printer starts processing after it
     * has received all data, the printer removes the JOB_DATA_INSUFFICIENT
     * and JOB_INCOMING reasons at the same time.
     * <p>
     *  打印机已创建打印作业,但打印机在将作业移动到"处理"状态之前,正在等待附加的打印数据。
     * 如果打印机在接收到所有数据之前开始处理,则打印机将删除JOB_DATA_INSUFFICIENT原因,但JOB_INCOMING原因仍然存在。
     * 如果打印机在接收到所有数据后开始处理,则打印机会同时移除JOB_DATA_INSUFFICIENT和JOB_INCOMING原因。
     * 
     */
    public static final JobStateReason
        JOB_DATA_INSUFFICIENT = new JobStateReason(1);

    /**
     * The Printer could not access one or more documents passed by reference
     * (i.e., the print data representation object is a URL). This reason is
     * intended to cover any file access problem,including file does not exist
     * and access denied because of an access control problem. Whether the
     * printer aborts the job and moves the job to the ABORTED job state or
     * prints all documents that are accessible and moves the job to the
     * COMPLETED job state and adds the COMPLETED_WITH_ERRORS reason to the
     * job's {@link JobStateReasons JobStateReasons} attribute depends on
     * implementation and/or site policy. This value should be supported if
     * the printer supports doc flavors with URL print data representation
     * objects.
     * <p>
     * 打印机不能访问通过引用传递的一个或多个文档(即,打印数据表示对象是URL)。此原因旨在涵盖任何文件访问问题,包括文件不存在和访问控制问题访问被拒绝。
     * 打印机是否中止作业并将作业移动到ABORTED作业状态或打印所有可访问的文档并将作业移动到COMPLETED作业状态,并将COMPLETED_WITH_ERRORS原因添加到作业的{@link JobStateReasons JobStateReasons}
     * 属性取决于实施和/或网站政策。
     * 打印机不能访问通过引用传递的一个或多个文档(即,打印数据表示对象是URL)。此原因旨在涵盖任何文件访问问题,包括文件不存在和访问控制问题访问被拒绝。
     * 如果打印机支持带有URL打印数据表示对象的doc风格,则应支持此值。
     * 
     */
    public static final JobStateReason
        DOCUMENT_ACCESS_ERROR = new JobStateReason(2);

    /**
     * The job was not completely submitted for some unforeseen reason.
     * Possibilities include (1) the printer has crashed before the job was
     * fully submitted by the client, (2) the printer or the document transfer
     * method has crashed in some non-recoverable way before the document data
     * was entirely transferred to the printer, (3) the client crashed before
     * the job was fully submitted.
     * <p>
     *  工作没有完全提交一些不可预见的原因。
     * 可能性包括(1)在作业被客户完全提交之前打印机已经崩溃,(2)在文档数据被完全传送到打印机之前,打印机或文档传送方法以一些不可恢复的方式崩溃, )客户端在作业完全提交之前崩溃。
     * 
     */
    public static final JobStateReason
        SUBMISSION_INTERRUPTED = new JobStateReason(3);

    /**
     * The printer is transmitting the job to the output device.
     * <p>
     *  打印机正在将作业发送到输出设备。
     * 
     */
    public static final JobStateReason
        JOB_OUTGOING = new JobStateReason(4);

    /**
     * The value of the job's {@link JobHoldUntil JobHoldUntil} attribute was
     * specified with a date-time that is still in the future. The job must
     * not be a candidate for processing until this reason is removed and
     * there are
     * no other reasons to hold the job. This value should be supported if the
     * {@link JobHoldUntil JobHoldUntil} job template attribute is supported.
     * <p>
     *  作业的{@link JobHoldUntil JobHoldUntil}属性的值使用仍在未来的日期时间指定。该作业必须不是处理的候选,直到该原因被移除并且没有其他理由来保存该作业。
     * 如果支持{@link JobHoldUntil JobHoldUntil}作业模板属性,则应支持此值。
     * 
     */
    public static final JobStateReason
        JOB_HOLD_UNTIL_SPECIFIED = new JobStateReason(5);

    /**
     * At least one of the resources needed by the job, such as media, fonts,
     * resource objects, etc., is not ready on any of the physical printers
     * for which the job is a candidate. This condition may be detected
     * when the job is accepted, or subsequently while the job is pending
     * or processing, depending on implementation.
     * The job may remain in its current state or
     * be moved to the PENDING_HELD state, depending on implementation and/or
     * job scheduling policy.
     * <p>
     * 作业所需的资源(例如媒体,字体,资源对象等)中的至少一个在该作业是候选者的任何物理打印机上都不准备就绪。根据实现,当接受作业时,或者随后在作业待决或处理时,可以检测到该条件。
     * 根据实现和/或作业调度策略,作业可以保持在其当前状态或被移动到PENDING_HELD状态。
     * 
     */
    public static final JobStateReason
        RESOURCES_ARE_NOT_READY = new JobStateReason(6);

    /**
     * The value of the printer's {@link PrinterStateReasons
     * PrinterStateReasons} attribute contains a {@link PrinterStateReason
     * PrinterStateReason} value of STOPPED_PARTLY.
     * <p>
     *  打印机的{@link PrinterStateReasons PrinterStateReasons}属性的值包含STOPPED_PARTLY的{@link PrinterStateReason PrinterStateReason}
     * 值。
     * 
     */
    public static final JobStateReason
        PRINTER_STOPPED_PARTLY = new JobStateReason(7);

    /**
     * The value of the printer's {@link PrinterState PrinterState} attribute
     * ia STOPPED.
     * <p>
     *  打印机的{@link PrinterState PrinterState}属性ia STOPPED的值。
     * 
     */
    public static final JobStateReason
        PRINTER_STOPPED = new JobStateReason(8);

    /**
     * The job is in the PROCESSING state, but more specifically, the printer
     * ia interpreting the document data.
     * <p>
     *  作业处于PROCESSING状态,但更具体地说,打印机ia解释文档数据。
     * 
     */
    public static final JobStateReason
        JOB_INTERPRETING = new JobStateReason(9);

    /**
     * The job is in the PROCESSING state, but more specifically, the printer
     * has queued the document data.
     * <p>
     *  作业处于PROCESSING状态,但更具体地说,打印机已将文档数据排入队列。
     * 
     */
    public static final JobStateReason JOB_QUEUED = new JobStateReason(10);

    /**
     * The job is in the PROCESSING state, but more specifically, the printer
     * is interpreting document data and producing another electronic
     * representation.
     * <p>
     *  作业处于处理状态,但更具体地,打印机正在解释文档数据并产生另一电子表示。
     * 
     */
    public static final JobStateReason
        JOB_TRANSFORMING = new JobStateReason(11);

    /**
     * The job is in the PENDING_HELD, PENDING, or PROCESSING state, but more
     * specifically, the printer has completed enough processing of the document
     * to be able to start marking and the job is waiting for the marker.
     * Systems that require human intervention to release jobs put the job into
     * the PENDING_HELD job state. Systems that automatically select a job to
     * use the marker put the job into the PENDING job state or keep the job
     * in the PROCESSING job state while waiting for the marker, depending on
     * implementation. All implementations put the job into (or back into) the
     * PROCESSING state when marking does begin.
     * <p>
     * 作业处于PENDING_HELD,PENDING或PROCESSING状态,但更具体地说,打印机已完成了足够的文档处理,以便能够开始标记,并且作业正在等待标记。
     * 需要人为干预来释放作业的系统将作业置于PENDING_HELD作业状态。
     * 自动选择作业以使用标记的系统将作业置于PENDING作业状态,或者在等待标记时将作业保留在PROCESSING作业状态,具体取决于实施。
     * 当标记开始时,所有实现将作业置于(或返回)PROCESSING状态。
     * 
     */
    public static final JobStateReason
        JOB_QUEUED_FOR_MARKER = new JobStateReason(12);

    /**
     * The output device is marking media. This value is useful for printers
     * which spend a great deal of time processing (1) when no marking is
     * happening and then want to show that marking is now happening or (2) when
     * the job is in the process of being canceled or aborted while the job
     * remains in the PROCESSING state, but the marking has not yet stopped so
     * that impression or sheet counts are still increasing for the job.
     * <p>
     *  输出设备是标记介质。
     * 此值对于花费大量时间处理的打印机是有用的(1),当没有标记发生时,然后想要显示标记正在发生或(2)当作业正在被取消或中止时作业仍处于"处理"状态,但标记尚未停止,因此作业的印数或页数仍在增加。
     * 
     */
    public static final JobStateReason
        JOB_PRINTING = new JobStateReason(13);

    /**
     * The job was canceled by the owner of the job, i.e., by a user whose
     * authenticated identity is the same as the value of the originating user
     * that created the Print Job, or by some other authorized end-user, such as
     * a member of the job owner's security group. This value should be
     * supported.
     * <p>
     *  作业被作业的所有者取消,即由其认证身份与创建打印作业的发起用户的值相同的用户,或者由某个其他授权的最终用户,例如作业所有者的安全组。应支持此值。
     * 
     */
    public static final JobStateReason
        JOB_CANCELED_BY_USER = new JobStateReason(14);

    /**
     * The job was canceled by the operator, i.e., by a user who has been
     * authenticated as having operator privileges (whether local or remote). If
     * the security policy is to allow anyone to cancel anyone's job, then this
     * value may be used when the job is canceled by someone other than the
     * owner of the job. For such a security policy, in effect, everyone is an
     * operator as far as canceling jobs is concerned. This value should be
     * supported if the implementation permits canceling by someone other than
     * the owner of the job.
     * <p>
     * 作业被操作者取消,即由已经被认证为具有操作者权限(无论是本地的还是远程的)的用户取消。如果安全策略允许任何人取消任何人的作业,则当作业由作业所有者以外的人取消时,可以使用此值。
     * 对于这样的安全策略,实际上,就取消工作而言,每个人都是运营商。如果实施允许由作业所有者以外的人取消,则应支持此值。
     * 
     */
    public static final JobStateReason
        JOB_CANCELED_BY_OPERATOR = new JobStateReason(15);

    /**
     * The job was canceled by an unidentified local user, i.e., a user at a
     * console at the device. This value should be supported if the
     * implementation supports canceling jobs at the console.
     * <p>
     *  作业由未识别的本地用户(即,设备处的控制台处的用户)取消。如果实施支持在控制台取消作业,则应支持此值。
     * 
     */
    public static final JobStateReason
        JOB_CANCELED_AT_DEVICE = new JobStateReason(16);

    /**
     * The job was aborted by the system. Either the job (1) is in the process
     * of being aborted, (2) has been aborted by the system and placed in the
     * ABORTED state, or (3) has been aborted by the system and placed in the
     * PENDING_HELD state, so that a user or operator can manually try the job
     * again. This value should be supported.
     * <p>
     *  作业被系统中止。作业(1)正在被中止,(2)已被系统中止并且被置于ABORTED状态,或(3)已被系统中止并且被置于PENDING_HELD状态,使​​得用户或操作员可以手动再次尝试作业。
     * 应支持此值。
     * 
     */
    public static final JobStateReason
        ABORTED_BY_SYSTEM = new JobStateReason(17);

    /**
     * The job was aborted by the system because the printer determined while
     * attempting to decompress the document's data that the compression is
     * actually not among those supported by the printer. This value must be
     * supported, since {@link Compression Compression} is a required doc
     * description attribute.
     * <p>
     *  作业被系统中止,因为打印机在尝试解压缩文档的数据时确定压缩实际上不在打印机支持的压缩之间。必须支持此值,因为{@link Compression Compression}是必需的doc描述属性。
     * 
     */
    public static final JobStateReason
        UNSUPPORTED_COMPRESSION = new JobStateReason(18);

    /**
     * The job was aborted by the system because the printer encountered an
     * error in the document data while decompressing it. If the printer posts
     * this reason, the document data has already passed any tests that would
     * have led to the UNSUPPORTED_COMPRESSION job state reason.
     * <p>
     * 作业被系统中止,因为打印机在解压缩文档数据时遇到错误。如果打印机发布了此原因,则文档数据已通过任何可能导致UNSUPPORTED_COMPRESSION作业状态原因的测试。
     * 
     */
    public static final JobStateReason
        COMPRESSION_ERROR = new JobStateReason(19);

    /**
     * The job was aborted by the system because the document data's document
     * format (doc flavor) is not among those supported by the printer. If the
     * client specifies a doc flavor with a MIME type of
     * <CODE>"application/octet-stream"</CODE>, the printer may abort the job if
     * the printer cannot determine the document data's actual format through
     * auto-sensing (even if the printer supports the document format if
     * specified explicitly). This value must be supported, since a doc flavor
     * is required to be specified for each doc.
     * <p>
     *  作业被系统中止,因为文档数据的文档格式(doc flavor)不在打印机支持的格式之间。
     * 如果客户端指定MIME类型为<CODE>"application / octet-stream"</CODE>的文档风格,打印机可能会中止作业,如果打印机无法通过自动感测确定文档数据的实际格式如果明确指
     * 定,打印机支持文档格式)。
     *  作业被系统中止,因为文档数据的文档格式(doc flavor)不在打印机支持的格式之间。必须支持此值,因为需要为每个文档指定doc flavor。
     * 
     */
    public static final JobStateReason
        UNSUPPORTED_DOCUMENT_FORMAT = new JobStateReason(20);

    /**
     * The job was aborted by the system because the printer encountered an
     * error in the document data while processing it. If the printer posts this
     * reason, the document data has already passed any tests that would have
     * led to the UNSUPPORTED_DOCUMENT_FORMAT job state reason.
     * <p>
     *  作业被系统中止,因为打印机在处理文档数据时遇到错误。如果打印机发布了此原因,则文档数据已通过任何可能导致UNSUPPORTED_DOCUMENT_FORMAT作业状态原因的测试。
     * 
     */
    public static final JobStateReason
        DOCUMENT_FORMAT_ERROR = new JobStateReason(21);

    /**
     * The requester has canceled the job or the printer has aborted the job,
     * but the printer is still performing some actions on the job until a
     * specified stop point occurs or job termination/cleanup is completed.
     * <P>
     * If the implementation requires some measurable time to cancel the job in
     * the PROCESSING or PROCESSING_STOPPED job states, the printer must use
     * this reason to indicate that the printer is still performing some actions
     * on the job while the job remains in the PROCESSING or PROCESSING_STOPPED
     * state. After all the job's job description attributes have stopped
     * incrementing, the printer moves the job from the PROCESSING state to the
     * CANCELED or ABORTED job states.
     * <p>
     *  请求者已取消作业或打印机已中止作业,但打印机仍在对作业执行某些操作,直到发生指定的停止点或作业终止/清除完成。
     * <P>
     * 如果实现需要一些可测量的时间来取消PROCESSING或PROCESSING_STOPPED作业状态中的作业,则打印机必须使用此原因来指示打印机仍在对作业执行某些操作,而作业仍处于PROCESSING或
     * PROCESSING_STOPPED状态。
     * 在所有作业的作业描述属性停止递增之后,打印机将作业从PROCESSING状态移动到CANCELED或ABORTED作业状态。
     * 
     */
    public static final JobStateReason
        PROCESSING_TO_STOP_POINT = new JobStateReason(22);

    /**
     * The printer is off-line and accepting no jobs. All PENDING jobs are put
     * into the PENDING_HELD state. This situation could be true if the
     * service's or document transform's input is impaired or broken.
     * <p>
     *  打印机处于脱机状态,不接受任何作业。所有PENDING作业都将处于PENDING_HELD状态。如果服务或文档变换的输入受损或损坏,则这种情况可能成立。
     * 
     */
    public static final JobStateReason
        SERVICE_OFF_LINE = new JobStateReason(23);

    /**
     * The job completed successfully. This value should be supported.
     * <p>
     *  作业成功完成。应支持此值。
     * 
     */
    public static final JobStateReason
        JOB_COMPLETED_SUCCESSFULLY = new JobStateReason(24);

    /**
     * The job completed with warnings. This value should be supported if the
     * implementation detects warnings.
     * <p>
     *  作业完成时带有警告。如果实施检测到警告,则应支持此值。
     * 
     */
    public static final JobStateReason
        JOB_COMPLETED_WITH_WARNINGS = new JobStateReason(25);

    /**
     * The job completed with errors (and possibly warnings too). This value
     * should be supported if the implementation detects errors.
     * <p>
     *  作业完成时出错(也可能是警告)。如果实现检测到错误,则应支持此值。
     * 
     */
    public static final JobStateReason
        JOB_COMPLETED_WITH_ERRORS = new JobStateReason(26);

    /**
     * This job is retained and is currently able to be restarted. If
     * JOB_RESTARTABLE is contained in the job's {@link JobStateReasons
     * JobStateReasons} attribute, then the printer must accept a request to
     * restart that job. This value should be supported if restarting jobs is
     * supported. <I>[The capability for restarting jobs is not in the Java
     * Print Service API at present.]</I>
     * <p>
     *  此作业将保留,并且当前可以重新启动。
     * 如果JOB_RESTARTABLE包含在作业的{@link JobStateReasons JobStateReasons}属性中,则打印机必须接受重新启动该作业的请求。
     * 如果支持重新启动作业,则应支持此值。 <I> [目前,重新启动作业的功能不在Java打印服务API中。] </I>。
     * 
     */
    public static final JobStateReason
        JOB_RESTARTABLE = new JobStateReason(27);

    /**
     * The job has been forwarded to a device or print system that is unable to
     * send back status. The printer sets the job's {@link JobState JobState}
     * attribute to COMPLETED and adds the QUEUED_IN_DEVICE reason to the job's
     * {@link JobStateReasons JobStateReasons} attribute to indicate that the
     * printer has no additional information about the job and never will have
     * any better information.
     * <p>
     * 作业已转发到无法发送回状态的设备或打印系统。
     * 打印机将作业的{@link JobState JobState}属性设置为COMPLETED,并将QUEUED_IN_DEVICE原因添加到作业的{@link JobStateReasons JobStateReasons}
     * 属性中,以指示打印机没有关于作业的其他信息,并且永远不会有任何更好的信息。
     * 作业已转发到无法发送回状态的设备或打印系统。
     * 
     */
    public static final JobStateReason
        QUEUED_IN_DEVICE = new JobStateReason(28);

    /**
     * Construct a new job state reason enumeration value with the given
     * integer  value.
     *
     * <p>
     *  使用给定的整数值构造新的作业状态原因枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected JobStateReason(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "job-incoming",
        "job-data-insufficient",
        "document-access-error",
        "submission-interrupted",
        "job-outgoing",
        "job-hold-until-specified",
        "resources-are-not-ready",
        "printer-stopped-partly",
        "printer-stopped",
        "job-interpreting",
        "job-queued",
        "job-transforming",
        "job-queued-for-marker",
        "job-printing",
        "job-canceled-by-user",
        "job-canceled-by-operator",
        "job-canceled-at-device",
        "aborted-by-system",
        "unsupported-compression",
        "compression-error",
        "unsupported-document-format",
        "document-format-error",
        "processing-to-stop-point",
        "service-off-line",
        "job-completed-successfully",
        "job-completed-with-warnings",
        "job-completed-with-errors",
        "job-restartable",
        "queued-in-device"};

    private static final JobStateReason[] myEnumValueTable = {
        JOB_INCOMING,
        JOB_DATA_INSUFFICIENT,
        DOCUMENT_ACCESS_ERROR,
        SUBMISSION_INTERRUPTED,
        JOB_OUTGOING,
        JOB_HOLD_UNTIL_SPECIFIED,
        RESOURCES_ARE_NOT_READY,
        PRINTER_STOPPED_PARTLY,
        PRINTER_STOPPED,
        JOB_INTERPRETING,
        JOB_QUEUED,
        JOB_TRANSFORMING,
        JOB_QUEUED_FOR_MARKER,
        JOB_PRINTING,
        JOB_CANCELED_BY_USER,
        JOB_CANCELED_BY_OPERATOR,
        JOB_CANCELED_AT_DEVICE,
        ABORTED_BY_SYSTEM,
        UNSUPPORTED_COMPRESSION,
        COMPRESSION_ERROR,
        UNSUPPORTED_DOCUMENT_FORMAT,
        DOCUMENT_FORMAT_ERROR,
        PROCESSING_TO_STOP_POINT,
        SERVICE_OFF_LINE,
        JOB_COMPLETED_SUCCESSFULLY,
        JOB_COMPLETED_WITH_WARNINGS,
        JOB_COMPLETED_WITH_ERRORS,
        JOB_RESTARTABLE,
        QUEUED_IN_DEVICE};

    /**
     * Returns the string table for class JobStateReason.
     * <p>
     *  返回JobStateReason类的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class JobStateReason.
     * <p>
     *  返回类JobStateReason的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }


    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class JobStateReason and any vendor-defined subclasses, the
     * category  is class JobStateReason itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于JobStateReason类和任何供应商定义的子类,类别是JobStateReason类本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return JobStateReason.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class JobStateReason and any vendor-defined subclasses, the
     * category name is <CODE>"job-state-reason"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "job-state-reason";
    }

}
