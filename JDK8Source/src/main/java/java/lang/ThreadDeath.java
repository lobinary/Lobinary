/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * An instance of {@code ThreadDeath} is thrown in the victim thread
 * when the (deprecated) {@link Thread#stop()} method is invoked.
 *
 * <p>An application should catch instances of this class only if it
 * must clean up after being terminated asynchronously.  If
 * {@code ThreadDeath} is caught by a method, it is important that it
 * be rethrown so that the thread actually dies.
 *
 * <p>The {@linkplain ThreadGroup#uncaughtException top-level error
 * handler} does not print out a message if {@code ThreadDeath} is
 * never caught.
 *
 * <p>The class {@code ThreadDeath} is specifically a subclass of
 * {@code Error} rather than {@code Exception}, even though it is a
 * "normal occurrence", because many applications catch all
 * occurrences of {@code Exception} and then discard the exception.
 *
 * <p>
 *  当调用(不推荐使用){@link Thread#stop()}方法时,受害者线程中会抛出{@code ThreadDeath}的实例。
 * 
 *  <p>应用程序应该捕获此类的实例,只有当它异步终止后必须清理。如果{@code ThreadDeath}被一个方法捕获,重要的是它被重新抛出,以便线程实际上死了。
 * 
 *  <p>如果{@code ThreadDeath}从未被捕获,则{@linkplain ThreadGroup#uncaughtException顶级错误处理程序}不会输出消息。
 * 
 * @since   JDK1.0
 */

public class ThreadDeath extends Error {
    private static final long serialVersionUID = -4417128565033088268L;
}
