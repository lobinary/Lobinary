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

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.Action;
import javax.swing.event.ChangeListener;

/**
 * A place within a document view that represents where
 * things can be inserted into the document model.  A caret
 * has a position in the document referred to as a dot.
 * The dot is where the caret is currently located in the
 * model.  There is
 * a second position maintained by the caret that represents
 * the other end of a selection called mark.  If there is
 * no selection the dot and mark will be equal.  If a selection
 * exists, the two values will be different.
 * <p>
 * The dot can be placed by either calling
 * <code>setDot</code> or <code>moveDot</code>.  Setting
 * the dot has the effect of removing any selection that may
 * have previously existed.  The dot and mark will be equal.
 * Moving the dot has the effect of creating a selection as
 * the mark is left at whatever position it previously had.
 *
 * <p>
 *  文档视图中的一个位置,表示可以将事物插入到文档模型中的位置。插入符在文档中具有称为点的位置。点是插入符当前位于模型中的位置。有一个由插入符号维护​​的第二个位置,它代表称为标记的选择的另一端。
 * 如果没有选择,点和标记将相等。如果存在选择,则两个值将不同。
 * <p>
 *  点可以通过调用<code> setDot </code>或<code> moveDot </code>来放置。设置点有删除以前可能存在的任何选择的效果。点和标记将相等。
 * 移动点具有创建选择的效果,因为标记留在其先前具有的任何位置。
 * 
 * 
 * @author  Timothy Prinzing
 */
public interface Caret {

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
     * @param c the JTextComponent
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
     * @param c the JTextComponent
     */
    public void deinstall(JTextComponent c);

    /**
     * Renders the caret. This method is called by UI classes.
     *
     * <p>
     *  渲染插入符号。此方法由UI类调用。
     * 
     * 
     * @param g the graphics context
     */
    public void paint(Graphics g);

    /**
     * Adds a listener to track whenever the caret position
     * has been changed.
     *
     * <p>
     *  添加监听器,以便在每次更改插入符位置时进行跟踪。
     * 
     * 
     * @param l the change listener
     */
    public void addChangeListener(ChangeListener l);

    /**
     * Removes a listener that was tracking caret position changes.
     *
     * <p>
     *  删除跟踪插入符位置更改的侦听器。
     * 
     * 
     * @param l the change listener
     */
    public void removeChangeListener(ChangeListener l);

    /**
     * Determines if the caret is currently visible.
     *
     * <p>
     *  确定插入符号当前是否可见。
     * 
     * 
     * @return true if the caret is visible else false
     */
    public boolean isVisible();

    /**
     * Sets the visibility of the caret.
     *
     * <p>
     * 设置插入符的可见性。
     * 
     * 
     * @param v  true if the caret should be shown,
     *  and false if the caret should be hidden
     */
    public void setVisible(boolean v);

    /**
     * Determines if the selection is currently visible.
     *
     * <p>
     *  确定选择是否当前可见。
     * 
     * 
     * @return true if the caret is visible else false
     */
    public boolean isSelectionVisible();

    /**
     * Sets the visibility of the selection
     *
     * <p>
     *  设置选择的可见性
     * 
     * 
     * @param v  true if the caret should be shown,
     *  and false if the caret should be hidden
     */
    public void setSelectionVisible(boolean v);

    /**
     * Set the current caret visual location.  This can be used when
     * moving between lines that have uneven end positions (such as
     * when caret up or down actions occur).  If text flows
     * left-to-right or right-to-left the x-coordinate will indicate
     * the desired navigation location for vertical movement.  If
     * the text flow is top-to-bottom, the y-coordinate will indicate
     * the desired navigation location for horizontal movement.
     *
     * <p>
     *  设置当前插入符的可视位置。当在具有不平的末端位置的线之间移动时(例如当发生插入符号或下移动时),可以使用此选项。如果文本从左到右或从右到左流动,则x坐标将指示用于垂直移动的期望导航位置。
     * 如果文本流是从上到下,则y坐标将指示用于水平移动的期望导航位置。
     * 
     * 
     * @param p  the Point to use for the saved position.  This
     *   can be null to indicate there is no visual location.
     */
    public void setMagicCaretPosition(Point p);

    /**
     * Gets the current caret visual location.
     *
     * <p>
     *  获取当前插入符视觉位置。
     * 
     * 
     * @return the visual position.
     * @see #setMagicCaretPosition
     */
    public Point getMagicCaretPosition();

    /**
     * Sets the blink rate of the caret.  This determines if
     * and how fast the caret blinks, commonly used as one
     * way to attract attention to the caret.
     *
     * <p>
     *  设置插入符号的闪烁速率。这决定了插入符是否以及快速闪烁,通常用作一种吸引注意插入符号的方法。
     * 
     * 
     * @param rate  the delay in milliseconds &gt;=0.  If this is
     *  zero the caret will not blink.
     */
    public void setBlinkRate(int rate);

    /**
     * Gets the blink rate of the caret.  This determines if
     * and how fast the caret blinks, commonly used as one
     * way to attract attention to the caret.
     *
     * <p>
     *  获取插入符号的闪烁速率。这决定了插入符是否以及快速闪烁,通常用作一种吸引注意插入符号的方法。
     * 
     * 
     * @return the delay in milliseconds &gt;=0.  If this is
     *  zero the caret will not blink.
     */
    public int getBlinkRate();

    /**
     * Fetches the current position of the caret.
     *
     * <p>
     *  获取插入符号的当前位置。
     * 
     * 
     * @return the position &gt;=0
     */
    public int getDot();

    /**
     * Fetches the current position of the mark.  If there
     * is a selection, the mark will not be the same as
     * the dot.
     *
     * <p>
     *  获取标记的当前位置。如果有选择,标记将不会与点相同。
     * 
     * 
     * @return the position &gt;=0
     */
    public int getMark();

    /**
     * Sets the caret position to some position.  This
     * causes the mark to become the same as the dot,
     * effectively setting the selection range to zero.
     * <p>
     * If the parameter is negative or beyond the length of the document,
     * the caret is placed at the beginning or at the end, respectively.
     *
     * <p>
     *  将插入符号位置设置为某个位置。这使得标记变得与点相同,有效地将选择范围设置为零。
     * <p>
     *  如果参数是负数或超过文档的长度,则插入符号分别放在开始或结尾。
     * 
     * 
     * @param dot  the new position to set the caret to
     */
    public void setDot(int dot);

    /**
     * Moves the caret position (dot) to some other position,
     * leaving behind the mark.  This is useful for
     * making selections.
     *
     * <p>
     * 
     * @param dot  the new position to move the caret to &gt;=0
     */
    public void moveDot(int dot);

};
