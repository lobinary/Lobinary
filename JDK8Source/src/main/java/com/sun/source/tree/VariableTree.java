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

import javax.lang.model.element.Name;

/**
 * A tree node for a variable declaration.
 *
 * For example:
 * <pre>
 *   <em>modifiers</em> <em>type</em> <em>name</em> <em>initializer</em> ;
 *   <em>modifiers</em> <em>type</em> <em>qualified-name</em>.this
 * </pre>
 *
 * @jls sections 8.3 and 14.4
 *
 * <p>
 *  变量声明的树节点。
 * 
 *  例如：
 * <pre>
 *  </em> <em> <em> </em> <em> </em> <em>修饰符</em> <em> <em> </em> <em> </em>
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface VariableTree extends StatementTree {
    ModifiersTree getModifiers();
    Name getName();
    ExpressionTree getNameExpression();
    Tree getType();
    ExpressionTree getInitializer();
}
