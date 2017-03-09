/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * Represents a union type.
 *
 * As of the {@link javax.lang.model.SourceVersion#RELEASE_7
 * RELEASE_7} source version, union types can appear as the type
 * of a multi-catch exception parameter.
 *
 * <p>
 *  表示联合类型。
 * 
 *  从{@link javax.lang.model.SourceVersion#RELEASE_7 RELEASE_7}源版本开始,联合类型可以显示为multi-catch异常参数的类型。
 * 
 * 
 * @since 1.7
 */
public interface UnionType extends TypeMirror {

    /**
     * Return the alternatives comprising this union type.
     *
     * <p>
     * 
     * @return the alternatives comprising this union type.
     */
    List<? extends TypeMirror> getAlternatives();
}
