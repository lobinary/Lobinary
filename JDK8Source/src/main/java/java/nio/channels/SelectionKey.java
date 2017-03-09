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

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.io.IOException;


/**
 * A token representing the registration of a {@link SelectableChannel} with a
 * {@link Selector}.
 *
 * <p> A selection key is created each time a channel is registered with a
 * selector.  A key remains valid until it is <i>cancelled</i> by invoking its
 * {@link #cancel cancel} method, by closing its channel, or by closing its
 * selector.  Cancelling a key does not immediately remove it from its
 * selector; it is instead added to the selector's <a
 * href="Selector.html#ks"><i>cancelled-key set</i></a> for removal during the
 * next selection operation.  The validity of a key may be tested by invoking
 * its {@link #isValid isValid} method.
 *
 * <a name="opsets"></a>
 *
 * <p> A selection key contains two <i>operation sets</i> represented as
 * integer values.  Each bit of an operation set denotes a category of
 * selectable operations that are supported by the key's channel.
 *
 * <ul>
 *
 *   <li><p> The <i>interest set</i> determines which operation categories will
 *   be tested for readiness the next time one of the selector's selection
 *   methods is invoked.  The interest set is initialized with the value given
 *   when the key is created; it may later be changed via the {@link
 *   #interestOps(int)} method. </p></li>
 *
 *   <li><p> The <i>ready set</i> identifies the operation categories for which
 *   the key's channel has been detected to be ready by the key's selector.
 *   The ready set is initialized to zero when the key is created; it may later
 *   be updated by the selector during a selection operation, but it cannot be
 *   updated directly. </p></li>
 *
 * </ul>
 *
 * <p> That a selection key's ready set indicates that its channel is ready for
 * some operation category is a hint, but not a guarantee, that an operation in
 * such a category may be performed by a thread without causing the thread to
 * block.  A ready set is most likely to be accurate immediately after the
 * completion of a selection operation.  It is likely to be made inaccurate by
 * external events and by I/O operations that are invoked upon the
 * corresponding channel.
 *
 * <p> This class defines all known operation-set bits, but precisely which
 * bits are supported by a given channel depends upon the type of the channel.
 * Each subclass of {@link SelectableChannel} defines an {@link
 * SelectableChannel#validOps() validOps()} method which returns a set
 * identifying just those operations that are supported by the channel.  An
 * attempt to set or test an operation-set bit that is not supported by a key's
 * channel will result in an appropriate run-time exception.
 *
 * <p> It is often necessary to associate some application-specific data with a
 * selection key, for example an object that represents the state of a
 * higher-level protocol and handles readiness notifications in order to
 * implement that protocol.  Selection keys therefore support the
 * <i>attachment</i> of a single arbitrary object to a key.  An object can be
 * attached via the {@link #attach attach} method and then later retrieved via
 * the {@link #attachment() attachment} method.
 *
 * <p> Selection keys are safe for use by multiple concurrent threads.  The
 * operations of reading and writing the interest set will, in general, be
 * synchronized with certain operations of the selector.  Exactly how this
 * synchronization is performed is implementation-dependent: In a naive
 * implementation, reading or writing the interest set may block indefinitely
 * if a selection operation is already in progress; in a high-performance
 * implementation, reading or writing the interest set may block briefly, if at
 * all.  In any case, a selection operation will always use the interest-set
 * value that was current at the moment that the operation began.  </p>
 *
 *
 * <p>
 *  表示使用{@link Selector}注册{@link SelectableChannel}的令牌。
 * 
 *  <p>每次向选择器注册频道时,都会创建选择键。在调用其{@link #cancel cancel}方法,关闭其频道或关闭其选择器之前,键仍然有效,直到<i> </i>被取消。
 * 取消键不会立即将其从选择器中删除;则会在下一个选择操作期间将其添加到选择器的<a href="Selector.html#ks"> <i>取消的密钥集</i> </a>中以便删除。
 * 可以通过调用其{@link #isValid isValid}方法来测试密钥的有效性。
 * 
 *  <a name="opsets"> </a>
 * 
 *  <p>选择键包含两个表示为整数值的<i>操作集</i>。操作集的每个位表示由键的通道支持的可选择操作的类别。
 * 
 * <ul>
 * 
 *  <li> <p> <i>兴趣集</i>决定下一次调用选择器的选择方法时,将测试哪些操作类别是否准备就绪。
 * 兴趣集用创建键时给定的值初始化;它可以通过{@link #interestOps(int)}方法更改。 </p> </li>。
 * 
 * <li> <p> <i> ready set </i>标识了键的选择器已检测到键的通道已准备就绪的操作类别。创建密钥时,就绪集将初始化为零;它可以稍后在选择操作期间由选择器更新,但是不能直接更新。
 *  </p> </li>。
 * 
 * </ul>
 * 
 *  <p>选择键的就绪集指示其通道已准备好用于某些操作类别是提示,但不是保证,在这样的类别中的操作可以由线程执行而不使线程阻塞。在完成选择操作之后,就绪集很可能是立即准确的。
 * 它可能由于外部事件和在相应通道上调用的I / O操作而不准确。
 * 
 *  <p>此类定义所有已知的操作集位,但确切地说,给定通道支持哪些位取决于通道的类型。
 *  {@link SelectableChannel}的每个子类定义了一个{@link SelectableChannel#validOps()validOps()}方法,该方法返回一组仅标识频道支持的操
 * 作。
 *  <p>此类定义所有已知的操作集位,但确切地说,给定通道支持哪些位取决于通道的类型。尝试设置或测试键的通道不支持的操作集位将导致适当的运行时异常。
 * 
 * 通常需要将一些特定于应用程序的数据与选择键相关联,例如表示更高级协议状态的对象,并处理准备就绪通知以实现该协议。因此,选择键支持单个任意对象到键的<i>附件</i>。
 * 对象可以通过{@link #attachment}方法附加,然后通过{@link #attachment()附件}方法检索。
 * 
 *  <p>选择键可安全地用于多个并发线程。读取和写入兴趣集合的操作通常将与选择器的某些操作同步。
 * 确切地说,如何执行这种同步是依赖于实现的：在一个幼稚的实现中,如果选择操作已经在进行中,则读取或写入兴趣集可能会无限地阻塞;在高性能实现中,读取或写入兴趣集可以短暂地阻塞,如果有的话。
 * 在任何情况下,选择操作将总是使用在操作开始时的当前的兴趣设置值。 </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 *
 * @see SelectableChannel
 * @see Selector
 */

public abstract class SelectionKey {

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     * 
     */
    protected SelectionKey() { }


    // -- Channel and selector operations --

    /**
     * Returns the channel for which this key was created.  This method will
     * continue to return the channel even after the key is cancelled.
     *
     * <p>
     *  返回创建此密钥的通道。即使在取消键之后,此方法仍将继续返回通道。
     * 
     * 
     * @return  This key's channel
     */
    public abstract SelectableChannel channel();

    /**
     * Returns the selector for which this key was created.  This method will
     * continue to return the selector even after the key is cancelled.
     *
     * <p>
     *  返回创建此键的选择器。即使取消键,此方法仍将继续返回选择器。
     * 
     * 
     * @return  This key's selector
     */
    public abstract Selector selector();

    /**
     * Tells whether or not this key is valid.
     *
     * <p> A key is valid upon creation and remains so until it is cancelled,
     * its channel is closed, or its selector is closed.  </p>
     *
     * <p>
     *  指出此键是否有效。
     * 
     * <p>键在创建时有效,并保持不变,直到取消,其通道关闭或选择器关闭。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this key is valid
     */
    public abstract boolean isValid();

    /**
     * Requests that the registration of this key's channel with its selector
     * be cancelled.  Upon return the key will be invalid and will have been
     * added to its selector's cancelled-key set.  The key will be removed from
     * all of the selector's key sets during the next selection operation.
     *
     * <p> If this key has already been cancelled then invoking this method has
     * no effect.  Once cancelled, a key remains forever invalid. </p>
     *
     * <p> This method may be invoked at any time.  It synchronizes on the
     * selector's cancelled-key set, and therefore may block briefly if invoked
     * concurrently with a cancellation or selection operation involving the
     * same selector.  </p>
     * <p>
     *  请求取消此键的频道与其选择器的注册。返回时,键将无效,并且已添加到其选择器的已取消键集。在下一个选择操作期间,键将从所有选择器的键集中删除。
     * 
     *  <p>如果此键已取消,则调用此方法不起作用。一旦取消,密钥将永远无效。 </p>
     * 
     *  <p>此方法可能随时被调用。它在选择器的取消键集上同步,因此如果与涉及相同选择器的取消或选择操作同时调用,则可以短暂地阻止。 </p>
     * 
     */
    public abstract void cancel();


    // -- Operation-set accessors --

    /**
     * Retrieves this key's interest set.
     *
     * <p> It is guaranteed that the returned set will only contain operation
     * bits that are valid for this key's channel.
     *
     * <p> This method may be invoked at any time.  Whether or not it blocks,
     * and for how long, is implementation-dependent.  </p>
     *
     * <p>
     *  检索此键的兴趣集。
     * 
     *  <p>它保证返回的集合将只包含对此键的通道有效的操作位。
     * 
     *  <p>此方法可能随时被调用。它是否阻塞以及阻塞和持续多长时间是实现相关的。 </p>
     * 
     * 
     * @return  This key's interest set
     *
     * @throws  CancelledKeyException
     *          If this key has been cancelled
     */
    public abstract int interestOps();

    /**
     * Sets this key's interest set to the given value.
     *
     * <p> This method may be invoked at any time.  Whether or not it blocks,
     * and for how long, is implementation-dependent.  </p>
     *
     * <p>
     *  将此键的兴趣集设置为给定值。
     * 
     *  <p>此方法可能随时被调用。它是否阻塞以及阻塞和持续多长时间是实现相关的。 </p>
     * 
     * 
     * @param  ops  The new interest set
     *
     * @return  This selection key
     *
     * @throws  IllegalArgumentException
     *          If a bit in the set does not correspond to an operation that
     *          is supported by this key's channel, that is, if
     *          {@code (ops & ~channel().validOps()) != 0}
     *
     * @throws  CancelledKeyException
     *          If this key has been cancelled
     */
    public abstract SelectionKey interestOps(int ops);

    /**
     * Retrieves this key's ready-operation set.
     *
     * <p> It is guaranteed that the returned set will only contain operation
     * bits that are valid for this key's channel.  </p>
     *
     * <p>
     *  检索此键的就绪操作集。
     * 
     *  <p>它保证返回的集合将只包含对此键的通道有效的操作位。 </p>
     * 
     * 
     * @return  This key's ready-operation set
     *
     * @throws  CancelledKeyException
     *          If this key has been cancelled
     */
    public abstract int readyOps();


    // -- Operation bits and bit-testing convenience methods --

    /**
     * Operation-set bit for read operations.
     *
     * <p> Suppose that a selection key's interest set contains
     * <tt>OP_READ</tt> at the start of a <a
     * href="Selector.html#selop">selection operation</a>.  If the selector
     * detects that the corresponding channel is ready for reading, has reached
     * end-of-stream, has been remotely shut down for further reading, or has
     * an error pending, then it will add <tt>OP_READ</tt> to the key's
     * ready-operation set and add the key to its selected-key&nbsp;set.  </p>
     * <p>
     *  读操作的操作设置位。
     * 
     * <p>假设在<a href="Selector.html#selop">选择操作</a>开始时,选择键的兴趣集包含<tt> OP_READ </tt>。
     * 如果选择器检测到相应的通道已准备好进行读取,已到达流末端,已被远程关闭以进行进一步读取,或者有错误未决,则会将<tt> OP_READ </tt>添加到键的就绪操作集,并将键添加到其所选键设置。
     *  </p>。
     * 
     */
    public static final int OP_READ = 1 << 0;

    /**
     * Operation-set bit for write operations.
     *
     * <p> Suppose that a selection key's interest set contains
     * <tt>OP_WRITE</tt> at the start of a <a
     * href="Selector.html#selop">selection operation</a>.  If the selector
     * detects that the corresponding channel is ready for writing, has been
     * remotely shut down for further writing, or has an error pending, then it
     * will add <tt>OP_WRITE</tt> to the key's ready set and add the key to its
     * selected-key&nbsp;set.  </p>
     * <p>
     *  写操作的操作设置位。
     * 
     *  <p>假设在<a href="Selector.html#selop">选择操作</a>开始时,选择键的兴趣集包含<tt> OP_WRITE </tt>。
     * 如果选择器检测到相应的通道已准备好写入,已被远程关闭以进行进一步写入,或者有未决错误,则将向键的就绪集添加<tt> OP_WRITE </tt>,并将键添加到其选定的键设置。 </p>。
     * 
     */
    public static final int OP_WRITE = 1 << 2;

    /**
     * Operation-set bit for socket-connect operations.
     *
     * <p> Suppose that a selection key's interest set contains
     * <tt>OP_CONNECT</tt> at the start of a <a
     * href="Selector.html#selop">selection operation</a>.  If the selector
     * detects that the corresponding socket channel is ready to complete its
     * connection sequence, or has an error pending, then it will add
     * <tt>OP_CONNECT</tt> to the key's ready set and add the key to its
     * selected-key&nbsp;set.  </p>
     * <p>
     *  插座连接操作的操作设置位。
     * 
     *  <p>假设在<a href="Selector.html#selop">选择操作</a>开始时,选择键的兴趣集包含<tt> OP_CONNECT </tt>。
     * 如果选择器检测到相应的套接字通道已准备好完成其连接序列,或者具有未决的错误,则它将向该键的就绪集添加<tt> OP_CONNECT </tt>,并将该键添加到其选定键&组。 </p>。
     * 
     */
    public static final int OP_CONNECT = 1 << 3;

    /**
     * Operation-set bit for socket-accept operations.
     *
     * <p> Suppose that a selection key's interest set contains
     * <tt>OP_ACCEPT</tt> at the start of a <a
     * href="Selector.html#selop">selection operation</a>.  If the selector
     * detects that the corresponding server-socket channel is ready to accept
     * another connection, or has an error pending, then it will add
     * <tt>OP_ACCEPT</tt> to the key's ready set and add the key to its
     * selected-key&nbsp;set.  </p>
     * <p>
     *  套接字接受操作的操作设置位。
     * 
     * <p>假设在<a href="Selector.html#selop">选择操作</a>开始时,选择键的兴趣集包含<tt> OP_ACCEPT </tt>。
     * 如果选择器检测到相应的服务器套接字通道已准备好接受另一个连接,或者有未决错误,则会将<tt> OP_ACCEPT </tt>添加到该键的就绪设置中,并将该键添加到其所选键;组。 </p>。
     * 
     */
    public static final int OP_ACCEPT = 1 << 4;

    /**
     * Tests whether this key's channel is ready for reading.
     *
     * <p> An invocation of this method of the form <tt>k.isReadable()</tt>
     * behaves in exactly the same way as the expression
     *
     * <blockquote><pre>{@code
     * k.readyOps() & OP_READ != 0
     * }</pre></blockquote>
     *
     * <p> If this key's channel does not support read operations then this
     * method always returns <tt>false</tt>.  </p>
     *
     * <p>
     *  测试此键的通道是否可以读取。
     * 
     *  <p>调用此方法的形式<tt> k.isReadable()</tt>的行为与表达式完全相同
     * 
     *  <blockquote> <pre> {@ code k.readyOps()&OP_READ！= 0} </pre> </blockquote>
     * 
     *  <p>如果此键的通道不支持读操作,则此方法总是返回<tt> false </tt>。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if,
                {@code readyOps() & OP_READ} is nonzero
     *
     * @throws  CancelledKeyException
     *          If this key has been cancelled
     */
    public final boolean isReadable() {
        return (readyOps() & OP_READ) != 0;
    }

    /**
     * Tests whether this key's channel is ready for writing.
     *
     * <p> An invocation of this method of the form <tt>k.isWritable()</tt>
     * behaves in exactly the same way as the expression
     *
     * <blockquote><pre>{@code
     * k.readyOps() & OP_WRITE != 0
     * }</pre></blockquote>
     *
     * <p> If this key's channel does not support write operations then this
     * method always returns <tt>false</tt>.  </p>
     *
     * <p>
     *  测试此键的通道是否可以写入。
     * 
     *  <p>以<tt> k.isWritable()</tt>的形式调用此方法的行为与表达式完全相同
     * 
     *  <blockquote> <pre> {@ code k.readyOps()&OP_WRITE！= 0} </pre> </blockquote>
     * 
     *  <p>如果此键的通道不支持写操作,则此方法总是返回<tt> false </tt>。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if,
     *          {@code readyOps() & OP_WRITE} is nonzero
     *
     * @throws  CancelledKeyException
     *          If this key has been cancelled
     */
    public final boolean isWritable() {
        return (readyOps() & OP_WRITE) != 0;
    }

    /**
     * Tests whether this key's channel has either finished, or failed to
     * finish, its socket-connection operation.
     *
     * <p> An invocation of this method of the form <tt>k.isConnectable()</tt>
     * behaves in exactly the same way as the expression
     *
     * <blockquote><pre>{@code
     * k.readyOps() & OP_CONNECT != 0
     * }</pre></blockquote>
     *
     * <p> If this key's channel does not support socket-connect operations
     * then this method always returns <tt>false</tt>.  </p>
     *
     * <p>
     *  测试此键的通道是否已完成或未能完成其套接字连接操作。
     * 
     *  <p>调用此方法的形式<tt> k.isConnectable()</tt>的行为与表达式完全相同
     * 
     *  <blockquote> <pre> {@ code k.readyOps()&OP_CONNECT！= 0} </pre> </blockquote>
     * 
     * <p>如果此键的通道不支持套接字连接操作,则此方法总是返回<tt> false </tt>。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if,
     *          {@code readyOps() & OP_CONNECT} is nonzero
     *
     * @throws  CancelledKeyException
     *          If this key has been cancelled
     */
    public final boolean isConnectable() {
        return (readyOps() & OP_CONNECT) != 0;
    }

    /**
     * Tests whether this key's channel is ready to accept a new socket
     * connection.
     *
     * <p> An invocation of this method of the form <tt>k.isAcceptable()</tt>
     * behaves in exactly the same way as the expression
     *
     * <blockquote><pre>{@code
     * k.readyOps() & OP_ACCEPT != 0
     * }</pre></blockquote>
     *
     * <p> If this key's channel does not support socket-accept operations then
     * this method always returns <tt>false</tt>.  </p>
     *
     * <p>
     *  测试此键的通道是否已准备好接受新的套接字连接。
     * 
     *  <p>以<tt> k.isAcceptable()</tt>的形式调用此方法的行为与表达式完全相同
     * 
     *  <blockquote> <pre> {@ code k.readyOps()&OP_ACCEPT！= 0} </pre> </blockquote>
     * 
     *  <p>如果此键的通道不支持套接字接受操作,则此方法总是返回<tt> false </tt>。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if,
     *          {@code readyOps() & OP_ACCEPT} is nonzero
     *
     * @throws  CancelledKeyException
     *          If this key has been cancelled
     */
    public final boolean isAcceptable() {
        return (readyOps() & OP_ACCEPT) != 0;
    }


    // -- Attachments --

    private volatile Object attachment = null;

    private static final AtomicReferenceFieldUpdater<SelectionKey,Object>
        attachmentUpdater = AtomicReferenceFieldUpdater.newUpdater(
            SelectionKey.class, Object.class, "attachment"
        );

    /**
     * Attaches the given object to this key.
     *
     * <p> An attached object may later be retrieved via the {@link #attachment()
     * attachment} method.  Only one object may be attached at a time; invoking
     * this method causes any previous attachment to be discarded.  The current
     * attachment may be discarded by attaching <tt>null</tt>.  </p>
     *
     * <p>
     *  将给定对象附加到此键。
     * 
     *  <p>稍后可以通过{@link #attachment()attachment}方法检索附加的对象。一次只能附加一个对象;调用此方法会导致丢弃任何先前的附件。
     * 可以通过附加<tt> null </tt>来丢弃当前附件。 </p>。
     * 
     * @param  ob
     *         The object to be attached; may be <tt>null</tt>
     *
     * @return  The previously-attached object, if any,
     *          otherwise <tt>null</tt>
     */
    public final Object attach(Object ob) {
        return attachmentUpdater.getAndSet(this, ob);
    }

    /**
     * Retrieves the current attachment.
     *
     * <p>
     * 
     * 
     * @return  The object currently attached to this key,
     *          or <tt>null</tt> if there is no attachment
     */
    public final Object attachment() {
        return attachment;
    }

}
