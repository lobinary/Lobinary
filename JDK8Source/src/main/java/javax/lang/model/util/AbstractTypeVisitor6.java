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

import javax.lang.model.type.*;

/**
 * A skeletal visitor of types with default behavior appropriate for
 * the {@link javax.lang.model.SourceVersion#RELEASE_6 RELEASE_6}
 * source version.
 *
 * <p> <b>WARNING:</b> The {@code TypeVisitor} interface implemented
 * by this class may have methods added to it in the future to
 * accommodate new, currently unknown, language structures added to
 * future versions of the Java&trade; programming language.
 * Therefore, methods whose names begin with {@code "visit"} may be
 * added to this class in the future; to avoid incompatibilities,
 * classes which extend this class should not declare any instance
 * methods with names beginning with {@code "visit"}.
 *
 * <p>When such a new visit method is added, the default
 * implementation in this class will be to call the {@link
 * #visitUnknown visitUnknown} method.  A new abstract type visitor
 * class will also be introduced to correspond to the new language
 * level; this visitor will have different default behavior for the
 * visit method in question.  When the new visitor is introduced, all
 * or portions of this visitor may be deprecated.
 *
 * <p>Note that adding a default implementation of a new visit method
 * in a visitor class will occur instead of adding a <em>default
 * method</em> directly in the visitor interface since a Java SE 8
 * language feature cannot be used to this version of the API since
 * this version is required to be runnable on Java SE 7
 * implementations.  Future versions of the API that are only required
 * to run on Java SE 8 and later may take advantage of default methods
 * in this situation.
 *
 * <p>
 *  适用于{@link javax.lang.model.SourceVersion#RELEASE_6 RELEASE_6}源版本的默认行为的类型的骨架访问者。
 * 
 *  <p> <b>警告：</b>此类别实施的{@code TypeVisitor}接口将来可能会添加方法,以容纳添加到未来版本的Java和贸易中的新的,当前未知的语言结构;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 *  <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 还将引入新的抽象类型访问者类来对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
 * 
 * <p>请注意,在访问者类中添加一个新访问方法的默认实现,而不是直接在访问者界面中添加<em>默认方法</em>,因为Java SE 8语言功能不能用于此版本的API,因为此版本需要在Java SE 7实
 * 现上运行。
 * 仅需要在Java SE 8和更高版本上运行的API的未来版本可以在这种情况下利用默认方法。
 * 
 * 
 * @param <R> the return type of this visitor's methods.  Use {@link
 *            Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 *
 * @see AbstractTypeVisitor7
 * @see AbstractTypeVisitor8
 * @since 1.6
 */
public abstract class AbstractTypeVisitor6<R, P> implements TypeVisitor<R, P> {
    /**
     * Constructor for concrete subclasses to call.
     * <p>
     *  构造函数具体子类调用。
     * 
     */
    protected AbstractTypeVisitor6() {}

    /**
     * Visits any type mirror as if by passing itself to that type
     * mirror's {@link TypeMirror#accept accept} method.  The
     * invocation {@code v.visit(t, p)} is equivalent to {@code
     * t.accept(v, p)}.
     *
     * <p>
     *  访问任何类型的镜像,就像将自身传递给该类型镜像的{@link TypeMirror#accept accept}方法。
     * 调用{@code v.visit(t,p)}等价于{@code t.accept(v,p)}。
     * 
     * 
     * @param t  the type to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    public final R visit(TypeMirror t, P p) {
        return t.accept(this, p);
    }

    /**
     * Visits any type mirror as if by passing itself to that type
     * mirror's {@link TypeMirror#accept accept} method and passing
     * {@code null} for the additional parameter.  The invocation
     * {@code v.visit(t)} is equivalent to {@code t.accept(v, null)}.
     *
     * <p>
     *  访问任何类型的镜像,就像将自身传递给该类型镜像的{@link TypeMirror#accept accept}方法,并为附加参数传递{@code null}。
     * 调用{@code v.visit(t)}等价于{@code t.accept(v,null)}。
     * 
     * 
     * @param t  the type to visit
     * @return a visitor-specified result
     */
    public final R visit(TypeMirror t) {
        return t.accept(this, null);
    }

    /**
     * Visits a {@code UnionType} element by calling {@code
     * visitUnknown}.

     * <p>
     *  通过调用{@code visitUnknown}访问{@code UnionType}元素。
     * 
     * 
     * @param t  {@inheritDoc}
     * @param p  {@inheritDoc}
     * @return the result of {@code visitUnknown}
     *
     * @since 1.7
     */
    public R visitUnion(UnionType t, P p) {
        return visitUnknown(t, p);
    }

    /**
     * Visits an {@code IntersectionType} element by calling {@code
     * visitUnknown}.

     * <p>
     *  通过调用{@code visitUnknown}访问{@code IntersectionType}元素。
     * 
     * 
     * @param t  {@inheritDoc}
     * @param p  {@inheritDoc}
     * @return the result of {@code visitUnknown}
     *
     * @since 1.8
     */
    public R visitIntersection(IntersectionType t, P p) {
        return visitUnknown(t, p);
    }

    /**
     * {@inheritDoc}
     *
     * <p> The default implementation of this method in {@code
     * AbstractTypeVisitor6} will always throw {@code
     * UnknownTypeException}.  This behavior is not required of a
     * subclass.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @param t  the type to visit
     * @return a visitor-specified result
     * @throws UnknownTypeException
     *  a visitor implementation may optionally throw this exception
     */
    public R visitUnknown(TypeMirror t, P p) {
        throw new UnknownTypeException(t, p);
    }
}
