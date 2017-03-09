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
 *  The <code>EventListener</code> interface is the primary method for
 * handling events. Users implement the <code>EventListener</code> interface
 * and register their listener on an <code>EventTarget</code> using the
 * <code>AddEventListener</code> method. The users should also remove their
 * <code>EventListener</code> from its <code>EventTarget</code> after they
 * have completed using the listener.
 * <p> When a <code>Node</code> is copied using the <code>cloneNode</code>
 * method the <code>EventListener</code>s attached to the source
 * <code>Node</code> are not attached to the copied <code>Node</code>. If
 * the user wishes the same <code>EventListener</code>s to be added to the
 * newly created copy the user must add them manually.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>Document Object Model (DOM) Level 2 Events Specification</a>.
 * <p>
 *  <code> EventListener </code>接口是处理事件的主要方法。
 * 用户实现<code> EventListener </code>接口,并使用<code> AddEventListener </code>方法在<code> EventTarget </code>上注册
 * 它们的监听器。
 *  <code> EventListener </code>接口是处理事件的主要方法。
 * 用户在完成使用侦听器后,还应从其<code> EventTarget </code>中删除<code> EventListener </code>。
 *  <p>当使用<code> cloneNode </code>方法复制<code> Node </code>时,附加到源<code> Node </code>的<code> EventListener 
 * </code>附加到复制的<code> Node </code>。
 * 
 * @since DOM Level 2
 */
public interface EventListener {
    /**
     *  This method is called whenever an event occurs of the type for which
     * the <code> EventListener</code> interface was registered.
     * <p>
     * 用户在完成使用侦听器后,还应从其<code> EventTarget </code>中删除<code> EventListener </code>。
     * 如果用户希望将相同的<code> EventListener </code>添加到新创建的副本,则用户必须手动添加它们。
     *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>文档对象模型(DOM)2级事件规范< a>。
     * 
     * 
     * @param evt  The <code>Event</code> contains contextual information
     *   about the event. It also contains the <code>stopPropagation</code>
     *   and <code>preventDefault</code> methods which are used in
     *   determining the event's flow and default action.
     */
    public void handleEvent(Event evt);

}
