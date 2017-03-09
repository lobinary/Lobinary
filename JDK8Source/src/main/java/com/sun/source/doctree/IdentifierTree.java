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

import javax.lang.model.element.Name;

/**
 * An identifier in a documentation comment.
 *
 * <p>
 * name
 *
 * <p>
 *  文档注释中的标识符。
 * 
 * <p>
 * 
 * @since 1.8
 */
@jdk.Exported
public interface IdentifierTree extends DocTree {
    Name getName();
}
