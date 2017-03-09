/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.tree;

import java.awt.Component;
import javax.swing.JTree;

/**
 * Defines the requirements for an object that displays a tree node.
 * See <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">How to Use Trees</a>
 * in <em>The Java Tutorial</em>
 * for an example of implementing a tree cell renderer
 * that displays custom icons.
 *
 * <p>
 *  定义显示树节点的对象的要求。
 * 请参阅<em> Java教程</em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html">如
 * 何使用树</a>。
 *  定义显示树节点的对象的要求。示例实现显示自定义图标的树单元格渲染器。
 * 
 * 
 * @author Rob Davis
 * @author Ray Ryan
 * @author Scott Violet
 */
public interface TreeCellRenderer {

    /**
     * Sets the value of the current tree cell to <code>value</code>.
     * If <code>selected</code> is true, the cell will be drawn as if
     * selected. If <code>expanded</code> is true the node is currently
     * expanded and if <code>leaf</code> is true the node represents a
     * leaf and if <code>hasFocus</code> is true the node currently has
     * focus. <code>tree</code> is the <code>JTree</code> the receiver is being
     * configured for.  Returns the <code>Component</code> that the renderer
     * uses to draw the value.
     * <p>
     * The <code>TreeCellRenderer</code> is also responsible for rendering the
     * the cell representing the tree's current DnD drop location if
     * it has one. If this renderer cares about rendering
     * the DnD drop location, it should query the tree directly to
     * see if the given row represents the drop location:
     * <pre>
     *     JTree.DropLocation dropLocation = tree.getDropLocation();
     *     if (dropLocation != null
     *             &amp;&amp; dropLocation.getChildIndex() == -1
     *             &amp;&amp; tree.getRowForPath(dropLocation.getPath()) == row) {
     *
     *         // this row represents the current drop location
     *         // so render it specially, perhaps with a different color
     *     }
     * </pre>
     *
     * <p>
     *  将当前树单元格的值设置为<code> value </code>。如果<code> selected </code>为true,则单元格将按照选定的方式绘制。
     * 如果<code> expanded </code>为真,则节点当前被展开,并且如果<code> leaf </code>为true,则节点表示叶,并且如果<code> hasFocus </code> 
     * 。
     *  将当前树单元格的值设置为<code> value </code>。如果<code> selected </code>为true,则单元格将按照选定的方式绘制。
     *  <code> tree </code>是配置接收器的<code> JTree </code>。返回渲染器用于绘制值的<code> Component </code>。
     * <p>
     *  <code> TreeCellRenderer </code>也负责渲染表示树的当前DnD丢弃位置的单元(如果它具有一个)。
     * 如果这个渲染器关心渲染DnD删除位置,它应该直接查询树以查看给定行是否表示删除位置：。
     * 
     * @return  the <code>Component</code> that the renderer uses to draw the value
     */
    Component getTreeCellRendererComponent(JTree tree, Object value,
                                   boolean selected, boolean expanded,
                                   boolean leaf, int row, boolean hasFocus);

}
