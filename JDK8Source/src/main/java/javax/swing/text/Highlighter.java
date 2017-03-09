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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

/**
 * An interface for an object that allows one to mark up the background
 * with colored areas.
 *
 * <p>
 *  一个对象的接口,允许用彩色区域标记背景。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface Highlighter {

    /**
     * Called when the UI is being installed into the
     * interface of a JTextComponent.  This can be used
     * to gain access to the model that is being navigated
     * by the implementation of this interface.
     *
     * <p>
     *  当UI安装到JTextComponent的接口时调用。这可以用于访问正在由该接口的实现导航的模型。
     * 
     * 
     * @param c the JTextComponent editor
     */
    public void install(JTextComponent c);

    /**
     * Called when the UI is being removed from the
     * interface of a JTextComponent.  This is used to
     * unregister any listeners that were attached.
     *
     * <p>
     *  当UI从JTextComponent的接口中删除时调用。这用于注销附加的任何侦听器。
     * 
     * 
     * @param c the JTextComponent editor
     */
    public void deinstall(JTextComponent c);

    /**
     * Renders the highlights.
     *
     * <p>
     *  呈现亮点。
     * 
     * 
     * @param g the graphics context.
     */
    public void paint(Graphics g);

    /**
     * Adds a highlight to the view.  Returns a tag that can be used
     * to refer to the highlight.
     *
     * <p>
     *  在视图中添加突出显示。返回可用于引用突出显示的标记。
     * 
     * 
     * @param p0 the beginning of the range &gt;= 0
     * @param p1 the end of the range &gt;= p0
     * @param p the painter to use for the actual highlighting
     * @return an object that refers to the highlight
     * @exception BadLocationException for an invalid range specification
     */
    public Object addHighlight(int p0, int p1, HighlightPainter p) throws BadLocationException;

    /**
     * Removes a highlight from the view.
     *
     * <p>
     *  从视图中删除突出显示。
     * 
     * 
     * @param tag  which highlight to remove
     */
    public void removeHighlight(Object tag);

    /**
     * Removes all highlights this highlighter is responsible for.
     * <p>
     *  删除此荧光笔负责的所有亮点。
     * 
     */
    public void removeAllHighlights();

    /**
     * Changes the given highlight to span a different portion of
     * the document.  This may be more efficient than a remove/add
     * when a selection is expanding/shrinking (such as a sweep
     * with a mouse) by damaging only what changed.
     *
     * <p>
     *  更改给定的突出显示以跨越文档的不同部分。这可能比通过仅损坏改变的选项正在扩展/收缩(例如用鼠标扫过)时的删除/添加更有效。
     * 
     * 
     * @param tag  which highlight to change
     * @param p0 the beginning of the range &gt;= 0
     * @param p1 the end of the range &gt;= p0
     * @exception BadLocationException for an invalid range specification
     */
    public void changeHighlight(Object tag, int p0, int p1) throws BadLocationException;

    /**
     * Fetches the current list of highlights.
     *
     * <p>
     *  获取当前的亮点列表。
     * 
     * 
     * @return the highlight list
     */
    public Highlight[] getHighlights();

    /**
     * Highlight renderer.
     * <p>
     *  突出显示渲染器。
     * 
     */
    public interface HighlightPainter {

        /**
         * Renders the highlight.
         *
         * <p>
         *  渲染突出显示。
         * 
         * 
         * @param g the graphics context
         * @param p0 the starting offset in the model &gt;= 0
         * @param p1 the ending offset in the model &gt;= p0
         * @param bounds the bounding box for the highlight
         * @param c the editor
         */
        public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c);

    }

    public interface Highlight {

        /**
         * Gets the starting model offset for the highlight.
         *
         * <p>
         *  获取突出显示的起始模型偏移。
         * 
         * 
         * @return the starting offset &gt;= 0
         */
        public int getStartOffset();

        /**
         * Gets the ending model offset for the highlight.
         *
         * <p>
         *  获取突出显示的结束模型偏移。
         * 
         * 
         * @return the ending offset &gt;= 0
         */
        public int getEndOffset();

        /**
         * Gets the painter for the highlighter.
         *
         * <p>
         *  获取荧光笔的画家。
         * 
         * @return the painter
         */
        public HighlightPainter getPainter();

    }

};
