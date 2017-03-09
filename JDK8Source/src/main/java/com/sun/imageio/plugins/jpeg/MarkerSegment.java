/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.IIOException;

import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

/**
 * All metadata is stored in MarkerSegments.  Marker segments
 * that we know about are stored in subclasses of this
 * basic class, which used for unrecognized APPn marker
 * segments.  XXX break out UnknownMarkerSegment as a subclass
 * and make this abstract, avoiding unused data field.
 * <p>
 *  所有元数据存储在MarkerSegments中。我们知道的标记段存储在这个基本类的子类中,用于无法识别的APPn标记段。
 *  XXX突破UnknownMarkerSegment作为子类,并使此抽象,避免未使用的数据字段。
 * 
 */
class MarkerSegment implements Cloneable {
    protected static final int LENGTH_SIZE = 2; // length is 2 bytes
    int tag;      // See JPEG.java
    int length;    /* Sometimes needed by subclasses; doesn't include
    int length;    /* <p>
    int length;    /* 
                      itself.  Meaningful only if constructed from a stream */
    byte [] data = null;  // Raw segment data, used for unrecognized segments
    boolean unknown = false; // Set to true if the tag is not recognized

    /**
     * Constructor for creating <code>MarkerSegment</code>s by reading
     * from an <code>ImageInputStream</code>.
     * <p>
     *  通过读取<code> ImageInputStream </code>创建<code> MarkerSegment </code>的构造函数。
     * 
     */
    MarkerSegment(JPEGBuffer buffer) throws IOException {

        buffer.loadBuf(3);  // tag plus length
        tag = buffer.buf[buffer.bufPtr++] & 0xff;
        length = (buffer.buf[buffer.bufPtr++] & 0xff) << 8;
        length |= buffer.buf[buffer.bufPtr++] & 0xff;
        length -= 2;  // JPEG length includes itself, we don't

        if (length < 0) {
            throw new IIOException("Invalid segment length: " + length);
        }
        buffer.bufAvail -= 3;
        // Now that we know the true length, ensure that we've got it,
        // or at least a bufferful if length is too big.
        buffer.loadBuf(length);
    }

    /**
     * Constructor used when creating segments other than by
     * reading them from a stream.
     * <p>
     *  在创建段而不是从流中读取时使用的构造函数。
     * 
     */
    MarkerSegment(int tag) {
        this.tag = tag;
        length = 0;
    }

    /**
     * Construct a MarkerSegment from an "unknown" DOM Node.
     * <p>
     *  从"未知"DOM节点构造MarkerSegment。
     * 
     */
    MarkerSegment(Node node) throws IIOInvalidTreeException {
        // The type of node should have been verified already.
        // get the attribute and assign it to the tag
        tag = getAttributeValue(node,
                                null,
                                "MarkerTag",
                                0, 255,
                                true);
        length = 0;
        // get the user object and clone it to the data
        if (node instanceof IIOMetadataNode) {
            IIOMetadataNode iioNode = (IIOMetadataNode) node;
            try {
                data = (byte []) iioNode.getUserObject();
            } catch (Exception e) {
                IIOInvalidTreeException newGuy =
                    new IIOInvalidTreeException
                    ("Can't get User Object", node);
                newGuy.initCause(e);
                throw newGuy;
            }
        } else {
            throw new IIOInvalidTreeException
                ("Node must have User Object", node);
        }
    }

    /**
     * Deep copy of data array.
     * <p>
     *  数据数组的深度副本。
     * 
     */
    protected Object clone() {
        MarkerSegment newGuy = null;
        try {
            newGuy = (MarkerSegment) super.clone();
        } catch (CloneNotSupportedException e) {} // won't happen
        if (this.data != null) {
            newGuy.data = (byte[]) data.clone();
        }
        return newGuy;
    }

    /**
     * We have determined that we don't know the type, so load
     * the data using the length parameter.
     * <p>
     *  我们已经确定我们不知道类型,因此使用length参数加载数据。
     * 
     */
    void loadData(JPEGBuffer buffer) throws IOException {
        data = new byte[length];
        buffer.readData(data);
    }

    IIOMetadataNode getNativeNode() {
        IIOMetadataNode node = new IIOMetadataNode("unknown");
        node.setAttribute("MarkerTag", Integer.toString(tag));
        node.setUserObject(data);

        return node;
    }

    static int getAttributeValue(Node node,
                                 NamedNodeMap attrs,
                                 String name,
                                 int min,
                                 int max,
                                 boolean required)
        throws IIOInvalidTreeException {
        if (attrs == null) {
            attrs = node.getAttributes();
        }
        String valueString = attrs.getNamedItem(name).getNodeValue();
        int value = -1;
        if (valueString == null) {
            if (required) {
                throw new IIOInvalidTreeException
                    (name + " attribute not found", node);
            }
        } else {
              value = Integer.parseInt(valueString);
              if ((value < min) || (value > max)) {
                  throw new IIOInvalidTreeException
                      (name + " attribute out of range", node);
              }
        }
        return value;
    }

    /**
     * Writes the marker, tag, and length.  Note that length
     * should be verified by the caller as a correct JPEG
     * length, i.e it includes itself.
     * <p>
     *  写入标记,标记和长度。注意,长度应该由调用者验证为正确的JPEG长度,即它包括自身。
     * 
     */
    void writeTag(ImageOutputStream ios) throws IOException {
        ios.write(0xff);
        ios.write(tag);
        write2bytes(ios, length);
    }

    /**
     * Writes the data for this segment to the stream in
     * valid JPEG format.
     * <p>
     *  以有效的JPEG格式将此段的数据写入流。
     */
    void write(ImageOutputStream ios) throws IOException {
        length = 2 + ((data != null) ? data.length : 0);
        writeTag(ios);
        if (data != null) {
            ios.write(data);
        }
    }

    static void write2bytes(ImageOutputStream ios,
                            int value) throws IOException {
        ios.write((value >> 8) & 0xff);
        ios.write(value & 0xff);

    }

    void printTag(String prefix) {
        System.out.println(prefix + " marker segment - marker = 0x"
                           + Integer.toHexString(tag));
        System.out.println("length: " + length);
    }

    void print() {
        printTag("Unknown");
        if (length > 10) {
            System.out.print("First 5 bytes:");
            for (int i=0;i<5;i++) {
                System.out.print(" Ox"
                                 + Integer.toHexString((int)data[i]));
            }
            System.out.print("\nLast 5 bytes:");
            for (int i=data.length-5;i<data.length;i++) {
                System.out.print(" Ox"
                                 + Integer.toHexString((int)data[i]));
            }
        } else {
            System.out.print("Data:");
            for (int i=0;i<data.length;i++) {
                System.out.print(" Ox"
                                 + Integer.toHexString((int)data[i]));
            }
        }
        System.out.println();
    }
}
