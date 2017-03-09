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
 * Thrown when a thread is waiting, sleeping, or otherwise occupied,
 * and the thread is interrupted, either before or during the activity.
 * Occasionally a method may wish to test whether the current
 * thread has been interrupted, and if so, to immediately throw
 * this exception.  The following code can be used to achieve
 * this effect:
 * <pre>
 *  if (Thread.interrupted())  // Clears interrupted status!
 *      throw new InterruptedException();
 * </pre>
 *
 * <p>
 *  当线程等待,睡眠或以其他方式占用时抛出,并且线程在活动之前或期间中断。偶尔,一个方法可能希望测试当前线程是否已经中断,如果是,则立即抛出此异常。下面的代码可以用来实现这个效果：
 * <pre>
 *  if(Thread.interrupted())//清除中断状态！ throw new InterruptedException();
 * </pre>
 * 
 * 
 * @author  Frank Yellin
 * @see     java.lang.Object#wait()
 * @see     java.lang.Object#wait(long)
 * @see     java.lang.Object#wait(long, int)
 * @see     java.lang.Thread#sleep(long)
 * @see     java.lang.Thread#interrupt()
 * @see     java.lang.Thread#interrupted()
 * @since   JDK1.0
 */
public
class InterruptedException extends Exception {
    private static final long serialVersionUID = 6700697376100628473L;

    /**
     * Constructs an <code>InterruptedException</code> with no detail  message.
     * <p>
     *  构造一个没有详细消息的<code> InterruptedException </code>。
     * 
     */
    public InterruptedException() {
        super();
    }

    /**
     * Constructs an <code>InterruptedException</code> with the
     * specified detail message.
     *
     * <p>
     *  用指定的详细消息构造一个<code> InterruptedException </code>。
     * 
     * @param   s   the detail message.
     */
    public InterruptedException(String s) {
        super(s);
    }
}
