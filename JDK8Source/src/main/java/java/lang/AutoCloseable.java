/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * An object that may hold resources (such as file or socket handles)
 * until it is closed. The {@link #close()} method of an {@code AutoCloseable}
 * object is called automatically when exiting a {@code
 * try}-with-resources block for which the object has been declared in
 * the resource specification header. This construction ensures prompt
 * release, avoiding resource exhaustion exceptions and errors that
 * may otherwise occur.
 *
 * @apiNote
 * <p>It is possible, and in fact common, for a base class to
 * implement AutoCloseable even though not all of its subclasses or
 * instances will hold releasable resources.  For code that must operate
 * in complete generality, or when it is known that the {@code AutoCloseable}
 * instance requires resource release, it is recommended to use {@code
 * try}-with-resources constructions. However, when using facilities such as
 * {@link java.util.stream.Stream} that support both I/O-based and
 * non-I/O-based forms, {@code try}-with-resources blocks are in
 * general unnecessary when using non-I/O-based forms.
 *
 * <p>
 *  可能包含资源(例如文件或套接字句柄)的对象,直到它被关闭。
 * 当退出对象已在资源规范头中声明的{@code try} -with-resources块时,会自动调用{@code AutoCloseable}对象的{@link #close()}方法。
 * 此结构确保快速释放,避免资源耗尽异常和错误,否则可能会发生。
 * 
 *  @apiNote <p>尽管并非所有的子类或实例都拥有可释放的资源,但基类可以实现AutoCloseable,这实际上很普遍。
 * 对于必须以完全通用性操作的代码,或者当知道{@code AutoCloseable}实例需要资源释放时,建议使用{@code try} -with-resources结构。
 * 但是,当使用支持基于I / O和非基于I / O的表单的{@link java.util.stream.Stream}等设施时,通常不需要{@code try} -with-resources块当使用非
 * 基于I / O的形式。
 * 对于必须以完全通用性操作的代码,或者当知道{@code AutoCloseable}实例需要资源释放时,建议使用{@code try} -with-resources结构。
 * 
 * 
 * @author Josh Bloch
 * @since 1.7
 */
public interface AutoCloseable {
    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {@code try}-with-resources statement.
     *
     * <p>While this interface method is declared to throw {@code
     * Exception}, implementers are <em>strongly</em> encouraged to
     * declare concrete implementations of the {@code close} method to
     * throw more specific exceptions, or to throw no exception at all
     * if the close operation cannot fail.
     *
     * <p> Cases where the close operation may fail require careful
     * attention by implementers. It is strongly advised to relinquish
     * the underlying resources and to internally <em>mark</em> the
     * resource as closed, prior to throwing the exception. The {@code
     * close} method is unlikely to be invoked more than once and so
     * this ensures that the resources are released in a timely manner.
     * Furthermore it reduces problems that could arise when the resource
     * wraps, or is wrapped, by another resource.
     *
     * <p><em>Implementers of this interface are also strongly advised
     * to not have the {@code close} method throw {@link
     * InterruptedException}.</em>
     *
     * This exception interacts with a thread's interrupted status,
     * and runtime misbehavior is likely to occur if an {@code
     * InterruptedException} is {@linkplain Throwable#addSuppressed
     * suppressed}.
     *
     * More generally, if it would cause problems for an
     * exception to be suppressed, the {@code AutoCloseable.close}
     * method should not throw it.
     *
     * <p>Note that unlike the {@link java.io.Closeable#close close}
     * method of {@link java.io.Closeable}, this {@code close} method
     * is <em>not</em> required to be idempotent.  In other words,
     * calling this {@code close} method more than once may have some
     * visible side effect, unlike {@code Closeable.close} which is
     * required to have no effect if called more than once.
     *
     * However, implementers of this interface are strongly encouraged
     * to make their {@code close} methods idempotent.
     *
     * <p>
     *  关闭此资源,放弃任何基础资源。此方法将自动在由{@code try} -with-resources语句管理的对象上调用。
     * 
     * <p>虽然这个接口方法被声明为抛出{@code Exception},但是我们鼓励实现者强烈地声明{@code close}方法的具体实现来抛出更多特定的异常,或者抛出异常,如果关闭操作不能失败。
     * 
     *  <p>关闭操作可能失败的情况需要实施者仔细注意。强烈建议放弃底层资源,并在抛出异常之前将内部资源标记为已关闭。
     * <em> </em> {@code close}方法不太可能被多次调用,因此这可确保资源及时发布。此外,它减少了当资源包裹或被另一个资源包裹时可能出现的问题。
     * 
     *  <p> <em>强烈建议您不要使用此界面的实施者{@code close}方法抛出{@link InterruptedException}。</em>
     * 
     *  这个异常与线程的中断状态相互作用,如果{@code InterruptedException}是{@linkplain Throwable#addSuppressed suppressed},则可能会
     * 
     * @throws Exception if this resource cannot be closed
     */
    void close() throws Exception;
}
