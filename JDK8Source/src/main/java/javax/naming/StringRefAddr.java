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
 * This class represents the string form of the address of
 * a communications end-point.
 * It consists of a type that describes the communication mechanism
 * and a string contents specific to that communication mechanism.
 * The format and interpretation of
 * the address type and the contents of the address are based on
 * the agreement of three parties: the client that uses the address,
 * the object/server that can be reached using the address, and the
 * administrator or program that creates the address.
 *
 * <p> An example of a string reference address is a host name.
 * Another example of a string reference address is a URL.
 *
 * <p> A string reference address is immutable:
 * once created, it cannot be changed.  Multithreaded access to
 * a single StringRefAddr need not be synchronized.
 *
 * <p>
 *  此类表示通信端点的地址的字符串形式。它由描述通信机制和该通信机制特有的字符串内容的类型组成。
 * 地址类型的格式和解释以及地址的内容基于三方的协议：使用地址的客户端,使用地址可以到达的对象/服务器,以及创建地址。
 * 
 *  <p>字符串引用地址的示例是主机名。字符串引用地址的另一个示例是URL。
 * 
 *  <p>字符串引用地址是不可变的：一旦创建,就无法更改。对单个StringRefAddr的多线程访问不需要同步。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 *
 * @see RefAddr
 * @see BinaryRefAddr
 * @since 1.3
 */

public class StringRefAddr extends RefAddr {
    /**
     * Contains the contents of this address.
     * Can be null.
     * <p>
     *  包含此地址的内容。可以为null。
     * 
     * 
     * @serial
     */
    private String contents;
    /**
      * Constructs a new instance of StringRefAddr using its address type
      * and contents.
      *
      * <p>
      *  使用其地址类型和内容构造StringRefAddr的新实例。
      * 
      * 
      * @param addrType A non-null string describing the type of the address.
      * @param addr The possibly null contents of the address in the form of a string.
      */
    public StringRefAddr(String addrType, String addr) {
        super(addrType);
        contents = addr;
    }

    /**
      * Retrieves the contents of this address. The result is a string.
      *
      * <p>
      *  检索此地址的内容。结果是一个字符串。
      * 
      * 
      * @return The possibly null address contents.
      */
    public Object getContent() {
        return contents;
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -8913762495138505527L;
}
