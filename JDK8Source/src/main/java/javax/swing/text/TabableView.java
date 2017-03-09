/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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


/**
 * Interface for <code>View</code>s that have size dependent upon tabs.
 *
 * <p>
 *  <code> View </code>的界面,其大小取决于制表符。
 * 
 * 
 * @author  Timothy Prinzing
 * @author  Scott Violet
 * @see TabExpander
 * @see LabelView
 * @see ParagraphView
 */
public interface TabableView {

    /**
     * Determines the desired span when using the given
     * tab expansion implementation.  If a container
     * calls this method, it will do so prior to the
     * normal layout which would call getPreferredSpan.
     * A view implementing this should give the same
     * result in any subsequent calls to getPreferredSpan
     * along the axis of tab expansion.
     *
     * <p>
     *  当使用给定的选项卡扩展实现时,确定所需的跨度。如果一个容器调用这个方法,它将在正常布局之前调用getPreferredSpan。
     * 实现这一点的视图应该在任何随后的调用getPreferredSpan沿着选项卡扩展轴的相同结果。
     * 
     * 
     * @param x the position the view would be located
     *  at for the purpose of tab expansion &gt;= 0.
     * @param e how to expand the tabs when encountered.
     * @return the desired span &gt;= 0
     */
    float getTabbedSpan(float x, TabExpander e);

    /**
     * Determines the span along the same axis as tab
     * expansion for a portion of the view.  This is
     * intended for use by the TabExpander for cases
     * where the tab expansion involves aligning the
     * portion of text that doesn't have whitespace
     * relative to the tab stop.  There is therefore
     * an assumption that the range given does not
     * contain tabs.
     *
     * <p>
     * 
     * @param p0 the starting location in the text document &gt;= 0
     * @param p1 the ending location in the text document &gt;= p0
     * @return the span &gt;= 0
     */
    float getPartialSpan(int p0, int p1);
}
