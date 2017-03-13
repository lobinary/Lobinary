/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2013, Oracle and/or its affiliates. All rights reserved.
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

import sun.misc.FloatingDecimal;
import sun.misc.FloatConsts;
import sun.misc.DoubleConsts;

/**
 * The {@code Float} class wraps a value of primitive type
 * {@code float} in an object. An object of type
 * {@code Float} contains a single field whose type is
 * {@code float}.
 *
 * <p>In addition, this class provides several methods for converting a
 * {@code float} to a {@code String} and a
 * {@code String} to a {@code float}, as well as other
 * constants and methods useful when dealing with a
 * {@code float}.
 *
 * <p>
 *  {@code Float}类在对象中封装了一个原始类型{@code float}的值一个类型为{@code Float}的对象包含一个类型为{@code float}的单个字段,
 * 
 *  <p>此外,此类提供了几种方法,用于将{@code float}转换为{@code String}和{@code String}转换为{@code float},以及其他常量和方法处理一个{@code float}
 * 。
 * 
 * 
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Joseph D. Darcy
 * @since JDK1.0
 */
public final class Float extends Number implements Comparable<Float> {
    /**
     * A constant holding the positive infinity of type
     * {@code float}. It is equal to the value returned by
     * {@code Float.intBitsToFloat(0x7f800000)}.
     * <p>
     *  一个保持类型为{@code float}的正无穷大的常量它等于{@code FloatintBitsToFloat(0x7f800000)}返回的值}
     * 
     */
    public static final float POSITIVE_INFINITY = 1.0f / 0.0f;

    /**
     * A constant holding the negative infinity of type
     * {@code float}. It is equal to the value returned by
     * {@code Float.intBitsToFloat(0xff800000)}.
     * <p>
     * 一个保持类型为{@code float}的负无穷大的常数它等于{@code FloatintBitsToFloat(0xff800000)}返回的值}
     * 
     */
    public static final float NEGATIVE_INFINITY = -1.0f / 0.0f;

    /**
     * A constant holding a Not-a-Number (NaN) value of type
     * {@code float}.  It is equivalent to the value returned by
     * {@code Float.intBitsToFloat(0x7fc00000)}.
     * <p>
     *  一个持有类型为{@code float}的Not-a-Number(NaN)值的常量它等价于{@code FloatintBitsToFloat(0x7fc00000)}返回的值
     * 
     */
    public static final float NaN = 0.0f / 0.0f;

    /**
     * A constant holding the largest positive finite value of type
     * {@code float}, (2-2<sup>-23</sup>)&middot;2<sup>127</sup>.
     * It is equal to the hexadecimal floating-point literal
     * {@code 0x1.fffffeP+127f} and also equal to
     * {@code Float.intBitsToFloat(0x7f7fffff)}.
     * <p>
     *  一个保持类型为{@code float},(2-2 <sup> -23 </sup>)和middot的最大正有限值的常量; 2 <sup> 127 </sup>它等于十六进制浮点文字{@code 0x1fffffeP + 127f}
     * ,也等于{@code FloatintBitsToFloat(0x7f7fffff)}。
     * 
     */
    public static final float MAX_VALUE = 0x1.fffffeP+127f; // 3.4028235e+38f

    /**
     * A constant holding the smallest positive normal value of type
     * {@code float}, 2<sup>-126</sup>.  It is equal to the
     * hexadecimal floating-point literal {@code 0x1.0p-126f} and also
     * equal to {@code Float.intBitsToFloat(0x00800000)}.
     *
     * <p>
     *  一个保持类型{@code float},2 <sup> -126 </sup>的最小正正常数的常数它等于十六进制浮点文本{@code 0x10p-126f},也等于{@code FloatintBitsToFloat(0x00800000)}
     * 。
     * 
     * 
     * @since 1.6
     */
    public static final float MIN_NORMAL = 0x1.0p-126f; // 1.17549435E-38f

    /**
     * A constant holding the smallest positive nonzero value of type
     * {@code float}, 2<sup>-149</sup>. It is equal to the
     * hexadecimal floating-point literal {@code 0x0.000002P-126f}
     * and also equal to {@code Float.intBitsToFloat(0x1)}.
     * <p>
     * 一个保持类型为{@code float},2 <sup> -149 </sup>的最小正非零值的常量它等于十六进制浮点文本{@code 0x0000002P-126f},也等于{@code FloatintBitsToFloat(0x1)}
     * 。
     * 
     */
    public static final float MIN_VALUE = 0x0.000002P-126f; // 1.4e-45f

    /**
     * Maximum exponent a finite {@code float} variable may have.  It
     * is equal to the value returned by {@code
     * Math.getExponent(Float.MAX_VALUE)}.
     *
     * <p>
     *  最大指数一个有限的{@code float}变量可能有它等于{@code MathgetExponent(FloatMAX_VALUE)}返回的值}
     * 
     * 
     * @since 1.6
     */
    public static final int MAX_EXPONENT = 127;

    /**
     * Minimum exponent a normalized {@code float} variable may have.
     * It is equal to the value returned by {@code
     * Math.getExponent(Float.MIN_NORMAL)}.
     *
     * <p>
     *  一个标准化的{@code float}变量的最小指数它等于{@code MathgetExponent(FloatMIN_NORMAL)}返回的值}
     * 
     * 
     * @since 1.6
     */
    public static final int MIN_EXPONENT = -126;

    /**
     * The number of bits used to represent a {@code float} value.
     *
     * <p>
     *  用于表示{@code float}值的位数
     * 
     * 
     * @since 1.5
     */
    public static final int SIZE = 32;

    /**
     * The number of bytes used to represent a {@code float} value.
     *
     * <p>
     *  用于表示{@code float}值的字节数
     * 
     * 
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * The {@code Class} instance representing the primitive type
     * {@code float}.
     *
     * <p>
     *  代表原始类型{@code float}的{@code Class}
     * 
     * 
     * @since JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Float> TYPE = (Class<Float>) Class.getPrimitiveClass("float");

    /**
     * Returns a string representation of the {@code float}
     * argument. All characters mentioned below are ASCII characters.
     * <ul>
     * <li>If the argument is NaN, the result is the string
     * "{@code NaN}".
     * <li>Otherwise, the result is a string that represents the sign and
     *     magnitude (absolute value) of the argument. If the sign is
     *     negative, the first character of the result is
     *     '{@code -}' ({@code '\u005Cu002D'}); if the sign is
     *     positive, no sign character appears in the result. As for
     *     the magnitude <i>m</i>:
     * <ul>
     * <li>If <i>m</i> is infinity, it is represented by the characters
     *     {@code "Infinity"}; thus, positive infinity produces
     *     the result {@code "Infinity"} and negative infinity
     *     produces the result {@code "-Infinity"}.
     * <li>If <i>m</i> is zero, it is represented by the characters
     *     {@code "0.0"}; thus, negative zero produces the result
     *     {@code "-0.0"} and positive zero produces the result
     *     {@code "0.0"}.
     * <li> If <i>m</i> is greater than or equal to 10<sup>-3</sup> but
     *      less than 10<sup>7</sup>, then it is represented as the
     *      integer part of <i>m</i>, in decimal form with no leading
     *      zeroes, followed by '{@code .}'
     *      ({@code '\u005Cu002E'}), followed by one or more
     *      decimal digits representing the fractional part of
     *      <i>m</i>.
     * <li> If <i>m</i> is less than 10<sup>-3</sup> or greater than or
     *      equal to 10<sup>7</sup>, then it is represented in
     *      so-called "computerized scientific notation." Let <i>n</i>
     *      be the unique integer such that 10<sup><i>n</i> </sup>&le;
     *      <i>m</i> {@literal <} 10<sup><i>n</i>+1</sup>; then let <i>a</i>
     *      be the mathematically exact quotient of <i>m</i> and
     *      10<sup><i>n</i></sup> so that 1 &le; <i>a</i> {@literal <} 10.
     *      The magnitude is then represented as the integer part of
     *      <i>a</i>, as a single decimal digit, followed by
     *      '{@code .}' ({@code '\u005Cu002E'}), followed by
     *      decimal digits representing the fractional part of
     *      <i>a</i>, followed by the letter '{@code E}'
     *      ({@code '\u005Cu0045'}), followed by a representation
     *      of <i>n</i> as a decimal integer, as produced by the
     *      method {@link java.lang.Integer#toString(int)}.
     *
     * </ul>
     * </ul>
     * How many digits must be printed for the fractional part of
     * <i>m</i> or <i>a</i>? There must be at least one digit
     * to represent the fractional part, and beyond that as many, but
     * only as many, more digits as are needed to uniquely distinguish
     * the argument value from adjacent values of type
     * {@code float}. That is, suppose that <i>x</i> is the
     * exact mathematical value represented by the decimal
     * representation produced by this method for a finite nonzero
     * argument <i>f</i>. Then <i>f</i> must be the {@code float}
     * value nearest to <i>x</i>; or, if two {@code float} values are
     * equally close to <i>x</i>, then <i>f</i> must be one of
     * them and the least significant bit of the significand of
     * <i>f</i> must be {@code 0}.
     *
     * <p>To create localized string representations of a floating-point
     * value, use subclasses of {@link java.text.NumberFormat}.
     *
     * <p>
     * 返回{@code float}参数的字符串表示形式下面提到的所有字符都是ASCII字符
     * <ul>
     *  <li>如果参数是NaN,则结果是字符串"{@code NaN}"<li>否则,结果是表示参数的符号和幅度(绝对值)的字符串。
     * 如果符号为负,结果的第一个字符是"{@code  - }"({@code'\\ u005Cu002D'});如果符号为正,则在结果中不出现符号字符。对于幅度m：。
     * <ul>
     * <li>如果<i> m </i>是无穷大,则由字符{@code"Infinity"}表示;因此,正无穷大产生结果{@code"Infinity"}和负无穷大产生结果{@code"-Infinity"} 
     * <li>如果<i> m </i>为零, @code"00"};因此,负零产生结果{@code"-00"},正零产生结果{@code"00"} <li>如果m大于或等于10 < -3 </sup>但小于1
     * 0 <sup> 7 </sup>,则以十进制形式表示为m的整数部分,没有前导零,后跟'{@ code}'({@code'\\ u005Cu002E'}),后跟一个或多个表示<i> m </i>的小数部分
     * 的十进制数字,<li>如果<m>小于10 <sup> -3 </sup>或大于或等于10 <sup> 7 </sup>,则它以所谓的"计算机化的科学符号"Let i n是唯一的整数,使得10 <sup>
     *  n </i> </sup> <i> m </i> {@ literal <} 10 <sup> </i> +1 </sup>;那么让a a是m的数学上精确的商和10 <sup> n </i> </sup>
     * ,使得1 < <i> a </i> {@literal <} 10然后将幅度表示为<i> a </i>的整数部分,作为单个十进制数字,后跟'{@code}'({@代码'\\ u005Cu002E'}),
     * 后跟表示<a> </i>的小数部分的十进制数字,后跟字母"{@code E}"({@code'\\ u005Cu0045'})作为由方法{@link javalang。
     * )生成的十进制整数的<i> n </i>的表示整数#toString(int)}。
     * 
     * </ul>
     * </ul>
     * 必须为<i> m </i>或<i> a </i>的小数部分打印多少位数?必须至少有一个数字来表示分数部分,并且超出那么多,但是只有多个,需要更多的数字来唯一地区分参数值和类型{@code float}的
     * 相邻值。
     * 也就是说,假设<i> x </i>是由该方法对有限非零参数产生的十进制表示所表示的确切数学值<i> f </i>然后<i> f </i>必须是{@code float}值最接近<i> x </i>;或者
     * 如果两个{@code float}值等于接近<i> x </i>,则<f> f </i>必须是其中之一,并且<f>的有效位数的最低有效位</i>必须是{@code 0}。
     * 
     * <p>要创建浮点值的本地化字符串表示,请使用{@link javatextNumberFormat}
     * 
     * 
     * @param   f   the float to be converted.
     * @return a string representation of the argument.
     */
    public static String toString(float f) {
        return FloatingDecimal.toJavaFormatString(f);
    }

    /**
     * Returns a hexadecimal string representation of the
     * {@code float} argument. All characters mentioned below are
     * ASCII characters.
     *
     * <ul>
     * <li>If the argument is NaN, the result is the string
     *     "{@code NaN}".
     * <li>Otherwise, the result is a string that represents the sign and
     * magnitude (absolute value) of the argument. If the sign is negative,
     * the first character of the result is '{@code -}'
     * ({@code '\u005Cu002D'}); if the sign is positive, no sign character
     * appears in the result. As for the magnitude <i>m</i>:
     *
     * <ul>
     * <li>If <i>m</i> is infinity, it is represented by the string
     * {@code "Infinity"}; thus, positive infinity produces the
     * result {@code "Infinity"} and negative infinity produces
     * the result {@code "-Infinity"}.
     *
     * <li>If <i>m</i> is zero, it is represented by the string
     * {@code "0x0.0p0"}; thus, negative zero produces the result
     * {@code "-0x0.0p0"} and positive zero produces the result
     * {@code "0x0.0p0"}.
     *
     * <li>If <i>m</i> is a {@code float} value with a
     * normalized representation, substrings are used to represent the
     * significand and exponent fields.  The significand is
     * represented by the characters {@code "0x1."}
     * followed by a lowercase hexadecimal representation of the rest
     * of the significand as a fraction.  Trailing zeros in the
     * hexadecimal representation are removed unless all the digits
     * are zero, in which case a single zero is used. Next, the
     * exponent is represented by {@code "p"} followed
     * by a decimal string of the unbiased exponent as if produced by
     * a call to {@link Integer#toString(int) Integer.toString} on the
     * exponent value.
     *
     * <li>If <i>m</i> is a {@code float} value with a subnormal
     * representation, the significand is represented by the
     * characters {@code "0x0."} followed by a
     * hexadecimal representation of the rest of the significand as a
     * fraction.  Trailing zeros in the hexadecimal representation are
     * removed. Next, the exponent is represented by
     * {@code "p-126"}.  Note that there must be at
     * least one nonzero digit in a subnormal significand.
     *
     * </ul>
     *
     * </ul>
     *
     * <table border>
     * <caption>Examples</caption>
     * <tr><th>Floating-point Value</th><th>Hexadecimal String</th>
     * <tr><td>{@code 1.0}</td> <td>{@code 0x1.0p0}</td>
     * <tr><td>{@code -1.0}</td>        <td>{@code -0x1.0p0}</td>
     * <tr><td>{@code 2.0}</td> <td>{@code 0x1.0p1}</td>
     * <tr><td>{@code 3.0}</td> <td>{@code 0x1.8p1}</td>
     * <tr><td>{@code 0.5}</td> <td>{@code 0x1.0p-1}</td>
     * <tr><td>{@code 0.25}</td>        <td>{@code 0x1.0p-2}</td>
     * <tr><td>{@code Float.MAX_VALUE}</td>
     *     <td>{@code 0x1.fffffep127}</td>
     * <tr><td>{@code Minimum Normal Value}</td>
     *     <td>{@code 0x1.0p-126}</td>
     * <tr><td>{@code Maximum Subnormal Value}</td>
     *     <td>{@code 0x0.fffffep-126}</td>
     * <tr><td>{@code Float.MIN_VALUE}</td>
     *     <td>{@code 0x0.000002p-126}</td>
     * </table>
     * <p>
     *  返回{@code float}参数的十六进制字符串表示形式下面提到的所有字符都是ASCII字符
     * 
     * <ul>
     *  <li>如果参数是NaN,则结果是字符串"{@code NaN}"<li>否则,结果是表示参数的符号和幅度(绝对值)的字符串。
     * 如果符号为负,结果的第一个字符是"{@code  - }"({@code'\\ u005Cu002D'});如果符号为正,则在结果中不出现符号字符。对于幅度m：。
     * 
     * <ul>
     * <li>如果<i> m </i>是无穷大,则它由字符串{@code"Infinity"}表示;因此,正无穷产生结果{@code"Infinity"}和负无穷产生结果{@code"-Infinity"}。
     * 
     *  <li>如果<i> m </i>为零,则由字符串{@code"0x00p0"}表示;因此,负零产生结果{@code"-0x00p0"},正零产生结果{@code"0x00p0"}
     * 
     * <li>如果<i> m </i>是具有规范化表示的{@code float}值,则子字符串用于表示有效位数和指数字段有效数字由字符{@code"0x1"}表示通过其余的有效位数的小写十六进制表示作为分数
     * 除去十六进制表示中的尾随零,除非所有数字都是零,在这种情况下使用单个零。
     * 接下来,指数由{@code"p" }后跟一个无偏指数的十进制字符串,就好像是通过调用{@link Integer#toString(int)IntegertoString}对指数值。
     * 
     * <li>如果<i> m </i>是具有次正规表示的{@code float}值,则有效数由字符{@code"0x0"}表示,后面跟有效数字的其余部分的十六进制表示作为分数删除十六进制表示中的尾随零。
     * 接下来,指数由{@code"p-126"}表示。请注意,在子正规有效数中必须至少有一个非零数字。
     * 
     * </ul>
     * 
     * </ul>
     * 
     * <table border>
     * <caption>示例</caption> <tr> <th>浮点值</th> <th>十六进制字符串</th> <tr> <td> {@ code 10} </td> <td> { @code 0x10p0}
     *  </td> <tr> <td> {@ code -10} </td> <td> {@ code -0x10p0} </td> <tr> <td> {@ code 20} td> <td> {@ code 0x10p1}
     *  </td> <tr> <td> {@ code 30} </td> <td> {@ code 0x18p1} </td> <tr> <td> {@ code 05} </td> <td> {@ code 0x10p-1}
     *  </td> <tr> <td> {@ code 025} </td> <td> tr> <td> {@ code FloatMAX_VALUE} </td> <td> {@ code 0x1fffffep127}
     *  </td> <tr> <td> {@ code Minimum Normal Value} </td> <td> {@ code 0x10p -126} </td> <tr> <td> {@ code Maximum Subnormal Value}
     *  </td> <td> {@ code 0x0fffffep-126} </td> <tr> <td> {@ code FloatMIN_VALUE} / td> <td> {@ code 0x0000002p-126}
     *  </td>。
     * </table>
     * 
     * @param   f   the {@code float} to be converted.
     * @return a hex string representation of the argument.
     * @since 1.5
     * @author Joseph D. Darcy
     */
    public static String toHexString(float f) {
        if (Math.abs(f) < FloatConsts.MIN_NORMAL
            &&  f != 0.0f ) {// float subnormal
            // Adjust exponent to create subnormal double, then
            // replace subnormal double exponent with subnormal float
            // exponent
            String s = Double.toHexString(Math.scalb((double)f,
                                                     /* -1022+126 */
                                                     DoubleConsts.MIN_EXPONENT-
                                                     FloatConsts.MIN_EXPONENT));
            return s.replaceFirst("p-1022$", "p-126");
        }
        else // double string will be the same as float string
            return Double.toHexString(f);
    }

    /**
     * Returns a {@code Float} object holding the
     * {@code float} value represented by the argument string
     * {@code s}.
     *
     * <p>If {@code s} is {@code null}, then a
     * {@code NullPointerException} is thrown.
     *
     * <p>Leading and trailing whitespace characters in {@code s}
     * are ignored.  Whitespace is removed as if by the {@link
     * String#trim} method; that is, both ASCII space and control
     * characters are removed. The rest of {@code s} should
     * constitute a <i>FloatValue</i> as described by the lexical
     * syntax rules:
     *
     * <blockquote>
     * <dl>
     * <dt><i>FloatValue:</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code NaN}
     * <dd><i>Sign<sub>opt</sub></i> {@code Infinity}
     * <dd><i>Sign<sub>opt</sub> FloatingPointLiteral</i>
     * <dd><i>Sign<sub>opt</sub> HexFloatingPointLiteral</i>
     * <dd><i>SignedInteger</i>
     * </dl>
     *
     * <dl>
     * <dt><i>HexFloatingPointLiteral</i>:
     * <dd> <i>HexSignificand BinaryExponent FloatTypeSuffix<sub>opt</sub></i>
     * </dl>
     *
     * <dl>
     * <dt><i>HexSignificand:</i>
     * <dd><i>HexNumeral</i>
     * <dd><i>HexNumeral</i> {@code .}
     * <dd>{@code 0x} <i>HexDigits<sub>opt</sub>
     *     </i>{@code .}<i> HexDigits</i>
     * <dd>{@code 0X}<i> HexDigits<sub>opt</sub>
     *     </i>{@code .} <i>HexDigits</i>
     * </dl>
     *
     * <dl>
     * <dt><i>BinaryExponent:</i>
     * <dd><i>BinaryExponentIndicator SignedInteger</i>
     * </dl>
     *
     * <dl>
     * <dt><i>BinaryExponentIndicator:</i>
     * <dd>{@code p}
     * <dd>{@code P}
     * </dl>
     *
     * </blockquote>
     *
     * where <i>Sign</i>, <i>FloatingPointLiteral</i>,
     * <i>HexNumeral</i>, <i>HexDigits</i>, <i>SignedInteger</i> and
     * <i>FloatTypeSuffix</i> are as defined in the lexical structure
     * sections of
     * <cite>The Java&trade; Language Specification</cite>,
     * except that underscores are not accepted between digits.
     * If {@code s} does not have the form of
     * a <i>FloatValue</i>, then a {@code NumberFormatException}
     * is thrown. Otherwise, {@code s} is regarded as
     * representing an exact decimal value in the usual
     * "computerized scientific notation" or as an exact
     * hexadecimal value; this exact numerical value is then
     * conceptually converted to an "infinitely precise"
     * binary value that is then rounded to type {@code float}
     * by the usual round-to-nearest rule of IEEE 754 floating-point
     * arithmetic, which includes preserving the sign of a zero
     * value.
     *
     * Note that the round-to-nearest rule also implies overflow and
     * underflow behaviour; if the exact value of {@code s} is large
     * enough in magnitude (greater than or equal to ({@link
     * #MAX_VALUE} + {@link Math#ulp(float) ulp(MAX_VALUE)}/2),
     * rounding to {@code float} will result in an infinity and if the
     * exact value of {@code s} is small enough in magnitude (less
     * than or equal to {@link #MIN_VALUE}/2), rounding to float will
     * result in a zero.
     *
     * Finally, after rounding a {@code Float} object representing
     * this {@code float} value is returned.
     *
     * <p>To interpret localized string representations of a
     * floating-point value, use subclasses of {@link
     * java.text.NumberFormat}.
     *
     * <p>Note that trailing format specifiers, specifiers that
     * determine the type of a floating-point literal
     * ({@code 1.0f} is a {@code float} value;
     * {@code 1.0d} is a {@code double} value), do
     * <em>not</em> influence the results of this method.  In other
     * words, the numerical value of the input string is converted
     * directly to the target floating-point type.  In general, the
     * two-step sequence of conversions, string to {@code double}
     * followed by {@code double} to {@code float}, is
     * <em>not</em> equivalent to converting a string directly to
     * {@code float}.  For example, if first converted to an
     * intermediate {@code double} and then to
     * {@code float}, the string<br>
     * {@code "1.00000017881393421514957253748434595763683319091796875001d"}<br>
     * results in the {@code float} value
     * {@code 1.0000002f}; if the string is converted directly to
     * {@code float}, <code>1.000000<b>1</b>f</code> results.
     *
     * <p>To avoid calling this method on an invalid string and having
     * a {@code NumberFormatException} be thrown, the documentation
     * for {@link Double#valueOf Double.valueOf} lists a regular
     * expression which can be used to screen the input.
     *
     * <p>
     * 返回一个{@code Float}对象,其中包含由参数字符串{@code s}表示的{@code float}值,
     * 
     *  <p>如果{@code s}是{@code null},则会抛出{@code NullPointerException}
     * 
     *  <p> {@code s}中的前导和尾随空格字符被忽略空格被删除,如同通过{@link String#trim}方法;也就是说,删除了ASCII空格和控制字符。
     * {@ code s}的其余部分应该构成一个<i> FloatValue </i>,如词法语法规则所描述的：。
     * 
     * <blockquote>
     * <dl>
     *  <dt> <i> FloatValue：</i> <dd> <i>签署<sub> opt </sub> </i> {@code NaN} <dd> <i> > </i> {@code Infinity}
     *  <dd> <i>签署<sub> opt </sub> FloatingPointLiteral </i> <dd> <i> <dd> <i> SignedInteger </i>。
     * </dl>
     * 
     * <dl>
     * <dt> <i> HexFloatingPointLiteral </i>：<dd> <i> HexSignificand BinaryExponent FloatTypeSuffix <sub> op
     * t </sub> </i>。
     * </dl>
     * 
     * <dl>
     *  <dt> <i> HexSignificand：</i> <dd> <i> HexNumeral </i> <dd> <i> HexNumeral </i> {@code} <dd> {@ code 0x}
     *  <sub> opt </sub> </i> {@ code} <i> HexDigits </i> <dd> {@ code 0X} <i> HexDigits <sub> opt </sub> </code} <i>
     *  HexDigits </i>。
     * </dl>
     * 
     * <dl>
     *  <dt> <i> BinaryExponent：</i> <dd> <i> BinaryExponentIndicator SignedInteger </i>
     * </dl>
     * 
     * <dl>
     *  <dt> <i> BinaryExponentIndicator：</i> <dd> {@ code p} <dd> {@ code P}
     * </dl>
     * 
     * </blockquote>
     * 
     * 其中<i> Sign </i>,<i> FloatingPointLiteral </i>,HexNumeral </i>,HexDigits </i>,<i> SignedInteger </i>和<i>
     *  FloatTypeSuffix </i>是在<cite> Java和trade的词法结构部分中定义的;除非在数字之间不接受下划线,否则{@code s}不具有<i> FloatValue </i>的形
     * 式,则会抛出{@code NumberFormatException}否则,{@code s}被视为在通常的"计算机化科学记数法"中表示精确的十进制值或精确的十六进制值;然后将该精确数值概念上转换为"无
     * 限精确"二进制值,然后通过IEEE 754浮点运算的常规舍入到最近规则舍入到类型{@code float},其包括保留零值。
     * 
     * 注意,round-to-nearest规则也意味着上溢和下溢行为;如果{@code s}的精确值足够大(大于或等于({@link #MAX_VALUE} + {@link Math#ulp(float)ulp(MAX_VALUE)}
     *  / 2),则舍入到{ @code float}将导致无穷大,如果{@code s}的精确值在量级上足够小(小于或等于{@link #MIN_VALUE} / 2),则舍入为float将导致零。
     * 
     *  最后,在舍入后,返回表示此{@code float}值的{@code Float}对象
     * 
     *  <p>要解释浮点值的本地化字符串表示,请使用{@link javatextNumberFormat}
     * 
     * <p>请注意,尾随格式说明符,确定浮点文字类型的说明符({@code 10f})是一个{@code float}值; {@code 10d}是一个{@code double}值, do <em>不影响此
     * 方法的结果换句话说,输入字符串的数值直接转换为目标浮点类型一般来说,两步转换序列,string to { @code double},然后是{@code double}到{@code float},<em>
     * 不等于将字符串直接转换为{@code float}例如,如果首先转换为中间{代码double},然后转换为{@code float},字符串<br> {@code"10000001788139342151495725374843459576368331941796875001d"}
     *  <br>会生成{@code float}值{@code 10000002f};如果字符串直接转换为{@code float},<code> 1000000 <b> 1 </b> f </code>结果
     * 。
     * 
     * <p>为了避免在无效的字符串上调用此方法并抛出{@code NumberFormatException},{@link Double#valueOf DoublevalueOf}的文档列出了可用于屏幕输
     * 入的正则表达式。
     * 
     * 
     * @param   s   the string to be parsed.
     * @return  a {@code Float} object holding the value
     *          represented by the {@code String} argument.
     * @throws  NumberFormatException  if the string does not contain a
     *          parsable number.
     */
    public static Float valueOf(String s) throws NumberFormatException {
        return new Float(parseFloat(s));
    }

    /**
     * Returns a {@code Float} instance representing the specified
     * {@code float} value.
     * If a new {@code Float} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Float(float)}, as this method is likely to yield
     * significantly better space and time performance by caching
     * frequently requested values.
     *
     * <p>
     *  返回一个代表指定的{@code float}值的{@code Float}实例如果不需要一个新的{@code Float}实例,通常应优先使用构造函数{@link #Float(float)} ,因为
     * 该方法通过缓存频繁请求的值可能产生明显更好的空间和时间性能。
     * 
     * 
     * @param  f a float value.
     * @return a {@code Float} instance representing {@code f}.
     * @since  1.5
     */
    public static Float valueOf(float f) {
        return new Float(f);
    }

    /**
     * Returns a new {@code float} initialized to the value
     * represented by the specified {@code String}, as performed
     * by the {@code valueOf} method of class {@code Float}.
     *
     * <p>
     *  返回一个新的{@code float}初始化为指定的{@code String}表示的值,由{@code Float}类的{@code valueOf}方法执行,
     * 
     * 
     * @param  s the string to be parsed.
     * @return the {@code float} value represented by the string
     *         argument.
     * @throws NullPointerException  if the string is null
     * @throws NumberFormatException if the string does not contain a
     *               parsable {@code float}.
     * @see    java.lang.Float#valueOf(String)
     * @since 1.2
     */
    public static float parseFloat(String s) throws NumberFormatException {
        return FloatingDecimal.parseFloat(s);
    }

    /**
     * Returns {@code true} if the specified number is a
     * Not-a-Number (NaN) value, {@code false} otherwise.
     *
     * <p>
     * 如果指定的数字是非数字(NaN)值,则返回{@code true},否则返回{@code false}
     * 
     * 
     * @param   v   the value to be tested.
     * @return  {@code true} if the argument is NaN;
     *          {@code false} otherwise.
     */
    public static boolean isNaN(float v) {
        return (v != v);
    }

    /**
     * Returns {@code true} if the specified number is infinitely
     * large in magnitude, {@code false} otherwise.
     *
     * <p>
     *  如果指定的数字大小无限大,则返回{@code true},否则返回{@code false}
     * 
     * 
     * @param   v   the value to be tested.
     * @return  {@code true} if the argument is positive infinity or
     *          negative infinity; {@code false} otherwise.
     */
    public static boolean isInfinite(float v) {
        return (v == POSITIVE_INFINITY) || (v == NEGATIVE_INFINITY);
    }


    /**
     * Returns {@code true} if the argument is a finite floating-point
     * value; returns {@code false} otherwise (for NaN and infinity
     * arguments).
     *
     * <p>
     *  如果参数是有限浮点值,则返回{@code true};返回{@code false}否则(对于NaN和无穷大参数)
     * 
     * 
     * @param f the {@code float} value to be tested
     * @return {@code true} if the argument is a finite
     * floating-point value, {@code false} otherwise.
     * @since 1.8
     */
     public static boolean isFinite(float f) {
        return Math.abs(f) <= FloatConsts.MAX_VALUE;
    }

    /**
     * The value of the Float.
     *
     * <p>
     *  浮点值
     * 
     * 
     * @serial
     */
    private final float value;

    /**
     * Constructs a newly allocated {@code Float} object that
     * represents the primitive {@code float} argument.
     *
     * <p>
     *  构造一个新分配的{@code Float}对象,表示原始的{@code float}参数
     * 
     * 
     * @param   value   the value to be represented by the {@code Float}.
     */
    public Float(float value) {
        this.value = value;
    }

    /**
     * Constructs a newly allocated {@code Float} object that
     * represents the argument converted to type {@code float}.
     *
     * <p>
     *  构造一个新分配的{@code Float}对象,该对象表示转换为类型{@code float}的参数,
     * 
     * 
     * @param   value   the value to be represented by the {@code Float}.
     */
    public Float(double value) {
        this.value = (float)value;
    }

    /**
     * Constructs a newly allocated {@code Float} object that
     * represents the floating-point value of type {@code float}
     * represented by the string. The string is converted to a
     * {@code float} value as if by the {@code valueOf} method.
     *
     * <p>
     * 构造一个新分配的{@code Float}对象,该对象表示由字符串表示的类型为{@code float}的浮点值该字符串被转换为{@code float}值,如同通过{@code valueOf}方法。
     * 
     * 
     * @param      s   a string to be converted to a {@code Float}.
     * @throws  NumberFormatException  if the string does not contain a
     *               parsable number.
     * @see        java.lang.Float#valueOf(java.lang.String)
     */
    public Float(String s) throws NumberFormatException {
        value = parseFloat(s);
    }

    /**
     * Returns {@code true} if this {@code Float} value is a
     * Not-a-Number (NaN), {@code false} otherwise.
     *
     * <p>
     *  如果此{@code Float}值为非数字(NaN),则返回{@code true},否则返回{@code false}
     * 
     * 
     * @return  {@code true} if the value represented by this object is
     *          NaN; {@code false} otherwise.
     */
    public boolean isNaN() {
        return isNaN(value);
    }

    /**
     * Returns {@code true} if this {@code Float} value is
     * infinitely large in magnitude, {@code false} otherwise.
     *
     * <p>
     *  如果此{@code Float}值的大小无限大,则返回{@code true},否则返回{@code false}
     * 
     * 
     * @return  {@code true} if the value represented by this object is
     *          positive infinity or negative infinity;
     *          {@code false} otherwise.
     */
    public boolean isInfinite() {
        return isInfinite(value);
    }

    /**
     * Returns a string representation of this {@code Float} object.
     * The primitive {@code float} value represented by this object
     * is converted to a {@code String} exactly as if by the method
     * {@code toString} of one argument.
     *
     * <p>
     *  返回此{@code Float}对象的字符串表示此对象表示的原始{@code float}值转换为{@code String},与使用一个参数的方法{@code toString}完全相同
     * 
     * 
     * @return  a {@code String} representation of this object.
     * @see java.lang.Float#toString(float)
     */
    public String toString() {
        return Float.toString(value);
    }

    /**
     * Returns the value of this {@code Float} as a {@code byte} after
     * a narrowing primitive conversion.
     *
     * <p>
     *  在缩小的基元转换后,将此{@code Float}的值作为{@code byte}返回
     * 
     * 
     * @return  the {@code float} value represented by this object
     *          converted to type {@code byte}
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public byte byteValue() {
        return (byte)value;
    }

    /**
     * Returns the value of this {@code Float} as a {@code short}
     * after a narrowing primitive conversion.
     *
     * <p>
     * 在缩小的原始转换后,将此{@code Float}的值返回为{@code short}
     * 
     * 
     * @return  the {@code float} value represented by this object
     *          converted to type {@code short}
     * @jls 5.1.3 Narrowing Primitive Conversions
     * @since JDK1.1
     */
    public short shortValue() {
        return (short)value;
    }

    /**
     * Returns the value of this {@code Float} as an {@code int} after
     * a narrowing primitive conversion.
     *
     * <p>
     *  在缩小的原始转换后,将此{@code Float}的值返回为{@code int}
     * 
     * 
     * @return  the {@code float} value represented by this object
     *          converted to type {@code int}
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public int intValue() {
        return (int)value;
    }

    /**
     * Returns value of this {@code Float} as a {@code long} after a
     * narrowing primitive conversion.
     *
     * <p>
     *  在缩小的基本转换后,将此{@code Float}的值返回为{@code long}
     * 
     * 
     * @return  the {@code float} value represented by this object
     *          converted to type {@code long}
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public long longValue() {
        return (long)value;
    }

    /**
     * Returns the {@code float} value of this {@code Float} object.
     *
     * <p>
     *  返回此{@code Float}对象的{@code float}值
     * 
     * 
     * @return the {@code float} value represented by this object
     */
    public float floatValue() {
        return value;
    }

    /**
     * Returns the value of this {@code Float} as a {@code double}
     * after a widening primitive conversion.
     *
     * <p>
     *  在扩展基元转换后,将此{@code Float}的值作为{@code double}返回
     * 
     * 
     * @return the {@code float} value represented by this
     *         object converted to type {@code double}
     * @jls 5.1.2 Widening Primitive Conversions
     */
    public double doubleValue() {
        return (double)value;
    }

    /**
     * Returns a hash code for this {@code Float} object. The
     * result is the integer bit representation, exactly as produced
     * by the method {@link #floatToIntBits(float)}, of the primitive
     * {@code float} value represented by this {@code Float}
     * object.
     *
     * <p>
     *  返回此{@code Float}对象的哈希码结果是整数位表示,完全与方法{@link #floatToIntBits(float)}生成的{@code float}值由此{@代码Float}对象
     * 
     * 
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code float} value; compatible with
     * {@code Float.hashCode()}.
     *
     * <p>
     *  返回{@code float}值的哈希码;兼容{@code FloathashCode()}
     * 
     * 
     * @param value the value to hash
     * @return a hash code value for a {@code float} value.
     * @since 1.8
     */
    public static int hashCode(float value) {
        return floatToIntBits(value);
    }

    /**

     * Compares this object against the specified object.  The result
     * is {@code true} if and only if the argument is not
     * {@code null} and is a {@code Float} object that
     * represents a {@code float} with the same value as the
     * {@code float} represented by this object. For this
     * purpose, two {@code float} values are considered to be the
     * same if and only if the method {@link #floatToIntBits(float)}
     * returns the identical {@code int} value when applied to
     * each.
     *
     * <p>Note that in most cases, for two instances of class
     * {@code Float}, {@code f1} and {@code f2}, the value
     * of {@code f1.equals(f2)} is {@code true} if and only if
     *
     * <blockquote><pre>
     *   f1.floatValue() == f2.floatValue()
     * </pre></blockquote>
     *
     * <p>also has the value {@code true}. However, there are two exceptions:
     * <ul>
     * <li>If {@code f1} and {@code f2} both represent
     *     {@code Float.NaN}, then the {@code equals} method returns
     *     {@code true}, even though {@code Float.NaN==Float.NaN}
     *     has the value {@code false}.
     * <li>If {@code f1} represents {@code +0.0f} while
     *     {@code f2} represents {@code -0.0f}, or vice
     *     versa, the {@code equal} test has the value
     *     {@code false}, even though {@code 0.0f==-0.0f}
     *     has the value {@code true}.
     * </ul>
     *
     * This definition allows hash tables to operate properly.
     *
     * <p>
     * 将此对象与指定的对象进行比较当且仅当参数不是{@code null}并且是一个{@code Float}对象时,结果是{@code true},该对象表示{@code float}此对象表示的{@code float}
     * 为此,当且仅当方法{@link #floatToIntBits(float)}返回相同的{@code int}时,两个{@code float}应用于每个时的值。
     * 
     *  <p>请注意,在大多数情况下,对于{@code Float}类{@code f1}和{@code f2}的两个实例,{@code f1equals(f2)}的值为{@code true} if只有如果
     * 。
     * 
     *  <blockquote> <pre> f1floatValue()== f2floatValue()</pre> </blockquote>
     * 
     *  <p>也有值{@code true}但是,有两个例外：
     * <ul>
     * <li>如果{@code f1}和{@code f2}都代表{@code FloatNaN},则{@code equals}方法会返回{@code true},即使{@code FloatNaN == FloatNaN}
     * 值{@code false} <li>如果{@code f1}表示{@code + 00f},而{@code f2}表示{@code -00f},反之亦然,{@code equal} {@code false}
     * ,即使{@code 00f ==  -  00f}的值为{@code true}。
     * </ul>
     * 
     *  此定义允许散列表正常操作
     * 
     * 
     * @param obj the object to be compared
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see java.lang.Float#floatToIntBits(float)
     */
    public boolean equals(Object obj) {
        return (obj instanceof Float)
               && (floatToIntBits(((Float)obj).value) == floatToIntBits(value));
    }

    /**
     * Returns a representation of the specified floating-point value
     * according to the IEEE 754 floating-point "single format" bit
     * layout.
     *
     * <p>Bit 31 (the bit that is selected by the mask
     * {@code 0x80000000}) represents the sign of the floating-point
     * number.
     * Bits 30-23 (the bits that are selected by the mask
     * {@code 0x7f800000}) represent the exponent.
     * Bits 22-0 (the bits that are selected by the mask
     * {@code 0x007fffff}) represent the significand (sometimes called
     * the mantissa) of the floating-point number.
     *
     * <p>If the argument is positive infinity, the result is
     * {@code 0x7f800000}.
     *
     * <p>If the argument is negative infinity, the result is
     * {@code 0xff800000}.
     *
     * <p>If the argument is NaN, the result is {@code 0x7fc00000}.
     *
     * <p>In all cases, the result is an integer that, when given to the
     * {@link #intBitsToFloat(int)} method, will produce a floating-point
     * value the same as the argument to {@code floatToIntBits}
     * (except all NaN values are collapsed to a single
     * "canonical" NaN value).
     *
     * <p>
     *  根据IEEE 754浮点"单格式"位布局返回指定浮点值的表示形式
     * 
     * <p>位31(由掩码{@code 0x80000000}选择的位)表示浮点数的符号位30-23(由掩码{@code 0x7f800000}选择的位)表示指数位22-0(由掩码{@code 0x007fffff}
     * 选择的位)表示浮点数的有效位数(有时称为尾数)。
     * 
     *  <p>如果参数是正无穷大,则结果是{@code 0x7f800000}
     * 
     *  <p>如果参数为负无穷大,则结果为{@code 0xff800000}
     * 
     *  <p>如果参数是NaN,则结果是{@code 0x7fc00000}
     * 
     * <p>在所有情况下,结果是一个整数,当给予{@link #intBitsToFloat(int)}方法时,将产生与{@code floatToIntBits}的参数相同的浮点值(除了所有NaN值被折叠成
     * 单个"规范"NaN值)。
     * 
     * 
     * @param   value   a floating-point number.
     * @return the bits that represent the floating-point number.
     */
    public static int floatToIntBits(float value) {
        int result = floatToRawIntBits(value);
        // Check for NaN based on values of bit fields, maximum
        // exponent and nonzero significand.
        if ( ((result & FloatConsts.EXP_BIT_MASK) ==
              FloatConsts.EXP_BIT_MASK) &&
             (result & FloatConsts.SIGNIF_BIT_MASK) != 0)
            result = 0x7fc00000;
        return result;
    }

    /**
     * Returns a representation of the specified floating-point value
     * according to the IEEE 754 floating-point "single format" bit
     * layout, preserving Not-a-Number (NaN) values.
     *
     * <p>Bit 31 (the bit that is selected by the mask
     * {@code 0x80000000}) represents the sign of the floating-point
     * number.
     * Bits 30-23 (the bits that are selected by the mask
     * {@code 0x7f800000}) represent the exponent.
     * Bits 22-0 (the bits that are selected by the mask
     * {@code 0x007fffff}) represent the significand (sometimes called
     * the mantissa) of the floating-point number.
     *
     * <p>If the argument is positive infinity, the result is
     * {@code 0x7f800000}.
     *
     * <p>If the argument is negative infinity, the result is
     * {@code 0xff800000}.
     *
     * <p>If the argument is NaN, the result is the integer representing
     * the actual NaN value.  Unlike the {@code floatToIntBits}
     * method, {@code floatToRawIntBits} does not collapse all the
     * bit patterns encoding a NaN to a single "canonical"
     * NaN value.
     *
     * <p>In all cases, the result is an integer that, when given to the
     * {@link #intBitsToFloat(int)} method, will produce a
     * floating-point value the same as the argument to
     * {@code floatToRawIntBits}.
     *
     * <p>
     *  根据IEEE 754浮点"单格式"位布局返回指定浮点值的表示,保留非数字(NaN)值
     * 
     * <p>位31(由掩码{@code 0x80000000}选择的位)表示浮点数的符号位30-23(由掩码{@code 0x7f800000}选择的位)表示指数位22-0(由掩码{@code 0x007fffff}
     * 选择的位)表示浮点数的有效位数(有时称为尾数)。
     * 
     *  <p>如果参数是正无穷大,则结果是{@code 0x7f800000}
     * 
     *  <p>如果参数为负无穷大,则结果为{@code 0xff800000}
     * 
     *  <p>如果参数是NaN,结果是表示实际NaN值的整数与{@code floatToIntBits}方法不同,{@code floatToRawIntBits}不会将编码NaN的所有位模式折叠为单个"规
     * 范"NaN值。
     * 
     * <p>在所有情况下,结果是一个整数,当给予{@link #intBitsToFloat(int)}方法时,将产生与{@code floatToRawIntBits}的参数相同的浮点值,
     * 
     * 
     * @param   value   a floating-point number.
     * @return the bits that represent the floating-point number.
     * @since 1.3
     */
    public static native int floatToRawIntBits(float value);

    /**
     * Returns the {@code float} value corresponding to a given
     * bit representation.
     * The argument is considered to be a representation of a
     * floating-point value according to the IEEE 754 floating-point
     * "single format" bit layout.
     *
     * <p>If the argument is {@code 0x7f800000}, the result is positive
     * infinity.
     *
     * <p>If the argument is {@code 0xff800000}, the result is negative
     * infinity.
     *
     * <p>If the argument is any value in the range
     * {@code 0x7f800001} through {@code 0x7fffffff} or in
     * the range {@code 0xff800001} through
     * {@code 0xffffffff}, the result is a NaN.  No IEEE 754
     * floating-point operation provided by Java can distinguish
     * between two NaN values of the same type with different bit
     * patterns.  Distinct values of NaN are only distinguishable by
     * use of the {@code Float.floatToRawIntBits} method.
     *
     * <p>In all other cases, let <i>s</i>, <i>e</i>, and <i>m</i> be three
     * values that can be computed from the argument:
     *
     * <blockquote><pre>{@code
     * int s = ((bits >> 31) == 0) ? 1 : -1;
     * int e = ((bits >> 23) & 0xff);
     * int m = (e == 0) ?
     *                 (bits & 0x7fffff) << 1 :
     *                 (bits & 0x7fffff) | 0x800000;
     * }</pre></blockquote>
     *
     * Then the floating-point result equals the value of the mathematical
     * expression <i>s</i>&middot;<i>m</i>&middot;2<sup><i>e</i>-150</sup>.
     *
     * <p>Note that this method may not be able to return a
     * {@code float} NaN with exactly same bit pattern as the
     * {@code int} argument.  IEEE 754 distinguishes between two
     * kinds of NaNs, quiet NaNs and <i>signaling NaNs</i>.  The
     * differences between the two kinds of NaN are generally not
     * visible in Java.  Arithmetic operations on signaling NaNs turn
     * them into quiet NaNs with a different, but often similar, bit
     * pattern.  However, on some processors merely copying a
     * signaling NaN also performs that conversion.  In particular,
     * copying a signaling NaN to return it to the calling method may
     * perform this conversion.  So {@code intBitsToFloat} may
     * not be able to return a {@code float} with a signaling NaN
     * bit pattern.  Consequently, for some {@code int} values,
     * {@code floatToRawIntBits(intBitsToFloat(start))} may
     * <i>not</i> equal {@code start}.  Moreover, which
     * particular bit patterns represent signaling NaNs is platform
     * dependent; although all NaN bit patterns, quiet or signaling,
     * must be in the NaN range identified above.
     *
     * <p>
     *  返回与给定位表示对应的{@code float}值根据IEEE 754浮点"单格式"位布局,该参数被认为是浮点值的表示
     * 
     *  <p>如果参数是{@code 0x7f800000},则结果为正无穷大
     * 
     *  <p>如果参数是{@code 0xff800000},则结果是负无穷大
     * 
     * <p>如果参数是{@code 0x7f800001}到{@code 0x7fffffff}范围内的任何值或在{@code 0xff800001}到{@code 0xffffffff}范围内的任何值,则结
     * 果是NaN否IEEE 754浮点Java提供的操作可以区分具有不同位模式的相同类型的两个NaN值。
     * NaN的不同值仅可通过使用{@code FloatfloatToRawIntBits}方法来区分。
     * 
     *  <p>在所有其他情况下,让<i> s </i>,<e> e和<m>是可以从参数计算的三个值：
     * 
     *  <blockquote> <pre> {@ code int s =((bits >> 31)== 0)? 1：-1; int e =((bits >> 23)&0xff); int m =(e == 0)? (bits&0x7fffff)<< 1：(bits&0x7fffff)| 0x800000; } </pre>
     *  </blockquote>。
     * 
     * 然后,浮点结果等于数学表达式的值<i> </i>&lt; i&gt;&lt; i&gt;&lt; i&gt;&lt; i&gt; >
     * 
     * <p>请注意,此方法可能无法返回与{@code int}参数完全相同的位模式的{@code float} NaN。
     * IEEE 754区分两种NaN,安静NaN和信号NaN </i>两种NaN之间的差异通常在Java中不可见。对信令NaN的算术运算将它们转换成具有不同但通常相似的位模式的安静NaN。
     * 然而,在一些处理器上,仅复制信令NaN也执行该转换特别地,复制信令NaN以将其返回到调用方法可以执行该转换。
     * 因此,{@code intBitsToFloat}可能不能返回具有信令NaN位模式的{@code float}因此,对于一些{@code int}值,{@code floatToRawIntBits(intBitsToFloat(start))}
     * 可能不等于{@code start}。
     * 然而,在一些处理器上,仅复制信令NaN也执行该转换特别地,复制信令NaN以将其返回到调用方法可以执行该转换。尽管所有NaN比特模式,安静或信令,必须在上述识别的NaN范围内。
     * 
     * 
     * @param   bits   an integer.
     * @return  the {@code float} floating-point value with the same bit
     *          pattern.
     */
    public static native float intBitsToFloat(int bits);

    /**
     * Compares two {@code Float} objects numerically.  There are
     * two ways in which comparisons performed by this method differ
     * from those performed by the Java language numerical comparison
     * operators ({@code <, <=, ==, >=, >}) when
     * applied to primitive {@code float} values:
     *
     * <ul><li>
     *          {@code Float.NaN} is considered by this method to
     *          be equal to itself and greater than all other
     *          {@code float} values
     *          (including {@code Float.POSITIVE_INFINITY}).
     * <li>
     *          {@code 0.0f} is considered by this method to be greater
     *          than {@code -0.0f}.
     * </ul>
     *
     * This ensures that the <i>natural ordering</i> of {@code Float}
     * objects imposed by this method is <i>consistent with equals</i>.
     *
     * <p>
     * 以数字方式比较两个{@code Float}对象此方法执行的比较有两种方法与Java语言数值比较运算符({@code <,<=,==,> =,>})执行的方法不同,应用于原始{@code float}值
     * ：。
     * 
     *  <ul> <li>此方法认为{@code FloatNaN}等于自己,大于所有其他{@code float}值(包括{@code FloatPOSITIVE_INFINITY})
     * <li>
     *  此方法认为{@code 00f}大于{@code -00f}
     * </ul>
     * 
     *  这确保由此方法强加的{@code Float}对象的<i>自然排序</i>与</i>
     * 
     * 
     * @param   anotherFloat   the {@code Float} to be compared.
     * @return  the value {@code 0} if {@code anotherFloat} is
     *          numerically equal to this {@code Float}; a value
     *          less than {@code 0} if this {@code Float}
     *          is numerically less than {@code anotherFloat};
     *          and a value greater than {@code 0} if this
     *          {@code Float} is numerically greater than
     *          {@code anotherFloat}.
     *
     * @since   1.2
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Float anotherFloat) {
        return Float.compare(value, anotherFloat.value);
    }

    /**
     * Compares the two specified {@code float} values. The sign
     * of the integer value returned is the same as that of the
     * integer that would be returned by the call:
     * <pre>
     *    new Float(f1).compareTo(new Float(f2))
     * </pre>
     *
     * <p>
     * 比较两个指定的{@code float}值返回的整数值的符号与调用返回的整数的符号相同：
     * <pre>
     *  new Float(f1)compareTo(new Float(f2))
     * </pre>
     * 
     * 
     * @param   f1        the first {@code float} to compare.
     * @param   f2        the second {@code float} to compare.
     * @return  the value {@code 0} if {@code f1} is
     *          numerically equal to {@code f2}; a value less than
     *          {@code 0} if {@code f1} is numerically less than
     *          {@code f2}; and a value greater than {@code 0}
     *          if {@code f1} is numerically greater than
     *          {@code f2}.
     * @since 1.4
     */
    public static int compare(float f1, float f2) {
        if (f1 < f2)
            return -1;           // Neither val is NaN, thisVal is smaller
        if (f1 > f2)
            return 1;            // Neither val is NaN, thisVal is larger

        // Cannot use floatToRawIntBits because of possibility of NaNs.
        int thisBits    = Float.floatToIntBits(f1);
        int anotherBits = Float.floatToIntBits(f2);

        return (thisBits == anotherBits ?  0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                 1));                          // (0.0, -0.0) or (NaN, !NaN)
    }

    /**
     * Adds two {@code float} values together as per the + operator.
     *
     * <p>
     *  根据+运算符将两个{@code float}值添加到一起
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @jls 4.2.4 Floating-Point Operations
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static float sum(float a, float b) {
        return a + b;
    }

    /**
     * Returns the greater of two {@code float} values
     * as if by calling {@link Math#max(float, float) Math.max}.
     *
     * <p>
     *  返回两个{@code float}值中的较大值,就像调用{@link Math#max(float,float)Mathmax}
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the greater of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code float} values
     * as if by calling {@link Math#min(float, float) Math.min}.
     *
     * <p>
     *  返回两个{@code float}值中较小的值,如同调用{@link Math#min(float,float)Mathmin}
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the smaller of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static float min(float a, float b) {
        return Math.min(a, b);
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -2671257302660747028L;
}
