/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import sun.misc.FloatingDecimal;

/**
 * Digit List. Private to DecimalFormat.
 * Handles the transcoding
 * between numeric values and strings of characters.  Only handles
 * non-negative numbers.  The division of labor between DigitList and
 * DecimalFormat is that DigitList handles the radix 10 representation
 * issues; DecimalFormat handles the locale-specific issues such as
 * positive/negative, grouping, decimal point, currency, and so on.
 *
 * A DigitList is really a representation of a floating point value.
 * It may be an integer value; we assume that a double has sufficient
 * precision to represent all digits of a long.
 *
 * The DigitList representation consists of a string of characters,
 * which are the digits radix 10, from '0' to '9'.  It also has a radix
 * 10 exponent associated with it.  The value represented by a DigitList
 * object can be computed by mulitplying the fraction f, where 0 <= f < 1,
 * derived by placing all the digits of the list to the right of the
 * decimal point, by 10^exponent.
 *
 * <p>
 *  数字列表。专用于DecimalFormat。处理数字值和字符串之间的转码。只处理非负数。
 *  DigitList和DecimalFormat之间的分工是DigitList处理基数10表示问题; DecimalFormat处理特定于语言环境的问题,例如正/负,分组,小数点,货币等。
 * 
 *  DigitList实际上是一个浮点值的表示。它可以是整数值;我们假设double具有足够的精度来表示long的所有数字。
 * 
 * DigitList表示由一个字符串组成,它是从"0"到"9"的十进制数字。它还有一个与其相关的基数10指数。
 * 由DigitList对象表示的值可以通过乘以分数f来计算,其中0 <= f <1,通过将列表的所有数字放置在小数点右侧,乘以10 ^指数来导出。
 * 
 * 
 * @see  Locale
 * @see  Format
 * @see  NumberFormat
 * @see  DecimalFormat
 * @see  ChoiceFormat
 * @see  MessageFormat
 * @author       Mark Davis, Alan Liu
 */
final class DigitList implements Cloneable {
    /**
     * The maximum number of significant digits in an IEEE 754 double, that
     * is, in a Java double.  This must not be increased, or garbage digits
     * will be generated, and should not be decreased, or accuracy will be lost.
     * <p>
     *  IEEE 754中的最大有效位数为double,即在Java中为double。这不能增加,或者将生成无用数字,并且不应该减少,否则精度将丢失。
     * 
     */
    public static final int MAX_COUNT = 19; // == Long.toString(Long.MAX_VALUE).length()

    /**
     * These data members are intentionally public and can be set directly.
     *
     * The value represented is given by placing the decimal point before
     * digits[decimalAt].  If decimalAt is < 0, then leading zeros between
     * the decimal point and the first nonzero digit are implied.  If decimalAt
     * is > count, then trailing zeros between the digits[count-1] and the
     * decimal point are implied.
     *
     * Equivalently, the represented value is given by f * 10^decimalAt.  Here
     * f is a value 0.1 <= f < 1 arrived at by placing the digits in Digits to
     * the right of the decimal.
     *
     * DigitList is normalized, so if it is non-zero, figits[0] is non-zero.  We
     * don't allow denormalized numbers because our exponent is effectively of
     * unlimited magnitude.  The count value contains the number of significant
     * digits present in digits[].
     *
     * Zero is represented by any DigitList with count == 0 or with each digits[i]
     * for all i <= count == '0'.
     * <p>
     *  这些数据成员是故意公开的,可以直接设置。
     * 
     *  表示的值通过将小数点放在数字[decimalAt]之前给出。如果decimalAt <0,则暗示小数点和第一个非零数字之间的前导零。
     * 如果decimalAt> count,则隐含数字[count-1]和小数点之间的尾随零。
     * 
     *  等价地,表示的值由f * 10 ^十进制给出。这里f是0.1 <= f <1的值,通过将数字放在小数右边的数字中。
     * 
     *  DigitList被归一化,因此如果它是非零,figits [0]是非零。我们不允许非规范化的数字,因为我们的指数实际上是无限大的。计数值包含数字[]中存在的有效数字位数。
     * 
     *  零由任何具有count == 0的数字列表表示,或者对于所有i <= count =='0'的每个数字[i]表示。
     * 
     */
    public int decimalAt = 0;
    public int count = 0;
    public char[] digits = new char[MAX_COUNT];

    private char[] data;
    private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
    private boolean isNegative = false;

    /**
     * Return true if the represented number is zero.
     * <p>
     * 如果所表示的数字为零,则返回true。
     * 
     */
    boolean isZero() {
        for (int i=0; i < count; ++i) {
            if (digits[i] != '0') {
                return false;
            }
        }
        return true;
    }

    /**
     * Set the rounding mode
     * <p>
     *  设置舍入模式
     * 
     */
    void setRoundingMode(RoundingMode r) {
        roundingMode = r;
    }

    /**
     * Clears out the digits.
     * Use before appending them.
     * Typically, you set a series of digits with append, then at the point
     * you hit the decimal point, you set myDigitList.decimalAt = myDigitList.count;
     * then go on appending digits.
     * <p>
     *  清除数字。在添加它们之前使用。
     * 通常,你用append设置一系列数字,然后在你点击小数点的那一点,你设置myDigitList.decimalAt = myDigitList.count;然后继续追加数字。
     * 
     */
    public void clear () {
        decimalAt = 0;
        count = 0;
    }

    /**
     * Appends a digit to the list, extending the list when necessary.
     * <p>
     *  将数字附加到列表,必要时扩展列表。
     * 
     */
    public void append(char digit) {
        if (count == digits.length) {
            char[] data = new char[count + 100];
            System.arraycopy(digits, 0, data, 0, count);
            digits = data;
        }
        digits[count++] = digit;
    }

    /**
     * Utility routine to get the value of the digit list
     * If (count == 0) this throws a NumberFormatException, which
     * mimics Long.parseLong().
     * <p>
     *  获得数字列表的值的实用程序如果(count == 0)这将抛出一个NumberFormatException,它模仿Long.parseLong()。
     * 
     */
    public final double getDouble() {
        if (count == 0) {
            return 0.0;
        }

        StringBuffer temp = getStringBuffer();
        temp.append('.');
        temp.append(digits, 0, count);
        temp.append('E');
        temp.append(decimalAt);
        return Double.parseDouble(temp.toString());
    }

    /**
     * Utility routine to get the value of the digit list.
     * If (count == 0) this returns 0, unlike Long.parseLong().
     * <p>
     *  获取数字列表值的实用程序。如果(count == 0)这返回0,不同于Long.parseLong()。
     * 
     */
    public final long getLong() {
        // for now, simple implementation; later, do proper IEEE native stuff

        if (count == 0) {
            return 0;
        }

        // We have to check for this, because this is the one NEGATIVE value
        // we represent.  If we tried to just pass the digits off to parseLong,
        // we'd get a parse failure.
        if (isLongMIN_VALUE()) {
            return Long.MIN_VALUE;
        }

        StringBuffer temp = getStringBuffer();
        temp.append(digits, 0, count);
        for (int i = count; i < decimalAt; ++i) {
            temp.append('0');
        }
        return Long.parseLong(temp.toString());
    }

    public final BigDecimal getBigDecimal() {
        if (count == 0) {
            if (decimalAt == 0) {
                return BigDecimal.ZERO;
            } else {
                return new BigDecimal("0E" + decimalAt);
            }
        }

       if (decimalAt == count) {
           return new BigDecimal(digits, 0, count);
       } else {
           return new BigDecimal(digits, 0, count).scaleByPowerOfTen(decimalAt - count);
       }
    }

    /**
     * Return true if the number represented by this object can fit into
     * a long.
     * <p>
     *  如果此对象表示的数字适合长整型,则返回true。
     * 
     * 
     * @param isPositive true if this number should be regarded as positive
     * @param ignoreNegativeZero true if -0 should be regarded as identical to
     * +0; otherwise they are considered distinct
     * @return true if this number fits into a Java long
     */
    boolean fitsIntoLong(boolean isPositive, boolean ignoreNegativeZero) {
        // Figure out if the result will fit in a long.  We have to
        // first look for nonzero digits after the decimal point;
        // then check the size.  If the digit count is 18 or less, then
        // the value can definitely be represented as a long.  If it is 19
        // then it may be too large.

        // Trim trailing zeros.  This does not change the represented value.
        while (count > 0 && digits[count - 1] == '0') {
            --count;
        }

        if (count == 0) {
            // Positive zero fits into a long, but negative zero can only
            // be represented as a double. - bug 4162852
            return isPositive || ignoreNegativeZero;
        }

        if (decimalAt < count || decimalAt > MAX_COUNT) {
            return false;
        }

        if (decimalAt < MAX_COUNT) return true;

        // At this point we have decimalAt == count, and count == MAX_COUNT.
        // The number will overflow if it is larger than 9223372036854775807
        // or smaller than -9223372036854775808.
        for (int i=0; i<count; ++i) {
            char dig = digits[i], max = LONG_MIN_REP[i];
            if (dig > max) return false;
            if (dig < max) return true;
        }

        // At this point the first count digits match.  If decimalAt is less
        // than count, then the remaining digits are zero, and we return true.
        if (count < decimalAt) return true;

        // Now we have a representation of Long.MIN_VALUE, without the leading
        // negative sign.  If this represents a positive value, then it does
        // not fit; otherwise it fits.
        return !isPositive;
    }

    /**
     * Set the digit list to a representation of the given double value.
     * This method supports fixed-point notation.
     * <p>
     *  将数字列表设置为给定double值的表示。此方法支持定点符号。
     * 
     * 
     * @param isNegative Boolean value indicating whether the number is negative.
     * @param source Value to be converted; must not be Inf, -Inf, Nan,
     * or a value <= 0.
     * @param maximumFractionDigits The most fractional digits which should
     * be converted.
     */
    final void set(boolean isNegative, double source, int maximumFractionDigits) {
        set(isNegative, source, maximumFractionDigits, true);
    }

    /**
     * Set the digit list to a representation of the given double value.
     * This method supports both fixed-point and exponential notation.
     * <p>
     *  将数字列表设置为给定double值的表示。此方法支持定点和指数符号。
     * 
     * 
     * @param isNegative Boolean value indicating whether the number is negative.
     * @param source Value to be converted; must not be Inf, -Inf, Nan,
     * or a value <= 0.
     * @param maximumDigits The most fractional or total digits which should
     * be converted.
     * @param fixedPoint If true, then maximumDigits is the maximum
     * fractional digits to be converted.  If false, total digits.
     */
    final void set(boolean isNegative, double source, int maximumDigits, boolean fixedPoint) {

        FloatingDecimal.BinaryToASCIIConverter fdConverter  = FloatingDecimal.getBinaryToASCIIConverter(source);
        boolean hasBeenRoundedUp = fdConverter.digitsRoundedUp();
        boolean valueExactAsDecimal = fdConverter.decimalDigitsExact();
        assert !fdConverter.isExceptional();
        String digitsString = fdConverter.toJavaFormatString();

        set(isNegative, digitsString,
            hasBeenRoundedUp, valueExactAsDecimal,
            maximumDigits, fixedPoint);
    }

    /**
     * Generate a representation of the form DDDDD, DDDDD.DDDDD, or
     * DDDDDE+/-DDDDD.
     * <p>
     *  生成表单DDDDD,DDDDD.DDDDD或DDDDDE +/- DDDDD。
     * 
     * 
     * @param roundedUp whether or not rounding up has already happened.
     * @param valueExactAsDecimal whether or not collected digits provide
     * an exact decimal representation of the value.
     */
    private void set(boolean isNegative, String s,
                     boolean roundedUp, boolean valueExactAsDecimal,
                     int maximumDigits, boolean fixedPoint) {

        this.isNegative = isNegative;
        int len = s.length();
        char[] source = getDataChars(len);
        s.getChars(0, len, source, 0);

        decimalAt = -1;
        count = 0;
        int exponent = 0;
        // Number of zeros between decimal point and first non-zero digit after
        // decimal point, for numbers < 1.
        int leadingZerosAfterDecimal = 0;
        boolean nonZeroDigitSeen = false;

        for (int i = 0; i < len; ) {
            char c = source[i++];
            if (c == '.') {
                decimalAt = count;
            } else if (c == 'e' || c == 'E') {
                exponent = parseInt(source, i, len);
                break;
            } else {
                if (!nonZeroDigitSeen) {
                    nonZeroDigitSeen = (c != '0');
                    if (!nonZeroDigitSeen && decimalAt != -1)
                        ++leadingZerosAfterDecimal;
                }
                if (nonZeroDigitSeen) {
                    digits[count++] = c;
                }
            }
        }
        if (decimalAt == -1) {
            decimalAt = count;
        }
        if (nonZeroDigitSeen) {
            decimalAt += exponent - leadingZerosAfterDecimal;
        }

        if (fixedPoint) {
            // The negative of the exponent represents the number of leading
            // zeros between the decimal and the first non-zero digit, for
            // a value < 0.1 (e.g., for 0.00123, -decimalAt == 2).  If this
            // is more than the maximum fraction digits, then we have an underflow
            // for the printed representation.
            if (-decimalAt > maximumDigits) {
                // Handle an underflow to zero when we round something like
                // 0.0009 to 2 fractional digits.
                count = 0;
                return;
            } else if (-decimalAt == maximumDigits) {
                // If we round 0.0009 to 3 fractional digits, then we have to
                // create a new one digit in the least significant location.
                if (shouldRoundUp(0, roundedUp, valueExactAsDecimal)) {
                    count = 1;
                    ++decimalAt;
                    digits[0] = '1';
                } else {
                    count = 0;
                }
                return;
            }
            // else fall through
        }

        // Eliminate trailing zeros.
        while (count > 1 && digits[count - 1] == '0') {
            --count;
        }

        // Eliminate digits beyond maximum digits to be displayed.
        // Round up if appropriate.
        round(fixedPoint ? (maximumDigits + decimalAt) : maximumDigits,
              roundedUp, valueExactAsDecimal);

     }

    /**
     * Round the representation to the given number of digits.
     * <p>
     *  将表示法四舍五入到给定数字位数。
     * 
     * 
     * @param maximumDigits The maximum number of digits to be shown.
     * @param alreadyRounded whether or not rounding up has already happened.
     * @param valueExactAsDecimal whether or not collected digits provide
     * an exact decimal representation of the value.
     *
     * Upon return, count will be less than or equal to maximumDigits.
     */
    private final void round(int maximumDigits,
                             boolean alreadyRounded,
                             boolean valueExactAsDecimal) {
        // Eliminate digits beyond maximum digits to be displayed.
        // Round up if appropriate.
        if (maximumDigits >= 0 && maximumDigits < count) {
            if (shouldRoundUp(maximumDigits, alreadyRounded, valueExactAsDecimal)) {
                // Rounding up involved incrementing digits from LSD to MSD.
                // In most cases this is simple, but in a worst case situation
                // (9999..99) we have to adjust the decimalAt value.
                for (;;) {
                    --maximumDigits;
                    if (maximumDigits < 0) {
                        // We have all 9's, so we increment to a single digit
                        // of one and adjust the exponent.
                        digits[0] = '1';
                        ++decimalAt;
                        maximumDigits = 0; // Adjust the count
                        break;
                    }

                    ++digits[maximumDigits];
                    if (digits[maximumDigits] <= '9') break;
                    // digits[maximumDigits] = '0'; // Unnecessary since we'll truncate this
                }
                ++maximumDigits; // Increment for use as count
            }
            count = maximumDigits;

            // Eliminate trailing zeros.
            while (count > 1 && digits[count-1] == '0') {
                --count;
            }
        }
    }


    /**
     * Return true if truncating the representation to the given number
     * of digits will result in an increment to the last digit.  This
     * method implements the rounding modes defined in the
     * java.math.RoundingMode class.
     * [bnf]
     * <p>
     *  如果将表达式截断到给定数字位数,则返回true,将导致最后一位数字递增。此方法实现java.math.RoundingMode类中定义的舍入模式。 [bnf]
     * 
     * 
     * @param maximumDigits the number of digits to keep, from 0 to
     * <code>count-1</code>.  If 0, then all digits are rounded away, and
     * this method returns true if a one should be generated (e.g., formatting
     * 0.09 with "#.#").
     * @param alreadyRounded whether or not rounding up has already happened.
     * @param valueExactAsDecimal whether or not collected digits provide
     * an exact decimal representation of the value.
     * @exception ArithmeticException if rounding is needed with rounding
     *            mode being set to RoundingMode.UNNECESSARY
     * @return true if digit <code>maximumDigits-1</code> should be
     * incremented
     */
    private boolean shouldRoundUp(int maximumDigits,
                                  boolean alreadyRounded,
                                  boolean valueExactAsDecimal) {
        if (maximumDigits < count) {
            /*
             * To avoid erroneous double-rounding or truncation when converting
             * a binary double value to text, information about the exactness
             * of the conversion result in FloatingDecimal, as well as any
             * rounding done, is needed in this class.
             *
             * - For the  HALF_DOWN, HALF_EVEN, HALF_UP rounding rules below:
             *   In the case of formating float or double, We must take into
             *   account what FloatingDecimal has done in the binary to decimal
             *   conversion.
             *
             *   Considering the tie cases, FloatingDecimal may round up the
             *   value (returning decimal digits equal to tie when it is below),
             *   or "truncate" the value to the tie while value is above it,
             *   or provide the exact decimal digits when the binary value can be
             *   converted exactly to its decimal representation given formating
             *   rules of FloatingDecimal ( we have thus an exact decimal
             *   representation of the binary value).
             *
             *   - If the double binary value was converted exactly as a decimal
             *     value, then DigitList code must apply the expected rounding
             *     rule.
             *
             *   - If FloatingDecimal already rounded up the decimal value,
             *     DigitList should neither round up the value again in any of
             *     the three rounding modes above.
             *
             *   - If FloatingDecimal has truncated the decimal value to
             *     an ending '5' digit, DigitList should round up the value in
             *     all of the three rounding modes above.
             *
             *
             *   This has to be considered only if digit at maximumDigits index
             *   is exactly the last one in the set of digits, otherwise there are
             *   remaining digits after that position and we don't have to consider
             *   what FloatingDecimal did.
             *
             * - Other rounding modes are not impacted by these tie cases.
             *
             * - For other numbers that are always converted to exact digits
             *   (like BigInteger, Long, ...), the passed alreadyRounded boolean
             *   have to be  set to false, and valueExactAsDecimal has to be set to
             *   true in the upper DigitList call stack, providing the right state
             *   for those situations..
             * <p>
             * 为了避免在将二进制双精度值转换为文本时出现错误的双舍入或截断,在此类中需要有关FloatingDecimal中的转换结果的精确性以及任何舍入操作的信息。
             * 
             *   - 对于HALF_DOWN,HALF_EVEN,HALF_UP舍入规则如下：在形成float或double的情况下,我们必须考虑FloatingDecimal在二进制到十进制转换中做了什么。
             * 
             *  考虑到关系的情况,FloatingDecimal可以向上舍入该值(当它低于时返回十进制数等于tie),或者当值高于该值时,"截断"该值,或者当二进制值可以时,提供确切的十进制数字被精确地转换为其十进制
             * 表示给定的浮点十进制的格式规则(因此我们有二进制值的精确的十进制表示)。
             * 
             *   - 如果双二进制值完全转换为十进制值,则DigitList代码必须应用预期的舍入规则。
             * 
             *   - 如果FloatingDecimal已经向上舍入十进制值,则DigitList不应该在上述三个舍入模式中的任一个中再次向上舍入该值。
             * 
             *   - 如果FloatingDecimal已将十进制值截断为结束"5"数字,则DigitList应将上述三个舍入模式中的值向上舍入。
             * 
             *  这是必须考虑只有当数字在maximumDigits索引正好是数字集中的最后一个,否则在该位置后有剩余的数字,我们不必考虑什么FloatingDecimal做的。
             * 
             *  - 其他舍入模式不受这些连结案例的影响。
             * 
             *   - 对于总是转换为精确数字(如BigInteger,Long,...)的其他数字,传递的alreadyRounded布尔值必须设置为false,并且valueExactAsDecimal必须在高位D
             * igitList调用堆栈中设置为true,正确的状态。
             * 
             */

            switch(roundingMode) {
            case UP:
                for (int i=maximumDigits; i<count; ++i) {
                    if (digits[i] != '0') {
                        return true;
                    }
                }
                break;
            case DOWN:
                break;
            case CEILING:
                for (int i=maximumDigits; i<count; ++i) {
                    if (digits[i] != '0') {
                        return !isNegative;
                    }
                }
                break;
            case FLOOR:
                for (int i=maximumDigits; i<count; ++i) {
                    if (digits[i] != '0') {
                        return isNegative;
                    }
                }
                break;
            case HALF_UP:
            case HALF_DOWN:
                if (digits[maximumDigits] > '5') {
                    // Value is above tie ==> must round up
                    return true;
                } else if (digits[maximumDigits] == '5') {
                    // Digit at rounding position is a '5'. Tie cases.
                    if (maximumDigits != (count - 1)) {
                        // There are remaining digits. Above tie => must round up
                        return true;
                    } else {
                        // Digit at rounding position is the last one !
                        if (valueExactAsDecimal) {
                            // Exact binary representation. On the tie.
                            // Apply rounding given by roundingMode.
                            return roundingMode == RoundingMode.HALF_UP;
                        } else {
                            // Not an exact binary representation.
                            // Digit sequence either rounded up or truncated.
                            // Round up only if it was truncated.
                            return !alreadyRounded;
                        }
                    }
                }
                // Digit at rounding position is < '5' ==> no round up.
                // Just let do the default, which is no round up (thus break).
                break;
            case HALF_EVEN:
                // Implement IEEE half-even rounding
                if (digits[maximumDigits] > '5') {
                    return true;
                } else if (digits[maximumDigits] == '5' ) {
                    if (maximumDigits == (count - 1)) {
                        // the rounding position is exactly the last index :
                        if (alreadyRounded)
                            // If FloatingDecimal rounded up (value was below tie),
                            // then we should not round up again.
                            return false;

                        if (!valueExactAsDecimal)
                            // Otherwise if the digits don't represent exact value,
                            // value was above tie and FloatingDecimal truncated
                            // digits to tie. We must round up.
                            return true;
                        else {
                            // This is an exact tie value, and FloatingDecimal
                            // provided all of the exact digits. We thus apply
                            // HALF_EVEN rounding rule.
                            return ((maximumDigits > 0) &&
                                    (digits[maximumDigits-1] % 2 != 0));
                        }
                    } else {
                        // Rounds up if it gives a non null digit after '5'
                        for (int i=maximumDigits+1; i<count; ++i) {
                            if (digits[i] != '0')
                                return true;
                        }
                    }
                }
                break;
            case UNNECESSARY:
                for (int i=maximumDigits; i<count; ++i) {
                    if (digits[i] != '0') {
                        throw new ArithmeticException(
                            "Rounding needed with the rounding mode being set to RoundingMode.UNNECESSARY");
                    }
                }
                break;
            default:
                assert false;
            }
        }
        return false;
    }

    /**
     * Utility routine to set the value of the digit list from a long
     * <p>
     *  实用程序设置数字列表的值从长
     * 
     */
    final void set(boolean isNegative, long source) {
        set(isNegative, source, 0);
    }

    /**
     * Set the digit list to a representation of the given long value.
     * <p>
     *  将数字列表设置为给定长值的表示形式。
     * 
     * 
     * @param isNegative Boolean value indicating whether the number is negative.
     * @param source Value to be converted; must be >= 0 or ==
     * Long.MIN_VALUE.
     * @param maximumDigits The most digits which should be converted.
     * If maximumDigits is lower than the number of significant digits
     * in source, the representation will be rounded.  Ignored if <= 0.
     */
    final void set(boolean isNegative, long source, int maximumDigits) {
        this.isNegative = isNegative;

        // This method does not expect a negative number. However,
        // "source" can be a Long.MIN_VALUE (-9223372036854775808),
        // if the number being formatted is a Long.MIN_VALUE.  In that
        // case, it will be formatted as -Long.MIN_VALUE, a number
        // which is outside the legal range of a long, but which can
        // be represented by DigitList.
        if (source <= 0) {
            if (source == Long.MIN_VALUE) {
                decimalAt = count = MAX_COUNT;
                System.arraycopy(LONG_MIN_REP, 0, digits, 0, count);
            } else {
                decimalAt = count = 0; // Values <= 0 format as zero
            }
        } else {
            // Rewritten to improve performance.  I used to call
            // Long.toString(), which was about 4x slower than this code.
            int left = MAX_COUNT;
            int right;
            while (source > 0) {
                digits[--left] = (char)('0' + (source % 10));
                source /= 10;
            }
            decimalAt = MAX_COUNT - left;
            // Don't copy trailing zeros.  We are guaranteed that there is at
            // least one non-zero digit, so we don't have to check lower bounds.
            for (right = MAX_COUNT - 1; digits[right] == '0'; --right)
                ;
            count = right - left + 1;
            System.arraycopy(digits, left, digits, 0, count);
        }
        if (maximumDigits > 0) round(maximumDigits, false, true);
    }

    /**
     * Set the digit list to a representation of the given BigDecimal value.
     * This method supports both fixed-point and exponential notation.
     * <p>
     *  将数字列表设置为给定的BigDecimal值的表示。此方法支持定点和指数符号。
     * 
     * 
     * @param isNegative Boolean value indicating whether the number is negative.
     * @param source Value to be converted; must not be a value <= 0.
     * @param maximumDigits The most fractional or total digits which should
     * be converted.
     * @param fixedPoint If true, then maximumDigits is the maximum
     * fractional digits to be converted.  If false, total digits.
     */
    final void set(boolean isNegative, BigDecimal source, int maximumDigits, boolean fixedPoint) {
        String s = source.toString();
        extendDigits(s.length());

        set(isNegative, s,
            false, true,
            maximumDigits, fixedPoint);
    }

    /**
     * Set the digit list to a representation of the given BigInteger value.
     * <p>
     *  将数字列表设置为给定BigInteger值的表示形式。
     * 
     * 
     * @param isNegative Boolean value indicating whether the number is negative.
     * @param source Value to be converted; must be >= 0.
     * @param maximumDigits The most digits which should be converted.
     * If maximumDigits is lower than the number of significant digits
     * in source, the representation will be rounded.  Ignored if <= 0.
     */
    final void set(boolean isNegative, BigInteger source, int maximumDigits) {
        this.isNegative = isNegative;
        String s = source.toString();
        int len = s.length();
        extendDigits(len);
        s.getChars(0, len, digits, 0);

        decimalAt = len;
        int right;
        for (right = len - 1; right >= 0 && digits[right] == '0'; --right)
            ;
        count = right + 1;

        if (maximumDigits > 0) {
            round(maximumDigits, false, true);
        }
    }

    /**
     * equality test between two digit lists.
     * <p>
     *  两个数字列表之间的相等测试。
     * 
     */
    public boolean equals(Object obj) {
        if (this == obj)                      // quick check
            return true;
        if (!(obj instanceof DigitList))         // (1) same object?
            return false;
        DigitList other = (DigitList) obj;
        if (count != other.count ||
        decimalAt != other.decimalAt)
            return false;
        for (int i = 0; i < count; i++)
            if (digits[i] != other.digits[i])
                return false;
        return true;
    }

    /**
     * Generates the hash code for the digit list.
     * <p>
     *  生成数字列表的哈希码。
     * 
     */
    public int hashCode() {
        int hashcode = decimalAt;

        for (int i = 0; i < count; i++) {
            hashcode = hashcode * 37 + digits[i];
        }

        return hashcode;
    }

    /**
     * Creates a copy of this object.
     * <p>
     *  创建此对象的副本。
     * 
     * 
     * @return a clone of this instance.
     */
    public Object clone() {
        try {
            DigitList other = (DigitList) super.clone();
            char[] newDigits = new char[digits.length];
            System.arraycopy(digits, 0, newDigits, 0, digits.length);
            other.digits = newDigits;
            other.tempBuffer = null;
            return other;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Returns true if this DigitList represents Long.MIN_VALUE;
     * false, otherwise.  This is required so that getLong() works.
     * <p>
     *  如果此DigitList表示Long.MIN_VALUE,则返回true; false,否则。这是必需的,以便getLong()工作。
     */
    private boolean isLongMIN_VALUE() {
        if (decimalAt != count || count != MAX_COUNT) {
            return false;
        }

        for (int i = 0; i < count; ++i) {
            if (digits[i] != LONG_MIN_REP[i]) return false;
        }

        return true;
    }

    private static final int parseInt(char[] str, int offset, int strLen) {
        char c;
        boolean positive = true;
        if ((c = str[offset]) == '-') {
            positive = false;
            offset++;
        } else if (c == '+') {
            offset++;
        }

        int value = 0;
        while (offset < strLen) {
            c = str[offset++];
            if (c >= '0' && c <= '9') {
                value = value * 10 + (c - '0');
            } else {
                break;
            }
        }
        return positive ? value : -value;
    }

    // The digit part of -9223372036854775808L
    private static final char[] LONG_MIN_REP = "9223372036854775808".toCharArray();

    public String toString() {
        if (isZero()) {
            return "0";
        }
        StringBuffer buf = getStringBuffer();
        buf.append("0.");
        buf.append(digits, 0, count);
        buf.append("x10^");
        buf.append(decimalAt);
        return buf.toString();
    }

    private StringBuffer tempBuffer;

    private StringBuffer getStringBuffer() {
        if (tempBuffer == null) {
            tempBuffer = new StringBuffer(MAX_COUNT);
        } else {
            tempBuffer.setLength(0);
        }
        return tempBuffer;
    }

    private void extendDigits(int len) {
        if (len > digits.length) {
            digits = new char[len];
        }
    }

    private final char[] getDataChars(int length) {
        if (data == null || data.length < length) {
            data = new char[length];
        }
        return data;
    }
}
