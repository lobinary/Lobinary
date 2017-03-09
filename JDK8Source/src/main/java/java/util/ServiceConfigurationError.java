/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.util;


/**
 * Error thrown when something goes wrong while loading a service provider.
 *
 * <p> This error will be thrown in the following situations:
 *
 * <ul>
 *
 *   <li> The format of a provider-configuration file violates the <a
 *   href="ServiceLoader.html#format">specification</a>; </li>
 *
 *   <li> An {@link java.io.IOException IOException} occurs while reading a
 *   provider-configuration file; </li>
 *
 *   <li> A concrete provider class named in a provider-configuration file
 *   cannot be found; </li>
 *
 *   <li> A concrete provider class is not a subclass of the service class;
 *   </li>
 *
 *   <li> A concrete provider class cannot be instantiated; or
 *
 *   <li> Some other kind of error occurs. </li>
 *
 * </ul>
 *
 *
 * <p>
 *  加载服务提供商时出现问题时抛出错误。
 * 
 *  <p>在以下情况下会抛出此错误：
 * 
 * <ul>
 * 
 *  <li>提供程序配置文件的格式违反<a href="ServiceLoader.html#format">规范</a>; </li>
 * 
 *  <li>在读取提供程序配置文件时发生{@link java.io.IOException IOException}; </li>
 * 
 *  <li>找不到在提供程序配置文件中命名的具体提供程序类; </li>
 * 
 *  <li>具体的提供程序类不是服务类的子类;
 * </li>
 * 
 * @author Mark Reinhold
 * @since 1.6
 */

public class ServiceConfigurationError
    extends Error
{

    private static final long serialVersionUID = 74132770414881L;

    /**
     * Constructs a new instance with the specified message.
     *
     * <p>
     * 
     *  <li>无法实例化具体的提供程序类;要么
     * 
     *  <li>发生其他一些错误。 </li>
     * 
     * </ul>
     * 
     * 
     * @param  msg  The message, or <tt>null</tt> if there is no message
     *
     */
    public ServiceConfigurationError(String msg) {
        super(msg);
    }

    /**
     * Constructs a new instance with the specified message and cause.
     *
     * <p>
     * 
     * @param  msg  The message, or <tt>null</tt> if there is no message
     *
     * @param  cause  The cause, or <tt>null</tt> if the cause is nonexistent
     *                or unknown
     */
    public ServiceConfigurationError(String msg, Throwable cause) {
        super(msg, cause);
    }

}
