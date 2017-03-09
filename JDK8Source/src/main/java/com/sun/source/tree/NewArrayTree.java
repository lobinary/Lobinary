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
 * A tree node for an expression to create a new instance of an array.
 *
 * For example:
 * <pre>
 *   new <em>type</em> <em>dimensions</em> <em>initializers</em>
 *
 *   new <em>type</em> <em>dimensions</em> [ ] <em>initializers</em>
 * </pre>
 *
 * @jls section 15.10
 *
 * <p>
 *  表达式的树节点,用于创建数组的新实例。
 * 
 *  例如：
 * <pre>
 *  新<em>类型</em> <em>维</em> <em>初始化器</em>
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface NewArrayTree extends ExpressionTree {
    Tree getType();
    List<? extends ExpressionTree> getDimensions();
    List<? extends ExpressionTree> getInitializers();
    List<? extends AnnotationTree> getAnnotations();
    List<? extends List<? extends AnnotationTree>> getDimAnnotations();
}
