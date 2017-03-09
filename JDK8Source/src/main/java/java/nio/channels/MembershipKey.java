/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.channels;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.io.IOException;

/**
 * A token representing the membership of an Internet Protocol (IP) multicast
 * group.
 *
 * <p> A membership key may represent a membership to receive all datagrams sent
 * to the group, or it may be <em>source-specific</em>, meaning that it
 * represents a membership that receives only datagrams from a specific source
 * address. Whether or not a membership key is source-specific may be determined
 * by invoking its {@link #sourceAddress() sourceAddress} method.
 *
 * <p> A membership key is valid upon creation and remains valid until the
 * membership is dropped by invoking the {@link #drop() drop} method, or
 * the channel is closed. The validity of the membership key may be tested
 * by invoking its {@link #isValid() isValid} method.
 *
 * <p> Where a membership key is not source-specific and the underlying operation
 * system supports source filtering, then the {@link #block block} and {@link
 * #unblock unblock} methods can be used to block or unblock multicast datagrams
 * from particular source addresses.
 *
 * <p>
 *  表示互联网协议(IP)组播组成员资格的令牌。
 * 
 *  会员密钥可以表示用于接收发送到组的所有数据报的成员资格,或者可以是源特定的,这意味着它表示仅接收来自特定源地址的数据报的成员资格。
 * 是否会话密钥是源特定的可以通过调用其{@link #sourceAddress()sourceAddress}方法来确定。
 * 
 *  <p>成员资格键在创建时有效,并保持有效,直到通过调用{@link #drop()drop}方法删除成员资格,或者关闭了渠道。
 * 可以通过调用其{@link #isValid()isValid}方法来测试成员关键字的有效性。
 * 
 *  <p>如果成员资格密钥不是源特定的,并且底层操作系统支持源过滤,那么{@link #block block}和{@link #unblock unblock}方法可用于阻止或取消阻止特定组播数据报源地
 * 址。
 * 
 * 
 * @see MulticastChannel
 *
 * @since 1.7
 */
public abstract class MembershipKey {

    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例。
     * 
     */
    protected MembershipKey() {
    }

    /**
     * Tells whether or not this membership is valid.
     *
     * <p> A multicast group membership is valid upon creation and remains
     * valid until the membership is dropped by invoking the {@link #drop() drop}
     * method, or the channel is closed.
     *
     * <p>
     *  指出此会员资格是否有效。
     * 
     *  <p>多播组成员资格在创建时有效,并且在调用{@link #drop()drop}方法删除成员资格之前保持有效,或者频道已关闭。
     * 
     * 
     * @return  {@code true} if this membership key is valid, {@code false}
     *          otherwise
     */
    public abstract boolean isValid();

    /**
     * Drop membership.
     *
     * <p> If the membership key represents a membership to receive all datagrams
     * then the membership is dropped and the channel will no longer receive any
     * datagrams sent to the group. If the membership key is source-specific
     * then the channel will no longer receive datagrams sent to the group from
     * that source address.
     *
     * <p> After membership is dropped it may still be possible to receive
     * datagrams sent to the group. This can arise when datagrams are waiting to
     * be received in the socket's receive buffer. After membership is dropped
     * then the channel may {@link MulticastChannel#join join} the group again
     * in which case a new membership key is returned.
     *
     * <p> Upon return, this membership object will be {@link #isValid() invalid}.
     * If the multicast group membership is already invalid then invoking this
     * method has no effect. Once a multicast group membership is invalid,
     * it remains invalid forever.
     * <p>
     *  删除成员资格。
     * 
     * <p>如果成员资格密钥表示接收所有数据报的成员资格,则成员资格将被丢弃,并且该信道将不再接收发送到该组的任何数据报。如果成员资格密钥是源特定的,则信道将不再接收从该源地址发送到组的数据报。
     * 
     *  <p>成员资格被删除后,仍然可以接收发送到组的数据报。当数据报等待在套接字的接收缓冲区中被接收时,就会出现这种情况。
     * 在成员资格被丢弃之后,信道可以再次{@link多播信道#加入join}该组,在这种情况下返回新的成员资格密钥。
     * 
     *  <p>返回后,此成员资格对象将为{@link #isValid()invalid}。如果多播组成员资格已经无效,则调用此方法不起作用。一旦多播组成员资格无效,它将永远保持无效。
     * 
     */
    public abstract void drop();

    /**
     * Block multicast datagrams from the given source address.
     *
     * <p> If this membership key is not source-specific, and the underlying
     * operating system supports source filtering, then this method blocks
     * multicast datagrams from the given source address. If the given source
     * address is already blocked then this method has no effect.
     * After a source address is blocked it may still be possible to receive
     * datagrams from that source. This can arise when datagrams are waiting to
     * be received in the socket's receive buffer.
     *
     * <p>
     *  阻止来自给定源地址的组播数据报。
     * 
     *  <p>如果此成员资格密钥不是源特定的,并且底层操作系统支持源过滤,则此方法将阻止来自给定源地址的组播数据报。如果给定的源地址已经被阻止,则此方法不起作用。
     * 在源地址被阻止之后,仍然可以从该源接收数据报。当数据报等待在套接字的接收缓冲区中被接收时,就会出现这种情况。
     * 
     * 
     * @param   source
     *          The source address to block
     *
     * @return  This membership key
     *
     * @throws  IllegalArgumentException
     *          If the {@code source} parameter is not a unicast address or
     *          is not the same address type as the multicast group
     * @throws  IllegalStateException
     *          If this membership key is source-specific or is no longer valid
     * @throws  UnsupportedOperationException
     *          If the underlying operating system does not support source
     *          filtering
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract MembershipKey block(InetAddress source) throws IOException;

    /**
     * Unblock multicast datagrams from the given source address that was
     * previously blocked using the {@link #block(InetAddress) block} method.
     *
     * <p>
     * 从先前使用{@link #block(InetAddress)块}方法阻塞的给定源地址解除阻止多播数据报。
     * 
     * 
     * @param   source
     *          The source address to unblock
     *
     * @return  This membership key
     *
     * @throws  IllegalStateException
     *          If the given source address is not currently blocked or the
     *          membership key is no longer valid
     */
    public abstract MembershipKey unblock(InetAddress source);

    /**
     * Returns the channel for which this membership key was created. This
     * method will continue to return the channel even after the membership
     * becomes {@link #isValid invalid}.
     *
     * <p>
     *  返回为其创建此会员密钥的渠道。即使在成员资格成为{@link #isValid invalid}之后,此方法仍将继续返回通道。
     * 
     * 
     * @return  the channel
     */
    public abstract MulticastChannel channel();

    /**
     * Returns the multicast group for which this membership key was created.
     * This method will continue to return the group even after the membership
     * becomes {@link #isValid invalid}.
     *
     * <p>
     *  返回为其创建此成员资格密钥的多播组。即使成员资格成为{@link #isValid invalid}后,此方法仍将继续返回该组。
     * 
     * 
     * @return  the multicast group
     */
    public abstract InetAddress group();

    /**
     * Returns the network interface for which this membership key was created.
     * This method will continue to return the network interface even after the
     * membership becomes {@link #isValid invalid}.
     *
     * <p>
     *  返回为其创建此成员资格密钥的网络接口。此方法将继续返回网络接口,即使成员资格成为{@link #isValid invalid}后。
     * 
     * 
     * @return  the network interface
     */
    public abstract NetworkInterface networkInterface();

    /**
     * Returns the source address if this membership key is source-specific,
     * or {@code null} if this membership is not source-specific.
     *
     * <p>
     *  如果此会员密钥是源特定的,则返回源地址,如果此成员资格不是源特定的,则返回{@code null}。
     * 
     * @return  The source address if this membership key is source-specific,
     *          otherwise {@code null}
     */
    public abstract InetAddress sourceAddress();
}
