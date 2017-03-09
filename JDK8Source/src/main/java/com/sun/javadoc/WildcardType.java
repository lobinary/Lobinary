/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a wildcard type argument.
 * Examples include:    <pre>
 * {@code <?>}
 * {@code <? extends E>}
 * {@code <? super T>}
 * </pre>
 * A wildcard type can have explicit <i>extends</i> bounds
 * or explicit <i>super</i> bounds or neither, but not both.
 *
 * <p>
 *  表示通配符类型参数。示例包括：<pre> {@code <?>} {@code <? extends E>} {@code <? super T>}
 * </pre>
 *  通配符类型可以具有显式的扩展边界或显式超边界,或者既不具有显式的边界,也不具有两者。
 * 
 * 
 * @author Scott Seligman
 * @since 1.5
 */
public interface WildcardType extends Type {

    /**
     * Return the upper bounds of this wildcard type argument
     * as given by the <i>extends</i> clause.
     * Return an empty array if no such bounds are explicitly given.
     *
     * <p>
     *  返回由<i> extends </i>子句给定的此通配符类型参数的上限。如果没有明确给出这样的边界,则返回一个空数组。
     * 
     * 
     * @return the extends bounds of this wildcard type argument
     */
    Type[] extendsBounds();

    /**
     * Return the lower bounds of this wildcard type argument
     * as given by the <i>super</i> clause.
     * Return an empty array if no such bounds are explicitly given.
     *
     * <p>
     *  返回由<i> super </i>子句给定的此通配符类型参数的下限。如果没有明确给出这样的边界,则返回一个空数组。
     * 
     * @return the super bounds of this wildcard type argument
     */
    Type[] superBounds();
}
