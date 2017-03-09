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

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;


/**
 * A multiplexor of {@link SelectableChannel} objects.
 *
 * <p> A selector may be created by invoking the {@link #open open} method of
 * this class, which will use the system's default {@link
 * java.nio.channels.spi.SelectorProvider selector provider} to
 * create a new selector.  A selector may also be created by invoking the
 * {@link java.nio.channels.spi.SelectorProvider#openSelector openSelector}
 * method of a custom selector provider.  A selector remains open until it is
 * closed via its {@link #close close} method.
 *
 * <a name="ks"></a>
 *
 * <p> A selectable channel's registration with a selector is represented by a
 * {@link SelectionKey} object.  A selector maintains three sets of selection
 * keys:
 *
 * <ul>
 *
 *   <li><p> The <i>key set</i> contains the keys representing the current
 *   channel registrations of this selector.  This set is returned by the
 *   {@link #keys() keys} method. </p></li>
 *
 *   <li><p> The <i>selected-key set</i> is the set of keys such that each
 *   key's channel was detected to be ready for at least one of the operations
 *   identified in the key's interest set during a prior selection operation.
 *   This set is returned by the {@link #selectedKeys() selectedKeys} method.
 *   The selected-key set is always a subset of the key set. </p></li>
 *
 *   <li><p> The <i>cancelled-key</i> set is the set of keys that have been
 *   cancelled but whose channels have not yet been deregistered.  This set is
 *   not directly accessible.  The cancelled-key set is always a subset of the
 *   key set. </p></li>
 *
 * </ul>
 *
 * <p> All three sets are empty in a newly-created selector.
 *
 * <p> A key is added to a selector's key set as a side effect of registering a
 * channel via the channel's {@link SelectableChannel#register(Selector,int)
 * register} method.  Cancelled keys are removed from the key set during
 * selection operations.  The key set itself is not directly modifiable.
 *
 * <p> A key is added to its selector's cancelled-key set when it is cancelled,
 * whether by closing its channel or by invoking its {@link SelectionKey#cancel
 * cancel} method.  Cancelling a key will cause its channel to be deregistered
 * during the next selection operation, at which time the key will removed from
 * all of the selector's key sets.
 *
 * <a name="sks"></a><p> Keys are added to the selected-key set by selection
 * operations.  A key may be removed directly from the selected-key set by
 * invoking the set's {@link java.util.Set#remove(java.lang.Object) remove}
 * method or by invoking the {@link java.util.Iterator#remove() remove} method
 * of an {@link java.util.Iterator iterator} obtained from the
 * set.  Keys are never removed from the selected-key set in any other way;
 * they are not, in particular, removed as a side effect of selection
 * operations.  Keys may not be added directly to the selected-key set. </p>
 *
 *
 * <a name="selop"></a>
 * <h2>Selection</h2>
 *
 * <p> During each selection operation, keys may be added to and removed from a
 * selector's selected-key set and may be removed from its key and
 * cancelled-key sets.  Selection is performed by the {@link #select()}, {@link
 * #select(long)}, and {@link #selectNow()} methods, and involves three steps:
 * </p>
 *
 * <ol>
 *
 *   <li><p> Each key in the cancelled-key set is removed from each key set of
 *   which it is a member, and its channel is deregistered.  This step leaves
 *   the cancelled-key set empty. </p></li>
 *
 *   <li><p> The underlying operating system is queried for an update as to the
 *   readiness of each remaining channel to perform any of the operations
 *   identified by its key's interest set as of the moment that the selection
 *   operation began.  For a channel that is ready for at least one such
 *   operation, one of the following two actions is performed: </p>
 *
 *   <ol>
 *
 *     <li><p> If the channel's key is not already in the selected-key set then
 *     it is added to that set and its ready-operation set is modified to
 *     identify exactly those operations for which the channel is now reported
 *     to be ready.  Any readiness information previously recorded in the ready
 *     set is discarded.  </p></li>
 *
 *     <li><p> Otherwise the channel's key is already in the selected-key set,
 *     so its ready-operation set is modified to identify any new operations
 *     for which the channel is reported to be ready.  Any readiness
 *     information previously recorded in the ready set is preserved; in other
 *     words, the ready set returned by the underlying system is
 *     bitwise-disjoined into the key's current ready set. </p></li>
 *
 *   </ol>
 *
 *   If all of the keys in the key set at the start of this step have empty
 *   interest sets then neither the selected-key set nor any of the keys'
 *   ready-operation sets will be updated.
 *
 *   <li><p> If any keys were added to the cancelled-key set while step (2) was
 *   in progress then they are processed as in step (1). </p></li>
 *
 * </ol>
 *
 * <p> Whether or not a selection operation blocks to wait for one or more
 * channels to become ready, and if so for how long, is the only essential
 * difference between the three selection methods. </p>
 *
 *
 * <h2>Concurrency</h2>
 *
 * <p> Selectors are themselves safe for use by multiple concurrent threads;
 * their key sets, however, are not.
 *
 * <p> The selection operations synchronize on the selector itself, on the key
 * set, and on the selected-key set, in that order.  They also synchronize on
 * the cancelled-key set during steps (1) and (3) above.
 *
 * <p> Changes made to the interest sets of a selector's keys while a
 * selection operation is in progress have no effect upon that operation; they
 * will be seen by the next selection operation.
 *
 * <p> Keys may be cancelled and channels may be closed at any time.  Hence the
 * presence of a key in one or more of a selector's key sets does not imply
 * that the key is valid or that its channel is open.  Application code should
 * be careful to synchronize and check these conditions as necessary if there
 * is any possibility that another thread will cancel a key or close a channel.
 *
 * <p> A thread blocked in one of the {@link #select()} or {@link
 * #select(long)} methods may be interrupted by some other thread in one of
 * three ways:
 *
 * <ul>
 *
 *   <li><p> By invoking the selector's {@link #wakeup wakeup} method,
 *   </p></li>
 *
 *   <li><p> By invoking the selector's {@link #close close} method, or
 *   </p></li>
 *
 *   <li><p> By invoking the blocked thread's {@link
 *   java.lang.Thread#interrupt() interrupt} method, in which case its
 *   interrupt status will be set and the selector's {@link #wakeup wakeup}
 *   method will be invoked. </p></li>
 *
 * </ul>
 *
 * <p> The {@link #close close} method synchronizes on the selector and all
 * three key sets in the same order as in a selection operation.
 *
 * <a name="ksc"></a>
 *
 * <p> A selector's key and selected-key sets are not, in general, safe for use
 * by multiple concurrent threads.  If such a thread might modify one of these
 * sets directly then access should be controlled by synchronizing on the set
 * itself.  The iterators returned by these sets' {@link
 * java.util.Set#iterator() iterator} methods are <i>fail-fast:</i> If the set
 * is modified after the iterator is created, in any way except by invoking the
 * iterator's own {@link java.util.Iterator#remove() remove} method, then a
 * {@link java.util.ConcurrentModificationException} will be thrown. </p>
 *
 *
 * <p>
 *  {@link SelectableChannel}对象的多路复用器。
 * 
 *  <p>可以通过调用此类的{@link #open open}方法创建选择器,该方法将使用系统的默认{@link java.nio.channels.spi.SelectorProvider selector provider}
 * 来创建新的选择器。
 * 也可以通过调用自定义选择器提供程序的{@link java.nio.channels.spi.SelectorProvider#openSelector openSelector}方法创建选择器。
 * 选择器保持打开状态,直到它通过其{@link #close close}方法关闭。
 * 
 *  <a name="ks"> </a>
 * 
 *  <p>用选择器注册的可选频道由{@link SelectionKey}对象表示。选择器维护三组选择键：
 * 
 * <ul>
 * 
 *  <li> <p> <i>键集</i>包含表示此选择器的当前通道注册的键。此集合由{@link #keys()keys}方法返回。 </p> </li>
 * 
 *  <li> <p>所选键集合</i>是一组键,使得检测到每个键的频道为在先前选择期间在键的兴趣集中识别的操作中的至少一个做好准备操作。
 * 此集合由{@link #selectedKeys()selectedKeys}方法返回。所选键集合总是键集合的子集。 </p> </li>。
 * 
 * <li> <p> <i> cancelled-key </i>设置是已取消但其频道尚未注销的一组键。此集不可直接访问。取消的键集合总是键集合的子集。 </p> </li>
 * 
 * </ul>
 * 
 *  <p>在新创建的选择器中,所有三个集都为空。
 * 
 *  <p>键会添加到选择器的键集中,作为通过通道的{@link SelectableChannel#register(Selector,int)register}方法注册通道的副作用。
 * 在选择操作期间,已取消的键将从键集中删除。密钥集本身不能直接修改。
 * 
 *  <p>当取消键时,通过关闭其通道或调用其{@link SelectionKey#cancel cancel}方法,键将被添加到其选择器的取消键集。
 * 取消密钥将导致其通道在下一个选择操作期间被取消注册,此时密钥将从所有选择器的密钥集中移除。
 * 
 *  <a name="sks"> </a> <p>通过选择操作将键添加到所选键集。
 * 键可以通过调用集合的{@link java.util.Set#remove(java.lang.Object)remove}方法或通过调用{@link java.util.Iterator#remove ()remove}
 * 方法从一个{@link java.util.Iterator迭代器}获得的集合。
 *  <a name="sks"> </a> <p>通过选择操作将键添加到所选键集。键不会以任何其他方式从所选键集中删除;它们尤其不作为选择操作的副作用而被移除。键不能直接添加到所选键集。 </p>。
 * 
 * <a name="selop"> </a> <h2>选择</h2>
 * 
 *  <p>在每个选择操作期间,可以将键添加到选择器的所选择的键集合并从中移除,并且可以从它的键和取消的键集合中移除键。
 * 选择由{@link #select()},{@link #select(long)}和{@link #selectNow()}方法执行,包括三个步骤：。
 * </p>
 * 
 * <ol>
 * 
 *  <li> <p>取消的键集中的每个键都将从它所属的每个键集中删除,并且其通道将被取消注册。此步骤将取消的键设置为空。 </p> </li>
 * 
 *  <li> <p>底层操作系统被查询关于每个剩余频道的准备情况的更新,以执行在选择操作开始时由其键设置的任何操作。对于准备好进行至少一个这样的操作的通道,执行以下两个动作之一：</p>
 * 
 * <ol>
 * 
 *  <li> <p>如果频道的键尚未位于所选键集中,则会将其添加到该集中,并修改其即时操作集,以准确地标识频道现在已准备就绪的那些操作。先前记录在就绪集中的任何准备就绪信息被丢弃。
 *  </p> </li>。
 * 
 * <li> <p>否则,频道的键已经在所选键集中,因此修改其就绪操作集以识别已报告频道已准备就绪的任何新操作。
 * 先前记录在就绪集中的任何准备信息被保留;换句话说,由底层系统返回的就绪集被按位分离到键的当前就绪集中。 </p> </li>。
 * 
 * </ol>
 * 
 *  如果在该步骤的开始处的键集合中的所有键具有空的兴趣集合,则选择的键集合或键的就绪操作集合中的任何一个都不会被更新。
 * 
 *  <li> <p>如果在步骤(2)正在进行时将任何键添加到取消的键集中,则会按照步骤(1)中的方式处理它们。 </p> </li>
 * 
 * </ol>
 * 
 *  <p>选择操作是否阻止等待一个或多个通道变为就绪,如果等待多久,则是三种选择方法之间唯一的本质区别。 </p>
 * 
 *  <h2>并发</h2>
 * 
 *  <p>选择器本身对于多个并发线程是安全的;但是它们的密钥集不是。
 * 
 *  <p>选择操作在选择器本身,键集和选定键集上按此顺序同步。它们还在上述步骤(1)和(3)期间在取消的键集合上同步。
 * 
 *  <p>在选择操作正在进行时对选择器键的兴趣集所做的更改不会影响该操作;它们将被下一个选择操作看到。
 * 
 * <p>键可能会被取消,频道可能随时关闭。因此,在一个或多个选择器的键集合中存在键并不意味着该键是有效的或者其通道是打开的。
 * 应用程序代码应仔细同步,并在必要时检查这些条件,如果有可能另一个线程将取消密钥或关闭通道。
 * 
 *  <p>在{@link #select()}或{@link #select(long)}方法之一封锁的线程可能被其他线程以下列三种方式中断：
 * 
 * <ul>
 * 
 *  <li> <p>通过调用选择器的{@link #wakeup wakeup}方法,</p> </li>
 * 
 *  <li> <p>通过调用选择器的{@link #close close}方法,或</p> </li>
 * 
 *  <li> <p>通过调用被阻止的线程的{@link java.lang.Thread#interrupt()interrupt}方法,在这种情况下,它的中断状态将被设置,并且选择器的{@link #wakeup wakeup}
 * 方法将被调用。
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 *
 * @see SelectableChannel
 * @see SelectionKey
 */

public abstract class Selector implements Closeable {

    /**
     * Initializes a new instance of this class.
     * <p>
     *  </p> </li>。
     * 
     * </ul>
     * 
     *  <p> {@link #close close}方法以与选择操作相同的顺序同步选择器和所有三个键集。
     * 
     *  <a name="ksc"> </a>
     * 
     * <p>选择器的键和选择键集通常不能安全地用于多个并发线程。如果这样的线程可能直接修改这些集合中的一个,那么应该通过在集合本身上同步来控制访问。
     * 这些集合的{@link java.util.Set#iterator()iterator}方法返回的迭代器<i> fail-fast：</i>如果在创建迭代器之后修改集合,调用迭代器自己的{@link java.util.Iterator#remove()remove}
     * 方法,那么将抛出{@link java.util.ConcurrentModificationException}。
     * <p>选择器的键和选择键集通常不能安全地用于多个并发线程。如果这样的线程可能直接修改这些集合中的一个,那么应该通过在集合本身上同步来控制访问。 </p>。
     * 
     */
    protected Selector() { }

    /**
     * Opens a selector.
     *
     * <p> The new selector is created by invoking the {@link
     * java.nio.channels.spi.SelectorProvider#openSelector openSelector} method
     * of the system-wide default {@link
     * java.nio.channels.spi.SelectorProvider} object.  </p>
     *
     * <p>
     *  初始化此类的新实例。
     * 
     * 
     * @return  A new selector
     *
     * @throws  IOException
     *          If an I/O error occurs
     */
    public static Selector open() throws IOException {
        return SelectorProvider.provider().openSelector();
    }

    /**
     * Tells whether or not this selector is open.
     *
     * <p>
     *  打开选择器。
     * 
     *  <p>新的选择器是通过调用系统级默认{@link java.nio.channels.spi.SelectorProvider}对象的{@link java.nio.channels.spi.SelectorProvider#openSelector openSelector}
     * 方法创建的。
     *  </p>。
     * 
     * 
     * @return <tt>true</tt> if, and only if, this selector is open
     */
    public abstract boolean isOpen();

    /**
     * Returns the provider that created this channel.
     *
     * <p>
     *  指示此选择器是否打开。
     * 
     * 
     * @return  The provider that created this channel
     */
    public abstract SelectorProvider provider();

    /**
     * Returns this selector's key set.
     *
     * <p> The key set is not directly modifiable.  A key is removed only after
     * it has been cancelled and its channel has been deregistered.  Any
     * attempt to modify the key set will cause an {@link
     * UnsupportedOperationException} to be thrown.
     *
     * <p> The key set is <a href="#ksc">not thread-safe</a>. </p>
     *
     * <p>
     *  返回创建此渠道的提供商。
     * 
     * 
     * @return  This selector's key set
     *
     * @throws  ClosedSelectorException
     *          If this selector is closed
     */
    public abstract Set<SelectionKey> keys();

    /**
     * Returns this selector's selected-key set.
     *
     * <p> Keys may be removed from, but not directly added to, the
     * selected-key set.  Any attempt to add an object to the key set will
     * cause an {@link UnsupportedOperationException} to be thrown.
     *
     * <p> The selected-key set is <a href="#ksc">not thread-safe</a>. </p>
     *
     * <p>
     *  返回此选择器的键集。
     * 
     *  <p>键集不能直接修改。只有在取消键并且其通道已取消注册后才会删除键。任何尝试修改密钥集将导致{@link UnsupportedOperationException}抛出。
     * 
     *  <p>键集是<a href="#ksc">不是线程安全</a>。 </p>
     * 
     * 
     * @return  This selector's selected-key set
     *
     * @throws  ClosedSelectorException
     *          If this selector is closed
     */
    public abstract Set<SelectionKey> selectedKeys();

    /**
     * Selects a set of keys whose corresponding channels are ready for I/O
     * operations.
     *
     * <p> This method performs a non-blocking <a href="#selop">selection
     * operation</a>.  If no channels have become selectable since the previous
     * selection operation then this method immediately returns zero.
     *
     * <p> Invoking this method clears the effect of any previous invocations
     * of the {@link #wakeup wakeup} method.  </p>
     *
     * <p>
     *  返回此选择器的所选键集。
     * 
     * <p>键可以从所选键集中删除,但不能直接添加到所选键集。任何尝试向键集添加对象都将导致{@link UnsupportedOperationException}被抛出。
     * 
     *  <p>所选键集合为<a href="#ksc">不是线程安全</a>。 </p>
     * 
     * 
     * @return  The number of keys, possibly zero, whose ready-operation sets
     *          were updated by the selection operation
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  ClosedSelectorException
     *          If this selector is closed
     */
    public abstract int selectNow() throws IOException;

    /**
     * Selects a set of keys whose corresponding channels are ready for I/O
     * operations.
     *
     * <p> This method performs a blocking <a href="#selop">selection
     * operation</a>.  It returns only after at least one channel is selected,
     * this selector's {@link #wakeup wakeup} method is invoked, the current
     * thread is interrupted, or the given timeout period expires, whichever
     * comes first.
     *
     * <p> This method does not offer real-time guarantees: It schedules the
     * timeout as if by invoking the {@link Object#wait(long)} method. </p>
     *
     * <p>
     *  选择相应通道已准备好进行I / O操作的一组键。
     * 
     *  <p>此方法执行非阻塞<a href="#selop">选择操作</a>。如果自从上一次选择操作后没有可选择的通道,则该方法立即返回零。
     * 
     *  <p>调用此方法可清除先前对{@link #wakeup wakeup}方法的任何调用的影响。 </p>
     * 
     * 
     * @param  timeout  If positive, block for up to <tt>timeout</tt>
     *                  milliseconds, more or less, while waiting for a
     *                  channel to become ready; if zero, block indefinitely;
     *                  must not be negative
     *
     * @return  The number of keys, possibly zero,
     *          whose ready-operation sets were updated
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  ClosedSelectorException
     *          If this selector is closed
     *
     * @throws  IllegalArgumentException
     *          If the value of the timeout argument is negative
     */
    public abstract int select(long timeout)
        throws IOException;

    /**
     * Selects a set of keys whose corresponding channels are ready for I/O
     * operations.
     *
     * <p> This method performs a blocking <a href="#selop">selection
     * operation</a>.  It returns only after at least one channel is selected,
     * this selector's {@link #wakeup wakeup} method is invoked, or the current
     * thread is interrupted, whichever comes first.  </p>
     *
     * <p>
     *  选择相应通道已准备好进行I / O操作的一组键。
     * 
     *  <p>此方法执行阻止<a href="#selop">选择操作</a>。
     * 仅在选择至少一个通道后,此选择器的{@link #wakeup wakeup}方法被调用,当前线程被中断或给定的超时时间到期(以先到者为准),它才会返回。
     * 
     *  <p>此方法不提供实时保证：它调度超时,如同通过调用{@link Object#wait(long)}方法。 </p>
     * 
     * 
     * @return  The number of keys, possibly zero,
     *          whose ready-operation sets were updated
     *
     * @throws  IOException
     *          If an I/O error occurs
     *
     * @throws  ClosedSelectorException
     *          If this selector is closed
     */
    public abstract int select() throws IOException;

    /**
     * Causes the first selection operation that has not yet returned to return
     * immediately.
     *
     * <p> If another thread is currently blocked in an invocation of the
     * {@link #select()} or {@link #select(long)} methods then that invocation
     * will return immediately.  If no selection operation is currently in
     * progress then the next invocation of one of these methods will return
     * immediately unless the {@link #selectNow()} method is invoked in the
     * meantime.  In any case the value returned by that invocation may be
     * non-zero.  Subsequent invocations of the {@link #select()} or {@link
     * #select(long)} methods will block as usual unless this method is invoked
     * again in the meantime.
     *
     * <p> Invoking this method more than once between two successive selection
     * operations has the same effect as invoking it just once.  </p>
     *
     * <p>
     *  选择相应通道已准备好进行I / O操作的一组键。
     * 
     * <p>此方法执行阻止<a href="#selop">选择操作</a>。它仅在选择至少一个通道后返回,此选择器的{@link #wakeup wakeup}方法被调用,或当前线程被中断,以先到者为准。
     *  </p>。
     * 
     * 
     * @return  This selector
     */
    public abstract Selector wakeup();

    /**
     * Closes this selector.
     *
     * <p> If a thread is currently blocked in one of this selector's selection
     * methods then it is interrupted as if by invoking the selector's {@link
     * #wakeup wakeup} method.
     *
     * <p> Any uncancelled keys still associated with this selector are
     * invalidated, their channels are deregistered, and any other resources
     * associated with this selector are released.
     *
     * <p> If this selector is already closed then invoking this method has no
     * effect.
     *
     * <p> After a selector is closed, any further attempt to use it, except by
     * invoking this method or the {@link #wakeup wakeup} method, will cause a
     * {@link ClosedSelectorException} to be thrown. </p>
     *
     * <p>
     *  导致尚未返回的第一个选择操作立即返回。
     * 
     *  <p>如果另一个线程在{@link #select()}或{@link #select(long)}方法的调用中被阻塞,那么该调用将立即返回。
     * 如果当前没有选择操作正在进行,那么除非在此期间调用{@link #selectNow()}方法,否则下一次调用这些方法之一将立即返回。在任何情况下,该调用返回的值可能不为零。
     *  {@link #select()}或{@link #select(long)}方法的后续调用将正常阻止,除非在此期间再次调用此方法。
     * 
     *  <p>在两个连续的选择操作之间多次调用此方法具有与仅调用一次相同的效果。 </p>
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs
     */
    public abstract void close() throws IOException;

}
