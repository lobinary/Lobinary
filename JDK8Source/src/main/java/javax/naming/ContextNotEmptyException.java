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
  * This exception is thrown when attempting to destroy a context that
  * is not empty.
  *<p>
  * If the program wants to handle this exception in particular, it
  * should catch ContextNotEmptyException explicitly before attempting to
  * catch NamingException. For example, after catching ContextNotEmptyException,
  * the program might try to remove the contents of the context before
  * reattempting the destroy.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  尝试销毁非空的上下文时抛出此异常。
  * p>
  *  如果程序想特别处理这个异常,它应该在捕获NamingException之前显式捕获ContextNotEmptyException。
  * 例如,捕获ContextNotEmptyException后,程序可能会尝试在重新尝试销毁之前删除上下文的内容。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see Context#destroySubcontext
  * @since 1.3
  */
public class ContextNotEmptyException extends NamingException {
    /**
     * Constructs a new instance of ContextNotEmptyException using an
     * explanation. All other fields default to null.
     *
     * <p>
     *  使用解释构造ContextNotEmptyException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null string containing
     * additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public ContextNotEmptyException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of ContextNotEmptyException with
      * all name resolution fields and explanation initialized to null.
      * <p>
      *  构造ContextNotEmptyException的新实例,并将所有名称解析字段和说明初始化为null。
      * 
      */
    public ContextNotEmptyException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 1090963683348219877L;
}
