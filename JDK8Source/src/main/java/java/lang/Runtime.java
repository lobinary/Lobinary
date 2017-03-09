/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

import java.io.*;
import java.util.StringTokenizer;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * Every Java application has a single instance of class
 * <code>Runtime</code> that allows the application to interface with
 * the environment in which the application is running. The current
 * runtime can be obtained from the <code>getRuntime</code> method.
 * <p>
 * An application cannot create its own instance of this class.
 *
 * <p>
 *  每个Java应用程序都有一个<code> Runtime </code>类的实例,它允许应用程序与运行应用程序的环境进行交互。
 * 当前运行时可以从<code> getRuntime </code>方法中获取。
 * <p>
 *  应用程序无法创建自己的此类的实例。
 * 
 * 
 * @author  unascribed
 * @see     java.lang.Runtime#getRuntime()
 * @since   JDK1.0
 */

public class Runtime {
    private static Runtime currentRuntime = new Runtime();

    /**
     * Returns the runtime object associated with the current Java application.
     * Most of the methods of class <code>Runtime</code> are instance
     * methods and must be invoked with respect to the current runtime object.
     *
     * <p>
     *  返回与当前Java应用程序关联的运行时对象。类<code> Runtime </code>的大多数方法是实例方法,必须针对当前运行时对象调用。
     * 
     * 
     * @return  the <code>Runtime</code> object associated with the current
     *          Java application.
     */
    public static Runtime getRuntime() {
        return currentRuntime;
    }

    /** Don't let anyone else instantiate this class */
    private Runtime() {}

    /**
     * Terminates the currently running Java virtual machine by initiating its
     * shutdown sequence.  This method never returns normally.  The argument
     * serves as a status code; by convention, a nonzero status code indicates
     * abnormal termination.
     *
     * <p> The virtual machine's shutdown sequence consists of two phases.  In
     * the first phase all registered {@link #addShutdownHook shutdown hooks},
     * if any, are started in some unspecified order and allowed to run
     * concurrently until they finish.  In the second phase all uninvoked
     * finalizers are run if {@link #runFinalizersOnExit finalization-on-exit}
     * has been enabled.  Once this is done the virtual machine {@link #halt
     * halts}.
     *
     * <p> If this method is invoked after the virtual machine has begun its
     * shutdown sequence then if shutdown hooks are being run this method will
     * block indefinitely.  If shutdown hooks have already been run and on-exit
     * finalization has been enabled then this method halts the virtual machine
     * with the given status code if the status is nonzero; otherwise, it
     * blocks indefinitely.
     *
     * <p> The <tt>{@link System#exit(int) System.exit}</tt> method is the
     * conventional and convenient means of invoking this method. <p>
     *
     * <p>
     *  通过启动其关闭序列来终止当前运行的Java虚拟机。此方法不会正常返回。参数作为状态码;按照惯例,非零状态码表示异常终止。
     * 
     *  <p>虚拟机的关闭序列由两个阶段组成。在第一阶段,所有注册的{@link #addShutdownHook关闭挂钩}(如果有的话)以一些未指定的顺序启动,并允许并发运行直到它们完成。
     * 在第二阶段,如果已启用{@link #runFinalizersOnExit finalization-on-exit},则将运行所有未引用的终结器。
     * 一旦这完成虚拟机{@link #halt halts}。
     * 
     * <p>如果在虚拟机已经开始其关闭序列之后调用此方法,则如果正在运行关闭挂接,则此方法将无限期地阻止。
     * 如果已经运行关闭挂接,并且启用了退出终止,则如果状态为非零,则此方法将使用给定的状态代码暂停虚拟机;否则,它会无限期阻塞。
     * 
     *  <p> <tt> {@ link System#exit(int)System.exit} </tt>方法是调用此方法的常规和方便的方法。 <p>
     * 
     * 
     * @param  status
     *         Termination status.  By convention, a nonzero status code
     *         indicates abnormal termination.
     *
     * @throws SecurityException
     *         If a security manager is present and its <tt>{@link
     *         SecurityManager#checkExit checkExit}</tt> method does not permit
     *         exiting with the specified status
     *
     * @see java.lang.SecurityException
     * @see java.lang.SecurityManager#checkExit(int)
     * @see #addShutdownHook
     * @see #removeShutdownHook
     * @see #runFinalizersOnExit
     * @see #halt(int)
     */
    public void exit(int status) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkExit(status);
        }
        Shutdown.exit(status);
    }

    /**
     * Registers a new virtual-machine shutdown hook.
     *
     * <p> The Java virtual machine <i>shuts down</i> in response to two kinds
     * of events:
     *
     *   <ul>
     *
     *   <li> The program <i>exits</i> normally, when the last non-daemon
     *   thread exits or when the <tt>{@link #exit exit}</tt> (equivalently,
     *   {@link System#exit(int) System.exit}) method is invoked, or
     *
     *   <li> The virtual machine is <i>terminated</i> in response to a
     *   user interrupt, such as typing <tt>^C</tt>, or a system-wide event,
     *   such as user logoff or system shutdown.
     *
     *   </ul>
     *
     * <p> A <i>shutdown hook</i> is simply an initialized but unstarted
     * thread.  When the virtual machine begins its shutdown sequence it will
     * start all registered shutdown hooks in some unspecified order and let
     * them run concurrently.  When all the hooks have finished it will then
     * run all uninvoked finalizers if finalization-on-exit has been enabled.
     * Finally, the virtual machine will halt.  Note that daemon threads will
     * continue to run during the shutdown sequence, as will non-daemon threads
     * if shutdown was initiated by invoking the <tt>{@link #exit exit}</tt>
     * method.
     *
     * <p> Once the shutdown sequence has begun it can be stopped only by
     * invoking the <tt>{@link #halt halt}</tt> method, which forcibly
     * terminates the virtual machine.
     *
     * <p> Once the shutdown sequence has begun it is impossible to register a
     * new shutdown hook or de-register a previously-registered hook.
     * Attempting either of these operations will cause an
     * <tt>{@link IllegalStateException}</tt> to be thrown.
     *
     * <p> Shutdown hooks run at a delicate time in the life cycle of a virtual
     * machine and should therefore be coded defensively.  They should, in
     * particular, be written to be thread-safe and to avoid deadlocks insofar
     * as possible.  They should also not rely blindly upon services that may
     * have registered their own shutdown hooks and therefore may themselves in
     * the process of shutting down.  Attempts to use other thread-based
     * services such as the AWT event-dispatch thread, for example, may lead to
     * deadlocks.
     *
     * <p> Shutdown hooks should also finish their work quickly.  When a
     * program invokes <tt>{@link #exit exit}</tt> the expectation is
     * that the virtual machine will promptly shut down and exit.  When the
     * virtual machine is terminated due to user logoff or system shutdown the
     * underlying operating system may only allow a fixed amount of time in
     * which to shut down and exit.  It is therefore inadvisable to attempt any
     * user interaction or to perform a long-running computation in a shutdown
     * hook.
     *
     * <p> Uncaught exceptions are handled in shutdown hooks just as in any
     * other thread, by invoking the <tt>{@link ThreadGroup#uncaughtException
     * uncaughtException}</tt> method of the thread's <tt>{@link
     * ThreadGroup}</tt> object.  The default implementation of this method
     * prints the exception's stack trace to <tt>{@link System#err}</tt> and
     * terminates the thread; it does not cause the virtual machine to exit or
     * halt.
     *
     * <p> In rare circumstances the virtual machine may <i>abort</i>, that is,
     * stop running without shutting down cleanly.  This occurs when the
     * virtual machine is terminated externally, for example with the
     * <tt>SIGKILL</tt> signal on Unix or the <tt>TerminateProcess</tt> call on
     * Microsoft Windows.  The virtual machine may also abort if a native
     * method goes awry by, for example, corrupting internal data structures or
     * attempting to access nonexistent memory.  If the virtual machine aborts
     * then no guarantee can be made about whether or not any shutdown hooks
     * will be run. <p>
     *
     * <p>
     *  注册新的虚拟机关闭挂接。
     * 
     *  <p> Java虚拟机<i>关闭</i>以响应两种事件：
     * 
     * <ul>
     * 
     *  <li>程序<i>正常退出</i>,当最后一个非守护线程退出或<tt> {@ link #exit exit} </tt>(等同地,{@link System#exit int)System.exit}
     * )方法被调用。
     * 
     *  <li>响应用户中断(如键入<tt> ^ C </tt>)或系统范围的事件(例如用户注销或系统关闭),虚拟机会终止</i>。
     * 
     * </ul>
     * 
     * <p> <i>关闭挂钩</i>只是一个初始化但未启动的线程。当虚拟机开始其关闭序列时,它将以一些未指定的顺序开始所有注册的关闭挂接,并让它们并发运行。
     * 当所有的挂钩完成后,如果启用了退出结束,它将运行所有未被调用的终结器。最后,虚拟机将停止。
     * 注意,守护线程将在关闭序列期间继续运行,非守护线程将通过调用<tt> {@ link #exit exit} </tt>方法启动关闭。
     * 
     *  <p>一旦关闭序列开始,它只能通过调用<tt> {@ link #halt halt} </tt>方法停止,该方法强制终止虚拟机。
     * 
     *  <p>一旦关闭序列开始,就不可能注册新的关闭挂钩或取消注册先前注册的挂钩。尝试这些操作之一将导致抛出<tt> {@ link IllegalStateException} </tt>。
     * 
     *  <p>关闭挂钩在虚拟机生命周期中的微妙时刻运行,因此应该进行防御编码。特别是,它们应该写成线程安全的并且尽可能地避免死锁。
     * 他们也不应该盲目地依赖于可能注册了自己的关闭挂钩的服务,因此可能自己在关闭的过程中。尝试使用其他基于线程的服务(例如AWT事件分派线程)可能导致死锁。
     * 
     * <p>关闭钩子也应该快速完成他们的工作。当程序调用<tt> {@ link #exit exit} </tt>时,期望虚拟机将立即关闭并退出。
     * 当虚拟机由于用户注销或系统关闭而终止时,底层操作系统可能只允许固定的时间量来关闭和退出。因此,不宜尝试任何用户交互或在关闭挂接中执行长时间运行的计算。
     * 
     *  <p>通过调用线程的<tt> {@ link ThreadGroup}的<tt> {@ link ThreadGroup#uncaughtException uncaughtException} </tt>
     * 方法,可以在关闭挂钩中处理未捕获的异常</tt >对象。
     * 该方法的默认实现将异常的堆栈跟踪打印到<tt> {@ link System#err} </tt>并终止线程;它不会导致虚拟机退出或停止。
     * 
     *  <p>在极少数情况下,虚拟机可能会中止</i>,即停止运行而不会彻底关闭。
     * 当虚拟机在外部终止时,例如在Unix上的<tt> SIGKILL </tt>信号或在Microsoft Windows上的<tt> TerminateProcess </tt>调用时,会出现这种情况。
     * 如果本地方法通过例如破坏内部数据结构或试图访问不存在的存储器,虚拟机也可以中止。如果虚拟机中止,则不能保证是否将运行任何关闭挂接。 <p>。
     * 
     * 
     * @param   hook
     *          An initialized but unstarted <tt>{@link Thread}</tt> object
     *
     * @throws  IllegalArgumentException
     *          If the specified hook has already been registered,
     *          or if it can be determined that the hook is already running or
     *          has already been run
     *
     * @throws  IllegalStateException
     *          If the virtual machine is already in the process
     *          of shutting down
     *
     * @throws  SecurityException
     *          If a security manager is present and it denies
     *          <tt>{@link RuntimePermission}("shutdownHooks")</tt>
     *
     * @see #removeShutdownHook
     * @see #halt(int)
     * @see #exit(int)
     * @since 1.3
     */
    public void addShutdownHook(Thread hook) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("shutdownHooks"));
        }
        ApplicationShutdownHooks.add(hook);
    }

    /**
     * De-registers a previously-registered virtual-machine shutdown hook. <p>
     *
     * <p>
     * 取消注册先前注册的虚拟机关闭挂接。 <p>
     * 
     * 
     * @param hook the hook to remove
     * @return <tt>true</tt> if the specified hook had previously been
     * registered and was successfully de-registered, <tt>false</tt>
     * otherwise.
     *
     * @throws  IllegalStateException
     *          If the virtual machine is already in the process of shutting
     *          down
     *
     * @throws  SecurityException
     *          If a security manager is present and it denies
     *          <tt>{@link RuntimePermission}("shutdownHooks")</tt>
     *
     * @see #addShutdownHook
     * @see #exit(int)
     * @since 1.3
     */
    public boolean removeShutdownHook(Thread hook) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("shutdownHooks"));
        }
        return ApplicationShutdownHooks.remove(hook);
    }

    /**
     * Forcibly terminates the currently running Java virtual machine.  This
     * method never returns normally.
     *
     * <p> This method should be used with extreme caution.  Unlike the
     * <tt>{@link #exit exit}</tt> method, this method does not cause shutdown
     * hooks to be started and does not run uninvoked finalizers if
     * finalization-on-exit has been enabled.  If the shutdown sequence has
     * already been initiated then this method does not wait for any running
     * shutdown hooks or finalizers to finish their work. <p>
     *
     * <p>
     *  强制终止当前正在运行的Java虚拟机。此方法不会正常返回。
     * 
     *  <p>此方法应极度小心使用。与<tt> {@ link #exit exit} </tt>方法不同,此方法不会导致关闭挂接被启动,并且如果启用了结束化退出,则不会运行未被引用的终结器。
     * 如果关闭序列已经被启动,则该方法不等待任何正在运行的关闭挂钩或终止器完成它们的工作。 <p>。
     * 
     * 
     * @param  status
     *         Termination status.  By convention, a nonzero status code
     *         indicates abnormal termination.  If the <tt>{@link Runtime#exit
     *         exit}</tt> (equivalently, <tt>{@link System#exit(int)
     *         System.exit}</tt>) method has already been invoked then this
     *         status code will override the status code passed to that method.
     *
     * @throws SecurityException
     *         If a security manager is present and its <tt>{@link
     *         SecurityManager#checkExit checkExit}</tt> method does not permit
     *         an exit with the specified status
     *
     * @see #exit
     * @see #addShutdownHook
     * @see #removeShutdownHook
     * @since 1.3
     */
    public void halt(int status) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkExit(status);
        }
        Shutdown.halt(status);
    }

    /**
     * Enable or disable finalization on exit; doing so specifies that the
     * finalizers of all objects that have finalizers that have not yet been
     * automatically invoked are to be run before the Java runtime exits.
     * By default, finalization on exit is disabled.
     *
     * <p>If there is a security manager,
     * its <code>checkExit</code> method is first called
     * with 0 as its argument to ensure the exit is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  在退出时启用或禁用终结;这样做指定具有尚未被自动调用的finalizer的所有对象的finalizer将在Java运行时退出之前运行。默认情况下,禁用在退出时完成。
     * 
     *  <p>如果有安全管理器,则首先调用其<code> checkExit </code>方法,其中的参数为0,以确保允许退出。这可能导致SecurityException。
     * 
     * 
     * @param value true to enable finalization on exit, false to disable
     * @deprecated  This method is inherently unsafe.  It may result in
     *      finalizers being called on live objects while other threads are
     *      concurrently manipulating those objects, resulting in erratic
     *      behavior or deadlock.
     *
     * @throws  SecurityException
     *        if a security manager exists and its <code>checkExit</code>
     *        method doesn't allow the exit.
     *
     * @see     java.lang.Runtime#exit(int)
     * @see     java.lang.Runtime#gc()
     * @see     java.lang.SecurityManager#checkExit(int)
     * @since   JDK1.1
     */
    @Deprecated
    public static void runFinalizersOnExit(boolean value) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            try {
                security.checkExit(0);
            } catch (SecurityException e) {
                throw new SecurityException("runFinalizersOnExit");
            }
        }
        Shutdown.setRunFinalizersOnExit(value);
    }

    /**
     * Executes the specified string command in a separate process.
     *
     * <p>This is a convenience method.  An invocation of the form
     * <tt>exec(command)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>{@link #exec(String, String[], File) exec}(command, null, null)</tt>.
     *
     * <p>
     *  在单独的进程中执行指定的字符串命令。
     * 
     *  <p>这是一个方便的方法。
     * 调用形式<tt> exec(command)</tt>的行为与调用<tt>完全相同{@ link #exec(String,String [],File)exec}(command,null,null 
     * )</tt>。
     *  <p>这是一个方便的方法。
     * 
     * 
     * @param   command   a specified system command.
     *
     * @return  A new {@link Process} object for managing the subprocess
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          {@link SecurityManager#checkExec checkExec}
     *          method doesn't allow creation of the subprocess
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  NullPointerException
     *          If <code>command</code> is <code>null</code>
     *
     * @throws  IllegalArgumentException
     *          If <code>command</code> is empty
     *
     * @see     #exec(String[], String[], File)
     * @see     ProcessBuilder
     */
    public Process exec(String command) throws IOException {
        return exec(command, null, null);
    }

    /**
     * Executes the specified string command in a separate process with the
     * specified environment.
     *
     * <p>This is a convenience method.  An invocation of the form
     * <tt>exec(command, envp)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>{@link #exec(String, String[], File) exec}(command, envp, null)</tt>.
     *
     * <p>
     *  在与指定环境不同的进程中执行指定的字符串命令。
     * 
     * <p>这是一个方便的方法。
     * 调用形式<tt> exec(command,envp)</tt>的行为与调用<tt>完全相同{@ link #exec(String,String [],File)exec}(command,envp 
     * ,null)</tt>。
     * <p>这是一个方便的方法。
     * 
     * 
     * @param   command   a specified system command.
     *
     * @param   envp      array of strings, each element of which
     *                    has environment variable settings in the format
     *                    <i>name</i>=<i>value</i>, or
     *                    <tt>null</tt> if the subprocess should inherit
     *                    the environment of the current process.
     *
     * @return  A new {@link Process} object for managing the subprocess
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          {@link SecurityManager#checkExec checkExec}
     *          method doesn't allow creation of the subprocess
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  NullPointerException
     *          If <code>command</code> is <code>null</code>,
     *          or one of the elements of <code>envp</code> is <code>null</code>
     *
     * @throws  IllegalArgumentException
     *          If <code>command</code> is empty
     *
     * @see     #exec(String[], String[], File)
     * @see     ProcessBuilder
     */
    public Process exec(String command, String[] envp) throws IOException {
        return exec(command, envp, null);
    }

    /**
     * Executes the specified string command in a separate process with the
     * specified environment and working directory.
     *
     * <p>This is a convenience method.  An invocation of the form
     * <tt>exec(command, envp, dir)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>{@link #exec(String[], String[], File) exec}(cmdarray, envp, dir)</tt>,
     * where <code>cmdarray</code> is an array of all the tokens in
     * <code>command</code>.
     *
     * <p>More precisely, the <code>command</code> string is broken
     * into tokens using a {@link StringTokenizer} created by the call
     * <code>new {@link StringTokenizer}(command)</code> with no
     * further modification of the character categories.  The tokens
     * produced by the tokenizer are then placed in the new string
     * array <code>cmdarray</code>, in the same order.
     *
     * <p>
     *  在具有指定环境和工作目录的单独进程中执行指定的字符串命令。
     * 
     *  <p>这是一个方便的方法。
     * 调用形式<tt> exec(command,envp,dir)</tt>的行为与调用<tt> {@ link #exec(String [],String [],File)exec} (cmdarray
     * ,envp,dir)</tt>,其中<code> cmdarray </code>是<code> command </code>中所有标记的数组。
     *  <p>这是一个方便的方法。
     * 
     *  <p>更精确地说,<code>命令</code>字符串使用通过调用<code> new {@link StringTokenizer}(command)</code>创建的{@link StringTokenizer}
     * 修改字符类别。
     * 然后,令牌生成器生成的令牌以相同的顺序放置在新的字符串数组<code> cmdarray </code>中。
     * 
     * 
     * @param   command   a specified system command.
     *
     * @param   envp      array of strings, each element of which
     *                    has environment variable settings in the format
     *                    <i>name</i>=<i>value</i>, or
     *                    <tt>null</tt> if the subprocess should inherit
     *                    the environment of the current process.
     *
     * @param   dir       the working directory of the subprocess, or
     *                    <tt>null</tt> if the subprocess should inherit
     *                    the working directory of the current process.
     *
     * @return  A new {@link Process} object for managing the subprocess
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          {@link SecurityManager#checkExec checkExec}
     *          method doesn't allow creation of the subprocess
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  NullPointerException
     *          If <code>command</code> is <code>null</code>,
     *          or one of the elements of <code>envp</code> is <code>null</code>
     *
     * @throws  IllegalArgumentException
     *          If <code>command</code> is empty
     *
     * @see     ProcessBuilder
     * @since 1.3
     */
    public Process exec(String command, String[] envp, File dir)
        throws IOException {
        if (command.length() == 0)
            throw new IllegalArgumentException("Empty command");

        StringTokenizer st = new StringTokenizer(command);
        String[] cmdarray = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); i++)
            cmdarray[i] = st.nextToken();
        return exec(cmdarray, envp, dir);
    }

    /**
     * Executes the specified command and arguments in a separate process.
     *
     * <p>This is a convenience method.  An invocation of the form
     * <tt>exec(cmdarray)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>{@link #exec(String[], String[], File) exec}(cmdarray, null, null)</tt>.
     *
     * <p>
     *  在单独的进程中执行指定的命令和参数。
     * 
     *  <p>这是一个方便的方法。
     * 调用形式<tt> exec(cmdarray)</tt>的行为与调用<tt>完全相同{@ link #exec(String [],String [],File)exec}(cmdarray,null 
     * ,null)</tt>。
     *  <p>这是一个方便的方法。
     * 
     * 
     * @param   cmdarray  array containing the command to call and
     *                    its arguments.
     *
     * @return  A new {@link Process} object for managing the subprocess
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          {@link SecurityManager#checkExec checkExec}
     *          method doesn't allow creation of the subprocess
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  NullPointerException
     *          If <code>cmdarray</code> is <code>null</code>,
     *          or one of the elements of <code>cmdarray</code> is <code>null</code>
     *
     * @throws  IndexOutOfBoundsException
     *          If <code>cmdarray</code> is an empty array
     *          (has length <code>0</code>)
     *
     * @see     ProcessBuilder
     */
    public Process exec(String cmdarray[]) throws IOException {
        return exec(cmdarray, null, null);
    }

    /**
     * Executes the specified command and arguments in a separate process
     * with the specified environment.
     *
     * <p>This is a convenience method.  An invocation of the form
     * <tt>exec(cmdarray, envp)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>{@link #exec(String[], String[], File) exec}(cmdarray, envp, null)</tt>.
     *
     * <p>
     *  在与指定环境不同的进程中执行指定的命令和参数。
     * 
     * <p>这是一个方便的方法。
     * 调用形式<tt> exec(cmdarray,envp)</tt>的行为与调用<tt>完全相同{@ link #exec(String [],String [],File)exec}(cmdarray 
     * ,envp,null)</tt>。
     * <p>这是一个方便的方法。
     * 
     * 
     * @param   cmdarray  array containing the command to call and
     *                    its arguments.
     *
     * @param   envp      array of strings, each element of which
     *                    has environment variable settings in the format
     *                    <i>name</i>=<i>value</i>, or
     *                    <tt>null</tt> if the subprocess should inherit
     *                    the environment of the current process.
     *
     * @return  A new {@link Process} object for managing the subprocess
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          {@link SecurityManager#checkExec checkExec}
     *          method doesn't allow creation of the subprocess
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  NullPointerException
     *          If <code>cmdarray</code> is <code>null</code>,
     *          or one of the elements of <code>cmdarray</code> is <code>null</code>,
     *          or one of the elements of <code>envp</code> is <code>null</code>
     *
     * @throws  IndexOutOfBoundsException
     *          If <code>cmdarray</code> is an empty array
     *          (has length <code>0</code>)
     *
     * @see     ProcessBuilder
     */
    public Process exec(String[] cmdarray, String[] envp) throws IOException {
        return exec(cmdarray, envp, null);
    }


    /**
     * Executes the specified command and arguments in a separate process with
     * the specified environment and working directory.
     *
     * <p>Given an array of strings <code>cmdarray</code>, representing the
     * tokens of a command line, and an array of strings <code>envp</code>,
     * representing "environment" variable settings, this method creates
     * a new process in which to execute the specified command.
     *
     * <p>This method checks that <code>cmdarray</code> is a valid operating
     * system command.  Which commands are valid is system-dependent,
     * but at the very least the command must be a non-empty list of
     * non-null strings.
     *
     * <p>If <tt>envp</tt> is <tt>null</tt>, the subprocess inherits the
     * environment settings of the current process.
     *
     * <p>A minimal set of system dependent environment variables may
     * be required to start a process on some operating systems.
     * As a result, the subprocess may inherit additional environment variable
     * settings beyond those in the specified environment.
     *
     * <p>{@link ProcessBuilder#start()} is now the preferred way to
     * start a process with a modified environment.
     *
     * <p>The working directory of the new subprocess is specified by <tt>dir</tt>.
     * If <tt>dir</tt> is <tt>null</tt>, the subprocess inherits the
     * current working directory of the current process.
     *
     * <p>If a security manager exists, its
     * {@link SecurityManager#checkExec checkExec}
     * method is invoked with the first component of the array
     * <code>cmdarray</code> as its argument. This may result in a
     * {@link SecurityException} being thrown.
     *
     * <p>Starting an operating system process is highly system-dependent.
     * Among the many things that can go wrong are:
     * <ul>
     * <li>The operating system program file was not found.
     * <li>Access to the program file was denied.
     * <li>The working directory does not exist.
     * </ul>
     *
     * <p>In such cases an exception will be thrown.  The exact nature
     * of the exception is system-dependent, but it will always be a
     * subclass of {@link IOException}.
     *
     *
     * <p>
     *  在指定的环境和工作目录的单独进程中执行指定的命令和参数。
     * 
     *  <p>给定一个表示命令行令牌的字符串<code> cmdarray </code>和表示"环境"变量设置的字符串数组<code> envp </code>,此方法创建一个新进程在其中执行指定的命令。
     * 
     *  <p>此方法检查<code> cmdarray </code>是否为有效的操作系统命令。哪些命令有效是依赖于系统的,但至少命令必须是非空字符串的非空列表。
     * 
     *  <p>如果<tt> envp </tt>是<tt> null </tt>,则子流程继承当前进程的环境设置。
     * 
     *  <p>在某些操作系统上启动进程可能需要一组最小的系统相关环境变量。因此,子过程可能继承超出指定环境中的其他环境变量设置。
     * 
     *  <p> {@ link ProcessBuilder#start()}现在是使用修改后的环境启动进程的首选方式。
     * 
     *  <p>新子流程的工作目录由<tt> dir </tt>指定。如果<tt> dir </tt>是<tt> null </tt>,则子流程继承当前进程的当前工作目录。
     * 
     * <p>如果存在安全管理器,则会使用数组<code> cmdarray </code>的第一个组件作为其参数来调用其{@link SecurityManager#checkExec checkExec}方
     * 法。
     * 这可能会导致{@link SecurityException}被抛出。
     * 
     *  <p>启动操作系统进程高度依赖于系统。在许多事情中可能出问题是：
     * <ul>
     *  <li>找不到操作系统程序文件。 <li>拒绝访问程序文件。 <li>工作目录不存在。
     * </ul>
     * 
     *  <p>在这种情况下,会抛出异常。异常的确切性质是系统依赖的,但它总是{@link IOException}的子类。
     * 
     * 
     * @param   cmdarray  array containing the command to call and
     *                    its arguments.
     *
     * @param   envp      array of strings, each element of which
     *                    has environment variable settings in the format
     *                    <i>name</i>=<i>value</i>, or
     *                    <tt>null</tt> if the subprocess should inherit
     *                    the environment of the current process.
     *
     * @param   dir       the working directory of the subprocess, or
     *                    <tt>null</tt> if the subprocess should inherit
     *                    the working directory of the current process.
     *
     * @return  A new {@link Process} object for managing the subprocess
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          {@link SecurityManager#checkExec checkExec}
     *          method doesn't allow creation of the subprocess
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  NullPointerException
     *          If <code>cmdarray</code> is <code>null</code>,
     *          or one of the elements of <code>cmdarray</code> is <code>null</code>,
     *          or one of the elements of <code>envp</code> is <code>null</code>
     *
     * @throws  IndexOutOfBoundsException
     *          If <code>cmdarray</code> is an empty array
     *          (has length <code>0</code>)
     *
     * @see     ProcessBuilder
     * @since 1.3
     */
    public Process exec(String[] cmdarray, String[] envp, File dir)
        throws IOException {
        return new ProcessBuilder(cmdarray)
            .environment(envp)
            .directory(dir)
            .start();
    }

    /**
     * Returns the number of processors available to the Java virtual machine.
     *
     * <p> This value may change during a particular invocation of the virtual
     * machine.  Applications that are sensitive to the number of available
     * processors should therefore occasionally poll this property and adjust
     * their resource usage appropriately. </p>
     *
     * <p>
     *  返回Java虚拟机可用的处理器数。
     * 
     *  <p>此值可能会在虚拟机的特定调用期间更改。因此,对可用处理器数量敏感的应用程序应偶尔轮询此属性并适当调整其资源使用情况。 </p>
     * 
     * 
     * @return  the maximum number of processors available to the virtual
     *          machine; never smaller than one
     * @since 1.4
     */
    public native int availableProcessors();

    /**
     * Returns the amount of free memory in the Java Virtual Machine.
     * Calling the
     * <code>gc</code> method may result in increasing the value returned
     * by <code>freeMemory.</code>
     *
     * <p>
     *  返回Java虚拟机中的可用内存量。调用<code> gc </code>方法可能会增加<code> freeMemory返回的值。</code>
     * 
     * 
     * @return  an approximation to the total amount of memory currently
     *          available for future allocated objects, measured in bytes.
     */
    public native long freeMemory();

    /**
     * Returns the total amount of memory in the Java virtual machine.
     * The value returned by this method may vary over time, depending on
     * the host environment.
     * <p>
     * Note that the amount of memory required to hold an object of any
     * given type may be implementation-dependent.
     *
     * <p>
     *  返回Java虚拟机中的内存总量。此方法返回的值可能会随时间变化,具体取决于主机环境。
     * <p>
     *  注意,保存任何给定类型的对象所需的存储器的量可以是实现相关的。
     * 
     * 
     * @return  the total amount of memory currently available for current
     *          and future objects, measured in bytes.
     */
    public native long totalMemory();

    /**
     * Returns the maximum amount of memory that the Java virtual machine will
     * attempt to use.  If there is no inherent limit then the value {@link
     * java.lang.Long#MAX_VALUE} will be returned.
     *
     * <p>
     * 返回Java虚拟机尝试使用的最大内存量。如果没有固有限制,则将返回值{@link java.lang.Long#MAX_VALUE}。
     * 
     * 
     * @return  the maximum amount of memory that the virtual machine will
     *          attempt to use, measured in bytes
     * @since 1.4
     */
    public native long maxMemory();

    /**
     * Runs the garbage collector.
     * Calling this method suggests that the Java virtual machine expend
     * effort toward recycling unused objects in order to make the memory
     * they currently occupy available for quick reuse. When control
     * returns from the method call, the virtual machine has made
     * its best effort to recycle all discarded objects.
     * <p>
     * The name <code>gc</code> stands for "garbage
     * collector". The virtual machine performs this recycling
     * process automatically as needed, in a separate thread, even if the
     * <code>gc</code> method is not invoked explicitly.
     * <p>
     * The method {@link System#gc()} is the conventional and convenient
     * means of invoking this method.
     * <p>
     *  运行垃圾收集器。调用此方法表明Java虚拟机花费了回收未使用的对象的努力,以使它们当前所占用的存储器可用于快速重用。当控制从方法调用返回时,虚拟机已尽最大努力回收所有丢弃的对象。
     * <p>
     *  名称<code> gc </code>代表"垃圾回收器"。虚拟机在单独的线程中根据需要自动执行此回收过程,即使未明确调用<code> gc </code>方法。
     * <p>
     *  方法{@link System#gc()}是调用此方法的常规和方便的方法。
     * 
     */
    public native void gc();

    /* Wormhole for calling java.lang.ref.Finalizer.runFinalization */
    private static native void runFinalization0();

    /**
     * Runs the finalization methods of any objects pending finalization.
     * Calling this method suggests that the Java virtual machine expend
     * effort toward running the <code>finalize</code> methods of objects
     * that have been found to be discarded but whose <code>finalize</code>
     * methods have not yet been run. When control returns from the
     * method call, the virtual machine has made a best effort to
     * complete all outstanding finalizations.
     * <p>
     * The virtual machine performs the finalization process
     * automatically as needed, in a separate thread, if the
     * <code>runFinalization</code> method is not invoked explicitly.
     * <p>
     * The method {@link System#runFinalization()} is the conventional
     * and convenient means of invoking this method.
     *
     * <p>
     *  运行任何对象的finalization方法,等待最终确定。
     * 调用此方法表明Java虚拟机花费了努力来运行已被发现被丢弃但其<code> finalize </code>方法尚未运行的对象的<code> finalize </code>方法。
     * 当控制从方法调用返回时,虚拟机已尽最大努力完成所有未完成的最终化。
     * <p>
     *  如果未明确调用<code> runFinalization </code>方法,则虚拟机在单独的线程中根据需要自动执行完成过程。
     * <p>
     * 方法{@link System#runFinalization()}是调用此方法的常规和方便的方法。
     * 
     * 
     * @see     java.lang.Object#finalize()
     */
    public void runFinalization() {
        runFinalization0();
    }

    /**
     * Enables/Disables tracing of instructions.
     * If the <code>boolean</code> argument is <code>true</code>, this
     * method suggests that the Java virtual machine emit debugging
     * information for each instruction in the virtual machine as it
     * is executed. The format of this information, and the file or other
     * output stream to which it is emitted, depends on the host environment.
     * The virtual machine may ignore this request if it does not support
     * this feature. The destination of the trace output is system
     * dependent.
     * <p>
     * If the <code>boolean</code> argument is <code>false</code>, this
     * method causes the virtual machine to stop performing the
     * detailed instruction trace it is performing.
     *
     * <p>
     *  启用/禁用指令跟踪。如果<code> boolean </code>参数是<code> true </code>,则此方法建议Java虚拟机在虚拟机执行时为每个指令发出调试信息。
     * 此信息的格式以及发出此信息的文件或其他输出流取决于主机环境。如果虚拟机不支持此功能,则可以忽略此请求。跟踪输出的目的地是系统相关的。
     * <p>
     *  如果<code> boolean </code>参数为<code> false </code>,则此方法会导致虚拟机停止执行其执行的详细指令跟踪。
     * 
     * 
     * @param   on   <code>true</code> to enable instruction tracing;
     *               <code>false</code> to disable this feature.
     */
    public native void traceInstructions(boolean on);

    /**
     * Enables/Disables tracing of method calls.
     * If the <code>boolean</code> argument is <code>true</code>, this
     * method suggests that the Java virtual machine emit debugging
     * information for each method in the virtual machine as it is
     * called. The format of this information, and the file or other output
     * stream to which it is emitted, depends on the host environment. The
     * virtual machine may ignore this request if it does not support
     * this feature.
     * <p>
     * Calling this method with argument false suggests that the
     * virtual machine cease emitting per-call debugging information.
     *
     * <p>
     *  启用/禁用方法调用的跟踪。如果<code> boolean </code>参数是<code> true </code>,则此方法建议Java虚拟机在调用虚拟机时为每个方法发出调试信息。
     * 此信息的格式以及发出此信息的文件或其他输出流取决于主机环境。如果虚拟机不支持此功能,则可以忽略此请求。
     * <p>
     *  使用参数false调用此方法意味着虚拟机停止发出每个调用的调试信息。
     * 
     * 
     * @param   on   <code>true</code> to enable instruction tracing;
     *               <code>false</code> to disable this feature.
     */
    public native void traceMethodCalls(boolean on);

    /**
     * Loads the native library specified by the filename argument.  The filename
     * argument must be an absolute path name.
     * (for example
     * <code>Runtime.getRuntime().load("/home/avh/lib/libX11.so");</code>).
     *
     * If the filename argument, when stripped of any platform-specific library
     * prefix, path, and file extension, indicates a library whose name is,
     * for example, L, and a native library called L is statically linked
     * with the VM, then the JNI_OnLoad_L function exported by the library
     * is invoked rather than attempting to load a dynamic library.
     * A filename matching the argument does not have to exist in the file
     * system. See the JNI Specification for more details.
     *
     * Otherwise, the filename argument is mapped to a native library image in
     * an implementation-dependent manner.
     * <p>
     * First, if there is a security manager, its <code>checkLink</code>
     * method is called with the <code>filename</code> as its argument.
     * This may result in a security exception.
     * <p>
     * This is similar to the method {@link #loadLibrary(String)}, but it
     * accepts a general file name as an argument rather than just a library
     * name, allowing any file of native code to be loaded.
     * <p>
     * The method {@link System#load(String)} is the conventional and
     * convenient means of invoking this method.
     *
     * <p>
     * 加载由filename参数指定的本机库。 filename参数必须是绝对路径名。 (例如<code> Runtime.getRuntime()。
     * load("/ home / avh / lib / libX11.so"); </code>)。
     * 
     *  如果文件名参数,当剥离任何特定于平台的库前缀,路径和文件扩展名时,指示其名称为例如L的库,并且称为L的本地库与VM静态链接,则JNI_OnLoad_L函数由库导出的调用而不是尝试加载动态库。
     * 与参数匹配的文件名不必存在于文件系统中。有关更多详细信息,请参阅JNI规范。
     * 
     *  否则,filename参数将以实现相关的方式映射到本机库映像。
     * <p>
     *  首先,如果有安全管理器,则以<code> filename </code>作为其参数调用其<code> checkLink </code>方法。这可能导致安全异常。
     * <p>
     *  这与{@link #loadLibrary(String)}方法类似,但它接受一个通用文件名作为参数,而不仅仅是一个库名,允许加载任何本地代码文件。
     * <p>
     *  方法{@link System#load(String)}是调用此方法的常规和方便的方法。
     * 
     * 
     * @param      filename   the file to load.
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkLink</code> method doesn't allow
     *             loading of the specified dynamic library
     * @exception  UnsatisfiedLinkError  if either the filename is not an
     *             absolute path name, the native library is not statically
     *             linked with the VM, or the library cannot be mapped to
     *             a native library image by the host system.
     * @exception  NullPointerException if <code>filename</code> is
     *             <code>null</code>
     * @see        java.lang.Runtime#getRuntime()
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkLink(java.lang.String)
     */
    @CallerSensitive
    public void load(String filename) {
        load0(Reflection.getCallerClass(), filename);
    }

    synchronized void load0(Class<?> fromClass, String filename) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkLink(filename);
        }
        if (!(new File(filename).isAbsolute())) {
            throw new UnsatisfiedLinkError(
                "Expecting an absolute path of the library: " + filename);
        }
        ClassLoader.loadLibrary(fromClass, filename, true);
    }

    /**
     * Loads the native library specified by the <code>libname</code>
     * argument.  The <code>libname</code> argument must not contain any platform
     * specific prefix, file extension or path. If a native library
     * called <code>libname</code> is statically linked with the VM, then the
     * JNI_OnLoad_<code>libname</code> function exported by the library is invoked.
     * See the JNI Specification for more details.
     *
     * Otherwise, the libname argument is loaded from a system library
     * location and mapped to a native library image in an implementation-
     * dependent manner.
     * <p>
     * First, if there is a security manager, its <code>checkLink</code>
     * method is called with the <code>libname</code> as its argument.
     * This may result in a security exception.
     * <p>
     * The method {@link System#loadLibrary(String)} is the conventional
     * and convenient means of invoking this method. If native
     * methods are to be used in the implementation of a class, a standard
     * strategy is to put the native code in a library file (call it
     * <code>LibFile</code>) and then to put a static initializer:
     * <blockquote><pre>
     * static { System.loadLibrary("LibFile"); }
     * </pre></blockquote>
     * within the class declaration. When the class is loaded and
     * initialized, the necessary native code implementation for the native
     * methods will then be loaded as well.
     * <p>
     * If this method is called more than once with the same library
     * name, the second and subsequent calls are ignored.
     *
     * <p>
     * 加载由<code> libname </code>参数指定的本机库。 <code> libname </code>参数不能包含任何平台特定的前缀,文件扩展名或路径。
     * 如果称为<code> libname </code>的本机库与VM静态链接,则调用由库导出的JNI_OnLoad_ <code> libname </code>函数。
     * 有关更多详细信息,请参阅JNI规范。
     * 
     *  否则,libname参数从系统库位置加载,并以实现相关的方式映射到本机库映像。
     * <p>
     *  首先,如果有安全管理器,则以<code> libname </code>作为其参数调用其<code> checkLink </code>方法。这可能导致安全异常。
     * <p>
     *  方法{@link System#loadLibrary(String)}是调用此方法的常规和方便的方法。
     * 如果本机方法要在类的实现中使用,标准的策略是将本地代码放在库文件中(调用<code> LibFile </code>),然后放置一个静态初始化器：<blockquote> <pre> static {System.loadLibrary("LibFile"); }
     *  </pre> </blockquote>。
     *  方法{@link System#loadLibrary(String)}是调用此方法的常规和方便的方法。当类被加载和初始化时,本地方法的必要的本地代码实现也将被加载。
     * <p>
     *  如果使用相同的库名称多次调用此方法,则忽略第二个和后续调用。
     * 
     * 
     * @param      libname   the name of the library.
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkLink</code> method doesn't allow
     *             loading of the specified dynamic library
     * @exception  UnsatisfiedLinkError if either the libname argument
     *             contains a file path, the native library is not statically
     *             linked with the VM,  or the library cannot be mapped to a
     *             native library image by the host system.
     * @exception  NullPointerException if <code>libname</code> is
     *             <code>null</code>
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkLink(java.lang.String)
     */
    @CallerSensitive
    public void loadLibrary(String libname) {
        loadLibrary0(Reflection.getCallerClass(), libname);
    }

    synchronized void loadLibrary0(Class<?> fromClass, String libname) {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkLink(libname);
        }
        if (libname.indexOf((int)File.separatorChar) != -1) {
            throw new UnsatisfiedLinkError(
    "Directory separator should not appear in library name: " + libname);
        }
        ClassLoader.loadLibrary(fromClass, libname, false);
    }

    /**
     * Creates a localized version of an input stream. This method takes
     * an <code>InputStream</code> and returns an <code>InputStream</code>
     * equivalent to the argument in all respects except that it is
     * localized: as characters in the local character set are read from
     * the stream, they are automatically converted from the local
     * character set to Unicode.
     * <p>
     * If the argument is already a localized stream, it may be returned
     * as the result.
     *
     * <p>
     * 创建输入流的本地化版本。
     * 这个方法接受一个<code> InputStream </code>并且返回一个等价于参数的<code> InputStream </code>,除了它被本地化：当从流中读取本地字符集中的字符时,会自动
     * 从本地字符集转换为Unicode。
     * 创建输入流的本地化版本。
     * <p>
     *  如果参数已经是本地化流,则可以作为结果返回。
     * 
     * 
     * @param      in InputStream to localize
     * @return     a localized input stream
     * @see        java.io.InputStream
     * @see        java.io.BufferedReader#BufferedReader(java.io.Reader)
     * @see        java.io.InputStreamReader#InputStreamReader(java.io.InputStream)
     * @deprecated As of JDK&nbsp;1.1, the preferred way to translate a byte
     * stream in the local encoding into a character stream in Unicode is via
     * the <code>InputStreamReader</code> and <code>BufferedReader</code>
     * classes.
     */
    @Deprecated
    public InputStream getLocalizedInputStream(InputStream in) {
        return in;
    }

    /**
     * Creates a localized version of an output stream. This method
     * takes an <code>OutputStream</code> and returns an
     * <code>OutputStream</code> equivalent to the argument in all respects
     * except that it is localized: as Unicode characters are written to
     * the stream, they are automatically converted to the local
     * character set.
     * <p>
     * If the argument is already a localized stream, it may be returned
     * as the result.
     *
     * <p>
     *  创建输出流的本地化版本。
     * 这个方法需要一个<code> OutputStream </code>并且返回一个等价于参数的<code> OutputStream </code>,除了它被本地化以外：当Unicode字符被写入流时,
     * 它们被自动转换为本地字符集。
     *  创建输出流的本地化版本。
     * 
     * @deprecated As of JDK&nbsp;1.1, the preferred way to translate a
     * Unicode character stream into a byte stream in the local encoding is via
     * the <code>OutputStreamWriter</code>, <code>BufferedWriter</code>, and
     * <code>PrintWriter</code> classes.
     *
     * @param      out OutputStream to localize
     * @return     a localized output stream
     * @see        java.io.OutputStream
     * @see        java.io.BufferedWriter#BufferedWriter(java.io.Writer)
     * @see        java.io.OutputStreamWriter#OutputStreamWriter(java.io.OutputStream)
     * @see        java.io.PrintWriter#PrintWriter(java.io.OutputStream)
     */
    @Deprecated
    public OutputStream getLocalizedOutputStream(OutputStream out) {
        return out;
    }

}
