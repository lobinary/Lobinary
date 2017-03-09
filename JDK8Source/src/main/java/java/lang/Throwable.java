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
import  java.io.*;
import  java.util.*;

/**
 * The {@code Throwable} class is the superclass of all errors and
 * exceptions in the Java language. Only objects that are instances of this
 * class (or one of its subclasses) are thrown by the Java Virtual Machine or
 * can be thrown by the Java {@code throw} statement. Similarly, only
 * this class or one of its subclasses can be the argument type in a
 * {@code catch} clause.
 *
 * For the purposes of compile-time checking of exceptions, {@code
 * Throwable} and any subclass of {@code Throwable} that is not also a
 * subclass of either {@link RuntimeException} or {@link Error} are
 * regarded as checked exceptions.
 *
 * <p>Instances of two subclasses, {@link java.lang.Error} and
 * {@link java.lang.Exception}, are conventionally used to indicate
 * that exceptional situations have occurred. Typically, these instances
 * are freshly created in the context of the exceptional situation so
 * as to include relevant information (such as stack trace data).
 *
 * <p>A throwable contains a snapshot of the execution stack of its
 * thread at the time it was created. It can also contain a message
 * string that gives more information about the error. Over time, a
 * throwable can {@linkplain Throwable#addSuppressed suppress} other
 * throwables from being propagated.  Finally, the throwable can also
 * contain a <i>cause</i>: another throwable that caused this
 * throwable to be constructed.  The recording of this causal information
 * is referred to as the <i>chained exception</i> facility, as the
 * cause can, itself, have a cause, and so on, leading to a "chain" of
 * exceptions, each caused by another.
 *
 * <p>One reason that a throwable may have a cause is that the class that
 * throws it is built atop a lower layered abstraction, and an operation on
 * the upper layer fails due to a failure in the lower layer.  It would be bad
 * design to let the throwable thrown by the lower layer propagate outward, as
 * it is generally unrelated to the abstraction provided by the upper layer.
 * Further, doing so would tie the API of the upper layer to the details of
 * its implementation, assuming the lower layer's exception was a checked
 * exception.  Throwing a "wrapped exception" (i.e., an exception containing a
 * cause) allows the upper layer to communicate the details of the failure to
 * its caller without incurring either of these shortcomings.  It preserves
 * the flexibility to change the implementation of the upper layer without
 * changing its API (in particular, the set of exceptions thrown by its
 * methods).
 *
 * <p>A second reason that a throwable may have a cause is that the method
 * that throws it must conform to a general-purpose interface that does not
 * permit the method to throw the cause directly.  For example, suppose
 * a persistent collection conforms to the {@link java.util.Collection
 * Collection} interface, and that its persistence is implemented atop
 * {@code java.io}.  Suppose the internals of the {@code add} method
 * can throw an {@link java.io.IOException IOException}.  The implementation
 * can communicate the details of the {@code IOException} to its caller
 * while conforming to the {@code Collection} interface by wrapping the
 * {@code IOException} in an appropriate unchecked exception.  (The
 * specification for the persistent collection should indicate that it is
 * capable of throwing such exceptions.)
 *
 * <p>A cause can be associated with a throwable in two ways: via a
 * constructor that takes the cause as an argument, or via the
 * {@link #initCause(Throwable)} method.  New throwable classes that
 * wish to allow causes to be associated with them should provide constructors
 * that take a cause and delegate (perhaps indirectly) to one of the
 * {@code Throwable} constructors that takes a cause.
 *
 * Because the {@code initCause} method is public, it allows a cause to be
 * associated with any throwable, even a "legacy throwable" whose
 * implementation predates the addition of the exception chaining mechanism to
 * {@code Throwable}.
 *
 * <p>By convention, class {@code Throwable} and its subclasses have two
 * constructors, one that takes no arguments and one that takes a
 * {@code String} argument that can be used to produce a detail message.
 * Further, those subclasses that might likely have a cause associated with
 * them should have two more constructors, one that takes a
 * {@code Throwable} (the cause), and one that takes a
 * {@code String} (the detail message) and a {@code Throwable} (the
 * cause).
 *
 * <p>
 *  {@code Throwable}类是Java语言中所有错误和异常的超类。只有作为该类(或其子类之一)的实例的对象才会由Java虚拟机抛出,或者可以由Java {@code throw}语句抛出。
 * 类似地,只有这个类或其子类可以是{@code catch}子句中的参数类型。
 * 
 *  为了对异常进行编译时检查,{@code Throwable}和{@code Throwable}的任何子类都不是{@link RuntimeException}或{@link Error}的子类,都被
 * 视为异常。
 * 
 *  <p>两个子类{@link java.lang.Error}和{@link java.lang.Exception}的实例通常用于表示发生异常情况。
 * 通常,这些实例是在异常情况的上下文中新创建的,以便包括相关信息(例如堆栈跟踪数据)。
 * 
 * <p> throwable包含其创建时其线程的执行堆栈的快照。它还可以包含一个消息字符串,提供有关错误的更多信息。
 * 随着时间的推移,throwable可以{@linkplain Throwable#addSuppressed suppress}其他throwables不被传播。
 * 最后,throwable还可以包含一个<i> cause </i>：另一个throwable,它引起这个throwable被构造。
 * 这种因果信息的记录被称为"连锁的异常"设施,因为原因本身可能具有导致异常的"链"的原因等,每个异常由另一个异常引起。
 * 
 * 可抛弃物可能有原因的一个原因是抛出它的类是在较低层抽象的顶部构建的,并且由于较低层中的失败,上层的操作失败。使较低层抛出的可抛弃物向外传播是不好的设计,因为它通常与上层提供的抽象无关。
 * 此外,这样做将把上层的API绑定到其实现的细节,假定下层的异常是检查的异常。抛出"包装异常"(即,包含原因的异常)允许上层将故障的细节传达给其调用者,而不招致这些缺点中的任一个。
 * 它保留了改变上层的实现而不改变其API(特别是由其方法抛出的异常集合)的灵活性。
 * 
 * <p> throwable可能有原因的第二个原因是抛出它的方法必须符合通用接口,不允许该方法直接抛出原因。
 * 例如,假设持久化集合符合{@link java.util.Collection Collection}接口,并且其持久性在{@code java.io}上实现。
 * 假设{@code add}方法的内部可以抛出一个{@link java.io.IOException IOException}。
 * 实现可以通过在适当的未检查异常中包装{@code IOException}来将{@code IOException}的细节传达给其调用者,同时符合{@code Collection}接口。
 *  (持久性收集的规范应该表明它能够抛出这样的异常。)。
 * 
 *  <p>原因可以通过两种方式与throwable相关联：通过将原因作为参数的构造函数或通过{@link #initCause(Throwable)}方法。
 * 希望允许原因与它们相关联的新的可抛出类应该提供构造函数,这些构造函数接受一个原因并委托(或许间接)给一个接受原因的{@code Throwable}构造函数。
 * 
 *  因为{@code initCause}方法是公开的,它允许一个原因与任何可抛出的,甚至是一个"遗留可抛弃"相关联,其实现先于向{@code Throwable}添加异常链接机制。
 * 
 * 按照惯例,{@code Throwable}类及其子类有两个构造函数,一个不带参数,另一个采用{@code String}参数,用于生成详细消息。
 * 此外,那些可能具有与它们相关联的原因的子类应当具有两个构造函数,一个采用{@code Throwable}(原因),另一个采用{@code String}(详细消息)和{@code Throwable}
 * (原因)。
 * 按照惯例,{@code Throwable}类及其子类有两个构造函数,一个不带参数,另一个采用{@code String}参数,用于生成详细消息。
 * 
 * 
 * @author  unascribed
 * @author  Josh Bloch (Added exception chaining and programmatic access to
 *          stack trace in 1.4.)
 * @jls 11.2 Compile-Time Checking of Exceptions
 * @since JDK1.0
 */
public class Throwable implements Serializable {
    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -3042686055658047285L;

    /**
     * Native code saves some indication of the stack backtrace in this slot.
     * <p>
     *  本地代码保存了在这个槽中的堆栈回溯的一些指示。
     * 
     */
    private transient Object backtrace;

    /**
     * Specific details about the Throwable.  For example, for
     * {@code FileNotFoundException}, this contains the name of
     * the file that could not be found.
     *
     * <p>
     *  关于Throwable的具体细节。例如,对于{@code FileNotFoundException},它包含无法找到的文件的名称。
     * 
     * 
     * @serial
     */
    private String detailMessage;


    /**
     * Holder class to defer initializing sentinel objects only used
     * for serialization.
     * <p>
     *  Holder类,用于延迟初始化只用于序列化的哨兵对象。
     * 
     */
    private static class SentinelHolder {
        /**
         * {@linkplain #setStackTrace(StackTraceElement[]) Setting the
         * stack trace} to a one-element array containing this sentinel
         * value indicates future attempts to set the stack trace will be
         * ignored.  The sentinal is equal to the result of calling:<br>
         * {@code new StackTraceElement("", "", null, Integer.MIN_VALUE)}
         * <p>
         *  {@linkplain #setStackTrace(StackTraceElement [])将堆栈跟踪设置为包含此sentinel值的一个单元数组,表示未来尝试设置堆栈跟踪将被忽略。
         *  sentinal等于调用的结果：<br> {@code new StackTraceElement("","",null,Integer.MIN_VALUE)}。
         * 
         */
        public static final StackTraceElement STACK_TRACE_ELEMENT_SENTINEL =
            new StackTraceElement("", "", null, Integer.MIN_VALUE);

        /**
         * Sentinel value used in the serial form to indicate an immutable
         * stack trace.
         * <p>
         *  在串行形式中使用的哨兵值,用于指示不可变的堆栈跟踪。
         * 
         */
        public static final StackTraceElement[] STACK_TRACE_SENTINEL =
            new StackTraceElement[] {STACK_TRACE_ELEMENT_SENTINEL};
    }

    /**
     * A shared value for an empty stack.
     * <p>
     *  空堆栈的共享值。
     * 
     */
    private static final StackTraceElement[] UNASSIGNED_STACK = new StackTraceElement[0];

    /*
     * To allow Throwable objects to be made immutable and safely
     * reused by the JVM, such as OutOfMemoryErrors, fields of
     * Throwable that are writable in response to user actions, cause,
     * stackTrace, and suppressedExceptions obey the following
     * protocol:
     *
     * 1) The fields are initialized to a non-null sentinel value
     * which indicates the value has logically not been set.
     *
     * 2) Writing a null to the field indicates further writes
     * are forbidden
     *
     * 3) The sentinel value may be replaced with another non-null
     * value.
     *
     * For example, implementations of the HotSpot JVM have
     * preallocated OutOfMemoryError objects to provide for better
     * diagnosability of that situation.  These objects are created
     * without calling the constructor for that class and the fields
     * in question are initialized to null.  To support this
     * capability, any new fields added to Throwable that require
     * being initialized to a non-null value require a coordinated JVM
     * change.
     * <p>
     *  为了允许Throwable对象被JVM不可变和安全地重用(例如OutOfMemoryErrors),Throwable的字段可响应用户操作而写入,原因,stackTrace和suppressedExc
     * eptions遵守以下协议：。
     * 
     * 1)字段被初始化为非空的标记值,表示该值在逻辑上未设置。
     * 
     *  2)向字段写入空值表示禁止进一步写入
     * 
     *  3)标记值可以用另一非空值替换。
     * 
     *  例如,HotSpot JVM的实现已预先分配了OutOfMemoryError对象,以提供该情况的更好的诊断性。这些对象是在不调用该类的构造函数的情况下创建的,并且相关字段初始化为null。
     * 要支持此功能,添加到Throwable的任何新字段(需要初始化为非空值)都需要协调JVM更改。
     * 
     */

    /**
     * The throwable that caused this throwable to get thrown, or null if this
     * throwable was not caused by another throwable, or if the causative
     * throwable is unknown.  If this field is equal to this throwable itself,
     * it indicates that the cause of this throwable has not yet been
     * initialized.
     *
     * <p>
     *  throwable引起这个throwable被抛出,或者null如果这个throwable不是由另一个throwable引起的,或者如果原因throwable是未知的。
     * 如果此字段等于此throwable本身,则表示此可抛弃式的原因尚未初始化。
     * 
     * 
     * @serial
     * @since 1.4
     */
    private Throwable cause = this;

    /**
     * The stack trace, as returned by {@link #getStackTrace()}.
     *
     * The field is initialized to a zero-length array.  A {@code
     * null} value of this field indicates subsequent calls to {@link
     * #setStackTrace(StackTraceElement[])} and {@link
     * #fillInStackTrace()} will be be no-ops.
     *
     * <p>
     *  堆栈跟踪,由{@link #getStackTrace()}返回。
     * 
     *  该字段初始化为零长度数组。
     * 此字段的{@code null}值表示对{@link #setStackTrace(StackTraceElement [])}和{@link #fillInStackTrace()}的后续调用将为无操
     * 作。
     *  该字段初始化为零长度数组。
     * 
     * 
     * @serial
     * @since 1.4
     */
    private StackTraceElement[] stackTrace = UNASSIGNED_STACK;

    // Setting this static field introduces an acceptable
    // initialization dependency on a few java.util classes.
    private static final List<Throwable> SUPPRESSED_SENTINEL =
        Collections.unmodifiableList(new ArrayList<Throwable>(0));

    /**
     * The list of suppressed exceptions, as returned by {@link
     * #getSuppressed()}.  The list is initialized to a zero-element
     * unmodifiable sentinel list.  When a serialized Throwable is
     * read in, if the {@code suppressedExceptions} field points to a
     * zero-element list, the field is reset to the sentinel value.
     *
     * <p>
     * {@link #getSuppressed()}返回的禁止异常列表。该列表被初始化为零元素不可修改的哨兵列表。
     * 当读入序列化的Throwable时,如果{@code suppressedExceptions}字段指向零元素列表,则该字段将重置为sentinel值。
     * 
     * 
     * @serial
     * @since 1.7
     */
    private List<Throwable> suppressedExceptions = SUPPRESSED_SENTINEL;

    /** Message for trying to suppress a null exception. */
    private static final String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";

    /** Message for trying to suppress oneself. */
    private static final String SELF_SUPPRESSION_MESSAGE = "Self-suppression not permitted";

    /** Caption  for labeling causative exception stack traces */
    private static final String CAUSE_CAPTION = "Caused by: ";

    /** Caption for labeling suppressed exception stack traces */
    private static final String SUPPRESSED_CAPTION = "Suppressed: ";

    /**
     * Constructs a new throwable with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * <p>The {@link #fillInStackTrace()} method is called to initialize
     * the stack trace data in the newly created throwable.
     * <p>
     *  使用{@code null}作为其详细消息构造一个新的可抛弃项。原因未初始化,并可能随后通过调用{@link #initCause}初始化。
     * 
     *  <p>调用{@link #fillInStackTrace()}方法初始化新创建的throwable中的堆栈跟踪数据。
     * 
     */
    public Throwable() {
        fillInStackTrace();
    }

    /**
     * Constructs a new throwable with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * <p>The {@link #fillInStackTrace()} method is called to initialize
     * the stack trace data in the newly created throwable.
     *
     * <p>
     *  构造具有指定详细消息的新throwable。原因未初始化,并可能随后通过调用{@link #initCause}初始化。
     * 
     *  <p>调用{@link #fillInStackTrace()}方法初始化新创建的throwable中的堆栈跟踪数据。
     * 
     * 
     * @param   message   the detail message. The detail message is saved for
     *          later retrieval by the {@link #getMessage()} method.
     */
    public Throwable(String message) {
        fillInStackTrace();
        detailMessage = message;
    }

    /**
     * Constructs a new throwable with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this throwable's detail message.
     *
     * <p>The {@link #fillInStackTrace()} method is called to initialize
     * the stack trace data in the newly created throwable.
     *
     * <p>
     *  构造具有指定的详细消息和原因的新throwable。 <p>请注意,与{@code cause}相关联的详细信息</i>不会自动并入此可投诉的详细信息中。
     * 
     *  <p>调用{@link #fillInStackTrace()}方法初始化新创建的throwable中的堆栈跟踪数据。
     * 
     * 
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public Throwable(String message, Throwable cause) {
        fillInStackTrace();
        detailMessage = message;
        this.cause = cause;
    }

    /**
     * Constructs a new throwable with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}).
     * This constructor is useful for throwables that are little more than
     * wrappers for other throwables (for example, {@link
     * java.security.PrivilegedActionException}).
     *
     * <p>The {@link #fillInStackTrace()} method is called to initialize
     * the stack trace data in the newly created throwable.
     *
     * <p>
     * 构造一个具有指定原因和{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细消息)的详细消息的新throwable。
     * 这个构造函数对于其他throwable的包装器(例如,{@link java.security.PrivilegedActionException})只是很有用。
     * 
     *  <p>调用{@link #fillInStackTrace()}方法初始化新创建的throwable中的堆栈跟踪数据。
     * 
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public Throwable(Throwable cause) {
        fillInStackTrace();
        detailMessage = (cause==null ? null : cause.toString());
        this.cause = cause;
    }

    /**
     * Constructs a new throwable with the specified detail message,
     * cause, {@linkplain #addSuppressed suppression} enabled or
     * disabled, and writable stack trace enabled or disabled.  If
     * suppression is disabled, {@link #getSuppressed} for this object
     * will return a zero-length array and calls to {@link
     * #addSuppressed} that would otherwise append an exception to the
     * suppressed list will have no effect.  If the writable stack
     * trace is false, this constructor will not call {@link
     * #fillInStackTrace()}, a {@code null} will be written to the
     * {@code stackTrace} field, and subsequent calls to {@code
     * fillInStackTrace} and {@link
     * #setStackTrace(StackTraceElement[])} will not set the stack
     * trace.  If the writable stack trace is false, {@link
     * #getStackTrace} will return a zero length array.
     *
     * <p>Note that the other constructors of {@code Throwable} treat
     * suppression as being enabled and the stack trace as being
     * writable.  Subclasses of {@code Throwable} should document any
     * conditions under which suppression is disabled and document
     * conditions under which the stack trace is not writable.
     * Disabling of suppression should only occur in exceptional
     * circumstances where special requirements exist, such as a
     * virtual machine reusing exception objects under low-memory
     * situations.  Circumstances where a given exception object is
     * repeatedly caught and rethrown, such as to implement control
     * flow between two sub-systems, is another situation where
     * immutable throwable objects would be appropriate.
     *
     * <p>
     *  使用指定的详细消息构造一个新的可抛弃型数据库,因为,启用或禁用了{@linkplain #addSuppressed suppression},并启用或禁用了可写堆栈跟踪。
     * 如果禁用抑制,则此对象的{@link #getSuppressed}将返回零长度数组,并且对{@link #addSuppressed}的调用(否则会将异常附加到抑制列表)将不起作用。
     * 如果可写的栈跟踪为false,这个构造函数不会调用{@link #fillInStackTrace()},{@code null}将被写入{@code stackTrace}字段,然后调用{@code fillInStackTrace}
     *  {@link #setStackTrace(StackTraceElement [])}不会设置堆栈跟踪。
     * 如果禁用抑制,则此对象的{@link #getSuppressed}将返回零长度数组,并且对{@link #addSuppressed}的调用(否则会将异常附加到抑制列表)将不起作用。
     * 如果可写堆栈跟踪为false,{@link #getStackTrace}将返回零长度数组。
     * 
     * <p>请注意,{@code Throwable}的其他构造函数将抑制视为已启用,并将堆栈跟踪视为可写。 {@code Throwable}的子类应记录禁用抑制的任何条件以及堆栈跟踪不可写的文档条件。
     * 禁用抑制只应在存在特殊要求的特殊情况下发生,例如虚拟机在低内存情况下重用异常对象。
     * 给定异常对象被重复捕获和重新引用(例如实现两个子系统之间的控制流)的情况是另一种情况,其中不可变的可抛弃对象将是适当的。
     * 
     * 
     * @param  message the detail message.
     * @param cause the cause.  (A {@code null} value is permitted,
     * and indicates that the cause is nonexistent or unknown.)
     * @param enableSuppression whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be
     *                           writable
     *
     * @see OutOfMemoryError
     * @see NullPointerException
     * @see ArithmeticException
     * @since 1.7
     */
    protected Throwable(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        if (writableStackTrace) {
            fillInStackTrace();
        } else {
            stackTrace = null;
        }
        detailMessage = message;
        this.cause = cause;
        if (!enableSuppression)
            suppressedExceptions = null;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * <p>
     *  返回此throwable的详细消息字符串。
     * 
     * 
     * @return  the detail message string of this {@code Throwable} instance
     *          (which may be {@code null}).
     */
    public String getMessage() {
        return detailMessage;
    }

    /**
     * Creates a localized description of this throwable.
     * Subclasses may override this method in order to produce a
     * locale-specific message.  For subclasses that do not override this
     * method, the default implementation returns the same result as
     * {@code getMessage()}.
     *
     * <p>
     *  创建此可抛弃项的本地化描述。子类可以覆盖此方法,以便生成特定于语言环境的消息。对于不重写此方法的子类,默认实现返回与{@code getMessage()}相同的结果。
     * 
     * 
     * @return  The localized description of this throwable.
     * @since   JDK1.1
     */
    public String getLocalizedMessage() {
        return getMessage();
    }

    /**
     * Returns the cause of this throwable or {@code null} if the
     * cause is nonexistent or unknown.  (The cause is the throwable that
     * caused this throwable to get thrown.)
     *
     * <p>This implementation returns the cause that was supplied via one of
     * the constructors requiring a {@code Throwable}, or that was set after
     * creation with the {@link #initCause(Throwable)} method.  While it is
     * typically unnecessary to override this method, a subclass can override
     * it to return a cause set by some other means.  This is appropriate for
     * a "legacy chained throwable" that predates the addition of chained
     * exceptions to {@code Throwable}.  Note that it is <i>not</i>
     * necessary to override any of the {@code PrintStackTrace} methods,
     * all of which invoke the {@code getCause} method to determine the
     * cause of a throwable.
     *
     * <p>
     *  如果原因不存在或未知,则返回此throwable的原因或{@code null}。 (原因是throwable引起这个throwable被抛出。)
     * 
     * <p>此实现返回通过需要{@code Throwable}的构造函数或通过{@link #initCause(Throwable)}方法创建后设置的原因。
     * 虽然通常不必重写此方法,但子类可以覆盖它以返回通过某些其他方式设置的原因。这适用于"遗留链接可抛弃",它会将链接异常添加到{@code Throwable}。
     * 请注意,并非必须覆写任何{@code PrintStackTrace}方法,所有方法都会调用{@code getCause}方法来确定可抛弃项的原因。
     * 
     * 
     * @return  the cause of this throwable or {@code null} if the
     *          cause is nonexistent or unknown.
     * @since 1.4
     */
    public synchronized Throwable getCause() {
        return (cause==this ? null : cause);
    }

    /**
     * Initializes the <i>cause</i> of this throwable to the specified value.
     * (The cause is the throwable that caused this throwable to get thrown.)
     *
     * <p>This method can be called at most once.  It is generally called from
     * within the constructor, or immediately after creating the
     * throwable.  If this throwable was created
     * with {@link #Throwable(Throwable)} or
     * {@link #Throwable(String,Throwable)}, this method cannot be called
     * even once.
     *
     * <p>An example of using this method on a legacy throwable type
     * without other support for setting the cause is:
     *
     * <pre>
     * try {
     *     lowLevelOp();
     * } catch (LowLevelException le) {
     *     throw (HighLevelException)
     *           new HighLevelException().initCause(le); // Legacy constructor
     * }
     * </pre>
     *
     * <p>
     *  将此可抛弃项的<i>原因</i>初始化为指定的值。 (原因是throwable引起这个throwable被抛出。)
     * 
     *  <p>此方法最多可调用一次。它通常在构造函数内调用,或者在创建可抛出对象之后立即调用。
     * 如果这个throwable是用{@link #Throwable(Throwable)}或{@link #Throwable(String,Throwable)}创建的,这个方法不能被调用一次。
     * 
     *  <p>在没有其他支持设置原因的传统可抛出类型上使用此方法的示例是：
     * 
     * <pre>
     *  try {lowLevelOp(); } catch(LowLevelException le){throw(HighLevelException)new HighLevelException()。
     * initCause(le); // Legacy constructor}。
     * </pre>
     * 
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @return  a reference to this {@code Throwable} instance.
     * @throws IllegalArgumentException if {@code cause} is this
     *         throwable.  (A throwable cannot be its own cause.)
     * @throws IllegalStateException if this throwable was
     *         created with {@link #Throwable(Throwable)} or
     *         {@link #Throwable(String,Throwable)}, or this method has already
     *         been called on this throwable.
     * @since  1.4
     */
    public synchronized Throwable initCause(Throwable cause) {
        if (this.cause != this)
            throw new IllegalStateException("Can't overwrite cause with " +
                                            Objects.toString(cause, "a null"), this);
        if (cause == this)
            throw new IllegalArgumentException("Self-causation not permitted", this);
        this.cause = cause;
        return this;
    }

    /**
     * Returns a short description of this throwable.
     * The result is the concatenation of:
     * <ul>
     * <li> the {@linkplain Class#getName() name} of the class of this object
     * <li> ": " (a colon and a space)
     * <li> the result of invoking this object's {@link #getLocalizedMessage}
     *      method
     * </ul>
     * If {@code getLocalizedMessage} returns {@code null}, then just
     * the class name is returned.
     *
     * <p>
     *  返回此throwable的简短描述。结果是连接：
     * <ul>
     * <li>此对象类别的{@linkplain Class#getName()name} <li>"："(冒号和空格)<li>调用此对象的{@link #getLocalizedMessage}方法的结果。
     * </ul>
     *  如果{@code getLocalizedMessage}返回{@code null},则只返回类名。
     * 
     * 
     * @return a string representation of this throwable.
     */
    public String toString() {
        String s = getClass().getName();
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

    /**
     * Prints this throwable and its backtrace to the
     * standard error stream. This method prints a stack trace for this
     * {@code Throwable} object on the error output stream that is
     * the value of the field {@code System.err}. The first line of
     * output contains the result of the {@link #toString()} method for
     * this object.  Remaining lines represent data previously recorded by
     * the method {@link #fillInStackTrace()}. The format of this
     * information depends on the implementation, but the following
     * example may be regarded as typical:
     * <blockquote><pre>
     * java.lang.NullPointerException
     *         at MyClass.mash(MyClass.java:9)
     *         at MyClass.crunch(MyClass.java:6)
     *         at MyClass.main(MyClass.java:3)
     * </pre></blockquote>
     * This example was produced by running the program:
     * <pre>
     * class MyClass {
     *     public static void main(String[] args) {
     *         crunch(null);
     *     }
     *     static void crunch(int[] a) {
     *         mash(a);
     *     }
     *     static void mash(int[] b) {
     *         System.out.println(b[0]);
     *     }
     * }
     * </pre>
     * The backtrace for a throwable with an initialized, non-null cause
     * should generally include the backtrace for the cause.  The format
     * of this information depends on the implementation, but the following
     * example may be regarded as typical:
     * <pre>
     * HighLevelException: MidLevelException: LowLevelException
     *         at Junk.a(Junk.java:13)
     *         at Junk.main(Junk.java:4)
     * Caused by: MidLevelException: LowLevelException
     *         at Junk.c(Junk.java:23)
     *         at Junk.b(Junk.java:17)
     *         at Junk.a(Junk.java:11)
     *         ... 1 more
     * Caused by: LowLevelException
     *         at Junk.e(Junk.java:30)
     *         at Junk.d(Junk.java:27)
     *         at Junk.c(Junk.java:21)
     *         ... 3 more
     * </pre>
     * Note the presence of lines containing the characters {@code "..."}.
     * These lines indicate that the remainder of the stack trace for this
     * exception matches the indicated number of frames from the bottom of the
     * stack trace of the exception that was caused by this exception (the
     * "enclosing" exception).  This shorthand can greatly reduce the length
     * of the output in the common case where a wrapped exception is thrown
     * from same method as the "causative exception" is caught.  The above
     * example was produced by running the program:
     * <pre>
     * public class Junk {
     *     public static void main(String args[]) {
     *         try {
     *             a();
     *         } catch(HighLevelException e) {
     *             e.printStackTrace();
     *         }
     *     }
     *     static void a() throws HighLevelException {
     *         try {
     *             b();
     *         } catch(MidLevelException e) {
     *             throw new HighLevelException(e);
     *         }
     *     }
     *     static void b() throws MidLevelException {
     *         c();
     *     }
     *     static void c() throws MidLevelException {
     *         try {
     *             d();
     *         } catch(LowLevelException e) {
     *             throw new MidLevelException(e);
     *         }
     *     }
     *     static void d() throws LowLevelException {
     *        e();
     *     }
     *     static void e() throws LowLevelException {
     *         throw new LowLevelException();
     *     }
     * }
     *
     * class HighLevelException extends Exception {
     *     HighLevelException(Throwable cause) { super(cause); }
     * }
     *
     * class MidLevelException extends Exception {
     *     MidLevelException(Throwable cause)  { super(cause); }
     * }
     *
     * class LowLevelException extends Exception {
     * }
     * </pre>
     * As of release 7, the platform supports the notion of
     * <i>suppressed exceptions</i> (in conjunction with the {@code
     * try}-with-resources statement). Any exceptions that were
     * suppressed in order to deliver an exception are printed out
     * beneath the stack trace.  The format of this information
     * depends on the implementation, but the following example may be
     * regarded as typical:
     *
     * <pre>
     * Exception in thread "main" java.lang.Exception: Something happened
     *  at Foo.bar(Foo.java:10)
     *  at Foo.main(Foo.java:5)
     *  Suppressed: Resource$CloseFailException: Resource ID = 0
     *          at Resource.close(Resource.java:26)
     *          at Foo.bar(Foo.java:9)
     *          ... 1 more
     * </pre>
     * Note that the "... n more" notation is used on suppressed exceptions
     * just at it is used on causes. Unlike causes, suppressed exceptions are
     * indented beyond their "containing exceptions."
     *
     * <p>An exception can have both a cause and one or more suppressed
     * exceptions:
     * <pre>
     * Exception in thread "main" java.lang.Exception: Main block
     *  at Foo3.main(Foo3.java:7)
     *  Suppressed: Resource$CloseFailException: Resource ID = 2
     *          at Resource.close(Resource.java:26)
     *          at Foo3.main(Foo3.java:5)
     *  Suppressed: Resource$CloseFailException: Resource ID = 1
     *          at Resource.close(Resource.java:26)
     *          at Foo3.main(Foo3.java:5)
     * Caused by: java.lang.Exception: I did it
     *  at Foo3.main(Foo3.java:8)
     * </pre>
     * Likewise, a suppressed exception can have a cause:
     * <pre>
     * Exception in thread "main" java.lang.Exception: Main block
     *  at Foo4.main(Foo4.java:6)
     *  Suppressed: Resource2$CloseFailException: Resource ID = 1
     *          at Resource2.close(Resource2.java:20)
     *          at Foo4.main(Foo4.java:5)
     *  Caused by: java.lang.Exception: Rats, you caught me
     *          at Resource2$CloseFailException.&lt;init&gt;(Resource2.java:45)
     *          ... 2 more
     * </pre>
     * <p>
     *  将此可抛弃项及其回溯打印到标准错误流。此方法在错误输出流上为此{@code Throwable}对象打印一个堆栈跟踪,它是字段{@code System.err}的值。
     * 第一行输出包含此对象的{@link #toString()}方法的结果。剩余行表示先前通过方法{@link #fillInStackTrace()}记录的数据。
     * 此信息的格式取决于实现,但以下示例可能被视为典型：<blockquote> <Pre> java.lang.NullPointerException在MyClass.mash(MyClass.java:
     * 9)在MyClass.crunch(MyClass.java ：6)在MyClass.main(MyClass.java:3)</pre> </blockquote>这个例子是通过运行程序：。
     * 第一行输出包含此对象的{@link #toString()}方法的结果。剩余行表示先前通过方法{@link #fillInStackTrace()}记录的数据。
     * <pre>
     *  class MyClass {public static void main(String [] args){crunch(null); } static void crunch(int [] a){mash(a); }
     *  static void mash(int [] b){System.out.println(b [0]); }}。
     * </pre>
     *  带有初始化的非空原因的throwable的backtrace应该通常包括原因的回溯。此信息的格式取决于实现,但以下示例可能被视为典型：
     * <pre>
     * HighLevelException：MidLevelException：在Junk.a(Junk.java:13)在Junk.main(Junk.java:4)的LowLevelException：导
     * 致：MidLevelException：LowLevelException在Junk.c(Junk.java:23)在Junk.b .java：17)in Junk.a(Junk.java:11)​​.
     * .. 1另请参见：Junk.e(Junk.java:30)在Junk.d(Junk.java:27)的LowLevelException在Junk。
     *  c(Junk.java:21)...更多。
     * </pre>
     *  注意包含字符{@code"..."}的行的存在。这些行指示此异常的堆栈跟踪的其余部分与从此异常("封闭"异常)引起的异常的堆栈跟踪的底部指示的帧数匹配。
     * 在常见情况下,这种缩写可以大大减少输出的长度,其中从捕获"原因异常"的相同方法抛出包装异常。上面的例子是通过运行程序产生的：。
     * <pre>
     *  public class Junk {public static void main(String args []){try {a(); } catch(HighLevelException e){e.printStackTrace(); }
     * } static void a()throws HighLevelException {try {b(); } catch(MidLevelException e){throw new HighLevelException(e); }
     * } static void b()throws MidLevelException {c(); } static void c()throws MidLevelException {try {d(); }
     *  catch(LowLevelException e){throw new MidLevelException(e); }} static void d()throws LowLevelExceptio
     * n {e(); } static void e()throws LowLevelException {throw new LowLevelException(); }}。
     * 
     * class HighLevelException extends Exception {HighLevelException(Throwable cause){super(cause); }}
     * 
     *  class MidLevelException extends Exception {MidLevelException(Throwable cause){super(cause); }}
     * 
     *  class LowLevelException extends Exception {}
     * </pre>
     *  从版本7开始,平台支持<i>抑制例外</i>(与{@code try} -with-resources语句一起)的概念。为了传递异常而被抑制的任何异常都会在堆栈跟踪下面打印出来。
     * 此信息的格式取决于实现,但以下示例可能被视为典型：。
     * 
     * <pre>
     *  线程"main"中的异常java.lang.Exception：Foo.bar(Foo.java:10)在Foo.main(Foo.java:5)发生的事情抑制：Resource $ CloseFai
     * lException：Resource.close = 0 at Resource.close (Resource.java:26)at Foo.bar(Foo.java:9)...另外1个。
     * </pre>
     *  注意,"... n more"符号用于抑制异常,只是在原因中使用它。与原因不同,抑制异常缩进超出其"包含异常"。
     * 
     *  <p>异常可以同时具有原因和一个或多个抑制异常：
     * <pre>
     * 线程"main"中的异常java.lang.Exception：Foo3.main处的主块(Foo3.java:7)抑制：Resource $ CloseFailException：Resource0 
     * = Resource.close(Resource.java:26)at Foo3.main (Foo3.java:5)禁止：资源$ CloseFailException：Resource.coin(R
     * esource.java:26)的资源ID = 1在Foo3.main(Foo3.java:5)导致：java.lang.Exception：它在Foo3.main(Foo3.java:8)。
     * </pre>
     *  同样,被抑制的异常可能有一个原因：
     * <pre>
     *  线程"main"中的异常java.lang.Exception：Foo4.main处的主块(Foo4.java:6)抑制：Resource2 $ CloseFailException：Resource
     * 2.close(Resource2.java:20)在Foo4.main处的资源ID = 1 (Foo4.java:5)Caused by：java.lang.Exception：Rats,你抓住了我在
     * Resource2 $ CloseFailException。
     * &lt; init&gt;(Resource2.java:45)... 2另。
     * </pre>
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /**
     * Prints this throwable and its backtrace to the specified print stream.
     *
     * <p>
     *  将此可抛弃项及其回溯打印到指定的打印流。
     * 
     * 
     * @param s {@code PrintStream} to use for output
     */
    public void printStackTrace(PrintStream s) {
        printStackTrace(new WrappedPrintStream(s));
    }

    private void printStackTrace(PrintStreamOrWriter s) {
        // Guard against malicious overrides of Throwable.equals by
        // using a Set with identity equality semantics.
        Set<Throwable> dejaVu =
            Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
        dejaVu.add(this);

        synchronized (s.lock()) {
            // Print our stack trace
            s.println(this);
            StackTraceElement[] trace = getOurStackTrace();
            for (StackTraceElement traceElement : trace)
                s.println("\tat " + traceElement);

            // Print suppressed exceptions, if any
            for (Throwable se : getSuppressed())
                se.printEnclosedStackTrace(s, trace, SUPPRESSED_CAPTION, "\t", dejaVu);

            // Print cause, if any
            Throwable ourCause = getCause();
            if (ourCause != null)
                ourCause.printEnclosedStackTrace(s, trace, CAUSE_CAPTION, "", dejaVu);
        }
    }

    /**
     * Print our stack trace as an enclosed exception for the specified
     * stack trace.
     * <p>
     *  打印我们的堆栈跟踪作为指定堆栈跟踪的封闭异常。
     * 
     */
    private void printEnclosedStackTrace(PrintStreamOrWriter s,
                                         StackTraceElement[] enclosingTrace,
                                         String caption,
                                         String prefix,
                                         Set<Throwable> dejaVu) {
        assert Thread.holdsLock(s.lock());
        if (dejaVu.contains(this)) {
            s.println("\t[CIRCULAR REFERENCE:" + this + "]");
        } else {
            dejaVu.add(this);
            // Compute number of frames in common between this and enclosing trace
            StackTraceElement[] trace = getOurStackTrace();
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >=0 && trace[m].equals(enclosingTrace[n])) {
                m--; n--;
            }
            int framesInCommon = trace.length - 1 - m;

            // Print our stack trace
            s.println(prefix + caption + this);
            for (int i = 0; i <= m; i++)
                s.println(prefix + "\tat " + trace[i]);
            if (framesInCommon != 0)
                s.println(prefix + "\t... " + framesInCommon + " more");

            // Print suppressed exceptions, if any
            for (Throwable se : getSuppressed())
                se.printEnclosedStackTrace(s, trace, SUPPRESSED_CAPTION,
                                           prefix +"\t", dejaVu);

            // Print cause, if any
            Throwable ourCause = getCause();
            if (ourCause != null)
                ourCause.printEnclosedStackTrace(s, trace, CAUSE_CAPTION, prefix, dejaVu);
        }
    }

    /**
     * Prints this throwable and its backtrace to the specified
     * print writer.
     *
     * <p>
     *  将此可抛弃项及其回溯打印到指定的打印作者。
     * 
     * 
     * @param s {@code PrintWriter} to use for output
     * @since   JDK1.1
     */
    public void printStackTrace(PrintWriter s) {
        printStackTrace(new WrappedPrintWriter(s));
    }

    /**
     * Wrapper class for PrintStream and PrintWriter to enable a single
     * implementation of printStackTrace.
     * <p>
     *  PrintStream和PrintWriter的包装器类,以启用printStackTrace的单个实现。
     * 
     */
    private abstract static class PrintStreamOrWriter {
        /** Returns the object to be locked when using this StreamOrWriter */
        abstract Object lock();

        /** Prints the specified string as a line on this StreamOrWriter */
        abstract void println(Object o);
    }

    private static class WrappedPrintStream extends PrintStreamOrWriter {
        private final PrintStream printStream;

        WrappedPrintStream(PrintStream printStream) {
            this.printStream = printStream;
        }

        Object lock() {
            return printStream;
        }

        void println(Object o) {
            printStream.println(o);
        }
    }

    private static class WrappedPrintWriter extends PrintStreamOrWriter {
        private final PrintWriter printWriter;

        WrappedPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        Object lock() {
            return printWriter;
        }

        void println(Object o) {
            printWriter.println(o);
        }
    }

    /**
     * Fills in the execution stack trace. This method records within this
     * {@code Throwable} object information about the current state of
     * the stack frames for the current thread.
     *
     * <p>If the stack trace of this {@code Throwable} {@linkplain
     * Throwable#Throwable(String, Throwable, boolean, boolean) is not
     * writable}, calling this method has no effect.
     *
     * <p>
     *  填充执行堆栈跟踪。此方法在此{@code Throwable}对象信息中记录当前线程的堆栈帧的当前状态。
     * 
     *  <p>如果此{@code Throwable} {@linkplain Throwable#Throwable(String,Throwable,boolean,boolean)的堆栈跟踪不可写},则
     * 调用此方法不起作用。
     * 
     * 
     * @return  a reference to this {@code Throwable} instance.
     * @see     java.lang.Throwable#printStackTrace()
     */
    public synchronized Throwable fillInStackTrace() {
        if (stackTrace != null ||
            backtrace != null /* Out of protocol state */ ) {
            fillInStackTrace(0);
            stackTrace = UNASSIGNED_STACK;
        }
        return this;
    }

    private native Throwable fillInStackTrace(int dummy);

    /**
     * Provides programmatic access to the stack trace information printed by
     * {@link #printStackTrace()}.  Returns an array of stack trace elements,
     * each representing one stack frame.  The zeroth element of the array
     * (assuming the array's length is non-zero) represents the top of the
     * stack, which is the last method invocation in the sequence.  Typically,
     * this is the point at which this throwable was created and thrown.
     * The last element of the array (assuming the array's length is non-zero)
     * represents the bottom of the stack, which is the first method invocation
     * in the sequence.
     *
     * <p>Some virtual machines may, under some circumstances, omit one
     * or more stack frames from the stack trace.  In the extreme case,
     * a virtual machine that has no stack trace information concerning
     * this throwable is permitted to return a zero-length array from this
     * method.  Generally speaking, the array returned by this method will
     * contain one element for every frame that would be printed by
     * {@code printStackTrace}.  Writes to the returned array do not
     * affect future calls to this method.
     *
     * <p>
     * fillInStackTrace(0); stackTrace = UNASSIGNED_STACK; } return this; }}
     * 
     *  private local Throwable fillInStackTrace(int dummy);
     * 
     *  / **提供对由{@link #printStackTrace()}打印的堆栈跟踪信息的编程访问。返回堆栈跟踪元素的数组,每个元素表示一个堆栈帧。
     * 数组的第零个元素(假设数组的长度不为零)表示堆栈的顶部,这是序列中的最后一个方法调用。通常,这是创建和抛出此可抛弃物的点。
     * 数组的最后一个元素(假设数组的长度不为零)表示堆栈的底部,这是序列中的第一个方法调用。
     * 
     *  <p>在某些情况下,一些虚拟机可能会从堆栈跟踪中省略一个或多个堆栈帧。在极端情况下,允许没有关于该可抛弃物的堆栈跟踪信息的虚拟机从该方法返回零长度数组。
     * 一般来说,此方法返回的数组将为每个由{@code printStackTrace}打印的帧包含一个元素。对返回的数组的写操作不会影响对此方法的未来调用。
     * 
     * 
     * @return an array of stack trace elements representing the stack trace
     *         pertaining to this throwable.
     * @since  1.4
     */
    public StackTraceElement[] getStackTrace() {
        return getOurStackTrace().clone();
    }

    private synchronized StackTraceElement[] getOurStackTrace() {
        // Initialize stack trace field with information from
        // backtrace if this is the first call to this method
        if (stackTrace == UNASSIGNED_STACK ||
            (stackTrace == null && backtrace != null) /* Out of protocol state */) {
            int depth = getStackTraceDepth();
            stackTrace = new StackTraceElement[depth];
            for (int i=0; i < depth; i++)
                stackTrace[i] = getStackTraceElement(i);
        } else if (stackTrace == null) {
            return UNASSIGNED_STACK;
        }
        return stackTrace;
    }

    /**
     * Sets the stack trace elements that will be returned by
     * {@link #getStackTrace()} and printed by {@link #printStackTrace()}
     * and related methods.
     *
     * This method, which is designed for use by RPC frameworks and other
     * advanced systems, allows the client to override the default
     * stack trace that is either generated by {@link #fillInStackTrace()}
     * when a throwable is constructed or deserialized when a throwable is
     * read from a serialization stream.
     *
     * <p>If the stack trace of this {@code Throwable} {@linkplain
     * Throwable#Throwable(String, Throwable, boolean, boolean) is not
     * writable}, calling this method has no effect other than
     * validating its argument.
     *
     * <p>
     *  int depth = getStackTraceDepth(); stackTrace = new StackTraceElement [depth]; for(int i = 0; i <depth; i ++)stackTrace [i] = getStackTraceElement(i); } else if(stackTrace == null){return UNASSIGNED_STACK; } return stackTrace; }}。
     * 
     * / **设置由{@link #getStackTrace()}返回并由{@link #printStackTrace()}和相关方法打印的堆栈跟踪元素。
     * 
     *  此方法专为RPC框架和其他高级系统使用,允许客户端覆盖由{@link #fillInStackTrace()}生成的默认堆栈跟踪,当throwable被构造或反序列化时,当一个throwable被读取
     * 从串行化流。
     * 
     *  <p>如果此{@code Throwable} {@linkplain Throwable#Throwable(String,Throwable,boolean,boolean)的堆栈跟踪不可写},那
     * 么除非验证其参数,否则调用此方法没有任何效果。
     * 
     * 
     * @param   stackTrace the stack trace elements to be associated with
     * this {@code Throwable}.  The specified array is copied by this
     * call; changes in the specified array after the method invocation
     * returns will have no affect on this {@code Throwable}'s stack
     * trace.
     *
     * @throws NullPointerException if {@code stackTrace} is
     *         {@code null} or if any of the elements of
     *         {@code stackTrace} are {@code null}
     *
     * @since  1.4
     */
    public void setStackTrace(StackTraceElement[] stackTrace) {
        // Validate argument
        StackTraceElement[] defensiveCopy = stackTrace.clone();
        for (int i = 0; i < defensiveCopy.length; i++) {
            if (defensiveCopy[i] == null)
                throw new NullPointerException("stackTrace[" + i + "]");
        }

        synchronized (this) {
            if (this.stackTrace == null && // Immutable stack
                backtrace == null) // Test for out of protocol state
                return;
            this.stackTrace = defensiveCopy;
        }
    }

    /**
     * Returns the number of elements in the stack trace (or 0 if the stack
     * trace is unavailable).
     *
     * package-protection for use by SharedSecrets.
     * <p>
     *  返回堆栈跟踪中的元素数(如果堆栈跟踪不可用,则返回0)。
     * 
     *  包保护以供SharedSecrets使用。
     * 
     */
    native int getStackTraceDepth();

    /**
     * Returns the specified element of the stack trace.
     *
     * package-protection for use by SharedSecrets.
     *
     * <p>
     *  返回堆栈跟踪的指定元素。
     * 
     *  包保护以供SharedSecrets使用。
     * 
     * 
     * @param index index of the element to return.
     * @throws IndexOutOfBoundsException if {@code index < 0 ||
     *         index >= getStackTraceDepth() }
     */
    native StackTraceElement getStackTraceElement(int index);

    /**
     * Reads a {@code Throwable} from a stream, enforcing
     * well-formedness constraints on fields.  Null entries and
     * self-pointers are not allowed in the list of {@code
     * suppressedExceptions}.  Null entries are not allowed for stack
     * trace elements.  A null stack trace in the serial form results
     * in a zero-length stack element array. A single-element stack
     * trace whose entry is equal to {@code new StackTraceElement("",
     * "", null, Integer.MIN_VALUE)} results in a {@code null} {@code
     * stackTrace} field.
     *
     * Note that there are no constraints on the value the {@code
     * cause} field can hold; both {@code null} and {@code this} are
     * valid values for the field.
     * <p>
     *  从流中读取{@code Throwable},强制对字段执行良好的形式约束。在{@code suppressedExceptions}的列表中不允许空条目和自指针。不允许对堆栈跟踪元素使用空值。
     * 串行形式的空栈跟踪导致零长度的堆栈元素数组。
     * 单元素堆栈跟踪的条目等于{@code new StackTraceElement("","",null,Integer.MIN_VALUE)}会产生一个{@code null} {@code stackTrace}
     * 字段。
     * 串行形式的空栈跟踪导致零长度的堆栈元素数组。
     * 
     * 注意,对{@code cause}字段可以保存的值没有约束; {@code null}和{@code this}都是字段的有效值。
     * 
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();     // read in all fields
        if (suppressedExceptions != null) {
            List<Throwable> suppressed = null;
            if (suppressedExceptions.isEmpty()) {
                // Use the sentinel for a zero-length list
                suppressed = SUPPRESSED_SENTINEL;
            } else { // Copy Throwables to new list
                suppressed = new ArrayList<>(1);
                for (Throwable t : suppressedExceptions) {
                    // Enforce constraints on suppressed exceptions in
                    // case of corrupt or malicious stream.
                    if (t == null)
                        throw new NullPointerException(NULL_CAUSE_MESSAGE);
                    if (t == this)
                        throw new IllegalArgumentException(SELF_SUPPRESSION_MESSAGE);
                    suppressed.add(t);
                }
            }
            suppressedExceptions = suppressed;
        } // else a null suppressedExceptions field remains null

        /*
         * For zero-length stack traces, use a clone of
         * UNASSIGNED_STACK rather than UNASSIGNED_STACK itself to
         * allow identity comparison against UNASSIGNED_STACK in
         * getOurStackTrace.  The identity of UNASSIGNED_STACK in
         * stackTrace indicates to the getOurStackTrace method that
         * the stackTrace needs to be constructed from the information
         * in backtrace.
         * <p>
         *  对于零长度堆栈跟踪,使用UNASSIGNED_STACK的克隆而不是UNASSIGNED_STACK本身来允许与getOurStackTrace中的UNASSIGNED_STACK进行身份比较。
         *  stackTrace中的UNASSIGNED_STACK的标识向getOurStackTrace方法指示,stackTrace需要从backtrace中的信息构造。
         * 
         */
        if (stackTrace != null) {
            if (stackTrace.length == 0) {
                stackTrace = UNASSIGNED_STACK.clone();
            }  else if (stackTrace.length == 1 &&
                        // Check for the marker of an immutable stack trace
                        SentinelHolder.STACK_TRACE_ELEMENT_SENTINEL.equals(stackTrace[0])) {
                stackTrace = null;
            } else { // Verify stack trace elements are non-null.
                for(StackTraceElement ste : stackTrace) {
                    if (ste == null)
                        throw new NullPointerException("null StackTraceElement in serial stream. ");
                }
            }
        } else {
            // A null stackTrace field in the serial form can result
            // from an exception serialized without that field in
            // older JDK releases; treat such exceptions as having
            // empty stack traces.
            stackTrace = UNASSIGNED_STACK.clone();
        }
    }

    /**
     * Write a {@code Throwable} object to a stream.
     *
     * A {@code null} stack trace field is represented in the serial
     * form as a one-element array whose element is equal to {@code
     * new StackTraceElement("", "", null, Integer.MIN_VALUE)}.
     * <p>
     *  向流中写入{@code Throwable}对象。
     * 
     *  {@code null}堆栈跟踪字段以串联形式表示为一个单元素数组,其元素等于{@code new StackTraceElement("","",null,Integer.MIN_VALUE)}。
     * 
     */
    private synchronized void writeObject(ObjectOutputStream s)
        throws IOException {
        // Ensure that the stackTrace field is initialized to a
        // non-null value, if appropriate.  As of JDK 7, a null stack
        // trace field is a valid value indicating the stack trace
        // should not be set.
        getOurStackTrace();

        StackTraceElement[] oldStackTrace = stackTrace;
        try {
            if (stackTrace == null)
                stackTrace = SentinelHolder.STACK_TRACE_SENTINEL;
            s.defaultWriteObject();
        } finally {
            stackTrace = oldStackTrace;
        }
    }

    /**
     * Appends the specified exception to the exceptions that were
     * suppressed in order to deliver this exception. This method is
     * thread-safe and typically called (automatically and implicitly)
     * by the {@code try}-with-resources statement.
     *
     * <p>The suppression behavior is enabled <em>unless</em> disabled
     * {@linkplain #Throwable(String, Throwable, boolean, boolean) via
     * a constructor}.  When suppression is disabled, this method does
     * nothing other than to validate its argument.
     *
     * <p>Note that when one exception {@linkplain
     * #initCause(Throwable) causes} another exception, the first
     * exception is usually caught and then the second exception is
     * thrown in response.  In other words, there is a causal
     * connection between the two exceptions.
     *
     * In contrast, there are situations where two independent
     * exceptions can be thrown in sibling code blocks, in particular
     * in the {@code try} block of a {@code try}-with-resources
     * statement and the compiler-generated {@code finally} block
     * which closes the resource.
     *
     * In these situations, only one of the thrown exceptions can be
     * propagated.  In the {@code try}-with-resources statement, when
     * there are two such exceptions, the exception originating from
     * the {@code try} block is propagated and the exception from the
     * {@code finally} block is added to the list of exceptions
     * suppressed by the exception from the {@code try} block.  As an
     * exception unwinds the stack, it can accumulate multiple
     * suppressed exceptions.
     *
     * <p>An exception may have suppressed exceptions while also being
     * caused by another exception.  Whether or not an exception has a
     * cause is semantically known at the time of its creation, unlike
     * whether or not an exception will suppress other exceptions
     * which is typically only determined after an exception is
     * thrown.
     *
     * <p>Note that programmer written code is also able to take
     * advantage of calling this method in situations where there are
     * multiple sibling exceptions and only one can be propagated.
     *
     * <p>
     *  将指定的异常附加到为了传递此异常而被禁止的异常。此方法是线程安全的,通常由{@code try} -with-resources语句调用(自动和隐式地)。
     * 
     *  <p>启用抑制行为<em>除非</em>已通过构造函数禁用{@linkplain #Throwable(String,Throwable,boolean,boolean)。
     * 禁用抑制时,此方法不执行任何其他操作来验证其参数。
     * 
     *  <p>请注意,当一个异常{@linkplain #initCause(Throwable)导致}另一个异常时,通常会捕获第一个异常,然后响应抛出第二个异常。换句话说,两个异常之间存在因果关系。
     * 
     * 相反,存在两种独立的异常可以在同步代码块中引起的情况,特别是在{@code try} -with-resources语句的{@code try}块和编译器生成的{@code finally}块关闭资源。
     * 
     *  在这些情况下,只能传播其中一个抛出的异常。
     * 在{@code try} -with-resources语句中,当有两个这样的异常时,来自{@code try}块的异常被传播,来自{@code finally}块的异常被添加到异常被{@code try}
     * 块中的异常抑制。
     * 
     * @param exception the exception to be added to the list of
     *        suppressed exceptions
     * @throws IllegalArgumentException if {@code exception} is this
     *         throwable; a throwable cannot suppress itself.
     * @throws NullPointerException if {@code exception} is {@code null}
     * @since 1.7
     */
    public final synchronized void addSuppressed(Throwable exception) {
        if (exception == this)
            throw new IllegalArgumentException(SELF_SUPPRESSION_MESSAGE, exception);

        if (exception == null)
            throw new NullPointerException(NULL_CAUSE_MESSAGE);

        if (suppressedExceptions == null) // Suppressed exceptions not recorded
            return;

        if (suppressedExceptions == SUPPRESSED_SENTINEL)
            suppressedExceptions = new ArrayList<>(1);

        suppressedExceptions.add(exception);
    }

    private static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];

    /**
     * Returns an array containing all of the exceptions that were
     * suppressed, typically by the {@code try}-with-resources
     * statement, in order to deliver this exception.
     *
     * If no exceptions were suppressed or {@linkplain
     * #Throwable(String, Throwable, boolean, boolean) suppression is
     * disabled}, an empty array is returned.  This method is
     * thread-safe.  Writes to the returned array do not affect future
     * calls to this method.
     *
     * <p>
     *  在这些情况下,只能传播其中一个抛出的异常。作为例外,展开堆栈,它可以累积多个抑制的异常。
     * 
     *  <p>异常可能会抑制异常,同时也是由另一个异常引起的。异常是否具有原因在其创建时在语义上是已知的,不同于异常是否将抑制通常仅在抛出异常之后才确定的其它异常。
     * 
     *  <p>请注意,程序员编写的代码还能够在存在多个同类异常并且只能传播一个异常异常的情况下调用此方法。
     * 
     * 
     * @return an array containing all of the exceptions that were
     *         suppressed to deliver this exception.
     * @since 1.7
     */
    public final synchronized Throwable[] getSuppressed() {
        if (suppressedExceptions == SUPPRESSED_SENTINEL ||
            suppressedExceptions == null)
            return EMPTY_THROWABLE_ARRAY;
        else
            return suppressedExceptions.toArray(EMPTY_THROWABLE_ARRAY);
    }
}
