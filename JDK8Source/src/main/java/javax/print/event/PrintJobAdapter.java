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
  * An abstract adapter class for receiving print job events.
  * The methods in this class are empty.
  * This class exists as a convenience for creating listener objects.
  * Extend this class to create a {@link PrintJobEvent} listener and override
  * the methods for the events of interest.  Unlike the
  * {@link java.awt.event.ComponentListener ComponentListener}
  * interface, this abstract interface provides null methods so that you
  * only need to define the methods you need, rather than all of the methods.
  *
  * <p>
  *  用于接收打印作业事件的抽象适配器类。此类中的方法为空。此类存在为方便创建侦听器对象。扩展此类来创建一个{@link PrintJobEvent}侦听器,并覆盖感兴趣的事件的方法。
  * 与{@link java.awt.event.ComponentListener ComponentListener}接口不同,此抽象接口提供了空方法,因此您只需要定义所需的方法,而不是所有方法。
  * 
  */

public abstract class PrintJobAdapter implements PrintJobListener {

    /**
     * Called to notify the client that data has been successfully
     * transferred to the print service, and the client may free
     * local resources allocated for that data.  The client should
     * not assume that the data has been completely printed after
     * receiving this event.
     *
     * <p>
     *  调用以通知客户端数据已成功传输到打印服务,并且客户端可释放为该数据分配的本地资源。客户端在接收到此事件后不应假定数据已完全打印。
     * 
     * 
     * @param pje the event being notified
     */
    public void printDataTransferCompleted(PrintJobEvent pje)  {
    }

    /**
     * Called to notify the client that the job completed successfully.
     *
     * <p>
     *  调用以通知客户端作业成功完成。
     * 
     * 
     * @param pje the event being notified
     */
    public void printJobCompleted(PrintJobEvent pje)  {
    }


    /**
     * Called to notify the client that the job failed to complete
     * successfully and will have to be resubmitted.
     *
     * <p>
     *  调用通知客户端作业无法成功完成,必须重新提交。
     * 
     * 
     * @param pje the event being notified
     */
    public void printJobFailed(PrintJobEvent pje)  {
    }

    /**
     * Called to notify the client that the job was canceled
     * by user or program.
     *
     * <p>
     *  调用以通知客户端作业已被用户或程序取消。
     * 
     * 
     * @param pje the event being notified
     */
    public void printJobCanceled(PrintJobEvent pje) {
    }


    /**
     * Called to notify the client that no more events will be delivered.
     * One cause of this event being generated is if the job
     * has successfully completed, but the printing system
     * is limited in capability and cannot verify this.
     * This event is required to be delivered if none of the other
     * terminal events (completed/failed/canceled) are delivered.
     *
     * <p>
     *  呼叫通知客户端不会再发送更多事件。产生此事件的一个原因是如果作业已成功完成,但是打印系统的能力有限并且不能验证这一点。如果未传送任何其他终端事件(完成/失败/取消),则需要传送此事件。
     * 
     * 
     * @param pje the event being notified
     */
    public void printJobNoMoreEvents(PrintJobEvent pje)  {
    }


    /**
     * Called to notify the client that some possibly user rectifiable
     * problem occurs (eg printer out of paper).
     *
     * <p>
     * 调用以通知客户端可能发生用户可能纠正的问题(例如,打印机缺纸)。
     * 
     * @param pje the event being notified
     */
    public void printJobRequiresAttention(PrintJobEvent pje)  {
    }

}
