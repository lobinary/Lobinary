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


import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;


/**
 * Represents a declared type, either a class type or an interface type.
 * This includes parameterized types such as {@code java.util.Set<String>}
 * as well as raw types.
 *
 * <p> While a {@code TypeElement} represents a class or interface
 * <i>element</i>, a {@code DeclaredType} represents a class
 * or interface <i>type</i>, the latter being a use
 * (or <i>invocation</i>) of the former.
 * See {@link TypeElement} for more on this distinction.
 *
 * <p> The supertypes (both class and interface types) of a declared
 * type may be found using the {@link
 * Types#directSupertypes(TypeMirror)} method.  This returns the
 * supertypes with any type arguments substituted in.
 *
 * <p>
 *  表示声明的类型,类类型或接口类型。这包括参数化类型,例如{@code java.util.Set <String>}以及原始类型。
 * 
 *  <p> {@code TypeElement}表示类或接口<i>元素</i>,{@code DeclaredType}表示类或接口<i>类型</i>,后者是一个或<i>调用</i>)。
 * 有关此区别的更多信息,请参见{@link TypeElement}。
 * 
 *  <p>声明类型的超类型(类和接口类型)可以使用{@link Types#directSupertypes(TypeMirror)}方法找到。这返回任何类型参数替换的超类型。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see TypeElement
 * @since 1.6
 */
public interface DeclaredType extends ReferenceType {

    /**
     * Returns the element corresponding to this type.
     *
     * <p>
     *  返回与此类型对应的元素。
     * 
     * 
     * @return the element corresponding to this type
     */
    Element asElement();

    /**
     * Returns the type of the innermost enclosing instance or a
     * {@code NoType} of kind {@code NONE} if there is no enclosing
     * instance.  Only types corresponding to inner classes have an
     * enclosing instance.
     *
     * <p>
     *  如果没有封闭实例,则返回最内层实例的类型或{@code NONE}类型的{@code NoType}。只有对应于内部类的类型才有封闭的实例。
     * 
     * 
     * @return a type mirror for the enclosing type
     * @jls 8.1.3 Inner Classes and Enclosing Instances
     * @jls 15.9.2 Determining Enclosing Instances
     */
    TypeMirror getEnclosingType();

    /**
     * Returns the actual type arguments of this type.
     * For a type nested within a parameterized type
     * (such as {@code Outer<String>.Inner<Number>}), only the type
     * arguments of the innermost type are included.
     *
     * <p>
     *  返回此类型的实际类型参数。对于嵌套在参数化类型中的类型(例如{@code Outer <String> .Inner <Number>}),只包括最内层类型的类型参数。
     * 
     * @return the actual type arguments of this type, or an empty list
     *           if none
     */
    List<? extends TypeMirror> getTypeArguments();
}
