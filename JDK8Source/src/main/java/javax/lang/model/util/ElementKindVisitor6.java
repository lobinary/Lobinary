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
import static javax.lang.model.element.ElementKind.*;
import javax.annotation.processing.SupportedSourceVersion;
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.SourceVersion;


/**
 * A visitor of program elements based on their {@linkplain
 * ElementKind kind} with default behavior appropriate for the {@link
 * SourceVersion#RELEASE_6 RELEASE_6} source version.  For {@linkplain
 * Element elements} <tt><i>XYZ</i></tt> that may have more than one
 * kind, the <tt>visit<i>XYZ</i></tt> methods in this class delegate
 * to the <tt>visit<i>XYZKind</i></tt> method corresponding to the
 * first argument's kind.  The <tt>visit<i>XYZKind</i></tt> methods
 * call {@link #defaultAction defaultAction}, passing their arguments
 * to {@code defaultAction}'s corresponding parameters.
 *
 * <p> Methods in this class may be overridden subject to their
 * general contract.  Note that annotating methods in concrete
 * subclasses with {@link java.lang.Override @Override} will help
 * ensure that methods are overridden as intended.
 *
 * <p> <b>WARNING:</b> The {@code ElementVisitor} interface
 * implemented by this class may have methods added to it or the
 * {@code ElementKind} {@code enum} used in this case may have
 * constants added to it in the future to accommodate new, currently
 * unknown, language structures added to future versions of the
 * Java&trade; programming language.  Therefore, methods whose names
 * begin with {@code "visit"} may be added to this class in the
 * future; to avoid incompatibilities, classes which extend this class
 * should not declare any instance methods with names beginning with
 * {@code "visit"}.
 *
 * <p>When such a new visit method is added, the default
 * implementation in this class will be to call the {@link
 * #visitUnknown visitUnknown} method.  A new abstract element kind
 * visitor class will also be introduced to correspond to the new
 * language level; this visitor will have different default behavior
 * for the visit method in question.  When the new visitor is
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
 *  基于{@linkplain ElementKind kind}的程序元素的访问者,其默认行为适用于{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 * 对于可能有多种类型的{@linkplain Element elements} <tt> <i> XYZ </i> </tt>,<tt>访问<i> XYZ </i> </tt>类委托给<tt>访问对应于
 * 第一个参数种类的<i> XYZKind </i> </tt>方法。
 *  基于{@linkplain ElementKind kind}的程序元素的访问者,其默认行为适用于{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 *  <tt>访问<z> XYZKind </i> </tt>方法调用{@link #defaultAction defaultAction},将其参数传递给{@code defaultAction}的相应
 * 参数。
 *  基于{@linkplain ElementKind kind}的程序元素的访问者,其默认行为适用于{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 * 
 *  <p>此类别中的方法可能会根据其总合同被覆盖。注意,在具体子类中使用{@link java.lang.Override @Override}注释方法将有助于确保方法按预期被覆盖。
 * 
 * <p> <b>警告：</b>此类别实施的{@code ElementVisitor}界面可能已添加了方法,或者在此情况下使用的{@code ElementKind} {@code枚举}它在未来适应新的,
 * 目前未知的语言结构添加到未来版本的Java&贸易;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 *  <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 还将引入新的抽象元素类访问者类来对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
 * 
 *  <p>请注意,在访问者类中添加一个新访问方法的默认实现,而不是直接在访问者界面中添加<em>默认方法</em>,因为Java SE 8语言功能不能用于此版本的API,因为此版本需要在Java SE 7
 * 实现上运行。
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
 * @see ElementKindVisitor7
 * @see ElementKindVisitor8
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public class ElementKindVisitor6<R, P>
                  extends SimpleElementVisitor6<R, P> {
    /**
     * Constructor for concrete subclasses; uses {@code null} for the
     * default value.
     * <p>
     * 具体子类的构造函数;使用{@code null}作为默认值。
     * 
     */
    protected ElementKindVisitor6() {
        super(null);
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
    protected ElementKindVisitor6(R defaultValue) {
        super(defaultValue);
    }

    /**
     * {@inheritDoc}
     *
     * The element argument has kind {@code PACKAGE}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  元素参数的类型为{@code PACKAGE}。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  {@inheritDoc}
     */
    @Override
    public R visitPackage(PackageElement e, P p) {
        assert e.getKind() == PACKAGE: "Bad kind on PackageElement";
        return defaultAction(e, p);
    }

    /**
     * Visits a type element, dispatching to the visit method for the
     * specific {@linkplain ElementKind kind} of type, {@code
     * ANNOTATION_TYPE}, {@code CLASS}, {@code ENUM}, or {@code
     * INTERFACE}.
     *
     * <p>
     *  访问类型元素,将其分派给特定{@linkplain ElementKind kind}类型{@code ANNOTATION_TYPE},{@code CLASS},{@code ENUM}或{@code INTERFACE}
     * 的visit方法。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of the kind-specific visit method
     */
    @Override
    public R visitType(TypeElement e, P p) {
        ElementKind k = e.getKind();
        switch(k) {
        case ANNOTATION_TYPE:
            return visitTypeAsAnnotationType(e, p);

        case CLASS:
            return visitTypeAsClass(e, p);

        case ENUM:
            return visitTypeAsEnum(e, p);

        case INTERFACE:
            return visitTypeAsInterface(e, p);

        default:
            throw new AssertionError("Bad kind " + k + " for TypeElement" + e);
        }
    }

    /**
     * Visits an {@code ANNOTATION_TYPE} type element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code ANNOTATION_TYPE}类型元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitTypeAsAnnotationType(TypeElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a {@code CLASS} type element by calling {@code
     * defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code CLASS}类型元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitTypeAsClass(TypeElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits an {@code ENUM} type element by calling {@code
     * defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code ENUM}类型元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitTypeAsEnum(TypeElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits an {@code INTERFACE} type element by calling {@code
     * defaultAction}.
     *.
     * <p>
     *  通过调用{@code defaultAction}访问{@code INTERFACE}类型元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitTypeAsInterface(TypeElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a variable element, dispatching to the visit method for
     * the specific {@linkplain ElementKind kind} of variable, {@code
     * ENUM_CONSTANT}, {@code EXCEPTION_PARAMETER}, {@code FIELD},
     * {@code LOCAL_VARIABLE}, {@code PARAMETER}, or {@code RESOURCE_VARIABLE}.
     *
     * <p>
     *  访问变量元素,分派给特定{@linkplain ElementKind kind}变量{@code ENUM_CONSTANT},{@code EXCEPTION_PARAMETER},{@code FIELD}
     * ,{@code LOCAL_VARIABLE},{@code PARAMETER}的访问方法}或{@code RESOURCE_VARIABLE}。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of the kind-specific visit method
     */
    @Override
    public R visitVariable(VariableElement e, P p) {
        ElementKind k = e.getKind();
        switch(k) {
        case ENUM_CONSTANT:
            return visitVariableAsEnumConstant(e, p);

        case EXCEPTION_PARAMETER:
            return visitVariableAsExceptionParameter(e, p);

        case FIELD:
            return visitVariableAsField(e, p);

        case LOCAL_VARIABLE:
            return visitVariableAsLocalVariable(e, p);

        case PARAMETER:
            return visitVariableAsParameter(e, p);

        case RESOURCE_VARIABLE:
            return visitVariableAsResourceVariable(e, p);

        default:
            throw new AssertionError("Bad kind " + k + " for VariableElement" + e);
        }
    }

    /**
     * Visits an {@code ENUM_CONSTANT} variable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code ENUM_CONSTANT}变量元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitVariableAsEnumConstant(VariableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits an {@code EXCEPTION_PARAMETER} variable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code EXCEPTION_PARAMETER}变量元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitVariableAsExceptionParameter(VariableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a {@code FIELD} variable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code FIELD}变量元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitVariableAsField(VariableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a {@code LOCAL_VARIABLE} variable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code LOCAL_VARIABLE}变量元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitVariableAsLocalVariable(VariableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a {@code PARAMETER} variable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code PARAMETER}变量元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitVariableAsParameter(VariableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a {@code RESOURCE_VARIABLE} variable element by calling
     * {@code visitUnknown}.
     *
     * <p>
     *  通过调用{@code visitUnknown}访问{@code RESOURCE_VARIABLE}变量元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code visitUnknown}
     *
     * @since 1.7
     */
    public R visitVariableAsResourceVariable(VariableElement e, P p) {
        return visitUnknown(e, p);
    }

    /**
     * Visits an executable element, dispatching to the visit method
     * for the specific {@linkplain ElementKind kind} of executable,
     * {@code CONSTRUCTOR}, {@code INSTANCE_INIT}, {@code METHOD}, or
     * {@code STATIC_INIT}.
     *
     * <p>
     * 访问可执行元素,将其分派给特定{@linkplain ElementKind kind}可执行文件,{@code CONSTRUCTOR},{@code INSTANCE_INIT},{@code METHOD}
     * 或{@code STATIC_INIT}的visit方法。
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of the kind-specific visit method
     */
    @Override
    public R visitExecutable(ExecutableElement e, P p) {
        ElementKind k = e.getKind();
        switch(k) {
        case CONSTRUCTOR:
            return visitExecutableAsConstructor(e, p);

        case INSTANCE_INIT:
            return visitExecutableAsInstanceInit(e, p);

        case METHOD:
            return visitExecutableAsMethod(e, p);

        case STATIC_INIT:
            return visitExecutableAsStaticInit(e, p);

        default:
            throw new AssertionError("Bad kind " + k + " for ExecutableElement" + e);
        }
    }

    /**
     * Visits a {@code CONSTRUCTOR} executable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code CONSTRUCTOR}可执行元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitExecutableAsConstructor(ExecutableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits an {@code INSTANCE_INIT} executable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code INSTANCE_INIT}可执行元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitExecutableAsInstanceInit(ExecutableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a {@code METHOD} executable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code METHOD}可执行元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitExecutableAsMethod(ExecutableElement e, P p) {
        return defaultAction(e, p);
    }

    /**
     * Visits a {@code STATIC_INIT} executable element by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code STATIC_INIT}可执行元素。
     * 
     * 
     * @param e the element to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitExecutableAsStaticInit(ExecutableElement e, P p) {
        return defaultAction(e, p);
    }


    /**
     * {@inheritDoc}
     *
     * The element argument has kind {@code TYPE_PARAMETER}.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @param e {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  {@inheritDoc}
     */
    @Override
    public R visitTypeParameter(TypeParameterElement e, P p) {
        assert e.getKind() == TYPE_PARAMETER: "Bad kind on TypeParameterElement";
        return defaultAction(e, p);
    }
}
