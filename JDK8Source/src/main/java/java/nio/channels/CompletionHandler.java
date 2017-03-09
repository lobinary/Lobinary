/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.channels;

/**
 * A handler for consuming the result of an asynchronous I/O operation.
 *
 * <p> The asynchronous channels defined in this package allow a completion
 * handler to be specified to consume the result of an asynchronous operation.
 * The {@link #completed completed} method is invoked when the I/O operation
 * completes successfully. The {@link #failed failed} method is invoked if the
 * I/O operations fails. The implementations of these methods should complete
 * in a timely manner so as to avoid keeping the invoking thread from dispatching
 * to other completion handlers.
 *
 * <p>
 *  用于消耗异步I / O操作结果的处理程序。
 * 
 *  <p>此程序包中定义的异步通道允许指定一个完成处理程序来消耗异步操作的结果。当I / O操作成功完成时,调用{@link #completed completed}方法。
 * 如果I / O操作失败,则调用{@link #failed failed}方法。这些方法的实现应该及时完成,以避免保持调用线程调度到其他完成处理程序。
 * 
 * 
 * @param   <V>     The result type of the I/O operation
 * @param   <A>     The type of the object attached to the I/O operation
 *
 * @since 1.7
 */

public interface CompletionHandler<V,A> {

    /**
     * Invoked when an operation has completed.
     *
     * <p>
     *  操作完成时调用。
     * 
     * 
     * @param   result
     *          The result of the I/O operation.
     * @param   attachment
     *          The object attached to the I/O operation when it was initiated.
     */
    void completed(V result, A attachment);

    /**
     * Invoked when an operation fails.
     *
     * <p>
     *  操作失败时调用。
     * 
     * @param   exc
     *          The exception to indicate why the I/O operation failed
     * @param   attachment
     *          The object attached to the I/O operation when it was initiated.
     */
    void failed(Throwable exc, A attachment);
}
