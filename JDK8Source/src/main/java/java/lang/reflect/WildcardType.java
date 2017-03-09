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

package java.lang.reflect;

/**
 * WildcardType represents a wildcard type expression, such as
 * {@code ?}, {@code ? extends Number}, or {@code ? super Integer}.
 *
 * <p>
 *  WildcardType表示通配符类型表达式,例如{@code?},{@code?扩展Number}或{@code? super Integer}。
 * 
 * 
 * @since 1.5
 */
public interface WildcardType extends Type {
    /**
     * Returns an array of {@code Type} objects representing the  upper
     * bound(s) of this type variable.  Note that if no upper bound is
     * explicitly declared, the upper bound is {@code Object}.
     *
     * <p>For each upper bound B :
     * <ul>
     *  <li>if B is a parameterized type or a type variable, it is created,
     *  (see {@link java.lang.reflect.ParameterizedType ParameterizedType}
     *  for the details of the creation process for parameterized types).
     *  <li>Otherwise, B is resolved.
     * </ul>
     *
     * <p>
     *  返回表示此类型变量上限的{@code Type}对象数组。注意,如果没有明确声明上界,上界是{@code Object}。
     * 
     *  <p>对于每个上限B：
     * <ul>
     *  <li>如果B是参数化类型或类型变量,则会创建它(有关参数化类型的创建过程的详细信息,请参阅{@link java.lang.reflect.ParameterizedType ParameterizedType}
     * )。
     *  <li>否则,B已解决。
     * </ul>
     * 
     * 
     * @return an array of Types representing the upper bound(s) of this
     *     type variable
     * @throws TypeNotPresentException if any of the
     *     bounds refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the
     *     bounds refer to a parameterized type that cannot be instantiated
     *     for any reason
     */
    Type[] getUpperBounds();

    /**
     * Returns an array of {@code Type} objects representing the
     * lower bound(s) of this type variable.  Note that if no lower bound is
     * explicitly declared, the lower bound is the type of {@code null}.
     * In this case, a zero length array is returned.
     *
     * <p>For each lower bound B :
     * <ul>
     *   <li>if B is a parameterized type or a type variable, it is created,
     *  (see {@link java.lang.reflect.ParameterizedType ParameterizedType}
     *  for the details of the creation process for parameterized types).
     *   <li>Otherwise, B is resolved.
     * </ul>
     *
     * <p>
     *  返回表示此类型变量的下限的{@code Type}对象的数组。注意,如果没有明确声明下界,下界是{@code null}的类型。在这种情况下,返回零长度数组。
     * 
     *  <p>对于每个下限B：
     * <ul>
     *  <li>如果B是参数化类型或类型变量,则会创建它(有关参数化类型的创建过程的详细信息,请参阅{@link java.lang.reflect.ParameterizedType ParameterizedType}
     * 
     * @return an array of Types representing the lower bound(s) of this
     *     type variable
     * @throws TypeNotPresentException if any of the
     *     bounds refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the
     *     bounds refer to a parameterized type that cannot be instantiated
     *     for any reason
     */
    Type[] getLowerBounds();
    // one or many? Up to language spec; currently only one, but this API
    // allows for generalization.
}
