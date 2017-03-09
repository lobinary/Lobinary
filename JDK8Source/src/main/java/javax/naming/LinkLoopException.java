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
  * This exception is thrown when
  * a loop was detected will attempting to resolve a link, or an implementation
  * specific limit on link counts has been reached.
  * <p>
  * Synchronization and serialization issues that apply to LinkException
  * apply directly here.
  *
  * <p>
  *  当检测到循环将尝试解析链接时,或者已达到链接计数的实现特定限制时,抛出此异常。
  * <p>
  *  适用于LinkException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see LinkRef
  * @since 1.3
  */

public class LinkLoopException extends LinkException {
    /**
      * Constructs a new instance of LinkLoopException with an explanation
      * All the other fields are initialized to null.
      * <p>
      *  构造一个带有解释的LinkLoopException的新实例所有其他字段都初始化为null。
      * 
      * 
      * @param  explanation     A possibly null string containing additional
      *                         detail about this exception.
      * @see java.lang.Throwable#getMessage
      */
    public LinkLoopException(String explanation) {
        super(explanation);
    }

    /**
      * Constructs a new instance of LinkLoopException.
      * All the non-link-related and link-related fields are initialized to null.
      * <p>
      *  构造一个新的LinkLoopException实例。所有非链接相关和链接相关字段都初始化为null。
      * 
      */
    public LinkLoopException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -3119189944325198009L;
}
