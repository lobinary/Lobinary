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
 * The top level representation of a documentation comment.
 *
 * <p>
 * first-sentence body block-tags
 *
 * <p>
 *  文档注释的顶级表示。
 * 
 * <p>
 * 
 * @since 1.8
 */
@jdk.Exported
public interface DocCommentTree extends DocTree {
    List<? extends DocTree> getFirstSentence();
    List<? extends DocTree> getBody();
    List<? extends DocTree> getBlockTags();
}
