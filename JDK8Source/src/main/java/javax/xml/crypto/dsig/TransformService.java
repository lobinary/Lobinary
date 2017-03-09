/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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
 * $Id: TransformService.java,v 1.6.4.1 2005/09/15 12:42:11 mullan Exp $
 * <p>
 *  $ Id：TransformService.java,v 1.6.4.1 2005/09/15 12:42:11 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.*;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;

/**
 * A Service Provider Interface for transform and canonicalization algorithms.
 *
 * <p>Each instance of <code>TransformService</code> supports a specific
 * transform or canonicalization algorithm and XML mechanism type. To create a
 * <code>TransformService</code>, call one of the static
 * {@link #getInstance getInstance} methods, passing in the algorithm URI and
 * XML mechanism type desired, for example:
 *
 * <blockquote><code>
 * TransformService ts = TransformService.getInstance(Transform.XPATH2, "DOM");
 * </code></blockquote>
 *
 * <p><code>TransformService</code> implementations are registered and loaded
 * using the {@link java.security.Provider} mechanism.  Each
 * <code>TransformService</code> service provider implementation should include
 * a <code>MechanismType</code> service attribute that identifies the XML
 * mechanism type that it supports. If the attribute is not specified,
 * "DOM" is assumed. For example, a service provider that supports the
 * XPath Filter 2 Transform and DOM mechanism would be specified in the
 * <code>Provider</code> subclass as:
 * <pre>
 *     put("TransformService." + Transform.XPATH2,
 *         "org.example.XPath2TransformService");
 *     put("TransformService." + Transform.XPATH2 + " MechanismType", "DOM");
 * </pre>
 * <code>TransformService</code> implementations that support the DOM
 * mechanism type must abide by the DOM interoperability requirements defined
 * in the
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#DOM Mechanism Requirements">
 * DOM Mechanism Requirements</a> section of the API overview. See the
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#Service Provider">
 * Service Providers</a> section of the API overview for a list of standard
 * mechanism types.
 * <p>
 * Once a <code>TransformService</code> has been created, it can be used
 * to process <code>Transform</code> or <code>CanonicalizationMethod</code>
 * objects. If the <code>Transform</code> or <code>CanonicalizationMethod</code>
 * exists in XML form (for example, when validating an existing
 * <code>XMLSignature</code>), the {@link #init(XMLStructure, XMLCryptoContext)}
 * method must be first called to initialize the transform and provide document
 * context (even if there are no parameters). Alternatively, if the
 * <code>Transform</code> or <code>CanonicalizationMethod</code> is being
 * created from scratch, the {@link #init(TransformParameterSpec)} method
 * is called to initialize the transform with parameters and the
 * {@link #marshalParams marshalParams} method is called to marshal the
 * parameters to XML and provide the transform with document context. Finally,
 * the {@link #transform transform} method is called to perform the
 * transformation.
 * <p>
 * <b>Concurrent Access</b>
 * <p>The static methods of this class are guaranteed to be thread-safe.
 * Multiple threads may concurrently invoke the static methods defined in this
 * class with no ill effects.
 *
 * <p>However, this is not true for the non-static methods defined by this
 * class. Unless otherwise documented by a specific provider, threads that
 * need to access a single <code>TransformService</code> instance
 * concurrently should synchronize amongst themselves and provide the
 * necessary locking. Multiple threads each manipulating a different
 * <code>TransformService</code> instance need not synchronize.
 *
 * <p>
 *  用于变换和规范化算法的服务提供程序接口。
 * 
 *  <p> <code> TransformService </code>的每个实例都支持特定的转换或规范化算法和XML机制类型。
 * 要创建<code> TransformService </code>,请调用静态{@link #getInstance getInstance}方法之一,传入所需的算法URI和XML机制类型,例如：。
 * 
 *  <blockquote> <code> TransformService ts = TransformService.getInstance(Transform.XPATH2,"DOM"); </code>
 *  </blockquote>。
 * 
 *  <p> <code> TransformService </code>实现使用{@link java.security.Provider}机制注册和加载。
 * 每个<code> TransformService </code>服务提供程序实现应包括一个<code> MechanismType </code>服务属性,它标识它支持的XML机制类型。
 * 如果未指定属性,则假定为"DOM"。例如,支持XPath Filter 2 Transform和DOM机制的服务提供程序将在<code> Provider </code>子类中指定为：。
 * <pre>
 *  put("TransformService。
 * "+ Transform.XPATH2,"org.example.XPath2TransformService"); put("TransformService。
 * "+ Transform.XPATH2 +"MechanismType","DOM");。
 * </pre>
 * 支持DOM机制类型的<code> TransformService </code>实现必须遵守在中定义的DOM互操作性要求
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#DOM Mechanism Requirements">
 *  DOM机制要求</a>部分。看到
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#Service Provider">
 *  服务提供商</a>部分,了解标准机制类型的列表。
 * <p>
 *  一旦创建了一个<code> TransformService </code>,它就可以用来处理<code> Transform </code>或<code> CanonicalizationMetho
 * d </code>对象。
 * 如果<code> Transform </code>或<code> CanonicalizationMethod </code>以XML形式存在(例如,在验证现有的<code> XMLSignature
 *  </code>时),{@link #init(XMLStructure, XMLCryptoContext)}方法必须首先被调用来初始化转换并提供文档上下文(即使没有参数)。
 * 或者,如果从头开始创建<code> Transform </code>或<code> CanonicalizationMethod </code>,则调用{@link #init(TransformParameterSpec)}
 * 方法来初始化具有参数的变换,链接#marshalParams marshalParams}方法被调用来将参数封送到XML,并提供具有文档上下文的变换。
 * 最后,调用{@link #transform transform}方法来执行转换。
 * <p>
 *  <b>并发访问</b> <p>此类的静态方法保证是线程安全的。多线程可以同时调用这个类中定义的静态方法,没有不良影响。
 * 
 * <p>但是,这不是由这个类定义的非静态方法。除非特定提供程序另有说明,需要并发访问单个<code> TransformService </code>实例的线程应在它们之间同步并提供必要的锁定。
 * 每个操作不同<code> TransformService </code>实例的多个线程不需要同步。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public abstract class TransformService implements Transform {

    private String algorithm;
    private String mechanism;
    private Provider provider;

    /**
     * Default constructor, for invocation by subclasses.
     * <p>
     *  默认构造函数,用于由子类调用。
     * 
     */
    protected TransformService() {}

    /**
     * Returns a <code>TransformService</code> that supports the specified
     * algorithm URI (ex: {@link Transform#XPATH2}) and mechanism type
     * (ex: DOM).
     *
     * <p>This method uses the standard JCA provider lookup mechanism to
     * locate and instantiate a <code>TransformService</code> implementation
     * of the desired algorithm and <code>MechanismType</code> service
     * attribute. It traverses the list of registered security
     * <code>Provider</code>s, starting with the most preferred
     * <code>Provider</code>. A new <code>TransformService</code> object
     * from the first <code>Provider</code> that supports the specified
     * algorithm and mechanism type is returned.
     *
     * <p> Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回支持指定算法URI(例如：{@link Transform#XPATH2})和机制类型(例如：DOM)的<code> TransformService </code>。
     * 
     *  <p>此方法使用标准JCA提供程序查找机制来定位和实例化所需算法和<code> MechanismType </code>服务属性的<code> TransformService </code>实现。
     * 它遍历注册安全性<code>提供程序</code>的列表,从最优选的<code>提供程序</code>开始。
     * 将返回从支持指定算法和机制类型的第一个<code>提供程序</code>中创建的<code> TransformService </code>对象。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the URI of the algorithm
     * @param mechanismType the type of the XML processing mechanism and
     *   representation
     * @return a new <code>TransformService</code>
     * @throws NullPointerException if <code>algorithm</code> or
     *   <code>mechanismType</code> is  <code>null</code>
     * @throws NoSuchAlgorithmException if no <code>Provider</code> supports a
     *   <code>TransformService</code> implementation for the specified
     *   algorithm and mechanism type
     * @see Provider
     */
    public static TransformService getInstance
        (String algorithm, String mechanismType)
        throws NoSuchAlgorithmException {
        if (mechanismType == null || algorithm == null) {
            throw new NullPointerException();
        }
        boolean dom = false;
        if (mechanismType.equals("DOM")) {
            dom = true;
        }
        List<Service> services = GetInstance.getServices("TransformService", algorithm);
        for (Iterator<Service> t = services.iterator(); t.hasNext(); ) {
            Service s = t.next();
            String value = s.getAttribute("MechanismType");
            if ((value == null && dom) ||
                (value != null && value.equals(mechanismType))) {
                Instance instance = GetInstance.getInstance(s, null);
                TransformService ts = (TransformService) instance.impl;
                ts.algorithm = algorithm;
                ts.mechanism = mechanismType;
                ts.provider = instance.provider;
                return ts;
            }
        }
        throw new NoSuchAlgorithmException
            (algorithm + " algorithm and " + mechanismType
                 + " mechanism not available");
    }

    /**
     * Returns a <code>TransformService</code> that supports the specified
     * algorithm URI (ex: {@link Transform#XPATH2}) and mechanism type
     * (ex: DOM) as supplied by the specified provider. Note that the specified
     * <code>Provider</code> object does not have to be registered in the
     * provider list.
     *
     * <p>
     * 返回支持指定提供程序提供的指定算法URI(例如：{@link Transform#XPATH2})和机制类型(例如：DOM)的<code> TransformService </code>。
     * 请注意,指定的<code> Provider </code>对象不必在提供程序列表中注册。
     * 
     * 
     * @param algorithm the URI of the algorithm
     * @param mechanismType the type of the XML processing mechanism and
     *   representation
     * @param provider the <code>Provider</code> object
     * @return a new <code>TransformService</code>
     * @throws NullPointerException if <code>provider</code>,
     *   <code>algorithm</code>, or <code>mechanismType</code> is
     *   <code>null</code>
     * @throws NoSuchAlgorithmException if a <code>TransformService</code>
     *   implementation for the specified algorithm and mechanism type is not
     *   available from the specified <code>Provider</code> object
     * @see Provider
     */
    public static TransformService getInstance
        (String algorithm, String mechanismType, Provider provider)
        throws NoSuchAlgorithmException {
        if (mechanismType == null || algorithm == null || provider == null) {
            throw new NullPointerException();
        }

        boolean dom = false;
        if (mechanismType.equals("DOM")) {
            dom = true;
        }
        Service s = GetInstance.getService
            ("TransformService", algorithm, provider);
        String value = s.getAttribute("MechanismType");
        if ((value == null && dom) ||
            (value != null && value.equals(mechanismType))) {
            Instance instance = GetInstance.getInstance(s, null);
            TransformService ts = (TransformService) instance.impl;
            ts.algorithm = algorithm;
            ts.mechanism = mechanismType;
            ts.provider = instance.provider;
            return ts;
        }
        throw new NoSuchAlgorithmException
            (algorithm + " algorithm and " + mechanismType
                 + " mechanism not available");
    }

    /**
     * Returns a <code>TransformService</code> that supports the specified
     * algorithm URI (ex: {@link Transform#XPATH2}) and mechanism type
     * (ex: DOM) as supplied by the specified provider. The specified provider
     * must be registered in the security provider list.
     *
     * <p>Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回支持指定提供程序提供的指定算法URI(例如：{@link Transform#XPATH2})和机制类型(例如：DOM)的<code> TransformService </code>。
     * 指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param algorithm the URI of the algorithm
     * @param mechanismType the type of the XML processing mechanism and
     *   representation
     * @param provider the string name of the provider
     * @return a new <code>TransformService</code>
     * @throws NoSuchProviderException if the specified provider is not
     *   registered in the security provider list
     * @throws NullPointerException if <code>provider</code>,
     *   <code>mechanismType</code>, or <code>algorithm</code> is
     *   <code>null</code>
     * @throws NoSuchAlgorithmException if a <code>TransformService</code>
     *   implementation for the specified algorithm and mechanism type is not
     *   available from the specified provider
     * @see Provider
     */
    public static TransformService getInstance
        (String algorithm, String mechanismType, String provider)
        throws NoSuchAlgorithmException, NoSuchProviderException {
        if (mechanismType == null || algorithm == null || provider == null) {
            throw new NullPointerException();
        } else if (provider.length() == 0) {
            throw new NoSuchProviderException();
        }
        boolean dom = false;
        if (mechanismType.equals("DOM")) {
            dom = true;
        }
        Service s = GetInstance.getService
            ("TransformService", algorithm, provider);
        String value = s.getAttribute("MechanismType");
        if ((value == null && dom) ||
            (value != null && value.equals(mechanismType))) {
            Instance instance = GetInstance.getInstance(s, null);
            TransformService ts = (TransformService) instance.impl;
            ts.algorithm = algorithm;
            ts.mechanism = mechanismType;
            ts.provider = instance.provider;
            return ts;
        }
        throw new NoSuchAlgorithmException
            (algorithm + " algorithm and " + mechanismType
                 + " mechanism not available");
    }

    private static class MechanismMapEntry implements Map.Entry<String,String> {
        private final String mechanism;
        private final String algorithm;
        private final String key;
        MechanismMapEntry(String algorithm, String mechanism) {
            this.algorithm = algorithm;
            this.mechanism = mechanism;
            this.key = "TransformService." + algorithm + " MechanismType";
        }
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?,?> e = (Map.Entry<?,?>) o;
            return (getKey()==null ?
                    e.getKey()==null : getKey().equals(e.getKey())) &&
                   (getValue()==null ?
                    e.getValue()==null : getValue().equals(e.getValue()));
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return mechanism;
        }
        public String setValue(String value) {
            throw new UnsupportedOperationException();
        }
        public int hashCode() {
            return (getKey()==null ? 0 : getKey().hashCode()) ^
                   (getValue()==null ? 0 : getValue().hashCode());
        }
    }

    /**
     * Returns the mechanism type supported by this <code>TransformService</code>.
     *
     * <p>
     *  返回此<code> TransformService </code>支持的机制类型。
     * 
     * 
     * @return the mechanism type
     */
    public final String getMechanismType() {
        return mechanism;
    }

    /**
     * Returns the URI of the algorithm supported by this
     * <code>TransformService</code>.
     *
     * <p>
     *  返回此<code> TransformService </code>支持的算法的URI。
     * 
     * 
     * @return the algorithm URI
     */
    public final String getAlgorithm() {
        return algorithm;
    }

    /**
     * Returns the provider of this <code>TransformService</code>.
     *
     * <p>
     *  返回此<code> TransformService </code>的提供程序。
     * 
     * 
     * @return the provider
     */
    public final Provider getProvider() {
        return provider;
    }

    /**
     * Initializes this <code>TransformService</code> with the specified
     * parameters.
     *
     * <p>If the parameters exist in XML form, the
     * {@link #init(XMLStructure, XMLCryptoContext)} method should be used to
     * initialize the <code>TransformService</code>.
     *
     * <p>
     *  使用指定的参数初始化此<code> TransformService </code>。
     * 
     *  <p>如果参数以XML形式存在,则应使用{@link #init(XMLStructure,XMLCryptoContext)}方法初始化<code> TransformService </code>
     * 。
     * 
     * 
     * @param params the algorithm parameters (may be <code>null</code> if
     *   not required or optional)
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *   are invalid for this algorithm
     */
    public abstract void init(TransformParameterSpec params)
        throws InvalidAlgorithmParameterException;

    /**
     * Marshals the algorithm-specific parameters. If there are no parameters
     * to be marshalled, this method returns without throwing an exception.
     *
     * <p>
     *  编排算法特定的参数。如果没有要编组的参数,则此方法将返回而不抛出异常。
     * 
     * 
     * @param parent a mechanism-specific structure containing the parent
     *    node that the marshalled parameters should be appended to
     * @param context the <code>XMLCryptoContext</code> containing
     *    additional context (may be <code>null</code> if not applicable)
     * @throws ClassCastException if the type of <code>parent</code> or
     *    <code>context</code> is not compatible with this
     *    <code>TransformService</code>
     * @throws NullPointerException if <code>parent</code> is <code>null</code>
     * @throws MarshalException if the parameters cannot be marshalled
     */
    public abstract void marshalParams
        (XMLStructure parent, XMLCryptoContext context)
        throws MarshalException;

    /**
     * Initializes this <code>TransformService</code> with the specified
     * parameters and document context.
     *
     * <p>
     *  使用指定的参数和文档上下文初始化此<code> TransformService </code>。
     * 
     * @param parent a mechanism-specific structure containing the parent
     *    structure
     * @param context the <code>XMLCryptoContext</code> containing
     *    additional context (may be <code>null</code> if not applicable)
     * @throws ClassCastException if the type of <code>parent</code> or
     *    <code>context</code> is not compatible with this
     *    <code>TransformService</code>
     * @throws NullPointerException if <code>parent</code> is <code>null</code>
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *   are invalid for this algorithm
     */
    public abstract void init(XMLStructure parent, XMLCryptoContext context)
        throws InvalidAlgorithmParameterException;
}
