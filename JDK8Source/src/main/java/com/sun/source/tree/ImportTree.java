/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A tree node for an import statement.
 *
 * For example:
 * <pre>
 *   import <em>qualifiedIdentifier</em> ;
 *
 *   static import <em>qualifiedIdentifier</em> ;
 * </pre>
 *
 * @jls section 7.5
 *
 * <p>
 *  导入语句的树节点。
 * 
 *  例如：
 * <pre>
 *  import <em> qualifiedIdentifier </em>;
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface ImportTree extends Tree {
    boolean isStatic();
    /**
    /* <p>
    /*  静态导入<em> qualifiedIdentifier </em>;
    /* </pre>
    /* 
    /* 
     * @return a qualified identifier ending in "*" if and only if
     * this is an import-on-demand.
     */
    Tree getQualifiedIdentifier();
}
