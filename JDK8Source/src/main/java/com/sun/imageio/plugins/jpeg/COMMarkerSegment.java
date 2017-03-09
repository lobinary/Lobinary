/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.imageio.plugins.jpeg;

import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.metadata.IIOInvalidTreeException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.w3c.dom.Node;

/**
 * A Comment marker segment.  Retains an array of bytes representing the
 * comment data as it is read from the stream.  If the marker segment is
 * constructed from a String, then local default encoding is assumed
 * when creating the byte array.  If the marker segment is created from
 * an <code>IIOMetadataNode</code>, the user object, if present is
 * assumed to be a byte array containing the comment data.  If there is
 * no user object then the comment attribute is used to create the
 * byte array, again assuming the default local encoding.
 * <p>
 *  注释标记段。保留从流中读取的表示注释数据的字节数组。如果标记段是从字符串构造的,则在创建字节数组时采用局部默认编码。
 * 如果标记段是从<code> IIOMetadataNode </code>创建的,则假定用户对象(如果存在)是包含注释数据的字节数组。
 * 如果没有用户对象,则使用comment属性创建字节数组,再次假定为默认的本地编码。
 * 
 */
class COMMarkerSegment extends MarkerSegment {
    private static final String ENCODING = "ISO-8859-1";

    /**
     * Constructs a marker segment from the given buffer, which contains
     * data from an <code>ImageInputStream</code>.  This is used when
     * reading metadata from a stream.
     * <p>
     *  构造来自给定缓冲区的标记段,其包含来自<code> ImageInputStream </code>的数据。这用于从流中读取元数据。
     * 
     */
    COMMarkerSegment(JPEGBuffer buffer) throws IOException {
        super(buffer);
        loadData(buffer);
    }

    /**
     * Constructs a marker segment from a String.  This is used when
     * modifying metadata from a non-native tree and when transcoding.
     * The default encoding is used to construct the byte array.
     * <p>
     *  从字符串构造标记段。当从非本地树修改元数据时和转码时使用。默认编码用于构造字节数组。
     * 
     */
    COMMarkerSegment(String comment) {
        super(JPEG.COM);
        data = comment.getBytes(); // Default encoding
    }

    /**
     * Constructs a marker segment from a native tree node.  If the node
     * is an <code>IIOMetadataNode</code> and contains a user object,
     * that object is used rather than the string attribute.  If the
     * string attribute is used, the default encoding is used.
     * <p>
     *  从本机树节点构造标记段。如果节点是<code> IIOMetadataNode </code>并且包含用户对象,则使用该对象而不是字符串属性。如果使用string属性,则使用缺省编码。
     * 
     */
    COMMarkerSegment(Node node) throws IIOInvalidTreeException{
        super(JPEG.COM);
        if (node instanceof IIOMetadataNode) {
            IIOMetadataNode ourNode = (IIOMetadataNode) node;
            data = (byte []) ourNode.getUserObject();
        }
        if (data == null) {
            String comment =
                node.getAttributes().getNamedItem("comment").getNodeValue();
            if (comment != null) {
                data = comment.getBytes(); // Default encoding
            } else {
                throw new IIOInvalidTreeException("Empty comment node!", node);
            }
        }
    }

    /**
     * Returns the array encoded as a String, using ISO-Latin-1 encoding.
     * If an application needs another encoding, the data array must be
     * consulted directly.
     * <p>
     *  返回使用ISO-Latin-1编码作为String编码的数组。如果应用程序需要另一个编码,则必须直接查阅数据数组。
     * 
     */
    String getComment() {
        try {
            return new String (data, ENCODING);
        } catch (UnsupportedEncodingException e) {}  // Won't happen
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> containing the data array
     * as a user object and a string encoded using ISO-8895-1, as an
     * attribute.
     * <p>
     * 返回包含数据数组作为用户对象的<code> IIOMetadataNode </code>和使用ISO-8895-1编码的字符串作为属性。
     * 
     */
    IIOMetadataNode getNativeNode() {
        IIOMetadataNode node = new IIOMetadataNode("com");
        node.setAttribute("comment", getComment());
        if (data != null) {
            node.setUserObject(data.clone());
        }
        return node;
    }

    /**
     * Writes the data for this segment to the stream in
     * valid JPEG format, directly from the data array.
     * <p>
     *  以有效的JPEG格式将此段的数据写入流,直接从数据数组。
     */
    void write(ImageOutputStream ios) throws IOException {
        length = 2 + data.length;
        writeTag(ios);
        ios.write(data);
    }

    void print() {
        printTag("COM");
        System.out.println("<" + getComment() + ">");
    }
}
