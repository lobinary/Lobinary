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
  * This exception is thrown to indicate that the result being returned
  * or returned so far is partial, and that the operation cannot
  * be completed.  For example, when listing a context, this exception
  * indicates that returned results only represents some of the bindings
  * in the context.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  抛出此异常以指示到目前为止返回或返回的结果是部分的,并且无法完成操作。例如,列出上下文时,此异常指示返回的结果仅表示上下文中的某些绑定。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class PartialResultException extends NamingException {
    /**
      * Constructs a new instance of the exception using the explanation
      * message specified. All other fields default to null.
      *
      * <p>
      *  使用指定的解释消息构造异常的新实例。所有其他字段默认为null。
      * 
      * 
      * @param  explanation     Possibly null detail explaining the exception.
      */
    public PartialResultException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of PartialResultException.
      * All fields default to null.
      * <p>
      *  构造一个PartialResultException的新实例。所有字段默认为null。
      * 
      */
    public PartialResultException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 2572144970049426786L;
}
