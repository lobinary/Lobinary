/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * The {@code Void} class is an uninstantiable placeholder class to hold a
 * reference to the {@code Class} object representing the Java keyword
 * void.
 *
 * <p>
 *  {@code Void}类是一个不可重复的占位符类,用于保存对表示Java关键字void的{@code Class}对象的引用。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.1
 */
public final
class Void {

    /**
     * The {@code Class} object representing the pseudo-type corresponding to
     * the keyword {@code void}.
     * <p>
     *  表示与关键字{@code void}相对应的伪类型的{@code Class}对象。
     * 
     */
    @SuppressWarnings("unchecked")
    public static final Class<Void> TYPE = (Class<Void>) Class.getPrimitiveClass("void");

    /*
     * The Void class cannot be instantiated.
     * <p>
     *  Void类不能被实例化。
     */
    private Void() {}
}
