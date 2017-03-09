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


import javax.lang.model.element.*;

import static javax.lang.model.SourceVersion.*;
import javax.lang.model.SourceVersion;
import javax.annotation.processing.SupportedSourceVersion;

/**
 * A skeletal visitor for annotation values with default behavior
 * appropriate for the {@link SourceVersion#RELEASE_6 RELEASE_6}
 * source version.
 *
 * <p> <b>WARNING:</b> The {@code AnnotationValueVisitor} interface
 * implemented by this class may have methods added to it in the
 * future to accommodate new, currently unknown, language structures
 * added to future versions of the Java&trade; programming language.
 * Therefore, methods whose names begin with {@code "visit"} may be
 * added to this class in the future; to avoid incompatibilities,
 * classes which extend this class should not declare any instance
 * methods with names beginning with {@code "visit"}.
 *
 * <p>When such a new visit method is added, the default
 * implementation in this class will be to call the {@link
 * #visitUnknown visitUnknown} method.  A new abstract annotation
 * value visitor class will also be introduced to correspond to the
 * new language level; this visitor will have different default
 * behavior for the visit method in question.  When the new visitor is
 * introduced, all or portions of this visitor may be deprecated.
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
 *  用于注释值的骨架访问者,其默认行为适用于{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 * 
 *  <p> <b>警告：</b>此类别实施的{@code AnnotationValueVisitor}接口可能会在未来添加方法,以适应添加到未来版本的Java和贸易的新的,当前未知的语言结构;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 *  <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 还将引入新的抽象注释值访问者类来对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
 * 
 * <p>请注意,在访问者类中添加一个新访问方法的默认实现,而不是直接在访问者界面中添加<em>默认方法</em>,因为Java SE 8语言功能不能用于此版本的API,因为此版本需要在Java SE 7实
 * 现上运行。
 * 仅需要在Java SE 8和更高版本上运行的API的未来版本可以在这种情况下利用默认方法。
 * 
 * 
 * @param <R> the return type of this visitor's methods
 * @param <P> the type of the additional parameter to this visitor's methods.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 *
 * @see AbstractAnnotationValueVisitor7
 * @see AbstractAnnotationValueVisitor8
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public abstract class AbstractAnnotationValueVisitor6<R, P>
    implements AnnotationValueVisitor<R, P> {

    /**
     * Constructor for concrete subclasses to call.
     * <p>
     *  构造函数具体子类调用。
     * 
     */
    protected AbstractAnnotationValueVisitor6() {}

    /**
     * Visits an annotation value as if by passing itself to that
     * value's {@link AnnotationValue#accept accept}.  The invocation
     * {@code v.visit(av)} is equivalent to {@code av.accept(v, p)}.
     * <p>
     *  访问注释值,就像将自己传递到该值的{@link AnnotationValue#accept accept}。
     * 调用{@code v.visit(av)}等价于{@code av.accept(v,p)}。
     * 
     * 
     * @param av {@inheritDoc}
     * @param p  {@inheritDoc}
     */
    public final R visit(AnnotationValue av, P p) {
        return av.accept(this, p);
    }

    /**
     * Visits an annotation value as if by passing itself to that
     * value's {@link AnnotationValue#accept accept} method passing
     * {@code null} for the additional parameter.  The invocation
     * {@code v.visit(av)} is equivalent to {@code av.accept(v,
     * null)}.
     * <p>
     *  通过将附加参数传递给{@link AnnotationValue#accept accept}方法传递{@code null},来访问注释值。
     * 调用{@code v.visit(av)}等价于{@code av.accept(v,null)}。
     * 
     * 
     * @param av {@inheritDoc}
     */
    public final R visit(AnnotationValue av) {
        return av.accept(this, null);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation of this method in {@code
     * AbstractAnnotationValueVisitor6} will always throw {@code
     * UnknownAnnotationValueException}.  This behavior is not
     * required of a subclass.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p> {@code AbstractAnnotationValueVisitor6}中此方法的默认实现将始终抛出{@code UnknownAnnotationValueException}。
     * 
     * @param av {@inheritDoc}
     * @param p  {@inheritDoc}
     */
    public R visitUnknown(AnnotationValue av, P p) {
        throw new UnknownAnnotationValueException(av, p);
    }
}
