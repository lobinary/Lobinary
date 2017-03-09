/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model.element;

import java.util.List;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

/**
 * Represents a formal type parameter of a generic class, interface, method,
 * or constructor element.
 * A type parameter declares a {@link TypeVariable}.
 *
 * <p>
 *  表示通用类,接口,方法或构造函数元素的形式类型参数。类型参数声明一个{@link TypeVariable}。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see TypeVariable
 * @since 1.6
 */
public interface TypeParameterElement extends Element {

    /**
     * Returns the generic class, interface, method, or constructor that is
     * parameterized by this type parameter.
     *
     * <p>
     *  返回由此类型参数参数化的通用类,接口,方法或构造函数。
     * 
     * 
     * @return the generic class, interface, method, or constructor that is
     * parameterized by this type parameter
     */
    Element getGenericElement();

    /**
     * Returns the bounds of this type parameter.
     * These are the types given by the {@code extends} clause
     * used to declare this type parameter.
     * If no explicit {@code extends} clause was used,
     * then {@code java.lang.Object} is considered to be the sole bound.
     *
     * <p>
     *  返回此类型参数的边界。这些是由用于声明此类型参数的{@code extends}子句给出的类型。
     * 如果没有使用明确的{@code extends}子句,则{@code java.lang.Object}被认为是唯一的边界。
     * 
     * 
     * @return the bounds of this type parameter, or an empty list if
     * there are none
     */
    List<? extends TypeMirror> getBounds();

    /**
     * Returns the {@linkplain TypeParameterElement#getGenericElement generic element} of this type parameter.
     *
     * <p>
     * 
     * @return the generic element of this type parameter
     */
    @Override
    Element getEnclosingElement();
}
