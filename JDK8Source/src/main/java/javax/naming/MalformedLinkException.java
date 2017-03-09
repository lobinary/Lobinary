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
  * This exception is thrown when a malformed link was encountered while
  * resolving or constructing a link.
  * <p>
  * Synchronization and serialization issues that apply to LinkException
  * apply directly here.
  *
  * <p>
  *  在解析或构造链接时遇到格式错误的链接时抛出此异常。
  * <p>
  *  适用于LinkException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see LinkRef#getLinkName
  * @see LinkRef
  * @since 1.3
  */

public class MalformedLinkException extends LinkException {
    /**
      * Constructs a new instance of MalformedLinkException with an explanation
      * All the other fields are initialized to null.
      * <p>
      *  构造带有解释的MalformedLinkException的新实例所有其他字段都初始化为null。
      * 
      * 
      * @param  explanation     A possibly null string containing additional
      *                         detail about this exception.
      */
    public MalformedLinkException(String explanation) {
        super(explanation);
    }


    /**
      * Constructs a new instance of Malformed LinkException.
      * All fields are initialized to null.
      * <p>
      *  构造一个新的Malformed LinkException实例。所有字段都初始化为null。
      * 
      */
    public MalformedLinkException() {
        super();
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -3066740437737830242L;
}
