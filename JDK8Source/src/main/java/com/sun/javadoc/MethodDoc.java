/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javadoc;

/**
 * Represents a method of a java class.
 *
 * <p>
 *  表示java类的方法。
 * 
 * 
 * @since 1.2
 * @author Robert Field
 */
public interface MethodDoc extends ExecutableMemberDoc {

    /**
     * Return true if this method is abstract
     * <p>
     *  如果此方法是抽象的,则返回true
     * 
     */
    boolean isAbstract();

    /**
     * Return true if this method is default
     * <p>
     *  如果此方法是默认值,则返回true
     * 
     */
    boolean isDefault();

    /**
     * Get return type.
     *
     * <p>
     *  获取返回类型。
     * 
     * 
     * @return the return type of this method, null if it
     * is a constructor.
     */
    Type returnType();

    /**
     * Return the class containing the method that this method overrides.
     *
     * <p> <i>The <code>overriddenClass</code> method cannot
     * accommodate certain generic type constructs.  The
     * <code>overriddenType</code> method should be used instead.</i>
     *
     * <p>
     *  返回包含此方法覆盖的方法的类。
     * 
     *  <p> <i> <code> overriddenClass </code>方法无法容纳某些通用类型结构。应该使用<code> overriddenType </code>方法。</i>
     * 
     * 
     * @return a ClassDoc representing the superclass
     *         defining a method that this method overrides, or null if
     *         this method does not override.
     */
    ClassDoc overriddenClass();

    /**
     * Return the type containing the method that this method overrides.
     * It may be a <code>ClassDoc</code> or a <code>ParameterizedType</code>.
     *
     * <p>
     *  返回包含此方法覆盖的方法的类型。它可以是<code> ClassDoc </code>或<code> ParameterizedType </code>。
     * 
     * 
     * @return the supertype whose method is overridden, or null if this
     *         method does not override another in a superclass
     * @since 1.5
     */
    Type overriddenType();

    /**
     * Return the method that this method overrides.
     *
     * <p>
     *  返回此方法覆盖的方法。
     * 
     * 
     * @return a MethodDoc representing a method definition
     * in a superclass this method overrides, null if
     * this method does not override.
     */
    MethodDoc overriddenMethod();

    /**
     * Tests whether this method overrides another.
     * The overridden method may be one declared in a superclass or
     * a superinterface (unlike {@link #overriddenMethod()}).
     *
     * <p> When a non-abstract method overrides an abstract one, it is
     * also said to <i>implement</i> the other.
     *
     * <p>
     *  测试此方法是否覆盖另一个。重写的方法可以是在超类或超接口中声明的(不像{@link #overriddenMethod()})。
     * 
     * 
     * @param meth  the other method to examine
     * @return <tt>true</tt> if this method overrides the other
     * @since 1.5
     */
    boolean overrides(MethodDoc meth);
}
