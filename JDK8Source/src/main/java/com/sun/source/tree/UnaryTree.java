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
 * A tree node for postfix and unary expressions.
 * Use {@link #getKind getKind} to determine the kind of operator.
 *
 * For example:
 * <pre>
 *   <em>operator</em> <em>expression</em>
 *
 *   <em>expression</em> <em>operator</em>
 * </pre>
 *
 * @jls sections 15.14 and 15.15
 *
 * <p>
 *  用于后缀和一元表达式的树节点。使用{@link #getKind getKind}来确定运算符的类型。
 * 
 *  例如：
 * <pre>
 *  <em> <em> </em> <em> <em> </em>
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface UnaryTree extends ExpressionTree {
    ExpressionTree getExpression();
}
