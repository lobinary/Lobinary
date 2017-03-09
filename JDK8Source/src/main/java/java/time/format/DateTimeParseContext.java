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

import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Context object used during date and time parsing.
 * <p>
 * This class represents the current state of the parse.
 * It has the ability to store and retrieve the parsed values and manage optional segments.
 * It also provides key information to the parsing methods.
 * <p>
 * Once parsing is complete, the {@link #toUnresolved()} is used to obtain the unresolved
 * result data. The {@link #toResolved()} is used to obtain the resolved result.
 *
 * @implSpec
 * This class is a mutable context intended for use from a single thread.
 * Usage of the class is thread-safe within standard parsing as a new instance of this class
 * is automatically created for each parse and parsing is single-threaded
 *
 * <p>
 *  日期和时间解析期间使用的上下文对象。
 * <p>
 *  此类表示解析的当前状态。它具有存储和检索解析值和管理可选段的能力。它还为解析方法提供了关键信息。
 * <p>
 *  一旦解析完成,{@link #toUnresolved()}用于获取未解析的结果数据。 {@link #toResolved()}用于获取已解析的结果。
 * 
 *  @implSpec这个类是一个可变的上下文,用于单线程。在标准解析中,类的使用是线程安全的,因为每个解析都会自动创建此类的新实例,并且解析是单线程的
 * 
 * 
 * @since 1.8
 */
final class DateTimeParseContext {

    /**
     * The formatter, not null.
     * <p>
     *  格式化程序,不是null。
     * 
     */
    private DateTimeFormatter formatter;
    /**
     * Whether to parse using case sensitively.
     * <p>
     *  是否敏感地解析用例。
     * 
     */
    private boolean caseSensitive = true;
    /**
     * Whether to parse using strict rules.
     * <p>
     * 是否使用严格规则解析。
     * 
     */
    private boolean strict = true;
    /**
     * The list of parsed data.
     * <p>
     *  已解析数据的列表。
     * 
     */
    private final ArrayList<Parsed> parsed = new ArrayList<>();
    /**
     * List of Consumers<Chronology> to be notified if the Chronology changes.
     * <p>
     *  消费者列表<年表>,如果年表更改通知。
     * 
     */
    private ArrayList<Consumer<Chronology>> chronoListeners = null;

    /**
     * Creates a new instance of the context.
     *
     * <p>
     *  创建上下文的新实例。
     * 
     * 
     * @param formatter  the formatter controlling the parse, not null
     */
    DateTimeParseContext(DateTimeFormatter formatter) {
        super();
        this.formatter = formatter;
        parsed.add(new Parsed());
    }

    /**
     * Creates a copy of this context.
     * This retains the case sensitive and strict flags.
     * <p>
     *  创建此上下文的副本。这保留区分大小写和严格的标志。
     * 
     */
    DateTimeParseContext copy() {
        DateTimeParseContext newContext = new DateTimeParseContext(formatter);
        newContext.caseSensitive = caseSensitive;
        newContext.strict = strict;
        return newContext;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the locale.
     * <p>
     * This locale is used to control localization in the parse except
     * where localization is controlled by the DecimalStyle.
     *
     * <p>
     *  获取区域设置。
     * <p>
     *  此区域设置用于控制解析中的本地化,除非本地化由DecimalStyle控制。
     * 
     * 
     * @return the locale, not null
     */
    Locale getLocale() {
        return formatter.getLocale();
    }

    /**
     * Gets the DecimalStyle.
     * <p>
     * The DecimalStyle controls the numeric parsing.
     *
     * <p>
     *  获取DecimalStyle。
     * <p>
     *  DecimalStyle控制数字解析。
     * 
     * 
     * @return the DecimalStyle, not null
     */
    DecimalStyle getDecimalStyle() {
        return formatter.getDecimalStyle();
    }

    /**
     * Gets the effective chronology during parsing.
     *
     * <p>
     *  获取解析期间的有效年表。
     * 
     * 
     * @return the effective parsing chronology, not null
     */
    Chronology getEffectiveChronology() {
        Chronology chrono = currentParsed().chrono;
        if (chrono == null) {
            chrono = formatter.getChronology();
            if (chrono == null) {
                chrono = IsoChronology.INSTANCE;
            }
        }
        return chrono;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if parsing is case sensitive.
     *
     * <p>
     *  检查解析是否区分大小写。
     * 
     * 
     * @return true if parsing is case sensitive, false if case insensitive
     */
    boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * Sets whether the parsing is case sensitive or not.
     *
     * <p>
     *  设置解析是否区分大小写。
     * 
     * 
     * @param caseSensitive  changes the parsing to be case sensitive or not from now on
     */
    void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    //-----------------------------------------------------------------------
    /**
     * Helper to compare two {@code CharSequence} instances.
     * This uses {@link #isCaseSensitive()}.
     *
     * <p>
     *  助手来比较两个{@code CharSequence}实例。这使用{@link #isCaseSensitive()}。
     * 
     * 
     * @param cs1  the first character sequence, not null
     * @param offset1  the offset into the first sequence, valid
     * @param cs2  the second character sequence, not null
     * @param offset2  the offset into the second sequence, valid
     * @param length  the length to check, valid
     * @return true if equal
     */
    boolean subSequenceEquals(CharSequence cs1, int offset1, CharSequence cs2, int offset2, int length) {
        if (offset1 + length > cs1.length() || offset2 + length > cs2.length()) {
            return false;
        }
        if (isCaseSensitive()) {
            for (int i = 0; i < length; i++) {
                char ch1 = cs1.charAt(offset1 + i);
                char ch2 = cs2.charAt(offset2 + i);
                if (ch1 != ch2) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                char ch1 = cs1.charAt(offset1 + i);
                char ch2 = cs2.charAt(offset2 + i);
                if (ch1 != ch2 && Character.toUpperCase(ch1) != Character.toUpperCase(ch2) &&
                        Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Helper to compare two {@code char}.
     * This uses {@link #isCaseSensitive()}.
     *
     * <p>
     *  助手比较两个{@code char}。这使用{@link #isCaseSensitive()}。
     * 
     * 
     * @param ch1  the first character
     * @param ch2  the second character
     * @return true if equal
     */
    boolean charEquals(char ch1, char ch2) {
        if (isCaseSensitive()) {
            return ch1 == ch2;
        }
        return charEqualsIgnoreCase(ch1, ch2);
    }

    /**
     * Compares two characters ignoring case.
     *
     * <p>
     *  比较两个字符忽略大小写。
     * 
     * 
     * @param c1  the first
     * @param c2  the second
     * @return true if equal
     */
    static boolean charEqualsIgnoreCase(char c1, char c2) {
        return c1 == c2 ||
                Character.toUpperCase(c1) == Character.toUpperCase(c2) ||
                Character.toLowerCase(c1) == Character.toLowerCase(c2);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if parsing is strict.
     * <p>
     * Strict parsing requires exact matching of the text and sign styles.
     *
     * <p>
     *  检查解析是否严格。
     * <p>
     *  严格解析需要文本和标志样式的完全匹配。
     * 
     * 
     * @return true if parsing is strict, false if lenient
     */
    boolean isStrict() {
        return strict;
    }

    /**
     * Sets whether parsing is strict or lenient.
     *
     * <p>
     *  设置解析是严格还是宽松。
     * 
     * 
     * @param strict  changes the parsing to be strict or lenient from now on
     */
    void setStrict(boolean strict) {
        this.strict = strict;
    }

    //-----------------------------------------------------------------------
    /**
     * Starts the parsing of an optional segment of the input.
     * <p>
     *  开始解析输入的可选段。
     * 
     */
    void startOptional() {
        parsed.add(currentParsed().copy());
    }

    /**
     * Ends the parsing of an optional segment of the input.
     *
     * <p>
     *  结束输入的可选段的解析。
     * 
     * 
     * @param successful  whether the optional segment was successfully parsed
     */
    void endOptional(boolean successful) {
        if (successful) {
            parsed.remove(parsed.size() - 2);
        } else {
            parsed.remove(parsed.size() - 1);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the currently active temporal objects.
     *
     * <p>
     *  获取当前活动的临时对象。
     * 
     * 
     * @return the current temporal objects, not null
     */
    private Parsed currentParsed() {
        return parsed.get(parsed.size() - 1);
    }

    /**
     * Gets the unresolved result of the parse.
     *
     * <p>
     *  获取解析的未解析结果。
     * 
     * 
     * @return the result of the parse, not null
     */
    Parsed toUnresolved() {
        return currentParsed();
    }

    /**
     * Gets the resolved result of the parse.
     *
     * <p>
     *  获取解析的解析结果。
     * 
     * 
     * @return the result of the parse, not null
     */
    TemporalAccessor toResolved(ResolverStyle resolverStyle, Set<TemporalField> resolverFields) {
        Parsed parsed = currentParsed();
        parsed.chrono = getEffectiveChronology();
        parsed.zone = (parsed.zone != null ? parsed.zone : formatter.getZone());
        return parsed.resolve(resolverStyle, resolverFields);
    }


    //-----------------------------------------------------------------------
    /**
     * Gets the first value that was parsed for the specified field.
     * <p>
     * This searches the results of the parse, returning the first value found
     * for the specified field. No attempt is made to derive a value.
     * The field may have an out of range value.
     * For example, the day-of-month might be set to 50, or the hour to 1000.
     *
     * <p>
     *  获取为指定字段分析的第一个值。
     * <p>
     * 这将搜索解析的结果,返回为指定字段找到的第一个值。不尝试导出值。该字段可能具有超出范围值。例如,日期可能设置为50,或小时设置为1000。
     * 
     * 
     * @param field  the field to query from the map, null returns null
     * @return the value mapped to the specified field, null if field was not parsed
     */
    Long getParsed(TemporalField field) {
        return currentParsed().fieldValues.get(field);
    }

    /**
     * Stores the parsed field.
     * <p>
     * This stores a field-value pair that has been parsed.
     * The value stored may be out of range for the field - no checks are performed.
     *
     * <p>
     *  存储解析的字段。
     * <p>
     *  这存储了已解析的字段值对。存储的值可能超出字段的范围 - 不执行检查。
     * 
     * 
     * @param field  the field to set in the field-value map, not null
     * @param value  the value to set in the field-value map
     * @param errorPos  the position of the field being parsed
     * @param successPos  the position after the field being parsed
     * @return the new position
     */
    int setParsedField(TemporalField field, long value, int errorPos, int successPos) {
        Objects.requireNonNull(field, "field");
        Long old = currentParsed().fieldValues.put(field, value);
        return (old != null && old.longValue() != value) ? ~errorPos : successPos;
    }

    /**
     * Stores the parsed chronology.
     * <p>
     * This stores the chronology that has been parsed.
     * No validation is performed other than ensuring it is not null.
     * <p>
     * The list of listeners is copied and cleared so that each
     * listener is called only once.  A listener can add itself again
     * if it needs to be notified of future changes.
     *
     * <p>
     *  存储解析的年表。
     * <p>
     *  这存储已解析的年表。除了确保它不为null之外,不执行验证。
     * <p>
     *  复制和清除侦听器列表,以便每个侦听器只调用一次。如果需要通知未来更改,侦听器可以再次添加自己。
     * 
     * 
     * @param chrono  the parsed chronology, not null
     */
    void setParsed(Chronology chrono) {
        Objects.requireNonNull(chrono, "chrono");
        currentParsed().chrono = chrono;
        if (chronoListeners != null && !chronoListeners.isEmpty()) {
            @SuppressWarnings({"rawtypes", "unchecked"})
            Consumer<Chronology>[] tmp = new Consumer[1];
            Consumer<Chronology>[] listeners = chronoListeners.toArray(tmp);
            chronoListeners.clear();
            for (Consumer<Chronology> l : listeners) {
                l.accept(chrono);
            }
        }
    }

    /**
     * Adds a Consumer<Chronology> to the list of listeners to be notified
     * if the Chronology changes.
     * <p>
     *  如果年表更改,则向要通知的侦听器列表中添加一个Consumer <Chronology>。
     * 
     * 
     * @param listener a Consumer<Chronology> to be called when Chronology changes
     */
    void addChronoChangedListener(Consumer<Chronology> listener) {
        if (chronoListeners == null) {
            chronoListeners = new ArrayList<Consumer<Chronology>>();
        }
        chronoListeners.add(listener);
    }

    /**
     * Stores the parsed zone.
     * <p>
     * This stores the zone that has been parsed.
     * No validation is performed other than ensuring it is not null.
     *
     * <p>
     *  存储解析的区域。
     * <p>
     *  这存储已解析的区域。除了确保它不为null之外,不执行验证。
     * 
     * 
     * @param zone  the parsed zone, not null
     */
    void setParsed(ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        currentParsed().zone = zone;
    }

    /**
     * Stores the parsed leap second.
     * <p>
     *  存储解析的闰秒。
     * 
     */
    void setParsedLeapSecond() {
        currentParsed().leapSecond = true;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a string version of the context for debugging.
     *
     * <p>
     *  返回调试的上下文的字符串版本。
     * 
     * @return a string representation of the context data, not null
     */
    @Override
    public String toString() {
        return currentParsed().toString();
    }

}
