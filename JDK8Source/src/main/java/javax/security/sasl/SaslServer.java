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

package javax.security.sasl;

/**
 * Performs SASL authentication as a server.
 *<p>
 * A server such an LDAP server gets an instance of this
 * class in order to perform authentication defined by a specific SASL
 * mechanism. Invoking methods on the {@code SaslServer} instance
 * generates challenges according to the SASL
 * mechanism implemented by the {@code SaslServer}.
 * As the authentication proceeds, the instance
 * encapsulates the state of a SASL server's authentication exchange.
 *<p>
 * Here's an example of how an LDAP server might use a {@code SaslServer}.
 * It first gets an instance of a {@code SaslServer} for the SASL mechanism
 * requested by the client:
 *<blockquote><pre>
 * SaslServer ss = Sasl.createSaslServer(mechanism,
 *     "ldap", myFQDN, props, callbackHandler);
 *</pre></blockquote>
 * It can then proceed to use the server for authentication.
 * For example, suppose the LDAP server received an LDAP BIND request
 * containing the name of the SASL mechanism and an (optional) initial
 * response. It then might use the server as follows:
 *<blockquote><pre>{@code
 * while (!ss.isComplete()) {
 *     try {
 *         byte[] challenge = ss.evaluateResponse(response);
 *         if (ss.isComplete()) {
 *             status = ldap.sendBindResponse(mechanism, challenge, SUCCESS);
 *         } else {
 *             status = ldap.sendBindResponse(mechanism, challenge,
                   SASL_BIND_IN_PROGRESS);
 *             response = ldap.readBindRequest();
 *         }
 *     } catch (SaslException e) {
 *          status = ldap.sendErrorResponse(e);
 *          break;
 *     }
 * }
 * if (ss.isComplete() && status == SUCCESS) {
 *    String qop = (String) sc.getNegotiatedProperty(Sasl.QOP);
 *    if (qop != null
 *        && (qop.equalsIgnoreCase("auth-int")
 *            || qop.equalsIgnoreCase("auth-conf"))) {
 *
 *      // Use SaslServer.wrap() and SaslServer.unwrap() for future
 *      // communication with client
 *      ldap.in = new SecureInputStream(ss, ldap.in);
 *      ldap.out = new SecureOutputStream(ss, ldap.out);
 *    }
 * }
 *}</pre></blockquote>
 *
 * <p>
 *  将SASL认证作为服务器执行。
 * p>
 *  服务器这样的LDAP服务器获得此类的实例,以便执行由特定SASL机制定义的认证。
 * 对{@code SaslServer}实例调用方法会根据{@code SaslServer}实现的SASL机制产生挑战。随着认证的进行,实例封装了SASL服务器的认证交换的状态。
 * p>
 * 以下是LDAP服务器如何使用{@code SaslServer}的示例。
 * 它首先获得客户请求的SASL机制的{@code SaslServer}实例：blockquote> <pre> SaslServer ss = Sasl.createSaslServer(机制,"lda
 * p",myFQDN,props,callbackHandler); / pre> </blockquote>然后可以继续使用服务器进行身份验证。
 * 以下是LDAP服务器如何使用{@code SaslServer}的示例。例如,假设LDAP服务器接收到包含SASL机制的名称和(可选)初始响应的LDAP BIND请求。
 * 然后它可能使用服务器如下：blockquote> <pre> {@ code while(！ss.isComplete()){try {byte [] challenge = ss.evaluateResponse(response); if(ss.isComplete()){status = ldap.sendBindResponse(机制,挑战,SUCCESS); }
 *  else {status = ldap.sendBindResponse(mechanism,challenge,SASL_BIND_IN_PROGRESS); response = ldap.readBindRequest(); }
 * } catch(SaslException e){status = ldap.sendErrorResponse(e);}}打破; }} if(ss.isComplete()&& status == S
 * UCCESS){String qop =(String)sc.getNegotiatedProperty(Sasl.QOP); if(qop！= null &&(qop.equalsIgnoreCase("auth-int")|| qop.equalsIgnoreCase("auth-conf"))){。
 * 以下是LDAP服务器如何使用{@code SaslServer}的示例。例如,假设LDAP服务器接收到包含SASL机制的名称和(可选)初始响应的LDAP BIND请求。
 * 
 *  //使用SaslServer.wrap()和SaslServer.unwrap()以便将来与客户端通信ldap.in = new SecureInputStream(ss,ldap.in); ldap
 * .out = new SecureOutputStream(ss,ldap.out); }} </pre> </blockquote>。
 * 
 * 
 * @since 1.5
 *
 * @see Sasl
 * @see SaslServerFactory
 *
 * @author Rosanna Lee
 * @author Rob Weltman
 */
public abstract interface SaslServer {

    /**
     * Returns the IANA-registered mechanism name of this SASL server.
     * (e.g. "CRAM-MD5", "GSSAPI").
     * <p>
     *  返回此SASL服务器的IANA注册的机制名称。 (例如"CRAM-MD5","GSSAPI")。
     * 
     * 
     * @return A non-null string representing the IANA-registered mechanism name.
     */
    public abstract String getMechanismName();

    /**
     * Evaluates the response data and generates a challenge.
     *
     * If a response is received from the client during the authentication
     * process, this method is called to prepare an appropriate next
     * challenge to submit to the client. The challenge is null if the
     * authentication has succeeded and no more challenge data is to be sent
     * to the client. It is non-null if the authentication must be continued
     * by sending a challenge to the client, or if the authentication has
     * succeeded but challenge data needs to be processed by the client.
     * {@code isComplete()} should be called
     * after each call to {@code evaluateResponse()},to determine if any further
     * response is needed from the client.
     *
     * <p>
     *  评估响应数据并生成质询。
     * 
     * 如果在认证过程期间从客户端接收到响应,则调用该方法以准备适当的下一个挑战以提交给客户端。如果认证成功并且没有更多的挑战数据要发送到客户端,则质询为null。
     * 如果必须通过向客户端发送质询或者如果身份验证成功但客户端需要处理质询数据来继续身份验证,那么它是非空的。
     *  {@code isComplete()}应该在每次调用{@code evaluateResponse()}后调用,以确定是否需要从客户端进一步的响应。
     * 
     * 
     * @param response The non-null (but possibly empty) response sent
     * by the client.
     *
     * @return The possibly null challenge to send to the client.
     * It is null if the authentication has succeeded and there is
     * no more challenge data to be sent to the client.
     * @exception SaslException If an error occurred while processing
     * the response or generating a challenge.
     */
    public abstract byte[] evaluateResponse(byte[] response)
        throws SaslException;

    /**
      * Determines whether the authentication exchange has completed.
      * This method is typically called after each invocation of
      * {@code evaluateResponse()} to determine whether the
      * authentication has completed successfully or should be continued.
      * <p>
      *  确定身份验证交换是否已完成。通常在每次调用{@code evaluateResponse()}之后调用此方法,以确定身份验证是否已成功完成或应继续。
      * 
      * 
      * @return true if the authentication exchange has completed; false otherwise.
      */
    public abstract boolean isComplete();

    /**
     * Reports the authorization ID in effect for the client of this
     * session.
     * This method can only be called if isComplete() returns true.
     * <p>
     *  报告此会话的客户端的有效授权ID。此方法只能在isComplete()返回true的情况下调用。
     * 
     * 
     * @return The authorization ID of the client.
     * @exception IllegalStateException if this authentication session has not completed
     */
    public String getAuthorizationID();

    /**
     * Unwraps a byte array received from the client.
     * This method can be called only after the authentication exchange has
     * completed (i.e., when {@code isComplete()} returns true) and only if
     * the authentication exchange has negotiated integrity and/or privacy
     * as the quality of protection; otherwise,
     * an {@code IllegalStateException} is thrown.
     *<p>
     * {@code incoming} is the contents of the SASL buffer as defined in RFC 2222
     * without the leading four octet field that represents the length.
     * {@code offset} and {@code len} specify the portion of {@code incoming}
     * to use.
     *
     * <p>
     *  展开从客户端接收的字节数组。
     * 仅当认证交换完成后(即,当{@code isComplete()}返回true)且仅当认证交换已经协商完整性和/或隐私作为保护质量时才可以调用此方法;否则,将抛出{@code IllegalStateException}
     * 。
     *  展开从客户端接收的字节数组。
     * p>
     * {@code incoming}是RFC 2222中定义的SASL缓冲区的内容,而不包含表示长度的前四个八位字节字段。
     *  {@code offset}和{@code len}指定要使用的{@code incoming}部分。
     * 
     * 
     * @param incoming A non-null byte array containing the encoded bytes
     *                from the client.
     * @param offset The starting position at {@code incoming} of the bytes to use.
     * @param len The number of bytes from {@code incoming} to use.
     * @return A non-null byte array containing the decoded bytes.
     * @exception SaslException if {@code incoming} cannot be successfully
     * unwrapped.
     * @exception IllegalStateException if the authentication exchange has
     * not completed, or if the negotiated quality of protection
     * has neither integrity nor privacy
     */
    public abstract byte[] unwrap(byte[] incoming, int offset, int len)
        throws SaslException;

    /**
     * Wraps a byte array to be sent to the client.
     * This method can be called only after the authentication exchange has
     * completed (i.e., when {@code isComplete()} returns true) and only if
     * the authentication exchange has negotiated integrity and/or privacy
     * as the quality of protection; otherwise, a {@code SaslException} is thrown.
     *<p>
     * The result of this method
     * will make up the contents of the SASL buffer as defined in RFC 2222
     * without the leading four octet field that represents the length.
     * {@code offset} and {@code len} specify the portion of {@code outgoing}
     * to use.
     *
     * <p>
     *  包装要发送到客户端的字节数组。
     * 仅当认证交换完成后(即,当{@code isComplete()}返回true)且仅当认证交换已经协商完整性和/或隐私作为保护质量时才可以调用此方法;否则,抛出{@code SaslException}
     * 。
     *  包装要发送到客户端的字节数组。
     * p>
     *  此方法的结果将构成RFC 2222中定义的SASL缓冲区的内容,而不包含表示长度的前四个八位字节字段。
     *  {@code offset}和{@code len}指定要使用的{@code outgoing}部分。
     * 
     * @param outgoing A non-null byte array containing the bytes to encode.
     * @param offset The starting position at {@code outgoing} of the bytes to use.
     * @param len The number of bytes from {@code outgoing} to use.
     * @return A non-null byte array containing the encoded bytes.
     * @exception SaslException if {@code outgoing} cannot be successfully
     * wrapped.
     * @exception IllegalStateException if the authentication exchange has
     * not completed, or if the negotiated quality of protection has
     * neither integrity nor privacy.
     */
    public abstract byte[] wrap(byte[] outgoing, int offset, int len)
        throws SaslException;

    /**
     * Retrieves the negotiated property.
     * This method can be called only after the authentication exchange has
     * completed (i.e., when {@code isComplete()} returns true); otherwise, an
     * {@code IllegalStateException} is thrown.
     *
     * <p>
     * 
     * 
     * @param propName the property
     * @return The value of the negotiated property. If null, the property was
     * not negotiated or is not applicable to this mechanism.
     * @exception IllegalStateException if this authentication exchange has not completed
     */

    public abstract Object getNegotiatedProperty(String propName);

     /**
      * Disposes of any system resources or security-sensitive information
      * the SaslServer might be using. Invoking this method invalidates
      * the SaslServer instance. This method is idempotent.
      * <p>
      *  检索协商属性。此方法只能在认证交换完成后调用(即{@code isComplete()}返回true);否则,将抛出{@code IllegalStateException}。
      * 
      * 
      * @throws SaslException If a problem was encountered while disposing
      * the resources.
      */
    public abstract void dispose() throws SaslException;
}
