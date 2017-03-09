/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
 *
 *
 *
 *
 *
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 *  版权所有(c)2008-2012,Stephen Colebourne和Michael Nascimento Santos
 * 
 *  版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  *源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  *二进制形式的再分发必须在随发行提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  *未经特定事先书面许可,JSR-310的名称及其贡献者的名称不得用于支持或推广衍生自此软件的产品。
 * 
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,版权所有者或贡献者对任何直接,间接,偶发,特殊,惩戒性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据或利润损失,或业务中断),无论是由于任何责任推定,无论是在合同,严格责任,或
 * 侵权(包括疏忽或其他)任何方式使用本软件,即使已被告知此类损害的可能性。
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 
 */
package java.time.format;

import java.util.Calendar;

/**
 * Enumeration of the style of text formatting and parsing.
 * <p>
 * Text styles define three sizes for the formatted text - 'full', 'short' and 'narrow'.
 * Each of these three sizes is available in both 'standard' and 'stand-alone' variations.
 * <p>
 * The difference between the three sizes is obvious in most languages.
 * For example, in English the 'full' month is 'January', the 'short' month is 'Jan'
 * and the 'narrow' month is 'J'. Note that the narrow size is often not unique.
 * For example, 'January', 'June' and 'July' all have the 'narrow' text 'J'.
 * <p>
 * The difference between the 'standard' and 'stand-alone' forms is trickier to describe
 * as there is no difference in English. However, in other languages there is a difference
 * in the word used when the text is used alone, as opposed to in a complete date.
 * For example, the word used for a month when used alone in a date picker is different
 * to the word used for month in association with a day and year in a date.
 *
 * @implSpec
 * This is immutable and thread-safe enum.
 * <p>
 *  枚举文本格式和解析的样式。
 * <p>
 *  文本样式为格式化的文本定义了三种尺寸："full","short"和"narrow"。这三种尺寸中的每一种都可用于"标准"和"独立"变体。
 * <p>
 *  在大多数语言中,三种大小之间的差异是显而易见的。例如,在英语中,'full'是'January','short'是'Jan','narrow'是'J'。注意,窄尺寸通常不是唯一的。
 * 例如,'一月','六月'和'七月'都有'窄'文本'J'。
 * <p>
 * "标准"和"独立"形式之间的区别很难描述,因为英语没有区别。然而,在其他语言中,当单独使用文本时使用的词语存在差异,而不是在完整的日期中。
 * 例如,单独在日期选择器中使用的一个月使用的单词与日期中与日期和年份相关联的用于月份的单词不同。
 * 
 *  @implSpec这是不可变的和线程安全的枚举。
 * 
 */
public enum TextStyle {
    // ordered from large to small
    // ordered so that bit 0 of the ordinal indicates stand-alone.

    /**
     * Full text, typically the full description.
     * For example, day-of-week Monday might output "Monday".
     * <p>
     *  全文,通常是完整说明。例如,星期一星期一可能输出"星期一"。
     * 
     */
    FULL(Calendar.LONG_FORMAT, 0),
    /**
     * Full text for stand-alone use, typically the full description.
     * For example, day-of-week Monday might output "Monday".
     * <p>
     *  用于独立使用的完整文本,通常为完整说明。例如,星期一星期一可能输出"星期一"。
     * 
     */
    FULL_STANDALONE(Calendar.LONG_STANDALONE, 0),
    /**
     * Short text, typically an abbreviation.
     * For example, day-of-week Monday might output "Mon".
     * <p>
     *  短文本,通常是缩写。例如,星期一星期一可能输出"星期一"。
     * 
     */
    SHORT(Calendar.SHORT_FORMAT, 1),
    /**
     * Short text for stand-alone use, typically an abbreviation.
     * For example, day-of-week Monday might output "Mon".
     * <p>
     *  用于独立使用的短文本,通常为缩写。例如,星期一星期一可能输出"星期一"。
     * 
     */
    SHORT_STANDALONE(Calendar.SHORT_STANDALONE, 1),
    /**
     * Narrow text, typically a single letter.
     * For example, day-of-week Monday might output "M".
     * <p>
     *  缩小文本,通常为单个字母。例如,星期一星期一可能输出"M"。
     * 
     */
    NARROW(Calendar.NARROW_FORMAT, 1),
    /**
     * Narrow text for stand-alone use, typically a single letter.
     * For example, day-of-week Monday might output "M".
     * <p>
     *  缩小文本以供单独使用,通常为单个字母。例如,星期一星期一可能输出"M"。
     * 
     */
    NARROW_STANDALONE(Calendar.NARROW_STANDALONE, 1);

    private final int calendarStyle;
    private final int zoneNameStyleIndex;

    private TextStyle(int calendarStyle, int zoneNameStyleIndex) {
        this.calendarStyle = calendarStyle;
        this.zoneNameStyleIndex = zoneNameStyleIndex;
    }

    /**
     * Returns true if the Style is a stand-alone style.
     * <p>
     *  如果Style是独立样式,则返回true。
     * 
     * 
     * @return true if the style is a stand-alone style.
     */
    public boolean isStandalone() {
        return (ordinal() & 1) == 1;
    }

    /**
     * Returns the stand-alone style with the same size.
     * <p>
     *  返回具有相同大小的独立样式。
     * 
     * 
     * @return the stand-alone style with the same size
     */
    public TextStyle asStandalone() {
        return TextStyle.values()[ordinal()  | 1];
    }

    /**
     * Returns the normal style with the same size.
     *
     * <p>
     *  返回大小相同的正常样式。
     * 
     * 
     * @return the normal style with the same size
     */
    public TextStyle asNormal() {
        return TextStyle.values()[ordinal() & ~1];
    }

    /**
     * Returns the {@code Calendar} style corresponding to this {@code TextStyle}.
     *
     * <p>
     *  返回与此{@code TextStyle}对应的{@code Calendar}样式。
     * 
     * 
     * @return the corresponding {@code Calendar} style
     */
    int toCalendarStyle() {
        return calendarStyle;
    }

    /**
     * Returns the relative index value to an element of the {@link
     * java.text.DateFormatSymbols#getZoneStrings() DateFormatSymbols.getZoneStrings()}
     * value, 0 for long names and 1 for short names (abbreviations). Note that these values
     * do <em>not</em> correspond to the {@link java.util.TimeZone#LONG} and {@link
     * java.util.TimeZone#SHORT} values.
     *
     * <p>
     * 返回{@link java.text.DateFormatSymbols#getZoneStrings()DateFormatSymbols.getZoneStrings()}值的元素的相对索引值,长名
     * 称为0,短名称为1。
     * 
     * @return the relative index value to time zone names array
     */
    int zoneNameStyleIndex() {
        return zoneNameStyleIndex;
    }
}
