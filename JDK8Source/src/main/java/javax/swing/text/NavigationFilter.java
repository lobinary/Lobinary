/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Shape;

/**
 * <code>NavigationFilter</code> can be used to restrict where the cursor can
 * be positioned. When the default cursor positioning actions attempt to
 * reposition the cursor they will call into the
 * <code>NavigationFilter</code>, assuming
 * the <code>JTextComponent</code> has a non-null
 * <code>NavigationFilter</code> set. In this manner
 * the <code>NavigationFilter</code> can effectively restrict where the
 * cursor can be positioned. Similarly <code>DefaultCaret</code> will call
 * into the <code>NavigationFilter</code> when the user is changing the
 * selection to further restrict where the cursor can be positioned.
 * <p>
 * Subclasses can conditionally call into supers implementation to restrict
 * where the cursor can be placed, or call directly into the
 * <code>FilterBypass</code>.
 *
 * <p>
 *  <code> NavigationFilter </code>可用于限制光标位置。
 * 当默认光标定位操作尝试重新定位光标时,假设<code> JTextComponent </code>具有非null <code> NavigationFilter </code>设置,它们将调用<code>
 *  NavigationFilter </code>。
 *  <code> NavigationFilter </code>可用于限制光标位置。以这种方式,<code> NavigationFilter </code>可以有效地限制光标可以定位的位置。
 * 类似地,当用户改变选择以进一步限制光标可以被定位时,<code> DefaultCaret </code>将调用<code> NavigationFilter </code>。
 * <p>
 *  子类可以有条件地调用超级实现来限制光标可放置的位置,或直接调用到<code> FilterBypass </code>中。
 * 
 * 
 * @see javax.swing.text.Caret
 * @see javax.swing.text.DefaultCaret
 * @see javax.swing.text.View
 *
 * @since 1.4
 */
public class NavigationFilter {
    /**
     * Invoked prior to the Caret setting the dot. The default implementation
     * calls directly into the <code>FilterBypass</code> with the passed
     * in arguments. Subclasses may wish to conditionally
     * call super with a different location, or invoke the necessary method
     * on the <code>FilterBypass</code>
     *
     * <p>
     *  在光标设置点之前调用。默认实现直接调用<code> FilterBypass </code>和传入的参数。
     * 子类可能希望有条件地使用不同的位置调用super,或调用<code> FilterBypass </code>上的必要方法,。
     * 
     * 
     * @param fb FilterBypass that can be used to mutate caret position
     * @param dot the position &gt;= 0
     * @param bias Bias to place the dot at
     */
    public void setDot(FilterBypass fb, int dot, Position.Bias bias) {
        fb.setDot(dot, bias);
    }

    /**
     * Invoked prior to the Caret moving the dot. The default implementation
     * calls directly into the <code>FilterBypass</code> with the passed
     * in arguments. Subclasses may wish to conditionally
     * call super with a different location, or invoke the necessary
     * methods on the <code>FilterBypass</code>.
     *
     * <p>
     *  在插入符移动点之前调用。默认实现直接调用<code> FilterBypass </code>和传入的参数。
     * 子类可能希望有条件地使用不同的位置调用super,或调用<code> FilterBypass </code>上必要的方法。
     * 
     * 
     * @param fb FilterBypass that can be used to mutate caret position
     * @param dot the position &gt;= 0
     * @param bias Bias for new location
     */
    public void moveDot(FilterBypass fb, int dot, Position.Bias bias) {
        fb.moveDot(dot, bias);
    }

    /**
     * Returns the next visual position to place the caret at from an
     * existing position. The default implementation simply forwards the
     * method to the root View. Subclasses may wish to further restrict the
     * location based on additional criteria.
     *
     * <p>
     * 返回下一个可视位置,以从现有位置放置插入符号。默认实现只是将该方法转发到根View。子类可能希望基于附加标准进一步限制位置。
     * 
     * 
     * @param text JTextComponent containing text
     * @param pos Position used in determining next position
     * @param bias Bias used in determining next position
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard.
     *  This will be one of the following values:
     * <ul>
     * <li>SwingConstants.WEST
     * <li>SwingConstants.EAST
     * <li>SwingConstants.NORTH
     * <li>SwingConstants.SOUTH
     * </ul>
     * @param biasRet Used to return resulting Bias of next position
     * @return the location within the model that best represents the next
     *  location visual position
     * @exception BadLocationException
     * @exception IllegalArgumentException if <code>direction</code>
     *          doesn't have one of the legal values above
     */
    public int getNextVisualPositionFrom(JTextComponent text, int pos,
                                         Position.Bias bias, int direction,
                                         Position.Bias[] biasRet)
                                           throws BadLocationException {
        return text.getUI().getNextVisualPositionFrom(text, pos, bias,
                                                      direction, biasRet);
    }


    /**
     * Used as a way to circumvent calling back into the caret to
     * position the cursor. Caret implementations that wish to support
     * a NavigationFilter must provide an implementation that will
     * not callback into the NavigationFilter.
     * <p>
     *  用作一种规避回调光标位置的插入符号的方法。希望支持NavigationFilter的插入点实现必须提供一个不会回调到NavigationFilter中的实现。
     * 
     * 
     * @since 1.4
     */
    public static abstract class FilterBypass {
        /**
         * Returns the Caret that is changing.
         *
         * <p>
         *  返回更改的插入符号。
         * 
         * 
         * @return Caret that is changing
         */
        public abstract Caret getCaret();

        /**
         * Sets the caret location, bypassing the NavigationFilter.
         *
         * <p>
         *  设置插入符位置,绕过NavigationFilter。
         * 
         * 
         * @param dot the position &gt;= 0
         * @param bias Bias to place the dot at
         */
        public abstract void setDot(int dot, Position.Bias bias);

        /**
         * Moves the caret location, bypassing the NavigationFilter.
         *
         * <p>
         *  移动插入符位置,绕过NavigationFilter。
         * 
         * @param dot the position &gt;= 0
         * @param bias Bias for new location
         */
        public abstract void moveDot(int dot, Position.Bias bias);
    }
}
