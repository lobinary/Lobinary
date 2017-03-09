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
 * $Id: XMLSignatureFactory.java,v 1.14 2005/09/15 14:29:01 mullan Exp $
 * <p>
 *  $ Id：XMLSignatureFactory.java,v 1.14 2005/09/15 14:29:01 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig;

import javax.xml.crypto.Data;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.NoSuchMechanismException;
import javax.xml.crypto.URIDereferencer;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.spec.*;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.dom.DOMSignContext;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.List;

import sun.security.jca.*;
import sun.security.jca.GetInstance.Instance;

/**
 * A factory for creating {@link XMLSignature} objects from scratch or
 * for unmarshalling an <code>XMLSignature</code> object from a corresponding
 * XML representation.
 *
 * <h2>XMLSignatureFactory Type</h2>
 *
 * <p>Each instance of <code>XMLSignatureFactory</code> supports a specific
 * XML mechanism type. To create an <code>XMLSignatureFactory</code>, call one
 * of the static {@link #getInstance getInstance} methods, passing in the XML
 * mechanism type desired, for example:
 *
 * <blockquote><code>
 * XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM");
 * </code></blockquote>
 *
 * <p>The objects that this factory produces will be based
 * on DOM and abide by the DOM interoperability requirements as defined in the
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#DOM Mechanism Requirements">
 * DOM Mechanism Requirements</a> section of the API overview. See the
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#Service Provider">
 * Service Providers</a> section of the API overview for a list of standard
 * mechanism types.
 *
 * <p><code>XMLSignatureFactory</code> implementations are registered and loaded
 * using the {@link java.security.Provider} mechanism.
 * For example, a service provider that supports the
 * DOM mechanism would be specified in the <code>Provider</code> subclass as:
 * <pre>
 *     put("XMLSignatureFactory.DOM", "org.example.DOMXMLSignatureFactory");
 * </pre>
 *
 * <p>An implementation MUST minimally support the default mechanism type: DOM.
 *
 * <p>Note that a caller must use the same <code>XMLSignatureFactory</code>
 * instance to create the <code>XMLStructure</code>s of a particular
 * <code>XMLSignature</code> that is to be generated. The behavior is
 * undefined if <code>XMLStructure</code>s from different providers or
 * different mechanism types are used together.
 *
 * <p>Also, the <code>XMLStructure</code>s that are created by this factory
 * may contain state specific to the <code>XMLSignature</code> and are not
 * intended to be reusable.
 *
 * <h2>Creating XMLSignatures from scratch</h2>
 *
 * <p>Once the <code>XMLSignatureFactory</code> has been created, objects
 * can be instantiated by calling the appropriate method. For example, a
 * {@link Reference} instance may be created by invoking one of the
 * {@link #newReference newReference} methods.
 *
 * <h2>Unmarshalling XMLSignatures from XML</h2>
 *
 * <p>Alternatively, an <code>XMLSignature</code> may be created from an
 * existing XML representation by invoking the {@link #unmarshalXMLSignature
 * unmarshalXMLSignature} method and passing it a mechanism-specific
 * {@link XMLValidateContext} instance containing the XML content:
 *
 * <pre>
 * DOMValidateContext context = new DOMValidateContext(key, signatureElement);
 * XMLSignature signature = factory.unmarshalXMLSignature(context);
 * </pre>
 *
 * Each <code>XMLSignatureFactory</code> must support the required
 * <code>XMLValidateContext</code> types for that factory type, but may support
 * others. A DOM <code>XMLSignatureFactory</code> must support {@link
 * DOMValidateContext} objects.
 *
 * <h2>Signing and marshalling XMLSignatures to XML</h2>
 *
 * Each <code>XMLSignature</code> created by the factory can also be
 * marshalled to an XML representation and signed, by invoking the
 * {@link XMLSignature#sign sign} method of the
 * {@link XMLSignature} object and passing it a mechanism-specific
 * {@link XMLSignContext} object containing the signing key and
 * marshalling parameters (see {@link DOMSignContext}).
 * For example:
 *
 * <pre>
 *    DOMSignContext context = new DOMSignContext(privateKey, document);
 *    signature.sign(context);
 * </pre>
 *
 * <b>Concurrent Access</b>
 * <p>The static methods of this class are guaranteed to be thread-safe.
 * Multiple threads may concurrently invoke the static methods defined in this
 * class with no ill effects.
 *
 * <p>However, this is not true for the non-static methods defined by this
 * class. Unless otherwise documented by a specific provider, threads that
 * need to access a single <code>XMLSignatureFactory</code> instance
 * concurrently should synchronize amongst themselves and provide the
 * necessary locking. Multiple threads each manipulating a different
 * <code>XMLSignatureFactory</code> instance need not synchronize.
 *
 * <p>
 *  一个从头开始创建{@link XMLSignature}对象或从对应的XML表示中解组合一个<code> XMLSignature </code>对象的工厂。
 * 
 *  <h2> XMLSignatureFactory类型</h2>
 * 
 *  <p> <code> XMLSignatureFactory </code>的每个实例都支持特定的XML机制类型。
 * 要创建<code> XMLSignatureFactory </code>,请调用静态{@link #getInstance getInstance}方法之一,传入所需的XML机制类型,例如：。
 * 
 *  <blockquote> <code> XMLSignatureFactory factory = XMLSignatureFactory.getInstance("DOM"); </code> </blockquote>
 * 。
 * 
 *  <p>此工厂生成的对象将基于DOM,并遵守DOM中定义的DOM互操作性要求
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#DOM Mechanism Requirements">
 *  DOM机制要求</a>部分。看到
 * <a href="../../../../../technotes/guides/security/xmldsig/overview.html#Service Provider">
 *  服务提供商</a>部分,了解标准机制类型的列表。
 * 
 *  使用{@link java.security.Provider}机制注册并加载<p> <code> XMLSignatureFactory </code>实现。
 * 例如,支持DOM机制的服务提供者将在<code> Provider </code>子类中指定为：。
 * <pre>
 *  put("XMLSignatureFactory.DOM","org.example.DOMXMLSignatureFactory");
 * </pre>
 * 
 *  <p>实现必须最低限度支持默认机制类型：DOM。
 * 
 * <p>请注意,调用者必须使用相同的<code> XMLSignatureFactory </code>实例来创建要生成的特定<code> XMLSignature </code>的<code> XMLS
 * tructure </code>。
 * 如果来自不同提供程序或不同机制类型的<code> XMLStructure </code>一起使用,则行为是未定义的。
 * 
 *  <p>此外,由此工厂创建的<code> XMLStructure </code>可能包含特定于<code> XMLSignature </code>的状态,并且不打算可重复使用。
 * 
 *  <h2>从头创建XMLSignatures </h2>
 * 
 *  <p>一旦创建了<code> XMLSignatureFactory </code>,就可以通过调用适当的方法来实例化对象。
 * 例如,可以通过调用{@link #newReference newReference}方法之一来创建{@link Reference}实例。
 * 
 *  <h2>从XML解组XMLSignatures </h2>
 * 
 *  <p>或者,可以通过调用{@link #unmarshalXMLSignature unmarshalXMLSignature}方法并传递包含XML内容的特定于机制的{@link XMLValidateContext}
 * 实例,从现有XML表示中创建<code> XMLSignature </code> ：。
 * 
 * <pre>
 *  DOMValidateContext context = new DOMValidateContext(key,signatureElement); XMLSignature signature = 
 * factory.unmarshalXMLSignature(context);。
 * </pre>
 * 
 *  每个<code> XMLSignatureFactory </code>必须支持该工厂类型所需的<code> XMLValidateContext </code>类型,但可以支持其他类型。
 *  DOM <code> XMLSignatureFactory </code>必须支持{@link DOMValidateContext}对象。
 * 
 * <h2>将XMLSignatures签名和编组到XML </h2>
 * 
 *  通过调用{@link XMLSignature}对象的{@link XMLSignature#sign sign}方法并将其传递给机制,可以将工厂创建的每个<code> XMLSignature </code>
 * 编组为XML表示形式并进行签名,包含签名密钥和编组参数的特定{@link XMLSignContext}对象(参见{@link DOMSignContext})。
 * 例如：。
 * 
 * <pre>
 *  DOMSignContext context = new DOMSignContext(privateKey,document); signature.sign(context);
 * </pre>
 * 
 *  <b>并发访问</b> <p>此类的静态方法保证是线程安全的。多线程可以同时调用这个类中定义的静态方法,没有不良影响。
 * 
 *  <p>但是,这不是由这个类定义的非静态方法。除非特定提供者另有说明,需要同时访问单个<code> XMLSignatureFactory </code>实例的线程应在它们之间同步并提供必要的锁定。
 * 每个操作不同<code> XMLSignatureFactory </code>实例的多个线程不需要同步。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public abstract class XMLSignatureFactory {

    private String mechanismType;
    private Provider provider;

    /**
     * Default constructor, for invocation by subclasses.
     * <p>
     *  默认构造函数,用于由子类调用。
     * 
     */
    protected XMLSignatureFactory() {}

    /**
     * Returns an <code>XMLSignatureFactory</code> that supports the
     * specified XML processing mechanism and representation type (ex: "DOM").
     *
     * <p>This method uses the standard JCA provider lookup mechanism to
     * locate and instantiate an <code>XMLSignatureFactory</code>
     * implementation of the desired mechanism type. It traverses the list of
     * registered security <code>Provider</code>s, starting with the most
     * preferred <code>Provider</code>.  A new <code>XMLSignatureFactory</code>
     * object from the first <code>Provider</code> that supports the specified
     * mechanism is returned.
     *
     * <p>Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回支持指定的XML处理机制和表示类型(例如："DOM")的<code> XMLSignatureFactory </code>。
     * 
     * <p>此方法使用标准JCA提供程序查找机制来定位和实例化所需机制类型的<code> XMLSignatureFactory </code>实现。
     * 它遍历注册安全性<code>提供程序</code>的列表,从最优选的<code>提供程序</code>开始。
     * 将返回从支持指定机制的第一个<code>提供程序</code>中创建的新<代码> XMLSignatureFactory </code>对象。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param mechanismType the type of the XML processing mechanism and
     *    representation. See the <a
     *    href="../../../../../technotes/guides/security/xmldsig/overview.html#Service Provider">
     *    Service Providers</a> section of the API overview for a list of
     *    standard mechanism types.
     * @return a new <code>XMLSignatureFactory</code>
     * @throws NullPointerException if <code>mechanismType</code> is
     *    <code>null</code>
     * @throws NoSuchMechanismException if no <code>Provider</code> supports an
     *    <code>XMLSignatureFactory</code> implementation for the specified
     *    mechanism
     * @see Provider
     */
    public static XMLSignatureFactory getInstance(String mechanismType) {
        if (mechanismType == null) {
            throw new NullPointerException("mechanismType cannot be null");
        }
        Instance instance;
        try {
            instance = GetInstance.getInstance
                ("XMLSignatureFactory", null, mechanismType);
        } catch (NoSuchAlgorithmException nsae) {
            throw new NoSuchMechanismException(nsae);
        }
        XMLSignatureFactory factory = (XMLSignatureFactory) instance.impl;
        factory.mechanismType = mechanismType;
        factory.provider = instance.provider;
        return factory;
    }

    /**
     * Returns an <code>XMLSignatureFactory</code> that supports the
     * requested XML processing mechanism and representation type (ex: "DOM"),
     * as supplied by the specified provider. Note that the specified
     * <code>Provider</code> object does not have to be registered in the
     * provider list.
     *
     * <p>
     *  返回一个<code> XMLSignatureFactory </code>,它支持由指定提供程序提供的请求的XML处理机制和表示类型(例如："DOM")。
     * 请注意,指定的<code> Provider </code>对象不必在提供程序列表中注册。
     * 
     * 
     * @param mechanismType the type of the XML processing mechanism and
     *    representation. See the <a
     *    href="../../../../../technotes/guides/security/xmldsig/overview.html#Service Provider">
     *    Service Providers</a> section of the API overview for a list of
     *    standard mechanism types.
     * @param provider the <code>Provider</code> object
     * @return a new <code>XMLSignatureFactory</code>
     * @throws NullPointerException if <code>provider</code> or
     *    <code>mechanismType</code> is <code>null</code>
     * @throws NoSuchMechanismException if an <code>XMLSignatureFactory</code>
     *   implementation for the specified mechanism is not available
     *   from the specified <code>Provider</code> object
     * @see Provider
     */
    public static XMLSignatureFactory getInstance(String mechanismType,
        Provider provider) {
        if (mechanismType == null) {
            throw new NullPointerException("mechanismType cannot be null");
        } else if (provider == null) {
            throw new NullPointerException("provider cannot be null");
        }

        Instance instance;
        try {
            instance = GetInstance.getInstance
                ("XMLSignatureFactory", null, mechanismType, provider);
        } catch (NoSuchAlgorithmException nsae) {
            throw new NoSuchMechanismException(nsae);
        }
        XMLSignatureFactory factory = (XMLSignatureFactory) instance.impl;
        factory.mechanismType = mechanismType;
        factory.provider = instance.provider;
        return factory;
    }

    /**
     * Returns an <code>XMLSignatureFactory</code> that supports the
     * requested XML processing mechanism and representation type (ex: "DOM"),
     * as supplied by the specified provider. The specified provider must be
     * registered in the security provider list.
     *
     * <p>Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回一个<code> XMLSignatureFactory </code>,它支持由指定提供程序提供的请求的XML处理机制和表示类型(例如："DOM")。
     * 指定的提供程序必须在安全提供程序列表中注册。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @param mechanismType the type of the XML processing mechanism and
     *    representation. See the <a
     *    href="../../../../../technotes/guides/security/xmldsig/overview.html#Service Provider">
     *    Service Providers</a> section of the API overview for a list of
     *    standard mechanism types.
     * @param provider the string name of the provider
     * @return a new <code>XMLSignatureFactory</code>
     * @throws NoSuchProviderException if the specified provider is not
     *    registered in the security provider list
     * @throws NullPointerException if <code>provider</code> or
     *    <code>mechanismType</code> is <code>null</code>
     * @throws NoSuchMechanismException if an <code>XMLSignatureFactory</code>
     *    implementation for the specified mechanism is not
     *    available from the specified provider
     * @see Provider
     */
    public static XMLSignatureFactory getInstance(String mechanismType,
        String provider) throws NoSuchProviderException {
        if (mechanismType == null) {
            throw new NullPointerException("mechanismType cannot be null");
        } else if (provider == null) {
            throw new NullPointerException("provider cannot be null");
        } else if (provider.length() == 0) {
            throw new NoSuchProviderException();
        }

        Instance instance;
        try {
            instance = GetInstance.getInstance
                ("XMLSignatureFactory", null, mechanismType, provider);
        } catch (NoSuchAlgorithmException nsae) {
            throw new NoSuchMechanismException(nsae);
        }
        XMLSignatureFactory factory = (XMLSignatureFactory) instance.impl;
        factory.mechanismType = mechanismType;
        factory.provider = instance.provider;
        return factory;
    }

    /**
     * Returns an <code>XMLSignatureFactory</code> that supports the
     * default XML processing mechanism and representation type ("DOM").
     *
     * <p>This method uses the standard JCA provider lookup mechanism to
     * locate and instantiate an <code>XMLSignatureFactory</code>
     * implementation of the default mechanism type. It traverses the list of
     * registered security <code>Provider</code>s, starting with the most
     * preferred <code>Provider</code>.  A new <code>XMLSignatureFactory</code>
     * object from the first <code>Provider</code> that supports the DOM
     * mechanism is returned.
     *
     * <p>Note that the list of registered providers may be retrieved via
     * the {@link Security#getProviders() Security.getProviders()} method.
     *
     * <p>
     *  返回支持默认XML处理机制和表示类型("DOM")的<code> XMLSignatureFactory </code>。
     * 
     * <p>此方法使用标准JCA提供程序查找机制来定位和实例化默认机制类型的<code> XMLSignatureFactory </code>实现。
     * 它遍历注册安全性<code>提供程序</code>的列表,从最优选的<code>提供程序</code>开始。
     * 将返回来自支持DOM机制的第一个<code> Provider </code>的新<XML> XMLSignatureFactory </code>对象。
     * 
     *  <p>请注意,可以通过{@link Security#getProviders()Security.getProviders()}方法检索注册提供商的列表。
     * 
     * 
     * @return a new <code>XMLSignatureFactory</code>
     * @throws NoSuchMechanismException if no <code>Provider</code> supports an
     *    <code>XMLSignatureFactory</code> implementation for the DOM
     *    mechanism
     * @see Provider
     */
    public static XMLSignatureFactory getInstance() {
        return getInstance("DOM");
    }

    /**
     * Returns the type of the XML processing mechanism and representation
     * supported by this <code>XMLSignatureFactory</code> (ex: "DOM").
     *
     * <p>
     *  返回此<code> XMLSignatureFactory </code>(例如："DOM")支持的XML处理机制和表示形式的类型。
     * 
     * 
     * @return the XML processing mechanism type supported by this
     *    <code>XMLSignatureFactory</code>
     */
    public final String getMechanismType() {
        return mechanismType;
    }

    /**
     * Returns the provider of this <code>XMLSignatureFactory</code>.
     *
     * <p>
     *  返回此<code> XMLSignatureFactory </code>的提供程序。
     * 
     * 
     * @return the provider of this <code>XMLSignatureFactory</code>
     */
    public final Provider getProvider() {
        return provider;
    }

    /**
     * Creates an <code>XMLSignature</code> and initializes it with the contents
     * of the specified <code>SignedInfo</code> and <code>KeyInfo</code>
     * objects.
     *
     * <p>
     *  创建<code> XMLSignature </code>并使用指定的<code> SignedInfo </code>和<code> KeyInfo </code>对象的内容初始化它。
     * 
     * 
     * @param si the signed info
     * @param ki the key info (may be <code>null</code>)
     * @return an <code>XMLSignature</code>
     * @throws NullPointerException if <code>si</code> is <code>null</code>
     */
    public abstract XMLSignature newXMLSignature(SignedInfo si, KeyInfo ki);

    /**
     * Creates an <code>XMLSignature</code> and initializes it with the
     * specified parameters.
     *
     * <p>
     *  创建<code> XMLSignature </code>并使用指定的参数初始化它。
     * 
     * 
     * @param si the signed info
     * @param ki the key info (may be <code>null</code>)
     * @param objects a list of {@link XMLObject}s (may be empty or
     *    <code>null</code>)
     * @param id the Id (may be <code>null</code>)
     * @param signatureValueId the SignatureValue Id (may be <code>null</code>)
     * @return an <code>XMLSignature</code>
     * @throws NullPointerException if <code>si</code> is <code>null</code>
     * @throws ClassCastException if any of the <code>objects</code> are not of
     *    type <code>XMLObject</code>
     */
    @SuppressWarnings("rawtypes")
    public abstract XMLSignature newXMLSignature(SignedInfo si, KeyInfo ki,
        List objects, String id, String signatureValueId);

    /**
     * Creates a <code>Reference</code> with the specified URI and digest
     * method.
     *
     * <p>
     *  使用指定的URI和digest方法创建<code>引用</code>。
     * 
     * 
     * @param uri the reference URI (may be <code>null</code>)
     * @param dm the digest method
     * @return a <code>Reference</code>
     * @throws IllegalArgumentException if <code>uri</code> is not RFC 2396
     *    compliant
     * @throws NullPointerException if <code>dm</code> is <code>null</code>
     */
    public abstract Reference newReference(String uri, DigestMethod dm);

    /**
     * Creates a <code>Reference</code> with the specified parameters.
     *
     * <p>
     *  使用指定的参数创建<code>引用</code>。
     * 
     * 
     * @param uri the reference URI (may be <code>null</code>)
     * @param dm the digest method
     * @param transforms a list of {@link Transform}s. The list is defensively
     *    copied to protect against subsequent modification. May be
     *    <code>null</code> or empty.
     * @param type the reference type, as a URI (may be <code>null</code>)
     * @param id the reference ID (may be <code>null</code>)
     * @return a <code>Reference</code>
     * @throws ClassCastException if any of the <code>transforms</code> are
     *    not of type <code>Transform</code>
     * @throws IllegalArgumentException if <code>uri</code> is not RFC 2396
     *    compliant
     * @throws NullPointerException if <code>dm</code> is <code>null</code>
     */
    @SuppressWarnings("rawtypes")
    public abstract Reference newReference(String uri, DigestMethod dm,
        List transforms, String type, String id);

    /**
     * Creates a <code>Reference</code> with the specified parameters and
     * pre-calculated digest value.
     *
     * <p>This method is useful when the digest value of a
     * <code>Reference</code> has been previously computed. See for example,
     * the
     * <a href="http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=dss">
     * OASIS-DSS (Digital Signature Services)</a> specification.
     *
     * <p>
     *  创建具有指定参数和预计算摘要值的<code>参考</code>。
     * 
     *  <p>当之前已经计算了<code> Reference </code>的摘要值时,此方法很有用。参见例如
     * <a href="http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=dss">
     *  OASIS-DSS(数字签名服务)</a>规范。
     * 
     * 
     * @param uri the reference URI (may be <code>null</code>)
     * @param dm the digest method
     * @param transforms a list of {@link Transform}s. The list is defensively
     *    copied to protect against subsequent modification. May be
     *    <code>null</code> or empty.
     * @param type the reference type, as a URI (may be <code>null</code>)
     * @param id the reference ID (may be <code>null</code>)
     * @param digestValue the digest value. The array is cloned to protect
     *    against subsequent modification.
     * @return a <code>Reference</code>
     * @throws ClassCastException if any of the <code>transforms</code> are
     *    not of type <code>Transform</code>
     * @throws IllegalArgumentException if <code>uri</code> is not RFC 2396
     *    compliant
     * @throws NullPointerException if <code>dm</code> or
     *    <code>digestValue</code> is <code>null</code>
     */
    @SuppressWarnings("rawtypes")
    public abstract Reference newReference(String uri, DigestMethod dm,
        List transforms, String type, String id, byte[] digestValue);

    /**
     * Creates a <code>Reference</code> with the specified parameters.
     *
     * <p>This method is useful when a list of transforms have already been
     * applied to the <code>Reference</code>. See for example,
     * the
     * <a href="http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=dss">
     * OASIS-DSS (Digital Signature Services)</a> specification.
     *
     * <p>When an <code>XMLSignature</code> containing this reference is
     * generated, the specified <code>transforms</code> (if non-null) are
     * applied to the specified <code>result</code>. The
     * <code>Transforms</code> element of the resulting <code>Reference</code>
     * element is set to the concatenation of the
     * <code>appliedTransforms</code> and <code>transforms</code>.
     *
     * <p>
     *  使用指定的参数创建<code>引用</code>。
     * 
     * <p>此方法在变换列表已应用于<code> Reference </code>时非常有用。参见例如
     * <a href="http://www.oasis-open.org/committees/tc_home.php?wg_abbrev=dss">
     *  OASIS-DSS(数字签名服务)</a>规范。
     * 
     *  <p>当生成包含此引用的<code> XMLSignature </code>时,指定的<code> transforms </code>(如果为非null)应用于指定的<code> result </code>
     * 。
     * 生成的<code> Reference </code>元素的<code> Transforms </code>元素设置为<code> appliedTransforms </code>和<code> t
     * ransforms </code>的连接。
     * 
     * 
     * @param uri the reference URI (may be <code>null</code>)
     * @param dm the digest method
     * @param appliedTransforms a list of {@link Transform}s that have
     *    already been applied. The list is defensively
     *    copied to protect against subsequent modification. The list must
     *    contain at least one entry.
     * @param result the result of processing the sequence of
     *    <code>appliedTransforms</code>
     * @param transforms a list of {@link Transform}s that are to be applied
     *    when generating the signature. The list is defensively copied to
     *    protect against subsequent modification. May be <code>null</code>
     *    or empty.
     * @param type the reference type, as a URI (may be <code>null</code>)
     * @param id the reference ID (may be <code>null</code>)
     * @return a <code>Reference</code>
     * @throws ClassCastException if any of the transforms (in either list)
     *    are not of type <code>Transform</code>
     * @throws IllegalArgumentException if <code>uri</code> is not RFC 2396
     *    compliant or <code>appliedTransforms</code> is empty
     * @throws NullPointerException if <code>dm</code>,
     *    <code>appliedTransforms</code> or <code>result</code> is
     *    <code>null</code>
     */
    @SuppressWarnings("rawtypes")
    public abstract Reference newReference(String uri, DigestMethod dm,
        List appliedTransforms, Data result, List transforms, String type,
        String id);

    /**
     * Creates a <code>SignedInfo</code> with the specified canonicalization
     * and signature methods, and list of one or more references.
     *
     * <p>
     *  使用指定的规范化和签名方法以及一个或多个引用的列表创建一个<code> SignedInfo </code>。
     * 
     * 
     * @param cm the canonicalization method
     * @param sm the signature method
     * @param references a list of one or more {@link Reference}s. The list is
     *    defensively copied to protect against subsequent modification.
     * @return a <code>SignedInfo</code>
     * @throws ClassCastException if any of the references are not of
     *    type <code>Reference</code>
     * @throws IllegalArgumentException if <code>references</code> is empty
     * @throws NullPointerException if any of the parameters
     *    are <code>null</code>
     */
    @SuppressWarnings("rawtypes")
    public abstract SignedInfo newSignedInfo(CanonicalizationMethod cm,
        SignatureMethod sm, List references);

    /**
     * Creates a <code>SignedInfo</code> with the specified parameters.
     *
     * <p>
     *  使用指定的参数创建<code> SignedInfo </code>。
     * 
     * 
     * @param cm the canonicalization method
     * @param sm the signature method
     * @param references a list of one or more {@link Reference}s. The list is
     *    defensively copied to protect against subsequent modification.
     * @param id the id (may be <code>null</code>)
     * @return a <code>SignedInfo</code>
     * @throws ClassCastException if any of the references are not of
     *    type <code>Reference</code>
     * @throws IllegalArgumentException if <code>references</code> is empty
     * @throws NullPointerException if <code>cm</code>, <code>sm</code>, or
     *    <code>references</code> are <code>null</code>
     */
    @SuppressWarnings("rawtypes")
    public abstract SignedInfo newSignedInfo(CanonicalizationMethod cm,
        SignatureMethod sm, List references, String id);

    // Object factory methods
    /**
     * Creates an <code>XMLObject</code> from the specified parameters.
     *
     * <p>
     *  从指定的参数创建<code> XMLObject </code>。
     * 
     * 
     * @param content a list of {@link XMLStructure}s. The list
     *    is defensively copied to protect against subsequent modification.
     *    May be <code>null</code> or empty.
     * @param id the Id (may be <code>null</code>)
     * @param mimeType the mime type (may be <code>null</code>)
     * @param encoding the encoding (may be <code>null</code>)
     * @return an <code>XMLObject</code>
     * @throws ClassCastException if <code>content</code> contains any
     *    entries that are not of type {@link XMLStructure}
     */
    @SuppressWarnings("rawtypes")
    public abstract XMLObject newXMLObject(List content, String id,
        String mimeType, String encoding);

    /**
     * Creates a <code>Manifest</code> containing the specified
     * list of {@link Reference}s.
     *
     * <p>
     *  创建一个包含指定的{@link Reference}列表的<code> Manifest </code>。
     * 
     * 
     * @param references a list of one or more <code>Reference</code>s. The list
     *    is defensively copied to protect against subsequent modification.
     * @return a <code>Manifest</code>
     * @throws NullPointerException if <code>references</code> is
     *    <code>null</code>
     * @throws IllegalArgumentException if <code>references</code> is empty
     * @throws ClassCastException if <code>references</code> contains any
     *    entries that are not of type {@link Reference}
     */
    @SuppressWarnings("rawtypes")
    public abstract Manifest newManifest(List references);

    /**
     * Creates a <code>Manifest</code> containing the specified
     * list of {@link Reference}s and optional id.
     *
     * <p>
     *  创建包含指定的{@link Reference}和可选ID列表的<code> Manifest </code>。
     * 
     * 
     * @param references a list of one or more <code>Reference</code>s. The list
     *    is defensively copied to protect against subsequent modification.
     * @param id the id (may be <code>null</code>)
     * @return a <code>Manifest</code>
     * @throws NullPointerException if <code>references</code> is
     *    <code>null</code>
     * @throws IllegalArgumentException if <code>references</code> is empty
     * @throws ClassCastException if <code>references</code> contains any
     *    entries that are not of type {@link Reference}
     */
    @SuppressWarnings("rawtypes")
    public abstract Manifest newManifest(List references, String id);

    /**
     * Creates a <code>SignatureProperty</code> containing the specified
     * list of {@link XMLStructure}s, target URI and optional id.
     *
     * <p>
     *  创建包含指定的{@link XMLStructure},目标URI和可选ID列表的<code> SignatureProperty </code>。
     * 
     * 
     * @param content a list of one or more <code>XMLStructure</code>s. The list
     *    is defensively copied to protect against subsequent modification.
     * @param target the target URI of the Signature that this property applies
     *    to
     * @param id the id (may be <code>null</code>)
     * @return a <code>SignatureProperty</code>
     * @throws NullPointerException if <code>content</code> or
     *    <code>target</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>content</code> is empty
     * @throws ClassCastException if <code>content</code> contains any
     *    entries that are not of type {@link XMLStructure}
     */
    @SuppressWarnings("rawtypes")
    public abstract SignatureProperty newSignatureProperty
        (List content, String target, String id);

    /**
     * Creates a <code>SignatureProperties</code> containing the specified
     * list of {@link SignatureProperty}s and optional id.
     *
     * <p>
     *  创建包含指定的{@link SignatureProperty}和可选ID列表的<code> SignatureProperties </code>。
     * 
     * 
     * @param properties a list of one or more <code>SignatureProperty</code>s.
     *    The list is defensively copied to protect against subsequent
     *    modification.
     * @param id the id (may be <code>null</code>)
     * @return a <code>SignatureProperties</code>
     * @throws NullPointerException if <code>properties</code>
     *    is <code>null</code>
     * @throws IllegalArgumentException if <code>properties</code> is empty
     * @throws ClassCastException if <code>properties</code> contains any
     *    entries that are not of type {@link SignatureProperty}
     */
    @SuppressWarnings("rawtypes")
    public abstract SignatureProperties newSignatureProperties
        (List properties, String id);

    // Algorithm factory methods
    /**
     * Creates a <code>DigestMethod</code> for the specified algorithm URI
     * and parameters.
     *
     * <p>
     *  为指定的算法URI和参数创建<code> DigestMethod </code>。
     * 
     * 
     * @param algorithm the URI identifying the digest algorithm
     * @param params algorithm-specific digest parameters (may be
     *    <code>null</code>)
     * @return the <code>DigestMethod</code>
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *    are inappropriate for the requested algorithm
     * @throws NoSuchAlgorithmException if an implementation of the
     *    specified algorithm cannot be found
     * @throws NullPointerException if <code>algorithm</code> is
     *    <code>null</code>
     */
    public abstract DigestMethod newDigestMethod(String algorithm,
        DigestMethodParameterSpec params) throws NoSuchAlgorithmException,
        InvalidAlgorithmParameterException;

    /**
     * Creates a <code>SignatureMethod</code> for the specified algorithm URI
     * and parameters.
     *
     * <p>
     *  为指定的算法URI和参数创建<code> SignatureMethod </code>。
     * 
     * 
     * @param algorithm the URI identifying the signature algorithm
     * @param params algorithm-specific signature parameters (may be
     *    <code>null</code>)
     * @return the <code>SignatureMethod</code>
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *    are inappropriate for the requested algorithm
     * @throws NoSuchAlgorithmException if an implementation of the
     *    specified algorithm cannot be found
     * @throws NullPointerException if <code>algorithm</code> is
     *    <code>null</code>
     */
    public abstract SignatureMethod newSignatureMethod(String algorithm,
        SignatureMethodParameterSpec params) throws NoSuchAlgorithmException,
        InvalidAlgorithmParameterException;

    /**
     * Creates a <code>Transform</code> for the specified algorithm URI
     * and parameters.
     *
     * <p>
     *  为指定的算法URI和参数创建<code>变换</code>。
     * 
     * 
     * @param algorithm the URI identifying the transform algorithm
     * @param params algorithm-specific transform parameters (may be
     *    <code>null</code>)
     * @return the <code>Transform</code>
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *    are inappropriate for the requested algorithm
     * @throws NoSuchAlgorithmException if an implementation of the
     *    specified algorithm cannot be found
     * @throws NullPointerException if <code>algorithm</code> is
     *    <code>null</code>
     */
    public abstract Transform newTransform(String algorithm,
        TransformParameterSpec params) throws NoSuchAlgorithmException,
        InvalidAlgorithmParameterException;

    /**
     * Creates a <code>Transform</code> for the specified algorithm URI
     * and parameters. The parameters are specified as a mechanism-specific
     * <code>XMLStructure</code> (ex: {@link DOMStructure}). This method is
     * useful when the parameters are in XML form or there is no standard
     * class for specifying the parameters.
     *
     * <p>
     * 为指定的算法URI和参数创建<code>变换</code>。参数被指定为机制特定的<code> XMLStructure </code>(例如：{@link DOMStructure})。
     * 当参数为XML形式或没有用于指定参数的标准类时,此方法很有用。
     * 
     * 
     * @param algorithm the URI identifying the transform algorithm
     * @param params a mechanism-specific XML structure from which to
     *   unmarshal the parameters from (may be <code>null</code> if
     *   not required or optional)
     * @return the <code>Transform</code>
     * @throws ClassCastException if the type of <code>params</code> is
     *   inappropriate for this <code>XMLSignatureFactory</code>
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *    are inappropriate for the requested algorithm
     * @throws NoSuchAlgorithmException if an implementation of the
     *    specified algorithm cannot be found
     * @throws NullPointerException if <code>algorithm</code> is
     *    <code>null</code>
     */
    public abstract Transform newTransform(String algorithm,
        XMLStructure params) throws NoSuchAlgorithmException,
        InvalidAlgorithmParameterException;

    /**
     * Creates a <code>CanonicalizationMethod</code> for the specified
     * algorithm URI and parameters.
     *
     * <p>
     *  为指定的算法URI和参数创建<code> CanonicalizationMethod </code>。
     * 
     * 
     * @param algorithm the URI identifying the canonicalization algorithm
     * @param params algorithm-specific canonicalization parameters (may be
     *    <code>null</code>)
     * @return the <code>CanonicalizationMethod</code>
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *    are inappropriate for the requested algorithm
     * @throws NoSuchAlgorithmException if an implementation of the
     *    specified algorithm cannot be found
     * @throws NullPointerException if <code>algorithm</code> is
     *    <code>null</code>
     */
    public abstract CanonicalizationMethod newCanonicalizationMethod(
        String algorithm, C14NMethodParameterSpec params)
        throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;

    /**
     * Creates a <code>CanonicalizationMethod</code> for the specified
     * algorithm URI and parameters. The parameters are specified as a
     * mechanism-specific <code>XMLStructure</code> (ex: {@link DOMStructure}).
     * This method is useful when the parameters are in XML form or there is
     * no standard class for specifying the parameters.
     *
     * <p>
     *  为指定的算法URI和参数创建<code> CanonicalizationMethod </code>。
     * 参数被指定为机制特定的<code> XMLStructure </code>(例如：{@link DOMStructure})。当参数为XML形式或没有用于指定参数的标准类时,此方法很有用。
     * 
     * 
     * @param algorithm the URI identifying the canonicalization algorithm
     * @param params a mechanism-specific XML structure from which to
     *   unmarshal the parameters from (may be <code>null</code> if
     *   not required or optional)
     * @return the <code>CanonicalizationMethod</code>
     * @throws ClassCastException if the type of <code>params</code> is
     *   inappropriate for this <code>XMLSignatureFactory</code>
     * @throws InvalidAlgorithmParameterException if the specified parameters
     *    are inappropriate for the requested algorithm
     * @throws NoSuchAlgorithmException if an implementation of the
     *    specified algorithm cannot be found
     * @throws NullPointerException if <code>algorithm</code> is
     *    <code>null</code>
     */
    public abstract CanonicalizationMethod newCanonicalizationMethod(
        String algorithm, XMLStructure params)
        throws NoSuchAlgorithmException, InvalidAlgorithmParameterException;

    /**
     * Returns a <code>KeyInfoFactory</code> that creates <code>KeyInfo</code>
     * objects. The returned <code>KeyInfoFactory</code> has the same
     * mechanism type and provider as this <code>XMLSignatureFactory</code>.
     *
     * <p>
     *  返回创建<code> KeyInfo </code>对象的<code> KeyInfoFactory </code>。
     * 返回的<code> KeyInfoFactory </code>具有与此<code> XMLSignatureFactory </code>相同的机制类型和提供程序。
     * 
     * 
     * @return a <code>KeyInfoFactory</code>
     * @throws NoSuchMechanismException if a <code>KeyFactory</code>
     *    implementation with the same mechanism type and provider
     *    is not available
     */
    public final KeyInfoFactory getKeyInfoFactory() {
        return KeyInfoFactory.getInstance(getMechanismType(), getProvider());
    }

    /**
     * Unmarshals a new <code>XMLSignature</code> instance from a
     * mechanism-specific <code>XMLValidateContext</code> instance.
     *
     * <p>
     *  从机制特定的<code> XMLValidateContext </code>实例中解组新的<code> XMLSignature </code>实例。
     * 
     * 
     * @param context a mechanism-specific context from which to unmarshal the
     *    signature from
     * @return the <code>XMLSignature</code>
     * @throws NullPointerException if <code>context</code> is
     *    <code>null</code>
     * @throws ClassCastException if the type of <code>context</code> is
     *    inappropriate for this factory
     * @throws MarshalException if an unrecoverable exception occurs
     *    during unmarshalling
     */
    public abstract XMLSignature unmarshalXMLSignature
        (XMLValidateContext context) throws MarshalException;

    /**
     * Unmarshals a new <code>XMLSignature</code> instance from a
     * mechanism-specific <code>XMLStructure</code> instance.
     * This method is useful if you only want to unmarshal (and not
     * validate) an <code>XMLSignature</code>.
     *
     * <p>
     *  从机制特定的<code> XMLStructure </code>实例中解组新的<code> XMLSignature </code>实例。
     * 如果您只想解组(而不验证)<code> XMLSignature </code>,此方法很有用。
     * 
     * 
     * @param xmlStructure a mechanism-specific XML structure from which to
     *    unmarshal the signature from
     * @return the <code>XMLSignature</code>
     * @throws NullPointerException if <code>xmlStructure</code> is
     *    <code>null</code>
     * @throws ClassCastException if the type of <code>xmlStructure</code> is
     *    inappropriate for this factory
     * @throws MarshalException if an unrecoverable exception occurs
     *    during unmarshalling
     */
    public abstract XMLSignature unmarshalXMLSignature
        (XMLStructure xmlStructure) throws MarshalException;

    /**
     * Indicates whether a specified feature is supported.
     *
     * <p>
     *  指示是否支持指定的功能。
     * 
     * 
     * @param feature the feature name (as an absolute URI)
     * @return <code>true</code> if the specified feature is supported,
     *    <code>false</code> otherwise
     * @throws NullPointerException if <code>feature</code> is <code>null</code>
     */
    public abstract boolean isFeatureSupported(String feature);

    /**
     * Returns a reference to the <code>URIDereferencer</code> that is used by
     * default to dereference URIs in {@link Reference} objects.
     *
     * <p>
     *  返回对<code> URIDereferencer </code>的引用,该引用默认用于解除引用{@link Reference}对象中的URI。
     * 
     * @return a reference to the default <code>URIDereferencer</code> (never
     *    <code>null</code>)
     */
    public abstract URIDereferencer getURIDereferencer();
}
