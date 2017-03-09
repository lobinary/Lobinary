/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.spi;

import java.util.Locale;

/**
 * <p>
 * This is the super class of all the locale sensitive service provider
 * interfaces (SPIs).
 * <p>
 * Locale sensitive  service provider interfaces are interfaces that
 * correspond to locale sensitive classes in the <code>java.text</code>
 * and <code>java.util</code> packages. The interfaces enable the
 * construction of locale sensitive objects and the retrieval of
 * localized names for these packages. Locale sensitive factory methods
 * and methods for name retrieval in the <code>java.text</code> and
 * <code>java.util</code> packages use implementations of the provider
 * interfaces to offer support for locales beyond the set of locales
 * supported by the Java runtime environment itself.
 *
 * <h3>Packaging of Locale Sensitive Service Provider Implementations</h3>
 * Implementations of these locale sensitive services are packaged using the
 * <a href="../../../../technotes/guides/extensions/index.html">Java Extension Mechanism</a>
 * as installed extensions.  A provider identifies itself with a
 * provider-configuration file in the resource directory META-INF/services,
 * using the fully qualified provider interface class name as the file name.
 * The file should contain a list of fully-qualified concrete provider class names,
 * one per line. A line is terminated by any one of a line feed ('\n'), a carriage
 * return ('\r'), or a carriage return followed immediately by a line feed. Space
 * and tab characters surrounding each name, as well as blank lines, are ignored.
 * The comment character is '#' ('\u0023'); on each line all characters following
 * the first comment character are ignored. The file must be encoded in UTF-8.
 * <p>
 * If a particular concrete provider class is named in more than one configuration
 * file, or is named in the same configuration file more than once, then the
 * duplicates will be ignored. The configuration file naming a particular provider
 * need not be in the same jar file or other distribution unit as the provider itself.
 * The provider must be accessible from the same class loader that was initially
 * queried to locate the configuration file; this is not necessarily the class loader
 * that loaded the file.
 * <p>
 * For example, an implementation of the
 * {@link java.text.spi.DateFormatProvider DateFormatProvider} class should
 * take the form of a jar file which contains the file:
 * <pre>
 * META-INF/services/java.text.spi.DateFormatProvider
 * </pre>
 * And the file <code>java.text.spi.DateFormatProvider</code> should have
 * a line such as:
 * <pre>
 * <code>com.foo.DateFormatProviderImpl</code>
 * </pre>
 * which is the fully qualified class name of the class implementing
 * <code>DateFormatProvider</code>.
 * <h4>Invocation of Locale Sensitive Services</h4>
 * <p>
 * Locale sensitive factory methods and methods for name retrieval in the
 * <code>java.text</code> and <code>java.util</code> packages invoke
 * service provider methods when needed to support the requested locale.
 * The methods first check whether the Java runtime environment itself
 * supports the requested locale, and use its support if available.
 * Otherwise, they call the {@link #isSupportedLocale(Locale) isSupportedLocale}
 * methods of installed providers for the appropriate interface to find one that
 * supports the requested locale. If such a provider is found, its other
 * methods are called to obtain the requested object or name.  When checking
 * whether a locale is supported, the <a href="../Locale.html#def_extensions">
 * locale's extensions</a> are ignored by default. (If locale's extensions should
 * also be checked, the {@code isSupportedLocale} method must be overridden.)
 * If neither the Java runtime environment itself nor an installed provider
 * supports the requested locale, the methods go through a list of candidate
 * locales and repeat the availability check for each until a match is found.
 * The algorithm used for creating a list of candidate locales is same as
 * the one used by <code>ResourceBundle</code> by default (see
 * {@link java.util.ResourceBundle.Control#getCandidateLocales getCandidateLocales}
 * for the details).  Even if a locale is resolved from the candidate list,
 * methods that return requested objects or names are invoked with the original
 * requested locale including {@code Locale} extensions. The Java runtime
 * environment must support the root locale for all locale sensitive services in
 * order to guarantee that this process terminates.
 * <p>
 * Providers of names (but not providers of other objects) are allowed to
 * return null for some name requests even for locales that they claim to
 * support by including them in their return value for
 * <code>getAvailableLocales</code>. Similarly, the Java runtime
 * environment itself may not have all names for all locales that it
 * supports. This is because the sets of objects for which names are
 * requested can be large and vary over time, so that it's not always
 * feasible to cover them completely. If the Java runtime environment or a
 * provider returns null instead of a name, the lookup will proceed as
 * described above as if the locale was not supported.
 * <p>
 * Starting from JDK8, the search order of locale sensitive services can
 * be configured by using the "java.locale.providers" system property.
 * This system property declares the user's preferred order for looking up
 * the locale sensitive services separated by a comma. It is only read at
 * the Java runtime startup, so the later call to System.setProperty() won't
 * affect the order.
 * <p>
 * For example, if the following is specified in the property:
 * <pre>
 * java.locale.providers=SPI,JRE
 * </pre>
 * where "SPI" represents the locale sensitive services implemented in the
 * installed SPI providers, and "JRE" represents the locale sensitive services
 * in the Java Runtime Environment, the locale sensitive services in the SPI
 * providers are looked up first.
 * <p>
 * There are two other possible locale sensitive service providers, i.e., "CLDR"
 * which is a provider based on Unicode Consortium's
 * <a href="http://cldr.unicode.org/">CLDR Project</a>, and "HOST" which is a
 * provider that reflects the user's custom settings in the underlying operating
 * system. These two providers may not be available, depending on the Java Runtime
 * Environment implementation. Specifying "JRE,SPI" is identical to the default
 * behavior, which is compatibile with the prior releases.
 *
 * <p>
 * <p>
 *  这是所有区域性敏感服务提供程序接口(SPI)的超类。
 * <p>
 *  区域性敏感服务提供程序接口是与<code> java.text </code>和<code> java.util </code>包中区域性敏感类相对应的接口。
 * 这些接口支持构建区域性敏感对象和检索这些包的本地化名称。
 * 用于在<code> java.text </code>和<code> java.util </code>包中进行名称检索的区域性敏感的工厂方法和方法使用提供程序接口的实现来支持超出所支持的区域集由Jav
 * a运行时环境本身。
 * 这些接口支持构建区域性敏感对象和检索这些包的本地化名称。
 * 
 * <h3>区域敏感服务提供程序实施的包装</h3>这些区域敏感服务的实现使用<a href ="../../../../ technotes / guides / extensions / index.html ">
 *  Java扩展机制</a>作为已安装的扩展。
 * 提供程序使用资源目录META-INF / services中的提供程序配置文件来标识自身,使用完全限定的提供程序接口类名作为文件名。该文件应包含完全合格的具体提供程序类名称列表,每行一个。
 * 一行通过换行符('\ n'),回车符('\ r')或回车符后紧跟换行符中的任何一个终止。忽略每个名称周围的空格和制表符以及空行。
 * 注释字符为'#'('\ u0023');在每行上,忽略第一个注释字符后面的所有字符。该文件必须以UTF-8编码。
 * <p>
 *  如果特定的具体提供程序类在多个配置文件中命名,或者在同一配置文件中多次命名,则将忽略重复项。指定特定提供者的配置文件不需要位于与提供者本身相同的jar文件或其他分发单元中。
 * 提供程序必须可以从最初查询以查找配置文件的同一类加载器访问;这不一定是加载文件的类加载器。
 * <p>
 * 例如,{@link java.text.spi.DateFormatProvider DateFormatProvider}类的实现应采用包含文件的jar文件的形式：
 * <pre>
 *  META-INF / services / java.text.spi.DateFormatProvider
 * </pre>
 *  而文件<code> java.text.spi.DateFormatProvider </code>应该有一行如：
 * <pre>
 *  <code> com.foo.DateFormatProviderImpl </code>
 * </pre>
 *  这是实现<code> DateFormatProvider </code>的类的完全限定类名。 <h4>调用区域敏感服务</h4>
 * <p>
 * 用于在<code> java.text </code>和<code> java.util </code>包中进行名称检索的区域敏感的工厂方法和方法在需要时调用服务提供程序方法以支持所请求的语言环境。
 * 方法首先检查Java运行时环境本身是否支持请求的语言环境,并使用其支持(如果可用)。
 * 否则,他们调用已安装的提供程序的{@link #isSupportedLocale(Locale)isSupportedLocale}方法,以找到支持请求的语言环境的适当接口。
 * 如果找到这样的提供者,则调用其它方法以获得所请求的对象或名称。
 * 在检查是否支持区域设置时,默认情况下会忽略<a href="../Locale.html#def_extensions">区域设置的扩展程序</a>。
 *  (如果还要检查语言环境的扩展,则必须覆盖{@code isSupportedLocale}方法。
 * )如果Java运行时环境本身和安装的提供程序都不支持请求的语言环境,则方法将遍历候选语言环境列表,并重复可用性检查每个,直到找到匹配。
 * 用于创建候选语言环境列表的算法与默认情况下由<code> ResourceBundle </code>使用的算法相同(有关详细信息,请参阅{@link java.util.ResourceBundle.Control#getCandidateLocales getCandidateLocales}
 * )。
 * )如果Java运行时环境本身和安装的提供程序都不支持请求的语言环境,则方法将遍历候选语言环境列表,并重复可用性检查每个,直到找到匹配。
 * 即使从候选列表中解析出某个区域设置,也会使用包含{@code Locale}扩展名的原始请求的区域设置调用返回请求的对象或名称的方法。
 * 
 * @since        1.6
 */
public abstract class LocaleServiceProvider {

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  Java运行时环境必须支持所有区域性敏感服务的根区域设置,以保证此过程终止。
     * <p>
     * 允许名称提供程序(但不是其他对象的提供程序)对某些名称请求返回null,即使对于他们声明支持的语言环境,将它们包含在<code> getAvailableLocales </code>的返回值中也是如此
     * 。
     * 类似地,Java运行时环境本身可能不具有其支持的所有语言环境的所有名称。这是因为请求名称的对象集可能很大,并且随时间变化,因此完全覆盖它们并不总是可行的。
     * 如果Java运行时环境或提供程序返回null而不是名称,则查找将按照上述方法进行,就好像不支持语言环境一样。
     * <p>
     *  从JDK8开始,可以使用"java.locale.providers"系统属性配置区域设置敏感服务的搜索顺序。此系统属性声明用户查找区域设置敏感服务的首选顺序,用逗号分隔。
     * 它只在Java运行时启动时读取,因此稍后调用System.setProperty()将不会影响顺序。
     * <p>
     *  例如,如果在属性中指定以下内容：
     * <pre>
     *  java.locale.providers = SPI,JRE
     * </pre>
     *  其中"SPI"表示在已安装的SPI提供程序中实现的区域性敏感服务,"JRE"表示Java运行时环境中的区域性敏感服务,首先查找SPI提供程序中的区域敏感服务。
     * <p>
     * 还有两个其他可能的区域性敏感服务提供商,即"CLDR",它是基于Unicode Consortium的<a href="http://cldr.unicode.org/"> CLDR项目</a>的提供商
     * ,以及"HOST "这是反映用户在底层操作系统中的自定义设置的提供程序。
     * 这两个提供程序可能不可用,这取决于Java Runtime Environment实现。指定"JRE,SPI"与默认行为相同,这与早期版本兼容。
     * 
     */
    protected LocaleServiceProvider() {
    }

    /**
     * Returns an array of all locales for which this locale service provider
     * can provide localized objects or names. This information is used to
     * compose {@code getAvailableLocales()} values of the locale-dependent
     * services, such as {@code DateFormat.getAvailableLocales()}.
     *
     * <p>The array returned by this method should not include two or more
     * {@code Locale} objects only differing in their extensions.
     *
     * <p>
     * 
     * @return An array of all locales for which this locale service provider
     * can provide localized objects or names.
     */
    public abstract Locale[] getAvailableLocales();

    /**
     * Returns {@code true} if the given {@code locale} is supported by
     * this locale service provider. The given {@code locale} may contain
     * <a href="../Locale.html#def_extensions">extensions</a> that should be
     * taken into account for the support determination.
     *
     * <p>The default implementation returns {@code true} if the given {@code locale}
     * is equal to any of the available {@code Locale}s returned by
     * {@link #getAvailableLocales()} with ignoring any extensions in both the
     * given {@code locale} and the available locales. Concrete locale service
     * provider implementations should override this method if those
     * implementations are {@code Locale} extensions-aware. For example,
     * {@code DecimalFormatSymbolsProvider} implementations will need to check
     * extensions in the given {@code locale} to see if any numbering system is
     * specified and can be supported. However, {@code CollatorProvider}
     * implementations may not be affected by any particular numbering systems,
     * and in that case, extensions for numbering systems should be ignored.
     *
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     * 
     * @param locale a {@code Locale} to be tested
     * @return {@code true} if the given {@code locale} is supported by this
     *         provider; {@code false} otherwise.
     * @throws NullPointerException
     *         if the given {@code locale} is {@code null}
     * @see Locale#hasExtensions()
     * @see Locale#stripExtensions()
     * @since 1.8
     */
    public boolean isSupportedLocale(Locale locale) {
        locale = locale.stripExtensions(); // throws NPE if locale == null
        for (Locale available : getAvailableLocales()) {
            if (locale.equals(available.stripExtensions())) {
                return true;
}
        }
        return false;
    }
}
