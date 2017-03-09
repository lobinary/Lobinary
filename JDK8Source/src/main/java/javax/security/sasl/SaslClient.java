/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Performs SASL authentication as a client.
 *<p>
 * A protocol library such as one for LDAP gets an instance of this
 * class in order to perform authentication defined by a specific SASL
 * mechanism. Invoking methods on the {@code SaslClient} instance
 * process challenges and create responses according to the SASL
 * mechanism implemented by the {@code SaslClient}.
 * As the authentication proceeds, the instance
 * encapsulates the state of a SASL client's authentication exchange.
 *<p>
 * Here's an example of how an LDAP library might use a {@code SaslClient}.
 * It first gets an instance of a {@code SaslClient}:
 *<blockquote><pre>{@code
 * SaslClient sc = Sasl.createSaslClient(mechanisms,
 *     authorizationId, protocol, serverName, props, callbackHandler);
 *}</pre></blockquote>
 * It can then proceed to use the client for authentication.
 * For example, an LDAP library might use the client as follows:
 *<blockquote><pre>{@code
 * // Get initial response and send to server
 * byte[] response = (sc.hasInitialResponse() ? sc.evaluateChallenge(new byte[0]) :
 *     null);
 * LdapResult res = ldap.sendBindRequest(dn, sc.getName(), response);
 * while (!sc.isComplete() &&
 *     (res.status == SASL_BIND_IN_PROGRESS || res.status == SUCCESS)) {
 *     response = sc.evaluateChallenge(res.getBytes());
 *     if (res.status == SUCCESS) {
 *         // we're done; don't expect to send another BIND
 *         if (response != null) {
 *             throw new SaslException(
 *                 "Protocol error: attempting to send response after completion");
 *         }
 *         break;
 *     }
 *     res = ldap.sendBindRequest(dn, sc.getName(), response);
 * }
 * if (sc.isComplete() && res.status == SUCCESS) {
 *    String qop = (String) sc.getNegotiatedProperty(Sasl.QOP);
 *    if (qop != null
 *        && (qop.equalsIgnoreCase("auth-int")
 *            || qop.equalsIgnoreCase("auth-conf"))) {
 *
 *      // Use SaslClient.wrap() and SaslClient.unwrap() for future
 *      // communication with server
 *      ldap.in = new SecureInputStream(sc, ldap.in);
 *      ldap.out = new SecureOutputStream(sc, ldap.out);
 *    }
 * }
 *}</pre></blockquote>
 *
 * If the mechanism has an initial response, the library invokes
 * {@code evaluateChallenge()} with an empty
 * challenge and to get initial response.
 * Protocols such as IMAP4, which do not include an initial response with
 * their first authentication command to the server, initiates the
 * authentication without first calling {@code hasInitialResponse()}
 * or {@code evaluateChallenge()}.
 * When the server responds to the command, it sends an initial challenge.
 * For a SASL mechanism in which the client sends data first, the server should
 * have issued a challenge with no data. This will then result in a call
 * (on the client) to {@code evaluateChallenge()} with an empty challenge.
 *
 * <p>
 *  作为客户端执行SASL认证。
 * p>
 *  协议库(例如用于LDAP的协议库)获取此类的实例,以便执行由特定SASL机制定义的认证。
 * 对{@code SaslClient}实例进程调用方法挑战并根据{@code SaslClient}实现的SASL机制创建响应。随着认证的进行,实例封装了SASL客户端的认证交换的状态。
 * p>
 * 下面是一个LDAP库如何使用{@code SaslClient}的示例。
 * 它首先获取{@code SaslClient}：blockquote> <pre>的实例{@ code SaslClient sc = Sasl.createSaslClient(mechanisms,authorizationId,protocol,serverName,props,callbackHandler); </pre> </blockquote>然后可以继续使用客户端进行身份验证。
 * 下面是一个LDAP库如何使用{@code SaslClient}的示例。
 * 例如,LDAP库可能使用客户端如下：blockquote> <pre> {@ code //获取初始响应并发送到服务器byte [] response =(sc.hasInitialResponse()?sc.evaluateChallenge(new byte [0 LdapResult res = ldap.sendBindRequest(dn,sc.getName(),response); while(！sc.isComplete()&&(res.status == SASL_BIND_IN_PROGRESS || res.status == SUCCESS)){response = sc.evaluateChallenge(res.getBytes()); if(res.status == SUCCESS){//我们完成了;不要期望发送另一个BIND if(response！= null){throw new SaslException("Protocol error：trying to send response after completion"); }
 *  break; } res = ldap.sendBindRequest(dn,sc.getName(),response); } if(sc.isComplete()&& res.status == 
 * SUCCESS){String qop =(String)sc.getNegotiatedProperty(Sasl.QOP); if(qop！= null &&(qop.equalsIgnoreCase("auth-int")|| qop.equalsIgnoreCase("auth-conf"))){。
 * 下面是一个LDAP库如何使用{@code SaslClient}的示例。
 * 
 *  //使用SaslClient.wrap()和SaslClient.unwrap()以便将来与服务器通信ldap.in = new SecureInputStream(sc,ldap.in); ldap
 * .out = new SecureOutputStream(sc,ldap.out); }} </pre> </blockquote>。
 * 
 * 如果机制具有初始响应,则库使用空挑战调用{@code evaluateChallenge()}并获得初始响应。
 * 诸如IMAP4的协议,其不包括具有对服务器的第一认证命令的初始响应,在不首先调用{@code hasInitialResponse()}或{@code evaluateChallenge()}的情况下启
 * 动认证。
 * 如果机制具有初始响应,则库使用空挑战调用{@code evaluateChallenge()}并获得初始响应。当服务器响应该命令时,它发送初始质询。
 * 对于客户端首先发送数据的SASL机制,服务器应该发出没有数据的挑战。然后,这将导致一个调用(在客户端){@code evaluateChallenge()}与一个空的挑战。
 * 
 * 
 * @since 1.5
 *
 * @see Sasl
 * @see SaslClientFactory
 *
 * @author Rosanna Lee
 * @author Rob Weltman
 */
public abstract interface SaslClient {

    /**
     * Returns the IANA-registered mechanism name of this SASL client.
     * (e.g. "CRAM-MD5", "GSSAPI").
     * <p>
     *  返回此SASL客户端的IANA注册的机制名称。 (例如"CRAM-MD5","GSSAPI")。
     * 
     * 
     * @return A non-null string representing the IANA-registered mechanism name.
     */
    public abstract String getMechanismName();

    /**
     * Determines whether this mechanism has an optional initial response.
     * If true, caller should call {@code evaluateChallenge()} with an
     * empty array to get the initial response.
     *
     * <p>
     *  确定此机制是否具有可选的初始响应。如果为true,调用者应该使用空数组调用{@code evaluateChallenge()}以获取初始响应。
     * 
     * 
     * @return true if this mechanism has an initial response.
     */
    public abstract boolean hasInitialResponse();

    /**
     * Evaluates the challenge data and generates a response.
     * If a challenge is received from the server during the authentication
     * process, this method is called to prepare an appropriate next
     * response to submit to the server.
     *
     * <p>
     *  评估挑战数据并生成响应。如果在认证过程期间从服务器接收到质询,则调用该方法以准备适当的下一响应以提交给服务器。
     * 
     * 
     * @param challenge The non-null challenge sent from the server.
     * The challenge array may have zero length.
     *
     * @return The possibly null response to send to the server.
     * It is null if the challenge accompanied a "SUCCESS" status and the challenge
     * only contains data for the client to update its state and no response
     * needs to be sent to the server. The response is a zero-length byte
     * array if the client is to send a response with no data.
     * @exception SaslException If an error occurred while processing
     * the challenge or generating a response.
     */
    public abstract byte[] evaluateChallenge(byte[] challenge)
        throws SaslException;

    /**
      * Determines whether the authentication exchange has completed.
      * This method may be called at any time, but typically, it
      * will not be called until the caller has received indication
      * from the server
      * (in a protocol-specific manner) that the exchange has completed.
      *
      * <p>
      *  确定身份验证交换是否已完成。该方法可以在任何时间被调用,但是通常,直到呼叫者已经从服务器(以协议特定的方式)接收到交换已经完成的指示为止才会被调用。
      * 
      * 
      * @return true if the authentication exchange has completed; false otherwise.
      */
    public abstract boolean isComplete();

    /**
     * Unwraps a byte array received from the server.
     * This method can be called only after the authentication exchange has
     * completed (i.e., when {@code isComplete()} returns true) and only if
     * the authentication exchange has negotiated integrity and/or privacy
     * as the quality of protection; otherwise, an
     * {@code IllegalStateException} is thrown.
     *<p>
     * {@code incoming} is the contents of the SASL buffer as defined in RFC 2222
     * without the leading four octet field that represents the length.
     * {@code offset} and {@code len} specify the portion of {@code incoming}
     * to use.
     *
     * <p>
     * 打开从服务器接收的字节数组。
     * 仅当认证交换完成后(即,当{@code isComplete()}返回true)且仅当认证交换已经协商完整性和/或隐私作为保护质量时才可以调用此方法;否则,将抛出{@code IllegalStateException}
     * 。
     * 打开从服务器接收的字节数组。
     * p>
     *  {@code incoming}是RFC 2222中定义的SASL缓冲区的内容,而不包含表示长度的前四个八位字节字段。
     *  {@code offset}和{@code len}指定要使用的{@code incoming}部分。
     * 
     * 
     * @param incoming A non-null byte array containing the encoded bytes
     *                from the server.
     * @param offset The starting position at {@code incoming} of the bytes to use.
     * @param len The number of bytes from {@code incoming} to use.
     * @return A non-null byte array containing the decoded bytes.
     * @exception SaslException if {@code incoming} cannot be successfully
     * unwrapped.
     * @exception IllegalStateException if the authentication exchange has
     * not completed, or  if the negotiated quality of protection
     * has neither integrity nor privacy.
     */
    public abstract byte[] unwrap(byte[] incoming, int offset, int len)
        throws SaslException;

    /**
     * Wraps a byte array to be sent to the server.
     * This method can be called only after the authentication exchange has
     * completed (i.e., when {@code isComplete()} returns true) and only if
     * the authentication exchange has negotiated integrity and/or privacy
     * as the quality of protection; otherwise, an
     * {@code IllegalStateException} is thrown.
     *<p>
     * The result of this method will make up the contents of the SASL buffer
     * as defined in RFC 2222 without the leading four octet field that
     * represents the length.
     * {@code offset} and {@code len} specify the portion of {@code outgoing}
     * to use.
     *
     * <p>
     *  包装要发送到服务器的字节数组。
     * 仅当认证交换完成后(即,当{@code isComplete()}返回true)且仅当认证交换已经协商完整性和/或隐私作为保护质量时才可以调用此方法;否则,将抛出{@code IllegalStateException}
     * 。
     *  包装要发送到服务器的字节数组。
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
     * not completed, or if the negotiated quality of protection
     * has neither integrity nor privacy.
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
     * @param propName The non-null property name.
     * @return The value of the negotiated property. If null, the property was
     * not negotiated or is not applicable to this mechanism.
     * @exception IllegalStateException if this authentication exchange
     * has not completed
     */

    public abstract Object getNegotiatedProperty(String propName);

     /**
      * Disposes of any system resources or security-sensitive information
      * the SaslClient might be using. Invoking this method invalidates
      * the SaslClient instance. This method is idempotent.
      * <p>
      *  检索协商属性。此方法只能在认证交换完成后调用(即{@code isComplete()}返回true);否则,将抛出{@code IllegalStateException}。
      * 
      * 
      * @throws SaslException If a problem was encountered while disposing
      * the resources.
      */
    public abstract void dispose() throws SaslException;
}
