/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that an attribute called "transient"
 * should be declared with the given {@code value}
 * when the {@link Introspector} constructs
 * a {@link PropertyDescriptor} or {@link EventSetDescriptor}
 * classes associated with the annotated code element.
 * A {@code true} value for the "transient" attribute
 * indicates to encoders derived from {@link Encoder}
 * that this feature should be ignored.
 * <p>
 * The {@code Transient} annotation may be be used
 * in any of the methods that are involved
 * in a {@link FeatureDescriptor} subclass
 * to identify the transient feature in the annotated class and its subclasses.
 * Normally, the method that starts with "get" is the best place
 * to put the annotation and it is this declaration
 * that takes precedence in the case of multiple annotations
 * being defined for the same feature.
 * <p>
 * To declare a feature non-transient in a class
 * whose superclass declares it transient,
 * use {@code @Transient(false)}.
 * In all cases, the {@link Introspector} decides
 * if a feature is transient by referring to the annotation
 * on the most specific superclass.
 * If no {@code Transient} annotation is present
 * in any superclass the feature is not transient.
 *
 * <p>
 *  指示当{@link Introspector}构造与带注释的代码元素相关联的{@link PropertyDescriptor}或{@link EventSetDescriptor}类时,应使用给定的
 * {@code value}来声明名为"transient"的属性。
 *  "transient"属性的{@code true}值表示从{@link Encoder}派生的编码器应忽略此功能。
 * <p>
 *  {@code Transient}注解可以用于{@link FeatureDescriptor}子类中涉及的任何方法中,以识别注释类及其子类中的瞬态特征。
 * 通常,以"get"开头的方法是放置注释的最佳位置,并且在为相同特征定义多个注释的情况下,该声明优先。
 * <p>
 * 
 * @since 1.7
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface Transient {
    /**
     * Returns whether or not the {@code Introspector} should
     * construct artifacts for the annotated method.
     * <p>
     *  要在其超类声明为transient的类中声明非瞬态特征,请使用{@code @Transient(false)}。
     * 在所有情况下,{@link Introspector}通过引用最特定超类上的注释来决定要素是否是暂时的。如果在任何超类中不存在{@code Transient}注释,则该特征不是暂时的。
     * 
     * 
     * @return whether or not the {@code Introspector} should
     * construct artifacts for the annotated method
     */
    boolean value() default true;
}
