/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.reflect;

/**
 * Member is an interface that reflects identifying information about
 * a single member (a field or a method) or a constructor.
 *
 * <p>
 *  成员是反映关于单个成员(字段或方法)或构造函数的标识信息的接口。
 * 
 * 
 * @see java.lang.Class
 * @see Field
 * @see Method
 * @see Constructor
 *
 * @author Nakul Saraiya
 */
public
interface Member {

    /**
     * Identifies the set of all public members of a class or interface,
     * including inherited members.
     * <p>
     *  标识类或接口的所有公共成员的集合,包括继承的成员。
     * 
     */
    public static final int PUBLIC = 0;

    /**
     * Identifies the set of declared members of a class or interface.
     * Inherited members are not included.
     * <p>
     *  标识类或接口的已声明成员的集合。不包括继承的成员。
     * 
     */
    public static final int DECLARED = 1;

    /**
     * Returns the Class object representing the class or interface
     * that declares the member or constructor represented by this Member.
     *
     * <p>
     *  返回表示声明由此成员表示的成员或构造函数的类或接口的Class对象。
     * 
     * 
     * @return an object representing the declaring class of the
     * underlying member
     */
    public Class<?> getDeclaringClass();

    /**
     * Returns the simple name of the underlying member or constructor
     * represented by this Member.
     *
     * <p>
     *  返回此成员表示的底层成员或构造函数的简单名称。
     * 
     * 
     * @return the simple name of the underlying member
     */
    public String getName();

    /**
     * Returns the Java language modifiers for the member or
     * constructor represented by this Member, as an integer.  The
     * Modifier class should be used to decode the modifiers in
     * the integer.
     *
     * <p>
     *  返回此成员表示的成员或构造函数的Java语言修饰符作为整数。修饰符类应该用于解码整数中的修饰符。
     * 
     * 
     * @return the Java language modifiers for the underlying member
     * @see Modifier
     */
    public int getModifiers();

    /**
     * Returns {@code true} if this member was introduced by
     * the compiler; returns {@code false} otherwise.
     *
     * <p>
     *  如果此成员由编译器引入,则返回{@code true};否则返回{@code false}。
     * 
     * @return true if and only if this member was introduced by
     * the compiler.
     * @jls 13.1 The Form of a Binary
     * @since 1.5
     */
    public boolean isSynthetic();
}
