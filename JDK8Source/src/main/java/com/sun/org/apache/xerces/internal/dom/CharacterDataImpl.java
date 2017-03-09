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

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * CharacterData is an abstract Node that can carry character data as its
 * Value.  It provides shared behavior for Text, CData, and
 * possibly other node types. All offsets are 0-based.
 * <p>
 * Since ProcessingInstructionImpl inherits from this class to reuse the
 * setNodeValue method, this class isn't declared as implementing the interface
 * CharacterData. This is done by relevant subclasses (TexImpl, CommentImpl).
 * <p>
 * This class doesn't directly support mutation events, however, it notifies
 * the document when mutations are performed so that the document class do so.
 *
 * @xerces.internal
 *
 * <p>
 *  CharacterData是一个可以携带字符数据作为其值的抽象节点。它为Text,CData和可能的其他节点类型提供了共享行为。所有偏移都是基于0的。
 * <p>
 *  因为ProcessingInstructionImpl从这个类继承了重用setNodeValue方法,所以这个类不被声明为实现接口CharacterData。
 * 这由相关子类(TexImpl,CommentImpl)完成。
 * <p>
 *  这个类不直接支持突变事件,但是,当执行突变时它通知文档,以便文档类这样做。
 * 
 *  @ xerces.internal
 * 
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public abstract class CharacterDataImpl
    extends ChildNode {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 7931170150428474230L;

    //
    // Data
    //

    protected String data;

    /** Empty child nodes. */
    private static transient NodeList singletonNodeList = new NodeList() {
        public Node item(int index) { return null; }
        public int getLength() { return 0; }
    };

    //
    // Constructors
    //

    public CharacterDataImpl(){}

    /** Factory constructor. */
    protected CharacterDataImpl(CoreDocumentImpl ownerDocument, String data) {
        super(ownerDocument);
        this.data = data;
    }

    //
    // Node methods
    //

    /** Returns an empty node list. */
    public NodeList getChildNodes() {
        return singletonNodeList;
    }

    /*
     * returns the content of this node
     * <p>
     *  返回此节点的内容
     * 
     */
    public String getNodeValue() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return data;
    }

   /** Convenience wrapper for calling setNodeValueInternal when
     * we are not performing a replacement operation
     * <p>
     *  我们不执行替换操作
     * 
     */
    protected void setNodeValueInternal (String value) {
        setNodeValueInternal(value, false);
    }

    /** This function added so that we can distinguish whether
     *  setNodeValue has been called from some other DOM functions.
     *  or by the client.<p>
     *  This is important, because we do one type of Range fix-up,
     *  from the high-level functions in CharacterData, and another
     *  type if the client simply calls setNodeValue(value).
     * <p>
     * setNodeValue已经从一些其他DOM函数调用。或者由客户端。
     * <p>这很重要,因为我们从CharacterData中的高级函数执行一种类型的范围修正,如果客户端只调用setNodeValue(value),则使用另一种类型。
     * 
     */
    protected void setNodeValueInternal(String value, boolean replace) {

        CoreDocumentImpl ownerDocument = ownerDocument();

        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }

        // revisit: may want to set the value in ownerDocument.
        // Default behavior, overridden in some subclasses
        if (needsSyncData()) {
            synchronizeData();
        }

        // keep old value for document notification
        String oldvalue = this.data;

        // notify document
        ownerDocument.modifyingCharacterData(this, replace);

        this.data = value;

        // notify document
        ownerDocument.modifiedCharacterData(this, oldvalue, value, replace);
    }

    /**
     * Sets the content, possibly firing related events,
     * and updating ranges (via notification to the document)
     * <p>
     *  设置内容,可能触发相关事件和更新范围(通过通知到文档)
     * 
     */
    public void setNodeValue(String value) {

        setNodeValueInternal(value);

        // notify document
        ownerDocument().replacedText(this);
    }

    //
    // CharacterData methods
    //

    /**
     * Retrieve character data currently stored in this node.
     *
     * <p>
     *  检索当前存储在此节点中的字符数据。
     * 
     * 
     * @throws DOMExcpetion(DOMSTRING_SIZE_ERR) In some implementations,
     * the stored data may exceed the permitted length of strings. If so,
     * getData() will throw this DOMException advising the user to
     * instead retrieve the data in chunks via the substring() operation.
     */
    public String getData() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return data;
    }

    /**
     * Report number of characters currently stored in this node's
     * data. It may be 0, meaning that the value is an empty string.
     * <p>
     *  报告当前存储在此节点数据中的字符数。它可能为0,表示该值为空字符串。
     * 
     */
    public int getLength() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return data.length();
    }

    /**
     * Concatenate additional characters onto the end of the data
     * stored in this node. Note that this, and insert(), are the paths
     * by which a DOM could wind up accumulating more data than the
     * language's strings can easily handle. (See above discussion.)
     *
     * <p>
     *  将附加字符连接到存储在此节点中的数据的末尾。注意,这和insert()是一个路径,通过它,一个DOM可以累积累积更多的数据比语言的字符串可以轻松处理。 (见上文讨论)
     * 
     * 
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if node is readonly.
     */
    public void appendData(String data) {

        if (isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }
        if (data == null) {
            return;
        }
        if (needsSyncData()) {
            synchronizeData();
        }

        setNodeValue(this.data + data);

    } // appendData(String)

    /**
     * Remove a range of characters from the node's value. Throws a
     * DOMException if the offset is beyond the end of the
     * string. However, a deletion _count_ that exceeds the available
     * data is accepted as a delete-to-end request.
     *
     * <p>
     *  从节点的值中删除一定范围的字符。如果偏移超出字符串的末尾,则抛出一个DOMException。但是,超过可用数据的删除_count_被接受为删除到终止请求。
     * 
     * 
     * @throws DOMException(INDEX_SIZE_ERR) if offset is negative or
     * greater than length, or if count is negative.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if node is
     * readonly.
     */
    public void deleteData(int offset, int count)
        throws DOMException {

        internalDeleteData(offset, count, false);
    } // deleteData(int,int)


    /** NON-DOM INTERNAL: Within DOM actions, we sometimes need to be able
     * to control which mutation events are spawned. This version of the
     * deleteData operation allows us to do so. It is not intended
     * for use by application programs.
     * <p>
     *  以控制产生哪些突变事件。这个版本的deleteData操作允许我们这样做。它不适用于应用程序。
     * 
     */
    void internalDeleteData (int offset, int count, boolean replace)
    throws DOMException {

        CoreDocumentImpl ownerDocument = ownerDocument();
        if (ownerDocument.errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
            }

            if (count < 0) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null);
                throw new DOMException(DOMException.INDEX_SIZE_ERR, msg);
            }
        }

        if (needsSyncData()) {
            synchronizeData();
        }
        int tailLength = Math.max(data.length() - count - offset, 0);
        try {
            String value = data.substring(0, offset) +
            (tailLength > 0 ? data.substring(offset + count, offset + count + tailLength) : "");

            setNodeValueInternal(value, replace);

            // notify document
            ownerDocument.deletedText(this, offset, count);
        }
        catch (StringIndexOutOfBoundsException e) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null);
            throw new DOMException(DOMException.INDEX_SIZE_ERR, msg);
        }

    } // internalDeleteData(int,int,boolean)

    /**
     * Insert additional characters into the data stored in this node,
     * at the offset specified.
     *
     * <p>
     *  在指定的偏移量处,将附加字符插入存储在此节点中的数据中。
     * 
     * 
     * @throws DOMException(INDEX_SIZE_ERR) if offset is negative or
     * greater than length.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if node is readonly.
     */
    public void insertData(int offset, String data)
        throws DOMException {

        internalInsertData(offset, data, false);

    } // insertData(int,int)



    /** NON-DOM INTERNAL: Within DOM actions, we sometimes need to be able
     * to control which mutation events are spawned. This version of the
     * insertData operation allows us to do so. It is not intended
     * for use by application programs.
     * <p>
     *  以控制产生哪些突变事件。这个版本的insertData操作允许我们这样做。它不适用于应用程序。
     * 
     */
    void internalInsertData (int offset, String data, boolean replace)
    throws DOMException {

        CoreDocumentImpl ownerDocument = ownerDocument();

        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }

        if (needsSyncData()) {
            synchronizeData();
        }
        try {
            String value =
                new StringBuffer(this.data).insert(offset, data).toString();


            setNodeValueInternal(value, replace);

            // notify document
            ownerDocument.insertedText(this, offset, data.length());
        }
        catch (StringIndexOutOfBoundsException e) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null);
            throw new DOMException(DOMException.INDEX_SIZE_ERR, msg);
        }

    } // internalInsertData(int,String,boolean)



    /**
     * Replace a series of characters at the specified (zero-based)
     * offset with a new string, NOT necessarily of the same
     * length. Convenience method, equivalent to a delete followed by an
     * insert. Throws a DOMException if the specified offset is beyond
     * the end of the existing data.
     *
     * <p>
     * 将指定(从零开始)偏移处的一系列字符替换为新字符串,不一定相同长度。方便的方法,相当于删除后插入。如果指定的偏移量超出现有数据的末尾,则抛出一个DOMException异常。
     * 
     * 
     * @param offset       The offset at which to begin replacing.
     *
     * @param count        The number of characters to remove,
     * interpreted as in the delete() method.
     *
     * @param data         The new string to be inserted at offset in place of
     * the removed data. Note that the entire string will
     * be inserted -- the count parameter does not affect
     * insertion, and the new data may be longer or shorter
     * than the substring it replaces.
     *
     * @throws DOMException(INDEX_SIZE_ERR) if offset is negative or
     * greater than length, or if count is negative.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if node is
     * readonly.
     */
    public void replaceData(int offset, int count, String data)
    throws DOMException {

        CoreDocumentImpl ownerDocument = ownerDocument();

        // The read-only check is done by deleteData()
        // ***** This could be more efficient w/r/t Mutation Events,
        // specifically by aggregating DOMAttrModified and
        // DOMSubtreeModified. But mutation events are
        // underspecified; I don't feel compelled
        // to deal with it right now.
        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }

        if (needsSyncData()) {
            synchronizeData();
        }

        //notify document
        ownerDocument.replacingData(this);

        // keep old value for document notification
        String oldvalue = this.data;

        internalDeleteData(offset, count, true);
        internalInsertData(offset, data, true);

        ownerDocument.replacedCharacterData(this, oldvalue, this.data);

    } // replaceData(int,int,String)

    /**
     * Store character data into this node.
     *
     * <p>
     *  将字符数据存储到此节点。
     * 
     * 
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if node is readonly.
     */
    public void setData(String value)
        throws DOMException {
        setNodeValue(value);
    }

    /**
     * Substring is more than a convenience function. In some
     * implementations of the DOM, where the stored data may exceed the
     * length that can be returned in a single string, the only way to
     * read it all is to extract it in chunks via this method.
     *
     * <p>
     *  子字符串不仅仅是一个方便的函数。在DOM的一些实现中,其中存储的数据可能超过可以在单个字符串中返回的长度,读取它的唯一方式是通过该方法以块提取它。
     * 
     * @param offset        Zero-based offset of first character to retrieve.
     * @param count Number of characters to retrieve.
     *
     * If the sum of offset and count exceeds the length, all characters
     * to end of data are returned.
     *
     * @throws DOMException(INDEX_SIZE_ERR) if offset is negative or
     * greater than length, or if count is negative.
     *
     * @throws DOMException(WSTRING_SIZE_ERR) In some implementations,
     * count may exceed the permitted length of strings. If so,
     * substring() will throw this DOMException advising the user to
     * instead retrieve the data in smaller chunks.
     */
    public String substringData(int offset, int count)
        throws DOMException {

        if (needsSyncData()) {
            synchronizeData();
        }

        int length = data.length();
        if (count < 0 || offset < 0 || offset > length - 1) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null);
            throw new DOMException(DOMException.INDEX_SIZE_ERR, msg);
        }

        int tailIndex = Math.min(offset + count, length);

        return data.substring(offset, tailIndex);

    } // substringData(int,int):String

} // class CharacterDataImpl
