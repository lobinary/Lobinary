/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.reflect;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.Repeatable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import sun.reflect.annotation.AnnotationSupport;
import sun.reflect.annotation.AnnotationType;

/**
 * Represents an annotated element of the program currently running in this
 * VM.  This interface allows annotations to be read reflectively.  All
 * annotations returned by methods in this interface are immutable and
 * serializable. The arrays returned by methods of this interface may be modified
 * by callers without affecting the arrays returned to other callers.
 *
 * <p>The {@link #getAnnotationsByType(Class)} and {@link
 * #getDeclaredAnnotationsByType(Class)} methods support multiple
 * annotations of the same type on an element. If the argument to
 * either method is a repeatable annotation type (JLS 9.6), then the
 * method will "look through" a container annotation (JLS 9.7), if
 * present, and return any annotations inside the container. Container
 * annotations may be generated at compile-time to wrap multiple
 * annotations of the argument type.
 *
 * <p>The terms <em>directly present</em>, <em>indirectly present</em>,
 * <em>present</em>, and <em>associated</em> are used throughout this
 * interface to describe precisely which annotations are returned by
 * methods:
 *
 * <ul>
 *
 * <li> An annotation <i>A</i> is <em>directly present</em> on an
 * element <i>E</i> if <i>E</i> has a {@code
 * RuntimeVisibleAnnotations} or {@code
 * RuntimeVisibleParameterAnnotations} or {@code
 * RuntimeVisibleTypeAnnotations} attribute, and the attribute
 * contains <i>A</i>.
 *
 * <li>An annotation <i>A</i> is <em>indirectly present</em> on an
 * element <i>E</i> if <i>E</i> has a {@code RuntimeVisibleAnnotations} or
 * {@code RuntimeVisibleParameterAnnotations} or {@code RuntimeVisibleTypeAnnotations}
 * attribute, and <i>A</i> 's type is repeatable, and the attribute contains
 * exactly one annotation whose value element contains <i>A</i> and whose
 * type is the containing annotation type of <i>A</i> 's type.
 *
 * <li>An annotation <i>A</i> is present on an element <i>E</i> if either:
 *
 * <ul>
 *
 * <li><i>A</i> is directly present on <i>E</i>; or
 *
 * <li>No annotation of <i>A</i> 's type is directly present on
 * <i>E</i>, and <i>E</i> is a class, and <i>A</i> 's type is
 * inheritable, and <i>A</i> is present on the superclass of <i>E</i>.
 *
 * </ul>
 *
 * <li>An annotation <i>A</i> is <em>associated</em> with an element <i>E</i>
 * if either:
 *
 * <ul>
 *
 * <li><i>A</i> is directly or indirectly present on <i>E</i>; or
 *
 * <li>No annotation of <i>A</i> 's type is directly or indirectly
 * present on <i>E</i>, and <i>E</i> is a class, and <i>A</i>'s type
 * is inheritable, and <i>A</i> is associated with the superclass of
 * <i>E</i>.
 *
 * </ul>
 *
 * </ul>
 *
 * <p>The table below summarizes which kind of annotation presence
 * different methods in this interface examine.
 *
 * <table border>
 * <caption>Overview of kind of presence detected by different AnnotatedElement methods</caption>
 * <tr><th colspan=2></th><th colspan=4>Kind of Presence</th>
 * <tr><th colspan=2>Method</th><th>Directly Present</th><th>Indirectly Present</th><th>Present</th><th>Associated</th>
 * <tr><td align=right>{@code T}</td><td>{@link #getAnnotation(Class) getAnnotation(Class&lt;T&gt;)}
 * <td></td><td></td><td>X</td><td></td>
 * </tr>
 * <tr><td align=right>{@code Annotation[]}</td><td>{@link #getAnnotations getAnnotations()}
 * <td></td><td></td><td>X</td><td></td>
 * </tr>
 * <tr><td align=right>{@code T[]}</td><td>{@link #getAnnotationsByType(Class) getAnnotationsByType(Class&lt;T&gt;)}
 * <td></td><td></td><td></td><td>X</td>
 * </tr>
 * <tr><td align=right>{@code T}</td><td>{@link #getDeclaredAnnotation(Class) getDeclaredAnnotation(Class&lt;T&gt;)}
 * <td>X</td><td></td><td></td><td></td>
 * </tr>
 * <tr><td align=right>{@code Annotation[]}</td><td>{@link #getDeclaredAnnotations getDeclaredAnnotations()}
 * <td>X</td><td></td><td></td><td></td>
 * </tr>
 * <tr><td align=right>{@code T[]}</td><td>{@link #getDeclaredAnnotationsByType(Class) getDeclaredAnnotationsByType(Class&lt;T&gt;)}
 * <td>X</td><td>X</td><td></td><td></td>
 * </tr>
 * </table>
 *
 * <p>For an invocation of {@code get[Declared]AnnotationsByType( Class <
 * T >)}, the order of annotations which are directly or indirectly
 * present on an element <i>E</i> is computed as if indirectly present
 * annotations on <i>E</i> are directly present on <i>E</i> in place
 * of their container annotation, in the order in which they appear in
 * the value element of the container annotation.
 *
 * <p>There are several compatibility concerns to keep in mind if an
 * annotation type <i>T</i> is originally <em>not</em> repeatable and
 * later modified to be repeatable.
 *
 * The containing annotation type for <i>T</i> is <i>TC</i>.
 *
 * <ul>
 *
 * <li>Modifying <i>T</i> to be repeatable is source and binary
 * compatible with existing uses of <i>T</i> and with existing uses
 * of <i>TC</i>.
 *
 * That is, for source compatibility, source code with annotations of
 * type <i>T</i> or of type <i>TC</i> will still compile. For binary
 * compatibility, class files with annotations of type <i>T</i> or of
 * type <i>TC</i> (or with other kinds of uses of type <i>T</i> or of
 * type <i>TC</i>) will link against the modified version of <i>T</i>
 * if they linked against the earlier version.
 *
 * (An annotation type <i>TC</i> may informally serve as an acting
 * containing annotation type before <i>T</i> is modified to be
 * formally repeatable. Alternatively, when <i>T</i> is made
 * repeatable, <i>TC</i> can be introduced as a new type.)
 *
 * <li>If an annotation type <i>TC</i> is present on an element, and
 * <i>T</i> is modified to be repeatable with <i>TC</i> as its
 * containing annotation type then:
 *
 * <ul>
 *
 * <li>The change to <i>T</i> is behaviorally compatible with respect
 * to the {@code get[Declared]Annotation(Class<T>)} (called with an
 * argument of <i>T</i> or <i>TC</i>) and {@code
 * get[Declared]Annotations()} methods because the results of the
 * methods will not change due to <i>TC</i> becoming the containing
 * annotation type for <i>T</i>.
 *
 * <li>The change to <i>T</i> changes the results of the {@code
 * get[Declared]AnnotationsByType(Class<T>)} methods called with an
 * argument of <i>T</i>, because those methods will now recognize an
 * annotation of type <i>TC</i> as a container annotation for <i>T</i>
 * and will "look through" it to expose annotations of type <i>T</i>.
 *
 * </ul>
 *
 * <li>If an annotation of type <i>T</i> is present on an
 * element and <i>T</i> is made repeatable and more annotations of
 * type <i>T</i> are added to the element:
 *
 * <ul>
 *
 * <li> The addition of the annotations of type <i>T</i> is both
 * source compatible and binary compatible.
 *
 * <li>The addition of the annotations of type <i>T</i> changes the results
 * of the {@code get[Declared]Annotation(Class<T>)} methods and {@code
 * get[Declared]Annotations()} methods, because those methods will now
 * only see a container annotation on the element and not see an
 * annotation of type <i>T</i>.
 *
 * <li>The addition of the annotations of type <i>T</i> changes the
 * results of the {@code get[Declared]AnnotationsByType(Class<T>)}
 * methods, because their results will expose the additional
 * annotations of type <i>T</i> whereas previously they exposed only a
 * single annotation of type <i>T</i>.
 *
 * </ul>
 *
 * </ul>
 *
 * <p>If an annotation returned by a method in this interface contains
 * (directly or indirectly) a {@link Class}-valued member referring to
 * a class that is not accessible in this VM, attempting to read the class
 * by calling the relevant Class-returning method on the returned annotation
 * will result in a {@link TypeNotPresentException}.
 *
 * <p>Similarly, attempting to read an enum-valued member will result in
 * a {@link EnumConstantNotPresentException} if the enum constant in the
 * annotation is no longer present in the enum type.
 *
 * <p>If an annotation type <i>T</i> is (meta-)annotated with an
 * {@code @Repeatable} annotation whose value element indicates a type
 * <i>TC</i>, but <i>TC</i> does not declare a {@code value()} method
 * with a return type of <i>T</i>{@code []}, then an exception of type
 * {@link java.lang.annotation.AnnotationFormatError} is thrown.
 *
 * <p>Finally, attempting to read a member whose definition has evolved
 * incompatibly will result in a {@link
 * java.lang.annotation.AnnotationTypeMismatchException} or an
 * {@link java.lang.annotation.IncompleteAnnotationException}.
 *
 * <p>
 *  表示当前在此VM中运行的程序的注释元素。此接口允许反射读取注释。此接口中的方法返回的所有注释都是不可变的和可序列化的。由该接口的方法返回的数组可以被调用者修改而不影响返回给其他调用者的数组。
 * 
 *  <p> {@link #getAnnotationsByType(Class)}和{@link #getDeclaredAnnotationsByType(Class)}方法支持元素上同一类型的多个注
 * 释。
 * 如果任一方法的参数是可重复的注释类型(JLS 9.6),那么该方法将"遍历"容器注解(JLS 9.7)(如果存在),并返回容器内的任何注释。容器注释可以在编译时生成以包装参数类型的多个注释。
 * 
 *  <p> </em>直接出现</em>,间接出现的</em>,<em>现在</em>和<em>正是哪些注释通过方法返回：
 * 
 * <ul>
 * 
 *  <li>如果<i> E </i>有{@code RuntimeVisibleAnnotations},则注释<i> </i>会直接出现在元素<i>或{@code RuntimeVisibleParameterAnnotations}
 * 或{@code RuntimeVisibleTypeAnnotations}属性,并且属性包含<i> A </i>。
 * 
 * <li>如果<i> E </i>有{@code RuntimeVisibleAnnotations},则<i> </i>在元素<i>或{@code RuntimeVisibleParameterAnnotations}
 * 或{@code RuntimeVisibleTypeAnnotations}属性,并且</i>的类型是可重复的,并且该属性仅包含其值元素包含<i> A </i> type是<i> A </i>类型的注释
 * 类型。
 * 
 *  <li>注释<i> A </i>出现在元素<i> E </i>上：
 * 
 * <ul>
 * 
 *  <li> <i> </i>直接出现在<i> E </i>;要么
 * 
 *  <li>没有对<i>类型的注释直接出现在<i> E </i>上,<i> </i>是类别,<A> i类型是可继承的,并且<i> </i>存在于<i> </i>的超类上。
 * 
 * </ul>
 * 
 *  <li>如果以下情况之一,注释<i> </i>与元素<i> E </i>相关联</em>
 * 
 * <ul>
 * 
 *  <li> <i> A </i>直接或间接出现在<i> E </i>上;要么
 * 
 *  <li> <i>的类型的注释不直接或间接出现在<i> E </i>上,</i>是类别,<A> </i>的类型是可继承的,<i> </i>与<i> E </i>的超类相关联。
 * 
 * </ul>
 * 
 * </ul>
 * 
 *  <p>下表总结了此界面中检查的注释的不同方法。
 * 
 * <table border>
 * <caption>不同AnnotatedElement方法检测到的存在类型概述</caption> <tr> <th colspan = 2> </th> <th colspan = 4>存在类型</th>
 *  <tr> <th colspan = 2>方法</th> <th>直接呈现</th> <th>间接呈现</th> <th>当前</th> <th>相关</th> <tr> <td align = {@code T} </td>
 *  <td> {@ link #getAnnotation(Class)getAnnotation(Class&lt; T&gt;)} <td> </td> <td> </td> <td> X </td>
 *  <td> </td>。
 * </tr>
 *  <tr> <td align = right> {@ code Annotation []} </td> <td> {@ link #getAnnotations getAnnotations()} 
 * <td> </td> <td> </td> </td> <td> </td>。
 * </tr>
 *  <tr> <td align = right> {@ code T []} </td> <td> {@ link #getAnnotationsByType(Class)getAnnotationsByType(Class&lt; T&gt;)}
 *  <td> </td> <td> / td> <td> </td> <td> X </td>。
 * </tr>
 *  <tr> <td align = right> {@ code T} </td> <td> {@ link #getDeclaredAnnotation(Class)getDeclaredAnnotation(Class&lt; T&gt;)}
 *  <td> X </td> <td> td> <td> </td> <td> </td>。
 * </tr>
 *  <tr> <td align = right> {@ code Annotation []} </td> <td> {@ link #getDeclaredAnnotations getDeclaredAnnotations()}
 *  <td> X </td> <td> </td> <td> </td> <td> </td>。
 * </tr>
 *  <tr> <td align = right> {@ code T []} </td> <td> {@ link #getDeclaredAnnotationsByType(Class)getDeclaredAnnotationsByType(Class&lt; T&gt;)}
 *  <td> X </td> <td> X </td> <td> </td> <td> </td>。
 * </tr>
 * </table>
 * 
 * <p>对于{@code get [Declared] AnnotationsByType(Class <T>)}的调用,直接或间接出现在元素E上的注释的顺序被计算为如同间接存在</i>上的注释以它们在容
 * 器注释的值元素中出现的顺序直接出现在</i>代替它们的容器注释。
 * 
 *  <p>如果注释类型<i> T </i>最初不是</em>可重复,后来修改为可重复,则需要记住几个兼容性问题。
 * 
 *  包含<i> T </i>的注释类型是<TC> </i>。
 * 
 * <ul>
 * 
 *  <li>修改<i> T </i>为可重复的来源和二进制兼容<i> T </i>的现有用途和<i> TC </i>的现有用途。
 * 
 *  也就是说,为了源兼容性,具有类型<i> T </i>或</i>类型的注释的源代码仍然将被编译。
 * 对于二进制兼容性,具有类型<i> T </i>或类型<i> TC </i>的注释的类文件(或类型<i> T </i> i> TC </i>)将链接到<i> T </i>的修改版本,如果它们链接到较早版本
 * 。
 *  也就是说,为了源兼容性,具有类型<i> T </i>或</i>类型的注释的源代码仍然将被编译。
 * 
 *  (注释类型TC可以在<T> T被修改为正式可重复之前非正式地充当包含注释类型的动作。或者,当<T> <T>时可重复的,<TC>可以作为新类型引入。)
 * 
 * <li>如果注释类型<i> TC </i>存在于元素上,且<i> T </i>被修改为可以使用<TC> </i>作为其包含的注释类型：
 * 
 * <ul>
 * 
 *  <li>对<@> T </i>的改变在行为上与{@code get [Declared] Annotation(Class <T>)}的行为兼容(使用<i> T </i>或<@ TC </i>)和{@code get [Declared] Annotations()}
 * 方法,因为方法的结果不会因<i> TC成为<i的包含注释类型而改变> T </i>。
 * 
 *  <li>更改为<i> T </i>会更改以参数<i> T </i>调用的{@code get [Declared] AnnotationsByType(Class <T>)}方法的结果,因为这些方法现
 * 在将识别类型TC的注释作为</i>的容器注释,并将"浏览"它以暴露类型</i>的注释, 。
 * 
 * </ul>
 * 
 * 
 * @see java.lang.EnumConstantNotPresentException
 * @see java.lang.TypeNotPresentException
 * @see AnnotationFormatError
 * @see java.lang.annotation.AnnotationTypeMismatchException
 * @see java.lang.annotation.IncompleteAnnotationException
 * @since 1.5
 * @author Josh Bloch
 */
public interface AnnotatedElement {
    /**
     * Returns true if an annotation for the specified type
     * is <em>present</em> on this element, else false.  This method
     * is designed primarily for convenient access to marker annotations.
     *
     * <p>The truth value returned by this method is equivalent to:
     * {@code getAnnotation(annotationClass) != null}
     *
     * <p>The body of the default method is specified to be the code
     * above.
     *
     * <p>
     *  <li>如果元素上存在<i> T </i>类型的注释,且<i> T </i>具有可重复性,且向<i> T </i>元件：
     * 
     * <ul>
     * 
     *  <li>添加<i> T </i>类型的注释既是源兼容的,也是二进制兼容的。
     * 
     *  <li>添加类型<i> T </i>的注释会更改{@code get [Declared]注释(类<T>)}方法和{@code get [Declared] Annotations }方法,因为这些方
     * 法现在只会在元素上看到容器注释,而不会看到类型为<i> T </i>的注释。
     * 
     * <li>添加类型<i> T </i>的注释会更改{@code get [Declared] AnnotationsByType(Class <T>)}方法的结果,因为它们的结果会公开附加的类型注释<i>
     *  T </i>,而以前他们只显示<i> T </i>类型的单个注释。
     * 
     * </ul>
     * 
     * </ul>
     * 
     *  <p>如果此接口中的方法返回的注释包含(直接或间接){@link Class}值的成员引用在此VM中无法访问的类,则尝试通过调用相关类 - 返回注释的返回方法将导致{@link TypeNotPresentException}
     * 。
     * 
     *  <p>同样,如果注释中的枚举常量不再出现在枚举类型中,那么尝试读取枚举值成员将导致{@link EnumConstantNotPresentException}。
     * 
     *  <p>如果注释类型<i> T </i>是使用其值元素表示类型TC </i>的{@code @Repeatable}注释的(元)注释, </i>不会声明返回类型为<i> T </i> {@ code []}
     * 的{@code value()}方法,而是类型为{@link java.lang.annotation。
     *  AnnotationFormatError}被抛出。
     * 
     *  <p>最后,尝试读取定义不兼容的成员将导致{@link java.lang.annotation.AnnotationTypeMismatchException}或{@link java.lang.annotation.IncompleteAnnotationException}
     * 。
     * 
     * 
     * @param annotationClass the Class object corresponding to the
     *        annotation type
     * @return true if an annotation for the specified annotation
     *     type is present on this element, else false
     * @throws NullPointerException if the given annotation class is null
     * @since 1.5
     */
    default boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return getAnnotation(annotationClass) != null;
    }

   /**
     * Returns this element's annotation for the specified type if
     * such an annotation is <em>present</em>, else null.
     *
     * <p>
     * 如果指定类型的注释<em>存在</em>在此元素上,则返回true,否则为false。此方法主要用于方便地访问标记注释。
     * 
     *  <p>此方法返回的真值等价于：{@code getAnnotation(annotationClass)！= null}
     * 
     *  <p>默认方法的正文被指定为上面的代码。
     * 
     * 
     * @param <T> the type of the annotation to query for and return if present
     * @param annotationClass the Class object corresponding to the
     *        annotation type
     * @return this element's annotation for the specified annotation type if
     *     present on this element, else null
     * @throws NullPointerException if the given annotation class is null
     * @since 1.5
     */
    <T extends Annotation> T getAnnotation(Class<T> annotationClass);

    /**
     * Returns annotations that are <em>present</em> on this element.
     *
     * If there are no annotations <em>present</em> on this element, the return
     * value is an array of length 0.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     *
     * <p>
     *  如果此类注释<em>存在</em>,则返回此元素对指定类型的注释,否则返回null。
     * 
     * 
     * @return annotations present on this element
     * @since 1.5
     */
    Annotation[] getAnnotations();

    /**
     * Returns annotations that are <em>associated</em> with this element.
     *
     * If there are no annotations <em>associated</em> with this element, the return
     * value is an array of length 0.
     *
     * The difference between this method and {@link #getAnnotation(Class)}
     * is that this method detects if its argument is a <em>repeatable
     * annotation type</em> (JLS 9.6), and if so, attempts to find one or
     * more annotations of that type by "looking through" a container
     * annotation.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     *
     * @implSpec The default implementation first calls {@link
     * #getDeclaredAnnotationsByType(Class)} passing {@code
     * annotationClass} as the argument. If the returned array has
     * length greater than zero, the array is returned. If the returned
     * array is zero-length and this {@code AnnotatedElement} is a
     * class and the argument type is an inheritable annotation type,
     * and the superclass of this {@code AnnotatedElement} is non-null,
     * then the returned result is the result of calling {@link
     * #getAnnotationsByType(Class)} on the superclass with {@code
     * annotationClass} as the argument. Otherwise, a zero-length
     * array is returned.
     *
     * <p>
     *  返回此元素上<em>存在的注释</em>。
     * 
     *  如果此元素上没有注释<em> </em>,则返回值是长度为0的数组。
     * 
     *  这个方法的调用者可以自由修改返回的数组;它对返回给其他调用者的数组没有影响。
     * 
     * 
     * @param <T> the type of the annotation to query for and return if present
     * @param annotationClass the Class object corresponding to the
     *        annotation type
     * @return all this element's annotations for the specified annotation type if
     *     associated with this element, else an array of length zero
     * @throws NullPointerException if the given annotation class is null
     * @since 1.8
     */
    default <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
         /*
          * Definition of associated: directly or indirectly present OR
          * neither directly nor indirectly present AND the element is
          * a Class, the annotation type is inheritable, and the
          * annotation type is associated with the superclass of the
          * element.
          * <p>
          *  返回与此元素<em>关联的注释</em>。
          * 
          *  如果没有与此元素<em>关联的注释</em>,则返回值是长度为0的数组。
          * 
          *  此方法与{@link #getAnnotation(Class)}之间的差异是此方法检测其参数是否为可重复注记类型(JLS 9.6),如果是,则尝试查找一个或多个通过"查找"容器注释的该类型的注释。
          * 
          *  这个方法的调用者可以自由修改返回的数组;它对返回给其他调用者的数组没有影响。
          * 
          * @implSpec默认实现首先调用{@link #getDeclaredAnnotationsByType(Class)}传递{@code annotationClass}作为参数。
          * 如果返回的数组的长度大于零,则返回数组。
          * 如果返回的数组是零长度的,并且这个{@code AnnotatedElement}是一个类,并且参数类型是一个可继承的注释类型,并且这个{@code AnnotatedElement}的超类是非空的,那
          * 么返回的结果是结果使用{@code annotationClass}作为参数调用超类上的{@link #getAnnotationsByType(Class)}。
          * 如果返回的数组的长度大于零,则返回数组。否则,返回零长度数组。
          * 
          */
         T[] result = getDeclaredAnnotationsByType(annotationClass);

         if (result.length == 0 && // Neither directly nor indirectly present
             this instanceof Class && // the element is a class
             AnnotationType.getInstance(annotationClass).isInherited()) { // Inheritable
             Class<?> superClass = ((Class<?>) this).getSuperclass();
             if (superClass != null) {
                 // Determine if the annotation is associated with the
                 // superclass
                 result = superClass.getAnnotationsByType(annotationClass);
             }
         }

         return result;
     }

    /**
     * Returns this element's annotation for the specified type if
     * such an annotation is <em>directly present</em>, else null.
     *
     * This method ignores inherited annotations. (Returns null if no
     * annotations are directly present on this element.)
     *
     * @implSpec The default implementation first performs a null check
     * and then loops over the results of {@link
     * #getDeclaredAnnotations} returning the first annotation whose
     * annotation type matches the argument type.
     *
     * <p>
     *  关联的定义：直接或间接存在OR既不直接也不间接存在,并且元素是一个类,注释类型是可继承的,注释类型与元素的超类相关联。
     * 
     * 
     * @param <T> the type of the annotation to query for and return if directly present
     * @param annotationClass the Class object corresponding to the
     *        annotation type
     * @return this element's annotation for the specified annotation type if
     *     directly present on this element, else null
     * @throws NullPointerException if the given annotation class is null
     * @since 1.8
     */
    default <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
         Objects.requireNonNull(annotationClass);
         // Loop over all directly-present annotations looking for a matching one
         for (Annotation annotation : getDeclaredAnnotations()) {
             if (annotationClass.equals(annotation.annotationType())) {
                 // More robust to do a dynamic cast at runtime instead
                 // of compile-time only.
                 return annotationClass.cast(annotation);
             }
         }
         return null;
     }

    /**
     * Returns this element's annotation(s) for the specified type if
     * such annotations are either <em>directly present</em> or
     * <em>indirectly present</em>. This method ignores inherited
     * annotations.
     *
     * If there are no specified annotations directly or indirectly
     * present on this element, the return value is an array of length
     * 0.
     *
     * The difference between this method and {@link
     * #getDeclaredAnnotation(Class)} is that this method detects if its
     * argument is a <em>repeatable annotation type</em> (JLS 9.6), and if so,
     * attempts to find one or more annotations of that type by "looking
     * through" a container annotation if one is present.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     *
     * @implSpec The default implementation may call {@link
     * #getDeclaredAnnotation(Class)} one or more times to find a
     * directly present annotation and, if the annotation type is
     * repeatable, to find a container annotation. If annotations of
     * the annotation type {@code annotationClass} are found to be both
     * directly and indirectly present, then {@link
     * #getDeclaredAnnotations()} will get called to determine the
     * order of the elements in the returned array.
     *
     * <p>Alternatively, the default implementation may call {@link
     * #getDeclaredAnnotations()} a single time and the returned array
     * examined for both directly and indirectly present
     * annotations. The results of calling {@link
     * #getDeclaredAnnotations()} are assumed to be consistent with the
     * results of calling {@link #getDeclaredAnnotation(Class)}.
     *
     * <p>
     *  如果此类注释<em>直接显示</em>,则返回此元素对指定类型的注释,否则返回null。
     * 
     *  此方法忽略继承的注释。 (如果没有注释直接出现在此元素上,则返回null。)
     * 
     *  @implSpec默认实现首先执行一个空检查,然后循环遍历{@link #getDeclaredAnnotations}的结果,返回其注释类型与参数类型匹配的第一个注释。
     * 
     * 
     * @param <T> the type of the annotation to query for and return
     * if directly or indirectly present
     * @param annotationClass the Class object corresponding to the
     *        annotation type
     * @return all this element's annotations for the specified annotation type if
     *     directly or indirectly present on this element, else an array of length zero
     * @throws NullPointerException if the given annotation class is null
     * @since 1.8
     */
    default <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return AnnotationSupport.
            getDirectlyAndIndirectlyPresent(Arrays.stream(getDeclaredAnnotations()).
                                            collect(Collectors.toMap(Annotation::annotationType,
                                                                     Function.identity(),
                                                                     ((first,second) -> first),
                                                                     LinkedHashMap::new)),
                                            annotationClass);
    }

    /**
     * Returns annotations that are <em>directly present</em> on this element.
     * This method ignores inherited annotations.
     *
     * If there are no annotations <em>directly present</em> on this element,
     * the return value is an array of length 0.
     *
     * The caller of this method is free to modify the returned array; it will
     * have no effect on the arrays returned to other callers.
     *
     * <p>
     *  如果此类注释是<em>直接显示</em>或<em>间接显示</em>,则返回此元素对指定类型的注释。此方法忽略继承的注释。
     * 
     * 如果在此元素上没有直接或间接指定的注释,则返回值是长度为0的数组。
     * 
     *  此方法与{@link #getDeclaredAnnotation(Class)}之间的区别在于此方法检测其参数是否为可重复注记类型(JLS 9.6),如果是,则尝试查找一个或多个通过"查找"容器注释
     * (如果存在)的那种类型的注释。
     * 
     *  这个方法的调用者可以自由修改返回的数组;它对返回给其他调用者的数组没有影响。
     * 
     *  @implSpec默认实现可以调用{@link #getDeclaredAnnotation(Class)}一次或多次,以查找直接显示的注释,如果注释类型是可重复的,可以查找容器注释。
     * 如果发现注释类型{@code annotationClass}的注释直接和间接出现,则将调用{@link #getDeclaredAnnotations()}以确定返回数组中元素的顺序。
     * 
     * @return annotations directly present on this element
     * @since 1.5
     */
    Annotation[] getDeclaredAnnotations();
}
