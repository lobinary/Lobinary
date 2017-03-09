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
 * A {@code Future} represents the result of an asynchronous
 * computation.  Methods are provided to check if the computation is
 * complete, to wait for its completion, and to retrieve the result of
 * the computation.  The result can only be retrieved using method
 * {@code get} when the computation has completed, blocking if
 * necessary until it is ready.  Cancellation is performed by the
 * {@code cancel} method.  Additional methods are provided to
 * determine if the task completed normally or was cancelled. Once a
 * computation has completed, the computation cannot be cancelled.
 * If you would like to use a {@code Future} for the sake
 * of cancellability but not provide a usable result, you can
 * declare types of the form {@code Future<?>} and
 * return {@code null} as a result of the underlying task.
 *
 * <p>
 * <b>Sample Usage</b> (Note that the following classes are all
 * made-up.)
 * <pre> {@code
 * interface ArchiveSearcher { String search(String target); }
 * class App {
 *   ExecutorService executor = ...
 *   ArchiveSearcher searcher = ...
 *   void showSearch(final String target)
 *       throws InterruptedException {
 *     Future<String> future
 *       = executor.submit(new Callable<String>() {
 *         public String call() {
 *             return searcher.search(target);
 *         }});
 *     displayOtherThings(); // do other things while searching
 *     try {
 *       displayText(future.get()); // use future
 *     } catch (ExecutionException ex) { cleanup(); return; }
 *   }
 * }}</pre>
 *
 * The {@link FutureTask} class is an implementation of {@code Future} that
 * implements {@code Runnable}, and so may be executed by an {@code Executor}.
 * For example, the above construction with {@code submit} could be replaced by:
 *  <pre> {@code
 * FutureTask<String> future =
 *   new FutureTask<String>(new Callable<String>() {
 *     public String call() {
 *       return searcher.search(target);
 *   }});
 * executor.execute(future);}</pre>
 *
 * <p>Memory consistency effects: Actions taken by the asynchronous computation
 * <a href="package-summary.html#MemoryVisibility"> <i>happen-before</i></a>
 * actions following the corresponding {@code Future.get()} in another thread.
 *
 * <p>
 *  {@code Future}表示异步计算的结果。提供了检查计算是否完成,等待其完成以及检索计算结果的方法。只有当计算完成时,才能使用方法{@code get}检索结果,如果必要,阻塞,直到准备好。
 * 取消由{@code cancel}方法执行。提供了附加的方法来确定任务是否正常完成或被取消。一旦计算完成,计算就不能被取消。
 * 如果您想要使用{@code Future}以取消可用性,但不提供可用的结果,则可以声明{@code Future <?>}格式的类型,并返回{@code null}基础任务。
 * 
 * <p>
 * <b>示例用法</b>(请注意,以下类都是组合的。
 * )<pre> {@code interface ArchiveSearcher {String search(String target); } class App {ExecutorService executor = ... ArchiveSearcher searcher = ... void showSearch(final String target)throws InterruptedException {Future <String> future = executor.submit(new Callable <String>(){public String call return searcher.search(target);}
 * }); displayOtherThings(); //做其他事情,而搜索try {displayText(future.get()); // use future} catch(ExecutionEx
 * ception ex){cleanup();返回; }}}} </pre>。
 * <b>示例用法</b>(请注意,以下类都是组合的。
 * 
 *  {@link FutureTask}类是实现{@code Runnable}的{@code Future}的实现,因此可以由{@code Executor}执行。
 * 例如,上面用{@code submit}的结构可以替换为：<pre> {@code FutureTask <String> future = new FutureTask <String>(new Callable <String>(){public String call searcher.search(target);}
 * }); executor.execute(future);} </pre>。
 *  {@link FutureTask}类是实现{@code Runnable}的{@code Future}的实现,因此可以由{@code Executor}执行。
 * 
 *  <p>内存一致性效应：在对应的{@code Future}之后的异步计算<a href="package-summary.html#MemoryVisibility"> <i>发生于</i> </a>
 * 之前的操作。
 *  get()}。
 * 
 * 
 * @see FutureTask
 * @see Executor
 * @since 1.5
 * @author Doug Lea
 * @param <V> The result type returned by this Future's {@code get} method
 */
public interface Future<V> {

    /**
     * Attempts to cancel execution of this task.  This attempt will
     * fail if the task has already completed, has already been cancelled,
     * or could not be cancelled for some other reason. If successful,
     * and this task has not started when {@code cancel} is called,
     * this task should never run.  If the task has already started,
     * then the {@code mayInterruptIfRunning} parameter determines
     * whether the thread executing this task should be interrupted in
     * an attempt to stop the task.
     *
     * <p>After this method returns, subsequent calls to {@link #isDone} will
     * always return {@code true}.  Subsequent calls to {@link #isCancelled}
     * will always return {@code true} if this method returned {@code true}.
     *
     * <p>
     * 尝试取消此任务的执行。如果任务已完成,已取消或因其他原因无法取消,则此尝试将失败。如果成功,并且调用{@code cancel}时此任务尚未启动,则此任务不应运行。
     * 如果任务已经启动,那么{@code mayInterruptIfRunning}参数确定是否应该中断执行此任务的线程,以尝试停止任务。
     * 
     *  <p>此方法返回后,对{@link #isDone}的后续调用将始终返回{@code true}。
     * 如果此方法返回{@code true},对{@link #isCancelled}的后续调用将始终返回{@code true}。
     * 
     * 
     * @param mayInterruptIfRunning {@code true} if the thread executing this
     * task should be interrupted; otherwise, in-progress tasks are allowed
     * to complete
     * @return {@code false} if the task could not be cancelled,
     * typically because it has already completed normally;
     * {@code true} otherwise
     */
    boolean cancel(boolean mayInterruptIfRunning);

    /**
     * Returns {@code true} if this task was cancelled before it completed
     * normally.
     *
     * <p>
     *  如果此任务在正常完成之前被取消,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this task was cancelled before it completed
     */
    boolean isCancelled();

    /**
     * Returns {@code true} if this task completed.
     *
     * Completion may be due to normal termination, an exception, or
     * cancellation -- in all of these cases, this method will return
     * {@code true}.
     *
     * <p>
     *  如果此任务完成,则返回{@code true}。
     * 
     *  完成可能是由于正常终止,异常或取消 - 在所有这些情况下,此方法将返回{@code true}。
     * 
     * 
     * @return {@code true} if this task completed
     */
    boolean isDone();

    /**
     * Waits if necessary for the computation to complete, and then
     * retrieves its result.
     *
     * <p>
     *  如果需要等待计算完成,然后检索其结果。
     * 
     * 
     * @return the computed result
     * @throws CancellationException if the computation was cancelled
     * @throws ExecutionException if the computation threw an
     * exception
     * @throws InterruptedException if the current thread was interrupted
     * while waiting
     */
    V get() throws InterruptedException, ExecutionException;

    /**
     * Waits if necessary for at most the given time for the computation
     * to complete, and then retrieves its result, if available.
     *
     * <p>
     *  如果有必要,最多等待计算完成的给定时间,然后检索其结果(如果可用)。
     * 
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return the computed result
     * @throws CancellationException if the computation was cancelled
     * @throws ExecutionException if the computation threw an
     * exception
     * @throws InterruptedException if the current thread was interrupted
     * while waiting
     * @throws TimeoutException if the wait timed out
     */
    V get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
