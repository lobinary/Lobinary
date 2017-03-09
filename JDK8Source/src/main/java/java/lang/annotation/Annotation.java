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

package java.lang.annotation;

/**
 * The common interface extended by all annotation types.  Note that an
 * interface that manually extends this one does <i>not</i> define
 * an annotation type.  Also note that this interface does not itself
 * define an annotation type.
 *
 * More information about annotation types can be found in section 9.6 of
 * <cite>The Java&trade; Language Specification</cite>.
 *
 * The {@link java.lang.reflect.AnnotatedElement} interface discusses
 * compatibility concerns when evolving an annotation type from being
 * non-repeatable to being repeatable.
 *
 * <p>
 *  所有注释类型扩展的公共接口。请注意,手动扩展此界面的界面<i>不会</i>定义注释类型。还要注意,此接口本身不定义注释类型。
 * 
 *  有关注释类型的更多信息可以在<cite> Java&trade;语言规范</cite>。
 * 
 *  {@link java.lang.reflect.AnnotatedElement}接口讨论了将注释类型从不可重复的转换为可重复时的兼容性问题。
 * 
 * 
 * @author  Josh Bloch
 * @since   1.5
 */
public interface Annotation {
    /**
     * Returns true if the specified object represents an annotation
     * that is logically equivalent to this one.  In other words,
     * returns true if the specified object is an instance of the same
     * annotation type as this instance, all of whose members are equal
     * to the corresponding member of this annotation, as defined below:
     * <ul>
     *    <li>Two corresponding primitive typed members whose values are
     *    <tt>x</tt> and <tt>y</tt> are considered equal if <tt>x == y</tt>,
     *    unless their type is <tt>float</tt> or <tt>double</tt>.
     *
     *    <li>Two corresponding <tt>float</tt> members whose values
     *    are <tt>x</tt> and <tt>y</tt> are considered equal if
     *    <tt>Float.valueOf(x).equals(Float.valueOf(y))</tt>.
     *    (Unlike the <tt>==</tt> operator, NaN is considered equal
     *    to itself, and <tt>0.0f</tt> unequal to <tt>-0.0f</tt>.)
     *
     *    <li>Two corresponding <tt>double</tt> members whose values
     *    are <tt>x</tt> and <tt>y</tt> are considered equal if
     *    <tt>Double.valueOf(x).equals(Double.valueOf(y))</tt>.
     *    (Unlike the <tt>==</tt> operator, NaN is considered equal
     *    to itself, and <tt>0.0</tt> unequal to <tt>-0.0</tt>.)
     *
     *    <li>Two corresponding <tt>String</tt>, <tt>Class</tt>, enum, or
     *    annotation typed members whose values are <tt>x</tt> and <tt>y</tt>
     *    are considered equal if <tt>x.equals(y)</tt>.  (Note that this
     *    definition is recursive for annotation typed members.)
     *
     *    <li>Two corresponding array typed members <tt>x</tt> and <tt>y</tt>
     *    are considered equal if <tt>Arrays.equals(x, y)</tt>, for the
     *    appropriate overloading of {@link java.util.Arrays#equals}.
     * </ul>
     *
     * <p>
     *  如果指定的对象表示在逻辑上等价于此的注释,则返回true。换句话说,如果指定的对象是与此实例相同注释类型的实例,则返回true,其所有成员都等于此注释的相应成员,如下所定义：
     * <ul>
     *  <li>如果<tt> x == y </tt>,则其值为<tt> x </tt>和<tt> y </tt>的两个对应基本类型成员被视为等价,除非它们的类型为<tt > float </tt>或<tt>
     *  double </tt>。
     * 
     *  <li>如果<tt> Float.valueOf(x).equals(),则其值为<tt> x </tt>和<tt> y </tt>的两个对应的<tt> float </tt> Float.value
     * Of(y))</tt>。
     *  (与<tt> == </tt>运算符不同,NaN被认为等于它自己,并且<tt> 0.0f </tt>不等于<tt> -0.0f </tt>。)。
     * 
     * <li>如果<tt> Double.valueOf(x).equals(x),则其值为<tt> x </tt>和<tt> y </tt>的两个对应的<tt> double </tt> Double.va
     * lueOf(y))</tt>。
     *  (与<tt> == </tt>运算符不同,NaN被认为等于它自己,并且<tt> 0.0 </tt>不等于<tt> -0.0 </tt>。
     * 
     *  <li>其值为<tt> x </tt>和<tt> y </tt>的两个对应的<tt> String </tt>,<tt> Class </tt>,枚举或注释类型成员如果<tt> x.equals(y)
     * </tt>则认为相等。
     *  (请注意,此定义对于注释类型成员是递归的。)。
     * 
     *  <li>如果<tt> Arrays.equals(x,y)</tt>,对于适当的重载,两个相应的数组类型成员<tt> x </tt>和<tt> y </tt> {@link java.util.Arrays#equals}
     * 。
     * </ul>
     * 
     * 
     * @return true if the specified object represents an annotation
     *     that is logically equivalent to this one, otherwise false
     */
    boolean equals(Object obj);

    /**
     * Returns the hash code of this annotation, as defined below:
     *
     * <p>The hash code of an annotation is the sum of the hash codes
     * of its members (including those with default values), as defined
     * below:
     *
     * The hash code of an annotation member is (127 times the hash code
     * of the member-name as computed by {@link String#hashCode()}) XOR
     * the hash code of the member-value, as defined below:
     *
     * <p>The hash code of a member-value depends on its type:
     * <ul>
     * <li>The hash code of a primitive value <tt><i>v</i></tt> is equal to
     *     <tt><i>WrapperType</i>.valueOf(<i>v</i>).hashCode()</tt>, where
     *     <tt><i>WrapperType</i></tt> is the wrapper type corresponding
     *     to the primitive type of <tt><i>v</i></tt> ({@link Byte},
     *     {@link Character}, {@link Double}, {@link Float}, {@link Integer},
     *     {@link Long}, {@link Short}, or {@link Boolean}).
     *
     * <li>The hash code of a string, enum, class, or annotation member-value
     I     <tt><i>v</i></tt> is computed as by calling
     *     <tt><i>v</i>.hashCode()</tt>.  (In the case of annotation
     *     member values, this is a recursive definition.)
     *
     * <li>The hash code of an array member-value is computed by calling
     *     the appropriate overloading of
     *     {@link java.util.Arrays#hashCode(long[]) Arrays.hashCode}
     *     on the value.  (There is one overloading for each primitive
     *     type, and one for object reference types.)
     * </ul>
     *
     * <p>
     *  返回此注释的哈希码,如下所定义：
     * 
     *  <p>注释的哈希码是其成员(包括具有默认值的那些)的哈希码的总和,如下所定义：
     * 
     *  注释成员的哈希码是({@link String#hashCode()}计算的成员名的哈希码的127倍)对成员值的哈希码进行异或,如下所定义：
     * 
     *  <p>成员值的哈希码取决于其类型：
     * <ul>
     * <li>原始值<tt> <i> v </i> </tt>的哈希码等于<tt> <i> WrapperType </i> .valueOf(<i> v </i> ).hashCode()</tt>,其中<tt>
     *  <i> WrapperType </i> </tt>是对应于<tt> <i> v </i> >({@link Byte},{@link Character},{@link Double},{@link Float}
     * ,{@link Integer},{@link Long},{@link Short}或{@link Boolean} )。
     * 
     *  <li>字符串,枚举,类或注释成员值I <tt> <i> v </i> </tt>的哈希码通过调用<tt> <i> v </i > .hashCode()</tt>。
     *  (在注释成员值的情况下,这是递归定义。)。
     * 
     * @return the hash code of this annotation
     */
    int hashCode();

    /**
     * Returns a string representation of this annotation.  The details
     * of the representation are implementation-dependent, but the following
     * may be regarded as typical:
     * <pre>
     *   &#064;com.acme.util.Name(first=Alfred, middle=E., last=Neuman)
     * </pre>
     *
     * <p>
     * 
     *  <li>通过调用对值的适当重载{@link java.util.Arrays#hashCode(long [])Arrays.hashCode}来计算数组成员值的哈希码。
     *  (每个基本类型有一个重载,一个对象引用类型。)。
     * </ul>
     * 
     * 
     * @return a string representation of this annotation
     */
    String toString();

    /**
     * Returns the annotation type of this annotation.
     * <p>
     *  返回此注记的字符串表示形式。表示的细节是依赖于实现的,但是以下可以被认为是典型的：
     * <pre>
     *  @ com.acme.util.Name(first = Alfred,middle = E。,last = Neuman)
     * </pre>
     * 
     * @return the annotation type of this annotation
     */
    Class<? extends Annotation> annotationType();
}
