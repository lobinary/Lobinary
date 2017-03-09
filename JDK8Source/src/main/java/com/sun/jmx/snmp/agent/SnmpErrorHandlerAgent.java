/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.snmp.agent;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.logging.Level;

import javax.management.ObjectName;
import javax.management.MBeanServer;

import static com.sun.jmx.defaults.JmxProperties.SNMP_ADAPTOR_LOGGER;
import com.sun.jmx.snmp.SnmpStatusException;
import com.sun.jmx.snmp.SnmpDefinitions;
import com.sun.jmx.snmp.SnmpVarBind;

/**
 * A simple MIB agent that implements SNMP calls (get, set, getnext and getbulk) in a way that only errors or exceptions are returned. Every call done on this agent fails. Error handling is done according to the manager's SNMP protocol version.
 * <P>It is used by <CODE>SnmpAdaptorServer</CODE> for its default agent behavior. When a received Oid doesn't match, this agent is called to fill the result list with errors.</P>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  一个简单的MIB代理,它以只返回错误或异常的方式实现SNMP调用(get,set,getnext和getbulk)。在此代理上执行的每个调用都将失败。根据管理器的SNMP协议版本进行错误处理。
 *  <P>它由<CODE> SnmpAdaptorServer </CODE>用于其默认代理行为。当接收到的Oid不匹配时,将调用此代理来填充结果列表中的错误。
 * </p> <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。 > </p>。
 * 
 * 
 * @since 1.5
 *
 */

public class SnmpErrorHandlerAgent extends SnmpMibAgent
        implements Serializable {
    private static final long serialVersionUID = 7751082923508885650L;

    public SnmpErrorHandlerAgent() {}

    /**
     * Initializes the MIB (with no registration of the MBeans into the
     * MBean server). Does nothing.
     *
     * <p>
     *  初始化MIB(无需将MBean注册到MBean服务器中)。什么也没做。
     * 
     * 
     * @exception IllegalAccessException The MIB cannot be initialized.
     */

    @Override
    public void init() throws IllegalAccessException {
    }

    /**
     * Initializes the MIB but each single MBean representing the MIB
     * is inserted into the MBean server.
     *
     * <p>
     *  初始化MIB,但表示MIB的每个单个MBean都插入到MBean服务器中。
     * 
     * 
     * @param server The MBean server to register the service with.
     * @param name The object name.
     *
     * @return The passed name parameter.
     *
     * @exception java.lang.Exception
     */

    @Override
    public ObjectName preRegister(MBeanServer server, ObjectName name)
        throws Exception {
        return name;
    }

    /**
     * Gets the root object identifier of the MIB.
     * <P>The root object identifier is the object identifier uniquely
     * identifying the MIB.
     *
     * <p>
     *  获取MIB的根对象标识符。 <P>根对象标识符是唯一地标识MIB的对象标识符。
     * 
     * 
     * @return The returned oid is null.
     */

    @Override
    public long[] getRootOid() {
        return null;
    }

    /**
     * Processes a <CODE>get</CODE> operation. It will throw an exception for V1 requests or it will set exceptions within the list for V2 requests.
     *
     * <p>
     *  处理<CODE> get </CODE>操作。它会为V1请求抛出异常,或者在V2请求的列表中设置异常。
     * 
     * 
     * @param inRequest The SnmpMibRequest object holding the list of variable to be retrieved.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     */

    @Override
    public void get(SnmpMibRequest inRequest) throws SnmpStatusException {

        SNMP_ADAPTOR_LOGGER.logp(Level.FINEST,
                SnmpErrorHandlerAgent.class.getName(),
                "get", "Get in Exception");

        if(inRequest.getVersion() == SnmpDefinitions.snmpVersionOne)
            throw new SnmpStatusException(SnmpStatusException.noSuchName);

        Enumeration<SnmpVarBind> l = inRequest.getElements();
        while(l.hasMoreElements()) {
            SnmpVarBind varbind = l.nextElement();
            varbind.setNoSuchObject();
        }
    }

    /**
     * Checks if a <CODE>set</CODE> operation can be performed.
     * If the operation can not be performed, the method should emit a
     * <CODE>SnmpStatusException</CODE>.
     *
     * <p>
     *  检查是否可以执行<CODE>设置</CODE>操作。如果无法执行该操作,则该方法应该发出<CODE> SnmpStatusException </CODE>。
     * 
     * 
     * @param inRequest The SnmpMibRequest object holding the list of variables to
     *            be set. This list is composed of
     *            <CODE>SnmpVarBind</CODE> objects.
     *
     * @exception SnmpStatusException The <CODE>set</CODE> operation
     *    cannot be performed.
     */

    @Override
    public void check(SnmpMibRequest inRequest) throws SnmpStatusException {

        SNMP_ADAPTOR_LOGGER.logp(Level.FINEST,
                SnmpErrorHandlerAgent.class.getName(),
                "check", "Check in Exception");

        throw new SnmpStatusException(SnmpDefinitions.snmpRspNotWritable);
    }

    /**
     * Processes a <CODE>set</CODE> operation. Should never be called (check previously called having failed).
     *
     * <p>
     *  处理<CODE>设置</CODE>操作。永远不应该被调用(检查以前调用失败)。
     * 
     * 
     * @param inRequest The SnmpMibRequest object holding the list of variable to be set.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     */

    @Override
    public void set(SnmpMibRequest inRequest) throws SnmpStatusException {

        SNMP_ADAPTOR_LOGGER.logp(Level.FINEST,
                SnmpErrorHandlerAgent.class.getName(),
                "set", "Set in Exception, CANNOT be called");

        throw new SnmpStatusException(SnmpDefinitions.snmpRspNotWritable);
    }

    /**
     * Processes a <CODE>getNext</CODE> operation. It will throw an exception for V1 requests or it will set exceptions within the list for V2 requests..
     *
     * <p>
     * 处理<CODE> getNext </CODE>操作。它会为V1请求抛出异常,或者会在V2请求的列表中设置异常。
     * 
     * 
     * @param inRequest The SnmpMibRequest object holding the list of variables to be retrieved.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     */

    @Override
    public void getNext(SnmpMibRequest inRequest) throws SnmpStatusException {

        SNMP_ADAPTOR_LOGGER.logp(Level.FINEST,
                SnmpErrorHandlerAgent.class.getName(),
                "getNext", "GetNext in Exception");

        if(inRequest.getVersion() == SnmpDefinitions.snmpVersionOne)
            throw new SnmpStatusException(SnmpStatusException.noSuchName);

        Enumeration<SnmpVarBind> l = inRequest.getElements();
        while(l.hasMoreElements()) {
            SnmpVarBind varbind = l.nextElement();
            varbind.setEndOfMibView();
        }
    }

    /**
     * Processes a <CODE>getBulk</CODE> operation. It will throw an exception if the request is a V1 one or it will set exceptions within the list for V2 ones.
     *
     * <p>
     *  处理<CODE> getBulk </CODE>操作。如果请求是一个V1,它将抛出异常,或者它将在V2的列表中设置异常。
     * 
     * @param inRequest The SnmpMibRequest object holding the list of variable to be retrieved.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     */

    @Override
    public void getBulk(SnmpMibRequest inRequest, int nonRepeat, int maxRepeat)
        throws SnmpStatusException {

        SNMP_ADAPTOR_LOGGER.logp(Level.FINEST,
                SnmpErrorHandlerAgent.class.getName(),
                "getBulk", "GetBulk in Exception");

        if(inRequest.getVersion() == SnmpDefinitions.snmpVersionOne)
            throw new SnmpStatusException(SnmpDefinitions.snmpRspGenErr, 0);

        Enumeration<SnmpVarBind> l = inRequest.getElements();
        while(l.hasMoreElements()) {
            SnmpVarBind varbind = l.nextElement();
            varbind.setEndOfMibView();
        }
    }

}
