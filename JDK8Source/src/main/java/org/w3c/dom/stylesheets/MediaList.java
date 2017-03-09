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

package org.w3c.dom.stylesheets;

import org.w3c.dom.DOMException;

/**
 *  The <code>MediaList</code> interface provides the abstraction of an
 * ordered collection of media, without defining or constraining how this
 * collection is implemented. An empty list is the same as a list that
 * contains the medium <code>"all"</code>.
 * <p> The items in the <code>MediaList</code> are accessible via an integral
 * index, starting from 0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> MediaList </code>接口提供了有序的媒体集合的抽象,而没有定义或约束如何实现该集合。空列表与包含介质<code>"all"</code>的列表相同。
 *  <p> <code> MediaList </code>中的项目可通过整数索引从0开始访问。
 * <p>另请参阅<a href ='http：//www.w3.org/TR/2000 / REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范</a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface MediaList {
    /**
     *  The parsable textual representation of the media list. This is a
     * comma-separated list of media.
     * <p>
     *  媒体列表的可解析文本表示。这是以逗号分隔的媒体列表。
     * 
     */
    public String getMediaText();
    /**
     *  The parsable textual representation of the media list. This is a
     * comma-separated list of media.
     * <p>
     *  媒体列表的可解析文本表示。这是以逗号分隔的媒体列表。
     * 
     * 
     * @exception DOMException
     *   SYNTAX_ERR: Raised if the specified string value has a syntax error
     *   and is unparsable.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this media list is
     *   readonly.
     */
    public void setMediaText(String mediaText)
                             throws DOMException;

    /**
     *  The number of media in the list. The range of valid media is
     * <code>0</code> to <code>length-1</code> inclusive.
     * <p>
     *  列表中的媒体数。有效介质的范围是<code> 0 </code>到<code> length-1 </code>(含)。
     * 
     */
    public int getLength();

    /**
     *  Returns the <code>index</code>th in the list. If <code>index</code> is
     * greater than or equal to the number of media in the list, this
     * returns <code>null</code>.
     * <p>
     * 返回列表中的<code> index </code> th。如果<code> index </code>大于或等于列表中的媒体数,则返回<code> null </code>。
     * 
     * 
     * @param index  Index into the collection.
     * @return  The medium at the <code>index</code>th position in the
     *   <code>MediaList</code>, or <code>null</code> if that is not a valid
     *   index.
     */
    public String item(int index);

    /**
     *  Deletes the medium indicated by <code>oldMedium</code> from the list.
     * <p>
     *  从列表中删除<code> oldMedium </code>所指示的介质。
     * 
     * 
     * @param oldMedium The medium to delete in the media list.
     * @exception DOMException
     *    NO_MODIFICATION_ALLOWED_ERR: Raised if this list is readonly.
     *   <br> NOT_FOUND_ERR: Raised if <code>oldMedium</code> is not in the
     *   list.
     */
    public void deleteMedium(String oldMedium)
                             throws DOMException;

    /**
     *  Adds the medium <code>newMedium</code> to the end of the list. If the
     * <code>newMedium</code> is already used, it is first removed.
     * <p>
     *  将媒体<code> newMedium </code>添加到列表的末尾。如果已经使用<code> newMedium </code>,它将首先被删除。
     * 
     * @param newMedium The new medium to add.
     * @exception DOMException
     *    INVALID_CHARACTER_ERR: If the medium contains characters that are
     *   invalid in the underlying style language.
     *   <br> NO_MODIFICATION_ALLOWED_ERR: Raised if this list is readonly.
     */
    public void appendMedium(String newMedium)
                             throws DOMException;

}
