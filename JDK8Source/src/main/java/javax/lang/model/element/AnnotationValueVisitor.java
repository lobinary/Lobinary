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

import javax.lang.model.type.TypeMirror;


/**
 * A visitor of the values of annotation type elements, using a
 * variant of the visitor design pattern.  Unlike a standard visitor
 * which dispatches based on the concrete type of a member of a type
 * hierarchy, this visitor dispatches based on the type of data
 * stored; there are no distinct subclasses for storing, for example,
 * {@code boolean} values versus {@code int} values.  Classes
 * implementing this interface are used to operate on a value when the
 * type of that value is unknown at compile time.  When a visitor is
 * passed to a value's {@link AnnotationValue#accept accept} method,
 * the <tt>visit<i>XYZ</i></tt> method applicable to that value is
 * invoked.
 *
 * <p> Classes implementing this interface may or may not throw a
 * {@code NullPointerException} if the additional parameter {@code p}
 * is {@code null}; see documentation of the implementing class for
 * details.
 *
 * <p> <b>WARNING:</b> It is possible that methods will be added to
 * this interface to accommodate new, currently unknown, language
 * structures added to future versions of the Java&trade; programming
 * language.  Therefore, visitor classes directly implementing this
 * interface may be source incompatible with future versions of the
 * platform.  To avoid this source incompatibility, visitor
 * implementations are encouraged to instead extend the appropriate
 * abstract visitor class that implements this interface.  However, an
 * API should generally use this visitor interface as the type for
 * parameters, return type, etc. rather than one of the abstract
 * classes.
 *
 * <p>Note that methods to accommodate new language constructs could
 * be added in a source <em>compatible</em> way if they were added as
 * <em>default methods</em>.  However, default methods are only
 * available on Java SE 8 and higher releases and the {@code
 * javax.lang.model.*} packages bundled in Java SE 8 are required to
 * also be runnable on Java SE 7.  Therefore, default methods
 * <em>cannot</em> be used when extending {@code javax.lang.model.*}
 * to cover Java SE 8 language features.  However, default methods may
 * be used in subsequent revisions of the {@code javax.lang.model.*}
 * packages that are only required to run on Java SE 8 and higher
 * platform versions.
 *
 * <p>
 *  注释类型元素的值的访问者,使用访问者设计模式的变体。
 * 与根据类型层次结构的成员的具体类型分派的标准访问者不同,此访问者基于存储的数据类型进行调度;没有用于存储例如{@code boolean}值与{@code int}值的不同子类。
 * 实现此接口的类用于在该值的类型在编译时未知时对值进行操作。
 * 当访问者传递到值的{@link AnnotationValue#accept accept}方法时,将调用适用于该值的<tt>访问<i> XYZ </i> </tt>方法。
 * 
 *  <p>实现此接口的类可能会抛出一个{@code NullPointerException},如果附加参数{@code p}是{@code null};有关详细信息,请参阅实现类的文档。
 * 
 * <p> <b>警告：</b>可能的方法将被添加到此接口,以适应新的,目前未知的语言结构添加到未来版本的Java和贸易;编程语言。因此,直接实现此接口的访问者类可能与未来版本的平台不兼容。
 * 为了避免这种源不兼容,鼓励访问者实现扩展实现此接口的适当的抽象访问类。然而,API通常应该使用这个访问者接口作为参数,返回类型等的类型,而不是一个抽象类。
 * 
 *  <p>请注意,如果添加为<em>默认方法</em>,则可以以源<em>兼容</em>方式添加适应新语言结构的方法。
 * 但是,默认方法仅适用于Java SE 8和更高版本,并且Java SE 8中捆绑的{@code javax.lang.model。*}包也需要在Java SE 7上运行。
 * 因此,默认方法<em >不能</em>用于扩展{@code javax.lang.model。*}以涵盖Java SE 8语言功能。
 * 但是,在仅需要在Java SE 8和更高版本的平台上运行的{@code javax.lang.model。*}软件包的后续版本中可以使用默认方法。
 * 
 * 
 * @param <R> the return type of this visitor's methods
 * @param <P> the type of the additional parameter to this visitor's methods.
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface AnnotationValueVisitor<R, P> {
    /**
     * Visits an annotation value.
     * <p>
     *  访问注释值。
     * 
     * 
     * @param av the value to visit
     * @param p a visitor-specified parameter
     * @return  a visitor-specified result
     */
    R visit(AnnotationValue av, P p);

    /**
     * A convenience method equivalent to {@code v.visit(av, null)}.
     * <p>
     *  相当于{@code v.visit(av,null)}的便利方法。
     * 
     * 
     * @param av the value to visit
     * @return  a visitor-specified result
     */
    R visit(AnnotationValue av);

    /**
     * Visits a {@code boolean} value in an annotation.
     * <p>
     *  在注释中访问{@code boolean}值。
     * 
     * 
     * @param b the value being visited
     * @param p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitBoolean(boolean b, P p);

    /**
     * Visits a {@code byte} value in an annotation.
     * <p>
     *  访问注释中的{@code byte}值。
     * 
     * 
     * @param  b the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitByte(byte b, P p);

    /**
     * Visits a {@code char} value in an annotation.
     * <p>
     * 访问注释中的{@code char}值。
     * 
     * 
     * @param  c the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitChar(char c, P p);

    /**
     * Visits a {@code double} value in an annotation.
     * <p>
     *  访问注释中的{@code double}值。
     * 
     * 
     * @param  d the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitDouble(double d, P p);

    /**
     * Visits a {@code float} value in an annotation.
     * <p>
     *  访问注释中的{@code float}值。
     * 
     * 
     * @param  f the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitFloat(float f, P p);

    /**
     * Visits an {@code int} value in an annotation.
     * <p>
     *  访问注释中的{@code int}值。
     * 
     * 
     * @param  i the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitInt(int i, P p);

    /**
     * Visits a {@code long} value in an annotation.
     * <p>
     *  访问注释中的{@code long}值。
     * 
     * 
     * @param  i the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitLong(long i, P p);

    /**
     * Visits a {@code short} value in an annotation.
     * <p>
     *  访问注释中的{@code short}值。
     * 
     * 
     * @param  s the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitShort(short s, P p);

    /**
     * Visits a string value in an annotation.
     * <p>
     *  访问注释中的字符串值。
     * 
     * 
     * @param  s the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitString(String s, P p);

    /**
     * Visits a type value in an annotation.
     * <p>
     *  访问注释中的类型值。
     * 
     * 
     * @param  t the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitType(TypeMirror t, P p);

    /**
     * Visits an {@code enum} value in an annotation.
     * <p>
     *  在注释中访问{@code枚举}值。
     * 
     * 
     * @param  c the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitEnumConstant(VariableElement c, P p);

    /**
     * Visits an annotation value in an annotation.
     * <p>
     *  访问注释中的注释值。
     * 
     * 
     * @param  a the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitAnnotation(AnnotationMirror a, P p);

    /**
     * Visits an array value in an annotation.
     * <p>
     *  访问注释中的数组值。
     * 
     * 
     * @param  vals the value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     */
    R visitArray(List<? extends AnnotationValue> vals, P p);

    /**
     * Visits an unknown kind of annotation value.
     * This can occur if the language evolves and new kinds
     * of value can be stored in an annotation.
     * <p>
     *  访问未知种类的注释值。如果语言演变并且新的值可以存储在注释中,则会出现这种情况。
     * 
     * @param  av the unknown value being visited
     * @param  p a visitor-specified parameter
     * @return the result of the visit
     * @throws UnknownAnnotationValueException
     *  a visitor implementation may optionally throw this exception
     */
    R visitUnknown(AnnotationValue av, P p);
}
