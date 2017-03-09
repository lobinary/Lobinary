/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
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

import static com.sun.jmx.defaults.JmxProperties.MBEANSERVER_LOGGER;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

/**
 * <p>Keeps the list of Class Loaders registered in the MBean Server.
 * It provides the necessary methods to load classes using the registered
 * Class Loaders.</p>
 *
 * <p>This deprecated class is maintained for compatibility.  In
 * previous versions of JMX, there was one
 * <code>DefaultLoaderRepository</code> shared by all MBean servers.
 * As of JMX 1.2, that functionality is approximated by using {@link
 * MBeanServerFactory#findMBeanServer} to find all known MBean
 * servers, and consulting the {@link ClassLoaderRepository} of each
 * one.  It is strongly recommended that code referencing
 * <code>DefaultLoaderRepository</code> be rewritten.</p>
 *
 * <p>
 *  <p>保留在MBean服务器中注册的类加载器的列表。它提供了使用注册的类装载器加载类的必要方法。</p>
 * 
 *  <p>维护此已弃用的类以实现兼容性。在以前的JMX版本中,所有MBean服务器共享一个<code> DefaultLoaderRepository </code>。
 * 从JMX 1.2开始,该功能通过使用{@link MBeanServerFactory#findMBeanServer}来查找所有已知的MBean服务器,并咨询每个服务器的{@link ClassLoaderRepository}
 * 。
 *  <p>维护此已弃用的类以实现兼容性。在以前的JMX版本中,所有MBean服务器共享一个<code> DefaultLoaderRepository </code>。
 * 强烈建议重写代码引用<code> DefaultLoaderRepository </code>。</p>。
 * 
 * 
 * @deprecated Use
 * {@link javax.management.MBeanServer#getClassLoaderRepository()}}
 * instead.
 *
 * @since 1.5
 */
@Deprecated
public class DefaultLoaderRepository {

    /**
     * Go through the list of class loaders and try to load the requested
     * class.
     * The method will stop as soon as the class is found. If the class
     * is not found the method will throw a <CODE>ClassNotFoundException</CODE>
     * exception.
     *
     * <p>
     * 
     * @param className The name of the class to be loaded.
     *
     * @return the loaded class.
     *
     * @exception ClassNotFoundException The specified class could not be
     *            found.
     */
    public static Class<?> loadClass(String className)
        throws ClassNotFoundException {
        MBEANSERVER_LOGGER.logp(Level.FINEST,
                DefaultLoaderRepository.class.getName(),
                "loadClass", className);
        return load(null, className);
    }

    /**
     * Go through the list of class loaders but exclude the given
     * class loader, then try to load
     * the requested class.
     * The method will stop as soon as the class is found. If the class
     * is not found the method will throw a <CODE>ClassNotFoundException</CODE>
     * exception.
     *
     * <p>
     *  浏览类加载器列表,并尝试加载请求的类。该方法将在找到类后立即停止。如果没有找到该类,该方法将抛出一个<CODE> ClassNotFoundException </CODE>异常。
     * 
     * 
     * @param className The name of the class to be loaded.
     * @param loader The class loader to be excluded.
     *
     * @return the loaded class.
     *
     * @exception ClassNotFoundException The specified class could not be
     *    found.
     */
    public static Class<?> loadClassWithout(ClassLoader loader,
                                         String className)
        throws ClassNotFoundException {
        MBEANSERVER_LOGGER.logp(Level.FINEST,
                DefaultLoaderRepository.class.getName(),
                "loadClassWithout", className);
        return load(loader, className);
    }

    private static Class<?> load(ClassLoader without, String className)
            throws ClassNotFoundException {
        final List<MBeanServer> mbsList = MBeanServerFactory.findMBeanServer(null);

        for (MBeanServer mbs : mbsList) {
            ClassLoaderRepository clr = mbs.getClassLoaderRepository();
            try {
                return clr.loadClassWithout(without, className);
            } catch (ClassNotFoundException e) {
                // OK : Try with next one...
            }
        }
        throw new ClassNotFoundException(className);
    }

 }
