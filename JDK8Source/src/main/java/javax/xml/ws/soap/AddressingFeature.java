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

package javax.xml.ws.soap;

import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

/**
 * AddressingFeature represents the use of WS-Addressing with either
 * the SOAP 1.1/HTTP or SOAP 1.2/HTTP binding. Using this feature
 * with any other binding is undefined.
 * <p>
 * This feature can be used during the creation of SEI proxy, and
 * {@link javax.xml.ws.Dispatch} instances on the client side and {@link Endpoint}
 * instances on the server side. This feature cannot be used for {@link Service}
 * instance creation on the client side.
 * <p>
 * The following describes the effects of this feature with respect
 * to be enabled or disabled:
 * <ul>
 *  <li> ENABLED: In this Mode, WS-Addressing will be enabled. It means
 *       the endpoint supports WS-Addressing but does not require its use.
 *       A sender could send messages with WS-Addressing headers or without
 *       WS-Addressing headers. But a receiver MUST consume both types of
 *       messages.
 *  <li> DISABLED: In this Mode, WS-Addressing will be disabled.
 *       At runtime, WS-Addressing headers MUST NOT be used by a sender or
 *       receiver.
 * </ul>
 * <p>
 * If the feature is enabled, the <code>required</code> property determines
 * whether the endpoint requires WS-Addressing. If it is set true,
 * WS-Addressing headers MUST be present on incoming and outgoing messages.
 * By default the <code>required</code> property is <code>false</code>.
 *
 * <p>
 * If the web service developer has not explicitly enabled this feature,
 * WSDL's wsam:Addressing policy assertion is used to find
 * the use of WS-Addressing. By using the feature explicitly, an application
 * overrides WSDL's indication of the use of WS-Addressing. In some cases,
 * this is really required. For example, if an application has implemented
 * WS-Addressing itself, it can use this feature to disable addressing. That
 * means a JAX-WS implementation doesn't consume or produce WS-Addressing
 * headers.
 *
 * <p>
 * If addressing is enabled, a corresponding wsam:Addressing policy assertion
 * must be generated in the WSDL as per
 * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyassertions">
 * 3.1 WS-Policy Assertions</a>
 *
 * <p>
 * <b>Example 1: </b>Possible Policy Assertion in the generated WSDL for
 * <code>&#64;Addressing</code>
 * <pre>
 *   &lt;wsam:Addressing wsp:Optional="true">
 *     &lt;wsp:Policy/>
 *   &lt;/wsam:Addressing>
 * </pre>
 *
 * <p>
 * <b>Example 2: </b>Possible Policy Assertion in the generated WSDL for
 * <code>&#64;Addressing(required=true)</code>
 * <pre>
 *   &lt;wsam:Addressing>
 *     &lt;wsp:Policy/>
 *   &lt;/wsam:Addressing>
 * </pre>
 *
 * <p>
 * <b>Example 3: </b>Possible Policy Assertion in the generated WSDL for
 * <code>&#64;Addressing(required=true, responses=Responses.ANONYMOUS)</code>
 * <pre>
 *   &lt;wsam:Addressing>
 *      &lt;wsp:Policy>
 *        &lt;wsam:AnonymousResponses/>
 *      &lt;/wsp:Policy>
 *   &lt;/wsam:Addressing>
 * </pre>
 *
 * <p>
 * See <a href="http://www.w3.org/TR/2006/REC-ws-addr-core-20060509/">
 * Web Services Addressing - Core</a>,
 * <a href="http://www.w3.org/TR/2006/REC-ws-addr-soap-20060509/">
 * Web Services Addressing 1.0 - SOAP Binding</a>,
 * and <a href="http://www.w3.org/TR/ws-addr-metadata/">
 * Web Services Addressing 1.0 - Metadata</a>
 * for more information on WS-Addressing.
 *
 * <p>
 *  AddressingFeature表示使用带有SOAP 1.1 / HTTP或SOAP 1.2 / HTTP绑定的WS-Addressing。将此功能与任何其他绑定一起使用是未定义的。
 * <p>
 *  此功能可在创建SEI代理时使用,在客户端使用{@link javax.xml.ws.Dispatch}实例,并在服务器端使用{@link Endpoint}实例。
 * 此功能不能用于在客户端上创建{@link Service}实例。
 * <p>
 *  以下描述了此功能对启用或禁用的影响：
 * <ul>
 *  <li> ENABLED：在此模式下,将启用WS-Addressing。这意味着端点支持WS-Addressing,但不需要它的使用。
 * 发送方可以发送带有WS-Addressing头或没有WS-Addressing头的消息。但是接收者必须消费这两种类型的消息。 <li> DISABLED：在此模式下,将禁用WS-Addressing。
 * 在运行时,WS-Addressing头不能被发送者或接收者使用。
 * </ul>
 * <p>
 *  如果启用该功能,则<code> required </code>属性确定端点是否需要WS-Addressing。如果设置为true,则WS-Addressing头必须存在于传入和传出的消息上。
 * 默认情况下,<code> required </code>属性为<code> false </code>。
 * 
 * <p>
 * 如果Web服务开发人员没有明确启用此功能,WSDL的wsam：Addressing策略断言用于查找WS-Addressing的使用。
 * 通过明确使用该功能,应用程序会覆盖WSDL对使用WS-Addressing的指示。在某些情况下,这是真正需要的。例如,如果应用程序已经实现了WS-Addressing本身,它可以使用此功能禁用寻址。
 * 这意味着JAX-WS实现不会消耗或产生WS-Addressing头。
 * 
 * <p>
 *  如果启用寻址,则必须在WSDL中按照生成对应的wsam：Addressing策略断言
 * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyassertions">
 *  3.1 WS-Policy断言</a>
 * 
 * <p>
 *  <b>示例1：</b> <code> @Addressing </code>的生​​成的WSDL中可能的策略声明
 * <pre>
 * &lt;wsam:Addressing wsp:Optional="true">
 * &lt;wsp:Policy/>
 * &lt;/wsam:Addressing>
 * </pre>
 * 
 * <p>
 *  <b>示例2：</b> <code> @Addressing(required = true)生成的WSDL中可能的策略断言</code>
 * <pre>
 * &lt;wsam:Addressing>
 * &lt;wsp:Policy/>
 * &lt;/wsam:Addressing>
 * </pre>
 * 
 * <p>
 *  <b>示例3：</b> <code> @Addressing(required = true,responses = Responses.ANONYMOUS)生成的WSDL中可能的策略断言</code>
 * 。
 * <pre>
 * &lt;wsam:Addressing>
 * &lt;wsp:Policy>
 * &lt;wsam:AnonymousResponses/>
 * &lt;/wsp:Policy>
 * &lt;/wsam:Addressing>
 * </pre>
 * 
 * <p>
 *  请参见<a href="http://www.w3.org/TR/2006/REC-ws-addr-core-20060509/"> Web服务寻址 - 核心</a>,
 * <a href="http://www.w3.org/TR/2006/REC-ws-addr-soap-20060509/">
 *  Web服务寻址1.0  -  SOAP绑定</a>和<a href="http://www.w3.org/TR/ws-addr-metadata/"> Web服务寻址1.0  - 元数据</a>了解更
 * 多WS-Addressing的信息。
 * 
 * 
 * @see Addressing
 * @since JAX-WS 2.1
 */

public final class AddressingFeature extends WebServiceFeature {
    /**
     * Constant value identifying the AddressingFeature
     * <p>
     *  标识AddressingFeature的常量值
     * 
     */
    public static final String ID = "http://www.w3.org/2005/08/addressing/module";

    /**
     * If addressing is enabled, this property determines whether the endpoint
     * requires WS-Addressing. If required is true, WS-Addressing headers MUST
     * be present on incoming and outgoing messages.
     * <p>
     * 如果启用寻址,此属性确定端点是否需要WS-Addressing。如果需要,则WS-Addressing头必须存在于输入和输出消息上。
     * 
     */
    // should be private final, keeping original modifier due to backwards compatibility
    protected boolean required;

    /**
     * If addressing is enabled, this property determines if endpoint requires
     * the use of only anonymous responses, or only non-anonymous responses, or all.
     *
     * <p>
     * {@link Responses#ALL} supports all response types and this is the default
     * value.
     *
     * <p>
     * {@link Responses#ANONYMOUS} requires the use of only anonymous
     * responses. It will result into wsam:AnonymousResponses nested assertion
     * as specified in
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
     * 3.1.2 AnonymousResponses Assertion</a> in the generated WSDL.
     *
     * <p>
     * {@link Responses#NON_ANONYMOUS} requires the use of only non-anonymous
     * responses. It will result into
     * wsam:NonAnonymousResponses nested assertion as specified in
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicynonanonresponses">
     * 3.1.3 NonAnonymousResponses Assertion</a> in the generated WSDL.
     *
     * <p>
     *  如果启用寻址,此属性确定端点是否需要仅使用匿名响应,还是仅使用非匿名响应,还是全部。
     * 
     * <p>
     *  {@link Responses#ALL}支持所有响应类型,这是默认值。
     * 
     * <p>
     *  {@link回应#ANONYMOUS}需要使用匿名回应。它将导致wsam：AnonymousResponses在中指定的嵌套断言
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
     *  3.1.2生成的WSDL中的AnonymousResponses Assertion </a>。
     * 
     * <p>
     *  {@link回应#NON_ANONYMOUS}只需要使用非匿名回应。它将导致wsam：NonAnonymousResponses在中指定的嵌套断言
     * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicynonanonresponses">
     *  3.1.3在生成的WSDL中的NonAnonymousResponses Assertion </a>。
     * 
     * 
     * @since JAX-WS 2.2
     */
    public enum Responses {
        /**
         * Specifies the use of only anonymous
         * responses. It will result into wsam:AnonymousResponses nested assertion
         * as specified in
         * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
         * 3.1.2 AnonymousResponses Assertion</a> in the generated WSDL.
         * <p>
         *  指定仅使用匿名响应。它将导致wsam：AnonymousResponses在中指定的嵌套断言
         * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicyanonresponses">
         *  3.1.2生成的WSDL中的AnonymousResponses Assertion </a>。
         * 
         */
        ANONYMOUS,

        /**
         * Specifies the use of only non-anonymous
         * responses. It will result into
         * wsam:NonAnonymousResponses nested assertion as specified in
         * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicynonanonresponses">
         * 3.1.3 NonAnonymousResponses Assertion</a> in the generated WSDL.
         * <p>
         *  指定仅使用非匿名响应。它将导致wsam：NonAnonymousResponses在中指定的嵌套断言
         * <a href="http://www.w3.org/TR/ws-addr-metadata/#wspolicynonanonresponses">
         *  3.1.3在生成的WSDL中的NonAnonymousResponses Assertion </a>。
         * 
         */
        NON_ANONYMOUS,

        /**
         * Supports all response types and this is the default
         * <p>
         *  支持所有响应类型,这是默认值
         * 
         */
        ALL
    }

    private final Responses responses;

    /**
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. The created feature enables
     * ws-addressing i.e. supports ws-addressing but doesn't require
     * its use. It is also configured to accept all the response types.
     * <p>
     * 使用寻址要求创建和配置<code> AddressingFeature </code>。创建的功能启用ws寻址,即支持ws寻址,但不需要它的使用。它还配置为接受所有响应类型。
     * 
     */
    public AddressingFeature() {
        this(true, false, Responses.ALL);
    }

    /**
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. If <code>enabled</code> is true,
     * it enables ws-addressing i.e. supports ws-addressing but doesn't
     * require its use. It also configures to accept all the response types.
     *
     * <p>
     *  使用寻址要求创建和配置<code> AddressingFeature </code>。如果<code> enabled </code>为true,它启用ws寻址,即支持ws寻址,但不需要它的使用。
     * 它还配置为接受所有响应类型。
     * 
     * 
     * @param enabled true enables ws-addressing i.e.ws-addressing
     * is supported but doesn't require its use
     */
    public AddressingFeature(boolean enabled) {
        this(enabled, false, Responses.ALL);
    }

    /**
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. If <code>enabled</code> and
     * <code>required</code> are true, it enables ws-addressing and
     * requires its use. It also configures to accept all the response types.
     *
     * <p>
     *  使用寻址要求创建和配置<code> AddressingFeature </code>。
     * 如果<code> enabled </code>和<code> required </code>为true,它将启用ws寻址并需要使用它。它还配置为接受所有响应类型。
     * 
     * 
     * @param enabled true enables ws-addressing i.e.ws-addressing
     * is supported but doesn't require its use
     * @param required true means requires the use of ws-addressing .
     */
    public AddressingFeature(boolean enabled, boolean required) {
        this(enabled, required, Responses.ALL);
    }

    /**
     * Creates and configures an <code>AddressingFeature</code> with the
     * use of addressing requirements. If <code>enabled</code> and
     * <code>required</code> are true, it enables ws-addressing and
     * requires its use. Also, the response types can be configured using
     * <code>responses</code> parameter.
     *
     * <p>
     *  使用寻址要求创建和配置<code> AddressingFeature </code>。
     * 如果<code> enabled </code>和<code> required </code>为true,它将启用ws寻址并需要使用它。
     * 此外,可以使用<code> responses </code>参数配置响应类型。
     * 
     * 
     * @param enabled true enables ws-addressing i.e.ws-addressing
     * is supported but doesn't require its use
     * @param required true means requires the use of ws-addressing .
     * @param responses specifies what type of responses are required
     *
     * @since JAX-WS 2.2
     */
    public AddressingFeature(boolean enabled, boolean required, Responses responses) {
        this.enabled = enabled;
        this.required = required;
        this.responses = responses;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public String getID() {
        return ID;
    }

    /**
     * If addressing is enabled, this property determines whether the endpoint
     * requires WS-Addressing. If required is true, WS-Addressing headers MUST
     * be present on incoming and outgoing messages.
     *
     * <p>
     *  如果启用寻址,此属性确定端点是否需要WS-Addressing。如果需要,则WS-Addressing头必须存在于输入和输出消息上。
     * 
     * 
     * @return the current required value
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * If addressing is enabled, this property determines whether endpoint
     * requires the use of anonymous responses, or non-anonymous responses,
     * or all responses.
     *
     * <p>
     * <p>
     *  如果启用寻址,此属性确定端点是否需要使用匿名响应,非匿名响应或所有响应。
     * 
     * 
     * @return {@link Responses#ALL} when endpoint supports all types of
     * responses,
     *         {@link Responses#ANONYMOUS} when endpoint requires the use of
     * only anonymous responses,
     *         {@link Responses#NON_ANONYMOUS} when endpoint requires the use
     * of only non-anonymous responses
     *
     * @since JAX-WS 2.2
     */
    public Responses getResponses() {
        return responses;
    }

}
