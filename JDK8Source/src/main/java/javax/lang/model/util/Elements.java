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


import java.util.List;
import java.util.Map;

import javax.lang.model.element.*;
import javax.lang.model.type.*;


/**
 * Utility methods for operating on program elements.
 *
 * <p><b>Compatibility Note:</b> Methods may be added to this interface
 * in future releases of the platform.
 *
 * <p>
 *  用于操作程序元素的实用方法。
 * 
 *  <p> <b>兼容性注意</b>：在未来的平台版本中,可能会将方法添加到此界面中。
 * 
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @see javax.annotation.processing.ProcessingEnvironment#getElementUtils
 * @since 1.6
 */
public interface Elements {

    /**
     * Returns a package given its fully qualified name.
     *
     * <p>
     *  返回给定其完全限定名称的包。
     * 
     * 
     * @param name  fully qualified package name, or "" for an unnamed package
     * @return the named package, or {@code null} if it cannot be found
     */
    PackageElement getPackageElement(CharSequence name);

    /**
     * Returns a type element given its canonical name.
     *
     * <p>
     *  返回给定其规范名称的类型元素。
     * 
     * 
     * @param name  the canonical name
     * @return the named type element, or {@code null} if it cannot be found
     */
    TypeElement getTypeElement(CharSequence name);

    /**
     * Returns the values of an annotation's elements, including defaults.
     *
     * <p>
     *  返回注释元素的值,包括默认值。
     * 
     * 
     * @see AnnotationMirror#getElementValues()
     * @param a  annotation to examine
     * @return the values of the annotation's elements, including defaults
     */
    Map<? extends ExecutableElement, ? extends AnnotationValue>
            getElementValuesWithDefaults(AnnotationMirror a);

    /**
     * Returns the text of the documentation (&quot;Javadoc&quot;)
     * comment of an element.
     *
     * <p> A documentation comment of an element is a comment that
     * begins with "{@code /**}" , ends with a separate
     * "<code>*&#47;</code>", and immediately precedes the element,
     * ignoring white space.  Therefore, a documentation comment
     * contains at least three"{@code *}" characters.  The text
     * returned for the documentation comment is a processed form of
     * the comment as it appears in source code.  The leading "{@code
     * /**}" and trailing "<code>*&#47;</code>" are removed.  For lines
     * of the comment starting after the initial "{@code /**}",
     * leading white space characters are discarded as are any
     * consecutive "{@code *}" characters appearing after the white
     * space or starting the line.  The processed lines are then
     * concatenated together (including line terminators) and
     * returned.
     *
     * <p>
     *  返回元素的文档("Javadoc")注释的文本。
     * 
     *  <p>元素的文档注释是以"{@code / **}"开头的注释,以单独的"<code> * / </code>"结束,紧接在元素之前,忽略空格。
     * 因此,文档注释至少包含三个"{@ code *}"字符。为文档注释返回的文本是在源代码中显示的注释的处理形式。
     * 系统会移除前导"{@code / **}"和尾部的"<code> * / </code>"。
     * 对于在初始"{@code / **}"之后开始的注释行,丢弃前导空白字符以及在空格或开始行之后出现的任何连续"{@code *}"字符。然后将处理的行连接在一起(包括行终止符)并返回。
     * 
     * 
     * @param e  the element being examined
     * @return the documentation comment of the element, or {@code null}
     *          if there is none
     * @jls 3.6 White Space
     */
    String getDocComment(Element e);

    /**
     * Returns {@code true} if the element is deprecated, {@code false} otherwise.
     *
     * <p>
     *  如果元素已弃用,则返回{@code true},否则返回{@code false}。
     * 
     * 
     * @param e  the element being examined
     * @return {@code true} if the element is deprecated, {@code false} otherwise
     */
    boolean isDeprecated(Element e);

    /**
     * Returns the <i>binary name</i> of a type element.
     *
     * <p>
     *  返回类型元素的<i>二进制名称</i>。
     * 
     * 
     * @param type  the type element being examined
     * @return the binary name
     *
     * @see TypeElement#getQualifiedName
     * @jls 13.1 The Form of a Binary
     */
    Name getBinaryName(TypeElement type);


    /**
     * Returns the package of an element.  The package of a package is
     * itself.
     *
     * <p>
     * 返回元素的包。包的包本身。
     * 
     * 
     * @param type the element being examined
     * @return the package of an element
     */
    PackageElement getPackageOf(Element type);

    /**
     * Returns all members of a type element, whether inherited or
     * declared directly.  For a class the result also includes its
     * constructors, but not local or anonymous classes.
     *
     * <p>Note that elements of certain kinds can be isolated using
     * methods in {@link ElementFilter}.
     *
     * <p>
     *  返回类型元素的所有成员,无论是继承还是直接声明。对于类,结果还包括其构造函数,但不包括本地或匿名类。
     * 
     *  <p>请注意,某些种类的元素可以使用{@link ElementFilter}中的方法隔离。
     * 
     * 
     * @param type  the type being examined
     * @return all members of the type
     * @see Element#getEnclosedElements
     */
    List<? extends Element> getAllMembers(TypeElement type);

    /**
     * Returns all annotations <i>present</i> on an element, whether
     * directly present or present via inheritance.
     *
     * <p>
     *  返回元素上的所有注释<i> <i> </i>,无论是直接存在还是通过继承呈现。
     * 
     * 
     * @param e  the element being examined
     * @return all annotations of the element
     * @see Element#getAnnotationMirrors
     * @see javax.lang.model.AnnotatedConstruct
     */
    List<? extends AnnotationMirror> getAllAnnotationMirrors(Element e);

    /**
     * Tests whether one type, method, or field hides another.
     *
     * <p>
     *  测试一个类型,方法或字段是否隐藏了另一个。
     * 
     * 
     * @param hider   the first element
     * @param hidden  the second element
     * @return {@code true} if and only if the first element hides
     *          the second
     */
    boolean hides(Element hider, Element hidden);

    /**
     * Tests whether one method, as a member of a given type,
     * overrides another method.
     * When a non-abstract method overrides an abstract one, the
     * former is also said to <i>implement</i> the latter.
     *
     * <p> In the simplest and most typical usage, the value of the
     * {@code type} parameter will simply be the class or interface
     * directly enclosing {@code overrider} (the possibly-overriding
     * method).  For example, suppose {@code m1} represents the method
     * {@code String.hashCode} and {@code m2} represents {@code
     * Object.hashCode}.  We can then ask whether {@code m1} overrides
     * {@code m2} within the class {@code String} (it does):
     *
     * <blockquote>
     * {@code assert elements.overrides(m1, m2,
     *          elements.getTypeElement("java.lang.String")); }
     * </blockquote>
     *
     * A more interesting case can be illustrated by the following example
     * in which a method in type {@code A} does not override a
     * like-named method in type {@code B}:
     *
     * <blockquote>
     * {@code class A { public void m() {} } }<br>
     * {@code interface B { void m(); } }<br>
     * ...<br>
     * {@code m1 = ...;  // A.m }<br>
     * {@code m2 = ...;  // B.m }<br>
     * {@code assert ! elements.overrides(m1, m2,
     *          elements.getTypeElement("A")); }
     * </blockquote>
     *
     * When viewed as a member of a third type {@code C}, however,
     * the method in {@code A} does override the one in {@code B}:
     *
     * <blockquote>
     * {@code class C extends A implements B {} }<br>
     * ...<br>
     * {@code assert elements.overrides(m1, m2,
     *          elements.getTypeElement("C")); }
     * </blockquote>
     *
     * <p>
     *  测试作为给定类型的成员的一个方法是否覆盖另一个方法。当非抽象方法覆盖抽象方法时,前者也被称为实现</i>后者。
     * 
     *  <p>在最简单和最典型的用法中,{@code type}参数的值只是直接包含{@code overrider}(可能覆盖的方法)的类或接口。
     * 例如,假设{@code m1}表示方法{@code String.hashCode},而{@code m2}表示{@code Object.hashCode}。
     * 然后我们可以询问{@code m1}是否覆盖{@code String}类中的{@code m2}：。
     * 
     * <blockquote>
     *  {@code assert elements.overrides(m1,m2,elements.getTypeElement("java.lang.String")); }}
     * </blockquote>
     * 
     *  更有趣的情况可以通过以下示例来说明,其中类型{@code A}中的方法不覆盖类型{@code B}中的类似命名方法：
     * 
     * <blockquote>
     * {@code class A {public void m(){}}} <br> {@code interface B {void m(); }} <br> ... <br> {@code m1 = ...; // A.m}
     *  <br> {@code m2 = ...; // B.m} <br> {@code assert！ elements.overrides(m1,m2,elements.getTypeElement("A")); }
     * }。
     * </blockquote>
     * 
     *  但是,当查看{@code C}的成员时,{@code A}中的方法会覆盖{@code B}中的方法：
     * 
     * @param overrider  the first method, possible overrider
     * @param overridden  the second method, possibly being overridden
     * @param type   the type of which the first method is a member
     * @return {@code true} if and only if the first method overrides
     *          the second
     * @jls 8.4.8 Inheritance, Overriding, and Hiding
     * @jls 9.4.1 Inheritance and Overriding
     */
    boolean overrides(ExecutableElement overrider, ExecutableElement overridden,
                      TypeElement type);

    /**
     * Returns the text of a <i>constant expression</i> representing a
     * primitive value or a string.
     * The text returned is in a form suitable for representing the value
     * in source code.
     *
     * <p>
     * 
     * <blockquote>
     *  {@code class C extends A implements B {}} <br> ... <br> {@code assert elements.overrides(m1,m2,elements.getTypeElement("C")); }
     * }。
     * </blockquote>
     * 
     * 
     * @param value  a primitive value or string
     * @return the text of a constant expression
     * @throws IllegalArgumentException if the argument is not a primitive
     *          value or string
     *
     * @see VariableElement#getConstantValue()
     */
    String getConstantExpression(Object value);

    /**
     * Prints a representation of the elements to the given writer in
     * the specified order.  The main purpose of this method is for
     * diagnostics.  The exact format of the output is <em>not</em>
     * specified and is subject to change.
     *
     * <p>
     *  返回表示原始值或字符串的<i>常量表达式</i>的文本。返回的文本是适合于在源代码中表示值的形式。
     * 
     * 
     * @param w the writer to print the output to
     * @param elements the elements to print
     */
    void printElements(java.io.Writer w, Element... elements);

    /**
     * Return a name with the same sequence of characters as the
     * argument.
     *
     * <p>
     *  以指定的顺序将元素的表示打印到给定的writer。此方法的主要目的是诊断。输出的确切格式不是</em>指定的,可能会更改。
     * 
     * 
     * @param cs the character sequence to return as a name
     * @return a name with the same sequence of characters as the argument
     */
    Name getName(CharSequence cs);

    /**
     * Returns {@code true} if the type element is a functional interface, {@code false} otherwise.
     *
     * <p>
     *  返回与参数具有相同字符序列的名称。
     * 
     * 
     * @param type the type element being examined
     * @return {@code true} if the element is a functional interface, {@code false} otherwise
     * @jls 9.8 Functional Interfaces
     * @since 1.8
     */
    boolean isFunctionalInterface(TypeElement type);
}
