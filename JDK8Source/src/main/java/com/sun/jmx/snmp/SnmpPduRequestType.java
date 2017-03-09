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

/**
 * Interface implemented by classes modelizing request pdu.
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  通过类实现的接口模型化请求pdu。 <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */
public interface SnmpPduRequestType extends SnmpAckPdu {
    /**
     * Error index setter. Remember that SNMP indices start from 1.
     * Thus the corresponding <CODE>SnmpVarBind</CODE> is
     * <CODE>varBindList[errorIndex-1]</CODE>.
     * <p>
     *  错误索引设置器。记住SNMP索引从1开始。因此,相应的<CODE> SnmpVarBind </CODE>是<CODE> varBindList [errorIndex-1] </CODE>。
     * 
     * 
     * @param i Error index.
     */
    public void setErrorIndex(int i);
    /**
     * Error status setter. Statuses are defined in
     * {@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}.
     * <p>
     *  错误状态设置器。状态在{@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}中定义。
     * 
     * 
     * @param i Error status.
     */
    public void setErrorStatus(int i);
    /**
     * Error index getter. Remember that SNMP indices start from 1.
     * Thus the corresponding <CODE>SnmpVarBind</CODE> is
     * <CODE>varBindList[errorIndex-1]</CODE>.
     * <p>
     *  错误索引getter。记住SNMP索引从1开始。因此,相应的<CODE> SnmpVarBind </CODE>是<CODE> varBindList [errorIndex-1] </CODE>。
     * 
     * 
     * @return Error index.
     */
    public int getErrorIndex();
    /**
     * Error status getter. Statuses are defined in
     * {@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}.
     * <p>
     *  错误状态getter。状态在{@link com.sun.jmx.snmp.SnmpDefinitions SnmpDefinitions}中定义。
     * 
     * @return Error status.
     */
    public int getErrorStatus();
}
