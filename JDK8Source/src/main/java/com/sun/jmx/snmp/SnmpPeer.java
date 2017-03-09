/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
// Copyright (c) 1995-96 by Cisco Systems, Inc.

package com.sun.jmx.snmp;

// java imports
//
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.Serializable;

/**
 * Holds information about an SNMP agent. This information is used to communicate with the agent.
 * These are the IP address, port number, SNMP parameters, and peer channel parameters
 * (such as the maximum request packet size, maximum number of variable bindings in a packet, retries, and timeouts).
 * Changing these would affect all active requests.
 * <P>
 * The class contains the following properties:
 * <UL>
 * <LI><B>destPort</B>: port number of the destination host.
 * <BR>The default port is <B>161</B>.
 *
 * <LI><B>maxVarBindLimit</B>: maximum number of OIDs which can be encoded in a single request packet.
 * This is set by the user.
 * <BR>A request which contains more than this limit will be automatically split into multiple requests.
 * Typically used when multiplexing requests.
 * <BR>The default value is 25.
 *
 * <LI><B>maxSnmpPacketSize</B>: maximum packet size of the request PDU.
 * This can be set by the user.
 * <BR> If the request crosses this limit while encoding, the request is automatically split into
 * multiple small requests. Each of these requests will again be within this limit.
 * <BR>The default value is (2 * 1024).
 *
 * <LI><B>maxTries</B>: number of times to try before giving up.
 * <BR>The default number is <B>3</B>.
 *
 * <LI><B>timeout</B>: amount of time to wait for a response from the
 * peer.  If this amount of time passes without a response, and if the
 * <B>maxTries</B> value has not been exceeded, the request will be
 * resent.  <BR>The default amount of time is <B>3000
 * milliseconds</B>.
 *
 * <LI><B>snmpParameters</B>: SNMP parameters to be used when communicating with the agent.
 * The parameters contain the protocol version and security information (the parameters can be shared amongst several peers).
 *
 *</UL>
 * JavaBean compliant getters and setters allow the properties listed above to be modified.
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  保存有关SNMP代理的信息。此信息用于与代理通信。这些是IP地址,端口号,SNMP参数和对等通道参数(例如最大请求数据包大小,数据包中的变量绑定的最大数量,重试和超时)。更改这些会影响所有活动请求。
 * <P>
 *  该类包含以下属性：
 * <UL>
 *  <LI> <B> destPort </B>：目的主机的端口号。 <BR>默认端口为<B> 161 </B>。
 * 
 *  <LI> <B> maxVarBindLimit </B>：可以在单个请求数据包中编码的OID的最大数量。这是由用户设置的。 <BR>包含超过此限制的请求将自动拆分为多个请求。通常在复用请求时使用。
 *  <BR>默认值为25。
 * 
 *  <LI> <B> maxSnmpPacketSize </B>：请求PDU的最大包大小。这可以由用户设置。 <BR>如果请求在编码时跨越此限制,则请求会自动拆分为多个小请求。
 * 这些请求中的每一个都将再次在此限制内。 <BR>默认值为(2 * 1024)。
 * 
 *  <LI> <B> maxTries </B>：放弃之前尝试的次数。 <BR>默认号码为<B> 3 </B>。
 * 
 * <LI> <B> timeout </B>：等待来自对等体的响应的时间量。如果这段时间没有响应,并且未超过<B> maxTries </B>值,则会重新发送请求。
 *  <BR>默认时间为<B> 3000毫秒</B>。
 * 
 *  <LI> <B> snmpParameters </B>：与代理通信时要使用的SNMP参数。参数包含协议版本和安全信息(参数可以在几个对等体之间共享)。
 * 
 * /UL>
 *  符合JavaBean的getter和setter允许修改上面列出的属性。
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @see com.sun.jmx.snmp.SnmpParameters
 */

public class SnmpPeer implements Serializable {
    private static final long serialVersionUID = -5554565062847175999L;

    // PUBLIC VARIABLES
    //-----------------

    /**
     * The default SNMP packet size of an SNMP request (2 * 1024).
     * <BR>The maximum size is initially set to Ethernet maximum transfer unit (MTU).
     * <p>
     *  SNMP请求的默认SNMP数据包大小(2 * 1024)。 <BR>最大大小最初设置为以太网最大传输单位(MTU)。
     * 
     */

    public static final int defaultSnmpRequestPktSize = 2 * 1024 ;

    /**
     * The default SNMP packet size of an SNMP response (8 * 1024).
     * <BR>This will be the default size that the session socket uses when receiving a packet.
     * <p>
     *  SNMP响应的默认SNMP数据包大小(8 * 1024)。 <BR>这将是会话套接字接收数据包时使用的默认大小。
     * 
     */
    public static final int defaultSnmpResponsePktSize = 8 * 1024 ;


    // PRIVATE VARIABLES
    //------------------

    /**
     * The maximum number of variable bindings that can be packed into a request.
     * The default value is 25.
     * <p>
     *  可以打包到请求中的变量绑定的最大数量。默认值为25。
     * 
     */
    private int maxVarBindLimit = 25 ;

    /**
     * Port number of the destination host.
     * The default port is 161.
     * <p>
     *  目标主机的端口号。默认端口为161。
     * 
     */
    private int portNum = 161 ;

    /**
     * Number of times to try before giving up.
     * The default number is 3.
     * <p>
     *  放弃之前尝试的次数。默认值为3。
     * 
     */
    private int maxTries = 3 ;

    /**
     * The amount of time to wait for a response from the peer.
     * The default amount of time is 3000 millisec.
     * <p>
     *  等待来自对等体的响应的时间量。默认时间为3000毫秒。
     * 
     */
    private int timeout = 3000;

    /**
     * The PDU factory. The default factory is an instance of <CODE>SnmpPduFactoryBER</CODE>.
     * <p>
     *  PDU工厂。默认工厂是<CODE> SnmpPduFactoryBER </CODE>的实例。
     * 
     */
    private SnmpPduFactory pduFactory = new SnmpPduFactoryBER() ;

    /**
     * The maximum round trip time for a packet with the peer.
     * <p>
     *  与对等体的数据包的最大往返时间。
     * 
     */
    private long _maxrtt ;
    /**
     * The minimum round trip time for a packet with the peer.
     * <p>
     *  与对等体的数据包的最小往返时间。
     * 
     */
    private long _minrtt ;
    /**
     * The average round trip time for a packet with the peer.
     * <p>
     * 与对等体的数据包的平均往返时间。
     * 
     */
    private long _avgrtt ;

    /**
     * SNMP parameters for this peer are valid for all requests using this peer.
     * <p>
     *  此对等体的SNMP参数对使用此对等体的所有请求有效。
     * 
     * 
     * @see com.sun.jmx.snmp.SnmpParameters
     */
    private SnmpParams _snmpParameter = new SnmpParameters() ;

    /**
     * Internet address of the peer to be used when communicating with the peer.
     * <p>
     *  与对等体通信时要使用的对等体的Internet地址。
     * 
     */
    private InetAddress _devAddr = null ;

    /**
     * Maximum packet size of the request PDU.  This can be set by the user.
     * If the request crosses this limit while encoding, the request is automatically split
     * into multiple small request. Each of these requests will again be within this limit.
     * The default value is (2 * 1024).
     * <p>
     *  请求PDU的最大包大小。这可以由用户设置。如果请求在编码时跨越此限制,则请求会自动拆分为多个小请求。这些请求中的每一个都将再次在此限制内。默认值为(2 * 1024)。
     * 
     */
    private int maxSnmpPacketSize = defaultSnmpRequestPktSize ;

    /**
     * List of alternate addresses.
     * <p>
     *  备用地址列表。
     * 
     */
    InetAddress _devAddrList[] = null ;

    /**
     * The index of address currently being used.
     * <p>
     *  当前正在使用的地址索引。
     * 
     */
    int _addrIndex = 0 ;


    private boolean customPduFactory = false;

    // CONSTRUCTORS
    //-------------

    /**
     * Creates an SNMP peer object for a device. The default port is 161.
     * <p>
     *  为设备创建SNMP对等体对象。默认端口为161。
     * 
     * 
     * @param host The peer name.
     * @exception UnknownHostException If the host name cannot be resolved.
     */
    public SnmpPeer(String host) throws UnknownHostException {
        this(host, 161) ;
    }

    /**
     * Creates an SNMP peer object for a device. The default port is 161.
     * <p>
     *  为设备创建SNMP对等体对象。默认端口为161。
     * 
     * 
     * @param netaddr The peer <CODE>InetAddress</CODE>.
     * @param port The port number.
     */
    public SnmpPeer(InetAddress netaddr, int port) {
        _devAddr = netaddr ;
        portNum = port;
   }

    /**
     * Creates an SNMP peer object for a device. The default port is 161.
     * <p>
     *  为设备创建SNMP对等体对象。默认端口为161。
     * 
     * 
     * @param netaddr The peer <CODE>InetAddress</CODE>.
     */
    public SnmpPeer(InetAddress netaddr) {
        _devAddr = netaddr ;
     }

    /**
     * Creates an SNMP peer object for a device with the specified port.
     * <p>
     *  为具有指定端口的设备创建SNMP对等体对象。
     * 
     * 
     * @param host The peer name.
     * @param port The port number.
     * @exception UnknownHostException If the host name cannot be resolved.
     */
    public SnmpPeer(String host, int port) throws UnknownHostException {
        useIPAddress(host) ;
        portNum = port;
    }


    // PUBLIC METHODS
    //---------------

    /**
     * Sets a specific IP address to which the peer will communicate.
     * Typically used to set an alternate IP address or a specific address which is known to respond to requests.
     * The IP address <CODE>String</CODE> can either be a machine name, such as <CODE>ibiza</CODE>,
     * or a <CODE>String</CODE> representing its IP address, such as "206.26.48.100".
     * <p>
     *  设置对等体将与之通信的特定IP地址。通常用于设置备用IP地址或已知响应请求的特定地址。
     *  IP地址<CODE> String </CODE>可以是机器名称,例如<CODE> ibiza </CODE>,也可以是表示其IP地址的<CODE> String </CODE>,例如"206.26.
     * 48.100 "。
     *  设置对等体将与之通信的特定IP地址。通常用于设置备用IP地址或已知响应请求的特定地址。
     * 
     * 
     * @param ipaddr Dot formatted IP address or logical host name.
     * @exception UnknownHostException If the host name cannot be resolved.
     */
    final public synchronized void useIPAddress(String ipaddr) throws UnknownHostException {
        _devAddr = InetAddress.getByName(ipaddr) ;
    }

    /**
     * Returns the dot-formatted IP address string (for example 171.69.220.224).
     * Useful when you want to know which IP address is used
     * when the address was resolved using a DNS name.
     * <p>
     *  返回点格式的IP地址字符串(例如171.69.220.224)。当您想知道当使用DNS名称解析地址时使用哪个IP地址时有用。
     * 
     * 
     * @return The dot-formatted IP address being used.
     */
    final public synchronized String ipAddressInUse() {
        byte [] adr = _devAddr.getAddress() ;
        return
            (adr[0]&0xFF) + "." + (adr[1]&0xFF) + "." +
            (adr[2]&0xFF) + "." + (adr[3]&0xFF);
    }

    /**
     * Specifies the list of addresses to be used.  When a host is not responding
     * the user can switch to the next address by calling <CODE>useNextAddress</CODE>.
     * <p>
     * 指定要使用的地址列表。当主机没有响应时,用户可以通过调用<CODE> useNextAddress </CODE>切换到下一个地址。
     * 
     * 
     * @param adrList The list of <CODE>InetAddresses</CODE>.
     */
    final public synchronized void useAddressList(InetAddress adrList[]) {
        _devAddrList = (adrList != null) ? adrList.clone() : null;
        _addrIndex = 0 ;
        useNextAddress() ;
    }

    /**
     * Causes all subsequent requests to go to the new address
     * obtained from the specified list of alternate addresses.
     * If it reaches the end of list, it starts again at the first address.
     * <p>
     *  使所有后续请求转到从指定的备用地址列表获取的新地址。如果它到达列表的结尾,它将再次从第一个地址开始。
     * 
     */
    final public synchronized void useNextAddress() {
        if (_devAddrList == null)
            return ;
/* NPCTE fix for bug 4486059, esc 0 MR 03-August-2001 */
/*      if (_addrIndex > _devAddrList.length) */
        if (_addrIndex > _devAddrList.length-1)
/* end of NPCTE fix for bugid 4486059 */
            _addrIndex = 0 ;
        _devAddr = _devAddrList[_addrIndex++] ;
    }

    /**
     * Determines whether an SNMP <CODE>set</CODE> operation is allowed with this
     * peer object. For now it just makes sure a parameter is configured for a write operation.
     * <p>
     *  确定此对等体对象是否允许SNMP <CODE>设置</CODE>操作。现在它只是确保为写操作配置了一个参数。
     * 
     * 
     * @return <CODE>true</CODE> if SNMP <CODE>set</CODE> is allowed and the parameter is configured,
     * <CODE>false</CODE> otherwise.
     */
    public boolean allowSnmpSets() {
        return _snmpParameter.allowSnmpSets() ;
    }

    /**
     * Gets the list of alternate <CODE>InetAddress</CODE> configured for this peer.
     * <p>
     *  获取为此对等体配置的备用<CODE> InetAddress </CODE>列表。
     * 
     * 
     * @return The <CODE>InetAddress</CODE> of the peer.
     */
    final public InetAddress[] getDestAddrList() {
        return _devAddrList == null ? null : _devAddrList.clone();
    }

    /**
     * Gets the <CODE>InetAddress</CODE> object for this peer.
     * <p>
     *  获取此对等体的<CODE> InetAddress </CODE>对象。
     * 
     * 
     * @return The <CODE>InetAddress</CODE> of the peer.
     */
    final public InetAddress getDestAddr() {
        return _devAddr ;
    }

    /**
     * Gets the destination port number of the peer to which SNMP requests are to be sent.
     * <p>
     *  获取要发送SNMP请求的对等体的目标端口号。
     * 
     * 
     * @return The destination port number.
     */
    final public int getDestPort() {
        return portNum ;
    }

    /**
     * Changes the port address of the destination for the request.
     * <p>
     *  更改请求的目标的端口地址。
     * 
     * 
     * @param newPort The destination port.
     */
    final public synchronized void setDestPort(int newPort) {
        portNum = newPort ;
    }

    /**
     * Gets the timeout to wait for a response from the peer.
     * <p>
     *  获取等待来自对等体的响应的超时。
     * 
     * 
     * @return The value of the timeout property.
     */
    final public int getTimeout() {
        return timeout;
    }

    /**
     * Changes the timeout to wait for a response from the peer.
     * <p>
     *  更改超时以等待来自对等体的响应。
     * 
     * 
     * @param newTimeout The timeout (in milliseconds).
     */
    final public synchronized void setTimeout(int newTimeout) {
        if (newTimeout < 0)
            throw new IllegalArgumentException();
        timeout= newTimeout;
    }

    /**
     * Gets the number of times to try before giving up.
     * <p>
     *  获取放弃之前尝试的次数。
     * 
     * 
     * @return The maximun number of tries.
     */
    final public int getMaxTries() {
        return maxTries;
    }

    /**
     * Changes the maximun number of times to try before giving up.
     * <p>
     *  改变最大尝试次数之前放弃。
     * 
     * 
     * @param newMaxTries The maximun number of tries.
     */
    final public synchronized void setMaxTries(int newMaxTries) {
        if (newMaxTries < 0)
            throw new IllegalArgumentException();
        maxTries= newMaxTries;
    }

    /**
     * Gets the name specified in the constructor while creating this object.
     * <p>
     *  创建此对象时获取构造函数中指定的名称。
     * 
     * 
     * @return The name of the host as specified while creating this object.
     */
    final public String getDevName() {
        return getDestAddr().getHostName() ;
    }

    /**
     * Returns the <CODE>String</CODE> representation for this <CODE>SnmpPeer</CODE>.
     * <p>
     *  返回此<CODE> SnmpPeer </CODE>的<CODE> String </CODE>表示形式。
     * 
     * 
     * @return The <CODE>String</CODE> representation.
     */
    @Override
    public String toString() {
        // For security and performance reasons we don't call getHostName here
        // Use getDevName() explicitly when necessary.
        return "Peer/Port : " + getDestAddr().getHostAddress() + "/" + getDestPort() ;
    }

    /**
     * Gets the maximum number of variable bindings that can be sent to a peer.
     * <p>
     *  获取可发送给对等体的变量绑定的最大数量。
     * 
     * 
     * @return The maximum variable bindings that can be encoded into a request packet.
     */
    final public synchronized int getVarBindLimit() {
        return maxVarBindLimit ;
    }

    /**
     * Configures the maximum number of variable bindings that can be sent to a peer.
     * <p>
     *  配置可发送给对等体的变量绑定的最大数量。
     * 
     * 
     * @param limit The desired limit.
     */
    final public synchronized void setVarBindLimit(int limit) {
        maxVarBindLimit = limit ;
    }

    /**
     * Sets the <CODE>SnmpParams</CODE> object associated with the peer.
     * <p>
     *  设置与对等关联的<CODE> SnmpParams </CODE>对象。
     * 
     * 
     * @param params The desired parameters.
     */
    public void setParams(SnmpParams params) {
        _snmpParameter = params;
    }
    /**
     * Gets the <CODE>SnmpParams</CODE> object associated with the peer.
     * <p>
     *  获取与对等关联的<CODE> SnmpParams </CODE>对象。
     * 
     * 
     * @return The associated parameters.
     */
    public SnmpParams getParams() {
        return _snmpParameter;
    }

    /**
     * Gets the maximum request packet size that is currently used.
     * <p>
     * 获取当前使用的最大请求数据包大小。
     * 
     * 
     * @return The maximum request packet size.
     */
    final public int getMaxSnmpPktSize() {
        return maxSnmpPacketSize ;
    }

    /**
     * Configures the maximum packet size that can be used when generating an SNMP request.
     * <p>
     *  配置生成SNMP请求时可以使用的最大数据包大小。
     * 
     * 
     * @param newsize The desired packet size.
     */
    final public synchronized void setMaxSnmpPktSize(int newsize) {
        maxSnmpPacketSize = newsize ;
    }

    boolean isCustomPduFactory() {
        return customPduFactory;
    }

    /**
     * Finalizer of the <CODE>SnmpPeer</CODE> objects.
     * This method is called by the garbage collector on an object
     * when garbage collection determines that there are no more references to the object.
     * <P>Sets all the references to this SNMP peer object to <CODE>null</CODE>.
     * <p>
     *  <CODE> SnmpPeer </CODE>对象的终结器。当垃圾回收确定没有对对象的更多引用时,垃圾收集器在对象上调用此方法。
     *  <P>将对此SNMP对等体对象的所有引用设置为<CODE> null </CODE>。
     * 
     */
    @Override
    protected void finalize() {
        _devAddr = null ;
        _devAddrList = null ;
        _snmpParameter = null ;
    }

    /**
     * Gets the minimum round trip time for a packet with the peer.
     * <p>
     *  获取与对等体的数据包的最小往返时间。
     * 
     * 
     * @return The minimum round trip time for a packet with the peer.
     */
    public long getMinRtt() {
        return _minrtt ;
    }

    /**
     * Gets the maximum round trip time for a packet with the peer.
     * <p>
     *  获取与对等体的数据包的最大往返时间。
     * 
     * 
     * @return The maximum round trip time for a packet with the peer.
     */
    public long getMaxRtt() {
        return _maxrtt ;
    }

    /**
     * Gets the average round trip time for a packet with the peer.
     * <p>
     *  获取与对等体的数据包的平均往返时间。
     * 
     * @return The average round trip time for a packet with the peer.
     */
    public long getAvgRtt() {
        return _avgrtt ;
    }


    // PRIVATE METHODS
    //----------------

    private void updateRttStats(long tm) {
        if (_minrtt > tm)
            _minrtt = tm ;
        else if (_maxrtt < tm)
            _maxrtt = tm ;
        else
            _avgrtt = tm ;  // to do later.
    }
}
