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
import java.util.List;
import java.util.Collection;

/**
 * An {@link Executor} that provides methods to manage termination and
 * methods that can produce a {@link Future} for tracking progress of
 * one or more asynchronous tasks.
 *
 * <p>An {@code ExecutorService} can be shut down, which will cause
 * it to reject new tasks.  Two different methods are provided for
 * shutting down an {@code ExecutorService}. The {@link #shutdown}
 * method will allow previously submitted tasks to execute before
 * terminating, while the {@link #shutdownNow} method prevents waiting
 * tasks from starting and attempts to stop currently executing tasks.
 * Upon termination, an executor has no tasks actively executing, no
 * tasks awaiting execution, and no new tasks can be submitted.  An
 * unused {@code ExecutorService} should be shut down to allow
 * reclamation of its resources.
 *
 * <p>Method {@code submit} extends base method {@link
 * Executor#execute(Runnable)} by creating and returning a {@link Future}
 * that can be used to cancel execution and/or wait for completion.
 * Methods {@code invokeAny} and {@code invokeAll} perform the most
 * commonly useful forms of bulk execution, executing a collection of
 * tasks and then waiting for at least one, or all, to
 * complete. (Class {@link ExecutorCompletionService} can be used to
 * write customized variants of these methods.)
 *
 * <p>The {@link Executors} class provides factory methods for the
 * executor services provided in this package.
 *
 * <h3>Usage Examples</h3>
 *
 * Here is a sketch of a network service in which threads in a thread
 * pool service incoming requests. It uses the preconfigured {@link
 * Executors#newFixedThreadPool} factory method:
 *
 *  <pre> {@code
 * class NetworkService implements Runnable {
 *   private final ServerSocket serverSocket;
 *   private final ExecutorService pool;
 *
 *   public NetworkService(int port, int poolSize)
 *       throws IOException {
 *     serverSocket = new ServerSocket(port);
 *     pool = Executors.newFixedThreadPool(poolSize);
 *   }
 *
 *   public void run() { // run the service
 *     try {
 *       for (;;) {
 *         pool.execute(new Handler(serverSocket.accept()));
 *       }
 *     } catch (IOException ex) {
 *       pool.shutdown();
 *     }
 *   }
 * }
 *
 * class Handler implements Runnable {
 *   private final Socket socket;
 *   Handler(Socket socket) { this.socket = socket; }
 *   public void run() {
 *     // read and service request on socket
 *   }
 * }}</pre>
 *
 * The following method shuts down an {@code ExecutorService} in two phases,
 * first by calling {@code shutdown} to reject incoming tasks, and then
 * calling {@code shutdownNow}, if necessary, to cancel any lingering tasks:
 *
 *  <pre> {@code
 * void shutdownAndAwaitTermination(ExecutorService pool) {
 *   pool.shutdown(); // Disable new tasks from being submitted
 *   try {
 *     // Wait a while for existing tasks to terminate
 *     if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
 *       pool.shutdownNow(); // Cancel currently executing tasks
 *       // Wait a while for tasks to respond to being cancelled
 *       if (!pool.awaitTermination(60, TimeUnit.SECONDS))
 *           System.err.println("Pool did not terminate");
 *     }
 *   } catch (InterruptedException ie) {
 *     // (Re-)Cancel if current thread also interrupted
 *     pool.shutdownNow();
 *     // Preserve interrupt status
 *     Thread.currentThread().interrupt();
 *   }
 * }}</pre>
 *
 * <p>Memory consistency effects: Actions in a thread prior to the
 * submission of a {@code Runnable} or {@code Callable} task to an
 * {@code ExecutorService}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * any actions taken by that task, which in turn <i>happen-before</i> the
 * result is retrieved via {@code Future.get()}.
 *
 * <p>
 *  一个{@link Executor},它提供了管理终止的方法和可以产生{@link Future}的方法,用于跟踪一个或多个异步任务的进度。
 * 
 *  <p> {@code ExecutorService}可以关闭,这会导致它拒绝新任务。提供了两种不同的方法来关闭{@code ExecutorService}。
 *  {@link #shutdown}方法将允许先前提交的任务在终止前执行,而{@link #shutdownNow}方法可防止等待任务启动并尝试停止当前正在执行的任务。
 * 在终止时,执行程序没有任何活动执行的任务,没有等待执行的任务,并且不能提交新任务。未使用的{@code ExecutorService}应该关闭以允许回收其资源。
 * 
 * <p>通过创建并返回可用于取消执行和/或等待完成的{@link Future},{@code submit}扩展基本方法{@link Executor#execute(Runnable)}。
 * 方法{@code invokeAny}和{@code invokeAll}执行最常用的批量执行形式,执行一组任务,然后等待至少一个或全部完成。
 *  (类{@link ExecutorCompletionService}可用于编写这些方法的自定义变体。)。
 * 
 *  <p> {@link Executors}类为此程序包中提供的执行程序服务提供了工厂方法。
 * 
 *  <h3>使用示例</h3>
 * 
 *  这里是一个网络服务的草图,其中线程池中的线程服务传入请求。它使用预配置的{@link Executors#newFixedThreadPool}工厂方法：
 * 
 *  <pre> {@code类NetworkService实现Runnable {private final ServerSocket serverSocket; private final ExecutorService pool;。
 * 
 *  public NetworkService(int port,int poolSize)throws IOException {serverSocket = new ServerSocket(port); pool = Executors.newFixedThreadPool(poolSize); }
 * }。
 * 
 *  public void run(){//运行服务尝试{for(;;){pool.execute(new Handler(serverSocket.accept())); }} catch(IOExce
 * ption ex){pool.shutdown(); }}}。
 * 
 *  类Handler实现Runnable {private final Socket socket; Handler(Socket socket){this.socket = socket; } publ
 * ic void run(){// socket上的读取和服务请求}}} </pre>。
 * 
 * 以下方法在两个阶段关闭{@code ExecutorService},首先通过调用{@code shutdown}拒绝接收的任务,然后调用{@code shutdownNow}(如有必要)取消任何延迟任
 * 务：。
 * 
 *  <pre> {@code void shutdownAndAwaitTermination(ExecutorService pool){pool.shutdown(); //禁止新任务被提交try {//如果(！pool.awaitTermination(60,TimeUnit.SECONDS)){pool.shutdownNow();}
 * 等待一段时间, //取消当前正在执行的任务//等待一段时间,任务响应被取消if(！pool.awaitTermination(60,TimeUnit.SECONDS))System.err.printl
 * n("池未终止"); }} catch(InterruptedException ie){//(重新)取消当前线程也中断pool.shutdownNow(); //保持中断状态Thread.currentThread()。
 * interrupt(); }}} </pre>。
 * 
 *  <p>内存一致性影响：在将{@code Runnable}或{@code Callable}任务提交给{@code ExecutorService} <a href="package-summary.html#MemoryVisibility">
 * 之前,线程中的操作<i>发生在</i>之前</i>任何由该任务采取的动作,而这又发生在通过{@code Future.get()}检索的结果之前。
 * 
 * 
 * @since 1.5
 * @author Doug Lea
 */
public interface ExecutorService extends Executor {

    /**
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted.
     * Invocation has no additional effect if already shut down.
     *
     * <p>This method does not wait for previously submitted tasks to
     * complete execution.  Use {@link #awaitTermination awaitTermination}
     * to do that.
     *
     * <p>
     *  启动有序关闭,其中执行先前提交的任务,但不会接受新任务。如果已关闭,调用没有其他效果。
     * 
     * <p>此方法不等待先前提交的任务完成执行。使用{@link #awaitTermination awaitTermination}这样做。
     * 
     * 
     * @throws SecurityException if a security manager exists and
     *         shutting down this ExecutorService may manipulate
     *         threads that the caller is not permitted to modify
     *         because it does not hold {@link
     *         java.lang.RuntimePermission}{@code ("modifyThread")},
     *         or the security manager's {@code checkAccess} method
     *         denies access.
     */
    void shutdown();

    /**
     * Attempts to stop all actively executing tasks, halts the
     * processing of waiting tasks, and returns a list of the tasks
     * that were awaiting execution.
     *
     * <p>This method does not wait for actively executing tasks to
     * terminate.  Use {@link #awaitTermination awaitTermination} to
     * do that.
     *
     * <p>There are no guarantees beyond best-effort attempts to stop
     * processing actively executing tasks.  For example, typical
     * implementations will cancel via {@link Thread#interrupt}, so any
     * task that fails to respond to interrupts may never terminate.
     *
     * <p>
     *  尝试停止所有正在执行的任务,停止等待任务的处理,并返回等待执行的任务的列表。
     * 
     *  <p>此方法不等待主动执行任务终止。使用{@link #awaitTermination awaitTermination}这样做。
     * 
     *  <p>除了尽力尝试停止处理主动执行的任务之外,没有任何保证。例如,典型的实现将通过{@link Thread#interrupt}取消,因此任何未能响应中断的任务可能永远不会终止。
     * 
     * 
     * @return list of tasks that never commenced execution
     * @throws SecurityException if a security manager exists and
     *         shutting down this ExecutorService may manipulate
     *         threads that the caller is not permitted to modify
     *         because it does not hold {@link
     *         java.lang.RuntimePermission}{@code ("modifyThread")},
     *         or the security manager's {@code checkAccess} method
     *         denies access.
     */
    List<Runnable> shutdownNow();

    /**
     * Returns {@code true} if this executor has been shut down.
     *
     * <p>
     *  如果此执行器已关闭,则返回{@code true}。
     * 
     * 
     * @return {@code true} if this executor has been shut down
     */
    boolean isShutdown();

    /**
     * Returns {@code true} if all tasks have completed following shut down.
     * Note that {@code isTerminated} is never {@code true} unless
     * either {@code shutdown} or {@code shutdownNow} was called first.
     *
     * <p>
     *  如果所有任务在关闭后完成,则返回{@code true}。
     * 请注意,除非先调用{@code shutdown}或{@code shutdownNow},否则{@code isTerminated}永远不会是{@code true}。
     * 
     * 
     * @return {@code true} if all tasks have completed following shut down
     */
    boolean isTerminated();

    /**
     * Blocks until all tasks have completed execution after a shutdown
     * request, or the timeout occurs, or the current thread is
     * interrupted, whichever happens first.
     *
     * <p>
     *  阻塞直到所有任务在关闭请求后完成执行,或超时发生,或当前线程被中断,以先发生者为准。
     * 
     * 
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return {@code true} if this executor terminated and
     *         {@code false} if the timeout elapsed before termination
     * @throws InterruptedException if interrupted while waiting
     */
    boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Submits a value-returning task for execution and returns a
     * Future representing the pending results of the task. The
     * Future's {@code get} method will return the task's result upon
     * successful completion.
     *
     * <p>
     * If you would like to immediately block waiting
     * for a task, you can use constructions of the form
     * {@code result = exec.submit(aCallable).get();}
     *
     * <p>Note: The {@link Executors} class includes a set of methods
     * that can convert some other common closure-like objects,
     * for example, {@link java.security.PrivilegedAction} to
     * {@link Callable} form so they can be submitted.
     *
     * <p>
     *  提交要执行的值返回任务,并返回代表任务的待处理结果的Future。 Future的{@code get}方法将在成功完成后返回任务的结果。
     * 
     * <p>
     *  如果你想立即阻塞等待任务,你可以使用{@code result = exec.submit(aCallable).get();}形式的构造,
     * 
     * <p>注意：{@link Executors}类包含一组方法,可将其他常见的类似闭包的对象(例如{@link java.security.PrivilegedAction})转换为{@link Callable}
     * 表单,以便他们可以提交。
     * 
     * 
     * @param task the task to submit
     * @param <T> the type of the task's result
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    <T> Future<T> submit(Callable<T> task);

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return the given result upon successful completion.
     *
     * <p>
     *  提交Runnable任务以执行,并返回代表该任务的Future。未来的{@code get}方法将在成功完成后返回给定的结果。
     * 
     * 
     * @param task the task to submit
     * @param result the result to return
     * @param <T> the type of the result
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    <T> Future<T> submit(Runnable task, T result);

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return {@code null} upon <em>successful</em> completion.
     *
     * <p>
     *  提交Runnable任务以执行,并返回代表该任务的Future。未来的{@code get}方法会在<em>成功</em>完成后返回{@code null}。
     * 
     * 
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    Future<?> submit(Runnable task);

    /**
     * Executes the given tasks, returning a list of Futures holding
     * their status and results when all complete.
     * {@link Future#isDone} is {@code true} for each
     * element of the returned list.
     * Note that a <em>completed</em> task could have
     * terminated either normally or by throwing an exception.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * <p>
     *  执行给定的任务,返回一个期货的列表,保存他们的状态和结果,当所有完成。 {@link Future#isDone}对于返回的列表的每个元素都是{@code true}。
     * 请注意,<em>完成的</em>任务可能已正常终止或抛出异常。如果在此操作正在进行时修改给定集合,则此方法的结果是未定义的。
     * 
     * 
     * @param tasks the collection of tasks
     * @param <T> the type of the values returned from the tasks
     * @return a list of Futures representing the tasks, in the same
     *         sequential order as produced by the iterator for the
     *         given task list, each of which has completed
     * @throws InterruptedException if interrupted while waiting, in
     *         which case unfinished tasks are cancelled
     * @throws NullPointerException if tasks or any of its elements are {@code null}
     * @throws RejectedExecutionException if any task cannot be
     *         scheduled for execution
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
        throws InterruptedException;

    /**
     * Executes the given tasks, returning a list of Futures holding
     * their status and results
     * when all complete or the timeout expires, whichever happens first.
     * {@link Future#isDone} is {@code true} for each
     * element of the returned list.
     * Upon return, tasks that have not completed are cancelled.
     * Note that a <em>completed</em> task could have
     * terminated either normally or by throwing an exception.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * <p>
     *  执行给定的任务,在所有完成或超时到期时返回保持其状态和结果的期货的列表,以先发生者为准。 {@link Future#isDone}对于返回的列表的每个元素都是{@code true}。
     * 返回后,未完成的任务将被取消。请注意,<em>完成的</em>任务可能已正常终止或抛出异常。如果在此操作正在进行时修改给定集合,则此方法的结果是未定义的。
     * 
     * 
     * @param tasks the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @param <T> the type of the values returned from the tasks
     * @return a list of Futures representing the tasks, in the same
     *         sequential order as produced by the iterator for the
     *         given task list. If the operation did not time out,
     *         each task will have completed. If it did time out, some
     *         of these tasks will not have completed.
     * @throws InterruptedException if interrupted while waiting, in
     *         which case unfinished tasks are cancelled
     * @throws NullPointerException if tasks, any of its elements, or
     *         unit are {@code null}
     * @throws RejectedExecutionException if any task cannot be scheduled
     *         for execution
     */
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                  long timeout, TimeUnit unit)
        throws InterruptedException;

    /**
     * Executes the given tasks, returning the result
     * of one that has completed successfully (i.e., without throwing
     * an exception), if any do. Upon normal or exceptional return,
     * tasks that have not completed are cancelled.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * <p>
     * 执行给定的任务,返回已成功完成的结果(即,不抛出异常),如果有的话。在正常或异常返回时,未完成的任务被取消。如果在此操作正在进行时修改给定集合,则此方法的结果是未定义的。
     * 
     * 
     * @param tasks the collection of tasks
     * @param <T> the type of the values returned from the tasks
     * @return the result returned by one of the tasks
     * @throws InterruptedException if interrupted while waiting
     * @throws NullPointerException if tasks or any element task
     *         subject to execution is {@code null}
     * @throws IllegalArgumentException if tasks is empty
     * @throws ExecutionException if no task successfully completes
     * @throws RejectedExecutionException if tasks cannot be scheduled
     *         for execution
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
        throws InterruptedException, ExecutionException;

    /**
     * Executes the given tasks, returning the result
     * of one that has completed successfully (i.e., without throwing
     * an exception), if any do before the given timeout elapses.
     * Upon normal or exceptional return, tasks that have not
     * completed are cancelled.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * <p>
     *  执行给定任务,返回已成功完成的任务的结果(即,不抛出异常),如果在给定超时时间之前有任何操作。在正常或异常返回时,未完成的任务被取消。如果在此操作正在进行时修改给定集合,则此方法的结果是未定义的。
     * 
     * @param tasks the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @param <T> the type of the values returned from the tasks
     * @return the result returned by one of the tasks
     * @throws InterruptedException if interrupted while waiting
     * @throws NullPointerException if tasks, or unit, or any element
     *         task subject to execution is {@code null}
     * @throws TimeoutException if the given timeout elapses before
     *         any task successfully completes
     * @throws ExecutionException if no task successfully completes
     * @throws RejectedExecutionException if tasks cannot be scheduled
     *         for execution
     */
    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                    long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException;
}
