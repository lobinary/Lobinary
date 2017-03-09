/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.dnd;

/**
 * An abstract adapter class for receiving drop target events. The methods in
 * this class are empty. This class exists only as a convenience for creating
 * listener objects.
 * <p>
 * Extend this class to create a <code>DropTargetEvent</code> listener
 * and override the methods for the events of interest. (If you implement the
 * <code>DropTargetListener</code> interface, you have to define all of
 * the methods in it. This abstract class defines a null implementation for
 * every method except <code>drop(DropTargetDropEvent)</code>, so you only have
 * to define methods for events you care about.) You must provide an
 * implementation for at least <code>drop(DropTargetDropEvent)</code>. This
 * method cannot have a null implementation because its specification requires
 * that you either accept or reject the drop, and, if accepted, indicate
 * whether the drop was successful.
 * <p>
 * Create a listener object using the extended class and then register it with
 * a <code>DropTarget</code>. When the drag enters, moves over, or exits
 * the operable part of the drop site for that <code>DropTarget</code>, when
 * the drop action changes, and when the drop occurs, the relevant method in
 * the listener object is invoked, and the <code>DropTargetEvent</code> is
 * passed to it.
 * <p>
 * The operable part of the drop site for the <code>DropTarget</code> is
 * the part of the associated <code>Component</code>'s geometry that is not
 * obscured by an overlapping top-level window or by another
 * <code>Component</code> higher in the Z-order that has an associated active
 * <code>DropTarget</code>.
 * <p>
 * During the drag, the data associated with the current drag operation can be
 * retrieved by calling <code>getTransferable()</code> on
 * <code>DropTargetDragEvent</code> instances passed to the listener's
 * methods.
 * <p>
 * Note that <code>getTransferable()</code> on the
 * <code>DropTargetDragEvent</code> instance should only be called within the
 * respective listener's method and all the necessary data should be retrieved
 * from the returned <code>Transferable</code> before that method returns.
 *
 * <p>
 *  用于接收放置目标事件的抽象适配器类。此类中的方法为空。此类仅作为创建侦听器对象的方便存在。
 * <p>
 *  扩展这个类来创建一个<code> DropTargetEvent </code>监听器并覆盖感兴趣的事件的方法。
 *  (如果你实现<code> DropTargetListener </code>接口,你必须定义所有的方法。
 * 这个抽象类定义一个null实现除了<code> drop(DropTargetDropEvent)</code>你只需要为你关心的事件定义方法。
 * )你必须提供至少<code> drop(DropTargetDropEvent)</code>的实现。此方法不能有null实现,因为其规范要求您接受或拒绝该删除,如果接受,则指示删除是否成功。
 * <p>
 *  使用扩展类创建一个监听器对象,然后使用<code> DropTarget </code>注册它。
 * 当拖动操作改变时,拖动进入,移动或退出对于<code> DropTarget </code>的拖放站点的可操作部分,并且当发生拖放时,调用侦听器对象中的相关方法,并将<code> DropTargetE
 * vent </code>传递给它。
 *  使用扩展类创建一个监听器对象,然后使用<code> DropTarget </code>注册它。
 * <p>
 * <code> DropTarget </code>的放置站点的可操作部分是关联的<code> Component </code>的几何的一部分,它不被重叠的顶级窗口或另一个<代码>组件</code>在具
 * 有相关联的活动<code> DropTarget </code>的Z顺序中更高。
 * <p>
 *  在拖动期间,可以通过在传递到侦听器方法的<code> DropTargetDragEvent </code>实例上调用<code> getTransferable()</code>来检索与当前拖动操作
 * 相关联的数据。
 * 
 * @see DropTargetEvent
 * @see DropTargetListener
 *
 * @author David Mendenhall
 * @since 1.4
 */
public abstract class DropTargetAdapter implements DropTargetListener {

    /**
     * Called while a drag operation is ongoing, when the mouse pointer enters
     * the operable part of the drop site for the <code>DropTarget</code>
     * registered with this listener.
     *
     * <p>
     * <p>
     *  注意,<code> DropTargetDragEvent </code>实例上的<code> getTransferable()</code>应该只在相应的监听器的方法中调用,并且所有必要的数据都应
     * 该从返回的<code> Transferable </code >之前的方法返回。
     * 
     * 
     * @param dtde the <code>DropTargetDragEvent</code>
     */
    public void dragEnter(DropTargetDragEvent dtde) {}

    /**
     * Called when a drag operation is ongoing, while the mouse pointer is still
     * over the operable part of the drop site for the <code>DropTarget</code>
     * registered with this listener.
     *
     * <p>
     *  在拖动操作正在进行时调用,当鼠标指针为注册到此侦听器的<code> DropTarget </code>输入放置站点的可操作部分时。
     * 
     * 
     * @param dtde the <code>DropTargetDragEvent</code>
     */
    public void dragOver(DropTargetDragEvent dtde) {}

    /**
     * Called if the user has modified
     * the current drop gesture.
     *
     * <p>
     *  当拖动操作正在进行时调用,而鼠标指针仍然位于向此侦听器注册的<code> DropTarget </code>的放置站点的可操作部分。
     * 
     * 
     * @param dtde the <code>DropTargetDragEvent</code>
     */
    public void dropActionChanged(DropTargetDragEvent dtde) {}

    /**
     * Called while a drag operation is ongoing, when the mouse pointer has
     * exited the operable part of the drop site for the
     * <code>DropTarget</code> registered with this listener.
     *
     * <p>
     *  如果用户已修改当前放置手势,则调用。
     * 
     * 
     * @param dte the <code>DropTargetEvent</code>
     */
    public void dragExit(DropTargetEvent dte) {}
}
