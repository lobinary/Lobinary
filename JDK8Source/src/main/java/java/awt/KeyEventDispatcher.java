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
package java.awt;

import java.awt.event.KeyEvent;


/**
 * A KeyEventDispatcher cooperates with the current KeyboardFocusManager in the
 * targeting and dispatching of all KeyEvents. KeyEventDispatchers registered
 * with the current KeyboardFocusManager will receive KeyEvents before they are
 * dispatched to their targets, allowing each KeyEventDispatcher to retarget
 * the event, consume it, dispatch the event itself, or make other changes.
 * <p>
 * Note that KeyboardFocusManager itself implements KeyEventDispatcher. By
 * default, the current KeyboardFocusManager will be the sink for all KeyEvents
 * not dispatched by the registered KeyEventDispatchers. The current
 * KeyboardFocusManager cannot be completely deregistered as a
 * KeyEventDispatcher. However, if a KeyEventDispatcher reports that it
 * dispatched the KeyEvent, regardless of whether it actually did so, the
 * KeyboardFocusManager will take no further action with regard to the
 * KeyEvent. (While it is possible for client code to register the current
 * KeyboardFocusManager as a KeyEventDispatcher one or more times, this is
 * usually unnecessary and not recommended.)
 *
 * <p>
 *  KeyEventDispatcher在目标和分派所有KeyEvents时与当前的KeyboardFocusManager合作。
 * 使用当前KeyboardFocusManager注册的KeyEventDispatchers将在分派到其目标之前接收KeyEvent,从而允许每个KeyEventDispatcher重新定位事件,使用它
 * ,调度事件本身或进行其他更改。
 *  KeyEventDispatcher在目标和分派所有KeyEvents时与当前的KeyboardFocusManager合作。
 * <p>
 *  注意,KeyboardFocusManager本身实现了KeyEventDispatcher。
 * 默认情况下,当前KeyboardFocusManager将是所有未由注册的KeyEventDispatchers分派的KeyEvent的接收器。
 * 当前KeyboardFocusManager无法完全取消注册为KeyEventDispatcher。
 * 但是,如果一个KeyEventDispatcher报告它调度了KeyEvent,无论是否实际这样做,KeyboardFocusManager将不会对KeyEvent采取进一步的操作。
 *  (虽然客户端代码可能将当前的KeyboardFocusManager注册为一个或多个KeyEventDispatcher,但这通常是不必要的,因此不推荐。
 * 
 * 
 * @author David Mendenhall
 *
 * @see KeyboardFocusManager#addKeyEventDispatcher
 * @see KeyboardFocusManager#removeKeyEventDispatcher
 * @since 1.4
 */
@FunctionalInterface
public interface KeyEventDispatcher {

    /**
     * This method is called by the current KeyboardFocusManager requesting
     * that this KeyEventDispatcher dispatch the specified event on its behalf.
     * This KeyEventDispatcher is free to retarget the event, consume it,
     * dispatch it itself, or make other changes. This capability is typically
     * used to deliver KeyEvents to Components other than the focus owner. This
     * can be useful when navigating children of non-focusable Windows in an
     * accessible environment, for example. Note that if a KeyEventDispatcher
     * dispatches the KeyEvent itself, it must use <code>redispatchEvent</code>
     * to prevent the current KeyboardFocusManager from recursively requesting
     * that this KeyEventDispatcher dispatch the event again.
     * <p>
     * If an implementation of this method returns <code>false</code>, then
     * the KeyEvent is passed to the next KeyEventDispatcher in the chain,
     * ending with the current KeyboardFocusManager. If an implementation
     * returns <code>true</code>, the KeyEvent is assumed to have been
     * dispatched (although this need not be the case), and the current
     * KeyboardFocusManager will take no further action with regard to the
     * KeyEvent. In such a case,
     * <code>KeyboardFocusManager.dispatchEvent</code> should return
     * <code>true</code> as well. If an implementation consumes the KeyEvent,
     * but returns <code>false</code>, the consumed event will still be passed
     * to the next KeyEventDispatcher in the chain. It is important for
     * developers to check whether the KeyEvent has been consumed before
     * dispatching it to a target. By default, the current KeyboardFocusManager
     * will not dispatch a consumed KeyEvent.
     *
     * <p>
     * 此方法由当前KeyboardFocusManager调用,请求此KeyEventDispatcher代表其调度指定的事件。
     * 此KeyEventDispatcher可以自由重定向事件,使用它,调度它自己或进行其他更改。此功能通常用于将KeyEvent传递给除焦点所有者之外的组件。
     * 例如,当在可访问的环境中导航非可聚焦Windows的孩子时,这可能很有用。
     * 注意,如果KeyEventDispatcher调度KeyEvent本身,它必须使用<code> redispatchEvent </code>来阻止当前的KeyboardFocusManager递归地请
     * 求此KeyEventDispatcher再次分派事件。
     * 例如,当在可访问的环境中导航非可聚焦Windows的孩子时,这可能很有用。
     * <p>
     * 
     * @param e the KeyEvent to dispatch
     * @return <code>true</code> if the KeyboardFocusManager should take no
     *         further action with regard to the KeyEvent; <code>false</code>
     *         otherwise
     * @see KeyboardFocusManager#redispatchEvent
     */
    boolean dispatchKeyEvent(KeyEvent e);
}
