/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2000, Oracle and/or its affiliates. All rights reserved.
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
package org.omg.CORBA;

/**
 * An object that indicates whether a method had completed running
 * when a <code>SystemException</code> was thrown.
 * <P>
 * The class <code>CompletionStatus</code>
 * contains three <code>CompletionStatus</code> instances, which are constants
 * representing each
 * possible completion status: <code>COMPLETED_MAYBE</code>,
 * <code>COMPLETED_NO</code>, and <code>COMPLETED_YES</code>.
 * It also contains
 * three <code>int</code> members, each a constant corresponding to one of
 * the <code>CompletionStatus</code> instances.  These <code>int</code>
 * members make it possible to use a <code>switch</code> statement.
 * <P>
 * The class also contains two methods:
 * <UL>
 * <LI><code>public int <bold>value</bold>()</code> -- which accesses the
 * <code>value</code> field of a <code>CompletionStatus</code> object
 * <LI><code>public static CompletionStatus
 * <bold>from_int</bold>(int i)</code> --
 * for creating an instance from one of the <code>int</code> members
 * </UL>
 * <p>
 *  一个对象,指示在抛出<code> SystemException </code>时方法是否已完成运行。
 * <P>
 *  类<code> CompletionStatus </code>包含三个<code> CompletionStatus </code>实例,它们是表示每个可能完成状态的常量：<code> COMPLE
 * TED_MAYBE </code>,<code> COMPLETED_NO </code> <code> COMPLETED_YES </code>。
 * 它还包含三个<code> int </code>成员,每个成员对应于<code> CompletionStatus </code>实例之一。
 * 这些<code> int </code>成员可以使用<code> switch </code>语句。
 * <P>
 *  该类还包含两种方法：
 * <UL>
 *  </>> </>>对象</>>的<code>值</code>字段中输入<li> <int> <code> public static CompletionStatus <bold> from_int 
 * </bold>(int i)</code>  - 用于从<code> int </code>。
 * </UL>
 * 
 * @see     org.omg.CORBA.SystemException
 * @since   JDK1.2
 */

public final class CompletionStatus implements org.omg.CORBA.portable.IDLEntity
{
/**
 * The constant indicating that a method completed running
 * before a <code>SystemException</code> was thrown.
 * <p>
 *  常量,表示方法在<code> SystemException </code>之前完成运行。
 * 
 */
    public static final int _COMPLETED_YES = 0,

/**
 * The constant indicating that a method had not completed running
 * when a <code>SystemException</code> was thrown.
 * <p>
 *  常量,表示当抛出<code> SystemException </code>时,方法未完成运行。
 * 
 */
        _COMPLETED_NO = 1,

/**
 * The constant indicating that it is unknown whether a method had
 * completed running when a <code>SystemException</code> was thrown.
 * <p>
 *  常量,表示当一个<code> SystemException </code>被抛出时,一个方法是否已经完成运行。
 * 
 */
        _COMPLETED_MAYBE = 2;


/**
 * An instance of <code>CompletionStatus</code> initialized with
 * the constant <code>_COMPLETED_YES</code>.
 * <p>
 * 使用常量<code> _COMPLETED_YES </code>初始化的<code> CompletionStatus </code>的实例。
 * 
 */
    public static final CompletionStatus COMPLETED_YES   = new CompletionStatus(_COMPLETED_YES);

/**
 * An instance of <code>CompletionStatus</code> initialized with
 * the constant <code>_COMPLETED_NO</code>.
 * <p>
 *  用常量<code> _COMPLETED_NO </code>初始化的<code> CompletionStatus </code>实例。
 * 
 */
    public static final CompletionStatus COMPLETED_NO    = new CompletionStatus(_COMPLETED_NO);

    /**
 * An instance of <code>CompletionStatus</code> initialized with
 * the constant <code>_COMPLETED_MAYBE</code>.
 * <p>
 *  用常量<code> _COMPLETED_MAYBE </code>初始化的<code> CompletionStatus </code>的实例。
 * 
 */
    public static final CompletionStatus COMPLETED_MAYBE = new CompletionStatus(_COMPLETED_MAYBE);

    /**
 * Retrieves the value of this <code>CompletionStatus</code> object.
 *
 * <p>
 *  检索此<code> CompletionStatus </code>对象的值。
 * 
 * 
 * @return  one of the possible <code>CompletionStatus</code> values:
 *          <code>_COMPLETED_YES</code>, <code>_COMPLETED_NO</code>, or
 *          <code>_COMPLETED_MAYBE</code>
 *
 */
    public int value() { return _value; }

/**
 * Creates a <code>CompletionStatus</code> object from the given <code>int</code>.
 *
 * <p>
 *  从给定的<code> int </code>创建<code> CompletionStatus </code>对象。
 * 
 * 
 * @param i  one of <code>_COMPLETED_YES</code>, <code>_COMPLETED_NO</code>, or
 *          <code>_COMPLETED_MAYBE</code>
 *
 * @return  one of the possible <code>CompletionStatus</code> objects
 *          with values:
 *          <code>_COMPLETED_YES</code>, <code>_COMPLETED_NO</code>, or
 *          <code>_COMPLETED_MAYBE</code>
 *
 * @exception org.omg.CORBA.BAD_PARAM  if the argument given is not one of the
 *            <code>int</code> constants defined in <code>CompletionStatus</code>
 */
    public static CompletionStatus from_int(int i)  {
        switch (i) {
        case _COMPLETED_YES:
            return COMPLETED_YES;
        case _COMPLETED_NO:
            return COMPLETED_NO;
        case _COMPLETED_MAYBE:
            return COMPLETED_MAYBE;
        default:
            throw new org.omg.CORBA.BAD_PARAM();
        }
    }


/**
 * Creates a <code>CompletionStatus</code> object from the given <code>int</code>.
 *
 * <p>
 *  从给定的<code> int </code>创建<code> CompletionStatus </code>对象。
 * 
 * @param _value  one of <code>_COMPLETED_YES</code>, <code>_COMPLETED_NO</code>, or
 *          <code>_COMPLETED_MAYBE</code>
 *
 */
    private CompletionStatus(int _value) {
        this._value = _value;
    }

    private int _value;
}
