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
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究信息学和数学联合会,庆应大学)保留所有权利本作品根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证
 * 
 *  [1] http：// wwww3org / Consortium / Legal / 2002 / copyright-software-20021231
 * 
 */

package org.w3c.dom.ls;

import org.w3c.dom.events.Event;

/**
 *  This interface represents a progress event object that notifies the
 * application about progress as a document is parsed. It extends the
 * <code>Event</code> interface defined in [<a href='http://www.w3.org/TR/2003/NOTE-DOM-Level-3-Events-20031107'>DOM Level 3 Events</a>]
 * .
 * <p> The units used for the attributes <code>position</code> and
 * <code>totalSize</code> are not specified and can be implementation and
 * input dependent.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 * <p>
 * 此接口代表一个进度事件对象,它通知应用程序关于文档解析时的进度扩展<[href ='http：// wwww3org / TR / 2003 / NOTE-]中定义的<code> Event </code>
 *  DOM-Level-3-Events-20031107'> DOM Level 3 Events </a>] <p>未指定用于属性<code> position </code>和<code> tota
 * lSize </code>的单位可以实现和输入依赖<p>另请参见<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-LS-20040407'>文档对象模型(
 * DOM)3级负载和保存规范</a>。
 * 
 */
public interface LSProgressEvent extends Event {
    /**
     * The input source that is being parsed.
     * <p>
     *  正在解析的输入源
     * 
     */
    public LSInput getInput();

    /**
     * The current position in the input source, including all external
     * entities and other resources that have been read.
     * <p>
     *  输入源中的当前位置,包括已读取的所有外部实体和其他资源
     * 
     */
    public int getPosition();

    /**
     * The total size of the document including all external resources, this
     * number might change as a document is being parsed if references to
     * more external resources are seen. A value of <code>0</code> is
     * returned if the total size cannot be determined or estimated.
     * <p>
     * 包含所有外部资源的文档的总大小,如果查看更多外部资源的引用,则此数字可能会随着文档的解析而改变。如果无法确定总大小,则会返回<code> 0 </code>的值;估计
     */
    public int getTotalSize();

}
