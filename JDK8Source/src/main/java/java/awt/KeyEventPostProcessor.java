/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A KeyEventPostProcessor cooperates with the current KeyboardFocusManager
 * in the final resolution of all unconsumed KeyEvents. KeyEventPostProcessors
 * registered with the current KeyboardFocusManager will receive KeyEvents
 * after the KeyEvents have been dispatched to and handled by their targets.
 * KeyEvents that would have been otherwise discarded because no Component in
 * the application currently owns the focus will also be forwarded to
 * registered KeyEventPostProcessors. This will allow applications to implement
 * features that require global KeyEvent post-handling, such as menu shortcuts.
 * <p>
 * Note that the KeyboardFocusManager itself implements KeyEventPostProcessor.
 * By default, the current KeyboardFocusManager will be the final
 * KeyEventPostProcessor in the chain. The current KeyboardFocusManager cannot
 * be completely deregistered as a KeyEventPostProcessor. However, if a
 * KeyEventPostProcessor reports that no further post-processing of the
 * KeyEvent should take place, the AWT will consider the event fully handled
 * and will take no additional action with regard to the event. (While it is
 * possible for client code to register the current KeyboardFocusManager as
 * a KeyEventPostProcessor one or more times, this is usually unnecessary and
 * not recommended.)
 *
 * <p>
 *  KeyEventPostProcessor与当前KeyboardFocusManager在所有未消费的KeyEvent的最终分辨率中协作。
 * 在当前KeyboardFocusManager注册的KeyEventPostProcessors将在KeyEvent分派到其目标并由其处理后接收KeyEvent。
 * 否则将丢弃KeyEvent,因为应用程序中当前没有组件拥有焦点,也会被转发到注册的KeyEventPostProcessors。
 * 这将允许应用程序实现需要全局KeyEvent后处理的功能,例如菜单快捷方式。
 * <p>
 *  注意,KeyboardFocusManager本身实现了KeyEventPostProcessor。
 * 默认情况下,当前的KeyboardFocusManager将是链中最后的KeyEventPostProcessor。
 * 当前KeyboardFocusManager不能完全取消注册为一个KeyEventPostProcessor。
 * 然而,如果KeyEventPostProcessor报告不应发生对KeyEvent的进一步后处理,则AWT将考虑事件被完全处理,并且将不采取关于事件的附加动作。
 *  (虽然客户端代码可能将当前的KeyboardFocusManager注册为一个或多个KeyEventPostProcessor,但这通常是不必要的,不推荐使用。)。
 * 
 * 
 * @author David Mendenhall
 *
 * @see KeyboardFocusManager#addKeyEventPostProcessor
 * @see KeyboardFocusManager#removeKeyEventPostProcessor
 * @since 1.4
 */
@FunctionalInterface
public interface KeyEventPostProcessor {

    /**
     * This method is called by the current KeyboardFocusManager, requesting
     * that this KeyEventPostProcessor perform any necessary post-processing
     * which should be part of the KeyEvent's final resolution. At the time
     * this method is invoked, typically the KeyEvent has already been
     * dispatched to and handled by its target. However, if no Component in
     * the application currently owns the focus, then the KeyEvent has not
     * been dispatched to any Component. Typically, KeyEvent post-processing
     * will be used to implement features which require global KeyEvent
     * post-handling, such as menu shortcuts. Note that if a
     * KeyEventPostProcessor wishes to dispatch the KeyEvent, it must use
     * <code>redispatchEvent</code> to prevent the AWT from recursively
     * requesting that this KeyEventPostProcessor perform post-processing
     * of the event again.
     * <p>
     * If an implementation of this method returns <code>false</code>, then the
     * KeyEvent is passed to the next KeyEventPostProcessor in the chain,
     * ending with the current KeyboardFocusManager. If an implementation
     * returns <code>true</code>, the KeyEvent is assumed to have been fully
     * handled (although this need not be the case), and the AWT will take no
     * further action with regard to the KeyEvent. If an implementation
     * consumes the KeyEvent but returns <code>false</code>, the consumed
     * event will still be passed to the next KeyEventPostProcessor in the
     * chain. It is important for developers to check whether the KeyEvent has
     * been consumed before performing any post-processing of the KeyEvent. By
     * default, the current KeyboardFocusManager will perform no post-
     * processing in response to a consumed KeyEvent.
     *
     * <p>
     * 此方法由当前KeyboardFocusManager调用,请求此KeyEventPostProcessor执行任何必要的后处理,该后处理应该是KeyEvent的最终解决方案的一部分。
     * 在调用此方法时,通常已将KeyEvent分派给其目标并由其处理。但是,如果应用程序中没有组件当前拥有焦点,则未将KeyEvent分派给任何组件。
     * 通常,KeyEvent后处理将用于实现需要全局KeyEvent后处理的功能,例如菜单快捷方式。
     * 注意,如果KeyEventPostProcessor希望分派KeyEvent,它必须使用<code> redispatchEvent </code>来防止AWT递归地请求此KeyEventPostPro
     * cessor再次执行事件的后处理。
     * 通常,KeyEvent后处理将用于实现需要全局KeyEvent后处理的功能,例如菜单快捷方式。
     * 
     * @param e the KeyEvent to post-process
     * @return <code>true</code> if the AWT should take no further action with
     *         regard to the KeyEvent; <code>false</code> otherwise
     * @see KeyboardFocusManager#redispatchEvent
     */
    boolean postProcessKeyEvent(KeyEvent e);
}
