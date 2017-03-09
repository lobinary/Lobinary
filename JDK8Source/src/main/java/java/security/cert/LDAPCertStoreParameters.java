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

package java.security.cert;

/**
 * Parameters used as input for the LDAP {@code CertStore} algorithm.
 * <p>
 * This class is used to provide necessary configuration parameters (server
 * name and port number) to implementations of the LDAP {@code CertStore}
 * algorithm.
 * <p>
 * <b>Concurrent Access</b>
 * <p>
 * Unless otherwise specified, the methods defined in this class are not
 * thread-safe. Multiple threads that need to access a single
 * object concurrently should synchronize amongst themselves and
 * provide the necessary locking. Multiple threads each manipulating
 * separate objects need not synchronize.
 *
 * <p>
 *  用作LDAP {@code CertStore}算法的输入的参数。
 * <p>
 *  此类用于向LDAP {@code CertStore}算法的实现提供必要的配置参数(服务器名称和端口号)。
 * <p>
 *  <b>并行访问</b>
 * <p>
 *  除非另有说明,否则此类中定义的方法不是线程安全的。需要并发访问单个对象的多个线程应在它们之间同步并提供必要的锁定。每个操作单独对象的多个线程不需要同步。
 * 
 * 
 * @since       1.4
 * @author      Steve Hanna
 * @see         CertStore
 */
public class LDAPCertStoreParameters implements CertStoreParameters {

    private static final int LDAP_DEFAULT_PORT = 389;

    /**
     * the port number of the LDAP server
     * <p>
     *  LDAP服务器的端口号
     * 
     */
    private int port;

    /**
     * the DNS name of the LDAP server
     * <p>
     *  LDAP服务器的DNS名称
     * 
     */
    private String serverName;

    /**
     * Creates an instance of {@code LDAPCertStoreParameters} with the
     * specified parameter values.
     *
     * <p>
     *  使用指定的参数值创建{@code LDAPCertStoreParameters}的实例。
     * 
     * 
     * @param serverName the DNS name of the LDAP server
     * @param port the port number of the LDAP server
     * @exception NullPointerException if {@code serverName} is
     * {@code null}
     */
    public LDAPCertStoreParameters(String serverName, int port) {
        if (serverName == null)
            throw new NullPointerException();
        this.serverName = serverName;
        this.port = port;
    }

    /**
     * Creates an instance of {@code LDAPCertStoreParameters} with the
     * specified server name and a default port of 389.
     *
     * <p>
     *  使用指定的服务器名称和默认端口389创建{@code LDAPCertStoreParameters}的实例。
     * 
     * 
     * @param serverName the DNS name of the LDAP server
     * @exception NullPointerException if {@code serverName} is
     * {@code null}
     */
    public LDAPCertStoreParameters(String serverName) {
        this(serverName, LDAP_DEFAULT_PORT);
    }

    /**
     * Creates an instance of {@code LDAPCertStoreParameters} with the
     * default parameter values (server name "localhost", port 389).
     * <p>
     *  使用默认参数值(服务器名称"localhost",端口389)创建{@code LDAPCertStoreParameters}的实例。
     * 
     */
    public LDAPCertStoreParameters() {
        this("localhost", LDAP_DEFAULT_PORT);
    }

    /**
     * Returns the DNS name of the LDAP server.
     *
     * <p>
     *  返回LDAP服务器的DNS名称。
     * 
     * 
     * @return the name (not {@code null})
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * Returns the port number of the LDAP server.
     *
     * <p>
     *  返回LDAP服务器的端口号。
     * 
     * 
     * @return the port number
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns a copy of this object. Changes to the copy will not affect
     * the original and vice versa.
     * <p>
     * Note: this method currently performs a shallow copy of the object
     * (simply calls {@code Object.clone()}). This may be changed in a
     * future revision to perform a deep copy if new parameters are added
     * that should not be shared.
     *
     * <p>
     *  返回此对象的副本。对副本的更改不会影响原始副本,反之亦然。
     * <p>
     * 注意：此方法当前执行对象的浅拷贝(只需调用{@code Object.clone()})。如果添加了不应共享的新参数,则可以在将来的版本中更改此选项以执行深度复制。
     * 
     * 
     * @return the copy
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            /* Cannot happen */
            throw new InternalError(e.toString(), e);
        }
    }

    /**
     * Returns a formatted string describing the parameters.
     *
     * <p>
     * 
     * @return a formatted string describing the parameters
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LDAPCertStoreParameters: [\n");

        sb.append("  serverName: " + serverName + "\n");
        sb.append("  port: " + port + "\n");
        sb.append("]");
        return sb.toString();
    }
}
