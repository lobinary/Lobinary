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

import java.io.*;
import java.lang.reflect.Executable;
import java.lang.annotation.Annotation;
import java.security.AccessControlContext;
import java.util.Properties;
import java.util.PropertyPermission;
import java.util.StringTokenizer;
import java.util.Map;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.AllPermission;
import java.nio.channels.Channel;
import java.nio.channels.spi.SelectorProvider;
import sun.nio.ch.Interruptible;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.security.util.SecurityConstants;
import sun.reflect.annotation.AnnotationType;

/**
 * The <code>System</code> class contains several useful class fields
 * and methods. It cannot be instantiated.
 *
 * <p>Among the facilities provided by the <code>System</code> class
 * are standard input, standard output, and error output streams;
 * access to externally defined properties and environment
 * variables; a means of loading files and libraries; and a utility
 * method for quickly copying a portion of an array.
 *
 * <p>
 *  <code> System </code>类包含几个有用的类字段和方法它不能被实例化
 * 
 *  <p> <code> System </code>类提供的工具包括标准输入,标准输出和错误输出流;访问外部定义的属性和环境变量;加载文件和库的方法;以及用于快速复制数组的一部分的实用方法
 * 
 * 
 * @author  unascribed
 * @since   JDK1.0
 */
public final class System {

    /* register the natives via the static initializer.
     *
     * VM will invoke the initializeSystemClass method to complete
     * the initialization for this class separated from clinit.
     * Note that to use properties set by the VM, see the constraints
     * described in the initializeSystemClass method.
     * <p>
     *  VM将调用initializeSystemClass方法来完成从clinit分离的此类的初始化请注意,要使用由VM设置的属性,请参阅initializeSystemClass方法中描述的约束
     * 
     */
    private static native void registerNatives();
    static {
        registerNatives();
    }

    /** Don't let anyone instantiate this class */
    private System() {
    }

    /**
     * The "standard" input stream. This stream is already
     * open and ready to supply input data. Typically this stream
     * corresponds to keyboard input or another input source specified by
     * the host environment or user.
     * <p>
     * "标准"输入流此流已经打开并准备提供输入数据通常此流对应于键盘输入或由主机环境或用户指定的另一个输入源
     * 
     */
    public final static InputStream in = null;

    /**
     * The "standard" output stream. This stream is already
     * open and ready to accept output data. Typically this stream
     * corresponds to display output or another output destination
     * specified by the host environment or user.
     * <p>
     * For simple stand-alone Java applications, a typical way to write
     * a line of output data is:
     * <blockquote><pre>
     *     System.out.println(data)
     * </pre></blockquote>
     * <p>
     * See the <code>println</code> methods in class <code>PrintStream</code>.
     *
     * <p>
     *  "标准"输出流此流已打开并准备接受输出数据通常此流对应于显示输出或由主机环境或用户指定的另一个输出目标
     * <p>
     *  对于简单的独立Java应用程序,写一行输出数据的典型方法是：<blockquote> <pre> Systemoutprintln(data)</pre> </blockquote>
     * <p>
     *  参见<code> PrintStream </code>类中的<code> println </code>方法
     * 
     * 
     * @see     java.io.PrintStream#println()
     * @see     java.io.PrintStream#println(boolean)
     * @see     java.io.PrintStream#println(char)
     * @see     java.io.PrintStream#println(char[])
     * @see     java.io.PrintStream#println(double)
     * @see     java.io.PrintStream#println(float)
     * @see     java.io.PrintStream#println(int)
     * @see     java.io.PrintStream#println(long)
     * @see     java.io.PrintStream#println(java.lang.Object)
     * @see     java.io.PrintStream#println(java.lang.String)
     */
    public final static PrintStream out = null;

    /**
     * The "standard" error output stream. This stream is already
     * open and ready to accept output data.
     * <p>
     * Typically this stream corresponds to display output or another
     * output destination specified by the host environment or user. By
     * convention, this output stream is used to display error messages
     * or other information that should come to the immediate attention
     * of a user even if the principal output stream, the value of the
     * variable <code>out</code>, has been redirected to a file or other
     * destination that is typically not continuously monitored.
     * <p>
     *  "标准"错误输出流此流已打开并准备好接受输出数据
     * <p>
     * 通常,该流对应于显示输出或由主机环境或用户指定的另一输出目的。
     * 按照惯例,该输出流用于显示应该立即引起用户注意的错误消息或其他信息,即使主输出流,变量<code> out </code>的值已被重定向到文件或其他目标,通常不会被连续监视。
     * 
     */
    public final static PrintStream err = null;

    /* The security manager for the system.
    /* <p>
     */
    private static volatile SecurityManager security = null;

    /**
     * Reassigns the "standard" input stream.
     *
     * <p>First, if there is a security manager, its <code>checkPermission</code>
     * method is called with a <code>RuntimePermission("setIO")</code> permission
     *  to see if it's ok to reassign the "standard" input stream.
     * <p>
     *
     * <p>
     *  重新分配"标准"输入流
     * 
     *  <p>首先,如果有安全管理器,则使用<code> RuntimePermission("setIO")</code>权限调用其<code> checkPermission </code>方法,以查看是
     * 否可以重新分配" "输入流。
     * <p>
     * 
     * 
     * @param in the new standard input stream.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        <code>checkPermission</code> method doesn't allow
     *        reassigning of the standard input stream.
     *
     * @see SecurityManager#checkPermission
     * @see java.lang.RuntimePermission
     *
     * @since   JDK1.1
     */
    public static void setIn(InputStream in) {
        checkIO();
        setIn0(in);
    }

    /**
     * Reassigns the "standard" output stream.
     *
     * <p>First, if there is a security manager, its <code>checkPermission</code>
     * method is called with a <code>RuntimePermission("setIO")</code> permission
     *  to see if it's ok to reassign the "standard" output stream.
     *
     * <p>
     *  重新分配"标准"输出流
     * 
     * <p>首先,如果有安全管理器,则使用<code> RuntimePermission("setIO")</code>权限调用其<code> checkPermission </code>方法,以查看是否
     * 可以重新分配" "输出流。
     * 
     * 
     * @param out the new standard output stream
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        <code>checkPermission</code> method doesn't allow
     *        reassigning of the standard output stream.
     *
     * @see SecurityManager#checkPermission
     * @see java.lang.RuntimePermission
     *
     * @since   JDK1.1
     */
    public static void setOut(PrintStream out) {
        checkIO();
        setOut0(out);
    }

    /**
     * Reassigns the "standard" error output stream.
     *
     * <p>First, if there is a security manager, its <code>checkPermission</code>
     * method is called with a <code>RuntimePermission("setIO")</code> permission
     *  to see if it's ok to reassign the "standard" error output stream.
     *
     * <p>
     *  重新分配"标准"错误输出流
     * 
     *  <p>首先,如果有安全管理器,则使用<code> RuntimePermission("setIO")</code>权限调用其<code> checkPermission </code>方法,以查看是
     * 否可以重新分配" "错误输出流。
     * 
     * 
     * @param err the new standard error output stream.
     *
     * @throws SecurityException
     *        if a security manager exists and its
     *        <code>checkPermission</code> method doesn't allow
     *        reassigning of the standard error output stream.
     *
     * @see SecurityManager#checkPermission
     * @see java.lang.RuntimePermission
     *
     * @since   JDK1.1
     */
    public static void setErr(PrintStream err) {
        checkIO();
        setErr0(err);
    }

    private static volatile Console cons = null;
    /**
     * Returns the unique {@link java.io.Console Console} object associated
     * with the current Java virtual machine, if any.
     *
     * <p>
     *  返回与当前Java虚拟机关联的唯一{@link javaioConsole Console}对象(如果有)
     * 
     * 
     * @return  The system console, if any, otherwise <tt>null</tt>.
     *
     * @since   1.6
     */
     public static Console console() {
         if (cons == null) {
             synchronized (System.class) {
                 cons = sun.misc.SharedSecrets.getJavaIOAccess().console();
             }
         }
         return cons;
     }

    /**
     * Returns the channel inherited from the entity that created this
     * Java virtual machine.
     *
     * <p> This method returns the channel obtained by invoking the
     * {@link java.nio.channels.spi.SelectorProvider#inheritedChannel
     * inheritedChannel} method of the system-wide default
     * {@link java.nio.channels.spi.SelectorProvider} object. </p>
     *
     * <p> In addition to the network-oriented channels described in
     * {@link java.nio.channels.spi.SelectorProvider#inheritedChannel
     * inheritedChannel}, this method may return other kinds of
     * channels in the future.
     *
     * <p>
     *  返回从创建此Java虚拟机的实体继承的通道
     * 
     * <p>此方法返回通过调用系统范围默认{@link javaniochannelsspiSelectorProvider}对象的{@link javaniochannelsspiSelectorProvider#inheritedChannel inheritedChannel}
     * 方法获得的渠道</p>。
     * 
     *  <p>除了{@link javaniochannelsspiSelectorProvider#inheritedChannel inheritedChannel}中描述的面向网络的频道,此方法可能会在
     * 未来返回其他类型的频道。
     * 
     * 
     * @return  The inherited channel, if any, otherwise <tt>null</tt>.
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  SecurityException
     *          If a security manager is present and it does not
     *          permit access to the channel.
     *
     * @since 1.5
     */
    public static Channel inheritedChannel() throws IOException {
        return SelectorProvider.provider().inheritedChannel();
    }

    private static void checkIO() {
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("setIO"));
        }
    }

    private static native void setIn0(InputStream in);
    private static native void setOut0(PrintStream out);
    private static native void setErr0(PrintStream err);

    /**
     * Sets the System security.
     *
     * <p> If there is a security manager already installed, this method first
     * calls the security manager's <code>checkPermission</code> method
     * with a <code>RuntimePermission("setSecurityManager")</code>
     * permission to ensure it's ok to replace the existing
     * security manager.
     * This may result in throwing a <code>SecurityException</code>.
     *
     * <p> Otherwise, the argument is established as the current
     * security manager. If the argument is <code>null</code> and no
     * security manager has been established, then no action is taken and
     * the method simply returns.
     *
     * <p>
     *  设置系统安全
     * 
     * <p>如果已经安装了安全管理器,此方法首先使用<code> RuntimePermission("setSecurityManager")</code>权限调用安全管理器的<code> checkPer
     * mission </code>方法,现有的安全管理器这可能会导致抛出一个<code> SecurityException </code>。
     * 
     *  <p>否则,参数被建立为当前安全管理器如果参数是<code> null </code>,并且没有建立安全管理器,则不采取任何动作,该方法简单地返回
     * 
     * 
     * @param      s   the security manager.
     * @exception  SecurityException  if the security manager has already
     *             been set and its <code>checkPermission</code> method
     *             doesn't allow it to be replaced.
     * @see #getSecurityManager
     * @see SecurityManager#checkPermission
     * @see java.lang.RuntimePermission
     */
    public static
    void setSecurityManager(final SecurityManager s) {
        try {
            s.checkPackageAccess("java.lang");
        } catch (Exception e) {
            // no-op
        }
        setSecurityManager0(s);
    }

    private static synchronized
    void setSecurityManager0(final SecurityManager s) {
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            // ask the currently installed security manager if we
            // can replace it.
            sm.checkPermission(new RuntimePermission
                                     ("setSecurityManager"));
        }

        if ((s != null) && (s.getClass().getClassLoader() != null)) {
            // New security manager class is not on bootstrap classpath.
            // Cause policy to get initialized before we install the new
            // security manager, in order to prevent infinite loops when
            // trying to initialize the policy (which usually involves
            // accessing some security and/or system properties, which in turn
            // calls the installed security manager's checkPermission method
            // which will loop infinitely if there is a non-system class
            // (in this case: the new security manager class) on the stack).
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    s.getClass().getProtectionDomain().implies
                        (SecurityConstants.ALL_PERMISSION);
                    return null;
                }
            });
        }

        security = s;
    }

    /**
     * Gets the system security interface.
     *
     * <p>
     *  获取系统安全接口
     * 
     * 
     * @return  if a security manager has already been established for the
     *          current application, then that security manager is returned;
     *          otherwise, <code>null</code> is returned.
     * @see     #setSecurityManager
     */
    public static SecurityManager getSecurityManager() {
        return security;
    }

    /**
     * Returns the current time in milliseconds.  Note that
     * while the unit of time of the return value is a millisecond,
     * the granularity of the value depends on the underlying
     * operating system and may be larger.  For example, many
     * operating systems measure time in units of tens of
     * milliseconds.
     *
     * <p> See the description of the class <code>Date</code> for
     * a discussion of slight discrepancies that may arise between
     * "computer time" and coordinated universal time (UTC).
     *
     * <p>
     * 返回当前时间(以毫秒为单位)请注意,虽然返回值的时间单位为毫秒,但值的粒度取决于底层操作系统,并且可能更大例如,许多操作系统以几十毫秒为单位测量时间
     * 
     *  <p>有关<计算机时间>和协调世界时(UTC)之间可能出现的微小差异的讨论,请参阅<code> Date </code>
     * 
     * 
     * @return  the difference, measured in milliseconds, between
     *          the current time and midnight, January 1, 1970 UTC.
     * @see     java.util.Date
     */
    public static native long currentTimeMillis();

    /**
     * Returns the current value of the running Java Virtual Machine's
     * high-resolution time source, in nanoseconds.
     *
     * <p>This method can only be used to measure elapsed time and is
     * not related to any other notion of system or wall-clock time.
     * The value returned represents nanoseconds since some fixed but
     * arbitrary <i>origin</i> time (perhaps in the future, so values
     * may be negative).  The same origin is used by all invocations of
     * this method in an instance of a Java virtual machine; other
     * virtual machine instances are likely to use a different origin.
     *
     * <p>This method provides nanosecond precision, but not necessarily
     * nanosecond resolution (that is, how frequently the value changes)
     * - no guarantees are made except that the resolution is at least as
     * good as that of {@link #currentTimeMillis()}.
     *
     * <p>Differences in successive calls that span greater than
     * approximately 292 years (2<sup>63</sup> nanoseconds) will not
     * correctly compute elapsed time due to numerical overflow.
     *
     * <p>The values returned by this method become meaningful only when
     * the difference between two such values, obtained within the same
     * instance of a Java virtual machine, is computed.
     *
     * <p> For example, to measure how long some code takes to execute:
     *  <pre> {@code
     * long startTime = System.nanoTime();
     * // ... the code being measured ...
     * long estimatedTime = System.nanoTime() - startTime;}</pre>
     *
     * <p>To compare two nanoTime values
     *  <pre> {@code
     * long t0 = System.nanoTime();
     * ...
     * long t1 = System.nanoTime();}</pre>
     *
     * one should use {@code t1 - t0 < 0}, not {@code t1 < t0},
     * because of the possibility of numerical overflow.
     *
     * <p>
     *  返回正在运行的Java虚拟机的高分辨率时间源的当前值(以纳秒为单位)
     * 
     * <p>此方法只能用于测量经过的时间,与系统或挂钟时间的任何其他概念无关。
     * 返回的值表示纳秒,因为某些固定但任意的<i>原点时间(也许在未来,因此值可能为负)在Java虚拟机的实例中,此方法的所有调用使用相同的源;其他虚拟机实例可能使用不同的来源。
     * 
     *  <p>此方法提供纳秒精度,但不一定是纳秒分辨率(即值的变化频率) - 不保证除了分辨率至少与{@link #currentTimeMillis()}一样好
     * 
     * <p>跨越大于约292年(2 <sup> 63 </sup>纳秒)的连续调用中的差异将无法正确计算由于数值溢出而产生的经过时间
     * 
     *  <p>此方法返回的值只有在计算在Java虚拟机的同一实例内获得的两个这样的值之间的差异时才变得有意义
     * 
     *  <p>例如,要测量一些代码需要执行多长时间：<pre> {@code long startTime = SystemnanoTime(); //被测量的代码long estimatedTime = SystemnanoTime() -  startTime;}
     *  </pre>。
     * 
     *  <p>要比较两个nanoTime值<pre> {@code long t0 = SystemnanoTime(); long t1 = SystemnanoTime();} </pre>
     * 
     *  应该使用{@code t1  -  t0 <0},而不是{@code t1 <t0},因为数值溢出的可能性
     * 
     * 
     * @return the current value of the running Java Virtual Machine's
     *         high-resolution time source, in nanoseconds
     * @since 1.5
     */
    public static native long nanoTime();

    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * array referenced by <code>src</code> to the destination array
     * referenced by <code>dest</code>. The number of components copied is
     * equal to the <code>length</code> argument. The components at
     * positions <code>srcPos</code> through
     * <code>srcPos+length-1</code> in the source array are copied into
     * positions <code>destPos</code> through
     * <code>destPos+length-1</code>, respectively, of the destination
     * array.
     * <p>
     * If the <code>src</code> and <code>dest</code> arguments refer to the
     * same array object, then the copying is performed as if the
     * components at positions <code>srcPos</code> through
     * <code>srcPos+length-1</code> were first copied to a temporary
     * array with <code>length</code> components and then the contents of
     * the temporary array were copied into positions
     * <code>destPos</code> through <code>destPos+length-1</code> of the
     * destination array.
     * <p>
     * If <code>dest</code> is <code>null</code>, then a
     * <code>NullPointerException</code> is thrown.
     * <p>
     * If <code>src</code> is <code>null</code>, then a
     * <code>NullPointerException</code> is thrown and the destination
     * array is not modified.
     * <p>
     * Otherwise, if any of the following is true, an
     * <code>ArrayStoreException</code> is thrown and the destination is
     * not modified:
     * <ul>
     * <li>The <code>src</code> argument refers to an object that is not an
     *     array.
     * <li>The <code>dest</code> argument refers to an object that is not an
     *     array.
     * <li>The <code>src</code> argument and <code>dest</code> argument refer
     *     to arrays whose component types are different primitive types.
     * <li>The <code>src</code> argument refers to an array with a primitive
     *    component type and the <code>dest</code> argument refers to an array
     *     with a reference component type.
     * <li>The <code>src</code> argument refers to an array with a reference
     *    component type and the <code>dest</code> argument refers to an array
     *     with a primitive component type.
     * </ul>
     * <p>
     * Otherwise, if any of the following is true, an
     * <code>IndexOutOfBoundsException</code> is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The <code>srcPos</code> argument is negative.
     * <li>The <code>destPos</code> argument is negative.
     * <li>The <code>length</code> argument is negative.
     * <li><code>srcPos+length</code> is greater than
     *     <code>src.length</code>, the length of the source array.
     * <li><code>destPos+length</code> is greater than
     *     <code>dest.length</code>, the length of the destination array.
     * </ul>
     * <p>
     * Otherwise, if any actual component of the source array from
     * position <code>srcPos</code> through
     * <code>srcPos+length-1</code> cannot be converted to the component
     * type of the destination array by assignment conversion, an
     * <code>ArrayStoreException</code> is thrown. In this case, let
     * <b><i>k</i></b> be the smallest nonnegative integer less than
     * length such that <code>src[srcPos+</code><i>k</i><code>]</code>
     * cannot be converted to the component type of the destination
     * array; when the exception is thrown, source array components from
     * positions <code>srcPos</code> through
     * <code>srcPos+</code><i>k</i><code>-1</code>
     * will already have been copied to destination array positions
     * <code>destPos</code> through
     * <code>destPos+</code><i>k</I><code>-1</code> and no other
     * positions of the destination array will have been modified.
     * (Because of the restrictions already itemized, this
     * paragraph effectively applies only to the situation where both
     * arrays have component types that are reference types.)
     *
     * <p>
     * 将指定源数组的数组从指定位置复制到目标数组的指定位置将数组组件的子序列从<code> src </code>引用的源数组复制到引用的目标数组<code> dest </code>复制的组件数等于<code>
     *  length </code>参数<code> srcPos </code>到<code> srcPos + length-1 </code >分别复制到目的地数组中的<code> destPos </code>
     * 到<code> destPos + length-1 </code>。
     * <p>
     * 如果<code> src </code>和<code> dest </code>参数指向相同的数组对象,则执行复制,就像位置<code> srcPos </code>到<code> srcPos + l
     * ength-1 </code>首先被复制到具有<code> length </code>组件的临时数组,然后将临时数组的内容复制到<code> destPos </code> destPos + len
     * gth-1 </code>。
     * <p>
     *  如果<code> dest </code>是<code> null </code>,则抛出<code> NullPointerException </code>
     * <p>
     *  如果<code> src </code>是<code> null </code>,那么将抛出<code> NullPointerException </code>,并且不修改目标数组
     * <p>
     * 否则,如果以下任何一个为真,则抛出<code> ArrayStoreException </code>,并且不修改目标：
     * <ul>
     *  <li> <code> src </code>参数指的是不是数组的对象<li> <code> dest </code>参数指的是不是数组的对象<li> <code > src </code>参数和<code>
     *  dest </code>参数指的是其组件类型是不同基本类型的数组<li> <code> src </code>参数是指具有基本组件类型的数组, <code> dest </code>参数是指具有引用组
     * 件类型的数组<li> <code> src </code>参数是指具有引用组件类型和<code> dest </code > argument是指具有原始组件类型的数组。
     * </ul>
     * <p>
     * 否则,如果以下任何一个为真,则抛出<code> IndexOutOfBoundsException </code>,并且不修改目标：
     * <ul>
     *  <li> <code> srcPos </code>参数为负<li> <code> destPos </code>参数为负<li> <code> > srcPos + length </code>大于
     * <code> srclength </code>,则源数组<li> <code> destPos + length </code>的长度大于<code> destlength </code> ,目标数组
     * 的长度。
     * </ul>
     * <p>
     * 否则,如果源阵列从位置<code> srcPos </code>到<code> srcPos + length-1 </code>的任何实际组件无法通过赋值转换转换为目标数组的组件类型,代码> Arra
     * yStoreException </code>在这种情况下,让<b> <i> k </i> </b>是小于长度的最小非负整数,使得<code> src [srcPos + </code> </i> <code >
     * ] </code>不能转换为目标数组的组件类型;当抛出异常时,来自<code> srcPos </code>到<code> srcPos + </code> <i> k </i> <code> -1 </code>
     * 的源阵列组件将已经被复制到目的地数组位置<code> destPos </code>到<code> destPos + </code> <i> k </i> <code> -1 </code>,并且目标
     * 数组的其他位置都不会被修改(由于已经列出的限制,本段仅有效地应用于两个数组都具有作为引用类型的组件类型的情况)。
     * 
     * 
     * @param      src      the source array.
     * @param      srcPos   starting position in the source array.
     * @param      dest     the destination array.
     * @param      destPos  starting position in the destination data.
     * @param      length   the number of array elements to be copied.
     * @exception  IndexOutOfBoundsException  if copying would cause
     *               access of data outside array bounds.
     * @exception  ArrayStoreException  if an element in the <code>src</code>
     *               array could not be stored into the <code>dest</code> array
     *               because of a type mismatch.
     * @exception  NullPointerException if either <code>src</code> or
     *               <code>dest</code> is <code>null</code>.
     */
    public static native void arraycopy(Object src,  int  srcPos,
                                        Object dest, int destPos,
                                        int length);

    /**
     * Returns the same hash code for the given object as
     * would be returned by the default method hashCode(),
     * whether or not the given object's class overrides
     * hashCode().
     * The hash code for the null reference is zero.
     *
     * <p>
     * 返回与默认方法hashCode()返回的给定对象相同的哈希码,无论给定对象的类是否覆盖了hashCode()null引用的哈希码为零
     * 
     * 
     * @param x object for which the hashCode is to be calculated
     * @return  the hashCode
     * @since   JDK1.1
     */
    public static native int identityHashCode(Object x);

    /**
     * System properties. The following properties are guaranteed to be defined:
     * <dl>
     * <dt>java.version         <dd>Java version number
     * <dt>java.vendor          <dd>Java vendor specific string
     * <dt>java.vendor.url      <dd>Java vendor URL
     * <dt>java.home            <dd>Java installation directory
     * <dt>java.class.version   <dd>Java class version number
     * <dt>java.class.path      <dd>Java classpath
     * <dt>os.name              <dd>Operating System Name
     * <dt>os.arch              <dd>Operating System Architecture
     * <dt>os.version           <dd>Operating System Version
     * <dt>file.separator       <dd>File separator ("/" on Unix)
     * <dt>path.separator       <dd>Path separator (":" on Unix)
     * <dt>line.separator       <dd>Line separator ("\n" on Unix)
     * <dt>user.name            <dd>User account name
     * <dt>user.home            <dd>User home directory
     * <dt>user.dir             <dd>User's current working directory
     * </dl>
     * <p>
     *  系统属性保证定义以下属性：
     * <dl>
     * <dt> javaversion <dd> Java版本号<dt> javavendor <dd> Java供应商特定字符串<dt> javavendorurl <dd> Java供应商URL <dt>
     *  javahome <dd> Java安装目录<版本号<dt> javaclasspath <dd> Java classpath <dt> osname <dd>操作系统名称<dt> osarch <dd>
     * 操作系统架构<dt> osversion <dd>操作系统版本<dt> fileseparator < (Unix上的"/")<dt> pathseparator <dd>路径分隔符(Unix上的"："
     * )<dt> lineseparator <dd>行分隔符(Unix上的"\n") name <dt> userhome <dd>用户主目录<dt> userdir <dd>用户的当前工作目录。
     * </dl>
     */

    private static Properties props;
    private static native Properties initProperties(Properties props);

    /**
     * Determines the current system properties.
     * <p>
     * First, if there is a security manager, its
     * <code>checkPropertiesAccess</code> method is called with no
     * arguments. This may result in a security exception.
     * <p>
     * The current set of system properties for use by the
     * {@link #getProperty(String)} method is returned as a
     * <code>Properties</code> object. If there is no current set of
     * system properties, a set of system properties is first created and
     * initialized. This set of system properties always includes values
     * for the following keys:
     * <table summary="Shows property keys and associated values">
     * <tr><th>Key</th>
     *     <th>Description of Associated Value</th></tr>
     * <tr><td><code>java.version</code></td>
     *     <td>Java Runtime Environment version</td></tr>
     * <tr><td><code>java.vendor</code></td>
     *     <td>Java Runtime Environment vendor</td></tr>
     * <tr><td><code>java.vendor.url</code></td>
     *     <td>Java vendor URL</td></tr>
     * <tr><td><code>java.home</code></td>
     *     <td>Java installation directory</td></tr>
     * <tr><td><code>java.vm.specification.version</code></td>
     *     <td>Java Virtual Machine specification version</td></tr>
     * <tr><td><code>java.vm.specification.vendor</code></td>
     *     <td>Java Virtual Machine specification vendor</td></tr>
     * <tr><td><code>java.vm.specification.name</code></td>
     *     <td>Java Virtual Machine specification name</td></tr>
     * <tr><td><code>java.vm.version</code></td>
     *     <td>Java Virtual Machine implementation version</td></tr>
     * <tr><td><code>java.vm.vendor</code></td>
     *     <td>Java Virtual Machine implementation vendor</td></tr>
     * <tr><td><code>java.vm.name</code></td>
     *     <td>Java Virtual Machine implementation name</td></tr>
     * <tr><td><code>java.specification.version</code></td>
     *     <td>Java Runtime Environment specification  version</td></tr>
     * <tr><td><code>java.specification.vendor</code></td>
     *     <td>Java Runtime Environment specification  vendor</td></tr>
     * <tr><td><code>java.specification.name</code></td>
     *     <td>Java Runtime Environment specification  name</td></tr>
     * <tr><td><code>java.class.version</code></td>
     *     <td>Java class format version number</td></tr>
     * <tr><td><code>java.class.path</code></td>
     *     <td>Java class path</td></tr>
     * <tr><td><code>java.library.path</code></td>
     *     <td>List of paths to search when loading libraries</td></tr>
     * <tr><td><code>java.io.tmpdir</code></td>
     *     <td>Default temp file path</td></tr>
     * <tr><td><code>java.compiler</code></td>
     *     <td>Name of JIT compiler to use</td></tr>
     * <tr><td><code>java.ext.dirs</code></td>
     *     <td>Path of extension directory or directories
     *         <b>Deprecated.</b> <i>This property, and the mechanism
     *            which implements it, may be removed in a future
     *            release.</i> </td></tr>
     * <tr><td><code>os.name</code></td>
     *     <td>Operating system name</td></tr>
     * <tr><td><code>os.arch</code></td>
     *     <td>Operating system architecture</td></tr>
     * <tr><td><code>os.version</code></td>
     *     <td>Operating system version</td></tr>
     * <tr><td><code>file.separator</code></td>
     *     <td>File separator ("/" on UNIX)</td></tr>
     * <tr><td><code>path.separator</code></td>
     *     <td>Path separator (":" on UNIX)</td></tr>
     * <tr><td><code>line.separator</code></td>
     *     <td>Line separator ("\n" on UNIX)</td></tr>
     * <tr><td><code>user.name</code></td>
     *     <td>User's account name</td></tr>
     * <tr><td><code>user.home</code></td>
     *     <td>User's home directory</td></tr>
     * <tr><td><code>user.dir</code></td>
     *     <td>User's current working directory</td></tr>
     * </table>
     * <p>
     * Multiple paths in a system property value are separated by the path
     * separator character of the platform.
     * <p>
     * Note that even if the security manager does not permit the
     * <code>getProperties</code> operation, it may choose to permit the
     * {@link #getProperty(String)} operation.
     *
     * <p>
     * 确定当前系统属性
     * <p>
     *  首先,如果有安全管理器,它的<code> checkPropertiesAccess </code>方法被称为不带参数,这可能导致安全异常
     * <p>
     *  由{@link #getProperty(字符串)}方法使用的当前系统属性集合返回为一个<代码>属性</code>对象如果没有当前系统属性的集合,一组系统属性是第一已创建和初始化此组系统属性始终包括以
     * 下键的值：。
     * <table summary="Shows property keys and associated values">
     * <tr> <th>键</th> <th>相关值说明</tr> </tr> <tr> <td> <code> javaversion </code> </td> <td> Java Runtime Env
     * ironment版本</td> </tr> <tr> <td> <code> javavendor </code> </td> <td> Java Runtime Environment供应商</td>
     *  </tr> <tr> <td> <code > javavendorurl </code> </td> <td> Java供应商URL </td> </tr> <tr> <td> <code> jav
     * ahome </code> </td> <td> </td> </td> </td> </t>> </td> </t>> </td> </tr> </td> </t> </code> </td> </td>
     *  Java虚拟机规范供应商</td> </tr> <tr> <td> <code> javavmspecificationname </code> </td> <td> </td> </tr> <tr>
     *  <td> <code> javavm版本</code> </td> <td> Java虚拟机实现版本</td> </tr> <tr> <td> <code> javavmvendor </code> 
     * </td> <td>供应商</td> </tr> <tr> <td> <code> javavmname </code> </td> <td> Java虚拟机实施名称</td> </tr> <tr> <td>
     *  <代码> javaspecificationversion </code> </td> <td> Java运行时环境规范版本</td> </tr> <tr> <td> <code> javaspeci
     * ficationvendor </code>环境规范供应商</td> </tr> <tr> <td> <code> javaspecificationname </code> </td> <td> Ja
     * va运行时环境规范名称</td> </tr> <tr> <td > <code> javaclassversion </code> </td> <td> Java类格式版本号</td> </tr> <tr>
     *  <td> <code> javaclasspath </code> </td> <td> Java类路径</td> </tr> <tr> <td> <code> javalibrary路径</code>
     *  </td> <td>加载库时搜索的路径列表</td> </tr> <tr> <td> <code> javaiotmpdir </code> </td> <td>临时文件路径</td> </tr> <tr>
     *  <td> <code> javacompiler </code> </td> <td>要使用的JIT编译器名称</td> </tr> <tr> < td> <code> javaextdirs </code>
     *  </td> <td>扩展目录或目录的路径<b>已弃用</b> <i>此属性及其实现机制可能会在将来删除发布</i> </td> </tr> <tr> <td> <code> osname </code>
     *  </td> <td>操作系统名称</td> </tr> <tr> <td > <code> osarch </code> </td> <td>操作系统架构</td> </tr> <tr> <td> <code>
     *  osversion </code> </td> </td> </td> </td> </td> </td> <td> <code>路径分隔符</code> </td> <td>路径分隔符(在UNIX上
     * 为"：")</td> </tr> <tr> <td> <code> lineseparator </code> </td> <td>行分隔符(UNIX上的"\n")</td> </tr> <tr> <td>
     *  <code>用户名</code> </td> <td>用户的帐户名称</td> </tr > <tr> <td> <code> userhome </code> </td> <td>用户的主目录</td>
     *  </tr> <tr> <td> <code> userdir </code> </td > <td>用户的当前工作目录</td> </tr>。
     * </table>
     * <p>
     * 系统属性值中的多个路径由平台的路径分隔符分隔
     * <p>
     *  注意,即使安全管理器不允许<code> getProperties </code>操作,它也可以选择允许{@link #getProperty(String)}操作
     * 
     * 
     * @return     the system properties
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertiesAccess</code> method doesn't allow access
     *              to the system properties.
     * @see        #setProperties
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkPropertiesAccess()
     * @see        java.util.Properties
     */
    public static Properties getProperties() {
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPropertiesAccess();
        }

        return props;
    }

    /**
     * Returns the system-dependent line separator string.  It always
     * returns the same value - the initial value of the {@linkplain
     * #getProperty(String) system property} {@code line.separator}.
     *
     * <p>On UNIX systems, it returns {@code "\n"}; on Microsoft
     * Windows systems it returns {@code "\r\n"}.
     *
     * <p>
     *  返回系统相关的行分隔符字符串它总是返回相同的值 -  {@linkplain #getProperty(String)系统属性的初始值} {@code lineseparator}
     * 
     *  <p>在UNIX系统上,它返回{@code"\n"};在Microsoft Windows系统上,它返回{@code"\\ r\n"}
     * 
     * 
     * @return the system-dependent line separator string
     * @since 1.7
     */
    public static String lineSeparator() {
        return lineSeparator;
    }

    private static String lineSeparator;

    /**
     * Sets the system properties to the <code>Properties</code>
     * argument.
     * <p>
     * First, if there is a security manager, its
     * <code>checkPropertiesAccess</code> method is called with no
     * arguments. This may result in a security exception.
     * <p>
     * The argument becomes the current set of system properties for use
     * by the {@link #getProperty(String)} method. If the argument is
     * <code>null</code>, then the current set of system properties is
     * forgotten.
     *
     * <p>
     *  将系统属性设置为<code> Properties </code>参数
     * <p>
     *  首先,如果有安全管理器,它的<code> checkPropertiesAccess </code>方法被称为不带参数,这可能导致安全异常
     * <p>
     * 参数成为{@link #getProperty(String)}方法使用的当前系统属性集合如果参数为<code> null </code>,则当前的系统属性集将被遗忘
     * 
     * 
     * @param      props   the new system properties.
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertiesAccess</code> method doesn't allow access
     *              to the system properties.
     * @see        #getProperties
     * @see        java.util.Properties
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkPropertiesAccess()
     */
    public static void setProperties(Properties props) {
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPropertiesAccess();
        }
        if (props == null) {
            props = new Properties();
            initProperties(props);
        }
        System.props = props;
    }

    /**
     * Gets the system property indicated by the specified key.
     * <p>
     * First, if there is a security manager, its
     * <code>checkPropertyAccess</code> method is called with the key as
     * its argument. This may result in a SecurityException.
     * <p>
     * If there is no current set of system properties, a set of system
     * properties is first created and initialized in the same manner as
     * for the <code>getProperties</code> method.
     *
     * <p>
     *  获取指定键指示的系统属性
     * <p>
     *  首先,如果有一个安全管理器,它的<code> checkPropertyAccess </code>方法被调用与作为其参数的密钥这可能导致SecurityException
     * <p>
     *  如果没有当前的系统属性集,则首先以与<code> getProperties </code>方法相同的方式创建和初始化一组系统属性
     * 
     * 
     * @param      key   the name of the system property.
     * @return     the string value of the system property,
     *             or <code>null</code> if there is no property with that key.
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertyAccess</code> method doesn't allow
     *              access to the specified system property.
     * @exception  NullPointerException if <code>key</code> is
     *             <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #setProperty
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see        java.lang.System#getProperties()
     */
    public static String getProperty(String key) {
        checkKey(key);
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPropertyAccess(key);
        }

        return props.getProperty(key);
    }

    /**
     * Gets the system property indicated by the specified key.
     * <p>
     * First, if there is a security manager, its
     * <code>checkPropertyAccess</code> method is called with the
     * <code>key</code> as its argument.
     * <p>
     * If there is no current set of system properties, a set of system
     * properties is first created and initialized in the same manner as
     * for the <code>getProperties</code> method.
     *
     * <p>
     *  获取指定键指示的系统属性
     * <p>
     *  首先,如果有安全管理器,则以<code> key </code>作为其参数来调用其<code> checkPropertyAccess </code>
     * <p>
     * 如果没有当前的系统属性集,则首先以与<code> getProperties </code>方法相同的方式创建和初始化一组系统属性
     * 
     * 
     * @param      key   the name of the system property.
     * @param      def   a default value.
     * @return     the string value of the system property,
     *             or the default value if there is no property with that key.
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertyAccess</code> method doesn't allow
     *             access to the specified system property.
     * @exception  NullPointerException if <code>key</code> is
     *             <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #setProperty
     * @see        java.lang.SecurityManager#checkPropertyAccess(java.lang.String)
     * @see        java.lang.System#getProperties()
     */
    public static String getProperty(String key, String def) {
        checkKey(key);
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPropertyAccess(key);
        }

        return props.getProperty(key, def);
    }

    /**
     * Sets the system property indicated by the specified key.
     * <p>
     * First, if a security manager exists, its
     * <code>SecurityManager.checkPermission</code> method
     * is called with a <code>PropertyPermission(key, "write")</code>
     * permission. This may result in a SecurityException being thrown.
     * If no exception is thrown, the specified property is set to the given
     * value.
     * <p>
     *
     * <p>
     *  设置由指定键指示的系统属性
     * <p>
     *  首先,如果安全管理器存在,它的<code> SecurityManagercheckPermission </code>方法被调用与<code> PropertyPermission(key,"wri
     * te")</code>权限这可能导致SecurityException被抛出如果没有异常抛出,指定的属性设置为给定的值。
     * <p>
     * 
     * 
     * @param      key   the name of the system property.
     * @param      value the value of the system property.
     * @return     the previous value of the system property,
     *             or <code>null</code> if it did not have one.
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPermission</code> method doesn't allow
     *             setting of the specified property.
     * @exception  NullPointerException if <code>key</code> or
     *             <code>value</code> is <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #getProperty
     * @see        java.lang.System#getProperty(java.lang.String)
     * @see        java.lang.System#getProperty(java.lang.String, java.lang.String)
     * @see        java.util.PropertyPermission
     * @see        SecurityManager#checkPermission
     * @since      1.2
     */
    public static String setProperty(String key, String value) {
        checkKey(key);
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new PropertyPermission(key,
                SecurityConstants.PROPERTY_WRITE_ACTION));
        }

        return (String) props.setProperty(key, value);
    }

    /**
     * Removes the system property indicated by the specified key.
     * <p>
     * First, if a security manager exists, its
     * <code>SecurityManager.checkPermission</code> method
     * is called with a <code>PropertyPermission(key, "write")</code>
     * permission. This may result in a SecurityException being thrown.
     * If no exception is thrown, the specified property is removed.
     * <p>
     *
     * <p>
     *  删除指定键指示的系统属性
     * <p>
     * 首先,如果安全管理器存在,它的<code> SecurityManagercheckPermission </code>方法被调用与<code> PropertyPermission(key,"writ
     * e")</code>权限这可能导致SecurityException被抛出如果没有异常抛出,指定的属性将被删除。
     * <p>
     * 
     * 
     * @param      key   the name of the system property to be removed.
     * @return     the previous string value of the system property,
     *             or <code>null</code> if there was no property with that key.
     *
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertyAccess</code> method doesn't allow
     *              access to the specified system property.
     * @exception  NullPointerException if <code>key</code> is
     *             <code>null</code>.
     * @exception  IllegalArgumentException if <code>key</code> is empty.
     * @see        #getProperty
     * @see        #setProperty
     * @see        java.util.Properties
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkPropertiesAccess()
     * @since 1.5
     */
    public static String clearProperty(String key) {
        checkKey(key);
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new PropertyPermission(key, "write"));
        }

        return (String) props.remove(key);
    }

    private static void checkKey(String key) {
        if (key == null) {
            throw new NullPointerException("key can't be null");
        }
        if (key.equals("")) {
            throw new IllegalArgumentException("key can't be empty");
        }
    }

    /**
     * Gets the value of the specified environment variable. An
     * environment variable is a system-dependent external named
     * value.
     *
     * <p>If a security manager exists, its
     * {@link SecurityManager#checkPermission checkPermission}
     * method is called with a
     * <code>{@link RuntimePermission}("getenv."+name)</code>
     * permission.  This may result in a {@link SecurityException}
     * being thrown.  If no exception is thrown the value of the
     * variable <code>name</code> is returned.
     *
     * <p><a name="EnvironmentVSSystemProperties"><i>System
     * properties</i> and <i>environment variables</i></a> are both
     * conceptually mappings between names and values.  Both
     * mechanisms can be used to pass user-defined information to a
     * Java process.  Environment variables have a more global effect,
     * because they are visible to all descendants of the process
     * which defines them, not just the immediate Java subprocess.
     * They can have subtly different semantics, such as case
     * insensitivity, on different operating systems.  For these
     * reasons, environment variables are more likely to have
     * unintended side effects.  It is best to use system properties
     * where possible.  Environment variables should be used when a
     * global effect is desired, or when an external system interface
     * requires an environment variable (such as <code>PATH</code>).
     *
     * <p>On UNIX systems the alphabetic case of <code>name</code> is
     * typically significant, while on Microsoft Windows systems it is
     * typically not.  For example, the expression
     * <code>System.getenv("FOO").equals(System.getenv("foo"))</code>
     * is likely to be true on Microsoft Windows.
     *
     * <p>
     *  获取指定环境变量的值环境变量是系统相关的外部命名值
     * 
     *  <p>如果存在安全管理器,则会使用<code> {@ link RuntimePermission}("getenv"+ name)</code>权限调用其{@link SecurityManager#checkPermission checkPermission}
     * 方法。
     * 这可能会导致{@ link SecurityException}被抛出如果没有抛出异常,则返回变量<code> name </code>的值。
     * 
     * <p> <a name=\"EnvironmentVSSystemProperties\"> <i>系统属性</i>和<i>环境变量</i> </a>概念上都是名称和值之间的映射两种机制都可用于传递用户
     * 定义信息到Java进程环境变量具有更多的全局效应,因为它们对定义它们的进程的所有后代都是可见的,而不仅仅是紧接着的Java子进程。
     * 它们可以具有略微不同的语义,例如不区分大小写,系统由于这些原因,环境变量更可能具有非预期的副作用最好在可能的情况下使用系统属性当需要全局效果时,或者当外部系统接口需要环境变量(例如<code> PATH
     *  </code>)时,应使用环境变量。
     * 
     * <p>在UNIX系统上,<code> name </code>的字母大小写通常是重要的,而在Microsoft Windows系统上通常不是。
     * 例如,表达式<code> Systemgetenv("FOO")equals(Systemgetenv foo"))</code>在Microsoft Windows上可能是真的。
     * 
     * 
     * @param  name the name of the environment variable
     * @return the string value of the variable, or <code>null</code>
     *         if the variable is not defined in the system environment
     * @throws NullPointerException if <code>name</code> is <code>null</code>
     * @throws SecurityException
     *         if a security manager exists and its
     *         {@link SecurityManager#checkPermission checkPermission}
     *         method doesn't allow access to the environment variable
     *         <code>name</code>
     * @see    #getenv()
     * @see    ProcessBuilder#environment()
     */
    public static String getenv(String name) {
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("getenv."+name));
        }

        return ProcessEnvironment.getenv(name);
    }


    /**
     * Returns an unmodifiable string map view of the current system environment.
     * The environment is a system-dependent mapping from names to
     * values which is passed from parent to child processes.
     *
     * <p>If the system does not support environment variables, an
     * empty map is returned.
     *
     * <p>The returned map will never contain null keys or values.
     * Attempting to query the presence of a null key or value will
     * throw a {@link NullPointerException}.  Attempting to query
     * the presence of a key or value which is not of type
     * {@link String} will throw a {@link ClassCastException}.
     *
     * <p>The returned map and its collection views may not obey the
     * general contract of the {@link Object#equals} and
     * {@link Object#hashCode} methods.
     *
     * <p>The returned map is typically case-sensitive on all platforms.
     *
     * <p>If a security manager exists, its
     * {@link SecurityManager#checkPermission checkPermission}
     * method is called with a
     * <code>{@link RuntimePermission}("getenv.*")</code>
     * permission.  This may result in a {@link SecurityException} being
     * thrown.
     *
     * <p>When passing information to a Java subprocess,
     * <a href=#EnvironmentVSSystemProperties>system properties</a>
     * are generally preferred over environment variables.
     *
     * <p>
     *  返回当前系统环境的不可修改的字符串映射视图环境是从名称到从父进程传递到子进程的值的系统相关映射
     * 
     *  <p>如果系统不支持环境变量,则返回空映射
     * 
     * <P>返回的映射永远不会包含null键或值试图查询null键或值的存在都将抛出{@link NullPointerException异常}试图查询键或值的存在它的类型是{不@link String}会抛
     * 出一个{@link ClassCastException}。
     * 
     *  <p>返回的地图及其集合视图可能不符合{@link Object#equals}和{@link Object#hashCode}方法的一般合同
     * 
     *  <p>返回的地图在所有平台上通常区分大小写
     * 
     *  <p>如果存在安全管理器,则会使用<code> {@ link RuntimePermission}("getenv *")</code>权限调用其{@link SecurityManager#checkPermission checkPermission}
     * 方法。
     * 这可能会导致{@link SecurityException}被抛出。
     * 
     * <p>将信息传递给Java子流程时,<a href=#EnvironmentVSSystemProperties>系统属性</a>通常优先于环境变量
     * 
     * 
     * @return the environment as a map of variable names to values
     * @throws SecurityException
     *         if a security manager exists and its
     *         {@link SecurityManager#checkPermission checkPermission}
     *         method doesn't allow access to the process environment
     * @see    #getenv(String)
     * @see    ProcessBuilder#environment()
     * @since  1.5
     */
    public static java.util.Map<String,String> getenv() {
        SecurityManager sm = getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("getenv.*"));
        }

        return ProcessEnvironment.getenv();
    }

    /**
     * Terminates the currently running Java Virtual Machine. The
     * argument serves as a status code; by convention, a nonzero status
     * code indicates abnormal termination.
     * <p>
     * This method calls the <code>exit</code> method in class
     * <code>Runtime</code>. This method never returns normally.
     * <p>
     * The call <code>System.exit(n)</code> is effectively equivalent to
     * the call:
     * <blockquote><pre>
     * Runtime.getRuntime().exit(n)
     * </pre></blockquote>
     *
     * <p>
     *  终止当前运行的Java虚拟机参数用作状态代码;按照惯例,非零状态码表示异常终止
     * <p>
     *  此方法调用<code> Runtime </code>类中的<code> exit </code>方法此方法不会正常返回
     * <p>
     *  调用<code> Systemexit(n)</code>实际上等价于调用：<blockquote> <pre> RuntimegetRuntime()exit(n)</pre> </blockquote>
     * 。
     * 
     * 
     * @param      status   exit status.
     * @throws  SecurityException
     *        if a security manager exists and its <code>checkExit</code>
     *        method doesn't allow exit with the specified status.
     * @see        java.lang.Runtime#exit(int)
     */
    public static void exit(int status) {
        Runtime.getRuntime().exit(status);
    }

    /**
     * Runs the garbage collector.
     * <p>
     * Calling the <code>gc</code> method suggests that the Java Virtual
     * Machine expend effort toward recycling unused objects in order to
     * make the memory they currently occupy available for quick reuse.
     * When control returns from the method call, the Java Virtual
     * Machine has made a best effort to reclaim space from all discarded
     * objects.
     * <p>
     * The call <code>System.gc()</code> is effectively equivalent to the
     * call:
     * <blockquote><pre>
     * Runtime.getRuntime().gc()
     * </pre></blockquote>
     *
     * <p>
     *  运行垃圾收集器
     * <p>
     * 调用<code> gc </code>方法表明Java虚拟机花费了回收未使用的对象的努力,以使它们当前占用的内存可用于快速重用当控制从方法调用返回时,Java虚拟机已经尽最大努力从所有丢弃的对象中回收空
     * 间。
     * <p>
     *  调用<code> Systemgc()</code>实际上等价于调用：<blockquote> <pre> RuntimegetRuntime()gc()</pre> </blockquote>
     * 
     * 
     * @see     java.lang.Runtime#gc()
     */
    public static void gc() {
        Runtime.getRuntime().gc();
    }

    /**
     * Runs the finalization methods of any objects pending finalization.
     * <p>
     * Calling this method suggests that the Java Virtual Machine expend
     * effort toward running the <code>finalize</code> methods of objects
     * that have been found to be discarded but whose <code>finalize</code>
     * methods have not yet been run. When control returns from the
     * method call, the Java Virtual Machine has made a best effort to
     * complete all outstanding finalizations.
     * <p>
     * The call <code>System.runFinalization()</code> is effectively
     * equivalent to the call:
     * <blockquote><pre>
     * Runtime.getRuntime().runFinalization()
     * </pre></blockquote>
     *
     * <p>
     *  运行任何对象的finalization方法,等待最终确定
     * <p>
     * 调用此方法表明Java虚拟机花费努力来运行已被发现被丢弃但其<code> finalize </code>方法尚未运行的对象的<code> finalize </code>方法当控制从方法调用返回,Ja
     * va虚拟机已尽最大努力完成所有未完成的最终化。
     * <p>
     *  调用<code> SystemrunFinalization()</code>实际上等效于调用：<blockquote> <pre> RuntimegetRuntime()ru​​nFinalizat
     * ion()</pre> </blockquote>。
     * 
     * 
     * @see     java.lang.Runtime#runFinalization()
     */
    public static void runFinalization() {
        Runtime.getRuntime().runFinalization();
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
     *  在退出时启用或禁用终结;这样做指定具有尚未被自动调用的finalizer的所有对象的finalizer将在Java运行时退出之前运行默认情况下,禁用退出时的finalization
     * 
     * <p>如果有安全管理器,则首先调用其<code> checkExit </code>方法,其中的参数为0,以确保允许退出。这可能导致SecurityException
     * 
     * 
     * @deprecated  This method is inherently unsafe.  It may result in
     *      finalizers being called on live objects while other threads are
     *      concurrently manipulating those objects, resulting in erratic
     *      behavior or deadlock.
     * @param value indicating enabling or disabling of finalization
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
        Runtime.runFinalizersOnExit(value);
    }

    /**
     * Loads the native library specified by the filename argument.  The filename
     * argument must be an absolute path name.
     *
     * If the filename argument, when stripped of any platform-specific library
     * prefix, path, and file extension, indicates a library whose name is,
     * for example, L, and a native library called L is statically linked
     * with the VM, then the JNI_OnLoad_L function exported by the library
     * is invoked rather than attempting to load a dynamic library.
     * A filename matching the argument does not have to exist in the
     * file system.
     * See the JNI Specification for more details.
     *
     * Otherwise, the filename argument is mapped to a native library image in
     * an implementation-dependent manner.
     *
     * <p>
     * The call <code>System.load(name)</code> is effectively equivalent
     * to the call:
     * <blockquote><pre>
     * Runtime.getRuntime().load(name)
     * </pre></blockquote>
     *
     * <p>
     *  加载由filename参数指定的本机库文件名参数必须是绝对路径名
     * 
     *  如果文件名参数,当剥离任何特定于平台的库前缀,路径和文件扩展名时,表示其名称为例如L的库,并且称为L的本地库与VM静态链接,则JNI_OnLoad_L函数由库导出的调用而不是尝试加载动态库与文件系统中
     * 不存在匹配参数的文件名有关更多详细信息,请参阅JNI规范。
     * 
     * 否则,filename参数将以实现相关的方式映射到本机库映像
     * 
     * <p>
     *  调用<code> Systemload(name)</code>实际上等效于调用：<blockquote> <pre> RuntimegetRuntime()load(name)</pre> </blockquote>
     * 。
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
     * @see        java.lang.Runtime#load(java.lang.String)
     * @see        java.lang.SecurityManager#checkLink(java.lang.String)
     */
    @CallerSensitive
    public static void load(String filename) {
        Runtime.getRuntime().load0(Reflection.getCallerClass(), filename);
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
     * The call <code>System.loadLibrary(name)</code> is effectively
     * equivalent to the call
     * <blockquote><pre>
     * Runtime.getRuntime().loadLibrary(name)
     * </pre></blockquote>
     *
     * <p>
     *  加载由<code> libname </code>参数指定的本机库</code> libname </code>参数不能包含任何平台特定的前缀,文件扩展名或路径如果一个本地库称为<code> libn
     * ame </code >与VM静态链接,则调用由库导出的JNI_OnLoad_ <code> libname </code>函数有关详细信息,请参阅JNI规范。
     * 
     * 否则,libname参数从系统库位置加载,并以实现相关的方式映射到本机库映像
     * <p>
     *  调用<code> SystemloadLibrary(name)</code>实际上等效于调用<blockquote> <pre> RuntimegetRuntime()loadLibrary(nam
     * e)</pre> </blockquote>。
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
     * @see        java.lang.Runtime#loadLibrary(java.lang.String)
     * @see        java.lang.SecurityManager#checkLink(java.lang.String)
     */
    @CallerSensitive
    public static void loadLibrary(String libname) {
        Runtime.getRuntime().loadLibrary0(Reflection.getCallerClass(), libname);
    }

    /**
     * Maps a library name into a platform-specific string representing
     * a native library.
     *
     * <p>
     *  将库名称映射到表示本机库的平台特定字符串
     * 
     * 
     * @param      libname the name of the library.
     * @return     a platform-dependent native library name.
     * @exception  NullPointerException if <code>libname</code> is
     *             <code>null</code>
     * @see        java.lang.System#loadLibrary(java.lang.String)
     * @see        java.lang.ClassLoader#findLibrary(java.lang.String)
     * @since      1.2
     */
    public static native String mapLibraryName(String libname);

    /**
     * Create PrintStream for stdout/err based on encoding.
     * <p>
     *  基于编码为stdout / err创建PrintStream
     * 
     */
    private static PrintStream newPrintStream(FileOutputStream fos, String enc) {
       if (enc != null) {
            try {
                return new PrintStream(new BufferedOutputStream(fos, 128), true, enc);
            } catch (UnsupportedEncodingException uee) {}
        }
        return new PrintStream(new BufferedOutputStream(fos, 128), true);
    }


    /**
     * Initialize the system class.  Called after thread initialization.
     * <p>
     *  初始化系统类在线程初始化后调用
     */
    private static void initializeSystemClass() {

        // VM might invoke JNU_NewStringPlatform() to set those encoding
        // sensitive properties (user.home, user.name, boot.class.path, etc.)
        // during "props" initialization, in which it may need access, via
        // System.getProperty(), to the related system encoding property that
        // have been initialized (put into "props") at early stage of the
        // initialization. So make sure the "props" is available at the
        // very beginning of the initialization and all system properties to
        // be put into it directly.
        props = new Properties();
        initProperties(props);  // initialized by the VM

        // There are certain system configurations that may be controlled by
        // VM options such as the maximum amount of direct memory and
        // Integer cache size used to support the object identity semantics
        // of autoboxing.  Typically, the library will obtain these values
        // from the properties set by the VM.  If the properties are for
        // internal implementation use only, these properties should be
        // removed from the system properties.
        //
        // See java.lang.Integer.IntegerCache and the
        // sun.misc.VM.saveAndRemoveProperties method for example.
        //
        // Save a private copy of the system properties object that
        // can only be accessed by the internal implementation.  Remove
        // certain system properties that are not intended for public access.
        sun.misc.VM.saveAndRemoveProperties(props);


        lineSeparator = props.getProperty("line.separator");
        sun.misc.Version.init();

        FileInputStream fdIn = new FileInputStream(FileDescriptor.in);
        FileOutputStream fdOut = new FileOutputStream(FileDescriptor.out);
        FileOutputStream fdErr = new FileOutputStream(FileDescriptor.err);
        setIn0(new BufferedInputStream(fdIn));
        setOut0(newPrintStream(fdOut, props.getProperty("sun.stdout.encoding")));
        setErr0(newPrintStream(fdErr, props.getProperty("sun.stderr.encoding")));

        // Load the zip library now in order to keep java.util.zip.ZipFile
        // from trying to use itself to load this library later.
        loadLibrary("zip");

        // Setup Java signal handlers for HUP, TERM, and INT (where available).
        Terminator.setup();

        // Initialize any miscellenous operating system settings that need to be
        // set for the class libraries. Currently this is no-op everywhere except
        // for Windows where the process-wide error mode is set before the java.io
        // classes are used.
        sun.misc.VM.initializeOSEnvironment();

        // The main thread is not added to its thread group in the same
        // way as other threads; we must do it ourselves here.
        Thread current = Thread.currentThread();
        current.getThreadGroup().add(current);

        // register shared secrets
        setJavaLangAccess();

        // Subsystems that are invoked during initialization can invoke
        // sun.misc.VM.isBooted() in order to avoid doing things that should
        // wait until the application class loader has been set up.
        // IMPORTANT: Ensure that this remains the last initialization action!
        sun.misc.VM.booted();
    }

    private static void setJavaLangAccess() {
        // Allow privileged classes outside of java.lang
        sun.misc.SharedSecrets.setJavaLangAccess(new sun.misc.JavaLangAccess(){
            public sun.reflect.ConstantPool getConstantPool(Class<?> klass) {
                return klass.getConstantPool();
            }
            public boolean casAnnotationType(Class<?> klass, AnnotationType oldType, AnnotationType newType) {
                return klass.casAnnotationType(oldType, newType);
            }
            public AnnotationType getAnnotationType(Class<?> klass) {
                return klass.getAnnotationType();
            }
            public Map<Class<? extends Annotation>, Annotation> getDeclaredAnnotationMap(Class<?> klass) {
                return klass.getDeclaredAnnotationMap();
            }
            public byte[] getRawClassAnnotations(Class<?> klass) {
                return klass.getRawAnnotations();
            }
            public byte[] getRawClassTypeAnnotations(Class<?> klass) {
                return klass.getRawTypeAnnotations();
            }
            public byte[] getRawExecutableTypeAnnotations(Executable executable) {
                return Class.getExecutableTypeAnnotationBytes(executable);
            }
            public <E extends Enum<E>>
                    E[] getEnumConstantsShared(Class<E> klass) {
                return klass.getEnumConstantsShared();
            }
            public void blockedOn(Thread t, Interruptible b) {
                t.blockedOn(b);
            }
            public void registerShutdownHook(int slot, boolean registerShutdownInProgress, Runnable hook) {
                Shutdown.add(slot, registerShutdownInProgress, hook);
            }
            public int getStackTraceDepth(Throwable t) {
                return t.getStackTraceDepth();
            }
            public StackTraceElement getStackTraceElement(Throwable t, int i) {
                return t.getStackTraceElement(i);
            }
            public String newStringUnsafe(char[] chars) {
                return new String(chars, true);
            }
            public Thread newThreadWithAcc(Runnable target, AccessControlContext acc) {
                return new Thread(target, acc);
            }
            public void invokeFinalize(Object o) throws Throwable {
                o.finalize();
            }
        });
    }
}
