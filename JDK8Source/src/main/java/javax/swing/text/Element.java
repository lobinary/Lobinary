/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

/**
 * Interface to describe a structural piece of a document.  It
 * is intended to capture the spirit of an SGML element.
 *
 * <p>
 *  用于描述文档的结构部分的接口。它旨在捕获SGML元素的精神。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface Element {

    /**
     * Fetches the document associated with this element.
     *
     * <p>
     *  获取与此元素相关联的文档。
     * 
     * 
     * @return the document
     */
    public Document getDocument();

    /**
     * Fetches the parent element.  If the element is a root level
     * element returns <code>null</code>.
     *
     * <p>
     *  获取父元素。如果元素是根级别元素,则返回<code> null </code>。
     * 
     * 
     * @return the parent element
     */
    public Element getParentElement();

    /**
     * Fetches the name of the element.  If the element is used to
     * represent some type of structure, this would be the type
     * name.
     *
     * <p>
     *  获取元素的名称。如果元素用于表示某种类型的结构,这将是类型名称。
     * 
     * 
     * @return the element name
     */
    public String getName();

    /**
     * Fetches the collection of attributes this element contains.
     *
     * <p>
     *  获取此元素包含的属性集合。
     * 
     * 
     * @return the attributes for the element
     */
    public AttributeSet getAttributes();

    /**
     * Fetches the offset from the beginning of the document
     * that this element begins at.  If this element has
     * children, this will be the offset of the first child.
     * As a document position, there is an implied forward bias.
     *
     * <p>
     *  从此元素开始的文档开头获取偏移量。如果这个元素有孩子,这将是第一个孩子的偏移量。作为文件的位置,有一个暗示的正向偏差。
     * 
     * 
     * @return the starting offset &gt;= 0 and &lt; getEndOffset();
     * @see Document
     * @see AbstractDocument
     */
    public int getStartOffset();

    /**
     * Fetches the offset from the beginning of the document
     * that this element ends at.  If this element has
     * children, this will be the end offset of the last child.
     * As a document position, there is an implied backward bias.
     * <p>
     * All the default <code>Document</code> implementations
     * descend from <code>AbstractDocument</code>.
     * <code>AbstractDocument</code> models an implied break at the end of
     * the document. As a result of this, it is possible for this to
     * return a value greater than the length of the document.
     *
     * <p>
     *  从文档的开头获取此元素结束的偏移量。如果这个元素有孩子,这将是最后一个孩子的结束偏移量。作为文档位置,存在隐含的向后偏差。
     * <p>
     *  所有默认的<code> Document </code>实现从<code> AbstractDocument </code>下降。
     *  <code> AbstractDocument </code>会在文档末尾建立一个隐含的断点。作为其结果,可以返回大于文档长度的值。
     * 
     * 
     * @return the ending offset &gt; getStartOffset() and
     *     &lt;= getDocument().getLength() + 1
     * @see Document
     * @see AbstractDocument
     */
    public int getEndOffset();

    /**
     * Gets the child element index closest to the given offset.
     * The offset is specified relative to the beginning of the
     * document.  Returns <code>-1</code> if the
     * <code>Element</code> is a leaf, otherwise returns
     * the index of the <code>Element</code> that best represents
     * the given location.  Returns <code>0</code> if the location
     * is less than the start offset. Returns
     * <code>getElementCount() - 1</code> if the location is
     * greater than or equal to the end offset.
     *
     * <p>
     * 获取最接近给定偏移的子元素索引。偏移量相对于文档的开头指定。
     * 如果<code> Element </code>是叶,则返回<code> -1 </code>,否则返回最能代表给定位置的<code> Element </code>的索引。
     * 如果位置小于开始偏移,则返回<code> 0 </code>。如果位置大于或等于结束偏移量,则返回<code> getElementCount() -  1 </code>。
     * 
     * 
     * @param offset the specified offset &gt;= 0
     * @return the element index &gt;= 0
     */
    public int getElementIndex(int offset);

    /**
     * Gets the number of child elements contained by this element.
     * If this element is a leaf, a count of zero is returned.
     *
     * <p>
     *  获取此元素包含的子元素的数量。如果此元素是叶,则返回零计数。
     * 
     * 
     * @return the number of child elements &gt;= 0
     */
    public int getElementCount();

    /**
     * Fetches the child element at the given index.
     *
     * <p>
     *  在给定的索引处获取子元素。
     * 
     * 
     * @param index the specified index &gt;= 0
     * @return the child element
     */
    public Element getElement(int index);

    /**
     * Is this element a leaf element? An element that
     * <i>may</i> have children, even if it currently
     * has no children, would return <code>false</code>.
     *
     * <p>
     *  这个元素是叶元素吗? <i>可能</i>有子元素的元素,即使它目前没有子元素,也会返回<code> false </code>。
     * 
     * @return true if a leaf element else false
     */
    public boolean isLeaf();


}
