/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * TypeVariable is the common superinterface for type variables of kinds.
 * A type variable is created the first time it is needed by a reflective
 * method, as specified in this package.  If a type variable t is referenced
 * by a type (i.e, class, interface or annotation type) T, and T is declared
 * by the nth enclosing class of T (see JLS 8.1.2), then the creation of t
 * requires the resolution (see JVMS 5) of the ith enclosing class of T,
 * for i = 0 to n, inclusive. Creating a type variable must not cause the
 * creation of its bounds. Repeated creation of a type variable has no effect.
 *
 * <p>Multiple objects may be instantiated at run-time to
 * represent a given type variable. Even though a type variable is
 * created only once, this does not imply any requirement to cache
 * instances representing the type variable. However, all instances
 * representing a type variable must be equal() to each other.
 * As a consequence, users of type variables must not rely on the identity
 * of instances of classes implementing this interface.
 *
 * <p>
 *  TypeVariable是类型变量的常用超级接口。类型变量在第一次被反射方法需要时创建,如本包中所述。
 * 如果类型变量t由类型(即类,接口或注释类型)T引用,并且T由T的第n个封闭类(参见JLS 8.1.2)声明,则创建t需要解析见JVMS 5)的第i个封闭类T,对于i = 0到n,包括。
 * 创建类型变量不能导致创建其边界。重复创建类型变量没有任何效果。
 * 
 *  <p>多个对象可以在运行时实例化以表示给定的类型变量。即使一个类型变量只创建一次,这并不意味着对表示类型变量的实例的任何缓存。但是,表示一个类型变量的所有实例必须彼此相等()。
 * 因此,类型变量的用户不能依赖于实现此接口的类的实例的身份。
 * 
 * 
 * @param <D> the type of generic declaration that declared the
 * underlying type variable.
 *
 * @since 1.5
 */
public interface TypeVariable<D extends GenericDeclaration> extends Type, AnnotatedElement {
    /**
     * Returns an array of {@code Type} objects representing the
     * upper bound(s) of this type variable.  Note that if no upper bound is
     * explicitly declared, the upper bound is {@code Object}.
     *
     * <p>For each upper bound B: <ul> <li>if B is a parameterized
     * type or a type variable, it is created, (see {@link
     * java.lang.reflect.ParameterizedType ParameterizedType} for the
     * details of the creation process for parameterized types).
     * <li>Otherwise, B is resolved.  </ul>
     *
     * <p>
     *  返回表示此类型变量上限的{@code Type}对象数组。注意,如果没有明确声明上界,上界是{@code Object}。
     * 
     * <p>对于每个上界B：<ul> <li>如果B是参数化类型或类型变量,则创建它(参见{@link java.lang.reflect.ParameterizedType ParameterizedType}
     * 参数化类型的过程)。
     *  <li>否则,B已解决。 </ul>。
     * 
     * 
     * @throws TypeNotPresentException  if any of the
     *     bounds refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if any of the
     *     bounds refer to a parameterized type that cannot be instantiated
     *     for any reason
     * @return an array of {@code Type}s representing the upper
     *     bound(s) of this type variable
    */
    Type[] getBounds();

    /**
     * Returns the {@code GenericDeclaration} object representing the
     * generic declaration declared this type variable.
     *
     * <p>
     *  返回表示声明此类型变量的通用声明的{@code GenericDeclaration}对象。
     * 
     * 
     * @return the generic declaration declared for this type variable.
     *
     * @since 1.5
     */
    D getGenericDeclaration();

    /**
     * Returns the name of this type variable, as it occurs in the source code.
     *
     * <p>
     *  返回此类型变量的名称,因为它出现在源代码中。
     * 
     * 
     * @return the name of this type variable, as it appears in the source code
     */
    String getName();

    /**
     * Returns an array of AnnotatedType objects that represent the use of
     * types to denote the upper bounds of the type parameter represented by
     * this TypeVariable. The order of the objects in the array corresponds to
     * the order of the bounds in the declaration of the type parameter.
     *
     * Returns an array of length 0 if the type parameter declares no bounds.
     *
     * <p>
     *  返回一个AnnotatedType对象的数组,这些对象表示使用类型来表示由此TypeVariable表示的类型参数的上限。数组中对象的顺序与type参数的声明中的bounds的顺序相对应。
     * 
     * 
     * @return an array of objects representing the upper bounds of the type variable
     * @since 1.8
     */
     AnnotatedType[] getAnnotatedBounds();
}
