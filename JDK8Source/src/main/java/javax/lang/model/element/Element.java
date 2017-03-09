/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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


import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.List;
import java.util.Set;

import javax.lang.model.type.*;
import javax.lang.model.util.*;


/**
 * Represents a program element such as a package, class, or method.
 * Each element represents a static, language-level construct
 * (and not, for example, a runtime construct of the virtual machine).
 *
 * <p> Elements should be compared using the {@link #equals(Object)}
 * method.  There is no guarantee that any particular element will
 * always be represented by the same object.
 *
 * <p> To implement operations based on the class of an {@code
 * Element} object, either use a {@linkplain ElementVisitor visitor} or
 * use the result of the {@link #getKind} method.  Using {@code
 * instanceof} is <em>not</em> necessarily a reliable idiom for
 * determining the effective class of an object in this modeling
 * hierarchy since an implementation may choose to have a single object
 * implement multiple {@code Element} subinterfaces.
 *
 * <p>
 *  表示程序元素,如包,类或方法。每个元素表示一个静态的语言级结构(而不​​是虚拟机的运行时结构)。
 * 
 *  <p>元素应使用{@link #equals(Object)}方法进行比较。不能保证任何特定元素将始终由相同的对象表示。
 * 
 *  <p>要根据{@code Element}对象的类实现操作,请使用{@linkplain ElementVisitor visitor}或使用{@link #getKind}方法的结果。
 * 由于实现可能选择让单个对象实现多个{@code Element}子接口,因此使用{@code instanceof}不是</em>确定此建模层次结构中对象的有效类的可靠习语。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see Elements
 * @see TypeMirror
 * @since 1.6
 */
public interface Element extends javax.lang.model.AnnotatedConstruct {
    /**
     * Returns the type defined by this element.
     *
     * <p> A generic element defines a family of types, not just one.
     * If this is a generic element, a <i>prototypical</i> type is
     * returned.  This is the element's invocation on the
     * type variables corresponding to its own formal type parameters.
     * For example,
     * for the generic class element {@code C<N extends Number>},
     * the parameterized type {@code C<N>} is returned.
     * The {@link Types} utility interface has more general methods
     * for obtaining the full range of types defined by an element.
     *
     * <p>
     *  返回此元素定义的类型。
     * 
     *  <p>通用元素定义了一系列类型,而不只是一个。如果这是通用元素,则返回<i>原型</i>类型。这是元素对类型变量的调用,对应于它自己的形式类型参数。
     * 例如,对于通用类元素{@code C <N extends Number>},将返回参数化类型{@code C <N>}。
     *  {@link Types}实用程序接口有更通用的方法来获取元素定义的所有类型的类型。
     * 
     * 
     * @see Types
     *
     * @return the type defined by this element
     */
    TypeMirror asType();

    /**
     * Returns the {@code kind} of this element.
     *
     * <p>
     * 返回此元素的{@code kind}。
     * 
     * 
     * @return the kind of this element
     */
    ElementKind getKind();

    /**
     * Returns the modifiers of this element, excluding annotations.
     * Implicit modifiers, such as the {@code public} and {@code static}
     * modifiers of interface members, are included.
     *
     * <p>
     *  返回此元素的修饰符,不包括注释。包括隐式修饰符,例如接口成员的{@code public}和{@code static}修饰符。
     * 
     * 
     * @return the modifiers of this element, or an empty set if there are none
     */
    Set<Modifier> getModifiers();

    /**
     * Returns the simple (unqualified) name of this element.  The
     * name of a generic type does not include any reference to its
     * formal type parameters.
     *
     * For example, the simple name of the type element {@code
     * java.util.Set<E>} is {@code "Set"}.
     *
     * If this element represents an unnamed {@linkplain
     * PackageElement#getSimpleName package}, an empty name is
     * returned.
     *
     * If it represents a {@linkplain ExecutableElement#getSimpleName
     * constructor}, the name "{@code <init>}" is returned.  If it
     * represents a {@linkplain ExecutableElement#getSimpleName static
     * initializer}, the name "{@code <clinit>}" is returned.
     *
     * If it represents an {@linkplain TypeElement#getSimpleName
     * anonymous class} or {@linkplain ExecutableElement#getSimpleName
     * instance initializer}, an empty name is returned.
     *
     * <p>
     *  返回此元素的简单(无限定)名称。通用类型的名称不包括对其正式类型参数的任何引用。
     * 
     *  例如,类型元素{@code java.util.Set <E>}的简单名称是{@code"Set"}。
     * 
     *  如果此元素表示未命名的{@linkplain PackageElement#getSimpleName包},则会返回一个空名称。
     * 
     *  如果它代表一个{@linkplain ExecutableElement#getSimpleName构造函数},则返回名称"{@code <init>}"。
     * 如果它代表一个{@linkplain ExecutableElement#getSimpleName static initializer},则返回名称"{@code <clinit>}"。
     * 
     *  如果它代表{@linkplain TypeElement#getSimpleName匿名类}或{@linkplain ExecutableElement#getSimpleName实例初始化程序},则
     * 返回一个空名称。
     * 
     * 
     * @return the simple name of this element
     * @see PackageElement#getSimpleName
     * @see ExecutableElement#getSimpleName
     * @see TypeElement#getSimpleName
     * @see VariableElement#getSimpleName
     */
    Name getSimpleName();

    /**
     * Returns the innermost element
     * within which this element is, loosely speaking, enclosed.
     * <ul>
     * <li> If this element is one whose declaration is lexically enclosed
     * immediately within the declaration of another element, that other
     * element is returned.
     *
     * <li> If this is a {@linkplain TypeElement#getEnclosingElement
     * top-level type}, its package is returned.
     *
     * <li> If this is a {@linkplain
     * PackageElement#getEnclosingElement package}, {@code null} is
     * returned.
     *
     * <li> If this is a {@linkplain
     * TypeParameterElement#getEnclosingElement type parameter},
     * {@linkplain TypeParameterElement#getGenericElement the
     * generic element} of the type parameter is returned.
     *
     * <li> If this is a {@linkplain
     * VariableElement#getEnclosingElement method or constructor
     * parameter}, {@linkplain ExecutableElement the executable
     * element} which declares the parameter is returned.
     *
     * </ul>
     *
     * <p>
     *  返回最里面的元素,在这个元素是松散地说,封闭。
     * <ul>
     *  <li>如果此元素是其声明立即包含在另一个元素的声明中的词,则返回该另一个元素。
     * 
     *  <li>如果这是{@linkplain TypeElement#getEnclosingElement顶级类型},则会返回其包。
     * 
     *  <li>如果这是{@linkplain PackageElement#getEnclosingElement package},则会返回{@code null}。
     * 
     * <li>如果这是{@linkplain TypeParameterElement#getEnclosingElement类型参数},则会返回类型参数的{@linkplain TypeParameterElement#getGenericElement通用元素}
     * 。
     * 
     *  <li>如果这是{@linkplain VariableElement#getEnclosingElement方法或构造函数参数},则返回声明该参数的{@linkplain ExecutableElement可执行元素}
     * 。
     * 
     * </ul>
     * 
     * 
     * @return the enclosing element, or {@code null} if there is none
     * @see Elements#getPackageOf
     */
    Element getEnclosingElement();

    /**
     * Returns the elements that are, loosely speaking, directly
     * enclosed by this element.
     *
     * A {@linkplain TypeElement#getEnclosedElements class or
     * interface} is considered to enclose the fields, methods,
     * constructors, and member types that it directly declares.
     *
     * A {@linkplain PackageElement#getEnclosedElements package}
     * encloses the top-level classes and interfaces within it, but is
     * not considered to enclose subpackages.
     *
     * Other kinds of elements are not currently considered to enclose
     * any elements; however, that may change as this API or the
     * programming language evolves.
     *
     * <p>Note that elements of certain kinds can be isolated using
     * methods in {@link ElementFilter}.
     *
     * <p>
     *  返回宽松地直接包含在此元素中的元素。
     * 
     *  一个{@linkplain TypeElement#getEnclosedElements类或接口}被认为包含它直接声明的字段,方法,构造函数和成员类型。
     * 
     *  {@linkplain PackageElement#getEnclosedElements package}包含其中的顶级类和接口,但不认为包含子包。
     * 
     *  其他种类的元素目前不被认为包含任何元素;然而,这可能随着这个API或编程语言的发展而改变。
     * 
     *  <p>请注意,某些种类的元素可以使用{@link ElementFilter}中的方法隔离。
     * 
     * 
     * @return the enclosed elements, or an empty list if none
     * @see PackageElement#getEnclosedElements
     * @see TypeElement#getEnclosedElements
     * @see Elements#getAllMembers
     * @jls 8.8.9 Default Constructor
     * @jls 8.9 Enums
     */
    List<? extends Element> getEnclosedElements();

    /**
     * Returns {@code true} if the argument represents the same
     * element as {@code this}, or {@code false} otherwise.
     *
     * <p>Note that the identity of an element involves implicit state
     * not directly accessible from the element's methods, including
     * state about the presence of unrelated types.  Element objects
     * created by different implementations of these interfaces should
     * <i>not</i> be expected to be equal even if &quot;the same&quot;
     * element is being modeled; this is analogous to the inequality
     * of {@code Class} objects for the same class file loaded through
     * different class loaders.
     *
     * <p>
     *  如果参数表示与{@code this}相同的元素,则返回{@code true},否则返回{@code false}。
     * 
     * <p>请注意,元素的标识涉及隐含状态,不能从元素的方法直接访问,包括关于存在不相关类型的状态。
     * 由这些接口的不同实现创建的元素对象不应该预期是相等的,即使"相同"元素正在建模;这类似于通过不同的类加载器加载的同一类文件的{@code Class}对象的不等式。
     * 
     * 
     * @param obj  the object to be compared with this element
     * @return {@code true} if the specified object represents the same
     *          element as this
     */
    @Override
    boolean equals(Object obj);

    /**
     * Obeys the general contract of {@link Object#hashCode Object.hashCode}.
     *
     * <p>
     *  遵守{@link Object#hashCode Object.hashCode}的一般合同。
     * 
     * 
     * @see #equals
     */
    @Override
    int hashCode();


    /**
     * {@inheritDoc}
     *
     * <p> To get inherited annotations as well, use {@link
     * Elements#getAllAnnotationMirrors(Element)
     * getAllAnnotationMirrors}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>要获取继承的注释,请使用{@link Elements#getAllAnnotationMirrors(Element)getAllAnnotationMirrors}。
     * 
     * 
     * @since 1.6
     */
    @Override
    List<? extends AnnotationMirror> getAnnotationMirrors();

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    @Override
    <A extends Annotation> A getAnnotation(Class<A> annotationType);

    /**
     * Applies a visitor to this element.
     *
     * <p>
     *  将访问者应用于此元素。
     * 
     * @param <R> the return type of the visitor's methods
     * @param <P> the type of the additional parameter to the visitor's methods
     * @param v   the visitor operating on this element
     * @param p   additional parameter to the visitor
     * @return a visitor-specified result
     */
    <R, P> R accept(ElementVisitor<R, P> v, P p);
}
