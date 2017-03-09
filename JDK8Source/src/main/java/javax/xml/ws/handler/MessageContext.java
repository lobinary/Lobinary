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

package javax.xml.ws.handler;
import java.util.Map;

/**
 * The interface <code>MessageContext</code> abstracts the message
 * context that is processed by a handler in the <code>handle</code>
 * method.
 *
 * <p>The <code>MessageContext</code> interface provides methods to
 * manage a property set. <code>MessageContext</code> properties
 * enable handlers in a handler chain to share processing related
 * state.
 *
 * <p>
 *  接口<code> MessageContext </code>抽象由<code> handle </code>方法中的处理程序处理的消息上下文。
 * 
 *  <p> <code> MessageContext </code>接口提供了管理属性集的方法。
 *  <code> MessageContext </code>属性使处理程序链中的处理程序能够共享处理相关的状态。
 * 
 * 
 * @since JAX-WS 2.0
 */
public interface MessageContext extends Map<String, Object> {

    /**
     * Standard property: message direction, <code>true</code> for
     * outbound messages, <code>false</code> for inbound.
     * <p>Type: boolean
     * <p>
     *  标准属性：消息方向,出站邮件的<code> true </code>,入站的<code> false </code>。 <p>类型：布尔值
     * 
     */
    public static final String MESSAGE_OUTBOUND_PROPERTY =
            "javax.xml.ws.handler.message.outbound";

    /**
     * Standard property: Map of attachments to a message for the inbound
     * message, key is  the MIME Content-ID, value is a DataHandler.
     * <p>Type: java.util.Map&lt;String,DataHandler>
     * <p>
     *  标准属性：映射附件到消息的入站消息,key是MIME Con​​tent-ID,值是一个DataHandler。
     * <p>Type: java.util.Map&lt;String,DataHandler>
     */
    public static final String INBOUND_MESSAGE_ATTACHMENTS =
            "javax.xml.ws.binding.attachments.inbound";

    /**
     * Standard property: Map of attachments to a message for the outbound
     * message, key is the MIME Content-ID, value is a DataHandler.
     * <p>Type: java.util.Map&lt;String,DataHandler>
     * <p>
     *  标准属性：邮件的附件到出站邮件的地图,key是MIME Con​​tent-ID,值是一个DataHandler。
     * <p>Type: java.util.Map&lt;String,DataHandler>
     */
    public static final String OUTBOUND_MESSAGE_ATTACHMENTS =
            "javax.xml.ws.binding.attachments.outbound";

    /**
     * Standard property: input source for WSDL document.
     * <p>Type: org.xml.sax.InputSource
     * <p>
     *  标准属性：WSDL文档的输入源。 <p>键入：org.xml.sax.InputSource
     * 
     */
    public static final String WSDL_DESCRIPTION =
            "javax.xml.ws.wsdl.description";

    /**
     * Standard property: name of WSDL service.
     * <p>Type: javax.xml.namespace.QName
     * <p>
     *  标准属性：WSDL服务的名称。 <p>类型：javax.xml.namespace.QName
     * 
     */
    public static final String WSDL_SERVICE =
            "javax.xml.ws.wsdl.service";

    /**
     * Standard property: name of WSDL port.
     * <p>Type: javax.xml.namespace.QName
     * <p>
     *  标准属性：WSDL端口的名称。 <p>类型：javax.xml.namespace.QName
     * 
     */
    public static final String WSDL_PORT =
            "javax.xml.ws.wsdl.port";

    /**
     * Standard property: name of wsdl interface (2.0) or port type (1.1).
     * <p>Type: javax.xml.namespace.QName
     * <p>
     *  标准属性：wsdl接口(2.0)的名称或端口类型(1.1)。 <p>类型：javax.xml.namespace.QName
     * 
     */
    public static final String WSDL_INTERFACE =
            "javax.xml.ws.wsdl.interface";

    /**
     * Standard property: name of WSDL operation.
     * <p>Type: javax.xml.namespace.QName
     * <p>
     *  标准属性：WSDL操作的名称。 <p>类型：javax.xml.namespace.QName
     * 
     */
    public static final String WSDL_OPERATION =
            "javax.xml.ws.wsdl.operation";

    /**
     * Standard property: HTTP response status code.
     * <p>Type: java.lang.Integer
     * <p>
     *  标准属性：HTTP响应状态代码。 <p> Type：java.lang.Integer
     * 
     */
    public static final String HTTP_RESPONSE_CODE =
            "javax.xml.ws.http.response.code";

    /**
     * Standard property: HTTP request headers.
     * <p>Type: java.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.String>>
     * <p>
     *  标准属性：HTTP请求标头。
     * <p>Type: java.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.String>>
     */
    public static final String HTTP_REQUEST_HEADERS =
            "javax.xml.ws.http.request.headers";

    /**
     * Standard property: HTTP response headers.
     * <p>Type: java.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.String>>
     * <p>
     *  标准属性：HTTP响应标头。
     * <p>Type: java.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.String>>
     */
    public static final String HTTP_RESPONSE_HEADERS =
            "javax.xml.ws.http.response.headers";

    /**
     * Standard property: HTTP request method.
     * <p>Type: java.lang.String
     * <p>
     * 标准属性：HTTP请求方法。 <p>类型：java.lang.String
     * 
     */
    public static final String HTTP_REQUEST_METHOD =
            "javax.xml.ws.http.request.method";

    /**
     * Standard property: servlet request object.
     * <p>Type: javax.servlet.http.HttpServletRequest
     * <p>
     *  标准属性：servlet请求对象。 <p>键入：javax.servlet.http.HttpServletRequest
     * 
     */
    public static final String SERVLET_REQUEST =
            "javax.xml.ws.servlet.request";

    /**
     * Standard property: servlet response object.
     * <p>Type: javax.servlet.http.HttpServletResponse
     * <p>
     *  标准属性：servlet响应对象。 <p>键入：javax.servlet.http.HttpServletResponse
     * 
     */
    public static final String SERVLET_RESPONSE =
            "javax.xml.ws.servlet.response";

    /**
     * Standard property: servlet context object.
     * <p>Type: javax.servlet.ServletContext
     * <p>
     *  标准属性：servlet上下文对象。 <p>类型：javax.servlet.ServletContext
     * 
     */
    public static final String SERVLET_CONTEXT =
            "javax.xml.ws.servlet.context";

    /**
     * Standard property: Query string for request.
     * <p>Type: String
     * <p>
     *  标准属性：请求的查询字符串。 <p>类型：字符串
     * 
     * 
     **/
    public static final String QUERY_STRING =
            "javax.xml.ws.http.request.querystring";

    /**
     * Standard property: Request Path Info
     * <p>Type: String
     * <p>
     *  标准属性：请求路径信息<p>类型：字符串
     * 
     */
    public static final String PATH_INFO =
            "javax.xml.ws.http.request.pathinfo";

    /**
     * Standard property: WS Addressing Reference Parameters.
     * The list MUST include all SOAP headers marked with the
     * wsa:IsReferenceParameter="true" attribute.
     * <p>Type: List&lt;Element>
     *
     * <p>
     *  标准属性：WS寻址参考参数。该列表必须包括标记有wsa：IsReferenceParameter ="true"属性的所有SOAP头。
     * <p>Type: List&lt;Element>
     * 
     * 
     * @since JAX-WS 2.1
     */
    public static final String REFERENCE_PARAMETERS =
            "javax.xml.ws.reference.parameters";

    /**
     * Property scope. Properties scoped as <code>APPLICATION</code> are
     * visible to handlers,
     * client applications and service endpoints; properties scoped as
     * <code>HANDLER</code>
     * are only normally visible to handlers.
     * <p>
     *  属性范围。作为<code> APPLICATION </code>范围的属性对处理程序,客户端应用程序和服务端点可见;作为<code> HANDLER </code>的属性通常只对处理程序可见。
     * 
     */
    public enum Scope {APPLICATION, HANDLER};

    /**
     * Sets the scope of a property.
     *
     * <p>
     *  设置属性的作用域。
     * 
     * 
     * @param name Name of the property associated with the
     *             <code>MessageContext</code>
     * @param scope Desired scope of the property
     * @throws java.lang.IllegalArgumentException if an illegal
     *             property name is specified
     */
    public void setScope(String name,  Scope scope);

    /**
     * Gets the scope of a property.
     *
     * <p>
     *  获取属性的作用域。
     * 
     * @param name Name of the property
     * @return Scope of the property
     * @throws java.lang.IllegalArgumentException if a non-existant
     *             property name is specified
     */
    public Scope getScope(String  name);
}
