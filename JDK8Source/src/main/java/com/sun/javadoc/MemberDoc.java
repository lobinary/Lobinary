/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a member of a java class: field, constructor, or method.
 * This is an abstract class dealing with information common to
 * method, constructor and field members. Class members of a class
 * (innerclasses) are represented instead by ClassDoc.
 *
 * <p>
 *  表示java类的成员：字段,构造函数或方法。这是一个处理方法,构造函数和字段成员通用的信息的抽象类。类(innerclasses)的类成员由ClassDoc表示。
 * 
 * 
 * @see MethodDoc
 * @see FieldDoc
 * @see ClassDoc
 *
 * @author Kaiyang Liu (original)
 * @author Robert Field (rewrite)
 */
public interface MemberDoc extends ProgramElementDoc {

    /**
     * Returns true if this member was synthesized by the compiler.
     * <p>
     *  如果此成员由编译器合成,则返回true。
     */
    boolean isSynthetic();
}
