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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Text nodes hold the non-markup, non-Entity content of
 * an Element or Attribute.
 * <P>
 * When a document is first made available to the DOM, there is only
 * one Text object for each block of adjacent plain-text. Users (ie,
 * applications) may create multiple adjacent Texts during editing --
 * see {@link org.w3c.dom.Element#normalize} for discussion.
 * <P>
 * Note that CDATASection is a subclass of Text. This is conceptually
 * valid, since they're really just two different ways of quoting
 * characters when they're written out as part of an XML stream.
 *
 * @xerces.internal
 *
 * <p>
 *  文本节点保存元素或属性的非标记,非实体内容。
 * <P>
 *  当文档首次可用于DOM时,每个相邻纯文本块只有一个Text对象。
 * 用户(即应用程序)可以在编辑期间创建多个相邻的文本 - 请参阅{@link org.w3c.dom.Element#normalize}以供讨论。
 * <P>
 *  注意CDATASection是Text的子类。这在概念上是有效的,因为当它们作为XML流的一部分被写出时,它们只是两种不同的引用字符的方式。
 * 
 *  @ xerces.internal
 * 
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class TextImpl
    extends CharacterDataImpl
    implements CharacterData, Text {

    //
    // Private Data members
    //


    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -5294980852957403469L;

    //
    // Constructors
    //

    /** Default constructor */
    public TextImpl(){}

    /** Factory constructor. */
    public TextImpl(CoreDocumentImpl ownerDoc, String data) {
        super(ownerDoc, data);
    }

    /**
     * NON-DOM: resets node and sets specified values for the current node
     *
     * <p>
     *  NON-DOM：重置节点并为当前节点设置指定的值
     * 
     * 
     * @param ownerDoc
     * @param data
     */
    public void setValues(CoreDocumentImpl ownerDoc, String data){

        flags=0;
        nextSibling = null;
        previousSibling=null;
        setOwnerDocument(ownerDoc);
        super.data = data;
    }
    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     *  指示这是什么类型的节点的短整数。此值的命名常量在org.w3c.dom.Node接口中定义。
     * 
     */
    public short getNodeType() {
        return Node.TEXT_NODE;
    }

    /** Returns the node name. */
    public String getNodeName() {
        return "#text";
    }

    /**
     * NON-DOM: Set whether this Text is ignorable whitespace.
     * <p>
     * NON-DOM：设置此文本是否为可忽略的空格。
     * 
     */
    public void setIgnorableWhitespace(boolean ignore) {

        if (needsSyncData()) {
            synchronizeData();
        }
        isIgnorableWhitespace(ignore);

    } // setIgnorableWhitespace(boolean)


    /**
     * DOM L3 Core CR - Experimental
     *
     * Returns whether this text node contains
     * element content whitespace</a>, often abusively called "ignorable whitespace".
     * The text node is determined to contain whitespace in element content
     * during the load of the document or if validation occurs while using
     * <code>Document.normalizeDocument()</code>.
     * <p>
     *  DOM L3核心CR  - 实验
     * 
     *  返回此文本节点是否包含元素内容空格</a>,通常称为"可忽略的空格"。
     * 在文档加载期间或者如果使用<code> Document.normalizeDocument()</code>时进行验证时,文本节点被确定为在元素内容中包含空格。
     * 
     * 
     * @since DOM Level 3
     */
    public boolean isElementContentWhitespace() {
        // REVISIT: is this implemenation correct?
        if (needsSyncData()) {
            synchronizeData();
        }
        return internalIsIgnorableWhitespace();
    }


    /**
     * DOM Level 3 WD - Experimental.
     * Returns all text of <code>Text</code> nodes logically-adjacent text
     * nodes to this node, concatenated in document order.
     * <p>
     *  DOM 3级WD  - 实验。将按文档顺序连接的<code> Text </code>节点逻辑相邻文本节点的所有文本返回到此节点。
     * 
     * 
     * @since DOM Level 3
     */
    public String getWholeText(){

        if (needsSyncData()) {
            synchronizeData();
        }

        if (fBufferStr == null){
            fBufferStr = new StringBuffer();
        }
        else {
            fBufferStr.setLength(0);
        }
        if (data != null && data.length() != 0) {
            fBufferStr.append(data);
        }

        //concatenate text of logically adjacent text nodes to the left of this node in the tree
        getWholeTextBackward(this.getPreviousSibling(), fBufferStr, this.getParentNode());
        String temp = fBufferStr.toString();

        //clear buffer
        fBufferStr.setLength(0);

        //concatenate text of logically adjacent text nodes to the right of this node in the tree
        getWholeTextForward(this.getNextSibling(), fBufferStr, this.getParentNode());

        return temp + fBufferStr.toString();

    }

    /**
     * internal method taking a StringBuffer in parameter and inserts the
     * text content at the start of the buffer
     *
     * <p>
     *  内部方法在参数中使用StringBuffer in,并将文本内容插入到缓冲区的开头
     * 
     * 
     * @param buf
     */
    protected void insertTextContent(StringBuffer buf) throws DOMException {
         String content = getNodeValue();
         if (content != null) {
             buf.insert(0, content);
         }
     }

    /**
     * Concatenates the text of all logically-adjacent text nodes to the
     * right of this node
     * <p>
     *  将所有逻辑相邻文本节点的文本连接到此节点的右侧
     * 
     * 
     * @param node
     * @param buffer
     * @param parent
     * @return true - if execution was stopped because the type of node
     *         other than EntityRef, Text, CDATA is encountered, otherwise
     *         return false
     */
    private boolean getWholeTextForward(Node node, StringBuffer buffer, Node parent){
        // boolean to indicate whether node is a child of an entity reference
        boolean inEntRef = false;

        if (parent!=null) {
                inEntRef = parent.getNodeType()==Node.ENTITY_REFERENCE_NODE;
        }

        while (node != null) {
            short type = node.getNodeType();
            if (type == Node.ENTITY_REFERENCE_NODE) {
                if (getWholeTextForward(node.getFirstChild(), buffer, node)){
                    return true;
                }
            }
            else if (type == Node.TEXT_NODE ||
                     type == Node.CDATA_SECTION_NODE) {
                ((NodeImpl)node).getTextContent(buffer);
            }
            else {
                return true;
            }

            node = node.getNextSibling();
        }

        // if the parent node is an entity reference node, must
        // check nodes to the right of the parent entity reference node for logically adjacent
        // text nodes
        if (inEntRef) {
            getWholeTextForward(parent.getNextSibling(), buffer, parent.getParentNode());
                        return true;
        }

        return false;
    }

    /**
     * Concatenates the text of all logically-adjacent text nodes to the left of
     * the node
     * <p>
     *  将所有逻辑相邻文本节点的文本连接到节点的左侧
     * 
     * 
     * @param node
     * @param buffer
     * @param parent
     * @return true - if execution was stopped because the type of node
     *         other than EntityRef, Text, CDATA is encountered, otherwise
     *         return false
     */
    private boolean getWholeTextBackward(Node node, StringBuffer buffer, Node parent){

        // boolean to indicate whether node is a child of an entity reference
        boolean inEntRef = false;
        if (parent!=null) {
                inEntRef = parent.getNodeType()==Node.ENTITY_REFERENCE_NODE;
        }

        while (node != null) {
            short type = node.getNodeType();
            if (type == Node.ENTITY_REFERENCE_NODE) {
                if (getWholeTextBackward(node.getLastChild(), buffer, node)){
                    return true;
                }
            }
            else if (type == Node.TEXT_NODE ||
                     type == Node.CDATA_SECTION_NODE) {
                ((TextImpl)node).insertTextContent(buffer);
            }
            else {
                return true;
            }

            node = node.getPreviousSibling();
        }

        // if the parent node is an entity reference node, must
        // check nodes to the left of the parent entity reference node for logically adjacent
        // text nodes
        if (inEntRef) {
                getWholeTextBackward(parent.getPreviousSibling(), buffer, parent.getParentNode());
            return true;
        }

        return false;
    }

    /**
     * Replaces the text of the current node and all logically-adjacent text
     * nodes with the specified text. All logically-adjacent text nodes are
     * removed including the current node unless it was the recipient of the
     * replacement text.
     *
     * <p>
     *  用指定的文本替换当前节点的文本和所有逻辑相邻的文本节点。除去包括当前节点的所有逻辑相邻文本节点,除非它是替换文本的接收者。
     * 
     * 
     * @param content
     *            The content of the replacing Text node.
     * @return text - The Text node created with the specified content.
     * @since DOM Level 3
     */
    public Text replaceWholeText(String content) throws DOMException {

        if (needsSyncData()) {
            synchronizeData();
        }

        //if the content is null
        Node parent = this.getParentNode();
        if (content == null || content.length() == 0) {
            // remove current node
            if (parent != null) { // check if node in the tree
                parent.removeChild(this);
            }
            return null;
        }

        // make sure we can make the replacement
        if (ownerDocument().errorChecking) {
            if (!canModifyPrev(this)) {
                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,
                        DOMMessageFormatter.formatMessage(
                                DOMMessageFormatter.DOM_DOMAIN,
                                "NO_MODIFICATION_ALLOWED_ERR", null));
            }

            // make sure we can make the replacement
            if (!canModifyNext(this)) {
                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,
                        DOMMessageFormatter.formatMessage(
                                DOMMessageFormatter.DOM_DOMAIN,
                                "NO_MODIFICATION_ALLOWED_ERR", null));
            }
        }

        //replace the text node
        Text currentNode = null;
        if (isReadOnly()) {
            Text newNode = this.ownerDocument().createTextNode(content);
            if (parent != null) { // check if node in the tree
                parent.insertBefore(newNode, this);
                parent.removeChild(this);
                currentNode = newNode;
            } else {
                return newNode;
            }
        } else {
            this.setData(content);
            currentNode = this;
        }

        //check logically-adjacent text nodes
        Node prev = currentNode.getPreviousSibling();
        while (prev != null) {
            //If the logically-adjacent next node can be removed
            //remove it. A logically adjacent node can be removed if
            //it is a Text or CDATASection node or an EntityReference with
            //Text and CDATA only children.
            if ((prev.getNodeType() == Node.TEXT_NODE)
                    || (prev.getNodeType() == Node.CDATA_SECTION_NODE)
                    || (prev.getNodeType() == Node.ENTITY_REFERENCE_NODE && hasTextOnlyChildren(prev))) {
                parent.removeChild(prev);
                prev = currentNode;
            } else {
                break;
            }
            prev = prev.getPreviousSibling();
        }

        //check logically-adjacent text nodes
        Node next = currentNode.getNextSibling();
        while (next != null) {
            //If the logically-adjacent next node can be removed
            //remove it. A logically adjacent node can be removed if
            //it is a Text or CDATASection node or an EntityReference with
            //Text and CDATA only children.
            if ((next.getNodeType() == Node.TEXT_NODE)
                    || (next.getNodeType() == Node.CDATA_SECTION_NODE)
                    || (next.getNodeType() == Node.ENTITY_REFERENCE_NODE && hasTextOnlyChildren(next))) {
                parent.removeChild(next);
                next = currentNode;
            } else {
                break;
            }
            next = next.getNextSibling();
        }

        return currentNode;
    }

    /**
     * If any EntityReference to be removed has descendants that are not
     * EntityReference, Text, or CDATASection nodes, the replaceWholeText method
     * must fail before performing any modification of the document, raising a
     * DOMException with the code NO_MODIFICATION_ALLOWED_ERR. Traverse previous
     * siblings of the node to be replaced. If a previous sibling is an
     * EntityReference node, get it's last child. If the last child was a Text
     * or CDATASection node and its previous siblings are neither a replaceable
     * EntityReference or Text or CDATASection nodes, return false. IF the last
     * child was neither Text nor CDATASection nor a replaceable EntityReference
     * Node, then return true. If the last child was a Text or CDATASection node
     * any its previous sibling was not or was an EntityReference that did not
     * contain only Text or CDATASection nodes, return false. Check this
     * recursively for EntityReference nodes.
     *
     * <p>
     * 如果要删除的任何EntityReference具有不是EntityReference,Text或CDATASection节点的后代,则replaceWholeText方法必须在对文档执行任何修改之前失败
     * ,从而产生具有代码NO_MODIFICATION_ALLOWED_ERR的DOMException。
     * 遍历要替换的节点的先前兄弟。如果先前的兄弟节点是EntityReference节点,则获取它的最后一个子节点。
     * 如果最后一个子节点是Text或CDATASection节点,并且其先前的兄弟节点既不是可替换的EntityReference,也不是Text或CDATASection节点,则返回false。
     * 如果最后一个子节点既不是Text也不是CDATASection,也不是可替换的EntityReference节点,则返回true。
     * 如果最后一个子节点是Text或CDATASection节点,则其前一个兄弟节点不是或者是不仅包含Text或CDATASection节点的EntityReference,返回false。
     * 以递归方式检查EntityReference节点。
     * 
     * 
     * @param node
     * @return true - can replace text false - can't replace exception must be
     *         raised
     */
    private boolean canModifyPrev(Node node) {
        boolean textLastChild = false;

        Node prev = node.getPreviousSibling();

        while (prev != null) {

            short type = prev.getNodeType();

            if (type == Node.ENTITY_REFERENCE_NODE) {
                //If the previous sibling was entityreference
                //check if its content is replaceable
                Node lastChild = prev.getLastChild();

                //if the entity reference has no children
                //return false
                if (lastChild == null) {
                    return false;
                }

                //The replacement text of the entity reference should
                //be either only text,cadatsections or replaceable entity
                //reference nodes or the last child should be neither of these
                while (lastChild != null) {
                    short lType = lastChild.getNodeType();

                    if (lType == Node.TEXT_NODE
                            || lType == Node.CDATA_SECTION_NODE) {
                        textLastChild = true;
                    } else if (lType == Node.ENTITY_REFERENCE_NODE) {
                        if (!canModifyPrev(lastChild)) {
                            return false;
                        } else {
                            //If the EntityReference child contains
                            //only text, or non-text or ends with a
                            //non-text node.
                            textLastChild = true;
                        }
                    } else {
                        //If the last child was replaceable and others are not
                        //Text or CDataSection or replaceable EntityRef nodes
                        //return false.
                        if (textLastChild) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                    lastChild = lastChild.getPreviousSibling();
                }
            } else if (type == Node.TEXT_NODE
                    || type == Node.CDATA_SECTION_NODE) {
                //If the previous sibling was text or cdatasection move to next
            } else {
                //If the previous sibling was anything but text or
                //cdatasection or an entity reference, stop search and
                //return true
                return true;
            }

            prev = prev.getPreviousSibling();
        }

        return true;
    }

    /**
     * If any EntityReference to be removed has descendants that are not
     * EntityReference, Text, or CDATASection nodes, the replaceWholeText method
     * must fail before performing any modification of the document, raising a
     * DOMException with the code NO_MODIFICATION_ALLOWED_ERR. Traverse previous
     * siblings of the node to be replaced. If a previous sibling is an
     * EntityReference node, get it's last child. If the first child was a Text
     * or CDATASection node and its next siblings are neither a replaceable
     * EntityReference or Text or CDATASection nodes, return false. IF the first
     * child was neither Text nor CDATASection nor a replaceable EntityReference
     * Node, then return true. If the first child was a Text or CDATASection
     * node any its next sibling was not or was an EntityReference that did not
     * contain only Text or CDATASection nodes, return false. Check this
     * recursively for EntityReference nodes.
     *
     * <p>
     * 如果要删除的任何EntityReference具有不是EntityReference,Text或CDATASection节点的后代,则replaceWholeText方法必须在对文档执行任何修改之前失败
     * ,从而产生具有代码NO_MODIFICATION_ALLOWED_ERR的DOMException。
     * 遍历要替换的节点的先前兄弟。如果先前的兄弟节点是EntityReference节点,则获取它的最后一个子节点。
     * 如果第一个子节点是Text或CDATASection节点,并且其下一个兄弟节点既不是可替换的EntityReference节点,也不是Text或CDATASection节点,则返回false。
     * 如果第一个孩子既不是Text也不是CDATASection也不是可替换的EntityReference节点,那么返回true。
     * 如果第一个子节点是Text或CDATASection节点,则其下一个兄弟节点不是或者是不仅包含Text或CDATASection节点的EntityReference,返回false。
     * 以递归方式检查EntityReference节点。
     * 
     * 
     * @param node
     * @return true - can replace text false - can't replace exception must be
     *         raised
     */
    private boolean canModifyNext(Node node) {
        boolean textFirstChild = false;

        Node next = node.getNextSibling();
        while (next != null) {

            short type = next.getNodeType();

            if (type == Node.ENTITY_REFERENCE_NODE) {
                //If the previous sibling was entityreference
                //check if its content is replaceable
                Node firstChild = next.getFirstChild();

                //if the entity reference has no children
                //return false
                if (firstChild == null) {
                    return false;
                }

                //The replacement text of the entity reference should
                //be either only text,cadatsections or replaceable entity
                //reference nodes or the last child should be neither of these
                while (firstChild != null) {
                    short lType = firstChild.getNodeType();

                    if (lType == Node.TEXT_NODE
                            || lType == Node.CDATA_SECTION_NODE) {
                        textFirstChild = true;
                    } else if (lType == Node.ENTITY_REFERENCE_NODE) {
                        if (!canModifyNext(firstChild)) {
                            return false;
                        } else {
                            //If the EntityReference child contains
                            //only text, or non-text or ends with a
                            //non-text node.
                            textFirstChild = true;
                        }
                    } else {
                        //If the first child was replaceable text and next
                        //children are not, then return false
                        if (textFirstChild) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                    firstChild = firstChild.getNextSibling();
                }
            } else if (type == Node.TEXT_NODE
                    || type == Node.CDATA_SECTION_NODE) {
                //If the previous sibling was text or cdatasection move to next
            } else {
                //If the next sibling was anything but text or
                //cdatasection or an entity reference, stop search and
                //return true
                return true;
            }

            next = next.getNextSibling();
        }

        return true;
    }

    /**
     * Check if an EntityReference node has Text Only child nodes
     *
     * <p>
     *  检查EntityReference节点是否具有仅文本节点
     * 
     * 
     * @param node
     * @return true - Contains text only children
     */
    private boolean hasTextOnlyChildren(Node node) {

        Node child = node;

        if (child == null) {
            return false;
        }

        child = child.getFirstChild();
        while (child != null) {
            int type = child.getNodeType();

            if (type == Node.ENTITY_REFERENCE_NODE) {
                return hasTextOnlyChildren(child);
            }
            else if (type != Node.TEXT_NODE
                    && type != Node.CDATA_SECTION_NODE
                    && type != Node.ENTITY_REFERENCE_NODE) {
                return false;
            }
            child = child.getNextSibling();
        }
        return true;
    }


    /**
     * NON-DOM: Returns whether this Text is ignorable whitespace.
     * <p>
     *  NON-DOM：返回此Text是否为可忽略的空格。
     * 
     */
    public boolean isIgnorableWhitespace() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return internalIsIgnorableWhitespace();

    } // isIgnorableWhitespace():boolean


    //
    // Text methods
    //

    /**
     * Break a text node into two sibling nodes. (Note that if the current node
     * has no parent, they won't wind up as "siblings" -- they'll both be
     * orphans.)
     *
     * <p>
     *  将文本节点分成两个兄弟节点。 (注意,如果当前节点没有父节点,它们将不会成为"兄弟节点" - 它们都将是孤立的。)
     * 
     * 
     * @param offset
     *            The offset at which to split. If offset is at the end of the
     *            available data, the second node will be empty.
     *
     * @return A reference to the new node (containing data after the offset
     *         point). The original node will contain data up to that point.
     *
     * @throws DOMException(INDEX_SIZE_ERR)
     *             if offset is <0 or >length.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR)
     *             if node is read-only.
     */
    public Text splitText(int offset)
        throws DOMException {

        if (isReadOnly()) {
            throw new DOMException(
            DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }

        if (needsSyncData()) {
            synchronizeData();
        }
        if (offset < 0 || offset > data.length() ) {
            throw new DOMException(DOMException.INDEX_SIZE_ERR,
                DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
        }

        // split text into two separate nodes
        Text newText =
            getOwnerDocument().createTextNode(data.substring(offset));
        setNodeValue(data.substring(0, offset));

        // insert new text node
        Node parentNode = getParentNode();
        if (parentNode != null) {
            parentNode.insertBefore(newText, nextSibling);
        }

        return newText;

    } // splitText(int):Text


    /**
     * NON-DOM (used by DOMParser): Reset data for the node.
     * <p>
     *  NON-DOM(由DOMParser使用)：重置节点的数据。
     * 
     */
    public void replaceData (String value){
        data = value;
    }


    /**
     * NON-DOM (used by DOMParser: Sets data to empty string.
     *  Returns the value the data was set to.
     * <p>
     *  NON-DOM(由DOMParser使用：将数据设置为空字符串。返回数据设置为的值。
     */
    public String removeData (){
        String olddata=data;
        data = "";
        return olddata;
    }

} // class TextImpl
