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
import java.math.*;


/**
 * The {@code Long} class wraps a value of the primitive type {@code
 * long} in an object. An object of type {@code Long} contains a
 * single field whose type is {@code long}.
 *
 * <p> In addition, this class provides several methods for converting
 * a {@code long} to a {@code String} and a {@code String} to a {@code
 * long}, as well as other constants and methods useful when dealing
 * with a {@code long}.
 *
 * <p>Implementation note: The implementations of the "bit twiddling"
 * methods (such as {@link #highestOneBit(long) highestOneBit} and
 * {@link #numberOfTrailingZeros(long) numberOfTrailingZeros}) are
 * based on material from Henry S. Warren, Jr.'s <i>Hacker's
 * Delight</i>, (Addison Wesley, 2002).
 *
 * <p>
 *  {@code Long}类在对象中封装了原始类型{@code long}的值。类型为{@code Long}的对象包含类型为{@code long}的单个字段。
 * 
 *  <p>此外,此类提供了几种方法,用于将{@code long}转换为{@code String}和{@code String}转换为{@code long},以及其他常数和方法处理一个{@code long}
 * 。
 * 
 *  <p>实现注意事项："bit twiddling"方法(例如{@link #highestOneBit(long)highestOneBit}和{@link #numberOfTrailingZeros(long)numberOfTrailingZeros}
 * )的实现基于来自Henry S. Warren, Jr.的</i> Hacker's Delight </i>,(Addison Wesley,2002)。
 * 
 * 
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Josh Bloch
 * @author  Joseph D. Darcy
 * @since   JDK1.0
 */
public final class Long extends Number implements Comparable<Long> {
    /**
     * A constant holding the minimum value a {@code long} can
     * have, -2<sup>63</sup>.
     * <p>
     *  保持最小值a {@code long}的常数可以具有,-2 <sup> 63 </sup>。
     * 
     */
    @Native public static final long MIN_VALUE = 0x8000000000000000L;

    /**
     * A constant holding the maximum value a {@code long} can
     * have, 2<sup>63</sup>-1.
     * <p>
     *  保持最大值a {@code long}的常数可以具有2 <sup> 63 </sup> -1。
     * 
     */
    @Native public static final long MAX_VALUE = 0x7fffffffffffffffL;

    /**
     * The {@code Class} instance representing the primitive type
     * {@code long}.
     *
     * <p>
     *  代表原始类型{@code long}的{@code Class}实例。
     * 
     * 
     * @since   JDK1.1
     */
    @SuppressWarnings("unchecked")
    public static final Class<Long>     TYPE = (Class<Long>) Class.getPrimitiveClass("long");

    /**
     * Returns a string representation of the first argument in the
     * radix specified by the second argument.
     *
     * <p>If the radix is smaller than {@code Character.MIN_RADIX}
     * or larger than {@code Character.MAX_RADIX}, then the radix
     * {@code 10} is used instead.
     *
     * <p>If the first argument is negative, the first element of the
     * result is the ASCII minus sign {@code '-'}
     * ({@code '\u005Cu002d'}). If the first argument is not
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
     * {@code '\u005Cu007a'}. If {@code radix} is
     * <var>N</var>, then the first <var>N</var> of these characters
     * are used as radix-<var>N</var> digits in the order shown. Thus,
     * the digits for hexadecimal (radix 16) are
     * {@code 0123456789abcdef}. If uppercase letters are
     * desired, the {@link java.lang.String#toUpperCase()} method may
     * be called on the result:
     *
     * <blockquote>
     *  {@code Long.toString(n, 16).toUpperCase()}
     * </blockquote>
     *
     * <p>
     *  返回由第二个参数指定的基数中的第一个参数的字符串表示形式。
     * 
     *  <p>如果基数小于{@code Character.MIN_RADIX}或大于{@code Character.MAX_RADIX},那么将使用基数{@code 10}。
     * 
     * <p>如果第一个参数为负数,结果的第一个元素是ASCII减号{@code' - '}({@code'\ u005Cu002d'})。如果第一个参数不为负,结果中不会出现符号字符。
     * 
     *  <p>结果的其余字符表示第一个参数的大小。如果幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,幅度表示的第一个字符将不是零字符。
     * 以下ASCII字符用作数字：。
     * 
     * <blockquote>
     *  {@code 0123456789abcdefghijklmnopqrstuvwxyz}
     * </blockquote>
     * 
     *  这些是{@code'\ u005Cu0030'}到{@code'\ u005Cu0039'}和{@code'\ u005Cu0061'}到{@code'\ u005Cu007a'}。
     * 如果{@code radix}是<var> N </var>,那么这些字符的第一个<var> N </var>将按照所示的顺序用作radix- <var> N </var>数字。
     * 因此,十六进制(基数16)的数字是{@code 0123456789abcdef}。
     * 如果需要大写字母,可以在结果上调用{@link java.lang.String#toUpperCase()}方法：。
     * 
     * <blockquote>
     *  {@code Long.toString(n,16).toUpperCase()}
     * </blockquote>
     * 
     * 
     * @param   i       a {@code long} to be converted to a string.
     * @param   radix   the radix to use in the string representation.
     * @return  a string representation of the argument in the specified radix.
     * @see     java.lang.Character#MAX_RADIX
     * @see     java.lang.Character#MIN_RADIX
     */
    public static String toString(long i, int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX)
            radix = 10;
        if (radix == 10)
            return toString(i);
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);

        if (!negative) {
            i = -i;
        }

        while (i <= -radix) {
            buf[charPos--] = Integer.digits[(int)(-(i % radix))];
            i = i / radix;
        }
        buf[charPos] = Integer.digits[(int)(-i)];

        if (negative) {
            buf[--charPos] = '-';
        }

        return new String(buf, charPos, (65 - charPos));
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
     * are the same as {@link #toString(long, int) toString}.
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
     *  <p>基数和用作数字的字符的行为与{@link #toString(long,int)toString}相同。
     * 
     * 
     * @param   i       an integer to be converted to an unsigned string.
     * @param   radix   the radix to use in the string representation.
     * @return  an unsigned string representation of the argument in the specified radix.
     * @see     #toString(long, int)
     * @since 1.8
     */
    public static String toUnsignedString(long i, int radix) {
        if (i >= 0)
            return toString(i, radix);
        else {
            switch (radix) {
            case 2:
                return toBinaryString(i);

            case 4:
                return toUnsignedString0(i, 2);

            case 8:
                return toOctalString(i);

            case 10:
                /*
                 * We can get the effect of an unsigned division by 10
                 * on a long value by first shifting right, yielding a
                 * positive value, and then dividing by 5.  This
                 * allows the last digit and preceding digits to be
                 * isolated more quickly than by an initial conversion
                 * to BigInteger.
                 * <p>
                 *  我们可以通过首先向右移位,产生正值,然后除以5,得到无符号除法10对长值的影响。这允许最后一位数字和前面的数字比通过初始转换更快地被隔离BigInteger。
                 * 
                 */
                long quot = (i >>> 1) / 5;
                long rem = i - quot * 10;
                return toString(quot) + rem;

            case 16:
                return toHexString(i);

            case 32:
                return toUnsignedString0(i, 5);

            default:
                return toUnsignedBigInteger(i).toString(radix);
            }
        }
    }

    /**
     * Return a BigInteger equal to the unsigned value of the
     * argument.
     * <p>
     *  返回等于参数的无符号值的BigInteger。
     * 
     */
    private static BigInteger toUnsignedBigInteger(long i) {
        if (i >= 0L)
            return BigInteger.valueOf(i);
        else {
            int upper = (int) (i >>> 32);
            int lower = (int) i;

            // return (upper << 32) + lower
            return (BigInteger.valueOf(Integer.toUnsignedLong(upper))).shiftLeft(32).
                add(BigInteger.valueOf(Integer.toUnsignedLong(lower)));
        }
    }

    /**
     * Returns a string representation of the {@code long}
     * argument as an unsigned integer in base&nbsp;16.
     *
     * <p>The unsigned {@code long} value is the argument plus
     * 2<sup>64</sup> if the argument is negative; otherwise, it is
     * equal to the argument.  This value is converted to a string of
     * ASCII digits in hexadecimal (base&nbsp;16) with no extra
     * leading {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Long#parseUnsignedLong(String, int) Long.parseUnsignedLong(s,
     * 16)}.
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
     * {@code '\u005Cu0039'} and  {@code '\u005Cu0061'} through
     * {@code '\u005Cu0066'}.  If uppercase letters are desired,
     * the {@link java.lang.String#toUpperCase()} method may be called
     * on the result:
     *
     * <blockquote>
     *  {@code Long.toHexString(n).toUpperCase()}
     * </blockquote>
     *
     * <p>
     *  在基础16中返回{@code long}参数的字符串表示形式,作为无符号整数。
     * 
     *  <p>无符号{@code long}值是参数加上2 <sup> 64 </sup>如果参数为负数,否则,它等于参数。
     * 此值将转换为十六进制(base&nbsp; 16)的ASCII字符串字符串,没有额外的前导{@code 0}。
     * 
     *  <p>参数的值可以通过调用{@link Long#parseUnsignedLong(String,int)Long.parseUnsignedLong(s,16)}从返回的字符串{@code s}中
     * 恢复。
     * 
     *  <p>如果无符号幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,无符号幅度的表示的第一个字符将不是零字符。以下字符用作十六进制数字：
     * 
     * <blockquote>
     * {@code 0123456789abcdef}
     * </blockquote>
     * 
     *  这些是字符{@code'\ u005Cu0030'}到{@code'\ u005Cu0039'}和{@code'\ u005Cu0061'}到{@code'\ u005Cu0066'}。
     * 如果需要大写字母,可以在结果上调用{@link java.lang.String#toUpperCase()}方法：。
     * 
     * <blockquote>
     *  {@code Long.toHexString(n).toUpperCase()}
     * </blockquote>
     * 
     * 
     * @param   i   a {@code long} to be converted to a string.
     * @return  the string representation of the unsigned {@code long}
     *          value represented by the argument in hexadecimal
     *          (base&nbsp;16).
     * @see #parseUnsignedLong(String, int)
     * @see #toUnsignedString(long, int)
     * @since   JDK 1.0.2
     */
    public static String toHexString(long i) {
        return toUnsignedString0(i, 4);
    }

    /**
     * Returns a string representation of the {@code long}
     * argument as an unsigned integer in base&nbsp;8.
     *
     * <p>The unsigned {@code long} value is the argument plus
     * 2<sup>64</sup> if the argument is negative; otherwise, it is
     * equal to the argument.  This value is converted to a string of
     * ASCII digits in octal (base&nbsp;8) with no extra leading
     * {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Long#parseUnsignedLong(String, int) Long.parseUnsignedLong(s,
     * 8)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * following characters are used as octal digits:
     *
     * <blockquote>
     *  {@code 01234567}
     * </blockquote>
     *
     * These are the characters {@code '\u005Cu0030'} through
     * {@code '\u005Cu0037'}.
     *
     * <p>
     *  返回{@code long}参数的字符串表示形式,作为第8个字节中的无符号整数。
     * 
     *  <p>无符号{@code long}值是参数加上2 <sup> 64 </sup>如果参数为负数,否则,它等于参数。
     * 此值将转换为八进制(基数为8)的ASCII数字字符串,没有额外的前导{@code 0}。
     * 
     *  <p>参数的值可以通过调用{@link Long#parseUnsignedLong(String,int)Long.parseUnsignedLong(s,8)}从返回的字符串{@code s}中恢
     * 复。
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
     * @param   i   a {@code long} to be converted to a string.
     * @return  the string representation of the unsigned {@code long}
     *          value represented by the argument in octal (base&nbsp;8).
     * @see #parseUnsignedLong(String, int)
     * @see #toUnsignedString(long, int)
     * @since   JDK 1.0.2
     */
    public static String toOctalString(long i) {
        return toUnsignedString0(i, 3);
    }

    /**
     * Returns a string representation of the {@code long}
     * argument as an unsigned integer in base&nbsp;2.
     *
     * <p>The unsigned {@code long} value is the argument plus
     * 2<sup>64</sup> if the argument is negative; otherwise, it is
     * equal to the argument.  This value is converted to a string of
     * ASCII digits in binary (base&nbsp;2) with no extra leading
     * {@code 0}s.
     *
     * <p>The value of the argument can be recovered from the returned
     * string {@code s} by calling {@link
     * Long#parseUnsignedLong(String, int) Long.parseUnsignedLong(s,
     * 2)}.
     *
     * <p>If the unsigned magnitude is zero, it is represented by a
     * single zero character {@code '0'} ({@code '\u005Cu0030'});
     * otherwise, the first character of the representation of the
     * unsigned magnitude will not be the zero character. The
     * characters {@code '0'} ({@code '\u005Cu0030'}) and {@code
     * '1'} ({@code '\u005Cu0031'}) are used as binary digits.
     *
     * <p>
     *  返回{@code long}参数的字符串表示形式,作为基础2中的无符号整数。
     * 
     * <p>无符号{@code long}值是参数加上2 <sup> 64 </sup>如果参数为负数,否则,它等于参数。
     * 此值将转换为二进制(基础2)的ASCII数字字符串,没有额外的前导{@code 0}。
     * 
     *  <p>参数的值可以通过调用{@link Long#parseUnsignedLong(String,int)Long.parseUnsignedLong(s,2)}从返回的字符串{@code s}中恢
     * 复。
     * 
     *  <p>如果无符号幅度为零,则它由单个零字符{@code'0'}({@code'\ u005Cu0030'})表示;否则,无符号幅度的表示的第一个字符将不是零字符。
     * 字符{@code'0'}({@code'\ u005Cu0030'})和{@code'1'}({@code'\ u005Cu0031'})用作二进制数字。
     * 
     * 
     * @param   i   a {@code long} to be converted to a string.
     * @return  the string representation of the unsigned {@code long}
     *          value represented by the argument in binary (base&nbsp;2).
     * @see #parseUnsignedLong(String, int)
     * @see #toUnsignedString(long, int)
     * @since   JDK 1.0.2
     */
    public static String toBinaryString(long i) {
        return toUnsignedString0(i, 1);
    }

    /**
     * Format a long (treated as unsigned) into a String.
     * <p>
     *  将一个long(被视为无符号)格式化为一个String。
     * 
     * 
     * @param val the value to format
     * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
     */
    static String toUnsignedString0(long val, int shift) {
        // assert shift > 0 && shift <=5 : "Illegal shift value";
        int mag = Long.SIZE - Long.numberOfLeadingZeros(val);
        int chars = Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf = new char[chars];

        formatUnsignedLong(val, shift, buf, 0, chars);
        return new String(buf, true);
    }

    /**
     * Format a long (treated as unsigned) into a character buffer.
     * <p>
     *  将long(被视为无符号)格式化为字符缓冲区。
     * 
     * 
     * @param val the unsigned long to format
     * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
     * @param buf the character buffer to write to
     * @param offset the offset in the destination buffer to start at
     * @param len the number of characters to write
     * @return the lowest character location used
     */
     static int formatUnsignedLong(long val, int shift, char[] buf, int offset, int len) {
        int charPos = len;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[offset + --charPos] = Integer.digits[((int) val) & mask];
            val >>>= shift;
        } while (val != 0 && charPos > 0);

        return charPos;
    }

    /**
     * Returns a {@code String} object representing the specified
     * {@code long}.  The argument is converted to signed decimal
     * representation and returned as a string, exactly as if the
     * argument and the radix 10 were given as arguments to the {@link
     * #toString(long, int)} method.
     *
     * <p>
     *  返回表示指定的{@code long}的{@code String}对象。
     * 参数被转换为有符号的十进制表示,并作为字符串返回,正如将参数和基数10作为{@link #toString(long,int)}方法的参数一样。
     * 
     * 
     * @param   i   a {@code long} to be converted.
     * @return  a string representation of the argument in base&nbsp;10.
     */
    public static String toString(long i) {
        if (i == Long.MIN_VALUE)
            return "-9223372036854775808";
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
     * 10 were given as arguments to the {@link #toUnsignedString(long,
     * int)} method.
     *
     * <p>
     *  以无符号十进制值形式返回参数的字符串表示形式。
     * 
     *  参数被转换为无符号十进制表示,并作为字符串返回,正如将参数和radix 10作为{@link #toUnsignedString(long,int)}方法的参数一样。
     * 
     * 
     * @param   i  an integer to be converted to an unsigned string.
     * @return  an unsigned string representation of the argument.
     * @see     #toUnsignedString(long, int)
     * @since 1.8
     */
    public static String toUnsignedString(long i) {
        return toUnsignedString(i, 10);
    }

    /**
     * Places characters representing the integer i into the
     * character array buf. The characters are placed into
     * the buffer backwards starting with the least significant
     * digit at the specified index (exclusive), and working
     * backwards from there.
     *
     * Will fail if i == Long.MIN_VALUE
     * <p>
     * 将表示整数i的字符放入字符数组buf中。字符从指定索引(独占)处的最低有效数字开始向后放置到缓冲区中,并从那里向后工作。
     * 
     *  如果i == Long.MIN_VALUE,将失败
     * 
     */
    static void getChars(long i, int index, char[] buf) {
        long q;
        int r;
        int charPos = index;
        char sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Get 2 digits/iteration using longs until quotient fits into an int
        while (i > Integer.MAX_VALUE) {
            q = i / 100;
            // really: r = i - (q * 100);
            r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));
            i = q;
            buf[--charPos] = Integer.DigitOnes[r];
            buf[--charPos] = Integer.DigitTens[r];
        }

        // Get 2 digits/iteration using ints
        int q2;
        int i2 = (int)i;
        while (i2 >= 65536) {
            q2 = i2 / 100;
            // really: r = i2 - (q * 100);
            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
            i2 = q2;
            buf[--charPos] = Integer.DigitOnes[r];
            buf[--charPos] = Integer.DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i2 <= 65536, i2);
        for (;;) {
            q2 = (i2 * 52429) >>> (16+3);
            r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
            buf[--charPos] = Integer.digits[r];
            i2 = q2;
            if (i2 == 0) break;
        }
        if (sign != 0) {
            buf[--charPos] = sign;
        }
    }

    // Requires positive x
    static int stringSize(long x) {
        long p = 10;
        for (int i=1; i<19; i++) {
            if (x < p)
                return i;
            p = 10*p;
        }
        return 19;
    }

    /**
     * Parses the string argument as a signed {@code long} in the
     * radix specified by the second argument. The characters in the
     * string must all be digits of the specified radix (as determined
     * by whether {@link java.lang.Character#digit(char, int)} returns
     * a nonnegative value), except that the first character may be an
     * ASCII minus sign {@code '-'} ({@code '\u005Cu002D'}) to
     * indicate a negative value or an ASCII plus sign {@code '+'}
     * ({@code '\u005Cu002B'}) to indicate a positive value. The
     * resulting {@code long} value is returned.
     *
     * <p>Note that neither the character {@code L}
     * ({@code '\u005Cu004C'}) nor {@code l}
     * ({@code '\u005Cu006C'}) is permitted to appear at the end
     * of the string as a type indicator, as would be permitted in
     * Java programming language source code - except that either
     * {@code L} or {@code l} may appear as a digit for a
     * radix greater than or equal to 22.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     *
     * <li>The first argument is {@code null} or is a string of
     * length zero.
     *
     * <li>The {@code radix} is either smaller than {@link
     * java.lang.Character#MIN_RADIX} or larger than {@link
     * java.lang.Character#MAX_RADIX}.
     *
     * <li>Any character of the string is not a digit of the specified
     * radix, except that the first character may be a minus sign
     * {@code '-'} ({@code '\u005Cu002d'}) or plus sign {@code
     * '+'} ({@code '\u005Cu002B'}) provided that the string is
     * longer than length 1.
     *
     * <li>The value represented by the string is not a value of type
     *      {@code long}.
     * </ul>
     *
     * <p>Examples:
     * <blockquote><pre>
     * parseLong("0", 10) returns 0L
     * parseLong("473", 10) returns 473L
     * parseLong("+42", 10) returns 42L
     * parseLong("-0", 10) returns 0L
     * parseLong("-FF", 16) returns -255L
     * parseLong("1100110", 2) returns 102L
     * parseLong("99", 8) throws a NumberFormatException
     * parseLong("Hazelnut", 10) throws a NumberFormatException
     * parseLong("Hazelnut", 36) returns 1356099454469L
     * </pre></blockquote>
     *
     * <p>
     *  将string参数解析为由第二个参数指定的基数中的带符号的{@code long}。
     * 字符串中的字符必须都是指定基数的数字(由{@link java.lang.Character#digit(char,int)}返回一个非负值决定),除了第一个字符可能是一个ASCII减标记{@code' - '}
     * ({@code'\ u005Cu002D'})以指示负值或ASCII加号{@code'+'}({@code'\ u005Cu002B'}) 。
     *  将string参数解析为由第二个参数指定的基数中的带符号的{@code long}。返回生成的{@code long}值。
     * 
     *  <p>请注意,字符{@code L}({@code'\ u005Cu004C'})和{@code l}({@code'\ u005Cu006C'})都不允许出现在字符串的结尾类型指示符,除了{@code L}
     * 或{@code l}可能出现为大于或等于22的基数的数字时,将在Java编程语言源代码中允许。
     * 
     *  <p>如果发生以下任何情况,将抛出{@code NumberFormatException}类型的异常：
     * <ul>
     * 
     *  <li>第一个参数是{@code null}或是长度为零的字符串。
     * 
     *  <li> {@code radix}小于{@link java.lang.Character#MIN_RADIX}或大于{@link java.lang.Character#MAX_RADIX}。
     * 
     * <li>字符串的任何字符都不是指定基数的数字,但第一个字符可能是减号{@code' - '}({@code'\ u005Cu002d'})或加号{@code '+'}({@code'\ u005Cu002B'}
     * ),前提是字符串长度大于长度1。
     * 
     *  <li>字符串表示的值不是类型{@code long}的值。
     * </ul>
     * 
     *  <p>例如：parseLong("0",10)返回0L parseLong("473",10)返回473L parseLong("+ 42",10)返回42L parseLong )返回0L pars
     * eLong("Hazelnut",10)抛出一个NumberFormatException parseLong("1100"),返回0L parseLong(" -  FF",16) Hazelnut"
     * ,36)返回1356099454469L </pre> </blockquote>。
     * 
     * 
     * @param      s       the {@code String} containing the
     *                     {@code long} representation to be parsed.
     * @param      radix   the radix to be used while parsing {@code s}.
     * @return     the {@code long} represented by the string argument in
     *             the specified radix.
     * @throws     NumberFormatException  if the string does not contain a
     *             parsable {@code long}.
     */
    public static long parseLong(String s, int radix)
              throws NumberFormatException
    {
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

        long result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
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
     * Parses the string argument as a signed decimal {@code long}.
     * The characters in the string must all be decimal digits, except
     * that the first character may be an ASCII minus sign {@code '-'}
     * ({@code \u005Cu002D'}) to indicate a negative value or an
     * ASCII plus sign {@code '+'} ({@code '\u005Cu002B'}) to
     * indicate a positive value. The resulting {@code long} value is
     * returned, exactly as if the argument and the radix {@code 10}
     * were given as arguments to the {@link
     * #parseLong(java.lang.String, int)} method.
     *
     * <p>Note that neither the character {@code L}
     * ({@code '\u005Cu004C'}) nor {@code l}
     * ({@code '\u005Cu006C'}) is permitted to appear at the end
     * of the string as a type indicator, as would be permitted in
     * Java programming language source code.
     *
     * <p>
     *  将字符串参数解析为带符号的十进制{@code long}。
     * 字符串中的字符必须都是十进制数字,除了第一个字符可以是一个ASCII减号{@code' - '}({@code \ u005Cu002D'}),以指示负值或ASCII加号{@ code'+'}({@code'\ u005Cu002B'}
     * )以指示正值。
     *  将字符串参数解析为带符号的十进制{@code long}。
     * 返回所得到的{@code long}值,就像参数和基数{@code 10}作为{@link #parseLong(java.lang.String,int)}方法的参数一样。
     * 
     *  <p>请注意,字符{@code L}({@code'\ u005Cu004C'})和{@code l}({@code'\ u005Cu006C'})都不允许出现在字符串的结尾类型指示符,如Java编程
     * 语言源代码中所允许的。
     * 
     * 
     * @param      s   a {@code String} containing the {@code long}
     *             representation to be parsed
     * @return     the {@code long} represented by the argument in
     *             decimal.
     * @throws     NumberFormatException  if the string does not contain a
     *             parsable {@code long}.
     */
    public static long parseLong(String s) throws NumberFormatException {
        return parseLong(s, 10);
    }

    /**
     * Parses the string argument as an unsigned {@code long} in the
     * radix specified by the second argument.  An unsigned integer
     * maps the values usually associated with negative numbers to
     * positive numbers larger than {@code MAX_VALUE}.
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
     * largest unsigned {@code long}, 2<sup>64</sup>-1.
     *
     * </ul>
     *
     *
     * <p>
     * 将字符串参数解析为由第二个参数指定的基数中的无符号{@code long}。无符号整数将通常与负数关联的值映射为大于{@code MAX_VALUE}的正数。
     * 
     *  字符串中的字符必须都是指定基数的数字(由{@link java.lang.Character#digit(char,int)}返回一个非负值),除了第一个字符可以是ASCII加符号{@code'+'}
     * ({@code'\ u005Cu002B'})。
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
     *  <li>由字符串表示的值大于最大的无符号{@code long},2 <sup> 64 </sup> -1。
     * 
     * </ul>
     * 
     * 
     * @param      s   the {@code String} containing the unsigned integer
     *                  representation to be parsed
     * @param      radix   the radix to be used while parsing {@code s}.
     * @return     the unsigned {@code long} represented by the string
     *             argument in the specified radix.
     * @throws     NumberFormatException if the {@code String}
     *             does not contain a parsable {@code long}.
     * @since 1.8
     */
    public static long parseUnsignedLong(String s, int radix)
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
                if (len <= 12 || // Long.MAX_VALUE in Character.MAX_RADIX is 13 digits
                    (radix == 10 && len <= 18) ) { // Long.MAX_VALUE in base 10 is 19 digits
                    return parseLong(s, radix);
                }

                // No need for range checks on len due to testing above.
                long first = parseLong(s.substring(0, len - 1), radix);
                int second = Character.digit(s.charAt(len - 1), radix);
                if (second < 0) {
                    throw new NumberFormatException("Bad digit at end of " + s);
                }
                long result = first * radix + second;
                if (compareUnsigned(result, first) < 0) {
                    /*
                     * The maximum unsigned value, (2^64)-1, takes at
                     * most one more digit to represent than the
                     * maximum signed value, (2^63)-1.  Therefore,
                     * parsing (len - 1) digits will be appropriately
                     * in-range of the signed parsing.  In other
                     * words, if parsing (len -1) digits overflows
                     * signed parsing, parsing len digits will
                     * certainly overflow unsigned parsing.
                     *
                     * The compareUnsigned check above catches
                     * situations where an unsigned overflow occurs
                     * incorporating the contribution of the final
                     * digit.
                     * <p>
                     * 最大无符号值(2 ^ 64)-1最多用一个数字表示最大有符号值,(2 ^ 63)-1。因此,解析(len-1)数字将适当地在带符号解析的范围内。
                     * 换句话说,如果解析(len -1)数字溢出带符号解析,解析len数字肯定会溢出无符号解析。
                     * 
                     *  上面的compareUnsigned检查捕获了出现无符号溢出的情况,其中包括最后一位数字的贡献。
                     * 
                     */
                    throw new NumberFormatException(String.format("String value %s exceeds " +
                                                                  "range of unsigned long.", s));
                }
                return result;
            }
        } else {
            throw NumberFormatException.forInputString(s);
        }
    }

    /**
     * Parses the string argument as an unsigned decimal {@code long}. The
     * characters in the string must all be decimal digits, except
     * that the first character may be an an ASCII plus sign {@code
     * '+'} ({@code '\u005Cu002B'}). The resulting integer value
     * is returned, exactly as if the argument and the radix 10 were
     * given as arguments to the {@link
     * #parseUnsignedLong(java.lang.String, int)} method.
     *
     * <p>
     *  将字符串参数解析为无符号十进制{@code long}。字符串中的字符必须都是十进制数字,除了第一个字符可以是ASCII加号{@code'+'}({@code'\ u005Cu002B'})。
     * 返回结果的整数值,就像参数和基数10作为{@link #parseUnsignedLong(java.lang.String,int)}方法的参数一样。
     * 
     * 
     * @param s   a {@code String} containing the unsigned {@code long}
     *            representation to be parsed
     * @return    the unsigned {@code long} value represented by the decimal string argument
     * @throws    NumberFormatException  if the string does not contain a
     *            parsable unsigned integer.
     * @since 1.8
     */
    public static long parseUnsignedLong(String s) throws NumberFormatException {
        return parseUnsignedLong(s, 10);
    }

    /**
     * Returns a {@code Long} object holding the value
     * extracted from the specified {@code String} when parsed
     * with the radix given by the second argument.  The first
     * argument is interpreted as representing a signed
     * {@code long} in the radix specified by the second
     * argument, exactly as if the arguments were given to the {@link
     * #parseLong(java.lang.String, int)} method. The result is a
     * {@code Long} object that represents the {@code long}
     * value specified by the string.
     *
     * <p>In other words, this method returns a {@code Long} object equal
     * to the value of:
     *
     * <blockquote>
     *  {@code new Long(Long.parseLong(s, radix))}
     * </blockquote>
     *
     * <p>
     *  返回一个{@code Long}对象,当使用第二个参数给出的基数解析时,该对象保存从指定的{@code String}中提取的值。
     * 第一个参数被解释为表示由第二个参数指定的基数中的一个带符号的{@code long},就像参数被赋予{@link #parseLong(java.lang.String,int)}方法一样。
     * 结果是一个{@code Long}对象,它表示由字符串指定的{@code long}值。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Long}对象：
     * 
     * <blockquote>
     *  {@code new Long(Long.parseLong(s,radix))}
     * </blockquote>
     * 
     * 
     * @param      s       the string to be parsed
     * @param      radix   the radix to be used in interpreting {@code s}
     * @return     a {@code Long} object holding the value
     *             represented by the string argument in the specified
     *             radix.
     * @throws     NumberFormatException  If the {@code String} does not
     *             contain a parsable {@code long}.
     */
    public static Long valueOf(String s, int radix) throws NumberFormatException {
        return Long.valueOf(parseLong(s, radix));
    }

    /**
     * Returns a {@code Long} object holding the value
     * of the specified {@code String}. The argument is
     * interpreted as representing a signed decimal {@code long},
     * exactly as if the argument were given to the {@link
     * #parseLong(java.lang.String)} method. The result is a
     * {@code Long} object that represents the integer value
     * specified by the string.
     *
     * <p>In other words, this method returns a {@code Long} object
     * equal to the value of:
     *
     * <blockquote>
     *  {@code new Long(Long.parseLong(s))}
     * </blockquote>
     *
     * <p>
     * 返回一个包含指定的{@code String}值的{@code Long}对象。
     * 参数被解释为表示一个有符号的十进制{@code long},就像参数被赋予{@link #parseLong(java.lang.String)}方法一样。
     * 结果是一个{@code Long}对象,它表示由字符串指定的整数值。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Long}对象：
     * 
     * <blockquote>
     *  {@code new Long(Long.parseLong(s))}
     * </blockquote>
     * 
     * 
     * @param      s   the string to be parsed.
     * @return     a {@code Long} object holding the value
     *             represented by the string argument.
     * @throws     NumberFormatException  If the string cannot be parsed
     *             as a {@code long}.
     */
    public static Long valueOf(String s) throws NumberFormatException
    {
        return Long.valueOf(parseLong(s, 10));
    }

    private static class LongCache {
        private LongCache(){}

        static final Long cache[] = new Long[-(-128) + 127 + 1];

        static {
            for(int i = 0; i < cache.length; i++)
                cache[i] = new Long(i - 128);
        }
    }

    /**
     * Returns a {@code Long} instance representing the specified
     * {@code long} value.
     * If a new {@code Long} instance is not required, this method
     * should generally be used in preference to the constructor
     * {@link #Long(long)}, as this method is likely to yield
     * significantly better space and time performance by caching
     * frequently requested values.
     *
     * Note that unlike the {@linkplain Integer#valueOf(int)
     * corresponding method} in the {@code Integer} class, this method
     * is <em>not</em> required to cache values within a particular
     * range.
     *
     * <p>
     *  返回表示指定的{@code long}值的{@code Long}实例。
     * 如果不需要新的{@code Long}实例,通常应该优先使用构造函数{@link#Long(long)},因为此方法可能通过高速缓存获得明显更好的空间和时间性能请求值。
     * 
     *  请注意,与{@code Integer}类中的{@linkplain Integer#valueOf(int)对应方法}不同,该方法不是</em>需要在特定范围内缓存值。
     * 
     * 
     * @param  l a long value.
     * @return a {@code Long} instance representing {@code l}.
     * @since  1.5
     */
    public static Long valueOf(long l) {
        final int offset = 128;
        if (l >= -128 && l <= 127) { // will cache
            return LongCache.cache[(int)l + offset];
        }
        return new Long(l);
    }

    /**
     * Decodes a {@code String} into a {@code Long}.
     * Accepts decimal, hexadecimal, and octal numbers given by the
     * following grammar:
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
     * Long.parseLong} method with the indicated radix (10, 16, or 8).
     * This sequence of characters must represent a positive value or
     * a {@link NumberFormatException} will be thrown.  The result is
     * negated if first character of the specified {@code String} is
     * the minus sign.  No whitespace characters are permitted in the
     * {@code String}.
     *
     * <p>
     *  将{@code String}解码为{@code Long}。接受由以下语法提供的十进制,十六进制和八进制数：
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
     * <i> DecimalNumeral </i>,<HexDigits </i>和<OctalDigits </i>的定义在<cite> Java&trade;语言规范</cite>,除了在数字之间不接受
     * 下划线。
     * 
     *  <p>可选符号和/或基数说明符("{@code 0x}","{@code 0X}","{@code#}"或前导零)后面的字符序列由{@code Long.parseLong}方法与指定的基数(10,1
     * 6或8)。
     * 这个字符序列必须表示正值,否则将抛出{@link NumberFormatException}。如果指定的{@code String}的第一个字符是减号,则结果将被否定。
     *  {@code String}中不允许使用空格字符。
     * 
     * 
     * @param     nm the {@code String} to decode.
     * @return    a {@code Long} object holding the {@code long}
     *            value represented by {@code nm}
     * @throws    NumberFormatException  if the {@code String} does not
     *            contain a parsable {@code long}.
     * @see java.lang.Long#parseLong(String, int)
     * @since 1.2
     */
    public static Long decode(String nm) throws NumberFormatException {
        int radix = 10;
        int index = 0;
        boolean negative = false;
        Long result;

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
            result = Long.valueOf(nm.substring(index), radix);
            result = negative ? Long.valueOf(-result.longValue()) : result;
        } catch (NumberFormatException e) {
            // If number is Long.MIN_VALUE, we'll end up here. The next line
            // handles this case, and causes any genuine format error to be
            // rethrown.
            String constant = negative ? ("-" + nm.substring(index))
                                       : nm.substring(index);
            result = Long.valueOf(constant, radix);
        }
        return result;
    }

    /**
     * The value of the {@code Long}.
     *
     * <p>
     *  {@code Long}的值。
     * 
     * 
     * @serial
     */
    private final long value;

    /**
     * Constructs a newly allocated {@code Long} object that
     * represents the specified {@code long} argument.
     *
     * <p>
     *  构造一个新分配的{@code Long}对象,表示指定的{@code long}参数。
     * 
     * 
     * @param   value   the value to be represented by the
     *          {@code Long} object.
     */
    public Long(long value) {
        this.value = value;
    }

    /**
     * Constructs a newly allocated {@code Long} object that
     * represents the {@code long} value indicated by the
     * {@code String} parameter. The string is converted to a
     * {@code long} value in exactly the manner used by the
     * {@code parseLong} method for radix 10.
     *
     * <p>
     *  构造一个新分配的{@code Long}对象,表示{@code String}参数指示的{@code long}值。
     * 该字符串以{@code parseLong}方法为基数10使用的方式转换为{@code long}值。
     * 
     * 
     * @param      s   the {@code String} to be converted to a
     *             {@code Long}.
     * @throws     NumberFormatException  if the {@code String} does not
     *             contain a parsable {@code long}.
     * @see        java.lang.Long#parseLong(java.lang.String, int)
     */
    public Long(String s) throws NumberFormatException {
        this.value = parseLong(s, 10);
    }

    /**
     * Returns the value of this {@code Long} as a {@code byte} after
     * a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     * <p>
     *  在缩小的原始转换后,将此{@code Long}的值返回为{@code byte}。 @jls 5.1.3缩小基本转换
     * 
     */
    public byte byteValue() {
        return (byte)value;
    }

    /**
     * Returns the value of this {@code Long} as a {@code short} after
     * a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     * <p>
     *  在缩小的原始转换后,将此{@code Long}的值返回为{@code short}。 @jls 5.1.3缩小基本转换
     * 
     */
    public short shortValue() {
        return (short)value;
    }

    /**
     * Returns the value of this {@code Long} as an {@code int} after
     * a narrowing primitive conversion.
     * @jls 5.1.3 Narrowing Primitive Conversions
     * <p>
     * 在缩小的原始转换后,将此{@code Long}的值返回为{@code int}。 @jls 5.1.3缩小基本转换
     * 
     */
    public int intValue() {
        return (int)value;
    }

    /**
     * Returns the value of this {@code Long} as a
     * {@code long} value.
     * <p>
     *  将此{@code Long}的值作为{@code long}值返回。
     * 
     */
    public long longValue() {
        return value;
    }

    /**
     * Returns the value of this {@code Long} as a {@code float} after
     * a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后返回此{@code Long}的值作为{@code float}。 @jls 5.1.2扩大原始转换
     * 
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * Returns the value of this {@code Long} as a {@code double}
     * after a widening primitive conversion.
     * @jls 5.1.2 Widening Primitive Conversions
     * <p>
     *  在扩展基元转换后,将此{@code Long}的值返回为{@code double}。 @jls 5.1.2扩大原始转换
     * 
     */
    public double doubleValue() {
        return (double)value;
    }

    /**
     * Returns a {@code String} object representing this
     * {@code Long}'s value.  The value is converted to signed
     * decimal representation and returned as a string, exactly as if
     * the {@code long} value were given as an argument to the
     * {@link java.lang.Long#toString(long)} method.
     *
     * <p>
     *  返回表示此{@code Long}的值的{@code String}对象。
     * 该值将转换为带符号的十进制表示形式,并作为字符串返回,就像{@code long}值作为{@link java.lang.Long#toString(long)}方法的参数给出。
     * 
     * 
     * @return  a string representation of the value of this object in
     *          base&nbsp;10.
     */
    public String toString() {
        return toString(value);
    }

    /**
     * Returns a hash code for this {@code Long}. The result is
     * the exclusive OR of the two halves of the primitive
     * {@code long} value held by this {@code Long}
     * object. That is, the hashcode is the value of the expression:
     *
     * <blockquote>
     *  {@code (int)(this.longValue()^(this.longValue()>>>32))}
     * </blockquote>
     *
     * <p>
     *  返回此{@code Long}的哈希码。结果是由此{@code Long}对象保持的原始{@code long}值的两半的异或。也就是说,哈希码是表达式的值：
     * 
     * <blockquote>
     *  {@code(int)(this.longValue()^(this.longValue()>>> 32))}
     * </blockquote>
     * 
     * 
     * @return  a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    /**
     * Returns a hash code for a {@code long} value; compatible with
     * {@code Long.hashCode()}.
     *
     * <p>
     *  返回{@code long}值的哈希码;兼容{@code Long.hashCode()}。
     * 
     * 
     * @param value the value to hash
     * @return a hash code value for a {@code long} value.
     * @since 1.8
     */
    public static int hashCode(long value) {
        return (int)(value ^ (value >>> 32));
    }

    /**
     * Compares this object to the specified object.  The result is
     * {@code true} if and only if the argument is not
     * {@code null} and is a {@code Long} object that
     * contains the same {@code long} value as this object.
     *
     * <p>
     *  将此对象与指定的对象进行比较。如果且仅当参数不是{@code null}并且是包含与此对象相同的{@code long}值的{@code Long}对象时,结果是{@code true}。
     * 
     * 
     * @param   obj   the object to compare with.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Long) {
            return value == ((Long)obj).longValue();
        }
        return false;
    }

    /**
     * Determines the {@code long} value of the system property
     * with the specified name.
     *
     * <p>The first argument is treated as the name of a system
     * property.  System properties are accessible through the {@link
     * java.lang.System#getProperty(java.lang.String)} method. The
     * string value of this property is then interpreted as a {@code
     * long} value using the grammar supported by {@link Long#decode decode}
     * and a {@code Long} object representing this value is returned.
     *
     * <p>If there is no property with the specified name, if the
     * specified name is empty or {@code null}, or if the property
     * does not have the correct numeric format, then {@code null} is
     * returned.
     *
     * <p>In other words, this method returns a {@code Long} object
     * equal to the value of:
     *
     * <blockquote>
     *  {@code getLong(nm, null)}
     * </blockquote>
     *
     * <p>
     *  确定具有指定名称的系统属性的{@code long}值。
     * 
     * <p>第一个参数被视为系统属性的名称。系统属性可通过{@link java.lang.System#getProperty(java.lang.String)}方法访问。
     * 然后,使用{@link Long#decode decode}支持的语法将此属性的字符串值解释为{@code long}值,并返回表示此值的{@code Long}对象。
     * 
     *  <p>如果没有指定名称的属性,如果指定的名称为空或{@code null},或者该属性没有正确的数字格式,则返回{@code null}。
     * 
     *  <p>换句话说,此方法返回等于以下值的{@code Long}对象：
     * 
     * <blockquote>
     *  {@code getLong(nm,null)}
     * </blockquote>
     * 
     * 
     * @param   nm   property name.
     * @return  the {@code Long} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     java.lang.System#getProperty(java.lang.String)
     * @see     java.lang.System#getProperty(java.lang.String, java.lang.String)
     */
    public static Long getLong(String nm) {
        return getLong(nm, null);
    }

    /**
     * Determines the {@code long} value of the system property
     * with the specified name.
     *
     * <p>The first argument is treated as the name of a system
     * property.  System properties are accessible through the {@link
     * java.lang.System#getProperty(java.lang.String)} method. The
     * string value of this property is then interpreted as a {@code
     * long} value using the grammar supported by {@link Long#decode decode}
     * and a {@code Long} object representing this value is returned.
     *
     * <p>The second argument is the default value. A {@code Long} object
     * that represents the value of the second argument is returned if there
     * is no property of the specified name, if the property does not have
     * the correct numeric format, or if the specified name is empty or null.
     *
     * <p>In other words, this method returns a {@code Long} object equal
     * to the value of:
     *
     * <blockquote>
     *  {@code getLong(nm, new Long(val))}
     * </blockquote>
     *
     * but in practice it may be implemented in a manner such as:
     *
     * <blockquote><pre>
     * Long result = getLong(nm, null);
     * return (result == null) ? new Long(val) : result;
     * </pre></blockquote>
     *
     * to avoid the unnecessary allocation of a {@code Long} object when
     * the default value is not needed.
     *
     * <p>
     *  确定具有指定名称的系统属性的{@code long}值。
     * 
     *  <p>第一个参数被视为系统属性的名称。系统属性可通过{@link java.lang.System#getProperty(java.lang.String)}方法访问。
     * 然后,使用{@link Long#decode decode}支持的语法将此属性的字符串值解释为{@code long}值,并返回表示此值的{@code Long}对象。
     * 
     *  <p>第二个参数是默认值。如果没有指定名称的属性,如果属性没有正确的数字格式,或者如果指定的名称为空或为null,则会返回表示第二个参数的值的{@code Long}对象。
     * 
     * <p>换句话说,此方法返回等于以下值的{@code Long}对象：
     * 
     * <blockquote>
     *  {@code getLong(nm,new Long(val))}
     * </blockquote>
     * 
     *  但是在实践中它可以以如下方式实现：
     * 
     *  <blockquote> <pre> Long result = getLong(nm,null); return(result == null)? new Long(val)：result; </pre>
     *  </blockquote>。
     * 
     *  以避免在不需要默认值时对{@code Long}对象进行不必要的分配。
     * 
     * 
     * @param   nm    property name.
     * @param   val   default value.
     * @return  the {@code Long} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     java.lang.System#getProperty(java.lang.String)
     * @see     java.lang.System#getProperty(java.lang.String, java.lang.String)
     */
    public static Long getLong(String nm, long val) {
        Long result = Long.getLong(nm, null);
        return (result == null) ? Long.valueOf(val) : result;
    }

    /**
     * Returns the {@code long} value of the system property with
     * the specified name.  The first argument is treated as the name
     * of a system property.  System properties are accessible through
     * the {@link java.lang.System#getProperty(java.lang.String)}
     * method. The string value of this property is then interpreted
     * as a {@code long} value, as per the
     * {@link Long#decode decode} method, and a {@code Long} object
     * representing this value is returned; in summary:
     *
     * <ul>
     * <li>If the property value begins with the two ASCII characters
     * {@code 0x} or the ASCII character {@code #}, not followed by
     * a minus sign, then the rest of it is parsed as a hexadecimal integer
     * exactly as for the method {@link #valueOf(java.lang.String, int)}
     * with radix 16.
     * <li>If the property value begins with the ASCII character
     * {@code 0} followed by another character, it is parsed as
     * an octal integer exactly as by the method {@link
     * #valueOf(java.lang.String, int)} with radix 8.
     * <li>Otherwise the property value is parsed as a decimal
     * integer exactly as by the method
     * {@link #valueOf(java.lang.String, int)} with radix 10.
     * </ul>
     *
     * <p>Note that, in every case, neither {@code L}
     * ({@code '\u005Cu004C'}) nor {@code l}
     * ({@code '\u005Cu006C'}) is permitted to appear at the end
     * of the property value as a type indicator, as would be
     * permitted in Java programming language source code.
     *
     * <p>The second argument is the default value. The default value is
     * returned if there is no property of the specified name, if the
     * property does not have the correct numeric format, or if the
     * specified name is empty or {@code null}.
     *
     * <p>
     *  返回具有指定名称的系统属性的{@code long}值。第一个参数被视为系统属性的名称。
     * 系统属性可通过{@link java.lang.System#getProperty(java.lang.String)}方法访问。
     * 然后,根据{@link Long#decode decode}方法,此属性的字符串值被解释为{@code long}值,并返回表示此值的{@code Long}对象;综上所述：。
     * 
     * <ul>
     *  <li>如果属性值以两个ASCII字符{@code 0x}或ASCII字符{@code#}开头,后跟一个减号,则其余部分将解析为十六进制整数,正如方法{@link #valueOf(java.lang.String,int)}
     * 与radix 16. <li>如果属性值以ASCII字符{@code 0}开头,后跟另一个字符,它将被精确解析为八进制整数通过方法{@link #valueOf(java.lang.String,int)}
     * 与radix 8. <li>否则属性值被解析为一个十进制整数,正如方法{@link #valueOf(java.lang .String,int)}。
     * </ul>
     * 
     * <p>请注意,在任何情况下,{@code L}({@code'\ u005Cu004C'})和{@code l}({@code'\ u005Cu006C'})属性值作为类型指示符,这将在Java编程语言
     * 源代码中被允许。
     * 
     *  <p>第二个参数是默认值。如果没有指定名称的属性,如果属性没有正确的数字格式,或者如果指定的名称为空或{@code null},则返回默认值。
     * 
     * 
     * @param   nm   property name.
     * @param   val   default value.
     * @return  the {@code Long} value of the property.
     * @throws  SecurityException for the same reasons as
     *          {@link System#getProperty(String) System.getProperty}
     * @see     System#getProperty(java.lang.String)
     * @see     System#getProperty(java.lang.String, java.lang.String)
     */
    public static Long getLong(String nm, Long val) {
        String v = null;
        try {
            v = System.getProperty(nm);
        } catch (IllegalArgumentException | NullPointerException e) {
        }
        if (v != null) {
            try {
                return Long.decode(v);
            } catch (NumberFormatException e) {
            }
        }
        return val;
    }

    /**
     * Compares two {@code Long} objects numerically.
     *
     * <p>
     *  以数字方式比较两个{@code Long}对象。
     * 
     * 
     * @param   anotherLong   the {@code Long} to be compared.
     * @return  the value {@code 0} if this {@code Long} is
     *          equal to the argument {@code Long}; a value less than
     *          {@code 0} if this {@code Long} is numerically less
     *          than the argument {@code Long}; and a value greater
     *          than {@code 0} if this {@code Long} is numerically
     *           greater than the argument {@code Long} (signed
     *           comparison).
     * @since   1.2
     */
    public int compareTo(Long anotherLong) {
        return compare(this.value, anotherLong.value);
    }

    /**
     * Compares two {@code long} values numerically.
     * The value returned is identical to what would be returned by:
     * <pre>
     *    Long.valueOf(x).compareTo(Long.valueOf(y))
     * </pre>
     *
     * <p>
     *  以数字方式比较两个{@code long}值。返回的值与由以下内容返回的值相同：
     * <pre>
     *  Long.valueOf(x).compareTo(Long.valueOf(y))
     * </pre>
     * 
     * 
     * @param  x the first {@code long} to compare
     * @param  y the second {@code long} to compare
     * @return the value {@code 0} if {@code x == y};
     *         a value less than {@code 0} if {@code x < y}; and
     *         a value greater than {@code 0} if {@code x > y}
     * @since 1.7
     */
    public static int compare(long x, long y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    /**
     * Compares two {@code long} values numerically treating the values
     * as unsigned.
     *
     * <p>
     *  比较两个{@code long}值,以数字方式将值视为unsigned。
     * 
     * 
     * @param  x the first {@code long} to compare
     * @param  y the second {@code long} to compare
     * @return the value {@code 0} if {@code x == y}; a value less
     *         than {@code 0} if {@code x < y} as unsigned values; and
     *         a value greater than {@code 0} if {@code x > y} as
     *         unsigned values
     * @since 1.8
     */
    public static int compareUnsigned(long x, long y) {
        return compare(x + MIN_VALUE, y + MIN_VALUE);
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
    public static long divideUnsigned(long dividend, long divisor) {
        if (divisor < 0L) { // signed comparison
            // Answer must be 0 or 1 depending on relative magnitude
            // of dividend and divisor.
            return (compareUnsigned(dividend, divisor)) < 0 ? 0L :1L;
        }

        if (dividend > 0) //  Both inputs non-negative
            return dividend/divisor;
        else {
            /*
             * For simple code, leveraging BigInteger.  Longer and faster
             * code written directly in terms of operations on longs is
             * possible; see "Hacker's Delight" for divide and remainder
             * algorithms.
             * <p>
             *  对于简单的代码,利用BigInteger。更长和更快的代码直接写在操作上对longs是可能的;参见"Hacker's Delight"用于除法和余数算法。
             * 
             */
            return toUnsignedBigInteger(dividend).
                divide(toUnsignedBigInteger(divisor)).longValue();
        }
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     *
     * <p>
     * 返回将第一个参数除以第二个的无符号余数,其中每个参数和结果解释为无符号值。
     * 
     * 
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument
     * @see #divideUnsigned
     * @since 1.8
     */
    public static long remainderUnsigned(long dividend, long divisor) {
        if (dividend > 0 && divisor > 0) { // signed comparisons
            return dividend % divisor;
        } else {
            if (compareUnsigned(dividend, divisor) < 0) // Avoid explicit check for 0 divisor
                return dividend;
            else
                return toUnsignedBigInteger(dividend).
                    remainder(toUnsignedBigInteger(divisor)).longValue();
        }
    }

    // Bit Twiddling

    /**
     * The number of bits used to represent a {@code long} value in two's
     * complement binary form.
     *
     * <p>
     *  用于以二进制补码二进制形式表示{@code long}值的位数。
     * 
     * 
     * @since 1.5
     */
    @Native public static final int SIZE = 64;

    /**
     * The number of bytes used to represent a {@code long} value in two's
     * complement binary form.
     *
     * <p>
     *  用于以二进制补码二进制形式表示{@code long}值的字节数。
     * 
     * 
     * @since 1.8
     */
    public static final int BYTES = SIZE / Byte.SIZE;

    /**
     * Returns a {@code long} value with at most a single one-bit, in the
     * position of the highest-order ("leftmost") one-bit in the specified
     * {@code long} value.  Returns zero if the specified value has no
     * one-bits in its two's complement binary representation, that is, if it
     * is equal to zero.
     *
     * <p>
     *  在指定的{@code long}值中的最高位("最左边")一位的位置中返回最多有一个一位的{@code long}值。如果指定的值在其二进制补码二进制表示中没有一个比特,即如果它等于零,则返回零。
     * 
     * 
     * @param i the value whose highest one bit is to be computed
     * @return a {@code long} value with a single one-bit, in the position
     *     of the highest-order one-bit in the specified value, or zero if
     *     the specified value is itself equal to zero.
     * @since 1.5
     */
    public static long highestOneBit(long i) {
        // HD, Figure 3-1
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        i |= (i >> 32);
        return i - (i >>> 1);
    }

    /**
     * Returns a {@code long} value with at most a single one-bit, in the
     * position of the lowest-order ("rightmost") one-bit in the specified
     * {@code long} value.  Returns zero if the specified value has no
     * one-bits in its two's complement binary representation, that is, if it
     * is equal to zero.
     *
     * <p>
     *  在指定的{@code long}值中的最低位("最右侧")一位的位置中返回最多有一个一位的{@code long}值。如果指定的值在其二进制补码二进制表示中没有一个比特,即如果它等于零,则返回零。
     * 
     * 
     * @param i the value whose lowest one bit is to be computed
     * @return a {@code long} value with a single one-bit, in the position
     *     of the lowest-order one-bit in the specified value, or zero if
     *     the specified value is itself equal to zero.
     * @since 1.5
     */
    public static long lowestOneBit(long i) {
        // HD, Section 2-1
        return i & -i;
    }

    /**
     * Returns the number of zero bits preceding the highest-order
     * ("leftmost") one-bit in the two's complement binary representation
     * of the specified {@code long} value.  Returns 64 if the
     * specified value has no one-bits in its two's complement representation,
     * in other words if it is equal to zero.
     *
     * <p>Note that this method is closely related to the logarithm base 2.
     * For all positive {@code long} values x:
     * <ul>
     * <li>floor(log<sub>2</sub>(x)) = {@code 63 - numberOfLeadingZeros(x)}
     * <li>ceil(log<sub>2</sub>(x)) = {@code 64 - numberOfLeadingZeros(x - 1)}
     * </ul>
     *
     * <p>
     *  返回指定{@code long}值的二进制补码二进制表示中最高位("最左边")一位之前的零位数。如果指定的值在其二进制补码表示中没有一个位,则返回64,换句话说,如果它等于零。
     * 
     *  <p>请注意,此方法与对数基数密切相关2.对于所有正的{@code long}值x：
     * <ul>
     *  <li> floor(log <sub> 2 </sub>(x))= {@code 63-numberOfLeadingZeros(x)} <li> ceil(log < code 64  -  numberOfLeadingZeros(x  -  1)}。
     * </ul>
     * 
     * 
     * @param i the value whose number of leading zeros is to be computed
     * @return the number of zero bits preceding the highest-order
     *     ("leftmost") one-bit in the two's complement binary representation
     *     of the specified {@code long} value, or 64 if the value
     *     is equal to zero.
     * @since 1.5
     */
    public static int numberOfLeadingZeros(long i) {
        // HD, Figure 5-6
         if (i == 0)
            return 64;
        int n = 1;
        int x = (int)(i >>> 32);
        if (x == 0) { n += 32; x = (int)i; }
        if (x >>> 16 == 0) { n += 16; x <<= 16; }
        if (x >>> 24 == 0) { n +=  8; x <<=  8; }
        if (x >>> 28 == 0) { n +=  4; x <<=  4; }
        if (x >>> 30 == 0) { n +=  2; x <<=  2; }
        n -= x >>> 31;
        return n;
    }

    /**
     * Returns the number of zero bits following the lowest-order ("rightmost")
     * one-bit in the two's complement binary representation of the specified
     * {@code long} value.  Returns 64 if the specified value has no
     * one-bits in its two's complement representation, in other words if it is
     * equal to zero.
     *
     * <p>
     * 返回指定{@code long}值的二进制补码二进制表示中最低位("最右侧")一位后的零位数。如果指定的值在其二进制补码表示中没有一个位,则返回64,换句话说,如果它等于零。
     * 
     * 
     * @param i the value whose number of trailing zeros is to be computed
     * @return the number of zero bits following the lowest-order ("rightmost")
     *     one-bit in the two's complement binary representation of the
     *     specified {@code long} value, or 64 if the value is equal
     *     to zero.
     * @since 1.5
     */
    public static int numberOfTrailingZeros(long i) {
        // HD, Figure 5-14
        int x, y;
        if (i == 0) return 64;
        int n = 63;
        y = (int)i; if (y != 0) { n = n -32; x = y; } else x = (int)(i>>>32);
        y = x <<16; if (y != 0) { n = n -16; x = y; }
        y = x << 8; if (y != 0) { n = n - 8; x = y; }
        y = x << 4; if (y != 0) { n = n - 4; x = y; }
        y = x << 2; if (y != 0) { n = n - 2; x = y; }
        return n - ((x << 1) >>> 31);
    }

    /**
     * Returns the number of one-bits in the two's complement binary
     * representation of the specified {@code long} value.  This function is
     * sometimes referred to as the <i>population count</i>.
     *
     * <p>
     *  返回指定{@code long}值的二进制补码二进制表示中的一位数。此函数有时称为<i>种群计数</i>。
     * 
     * 
     * @param i the value whose bits are to be counted
     * @return the number of one-bits in the two's complement binary
     *     representation of the specified {@code long} value.
     * @since 1.5
     */
     public static int bitCount(long i) {
        // HD, Figure 5-14
        i = i - ((i >>> 1) & 0x5555555555555555L);
        i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
        i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        i = i + (i >>> 32);
        return (int)i & 0x7f;
     }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code long} value left by the
     * specified number of bits.  (Bits shifted out of the left hand, or
     * high-order, side reenter on the right, or low-order.)
     *
     * <p>Note that left rotation with a negative distance is equivalent to
     * right rotation: {@code rotateLeft(val, -distance) == rotateRight(val,
     * distance)}.  Note also that rotation by any multiple of 64 is a
     * no-op, so all but the last six bits of the rotation distance can be
     * ignored, even if the distance is negative: {@code rotateLeft(val,
     * distance) == rotateLeft(val, distance & 0x3F)}.
     *
     * <p>
     *  返回通过将指定的{@code long}值的二进制补码二进制表示旋转左指定位数所获得的值。 (从左手移出的位,或高阶,右边的重新输入或低阶)。
     * 
     *  <p>请注意,具有负距离的左旋转等效于右旋转：{@code rotateLeft(val,-distance)== rotateRight(val,distance)}。
     * 还要注意,64的任何倍数的旋转是无操作的,所以除了旋转距离的最后六位可以被忽略,即使距离是负的：{@code rotateLeft(val,distance)== rotateLeft val,distance&0x3F)}
     * 。
     *  <p>请注意,具有负距离的左旋转等效于右旋转：{@code rotateLeft(val,-distance)== rotateRight(val,distance)}。
     * 
     * 
     * @param i the value whose bits are to be rotated left
     * @param distance the number of bit positions to rotate left
     * @return the value obtained by rotating the two's complement binary
     *     representation of the specified {@code long} value left by the
     *     specified number of bits.
     * @since 1.5
     */
    public static long rotateLeft(long i, int distance) {
        return (i << distance) | (i >>> -distance);
    }

    /**
     * Returns the value obtained by rotating the two's complement binary
     * representation of the specified {@code long} value right by the
     * specified number of bits.  (Bits shifted out of the right hand, or
     * low-order, side reenter on the left, or high-order.)
     *
     * <p>Note that right rotation with a negative distance is equivalent to
     * left rotation: {@code rotateRight(val, -distance) == rotateLeft(val,
     * distance)}.  Note also that rotation by any multiple of 64 is a
     * no-op, so all but the last six bits of the rotation distance can be
     * ignored, even if the distance is negative: {@code rotateRight(val,
     * distance) == rotateRight(val, distance & 0x3F)}.
     *
     * <p>
     *  返回通过将指定的{@code long}值的二进制补码二进制表示右移指定位数所获得的值。 (从右手移出的位或低位,在左边重新输入或高位。
     * 
     * <p>请注意,具有负距离的右旋转等效于左旋转：{@code rotateRight(val,-distance)== rotateLeft(val,distance)}。
     * 还要注意,64的任何倍数的旋转是无操作的,所以除了旋转距离的最后六位可以忽略,即使距离是负的：{@code rotateRight(val,distance)== rotateRight val,distance&0x3F)}
     * 。
     * <p>请注意,具有负距离的右旋转等效于左旋转：{@code rotateRight(val,-distance)== rotateLeft(val,distance)}。
     * 
     * 
     * @param i the value whose bits are to be rotated right
     * @param distance the number of bit positions to rotate right
     * @return the value obtained by rotating the two's complement binary
     *     representation of the specified {@code long} value right by the
     *     specified number of bits.
     * @since 1.5
     */
    public static long rotateRight(long i, int distance) {
        return (i >>> distance) | (i << -distance);
    }

    /**
     * Returns the value obtained by reversing the order of the bits in the
     * two's complement binary representation of the specified {@code long}
     * value.
     *
     * <p>
     *  返回通过反转指定{@code long}值的二进制补码二进制表示中的位的顺序获得的值。
     * 
     * 
     * @param i the value to be reversed
     * @return the value obtained by reversing order of the bits in the
     *     specified {@code long} value.
     * @since 1.5
     */
    public static long reverse(long i) {
        // HD, Figure 7-1
        i = (i & 0x5555555555555555L) << 1 | (i >>> 1) & 0x5555555555555555L;
        i = (i & 0x3333333333333333L) << 2 | (i >>> 2) & 0x3333333333333333L;
        i = (i & 0x0f0f0f0f0f0f0f0fL) << 4 | (i >>> 4) & 0x0f0f0f0f0f0f0f0fL;
        i = (i & 0x00ff00ff00ff00ffL) << 8 | (i >>> 8) & 0x00ff00ff00ff00ffL;
        i = (i << 48) | ((i & 0xffff0000L) << 16) |
            ((i >>> 16) & 0xffff0000L) | (i >>> 48);
        return i;
    }

    /**
     * Returns the signum function of the specified {@code long} value.  (The
     * return value is -1 if the specified value is negative; 0 if the
     * specified value is zero; and 1 if the specified value is positive.)
     *
     * <p>
     *  返回指定的{@code long}值的signum函数。 (如果指定值为负,则返回值为-1;如果指定值为零,则返回值为0;如果指定值为正,返回值为1)。
     * 
     * 
     * @param i the value whose signum is to be computed
     * @return the signum function of the specified {@code long} value.
     * @since 1.5
     */
    public static int signum(long i) {
        // HD, Section 2-7
        return (int) ((i >> 63) | (-i >>> 63));
    }

    /**
     * Returns the value obtained by reversing the order of the bytes in the
     * two's complement representation of the specified {@code long} value.
     *
     * <p>
     *  返回通过反转指定{@code long}值的二进制补码表示中的字节顺序获得的值。
     * 
     * 
     * @param i the value whose bytes are to be reversed
     * @return the value obtained by reversing the bytes in the specified
     *     {@code long} value.
     * @since 1.5
     */
    public static long reverseBytes(long i) {
        i = (i & 0x00ff00ff00ff00ffL) << 8 | (i >>> 8) & 0x00ff00ff00ff00ffL;
        return (i << 48) | ((i & 0xffff0000L) << 16) |
            ((i >>> 16) & 0xffff0000L) | (i >>> 48);
    }

    /**
     * Adds two {@code long} values together as per the + operator.
     *
     * <p>
     *  根据+运算符将两个{@code long}值添加在一起。
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the sum of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long sum(long a, long b) {
        return a + b;
    }

    /**
     * Returns the greater of two {@code long} values
     * as if by calling {@link Math#max(long, long) Math.max}.
     *
     * <p>
     *  返回两个{@code long}值中的较大值,就像调用{@link Math#max(long,long)Math.max}一样。
     * 
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the greater of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code long} values
     * as if by calling {@link Math#min(long, long) Math.min}.
     *
     * <p>
     *  返回两个{@code long}值中较小的值,如同调用{@link Math#min(long,long)Math.min}。
     * 
     * @param a the first operand
     * @param b the second operand
     * @return the smaller of {@code a} and {@code b}
     * @see java.util.function.BinaryOperator
     * @since 1.8
     */
    public static long min(long a, long b) {
        return Math.min(a, b);
    }

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    @Native private static final long serialVersionUID = 4290774380558885855L;
}
