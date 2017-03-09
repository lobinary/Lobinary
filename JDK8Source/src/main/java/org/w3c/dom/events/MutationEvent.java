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

import org.w3c.dom.Node;

/**
 * The <code>MutationEvent</code> interface provides specific contextual
 * information associated with Mutation events.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>Document Object Model (DOM) Level 2 Events Specification</a>.
 * <p>
 *  <code> MutationEvent </code>接口提供与Mutation事件相关联的特定上下文信息。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>文档对象模型(DOM)2级事件规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface MutationEvent extends Event {
    // attrChangeType
    /**
     * The <code>Attr</code> was modified in place.
     * <p>
     *  <code> Attr </code>已修改。
     * 
     */
    public static final short MODIFICATION              = 1;
    /**
     * The <code>Attr</code> was just added.
     * <p>
     *  刚刚添加了<code> Attr </code>。
     * 
     */
    public static final short ADDITION                  = 2;
    /**
     * The <code>Attr</code> was just removed.
     * <p>
     *  <code> Attr </code>刚删除。
     * 
     */
    public static final short REMOVAL                   = 3;

    /**
     *  <code>relatedNode</code> is used to identify a secondary node related
     * to a mutation event. For example, if a mutation event is dispatched
     * to a node indicating that its parent has changed, the
     * <code>relatedNode</code> is the changed parent. If an event is
     * instead dispatched to a subtree indicating a node was changed within
     * it, the <code>relatedNode</code> is the changed node. In the case of
     * the DOMAttrModified event it indicates the <code>Attr</code> node
     * which was modified, added, or removed.
     * <p>
     *  <code> relatedNode </code>用于标识与突变事件相关的次要节点。
     * 例如,如果将突变事件分派给指示其父代更改的节点,则<code> relatedNode </code>是已更改的父代。
     * 如果事件被分派到子树,指示节点在其中被改变,则<code> relatedNode </code>是被改变的节点。
     * 在DOMAttrModified事件的情况下,它指示被修改,添加或删除的<code> Attr </code>节点。
     * 
     */
    public Node getRelatedNode();

    /**
     *  <code>prevValue</code> indicates the previous value of the
     * <code>Attr</code> node in DOMAttrModified events, and of the
     * <code>CharacterData</code> node in DOMCharacterDataModified events.
     * <p>
     * <code> prevValue </code>指示DOMAttrModified事件中的<code> Attr </code>节点和DOMCharacterDataModified事件中的<code>
     *  CharacterData </code>节点的上一个值。
     * 
     */
    public String getPrevValue();

    /**
     *  <code>newValue</code> indicates the new value of the <code>Attr</code>
     * node in DOMAttrModified events, and of the <code>CharacterData</code>
     * node in DOMCharacterDataModified events.
     * <p>
     *  <code> newValue </code>表示DOMAttrModified事件中的<code> Attr </code>节点和DOMCharacterDataModified事件中的<code>
     *  CharacterData </code>节点的新值。
     * 
     */
    public String getNewValue();

    /**
     *  <code>attrName</code> indicates the name of the changed
     * <code>Attr</code> node in a DOMAttrModified event.
     * <p>
     *  <code> attrName </code>表示DOMAttrModified事件中已更改的<code> Attr </code>节点的名称。
     * 
     */
    public String getAttrName();

    /**
     *  <code>attrChange</code> indicates the type of change which triggered
     * the DOMAttrModified event. The values can be <code>MODIFICATION</code>
     * , <code>ADDITION</code>, or <code>REMOVAL</code>.
     * <p>
     *  <code> attrChange </code>表示触发DOMAttrModified事件的更改类型。
     * 值可以是<code> MODIFICATION </code>,<code> ADDITION </code>或<code> REMOVAL </code>。
     * 
     */
    public short getAttrChange();

    /**
     * The <code>initMutationEvent</code> method is used to initialize the
     * value of a <code>MutationEvent</code> created through the
     * <code>DocumentEvent</code> interface. This method may only be called
     * before the <code>MutationEvent</code> has been dispatched via the
     * <code>dispatchEvent</code> method, though it may be called multiple
     * times during that phase if necessary. If called multiple times, the
     * final invocation takes precedence.
     * <p>
     *  <code> initMutationEvent </code>方法用于初始化通过<code> DocumentEvent </code>接口创建的<code> MutationEvent </code>
     * 的值。
     * 此方法只能在通过<code> dispatchEvent </code>方法分派<code> MutationEvent </code>之前调用,但如果必要,可以在该阶段多次调用。
     * 
     * @param typeArg Specifies the event type.
     * @param canBubbleArg Specifies whether or not the event can bubble.
     * @param cancelableArg Specifies whether or not the event's default
     *   action can be prevented.
     * @param relatedNodeArg Specifies the <code>Event</code>'s related Node.
     * @param prevValueArg Specifies the <code>Event</code>'s
     *   <code>prevValue</code> attribute. This value may be null.
     * @param newValueArg Specifies the <code>Event</code>'s
     *   <code>newValue</code> attribute. This value may be null.
     * @param attrNameArg Specifies the <code>Event</code>'s
     *   <code>attrName</code> attribute. This value may be null.
     * @param attrChangeArg Specifies the <code>Event</code>'s
     *   <code>attrChange</code> attribute
     */
    public void initMutationEvent(String typeArg,
                                  boolean canBubbleArg,
                                  boolean cancelableArg,
                                  Node relatedNodeArg,
                                  String prevValueArg,
                                  String newValueArg,
                                  String attrNameArg,
                                  short attrChangeArg);

}
