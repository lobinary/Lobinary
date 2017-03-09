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
 * A tree node for a 'case' in a 'switch' statement.
 *
 * For example:
 * <pre>
 *   case <em>expression</em> :
 *       <em>statements</em>
 *
 *   default :
 *       <em>statements</em>
 * </pre>
 *
 * @jls section 14.11
 *
 * <p>
 *  "switch"语句中的"case"的树节点。
 * 
 *  例如：
 * <pre>
 *  case <em>表达式</em>：<em> </em>
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface CaseTree extends Tree {
    /**
    /* <p>
    /*  默认：<em>语句</em>
    /* </pre>
    /* 
    /* 
     * @return null if and only if this Case is {@code default:}
     */
    ExpressionTree getExpression();
    List<? extends StatementTree> getStatements();
}
