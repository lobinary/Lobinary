/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package org.ietf.jgss;

import java.net.InetAddress;
import java.util.Arrays;

/**
 * This class encapsulates the concept of caller-provided channel
 * binding information. Channel bindings are used to strengthen the
 * quality with which peer entity authentication is provided during
 * context establishment.  They enable the GSS-API callers to bind the
 * establishment of the security context to relevant characteristics
 * like addresses or to application specific data.<p>
 *
 * The caller initiating the security context must determine the
 * appropriate channel binding values to set in the GSSContext object.
 * The acceptor must provide an identical binding in order to validate
 * that received tokens possess correct channel-related characteristics.<p>
 *
 * Use of channel bindings is optional in GSS-API. ChannelBinding can be
 * set for the {@link GSSContext GSSContext} using the {@link
 * GSSContext#setChannelBinding(ChannelBinding) setChannelBinding} method
 * before the first call to {@link GSSContext#initSecContext(byte[], int, int)
 * initSecContext} or {@link GSSContext#acceptSecContext(byte[], int, int)
 * acceptSecContext} has been performed.  Unless the <code>setChannelBinding</code>
 * method has been used to set the ChannelBinding for a GSSContext object,
 * <code>null</code> ChannelBinding will be assumed. <p>
 *
 * Conceptually, the GSS-API concatenates the initiator and acceptor
 * address information, and the application supplied byte array to form an
 * octet string.  The mechanism calculates a MIC over this octet string and
 * binds the MIC to the context establishment token emitted by
 * <code>initSecContext</code> method of the <code>GSSContext</code>
 * interface.  The same bindings are set by the context acceptor for its
 * <code>GSSContext</code> object and during processing of the
 * <code>acceptSecContext</code> method a MIC is calculated in the same
 * way. The calculated MIC is compared with that found in the token, and if
 * the MICs differ, accept will throw a <code>GSSException</code> with the
 * major code set to {@link GSSException#BAD_BINDINGS BAD_BINDINGS}, and
 * the context will not be established. Some mechanisms may include the
 * actual channel binding data in the token (rather than just a MIC);
 * applications should therefore not use confidential data as
 * channel-binding components.<p>
 *
 *  Individual mechanisms may impose additional constraints on addresses
 *  that may appear in channel bindings.  For example, a mechanism may
 *  verify that the initiator address field of the channel binding
 *  contains the correct network address of the host system.  Portable
 *  applications should therefore ensure that they either provide correct
 *  information for the address fields, or omit setting of the addressing
 *  information.
 *
 * <p>
 *  这个类封装了调用者提供的通道绑定信息的概念。信道绑定用于加强在上下文建立期间提供对等实体认证的质量。它们使GSS-API调用者能够将安全上下文的建立绑定到相关特性,如地址或应用程序特定数据。<p>
 * 
 *  启动安全上下文的调用者必须确定在GSSContext对象中设置的适当的通道绑定值。接受者必须提供相同的绑定,以验证接收的令牌具有正确的信道相关特性
 * 
 *  在GSS-API中使用通道绑定是可选的。
 * 可以在首次调用{@link GSSContext#initSecContext(byte [],int,int)initSecContext}或{@link GSSontext}之前使用{@link GSSContext#setChannelBinding(ChannelBinding)setChannelBinding}
 * 方法为{@link GSSContext GSSContext}已经执行了@link GSSContext#acceptSecContext(byte [],int,int)acceptSecConte
 * xt}。
 *  在GSS-API中使用通道绑定是可选的。
 * 除非已经使用<code> setChannelBinding </code>方法为GSSContext对象设置ChannelBinding,将假设<code> null </code> ChannelB
 * inding。
 *  在GSS-API中使用通道绑定是可选的。 <p>。
 * 
 * 从概念上讲,GSS-API将启动器和接受器地址信息以及应用程序提供的字节数组连接起来,形成一个字节串。
 * 该机制计算该八位字节串上的MIC,并将MIC绑定到由<code> GSSContext </code>接口的<code> initSecContext </code>方法发出的上下文建立令牌。
 * 相同的绑定由上下文接受器为其<code> GSSContext </code>对象设置,并且在<code> acceptSecContext </code>方法的处理期间,MIC以相同的方式计算。
 * 将计算的MIC与令牌中发现的MIC进行比较,如果MIC不同,则接受将抛出一个主代码设置为{@link GSSException#BAD_BINDINGS BAD_BINDINGS}的<code> GSS
 * Exception </code>,并且上下文不会成立。
 * 相同的绑定由上下文接受器为其<code> GSSContext </code>对象设置,并且在<code> acceptSecContext </code>方法的处理期间,MIC以相同的方式计算。
 * 一些机制可以包括令牌中的实际信道绑定数据(而不仅仅是MIC);应用程序因此不应使用机密数据作为通道绑定组件。<p>。
 * 
 *  个别机制可以对可能出现在信道绑定中的地址施加额外的约束。例如,机制可以验证信道绑定的发起方地址字段包含主机系统的正确网络地址。
 * 因此,便携式应用程序应确保它们为地址字段提供正确的信息,或者省略寻址信息的设置。
 * 
 * @author Mayank Upadhyay
 * @since 1.4
 */
public class ChannelBinding {

    private InetAddress initiator;
    private InetAddress acceptor;
    private  byte[] appData;

    /**
     * Create a ChannelBinding object with user supplied address information
     * and data.  <code>null</code> values can be used for any fields which the
     * application does not want to specify.
     *
     * <p>
     * 
     * 
     * @param initAddr the address of the context initiator.
     * <code>null</code> value can be supplied to indicate that the
     * application does not want to set this value.
     * @param acceptAddr the address of the context
     * acceptor. <code>null</code> value can be supplied to indicate that
     * the application does not want to set this value.
     * @param appData application supplied data to be used as part of the
     * channel bindings. <code>null</code> value can be supplied to
     * indicate that the application does not want to set this value.
     */
    public ChannelBinding(InetAddress initAddr, InetAddress acceptAddr,
                        byte[] appData) {

        initiator = initAddr;
        acceptor = acceptAddr;

        if (appData != null) {
            this.appData = new byte[appData.length];
            java.lang.System.arraycopy(appData, 0, this.appData, 0,
                                appData.length);
        }
    }

    /**
     * Creates a ChannelBinding object without any addressing information.
     *
     * <p>
     * 使用用户提供的地址信息和数据创建ChannelBinding对象。 <code> null </code>值可用于应用程序不想指定的任何字段。
     * 
     * 
     * @param appData application supplied data to be used as part of the
     * channel bindings.
     */
    public ChannelBinding(byte[] appData) {
        this(null, null, appData);
    }

    /**
     * Get the initiator's address for this channel binding.
     *
     * <p>
     *  创建一个没有任何地址信息的ChannelBinding对象。
     * 
     * 
     * @return the initiator's address. <code>null</code> is returned if
     * the address has not been set.
     */
    public InetAddress getInitiatorAddress() {
        return initiator;
    }

    /**
     * Get the acceptor's address for this channel binding.
     *
     * <p>
     *  获取此通道绑定的启动程序地址。
     * 
     * 
     * @return the acceptor's address. null is returned if the address has
     * not been set.
     */
    public InetAddress getAcceptorAddress() {
        return acceptor;
    }

    /**
     * Get the application specified data for this channel binding.
     *
     * <p>
     *  获取此频道绑定的接受者地址。
     * 
     * 
     * @return the application data being used as part of the
     * ChannelBinding. <code>null</code> is returned if no application data
     * has been specified for the channel binding.
     */
    public byte[] getApplicationData() {

        if (appData == null) {
            return null;
        }

        byte[] retVal = new byte[appData.length];
        System.arraycopy(appData, 0, retVal, 0, appData.length);
        return retVal;
    }

    /**
     * Compares two instances of ChannelBinding.
     *
     * <p>
     *  获取该通道绑定的应用程序指定数据。
     * 
     * 
     * @param obj another ChannelBinding to compare this one with
     * @return true if the two ChannelBinding's contain
     * the same values for the initiator and acceptor addresses and the
     * application data.
     */
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (! (obj instanceof ChannelBinding))
            return false;

        ChannelBinding cb = (ChannelBinding) obj;

        if ((initiator != null && cb.initiator == null) ||
            (initiator == null && cb.initiator != null))
            return false;

        if (initiator != null && !initiator.equals(cb.initiator))
            return false;

        if ((acceptor != null && cb.acceptor == null) ||
            (acceptor == null && cb.acceptor != null))
            return false;

        if (acceptor != null && !acceptor.equals(cb.acceptor))
            return false;

        return Arrays.equals(appData, cb.appData);
    }

    /**
     * Returns a hashcode value for this ChannelBinding object.
     *
     * <p>
     *  比较两个ChannelBinding实例。
     * 
     * 
     * @return a hashCode value
     */
    public int hashCode() {
        if (initiator != null)
            return initiator.hashCode();
        else if (acceptor != null)
            return acceptor.hashCode();
        else if (appData != null)
            return new String(appData).hashCode();
        else
            return 1;
    }
}
