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
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;


/**
 * A simple visitor of program elements with default behavior
 * appropriate for the {@link SourceVersion#RELEASE_6 RELEASE_6}
 * source version.
 *
 * Visit methods corresponding to {@code RELEASE_6} language
 * constructs call {@link #defaultAction defaultAction}, passing their
 * arguments to {@code defaultAction}'s corresponding parameters.
 *
 * For constructs introduced in {@code RELEASE_7} and later, {@code
 * visitUnknown} is called instead.
 *
 * <p> Methods in this class may be overridden subject to their
 * general contract.  Note that annotating methods in concrete
 * subclasses with {@link java.lang.Override @Override} will help
 * ensure that methods are overridden as intended.
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
 * #visitUnknown visitUnknown} method.  A new simple element visitor
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
 *  具有适合{@link SourceVersion#RELEASE_6 RELEASE_6}源版本的默认行为的程序元素的简单访问者。
 * 
 *  访问与{@code RELEASE_6}语言结构相对应的方法,调用{@link #defaultAction defaultAction},将其参数传递到{@code defaultAction}的相
 * 应参数。
 * 
 *  对于在{@code RELEASE_7}和更高版本中引入的结构,将调用{@code visitUnknown}。
 * 
 *  <p>此类别中的方法可能会根据其总合同被覆盖。注意,在具体子类中使用{@link java.lang.Override @Override}注释方法将有助于确保方法按预期被覆盖。
 * 
 *  <p> <b>警告：</b>此类别实现的{@code ElementVisitor}接口可能会在未来添加方法,以容纳添加到未来版本的Java和贸易中的新的,当前未知的语言结构;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 * <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 一个新的简单元素访问类也将被引入以对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
 * 
 *  <p>请注意,在访问者类中添加一个新访问方法的默认实现,而不是直接在访问者界面中添加<em>默认方法</em>,因为Java SE 8语言功能不能用于此版本的API,因为此版本需要在Java SE 7
 * 实现上运行。
 * 仅需要在Java SE 8和更高版本上运行的API的未来版本可以在这种情况下利用默认方法。
 * 
 * 
 * @param <R> the return type of this visitor's methods.  Use {@code Void}
 *             for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's methods.  Use {@code Void}
 *              for visitors that do not need an additional parameter.
 *
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 *
 * @see SimpleElementVisitor7
 * @see SimpleElementVisitor8
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public class SimpleElementVisitor6<R, P> extends AbstractElementVisitor6<R, P> {
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
    protected SimpleElementVisitor6(){
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
    protected SimpleElementVisitor6(R defaultValue){
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
     * @param e the element to process
     * @param p a visitor-specified parameter
     * @return {@code DEFAULT_VALUE} unless overridden
     */
    protected R defaultAction(Element e, P p) {
        return DEFAULT_VALUE;
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitPackage(PackageElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitType(TypeElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * {@inheritDoc}
     *
     * This implementation calls {@code defaultAction}, unless the
     * element is a {@code RESOURCE_VARIABLE} in which case {@code
     * visitUnknown} is called.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 这个实现调用{@code defaultAction},除非元素是{@code RESOURCE_VARIABLE},在这种情况下调用{@code visitUnknown}。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction} or {@code visitUnknown}
     */
    public R visitVariable(VariableElement e, P p) {
        if (e.getKind() != ElementKind.RESOURCE_VARIABLE)
            return defaultAction(e, p);
        else
            return visitUnknown(e, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitExecutable(ExecutableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * {@inheritDoc} This implementation calls {@code defaultAction}.
     *
     * <p>
     *  {@inheritDoc}此实现调用{@code defaultAction}。
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of {@code defaultAction}
     */
    public R visitTypeParameter(TypeParameterElement e, P p) {
        return defaultAction(e, p);
    }
}
