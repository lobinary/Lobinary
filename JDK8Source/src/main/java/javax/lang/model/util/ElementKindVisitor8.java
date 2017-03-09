/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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
import static javax.lang.model.SourceVersion.*;
import javax.lang.model.SourceVersion;

/**
 * A visitor of program elements based on their {@linkplain
 * ElementKind kind} with default behavior appropriate for the {@link
 * SourceVersion#RELEASE_8 RELEASE_8} source version.  For {@linkplain
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
 *  基于其{@linkplain ElementKind kind}的程序元素的访问者,其默认行为适合{@link SourceVersion#RELEASE_8 RELEASE_8}源版本。
 * 对于可能有多种类型的{@linkplain Element elements} <tt> <i> XYZ </i> </tt>,<tt>访问<i> XYZ </i> </tt>类委托给<tt>访问对应于
 * 第一个参数种类的<i> XYZKind </i> </tt>方法。
 *  基于其{@linkplain ElementKind kind}的程序元素的访问者,其默认行为适合{@link SourceVersion#RELEASE_8 RELEASE_8}源版本。
 *  <tt>访问<z> XYZKind </i> </tt>方法调用{@link #defaultAction defaultAction},将其参数传递给{@code defaultAction}的相应
 * 参数。
 *  基于其{@linkplain ElementKind kind}的程序元素的访问者,其默认行为适合{@link SourceVersion#RELEASE_8 RELEASE_8}源版本。
 * 
 *  <p>此类别中的方法可能会根据其总合同被覆盖。注意,在具体子类中使用{@link java.lang.Override @Override}注释方法将有助于确保方法按预期被覆盖。
 * 
 * <p> <b>警告：</b>此类别实施的{@code ElementVisitor}界面可能已添加了方法,或者在此情况下使用的{@code ElementKind} {@code枚举}它在未来适应新的,
 * 目前未知的语言结构添加到未来版本的Java&贸易;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 * 
 * @param <R> the return type of this visitor's methods.  Use {@link
 *            Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter.
 *
 * @see ElementKindVisitor6
 * @see ElementKindVisitor7
 * @since 1.8
 */
@SupportedSourceVersion(RELEASE_8)
public class ElementKindVisitor8<R, P> extends ElementKindVisitor7<R, P> {
    /**
     * Constructor for concrete subclasses; uses {@code null} for the
     * default value.
     * <p>
     *  <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
     * 还将引入新的抽象元素类访问者类来对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
     * 
     *  <p>请注意,在访问者类中添加一个新访问方法的默认实现,而不是直接在访问者界面中添加<em>默认方法</em>,因为Java SE 8语言功能不能用于此版本的API,因为此版本需要在Java SE 7
     * 实现上运行。
     * 仅需要在Java SE 8和更高版本上运行的API的未来版本可以在这种情况下利用默认方法。
     * 
     */
    protected ElementKindVisitor8() {
        super(null);
    }

    /**
     * Constructor for concrete subclasses; uses the argument for the
     * default value.
     *
     * <p>
     * 
     * @param defaultValue the value to assign to {@link #DEFAULT_VALUE}
     */
    protected ElementKindVisitor8(R defaultValue) {
        super(defaultValue);
    }
}
