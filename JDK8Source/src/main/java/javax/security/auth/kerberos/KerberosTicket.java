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

package javax.security.auth.kerberos;

import java.io.*;
import java.util.Date;
import java.util.Arrays;
import java.net.InetAddress;
import javax.crypto.SecretKey;
import javax.security.auth.Refreshable;
import javax.security.auth.Destroyable;
import javax.security.auth.RefreshFailedException;
import javax.security.auth.DestroyFailedException;
import sun.misc.HexDumpEncoder;
import sun.security.krb5.EncryptionKey;
import sun.security.krb5.Asn1Exception;
import sun.security.util.*;

/**
 * This class encapsulates a Kerberos ticket and associated
 * information as viewed from the client's point of view. It captures all
 * information that the Key Distribution Center (KDC) sends to the client
 * in the reply message KDC-REP defined in the Kerberos Protocol
 * Specification (<a href=http://www.ietf.org/rfc/rfc4120.txt>RFC 4120</a>).
 * <p>
 * All Kerberos JAAS login modules that authenticate a user to a KDC should
 * use this class. Where available, the login module might even read this
 * information from a ticket cache in the operating system instead of
 * directly communicating with the KDC. During the commit phase of the JAAS
 * authentication process, the JAAS login module should instantiate this
 * class and store the instance in the private credential set of a
 * {@link javax.security.auth.Subject Subject}.<p>
 *
 * It might be necessary for the application to be granted a
 * {@link javax.security.auth.PrivateCredentialPermission
 * PrivateCredentialPermission} if it needs to access a KerberosTicket
 * instance from a Subject. This permission is not needed when the
 * application depends on the default JGSS Kerberos mechanism to access the
 * KerberosTicket. In that case, however, the application will need an
 * appropriate
 * {@link javax.security.auth.kerberos.ServicePermission ServicePermission}.
 * <p>
 * Note that this class is applicable to both ticket granting tickets and
 * other regular service tickets. A ticket granting ticket is just a
 * special case of a more generalized service ticket.
 *
 * <p>
 *  此类封装了Kerberos票据和从客户端的角度查看的关联信息。
 * 它捕获密钥分发中心(KDC)在Kerberos协议规范中定义的回复消息KDC-REP中发送给客户端的所有信息(<a href=http://www.ietf.org/rfc/rfc4120.txt> R
 * FC 4120 </a>)。
 *  此类封装了Kerberos票据和从客户端的角度查看的关联信息。
 * <p>
 *  所有用于向KDC认证用户的Kerberos JAAS登录模块都应使用此类。如果可用,登录模块甚至可以从操作系统中的票证缓存读取此信息,而不是直接与KDC通信。
 * 在JAAS认证过程的提交阶段,JAAS登录模块应实例化此类并将实例存储在{@link javax.security.auth.Subject Subject}的私有凭证集中。<p>。
 * 
 *  如果需要从主题访问KerberosTicket实例,则可能需要向应用程序授予{@link javax.security.auth.PrivateCredentialPermission PrivateCredentialPermission}
 * 。
 * 当应用程序依赖于默认的JGSS Kerberos机制来访问KerberosTicket时,不需要此权限。
 * 在这种情况下,应用程序将需要一个适当的{@link javax.security.auth.kerberos.ServicePermission ServicePermission}。
 * <p>
 * 请注意,此类别适用于票券授予票和其他常规服务票。票证授予票只是更普遍的服务票的特殊情况。
 * 
 * 
 * @see javax.security.auth.Subject
 * @see javax.security.auth.PrivateCredentialPermission
 * @see javax.security.auth.login.LoginContext
 * @see org.ietf.jgss.GSSCredential
 * @see org.ietf.jgss.GSSManager
 *
 * @author Mayank Upadhyay
 * @since 1.4
 */
public class KerberosTicket implements Destroyable, Refreshable,
         java.io.Serializable {

    private static final long serialVersionUID = 7395334370157380539L;

    // XXX Make these flag indices public
    private static final int FORWARDABLE_TICKET_FLAG = 1;
    private static final int FORWARDED_TICKET_FLAG   = 2;
    private static final int PROXIABLE_TICKET_FLAG   = 3;
    private static final int PROXY_TICKET_FLAG       = 4;
    private static final int POSTDATED_TICKET_FLAG   = 6;
    private static final int RENEWABLE_TICKET_FLAG   = 8;
    private static final int INITIAL_TICKET_FLAG     = 9;

    private static final int NUM_FLAGS = 32;

    /**
     *
     * ASN.1 DER Encoding of the Ticket as defined in the
     * Kerberos Protocol Specification RFC4120.
     *
     * <p>
     *  ASN.1 DER Kerberos协议规范RFC4120中定义的票证的编码。
     * 
     * 
     * @serial
     */

    private byte[] asn1Encoding;

    /**
     *{@code KeyImpl} is serialized by writing out the ASN1 Encoded bytes
     * of the encryption key. The ASN1 encoding is defined in RFC4120 and as
     * follows:
     * <pre>
     * EncryptionKey   ::= SEQUENCE {
     *          keytype    [0] Int32 -- actually encryption type --,
     *          keyvalue   [1] OCTET STRING
     * }
     * </pre>
     *
     * <p>
     *  @code KeyImpl}通过写出加密密钥的ASN1编码字节来序列化。 ASN1编码在RFC4120中定义如下：
     * <pre>
     *  EncryptionKey :: = SEQUENCE {keytype [0] Int32  - 实际上是加密类型 - ,keyvalue [1] OCTET STRING}
     * </pre>
     * 
     * 
     * @serial
     */

    private KeyImpl sessionKey;

    /**
     *
     * Ticket Flags as defined in the Kerberos Protocol Specification RFC4120.
     *
     * <p>
     *  Ticket标志,如Kerberos协议规范RFC4120中定义。
     * 
     * 
     * @serial
     */

    private boolean[] flags;

    /**
     *
     * Time of initial authentication
     *
     * <p>
     *  初始认证时间
     * 
     * 
     * @serial
     */

    private Date authTime;

    /**
     *
     * Time after which the ticket is valid.
     * <p>
     *  票证有效的时间。
     * 
     * 
     * @serial
     */
    private Date startTime;

    /**
     *
     * Time after which the ticket will not be honored. (its expiration time).
     *
     * <p>
     *  时间之后,票将不被兑现。 (其到期时间)。
     * 
     * 
     * @serial
     */

    private Date endTime;

    /**
     *
     * For renewable Tickets it indicates the maximum endtime that may be
     * included in a renewal. It can be thought of as the absolute expiration
     * time for the ticket, including all renewals. This field may be null
     * for tickets that are not renewable.
     *
     * <p>
     *  对于可再生票证,它指示可以包括在续订中的最大结束时间。它可以被认为是票的绝对到期时间,包括所有续订。对于不可再生的票券,此字段可以为空。
     * 
     * 
     * @serial
     */

    private Date renewTill;

    /**
     *
     * Client that owns the service ticket
     *
     * <p>
     *  拥有服务票证的客户端
     * 
     * 
     * @serial
     */

    private KerberosPrincipal client;

    /**
     *
     * The service for which the ticket was issued.
     *
     * <p>
     *  发出票证的服务。
     * 
     * 
     * @serial
     */

    private KerberosPrincipal server;

    /**
     *
     * The addresses from where the ticket may be used by the client.
     * This field may be null when the ticket is usable from any address.
     *
     * <p>
     *  客户机可以使用故障单的地址。当票证可以从任何地址使用时,此字段可以为空。
     * 
     * 
     * @serial
     */


    private InetAddress[] clientAddresses;

    private transient boolean destroyed = false;

    /**
     * Constructs a KerberosTicket using credentials information that a
     * client either receives from a KDC or reads from a cache.
     *
     * <p>
     *  使用客户端从KDC接收或从缓存读取的凭据信息构造KerberosTicket。
     * 
     * 
     * @param asn1Encoding the ASN.1 encoding of the ticket as defined by
     * the Kerberos protocol specification.
     * @param client the client that owns this service
     * ticket
     * @param server the service that this ticket is for
     * @param sessionKey the raw bytes for the session key that must be
     * used to encrypt the authenticator that will be sent to the server
     * @param keyType the key type for the session key as defined by the
     * Kerberos protocol specification.
     * @param flags the ticket flags. Each element in this array indicates
     * the value for the corresponding bit in the ASN.1 BitString that
     * represents the ticket flags. If the number of elements in this array
     * is less than the number of flags used by the Kerberos protocol,
     * then the missing flags will be filled in with false.
     * @param authTime the time of initial authentication for the client
     * @param startTime the time after which the ticket will be valid. This
     * may be null in which case the value of authTime is treated as the
     * startTime.
     * @param endTime the time after which the ticket will no longer be
     * valid
     * @param renewTill an absolute expiration time for the ticket,
     * including all renewal that might be possible. This field may be null
     * for tickets that are not renewable.
     * @param clientAddresses the addresses from where the ticket may be
     * used by the client. This field may be null when the ticket is usable
     * from any address.
     */
    public KerberosTicket(byte[] asn1Encoding,
                         KerberosPrincipal client,
                         KerberosPrincipal server,
                         byte[] sessionKey,
                         int keyType,
                         boolean[] flags,
                         Date authTime,
                         Date startTime,
                         Date endTime,
                         Date renewTill,
                         InetAddress[] clientAddresses) {

        init(asn1Encoding, client, server, sessionKey, keyType, flags,
            authTime, startTime, endTime, renewTill, clientAddresses);
    }

    private void init(byte[] asn1Encoding,
                         KerberosPrincipal client,
                         KerberosPrincipal server,
                         byte[] sessionKey,
                         int keyType,
                         boolean[] flags,
                         Date authTime,
                         Date startTime,
                         Date endTime,
                         Date renewTill,
                         InetAddress[] clientAddresses) {
        if (sessionKey == null)
           throw new IllegalArgumentException("Session key for ticket"
                                              + " cannot be null");
        init(asn1Encoding, client, server,
             new KeyImpl(sessionKey, keyType), flags, authTime,
             startTime, endTime, renewTill, clientAddresses);
    }

    private void init(byte[] asn1Encoding,
                         KerberosPrincipal client,
                         KerberosPrincipal server,
                         KeyImpl sessionKey,
                         boolean[] flags,
                         Date authTime,
                         Date startTime,
                         Date endTime,
                         Date renewTill,
                         InetAddress[] clientAddresses) {
        if (asn1Encoding == null)
           throw new IllegalArgumentException("ASN.1 encoding of ticket"
                                              + " cannot be null");
        this.asn1Encoding = asn1Encoding.clone();

        if (client == null)
           throw new IllegalArgumentException("Client name in ticket"
                                              + " cannot be null");
        this.client = client;

        if (server == null)
           throw new IllegalArgumentException("Server name in ticket"
                                              + " cannot be null");
        this.server = server;

        // Caller needs to make sure `sessionKey` will not be null
        this.sessionKey = sessionKey;

        if (flags != null) {
           if (flags.length >= NUM_FLAGS)
                this.flags = flags.clone();
           else {
                this.flags = new boolean[NUM_FLAGS];
                // Fill in whatever we have
                for (int i = 0; i < flags.length; i++)
                    this.flags[i] = flags[i];
           }
        } else
           this.flags = new boolean[NUM_FLAGS];

        if (this.flags[RENEWABLE_TICKET_FLAG]) {
           if (renewTill == null)
                throw new IllegalArgumentException("The renewable period "
                       + "end time cannot be null for renewable tickets.");

           this.renewTill = new Date(renewTill.getTime());
        }

        if (authTime != null) {
            this.authTime = new Date(authTime.getTime());
        }
        if (startTime != null) {
            this.startTime = new Date(startTime.getTime());
        } else {
            this.startTime = this.authTime;
        }

        if (endTime == null)
           throw new IllegalArgumentException("End time for ticket validity"
                                              + " cannot be null");
        this.endTime = new Date(endTime.getTime());

        if (clientAddresses != null)
           this.clientAddresses = clientAddresses.clone();
    }

    /**
     * Returns the client principal associated with this ticket.
     *
     * <p>
     *  返回与此故障单关联的客户主体。
     * 
     * 
     * @return the client principal.
     */
    public final KerberosPrincipal getClient() {
        return client;
    }

    /**
     * Returns the service principal associated with this ticket.
     *
     * <p>
     *  返回与此故障单关联的服务主体。
     * 
     * 
     * @return the service principal.
     */
    public final KerberosPrincipal getServer() {
        return server;
    }

    /**
     * Returns the session key associated with this ticket.
     *
     * <p>
     * 返回与此故障单关联的会话密钥。
     * 
     * 
     * @return the session key.
     */
    public final SecretKey getSessionKey() {
        if (destroyed)
            throw new IllegalStateException("This ticket is no longer valid");
        return sessionKey;
    }

    /**
     * Returns the key type of the session key associated with this
     * ticket as defined by the Kerberos Protocol Specification.
     *
     * <p>
     *  返回与此故障单相关联的会话密钥的密钥类型,如Kerberos协议规范所定义。
     * 
     * 
     * @return the key type of the session key associated with this
     * ticket.
     *
     * @see #getSessionKey()
     */
    public final int getSessionKeyType() {
        if (destroyed)
            throw new IllegalStateException("This ticket is no longer valid");
        return sessionKey.getKeyType();
    }

    /**
     * Determines if this ticket is forwardable.
     *
     * <p>
     *  确定此支持消息是否可转发。
     * 
     * 
     * @return true if this ticket is forwardable, false if not.
     */
    public final boolean isForwardable() {
        return flags[FORWARDABLE_TICKET_FLAG];
    }

    /**
     * Determines if this ticket had been forwarded or was issued based on
     * authentication involving a forwarded ticket-granting ticket.
     *
     * <p>
     *  确定此票据是否已根据包含转发的票证授予票证的身份验证转发或发出。
     * 
     * 
     * @return true if this ticket had been forwarded or was issued based on
     * authentication involving a forwarded ticket-granting ticket,
     * false otherwise.
     */
    public final boolean isForwarded() {
        return flags[FORWARDED_TICKET_FLAG];
    }

    /**
     * Determines if this ticket is proxiable.
     *
     * <p>
     *  确定此票证是否可行。
     * 
     * 
     * @return true if this ticket is proxiable, false if not.
     */
    public final boolean isProxiable() {
        return flags[PROXIABLE_TICKET_FLAG];
    }

    /**
     * Determines is this ticket is a proxy-ticket.
     *
     * <p>
     *  确定这张票是一张代理票。
     * 
     * 
     * @return true if this ticket is a proxy-ticket, false if not.
     */
    public final boolean isProxy() {
        return flags[PROXY_TICKET_FLAG];
    }


    /**
     * Determines is this ticket is post-dated.
     *
     * <p>
     *  确定这张票是过时的。
     * 
     * 
     * @return true if this ticket is post-dated, false if not.
     */
    public final boolean isPostdated() {
        return flags[POSTDATED_TICKET_FLAG];
    }

    /**
     * Determines is this ticket is renewable. If so, the {@link #refresh()
     * refresh} method can be called, assuming the validity period for
     * renewing is not already over.
     *
     * <p>
     *  确定这张票是可续订的。如果是这样,则可以调用{@link #refresh()refresh}方法,假定更新的有效期尚未结束。
     * 
     * 
     * @return true if this ticket is renewable, false if not.
     */
    public final boolean isRenewable() {
        return flags[RENEWABLE_TICKET_FLAG];
    }

    /**
     * Determines if this ticket was issued using the Kerberos AS-Exchange
     * protocol, and not issued based on some ticket-granting ticket.
     *
     * <p>
     *  确定此故障单是使用Kerberos AS-Exchange协议发出的,而不是基于某些故障单授予故障单发出的。
     * 
     * 
     * @return true if this ticket was issued using the Kerberos AS-Exchange
     * protocol, false if not.
     */
    public final boolean isInitial() {
        return flags[INITIAL_TICKET_FLAG];
    }

    /**
     * Returns the flags associated with this ticket. Each element in the
     * returned array indicates the value for the corresponding bit in the
     * ASN.1 BitString that represents the ticket flags.
     *
     * <p>
     *  返回与此故障单关联的标志。返回数组中的每个元素表示表示票证标志的ASN.1 BitString中对应位的值。
     * 
     * 
     * @return the flags associated with this ticket.
     */
    public final boolean[]  getFlags() {
        return (flags == null? null: flags.clone());
    }

    /**
     * Returns the time that the client was authenticated.
     *
     * <p>
     *  返回客户端通过身份验证的时间。
     * 
     * 
     * @return the time that the client was authenticated
     *         or null if not set.
     */
    public final java.util.Date getAuthTime() {
        return (authTime == null) ? null : (Date)authTime.clone();
    }

    /**
     * Returns the start time for this ticket's validity period.
     *
     * <p>
     *  返回此票证有效期的开始时间。
     * 
     * 
     * @return the start time for this ticket's validity period
     *         or null if not set.
     */
    public final java.util.Date getStartTime() {
        return (startTime == null) ? null : (Date)startTime.clone();
    }

    /**
     * Returns the expiration time for this ticket's validity period.
     *
     * <p>
     *  返回此故障单的有效期的到期时间。
     * 
     * 
     * @return the expiration time for this ticket's validity period.
     */
    public final java.util.Date getEndTime() {
        return (Date) endTime.clone();
    }

    /**
     * Returns the latest expiration time for this ticket, including all
     * renewals. This will return a null value for non-renewable tickets.
     *
     * <p>
     *  返回此故障单的最近到期时间,包括所有续订。这将为不可再生票券返回一个空值。
     * 
     * 
     * @return the latest expiration time for this ticket.
     */
    public final java.util.Date getRenewTill() {
        return (renewTill == null) ? null: (Date)renewTill.clone();
    }

    /**
     * Returns a list of addresses from where the ticket can be used.
     *
     * <p>
     *  返回可以使用故障单的地址列表。
     * 
     * 
     * @return ths list of addresses or null, if the field was not
     * provided.
     */
    public final java.net.InetAddress[] getClientAddresses() {
        return (clientAddresses == null) ? null: clientAddresses.clone();
    }

    /**
     * Returns an ASN.1 encoding of the entire ticket.
     *
     * <p>
     *  返回整个票证的ASN.1编码。
     * 
     * 
     * @return an ASN.1 encoding of the entire ticket.
     */
    public final byte[] getEncoded() {
        if (destroyed)
            throw new IllegalStateException("This ticket is no longer valid");
        return asn1Encoding.clone();
    }

    /** Determines if this ticket is still current.  */
    public boolean isCurrent() {
        return (System.currentTimeMillis() <= getEndTime().getTime());
    }

    /**
     * Extends the validity period of this ticket. The ticket will contain
     * a new session key if the refresh operation succeeds. The refresh
     * operation will fail if the ticket is not renewable or the latest
     * allowable renew time has passed. Any other error returned by the
     * KDC will also cause this method to fail.
     *
     * Note: This method is not synchronized with the the accessor
     * methods of this object. Hence callers need to be aware of multiple
     * threads that might access this and try to renew it at the same
     * time.
     *
     * <p>
     * 延长此票的有效期。如果刷新操作成功,则票证将包含新的会话密钥。如果故障单不可更新或最近允许的更新时间已过,则刷新操作将失败。 KDC返回的任何其他错误也将导致此方法失败。
     * 
     *  注意：此方法不与此对象的访问器方法同步。因此,呼叫者需要知道可能访问此并尝试在同一时间更新它的多个线程。
     * 
     * 
     * @throws RefreshFailedException if the ticket is not renewable, or
     * the latest allowable renew time has passed, or the KDC returns some
     * error.
     *
     * @see #isRenewable()
     * @see #getRenewTill()
     */
    public void refresh() throws RefreshFailedException {

        if (destroyed)
            throw new RefreshFailedException("A destroyed ticket "
                                             + "cannot be renewd.");

        if (!isRenewable())
            throw new RefreshFailedException("This ticket is not renewable");

        if (System.currentTimeMillis() > getRenewTill().getTime())
            throw new RefreshFailedException("This ticket is past "
                                             + "its last renewal time.");
        Throwable e = null;
        sun.security.krb5.Credentials krb5Creds = null;

        try {
            krb5Creds = new sun.security.krb5.Credentials(asn1Encoding,
                                                    client.toString(),
                                                    server.toString(),
                                                    sessionKey.getEncoded(),
                                                    sessionKey.getKeyType(),
                                                    flags,
                                                    authTime,
                                                    startTime,
                                                    endTime,
                                                    renewTill,
                                                    clientAddresses);
            krb5Creds = krb5Creds.renew();
        } catch (sun.security.krb5.KrbException krbException) {
            e = krbException;
        } catch (java.io.IOException ioException) {
            e = ioException;
        }

        if (e != null) {
            RefreshFailedException rfException
                = new RefreshFailedException("Failed to renew Kerberos Ticket "
                                             + "for client " + client
                                             + " and server " + server
                                             + " - " + e.getMessage());
            rfException.initCause(e);
            throw rfException;
        }

        /*
         * In case multiple threads try to refresh it at the same time.
         * <p>
         *  如果多个线程尝试同时刷新它。
         * 
         */
        synchronized (this) {
            try {
                this.destroy();
            } catch (DestroyFailedException dfException) {
                // Squelch it since we don't care about the old ticket.
            }
            init(krb5Creds.getEncoded(),
                 new KerberosPrincipal(krb5Creds.getClient().getName()),
                 new KerberosPrincipal(krb5Creds.getServer().getName(),
                                        KerberosPrincipal.KRB_NT_SRV_INST),
                 krb5Creds.getSessionKey().getBytes(),
                 krb5Creds.getSessionKey().getEType(),
                 krb5Creds.getFlags(),
                 krb5Creds.getAuthTime(),
                 krb5Creds.getStartTime(),
                 krb5Creds.getEndTime(),
                 krb5Creds.getRenewTill(),
                 krb5Creds.getClientAddresses());
            destroyed = false;
        }
    }

    /**
     * Destroys the ticket and destroys any sensitive information stored in
     * it.
     * <p>
     *  销毁票据并销毁其中存储的任何敏感信息。
     * 
     */
    public void destroy() throws DestroyFailedException {
        if (!destroyed) {
            Arrays.fill(asn1Encoding, (byte) 0);
            client = null;
            server = null;
            sessionKey.destroy();
            flags = null;
            authTime = null;
            startTime = null;
            endTime = null;
            renewTill = null;
            clientAddresses = null;
            destroyed = true;
        }
    }

    /**
     * Determines if this ticket has been destroyed.
     * <p>
     *  确定此故障单是否已销毁。
     * 
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    public String toString() {
        if (destroyed)
            throw new IllegalStateException("This ticket is no longer valid");
        StringBuffer caddrBuf = new StringBuffer();
        if (clientAddresses != null) {
            for (int i = 0; i < clientAddresses.length; i++) {
                caddrBuf.append("clientAddresses[" + i + "] = " +
                                 clientAddresses[i].toString());
            }
        }
        return ("Ticket (hex) = " + "\n" +
                 (new HexDumpEncoder()).encodeBuffer(asn1Encoding) + "\n" +
                "Client Principal = " + client.toString() + "\n" +
                "Server Principal = " + server.toString() + "\n" +
                "Session Key = " + sessionKey.toString() + "\n" +
                "Forwardable Ticket " + flags[FORWARDABLE_TICKET_FLAG] + "\n" +
                "Forwarded Ticket " + flags[FORWARDED_TICKET_FLAG] + "\n" +
                "Proxiable Ticket " + flags[PROXIABLE_TICKET_FLAG] + "\n" +
                "Proxy Ticket " + flags[PROXY_TICKET_FLAG] + "\n" +
                "Postdated Ticket " + flags[POSTDATED_TICKET_FLAG] + "\n" +
                "Renewable Ticket " + flags[RENEWABLE_TICKET_FLAG] + "\n" +
                "Initial Ticket " + flags[RENEWABLE_TICKET_FLAG] + "\n" +
                "Auth Time = " + String.valueOf(authTime) + "\n" +
                "Start Time = " + String.valueOf(startTime) + "\n" +
                "End Time = " + endTime.toString() + "\n" +
                "Renew Till = " + String.valueOf(renewTill) + "\n" +
                "Client Addresses " +
                (clientAddresses == null ? " Null " : caddrBuf.toString() +
                "\n"));
    }

    /**
     * Returns a hashcode for this KerberosTicket.
     *
     * <p>
     *  返回此KerberosTicket的哈希码。
     * 
     * 
     * @return a hashCode() for the {@code KerberosTicket}
     * @since 1.6
     */
    public int hashCode() {
        int result = 17;
        if (isDestroyed()) {
            return result;
        }
        result = result * 37 + Arrays.hashCode(getEncoded());
        result = result * 37 + endTime.hashCode();
        result = result * 37 + client.hashCode();
        result = result * 37 + server.hashCode();
        result = result * 37 + sessionKey.hashCode();

        // authTime may be null
        if (authTime != null) {
            result = result * 37 + authTime.hashCode();
        }

        // startTime may be null
        if (startTime != null) {
            result = result * 37 + startTime.hashCode();
        }

        // renewTill may be null
        if (renewTill != null) {
            result = result * 37 + renewTill.hashCode();
        }

        // clientAddress may be null, the array's hashCode is 0
        result = result * 37 + Arrays.hashCode(clientAddresses);
        return result * 37 + Arrays.hashCode(flags);
    }

    /**
     * Compares the specified Object with this KerberosTicket for equality.
     * Returns true if the given object is also a
     * {@code KerberosTicket} and the two
     * {@code KerberosTicket} instances are equivalent.
     *
     * <p>
     *  将指定的对象与此KerberosTicket比较以确保相等。
     * 如果给定对象也是{@code KerberosTicket}并且两个{@code KerberosTicket}实例是等效的,则返回true。
     * 
     * @param other the Object to compare to
     * @return true if the specified object is equal to this KerberosTicket,
     * false otherwise. NOTE: Returns false if either of the KerberosTicket
     * objects has been destroyed.
     * @since 1.6
     */
    public boolean equals(Object other) {

        if (other == this)
            return true;

        if (! (other instanceof KerberosTicket)) {
            return false;
        }

        KerberosTicket otherTicket = ((KerberosTicket) other);
        if (isDestroyed() || otherTicket.isDestroyed()) {
            return false;
        }

        if (!Arrays.equals(getEncoded(), otherTicket.getEncoded()) ||
                !endTime.equals(otherTicket.getEndTime()) ||
                !server.equals(otherTicket.getServer()) ||
                !client.equals(otherTicket.getClient()) ||
                !sessionKey.equals(otherTicket.getSessionKey()) ||
                !Arrays.equals(clientAddresses, otherTicket.getClientAddresses()) ||
                !Arrays.equals(flags, otherTicket.getFlags())) {
            return false;
        }

        // authTime may be null
        if (authTime == null) {
            if (otherTicket.getAuthTime() != null)
                return false;
        } else {
            if (!authTime.equals(otherTicket.getAuthTime()))
                return false;
        }

        // startTime may be null
        if (startTime == null) {
            if (otherTicket.getStartTime() != null)
                return false;
        } else {
            if (!startTime.equals(otherTicket.getStartTime()))
                return false;
        }

        if (renewTill == null) {
            if (otherTicket.getRenewTill() != null)
                return false;
        } else {
            if (!renewTill.equals(otherTicket.getRenewTill()))
                return false;
        }

        return true;
    }

    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        if (sessionKey == null) {
           throw new InvalidObjectException("Session key cannot be null");
        }
        try {
            init(asn1Encoding, client, server, sessionKey,
                 flags, authTime, startTime, endTime,
                 renewTill, clientAddresses);
        } catch (IllegalArgumentException iae) {
            throw (InvalidObjectException)
                new InvalidObjectException(iae.getMessage()).initCause(iae);
        }
    }
}
