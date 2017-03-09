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

package javax.swing.plaf.basic;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import javax.swing.JList;


/**
 * The interface which defines the methods required for the implementation of the popup
 * portion of a combo box.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  定义组合框的弹出部分的实现所需的方法的接口。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Tom Santos
 */
public interface ComboPopup {
    /**
     * Shows the popup
     * <p>
     *  显示弹出窗口
     * 
     */
    public void show();

    /**
     * Hides the popup
     * <p>
     *  隐藏弹出窗口
     * 
     */
    public void hide();

    /**
     * Returns true if the popup is visible (currently being displayed).
     *
     * <p>
     *  如果弹出窗口是可见的(当前正在显示),则返回true。
     * 
     * 
     * @return <code>true</code> if the component is visible; <code>false</code> otherwise.
     */
    public boolean isVisible();

    /**
     * Returns the list that is being used to draw the items in the combo box.
     * This method is highly implementation specific and should not be used
     * for general list manipulation.
     * <p>
     *  返回用于在组合框中绘制项目的列表。此方法高度实现特定,不应用于一般列表操作。
     * 
     */
    public JList getList();

    /**
     * Returns a mouse listener that will be added to the combo box or null.
     * If this method returns null then it will not be added to the combo box.
     *
     * <p>
     *  返回将添加到组合框或null的鼠标监听器。如果此方法返回null,那么它不会被添加到组合框。
     * 
     * 
     * @return a <code>MouseListener</code> or null
     */
    public MouseListener getMouseListener();

    /**
     * Returns a mouse motion listener that will be added to the combo box or null.
     * If this method returns null then it will not be added to the combo box.
     *
     * <p>
     *  返回将添加到组合框或null的鼠标移动侦听器。如果此方法返回null,那么它不会被添加到组合框。
     * 
     * 
     * @return a <code>MouseMotionListener</code> or null
     */
    public MouseMotionListener getMouseMotionListener();

    /**
     * Returns a key listener that will be added to the combo box or null.
     * If this method returns null then it will not be added to the combo box.
     * <p>
     *  返回将添加到组合框或null的键监听器。如果此方法返回null,那么它不会被添加到组合框。
     * 
     */
    public KeyListener getKeyListener();

    /**
     * Called to inform the ComboPopup that the UI is uninstalling.
     * If the ComboPopup added any listeners in the component, it should remove them here.
     * <p>
     * 调用通知ComboPopup UI正在卸载。如果ComboPopup在组件中添加了任何监听器,它应该在这里删除它们。
     */
    public void uninstallingUI();
}
