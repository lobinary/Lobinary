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

import javax.management.MBeanServer; // for Javadoc

/**
 * <p>Instances of this interface are used to keep the list of ClassLoaders
 * registered in an MBean Server.
 * They provide the necessary methods to load classes using the registered
 * ClassLoaders.</p>
 *
 * <p>The first ClassLoader in a <code>ClassLoaderRepository</code> is
 * always the MBean Server's own ClassLoader.</p>
 *
 * <p>When an MBean is registered in an MBean Server, if it is of a
 * subclass of {@link java.lang.ClassLoader} and if it does not
 * implement the interface {@link PrivateClassLoader}, it is added to
 * the end of the MBean Server's <code>ClassLoaderRepository</code>.
 * If it is subsequently unregistered from the MBean Server, it is
 * removed from the <code>ClassLoaderRepository</code>.</p>
 *
 * <p>The order of MBeans in the <code>ClassLoaderRepository</code> is
 * significant.  For any two MBeans <em>X</em> and <em>Y</em> in the
 * <code>ClassLoaderRepository</code>, <em>X</em> must appear before
 * <em>Y</em> if the registration of <em>X</em> was completed before
 * the registration of <em>Y</em> started.  If <em>X</em> and
 * <em>Y</em> were registered concurrently, their order is
 * indeterminate.  The registration of an MBean corresponds to the
 * call to {@link MBeanServer#registerMBean} or one of the {@link
 * MBeanServer}<code>.createMBean</code> methods.</p>
 *
 * <p>
 *  <p>此接口的实例用于保留在MBean服务器中注册的ClassLoaders列表。它们提供了使用注册的ClassLoaders加载类的必要方法。</p>
 * 
 *  <p> <code> ClassLoaderRepository </code>中的第一个ClassLoader始终是MBean Server自己的ClassLoader。</p>
 * 
 *  <p>当MBean在MBean Server中注册时,如果它是{@link java.lang.ClassLoader}的子类,如果它不实现接口{@link PrivateClassLoader},它
 * 将被添加到MBean Server的<code> ClassLoaderRepository </code>。
 * 如果随后从MBean Server注销,它将从<code> ClassLoaderRepository </code>中删除。</p>。
 * 
 *  <p> MBeans在<code> ClassLoaderRepository </code>中的顺序很重要。
 * 对于<code> ClassLoaderRepository </code>中的任何两个MBean <em> X </em>和<em> Y </em>,<em> >如果<em> X </em>的注册在注
 * 册<y> </em>开始之前完成,如果同时注册了<em> X </em>和<em> Y </em>,则其顺序不确定。
 *  <p> MBeans在<code> ClassLoaderRepository </code>中的顺序很重要。
 *  MBean的注册对应于对{@link MBeanServer#registerMBean}或{@link MBeanServer} <code> .createMBean </code>方法之一的调用
 * 。
 *  <p> MBeans在<code> ClassLoaderRepository </code>中的顺序很重要。</p>。
 * 
 * 
 * @see javax.management.MBeanServerFactory
 *
 * @since 1.5
 */
public interface ClassLoaderRepository {

    /**
     * <p>Load the given class name through the list of class loaders.
     * Each ClassLoader in turn from the ClassLoaderRepository is
     * asked to load the class via its {@link
     * ClassLoader#loadClass(String)} method.  If it successfully
     * returns a {@link Class} object, that is the result of this
     * method.  If it throws a {@link ClassNotFoundException}, the
     * search continues with the next ClassLoader.  If it throws
     * another exception, the exception is propagated from this
     * method.  If the end of the list is reached, a {@link
     * ClassNotFoundException} is thrown.</p>
     *
     * <p>
     * <p>通过类加载器列表加载给定的类名。
     * 每个ClassLoader从ClassLoaderRepository被要求通过其{@link ClassLoader#loadClass(String)}方法加载类。
     * 如果它成功返回一个{@link Class}对象,那就是这个方法的结果。如果它抛出一个{@link ClassNotFoundException},搜索将继续下一个ClassLoader。
     * 如果它抛出另一个异常,异常从这个方法传播。如果到达列表的末尾,将抛出一个{@link ClassNotFoundException}。</p>。
     * 
     * 
     * @param className The name of the class to be loaded.
     *
     * @return the loaded class.
     *
     * @exception ClassNotFoundException The specified class could not be
     *            found.
     */
    public Class<?> loadClass(String className)
            throws ClassNotFoundException;

    /**
     * <p>Load the given class name through the list of class loaders,
     * excluding the given one.  Each ClassLoader in turn from the
     * ClassLoaderRepository, except <code>exclude</code>, is asked to
     * load the class via its {@link ClassLoader#loadClass(String)}
     * method.  If it successfully returns a {@link Class} object,
     * that is the result of this method.  If it throws a {@link
     * ClassNotFoundException}, the search continues with the next
     * ClassLoader.  If it throws another exception, the exception is
     * propagated from this method.  If the end of the list is
     * reached, a {@link ClassNotFoundException} is thrown.</p>
     *
     * <p>Be aware that if a ClassLoader in the ClassLoaderRepository
     * calls this method from its {@link ClassLoader#loadClass(String)
     * loadClass} method, it exposes itself to a deadlock if another
     * ClassLoader in the ClassLoaderRepository does the same thing at
     * the same time.  The {@link #loadClassBefore} method is
     * recommended to avoid the risk of deadlock.</p>
     *
     * <p>
     *  <p>通过类加载器列表加载给定的类名,不包括给定的类名。
     * 每个ClassLoader从ClassLoaderRepository,除了<code> exclude </code>,被要求通过其{@link ClassLoader#loadClass(String)}
     * 方法加载类。
     *  <p>通过类加载器列表加载给定的类名,不包括给定的类名。如果它成功返回一个{@link Class}对象,那就是这个方法的结果。
     * 如果它抛出一个{@link ClassNotFoundException},搜索将继续下一个ClassLoader。如果它抛出另一个异常,异常从这个方法传播。
     * 如果到达列表的末尾,将抛出一个{@link ClassNotFoundException}。</p>。
     * 
     * <p>请注意,如果ClassLoaderRepository中的ClassLoader从其{@link ClassLoader#loadClass(String)loadClass}方法中调用此方法,则
     * 如果ClassLoaderRepository中的另一个ClassLoader在同一时间执行相同的操作,则会暴露自己的死锁。
     * 建议使用{@link #loadClassBefore}方法,以避免死锁的风险。</p>。
     * 
     * 
     * @param className The name of the class to be loaded.
     * @param exclude The class loader to be excluded.  May be null,
     * in which case this method is equivalent to {@link #loadClass
     * loadClass(className)}.
     *
     * @return the loaded class.
     *
     * @exception ClassNotFoundException The specified class could not
     * be found.
     */
    public Class<?> loadClassWithout(ClassLoader exclude,
                                     String className)
            throws ClassNotFoundException;

    /**
     * <p>Load the given class name through the list of class loaders,
     * stopping at the given one.  Each ClassLoader in turn from the
     * ClassLoaderRepository is asked to load the class via its {@link
     * ClassLoader#loadClass(String)} method.  If it successfully
     * returns a {@link Class} object, that is the result of this
     * method.  If it throws a {@link ClassNotFoundException}, the
     * search continues with the next ClassLoader.  If it throws
     * another exception, the exception is propagated from this
     * method.  If the search reaches <code>stop</code> or the end of
     * the list, a {@link ClassNotFoundException} is thrown.</p>
     *
     * <p>Typically this method is called from the {@link
     * ClassLoader#loadClass(String) loadClass} method of
     * <code>stop</code>, to consult loaders that appear before it
     * in the <code>ClassLoaderRepository</code>.  By stopping the
     * search as soon as <code>stop</code> is reached, a potential
     * deadlock with concurrent class loading is avoided.</p>
     *
     * <p>
     * 
     * @param className The name of the class to be loaded.
     * @param stop The class loader at which to stop.  May be null, in
     * which case this method is equivalent to {@link #loadClass(String)
     * loadClass(className)}.
     *
     * @return the loaded class.
     *
     * @exception ClassNotFoundException The specified class could not
     * be found.
     *
     */
    public Class<?> loadClassBefore(ClassLoader stop,
                                    String className)
            throws ClassNotFoundException;

}
