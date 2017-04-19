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

import java.lang.annotation.Native;

/**
 * The {@code Integer} class wraps a value of the primitive type
 * {@code int} in an object. An object of type {@code Integer}
 * contains a single field whose type is {@code int}.
 *
 * <p>In addition, this class provides several methods for converting
 * an {@code int} to a {@code String} and a {@code String} to an
 * {@code int}, as well as other constants and methods useful when
 * dealing with an {@code int}.
 *
 * <p>Implementation note: The implementations of the "bit twiddling"
 * methods (such as {@link #highestOneBit(int) highestOneBit} and
 * {@link #numberOfTrailingZeros(int) numberOfTrailingZeros}) are
 * based on material from Henry S. Warren, Jr.'s <i>Hacker's
 * Delight</i>, (Addison Wesley, 2002).
 *
 * <p>
 *  {@code Integer}类将原始类型{@code int}的值封装在对象中。 {@code Integer}类型的对象包含一个类型为{@code int}的字段。
 * 
 *  <p>此外,此类提供了几种方法,用于将{@code int}转换为{@code String}和{@code String}转换为{@code int},以及其他常数和方法处理一个{@code int}
 * 。
 * 
 *  <p>实现注意事项："bit twiddling"方法(如{@link #highestOneBit(int)highestOneBit}和{@link #numberOfTrailingZeros(int)numberOfTrailingZeros}
 * )的实现基于Henry S. Warren, Jr.的</i> Hacker's Delight </i>,(Addison Wesley,2002)。
 * 
 * 
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Josh Bloch
 * @author  Joseph D. Darcy
 * @since JDK1.0
 */
public final class Integer extends Number implements Comparable<Integer> {
    /**
     * A constant holding the minimum value an {@code int} can
     * have, -2<sup>31</sup>.
     * <p>
     *  保持最小值的常数an {@code int}可以具有,-2 <sup> 31 </sup>。
     * 
     */
    @Native public static final int   MIN_VALUE = 0x80000000;

    /**
     * A constant holding the maximum value an {@code int} can
     * have, 2<sup>31</sup>-1.
     * <p>
     *  一个拥有{@code int}可以拥有的最大值的常量,2 <sup> 31 </sup> -1。
     * 
     */
    @Native public static final int   MAX_VALUE = 0x7fffffff;

    /**
     * The {@code Class} instance representing the primitive type
     * {@code int}.
     *
     * <p>
     *  代表原始类型{@code int}的{@code Class}实例。
     * 
     * 
     * @since   JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Integer>  TYPE = (Class<Integer>) Class.getPrimitiveClass("int");

    /**
     * All possible chars for representing a number as a String
     * <p>
     *  用于将数字表示为字符串的所有可能的字符
     * 
     */
    final static char[] digits = {
        '0' , '1' , '2' , '3' , '4' , '5' ,
        '6' , '7' , '8' , '9' , 'a' , 'b' ,
        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    /**
     * Returns a string representation of the first argument in the
     * radix specified by the second argument.
     *
     * <p>If the radix is smaller than {@code Character.MIN_RADIX}
     * or larger than {@code Character.MAX_RADIX}, then the radix
     * {@code 10} is used instead.
     *
     * <p>If the first argument is negative, the first element of the
     * result is the ASCII minus character {@code '-'}
     * ({@code '\u005Cu002D'}). If the first argument is not
     * negative, no sign character appears in the result.
     *
     * <p>The remaining characters of the result represent the magnitude
     * of the first argument. If the magnitude is zero, it is
     * represented by a single zero character {@code '0'}
     * ({@code '\u005Cu0030'}); otherwise, the first character of
     * the representation of the magnitude will not be the zero
     * character.  The following ASCII characters are used as digits:
     *
     * <blockquote>
     *   {@code 0123456789abcdefghijklmnopqrstuvwxyz}
     * </blockquote>
     *
     * These are {@code '\u005Cu0030'} through
     * {@code '\u005Cu0039'} and {@code '\u005Cu0061'} through
     * {@code '\u005Cu007A'}. If {@code radix} is
     * <var>N</var>, then the first <var>N</var> of these characters
     * are used as radix-<var>N</var> digits in the order shown. Thus,
     * the digits for hexadecimal (radix 16) are
     * {@code 0123456789abcdef}. If uppercase letters are
     * desired, the {@link java.lang.String#toUpperCase()} method may
     * be called on the result:
     *
     * <blockquote>
     *  {@code Integer.toString(n, 16).toUpperCase()}
     * </blockquote>
     *
     * <p>
     *  返回由第二个参数指定的基数中的第一个参数的字符串表示形式。
     * 
     *  <p>如果基数小于{@code Character.MIN_RADIX}或大于{@code Character.MAX_RADIX},那么将使用基数{@code 10}。
     * 
     * <p>如果第一个参数为负数,结果的第一个元素是ASCII减去字符{@code' - '}({@code'\ u005Cu002D'})。如果第一个参数不为负,结果中不会出现符号字符。
     * 
     *  <p>结果的其余字符表示第一个参数的大小。如果幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,幅度表示的第一个字符将不是零字符。
     * 以下ASCII字符用作数字：。
     * 
     * <blockquote>
     *  {@code 0123456789abcdefghijklmnopqrstuvwxyz}
     * </blockquote>
     * 
     *  这些是{@code'\ u005Cu0030'}到{@code'\ u005Cu0039'}和{@code'\ u005Cu0061'}到{@code'\ u005Cu007A'}。
     * 如果{@code radix}是<var> N </var>,那么这些字符的第一个<var> N </var>将按照所示的顺序用作radix- <var> N </var>数字。
     * 因此,十六进制(基数16)的数字是{@code 0123456789abcdef}。
     * 如果需要大写字母,可以在结果上调用{@link java.lang.String#toUpperCase()}方法：。
     * 
     * <blockquote>
     *  {@code Integer.toString(n,16).toUpperCase()}
     * </blockquote>
     * 
     * 
     * @param   i       an integer to be converted to a string.
     * @param   radix   the radix to use in the string representation.
     * @return  a string representation of the argument in the specified radix.
     * @see     java.lang.Character#MAX_RADIX
     * @see     java.lang.Character#MIN_RADIX
     */
    public static String toString(int i, int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
            radix = 10;

        /* Use the faster version */
        if (radix == 10) {
            return toString(i);
        }

        char buf[] = new char[33];
        boolean negative = (i < 0);
        int charPos = 32;

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = digits[-(i % radix)];
            i = i / radix;
        }
        buf[charPos] = digits[-i];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (33 - charPos));
    }

    /**
     * Returns a string representation of the first argument as an
     * unsigned integer value in the radix specified by the second
     * argument.
     *
     * <p>If the radix is smaller than {@code Character.MIN_RADIX}
     * or larger than {@code Character.MAX_RADIX}, then the radix
     * {@code 10} is used instead.
     *
     * <p>Note that since the first argument is treated as an unsigned
     * value, no leading sign character is printed.
     *
     * <p>If the magnitude is zero, it is represented by a single zero
     * character {@code '0'} ({@code '\u005Cu0030'}); otherwise,
     * the first character of the representation of the magnitude will
     * not be the zero character.
     *
     * <p>The behavior of radixes and the characters used as digits
     * are the same as {@link #toString(int, int) toString}.
     *
     * <p>
     *  返回第一个参数的字符串表示形式,作为第二个参数指定的基数中的无符号整数值。
     * 
     *  <p>如果基数小于{@code Character.MIN_RADIX}或大于{@code Character.MAX_RADIX},那么将使用基数{@code 10}。
     * 
     *  <p>请注意,由于第一个参数被视为无符号值,因此不会打印前导符号字符。
     * 
     * <p>如果幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,幅度表示的第一个字符将不是零字符。
     * 
     *  <p>基数和用作数字的字符的行为与{@link #toString(int,int)toString}相同。
     * 
     * 
     * @param   i       an integer to be converted to an unsigned string.
     * @param   radix   the radix to use in the string representation.
     * @return  an unsigned string representation of the argument in the specified radix.
     * @see     #toString(int, int)
     * @since 1.8
     */
    public static String toUnsignedString(int i, int radix) {
        return Long.toUnsignedString(toUnsignedLong(i), radix);
    }

    /**
     * Returns a string representation of the integer argument as an
     * unsigned integer in base&nbsp;16.
     *
     * <p>The unsigned integer value is the argument plus 2<sup>32</sup>
     * if the argument is negative; otherwise, it is equal to the
     * argument.  This value is converted to a string of ASCII digits
     * in hexadecimal (base&nbsp;16) with no extra leading
     * {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Integer#parseUnsignedInt(String, int)
     * Integer.parseUnsignedInt(s, 16)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * following characters are used as hexadecimal digits:
     *
     * <blockquote>
     *  {@code 0123456789abcdef}
     * </blockquote>
     *
     * These are the characters {@code '\u005Cu0030'} through
     * {@code '\u005Cu0039'} and {@code '\u005Cu0061'} through
     * {@code '\u005Cu0066'}. If uppercase letters are
     * desired, the {@link java.lang.String#toUpperCase()} method may
     * be called on the result:
     *
     * <blockquote>
     *  {@code Integer.toHexString(n).toUpperCase()}
     * </blockquote>
     *
     * <p>
     *  在基础16中返回整数参数的字符串表示形式,作为无符号整数。
     * 
     *  <p>无符号整数值是参数加上2 <sup> 32 </sup>如果参数为负数;否则,它等于参数。
     * 此值将转换为十六进制(base&nbsp; 16)的ASCII字符串字符串,没有额外的前导{@code 0}。
     * 
     *  <p>参数的值可以通过调用{@link Integer#parseUnsignedInt(String,int)Integer.parseUnsignedInt(s,16)}从返回的字符串{@code s}
     * 中恢复。
     * 
     *  <p>如果无符号幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,无符号幅度的表示的第一个字符将不是零字符。以下字符用作十六进制数字：
     * 
     * <blockquote>
     *  {@code 0123456789abcdef}
     * </blockquote>
     * 
     *  这些是字符{@code'\ u005Cu0030'}到{@code'\ u005Cu0039'}和{@code'\ u005Cu0061'}到{@code'\ u005Cu0066'}。
     * 如果需要大写字母,可以在结果上调用{@link java.lang.String#toUpperCase()}方法：。
     * 
     * <blockquote>
     *  {@code Integer.toHexString(n).toUpperCase()}
     * </blockquote>
     * 
     * 
     * @param   i   an integer to be converted to a string.
     * @return  the string representation of the unsigned integer value
     *          represented by the argument in hexadecimal (base&nbsp;16).
     * @see #parseUnsignedInt(String, int)
     * @see #toUnsignedString(int, int)
     * @since   JDK1.0.2
     */
    public static String toHexString(int i) {
        return toUnsignedString0(i, 4);
    }

    /**
     * Returns a string representation of the integer argument as an
     * unsigned integer in base&nbsp;8.
     *
     * <p>The unsigned integer value is the argument plus 2<sup>32</sup>
     * if the argument is negative; otherwise, it is equal to the
     * argument.  This value is converted to a string of ASCII digits
     * in octal (base&nbsp;8) with no extra leading {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Integer#parseUnsignedInt(String, int)
     * Integer.parseUnsignedInt(s, 8)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * following characters are used as octal digits:
     *
     * <blockquote>
     * {@code 01234567}
     * </blockquote>
     *
     * These are the characters {@code '\u005Cu0030'} through
     * {@code '\u005Cu0037'}.
     *
     * <p>
     * 返回整数参数的字符串表示形式,作为第8个字节中的无符号整数。
     * 
     *  <p>无符号整数值是参数加上2 <sup> 32 </sup>如果参数为负数;否则,它等于参数。此值将转换为八进制(基数为8)的ASCII数字字符串,没有额外的前导{@code 0}。
     * 
     *  <p>参数的值可以通过调用{@link Integer#parseUnsignedInt(String,int)Integer.parseUnsignedInt(s,8)}从返回的字符串{@code s}
     * 中恢复。
     * 
     *  <p>如果无符号幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,无符号幅度的表示的第一个字符将不是零字符。以下字符用作八进制数字：
     * 
     * <blockquote>
     *  {@code 01234567}
     * </blockquote>
     * 
     *  这些是字符{@code'\ u005Cu0030'}到{@code'\ u005Cu0037'}。
     * 
     * 
     * @param   i   an integer to be converted to a string.
     * @return  the string representation of the unsigned integer value
     *          represented by the argument in octal (base&nbsp;8).
     * @see #parseUnsignedInt(String, int)
     * @see #toUnsignedString(int, int)
     * @since   JDK1.0.2
     */
    public static String toOctalString(int i) {
        return toUnsignedString0(i, 3);
    }

    /**
     * Returns a string representation of the integer argument as an
     * unsigned integer in base&nbsp;2.
     *
     * <p>The unsigned integer value is the argument plus 2<sup>32</sup>
     * if the argument is negative; otherwise it is equal to the
     * argument.  This value is converted to a string of ASCII digits
     * in binary (base&nbsp;2) with no extra leading {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Integer#parseUnsignedInt(String, int)
     * Integer.parseUnsignedInt(s, 2)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * characters {@code '0'} ({@code '\u005Cu0030'}) and {@code
     * '1'} ({@code '\u005Cu0031'}) are used as binary digits.
     *
     * <p>
     *  返回整数参数的字符串表示形式,作为基础2中的无符号整数。
     * 
     *  <p>无符号整数值是参数加上2 <sup> 32 </sup>如果参数为负数;否则等于参数。此值将转换为二进制(基础2)的ASCII数字字符串,没有额外的前导{@code 0}。
     * 
     *  <p>参数的值可以通过调用{@link Integer#parseUnsignedInt(String,int)Integer.parseUnsignedInt(s,2)}从返回的字符串{@code s}
     * 中恢复。
     * 
     * <p>如果无符号幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,无符号幅度的表示的第一个字符将不是零字符。
     * 字符{@code'0'}({@code'\ u005Cu0030'})和{@code'1'}({@code'\ u005Cu0031'})用作二进制数字。
     * 
     * 
     * @param   i   an integer to be converted to a string.
     * @return  the string representation of the unsigned integer value
     *          represented by the argument in binary (base&nbsp;2).
     * @see #parseUnsignedInt(String, int)
     * @see #toUnsignedString(int, int)
     * @since   JDK1.0.2
     */
    public static String toBinaryString(int i) {
        return toUnsignedString0(i, 1);
    }

    /**
     * Convert the integer to an unsigned number.
     * <p>
     *  将整数转换为无符号数。
     * 
     */
    private static String toUnsignedString0(int val, int shift) {
        // assert shift > 0 && shift <=5 : "Illegal shift value";
        int mag = Integer.SIZE - Integer.numberOfLeadingZeros(val);
        int chars = Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf = new char[chars];

        formatUnsignedInt(val, shift, buf, 0, chars);

        // Use special constructor which takes over "buf".
        return new String(buf, true);
    }

    /**
     * Format a long (treated as unsigned) into a character buffer.
     * <p>
     *  将long(被视为无符号)格式化为字符缓冲区。
     * 
     * 
     * @param val the unsigned int to format
     * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
     * @param buf the character buffer to write to
     * @param offset the offset in the destination buffer to start at
     * @param len the number of characters to write
     * @return the lowest character  location used
     */
     static int formatUnsignedInt(int val, int shift, char[] buf, int offset, int len) {
        int charPos = len;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[offset + --charPos] = Integer.digits[val & mask];
            val >>>= shift;
        } while (val != 0 && charPos > 0);

        return charPos;
    }

    final static char [] DigitTens = {
        '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
        '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
        '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
        '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
        '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
        '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
        '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
        '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
        '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
        '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
        } ;

    final static char [] DigitOnes = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        } ;

        // I use the "invariant division by multiplication" trick to
        // accelerate Integer.toString.  In particular we want to
        // avoid division by 10.
        //
        // The "trick" has roughly the same performance characteristics
        // as the "classic" Integer.toString code on a non-JIT VM.
        // The trick avoids .rem and .div calls but has a longer code
        // path and is thus dominated by dispatch overhead.  In the
        // JIT case the dispatch overhead doesn't exist and the
        // "trick" is considerably faster than the classic code.
        //
        // TODO-FIXME: convert (x * 52429) into the equiv shift-add
        // sequence.
        //
        // RE:  Division by Invariant Integers using Multiplication
        //      T Gralund, P Montgomery
        //      ACM PLDI 1994
        //

    /**
     * Returns a {@code String} object representing the
     * specified integer. The argument is converted to signed decimal
     * representation and returned as a string, exactly as if the
     * argument and radix 10 were given as arguments to the {@link
     * #toString(int, int)} method.
     *
     * <p>
     *  返回表示指定整数的{@code String}对象。
     * 参数被转换为有符号的十进制表示,并作为字符串返回,正如将参数和radix 10作为{@link #toString(int,int)}方法的参数一样。
     * 
     * 
     * @param   i   an integer to be converted.
     * @return  a string representation of the argument in base&nbsp;10.
     */
    public static String toString(int i) {
        if (i == Integer.MIN_VALUE)
            return "-2147483648";
        int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
        char[] buf = new char[size];
        getChars(i, size, buf);
        return new String(buf, true);
    }

    /**
     * Returns a string representation of the argument as an unsigned
     * decimal value.
     *
     * The argument is converted to unsigned decimal representation
     * and returned as a string exactly as if the argument and radix
     * 10 were given as arguments to the {@link #toUnsignedString(int,
     * int)} method.
     *
     * <p>
     *  以无符号十进制值形式返回参数的字符串表示形式。
     * 
     *  参数转换为无符号十进制表示,并作为字符串返回,就像参数和radix 10作为{@link #toUnsignedString(int,int)}方法的参数一样。
     * 
     * 
     * @param   i  an integer to be converted to an unsigned string.
     * @return  an unsigned string representation of the argument.
     * @see     #toUnsignedString(int, int)
     * @since 1.8
     */
    public static String toUnsignedString(int i) {
        return Long.toString(toUnsignedLong(i));
    }

    /**
     * Places characters representing the integer i into the
     * character array buf. The characters are placed into
     * the buffer backwards starting with the least significant
     * digit at the specified index (exclusive), and working
     * backwards from there.
     *
     * Will fail if i == Integer.MIN_VALUE
     * <p>
     *  将表示整数i的字符放入字符数组buf中。字符从指定索引(独占)处的最低有效数字开始向后放置到缓冲区中,并从那里向后工作。
     * 
     *  如果i == Integer.MIN_VALUE将失败
     * 
     */
    static void getChars(int int数据, int 开始指针, char[] 字符串数组) {
        int 被截取后的数字, 截取的数字;
        int 字符串数组指针 = 开始指针;
        char 正负数标识 = 0;

      //提取符号位，并将i转换成正数
        if (int数据 < 0) {
            正负数标识 = '-';
            int数据 = -int数据;
        }

        // Generate two digits per iteration
        while (int数据 >= 65536) {//如果是16位到32位的int值			1234567
            被截取后的数字 = int数据 / 100;								//q=  12345
        // really: r = i - (q * 100);
            截取的数字 = int数据 - ((被截取后的数字 << 6) + (被截取后的数字 << 5) + (被截取后的数字 << 2));	//r=1234567-(12345*100) = 1234567-1234500=67;
            int数据 = 被截取后的数字;										//int数据=12345
            			
            字符串数组 [--字符串数组指针] = DigitOnes[截取的数字];		//[][][][][][][7]
            字符串数组 [--字符串数组指针] = DigitTens[截取的数字];		//[][][][][][6][7]
        }

        // Fall thru to fast mode for smaller numbers
//         assert(int数据 <= 65536, int数据);
        //int数据=12345 
        for (;;) {
        	//12345-12340=5
        	//12345/10*10
        	//
            被截取后的数字 = (int数据 * 52429) >>> (16+3);	//12345/10
            //  0010 0110 1001 0100 0000 1001 1010 0101
            //	0000 0000 0000 0000 0000 0100 1101 0010 //100 0000 1001 1010 0101
            //	被截取后的数字 = 1234
            //  截取的数字 = 12345 - ((1234<<3)+(1234<<1))=12345-(9872+2468)=12345-12340=5
            截取的数字 = int数据 - ((被截取后的数字 << 3) + (被截取后的数字 << 1));  // r = i-(q*10) ...
            字符串数组 [--字符串数组指针] = digits [截取的数字];//[][][][][5][6][7]
            int数据 = 被截取后的数字;//int数据 = 1234
            if (int数据 == 0) break;
        }
        if (正负数标识 != 0) {
            字符串数组 [--字符串数组指针] = 正负数标识;
        }
    }

    final static int [] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999,
                                      99999999, 999999999, Integer.MAX_VALUE };

    // Requires positive x
    static int stringSize(int x) {
        for (int i=0; ; i++)
            if (x <= sizeTable[i])
                return i+1;
    }

    /**
     * Parses the string argument as a signed integer in the radix
     * specified by the second argument. The characters in the string
     * must all be digits of the specified radix (as determined by
     * whether {@link java.lang.Character#digit(char, int)} returns a
     * nonnegative value), except that the first character may be an
     * ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to
     * indicate a negative value or an ASCII plus sign {@code '+'}
     * ({@code '\u005Cu002B'}) to indicate a positive value. The
     * resulting integer value is returned.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     * <li>The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li>The radix is either smaller than
     * {@link java.lang.Character#MIN_RADIX} or
     * larger than {@link java.lang.Character#MAX_RADIX}.
     *
     * <li>Any character of the string is not a digit of the specified
     * radix, except that the first character may be a minus sign
     * {@code '-'} ({@code '\u005Cu002D'}) or plus sign
     * {@code '+'} ({@code '\u005Cu002B'}) provided that the
     * string is longer than length 1.
     *
     * <li>The value represented by the string is not a value of type
     * {@code int}.
     * </ul>
     *
     * <p>Examples:
     * <blockquote><pre>
     * parseInt("0", 10) returns 0
     * parseInt("473", 10) returns 473
     * parseInt("+42", 10) returns 42
     * parseInt("-0", 10) returns 0
     * parseInt("-FF", 16) returns -255
     * parseInt("1100110", 2) returns 102
     * parseInt("2147483647", 10) returns 2147483647
     * parseInt("-2147483648", 10) returns -2147483648
     * parseInt("2147483648", 10) throws a NumberFormatException
     * parseInt("99", 8) throws a NumberFormatException
     * parseInt("Kona", 10) throws a NumberFormatException
     * parseInt("Kona", 27) returns 411787
     * </pre></blockquote>
     *
     * <p>
     * 将string参数解析为由第二个参数指定的基数中的有符号整数。
     * 字符串中的字符必须都是指定基数的数字(由{@link java.lang.Character#digit(char,int)}返回一个非负值决定),除了第一个字符可能是一个ASCII减标记{@code' - '}
     * ({@code'\ u005Cu002D'})以指示负值或ASCII加号{@code'+'}({@code'\ u005Cu002B'}) 。
     * 将string参数解析为由第二个参数指定的基数中的有符号整数。返回生成的整数值。
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
     *  <li>字符串表示的值不是类型{@code int}的值。
     * </ul>
     * 
     * <p>示例：<blockquote> <pre> parseInt("0",10)返回0 parseInt("473",10)返回473 parseInt("+ 42",10) )返回0 parseIn
     * t(" -  2147483648",10)返回-2147483648 parseInt(" -  2147483648",10)返回0 parseInt(" 2147483648",10)throws
     * 一个NumberFormatException parseInt("99",8)throws一个NumberFormatException parseInt("Kona",10)throws一个Numb
     * erFormatException parseInt("Kona",27)返回411787 </pre> </blockquote>。
     * 
     * 
     * @param      s   the {@code String} containing the integer
     *                  representation to be parsed
     * @param      radix   the radix to be used while parsing {@code s}.
     * @return     the integer represented by the string argument in the
     *             specified radix.
     * @exception  NumberFormatException if the {@code String}
     *             does not contain a parsable {@code int}.
     */
    public static int parseInt(String s, int radix)
                throws NumberFormatException
    {
        /*
         * WARNING: This method may be invoked early during VM initialization
         * before IntegerCache is initialized. Care must be taken to not use
         * the valueOf method.
         * <p>
         *  警告：在初始化IntegerCache之前,可能在VM初始化期间提前调用此方法。必须注意不要使用valueOf方法。
         * 
         */

        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                                            " less than Character.MIN_RADIX");
        }

        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                                            " greater than Character.MAX_RADIX");
        }

        int result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+')
                    throw NumberFormatException.forInputString(s);

                if (len == 1) // Cannot have lone "+" or "-"
                    throw NumberFormatException.forInputString(s);
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++),radix);
                if (digit < 0) {
                    throw NumberFormatException.forInputString(s);
                }
                if (result < multmin) {
                    throw NumberFormatException.forInputString(s);
                }
                result *= radix;
                if (result < limit + digit) {
                    throw NumberFormatException.forInputString(s);
                }
                result -= digit;
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
        return negative ? result : -result;
    }

    /**
     * Parses the string argument as a signed decimal integer. The
     * characters in the string must all be decimal digits, except
     * that the first character may be an ASCII minus sign {@code '-'}
     * ({@code '\u005Cu002D'}) to indicate a negative value or an
     * ASCII plus sign {@code '+'} ({@code '\u005Cu002B'}) to
     * indicate a positive value. The resulting integer value is
     * returned, exactly as if the argument and the radix 10 were
     * given as arguments to the {@link #parseInt(java.lang.String,
     * int)} method.
     *
     * <p>
     *  将字符串参数解析为带符号的十进制整数。
     * 字符串中的字符必须都是十进制数字,除了第一个字符可以是一个ASCII减号{@code' - '}({@code'\ u005Cu002D'}),表示负值或ASCII加号{ @code'+'}({@code'\ u005Cu002B'}
     * )以指示正值。
     *  将字符串参数解析为带符号的十进制整数。返回结果的整数值,就像参数和基数10作为{@link #parseInt(java.lang.String,int)}方法的参数一样。
     * 
     * 
     * @param s    a {@code String} containing the {@code int}
     *             representation to be parsed
     * @return     the integer value represented by the argument in decimal.
     * @exception  NumberFormatException  if the string does not contain a
     *               parsable integer.
     */
    public static int parseInt(String s) throws NumberFormatException {
        return parseInt(s,10);
    }

    /**
     * Parses the string argument as an unsigned integer in the radix
     * specified by the second argument.  An unsigned integer maps the
     * values usually associated with negative numbers to positive
     * numbers larger than {@code MAX_VALUE}.
     *
     * The characters in the string must all be digits of the
     * specified radix (as determined by whether {@link
     * java.lang.Character#digit(char, int)} returns a nonnegative
     * value), except that the first character may be an ASCII plus
     * sign {@code '+'} ({@code '\u005Cu002B'}). The resulting
     * integer value is returned.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     * <li>The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li>The radix is either smaller than
     * {@link java.lang.Character#MIN_RADIX} or
     * larger than {@link java.lang.Character#MAX_RADIX}.
     *
     * <li>Any character of the string is not a digit of the specified
     * radix, except that the first character may be a plus sign
     * {@code '+'} ({@code '\u005Cu002B'}) provided that the
     * string is longer than length 1.
     *
     * <li>The value represented by the string is larger than the
     * largest unsigned {@code int}, 2<sup>32</sup>-1.
     *
     * </ul>
     *
     *
     * <p>
     *  将字符串参数解析为由第二个参数指定的基数中的无符号整数。无符号整数将通常与负数关联的值映射为大于{@code MAX_VALUE}的正数。
     * 
     * 字符串中的字符必须都是指定基数的数字(由{@link java.lang.Character#digit(char,int)}返回一个非负值),除了第一个字符可以是ASCII加符号{@code'+'}(
     * {@code'\ u005Cu002B'})。
     * 返回生成的整数值。
     * 
     *  <p>如果发生以下任何情况,将抛出{@code NumberFormatException}类型的异常：
     * <ul>
     *  <li>第一个参数是{@code null}或是长度为零的字符串。
     * 
     *  <li>基数小于{@link java.lang.Character#MIN_RADIX}或大于{@link java.lang.Character#MAX_RADIX}。
     * 
     *  <li>字符串的任何字符都不是指定基数的数字,除非第一个字符可以是加号{@code'+'}({@code'\ u005Cu002B'}),前提是字符串较长比长度1。
     * 
     *  <li>字符串表示的值大于最大的无符号{@code int},2 <sup> 32 </sup> -1。
     * 
     * </ul>
     * 
     * 
     * @param      s   the {@code String} containing the unsigned integer
     *                  representation to be parsed
     * @param      radix   the radix to be used while parsing {@code s}.
     * @return     the integer represented by the string argument in the
     *             specified radix.
     * @throws     NumberFormatException if the {@code String}
     *             does not contain a parsable {@code int}.
     * @since 1.8
     */
    public static int parseUnsignedInt(String s, int radix)
                throws NumberFormatException {
        if (s == null)  {
            throw new NumberFormatException("null");
        }

        int len = s.length();
        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar == '-') {
                throw new
                    NumberFormatException(String.format("Illegal leading minus sign " +
                                                       "on unsigned string %s.", s));
            } else {
                if (len <= 5 || // Integer.MAX_VALUE in Character.MAX_RADIX is 6 digits
                    (radix == 10 && len <= 9) ) { // Integer.MAX_VALUE in base 10 is 10 digits
                    return parseInt(s, radix);
                } else {
                    long ell = Long.parseLong(s, radix);
                    if ((ell & 0xffff_ffff_0000_0000L) == 0) {
                        return (int) ell;
                    } else {
                        throw new
                            NumberFormatException(String.format("String value %s exceeds " +
                                                                "range of unsigned int.", s));
                    }
                }
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
    }

    /**
     * Parses the string argument as an unsigned decimal integer. The
     * characters in the string must all be decimal digits, except
     * that the first character may be an an ASCII plus sign {@code
     * '+'} ({@code '\u005Cu002B'}). The resulting integer value
     * is returned, exactly as if the argument and the radix 10 were
     * given as arguments to the {@link
     * #parseUnsignedInt(java.lang.String, int)} method.
     *
     * <p>
     *  将字符串参数解析为无符号十进制整数。字符串中的字符必须都是十进制数字,除了第一个字符可以是ASCII加号{@code'+'}({@code'\ u005Cu002B'})。
     * 返回结果的整数值,就像参数和基数10作为{@link #parseUnsignedInt(java.lang.String,int)}方法的参数一样。
     * 
     * 
     * @param s   a {@code String} containing the unsigned {@code int}
     *            representation to be parsed
     * @return    the unsigned integer value represented by the argument in decimal.
     * @throws    NumberFormatException  if the string does not contain a
     *            parsable unsigned integer.
     * @since 1.8
     */
    public static int parseUnsignedInt(String s) throws NumberFormatException {
        return parseUnsignedInt(s, 10);
    }

    /**
     * Returns an {@code Integer} object holding the value
     * extracted from the specified {@code String} when parsed
     * with the radix given by the second argument. The first argument
     * is interpreted as representing a signed integer in the radix
     * specified by the second argument, exactly as if the arguments
     * were given to the {@link #parseInt(java.lang.String, int)}
     * method. The result is an {@code Integer} object that
     * represents the integer value specified by the string.
     *
     * <p>In other words, this method returns an {@code Integer}
     * object equal to the value of:
     *
     * <blockquote>
     *  {@code new Integer(Integer.parseInt(s, radix))}
     * </blockquote>
     *
     * <p>
     * 返回一个{@code Integer}对象,当使用第二个参数给出的基数解析时,该对象保存从指定的{@code String}中提取的值。
     * 第一个参数被解释为表示由第二个参数指定的基数中的有符号整数,就像参数被赋予{@link #parseInt(java.lang.String,int)}方法一样。
     * 结果是一个{@code Integer}对象,它表示由字符串指定的整数值。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Integer}对象：
     * 
     * <blockquote>
     *  {@code new Integer(Integer.parseInt(s,radix))}
     * </blockquote>
     * 
     * 
     * @param      s   the string to be parsed.
     * @param      radix the radix to be used in interpreting {@code s}
     * @return     an {@code Integer} object holding the value
     *             represented by the string argument in the specified
     *             radix.
     * @exception NumberFormatException if the {@code String}
     *            does not contain a parsable {@code int}.
     */
    public static Integer valueOf(String s, int radix) throws NumberFormatException {
        return Integer.valueOf(parseInt(s,radix));
    }

    /**
     * Returns an {@code Integer} object holding the
     * value of the specified {@code String}. The argument is
     * interpreted as representing a signed decimal integer, exactly
     * as if the argument were given to the {@link
     * #parseInt(java.lang.String)} method. The result is an
     * {@code Integer} object that represents the integer value
     * specified by the string.
     *
     * <p>In other words, this method returns an {@code Integer}
     * object equal to the value of:
     *
     * <blockquote>
     *  {@code new Integer(Integer.parseInt(s))}
     * </blockquote>
     *
     * <p>
     *  返回一个包含指定{@code String}的值的{@code Integer}对象。
     * 该参数被解释为表示一个有符号的十进制整数,就像参数被赋予{@link #parseInt(java.lang.String)}方法一样。
     * 结果是一个{@code Integer}对象,它表示由字符串指定的整数值。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Integer}对象：
     * 
     * <blockquote>
     *  {@code new Integer(Integer.parseInt(s))}
     * </blockquote>
     * 
     * 
     * @param      s   the string to be parsed.
     * @return     an {@code Integer} object holding the value
     *             represented by the string argument.
     * @exception  NumberFormatException  if the string cannot be parsed
     *             as an integer.
     */
    public static Integer valueOf(String s) throws NumberFormatException {
        return Integer.valueOf(parseInt(s, 10));
    }

    /**
     * Cache to support the object identity semantics of autoboxing for values between
     * -128 and 127 (inclusive) as required by JLS.
     *
     * The cache is initialized on first usage.  The size of the cache
     * may be controlled by the {@code -XX:AutoBoxCacheMax=<size>} option.
     * During VM initialization, java.lang.Integer.IntegerCache.high property
     * may be set and saved in the private system properties in the
     * sun.misc.VM class.
     * <p>
     *  缓存支持自动装箱的对象标识语义,值为-128到127(含)之间的值,如JLS所要求的。
     * 
     *  缓存在第一次使用时初始化。缓存的大小可以由{@code -XX：AutoBoxCacheMax = <size>}选项控制。
     * 在VM初始化期间,可以设置java.lang.Integer.IntegerCache.high属性并将其保存在sun.misc.VM类中的私有系统属性中。
     * 
     */

    private static class IntegerCache {
        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            // high value may be configured by property
            int h = 127;
            String integerCacheHighPropValue =
                sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    int i = parseInt(integerCacheHighPropValue);
                    i = Math.max(i, 127);
                    // Maximum array size is Integer.MAX_VALUE
                    h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
                } catch( NumberFormatException nfe) {
                    // If the property cannot be parsed into an int, ignore it.
                }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for(int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);

            // range [-128, 127] must be interned (JLS7 5.1.7)
            assert IntegerCache.high >= 127;
        }

        private IntegerCache() {}
    }

    /**
     * Returns an {@code Integer} instance representing the specified
     * {@code int} value.  If a new {@code Integer} instance is not
     * required, this method should generally be used in preference to
     * the constructor {@link #Integer(int)}, as this method is likely
     * to yield significantly better space and time performance by
     * caching frequently requested values.
     *
     * This method will always cache values in the range -128 to 127,
     * inclusive, and may cache other values outside of this range.
     *
     * <p>
     * 返回表示指定的{@code int}值的{@code Integer}实例。
     * 如果不需要新的{@code Integer}实例,通常应该优先使用构造函数{@link #Integer(int)},因为这种方法可能通过高速缓存获得明显更好的空间和时间性能请求值。
     * 
     *  此方法将始终缓存范围在-128到127之间的值(包括端值),并可缓存此范围之外的其他值。
     * 
     * 
     * @param  i an {@code int} value.
     * @return an {@code Integer} instance representing {@code i}.
     * @since  1.5
     */
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }

    /**
     * The value of the {@code Integer}.
     *
     * <p>
     *  {@code整数}的值。
     * 
     * 
     * @serial
     */
    private final int value;

    /**
     * Constructs a newly allocated {@code Integer} object that
     * represents the specified {@code int} value.
     *
     * <p>
     *  构造一个新分配的{@code Integer}对象,该对象表示指定的{@code int}值。
     * 
     * 
     * @param   value   the value to be represented by the
     *                  {@code Integer} object.
     */
    public Integer(int value) {
        this.value = value;
    }

    /**
     * Constructs a newly allocated {@code Integer} object that
     * represents the {@code int} value indicated by the
     * {@code String} parameter. The string is converted to an
     * {@code int} value in exactly the manner used by the
     * {@code parseInt} method for radix 10.
     *
     * <p>
     *  构造一个新分配的{@code Integer}对象,该对象表示{@code String}参数指示的{@code int}值。
     * 该字符串被转换为{@code int}值,其方式与{#code parseInt}方法对基数10的使用方式完全相同。
     * 
     * 
     * @param      s   the {@code String} to be converted to an
     *                 {@code Integer}.
     * @exception  NumberFormatException  if the {@code String} does not
     *               contain a parsable integer.
     * @see        java.lang.Integer#parseInt(java.lang.String, int)
     */
    public Integer(String s) throws NumberFormatException {
        this.value = parseInt(s, 10);
    }

    /**
     * Returns the value of this {@code Integer} as a {@code byte}
     * after a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     * <p>
     *  在缩小的基元转换后,将此{@code Integer}的值作为{@code byte}返回。 @jls 5.1.3缩小基本转换
     * 
     */
    public byte byteValue() {
        return (byte)value;
    }

    /**
     * Returns the value of this {@code Integer} as a {@code short}
     * after a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     * <p>
     *  在缩小的基元转换后,将此{@code Integer}的值返回为{@code short}。 @jls 5.1.3缩小基本转换
     * 
     */
    public short shortValue() {
        return (short)value;
    }

    /**
     * Returns the value of this {@code Integer} as an
     * {@code int}.
     * <p>
     *  以{@code int}形式返回此{@code Integer}的值。
     * 
     */
    public int intValue() {
        return value;
    }

    /**
     * Returns the value of this {@code Integer} as a {@code long}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后,将此{@code Integer}的值返回为{@code long}。 @jls 5.1.2扩大原始转换
     * 
     * 
     * @see Integer#toUnsignedLong(int)
     */
    public long longValue() {
        return (long)value;
    }

    /**
     * Returns the value of this {@code Integer} as a {@code float}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     * 在扩展基元转换后返回此{@code Integer}的值作为{@code float}。 @jls 5.1.2扩大原始转换
     * 
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * Returns the value of this {@code Integer} as a {@code double}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后,将此{@code Integer}的值返回为{@code double}。 @jls 5.1.2扩大原始转换
     * 
     */
    public double doubleValue() {
        return (double)value;
    }

    /**
     * Returns a {@code String} object representing this
     * {@code Integer}'s value. The value is converted to signed
     * decimal representation and returned as a string, exactly as if
     * the integer value were given as an argument to the {@link
     * java.lang.Integer#toString(int)} method.
     *
     * <p>
     *  返回表示此{@code Integer}的值的{@code String}对象。
     * 该值将转换为带符号的十进制表示形式,并作为字符串返回,正如将整数值作为{@link java.lang.Integer#toString(int)}方法的参数给出。
     * 
     * 
     * @return  a string representation of the value of this object in
     *          base&nbsp;10.
     */
    public String toString() {
        return toString(value);
    }

    /**
     * Returns a hash code for this {@code Integer}.
     *
     * <p>
     *  返回此{@code Integer}的哈希码。
     * 
     * 
     * @return  a hash code value for this object, equal to the
     *          primitive {@code int} value represented by this
     *          {@code Integer} object.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code int} value; compatible with
     * {@code Integer.hashCode()}.
     *
     * <p>
     *  返回{@code int}值的哈希码;兼容{@code Integer.hashCode()}。
     * 
     * 
     * @param value the value to hash
     * @since 1.8
     *
     * @return a hash code value for a {@code int} value.
     */
    public static int hashCode(int value) {
        return value;
    }

    /**
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is an {@code Integer} object that
     * contains the same {@code int} value as this object.
     *
     * <p>
     *  将此对象与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},并且是包含与此对象相同的{@code int}值的{@code Integer}对象。
     * 
     * 
     * @param   obj   the object to compare with.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            return value == ((Integer)obj).intValue();
        }
        return false;
    }

    /**
     * Determines the integer value of the system property with the
     * specified name.
     *
     * <p>The first argument is treated as the name of a system
     * property.  System properties are accessible through the {@link
     * java.lang.System#getProperty(java.lang.String)} method. The
     * string value of this property is then interpreted as an integer
     * value using the grammar supported by {@link Integer#decode decode} and
     * an {@code Integer} object representing this value is returned.
     *
     * <p>If there is no property with the specified name, if the
     * specified name is empty or {@code null}, or if the property
     * does not have the correct numeric format, then {@code null} is
     * returned.
     *
     * <p>In other words, this method returns an {@code Integer}
     * object equal to the value of:
     *
     * <blockquote>
     *  {@code getInteger(nm, null)}
     * </blockquote>
     *
     * <p>
     *  确定具有指定名称的系统属性的整数值。
     * 
     *  <p>第一个参数被视为系统属性的名称。系统属性可通过{@link java.lang.System#getProperty(java.lang.String)}方法访问。
     * 然后,此属性的字符串值将使用{@link Integer#decode decode}支持的语法解释为整数值,并返回表示此值的{@code Integer}对象。
     * 
     * <p>如果没有指定名称的属性,如果指定的名称为空或{@code null},或者该属性没有正确的数字格式,则返回{@code null}。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Integer}对象：
     * 
     * <blockquote>
     *  {@code getInteger(nm,null)}
     * </blockquote>
     * 
     * 
     * @param   nm   property name.
     * @return  the {@code Integer} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     java.lang.System#getProperty(java.lang.String)
     * @see     java.lang.System#getProperty(java.lang.String, java.lang.String)
     */
    public static Integer getInteger(String nm) {
        return getInteger(nm, null);
    }

    /**
     * Determines the integer value of the system property with the
     * specified name.
     *
     * <p>The first argument is treated as the name of a system
     * property.  System properties are accessible through the {@link
     * java.lang.System#getProperty(java.lang.String)} method. The
     * string value of this property is then interpreted as an integer
     * value using the grammar supported by {@link Integer#decode decode} and
     * an {@code Integer} object representing this value is returned.
     *
     * <p>The second argument is the default value. An {@code Integer} object
     * that represents the value of the second argument is returned if there
     * is no property of the specified name, if the property does not have
     * the correct numeric format, or if the specified name is empty or
     * {@code null}.
     *
     * <p>In other words, this method returns an {@code Integer} object
     * equal to the value of:
     *
     * <blockquote>
     *  {@code getInteger(nm, new Integer(val))}
     * </blockquote>
     *
     * but in practice it may be implemented in a manner such as:
     *
     * <blockquote><pre>
     * Integer result = getInteger(nm, null);
     * return (result == null) ? new Integer(val) : result;
     * </pre></blockquote>
     *
     * to avoid the unnecessary allocation of an {@code Integer}
     * object when the default value is not needed.
     *
     * <p>
     *  确定具有指定名称的系统属性的整数值。
     * 
     *  <p>第一个参数被视为系统属性的名称。系统属性可通过{@link java.lang.System#getProperty(java.lang.String)}方法访问。
     * 然后,此属性的字符串值将使用{@link Integer#decode decode}支持的语法解释为整数值,并返回表示此值的{@code Integer}对象。
     * 
     *  <p>第二个参数是默认值。如果没有指定名称的属性,则返回表示第二个参数的值的{@code Integer}对象,如果该属性没有正确的数字格式,或者指定的名称为空或{@code null }。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Integer}对象：
     * 
     * <blockquote>
     *  {@code getInteger(nm,new Integer(val))}
     * </blockquote>
     * 
     *  但是在实践中它可以以如下方式实现：
     * 
     *  <blockquote> <pre> Integer result = getInteger(nm,null); return(result == null)? new Integer(val)：re
     * sult; </pre> </blockquote>。
     * 
     *  以避免在不需要默认值时不必要地分配{@code Integer}对象。
     * 
     * 
     * @param   nm   property name.
     * @param   val   default value.
     * @return  the {@code Integer} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     java.lang.System#getProperty(java.lang.String)
     * @see     java.lang.System#getProperty(java.lang.String, java.lang.String)
     */
    public static Integer getInteger(String nm, int val) {
        Integer result = getInteger(nm, null);
        return (result == null) ? Integer.valueOf(val) : result;
    }

    /**
     * Returns the integer value of the system property with the
     * specified name.  The first argument is treated as the name of a
     * system property.  System properties are accessible through the
     * {@link java.lang.System#getProperty(java.lang.String)} method.
     * The string value of this property is then interpreted as an
     * integer value, as per the {@link Integer#decode decode} method,
     * and an {@code Integer} object representing this value is
     * returned; in summary:
     *
     * <ul><li>If the property value begins with the two ASCII characters
     *         {@code 0x} or the ASCII character {@code #}, not
     *      followed by a minus sign, then the rest of it is parsed as a
     *      hexadecimal integer exactly as by the method
     *      {@link #valueOf(java.lang.String, int)} with radix 16.
     * <li>If the property value begins with the ASCII character
     *     {@code 0} followed by another character, it is parsed as an
     *     octal integer exactly as by the method
     *     {@link #valueOf(java.lang.String, int)} with radix 8.
     * <li>Otherwise, the property value is parsed as a decimal integer
     * exactly as by the method {@link #valueOf(java.lang.String, int)}
     * with radix 10.
     * </ul>
     *
     * <p>The second argument is the default value. The default value is
     * returned if there is no property of the specified name, if the
     * property does not have the correct numeric format, or if the
     * specified name is empty or {@code null}.
     *
     * <p>
     * 返回具有指定名称的系统属性的整数值。第一个参数被视为系统属性的名称。系统属性可通过{@link java.lang.System#getProperty(java.lang.String)}方法访问。
     * 此属性的字符串值将根据{@link Integer#decode decode}方法解释为整数值,并返回表示此值的{@code Integer}对象;综上所述：。
     * 
     *  <ul> <li>如果属性值以两个ASCII字符{@code 0x}或ASCII字符{@code#}开头,后面没有减号,那么其余部分将被精确解析为十六进制整数如通过方法{@link #valueOf(java.lang.String,int)}
     * 与基数16. <li>如果属性值以ASCII字符{@code 0}开头,后跟另一个字符,它将被解析为八进制整数,正如方法{@link #valueOf(java.lang.String,int)}与ra
     * dix 8. <li>否则,属性值被解析为一个十进制整数正如方法{@link #valueOf (java.lang.String,int)}。
     * </ul>
     * 
     *  <p>第二个参数是默认值。如果没有指定名称的属性,如果属性没有正确的数字格式,或者如果指定的名称为空或{@code null},则返回默认值。
     * 
     * 
     * @param   nm   property name.
     * @param   val   default value.
     * @return  the {@code Integer} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     System#getProperty(java.lang.String)
     * @see     System#getProperty(java.lang.String, java.lang.String)
     */
    public static Integer getInteger(String nm, Integer val) {
        String v = null;
        try {
            v = System.getProperty(nm);
        } catch (IllegalArgumentException | NullPointerException e) {
        }
        if (v != null) {
            try {
                return Integer.decode(v);
            } catch (NumberFormatException e) {
            }
        }
        return val;
    }

    /**
     * Decodes a {@code String} into an {@code Integer}.
     * Accepts decimal, hexadecimal, and octal numbers given
     * by the following grammar:
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
     * Integer.parseInt} method with the indicated radix (10, 16, or
     * 8).  This sequence of characters must represent a positive
     * value or a {@link NumberFormatException} will be thrown.  The
     * result is negated if first character of the specified {@code
     * String} is the minus sign.  No whitespace characters are
     * permitted in the {@code String}.
     *
     * <p>
     *  将{@code String}解码为{@code Integer}。接受由以下语法提供的十进制,十六进制和八进制数：
     * 
     * <blockquote>
     * <dl>
     * <dt> <i>可解码字符串：</i> <dd> <i>签署<sub> opt </sub> DecimalNumeral </i> <dd> <i> > {@code 0x} <i> HexDigit
     * s </i> <dd> <i> Sign <sub> opt </sub> </i> {@code 0X} <i> HexDigits </i> <dd> < i>签署<sub> opt </sub> 
     * </i> {@code#} <i> HexDigits </i> <dd> <i> 0} <i> OctalDigits </i>。
     * 
     *  <dt> <i>签名：</i> <dd> {@ code  - } <dd> {@ code +}
     * </dl>
     * </blockquote>
     * 
     *  <i> DecimalNumeral </i>,<HexDigits </i>和<OctalDigits </i>的定义在<cite> Java&trade;语言规范</cite>,除了在数字之间不接
     * 受下划线。
     * 
     *  <p>可选符号和/或基数说明符("{@code 0x}","{@code 0X}","{@code#}"或前导零)后面的字符序列由{@code Integer.parseInt}方法与指定的基数(10
     * ,16或8)。
     * 这个字符序列必须表示正值,否则将抛出{@link NumberFormatException}。如果指定的{@code String}的第一个字符是减号,则结果将被否定。
     *  {@code String}中不允许使用空格字符。
     * 
     * 
     * @param     nm the {@code String} to decode.
     * @return    an {@code Integer} object holding the {@code int}
     *             value represented by {@code nm}
     * @exception NumberFormatException  if the {@code String} does not
     *            contain a parsable integer.
     * @see java.lang.Integer#parseInt(java.lang.String, int)
     */
    public static Integer decode(String nm) throws NumberFormatException {
        int radix = 10;
        int index = 0;
        boolean negative = false;
        Integer result;

        if (nm.length() == 0)
            throw new NumberFormatException("Zero length string");
        char firstChar = nm.charAt(0);
        // Handle sign, if present
        if (firstChar == '-') {
            negative = true;
            index++;
        } else if (firstChar == '+')
            index++;

        // Handle radix specifier, if present
        if (nm.startsWith("0x", index) || nm.startsWith("0X", index)) {
            index += 2;
            radix = 16;
        }
        else if (nm.startsWith("#", index)) {
            index ++;
            radix = 16;
        }
        else if (nm.startsWith("0", index) && nm.length() > 1 + index) {
            index ++;
            radix = 8;
        }

        if (nm.startsWith("-", index) || nm.startsWith("+", index))
            throw new NumberFormatException("Sign character in wrong position");

        try {
            result = Integer.valueOf(nm.substring(index), radix);
            result = negative ? Integer.valueOf(-result.intValue()) : result;
        } catch (NumberFormatException e) {
            // If number is Integer.MIN_VALUE, we'll end up here. The next line
            // handles this case, and causes any genuine format error to be
            // rethrown.
            String constant = negative ? ("-" + nm.substring(index))
                                       : nm.substring(index);
            result = Integer.valueOf(constant, radix);
        }
        return result;
    }

    /**
     * Compares two {@code Integer} objects numerically.
     *
     * <p>
     *  以数字方式比较两个{@code Integer}对象。
     * 
     * 
     * @param   anotherInteger   the {@code Integer} to be compared.
     * @return  the value {@code 0} if this {@code Integer} is
     *          equal to the argument {@code Integer}; a value less than
     *          {@code 0} if this {@code Integer} is numerically less
     *          than the argument {@code Integer}; and a value greater
     *          than {@code 0} if this {@code Integer} is numerically
     *           greater than the argument {@code Integer} (signed
     *           comparison).
     * @since   1.2
     */
    public int compareTo(Integer anotherInteger) {
        return compare(this.value, anotherInteger.value);
    }

    /**
     * Compares two {@code int} values numerically.
     * The value returned is identical to what would be returned by:
     * <pre>
     *    Integer.valueOf(x).compareTo(Integer.valueOf(y))
     * </pre>
     *
     * <p>
     *  以数字方式比较两个{@code int}值。返回的值与由以下内容返回的值相同：
     * <pre>
     *  Integer.valueOf(x).compareTo(Integer.valueOf(y))
     * </pre>
     * 
     * 
     * @param  x the first {@code int} to compare
     * @param  y the second {@code int} to compare
     * @return the value {@code 0} if {@code x == y};
     *         a value less than {@code 0} if {@code x < y}; and
     *         a value greater than {@code 0} if {@code x > y}
     * @since 1.7
     */
    public static int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    /**
     * Compares two {@code int} values numerically treating the values
     * as unsigned.
     *
     * <p>
     *  比较两个{@code int}值,将值以数字方式处理为unsigned。
     * 
     * 
     * @param  x the first {@code int} to compare
     * @param  y the second {@code int} to compare
     * @return the value {@code 0} if {@code x == y}; a value less
     *         than {@code 0} if {@code x < y} as unsigned values; and
     *         a value greater than {@code 0} if {@code x > y} as
     *         unsigned values
     * @since 1.8
     */
    public static int compareUnsigned(int x, int y) {
        return compare(x + MIN_VALUE, y + MIN_VALUE);
    }

    /**
     * Converts the argument to a {@code long} by an unsigned
     * conversion.  In an unsigned conversion to a {@code long}, the
     * high-order 32 bits of the {@code long} are zero and the
     * low-order 32 bits are equal to the bits of the integer
     * argument.
     *
     * Consequently, zero and positive {@code int} values are mapped
     * to a numerically equal {@code long} value and negative {@code
     * int} values are mapped to a {@code long} value equal to the
     * input plus 2<sup>32</sup>.
     *
     * <p>
     * 通过无符号转换将参数转换为{@code long}。在对{@code long}的无符号转换中,{@code long}的高位32位为零,低位32位等于整数参数的位。
     * 
     *  因此,零和正的{@code int}值被映射到数值上等于{@code long}的值,而负的{@code int}值被映射到等于输入加上2的{@code long} 32 </sup>。
     * 
     * 
     * @param  x the value to convert to an unsigned {@code long}
     * @return the argument converted to {@code long} by an unsigned
     *         conversion
     * @since 1.8
     */
    public static long toUnsignedLong(int x) {
        return ((long) x) & 0xffffffffL;
    }

    /**
     * Returns the unsigned quotient of dividing the first argument by
     * the second where each argument and the result is interpreted as
     * an unsigned value.
     *
     * <p>Note that in two's complement arithmetic, the three other
     * basic arithmetic operations of add, subtract, and multiply are
     * bit-wise identical if the two operands are regarded as both
     * being signed or both being unsigned.  Therefore separate {@code
     * addUnsigned}, etc. methods are not provided.
     *
     * <p>
     *  返回将第一个参数除以第二个的无符号商,其中每个参数和结果被解释为无符号值。
     * 
     *  <p>请注意,在二的补码运算中,如果两个操作数都被视为有符号或两者都是无符号的,则加法,减法和乘法的三个其他基本算术运算是按位相同的。因此,不提供单独的{@code addUnsigned}等方法。
     * 
     * 
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned quotient of the first argument divided by
     * the second argument
     * @see #remainderUnsigned
     * @since 1.8
     */
    public static int divideUnsigned(int dividend, int divisor) {
        // In lieu of tricky code, for now just use long arithmetic.
        return (int)(toUnsignedLong(dividend) / toUnsignedLong(divisor));
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     *
     * <p>
     *  返回将第一个参数除以第二个的无符号余数,其中每个参数和结果解释为无符号值。
     * 
     * 
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument
     * @see #divideUnsigned
     * @since 1.8
     */
    public static int remainderUnsigned(int dividend, int divisor) {
        // In lieu of tricky code, for now just use long arithmetic.
        return (int)(toUnsignedLong(dividend) % toUnsignedLong(divisor));
    }


    // Bit twiddling

    /**
     * The number of bits used to represent an {@code int} value in two's
     * complement binary form.
     *
     * <p>
     *  用于以二进制补码二进制形式表示{@code int}值的位数。
     * 
     * 
     * @since 1.5
     */
    @Native public static final int SIZE = 32;

    /**
     * The number of bytes used to represent a {@code int} value in two's
     * complement binary form.
     *
     * <p>
     *  用于以二进制补码二进制形式表示{@code int}值的字节数。
     * 
     * 
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * Returns an {@code int} value with at most a single one-bit, in the
     * position of the highest-order ("leftmost") one-bit in the specified
     * {@code int} value.  Returns zero if the specified value has no
     * one-bits in its two's complement binary representation, that is, if it
     * is equal to zero.
     *
     * <p>
     * 在指定的{@code int}值中的最高位("最左边")一位的位置中返回最多有一个一位的{@code int}值。如果指定的值在其二进制补码二进制表示中没有一个比特,即如果它等于零,则返回零。
     * 
     * 
     * @param i the value whose highest one bit is to be computed
     * @return an {@code int} value with a single one-bit, in the position
     *     of the highest-order one-bit in the specified value, or zero if
     *     the specified value is itself equal to zero.
     * @since 1.5
     */
    public static int highestOneBit(int i) {
        // HD, Figure 3-1
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }

    /**
     * Returns an {@code int} value with at most a single one-bit, in the
     * position of the lowest-order ("rightmost") one-bit in the specified
     * {@code int} value.  Returns zero if the specified value has no
     * one-bits in its two's complement binary representation, that is, if it
     * is equal to zero.
     *
     * <p>
     *  在指定的{@code int}值的最低位("最右侧")一位的位置返回一个最多为一个一位的{@code int}值。如果指定的值在其二进制补码二进制表示中没有一个比特,即如果它等于零,则返回零。
     * 
     * 
     * @param i the value whose lowest one bit is to be computed
     * @return an {@code int} value with a single one-bit, in the position
     *     of the lowest-order one-bit in the specified value, or zero if
     *     the specified value is itself equal to zero.
     * @since 1.5
     */
    public static int lowestOneBit(int i) {
        // HD, Section 2-1
        return i & -i;
    }

    /**
     * Returns the number of zero bits preceding the highest-order
     * ("leftmost") one-bit in the two's complement binary representation
     * of the specified {@code int} value.  Returns 32 if the
     * specified value has no one-bits in its two's complement representation,
     * in other words if it is equal to zero.
     *
     * <p>Note that this method is closely related to the logarithm base 2.
     * For all positive {@code int} values x:
     * <ul>
     * <li>floor(log<sub>2</sub>(x)) = {@code 31 - numberOfLeadingZeros(x)}
     * <li>ceil(log<sub>2</sub>(x)) = {@code 32 - numberOfLeadingZeros(x - 1)}
     * </ul>
     *
     * <p>
     *  返回指定{@code int}值的二进制补码二进制表示中最高位("最左边")一位之前的零位数。如果指定的值在其二进制补码表示中没有一个比特,换句话说,如果它等于零,则返回32。
     * 
     *  <p>请注意,此方法与对数基数密切相关2.对于所有正{@code int}值x：
     * <ul>
     *  <li> floor(log <sub> 2 </sub>(x))= {@code 31-numberOfLeadingZeros(x)} <li> ceil(log < code 32  -  numberOfLeadingZeros(x  -  1)}。
     * </ul>
     * 
     * 
     * @param i the value whose number of leading zeros is to be computed
     * @return the number of zero bits preceding the highest-order
     *     ("leftmost") one-bit in the two's complement binary representation
     *     of the specified {@code int} value, or 32 if the value
     *     is equal to zero.
     * @since 1.5
     */
    public static int numberOfLeadingZeros(int i) {
        // HD, Figure 5-6
        if (i == 0)
            return 32;
        int n = 1;
        if (i >>> 16 == 0) { n += 16; i <<= 16; }
        if (i >>> 24 == 0) { n +=  8; i <<=  8; }
        if (i >>> 28 == 0) { n +=  4; i <<=  4; }
        if (i >>> 30 == 0) { n +=  2; i <<=  2; }
        n -= i >>> 31;
        return n;
    }

    /**
     * Returns the number of zero bits following the lowest-order ("rightmost")
     * one-bit in the two's complement binary representation of the specified
     * {@code int} value.  Returns 32 if the specified value has no
     * one-bits in its two's complement representation, in other words if it is
     * equal to zero.
     *
     * <p>
     *  返回指定{@code int}值的二进制补码二进制表示中最低位("最右侧")一位后的零位数。如果指定的值在其二进制补码表示中没有一个比特,换句话说,如果它等于零,则返回32。
     * 
     * 
     * @param i the value whose number of trailing zeros is to be computed
     * @return the number of zero bits following the lowest-order ("rightmost")
     *     one-bit in the two's complement binary representation of the
     *     specified {@code int} value, or 32 if the value is equal
     *     to zero.
     * @since 1.5
     */
    public static int numberOfTrailingZeros(int i) {
        // HD, Figure 5-14
        int y;
        if (i == 0) return 32;
        int n = 31;
        y = i <<16; if (y != 0) { n = n -16; i = y; }
        y = i << 8; if (y != 0) { n = n - 8; i = y; }
        y = i << 4; if (y != 0) { n = n - 4; i = y; }
        y = i << 2; if (y != 0) { n = n - 2; i = y; }
        return n - ((i << 1) >>> 31);
    }

    /**
     * Returns the number of one-bits in the two's complement binary
     * representation of the specified {@code int} value.  This function is
     * sometimes referred to as the <i>population count</i>.
     *
     * <p>
     * 返回指定{@code int}值的二进制补码二进制表示中的一位数。此函数有时称为<i>种群计数</i>。
     * 
     * 
     * @param i the value whose bits are to be counted
     * @return the number of one-bits in the two's complement binary
     *     representation of the specified {@code int} value.
     * @since 1.5
     */
    public static int bitCount(int i) {
        // HD, Figure 5-2
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;
    }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code int} value left by the
     * specified number of bits.  (Bits shifted out of the left hand, or
     * high-order, side reenter on the right, or low-order.)
     *
     * <p>Note that left rotation with a negative distance is equivalent to
     * right rotation: {@code rotateLeft(val, -distance) == rotateRight(val,
     * distance)}.  Note also that rotation by any multiple of 32 is a
     * no-op, so all but the last five bits of the rotation distance can be
     * ignored, even if the distance is negative: {@code rotateLeft(val,
     * distance) == rotateLeft(val, distance & 0x1F)}.
     *
     * <p>
     *  返回通过将指定的{@code int}值的二进制补码二进制表示旋转指定位数所获得的值。 (从左手移出的位,或高阶,右边的重新输入或低阶)。
     * 
     *  <p>请注意,具有负距离的左旋转等效于右旋转：{@code rotateLeft(val,-distance)== rotateRight(val,distance)}。
     * 还要注意,32的任何倍数的旋转是无操作,因此除了旋转距离的最后五位之外的所有旋转距离都可以被忽略,即使距离是负的：{@code rotateLeft(val,distance)== rotateLeft val,distance&0x1F)}
     * 。
     *  <p>请注意,具有负距离的左旋转等效于右旋转：{@code rotateLeft(val,-distance)== rotateRight(val,distance)}。
     * 
     * 
     * @param i the value whose bits are to be rotated left
     * @param distance the number of bit positions to rotate left
     * @return the value obtained by rotating the two's complement binary
     *     representation of the specified {@code int} value left by the
     *     specified number of bits.
     * @since 1.5
     */
    public static int rotateLeft(int i, int distance) {
        return (i << distance) | (i >>> -distance);
    }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code int} value right by the
     * specified number of bits.  (Bits shifted out of the right hand, or
     * low-order, side reenter on the left, or high-order.)
     *
     * <p>Note that right rotation with a negative distance is equivalent to
     * left rotation: {@code rotateRight(val, -distance) == rotateLeft(val,
     * distance)}.  Note also that rotation by any multiple of 32 is a
     * no-op, so all but the last five bits of the rotation distance can be
     * ignored, even if the distance is negative: {@code rotateRight(val,
     * distance) == rotateRight(val, distance & 0x1F)}.
     *
     * <p>
     *  返回通过将指定的{@code int}值的二进制补码二进制表示右移指定位数所获得的值。 (从右手移出的位或低位,在左边重新输入或高位。
     * 
     *  <p>请注意,具有负距离的右旋转等效于左旋转：{@code rotateRight(val,-distance)== rotateLeft(val,distance)}。
     * 还要注意,32的任何倍数的旋转是无操作的,因此除了旋转距离的最后5位之外的所有旋转距离都可以被忽略,即使距离是负的：{@code rotateRight(val,distance)== rotateRight val,distance&0x1F)}
     * 。
     *  <p>请注意,具有负距离的右旋转等效于左旋转：{@code rotateRight(val,-distance)== rotateLeft(val,distance)}。
     * 
     * 
     * @param i the value whose bits are to be rotated right
     * @param distance the number of bit positions to rotate right
     * @return the value obtained by rotating the two's complement binary
     *     representation of the specified {@code int} value right by the
     *     specified number of bits.
     * @since 1.5
     */
    public static int rotateRight(int i, int distance) {
        return (i >>> distance) | (i << -distance);
    }

    /**
     * Returns the value obtained by reversing the order of the bits in the
     * two's complement binary representation of the specified {@code int}
     * value.
     *
     * <p>
     * 返回通过反转指定{@code int}值的二进制补码二进制表示中的位的顺序获得的值。
     * 
     * 
     * @param i the value to be reversed
     * @return the value obtained by reversing order of the bits in the
     *     specified {@code int} value.
     * @since 1.5
     */
    public static int reverse(int i) {
        // HD, Figure 7-1
        i = (i & 0x55555555) << 1 | (i >>> 1) & 0x55555555;
        i = (i & 0x33333333) << 2 | (i >>> 2) & 0x33333333;
        i = (i & 0x0f0f0f0f) << 4 | (i >>> 4) & 0x0f0f0f0f;
        i = (i << 24) | ((i & 0xff00) << 8) |
            ((i >>> 8) & 0xff00) | (i >>> 24);
        return i;
    }

    /**
     * Returns the signum function of the specified {@code int} value.  (The
     * return value is -1 if the specified value is negative; 0 if the
     * specified value is zero; and 1 if the specified value is positive.)
     *
     * <p>
     *  返回指定的{@code int}值的signum函数。 (如果指定值为负,则返回值为-1;如果指定值为零,则返回值为0;如果指定值为正,返回值为1)。
     * 
     * 
     * @param i the value whose signum is to be computed
     * @return the signum function of the specified {@code int} value.
     * @since 1.5
     */
    public static int signum(int i) {
        // HD, Section 2-7
        return (i >> 31) | (-i >>> 31);
    }

    /**
     * Returns the value obtained by reversing the order of the bytes in the
     * two's complement representation of the specified {@code int} value.
     *
     * <p>
     *  返回通过反转指定{@code int}值的二进制补码表示中的字节顺序获得的值。
     * 
     * 
     * @param i the value whose bytes are to be reversed
     * @return the value obtained by reversing the bytes in the specified
     *     {@code int} value.
     * @since 1.5
     */
    public static int reverseBytes(int i) {
        return ((i >>> 24)           ) |
               ((i >>   8) &   0xFF00) |
               ((i <<   8) & 0xFF0000) |
               ((i << 24));
    }

    /**
     * Adds two integers together as per the + operator.
     *
     * <p>
     *  按照+运算符将两个整数加在一起。
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static int sum(int a, int b) {
        return a + b;
    }

    /**
     * Returns the greater of two {@code int} values
     * as if by calling {@link Math#max(int, int) Math.max}.
     *
     * <p>
     *  返回两个{@code int}值中的较大值,如同调用{@link Math#max(int,int)Math.max}。
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the greater of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code int} values
     * as if by calling {@link Math#min(int, int) Math.min}.
     *
     * <p>
     *  返回两个{@code int}值中较小的值,就像调用{@link Math#min(int,int)Math.min}一样。
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the smaller of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static int min(int a, int b) {
        return Math.min(a, b);
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    @Native private static final long serialVersionUID = 1360826667806852920L;
}
