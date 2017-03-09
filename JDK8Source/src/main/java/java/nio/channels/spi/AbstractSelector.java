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

package java.nio.channels.spi;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashSet;
import java.util.Set;
import sun.nio.ch.Interruptible;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Base implementation class for selectors.
 *
 * <p> This class encapsulates the low-level machinery required to implement
 * the interruption of selection operations.  A concrete selector class must
 * invoke the {@link #begin begin} and {@link #end end} methods before and
 * after, respectively, invoking an I/O operation that might block
 * indefinitely.  In order to ensure that the {@link #end end} method is always
 * invoked, these methods should be used within a
 * <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block:
 *
 * <blockquote><pre>
 * try {
 *     begin();
 *     // Perform blocking I/O operation here
 *     ...
 * } finally {
 *     end();
 * }</pre></blockquote>
 *
 * <p> This class also defines methods for maintaining a selector's
 * cancelled-key set and for removing a key from its channel's key set, and
 * declares the abstract {@link #register register} method that is invoked by a
 * selectable channel's {@link AbstractSelectableChannel#register register}
 * method in order to perform the actual work of registering a channel.  </p>
 *
 *
 * <p>
 *  选择器的基本实现类。
 * 
 *  <p>此类封装了实现选择操作中断所需的低级机制。具体的选择器类必须分别调用之前和之后的{@link #begin begin}和{@link #end end}方法调用可能无限阻塞的I / O操作。
 * 为了确保始终调用{@link #end end}方法,应在<tt> try </tt>&nbsp; ...&nbsp; <tt> finally </tt>块中使用这些方法：。
 * 
 *  <blockquote> <pre> try {begin(); //在这里执行阻塞I / O操作...} finally {end(); } </pre> </blockquote>
 * 
 *  <p>此类还定义了用于维护选择器的取消键集以及从其通道的键集中删除键的方法,并声明由可选通道的{@link AbstractSelectableChannel()调用的抽象{@link #register register}
 * 方法#register register}方法,以便执行注册通道的实际工作。
 *  </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public abstract class AbstractSelector
    extends Selector
{

    private AtomicBoolean selectorOpen = new AtomicBoolean(true);

    // The provider that created this selector
    private final SelectorProvider provider;

    /**
     * Initializes a new instance of this class.
     *
     * <p>
     *  初始化此类的新实例。
     * 
     * 
     * @param  provider
     *         The provider that created this selector
     */
    protected AbstractSelector(SelectorProvider provider) {
        this.provider = provider;
    }

    private final Set<SelectionKey> cancelledKeys = new HashSet<SelectionKey>();

    void cancel(SelectionKey k) {                       // package-private
        synchronized (cancelledKeys) {
            cancelledKeys.add(k);
        }
    }

    /**
     * Closes this selector.
     *
     * <p> If the selector has already been closed then this method returns
     * immediately.  Otherwise it marks the selector as closed and then invokes
     * the {@link #implCloseSelector implCloseSelector} method in order to
     * complete the close operation.  </p>
     *
     * <p>
     *  关闭此选择器。
     * 
     *  <p>如果选择器已经关闭,则此方法会立即返回。否则,它将选择器标记为已关闭,然后调用{@link #implCloseSelector implCloseSelector}方法以完成关闭操作。
     *  </p>。
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs
     */
    public final void close() throws IOException {
        boolean open = selectorOpen.getAndSet(false);
        if (!open)
            return;
        implCloseSelector();
    }

    /**
     * Closes this selector.
     *
     * <p> This method is invoked by the {@link #close close} method in order
     * to perform the actual work of closing the selector.  This method is only
     * invoked if the selector has not yet been closed, and it is never invoked
     * more than once.
     *
     * <p> An implementation of this method must arrange for any other thread
     * that is blocked in a selection operation upon this selector to return
     * immediately as if by invoking the {@link
     * java.nio.channels.Selector#wakeup wakeup} method. </p>
     *
     * <p>
     *  关闭此选择器。
     * 
     * <p>此方法由{@link #close close}方法调用,以便执行关闭选择器的实际工作。仅当选择器尚未关闭时才会调用此方法,并且它不会被多次调用。
     * 
     *  <p>此方法的实现必须安排在此选择器上的选择操作中阻塞的任何其他线程,如同通过调用{@link java.nio.channels.Selector#wakeup wakeup}方法立即返回。
     *  </p>。
     * 
     * 
     * @throws  IOException
     *          If an I/O error occurs while closing the selector
     */
    protected abstract void implCloseSelector() throws IOException;

    public final boolean isOpen() {
        return selectorOpen.get();
    }

    /**
     * Returns the provider that created this channel.
     *
     * <p>
     *  返回创建此渠道的提供商。
     * 
     * 
     * @return  The provider that created this channel
     */
    public final SelectorProvider provider() {
        return provider;
    }

    /**
     * Retrieves this selector's cancelled-key set.
     *
     * <p> This set should only be used while synchronized upon it.  </p>
     *
     * <p>
     *  检索此选择器的取消键集。
     * 
     *  <p>此集合只应在同步时使用。 </p>
     * 
     * 
     * @return  The cancelled-key set
     */
    protected final Set<SelectionKey> cancelledKeys() {
        return cancelledKeys;
    }

    /**
     * Registers the given channel with this selector.
     *
     * <p> This method is invoked by a channel's {@link
     * AbstractSelectableChannel#register register} method in order to perform
     * the actual work of registering the channel with this selector.  </p>
     *
     * <p>
     *  使用此选择器注册给定通道。
     * 
     *  <p>此方法由频道的{@link AbstractSelectableChannel#register register}方法调用,以便执行使用此选择器注册频道的实际工作。 </p>
     * 
     * 
     * @param  ch
     *         The channel to be registered
     *
     * @param  ops
     *         The initial interest set, which must be valid
     *
     * @param  att
     *         The initial attachment for the resulting key
     *
     * @return  A new key representing the registration of the given channel
     *          with this selector
     */
    protected abstract SelectionKey register(AbstractSelectableChannel ch,
                                             int ops, Object att);

    /**
     * Removes the given key from its channel's key set.
     *
     * <p> This method must be invoked by the selector for each channel that it
     * deregisters.  </p>
     *
     * <p>
     *  从其频道的键集中删除给定的键。
     * 
     *  <p>此方法必须由选择器为其取消注册的每个通道调用。 </p>
     * 
     * 
     * @param  key
     *         The selection key to be removed
     */
    protected final void deregister(AbstractSelectionKey key) {
        ((AbstractSelectableChannel)key.channel()).removeKey(key);
    }


    // -- Interruption machinery --

    private Interruptible interruptor = null;

    /**
     * Marks the beginning of an I/O operation that might block indefinitely.
     *
     * <p> This method should be invoked in tandem with the {@link #end end}
     * method, using a <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block as
     * shown <a href="#be">above</a>, in order to implement interruption for
     * this selector.
     *
     * <p> Invoking this method arranges for the selector's {@link
     * Selector#wakeup wakeup} method to be invoked if a thread's {@link
     * Thread#interrupt interrupt} method is invoked while the thread is
     * blocked in an I/O operation upon the selector.  </p>
     * <p>
     *  标记可能无限阻止的I / O操作的开始。
     * 
     *  <p>此方法应与{@link #end end}方法一起调用,使用<tt> try </tt>&nbsp; ...&nbsp; <tt> finally </tt> a href ="#be"> </a>
     * ,以便为此选择器实现中断。
     * 
     * <p>如果在选择器的I / O操作中线程被阻塞时调用线程的{@link线程#中断中断}方法,则调用此方法将调用选择器的{@link Selector#wakeup wakeup}方法。 </p>
     * 
     */
    protected final void begin() {
        if (interruptor == null) {
            interruptor = new Interruptible() {
                    public void interrupt(Thread ignore) {
                        AbstractSelector.this.wakeup();
                    }};
        }
        AbstractInterruptibleChannel.blockedOn(interruptor);
        Thread me = Thread.currentThread();
        if (me.isInterrupted())
            interruptor.interrupt(me);
    }

    /**
     * Marks the end of an I/O operation that might block indefinitely.
     *
     * <p> This method should be invoked in tandem with the {@link #begin begin}
     * method, using a <tt>try</tt>&nbsp;...&nbsp;<tt>finally</tt> block as
     * shown <a href="#be">above</a>, in order to implement interruption for
     * this selector.  </p>
     * <p>
     */
    protected final void end() {
        AbstractInterruptibleChannel.blockedOn(null);
    }

}
