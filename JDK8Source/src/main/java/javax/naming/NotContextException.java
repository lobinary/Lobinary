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
  * This exception is thrown when a naming operation proceeds to a point
  * where a context is required to continue the operation, but the
  * resolved object is not a context. For example, Context.destroy() requires
  * that the named object be a context. If it is not, NotContextException
  * is thrown. Another example is a non-context being encountered during
  * the resolution phase of the Context methods.
  *<p>
  * It is also thrown when a particular subtype of context is required,
  * such as a DirContext, and the resolved object is a context but not of
  * the required subtype.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  * <p>
  *  当命名操作进行到需要上下文以继续操作的点,但解析的对象不是上下文时,抛出此异常。例如,Context.destroy()要求命名对象是上下文。如果不是,则抛出NotContextException。
  * 另一个示例是在上下文方法的解析阶段期间遇到的非上下文。
  * p>
  *  当需要上下文的特定子类型(如DirContext)时,也会抛出此异常,并且解析的对象是上下文,但不是必需的子类型。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @see Context#destroySubcontext
  *
  * @author Rosanna Lee
  * @author Scott Seligman
  * @since 1.3
  */

public class NotContextException extends NamingException {
    /**
     * Constructs a new instance of NotContextException using an
     * explanation. All other fields default to null.
     *
     * <p>
     *  使用解释构造NotContextException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public NotContextException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of NotContextException.
      * All fields default to null.
      * <p>
      *  构造一个NotContextException的新实例。所有字段默认为null。
      * 
      */
    public NotContextException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = 849752551644540417L;
}
