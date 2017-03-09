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

package java.lang.invoke;

import java.lang.annotation.*;

/**
 * A field may be annotated as stable if all of its component variables
 * changes value at most once.
 * A field's value counts as its component value.
 * If the field is typed as an array, then all the non-null components
 * of the array, of depth up to the rank of the field's array type,
 * also count as component values.
 * By extension, any variable (either array or field) which has annotated
 * as stable is called a stable variable, and its non-null or non-zero
 * value is called a stable value.
 * <p>
 * Since all fields begin with a default value of null for references
 * (resp., zero for primitives), it follows that this annotation indicates
 * that the first non-null (resp., non-zero) value stored in the field
 * will never be changed.
 * <p>
 * If the field is not of an array type, there are no array elements,
 * then the value indicated as stable is simply the value of the field.
 * If the dynamic type of the field value is an array but the static type
 * is not, the components of the array are <em>not</em> regarded as stable.
 * <p>
 * If the field is an array type, then both the field value and
 * all the components of the field value (if the field value is non-null)
 * are indicated to be stable.
 * If the field type is an array type with rank {@code N &gt; 1},
 * then each component of the field value (if the field value is non-null),
 * is regarded as a stable array of rank {@code N-1}.
 * <p>
 * Fields which are declared {@code final} may also be annotated as stable.
 * Since final fields already behave as stable values, such an annotation
 * indicates no additional information, unless the type of the field is
 * an array type.
 * <p>
 * It is (currently) undefined what happens if a field annotated as stable
 * is given a third value.  In practice, if the JVM relies on this annotation
 * to promote a field reference to a constant, it may be that the Java memory
 * model would appear to be broken, if such a constant (the second value of the field)
 * is used as the value of the field even after the field value has changed.
 * <p>
 *  如果字段的所有组件变量最多只更改一次值,则可以将其注释为stable。字段的值计算为其组件值。如果字段键入数组,则数组的所有非空组件(深度达到字段数组类型的排名)也计为组件值。
 * 通过扩展,已注释为stable的任何变量(数组或字段)称为稳定变量,其非空值或非零值称为稳定值。
 * <p>
 *  由于对于引用,所有字段都以默认值null开始(相应地,对于基元为零),因此该注释指示存储在字段中的第一非空(相应地,非零)值将永远不会改变。
 * <p>
 *  如果字段不是数组类型,则没有数组元素,则表示为stable的值仅仅是字段的值。如果字段值的动态类型是数组,但静态类型不是,则数组的组件不被视为稳定的。
 * <p>
 *  如果字段是数组类型,则字段值和字段值的所有分量(如果字段值为非空)都表示为稳定。
 */
/* package-private */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Stable {
}
