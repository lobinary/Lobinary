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
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2007-2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;

/**
 * A time-zone ID, such as {@code Europe/Paris}.
 * <p>
 * A {@code ZoneId} is used to identify the rules used to convert between
 * an {@link Instant} and a {@link LocalDateTime}.
 * There are two distinct types of ID:
 * <ul>
 * <li>Fixed offsets - a fully resolved offset from UTC/Greenwich, that uses
 *  the same offset for all local date-times
 * <li>Geographical regions - an area where a specific set of rules for finding
 *  the offset from UTC/Greenwich apply
 * </ul>
 * Most fixed offsets are represented by {@link ZoneOffset}.
 * Calling {@link #normalized()} on any {@code ZoneId} will ensure that a
 * fixed offset ID will be represented as a {@code ZoneOffset}.
 * <p>
 * The actual rules, describing when and how the offset changes, are defined by {@link ZoneRules}.
 * This class is simply an ID used to obtain the underlying rules.
 * This approach is taken because rules are defined by governments and change
 * frequently, whereas the ID is stable.
 * <p>
 * The distinction has other effects. Serializing the {@code ZoneId} will only send
 * the ID, whereas serializing the rules sends the entire data set.
 * Similarly, a comparison of two IDs only examines the ID, whereas
 * a comparison of two rules examines the entire data set.
 *
 * <h3>Time-zone IDs</h3>
 * The ID is unique within the system.
 * There are three types of ID.
 * <p>
 * The simplest type of ID is that from {@code ZoneOffset}.
 * This consists of 'Z' and IDs starting with '+' or '-'.
 * <p>
 * The next type of ID are offset-style IDs with some form of prefix,
 * such as 'GMT+2' or 'UTC+01:00'.
 * The recognised prefixes are 'UTC', 'GMT' and 'UT'.
 * The offset is the suffix and will be normalized during creation.
 * These IDs can be normalized to a {@code ZoneOffset} using {@code normalized()}.
 * <p>
 * The third type of ID are region-based IDs. A region-based ID must be of
 * two or more characters, and not start with 'UTC', 'GMT', 'UT' '+' or '-'.
 * Region-based IDs are defined by configuration, see {@link ZoneRulesProvider}.
 * The configuration focuses on providing the lookup from the ID to the
 * underlying {@code ZoneRules}.
 * <p>
 * Time-zone rules are defined by governments and change frequently.
 * There are a number of organizations, known here as groups, that monitor
 * time-zone changes and collate them.
 * The default group is the IANA Time Zone Database (TZDB).
 * Other organizations include IATA (the airline industry body) and Microsoft.
 * <p>
 * Each group defines its own format for the region ID it provides.
 * The TZDB group defines IDs such as 'Europe/London' or 'America/New_York'.
 * TZDB IDs take precedence over other groups.
 * <p>
 * It is strongly recommended that the group name is included in all IDs supplied by
 * groups other than TZDB to avoid conflicts. For example, IATA airline time-zone
 * region IDs are typically the same as the three letter airport code.
 * However, the airport of Utrecht has the code 'UTC', which is obviously a conflict.
 * The recommended format for region IDs from groups other than TZDB is 'group~region'.
 * Thus if IATA data were defined, Utrecht airport would be 'IATA~UTC'.
 *
 * <h3>Serialization</h3>
 * This class can be serialized and stores the string zone ID in the external form.
 * The {@code ZoneOffset} subclass uses a dedicated format that only stores the
 * offset from UTC/Greenwich.
 * <p>
 * A {@code ZoneId} can be deserialized in a Java Runtime where the ID is unknown.
 * For example, if a server-side Java Runtime has been updated with a new zone ID, but
 * the client-side Java Runtime has not been updated. In this case, the {@code ZoneId}
 * object will exist, and can be queried using {@code getId}, {@code equals},
 * {@code hashCode}, {@code toString}, {@code getDisplayName} and {@code normalized}.
 * However, any call to {@code getRules} will fail with {@code ZoneRulesException}.
 * This approach is designed to allow a {@link ZonedDateTime} to be loaded and
 * queried, but not modified, on a Java Runtime with incomplete time-zone information.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code ZoneId} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This abstract class has two implementations, both of which are immutable and thread-safe.
 * One implementation models region-based IDs, the other is {@code ZoneOffset} modelling
 * offset-based IDs. This difference is visible in serialization.
 *
 * <p>
 *  时区ID,例如{@code Europe / Paris}。
 * <p>
 *  {@code ZoneId}用于标识用于在{@link Instant}和{@link LocalDateTime}之间进行转换的规则。有两种不同类型的ID：
 * <ul>
 *  <li>固定偏移 - 与UTC /格林威治完全偏移的偏移,在所有本地日期时间使用相同的偏移<li>地理区域 - 用于查找UTC /格林威治偏移的特定规则集合的区域
 * </ul>
 *  大多数固定偏移由{@link ZoneOffset}表示。
 * 在任何{@code ZoneId}上调用{@link #normalized()}将确保固定的偏移ID将表示为{@code ZoneOffset}。
 * <p>
 * 描述偏移变化的时间和方式的实际规则由{@link ZoneRules}定义。这个类只是一个用于获取基础规则的ID。采用这种方法是因为规则由政府定义并且频繁变化,而ID是稳定的。
 * <p>
 *  区别有其他影响。序列化{@code ZoneId}只会发送ID,而序列化规则会发送整个数据集。类似地,两个ID的比较仅检查ID,而两个规则的比较检查整个数据集。
 * 
 *  <h3>时区ID </h3> ID在系统中是唯一的。有三种类型的ID。
 * <p>
 *  最简单的ID类型是{@code ZoneOffset}。这由"Z"和以"+"或" - "开头的ID组成。
 * <p>
 *  下一种类型的ID是具有某种形式的前缀的偏移样式ID,例如"GMT + 2"或"UTC + 01：00"。识别的前缀是"UTC","GMT"和"UT"。偏移量是后缀,将在创建过程中进行标准化。
 * 这些ID可以使用{@code normalized()}标准化为{@code ZoneOffset}。
 * <p>
 *  第三类型的ID是基于区域的ID。基于区域的ID必须为两个或多个字符,且不能以"UTC","GMT","UT","+"或" - "开头。
 * 基于区域的ID由配置定义,请参阅{@link ZoneRulesProvider}。配置重点在于提供从ID到底层{@code ZoneRules}的查找。
 * <p>
 * 时区规则由政府定义并经常变化。有许多组织,这里称为组,监视时区更改和整理它们。默认组是IANA时区数据库(TZDB)。其他组织包括IATA(航空工业机构)和Microsoft。
 * <p>
 *  每个组为其提供的区域ID定义自己的格式。 TZDB组定义了"Europe / London"或"America / New_York"等ID。 TZDB ID优先于其他组。
 * <p>
 *  强烈建议组名称包括在除TZDB以外的组提供的所有ID中,以避免冲突。例如,IATA航空公司时区区域ID通常与三字母机场代码相同。然而,乌得勒支的机场有代码"UTC",这显然是一个冲突。
 * 来自TZDB以外的组的区域ID的推荐格式为"group〜region"。因此,如果IATA数据被定义,Utrecht机场将是'IATA〜UTC'。
 * 
 *  <h3>序列化</h3>此类可以序列化,并以外部形式存储字符串区域ID。 {@code ZoneOffset}子类使用专用格式,只存储UTC / Greenwich的偏移量。
 * <p>
 * {@code ZoneId}可以在未知ID的Java运行时反序列化。例如,如果服务器端Java运行时已使用新的区域ID更新,但客户端Java运行时尚未更新。
 * 在这种情况下,{@code ZoneId}对象将存在,并且可以使用{@code getId},{@code equals},{@code hashCode},{@code toString},{@code getDisplayName}
 *  @code normalized}。
 * {@code ZoneId}可以在未知ID的Java运行时反序列化。例如,如果服务器端Java运行时已使用新的区域ID更新,但客户端Java运行时尚未更新。
 * 但是,对{@code getRules}的任何调用都将失败,并显示{@code ZoneRulesException}。
 * 此方法旨在允许在具有不完整时区信息的Java运行时上加载和查询{@link ZonedDateTime},但不进行修改。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code ZoneId}实例上使用身份敏感操作(包
 * 括引用等同性({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个抽象类有两个实现,它们都是不可变的和线程安全的。一个实现模型基于区域的ID,另一个是{@code ZoneOffset}建模基于偏移的ID。这种差异在序列化中可见。
 * 
 * 
 * @since 1.8
 */
public abstract class ZoneId implements Serializable {

    /**
     * A map of zone overrides to enable the short time-zone names to be used.
     * <p>
     * Use of short zone IDs has been deprecated in {@code java.util.TimeZone}.
     * This map allows the IDs to continue to be used via the
     * {@link #of(String, Map)} factory method.
     * <p>
     * This map contains a mapping of the IDs that is in line with TZDB 2005r and
     * later, where 'EST', 'MST' and 'HST' map to IDs which do not include daylight
     * savings.
     * <p>
     * This maps as follows:
     * <ul>
     * <li>EST - -05:00</li>
     * <li>HST - -10:00</li>
     * <li>MST - -07:00</li>
     * <li>ACT - Australia/Darwin</li>
     * <li>AET - Australia/Sydney</li>
     * <li>AGT - America/Argentina/Buenos_Aires</li>
     * <li>ART - Africa/Cairo</li>
     * <li>AST - America/Anchorage</li>
     * <li>BET - America/Sao_Paulo</li>
     * <li>BST - Asia/Dhaka</li>
     * <li>CAT - Africa/Harare</li>
     * <li>CNT - America/St_Johns</li>
     * <li>CST - America/Chicago</li>
     * <li>CTT - Asia/Shanghai</li>
     * <li>EAT - Africa/Addis_Ababa</li>
     * <li>ECT - Europe/Paris</li>
     * <li>IET - America/Indiana/Indianapolis</li>
     * <li>IST - Asia/Kolkata</li>
     * <li>JST - Asia/Tokyo</li>
     * <li>MIT - Pacific/Apia</li>
     * <li>NET - Asia/Yerevan</li>
     * <li>NST - Pacific/Auckland</li>
     * <li>PLT - Asia/Karachi</li>
     * <li>PNT - America/Phoenix</li>
     * <li>PRT - America/Puerto_Rico</li>
     * <li>PST - America/Los_Angeles</li>
     * <li>SST - Pacific/Guadalcanal</li>
     * <li>VST - Asia/Ho_Chi_Minh</li>
     * </ul>
     * The map is unmodifiable.
     * <p>
     *  区域覆盖的地图,以便使用短时区名称。
     * <p>
     *  {@code java.util.TimeZone}已弃用使用短区域ID。此地图允许通过{@link #of(String,Map)}工厂方法继续使用ID。
     * <p>
     * 此映射包含与TZDB 2005r及更高版本一致的ID映射,其中"EST","MST"和"HST"映射到不包括夏令时的ID。
     * <p>
     *  这个映射如下：
     * <ul>
     *  <li> EST  -  -05：00 </li> <li> HST  -  -10：00 </li> <li> MST  -  -07：00 </li> <li> ACT  -  Australia
     *  / Darwin </li > <li>澳大利亚/悉尼</li> <li> AGT  - 美洲/阿根廷/布宜诺斯艾利斯</li> <li> ART  - 非洲/开罗</li> <li> AST  - 
     * 美洲/ > <li> BET  - 美国/ Sao_Paulo </li> <li> BST  - 亚洲/达卡</li> <li> CAT  - 非洲/哈拉雷</li> <li> CNT-美国/ St_
     * Johns </li> </li> <li> CTT  - 亚洲/上海</li> <li> EAT  - 非洲/ Addis_Ababa </li> <li> ECT-欧洲/巴黎</li> <li> I
     * ET  -  America / Indiana / Indianapolis </li> <li> IST  - 亚洲/加尔各答</li> <li> JST-亚洲/东京</li> <li> MIT-太
     * 平洋/ NET  - 亚洲/埃里温</li> <li> NST  - 太平洋/奥克兰</li> <li> PLT-亚洲/卡拉奇</li> <li> PNT-美国/凤凰</li> <li> America
     *  / Puerto_Rico </li> <li> PST  - 美洲/ Los_Angeles </li> <li> SST  - 太平洋/ Guadalcanal </li> <li> VST  -
     *   Asia / Ho_Chi_Minh </li>。
     * </ul>
     *  地图是不可修改的。
     * 
     */
    public static final Map<String, String> SHORT_IDS;
    static {
        Map<String, String> map = new HashMap<>(64);
        map.put("ACT", "Australia/Darwin");
        map.put("AET", "Australia/Sydney");
        map.put("AGT", "America/Argentina/Buenos_Aires");
        map.put("ART", "Africa/Cairo");
        map.put("AST", "America/Anchorage");
        map.put("BET", "America/Sao_Paulo");
        map.put("BST", "Asia/Dhaka");
        map.put("CAT", "Africa/Harare");
        map.put("CNT", "America/St_Johns");
        map.put("CST", "America/Chicago");
        map.put("CTT", "Asia/Shanghai");
        map.put("EAT", "Africa/Addis_Ababa");
        map.put("ECT", "Europe/Paris");
        map.put("IET", "America/Indiana/Indianapolis");
        map.put("IST", "Asia/Kolkata");
        map.put("JST", "Asia/Tokyo");
        map.put("MIT", "Pacific/Apia");
        map.put("NET", "Asia/Yerevan");
        map.put("NST", "Pacific/Auckland");
        map.put("PLT", "Asia/Karachi");
        map.put("PNT", "America/Phoenix");
        map.put("PRT", "America/Puerto_Rico");
        map.put("PST", "America/Los_Angeles");
        map.put("SST", "Pacific/Guadalcanal");
        map.put("VST", "Asia/Ho_Chi_Minh");
        map.put("EST", "-05:00");
        map.put("MST", "-07:00");
        map.put("HST", "-10:00");
        SHORT_IDS = Collections.unmodifiableMap(map);
    }
    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 8352817235686L;

    //-----------------------------------------------------------------------
    /**
     * Gets the system default time-zone.
     * <p>
     * This queries {@link TimeZone#getDefault()} to find the default time-zone
     * and converts it to a {@code ZoneId}. If the system default time-zone is changed,
     * then the result of this method will also change.
     *
     * <p>
     *  获取系统默认时区。
     * <p>
     *  这将查询{@link TimeZone#getDefault()}以查找默认时区,并将其转换为{@code ZoneId}。如果系统默认时区更改,则此方法的结果也将更改。
     * 
     * 
     * @return the zone ID, not null
     * @throws DateTimeException if the converted zone ID has an invalid format
     * @throws ZoneRulesException if the converted zone region ID cannot be found
     */
    public static ZoneId systemDefault() {
        return TimeZone.getDefault().toZoneId();
    }

    /**
     * Gets the set of available zone IDs.
     * <p>
     * This set includes the string form of all available region-based IDs.
     * Offset-based zone IDs are not included in the returned set.
     * The ID can be passed to {@link #of(String)} to create a {@code ZoneId}.
     * <p>
     * The set of zone IDs can increase over time, although in a typical application
     * the set of IDs is fixed. Each call to this method is thread-safe.
     *
     * <p>
     *  获取可用区域ID集。
     * <p>
     * 此集包括所有可用的基于区域的ID的字符串形式。基于偏移的区域ID不包括在返回的集中。可以将ID传递给{@link #of(String)}以创建{@code ZoneId}。
     * <p>
     *  区域ID的集合可以随时间增加,但是在典型的应用中,ID的集合是固定的。每个对这个方法的调用都是线程安全的。
     * 
     * 
     * @return a modifiable copy of the set of zone IDs, not null
     */
    public static Set<String> getAvailableZoneIds() {
        return ZoneRulesProvider.getAvailableZoneIds();
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZoneId} using its ID using a map
     * of aliases to supplement the standard zone IDs.
     * <p>
     * Many users of time-zones use short abbreviations, such as PST for
     * 'Pacific Standard Time' and PDT for 'Pacific Daylight Time'.
     * These abbreviations are not unique, and so cannot be used as IDs.
     * This method allows a map of string to time-zone to be setup and reused
     * within an application.
     *
     * <p>
     *  使用别名的地图来获取{@code ZoneId}的实例(使用其ID),以补充标准区域ID。
     * <p>
     *  时区的许多用户使用简短缩写,例如"太平洋标准时间"的PST和"太平洋夏令时"的PDT。这些缩写不是唯一的,因此不能用作ID。此方法允许在应用程序中设置和重新使用字符串到时区的映射。
     * 
     * 
     * @param zoneId  the time-zone ID, not null
     * @param aliasMap  a map of alias zone IDs (typically abbreviations) to real zone IDs, not null
     * @return the zone ID, not null
     * @throws DateTimeException if the zone ID has an invalid format
     * @throws ZoneRulesException if the zone ID is a region ID that cannot be found
     */
    public static ZoneId of(String zoneId, Map<String, String> aliasMap) {
        Objects.requireNonNull(zoneId, "zoneId");
        Objects.requireNonNull(aliasMap, "aliasMap");
        String id = aliasMap.get(zoneId);
        id = (id != null ? id : zoneId);
        return of(id);
    }

    /**
     * Obtains an instance of {@code ZoneId} from an ID ensuring that the
     * ID is valid and available for use.
     * <p>
     * This method parses the ID producing a {@code ZoneId} or {@code ZoneOffset}.
     * A {@code ZoneOffset} is returned if the ID is 'Z', or starts with '+' or '-'.
     * The result will always be a valid ID for which {@link ZoneRules} can be obtained.
     * <p>
     * Parsing matches the zone ID step by step as follows.
     * <ul>
     * <li>If the zone ID equals 'Z', the result is {@code ZoneOffset.UTC}.
     * <li>If the zone ID consists of a single letter, the zone ID is invalid
     *  and {@code DateTimeException} is thrown.
     * <li>If the zone ID starts with '+' or '-', the ID is parsed as a
     *  {@code ZoneOffset} using {@link ZoneOffset#of(String)}.
     * <li>If the zone ID equals 'GMT', 'UTC' or 'UT' then the result is a {@code ZoneId}
     *  with the same ID and rules equivalent to {@code ZoneOffset.UTC}.
     * <li>If the zone ID starts with 'UTC+', 'UTC-', 'GMT+', 'GMT-', 'UT+' or 'UT-'
     *  then the ID is a prefixed offset-based ID. The ID is split in two, with
     *  a two or three letter prefix and a suffix starting with the sign.
     *  The suffix is parsed as a {@link ZoneOffset#of(String) ZoneOffset}.
     *  The result will be a {@code ZoneId} with the specified UTC/GMT/UT prefix
     *  and the normalized offset ID as per {@link ZoneOffset#getId()}.
     *  The rules of the returned {@code ZoneId} will be equivalent to the
     *  parsed {@code ZoneOffset}.
     * <li>All other IDs are parsed as region-based zone IDs. Region IDs must
     *  match the regular expression <code>[A-Za-z][A-Za-z0-9~/._+-]+</code>
     *  otherwise a {@code DateTimeException} is thrown. If the zone ID is not
     *  in the configured set of IDs, {@code ZoneRulesException} is thrown.
     *  The detailed format of the region ID depends on the group supplying the data.
     *  The default set of data is supplied by the IANA Time Zone Database (TZDB).
     *  This has region IDs of the form '{area}/{city}', such as 'Europe/Paris' or 'America/New_York'.
     *  This is compatible with most IDs from {@link java.util.TimeZone}.
     * </ul>
     *
     * <p>
     *  从ID获取{@code ZoneId}的实例,确保该ID有效并可供使用。
     * <p>
     *  此方法解析产生{@code ZoneId}或{@code ZoneOffset}的ID。如果ID为"Z",或者以"+"或" - "开头,则返回{@code ZoneOffset}。
     * 结果将始终是可获取{@link ZoneRules}的有效ID。
     * <p>
     *  解析与区域ID逐步匹配如下。
     * <ul>
     * <li>如果区域ID等于"Z",则结果为{@code ZoneOffset.UTC}。
     *  <li>如果区域ID由单个字母组成,则区域ID无效,并且会抛出{@code DateTimeException}。
     *  <li>如果区域ID以"+"或" - "开头,则使用{@link ZoneOffset#of(String)}将ID解析为{@code ZoneOffset}。
     *  <li>如果区域ID等于'GMT','UTC'或'UT',则结果是具有与{@code ZoneOffset.UTC}相同的ID和规则的{@code ZoneId}。
     *  <li>如果区域ID以"UTC +","UTC-","GMT +","GMT-","UT +"或"UT-"开头,则ID是基于前缀的基于偏移量的ID。
     *  ID分为两部分,两个或三个字母的前缀和一个以符号开头的后缀。后缀解析为{@link ZoneOffset#of(String)ZoneOffset}。
     * 结果将是具有指定的UTC / GMT / UT前缀的{@code ZoneId},以及根据{@link ZoneOffset#getId()}的规范化偏移ID。
     * 返回的{@code ZoneId}的规则将等同于解析的{@code ZoneOffset}。 <li>所有其他ID都会解析为基于区域的区域ID。
     * 区域ID必须与正则表达式<code> [A-Za-z] [A-Za-z0-9〜/._+-]+</code>相匹配,否则抛出{@code DateTimeException}。
     * 如果区域ID不在已配置的ID集中,则会抛出{@code ZoneRulesException}。区域ID的详细格式取决于提供数据的组。默认数据集由IANA时区数据库(TZDB)提供。
     * 它具有"{area} / {city}"形式的区域ID,例如"Europe / Paris"或"America / New_York"。
     * 这与来自{@link java.util.TimeZone}的大多数ID兼容。
     * </ul>
     * 
     * 
     * @param zoneId  the time-zone ID, not null
     * @return the zone ID, not null
     * @throws DateTimeException if the zone ID has an invalid format
     * @throws ZoneRulesException if the zone ID is a region ID that cannot be found
     */
    public static ZoneId of(String zoneId) {
        return of(zoneId, true);
    }

    /**
     * Obtains an instance of {@code ZoneId} wrapping an offset.
     * <p>
     * If the prefix is "GMT", "UTC", or "UT" a {@code ZoneId}
     * with the prefix and the non-zero offset is returned.
     * If the prefix is empty {@code ""} the {@code ZoneOffset} is returned.
     *
     * <p>
     * 获取{@code ZoneId}包装偏移量的实例。
     * <p>
     *  如果前缀是"GMT","UTC"或"UT",将返回带有前缀和非零偏移量的{@code ZoneId}。如果前缀为空{@code""},则返回{@code ZoneOffset}。
     * 
     * 
     * @param prefix  the time-zone ID, not null
     * @param offset  the offset, not null
     * @return the zone ID, not null
     * @throws IllegalArgumentException if the prefix is not one of
     *     "GMT", "UTC", or "UT", or ""
     */
    public static ZoneId ofOffset(String prefix, ZoneOffset offset) {
        Objects.requireNonNull(prefix, "prefix");
        Objects.requireNonNull(offset, "offset");
        if (prefix.length() == 0) {
            return offset;
        }

        if (!prefix.equals("GMT") && !prefix.equals("UTC") && !prefix.equals("UT")) {
             throw new IllegalArgumentException("prefix should be GMT, UTC or UT, is: " + prefix);
        }

        if (offset.getTotalSeconds() != 0) {
            prefix = prefix.concat(offset.getId());
        }
        return new ZoneRegion(prefix, offset.getRules());
    }

    /**
     * Parses the ID, taking a flag to indicate whether {@code ZoneRulesException}
     * should be thrown or not, used in deserialization.
     *
     * <p>
     *  解析ID,使用一个标志来指示是否应该抛出{@code ZoneRulesException},用于反序列化。
     * 
     * 
     * @param zoneId  the time-zone ID, not null
     * @param checkAvailable  whether to check if the zone ID is available
     * @return the zone ID, not null
     * @throws DateTimeException if the ID format is invalid
     * @throws ZoneRulesException if checking availability and the ID cannot be found
     */
    static ZoneId of(String zoneId, boolean checkAvailable) {
        Objects.requireNonNull(zoneId, "zoneId");
        if (zoneId.length() <= 1 || zoneId.startsWith("+") || zoneId.startsWith("-")) {
            return ZoneOffset.of(zoneId);
        } else if (zoneId.startsWith("UTC") || zoneId.startsWith("GMT")) {
            return ofWithPrefix(zoneId, 3, checkAvailable);
        } else if (zoneId.startsWith("UT")) {
            return ofWithPrefix(zoneId, 2, checkAvailable);
        }
        return ZoneRegion.ofId(zoneId, checkAvailable);
    }

    /**
     * Parse once a prefix is established.
     *
     * <p>
     *  一个前缀建立后解析。
     * 
     * 
     * @param zoneId  the time-zone ID, not null
     * @param prefixLength  the length of the prefix, 2 or 3
     * @return the zone ID, not null
     * @throws DateTimeException if the zone ID has an invalid format
     */
    private static ZoneId ofWithPrefix(String zoneId, int prefixLength, boolean checkAvailable) {
        String prefix = zoneId.substring(0, prefixLength);
        if (zoneId.length() == prefixLength) {
            return ofOffset(prefix, ZoneOffset.UTC);
        }
        if (zoneId.charAt(prefixLength) != '+' && zoneId.charAt(prefixLength) != '-') {
            return ZoneRegion.ofId(zoneId, checkAvailable);  // drop through to ZoneRulesProvider
        }
        try {
            ZoneOffset offset = ZoneOffset.of(zoneId.substring(prefixLength));
            if (offset == ZoneOffset.UTC) {
                return ofOffset(prefix, offset);
            }
            return ofOffset(prefix, offset);
        } catch (DateTimeException ex) {
            throw new DateTimeException("Invalid ID for offset-based ZoneId: " + zoneId, ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZoneId} from a temporal object.
     * <p>
     * This obtains a zone based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ZoneId}.
     * <p>
     * A {@code TemporalAccessor} represents some form of date and time information.
     * This factory converts the arbitrary temporal object to an instance of {@code ZoneId}.
     * <p>
     * The conversion will try to obtain the zone in a way that favours region-based
     * zones over offset-based zones using {@link TemporalQueries#zone()}.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code ZoneId::from}.
     *
     * <p>
     *  从临时对象获取{@code ZoneId}的实例。
     * <p>
     *  这基于指定的时间获得区。 {@code TemporalAccessor}表示一组任意的日期和时间信息,该工厂将其转换为{@code ZoneId}的实例。
     * <p>
     *  {@code TemporalAccessor}表示某种形式的日期和时间信息。此工厂将任意临时对象转换为{@code ZoneId}的实例。
     * <p>
     *  转换将尝试使用{@link TemporalQueries#zone()},以偏好为基础的区域优先于基于区域的区域的方式获取区域。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code ZoneId :: from}在查询中使用。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the zone ID, not null
     * @throws DateTimeException if unable to convert to a {@code ZoneId}
     */
    public static ZoneId from(TemporalAccessor temporal) {
        ZoneId obj = temporal.query(TemporalQueries.zone());
        if (obj == null) {
            throw new DateTimeException("Unable to obtain ZoneId from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName());
        }
        return obj;
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor only accessible within the package.
     * <p>
     *  构造函数只能在包中访问。
     * 
     */
    ZoneId() {
        if (getClass() != ZoneOffset.class && getClass() != ZoneRegion.class) {
            throw new AssertionError("Invalid subclass");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the unique time-zone ID.
     * <p>
     * This ID uniquely defines this object.
     * The format of an offset based ID is defined by {@link ZoneOffset#getId()}.
     *
     * <p>
     *  获取唯一的时区ID。
     * <p>
     *  此ID唯一定义此对象。基于偏移的ID的格式由{@link ZoneOffset#getId()}定义。
     * 
     * 
     * @return the time-zone unique ID, not null
     */
    public abstract String getId();

    //-----------------------------------------------------------------------
    /**
     * Gets the textual representation of the zone, such as 'British Time' or
     * '+02:00'.
     * <p>
     * This returns the textual name used to identify the time-zone ID,
     * suitable for presentation to the user.
     * The parameters control the style of the returned text and the locale.
     * <p>
     * If no textual mapping is found then the {@link #getId() full ID} is returned.
     *
     * <p>
     *  获取区域的文本表示,例如"英国时间"或"+02：00"。
     * <p>
     * 这返回用于标识时区ID的文本名称,适合向用户显示。参数控制返回的文本和语言环境的样式。
     * <p>
     *  如果没有找到文本映射,则返回{@link #getId()full ID}。
     * 
     * 
     * @param style  the length of the text required, not null
     * @param locale  the locale to use, not null
     * @return the text value of the zone, not null
     */
    public String getDisplayName(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder().appendZoneText(style).toFormatter(locale).format(toTemporal());
    }

    /**
     * Converts this zone to a {@code TemporalAccessor}.
     * <p>
     * A {@code ZoneId} can be fully represented as a {@code TemporalAccessor}.
     * However, the interface is not implemented by this class as most of the
     * methods on the interface have no meaning to {@code ZoneId}.
     * <p>
     * The returned temporal has no supported fields, with the query method
     * supporting the return of the zone using {@link TemporalQueries#zoneId()}.
     *
     * <p>
     *  将此区域转换为{@code TemporalAccessor}。
     * <p>
     *  {@code ZoneId}可以完全表示为{@code TemporalAccessor}。但是,该接口不是由这个类实现的,因为接口上的大多数方法对{@code ZoneId}没有意义。
     * <p>
     *  返回的temporal没有支持的字段,查询方法支持使用{@link TemporalQueries#zoneId()}返回区域。
     * 
     * 
     * @return a temporal equivalent to this zone, not null
     */
    private TemporalAccessor toTemporal() {
        return new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                return false;
            }
            @Override
            public long getLong(TemporalField field) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
            }
            @SuppressWarnings("unchecked")
            @Override
            public <R> R query(TemporalQuery<R> query) {
                if (query == TemporalQueries.zoneId()) {
                    return (R) ZoneId.this;
                }
                return TemporalAccessor.super.query(query);
            }
        };
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the time-zone rules for this ID allowing calculations to be performed.
     * <p>
     * The rules provide the functionality associated with a time-zone,
     * such as finding the offset for a given instant or local date-time.
     * <p>
     * A time-zone can be invalid if it is deserialized in a Java Runtime which
     * does not have the same rules loaded as the Java Runtime that stored it.
     * In this case, calling this method will throw a {@code ZoneRulesException}.
     * <p>
     * The rules are supplied by {@link ZoneRulesProvider}. An advanced provider may
     * support dynamic updates to the rules without restarting the Java Runtime.
     * If so, then the result of this method may change over time.
     * Each individual call will be still remain thread-safe.
     * <p>
     * {@link ZoneOffset} will always return a set of rules where the offset never changes.
     *
     * <p>
     *  获取此ID的时区规则,以便执行计算。
     * <p>
     *  规则提供与时区相关联的功能,例如找到给定时刻或本地日期时间的偏移。
     * <p>
     *  如果时区在Java运行时中反序列化,该时间库不具有与存储它的Java运行时相同的规则,则该时区可能无效。在这种情况下,调用此方法将抛出一个{@code ZoneRulesException}。
     * <p>
     *  规则由{@link ZoneRulesProvider}提供。高级提供程序可以支持对规则的动态更新,而不必重新启动Java运行时。如果是,则此方法的结果可能随时间而改变。
     * 每个单独的调用仍将保持线程安全。
     * <p>
     *  {@link ZoneOffset}将总是返回一组规则,其中偏移量从不改变。
     * 
     * 
     * @return the rules, not null
     * @throws ZoneRulesException if no rules are available for this ID
     */
    public abstract ZoneRules getRules();

    /**
     * Normalizes the time-zone ID, returning a {@code ZoneOffset} where possible.
     * <p>
     * The returns a normalized {@code ZoneId} that can be used in place of this ID.
     * The result will have {@code ZoneRules} equivalent to those returned by this object,
     * however the ID returned by {@code getId()} may be different.
     * <p>
     * The normalization checks if the rules of this {@code ZoneId} have a fixed offset.
     * If they do, then the {@code ZoneOffset} equal to that offset is returned.
     * Otherwise {@code this} is returned.
     *
     * <p>
     * 规范化时区ID,尽可能返回{@code ZoneOffset}。
     * <p>
     *  返回一个标准化的{@code ZoneId},可以用来代替这个ID。结果将有{@code ZoneRules}等价于由此对象返回的那些,但是{@code getId()}返回的ID可能不同。
     * <p>
     *  规范化检查此{@code ZoneId}的规则是否具有固定偏移量。如果它们这样做,则返回等于该偏移量的{@code ZoneOffset}。否则返回{@code this}。
     * 
     * 
     * @return the time-zone unique ID, not null
     */
    public ZoneId normalized() {
        try {
            ZoneRules rules = getRules();
            if (rules.isFixedOffset()) {
                return rules.getOffset(Instant.EPOCH);
            }
        } catch (ZoneRulesException ex) {
            // invalid ZoneRegion is not important to this method
        }
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this time-zone ID is equal to another time-zone ID.
     * <p>
     * The comparison is based on the ID.
     *
     * <p>
     *  检查此时区ID是否等于另一个时区ID。
     * <p>
     *  比较基于ID。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other time-zone ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
           return true;
        }
        if (obj instanceof ZoneId) {
            ZoneId other = (ZoneId) obj;
            return getId().equals(other.getId());
        }
        return false;
    }

    /**
     * A hash code for this time-zone ID.
     *
     * <p>
     *  此时区ID的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    //-----------------------------------------------------------------------
    /**
     * Defend against malicious streams.
     *
     * <p>
     *  防御恶意流。
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /**
     * Outputs this zone as a {@code String}, using the ID.
     *
     * <p>
     *  使用ID将此区域输出为{@code String}。
     * 
     * 
     * @return a string representation of this time-zone ID, not null
     */
    @Override
    public String toString() {
        return getId();
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href="../../serialized-form.html#java.time.Ser">专用序列化表单</a>写入对象。
     * 
     * @serialData
     * <pre>
     *  out.writeByte(7);  // identifies a ZoneId (not ZoneOffset)
     *  out.writeUTF(getId());
     * </pre>
     * <p>
     * When read back in, the {@code ZoneId} will be created as though using
     * {@link #of(String)}, but without any exception in the case where the
     * ID has a valid format, but is not in the known set of region-based IDs.
     *
     * @return the instance of {@code Ser}, not null
     */
    // this is here for serialization Javadoc
    private Object writeReplace() {
        return new Ser(Ser.ZONE_REGION_TYPE, this);
    }

    abstract void write(DataOutput out) throws IOException;

}
