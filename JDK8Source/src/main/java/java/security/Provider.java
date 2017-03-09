/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.io.*;
import java.util.*;
import static java.util.Locale.ENGLISH;
import java.lang.ref.*;
import java.lang.reflect.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class represents a "provider" for the
 * Java Security API, where a provider implements some or all parts of
 * Java Security. Services that a provider may implement include:
 *
 * <ul>
 *
 * <li>Algorithms (such as DSA, RSA, MD5 or SHA-1).
 *
 * <li>Key generation, conversion, and management facilities (such as for
 * algorithm-specific keys).
 *
 *</ul>
 *
 * <p>Each provider has a name and a version number, and is configured
 * in each runtime it is installed in.
 *
 * <p>See <a href =
 * "../../../technotes/guides/security/crypto/CryptoSpec.html#Provider">The Provider Class</a>
 * in the "Java Cryptography Architecture API Specification &amp; Reference"
 * for information about how a particular type of provider, the
 * cryptographic service provider, works and is installed. However,
 * please note that a provider can be used to implement any security
 * service in Java that uses a pluggable architecture with a choice
 * of implementations that fit underneath.
 *
 * <p>Some provider implementations may encounter unrecoverable internal
 * errors during their operation, for example a failure to communicate with a
 * security token. A {@link ProviderException} should be used to indicate
 * such errors.
 *
 * <p>The service type {@code Provider} is reserved for use by the
 * security framework. Services of this type cannot be added, removed,
 * or modified by applications.
 * The following attributes are automatically placed in each Provider object:
 * <table cellspacing=4>
 * <caption><b>Attributes Automatically Placed in a Provider Object</b></caption>
 * <tr><th>Name</th><th>Value</th>
 * <tr><td>{@code Provider.id name}</td>
  *    <td>{@code String.valueOf(provider.getName())}</td>
 * <tr><td>{@code Provider.id version}</td>
 *     <td>{@code String.valueOf(provider.getVersion())}</td>
 * <tr><td>{@code Provider.id info}</td>
       <td>{@code String.valueOf(provider.getInfo())}</td>
 * <tr><td>{@code Provider.id className}</td>
 *     <td>{@code provider.getClass().getName()}</td>
 * </table>
 *
 * <p>
 *  此类表示Java安全API的"提供程序",其中提供程序实现Java安全性的某些或所有部分。提供商可以实施的服务包括：
 * 
 * <ul>
 * 
 *  <li>算法(例如DSA,RSA,MD5或SHA-1)。
 * 
 *  <li>密钥生成,转换和管理功能(例如针对算法特定密钥)。
 * 
 * /ul>
 * 
 *  <p>每个提供程序都有一个名称和一个版本号,并在安装的每个运行时配置。
 * 
 *  <p>请参阅"Java加密架构API规范&amp;实用程序"中的<a href = "../../../technotes/guides/security/crypto/CryptoSpec.html#Provider">
 * 提供程序类</a>。
 * 参考"以获得关于特定类型的提供者,加密服务提供者如何工作和安装的信息。但是,请注意,提供程序可以用于实现Java中的任何安全服务,该安全服务使用可插入架构,可选择适合其下的实施。
 * 
 *  <p>某些提供程序实施可能在其操作期间遇到不可恢复的内部错误,例如无法与安全令牌通信。应使用{@link ProviderException}来指示此类错误。
 * 
 * <p>服务类型{@code Provider}保留供安全框架使用。此类服务无法由应用程序添加,删除或修改。以下属性自动放置在每个Provider对象中：
 * <table cellspacing=4>
 *  <caption> <b>自动置于提供者对象中的属性</b> </caption> <tr> <th>名称</th> <th>值</th> <tr> <td> {@ code Provider .id name}
 *  </td> <td> {@ code String.valueOf(provider.getName())} </td> <tr> <td> {@ code Provider.id version} 
 * </td> <td> {@code String.valueOf(provider.getVersion())} </td> <tr> <td> {@ code Provider.id info} </td>
 *  <td> {@ code String.valueOf(provider.getInfo )}} </td> <tr> <td> {@ code Provider.id className} </td>
 *  <td> {@ code provider.getClass()。
 * getName()} </td>。
 * </table>
 * 
 * 
 * @author Benjamin Renaud
 * @author Andreas Sterbenz
 */
public abstract class Provider extends Properties {

    // Declare serialVersionUID to be compatible with JDK1.1
    static final long serialVersionUID = -4298000515446427739L;

    private static final sun.security.util.Debug debug =
        sun.security.util.Debug.getInstance
        ("provider", "Provider");

    /**
     * The provider name.
     *
     * <p>
     *  提供程序名称。
     * 
     * 
     * @serial
     */
    private String name;

    /**
     * A description of the provider and its services.
     *
     * <p>
     *  提供商及其服务的描述。
     * 
     * 
     * @serial
     */
    private String info;

    /**
     * The provider version number.
     *
     * <p>
     *  提供程序版本号。
     * 
     * 
     * @serial
     */
    private double version;


    private transient Set<Map.Entry<Object,Object>> entrySet = null;
    private transient int entrySetCallCount = 0;

    private transient boolean initialized;

    /**
     * Constructs a provider with the specified name, version number,
     * and information.
     *
     * <p>
     *  构造具有指定的名称,版本号和信息的提供程序。
     * 
     * 
     * @param name the provider name.
     *
     * @param version the provider version number.
     *
     * @param info a description of the provider and its services.
     */
    protected Provider(String name, double version, String info) {
        this.name = name;
        this.version = version;
        this.info = info;
        putId();
        initialized = true;
    }

    /**
     * Returns the name of this provider.
     *
     * <p>
     *  返回此提供程序的名称。
     * 
     * 
     * @return the name of this provider.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the version number for this provider.
     *
     * <p>
     *  返回此提供程序的版本号。
     * 
     * 
     * @return the version number for this provider.
     */
    public double getVersion() {
        return version;
    }

    /**
     * Returns a human-readable description of the provider and its
     * services.  This may return an HTML page, with relevant links.
     *
     * <p>
     *  返回提供者及其服务的人类可读描述。这可能返回一个HTML页面,其中包含相关链接。
     * 
     * 
     * @return a description of the provider and its services.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Returns a string with the name and the version number
     * of this provider.
     *
     * <p>
     *  返回包含此提供程序的名称和版本号的字符串。
     * 
     * 
     * @return the string with the name and the version number
     * for this provider.
     */
    public String toString() {
        return name + " version " + version;
    }

    /*
     * override the following methods to ensure that provider
     * information can only be changed if the caller has the appropriate
     * permissions.
     * <p>
     *  覆盖以下方法以确保仅当调用者具有适当的权限时才能更改提供程序信息。
     * 
     */

    /**
     * Clears this provider so that it no longer contains the properties
     * used to look up facilities implemented by the provider.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "clearProviderProperties."+name}
     * (where {@code name} is the provider name) to see if it's ok to clear
     * this provider.
     *
     * <p>
     *  清除此提供程序,以使其不再包含用于查找提供程序实现的设施的属性。
     * 
     * <p>如果启用了安全管理器,则会使用字符串{@code"clearProviderProperties。
     * "+ name}(其中{@code name}是提供程序名称)调用其{@code checkSecurityAccess}方法,以查看是否可以清除此提供程序。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to clear this provider
     *
     * @since 1.2
     */
    @Override
    public synchronized void clear() {
        check("clearProviderProperties."+name);
        if (debug != null) {
            debug.println("Remove " + name + " provider properties");
        }
        implClear();
    }

    /**
     * Reads a property list (key and element pairs) from the input stream.
     *
     * <p>
     *  从输入流读取属性列表(键和元素对)。
     * 
     * 
     * @param inStream   the input stream.
     * @exception  IOException  if an error occurred when reading from the
     *               input stream.
     * @see java.util.Properties#load
     */
    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        check("putProviderProperty."+name);
        if (debug != null) {
            debug.println("Load " + name + " provider properties");
        }
        Properties tempProperties = new Properties();
        tempProperties.load(inStream);
        implPutAll(tempProperties);
    }

    /**
     * Copies all of the mappings from the specified Map to this provider.
     * These mappings will replace any properties that this provider had
     * for any of the keys currently in the specified Map.
     *
     * <p>
     *  将指定映射中的所有映射复制到此提供程序。这些映射将替换此提供者对于当前在指定映射中的任何键具有的任何属性。
     * 
     * 
     * @since 1.2
     */
    @Override
    public synchronized void putAll(Map<?,?> t) {
        check("putProviderProperty."+name);
        if (debug != null) {
            debug.println("Put all " + name + " provider properties");
        }
        implPutAll(t);
    }

    /**
     * Returns an unmodifiable Set view of the property entries contained
     * in this Provider.
     *
     * <p>
     *  返回此提供程序中包含的属性条目的不可修改的Set视图。
     * 
     * 
     * @see   java.util.Map.Entry
     * @since 1.2
     */
    @Override
    public synchronized Set<Map.Entry<Object,Object>> entrySet() {
        checkInitialized();
        if (entrySet == null) {
            if (entrySetCallCount++ == 0)  // Initial call
                entrySet = Collections.unmodifiableMap(this).entrySet();
            else
                return super.entrySet();   // Recursive call
        }

        // This exception will be thrown if the implementation of
        // Collections.unmodifiableMap.entrySet() is changed such that it
        // no longer calls entrySet() on the backing Map.  (Provider's
        // entrySet implementation depends on this "implementation detail",
        // which is unlikely to change.
        if (entrySetCallCount != 2)
            throw new RuntimeException("Internal error.");

        return entrySet;
    }

    /**
     * Returns an unmodifiable Set view of the property keys contained in
     * this provider.
     *
     * <p>
     *  返回此提供程序中包含的属性键的不可修改的Set视图。
     * 
     * 
     * @since 1.2
     */
    @Override
    public Set<Object> keySet() {
        checkInitialized();
        return Collections.unmodifiableSet(super.keySet());
    }

    /**
     * Returns an unmodifiable Collection view of the property values
     * contained in this provider.
     *
     * <p>
     *  返回此提供程序中包含的属性值的不可修改的集合视图。
     * 
     * 
     * @since 1.2
     */
    @Override
    public Collection<Object> values() {
        checkInitialized();
        return Collections.unmodifiableCollection(super.values());
    }

    /**
     * Sets the {@code key} property to have the specified
     * {@code value}.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "putProviderProperty."+name},
     * where {@code name} is the provider name, to see if it's ok to set this
     * provider's property values.
     *
     * <p>
     *  将{@code key}属性设置为指定的{@code value}。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,设置此提供程序的属性值。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values.
     *
     * @since 1.2
     */
    @Override
    public synchronized Object put(Object key, Object value) {
        check("putProviderProperty."+name);
        if (debug != null) {
            debug.println("Set " + name + " provider property [" +
                          key + "/" + value +"]");
        }
        return implPut(key, value);
    }

    /**
     * If the specified key is not already associated with a value (or is mapped
     * to {@code null}) associates it with the given value and returns
     * {@code null}, else returns the current value.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "putProviderProperty."+name},
     * where {@code name} is the provider name, to see if it's ok to set this
     * provider's property values.
     *
     * <p>
     *  如果指定的键尚未与某个值相关联(或映射到{@code null}),则将其与给定值相关联并返回{@code null},否则返回当前值。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,设置此提供程序的属性值。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values.
     *
     * @since 1.8
     */
    @Override
    public synchronized Object putIfAbsent(Object key, Object value) {
        check("putProviderProperty."+name);
        if (debug != null) {
            debug.println("Set " + name + " provider property [" +
                          key + "/" + value +"]");
        }
        return implPutIfAbsent(key, value);
    }

    /**
     * Removes the {@code key} property (and its corresponding
     * {@code value}).
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "removeProviderProperty."+name},
     * where {@code name} is the provider name, to see if it's ok to remove this
     * provider's properties.
     *
     * <p>
     * 删除{@code key}属性(及其对应的{@code value})。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,删除此提供程序的属性。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to remove this provider's properties.
     *
     * @since 1.2
     */
    @Override
    public synchronized Object remove(Object key) {
        check("removeProviderProperty."+name);
        if (debug != null) {
            debug.println("Remove " + name + " provider property " + key);
        }
        return implRemove(key);
    }

    /**
     * Removes the entry for the specified key only if it is currently
     * mapped to the specified value.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "removeProviderProperty."+name},
     * where {@code name} is the provider name, to see if it's ok to remove this
     * provider's properties.
     *
     * <p>
     *  仅当指定键的条目当前映射到指定值时,才删除该条目。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,删除此提供程序的属性。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to remove this provider's properties.
     *
     * @since 1.8
     */
    @Override
    public synchronized boolean remove(Object key, Object value) {
        check("removeProviderProperty."+name);
        if (debug != null) {
            debug.println("Remove " + name + " provider property " + key);
        }
        return implRemove(key, value);
    }

    /**
     * Replaces the entry for the specified key only if currently
     * mapped to the specified value.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "putProviderProperty."+name},
     * where {@code name} is the provider name, to see if it's ok to set this
     * provider's property values.
     *
     * <p>
     *  仅当当前映射到指定值时替换指定键的条目。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,设置此提供程序的属性值。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values.
     *
     * @since 1.8
     */
    @Override
    public synchronized boolean replace(Object key, Object oldValue,
            Object newValue) {
        check("putProviderProperty." + name);

        if (debug != null) {
            debug.println("Replace " + name + " provider property " + key);
        }
        return implReplace(key, oldValue, newValue);
    }

    /**
     * Replaces the entry for the specified key only if it is
     * currently mapped to some value.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "putProviderProperty."+name},
     * where {@code name} is the provider name, to see if it's ok to set this
     * provider's property values.
     *
     * <p>
     *  仅当指定键的条目当前映射到某个值时,才替换该条目。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,设置此提供程序的属性值。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values.
     *
     * @since 1.8
     */
    @Override
    public synchronized Object replace(Object key, Object value) {
        check("putProviderProperty." + name);

        if (debug != null) {
            debug.println("Replace " + name + " provider property " + key);
        }
        return implReplace(key, value);
    }

    /**
     * Replaces each entry's value with the result of invoking the given
     * function on that entry, in the order entries are returned by an entry
     * set iterator, until all entries have been processed or the function
     * throws an exception.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the string {@code "putProviderProperty."+name},
     * where {@code name} is the provider name, to see if it's ok to set this
     * provider's property values.
     *
     * <p>
     * 将每个条目的值替换为调用该条目上的给定函数的结果,条目集由迭代器返回,直到所有条目都被处理或函数抛出异常。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,设置此提供程序的属性值。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values.
     *
     * @since 1.8
     */
    @Override
    public synchronized void replaceAll(BiFunction<? super Object, ? super Object, ? extends Object> function) {
        check("putProviderProperty." + name);

        if (debug != null) {
            debug.println("ReplaceAll " + name + " provider property ");
        }
        implReplaceAll(function);
    }

    /**
     * Attempts to compute a mapping for the specified key and its
     * current mapped value (or {@code null} if there is no current
     * mapping).
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the strings {@code "putProviderProperty."+name}
     * and {@code "removeProviderProperty."+name}, where {@code name} is the
     * provider name, to see if it's ok to set this provider's property values
     * and remove this provider's properties.
     *
     * <p>
     *  尝试计算指定键及其当前映射值的映射(如果没有当前映射,则为{@code null})。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。"+ name}和{@code"removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,以查看是否可以设置此提供程序的属性值并删除此提供程序的属性。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values or remove properties.
     *
     * @since 1.8
     */
    @Override
    public synchronized Object compute(Object key,
        BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        check("putProviderProperty." + name);
        check("removeProviderProperty" + name);

        if (debug != null) {
            debug.println("Compute " + name + " provider property " + key);
        }
        return implCompute(key, remappingFunction);
    }

    /**
     * If the specified key is not already associated with a value (or
     * is mapped to {@code null}), attempts to compute its value using
     * the given mapping function and enters it into this map unless
     * {@code null}.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the strings {@code "putProviderProperty."+name}
     * and {@code "removeProviderProperty."+name}, where {@code name} is the
     * provider name, to see if it's ok to set this provider's property values
     * and remove this provider's properties.
     *
     * <p>
     *  如果指定的键尚未与某个值相关联(或映射到{@code null}),则尝试使用给定的映射函数计算其值,并将其输入到此映射中,除非{@code null}。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。"+ name}和{@code"removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,以查看是否可以设置此提供程序的属性值并删除此提供程序的属性。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values and remove properties.
     *
     * @since 1.8
     */
    @Override
    public synchronized Object computeIfAbsent(Object key, Function<? super Object, ? extends Object> mappingFunction) {
        check("putProviderProperty." + name);
        check("removeProviderProperty" + name);

        if (debug != null) {
            debug.println("ComputeIfAbsent " + name + " provider property " +
                    key);
        }
        return implComputeIfAbsent(key, mappingFunction);
    }

    /**
     * If the value for the specified key is present and non-null, attempts to
     * compute a new mapping given the key and its current mapped value.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the strings {@code "putProviderProperty."+name}
     * and {@code "removeProviderProperty."+name}, where {@code name} is the
     * provider name, to see if it's ok to set this provider's property values
     * and remove this provider's properties.
     *
     * <p>
     * 如果指定键的值存在且非空,则尝试计算给定键和其当前映射值的新映射。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。"+ name}和{@code"removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,以查看是否可以设置此提供程序的属性值并删除此提供程序的属性。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values or remove properties.
     *
     * @since 1.8
     */
    @Override
    public synchronized Object computeIfPresent(Object key, BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        check("putProviderProperty." + name);
        check("removeProviderProperty" + name);

        if (debug != null) {
            debug.println("ComputeIfPresent " + name + " provider property " +
                    key);
        }
        return implComputeIfPresent(key, remappingFunction);
    }

    /**
     * If the specified key is not already associated with a value or is
     * associated with null, associates it with the given value. Otherwise,
     * replaces the value with the results of the given remapping function,
     * or removes if the result is null. This method may be of use when
     * combining multiple mapped values for a key.
     *
     * <p>If a security manager is enabled, its {@code checkSecurityAccess}
     * method is called with the strings {@code "putProviderProperty."+name}
     * and {@code "removeProviderProperty."+name}, where {@code name} is the
     * provider name, to see if it's ok to set this provider's property values
     * and remove this provider's properties.
     *
     * <p>
     *  如果指定的键尚未与值关联或与空值相关联,则将其与给定值相关联。否则,用给定重映射函数的结果替换值,如果结果为null,则删除。当组合键的多个映射值时,该方法可以是有用的。
     * 
     *  <p>如果启用了安全管理器,则会使用字符串{@code"putProviderProperty。"+ name}和{@code"removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,以查看是否可以设置此提供程序的属性值并删除此提供程序的属性。
     * 
     * 
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method
     *          denies access to set property values or remove properties.
     *
     * @since 1.8
     */
    @Override
    public synchronized Object merge(Object key, Object value,  BiFunction<? super Object, ? super Object, ? extends Object>  remappingFunction) {
        check("putProviderProperty." + name);
        check("removeProviderProperty" + name);

        if (debug != null) {
            debug.println("Merge " + name + " provider property " + key);
        }
        return implMerge(key, value, remappingFunction);
    }

    // let javadoc show doc from superclass
    @Override
    public Object get(Object key) {
        checkInitialized();
        return super.get(key);
    }
    /**
    /* <p>
    /* 
     * @since 1.8
     */
    @Override
    public synchronized Object getOrDefault(Object key, Object defaultValue) {
        checkInitialized();
        return super.getOrDefault(key, defaultValue);
    }

    /**
    /* <p>
    /* 
     * @since 1.8
     */
    @Override
    public synchronized void forEach(BiConsumer<? super Object, ? super Object> action) {
        checkInitialized();
        super.forEach(action);
    }

    // let javadoc show doc from superclass
    @Override
    public Enumeration<Object> keys() {
        checkInitialized();
        return super.keys();
    }

    // let javadoc show doc from superclass
    @Override
    public Enumeration<Object> elements() {
        checkInitialized();
        return super.elements();
    }

    // let javadoc show doc from superclass
    public String getProperty(String key) {
        checkInitialized();
        return super.getProperty(key);
    }

    private void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException();
        }
    }

    private void check(String directive) {
        checkInitialized();
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSecurityAccess(directive);
        }
    }

    // legacy properties changed since last call to any services method?
    private transient boolean legacyChanged;
    // serviceMap changed since last call to getServices()
    private transient boolean servicesChanged;

    // Map<String,String>
    private transient Map<String,String> legacyStrings;

    // Map<ServiceKey,Service>
    // used for services added via putService(), initialized on demand
    private transient Map<ServiceKey,Service> serviceMap;

    // Map<ServiceKey,Service>
    // used for services added via legacy methods, init on demand
    private transient Map<ServiceKey,Service> legacyMap;

    // Set<Service>
    // Unmodifiable set of all services. Initialized on demand.
    private transient Set<Service> serviceSet;

    // register the id attributes for this provider
    // this is to ensure that equals() and hashCode() do not incorrectly
    // report to different provider objects as the same
    private void putId() {
        // note: name and info may be null
        super.put("Provider.id name", String.valueOf(name));
        super.put("Provider.id version", String.valueOf(version));
        super.put("Provider.id info", String.valueOf(info));
        super.put("Provider.id className", this.getClass().getName());
    }

    private void readObject(ObjectInputStream in)
                throws IOException, ClassNotFoundException {
        Map<Object,Object> copy = new HashMap<>();
        for (Map.Entry<Object,Object> entry : super.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }
        defaults = null;
        in.defaultReadObject();
        implClear();
        initialized = true;
        putAll(copy);
    }

    private boolean checkLegacy(Object key) {
        String keyString = (String)key;
        if (keyString.startsWith("Provider.")) {
            return false;
        }

        legacyChanged = true;
        if (legacyStrings == null) {
            legacyStrings = new LinkedHashMap<String,String>();
        }
        return true;
    }

    /**
     * Copies all of the mappings from the specified Map to this provider.
     * Internal method to be called AFTER the security check has been
     * performed.
     * <p>
     *  将指定映射中的所有映射复制到此提供程序。执行安全检查后要调用的内部方法。
     * 
     */
    private void implPutAll(Map<?,?> t) {
        for (Map.Entry<?,?> e : t.entrySet()) {
            implPut(e.getKey(), e.getValue());
        }
    }

    private Object implRemove(Object key) {
        if (key instanceof String) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.remove((String)key);
        }
        return super.remove(key);
    }

    private boolean implRemove(Object key, Object value) {
        if (key instanceof String && value instanceof String) {
            if (!checkLegacy(key)) {
                return false;
            }
            legacyStrings.remove((String)key, value);
        }
        return super.remove(key, value);
    }

    private boolean implReplace(Object key, Object oldValue, Object newValue) {
        if ((key instanceof String) && (oldValue instanceof String) &&
                (newValue instanceof String)) {
            if (!checkLegacy(key)) {
                return false;
            }
            legacyStrings.replace((String)key, (String)oldValue,
                    (String)newValue);
        }
        return super.replace(key, oldValue, newValue);
    }

    private Object implReplace(Object key, Object value) {
        if ((key instanceof String) && (value instanceof String)) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.replace((String)key, (String)value);
        }
        return super.replace(key, value);
    }

    private void implReplaceAll(BiFunction<? super Object, ? super Object, ? extends Object> function) {
        legacyChanged = true;
        if (legacyStrings == null) {
            legacyStrings = new LinkedHashMap<String,String>();
        } else {
            legacyStrings.replaceAll((BiFunction<? super String, ? super String, ? extends String>) function);
        }
        super.replaceAll(function);
    }


    private Object implMerge(Object key, Object value, BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        if ((key instanceof String) && (value instanceof String)) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.merge((String)key, (String)value,
                    (BiFunction<? super String, ? super String, ? extends String>) remappingFunction);
        }
        return super.merge(key, value, remappingFunction);
    }

    private Object implCompute(Object key, BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        if (key instanceof String) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.computeIfAbsent((String) key,
                    (Function<? super String, ? extends String>) remappingFunction);
        }
        return super.compute(key, remappingFunction);
    }

    private Object implComputeIfAbsent(Object key, Function<? super Object, ? extends Object> mappingFunction) {
        if (key instanceof String) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.computeIfAbsent((String) key,
                    (Function<? super String, ? extends String>) mappingFunction);
        }
        return super.computeIfAbsent(key, mappingFunction);
    }

    private Object implComputeIfPresent(Object key, BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
        if (key instanceof String) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.computeIfPresent((String) key,
                    (BiFunction<? super String, ? super String, ? extends String>) remappingFunction);
        }
        return super.computeIfPresent(key, remappingFunction);
    }

    private Object implPut(Object key, Object value) {
        if ((key instanceof String) && (value instanceof String)) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.put((String)key, (String)value);
        }
        return super.put(key, value);
    }

    private Object implPutIfAbsent(Object key, Object value) {
        if ((key instanceof String) && (value instanceof String)) {
            if (!checkLegacy(key)) {
                return null;
            }
            legacyStrings.putIfAbsent((String)key, (String)value);
        }
        return super.putIfAbsent(key, value);
    }

    private void implClear() {
        if (legacyStrings != null) {
            legacyStrings.clear();
        }
        if (legacyMap != null) {
            legacyMap.clear();
        }
        if (serviceMap != null) {
            serviceMap.clear();
        }
        legacyChanged = false;
        servicesChanged = false;
        serviceSet = null;
        super.clear();
        putId();
    }

    // used as key in the serviceMap and legacyMap HashMaps
    private static class ServiceKey {
        private final String type;
        private final String algorithm;
        private final String originalAlgorithm;
        private ServiceKey(String type, String algorithm, boolean intern) {
            this.type = type;
            this.originalAlgorithm = algorithm;
            algorithm = algorithm.toUpperCase(ENGLISH);
            this.algorithm = intern ? algorithm.intern() : algorithm;
        }
        public int hashCode() {
            return type.hashCode() + algorithm.hashCode();
        }
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ServiceKey == false) {
                return false;
            }
            ServiceKey other = (ServiceKey)obj;
            return this.type.equals(other.type)
                && this.algorithm.equals(other.algorithm);
        }
        boolean matches(String type, String algorithm) {
            return (this.type == type) && (this.originalAlgorithm == algorithm);
        }
    }

    /**
     * Ensure all the legacy String properties are fully parsed into
     * service objects.
     * <p>
     *  确保所有的旧String属性完全解析为服务对象。
     * 
     */
    private void ensureLegacyParsed() {
        if ((legacyChanged == false) || (legacyStrings == null)) {
            return;
        }
        serviceSet = null;
        if (legacyMap == null) {
            legacyMap = new LinkedHashMap<ServiceKey,Service>();
        } else {
            legacyMap.clear();
        }
        for (Map.Entry<String,String> entry : legacyStrings.entrySet()) {
            parseLegacyPut(entry.getKey(), entry.getValue());
        }
        removeInvalidServices(legacyMap);
        legacyChanged = false;
    }

    /**
     * Remove all invalid services from the Map. Invalid services can only
     * occur if the legacy properties are inconsistent or incomplete.
     * <p>
     *  从地图中删除所有无效的服务。无效服务只有在旧属性不一致或不完整时才会发生。
     * 
     */
    private void removeInvalidServices(Map<ServiceKey,Service> map) {
        for (Iterator<Map.Entry<ServiceKey, Service>> t =
                map.entrySet().iterator(); t.hasNext(); ) {
            Service s = t.next().getValue();
            if (s.isValid() == false) {
                t.remove();
            }
        }
    }

    private String[] getTypeAndAlgorithm(String key) {
        int i = key.indexOf(".");
        if (i < 1) {
            if (debug != null) {
                debug.println("Ignoring invalid entry in provider "
                        + name + ":" + key);
            }
            return null;
        }
        String type = key.substring(0, i);
        String alg = key.substring(i + 1);
        return new String[] {type, alg};
    }

    private final static String ALIAS_PREFIX = "Alg.Alias.";
    private final static String ALIAS_PREFIX_LOWER = "alg.alias.";
    private final static int ALIAS_LENGTH = ALIAS_PREFIX.length();

    private void parseLegacyPut(String name, String value) {
        if (name.toLowerCase(ENGLISH).startsWith(ALIAS_PREFIX_LOWER)) {
            // e.g. put("Alg.Alias.MessageDigest.SHA", "SHA-1");
            // aliasKey ~ MessageDigest.SHA
            String stdAlg = value;
            String aliasKey = name.substring(ALIAS_LENGTH);
            String[] typeAndAlg = getTypeAndAlgorithm(aliasKey);
            if (typeAndAlg == null) {
                return;
            }
            String type = getEngineName(typeAndAlg[0]);
            String aliasAlg = typeAndAlg[1].intern();
            ServiceKey key = new ServiceKey(type, stdAlg, true);
            Service s = legacyMap.get(key);
            if (s == null) {
                s = new Service(this);
                s.type = type;
                s.algorithm = stdAlg;
                legacyMap.put(key, s);
            }
            legacyMap.put(new ServiceKey(type, aliasAlg, true), s);
            s.addAlias(aliasAlg);
        } else {
            String[] typeAndAlg = getTypeAndAlgorithm(name);
            if (typeAndAlg == null) {
                return;
            }
            int i = typeAndAlg[1].indexOf(' ');
            if (i == -1) {
                // e.g. put("MessageDigest.SHA-1", "sun.security.provider.SHA");
                String type = getEngineName(typeAndAlg[0]);
                String stdAlg = typeAndAlg[1].intern();
                String className = value;
                ServiceKey key = new ServiceKey(type, stdAlg, true);
                Service s = legacyMap.get(key);
                if (s == null) {
                    s = new Service(this);
                    s.type = type;
                    s.algorithm = stdAlg;
                    legacyMap.put(key, s);
                }
                s.className = className;
            } else { // attribute
                // e.g. put("MessageDigest.SHA-1 ImplementedIn", "Software");
                String attributeValue = value;
                String type = getEngineName(typeAndAlg[0]);
                String attributeString = typeAndAlg[1];
                String stdAlg = attributeString.substring(0, i).intern();
                String attributeName = attributeString.substring(i + 1);
                // kill additional spaces
                while (attributeName.startsWith(" ")) {
                    attributeName = attributeName.substring(1);
                }
                attributeName = attributeName.intern();
                ServiceKey key = new ServiceKey(type, stdAlg, true);
                Service s = legacyMap.get(key);
                if (s == null) {
                    s = new Service(this);
                    s.type = type;
                    s.algorithm = stdAlg;
                    legacyMap.put(key, s);
                }
                s.addAttribute(attributeName, attributeValue);
            }
        }
    }

    /**
     * Get the service describing this Provider's implementation of the
     * specified type of this algorithm or alias. If no such
     * implementation exists, this method returns null. If there are two
     * matching services, one added to this provider using
     * {@link #putService putService()} and one added via {@link #put put()},
     * the service added via {@link #putService putService()} is returned.
     *
     * <p>
     * 获取描述此Provider的实现的服务,指定类型的此算法或别名。如果不存在此类实现,则此方法返回null。
     * 如果有两个匹配服务,使用{@link #putService putService()}添加到此提供程序,通过{@link #put put()}添加一个服务,通过{@link #putService putService()}
     * 添加的服务是回。
     * 获取描述此Provider的实现的服务,指定类型的此算法或别名。如果不存在此类实现,则此方法返回null。
     * 
     * 
     * @param type the type of {@link Service service} requested
     * (for example, {@code MessageDigest})
     * @param algorithm the case insensitive algorithm name (or alternate
     * alias) of the service requested (for example, {@code SHA-1})
     *
     * @return the service describing this Provider's matching service
     * or null if no such service exists
     *
     * @throws NullPointerException if type or algorithm is null
     *
     * @since 1.5
     */
    public synchronized Service getService(String type, String algorithm) {
        checkInitialized();
        // avoid allocating a new key object if possible
        ServiceKey key = previousKey;
        if (key.matches(type, algorithm) == false) {
            key = new ServiceKey(type, algorithm, false);
            previousKey = key;
        }
        if (serviceMap != null) {
            Service service = serviceMap.get(key);
            if (service != null) {
                return service;
            }
        }
        ensureLegacyParsed();
        return (legacyMap != null) ? legacyMap.get(key) : null;
    }

    // ServiceKey from previous getService() call
    // by re-using it if possible we avoid allocating a new object
    // and the toUpperCase() call.
    // re-use will occur e.g. as the framework traverses the provider
    // list and queries each provider with the same values until it finds
    // a matching service
    private static volatile ServiceKey previousKey =
                                            new ServiceKey("", "", false);

    /**
     * Get an unmodifiable Set of all services supported by
     * this Provider.
     *
     * <p>
     *  获取此提供程序支持的所有服务的不可修改集。
     * 
     * 
     * @return an unmodifiable Set of all services supported by
     * this Provider
     *
     * @since 1.5
     */
    public synchronized Set<Service> getServices() {
        checkInitialized();
        if (legacyChanged || servicesChanged) {
            serviceSet = null;
        }
        if (serviceSet == null) {
            ensureLegacyParsed();
            Set<Service> set = new LinkedHashSet<>();
            if (serviceMap != null) {
                set.addAll(serviceMap.values());
            }
            if (legacyMap != null) {
                set.addAll(legacyMap.values());
            }
            serviceSet = Collections.unmodifiableSet(set);
            servicesChanged = false;
        }
        return serviceSet;
    }

    /**
     * Add a service. If a service of the same type with the same algorithm
     * name exists and it was added using {@link #putService putService()},
     * it is replaced by the new service.
     * This method also places information about this service
     * in the provider's Hashtable values in the format described in the
     * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
     * Java Cryptography Architecture API Specification &amp; Reference </a>.
     *
     * <p>Also, if there is a security manager, its
     * {@code checkSecurityAccess} method is called with the string
     * {@code "putProviderProperty."+name}, where {@code name} is
     * the provider name, to see if it's ok to set this provider's property
     * values. If the default implementation of {@code checkSecurityAccess}
     * is used (that is, that method is not overriden), then this results in
     * a call to the security manager's {@code checkPermission} method with
     * a {@code SecurityPermission("putProviderProperty."+name)}
     * permission.
     *
     * <p>
     *  添加服务。如果存在具有相同算法名称的相同类型的服务,并且使用{@link #putService putService()}添加该服务,则该服务将被新服务替换。
     * 此方法还以此中描述的格式将有关此服务的信息放在提供程序的Hashtable值中。
     * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
     *  Java加密架构API规范&amp;参考</a>。
     * 
     *  <p>此外,如果有安全管理器,则会使用字符串{@code"putProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,确定设置此提供程序的属性值。
     * 如果使用{@code checkSecurityAccess}的默认实现(即该方法不被覆盖),那么这将导致使用{@code SecurityPermission("putProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,确定设置此提供程序的属性值。
     * ")调用安全管理器的{@code checkPermission}方法。 + name)}权限。
     * 
     * 
     * @param s the Service to add
     *
     * @throws SecurityException
     *      if a security manager exists and its {@link
     *      java.lang.SecurityManager#checkSecurityAccess} method denies
     *      access to set property values.
     * @throws NullPointerException if s is null
     *
     * @since 1.5
     */
    protected synchronized void putService(Service s) {
        check("putProviderProperty." + name);
        if (debug != null) {
            debug.println(name + ".putService(): " + s);
        }
        if (s == null) {
            throw new NullPointerException();
        }
        if (s.getProvider() != this) {
            throw new IllegalArgumentException
                    ("service.getProvider() must match this Provider object");
        }
        if (serviceMap == null) {
            serviceMap = new LinkedHashMap<ServiceKey,Service>();
        }
        servicesChanged = true;
        String type = s.getType();
        String algorithm = s.getAlgorithm();
        ServiceKey key = new ServiceKey(type, algorithm, true);
        // remove existing service
        implRemoveService(serviceMap.get(key));
        serviceMap.put(key, s);
        for (String alias : s.getAliases()) {
            serviceMap.put(new ServiceKey(type, alias, true), s);
        }
        putPropertyStrings(s);
    }

    /**
     * Put the string properties for this Service in this Provider's
     * Hashtable.
     * <p>
     *  将此服务的字符串属性放在此提供程序的散列表中。
     * 
     */
    private void putPropertyStrings(Service s) {
        String type = s.getType();
        String algorithm = s.getAlgorithm();
        // use super() to avoid permission check and other processing
        super.put(type + "." + algorithm, s.getClassName());
        for (String alias : s.getAliases()) {
            super.put(ALIAS_PREFIX + type + "." + alias, algorithm);
        }
        for (Map.Entry<UString,String> entry : s.attributes.entrySet()) {
            String key = type + "." + algorithm + " " + entry.getKey();
            super.put(key, entry.getValue());
        }
    }

    /**
     * Remove the string properties for this Service from this Provider's
     * Hashtable.
     * <p>
     *  从此提供程序的散列表中删除此服务的字符串属性。
     * 
     */
    private void removePropertyStrings(Service s) {
        String type = s.getType();
        String algorithm = s.getAlgorithm();
        // use super() to avoid permission check and other processing
        super.remove(type + "." + algorithm);
        for (String alias : s.getAliases()) {
            super.remove(ALIAS_PREFIX + type + "." + alias);
        }
        for (Map.Entry<UString,String> entry : s.attributes.entrySet()) {
            String key = type + "." + algorithm + " " + entry.getKey();
            super.remove(key);
        }
    }

    /**
     * Remove a service previously added using
     * {@link #putService putService()}. The specified service is removed from
     * this provider. It will no longer be returned by
     * {@link #getService getService()} and its information will be removed
     * from this provider's Hashtable.
     *
     * <p>Also, if there is a security manager, its
     * {@code checkSecurityAccess} method is called with the string
     * {@code "removeProviderProperty."+name}, where {@code name} is
     * the provider name, to see if it's ok to remove this provider's
     * properties. If the default implementation of
     * {@code checkSecurityAccess} is used (that is, that method is not
     * overriden), then this results in a call to the security manager's
     * {@code checkPermission} method with a
     * {@code SecurityPermission("removeProviderProperty."+name)}
     * permission.
     *
     * <p>
     * 移除先前使用{@link #putService putService()}添加的服务。指定的服务将从此提供程序中删除。
     * 它将不再由{@link #getService getService()}返回,其信息将从此提供商的Hashtable中删除。
     * 
     *  <p>此外,如果有安全管理器,则会使用字符串{@code"removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,确定删除此提供程序的属性。
     * 如果使用{@code checkSecurityAccess}的默认实现(即该方法不被覆盖),那么这会导致使用{@code SecurityPermission("removeProviderProperty。
     * "+ name}调用其{@code checkSecurityAccess}方法,其中{@code name}是提供程序名称,确定删除此提供程序的属性。
     * ")调用安全管理器的{@code checkPermission}方法。 + name)}权限。
     * 
     * 
     * @param s the Service to be removed
     *
     * @throws  SecurityException
     *          if a security manager exists and its {@link
     *          java.lang.SecurityManager#checkSecurityAccess} method denies
     *          access to remove this provider's properties.
     * @throws NullPointerException if s is null
     *
     * @since 1.5
     */
    protected synchronized void removeService(Service s) {
        check("removeProviderProperty." + name);
        if (debug != null) {
            debug.println(name + ".removeService(): " + s);
        }
        if (s == null) {
            throw new NullPointerException();
        }
        implRemoveService(s);
    }

    private void implRemoveService(Service s) {
        if ((s == null) || (serviceMap == null)) {
            return;
        }
        String type = s.getType();
        String algorithm = s.getAlgorithm();
        ServiceKey key = new ServiceKey(type, algorithm, false);
        Service oldService = serviceMap.get(key);
        if (s != oldService) {
            return;
        }
        servicesChanged = true;
        serviceMap.remove(key);
        for (String alias : s.getAliases()) {
            serviceMap.remove(new ServiceKey(type, alias, false));
        }
        removePropertyStrings(s);
    }

    // Wrapped String that behaves in a case insensitive way for equals/hashCode
    private static class UString {
        final String string;
        final String lowerString;

        UString(String s) {
            this.string = s;
            this.lowerString = s.toLowerCase(ENGLISH);
        }

        public int hashCode() {
            return lowerString.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof UString == false) {
                return false;
            }
            UString other = (UString)obj;
            return lowerString.equals(other.lowerString);
        }

        public String toString() {
            return string;
        }
    }

    // describe relevant properties of a type of engine
    private static class EngineDescription {
        final String name;
        final boolean supportsParameter;
        final String constructorParameterClassName;
        private volatile Class<?> constructorParameterClass;

        EngineDescription(String name, boolean sp, String paramName) {
            this.name = name;
            this.supportsParameter = sp;
            this.constructorParameterClassName = paramName;
        }
        Class<?> getConstructorParameterClass() throws ClassNotFoundException {
            Class<?> clazz = constructorParameterClass;
            if (clazz == null) {
                clazz = Class.forName(constructorParameterClassName);
                constructorParameterClass = clazz;
            }
            return clazz;
        }
    }

    // built in knowledge of the engine types shipped as part of the JDK
    private static final Map<String,EngineDescription> knownEngines;

    private static void addEngine(String name, boolean sp, String paramName) {
        EngineDescription ed = new EngineDescription(name, sp, paramName);
        // also index by canonical name to avoid toLowerCase() for some lookups
        knownEngines.put(name.toLowerCase(ENGLISH), ed);
        knownEngines.put(name, ed);
    }

    static {
        knownEngines = new HashMap<String,EngineDescription>();
        // JCA
        addEngine("AlgorithmParameterGenerator",        false, null);
        addEngine("AlgorithmParameters",                false, null);
        addEngine("KeyFactory",                         false, null);
        addEngine("KeyPairGenerator",                   false, null);
        addEngine("KeyStore",                           false, null);
        addEngine("MessageDigest",                      false, null);
        addEngine("SecureRandom",                       false, null);
        addEngine("Signature",                          true,  null);
        addEngine("CertificateFactory",                 false, null);
        addEngine("CertPathBuilder",                    false, null);
        addEngine("CertPathValidator",                  false, null);
        addEngine("CertStore",                          false,
                            "java.security.cert.CertStoreParameters");
        // JCE
        addEngine("Cipher",                             true,  null);
        addEngine("ExemptionMechanism",                 false, null);
        addEngine("Mac",                                true,  null);
        addEngine("KeyAgreement",                       true,  null);
        addEngine("KeyGenerator",                       false, null);
        addEngine("SecretKeyFactory",                   false, null);
        // JSSE
        addEngine("KeyManagerFactory",                  false, null);
        addEngine("SSLContext",                         false, null);
        addEngine("TrustManagerFactory",                false, null);
        // JGSS
        addEngine("GssApiMechanism",                    false, null);
        // SASL
        addEngine("SaslClientFactory",                  false, null);
        addEngine("SaslServerFactory",                  false, null);
        // POLICY
        addEngine("Policy",                             false,
                            "java.security.Policy$Parameters");
        // CONFIGURATION
        addEngine("Configuration",                      false,
                            "javax.security.auth.login.Configuration$Parameters");
        // XML DSig
        addEngine("XMLSignatureFactory",                false, null);
        addEngine("KeyInfoFactory",                     false, null);
        addEngine("TransformService",                   false, null);
        // Smart Card I/O
        addEngine("TerminalFactory",                    false,
                            "java.lang.Object");
    }

    // get the "standard" (mixed-case) engine name for arbitary case engine name
    // if there is no known engine by that name, return s
    private static String getEngineName(String s) {
        // try original case first, usually correct
        EngineDescription e = knownEngines.get(s);
        if (e == null) {
            e = knownEngines.get(s.toLowerCase(ENGLISH));
        }
        return (e == null) ? s : e.name;
    }

    /**
     * The description of a security service. It encapsulates the properties
     * of a service and contains a factory method to obtain new implementation
     * instances of this service.
     *
     * <p>Each service has a provider that offers the service, a type,
     * an algorithm name, and the name of the class that implements the
     * service. Optionally, it also includes a list of alternate algorithm
     * names for this service (aliases) and attributes, which are a map of
     * (name, value) String pairs.
     *
     * <p>This class defines the methods {@link #supportsParameter
     * supportsParameter()} and {@link #newInstance newInstance()}
     * which are used by the Java security framework when it searches for
     * suitable services and instantiates them. The valid arguments to those
     * methods depend on the type of service. For the service types defined
     * within Java SE, see the
     * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
     * Java Cryptography Architecture API Specification &amp; Reference </a>
     * for the valid values.
     * Note that components outside of Java SE can define additional types of
     * services and their behavior.
     *
     * <p>Instances of this class are immutable.
     *
     * <p>
     *  安全服务的描述。它封装了服务的属性,并包含一个工厂方法来获取这个服务的新实现实例。
     * 
     *  <p>每个服务都有一个提供程序,提供服务,类型,算法名称以及实现服务的类的名称。 (可选)还包括此服务(别名)和属性的备用算法名称列表,这是一个(名称,值)String对的映射。
     * 
     * <p>此类定义了方法{@link #supportsParameter supportsParameter()}和{@link #newInstance newInstance()},它们在Java安全
     * 框架搜索合适的服务并实例化时使用。
     * 这些方法的有效参数取决于服务的类型。对于在Java SE中定义的服务类型,请参阅。
     * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
     *  Java加密架构API规范&amp;参考</a>有效值。请注意,Java SE之外的组件可以定义其他类型的服务及其行为。
     * 
     *  <p>此类的实例是不可变的。
     * 
     * 
     * @since 1.5
     */
    public static class Service {

        private String type, algorithm, className;
        private final Provider provider;
        private List<String> aliases;
        private Map<UString,String> attributes;

        // Reference to the cached implementation Class object
        private volatile Reference<Class<?>> classRef;

        // flag indicating whether this service has its attributes for
        // supportedKeyFormats or supportedKeyClasses set
        // if null, the values have not been initialized
        // if TRUE, at least one of supportedFormats/Classes is non null
        private volatile Boolean hasKeyAttributes;

        // supported encoding formats
        private String[] supportedFormats;

        // names of the supported key (super) classes
        private Class[] supportedClasses;

        // whether this service has been registered with the Provider
        private boolean registered;

        private static final Class<?>[] CLASS0 = new Class<?>[0];

        // this constructor and these methods are used for parsing
        // the legacy string properties.

        private Service(Provider provider) {
            this.provider = provider;
            aliases = Collections.<String>emptyList();
            attributes = Collections.<UString,String>emptyMap();
        }

        private boolean isValid() {
            return (type != null) && (algorithm != null) && (className != null);
        }

        private void addAlias(String alias) {
            if (aliases.isEmpty()) {
                aliases = new ArrayList<String>(2);
            }
            aliases.add(alias);
        }

        void addAttribute(String type, String value) {
            if (attributes.isEmpty()) {
                attributes = new HashMap<UString,String>(8);
            }
            attributes.put(new UString(type), value);
        }

        /**
         * Construct a new service.
         *
         * <p>
         *  构建新服务。
         * 
         * 
         * @param provider the provider that offers this service
         * @param type the type of this service
         * @param algorithm the algorithm name
         * @param className the name of the class implementing this service
         * @param aliases List of aliases or null if algorithm has no aliases
         * @param attributes Map of attributes or null if this implementation
         *                   has no attributes
         *
         * @throws NullPointerException if provider, type, algorithm, or
         * className is null
         */
        public Service(Provider provider, String type, String algorithm,
                String className, List<String> aliases,
                Map<String,String> attributes) {
            if ((provider == null) || (type == null) ||
                    (algorithm == null) || (className == null)) {
                throw new NullPointerException();
            }
            this.provider = provider;
            this.type = getEngineName(type);
            this.algorithm = algorithm;
            this.className = className;
            if (aliases == null) {
                this.aliases = Collections.<String>emptyList();
            } else {
                this.aliases = new ArrayList<String>(aliases);
            }
            if (attributes == null) {
                this.attributes = Collections.<UString,String>emptyMap();
            } else {
                this.attributes = new HashMap<UString,String>();
                for (Map.Entry<String,String> entry : attributes.entrySet()) {
                    this.attributes.put(new UString(entry.getKey()), entry.getValue());
                }
            }
        }

        /**
         * Get the type of this service. For example, {@code MessageDigest}.
         *
         * <p>
         *  获取此服务的类型。例如,{@code MessageDigest}。
         * 
         * 
         * @return the type of this service
         */
        public final String getType() {
            return type;
        }

        /**
         * Return the name of the algorithm of this service. For example,
         * {@code SHA-1}.
         *
         * <p>
         *  返回此服务的算法的名称。例如,{@code SHA-1}。
         * 
         * 
         * @return the algorithm of this service
         */
        public final String getAlgorithm() {
            return algorithm;
        }

        /**
         * Return the Provider of this service.
         *
         * <p>
         *  返回此服务的提供程序。
         * 
         * 
         * @return the Provider of this service
         */
        public final Provider getProvider() {
            return provider;
        }

        /**
         * Return the name of the class implementing this service.
         *
         * <p>
         *  返回实现此服务的类的名称。
         * 
         * 
         * @return the name of the class implementing this service
         */
        public final String getClassName() {
            return className;
        }

        // internal only
        private final List<String> getAliases() {
            return aliases;
        }

        /**
         * Return the value of the specified attribute or null if this
         * attribute is not set for this Service.
         *
         * <p>
         *  返回指定属性的值,如果未为此服务设置此属性,则返回null。
         * 
         * 
         * @param name the name of the requested attribute
         *
         * @return the value of the specified attribute or null if the
         *         attribute is not present
         *
         * @throws NullPointerException if name is null
         */
        public final String getAttribute(String name) {
            if (name == null) {
                throw new NullPointerException();
            }
            return attributes.get(new UString(name));
        }

        /**
         * Return a new instance of the implementation described by this
         * service. The security provider framework uses this method to
         * construct implementations. Applications will typically not need
         * to call it.
         *
         * <p>The default implementation uses reflection to invoke the
         * standard constructor for this type of service.
         * Security providers can override this method to implement
         * instantiation in a different way.
         * For details and the values of constructorParameter that are
         * valid for the various types of services see the
         * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
         * Java Cryptography Architecture API Specification &amp;
         * Reference</a>.
         *
         * <p>
         *  返回此服务描述的实现的新实例。安全提供程序框架使用此方法来构造实现。应用程序通常不需要调用它。
         * 
         *  <p>默认实现使用反射来调用此类服务​​的标准构造函数。安全提供程序可以覆盖此方法以不同的方式实现实例化。有关对各种服务类型有效的constructorParameter的详细信息和值,请参阅
         * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
         * Java加密架构API规范&amp;参考</a>。
         * 
         * 
         * @param constructorParameter the value to pass to the constructor,
         * or null if this type of service does not use a constructorParameter.
         *
         * @return a new implementation of this service
         *
         * @throws InvalidParameterException if the value of
         * constructorParameter is invalid for this type of service.
         * @throws NoSuchAlgorithmException if instantiation failed for
         * any other reason.
         */
        public Object newInstance(Object constructorParameter)
                throws NoSuchAlgorithmException {
            if (registered == false) {
                if (provider.getService(type, algorithm) != this) {
                    throw new NoSuchAlgorithmException
                        ("Service not registered with Provider "
                        + provider.getName() + ": " + this);
                }
                registered = true;
            }
            try {
                EngineDescription cap = knownEngines.get(type);
                if (cap == null) {
                    // unknown engine type, use generic code
                    // this is the code path future for non-core
                    // optional packages
                    return newInstanceGeneric(constructorParameter);
                }
                if (cap.constructorParameterClassName == null) {
                    if (constructorParameter != null) {
                        throw new InvalidParameterException
                            ("constructorParameter not used with " + type
                            + " engines");
                    }
                    Class<?> clazz = getImplClass();
                    Class<?>[] empty = {};
                    Constructor<?> con = clazz.getConstructor(empty);
                    return con.newInstance();
                } else {
                    Class<?> paramClass = cap.getConstructorParameterClass();
                    if (constructorParameter != null) {
                        Class<?> argClass = constructorParameter.getClass();
                        if (paramClass.isAssignableFrom(argClass) == false) {
                            throw new InvalidParameterException
                            ("constructorParameter must be instanceof "
                            + cap.constructorParameterClassName.replace('$', '.')
                            + " for engine type " + type);
                        }
                    }
                    Class<?> clazz = getImplClass();
                    Constructor<?> cons = clazz.getConstructor(paramClass);
                    return cons.newInstance(constructorParameter);
                }
            } catch (NoSuchAlgorithmException e) {
                throw e;
            } catch (InvocationTargetException e) {
                throw new NoSuchAlgorithmException
                    ("Error constructing implementation (algorithm: "
                    + algorithm + ", provider: " + provider.getName()
                    + ", class: " + className + ")", e.getCause());
            } catch (Exception e) {
                throw new NoSuchAlgorithmException
                    ("Error constructing implementation (algorithm: "
                    + algorithm + ", provider: " + provider.getName()
                    + ", class: " + className + ")", e);
            }
        }

        // return the implementation Class object for this service
        private Class<?> getImplClass() throws NoSuchAlgorithmException {
            try {
                Reference<Class<?>> ref = classRef;
                Class<?> clazz = (ref == null) ? null : ref.get();
                if (clazz == null) {
                    ClassLoader cl = provider.getClass().getClassLoader();
                    if (cl == null) {
                        clazz = Class.forName(className);
                    } else {
                        clazz = cl.loadClass(className);
                    }
                    if (!Modifier.isPublic(clazz.getModifiers())) {
                        throw new NoSuchAlgorithmException
                            ("class configured for " + type + " (provider: " +
                            provider.getName() + ") is not public.");
                    }
                    classRef = new WeakReference<Class<?>>(clazz);
                }
                return clazz;
            } catch (ClassNotFoundException e) {
                throw new NoSuchAlgorithmException
                    ("class configured for " + type + " (provider: " +
                    provider.getName() + ") cannot be found.", e);
            }
        }

        /**
         * Generic code path for unknown engine types. Call the
         * no-args constructor if constructorParameter is null, otherwise
         * use the first matching constructor.
         * <p>
         *  未知引擎类型的通用代码路径。如果constructorParameter为null,则调用no-args构造函数,否则使用第一个匹配构造函数。
         * 
         */
        private Object newInstanceGeneric(Object constructorParameter)
                throws Exception {
            Class<?> clazz = getImplClass();
            if (constructorParameter == null) {
                // create instance with public no-arg constructor if it exists
                try {
                    Class<?>[] empty = {};
                    Constructor<?> con = clazz.getConstructor(empty);
                    return con.newInstance();
                } catch (NoSuchMethodException e) {
                    throw new NoSuchAlgorithmException("No public no-arg "
                        + "constructor found in class " + className);
                }
            }
            Class<?> argClass = constructorParameter.getClass();
            Constructor[] cons = clazz.getConstructors();
            // find first public constructor that can take the
            // argument as parameter
            for (Constructor<?> con : cons) {
                Class<?>[] paramTypes = con.getParameterTypes();
                if (paramTypes.length != 1) {
                    continue;
                }
                if (paramTypes[0].isAssignableFrom(argClass) == false) {
                    continue;
                }
                return con.newInstance(constructorParameter);
            }
            throw new NoSuchAlgorithmException("No public constructor matching "
                + argClass.getName() + " found in class " + className);
        }

        /**
         * Test whether this Service can use the specified parameter.
         * Returns false if this service cannot use the parameter. Returns
         * true if this service can use the parameter, if a fast test is
         * infeasible, or if the status is unknown.
         *
         * <p>The security provider framework uses this method with
         * some types of services to quickly exclude non-matching
         * implementations for consideration.
         * Applications will typically not need to call it.
         *
         * <p>For details and the values of parameter that are valid for the
         * various types of services see the top of this class and the
         * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
         * Java Cryptography Architecture API Specification &amp;
         * Reference</a>.
         * Security providers can override it to implement their own test.
         *
         * <p>
         *  测试此服务是否可以使用指定的参数。如果此服务无法使用参数,则返回false。如果此服务可以使用参数,如果快速测试不可行或状态未知,则返回true。
         * 
         *  <p>安全提供程序框架对某些类型的服务使用此方法,以快速排除不匹配的实施以供考虑。应用程序通常不需要调用它。
         * 
         *  <p>有关各种服务类型有效的参数的详细信息和值,请参阅此类的顶部和
         * <a href="../../../technotes/guides/security/crypto/CryptoSpec.html">
         *  Java加密架构API规范&amp;参考</a>。安全提供者可以覆盖它来实现自己的测试。
         * 
         * @param parameter the parameter to test
         *
         * @return false if this this service cannot use the specified
         * parameter; true if it can possibly use the parameter
         *
         * @throws InvalidParameterException if the value of parameter is
         * invalid for this type of service or if this method cannot be
         * used with this type of service
         */
        public boolean supportsParameter(Object parameter) {
            EngineDescription cap = knownEngines.get(type);
            if (cap == null) {
                // unknown engine type, return true by default
                return true;
            }
            if (cap.supportsParameter == false) {
                throw new InvalidParameterException("supportsParameter() not "
                    + "used with " + type + " engines");
            }
            // allow null for keys without attributes for compatibility
            if ((parameter != null) && (parameter instanceof Key == false)) {
                throw new InvalidParameterException
                    ("Parameter must be instanceof Key for engine " + type);
            }
            if (hasKeyAttributes() == false) {
                return true;
            }
            if (parameter == null) {
                return false;
            }
            Key key = (Key)parameter;
            if (supportsKeyFormat(key)) {
                return true;
            }
            if (supportsKeyClass(key)) {
                return true;
            }
            return false;
        }

        /**
         * Return whether this service has its Supported* properties for
         * keys defined. Parses the attributes if not yet initialized.
         * <p>
         * 
         */
        private boolean hasKeyAttributes() {
            Boolean b = hasKeyAttributes;
            if (b == null) {
                synchronized (this) {
                    String s;
                    s = getAttribute("SupportedKeyFormats");
                    if (s != null) {
                        supportedFormats = s.split("\\|");
                    }
                    s = getAttribute("SupportedKeyClasses");
                    if (s != null) {
                        String[] classNames = s.split("\\|");
                        List<Class<?>> classList =
                            new ArrayList<>(classNames.length);
                        for (String className : classNames) {
                            Class<?> clazz = getKeyClass(className);
                            if (clazz != null) {
                                classList.add(clazz);
                            }
                        }
                        supportedClasses = classList.toArray(CLASS0);
                    }
                    boolean bool = (supportedFormats != null)
                        || (supportedClasses != null);
                    b = Boolean.valueOf(bool);
                    hasKeyAttributes = b;
                }
            }
            return b.booleanValue();
        }

        // get the key class object of the specified name
        private Class<?> getKeyClass(String name) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException e) {
                // ignore
            }
            try {
                ClassLoader cl = provider.getClass().getClassLoader();
                if (cl != null) {
                    return cl.loadClass(name);
                }
            } catch (ClassNotFoundException e) {
                // ignore
            }
            return null;
        }

        private boolean supportsKeyFormat(Key key) {
            if (supportedFormats == null) {
                return false;
            }
            String format = key.getFormat();
            if (format == null) {
                return false;
            }
            for (String supportedFormat : supportedFormats) {
                if (supportedFormat.equals(format)) {
                    return true;
                }
            }
            return false;
        }

        private boolean supportsKeyClass(Key key) {
            if (supportedClasses == null) {
                return false;
            }
            Class<?> keyClass = key.getClass();
            for (Class<?> clazz : supportedClasses) {
                if (clazz.isAssignableFrom(keyClass)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Return a String representation of this service.
         *
         * <p>
         *  返回此服务是否具有定义的键的Supported *属性。解析属性(如果尚未初始化)。
         * 
         * 
         * @return a String representation of this service.
         */
        public String toString() {
            String aString = aliases.isEmpty()
                ? "" : "\r\n  aliases: " + aliases.toString();
            String attrs = attributes.isEmpty()
                ? "" : "\r\n  attributes: " + attributes.toString();
            return provider.getName() + ": " + type + "." + algorithm
                + " -> " + className + aString + attrs + "\r\n";
        }

    }

}
