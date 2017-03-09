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

import java.util.List;

/**
 * A tree node for an unrecognized inline tag.
 *
 * <p>
 * &#064;name content
 *
 * <p>
 *  无法识别的内联标记的树节点。
 * 
 * <p>
 * 
 * @since 1.8
 *
 */
@jdk.Exported
public interface UnknownBlockTagTree extends BlockTagTree {
    List<? extends DocTree> getContent();
}
