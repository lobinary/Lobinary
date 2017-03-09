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
  * This exception is thrown by methods to indicate that
  * a binding cannot be added because the name is already bound to
  * another object.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  方法抛出此异常以指示无法添加绑定,因为名称已绑定到另一个对象。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see Context#bind
  * @see Context#rebind
  * @see Context#createSubcontext
  * @see javax.naming.directory.DirContext#bind
  * @see javax.naming.directory.DirContext#rebind
  * @see javax.naming.directory.DirContext#createSubcontext
  * @since 1.3
  */

public class NameAlreadyBoundException extends NamingException {
    /**
     * Constructs a new instance of NameAlreadyBoundException using the
     * explanation supplied. All other fields default to null.
     *
     *
     * <p>
     *  使用提供的解释构造NameAlreadyBoundException的新实例。所有其他字段默认为null。
     * 
     * 
     * @param   explanation     Possibly null additional detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public NameAlreadyBoundException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of NameAlreadyBoundException.
      * All fields are set to null;
      * <p>
      *  构造一个新的NameAlreadyBoundException实例。所有字段都设置为null;
      * 
      */
    public NameAlreadyBoundException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -8491441000356780586L;
}
