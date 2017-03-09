/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

import java.net.URI;
import java.util.*;
import static java.security.KeyStore.*;

/**
 * Configuration data that specifies the keystores in a keystore domain.
 * A keystore domain is a collection of keystores that are presented as a
 * single logical keystore. The configuration data is used during
 * {@code KeyStore}
 * {@link KeyStore#load(KeyStore.LoadStoreParameter) load} and
 * {@link KeyStore#store(KeyStore.LoadStoreParameter) store} operations.
 * <p>
 * The following syntax is supported for configuration data:
 * <pre>{@code
 *     domain <domainName> [<property> ...] {
 *         keystore <keystoreName> [<property> ...] ;
 *         ...
 *     };
 *     ...
 * }</pre>
 * where {@code domainName} and {@code keystoreName} are identifiers
 * and {@code property} is a key/value pairing. The key and value are
 * separated by an 'equals' symbol and the value is enclosed in double
 * quotes. A property value may be either a printable string or a binary
 * string of colon-separated pairs of hexadecimal digits. Multi-valued
 * properties are represented as a comma-separated list of values,
 * enclosed in square brackets.
 * See {@link Arrays#toString(java.lang.Object[])}.
 * <p>
 * To ensure that keystore entries are uniquely identified, each
 * entry's alias is prefixed by its {@code keystoreName} followed
 * by the entry name separator and each {@code keystoreName} must be
 * unique within its domain. Entry name prefixes are omitted when
 * storing a keystore.
 * <p>
 * Properties are context-sensitive: properties that apply to
 * all the keystores in a domain are located in the domain clause,
 * and properties that apply only to a specific keystore are located
 * in that keystore's clause.
 * Unless otherwise specified, a property in a keystore clause overrides
 * a property of the same name in the domain clause. All property names
 * are case-insensitive. The following properties are supported:
 * <dl>
 * <dt> {@code keystoreType="<type>"} </dt>
 *     <dd> The keystore type. </dd>
 * <dt> {@code keystoreURI="<url>"} </dt>
 *     <dd> The keystore location. </dd>
 * <dt> {@code keystoreProviderName="<name>"} </dt>
 *     <dd> The name of the keystore's JCE provider. </dd>
 * <dt> {@code keystorePasswordEnv="<environment-variable>"} </dt>
 *     <dd> The environment variable that stores a keystore password.
 *          Alternatively, passwords may be supplied to the constructor
 *          method in a {@code Map<String, ProtectionParameter>}. </dd>
 * <dt> {@code entryNameSeparator="<separator>"} </dt>
 *     <dd> The separator between a keystore name prefix and an entry name.
 *          When specified, it applies to all the entries in a domain.
 *          Its default value is a space. </dd>
 * </dl>
 * <p>
 * For example, configuration data for a simple keystore domain
 * comprising three keystores is shown below:
 * <pre>
 *
 * domain app1 {
 *     keystore app1-truststore
 *         keystoreURI="file:///app1/etc/truststore.jks";
 *
 *     keystore system-truststore
 *         keystoreURI="${java.home}/lib/security/cacerts";
 *
 *     keystore app1-keystore
 *         keystoreType="PKCS12"
 *         keystoreURI="file:///app1/etc/keystore.p12";
 * };
 *
 * </pre>
 * <p>
 *  指定密钥库域中的密钥库的配置数据。密钥库域是作为单个逻辑密钥库显示的密钥库集合。
 * 配置数据在{@code KeyStore} {@link KeyStore#load(KeyStore.LoadStoreParameter)load}和{@link KeyStore#store(KeyStore.LoadStoreParameter)store}
 * 操作中使用。
 *  指定密钥库域中的密钥库的配置数据。密钥库域是作为单个逻辑密钥库显示的密钥库集合。
 * <p>
 *  配置数据支持以下语法：<pre> {@ code domain <domainName> [<property> ...] {keystore <keystoreName> [<property> ...] ...}
 * ; ...} </pre>其中{@code domainName}和{@code keystoreName}是标识符,{@code property}是键/值对。
 * 键和值由"等号"符号分隔,并且值用双引号括起来。属性值可以是可打印字符串或十六进制数字的冒号分隔的对的二进制字符串。多值属性表示为用逗号分隔的值列表,用方括号括起来。
 * 参见{@link Arrays#toString(java.lang.Object [])}。
 * <p>
 *  为了确保密钥库条目被唯一标识,每个条目的别名都以其{@code keystoreName}后缀为条目名称分隔符为前缀,每个{@code keystoreName}在其域中必须是唯一的。
 * 存储密钥库时省略条目名称前缀。
 * <p>
 * 属性是上下文敏感的：应用于域中所有密钥库的属性位于domain子句中,并且仅应用于特定密钥库的属性位于该密钥库子句中。除非另有说明,keystore子句中的属性将覆盖domain子句中同名的属性。
 * 所有属性名称都不区分大小写。支持以下属性：。
 * <dl>
 *  <dt> {@code keystoreType ="<type>"} </dt> <dd>密钥库类型。
 *  </dd> <dt> {@code keystoreURI ="<url>"} </dt> <dd>密钥库位置。
 *  </dd> <dt> {@code keystoreProviderName ="<name>"} </dt> <dd>密钥库的JCE提供程序的名称。
 *  </dd> <dt> {@code keystorePasswordEnv ="<environment-variable>"} </dt> <dd>存储密钥库密码的环境变量。
 * 或者,可以在{@code Map <String,ProtectionParameter>}中为构造函数方法提供密码。
 *  </dd> <dt> {@code entryNameSeparator ="<separator>"} </dt> <dd>密钥库名称前缀和条目名称之间的分隔符。指定时,它适用于域中的所有条目。
 * 它的默认值是一个空格。 </dd>。
 * </dl>
 * 
 * @since 1.8
 */
public final class DomainLoadStoreParameter implements LoadStoreParameter {

    private final URI configuration;
    private final Map<String,ProtectionParameter> protectionParams;

    /**
     * Constructs a DomainLoadStoreParameter for a keystore domain with
     * the parameters used to protect keystore data.
     *
     * <p>
     * <p>
     *  例如,包括三个密钥库的简单密钥库域的配置数据如下所示：
     * <pre>
     * 
     *  domain app1 {keystore app1-truststore keystoreURI ="file：///app1/etc/truststore.jks";
     * 
     *  keystore system-truststore keystoreURI ="$ {java.home} / lib / security / cacerts";
     * 
     *  keystore app1-keystore keystoreType ="PKCS12"keystoreURI ="file：///app1/etc/keystore.p12"; };
     * 
     * </pre>
     * 
     * @param configuration identifier for the domain configuration data.
     *     The name of the target domain should be specified in the
     *     {@code java.net.URI} fragment component when it is necessary
     *     to distinguish between several domain configurations at the
     *     same location.
     *
     * @param protectionParams the map from keystore name to the parameter
     *     used to protect keystore data.
     *     A {@code java.util.Collections.EMPTY_MAP} should be used
     *     when protection parameters are not required or when they have
     *     been specified by properties in the domain configuration data.
     *     It is cloned to prevent subsequent modification.
     *
     * @exception NullPointerException if {@code configuration} or
     *     {@code protectionParams} is {@code null}
     */
    public DomainLoadStoreParameter(URI configuration,
        Map<String,ProtectionParameter> protectionParams) {
        if (configuration == null || protectionParams == null) {
            throw new NullPointerException("invalid null input");
        }
        this.configuration = configuration;
        this.protectionParams =
            Collections.unmodifiableMap(new HashMap<>(protectionParams));
    }

    /**
     * Gets the identifier for the domain configuration data.
     *
     * <p>
     * 使用用于保护密钥库数据的参数构造密钥库域的DomainLoadStoreParameter。
     * 
     * 
     * @return the identifier for the configuration data
     */
    public URI getConfiguration() {
        return configuration;
    }

    /**
     * Gets the keystore protection parameters for keystores in this
     * domain.
     *
     * <p>
     *  获取域配置数据的标识符。
     * 
     * 
     * @return an unmodifiable map of keystore names to protection
     *     parameters
     */
    public Map<String,ProtectionParameter> getProtectionParams() {
        return protectionParams;
    }

    /**
     * Gets the keystore protection parameters for this domain.
     * Keystore domains do not support a protection parameter.
     *
     * <p>
     *  获取此域中密钥库的密钥库保护参数。
     * 
     * 
     * @return always returns {@code null}
     */
    @Override
    public KeyStore.ProtectionParameter getProtectionParameter() {
        return null;
    }
}
