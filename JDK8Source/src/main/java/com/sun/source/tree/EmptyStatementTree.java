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
 * A tree node for an empty (skip) statement.
 *
 * For example:
 * <pre>
 *    ;
 * </pre>
 *
 * @jls section 14.6
 *
 * <p>
 *  用于空(跳过)语句的树节点。
 * 
 *  例如：
 * <pre>
 *  ;
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface EmptyStatementTree extends StatementTree {}
