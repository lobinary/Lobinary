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

import java.util.List;
import javax.lang.model.type.*;
import javax.lang.model.util.*;

/**
 * Represents a class or interface program element.  Provides access
 * to information about the type and its members.  Note that an enum
 * type is a kind of class and an annotation type is a kind of
 * interface.
 *
 * <p> <a name="ELEM_VS_TYPE"></a>
 * While a {@code TypeElement} represents a class or interface
 * <i>element</i>, a {@link DeclaredType} represents a class
 * or interface <i>type</i>, the latter being a use
 * (or <i>invocation</i>) of the former.
 * The distinction is most apparent with generic types,
 * for which a single element can define a whole
 * family of types.  For example, the element
 * {@code java.util.Set} corresponds to the parameterized types
 * {@code java.util.Set<String>} and {@code java.util.Set<Number>}
 * (and many others), and to the raw type {@code java.util.Set}.
 *
 * <p> Each method of this interface that returns a list of elements
 * will return them in the order that is natural for the underlying
 * source of program information.  For example, if the underlying
 * source of information is Java source code, then the elements will be
 * returned in source code order.
 *
 * <p>
 *  表示类或接口程序元素。提供对类型及其成员信息的访问。注意,枚举类型是一种类,注解类型是一种接口。
 * 
 *  <p> <a name="ELEM_VS_TYPE"> </a> {@code TypeElement}表示类或接口<i>元素</i>,{@link DeclaredType}表示类或接口<i>类型</i>
 * ,后者是前者的使用(或<i>调用</i>)。
 * 对于通用类型,区别是最明显的,单个元素可以定义整个类型的族。
 * 例如,元素{@code java.util.Set}对应于参数化类型{@code java.util.Set <String>}和{@code java.util.Set <Number>}(和许多其他
 * )和原始类型{@code java.util.Set}。
 * 对于通用类型,区别是最明显的,单个元素可以定义整个类型的族。
 * 
 *  <p>此接口的每个返回元素列表的方法都将按照对于程序信息的基础源自然的顺序返回它们。例如,如果底层的信息源是Java源代码,那么将按源代码顺序返回元素。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see DeclaredType
 * @since 1.6
 */
public interface TypeElement extends Element, Parameterizable, QualifiedNameable {
    /**
     * Returns the fields, methods, constructors, and member types
     * that are directly declared in this class or interface.
     *
     * This includes any (implicit) default constructor and
     * the implicit {@code values} and {@code valueOf} methods of an
     * enum type.
     *
     * <p> Note that as a particular instance of the {@linkplain
     * javax.lang.model.element general accuracy requirements} and the
     * ordering behavior required of this interface, the list of
     * enclosed elements will be returned in the natural order for the
     * originating source of information about the type.  For example,
     * if the information about the type is originating from a source
     * file, the elements will be returned in source code order.
     * (However, in that case the the ordering of synthesized
     * elements, such as a default constructor, is not specified.)
     *
     * <p>
     *  返回在此类或接口中直接声明的字段,方法,构造函数和成员类型。
     * 
     *  这包括枚举类型的任何(隐式)默认构造函数和隐式{@code values}和{@code valueOf}方法。
     * 
     * <p>请注意,作为{@linkplain javax.lang.model.element一般准确度要求}的特定实例和此接口所需的排序行为,将以原始来源的自然顺序返回所包含元素的列表的类型信息。
     * 例如,如果关于类型的信息源自源文件,则将按源代码顺序返回元素。 (但是,在这种情况下,未指定合成元素的排序,例如默认构造函数。)。
     * 
     * 
     * @return the enclosed elements in proper order, or an empty list if none
     */
    @Override
    List<? extends Element> getEnclosedElements();

    /**
     * Returns the <i>nesting kind</i> of this type element.
     *
     * <p>
     *  返回此类型元素的<i>嵌套种类</i>。
     * 
     * 
     * @return the nesting kind of this type element
     */
    NestingKind getNestingKind();

    /**
     * Returns the fully qualified name of this type element.
     * More precisely, it returns the <i>canonical</i> name.
     * For local and anonymous classes, which do not have canonical names,
     * an empty name is returned.
     *
     * <p>The name of a generic type does not include any reference
     * to its formal type parameters.
     * For example, the fully qualified name of the interface
     * {@code java.util.Set<E>} is "{@code java.util.Set}".
     * Nested types use "{@code .}" as a separator, as in
     * "{@code java.util.Map.Entry}".
     *
     * <p>
     *  返回此类型元素的完全限定名称。更确切地说,它返回<i>规范</i>名称。对于没有规范名称的本地和匿名类,返回一个空名称。
     * 
     *  <p>通用类型的名称不包括对其正式类型参数的任何引用。例如,接口{@code java.util.Set <E>}的完全限定名是"{@code java.util.Set}"。
     * 嵌套类型使用"{@code。}"作为分隔符,如在"{@code java.util.Map.Entry}"中。
     * 
     * 
     * @return the fully qualified name of this class or interface, or
     * an empty name if none
     *
     * @see Elements#getBinaryName
     * @jls 6.7 Fully Qualified Names and Canonical Names
     */
    Name getQualifiedName();

    /**
     * Returns the simple name of this type element.
     *
     * For an anonymous class, an empty name is returned.
     *
     * <p>
     *  返回此类型元素的简单名称。
     * 
     *  对于匿名类,返回空名称。
     * 
     * 
     * @return the simple name of this class or interface,
     * an empty name for an anonymous class
     *
     */
    @Override
    Name getSimpleName();

    /**
     * Returns the direct superclass of this type element.
     * If this type element represents an interface or the class
     * {@code java.lang.Object}, then a {@link NoType}
     * with kind {@link TypeKind#NONE NONE} is returned.
     *
     * <p>
     *  返回此类型元素的直接超类。
     * 如果此类型元素表示接口或类{@code java.lang.Object},则返回带有类型{@link TypeKind#NONE NONE}的{@link NoType}。
     * 
     * 
     * @return the direct superclass, or a {@code NoType} if there is none
     */
    TypeMirror getSuperclass();

    /**
     * Returns the interface types directly implemented by this class
     * or extended by this interface.
     *
     * <p>
     *  返回由此类直接实现或由此接口扩展的接口类型。
     * 
     * 
     * @return the interface types directly implemented by this class
     * or extended by this interface, or an empty list if there are none
     */
    List<? extends TypeMirror> getInterfaces();

    /**
     * Returns the formal type parameters of this type element
     * in declaration order.
     *
     * <p>
     * 以声明顺序返回此类型元素的形式类型参数。
     * 
     * 
     * @return the formal type parameters, or an empty list
     * if there are none
     */
    List<? extends TypeParameterElement> getTypeParameters();

    /**
     * Returns the package of a top-level type and returns the
     * immediately lexically enclosing element for a {@linkplain
     * NestingKind#isNested nested} type.
     *
     * <p>
     *  返回顶级类型的包,并返回{@linkplain NestingKind#isNested nested}类型的立即包含字符串的元素。
     * 
     * @return the package of a top-level type, the immediately
     * lexically enclosing element for a nested type
     */
    @Override
    Element getEnclosingElement();
}
