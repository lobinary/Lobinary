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

import org.w3c.dom.views.AbstractView;

/**
 * The <code>MouseEvent</code> interface provides specific contextual
 * information associated with Mouse events.
 * <p>The <code>detail</code> attribute inherited from <code>UIEvent</code>
 * indicates the number of times a mouse button has been pressed and
 * released over the same screen location during a user action. The
 * attribute value is 1 when the user begins this action and increments by 1
 * for each full sequence of pressing and releasing. If the user moves the
 * mouse between the mousedown and mouseup the value will be set to 0,
 * indicating that no click is occurring.
 * <p>In the case of nested elements mouse events are always targeted at the
 * most deeply nested element. Ancestors of the targeted element may use
 * bubbling to obtain notification of mouse events which occur within its
 * descendent elements.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>Document Object Model (DOM) Level 2 Events Specification</a>.
 * <p>
 * <code> MouseEvent </code>界面提供与鼠标事件相关的特定上下文信息。
 *  <p>继承自<code> UIEvent </code>的<code> detail </code>属性表示在用户操作期间在同一屏幕位置按下和释放鼠标按钮的次数。
 * 当用户开始此操作时,属性值为1,对于每个完整的按下和释放序列,属性值递增1。如果用户在mousedown和mouseup之间移动鼠标,该值将被设置为0,表示没有发生任何点击。
 *  <p>在嵌套元素的情况下,鼠标事件总是针对最深的嵌套元素。目标元素的祖先可以使用冒泡来获得在其派生元素内发生的鼠标事件的通知。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>文档对象模型(DOM)2级事件规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface MouseEvent extends UIEvent {
    /**
     * The horizontal coordinate at which the event occurred relative to the
     * origin of the screen coordinate system.
     * <p>
     *  事件发生的水平坐标相对于屏幕坐标系的原点。
     * 
     */
    public int getScreenX();

    /**
     * The vertical coordinate at which the event occurred relative to the
     * origin of the screen coordinate system.
     * <p>
     *  事件发生的垂直坐标相对于屏幕坐标系的原点。
     * 
     */
    public int getScreenY();

    /**
     * The horizontal coordinate at which the event occurred relative to the
     * DOM implementation's client area.
     * <p>
     *  事件发生的水平坐标相对于DOM实现的客户区。
     * 
     */
    public int getClientX();

    /**
     * The vertical coordinate at which the event occurred relative to the DOM
     * implementation's client area.
     * <p>
     *  事件发生的垂直坐标相对于DOM实现的客户区。
     * 
     */
    public int getClientY();

    /**
     * Used to indicate whether the 'ctrl' key was depressed during the firing
     * of the event.
     * <p>
     *  用于指示在触发事件期间是否按下"ctrl"键。
     * 
     */
    public boolean getCtrlKey();

    /**
     * Used to indicate whether the 'shift' key was depressed during the
     * firing of the event.
     * <p>
     * 用于指示在事件触发期间"shift"键是否被按下。
     * 
     */
    public boolean getShiftKey();

    /**
     * Used to indicate whether the 'alt' key was depressed during the firing
     * of the event. On some platforms this key may map to an alternative
     * key name.
     * <p>
     *  用于指示在事件触发期间"alt"键是否被按下。在某些平台上,此键可映射到替代键名称。
     * 
     */
    public boolean getAltKey();

    /**
     * Used to indicate whether the 'meta' key was depressed during the firing
     * of the event. On some platforms this key may map to an alternative
     * key name.
     * <p>
     *  用于指示在事件触发期间"meta"键是否被按下。在某些平台上,此键可映射到替代键名称。
     * 
     */
    public boolean getMetaKey();

    /**
     * During mouse events caused by the depression or release of a mouse
     * button, <code>button</code> is used to indicate which mouse button
     * changed state. The values for <code>button</code> range from zero to
     * indicate the left button of the mouse, one to indicate the middle
     * button if present, and two to indicate the right button. For mice
     * configured for left handed use in which the button actions are
     * reversed the values are instead read from right to left.
     * <p>
     *  在由按下或释放鼠标按钮引起的鼠标事件期间,使用<code>按钮</code>来指示哪个鼠标按钮改变了状态。
     *  <code>按钮</code>的值的范围从零到指示鼠标的左按钮,一个指示中间按钮(如果存在)和两个指示右按钮。对于配置为左手使用的鼠标,其中按钮动作反转,而是从右到左读取值。
     * 
     */
    public short getButton();

    /**
     * Used to identify a secondary <code>EventTarget</code> related to a UI
     * event. Currently this attribute is used with the mouseover event to
     * indicate the <code>EventTarget</code> which the pointing device
     * exited and with the mouseout event to indicate the
     * <code>EventTarget</code> which the pointing device entered.
     * <p>
     *  用于标识与UI事件相关的辅助<code> EventTarget </code>。
     * 当前,该属性与鼠标悬停事件一起用于指示定点设备退出的<code> EventTarget </code>,以及指示定点设备进入的<code> EventTarget </code>的mouseout事件
     * 。
     *  用于标识与UI事件相关的辅助<code> EventTarget </code>。
     * 
     */
    public EventTarget getRelatedTarget();

    /**
     * The <code>initMouseEvent</code> method is used to initialize the value
     * of a <code>MouseEvent</code> created through the
     * <code>DocumentEvent</code> interface. This method may only be called
     * before the <code>MouseEvent</code> has been dispatched via the
     * <code>dispatchEvent</code> method, though it may be called multiple
     * times during that phase if necessary. If called multiple times, the
     * final invocation takes precedence.
     * <p>
     * 
     * @param typeArg Specifies the event type.
     * @param canBubbleArg Specifies whether or not the event can bubble.
     * @param cancelableArg Specifies whether or not the event's default
     *   action can be prevented.
     * @param viewArg Specifies the <code>Event</code>'s
     *   <code>AbstractView</code>.
     * @param detailArg Specifies the <code>Event</code>'s mouse click count.
     * @param screenXArg Specifies the <code>Event</code>'s screen x
     *   coordinate
     * @param screenYArg Specifies the <code>Event</code>'s screen y
     *   coordinate
     * @param clientXArg Specifies the <code>Event</code>'s client x
     *   coordinate
     * @param clientYArg Specifies the <code>Event</code>'s client y
     *   coordinate
     * @param ctrlKeyArg Specifies whether or not control key was depressed
     *   during the <code>Event</code>.
     * @param altKeyArg Specifies whether or not alt key was depressed during
     *   the <code>Event</code>.
     * @param shiftKeyArg Specifies whether or not shift key was depressed
     *   during the <code>Event</code>.
     * @param metaKeyArg Specifies whether or not meta key was depressed
     *   during the <code>Event</code>.
     * @param buttonArg Specifies the <code>Event</code>'s mouse button.
     * @param relatedTargetArg Specifies the <code>Event</code>'s related
     *   <code>EventTarget</code>.
     */
    public void initMouseEvent(String typeArg,
                               boolean canBubbleArg,
                               boolean cancelableArg,
                               AbstractView viewArg,
                               int detailArg,
                               int screenXArg,
                               int screenYArg,
                               int clientXArg,
                               int clientYArg,
                               boolean ctrlKeyArg,
                               boolean altKeyArg,
                               boolean shiftKeyArg,
                               boolean metaKeyArg,
                               short buttonArg,
                               EventTarget relatedTargetArg);

}
