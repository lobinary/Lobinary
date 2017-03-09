/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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
/* <p>
/* 
 * Represents a @throws or @exception documentation tag.
 * Parses and holds the exception name and exception comment.
 * Note: @exception is a backwards compatible synonymy for @throws.
 *
 * @author Robert Field
 * @author Atul M Dambalkar
 * @see ExecutableMemberDoc#throwsTags()
 *
 */
public interface ThrowsTag extends Tag {

    /**
     * Return the name of the exception
     * associated with this <code>ThrowsTag</code>.
     *
     * <p>
     *  返回与此<code> ThrowsTag </code>关联的异常的名称。
     * 
     * 
     * @return name of the exception.
     */
    String exceptionName();

    /**
     * Return the exception comment
     * associated with this <code>ThrowsTag</code>.
     *
     * <p>
     *  返回与此<code> ThrowsTag </code>关联的异常注释。
     * 
     * 
     * @return exception comment.
     */
    String exceptionComment();

    /**
     * Return a <code>ClassDoc</code> that represents the exception.
     * If the type of the exception is a type variable, return the
     * <code>ClassDoc</code> of its erasure.
     *
     * <p> <i>This method cannot accommodate certain generic type
     * constructs.  The <code>exceptionType</code> method
     * should be used instead.</i>
     *
     * <p>
     *  返回一个代表异常的<code> ClassDoc </code>。如果异常的类型是类型变量,则返回其擦除的<code> ClassDoc </code>。
     * 
     *  <p> <i>此方法无法容纳某些通用类型结构。应该使用<code> exceptionType </code>方法。</i>
     * 
     * 
     * @return <code>ClassDoc</code> that represents the exception.
     * @see #exceptionType
     */
    ClassDoc exception();

    /**
     * Return the type of the exception
     * associated with this <code>ThrowsTag</code>.
     * This may be a <code>ClassDoc</code> or a <code>TypeVariable</code>.
     *
     * <p>
     * 
     * @return the type of the exception.
     * @since 1.5
     */
    Type exceptionType();
}
