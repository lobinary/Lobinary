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
 * ParameterizedType represents a parameterized type such as
 * Collection&lt;String&gt;.
 *
 * <p>A parameterized type is created the first time it is needed by a
 * reflective method, as specified in this package. When a
 * parameterized type p is created, the generic type declaration that
 * p instantiates is resolved, and all type arguments of p are created
 * recursively. See {@link java.lang.reflect.TypeVariable
 * TypeVariable} for details on the creation process for type
 * variables. Repeated creation of a parameterized type has no effect.
 *
 * <p>Instances of classes that implement this interface must implement
 * an equals() method that equates any two instances that share the
 * same generic type declaration and have equal type parameters.
 *
 * <p>
 *  ParameterizedType表示参数化类型,例如Collection&lt; String&gt ;.
 * 
 *  <p>参数化类型在第一次被反射方法需要时创建,如本包中所述。当创建参数化类型p时,p实例化的通用类型声明被解析,并且递归地创建p的所有类型参数。
 * 有关类型变量创建过程的详细信息,请参阅{@link java.lang.reflect.TypeVariable TypeVariable}。重复创建参数化类型不起作用。
 * 
 *  <p>实现此接口的类实例必须实现equals()方法,该方法等同于共享同一通用类型声明并具有相等类型参数的任何两个实例。
 * 
 * 
 * @since 1.5
 */
public interface ParameterizedType extends Type {
    /**
     * Returns an array of {@code Type} objects representing the actual type
     * arguments to this type.
     *
     * <p>Note that in some cases, the returned array be empty. This can occur
     * if this type represents a non-parameterized type nested within
     * a parameterized type.
     *
     * <p>
     *  返回表示此类型的实际类型参数的{@code Type}对象数组。
     * 
     *  <p>请注意,在某些情况下,返回的数组为空。如果此类型表示嵌套在参数化类型中的非参数化类型,则会发生此错误。
     * 
     * 
     * @return an array of {@code Type} objects representing the actual type
     *     arguments to this type
     * @throws TypeNotPresentException if any of the
     *     actual type arguments refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the
     *     actual type parameters refer to a parameterized type that cannot
     *     be instantiated for any reason
     * @since 1.5
     */
    Type[] getActualTypeArguments();

    /**
     * Returns the {@code Type} object representing the class or interface
     * that declared this type.
     *
     * <p>
     *  返回表示声明此类型的类或接口的{@code Type}对象。
     * 
     * 
     * @return the {@code Type} object representing the class or interface
     *     that declared this type
     * @since 1.5
     */
    Type getRawType();

    /**
     * Returns a {@code Type} object representing the type that this type
     * is a member of.  For example, if this type is {@code O<T>.I<S>},
     * return a representation of {@code O<T>}.
     *
     * <p>If this type is a top-level type, {@code null} is returned.
     *
     * <p>
     *  返回表示此类型是其成员的类型的{@code Type}对象。例如,如果该类型是{@code O <T> .I <S>},则返回{@code O <T>}的表示。
     * 
     * 
     * @return a {@code Type} object representing the type that
     *     this type is a member of. If this type is a top-level type,
     *     {@code null} is returned
     * @throws TypeNotPresentException if the owner type
     *     refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if the owner type
     *     refers to a parameterized type that cannot be instantiated
     *     for any reason
     * @since 1.5
     */
    Type getOwnerType();
}
