/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.auth.login;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.net.URI;

// NOTE: As of JDK 8, this class instantiates
// sun.security.provider.ConfigFile.Spi and forwards all methods to that
// implementation. All implementation fixes and enhancements should be made to
// sun.security.provider.ConfigFile.Spi and not this class.
// See JDK-8005117 for more information.

/**
 * This class represents a default implementation for
 * {@code javax.security.auth.login.Configuration}.
 *
 * <p> This object stores the runtime login configuration representation,
 * and is the amalgamation of multiple static login
 * configurations that resides in files.
 * The algorithm for locating the login configuration file(s) and reading their
 * information into this {@code Configuration} object is:
 *
 * <ol>
 * <li>
 *   Loop through the security properties,
 *   <i>login.config.url.1</i>, <i>login.config.url.2</i>, ...,
 *   <i>login.config.url.X</i>.
 *   Each property value specifies a {@code URL} pointing to a
 *   login configuration file to be loaded.  Read in and load
 *   each configuration.
 *
 * <li>
 *   The {@code java.lang.System} property
 *   <i>java.security.auth.login.config</i>
 *   may also be set to a {@code URL} pointing to another
 *   login configuration file
 *   (which is the case when a user uses the -D switch at runtime).
 *   If this property is defined, and its use is allowed by the
 *   security property file (the Security property,
 *   <i>policy.allowSystemProperty</i> is set to <i>true</i>),
 *   also load that login configuration.
 *
 * <li>
 *   If the <i>java.security.auth.login.config</i> property is defined using
 *   "==" (rather than "="), then ignore all other specified
 *   login configurations and only load this configuration.
 *
 * <li>
 *   If no system or security properties were set, try to read from the file,
 *   ${user.home}/.java.login.config, where ${user.home} is the value
 *   represented by the "user.home" System property.
 * </ol>
 *
 * <p> The configuration syntax supported by this implementation
 * is exactly that syntax specified in the
 * {@code javax.security.auth.login.Configuration} class.
 *
 * <p>
 *  此类表示{@code javax.security.auth.login.Configuration}的默认实现。
 * 
 *  <p>此对象存储运行时登录配置表示,并且是驻留在文件中的多个静态登录配置的合并。用于定位登录配置文件并将其信息读入此{@code Configuration}对象的算法是：
 * 
 * <ol>
 * <li>
 *  循环浏览安全属性<i> login.config.url.1 </i>,<i> login.config.url.2 </i>,...,<i> login.config.url.X </i>。
 * 每个属性值指定一个{@code URL}指向要加载的登录配置文件。读入并加载每个配置。
 * 
 * <li>
 *  {@code java.lang.System}属性<i> java.security.auth.login.config </i>也可能设置为指向另一个登录配置文件的{@code URL}(这是用户
 * 在运行时使用-D开关)。
 * 如果定义此属性,并且安全属性文件允许其使用(Security属性,<i> policy.allowSystemProperty </i>设置为<i> true </i>),也请加载该登录配置。
 * 
 * <li>
 *  如果使用"=="(而不是"=")定义<i> java.security.auth.login.config </i>属性,则忽略所有其他指定的登录配置,并且只加载此配置。
 * 
 * <li>
 * 
 * @see javax.security.auth.login.LoginContext
 * @see java.security.Security security properties
 */
@jdk.Exported
public class ConfigFile extends Configuration {

    private final sun.security.provider.ConfigFile.Spi spi;

    /**
     * Create a new {@code Configuration} object.
     *
     * <p>
     * 如果未设置系统或安全属性,请尝试从文件$ {user.home} /。java.login.config中读取,其中$ {user.home}是由"user.home"表示的值系统属性。
     * </ol>
     * 
     *  <p>此实现支持的配置语法与{@code javax.security.auth.login.Configuration}类中指定的语法完全相同。
     * 
     * 
     * @throws SecurityException if the {@code Configuration} can not be
     *                           initialized
     */
    public ConfigFile() {
        spi = new sun.security.provider.ConfigFile.Spi();
    }

    /**
     * Create a new {@code Configuration} object from the specified {@code URI}.
     *
     * <p>
     *  创建一个新的{@code Configuration}对象。
     * 
     * 
     * @param uri the {@code URI}
     * @throws SecurityException if the {@code Configuration} can not be
     *                           initialized
     * @throws NullPointerException if {@code uri} is null
     */
    public ConfigFile(URI uri) {
        spi = new sun.security.provider.ConfigFile.Spi(uri);
    }

    /**
     * Retrieve an entry from the {@code Configuration} using an application
     * name as an index.
     *
     * <p>
     *  从指定的{@code URI}创建一个新的{@code Configuration}对象。
     * 
     * 
     * @param applicationName the name used to index the {@code Configuration}
     * @return an array of {@code AppConfigurationEntry} which correspond to
     *         the stacked configuration of {@code LoginModule}s for this
     *         application, or null if this application has no configured
     *         {@code LoginModule}s.
     */
    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry
        (String applicationName) {

        return spi.engineGetAppConfigurationEntry(applicationName);
    }

    /**
     * Refresh and reload the {@code Configuration} by re-reading all of the
     * login configurations.
     *
     * <p>
     *  使用应用程序名称作为索引从{@code Configuration}中检索条目。
     * 
     * 
     * @throws SecurityException if the caller does not have permission
     *                           to refresh the {@code Configuration}
     */
    @Override
    public void refresh() {
        spi.engineRefresh();
    }
}
