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

package javax.lang.model.type;

import java.lang.annotation.Annotation;
import java.util.List;
import javax.lang.model.element.*;
import javax.lang.model.util.Types;

/**
 * Represents a type in the Java programming language.
 * Types include primitive types, declared types (class and interface types),
 * array types, type variables, and the null type.
 * Also represented are wildcard type arguments,
 * the signature and return types of executables,
 * and pseudo-types corresponding to packages and to the keyword {@code void}.
 *
 * <p> Types should be compared using the utility methods in {@link
 * Types}.  There is no guarantee that any particular type will always
 * be represented by the same object.
 *
 * <p> To implement operations based on the class of an {@code
 * TypeMirror} object, either use a {@linkplain TypeVisitor visitor}
 * or use the result of the {@link #getKind} method.  Using {@code
 * instanceof} is <em>not</em> necessarily a reliable idiom for
 * determining the effective class of an object in this modeling
 * hierarchy since an implementation may choose to have a single
 * object implement multiple {@code TypeMirror} subinterfaces.
 *
 * <p>
 *  表示Java编程语言中的类型。类型包括基本类型,声明类型(类和接口类型),数组类型,类型变量和空类型。
 * 还表示通配符类型参数,可执行文件的签名和返回类型,以及对应于包和关键字{@code void}的伪类型。
 * 
 *  <p>应使用{@link Types}中的实用程序方法来比较类型。不能保证任何特定类型将始终由同一对象表示。
 * 
 *  <p>要根据{@code TypeMirror}对象的类实现操作,请使用{@linkplain TypeVisitor visitor}或使用{@link #getKind}方法的结果。
 * 使用{@code instanceof}不是一种可靠的方式来确定此建模层次结构中对象的有效类,因为实现可能选择让单个对象实现多个{@code TypeMirror}子接口。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see Element
 * @see Types
 * @since 1.6
 */
public interface TypeMirror extends javax.lang.model.AnnotatedConstruct {

    /**
     * Returns the {@code kind} of this type.
     *
     * <p>
     *  返回此类型的{@code kind}。
     * 
     * 
     * @return the kind of this type
     */
    TypeKind getKind();

    /**
     * Obeys the general contract of {@link Object#equals Object.equals}.
     * This method does not, however, indicate whether two types represent
     * the same type.
     * Semantic comparisons of type equality should instead use
     * {@link Types#isSameType(TypeMirror, TypeMirror)}.
     * The results of {@code t1.equals(t2)} and
     * {@code Types.isSameType(t1, t2)} may differ.
     *
     * <p>
     *  遵守{@link Object#equals Object.equals}的一般合同。然而,此方法不指示两种类型是否表示相同类型。
     * 类型等价的语义比较应该使用{@link Types#isSameType(TypeMirror,TypeMirror)}。
     *  {@code t1.equals(t2)}和{@code Types.isSameType(t1,t2)}的结果可能不同。
     * 
     * 
     * @param obj  the object to be compared with this type
     * @return {@code true} if the specified object is equal to this one
     */
    boolean equals(Object obj);

    /**
     * Obeys the general contract of {@link Object#hashCode Object.hashCode}.
     *
     * <p>
     * 遵守{@link Object#hashCode Object.hashCode}的一般合同。
     * 
     * 
     * @see #equals
     */
    int hashCode();

    /**
     * Returns an informative string representation of this type.  If
     * possible, the string should be of a form suitable for
     * representing this type in source code.  Any names embedded in
     * the result are qualified if possible.
     *
     * <p>
     *  返回此类型的信息性字符串表示形式。如果可能,字符串应为适合在源代码中表示此类型的形式。如果可能,嵌入在结果中的任何名称都是合格的。
     * 
     * 
     * @return a string representation of this type
     */
    String toString();

    /**
     * Applies a visitor to this type.
     *
     * <p>
     *  将访问者应用于此类型。
     * 
     * @param <R> the return type of the visitor's methods
     * @param <P> the type of the additional parameter to the visitor's methods
     * @param v   the visitor operating on this type
     * @param p   additional parameter to the visitor
     * @return a visitor-specified result
     */
    <R, P> R accept(TypeVisitor<R, P> v, P p);
}
