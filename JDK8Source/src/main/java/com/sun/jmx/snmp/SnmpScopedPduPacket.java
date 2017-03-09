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

package com.sun.jmx.snmp;

import java.io.Serializable;

import com.sun.jmx.snmp.SnmpSecurityParameters;

import com.sun.jmx.snmp.SnmpDefinitions;
/**
 * Is the fully decoded representation of an SNMP V3 packet.
 * <P>
 *
 * Classes are derived from <CODE>SnmpPdu</CODE> to
 * represent the different forms of SNMP pdu
 * ({@link com.sun.jmx.snmp.SnmpScopedPduRequest SnmpScopedPduRequest},
 * {@link com.sun.jmx.snmp.SnmpScopedPduBulk SnmpScopedPduBulk}).
 * <BR>The <CODE>SnmpScopedPduPacket</CODE> class defines the attributes
 * common to every scoped SNMP packets.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  是SNMP V3数据包的完全解码表示。
 * <P>
 * 
 *  类派生自<CODE> SnmpPdu </CODE>以表示不同形式的SNMP pdu({@link com.sun.jmx.snmp.SnmpScopedPduRequest SnmpScopedPduRequest}
 * ,{@link com.sun.jmx.snmp.SnmpScopedPduBulk SnmpScopedPduBulk} )。
 *  <BR> <CODE> SnmpScopedPduPacket </CODE>类定义了每个作用域的SNMP数据包通用的属性。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see SnmpV3Message
 *
 * @since 1.5
 */
public abstract class SnmpScopedPduPacket extends SnmpPdu
    implements Serializable {
    /**
     * Message max size the pdu sender can deal with.
     * <p>
     *  邮件最大大小pdu发件人可以处理。
     * 
     */
    public int msgMaxSize = 0;

    /**
     * Message identifier.
     * <p>
     *  消息标识符。
     * 
     */
    public int msgId = 0;

    /**
     * Message flags. Reportable flag  and security level.</P>
     *<PRE>
     * --  .... ...1   authFlag
     * --  .... ..1.   privFlag
     * --  .... .1..   reportableFlag
     * --              Please observe:
     * --  .... ..00   is OK, means noAuthNoPriv
     * --  .... ..01   is OK, means authNoPriv
     * --  .... ..10   reserved, must NOT be used.
     * --  .... ..11   is OK, means authPriv
     *</PRE>
     * <p>
     *  消息标志。可报告的标志和安全级别。</P>
     * PRE>
     *   -  .... ... 1 authFlag  -  ....。
     *  privFlag  -  .... .1 .. reportableFlag  - 请注意： -  .... ..00是OK,表示noAuthNoPriv  -  .... ..01是OK,表示aut
     * hNoPriv  -  ... 。
     *   -  .... ... 1 authFlag  -  ....。..10保留,不得使用。 -  .... ..11是OK,表示authPriv。
     * /PRE>
     */
    public byte msgFlags = 0;

    /**
     * The security model the security sub system MUST use in order to deal with this pdu (eg: User based Security Model Id = 3).
     * <p>
     *  安全子系统必须使用的安全模型为了处理这个pdu(例如：基于用户的安全模型Id = 3)。
     * 
     */
    public int msgSecurityModel = 0;

    /**
     * The context engine Id in which the pdu must be handled (Generaly the local engine Id).
     * <p>
     *  必须处理pdu的上下文引擎Id(通常为本地引擎Id)。
     * 
     */
    public byte[] contextEngineId = null;

    /**
     * The context name in which the OID have to be interpreted.
     * <p>
     *  必须解释OID的上下文名称。
     * 
     */
    public byte[] contextName = null;

    /**
     * The security parameters. This is an opaque member that is
     * interpreted by the concerned security model.
     * <p>
     *  安全参数。这是一个不透明的成员,由相关的安全模型解释。
     * 
     */
    public SnmpSecurityParameters securityParameters = null;

    /**
     * Constructor. Is only called by a son. Set the version to <CODE>SnmpDefinitions.snmpVersionThree</CODE>.
     * <p>
     * 构造函数。只有一个儿子叫。将版本设置为<CODE> SnmpDefinitions.snmpVersionThree </CODE>。
     */
    protected SnmpScopedPduPacket() {
        version = SnmpDefinitions.snmpVersionThree;
    }
}
