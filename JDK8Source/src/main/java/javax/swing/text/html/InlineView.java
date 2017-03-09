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
package javax.swing.text.html;

import java.awt.*;
import java.text.BreakIterator;
import javax.swing.event.DocumentEvent;
import javax.swing.text.*;

/**
 * Displays the <dfn>inline element</dfn> styles
 * based upon css attributes.
 *
 * <p>
 *  显示基于css属性的<dfn>内联元素</dfn>样式。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class InlineView extends LabelView {

    /**
     * Constructs a new view wrapped on an element.
     *
     * <p>
     *  构造包裹在元素上的新视图。
     * 
     * 
     * @param elem the element
     */
    public InlineView(Element elem) {
        super(elem);
        StyleSheet sheet = getStyleSheet();
        attr = sheet.getViewAttributes(this);
    }

    /**
     * Gives notification that something was inserted into
     * the document in a location that this view is responsible for.
     * If either parameter is <code>null</code>, behavior of this method is
     * implementation dependent.
     *
     * <p>
     *  提供通知,说明在此数据视图负责的位置,文档中插入了某些内容。如果任何一个参数是<code> null </code>,这个方法的行为是依赖于实现。
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @since 1.5
     * @see View#insertUpdate
     */
    public void insertUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        super.insertUpdate(e, a, f);
    }

    /**
     * Gives notification that something was removed from the document
     * in a location that this view is responsible for.
     * If either parameter is <code>null</code>, behavior of this method is
     * implementation dependent.
     *
     * <p>
     *  提供通知,说明该视图负责的位置中的文档被删除了。如果任一参数是<code> null </code>,这个方法的行为是依赖于实现。
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @since 1.5
     * @see View#removeUpdate
     */
    public void removeUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        super.removeUpdate(e, a, f);
    }

    /**
     * Gives notification from the document that attributes were changed
     * in a location that this view is responsible for.
     *
     * <p>
     *  从文档中提供属性在此视图负责的位置中更改的通知。
     * 
     * 
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @param f the factory to use to rebuild if the view has children
     * @see View#changedUpdate
     */
    public void changedUpdate(DocumentEvent e, Shape a, ViewFactory f) {
        super.changedUpdate(e, a, f);
        StyleSheet sheet = getStyleSheet();
        attr = sheet.getViewAttributes(this);
        preferenceChanged(null, true, true);
    }

    /**
     * Fetches the attributes to use when rendering.  This is
     * implemented to multiplex the attributes specified in the
     * model with a StyleSheet.
     * <p>
     *  获取渲染时要使用的属性。这被实现为将在模型中指定的属性与StyleSheet复用。
     * 
     */
    public AttributeSet getAttributes() {
        return attr;
    }

    /**
     * Determines how attractive a break opportunity in
     * this view is.  This can be used for determining which
     * view is the most attractive to call <code>breakView</code>
     * on in the process of formatting.  A view that represents
     * text that has whitespace in it might be more attractive
     * than a view that has no whitespace, for example.  The
     * higher the weight, the more attractive the break.  A
     * value equal to or lower than <code>BadBreakWeight</code>
     * should not be considered for a break.  A value greater
     * than or equal to <code>ForcedBreakWeight</code> should
     * be broken.
     * <p>
     * This is implemented to provide the default behavior
     * of returning <code>BadBreakWeight</code> unless the length
     * is greater than the length of the view in which case the
     * entire view represents the fragment.  Unless a view has
     * been written to support breaking behavior, it is not
     * attractive to try and break the view.  An example of
     * a view that does support breaking is <code>LabelView</code>.
     * An example of a view that uses break weight is
     * <code>ParagraphView</code>.
     *
     * <p>
     *  确定此视图中的休息机会的吸引力。这可以用于确定哪个视图是最有吸引力的在格式化过程中调用<code> breakView </code>。
     * 例如,表示其中具有空格的文本的视图可能比没有空格的视图更有吸引力。重量越高,断裂越有吸引力。等于或小于<code> BadBreakWeight </code>的值不应被视为中断。
     * 大于或等于<code> ForcedBreakWeight </code>的值应该被破坏。
     * <p>
     * 这被实现以提供返回<code> BadBreakWeight </code>的默认行为,除非长度大于视图的长度,在这种情况下整个视图表示片段。
     * 除非一个视图被写成支持破坏行为,尝试和破坏视图是没有吸引力的。支持断开的视图的示例是<code> LabelView </code>。
     * 使用break权重的视图的示例是<code> ParagraphView </code>。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @param pos the potential location of the start of the
     *   broken view &gt;= 0.  This may be useful for calculating tab
     *   positions.
     * @param len specifies the relative length from <em>pos</em>
     *   where a potential break is desired &gt;= 0.
     * @return the weight, which should be a value between
     *   ForcedBreakWeight and BadBreakWeight.
     * @see LabelView
     * @see ParagraphView
     * @see javax.swing.text.View#BadBreakWeight
     * @see javax.swing.text.View#GoodBreakWeight
     * @see javax.swing.text.View#ExcellentBreakWeight
     * @see javax.swing.text.View#ForcedBreakWeight
     */
    public int getBreakWeight(int axis, float pos, float len) {
        if (nowrap) {
            return BadBreakWeight;
        }
        return super.getBreakWeight(axis, pos, len);
    }

    /**
     * Tries to break this view on the given axis. Refer to
     * {@link javax.swing.text.View#breakView} for a complete
     * description of this method.
     * <p>Behavior of this method is unspecified in case <code>axis</code>
     * is neither <code>View.X_AXIS</code> nor <code>View.Y_AXIS</code>, and
     * in case <code>offset</code>, <code>pos</code>, or <code>len</code>
     * is null.
     *
     * <p>
     *  尝试在给定轴上断开此视图。有关此方法的完整说明,请参阅{@link javax.swing.text.View#breakView}。
     *  <p>如果<code> axis </code>既不是<code> View.X_AXIS </code>也不是<code> View.Y_AXIS </code>,以及<code> offset </code>
     * ,<code> pos </code>或<code> len </code>为空。
     *  尝试在给定轴上断开此视图。有关此方法的完整说明,请参阅{@link javax.swing.text.View#breakView}。
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @param offset the location in the document model
     *   that a broken fragment would occupy &gt;= 0.  This
     *   would be the starting offset of the fragment
     *   returned
     * @param pos the position along the axis that the
     *  broken view would occupy &gt;= 0.  This may be useful for
     *  things like tab calculations
     * @param len specifies the distance along the axis
     *  where a potential break is desired &gt;= 0
     * @return the fragment of the view that represents the
     *  given span.
     * @since 1.5
     * @see javax.swing.text.View#breakView
     */
    public View breakView(int axis, int offset, float pos, float len) {
        return super.breakView(axis, offset, pos, len);
    }


    /**
     * Set the cached properties from the attributes.
     * <p>
     * 
     */
    protected void setPropertiesFromAttributes() {
        super.setPropertiesFromAttributes();
        AttributeSet a = getAttributes();
        Object decor = a.getAttribute(CSS.Attribute.TEXT_DECORATION);
        boolean u = (decor != null) ?
          (decor.toString().indexOf("underline") >= 0) : false;
        setUnderline(u);
        boolean s = (decor != null) ?
          (decor.toString().indexOf("line-through") >= 0) : false;
        setStrikeThrough(s);
        Object vAlign = a.getAttribute(CSS.Attribute.VERTICAL_ALIGN);
        s = (vAlign != null) ? (vAlign.toString().indexOf("sup") >= 0) : false;
        setSuperscript(s);
        s = (vAlign != null) ? (vAlign.toString().indexOf("sub") >= 0) : false;
        setSubscript(s);

        Object whitespace = a.getAttribute(CSS.Attribute.WHITE_SPACE);
        if ((whitespace != null) && whitespace.equals("nowrap")) {
            nowrap = true;
        } else {
            nowrap = false;
        }

        HTMLDocument doc = (HTMLDocument)getDocument();
        // fetches background color from stylesheet if specified
        Color bg = doc.getBackground(a);
        if (bg != null) {
            setBackground(bg);
        }
    }


    protected StyleSheet getStyleSheet() {
        HTMLDocument doc = (HTMLDocument) getDocument();
        return doc.getStyleSheet();
    }

    private boolean nowrap;
    private AttributeSet attr;
}
