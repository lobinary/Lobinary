/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: ThreadControllerWrapper.java,v 1.2.4.1 2005/09/15 08:15:59 suresh_emailid Exp $
 * <p>
 *  $ Id：ThreadControllerWrapper.java,v 1.2.4.1 2005/09/15 08:15:59 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * A utility class that wraps the ThreadController, which is used
 * by IncrementalSAXSource for the incremental building of DTM.
 * <p>
 *  一个包装ThreadController的实用程序类,由IncrementalSAXSource用于增量构建DTM。
 * 
 */
public class ThreadControllerWrapper
{

  /** The ThreadController pool   */
  private static ThreadController m_tpool = new ThreadController();

  public static Thread runThread(Runnable runnable, int priority)
  {
    return m_tpool.run(runnable, priority);
  }

  public static void waitThread(Thread worker, Runnable task)
    throws InterruptedException
  {
    m_tpool.waitThread(worker, task);
  }

  /**
   * Thread controller utility class for incremental SAX source. Must
   * be overriden with a derived class to support thread pooling.
   *
   * All thread-related stuff is in this class.
   * <p>
   *  增量SAX源的线程控制器实用程序类。必须用一个派生类来覆盖以支持线程池。
   * 
   *  所有与线程相关的东西都在这个类中。
   * 
   */
  public static class ThreadController
  {

    /**
     * This class was introduced as a fix for CR 6607339.
     * <p>
     *  此类作为CR 6607339的修订引入。
     * 
     */
    final class SafeThread extends Thread {
         private volatile boolean ran = false;

         public SafeThread(Runnable target) {
             super(target);
         }

         public final void run() {
             if (Thread.currentThread() != this) {
                 throw new IllegalStateException("The run() method in a"
                     + " SafeThread cannot be called from another thread.");
             }
             synchronized (this) {
                if (!ran) {
                    ran = true;
                }
                else {
                 throw new IllegalStateException("The run() method in a"
                     + " SafeThread cannot be called more than once.");
                 }
             }
             super.run();
         }
    }

    /**
     * Will get a thread from the pool, execute the task
     *  and return the thread to the pool.
     *
     *  The return value is used only to wait for completion
     *
     *
     * <p>
     *  将从池中获取线程,执行任务并将线程返回到池。
     * 
     *  返回值仅用于等待完成
     * 
     * 
     * NEEDSDOC @param task
     * @param priority if >0 the task will run with the given priority
     *  ( doesn't seem to be used in xalan, since it's allways the default )
     * @return  The thread that is running the task, can be used
     *          to wait for completion
     */
    public Thread run(Runnable task, int priority)
    {

      Thread t = new SafeThread(task);

      t.start();

      //       if( priority > 0 )
      //      t.setPriority( priority );
      return t;
    }

    /**
     *  Wait until the task is completed on the worker
     *  thread.
     *
     * <p>
     * 
     * NEEDSDOC @param worker
     * NEEDSDOC @param task
     *
     * @throws InterruptedException
     */
    public void waitThread(Thread worker, Runnable task)
            throws InterruptedException
    {

      // This should wait until the transformThread is considered not alive.
      worker.join();
    }
  }

}
