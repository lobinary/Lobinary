/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.annotation;

/**
 * Annotation retention policy.  The constants of this enumerated type
 * describe the various policies for retaining annotations.  They are used
 * in conjunction with the {@link Retention} meta-annotation type to specify
 * how long annotations are to be retained.
 *
 * <p>
 *  注释保留策略。此枚举类型的常量描述了用于保留注释的各种策略。它们与{@link Retention}元注释类型结合使用,以指定要保留注释的时间长度。
 * 
 * 
 * @author  Joshua Bloch
 * @since 1.5
 */
public enum RetentionPolicy {
    /**
     * Annotations are to be discarded by the compiler.
     * <p>
     *  注释将被编译器丢弃。
     * 
     */
    SOURCE,

    /**
     * Annotations are to be recorded in the class file by the compiler
     * but need not be retained by the VM at run time.  This is the default
     * behavior.
     * <p>
     *  注释由编译器记录在类文件中,但不需要在运行时由VM保留。这是默认行为。
     * 
     */
    CLASS,

    /**
     * Annotations are to be recorded in the class file by the compiler and
     * retained by the VM at run time, so they may be read reflectively.
     *
     * <p>
     *  注释由编译器记录在类文件中,并由VM在运行时保留,因此它们可以被反射读取。
     * 
     * @see java.lang.reflect.AnnotatedElement
     */
    RUNTIME
}
