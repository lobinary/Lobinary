/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.security.jgss;

/**
 * Attribute types that can be specified as an argument of
 * {@link com.sun.security.jgss.ExtendedGSSContext#inquireSecContext}
 * <p>
 *  可以指定为{@link com.sun.security.jgss.ExtendedGSSContext#inquireSecContext}的参数的属性类型
 * 
 */
@jdk.Exported
public enum InquireType {
    /**
     * Attribute type for retrieving the session key of an
     * established Kerberos 5 security context.
     * <p>
     *  用于检索已建立的Kerberos 5安全上下文的会话密钥的属性类型。
     * 
     */
    KRB5_GET_SESSION_KEY,
    /**
     * Attribute type for retrieving the service ticket flags of an
     * established Kerberos 5 security context.
     * <p>
     *  用于检索已建立的Kerberos 5安全上下文的服务标签标志的属性类型。
     * 
     */
    KRB5_GET_TKT_FLAGS,
    /**
     * Attribute type for retrieving the authorization data in the
     * service ticket of an established Kerberos 5 security context.
     * Only supported on the acceptor side.
     * <p>
     *  用于在已建立的Kerberos 5安全上下文的服务故障单中检索授权数据的属性类型。仅在受体侧支持。
     * 
     */
    KRB5_GET_AUTHZ_DATA,
    /**
     * Attribute type for retrieving the authtime in the service ticket
     * of an established Kerberos 5 security context.
     * <p>
     *  用于在已建立的Kerberos 5安全上下文的服务标签中检索authtime的属性类型。
     */
    KRB5_GET_AUTHTIME
}
