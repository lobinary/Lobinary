/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import java.net.URI;

/**
 * Interface URIException is a mixin interface which a subclass of {@link
 * PrintException PrintException} can implement to report an error condition
 * involving a URI address. The Print Service API does not define any print
 * exception classes that implement interface URIException, that being left to
 * the Print Service implementor's discretion.
 *
 * <p>
 *  接口URIException是一个mixin接口,{@link PrintException PrintException}的子类可以实现来报告涉及URI地址的错误条件。
 * 打印服务API不定义任何实现接口URIException的打印异常类,由打印服务实现者自行决定。
 * 
 */

public interface URIException {

    /**
     * Indicates that the printer cannot access the URI address.
     * For example, the printer might report this error if it goes to get
     * the print data and cannot even establish a connection to the
     * URI address.
     * <p>
     *  表示打印机无法访问URI地址。例如,如果打印机要获取打印数据,甚至无法建立与URI地址的连接,则打印机可能会报告此错误。
     * 
     */
    public static final int URIInaccessible = 1;

    /**
     * Indicates that the printer does not support the URI
     * scheme ("http", "ftp", etc.) in the URI address.
     * <p>
     *  表示打印机不支持URI地址中的URI方案("http","ftp"等)。
     * 
     */
    public static final int URISchemeNotSupported = 2;

    /**
     * Indicates any kind of problem not specifically identified
     * by the other reasons.
     * <p>
     *  表示没有被其他原因明确指出的任何种类的问题。
     * 
     */
    public static final int URIOtherProblem = -1;

    /**
     * Return the URI.
     * <p>
     *  返回URI。
     * 
     * 
     * @return the URI that is the cause of this exception.
     */
    public URI getUnsupportedURI();

    /**
     * Return the reason for the event.
     * <p>
     *  返回事件的原因。
     * 
     * @return one of the predefined reasons enumerated in this interface.
     */
    public int getReason();

}
