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

import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

import java.io.IOException;

import org.w3c.dom.Node;

/**
     * A DRI (Define Restart Interval) marker segment.
     * <p>
     *  DRI(定义重新启动间隔)标记段。
     * 
     */
class DRIMarkerSegment extends MarkerSegment {
    /**
     * Restart interval, or 0 if none is specified.
     * <p>
     *  重新启动间隔,如果未指定,则为0。
     * 
     */
    int restartInterval = 0;

    DRIMarkerSegment(JPEGBuffer buffer)
        throws IOException {
        super(buffer);
        restartInterval = (buffer.buf[buffer.bufPtr++] & 0xff) << 8;
        restartInterval |= buffer.buf[buffer.bufPtr++] & 0xff;
        buffer.bufAvail -= length;
    }

    DRIMarkerSegment(Node node) throws IIOInvalidTreeException {
        super(JPEG.DRI);
        updateFromNativeNode(node, true);
    }

    IIOMetadataNode getNativeNode() {
        IIOMetadataNode node = new IIOMetadataNode("dri");
        node.setAttribute("interval", Integer.toString(restartInterval));
        return node;
    }

    void updateFromNativeNode(Node node, boolean fromScratch)
        throws IIOInvalidTreeException {
        restartInterval = getAttributeValue(node, null, "interval",
                                            0, 65535, true);
    }

    /**
     * Writes the data for this segment to the stream in
     * valid JPEG format.
     * <p>
     *  以有效的JPEG格式将此段的数据写入流。
     */
    void write(ImageOutputStream ios) throws IOException {
        // We don't write DRI segments; the IJG library does.
    }

    void print() {
        printTag("DRI");
        System.out.println("Interval: "
                           + Integer.toString(restartInterval));
    }
}
