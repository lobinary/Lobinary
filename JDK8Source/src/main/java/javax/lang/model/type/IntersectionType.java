/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Represents an intersection type.
 *
 * <p>An intersection type can be either implicitly or explicitly
 * declared in a program. For example, the bound of the type parameter
 * {@code <T extends Number & Runnable>} is an (implicit) intersection
 * type.  As of {@link javax.lang.model.SourceVersion#RELEASE_8
 * RELEASE_8}, this is represented by an {@code IntersectionType} with
 * {@code Number} and {@code Runnable} as its bounds.
 *
 * @implNote Also as of {@link
 * javax.lang.model.SourceVersion#RELEASE_8 RELEASE_8}, in the
 * reference implementation an {@code IntersectionType} is used to
 * model the explicit target type of a cast expression.
 *
 * <p>
 *  表示交叉路口类型。
 * 
 *  <p>交集类型可以在程式中隐式或显式声明。例如,类型参数{@code <T extends Number&Runnable>}的边界是一个(隐式)交集类型。
 * 从{@link javax.lang.model.SourceVersion#RELEASE_8 RELEASE_8}开始,这由{@code IntersectionType}表示为{@code Number}
 * 和{@code Runnable}作为其边界。
 *  <p>交集类型可以在程式中隐式或显式声明。例如,类型参数{@code <T extends Number&Runnable>}的边界是一个(隐式)交集类型。
 * 
 * 
 * @since 1.8
 */
public interface IntersectionType extends TypeMirror {

    /**
     * Return the bounds comprising this intersection type.
     *
     * <p>
     *  @implNote从{@link javax.lang.model.SourceVersion#RELEASE_8 RELEASE_8}开始,在参考实现中,{@code IntersectionType}
     * 用于对转换表达式的显式目标类型建模。
     * 
     * 
     * @return the bounds of this intersection types.
     */
    List<? extends TypeMirror> getBounds();
}
