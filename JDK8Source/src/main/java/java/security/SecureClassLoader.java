/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.util.HashMap;
import java.util.ArrayList;
import java.net.URL;

import sun.security.util.Debug;

/**
 * This class extends ClassLoader with additional support for defining
 * classes with an associated code source and permissions which are
 * retrieved by the system policy by default.
 *
 * <p>
 *  此类扩展了ClassLoader,同时还支持定义具有相关代码源和权限的类,这些权限由系统策略默认检索。
 * 
 * 
 * @author  Li Gong
 * @author  Roland Schemers
 */
public class SecureClassLoader extends ClassLoader {
    /*
     * If initialization succeed this is set to true and security checks will
     * succeed. Otherwise the object is not initialized and the object is
     * useless.
     * <p>
     *  如果初始化成功,则设置为true,安全检查将成功。否则对象不被初始化,对象是无用的。
     * 
     */
    private final boolean initialized;

    // HashMap that maps CodeSource to ProtectionDomain
    // @GuardedBy("pdcache")
    private final HashMap<CodeSource, ProtectionDomain> pdcache =
                        new HashMap<>(11);

    private static final Debug debug = Debug.getInstance("scl");

    static {
        ClassLoader.registerAsParallelCapable();
    }

    /**
     * Creates a new SecureClassLoader using the specified parent
     * class loader for delegation.
     *
     * <p>If there is a security manager, this method first
     * calls the security manager's {@code checkCreateClassLoader}
     * method  to ensure creation of a class loader is allowed.
     * <p>
     * <p>
     *  使用指定的父类装入器创建新的SecureClassLoader以进行委派。
     * 
     *  <p>如果有安全管理员,此方法首先会调用安全管理员的{@code checkCreateClassLoader}方法,以确保允许创建类加载器。
     * <p>
     * 
     * @param parent the parent ClassLoader
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkCreateClassLoader} method doesn't allow
     *             creation of a class loader.
     * @see SecurityManager#checkCreateClassLoader
     */
    protected SecureClassLoader(ClassLoader parent) {
        super(parent);
        // this is to make the stack depth consistent with 1.1
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkCreateClassLoader();
        }
        initialized = true;
    }

    /**
     * Creates a new SecureClassLoader using the default parent class
     * loader for delegation.
     *
     * <p>If there is a security manager, this method first
     * calls the security manager's {@code checkCreateClassLoader}
     * method  to ensure creation of a class loader is allowed.
     *
     * <p>
     *  使用默认父类装入器创建新的SecureClassLoader以进行委派。
     * 
     *  <p>如果有安全管理员,此方法首先会调用安全管理员的{@code checkCreateClassLoader}方法,以确保允许创建类加载器。
     * 
     * 
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkCreateClassLoader} method doesn't allow
     *             creation of a class loader.
     * @see SecurityManager#checkCreateClassLoader
     */
    protected SecureClassLoader() {
        super();
        // this is to make the stack depth consistent with 1.1
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkCreateClassLoader();
        }
        initialized = true;
    }

    /**
     * Converts an array of bytes into an instance of class Class,
     * with an optional CodeSource. Before the
     * class can be used it must be resolved.
     * <p>
     * If a non-null CodeSource is supplied a ProtectionDomain is
     * constructed and associated with the class being defined.
     * <p>
     * <p>
     *  将字节数组转换为Class类的实例,使用可选的CodeSource。在类可以使用之前必须解决。
     * <p>
     *  如果提供了一个非空的CodeSource,则构造一个ProtectionDomain并与被定义的类相关联。
     * <p>
     * 
     * @param      name the expected name of the class, or {@code null}
     *                  if not known, using '.' and not '/' as the separator
     *                  and without a trailing ".class" suffix.
     * @param      b    the bytes that make up the class data. The bytes in
     *             positions {@code off} through {@code off+len-1}
     *             should have the format of a valid class file as defined by
     *             <cite>The Java&trade; Virtual Machine Specification</cite>.
     * @param      off  the start offset in {@code b} of the class data
     * @param      len  the length of the class data
     * @param      cs   the associated CodeSource, or {@code null} if none
     * @return the {@code Class} object created from the data,
     *         and optional CodeSource.
     * @exception  ClassFormatError if the data did not contain a valid class
     * @exception  IndexOutOfBoundsException if either {@code off} or
     *             {@code len} is negative, or if
     *             {@code off+len} is greater than {@code b.length}.
     *
     * @exception  SecurityException if an attempt is made to add this class
     *             to a package that contains classes that were signed by
     *             a different set of certificates than this class, or if
     *             the class name begins with "java.".
     */
    protected final Class<?> defineClass(String name,
                                         byte[] b, int off, int len,
                                         CodeSource cs)
    {
        return defineClass(name, b, off, len, getProtectionDomain(cs));
    }

    /**
     * Converts a {@link java.nio.ByteBuffer ByteBuffer}
     * into an instance of class {@code Class}, with an optional CodeSource.
     * Before the class can be used it must be resolved.
     * <p>
     * If a non-null CodeSource is supplied a ProtectionDomain is
     * constructed and associated with the class being defined.
     * <p>
     * <p>
     *  将{@link java.nio.ByteBuffer ByteBuffer}转换为类{@code Class}的实例,并带有一个可选的CodeSource。在类可以使用之前必须解决。
     * <p>
     * 如果提供了一个非空的CodeSource,则构造一个ProtectionDomain并与被定义的类相关联。
     * <p>
     * 
     * @param      name the expected name of the class, or {@code null}
     *                  if not known, using '.' and not '/' as the separator
     *                  and without a trailing ".class" suffix.
     * @param      b    the bytes that make up the class data.  The bytes from positions
     *                  {@code b.position()} through {@code b.position() + b.limit() -1}
     *                  should have the format of a valid class file as defined by
     *                  <cite>The Java&trade; Virtual Machine Specification</cite>.
     * @param      cs   the associated CodeSource, or {@code null} if none
     * @return the {@code Class} object created from the data,
     *         and optional CodeSource.
     * @exception  ClassFormatError if the data did not contain a valid class
     * @exception  SecurityException if an attempt is made to add this class
     *             to a package that contains classes that were signed by
     *             a different set of certificates than this class, or if
     *             the class name begins with "java.".
     *
     * @since  1.5
     */
    protected final Class<?> defineClass(String name, java.nio.ByteBuffer b,
                                         CodeSource cs)
    {
        return defineClass(name, b, getProtectionDomain(cs));
    }

    /**
     * Returns the permissions for the given CodeSource object.
     * <p>
     * This method is invoked by the defineClass method which takes
     * a CodeSource as an argument when it is constructing the
     * ProtectionDomain for the class being defined.
     * <p>
     * <p>
     *  返回给定CodeSource对象的权限。
     * <p>
     *  此方法由defineClass方法调用,该方法在为正在定义的类构造ProtectionDomain时将CodeSource作为参数。
     * <p>
     * 
     * @param codesource the codesource.
     *
     * @return the permissions granted to the codesource.
     *
     */
    protected PermissionCollection getPermissions(CodeSource codesource)
    {
        check();
        return new Permissions(); // ProtectionDomain defers the binding
    }

    /*
     * Returned cached ProtectionDomain for the specified CodeSource.
     * <p>
     *  返回指定CodeSource的缓存ProtectionDomain。
     * 
     */
    private ProtectionDomain getProtectionDomain(CodeSource cs) {
        if (cs == null)
            return null;

        ProtectionDomain pd = null;
        synchronized (pdcache) {
            pd = pdcache.get(cs);
            if (pd == null) {
                PermissionCollection perms = getPermissions(cs);
                pd = new ProtectionDomain(cs, perms, this, null);
                pdcache.put(cs, pd);
                if (debug != null) {
                    debug.println(" getPermissions "+ pd);
                    debug.println("");
                }
            }
        }
        return pd;
    }

    /*
     * Check to make sure the class loader has been initialized.
     * <p>
     *  检查以确保类加载器已初始化。
     */
    private void check() {
        if (!initialized) {
            throw new SecurityException("ClassLoader object not initialized");
        }
    }

}
