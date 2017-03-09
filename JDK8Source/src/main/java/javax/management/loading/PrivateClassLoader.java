/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.loading;

/**
 * Marker interface indicating that a ClassLoader should not be added
 * to the {@link ClassLoaderRepository}.  When a ClassLoader is
 * registered as an MBean in the MBean server, it is added to the
 * MBean server's ClassLoaderRepository unless it implements this
 * interface.
 *
 * <p>
 *  标记界面指示不应将ClassLoader添加到{@link ClassLoaderRepository}。
 * 当ClassLoader在MBean服务器中注册为MBean时,它将被添加到MBean服务器的ClassLoaderRepository,除非它实现此接口。
 * 
 * @since 1.5
 */
public interface PrivateClassLoader {}
