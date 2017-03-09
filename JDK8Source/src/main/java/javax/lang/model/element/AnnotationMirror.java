/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Map;
import javax.lang.model.type.DeclaredType;

/**
 * Represents an annotation.  An annotation associates a value with
 * each element of an annotation type.
 *
 * <p> Annotations should be compared using the {@code equals}
 * method.  There is no guarantee that any particular annotation will
 * always be represented by the same object.
 *
 * <p>
 *  表示注释。注释将值与注释类型的每个元素相关联。
 * 
 *  <p>注解应使用{@code equals}方法进行比较。不能保证任何特定的注释总是由相同的对象表示。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface AnnotationMirror {

    /**
     * Returns the type of this annotation.
     *
     * <p>
     *  返回此注释的类型。
     * 
     * 
     * @return the type of this annotation
     */
    DeclaredType getAnnotationType();

    /**
     * Returns the values of this annotation's elements.
     * This is returned in the form of a map that associates elements
     * with their corresponding values.
     * Only those elements with values explicitly present in the
     * annotation are included, not those that are implicitly assuming
     * their default values.
     * The order of the map matches the order in which the
     * values appear in the annotation's source.
     *
     * <p>Note that an annotation mirror of a marker annotation type
     * will by definition have an empty map.
     *
     * <p>To fill in default values, use {@link
     * javax.lang.model.util.Elements#getElementValuesWithDefaults
     * getElementValuesWithDefaults}.
     *
     * <p>
     *  返回此注释元素的值。这是以将元素与其对应的值相关联的映射的形式返回的。只包括那些在注释中显式存在的值的元素,而不是隐含地假定其默认值的那些元素。地图的顺序与值在注释来源中显示的顺序相匹配。
     * 
     *  <p>请注意,标记注释类型的注释镜像将根据定义具有空映射。
     * 
     * 
     * @return the values of this annotation's elements,
     *          or an empty map if there are none
     */
    Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValues();
}
