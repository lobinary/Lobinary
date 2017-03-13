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
import sun.misc.FpUtils;
import sun.misc.DoubleConsts;

/**
 * The {@code Double} class wraps a value of the primitive type
 * {@code double} in an object. An object of type
 * {@code Double} contains a single field whose type is
 * {@code double}.
 *
 * <p>In addition, this class provides several methods for converting a
 * {@code double} to a {@code String} and a
 * {@code String} to a {@code double}, as well as other
 * constants and methods useful when dealing with a
 * {@code double}.
 *
 * <p>
 *  {@code Double}类在对象中封装了原始类型{@code double}的值一个类型为{@code Double}的对象包含一个类型为{@code double}的单个字段,
 * 
 *  <p>此外,此类提供了几种方法,用于将{@code double}转换为{@code String}和{@code String}转换为{@code double},以及其他常量和方法处理一个{@code double}
 * 。
 * 
 * 
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Joseph D. Darcy
 * @since JDK1.0
 */
public final class Double extends Number implements Comparable<Double> {
    /**
     * A constant holding the positive infinity of type
     * {@code double}. It is equal to the value returned by
     * {@code Double.longBitsToDouble(0x7ff0000000000000L)}.
     * <p>
     *  保持类型为{@code double}的正无穷大的常数它等于{@code DoublelongBitsToDouble(0x7ff0000000000000L)}返回的值
     * 
     */
    public static final double POSITIVE_INFINITY = 1.0 / 0.0;

    /**
     * A constant holding the negative infinity of type
     * {@code double}. It is equal to the value returned by
     * {@code Double.longBitsToDouble(0xfff0000000000000L)}.
     * <p>
     * 保持类型为{@code double}的负无穷大的常数它等于{@code DoublelongBitsToDouble(0xfff0000000000000L)}返回的值
     * 
     */
    public static final double NEGATIVE_INFINITY = -1.0 / 0.0;

    /**
     * A constant holding a Not-a-Number (NaN) value of type
     * {@code double}. It is equivalent to the value returned by
     * {@code Double.longBitsToDouble(0x7ff8000000000000L)}.
     * <p>
     *  保存类型为{@code double}的非数字(NaN)值的常量它等价于{@code DoublelongBitsToDouble(0x7ff8000000000000L)}返回的值
     * 
     */
    public static final double NaN = 0.0d / 0.0;

    /**
     * A constant holding the largest positive finite value of type
     * {@code double},
     * (2-2<sup>-52</sup>)&middot;2<sup>1023</sup>.  It is equal to
     * the hexadecimal floating-point literal
     * {@code 0x1.fffffffffffffP+1023} and also equal to
     * {@code Double.longBitsToDouble(0x7fefffffffffffffL)}.
     * <p>
     *  保持类型为{@code double},(2-2 <sup> -52 </sup>)和middot的最大正有限值的常数; 2 <sup> 1023 </sup>它等于十六进制浮点文字{@code 0x1fffffffffffffP + 1023}
     * ,也等于{@code DoublelongBitsToDouble(0x7fefffffffffffffL)}。
     * 
     */
    public static final double MAX_VALUE = 0x1.fffffffffffffP+1023; // 1.7976931348623157e+308

    /**
     * A constant holding the smallest positive normal value of type
     * {@code double}, 2<sup>-1022</sup>.  It is equal to the
     * hexadecimal floating-point literal {@code 0x1.0p-1022} and also
     * equal to {@code Double.longBitsToDouble(0x0010000000000000L)}.
     *
     * <p>
     * 一个保持类型为{@code double},2 <sup> -1022 </sup>的最小正正常数的常数它等于十六进制浮点文本{@code 0x10p-1022},也等于{@code DoublelongBitsToDouble(0x0010000000000000L)}
     * 。
     * 
     * 
     * @since 1.6
     */
    public static final double MIN_NORMAL = 0x1.0p-1022; // 2.2250738585072014E-308

    /**
     * A constant holding the smallest positive nonzero value of type
     * {@code double}, 2<sup>-1074</sup>. It is equal to the
     * hexadecimal floating-point literal
     * {@code 0x0.0000000000001P-1022} and also equal to
     * {@code Double.longBitsToDouble(0x1L)}.
     * <p>
     *  一个保持类型为{@code double},2 <sup> -1074 </sup>的最小正非零值的常数它等于十六进制浮点文本{@code 0x00000000000001P-1022},也等于{@code DoublelongBitsToDouble(0x1L)}
     * 。
     * 
     */
    public static final double MIN_VALUE = 0x0.0000000000001P-1022; // 4.9e-324

    /**
     * Maximum exponent a finite {@code double} variable may have.
     * It is equal to the value returned by
     * {@code Math.getExponent(Double.MAX_VALUE)}.
     *
     * <p>
     *  有限的{@code double}变量可能具有的最大指数它等于{@code MathgetExponent(DoubleMAX_VALUE)}返回的值
     * 
     * 
     * @since 1.6
     */
    public static final int MAX_EXPONENT = 1023;

    /**
     * Minimum exponent a normalized {@code double} variable may
     * have.  It is equal to the value returned by
     * {@code Math.getExponent(Double.MIN_NORMAL)}.
     *
     * <p>
     *  标准化的{@code double}变量可能具有的最小指数它等于{@code MathgetExponent(DoubleMIN_NORMAL)}返回的值
     * 
     * 
     * @since 1.6
     */
    public static final int MIN_EXPONENT = -1022;

    /**
     * The number of bits used to represent a {@code double} value.
     *
     * <p>
     * 用于表示{@code double}值的位数
     * 
     * 
     * @since 1.5
     */
    public static final int SIZE = 64;

    /**
     * The number of bytes used to represent a {@code double} value.
     *
     * <p>
     *  用于表示{@code double}值的字节数
     * 
     * 
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * The {@code Class} instance representing the primitive type
     * {@code double}.
     *
     * <p>
     *  代表原始类型{@code double}的{@code Class}实例
     * 
     * 
     * @since JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Double>   TYPE = (Class<Double>) Class.getPrimitiveClass("double");

    /**
     * Returns a string representation of the {@code double}
     * argument. All characters mentioned below are ASCII characters.
     * <ul>
     * <li>If the argument is NaN, the result is the string
     *     "{@code NaN}".
     * <li>Otherwise, the result is a string that represents the sign and
     * magnitude (absolute value) of the argument. If the sign is negative,
     * the first character of the result is '{@code -}'
     * ({@code '\u005Cu002D'}); if the sign is positive, no sign character
     * appears in the result. As for the magnitude <i>m</i>:
     * <ul>
     * <li>If <i>m</i> is infinity, it is represented by the characters
     * {@code "Infinity"}; thus, positive infinity produces the result
     * {@code "Infinity"} and negative infinity produces the result
     * {@code "-Infinity"}.
     *
     * <li>If <i>m</i> is zero, it is represented by the characters
     * {@code "0.0"}; thus, negative zero produces the result
     * {@code "-0.0"} and positive zero produces the result
     * {@code "0.0"}.
     *
     * <li>If <i>m</i> is greater than or equal to 10<sup>-3</sup> but less
     * than 10<sup>7</sup>, then it is represented as the integer part of
     * <i>m</i>, in decimal form with no leading zeroes, followed by
     * '{@code .}' ({@code '\u005Cu002E'}), followed by one or
     * more decimal digits representing the fractional part of <i>m</i>.
     *
     * <li>If <i>m</i> is less than 10<sup>-3</sup> or greater than or
     * equal to 10<sup>7</sup>, then it is represented in so-called
     * "computerized scientific notation." Let <i>n</i> be the unique
     * integer such that 10<sup><i>n</i></sup> &le; <i>m</i> {@literal <}
     * 10<sup><i>n</i>+1</sup>; then let <i>a</i> be the
     * mathematically exact quotient of <i>m</i> and
     * 10<sup><i>n</i></sup> so that 1 &le; <i>a</i> {@literal <} 10. The
     * magnitude is then represented as the integer part of <i>a</i>,
     * as a single decimal digit, followed by '{@code .}'
     * ({@code '\u005Cu002E'}), followed by decimal digits
     * representing the fractional part of <i>a</i>, followed by the
     * letter '{@code E}' ({@code '\u005Cu0045'}), followed
     * by a representation of <i>n</i> as a decimal integer, as
     * produced by the method {@link Integer#toString(int)}.
     * </ul>
     * </ul>
     * How many digits must be printed for the fractional part of
     * <i>m</i> or <i>a</i>? There must be at least one digit to represent
     * the fractional part, and beyond that as many, but only as many, more
     * digits as are needed to uniquely distinguish the argument value from
     * adjacent values of type {@code double}. That is, suppose that
     * <i>x</i> is the exact mathematical value represented by the decimal
     * representation produced by this method for a finite nonzero argument
     * <i>d</i>. Then <i>d</i> must be the {@code double} value nearest
     * to <i>x</i>; or if two {@code double} values are equally close
     * to <i>x</i>, then <i>d</i> must be one of them and the least
     * significant bit of the significand of <i>d</i> must be {@code 0}.
     *
     * <p>To create localized string representations of a floating-point
     * value, use subclasses of {@link java.text.NumberFormat}.
     *
     * <p>
     *  返回{@code double}参数的字符串表示形式下面提到的所有字符都是ASCII字符
     * <ul>
     *  <li>如果参数是NaN,则结果是字符串"{@code NaN}"<li>否则,结果是表示参数的符号和幅度(绝对值)的字符串。
     * 如果符号为负,结果的第一个字符是"{@code  - }"({@code'\\ u005Cu002D'});如果符号为正,则在结果中不出现符号字符。对于幅度m：。
     * <ul>
     * <li>如果<i> m </i>是无穷大,则由字符{@code"Infinity"}表示;因此,正无穷产生结果{@code"Infinity"}和负无穷产生结果{@code"-Infinity"}
     * 
     *  <li>如果<i> m </i>为零,则由字符{@code"00"}表示;因此,负零产生结果{@code"-00"},正零产生结果{@code"00"}
     * 
     *  <li>如果<i> m </sup>大于或等于10 <sup> -3 </sup>但小于10 <sup> 7 </sup>,则它表示为<i> m </i>,后面跟着'{@code}'({@code'\\ u005Cu002E'}
     * ),后跟一个或多个十进制数字,表示<i的小数部分> m </i>。
     * 
     * <li>如果</i>小于10 <sup> -3 </sup>或大于或等于10 <sup> 7 </sup>,则它以所谓的"计算机化的科学记数法"使得</i>是唯一的整数,使得10 <sup> </i> 
     * </sup> <i> m </i> {@ literal <} 10 <sup> </i> +1 </sup>;那么让a a是m的数学上精确的商和10 <sup> n </i> </sup>,使得1 < <i>
     *  a </i> {@literal <} 10然后将幅度表示为<i> a </i>的整数部分,作为一个十进制数字,后跟'{@code}({@code'\\ u005Cu002E'}),后跟表示<a> </i>
     * 的小数部分的十进制数字,后跟字母"{@code E}"({@code'\\ u005Cu0045' }),然后是由方法{@link Integer#toString(int)}生成的<i> n </i>
     * 表示为十进制整数。
     * </ul>
     * </ul>
     * 必须为<i> m </i>或<i> a </i>的小数部分打印多少位数?必须有至少一个数字来表示小数部分,并且超出那么多,但是只有多少,更多的数字是唯一地区分参数值和类型{@code double}的相
     * 邻值的数字。
     * 也就是说,假设<i> x </i>是由此方法为有限非零参数产生的十进制表示所表示的确切数学值<i> d </i> double}值最接近<i> x </i>;或者如果两个{@code double}值等
     * 于接近<i> x </i>,则<d>必须是其中之一,并且<d>的有效位数的最低有效位< / i>必须是{@code 0}。
     * 
     * <p>要创建浮点值的本地化字符串表示,请使用{@link javatextNumberFormat}
     * 
     * 
     * @param   d   the {@code double} to be converted.
     * @return a string representation of the argument.
     */
    public static String toString(double d) {
        return FloatingDecimal.toJavaFormatString(d);
    }

    /**
     * Returns a hexadecimal string representation of the
     * {@code double} argument. All characters mentioned below
     * are ASCII characters.
     *
     * <ul>
     * <li>If the argument is NaN, the result is the string
     *     "{@code NaN}".
     * <li>Otherwise, the result is a string that represents the sign
     * and magnitude of the argument. If the sign is negative, the
     * first character of the result is '{@code -}'
     * ({@code '\u005Cu002D'}); if the sign is positive, no sign
     * character appears in the result. As for the magnitude <i>m</i>:
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
     * <li>If <i>m</i> is a {@code double} value with a
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
     * <li>If <i>m</i> is a {@code double} value with a subnormal
     * representation, the significand is represented by the
     * characters {@code "0x0."} followed by a
     * hexadecimal representation of the rest of the significand as a
     * fraction.  Trailing zeros in the hexadecimal representation are
     * removed. Next, the exponent is represented by
     * {@code "p-1022"}.  Note that there must be at
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
     * <tr><td>{@code Double.MAX_VALUE}</td>
     *     <td>{@code 0x1.fffffffffffffp1023}</td>
     * <tr><td>{@code Minimum Normal Value}</td>
     *     <td>{@code 0x1.0p-1022}</td>
     * <tr><td>{@code Maximum Subnormal Value}</td>
     *     <td>{@code 0x0.fffffffffffffp-1022}</td>
     * <tr><td>{@code Double.MIN_VALUE}</td>
     *     <td>{@code 0x0.0000000000001p-1022}</td>
     * </table>
     * <p>
     *  返回{@code double}参数的十六进制字符串表示形式下面提到的所有字符都是ASCII字符
     * 
     * <ul>
     *  <li>如果参数是NaN,则结果是字符串"{@code NaN}"<li>否则,结果是表示参数的符号和幅度的字符串。
     * 如果符号为负,则第一个字符结果是"{@code  - }"({@code'\\ u005Cu002D'});如果符号为正,则在结果中不出现符号字符。对于幅度m：。
     * 
     * <ul>
     * <li>如果<i> m </i>是无穷大,则它由字符串{@code"Infinity"}表示;因此,正无穷产生结果{@code"Infinity"}和负无穷产生结果{@code"-Infinity"}。
     * 
     *  <li>如果<i> m </i>为零,则由字符串{@code"0x00p0"}表示;因此,负零产生结果{@code"-0x00p0"},正零产生结果{@code"0x00p0"}
     * 
     * <li>如果<i> m </i>是带有规范化表示的{@code double}值,子字符串用于表示有效位数和指数字段。
     * 有效数字由字符{@code"0x1"}表示通过其余的有效位数的小写十六进制表示作为分数除去十六进制表示中的尾随零,除非所有数字都是零,在这种情况下使用单个零。
     * 接下来,指数由{@code"p" }后跟一个无偏指数的十进制字符串,就好像是通过调用{@link Integer#toString(int)IntegertoString}对指数值。
     * 
     * <li>如果<i> m </i>是带有次正规表示的{@code double}值,则有效数由字符{@code"0x0"}表示,后面是有效数其余部分的十六进制表示作为分数删除十六进制表示中的尾随零。
     * 接下来,指数由{@code"p-1022"}表示。请注意,在子正规有效数字中必须至少有一个非零数字。
     * 
     * </ul>
     * 
     * </ul>
     * 
     * <table border>
     * <caption>示例</caption> <tr> <th>浮点值</th> <th>十六进制字符串</th> <tr> <td> {@ code 10} </td> <td> { @code 0x10p0}
     *  </td> <tr> <td> {@ code -10} </td> <td> {@ code -0x10p0} </td> <tr> <td> {@ code 20} td> <td> {@ code 0x10p1}
     *  </td> <tr> <td> {@ code 30} </td> <td> {@ code 0x18p1} </td> <tr> <td> {@ code 05} </td> <td> {@ code 0x10p-1}
     *  </td> <tr> <td> {@ code 025} </td> <td> tr> <td> {@ code DoubleMAX_VALUE} </td> <td> {@ code 0x1fffffffffffp1023}
     *  </td> <tr> <td> {@ code Minimum Normal Value} </td> <td> {@ code 0x10p -1022} </td> <tr> <td> {@ code Maximum Subnormal Value}
     *  </td> <td> {@ code 0x0ffffffffffffpp-1022} </td> <tr> <td> {@ code DoubleMIN_VALUE} / td> <td> {@ code 0x00000000000001p-1022}
     *  </td>。
     * </table>
     * 
     * @param   d   the {@code double} to be converted.
     * @return a hex string representation of the argument.
     * @since 1.5
     * @author Joseph D. Darcy
     */
    public static String toHexString(double d) {
        /*
         * Modeled after the "a" conversion specifier in C99, section
         * 7.19.6.1; however, the output of this method is more
         * tightly specified.
         * <p>
         * 在C99中的"a"转换说明符第71961节之后建模;然而,这种方法的输出更加严格地指定
         * 
         */
        if (!isFinite(d) )
            // For infinity and NaN, use the decimal output.
            return Double.toString(d);
        else {
            // Initialized to maximum size of output.
            StringBuilder answer = new StringBuilder(24);

            if (Math.copySign(1.0, d) == -1.0)    // value is negative,
                answer.append("-");                  // so append sign info

            answer.append("0x");

            d = Math.abs(d);

            if(d == 0.0) {
                answer.append("0.0p0");
            } else {
                boolean subnormal = (d < DoubleConsts.MIN_NORMAL);

                // Isolate significand bits and OR in a high-order bit
                // so that the string representation has a known
                // length.
                long signifBits = (Double.doubleToLongBits(d)
                                   & DoubleConsts.SIGNIF_BIT_MASK) |
                    0x1000000000000000L;

                // Subnormal values have a 0 implicit bit; normal
                // values have a 1 implicit bit.
                answer.append(subnormal ? "0." : "1.");

                // Isolate the low-order 13 digits of the hex
                // representation.  If all the digits are zero,
                // replace with a single 0; otherwise, remove all
                // trailing zeros.
                String signif = Long.toHexString(signifBits).substring(3,16);
                answer.append(signif.equals("0000000000000") ? // 13 zeros
                              "0":
                              signif.replaceFirst("0{1,12}$", ""));

                answer.append('p');
                // If the value is subnormal, use the E_min exponent
                // value for double; otherwise, extract and report d's
                // exponent (the representation of a subnormal uses
                // E_min -1).
                answer.append(subnormal ?
                              DoubleConsts.MIN_EXPONENT:
                              Math.getExponent(d));
            }
            return answer.toString();
        }
    }

    /**
     * Returns a {@code Double} object holding the
     * {@code double} value represented by the argument string
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
     * binary value that is then rounded to type {@code double}
     * by the usual round-to-nearest rule of IEEE 754 floating-point
     * arithmetic, which includes preserving the sign of a zero
     * value.
     *
     * Note that the round-to-nearest rule also implies overflow and
     * underflow behaviour; if the exact value of {@code s} is large
     * enough in magnitude (greater than or equal to ({@link
     * #MAX_VALUE} + {@link Math#ulp(double) ulp(MAX_VALUE)}/2),
     * rounding to {@code double} will result in an infinity and if the
     * exact value of {@code s} is small enough in magnitude (less
     * than or equal to {@link #MIN_VALUE}/2), rounding to float will
     * result in a zero.
     *
     * Finally, after rounding a {@code Double} object representing
     * this {@code double} value is returned.
     *
     * <p> To interpret localized string representations of a
     * floating-point value, use subclasses of {@link
     * java.text.NumberFormat}.
     *
     * <p>Note that trailing format specifiers, specifiers that
     * determine the type of a floating-point literal
     * ({@code 1.0f} is a {@code float} value;
     * {@code 1.0d} is a {@code double} value), do
     * <em>not</em> influence the results of this method.  In other
     * words, the numerical value of the input string is converted
     * directly to the target floating-point type.  The two-step
     * sequence of conversions, string to {@code float} followed
     * by {@code float} to {@code double}, is <em>not</em>
     * equivalent to converting a string directly to
     * {@code double}. For example, the {@code float}
     * literal {@code 0.1f} is equal to the {@code double}
     * value {@code 0.10000000149011612}; the {@code float}
     * literal {@code 0.1f} represents a different numerical
     * value than the {@code double} literal
     * {@code 0.1}. (The numerical value 0.1 cannot be exactly
     * represented in a binary floating-point number.)
     *
     * <p>To avoid calling this method on an invalid string and having
     * a {@code NumberFormatException} be thrown, the regular
     * expression below can be used to screen the input string:
     *
     * <pre>{@code
     *  final String Digits     = "(\\p{Digit}+)";
     *  final String HexDigits  = "(\\p{XDigit}+)";
     *  // an exponent is 'e' or 'E' followed by an optionally
     *  // signed decimal integer.
     *  final String Exp        = "[eE][+-]?"+Digits;
     *  final String fpRegex    =
     *      ("[\\x00-\\x20]*"+  // Optional leading "whitespace"
     *       "[+-]?(" + // Optional sign character
     *       "NaN|" +           // "NaN" string
     *       "Infinity|" +      // "Infinity" string
     *
     *       // A decimal floating-point string representing a finite positive
     *       // number without a leading sign has at most five basic pieces:
     *       // Digits . Digits ExponentPart FloatTypeSuffix
     *       //
     *       // Since this method allows integer-only strings as input
     *       // in addition to strings of floating-point literals, the
     *       // two sub-patterns below are simplifications of the grammar
     *       // productions from section 3.10.2 of
     *       // The Java Language Specification.
     *
     *       // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
     *       "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+
     *
     *       // . Digits ExponentPart_opt FloatTypeSuffix_opt
     *       "(\\.("+Digits+")("+Exp+")?)|"+
     *
     *       // Hexadecimal strings
     *       "((" +
     *        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
     *        "(0[xX]" + HexDigits + "(\\.)?)|" +
     *
     *        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
     *        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +
     *
     *        ")[pP][+-]?" + Digits + "))" +
     *       "[fFdD]?))" +
     *       "[\\x00-\\x20]*");// Optional trailing "whitespace"
     *
     *  if (Pattern.matches(fpRegex, myString))
     *      Double.valueOf(myString); // Will not throw NumberFormatException
     *  else {
     *      // Perform suitable alternative action
     *  }
     * }</pre>
     *
     * <p>
     *  返回一个{@code Double}对象,其中包含由参数字符串{@code s}表示的{@code double}
     * 
     *  <p>如果{@code s}是{@code null},则会抛出{@code NullPointerException}
     * 
     *  <p> {@code s}中的前导和尾随空格字符被忽略空格被删除,如同通过{@link String#trim}方法;也就是说,删除了ASCII空格和控制字符。
     * {@ code s}的其余部分应该构成一个<i> FloatValue </i>,如词法语法规则所描述的：。
     * 
     * <blockquote>
     * <dl>
     * <dt> <i> FloatValue：</i> <dd> <i>签署<sub> opt </sub> </i> {@code NaN} <dd> <i> > </i> {@code Infinity}
     *  <dd> <i>签署<sub> opt </sub> FloatingPointLiteral </i> <dd> <i> <dd> <i> SignedInteger </i>。
     * </dl>
     * 
     * <dl>
     *  <dt> <i> HexFloatingPointLiteral </i>：<dd> <i> HexSignificand BinaryExponent FloatTypeSuffix <sub> o
     * pt </sub> </i>。
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
     * 限精确"二进制值,然后通过IEEE 754浮点运算的通常的循环到最近规则将其舍入为类型{@code double},其包括保留零值。
     * 
     * 注意,round-to-nearest规则也意味着上溢和下溢行为;如果{@code s}的精确值足够大(大于或等于({@link #MAX_VALUE} + {@link Math#ulp(double)ulp(MAX_VALUE)}
     *  / 2),则舍入到{ @code double}将导致无穷大,如果{@code s}的确切值在数量上足够小(小于或等于{@link #MIN_VALUE} / 2),则舍入为float将导致零。
     * 
     *  最后,在舍入后,返回表示此{@code double}值的{@code Double}对象
     * 
     *  <p>要解释浮点值的本地化字符串表示,请使用{@link javatextNumberFormat}
     * 
     * <p>请注意,尾随格式说明符,确定浮点文字类型的说明符({@code 10f})是一个{@code float}值; {@code 10d}是一个{@code double}值, do <em>不影响此
     * 方法的结果换句话说,输入字符串的数值直接转换为目标浮点类型两步转换序列,字符串到{@code float }其次是{@code float}到{@code double},是<em>不是</em>等效于
     * 将字符串直接转换为{@code double}例如,{@code float} literal {@code 01f }等于{@code double}值{@code 010000000149011612}
     * ; {@code float} literal {@code 01f}表示与{@code double}文字{@code 01}不同的数值(数值01不能在二进制浮点数中精确表示)。
     * 
     * <p>为了避免在无效的字符串上调用此方法并抛出{@code NumberFormatException},可以使用下面的正则表达式来筛选输入字符串：
     * 
     *  <pre> {@ code final String Digits ="(\\\\ p {Digit} +)"; final String HexDigits ="(\\\\ p {XDigit} +
     * )"; //指数是'e'或'E',后跟可选的//有符号的十进制整数final String Exp ="[eE] [+  - ]?"+ Digits; final String fpRegex =("[
     * \\\\ x00  -  \\\\ x20] *"+ //可选前导"空格""[+  - ]?("+ //可选符号字符"NaN |"+ //"NaN" "Infinity |"+ //"Infinity"
     * 字符串。
     * 
     * //表示有限正数的十进制浮点数字//没有前导符号的数字最多有五个基本段：// Digits Digit ExponentPart FloatTypeSuffix // //由于此方法允许仅整数字符串作为
     * 输入//另外到浮点文本字符串,//下面的两个子模式是来自Java语言规范的第3102节的语法//生成的简化。
     * 
     *  // Digits _opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt"(("+ Digits +"(\\\\)?("+ Digits +"?)(
     * "+ Exp +")?)。
     * 
     *  // Digits ExponentPart_opt FloatTypeSuffix_opt"(\\\\("+ Digits +")("+ Exp +")?)|"+
     * 
     *  //十六进制字符串"(("+ // 0 [xX] HexDigits _opt BinaryExponent FloatTypeSuffix_opt"(0 [xX]"+ HexDigits +"(\\
     * \\)?)|"。
     * 
     * // 0 [xX] HexDigits_opt HexDigits BinaryExponent FloatTypeSuffix_opt"(0 [xX]"+ HexDigits +"?(\\\\)"+ 
     * HexDigits +")"+。
     * 
     *  ")[pP] [+  - ]?" + Digits +"))"+"[fFdD]?))"+"[\\\\ x00  -  \\\\ x20] *"); //可选的"空格"
     * 
     *  if(Patternmatches(fpRegex,myString))DoublevalueOf(myString); //不会抛出NumberFormatException else {//执行合适的替代操作}
     * } </pre>。
     * 
     * 
     * @param      s   the string to be parsed.
     * @return     a {@code Double} object holding the value
     *             represented by the {@code String} argument.
     * @throws     NumberFormatException  if the string does not contain a
     *             parsable number.
     */
    public static Double valueOf(String s) throws NumberFormatException {
        return new Double(parseDouble(s));
    }

    /**
     * Returns a {@code Double} instance representing the specified
     * {@code double} value.
     * If a new {@code Double} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Double(double)}, as this method is likely to yield
     * significantly better space and time performance by caching
     * frequently requested values.
     *
     * <p>
     *  返回表示指定的{@code double}值的{@code Double}实例如果不需要新的{@code Double}实例,则通常应优先使用构造函数{@link #Double(double)} ,
     * 因为该方法通过缓存频繁请求的值可能产生明显更好的空间和时间性能。
     * 
     * 
     * @param  d a double value.
     * @return a {@code Double} instance representing {@code d}.
     * @since  1.5
     */
    public static Double valueOf(double d) {
        return new Double(d);
    }

    /**
     * Returns a new {@code double} initialized to the value
     * represented by the specified {@code String}, as performed
     * by the {@code valueOf} method of class
     * {@code Double}.
     *
     * <p>
     * 返回一个新的{@code double}初始化为指定的{@code String}表示的值,由{@code Double}类的{@code valueOf}方法执行,
     * 
     * 
     * @param  s   the string to be parsed.
     * @return the {@code double} value represented by the string
     *         argument.
     * @throws NullPointerException  if the string is null
     * @throws NumberFormatException if the string does not contain
     *         a parsable {@code double}.
     * @see    java.lang.Double#valueOf(String)
     * @since 1.2
     */
    public static double parseDouble(String s) throws NumberFormatException {
        return FloatingDecimal.parseDouble(s);
    }

    /**
     * Returns {@code true} if the specified number is a
     * Not-a-Number (NaN) value, {@code false} otherwise.
     *
     * <p>
     *  如果指定的数字是非数字(NaN)值,则返回{@code true},否则返回{@code false}
     * 
     * 
     * @param   v   the value to be tested.
     * @return  {@code true} if the value of the argument is NaN;
     *          {@code false} otherwise.
     */
    public static boolean isNaN(double v) {
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
     * @return  {@code true} if the value of the argument is positive
     *          infinity or negative infinity; {@code false} otherwise.
     */
    public static boolean isInfinite(double v) {
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
     * @param d the {@code double} value to be tested
     * @return {@code true} if the argument is a finite
     * floating-point value, {@code false} otherwise.
     * @since 1.8
     */
    public static boolean isFinite(double d) {
        return Math.abs(d) <= DoubleConsts.MAX_VALUE;
    }

    /**
     * The value of the Double.
     *
     * <p>
     *  Double的值
     * 
     * 
     * @serial
     */
    private final double value;

    /**
     * Constructs a newly allocated {@code Double} object that
     * represents the primitive {@code double} argument.
     *
     * <p>
     *  构造一个新分配的{@code Double}对象,表示原始的{@code double}参数
     * 
     * 
     * @param   value   the value to be represented by the {@code Double}.
     */
    public Double(double value) {
        this.value = value;
    }

    /**
     * Constructs a newly allocated {@code Double} object that
     * represents the floating-point value of type {@code double}
     * represented by the string. The string is converted to a
     * {@code double} value as if by the {@code valueOf} method.
     *
     * <p>
     * 构造一个新分配的{@code Double}对象,表示由字符串表示的类型为{@code double}的浮点值该字符串被转换为{@code double}值,如同通过{@code valueOf}方法。
     * 
     * 
     * @param  s  a string to be converted to a {@code Double}.
     * @throws    NumberFormatException  if the string does not contain a
     *            parsable number.
     * @see       java.lang.Double#valueOf(java.lang.String)
     */
    public Double(String s) throws NumberFormatException {
        value = parseDouble(s);
    }

    /**
     * Returns {@code true} if this {@code Double} value is
     * a Not-a-Number (NaN), {@code false} otherwise.
     *
     * <p>
     *  如果此{@code Double}值为非数字(NaN),则返回{@code true},否则返回{@code false}
     * 
     * 
     * @return  {@code true} if the value represented by this object is
     *          NaN; {@code false} otherwise.
     */
    public boolean isNaN() {
        return isNaN(value);
    }

    /**
     * Returns {@code true} if this {@code Double} value is
     * infinitely large in magnitude, {@code false} otherwise.
     *
     * <p>
     *  如果此{@code Double}值的大小无限大,则返回{@code true},否则返回{@code false}
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
     * Returns a string representation of this {@code Double} object.
     * The primitive {@code double} value represented by this
     * object is converted to a string exactly as if by the method
     * {@code toString} of one argument.
     *
     * <p>
     *  返回此{@code Double}对象的字符串表示形式此对象表示的原始{@code double}值将转换为一个字符串,就像使用一个参数的方法{@code toString}
     * 
     * 
     * @return  a {@code String} representation of this object.
     * @see java.lang.Double#toString(double)
     */
    public String toString() {
        return toString(value);
    }

    /**
     * Returns the value of this {@code Double} as a {@code byte}
     * after a narrowing primitive conversion.
     *
     * <p>
     *  在缩小的基元转换后,将此{@code Double}的值返回为{@code byte}
     * 
     * 
     * @return  the {@code double} value represented by this object
     *          converted to type {@code byte}
     * @jls 5.1.3 Narrowing Primitive Conversions
     * @since JDK1.1
     */
    public byte byteValue() {
        return (byte)value;
    }

    /**
     * Returns the value of this {@code Double} as a {@code short}
     * after a narrowing primitive conversion.
     *
     * <p>
     * 在缩小的原始转换后,将此{@code Double}的值返回为{@code short}
     * 
     * 
     * @return  the {@code double} value represented by this object
     *          converted to type {@code short}
     * @jls 5.1.3 Narrowing Primitive Conversions
     * @since JDK1.1
     */
    public short shortValue() {
        return (short)value;
    }

    /**
     * Returns the value of this {@code Double} as an {@code int}
     * after a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     *
     * <p>
     *  在缩小的原始转换后返回此{@code Double}的值作为{@code int} @jls 513缩小基本转换
     * 
     * 
     * @return  the {@code double} value represented by this object
     *          converted to type {@code int}
     */
    public int intValue() {
        return (int)value;
    }

    /**
     * Returns the value of this {@code Double} as a {@code long}
     * after a narrowing primitive conversion.
     *
     * <p>
     *  在缩小的原始转换后,将此{@code Double}的值返回为{@code long}
     * 
     * 
     * @return  the {@code double} value represented by this object
     *          converted to type {@code long}
     * @jls 5.1.3 Narrowing Primitive Conversions
     */
    public long longValue() {
        return (long)value;
    }

    /**
     * Returns the value of this {@code Double} as a {@code float}
     * after a narrowing primitive conversion.
     *
     * <p>
     *  在缩小的原始转换后,将此{@code Double}的值返回为{@code float}
     * 
     * 
     * @return  the {@code double} value represented by this object
     *          converted to type {@code float}
     * @jls 5.1.3 Narrowing Primitive Conversions
     * @since JDK1.0
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * Returns the {@code double} value of this {@code Double} object.
     *
     * <p>
     *  返回此{@code Double}对象的{@code double}值
     * 
     * 
     * @return the {@code double} value represented by this object
     */
    public double doubleValue() {
        return value;
    }

    /**
     * Returns a hash code for this {@code Double} object. The
     * result is the exclusive OR of the two halves of the
     * {@code long} integer bit representation, exactly as
     * produced by the method {@link #doubleToLongBits(double)}, of
     * the primitive {@code double} value represented by this
     * {@code Double} object. That is, the hash code is the value
     * of the expression:
     *
     * <blockquote>
     *  {@code (int)(v^(v>>>32))}
     * </blockquote>
     *
     * where {@code v} is defined by:
     *
     * <blockquote>
     *  {@code long v = Double.doubleToLongBits(this.doubleValue());}
     * </blockquote>
     *
     * <p>
     * 返回此{@code Double}对象的哈希码结果是{@code long}整数位表示形式的两半的异或,正如方法{@link #doubleToLongBits(double)}生成的那样,由此{@code Double}
     * 对象表示的原始{@code double}值即,哈希码是表达式的值：。
     * 
     * <blockquote>
     *  {@ code(int)(v ^(v >>> 32))}
     * </blockquote>
     * 
     *  其中{@code v}定义为：
     * 
     * <blockquote>
     *  {@code long v = DoubledoubleToLongBits(thisdoubleValue());}
     * </blockquote>
     * 
     * 
     * @return  a {@code hash code} value for this object.
     */
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code double} value; compatible with
     * {@code Double.hashCode()}.
     *
     * <p>
     *  返回{@code double}值的哈希码;兼容{@code DoublehashCode()}
     * 
     * 
     * @param value the value to hash
     * @return a hash code value for a {@code double} value.
     * @since 1.8
     */
    public static int hashCode(double value) {
        long bits = doubleToLongBits(value);
        return (int)(bits ^ (bits >>> 32));
    }

    /**
     * Compares this object against the specified object.  The result
     * is {@code true} if and only if the argument is not
     * {@code null} and is a {@code Double} object that
     * represents a {@code double} that has the same value as the
     * {@code double} represented by this object. For this
     * purpose, two {@code double} values are considered to be
     * the same if and only if the method {@link
     * #doubleToLongBits(double)} returns the identical
     * {@code long} value when applied to each.
     *
     * <p>Note that in most cases, for two instances of class
     * {@code Double}, {@code d1} and {@code d2}, the
     * value of {@code d1.equals(d2)} is {@code true} if and
     * only if
     *
     * <blockquote>
     *  {@code d1.doubleValue() == d2.doubleValue()}
     * </blockquote>
     *
     * <p>also has the value {@code true}. However, there are two
     * exceptions:
     * <ul>
     * <li>If {@code d1} and {@code d2} both represent
     *     {@code Double.NaN}, then the {@code equals} method
     *     returns {@code true}, even though
     *     {@code Double.NaN==Double.NaN} has the value
     *     {@code false}.
     * <li>If {@code d1} represents {@code +0.0} while
     *     {@code d2} represents {@code -0.0}, or vice versa,
     *     the {@code equal} test has the value {@code false},
     *     even though {@code +0.0==-0.0} has the value {@code true}.
     * </ul>
     * This definition allows hash tables to operate properly.
     * <p>
     * 将此对象与指定的对象进行比较当且仅当参数不是{@code null}并且是表示具有相同值的{@code double}的{@code Double}对象时,结果是{@code true}作为由此对象表示
     * 的{@code double}为此目的,当且仅当方法{@link #doubleToLongBits(double)}返回相同的{@code long)时,两个{@code double}值被认为是相同
     * 的}值应用于每个。
     * 
     *  <p>请注意,在大多数情况下,对于类{@code Double},{@code d1}和{@code d2}的两个实例,{@code d1equals(d2)}的值为{@code true} if只有
     * 如果。
     * 
     * <blockquote>
     *  {@code d1doubleValue()== d2doubleValue()}
     * </blockquote>
     * 
     *  <p>也有值{@code true}但是,有两个例外：
     * <ul>
     * <li>如果{@code d1}和{@code d2}都代表{@code DoubleNaN},则{@code equals}方法会返回{@code true},即使{@code DoubleNaN == DoubleNaN}
     * 值{@code false} <li>如果{@code d1}表示{@code +00},而{@code d2}表示{@code -00},反之亦然,{@code equal} {@code false}
     * ,即使{@code +00 ==  -  00}的值为{@code true}。
     * </ul>
     *  此定义允许散列表正常操作
     * 
     * 
     * @param   obj   the object to compare with.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     * @see java.lang.Double#doubleToLongBits(double)
     */
    public boolean equals(Object obj) {
        return (obj instanceof Double)
               && (doubleToLongBits(((Double)obj).value) ==
                      doubleToLongBits(value));
    }

    /**
     * Returns a representation of the specified floating-point value
     * according to the IEEE 754 floating-point "double
     * format" bit layout.
     *
     * <p>Bit 63 (the bit that is selected by the mask
     * {@code 0x8000000000000000L}) represents the sign of the
     * floating-point number. Bits
     * 62-52 (the bits that are selected by the mask
     * {@code 0x7ff0000000000000L}) represent the exponent. Bits 51-0
     * (the bits that are selected by the mask
     * {@code 0x000fffffffffffffL}) represent the significand
     * (sometimes called the mantissa) of the floating-point number.
     *
     * <p>If the argument is positive infinity, the result is
     * {@code 0x7ff0000000000000L}.
     *
     * <p>If the argument is negative infinity, the result is
     * {@code 0xfff0000000000000L}.
     *
     * <p>If the argument is NaN, the result is
     * {@code 0x7ff8000000000000L}.
     *
     * <p>In all cases, the result is a {@code long} integer that, when
     * given to the {@link #longBitsToDouble(long)} method, will produce a
     * floating-point value the same as the argument to
     * {@code doubleToLongBits} (except all NaN values are
     * collapsed to a single "canonical" NaN value).
     *
     * <p>
     *  根据IEEE 754浮点"双格式"位布局返回指定浮点值的表示形式
     * 
     * <p>位63(由掩码{@code 0x8000000000000000L}选择的位)表示浮点数的符号位62-52(由掩码{@code 0x7ff0000000000000L}选择的位)表示指数位51-0
     * (由掩码{@code 0x000fffffffffffffL}选择的位)表示浮点数的有效位数(有时称为尾数)。
     * 
     *  <p>如果参数是正无穷大,则结果是{@code 0x7ff0000000000000L}
     * 
     *  <p>如果参数为负无穷大,则结果为{@code 0xfff0000000000000L}
     * 
     *  <p>如果参数是NaN,则结果是{@code 0x7ff8000000000000L}
     * 
     * <p>在所有情况下,结果都是{@code long}整数,当给定{@link #longBitsToDouble(long)}方法时,将产生一个与{@code doubleToLongBits}(除了所
     * 有NaN值被折叠为单个"规范"NaN值)。
     * 
     * 
     * @param   value   a {@code double} precision floating-point number.
     * @return the bits that represent the floating-point number.
     */
    public static long doubleToLongBits(double value) {
        long result = doubleToRawLongBits(value);
        // Check for NaN based on values of bit fields, maximum
        // exponent and nonzero significand.
        if ( ((result & DoubleConsts.EXP_BIT_MASK) ==
              DoubleConsts.EXP_BIT_MASK) &&
             (result & DoubleConsts.SIGNIF_BIT_MASK) != 0L)
            result = 0x7ff8000000000000L;
        return result;
    }

    /**
     * Returns a representation of the specified floating-point value
     * according to the IEEE 754 floating-point "double
     * format" bit layout, preserving Not-a-Number (NaN) values.
     *
     * <p>Bit 63 (the bit that is selected by the mask
     * {@code 0x8000000000000000L}) represents the sign of the
     * floating-point number. Bits
     * 62-52 (the bits that are selected by the mask
     * {@code 0x7ff0000000000000L}) represent the exponent. Bits 51-0
     * (the bits that are selected by the mask
     * {@code 0x000fffffffffffffL}) represent the significand
     * (sometimes called the mantissa) of the floating-point number.
     *
     * <p>If the argument is positive infinity, the result is
     * {@code 0x7ff0000000000000L}.
     *
     * <p>If the argument is negative infinity, the result is
     * {@code 0xfff0000000000000L}.
     *
     * <p>If the argument is NaN, the result is the {@code long}
     * integer representing the actual NaN value.  Unlike the
     * {@code doubleToLongBits} method,
     * {@code doubleToRawLongBits} does not collapse all the bit
     * patterns encoding a NaN to a single "canonical" NaN
     * value.
     *
     * <p>In all cases, the result is a {@code long} integer that,
     * when given to the {@link #longBitsToDouble(long)} method, will
     * produce a floating-point value the same as the argument to
     * {@code doubleToRawLongBits}.
     *
     * <p>
     *  根据IEEE 754浮点"双格式"位布局返回指定浮点值的表示,保留非数字(NaN)值
     * 
     * <p>位63(由掩码{@code 0x8000000000000000L}选择的位)表示浮点数的符号位62-52(由掩码{@code 0x7ff0000000000000L}选择的位)表示指数位51-0
     * (由掩码{@code 0x000fffffffffffffL}选择的位)表示浮点数的有效位数(有时称为尾数)。
     * 
     *  <p>如果参数是正无穷大,则结果是{@code 0x7ff0000000000000L}
     * 
     *  <p>如果参数为负无穷大,则结果为{@code 0xfff0000000000000L}
     * 
     * <p>如果参数是NaN,结果是表示实际NaN值的{@code long}整数与{@code doubleToLongBits}方法不同,{@code doubleToRawLongBits}不会将编码N
     * aN的所有位模式折叠为单"规范"NaN值。
     * 
     *  <p>在所有情况下,结果是一个{@code long}整数,当给定{@link #longBitsToDouble(long)}方法时,将产生一个与{@code doubleToRawLongBits}
     * 。
     * 
     * 
     * @param   value   a {@code double} precision floating-point number.
     * @return the bits that represent the floating-point number.
     * @since 1.3
     */
    public static native long doubleToRawLongBits(double value);

    /**
     * Returns the {@code double} value corresponding to a given
     * bit representation.
     * The argument is considered to be a representation of a
     * floating-point value according to the IEEE 754 floating-point
     * "double format" bit layout.
     *
     * <p>If the argument is {@code 0x7ff0000000000000L}, the result
     * is positive infinity.
     *
     * <p>If the argument is {@code 0xfff0000000000000L}, the result
     * is negative infinity.
     *
     * <p>If the argument is any value in the range
     * {@code 0x7ff0000000000001L} through
     * {@code 0x7fffffffffffffffL} or in the range
     * {@code 0xfff0000000000001L} through
     * {@code 0xffffffffffffffffL}, the result is a NaN.  No IEEE
     * 754 floating-point operation provided by Java can distinguish
     * between two NaN values of the same type with different bit
     * patterns.  Distinct values of NaN are only distinguishable by
     * use of the {@code Double.doubleToRawLongBits} method.
     *
     * <p>In all other cases, let <i>s</i>, <i>e</i>, and <i>m</i> be three
     * values that can be computed from the argument:
     *
     * <blockquote><pre>{@code
     * int s = ((bits >> 63) == 0) ? 1 : -1;
     * int e = (int)((bits >> 52) & 0x7ffL);
     * long m = (e == 0) ?
     *                 (bits & 0xfffffffffffffL) << 1 :
     *                 (bits & 0xfffffffffffffL) | 0x10000000000000L;
     * }</pre></blockquote>
     *
     * Then the floating-point result equals the value of the mathematical
     * expression <i>s</i>&middot;<i>m</i>&middot;2<sup><i>e</i>-1075</sup>.
     *
     * <p>Note that this method may not be able to return a
     * {@code double} NaN with exactly same bit pattern as the
     * {@code long} argument.  IEEE 754 distinguishes between two
     * kinds of NaNs, quiet NaNs and <i>signaling NaNs</i>.  The
     * differences between the two kinds of NaN are generally not
     * visible in Java.  Arithmetic operations on signaling NaNs turn
     * them into quiet NaNs with a different, but often similar, bit
     * pattern.  However, on some processors merely copying a
     * signaling NaN also performs that conversion.  In particular,
     * copying a signaling NaN to return it to the calling method
     * may perform this conversion.  So {@code longBitsToDouble}
     * may not be able to return a {@code double} with a
     * signaling NaN bit pattern.  Consequently, for some
     * {@code long} values,
     * {@code doubleToRawLongBits(longBitsToDouble(start))} may
     * <i>not</i> equal {@code start}.  Moreover, which
     * particular bit patterns represent signaling NaNs is platform
     * dependent; although all NaN bit patterns, quiet or signaling,
     * must be in the NaN range identified above.
     *
     * <p>
     *  返回对应于给定位表示的{@code double}值根据IEEE 754浮点"双格式"位布局,该参数被认为是浮点值的表示
     * 
     *  <p>如果参数是{@code 0x7ff0000000000000L},则结果为正无穷大
     * 
     * <p>如果参数是{@code 0xfff0000000000000L},则结果为负无穷大
     * 
     *  <p>如果参数是{@code 0x7ff0000000000001L}到{@code 0x7ff0000000000001L}到{@code 0x7ffff0000ff001ff}范围内的任何值,或者
     * 在{@code 0xfff0000000000001L}到{@code 0xffff0000000000001L}到{@code 0xffffffffffffffffL}之间的任何值,则结果是NaN否I
     * EEE 754浮点Java提供的操作可以区分具有不同位模式的相同类型的两个NaN值。
     * NaN的独特值只能通过使用{@code DoubledoubleToRawLongBits}方法来区分。
     * 
     *  <p>在所有其他情况下,让<i> s </i>,<e> e和<m>是可以从参数计算的三个值：
     * 
     * <blockquote> <pre> {@ code int s =((bits >> 63)== 0)? 1：-1; int e =(int)((bits >> 52)&0x7ffL); long m =(e == 0)? (bits&0xfffffffffffffL)<< 1：(bits&0xfffffffffffffL)| 0x10000000000000L; } </pre>
     *  </blockquote>。
     * 
     *  然后,浮点结果等于数学表达式的值<i> </i>&lt; i&gt;&lt; i&gt;&lt; i&gt;&lt; i&gt; >
     * 
     * <p>请注意,此方法可能无法返回与{@code long}参数完全相同位模式的{@code double} NaN。
     * IEEE 754区分两种NaN,安静NaN和信号NaN两种NaN之间的差异通常在Java中不可见。对信令NaN的算术运算将它们转换成具有不同但通常相似的位模式的安静NaN。
     * 然而,在一些处理器上,仅复制信令NaN也执行该转换特别地,复制信令NaN以将其返回到调用方法可以执行该转换。
     * 因此,{@code longBitsToDouble}可能不能返回具有信令NaN位模式的{@code double}因此,对于一些{@code long}值,{@code doubleToRawLongBits(longBitsToDouble(start))}
     * 可能不等于{@code start}此外,哪些特定的位模式表示信令NaNs是平台相关的;尽管所有NaN比特模式,安静或信令,必须在上述识别的NaN范围内。
     * 然而,在一些处理器上,仅复制信令NaN也执行该转换特别地,复制信令NaN以将其返回到调用方法可以执行该转换。
     * 
     * @param   bits   any {@code long} integer.
     * @return  the {@code double} floating-point value with the same
     *          bit pattern.
     */
    public static native double longBitsToDouble(long bits);

    /**
     * Compares two {@code Double} objects numerically.  There
     * are two ways in which comparisons performed by this method
     * differ from those performed by the Java language numerical
     * comparison operators ({@code <, <=, ==, >=, >})
     * when applied to primitive {@code double} values:
     * <ul><li>
     *          {@code Double.NaN} is considered by this method
     *          to be equal to itself and greater than all other
     *          {@code double} values (including
     *          {@code Double.POSITIVE_INFINITY}).
     * <li>
     *          {@code 0.0d} is considered by this method to be greater
     *          than {@code -0.0d}.
     * </ul>
     * This ensures that the <i>natural ordering</i> of
     * {@code Double} objects imposed by this method is <i>consistent
     * with equals</i>.
     *
     * <p>
     * 
     * 
     * @param   anotherDouble   the {@code Double} to be compared.
     * @return  the value {@code 0} if {@code anotherDouble} is
     *          numerically equal to this {@code Double}; a value
     *          less than {@code 0} if this {@code Double}
     *          is numerically less than {@code anotherDouble};
     *          and a value greater than {@code 0} if this
     *          {@code Double} is numerically greater than
     *          {@code anotherDouble}.
     *
     * @since   1.2
     */
    public int compareTo(Double anotherDouble) {
        return Double.compare(value, anotherDouble.value);
    }

    /**
     * Compares the two specified {@code double} values. The sign
     * of the integer value returned is the same as that of the
     * integer that would be returned by the call:
     * <pre>
     *    new Double(d1).compareTo(new Double(d2))
     * </pre>
     *
     * <p>
     * 以数字方式比较两个{@code Double}对象此方法执行的比较有两种方式与Java语言数值比较运算符({@code <,<=,==,> =,>})执行的方式不同,应用于原始{@code double}
     * 值：<ul> <li>此方法认为{@code DoubleNaN}等于其自身且大于所有其他{@code double}值(包括{@code DoublePOSITIVE_INFINITY})。
     * <li>
     *  此方法认为{@code 00d}大于{@code -00d}
     * </ul>
     *  这样可以确保此方法强加的{@code Double}对象的<i>自然排序</i>与equals </i>
     * 
     * 
     * @param   d1        the first {@code double} to compare
     * @param   d2        the second {@code double} to compare
     * @return  the value {@code 0} if {@code d1} is
     *          numerically equal to {@code d2}; a value less than
     *          {@code 0} if {@code d1} is numerically less than
     *          {@code d2}; and a value greater than {@code 0}
     *          if {@code d1} is numerically greater than
     *          {@code d2}.
     * @since 1.4
     */
    public static int compare(double d1, double d2) {
        if (d1 < d2)
            return -1;           // Neither val is NaN, thisVal is smaller
        if (d1 > d2)
            return 1;            // Neither val is NaN, thisVal is larger

        // Cannot use doubleToRawLongBits because of possibility of NaNs.
        long thisBits    = Double.doubleToLongBits(d1);
        long anotherBits = Double.doubleToLongBits(d2);

        return (thisBits == anotherBits ?  0 : // Values are equal
                (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
                 1));                          // (0.0, -0.0) or (NaN, !NaN)
    }

    /**
     * Adds two {@code double} values together as per the + operator.
     *
     * <p>
     * 比较两个指定的{@code double}值返回的整数值的符号与调用返回的整数的符号相同：
     * <pre>
     *  new Double(d1)compareTo(new Double(d2))
     * </pre>
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @jls 4.2.4 Floating-Point Operations
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static double sum(double a, double b) {
        return a + b;
    }

    /**
     * Returns the greater of two {@code double} values
     * as if by calling {@link Math#max(double, double) Math.max}.
     *
     * <p>
     *  根据+运算符将两个{@code double}值添加到一起
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the greater of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static double max(double a, double b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code double} values
     * as if by calling {@link Math#min(double, double) Math.min}.
     *
     * <p>
     *  返回两个{@code double}值中的较大值,如同调用{@link Math#max(double,double)Mathmax}
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the smaller of {@code a} and {@code b}.
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static double min(double a, double b) {
        return Math.min(a, b);
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -9172774392245257468L;
}
