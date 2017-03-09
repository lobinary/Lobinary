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

/*
 * (C) Copyright Taligent, Inc. 1996 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996  - 保留所有权利(C)版权所有IBM Corp. 1996  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.text.spi.DateFormatSymbolsProvider;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleServiceProviderPool;
import sun.util.locale.provider.ResourceBundleBasedAdapter;
import sun.util.locale.provider.TimeZoneNameUtility;

/**
 * <code>DateFormatSymbols</code> is a public class for encapsulating
 * localizable date-time formatting data, such as the names of the
 * months, the names of the days of the week, and the time zone data.
 * <code>SimpleDateFormat</code> uses
 * <code>DateFormatSymbols</code> to encapsulate this information.
 *
 * <p>
 * Typically you shouldn't use <code>DateFormatSymbols</code> directly.
 * Rather, you are encouraged to create a date-time formatter with the
 * <code>DateFormat</code> class's factory methods: <code>getTimeInstance</code>,
 * <code>getDateInstance</code>, or <code>getDateTimeInstance</code>.
 * These methods automatically create a <code>DateFormatSymbols</code> for
 * the formatter so that you don't have to. After the
 * formatter is created, you may modify its format pattern using the
 * <code>setPattern</code> method. For more information about
 * creating formatters using <code>DateFormat</code>'s factory methods,
 * see {@link DateFormat}.
 *
 * <p>
 * If you decide to create a date-time formatter with a specific
 * format pattern for a specific locale, you can do so with:
 * <blockquote>
 * <pre>
 * new SimpleDateFormat(aPattern, DateFormatSymbols.getInstance(aLocale)).
 * </pre>
 * </blockquote>
 *
 * <p>
 * <code>DateFormatSymbols</code> objects are cloneable. When you obtain
 * a <code>DateFormatSymbols</code> object, feel free to modify the
 * date-time formatting data. For instance, you can replace the localized
 * date-time format pattern characters with the ones that you feel easy
 * to remember. Or you can change the representative cities
 * to your favorite ones.
 *
 * <p>
 * New <code>DateFormatSymbols</code> subclasses may be added to support
 * <code>SimpleDateFormat</code> for date-time formatting for additional locales.

 * <p>
 *  <code> DateFormatSymbols </code>是用于封装可本地化的日期时间格式化数据的公共类,例如月份的名称,星期几的名称和时区数据。
 *  <code> SimpleDateFormat </code>使用<code> DateFormatSymbols </code>封装此信息。
 * 
 * <p>
 * 通常你不应该直接使用<code> DateFormatSymbols </code>。
 * 而是鼓励您使用<code> DateFormat </code>类的工厂方法创建一个日期时间格式化程序：<code> getTimeInstance </code>,<code> getDateInst
 * ance </code>或<code> getDateTimeInstance < / code>。
 * 通常你不应该直接使用<code> DateFormatSymbols </code>。
 * 这些方法会自动为格式化程序创建一个<code> DateFormatSymbols </code>,以便您不必执行。
 * 格式化程序创建后,您可以使用<code> setPattern </code>方法修改其格式模式。
 * 有关使用<code> DateFormat </code>的工厂方法创建格式化程序的更多信息,请参阅{@link DateFormat}。
 * 
 * <p>
 *  如果您决定为特定区域设置创建具有特定格式模式的日期时间格式器,可以使用：
 * <blockquote>
 * <pre>
 *  new SimpleDateFormat(aPattern,DateFormatSymbols.getInstance(aLocale))。
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  <code> DateFormatSymbols </code>对象是可克隆的。当您获取<code> DateFormatSymbols </code>对象时,可以随意修改日期时间格式数据。
 * 例如,您可以将本地化的日期/时间格式模式字符替换为您容易记住的字符。或者,您可以将代表城市更改为您最喜欢的城市。
 * 
 * <p>
 *  可以添加新的<code> DateFormatSymbols </code>子类以支持<code> SimpleDateFormat </code>用于其他语言环境的日期时间格式化。
 * 
 * 
 * @see          DateFormat
 * @see          SimpleDateFormat
 * @see          java.util.SimpleTimeZone
 * @author       Chen-Lieh Huang
 */
public class DateFormatSymbols implements Serializable, Cloneable {

    /**
     * Construct a DateFormatSymbols object by loading format data from
     * resources for the default {@link java.util.Locale.Category#FORMAT FORMAT}
     * locale. This constructor can only
     * construct instances for the locales supported by the Java
     * runtime environment, not for those supported by installed
     * {@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * implementations. For full locale coverage, use the
     * {@link #getInstance(Locale) getInstance} method.
     * <p>This is equivalent to calling
     * {@link #DateFormatSymbols(Locale)
     *     DateFormatSymbols(Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     * 通过从默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置的资源中加载格式数据,构造DateFormatSymbols对象。
     * 此构造函数只能为Java运行时环境支持的语言环境构造实例,而不能为已安装的{@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * 实现支持的语言环境构建实例。
     * 通过从默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置的资源中加载格式数据,构造DateFormatSymbols对象。
     * 对于完整的区域覆盖,请使用{@link #getInstance(Locale)getInstance}方法。
     *  <p>这相当于调用{@link #DateFormatSymbols(Locale)DateFormatSymbols(Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     * 对于完整的区域覆盖,请使用{@link #getInstance(Locale)getInstance}方法。
     * 
     * 
     * @see #getInstance()
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @exception  java.util.MissingResourceException
     *             if the resources for the default locale cannot be
     *             found or cannot be loaded.
     */
    public DateFormatSymbols()
    {
        initializeData(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Construct a DateFormatSymbols object by loading format data from
     * resources for the given locale. This constructor can only
     * construct instances for the locales supported by the Java
     * runtime environment, not for those supported by installed
     * {@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * implementations. For full locale coverage, use the
     * {@link #getInstance(Locale) getInstance} method.
     *
     * <p>
     *  通过从给定语言环境的资源加载格式数据来构造DateFormatSymbols对象。
     * 此构造函数只能为Java运行时环境支持的语言环境构造实例,而不能为已安装的{@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * 实现支持的语言环境构建实例。
     *  通过从给定语言环境的资源加载格式数据来构造DateFormatSymbols对象。对于完整的区域覆盖,请使用{@link #getInstance(Locale)getInstance}方法。
     * 
     * 
     * @param locale the desired locale
     * @see #getInstance(Locale)
     * @exception  java.util.MissingResourceException
     *             if the resources for the specified locale cannot be
     *             found or cannot be loaded.
     */
    public DateFormatSymbols(Locale locale)
    {
        initializeData(locale);
    }

    /**
     * Era strings. For example: "AD" and "BC".  An array of 2 strings,
     * indexed by <code>Calendar.BC</code> and <code>Calendar.AD</code>.
     * <p>
     *  时代字符串。例如："AD"和"BC"。由2个字符串组成的数组,由<code> Calendar.BC </code>和<code> Calendar.AD </code>索引。
     * 
     * 
     * @serial
     */
    String eras[] = null;

    /**
     * Month strings. For example: "January", "February", etc.  An array
     * of 13 strings (some calendars have 13 months), indexed by
     * <code>Calendar.JANUARY</code>, <code>Calendar.FEBRUARY</code>, etc.
     * <p>
     *  月字符串。例如："January","February"等。
     * 由<code> Calendar.JANUARY </code>,<code> Calendar.FEBRUARY </code>索引的13个字符串数组(某些日历有13个月)等等。
     * 
     * 
     * @serial
     */
    String months[] = null;

    /**
     * Short month strings. For example: "Jan", "Feb", etc.  An array of
     * 13 strings (some calendars have 13 months), indexed by
     * <code>Calendar.JANUARY</code>, <code>Calendar.FEBRUARY</code>, etc.

     * <p>
     * 短月字符串。例如："Jan","Feb"等。
     * 由<code> Calendar.JANUARY </code>,<code> Calendar.FEBRUARY </code>索引的13个字符串数组(某些日历有13个月)等等。
     * 
     * 
     * @serial
     */
    String shortMonths[] = null;

    /**
     * Weekday strings. For example: "Sunday", "Monday", etc.  An array
     * of 8 strings, indexed by <code>Calendar.SUNDAY</code>,
     * <code>Calendar.MONDAY</code>, etc.
     * The element <code>weekdays[0]</code> is ignored.
     * <p>
     *  星期字符串。例如："Sunday","Monday"等。
     * 由<code> Calendar.SUNDAY </code>,<code> Calendar.MONDAY </code>等索引的8个字符串的数组。
     * 元素<code> weekdays [0] </code>被忽略。
     * 
     * 
     * @serial
     */
    String weekdays[] = null;

    /**
     * Short weekday strings. For example: "Sun", "Mon", etc.  An array
     * of 8 strings, indexed by <code>Calendar.SUNDAY</code>,
     * <code>Calendar.MONDAY</code>, etc.
     * The element <code>shortWeekdays[0]</code> is ignored.
     * <p>
     *  短星期字符串。例如："Sun","Mon"等。由<code> Calendar.SUNDAY </code>,<code> Calendar.MONDAY </code>等索引的8个字符串的数组。
     * 元素<code> shortWeekdays [0] </code>被忽略。
     * 
     * 
     * @serial
     */
    String shortWeekdays[] = null;

    /**
     * AM and PM strings. For example: "AM" and "PM".  An array of
     * 2 strings, indexed by <code>Calendar.AM</code> and
     * <code>Calendar.PM</code>.
     * <p>
     *  AM和PM字符串。例如："AM"和"PM"。由2个字符串组成的数组,由<code> Calendar.AM </code>和<code> Calendar.PM </code>索引。
     * 
     * 
     * @serial
     */
    String ampms[] = null;

    /**
     * Localized names of time zones in this locale.  This is a
     * two-dimensional array of strings of size <em>n</em> by <em>m</em>,
     * where <em>m</em> is at least 5.  Each of the <em>n</em> rows is an
     * entry containing the localized names for a single <code>TimeZone</code>.
     * Each such row contains (with <code>i</code> ranging from
     * 0..<em>n</em>-1):
     * <ul>
     * <li><code>zoneStrings[i][0]</code> - time zone ID</li>
     * <li><code>zoneStrings[i][1]</code> - long name of zone in standard
     * time</li>
     * <li><code>zoneStrings[i][2]</code> - short name of zone in
     * standard time</li>
     * <li><code>zoneStrings[i][3]</code> - long name of zone in daylight
     * saving time</li>
     * <li><code>zoneStrings[i][4]</code> - short name of zone in daylight
     * saving time</li>
     * </ul>
     * The zone ID is <em>not</em> localized; it's one of the valid IDs of
     * the {@link java.util.TimeZone TimeZone} class that are not
     * <a href="../java/util/TimeZone.html#CustomID">custom IDs</a>.
     * All other entries are localized names.
     * <p>
     *  此区域设置中的时区的本地化名称。
     * 这是由<em> m </em>大小为<em> n </em>的字符串的二维数组,其中<em> m </em>至少为5.每个<em> n </em> rows是包含单个<code> TimeZone </code>
     * 的本地化名称的条目。
     *  此区域设置中的时区的本地化名称。每个这样的行包含(与<code> i </code>范围从0 .. <em> n </em> -1)：。
     * <ul>
     *  <li> <code> zoneStrings [i] [0] </code>  - 时区ID </li> <li> <code> zoneStrings [i] [1] </li> <li> <code>
     *  zoneStrings [i] [2] </code>  - 标准时间区域的短名称</li> <li> <code> zoneStrings [i] - 区域在夏令时的长名称</li> <li> <code>
     *  zoneStrings [i] [4] </code>。
     * </ul>
     * 区域ID未本地化</em>;它是{@link java.util.TimeZone TimeZone}类的有效ID之一,不是<a href="../java/util/TimeZone.html#CustomID">
     * 自定义ID </a>。
     * 所有其他条目都是本地化名称。
     * 
     * 
     * @see java.util.TimeZone
     * @serial
     */
    String zoneStrings[][] = null;

    /**
     * Indicates that zoneStrings is set externally with setZoneStrings() method.
     * <p>
     *  表示使用setZoneStrings()方法在外部设置zoneStrings。
     * 
     */
    transient boolean isZoneStringsSet = false;

    /**
     * Unlocalized date-time pattern characters. For example: 'y', 'd', etc.
     * All locales use the same these unlocalized pattern characters.
     * <p>
     *  未定位的日期/时间模式字符。例如：'y','d'等。所有区域设置使用相同的这些非定位模式字符。
     * 
     */
    static final String  patternChars = "GyMdkHmsSEDFwWahKzZYuXL";

    static final int PATTERN_ERA                  =  0; // G
    static final int PATTERN_YEAR                 =  1; // y
    static final int PATTERN_MONTH                =  2; // M
    static final int PATTERN_DAY_OF_MONTH         =  3; // d
    static final int PATTERN_HOUR_OF_DAY1         =  4; // k
    static final int PATTERN_HOUR_OF_DAY0         =  5; // H
    static final int PATTERN_MINUTE               =  6; // m
    static final int PATTERN_SECOND               =  7; // s
    static final int PATTERN_MILLISECOND          =  8; // S
    static final int PATTERN_DAY_OF_WEEK          =  9; // E
    static final int PATTERN_DAY_OF_YEAR          = 10; // D
    static final int PATTERN_DAY_OF_WEEK_IN_MONTH = 11; // F
    static final int PATTERN_WEEK_OF_YEAR         = 12; // w
    static final int PATTERN_WEEK_OF_MONTH        = 13; // W
    static final int PATTERN_AM_PM                = 14; // a
    static final int PATTERN_HOUR1                = 15; // h
    static final int PATTERN_HOUR0                = 16; // K
    static final int PATTERN_ZONE_NAME            = 17; // z
    static final int PATTERN_ZONE_VALUE           = 18; // Z
    static final int PATTERN_WEEK_YEAR            = 19; // Y
    static final int PATTERN_ISO_DAY_OF_WEEK      = 20; // u
    static final int PATTERN_ISO_ZONE             = 21; // X
    static final int PATTERN_MONTH_STANDALONE     = 22; // L

    /**
     * Localized date-time pattern characters. For example, a locale may
     * wish to use 'u' rather than 'y' to represent years in its date format
     * pattern strings.
     * This string must be exactly 18 characters long, with the index of
     * the characters described by <code>DateFormat.ERA_FIELD</code>,
     * <code>DateFormat.YEAR_FIELD</code>, etc.  Thus, if the string were
     * "Xz...", then localized patterns would use 'X' for era and 'z' for year.
     * <p>
     *  本地化日期/时间模式字符。例如,语言环境可能希望使用"u"而不是"y"来表示其日期格式模式字符串中的年份。
     * 此字符串必须正好为18个字符长,具有由<code> DateFormat.ERA_FIELD </code>,<code> DateFormat.YEAR_FIELD </code>等描述的字符的索引。
     *  本地化日期/时间模式字符。例如,语言环境可能希望使用"u"而不是"y"来表示其日期格式模式字符串中的年份。因此,如果字符串为"Xz。 ..",那么本地化模式将使用"X"表示时代,"z"表示年。
     * 
     * 
     * @serial
     */
    String  localPatternChars = null;

    /**
     * The locale which is used for initializing this DateFormatSymbols object.
     *
     * <p>
     *  用于初始化此DateFormatSymbols对象的语言环境。
     * 
     * 
     * @since 1.6
     * @serial
     */
    Locale locale = null;

    /* use serialVersionUID from JDK 1.1.4 for interoperability */
    static final long serialVersionUID = -5987973545549424702L;

    /**
     * Returns an array of all locales for which the
     * <code>getInstance</code> methods of this class can return
     * localized instances.
     * The returned array represents the union of locales supported by the
     * Java runtime and by installed
     * {@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * implementations.  It must contain at least a <code>Locale</code>
     * instance equal to {@link java.util.Locale#US Locale.US}.
     *
     * <p>
     *  返回所有语言环境的数组,其中此类的<code> getInstance </code>方法可以返回本地化实例。
     * 返回的数组表示Java运行时支持的语言环境的并集,以及安装的{@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * 实现。
     *  返回所有语言环境的数组,其中此类的<code> getInstance </code>方法可以返回本地化实例。
     * 它必须至少包含等于{@link java.util.Locale#US Locale.US}的<code> Locale </code>实例。
     * 
     * 
     * @return An array of locales for which localized
     *         <code>DateFormatSymbols</code> instances are available.
     * @since 1.6
     */
    public static Locale[] getAvailableLocales() {
        LocaleServiceProviderPool pool=
            LocaleServiceProviderPool.getPool(DateFormatSymbolsProvider.class);
        return pool.getAvailableLocales();
    }

    /**
     * Gets the <code>DateFormatSymbols</code> instance for the default
     * locale.  This method provides access to <code>DateFormatSymbols</code>
     * instances for locales supported by the Java runtime itself as well
     * as for those supported by installed
     * {@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * implementations.
     * <p>This is equivalent to calling {@link #getInstance(Locale)
     *     getInstance(Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     * 获取默认语言环境的<code> DateFormatSymbols </code>实例。
     * 此方法提供对Java运行时本身支持的语言环境以及已安装的{@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * 实现支持的语言环境的<code> DateFormatSymbols </code>实例的访问。
     * 获取默认语言环境的<code> DateFormatSymbols </code>实例。
     *  <p>这相当于调用{@link #getInstance(Locale)getInstance(Locale.getDefault(Locale.Category.FORMAT))}。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @return a <code>DateFormatSymbols</code> instance.
     * @since 1.6
     */
    public static final DateFormatSymbols getInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the <code>DateFormatSymbols</code> instance for the specified
     * locale.  This method provides access to <code>DateFormatSymbols</code>
     * instances for locales supported by the Java runtime itself as well
     * as for those supported by installed
     * {@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * implementations.
     * <p>
     *  获取指定语言环境的<code> DateFormatSymbols </code>实例。
     * 此方法提供对Java运行时本身支持的语言环境以及已安装的{@link java.text.spi.DateFormatSymbolsProvider DateFormatSymbolsProvider}
     * 实现支持的语言环境的<code> DateFormatSymbols </code>实例的访问。
     *  获取指定语言环境的<code> DateFormatSymbols </code>实例。
     * 
     * 
     * @param locale the given locale.
     * @return a <code>DateFormatSymbols</code> instance.
     * @exception NullPointerException if <code>locale</code> is null
     * @since 1.6
     */
    public static final DateFormatSymbols getInstance(Locale locale) {
        DateFormatSymbols dfs = getProviderInstance(locale);
        if (dfs != null) {
            return dfs;
        }
        throw new RuntimeException("DateFormatSymbols instance creation failed.");
    }

    /**
     * Returns a DateFormatSymbols provided by a provider or found in
     * the cache. Note that this method returns a cached instance,
     * not its clone. Therefore, the instance should never be given to
     * an application.
     * <p>
     *  返回由提供程序提供或在缓存中找到的DateFormatSymbol。请注意,此方法返回一个缓存的实例,而不是它的克隆。因此,不应该将该实例分配给应用程序。
     * 
     */
    static final DateFormatSymbols getInstanceRef(Locale locale) {
        DateFormatSymbols dfs = getProviderInstance(locale);
        if (dfs != null) {
            return dfs;
        }
        throw new RuntimeException("DateFormatSymbols instance creation failed.");
    }

    private static DateFormatSymbols getProviderInstance(Locale locale) {
        LocaleProviderAdapter adapter = LocaleProviderAdapter.getAdapter(DateFormatSymbolsProvider.class, locale);
        DateFormatSymbolsProvider provider = adapter.getDateFormatSymbolsProvider();
        DateFormatSymbols dfsyms = provider.getInstance(locale);
        if (dfsyms == null) {
            provider = LocaleProviderAdapter.forJRE().getDateFormatSymbolsProvider();
            dfsyms = provider.getInstance(locale);
        }
        return dfsyms;
    }

    /**
     * Gets era strings. For example: "AD" and "BC".
     * <p>
     *  获取时代字符串。例如："AD"和"BC"。
     * 
     * 
     * @return the era strings.
     */
    public String[] getEras() {
        return Arrays.copyOf(eras, eras.length);
    }

    /**
     * Sets era strings. For example: "AD" and "BC".
     * <p>
     *  设置时代字符串。例如："AD"和"BC"。
     * 
     * 
     * @param newEras the new era strings.
     */
    public void setEras(String[] newEras) {
        eras = Arrays.copyOf(newEras, newEras.length);
        cachedHashCode = 0;
    }

    /**
     * Gets month strings. For example: "January", "February", etc.
     *
     * <p>If the language requires different forms for formatting and
     * stand-alone usages, this method returns month names in the
     * formatting form. For example, the preferred month name for
     * January in the Czech language is <em>ledna</em> in the
     * formatting form, while it is <em>leden</em> in the stand-alone
     * form. This method returns {@code "ledna"} in this case. Refer
     * to the <a href="http://unicode.org/reports/tr35/#Calendar_Elements">
     * Calendar Elements in the Unicode Locale Data Markup Language
     * (LDML) specification</a> for more details.
     *
     * <p>
     *  获取月份字符串。例如："一月","二月"等。
     * 
     * <p>如果语言对格式化和独立使用需要不同的格式,则此方法以格式化形式返回月份名称。例如,捷克语中1月的首选月份名称为<em> ledna </em>,格式为<led> </em>为独立表单。
     * 在这种情况下,此方法返回{@code"ledna"}。
     * 有关详细信息,请参阅Unicode语言环境数据标记语言(LDML)规范中的<a href="http://unicode.org/reports/tr35/#Calendar_Elements">日历元
     * 素</a>。
     * 在这种情况下,此方法返回{@code"ledna"}。
     * 
     * 
     * @return the month strings.
     */
    public String[] getMonths() {
        return Arrays.copyOf(months, months.length);
    }

    /**
     * Sets month strings. For example: "January", "February", etc.
     * <p>
     *  设置月份字符串。例如："一月","二月"等。
     * 
     * 
     * @param newMonths the new month strings.
     */
    public void setMonths(String[] newMonths) {
        months = Arrays.copyOf(newMonths, newMonths.length);
        cachedHashCode = 0;
    }

    /**
     * Gets short month strings. For example: "Jan", "Feb", etc.
     *
     * <p>If the language requires different forms for formatting and
     * stand-alone usages, This method returns short month names in
     * the formatting form. For example, the preferred abbreviation
     * for January in the Catalan language is <em>de gen.</em> in the
     * formatting form, while it is <em>gen.</em> in the stand-alone
     * form. This method returns {@code "de gen."} in this case. Refer
     * to the <a href="http://unicode.org/reports/tr35/#Calendar_Elements">
     * Calendar Elements in the Unicode Locale Data Markup Language
     * (LDML) specification</a> for more details.
     *
     * <p>
     *  获取短的月份字符串。例如："Jan","Feb"等
     * 
     *  <p>如果语言对格式化和独立使用需要不同的格式,则此方法以格式化形式返回短的月份名称。例如,加泰罗尼亚语中一月的优选缩写是格式化形式的<em> </em>,而<em> </em>是独立形式。
     * 在这种情况下,此方法返回{@code"de gen。"}。
     * 有关详细信息,请参阅Unicode语言环境数据标记语言(LDML)规范中的<a href="http://unicode.org/reports/tr35/#Calendar_Elements">日历元
     * 素</a>。
     * 在这种情况下,此方法返回{@code"de gen。"}。
     * 
     * 
     * @return the short month strings.
     */
    public String[] getShortMonths() {
        return Arrays.copyOf(shortMonths, shortMonths.length);
    }

    /**
     * Sets short month strings. For example: "Jan", "Feb", etc.
     * <p>
     *  设置短的月份字符串。例如："Jan","Feb"等
     * 
     * 
     * @param newShortMonths the new short month strings.
     */
    public void setShortMonths(String[] newShortMonths) {
        shortMonths = Arrays.copyOf(newShortMonths, newShortMonths.length);
        cachedHashCode = 0;
    }

    /**
     * Gets weekday strings. For example: "Sunday", "Monday", etc.
     * <p>
     *  获取工作日字符串。例如："星期日","星期一"等。
     * 
     * 
     * @return the weekday strings. Use <code>Calendar.SUNDAY</code>,
     * <code>Calendar.MONDAY</code>, etc. to index the result array.
     */
    public String[] getWeekdays() {
        return Arrays.copyOf(weekdays, weekdays.length);
    }

    /**
     * Sets weekday strings. For example: "Sunday", "Monday", etc.
     * <p>
     *  设置周日字符串。例如："星期日","星期一"等。
     * 
     * 
     * @param newWeekdays the new weekday strings. The array should
     * be indexed by <code>Calendar.SUNDAY</code>,
     * <code>Calendar.MONDAY</code>, etc.
     */
    public void setWeekdays(String[] newWeekdays) {
        weekdays = Arrays.copyOf(newWeekdays, newWeekdays.length);
        cachedHashCode = 0;
    }

    /**
     * Gets short weekday strings. For example: "Sun", "Mon", etc.
     * <p>
     *  获取较短的星期字符串。例如："Sun","Mon"等
     * 
     * 
     * @return the short weekday strings. Use <code>Calendar.SUNDAY</code>,
     * <code>Calendar.MONDAY</code>, etc. to index the result array.
     */
    public String[] getShortWeekdays() {
        return Arrays.copyOf(shortWeekdays, shortWeekdays.length);
    }

    /**
     * Sets short weekday strings. For example: "Sun", "Mon", etc.
     * <p>
     * 设置星期几字符串。例如："Sun","Mon"等
     * 
     * 
     * @param newShortWeekdays the new short weekday strings. The array should
     * be indexed by <code>Calendar.SUNDAY</code>,
     * <code>Calendar.MONDAY</code>, etc.
     */
    public void setShortWeekdays(String[] newShortWeekdays) {
        shortWeekdays = Arrays.copyOf(newShortWeekdays, newShortWeekdays.length);
        cachedHashCode = 0;
    }

    /**
     * Gets ampm strings. For example: "AM" and "PM".
     * <p>
     *  获取ampm字符串。例如："AM"和"PM"。
     * 
     * 
     * @return the ampm strings.
     */
    public String[] getAmPmStrings() {
        return Arrays.copyOf(ampms, ampms.length);
    }

    /**
     * Sets ampm strings. For example: "AM" and "PM".
     * <p>
     *  设置ampm字符串。例如："AM"和"PM"。
     * 
     * 
     * @param newAmpms the new ampm strings.
     */
    public void setAmPmStrings(String[] newAmpms) {
        ampms = Arrays.copyOf(newAmpms, newAmpms.length);
        cachedHashCode = 0;
    }

    /**
     * Gets time zone strings.  Use of this method is discouraged; use
     * {@link java.util.TimeZone#getDisplayName() TimeZone.getDisplayName()}
     * instead.
     * <p>
     * The value returned is a
     * two-dimensional array of strings of size <em>n</em> by <em>m</em>,
     * where <em>m</em> is at least 5.  Each of the <em>n</em> rows is an
     * entry containing the localized names for a single <code>TimeZone</code>.
     * Each such row contains (with <code>i</code> ranging from
     * 0..<em>n</em>-1):
     * <ul>
     * <li><code>zoneStrings[i][0]</code> - time zone ID</li>
     * <li><code>zoneStrings[i][1]</code> - long name of zone in standard
     * time</li>
     * <li><code>zoneStrings[i][2]</code> - short name of zone in
     * standard time</li>
     * <li><code>zoneStrings[i][3]</code> - long name of zone in daylight
     * saving time</li>
     * <li><code>zoneStrings[i][4]</code> - short name of zone in daylight
     * saving time</li>
     * </ul>
     * The zone ID is <em>not</em> localized; it's one of the valid IDs of
     * the {@link java.util.TimeZone TimeZone} class that are not
     * <a href="../util/TimeZone.html#CustomID">custom IDs</a>.
     * All other entries are localized names.  If a zone does not implement
     * daylight saving time, the daylight saving time names should not be used.
     * <p>
     * If {@link #setZoneStrings(String[][]) setZoneStrings} has been called
     * on this <code>DateFormatSymbols</code> instance, then the strings
     * provided by that call are returned. Otherwise, the returned array
     * contains names provided by the Java runtime and by installed
     * {@link java.util.spi.TimeZoneNameProvider TimeZoneNameProvider}
     * implementations.
     *
     * <p>
     *  获取时区字符串。不鼓励使用此方法;请改用{@link java.util.TimeZone#getDisplayName()TimeZone.getDisplayName()}。
     * <p>
     *  返回的值是由<em> m </em>大小为<em> n </em>的字符串的二维数组,其中<em> m </em>至少为5. <em> > n </em> rows是包含单个<code> TimeZo
     * ne </code>的本地化名称的条目。
     * 每个这样的行包含(与<code> i </code>范围从0 .. <em> n </em> -1)：。
     * <ul>
     *  <li> <code> zoneStrings [i] [0] </code>  - 时区ID </li> <li> <code> zoneStrings [i] [1] </li> <li> <code>
     *  zoneStrings [i] [2] </code>  - 标准时间区域的短名称</li> <li> <code> zoneStrings [i] - 区域在夏令时的长名称</li> <li> <code>
     *  zoneStrings [i] [4] </code>。
     * </ul>
     *  区域ID未本地化</em>;它是{@link java.util.TimeZone TimeZone}类的有效ID之一,不是<a href="../util/TimeZone.html#CustomID">
     * 自定义ID </a>。
     * 所有其他条目都是本地化名称。如果区域未实施夏令时,则不应使用夏令时名称。
     * <p>
     * 如果在此<code> DateFormatSymbols </code>实例上调用了{@link #setZoneStrings(String [] [])setZoneStrings},则返回该调用提
     * 供的字符串。
     * 否则,返回的数组包含由Java运行时和安装的{@link java.util.spi.TimeZoneNameProvider TimeZoneNameProvider}实现提供的名称。
     * 
     * 
     * @return the time zone strings.
     * @see #setZoneStrings(String[][])
     */
    public String[][] getZoneStrings() {
        return getZoneStringsImpl(true);
    }

    /**
     * Sets time zone strings.  The argument must be a
     * two-dimensional array of strings of size <em>n</em> by <em>m</em>,
     * where <em>m</em> is at least 5.  Each of the <em>n</em> rows is an
     * entry containing the localized names for a single <code>TimeZone</code>.
     * Each such row contains (with <code>i</code> ranging from
     * 0..<em>n</em>-1):
     * <ul>
     * <li><code>zoneStrings[i][0]</code> - time zone ID</li>
     * <li><code>zoneStrings[i][1]</code> - long name of zone in standard
     * time</li>
     * <li><code>zoneStrings[i][2]</code> - short name of zone in
     * standard time</li>
     * <li><code>zoneStrings[i][3]</code> - long name of zone in daylight
     * saving time</li>
     * <li><code>zoneStrings[i][4]</code> - short name of zone in daylight
     * saving time</li>
     * </ul>
     * The zone ID is <em>not</em> localized; it's one of the valid IDs of
     * the {@link java.util.TimeZone TimeZone} class that are not
     * <a href="../util/TimeZone.html#CustomID">custom IDs</a>.
     * All other entries are localized names.
     *
     * <p>
     *  设置时区字符串。
     * 参数必须是由<em> m </em>大小为<em> n </em>的字符串的二维数组,其中<em> m </em>至少为5. <em> > n </em> rows是包含单个<code> TimeZon
     * e </code>的本地化名称的条目。
     *  设置时区字符串。每个这样的行包含(与<code> i </code>范围从0 .. <em> n </em> -1)：。
     * <ul>
     *  <li> <code> zoneStrings [i] [0] </code>  - 时区ID </li> <li> <code> zoneStrings [i] [1] </li> <li> <code>
     *  zoneStrings [i] [2] </code>  - 标准时间区域的短名称</li> <li> <code> zoneStrings [i] - 区域在夏令时的长名称</li> <li> <code>
     *  zoneStrings [i] [4] </code>。
     * </ul>
     *  区域ID未本地化</em>;它是{@link java.util.TimeZone TimeZone}类的有效ID之一,不是<a href="../util/TimeZone.html#CustomID">
     * 自定义ID </a>。
     * 所有其他条目都是本地化名称。
     * 
     * 
     * @param newZoneStrings the new time zone strings.
     * @exception IllegalArgumentException if the length of any row in
     *    <code>newZoneStrings</code> is less than 5
     * @exception NullPointerException if <code>newZoneStrings</code> is null
     * @see #getZoneStrings()
     */
    public void setZoneStrings(String[][] newZoneStrings) {
        String[][] aCopy = new String[newZoneStrings.length][];
        for (int i = 0; i < newZoneStrings.length; ++i) {
            int len = newZoneStrings[i].length;
            if (len < 5) {
                throw new IllegalArgumentException();
            }
            aCopy[i] = Arrays.copyOf(newZoneStrings[i], len);
        }
        zoneStrings = aCopy;
        isZoneStringsSet = true;
        cachedHashCode = 0;
    }

    /**
     * Gets localized date-time pattern characters. For example: 'u', 't', etc.
     * <p>
     *  获取本地化的日期时间模式字符。例如：'u','t'等
     * 
     * 
     * @return the localized date-time pattern characters.
     */
    public String getLocalPatternChars() {
        return localPatternChars;
    }

    /**
     * Sets localized date-time pattern characters. For example: 'u', 't', etc.
     * <p>
     *  设置本地化的日期/时间模式字符。例如：'u','t'等
     * 
     * 
     * @param newLocalPatternChars the new localized date-time
     * pattern characters.
     */
    public void setLocalPatternChars(String newLocalPatternChars) {
        // Call toString() to throw an NPE in case the argument is null
        localPatternChars = newLocalPatternChars.toString();
        cachedHashCode = 0;
    }

    /**
     * Overrides Cloneable
     * <p>
     *  覆盖可克隆
     * 
     */
    public Object clone()
    {
        try
        {
            DateFormatSymbols other = (DateFormatSymbols)super.clone();
            copyMembers(this, other);
            return other;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Override hashCode.
     * Generates a hash code for the DateFormatSymbols object.
     * <p>
     * 覆盖hashCode。为DateFormatSymbols对象生成哈希代码。
     * 
     */
    @Override
    public int hashCode() {
        int hashCode = cachedHashCode;
        if (hashCode == 0) {
            hashCode = 5;
            hashCode = 11 * hashCode + Arrays.hashCode(eras);
            hashCode = 11 * hashCode + Arrays.hashCode(months);
            hashCode = 11 * hashCode + Arrays.hashCode(shortMonths);
            hashCode = 11 * hashCode + Arrays.hashCode(weekdays);
            hashCode = 11 * hashCode + Arrays.hashCode(shortWeekdays);
            hashCode = 11 * hashCode + Arrays.hashCode(ampms);
            hashCode = 11 * hashCode + Arrays.deepHashCode(getZoneStringsWrapper());
            hashCode = 11 * hashCode + Objects.hashCode(localPatternChars);
            cachedHashCode = hashCode;
        }

        return hashCode;
    }

    /**
     * Override equals
     * <p>
     *  覆盖等于
     * 
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DateFormatSymbols that = (DateFormatSymbols) obj;
        return (Arrays.equals(eras, that.eras)
                && Arrays.equals(months, that.months)
                && Arrays.equals(shortMonths, that.shortMonths)
                && Arrays.equals(weekdays, that.weekdays)
                && Arrays.equals(shortWeekdays, that.shortWeekdays)
                && Arrays.equals(ampms, that.ampms)
                && Arrays.deepEquals(getZoneStringsWrapper(), that.getZoneStringsWrapper())
                && ((localPatternChars != null
                  && localPatternChars.equals(that.localPatternChars))
                 || (localPatternChars == null
                  && that.localPatternChars == null)));
    }

    // =======================privates===============================

    /**
     * Useful constant for defining time zone offsets.
     * <p>
     *  用于定义时区偏移的常用常数。
     * 
     */
    static final int millisPerHour = 60*60*1000;

    /**
     * Cache to hold DateFormatSymbols instances per Locale.
     * <p>
     *  缓存以保存每个Locale的DateFormatSymbols实例。
     * 
     */
    private static final ConcurrentMap<Locale, SoftReference<DateFormatSymbols>> cachedInstances
        = new ConcurrentHashMap<>(3);

    private transient int lastZoneIndex = 0;

    /**
     * Cached hash code
     * <p>
     *  缓存哈希码
     * 
     */
    transient volatile int cachedHashCode = 0;

    private void initializeData(Locale desiredLocale) {
        locale = desiredLocale;

        // Copy values of a cached instance if any.
        SoftReference<DateFormatSymbols> ref = cachedInstances.get(locale);
        DateFormatSymbols dfs;
        if (ref != null && (dfs = ref.get()) != null) {
            copyMembers(dfs, this);
            return;
        }

        // Initialize the fields from the ResourceBundle for locale.
        LocaleProviderAdapter adapter = LocaleProviderAdapter.getAdapter(DateFormatSymbolsProvider.class, locale);
        // Avoid any potential recursions
        if (!(adapter instanceof ResourceBundleBasedAdapter)) {
            adapter = LocaleProviderAdapter.getResourceBundleBased();
        }
        ResourceBundle resource = ((ResourceBundleBasedAdapter)adapter).getLocaleData().getDateFormatData(locale);

        // JRE and CLDR use different keys
        // JRE: Eras, short.Eras and narrow.Eras
        // CLDR: long.Eras, Eras and narrow.Eras
        if (resource.containsKey("Eras")) {
            eras = resource.getStringArray("Eras");
        } else if (resource.containsKey("long.Eras")) {
            eras = resource.getStringArray("long.Eras");
        } else if (resource.containsKey("short.Eras")) {
            eras = resource.getStringArray("short.Eras");
        }
        months = resource.getStringArray("MonthNames");
        shortMonths = resource.getStringArray("MonthAbbreviations");
        ampms = resource.getStringArray("AmPmMarkers");
        localPatternChars = resource.getString("DateTimePatternChars");

        // Day of week names are stored in a 1-based array.
        weekdays = toOneBasedArray(resource.getStringArray("DayNames"));
        shortWeekdays = toOneBasedArray(resource.getStringArray("DayAbbreviations"));

        // Put a clone in the cache
        ref = new SoftReference<>((DateFormatSymbols)this.clone());
        SoftReference<DateFormatSymbols> x = cachedInstances.putIfAbsent(locale, ref);
        if (x != null) {
            DateFormatSymbols y = x.get();
            if (y == null) {
                // Replace the empty SoftReference with ref.
                cachedInstances.put(locale, ref);
            }
        }
    }

    private static String[] toOneBasedArray(String[] src) {
        int len = src.length;
        String[] dst = new String[len + 1];
        dst[0] = "";
        for (int i = 0; i < len; i++) {
            dst[i + 1] = src[i];
        }
        return dst;
    }

    /**
     * Package private: used by SimpleDateFormat
     * Gets the index for the given time zone ID to obtain the time zone
     * strings for formatting. The time zone ID is just for programmatic
     * lookup. NOT LOCALIZED!!!
     * <p>
     *  包私有：由SimpleDateFormat使用获取给定时区ID的索引,以获取格式化的时区字符串。时区ID仅用于程序查找。没有本地化！
     * 
     * 
     * @param ID the given time zone ID.
     * @return the index of the given time zone ID.  Returns -1 if
     * the given time zone ID can't be located in the DateFormatSymbols object.
     * @see java.util.SimpleTimeZone
     */
    final int getZoneIndex(String ID) {
        String[][] zoneStrings = getZoneStringsWrapper();

        /*
         * getZoneIndex has been re-written for performance reasons. instead of
         * traversing the zoneStrings array every time, we cache the last used zone
         * index
         * <p>
         *  由于性能原因,getZoneIndex已被重写。而不是每次遍历zoneStrings数组,我们缓存最后使用的区域索引
         * 
         */
        if (lastZoneIndex < zoneStrings.length && ID.equals(zoneStrings[lastZoneIndex][0])) {
            return lastZoneIndex;
        }

        /* slow path, search entire list */
        for (int index = 0; index < zoneStrings.length; index++) {
            if (ID.equals(zoneStrings[index][0])) {
                lastZoneIndex = index;
                return index;
            }
        }

        return -1;
    }

    /**
     * Wrapper method to the getZoneStrings(), which is called from inside
     * the java.text package and not to mutate the returned arrays, so that
     * it does not need to create a defensive copy.
     * <p>
     *  getZoneStrings()的包装器方法,它从java.text包中调用,而不是改变返回的数组,这样它不需要创建防御副本。
     * 
     */
    final String[][] getZoneStringsWrapper() {
        if (isSubclassObject()) {
            return getZoneStrings();
        } else {
            return getZoneStringsImpl(false);
        }
    }

    private String[][] getZoneStringsImpl(boolean needsCopy) {
        if (zoneStrings == null) {
            zoneStrings = TimeZoneNameUtility.getZoneStrings(locale);
        }

        if (!needsCopy) {
            return zoneStrings;
        }

        int len = zoneStrings.length;
        String[][] aCopy = new String[len][];
        for (int i = 0; i < len; i++) {
            aCopy[i] = Arrays.copyOf(zoneStrings[i], zoneStrings[i].length);
        }
        return aCopy;
    }

    private boolean isSubclassObject() {
        return !getClass().getName().equals("java.text.DateFormatSymbols");
    }

    /**
     * Clones all the data members from the source DateFormatSymbols to
     * the target DateFormatSymbols. This is only for subclasses.
     * <p>
     *  将所有数据成员从源DateFormatSymbol克隆到目标DateFormatSymbols。这只适用于子类。
     * 
     * 
     * @param src the source DateFormatSymbols.
     * @param dst the target DateFormatSymbols.
     */
    private void copyMembers(DateFormatSymbols src, DateFormatSymbols dst)
    {
        dst.eras = Arrays.copyOf(src.eras, src.eras.length);
        dst.months = Arrays.copyOf(src.months, src.months.length);
        dst.shortMonths = Arrays.copyOf(src.shortMonths, src.shortMonths.length);
        dst.weekdays = Arrays.copyOf(src.weekdays, src.weekdays.length);
        dst.shortWeekdays = Arrays.copyOf(src.shortWeekdays, src.shortWeekdays.length);
        dst.ampms = Arrays.copyOf(src.ampms, src.ampms.length);
        if (src.zoneStrings != null) {
            dst.zoneStrings = src.getZoneStringsImpl(true);
        } else {
            dst.zoneStrings = null;
        }
        dst.localPatternChars = src.localPatternChars;
        dst.cachedHashCode = 0;
    }

    /**
     * Write out the default serializable data, after ensuring the
     * <code>zoneStrings</code> field is initialized in order to make
     * sure the backward compatibility.
     *
     * <p>
     *  写出默认的可序列化数据,确保<code> zoneStrings </code>字段被初始化以确保向后兼容性。
     * 
     * @since 1.6
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        if (zoneStrings == null) {
            zoneStrings = TimeZoneNameUtility.getZoneStrings(locale);
        }
        stream.defaultWriteObject();
    }
}
