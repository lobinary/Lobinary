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
import javax.lang.model.element.*;

import javax.lang.model.type.TypeMirror;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.SourceVersion;
import javax.annotation.processing.SupportedSourceVersion;

/**
 * A simple visitor for annotation values with default behavior
 * appropriate for the {@link SourceVersion#RELEASE_6 RELEASE_6}
 * source version.  Visit methods call {@link
 * #defaultAction} passing their arguments to {@code defaultAction}'s
 * corresponding parameters.
 *
 * <p> Methods in this class may be overridden subject to their
 * general contract.  Note that annotating methods in concrete
 * subclasses with {@link java.lang.Override @Override} will help
 * ensure that methods are overridden as intended.
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
 * #visitUnknown visitUnknown} method.  A new simple annotation
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
 *  用于注释值的简单访问者,其默认行为适合{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 * 访问方法调用{@link #defaultAction}将参数传递给{@code defaultAction}的相应参数。
 * 
 *  <p>此类别中的方法可能会根据其总合同被覆盖。注意,在具体子类中使用{@link java.lang.Override @Override}注释方法将有助于确保方法按预期被覆盖。
 * 
 *  <p> <b>警告：</b>此类别实施的{@code AnnotationValueVisitor}接口可能会在未来添加方法,以适应添加到未来版本的Java和贸易的新的,当前未知的语言结构;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 * <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 还将引入新的简单注释值访问者类以对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
 * 
 *  <p>请注意,在访问者类中添加一个新访问方法的默认实现,而不是直接在访问者界面中添加<em>默认方法</em>,因为Java SE 8语言功能不能用于此版本的API,因为此版本需要在Java SE 7
 * 实现上运行。
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
 * @see SimpleAnnotationValueVisitor7
 * @see SimpleAnnotationValueVisitor8
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public class SimpleAnnotationValueVisitor6<R, P>
    extends AbstractAnnotationValueVisitor6<R, P> {

    /**
     * Default value to be returned; {@link #defaultAction
     * defaultAction} returns this value unless the method is
     * overridden.
     * <p>
     *  要返回的默认值; {@link #defaultAction defaultAction}返回此值,除非该方法被覆盖。
     * 
     */
    protected final R DEFAULT_VALUE;

    /**
     * Constructor for concrete subclasses; uses {@code null} for the
     * default value.
     * <p>
     *  具体子类的构造函数;使用{@code null}作为默认值。
     * 
     */
    protected SimpleAnnotationValueVisitor6() {
        super();
        DEFAULT_VALUE = null;
    }

    /**
     * Constructor for concrete subclasses; uses the argument for the
     * default value.
     *
     * <p>
     *  具体子类的构造函数;使用该参数作为默认值。
     * 
     * 
     * @param defaultValue the value to assign to {@link #DEFAULT_VALUE}
     */
    protected SimpleAnnotationValueVisitor6(R defaultValue) {
        super();
        DEFAULT_VALUE = defaultValue;
    }

    /**
     * The default action for visit methods.  The implementation in
     * this class just returns {@link #DEFAULT_VALUE}; subclasses will
     * commonly override this method.
     *
     * <p>
     *  访问方法的默认操作。此类中的实现只返回{@link #DEFAULT_VALUE};子类通常会覆盖此方法。
     * 
     * 
     * @param o the value of the annotation
     * @param p a visitor-specified parameter
     * @return {@code DEFAULT_VALUE} unless overridden
     */
    protected R defaultAction(Object o, P p) {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param b {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitBoolean(boolean b, P p) {
        return defaultAction(b, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param b {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitByte(byte b, P p) {
        return defaultAction(b, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     * {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param c {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitChar(char c, P p) {
        return defaultAction(c, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param d {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitDouble(double d, P p) {
        return defaultAction(d, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param f {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitFloat(float f, P p) {
        return defaultAction(f, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param i {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitInt(int i, P p) {
        return defaultAction(i, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param i {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitLong(long i, P p) {
        return defaultAction(i, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param s {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitShort(short s, P p) {
        return defaultAction(s, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param s {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitString(String s, P p) {
        return defaultAction(s, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param t {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitType(TypeMirror t, P p) {
        return defaultAction(t, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param c {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitEnumConstant(VariableElement c, P p) {
        return defaultAction(c, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param a {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitAnnotation(AnnotationMirror a, P p) {
        return defaultAction(a, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * @param vals {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitArray(List<? extends AnnotationValue> vals, P p) {
        return defaultAction(vals, p);
    }
}
