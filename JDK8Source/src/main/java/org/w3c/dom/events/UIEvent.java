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
 * The <code>UIEvent</code> interface provides specific contextual information
 * associated with User Interface events.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>Document Object Model (DOM) Level 2 Events Specification</a>.
 * <p>
 *  <code> UIEvent </code>界面提供与用户界面事件相关联的特定上下文信息。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>文档对象模型(DOM)2级事件规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface UIEvent extends Event {
    /**
     * The <code>view</code> attribute identifies the <code>AbstractView</code>
     *  from which the event was generated.
     * <p>
     *  <code> view </code>属性标识生成事件的<code> AbstractView </code>。
     * 
     */
    public AbstractView getView();

    /**
     * Specifies some detail information about the <code>Event</code>,
     * depending on the type of event.
     * <p>
     *  指定有关<code> Event </code>的一些详细信息,具体取决于事件的类型。
     * 
     */
    public int getDetail();

    /**
     * The <code>initUIEvent</code> method is used to initialize the value of
     * a <code>UIEvent</code> created through the <code>DocumentEvent</code>
     * interface. This method may only be called before the
     * <code>UIEvent</code> has been dispatched via the
     * <code>dispatchEvent</code> method, though it may be called multiple
     * times during that phase if necessary. If called multiple times, the
     * final invocation takes precedence.
     * <p>
     *  <code> initUIEvent </code>方法用于初始化通过<code> DocumentEvent </code>接口创建的<code> UIEvent </code>的值。
     * 此方法只能在通过<code> dispatchEvent </code>方法分派<code> UIEvent </code>之前调用,但如果需要,可以在该阶段多次调用。如果多次调用,则最终调用优先。
     * 
     * @param typeArg Specifies the event type.
     * @param canBubbleArg Specifies whether or not the event can bubble.
     * @param cancelableArg Specifies whether or not the event's default
     *   action can be prevented.
     * @param viewArg Specifies the <code>Event</code>'s
     *   <code>AbstractView</code>.
     * @param detailArg Specifies the <code>Event</code>'s detail.
     */
    public void initUIEvent(String typeArg,
                            boolean canBubbleArg,
                            boolean cancelableArg,
                            AbstractView viewArg,
                            int detailArg);

}
