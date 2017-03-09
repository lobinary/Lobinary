/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.instrument;

/*
 * Copyright 2003 Wily Technology, Inc.
 * <p>
 *  版权所有2003 Wily Technology,Inc.
 * 
 */

/**
 * This class serves as a parameter block to the <code>Instrumentation.redefineClasses</code> method.
 * Serves to bind the <code>Class</code> that needs redefining together with the new class file bytes.
 *
 * <p>
 *  此类充当<code> Instrumentation.redefineClasses </code>方法的参数块。用于绑定需要与新类文件字节一起重新定义的<code> Class </code>。
 * 
 * 
 * @see     java.lang.instrument.Instrumentation#redefineClasses
 * @since   1.5
 */
public final class ClassDefinition {
    /**
     *  The class to redefine
     * <p>
     *  重新定义的类
     * 
     */
    private final Class<?> mClass;

    /**
     *  The replacement class file bytes
     * <p>
     *  替换类文件字节
     * 
     */
    private final byte[]   mClassFile;

    /**
     *  Creates a new <code>ClassDefinition</code> binding using the supplied
     *  class and class file bytes. Does not copy the supplied buffer, just captures a reference to it.
     *
     * <p>
     *  使用提供的类和类文件字节创建一个新的<code> ClassDefinition </code>绑定。不复制提供的缓冲区,只是捕获了它的引用。
     * 
     * 
     * @param theClass the <code>Class</code> that needs redefining
     * @param theClassFile the new class file bytes
     *
     * @throws java.lang.NullPointerException if the supplied class or array is <code>null</code>.
     */
    public
    ClassDefinition(    Class<?> theClass,
                        byte[]  theClassFile) {
        if (theClass == null || theClassFile == null) {
            throw new NullPointerException();
        }
        mClass      = theClass;
        mClassFile  = theClassFile;
    }

    /**
     * Returns the class.
     *
     * <p>
     *  返回类。
     * 
     * 
     * @return    the <code>Class</code> object referred to.
     */
    public Class<?>
    getDefinitionClass() {
        return mClass;
    }

    /**
     * Returns the array of bytes that contains the new class file.
     *
     * <p>
     *  返回包含新类文件的字节数组。
     * 
     * @return    the class file bytes.
     */
    public byte[]
    getDefinitionClassFile() {
        return mClassFile;
    }
}
