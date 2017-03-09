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

package javax.lang.model.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.List;
import javax.lang.model.element.*;
import javax.lang.model.type.*;

/**
 * Utility methods for operating on types.
 *
 * <p><b>Compatibility Note:</b> Methods may be added to this interface
 * in future releases of the platform.
 *
 * <p>
 *  在类型上操作的实用方法。
 * 
 *  <p> <b>兼容性注意</b>：在未来的平台版本中,可能会将方法添加到此界面中。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see javax.annotation.processing.ProcessingEnvironment#getTypeUtils
 * @since 1.6
 */
public interface Types {

    /**
     * Returns the element corresponding to a type.
     * The type may be a {@code DeclaredType} or {@code TypeVariable}.
     * Returns {@code null} if the type is not one with a
     * corresponding element.
     *
     * <p>
     *  返回与类型对应的元素。类型可以是{@code DeclaredType}或{@code TypeVariable}。如果类型不是具有相应元素的类型,则返回{@code null}。
     * 
     * 
     * @param t the type to map to an element
     * @return the element corresponding to the given type
     */
    Element asElement(TypeMirror t);

    /**
     * Tests whether two {@code TypeMirror} objects represent the same type.
     *
     * <p>Caveat: if either of the arguments to this method represents a
     * wildcard, this method will return false.  As a consequence, a wildcard
     * is not the same type as itself.  This might be surprising at first,
     * but makes sense once you consider that an example like this must be
     * rejected by the compiler:
     * <pre>
     *   {@code List<?> list = new ArrayList<Object>();}
     *   {@code list.add(list.get(0));}
     * </pre>
     *
     * <p>Since annotations are only meta-data associated with a type,
     * the set of annotations on either argument is <em>not</em> taken
     * into account when computing whether or not two {@code
     * TypeMirror} objects are the same type. In particular, two
     * {@code TypeMirror} objects can have different annotations and
     * still be considered the same.
     *
     * <p>
     *  测试两个{@code TypeMirror}对象是否表示相同的类型。
     * 
     *  <p>注意：如果此方法的任一参数表示通配符,则此方法将返回false。因此,通配符与其本身不是同一类型。这可能是令人惊讶的,但是有意义的一旦你认为这样的例子必须被编译器拒绝：
     * <pre>
     *  {@code list <?> list = new ArrayList <Object>();} {@code list.add(list.get(0));}
     * </pre>
     * 
     *  <p>由于注释只是与某个类型相关联的元数据,因此在计算两个{@code TypeMirror}对象是否为相同类型时,不会考虑任一参数上的注释集合</em> 。
     * 特别地,两个{@code TypeMirror}对象可以具有不同的注释,并且仍然被认为是相同的。
     * 
     * 
     * @param t1  the first type
     * @param t2  the second type
     * @return {@code true} if and only if the two types are the same
     */
    boolean isSameType(TypeMirror t1, TypeMirror t2);

    /**
     * Tests whether one type is a subtype of another.
     * Any type is considered to be a subtype of itself.
     *
     * <p>
     *  测试一种类型是否是另一种类型的子类型。任何类型都被认为是其自身的子类型。
     * 
     * 
     * @param t1  the first type
     * @param t2  the second type
     * @return {@code true} if and only if the first type is a subtype
     *          of the second
     * @throws IllegalArgumentException if given an executable or package type
     * @jls 4.10 Subtyping
     */
    boolean isSubtype(TypeMirror t1, TypeMirror t2);

    /**
     * Tests whether one type is assignable to another.
     *
     * <p>
     *  测试一种类型是否可分配给另一种类型。
     * 
     * 
     * @param t1  the first type
     * @param t2  the second type
     * @return {@code true} if and only if the first type is assignable
     *          to the second
     * @throws IllegalArgumentException if given an executable or package type
     * @jls 5.2 Assignment Conversion
     */
    boolean isAssignable(TypeMirror t1, TypeMirror t2);

    /**
     * Tests whether one type argument <i>contains</i> another.
     *
     * <p>
     *  测试一个类型参数<i>是否包含</i>另一个。
     * 
     * 
     * @param t1  the first type
     * @param t2  the second type
     * @return {@code true} if and only if the first type contains the second
     * @throws IllegalArgumentException if given an executable or package type
     * @jls 4.5.1.1 Type Argument Containment and Equivalence
     */
    boolean contains(TypeMirror t1, TypeMirror t2);

    /**
     * Tests whether the signature of one method is a <i>subsignature</i>
     * of another.
     *
     * <p>
     * 测试一种方法的签名是否是另一种方法的<子签名</i>。
     * 
     * 
     * @param m1  the first method
     * @param m2  the second method
     * @return {@code true} if and only if the first signature is a
     *          subsignature of the second
     * @jls 8.4.2 Method Signature
     */
    boolean isSubsignature(ExecutableType m1, ExecutableType m2);

    /**
     * Returns the direct supertypes of a type.  The interface types, if any,
     * will appear last in the list.
     *
     * <p>
     *  返回类型的直接超类型。接口类型(如果有)将显示在列表的最后。
     * 
     * 
     * @param t  the type being examined
     * @return the direct supertypes, or an empty list if none
     * @throws IllegalArgumentException if given an executable or package type
     */
    List<? extends TypeMirror> directSupertypes(TypeMirror t);

    /**
     * Returns the erasure of a type.
     *
     * <p>
     *  返回类型的擦除。
     * 
     * 
     * @param t  the type to be erased
     * @return the erasure of the given type
     * @throws IllegalArgumentException if given a package type
     * @jls 4.6 Type Erasure
     */
    TypeMirror erasure(TypeMirror t);

    /**
     * Returns the class of a boxed value of a given primitive type.
     * That is, <i>boxing conversion</i> is applied.
     *
     * <p>
     *  返回给定基本类型的加框值的类。也就是说,应用</i>装箱转换</i>。
     * 
     * 
     * @param p  the primitive type to be converted
     * @return the class of a boxed value of type {@code p}
     * @jls 5.1.7 Boxing Conversion
     */
    TypeElement boxedClass(PrimitiveType p);

    /**
     * Returns the type (a primitive type) of unboxed values of a given type.
     * That is, <i>unboxing conversion</i> is applied.
     *
     * <p>
     *  返回给定类型的未装箱值的类型(基本类型)。也就是说,应用<i>拆箱转换</i>。
     * 
     * 
     * @param t  the type to be unboxed
     * @return the type of an unboxed value of type {@code t}
     * @throws IllegalArgumentException if the given type has no
     *          unboxing conversion
     * @jls 5.1.8 Unboxing Conversion
     */
    PrimitiveType unboxedType(TypeMirror t);

    /**
     * Applies capture conversion to a type.
     *
     * <p>
     *  将捕获转换应用于类型。
     * 
     * 
     * @param t  the type to be converted
     * @return the result of applying capture conversion
     * @throws IllegalArgumentException if given an executable or package type
     * @jls 5.1.10 Capture Conversion
     */
    TypeMirror capture(TypeMirror t);

    /**
     * Returns a primitive type.
     *
     * <p>
     *  返回基本类型。
     * 
     * 
     * @param kind  the kind of primitive type to return
     * @return a primitive type
     * @throws IllegalArgumentException if {@code kind} is not a primitive kind
     */
    PrimitiveType getPrimitiveType(TypeKind kind);

    /**
     * Returns the null type.  This is the type of {@code null}.
     *
     * <p>
     *  返回null类型。这是{@code null}的类型。
     * 
     * 
     * @return the null type
     */
    NullType getNullType();

    /**
     * Returns a pseudo-type used where no actual type is appropriate.
     * The kind of type to return may be either
     * {@link TypeKind#VOID VOID} or {@link TypeKind#NONE NONE}.
     * For packages, use
     * {@link Elements#getPackageElement(CharSequence)}{@code .asType()}
     * instead.
     *
     * <p>
     *  返回在没有实际类型适合的情况下使用的伪类型。返回的类型可以是{@link TypeKind#VOID VOID}或{@link TypeKind#NONE NONE}。
     * 对于程序包,请改用{@link Elements#getPackageElement(CharSequence)} {@ code .asType()}。
     * 
     * 
     * @param kind  the kind of type to return
     * @return a pseudo-type of kind {@code VOID} or {@code NONE}
     * @throws IllegalArgumentException if {@code kind} is not valid
     */
    NoType getNoType(TypeKind kind);

    /**
     * Returns an array type with the specified component type.
     *
     * <p>
     *  返回具有指定组件类型的数组类型。
     * 
     * 
     * @param componentType  the component type
     * @return an array type with the specified component type.
     * @throws IllegalArgumentException if the component type is not valid for
     *          an array
     */
    ArrayType getArrayType(TypeMirror componentType);

    /**
     * Returns a new wildcard type argument.  Either of the wildcard's
     * bounds may be specified, or neither, but not both.
     *
     * <p>
     *  返回新的通配符类型参数。可以指定通配符的边界,也可以不指定通配符的边界,但不能同时指定两者。
     * 
     * 
     * @param extendsBound  the extends (upper) bound, or {@code null} if none
     * @param superBound    the super (lower) bound, or {@code null} if none
     * @return a new wildcard
     * @throws IllegalArgumentException if bounds are not valid
     */
    WildcardType getWildcardType(TypeMirror extendsBound,
                                 TypeMirror superBound);

    /**
     * Returns the type corresponding to a type element and
     * actual type arguments.
     * Given the type element for {@code Set} and the type mirror
     * for {@code String},
     * for example, this method may be used to get the
     * parameterized type {@code Set<String>}.
     *
     * <p> The number of type arguments must either equal the
     * number of the type element's formal type parameters, or must be
     * zero.  If zero, and if the type element is generic,
     * then the type element's raw type is returned.
     *
     * <p> If a parameterized type is being returned, its type element
     * must not be contained within a generic outer class.
     * The parameterized type {@code Outer<String>.Inner<Number>},
     * for example, may be constructed by first using this
     * method to get the type {@code Outer<String>}, and then invoking
     * {@link #getDeclaredType(DeclaredType, TypeElement, TypeMirror...)}.
     *
     * <p>
     *  返回类型元素和实际类型参数对应的类型。例如,给定{@code Set}的类型元素和{@code String}的类型镜像,可以使用此方法获取参数化类型{@code Set <String>}。
     * 
     *  <p>类型参数的数量必须等于类型元素的形式类型参数的数量,或必须为零。如果为零,并且类型元素是通用的,则返回类型元素的原始类型。
     * 
     * <p>如果返回参数化类型,则其类型元素不能包含在通用外部类中。
     * 例如,参数化类型{@code Outer <String> .Inner <Number>}可以通过首先使用此方法获得类型{@code Outer <String>},然后调用{@link #getDeclaredType DeclaredType,TypeElement,TypeMirror ...)}
     * 。
     * <p>如果返回参数化类型,则其类型元素不能包含在通用外部类中。
     * 
     * 
     * @param typeElem  the type element
     * @param typeArgs  the actual type arguments
     * @return the type corresponding to the type element and
     *          actual type arguments
     * @throws IllegalArgumentException if too many or too few
     *          type arguments are given, or if an inappropriate type
     *          argument or type element is provided
     */
    DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs);

    /**
     * Returns the type corresponding to a type element
     * and actual type arguments, given a
     * {@linkplain DeclaredType#getEnclosingType() containing type}
     * of which it is a member.
     * The parameterized type {@code Outer<String>.Inner<Number>},
     * for example, may be constructed by first using
     * {@link #getDeclaredType(TypeElement, TypeMirror...)}
     * to get the type {@code Outer<String>}, and then invoking
     * this method.
     *
     * <p> If the containing type is a parameterized type,
     * the number of type arguments must equal the
     * number of {@code typeElem}'s formal type parameters.
     * If it is not parameterized or if it is {@code null}, this method is
     * equivalent to {@code getDeclaredType(typeElem, typeArgs)}.
     *
     * <p>
     *  返回类型元素和实际类型参数对应的类型,给定它是其成员的{@linkplain DeclaredType#getEnclosingType()包含类型}。
     * 例如,参数化类型{@code Outer <String> .Inner <Number>}可以通过首先使用{@link #getDeclaredType(TypeElement,TypeMirror ...)}
     * 来获得类型{@code Outer <String >},然后调用此方法。
     *  返回类型元素和实际类型参数对应的类型,给定它是其成员的{@linkplain DeclaredType#getEnclosingType()包含类型}。
     * 
     *  <p>如果包含类型是参数化类型,则类型参数的数量必须等于{@code typeElem}的形式类型参数的数量。
     * 如果没有参数化或者它是{@code null},这个方法等价于{@code getDeclaredType(typeElem,typeArgs)}。
     * 
     * @param containing  the containing type, or {@code null} if none
     * @param typeElem    the type element
     * @param typeArgs    the actual type arguments
     * @return the type corresponding to the type element and
     *          actual type arguments, contained within the given type
     * @throws IllegalArgumentException if too many or too few
     *          type arguments are given, or if an inappropriate type
     *          argument, type element, or containing type is provided
     */
    DeclaredType getDeclaredType(DeclaredType containing,
                                 TypeElement typeElem, TypeMirror... typeArgs);

    /**
     * Returns the type of an element when that element is viewed as
     * a member of, or otherwise directly contained by, a given type.
     * For example,
     * when viewed as a member of the parameterized type {@code Set<String>},
     * the {@code Set.add} method is an {@code ExecutableType}
     * whose parameter is of type {@code String}.
     *
     * <p>
     * 
     * 
     * @param containing  the containing type
     * @param element     the element
     * @return the type of the element as viewed from the containing type
     * @throws IllegalArgumentException if the element is not a valid one
     *          for the given type
     */
    TypeMirror asMemberOf(DeclaredType containing, Element element);
}
