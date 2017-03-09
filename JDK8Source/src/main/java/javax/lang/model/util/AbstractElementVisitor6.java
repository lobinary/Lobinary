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

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import static javax.lang.model.SourceVersion.*;


/**
 * A skeletal visitor of program elements with default behavior
 * appropriate for the {@link SourceVersion#RELEASE_6 RELEASE_6}
 * source version.
 *
 * <p> <b>WARNING:</b> The {@code ElementVisitor} interface
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
 * #visitUnknown visitUnknown} method.  A new abstract element visitor
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
 *  适用于{@link SourceVersion#RELEASE_6 RELEASE_6}源版本的默认行为的程序元素的骨架访问者。
 * 
 *  <p> <b>警告：</b>此类别实施的{@code ElementVisitor}接口可能会在未来添加方法,以容纳添加到未来版本的Java和贸易中的新的,当前未知的语言结构;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 *  <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 还将引入新的抽象元素访问类来对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
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
 * @see AbstractElementVisitor7
 * @see AbstractElementVisitor8
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public abstract class AbstractElementVisitor6<R, P> implements ElementVisitor<R, P> {
    /**
     * Constructor for concrete subclasses to call.
     * <p>
     *  构造函数具体子类调用。
     * 
     */
    protected AbstractElementVisitor6(){}

    /**
     * Visits any program element as if by passing itself to that
     * element's {@link Element#accept accept} method.  The invocation
     * {@code v.visit(elem)} is equivalent to {@code elem.accept(v,
     * p)}.
     *
     * <p>
     *  访问任何程序元素,就像将自身传递到该元素的{@link Element#accept accept}方法。
     * 调用{@code v.visit(elem)}等价于{@code elem.accept(v,p)}。
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    public final R visit(Element e, P p) {
        return e.accept(this, p);
    }

    /**
     * Visits any program element as if by passing itself to that
     * element's {@link Element#accept accept} method and passing
     * {@code null} for the additional parameter.  The invocation
     * {@code v.visit(elem)} is equivalent to {@code elem.accept(v,
     * null)}.
     *
     * <p>
     *  访问任何程序元素,就像将自身传递到该元素的{@link Element#accept accept}方法并为附加参数传递{@code null}。
     * 调用{@code v.visit(elem)}等价于{@code elem.accept(v,null)}。
     * 
     * 
     * @param e  the element to visit
     * @return a visitor-specified result
     */
    public final R visit(Element e) {
        return e.accept(this, null);
    }

    /**
     * {@inheritDoc}
     *
     * <p> The default implementation of this method in
     * {@code AbstractElementVisitor6} will always throw
     * {@code UnknownElementException}.
     * This behavior is not required of a subclass.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     * @throws UnknownElementException
     *          a visitor implementation may optionally throw this exception
     */
    public R visitUnknown(Element e, P p) {
        throw new UnknownElementException(e, p);
    }
}
