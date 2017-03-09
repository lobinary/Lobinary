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
package javax.swing.text;

import java.util.Vector;
import java.awt.*;
import javax.swing.plaf.*;
import javax.swing.*;

/**
 * Implements the Highlighter interfaces.  Implements a simple highlight
 * painter that renders in a solid color.
 *
 * <p>
 *  实现Highlighter界面。实现一个简单的高亮画家呈现为纯色。
 * 
 * 
 * @author  Timothy Prinzing
 * @see     Highlighter
 */
public class DefaultHighlighter extends LayeredHighlighter {

    /**
     * Creates a new DefaultHighlighther object.
     * <p>
     *  创建一个新的DefaultHighlighther对象。
     * 
     */
    public DefaultHighlighter() {
        drawsLayeredHighlights = true;
    }

    // ---- Highlighter methods ----------------------------------------------

    /**
     * Renders the highlights.
     *
     * <p>
     *  呈现亮点。
     * 
     * 
     * @param g the graphics context
     */
    public void paint(Graphics g) {
        // PENDING(prinz) - should cull ranges not visible
        int len = highlights.size();
        for (int i = 0; i < len; i++) {
            HighlightInfo info = highlights.elementAt(i);
            if (!(info instanceof LayeredHighlightInfo)) {
                // Avoid allocing unless we need it.
                Rectangle a = component.getBounds();
                Insets insets = component.getInsets();
                a.x = insets.left;
                a.y = insets.top;
                a.width -= insets.left + insets.right;
                a.height -= insets.top + insets.bottom;
                for (; i < len; i++) {
                    info = highlights.elementAt(i);
                    if (!(info instanceof LayeredHighlightInfo)) {
                        Highlighter.HighlightPainter p = info.getPainter();
                        p.paint(g, info.getStartOffset(), info.getEndOffset(),
                                a, component);
                    }
                }
            }
        }
    }

    /**
     * Called when the UI is being installed into the
     * interface of a JTextComponent.  Installs the editor, and
     * removes any existing highlights.
     *
     * <p>
     *  当UI安装到JTextComponent的接口时调用。安装编辑器,并删除任何现有的亮点。
     * 
     * 
     * @param c the editor component
     * @see Highlighter#install
     */
    public void install(JTextComponent c) {
        component = c;
        removeAllHighlights();
    }

    /**
     * Called when the UI is being removed from the interface of
     * a JTextComponent.
     *
     * <p>
     *  当UI从JTextComponent的接口中删除时调用。
     * 
     * 
     * @param c the component
     * @see Highlighter#deinstall
     */
    public void deinstall(JTextComponent c) {
        component = null;
    }

    /**
     * Adds a highlight to the view.  Returns a tag that can be used
     * to refer to the highlight.
     *
     * <p>
     *  在视图中添加突出显示。返回可用于引用突出显示的标记。
     * 
     * 
     * @param p0   the start offset of the range to highlight &gt;= 0
     * @param p1   the end offset of the range to highlight &gt;= p0
     * @param p    the painter to use to actually render the highlight
     * @return     an object that can be used as a tag
     *   to refer to the highlight
     * @exception BadLocationException if the specified location is invalid
     */
    public Object addHighlight(int p0, int p1, Highlighter.HighlightPainter p) throws BadLocationException {
        if (p0 < 0) {
            throw new BadLocationException("Invalid start offset", p0);
        }

        if (p1 < p0) {
            throw new BadLocationException("Invalid end offset", p1);
        }

        Document doc = component.getDocument();
        HighlightInfo i = (getDrawsLayeredHighlights() &&
                           (p instanceof LayeredHighlighter.LayerPainter)) ?
                          new LayeredHighlightInfo() : new HighlightInfo();
        i.painter = p;
        i.p0 = doc.createPosition(p0);
        i.p1 = doc.createPosition(p1);
        highlights.addElement(i);
        safeDamageRange(p0, p1);
        return i;
    }

    /**
     * Removes a highlight from the view.
     *
     * <p>
     *  从视图中删除突出显示。
     * 
     * 
     * @param tag the reference to the highlight
     */
    public void removeHighlight(Object tag) {
        if (tag instanceof LayeredHighlightInfo) {
            LayeredHighlightInfo lhi = (LayeredHighlightInfo)tag;
            if (lhi.width > 0 && lhi.height > 0) {
                component.repaint(lhi.x, lhi.y, lhi.width, lhi.height);
            }
        }
        else {
            HighlightInfo info = (HighlightInfo) tag;
            safeDamageRange(info.p0, info.p1);
        }
        highlights.removeElement(tag);
    }

    /**
     * Removes all highlights.
     * <p>
     *  删除所有亮点。
     * 
     */
    public void removeAllHighlights() {
        TextUI mapper = component.getUI();
        if (getDrawsLayeredHighlights()) {
            int len = highlights.size();
            if (len != 0) {
                int minX = 0;
                int minY = 0;
                int maxX = 0;
                int maxY = 0;
                int p0 = -1;
                int p1 = -1;
                for (int i = 0; i < len; i++) {
                    HighlightInfo hi = highlights.elementAt(i);
                    if (hi instanceof LayeredHighlightInfo) {
                        LayeredHighlightInfo info = (LayeredHighlightInfo)hi;
                        minX = Math.min(minX, info.x);
                        minY = Math.min(minY, info.y);
                        maxX = Math.max(maxX, info.x + info.width);
                        maxY = Math.max(maxY, info.y + info.height);
                    }
                    else {
                        if (p0 == -1) {
                            p0 = hi.p0.getOffset();
                            p1 = hi.p1.getOffset();
                        }
                        else {
                            p0 = Math.min(p0, hi.p0.getOffset());
                            p1 = Math.max(p1, hi.p1.getOffset());
                        }
                    }
                }
                if (minX != maxX && minY != maxY) {
                    component.repaint(minX, minY, maxX - minX, maxY - minY);
                }
                if (p0 != -1) {
                    try {
                        safeDamageRange(p0, p1);
                    } catch (BadLocationException e) {}
                }
                highlights.removeAllElements();
            }
        }
        else if (mapper != null) {
            int len = highlights.size();
            if (len != 0) {
                int p0 = Integer.MAX_VALUE;
                int p1 = 0;
                for (int i = 0; i < len; i++) {
                    HighlightInfo info = highlights.elementAt(i);
                    p0 = Math.min(p0, info.p0.getOffset());
                    p1 = Math.max(p1, info.p1.getOffset());
                }
                try {
                    safeDamageRange(p0, p1);
                } catch (BadLocationException e) {}

                highlights.removeAllElements();
            }
        }
    }

    /**
     * Changes a highlight.
     *
     * <p>
     *  更改突出显示。
     * 
     * 
     * @param tag the highlight tag
     * @param p0 the beginning of the range &gt;= 0
     * @param p1 the end of the range &gt;= p0
     * @exception BadLocationException if the specified location is invalid
     */
    public void changeHighlight(Object tag, int p0, int p1) throws BadLocationException {
        if (p0 < 0) {
            throw new BadLocationException("Invalid beginning of the range", p0);
        }

        if (p1 < p0) {
            throw new BadLocationException("Invalid end of the range", p1);
        }

        Document doc = component.getDocument();
        if (tag instanceof LayeredHighlightInfo) {
            LayeredHighlightInfo lhi = (LayeredHighlightInfo)tag;
            if (lhi.width > 0 && lhi.height > 0) {
                component.repaint(lhi.x, lhi.y, lhi.width, lhi.height);
            }
            // Mark the highlights region as invalid, it will reset itself
            // next time asked to paint.
            lhi.width = lhi.height = 0;
            lhi.p0 = doc.createPosition(p0);
            lhi.p1 = doc.createPosition(p1);
            safeDamageRange(Math.min(p0, p1), Math.max(p0, p1));
        }
        else {
            HighlightInfo info = (HighlightInfo) tag;
            int oldP0 = info.p0.getOffset();
            int oldP1 = info.p1.getOffset();
            if (p0 == oldP0) {
                safeDamageRange(Math.min(oldP1, p1),
                                   Math.max(oldP1, p1));
            } else if (p1 == oldP1) {
                safeDamageRange(Math.min(p0, oldP0),
                                   Math.max(p0, oldP0));
            } else {
                safeDamageRange(oldP0, oldP1);
                safeDamageRange(p0, p1);
            }
            info.p0 = doc.createPosition(p0);
            info.p1 = doc.createPosition(p1);
        }
    }

    /**
     * Makes a copy of the highlights.  Does not actually clone each highlight,
     * but only makes references to them.
     *
     * <p>
     *  制作亮点的副本。实际上并不克隆每个突出显示,而只是引用它们。
     * 
     * 
     * @return the copy
     * @see Highlighter#getHighlights
     */
    public Highlighter.Highlight[] getHighlights() {
        int size = highlights.size();
        if (size == 0) {
            return noHighlights;
        }
        Highlighter.Highlight[] h = new Highlighter.Highlight[size];
        highlights.copyInto(h);
        return h;
    }

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
    public void paintLayeredHighlights(Graphics g, int p0, int p1,
                                       Shape viewBounds,
                                       JTextComponent editor, View view) {
        for (int counter = highlights.size() - 1; counter >= 0; counter--) {
            HighlightInfo tag = highlights.elementAt(counter);
            if (tag instanceof LayeredHighlightInfo) {
                LayeredHighlightInfo lhi = (LayeredHighlightInfo)tag;
                int start = lhi.getStartOffset();
                int end = lhi.getEndOffset();
                if ((p0 < start && p1 > start) ||
                    (p0 >= start && p0 < end)) {
                    lhi.paintLayeredHighlights(g, p0, p1, viewBounds,
                                               editor, view);
                }
            }
        }
    }

    /**
     * Queues damageRange() call into event dispatch thread
     * to be sure that views are in consistent state.
     * <p>
     *  将damageRange()调用到事件分派线程,以确保视图处于一致状态。
     * 
     */
    private void safeDamageRange(final Position p0, final Position p1) {
        safeDamager.damageRange(p0, p1);
    }

    /**
     * Queues damageRange() call into event dispatch thread
     * to be sure that views are in consistent state.
     * <p>
     *  将damageRange()调用到事件分派线程,以确保视图处于一致状态。
     * 
     */
    private void safeDamageRange(int a0, int a1) throws BadLocationException {
        Document doc = component.getDocument();
        safeDamageRange(doc.createPosition(a0), doc.createPosition(a1));
    }

    /**
     * If true, highlights are drawn as the Views draw the text. That is
     * the Views will call into <code>paintLayeredHighlight</code> which
     * will result in a rectangle being drawn before the text is drawn
     * (if the offsets are in a highlighted region that is). For this to
     * work the painter supplied must be an instance of
     * LayeredHighlightPainter.
     * <p>
     * 如果为true,则会在视图绘制文本时绘制突出显示。这就是视图将调用<code> paintLayeredHighlight </code>,这将导致在绘制文本之前绘制一个矩形(如果偏移位于高亮区域)。
     * 为了这个工作,提供的画家必须是LayeredHighlightPainter的实例。
     * 
     */
    public void setDrawsLayeredHighlights(boolean newValue) {
        drawsLayeredHighlights = newValue;
    }

    public boolean getDrawsLayeredHighlights() {
        return drawsLayeredHighlights;
    }

    // ---- member variables --------------------------------------------

    private final static Highlighter.Highlight[] noHighlights =
            new Highlighter.Highlight[0];
    private Vector<HighlightInfo> highlights = new Vector<HighlightInfo>();
    private JTextComponent component;
    private boolean drawsLayeredHighlights;
    private SafeDamager safeDamager = new SafeDamager();


    /**
     * Default implementation of LayeredHighlighter.LayerPainter that can
     * be used for painting highlights.
     * <p>
     * As of 1.4 this field is final.
     * <p>
     *  LayeredHighlighter.LayerPainter的默认实现,可用于绘制突出显示。
     * <p>
     *  从1.4这个字段是最后的。
     * 
     */
    public static final LayeredHighlighter.LayerPainter DefaultPainter = new DefaultHighlightPainter(null);


    /**
     * Simple highlight painter that fills a highlighted area with
     * a solid color.
     * <p>
     *  简单的高亮显示画面,用纯色填充高亮区域。
     * 
     */
    public static class DefaultHighlightPainter extends LayeredHighlighter.LayerPainter {

        /**
         * Constructs a new highlight painter. If <code>c</code> is null,
         * the JTextComponent will be queried for its selection color.
         *
         * <p>
         *  构建新的高亮画家。如果<code> c </code>为null,将查询JTextComponent的选择颜色。
         * 
         * 
         * @param c the color for the highlight
         */
        public DefaultHighlightPainter(Color c) {
            color = c;
        }

        /**
         * Returns the color of the highlight.
         *
         * <p>
         *  返回高亮的颜色。
         * 
         * 
         * @return the color
         */
        public Color getColor() {
            return color;
        }

        // --- HighlightPainter methods ---------------------------------------

        /**
         * Paints a highlight.
         *
         * <p>
         *  画一个亮点。
         * 
         * 
         * @param g the graphics context
         * @param offs0 the starting model offset &gt;= 0
         * @param offs1 the ending model offset &gt;= offs1
         * @param bounds the bounding box for the highlight
         * @param c the editor
         */
        public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
            Rectangle alloc = bounds.getBounds();
            try {
                // --- determine locations ---
                TextUI mapper = c.getUI();
                Rectangle p0 = mapper.modelToView(c, offs0);
                Rectangle p1 = mapper.modelToView(c, offs1);

                // --- render ---
                Color color = getColor();

                if (color == null) {
                    g.setColor(c.getSelectionColor());
                }
                else {
                    g.setColor(color);
                }
                if (p0.y == p1.y) {
                    // same line, render a rectangle
                    Rectangle r = p0.union(p1);
                    g.fillRect(r.x, r.y, r.width, r.height);
                } else {
                    // different lines
                    int p0ToMarginWidth = alloc.x + alloc.width - p0.x;
                    g.fillRect(p0.x, p0.y, p0ToMarginWidth, p0.height);
                    if ((p0.y + p0.height) != p1.y) {
                        g.fillRect(alloc.x, p0.y + p0.height, alloc.width,
                                   p1.y - (p0.y + p0.height));
                    }
                    g.fillRect(alloc.x, p1.y, (p1.x - alloc.x), p1.height);
                }
            } catch (BadLocationException e) {
                // can't render
            }
        }

        // --- LayerPainter methods ----------------------------
        /**
         * Paints a portion of a highlight.
         *
         * <p>
         *  描绘高亮的一部分。
         * 
         * 
         * @param g the graphics context
         * @param offs0 the starting model offset &gt;= 0
         * @param offs1 the ending model offset &gt;= offs1
         * @param bounds the bounding box of the view, which is not
         *        necessarily the region to paint.
         * @param c the editor
         * @param view View painting for
         * @return region drawing occurred in
         */
        public Shape paintLayer(Graphics g, int offs0, int offs1,
                                Shape bounds, JTextComponent c, View view) {
            Color color = getColor();

            if (color == null) {
                g.setColor(c.getSelectionColor());
            }
            else {
                g.setColor(color);
            }

            Rectangle r;

            if (offs0 == view.getStartOffset() &&
                offs1 == view.getEndOffset()) {
                // Contained in view, can just use bounds.
                if (bounds instanceof Rectangle) {
                    r = (Rectangle) bounds;
                }
                else {
                    r = bounds.getBounds();
                }
            }
            else {
                // Should only render part of View.
                try {
                    // --- determine locations ---
                    Shape shape = view.modelToView(offs0, Position.Bias.Forward,
                                                   offs1,Position.Bias.Backward,
                                                   bounds);
                    r = (shape instanceof Rectangle) ?
                                  (Rectangle)shape : shape.getBounds();
                } catch (BadLocationException e) {
                    // can't render
                    r = null;
                }
            }

            if (r != null) {
                // If we are asked to highlight, we should draw something even
                // if the model-to-view projection is of zero width (6340106).
                r.width = Math.max(r.width, 1);

                g.fillRect(r.x, r.y, r.width, r.height);
            }

            return r;
        }

        private Color color;

    }


    class HighlightInfo implements Highlighter.Highlight {

        public int getStartOffset() {
            return p0.getOffset();
        }

        public int getEndOffset() {
            return p1.getOffset();
        }

        public Highlighter.HighlightPainter getPainter() {
            return painter;
        }

        Position p0;
        Position p1;
        Highlighter.HighlightPainter painter;
    }


    /**
     * LayeredHighlightPainter is used when a drawsLayeredHighlights is
     * true. It maintains a rectangle of the region to paint.
     * <p>
     *  当drawLayeredHighlights为true时,使用LayeredHighlightPainter。它保持要绘制的区域的矩形。
     * 
     */
    class LayeredHighlightInfo extends HighlightInfo {

        void union(Shape bounds) {
            if (bounds == null)
                return;

            Rectangle alloc;
            if (bounds instanceof Rectangle) {
                alloc = (Rectangle)bounds;
            }
            else {
                alloc = bounds.getBounds();
            }
            if (width == 0 || height == 0) {
                x = alloc.x;
                y = alloc.y;
                width = alloc.width;
                height = alloc.height;
            }
            else {
                width = Math.max(x + width, alloc.x + alloc.width);
                height = Math.max(y + height, alloc.y + alloc.height);
                x = Math.min(x, alloc.x);
                width -= x;
                y = Math.min(y, alloc.y);
                height -= y;
            }
        }

        /**
         * Restricts the region based on the receivers offsets and messages
         * the painter to paint the region.
         * <p>
         *  根据接收器偏移限制区域,并消息绘制区域的画家。
         * 
         */
        void paintLayeredHighlights(Graphics g, int p0, int p1,
                                    Shape viewBounds, JTextComponent editor,
                                    View view) {
            int start = getStartOffset();
            int end = getEndOffset();
            // Restrict the region to what we represent
            p0 = Math.max(start, p0);
            p1 = Math.min(end, p1);
            // Paint the appropriate region using the painter and union
            // the effected region with our bounds.
            union(((LayeredHighlighter.LayerPainter)painter).paintLayer
                  (g, p0, p1, viewBounds, editor, view));
        }

        int x;
        int y;
        int width;
        int height;
    }

    /**
     * This class invokes <code>mapper.damageRange</code> in
     * EventDispatchThread. The only one instance per Highlighter
     * is cretaed. When a number of ranges should be damaged
     * it collects them into queue and damages
     * them in consecutive order in <code>run</code>
     * call.
     * <p>
     *  此类调用EventDispatchThread中的<code> mapper.damageRange </code>。每个荧光笔只有一个实例是cretaed。
     * 当多个范围应该被损坏时,它将它们收集到队列中,并在<code> run </code> call中以连续顺序损坏它们。
     * 
     */
    class SafeDamager implements Runnable {
        private Vector<Position> p0 = new Vector<Position>(10);
        private Vector<Position> p1 = new Vector<Position>(10);
        private Document lastDoc = null;

        /**
         * Executes range(s) damage and cleans range queue.
         * <p>
         *  执行范围损坏并清除范围队列。
         * 
         */
        public synchronized void run() {
            if (component != null) {
                TextUI mapper = component.getUI();
                if (mapper != null && lastDoc == component.getDocument()) {
                    // the Document should be the same to properly
                    // display highlights
                    int len = p0.size();
                    for (int i = 0; i < len; i++){
                        mapper.damageRange(component,
                                p0.get(i).getOffset(),
                                p1.get(i).getOffset());
                    }
                }
            }
            p0.clear();
            p1.clear();

            // release reference
            lastDoc = null;
        }

        /**
         * Adds the range to be damaged into the range queue. If the
         * range queue is empty (the first call or run() was already
         * invoked) then adds this class instance into EventDispatch
         * queue.
         *
         * The method also tracks if the current document changed or
         * component is null. In this case it removes all ranges added
         * before from range queue.
         * <p>
         *  将要损坏的范围添加到范围队列中。如果范围队列为空(第一次调用或run()已被调用),则将此类实例添加到EventDispatch队列中。
         * 
         */
        public synchronized void damageRange(Position pos0, Position pos1) {
            if (component == null) {
                p0.clear();
                lastDoc = null;
                return;
            }

            boolean addToQueue = p0.isEmpty();
            Document curDoc = component.getDocument();
            if (curDoc != lastDoc) {
                if (!p0.isEmpty()) {
                    p0.clear();
                    p1.clear();
                }
                lastDoc = curDoc;
            }
            p0.add(pos0);
            p1.add(pos1);

            if (addToQueue) {
                SwingUtilities.invokeLater(this);
            }
        }
    }
}
