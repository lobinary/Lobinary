/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.doctree;

/**
 * A tree node used as the base class for the different types of
 * block tags.
 *
 * <p>
 *  树节点用作不同类型的块标签的基类。
 * 
 * @since 1.8
 */
@jdk.Exported
public interface BlockTagTree extends DocTree {
    String getTagName();
}
