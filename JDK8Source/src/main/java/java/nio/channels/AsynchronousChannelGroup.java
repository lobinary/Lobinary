/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.channels.spi.AsynchronousChannelProvider;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * A grouping of asynchronous channels for the purpose of resource sharing.
 *
 * <p> An asynchronous channel group encapsulates the mechanics required to
 * handle the completion of I/O operations initiated by {@link AsynchronousChannel
 * asynchronous channels} that are bound to the group. A group has an associated
 * thread pool to which tasks are submitted to handle I/O events and dispatch to
 * {@link CompletionHandler completion-handlers} that consume the result of
 * asynchronous operations performed on channels in the group. In addition to
 * handling I/O events, the pooled threads may also execute other tasks required
 * to support the execution of asynchronous I/O operations.
 *
 * <p> An asynchronous channel group is created by invoking the {@link
 * #withFixedThreadPool withFixedThreadPool} or {@link #withCachedThreadPool
 * withCachedThreadPool} methods defined here. Channels are bound to a group by
 * specifying the group when constructing the channel. The associated thread
 * pool is <em>owned</em> by the group; termination of the group results in the
 * shutdown of the associated thread pool.
 *
 * <p> In addition to groups created explicitly, the Java virtual machine
 * maintains a system-wide <em>default group</em> that is constructed
 * automatically. Asynchronous channels that do not specify a group at
 * construction time are bound to the default group. The default group has an
 * associated thread pool that creates new threads as needed. The default group
 * may be configured by means of system properties defined in the table below.
 * Where the {@link java.util.concurrent.ThreadFactory ThreadFactory} for the
 * default group is not configured then the pooled threads of the default group
 * are {@link Thread#isDaemon daemon} threads.
 *
 * <table border summary="System properties">
 *   <tr>
 *     <th>System property</th>
 *     <th>Description</th>
 *   </tr>
 *   <tr>
 *     <td> {@code java.nio.channels.DefaultThreadPool.threadFactory} </td>
 *     <td> The value of this property is taken to be the fully-qualified name
 *     of a concrete {@link java.util.concurrent.ThreadFactory ThreadFactory}
 *     class. The class is loaded using the system class loader and instantiated.
 *     The factory's {@link java.util.concurrent.ThreadFactory#newThread
 *     newThread} method is invoked to create each thread for the default
 *     group's thread pool. If the process to load and instantiate the value
 *     of the property fails then an unspecified error is thrown during the
 *     construction of the default group. </td>
 *   </tr>
 *   <tr>
 *     <td> {@code java.nio.channels.DefaultThreadPool.initialSize} </td>
 *     <td> The value of the {@code initialSize} parameter for the default
 *     group (see {@link #withCachedThreadPool withCachedThreadPool}).
 *     The value of the property is taken to be the {@code String}
 *     representation of an {@code Integer} that is the initial size parameter.
 *     If the value cannot be parsed as an {@code Integer} it causes an
 *     unspecified error to be thrown during the construction of the default
 *     group. </td>
 *   </tr>
 * </table>
 *
 * <a name="threading"></a><h2>Threading</h2>
 *
 * <p> The completion handler for an I/O operation initiated on a channel bound
 * to a group is guaranteed to be invoked by one of the pooled threads in the
 * group. This ensures that the completion handler is run by a thread with the
 * expected <em>identity</em>.
 *
 * <p> Where an I/O operation completes immediately, and the initiating thread
 * is one of the pooled threads in the group then the completion handler may
 * be invoked directly by the initiating thread. To avoid stack overflow, an
 * implementation may impose a limit as to the number of activations on the
 * thread stack. Some I/O operations may prohibit invoking the completion
 * handler directly by the initiating thread (see {@link
 * AsynchronousServerSocketChannel#accept(Object,CompletionHandler) accept}).
 *
 * <a name="shutdown"></a><h2>Shutdown and Termination</h2>
 *
 * <p> The {@link #shutdown() shutdown} method is used to initiate an <em>orderly
 * shutdown</em> of a group. An orderly shutdown marks the group as shutdown;
 * further attempts to construct a channel that binds to the group will throw
 * {@link ShutdownChannelGroupException}. Whether or not a group is shutdown can
 * be tested using the {@link #isShutdown() isShutdown} method. Once shutdown,
 * the group <em>terminates</em> when all asynchronous channels that are bound to
 * the group are closed, all actively executing completion handlers have run to
 * completion, and resources used by the group are released. No attempt is made
 * to stop or interrupt threads that are executing completion handlers. The
 * {@link #isTerminated() isTerminated} method is used to test if the group has
 * terminated, and the {@link #awaitTermination awaitTermination} method can be
 * used to block until the group has terminated.
 *
 * <p> The {@link #shutdownNow() shutdownNow} method can be used to initiate a
 * <em>forceful shutdown</em> of the group. In addition to the actions performed
 * by an orderly shutdown, the {@code shutdownNow} method closes all open channels
 * in the group as if by invoking the {@link AsynchronousChannel#close close}
 * method.
 *
 * <p>
 *  用于资源共享目的的一组异步通道。
 * 
 *  <p>异步通道组封装了处理完成由绑定到组的{@link异步通道异步通道}发起的I / O操作所需的机制。
 * 组具有关联的线程池,任务被提交到该线程池以处理I / O事件并分派到{@link CompletionHandler完成处理器},该消息使用对组中的信道执行的异步操作的结果。
 * 除了处理I / O事件之外,池化线程还可以执行支持异步I / O操作的执行所需的其它任务。
 * 
 *  <p>异步通道组是通过调用此处定义的{@link #withFixedThreadPool withFixedThreadPool}或{@link #withCachedThreadPool withCachedThreadPool}
 * 方法创建的。
 * 通道通过在构建通道时指定组而绑定到组。相关联的线程池由组拥有</em>;终止组导致关联的线程池的关闭。
 * 
 * <p>除了显式创建的组之外,Java虚拟机还维护一个系统级的自动构建的默认组</em>。在构建时未指定组的异步通道将绑定到默认组。默认组具有关联的线程池,根据需要创建新线程。
 * 默认组可以通过下表中定义的系统属性进行配置。
 * 如果未配置默认组的{@link java.util.concurrent.ThreadFactory ThreadFactory},则默认组的合并线程为{@link线程#isDaemon守护程序}线程。
 * 默认组可以通过下表中定义的系统属性进行配置。
 * 
 * <table border summary="System properties">
 * <tr>
 *  <th>系统属性</th> <th>描述</th>
 * </tr>
 * <tr>
 *  <td> {@code java.nio.channels.DefaultThreadPool.threadFactory} </td> <td>此属性的值被视为具体的完全限定名称{@link java.util.concurrent.ThreadFactory ThreadFactory}
 * 类。
 * 类使用系统类加载器加载并实例化。
 * 调用工厂的{@link java.util.concurrent.ThreadFactory#newThread newThread}方法来为默认组的线程池创建每个线程。
 * 如果加载和实例化属性的值的过程失败,则在构造默认组期间抛出未指定的错误。 </td>。
 * </tr>
 * <tr>
 * <td> {@code java.nio.channels.DefaultThreadPool.initialSize} </td> <td>默认组的{@code initialSize}参数的值(请参
 * 阅{@link #withCachedThreadPool withCachedThreadPool})。
 * 属性的值被视为初始大小参数{@code Integer}的{@code String}表示形式。如果该值不能被解析为{@code Integer},则会导致在构造默认组期间抛出未指定的错误。
 *  </td>。
 * </tr>
 * </table>
 * 
 *  <a name="threading"> </a> <h2>线程</h2>
 * 
 *  <p>在绑定到组的通道上启动的I / O操作的完成处理程序保证由组中的一个池线程调用。这确保完成处理程序由具有期望的<em>身份</em>的线程运行。
 * 
 *  <p>在I / O操作立即完成,并且启动线程是组中的合并线程之一时,完成处理程序可以直接由启动线程调用。为了避免堆栈溢出,实现可以对线程堆栈上的激活数量施加限制。
 * 一些I / O操作可能禁止由启动线程直接调用完成处理程序(参见{@link AsynchronousServerSocketChannel#accept(Object,CompletionHandler)accept}
 * )。
 *  <p>在I / O操作立即完成,并且启动线程是组中的合并线程之一时,完成处理程序可以直接由启动线程调用。为了避免堆栈溢出,实现可以对线程堆栈上的激活数量施加限制。
 * 
 *  <a name="shutdown"> </a> <h2>关闭和终止</h2>
 * 
 * <p> {@link #shutdown()shutdown}方法用于启动群组的<em>有序关闭</em>。
 * 有序关闭将组标记为关闭;进一步尝试构造绑定到组的通道将抛出{@link ShutdownChannelGroupException}。
 * 可以使用{@link #isShutdown()isShutdown}方法测试组是否关闭。
 * 一旦关闭,当与该组绑定的所有异步通道都关闭时,组<em>终止</em>,所有主动执行的完成处理程序已运行到完成,并且该组使用的资源被释放。不尝试停止或中断正在执行完成处理程序的线程。
 *  {@link #isTerminated()isTerminated}方法用于测试组是否已终止,并且{@link #awaitTermination awaitTermination}方法可用于阻止,
 * 直到组终止。
 * 一旦关闭,当与该组绑定的所有异步通道都关闭时,组<em>终止</em>,所有主动执行的完成处理程序已运行到完成,并且该组使用的资源被释放。不尝试停止或中断正在执行完成处理程序的线程。
 * 
 *  <p> {@link #shutdownNow()shutdownNow}方法可用于启动群组的<em>强制关闭</em>。
 * 除了有序关闭执行的操作之外,{@code shutdownNow}方法会关闭组中所有打开的通道,如同通过调用{@link AsynchronousChannel#close close}方法。
 * 
 * 
 * @since 1.7
 *
 * @see AsynchronousSocketChannel#open(AsynchronousChannelGroup)
 * @see AsynchronousServerSocketChannel#open(AsynchronousChannelGroup)
 */

public abstract class AsynchronousChannelGroup {
    private final AsynchronousChannelProvider provider;

    /**
     * Initialize a new instance of this class.
     *
     * <p>
     *  初始化此类的新实例。
     * 
     * 
     * @param   provider
     *          The asynchronous channel provider for this group
     */
    protected AsynchronousChannelGroup(AsynchronousChannelProvider provider) {
        this.provider = provider;
    }

    /**
     * Returns the provider that created this channel group.
     *
     * <p>
     *  返回创建此渠道组的提供商。
     * 
     * 
     * @return  The provider that created this channel group
     */
    public final AsynchronousChannelProvider provider() {
        return provider;
    }

    /**
     * Creates an asynchronous channel group with a fixed thread pool.
     *
     * <p> The resulting asynchronous channel group reuses a fixed number of
     * threads. At any point, at most {@code nThreads} threads will be active
     * processing tasks that are submitted to handle I/O events and dispatch
     * completion results for operations initiated on asynchronous channels in
     * the group.
     *
     * <p> The group is created by invoking the {@link
     * AsynchronousChannelProvider#openAsynchronousChannelGroup(int,ThreadFactory)
     * openAsynchronousChannelGroup(int,ThreadFactory)} method of the system-wide
     * default {@link AsynchronousChannelProvider} object.
     *
     * <p>
     *  创建具有固定线程池的异步通道组。
     * 
     * <p>生成的异步通道组重复使用固定数量的线程。在任何时候,最多{@code nThreads}线程将是提交以处理I / O事件的活动处理任务,以及用于在组中的异步通道上启动的操作的分派完成结果。
     * 
     *  <p>该组是通过调用系统级默认{@link AsynchronousChannelProvider}对象的{@link AsynchronousChannelProvider#openAsynchronousChannelGroup(int,ThreadFactory)openAsynchronousChannelGroup(int,ThreadFactory)}
     * 方法创建的。
     * 
     * 
     * @param   nThreads
     *          The number of threads in the pool
     * @param   threadFactory
     *          The factory to use when creating new threads
     *
     * @return  A new asynchronous channel group
     *
     * @throws  IllegalArgumentException
     *          If {@code nThreads <= 0}
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static AsynchronousChannelGroup withFixedThreadPool(int nThreads,
                                                               ThreadFactory threadFactory)
        throws IOException
    {
        return AsynchronousChannelProvider.provider()
            .openAsynchronousChannelGroup(nThreads, threadFactory);
    }

    /**
     * Creates an asynchronous channel group with a given thread pool that
     * creates new threads as needed.
     *
     * <p> The {@code executor} parameter is an {@code ExecutorService} that
     * creates new threads as needed to execute tasks that are submitted to
     * handle I/O events and dispatch completion results for operations initiated
     * on asynchronous channels in the group. It may reuse previously constructed
     * threads when they are available.
     *
     * <p> The {@code initialSize} parameter may be used by the implementation
     * as a <em>hint</em> as to the initial number of tasks it may submit. For
     * example, it may be used to indicate the initial number of threads that
     * wait on I/O events.
     *
     * <p> The executor is intended to be used exclusively by the resulting
     * asynchronous channel group. Termination of the group results in the
     * orderly  {@link ExecutorService#shutdown shutdown} of the executor
     * service. Shutting down the executor service by other means results in
     * unspecified behavior.
     *
     * <p> The group is created by invoking the {@link
     * AsynchronousChannelProvider#openAsynchronousChannelGroup(ExecutorService,int)
     * openAsynchronousChannelGroup(ExecutorService,int)} method of the system-wide
     * default {@link AsynchronousChannelProvider} object.
     *
     * <p>
     *  使用给定的线程池创建异步通道组,根据需要创建新线程。
     * 
     *  <@> {@code executor}参数是一个{@code ExecutorService},根据需要创建新线程,以执行提交以处理I / O事件的任务,以及针对在组中的异步通道上启动的操作的分派完
     * 成结果。
     * 当它们可用时,它可以重用先前构造的线程。
     * 
     *  <p> {@code initialSize}参数可以被实现用作它可以提交的任务的初始数量的<em>提示</em>。例如,它可以用于指示在I / O事件上等待的线程的初始数量。
     * 
     * <p>执行器旨在由所产生的异步通道组专门使用。组的终止导致执行器服务的有序{@link ExecutorService#shutdown shutdown}。
     * 通过其他方式关闭执行器服务会导致未指定的行为。
     * 
     *  <p>该组是通过调用系统级默认{@link AsynchronousChannelProvider}对象的{@link AsynchronousChannelProvider#openAsynchronousChannelGroup(ExecutorService,int)openAsynchronousChannelGroup(ExecutorService,int)}
     * 方法创建的。
     * 
     * 
     * @param   executor
     *          The thread pool for the resulting group
     * @param   initialSize
     *          A value {@code >=0} or a negative value for implementation
     *          specific default
     *
     * @return  A new asynchronous channel group
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @see java.util.concurrent.Executors#newCachedThreadPool
     */
    public static AsynchronousChannelGroup withCachedThreadPool(ExecutorService executor,
                                                                int initialSize)
        throws IOException
    {
        return AsynchronousChannelProvider.provider()
            .openAsynchronousChannelGroup(executor, initialSize);
    }

    /**
     * Creates an asynchronous channel group with a given thread pool.
     *
     * <p> The {@code executor} parameter is an {@code ExecutorService} that
     * executes tasks submitted to dispatch completion results for operations
     * initiated on asynchronous channels in the group.
     *
     * <p> Care should be taken when configuring the executor service. It
     * should support <em>direct handoff</em> or <em>unbounded queuing</em> of
     * submitted tasks, and the thread that invokes the {@link
     * ExecutorService#execute execute} method should never invoke the task
     * directly. An implementation may mandate additional constraints.
     *
     * <p> The executor is intended to be used exclusively by the resulting
     * asynchronous channel group. Termination of the group results in the
     * orderly  {@link ExecutorService#shutdown shutdown} of the executor
     * service. Shutting down the executor service by other means results in
     * unspecified behavior.
     *
     * <p> The group is created by invoking the {@link
     * AsynchronousChannelProvider#openAsynchronousChannelGroup(ExecutorService,int)
     * openAsynchronousChannelGroup(ExecutorService,int)} method of the system-wide
     * default {@link AsynchronousChannelProvider} object with an {@code
     * initialSize} of {@code 0}.
     *
     * <p>
     *  使用给定的线程池创建异步通道组。
     * 
     *  <p> {@code executor}参数是一个{@code ExecutorService},执行提交到派遣完成结果的任务,以便在组中的异步通道上启动操作。
     * 
     *  <p>在配置执行程序服务时应小心。
     * 它应该支持所提交任务的<em>直接切换</em>或<em>无界队列</em>,并且调用{@link ExecutorService#execute execute}方法的线程不应直接调用任务。
     * 实现可以要求额外的约束。
     * 
     *  <p>执行器旨在由所产生的异步通道组专门使用。组的终止导致执行器服务的有序{@link ExecutorService#shutdown shutdown}。
     * 通过其他方式关闭执行器服务会导致未指定的行为。
     * 
     * <p>通过调用系统级默认{@link AsynchronousChannelProvider}对象的{@link AsynchronousChannelProvider#openAsynchronousChannelGroup(ExecutorService,int)openAsynchronousChannelGroup(ExecutorService,int)}
     * 方法创建该组,{@code initialSize} @code 0}。
     * 
     * 
     * @param   executor
     *          The thread pool for the resulting group
     *
     * @return  A new asynchronous channel group
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static AsynchronousChannelGroup withThreadPool(ExecutorService executor)
        throws IOException
    {
        return AsynchronousChannelProvider.provider()
            .openAsynchronousChannelGroup(executor, 0);
    }

    /**
     * Tells whether or not this asynchronous channel group is shutdown.
     *
     * <p>
     *  告诉这个异步通道组是否关闭。
     * 
     * 
     * @return  {@code true} if this asynchronous channel group is shutdown or
     *          has been marked for shutdown.
     */
    public abstract boolean isShutdown();

    /**
     * Tells whether or not this group has terminated.
     *
     * <p> Where this method returns {@code true}, then the associated thread
     * pool has also {@link ExecutorService#isTerminated terminated}.
     *
     * <p>
     *  指出此群组是否已终止。
     * 
     *  <p>如果此方法返回{@code true},则相关联的线程池也会{@link ExecutorService#isTerminated terminated}。
     * 
     * 
     * @return  {@code true} if this group has terminated
     */
    public abstract boolean isTerminated();

    /**
     * Initiates an orderly shutdown of the group.
     *
     * <p> This method marks the group as shutdown. Further attempts to construct
     * channel that binds to this group will throw {@link ShutdownChannelGroupException}.
     * The group terminates when all asynchronous channels in the group are
     * closed, all actively executing completion handlers have run to completion,
     * and all resources have been released. This method has no effect if the
     * group is already shutdown.
     * <p>
     *  启动组的有序关闭。
     * 
     *  <p>此方法将组标记为关闭。进一步尝试构造绑定到此组的通道将抛出{@link ShutdownChannelGroupException}。
     * 当组中的所有异步通道都关闭时,组终止,所有主动执行的完成处理程序已运行完成,并且所有资源已释放。如果组已经关闭,此方法不起作用。
     * 
     */
    public abstract void shutdown();

    /**
     * Shuts down the group and closes all open channels in the group.
     *
     * <p> In addition to the actions performed by the {@link #shutdown() shutdown}
     * method, this method invokes the {@link AsynchronousChannel#close close}
     * method on all open channels in the group. This method does not attempt to
     * stop or interrupt threads that are executing completion handlers. The
     * group terminates when all actively executing completion handlers have run
     * to completion and all resources have been released. This method may be
     * invoked at any time. If some other thread has already invoked it, then
     * another invocation will block until the first invocation is complete,
     * after which it will return without effect.
     *
     * <p>
     *  关闭组并关闭组中所有打开的通道。
     * 
     * <p>除了{@link #shutdown()shutdown}方法执行的操作之外,此方法在组中所有打开的通道上调用{@link AsynchronousChannel#close close}方法。
     * 此方法不会尝试停止或中断正在执行完成处理程序的线程。当所有正在执行的完成处理程序运行完成并且所有资源已释放时,组终止。此方法可以在任何时候调用。
     * 如果一些其他线程已经调用它,则另一个调用将阻塞,直到第一次调用完成,之后它将返回没有效果。
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract void shutdownNow() throws IOException;

    /**
     * Awaits termination of the group.

     * <p> This method blocks until the group has terminated, or the timeout
     * occurs, or the current thread is interrupted, whichever happens first.
     *
     * <p>
     * 
     * @param   timeout
     *          The maximum time to wait, or zero or less to not wait
     * @param   unit
     *          The time unit of the timeout argument
     *
     * @return  {@code true} if the group has terminated; {@code false} if the
     *          timeout elapsed before termination
     *
     * @throws  InterruptedException
     *          If interrupted while waiting
     */
    public abstract boolean awaitTermination(long timeout, TimeUnit unit)
        throws InterruptedException;
}
