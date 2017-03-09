/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import com.sun.jmx.snmp.SnmpDefinitions;
import com.sun.jmx.snmp.SnmpStatusException;


/**
 * Contains a set of resources that are used by while sending or receiving
 * packets to and from an <CODE>SnmpPeer</CODE>. An <CODE>SnmpPeer</CODE> can
 * be configured explicitly to use a specific <CODE>SnmpParameter</CODE>.
 * A number of <CODE>SnmpPeer</CODE> objects can share a single parameter
 * object.
 * <P>
 * <B>Note</B>: Changing values for an <CODE>SnmpParameter</CODE> object
 * affects all <CODE>SnmpPeer</CODE> objects that share the parameter object.
 *
 * <p>
 *  包含在向<CODE> SnmpPeer </CODE>发送或接收数据包时使用的一组资源。
 * 可以显式配置<CODE> SnmpPeer </CODE>以使用特定的<CODE> SnmpParameter </CODE>。
 * 许多<CODE> SnmpPeer </CODE>对象可以共享单个参数对象。
 * <P>
 *  <B>注意</B>：更改<CODE> SnmpParameter </CODE>对象的值会影响共享参数对象的所有<CODE> SnmpPeer </CODE>对象。
 * 
 * 
 * @see com.sun.jmx.snmp.SnmpPeer
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 */


public class SnmpParameters extends SnmpParams implements Cloneable, Serializable {
    private static final long serialVersionUID = -1822462497931733790L;

    /**
     * Creates an <CODE>SnmpParameters</CODE> object with defaults set up.
     * By default, <CODE>set</CODE> operations are not allowed, the read community and
     * the inform community strings to use is "public" and the SNMP version is V1.
     * <p>
     *  创建具有默认设置的<CODE> SnmpParameters </CODE>对象。
     * 默认情况下,不允许执行<CODE> set </CODE>操作,要使用的读取社区和通知社区字符串为"public",SNMP版本为V1。
     * 
     */
    public SnmpParameters() {
        _readCommunity = defaultRdCommunity ;
        _informCommunity = defaultRdCommunity ;
    }

    /**
     * Creates an <CODE>SnmpParameters</CODE> object.
     * This constructor allows the specification of the read/write community strings.
     * The inform community string to use is "public".
     *
     * <p>
     *  创建<CODE> SnmpParameters </CODE>对象。此构造函数允许指定读/写团体字符串。要使用的通知社区字符串是"public"。
     * 
     * 
     * @param rdc community string to use for <CODE>get</CODE> operations.
     * @param wrc community string to use for <CODE>set</CODE> operations.
     */
    public SnmpParameters(String rdc, String wrc) {
        _readCommunity = rdc ;
        _writeCommunity = wrc ;
        _informCommunity = defaultRdCommunity ;
    }

    /**
     * Creates an <CODE>SnmpParameters</CODE> object.
     * This constructor allows the specification of the read/write/inform community strings.
     *
     * <p>
     *  创建<CODE> SnmpParameters </CODE>对象。此构造函数允许指定读/写/通知团体字符串。
     * 
     * 
     * @param rdc community string to use for <CODE>get</CODE> operations.
     * @param wrc community string to use for <CODE>set</CODE> operations.
     * @param inform community string to use for <CODE>inform</CODE> requests.
     */
    public SnmpParameters(String rdc, String wrc, String inform) {
        _readCommunity = rdc ;
        _writeCommunity = wrc ;
        _informCommunity = inform ;
    }

    /**
     * Gets the community to be used when issuing <CODE>get</CODE> operations.
     * <p>
     *  获取在发出<CODE> get </CODE>操作时使用的社区。
     * 
     * 
     * @return The community string.
     */
    public String getRdCommunity() {
        return _readCommunity ;
    }

    /**
     * Sets the community string to use when performing <CODE>get</CODE> operations.
     * <p>
     *  设置在执行<CODE> get </CODE>操作时要使用的团体字符串。
     * 
     * 
     * @param read The community string.
     */
    public synchronized void setRdCommunity(String read) {
        if (read == null)
            _readCommunity = defaultRdCommunity ;
        else
            _readCommunity = read ;
    }

    /**
     * Gets the community to be used when issuing <CODE>set</CODE> operations.
     * <p>
     *  获取在发出<CODE>设置</CODE>操作时要使用的社区。
     * 
     * 
     * @return The community string.
     */
    public String getWrCommunity() {
        return _writeCommunity ;
    }

    /**
     * Sets the community to be used when issuing <CODE>set</CODE> operations.
     * <p>
     *  设置在发出<CODE> set </CODE>操作时要使用的团体。
     * 
     * 
     * @param write The community string.
     */
    public void setWrCommunity(String write) {
        _writeCommunity = write;
    }

    /**
     * Gets the community to be used when issuing <CODE>inform</CODE> requests.
     * <p>
     *  获取发出<CODE>通知</CODE>请求时使用的社区。
     * 
     * 
     * @return The community string.
     */
    public String getInformCommunity() {
        return _informCommunity ;
    }

    /**
     * Sets the community string to use when performing <CODE>inform</CODE> requests.
     * <p>
     * 设置在执行<CODE>通知</CODE>请求时要使用的团体字符串。
     * 
     * 
     * @param inform The community string.
     */
    public void setInformCommunity(String inform) {
        if (inform == null)
            _informCommunity = defaultRdCommunity ;
        else
            _informCommunity = inform ;
    }

    /**
     * Checks whether parameters are in place for an SNMP <CODE>set</CODE> operation.
     * <p>
     *  检查是否已为SNMP <CODE>设置</CODE>操作设置了参数。
     * 
     * 
     * @return <CODE>true</CODE> if parameters are in place, <CODE>false</CODE> otherwise.
     */
    public boolean allowSnmpSets() {
        return _writeCommunity != null ;
    }

    /**
     * Compares two objects.
     * Two <CODE>SnmpParameters</CODE> are equal if they correspond to the same protocol version,
     * read community and write community.
     * <p>
     *  比较两个对象。如果两个<CODE> SnmpParameters </CODE>对应于相同的协议版本,读取团体和写入团体,则它们是相等的。
     * 
     * 
     * @param obj The object to compare <CODE>this</CODE> with.
     * @return <CODE>true</CODE> if <CODE>this</CODE> and the specified object are equal, <CODE>false</CODE> otherwise.
     */
    @Override
    public synchronized boolean equals(Object obj) {
        if (!( obj instanceof SnmpParameters))
            return false;

        if (this == obj)
            return true ;
        SnmpParameters param = (SnmpParameters) obj ;
        if (_protocolVersion == param._protocolVersion)
            if (_readCommunity.equals(param._readCommunity))
                return true ;
        return false ;
    }

    @Override
    public synchronized int hashCode() {
        return (_protocolVersion * 31) ^ Objects.hashCode(_readCommunity);
    }

    /**
     * Clones the object and its content.
     * <p>
     *  克隆对象及其内容。
     * 
     * 
     * @return The object clone.
     */
    public synchronized Object clone() {
        SnmpParameters par = null ;
        try {
            par = (SnmpParameters) super.clone() ;
            //par._retryPolicy = _retryPolicy ;
            par._readCommunity = _readCommunity ;
            par._writeCommunity = _writeCommunity ;
            par._informCommunity = _informCommunity ;
        } catch (CloneNotSupportedException e) {
            throw new InternalError() ; // VM bug.
        }
        return par ;
    }

    /**
     * For SNMP Runtime internal use only.
     * <p>
     *  仅供SNMP Runtime内部使用。
     * 
     */
    public byte[] encodeAuthentication(int snmpCmd)
        throws SnmpStatusException {
        //
        // Returns the community string associated to the specified command.
        //
        try {
            if (snmpCmd == pduSetRequestPdu)
                return _writeCommunity.getBytes("8859_1");
            else if (snmpCmd == pduInformRequestPdu)
                return _informCommunity.getBytes("8859_1") ;
            else
                return _readCommunity.getBytes("8859_1") ;
        }catch(UnsupportedEncodingException e) {
            throw new SnmpStatusException(e.getMessage());
        }
    }

    /**
     * Specify the default community string to use for <CODE>get</CODE> operations.
     * By default, the value is "public".
     * <p>
     *  指定用于<CODE> get </CODE>操作的默认社区字符串。默认情况下,值为"public"。
     * 
     */
    final static String defaultRdCommunity = "public" ;

    /**
     * The protocol version.
     * By default, the value is SNMP version 1.
     * <p>
     *  协议版本。缺省情况下,该值为SNMP版本1。
     * 
     * 
     * @serial
     */
    private int         _protocolVersion = snmpVersionOne ;
    /**
     * The community to be used when issuing <CODE>get</CODE> operations.
     * <p>
     *  发出<CODE> get </CODE>操作时使用的社区。
     * 
     * 
     * @serial
     */
    private String      _readCommunity ;
    /**
     * The community to be used when issuing <CODE>set</CODE> operations.
     * <p>
     *  发出<CODE>设置</CODE>操作时使用的社区。
     * 
     * 
     * @serial
     */
    private String      _writeCommunity ;
    /**
     * The community to be used when issuing <CODE>inform</CODE> requests.
     * <p>
     *  发出<CODE>通知</CODE>请求时使用的社区。
     * 
     * 
     * @serial
     */
    private String      _informCommunity ;
    /**
    /* <p>
     */
    //private int               _retryPolicy ;  // not implemented as yet.
}
