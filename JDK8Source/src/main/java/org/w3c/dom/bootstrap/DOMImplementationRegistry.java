/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */


package org.w3c.dom.bootstrap;

import java.util.StringTokenizer;
import java.util.Vector;
import org.w3c.dom.DOMImplementationSource;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementation;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * A factory that enables applications to obtain instances of
 * <code>DOMImplementation</code>.
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre class='example'>
 *  // get an instance of the DOMImplementation registry
 *  DOMImplementationRegistry registry =
 *       DOMImplementationRegistry.newInstance();
 *  // get a DOM implementation the Level 3 XML module
 *  DOMImplementation domImpl =
 *       registry.getDOMImplementation("XML 3.0");
 * </pre>
 *
 * <p>
 * This provides an application with an implementation-independent starting
 * point. DOM implementations may modify this class to meet new security
 * standards or to provide *additional* fallbacks for the list of
 * DOMImplementationSources.
 * </p>
 *
 * <p>
 *  使应用程序能够获取<code> DOMImplementation </code>实例的工厂。
 * 
 * <p>
 *  例：
 * </p>
 * 
 * <pre class='example'>
 *  //获取DOMImplementation注册表的实例DOMImplementationRegistry registry = DOMImplementationRegistry.newInstanc
 * e(); //获取DOM实现的第3级XML模块DOMImplementation domImpl = registry.getDOMImplementation("XML 3.0");。
 * </pre>
 * 
 * <p>
 *  这为应用程序提供了与实现无关的启动点。 DOM实现可以修改此类以满足新的安全标准或为DOMImplementationSource列表提供*额外的回退。
 * </p>
 * 
 * 
 * @see DOMImplementation
 * @see DOMImplementationSource
 * @since DOM Level 3
 */
public final class DOMImplementationRegistry {
    /**
     * The system property to specify the
     * DOMImplementationSource class names.
     * <p>
     *  系统属性指定DOMImplementationSource类的名称。
     * 
     */
    public static final String PROPERTY =
        "org.w3c.dom.DOMImplementationSourceList";

    /**
     * Default columns per line.
     * <p>
     *  每行默认列。
     * 
     */
    private static final int DEFAULT_LINE_LENGTH = 80;

    /**
     * The list of DOMImplementationSources.
     * <p>
     *  DOMImplementationSources的列表。
     * 
     */
    private Vector sources;

    /**
     * Default class name.
     * <p>
     *  默认类名。
     * 
     */
    private static final String FALLBACK_CLASS =
            "com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl";
    private static final String DEFAULT_PACKAGE =
            "com.sun.org.apache.xerces.internal.dom";
    /**
     * Private constructor.
     * <p>
     *  私有构造函数。
     * 
     * 
     * @param srcs Vector List of DOMImplementationSources
     */
    private DOMImplementationRegistry(final Vector srcs) {
        sources = srcs;
    }

    /**
     * Obtain a new instance of a <code>DOMImplementationRegistry</code>.
     *

     * The <code>DOMImplementationRegistry</code> is initialized by the
     * application or the implementation, depending on the context, by
     * first checking the value of the Java system property
     * <code>org.w3c.dom.DOMImplementationSourceList</code> and
     * the service provider whose contents are at
     * "<code>META_INF/services/org.w3c.dom.DOMImplementationSourceList</code>".
     * The value of this property is a white-space separated list of
     * names of availables classes implementing the
     * <code>DOMImplementationSource</code> interface. Each class listed
     * in the class name list is instantiated and any exceptions
     * encountered are thrown to the application.
     *
     * <p>
     *  获取<code> DOMImplementationRegistry </code>的新实例。
     * 
     * 根据上下文,通过首先检查Java系统属性<code> org.w3c.dom.DOMImplementationSourceList </code>和服务的值,应用程序或实现来初始化<code> DOM
     * ImplementationRegistry </code>提供者,其内容在"<code> META_INF / services / org.w3c.dom.DOMImplementationSour
     * ceList </code>"。
     * 此属性的值是一个用空格分隔的实现<code> DOMImplementationSource </code>接口的可用类的名称列表。
     * 类名列表中列出的每个类都会实例化,并且遇到的任何异常都会被抛出到应用程序中。
     * 
     * 
     * @return an initialized instance of DOMImplementationRegistry
     * @throws ClassNotFoundException
     *     If any specified class can not be found
     * @throws InstantiationException
     *     If any specified class is an interface or abstract class
     * @throws IllegalAccessException
     *     If the default constructor of a specified class is not accessible
     * @throws ClassCastException
     *     If any specified class does not implement
     * <code>DOMImplementationSource</code>
     */
    public static DOMImplementationRegistry newInstance()
        throws
        ClassNotFoundException,
        InstantiationException,
        IllegalAccessException,
        ClassCastException {
        Vector sources = new Vector();

        ClassLoader classLoader = getClassLoader();
        // fetch system property:
        String p = getSystemProperty(PROPERTY);

        //
        // if property is not specified then use contents of
        // META_INF/org.w3c.dom.DOMImplementationSourceList from classpath
        if (p == null) {
            p = getServiceValue(classLoader);
        }
        if (p == null) {
            //
            // DOM Implementations can modify here to add *additional* fallback
            // mechanisms to access a list of default DOMImplementationSources.
            //fall back to JAXP implementation class com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl
            p = FALLBACK_CLASS;
        }
        if (p != null) {
            StringTokenizer st = new StringTokenizer(p);
            while (st.hasMoreTokens()) {
                String sourceName = st.nextToken();
                // make sure we have access to restricted packages
                boolean internal = false;
                if (System.getSecurityManager() != null) {
                    if (sourceName != null && sourceName.startsWith(DEFAULT_PACKAGE)) {
                        internal = true;
                    }
                }
                Class sourceClass = null;
                if (classLoader != null && !internal) {
                    sourceClass = classLoader.loadClass(sourceName);
                } else {
                    sourceClass = Class.forName(sourceName);
                }
                DOMImplementationSource source =
                    (DOMImplementationSource) sourceClass.newInstance();
                sources.addElement(source);
            }
        }
        return new DOMImplementationRegistry(sources);
    }

    /**
     * Return the first implementation that has the desired
     * features, or <code>null</code> if none is found.
     *
     * <p>
     *  返回具有所需功能的第一个实现,如果没有找到,则返回<code> null </code>。
     * 
     * 
     * @param features
     *            A string that specifies which features are required. This is
     *            a space separated list in which each feature is specified by
     *            its name optionally followed by a space and a version number.
     *            This is something like: "XML 1.0 Traversal +Events 2.0"
     * @return An implementation that has the desired features,
     *         or <code>null</code> if none found.
     */
    public DOMImplementation getDOMImplementation(final String features) {
        int size = sources.size();
        String name = null;
        for (int i = 0; i < size; i++) {
            DOMImplementationSource source =
                (DOMImplementationSource) sources.elementAt(i);
            DOMImplementation impl = source.getDOMImplementation(features);
            if (impl != null) {
                return impl;
            }
        }
        return null;
    }

    /**
     * Return a list of implementations that support the
     * desired features.
     *
     * <p>
     *  返回支持所需功能的实现列表。
     * 
     * 
     * @param features
     *            A string that specifies which features are required. This is
     *            a space separated list in which each feature is specified by
     *            its name optionally followed by a space and a version number.
     *            This is something like: "XML 1.0 Traversal +Events 2.0"
     * @return A list of DOMImplementations that support the desired features.
     */
    public DOMImplementationList getDOMImplementationList(final String features) {
        final Vector implementations = new Vector();
        int size = sources.size();
        for (int i = 0; i < size; i++) {
            DOMImplementationSource source =
                (DOMImplementationSource) sources.elementAt(i);
            DOMImplementationList impls =
                source.getDOMImplementationList(features);
            for (int j = 0; j < impls.getLength(); j++) {
                DOMImplementation impl = impls.item(j);
                implementations.addElement(impl);
            }
        }
        return new DOMImplementationList() {
                public DOMImplementation item(final int index) {
                    if (index >= 0 && index < implementations.size()) {
                        try {
                            return (DOMImplementation)
                                implementations.elementAt(index);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            return null;
                        }
                    }
                    return null;
                }

                public int getLength() {
                    return implementations.size();
                }
            };
    }

    /**
     * Register an implementation.
     *
     * <p>
     *  注册实现。
     * 
     * 
     * @param s The source to be registered, may not be <code>null</code>
     */
    public void addSource(final DOMImplementationSource s) {
        if (s == null) {
            throw new NullPointerException();
        }
        if (!sources.contains(s)) {
            sources.addElement(s);
        }
    }

    /**
     *
     * Gets a class loader.
     *
     * <p>
     *  获取类加载器。
     * 
     * 
     * @return A class loader, possibly <code>null</code>
     */
    private static ClassLoader getClassLoader() {
        try {
            ClassLoader contextClassLoader = getContextClassLoader();

            if (contextClassLoader != null) {
                return contextClassLoader;
            }
        } catch (Exception e) {
            // Assume that the DOM application is in a JRE 1.1, use the
            // current ClassLoader
            return DOMImplementationRegistry.class.getClassLoader();
        }
        return DOMImplementationRegistry.class.getClassLoader();
    }

    /**
     * This method attempts to return the first line of the resource
     * META_INF/services/org.w3c.dom.DOMImplementationSourceList
     * from the provided ClassLoader.
     *
     * <p>
     *  此方法尝试从所提供的ClassLoader返回资源META_INF / services / org.w3c.dom.DOMImplementationSourceList的第一行。
     * 
     * 
     * @param classLoader classLoader, may not be <code>null</code>.
     * @return first line of resource, or <code>null</code>
     */
    private static String getServiceValue(final ClassLoader classLoader) {
        String serviceId = "META-INF/services/" + PROPERTY;
        // try to find services in CLASSPATH
        try {
            InputStream is = getResourceAsStream(classLoader, serviceId);

            if (is != null) {
                BufferedReader rd;
                try {
                    rd =
                        new BufferedReader(new InputStreamReader(is, "UTF-8"),
                                           DEFAULT_LINE_LENGTH);
                } catch (java.io.UnsupportedEncodingException e) {
                    rd =
                        new BufferedReader(new InputStreamReader(is),
                                           DEFAULT_LINE_LENGTH);
                }
                String serviceValue = rd.readLine();
                rd.close();
                if (serviceValue != null && serviceValue.length() > 0) {
                    return serviceValue;
                }
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    /**
     * A simple JRE (Java Runtime Environment) 1.1 test
     *
     * <p>
     *  一个简单的JRE(Java Runtime Environment)1.1测试
     * 
     * 
     * @return <code>true</code> if JRE 1.1
     */
    private static boolean isJRE11() {
        try {
            Class c = Class.forName("java.security.AccessController");
            // java.security.AccessController existed since 1.2 so, if no
            // exception was thrown, the DOM application is running in a JRE
            // 1.2 or higher
            return false;
        } catch (Exception ex) {
            // ignore
        }
        return true;
    }

    /**
     * This method returns the ContextClassLoader or <code>null</code> if
     * running in a JRE 1.1
     *
     * <p>
     *  如果在JRE 1.1中运行,此方法将返回ContextClassLoader或<code> null </code>
     * 
     * 
     * @return The Context Classloader
     */
    private static ClassLoader getContextClassLoader() {
        return isJRE11()
            ? null
            : (ClassLoader)
              AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        ClassLoader classLoader = null;
                        try {
                            classLoader =
                                Thread.currentThread().getContextClassLoader();
                        } catch (SecurityException ex) {
                        }
                        return classLoader;
                    }
                });
    }

    /**
     * This method returns the system property indicated by the specified name
     * after checking access control privileges. For a JRE 1.1, this check is
     * not done.
     *
     * <p>
     *  此方法在检查访问控制权限后返回由指定名称指示的系统属性。对于JRE 1.1,此检查未完成。
     * 
     * 
     * @param name the name of the system property
     * @return the system property
     */
    private static String getSystemProperty(final String name) {
        return isJRE11()
            ? (String) System.getProperty(name)
            : (String) AccessController.doPrivileged(new PrivilegedAction() {
                    public Object run() {
                        return System.getProperty(name);
                    }
                });
    }

    /**
     * This method returns an Inputstream for the reading resource
     * META_INF/services/org.w3c.dom.DOMImplementationSourceList after checking
     * access control privileges. For a JRE 1.1, this check is not done.
     *
     * <p>
     *  此方法在检查访问控制权限后为读取资源META_INF / services / org.w3c.dom.DOMImplementationSourceList返回一个Inputstream。
     * 对于JRE 1.1,此检查未完成。
     * 
     * @param classLoader classLoader
     * @param name the resource
     * @return an Inputstream for the resource specified
     */
    private static InputStream getResourceAsStream(final ClassLoader classLoader,
                                                   final String name) {
        if (isJRE11()) {
            InputStream ris;
            if (classLoader == null) {
                ris = ClassLoader.getSystemResourceAsStream(name);
            } else {
                ris = classLoader.getResourceAsStream(name);
            }
            return ris;
        } else {
            return (InputStream)
                AccessController.doPrivileged(new PrivilegedAction() {
                        public Object run() {
                            InputStream ris;
                            if (classLoader == null) {
                                ris =
                                    ClassLoader.getSystemResourceAsStream(name);
                            } else {
                                ris = classLoader.getResourceAsStream(name);
                            }
                            return ris;
                        }
                    });
        }
    }
}
