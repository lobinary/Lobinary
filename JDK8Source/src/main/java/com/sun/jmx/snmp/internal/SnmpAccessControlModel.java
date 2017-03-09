/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.jmx.snmp.internal;

import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpPdu;
/**
 * Access Control Model interface. Every access control model must implement this interface in order to be integrated in the engine based framework.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  访问控制模型接口。每个访问控制模型必须实现此接口,以便集成到基于引擎的框架中。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */
public interface SnmpAccessControlModel extends SnmpModel {
    /**
     * Method called by the dispatcher in order to control the access at an <CODE>SnmpOid</CODE> Level. If access is not allowed, an <CODE>SnmpStatusException</CODE> is thrown.
     * This method is called after the <CODE>checkPduAccess</CODE> pdu based method.
     * <p>
     *  调度器调用的方法,以便在<CODE> SnmpOid </CODE>级别控制访问。如果不允许访问,则会抛出<CODE> SnmpStatusException </CODE>。
     * 此方法在<CODE> checkPduAccess </CODE> pdu基础方法后调用。
     * 
     * 
     * @param version The SNMP protocol version number.
     * @param principal The request principal.
     * @param securityLevel The request security level as defined in <CODE>SnmpEngine</CODE>.
     * @param pduType The pdu type (get, set, ...).
     * @param securityModel The security model ID.
     * @param contextName The access control context name.
     * @param oid The OID to check.
     */
    public void checkAccess(int version,
                            String principal,
                            int securityLevel,
                            int pduType,
                            int securityModel,
                            byte[] contextName,
                            SnmpOid oid)
        throws SnmpStatusException;
    /**
     * Method called by the dispatcher in order to control the access at an SNMP pdu Level. If access is not allowed, an <CODE>SnmpStatusException</CODE> is thrown. In case of exception, the access control is aborted. OIDs are not checked.
     * This method should be called prior to the <CODE>checkAccess</CODE> OID based method.
     * <p>
     *  调度器调用的方法为了控制在SNMP pdu级别的访问。如果不允许访问,则会抛出<CODE> SnmpStatusException </CODE>。在异常的情况下,访问控制中止。 OID不检查。
     * 此方法应在基于<CODE> checkAccess </CODE> OID的方法之前调用。
     * 
     * 
     * @param version The SNMP protocol version number.
     * @param principal The request principal.
     * @param securityLevel The request security level as defined in <CODE>SnmpEngine</CODE>.
     * @param pduType The pdu type (get, set, ...).
     * @param securityModel The security model ID.
     * @param contextName The access control context name.
     * @param pdu The pdu to check.
     */
    public void checkPduAccess(int version,
                               String principal,
                               int securityLevel,
                               int pduType,
                               int securityModel,
                               byte[] contextName,
                               SnmpPdu pdu)
        throws SnmpStatusException;

    /**
     * Enable SNMP V1 and V2 set requests. Be aware that can lead to a security hole in a context of SNMP V3 management. By default SNMP V1 and V2 set requests are not authorized.
     * <p>
     *  启用S​​NMP V1和V2设置请求。请注意,在SNMP V3管理的上下文中可能导致安全漏洞。默认情况下,SNMP V1和V2设置请求未被授权。
     * 
     * 
     * @return boolean True the activation suceeded.
     */
    public boolean enableSnmpV1V2SetRequest();
    /**
     * Disable SNMP V1 and V2 set requests. By default SNMP V1 and V2 set requests are not authorized.
     * <p>
     *  禁用SNMP V1和V2设置请求。默认情况下,SNMP V1和V2设置请求未被授权。
     * 
     * 
     * @return boolean True the deactivation suceeded.
     */
    public boolean disableSnmpV1V2SetRequest();

    /**
     * The SNMP V1 and V2 set requests authorization status. By default SNMP V1 and V2 set requests are not authorized.
     * <p>
     *  SNMP V1和V2集请求授权状态。默认情况下,SNMP V1和V2设置请求未被授权。
     * 
     * @return boolean True SNMP V1 and V2 requests are authorized.
     */
    public boolean isSnmpV1V2SetRequestAuthorized();
}
