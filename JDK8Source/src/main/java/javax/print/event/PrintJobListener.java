/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

/**
  * Implementations of this listener interface should be attached to a
  * {@link javax.print.DocPrintJob DocPrintJob} to monitor the status of
  * the printer job.
  * These callback methods may be invoked on the thread processing the
  * print job, or a service created notification thread. In either case
  * the client should not perform lengthy processing in these callbacks.
  * <p>
  *  此监听器接口的实现应附加到{@link javax.print.DocPrintJob DocPrintJob},以监视打印机作业的状态。
  * 这些回调方法可以在处理打印作业的线程或服务创建的通知线程上被调用。在任一情况下,客户端不应在这些回调中执行冗长的处理。
  * 
  */

public interface PrintJobListener {

    /**
     * Called to notify the client that data has been successfully
     * transferred to the print service, and the client may free
     * local resources allocated for that data.  The client should
     * not assume that the data has been completely printed after
     * receiving this event.
     * If this event is not received the client should wait for a terminal
     * event (completed/canceled/failed) before freeing the resources.
     * <p>
     *  调用以通知客户端数据已成功传输到打印服务,并且客户端可释放为该数据分配的本地资源。客户端在接收到此事件后不应假定数据已完全打印。
     * 如果未收到此事件,客户端应在释放资源之前等待终端事件(完成/取消/失败)。
     * 
     * 
     * @param pje the job generating this event
     */
    public void printDataTransferCompleted(PrintJobEvent pje) ;


    /**
     * Called to notify the client that the job completed successfully.
     * <p>
     *  调用以通知客户端作业成功完成。
     * 
     * 
     * @param pje the job generating this event
     */
    public void printJobCompleted(PrintJobEvent pje) ;


    /**
     * Called to notify the client that the job failed to complete
     * successfully and will have to be resubmitted.
     * <p>
     *  调用通知客户端作业无法成功完成,必须重新提交。
     * 
     * 
     * @param pje the job generating this event
     */
    public void printJobFailed(PrintJobEvent pje) ;


    /**
     * Called to notify the client that the job was canceled
     * by a user or a program.
     * <p>
     *  调用以通知客户端作业已被用户或程序取消。
     * 
     * 
     * @param pje the job generating this event
     */
    public void printJobCanceled(PrintJobEvent pje) ;


    /**
     * Called to notify the client that no more events will be delivered.
     * One cause of this event being generated is if the job
     * has successfully completed, but the printing system
     * is limited in capability and cannot verify this.
     * This event is required to be delivered if none of the other
     * terminal events (completed/failed/canceled) are delivered.
     * <p>
     *  呼叫通知客户端不会再发送更多事件。产生此事件的一个原因是如果作业已成功完成,但是打印系统的能力有限并且不能验证这一点。如果未传送任何其他终端事件(完成/失败/取消),则需要传送此事件。
     * 
     * 
     * @param pje the job generating this event
     */
    public void printJobNoMoreEvents(PrintJobEvent pje) ;


    /**
     * Called to notify the client that an error has occurred that the
     * user might be able to fix.  One example of an error that can
     * generate this event is when the printer runs out of paper.
     * <p>
     * 调用以通知客户端发生用户可能能够修复的错误。可以生成此事件的错误的一个示例是当打印机缺纸时。
     * 
     * @param pje the job generating this event
     */
    public void printJobRequiresAttention(PrintJobEvent pje) ;

}
