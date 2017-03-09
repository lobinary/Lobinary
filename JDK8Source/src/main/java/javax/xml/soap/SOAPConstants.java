/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

import javax.xml.namespace.QName;

/**
 * The definition of constants pertaining to the SOAP protocol.
 * <p>
 *  与SOAP协议相关的常量的定义。
 * 
 */
public interface SOAPConstants {
    /**
     * Used to create <code>MessageFactory</code> instances that create
     * <code>SOAPMessages</code> whose concrete type is based on the
     * <code>Content-Type</code> MIME header passed to the
     * <code>createMessage</code> method. If no <code>Content-Type</code>
     * header is passed then the <code>createMessage</code> may throw an
     * <code>IllegalArgumentException</code> or, in the case of the no
     * argument version of <code>createMessage</code>, an
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  用于创建<code> MessageFactory </code>实例,该实例创建<code> SOAPMessages </code>,具体类型基于传递给<code> createMessage <代码>
     * 方法。
     * 如果没有传递<code> Content-Type </code>头,那么<code> createMessage </code>可能会抛出<code> IllegalArgumentException
     *  </code> createMessage </code>,<code> UnsupportedOperationException </code>。
     * 
     * 
     * @since  SAAJ 1.3
     */
    public static final String DYNAMIC_SOAP_PROTOCOL = "Dynamic Protocol";

    /**
     * Used to create <code>MessageFactory</code> instances that create
     * <code>SOAPMessages</code> whose behavior supports the SOAP 1.1  specification.
     *
     * <p>
     *  用于创建<code> MessageFactory </code>实例,创建<code> SOAPMessages </code>,其行为支持SOAP 1.1规范。
     * 
     * 
     * @since  SAAJ 1.3
     */
    public static final String SOAP_1_1_PROTOCOL = "SOAP 1.1 Protocol";

    /**
     * Used to create <code>MessageFactory</code> instances that create
     * <code>SOAPMessages</code> whose behavior supports the SOAP 1.2
     * specification
     *
     * <p>
     *  用于创建<code> MessageFactory </code>实例,创建<code> SOAPMessages </code>,其行为支持SOAP 1.2规范
     * 
     * 
     * @since  SAAJ 1.3
     */
    public static final String SOAP_1_2_PROTOCOL = "SOAP 1.2 Protocol";

    /**
     * The default protocol: SOAP 1.1 for backwards compatibility.
     *
     * <p>
     *  默认协议：SOAP 1.1用于向后兼容。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String DEFAULT_SOAP_PROTOCOL = SOAP_1_1_PROTOCOL;

    /**
     * The namespace identifier for the SOAP 1.1 envelope.
     * <p>
     *  SOAP 1.1信封的命名空间标识符。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
                URI_NS_SOAP_1_1_ENVELOPE = "http://schemas.xmlsoap.org/soap/envelope/";
    /**
     * The namespace identifier for the SOAP 1.2 envelope.
     * <p>
     *  SOAP 1.2信封的命名空间标识符。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
                URI_NS_SOAP_1_2_ENVELOPE = "http://www.w3.org/2003/05/soap-envelope";

    /**
     * The namespace identifier for the SOAP 1.1 envelope, All SOAPElements in this
     * namespace are defined by the SOAP 1.1 specification.
     * <p>
     *  SOAP 1.1包络的命名空间标识符,此命名空间中的所有SOAPElements由SOAP 1.1规范定义。
     * 
     */
    public static final String
        URI_NS_SOAP_ENVELOPE = URI_NS_SOAP_1_1_ENVELOPE;

    /**
     * The namespace identifier for the SOAP 1.1 encoding.
     * An attribute named <code>encodingStyle</code> in the
     * <code>URI_NS_SOAP_ENVELOPE</code> namespace and set to the value
     * <code>URI_NS_SOAP_ENCODING</code> can be added to an element to indicate
     * that it is encoded using the rules in section 5 of the SOAP 1.1
     * specification.
     * <p>
     * SOAP 1.1编码的命名空间标识符。
     * 在<code> URI_NS_SOAP_ENVELOPE </code>命名空间中设置为<code> URI_NS_SOAP_ENCODING </code>的值</code>的属性<code> enc
     * odingStyle </code>可以添加到一个元素以表示它使用规则在SOAP 1.1规范的第5节。
     * SOAP 1.1编码的命名空间标识符。
     * 
     */
    public static final String
        URI_NS_SOAP_ENCODING = "http://schemas.xmlsoap.org/soap/encoding/";

    /**
     * The namespace identifier for the SOAP 1.2 encoding.
     * <p>
     *  SOAP 1.2编码的命名空间标识符。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
        URI_NS_SOAP_1_2_ENCODING = "http://www.w3.org/2003/05/soap-encoding";

    /**
     * The media type  of the <code>Content-Type</code> MIME header in SOAP 1.1.
     * <p>
     *  SOAP 1.1中的<code> Content-Type </code> MIME标题的媒体类型。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
        SOAP_1_1_CONTENT_TYPE = "text/xml";

    /**
     * The media type  of the <code>Content-Type</code> MIME header in SOAP 1.2.
     * <p>
     *  SOAP 1.2中的<code> Content-Type </code> MIME标题的媒体类型。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
        SOAP_1_2_CONTENT_TYPE = "application/soap+xml";

    /**
     * The URI identifying the next application processing a SOAP request as the intended
     * actor for a SOAP 1.1 header entry (see section 4.2.2 of the SOAP 1.1 specification).
     * <p>
     * This value can be passed to
     * {@link SOAPHeader#examineMustUnderstandHeaderElements(String)},
     * {@link SOAPHeader#examineHeaderElements(String)} and
     * {@link SOAPHeader#extractHeaderElements(String)}
     * <p>
     *  标识处理SOAP请求的下一个应用程序的URI作为SOAP 1.1标题条目的预期动作者(请参阅SOAP 1.1规范的第4.2.2节)。
     * <p>
     *  此值可以传递到{@link SOAPHeader#examineMustUnderstandHeaderElements(String)},{@link SOAPHeader#examineHeaderElements(String)}
     * 和{@link SOAPHeader#extractHeaderElements(String)}。
     * 
     */
    public static final String
        URI_SOAP_ACTOR_NEXT = "http://schemas.xmlsoap.org/soap/actor/next";

    /**
     * The URI identifying the next application processing a SOAP request as the intended
     * role for a SOAP 1.2 header entry (see section 2.2 of part 1 of the SOAP 1.2
     * specification).
     * <p>
     *  标识处理SOAP请求的下一个应用程序的URI作为SOAP 1.2标头条目的预期角色(请参阅SOAP 1.2规范第1部分的第2.2节)。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
        URI_SOAP_1_2_ROLE_NEXT = URI_NS_SOAP_1_2_ENVELOPE + "/role/next";

    /**
     * The URI specifying the role None in SOAP 1.2.
     * <p>
     *  指定SOAP 1.2中的角色None的URI。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
        URI_SOAP_1_2_ROLE_NONE = URI_NS_SOAP_1_2_ENVELOPE + "/role/none";

    /**
     * The URI identifying the ultimate receiver of the SOAP 1.2 message.
     * <p>
     *  URI标识SOAP 1.2消息的最终接收者。
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String
        URI_SOAP_1_2_ROLE_ULTIMATE_RECEIVER =
            URI_NS_SOAP_1_2_ENVELOPE + "/role/ultimateReceiver";

    /**
     * The default namespace prefix for http://www.w3.org/2003/05/soap-envelope
     * <p>
     *  http://www.w3.org/2003/05/soap-envelope的默认命名空间前缀
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final String SOAP_ENV_PREFIX = "env";

    /**
     * SOAP 1.2 VersionMismatch Fault
     * <p>
     *  SOAP 1.2版本不匹配故障
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final QName SOAP_VERSIONMISMATCH_FAULT =
         new QName(URI_NS_SOAP_1_2_ENVELOPE, "VersionMismatch", SOAP_ENV_PREFIX);

    /**
     * SOAP 1.2 MustUnderstand Fault
     * <p>
     *  SOAP 1.2 MustUnderstand故障
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final QName SOAP_MUSTUNDERSTAND_FAULT =
         new QName(URI_NS_SOAP_1_2_ENVELOPE, "MustUnderstand", SOAP_ENV_PREFIX);

    /**
     * SOAP 1.2 DataEncodingUnknown Fault
     * <p>
     *  SOAP 1.2 DataEncodingUnknown故障
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final QName SOAP_DATAENCODINGUNKNOWN_FAULT =
         new QName(URI_NS_SOAP_1_2_ENVELOPE, "DataEncodingUnknown", SOAP_ENV_PREFIX);

    /**
     * SOAP 1.2 Sender Fault
     * <p>
     *  SOAP 1.2发件人故障
     * 
     * 
     * @since SAAJ 1.3
     */
    public static final QName SOAP_SENDER_FAULT =
         new QName(URI_NS_SOAP_1_2_ENVELOPE, "Sender", SOAP_ENV_PREFIX);

    /**
     * SOAP 1.2 Receiver Fault
     * <p>
     *  SOAP 1.2接收器故障
     * 
     * @since SAAJ 1.3
     */
    public static final QName SOAP_RECEIVER_FAULT =
         new QName(URI_NS_SOAP_1_2_ENVELOPE, "Receiver", SOAP_ENV_PREFIX);

}
