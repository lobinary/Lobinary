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
  * This is the superclass of security-related exceptions
  * thrown by operations in the Context and DirContext interfaces.
  * The nature of the failure is described by the name of the subclass.
  *<p>
  * If the program wants to handle this exception in particular, it
  * should catch NamingSecurityException explicitly before attempting to
  * catch NamingException. A program might want to do this, for example,
  * if it wants to treat security-related exceptions specially from
  * other sorts of naming exception.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  这是Context和DirContext接口中的操作抛出的安全相关异常的超类。故障的性质由子类的名称描述。
  * p>
  *  如果程序想特别处理这个异常,它应该在捕获NamingException之前显式地捕获NamingSecurityException。
  * 程序可能想要这样做,例如,如果它想特别地处理来自其他种类的命名异常的安全相关的异常。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public abstract class NamingSecurityException extends NamingException {
    /**
     * Constructs a new instance of NamingSecurityException using the
     * explanation supplied. All other fields default to null.
     *
     * <p>
     *  使用提供的解释构造NamingSecurityException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public NamingSecurityException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of NamingSecurityException.
      * All fields are initialized to null.
      * <p>
      *  构造NamingSecurityException的新实例。所有字段都初始化为null。
      * 
      */
    public NamingSecurityException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 5855287647294685775L;
};
