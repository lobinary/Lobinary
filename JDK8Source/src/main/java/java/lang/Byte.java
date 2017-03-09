/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 *
 * The {@code Byte} class wraps a value of primitive type {@code byte}
 * in an object.  An object of type {@code Byte} contains a single
 * field whose type is {@code byte}.
 *
 * <p>In addition, this class provides several methods for converting
 * a {@code byte} to a {@code String} and a {@code String} to a {@code
 * byte}, as well as other constants and methods useful when dealing
 * with a {@code byte}.
 *
 * <p>
 *  {@code Byte}类在对象中封装了原始类型{@code byte}的值。类型为{@code Byte}的对象包含一个类型为{@code byte}的单个字段。
 * 
 *  <p>此外,此类提供了几种方法,用于将{@code byte}转换为{@code String}和{@code String}转换为{@code byte},以及其他常数和方法处理一个{@code byte}
 * 。
 * 
 * 
 * @author  Nakul Saraiya
 * @author  Joseph D. Darcy
 * @see     java.lang.Number
 * @since   JDK1.1
 */
public final class Byte extends Number implements Comparable<Byte> {

    /**
     * A constant holding the minimum value a {@code byte} can
     * have, -2<sup>7</sup>.
     * <p>
     *  保持最小值a {@code byte}的常数可以具有,-2 <sup> 7 </sup>。
     * 
     */
    public static final byte   MIN_VALUE = -128;

    /**
     * A constant holding the maximum value a {@code byte} can
     * have, 2<sup>7</sup>-1.
     * <p>
     *  保持最大值a {@code byte}的常数可以具有2 <sup> 7 </sup> -1。
     * 
     */
    public static final byte   MAX_VALUE = 127;

    /**
     * The {@code Class} instance representing the primitive type
     * {@code byte}.
     * <p>
     *  代表原始类型{@code byte}的{@code Class}实例。
     * 
     */
    @SuppressWarnings("unchecked")
    public static final Class<Byte>     TYPE = (Class<Byte>) Class.getPrimitiveClass("byte");

    /**
     * Returns a new {@code String} object representing the
     * specified {@code byte}. The radix is assumed to be 10.
     *
     * <p>
     *  返回表示指定的{@code byte}的新{@code String}对象。假设基数为10。
     * 
     * 
     * @param b the {@code byte} to be converted
     * @return the string representation of the specified {@code byte}
     * @see java.lang.Integer#toString(int)
     */
    public static String toString(byte b) {
        return Integer.toString((int)b, 10);
    }

    private static class ByteCache {
        private ByteCache(){}

        static final Byte cache[] = new Byte[-(-128) + 127 + 1];

        static {
            for(int i = 0; i < cache.length; i++)
                cache[i] = new Byte((byte)(i - 128));
        }
    }

    /**
     * Returns a {@code Byte} instance representing the specified
     * {@code byte} value.
     * If a new {@code Byte} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Byte(byte)}, as this method is likely to yield
     * significantly better space and time performance since
     * all byte values are cached.
     *
     * <p>
     *  返回表示指定的{@code byte}值的{@code Byte}实例。
     * 如果不需要新的{@code Byte}实例,通常应该优先使用构造函数{@link #Byte(byte)},因为这个方法很可能产生明显更好的空间和时间性能,因为所有字节值将被缓存。
     * 
     * 
     * @param  b a byte value.
     * @return a {@code Byte} instance representing {@code b}.
     * @since  1.5
     */
    public static Byte valueOf(byte b) {
        final int offset = 128;
        return ByteCache.cache[(int)b + offset];
    }

    /**
     * Parses the string argument as a signed {@code byte} in the
     * radix specified by the second argument. The characters in the
     * string must all be digits, of the specified radix (as
     * determined by whether {@link java.lang.Character#digit(char,
     * int)} returns a nonnegative value) except that the first
     * character may be an ASCII minus sign {@code '-'}
     * ({@code '\u005Cu002D'}) to indicate a negative value or an
     * ASCII plus sign {@code '+'} ({@code '\u005Cu002B'}) to
     * indicate a positive value.  The resulting {@code byte} value is
     * returned.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     * <li> The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li> The radix is either smaller than {@link
     * java.lang.Character#MIN_RADIX} or larger than {@link
     * java.lang.Character#MAX_RADIX}.
     *
     * <li> Any character of the string is not a digit of the
     * specified radix, except that the first character may be a minus
     * sign {@code '-'} ({@code '\u005Cu002D'}) or plus sign
     * {@code '+'} ({@code '\u005Cu002B'}) provided that the
     * string is longer than length 1.
     *
     * <li> The value represented by the string is not a value of type
     * {@code byte}.
     * </ul>
     *
     * <p>
     * 将字符串参数解析为由第二个参数指定的基数中的带符号的{@code byte}。
     * 字符串中的字符必须都是指定基数的数字(由{@link java.lang.Character#digit(char,int)}返回非负值),除非第一个字符可能是ASCII减标记{@code' - '}(
     * {@code'\ u005Cu002D'})以指示负值或ASCII加号{@code'+'}({@code'\ u005Cu002B'}) 。
     * 将字符串参数解析为由第二个参数指定的基数中的带符号的{@code byte}。返回生成的{@code byte}值。
     * 
     *  <p>如果发生以下任何情况,将抛出{@code NumberFormatException}类型的异常：
     * <ul>
     *  <li>第一个参数是{@code null}或是长度为零的字符串。
     * 
     *  <li>基数小于{@link java.lang.Character#MIN_RADIX}或大于{@link java.lang.Character#MAX_RADIX}。
     * 
     *  <li>字符串的任何字符都不是指定基数的数字,除了第一个字符可以是减号{@code' - '}({@code'\ u005Cu002D'})或加号{@code '+'}({@code'\ u005Cu002B'}
     * ),前提是字符串长度大于长度1。
     * 
     *  <li>字符串表示的值不是类型为{@code byte}的值。
     * </ul>
     * 
     * 
     * @param s         the {@code String} containing the
     *                  {@code byte}
     *                  representation to be parsed
     * @param radix     the radix to be used while parsing {@code s}
     * @return          the {@code byte} value represented by the string
     *                   argument in the specified radix
     * @throws          NumberFormatException If the string does
     *                  not contain a parsable {@code byte}.
     */
    public static byte parseByte(String s, int radix)
        throws NumberFormatException {
        int i = Integer.parseInt(s, radix);
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException(
                "Value out of range. Value:\"" + s + "\" Radix:" + radix);
        return (byte)i;
    }

    /**
     * Parses the string argument as a signed decimal {@code
     * byte}. The characters in the string must all be decimal digits,
     * except that the first character may be an ASCII minus sign
     * {@code '-'} ({@code '\u005Cu002D'}) to indicate a negative
     * value or an ASCII plus sign {@code '+'}
     * ({@code '\u005Cu002B'}) to indicate a positive value. The
     * resulting {@code byte} value is returned, exactly as if the
     * argument and the radix 10 were given as arguments to the {@link
     * #parseByte(java.lang.String, int)} method.
     *
     * <p>
     * 将字符串参数解析为带符号的十进制{@code byte}。
     * 字符串中的字符必须都是十进制数字,除了第一个字符可以是一个ASCII减号{@code' - '}({@code'\ u005Cu002D'}),表示负值或ASCII加号{ @code'+'}({@code'\ u005Cu002B'}
     * )以指示正值。
     * 将字符串参数解析为带符号的十进制{@code byte}。
     * 返回生成的{@code byte}值,就像参数和基数10作为{@link #parseByte(java.lang.String,int)}方法的参数一样。
     * 
     * 
     * @param s         a {@code String} containing the
     *                  {@code byte} representation to be parsed
     * @return          the {@code byte} value represented by the
     *                  argument in decimal
     * @throws          NumberFormatException if the string does not
     *                  contain a parsable {@code byte}.
     */
    public static byte parseByte(String s) throws NumberFormatException {
        return parseByte(s, 10);
    }

    /**
     * Returns a {@code Byte} object holding the value
     * extracted from the specified {@code String} when parsed
     * with the radix given by the second argument. The first argument
     * is interpreted as representing a signed {@code byte} in
     * the radix specified by the second argument, exactly as if the
     * argument were given to the {@link #parseByte(java.lang.String,
     * int)} method. The result is a {@code Byte} object that
     * represents the {@code byte} value specified by the string.
     *
     * <p> In other words, this method returns a {@code Byte} object
     * equal to the value of:
     *
     * <blockquote>
     * {@code new Byte(Byte.parseByte(s, radix))}
     * </blockquote>
     *
     * <p>
     *  返回一个{@code Byte}对象,当使用第二个参数给出的基数解析时,该对象保存从指定的{@code String}中提取的值。
     * 第一个参数被解释为表示由第二个参数指定的基数中的一个有符号的{@code byte},就像该参数被赋予{@link #parseByte(java.lang.String,int)}方法一样。
     * 结果是一个{@code Byte}对象,表示由字符串指定的{@code byte}值。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Byte}对象：
     * 
     * <blockquote>
     *  {@code new Byte(Byte.parseByte(s,radix))}
     * </blockquote>
     * 
     * 
     * @param s         the string to be parsed
     * @param radix     the radix to be used in interpreting {@code s}
     * @return          a {@code Byte} object holding the value
     *                  represented by the string argument in the
     *                  specified radix.
     * @throws          NumberFormatException If the {@code String} does
     *                  not contain a parsable {@code byte}.
     */
    public static Byte valueOf(String s, int radix)
        throws NumberFormatException {
        return valueOf(parseByte(s, radix));
    }

    /**
     * Returns a {@code Byte} object holding the value
     * given by the specified {@code String}. The argument is
     * interpreted as representing a signed decimal {@code byte},
     * exactly as if the argument were given to the {@link
     * #parseByte(java.lang.String)} method. The result is a
     * {@code Byte} object that represents the {@code byte}
     * value specified by the string.
     *
     * <p> In other words, this method returns a {@code Byte} object
     * equal to the value of:
     *
     * <blockquote>
     * {@code new Byte(Byte.parseByte(s))}
     * </blockquote>
     *
     * <p>
     *  返回一个拥有由指定的{@code String}给定的值的{@code Byte}对象。
     * 该参数被解释为表示一个有符号的十进制{@code byte},就像参数被赋予{@link #parseByte(java.lang.String)}方法一样。
     * 结果是一个{@code Byte}对象,表示由字符串指定的{@code byte}值。
     * 
     * <p>换句话说,此方法返回等于以下值的{@code Byte}对象：
     * 
     * <blockquote>
     *  {@code new Byte(Byte.parseByte(s))}
     * </blockquote>
     * 
     * 
     * @param s         the string to be parsed
     * @return          a {@code Byte} object holding the value
     *                  represented by the string argument
     * @throws          NumberFormatException If the {@code String} does
     *                  not contain a parsable {@code byte}.
     */
    public static Byte valueOf(String s) throws NumberFormatException {
        return valueOf(s, 10);
    }

    /**
     * Decodes a {@code String} into a {@code Byte}.
     * Accepts decimal, hexadecimal, and octal numbers given by
     * the following grammar:
     *
     * <blockquote>
     * <dl>
     * <dt><i>DecodableString:</i>
     * <dd><i>Sign<sub>opt</sub> DecimalNumeral</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0x} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0X} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code #} <i>HexDigits</i>
     * <dd><i>Sign<sub>opt</sub></i> {@code 0} <i>OctalDigits</i>
     *
     * <dt><i>Sign:</i>
     * <dd>{@code -}
     * <dd>{@code +}
     * </dl>
     * </blockquote>
     *
     * <i>DecimalNumeral</i>, <i>HexDigits</i>, and <i>OctalDigits</i>
     * are as defined in section 3.10.1 of
     * <cite>The Java&trade; Language Specification</cite>,
     * except that underscores are not accepted between digits.
     *
     * <p>The sequence of characters following an optional
     * sign and/or radix specifier ("{@code 0x}", "{@code 0X}",
     * "{@code #}", or leading zero) is parsed as by the {@code
     * Byte.parseByte} method with the indicated radix (10, 16, or 8).
     * This sequence of characters must represent a positive value or
     * a {@link NumberFormatException} will be thrown.  The result is
     * negated if first character of the specified {@code String} is
     * the minus sign.  No whitespace characters are permitted in the
     * {@code String}.
     *
     * <p>
     *  将{@code String}解码为{@code Byte}。接受由以下语法提供的十进制,十六进制和八进制数：
     * 
     * <blockquote>
     * <dl>
     *  <dt> <i>可解码字符串：</i> <dd> <i>签署<sub> opt </sub> DecimalNumeral </i> <dd> <i> > {@code 0x} <i> HexDigi
     * ts </i> <dd> <i> Sign <sub> opt </sub> </i> {@code 0X} <i> HexDigits </i> <dd> < i>签署<sub> opt </sub>
     *  </i> {@code#} <i> HexDigits </i> <dd> <i> 0} <i> OctalDigits </i>。
     * 
     *  <dt> <i>签名：</i> <dd> {@ code  - } <dd> {@ code +}
     * </dl>
     * </blockquote>
     * 
     *  <i> DecimalNumeral </i>,<HexDigits </i>和<OctalDigits </i>的定义在<cite> Java&trade;语言规范</cite>,除了在数字之间不接
     * 受下划线。
     * 
     *  <p>可选符号和/或基数说明符("{@code 0x}","{@code 0X}","{@code#}"或前导零)后面的字符序列按使用指定基数(10,16或8)的{@code Byte.parseByte}
     * 方法。
     * 这个字符序列必须表示正值,否则将抛出{@link NumberFormatException}。如果指定的{@code String}的第一个字符是减号,则结果将被否定。
     *  {@code String}中不允许使用空格字符。
     * 
     * 
     * @param     nm the {@code String} to decode.
     * @return   a {@code Byte} object holding the {@code byte}
     *          value represented by {@code nm}
     * @throws  NumberFormatException  if the {@code String} does not
     *            contain a parsable {@code byte}.
     * @see java.lang.Byte#parseByte(java.lang.String, int)
     */
    public static Byte decode(String nm) throws NumberFormatException {
        int i = Integer.decode(nm);
        if (i < MIN_VALUE || i > MAX_VALUE)
            throw new NumberFormatException(
                    "Value " + i + " out of range from input " + nm);
        return valueOf((byte)i);
    }

    /**
     * The value of the {@code Byte}.
     *
     * <p>
     *  {@code Byte}的值。
     * 
     * 
     * @serial
     */
    private final byte value;

    /**
     * Constructs a newly allocated {@code Byte} object that
     * represents the specified {@code byte} value.
     *
     * <p>
     *  构造一个新分配的{@code Byte}对象,表示指定的{@code byte}值。
     * 
     * 
     * @param value     the value to be represented by the
     *                  {@code Byte}.
     */
    public Byte(byte value) {
        this.value = value;
    }

    /**
     * Constructs a newly allocated {@code Byte} object that
     * represents the {@code byte} value indicated by the
     * {@code String} parameter. The string is converted to a
     * {@code byte} value in exactly the manner used by the
     * {@code parseByte} method for radix 10.
     *
     * <p>
     * 构造一个新分配的{@code Byte}对象,该对象表示由{@code String}参数指示的{@code byte}值。
     * 该字符串被转换为{@code byte}值,正好与{@code parseByte}方法使用的基数10相同。
     * 
     * 
     * @param s         the {@code String} to be converted to a
     *                  {@code Byte}
     * @throws           NumberFormatException If the {@code String}
     *                  does not contain a parsable {@code byte}.
     * @see        java.lang.Byte#parseByte(java.lang.String, int)
     */
    public Byte(String s) throws NumberFormatException {
        this.value = parseByte(s, 10);
    }

    /**
     * Returns the value of this {@code Byte} as a
     * {@code byte}.
     * <p>
     *  将此{@code Byte}的值作为{@code byte}返回。
     * 
     */
    public byte byteValue() {
        return value;
    }

    /**
     * Returns the value of this {@code Byte} as a {@code short} after
     * a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后返回此{@code Byte}的值作为{@code short}。 @jls 5.1.2扩大原始转换
     * 
     */
    public short shortValue() {
        return (short)value;
    }

    /**
     * Returns the value of this {@code Byte} as an {@code int} after
     * a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后,将此{@code Byte}的值作为{@code int}返回。 @jls 5.1.2扩大原始转换
     * 
     */
    public int intValue() {
        return (int)value;
    }

    /**
     * Returns the value of this {@code Byte} as a {@code long} after
     * a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后,将此{@code Byte}的值作为{@code long}返回。 @jls 5.1.2扩大原始转换
     * 
     */
    public long longValue() {
        return (long)value;
    }

    /**
     * Returns the value of this {@code Byte} as a {@code float} after
     * a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后,将此{@code Byte}的值作为{@code float}返回。 @jls 5.1.2扩大原始转换
     * 
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * Returns the value of this {@code Byte} as a {@code double}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后,将此{@code Byte}的值作为{@code double}返回。 @jls 5.1.2扩大原始转换
     * 
     */
    public double doubleValue() {
        return (double)value;
    }

    /**
     * Returns a {@code String} object representing this
     * {@code Byte}'s value.  The value is converted to signed
     * decimal representation and returned as a string, exactly as if
     * the {@code byte} value were given as an argument to the
     * {@link java.lang.Byte#toString(byte)} method.
     *
     * <p>
     *  返回表示此{@code Byte}的值的{@code String}对象。
     * 该值将转换为带符号的十进制表示形式,并以字符串形式返回,就像{@code byte}值作为{@link java.lang.Byte#toString(byte)}方法的参数给出。
     * 
     * 
     * @return  a string representation of the value of this object in
     *          base&nbsp;10.
     */
    public String toString() {
        return Integer.toString((int)value);
    }

    /**
     * Returns a hash code for this {@code Byte}; equal to the result
     * of invoking {@code intValue()}.
     *
     * <p>
     *  返回此{@code Byte}的哈希码;等于调用{@code intValue()}的结果。
     * 
     * 
     * @return a hash code value for this {@code Byte}
     */
    @Override
    public int hashCode() {
        return Byte.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code byte} value; compatible with
     * {@code Byte.hashCode()}.
     *
     * <p>
     *  返回{@code byte}值的哈希码;兼容{@code Byte.hashCode()}。
     * 
     * 
     * @param value the value to hash
     * @return a hash code value for a {@code byte} value.
     * @since 1.8
     */
    public static int hashCode(byte value) {
        return (int)value;
    }

    /**
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is a {@code Byte} object that
     * contains the same {@code byte} value as this object.
     *
     * <p>
     * 将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},并且是包含与此对象相同的{@code byte}值的{@code Byte}对象。
     * 
     * 
     * @param obj       the object to compare with
     * @return          {@code true} if the objects are the same;
     *                  {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Byte) {
            return value == ((Byte)obj).byteValue();
        }
        return false;
    }

    /**
     * Compares two {@code Byte} objects numerically.
     *
     * <p>
     *  以数字方式比较两个{@code Byte}对象。
     * 
     * 
     * @param   anotherByte   the {@code Byte} to be compared.
     * @return  the value {@code 0} if this {@code Byte} is
     *          equal to the argument {@code Byte}; a value less than
     *          {@code 0} if this {@code Byte} is numerically less
     *          than the argument {@code Byte}; and a value greater than
     *           {@code 0} if this {@code Byte} is numerically
     *           greater than the argument {@code Byte} (signed
     *           comparison).
     * @since   1.2
     */
    public int compareTo(Byte anotherByte) {
        return compare(this.value, anotherByte.value);
    }

    /**
     * Compares two {@code byte} values numerically.
     * The value returned is identical to what would be returned by:
     * <pre>
     *    Byte.valueOf(x).compareTo(Byte.valueOf(y))
     * </pre>
     *
     * <p>
     *  以数字比较两个{@code byte}值。返回的值与由以下内容返回的值相同：
     * <pre>
     *  Byte.valueOf(x).compareTo(Byte.valueOf(y))
     * </pre>
     * 
     * 
     * @param  x the first {@code byte} to compare
     * @param  y the second {@code byte} to compare
     * @return the value {@code 0} if {@code x == y};
     *         a value less than {@code 0} if {@code x < y}; and
     *         a value greater than {@code 0} if {@code x > y}
     * @since 1.7
     */
    public static int compare(byte x, byte y) {
        return x - y;
    }

    /**
     * Converts the argument to an {@code int} by an unsigned
     * conversion.  In an unsigned conversion to an {@code int}, the
     * high-order 24 bits of the {@code int} are zero and the
     * low-order 8 bits are equal to the bits of the {@code byte} argument.
     *
     * Consequently, zero and positive {@code byte} values are mapped
     * to a numerically equal {@code int} value and negative {@code
     * byte} values are mapped to an {@code int} value equal to the
     * input plus 2<sup>8</sup>.
     *
     * <p>
     *  通过无符号转换将参数转换为{@code int}。在对{@code int}的无符号转换中,{@code int}的高位24位为零,低位8位等于{@code byte}参数的位。
     * 
     *  因此,零和正的{@code byte}值被映射到数值上等于{@code int}的值,而负的{@code byte}值被映射到等于输入加上2的{@code int} 8 </sup>。
     * 
     * 
     * @param  x the value to convert to an unsigned {@code int}
     * @return the argument converted to {@code int} by an unsigned
     *         conversion
     * @since 1.8
     */
    public static int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }

    /**
     * Converts the argument to a {@code long} by an unsigned
     * conversion.  In an unsigned conversion to a {@code long}, the
     * high-order 56 bits of the {@code long} are zero and the
     * low-order 8 bits are equal to the bits of the {@code byte} argument.
     *
     * Consequently, zero and positive {@code byte} values are mapped
     * to a numerically equal {@code long} value and negative {@code
     * byte} values are mapped to a {@code long} value equal to the
     * input plus 2<sup>8</sup>.
     *
     * <p>
     *  通过无符号转换将参数转换为{@code long}。在对{@code long}的无符号转换中,{@code long}的高阶56位为零,低阶8位等于{@code byte}参数的位。
     * 
     *  因此,零和正的{@code byte}值被映射到数值上等于{@code long}的值,而负的{@code byte}值被映射到等于输入加上2的< 8 </sup>。
     * 
     * 
     * @param  x the value to convert to an unsigned {@code long}
     * @return the argument converted to {@code long} by an unsigned
     *         conversion
     * @since 1.8
     */
    public static long toUnsignedLong(byte x) {
        return ((long) x) & 0xffL;
    }


    /**
     * The number of bits used to represent a {@code byte} value in two's
     * complement binary form.
     *
     * <p>
     *  用于以二进制补码二进制形式表示{@code byte}值的位数。
     * 
     * 
     * @since 1.5
     */
    public static final int SIZE = 8;

    /**
     * The number of bytes used to represent a {@code byte} value in two's
     * complement binary form.
     *
     * <p>
     * 用于以二进制补码二进制形式表示{@code byte}值的字节数。
     * 
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /** use serialVersionUID from JDK 1.1. for interoperability */
    private static final long serialVersionUID = -7183698231559129828L;
}
