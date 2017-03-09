/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2006, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

/**
 * A request or reply from the network is structurally invalid.
 * This error typically indicates a bug in either the client-side
 * or server-side run time. For example, if a reply from the server
 * indicates that the message contains 1000 bytes, but the actual
 * message is shorter or longer than 1000 bytes, the ORB raises
 * this exception. <tt>MARSHAL</tt> can also be caused by using
 * the DII or DSI incorrectly, for example, if the type of the
 * actual parameters sent does not agree with IDL signature of an
 * operation.<P>
 * It contains a minor code, which gives more detailed information about
 * what caused the exception, and a completion status. It may also contain
 * a string describing the exception.
 * <P>
 * See the section <A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">Minor
 * Code Meanings</A> to see the minor codes for this exception.
 *
 * <p>
 *  来自网络的请求或回复在结构上无效。此错误通常表示客户端或服务器端运行时的错误。例如,如果来自服务器的回复指示消息包含1000个字节,但实际消息短于或长于1000个字节,则ORB引发此异常。
 *  <tt> MARSHAL </tt>也可能由于使用DII或DSI不正确引起,例如,如果发送的实际参数的类型与操作的IDL签名不一致。
 * <P>它包含一个次要代码,其中提供了有关导致异常的详细信息以及完成状态。它还可以包含描述异常的字符串。
 * <P>
 *  请参阅<A href="../../../../technotes/guides/idl/jidlExceptions.html#minorcodemeanings">小调代码</A>一节,查看此例外
 * 的次要代码。
 * 
 * 
 * @since       JDK1.2
 */

public final class MARSHAL extends SystemException {
    /**
     * Constructs a <code>MARSHAL</code> exception with a default minor code
     * of 0, a completion state of CompletionStatus.COMPLETED_NO,
     * and a null description.
     * <p>
     *  构造一个<code> MARSHAL </code>异常,其中缺省次要代码为0,完成状态为CompletionStatus.COMPLETED_NO,以及空描述。
     * 
     */
    public MARSHAL() {
        this("");
    }

    /**
     * Constructs a <code>MARSHAL</code> exception with the specified description message,
     * a minor code of 0, and a completion state of COMPLETED_NO.
     * <p>
     *  构造具有指定描述消息的<code> MARSHAL </code>异常,次要代码为0,完成状态为COMPLETED_NO。
     * 
     * 
     * @param s the String containing a description of the exception
     */
    public MARSHAL(String s) {
        this(s, 0, CompletionStatus.COMPLETED_NO);
    }

    /**
     * Constructs a <code>MARSHAL</code> exception with the specified
     * minor code and completion status.
     * <p>
     *  构造具有指定次要代码和完成状态的<code> MARSHAL </code>异常。
     * 
     * 
     * @param minor the minor code
     * @param completed the completion status
     */
    public MARSHAL(int minor, CompletionStatus completed) {
        this("", minor, completed);
    }

    /**
     * Constructs a <code>MARSHAL</code> exception with the specified description
     * message, minor code, and completion status.
     * <p>
     * 构造具有指定的描述消息,次要代码和完成状态的<code> MARSHAL </code>异常。
     * 
     * @param s the String containing a description message
     * @param minor the minor code
     * @param completed the completion status
     */
    public MARSHAL(String s, int minor, CompletionStatus completed) {
        super(s, minor, completed);
    }
}
