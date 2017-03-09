/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.snmp.daemon ;

// JMX imports
//
import com.sun.jmx.snmp.SnmpDefinitions;
import com.sun.jmx.snmp.SnmpVarBindList;

/**
 * Provides the callback methods that are required to be implemented by the application
 * when an inform response is received by the agent.
 * <P>
 * Each inform request can be provided with an object that implements this callback
 * interface. An application then uses the SNMP adaptor to start an SNMP inform request,
 * which marks the request as active. The methods in this callback interface
 * get invoked when any of the following happens:
 * <P>
 * <UL>
 * <LI> The agent receives the SNMP inform response.
 * <LI> The agent does not receive any response within a specified time and the number of tries
 * have exceeded the limit (timeout condition).
 * <LI> An internal error occurs while processing or parsing the inform request.
 * </UL>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  提供代理接收到通知响应时应用程序需要实现的回调方法。
 * <P>
 *  可以为每个通知请求提供实现此回调接口的对象。然后应用程序使用SNMP适配器启动SNMP通知请求,该请求将请求标记为活动。当发生以下任何情况时,将调用此回调接口中的方法：
 * <P>
 * <UL>
 *  <LI>代理接收SNMP通知响应。 <LI>代理在指定时间内没有收到任何响应,尝试次数已超过限制(超时条件)。 <LI>在处理或解析通知请求时发生内部错误。
 * </UL>
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public interface SnmpInformHandler extends SnmpDefinitions {

    /**
     * This callback is invoked when a manager responds to an SNMP inform request.
     * The callback should check the error status of the inform request to determine
     * the kind of response.
     *
     * <p>
     * 
     * @param request The <CODE>SnmpInformRequest</CODE> associated with this callback.
     * @param errStatus The status of the request.
     * @param errIndex The index in the list that caused the error.
     * @param vblist The <CODE>Response varBind</CODE> list for the successful request.
     */
    public abstract void processSnmpPollData(SnmpInformRequest request, int errStatus, int errIndex, SnmpVarBindList vblist);

    /**
     * This callback is invoked when a manager does not respond within the
     * specified timeout value to the SNMP inform request. The number of tries have also
     * been exhausted.
     * <p>
     *  当管理器响应SNMP通知请求时,调用此回调。回调应检查通知请求的错误状态,以确定响应的类型。
     * 
     * 
     * @param request The <CODE>SnmpInformRequest</CODE> associated with this callback.
     */
    public abstract void processSnmpPollTimeout(SnmpInformRequest request);

    /**
     * This callback is invoked when any form of internal error occurs.
     * <p>
     *  当管理器在指定的超时值内未响应SNMP通知请求时,将调用此回调。尝试的次数也已用尽。
     * 
     * 
     * @param request The <CODE>SnmpInformRequest</CODE> associated with this callback.
     * @param errmsg The <CODE>String</CODE> describing the internal error.
     */
    public abstract void processSnmpInternalError(SnmpInformRequest request, String errmsg);
}
