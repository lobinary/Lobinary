/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.print.attribute.PrintJobAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobAttributeListener;
import javax.print.event.PrintJobListener;
import javax.print.PrintException;

/**
 *
 * This interface represents a print job that can print a specified
 * document with a set of job attributes.  An object implementing
 * this interface is obtained from a print service.
 *
 * <p>
 *  此接口表示可以打印具有一组作业属性的指定文档的打印作业。从打印服务获得实现此接口的对象。
 * 
 */

public interface DocPrintJob {

    /**
     * Determines the {@link PrintService} object to which this print job
     * object is bound.
     *
     * <p>
     *  确定此打印作业对象绑定到的{@link PrintService}对象。
     * 
     * 
     * @return  <code>PrintService</code> object.
     *
     */
    public PrintService getPrintService();

    /**
     * Obtains this Print Job's set of printing attributes.
     * The returned attribute set object is unmodifiable.
     * The returned attribute set object is a "snapshot" of this Print Job's
     * attribute set at the time of the {@link #getAttributes()} method
     * call; that is, the returned attribute set's object's contents will
     * not be updated if this Print Job's attribute set's contents change
     * in the future. To detect changes in attribute values, call
     * <code>getAttributes()</code> again and compare the new attribute
     * set to the previous attribute set; alternatively, register a
     * listener for print job events.
     * The returned value may be an empty set but should not be null.
     * <p>
     *  获取此打印作业的打印属性集。返回的属性集对象是不可修改的。
     * 返回的属性集对象是在{@link #getAttributes()}方法调用时设置的此打印作业属性的"快照";也就是说,如果此打印作业的属性集的内容将来更改,则不会更新返回的属性集的对象的内容。
     * 要检测属性值的更改,请再次调用<code> getAttributes()</code>,并将新属性集与先前的属性集进行比较;或者,为打印作业事件注册侦听器。返回的值可以是空集,但不应为null。
     * 
     * 
     * @return the print job attributes
     */
     public PrintJobAttributeSet getAttributes();

    /**
     * Registers a listener for event occurring during this print job.
     * If listener is null, no exception is thrown and no action is
     * performed.
     * If listener is already registered, it will be registered again.
     * <p>
     *  为在此打印作业期间发生的事件注册侦听器。如果侦听器为null,则不抛出异常,并且不执行任何操作。如果监听器已经注册,它将再次注册。
     * 
     * 
     * @see #removePrintJobListener
     *
     * @param listener  The object implementing the listener interface
     *
     */
    public void addPrintJobListener(PrintJobListener listener);

    /**
     * Removes a listener from this print job.
     * This method performs no function, nor does it throw an exception,
     * if the listener specified by the argument was not previously added
     * to this component. If listener is null, no exception is thrown and
     * no action is performed. If a listener was registered more than once
     * only one of the registrations will be removed.
     * <p>
     * 从此打印作业中删除侦听器。如果参数指定的侦听器以前未添加到此组件,此方法不执行任何函数,也不会抛出异常。如果侦听器为null,则不抛出异常,并且不执行任何操作。
     * 如果侦听器被多次注册,则只会删除其中一个注册。
     * 
     * 
     * @see #addPrintJobListener
     *
     * @param listener  The object implementing the listener interface
     */
    public void removePrintJobListener(PrintJobListener listener);

    /**
     * Registers a listener for changes in the specified attributes.
     * If listener is null, no exception is thrown and no action is
     * performed.
     * To determine the attribute updates that may be reported by this job,
     * a client can call <code>getAttributes()</code> and identify the
     * subset that are interesting and likely to be reported to the
     * listener. Clients expecting to be updated about changes in a
     * specific job attribute should verify it is in that set, but
     * updates about an attribute will be made only if it changes and this
     * is detected by the job. Also updates may be subject to batching
     * by the job. To minimize overhead in print job processing it is
     * recommended to listen on only that subset of attributes which
     * are likely to change.
     * If the specified set is empty no attribute updates will be reported
     * to the listener.
     * If the attribute set is null, then this means to listen on all
     * dynamic attributes that the job supports. This may result in no
     * update notifications if a job can not report any attribute updates.
     *
     * If listener is already registered, it will be registered again.
     * <p>
     *  为指定属性中的更改注册侦听器。如果侦听器为null,则不抛出异常,并且不执行任何操作。
     * 要确定此作业可能报告的属性更新,客户端可以调用<code> getAttributes()</code>,并标识有趣且可能向侦听器报告的子集。
     * 希望对特定作业属性中的更改进行更新的客户端应验证该属性是否在该集合中,但只有在属性发生更改且作业检测到属性时才会更新属性。此外,更新可能受作业的批处理。
     * 为了最小化打印作业处理中的开销,建议仅侦听可能改变的属性的子集。如果指定的集为空,则不会将属性更新报告给侦听器。如果属性集为null,则意味着侦听作业支持的所有动态属性。
     * 如果作业无法报告任何属性更新,则可能导致无更新通知。
     * 
     *  如果监听器已经注册,它将再次注册。
     * 
     * 
     * @see #removePrintJobAttributeListener
     *
     * @param listener  The object implementing the listener interface
     * @param attributes The attributes to listen on, or null to mean
     * all attributes that can change, as determined by the job.
     */
    public void addPrintJobAttributeListener(
                                  PrintJobAttributeListener listener,
                                  PrintJobAttributeSet attributes);

    /**
     * Removes an attribute listener from this print job.
     * This method performs no function, nor does it throw an exception,
     * if the listener specified by the argument was not previously added
     * to this component. If the listener is null, no exception is thrown
     * and no action is performed.
     * If a listener is registered more than once, even for a different
     * set of attributes, no guarantee is made which listener is removed.
     * <p>
     * 从此打印作业中删除属性侦听器。如果参数指定的侦听器以前未添加到此组件,此方法不执行任何函数,也不会抛出异常。如果侦听器为null,则不抛出异常,并且不执行任何操作。
     * 如果监听器被多次注册,即使对于一组不同的属性,也不能保证删除哪个监听器。
     * 
     * 
     * @see #addPrintJobAttributeListener
     *
     * @param listener  The object implementing the listener interface
     *
     */
    public void removePrintJobAttributeListener(
                                      PrintJobAttributeListener listener);

    /**
     * Prints a document with the specified job attributes.
     * This method should only be called once for a given print job.
     * Calling it again will not result in a new job being spooled to
     * the printer. The service implementation will define policy
     * for service interruption and recovery.
     * When the print method returns, printing may not yet have completed as
     * printing may happen asynchronously, perhaps in a different thread.
     * Application clients which  want to monitor the success or failure
     * should register a PrintJobListener.
     * <p>
     * Print service implementors should close any print data streams (ie
     * Reader or InputStream implementations) that they obtain
     * from the client doc. Robust clients may still wish to verify this.
     * An exception is always generated if a <code>DocFlavor</code> cannot
     * be printed.
     *
     * <p>
     *  打印具有指定作业属性的文档。对于给定的打印作业,此方法只应调用一次。再次调用它不会导致将新作业假脱机到打印机。服务实现将定义用于服务中断和恢复的策略。
     * 当打印方法返回时,打印可能尚未完成,因为打印可能异步地发生,可能在不同的线程中。要监视成功或失败的应用程序客户端应注册一个PrintJobListener。
     * <p>
     * 
     * @param doc       The document to be printed. If must be a flavor
     *                                  supported by this PrintJob.
     *
     * @param attributes The job attributes to be applied to this print job.
     *        If this parameter is null then the default attributes are used.
     * @throws PrintException The exception additionally may implement
     * an interface that more precisely describes the cause of the
     * exception
     * <ul>
     * <li>FlavorException.
     *  If the document has a flavor not supported by this print job.
     * <li>AttributeException.
     *  If one or more of the attributes are not valid for this print job.
     * </ul>
     */
    public void print(Doc doc, PrintRequestAttributeSet attributes)
          throws PrintException;

}
