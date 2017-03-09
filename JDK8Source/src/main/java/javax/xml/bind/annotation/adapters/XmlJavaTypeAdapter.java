/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.annotation.adapters;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaTypes;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.PACKAGE;


/**
 * Use an adapter that implements {@link XmlAdapter} for custom marshaling.
 *
 * <p> <b> Usage: </b> </p>
 *
 * <p> The <tt>@XmlJavaTypeAdapter</tt> annotation can be used with the
 * following program elements:
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> field </li>
 *   <li> parameter </li>
 *   <li> package </li>
 *   <li> from within {@link XmlJavaTypeAdapters} </li>
 * </ul>
 *
 * <p> When <tt>@XmlJavaTypeAdapter</tt> annotation is defined on a
 * class, it applies to all references to the class.
 * <p> When <tt>@XmlJavaTypeAdapter</tt> annotation is defined at the
 * package level it applies to all references from within the package
 * to <tt>@XmlJavaTypeAdapter.type()</tt>.
 * <p> When <tt>@XmlJavaTypeAdapter</tt> annotation is defined on the
 * field, property or parameter, then the annotation applies to the
 * field, property or the parameter only.
 * <p> A <tt>@XmlJavaTypeAdapter</tt> annotation on a field, property
 * or parameter overrides the <tt>@XmlJavaTypeAdapter</tt> annotation
 * associated with the class being referenced by the field, property
 * or parameter.
 * <p> A <tt>@XmlJavaTypeAdapter</tt> annotation on a class overrides
 * the <tt>@XmlJavaTypeAdapter</tt> annotation specified at the
 * package level for that class.
 *
 * <p>This annotation can be used with the following other annotations:
 * {@link XmlElement}, {@link XmlAttribute}, {@link XmlElementRef},
 * {@link XmlElementRefs}, {@link XmlAnyElement}. This can also be
 * used at the package level with the following annotations:
 * {@link XmlAccessorType}, {@link XmlSchema}, {@link XmlSchemaType},
 * {@link XmlSchemaTypes}.
 *
 * <p><b> Example: </b> See example in {@link XmlAdapter}
 *
 * <p>
 *  使用实现{@link XmlAdapter}的适配器进行自定义封送。
 * 
 *  <p> <b>用法：</b> </p>
 * 
 *  <p> <tt> @XmlJavaTypeAdapter </tt>注释可与以下程序元素一起使用：
 * <ul>
 *  <li> {@link XmlJavaTypeAdapters}中的</li> </li>字符串</li> <li>字段</li> <li>参数</li>
 * </ul>
 * 
 *  <p>当<tt> @XmlJavaTypeAdapter </tt>注释在类上定义时,它将应用于对类的所有引用。
 *  <p>当在包级别定义<tt> @XmlJavaTypeAdapter </tt>注释时,它适用于从包内到<tt> @ XmlJavaTypeAdapter.type()</tt>的所有引用。
 *  <p>当对字段,属性或参数定义<tt> @XmlJavaTypeAdapter </tt>注释时,注释仅应用于字段,属性或参数。
 *  <p>字段,属性或参数上的<tt> @XmlJavaTypeAdapter </tt>注释覆盖与字段,属性或参数引用的类关联的<tt> @XmlJavaTypeAdapter </tt>注释。
 *  <p>类的<tt> @XmlJavaTypeAdapter </tt>注释会覆盖在该类的包级别指定的<tt> @XmlJavaTypeAdapter </tt>注释。
 * 
 * <p>此注释可与以下其他注释一起使用：{@link XmlElement},{@link XmlAttribute},{@link XmlElementRef},{@link XmlElementRefs}
 * 
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems Inc.</li> <li> Kohsuke Kawaguchi, Sun Microsystems Inc.</li></ul>
 * @since JAXB2.0
 * @see XmlAdapter
 */

@Retention(RUNTIME) @Target({PACKAGE,FIELD,METHOD,TYPE,PARAMETER})
public @interface XmlJavaTypeAdapter {
    /**
     * Points to the class that converts a value type to a bound type or vice versa.
     * See {@link XmlAdapter} for more details.
     * <p>
     * ,{@link XmlAnyElement}。
     * 这也可以在包级别使用以下注释：{@link XmlAccessorType},{@link XmlSchema},{@link XmlSchemaType},{@link XmlSchemaTypes}
     * 。
     * 
     *  <p> <b>示例：</b>请参见{@link XmlAdapter}
     * 
     */
    Class<? extends XmlAdapter> value();

    /**
     * If this annotation is used at the package level, then value of
     * the type() must be specified.
     * <p>
     *  指向将值类型转换为绑定类型的类,反之亦然。有关详细信息,请参阅{@link XmlAdapter}。
     * 
     */

    Class type() default DEFAULT.class;

    /**
     * Used in {@link XmlJavaTypeAdapter#type()} to
     * signal that the type be inferred from the signature
     * of the field, property, parameter or the class.
     * <p>
     *  如果在包级别使用此注释,则必须指定type()的值。
     * 
     */

    static final class DEFAULT {}

}
