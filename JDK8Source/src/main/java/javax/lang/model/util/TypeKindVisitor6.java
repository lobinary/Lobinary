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
import javax.lang.model.type.*;
import static javax.lang.model.SourceVersion.*;

/**
 * A visitor of types based on their {@linkplain TypeKind kind} with
 * default behavior appropriate for the {@link SourceVersion#RELEASE_6
 * RELEASE_6} source version.  For {@linkplain
 * TypeMirror types} <tt><i>XYZ</i></tt> that may have more than one
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
 * #visitUnknown visitUnknown} method.  A new type kind visitor class
 * will also be introduced to correspond to the new language level;
 * this visitor will have different default behavior for the visit
 * method in question.  When the new visitor is introduced, all or
 * portions of this visitor may be deprecated.
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
 *  基于其{@linkplain TypeKind kind}类型的访问者,其默认行为适合{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 * 对于可能有多种类型的{@linkplain TypeMirror types} <tt> <i> XYZ </i> </tt>,<tt>请访问<i> XYZ </i> </tt>类委托给<tt>访问对应
 * 于第一个参数种类的<i> XYZKind </i> </tt>方法。
 *  基于其{@linkplain TypeKind kind}类型的访问者,其默认行为适合{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 *  <tt>访问<z> XYZKind </i> </tt>方法调用{@link #defaultAction defaultAction},将其参数传递给{@code defaultAction}的相应
 * 参数。
 *  基于其{@linkplain TypeKind kind}类型的访问者,其默认行为适合{@link SourceVersion#RELEASE_6 RELEASE_6}源版本。
 * 
 *  <p>此类别中的方法可能会根据其总合同被覆盖。注意,在具体子类中使用{@link java.lang.Override @Override}注释方法将有助于确保方法按预期被覆盖。
 * 
 *  <p> <b>警告：</b>此类别实施的{@code TypeVisitor}接口将来可能会添加方法,以容纳添加到未来版本的Java和贸易中的新的,当前未知的语言结构;编程语言。
 * 因此,名称以{@code"visit"}开头的方法可能会在将来添加到此类中;为避免不兼容性,扩展此类的类不应声明任何名称以{@code"visit"}开头的实例方法。
 * 
 * <p>添加这种新的访问方法时,此类中的默认实现将是调用{@link #visitUnknown visitUnknown}方法。
 * 新的类型访问类也将被引入以对应于新的语言级别;此访问者将对所讨论的访问方法具有不同的默认行为。当新访问者被引入时,该访问者的全部或部分可能被弃用。
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
 * @see TypeKindVisitor7
 * @see TypeKindVisitor8
 * @since 1.6
 */
@SupportedSourceVersion(RELEASE_6)
public class TypeKindVisitor6<R, P> extends SimpleTypeVisitor6<R, P> {
    /**
     * Constructor for concrete subclasses to call; uses {@code null}
     * for the default value.
     * <p>
     *  构建具体子类调用;使用{@code null}作为默认值。
     * 
     */
    protected TypeKindVisitor6() {
        super(null);
    }


    /**
     * Constructor for concrete subclasses to call; uses the argument
     * for the default value.
     *
     * <p>
     *  构建具体子类调用;使用该参数作为默认值。
     * 
     * 
     * @param defaultValue the value to assign to {@link #DEFAULT_VALUE}
     */
    protected TypeKindVisitor6(R defaultValue) {
        super(defaultValue);
    }

    /**
     * Visits a primitive type, dispatching to the visit method for
     * the specific {@linkplain TypeKind kind} of primitive type:
     * {@code BOOLEAN}, {@code BYTE}, etc.
     *
     * <p>
     *  访问原始类型,分派到{@linkplain TypeKind种类}原始类型{@code BOOLEAN},{@code BYTE}等的访问方法。
     * 
     * 
     * @param t {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of the kind-specific visit method
     */
    @Override
    public R visitPrimitive(PrimitiveType t, P p) {
        TypeKind k = t.getKind();
        switch (k) {
        case BOOLEAN:
            return visitPrimitiveAsBoolean(t, p);

        case BYTE:
            return visitPrimitiveAsByte(t, p);

        case SHORT:
            return visitPrimitiveAsShort(t, p);

        case INT:
            return visitPrimitiveAsInt(t, p);

        case LONG:
            return visitPrimitiveAsLong(t, p);

        case CHAR:
            return visitPrimitiveAsChar(t, p);

        case FLOAT:
            return visitPrimitiveAsFloat(t, p);

        case DOUBLE:
            return visitPrimitiveAsDouble(t, p);

        default:
            throw new AssertionError("Bad kind " + k + " for PrimitiveType" + t);
        }
    }

    /**
     * Visits a {@code BOOLEAN} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code BOOLEAN}原始类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsBoolean(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@code BYTE} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code BYTE}原始类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsByte(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@code SHORT} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code SHORT}原语类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsShort(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits an {@code INT} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     * 通过调用{@code defaultAction}访问{@code INT}基本类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsInt(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@code LONG} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code LONG}基本类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsLong(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@code CHAR} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code CHAR}基本类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsChar(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@code FLOAT} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code FLOAT}原始类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsFloat(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@code DOUBLE} primitive type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@code DOUBLE}基元类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitPrimitiveAsDouble(PrimitiveType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@link NoType} instance, dispatching to the visit method for
     * the specific {@linkplain TypeKind kind} of pseudo-type:
     * {@code VOID}, {@code PACKAGE}, or {@code NONE}.
     *
     * <p>
     *  访问{@link NoType}实例,将其分派到特定{@linkplain TypeKind kind}伪类型的访问方法：{@code VOID},{@code PACKAGE}或{@code NONE}
     * 。
     * 
     * 
     * @param t {@inheritDoc}
     * @param p {@inheritDoc}
     * @return  the result of the kind-specific visit method
     */
    @Override
    public R visitNoType(NoType t, P p) {
        TypeKind k = t.getKind();
        switch (k) {
        case VOID:
            return visitNoTypeAsVoid(t, p);

        case PACKAGE:
            return visitNoTypeAsPackage(t, p);

        case NONE:
            return visitNoTypeAsNone(t, p);

        default:
            throw new AssertionError("Bad kind " + k + " for NoType" + t);
        }
    }

    /**
     * Visits a {@link TypeKind#VOID VOID} pseudo-type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@link TypeKind#VOID VOID}伪类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitNoTypeAsVoid(NoType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@link TypeKind#PACKAGE PACKAGE} pseudo-type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@link TypeKind#PACKAGE PACKAGE}伪类型。
     * 
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitNoTypeAsPackage(NoType t, P p) {
        return defaultAction(t, p);
    }

    /**
     * Visits a {@link TypeKind#NONE NONE} pseudo-type by calling
     * {@code defaultAction}.
     *
     * <p>
     *  通过调用{@code defaultAction}访问{@link TypeKind#NONE NONE}伪类型。
     * 
     * @param t the type to visit
     * @param p a visitor-specified parameter
     * @return  the result of {@code defaultAction}
     */
    public R visitNoTypeAsNone(NoType t, P p) {
        return defaultAction(t, p);
    }
}
