/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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



// java imports
//
import java.util.Vector;

// jmx imports
//
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;
import javax.management.InstanceNotFoundException;
import javax.management.ServiceNotFoundException;
import com.sun.jmx.snmp.SnmpOid;
import com.sun.jmx.snmp.SnmpStatusException;

/**
 * Exposes the remote management interface of the <CODE>SnmpMibAgent</CODE> MBean.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  公开<CODE> SnmpMibAgent </CODE> MBean的远程管理界面。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

public interface SnmpMibAgentMBean {

    // PUBLIC METHODS
    //---------------

    /**
     * Processes a <CODE>get</CODE> operation.
     * This method must not be called from remote.
     *
     * <p>
     *  处理<CODE> get </CODE>操作。不能从远程调用此方法。
     * 
     * 
     * @param req The SnmpMibRequest object holding the list of variables to
     *            be retrieved. This list is composed of
     *            <CODE>SnmpVarBind</CODE> objects.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     * @see SnmpMibAgent#get(SnmpMibRequest)
     */
    public void get(SnmpMibRequest req) throws SnmpStatusException;

    /**
     * Processes a <CODE>getNext</CODE> operation.
     * This method must not be called from remote.
     *
     * <p>
     *  处理<CODE> getNext </CODE>操作。不能从远程调用此方法。
     * 
     * 
     * @param req The SnmpMibRequest object holding the list of variables to
     *            be retrieved. This list is composed of
     *            <CODE>SnmpVarBind</CODE> objects.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     * @see SnmpMibAgent#getNext(SnmpMibRequest)
     */
    public void getNext(SnmpMibRequest req) throws SnmpStatusException;

    /**
     * Processes a <CODE>getBulk</CODE> operation.
     * This method must not be called from remote.
     *
     * <p>
     *  处理<CODE> getBulk </CODE>操作。不能从远程调用此方法。
     * 
     * 
     * @param req The SnmpMibRequest object holding the list of variables to
     *            be retrieved. This list is composed of
     *            <CODE>SnmpVarBind</CODE> objects.
     *
     * @param nonRepeat The number of variables, starting with the first
     *    variable in the variable-bindings, for which a single
     *    lexicographic successor is requested.
     *
     * @param maxRepeat The number of lexicographic successors requested
     *    for each of the last R variables. R is the number of variables
     *    following the first <CODE>nonRepeat</CODE> variables for which
     *    multiple lexicographic successors are requested.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     * @see SnmpMibAgent#getBulk(SnmpMibRequest,int,int)
     */
    public void getBulk(SnmpMibRequest req, int nonRepeat, int maxRepeat)
        throws SnmpStatusException;

    /**
     * Processes a <CODE>set</CODE> operation.
     * This method must not be called from remote.
     *
     * <p>
     *  处理<CODE>设置</CODE>操作。不能从远程调用此方法。
     * 
     * 
     * @param req The SnmpMibRequest object holding the list of variables to
     *            be set. This list is composed of
     *            <CODE>SnmpVarBind</CODE> objects.
     *
     * @exception SnmpStatusException An error occurred during the operation.
     * @see SnmpMibAgent#set(SnmpMibRequest)
     */
    public void set(SnmpMibRequest req) throws SnmpStatusException;

    /**
     * Checks if a <CODE>set</CODE> operation can be performed.
     * If the operation cannot be performed, the method should emit a
     * <CODE>SnmpStatusException</CODE>.
     *
     * <p>
     *  检查是否可以执行<CODE>设置</CODE>操作。如果无法执行该操作,则该方法应该发出<CODE> SnmpStatusException </CODE>。
     * 
     * 
     * @param req The SnmpMibRequest object holding the list of variables to
     *            be set. This list is composed of
     *            <CODE>SnmpVarBind</CODE> objects.
     *
     * @exception SnmpStatusException The <CODE>set</CODE> operation
     *    cannot be performed.
     * @see SnmpMibAgent#check(SnmpMibRequest)
     */
    public void check(SnmpMibRequest req) throws SnmpStatusException;

    // GETTERS AND SETTERS
    //--------------------

    /**
     * Gets the reference to the MBean server in which the SNMP MIB is
     * registered.
     *
     * <p>
     *  获取对注册了SNMP MIB的MBean服务器的引用。
     * 
     * 
     * @return The MBean server or null if the MIB is not registered in any
     *         MBean server.
     */
    public MBeanServer getMBeanServer();

    /**
     * Gets the reference to the SNMP protocol adaptor to which the MIB is
     * bound.
     * <BR>This method is used for accessing the SNMP MIB handler property
     * of the SNMP MIB agent in case of a standalone agent.
     *
     * <p>
     *  获取对MIB绑定的SNMP协议适配器的引用。 <BR>此方法用于访问SNMP MIB代理的SNMP MIB处理程序属性(如果是独立代理)。
     * 
     * 
     * @return The SNMP MIB handler.
     */
    public SnmpMibHandler getSnmpAdaptor();

    /**
     * Sets the reference to the SNMP protocol adaptor through which the
     * MIB will be SNMP accessible and add this new MIB in the SNMP MIB
     * handler.
     * <BR>This method is used for setting the SNMP MIB handler property of
     * the SNMP MIB agent in case of a standalone agent.
     *
     * <p>
     *  设置SNMP协议适配器的引用,通过该协议适配器可以访问MIB,并将该新MIB添加到SNMP MIB处理程序中。
     *  <BR>此方法用于在独立代理的情况下设置SNMP MIB代理的SNMP MIB处理程序属性。
     * 
     * 
     * @param stack The SNMP MIB handler.
     */
    public void setSnmpAdaptor(SnmpMibHandler stack);

    /**
     * Sets the reference to the SNMP protocol adaptor through which the MIB
     * will be SNMP accessible and add this new MIB in the SNMP MIB handler.
     * This method is to be called to set a specific agent to a specific OID.
     * This can be useful when dealing with MIB overlapping.
     * Some OID can be implemented in more than one MIB. In this case, the
     * OID nearer agent will be used on SNMP operations.
     * <p>
     * 设置SNMP协议适配器的引用,通过该协议适配器可以访问MIB,并将该新MIB添加到SNMP MIB处理程序中。调用此方法以将特定代理设置为特定OID。这在处理MIB重叠时很有用。
     * 一些OID可以在多个MIB中实现。在这种情况下,OID近端代理将用于SNMP操作。
     * 
     * 
     * @param stack The SNMP MIB handler.
     * @param oids The set of OIDs this agent implements.
     *
     * @since 1.5
     */
    public void setSnmpAdaptor(SnmpMibHandler stack, SnmpOid[] oids);

    /**
     * Sets the reference to the SNMP protocol adaptor through which the MIB
     * will be SNMP accessible and add this new MIB in the SNMP MIB handler.
     * Adds a new contextualized MIB in the SNMP MIB handler.
     *
     * <p>
     *  设置SNMP协议适配器的引用,通过该协议适配器可以访问MIB,并将该新MIB添加到SNMP MIB处理程序中。在SNMP MIB处理程序中添加新的上下文化MIB。
     * 
     * 
     * @param stack The SNMP MIB handler.
     * @param contextName The MIB context name. If null is passed, will be
     *        registered in the default context.
     *
     * @exception IllegalArgumentException If the parameter is null.
     *
     * @since 1.5
     */
    public void setSnmpAdaptor(SnmpMibHandler stack, String contextName);

    /**
     * Sets the reference to the SNMP protocol adaptor through which the MIB
     * will be SNMP accessible and adds this new MIB in the SNMP MIB handler.
     * Adds a new contextualized MIB in the SNMP MIB handler.
     *
     * <p>
     *  设置SNMP协议适配器的引用,通过该协议适配器将SNMP可访问SNMP,并将此新MIB添加到SNMP MIB处理程序中。在SNMP MIB处理程序中添加新的上下文化MIB。
     * 
     * 
     * @param stack The SNMP MIB handler.
     * @param contextName The MIB context name. If null is passed, will be
     *        registered in the default context.
     * @param oids The set of OIDs this agent implements.
     * @exception IllegalArgumentException If the parameter is null.
     *
     * @since 1.5
     */
    public void setSnmpAdaptor(SnmpMibHandler stack,
                               String contextName,
                               SnmpOid[] oids);

    /**
     * Gets the object name of the SNMP protocol adaptor to which the MIB is
     * bound.
     *
     * <p>
     *  获取MIB绑定的SNMP协议适配器的对象名称。
     * 
     * 
     * @return The name of the SNMP protocol adaptor.
     */
    public ObjectName getSnmpAdaptorName();

    /**
     * Sets the reference to the SNMP protocol adaptor through which the MIB
     * will be SNMP accessible and add this new MIB in the SNMP MIB handler
     * associated to the specified <CODE>name</CODE>.
     *
     * <p>
     *  设置对MIB进行SNMP访问的SNMP协议适配器的引用,并将该新MIB添加到与指定的<CODE>名称</CODE>相关联的SNMP MIB处理程序中。
     * 
     * 
     * @param name The object name of the SNMP MIB handler.
     *
     * @exception InstanceNotFoundException The MBean does not exist in the
     *        MBean server.
     * @exception ServiceNotFoundException This SNMP MIB is not registered
     *        in the MBean server or the requested service is not supported.
     */
    public void setSnmpAdaptorName(ObjectName name)
        throws InstanceNotFoundException, ServiceNotFoundException;


    /**
     * Sets the reference to the SNMP protocol adaptor through which the MIB
     * will be SNMP accessible and add this new MIB in the SNMP MIB handler
     * associated to the specified <CODE>name</CODE>.
     * This method is to be called to set a specific agent to a specific OID.
     * This can be useful when dealing with MIB overlapping.
     * Some OID can be implemented in more than one MIB. In this case, the
     * OID nearer agent will be used on SNMP operations.
     * <p>
     *  设置对MIB进行SNMP访问的SNMP协议适配器的引用,并将该新MIB添加到与指定的<CODE>名称</CODE>相关联的SNMP MIB处理程序中。调用此方法以将特定代理设置为特定OID。
     * 这在处理MIB重叠时很有用。一些OID可以在多个MIB中实现。在这种情况下,OID近端代理将用于SNMP操作。
     * 
     * 
     * @param name The name of the SNMP protocol adaptor.
     * @param oids The set of OIDs this agent implements.
     * @exception InstanceNotFoundException The SNMP protocol adaptor does
     *     not exist in the MBean server.
     *
     * @exception ServiceNotFoundException This SNMP MIB is not registered
     *     in the MBean server or the requested service is not supported.
     *
     * @since 1.5
     */
    public void setSnmpAdaptorName(ObjectName name, SnmpOid[] oids)
        throws InstanceNotFoundException, ServiceNotFoundException;

    /**
     * Sets the reference to the SNMP protocol adaptor through which the MIB
     * will be SNMP accessible and add this new MIB in the SNMP MIB handler
     * associated to the specified <CODE>name</CODE>.
     *
     * <p>
     * 设置对MIB进行SNMP访问的SNMP协议适配器的引用,并将该新MIB添加到与指定的<CODE>名称</CODE>相关联的SNMP MIB处理程序中。
     * 
     * 
     * @param name The name of the SNMP protocol adaptor.
     * @param contextName The MIB context name. If null is passed, will be
     *     registered in the default context.
     * @exception InstanceNotFoundException The SNMP protocol adaptor does
     *     not exist in the MBean server.
     *
     * @exception ServiceNotFoundException This SNMP MIB is not registered
     *     in the MBean server or the requested service is not supported.
     *
     * @since 1.5
     */
    public void setSnmpAdaptorName(ObjectName name, String contextName)
        throws InstanceNotFoundException, ServiceNotFoundException;

     /**
     * Sets the reference to the SNMP protocol adaptor through which the MIB
     * will be SNMP accessible and add this new MIB in the SNMP MIB handler
     * associated to the specified <CODE>name</CODE>.
     *
     * <p>
     *  设置对MIB进行SNMP访问的SNMP协议适配器的引用,并将该新MIB添加到与指定的<CODE>名称</CODE>相关联的SNMP MIB处理程序中。
     * 
     * 
     * @param name The name of the SNMP protocol adaptor.
     * @param contextName The MIB context name. If null is passed, will be
     *        registered in the default context.
     * @param oids The set of OIDs this agent implements.
     * @exception InstanceNotFoundException The SNMP protocol adaptor does
     *     not exist in the MBean server.
     *
     * @exception ServiceNotFoundException This SNMP MIB is not registered
     *     in the MBean server or the requested service is not supported.
     *
     * @since 1.5
     */
    public void setSnmpAdaptorName(ObjectName name,
                                   String contextName,
                                   SnmpOid[] oids)
        throws InstanceNotFoundException, ServiceNotFoundException;

    /**
     * Indicates whether or not the MIB module is bound to a SNMP protocol
     * adaptor.
     * As a reminder, only bound MIBs can be accessed through SNMP protocol
     * adaptor.
     *
     * <p>
     *  指示MIB模块是否绑定到SNMP协议适配器。作为提醒,只有绑定的MIB可以通过SNMP协议适配器访问。
     * 
     * 
     * @return <CODE>true</CODE> if the MIB module is bound,
     *         <CODE>false</CODE> otherwise.
     */
    public boolean getBindingState();

    /**
     * Gets the MIB name.
     *
     * <p>
     *  获取MIB名称。
     * 
     * @return The MIB name.
     */
    public String getMibName();
}
