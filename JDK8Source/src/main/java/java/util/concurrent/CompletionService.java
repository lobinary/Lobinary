/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/1.0/
 * 
 */

package java.util.concurrent;

/**
 * A service that decouples the production of new asynchronous tasks
 * from the consumption of the results of completed tasks.  Producers
 * {@code submit} tasks for execution. Consumers {@code take}
 * completed tasks and process their results in the order they
 * complete.  A {@code CompletionService} can for example be used to
 * manage asynchronous I/O, in which tasks that perform reads are
 * submitted in one part of a program or system, and then acted upon
 * in a different part of the program when the reads complete,
 * possibly in a different order than they were requested.
 *
 * <p>Typically, a {@code CompletionService} relies on a separate
 * {@link Executor} to actually execute the tasks, in which case the
 * {@code CompletionService} only manages an internal completion
 * queue. The {@link ExecutorCompletionService} class provides an
 * implementation of this approach.
 *
 * <p>Memory consistency effects: Actions in a thread prior to
 * submitting a task to a {@code CompletionService}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions taken by that task, which in turn <i>happen-before</i>
 * actions following a successful return from the corresponding {@code take()}.
 * <p>
 *  将新异步任务的生产与已完成任务的结果消耗分离的服务。生产者{@code提交}要执行的任务。消费者{@code take}完成任务,并按照他们完成的顺序处理结果。
 *  {@code CompletionService}例如可以用于管理异步I / O,其中执行读取的任务在程序或系统的一部分中提交,然后当读取完成时在程序的不同部分中执行,可能以不同于他们要求的顺序。
 * 
 *  <p>通常,{@code CompletionService}依赖于一个单独的{@link Executor}来实际执行这些任务,在这种情况下,{@code CompletionService}仅管理
 * 一个内部完成队列。
 *  {@link ExecutorCompletionService}类提供了此方法的实现。
 * 
 *  <p>内存一致性效果：在向{@code CompletionService} <a href="package-summary.html#MemoryVisibility"> <i>发生之前</i> 
 * </a提交任务之前,线程中的操作>该任务采取的动作,而这些动作又发生在从相应的{@code take()}成功返回之后的</i>之前的动作。
 * 
 */
public interface CompletionService<V> {
    /**
     * Submits a value-returning task for execution and returns a Future
     * representing the pending results of the task.  Upon completion,
     * this task may be taken or polled.
     *
     * <p>
     * 提交要执行的值返回任务,并返回代表任务的待处理结果的Future。完成后,可以执行或轮询此任务。
     * 
     * 
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    Future<V> submit(Callable<V> task);

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task.  Upon completion, this task may be
     * taken or polled.
     *
     * <p>
     *  提交Runnable任务以执行,并返回代表该任务的Future。完成后,可以执行或轮询此任务。
     * 
     * 
     * @param task the task to submit
     * @param result the result to return upon successful completion
     * @return a Future representing pending completion of the task,
     *         and whose {@code get()} method will return the given
     *         result value upon completion
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    Future<V> submit(Runnable task, V result);

    /**
     * Retrieves and removes the Future representing the next
     * completed task, waiting if none are yet present.
     *
     * <p>
     *  检索并删除代表下一个已完成任务的"未来",如果尚未存在,则等待。
     * 
     * 
     * @return the Future representing the next completed task
     * @throws InterruptedException if interrupted while waiting
     */
    Future<V> take() throws InterruptedException;

    /**
     * Retrieves and removes the Future representing the next
     * completed task, or {@code null} if none are present.
     *
     * <p>
     *  检索并删除代表下一个已完成任务的未来,或{@code null}(如果没有)。
     * 
     * 
     * @return the Future representing the next completed task, or
     *         {@code null} if none are present
     */
    Future<V> poll();

    /**
     * Retrieves and removes the Future representing the next
     * completed task, waiting if necessary up to the specified wait
     * time if none are yet present.
     *
     * <p>
     *  检索并删除代表下一个已完成任务的未来,如果有必要,等待指定的等待时间(如果尚未存在)。
     * 
     * @param timeout how long to wait before giving up, in units of
     *        {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the
     *        {@code timeout} parameter
     * @return the Future representing the next completed task or
     *         {@code null} if the specified waiting time elapses
     *         before one is present
     * @throws InterruptedException if interrupted while waiting
     */
    Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException;
}
