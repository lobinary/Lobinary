/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model.type;


import javax.lang.model.element.Element;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.util.Types;


/**
 * Represents a type variable.
 * A type variable may be explicitly declared by a
 * {@linkplain TypeParameterElement type parameter} of a
 * type, method, or constructor.
 * A type variable may also be declared implicitly, as by
 * the capture conversion of a wildcard type argument
 * (see chapter 5 of
 * <cite>The Java&trade; Language Specification</cite>).
 *
 * <p>
 *  表示一个类型变量。类型变量可以由类型,方法或构造函数的{@linkplain TypeParameterElement类型参数}显式声明。
 * 类型变量也可以通过通配符类型参数的捕获转换来隐式声明(参见<cite> Java&trade; Language Specification </cite>的第5章)。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see TypeParameterElement
 * @since 1.6
 */
public interface TypeVariable extends ReferenceType {

    /**
     * Returns the element corresponding to this type variable.
     *
     * <p>
     *  返回与此类型变量对应的元素。
     * 
     * 
     * @return the element corresponding to this type variable
     */
    Element asElement();

    /**
     * Returns the upper bound of this type variable.
     *
     * <p> If this type variable was declared with no explicit
     * upper bounds, the result is {@code java.lang.Object}.
     * If it was declared with multiple upper bounds,
     * the result is an {@linkplain IntersectionType intersection type};
     * individual bounds can be found by examining the result's
     * {@linkplain IntersectionType#getBounds() bounds}.
     *
     * <p>
     *  返回此类型变量的上限。
     * 
     *  <p>如果这个类型变量没有明确的上界,结果是{@code java.lang.Object}。
     * 如果它被声明有多个上界,结果是{@linkplain IntersectionType交集类型};可以通过检查结果的{@linkplain IntersectionType#getBounds()bounds)找到单个边界。
     *  <p>如果这个类型变量没有明确的上界,结果是{@code java.lang.Object}。
     * 
     * @return the upper bound of this type variable
     */
    TypeMirror getUpperBound();

    /**
     * Returns the lower bound of this type variable.  While a type
     * parameter cannot include an explicit lower bound declaration,
     * capture conversion can produce a type variable with a
     * non-trivial lower bound.  Type variables otherwise have a
     * lower bound of {@link NullType}.
     *
     * <p>
     * 
     * 
     * @return the lower bound of this type variable
     */
    TypeMirror getLowerBound();
}
