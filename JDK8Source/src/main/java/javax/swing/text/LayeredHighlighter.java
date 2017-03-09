/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.awt.Graphics;
import java.awt.Shape;

/**
 *
 * <p>
 * 
 * @author  Scott Violet
 * @author  Timothy Prinzing
 * @see     Highlighter
 */
public abstract class LayeredHighlighter implements Highlighter {
    /**
     * When leaf Views (such as LabelView) are rendering they should
     * call into this method. If a highlight is in the given region it will
     * be drawn immediately.
     *
     * <p>
     *  当叶视图(如LabelView)正在呈现时,他们应该调用这个方法。如果突出显示在给定区域,它将立即绘制。
     * 
     * 
     * @param g Graphics used to draw
     * @param p0 starting offset of view
     * @param p1 ending offset of view
     * @param viewBounds Bounds of View
     * @param editor JTextComponent
     * @param view View instance being rendered
     */
    public abstract void paintLayeredHighlights(Graphics g, int p0, int p1,
                                                Shape viewBounds,
                                                JTextComponent editor,
                                                View view);


    /**
     * Layered highlight renderer.
     * <p>
     *  分层高亮渲染器。
     */
    static public abstract class LayerPainter implements Highlighter.HighlightPainter {
        public abstract Shape paintLayer(Graphics g, int p0, int p1,
                                        Shape viewBounds,JTextComponent editor,
                                        View view);
    }
}
