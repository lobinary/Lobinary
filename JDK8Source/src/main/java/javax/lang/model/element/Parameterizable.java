/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A mixin interface for an element that has type parameters.
 *
 * <p>
 *  具有类型参数的元素的mixin接口。
 * 
 * 
 * @author Joseph D. Darcy
 * @since 1.7
 */
public interface Parameterizable extends Element {
    /**
     * Returns the formal type parameters of the type element in
     * declaration order.
     *
     * <p>
     *  以声明顺序返回类型元素的形式类型参数。
     * 
     * @return the formal type parameters, or an empty list
     * if there are none
     */
    List<? extends TypeParameterElement> getTypeParameters();
}
