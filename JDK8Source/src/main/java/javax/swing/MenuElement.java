/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.*;
import java.awt.event.*;

/**
 * Any component that can be placed into a menu should implement this interface.
 * This interface is used by <code>MenuSelectionManager</code>
 * to handle selection and navigation in menu hierarchies.
 *
 * <p>
 *  可以放置到菜单中的任何组件都应实现此接口。此接口由<code> MenuSelectionManager </code>用于处理菜单层次结构中的选择和导航。
 * 
 * 
 * @author Arnaud Weber
 */

public interface MenuElement {

    /**
     * Processes a mouse event. <code>event</code> is a <code>MouseEvent</code>
     * with source being the receiving element's component.
     * <code>path</code> is the path of the receiving element in the menu
     * hierarchy including the receiving element itself.
     * <code>manager</code> is the <code>MenuSelectionManager</code>
     * for the menu hierarchy.
     * This method should process the <code>MouseEvent</code> and change
     * the menu selection if necessary
     * by using <code>MenuSelectionManager</code>'s API
     * Note: you do not have to forward the event to sub-components.
     * This is done automatically by the <code>MenuSelectionManager</code>.
     * <p>
     *  处理鼠标事件。 <code> event </code>是一个<code> MouseEvent </code>,其中source是接收元素的组件。
     *  <code> path </code>是包括接收元素本身的菜单层级中的接收元素的路径。
     *  <code> manager </code>是菜单层次结构的<code> MenuSelectionManager </code>。
     * 此方法应处理<code> MouseEvent </code>,并通过使用<code> MenuSelectionManager </code>的API更改菜单选择注意：您不必将事件转发到子组件。
     * 这是由<code> MenuSelectionManager </code>自动完成的。
     * 
     */
    public void processMouseEvent(MouseEvent event,MenuElement path[],MenuSelectionManager manager);


    /**
     *  Process a key event.
     * <p>
     *  处理关键事件。
     * 
     */
    public void processKeyEvent(KeyEvent event,MenuElement path[],MenuSelectionManager manager);

    /**
     * Call by the <code>MenuSelectionManager</code> when the
     * <code>MenuElement</code> is added or remove from
     * the menu selection.
     * <p>
     *  当添加或从菜单选择中删除<code> MenuElement </code>时,通过<code> MenuSelectionManager </code>调用。
     * 
     */
    public void menuSelectionChanged(boolean isIncluded);

    /**
     * This method should return an array containing the sub-elements for the receiving menu element
     *
     * <p>
     *  此方法应返回一个包含接收菜单元素的子元素的数组
     * 
     * 
     * @return an array of MenuElements
     */
    public MenuElement[] getSubElements();

    /**
     * This method should return the java.awt.Component used to paint the receiving element.
     * The returned component will be used to convert events and detect if an event is inside
     * a MenuElement's component.
     *
     * <p>
     *  此方法应返回用于绘制接收元素的java.awt.Component。返回的组件将用于转换事件并检测事件是否在MenuElement的组件内部。
     * 
     * @return the Component value
     */
    public Component getComponent();
}
