/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.util.Vector;
import sun.awt.AppContext;

/**
 * A queue of text layout tasks.
 *
 * <p>
 *  文本布局任务的队列。
 * 
 * 
 * @author  Timothy Prinzing
 * @see     AsyncBoxView
 * @since   1.3
 */
public class LayoutQueue {

    private static final Object DEFAULT_QUEUE = new Object();

    private Vector<Runnable> tasks;
    private Thread worker;

    /**
     * Construct a layout queue.
     * <p>
     *  构造布局队列。
     * 
     */
    public LayoutQueue() {
        tasks = new Vector<Runnable>();
    }

    /**
     * Fetch the default layout queue.
     * <p>
     *  获取默认布局队列。
     * 
     */
    public static LayoutQueue getDefaultQueue() {
        AppContext ac = AppContext.getAppContext();
        synchronized (DEFAULT_QUEUE) {
            LayoutQueue defaultQueue = (LayoutQueue) ac.get(DEFAULT_QUEUE);
            if (defaultQueue == null) {
                defaultQueue = new LayoutQueue();
                ac.put(DEFAULT_QUEUE, defaultQueue);
            }
            return defaultQueue;
        }
    }

    /**
     * Set the default layout queue.
     *
     * <p>
     *  设置默认布局队列。
     * 
     * 
     * @param q the new queue.
     */
    public static void setDefaultQueue(LayoutQueue q) {
        synchronized (DEFAULT_QUEUE) {
            AppContext.getAppContext().put(DEFAULT_QUEUE, q);
        }
    }

    /**
     * Add a task that is not needed immediately because
     * the results are not believed to be visible.
     * <p>
     *  添加不需要立即执行的任务,因为不认为结果是可见的。
     * 
     */
    public synchronized void addTask(Runnable task) {
        if (worker == null) {
            worker = new LayoutThread();
            worker.start();
        }
        tasks.addElement(task);
        notifyAll();
    }

    /**
     * Used by the worker thread to get a new task to execute
     * <p>
     *  由工作线程使用来获取一个新任务来执行
     * 
     */
    protected synchronized Runnable waitForWork() {
        while (tasks.size() == 0) {
            try {
                wait();
            } catch (InterruptedException ie) {
                return null;
            }
        }
        Runnable work = tasks.firstElement();
        tasks.removeElementAt(0);
        return work;
    }

    /**
     * low priority thread to perform layout work forever
     * <p>
     *  低优先级线程来执行布局工作永远
     */
    class LayoutThread extends Thread {

        LayoutThread() {
            super("text-layout");
            setPriority(Thread.MIN_PRIORITY);
        }

        public void run() {
            Runnable work;
            do {
                work = waitForWork();
                if (work != null) {
                    work.run();
                }
            } while (work != null);
        }


    }

}
