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

import sun.swing.SwingUtilities2;
import java.awt.*;
import javax.swing.JPasswordField;

/**
 * Implements a View suitable for use in JPasswordField
 * UI implementations.  This is basically a field ui that
 * renders its contents as the echo character specified
 * in the associated component (if it can narrow the
 * component to a JPasswordField).
 *
 * <p>
 *  实现适合在JPasswordField UI实现中使用的视图。这基本上是一个字段ui,它的内容作为在关联组件中指定的回显字符(如果它可以将组件缩小到一个JPasswordField)。
 * 
 * 
 * @author  Timothy Prinzing
 * @see     View
 */
public class PasswordView extends FieldView {

    /**
     * Constructs a new view wrapped on an element.
     *
     * <p>
     *  构造包裹在元素上的新视图。
     * 
     * 
     * @param elem the element
     */
    public PasswordView(Element elem) {
        super(elem);
    }

    /**
     * Renders the given range in the model as normal unselected
     * text.  This sets the foreground color and echos the characters
     * using the value returned by getEchoChar().
     *
     * <p>
     *  将模型中的给定范围渲染为正常未选择的文本。这将设置前景颜色并使用getEchoChar()返回的值回显字符。
     * 
     * 
     * @param g the graphics context
     * @param x the starting X coordinate &gt;= 0
     * @param y the starting Y coordinate &gt;= 0
     * @param p0 the starting offset in the model &gt;= 0
     * @param p1 the ending offset in the model &gt;= p0
     * @return the X location of the end of the range &gt;= 0
     * @exception BadLocationException if p0 or p1 are out of range
     */
    protected int drawUnselectedText(Graphics g, int x, int y,
                                     int p0, int p1) throws BadLocationException {

        Container c = getContainer();
        if (c instanceof JPasswordField) {
            JPasswordField f = (JPasswordField) c;
            if (! f.echoCharIsSet()) {
                return super.drawUnselectedText(g, x, y, p0, p1);
            }
            if (f.isEnabled()) {
                g.setColor(f.getForeground());
            }
            else {
                g.setColor(f.getDisabledTextColor());
            }
            char echoChar = f.getEchoChar();
            int n = p1 - p0;
            for (int i = 0; i < n; i++) {
                x = drawEchoCharacter(g, x, y, echoChar);
            }
        }
        return x;
    }

    /**
     * Renders the given range in the model as selected text.  This
     * is implemented to render the text in the color specified in
     * the hosting component.  It assumes the highlighter will render
     * the selected background.  Uses the result of getEchoChar() to
     * display the characters.
     *
     * <p>
     *  将模型中的给定范围渲染为选定文本。这被实现来渲染在托管组件中指定的颜色的文本。它假定荧光笔将呈现所选择的背景。使用getEchoChar()的结果显示字符。
     * 
     * 
     * @param g the graphics context
     * @param x the starting X coordinate &gt;= 0
     * @param y the starting Y coordinate &gt;= 0
     * @param p0 the starting offset in the model &gt;= 0
     * @param p1 the ending offset in the model &gt;= p0
     * @return the X location of the end of the range &gt;= 0
     * @exception BadLocationException if p0 or p1 are out of range
     */
    protected int drawSelectedText(Graphics g, int x,
                                   int y, int p0, int p1) throws BadLocationException {
        g.setColor(selected);
        Container c = getContainer();
        if (c instanceof JPasswordField) {
            JPasswordField f = (JPasswordField) c;
            if (! f.echoCharIsSet()) {
                return super.drawSelectedText(g, x, y, p0, p1);
            }
            char echoChar = f.getEchoChar();
            int n = p1 - p0;
            for (int i = 0; i < n; i++) {
                x = drawEchoCharacter(g, x, y, echoChar);
            }
        }
        return x;
    }

    /**
     * Renders the echo character, or whatever graphic should be used
     * to display the password characters.  The color in the Graphics
     * object is set to the appropriate foreground color for selected
     * or unselected text.
     *
     * <p>
     *  呈现回显字符,或用于显示密码字符的任何图形。 Graphics对象中的颜色设置为所选或未选择的文本的相应前景颜色。
     * 
     * 
     * @param g the graphics context
     * @param x the starting X coordinate &gt;= 0
     * @param y the starting Y coordinate &gt;= 0
     * @param c the echo character
     * @return the updated X position &gt;= 0
     */
    protected int drawEchoCharacter(Graphics g, int x, int y, char c) {
        ONE[0] = c;
        SwingUtilities2.drawChars(Utilities.getJComponent(this),
                                  g, ONE, 0, 1, x, y);
        return x + g.getFontMetrics().charWidth(c);
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
        Container c = getContainer();
        if (c instanceof JPasswordField) {
            JPasswordField f = (JPasswordField) c;
            if (! f.echoCharIsSet()) {
                return super.modelToView(pos, a, b);
            }
            char echoChar = f.getEchoChar();
            FontMetrics m = f.getFontMetrics(f.getFont());

            Rectangle alloc = adjustAllocation(a).getBounds();
            int dx = (pos - getStartOffset()) * m.charWidth(echoChar);
            alloc.x += dx;
            alloc.width = 1;
            return alloc;
        }
        return null;
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * 
     * @param fx the X coordinate &gt;= 0.0f
     * @param fy the Y coordinate &gt;= 0.0f
     * @param a the allocated region to render into
     * @return the location within the model that best represents the
     *  given point in the view
     * @see View#viewToModel
     */
    public int viewToModel(float fx, float fy, Shape a, Position.Bias[] bias) {
        bias[0] = Position.Bias.Forward;
        int n = 0;
        Container c = getContainer();
        if (c instanceof JPasswordField) {
            JPasswordField f = (JPasswordField) c;
            if (! f.echoCharIsSet()) {
                return super.viewToModel(fx, fy, a, bias);
            }
            char echoChar = f.getEchoChar();
            int charWidth = f.getFontMetrics(f.getFont()).charWidth(echoChar);
            a = adjustAllocation(a);
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle)a :
                              a.getBounds();
            n = (charWidth > 0 ?
                 ((int)fx - alloc.x) / charWidth : Integer.MAX_VALUE);
            if (n < 0) {
                n = 0;
            }
            else if (n > (getStartOffset() + getDocument().getLength())) {
                n = getDocument().getLength() - getStartOffset();
            }
        }
        return getStartOffset() + n;
    }

    /**
     * Determines the preferred span for this view along an
     * axis.
     *
     * <p>
     *  确定沿着轴的此视图的首选跨度。
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;= 0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     */
    public float getPreferredSpan(int axis) {
        switch (axis) {
        case View.X_AXIS:
            Container c = getContainer();
            if (c instanceof JPasswordField) {
                JPasswordField f = (JPasswordField) c;
                if (f.echoCharIsSet()) {
                    char echoChar = f.getEchoChar();
                    FontMetrics m = f.getFontMetrics(f.getFont());
                    Document doc = getDocument();
                    return m.charWidth(echoChar) * getDocument().getLength();
                }
            }
        }
        return super.getPreferredSpan(axis);
    }

    static char[] ONE = new char[1];
}
