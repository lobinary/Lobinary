/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.metadata;

import javax.imageio.IIOException;
import org.w3c.dom.Node;

/**
 * An <code>IIOInvalidTreeException</code> is thrown when an attempt
 * by an <code>IIOMetadata</code> object to parse a tree of
 * <code>IIOMetadataNode</code>s fails.  The node that led to the
 * parsing error may be stored.  As with any parsing error, the actual
 * error may occur at a different point that that where it is
 * detected.  The node returned by <code>getOffendingNode</code>
 * should merely be considered as a clue to the actual nature of the
 * problem.
 *
 * <p>
 *  当<code> IIOMetadata </code>对象解析<code> IIOMetadataNode </code>的树失败时,会抛出<code> IIOInvalidTreeException
 *  </code>。
 * 可以存储导致解析错误的节点。与任何解析错误一样,实际错误可能发生在与检测到它的地方不同的点。
 * 由<code> getOffendingNode </code>返回的节点应该仅仅被认为是对问题的实际性质的线索。
 * 
 * 
 * @see IIOMetadata#setFromTree
 * @see IIOMetadata#mergeTree
 * @see IIOMetadataNode
 *
 */
public class IIOInvalidTreeException extends IIOException {

    /**
     * The <code>Node</code> that led to the parsing error, or
     * <code>null</code>.
     * <p>
     *  导致解析错误的<code> Node </code>或<code> null </code>。
     * 
     */
    protected Node offendingNode = null;

    /**
     * Constructs an <code>IIOInvalidTreeException</code> with a
     * message string and a reference to the <code>Node</code> that
     * caused the parsing error.
     *
     * <p>
     *  构造具有消息字符串和对引起解析错误的<code> Node </code>的引用的<code> IIOInvalidTreeException </code>。
     * 
     * 
     * @param message a <code>String</code> containing the reason for
     * the parsing failure.
     * @param offendingNode the DOM <code>Node</code> that caused the
     * exception, or <code>null</code>.
     */
    public IIOInvalidTreeException(String message, Node offendingNode) {
        super(message);
        this.offendingNode = offendingNode;
    }

    /**
     * Constructs an <code>IIOInvalidTreeException</code> with a
     * message string, a reference to an exception that caused this
     * exception, and a reference to the <code>Node</code> that caused
     * the parsing error.
     *
     * <p>
     *  使用消息字符串,对引起此异常的异常的引用以及引起解析错误的<code> Node </code>的引用构造<code> IIOInvalidTreeException </code>。
     * 
     * 
     * @param message a <code>String</code> containing the reason for
     * the parsing failure.
     * @param cause the <code>Throwable</code> (<code>Error</code> or
     * <code>Exception</code>) that caused this exception to occur,
     * or <code>null</code>.
     * @param offendingNode the DOM <code>Node</code> that caused the
     * exception, or <code>null</code>.
     */
    public IIOInvalidTreeException(String message, Throwable cause,
                                   Node offendingNode) {
        super(message, cause);
        this.offendingNode = offendingNode;
    }

    /**
     * Returns the <code>Node</code> that caused the error in parsing.
     *
     * <p>
     *  返回导致解析错误的<code> Node </code>。
     * 
     * @return the offending <code>Node</code>.
     */
    public Node getOffendingNode() {
        return offendingNode;
    }
}
