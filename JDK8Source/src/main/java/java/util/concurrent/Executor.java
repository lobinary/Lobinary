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
 * An object that executes submitted {@link Runnable} tasks. This
 * interface provides a way of decoupling task submission from the
 * mechanics of how each task will be run, including details of thread
 * use, scheduling, etc.  An {@code Executor} is normally used
 * instead of explicitly creating threads. For example, rather than
 * invoking {@code new Thread(new(RunnableTask())).start()} for each
 * of a set of tasks, you might use:
 *
 * <pre>
 * Executor executor = <em>anExecutor</em>;
 * executor.execute(new RunnableTask1());
 * executor.execute(new RunnableTask2());
 * ...
 * </pre>
 *
 * However, the {@code Executor} interface does not strictly
 * require that execution be asynchronous. In the simplest case, an
 * executor can run the submitted task immediately in the caller's
 * thread:
 *
 *  <pre> {@code
 * class DirectExecutor implements Executor {
 *   public void execute(Runnable r) {
 *     r.run();
 *   }
 * }}</pre>
 *
 * More typically, tasks are executed in some thread other
 * than the caller's thread.  The executor below spawns a new thread
 * for each task.
 *
 *  <pre> {@code
 * class ThreadPerTaskExecutor implements Executor {
 *   public void execute(Runnable r) {
 *     new Thread(r).start();
 *   }
 * }}</pre>
 *
 * Many {@code Executor} implementations impose some sort of
 * limitation on how and when tasks are scheduled.  The executor below
 * serializes the submission of tasks to a second executor,
 * illustrating a composite executor.
 *
 *  <pre> {@code
 * class SerialExecutor implements Executor {
 *   final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
 *   final Executor executor;
 *   Runnable active;
 *
 *   SerialExecutor(Executor executor) {
 *     this.executor = executor;
 *   }
 *
 *   public synchronized void execute(final Runnable r) {
 *     tasks.offer(new Runnable() {
 *       public void run() {
 *         try {
 *           r.run();
 *         } finally {
 *           scheduleNext();
 *         }
 *       }
 *     });
 *     if (active == null) {
 *       scheduleNext();
 *     }
 *   }
 *
 *   protected synchronized void scheduleNext() {
 *     if ((active = tasks.poll()) != null) {
 *       executor.execute(active);
 *     }
 *   }
 * }}</pre>
 *
 * The {@code Executor} implementations provided in this package
 * implement {@link ExecutorService}, which is a more extensive
 * interface.  The {@link ThreadPoolExecutor} class provides an
 * extensible thread pool implementation. The {@link Executors} class
 * provides convenient factory methods for these Executors.
 *
 * <p>Memory consistency effects: Actions in a thread prior to
 * submitting a {@code Runnable} object to an {@code Executor}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * its execution begins, perhaps in another thread.
 *
 * <p>
 *  执行提交的{@link Runnable}任务的对象。此接口提供了一种将任务提交与每个任务如何运行的机制分离的方法,包括线程使用,调度等细节。
 * 通常使用{@code Executor}而不是显式创建线程。例如,不是为一组任务中的每一个调用{@code new Thread(new(RunnableTask()))。
 * start()},您可以使用：。
 * 
 * <pre>
 *  Executor executor = <em> anExecutor </em>; executor.execute(new RunnableTask1()); executor.execute(n
 * ew RunnableTask2()); ... ...。
 * </pre>
 * 
 *  但是,{@code Executor}接口并不严格要求执行是异步的。在最简单的情况下,执行器可以立即在调用者的线程中运行提交的任务：
 * 
 *  <pre> {@code class DirectExecutor implements Executor {public void execute(Runnable r){r.run(); }}} 
 * </pre>。
 * 
 *  更典型地,任务在除了调用者的线程之外的一些线程中执行。下面的执行器为每个任务产生一个新的线程。
 * 
 *  <pre> {@code类ThreadPerTaskExecutor实现Executor {public void execute(Runnable r){new Thread(r).start(); }
 * }} </pre>。
 * 
 * 许多{@code Executor}实现对如何和何时调度任务施加了某种限制。执行器将提交的任务序列化到第二个执行器,说明复合执行器。
 * 
 * @since 1.5
 * @author Doug Lea
 */
public interface Executor {

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the {@code Executor} implementation.
     *
     * <p>
     * 
     *  <pre> {@code class SerialExecutor implements Executor {final Queue <Runnable> tasks = new ArrayDeque <Runnable>();最终执行者执行者; Runnable活动;。
     * 
     *  SerialExecutor(Executor executor){this.executor = executor; }}
     * 
     *  public synchronized void execute(final Runnable r){tasks.offer(new Runnable(){public void run(){try {r.run();}
     *  finally {scheduleNext();}}}); if(active == null){scheduleNext(); }}。
     * 
     *  保护同步无效scheduleNext(){if((active = tasks.poll())！= null){executor.execute(active); }}}} </pre>
     * 
     * 
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     * accepted for execution
     * @throws NullPointerException if command is null
     */
    void execute(Runnable command);
}
