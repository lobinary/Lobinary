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

import java.net.URL;
import java.net.URLStreamHandlerFactory;

/**
 * An MLet that is not added to the {@link ClassLoaderRepository}.
 * This class acts exactly like its parent class, {@link MLet}, with
 * one exception.  When a PrivateMLet is registered in an MBean
 * server, it is not added to that MBean server's {@link
 * ClassLoaderRepository}.  This is true because this class implements
 * the interface {@link PrivateClassLoader}.
 *
 * <p>
 *  未添加到{@link ClassLoaderRepository}的MLet。这个类的行为完全像它的父类{@link MLet},有一个例外。
 * 当在MBean服务器中注册PrivateMLet时,它不会添加到该MBean服务器的{@link ClassLoaderRepository}。
 * 这是真的,因为这个类实现了接口{@link PrivateClassLoader}。
 * 
 * 
 * @since 1.5
 */
public class PrivateMLet extends MLet implements PrivateClassLoader {
    private static final long serialVersionUID = 2503458973393711979L;

    /**
      * Constructs a new PrivateMLet for the specified URLs using the
      * default delegation parent ClassLoader.  The URLs will be
      * searched in the order specified for classes and resources
      * after first searching in the parent class loader.
      *
      * <p>
      *  使用默认委托父类ClassLoader为指定的URL构造新的PrivateMLet。在首次在父类加载器中搜索之后,将按照为类和资源指定的顺序搜索URL。
      * 
      * 
      * @param  urls  The URLs from which to load classes and resources.
      * @param  delegateToCLR  True if, when a class is not found in
      * either the parent ClassLoader or the URLs, the MLet should delegate
      * to its containing MBeanServer's {@link ClassLoaderRepository}.
      *
      */
    public PrivateMLet(URL[] urls, boolean delegateToCLR) {
        super(urls, delegateToCLR);
    }

    /**
      * Constructs a new PrivateMLet for the given URLs. The URLs will
      * be searched in the order specified for classes and resources
      * after first searching in the specified parent class loader.
      * The parent argument will be used as the parent class loader
      * for delegation.
      *
      * <p>
      *  为给定的URL构造一个新的PrivateMLet。在首次在指定的父类装入器中搜索之后,将按照为类和资源指定的顺序搜索URL。父参数将用作委派的父类装入器。
      * 
      * 
      * @param  urls  The URLs from which to load classes and resources.
      * @param  parent The parent class loader for delegation.
      * @param  delegateToCLR  True if, when a class is not found in
      * either the parent ClassLoader or the URLs, the MLet should delegate
      * to its containing MBeanServer's {@link ClassLoaderRepository}.
      *
      */
    public PrivateMLet(URL[] urls, ClassLoader parent, boolean delegateToCLR) {
        super(urls, parent, delegateToCLR);
    }

    /**
      * Constructs a new PrivateMLet for the specified URLs, parent
      * class loader, and URLStreamHandlerFactory. The parent argument
      * will be used as the parent class loader for delegation. The
      * factory argument will be used as the stream handler factory to
      * obtain protocol handlers when creating new URLs.
      *
      * <p>
      *  为指定的URL,父类加载器和URLStreamHandlerFactory构造新的PrivateMLet。父参数将用作委派的父类装入器。
      * 工厂参数将用作流处理程序工厂来在创建新URL时获取协议处理程序。
      * 
      * @param  urls  The URLs from which to load classes and resources.
      * @param  parent The parent class loader for delegation.
      * @param  factory  The URLStreamHandlerFactory to use when creating URLs.
      * @param  delegateToCLR  True if, when a class is not found in
      * either the parent ClassLoader or the URLs, the MLet should delegate
      * to its containing MBeanServer's {@link ClassLoaderRepository}.
      *
      */
    public PrivateMLet(URL[] urls,
                       ClassLoader parent,
                       URLStreamHandlerFactory factory,
                       boolean delegateToCLR) {
        super(urls, parent, factory, delegateToCLR);
    }
}
