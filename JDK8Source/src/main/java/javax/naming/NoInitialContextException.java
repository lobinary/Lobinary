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
  * This exception is thrown when no initial context implementation
  * can be created.  The policy of how an initial context implementation
  * is selected is described in the documentation of the InitialContext class.
  *<p>
  * This exception can be thrown during any interaction with the
  * InitialContext, not only when the InitialContext is constructed.
  * For example, the implementation of the initial context might lazily
  * retrieve the context only when actual methods are invoked on it.
  * The application should not have any dependency on when the existence
  * of an initial context is determined.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当不能创建初始上下文实现时抛出此异常。在InitialContext类的文档中描述了如何选择初始上下文实现的策略。
  * p>
  *  这个异常可以在与InitialContext的任何交互期间抛出,不仅在构造InitialContext时。例如,初始上下文的实现可能只在实际方法被调用时才会检索上下文。
  * 应用程序不应该对何时确定初始上下文的存在具有任何依赖性。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see InitialContext
  * @see javax.naming.directory.InitialDirContext
  * @see javax.naming.spi.NamingManager#getInitialContext
  * @see javax.naming.spi.NamingManager#setInitialContextFactoryBuilder
  * @since 1.3
  */
public class NoInitialContextException extends NamingException {
    /**
      * Constructs an instance of NoInitialContextException.
      * All fields are initialized to null.
      * <p>
      *  构造NoInitialContextException的实例。所有字段都初始化为null。
      * 
      */
    public NoInitialContextException() {
        super();
    }

    /**
      * Constructs an instance of NoInitialContextException with an
      * explanation. All other fields are initialized to null.
      * <p>
      *  构造一个具有解释的NoInitialContextException的实例。所有其他字段均初始化为null。
      * 
      * 
      * @param  explanation     Possibly null additional detail about this exception.
      * @see java.lang.Throwable#getMessage
      */
    public NoInitialContextException(String explanation) {
        super(explanation);
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -3413733186901258623L;
}
