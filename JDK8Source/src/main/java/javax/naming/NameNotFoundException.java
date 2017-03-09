/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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
  * This exception is thrown when a component of the name cannot be resolved
  * because it is not bound.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当名称的组件因为未绑定而无法解析时抛出此异常。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class NameNotFoundException extends NamingException {
    /**
     * Constructs a new instance of NameNotFoundException using the
     * explanation supplied. All other fields default to null.
     *
     * <p>
     *  使用提供的解释构造NameNotFoundException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about
     *                          this exception.
     * @see java.lang.Throwable#getMessage
     */
    public NameNotFoundException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of NameNotFoundException.
      * all name resolution fields and explanation initialized to null.
      * <p>
      *  构造一个新的NameNotFoundException实例。所有名称解析字段和说明初始化为null。
      * 
      */
    public NameNotFoundException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -8007156725367842053L;
}
