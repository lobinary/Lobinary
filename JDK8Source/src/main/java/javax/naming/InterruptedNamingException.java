/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming;

/**
  * This exception is thrown when the naming operation
  * being invoked has been interrupted. For example, an application
  * might interrupt a thread that is performing a search. If the
  * search supports being interrupted, it will throw
  * InterruptedNamingException. Whether an operation is interruptible
  * and when depends on its implementation (as provided by the
  * service providers). Different implementations have different ways
  * of protecting their resources and objects from being damaged
  * due to unexpected interrupts.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当调用的命名操作已中断时,抛出此异常。例如,应用程序可能会中断正在执行搜索的线程。如果搜索支持中断,它将抛出InterruptedNamingException。
  * 操作是否可中断,以及何时取决于其实现(由服务提供商提供)。不同的实现具有不同的方式来保护它们的资源和对象免于由于意外的中断而被损坏。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see Context
  * @see javax.naming.directory.DirContext
  * @see java.lang.Thread#interrupt
  * @see java.lang.InterruptedException
  * @since 1.3
  */

public class InterruptedNamingException extends NamingException {
    /**
      * Constructs an instance of InterruptedNamingException using an
      * explanation of the problem.
      * All name resolution-related fields are initialized to null.
      * <p>
      *  使用对问题的解释构造InterruptedNamingException的实例。所有与名称解析相关的字段都初始化为null。
      * 
      * 
      * @param explanation      A possibly null message explaining the problem.
      * @see java.lang.Throwable#getMessage
      */
    public InterruptedNamingException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs an instance of InterruptedNamingException with
      * all name resolution fields and explanation initialized to null.
      * <p>
      *  构造具有所有名称解析字段和说明的InterruptedNamingException的实例初始化为null。
      * 
      */
    public InterruptedNamingException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 6404516648893194728L;
}
