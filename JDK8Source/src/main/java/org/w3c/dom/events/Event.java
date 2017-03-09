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
 * The <code>Event</code> interface is used to provide contextual information
 * about an event to the handler processing the event. An object which
 * implements the <code>Event</code> interface is generally passed as the
 * first parameter to an event handler. More specific context information is
 * passed to event handlers by deriving additional interfaces from
 * <code>Event</code> which contain information directly relating to the
 * type of event they accompany. These derived interfaces are also
 * implemented by the object passed to the event listener.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>Document Object Model (DOM) Level 2 Events Specification</a>.
 * <p>
 *  <code> Event </code>接口用于向处理事件的处理程序提供有关事件的上下文信息。实现<code> Event </code>接口的对象通常作为第一个参数传递给事件处理程序。
 * 更具体的上下文信息通过从&lt; code&gt; Event&lt; / code&gt;导出附加接口来传递给事件处理程序,该接口包含与它们所伴随的事件类型直接相关的信息。
 * 这些派生接口也由传递给事件侦听器的对象实现。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>文档对象模型(DOM)2级事件规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface Event {
    // PhaseType
    /**
     * The current event phase is the capturing phase.
     * <p>
     *  当前事件阶段是捕获阶段。
     * 
     */
    public static final short CAPTURING_PHASE           = 1;
    /**
     * The event is currently being evaluated at the target
     * <code>EventTarget</code>.
     * <p>
     *  该事件当前正在目标<code> EventTarget </code>进行评估。
     * 
     */
    public static final short AT_TARGET                 = 2;
    /**
     * The current event phase is the bubbling phase.
     * <p>
     *  当前事件阶段是冒泡阶段。
     * 
     */
    public static final short BUBBLING_PHASE            = 3;

    /**
     * The name of the event (case-insensitive). The name must be an XML name.
     * <p>
     * 事件的名称(不区分大小写)。名称必须是XML名称。
     * 
     */
    public String getType();

    /**
     * Used to indicate the <code>EventTarget</code> to which the event was
     * originally dispatched.
     * <p>
     *  用于指示事件最初分派到的<code> EventTarget </code>。
     * 
     */
    public EventTarget getTarget();

    /**
     * Used to indicate the <code>EventTarget</code> whose
     * <code>EventListeners</code> are currently being processed. This is
     * particularly useful during capturing and bubbling.
     * <p>
     *  用于指示其<code> EventListeners </code>当前正在处理的<code> EventTarget </code>。这在捕获和起泡期间特别有用。
     * 
     */
    public EventTarget getCurrentTarget();

    /**
     * Used to indicate which phase of event flow is currently being
     * evaluated.
     * <p>
     *  用于指示当前正在评估的事件流的哪个阶段。
     * 
     */
    public short getEventPhase();

    /**
     * Used to indicate whether or not an event is a bubbling event. If the
     * event can bubble the value is true, else the value is false.
     * <p>
     *  用于指示事件是否是冒泡事件。如果事件可以冒泡的值为true,否则值为false。
     * 
     */
    public boolean getBubbles();

    /**
     * Used to indicate whether or not an event can have its default action
     * prevented. If the default action can be prevented the value is true,
     * else the value is false.
     * <p>
     *  用于指示事件是否可以阻止其默认操作。如果可以阻止默认操作,则值为true,否则值为false。
     * 
     */
    public boolean getCancelable();

    /**
     *  Used to specify the time (in milliseconds relative to the epoch) at
     * which the event was created. Due to the fact that some systems may
     * not provide this information the value of <code>timeStamp</code> may
     * be not available for all events. When not available, a value of 0
     * will be returned. Examples of epoch time are the time of the system
     * start or 0:0:0 UTC 1st January 1970.
     * <p>
     *  用于指定创建事件的时间(相对于历元的毫秒数)。由于某些系统可能不提供此信息,<code> timeStamp </code>的值可能不适用于所有事件。当不可用时,将返回值0。
     * 纪元时间的示例是系统启动的时间或1970年1月1日0：0：0 UTC。
     * 
     */
    public long getTimeStamp();

    /**
     * The <code>stopPropagation</code> method is used prevent further
     * propagation of an event during event flow. If this method is called
     * by any <code>EventListener</code> the event will cease propagating
     * through the tree. The event will complete dispatch to all listeners
     * on the current <code>EventTarget</code> before event flow stops. This
     * method may be used during any stage of event flow.
     * <p>
     *  <code> stopPropagation </code>方法用于阻止事件在事件流中进一步传播。如果任何<code> EventListener </code>调用此方法,则事件将停止通过树传播。
     * 在事件流停止之前,事件将完成对当前<code> EventTarget </code>上的所有侦听器的分派。该方法可以在事件流的任何阶段期间使用。
     * 
     */
    public void stopPropagation();

    /**
     * If an event is cancelable, the <code>preventDefault</code> method is
     * used to signify that the event is to be canceled, meaning any default
     * action normally taken by the implementation as a result of the event
     * will not occur. If, during any stage of event flow, the
     * <code>preventDefault</code> method is called the event is canceled.
     * Any default action associated with the event will not occur. Calling
     * this method for a non-cancelable event has no effect. Once
     * <code>preventDefault</code> has been called it will remain in effect
     * throughout the remainder of the event's propagation. This method may
     * be used during any stage of event flow.
     * <p>
     * 如果事件是可取消的,则使用<code> preventDefault </code>方法来表示事件将被取消,这意味着由于事件的结果,实现通常不会发生任何默认动作。
     * 如果在事件流的任何阶段期间,调用<code> preventDefault </code>方法,则事件被取消。将不会发生与事件相关联的任何默认操作。为不可取消事件调用此方法不起作用。
     * 一旦<code> preventDefault </code>被调用,它将在整个事件传播的剩余时间内保持有效。该方法可以在事件流的任何阶段期间使用。
     * 
     */
    public void preventDefault();

    /**
     * The <code>initEvent</code> method is used to initialize the value of an
     * <code>Event</code> created through the <code>DocumentEvent</code>
     * interface. This method may only be called before the
     * <code>Event</code> has been dispatched via the
     * <code>dispatchEvent</code> method, though it may be called multiple
     * times during that phase if necessary. If called multiple times the
     * final invocation takes precedence. If called from a subclass of
     * <code>Event</code> interface only the values specified in the
     * <code>initEvent</code> method are modified, all other attributes are
     * left unchanged.
     * <p>
     *  <code> initEvent </code>方法用于初始化通过<code> DocumentEvent </code>接口创建的<code> Event </code>的值。
     * 此方法只能在通过<code> dispatchEvent </code>方法分派<code> Event </code>之前调用,但如果需要,可以在该阶段多次调用。如果多次调用,则最终调用优先。
     * 
     * @param eventTypeArg Specifies the event type. This type may be any
     *   event type currently defined in this specification or a new event
     *   type.. The string must be an XML name. Any new event type must not
     *   begin with any upper, lower, or mixed case version of the string
     *   "DOM". This prefix is reserved for future DOM event sets. It is
     *   also strongly recommended that third parties adding their own
     *   events use their own prefix to avoid confusion and lessen the
     *   probability of conflicts with other new events.
     * @param canBubbleArg Specifies whether or not the event can bubble.
     * @param cancelableArg Specifies whether or not the event's default
     *   action can be prevented.
     */
    public void initEvent(String eventTypeArg,
                          boolean canBubbleArg,
                          boolean cancelableArg);

}
