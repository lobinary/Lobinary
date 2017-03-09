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

package javax.swing;


/**
 * Constants used with the JScrollPane component.
 *
 * <p>
 *  与JScrollPane组件一起使用的常量。
 * 
 * 
 * @author Hans Muller
 */
public interface ScrollPaneConstants
{
    /**
     * Identifies a "viewport" or display area, within which
     * scrolled contents are visible.
     * <p>
     *  标识"视口"或显示区域,滚动内容在其中可见。
     * 
     */
    String VIEWPORT = "VIEWPORT";
    /** Identifies a vertical scrollbar. */
    String VERTICAL_SCROLLBAR = "VERTICAL_SCROLLBAR";
    /** Identifies a horizontal scrollbar. */
    String HORIZONTAL_SCROLLBAR = "HORIZONTAL_SCROLLBAR";
    /**
     * Identifies the area along the left side of the viewport between the
     * upper left corner and the lower left corner.
     * <p>
     *  标识视口左上角和左下角之间沿着左侧的区域。
     * 
     */
    String ROW_HEADER = "ROW_HEADER";
    /**
     * Identifies the area at the top the viewport between the
     * upper left corner and the upper right corner.
     * <p>
     *  标识视口左上角和右上角之间顶部的区域。
     * 
     */
    String COLUMN_HEADER = "COLUMN_HEADER";
    /** Identifies the lower left corner of the viewport. */
    String LOWER_LEFT_CORNER = "LOWER_LEFT_CORNER";
    /** Identifies the lower right corner of the viewport. */
    String LOWER_RIGHT_CORNER = "LOWER_RIGHT_CORNER";
    /** Identifies the upper left corner of the viewport. */
    String UPPER_LEFT_CORNER = "UPPER_LEFT_CORNER";
    /** Identifies the upper right corner of the viewport. */
    String UPPER_RIGHT_CORNER = "UPPER_RIGHT_CORNER";

    /** Identifies the lower leading edge corner of the viewport. The leading edge
     * is determined relative to the Scroll Pane's ComponentOrientation property.
     * <p>
     *  是相对于滚动窗格的ComponentOrientation属性确定的。
     * 
     */
    String LOWER_LEADING_CORNER = "LOWER_LEADING_CORNER";
    /** Identifies the lower trailing edge corner of the viewport. The trailing edge
     * is determined relative to the Scroll Pane's ComponentOrientation property.
     * <p>
     *  是相对于滚动窗格的ComponentOrientation属性确定的。
     * 
     */
    String LOWER_TRAILING_CORNER = "LOWER_TRAILING_CORNER";
    /** Identifies the upper leading edge corner of the viewport.  The leading edge
     * is determined relative to the Scroll Pane's ComponentOrientation property.
     * <p>
     *  是相对于滚动窗格的ComponentOrientation属性确定的。
     * 
     */
    String UPPER_LEADING_CORNER = "UPPER_LEADING_CORNER";
    /** Identifies the upper trailing edge corner of the viewport. The trailing edge
     * is determined relative to the Scroll Pane's ComponentOrientation property.
     * <p>
     *  是相对于滚动窗格的ComponentOrientation属性确定的。
     * 
     */
    String UPPER_TRAILING_CORNER = "UPPER_TRAILING_CORNER";

    /** Identifies the vertical scroll bar policy property. */
    String VERTICAL_SCROLLBAR_POLICY = "VERTICAL_SCROLLBAR_POLICY";
    /** Identifies the horizontal scroll bar policy property. */
    String HORIZONTAL_SCROLLBAR_POLICY = "HORIZONTAL_SCROLLBAR_POLICY";

    /**
     * Used to set the vertical scroll bar policy so that
     * vertical scrollbars are displayed only when needed.
     * <p>
     *  用于设置垂直滚动条策略,以便只在需要时才显示垂直滚动条。
     * 
     */
    int VERTICAL_SCROLLBAR_AS_NEEDED = 20;
    /**
     * Used to set the vertical scroll bar policy so that
     * vertical scrollbars are never displayed.
     * <p>
     *  用于设置垂直滚动条策略,以便不显示垂直滚动条。
     * 
     */
    int VERTICAL_SCROLLBAR_NEVER = 21;
    /**
     * Used to set the vertical scroll bar policy so that
     * vertical scrollbars are always displayed.
     * <p>
     *  用于设置垂直滚动条策略,以便始终显示垂直滚动条。
     * 
     */
    int VERTICAL_SCROLLBAR_ALWAYS = 22;

    /**
     * Used to set the horizontal scroll bar policy so that
     * horizontal scrollbars are displayed only when needed.
     * <p>
     *  用于设置水平滚动条策略,以便只在需要时才显示水平滚动条。
     * 
     */
    int HORIZONTAL_SCROLLBAR_AS_NEEDED = 30;
    /**
     * Used to set the horizontal scroll bar policy so that
     * horizontal scrollbars are never displayed.
     * <p>
     *  用于设置水平滚动条策略,以便不显示水平滚动条。
     * 
     */
    int HORIZONTAL_SCROLLBAR_NEVER = 31;
    /**
     * Used to set the horizontal scroll bar policy so that
     * horizontal scrollbars are always displayed.
     * <p>
     *  用于设置水平滚动条策略,以便始终显示水平滚动条。
     */
    int HORIZONTAL_SCROLLBAR_ALWAYS = 32;
}
