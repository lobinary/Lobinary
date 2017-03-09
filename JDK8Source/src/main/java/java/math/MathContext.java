/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2007, Oracle and/or its affiliates. All rights reserved.
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

/*
 * Portions Copyright IBM Corporation, 1997, 2001. All Rights Reserved.
 * <p>
 *  部分版权所有IBM Corporation,1997,2001.保留所有权利。
 * 
 */

package java.math;
import java.io.*;

/**
 * Immutable objects which encapsulate the context settings which
 * describe certain rules for numerical operators, such as those
 * implemented by the {@link BigDecimal} class.
 *
 * <p>The base-independent settings are:
 * <ol>
 * <li>{@code precision}:
 * the number of digits to be used for an operation; results are
 * rounded to this precision
 *
 * <li>{@code roundingMode}:
 * a {@link RoundingMode} object which specifies the algorithm to be
 * used for rounding.
 * </ol>
 *
 * <p>
 *  不可变对象,其封装描述数值运算符的某些规则的上下文设置,例如由{@link BigDecimal}类实现的那些规则。
 * 
 *  <p>与基础无关的设置包括：
 * <ol>
 *  <li> {@ code precision}：用于操作的数字位数;结果四舍五入到这个精度
 * 
 *  <li> {@ code roundingMode}：一个{@link RoundingMode}对象,它指定要用于舍入的算法。
 * </ol>
 * 
 * 
 * @see     BigDecimal
 * @see     RoundingMode
 * @author  Mike Cowlishaw
 * @author  Joseph D. Darcy
 * @since 1.5
 */

public final class MathContext implements Serializable {

    /* ----- Constants ----- */

    // defaults for constructors
    private static final int DEFAULT_DIGITS = 9;
    private static final RoundingMode DEFAULT_ROUNDINGMODE = RoundingMode.HALF_UP;
    // Smallest values for digits (Maximum is Integer.MAX_VALUE)
    private static final int MIN_DIGITS = 0;

    // Serialization version
    private static final long serialVersionUID = 5579720004786848255L;

    /* ----- Public Properties ----- */
    /**
     *  A {@code MathContext} object whose settings have the values
     *  required for unlimited precision arithmetic.
     *  The values of the settings are:
     *  <code>
     *  precision=0 roundingMode=HALF_UP
     *  </code>
     * <p>
     *  一个{@code MathContext}对象,其设置具有无限精度算术所需的值。设置的值为：
     * <code>
     *  precision = 0 roundingMode = HALF_UP
     * </code>
     */
    public static final MathContext UNLIMITED =
        new MathContext(0, RoundingMode.HALF_UP);

    /**
     *  A {@code MathContext} object with a precision setting
     *  matching the IEEE 754R Decimal32 format, 7 digits, and a
     *  rounding mode of {@link RoundingMode#HALF_EVEN HALF_EVEN}, the
     *  IEEE 754R default.
     * <p>
     *  一个{@code MathContext}对象,精度设置与IEEE 754R Decimal32格式(7位数)和舍入模式{@link RoundingMode#HALF_EVEN HALF_EVEN}
     * (IEEE 754R默认值)相匹配。
     * 
     */
    public static final MathContext DECIMAL32 =
        new MathContext(7, RoundingMode.HALF_EVEN);

    /**
     *  A {@code MathContext} object with a precision setting
     *  matching the IEEE 754R Decimal64 format, 16 digits, and a
     *  rounding mode of {@link RoundingMode#HALF_EVEN HALF_EVEN}, the
     *  IEEE 754R default.
     * <p>
     *  具有与IEEE 754R Decimal64格式(16位数)匹配的精度设置的{@code MathContext}对象和{@link RoundingMode#HALF_EVEN HALF_EVEN}
     * 的舍入模式,IEEE 754R默认值。
     * 
     */
    public static final MathContext DECIMAL64 =
        new MathContext(16, RoundingMode.HALF_EVEN);

    /**
     *  A {@code MathContext} object with a precision setting
     *  matching the IEEE 754R Decimal128 format, 34 digits, and a
     *  rounding mode of {@link RoundingMode#HALF_EVEN HALF_EVEN}, the
     *  IEEE 754R default.
     * <p>
     *  一个{@code MathContext}对象,精度设置与IEEE 754R Decimal128格式匹配,为34位数,舍入模式为{@link RoundingMode#HALF_EVEN HALF_EVEN}
     * ,默认值为IEEE 754R。
     * 
     */
    public static final MathContext DECIMAL128 =
        new MathContext(34, RoundingMode.HALF_EVEN);

    /* ----- Shared Properties ----- */
    /**
     * The number of digits to be used for an operation.  A value of 0
     * indicates that unlimited precision (as many digits as are
     * required) will be used.  Note that leading zeros (in the
     * coefficient of a number) are never significant.
     *
     * <p>{@code precision} will always be non-negative.
     *
     * <p>
     * 要用于操作的数字位数。值为0表示将使用不受限制的精度(所需的位数)。注意,前导零(在数字的系数中)从不重要。
     * 
     *  <p> {@ code precision}将始终为非负数。
     * 
     * 
     * @serial
     */
    final int precision;

    /**
     * The rounding algorithm to be used for an operation.
     *
     * <p>
     *  用于操作的舍入算法。
     * 
     * 
     * @see RoundingMode
     * @serial
     */
    final RoundingMode roundingMode;

    /* ----- Constructors ----- */

    /**
     * Constructs a new {@code MathContext} with the specified
     * precision and the {@link RoundingMode#HALF_UP HALF_UP} rounding
     * mode.
     *
     * <p>
     *  构造具有指定精度和{@link RoundingMode#HALF_UP HALF_UP}舍入模式的新{@code MathContext}。
     * 
     * 
     * @param setPrecision The non-negative {@code int} precision setting.
     * @throws IllegalArgumentException if the {@code setPrecision} parameter is less
     *         than zero.
     */
    public MathContext(int setPrecision) {
        this(setPrecision, DEFAULT_ROUNDINGMODE);
        return;
    }

    /**
     * Constructs a new {@code MathContext} with a specified
     * precision and rounding mode.
     *
     * <p>
     *  构造具有指定精度和舍入模式的新{@code MathContext}。
     * 
     * 
     * @param setPrecision The non-negative {@code int} precision setting.
     * @param setRoundingMode The rounding mode to use.
     * @throws IllegalArgumentException if the {@code setPrecision} parameter is less
     *         than zero.
     * @throws NullPointerException if the rounding mode argument is {@code null}
     */
    public MathContext(int setPrecision,
                       RoundingMode setRoundingMode) {
        if (setPrecision < MIN_DIGITS)
            throw new IllegalArgumentException("Digits < 0");
        if (setRoundingMode == null)
            throw new NullPointerException("null RoundingMode");

        precision = setPrecision;
        roundingMode = setRoundingMode;
        return;
    }

    /**
     * Constructs a new {@code MathContext} from a string.
     *
     * The string must be in the same format as that produced by the
     * {@link #toString} method.
     *
     * <p>An {@code IllegalArgumentException} is thrown if the precision
     * section of the string is out of range ({@code < 0}) or the string is
     * not in the format created by the {@link #toString} method.
     *
     * <p>
     *  从字符串构造一个新的{@code MathContext}。
     * 
     *  该字符串必须与{@link #toString}方法生成的格式相同。
     * 
     *  <p>如果字符串的precision部分超出范围({@code <0})或字符串不是{@link #toString}方法创建的格式,则会抛出{@code IllegalArgumentException}。
     * 
     * 
     * @param val The string to be parsed
     * @throws IllegalArgumentException if the precision section is out of range
     * or of incorrect format
     * @throws NullPointerException if the argument is {@code null}
     */
    public MathContext(String val) {
        boolean bad = false;
        int setPrecision;
        if (val == null)
            throw new NullPointerException("null String");
        try { // any error here is a string format problem
            if (!val.startsWith("precision=")) throw new RuntimeException();
            int fence = val.indexOf(' ');    // could be -1
            int off = 10;                     // where value starts
            setPrecision = Integer.parseInt(val.substring(10, fence));

            if (!val.startsWith("roundingMode=", fence+1))
                throw new RuntimeException();
            off = fence + 1 + 13;
            String str = val.substring(off, val.length());
            roundingMode = RoundingMode.valueOf(str);
        } catch (RuntimeException re) {
            throw new IllegalArgumentException("bad string format");
        }

        if (setPrecision < MIN_DIGITS)
            throw new IllegalArgumentException("Digits < 0");
        // the other parameters cannot be invalid if we got here
        precision = setPrecision;
    }

    /**
     * Returns the {@code precision} setting.
     * This value is always non-negative.
     *
     * <p>
     *  返回{@code precision}设置。此值始终为非负数。
     * 
     * 
     * @return an {@code int} which is the value of the {@code precision}
     *         setting
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * Returns the roundingMode setting.
     * This will be one of
     * {@link  RoundingMode#CEILING},
     * {@link  RoundingMode#DOWN},
     * {@link  RoundingMode#FLOOR},
     * {@link  RoundingMode#HALF_DOWN},
     * {@link  RoundingMode#HALF_EVEN},
     * {@link  RoundingMode#HALF_UP},
     * {@link  RoundingMode#UNNECESSARY}, or
     * {@link  RoundingMode#UP}.
     *
     * <p>
     *  返回roundingMode设置。
     * 这将是{@link RoundingMode#CEILING},{@link RoundingMode#DOWN},{@link RoundingMode#FLOOR},{@link RoundingMode#HALF_DOWN}
     * ,{@link RoundingMode#HALF_EVEN},{@link RoundingMode# HALF_UP},{@link RoundingMode#UNNECESSARY}或{@link RoundingMode#UP}
     * 。
     *  返回roundingMode设置。
     * 
     * 
     * @return a {@code RoundingMode} object which is the value of the
     *         {@code roundingMode} setting
     */

    public RoundingMode getRoundingMode() {
        return roundingMode;
    }

    /**
     * Compares this {@code MathContext} with the specified
     * {@code Object} for equality.
     *
     * <p>
     *  将此{@code MathContext}与指定的{@code Object}比较以确保相等。
     * 
     * 
     * @param  x {@code Object} to which this {@code MathContext} is to
     *         be compared.
     * @return {@code true} if and only if the specified {@code Object} is
     *         a {@code MathContext} object which has exactly the same
     *         settings as this object
     */
    public boolean equals(Object x){
        MathContext mc;
        if (!(x instanceof MathContext))
            return false;
        mc = (MathContext) x;
        return mc.precision == this.precision
            && mc.roundingMode == this.roundingMode; // no need for .equals()
    }

    /**
     * Returns the hash code for this {@code MathContext}.
     *
     * <p>
     *  返回此{@code MathContext}的哈希码。
     * 
     * 
     * @return hash code for this {@code MathContext}
     */
    public int hashCode() {
        return this.precision + roundingMode.hashCode() * 59;
    }

    /**
     * Returns the string representation of this {@code MathContext}.
     * The {@code String} returned represents the settings of the
     * {@code MathContext} object as two space-delimited words
     * (separated by a single space character, <tt>'&#92;u0020'</tt>,
     * and with no leading or trailing white space), as follows:
     * <ol>
     * <li>
     * The string {@code "precision="}, immediately followed
     * by the value of the precision setting as a numeric string as if
     * generated by the {@link Integer#toString(int) Integer.toString}
     * method.
     *
     * <li>
     * The string {@code "roundingMode="}, immediately
     * followed by the value of the {@code roundingMode} setting as a
     * word.  This word will be the same as the name of the
     * corresponding public constant in the {@link RoundingMode}
     * enum.
     * </ol>
     * <p>
     * For example:
     * <pre>
     * precision=9 roundingMode=HALF_UP
     * </pre>
     *
     * Additional words may be appended to the result of
     * {@code toString} in the future if more properties are added to
     * this class.
     *
     * <p>
     * 返回此{@code MathContext}的字符串表示形式。
     * 返回的{@code String}将{@code MathContext}对象的设置表示为两个空格分隔的单词(由单个空格字符<tt>'\ u0020'</tt>分隔,并且没有前导或尾随空白),如下：。
     * <ol>
     * <li>
     *  字符串{@code"precision ="},紧接着是作为数字字符串的精度设置的值,如同由{@link Integer#toString(int)Integer.toString}方法生成的。
     * 
     * <li>
     *  字符串{@code"roundingMode ="},紧接着是{@code roundingMode}设置的值作为单词。
     * 这个单词将与{@link RoundingMode}枚举中的相应公共常量的名称相同。
     * </ol>
     * <p>
     * 
     * @return a {@code String} representing the context settings
     */
    public java.lang.String toString() {
        return "precision=" +           precision + " " +
               "roundingMode=" +        roundingMode.toString();
    }

    // Private methods

    /**
     * Reconstitute the {@code MathContext} instance from a stream (that is,
     * deserialize it).
     *
     * <p>
     *  例如：
     * <pre>
     *  precision = 9 roundingMode = HALF_UP
     * </pre>
     * 
     * 
     * @param s the stream being read.
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();     // read in all fields
        // validate possibly bad fields
        if (precision < MIN_DIGITS) {
            String message = "MathContext: invalid digits in stream";
            throw new java.io.StreamCorruptedException(message);
        }
        if (roundingMode == null) {
            String message = "MathContext: null roundingMode in stream";
            throw new java.io.StreamCorruptedException(message);
        }
    }

}
