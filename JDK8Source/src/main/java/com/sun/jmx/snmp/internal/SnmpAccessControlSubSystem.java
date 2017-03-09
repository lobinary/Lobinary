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
import com.sun.jmx.snmp.SnmpUnknownAccContrModelException;
/**
 * Access Control sub system interface. To allow engine integration, an Access Control sub system must implement this interface.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  访问控制子系统界面。要允许引擎集成,访问控制子系统必须实现此接口。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */
public interface SnmpAccessControlSubSystem extends SnmpSubSystem {

    /**
     * Method called by the dispatcher in order to control the access at an SNMP pdu Level.
     * <P> This call is routed by the sub system to the target model according to the SNMP protocol version number.</P>
     * <p>
     *  调度器调用的方法为了控制在SNMP pdu级别的访问。 <P>此呼叫由子系统根据SNMP协议版本号路由到目标模型。</P>
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
                               SnmpPdu pdu) throws SnmpStatusException, SnmpUnknownAccContrModelException;
    /**
     * Method called by the dispatcher in order to control the access at an <CODE>SnmpOid</CODE> Level.
     * This method is called after the <CODE>checkPduAccess</CODE> pdu based method.
     * <P> This call is routed by the sub system to the target model according to the SNMP protocol version number.</P>
     * <p>
     *  调度器调用的方法为了控制在<CODE> SnmpOid </CODE>级别的访问。此方法在<CODE> checkPduAccess </CODE> pdu基础方法后调用。
     *  <P>此呼叫由子系统根据SNMP协议版本号路由到目标模型。</P>。
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
                            SnmpOid oid) throws SnmpStatusException, SnmpUnknownAccContrModelException;
}
