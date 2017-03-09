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
 * Copyright (c) 2009-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2009-2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time.zone;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Provider of time-zone rules to the system.
 * <p>
 * This class manages the configuration of time-zone rules.
 * The static methods provide the public API that can be used to manage the providers.
 * The abstract methods provide the SPI that allows rules to be provided.
 * <p>
 * ZoneRulesProvider may be installed in an instance of the Java Platform as
 * extension classes, that is, jar files placed into any of the usual extension
 * directories. Installed providers are loaded using the service-provider loading
 * facility defined by the {@link ServiceLoader} class. A ZoneRulesProvider
 * identifies itself with a provider configuration file named
 * {@code java.time.zone.ZoneRulesProvider} in the resource directory
 * {@code META-INF/services}. The file should contain a line that specifies the
 * fully qualified concrete zonerules-provider class name.
 * Providers may also be made available by adding them to the class path or by
 * registering themselves via {@link #registerProvider} method.
 * <p>
 * The Java virtual machine has a default provider that provides zone rules
 * for the time-zones defined by IANA Time Zone Database (TZDB). If the system
 * property {@code java.time.zone.DefaultZoneRulesProvider} is defined then
 * it is taken to be the fully-qualified name of a concrete ZoneRulesProvider
 * class to be loaded as the default provider, using the system class loader.
 * If this system property is not defined, a system-default provider will be
 * loaded to serve as the default provider.
 * <p>
 * Rules are looked up primarily by zone ID, as used by {@link ZoneId}.
 * Only zone region IDs may be used, zone offset IDs are not used here.
 * <p>
 * Time-zone rules are political, thus the data can change at any time.
 * Each provider will provide the latest rules for each zone ID, but they
 * may also provide the history of how the rules changed.
 *
 * @implSpec
 * This interface is a service provider that can be called by multiple threads.
 * Implementations must be immutable and thread-safe.
 * <p>
 * Providers must ensure that once a rule has been seen by the application, the
 * rule must continue to be available.
 * <p>
*  Providers are encouraged to implement a meaningful {@code toString} method.
 * <p>
 * Many systems would like to update time-zone rules dynamically without stopping the JVM.
 * When examined in detail, this is a complex problem.
 * Providers may choose to handle dynamic updates, however the default provider does not.
 *
 * <p>
 *  向系统提供时区规则。
 * <p>
 *  此类管理时区规则的配置。静态方法提供可用于管理提供程序的公共API。抽象方法提供允许提供规则的SPI。
 * <p>
 * ZoneRulesProvider可以作为扩展类安装在Java平台的一个实例中,也就是说,jar文件放在任何通常的扩展目录中。
 * 已安装的提供程序使用由{@link ServiceLoader}类定义的服务提供程序加载工具加载。
 *  ZoneRulesProvider使用资源目录{@code META-INF / services}中名为{@code java.time.zone.ZoneRulesProvider}的提供程序配置
 * 文件来标识自身。
 * 已安装的提供程序使用由{@link ServiceLoader}类定义的服务提供程序加载工具加载。该文件应包含一行,该行指定完全限定的具体zonerules-provider类名。
 * 提供者也可以通过将它们添加到类路径或通过{@link #registerProvider}方法注册自己。
 * <p>
 *  Java虚拟机具有为IANA时区数据库(TZDB)定义的时区提供区域规则的默认提供程序。
 * 如果定义了系统属性{@code java.time.zone.DefaultZoneRulesProvider},那么它将被视为使用系统类加载器作为默认提供程序加载的具体ZoneRulesProvide
 * r类的完全限定名。
 *  Java虚拟机具有为IANA时区数据库(TZDB)定义的时区提供区域规则的默认提供程序。如果未定义此系统属性,系统默认提供程序将加载作为默认提供程序。
 * <p>
 *  规则主要由区域ID查找,如{@link ZoneId}所使用。仅可以使用区域ID,在此不使用区域偏移ID。
 * <p>
 * 时区规则是政治规则,因此数据可以随时更改。每个提供商将为每个区域ID提供最新规则,但它们也可以提供规则更改的历史记录。
 * 
 *  @implSpec此接口是一个服务提供程序,可以由多个线程调用。实现必须是不可变的和线程安全的。
 * <p>
 *  提供商必须确保一旦应用程序看到规则,规则必须继续可用。
 * <p>
 *  鼓励提供者实现一个有意义的{@code toString}方法。
 * <p>
 *  许多系统希望动态更新时区规则,而不停止JVM。当详细审查时,这是一个复杂的问题。提供程序可以选择处理动态更新,但默认提供程序不会。
 * 
 * 
 * @since 1.8
 */
public abstract class ZoneRulesProvider {

    /**
     * The set of loaded providers.
     * <p>
     *  已加载的提供程序集。
     * 
     */
    private static final CopyOnWriteArrayList<ZoneRulesProvider> PROVIDERS = new CopyOnWriteArrayList<>();
    /**
     * The lookup from zone ID to provider.
     * <p>
     *  从区域ID到提供程序的查找。
     * 
     */
    private static final ConcurrentMap<String, ZoneRulesProvider> ZONES = new ConcurrentHashMap<>(512, 0.75f, 2);

    static {
        // if the property java.time.zone.DefaultZoneRulesProvider is
        // set then its value is the class name of the default provider
        final List<ZoneRulesProvider> loaded = new ArrayList<>();
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                String prop = System.getProperty("java.time.zone.DefaultZoneRulesProvider");
                if (prop != null) {
                    try {
                        Class<?> c = Class.forName(prop, true, ClassLoader.getSystemClassLoader());
                        ZoneRulesProvider provider = ZoneRulesProvider.class.cast(c.newInstance());
                        registerProvider(provider);
                        loaded.add(provider);
                    } catch (Exception x) {
                        throw new Error(x);
                    }
                } else {
                    registerProvider(new TzdbZoneRulesProvider());
                }
                return null;
            }
        });

        ServiceLoader<ZoneRulesProvider> sl = ServiceLoader.load(ZoneRulesProvider.class, ClassLoader.getSystemClassLoader());
        Iterator<ZoneRulesProvider> it = sl.iterator();
        while (it.hasNext()) {
            ZoneRulesProvider provider;
            try {
                provider = it.next();
            } catch (ServiceConfigurationError ex) {
                if (ex.getCause() instanceof SecurityException) {
                    continue;  // ignore the security exception, try the next provider
                }
                throw ex;
            }
            boolean found = false;
            for (ZoneRulesProvider p : loaded) {
                if (p.getClass() == provider.getClass()) {
                    found = true;
                }
            }
            if (!found) {
                registerProvider0(provider);
                loaded.add(provider);
            }
        }
        // CopyOnWriteList could be slow if lots of providers and each added individually
        PROVIDERS.addAll(loaded);
    }

    //-------------------------------------------------------------------------
    /**
     * Gets the set of available zone IDs.
     * <p>
     * These IDs are the string form of a {@link ZoneId}.
     *
     * <p>
     *  获取可用区域ID集。
     * <p>
     *  这些ID是{@link ZoneId}的字符串形式。
     * 
     * 
     * @return a modifiable copy of the set of zone IDs, not null
     */
    public static Set<String> getAvailableZoneIds() {
        return new HashSet<>(ZONES.keySet());
    }

    /**
     * Gets the rules for the zone ID.
     * <p>
     * This returns the latest available rules for the zone ID.
     * <p>
     * This method relies on time-zone data provider files that are configured.
     * These are loaded using a {@code ServiceLoader}.
     * <p>
     * The caching flag is designed to allow provider implementations to
     * prevent the rules being cached in {@code ZoneId}.
     * Under normal circumstances, the caching of zone rules is highly desirable
     * as it will provide greater performance. However, there is a use case where
     * the caching would not be desirable, see {@link #provideRules}.
     *
     * <p>
     *  获取区域ID的规则。
     * <p>
     *  这将返回区域ID的最新可用规则。
     * <p>
     *  此方法依赖于配置的时区数据提供程序文件。这些是使用{@code ServiceLoader}加载的。
     * <p>
     *  缓存标志旨在允许提供程序实现,以防止规则缓存在{@code ZoneId}。在正常情况下,高速缓存区域规则是非常可取的,因为它将提供更好的性能。
     * 然而,有一个使用情况,缓存是不可取的,参见{@link #provideRules}。
     * 
     * 
     * @param zoneId the zone ID as defined by {@code ZoneId}, not null
     * @param forCaching whether the rules are being queried for caching,
     * true if the returned rules will be cached by {@code ZoneId},
     * false if they will be returned to the user without being cached in {@code ZoneId}
     * @return the rules, null if {@code forCaching} is true and this
     * is a dynamic provider that wants to prevent caching in {@code ZoneId},
     * otherwise not null
     * @throws ZoneRulesException if rules cannot be obtained for the zone ID
     */
    public static ZoneRules getRules(String zoneId, boolean forCaching) {
        Objects.requireNonNull(zoneId, "zoneId");
        return getProvider(zoneId).provideRules(zoneId, forCaching);
    }

    /**
     * Gets the history of rules for the zone ID.
     * <p>
     * Time-zones are defined by governments and change frequently.
     * This method allows applications to find the history of changes to the
     * rules for a single zone ID. The map is keyed by a string, which is the
     * version string associated with the rules.
     * <p>
     * The exact meaning and format of the version is provider specific.
     * The version must follow lexicographical order, thus the returned map will
     * be order from the oldest known rules to the newest available rules.
     * The default 'TZDB' group uses version numbering consisting of the year
     * followed by a letter, such as '2009e' or '2012f'.
     * <p>
     * Implementations must provide a result for each valid zone ID, however
     * they do not have to provide a history of rules.
     * Thus the map will always contain one element, and will only contain more
     * than one element if historical rule information is available.
     *
     * <p>
     *  获取区域ID的规则的历史记录。
     * <p>
     * 时区由政府定义和频繁变化。此方法允许应用程序查找单个区域ID的规则更改的历史记录。映射由字符串键入,该字符串是与规则关联的版本字符串。
     * <p>
     *  版本的确切含义和格式是特定于提供程序的。版本必须遵循词典顺序,因此返回的地图将是从最旧的已知规则到最新的可用规则的​​顺序。
     * 默认的"TZDB"组使用版本编号,由年份后跟一个字母组成,例如"2009e"或"2012f"。
     * <p>
     *  实现必须为每个有效的区域ID提供结果,但是它们不必提供规则的历史记录。因此,映射将总是包含一个元素,并且如果历史规则信息可用,则将仅包含多于一个元素。
     * 
     * 
     * @param zoneId  the zone ID as defined by {@code ZoneId}, not null
     * @return a modifiable copy of the history of the rules for the ID, sorted
     *  from oldest to newest, not null
     * @throws ZoneRulesException if history cannot be obtained for the zone ID
     */
    public static NavigableMap<String, ZoneRules> getVersions(String zoneId) {
        Objects.requireNonNull(zoneId, "zoneId");
        return getProvider(zoneId).provideVersions(zoneId);
    }

    /**
     * Gets the provider for the zone ID.
     *
     * <p>
     *  获取区域ID的提供程序。
     * 
     * 
     * @param zoneId  the zone ID as defined by {@code ZoneId}, not null
     * @return the provider, not null
     * @throws ZoneRulesException if the zone ID is unknown
     */
    private static ZoneRulesProvider getProvider(String zoneId) {
        ZoneRulesProvider provider = ZONES.get(zoneId);
        if (provider == null) {
            if (ZONES.isEmpty()) {
                throw new ZoneRulesException("No time-zone data files registered");
            }
            throw new ZoneRulesException("Unknown time-zone ID: " + zoneId);
        }
        return provider;
    }

    //-------------------------------------------------------------------------
    /**
     * Registers a zone rules provider.
     * <p>
     * This adds a new provider to those currently available.
     * A provider supplies rules for one or more zone IDs.
     * A provider cannot be registered if it supplies a zone ID that has already been
     * registered. See the notes on time-zone IDs in {@link ZoneId}, especially
     * the section on using the concept of a "group" to make IDs unique.
     * <p>
     * To ensure the integrity of time-zones already created, there is no way
     * to deregister providers.
     *
     * <p>
     *  注册区域规则提供程序。
     * <p>
     *  这将为当前可用的添加一个新的提供程序。提供程序提供一个或多个区域ID的规则。如果提供商提供已注册的区域ID,则无法注册。
     * 请参阅{@link ZoneId}中的时区ID说明,特别是关于使用"组"概念使ID唯一的部分。
     * <p>
     *  为了确保已创建的时区的完整性,没有办法取消注册提供程序。
     * 
     * 
     * @param provider  the provider to register, not null
     * @throws ZoneRulesException if a zone ID is already registered
     */
    public static void registerProvider(ZoneRulesProvider provider) {
        Objects.requireNonNull(provider, "provider");
        registerProvider0(provider);
        PROVIDERS.add(provider);
    }

    /**
     * Registers the provider.
     *
     * <p>
     *  注册提供程序。
     * 
     * 
     * @param provider  the provider to register, not null
     * @throws ZoneRulesException if unable to complete the registration
     */
    private static void registerProvider0(ZoneRulesProvider provider) {
        for (String zoneId : provider.provideZoneIds()) {
            Objects.requireNonNull(zoneId, "zoneId");
            ZoneRulesProvider old = ZONES.putIfAbsent(zoneId, provider);
            if (old != null) {
                throw new ZoneRulesException(
                    "Unable to register zone as one already registered with that ID: " + zoneId +
                    ", currently loading from provider: " + provider);
            }
        }
    }

    /**
     * Refreshes the rules from the underlying data provider.
     * <p>
     * This method allows an application to request that the providers check
     * for any updates to the provided rules.
     * After calling this method, the offset stored in any {@link ZonedDateTime}
     * may be invalid for the zone ID.
     * <p>
     * Dynamic update of rules is a complex problem and most applications
     * should not use this method or dynamic rules.
     * To achieve dynamic rules, a provider implementation will have to be written
     * as per the specification of this class.
     * In addition, instances of {@code ZoneRules} must not be cached in the
     * application as they will become stale. However, the boolean flag on
     * {@link #provideRules(String, boolean)} allows provider implementations
     * to control the caching of {@code ZoneId}, potentially ensuring that
     * all objects in the system see the new rules.
     * Note that there is likely to be a cost in performance of a dynamic rules
     * provider. Note also that no dynamic rules provider is in this specification.
     *
     * <p>
     *  刷新基础数据提供程序的规则。
     * <p>
     * 此方法允许应用程序请求提供程序检查对提供的规则的任何更新。调用此方法后,存储在任何{@link ZonedDateTime}中的偏移量对于区域ID可能无效。
     * <p>
     *  规则的动态更新是一个复杂的问题,大多数应用程序不应使用此方法或动态规则。为了实现动态规则,提供者实现必须按照这个类的规范来编写。
     * 此外,{@code ZoneRules}的实例不能在应用程序中缓存,因为它们将失效。
     * 然而,{@link #provideRules(String,boolean)}上的布尔标志允许提供程序实现控制{@code ZoneId}的缓存,从而潜在地确保系统中的所有对象都看到新的规则。
     * 请注意,动态规则提供程序的性能可能会有成本。还要注意,在本规范中没有动态规则提供程序。
     * 
     * 
     * @return true if the rules were updated
     * @throws ZoneRulesException if an error occurs during the refresh
     */
    public static boolean refresh() {
        boolean changed = false;
        for (ZoneRulesProvider provider : PROVIDERS) {
            changed |= provider.provideRefresh();
        }
        return changed;
    }

    /**
     * Constructor.
     * <p>
     *  构造函数。
     * 
     */
    protected ZoneRulesProvider() {
    }

    //-----------------------------------------------------------------------
    /**
     * SPI method to get the available zone IDs.
     * <p>
     * This obtains the IDs that this {@code ZoneRulesProvider} provides.
     * A provider should provide data for at least one zone ID.
     * <p>
     * The returned zone IDs remain available and valid for the lifetime of the application.
     * A dynamic provider may increase the set of IDs as more data becomes available.
     *
     * <p>
     *  SPI方法获取可用的区域ID。
     * <p>
     *  这将获取此{@code ZoneRulesProvider}提供的ID。提供商应提供至少一个区域ID的数据。
     * <p>
     *  返回的区域ID在应用程序的生命周期内仍然可用且有效。动态提供者可以在更多数据变得可用时增加ID集合。
     * 
     * 
     * @return the set of zone IDs being provided, not null
     * @throws ZoneRulesException if a problem occurs while providing the IDs
     */
    protected abstract Set<String> provideZoneIds();

    /**
     * SPI method to get the rules for the zone ID.
     * <p>
     * This loads the rules for the specified zone ID.
     * The provider implementation must validate that the zone ID is valid and
     * available, throwing a {@code ZoneRulesException} if it is not.
     * The result of the method in the valid case depends on the caching flag.
     * <p>
     * If the provider implementation is not dynamic, then the result of the
     * method must be the non-null set of rules selected by the ID.
     * <p>
     * If the provider implementation is dynamic, then the flag gives the option
     * of preventing the returned rules from being cached in {@link ZoneId}.
     * When the flag is true, the provider is permitted to return null, where
     * null will prevent the rules from being cached in {@code ZoneId}.
     * When the flag is false, the provider must return non-null rules.
     *
     * <p>
     *  SPI方法获取区域ID的规则。
     * <p>
     * 这将加载指定区域ID的规则。提供程序实现必须验证区域ID有效且可用,如果不是,则抛出{@code ZoneRulesException}。有效情况下方法的结果取决于缓存标志。
     * <p>
     *  如果提供程序实现不是动态的,则方法的结果必须是由ID选择的非空规则集。
     * <p>
     *  如果提供程序实现是动态的,则该标志提供了防止将返回的规则缓存在{@link ZoneId}中的选项。
     * 当标志为true时,允许提供程序返回null,其中null将阻止规则被缓存在{@code ZoneId}中。当标志为假时,提供程序必须返回非空规则。
     * 
     * 
     * @param zoneId the zone ID as defined by {@code ZoneId}, not null
     * @param forCaching whether the rules are being queried for caching,
     * true if the returned rules will be cached by {@code ZoneId},
     * false if they will be returned to the user without being cached in {@code ZoneId}
     * @return the rules, null if {@code forCaching} is true and this
     * is a dynamic provider that wants to prevent caching in {@code ZoneId},
     * otherwise not null
     * @throws ZoneRulesException if rules cannot be obtained for the zone ID
     */
    protected abstract ZoneRules provideRules(String zoneId, boolean forCaching);

    /**
     * SPI method to get the history of rules for the zone ID.
     * <p>
     * This returns a map of historical rules keyed by a version string.
     * The exact meaning and format of the version is provider specific.
     * The version must follow lexicographical order, thus the returned map will
     * be order from the oldest known rules to the newest available rules.
     * The default 'TZDB' group uses version numbering consisting of the year
     * followed by a letter, such as '2009e' or '2012f'.
     * <p>
     * Implementations must provide a result for each valid zone ID, however
     * they do not have to provide a history of rules.
     * Thus the map will contain at least one element, and will only contain
     * more than one element if historical rule information is available.
     * <p>
     * The returned versions remain available and valid for the lifetime of the application.
     * A dynamic provider may increase the set of versions as more data becomes available.
     *
     * <p>
     *  SPI方法获取区域ID的规则的历史。
     * <p>
     *  这将返回由版本字符串键入的历史规则的映射。版本的确切含义和格式是特定于提供程序的。版本必须遵循词典顺序,因此返回的地图将是从最旧的已知规则到最新的可用规则的​​顺序。
     * 默认的"TZDB"组使用版本编号,由年份后跟一个字母组成,例如"2009e"或"2012f"。
     * <p>
     *  实现必须为每个有效的区域ID提供结果,但是它们不必提供规则的历史记录。因此,映射将包含至少一个元素,并且如果历史规则信息可用,则将仅包含多于一个元素。
     * <p>
     * 返回的版本保持可用,并在应用程序的生命周期内有效。动态提供者可以在更多数据变得可用时增加版本集合。
     * 
     * @param zoneId  the zone ID as defined by {@code ZoneId}, not null
     * @return a modifiable copy of the history of the rules for the ID, sorted
     *  from oldest to newest, not null
     * @throws ZoneRulesException if history cannot be obtained for the zone ID
     */
    protected abstract NavigableMap<String, ZoneRules> provideVersions(String zoneId);

    /**
     * SPI method to refresh the rules from the underlying data provider.
     * <p>
     * This method provides the opportunity for a provider to dynamically
     * recheck the underlying data provider to find the latest rules.
     * This could be used to load new rules without stopping the JVM.
     * Dynamic behavior is entirely optional and most providers do not support it.
     * <p>
     * This implementation returns false.
     *
     * <p>
     * 
     * 
     * @return true if the rules were updated
     * @throws ZoneRulesException if an error occurs during the refresh
     */
    protected boolean provideRefresh() {
        return false;
    }

}
