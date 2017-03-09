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
package javax.swing.plaf;

import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Insets;
import javax.swing.text.*;

/**
 * Text editor user interface
 *
 * <p>
 *  文本编辑器用户界面
 * 
 * 
 * @author  Timothy Prinzing
 */
public abstract class TextUI extends ComponentUI
{
    /**
     * Converts the given location in the model to a place in
     * the view coordinate system.
     *
     * <p>
     *  将模型中的给定位置转换为视图坐标系中的位置。
     * 
     * 
     * @param pos  the local location in the model to translate &gt;= 0
     * @return the coordinates as a rectangle
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document
     */
    public abstract Rectangle modelToView(JTextComponent t, int pos) throws BadLocationException;

    /**
     * Converts the given location in the model to a place in
     * the view coordinate system.
     *
     * <p>
     *  将模型中的给定位置转换为视图坐标系中的位置。
     * 
     * 
     * @param pos  the local location in the model to translate &gt;= 0
     * @return the coordinates as a rectangle
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document
     */
    public abstract Rectangle modelToView(JTextComponent t, int pos, Position.Bias bias) throws BadLocationException;

    /**
     * Converts the given place in the view coordinate system
     * to the nearest representative location in the model.
     *
     * <p>
     *  将视图坐标系中的给定位置转换为模型中最近的代表位置。
     * 
     * 
     * @param pt  the location in the view to translate.  This
     *   should be in the same coordinate system as the mouse
     *   events.
     * @return the offset from the start of the document &gt;= 0
     */
    public abstract int viewToModel(JTextComponent t, Point pt);

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。
     * 
     * 
     * @param pt the location in the view to translate.
     *           This should be in the same coordinate system
     *           as the mouse events.
     * @param biasReturn
     *           filled in by this method to indicate whether
     *           the point given is closer to the previous or the next
     *           character in the model
     *
     * @return the location within the model that best represents the
     *         given point in the view &gt;= 0
     */
    public abstract int viewToModel(JTextComponent t, Point pt,
                                    Position.Bias[] biasReturn);

    /**
     * Provides a way to determine the next visually represented model
     * location that one might place a caret.  Some views may not be visible,
     * they might not be in the same order found in the model, or they just
     * might not allow access to some of the locations in the model.
     *
     * <p>
     *  提供一种方法来确定下一个可视地表示的模型位置,人们可以放置插入符号。某些视图可能不可见,它们可能不是在模型中找到的相同顺序,或者它们可能不允许访问模型中的一些位置。
     * 
     * 
     * @param t the text component for which this UI is installed
     * @param pos the position to convert &gt;= 0
     * @param b the bias for the position
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard.
     *  This may be SwingConstants.WEST, SwingConstants.EAST,
     *  SwingConstants.NORTH, or SwingConstants.SOUTH
     * @param biasRet an array to contain the bias for the returned position
     * @return the location within the model that best represents the next
     *  location visual position
     * @exception BadLocationException
     * @exception IllegalArgumentException for an invalid direction
     */
    public abstract int getNextVisualPositionFrom(JTextComponent t,
                         int pos, Position.Bias b,
                         int direction, Position.Bias[] biasRet)
                         throws BadLocationException;

    /**
     * Causes the portion of the view responsible for the
     * given part of the model to be repainted.
     *
     * <p>
     *  导致对模型的给定部分负责的部分重新绘制。
     * 
     * 
     * @param p0 the beginning of the range &gt;= 0
     * @param p1 the end of the range &gt;= p0
     */
    public abstract void damageRange(JTextComponent t, int p0, int p1);

    /**
     * Causes the portion of the view responsible for the
     * given part of the model to be repainted.
     *
     * <p>
     *  导致对模型的给定部分负责的部分重新绘制。
     * 
     * 
     * @param p0 the beginning of the range &gt;= 0
     * @param p1 the end of the range &gt;= p0
     */
    public abstract void damageRange(JTextComponent t, int p0, int p1,
                                     Position.Bias firstBias,
                                     Position.Bias secondBias);

    /**
     * Fetches the binding of services that set a policy
     * for the type of document being edited.  This contains
     * things like the commands available, stream readers and
     * writers, etc.
     *
     * <p>
     *  获取为正在编辑的文档类型设置策略的服务绑定。它包含可用的命令,流读取器和写入器等。
     * 
     * 
     * @return the editor kit binding
     */
    public abstract EditorKit getEditorKit(JTextComponent t);

    /**
     * Fetches a View with the allocation of the associated
     * text component (i.e. the root of the hierarchy) that
     * can be traversed to determine how the model is being
     * represented spatially.
     *
     * <p>
     *  使用可以被遍历的相关联文本分量(即,分层的根)的分配来获取视图,以确定如何在空间上表示模型。
     * 
     * 
     * @return the view
     */
    public abstract View getRootView(JTextComponent t);

    /**
     * Returns the string to be used as the tooltip at the passed in location.
     *
     * <p>
     *  返回要在传入的位置处用作工具提示的字符串。
     * 
     * @see javax.swing.text.JTextComponent#getToolTipText
     * @since 1.4
     */
    public String getToolTipText(JTextComponent t, Point pt) {
        return null;
    }
}
