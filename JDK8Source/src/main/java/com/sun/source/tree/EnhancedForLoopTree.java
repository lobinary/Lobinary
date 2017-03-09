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
 * A tree node for an "enhanced" 'for' loop statement.
 *
 * For example:
 * <pre>
 *   for ( <em>variable</em> : <em>expression</em> )
 *       <em>statement</em>
 * </pre>
 *
 * @jls section 14.14.2
 *
 * <p>
 *  用于"增强的"'for'循环语句的树节点。
 * 
 *  例如：
 * <pre>
 *  for(<em> variable </em>：<em> expression </em>)<em> </em>
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface EnhancedForLoopTree extends StatementTree {
    VariableTree getVariable();
    ExpressionTree getExpression();
    StatementTree getStatement();
}
