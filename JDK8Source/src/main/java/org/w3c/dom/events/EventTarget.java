/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2000 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.events;

/**
 *  The <code>EventTarget</code> interface is implemented by all
 * <code>Nodes</code> in an implementation which supports the DOM Event
 * Model. Therefore, this interface can be obtained by using
 * binding-specific casting methods on an instance of the <code>Node</code>
 * interface. The interface allows registration and removal of
 * <code>EventListeners</code> on an <code>EventTarget</code> and dispatch
 * of events to that <code>EventTarget</code>.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>Document Object Model (DOM) Level 2 Events Specification</a>.
 * <p>
 *  <code> EventTarget </code>接口由支持DOM事件模型的实现中的所有<code> Nodes </code>实现。
 * 因此,可以通过在<code> Node </code>接口的实例上使用特定于绑定的转换方法来获得此接口。
 * 该接口允许在<code> EventTarget </code>上注册和删除<code> EventListeners </code>并将事件分派到<code> EventTarget </code>。
 * 因此,可以通过在<code> Node </code>接口的实例上使用特定于绑定的转换方法来获得此接口。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>文档对象模型(DOM)2级事件规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface EventTarget {
    /**
     * This method allows the registration of event listeners on the event
     * target. If an <code>EventListener</code> is added to an
     * <code>EventTarget</code> while it is processing an event, it will not
     * be triggered by the current actions but may be triggered during a
     * later stage of event flow, such as the bubbling phase.
     * <br> If multiple identical <code>EventListener</code>s are registered
     * on the same <code>EventTarget</code> with the same parameters the
     * duplicate instances are discarded. They do not cause the
     * <code>EventListener</code> to be called twice and since they are
     * discarded they do not need to be removed with the
     * <code>removeEventListener</code> method.
     * <p>
     * 此方法允许在事件目标上注册事件侦听器。
     * 如果在处理事件时将<code> EventListener </code>添加到<code> EventTarget </code>中,则它不会由当前动作触发,而是可以在事件流的后期阶段触发,例如鼓泡阶
     * 段。
     * 此方法允许在事件目标上注册事件侦听器。
     *  <br>如果在相同的<code> EventTarget </code>中使用相同的参数注册多个相同的<code> EventListener </code>,则会丢弃重复的实例。
     * 它们不会导致<code> EventListener </code>被调用两次,由于它们被丢弃,因此不需要使用<code> removeEventListener </code>方法删除它们。
     * 
     * 
     * @param type The event type for which the user is registering
     * @param listener The <code>listener</code> parameter takes an interface
     *   implemented by the user which contains the methods to be called
     *   when the event occurs.
     * @param useCapture If true, <code>useCapture</code> indicates that the
     *   user wishes to initiate capture. After initiating capture, all
     *   events of the specified type will be dispatched to the registered
     *   <code>EventListener</code> before being dispatched to any
     *   <code>EventTargets</code> beneath them in the tree. Events which
     *   are bubbling upward through the tree will not trigger an
     *   <code>EventListener</code> designated to use capture.
     */
    public void addEventListener(String type,
                                 EventListener listener,
                                 boolean useCapture);

    /**
     * This method allows the removal of event listeners from the event
     * target. If an <code>EventListener</code> is removed from an
     * <code>EventTarget</code> while it is processing an event, it will not
     * be triggered by the current actions. <code>EventListener</code>s can
     * never be invoked after being removed.
     * <br>Calling <code>removeEventListener</code> with arguments which do
     * not identify any currently registered <code>EventListener</code> on
     * the <code>EventTarget</code> has no effect.
     * <p>
     *  此方法允许从事件目标中删除事件侦听器。如果在处理事件时从<code> EventTarget </code>中删除<code> EventListener </code>,则它不会被当前操作触发。
     *  <code> EventListener </code>在删除后永远不能被调用。
     *  <br>使用不标识<code> EventTarget </code>上当前注册的任何<code> EventListener </code>的参数调用<code> removeEventListen
     * er </code>没有任何效果。
     *  <code> EventListener </code>在删除后永远不能被调用。
     * 
     * @param type Specifies the event type of the <code>EventListener</code>
     *   being removed.
     * @param listener The <code>EventListener</code> parameter indicates the
     *   <code>EventListener </code> to be removed.
     * @param useCapture Specifies whether the <code>EventListener</code>
     *   being removed was registered as a capturing listener or not. If a
     *   listener was registered twice, one with capture and one without,
     *   each must be removed separately. Removal of a capturing listener
     *   does not affect a non-capturing version of the same listener, and
     *   vice versa.
     */
    public void removeEventListener(String type,
                                    EventListener listener,
                                    boolean useCapture);

    /**
     * This method allows the dispatch of events into the implementations
     * event model. Events dispatched in this manner will have the same
     * capturing and bubbling behavior as events dispatched directly by the
     * implementation. The target of the event is the
     * <code> EventTarget</code> on which <code>dispatchEvent</code> is
     * called.
     * <p>
     * 
     * 
     * @param evt Specifies the event type, behavior, and contextual
     *   information to be used in processing the event.
     * @return The return value of <code>dispatchEvent</code> indicates
     *   whether any of the listeners which handled the event called
     *   <code>preventDefault</code>. If <code>preventDefault</code> was
     *   called the value is false, else the value is true.
     * @exception EventException
     *   UNSPECIFIED_EVENT_TYPE_ERR: Raised if the <code>Event</code>'s type
     *   was not specified by initializing the event before
     *   <code>dispatchEvent</code> was called. Specification of the
     *   <code>Event</code>'s type as <code>null</code> or an empty string
     *   will also trigger this exception.
     */
    public boolean dispatchEvent(Event evt)
                                 throws EventException;

}
