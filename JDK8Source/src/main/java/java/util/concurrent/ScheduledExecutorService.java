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
 * An {@link ExecutorService} that can schedule commands to run after a given
 * delay, or to execute periodically.
 *
 * <p>The {@code schedule} methods create tasks with various delays
 * and return a task object that can be used to cancel or check
 * execution. The {@code scheduleAtFixedRate} and
 * {@code scheduleWithFixedDelay} methods create and execute tasks
 * that run periodically until cancelled.
 *
 * <p>Commands submitted using the {@link Executor#execute(Runnable)}
 * and {@link ExecutorService} {@code submit} methods are scheduled
 * with a requested delay of zero. Zero and negative delays (but not
 * periods) are also allowed in {@code schedule} methods, and are
 * treated as requests for immediate execution.
 *
 * <p>All {@code schedule} methods accept <em>relative</em> delays and
 * periods as arguments, not absolute times or dates. It is a simple
 * matter to transform an absolute time represented as a {@link
 * java.util.Date} to the required form. For example, to schedule at
 * a certain future {@code date}, you can use: {@code schedule(task,
 * date.getTime() - System.currentTimeMillis(),
 * TimeUnit.MILLISECONDS)}. Beware however that expiration of a
 * relative delay need not coincide with the current {@code Date} at
 * which the task is enabled due to network time synchronization
 * protocols, clock drift, or other factors.
 *
 * <p>The {@link Executors} class provides convenient factory methods for
 * the ScheduledExecutorService implementations provided in this package.
 *
 * <h3>Usage Example</h3>
 *
 * Here is a class with a method that sets up a ScheduledExecutorService
 * to beep every ten seconds for an hour:
 *
 *  <pre> {@code
 * import static java.util.concurrent.TimeUnit.*;
 * class BeeperControl {
 *   private final ScheduledExecutorService scheduler =
 *     Executors.newScheduledThreadPool(1);
 *
 *   public void beepForAnHour() {
 *     final Runnable beeper = new Runnable() {
 *       public void run() { System.out.println("beep"); }
 *     };
 *     final ScheduledFuture<?> beeperHandle =
 *       scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
 *     scheduler.schedule(new Runnable() {
 *       public void run() { beeperHandle.cancel(true); }
 *     }, 60 * 60, SECONDS);
 *   }
 * }}</pre>
 *
 * <p>
 *  {@link ExecutorService},可以调度在给定延迟后运行的命令,或定期执行。
 * 
 *  <p> {@code schedule}方法创建具有各种延迟的任务,并返回可用于取消或检查执行的任务对象。
 *  {@code scheduleAtFixedRate}和{@code scheduleWithFixedDelay}方法创建并执行定期运行的任务,直到取消。
 * 
 *  <p>使用{@link Executor#execute(Runnable)}和{@link ExecutorService} {@code submit}方法提交的命令的调度请求延迟为零。
 * 在{@code schedule}方法中也允许零和负延迟(但不是句点),并被视为立即执行的请求。
 * 
 * <p>所有{@code schedule}方法都接受相对延迟和周期作为参数,而不是绝对时间或日期。将表示为{@link java.util.Date}的绝对时间转换为所需的表单很简单。
 * 例如,要在某个未来{@code date}计划,您可以使用：{@code schedule(task,date.getTime() -  System.currentTimeMillis(),TimeUnit.MILLISECONDS)}
 * 。
 * <p>所有{@code schedule}方法都接受相对延迟和周期作为参数,而不是绝对时间或日期。将表示为{@link java.util.Date}的绝对时间转换为所需的表单很简单。
 * 然而,请注意,由于网络时间同步协议,时钟漂移或其他因素,相对延迟的到期不需要与当前{@code Date}一致,在该当前{@code Date}启用任务。
 * 
 *  <p> {@link Executors}类为此程序包中提供的ScheduledExecutorService实现提供了方便的工厂方法。
 * 
 *  <h3>使用示例</h3>
 * 
 *  这里是一个类的方法,设置一个ScheduledExecutorService嘟声每十秒钟一小时：
 * 
 *  <pre> {@code import static java.util.concurrent.TimeUnit。
 * *; class BeeperControl {private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);。
 * 
 * @since 1.5
 * @author Doug Lea
 */
public interface ScheduledExecutorService extends ExecutorService {

    /**
     * Creates and executes a one-shot action that becomes enabled
     * after the given delay.
     *
     * <p>
     *  <pre> {@code import static java.util.concurrent.TimeUnit。
     * 
     *  public void beepForAnHour(){final Runnable beeper = new Runnable(){public void run(){System.out.println("beep"); }
     * };最后ScheduledFuture <?> beeperHandle = scheduler.scheduleAtFixedRate(beeper,10,10,SECONDS); scheduler
     * .schedule(new Runnable(){public void run(){beeperHandle.cancel(true);}},60 * 60,SECONDS); }}} </pre>。
     * 
     * 
     * @param command the task to execute
     * @param delay the time from now to delay execution
     * @param unit the time unit of the delay parameter
     * @return a ScheduledFuture representing pending completion of
     *         the task and whose {@code get()} method will return
     *         {@code null} upon completion
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if command is null
     */
    public ScheduledFuture<?> schedule(Runnable command,
                                       long delay, TimeUnit unit);

    /**
     * Creates and executes a ScheduledFuture that becomes enabled after the
     * given delay.
     *
     * <p>
     *  创建并执行在给定延迟后启用的单次操作。
     * 
     * 
     * @param callable the function to execute
     * @param delay the time from now to delay execution
     * @param unit the time unit of the delay parameter
     * @param <V> the type of the callable's result
     * @return a ScheduledFuture that can be used to extract result or cancel
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if callable is null
     */
    public <V> ScheduledFuture<V> schedule(Callable<V> callable,
                                           long delay, TimeUnit unit);

    /**
     * Creates and executes a periodic action that becomes enabled first
     * after the given initial delay, and subsequently with the given
     * period; that is executions will commence after
     * {@code initialDelay} then {@code initialDelay+period}, then
     * {@code initialDelay + 2 * period}, and so on.
     * If any execution of the task
     * encounters an exception, subsequent executions are suppressed.
     * Otherwise, the task will only terminate via cancellation or
     * termination of the executor.  If any execution of this task
     * takes longer than its period, then subsequent executions
     * may start late, but will not concurrently execute.
     *
     * <p>
     * 创建并执行在给定延迟后启用的ScheduledFuture。
     * 
     * 
     * @param command the task to execute
     * @param initialDelay the time to delay first execution
     * @param period the period between successive executions
     * @param unit the time unit of the initialDelay and period parameters
     * @return a ScheduledFuture representing pending completion of
     *         the task, and whose {@code get()} method will throw an
     *         exception upon cancellation
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if command is null
     * @throws IllegalArgumentException if period less than or equal to zero
     */
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                  long initialDelay,
                                                  long period,
                                                  TimeUnit unit);

    /**
     * Creates and executes a periodic action that becomes enabled first
     * after the given initial delay, and subsequently with the
     * given delay between the termination of one execution and the
     * commencement of the next.  If any execution of the task
     * encounters an exception, subsequent executions are suppressed.
     * Otherwise, the task will only terminate via cancellation or
     * termination of the executor.
     *
     * <p>
     *  创建并执行周期性动作,该周期性动作在给定的初始延迟之后,随后在给定周期内首先被使能;即{@code initialDelay},然后是{@code initialDelay + period},然后是
     * {@code initialDelay + 2 * period},依此类推。
     * 如果任务的任何执行遇到异常,则后续执行被抑制。否则,任务将仅通过取消或终止执行程序而终止。如果此任务的任何执行花费比其周期更长的时间,则后续执行可能开始较晚,但不会并发执行。
     * 
     * 
     * @param command the task to execute
     * @param initialDelay the time to delay first execution
     * @param delay the delay between the termination of one
     * execution and the commencement of the next
     * @param unit the time unit of the initialDelay and delay parameters
     * @return a ScheduledFuture representing pending completion of
     *         the task, and whose {@code get()} method will throw an
     *         exception upon cancellation
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if command is null
     * @throws IllegalArgumentException if delay less than or equal to zero
     */
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                     long initialDelay,
                                                     long delay,
                                                     TimeUnit unit);

}
