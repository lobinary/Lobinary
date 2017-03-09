/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.tree;

import java.util.List;

/**
 * A tree node for a union type expression in a multicatch var declaration.
 *
 * <p>
 *  多节点var声明中联合类型表达式的树节点。
 * 
 * @author Maurizio Cimadamore
 *
 * @since 1.7
 */
@jdk.Exported
public interface UnionTypeTree extends Tree {
    List<? extends Tree> getTypeAlternatives();
}
