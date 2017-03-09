/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.nio.channels.spi.SelectorProvider;


/**
 * A channel that can be multiplexed via a {@link Selector}.
 *
 * <p> In order to be used with a selector, an instance of this class must
 * first be <i>registered</i> via the {@link #register(Selector,int,Object)
 * register} method.  This method returns a new {@link SelectionKey} object
 * that represents the channel's registration with the selector.
 *
 * <p> Once registered with a selector, a channel remains registered until it
 * is <i>deregistered</i>.  This involves deallocating whatever resources were
 * allocated to the channel by the selector.
 *
 * <p> A channel cannot be deregistered directly; instead, the key representing
 * its registration must be <i>cancelled</i>.  Cancelling a key requests that
 * the channel be deregistered during the selector's next selection operation.
 * A key may be cancelled explicitly by invoking its {@link
 * SelectionKey#cancel() cancel} method.  All of a channel's keys are cancelled
 * implicitly when the channel is closed, whether by invoking its {@link
 * Channel#close close} method or by interrupting a thread blocked in an I/O
 * operation upon the channel.
 *
 * <p> If the selector itself is closed then the channel will be deregistered,
 * and the key representing its registration will be invalidated, without
 * further delay.
 *
 * <p> A channel may be registered at most once with any particular selector.
 *
 * <p> Whether or not a channel is registered with one or more selectors may be
 * determined by invoking the {@link #isRegistered isRegistered} method.
 *
 * <p> Selectable channels are safe for use by multiple concurrent
 * threads. </p>
 *
 *
 * <a name="bm"></a>
 * <h2>Blocking mode</h2>
 *
 * A selectable channel is either in <i>blocking</i> mode or in
 * <i>non-blocking</i> mode.  In blocking mode, every I/O operation invoked
 * upon the channel will block until it completes.  In non-blocking mode an I/O
 * operation will never block and may transfer fewer bytes than were requested
 * or possibly no bytes at all.  The blocking mode of a selectable channel may
 * be determined by invoking its {@link #isBlocking isBlocking} method.
 *
 * <p> Newly-created selectable channels are always in blocking mode.
 * Non-blocking mode is most useful in conjunction with selector-based
 * multiplexing.  A channel must be placed into non-blocking mode before being
 * registered with a selector, and may not be returned to blocking mode until
 * it has been deregistered.
 *
 *
 * <p>
 *  可以通过{@link Selector}复用的通道。
 * 
 *  <p>为了与选择器一起使用,必须首先通过{@link #register(Selector,int,Object)register}方法对此类的实例进行<i>注册</i>。
 * 此方法返回一个新的{@link SelectionKey}对象,表示通道在选择器中的注册。
 * 
 *  <p>使用选择器注册后,频道将保持注册状态,直到<i>取消注册</i>。这涉及解除由选择器分配给信道的任何资源。
 * 
 *  <p>频道不能直接取消注册;相反,表示其注册的密钥必须<i>取消</i>。取消密钥请求在选择器的下一个选择操作期间取消注册通道。
 * 可以通过调用其{@link SelectionKey#cancel()cancel}方法显式地取消键。
 * 当通道关闭时,无论是通过调用其{@link Channel#close close}方法还是通过中断在I / O操作中阻塞的线程,通道的所有键都会被隐藏。
 * 
 *  <p>如果选择器本身关闭,则通道将被取消注册,并且表示其注册的密钥将无效,无需进一步延迟。
 * 
 *  <p>频道最多可与任何特定选择器注册一次。
 * 
 * <p>可以通过调用{@link #isRegistered isRegistered}方法来确定信道是否向一个或多个选择器注册。
 * 
 *  <p>可选择的频道可安全地用于多个并行线程。 </p>
 * 
 *  <a name="bm"> </a> <h2>阻止模式</h2>
 * 
 *  可选信道处于阻塞</i>模式或非阻塞</i>模式。在阻塞模式下,在通道上调用的每个I / O操作都将阻塞,直到完成。
 * 在非阻塞模式下,I / O操作将永远不会阻塞,并且可能传输比请求的字节少或可能没有字节。可选信道的阻塞模式可以通过调用其{@link #isBlocking isBlocking}方法来确定。
 * 
 *  <p>新建的可选频道始终处于阻塞模式。非阻塞模式与基于选择器的复用结合是最有用的。通道必须在注册到选择器之前置于非阻塞模式,并且在取消注册之前不能返回到阻塞模式。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 *
 * @see SelectionKey
 * @see Selector
 */

public abstract class SelectableChannel
    extends AbstractInterruptibleChannel
    implements Channel
{

    /**
     * Initializes a new instance of this class.
     * <p>
     *  初始化此类的新实例。
     * 
     */
    protected SelectableChannel() { }

    /**
     * Returns the provider that created this channel.
     *
     * <p>
     *  返回创建此渠道的提供商。
     * 
     * 
     * @return  The provider that created this channel
     */
    public abstract SelectorProvider provider();

    /**
     * Returns an <a href="SelectionKey.html#opsets">operation set</a>
     * identifying this channel's supported operations.  The bits that are set
     * in this integer value denote exactly the operations that are valid for
     * this channel.  This method always returns the same value for a given
     * concrete channel class.
     *
     * <p>
     *  返回<a href="SelectionKey.html#opsets">操作集</a>,标识此频道支持的操作。在此整数值中设置的位精确表示对此通道有效的操作。
     * 此方法对于给定的具体通道类始终返回相同的值。
     * 
     * 
     * @return  The valid-operation set
     */
    public abstract int validOps();

    // Internal state:
    //   keySet, may be empty but is never null, typ. a tiny array
    //   boolean isRegistered, protected by key set
    //   regLock, lock object to prevent duplicate registrations
    //   boolean isBlocking, protected by regLock

    /**
     * Tells whether or not this channel is currently registered with any
     * selectors.  A newly-created channel is not registered.
     *
     * <p> Due to the inherent delay between key cancellation and channel
     * deregistration, a channel may remain registered for some time after all
     * of its keys have been cancelled.  A channel may also remain registered
     * for some time after it is closed.  </p>
     *
     * <p>
     * 指出此频道目前是否已向任何选择器注册。新创建的频道未注册。
     * 
     *  <p>由于密钥取消和信道取消注册之间的固有延迟,在所有密钥取消后,信道可能会保持注册一段时间。通道在关闭后也可能保持注册一段时间。 </p>
     * 
     * 
     * @return <tt>true</tt> if, and only if, this channel is registered
     */
    public abstract boolean isRegistered();
    //
    // sync(keySet) { return isRegistered; }

    /**
     * Retrieves the key representing the channel's registration with the given
     * selector.
     *
     * <p>
     *  使用给定的选择器检索表示频道注册的键。
     * 
     * 
     * @param   sel
     *          The selector
     *
     * @return  The key returned when this channel was last registered with the
     *          given selector, or <tt>null</tt> if this channel is not
     *          currently registered with that selector
     */
    public abstract SelectionKey keyFor(Selector sel);
    //
    // sync(keySet) { return findKey(sel); }

    /**
     * Registers this channel with the given selector, returning a selection
     * key.
     *
     * <p> If this channel is currently registered with the given selector then
     * the selection key representing that registration is returned.  The key's
     * interest set will have been changed to <tt>ops</tt>, as if by invoking
     * the {@link SelectionKey#interestOps(int) interestOps(int)} method.  If
     * the <tt>att</tt> argument is not <tt>null</tt> then the key's attachment
     * will have been set to that value.  A {@link CancelledKeyException} will
     * be thrown if the key has already been cancelled.
     *
     * <p> Otherwise this channel has not yet been registered with the given
     * selector, so it is registered and the resulting new key is returned.
     * The key's initial interest set will be <tt>ops</tt> and its attachment
     * will be <tt>att</tt>.
     *
     * <p> This method may be invoked at any time.  If this method is invoked
     * while another invocation of this method or of the {@link
     * #configureBlocking(boolean) configureBlocking} method is in progress
     * then it will first block until the other operation is complete.  This
     * method will then synchronize on the selector's key set and therefore may
     * block if invoked concurrently with another registration or selection
     * operation involving the same selector. </p>
     *
     * <p> If this channel is closed while this operation is in progress then
     * the key returned by this method will have been cancelled and will
     * therefore be invalid. </p>
     *
     * <p>
     *  使用给定的选择器注册此通道,返回选择键。
     * 
     *  <p>如果此频道目前已在指定的选择器中注册,则会传回表示该注册的选择键。
     * 键的兴趣集将改为<tt> ops </tt>,如同通过调用{@link SelectionKey#interestOps(int)interestOps(int)}方法。
     * 如果<tt> att </tt>参数不是<tt> null </tt>,则密钥的附件将被设置为该值。如果密钥已取消,则将抛出{@link CancelledKeyException}。
     * 
     *  <p>否则,此频道尚未向给定选择器注册,因此已注册,并返回生成的新密钥。键的初始兴趣集将为<tt> ops </tt>,其附件将为<tt> att </tt>。
     * 
     * <p>此方法可能随时被调用。
     * 如果在此方法或{@link #configureBlocking(boolean)configureBlocking}方法的另一个调用正在进行时调用此方法,则它将首先阻塞,直到其他操作完成。
     * 此方法然后将在选择器的键集合上同步,因此如果与涉及相同选择器的另一注册或选择操作同时调用,则该方法可以阻塞。 </p>。
     * 
     *  <p>如果此通道在此操作进行期间关闭,则此方法返回的键将被取消,因此将无效。 </p>
     * 
     * 
     * @param  sel
     *         The selector with which this channel is to be registered
     *
     * @param  ops
     *         The interest set for the resulting key
     *
     * @param  att
     *         The attachment for the resulting key; may be <tt>null</tt>
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  ClosedSelectorException
     *          If the selector is closed
     *
     * @throws  IllegalBlockingModeException
     *          If this channel is in blocking mode
     *
     * @throws  IllegalSelectorException
     *          If this channel was not created by the same provider
     *          as the given selector
     *
     * @throws  CancelledKeyException
     *          If this channel is currently registered with the given selector
     *          but the corresponding key has already been cancelled
     *
     * @throws  IllegalArgumentException
     *          If a bit in the <tt>ops</tt> set does not correspond to an
     *          operation that is supported by this channel, that is, if
     *          {@code set & ~validOps() != 0}
     *
     * @return  A key representing the registration of this channel with
     *          the given selector
     */
    public abstract SelectionKey register(Selector sel, int ops, Object att)
        throws ClosedChannelException;
    //
    // sync(regLock) {
    //   sync(keySet) { look for selector }
    //   if (channel found) { set interest ops -- may block in selector;
    //                        return key; }
    //   create new key -- may block somewhere in selector;
    //   sync(keySet) { add key; }
    //   attach(attachment);
    //   return key;
    // }

    /**
     * Registers this channel with the given selector, returning a selection
     * key.
     *
     * <p> An invocation of this convenience method of the form
     *
     * <blockquote><tt>sc.register(sel, ops)</tt></blockquote>
     *
     * behaves in exactly the same way as the invocation
     *
     * <blockquote><tt>sc.{@link
     * #register(java.nio.channels.Selector,int,java.lang.Object)
     * register}(sel, ops, null)</tt></blockquote>
     *
     * <p>
     *  使用给定的选择器注册此通道,返回选择键。
     * 
     *  <p>表单的这个方便方法的调用
     * 
     *  <blockquote> <tt> sc.register(sel,ops)</tt> </blockquote>
     * 
     *  其行为与调用的方式完全相同
     * 
     *  <blockquote> <tt> sc。
     * {@ link #register(java.nio.channels.Selector,int,java.lang.Object)register}(sel,ops,null)</tt> </blockquote>
     * 。
     *  <blockquote> <tt> sc。
     * 
     * 
     * @param  sel
     *         The selector with which this channel is to be registered
     *
     * @param  ops
     *         The interest set for the resulting key
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  ClosedSelectorException
     *          If the selector is closed
     *
     * @throws  IllegalBlockingModeException
     *          If this channel is in blocking mode
     *
     * @throws  IllegalSelectorException
     *          If this channel was not created by the same provider
     *          as the given selector
     *
     * @throws  CancelledKeyException
     *          If this channel is currently registered with the given selector
     *          but the corresponding key has already been cancelled
     *
     * @throws  IllegalArgumentException
     *          If a bit in <tt>ops</tt> does not correspond to an operation
     *          that is supported by this channel, that is, if {@code set &
     *          ~validOps() != 0}
     *
     * @return  A key representing the registration of this channel with
     *          the given selector
     */
    public final SelectionKey register(Selector sel, int ops)
        throws ClosedChannelException
    {
        return register(sel, ops, null);
    }

    /**
     * Adjusts this channel's blocking mode.
     *
     * <p> If this channel is registered with one or more selectors then an
     * attempt to place it into blocking mode will cause an {@link
     * IllegalBlockingModeException} to be thrown.
     *
     * <p> This method may be invoked at any time.  The new blocking mode will
     * only affect I/O operations that are initiated after this method returns.
     * For some implementations this may require blocking until all pending I/O
     * operations are complete.
     *
     * <p> If this method is invoked while another invocation of this method or
     * of the {@link #register(Selector, int) register} method is in progress
     * then it will first block until the other operation is complete. </p>
     *
     * <p>
     *  调整此频道的屏蔽模式。
     * 
     *  <p>如果此频道已注册一个或多个选择器,则尝试将其置于阻止模式将导致{@link IllegalBlockingModeException}被抛出。
     * 
     *  <p>此方法可能随时被调用。新的阻塞模式将仅影响此方法返回后启动的I / O操作。对于某些实现,这可能需要阻塞,直到所有待处理的I / O操作完成。
     * 
     * <p>如果在此方法或{@link #register(Selector,int)register}方法的另一次调用正在进行时调用此方法,则它将首先阻塞,直到其他操作完成。 </p>
     * 
     * 
     * @param  block  If <tt>true</tt> then this channel will be placed in
     *                blocking mode; if <tt>false</tt> then it will be placed
     *                non-blocking mode
     *
     * @return  This selectable channel
     *
     * @throws  ClosedChannelException
     *          If this channel is closed
     *
     * @throws  IllegalBlockingModeException
     *          If <tt>block</tt> is <tt>true</tt> and this channel is
     *          registered with one or more selectors
     *
     * @throws IOException
     *         If an I/O error occurs
     */
    public abstract SelectableChannel configureBlocking(boolean block)
        throws IOException;
    //
    // sync(regLock) {
    //   sync(keySet) { throw IBME if block && isRegistered; }
    //   change mode;
    // }

    /**
     * Tells whether or not every I/O operation on this channel will block
     * until it completes.  A newly-created channel is always in blocking mode.
     *
     * <p> If this channel is closed then the value returned by this method is
     * not specified. </p>
     *
     * <p>
     *  告诉该通道上的每个I / O操作是否将阻塞,直到它完成。新创建的通道始终处于阻塞模式。
     * 
     *  <p>如果此频道关闭,则不指定此方法返回的值。 </p>
     * 
     * 
     * @return <tt>true</tt> if, and only if, this channel is in blocking mode
     */
    public abstract boolean isBlocking();

    /**
     * Retrieves the object upon which the {@link #configureBlocking
     * configureBlocking} and {@link #register register} methods synchronize.
     * This is often useful in the implementation of adaptors that require a
     * specific blocking mode to be maintained for a short period of time.
     *
     * <p>
     * 
     * @return  The blocking-mode lock object
     */
    public abstract Object blockingLock();

}
