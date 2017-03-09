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

package com.sun.jmx.mbeanserver;


// JMX import
import javax.management.ObjectName;
import javax.management.loading.ClassLoaderRepository;

/**
 * This interface keeps the list of Class Loaders registered in the
 * MBean Server.
 * It provides the necessary methods to load classes using the
 * registered Class Loaders, and to add/remove class loaders from the
 * list.
 *
 * <p>
 *  此接口保留在MBean服务器中注册的类加载器的列表。它提供了必要的方法来使用注册的类加载器加载类,并从列表中添加/删除类加载器。
 * 
 * 
 * @since 1.5
 */
public interface ModifiableClassLoaderRepository
    extends ClassLoaderRepository {

    /**
     * Add an anonymous ClassLoader to the repository.
     * <p>
     *  向存储库添加匿名ClassLoader。
     * 
     * 
     **/
    public void addClassLoader(ClassLoader loader);

    /**
     * Remove the specified ClassLoader to the repository.
     * The class loader may or may not be anonymous.
     * <p>
     *  将指定的ClassLoader删除到存储库。类加载器可以是匿名的,也可以不是匿名的。
     * 
     * 
     **/
    public void removeClassLoader(ClassLoader loader);

    /**
     * Add a named ClassLoader to the repository.
     * <p>
     *  将一个命名的ClassLoader添加到存储库。
     * 
     * 
     **/
    public void addClassLoader(ObjectName name, ClassLoader loader);

    /**
     * Remove a named ClassLoader from the repository.
     * <p>
     *  从存储库中删除一个命名的ClassLoader。
     * 
     * 
     **/
    public void removeClassLoader(ObjectName name);

    /**
     * Get a named ClassLoader from the repository.
     * <p>
     *  从存储库获取一个命名的ClassLoader。
     * 
     **/
    public ClassLoader getClassLoader(ObjectName name);
}
