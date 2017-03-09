/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.print.event;

import javax.print.DocPrintJob;

/**
 *
 * Class <code>PrintJobEvent</code> encapsulates common events a print job
 * reports to let a listener know of progress in the processing of the
 * {@link DocPrintJob}.
 *
 * <p>
 *  类<code> PrintJobEvent </code>封装了打印作业报告的常见事件,以便让侦听器知道{@link DocPrintJob}的处理进度。
 * 
 */

public class PrintJobEvent extends PrintEvent {

   private static final long serialVersionUID = -1711656903622072997L;

   private int reason;

   /**
    * The job was canceled by the {@link javax.print.PrintService PrintService}.
    * <p>
    *  该作业已由{@link javax.print.PrintService PrintService}取消。
    * 
    */
   public static final int JOB_CANCELED   = 101;

   /**
    * The document cis completely printed.
    * <p>
    *  文件顺式完全打印。
    * 
    */
   public static final int JOB_COMPLETE       = 102;

   /**
    * The print service reports that the job cannot be completed.
    * The application must resubmit the job.
    * <p>
    *  打印服务报告无法完成作业。应用程序必须重新提交作业。
    * 
    */

   public static final int JOB_FAILED         = 103;

   /**
    * The print service indicates that a - possibly transient - problem
    * may require external intervention before the print service can
    * continue.  One example of an event that can
    * generate this message is when the printer runs out of paper.
    * <p>
    *  打印服务指示在打印服务可以继续之前,可能的瞬态问题可能需要外部干预。可以生成此消息的事件的一个示例是当打印机缺纸时。
    * 
    */
   public static final int REQUIRES_ATTENTION = 104;

   /**
    * Not all print services may be capable of delivering interesting
    * events, or even telling when a job is complete. This message indicates
    * the print job has no further information or communication
    * with the print service. This message should always be delivered
    * if a terminal event (completed/failed/canceled) is not delivered.
    * For example, if messages such as JOB_COMPLETE have NOT been received
    * before receiving this message, the only inference that should be drawn
    * is that the print service does not support delivering such an event.
    * <p>
    *  并非所有的打印服务都能够提供有趣的事件,或者甚至告诉工作完成的时间。此消息表示打印作业没有进一步的信息或与打印服务的通信。如果未传递终端事件(完成/失败/取消),则应始终传递此消息。
    * 例如,如果在接收此消息之前未接收到诸如JOB_COMPLETE的消息,则应当绘制的唯一推断是打印服务不支持递送这样的事件。
    * 
    */
   public static final int NO_MORE_EVENTS    = 105;

   /**
    * The job is not necessarily printed yet, but the data has been transferred
    * successfully from the client to the print service. The client may
    * free data resources.
    * <p>
    *  作业不一定要打印,但是数据已经从客户端成功传输到打印服务。客户端可以释放数据资源。
    * 
    */
   public static final int DATA_TRANSFER_COMPLETE    = 106;

   /**
     * Constructs a <code>PrintJobEvent</code> object.
     *
     * <p>
     *  构造一个<code> PrintJobEvent </code>对象。
     * 
     * 
     * @param source  a <code>DocPrintJob</code> object
     * @param reason  an int specifying the reason.
     * @throws IllegalArgumentException if <code>source</code> is
     *         <code>null</code>.
     */

    public PrintJobEvent( DocPrintJob source, int reason) {

        super(source);
        this.reason = reason;
   }

    /**
     * Gets the reason for this event.
     * <p>
     * 获取此事件的原因。
     * 
     * 
     * @return  reason int.
     */
    public int getPrintEventType() {
        return reason;
    }

    /**
     * Determines the <code>DocPrintJob</code> to which this print job
     * event pertains.
     *
     * <p>
     *  确定此打印作业事件所属的<code> DocPrintJob </code>。
     * 
     * @return  the <code>DocPrintJob</code> object that represents the
     *          print job that reports the events encapsulated by this
     *          <code>PrintJobEvent</code>.
     *
     */
    public DocPrintJob getPrintJob() {
        return (DocPrintJob) getSource();
    }


}
