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

/**
 * The <code>Runnable</code> interface should be implemented by any
 * class whose instances are intended to be executed by a thread. The
 * class must define a method of no arguments called <code>run</code>.
 * <p>
 * This interface is designed to provide a common protocol for objects that
 * wish to execute code while they are active. For example,
 * <code>Runnable</code> is implemented by class <code>Thread</code>.
 * Being active simply means that a thread has been started and has not
 * yet been stopped.
 * <p>
 * In addition, <code>Runnable</code> provides the means for a class to be
 * active while not subclassing <code>Thread</code>. A class that implements
 * <code>Runnable</code> can run without subclassing <code>Thread</code>
 * by instantiating a <code>Thread</code> instance and passing itself in
 * as the target.  In most cases, the <code>Runnable</code> interface should
 * be used if you are only planning to override the <code>run()</code>
 * method and no other <code>Thread</code> methods.
 * This is important because classes should not be subclassed
 * unless the programmer intends on modifying or enhancing the fundamental
 * behavior of the class.
 *
 * <p>
 *  <code> Runnable </code>接口应该由任何类实现,其实例旨在由线程执行。该类必须定义一个没有参数<code> run </code>的方法。
 * <p>
 *  此接口旨在为希望在活动时执行代码的对象提供公共协议。例如,<code> Runnable </code>由类<code> Thread </code>实现。
 * 处于活动状态仅仅意味着线程已经启动并且尚未停止。
 * <p>
 *  此外,<code> Runnable </code>提供了一个类处于活动状态的方法,而不是子类化<code> Thread </code>。
 * 实现<code> Runnable </code>的类可以通过实例化<code> Thread </code>实例并将其作为目标传递,而不用子类化<code> Thread </code>在大多数情况下
 * ,如果你只打算覆盖<code> run()</code>方法而没有其他<code> Thread </code>方法,那么应该使用<code> Runnable </code>这很重要,因为类不应该是子
 * 
 * @author  Arthur van Hoff
 * @see     java.lang.Thread
 * @see     java.util.concurrent.Callable
 * @since   JDK1.0
 */
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * <p>
     * 类,除非程序员打算修改或增强类的基本行为。
     *  此外,<code> Runnable </code>提供了一个类处于活动状态的方法,而不是子类化<code> Thread </code>。
     * 
     * 
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
