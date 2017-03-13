/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2002,2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Node;

/**
 * XML provides the CDATA markup to allow a region of text in which
 * most of the XML delimiter recognition does not take place. This is
 * intended to ease the task of quoting XML fragments and other
 * programmatic information in a document's text without needing to
 * escape these special characters. It's primarily a convenience feature
 * for those who are hand-editing XML.
 * <P>
 * CDATASection is an Extended DOM feature, and is not used in HTML
 * contexts.
 * <P>
 * Within the DOM, CDATASections are treated essentially as Text
 * blocks. Their distinct type is retained in order to allow us to
 * properly recreate the XML syntax when we write them out.
 * <P>
 * Reminder: CDATA IS NOT A COMPLETELY GENERAL SOLUTION; it can't
 * quote its own end-of-block marking. If you need to write out a
 * CDATA that contains the ]]> sequence, it's your responsibility to
 * split that string over two successive CDATAs at that time.
 * <P>
 * CDATA does not participate in Element.normalize() processing.
 *
 * @xerces.internal
 *
 * <p>
 * XML提供了CDATA标记,以允许不进行大多数XML分隔符识别的文本区域这旨在简化在文档的文本中引用XML片段和其他编程信息的任务,而不需要转义这些特殊字符。主要是那些手动编辑XML的方便功能
 * <P>
 *  CDATASection是一个扩展DOM功能,不在HTML上下文中使用
 * <P>
 *  在DOM中,CDATASections基本上被处理为文本块它们的不同类型被保留,以便允许我们在写出它们时正确地重新创建XML语法
 * <P>
 * 提醒：CDATA不是一个完整的通用解决方案;它不能引用自己的块结束标记如果你需要写出一个包含]]>序列的CDATA,那么你的责任就是将那个字符串拆分成两个连续的CDATA
 * <P>
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class CDATASectionImpl
    extends TextImpl
    implements CDATASection {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 2372071297878177780L;

    //
    // Constructors
    //

    /** Factory constructor for creating a CDATA section. */
    public CDATASectionImpl(CoreDocumentImpl ownerDoc, String data) {
        super(ownerDoc, data);
    }

    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     *  CDATA不参与Elementnormalize()处理
     * 
     *  @xercesinternal
     * 
     */
    public short getNodeType() {
        return Node.CDATA_SECTION_NODE;
    }

    /** Returns the node name. */
    public String getNodeName() {
        return "#cdata-section";
    }

} // class CDATASectionImpl
