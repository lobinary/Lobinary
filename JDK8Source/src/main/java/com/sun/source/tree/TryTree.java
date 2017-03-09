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

import java.util.List;

/**
 * A tree node for a 'try' statement.
 *
 * For example:
 * <pre>
 *   try
 *       <em>block</em>
 *   <em>catches</em>
 *   finally
 *       <em>finallyBlock</em>
 * </pre>
 *
 * @jls section 14.20
 *
 * <p>
 *  一个'try'语句的树节点。
 * 
 *  例如：
 * <pre>
 *  尝试<em>阻止</em> <em>捕获</em>最后<em> finallyBlock </em>
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface TryTree extends StatementTree {
    BlockTree getBlock();
    List<? extends CatchTree> getCatches();
    BlockTree getFinallyBlock();
    List<? extends Tree> getResources();
}
