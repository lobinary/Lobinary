/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * This exception may be thrown by methods that have detected concurrent
 * modification of an object when such modification is not permissible.
 * <p>
 * For example, it is not generally permissible for one thread to modify a Collection
 * while another thread is iterating over it.  In general, the results of the
 * iteration are undefined under these circumstances.  Some Iterator
 * implementations (including those of all the general purpose collection implementations
 * provided by the JRE) may choose to throw this exception if this behavior is
 * detected.  Iterators that do this are known as <i>fail-fast</i> iterators,
 * as they fail quickly and cleanly, rather that risking arbitrary,
 * non-deterministic behavior at an undetermined time in the future.
 * <p>
 * Note that this exception does not always indicate that an object has
 * been concurrently modified by a <i>different</i> thread.  If a single
 * thread issues a sequence of method invocations that violates the
 * contract of an object, the object may throw this exception.  For
 * example, if a thread modifies a collection directly while it is
 * iterating over the collection with a fail-fast iterator, the iterator
 * will throw this exception.
 *
 * <p>Note that fail-fast behavior cannot be guaranteed as it is, generally
 * speaking, impossible to make any hard guarantees in the presence of
 * unsynchronized concurrent modification.  Fail-fast operations
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>{@code ConcurrentModificationException}
 * should be used only to detect bugs.</i>
 *
 * <p>
 *  当不允许这种修改时,可能会由检测到对象的并发修改的方法抛出此异常。
 * <p>
 *  例如,通常不允许一个线程修改集合,而另一个线程正在迭代它。一般来说,迭代的结果在这些情况下是未定义的。
 * 一些Iterator实现(包括JRE提供的所有通用集合实现的实现)可能会选择在检测到此行为时抛出此异常。
 * 执行此操作的迭代器称为<i> fail-fast </i>迭代器,因为它们快速而干净地失败,而是冒着未来未确定时间的任意,非确定性行为的风险。
 * <p>
 *  请注意,此异常并不总是表示某个对象已被<i>不同的</i>线程同时修改。如果单个线程发出一系列违反对象契约的方法调用,那么对象可能会抛出此异常。
 * 例如,如果线程在使用fail-fast迭代器迭代集合时直接修改集合,则迭代器将抛出此异常。
 * 
 * <p>请注意,不能保证故障快速行为,因为一般来说,在不同步并发修改的情况下不可能做出任何硬的保证。
 * 故障快速操作以尽力而为的方式抛出{@code ConcurrentModificationException}。
 * 因此,编写依赖于此异常的程序的正确性是错误的：<i> {@ code ConcurrentModificationException}应该仅用于检测错误。</i>。
 * 
 * 
 * @author  Josh Bloch
 * @see     Collection
 * @see     Iterator
 * @see     Spliterator
 * @see     ListIterator
 * @see     Vector
 * @see     LinkedList
 * @see     HashSet
 * @see     Hashtable
 * @see     TreeMap
 * @see     AbstractList
 * @since   1.2
 */
public class ConcurrentModificationException extends RuntimeException {
    private static final long serialVersionUID = -3666751008965953603L;

    /**
     * Constructs a ConcurrentModificationException with no
     * detail message.
     * <p>
     *  构造一个没有详细消息的ConcurrentModificationException。
     * 
     */
    public ConcurrentModificationException() {
    }

    /**
     * Constructs a {@code ConcurrentModificationException} with the
     * specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的{@code ConcurrentModificationException}。
     * 
     * 
     * @param message the detail message pertaining to this exception.
     */
    public ConcurrentModificationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of {@code (cause==null ? null : cause.toString())} (which
     * typically contains the class and detail message of {@code cause}.
     *
     * <p>
     *  使用指定的原因和{@code(cause == null?null：cause.toString())}(通常包含{@code cause}的类和详细信息)的详细消息构造新异常。
     * 
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.7
     */
    public ConcurrentModificationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * <p>Note that the detail message associated with <code>cause</code> is
     * <i>not</i> automatically incorporated in this exception's detail
     * message.
     *
     * <p>
     *  构造具有指定的详细消息和原因的新异常。
     * 
     * 
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link Throwable#getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link Throwable#getCause()} method).  (A {@code null} value
     *         is permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since 1.7
     */
    public ConcurrentModificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
