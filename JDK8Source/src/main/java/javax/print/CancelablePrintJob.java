/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

/**
 * This interface is used by a printing application to cancel a
 * print job.  This interface extends {@link DocPrintJob}.  A
 * <code>DocPrintJob</code> implementation returned from a print
 * service implements this interface if the print job can be
 * cancelled.  Before trying to cancel
 * a print job, the client needs to test if the
 * <code>DocPrintJob</code> object returned from the print service
 * actually implements this interface.  Clients should never assume
 * that a <code>DocPrintJob</code> implements this interface.  A
 * print service might support cancellation only for certain types
 * of print data and representation class names.  This means that
 * only some of the <code>DocPrintJob</code> objects returned from
 * a service will implement this interface.
 * <p>
 * Service implementors are encouraged to implement this optional interface
 * and to deliver a javax.print.event.PrintJobEvent.JOB_CANCELLED event
 * to any listeners if a job is successfully cancelled with an
 * implementation of this interface. Services should also note that an
 * implementation of this method may be made from a separate client thread
 * than that which made the print request.  Thus the implementation of
 * this interface must be made thread safe.
 * <p>
 *  此接口由打印应用程序使用以取消打印作业。此界面扩展{@link DocPrintJob}。从打印服务返回的<code> DocPrintJob </code>实现实现此接口,如果可以取消打印作业。
 * 在尝试取消打印作业之前,客户端需要测试从打印服务返回的<code> DocPrintJob </code>对象是否实际实现了此接口。
 * 客户端不应该假设<code> DocPrintJob </code>实现此接口。打印服务可能仅支持取消某些类型的打印数据和表示类名称。
 * 这意味着只有从服务返回的一些<code> DocPrintJob </code>对象将实现此接口。
 * <p>
 *  鼓励服务实现者实现此可选接口,并且如果使用此接口的实现成功取消作业,则向任何侦听器传递javax.print.event.PrintJobEvent.JOB_CANCELLED事件。
 * 服务还应该注意,该方法的实现可以从与发出打印请求的客户机线程不同的客户机线程来进行。因此这个接口的实现必须使线程安全。
 * 
 */

public interface CancelablePrintJob extends DocPrintJob {

    /**
     * Stops further processing of a print job.
     * <p>
     * If a service supports this method it cannot be concluded that
     * job cancellation will always succeed. A job may not be able to be
     * cancelled once it has reached and passed some point in its processing.
     * A successful cancellation means only that the entire job was not
     * printed, some portion may already have printed when cancel returns.
     * <p>
     * The service will throw a PrintException if the cancellation did not
     * succeed. A job which has not yet been submitted for printing should
     * throw this exception.
     * Cancelling an already successfully cancelled Print Job is not
     * considered an error and will always succeed.
     * <p>
     * Cancellation in some services may be a lengthy process, involving
     * requests to a server and processing of its print queue. Clients
     * may wish to execute cancel in a thread which does not affect
     * application execution.
     * <p>
     *  停止打印作业的进一步处理。
     * <p>
     * 如果服务支持此方法,则不能断定作业取消将总是成功。作业可能无法在其处理中达到并通过某个点后被取消。成功取消仅意味着整个作业不被打印,当取消返回时,某些部分可能已经打印。
     * <p>
     * 
     * @throws PrintException if the job could not be successfully cancelled.
     */
    public void cancel() throws PrintException;

}
