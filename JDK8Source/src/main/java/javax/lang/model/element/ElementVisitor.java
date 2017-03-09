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

import javax.lang.model.util.*;

/**
 * A visitor of program elements, in the style of the visitor design
 * pattern.  Classes implementing this interface are used to operate
 * on an element when the kind of element is unknown at compile time.
 * When a visitor is passed to an element's {@link Element#accept
 * accept} method, the <tt>visit<i>XYZ</i></tt> method most applicable
 * to that element is invoked.
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
 *  程序元素的访客,在访客设计模式的风格。实现此接口的类用于在元素在编译时未知的情况下对元素进行操作。
 * 当访问者传递给元素的{@link Element#accept accept}方法时,会调用最适合该元素的<tt>访问<i> XYZ </i> </tt>方法。
 * 
 *  <p>实现此接口的类可能会抛出一个{@code NullPointerException},如果附加参数{@code p}是{@code null};有关详细信息,请参阅实现类的文档。
 * 
 *  <p> <b>警告：</b>可能的方法将被添加到此接口,以适应新的,目前未知的语言结构添加到未来版本的Java和贸易;编程语言。因此,直接实现此接口的访问者类可能与未来版本的平台不兼容。
 * 为了避免这种源不兼容,鼓励访问者实现扩展实现此接口的适当的抽象访问类。然而,API通常应该使用这个访问者接口作为参数,返回类型等的类型,而不是一个抽象类。
 * 
 * <p>请注意,如果添加为<em>默认方法</em>,则可以以源<em>兼容</em>方式添加适应新语言结构的方法。
 * 但是,默认方法仅适用于Java SE 8和更高版本,并且Java SE 8中捆绑的{@code javax.lang.model。*}包也需要在Java SE 7上运行。
 * 因此,默认方法<em >不能</em>用于扩展{@code javax.lang.model。*}以涵盖Java SE 8语言功能。
 * 但是,在仅需要在Java SE 8和更高版本的平台上运行的{@code javax.lang.model。*}软件包的后续版本中可以使用默认方法。
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
 * @see AbstractElementVisitor6
 * @see AbstractElementVisitor7
 * @since 1.6
 */
public interface ElementVisitor<R, P> {
    /**
     * Visits an element.
     * <p>
     *  访问元素。
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visit(Element e, P p);

    /**
     * A convenience method equivalent to {@code v.visit(e, null)}.
     * <p>
     *  一个相当于{@code v.visit(e,null)}的方便方法。
     * 
     * 
     * @param e  the element to visit
     * @return a visitor-specified result
     */
    R visit(Element e);

    /**
     * Visits a package element.
     * <p>
     *  访问包元素。
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitPackage(PackageElement e, P p);

    /**
     * Visits a type element.
     * <p>
     *  访问类型元素。
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitType(TypeElement e, P p);

    /**
     * Visits a variable element.
     * <p>
     *  访问可变元素。
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitVariable(VariableElement e, P p);

    /**
     * Visits an executable element.
     * <p>
     *  访问可执行元素。
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitExecutable(ExecutableElement e, P p);

    /**
     * Visits a type parameter element.
     * <p>
     *  访问类型参数元素。
     * 
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     */
    R visitTypeParameter(TypeParameterElement e, P p);

    /**
     * Visits an unknown kind of element.
     * This can occur if the language evolves and new kinds
     * of elements are added to the {@code Element} hierarchy.
     *
     * <p>
     *  访问一种未知的元素。如果语言演变并且将新种类的元素添加到{@code Element}层次结构中,则可能发生这种情况。
     * 
     * @param e  the element to visit
     * @param p  a visitor-specified parameter
     * @return a visitor-specified result
     * @throws UnknownElementException
     *  a visitor implementation may optionally throw this exception
     */
    R visitUnknown(Element e, P p);
}
