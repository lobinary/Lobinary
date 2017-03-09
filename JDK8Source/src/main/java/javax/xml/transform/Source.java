/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform;

/**
 * An object that implements this interface contains the information
 * needed to act as source input (XML source or transformation instructions).
 * <p>
 *  实现此接口的对象包含充当源输入(XML源或转换指令)所需的信息。
 * 
 */
public interface Source {

    /**
     * Set the system identifier for this Source.
     *
     * <p>The system identifier is optional if the source does not
     * get its data from a URL, but it may still be useful to provide one.
     * The application can use a system identifier, for example, to resolve
     * relative URIs and to include in error messages and warnings.</p>
     *
     * <p>
     *  设置此源的系统标识符。
     * 
     *  <p>如果来源不从网址获取其数据,则系统标识符是可选的,但是提供它仍可能是有用的。应用程序可以使用系统标识符,例如,来解析相关的URI,并将其包括在错误消息和警告中。</p>
     * 
     * 
     * @param systemId The system identifier as a URL string.
     */
    public void setSystemId(String systemId);

    /**
     * Get the system identifier that was set with setSystemId.
     *
     * <p>
     * 
     * @return The system identifier that was set with setSystemId, or null
     * if setSystemId was not called.
     */
    public String getSystemId();
}
