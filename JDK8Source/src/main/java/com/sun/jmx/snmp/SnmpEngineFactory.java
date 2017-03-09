/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.jmx.snmp;

/**
 * This <CODE>SnmpEngineFactory</CODE> is instantiating an <CODE>SnmpEngine</CODE> containing :
 * <ul>
 * <li> Message Processing Sub System + V1, V2 et V3 Message Processing Models</li>
 * <li> Security Sub System + User based Security Model (Id 3)</li>
 * <li> Access Control Sub System + Ip Acl + User based Access Control Model. See <CODE> IpAcl </CODE> and <CODE> UserAcl </CODE>.</li>
 * </ul>
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  这个<CODE> SnmpEngineFactory </CODE>是实例化一个<CODE> SnmpEngine </CODE>,包含：
 * <ul>
 *  <li>消息处理子系统+ V1,V2和V3消息处理模型</li> <li>安全子系统+基于用户的安全模型(Id 3)</li> <li>访问控制子系统+基于用户的访问控制模型。
 * 请参阅<CODE> IpAcl </CODE>和<CODE> UserAcl </CODE>。</li>。
 * </ul>
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * @since 1.5
 */
public interface SnmpEngineFactory {
    /**
     * The engine instantiation method.
     * <p>
     * 
     * 
     * @param p The parameters used to instantiate a new engine.
     * @throws IllegalArgumentException Throwed if one of the configuration file file doesn't exist (Acl files, security file).
     * @return The newly created SnmpEngine.
     */
    public SnmpEngine createEngine(SnmpEngineParameters p);

    /**
     * The engine instantiation method.
     * <p>
     *  发动机实例化方法。
     * 
     * 
     * @param p The parameters used to instantiate a new engine.
     * @param ipacl The Ip ACL to pass to the Access Control Model.
     * @throws IllegalArgumentException Throwed if one of the configuration
     *         file file doesn't exist (Acl files, security file).
     * @return The newly created SnmpEngine.
     */
    public SnmpEngine createEngine(SnmpEngineParameters p,
                                   InetAddressAcl ipacl);
}
