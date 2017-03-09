/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth.login;

import javax.security.auth.AuthPermission;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.Objects;

import sun.security.jca.GetInstance;

/**
 * A Configuration object is responsible for specifying which LoginModules
 * should be used for a particular application, and in what order the
 * LoginModules should be invoked.
 *
 * <p> A login configuration contains the following information.
 * Note that this example only represents the default syntax for the
 * {@code Configuration}.  Subclass implementations of this class
 * may implement alternative syntaxes and may retrieve the
 * {@code Configuration} from any source such as files, databases,
 * or servers.
 *
 * <pre>
 *      Name {
 *            ModuleClass  Flag    ModuleOptions;
 *            ModuleClass  Flag    ModuleOptions;
 *            ModuleClass  Flag    ModuleOptions;
 *      };
 *      Name {
 *            ModuleClass  Flag    ModuleOptions;
 *            ModuleClass  Flag    ModuleOptions;
 *      };
 *      other {
 *            ModuleClass  Flag    ModuleOptions;
 *            ModuleClass  Flag    ModuleOptions;
 *      };
 * </pre>
 *
 * <p> Each entry in the {@code Configuration} is indexed via an
 * application name, <i>Name</i>, and contains a list of
 * LoginModules configured for that application.  Each {@code LoginModule}
 * is specified via its fully qualified class name.
 * Authentication proceeds down the module list in the exact order specified.
 * If an application does not have a specific entry,
 * it defaults to the specific entry for "<i>other</i>".
 *
 * <p> The <i>Flag</i> value controls the overall behavior as authentication
 * proceeds down the stack.  The following represents a description of the
 * valid values for <i>Flag</i> and their respective semantics:
 *
 * <pre>
 *      1) Required     - The {@code LoginModule} is required to succeed.
 *                      If it succeeds or fails, authentication still continues
 *                      to proceed down the {@code LoginModule} list.
 *
 *      2) Requisite    - The {@code LoginModule} is required to succeed.
 *                      If it succeeds, authentication continues down the
 *                      {@code LoginModule} list.  If it fails,
 *                      control immediately returns to the application
 *                      (authentication does not proceed down the
 *                      {@code LoginModule} list).
 *
 *      3) Sufficient   - The {@code LoginModule} is not required to
 *                      succeed.  If it does succeed, control immediately
 *                      returns to the application (authentication does not
 *                      proceed down the {@code LoginModule} list).
 *                      If it fails, authentication continues down the
 *                      {@code LoginModule} list.
 *
 *      4) Optional     - The {@code LoginModule} is not required to
 *                      succeed.  If it succeeds or fails,
 *                      authentication still continues to proceed down the
 *                      {@code LoginModule} list.
 * </pre>
 *
 * <p> The overall authentication succeeds only if all <i>Required</i> and
 * <i>Requisite</i> LoginModules succeed.  If a <i>Sufficient</i>
 * {@code LoginModule} is configured and succeeds,
 * then only the <i>Required</i> and <i>Requisite</i> LoginModules prior to
 * that <i>Sufficient</i> {@code LoginModule} need to have succeeded for
 * the overall authentication to succeed. If no <i>Required</i> or
 * <i>Requisite</i> LoginModules are configured for an application,
 * then at least one <i>Sufficient</i> or <i>Optional</i>
 * {@code LoginModule} must succeed.
 *
 * <p> <i>ModuleOptions</i> is a space separated list of
 * {@code LoginModule}-specific values which are passed directly to
 * the underlying LoginModules.  Options are defined by the
 * {@code LoginModule} itself, and control the behavior within it.
 * For example, a {@code LoginModule} may define options to support
 * debugging/testing capabilities.  The correct way to specify options in the
 * {@code Configuration} is by using the following key-value pairing:
 * <i>debug="true"</i>.  The key and value should be separated by an
 * 'equals' symbol, and the value should be surrounded by double quotes.
 * If a String in the form, ${system.property}, occurs in the value,
 * it will be expanded to the value of the system property.
 * Note that there is no limit to the number of
 * options a {@code LoginModule} may define.
 *
 * <p> The following represents an example {@code Configuration} entry
 * based on the syntax above:
 *
 * <pre>
 * Login {
 *   com.sun.security.auth.module.UnixLoginModule required;
 *   com.sun.security.auth.module.Krb5LoginModule optional
 *                   useTicketCache="true"
 *                   ticketCache="${user.home}${/}tickets";
 * };
 * </pre>
 *
 * <p> This {@code Configuration} specifies that an application named,
 * "Login", requires users to first authenticate to the
 * <i>com.sun.security.auth.module.UnixLoginModule</i>, which is
 * required to succeed.  Even if the <i>UnixLoginModule</i>
 * authentication fails, the
 * <i>com.sun.security.auth.module.Krb5LoginModule</i>
 * still gets invoked.  This helps hide the source of failure.
 * Since the <i>Krb5LoginModule</i> is <i>Optional</i>, the overall
 * authentication succeeds only if the <i>UnixLoginModule</i>
 * (<i>Required</i>) succeeds.
 *
 * <p> Also note that the LoginModule-specific options,
 * <i>useTicketCache="true"</i> and
 * <i>ticketCache=${user.home}${/}tickets"</i>,
 * are passed to the <i>Krb5LoginModule</i>.
 * These options instruct the <i>Krb5LoginModule</i> to
 * use the ticket cache at the specified location.
 * The system properties, <i>user.home</i> and <i>/</i>
 * (file.separator), are expanded to their respective values.
 *
 * <p> There is only one Configuration object installed in the runtime at any
 * given time.  A Configuration object can be installed by calling the
 * {@code setConfiguration} method.  The installed Configuration object
 * can be obtained by calling the {@code getConfiguration} method.
 *
 * <p> If no Configuration object has been installed in the runtime, a call to
 * {@code getConfiguration} installs an instance of the default
 * Configuration implementation (a default subclass implementation of this
 * abstract class).
 * The default Configuration implementation can be changed by setting the value
 * of the {@code login.configuration.provider} security property to the fully
 * qualified name of the desired Configuration subclass implementation.
 *
 * <p> Application code can directly subclass Configuration to provide a custom
 * implementation.  In addition, an instance of a Configuration object can be
 * constructed by invoking one of the {@code getInstance} factory methods
 * with a standard type.  The default policy type is "JavaLoginConfig".
 * See the Configuration section in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#Configuration">
 * Java Cryptography Architecture Standard Algorithm Name Documentation</a>
 * for a list of standard Configuration types.
 *
 * <p>
 *  Configuration对象负责指定应为特定应用程序使用哪些LoginModules,以及应以什么顺序调用LoginModules。
 * 
 *  <p>登录配置包含以下信息。请注意,此示例仅表示{@code Configuration}的默认语法。
 * 这个类的子类实现可以实现替代语法,并且可以从任何源(例如文件,数据库或服务器)检索{@code Configuration}。
 * 
 * <pre>
 *  名称{ModuleClass Flag ModuleOptions; ModuleClass Flag ModuleOptions; ModuleClass Flag ModuleOptions; }
 * ;名称{ModuleClass Flag ModuleOptions; ModuleClass Flag ModuleOptions; };其他{ModuleClass Flag ModuleOptions; ModuleClass Flag ModuleOptions; }
 * ;。
 * </pre>
 * 
 *  <p> {@code Configuration}中的每个条目都通过应用程序名称<i> Name </i>进行索引,并包含为该应用程序配置的LoginModule列表。
 * 每个{@code LoginModule}通过其完全限定类名指定。认证按照指定的顺序沿着模块列表向下进行。如果应用程序没有特定条目,则默认为"<i>其他</i>"的特定条目。
 * 
 * <p> <i> </i>值控制当认证沿着堆栈向下进行时的整体行为。以下代表对<i> Flag </i>及其相应语义的有效值的描述：
 * 
 * <pre>
 *  1)必需 - 需要{@code LoginModule}才能成功。如果成功或失败,身份验证仍将继续沿着{@code LoginModule}列表进行。
 * 
 *  2)必需 - 需要{@code LoginModule}才能成功。如果成功,身份验证会在{@code LoginModule}列表中继续。
 * 如果失败,控制立即返回到应用程序(认证不会沿着{@code LoginModule}列表进行)。
 * 
 *  3)足够 - 不要求{@code LoginModule}成功。如果它成功,控制立即返回到应用程序(认证不会沿着{@code LoginModule}列表进行)。
 * 如果失败,身份验证将在{@code LoginModule}列表中继续。
 * 
 *  4)可选 -  {@code LoginModule}不是必需的成功。如果成功或失败,身份验证仍将继续沿着{@code LoginModule}列表进行。
 * </pre>
 * 
 * <p>只有当所有<i>必需</i>和<i> Requisite </i> LoginModules成功时,整个身份验证才会成功。
 * 如果配置了<i>足够</i> {@code LoginModule}并成功,则只有<i>必需</i>和<i>必需</i>登录模块, i> {@code LoginModule}需要成功才能使整个身份验证
 * 成功。
 * <p>只有当所有<i>必需</i>和<i> Requisite </i> LoginModules成功时,整个身份验证才会成功。
 * 如果没有为应用程序配置<i>必需</i>或<i>必需</i>登录模块,则至少要有一个<i>足够</i>或<i>可选</i> {@code LoginModule}必须成功。
 * 
 *  <p> <i> ModuleOptions </i>是一个空格分隔的{@code LoginModule}特定值列表,它们直接传递给底层的LoginModules。
 * 选项由{@code LoginModule}本身定义,并控制其中的行为。例如,{@code LoginModule}可以定义支持调试/测试功能的选项。
 * 在{@code Configuration}中指定选项的正确方法是使用以下键值对：<i> debug ="true"</i>。键和值应由"等号"符号分隔,并且值应该用双引号括起来。
 * 如果在值中出现形式为$ {system.property}的字符串,它将扩展为系统属性的值。请注意,{@code LoginModule}可能定义的选项数量没有限制。
 * 
 *  <p>以下代表基于上述语法的{@code Configuration}条目：
 * 
 * <pre>
 * 登录{com.sun.security.auth.module.UnixLoginModule必需; com.sun.security.auth.module.Krb5LoginModule可选useTicketCache ="true"ticketCache ="$ {user.home}
 *  $ {/} ticket"; };。
 * </pre>
 * 
 *  <p>此{@code Configuration}指定名为"登录"的应用程序要求用户首先对<i> com.sun.security.auth.module.UnixLoginModule </i>进行
 * 身份验证,这是成功所必需的。
 * 即使<i> UnixLoginModule </i>身份验证失败,仍会调用<i> com.sun.security.auth.module.Krb5LoginModule </i>。
 * 这有助于隐藏故障源。
 * 由于<i> Krb5LoginModule </i>是<i>可选</i>,因此只有在<i> UnixLoginModule </i>(<i>必需</i>)成功时,整个身份验证才会成功。
 * 
 *  <p>另请注意,特定于LoginModule的选项<i> useTicketCache ="true"</i>和<i> ticketCache = $ {user.home} $ {/}票证</i>这
 * 些选项指示<b> Krb5LoginModule </i>在指定位置使用票证缓存。
 * 系统属性<i> user.home </i>和<i> / </i>(file.separator),展开为各自的值。
 * 
 *  <p>在任何给定时间,在运行时中只安装一个Configuration对象。可以通过调用{@code setConfiguration}方法安装Configuration对象。
 * 可以通过调用{@code getConfiguration}方法获取已安装的配置对象。
 * 
 * <p>如果在运行时中没有安装配置对象,调用{@code getConfiguration}会安装默认配置实现(这个抽象类的默认子类实现)的实例。
 * 可以通过将{@code login.configuration.provider}安全属性的值设置为所需配置子类实现的完全限定名称来更改默认配置实现。
 * 
 *  <p>应用程序代码可以直接子类化配置以提供自定义实现。此外,可以通过调用具有标准类型的{@code getInstance}工厂方法之一来构造Configuration对象的实例。
 * 默认策略类型为"JavaLoginConfig"。请参阅<a href =中的配置部分。
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#Configuration">
 *  Java加密架构标准算法名称文档</a>,以获取标准配置类型的列表。
 * 
 * @see javax.security.auth.login.LoginContext
 * @see java.security.Security security properties
 */
public abstract class Configuration {

    private static Configuration configuration;

    private final java.security.AccessControlContext acc =
            java.security.AccessController.getContext();

    private static void checkPermission(String type) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new AuthPermission
                                ("createLoginConfiguration." + type));
        }
    }

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     * 
     */
    protected Configuration() { }

    /**
     * Get the installed login Configuration.
     *
     * <p>
     *
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     * 
     * @return the login Configuration.  If a Configuration object was set
     *          via the {@code Configuration.setConfiguration} method,
     *          then that object is returned.  Otherwise, a default
     *          Configuration object is returned.
     *
     * @exception SecurityException if the caller does not have permission
     *                          to retrieve the Configuration.
     *
     * @see #setConfiguration
     */
    public static Configuration getConfiguration() {

        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new AuthPermission("getLoginConfiguration"));

        synchronized (Configuration.class) {
            if (configuration == null) {
                String config_class = null;
                config_class = AccessController.doPrivileged
                    (new PrivilegedAction<String>() {
                    public String run() {
                        return java.security.Security.getProperty
                                    ("login.configuration.provider");
                    }
                });
                if (config_class == null) {
                    config_class = "sun.security.provider.ConfigFile";
                }

                try {
                    final String finalClass = config_class;
                    Configuration untrustedImpl = AccessController.doPrivileged(
                            new PrivilegedExceptionAction<Configuration>() {
                                public Configuration run() throws ClassNotFoundException,
                                        InstantiationException,
                                        IllegalAccessException {
                                    Class<? extends Configuration> implClass = Class.forName(
                                            finalClass, false,
                                            Thread.currentThread().getContextClassLoader()
                                    ).asSubclass(Configuration.class);
                                    return implClass.newInstance();
                                }
                            });
                    AccessController.doPrivileged(
                            new PrivilegedExceptionAction<Void>() {
                                public Void run() {
                                    setConfiguration(untrustedImpl);
                                    return null;
                                }
                            }, Objects.requireNonNull(untrustedImpl.acc)
                    );
                } catch (PrivilegedActionException e) {
                    Exception ee = e.getException();
                    if (ee instanceof InstantiationException) {
                        throw (SecurityException) new
                            SecurityException
                                    ("Configuration error:" +
                                     ee.getCause().getMessage() +
                                     "\n").initCause(ee.getCause());
                    } else {
                        throw (SecurityException) new
                            SecurityException
                                    ("Configuration error: " +
                                     ee.toString() +
                                     "\n").initCause(ee);
                    }
                }
            }
            return configuration;
        }
    }

    /**
     * Set the login {@code Configuration}.
     *
     * <p>
     *
     * <p>
     *  获取安装的登录配置。
     * 
     * <p>
     * 
     * 
     * @param configuration the new {@code Configuration}
     *
     * @exception SecurityException if the current thread does not have
     *                  Permission to set the {@code Configuration}.
     *
     * @see #getConfiguration
     */
    public static void setConfiguration(Configuration configuration) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null)
            sm.checkPermission(new AuthPermission("setLoginConfiguration"));
        Configuration.configuration = configuration;
    }

    /**
     * Returns a Configuration object of the specified type.
     *
     * <p> This method traverses the list of registered security providers,
     * starting with the most preferred Provider.
     * A new Configuration object encapsulating the
     * ConfigurationSpi implementation from the first
     * Provider that supports the specified type is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  设置登录{@code Configuration}。
     * 
     * <p>
     * 
     * 
     * @param type the specified Configuration type.  See the Configuration
     *    section in the <a href=
     *    "{@docRoot}/../technotes/guides/security/StandardNames.html#Configuration">
     *    Java Cryptography Architecture Standard Algorithm Name
     *    Documentation</a> for a list of standard Configuration types.
     *
     * @param params parameters for the Configuration, which may be null.
     *
     * @return the new Configuration object.
     *
     * @exception SecurityException if the caller does not have permission
     *          to get a Configuration instance for the specified type.
     *
     * @exception NullPointerException if the specified type is null.
     *
     * @exception IllegalArgumentException if the specified parameters
     *          are not understood by the ConfigurationSpi implementation
     *          from the selected Provider.
     *
     * @exception NoSuchAlgorithmException if no Provider supports a
     *          ConfigurationSpi implementation for the specified type.
     *
     * @see Provider
     * @since 1.6
     */
    public static Configuration getInstance(String type,
                                Configuration.Parameters params)
                throws NoSuchAlgorithmException {

        checkPermission(type);
        try {
            GetInstance.Instance instance = GetInstance.getInstance
                                                        ("Configuration",
                                                        ConfigurationSpi.class,
                                                        type,
                                                        params);
            return new ConfigDelegate((ConfigurationSpi)instance.impl,
                                                        instance.provider,
                                                        type,
                                                        params);
        } catch (NoSuchAlgorithmException nsae) {
            return handleException (nsae);
        }
    }

    /**
     * Returns a Configuration object of the specified type.
     *
     * <p> A new Configuration object encapsulating the
     * ConfigurationSpi implementation from the specified provider
     * is returned.   The specified provider must be registered
     * in the provider list.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回指定类型的配置对象。
     * 
     *  <p>此方法遍历注册的安全提供程序列表,从最优先的提供程序开始。将返回一个新的Configuration对象,用于封装来自支持指定类型的第一个Provider的ConfigurationSpi实现。
     * 
     * <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param type the specified Configuration type.  See the Configuration
     *    section in the <a href=
     *    "{@docRoot}/../technotes/guides/security/StandardNames.html#Configuration">
     *    Java Cryptography Architecture Standard Algorithm Name
     *    Documentation</a> for a list of standard Configuration types.
     *
     * @param params parameters for the Configuration, which may be null.
     *
     * @param provider the provider.
     *
     * @return the new Configuration object.
     *
     * @exception SecurityException if the caller does not have permission
     *          to get a Configuration instance for the specified type.
     *
     * @exception NullPointerException if the specified type is null.
     *
     * @exception IllegalArgumentException if the specified provider
     *          is null or empty,
     *          or if the specified parameters are not understood by
     *          the ConfigurationSpi implementation from the specified provider.
     *
     * @exception NoSuchProviderException if the specified provider is not
     *          registered in the security provider list.
     *
     * @exception NoSuchAlgorithmException if the specified provider does not
     *          support a ConfigurationSpi implementation for the specified
     *          type.
     *
     * @see Provider
     * @since 1.6
     */
    public static Configuration getInstance(String type,
                                Configuration.Parameters params,
                                String provider)
                throws NoSuchProviderException, NoSuchAlgorithmException {

        if (provider == null || provider.length() == 0) {
            throw new IllegalArgumentException("missing provider");
        }

        checkPermission(type);
        try {
            GetInstance.Instance instance = GetInstance.getInstance
                                                        ("Configuration",
                                                        ConfigurationSpi.class,
                                                        type,
                                                        params,
                                                        provider);
            return new ConfigDelegate((ConfigurationSpi)instance.impl,
                                                        instance.provider,
                                                        type,
                                                        params);
        } catch (NoSuchAlgorithmException nsae) {
            return handleException (nsae);
        }
    }

    /**
     * Returns a Configuration object of the specified type.
     *
     * <p> A new Configuration object encapsulating the
     * ConfigurationSpi implementation from the specified Provider
     * object is returned.  Note that the specified Provider object
     * does not have to be registered in the provider list.
     *
     * <p>
     *  返回指定类型的配置对象。
     * 
     *  <p>将返回一个新的Configuration对象,用于封装来自指定提供程序的ConfigurationSpi实现。指定的提供程序必须在提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param type the specified Configuration type.  See the Configuration
     *    section in the <a href=
     *    "{@docRoot}/../technotes/guides/security/StandardNames.html#Configuration">
     *    Java Cryptography Architecture Standard Algorithm Name
     *    Documentation</a> for a list of standard Configuration types.
     *
     * @param params parameters for the Configuration, which may be null.
     *
     * @param provider the Provider.
     *
     * @return the new Configuration object.
     *
     * @exception SecurityException if the caller does not have permission
     *          to get a Configuration instance for the specified type.
     *
     * @exception NullPointerException if the specified type is null.
     *
     * @exception IllegalArgumentException if the specified Provider is null,
     *          or if the specified parameters are not understood by
     *          the ConfigurationSpi implementation from the specified Provider.
     *
     * @exception NoSuchAlgorithmException if the specified Provider does not
     *          support a ConfigurationSpi implementation for the specified
     *          type.
     *
     * @see Provider
     * @since 1.6
     */
    public static Configuration getInstance(String type,
                                Configuration.Parameters params,
                                Provider provider)
                throws NoSuchAlgorithmException {

        if (provider == null) {
            throw new IllegalArgumentException("missing provider");
        }

        checkPermission(type);
        try {
            GetInstance.Instance instance = GetInstance.getInstance
                                                        ("Configuration",
                                                        ConfigurationSpi.class,
                                                        type,
                                                        params,
                                                        provider);
            return new ConfigDelegate((ConfigurationSpi)instance.impl,
                                                        instance.provider,
                                                        type,
                                                        params);
        } catch (NoSuchAlgorithmException nsae) {
            return handleException (nsae);
        }
    }

    private static Configuration handleException(NoSuchAlgorithmException nsae)
                throws NoSuchAlgorithmException {
        Throwable cause = nsae.getCause();
        if (cause instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)cause;
        }
        throw nsae;
    }

    /**
     * Return the Provider of this Configuration.
     *
     * <p> This Configuration instance will only have a Provider if it
     * was obtained via a call to {@code Configuration.getInstance}.
     * Otherwise this method returns null.
     *
     * <p>
     *  返回指定类型的配置对象。
     * 
     *  <p>返回一个新的Configuration对象,用于封装来自指定的Provider对象的ConfigurationSpi实现。请注意,指定的Provider对象不必在提供程序列表中注册。
     * 
     * 
     * @return the Provider of this Configuration, or null.
     *
     * @since 1.6
     */
    public Provider getProvider() {
        return null;
    }

    /**
     * Return the type of this Configuration.
     *
     * <p> This Configuration instance will only have a type if it
     * was obtained via a call to {@code Configuration.getInstance}.
     * Otherwise this method returns null.
     *
     * <p>
     *  返回此配置的提供程序。
     * 
     *  <p>此配置实例将只有一个提供者,如果它是通过调用{@code Configuration.getInstance}获得。否则,此方法返回null。
     * 
     * 
     * @return the type of this Configuration, or null.
     *
     * @since 1.6
     */
    public String getType() {
        return null;
    }

    /**
     * Return Configuration parameters.
     *
     * <p> This Configuration instance will only have parameters if it
     * was obtained via a call to {@code Configuration.getInstance}.
     * Otherwise this method returns null.
     *
     * <p>
     *  返回此配置的类型。
     * 
     *  <p>此配置实例将只有一个类型,如果它是通过调用{@code Configuration.getInstance}获得的。否则,此方法返回null。
     * 
     * 
     * @return Configuration parameters, or null.
     *
     * @since 1.6
     */
    public Configuration.Parameters getParameters() {
        return null;
    }

    /**
     * Retrieve the AppConfigurationEntries for the specified <i>name</i>
     * from this Configuration.
     *
     * <p>
     *
     * <p>
     *  返回配置参数。
     * 
     *  <p>此配置实例只有通过调用{@code Configuration.getInstance}获得的参数。否则,此方法返回null。
     * 
     * 
     * @param name the name used to index the Configuration.
     *
     * @return an array of AppConfigurationEntries for the specified <i>name</i>
     *          from this Configuration, or null if there are no entries
     *          for the specified <i>name</i>
     */
    public abstract AppConfigurationEntry[] getAppConfigurationEntry
                                                        (String name);

    /**
     * Refresh and reload the Configuration.
     *
     * <p> This method causes this Configuration object to refresh/reload its
     * contents in an implementation-dependent manner.
     * For example, if this Configuration object stores its entries in a file,
     * calling {@code refresh} may cause the file to be re-read.
     *
     * <p> The default implementation of this method does nothing.
     * This method should be overridden if a refresh operation is supported
     * by the implementation.
     *
     * <p>
     *  从此配置中检索指定<i>名称</i>的AppConfigurationEntries。
     * 
     * <p>
     * 
     * 
     * @exception SecurityException if the caller does not have permission
     *                          to refresh its Configuration.
     */
    public void refresh() { }

    /**
     * This subclass is returned by the getInstance calls.  All Configuration
     * calls are delegated to the underlying ConfigurationSpi.
     * <p>
     * 刷新并重新加载配置。
     * 
     *  <p>此方法导致此配置对象以实现相关方式刷新/重新加载其内容。例如,如果此配置对象将其条目存储在文件中,则调用{@code refresh}可能会导致该文件被重新读取。
     * 
     *  <p>此方法的默认实现不执行任何操作。如果实现支持刷新操作,则应覆盖此方法。
     * 
     */
    private static class ConfigDelegate extends Configuration {

        private ConfigurationSpi spi;
        private Provider p;
        private String type;
        private Configuration.Parameters params;

        private ConfigDelegate(ConfigurationSpi spi, Provider p,
                        String type, Configuration.Parameters params) {
            this.spi = spi;
            this.p = p;
            this.type = type;
            this.params = params;
        }

        public String getType() { return type; }

        public Configuration.Parameters getParameters() { return params; }

        public Provider getProvider() { return p; }

        public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
            return spi.engineGetAppConfigurationEntry(name);
        }

        public void refresh() {
            spi.engineRefresh();
        }
    }

    /**
     * This represents a marker interface for Configuration parameters.
     *
     * <p>
     * 
     * @since 1.6
     */
    public static interface Parameters { }
}
