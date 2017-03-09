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
 * A tree node for a binary expression.
 * Use {@link #getKind getKind} to determine the kind of operator.
 *
 * For example:
 * <pre>
 *   <em>leftOperand</em> <em>operator</em> <em>rightOperand</em>
 * </pre>
 *
 * @jls sections 15.17 to 15.24
 *
 * <p>
 *  二元表达式的树节点。使用{@link #getKind getKind}来确定运算符的类型。
 * 
 *  例如：
 * <pre>
 *  <em> leftOperand </em> <em>运算符</em> <em> rightOperand </em>
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface BinaryTree extends ExpressionTree {
    ExpressionTree getLeftOperand();
    ExpressionTree getRightOperand();
}
