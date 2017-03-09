/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: ElemContext.java,v 1.2.4.1 2005/09/15 08:15:15 suresh_emailid Exp $
 * <p>
 *  $ Id：ElemContext.java,v 1.2.4.1 2005/09/15 08:15:15 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

/**
 * This class is a stack frame that consists of
 * information about the element currently being processed
 * by a serializer. Consider this example:
 * <pre>
 *   <A>
 *     <B1>
 *     </B1>
 *     <B2>
 *     </B2>
 *   <A>
 * </pre>
 *
 * A stack frame will be pushed for "A" at depth 1,
 * then another one for "B1" at depth 2.
 * Then "B1" stackframe is popped.  When the stack frame for "B2" is
 * pushed, this implementation re-uses the old stack fram object used
 * by "B1" to be efficient at not creating too many of these object.
 *
 * This is by no means a public class, and neither are its fields or methods,
 * they are all helper fields for a serializer.
 *
 * The purpose of this class is to be more consistent with pushing information
 * when a new element is being serialized and more quickly restoring the old
 * information about the parent element with a simple pop() when the
 * child element is done.  Previously there was some redundant and error-prone
 * calculations going on to retore information.
 *
 * @xsl.usage internal
 * <p>
 *  这个类是一个堆栈帧,由有关当前正在由串行器处理的元素的信息组成。考虑这个例子：
 * <pre>
 * <A>
 * <B1>
 * </B1>
 * <B2>
 * </B2>
 * <A>
 * </pre>
 * 
 *  堆栈帧将在深度1处推送"A",然后在深度2推送另一个用于"B1"。然后弹出"B1"堆栈帧。当推送"B2"的栈帧时,该实现重新使用由"B1"使用的旧栈帧对象来有效率地不创建这些对象的太多。
 * 
 *  这绝不是一个公共类,也不是它的字段或方法,它们都是串行器的帮助字段。
 * 
 * 这个类的目的是在新元素被序列化时更加一致地推送信息,并且当子元素完成时,使用简单的pop()来更快地恢复父元素的旧信息。以前有一些冗余和容易出错的计算来重新存储信息。
 * 
 *  @ xsl.usage internal
 * 
 */
final class ElemContext
{
    // Fields that form the context of the element

    /**
     * The nesting depth of the element inside other elements.
     * <p>
     *  元素在其他元素内的嵌套深度。
     * 
     */
    final int m_currentElemDepth;

    /** HTML field, the element description of the HTML element */
    ElemDesc m_elementDesc = null;

    /**
     * The local name of the element.
     * <p>
     *  元素的本地名称。
     * 
     */
    String m_elementLocalName = null;

    /**
     * The fully qualified name of the element (with prefix, if any).
     * <p>
     *  元素的完全限定名(带前缀,如果有)。
     * 
     */
    String m_elementName = null;

    /**
     * The URI of the element.
     * <p>
     *  元素的URI。
     * 
     */
    String m_elementURI = null;

    /** If the element is in the cdata-section-names list
     * then the value is true. If it is true the text children of the element
     * should be output in CDATA section blocks.
     * <p>
     *  那么值为true。如果为真,则元素的文本子元素应在CDATA段块中输出。
     * 
     */
    boolean m_isCdataSection;

    /** True if the current element has output escaping disabled.
     * This is true for SCRIPT and STYLE elements.
     * <p>
     *  这适用于SCRIPT和STYLE元素。
     * 
     */
    boolean m_isRaw = false;

    /** The next element "stack frame". This value will only be
     * set once as deeper stack frames are not deleted when popped off,
     * but are rather re-used when a push is required.
     *
     * This makes for very fast pushing and popping of stack frames
     * because very few stack frame objects are ever created, they are
     * mostly re-used.  This re-use saves object creation but it also means
     * that connections between the frames via m_next and m_prev
     * never changes either. Just the contents of the frames change
     * as they are re-used. Only the reference to the current stack frame, which
     * is held by the serializer is changed via a quick pop() or push().
     * <p>
     *  设置一次,因为更深的堆栈帧在弹出时不会被删除,而是在需要推送时重新使用。
     * 
     *  这使得栈帧的推送和弹出非常快,因为很少创建栈帧对象,它们大多被重复使用。这种重用会保存对象创建,但也意味着通过m_next和m_prev在帧之间的连接也不会改变。
     * 只是帧的内容随着它们被重新使用而改变。只有通过快速pop()或push()更改对序列化器保存的当前堆栈帧的引用。
     * 
     */
    private ElemContext m_next;

    /** The previous element "stack frame". */
    final ElemContext m_prev;

    /**
     * Set to true when a start tag is started, or open, but not all the
     * attributes or namespace information is yet collected.
     * <p>
     *  当开始标记开始或打开但未收集所有属性或命名空间信息时,设置为true。
     * 
     */
    boolean m_startTagOpen = false;

    /**
     * Constructor to create the root of the element contexts.
     *
     * <p>
     *  构造器创建元素上下文的根。
     * 
     */
    ElemContext()
    {
        // this assignment means can never pop this context off
        m_prev = this;
        // depth 0 because it doesn't correspond to any element
        m_currentElemDepth = 0;
    }

    /**
     * Constructor to create the "stack frame" for a given element depth.
     *
     * This implementation will re-use the context at each depth. If
     * a documents deepest element depth is N then there will be (N+1)
     * such objects created, no more than that.
     *
     * <p>
     * 构造器为给定元素深度创建"堆栈框架"。
     * 
     *  这个实现将在每个深度重复使用上下文。如果文档最深的元素深度为N,那么将创建(N + 1)个这样的对象,不超过。
     * 
     * 
     * @param previous The "stack frame" corresponding to the new
     * elements parent element.
     */
    private ElemContext(final ElemContext previous)
    {
        m_prev = previous;
        m_currentElemDepth = previous.m_currentElemDepth + 1;
    }

    /**
     * Pop the current "stack frame".
     * <p>
     *  弹出当前的"堆栈帧"。
     * 
     * 
     * @return Returns the parent "stack frame" of the one popped.
     */
    final ElemContext pop()
    {
        /* a very simple pop.  No clean up is done of the deeper
         * stack frame.  All deeper stack frames are still attached
         * but dormant, just waiting to be re-used.
         * <p>
         *  堆栈框架。所有更深的堆栈帧仍然连接,但休眠,只是等待重新使用。
         * 
         */
        return this.m_prev;
    }

    /**
     * This method pushes an element "stack frame"
     * but with no initialization of values in that frame.
     * This method is used for optimization purposes, like when pushing
     * a stack frame for an HTML "IMG" tag which has no children and
     * the stack frame will almost immediately be popped.
     * <p>
     *  此方法推送元素"堆栈帧",但不对该帧中的值进行初始化。这个方法用于优化目的,比如为没有子节点的HTML"IMG"标记推栈帧时,堆栈帧几乎立即被弹出。
     * 
     */
    final ElemContext push()
    {
        ElemContext frame = this.m_next;
        if (frame == null)
        {
            /* We have never been at this depth yet, and there is no
             * stack frame to re-use, so we now make a new one.
             * <p>
             *  堆栈帧重用,所以我们现在做一个新的。
             * 
             */
            frame = new ElemContext(this);
            this.m_next = frame;
        }
        /*
         * We shouldn't need to set this true because we should just
         * be pushing a dummy stack frame that will be instantly popped.
         * Yet we need to be ready in case this element does have
         * unexpected children.
         * <p>
         *  我们不应该设置为真,因为我们应该只是推一个虚拟的堆栈帧,将立即弹出。然而,我们需要准备好,如果这个元素有意想不到的孩子。
         * 
         */
        frame.m_startTagOpen = true;
        return frame;
    }

    /**
     * Push an element context on the stack. This context keeps track of
     * information gathered about the element.
     * <p>
     *  在堆栈上推送元素上下文。此上下文跟踪收集的有关元素的信息。
     * 
     * 
     * @param uri The URI for the namespace for the element name,
     * can be null if it is not yet known.
     * @param localName The local name of the element (no prefix),
     * can be null.
     * @param qName The qualified name (with prefix, if any)
     * of the element, this parameter is required.
     */
    final ElemContext push(
        final String uri,
        final String localName,
        final String qName)
    {
        ElemContext frame = this.m_next;
        if (frame == null)
        {
            /* We have never been at this depth yet, and there is no
             * stack frame to re-use, so we now make a new one.
             * <p>
             *  堆栈帧重用,所以我们现在做一个新的。
             */
            frame = new ElemContext(this);
            this.m_next = frame;
        }

        // Initialize, or reset values in the new or re-used stack frame.
        frame.m_elementName = qName;
        frame.m_elementLocalName = localName;
        frame.m_elementURI = uri;
        frame.m_isCdataSection = false;
        frame.m_startTagOpen = true;

        // is_Raw is already set in the HTML startElement() method
        // frame.m_isRaw = false;
        return frame;
    }
}
