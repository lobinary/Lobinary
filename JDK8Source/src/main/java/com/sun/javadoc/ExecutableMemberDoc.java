/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a method or constructor of a java class.
 *
 * <p>
 *  表示java类的方法或构造函数。
 * 
 * 
 * @since 1.2
 * @author Robert Field
 */
public interface ExecutableMemberDoc extends MemberDoc {

    /**
     * Return exceptions this method or constructor throws.
     * If the type of the exception is a type variable, return the
     * <code>ClassDoc</code> of its erasure.
     *
     * <p> <i>The <code>thrownExceptions</code> method cannot
     * accommodate certain generic type constructs.  The
     * <code>thrownExceptionTypes</code> method should be used
     * instead.</i>
     *
     * <p>
     *  返回异常此方法或构造函数throws。如果异常的类型是类型变量,则返回其擦除的<code> ClassDoc </code>。
     * 
     *  <p> <i> <code> thrownExceptions </code>方法无法容纳某些通用类型结构。应该使用<code> thrownExceptionTypes </code>方法。
     * </i>。
     * 
     * 
     * @return an array of ClassDoc[] representing the exceptions
     *         thrown by this method.
     * @see #thrownExceptionTypes
     */
    ClassDoc[] thrownExceptions();

    /**
     * Return exceptions this method or constructor throws.
     *
     * <p>
     *  返回异常此方法或构造函数throws。
     * 
     * 
     * @return an array representing the exceptions thrown by this method.
     *         Each array element is either a <code>ClassDoc</code> or a
     *         <code>TypeVariable</code>.
     * @since 1.5
     */
    Type[] thrownExceptionTypes();

    /**
     * Return true if this method is native
     * <p>
     *  如果此方法是本机的,则返回true
     * 
     */
    boolean isNative();

    /**
     * Return true if this method is synchronized
     * <p>
     *  如果此方法同步,则​​返回true
     * 
     */
    boolean isSynchronized();

    /**
     * Return true if this method was declared to take a variable number
     * of arguments.
     *
     * <p>
     *  如果此方法被声明为采用可变数量的参数,则返回true。
     * 
     * 
     * @since 1.5
     */
    public boolean isVarArgs();

    /**
     * Get argument information.
     *
     * <p>
     *  获取参数信息。
     * 
     * 
     * @see Parameter
     *
     * @return an array of Parameter, one element per argument
     * in the order the arguments are present.
     */
    Parameter[] parameters();

    /**
     * Get the receiver type of this executable element.
     *
     * <p>
     *  获取此可执行元素的接收器类型。
     * 
     * 
     * @return the receiver type of this executable element.
     * @since 1.8
     */
    Type receiverType();

    /**
     * Return the throws tags in this method.
     *
     * <p>
     *  在此方法中返回throws标记。
     * 
     * 
     * @return an array of ThrowTag containing all <code>&#64;exception</code>
     * and <code>&#64;throws</code> tags.
     */
    ThrowsTag[] throwsTags();

    /**
     * Return the param tags in this method, excluding the type
     * parameter tags.
     *
     * <p>
     *  在此方法中返回参数标记,不包括类型参数标记。
     * 
     * 
     * @return an array of ParamTag containing all <code>&#64;param</code> tags
     * corresponding to the parameters of this method.
     */
    ParamTag[] paramTags();

    /**
     * Return the type parameter tags in this method.
     *
     * <p>
     *  返回此方法中的类型参数标签。
     * 
     * 
     * @return an array of ParamTag containing all <code>&#64;param</code> tags
     * corresponding to the type parameters of this method.
     * @since 1.5
     */
    ParamTag[] typeParamTags();

    /**
     * Get the signature. It is the parameter list, type is qualified.
     *      For instance, for a method <code>mymethod(String x, int y)</code>,
     *      it will return <code>(java.lang.String,int)</code>.
     * <p>
     *  获取签名。它是参数列表,类型是qualified。
     * 例如,对于<code> mymethod(String x,int y)</code>的方法,它将返回<code>(java.lang.String,int)</code>。
     * 
     */
    String signature();

    /**
     * get flat signature.  all types are not qualified.
     *      return a String, which is the flat signiture of this member.
     *      It is the parameter list, type is not qualified.
     *      For instance, for a method <code>mymethod(String x, int y)</code>,
     *      it will return <code>(String, int)</code>.
     * <p>
     *  获得平签名。所有类型都不合格。返回一个String,这是这个成员的平均意义。它是参数列表,类型不合格。
     * 例如,对于<code> mymethod(String x,int y)</code>的方法,它将返回<code>(String,int)</code>。
     * 
     */
    String flatSignature();

    /**
     * Return the formal type parameters of this method or constructor.
     * Return an empty array if this method or constructor is not generic.
     *
     * <p>
     * 
     * @return the formal type parameters of this method or constructor.
     * @since 1.5
     */
    TypeVariable[] typeParameters();
}
