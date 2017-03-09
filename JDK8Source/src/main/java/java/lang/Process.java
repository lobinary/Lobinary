/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2012, Oracle and/or its affiliates. All rights reserved.
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
import java.util.concurrent.TimeUnit;

/**
 * The {@link ProcessBuilder#start()} and
 * {@link Runtime#exec(String[],String[],File) Runtime.exec}
 * methods create a native process and return an instance of a
 * subclass of {@code Process} that can be used to control the process
 * and obtain information about it.  The class {@code Process}
 * provides methods for performing input from the process, performing
 * output to the process, waiting for the process to complete,
 * checking the exit status of the process, and destroying (killing)
 * the process.
 *
 * <p>The methods that create processes may not work well for special
 * processes on certain native platforms, such as native windowing
 * processes, daemon processes, Win16/DOS processes on Microsoft
 * Windows, or shell scripts.
 *
 * <p>By default, the created subprocess does not have its own terminal
 * or console.  All its standard I/O (i.e. stdin, stdout, stderr)
 * operations will be redirected to the parent process, where they can
 * be accessed via the streams obtained using the methods
 * {@link #getOutputStream()},
 * {@link #getInputStream()}, and
 * {@link #getErrorStream()}.
 * The parent process uses these streams to feed input to and get output
 * from the subprocess.  Because some native platforms only provide
 * limited buffer size for standard input and output streams, failure
 * to promptly write the input stream or read the output stream of
 * the subprocess may cause the subprocess to block, or even deadlock.
 *
 * <p>Where desired, <a href="ProcessBuilder.html#redirect-input">
 * subprocess I/O can also be redirected</a>
 * using methods of the {@link ProcessBuilder} class.
 *
 * <p>The subprocess is not killed when there are no more references to
 * the {@code Process} object, but rather the subprocess
 * continues executing asynchronously.
 *
 * <p>There is no requirement that a process represented by a {@code
 * Process} object execute asynchronously or concurrently with respect
 * to the Java process that owns the {@code Process} object.
 *
 * <p>As of 1.5, {@link ProcessBuilder#start()} is the preferred way
 * to create a {@code Process}.
 *
 * <p>
 *  {@link ProcessBuilder#start()}和{@link Runtime#exec(String [],String [],File)Runtime.exec}方法创建一个本地进程并
 * 返回{@code Process}其可以用于控制过程并获得关于它的信息。
 * 类{@code Process}提供了从进程执行输入,执行到进程的输出,等待进程完成,检查进程的退出状态以及销毁(杀死)进程的方法。
 * 
 *  <p>创建进程的方法对某些本机平台上的特殊进程(例如本机窗口进程,守护进程,Microsoft Windows上的Win16 / DOS进程或shell脚本)可能无效。
 * 
 * <p>默认情况下,创建的子进程没有自己的终端或控制台。
 * 所有其标准I / O(即stdin,stdout,stderr)操作将被重定向到父进程,在那里他们可以通过使用方法{@link #getOutputStream()},{@link #getInputStream )}
 * 和{@link #getErrorStream()}。
 * <p>默认情况下,创建的子进程没有自己的终端或控制台。父进程使用这些流向子进程提供输入和从子进程获取输出。
 * 由于一些本地平台仅为标准输入和输出流提供有限的缓冲区大小,因此无法及时写入输入流或读取子过程的输出流可能导致子过程阻塞甚至死锁。
 * 
 *  <p>如果需要,还可以使用{@link ProcessBuilder}类的方法重定向<a href="ProcessBuilder.html#redirect-input">子流程I / O。
 * 
 *  <p>当没有对{@code Process}对象的更多引用时,子进程不会被终止,而是子进程继续异步运行。
 * 
 *  <p>没有要求由{@code Process}对象表示的进程相对于拥有{@code Process}对象的Java进程异步或并发执行。
 * 
 *  <p>从1.5开始,{@link ProcessBuilder#start()}是创建{@code Process}的首选方式。
 * 
 * 
 * @since   JDK1.0
 */
public abstract class Process {
    /**
     * Returns the output stream connected to the normal input of the
     * subprocess.  Output to the stream is piped into the standard
     * input of the process represented by this {@code Process} object.
     *
     * <p>If the standard input of the subprocess has been redirected using
     * {@link ProcessBuilder#redirectInput(Redirect)
     * ProcessBuilder.redirectInput}
     * then this method will return a
     * <a href="ProcessBuilder.html#redirect-input">null output stream</a>.
     *
     * <p>Implementation note: It is a good idea for the returned
     * output stream to be buffered.
     *
     * <p>
     *  返回连接到子过程的正常输入的输出流。对流的输出被管道传送到由此{@code Process}对象表示的过程的标准输入中。
     * 
     * <p>如果子流程的标准输入已使用{@link ProcessBuilder#redirectInput(Redirect)ProcessBuilder.redirectInput}重定向,则此方法将返回
     * <a href="ProcessBuilder.html#redirect-input"> null输出流</a>。
     * 
     *  <p>实现注意事项：对于返回的输出流进行缓冲是一个好主意。
     * 
     * 
     * @return the output stream connected to the normal input of the
     *         subprocess
     */
    public abstract OutputStream getOutputStream();

    /**
     * Returns the input stream connected to the normal output of the
     * subprocess.  The stream obtains data piped from the standard
     * output of the process represented by this {@code Process} object.
     *
     * <p>If the standard output of the subprocess has been redirected using
     * {@link ProcessBuilder#redirectOutput(Redirect)
     * ProcessBuilder.redirectOutput}
     * then this method will return a
     * <a href="ProcessBuilder.html#redirect-output">null input stream</a>.
     *
     * <p>Otherwise, if the standard error of the subprocess has been
     * redirected using
     * {@link ProcessBuilder#redirectErrorStream(boolean)
     * ProcessBuilder.redirectErrorStream}
     * then the input stream returned by this method will receive the
     * merged standard output and the standard error of the subprocess.
     *
     * <p>Implementation note: It is a good idea for the returned
     * input stream to be buffered.
     *
     * <p>
     *  返回连接到子过程的正常输出的输入流。流从由{@code Process}对象表示的进程的标准输出中获得管道传送的数据。
     * 
     *  <p>如果子流程的标准输出已使用{@link ProcessBuilder#redirectOutput(Redirect)ProcessBuilder.redirectOutput}重定向,则此方法
     * 将返回<a href="ProcessBuilder.html#redirect-output"> null输入流</a>。
     * 
     *  <p>否则,如果子进程的标准错误已使用{@link ProcessBuilder#redirectErrorStream(boolean)ProcessBuilder.redirectErrorStream}
     * 重定向,则此方法返回的输入流将接收合并的标准输出和子进程的标准错误。
     * 
     *  <p>实现注意：对于返回的输入流进行缓冲是一个好主意。
     * 
     * 
     * @return the input stream connected to the normal output of the
     *         subprocess
     */
    public abstract InputStream getInputStream();

    /**
     * Returns the input stream connected to the error output of the
     * subprocess.  The stream obtains data piped from the error output
     * of the process represented by this {@code Process} object.
     *
     * <p>If the standard error of the subprocess has been redirected using
     * {@link ProcessBuilder#redirectError(Redirect)
     * ProcessBuilder.redirectError} or
     * {@link ProcessBuilder#redirectErrorStream(boolean)
     * ProcessBuilder.redirectErrorStream}
     * then this method will return a
     * <a href="ProcessBuilder.html#redirect-output">null input stream</a>.
     *
     * <p>Implementation note: It is a good idea for the returned
     * input stream to be buffered.
     *
     * <p>
     *  返回连接到子过程的错误输出的输入流。流获得从由此{@code Process}对象表示的过程的错误输出管道传送的数据。
     * 
     * <p>如果子进程的标准错误已使用{@link ProcessBuilder#redirectError(Redirect)ProcessBuilder.redirectError}或{@link ProcessBuilder#redirectErrorStream(boolean)ProcessBuilder.redirectErrorStream}
     * 重定向,则此方法将返回<a href ="ProcessBuilder.html#redirect-output"> null输入流</a>。
     * 
     *  <p>实现注意：对于返回的输入流进行缓冲是一个好主意。
     * 
     * 
     * @return the input stream connected to the error output of
     *         the subprocess
     */
    public abstract InputStream getErrorStream();

    /**
     * Causes the current thread to wait, if necessary, until the
     * process represented by this {@code Process} object has
     * terminated.  This method returns immediately if the subprocess
     * has already terminated.  If the subprocess has not yet
     * terminated, the calling thread will be blocked until the
     * subprocess exits.
     *
     * <p>
     *  使当前线程等待,如果必要,直到由{@code Process}对象表示的进程已终止。如果子进程已经终止,此方法立即返回。如果子进程尚未终止,则调用线程将被阻塞,直到子进程退出。
     * 
     * 
     * @return the exit value of the subprocess represented by this
     *         {@code Process} object.  By convention, the value
     *         {@code 0} indicates normal termination.
     * @throws InterruptedException if the current thread is
     *         {@linkplain Thread#interrupt() interrupted} by another
     *         thread while it is waiting, then the wait is ended and
     *         an {@link InterruptedException} is thrown.
     */
    public abstract int waitFor() throws InterruptedException;

    /**
     * Causes the current thread to wait, if necessary, until the
     * subprocess represented by this {@code Process} object has
     * terminated, or the specified waiting time elapses.
     *
     * <p>If the subprocess has already terminated then this method returns
     * immediately with the value {@code true}.  If the process has not
     * terminated and the timeout value is less than, or equal to, zero, then
     * this method returns immediately with the value {@code false}.
     *
     * <p>The default implementation of this methods polls the {@code exitValue}
     * to check if the process has terminated. Concrete implementations of this
     * class are strongly encouraged to override this method with a more
     * efficient implementation.
     *
     * <p>
     *  使当前线程等待,直到由此{@code Process}对象表示的子进程终止或指定的等待时间过去。
     * 
     *  <p>如果子进程已经终止,则此方法立即返回值{@code true}。如果进程尚未终止,并且超时值小于或等于零,则此方法将立即返回值{@code false}。
     * 
     *  <p>此方法的默认实现轮询{@code exitValue}以检查进程是否已终止。强烈建议使用此类的具体实现以更有效的实现覆盖此方法。
     * 
     * 
     * @param timeout the maximum time to wait
     * @param unit the time unit of the {@code timeout} argument
     * @return {@code true} if the subprocess has exited and {@code false} if
     *         the waiting time elapsed before the subprocess has exited.
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting.
     * @throws NullPointerException if unit is null
     * @since 1.8
     */
    public boolean waitFor(long timeout, TimeUnit unit)
        throws InterruptedException
    {
        long startTime = System.nanoTime();
        long rem = unit.toNanos(timeout);

        do {
            try {
                exitValue();
                return true;
            } catch(IllegalThreadStateException ex) {
                if (rem > 0)
                    Thread.sleep(
                        Math.min(TimeUnit.NANOSECONDS.toMillis(rem) + 1, 100));
            }
            rem = unit.toNanos(timeout) - (System.nanoTime() - startTime);
        } while (rem > 0);
        return false;
    }

    /**
     * Returns the exit value for the subprocess.
     *
     * <p>
     *  返回子过程的退出值。
     * 
     * 
     * @return the exit value of the subprocess represented by this
     *         {@code Process} object.  By convention, the value
     *         {@code 0} indicates normal termination.
     * @throws IllegalThreadStateException if the subprocess represented
     *         by this {@code Process} object has not yet terminated
     */
    public abstract int exitValue();

    /**
     * Kills the subprocess. Whether the subprocess represented by this
     * {@code Process} object is forcibly terminated or not is
     * implementation dependent.
     * <p>
     * 杀死子进程。由此{@code Process}对象表示的子过程是否被强制终止与实现相关。
     * 
     */
    public abstract void destroy();

    /**
     * Kills the subprocess. The subprocess represented by this
     * {@code Process} object is forcibly terminated.
     *
     * <p>The default implementation of this method invokes {@link #destroy}
     * and so may not forcibly terminate the process. Concrete implementations
     * of this class are strongly encouraged to override this method with a
     * compliant implementation.  Invoking this method on {@code Process}
     * objects returned by {@link ProcessBuilder#start} and
     * {@link Runtime#exec} will forcibly terminate the process.
     *
     * <p>Note: The subprocess may not terminate immediately.
     * i.e. {@code isAlive()} may return true for a brief period
     * after {@code destroyForcibly()} is called. This method
     * may be chained to {@code waitFor()} if needed.
     *
     * <p>
     *  杀死子进程。由此{@code Process}对象表示的子过程被强制终止。
     * 
     *  <p>此方法的默认实现调用{@link #destroy},因此不能强制终止该过程。强烈建议使用此类的具体实现来使用兼容实现覆盖此方法。
     * 在{@link ProcessBuilder#start}和{@link Runtime#exec}返回的{@code Process}对象上调用此方法将强制终止该过程。
     * 
     *  <p>注意：子进程可能不会立即终止。即{@code isAlive()}可能在调用{@code destroyForcibly()}后的短暂时间内返回true。
     * 
     * @return the {@code Process} object representing the
     *         subprocess to be forcibly destroyed.
     * @since 1.8
     */
    public Process destroyForcibly() {
        destroy();
        return this;
    }

    /**
     * Tests whether the subprocess represented by this {@code Process} is
     * alive.
     *
     * <p>
     * 如果需要,这个方法可以链接到{@code waitFor()}。
     * 
     * 
     * @return {@code true} if the subprocess represented by this
     *         {@code Process} object has not yet terminated.
     * @since 1.8
     */
    public boolean isAlive() {
        try {
            exitValue();
            return false;
        } catch(IllegalThreadStateException e) {
            return true;
        }
    }
}
