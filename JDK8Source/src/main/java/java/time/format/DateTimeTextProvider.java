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
 * Copyright (c) 2011-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2011-2012,Stephen Colebourne和Michael Nascimento Santos
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

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalField;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import sun.util.locale.provider.CalendarDataUtility;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleResources;

/**
 * A provider to obtain the textual form of a date-time field.
 *
 * @implSpec
 * Implementations must be thread-safe.
 * Implementations should cache the textual information.
 *
 * <p>
 *  提供程序以获取日期时间字段的文本形式。
 * 
 *  @implSpec实现必须是线程安全的。实现应缓存文本信息。
 * 
 * 
 * @since 1.8
 */
class DateTimeTextProvider {

    /** Cache. */
    private static final ConcurrentMap<Entry<TemporalField, Locale>, Object> CACHE = new ConcurrentHashMap<>(16, 0.75f, 2);
    /** Comparator. */
    private static final Comparator<Entry<String, Long>> COMPARATOR = new Comparator<Entry<String, Long>>() {
        @Override
        public int compare(Entry<String, Long> obj1, Entry<String, Long> obj2) {
            return obj2.getKey().length() - obj1.getKey().length();  // longest to shortest
        }
    };

    DateTimeTextProvider() {}

    /**
     * Gets the provider of text.
     *
     * <p>
     *  获取文本提供程序。
     * 
     * 
     * @return the provider, not null
     */
    static DateTimeTextProvider getInstance() {
        return new DateTimeTextProvider();
    }

    /**
     * Gets the text for the specified field, locale and style
     * for the purpose of formatting.
     * <p>
     * The text associated with the value is returned.
     * The null return value should be used if there is no applicable text, or
     * if the text would be a numeric representation of the value.
     *
     * <p>
     *  获取指定字段,区域设置和样式的文本以进行格式化。
     * <p>
     *  将返回与该值相关联的文本。如果没有适用的文本,或者如果文本将是值的数字表示,则应使用空返回值。
     * 
     * 
     * @param field  the field to get text for, not null
     * @param value  the field value to get text for, not null
     * @param style  the style to get text for, not null
     * @param locale  the locale to get text for, not null
     * @return the text for the field value, null if no text found
     */
    public String getText(TemporalField field, long value, TextStyle style, Locale locale) {
        Object store = findStore(field, locale);
        if (store instanceof LocaleStore) {
            return ((LocaleStore) store).getText(value, style);
        }
        return null;
    }

    /**
     * Gets the text for the specified chrono, field, locale and style
     * for the purpose of formatting.
     * <p>
     * The text associated with the value is returned.
     * The null return value should be used if there is no applicable text, or
     * if the text would be a numeric representation of the value.
     *
     * <p>
     *  获取指定计时码,字段,区域设置和样式的文本以进行格式化。
     * <p>
     *  将返回与该值相关联的文本。如果没有适用的文本,或者如果文本将是值的数字表示,则应使用空返回值。
     * 
     * 
     * @param chrono  the Chronology to get text for, not null
     * @param field  the field to get text for, not null
     * @param value  the field value to get text for, not null
     * @param style  the style to get text for, not null
     * @param locale  the locale to get text for, not null
     * @return the text for the field value, null if no text found
     */
    public String getText(Chronology chrono, TemporalField field, long value,
                                    TextStyle style, Locale locale) {
        if (chrono == IsoChronology.INSTANCE
                || !(field instanceof ChronoField)) {
            return getText(field, value, style, locale);
        }

        int fieldIndex;
        int fieldValue;
        if (field == ERA) {
            fieldIndex = Calendar.ERA;
            if (chrono == JapaneseChronology.INSTANCE) {
                if (value == -999) {
                    fieldValue = 0;
                } else {
                    fieldValue = (int) value + 2;
                }
            } else {
                fieldValue = (int) value;
            }
        } else if (field == MONTH_OF_YEAR) {
            fieldIndex = Calendar.MONTH;
            fieldValue = (int) value - 1;
        } else if (field == DAY_OF_WEEK) {
            fieldIndex = Calendar.DAY_OF_WEEK;
            fieldValue = (int) value + 1;
            if (fieldValue > 7) {
                fieldValue = Calendar.SUNDAY;
            }
        } else if (field == AMPM_OF_DAY) {
            fieldIndex = Calendar.AM_PM;
            fieldValue = (int) value;
        } else {
            return null;
        }
        return CalendarDataUtility.retrieveJavaTimeFieldValueName(
                chrono.getCalendarType(), fieldIndex, fieldValue, style.toCalendarStyle(), locale);
    }

    /**
     * Gets an iterator of text to field for the specified field, locale and style
     * for the purpose of parsing.
     * <p>
     * The iterator must be returned in order from the longest text to the shortest.
     * <p>
     * The null return value should be used if there is no applicable parsable text, or
     * if the text would be a numeric representation of the value.
     * Text can only be parsed if all the values for that field-style-locale combination are unique.
     *
     * <p>
     * 获取指定字段,语言环境和样式的文本迭代器字段,以便进行语法分析。
     * <p>
     *  迭代器必须按照从最长的文本到最短的顺序返回。
     * <p>
     *  如果没有适用的可解析文本,或者如果文本将是值的数字表示,则应使用空返回值。只有该字段样式区域设置组合的所有值都是唯一的,才能解析文本。
     * 
     * 
     * @param field  the field to get text for, not null
     * @param style  the style to get text for, null for all parsable text
     * @param locale  the locale to get text for, not null
     * @return the iterator of text to field pairs, in order from longest text to shortest text,
     *  null if the field or style is not parsable
     */
    public Iterator<Entry<String, Long>> getTextIterator(TemporalField field, TextStyle style, Locale locale) {
        Object store = findStore(field, locale);
        if (store instanceof LocaleStore) {
            return ((LocaleStore) store).getTextIterator(style);
        }
        return null;
    }

    /**
     * Gets an iterator of text to field for the specified chrono, field, locale and style
     * for the purpose of parsing.
     * <p>
     * The iterator must be returned in order from the longest text to the shortest.
     * <p>
     * The null return value should be used if there is no applicable parsable text, or
     * if the text would be a numeric representation of the value.
     * Text can only be parsed if all the values for that field-style-locale combination are unique.
     *
     * <p>
     *  获取指定的计时码,字段,语言环境和样式的文本迭代器,以便进行语法分析。
     * <p>
     *  迭代器必须按照从最长的文本到最短的顺序返回。
     * <p>
     *  如果没有适用的可解析文本,或者如果文本将是值的数字表示,则应使用空返回值。只有该字段样式区域设置组合的所有值都是唯一的,才能解析文本。
     * 
     * 
     * @param chrono  the Chronology to get text for, not null
     * @param field  the field to get text for, not null
     * @param style  the style to get text for, null for all parsable text
     * @param locale  the locale to get text for, not null
     * @return the iterator of text to field pairs, in order from longest text to shortest text,
     *  null if the field or style is not parsable
     */
    public Iterator<Entry<String, Long>> getTextIterator(Chronology chrono, TemporalField field,
                                                         TextStyle style, Locale locale) {
        if (chrono == IsoChronology.INSTANCE
                || !(field instanceof ChronoField)) {
            return getTextIterator(field, style, locale);
        }

        int fieldIndex;
        switch ((ChronoField)field) {
        case ERA:
            fieldIndex = Calendar.ERA;
            break;
        case MONTH_OF_YEAR:
            fieldIndex = Calendar.MONTH;
            break;
        case DAY_OF_WEEK:
            fieldIndex = Calendar.DAY_OF_WEEK;
            break;
        case AMPM_OF_DAY:
            fieldIndex = Calendar.AM_PM;
            break;
        default:
            return null;
        }

        int calendarStyle = (style == null) ? Calendar.ALL_STYLES : style.toCalendarStyle();
        Map<String, Integer> map = CalendarDataUtility.retrieveJavaTimeFieldValueNames(
                chrono.getCalendarType(), fieldIndex, calendarStyle, locale);
        if (map == null) {
            return null;
        }
        List<Entry<String, Long>> list = new ArrayList<>(map.size());
        switch (fieldIndex) {
        case Calendar.ERA:
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                int era = entry.getValue();
                if (chrono == JapaneseChronology.INSTANCE) {
                    if (era == 0) {
                        era = -999;
                    } else {
                        era -= 2;
                    }
                }
                list.add(createEntry(entry.getKey(), (long)era));
            }
            break;
        case Calendar.MONTH:
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                list.add(createEntry(entry.getKey(), (long)(entry.getValue() + 1)));
            }
            break;
        case Calendar.DAY_OF_WEEK:
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                list.add(createEntry(entry.getKey(), (long)toWeekDay(entry.getValue())));
            }
            break;
        default:
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                list.add(createEntry(entry.getKey(), (long)entry.getValue()));
            }
            break;
        }
        return list.iterator();
    }

    private Object findStore(TemporalField field, Locale locale) {
        Entry<TemporalField, Locale> key = createEntry(field, locale);
        Object store = CACHE.get(key);
        if (store == null) {
            store = createStore(field, locale);
            CACHE.putIfAbsent(key, store);
            store = CACHE.get(key);
        }
        return store;
    }

    private static int toWeekDay(int calWeekDay) {
        if (calWeekDay == Calendar.SUNDAY) {
            return 7;
        } else {
            return calWeekDay - 1;
        }
    }

    private Object createStore(TemporalField field, Locale locale) {
        Map<TextStyle, Map<Long, String>> styleMap = new HashMap<>();
        if (field == ERA) {
            for (TextStyle textStyle : TextStyle.values()) {
                if (textStyle.isStandalone()) {
                    // Stand-alone isn't applicable to era names.
                    continue;
                }
                Map<String, Integer> displayNames = CalendarDataUtility.retrieveJavaTimeFieldValueNames(
                        "gregory", Calendar.ERA, textStyle.toCalendarStyle(), locale);
                if (displayNames != null) {
                    Map<Long, String> map = new HashMap<>();
                    for (Entry<String, Integer> entry : displayNames.entrySet()) {
                        map.put((long) entry.getValue(), entry.getKey());
                    }
                    if (!map.isEmpty()) {
                        styleMap.put(textStyle, map);
                    }
                }
            }
            return new LocaleStore(styleMap);
        }

        if (field == MONTH_OF_YEAR) {
            for (TextStyle textStyle : TextStyle.values()) {
                Map<String, Integer> displayNames = CalendarDataUtility.retrieveJavaTimeFieldValueNames(
                        "gregory", Calendar.MONTH, textStyle.toCalendarStyle(), locale);
                Map<Long, String> map = new HashMap<>();
                if (displayNames != null) {
                    for (Entry<String, Integer> entry : displayNames.entrySet()) {
                        map.put((long) (entry.getValue() + 1), entry.getKey());
                    }

                } else {
                    // Narrow names may have duplicated names, such as "J" for January, Jun, July.
                    // Get names one by one in that case.
                    for (int month = Calendar.JANUARY; month <= Calendar.DECEMBER; month++) {
                        String name;
                        name = CalendarDataUtility.retrieveJavaTimeFieldValueName(
                                "gregory", Calendar.MONTH, month, textStyle.toCalendarStyle(), locale);
                        if (name == null) {
                            break;
                        }
                        map.put((long) (month + 1), name);
                    }
                }
                if (!map.isEmpty()) {
                    styleMap.put(textStyle, map);
                }
            }
            return new LocaleStore(styleMap);
        }

        if (field == DAY_OF_WEEK) {
            for (TextStyle textStyle : TextStyle.values()) {
                Map<String, Integer> displayNames = CalendarDataUtility.retrieveJavaTimeFieldValueNames(
                        "gregory", Calendar.DAY_OF_WEEK, textStyle.toCalendarStyle(), locale);
                Map<Long, String> map = new HashMap<>();
                if (displayNames != null) {
                    for (Entry<String, Integer> entry : displayNames.entrySet()) {
                        map.put((long)toWeekDay(entry.getValue()), entry.getKey());
                    }

                } else {
                    // Narrow names may have duplicated names, such as "S" for Sunday and Saturday.
                    // Get names one by one in that case.
                    for (int wday = Calendar.SUNDAY; wday <= Calendar.SATURDAY; wday++) {
                        String name;
                        name = CalendarDataUtility.retrieveJavaTimeFieldValueName(
                            "gregory", Calendar.DAY_OF_WEEK, wday, textStyle.toCalendarStyle(), locale);
                        if (name == null) {
                            break;
                        }
                        map.put((long)toWeekDay(wday), name);
                    }
                }
                if (!map.isEmpty()) {
                    styleMap.put(textStyle, map);
                }
            }
            return new LocaleStore(styleMap);
        }

        if (field == AMPM_OF_DAY) {
            for (TextStyle textStyle : TextStyle.values()) {
                if (textStyle.isStandalone()) {
                    // Stand-alone isn't applicable to AM/PM.
                    continue;
                }
                Map<String, Integer> displayNames = CalendarDataUtility.retrieveJavaTimeFieldValueNames(
                        "gregory", Calendar.AM_PM, textStyle.toCalendarStyle(), locale);
                if (displayNames != null) {
                    Map<Long, String> map = new HashMap<>();
                    for (Entry<String, Integer> entry : displayNames.entrySet()) {
                        map.put((long) entry.getValue(), entry.getKey());
                    }
                    if (!map.isEmpty()) {
                        styleMap.put(textStyle, map);
                    }
                }
            }
            return new LocaleStore(styleMap);
        }

        if (field == IsoFields.QUARTER_OF_YEAR) {
            // The order of keys must correspond to the TextStyle.values() order.
            final String[] keys = {
                "QuarterNames",
                "standalone.QuarterNames",
                "QuarterAbbreviations",
                "standalone.QuarterAbbreviations",
                "QuarterNarrows",
                "standalone.QuarterNarrows",
            };
            for (int i = 0; i < keys.length; i++) {
                String[] names = getLocalizedResource(keys[i], locale);
                if (names != null) {
                    Map<Long, String> map = new HashMap<>();
                    for (int q = 0; q < names.length; q++) {
                        map.put((long) (q + 1), names[q]);
                    }
                    styleMap.put(TextStyle.values()[i], map);
                }
            }
            return new LocaleStore(styleMap);
        }

        return "";  // null marker for map
    }

    /**
     * Helper method to create an immutable entry.
     *
     * <p>
     *  Helper方法创建一个不可变的条目。
     * 
     * 
     * @param text  the text, not null
     * @param field  the field, not null
     * @return the entry, not null
     */
    private static <A, B> Entry<A, B> createEntry(A text, B field) {
        return new SimpleImmutableEntry<>(text, field);
    }

    /**
     * Returns the localized resource of the given key and locale, or null
     * if no localized resource is available.
     *
     * <p>
     *  返回给定键和语言环境的本地化资源,如果没有本地化资源可用,则返回null。
     * 
     * 
     * @param key  the key of the localized resource, not null
     * @param locale  the locale, not null
     * @return the localized resource, or null if not available
     * @throws NullPointerException if key or locale is null
     */
    @SuppressWarnings("unchecked")
    static <T> T getLocalizedResource(String key, Locale locale) {
        LocaleResources lr = LocaleProviderAdapter.getResourceBundleBased()
                                    .getLocaleResources(locale);
        ResourceBundle rb = lr.getJavaTimeFormatData();
        return rb.containsKey(key) ? (T) rb.getObject(key) : null;
    }

    /**
     * Stores the text for a single locale.
     * <p>
     * Some fields have a textual representation, such as day-of-week or month-of-year.
     * These textual representations can be captured in this class for printing
     * and parsing.
     * <p>
     * This class is immutable and thread-safe.
     * <p>
     *  存储单个区域设置的文本。
     * <p>
     *  一些字段具有文本表示,例如星期几或年份。这些文本表示可以在此类中捕获以进行打印和解析。
     * <p>
     *  这个类是不可变的和线程安全的。
     * 
     */
    static final class LocaleStore {
        /**
         * Map of value to text.
         * <p>
         *  值到文本的映射。
         * 
         */
        private final Map<TextStyle, Map<Long, String>> valueTextMap;
        /**
         * Parsable data.
         * <p>
         *  可解析数据。
         * 
         */
        private final Map<TextStyle, List<Entry<String, Long>>> parsable;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数。
         * 
         * 
         * @param valueTextMap  the map of values to text to store, assigned and not altered, not null
         */
        LocaleStore(Map<TextStyle, Map<Long, String>> valueTextMap) {
            this.valueTextMap = valueTextMap;
            Map<TextStyle, List<Entry<String, Long>>> map = new HashMap<>();
            List<Entry<String, Long>> allList = new ArrayList<>();
            for (Map.Entry<TextStyle, Map<Long, String>> vtmEntry : valueTextMap.entrySet()) {
                Map<String, Entry<String, Long>> reverse = new HashMap<>();
                for (Map.Entry<Long, String> entry : vtmEntry.getValue().entrySet()) {
                    if (reverse.put(entry.getValue(), createEntry(entry.getValue(), entry.getKey())) != null) {
                        // TODO: BUG: this has no effect
                        continue;  // not parsable, try next style
                    }
                }
                List<Entry<String, Long>> list = new ArrayList<>(reverse.values());
                Collections.sort(list, COMPARATOR);
                map.put(vtmEntry.getKey(), list);
                allList.addAll(list);
                map.put(null, allList);
            }
            Collections.sort(allList, COMPARATOR);
            this.parsable = map;
        }

        /**
         * Gets the text for the specified field value, locale and style
         * for the purpose of printing.
         *
         * <p>
         *  获取指定字段值,语言环境和样式的文本,以便进行打印。
         * 
         * 
         * @param value  the value to get text for, not null
         * @param style  the style to get text for, not null
         * @return the text for the field value, null if no text found
         */
        String getText(long value, TextStyle style) {
            Map<Long, String> map = valueTextMap.get(style);
            return map != null ? map.get(value) : null;
        }

        /**
         * Gets an iterator of text to field for the specified style for the purpose of parsing.
         * <p>
         * The iterator must be returned in order from the longest text to the shortest.
         *
         * <p>
         *  获取指定样式的文本迭代器字段,以便进行解析。
         * <p>
         * 
         * @param style  the style to get text for, null for all parsable text
         * @return the iterator of text to field pairs, in order from longest text to shortest text,
         *  null if the style is not parsable
         */
        Iterator<Entry<String, Long>> getTextIterator(TextStyle style) {
            List<Entry<String, Long>> list = parsable.get(style);
            return list != null ? list.iterator() : null;
        }
    }
}
