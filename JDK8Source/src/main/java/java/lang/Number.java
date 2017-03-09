/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * The abstract class {@code Number} is the superclass of platform
 * classes representing numeric values that are convertible to the
 * primitive types {@code byte}, {@code double}, {@code float}, {@code
 * int}, {@code long}, and {@code short}.
 *
 * The specific semantics of the conversion from the numeric value of
 * a particular {@code Number} implementation to a given primitive
 * type is defined by the {@code Number} implementation in question.
 *
 * For platform classes, the conversion is often analogous to a
 * narrowing primitive conversion or a widening primitive conversion
 * as defining in <cite>The Java&trade; Language Specification</cite>
 * for converting between primitive types.  Therefore, conversions may
 * lose information about the overall magnitude of a numeric value, may
 * lose precision, and may even return a result of a different sign
 * than the input.
 *
 * See the documentation of a given {@code Number} implementation for
 * conversion details.
 *
 * <p>
 *  抽象类{@code Number}是平台类的超类,表示可转换为原始类型{@code byte},{@code double},{@code float},{@code int},{@ code long}
 * 和{@code short}。
 * 
 *  从特定{@code Number}实现的数字值到给定原语类型的转换的特定语义由所讨论的{@code Number}实现定义。
 * 
 *  对于平台类,转换通常类似于缩小的原始转换或扩大的原始转换,如在<cite> Java&trade;语言规范</cite>用于在原始类型之间进行转换。
 * 因此,转换可能丢失关于数值的整体幅度的信息,可能失去精确度,甚至可能返回与输入不同的符号的结果。
 * 
 *  有关转化详细信息,请参阅给定{@code Number}实施的文档。
 * 
 * 
 * @author      Lee Boynton
 * @author      Arthur van Hoff
 * @jls 5.1.2 Widening Primitive Conversions
 * @jls 5.1.3 Narrowing Primitive Conversions
 * @since   JDK1.0
 */
public abstract class Number implements java.io.Serializable {
    /**
     * Returns the value of the specified number as an {@code int},
     * which may involve rounding or truncation.
     *
     * <p>
     *  以{@code int}形式返回指定数字的值,这可能涉及舍入或截断。
     * 
     * 
     * @return  the numeric value represented by this object after conversion
     *          to type {@code int}.
     */
    public abstract int intValue();

    /**
     * Returns the value of the specified number as a {@code long},
     * which may involve rounding or truncation.
     *
     * <p>
     *  将指定数字的值作为{@code long}返回,这可能涉及舍入或截断。
     * 
     * 
     * @return  the numeric value represented by this object after conversion
     *          to type {@code long}.
     */
    public abstract long longValue();

    /**
     * Returns the value of the specified number as a {@code float},
     * which may involve rounding.
     *
     * <p>
     *  将指定数字的值作为{@code float}返回,这可能涉及舍入。
     * 
     * 
     * @return  the numeric value represented by this object after conversion
     *          to type {@code float}.
     */
    public abstract float floatValue();

    /**
     * Returns the value of the specified number as a {@code double},
     * which may involve rounding.
     *
     * <p>
     *  以{@code double}返回指定数字的值,这可能涉及舍入。
     * 
     * 
     * @return  the numeric value represented by this object after conversion
     *          to type {@code double}.
     */
    public abstract double doubleValue();

    /**
     * Returns the value of the specified number as a {@code byte},
     * which may involve rounding or truncation.
     *
     * <p>This implementation returns the result of {@link #intValue} cast
     * to a {@code byte}.
     *
     * <p>
     * 将指定数字的值返回为{@code byte},这可能涉及舍入或截断。
     * 
     *  <p>此实现将{@link #intValue}强制转换的结果返回到{@code byte}。
     * 
     * 
     * @return  the numeric value represented by this object after conversion
     *          to type {@code byte}.
     * @since   JDK1.1
     */
    public byte byteValue() {
        return (byte)intValue();
    }

    /**
     * Returns the value of the specified number as a {@code short},
     * which may involve rounding or truncation.
     *
     * <p>This implementation returns the result of {@link #intValue} cast
     * to a {@code short}.
     *
     * <p>
     *  将指定数字的值作为{@code short}返回,这可能涉及舍入或截断。
     * 
     * 
     * @return  the numeric value represented by this object after conversion
     *          to type {@code short}.
     * @since   JDK1.1
     */
    public short shortValue() {
        return (short)intValue();
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -8742448824652078965L;
}
