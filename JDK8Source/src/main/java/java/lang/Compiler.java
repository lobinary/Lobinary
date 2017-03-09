/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * The {@code Compiler} class is provided to support Java-to-native-code
 * compilers and related services. By design, the {@code Compiler} class does
 * nothing; it serves as a placeholder for a JIT compiler implementation.
 *
 * <p> When the Java Virtual Machine first starts, it determines if the system
 * property {@code java.compiler} exists. (System properties are accessible
 * through {@link System#getProperty(String)} and {@link
 * System#getProperty(String, String)}.  If so, it is assumed to be the name of
 * a library (with a platform-dependent exact location and type); {@link
 * System#loadLibrary} is called to load that library. If this loading
 * succeeds, the function named {@code java_lang_Compiler_start()} in that
 * library is called.
 *
 * <p> If no compiler is available, these methods do nothing.
 *
 * <p>
 *  提供{@code Compiler}类以支持Java到本地代码编译器和相关服务。通过设计,{@code Compiler}类不做任何事;它充当JIT编译器实现的占位符。
 * 
 *  <p>当Java虚拟机第一次启动时,它确定系统属性{@code java.compiler}是否存在。
 *  (系统属性可通过{@link System#getProperty(String)}和{@link System#getProperty(String,String)}访问,如果是这样,则假设它是一个
 * 库的名称位置和类型);调用{@link System#loadLibrary}加载该库。
 *  <p>当Java虚拟机第一次启动时,它确定系统属性{@code java.compiler}是否存在。
 * 如果加载成功,则调用该库中名为{@code java_lang_Compiler_start()}的函数。
 * 
 *  <p>如果没有可用的编译器,这些方法什么也不做。
 * 
 * 
 * @author  Frank Yellin
 * @since   JDK1.0
 */
public final class Compiler  {
    private Compiler() {}               // don't make instances

    private static native void initialize();

    private static native void registerNatives();

    static {
        registerNatives();
        java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction<Void>() {
                public Void run() {
                    boolean loaded = false;
                    String jit = System.getProperty("java.compiler");
                    if ((jit != null) && (!jit.equals("NONE")) &&
                        (!jit.equals("")))
                    {
                        try {
                            System.loadLibrary(jit);
                            initialize();
                            loaded = true;
                        } catch (UnsatisfiedLinkError e) {
                            System.err.println("Warning: JIT compiler \"" +
                              jit + "\" not found. Will use interpreter.");
                        }
                    }
                    String info = System.getProperty("java.vm.info");
                    if (loaded) {
                        System.setProperty("java.vm.info", info + ", " + jit);
                    } else {
                        System.setProperty("java.vm.info", info + ", nojit");
                    }
                    return null;
                }
            });
    }

    /**
     * Compiles the specified class.
     *
     * <p>
     *  编译指定的类。
     * 
     * 
     * @param  clazz
     *         A class
     *
     * @return  {@code true} if the compilation succeeded; {@code false} if the
     *          compilation failed or no compiler is available
     *
     * @throws  NullPointerException
     *          If {@code clazz} is {@code null}
     */
    public static native boolean compileClass(Class<?> clazz);

    /**
     * Compiles all classes whose name matches the specified string.
     *
     * <p>
     *  编译名称与指定字符串匹配的所有类。
     * 
     * 
     * @param  string
     *         The name of the classes to compile
     *
     * @return  {@code true} if the compilation succeeded; {@code false} if the
     *          compilation failed or no compiler is available
     *
     * @throws  NullPointerException
     *          If {@code string} is {@code null}
     */
    public static native boolean compileClasses(String string);

    /**
     * Examines the argument type and its fields and perform some documented
     * operation.  No specific operations are required.
     *
     * <p>
     *  检查参数类型及其字段并执行一些记录的操作。无需特定操作。
     * 
     * 
     * @param  any
     *         An argument
     *
     * @return  A compiler-specific value, or {@code null} if no compiler is
     *          available
     *
     * @throws  NullPointerException
     *          If {@code any} is {@code null}
     */
    public static native Object command(Object any);

    /**
     * Cause the Compiler to resume operation.
     * <p>
     *  导致编译器恢复操作。
     * 
     */
    public static native void enable();

    /**
     * Cause the Compiler to cease operation.
     * <p>
     *  导致编译器停止操作。
     */
    public static native void disable();
}
