/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model;

import java.lang.annotation.*;
import java.util.List;
import javax.lang.model.element.*;
import javax.lang.model.type.*;

/**
 * Represents a construct that can be annotated.
 *
 * A construct is either an {@linkplain
 * javax.lang.model.element.Element element} or a {@linkplain
 * javax.lang.model.type.TypeMirror type}.  Annotations on an element
 * are on a <em>declaration</em>, whereas annotations on a type are on
 * a specific <em>use</em> of a type name.
 *
 * The terms <em>directly present</em>, <em>present</em>,
 * <em>indirectly present</em>, and <em>associated </em> are used
 * throughout this interface to describe precisely which annotations
 * are returned by the methods defined herein.
 *
 * <p>In the definitions below, an annotation <i>A</i> has an
 * annotation type <i>AT</i>. If <i>AT</i> is a repeatable annotation
 * type, the type of the containing annotation is <i>ATC</i>.
 *
 * <p>Annotation <i>A</i> is <em>directly present</em> on a construct
 * <i>C</i> if either:
 *
 * <ul>
 *
 * <li><i>A</i> is explicitly or implicitly declared as applying to
 * the source code representation of <i>C</i>.
 *
 * <p>Typically, if exactly one annotation of type <i>AT</i> appears in
 * the source code of representation of <i>C</i>, then <i>A</i> is
 * explicitly declared as applying to <i>C</i>.
 *
 * If there are multiple annotations of type <i>AT</i> present on
 * <i>C</i>, then if <i>AT</i> is repeatable annotation type, an
 * annotation of type <i>ATC</i> is implicitly declared on <i>C</i>.
 *
 * <li> A representation of <i>A</i> appears in the executable output
 * for <i>C</i>, such as the {@code RuntimeVisibleAnnotations} or
 * {@code RuntimeVisibleParameterAnnotations} attributes of a class
 * file.
 *
 * </ul>
 *
 * <p>An annotation <i>A</i> is <em>present</em> on a
 * construct <i>C</i> if either:
 * <ul>
 *
 * <li><i>A</i> is directly present on <i>C</i>.
 *
 * <li>No annotation of type <i>AT</i> is directly present on
 * <i>C</i>, and <i>C</i> is a class and <i>AT</i> is inheritable
 * and <i>A</i> is present on the superclass of <i>C</i>.
 *
 * </ul>
 *
 * An annotation <i>A</i> is <em>indirectly present</em> on a construct
 * <i>C</i> if both:
 *
 * <ul>
 *
 * <li><i>AT</i> is a repeatable annotation type with a containing
 * annotation type <i>ATC</i>.
 *
 * <li>An annotation of type <i>ATC</i> is directly present on
 * <i>C</i> and <i>A</i> is an annotation included in the result of
 * calling the {@code value} method of the directly present annotation
 * of type <i>ATC</i>.
 *
 * </ul>
 *
 * An annotation <i>A</i> is <em>associated</em> with a construct
 * <i>C</i> if either:
 *
 * <ul>
 *
 * <li> <i>A</i> is directly or indirectly present on <i>C</i>.
 *
 * <li> No annotation of type <i>AT</i> is directly or indirectly
 * present on <i>C</i>, and <i>C</i> is a class, and <i>AT</i> is
 * inheritable, and <i>A</i> is associated with the superclass of
 * <i>C</i>.
 *
 * </ul>
 *
 * <p>
 *  表示可以注释的构造。
 * 
 *  结构是{@linkplain javax.lang.model.element.Element element}或{@linkplain javax.lang.model.type.TypeMirror type}
 * 。
 * 元素上的注释位于<em>声明</em>上,而类型上的注释则位于特定<em>使用</em>类型名称。
 * 
 *  在整个界面中使用直接呈现的<em> </em>,<em>呈现</em>,<em>间接呈现</em>和<em>相关</em>通过本文定义的方法返回。
 * 
 *  <p>在下面的定义中,注释<i> A </i>具有注释类型<i> AT </i>。如果<i> AT </i>是可重复注释类型,则包含注释的类型是ATC </i>。
 * 
 *  <p>注释<i> </em>直接出现在构造<i> C </i>上,如果：
 * 
 * <ul>
 * 
 *  <li> </i>明确或隐含地声明为适用于<i> C </i>的源代码表示。
 * 
 *  <p>通常,如果在<C> </i>的表示源代码中只出现一个类型<i> AT </i>的注释,则</i>到℃。
 * 
 *  如果在&lt; i&gt;上存在&lt; i&gt;类型的多个注释,则如果AT i是可重复注释类型,则类型&lt; i&gt; / i>隐含地在<i> C </i>上声明。
 * 
 * <li> <i> A </i>的表示形式出现在<i> C </i>的可执行输出中,例如类文件的{@code RuntimeVisibleAnnotations}或{@code RuntimeVisibleParameterAnnotations}
 * 属性。
 * 
 * </ul>
 * 
 *  <p>如果以下情况之一,则在构造<i> C </i>上出现<i> </i>注释</i>
 * <ul>
 * 
 *  <li> <i> A </i>直接出现在<i> C </i>上。
 * 
 *  <li>没有类型<i> AT </i>的注释直接显示在<C> </i>上,<C> </i>是类别,<AT>可继承且<i> A </i>存在于<C> </i>的超类。
 * 
 * </ul>
 * 
 *  如果以下两种情况下,注释<i> </i> <em> </em>间接存在于构造</i>
 * 
 * <ul>
 * 
 *  <li> <i> AT </i>是含有注释类型<i> ATC </i>的可重复注记类型。
 * 
 *  <li>类型<i> ATC </i>的注释直接出现在<i> C </i>和<i> A </i>是包含在调用{@code value }方法直接呈现类型<i> ATC </i>的注释。
 * 
 * </ul>
 * 
 * 
 * @since 1.8
 * @jls 9.6 Annotation Types
 * @jls 9.6.3.3 @Inherited
 */
public interface AnnotatedConstruct {
    /**
     * Returns the annotations that are <em>directly present</em> on
     * this construct.
     *
     * <p>
     *  如果以下情况之一,则注释<i> </i>与<i> </i>结构</i> </
     * 
     * <ul>
     * 
     *  <li> <i> A </i>直接或间接出现在<i> C </i>上。
     * 
     *  <li>没有类型<i> AT </i>的注释直接或间接出现在<C> </C>上,<C>是类别,<AT> i>是可继承的,并且<i> </i>与</i>的超类相关联。
     * 
     * </ul>
     * 
     * 
     * @return the annotations <em>directly present</em> on this
     * construct; an empty list if there are none
     */
    List<? extends AnnotationMirror> getAnnotationMirrors();

    /**
     * Returns this construct's annotation of the specified type if
     * such an annotation is <em>present</em>, else {@code null}.
     *
     * <p> The annotation returned by this method could contain an element
     * whose value is of type {@code Class}.
     * This value cannot be returned directly:  information necessary to
     * locate and load a class (such as the class loader to use) is
     * not available, and the class might not be loadable at all.
     * Attempting to read a {@code Class} object by invoking the relevant
     * method on the returned annotation
     * will result in a {@link MirroredTypeException},
     * from which the corresponding {@link TypeMirror} may be extracted.
     * Similarly, attempting to read a {@code Class[]}-valued element
     * will result in a {@link MirroredTypesException}.
     *
     * <blockquote>
     * <i>Note:</i> This method is unlike others in this and related
     * interfaces.  It operates on runtime reflective information &mdash;
     * representations of annotation types currently loaded into the
     * VM &mdash; rather than on the representations defined by and used
     * throughout these interfaces.  Consequently, calling methods on
     * the returned annotation object can throw many of the exceptions
     * that can be thrown when calling methods on an annotation object
     * returned by core reflection.  This method is intended for
     * callers that are written to operate on a known, fixed set of
     * annotation types.
     * </blockquote>
     *
     * <p>
     *  返回此构造上<em>直接呈现的注释</em>。
     * 
     * 
     * @param <A>  the annotation type
     * @param annotationType  the {@code Class} object corresponding to
     *          the annotation type
     * @return this construct's annotation for the specified
     * annotation type if present, else {@code null}
     *
     * @see #getAnnotationMirrors()
     * @see java.lang.reflect.AnnotatedElement#getAnnotation
     * @see EnumConstantNotPresentException
     * @see AnnotationTypeMismatchException
     * @see IncompleteAnnotationException
     * @see MirroredTypeException
     * @see MirroredTypesException
     * @jls 9.6.1 Annotation Type Elements
     */
    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    /**
     * Returns annotations that are <em>associated</em> with this construct.
     *
     * If there are no annotations associated with this construct, the
     * return value is an array of length 0.
     *
     * The order of annotations which are directly or indirectly
     * present on a construct <i>C</i> is computed as if indirectly present
     * annotations on <i>C</i> are directly present on <i>C</i> in place of their
     * container annotation, in the order in which they appear in the
     * value element of the container annotation.
     *
     * The difference between this method and {@link #getAnnotation(Class)}
     * is that this method detects if its argument is a <em>repeatable
     * annotation type</em>, and if so, attempts to find one or more
     * annotations of that type by "looking through" a container annotation.
     *
     * <p> The annotations returned by this method could contain an element
     * whose value is of type {@code Class}.
     * This value cannot be returned directly:  information necessary to
     * locate and load a class (such as the class loader to use) is
     * not available, and the class might not be loadable at all.
     * Attempting to read a {@code Class} object by invoking the relevant
     * method on the returned annotation
     * will result in a {@link MirroredTypeException},
     * from which the corresponding {@link TypeMirror} may be extracted.
     * Similarly, attempting to read a {@code Class[]}-valued element
     * will result in a {@link MirroredTypesException}.
     *
     * <blockquote>
     * <i>Note:</i> This method is unlike others in this and related
     * interfaces.  It operates on runtime reflective information &mdash;
     * representations of annotation types currently loaded into the
     * VM &mdash; rather than on the representations defined by and used
     * throughout these interfaces.  Consequently, calling methods on
     * the returned annotation object can throw many of the exceptions
     * that can be thrown when calling methods on an annotation object
     * returned by core reflection.  This method is intended for
     * callers that are written to operate on a known, fixed set of
     * annotation types.
     * </blockquote>
     *
     * <p>
     *  如果此类注释是<em>存在</em>,则返回此构造的指定类型的注释,否则返回{@code null}。
     * 
     * <p>此方法返回的注释可能包含值为{@code Class}类型的元素。此值不能直接返回：定位和加载类(例如要使用的类加载器)所需的信息不可用,并且该类可能根本不可加载。
     * 尝试通过在返回的注释上调用相关方法来读取{@code Class}对象将导致{@link MirroredTypeException},从中可以提取相应的{@link TypeMirror}。
     * 同样,尝试读取{@code Class []}值元素将导致{@link MirroredTypesException}。
     * 
     * <blockquote>
     *  <i>注意：</i>此方法与此界面和相关界面中的其他方法不同。它运行在运行时反射信息当前加载到VM中的注释类型的表示&mdash;而不是在由这些接口定义和使用的表示上。
     * 因此,对于返回的注记对象调用方法可能会抛出许多在核心反射返回的注解对象上调用方法时抛出的异常。此方法适用于编写为对已知的固定注释类型进行操作的调用方。
     * </blockquote>
     * 
     * 
     * @param <A>  the annotation type
     * @param annotationType  the {@code Class} object corresponding to
     *          the annotation type
     * @return this construct's annotations for the specified annotation
     *         type if present on this construct, else an empty array
     *
     * @see #getAnnotationMirrors()
     * @see #getAnnotation(Class)
     * @see java.lang.reflect.AnnotatedElement#getAnnotationsByType(Class)
     * @see EnumConstantNotPresentException
     * @see AnnotationTypeMismatchException
     * @see IncompleteAnnotationException
     * @see MirroredTypeException
     * @see MirroredTypesException
     * @jls 9.6 Annotation Types
     * @jls 9.6.1 Annotation Type Elements
     */
    <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType);
}
