/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.*;
import java.text.BreakIterator;
import javax.swing.event.*;
import java.util.BitSet;
import java.util.Locale;

import javax.swing.UIManager;
import sun.swing.SwingUtilities2;
import static sun.swing.SwingUtilities2.IMPLIED_CR;

/**
 * A GlyphView is a styled chunk of text that represents a view
 * mapped over an element in the text model. This view is generally
 * responsible for displaying text glyphs using character level
 * attributes in some way.
 * An implementation of the GlyphPainter class is used to do the
 * actual rendering and model/view translations.  This separates
 * rendering from layout and management of the association with
 * the model.
 * <p>
 * The view supports breaking for the purpose of formatting.
 * The fragments produced by breaking share the view that has
 * primary responsibility for the element (i.e. they are nested
 * classes and carry only a small amount of state of their own)
 * so they can share its resources.
 * <p>
 * Since this view
 * represents text that may have tabs embedded in it, it implements the
 * <code>TabableView</code> interface.  Tabs will only be
 * expanded if this view is embedded in a container that does
 * tab expansion.  ParagraphView is an example of a container
 * that does tab expansion.
 * <p>
 *
 * <p>
 *  GlyphView是一种样式化的文本块,代表在文本模型中的元素上映射的视图。此视图通常负责以某种方式使用字符级属性显示文本字形。 GlyphPainter类的实现用于执行实际的渲染和模型/视图转换。
 * 这将渲染从布局和管理与模型的关联分离。
 * <p>
 *  视图支持打破格式化的目的。断开产生的片段共享对元素负有主要责任的视图(即它们是嵌套类并且仅携带自己的少量状态),以便它们可以共享其资源。
 * <p>
 *  由于此视图表示可以在其中嵌入选项卡的文本,因此它实现了<code> TabableView </code>界面。仅当此视图嵌入到执行制表符展开的容器中时,才会展开标签。
 *  ParagraphView是执行制表符展开的容器的示例。
 * <p>
 * 
 * 
 * @since 1.3
 *
 * @author  Timothy Prinzing
 */
public class GlyphView extends View implements TabableView, Cloneable {

    /**
     * Constructs a new view wrapped on an element.
     *
     * <p>
     *  构造包裹在元素上的新视图。
     * 
     * 
     * @param elem the element
     */
    public GlyphView(Element elem) {
        super(elem);
        offset = 0;
        length = 0;
        Element parent = elem.getParentElement();
        AttributeSet attr = elem.getAttributes();

        //         if there was an implied CR
        impliedCR = (attr != null && attr.getAttribute(IMPLIED_CR) != null &&
        //         if this is non-empty paragraph
                   parent != null && parent.getElementCount() > 1);
        skipWidth = elem.getName().equals("br");
    }

    /**
     * Creates a shallow copy.  This is used by the
     * createFragment and breakView methods.
     *
     * <p>
     *  创建浅拷贝。这由createFragment和breakView方法使用。
     * 
     * 
     * @return the copy
     */
    protected final Object clone() {
        Object o;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            o = null;
        }
        return o;
    }

    /**
     * Fetch the currently installed glyph painter.
     * If a painter has not yet been installed, and
     * a default was not yet needed, null is returned.
     * <p>
     *  获取当前安装的字形画家。如果尚未安装绘图器,并且尚不需要默认值,则返回null。
     * 
     */
    public GlyphPainter getGlyphPainter() {
        return painter;
    }

    /**
     * Sets the painter to use for rendering glyphs.
     * <p>
     *  设置要用于呈现字形的绘图器。
     * 
     */
    public void setGlyphPainter(GlyphPainter p) {
        painter = p;
    }

    /**
     * Fetch a reference to the text that occupies
     * the given range.  This is normally used by
     * the GlyphPainter to determine what characters
     * it should render glyphs for.
     *
     * <p>
     * 获取占用给定范围的文本的引用。这通常由GlyphPainter使用来确定应该为其呈现字形的字符。
     * 
     * 
     * @param p0  the starting document offset &gt;= 0
     * @param p1  the ending document offset &gt;= p0
     * @return    the <code>Segment</code> containing the text
     */
     public Segment getText(int p0, int p1) {
         // When done with the returned Segment it should be released by
         // invoking:
         //    SegmentCache.releaseSharedSegment(segment);
         Segment text = SegmentCache.getSharedSegment();
         try {
             Document doc = getDocument();
             doc.getText(p0, p1 - p0, text);
         } catch (BadLocationException bl) {
             throw new StateInvariantError("GlyphView: Stale view: " + bl);
         }
         return text;
     }

    /**
     * Fetch the background color to use to render the
     * glyphs.  If there is no background color, null should
     * be returned.  This is implemented to call
     * <code>StyledDocument.getBackground</code> if the associated
     * document is a styled document, otherwise it returns null.
     * <p>
     *  获取用于渲染字形的背景颜色。如果没有背景颜色,应返回null。
     * 这是实现调用<code> StyledDocument.getBackground </code>如果关联的文档是一个样式文档,否则返回null。
     * 
     */
    public Color getBackground() {
        Document doc = getDocument();
        if (doc instanceof StyledDocument) {
            AttributeSet attr = getAttributes();
            if (attr.isDefined(StyleConstants.Background)) {
                return ((StyledDocument)doc).getBackground(attr);
            }
        }
        return null;
    }

    /**
     * Fetch the foreground color to use to render the
     * glyphs.  If there is no foreground color, null should
     * be returned.  This is implemented to call
     * <code>StyledDocument.getBackground</code> if the associated
     * document is a StyledDocument.  If the associated document
     * is not a StyledDocument, the associated components foreground
     * color is used.  If there is no associated component, null
     * is returned.
     * <p>
     *  获取要用于渲染字形的前景颜色。如果没有前景色,应返回null。
     * 如果相关联的文档是StyledDocument,则实现调用<code> StyledDocument.getBackground </code>。
     * 如果关联文档不是StyledDocument,则使用相关联的组件前景色。如果没有相关联的组件,则返回null。
     * 
     */
    public Color getForeground() {
        Document doc = getDocument();
        if (doc instanceof StyledDocument) {
            AttributeSet attr = getAttributes();
            return ((StyledDocument)doc).getForeground(attr);
        }
        Component c = getContainer();
        if (c != null) {
            return c.getForeground();
        }
        return null;
    }

    /**
     * Fetch the font that the glyphs should be based
     * upon.  This is implemented to call
     * <code>StyledDocument.getFont</code> if the associated
     * document is a StyledDocument.  If the associated document
     * is not a StyledDocument, the associated components font
     * is used.  If there is no associated component, null
     * is returned.
     * <p>
     *  获取字形应基于的字体。如果相关联的文档是StyledDocument,则实现调用<code> StyledDocument.getFont </code>。
     * 如果关联文档不是StyledDocument,则使用关联的组件字体。如果没有相关联的组件,则返回null。
     * 
     */
    public Font getFont() {
        Document doc = getDocument();
        if (doc instanceof StyledDocument) {
            AttributeSet attr = getAttributes();
            return ((StyledDocument)doc).getFont(attr);
        }
        Component c = getContainer();
        if (c != null) {
            return c.getFont();
        }
        return null;
    }

    /**
     * Determine if the glyphs should be underlined.  If true,
     * an underline should be drawn through the baseline.
     * <p>
     *  确定字形是否应加下划线。如果为true,则应在基线中绘制下划线。
     * 
     */
    public boolean isUnderline() {
        AttributeSet attr = getAttributes();
        return StyleConstants.isUnderline(attr);
    }

    /**
     * Determine if the glyphs should have a strikethrough
     * line.  If true, a line should be drawn through the center
     * of the glyphs.
     * <p>
     *  确定字形是否应该有删除线。如果为true,则应通过字形的中心绘制一条线。
     * 
     */
    public boolean isStrikeThrough() {
        AttributeSet attr = getAttributes();
        return StyleConstants.isStrikeThrough(attr);
    }

    /**
     * Determine if the glyphs should be rendered as superscript.
     * <p>
     *  确定字形是否应该渲染为上标。
     * 
     */
    public boolean isSubscript() {
        AttributeSet attr = getAttributes();
        return StyleConstants.isSubscript(attr);
    }

    /**
     * Determine if the glyphs should be rendered as subscript.
     * <p>
     *  确定字形是否应该渲染为下标。
     * 
     */
    public boolean isSuperscript() {
        AttributeSet attr = getAttributes();
        return StyleConstants.isSuperscript(attr);
    }

    /**
     * Fetch the TabExpander to use if tabs are present in this view.
     * <p>
     * 如果此视图中存在选项卡,请提取TabExpander以使用。
     * 
     */
    public TabExpander getTabExpander() {
        return expander;
    }

    /**
     * Check to see that a glyph painter exists.  If a painter
     * doesn't exist, a default glyph painter will be installed.
     * <p>
     *  检查字形画家是否存在。如果画家不存在,则将安装默认字形画家。
     * 
     */
    protected void checkPainter() {
        if (painter == null) {
            if (defaultPainter == null) {
                // the classname should probably come from a property file.
                String classname = "javax.swing.text.GlyphPainter1";
                try {
                    Class c;
                    ClassLoader loader = getClass().getClassLoader();
                    if (loader != null) {
                        c = loader.loadClass(classname);
                    } else {
                        c = Class.forName(classname);
                    }
                    Object o = c.newInstance();
                    if (o instanceof GlyphPainter) {
                        defaultPainter = (GlyphPainter) o;
                    }
                } catch (Throwable e) {
                    throw new StateInvariantError("GlyphView: Can't load glyph painter: "
                                                  + classname);
                }
            }
            setGlyphPainter(defaultPainter.getPainter(this, getStartOffset(),
                                                      getEndOffset()));
        }
    }

    // --- TabableView methods --------------------------------------

    /**
     * Determines the desired span when using the given
     * tab expansion implementation.
     *
     * <p>
     *  当使用给定的选项卡扩展实现时,确定所需的跨度。
     * 
     * 
     * @param x the position the view would be located
     *  at for the purpose of tab expansion &gt;= 0.
     * @param e how to expand the tabs when encountered.
     * @return the desired span &gt;= 0
     * @see TabableView#getTabbedSpan
     */
    public float getTabbedSpan(float x, TabExpander e) {
        checkPainter();

        TabExpander old = expander;
        expander = e;

        if (expander != old) {
            // setting expander can change horizontal span of the view,
            // so we have to call preferenceChanged()
            preferenceChanged(null, true, false);
        }

        this.x = (int) x;
        int p0 = getStartOffset();
        int p1 = getEndOffset();
        float width = painter.getSpan(this, p0, p1, expander, x);
        return width;
    }

    /**
     * Determines the span along the same axis as tab
     * expansion for a portion of the view.  This is
     * intended for use by the TabExpander for cases
     * where the tab expansion involves aligning the
     * portion of text that doesn't have whitespace
     * relative to the tab stop.  There is therefore
     * an assumption that the range given does not
     * contain tabs.
     * <p>
     * This method can be called while servicing the
     * getTabbedSpan or getPreferredSize.  It has to
     * arrange for its own text buffer to make the
     * measurements.
     *
     * <p>
     *  确定视图的一部分沿着同一轴的选项卡扩展的跨度。这是为TabExpander使用的情况,其中选项卡扩展涉及对齐没有相对于制表符的空格的文本部分。因此,假定给定的范围不包含制表符。
     * <p>
     *  此方法可以在服务getTabbedSpan或getPreferredSize时调用。它必须安排自己的文本缓冲区进行测量。
     * 
     * 
     * @param p0 the starting document offset &gt;= 0
     * @param p1 the ending document offset &gt;= p0
     * @return the span &gt;= 0
     */
    public float getPartialSpan(int p0, int p1) {
        checkPainter();
        float width = painter.getSpan(this, p0, p1, expander, x);
        return width;
    }

    // --- View methods ---------------------------------------------

    /**
     * Fetches the portion of the model that this view is responsible for.
     *
     * <p>
     *  获取此视图负责的模型部分。
     * 
     * 
     * @return the starting offset into the model
     * @see View#getStartOffset
     */
    public int getStartOffset() {
        Element e = getElement();
        return (length > 0) ? e.getStartOffset() + offset : e.getStartOffset();
    }

    /**
     * Fetches the portion of the model that this view is responsible for.
     *
     * <p>
     *  获取此视图负责的模型部分。
     * 
     * 
     * @return the ending offset into the model
     * @see View#getEndOffset
     */
    public int getEndOffset() {
        Element e = getElement();
        return (length > 0) ? e.getStartOffset() + offset + length : e.getEndOffset();
    }

    /**
     * Lazily initializes the selections field
     * <p>
     *  Lazily初始化选择字段
     * 
     */
    private void initSelections(int p0, int p1) {
        int viewPosCount = p1 - p0 + 1;
        if (selections == null || viewPosCount > selections.length) {
            selections = new byte[viewPosCount];
            return;
        }
        for (int i = 0; i < viewPosCount; selections[i++] = 0);
    }

    /**
     * Renders a portion of a text style run.
     *
     * <p>
     *  呈现文本样式运行的一部分。
     * 
     * 
     * @param g the rendering surface to use
     * @param a the allocated region to render into
     */
    public void paint(Graphics g, Shape a) {
        checkPainter();

        boolean paintedText = false;
        Component c = getContainer();
        int p0 = getStartOffset();
        int p1 = getEndOffset();
        Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a : a.getBounds();
        Color bg = getBackground();
        Color fg = getForeground();

        if (c != null && ! c.isEnabled()) {
            fg = (c instanceof JTextComponent ?
                ((JTextComponent)c).getDisabledTextColor() :
                UIManager.getColor("textInactiveText"));
        }
        if (bg != null) {
            g.setColor(bg);
            g.fillRect(alloc.x, alloc.y, alloc.width, alloc.height);
        }
        if (c instanceof JTextComponent) {
            JTextComponent tc = (JTextComponent) c;
            Highlighter h = tc.getHighlighter();
            if (h instanceof LayeredHighlighter) {
                ((LayeredHighlighter)h).paintLayeredHighlights
                    (g, p0, p1, a, tc, this);
            }
        }

        if (Utilities.isComposedTextElement(getElement())) {
            Utilities.paintComposedText(g, a.getBounds(), this);
            paintedText = true;
        } else if(c instanceof JTextComponent) {
            JTextComponent tc = (JTextComponent) c;
            Color selFG = tc.getSelectedTextColor();

            if (// there's a highlighter (bug 4532590), and
                (tc.getHighlighter() != null) &&
                // selected text color is different from regular foreground
                (selFG != null) && !selFG.equals(fg)) {

                Highlighter.Highlight[] h = tc.getHighlighter().getHighlights();
                if(h.length != 0) {
                    boolean initialized = false;
                    int viewSelectionCount = 0;
                    for (int i = 0; i < h.length; i++) {
                        Highlighter.Highlight highlight = h[i];
                        int hStart = highlight.getStartOffset();
                        int hEnd = highlight.getEndOffset();
                        if (hStart > p1 || hEnd < p0) {
                            // the selection is out of this view
                            continue;
                        }
                        if (!SwingUtilities2.useSelectedTextColor(highlight, tc)) {
                            continue;
                        }
                        if (hStart <= p0 && hEnd >= p1){
                            // the whole view is selected
                            paintTextUsingColor(g, a, selFG, p0, p1);
                            paintedText = true;
                            break;
                        }
                        // the array is lazily created only when the view
                        // is partially selected
                        if (!initialized) {
                            initSelections(p0, p1);
                            initialized = true;
                        }
                        hStart = Math.max(p0, hStart);
                        hEnd = Math.min(p1, hEnd);
                        paintTextUsingColor(g, a, selFG, hStart, hEnd);
                        // the array represents view positions [0, p1-p0+1]
                        // later will iterate this array and sum its
                        // elements. Positions with sum == 0 are not selected.
                        selections[hStart-p0]++;
                        selections[hEnd-p0]--;

                        viewSelectionCount++;
                    }

                    if (!paintedText && viewSelectionCount > 0) {
                        // the view is partially selected
                        int curPos = -1;
                        int startPos = 0;
                        int viewLen = p1 - p0;
                        while (curPos++ < viewLen) {
                            // searching for the next selection start
                            while(curPos < viewLen &&
                                    selections[curPos] == 0) curPos++;
                            if (startPos != curPos) {
                                // paint unselected text
                                paintTextUsingColor(g, a, fg,
                                        p0 + startPos, p0 + curPos);
                            }
                            int checkSum = 0;
                            // searching for next start position of unselected text
                            while (curPos < viewLen &&
                                    (checkSum += selections[curPos]) != 0) curPos++;
                            startPos = curPos;
                        }
                        paintedText = true;
                    }
                }
            }
        }
        if(!paintedText)
            paintTextUsingColor(g, a, fg, p0, p1);
    }

    /**
     * Paints the specified region of text in the specified color.
     * <p>
     *  以指定的颜色绘制文本的指定区域。
     * 
     */
    final void paintTextUsingColor(Graphics g, Shape a, Color c, int p0, int p1) {
        // render the glyphs
        g.setColor(c);
        painter.paint(this, g, a, p0, p1);

        // render underline or strikethrough if set.
        boolean underline = isUnderline();
        boolean strike = isStrikeThrough();
        if (underline || strike) {
            // calculate x coordinates
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a : a.getBounds();
            View parent = getParent();
            if ((parent != null) && (parent.getEndOffset() == p1)) {
                // strip whitespace on end
                Segment s = getText(p0, p1);
                while (Character.isWhitespace(s.last())) {
                    p1 -= 1;
                    s.count -= 1;
                }
                SegmentCache.releaseSharedSegment(s);
            }
            int x0 = alloc.x;
            int p = getStartOffset();
            if (p != p0) {
                x0 += (int) painter.getSpan(this, p, p0, getTabExpander(), x0);
            }
            int x1 = x0 + (int) painter.getSpan(this, p0, p1, getTabExpander(), x0);

            // calculate y coordinate
            int y = alloc.y + (int)(painter.getHeight(this) - painter.getDescent(this));
            if (underline) {
                int yTmp = y + 1;
                g.drawLine(x0, yTmp, x1, yTmp);
            }
            if (strike) {
                // move y coordinate above baseline
                int yTmp = y - (int) (painter.getAscent(this) * 0.3f);
                g.drawLine(x0, yTmp, x1, yTmp);
            }

        }
    }

    /**
     * Determines the minimum span for this view along an axis.
     *
     * <p>This implementation returns the longest non-breakable area within
     * the view as a minimum span for {@code View.X_AXIS}.</p>
     *
     * <p>
     *  确定沿轴的此视图的最小跨度。
     * 
     *  <p>此实现将视图中最长的不可断开区域作为{@code View.X_AXIS}的最小跨度返回。</p>
     * 
     * 
     * @param axis  may be either {@code View.X_AXIS} or {@code View.Y_AXIS}
     * @return      the minimum span the view can be rendered into
     * @throws IllegalArgumentException if the {@code axis} parameter is invalid
     * @see         javax.swing.text.View#getMinimumSpan
     */
    @Override
    public float getMinimumSpan(int axis) {
        switch (axis) {
            case View.X_AXIS:
                if (minimumSpan < 0) {
                    minimumSpan = 0;
                    int p0 = getStartOffset();
                    int p1 = getEndOffset();
                    while (p1 > p0) {
                        int breakSpot = getBreakSpot(p0, p1);
                        if (breakSpot == BreakIterator.DONE) {
                            // the rest of the view is non-breakable
                            breakSpot = p0;
                        }
                        minimumSpan = Math.max(minimumSpan,
                                getPartialSpan(breakSpot, p1));
                        // Note: getBreakSpot returns the *last* breakspot
                        p1 = breakSpot - 1;
                    }
                }
                return minimumSpan;
            case View.Y_AXIS:
                return super.getMinimumSpan(axis);
            default:
                throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

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
     */
    public float getPreferredSpan(int axis) {
        if (impliedCR) {
            return 0;
        }
        checkPainter();
        int p0 = getStartOffset();
        int p1 = getEndOffset();
        switch (axis) {
        case View.X_AXIS:
            if (skipWidth) {
                return 0;
            }
            return painter.getSpan(this, p0, p1, expander, this.x);
        case View.Y_AXIS:
            float h = painter.getHeight(this);
            if (isSuperscript()) {
                h += h/3;
            }
            return h;
        default:
            throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    /**
     * Determines the desired alignment for this view along an
     * axis.  For the label, the alignment is along the font
     * baseline for the y axis, and the superclasses alignment
     * along the x axis.
     *
     * <p>
     *  确定沿着轴的该视图的期望对准。对于标签,对齐是沿着y轴的字体基线,并且超类沿着x轴对齐。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return the desired alignment.  This should be a value
     *   between 0.0 and 1.0 inclusive, where 0 indicates alignment at the
     *   origin and 1.0 indicates alignment to the full span
     *   away from the origin.  An alignment of 0.5 would be the
     *   center of the view.
     */
    public float getAlignment(int axis) {
        checkPainter();
        if (axis == View.Y_AXIS) {
            boolean sup = isSuperscript();
            boolean sub = isSubscript();
            float h = painter.getHeight(this);
            float d = painter.getDescent(this);
            float a = painter.getAscent(this);
            float align;
            if (sup) {
                align = 1.0f;
            } else if (sub) {
                align = (h > 0) ? (h - (d + (a / 2))) / h : 0;
            } else {
                align = (h > 0) ? (h - d) / h : 0;
            }
            return align;
        }
        return super.getAlignment(axis);
    }

    /**
     * Provides a mapping from the document model coordinate space
     * to the coordinate space of the view mapped to it.
     *
     * <p>
     * 提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
     * 
     * 
     * @param pos the position to convert &gt;= 0
     * @param a   the allocated region to render into
     * @param b   either <code>Position.Bias.Forward</code>
     *                or <code>Position.Bias.Backward</code>
     * @return the bounding box of the given position
     * @exception BadLocationException  if the given position does not represent a
     *   valid location in the associated document
     * @see View#modelToView
     */
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        checkPainter();
        return painter.modelToView(this, pos, b, a);
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param a the allocated region to render into
     * @param biasReturn either <code>Position.Bias.Forward</code>
     *  or <code>Position.Bias.Backward</code> is returned as the
     *  zero-th element of this array
     * @return the location within the model that best represents the
     *  given point of view &gt;= 0
     * @see View#viewToModel
     */
    public int viewToModel(float x, float y, Shape a, Position.Bias[] biasReturn) {
        checkPainter();
        return painter.viewToModel(this, x, y, a, biasReturn);
    }

    /**
     * Determines how attractive a break opportunity in
     * this view is.  This can be used for determining which
     * view is the most attractive to call <code>breakView</code>
     * on in the process of formatting.  The
     * higher the weight, the more attractive the break.  A
     * value equal to or lower than <code>View.BadBreakWeight</code>
     * should not be considered for a break.  A value greater
     * than or equal to <code>View.ForcedBreakWeight</code> should
     * be broken.
     * <p>
     * This is implemented to forward to the superclass for
     * the Y_AXIS.  Along the X_AXIS the following values
     * may be returned.
     * <dl>
     * <dt><b>View.ExcellentBreakWeight</b>
     * <dd>if there is whitespace proceeding the desired break
     *   location.
     * <dt><b>View.BadBreakWeight</b>
     * <dd>if the desired break location results in a break
     *   location of the starting offset.
     * <dt><b>View.GoodBreakWeight</b>
     * <dd>if the other conditions don't occur.
     * </dl>
     * This will normally result in the behavior of breaking
     * on a whitespace location if one can be found, otherwise
     * breaking between characters.
     *
     * <p>
     *  确定此视图中的休息机会的吸引力。这可以用于确定哪个视图是最有吸引力的在格式化过程中调用<code> breakView </code>。重量越高,断裂越有吸引力。
     * 等于或低于<code> View.BadBreakWeight </code>的值不应被视为中断。大于或等于<code> View.ForcedBreakWeight </code>的值应该被破坏。
     * <p>
     *  这被实现为转发到Y_AXIS的超类。沿X_AXIS可以返回以下值。
     * <dl>
     *  <dt> <b> View.ExcellentBreakWeight </b> <dd>如果有空格继续所需的休息位置。
     *  <dt> <b> View.BadBreakWeight </b> <dd>如果所需的断点位置导致起始偏移的断点位置。
     *  <dt> <b> View.GoodBreakWeight </b> <dd>如果其他条件不发生。
     * </dl>
     *  这通常会导致在空格位置上断开的行为,如果可以找到,否则在字符之间断开。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @param pos the potential location of the start of the
     *   broken view &gt;= 0.  This may be useful for calculating tab
     *   positions.
     * @param len specifies the relative length from <em>pos</em>
     *   where a potential break is desired &gt;= 0.
     * @return the weight, which should be a value between
     *   View.ForcedBreakWeight and View.BadBreakWeight.
     * @see LabelView
     * @see ParagraphView
     * @see View#BadBreakWeight
     * @see View#GoodBreakWeight
     * @see View#ExcellentBreakWeight
     * @see View#ForcedBreakWeight
     */
    public int getBreakWeight(int axis, float pos, float len) {
        if (axis == View.X_AXIS) {
            checkPainter();
            int p0 = getStartOffset();
            int p1 = painter.getBoundedPosition(this, p0, pos, len);
            return p1 == p0 ? View.BadBreakWeight :
                   getBreakSpot(p0, p1) != BreakIterator.DONE ?
                            View.ExcellentBreakWeight : View.GoodBreakWeight;
        }
        return super.getBreakWeight(axis, pos, len);
    }

    /**
     * Breaks this view on the given axis at the given length.
     * This is implemented to attempt to break on a whitespace
     * location, and returns a fragment with the whitespace at
     * the end.  If a whitespace location can't be found, the
     * nearest character is used.
     *
     * <p>
     *  在给定长度的给定轴上中断此视图。这是为了尝试在空格位置上断开,并返回一个在结尾处带有空格的片段。如果找不到空格位置,则使用最接近的字符。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @param p0 the location in the model where the
     *  fragment should start it's representation &gt;= 0.
     * @param pos the position along the axis that the
     *  broken view would occupy &gt;= 0.  This may be useful for
     *  things like tab calculations.
     * @param len specifies the distance along the axis
     *  where a potential break is desired &gt;= 0.
     * @return the fragment of the view that represents the
     *  given span, if the view can be broken.  If the view
     *  doesn't support breaking behavior, the view itself is
     *  returned.
     * @see View#breakView
     */
    public View breakView(int axis, int p0, float pos, float len) {
        if (axis == View.X_AXIS) {
            checkPainter();
            int p1 = painter.getBoundedPosition(this, p0, pos, len);
            int breakSpot = getBreakSpot(p0, p1);

            if (breakSpot != -1) {
                p1 = breakSpot;
            }
            // else, no break in the region, return a fragment of the
            // bounded region.
            if (p0 == getStartOffset() && p1 == getEndOffset()) {
                return this;
            }
            GlyphView v = (GlyphView) createFragment(p0, p1);
            v.x = (int) pos;
            return v;
        }
        return this;
    }

    /**
     * Returns a location to break at in the passed in region, or
     * BreakIterator.DONE if there isn't a good location to break at
     * in the specified region.
     * <p>
     * 返回要在传入的区域中折断的位置,如果在指定区域中没有良好的位置,则返回BreakIterator.DONE。
     * 
     */
    private int getBreakSpot(int p0, int p1) {
        if (breakSpots == null) {
            // Re-calculate breakpoints for the whole view
            int start = getStartOffset();
            int end = getEndOffset();
            int[] bs = new int[end + 1 - start];
            int ix = 0;

            // Breaker should work on the parent element because there may be
            // a valid breakpoint at the end edge of the view (space, etc.)
            Element parent = getElement().getParentElement();
            int pstart = (parent == null ? start : parent.getStartOffset());
            int pend = (parent == null ? end : parent.getEndOffset());

            Segment s = getText(pstart, pend);
            s.first();
            BreakIterator breaker = getBreaker();
            breaker.setText(s);

            // Backward search should start from end+1 unless there's NO end+1
            int startFrom = end + (pend > end ? 1 : 0);
            for (;;) {
                startFrom = breaker.preceding(s.offset + (startFrom - pstart))
                          + (pstart - s.offset);
                if (startFrom > start) {
                    // The break spot is within the view
                    bs[ix++] = startFrom;
                } else {
                    break;
                }
            }

            SegmentCache.releaseSharedSegment(s);
            breakSpots = new int[ix];
            System.arraycopy(bs, 0, breakSpots, 0, ix);
        }

        int breakSpot = BreakIterator.DONE;
        for (int i = 0; i < breakSpots.length; i++) {
            int bsp = breakSpots[i];
            if (bsp <= p1) {
                if (bsp > p0) {
                    breakSpot = bsp;
                }
                break;
            }
        }
        return breakSpot;
    }

    /**
     * Return break iterator appropriate for the current document.
     *
     * For non-i18n documents a fast whitespace-based break iterator is used.
     * <p>
     *  适用于当前文档的返回break迭代器。
     * 
     *  对于非i18n文档,使用基于快速基于空格的break迭代器。
     * 
     */
    private BreakIterator getBreaker() {
        Document doc = getDocument();
        if ((doc != null) && Boolean.TRUE.equals(
                    doc.getProperty(AbstractDocument.MultiByteProperty))) {
            Container c = getContainer();
            Locale locale = (c == null ? Locale.getDefault() : c.getLocale());
            return BreakIterator.getLineInstance(locale);
        } else {
            return new WhitespaceBasedBreakIterator();
        }
    }

    /**
     * Creates a view that represents a portion of the element.
     * This is potentially useful during formatting operations
     * for taking measurements of fragments of the view.  If
     * the view doesn't support fragmenting (the default), it
     * should return itself.
     * <p>
     * This view does support fragmenting.  It is implemented
     * to return a nested class that shares state in this view
     * representing only a portion of the view.
     *
     * <p>
     *  创建表示元素的一部分的视图。这在用于对视图的片段进行测量的格式化操作期间潜在地有用。如果视图不支持分段(默认),它应该返回自身。
     * <p>
     *  此视图支持碎片。它被实现为返回一个嵌套类,该类在此视图中共享状态,仅表示视图的一部分。
     * 
     * 
     * @param p0 the starting offset &gt;= 0.  This should be a value
     *   greater or equal to the element starting offset and
     *   less than the element ending offset.
     * @param p1 the ending offset &gt; p0.  This should be a value
     *   less than or equal to the elements end offset and
     *   greater than the elements starting offset.
     * @return the view fragment, or itself if the view doesn't
     *   support breaking into fragments
     * @see LabelView
     */
    public View createFragment(int p0, int p1) {
        checkPainter();
        Element elem = getElement();
        GlyphView v = (GlyphView) clone();
        v.offset = p0 - elem.getStartOffset();
        v.length = p1 - p0;
        v.painter = painter.getPainter(v, p0, p1);
        v.justificationInfo = null;
        return v;
    }

    /**
     * Provides a way to determine the next visually represented model
     * location that one might place a caret.  Some views may not be
     * visible, they might not be in the same order found in the model, or
     * they just might not allow access to some of the locations in the
     * model.
     * This method enables specifying a position to convert
     * within the range of &gt;=0.  If the value is -1, a position
     * will be calculated automatically.  If the value &lt; -1,
     * the {@code BadLocationException} will be thrown.
     *
     * <p>
     *  提供一种方法来确定下一个可视地表示的模型位置,人们可以放置插入符号。某些视图可能不可见,它们可能不是在模型中找到的相同顺序,或者它们可能不允许访问模型中的一些位置。
     * 该方法使得能够指定在> = 0的范围内转换的位置。如果值为-1,将自动计算位置。如果值&lt; -1,将抛出{@code BadLocationException}。
     * 
     * 
     * @param pos the position to convert
     * @param a the allocated region to render into
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard.
     *  This may be SwingConstants.WEST, SwingConstants.EAST,
     *  SwingConstants.NORTH, or SwingConstants.SOUTH.
     * @return the location within the model that best represents the next
     *  location visual position.
     * @exception BadLocationException the given position is not a valid
     *                                 position within the document
     * @exception IllegalArgumentException for an invalid direction
     */
    public int getNextVisualPositionFrom(int pos, Position.Bias b, Shape a,
                                         int direction,
                                         Position.Bias[] biasRet)
        throws BadLocationException {

        if (pos < -1) {
            throw new BadLocationException("invalid position", pos);
        }
        return painter.getNextVisualPositionFrom(this, pos, b, a, direction, biasRet);
    }

    /**
     * Gives notification that something was inserted into
     * the document in a location that this view is responsible for.
     * This is implemented to call preferenceChanged along the
     * axis the glyphs are rendered.
     *
     * <p>
     *  提供通知,说明在此数据视图负责的位置,文档中插入了某些内容。这是实现沿着轴调用preferenceChanged字形渲染。
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#insertUpdate
     */
    public void insertUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        justificationInfo = null;
        breakSpots = null;
        minimumSpan = -1;
        syncCR();
        preferenceChanged(null, true, false);
    }

    /**
     * Gives notification that something was removed from the document
     * in a location that this view is responsible for.
     * This is implemented to call preferenceChanged along the
     * axis the glyphs are rendered.
     *
     * <p>
     * 提供通知,说明该视图负责的位置中的文档被删除了。这是实现沿着轴调用preferenceChanged字形渲染。
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#removeUpdate
     */
    public void removeUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        justificationInfo = null;
        breakSpots = null;
        minimumSpan = -1;
        syncCR();
        preferenceChanged(null, true, false);
    }

    /**
     * Gives notification from the document that attributes were changed
     * in a location that this view is responsible for.
     * This is implemented to call preferenceChanged along both the
     * horizontal and vertical axis.
     *
     * <p>
     *  从文档中提供属性在此视图负责的位置中更改的通知。这被实现为沿着水平和垂直轴调用preferenceChanged。
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#changedUpdate
     */
    public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        minimumSpan = -1;
        syncCR();
        preferenceChanged(null, true, true);
    }

    // checks if the paragraph is empty and updates impliedCR flag
    // accordingly
    private void syncCR() {
        if (impliedCR) {
            Element parent = getElement().getParentElement();
            impliedCR = (parent != null && parent.getElementCount() > 1);
        }
    }

    /** {@inheritDoc} */
    @Override
    void updateAfterChange() {
        // Drop the break spots. They will be re-calculated during
        // layout. It is necessary for proper line break calculation.
        breakSpots = null;
    }

    /**
     * Class to hold data needed to justify this GlyphView in a PargraphView.Row
     * <p>
     *  在PargraphView.Row中保存需要证明此GlyphView的数据的类
     * 
     */
    static class JustificationInfo {
        //justifiable content start
        final int start;
        //justifiable content end
        final int end;
        final int leadingSpaces;
        final int contentSpaces;
        final int trailingSpaces;
        final boolean hasTab;
        final BitSet spaceMap;
        JustificationInfo(int start, int end,
                          int leadingSpaces,
                          int contentSpaces,
                          int trailingSpaces,
                          boolean hasTab,
                          BitSet spaceMap) {
            this.start = start;
            this.end = end;
            this.leadingSpaces = leadingSpaces;
            this.contentSpaces = contentSpaces;
            this.trailingSpaces = trailingSpaces;
            this.hasTab = hasTab;
            this.spaceMap = spaceMap;
        }
    }



    JustificationInfo getJustificationInfo(int rowStartOffset) {
        if (justificationInfo != null) {
            return justificationInfo;
        }
        //states for the parsing
        final int TRAILING = 0;
        final int CONTENT  = 1;
        final int SPACES   = 2;
        int startOffset = getStartOffset();
        int endOffset = getEndOffset();
        Segment segment = getText(startOffset, endOffset);
        int txtOffset = segment.offset;
        int txtEnd = segment.offset + segment.count - 1;
        int startContentPosition = txtEnd + 1;
        int endContentPosition = txtOffset - 1;
        int lastTabPosition = txtOffset - 1;
        int trailingSpaces = 0;
        int contentSpaces = 0;
        int leadingSpaces = 0;
        boolean hasTab = false;
        BitSet spaceMap = new BitSet(endOffset - startOffset + 1);

        //we parse conent to the right of the rightmost TAB only.
        //we are looking for the trailing and leading spaces.
        //position after the leading spaces (startContentPosition)
        //position before the trailing spaces (endContentPosition)
        for (int i = txtEnd, state = TRAILING; i >= txtOffset; i--) {
            if (' ' == segment.array[i]) {
                spaceMap.set(i - txtOffset);
                if (state == TRAILING) {
                    trailingSpaces++;
                } else if (state == CONTENT) {
                    state = SPACES;
                    leadingSpaces = 1;
                } else if (state == SPACES) {
                    leadingSpaces++;
                }
            } else if ('\t' == segment.array[i]) {
                hasTab = true;
                break;
            } else {
                if (state == TRAILING) {
                    if ('\n' != segment.array[i]
                          && '\r' != segment.array[i]) {
                        state = CONTENT;
                        endContentPosition = i;
                    }
                } else if (state == CONTENT) {
                    //do nothing
                } else if (state == SPACES) {
                    contentSpaces += leadingSpaces;
                    leadingSpaces = 0;
                }
                startContentPosition = i;
            }
        }

        SegmentCache.releaseSharedSegment(segment);

        int startJustifiableContent = -1;
        if (startContentPosition < txtEnd) {
            startJustifiableContent =
                startContentPosition - txtOffset;
        }
        int endJustifiableContent = -1;
        if (endContentPosition > txtOffset) {
            endJustifiableContent =
                endContentPosition - txtOffset;
        }
        justificationInfo =
            new JustificationInfo(startJustifiableContent,
                                  endJustifiableContent,
                                  leadingSpaces,
                                  contentSpaces,
                                  trailingSpaces,
                                  hasTab,
                                  spaceMap);
        return justificationInfo;
    }

    // --- variables ------------------------------------------------

    /**
    * Used by paint() to store highlighted view positions
    * <p>
    *  由paint()用于存储突出显示的视图位置
    * 
    */
    private byte[] selections = null;

    int offset;
    int length;
    // if it is an implied newline character
    boolean impliedCR;
    boolean skipWidth;

    /**
     * how to expand tabs
     * <p>
     *  如何展开标签页
     * 
     */
    TabExpander expander;

    /** Cached minimum x-span value  */
    private float minimumSpan = -1;

    /** Cached breakpoints within the view  */
    private int[] breakSpots = null;

    /**
     * location for determining tab expansion against.
     * <p>
     *  确定标签页展开的位置。
     * 
     */
    int x;

    /**
     * Glyph rendering functionality.
     * <p>
     *  字形渲染功能。
     * 
     */
    GlyphPainter painter;

    /**
     * The prototype painter used by default.
     * <p>
     *  默认使用的原型绘画。
     * 
     */
    static GlyphPainter defaultPainter;

    private JustificationInfo justificationInfo = null;

    /**
     * A class to perform rendering of the glyphs.
     * This can be implemented to be stateless, or
     * to hold some information as a cache to
     * facilitate faster rendering and model/view
     * translation.  At a minimum, the GlyphPainter
     * allows a View implementation to perform its
     * duties independant of a particular version
     * of JVM and selection of capabilities (i.e.
     * shaping for i18n, etc).
     *
     * <p>
     *  一个类来执行字形的渲染。这可以被实现为无状态的,或者保持一些信息作为高速缓存以促进更快的呈现和模型/视图转换。
     * 至少,GlyphPainter允许View实现执行其独立于JVM的特定版本和选择能力(即,针对i18n的整形等)的职责。
     * 
     * 
     * @since 1.3
     */
    public static abstract class GlyphPainter {

        /**
         * Determine the span the glyphs given a start location
         * (for tab expansion).
         * <p>
         *  确定给定开始位置的字形的跨度(用于制表符展开)。
         * 
         */
        public abstract float getSpan(GlyphView v, int p0, int p1, TabExpander e, float x);

        public abstract float getHeight(GlyphView v);

        public abstract float getAscent(GlyphView v);

        public abstract float getDescent(GlyphView v);

        /**
         * Paint the glyphs representing the given range.
         * <p>
         *  绘制表示给定范围的字形。
         * 
         */
        public abstract void paint(GlyphView v, Graphics g, Shape a, int p0, int p1);

        /**
         * Provides a mapping from the document model coordinate space
         * to the coordinate space of the view mapped to it.
         * This is shared by the broken views.
         *
         * <p>
         *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。这是由破碎的视图共享的。
         * 
         * 
         * @param v     the <code>GlyphView</code> containing the
         *              destination coordinate space
         * @param pos   the position to convert
         * @param bias  either <code>Position.Bias.Forward</code>
         *                  or <code>Position.Bias.Backward</code>
         * @param a     Bounds of the View
         * @return      the bounding box of the given position
         * @exception BadLocationException  if the given position does not represent a
         *   valid location in the associated document
         * @see View#modelToView
         */
        public abstract Shape modelToView(GlyphView v,
                                          int pos, Position.Bias bias,
                                          Shape a) throws BadLocationException;

        /**
         * Provides a mapping from the view coordinate space to the logical
         * coordinate space of the model.
         *
         * <p>
         *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
         * 
         * 
         * @param v          the <code>GlyphView</code> to provide a mapping for
         * @param x          the X coordinate
         * @param y          the Y coordinate
         * @param a          the allocated region to render into
         * @param biasReturn either <code>Position.Bias.Forward</code>
         *                   or <code>Position.Bias.Backward</code>
         *                   is returned as the zero-th element of this array
         * @return the location within the model that best represents the
         *         given point of view
         * @see View#viewToModel
         */
        public abstract int viewToModel(GlyphView v,
                                        float x, float y, Shape a,
                                        Position.Bias[] biasReturn);

        /**
         * Determines the model location that represents the
         * maximum advance that fits within the given span.
         * This could be used to break the given view.  The result
         * should be a location just shy of the given advance.  This
         * differs from viewToModel which returns the closest
         * position which might be proud of the maximum advance.
         *
         * <p>
         * 确定表示适合给定范围内的最大提前的模型位置。这可以用来打破给定的视图。结果应该是一个位置只是害羞的给定的提前。这不同于viewToModel,它返回可能引起最大提前量的最接近的位置。
         * 
         * 
         * @param v the view to find the model location to break at.
         * @param p0 the location in the model where the
         *  fragment should start it's representation &gt;= 0.
         * @param x  the graphic location along the axis that the
         *  broken view would occupy &gt;= 0.  This may be useful for
         *  things like tab calculations.
         * @param len specifies the distance into the view
         *  where a potential break is desired &gt;= 0.
         * @return the maximum model location possible for a break.
         * @see View#breakView
         */
        public abstract int getBoundedPosition(GlyphView v, int p0, float x, float len);

        /**
         * Create a painter to use for the given GlyphView.  If
         * the painter carries state it can create another painter
         * to represent a new GlyphView that is being created.  If
         * the painter doesn't hold any significant state, it can
         * return itself.  The default behavior is to return itself.
         * <p>
         *  创建一个画家用于给定的GlyphView。如果画家携带状态,它可以创建另一个画家来表示正在创建的新GlyphView。如果画家没有任何重要的状态,它可以返回自己。默认行为是返回自身。
         * 
         * 
         * @param v  the <code>GlyphView</code> to provide a painter for
         * @param p0 the starting document offset &gt;= 0
         * @param p1 the ending document offset &gt;= p0
         */
        public GlyphPainter getPainter(GlyphView v, int p0, int p1) {
            return this;
        }

        /**
         * Provides a way to determine the next visually represented model
         * location that one might place a caret.  Some views may not be
         * visible, they might not be in the same order found in the model, or
         * they just might not allow access to some of the locations in the
         * model.
         *
         * <p>
         *  提供一种方法来确定下一个可视地表示的模型位置,人们可以放置插入符号。某些视图可能不可见,它们可能不是在模型中找到的相同顺序,或者它们可能不允许访问模型中的一些位置。
         * 
         * @param v the view to use
         * @param pos the position to convert &gt;= 0
         * @param b   either <code>Position.Bias.Forward</code>
         *                or <code>Position.Bias.Backward</code>
         * @param a the allocated region to render into
         * @param direction the direction from the current position that can
         *  be thought of as the arrow keys typically found on a keyboard.
         *  This may be SwingConstants.WEST, SwingConstants.EAST,
         *  SwingConstants.NORTH, or SwingConstants.SOUTH.
         * @param biasRet  either <code>Position.Bias.Forward</code>
         *                 or <code>Position.Bias.Backward</code>
         *                 is returned as the zero-th element of this array
         * @return the location within the model that best represents the next
         *  location visual position.
         * @exception BadLocationException
         * @exception IllegalArgumentException for an invalid direction
         */
        public int getNextVisualPositionFrom(GlyphView v, int pos, Position.Bias b, Shape a,
                                             int direction,
                                             Position.Bias[] biasRet)
            throws BadLocationException {

            int startOffset = v.getStartOffset();
            int endOffset = v.getEndOffset();
            Segment text;

            switch (direction) {
            case View.NORTH:
            case View.SOUTH:
                if (pos != -1) {
                    // Presumably pos is between startOffset and endOffset,
                    // since GlyphView is only one line, we won't contain
                    // the position to the nort/south, therefore return -1.
                    return -1;
                }
                Container container = v.getContainer();

                if (container instanceof JTextComponent) {
                    Caret c = ((JTextComponent)container).getCaret();
                    Point magicPoint;
                    magicPoint = (c != null) ? c.getMagicCaretPosition() :null;

                    if (magicPoint == null) {
                        biasRet[0] = Position.Bias.Forward;
                        return startOffset;
                    }
                    int value = v.viewToModel(magicPoint.x, 0f, a, biasRet);
                    return value;
                }
                break;
            case View.EAST:
                if(startOffset == v.getDocument().getLength()) {
                    if(pos == -1) {
                        biasRet[0] = Position.Bias.Forward;
                        return startOffset;
                    }
                    // End case for bidi text where newline is at beginning
                    // of line.
                    return -1;
                }
                if(pos == -1) {
                    biasRet[0] = Position.Bias.Forward;
                    return startOffset;
                }
                if(pos == endOffset) {
                    return -1;
                }
                if(++pos == endOffset) {
                    // Assumed not used in bidi text, GlyphPainter2 will
                    // override as necessary, therefore return -1.
                    return -1;
                }
                else {
                    biasRet[0] = Position.Bias.Forward;
                }
                return pos;
            case View.WEST:
                if(startOffset == v.getDocument().getLength()) {
                    if(pos == -1) {
                        biasRet[0] = Position.Bias.Forward;
                        return startOffset;
                    }
                    // End case for bidi text where newline is at beginning
                    // of line.
                    return -1;
                }
                if(pos == -1) {
                    // Assumed not used in bidi text, GlyphPainter2 will
                    // override as necessary, therefore return -1.
                    biasRet[0] = Position.Bias.Forward;
                    return endOffset - 1;
                }
                if(pos == startOffset) {
                    return -1;
                }
                biasRet[0] = Position.Bias.Forward;
                return (pos - 1);
            default:
                throw new IllegalArgumentException("Bad direction: " + direction);
            }
            return pos;

        }
    }
}
