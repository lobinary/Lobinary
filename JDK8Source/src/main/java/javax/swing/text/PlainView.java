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
import java.util.Properties;
import java.awt.*;
import javax.swing.event.*;

/**
 * Implements View interface for a simple multi-line text view
 * that has text in one font and color.  The view represents each
 * child element as a line of text.
 *
 * <p>
 *  实现一个简单的多行文本视图的视图界面,​​该视图具有一种字体和颜色的文本。视图将每个子元素表示为一行文本。
 * 
 * 
 * @author  Timothy Prinzing
 * @see     View
 */
public class PlainView extends View implements TabExpander {

    /**
     * Constructs a new PlainView wrapped on an element.
     *
     * <p>
     *  构造一个包含在元素上的新PlainView。
     * 
     * 
     * @param elem the element
     */
    public PlainView(Element elem) {
        super(elem);
    }

    /**
     * Returns the tab size set for the document, defaulting to 8.
     *
     * <p>
     *  返回为文档设置的选项卡大小,默认值为8。
     * 
     * 
     * @return the tab size
     */
    protected int getTabSize() {
        Integer i = (Integer) getDocument().getProperty(PlainDocument.tabSizeAttribute);
        int size = (i != null) ? i.intValue() : 8;
        return size;
    }

    /**
     * Renders a line of text, suppressing whitespace at the end
     * and expanding any tabs.  This is implemented to make calls
     * to the methods <code>drawUnselectedText</code> and
     * <code>drawSelectedText</code> so that the way selected and
     * unselected text are rendered can be customized.
     *
     * <p>
     *  呈现一行文本,在结尾处抑制空格,并展开任何标签。
     * 这被实现为调用方法<code> drawUnselectedText </code>和<code> drawSelectedText </code>,以便可以定制所选和未选择的文本的呈现方式。
     * 
     * 
     * @param lineIndex the line to draw &gt;= 0
     * @param g the <code>Graphics</code> context
     * @param x the starting X position &gt;= 0
     * @param y the starting Y position &gt;= 0
     * @see #drawUnselectedText
     * @see #drawSelectedText
     */
    protected void drawLine(int lineIndex, Graphics g, int x, int y) {
        Element line = getElement().getElement(lineIndex);
        Element elem;

        try {
            if (line.isLeaf()) {
                drawElement(lineIndex, line, g, x, y);
            } else {
                // this line contains the composed text.
                int count = line.getElementCount();
                for(int i = 0; i < count; i++) {
                    elem = line.getElement(i);
                    x = drawElement(lineIndex, elem, g, x, y);
                }
            }
        } catch (BadLocationException e) {
            throw new StateInvariantError("Can't render line: " + lineIndex);
        }
    }

    private int drawElement(int lineIndex, Element elem, Graphics g, int x, int y) throws BadLocationException {
        int p0 = elem.getStartOffset();
        int p1 = elem.getEndOffset();
        p1 = Math.min(getDocument().getLength(), p1);

        if (lineIndex == 0) {
            x += firstLineOffset;
        }
        AttributeSet attr = elem.getAttributes();
        if (Utilities.isComposedTextAttributeDefined(attr)) {
            g.setColor(unselected);
            x = Utilities.drawComposedText(this, attr, g, x, y,
                                        p0-elem.getStartOffset(),
                                        p1-elem.getStartOffset());
        } else {
            if (sel0 == sel1 || selected == unselected) {
                // no selection, or it is invisible
                x = drawUnselectedText(g, x, y, p0, p1);
            } else if ((p0 >= sel0 && p0 <= sel1) && (p1 >= sel0 && p1 <= sel1)) {
                x = drawSelectedText(g, x, y, p0, p1);
            } else if (sel0 >= p0 && sel0 <= p1) {
                if (sel1 >= p0 && sel1 <= p1) {
                    x = drawUnselectedText(g, x, y, p0, sel0);
                    x = drawSelectedText(g, x, y, sel0, sel1);
                    x = drawUnselectedText(g, x, y, sel1, p1);
                } else {
                    x = drawUnselectedText(g, x, y, p0, sel0);
                    x = drawSelectedText(g, x, y, sel0, p1);
                }
            } else if (sel1 >= p0 && sel1 <= p1) {
                x = drawSelectedText(g, x, y, p0, sel1);
                x = drawUnselectedText(g, x, y, sel1, p1);
            } else {
                x = drawUnselectedText(g, x, y, p0, p1);
            }
        }

        return x;
    }

    /**
     * Renders the given range in the model as normal unselected
     * text.  Uses the foreground or disabled color to render the text.
     *
     * <p>
     *  将模型中的给定范围渲染为正常未选择的文本。使用前景或禁用的颜色渲染文本。
     * 
     * 
     * @param g the graphics context
     * @param x the starting X coordinate &gt;= 0
     * @param y the starting Y coordinate &gt;= 0
     * @param p0 the beginning position in the model &gt;= 0
     * @param p1 the ending position in the model &gt;= 0
     * @return the X location of the end of the range &gt;= 0
     * @exception BadLocationException if the range is invalid
     */
    protected int drawUnselectedText(Graphics g, int x, int y,
                                     int p0, int p1) throws BadLocationException {
        g.setColor(unselected);
        Document doc = getDocument();
        Segment s = SegmentCache.getSharedSegment();
        doc.getText(p0, p1 - p0, s);
        int ret = Utilities.drawTabbedText(this, s, x, y, g, this, p0);
        SegmentCache.releaseSharedSegment(s);
        return ret;
    }

    /**
     * Renders the given range in the model as selected text.  This
     * is implemented to render the text in the color specified in
     * the hosting component.  It assumes the highlighter will render
     * the selected background.
     *
     * <p>
     *  将模型中的给定范围渲染为选定文本。这被实现来渲染在托管组件中指定的颜色的文本。它假定荧光笔将呈现所选择的背景。
     * 
     * 
     * @param g the graphics context
     * @param x the starting X coordinate &gt;= 0
     * @param y the starting Y coordinate &gt;= 0
     * @param p0 the beginning position in the model &gt;= 0
     * @param p1 the ending position in the model &gt;= 0
     * @return the location of the end of the range
     * @exception BadLocationException if the range is invalid
     */
    protected int drawSelectedText(Graphics g, int x,
                                   int y, int p0, int p1) throws BadLocationException {
        g.setColor(selected);
        Document doc = getDocument();
        Segment s = SegmentCache.getSharedSegment();
        doc.getText(p0, p1 - p0, s);
        int ret = Utilities.drawTabbedText(this, s, x, y, g, this, p0);
        SegmentCache.releaseSharedSegment(s);
        return ret;
    }

    /**
     * Gives access to a buffer that can be used to fetch
     * text from the associated document.
     *
     * <p>
     *  提供对可用于从相关联的文档获取文本的缓冲区的访问。
     * 
     * 
     * @return the buffer
     */
    protected final Segment getLineBuffer() {
        if (lineBuffer == null) {
            lineBuffer = new Segment();
        }
        return lineBuffer;
    }

    /**
     * Checks to see if the font metrics and longest line
     * are up-to-date.
     *
     * <p>
     *  检查字体指标和最长行是否是最新的。
     * 
     * 
     * @since 1.4
     */
    protected void updateMetrics() {
        Component host = getContainer();
        Font f = host.getFont();
        if (font != f) {
            // The font changed, we need to recalculate the
            // longest line.
            calculateLongestLine();
            tabSize = getTabSize() * metrics.charWidth('m');
        }
    }

    // ---- View methods ----------------------------------------------------

    /**
     * Determines the preferred span for this view along an
     * axis.
     *
     * <p>
     *  确定沿着轴的此视图的首选跨度。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;= 0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis
     */
    public float getPreferredSpan(int axis) {
        updateMetrics();
        switch (axis) {
        case View.X_AXIS:
            return getLineWidth(longLine);
        case View.Y_AXIS:
            return getElement().getElementCount() * metrics.getHeight();
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    /**
     * Renders using the given rendering surface and area on that surface.
     * The view may need to do layout and create child views to enable
     * itself to render into the given allocation.
     *
     * <p>
     *  使用给定的渲染表面和该表面上的区域渲染。视图可能需要进行布局和创建子视图,以使自身能够呈现给定的分配。
     * 
     * 
     * @param g the rendering surface to use
     * @param a the allocated region to render into
     *
     * @see View#paint
     */
    public void paint(Graphics g, Shape a) {
        Shape originalA = a;
        a = adjustPaintRegion(a);
        Rectangle alloc = (Rectangle) a;
        tabBase = alloc.x;
        JTextComponent host = (JTextComponent) getContainer();
        Highlighter h = host.getHighlighter();
        g.setFont(host.getFont());
        sel0 = host.getSelectionStart();
        sel1 = host.getSelectionEnd();
        unselected = (host.isEnabled()) ?
            host.getForeground() : host.getDisabledTextColor();
        Caret c = host.getCaret();
        selected = c.isSelectionVisible() && h != null ?
                       host.getSelectedTextColor() : unselected;
        updateMetrics();

        // If the lines are clipped then we don't expend the effort to
        // try and paint them.  Since all of the lines are the same height
        // with this object, determination of what lines need to be repainted
        // is quick.
        Rectangle clip = g.getClipBounds();
        int fontHeight = metrics.getHeight();
        int heightBelow = (alloc.y + alloc.height) - (clip.y + clip.height);
        int heightAbove = clip.y - alloc.y;
        int linesBelow, linesAbove, linesTotal;

        if (fontHeight > 0) {
            linesBelow = Math.max(0, heightBelow / fontHeight);
            linesAbove = Math.max(0, heightAbove / fontHeight);
            linesTotal = alloc.height / fontHeight;
            if (alloc.height % fontHeight != 0) {
                linesTotal++;
            }
        } else {
            linesBelow = linesAbove = linesTotal = 0;
        }

        // update the visible lines
        Rectangle lineArea = lineToRect(a, linesAbove);
        int y = lineArea.y + metrics.getAscent();
        int x = lineArea.x;
        Element map = getElement();
        int lineCount = map.getElementCount();
        int endLine = Math.min(lineCount, linesTotal - linesBelow);
        lineCount--;
        LayeredHighlighter dh = (h instanceof LayeredHighlighter) ?
                           (LayeredHighlighter)h : null;
        for (int line = linesAbove; line < endLine; line++) {
            if (dh != null) {
                Element lineElement = map.getElement(line);
                if (line == lineCount) {
                    dh.paintLayeredHighlights(g, lineElement.getStartOffset(),
                                              lineElement.getEndOffset(),
                                              originalA, host, this);
                }
                else {
                    dh.paintLayeredHighlights(g, lineElement.getStartOffset(),
                                              lineElement.getEndOffset() - 1,
                                              originalA, host, this);
                }
            }
            drawLine(line, g, x, y);
            y += fontHeight;
            if (line == 0) {
                // This should never really happen, in so far as if
                // firstLineOffset is non 0, there should only be one
                // line of text.
                x -= firstLineOffset;
            }
        }
    }

    /**
     * Should return a shape ideal for painting based on the passed in
     * Shape <code>a</code>. This is useful if painting in a different
     * region. The default implementation returns <code>a</code>.
     * <p>
     * 应该返回一个理想的绘制形状,基于传递的形式<code> a </code>。如果在不同的地区绘画,这是很有用的。默认实现返回<code> a </code>。
     * 
     */
    Shape adjustPaintRegion(Shape a) {
        return a;
    }

    /**
     * Provides a mapping from the document model coordinate space
     * to the coordinate space of the view mapped to it.
     *
     * <p>
     *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
     * 
     * 
     * @param pos the position to convert &gt;= 0
     * @param a the allocated region to render into
     * @return the bounding box of the given position
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document
     * @see View#modelToView
     */
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        // line coordinates
        Document doc = getDocument();
        Element map = getElement();
        int lineIndex = map.getElementIndex(pos);
        if (lineIndex < 0) {
            return lineToRect(a, 0);
        }
        Rectangle lineArea = lineToRect(a, lineIndex);

        // determine span from the start of the line
        tabBase = lineArea.x;
        Element line = map.getElement(lineIndex);
        int p0 = line.getStartOffset();
        Segment s = SegmentCache.getSharedSegment();
        doc.getText(p0, pos - p0, s);
        int xOffs = Utilities.getTabbedTextWidth(s, metrics, tabBase, this,p0);
        SegmentCache.releaseSharedSegment(s);

        // fill in the results and return
        lineArea.x += xOffs;
        lineArea.width = 1;
        lineArea.height = metrics.getHeight();
        return lineArea;
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * 
     * @param fx the X coordinate &gt;= 0
     * @param fy the Y coordinate &gt;= 0
     * @param a the allocated region to render into
     * @return the location within the model that best represents the
     *  given point in the view &gt;= 0
     * @see View#viewToModel
     */
    public int viewToModel(float fx, float fy, Shape a, Position.Bias[] bias) {
        // PENDING(prinz) properly calculate bias
        bias[0] = Position.Bias.Forward;

        Rectangle alloc = a.getBounds();
        Document doc = getDocument();
        int x = (int) fx;
        int y = (int) fy;
        if (y < alloc.y) {
            // above the area covered by this icon, so the the position
            // is assumed to be the start of the coverage for this view.
            return getStartOffset();
        } else if (y > alloc.y + alloc.height) {
            // below the area covered by this icon, so the the position
            // is assumed to be the end of the coverage for this view.
            return getEndOffset() - 1;
        } else {
            // positioned within the coverage of this view vertically,
            // so we figure out which line the point corresponds to.
            // if the line is greater than the number of lines contained, then
            // simply use the last line as it represents the last possible place
            // we can position to.
            Element map = doc.getDefaultRootElement();
            int fontHeight = metrics.getHeight();
            int lineIndex = (fontHeight > 0 ?
                                Math.abs((y - alloc.y) / fontHeight) :
                                map.getElementCount() - 1);
            if (lineIndex >= map.getElementCount()) {
                return getEndOffset() - 1;
            }
            Element line = map.getElement(lineIndex);
            int dx = 0;
            if (lineIndex == 0) {
                alloc.x += firstLineOffset;
                alloc.width -= firstLineOffset;
            }
            if (x < alloc.x) {
                // point is to the left of the line
                return line.getStartOffset();
            } else if (x > alloc.x + alloc.width) {
                // point is to the right of the line
                return line.getEndOffset() - 1;
            } else {
                // Determine the offset into the text
                try {
                    int p0 = line.getStartOffset();
                    int p1 = line.getEndOffset() - 1;
                    Segment s = SegmentCache.getSharedSegment();
                    doc.getText(p0, p1 - p0, s);
                    tabBase = alloc.x;
                    int offs = p0 + Utilities.getTabbedTextOffset(s, metrics,
                                                                  tabBase, x, this, p0);
                    SegmentCache.releaseSharedSegment(s);
                    return offs;
                } catch (BadLocationException e) {
                    // should not happen
                    return -1;
                }
            }
        }
    }

    /**
     * Gives notification that something was inserted into the document
     * in a location that this view is responsible for.
     *
     * <p>
     *  提供通知,说明在此数据视图负责的位置,文档中插入了某些内容。
     * 
     * 
     * @param changes the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#insertUpdate
     */
    public void insertUpdate(DocumentEvent changes, Shape a, ViewFactory f) {
        updateDamage(changes, a, f);
    }

    /**
     * Gives notification that something was removed from the document
     * in a location that this view is responsible for.
     *
     * <p>
     *  提供通知,说明该视图负责的位置中的文档被删除了。
     * 
     * 
     * @param changes the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#removeUpdate
     */
    public void removeUpdate(DocumentEvent changes, Shape a, ViewFactory f) {
        updateDamage(changes, a, f);
    }

    /**
     * Gives notification from the document that attributes were changed
     * in a location that this view is responsible for.
     *
     * <p>
     *  从文档中提供属性在此视图负责的位置中更改的通知。
     * 
     * 
     * @param changes the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#changedUpdate
     */
    public void changedUpdate(DocumentEvent changes, Shape a, ViewFactory f) {
        updateDamage(changes, a, f);
    }

    /**
     * Sets the size of the view.  This should cause
     * layout of the view along the given axis, if it
     * has any layout duties.
     *
     * <p>
     *  设置视图的大小。这应该导致沿给定轴的视图的布局,如果它有任何布局职责。
     * 
     * 
     * @param width the width &gt;= 0
     * @param height the height &gt;= 0
     */
    public void setSize(float width, float height) {
        super.setSize(width, height);
        updateMetrics();
    }

    // --- TabExpander methods ------------------------------------------

    /**
     * Returns the next tab stop position after a given reference position.
     * This implementation does not support things like centering so it
     * ignores the tabOffset argument.
     *
     * <p>
     *  返回给定参考位置后的下一个制表符停止位置。这个实现不支持像定中心这样的东西,所以它忽略了tabOffset参数。
     * 
     * 
     * @param x the current position &gt;= 0
     * @param tabOffset the position within the text stream
     *   that the tab occurred at &gt;= 0.
     * @return the tab stop, measured in points &gt;= 0
     */
    public float nextTabStop(float x, int tabOffset) {
        if (tabSize == 0) {
            return x;
        }
        int ntabs = (((int) x) - tabBase) / tabSize;
        return tabBase + ((ntabs + 1) * tabSize);
    }

    // --- local methods ------------------------------------------------

    /**
     * Repaint the region of change covered by the given document
     * event.  Damages the line that begins the range to cover
     * the case when the insert/remove is only on one line.
     * If lines are added or removed, damages the whole
     * view.  The longest line is checked to see if it has
     * changed.
     *
     * <p>
     *  重绘由给定文档事件覆盖的更改区域。损坏开始范围的线,以覆盖插入/移除只在一条线上的情况。如果添加或删除线,会损坏整个视图。检查最长行以查看其是否已更改。
     * 
     * 
     * @since 1.4
     */
    protected void updateDamage(DocumentEvent changes, Shape a, ViewFactory f) {
        Component host = getContainer();
        updateMetrics();
        Element elem = getElement();
        DocumentEvent.ElementChange ec = changes.getChange(elem);

        Element[] added = (ec != null) ? ec.getChildrenAdded() : null;
        Element[] removed = (ec != null) ? ec.getChildrenRemoved() : null;
        if (((added != null) && (added.length > 0)) ||
            ((removed != null) && (removed.length > 0))) {
            // lines were added or removed...
            if (added != null) {
                int currWide = getLineWidth(longLine);
                for (int i = 0; i < added.length; i++) {
                    int w = getLineWidth(added[i]);
                    if (w > currWide) {
                        currWide = w;
                        longLine = added[i];
                    }
                }
            }
            if (removed != null) {
                for (int i = 0; i < removed.length; i++) {
                    if (removed[i] == longLine) {
                        calculateLongestLine();
                        break;
                    }
                }
            }
            preferenceChanged(null, true, true);
            host.repaint();
        } else {
            Element map = getElement();
            int line = map.getElementIndex(changes.getOffset());
            damageLineRange(line, line, a, host);
            if (changes.getType() == DocumentEvent.EventType.INSERT) {
                // check to see if the line is longer than current
                // longest line.
                int w = getLineWidth(longLine);
                Element e = map.getElement(line);
                if (e == longLine) {
                    preferenceChanged(null, true, false);
                } else if (getLineWidth(e) > w) {
                    longLine = e;
                    preferenceChanged(null, true, false);
                }
            } else if (changes.getType() == DocumentEvent.EventType.REMOVE) {
                if (map.getElement(line) == longLine) {
                    // removed from longest line... recalc
                    calculateLongestLine();
                    preferenceChanged(null, true, false);
                }
            }
        }
    }

    /**
     * Repaint the given line range.
     *
     * <p>
     *  重新绘制给定的线范围。
     * 
     * 
     * @param host the component hosting the view (used to call repaint)
     * @param a  the region allocated for the view to render into
     * @param line0 the starting line number to repaint.  This must
     *   be a valid line number in the model.
     * @param line1 the ending line number to repaint.  This must
     *   be a valid line number in the model.
     * @since 1.4
     */
    protected void damageLineRange(int line0, int line1, Shape a, Component host) {
        if (a != null) {
            Rectangle area0 = lineToRect(a, line0);
            Rectangle area1 = lineToRect(a, line1);
            if ((area0 != null) && (area1 != null)) {
                Rectangle damage = area0.union(area1);
                host.repaint(damage.x, damage.y, damage.width, damage.height);
            } else {
                host.repaint();
            }
        }
    }

    /**
     * Determine the rectangle that represents the given line.
     *
     * <p>
     *  确定表示给定行的矩形。
     * 
     * 
     * @param a  the region allocated for the view to render into
     * @param line the line number to find the region of.  This must
     *   be a valid line number in the model.
     * @since 1.4
     */
    protected Rectangle lineToRect(Shape a, int line) {
        Rectangle r = null;
        updateMetrics();
        if (metrics != null) {
            Rectangle alloc = a.getBounds();
            if (line == 0) {
                alloc.x += firstLineOffset;
                alloc.width -= firstLineOffset;
            }
            r = new Rectangle(alloc.x, alloc.y + (line * metrics.getHeight()),
                              alloc.width, metrics.getHeight());
        }
        return r;
    }

    /**
     * Iterate over the lines represented by the child elements
     * of the element this view represents, looking for the line
     * that is the longest.  The <em>longLine</em> variable is updated to
     * represent the longest line contained.  The <em>font</em> variable
     * is updated to indicate the font used to calculate the
     * longest line.
     * <p>
     * 迭代由此视图表示的元素的子元素表示的线,寻找最长的线。 <em> longLine </em>变量已更新,以表示包含的最长行。更改<em>字体</em>变量以指示用于计算最长行的字体。
     * 
     */
    private void calculateLongestLine() {
        Component c = getContainer();
        font = c.getFont();
        metrics = c.getFontMetrics(font);
        Document doc = getDocument();
        Element lines = getElement();
        int n = lines.getElementCount();
        int maxWidth = -1;
        for (int i = 0; i < n; i++) {
            Element line = lines.getElement(i);
            int w = getLineWidth(line);
            if (w > maxWidth) {
                maxWidth = w;
                longLine = line;
            }
        }
    }

    /**
     * Calculate the width of the line represented by
     * the given element.  It is assumed that the font
     * and font metrics are up-to-date.
     * <p>
     *  计算由给定元素表示的线的宽度。假设字体和字体度量是最新的。
     * 
     */
    private int getLineWidth(Element line) {
        if (line == null) {
            return 0;
        }
        int p0 = line.getStartOffset();
        int p1 = line.getEndOffset();
        int w;
        Segment s = SegmentCache.getSharedSegment();
        try {
            line.getDocument().getText(p0, p1 - p0, s);
            w = Utilities.getTabbedTextWidth(s, metrics, tabBase, this, p0);
        } catch (BadLocationException ble) {
            w = 0;
        }
        SegmentCache.releaseSharedSegment(s);
        return w;
    }

    // --- member variables -----------------------------------------------

    /**
     * Font metrics for the current font.
     * <p>
     *  当前字体的字体指标。
     * 
     */
    protected FontMetrics metrics;

    /**
     * The current longest line.  This is used to calculate
     * the preferred width of the view.  Since the calculation
     * is potentially expensive we try to avoid it by stashing
     * which line is currently the longest.
     * <p>
     *  当前最长的行。这用于计算视图的首选宽度。由于计算是潜在的昂贵,我们试图通过隐藏哪条线当前是最长的来避免它。
     * 
     */
    Element longLine;

    /**
     * Font used to calculate the longest line... if this
     * changes we need to recalculate the longest line
     * <p>
     *  用于计算最长行的字体...如果这种更改,我们需要重新计算最长的行
     * 
     */
    Font font;

    Segment lineBuffer;
    int tabSize;
    int tabBase;

    int sel0;
    int sel1;
    Color unselected;
    Color selected;

    /**
     * Offset of where to draw the first character on the first line.
     * This is a hack and temporary until we can better address the problem
     * of text measuring. This field is actually never set directly in
     * PlainView, but by FieldView.
     * <p>
     *  在第一行绘制第一个字符的位置的偏移量。这是一个黑客和暂时,直到我们可以更好地解决文本测量的问题。该字段实际上从不直接在PlainView中设置,而是由FieldView设置。
     */
    int firstLineOffset;

}
