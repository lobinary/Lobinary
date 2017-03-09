/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.LockSupport;
import sun.nio.ch.Interruptible;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.security.util.SecurityConstants;


/**
 * A <i>thread</i> is a thread of execution in a program. The Java
 * Virtual Machine allows an application to have multiple threads of
 * execution running concurrently.
 * <p>
 * Every thread has a priority. Threads with higher priority are
 * executed in preference to threads with lower priority. Each thread
 * may or may not also be marked as a daemon. When code running in
 * some thread creates a new <code>Thread</code> object, the new
 * thread has its priority initially set equal to the priority of the
 * creating thread, and is a daemon thread if and only if the
 * creating thread is a daemon.
 * <p>
 * When a Java Virtual Machine starts up, there is usually a single
 * non-daemon thread (which typically calls the method named
 * <code>main</code> of some designated class). The Java Virtual
 * Machine continues to execute threads until either of the following
 * occurs:
 * <ul>
 * <li>The <code>exit</code> method of class <code>Runtime</code> has been
 *     called and the security manager has permitted the exit operation
 *     to take place.
 * <li>All threads that are not daemon threads have died, either by
 *     returning from the call to the <code>run</code> method or by
 *     throwing an exception that propagates beyond the <code>run</code>
 *     method.
 * </ul>
 * <p>
 * There are two ways to create a new thread of execution. One is to
 * declare a class to be a subclass of <code>Thread</code>. This
 * subclass should override the <code>run</code> method of class
 * <code>Thread</code>. An instance of the subclass can then be
 * allocated and started. For example, a thread that computes primes
 * larger than a stated value could be written as follows:
 * <hr><blockquote><pre>
 *     class PrimeThread extends Thread {
 *         long minPrime;
 *         PrimeThread(long minPrime) {
 *             this.minPrime = minPrime;
 *         }
 *
 *         public void run() {
 *             // compute primes larger than minPrime
 *             &nbsp;.&nbsp;.&nbsp;.
 *         }
 *     }
 * </pre></blockquote><hr>
 * <p>
 * The following code would then create a thread and start it running:
 * <blockquote><pre>
 *     PrimeThread p = new PrimeThread(143);
 *     p.start();
 * </pre></blockquote>
 * <p>
 * The other way to create a thread is to declare a class that
 * implements the <code>Runnable</code> interface. That class then
 * implements the <code>run</code> method. An instance of the class can
 * then be allocated, passed as an argument when creating
 * <code>Thread</code>, and started. The same example in this other
 * style looks like the following:
 * <hr><blockquote><pre>
 *     class PrimeRun implements Runnable {
 *         long minPrime;
 *         PrimeRun(long minPrime) {
 *             this.minPrime = minPrime;
 *         }
 *
 *         public void run() {
 *             // compute primes larger than minPrime
 *             &nbsp;.&nbsp;.&nbsp;.
 *         }
 *     }
 * </pre></blockquote><hr>
 * <p>
 * The following code would then create a thread and start it running:
 * <blockquote><pre>
 *     PrimeRun p = new PrimeRun(143);
 *     new Thread(p).start();
 * </pre></blockquote>
 * <p>
 * Every thread has a name for identification purposes. More than
 * one thread may have the same name. If a name is not specified when
 * a thread is created, a new name is generated for it.
 * <p>
 * Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method in this class will cause a {@link NullPointerException} to be
 * thrown.
 *
 * <p>
 *  <i>线程</i>是程序中的执行线程。 Java虚拟机允许应用程序具有并发运行的多个执行线程。
 * <p>
 *  每个线程都有一个优先级。具有较高优先级的线程优先于具有较低优先级的线程执行。每个线程可以或也可以不被标记为守护进程。
 * 当在一些线程中运行的代码创建一个新的<code> Thread </code>对象时,新线程的优先级最初设置为等于创建线程的优先级,并且当且仅当创建线程为守护进程。
 * <p>
 *  当Java虚拟机启动时,通常有一个非守护线程(通常调用某个指定类的<code> main </code>方法)。 Java虚拟机继续执行线程,直到发生以下任一情况：
 * <ul>
 *  <li>已调用<code> Runtime </code>类的<code> exit </code>方法,并且安全管理器已允许进行退出操作。
 *  <li>不是守护线程的所有线程都已经死亡,通过从调用返回到<code> run </code>方法或抛出超出<code> run </code>方法的异常。
 * </ul>
 * <p>
 * 有两种方法来创建新的执行线程。一个是将一个类声明为<code> Thread </code>的子类。
 * 这个子类应该覆盖<code> Thread </code>类的<code> run </code>方法。然后可以分配和启动子类的实例。
 * 例如,计算大于规定值的素数的线程可以写成如下：<hr> <blockquote> <pre> class PrimeThread extends Thread {long minPrime; PrimeThread(long minPrime){this.minPrime = minPrime; }
 * }。
 * 这个子类应该覆盖<code> Thread </code>类的<code> run </code>方法。然后可以分配和启动子类的实例。
 * 
 *  public void run(){//计算大于minPrime的素数&nbsp;。&nbsp;。&nbsp; }} </pre> </blockquote> <hr>
 * <p>
 *  下面的代码将创建一个线程并开始运行：<blockquote> <pre> PrimeThread p = new PrimeThread(143); p.start(); </pre> </blockquote>
 * 。
 * <p>
 *  创建线程的另一种方法是声明一个实现<code> Runnable </code>接口的类。然后该类实现<code> run </code>方法。
 * 然后可以分配类的实例,在创建<code> Thread </code>时作为参数传递,并启动。
 * 这个其他样式中的相同示例如下所示：<hr> <blockquote> <pre> class PrimeRun implements Runnable {long minPrime; PrimeRun(long minPrime){this.minPrime = minPrime; }
 * }。
 * 然后可以分配类的实例,在创建<code> Thread </code>时作为参数传递,并启动。
 * 
 *  public void run(){//计算大于minPrime的素数&nbsp;。&nbsp;。&nbsp; }} </pre> </blockquote> <hr>
 * <p>
 * 下面的代码将创建一个线程并开始运行：<blockquote> <pre> PrimeRun p = new PrimeRun(143);新线程(p).start(); </pre> </blockquote>
 * 。
 * <p>
 *  每个线程都有一个名称用于识别目的。多个线程可以具有相同的名称。如果在创建线程时未指定名称,则会为其生成新名称。
 * <p>
 *  除非另有说明,否则将{@code null}参数传递给此类中的构造函数或方法将导致抛出{@link NullPointerException}。
 * 
 * 
 * @author  unascribed
 * @see     Runnable
 * @see     Runtime#exit(int)
 * @see     #run()
 * @see     #stop()
 * @since   JDK1.0
 */
public
class Thread implements Runnable {
    /* Make sure registerNatives is the first thing <clinit> does. */
    private static native void registerNatives();
    static {
        registerNatives();
    }

    private volatile char  name[];
    private int            priority;
    private Thread         threadQ;
    private long           eetop;

    /* Whether or not to single_step this thread. */
    private boolean     single_step;

    /* Whether or not the thread is a daemon thread. */
    private boolean     daemon = false;

    /* JVM state */
    private boolean     stillborn = false;

    /* What will be run. */
    private Runnable target;

    /* The group of this thread */
    private ThreadGroup group;

    /* The context ClassLoader for this thread */
    private ClassLoader contextClassLoader;

    /* The inherited AccessControlContext of this thread */
    private AccessControlContext inheritedAccessControlContext;

    /* For autonumbering anonymous threads. */
    private static int threadInitNumber;
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }

    /* ThreadLocal values pertaining to this thread. This map is maintained
    /* <p>
    /* 
     * by the ThreadLocal class. */
    ThreadLocal.ThreadLocalMap threadLocals = null;

    /*
     * InheritableThreadLocal values pertaining to this thread. This map is
     * maintained by the InheritableThreadLocal class.
     * <p>
     *  与此线程有关的InheritableThreadLocal值。此映射由InheritableThreadLocal类维护。
     * 
     */
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;

    /*
     * The requested stack size for this thread, or 0 if the creator did
     * not specify a stack size.  It is up to the VM to do whatever it
     * likes with this number; some VMs will ignore it.
     * <p>
     *  此线程请求的堆栈大小,如果创建者未指定堆栈大小,则为0。它取决于虚拟机做任何它喜欢与这个数字;一些VM将忽略它。
     * 
     */
    private long stackSize;

    /*
     * JVM-private state that persists after native thread termination.
     * <p>
     *  JVM私有状态,在本地线程终止后仍然存在。
     * 
     */
    private long nativeParkEventPointer;

    /*
     * Thread ID
     * <p>
     *  线程ID
     * 
     */
    private long tid;

    /* For generating thread ID */
    private static long threadSeqNumber;

    /* Java thread status for tools,
     * initialized to indicate thread 'not yet started'
     * <p>
     *  初始化以指示线程"尚未开始"
     * 
     */

    private volatile int threadStatus = 0;


    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }

    /**
     * The argument supplied to the current call to
     * java.util.concurrent.locks.LockSupport.park.
     * Set by (private) java.util.concurrent.locks.LockSupport.setBlocker
     * Accessed using java.util.concurrent.locks.LockSupport.getBlocker
     * <p>
     *  参数提供给当前调用java.util.concurrent.locks.LockSupport.park。
     * 设置者(私人)java.util.concurrent.locks.LockSupport.setBlocker使用java.util.concurrent.locks.LockSupport.getB
     * locker访问。
     *  参数提供给当前调用java.util.concurrent.locks.LockSupport.park。
     * 
     */
    volatile Object parkBlocker;

    /* The object in which this thread is blocked in an interruptible I/O
     * operation, if any.  The blocker's interrupt method should be invoked
     * after setting this thread's interrupt status.
     * <p>
     *  操作,如果有的话。在设置该线程的中断状态后,应该调用阻塞程序的中断方法。
     * 
     */
    private volatile Interruptible blocker;
    private final Object blockerLock = new Object();

    /* Set the blocker field; invoked via sun.misc.SharedSecrets from java.nio code
    /* <p>
     */
    void blockedOn(Interruptible b) {
        synchronized (blockerLock) {
            blocker = b;
        }
    }

    /**
     * The minimum priority that a thread can have.
     * <p>
     *  线程可以拥有的最小优先级。
     * 
     */
    public final static int MIN_PRIORITY = 1;

   /**
     * The default priority that is assigned to a thread.
     * <p>
     *  分配给线程的默认优先级。
     * 
     */
    public final static int NORM_PRIORITY = 5;

    /**
     * The maximum priority that a thread can have.
     * <p>
     *  线程可以拥有的最大优先级。
     * 
     */
    public final static int MAX_PRIORITY = 10;

    /**
     * Returns a reference to the currently executing thread object.
     *
     * <p>
     *  返回对当前正在执行的线程对象的引用。
     * 
     * 
     * @return  the currently executing thread.
     */
    public static native Thread currentThread();

    /**
     * A hint to the scheduler that the current thread is willing to yield
     * its current use of a processor. The scheduler is free to ignore this
     * hint.
     *
     * <p> Yield is a heuristic attempt to improve relative progression
     * between threads that would otherwise over-utilise a CPU. Its use
     * should be combined with detailed profiling and benchmarking to
     * ensure that it actually has the desired effect.
     *
     * <p> It is rarely appropriate to use this method. It may be useful
     * for debugging or testing purposes, where it may help to reproduce
     * bugs due to race conditions. It may also be useful when designing
     * concurrency control constructs such as the ones in the
     * {@link java.util.concurrent.locks} package.
     * <p>
     * 对调度程序提示当前线程愿意产生其当前使用的处理器。调度程序可以随意忽略此提示。
     * 
     *  <p> Yield是一种启发式尝试,用于提高线程之间的相对进度,否则会过度利用CPU。它的使用应结合详细的剖析和基准测试,以确保它实际上具有预期的效果。
     * 
     *  <p>这种方法很少适用。它可能有用于调试或测试目的,它可能有助于重现由于竞争条件的错误。
     * 在设计并发控制结构(例如{@link java.util.concurrent.locks}包中的结构)时,它也可能很有用。
     * 
     */
    public static native void yield();

    /**
     * Causes the currently executing thread to sleep (temporarily cease
     * execution) for the specified number of milliseconds, subject to
     * the precision and accuracy of system timers and schedulers. The thread
     * does not lose ownership of any monitors.
     *
     * <p>
     *  导致当前执行的线程睡眠(临时停止执行)指定的毫秒数,服从系统计时器和调度程序的精度和准确性。线程不会丢失任何监视器的所有权。
     * 
     * 
     * @param  millis
     *         the length of time to sleep in milliseconds
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public static native void sleep(long millis) throws InterruptedException;

    /**
     * Causes the currently executing thread to sleep (temporarily cease
     * execution) for the specified number of milliseconds plus the specified
     * number of nanoseconds, subject to the precision and accuracy of system
     * timers and schedulers. The thread does not lose ownership of any
     * monitors.
     *
     * <p>
     *  导致当前执行的线程睡眠(暂时停止执行)指定的毫秒数加上指定的纳秒数,服从系统计时器和调度程序的精度和准确性。线程不会丢失任何监视器的所有权。
     * 
     * 
     * @param  millis
     *         the length of time to sleep in milliseconds
     *
     * @param  nanos
     *         {@code 0-999999} additional nanoseconds to sleep
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative, or the value of
     *          {@code nanos} is not in the range {@code 0-999999}
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public static void sleep(long millis, int nanos)
    throws InterruptedException {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        sleep(millis);
    }

    /**
     * Initializes a Thread with the current AccessControlContext.
     * <p>
     *  用当前AccessControlContext初始化一个线程。
     * 
     * 
     * @see #init(ThreadGroup,Runnable,String,long,AccessControlContext)
     */
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize) {
        init(g, target, name, stackSize, null);
    }

    /**
     * Initializes a Thread.
     *
     * <p>
     *  初始化线程。
     * 
     * 
     * @param g the Thread group
     * @param target the object whose run() method gets called
     * @param name the name of the new Thread
     * @param stackSize the desired stack size for the new thread, or
     *        zero to indicate that this parameter is to be ignored.
     * @param acc the AccessControlContext to inherit, or
     *            AccessController.getContext() if null
     */
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize, AccessControlContext acc) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name.toCharArray();

        Thread parent = currentThread();
        SecurityManager security = System.getSecurityManager();
        if (g == null) {
            /* Determine if it's an applet or not */

            /* If there is a security manager, ask the security manager
            /* <p>
            /* 
               what to do. */
            if (security != null) {
                g = security.getThreadGroup();
            }

            /* If the security doesn't have a strong opinion of the matter
            /* <p>
            /* 
               use the parent thread group. */
            if (g == null) {
                g = parent.getThreadGroup();
            }
        }

        /* checkAccess regardless of whether or not threadgroup is
        /* <p>
        /* 
           explicitly passed in. */
        g.checkAccess();

        /*
         * Do we have the required permissions?
         * <p>
         *  我们有所需的权限吗?
         * 
         */
        if (security != null) {
            if (isCCLOverridden(getClass())) {
                security.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
        }

        g.addUnstarted();

        this.group = g;
        this.daemon = parent.isDaemon();
        this.priority = parent.getPriority();
        if (security == null || isCCLOverridden(parent.getClass()))
            this.contextClassLoader = parent.getContextClassLoader();
        else
            this.contextClassLoader = parent.contextClassLoader;
        this.inheritedAccessControlContext =
                acc != null ? acc : AccessController.getContext();
        this.target = target;
        setPriority(priority);
        if (parent.inheritableThreadLocals != null)
            this.inheritableThreadLocals =
                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        /* Stash the specified stack size in case the VM cares */
        this.stackSize = stackSize;

        /* Set thread ID */
        tid = nextThreadID();
    }

    /**
     * Throws CloneNotSupportedException as a Thread can not be meaningfully
     * cloned. Construct a new Thread instead.
     *
     * <p>
     *  抛出CloneNotSupportedException作为线程不能被有意义地克隆。构造一个新的线程。
     * 
     * 
     * @throws  CloneNotSupportedException
     *          always
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     * <p>
     * 分配一个新的{@code Thread}对象。
     * 这个构造函数具有与{@linkplain #Thread(ThreadGroup,Runnable,String)Thread} {@code(null,null,gname)}相同的效果,其中{@code gname}
     * 是一个新生成的名称。
     * 分配一个新的{@code Thread}对象。自动生成的名称格式为{@code"Thread  - "+} <i> n </i>,其中<i> n </i>是整数。
     * 
     */
    public Thread() {
        init(null, null, "Thread-" + nextThreadNum(), 0);
    }

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (null, target, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     *
     * <p>
     *  分配一个新的{@code Thread}对象。
     * 这个构造函数具有与{@linkplain #Thread(ThreadGroup,Runnable,String)Thread} {@code(null,target,gname)}相同的效果,其中{@code gname}
     * 是一个新生成的名称。
     *  分配一个新的{@code Thread}对象。自动生成的名称格式为{@code"Thread  - "+} <i> n </i>,其中<i> n </i>是整数。
     * 
     * 
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this classes {@code run} method does
     *         nothing.
     */
    public Thread(Runnable target) {
        init(null, target, "Thread-" + nextThreadNum(), 0);
    }

    /**
     * Creates a new Thread that inherits the given AccessControlContext.
     * This is not a public constructor.
     * <p>
     *  创建一个新的Thread,继承给定的AccessControlContext。这不是一个公共构造函数。
     * 
     */
    Thread(Runnable target, AccessControlContext acc) {
        init(null, target, "Thread-" + nextThreadNum(), 0, acc);
    }

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (group, target, gname)} ,where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     *
     * <p>
     *  分配一个新的{@code Thread}对象。
     * 这个构造函数具有与{@linkplain #Thread(ThreadGroup,Runnable,String)Thread} {@code(group,target,gname)}相同的效果,其中{@code gname}
     * 是一个新生成的名称。
     *  分配一个新的{@code Thread}对象。自动生成的名称格式为{@code"Thread  - "+} <i> n </i>,其中<i> n </i>是整数。
     * 
     * 
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this thread's run method is invoked.
     *
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group
     */
    public Thread(ThreadGroup group, Runnable target) {
        init(group, target, "Thread-" + nextThreadNum(), 0);
    }

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (null, null, name)}.
     *
     * <p>
     *  分配一个新的{@code Thread}对象。
     * 此构造函数具有与{@linkplain #Thread(ThreadGroup,Runnable,String)Thread} {@code(null,null,name)}相同的效果。
     * 
     * 
     * @param   name
     *          the name of the new thread
     */
    public Thread(String name) {
        init(null, null, name, 0);
    }

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (group, null, name)}.
     *
     * <p>
     *  分配一个新的{@code Thread}对象。
     * 这个构造函数具有与{@linkplain #Thread(ThreadGroup,Runnable,String)Thread} {@code(group,null,name)}相同的效果。
     * 
     * 
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *
     * @param  name
     *         the name of the new thread
     *
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group
     */
    public Thread(ThreadGroup group, String name) {
        init(group, null, name, 0);
    }

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (null, target, name)}.
     *
     * <p>
     * 分配一个新的{@code Thread}对象。
     * 这个构造函数具有与{@linkplain #Thread(ThreadGroup,Runnable,String)Thread} {@code(null,target,name)}相同的效果。
     * 
     * 
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this thread's run method is invoked.
     *
     * @param  name
     *         the name of the new thread
     */
    public Thread(Runnable target, String name) {
        init(null, target, name, 0);
    }

    /**
     * Allocates a new {@code Thread} object so that it has {@code target}
     * as its run object, has the specified {@code name} as its name,
     * and belongs to the thread group referred to by {@code group}.
     *
     * <p>If there is a security manager, its
     * {@link SecurityManager#checkAccess(ThreadGroup) checkAccess}
     * method is invoked with the ThreadGroup as its argument.
     *
     * <p>In addition, its {@code checkPermission} method is invoked with
     * the {@code RuntimePermission("enableContextClassLoaderOverride")}
     * permission when invoked directly or indirectly by the constructor
     * of a subclass which overrides the {@code getContextClassLoader}
     * or {@code setContextClassLoader} methods.
     *
     * <p>The priority of the newly created thread is set equal to the
     * priority of the thread creating it, that is, the currently running
     * thread. The method {@linkplain #setPriority setPriority} may be
     * used to change the priority to a new value.
     *
     * <p>The newly created thread is initially marked as being a daemon
     * thread if and only if the thread creating it is currently marked
     * as a daemon thread. The method {@linkplain #setDaemon setDaemon}
     * may be used to change whether or not a thread is a daemon.
     *
     * <p>
     *  分配一个新的{@code Thread}对象,使其具有{@code target}作为其运行对象,具有指定的{@code name}作为其名称,并且属于{@code group}引用的线程组。
     * 
     *  <p>如果有安全管理器,则会调用其{@link SecurityManager#checkAccess(ThreadGroup)checkAccess}方法,并将ThreadGroup作为其参数。
     * 
     *  <p>此外,当直接或间接由子类的构造函数调用时,它的{@code checkPermission}方法会被{@code RuntimePermission("enableContextClassLoaderOverride")}
     * 权限调用,它会覆盖{@code getContextClassLoader}代码setContextClassLoader}方法。
     * 
     *  <p>新创建的线程的优先级设置为创建它的线程的优先级,即当前正在运行的线程。方法{@linkplain #setPriority setPriority}可用于将优先级更改为新值。
     * 
     *  <p>当且仅当创建它的线程当前被标记为守护线程时,新创建的线程才被初始标记为守护线程。方法{@linkplain #setDaemon setDaemon}可用于更改线程是否为守护程序。
     * 
     * 
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this thread's run method is invoked.
     *
     * @param  name
     *         the name of the new thread
     *
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group or cannot override the context class loader methods.
     */
    public Thread(ThreadGroup group, Runnable target, String name) {
        init(group, target, name, 0);
    }

    /**
     * Allocates a new {@code Thread} object so that it has {@code target}
     * as its run object, has the specified {@code name} as its name,
     * and belongs to the thread group referred to by {@code group}, and has
     * the specified <i>stack size</i>.
     *
     * <p>This constructor is identical to {@link
     * #Thread(ThreadGroup,Runnable,String)} with the exception of the fact
     * that it allows the thread stack size to be specified.  The stack size
     * is the approximate number of bytes of address space that the virtual
     * machine is to allocate for this thread's stack.  <b>The effect of the
     * {@code stackSize} parameter, if any, is highly platform dependent.</b>
     *
     * <p>On some platforms, specifying a higher value for the
     * {@code stackSize} parameter may allow a thread to achieve greater
     * recursion depth before throwing a {@link StackOverflowError}.
     * Similarly, specifying a lower value may allow a greater number of
     * threads to exist concurrently without throwing an {@link
     * OutOfMemoryError} (or other internal error).  The details of
     * the relationship between the value of the <tt>stackSize</tt> parameter
     * and the maximum recursion depth and concurrency level are
     * platform-dependent.  <b>On some platforms, the value of the
     * {@code stackSize} parameter may have no effect whatsoever.</b>
     *
     * <p>The virtual machine is free to treat the {@code stackSize}
     * parameter as a suggestion.  If the specified value is unreasonably low
     * for the platform, the virtual machine may instead use some
     * platform-specific minimum value; if the specified value is unreasonably
     * high, the virtual machine may instead use some platform-specific
     * maximum.  Likewise, the virtual machine is free to round the specified
     * value up or down as it sees fit (or to ignore it completely).
     *
     * <p>Specifying a value of zero for the {@code stackSize} parameter will
     * cause this constructor to behave exactly like the
     * {@code Thread(ThreadGroup, Runnable, String)} constructor.
     *
     * <p><i>Due to the platform-dependent nature of the behavior of this
     * constructor, extreme care should be exercised in its use.
     * The thread stack size necessary to perform a given computation will
     * likely vary from one JRE implementation to another.  In light of this
     * variation, careful tuning of the stack size parameter may be required,
     * and the tuning may need to be repeated for each JRE implementation on
     * which an application is to run.</i>
     *
     * <p>Implementation note: Java platform implementers are encouraged to
     * document their implementation's behavior with respect to the
     * {@code stackSize} parameter.
     *
     *
     * <p>
     * 分配一个新的{@code Thread}对象,使其具有{@code target}作为其运行对象,具有指定的{@code name}作为其名称,并且属于{@code group}引用的线程组,并具有指定
     * 的<i>堆栈大小</i>。
     * 
     *  <p>这个构造函数与{@link #Thread(ThreadGroup,Runnable,String)}完全相同,只不过它允许指定线程堆栈大小。
     * 堆栈大小是虚拟机要为此线程的堆栈分配的地址空间的大致字节数。 <b> {@code stackSize}参数(如果有)的效果高度依赖于平台。</b>。
     * 
     *  <p>在某些平台上,为{@code stackSize}参数指定较高的值可能允许线程在抛出{@link StackOverflowError}之前达到更大的递归深度。
     * 类似地,指定较低的值可允许更多数量的线程同时存在,而不引发{@link OutOfMemoryError}(或其他内部错误)。
     *  <tt> stackSize </tt>参数的值与最大递归深度和并发级别之间的关系的详细信息是平台相关的。 <b>在某些平台上,{@code stackSize}参数的值可能没有任何影响。</b>。
     * 
     * <p>虚拟机可以将{@code stackSize}参数视为建议。
     * 如果平台的指定值不合理地低,则虚拟机可以改为使用一些平台特定的最小值;如果指定的值不合理地高,则虚拟机可以改为使用一些平台特定的最大值。
     * 同样,虚拟机可以自由地向上或向下舍入指定值,因为它认为合适(或完全忽略它)。
     * 
     *  <p>为{@code stackSize}参数指定零值会使此构造函数的行为与{@code Thread(ThreadGroup,Runnable,String)}构造函数完全相同。
     * 
     *  <p> <i>由于此构造函数的行为的平台依赖性质,在使用时应特别小心。执行给定计算所需的线程堆栈大小可能从一个JRE实现到另一个JRE实现。
     * 鉴于这种变化,可能需要仔细调整堆栈大小参数,并且可能需要对应用程序在其上运行的每个JRE实现重复调整。</i>。
     * 
     *  <p>实现注意事项：鼓励Java平台实现者记录其实现的{@code stackSize}参数的行为。
     * 
     * 
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this thread's run method is invoked.
     *
     * @param  name
     *         the name of the new thread
     *
     * @param  stackSize
     *         the desired stack size for the new thread, or zero to indicate
     *         that this parameter is to be ignored.
     *
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group
     *
     * @since 1.4
     */
    public Thread(ThreadGroup group, Runnable target, String name,
                  long stackSize) {
        init(group, target, name, stackSize);
    }

    /**
     * Causes this thread to begin execution; the Java Virtual Machine
     * calls the <code>run</code> method of this thread.
     * <p>
     * The result is that two threads are running concurrently: the
     * current thread (which returns from the call to the
     * <code>start</code> method) and the other thread (which executes its
     * <code>run</code> method).
     * <p>
     * It is never legal to start a thread more than once.
     * In particular, a thread may not be restarted once it has completed
     * execution.
     *
     * <p>
     *  导致此线程开始执行; Java虚拟机调用此线程的<code> run </code>方法。
     * <p>
     * 结果是两个线程并发运行：当前线程(从调用返回到<code> start </code>方法)和另一个线程(执行<code> run </code>方法)。
     * <p>
     *  多次启动线程是不合法的。特别地,一旦线程已经完成执行,则可以不重新启动它。
     * 
     * 
     * @exception  IllegalThreadStateException  if the thread was already
     *               started.
     * @see        #run()
     * @see        #stop()
     */
    public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         * <p>
         *  对于由VM创建/设置的主方法线程或"系统"组线程,不会调用此方法。将来可能还必须将添加到此方法的任何新功能添加到VM。
         * 
         *  零状态值对应于状态"NEW"。
         * 
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * <p>
         *  以便可以将其添加到组的线程列表中
         * 
         * 
         * and the group's unstarted count can be decremented. */
        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                /* <p>
                /* 
                  it will be passed up the call stack */
            }
        }
    }

    private native void start0();

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * <p>
     *  如果这个线程是使用单独的<code> Runnable </code>运行对象构造的,那么<code> Runnable </code>对象的<code> run </code>否则,此方法不执行任何
     * 操作并返回。
     * <p>
     *  <code> Thread </code>的子类应该覆盖此方法。
     * 
     * 
     * @see     #start()
     * @see     #stop()
     * @see     #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }

    /**
     * This method is called by the system to give a Thread
     * a chance to clean up before it actually exits.
     * <p>
     *  这个方法被系统调用,给Thread一个机会在它实际退出之前清理。
     * 
     */
    private void exit() {
        if (group != null) {
            group.threadTerminated(this);
            group = null;
        }
        /* Aggressively null out all reference fields: see bug 4006245 */
        target = null;
        /* Speed the release of some of these resources */
        threadLocals = null;
        inheritableThreadLocals = null;
        inheritedAccessControlContext = null;
        blocker = null;
        uncaughtExceptionHandler = null;
    }

    /**
     * Forces the thread to stop executing.
     * <p>
     * If there is a security manager installed, its <code>checkAccess</code>
     * method is called with <code>this</code>
     * as its argument. This may result in a
     * <code>SecurityException</code> being raised (in the current thread).
     * <p>
     * If this thread is different from the current thread (that is, the current
     * thread is trying to stop a thread other than itself), the
     * security manager's <code>checkPermission</code> method (with a
     * <code>RuntimePermission("stopThread")</code> argument) is called in
     * addition.
     * Again, this may result in throwing a
     * <code>SecurityException</code> (in the current thread).
     * <p>
     * The thread represented by this thread is forced to stop whatever
     * it is doing abnormally and to throw a newly created
     * <code>ThreadDeath</code> object as an exception.
     * <p>
     * It is permitted to stop a thread that has not yet been started.
     * If the thread is eventually started, it immediately terminates.
     * <p>
     * An application should not normally try to catch
     * <code>ThreadDeath</code> unless it must do some extraordinary
     * cleanup operation (note that the throwing of
     * <code>ThreadDeath</code> causes <code>finally</code> clauses of
     * <code>try</code> statements to be executed before the thread
     * officially dies).  If a <code>catch</code> clause catches a
     * <code>ThreadDeath</code> object, it is important to rethrow the
     * object so that the thread actually dies.
     * <p>
     * The top-level error handler that reacts to otherwise uncaught
     * exceptions does not print out a message or otherwise notify the
     * application if the uncaught exception is an instance of
     * <code>ThreadDeath</code>.
     *
     * <p>
     *  强制线程停止执行。
     * <p>
     *  如果安装了安全管理器,则会使用<code> this </code>作为其参数来调用<code> checkAccess </code>方法。
     * 这可能导致产生<code> SecurityException </code>(在当前线程中)。
     * <p>
     * 如果这个线程与当前线程不同(也就是说,当前线程试图停止除自身外的线程),安全管理器的<code> checkPermission </code>方法(带有<code> RuntimePermission
     * ("stopThread" )</code>参数)。
     * 同样,这可能会导致抛出一个<code> SecurityException </code>(在当前线程中)。
     * <p>
     *  由此线程表示的线程被强制停止,无论它正在做什么异常,并抛出一个新创建的<code> ThreadDeath </code>对象作为异常。
     * <p>
     *  允许停止尚未启动的线程。如果线程最终启动,它立即终止。
     * <p>
     *  应用程序通常不应该尝试捕获<code> ThreadDeath </code>,除非它必须做一些非凡的清理操作(注意,抛出<code> ThreadDeath </code>会导致<code> fin
     * ally </code>代码> try </code>语句在线程正式死亡之前执行)。
     * 如果<code> catch </code>子句捕获一个<code> ThreadDeath </code>对象,重要的是重新抛出该对象,以使线程实际上死掉。
     * <p>
     *  如果未捕获的异常是<code> ThreadDeath </code>的实例,则对其他未捕获的异常作出反应的顶级错误处理程序不打印出消息或通知应用程序。
     * 
     * 
     * @exception  SecurityException  if the current thread cannot
     *               modify this thread.
     * @see        #interrupt()
     * @see        #checkAccess()
     * @see        #run()
     * @see        #start()
     * @see        ThreadDeath
     * @see        ThreadGroup#uncaughtException(Thread,Throwable)
     * @see        SecurityManager#checkAccess(Thread)
     * @see        SecurityManager#checkPermission
     * @deprecated This method is inherently unsafe.  Stopping a thread with
     *       Thread.stop causes it to unlock all of the monitors that it
     *       has locked (as a natural consequence of the unchecked
     *       <code>ThreadDeath</code> exception propagating up the stack).  If
     *       any of the objects previously protected by these monitors were in
     *       an inconsistent state, the damaged objects become visible to
     *       other threads, potentially resulting in arbitrary behavior.  Many
     *       uses of <code>stop</code> should be replaced by code that simply
     *       modifies some variable to indicate that the target thread should
     *       stop running.  The target thread should check this variable
     *       regularly, and return from its run method in an orderly fashion
     *       if the variable indicates that it is to stop running.  If the
     *       target thread waits for long periods (on a condition variable,
     *       for example), the <code>interrupt</code> method should be used to
     *       interrupt the wait.
     *       For more information, see
     *       <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">Why
     *       are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
     */
    @Deprecated
    public final void stop() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            checkAccess();
            if (this != Thread.currentThread()) {
                security.checkPermission(SecurityConstants.STOP_THREAD_PERMISSION);
            }
        }
        // A zero status value corresponds to "NEW", it can't change to
        // not-NEW because we hold the lock.
        if (threadStatus != 0) {
            resume(); // Wake up thread if it was suspended; no-op otherwise
        }

        // The VM can handle all thread states
        stop0(new ThreadDeath());
    }

    /**
     * Throws {@code UnsupportedOperationException}.
     *
     * <p>
     *  引发{@code UnsupportedOperationException}。
     * 
     * 
     * @param obj ignored
     *
     * @deprecated This method was originally designed to force a thread to stop
     *        and throw a given {@code Throwable} as an exception. It was
     *        inherently unsafe (see {@link #stop()} for details), and furthermore
     *        could be used to generate exceptions that the target thread was
     *        not prepared to handle.
     *        For more information, see
     *        <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">Why
     *        are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
     */
    @Deprecated
    public final synchronized void stop(Throwable obj) {
        throw new UnsupportedOperationException();
    }

    /**
     * Interrupts this thread.
     *
     * <p> Unless the current thread is interrupting itself, which is
     * always permitted, the {@link #checkAccess() checkAccess} method
     * of this thread is invoked, which may cause a {@link
     * SecurityException} to be thrown.
     *
     * <p> If this thread is blocked in an invocation of the {@link
     * Object#wait() wait()}, {@link Object#wait(long) wait(long)}, or {@link
     * Object#wait(long, int) wait(long, int)} methods of the {@link Object}
     * class, or of the {@link #join()}, {@link #join(long)}, {@link
     * #join(long, int)}, {@link #sleep(long)}, or {@link #sleep(long, int)},
     * methods of this class, then its interrupt status will be cleared and it
     * will receive an {@link InterruptedException}.
     *
     * <p> If this thread is blocked in an I/O operation upon an {@link
     * java.nio.channels.InterruptibleChannel InterruptibleChannel}
     * then the channel will be closed, the thread's interrupt
     * status will be set, and the thread will receive a {@link
     * java.nio.channels.ClosedByInterruptException}.
     *
     * <p> If this thread is blocked in a {@link java.nio.channels.Selector}
     * then the thread's interrupt status will be set and it will return
     * immediately from the selection operation, possibly with a non-zero
     * value, just as if the selector's {@link
     * java.nio.channels.Selector#wakeup wakeup} method were invoked.
     *
     * <p> If none of the previous conditions hold then this thread's interrupt
     * status will be set. </p>
     *
     * <p> Interrupting a thread that is not alive need not have any effect.
     *
     * <p>
     *  中断此线程。
     * 
     * <p>除非当前线程正在中断它自己,这是允许的,这个线程的{@link #checkAccess()checkAccess}方法被调用,这可能导致{@link SecurityException}被抛出。
     * 
     *  <p>如果此线程在调用{@link Object#wait()wait()},{@link Object#wait(long)wait(long)}或{@link Object#wait ,{@link #join()}
     * ,{@link #join(long)},{@link #join(long,int) int)},{@link #sleep(long)}或{@link #sleep(long,int)},这个类的方
     * 法,然后它的中断状态将被清除,它会收到{@link InterruptedException}。
     * 
     *  <p>如果这个线程在{@link java.nio.channels.InterruptibleChannel InterruptibleChannel}的I / O操作中被阻塞,那么通道将被关闭,线
     * 程的中断状态将被设置,线程将接收{ @link java.nio.channels.ClosedByInterruptException}。
     * 
     *  <p>如果此线程在{@link java.nio.channels.Selector}中被阻塞,则线程的中断状态将被设置,并且它将立即从选择操作返回,可能具有非零值,就像调用了选择器的{@link java.nio.channels.Selector#wakeup wakeup}
     * 方法。
     * 
     *  <p>如果先前的条件都不成立,则该线程的中断状态将被设置。 </p>
     * 
     *  <p>中断不存在的线程不需要有任何效果。
     * 
     * 
     * @throws  SecurityException
     *          if the current thread cannot modify this thread
     *
     * @revised 6.0
     * @spec JSR-51
     */
    public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();

        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0();           // Just to set the interrupt flag
                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }

    /**
     * Tests whether the current thread has been interrupted.  The
     * <i>interrupted status</i> of the thread is cleared by this method.  In
     * other words, if this method were to be called twice in succession, the
     * second call would return false (unless the current thread were
     * interrupted again, after the first call had cleared its interrupted
     * status and before the second call had examined it).
     *
     * <p>A thread interruption ignored because a thread was not alive
     * at the time of the interrupt will be reflected by this method
     * returning false.
     *
     * <p>
     * 测试当前线程是否已中断。该方法清除线程的<i>中断状态</i>。
     * 换句话说,如果这个方法被连续调用两次,第二次调用将返回false(除非当前线程在第一次调用已经清除其中断状态之后,第二次调用检查它之前再次中断)。
     * 
     *  <p>线程中断被忽略,因为在中断时线程不存活,这个方法将返回false。
     * 
     * 
     * @return  <code>true</code> if the current thread has been interrupted;
     *          <code>false</code> otherwise.
     * @see #isInterrupted()
     * @revised 6.0
     */
    public static boolean interrupted() {
        return currentThread().isInterrupted(true);
    }

    /**
     * Tests whether this thread has been interrupted.  The <i>interrupted
     * status</i> of the thread is unaffected by this method.
     *
     * <p>A thread interruption ignored because a thread was not alive
     * at the time of the interrupt will be reflected by this method
     * returning false.
     *
     * <p>
     *  测试此线程是否已中断。线程的<i>中断状态</i>不受此方法的影响。
     * 
     *  <p>线程中断被忽略,因为在中断时线程不存活,这个方法将返回false。
     * 
     * 
     * @return  <code>true</code> if this thread has been interrupted;
     *          <code>false</code> otherwise.
     * @see     #interrupted()
     * @revised 6.0
     */
    public boolean isInterrupted() {
        return isInterrupted(false);
    }

    /**
     * Tests if some Thread has been interrupted.  The interrupted state
     * is reset or not based on the value of ClearInterrupted that is
     * passed.
     * <p>
     *  测试某些线程是否已中断。根据传递的ClearInterrupted的值,重置或不重置中断状态。
     * 
     */
    private native boolean isInterrupted(boolean ClearInterrupted);

    /**
     * Throws {@link NoSuchMethodError}.
     *
     * <p>
     *  引发{@link NoSuchMethodError}。
     * 
     * 
     * @deprecated This method was originally designed to destroy this
     *     thread without any cleanup. Any monitors it held would have
     *     remained locked. However, the method was never implemented.
     *     If if were to be implemented, it would be deadlock-prone in
     *     much the manner of {@link #suspend}. If the target thread held
     *     a lock protecting a critical system resource when it was
     *     destroyed, no thread could ever access this resource again.
     *     If another thread ever attempted to lock this resource, deadlock
     *     would result. Such deadlocks typically manifest themselves as
     *     "frozen" processes. For more information, see
     *     <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">
     *     Why are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
     * @throws NoSuchMethodError always
     */
    @Deprecated
    public void destroy() {
        throw new NoSuchMethodError();
    }

    /**
     * Tests if this thread is alive. A thread is alive if it has
     * been started and has not yet died.
     *
     * <p>
     *  测试这个线程是否存活。如果一个线程已经启动并且还没有死亡,它还活着。
     * 
     * 
     * @return  <code>true</code> if this thread is alive;
     *          <code>false</code> otherwise.
     */
    public final native boolean isAlive();

    /**
     * Suspends this thread.
     * <p>
     * First, the <code>checkAccess</code> method of this thread is called
     * with no arguments. This may result in throwing a
     * <code>SecurityException </code>(in the current thread).
     * <p>
     * If the thread is alive, it is suspended and makes no further
     * progress unless and until it is resumed.
     *
     * <p>
     *  暂停此主题。
     * <p>
     *  首先,这个线程的<code> checkAccess </code>方法被调用没有参数。这可能会导致抛出<code> SecurityException </code>(在当前线程中)。
     * <p>
     *  如果线程是活的,它被挂起并且不再进一步进行,除非和直到它被恢复。
     * 
     * 
     * @exception  SecurityException  if the current thread cannot modify
     *               this thread.
     * @see #checkAccess
     * @deprecated   This method has been deprecated, as it is
     *   inherently deadlock-prone.  If the target thread holds a lock on the
     *   monitor protecting a critical system resource when it is suspended, no
     *   thread can access this resource until the target thread is resumed. If
     *   the thread that would resume the target thread attempts to lock this
     *   monitor prior to calling <code>resume</code>, deadlock results.  Such
     *   deadlocks typically manifest themselves as "frozen" processes.
     *   For more information, see
     *   <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">Why
     *   are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
     */
    @Deprecated
    public final void suspend() {
        checkAccess();
        suspend0();
    }

    /**
     * Resumes a suspended thread.
     * <p>
     * First, the <code>checkAccess</code> method of this thread is called
     * with no arguments. This may result in throwing a
     * <code>SecurityException</code> (in the current thread).
     * <p>
     * If the thread is alive but suspended, it is resumed and is
     * permitted to make progress in its execution.
     *
     * <p>
     *  恢复悬挂线程。
     * <p>
     * 首先,这个线程的<code> checkAccess </code>方法被调用没有参数。这可能会导致抛出<code> SecurityException </code>(在当前线程中)。
     * <p>
     *  如果线程仍然活动但是被挂起,则它被恢复并且被允许在其执行中取得进展。
     * 
     * 
     * @exception  SecurityException  if the current thread cannot modify this
     *               thread.
     * @see        #checkAccess
     * @see        #suspend()
     * @deprecated This method exists solely for use with {@link #suspend},
     *     which has been deprecated because it is deadlock-prone.
     *     For more information, see
     *     <a href="{@docRoot}/../technotes/guides/concurrency/threadPrimitiveDeprecation.html">Why
     *     are Thread.stop, Thread.suspend and Thread.resume Deprecated?</a>.
     */
    @Deprecated
    public final void resume() {
        checkAccess();
        resume0();
    }

    /**
     * Changes the priority of this thread.
     * <p>
     * First the <code>checkAccess</code> method of this thread is called
     * with no arguments. This may result in throwing a
     * <code>SecurityException</code>.
     * <p>
     * Otherwise, the priority of this thread is set to the smaller of
     * the specified <code>newPriority</code> and the maximum permitted
     * priority of the thread's thread group.
     *
     * <p>
     *  更改此线程的优先级。
     * <p>
     *  首先,该线程的<code> checkAccess </code>方法在没有参数的情况下被调用。这可能会导致抛出一个<code> SecurityException </code>。
     * <p>
     *  否则,此线程的优先级设置为指定的<code> newPriority </code>和线程线程组的最大允许优先级中的较小值。
     * 
     * 
     * @param newPriority priority to set this thread to
     * @exception  IllegalArgumentException  If the priority is not in the
     *               range <code>MIN_PRIORITY</code> to
     *               <code>MAX_PRIORITY</code>.
     * @exception  SecurityException  if the current thread cannot modify
     *               this thread.
     * @see        #getPriority
     * @see        #checkAccess()
     * @see        #getThreadGroup()
     * @see        #MAX_PRIORITY
     * @see        #MIN_PRIORITY
     * @see        ThreadGroup#getMaxPriority()
     */
    public final void setPriority(int newPriority) {
        ThreadGroup g;
        checkAccess();
        if (newPriority > MAX_PRIORITY || newPriority < MIN_PRIORITY) {
            throw new IllegalArgumentException();
        }
        if((g = getThreadGroup()) != null) {
            if (newPriority > g.getMaxPriority()) {
                newPriority = g.getMaxPriority();
            }
            setPriority0(priority = newPriority);
        }
    }

    /**
     * Returns this thread's priority.
     *
     * <p>
     *  返回此线程的优先级。
     * 
     * 
     * @return  this thread's priority.
     * @see     #setPriority
     */
    public final int getPriority() {
        return priority;
    }

    /**
     * Changes the name of this thread to be equal to the argument
     * <code>name</code>.
     * <p>
     * First the <code>checkAccess</code> method of this thread is called
     * with no arguments. This may result in throwing a
     * <code>SecurityException</code>.
     *
     * <p>
     *  将此线程的名称更改为等于<code> name </code>的参数。
     * <p>
     *  首先,该线程的<code> checkAccess </code>方法在没有参数的情况下被调用。这可能会导致抛出一个<code> SecurityException </code>。
     * 
     * 
     * @param      name   the new name for this thread.
     * @exception  SecurityException  if the current thread cannot modify this
     *               thread.
     * @see        #getName
     * @see        #checkAccess()
     */
    public final synchronized void setName(String name) {
        checkAccess();
        this.name = name.toCharArray();
        if (threadStatus != 0) {
            setNativeName(name);
        }
    }

    /**
     * Returns this thread's name.
     *
     * <p>
     *  返回此线程的名称。
     * 
     * 
     * @return  this thread's name.
     * @see     #setName(String)
     */
    public final String getName() {
        return new String(name, true);
    }

    /**
     * Returns the thread group to which this thread belongs.
     * This method returns null if this thread has died
     * (been stopped).
     *
     * <p>
     *  返回此线程所属的线程组。如果此线程已死亡(已停止),此方法将返回null。
     * 
     * 
     * @return  this thread's thread group.
     */
    public final ThreadGroup getThreadGroup() {
        return group;
    }

    /**
     * Returns an estimate of the number of active threads in the current
     * thread's {@linkplain java.lang.ThreadGroup thread group} and its
     * subgroups. Recursively iterates over all subgroups in the current
     * thread's thread group.
     *
     * <p> The value returned is only an estimate because the number of
     * threads may change dynamically while this method traverses internal
     * data structures, and might be affected by the presence of certain
     * system threads. This method is intended primarily for debugging
     * and monitoring purposes.
     *
     * <p>
     *  返回当前线程的{@linkplain java.lang.ThreadGroup线程组}及其子组中活动线程数的估计值。递归迭代当前线程的线程组中的所有子组。
     * 
     * <p>返回的值仅为估计值,因为线程数可能会在此方法遍历内部数据结构时动态更改,并且可能会受到某些系统线程的存在的影响。此方法主要用于调试和监视目的。
     * 
     * 
     * @return  an estimate of the number of active threads in the current
     *          thread's thread group and in any other thread group that
     *          has the current thread's thread group as an ancestor
     */
    public static int activeCount() {
        return currentThread().getThreadGroup().activeCount();
    }

    /**
     * Copies into the specified array every active thread in the current
     * thread's thread group and its subgroups. This method simply
     * invokes the {@link java.lang.ThreadGroup#enumerate(Thread[])}
     * method of the current thread's thread group.
     *
     * <p> An application might use the {@linkplain #activeCount activeCount}
     * method to get an estimate of how big the array should be, however
     * <i>if the array is too short to hold all the threads, the extra threads
     * are silently ignored.</i>  If it is critical to obtain every active
     * thread in the current thread's thread group and its subgroups, the
     * invoker should verify that the returned int value is strictly less
     * than the length of {@code tarray}.
     *
     * <p> Due to the inherent race condition in this method, it is recommended
     * that the method only be used for debugging and monitoring purposes.
     *
     * <p>
     *  将当前线程的线程组及其子组中的每个活动线程复制到指定的数组中。
     * 此方法简单地调用当前线程的线程组的{@link java.lang.ThreadGroup#enumerate(Thread [])}方法。
     * 
     *  <p>应用程序可能使用{@linkplain #activeCount activeCount}方法来估计数组的大小,但<i>如果数组太短,无法容纳所有线程,则额外线程会被忽略</i>如果获取当前线程
     * 的线程组及其子组中的每个活动线程是至关重要的,则调用者应该验证返回的int值是否严格小于{@code tarray}的长度。
     * 
     *  <p>由于此方法中的固有竞争条件,建议此方法仅用于调试和监视目的。
     * 
     * 
     * @param  tarray
     *         an array into which to put the list of threads
     *
     * @return  the number of threads put into the array
     *
     * @throws  SecurityException
     *          if {@link java.lang.ThreadGroup#checkAccess} determines that
     *          the current thread cannot access its thread group
     */
    public static int enumerate(Thread tarray[]) {
        return currentThread().getThreadGroup().enumerate(tarray);
    }

    /**
     * Counts the number of stack frames in this thread. The thread must
     * be suspended.
     *
     * <p>
     *  计算此线程中的堆栈帧数。线程必须挂起。
     * 
     * 
     * @return     the number of stack frames in this thread.
     * @exception  IllegalThreadStateException  if this thread is not
     *             suspended.
     * @deprecated The definition of this call depends on {@link #suspend},
     *             which is deprecated.  Further, the results of this call
     *             were never well-defined.
     */
    @Deprecated
    public native int countStackFrames();

    /**
     * Waits at most {@code millis} milliseconds for this thread to
     * die. A timeout of {@code 0} means to wait forever.
     *
     * <p> This implementation uses a loop of {@code this.wait} calls
     * conditioned on {@code this.isAlive}. As a thread terminates the
     * {@code this.notifyAll} method is invoked. It is recommended that
     * applications not use {@code wait}, {@code notify}, or
     * {@code notifyAll} on {@code Thread} instances.
     *
     * <p>
     *  此线程最多等待{@code millis}毫秒。超时{@code 0}意味着永远等待。
     * 
     * <p>此实现使用{@code this.wait}调用的循环{@code this.isAlive}。当线程终止时,调用{@code this.notifyAll}方法。
     * 建议应用程序不要在{@code Thread}实例上使用{@code wait},{@code notify}或{@code notifyAll}。
     * 
     * 
     * @param  millis
     *         the time to wait in milliseconds
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }

    /**
     * Waits at most {@code millis} milliseconds plus
     * {@code nanos} nanoseconds for this thread to die.
     *
     * <p> This implementation uses a loop of {@code this.wait} calls
     * conditioned on {@code this.isAlive}. As a thread terminates the
     * {@code this.notifyAll} method is invoked. It is recommended that
     * applications not use {@code wait}, {@code notify}, or
     * {@code notifyAll} on {@code Thread} instances.
     *
     * <p>
     *  最多等待{@code millis}毫秒加上此线程死亡的{@code nanos}纳秒。
     * 
     *  <p>此实现使用{@code this.wait}调用的循环{@code this.isAlive}。当线程终止时,调用{@code this.notifyAll}方法。
     * 建议应用程序不要在{@code Thread}实例上使用{@code wait},{@code notify}或{@code notifyAll}。
     * 
     * 
     * @param  millis
     *         the time to wait in milliseconds
     *
     * @param  nanos
     *         {@code 0-999999} additional nanoseconds to wait
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative, or the value
     *          of {@code nanos} is not in the range {@code 0-999999}
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public final synchronized void join(long millis, int nanos)
    throws InterruptedException {

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        join(millis);
    }

    /**
     * Waits for this thread to die.
     *
     * <p> An invocation of this method behaves in exactly the same
     * way as the invocation
     *
     * <blockquote>
     * {@linkplain #join(long) join}{@code (0)}
     * </blockquote>
     *
     * <p>
     *  等待这个线程死亡。
     * 
     *  <p>此方法的调用行为与调用完全相同
     * 
     * <blockquote>
     *  {@linkplain #join(long)join} {@ code(0)}
     * </blockquote>
     * 
     * 
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public final void join() throws InterruptedException {
        join(0);
    }

    /**
     * Prints a stack trace of the current thread to the standard error stream.
     * This method is used only for debugging.
     *
     * <p>
     *  将当前线程的堆栈跟踪打印到标准错误流。此方法仅用于调试。
     * 
     * 
     * @see     Throwable#printStackTrace()
     */
    public static void dumpStack() {
        new Exception("Stack trace").printStackTrace();
    }

    /**
     * Marks this thread as either a {@linkplain #isDaemon daemon} thread
     * or a user thread. The Java Virtual Machine exits when the only
     * threads running are all daemon threads.
     *
     * <p> This method must be invoked before the thread is started.
     *
     * <p>
     *  将此线程标记为{@linkplain #isDaemon守护进程}线程或用户线程。当唯一运行的线程都是守护线程时,Java虚拟机退出。
     * 
     *  <p>此方法必须在线程启动之前调用。
     * 
     * 
     * @param  on
     *         if {@code true}, marks this thread as a daemon thread
     *
     * @throws  IllegalThreadStateException
     *          if this thread is {@linkplain #isAlive alive}
     *
     * @throws  SecurityException
     *          if {@link #checkAccess} determines that the current
     *          thread cannot modify this thread
     */
    public final void setDaemon(boolean on) {
        checkAccess();
        if (isAlive()) {
            throw new IllegalThreadStateException();
        }
        daemon = on;
    }

    /**
     * Tests if this thread is a daemon thread.
     *
     * <p>
     *  测试这个线程是否是守护线程。
     * 
     * 
     * @return  <code>true</code> if this thread is a daemon thread;
     *          <code>false</code> otherwise.
     * @see     #setDaemon(boolean)
     */
    public final boolean isDaemon() {
        return daemon;
    }

    /**
     * Determines if the currently running thread has permission to
     * modify this thread.
     * <p>
     * If there is a security manager, its <code>checkAccess</code> method
     * is called with this thread as its argument. This may result in
     * throwing a <code>SecurityException</code>.
     *
     * <p>
     *  确定当前运行的线程是否有修改此线程的权限。
     * <p>
     *  如果有一个安全管理器,它的<code> checkAccess </code>方法被调用与此线程作为其参数。这可能会导致抛出一个<code> SecurityException </code>。
     * 
     * 
     * @exception  SecurityException  if the current thread is not allowed to
     *               access this thread.
     * @see        SecurityManager#checkAccess(Thread)
     */
    public final void checkAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkAccess(this);
        }
    }

    /**
     * Returns a string representation of this thread, including the
     * thread's name, priority, and thread group.
     *
     * <p>
     * 返回此线程的字符串表示形式,包括线程的名称,优先级和线程组。
     * 
     * 
     * @return  a string representation of this thread.
     */
    public String toString() {
        ThreadGroup group = getThreadGroup();
        if (group != null) {
            return "Thread[" + getName() + "," + getPriority() + "," +
                           group.getName() + "]";
        } else {
            return "Thread[" + getName() + "," + getPriority() + "," +
                            "" + "]";
        }
    }

    /**
     * Returns the context ClassLoader for this Thread. The context
     * ClassLoader is provided by the creator of the thread for use
     * by code running in this thread when loading classes and resources.
     * If not {@linkplain #setContextClassLoader set}, the default is the
     * ClassLoader context of the parent Thread. The context ClassLoader of the
     * primordial thread is typically set to the class loader used to load the
     * application.
     *
     * <p>If a security manager is present, and the invoker's class loader is not
     * {@code null} and is not the same as or an ancestor of the context class
     * loader, then this method invokes the security manager's {@link
     * SecurityManager#checkPermission(java.security.Permission) checkPermission}
     * method with a {@link RuntimePermission RuntimePermission}{@code
     * ("getClassLoader")} permission to verify that retrieval of the context
     * class loader is permitted.
     *
     * <p>
     *  返回此Thread的上下文ClassLoader。上下文ClassLoader由线程的创建者提供,供在加载类和资源时在此线程中运行的代码使用。
     * 如果没有{@linkplain #setContextClassLoader set},默认是父Thread的ClassLoader上下文。
     * 原始线程的上下文ClassLoader通常设置为用于加载应用程序的类加载器。
     * 
     *  <p>如果存在安全管理器,并且调用者的类加载器不是{@code null},并且不与上下文类加载器相同或是上下文类加载器的祖先,则此方法调用安全管理器的{@link SecurityManager#checkPermission (java.security.Permission)checkPermission}
     * 方法与{@link RuntimePermission RuntimePermission} {@ code("getClassLoader")}权限,以验证是否允许检索上下文类加载器。
     * 
     * 
     * @return  the context ClassLoader for this Thread, or {@code null}
     *          indicating the system class loader (or, failing that, the
     *          bootstrap class loader)
     *
     * @throws  SecurityException
     *          if the current thread cannot get the context ClassLoader
     *
     * @since 1.2
     */
    @CallerSensitive
    public ClassLoader getContextClassLoader() {
        if (contextClassLoader == null)
            return null;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            ClassLoader.checkClassLoaderPermission(contextClassLoader,
                                                   Reflection.getCallerClass());
        }
        return contextClassLoader;
    }

    /**
     * Sets the context ClassLoader for this Thread. The context
     * ClassLoader can be set when a thread is created, and allows
     * the creator of the thread to provide the appropriate class loader,
     * through {@code getContextClassLoader}, to code running in the thread
     * when loading classes and resources.
     *
     * <p>If a security manager is present, its {@link
     * SecurityManager#checkPermission(java.security.Permission) checkPermission}
     * method is invoked with a {@link RuntimePermission RuntimePermission}{@code
     * ("setContextClassLoader")} permission to see if setting the context
     * ClassLoader is permitted.
     *
     * <p>
     *  设置此线程的上下文ClassLoader。
     * 上下文ClassLoader可以在创建线程时设置,并允许线程的创建者通过{@code getContextClassLoader}为加载类和资源时在线程中运行的代码提供适当的类加载器。
     * 
     * <p>如果存在安全管理器,则会使用{@link RuntimePermission RuntimePermission} {@ code("setContextClassLoader")}权限调用其{@link SecurityManager#checkPermission(java.security.Permission)checkPermission}
     * 设置上下文ClassLoader是允许的。
     * 
     * 
     * @param  cl
     *         the context ClassLoader for this Thread, or null  indicating the
     *         system class loader (or, failing that, the bootstrap class loader)
     *
     * @throws  SecurityException
     *          if the current thread cannot set the context ClassLoader
     *
     * @since 1.2
     */
    public void setContextClassLoader(ClassLoader cl) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("setContextClassLoader"));
        }
        contextClassLoader = cl;
    }

    /**
     * Returns <tt>true</tt> if and only if the current thread holds the
     * monitor lock on the specified object.
     *
     * <p>This method is designed to allow a program to assert that
     * the current thread already holds a specified lock:
     * <pre>
     *     assert Thread.holdsLock(obj);
     * </pre>
     *
     * <p>
     *  当且仅当当前线程将监视器锁保留在指定对象上时,返回<tt> true </tt>。
     * 
     *  <p>此方法旨在允许程序断言当前线程已保存指定的锁：
     * <pre>
     *  assert Thread.holdsLock(obj);
     * </pre>
     * 
     * 
     * @param  obj the object on which to test lock ownership
     * @throws NullPointerException if obj is <tt>null</tt>
     * @return <tt>true</tt> if the current thread holds the monitor lock on
     *         the specified object.
     * @since 1.4
     */
    public static native boolean holdsLock(Object obj);

    private static final StackTraceElement[] EMPTY_STACK_TRACE
        = new StackTraceElement[0];

    /**
     * Returns an array of stack trace elements representing the stack dump
     * of this thread.  This method will return a zero-length array if
     * this thread has not started, has started but has not yet been
     * scheduled to run by the system, or has terminated.
     * If the returned array is of non-zero length then the first element of
     * the array represents the top of the stack, which is the most recent
     * method invocation in the sequence.  The last element of the array
     * represents the bottom of the stack, which is the least recent method
     * invocation in the sequence.
     *
     * <p>If there is a security manager, and this thread is not
     * the current thread, then the security manager's
     * <tt>checkPermission</tt> method is called with a
     * <tt>RuntimePermission("getStackTrace")</tt> permission
     * to see if it's ok to get the stack trace.
     *
     * <p>Some virtual machines may, under some circumstances, omit one
     * or more stack frames from the stack trace.  In the extreme case,
     * a virtual machine that has no stack trace information concerning
     * this thread is permitted to return a zero-length array from this
     * method.
     *
     * <p>
     *  返回表示此线程的堆栈转储的堆栈跟踪元素的数组。如果此线程尚未启动,已启动但尚未安排由系统运行或已终止,则此方法将返回零长度数组。
     * 如果返回的数组是非零长度,则数组的第一个元素表示堆栈的顶部,这是序列中最近的方法调用。数组的最后一个元素表示堆栈的底部,这是序列中最近一次的方法调用。
     * 
     *  <p>如果有安全管理员,且此线程不是当前线程,则会使用<tt> RuntimePermission("getStackTrace")</tt>权限调用安全管理器的<tt> checkPermissio
     * n </tt>看看是否可以获取堆栈跟踪。
     * 
     * <p>在某些情况下,一些虚拟机可能会从堆栈跟踪中省略一个或多个堆栈帧。在极端情况下,允许没有关于此线程的堆栈跟踪信息的虚拟机从该方法返回零长度数组。
     * 
     * 
     * @return an array of <tt>StackTraceElement</tt>,
     * each represents one stack frame.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        <tt>checkPermission</tt> method doesn't allow
     *        getting the stack trace of thread.
     * @see SecurityManager#checkPermission
     * @see RuntimePermission
     * @see Throwable#getStackTrace
     *
     * @since 1.5
     */
    public StackTraceElement[] getStackTrace() {
        if (this != Thread.currentThread()) {
            // check for getStackTrace permission
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkPermission(
                    SecurityConstants.GET_STACK_TRACE_PERMISSION);
            }
            // optimization so we do not call into the vm for threads that
            // have not yet started or have terminated
            if (!isAlive()) {
                return EMPTY_STACK_TRACE;
            }
            StackTraceElement[][] stackTraceArray = dumpThreads(new Thread[] {this});
            StackTraceElement[] stackTrace = stackTraceArray[0];
            // a thread that was alive during the previous isAlive call may have
            // since terminated, therefore not having a stacktrace.
            if (stackTrace == null) {
                stackTrace = EMPTY_STACK_TRACE;
            }
            return stackTrace;
        } else {
            // Don't need JVM help for current thread
            return (new Exception()).getStackTrace();
        }
    }

    /**
     * Returns a map of stack traces for all live threads.
     * The map keys are threads and each map value is an array of
     * <tt>StackTraceElement</tt> that represents the stack dump
     * of the corresponding <tt>Thread</tt>.
     * The returned stack traces are in the format specified for
     * the {@link #getStackTrace getStackTrace} method.
     *
     * <p>The threads may be executing while this method is called.
     * The stack trace of each thread only represents a snapshot and
     * each stack trace may be obtained at different time.  A zero-length
     * array will be returned in the map value if the virtual machine has
     * no stack trace information about a thread.
     *
     * <p>If there is a security manager, then the security manager's
     * <tt>checkPermission</tt> method is called with a
     * <tt>RuntimePermission("getStackTrace")</tt> permission as well as
     * <tt>RuntimePermission("modifyThreadGroup")</tt> permission
     * to see if it is ok to get the stack trace of all threads.
     *
     * <p>
     *  返回所有活动线程的堆栈跟踪的映射。映射键是线程,每个映射值是<tt> StackTraceElement </tt>的数组,表示相应<tt> Thread </tt>的堆栈转储。
     * 返回的堆栈跟踪采用为{@link #getStackTrace getStackTrace}方法指定的格式。
     * 
     *  <p>在调用此方法时,线程可能正在执行。每个线程的堆栈跟踪仅表示快照,并且可以在不同时间获得每个堆栈跟踪。如果虚拟机没有有关线程的堆栈跟踪信息,则将在映射值中返回零长度数组。
     * 
     *  <p>如果有安全管理员,则会使用<tt> RuntimePermission("getStackTrace")</tt>权限以及<tt> RuntimePermission("RuntimePermi
     * ssion")调用安全管理器的<tt> checkPermission </tt> modifyThreadGroup")</tt>权限,看看是否可以获取所有线程的堆栈跟踪。
     * 
     * 
     * @return a <tt>Map</tt> from <tt>Thread</tt> to an array of
     * <tt>StackTraceElement</tt> that represents the stack trace of
     * the corresponding thread.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        <tt>checkPermission</tt> method doesn't allow
     *        getting the stack trace of thread.
     * @see #getStackTrace
     * @see SecurityManager#checkPermission
     * @see RuntimePermission
     * @see Throwable#getStackTrace
     *
     * @since 1.5
     */
    public static Map<Thread, StackTraceElement[]> getAllStackTraces() {
        // check for getStackTrace permission
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(
                SecurityConstants.GET_STACK_TRACE_PERMISSION);
            security.checkPermission(
                SecurityConstants.MODIFY_THREADGROUP_PERMISSION);
        }

        // Get a snapshot of the list of all threads
        Thread[] threads = getThreads();
        StackTraceElement[][] traces = dumpThreads(threads);
        Map<Thread, StackTraceElement[]> m = new HashMap<>(threads.length);
        for (int i = 0; i < threads.length; i++) {
            StackTraceElement[] stackTrace = traces[i];
            if (stackTrace != null) {
                m.put(threads[i], stackTrace);
            }
            // else terminated so we don't put it in the map
        }
        return m;
    }


    private static final RuntimePermission SUBCLASS_IMPLEMENTATION_PERMISSION =
                    new RuntimePermission("enableContextClassLoaderOverride");

    /** cache of subclass security audit results */
    /* Replace with ConcurrentReferenceHashMap when/if it appears in a future
    /* <p>
    /* 
     * release */
    private static class Caches {
        /** cache of subclass security audit results */
        static final ConcurrentMap<WeakClassKey,Boolean> subclassAudits =
            new ConcurrentHashMap<>();

        /** queue for WeakReferences to audited subclasses */
        static final ReferenceQueue<Class<?>> subclassAuditsQueue =
            new ReferenceQueue<>();
    }

    /**
     * Verifies that this (possibly subclass) instance can be constructed
     * without violating security constraints: the subclass must not override
     * security-sensitive non-final methods, or else the
     * "enableContextClassLoaderOverride" RuntimePermission is checked.
     * <p>
     *  验证这个(可能是子类)实例可以在不违反安全约束的情况下构造：子类不能覆盖安全敏感的非最终方法,否则将检查"enableContextClassLoaderOverride"RuntimePermiss
     * ion。
     * 
     */
    private static boolean isCCLOverridden(Class<?> cl) {
        if (cl == Thread.class)
            return false;

        processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
        WeakClassKey key = new WeakClassKey(cl, Caches.subclassAuditsQueue);
        Boolean result = Caches.subclassAudits.get(key);
        if (result == null) {
            result = Boolean.valueOf(auditSubclass(cl));
            Caches.subclassAudits.putIfAbsent(key, result);
        }

        return result.booleanValue();
    }

    /**
     * Performs reflective checks on given subclass to verify that it doesn't
     * override security-sensitive non-final methods.  Returns true if the
     * subclass overrides any of the methods, false otherwise.
     * <p>
     * 对给定的子类执行反射检查以验证它不覆盖安全敏感的非最终方法。如果子类覆盖任何方法,则返回true,否则返回false。
     * 
     */
    private static boolean auditSubclass(final Class<?> subcl) {
        Boolean result = AccessController.doPrivileged(
            new PrivilegedAction<Boolean>() {
                public Boolean run() {
                    for (Class<?> cl = subcl;
                         cl != Thread.class;
                         cl = cl.getSuperclass())
                    {
                        try {
                            cl.getDeclaredMethod("getContextClassLoader", new Class<?>[0]);
                            return Boolean.TRUE;
                        } catch (NoSuchMethodException ex) {
                        }
                        try {
                            Class<?>[] params = {ClassLoader.class};
                            cl.getDeclaredMethod("setContextClassLoader", params);
                            return Boolean.TRUE;
                        } catch (NoSuchMethodException ex) {
                        }
                    }
                    return Boolean.FALSE;
                }
            }
        );
        return result.booleanValue();
    }

    private native static StackTraceElement[][] dumpThreads(Thread[] threads);
    private native static Thread[] getThreads();

    /**
     * Returns the identifier of this Thread.  The thread ID is a positive
     * <tt>long</tt> number generated when this thread was created.
     * The thread ID is unique and remains unchanged during its lifetime.
     * When a thread is terminated, this thread ID may be reused.
     *
     * <p>
     *  返回此Thread的标识符。线程ID是创建此线程时生成的正<tt>长</tt>数字。线程ID是唯一的,在其生命周期中保持不变。当线程被终止时,该线程ID可以被重用。
     * 
     * 
     * @return this thread's ID.
     * @since 1.5
     */
    public long getId() {
        return tid;
    }

    /**
     * A thread state.  A thread can be in one of the following states:
     * <ul>
     * <li>{@link #NEW}<br>
     *     A thread that has not yet started is in this state.
     *     </li>
     * <li>{@link #RUNNABLE}<br>
     *     A thread executing in the Java virtual machine is in this state.
     *     </li>
     * <li>{@link #BLOCKED}<br>
     *     A thread that is blocked waiting for a monitor lock
     *     is in this state.
     *     </li>
     * <li>{@link #WAITING}<br>
     *     A thread that is waiting indefinitely for another thread to
     *     perform a particular action is in this state.
     *     </li>
     * <li>{@link #TIMED_WAITING}<br>
     *     A thread that is waiting for another thread to perform an action
     *     for up to a specified waiting time is in this state.
     *     </li>
     * <li>{@link #TERMINATED}<br>
     *     A thread that has exited is in this state.
     *     </li>
     * </ul>
     *
     * <p>
     * A thread can be in only one state at a given point in time.
     * These states are virtual machine states which do not reflect
     * any operating system thread states.
     *
     * <p>
     *  线程状态。线程可以处于以下状态之一：
     * <ul>
     *  <li> {@ link #NEW} <br>尚未启动的主题处于此状态。
     * </li>
     *  <li> {@ link #RUNNABLE} <br>在Java虚拟机中执行的线程处于此状态。
     * </li>
     *  <li> {@ link #BLOCKED} <br>被阻止等待监视器锁定的线程处于此状态。
     * </li>
     *  <li> {@ link #WAITING} <br>无限期等待另一个线程执行特定操作的线程处于此状态。
     * </li>
     *  <li> {@ link #TIMED_WAITING} <br>等待另一个线程执行某个操作达到指定等待时间的线程处于此状态。
     * </li>
     *  <li> {@ link #TERMINATED} <br>已退出的主题处于此状态。
     * </li>
     * </ul>
     * 
     * <p>
     *  线程在给定的时间点只能处于一个状态。这些状态是不反映任何操作系统线程状态的虚拟机状态。
     * 
     * 
     * @since   1.5
     * @see #getState
     */
    public enum State {
        /**
         * Thread state for a thread which has not yet started.
         * <p>
         *  尚未启动的线程的线程状态。
         * 
         */
        NEW,

        /**
         * Thread state for a runnable thread.  A thread in the runnable
         * state is executing in the Java virtual machine but it may
         * be waiting for other resources from the operating system
         * such as processor.
         * <p>
         * 可运行线程的线程状态。处于可运行状态的线程正在Java虚拟机中执行,但它可能正在等待来自操作系统的其他资源,例如处理器。
         * 
         */
        RUNNABLE,

        /**
         * Thread state for a thread blocked waiting for a monitor lock.
         * A thread in the blocked state is waiting for a monitor lock
         * to enter a synchronized block/method or
         * reenter a synchronized block/method after calling
         * {@link Object#wait() Object.wait}.
         * <p>
         *  线程阻塞等待监视器锁的线程状态。处于阻塞状态的线程在调用{@link Object#wait()Object.wait}后等待监视器锁进入同步块/方法或重新输入同步块/方法。
         * 
         */
        BLOCKED,

        /**
         * Thread state for a waiting thread.
         * A thread is in the waiting state due to calling one of the
         * following methods:
         * <ul>
         *   <li>{@link Object#wait() Object.wait} with no timeout</li>
         *   <li>{@link #join() Thread.join} with no timeout</li>
         *   <li>{@link LockSupport#park() LockSupport.park}</li>
         * </ul>
         *
         * <p>A thread in the waiting state is waiting for another thread to
         * perform a particular action.
         *
         * For example, a thread that has called <tt>Object.wait()</tt>
         * on an object is waiting for another thread to call
         * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
         * that object. A thread that has called <tt>Thread.join()</tt>
         * is waiting for a specified thread to terminate.
         * <p>
         *  等待线程的线程状态。线程由于调用以下方法之一而处于等待状态：
         * <ul>
         *  <li> {@ link Object#wait()Object.wait}没有超时</li> <li> {@ link #join()Thread.join}没有超时</li> <li> {@ link LockSupport #park()LockSupport.park}
         *  </li>。
         * </ul>
         * 
         *  <p>处于等待状态的线程正在等待另一个线程执行特定操作。
         * 
         *  例如,在对象上调用<tt> Object.wait()</tt>的线程正在等待另一个线程调用<tt> Object.notify()</tt>或<tt> Object.notifyAll )</tt>
         * 。
         * 调用<tt> Thread.join()</tt>的线程正在等待指定的线程终止。
         * 
         */
        WAITING,

        /**
         * Thread state for a waiting thread with a specified waiting time.
         * A thread is in the timed waiting state due to calling one of
         * the following methods with a specified positive waiting time:
         * <ul>
         *   <li>{@link #sleep Thread.sleep}</li>
         *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
         *   <li>{@link #join(long) Thread.join} with timeout</li>
         *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
         *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
         * </ul>
         * <p>
         *  具有指定等待时间的等待线程的线程状态。由于调用指定正等待时间的以下方法之一,线程处于定时等待状态：
         * <ul>
         * <li> {@ link #sleep Thread.sleep} </li> <li> {@ link Object#wait(long)Object.wait} with timeout </li>
         *  <li> {@ link #join .join} with timeout </li> <li> {@ link LockSupport#parkNanos LockSupport.parkNanos}
         *  </li> <li> {@ link LockSupport#parkUntil LockSupport.parkUntil}。
         * </ul>
         */
        TIMED_WAITING,

        /**
         * Thread state for a terminated thread.
         * The thread has completed execution.
         * <p>
         *  终止线程的线程状态。线程已完成执行。
         * 
         */
        TERMINATED;
    }

    /**
     * Returns the state of this thread.
     * This method is designed for use in monitoring of the system state,
     * not for synchronization control.
     *
     * <p>
     *  返回此线程的状态。此方法设计用于监视系统状态,而不是用于同步控制。
     * 
     * 
     * @return this thread's state.
     * @since 1.5
     */
    public State getState() {
        // get current thread state
        return sun.misc.VM.toThreadState(threadStatus);
    }

    // Added in JSR-166

    /**
     * Interface for handlers invoked when a <tt>Thread</tt> abruptly
     * terminates due to an uncaught exception.
     * <p>When a thread is about to terminate due to an uncaught exception
     * the Java Virtual Machine will query the thread for its
     * <tt>UncaughtExceptionHandler</tt> using
     * {@link #getUncaughtExceptionHandler} and will invoke the handler's
     * <tt>uncaughtException</tt> method, passing the thread and the
     * exception as arguments.
     * If a thread has not had its <tt>UncaughtExceptionHandler</tt>
     * explicitly set, then its <tt>ThreadGroup</tt> object acts as its
     * <tt>UncaughtExceptionHandler</tt>. If the <tt>ThreadGroup</tt> object
     * has no
     * special requirements for dealing with the exception, it can forward
     * the invocation to the {@linkplain #getDefaultUncaughtExceptionHandler
     * default uncaught exception handler}.
     *
     * <p>
     *  当<tt> Thread </tt>由于未捕获异常而突然终止时调用的处理程序接口。
     *  <p>当线程由于未捕获异常而即将终止时,Java虚拟机将使用{@link #getUncaughtExceptionHandler}查询线程的<tt> UncaughtExceptionHandler
     *  </tt>,并将调用处理程序的<tt> uncaughtException < / tt>方法,传递线程和异常作为参数。
     *  当<tt> Thread </tt>由于未捕获异常而突然终止时调用的处理程序接口。
     * 如果一个线程没有明确设置它的<tt> UncaughtExceptionHandler </tt>,那么它的<tt> ThreadGroup </tt>对象作为<tt> UncaughtExceptio
     * nHandler </tt>。
     *  当<tt> Thread </tt>由于未捕获异常而突然终止时调用的处理程序接口。
     * 如果<tt> ThreadGroup </tt>对象没有处理异常的特殊要求,它可以将调用转发到{@linkplain #getDefaultUncaughtExceptionHandler默认未捕获异常处理程序}
     * 。
     *  当<tt> Thread </tt>由于未捕获异常而突然终止时调用的处理程序接口。
     * 
     * 
     * @see #setDefaultUncaughtExceptionHandler
     * @see #setUncaughtExceptionHandler
     * @see ThreadGroup#uncaughtException
     * @since 1.5
     */
    @FunctionalInterface
    public interface UncaughtExceptionHandler {
        /**
         * Method invoked when the given thread terminates due to the
         * given uncaught exception.
         * <p>Any exception thrown by this method will be ignored by the
         * Java Virtual Machine.
         * <p>
         *  由于给定的未捕获异常,当给定线程终止时调用方法。 <p>此方法抛出的任何异常都将被Java虚拟机忽略。
         * 
         * 
         * @param t the thread
         * @param e the exception
         */
        void uncaughtException(Thread t, Throwable e);
    }

    // null unless explicitly set
    private volatile UncaughtExceptionHandler uncaughtExceptionHandler;

    // null unless explicitly set
    private static volatile UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    /**
     * Set the default handler invoked when a thread abruptly terminates
     * due to an uncaught exception, and no other handler has been defined
     * for that thread.
     *
     * <p>Uncaught exception handling is controlled first by the thread, then
     * by the thread's {@link ThreadGroup} object and finally by the default
     * uncaught exception handler. If the thread does not have an explicit
     * uncaught exception handler set, and the thread's thread group
     * (including parent thread groups)  does not specialize its
     * <tt>uncaughtException</tt> method, then the default handler's
     * <tt>uncaughtException</tt> method will be invoked.
     * <p>By setting the default uncaught exception handler, an application
     * can change the way in which uncaught exceptions are handled (such as
     * logging to a specific device, or file) for those threads that would
     * already accept whatever &quot;default&quot; behavior the system
     * provided.
     *
     * <p>Note that the default uncaught exception handler should not usually
     * defer to the thread's <tt>ThreadGroup</tt> object, as that could cause
     * infinite recursion.
     *
     * <p>
     * 设置当线程由于未捕获异常而突然终止并且没有为该线程定义其他处理程序时调用的默认处理程序。
     * 
     *  <p>未捕获的异常处理首先由线程控制,然后由线程的{@link ThreadGroup}对象控制,最后由默认未捕获异常处理程序控制。
     * 如果线程没有明确的未捕获异常处理程序集,并且线程的线程组(包括父线程组)不专门化其<tt> uncaughtException </tt>方法,则缺省处理程序的<tt> uncaughtExceptio
     * n </tt>方法将被调用。
     *  <p>未捕获的异常处理首先由线程控制,然后由线程的{@link ThreadGroup}对象控制,最后由默认未捕获异常处理程序控制。
     * 通过设置默认未捕获异常处理程序,应用程序可以改变对于已经接受任何"默认"异常处理的那些线程处理未捕获异常的方式(诸如记录到特定设备或文件)。系统提供的行为。
     * 
     *  <p>请注意,默认的未捕获异常处理程序通常不应处理线程的<tt> ThreadGroup </tt>对象,因为这可能会导致无限递归。
     * 
     * 
     * @param eh the object to use as the default uncaught exception handler.
     * If <tt>null</tt> then there is no default handler.
     *
     * @throws SecurityException if a security manager is present and it
     *         denies <tt>{@link RuntimePermission}
     *         (&quot;setDefaultUncaughtExceptionHandler&quot;)</tt>
     *
     * @see #setUncaughtExceptionHandler
     * @see #getUncaughtExceptionHandler
     * @see ThreadGroup#uncaughtException
     * @since 1.5
     */
    public static void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(
                new RuntimePermission("setDefaultUncaughtExceptionHandler")
                    );
        }

         defaultUncaughtExceptionHandler = eh;
     }

    /**
     * Returns the default handler invoked when a thread abruptly terminates
     * due to an uncaught exception. If the returned value is <tt>null</tt>,
     * there is no default.
     * <p>
     *  返回当线程因未捕获异常而突然终止时调用的默认处理程序。如果返回的值为<tt> null </tt>,则没有默认值。
     * 
     * 
     * @since 1.5
     * @see #setDefaultUncaughtExceptionHandler
     * @return the default uncaught exception handler for all threads
     */
    public static UncaughtExceptionHandler getDefaultUncaughtExceptionHandler(){
        return defaultUncaughtExceptionHandler;
    }

    /**
     * Returns the handler invoked when this thread abruptly terminates
     * due to an uncaught exception. If this thread has not had an
     * uncaught exception handler explicitly set then this thread's
     * <tt>ThreadGroup</tt> object is returned, unless this thread
     * has terminated, in which case <tt>null</tt> is returned.
     * <p>
     * 返回当此线程由于未捕获异常而突然终止时调用的处理程序。
     * 如果这个线程没有显式设置未捕获的异常处理程序,则返回此线程的<tt> ThreadGroup </tt>对象,除非该线程已终止,在这种情况下返回<tt> null </tt>。
     * 
     * 
     * @since 1.5
     * @return the uncaught exception handler for this thread
     */
    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return uncaughtExceptionHandler != null ?
            uncaughtExceptionHandler : group;
    }

    /**
     * Set the handler invoked when this thread abruptly terminates
     * due to an uncaught exception.
     * <p>A thread can take full control of how it responds to uncaught
     * exceptions by having its uncaught exception handler explicitly set.
     * If no such handler is set then the thread's <tt>ThreadGroup</tt>
     * object acts as its handler.
     * <p>
     *  设置当此线程由于未捕获异常而突然终止时调用的处理程序。 <p>线程可以完全控制它如何通过显式设置未捕获的异常处理程序来响应未捕获的异常。
     * 如果没有设置这样的处理程序,则线程的<tt> ThreadGroup </tt>对象充当其处理程序。
     * 
     * 
     * @param eh the object to use as this thread's uncaught exception
     * handler. If <tt>null</tt> then this thread has no explicit handler.
     * @throws  SecurityException  if the current thread is not allowed to
     *          modify this thread.
     * @see #setDefaultUncaughtExceptionHandler
     * @see ThreadGroup#uncaughtException
     * @since 1.5
     */
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler eh) {
        checkAccess();
        uncaughtExceptionHandler = eh;
    }

    /**
     * Dispatch an uncaught exception to the handler. This method is
     * intended to be called only by the JVM.
     * <p>
     *  将未捕获的异常分派给处理程序。此方法旨在仅由JVM调用。
     * 
     */
    private void dispatchUncaughtException(Throwable e) {
        getUncaughtExceptionHandler().uncaughtException(this, e);
    }

    /**
     * Removes from the specified map any keys that have been enqueued
     * on the specified reference queue.
     * <p>
     *  从指定的映射中删除已在指定引用队列中入队的任何密钥。
     * 
     */
    static void processQueue(ReferenceQueue<Class<?>> queue,
                             ConcurrentMap<? extends
                             WeakReference<Class<?>>, ?> map)
    {
        Reference<? extends Class<?>> ref;
        while((ref = queue.poll()) != null) {
            map.remove(ref);
        }
    }

    /**
     *  Weak key for Class objects.
     * <p>
     *  类对象的弱键。
     * 
     * 
     **/
    static class WeakClassKey extends WeakReference<Class<?>> {
        /**
         * saved value of the referent's identity hash code, to maintain
         * a consistent hash code after the referent has been cleared
         * <p>
         *  保存引用对象的标识哈希码的值,以在引用对象被清除后保持一致的哈希码
         * 
         */
        private final int hash;

        /**
         * Create a new WeakClassKey to the given object, registered
         * with a queue.
         * <p>
         *  创建一个新的WeakClassKey给给定的对象,注册一个队列。
         * 
         */
        WeakClassKey(Class<?> cl, ReferenceQueue<Class<?>> refQueue) {
            super(cl, refQueue);
            hash = System.identityHashCode(cl);
        }

        /**
         * Returns the identity hash code of the original referent.
         * <p>
         *  返回原始指示对象的标识哈希码。
         * 
         */
        @Override
        public int hashCode() {
            return hash;
        }

        /**
         * Returns true if the given object is this identical
         * WeakClassKey instance, or, if this object's referent has not
         * been cleared, if the given object is another WeakClassKey
         * instance with the identical non-null referent as this one.
         * <p>
         *  如果给定的对象是这个相同的WeakClassKey实例,或者如果该对象的指示未被清除,则返回true,如果给定对象是具有与此相同的非空指示符的另一个WeakClassKey实例。
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;

            if (obj instanceof WeakClassKey) {
                Object referent = get();
                return (referent != null) &&
                       (referent == ((WeakClassKey) obj).get());
            } else {
                return false;
            }
        }
    }


    // The following three initially uninitialized fields are exclusively
    // managed by class java.util.concurrent.ThreadLocalRandom. These
    // fields are used to build the high-performance PRNGs in the
    // concurrent code, and we can not risk accidental false sharing.
    // Hence, the fields are isolated with @Contended.

    /** The current seed for a ThreadLocalRandom */
    @sun.misc.Contended("tlr")
    long threadLocalRandomSeed;

    /** Probe hash value; nonzero if threadLocalRandomSeed initialized */
    @sun.misc.Contended("tlr")
    int threadLocalRandomProbe;

    /** Secondary seed isolated from public ThreadLocalRandom sequence */
    @sun.misc.Contended("tlr")
    int threadLocalRandomSecondarySeed;

    /* Some private helper methods */
    private native void setPriority0(int newPriority);
    private native void stop0(Object o);
    private native void suspend0();
    private native void resume0();
    private native void interrupt0();
    private native void setNativeName(String name);
}
