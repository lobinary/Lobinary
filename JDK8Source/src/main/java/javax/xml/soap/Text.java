/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

/**
 * A representation of a node whose value is text.  A <code>Text</code> object
 * may represent text that is content or text that is a comment.
 *
 * <p>
 *  值为文本的节点的表示。 <code> Text </code>对象可以表示作为内容的文本或作为注释的文本。
 * 
 */
public interface Text extends Node, org.w3c.dom.Text {

    /**
     * Retrieves whether this <code>Text</code> object represents a comment.
     *
     * <p>
     *  检索此<code> Text </code>对象是否表示注释。
     * 
     * @return <code>true</code> if this <code>Text</code> object is a
     *         comment; <code>false</code> otherwise
     */
    public boolean isComment();
}
