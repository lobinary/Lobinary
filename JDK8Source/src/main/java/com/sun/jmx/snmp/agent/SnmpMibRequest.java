/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Enumeration;
import java.util.Vector;

import com.sun.jmx.snmp.SnmpVarBind;
import com.sun.jmx.snmp.SnmpPdu;
import com.sun.jmx.snmp.SnmpEngine;

/**
 * This interface models the part of a SNMP request that involves
 * a specific MIB. One object implementing this interface will be created
 * for every MIB involved in a SNMP request, and that object will be passed
 * to the SnmpMibAgent in charge of handling that MIB.
 *
 * Objects implementing this interface will be allocated by the SNMP engine.
 * You will never need to implement this interface. You will only use it.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此接口对涉及特定MIB的SNMP请求的一部分进行建模。将为SNMP请求中涉及的每个MIB创建实现此接口的一个对象,并将该对象传递给负责处理该MIB的SnmpMibAgent。
 * 
 *  实现此接口的对象将由SNMP引擎分配。你永远不需要实现这个接口。你只会使用它。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */
public interface SnmpMibRequest {
    /**
     * Returns the list of varbind to be handled by the SNMP mib node.
     *
     * <p>
     *  返回要由SNMP mib节点处理的varbind的列表。
     * 
     * 
     * @return The element of the enumeration are instances of
     *         {@link com.sun.jmx.snmp.SnmpVarBind}
     */
    public Enumeration<SnmpVarBind> getElements();

    /**
     * Returns the vector of varbind to be handled by the SNMP mib node.
     * The caller shall not modify this vector.
     *
     * <p>
     *  返回要由SNMP mib节点处理的varbind的向量。调用者不应修改此向量。
     * 
     * 
     * @return The element of the vector are instances of
     *         {@link com.sun.jmx.snmp.SnmpVarBind}
     */
    public Vector<SnmpVarBind> getSubList();

    /**
     * Returns the SNMP protocol version of the original request. If SNMP V1 request are received, the version is upgraded to SNMP V2.
     *
     * <p>
     *  返回原始请求的SNMP协议版本。如果收到SNMP V1请求,则将版本升级到SNMP V2。
     * 
     * 
     * @return The SNMP protocol version of the original request.
     */
    public int getVersion();

    /**
     * Returns the SNMP protocol version of the original request. No translation is done on the version. The actual received request SNMP version is returned.
     *
     * <p>
     *  返回原始请求的SNMP协议版本。在版本上不进行翻译。返回实际接收的SNMP版本。
     * 
     * 
     * @return The SNMP protocol version of the original request.
     *
     * @since 1.5
     */
    public int getRequestPduVersion();

    /**
     * Returns the local engine. This parameter is returned only if <CODE> SnmpV3AdaptorServer </CODE> is the adaptor receiving this request. Otherwise null is returned.
     * <p>
     *  返回本地引擎。仅当<CODE> SnmpV3AdaptorServer </CODE>是接收此请求的适配器时,才会返回此参数。否则返回null。
     * 
     * 
     * @return the local engine.
     *
     * @since 1.5
     */
    public SnmpEngine getEngine();
    /**
     * Gets the incoming request principal. This parameter is returned only if <CODE> SnmpV3AdaptorServer </CODE> is the adaptor receiving this request. Otherwise null is returned.
     * <p>
     *  获取传入请求主体。仅当<CODE> SnmpV3AdaptorServer </CODE>是接收此请求的适配器时,才会返回此参数。否则返回null。
     * 
     * 
     * @return The request principal.
     *
     * @since 1.5
     **/
    public String getPrincipal();
    /**
     * Gets the incoming request security level. This level is defined in {@link com.sun.jmx.snmp.SnmpEngine SnmpEngine}. This parameter is returned only if <CODE> SnmpV3AdaptorServer </CODE> is the adaptor receiving this request. Otherwise -1 is returned.
     * <p>
     * 获取传入请求安全级别。此级别在{@link com.sun.jmx.snmp.SnmpEngine SnmpEngine}中定义。
     * 仅当<CODE> SnmpV3AdaptorServer </CODE>是接收此请求的适配器时,才会返回此参数。否则返回-1。
     * 
     * 
     * @return The security level.
     *
     * @since 1.5
     */
    public int getSecurityLevel();
    /**
     * Gets the incoming request security model. This parameter is returned only if <CODE> SnmpV3AdaptorServer </CODE> is the adaptor receiving this request. Otherwise -1 is returned.
     * <p>
     *  获取传入请求安全模型。仅当<CODE> SnmpV3AdaptorServer </CODE>是接收此请求的适配器时,才会返回此参数。否则返回-1。
     * 
     * 
     * @return The security model.
     *
     * @since 1.5
     */
    public int getSecurityModel();
    /**
     * Gets the incoming request context name. This parameter is returned only if <CODE> SnmpV3AdaptorServer </CODE> is the adaptor receiving this request. Otherwise null is returned.
     * <p>
     *  获取传入请求上下文名称。仅当<CODE> SnmpV3AdaptorServer </CODE>是接收此请求的适配器时,才会返回此参数。否则返回null。
     * 
     * 
     * @return The context name.
     *
     * @since 1.5
     */
    public byte[] getContextName();
    /**
     * Gets the incoming request context name used by Access Control Model in order to allow or deny the access to OIDs. This parameter is returned only if <CODE> SnmpV3AdaptorServer </CODE> is the adaptor receiving this request. Otherwise null is returned.
     * <p>
     *  获取访问控制模型使用的传入请求上下文名称,以允许或拒绝对OID的访问。仅当<CODE> SnmpV3AdaptorServer </CODE>是接收此请求的适配器时,才会返回此参数。
     * 否则返回null。
     * 
     * 
     * @return The checked context name.
     *
     * @since 1.5
     */
    public byte[] getAccessContextName();

    /**
     * Returns a handle on a user allocated contextual object.
     * This contextual object is allocated through the SnmpUserDataFactory
     * on a per SNMP request basis, and is handed back to the user via
     * SnmpMibRequest (and derivative) objects. It is never accessed by
     * the system, but might be handed back in multiple threads. It is thus
     * the user responsibility to make sure he handles this object in a
     * thread safe manner.
     * <p>
     *  返回用户分配的上下文对象上的句柄。此上下文对象通过SnmpUserDataFactory在每个SNMP请求的基础上分配,并通过SnmpMibRequest(和派生)对象交还给用户。
     * 它从不被系统访问,但可能在多个线程中被交回。因此,用户有责任确保以线程安全的方式处理此对象。
     * 
     */
    public Object getUserData();

    /**
     * Returns the varbind index that should be embedded in an
     * SnmpStatusException for this particular varbind.
     * This does not necessarily correspond to the "real"
     * index value that will be returned in the result PDU.
     *
     * <p>
     *  返回应嵌入此特定varbind的SnmpStatusException中的varbind索引。这不一定对应于将在结果PDU中返回的"真实"索引值。
     * 
     * 
     * @param varbind The varbind for which the index value is
     *        querried. Note that this varbind <b>must</b> have
     *        been obtained from the enumeration returned by
     *        <CODE>getElements()</CODE>, or from the vector
     *        returned by <CODE>getSublist()</CODE>.
     *
     * @return The varbind index that should be embedded in an
     *         SnmpStatusException for this particular varbind.
     */
    public int getVarIndex(SnmpVarBind varbind);

    /**
     * Adds a varbind to this request sublist. This method is used for
     * internal purposes and you should never need to call it directly.
     *
     * <p>
     * 向此请求子列表中添加varbind。这个方法用于内部目的,你永远不需要直接调用它。
     * 
     * 
     * @param varbind The varbind to be added in the sublist.
     *
     */
    public void addVarBind(SnmpVarBind varbind);


    /**
     * Returns the number of elements (varbinds) in this request sublist.
     *
     * <p>
     *  返回此请求子列表中的元素(varbinds)的数量。
     * 
     * 
     * @return The number of elements in the sublist.
     *
     **/
    public int getSize();
    /**
     * Returns the SNMP PDU attached to the request.
     * <p>
     *  返回附加到请求的SNMP PDU。
     * 
     * @return The SNMP PDU.
     *
     * @since 1.5
     **/
    public SnmpPdu getPdu();
}
