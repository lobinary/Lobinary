/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text.html;

import javax.swing.text.*;

/**
 * Processes the &lt;BR&gt; tag.  In other words, forces a line break.
 *
 * <p>
 *  处理&lt; BR&gt;标签。换句话说,强制换行。
 * 
 * 
 * @author Sunita Mani
 */
class BRView extends InlineView {

    /**
     * Creates a new view that represents a &lt;BR&gt; element.
     *
     * <p>
     *  创建代表&lt; BR&gt;的新视图。元件。
     * 
     * 
     * @param elem the element to create a view for
     */
    public BRView(Element elem) {
        super(elem);
    }

    /**
     * Forces a line break.
     *
     * <p>
     *  强制换行。
     * 
     * @return View.ForcedBreakWeight
     */
    public int getBreakWeight(int axis, float pos, float len) {
        if (axis == X_AXIS) {
            return ForcedBreakWeight;
        } else {
            return super.getBreakWeight(axis, pos, len);
        }
    }
}
