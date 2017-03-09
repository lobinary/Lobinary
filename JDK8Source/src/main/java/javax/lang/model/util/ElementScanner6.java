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
 * A scanning visitor of program elements with default behavior
 * appropriate for the {@link SourceVersion#RELEASE_6 RELEASE_6}
 * source version.  The <tt>visit<i>XYZ</i></tt> methods in this
 * class scan their component elements by calling {@code scan} on
 * their {@linkplain Element#getEnclosedElements enclosed elements},
 * {@linkplain ExecutableElement#getParameters parameters}, etc., as
 * indicated in the individual method specifications.  A subclass can
 * control the order elements are visited by overriding the
 * <tt>visit<i>XYZ</i></tt> methods.  Note that clients of a scanner
 * may get the desired behavior be invoking {@code v.scan(e, p)} rather
 * than {@code v.visit(e, p)} on the root objects of interest.
 *
 * <p>When a subclass overrides a <tt>visit<i>XYZ</i></tt> method, the
 * new method can cause the enclosed elements to be scanned in the
 * default way by calling <tt>super.visit<i>XYZ</i></tt>.  In this
 * fashion, the concrete visitor can control the ordering of traversal
 * over the component elements with respect to the additional
 * processing; for example, consistently calling
 * <tt>super.visit<i>XYZ</i></tt> at the start of the overridden
 * methods will yield a preorder traversal, etc.  If the component
 * elements should be traversed in some other order, instead of
 * calling <tt>super.visit<i>XYZ</i></tt>, an overriding visit method
 * should call {@code scan} with the elements in the desired order.
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
 * #visitUnknown visitUnknown} method.  A new element scanner visitor
 * class will also be introduced to correspond to the new language
 * level; this visitor will have different default behavior for the
 * visit method in question.  When the new visitor is introduced, all
 * or portions of this visitor may be deprecated.
 *
 * <p>
 *  适用于{@link SourceVersion#RELEASE_6 RELEASE_6}源版本的默认行为的程序元素的扫描访问者。
 * 此类别中的<tt>访问<i> XYZ </i> </tt>方法通过在其{@linkplain Element#getEnclosedElements封闭元素} {@linkplain ExecutableElement#getParameters参数}
 * 等,如各个方法规范中所示。
 *  适用于{@link SourceVersion#RELEASE_6 RELEASE_6}源版本的默认行为的程序元素的扫描访问者。
 * 子类可以通过覆盖<tt>访问<i> XYZ </i> </tt>方法来控制访问元素的顺序。
 * 注意,扫描器的客户端可以在感兴趣的根对象上调用{@code v.scan(e,p)}而不是{@code v.visit(e,p)}所需的行为。
 * 
 * <p>当子类覆盖<tt>访问<i> XYZ </i> </tt>方法时,新方法可以使用默认方式扫描所包含的元素,方法是调用<tt> super.visit < i> XYZ </i> </t>。
 * 以这种方式,具体访问者可以相对于附加处理来控制遍历组件元素的顺序;例如,在重写方法开始时始终调用<tt> super.visit <XYZ </i> </tt>将产生预订遍历等。
 * 如果组件元素应以某种其他顺序遍历,而不是调用<tt> super.visit <i> XYZ </i> </tt>,覆盖访问方法应该使用所需顺序的元素调用{@code scan}。
 * 
 *  <p>此类别中的方法可能会根据其总合同被覆盖。注意,在具体子类中使用{@link java.lang.Override @Override}注释方法将有助于确保方法按预期被覆盖。
 * 
 *  <p> <b>警告：</b>此类别实现的{@code ElementVisitor}接口可能会在未来添加方法,以容纳添加到未来版本的Java和贸易中的新的,当前未知的语言结构;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 * <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 还将引入新的元素扫描器访问者类,以对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
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
 * @see ElementScanner7
 * @see ElementScanner8
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public class ElementScanner6<R, P> extends AbstractElementVisitor6<R, P> {
    /**
     * The specified default value.
     * <p>
     *  指定的默认值。
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
    protected ElementScanner6(){
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
     * @param defaultValue the default value
     */
    protected ElementScanner6(R defaultValue){
        DEFAULT_VALUE = defaultValue;
    }

    /**
     * Iterates over the given elements and calls {@link
     * #scan(Element, Object) scan(Element, P)} on each one.  Returns
     * the result of the last call to {@code scan} or {@code
     * DEFAULT_VALUE} for an empty iterable.
     *
     * <p>
     *  迭代给定的元素并调用{@link #scan(Element,Object)scan(Element,P)}。
     * 返回最后一次调用{@code scan}或{@code DEFAULT_VALUE}的结果,得到一个空的可迭代。
     * 
     * 
     * @param iterable the elements to scan
     * @param  p additional parameter
     * @return the scan of the last element or {@code DEFAULT_VALUE} if no elements
     */
    public final R scan(Iterable<? extends Element> iterable, P p) {
        R result = DEFAULT_VALUE;
        for(Element e : iterable)
            result = scan(e, p);
        return result;
    }

    /**
     * Processes an element by calling {@code e.accept(this, p)};
     * this method may be overridden by subclasses.
     *
     * <p>
     *  通过调用{@code e.accept(this,p)}来处理一个元素;此方法可能被子类覆盖。
     * 
     * 
     * @param e the element to scan
     * @param p a scanner-specified parameter
     * @return the result of visiting {@code e}.
     */
    public R scan(Element e, P p) {
        return e.accept(this, p);
    }

    /**
     * Convenience method equivalent to {@code v.scan(e, null)}.
     *
     * <p>
     *  相当于{@code v.scan(e,null)}的便捷方法。
     * 
     * 
     * @param e the element to scan
     * @return the result of scanning {@code e}.
     */
    public final R scan(Element e) {
        return scan(e, null);
    }

    /**
     * {@inheritDoc} This implementation scans the enclosed elements.
     *
     * <p>
     *  {@inheritDoc}此实现扫描封闭的元素。
     * 
     * 
     * @param e  {@inheritDoc}
     * @param p  {@inheritDoc}
     * @return the result of scanning
     */
    public R visitPackage(PackageElement e, P p) {
        return scan(e.getEnclosedElements(), p);
    }

    /**
     * {@inheritDoc} This implementation scans the enclosed elements.
     *
     * <p>
     *  {@inheritDoc}此实现扫描封闭的元素。
     * 
     * 
     * @param e  {@inheritDoc}
     * @param p  {@inheritDoc}
     * @return the result of scanning
     */
    public R visitType(TypeElement e, P p) {
        return scan(e.getEnclosedElements(), p);
    }

    /**
     * {@inheritDoc}
     *
     * This implementation scans the enclosed elements, unless the
     * element is a {@code RESOURCE_VARIABLE} in which case {@code
     * visitUnknown} is called.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  这个实现扫描封闭的元素,除非元素是{@code RESOURCE_VARIABLE},在这种情况下调用{@code visitUnknown}。
     * 
     * 
     * @param e  {@inheritDoc}
     * @param p  {@inheritDoc}
     * @return the result of scanning
     */
    public R visitVariable(VariableElement e, P p) {
        if (e.getKind() != ElementKind.RESOURCE_VARIABLE)
            return scan(e.getEnclosedElements(), p);
        else
            return visitUnknown(e, p);
    }

    /**
     * {@inheritDoc} This implementation scans the parameters.
     *
     * <p>
     *  {@inheritDoc}此实现扫描参数。
     * 
     * 
     * @param e  {@inheritDoc}
     * @param p  {@inheritDoc}
     * @return the result of scanning
     */
    public R visitExecutable(ExecutableElement e, P p) {
        return scan(e.getParameters(), p);
    }

    /**
     * {@inheritDoc} This implementation scans the enclosed elements.
     *
     * <p>
     *  {@inheritDoc}此实现扫描封闭的元素。
     * 
     * @param e  {@inheritDoc}
     * @param p  {@inheritDoc}
     * @return the result of scanning
     */
    public R visitTypeParameter(TypeParameterElement e, P p) {
        return scan(e.getEnclosedElements(), p);
    }
}
