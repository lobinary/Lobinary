/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.utils;

/**
 * This class is duplicated for each JAXP subpackage so keep it in sync.
 * It is package private and therefore is not exposed as part of the JAXP
 * API.
 * <p>
 * This code is designed to implement the JAXP 1.1 spec pluggability
 * feature and is designed to run on JDK version 1.1 and
 * later, and to compile on JDK 1.2 and onward.
 * The code also runs both as part of an unbundled jar file and
 * when bundled as part of the JDK.
 * <p>
 *
 * <p>
 *  这个类被复制到每个JAXP子包,所以保持同步。它是包私有的,因此不作为JAXP API的一部分公开。
 * <p>
 *  此代码旨在实现JAXP 1.1规范的可插入性功能,并设计为在JDK 1.1和更高版本上运行,并在JDK 1.2及更高版本上进行编译。
 * 代码还作为未绑定的jar文件的一部分运行,并作为JDK的一部分捆绑。
 * <p>
 * 
 * 
 * @version $Id: ObjectFactory.java,v 1.6 2010/04/23 01:44:34 joehw Exp $
 */
public final class ObjectFactory {

    //
    // Constants
    //
    private static final String JAXP_INTERNAL = "com.sun.org.apache";
    private static final String STAX_INTERNAL = "com.sun.xml.internal";

    /** Set to true for debugging */
    private static final boolean DEBUG = isDebugEnabled();


    //
    // Private static methods
    //

    /** Returns true if debug has been enabled. */
    private static boolean isDebugEnabled() {
        try {
            String val = SecuritySupport.getSystemProperty("xerces.debug");
            // Allow simply setting the prop to turn on debug
            return (val != null && (!"false".equals(val)));
        }
        catch (SecurityException se) {}
        return false;
    } // isDebugEnabled()

    /** Prints a message to standard error if debugging is enabled. */
    private static void debugPrintln(String msg) {
        if (DEBUG) {
            System.err.println("XERCES: " + msg);
        }
    } // debugPrintln(String)

    /**
     * Figure out which ClassLoader to use.  For JDK 1.2 and later use
     * the context ClassLoader.
     * <p>
     *  确定使用哪个ClassLoader。对于JDK 1.2和更高版本,使用上下文ClassLoader。
     * 
     */
    public static ClassLoader findClassLoader()
        throws ConfigurationError
    {
        if (System.getSecurityManager()!=null) {
            //this will ensure bootclassloader is used
            return null;
        }
        // Figure out which ClassLoader to use for loading the provider
        // class.  If there is a Context ClassLoader then use it.
        ClassLoader context = SecuritySupport.getContextClassLoader();
        ClassLoader system = SecuritySupport.getSystemClassLoader();

        ClassLoader chain = system;
        while (true) {
            if (context == chain) {
                // Assert: we are on JDK 1.1 or we have no Context ClassLoader
                // or any Context ClassLoader in chain of system classloader
                // (including extension ClassLoader) so extend to widest
                // ClassLoader (always look in system ClassLoader if Xerces
                // is in boot/extension/system classpath and in current
                // ClassLoader otherwise); normal classloaders delegate
                // back to system ClassLoader first so this widening doesn't
                // change the fact that context ClassLoader will be consulted
                ClassLoader current = ObjectFactory.class.getClassLoader();

                chain = system;
                while (true) {
                    if (current == chain) {
                        // Assert: Current ClassLoader in chain of
                        // boot/extension/system ClassLoaders
                        return system;
                    }
                    if (chain == null) {
                        break;
                    }
                    chain = SecuritySupport.getParentClassLoader(chain);
                }

                // Assert: Current ClassLoader not in chain of
                // boot/extension/system ClassLoaders
                return current;
            }

            if (chain == null) {
                // boot ClassLoader reached
                break;
            }

            // Check for any extension ClassLoaders in chain up to
            // boot ClassLoader
            chain = SecuritySupport.getParentClassLoader(chain);
        }

        // Assert: Context ClassLoader not in chain of
        // boot/extension/system ClassLoaders
        return context;
    } // findClassLoader():ClassLoader

    /**
     * Create an instance of a class using the same classloader for the ObjectFactory by default
     * or bootclassloader when Security Manager is in place
     * <p>
     *  创建一个类的实例,在安全管理器到位时,默认使用与ObjectFactory相同的类加载器或bootclassloader
     * 
     */
    public static Object newInstance(String className, boolean doFallback)
        throws ConfigurationError
    {
        if (System.getSecurityManager()!=null) {
            return newInstance(className, null, doFallback);
        } else {
            return newInstance(className,
                findClassLoader (), doFallback);
        }
    }

    /**
     * Create an instance of a class using the specified ClassLoader
     * <p>
     *  使用指定的ClassLoader创建类的实例
     * 
     */
    public static Object newInstance(String className, ClassLoader cl,
                                      boolean doFallback)
        throws ConfigurationError
    {
        // assert(className != null);
        try{
            Class providerClass = findProviderClass(className, cl, doFallback);
            Object instance = providerClass.newInstance();
            if (DEBUG) debugPrintln("created new instance of " + providerClass +
                   " using ClassLoader: " + cl);
            return instance;
        } catch (ClassNotFoundException x) {
            throw new ConfigurationError(
                "Provider " + className + " not found", x);
        } catch (Exception x) {
            throw new ConfigurationError(
                "Provider " + className + " could not be instantiated: " + x,
                x);
        }
    }

    /**
     * Find a Class using the same classloader for the ObjectFactory by default
     * or bootclassloader when Security Manager is in place
     * <p>
     * 在安全管理器就位时,使用默认情况下的ObjectFactory或bootclassloader使用相同的类加载器来查找类
     * 
     */
    public static Class findProviderClass(String className, boolean doFallback)
        throws ClassNotFoundException, ConfigurationError
    {
        return findProviderClass (className,
                findClassLoader (), doFallback);
    }
    /**
     * Find a Class using the specified ClassLoader
     * <p>
     *  使用指定的ClassLoader查找类
     */
    public static Class findProviderClass(String className, ClassLoader cl,
                                      boolean doFallback)
        throws ClassNotFoundException, ConfigurationError
    {
        //throw security exception if the calling thread is not allowed to access the package
        //restrict the access to package as speicified in java.security policy
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            if (className.startsWith(JAXP_INTERNAL) ||
                    className.startsWith(STAX_INTERNAL)) {
                cl = null;
            } else {
                final int lastDot = className.lastIndexOf(".");
                String packageName = className;
                if (lastDot != -1) packageName = className.substring(0, lastDot);
                security.checkPackageAccess(packageName);
            }
        }
        Class providerClass;
        if (cl == null) {
            //use the bootstrap ClassLoader.
            providerClass = Class.forName(className, false, ObjectFactory.class.getClassLoader());
        } else {
            try {
                providerClass = cl.loadClass(className);
            } catch (ClassNotFoundException x) {
                if (doFallback) {
                    // Fall back to current classloader
                    ClassLoader current = ObjectFactory.class.getClassLoader();
                    if (current == null) {
                        providerClass = Class.forName(className);
                    } else if (cl != current) {
                        cl = current;
                        providerClass = cl.loadClass(className);
                    } else {
                        throw x;
                    }
                } else {
                    throw x;
                }
            }
        }

        return providerClass;
    }

} // class ObjectFactory
