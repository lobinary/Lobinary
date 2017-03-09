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
package javax.swing;

import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.beans.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.TooManyListenersException;
import javax.swing.plaf.UIResource;
import javax.swing.event.*;
import javax.swing.text.JTextComponent;

import sun.reflect.misc.MethodUtil;
import sun.swing.SwingUtilities2;
import sun.awt.AppContext;
import sun.swing.*;
import sun.awt.SunToolkit;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.security.AccessControlContext;
import java.security.ProtectionDomain;
import sun.misc.SharedSecrets;
import sun.misc.JavaSecurityAccess;

import sun.awt.AWTAccessor;

/**
 * This class is used to handle the transfer of a <code>Transferable</code>
 * to and from Swing components.  The <code>Transferable</code> is used to
 * represent data that is exchanged via a cut, copy, or paste
 * to/from a clipboard.  It is also used in drag-and-drop operations
 * to represent a drag from a component, and a drop to a component.
 * Swing provides functionality that automatically supports cut, copy,
 * and paste keyboard bindings that use the functionality provided by
 * an implementation of this class.  Swing also provides functionality
 * that automatically supports drag and drop that uses the functionality
 * provided by an implementation of this class.  The Swing developer can
 * concentrate on specifying the semantics of a transfer primarily by setting
 * the <code>transferHandler</code> property on a Swing component.
 * <p>
 * This class is implemented to provide a default behavior of transferring
 * a component property simply by specifying the name of the property in
 * the constructor.  For example, to transfer the foreground color from
 * one component to another either via the clipboard or a drag and drop operation
 * a <code>TransferHandler</code> can be constructed with the string "foreground".  The
 * built in support will use the color returned by <code>getForeground</code> as the source
 * of the transfer, and <code>setForeground</code> for the target of a transfer.
 * <p>
 * Please see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html">
 * How to Use Drag and Drop and Data Transfer</a>,
 * a section in <em>The Java Tutorial</em>, for more information.
 *
 *
 * <p>
 *  这个类用于处理一个<code> Transferable </code>到Swing组件的传输。
 *  <code> Transferable </code>用于表示通过剪切,复制或粘贴到剪贴板或从剪贴板进行交换的数据。它也用在拖放操作中,用于表示从组件拖动,以及拖放到组件。
 *  Swing提供了自动支持剪切,复制和粘贴键盘绑定的功能,这些键盘绑定使用该类的实现提供的功能。 Swing还提供了自动支持使用此类的实现提供的功能的拖放功能。
 *  Swing开发人员可以专注于指定传输的语义,主要通过在Swing组件上设置<code> transferHandler </code>属性。
 * <p>
 * 实现此类是为了提供一个默认行为,通过在构造函数中指定属性的名称来传递组件属性。
 * 例如,要通过剪贴板或拖放操作将前景色从一个组件传输到另一个组件,可以使用字符串"foreground"构建一个<code> TransferHandler </code>。
 * 内置支持将使用由<code> getForeground </code>作为传输源和<code> setForeground </code>返回的颜色作为传输目标。
 * <p>
 *  请参见
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html">
 *  如何使用拖放和数据传输</a>,有关更多信息,请参阅<em> Java教程</em>中的一节。
 * 
 * 
 * @author Timothy Prinzing
 * @author Shannon Hickey
 * @since 1.4
 */
@SuppressWarnings("serial")
public class TransferHandler implements Serializable {

    /**
     * An <code>int</code> representing no transfer action.
     * <p>
     *  表示无传输操作的<code> int </code>。
     * 
     */
    public static final int NONE = DnDConstants.ACTION_NONE;

    /**
     * An <code>int</code> representing a &quot;copy&quot; transfer action.
     * This value is used when data is copied to a clipboard
     * or copied elsewhere in a drag and drop operation.
     * <p>
     *  代表"副本"的<code> int </code>转移动作。当将数据复制到剪贴板或在拖放操作中复制到其他位置时使用此值。
     * 
     */
    public static final int COPY = DnDConstants.ACTION_COPY;

    /**
     * An <code>int</code> representing a &quot;move&quot; transfer action.
     * This value is used when data is moved to a clipboard (i.e. a cut)
     * or moved elsewhere in a drag and drop operation.
     * <p>
     *  代表"移动"的<code> int </code>转移动作。当在拖放操作中将数据移动到剪贴板(即剪切)或移动到其他位置时使用此值。
     * 
     */
    public static final int MOVE = DnDConstants.ACTION_MOVE;

    /**
     * An <code>int</code> representing a source action capability of either
     * &quot;copy&quot; or &quot;move&quot;.
     * <p>
     *  表示"复制"的源动作能力的<code> int </code>或"移动"。
     * 
     */
    public static final int COPY_OR_MOVE = DnDConstants.ACTION_COPY_OR_MOVE;

    /**
     * An <code>int</code> representing a &quot;link&quot; transfer action.
     * This value is used to specify that data should be linked in a drag
     * and drop operation.
     *
     * <p>
     *  代表"链接"的<code> int </code>转移动作。此值用于指定应在拖放操作中链接数据。
     * 
     * 
     * @see java.awt.dnd.DnDConstants#ACTION_LINK
     * @since 1.6
     */
    public static final int LINK = DnDConstants.ACTION_LINK;

    /**
     * An interface to tag things with a {@code getTransferHandler} method.
     * <p>
     *  一个用{@code getTransferHandler}方法标记事物的接口。
     * 
     */
    interface HasGetTransferHandler {

        /** Returns the {@code TransferHandler}.
         *
         * <p>
         * 
         * @return The {@code TransferHandler} or {@code null}
         */
        public TransferHandler getTransferHandler();
    }

    /**
     * Represents a location where dropped data should be inserted.
     * This is a base class that only encapsulates a point.
     * Components supporting drop may provide subclasses of this
     * containing more information.
     * <p>
     * Developers typically shouldn't create instances of, or extend, this
     * class. Instead, these are something provided by the DnD
     * implementation by <code>TransferSupport</code> instances and by
     * components with a <code>getDropLocation()</code> method.
     *
     * <p>
     * 表示应插入已删除数据的位置。这是一个只封装一个点的基类。支持drop的组件可以提供包含更多信息的子类。
     * <p>
     *  开发人员通常不应该创建或扩展此类的实例。
     * 相反,这些是由<code> TransferSupport </code>实例的DnD实现和由<code> getDropLocation()</code>方法的组件提供的东西。
     * 
     * 
     * @see javax.swing.TransferHandler.TransferSupport#getDropLocation
     * @since 1.6
     */
    public static class DropLocation {
        private final Point dropPoint;

        /**
         * Constructs a drop location for the given point.
         *
         * <p>
         *  构造给定点的放置位置。
         * 
         * 
         * @param dropPoint the drop point, representing the mouse's
         *        current location within the component.
         * @throws IllegalArgumentException if the point
         *         is <code>null</code>
         */
        protected DropLocation(Point dropPoint) {
            if (dropPoint == null) {
                throw new IllegalArgumentException("Point cannot be null");
            }

            this.dropPoint = new Point(dropPoint);
        }

        /**
         * Returns the drop point, representing the mouse's
         * current location within the component.
         *
         * <p>
         *  返回drop point,表示鼠标在组件中的当前位置。
         * 
         * 
         * @return the drop point.
         */
        public final Point getDropPoint() {
            return new Point(dropPoint);
        }

        /**
         * Returns a string representation of this drop location.
         * This method is intended to be used for debugging purposes,
         * and the content and format of the returned string may vary
         * between implementations.
         *
         * <p>
         *  返回此放置位置的字符串表示形式。此方法旨在用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
         * 
         * 
         * @return a string representation of this drop location
         */
        public String toString() {
            return getClass().getName() + "[dropPoint=" + dropPoint + "]";
        }
    };

    /**
     * This class encapsulates all relevant details of a clipboard
     * or drag and drop transfer, and also allows for customizing
     * aspects of the drag and drop experience.
     * <p>
     * The main purpose of this class is to provide the information
     * needed by a developer to determine the suitability of a
     * transfer or to import the data contained within. But it also
     * doubles as a controller for customizing properties during drag
     * and drop, such as whether or not to show the drop location,
     * and which drop action to use.
     * <p>
     * Developers typically need not create instances of this
     * class. Instead, they are something provided by the DnD
     * implementation to certain methods in <code>TransferHandler</code>.
     *
     * <p>
     *  这个类封装了剪贴板或拖放传输的所有相关细节,还允许定制拖放体验的各个方面。
     * <p>
     *  此类的主要目的是提供开发人员确定传输的适合性或导入其中包含的数据所需的信息。但它也可以作为控制器在拖放期间自定义属性,例如是否显示放置位置以及要使用的放置操作。
     * <p>
     *  开发人员通常不需要创建此类的实例。相反,它们由DnD实现提供给<code> TransferHandler </code>中的某些方法。
     * 
     * 
     * @see #canImport(TransferHandler.TransferSupport)
     * @see #importData(TransferHandler.TransferSupport)
     * @since 1.6
     */
    public final static class TransferSupport {
        private boolean isDrop;
        private Component component;

        private boolean showDropLocationIsSet;
        private boolean showDropLocation;

        private int dropAction = -1;

        /**
         * The source is a {@code DropTargetDragEvent} or
         * {@code DropTargetDropEvent} for drops,
         * and a {@code Transferable} otherwise
         * <p>
         * 源代码是drop的{@code DropTargetDragEvent}或{@code DropTargetDropEvent},否则为{@code Transferable}
         * 
         */
        private Object source;

        private DropLocation dropLocation;

        /**
         * Create a <code>TransferSupport</code> with <code>isDrop()</code>
         * <code>true</code> for the given component, event, and index.
         *
         * <p>
         *  对于给定的组件,事件和索引,使用<code> isDrop()</code> <code> true </code>创建<code> TransferSupport </code>。
         * 
         * 
         * @param component the target component
         * @param event a <code>DropTargetEvent</code>
         */
        private TransferSupport(Component component,
                             DropTargetEvent event) {

            isDrop = true;
            setDNDVariables(component, event);
        }

        /**
         * Create a <code>TransferSupport</code> with <code>isDrop()</code>
         * <code>false</code> for the given component and
         * <code>Transferable</code>.
         *
         * <p>
         *  为给定组件和<code> Transferable </code>创建一个<code> TransferSupport </code>和<code> isDrop()</code> <code> fa
         * lse </code>。
         * 
         * 
         * @param component the target component
         * @param transferable the transferable
         * @throws NullPointerException if either parameter
         *         is <code>null</code>
         */
        public TransferSupport(Component component, Transferable transferable) {
            if (component == null) {
                throw new NullPointerException("component is null");
            }

            if (transferable == null) {
                throw new NullPointerException("transferable is null");
            }

            isDrop = false;
            this.component = component;
            this.source = transferable;
        }

        /**
         * Allows for a single instance to be reused during DnD.
         *
         * <p>
         *  允许在DnD期间重新使用单个实例。
         * 
         * 
         * @param component the target component
         * @param event a <code>DropTargetEvent</code>
         */
        private void setDNDVariables(Component component,
                                     DropTargetEvent event) {

            assert isDrop;

            this.component = component;
            this.source = event;
            dropLocation = null;
            dropAction = -1;
            showDropLocationIsSet = false;

            if (source == null) {
                return;
            }

            assert source instanceof DropTargetDragEvent ||
                   source instanceof DropTargetDropEvent;

            Point p = source instanceof DropTargetDragEvent
                          ? ((DropTargetDragEvent)source).getLocation()
                          : ((DropTargetDropEvent)source).getLocation();

            if (SunToolkit.isInstanceOf(component, "javax.swing.text.JTextComponent")) {
                dropLocation = SwingAccessor.getJTextComponentAccessor().
                                   dropLocationForPoint((JTextComponent)component, p);
            } else if (component instanceof JComponent) {
                dropLocation = ((JComponent)component).dropLocationForPoint(p);
            }

            /*
             * The drop location may be null at this point if the component
             * doesn't return custom drop locations. In this case, a point-only
             * drop location will be created lazily when requested.
             * <p>
             *  如果组件不返回自定义放置位置,则放置位置在此时可以为null。在这种情况下,将会在请求时延迟创建纯点下载位置。
             * 
             */
        }

        /**
         * Returns whether or not this <code>TransferSupport</code>
         * represents a drop operation.
         *
         * <p>
         *  返回此<code> TransferSupport </code>是否表示放置操作。
         * 
         * 
         * @return <code>true</code> if this is a drop operation,
         *         <code>false</code> otherwise.
         */
        public boolean isDrop() {
            return isDrop;
        }

        /**
         * Returns the target component of this transfer.
         *
         * <p>
         *  返回此传输的目标组件。
         * 
         * 
         * @return the target component
         */
        public Component getComponent() {
            return component;
        }

        /**
         * Checks that this is a drop and throws an
         * {@code IllegalStateException} if it isn't.
         *
         * <p>
         *  检查这是否为drop,如果没有,则抛出{@code IllegalStateException}。
         * 
         * 
         * @throws IllegalStateException if {@code isDrop} is false.
         */
        private void assureIsDrop() {
            if (!isDrop) {
                throw new IllegalStateException("Not a drop");
            }
        }

        /**
         * Returns the current (non-{@code null}) drop location for the component,
         * when this {@code TransferSupport} represents a drop.
         * <p>
         * Note: For components with built-in drop support, this location
         * will be a subclass of {@code DropLocation} of the same type
         * returned by that component's {@code getDropLocation} method.
         * <p>
         * This method is only for use with drag and drop transfers.
         * Calling it when {@code isDrop()} is {@code false} results
         * in an {@code IllegalStateException}.
         *
         * <p>
         *  返回当前{@code TransferSupport}表示丢弃的组件的当前(非 -  {@ code null})丢弃位置。
         * <p>
         *  注意：对于具有内置拖放支持的组件,此位置将是该组件的{@code getDropLocation}方法返回的相同类型的{@code DropLocation}的子类。
         * <p>
         *  此方法仅用于拖放传输。当{@code isDrop()}为{@code false}时调用它会产生{@code IllegalStateException}。
         * 
         * 
         * @return the drop location
         * @throws IllegalStateException if this is not a drop
         * @see #isDrop()
         */
        public DropLocation getDropLocation() {
            assureIsDrop();

            if (dropLocation == null) {
                /*
                 * component didn't give us a custom drop location,
                 * so lazily create a point-only location
                 * <p>
                 *  组件没有给我们定制放置位置,因此懒地创建一个点位置
                 * 
                 */
                Point p = source instanceof DropTargetDragEvent
                              ? ((DropTargetDragEvent)source).getLocation()
                              : ((DropTargetDropEvent)source).getLocation();

                dropLocation = new DropLocation(p);
            }

            return dropLocation;
        }

        /**
         * Sets whether or not the drop location should be visually indicated
         * for the transfer - which must represent a drop. This is applicable to
         * those components that automatically
         * show the drop location when appropriate during a drag and drop
         * operation). By default, the drop location is shown only when the
         * {@code TransferHandler} has said it can accept the import represented
         * by this {@code TransferSupport}. With this method you can force the
         * drop location to always be shown, or always not be shown.
         * <p>
         * This method is only for use with drag and drop transfers.
         * Calling it when {@code isDrop()} is {@code false} results
         * in an {@code IllegalStateException}.
         *
         * <p>
         * 设置是否应该为传输视觉指示放置位置 - 这必须表示放置。这适用于在拖放操作期间适当时自动显示放置位置的那些组件。
         * 默认情况下,仅当{@code TransferHandler}表示可以接受此{@code TransferSupport}所表示的导入时,才会显示删除位置。
         * 使用此方法,您可以强制删除位置始终显示,或始终不显示。
         * <p>
         *  此方法仅用于拖放传输。当{@code isDrop()}为{@code false}时调用它会产生{@code IllegalStateException}。
         * 
         * 
         * @param showDropLocation whether or not to indicate the drop location
         * @throws IllegalStateException if this is not a drop
         * @see #isDrop()
         */
        public void setShowDropLocation(boolean showDropLocation) {
            assureIsDrop();

            this.showDropLocation = showDropLocation;
            this.showDropLocationIsSet = true;
        }

        /**
         * Sets the drop action for the transfer - which must represent a drop
         * - to the given action,
         * instead of the default user drop action. The action must be
         * supported by the source's drop actions, and must be one
         * of {@code COPY}, {@code MOVE} or {@code LINK}.
         * <p>
         * This method is only for use with drag and drop transfers.
         * Calling it when {@code isDrop()} is {@code false} results
         * in an {@code IllegalStateException}.
         *
         * <p>
         *  设置传递的删除操作(必须表示删除)给定操作,而不是默认用户删除操作。操作必须由源的下拉操作支持,并且必须是{@code COPY},{@code MOVE}或{@code LINK}之一。
         * <p>
         *  此方法仅用于拖放传输。当{@code isDrop()}为{@code false}时调用它会产生{@code IllegalStateException}。
         * 
         * 
         * @param dropAction the drop action
         * @throws IllegalStateException if this is not a drop
         * @throws IllegalArgumentException if an invalid action is specified
         * @see #getDropAction
         * @see #getUserDropAction
         * @see #getSourceDropActions
         * @see #isDrop()
         */
        public void setDropAction(int dropAction) {
            assureIsDrop();

            int action = dropAction & getSourceDropActions();

            if (!(action == COPY || action == MOVE || action == LINK)) {
                throw new IllegalArgumentException("unsupported drop action: " + dropAction);
            }

            this.dropAction = dropAction;
        }

        /**
         * Returns the action chosen for the drop, when this
         * {@code TransferSupport} represents a drop.
         * <p>
         * Unless explicitly chosen by way of {@code setDropAction},
         * this returns the user drop action provided by
         * {@code getUserDropAction}.
         * <p>
         * You may wish to query this in {@code TransferHandler}'s
         * {@code importData} method to customize processing based
         * on the action.
         * <p>
         * This method is only for use with drag and drop transfers.
         * Calling it when {@code isDrop()} is {@code false} results
         * in an {@code IllegalStateException}.
         *
         * <p>
         *  当此{@code TransferSupport}表示删除时,返回为删除选择的操作。
         * <p>
         *  除非通过{@code setDropAction}明确选择,否则返回{@code getUserDropAction}提供的用户删除操作。
         * <p>
         *  您可以在{@code TransferHandler}的{@code importData}方法中进行查询,以根据操作自定义处理。
         * <p>
         * 此方法仅用于拖放传输。当{@code isDrop()}为{@code false}时调用它会产生{@code IllegalStateException}。
         * 
         * 
         * @return the action chosen for the drop
         * @throws IllegalStateException if this is not a drop
         * @see #setDropAction
         * @see #getUserDropAction
         * @see #isDrop()
         */
        public int getDropAction() {
            return dropAction == -1 ? getUserDropAction() : dropAction;
        }

        /**
         * Returns the user drop action for the drop, when this
         * {@code TransferSupport} represents a drop.
         * <p>
         * The user drop action is chosen for a drop as described in the
         * documentation for {@link java.awt.dnd.DropTargetDragEvent} and
         * {@link java.awt.dnd.DropTargetDropEvent}. A different action
         * may be chosen as the drop action by way of the {@code setDropAction}
         * method.
         * <p>
         * You may wish to query this in {@code TransferHandler}'s
         * {@code canImport} method when determining the suitability of a
         * drop or when deciding on a drop action to explicitly choose.
         * <p>
         * This method is only for use with drag and drop transfers.
         * Calling it when {@code isDrop()} is {@code false} results
         * in an {@code IllegalStateException}.
         *
         * <p>
         *  当此{@code TransferSupport}表示删除时,返回用户删除操作。
         * <p>
         *  为{@link java.awt.dnd.DropTargetDragEvent}和{@link java.awt.dnd.DropTargetDropEvent}文档中所述的drop选择用户drop
         * 操作。
         * 可以通过{@code setDropAction}方法选择不同的动作作为拖放动作。
         * <p>
         *  您可能希望在{@code TransferHandler}的{@code canImport}方法中查询此属性,以确定丢弃的适用性,或者决定明确选择丢弃操作时。
         * <p>
         *  此方法仅用于拖放传输。当{@code isDrop()}为{@code false}时调用它会产生{@code IllegalStateException}。
         * 
         * 
         * @return the user drop action
         * @throws IllegalStateException if this is not a drop
         * @see #setDropAction
         * @see #getDropAction
         * @see #isDrop()
         */
        public int getUserDropAction() {
            assureIsDrop();

            return (source instanceof DropTargetDragEvent)
                ? ((DropTargetDragEvent)source).getDropAction()
                : ((DropTargetDropEvent)source).getDropAction();
        }

        /**
         * Returns the drag source's supported drop actions, when this
         * {@code TransferSupport} represents a drop.
         * <p>
         * The source actions represent the set of actions supported by the
         * source of this transfer, and are represented as some bitwise-OR
         * combination of {@code COPY}, {@code MOVE} and {@code LINK}.
         * You may wish to query this in {@code TransferHandler}'s
         * {@code canImport} method when determining the suitability of a drop
         * or when deciding on a drop action to explicitly choose. To determine
         * if a particular action is supported by the source, bitwise-AND
         * the action with the source drop actions, and then compare the result
         * against the original action. For example:
         * <pre>
         * boolean copySupported = (COPY &amp; getSourceDropActions()) == COPY;
         * </pre>
         * <p>
         * This method is only for use with drag and drop transfers.
         * Calling it when {@code isDrop()} is {@code false} results
         * in an {@code IllegalStateException}.
         *
         * <p>
         *  当此{@code TransferSupport}表示删除时,返回拖动源支持的放置操作。
         * <p>
         * 源操作表示此传输来源支持的一组操作,并且表示为{@code COPY},{@code MOVE}和{@code LINK}的一些按位或OR组合。
         * 您可能希望在{@code TransferHandler}的{@code canImport}方法中查询此属性,以确定丢弃的适用性,或者决定明确选择丢弃操作时。
         * 要确定源是否支持特定操作,请使用源下拉操作按位与AND操作,然后将结果与原始操作进行比较。例如：。
         * <pre>
         *  boolean copySupported =(COPY&amp; getSourceDropActions())== COPY;
         * </pre>
         * <p>
         *  此方法仅用于拖放传输。当{@code isDrop()}为{@code false}时调用它会产生{@code IllegalStateException}。
         * 
         * 
         * @return the drag source's supported drop actions
         * @throws IllegalStateException if this is not a drop
         * @see #isDrop()
         */
        public int getSourceDropActions() {
            assureIsDrop();

            return (source instanceof DropTargetDragEvent)
                ? ((DropTargetDragEvent)source).getSourceActions()
                : ((DropTargetDropEvent)source).getSourceActions();
        }

        /**
         * Returns the data flavors for this transfer.
         *
         * <p>
         *  返回此传输的数据flavor。
         * 
         * 
         * @return the data flavors for this transfer
         */
        public DataFlavor[] getDataFlavors() {
            if (isDrop) {
                if (source instanceof DropTargetDragEvent) {
                    return ((DropTargetDragEvent)source).getCurrentDataFlavors();
                } else {
                    return ((DropTargetDropEvent)source).getCurrentDataFlavors();
                }
            }

            return ((Transferable)source).getTransferDataFlavors();
        }

        /**
         * Returns whether or not the given data flavor is supported.
         *
         * <p>
         *  返回是否支持给定的数据flavor。
         * 
         * 
         * @param df the <code>DataFlavor</code> to test
         * @return whether or not the given flavor is supported.
         */
        public boolean isDataFlavorSupported(DataFlavor df) {
            if (isDrop) {
                if (source instanceof DropTargetDragEvent) {
                    return ((DropTargetDragEvent)source).isDataFlavorSupported(df);
                } else {
                    return ((DropTargetDropEvent)source).isDataFlavorSupported(df);
                }
            }

            return ((Transferable)source).isDataFlavorSupported(df);
        }

        /**
         * Returns the <code>Transferable</code> associated with this transfer.
         * <p>
         * Note: Unless it is necessary to fetch the <code>Transferable</code>
         * directly, use one of the other methods on this class to inquire about
         * the transfer. This may perform better than fetching the
         * <code>Transferable</code> and asking it directly.
         *
         * <p>
         *  返回与此转移相关联的<code>可转移</code>。
         * <p>
         *  注意：除非必须直接获取<code> Transferable </code>,否则请使用此类的其他方法之一来查询传输。
         * 这可能比提取<code> Transferable </code>更好,并直接询问它。
         * 
         * 
         * @return the <code>Transferable</code> associated with this transfer
         */
        public Transferable getTransferable() {
            if (isDrop) {
                if (source instanceof DropTargetDragEvent) {
                    return ((DropTargetDragEvent)source).getTransferable();
                } else {
                    return ((DropTargetDropEvent)source).getTransferable();
                }
            }

            return (Transferable)source;
        }
    }


    /**
     * Returns an {@code Action} that performs cut operations to the
     * clipboard. When performed, this action operates on the {@code JComponent}
     * source of the {@code ActionEvent} by invoking {@code exportToClipboard},
     * with a {@code MOVE} action, on the component's {@code TransferHandler}.
     *
     * <p>
     *  返回对剪贴板执行剪切操作的{@code Action}。
     * 执行时,此操作通过在组件的{@code TransferHandler}上调用{@code exportToClipboard}(使用{@code MOVE}操作)在{@code ActionEvent}
     * 的{@code JComponent}源操作。
     *  返回对剪贴板执行剪切操作的{@code Action}。
     * 
     * 
     * @return an {@code Action} for performing cuts to the clipboard
     */
    public static Action getCutAction() {
        return cutAction;
    }

    /**
     * Returns an {@code Action} that performs copy operations to the
     * clipboard. When performed, this action operates on the {@code JComponent}
     * source of the {@code ActionEvent} by invoking {@code exportToClipboard},
     * with a {@code COPY} action, on the component's {@code TransferHandler}.
     *
     * <p>
     * 返回对剪贴板执行复制操作的{@code Action}。
     * 执行此操作时,此操作通过在组件的{@code TransferHandler}上调用{@code exportToClipboard}(使用{@code COPY}操作)对{@code ActionEvent}
     * 的{@code JComponent}。
     * 返回对剪贴板执行复制操作的{@code Action}。
     * 
     * 
     * @return an {@code Action} for performing copies to the clipboard
     */
    public static Action getCopyAction() {
        return copyAction;
    }

    /**
     * Returns an {@code Action} that performs paste operations from the
     * clipboard. When performed, this action operates on the {@code JComponent}
     * source of the {@code ActionEvent} by invoking {@code importData},
     * with the clipboard contents, on the component's {@code TransferHandler}.
     *
     * <p>
     *  返回从剪贴板执行粘贴操作的{@code Action}。
     * 执行时,此操作通过在组件的{@code TransferHandler}上调用带剪贴板内容的{@code importData}在{@code ActionEvent}的{@code JComponent}
     * 源操作。
     *  返回从剪贴板执行粘贴操作的{@code Action}。
     * 
     * 
     * @return an {@code Action} for performing pastes from the clipboard
     */
    public static Action getPasteAction() {
        return pasteAction;
    }


    /**
     * Constructs a transfer handler that can transfer a Java Bean property
     * from one component to another via the clipboard or a drag and drop
     * operation.
     *
     * <p>
     *  构造一个传输处理程序,可以通过剪贴板或拖放操作将Java Bean属性从一个组件传输到另一个组件。
     * 
     * 
     * @param property  the name of the property to transfer; this can
     *  be <code>null</code> if there is no property associated with the transfer
     *  handler (a subclass that performs some other kind of transfer, for example)
     */
    public TransferHandler(String property) {
        propertyName = property;
    }

    /**
     * Convenience constructor for subclasses.
     * <p>
     *  子类的便利构造函数。
     * 
     */
    protected TransferHandler() {
        this(null);
    }


    /**
     * image for the {@code startDrag} method
     *
     * <p>
     *  图像为{@code startDrag}方法
     * 
     * 
     * @see java.awt.dnd.DragGestureEvent#startDrag(Cursor dragCursor, Image dragImage, Point imageOffset, Transferable transferable, DragSourceListener dsl)
     */
    private  Image dragImage;

    /**
     * anchor offset for the {@code startDrag} method
     *
     * <p>
     *  {@code startDrag}方法的锚点偏移
     * 
     * 
     * @see java.awt.dnd.DragGestureEvent#startDrag(Cursor dragCursor, Image dragImage, Point imageOffset, Transferable transferable, DragSourceListener dsl)
     */
    private  Point dragImageOffset;

    /**
     * Sets the drag image parameter. The image has to be prepared
     * for rendering by the moment of the call. The image is stored
     * by reference because of some performance reasons.
     *
     * <p>
     *  设置拖动图像参数。图像必须准备在呼叫的时刻进行渲染。由于某些性能原因,图像通过引用存储。
     * 
     * 
     * @param img an image to drag
     */
    public void setDragImage(Image img) {
        dragImage = img;
    }

    /**
     * Returns the drag image. If there is no image to drag,
     * the returned value is {@code null}.
     *
     * <p>
     *  返回拖动图像。如果没有要拖动的图像,则返回的值为{@code null}。
     * 
     * 
     * @return the reference to the drag image
     */
    public Image getDragImage() {
        return dragImage;
    }

    /**
     * Sets an anchor offset for the image to drag.
     * It can not be {@code null}.
     *
     * <p>
     *  设置要拖动的图像的锚点偏移。它不能是{@code null}。
     * 
     * 
     * @param p a {@code Point} object that corresponds
     * to coordinates of an anchor offset of the image
     * relative to the upper left corner of the image
     */
    public void setDragImageOffset(Point p) {
        dragImageOffset = new Point(p);
    }

    /**
     * Returns an anchor offset for the image to drag.
     *
     * <p>
     *  返回要拖动的图像的锚点偏移量。
     * 
     * 
     * @return a {@code Point} object that corresponds
     * to coordinates of an anchor offset of the image
     * relative to the upper left corner of the image.
     * The point {@code (0,0)} returns by default.
     */
    public Point getDragImageOffset() {
        if (dragImageOffset == null) {
            return new Point(0,0);
        }
        return new Point(dragImageOffset);
    }

    /**
     * Causes the Swing drag support to be initiated.  This is called by
     * the various UI implementations in the <code>javax.swing.plaf.basic</code>
     * package if the dragEnabled property is set on the component.
     * This can be called by custom UI
     * implementations to use the Swing drag support.  This method can also be called
     * by a Swing extension written as a subclass of <code>JComponent</code>
     * to take advantage of the Swing drag support.
     * <p>
     * The transfer <em>will not necessarily</em> have been completed at the
     * return of this call (i.e. the call does not block waiting for the drop).
     * The transfer will take place through the Swing implementation of the
     * <code>java.awt.dnd</code> mechanism, requiring no further effort
     * from the developer. The <code>exportDone</code> method will be called
     * when the transfer has completed.
     *
     * <p>
     * 导致回转阻力支持启动。如果在组件上设置了dragEnabled属性,那么它会被<code> javax.swing.plaf.basic </code>包中的各种UI实现调用。
     * 这可以通过自定义UI实现来调用,以使用Swing拖动支持。此方法也可以通过作为<code> JComponent </code>的子类的Swing扩展来调用,以利用Swing拖动支持。
     * <p>
     *  在返回此呼叫时,传输<em>不一定已经完成(即呼叫不阻塞等待下降)。传输将通过Swing实现的<code> java.awt.dnd </code>机制进行,无需开发人员进一步努力。
     * 传输完成后,将调用<code> exportDone </code>方法。
     * 
     * 
     * @param comp  the component holding the data to be transferred;
     *              provided to enable sharing of <code>TransferHandler</code>s
     * @param e     the event that triggered the transfer
     * @param action the transfer action initially requested;
     *               either {@code COPY}, {@code MOVE} or {@code LINK};
     *               the DnD system may change the action used during the
     *               course of the drag operation
     */
    public void exportAsDrag(JComponent comp, InputEvent e, int action) {
        int srcActions = getSourceActions(comp);

        // only mouse events supported for drag operations
        if (!(e instanceof MouseEvent)
                // only support known actions
                || !(action == COPY || action == MOVE || action == LINK)
                // only support valid source actions
                || (srcActions & action) == 0) {

            action = NONE;
        }

        if (action != NONE && !GraphicsEnvironment.isHeadless()) {
            if (recognizer == null) {
                recognizer = new SwingDragGestureRecognizer(new DragHandler());
            }
            recognizer.gestured(comp, (MouseEvent)e, srcActions, action);
        } else {
            exportDone(comp, null, NONE);
        }
    }

    /**
     * Causes a transfer from the given component to the
     * given clipboard.  This method is called by the default cut and
     * copy actions registered in a component's action map.
     * <p>
     * The transfer will take place using the <code>java.awt.datatransfer</code>
     * mechanism, requiring no further effort from the developer. Any data
     * transfer <em>will</em> be complete and the <code>exportDone</code>
     * method will be called with the action that occurred, before this method
     * returns. Should the clipboard be unavailable when attempting to place
     * data on it, the <code>IllegalStateException</code> thrown by
     * {@link Clipboard#setContents(Transferable, ClipboardOwner)} will
     * be propagated through this method. However,
     * <code>exportDone</code> will first be called with an action
     * of <code>NONE</code> for consistency.
     *
     * <p>
     *  导致从给定组件到给定剪贴板的传输。此方法由组件的操作映射中注册的默认剪切和复制操作调用。
     * <p>
     * 传输将使用<code> java.awt.datatransfer </code>机制进行,无需开发人员进一步努力。
     * 任何数据传输<em>将完成,<code> exportDone </code>方法将在发生的操作之前调用,然后返回此方法。
     * 如果剪贴板在尝试在其上放置数据时不可用,则{@link Clipboard#setContents(Transferable,ClipboardOwner)}抛出的<code> IllegalState
     * Exception </code>将通过此方法传播。
     * 任何数据传输<em>将完成,<code> exportDone </code>方法将在发生的操作之前调用,然后返回此方法。
     * 然而,为了一致性,将首先使用<code> NONE </code>的动作来调用<code> exportDone </code>。
     * 
     * 
     * @param comp  the component holding the data to be transferred;
     *              provided to enable sharing of <code>TransferHandler</code>s
     * @param clip  the clipboard to transfer the data into
     * @param action the transfer action requested; this should
     *  be a value of either <code>COPY</code> or <code>MOVE</code>;
     *  the operation performed is the intersection  of the transfer
     *  capabilities given by getSourceActions and the requested action;
     *  the intersection may result in an action of <code>NONE</code>
     *  if the requested action isn't supported
     * @throws IllegalStateException if the clipboard is currently unavailable
     * @see Clipboard#setContents(Transferable, ClipboardOwner)
     */
    public void exportToClipboard(JComponent comp, Clipboard clip, int action)
                                                  throws IllegalStateException {

        if ((action == COPY || action == MOVE)
                && (getSourceActions(comp) & action) != 0) {

            Transferable t = createTransferable(comp);
            if (t != null) {
                try {
                    clip.setContents(t, null);
                    exportDone(comp, t, action);
                    return;
                } catch (IllegalStateException ise) {
                    exportDone(comp, t, NONE);
                    throw ise;
                }
            }
        }

        exportDone(comp, null, NONE);
    }

    /**
     * Causes a transfer to occur from a clipboard or a drag and
     * drop operation. The <code>Transferable</code> to be
     * imported and the component to transfer to are contained
     * within the <code>TransferSupport</code>.
     * <p>
     * While the drag and drop implementation calls {@code canImport}
     * to determine the suitability of a transfer before calling this
     * method, the implementation of paste does not. As such, it cannot
     * be assumed that the transfer is acceptable upon a call to
     * this method for paste. It is recommended that {@code canImport} be
     * explicitly called to cover this case.
     * <p>
     * Note: The <code>TransferSupport</code> object passed to this method
     * is only valid for the duration of the method call. It is undefined
     * what values it may contain after this method returns.
     *
     * <p>
     *  导致从剪贴板或拖放操作进行传送。要导入的<code> Transferable </code>和要传输的组件包含在<code> TransferSupport </code>中。
     * <p>
     *  虽然拖放实现在调用此方法之前调用{@code canImport}来确定传输的适用性,但是实现粘贴不会。因此,不能假定在调用该方法进行粘贴时传输是可接受的。
     * 建议明确调用{@code canImport}来覆盖这种情况。
     * <p>
     *  注意：传递给此方法的<code> TransferSupport </code>对象仅在方法调用期间有效。在此方法返回后,它未定义它可能包含的值。
     * 
     * 
     * @param support the object containing the details of
     *        the transfer, not <code>null</code>.
     * @return true if the data was inserted into the component,
     *         false otherwise
     * @throws NullPointerException if <code>support</code> is {@code null}
     * @see #canImport(TransferHandler.TransferSupport)
     * @since 1.6
     */
    public boolean importData(TransferSupport support) {
        return support.getComponent() instanceof JComponent
            ? importData((JComponent)support.getComponent(), support.getTransferable())
            : false;
    }

    /**
     * Causes a transfer to a component from a clipboard or a
     * DND drop operation.  The <code>Transferable</code> represents
     * the data to be imported into the component.
     * <p>
     * Note: Swing now calls the newer version of <code>importData</code>
     * that takes a <code>TransferSupport</code>, which in turn calls this
     * method (if the component in the {@code TransferSupport} is a
     * {@code JComponent}). Developers are encouraged to call and override the
     * newer version as it provides more information (and is the only
     * version that supports use with a {@code TransferHandler} set directly
     * on a {@code JFrame} or other non-{@code JComponent}).
     *
     * <p>
     * 导致从剪贴板或DND拖放操作转移到组件。 <code> Transferable </code>表示要导入到组件中的数据。
     * <p>
     *  注意：Swing现在调用<code> importData </code>的新版本,它接受一个<code> TransferSupport </code>,如果{@code TransferSupport}
     * 中的组件是{@代码JComponent})。
     * 我们鼓励开发人员调用并覆盖较新版本,因为它提供了更多信息(是唯一支持直接在{@code JFrame}或其他非 -  {@ code JComponent}上使用{@code TransferHandler}
     * 集合的信息) 。
     * 
     * 
     * @param comp  the component to receive the transfer;
     *              provided to enable sharing of <code>TransferHandler</code>s
     * @param t     the data to import
     * @return  true if the data was inserted into the component, false otherwise
     * @see #importData(TransferHandler.TransferSupport)
     */
    public boolean importData(JComponent comp, Transferable t) {
        PropertyDescriptor prop = getPropertyDescriptor(comp);
        if (prop != null) {
            Method writer = prop.getWriteMethod();
            if (writer == null) {
                // read-only property. ignore
                return false;
            }
            Class<?>[] params = writer.getParameterTypes();
            if (params.length != 1) {
                // zero or more than one argument, ignore
                return false;
            }
            DataFlavor flavor = getPropertyDataFlavor(params[0], t.getTransferDataFlavors());
            if (flavor != null) {
                try {
                    Object value = t.getTransferData(flavor);
                    Object[] args = { value };
                    MethodUtil.invoke(writer, comp, args);
                    return true;
                } catch (Exception ex) {
                    System.err.println("Invocation failed");
                    // invocation code
                }
            }
        }
        return false;
    }

    /**
     * This method is called repeatedly during a drag and drop operation
     * to allow the developer to configure properties of, and to return
     * the acceptability of transfers; with a return value of {@code true}
     * indicating that the transfer represented by the given
     * {@code TransferSupport} (which contains all of the details of the
     * transfer) is acceptable at the current time, and a value of {@code false}
     * rejecting the transfer.
     * <p>
     * For those components that automatically display a drop location during
     * drag and drop, accepting the transfer, by default, tells them to show
     * the drop location. This can be changed by calling
     * {@code setShowDropLocation} on the {@code TransferSupport}.
     * <p>
     * By default, when the transfer is accepted, the chosen drop action is that
     * picked by the user via their drag gesture. The developer can override
     * this and choose a different action, from the supported source
     * actions, by calling {@code setDropAction} on the {@code TransferSupport}.
     * <p>
     * On every call to {@code canImport}, the {@code TransferSupport} contains
     * fresh state. As such, any properties set on it must be set on every
     * call. Upon a drop, {@code canImport} is called one final time before
     * calling into {@code importData}. Any state set on the
     * {@code TransferSupport} during that last call will be available in
     * {@code importData}.
     * <p>
     * This method is not called internally in response to paste operations.
     * As such, it is recommended that implementations of {@code importData}
     * explicitly call this method for such cases and that this method
     * be prepared to return the suitability of paste operations as well.
     * <p>
     * Note: The <code>TransferSupport</code> object passed to this method
     * is only valid for the duration of the method call. It is undefined
     * what values it may contain after this method returns.
     *
     * <p>
     *  在拖放操作期间重复调用此方法,以允许开发人员配置属性并返回传输的可接受性;返回值为{@code true},表示由给定的{@code TransferSupport}(其包含传输的所有细节)表示的传输
     * 在当前时间是可接受的,值为{@code false}拒绝转移。
     * <p>
     *  对于在拖放期间自动显示放置位置的那些组件,默认情况下接受传输将告诉他们显示放置位置。
     * 这可以通过在{@code TransferSupport}上调用{@code setShowDropLocation}来更改。
     * <p>
     * 默认情况下,当接受传输时,所选择的拖放动作是由用户经由其拖动手势选择的。
     * 开发人员可以通过调用{@code TransferSupport}上的{@code setDropAction}来覆盖此操作并从支持的源操作中选择不同的操作。
     * <p>
     *  每次调用{@code canImport}时,{@code TransferSupport}都包含新鲜状态。因此,在其上设置的任何属性必须在每次调用时设置。
     * 一旦删除,{@code canImport}最终调用前调用{@code importData}。
     * 在上次调用期间在{@code TransferSupport}设置的任何状态都将在{@code importData}中可用。
     * <p>
     *  响应于粘贴操作,不会在内部调用此方法。因此,建议{@code importData}的实现为这种情况显式调用此方法,并且该方法也准备好返回粘贴操作的适用性。
     * <p>
     *  注意：传递给此方法的<code> TransferSupport </code>对象仅在方法调用期间有效。在此方法返回后,它未定义它可能包含的值。
     * 
     * 
     * @param support the object containing the details of
     *        the transfer, not <code>null</code>.
     * @return <code>true</code> if the import can happen,
     *         <code>false</code> otherwise
     * @throws NullPointerException if <code>support</code> is {@code null}
     * @see #importData(TransferHandler.TransferSupport)
     * @see javax.swing.TransferHandler.TransferSupport#setShowDropLocation
     * @see javax.swing.TransferHandler.TransferSupport#setDropAction
     * @since 1.6
     */
    public boolean canImport(TransferSupport support) {
        return support.getComponent() instanceof JComponent
            ? canImport((JComponent)support.getComponent(), support.getDataFlavors())
            : false;
    }

    /**
     * Indicates whether a component will accept an import of the given
     * set of data flavors prior to actually attempting to import it.
     * <p>
     * Note: Swing now calls the newer version of <code>canImport</code>
     * that takes a <code>TransferSupport</code>, which in turn calls this
     * method (only if the component in the {@code TransferSupport} is a
     * {@code JComponent}). Developers are encouraged to call and override the
     * newer version as it provides more information (and is the only
     * version that supports use with a {@code TransferHandler} set directly
     * on a {@code JFrame} or other non-{@code JComponent}).
     *
     * <p>
     *  指示组件在实际尝试导入之前是否接受给定数据类型的导入。
     * <p>
     * 注意：Swing现在调用<code> canImport </code>的更新版本,它接受一个<code> TransferSupport </code>,它会调用此方法(只有{@code TransferSupport}
     *  @code JComponent})。
     * 我们鼓励开发人员调用并覆盖较新版本,因为它提供了更多信息(是唯一支持直接在{@code JFrame}或其他非 -  {@ code JComponent}上使用{@code TransferHandler}
     * 集合的信息) 。
     * 
     * 
     * @param comp  the component to receive the transfer;
     *              provided to enable sharing of <code>TransferHandler</code>s
     * @param transferFlavors  the data formats available
     * @return  true if the data can be inserted into the component, false otherwise
     * @see #canImport(TransferHandler.TransferSupport)
     */
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        PropertyDescriptor prop = getPropertyDescriptor(comp);
        if (prop != null) {
            Method writer = prop.getWriteMethod();
            if (writer == null) {
                // read-only property. ignore
                return false;
            }
            Class<?>[] params = writer.getParameterTypes();
            if (params.length != 1) {
                // zero or more than one argument, ignore
                return false;
            }
            DataFlavor flavor = getPropertyDataFlavor(params[0], transferFlavors);
            if (flavor != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the type of transfer actions supported by the source;
     * any bitwise-OR combination of {@code COPY}, {@code MOVE}
     * and {@code LINK}.
     * <p>
     * Some models are not mutable, so a transfer operation of {@code MOVE}
     * should not be advertised in that case. Returning {@code NONE}
     * disables transfers from the component.
     *
     * <p>
     *  返回源支持的传输操作的类型; {@code COPY},{@code MOVE}和{@code LINK}的任何按位或组合。
     * <p>
     *  某些模型不可变,因此在这种情况下不应公布{@code MOVE}的传输操作。返回{@code NONE}会停用组件的传输。
     * 
     * 
     * @param c  the component holding the data to be transferred;
     *           provided to enable sharing of <code>TransferHandler</code>s
     * @return {@code COPY} if the transfer property can be found,
     *          otherwise returns <code>NONE</code>
     */
    public int getSourceActions(JComponent c) {
        PropertyDescriptor prop = getPropertyDescriptor(c);
        if (prop != null) {
            return COPY;
        }
        return NONE;
    }

    /**
     * Returns an object that establishes the look of a transfer.  This is
     * useful for both providing feedback while performing a drag operation and for
     * representing the transfer in a clipboard implementation that has a visual
     * appearance.  The implementation of the <code>Icon</code> interface should
     * not alter the graphics clip or alpha level.
     * The icon implementation need not be rectangular or paint all of the
     * bounding rectangle and logic that calls the icons paint method should
     * not assume the all bits are painted. <code>null</code> is a valid return value
     * for this method and indicates there is no visual representation provided.
     * In that case, the calling logic is free to represent the
     * transferable however it wants.
     * <p>
     * The default Swing logic will not do an alpha blended drag animation if
     * the return is <code>null</code>.
     *
     * <p>
     *  返回一个确定传输外观的对象。这对于在执行拖动操作时提供反馈和在具有视觉外观的剪贴板实现中表示传输两者是有用的。 <code> Icon </code>界面的实现不应该改变图形剪辑或alpha级别。
     * 图标实现不需要是矩形或绘制所有的边界矩形和调用图标的逻辑绘制方法不应该假定所有位都被绘制。 <code> null </code>是此方法的有效返回值,表示没有提供可视化表示。
     * 在这种情况下,调用逻辑可以自由地表示它想要的可转移。
     * <p>
     * 如果返回是<code> null </code>,则默认的Swing逻辑不会执行alpha混合拖动动画。
     * 
     * 
     * @param t  the data to be transferred; this value is expected to have been
     *  created by the <code>createTransferable</code> method
     * @return  <code>null</code>, indicating
     *    there is no default visual representation
     */
    public Icon getVisualRepresentation(Transferable t) {
        return null;
    }

    /**
     * Creates a <code>Transferable</code> to use as the source for
     * a data transfer. Returns the representation of the data to
     * be transferred, or <code>null</code> if the component's
     * property is <code>null</code>
     *
     * <p>
     *  创建<code>可传输</code>以用作数据传输的源。如果组件的属性是<code> null </code>,则返回要传输的数据的表示形式或<code> null </code>
     * 
     * 
     * @param c  the component holding the data to be transferred;
     *              provided to enable sharing of <code>TransferHandler</code>s
     * @return  the representation of the data to be transferred, or
     *  <code>null</code> if the property associated with <code>c</code>
     *  is <code>null</code>
     *
     */
    protected Transferable createTransferable(JComponent c) {
        PropertyDescriptor property = getPropertyDescriptor(c);
        if (property != null) {
            return new PropertyTransferable(property, c);
        }
        return null;
    }

    /**
     * Invoked after data has been exported.  This method should remove
     * the data that was transferred if the action was <code>MOVE</code>.
     * <p>
     * This method is implemented to do nothing since <code>MOVE</code>
     * is not a supported action of this implementation
     * (<code>getSourceActions</code> does not include <code>MOVE</code>).
     *
     * <p>
     *  导出数据后调用。如果操作是<code> MOVE </code>,此方法应删除传输的数据。
     * <p>
     *  因为<code> MOVE </code>不是此实现的支持操作(<code> getSourceActions </code>不包括<code> MOVE </code>),所以此方法不执行任何操作。
     * 
     * 
     * @param source the component that was the source of the data
     * @param data   The data that was transferred or possibly null
     *               if the action is <code>NONE</code>.
     * @param action the actual action that was performed
     */
    protected void exportDone(JComponent source, Transferable data, int action) {
    }

    /**
     * Fetches the property descriptor for the property assigned to this transfer
     * handler on the given component (transfer handler may be shared).  This
     * returns <code>null</code> if the property descriptor can't be found
     * or there is an error attempting to fetch the property descriptor.
     * <p>
     *  获取在给定组件上分配给此传输处理程序的属性的属性描述符(传输处理程序可以共享)。如果无法找到属性描述符或尝试获取属性描述符时出现错误,则返回<code> null </code>。
     * 
     */
    private PropertyDescriptor getPropertyDescriptor(JComponent comp) {
        if (propertyName == null) {
            return null;
        }
        Class<?> k = comp.getClass();
        BeanInfo bi;
        try {
            bi = Introspector.getBeanInfo(k);
        } catch (IntrospectionException ex) {
            return null;
        }
        PropertyDescriptor props[] = bi.getPropertyDescriptors();
        for (int i=0; i < props.length; i++) {
            if (propertyName.equals(props[i].getName())) {
                Method reader = props[i].getReadMethod();

                if (reader != null) {
                    Class<?>[] params = reader.getParameterTypes();

                    if (params == null || params.length == 0) {
                        // found the desired descriptor
                        return props[i];
                    }
                }
            }
        }
        return null;
    }

    /**
     * Fetches the data flavor from the array of possible flavors that
     * has data of the type represented by property type.  Null is
     * returned if there is no match.
     * <p>
     *  从具有由属性类型表示的类型的数据的可能风格的数组中获取数据风味。如果没有匹配,则返回Null。
     * 
     */
    private DataFlavor getPropertyDataFlavor(Class<?> k, DataFlavor[] flavors) {
        for(int i = 0; i < flavors.length; i++) {
            DataFlavor flavor = flavors[i];
            if ("application".equals(flavor.getPrimaryType()) &&
                "x-java-jvm-local-objectref".equals(flavor.getSubType()) &&
                k.isAssignableFrom(flavor.getRepresentationClass())) {

                return flavor;
            }
        }
        return null;
    }


    private String propertyName;
    private static SwingDragGestureRecognizer recognizer = null;

    private static DropTargetListener getDropTargetListener() {
        synchronized(DropHandler.class) {
            DropHandler handler =
                (DropHandler)AppContext.getAppContext().get(DropHandler.class);

            if (handler == null) {
                handler = new DropHandler();
                AppContext.getAppContext().put(DropHandler.class, handler);
            }

            return handler;
        }
    }

    static class PropertyTransferable implements Transferable {

        PropertyTransferable(PropertyDescriptor p, JComponent c) {
            property = p;
            component = c;
        }

        // --- Transferable methods ----------------------------------------------

        /**
         * Returns an array of <code>DataFlavor</code> objects indicating the flavors the data
         * can be provided in.  The array should be ordered according to preference
         * for providing the data (from most richly descriptive to least descriptive).
         * <p>
         *  返回一个<code> DataFlavor </code>对象的数组,指示数据可以提供的风格。数组应根据提供数据的偏好排序(从最丰富的描述性到最少描述性)。
         * 
         * 
         * @return an array of data flavors in which this data can be transferred
         */
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[1];
            Class<?> propertyType = property.getPropertyType();
            String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=" + propertyType.getName();
            try {
                flavors[0] = new DataFlavor(mimeType);
            } catch (ClassNotFoundException cnfe) {
                flavors = new DataFlavor[0];
            }
            return flavors;
        }

        /**
         * Returns whether the specified data flavor is supported for
         * this object.
         * <p>
         *  返回此对象是否支持指定的数据flavor。
         * 
         * 
         * @param flavor the requested flavor for the data
         * @return true if this <code>DataFlavor</code> is supported,
         *   otherwise false
         */
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            Class<?> propertyType = property.getPropertyType();
            if ("application".equals(flavor.getPrimaryType()) &&
                "x-java-jvm-local-objectref".equals(flavor.getSubType()) &&
                flavor.getRepresentationClass().isAssignableFrom(propertyType)) {

                return true;
            }
            return false;
        }

        /**
         * Returns an object which represents the data to be transferred.  The class
         * of the object returned is defined by the representation class of the flavor.
         *
         * <p>
         * 返回表示要传输的数据的对象。返回的对象的类由flavor的表示类定义。
         * 
         * 
         * @param flavor the requested flavor for the data
         * @see DataFlavor#getRepresentationClass
         * @exception IOException                if the data is no longer available
         *              in the requested flavor.
         * @exception UnsupportedFlavorException if the requested data flavor is
         *              not supported.
         */
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (! isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            Method reader = property.getReadMethod();
            Object value = null;
            try {
                value = MethodUtil.invoke(reader, component, (Object[])null);
            } catch (Exception ex) {
                throw new IOException("Property read failed: " + property.getName());
            }
            return value;
        }

        JComponent component;
        PropertyDescriptor property;
    }

    /**
     * This is the default drop target for drag and drop operations if
     * one isn't provided by the developer.  <code>DropTarget</code>
     * only supports one <code>DropTargetListener</code> and doesn't
     * function properly if it isn't set.
     * This class sets the one listener as the linkage of drop handling
     * to the <code>TransferHandler</code>, and adds support for
     * additional listeners which some of the <code>ComponentUI</code>
     * implementations install to manipulate a drop insertion location.
     * <p>
     *  这是拖放操作的默认放置目标,如果开发人员不提供的话。
     *  <code> DropTarget </code>只支持一个<code> DropTargetListener </code>,如果未设置,则无法正常工作。
     * 这个类将一个监听器设置为drop处理到<code> TransferHandler </code>的链接,并且添加对一些<code> ComponentUI </code>实现安装以操纵一个插入插入位置
     * 的附加监听器的支持。
     *  <code> DropTarget </code>只支持一个<code> DropTargetListener </code>,如果未设置,则无法正常工作。
     * 
     */
    static class SwingDropTarget extends DropTarget implements UIResource {

        SwingDropTarget(Component c) {
            super(c, COPY_OR_MOVE | LINK, null);
            try {
                // addDropTargetListener is overridden
                // we specifically need to add to the superclass
                super.addDropTargetListener(getDropTargetListener());
            } catch (TooManyListenersException tmle) {}
        }

        public void addDropTargetListener(DropTargetListener dtl) throws TooManyListenersException {
            // Since the super class only supports one DropTargetListener,
            // and we add one from the constructor, we always add to the
            // extended list.
            if (listenerList == null) {
                listenerList = new EventListenerList();
            }
            listenerList.add(DropTargetListener.class, dtl);
        }

        public void removeDropTargetListener(DropTargetListener dtl) {
            if (listenerList != null) {
                listenerList.remove(DropTargetListener.class, dtl);
            }
        }

        // --- DropTargetListener methods (multicast) --------------------------

        public void dragEnter(DropTargetDragEvent e) {
            super.dragEnter(e);
            if (listenerList != null) {
                Object[] listeners = listenerList.getListenerList();
                for (int i = listeners.length-2; i>=0; i-=2) {
                    if (listeners[i]==DropTargetListener.class) {
                        ((DropTargetListener)listeners[i+1]).dragEnter(e);
                    }
                }
            }
        }

        public void dragOver(DropTargetDragEvent e) {
            super.dragOver(e);
            if (listenerList != null) {
                Object[] listeners = listenerList.getListenerList();
                for (int i = listeners.length-2; i>=0; i-=2) {
                    if (listeners[i]==DropTargetListener.class) {
                        ((DropTargetListener)listeners[i+1]).dragOver(e);
                    }
                }
            }
        }

        public void dragExit(DropTargetEvent e) {
            super.dragExit(e);
            if (listenerList != null) {
                Object[] listeners = listenerList.getListenerList();
                for (int i = listeners.length-2; i>=0; i-=2) {
                    if (listeners[i]==DropTargetListener.class) {
                        ((DropTargetListener)listeners[i+1]).dragExit(e);
                    }
                }
            }
            if (!isActive()) {
                // If the Drop target is inactive the dragExit will not be dispatched to the dtListener,
                // so make sure that we clean up the dtListener anyway.
                DropTargetListener dtListener = getDropTargetListener();
                    if (dtListener != null && dtListener instanceof DropHandler) {
                        ((DropHandler)dtListener).cleanup(false);
                    }
            }
        }

        public void drop(DropTargetDropEvent e) {
            super.drop(e);
            if (listenerList != null) {
                Object[] listeners = listenerList.getListenerList();
                for (int i = listeners.length-2; i>=0; i-=2) {
                    if (listeners[i]==DropTargetListener.class) {
                        ((DropTargetListener)listeners[i+1]).drop(e);
                    }
                }
            }
        }

        public void dropActionChanged(DropTargetDragEvent e) {
            super.dropActionChanged(e);
            if (listenerList != null) {
                Object[] listeners = listenerList.getListenerList();
                for (int i = listeners.length-2; i>=0; i-=2) {
                    if (listeners[i]==DropTargetListener.class) {
                        ((DropTargetListener)listeners[i+1]).dropActionChanged(e);
                    }
                }
            }
        }

        private EventListenerList listenerList;
    }

    private static class DropHandler implements DropTargetListener,
                                                Serializable,
                                                ActionListener {

        private Timer timer;
        private Point lastPosition;
        private Rectangle outer = new Rectangle();
        private Rectangle inner = new Rectangle();
        private int hysteresis = 10;

        private Component component;
        private Object state;
        private TransferSupport support =
            new TransferSupport(null, (DropTargetEvent)null);

        private static final int AUTOSCROLL_INSET = 10;

        /**
         * Update the geometry of the autoscroll region.  The geometry is
         * maintained as a pair of rectangles.  The region can cause
         * a scroll if the pointer sits inside it for the duration of the
         * timer.  The region that causes the timer countdown is the area
         * between the two rectangles.
         * <p>
         * This is implemented to use the visible area of the component
         * as the outer rectangle, and the insets are fixed at 10. Should
         * the component be smaller than a total of 20 in any direction,
         * autoscroll will not occur in that direction.
         * <p>
         *  更新自动滚动区域的几何。几何图形保持为一对矩形。如果指针在定时器的持续时间内在该区域内,则该区域可以导致滚动。导致定时器倒计时的区域是两个矩形之间的区域。
         * <p>
         *  这被实现为使用组件的可见区域作为外部矩形,并且插入固定为10.如果组件在任何方向上小于总共20个,则在该方向上不会发生自动滚动。
         * 
         */
        private void updateAutoscrollRegion(JComponent c) {
            // compute the outer
            Rectangle visible = c.getVisibleRect();
            outer.setBounds(visible.x, visible.y, visible.width, visible.height);

            // compute the insets
            Insets i = new Insets(0, 0, 0, 0);
            if (c instanceof Scrollable) {
                int minSize = 2 * AUTOSCROLL_INSET;

                if (visible.width >= minSize) {
                    i.left = i.right = AUTOSCROLL_INSET;
                }

                if (visible.height >= minSize) {
                    i.top = i.bottom = AUTOSCROLL_INSET;
                }
            }

            // set the inner from the insets
            inner.setBounds(visible.x + i.left,
                          visible.y + i.top,
                          visible.width - (i.left + i.right),
                          visible.height - (i.top  + i.bottom));
        }

        /**
         * Perform an autoscroll operation.  This is implemented to scroll by the
         * unit increment of the Scrollable using scrollRectToVisible.  If the
         * cursor is in a corner of the autoscroll region, more than one axis will
         * scroll.
         * <p>
         *  执行自动滚动操作。这是实现滚动Scrollable的单位增量使用scrollRectToVisible。如果光标位于自动滚动区域的一角,则会滚动多个轴。
         * 
         */
        private void autoscroll(JComponent c, Point pos) {
            if (c instanceof Scrollable) {
                Scrollable s = (Scrollable) c;
                if (pos.y < inner.y) {
                    // scroll upward
                    int dy = s.getScrollableUnitIncrement(outer, SwingConstants.VERTICAL, -1);
                    Rectangle r = new Rectangle(inner.x, outer.y - dy, inner.width, dy);
                    c.scrollRectToVisible(r);
                } else if (pos.y > (inner.y + inner.height)) {
                    // scroll downard
                    int dy = s.getScrollableUnitIncrement(outer, SwingConstants.VERTICAL, 1);
                    Rectangle r = new Rectangle(inner.x, outer.y + outer.height, inner.width, dy);
                    c.scrollRectToVisible(r);
                }

                if (pos.x < inner.x) {
                    // scroll left
                    int dx = s.getScrollableUnitIncrement(outer, SwingConstants.HORIZONTAL, -1);
                    Rectangle r = new Rectangle(outer.x - dx, inner.y, dx, inner.height);
                    c.scrollRectToVisible(r);
                } else if (pos.x > (inner.x + inner.width)) {
                    // scroll right
                    int dx = s.getScrollableUnitIncrement(outer, SwingConstants.HORIZONTAL, 1);
                    Rectangle r = new Rectangle(outer.x + outer.width, inner.y, dx, inner.height);
                    c.scrollRectToVisible(r);
                }
            }
        }

        /**
         * Initializes the internal properties if they haven't been already
         * inited. This is done lazily to avoid loading of desktop properties.
         * <p>
         *  初始化内部属性(如果尚未引入)。这是懒惰完成,以避免加载桌面属性。
         * 
         */
        private void initPropertiesIfNecessary() {
            if (timer == null) {
                Toolkit t = Toolkit.getDefaultToolkit();
                Integer prop;

                prop = (Integer)
                    t.getDesktopProperty("DnD.Autoscroll.interval");

                timer = new Timer(prop == null ? 100 : prop.intValue(), this);

                prop = (Integer)
                    t.getDesktopProperty("DnD.Autoscroll.initialDelay");

                timer.setInitialDelay(prop == null ? 100 : prop.intValue());

                prop = (Integer)
                    t.getDesktopProperty("DnD.Autoscroll.cursorHysteresis");

                if (prop != null) {
                    hysteresis = prop.intValue();
                }
            }
        }

        /**
         * The timer fired, perform autoscroll if the pointer is within the
         * autoscroll region.
         * <P>
         * <p>
         * 定时器触发,如果指针在自动滚动区域内,则执行自动滚动。
         * <P>
         * 
         * @param e the <code>ActionEvent</code>
         */
        public void actionPerformed(ActionEvent e) {
            updateAutoscrollRegion((JComponent)component);
            if (outer.contains(lastPosition) && !inner.contains(lastPosition)) {
                autoscroll((JComponent)component, lastPosition);
            }
        }

        // --- DropTargetListener methods -----------------------------------

        private void setComponentDropLocation(TransferSupport support,
                                              boolean forDrop) {

            DropLocation dropLocation = (support == null)
                                        ? null
                                        : support.getDropLocation();

            if (SunToolkit.isInstanceOf(component, "javax.swing.text.JTextComponent")) {
                state = SwingAccessor.getJTextComponentAccessor().
                            setDropLocation((JTextComponent)component, dropLocation, state, forDrop);
            } else if (component instanceof JComponent) {
                state = ((JComponent)component).setDropLocation(dropLocation, state, forDrop);
            }
        }

        private void handleDrag(DropTargetDragEvent e) {
            TransferHandler importer =
                ((HasGetTransferHandler)component).getTransferHandler();

            if (importer == null) {
                e.rejectDrag();
                setComponentDropLocation(null, false);
                return;
            }

            support.setDNDVariables(component, e);
            boolean canImport = importer.canImport(support);

            if (canImport) {
                e.acceptDrag(support.getDropAction());
            } else {
                e.rejectDrag();
            }

            boolean showLocation = support.showDropLocationIsSet ?
                                   support.showDropLocation :
                                   canImport;

            setComponentDropLocation(showLocation ? support : null, false);
        }

        public void dragEnter(DropTargetDragEvent e) {
            state = null;
            component = e.getDropTargetContext().getComponent();

            handleDrag(e);

            if (component instanceof JComponent) {
                lastPosition = e.getLocation();
                updateAutoscrollRegion((JComponent)component);
                initPropertiesIfNecessary();
            }
        }

        public void dragOver(DropTargetDragEvent e) {
            handleDrag(e);

            if (!(component instanceof JComponent)) {
                return;
            }

            Point p = e.getLocation();

            if (Math.abs(p.x - lastPosition.x) > hysteresis
                    || Math.abs(p.y - lastPosition.y) > hysteresis) {
                // no autoscroll
                if (timer.isRunning()) timer.stop();
            } else {
                if (!timer.isRunning()) timer.start();
            }

            lastPosition = p;
        }

        public void dragExit(DropTargetEvent e) {
            cleanup(false);
        }

        public void drop(DropTargetDropEvent e) {
            TransferHandler importer =
                ((HasGetTransferHandler)component).getTransferHandler();

            if (importer == null) {
                e.rejectDrop();
                cleanup(false);
                return;
            }

            support.setDNDVariables(component, e);
            boolean canImport = importer.canImport(support);

            if (canImport) {
                e.acceptDrop(support.getDropAction());

                boolean showLocation = support.showDropLocationIsSet ?
                                       support.showDropLocation :
                                       canImport;

                setComponentDropLocation(showLocation ? support : null, false);

                boolean success;

                try {
                    success = importer.importData(support);
                } catch (RuntimeException re) {
                    success = false;
                }

                e.dropComplete(success);
                cleanup(success);
            } else {
                e.rejectDrop();
                cleanup(false);
            }
        }

        public void dropActionChanged(DropTargetDragEvent e) {
            /*
             * Work-around for Linux bug where dropActionChanged
             * is called before dragEnter.
             * <p>
             *  解决方法：在dragEnter之前调用dropActionChanged。
             * 
             */
            if (component == null) {
                return;
            }

            handleDrag(e);
        }

        private void cleanup(boolean forDrop) {
            setComponentDropLocation(null, forDrop);
            if (component instanceof JComponent) {
                ((JComponent)component).dndDone();
            }

            if (timer != null) {
                timer.stop();
            }

            state = null;
            component = null;
            lastPosition = null;
        }
    }

    /**
     * This is the default drag handler for drag and drop operations that
     * use the <code>TransferHandler</code>.
     * <p>
     *  这是使用<code> TransferHandler </code>的拖放操作的默认拖动处理程序。
     * 
     */
    private static class DragHandler implements DragGestureListener, DragSourceListener {

        private boolean scrolls;

        // --- DragGestureListener methods -----------------------------------

        /**
         * a Drag gesture has been recognized
         * <p>
         *  已识别拖动手势
         * 
         */
        public void dragGestureRecognized(DragGestureEvent dge) {
            JComponent c = (JComponent) dge.getComponent();
            TransferHandler th = c.getTransferHandler();
            Transferable t = th.createTransferable(c);
            if (t != null) {
                scrolls = c.getAutoscrolls();
                c.setAutoscrolls(false);
                try {
                    Image im = th.getDragImage();
                    if (im == null) {
                        dge.startDrag(null, t, this);
                    } else {
                        dge.startDrag(null, im, th.getDragImageOffset(), t, this);
                    }
                    return;
                } catch (RuntimeException re) {
                    c.setAutoscrolls(scrolls);
                }
            }

            th.exportDone(c, t, NONE);
        }

        // --- DragSourceListener methods -----------------------------------

        /**
         * as the hotspot enters a platform dependent drop site
         * <p>
         *  因为热点进入平台依赖卸载站点
         * 
         */
        public void dragEnter(DragSourceDragEvent dsde) {
        }

        /**
         * as the hotspot moves over a platform dependent drop site
         * <p>
         *  因为热点在平台依赖下降站点上移动
         * 
         */
        public void dragOver(DragSourceDragEvent dsde) {
        }

        /**
         * as the hotspot exits a platform dependent drop site
         * <p>
         *  因为热点退出平台依赖卸载站点
         * 
         */
        public void dragExit(DragSourceEvent dsde) {
        }

        /**
         * as the operation completes
         * <p>
         *  作为操作完成
         * 
         */
        public void dragDropEnd(DragSourceDropEvent dsde) {
            DragSourceContext dsc = dsde.getDragSourceContext();
            JComponent c = (JComponent)dsc.getComponent();
            if (dsde.getDropSuccess()) {
                c.getTransferHandler().exportDone(c, dsc.getTransferable(), dsde.getDropAction());
            } else {
                c.getTransferHandler().exportDone(c, dsc.getTransferable(), NONE);
            }
            c.setAutoscrolls(scrolls);
        }

        public void dropActionChanged(DragSourceDragEvent dsde) {
        }
    }

    private static class SwingDragGestureRecognizer extends DragGestureRecognizer {

        SwingDragGestureRecognizer(DragGestureListener dgl) {
            super(DragSource.getDefaultDragSource(), null, NONE, dgl);
        }

        void gestured(JComponent c, MouseEvent e, int srcActions, int action) {
            setComponent(c);
            setSourceActions(srcActions);
            appendEvent(e);
            fireDragGestureRecognized(action, e.getPoint());
        }

        /**
         * register this DragGestureRecognizer's Listeners with the Component
         * <p>
         *  使用Component注册此DragGestureRecognizer的Listeners
         * 
         */
        protected void registerListeners() {
        }

        /**
         * unregister this DragGestureRecognizer's Listeners with the Component
         *
         * subclasses must override this method
         * <p>
         *  请使用Component取消注册此DragGestureRecognizer的侦听器
         * 
         *  子类必须重写此方法
         * 
         */
        protected void unregisterListeners() {
        }

    }

    static final Action cutAction = new TransferAction("cut");
    static final Action copyAction = new TransferAction("copy");
    static final Action pasteAction = new TransferAction("paste");

    static class TransferAction extends UIAction implements UIResource {

        TransferAction(String name) {
            super(name);
        }

        public boolean isEnabled(Object sender) {
            if (sender instanceof JComponent
                && ((JComponent)sender).getTransferHandler() == null) {
                    return false;
            }

            return true;
        }

        private static final JavaSecurityAccess javaSecurityAccess =
            SharedSecrets.getJavaSecurityAccess();

        public void actionPerformed(final ActionEvent e) {
            final Object src = e.getSource();

            final PrivilegedAction<Void> action = new PrivilegedAction<Void>() {
                public Void run() {
                    actionPerformedImpl(e);
                    return null;
                }
            };

            final AccessControlContext stack = AccessController.getContext();
            final AccessControlContext srcAcc = AWTAccessor.getComponentAccessor().getAccessControlContext((Component)src);
            final AccessControlContext eventAcc = AWTAccessor.getAWTEventAccessor().getAccessControlContext(e);

                if (srcAcc == null) {
                    javaSecurityAccess.doIntersectionPrivilege(action, stack, eventAcc);
                } else {
                    javaSecurityAccess.doIntersectionPrivilege(
                        new PrivilegedAction<Void>() {
                            public Void run() {
                                javaSecurityAccess.doIntersectionPrivilege(action, eventAcc);
                                return null;
                             }
                    }, stack, srcAcc);
                }
        }

        private void actionPerformedImpl(ActionEvent e) {
            Object src = e.getSource();
            if (src instanceof JComponent) {
                JComponent c = (JComponent) src;
                TransferHandler th = c.getTransferHandler();
                Clipboard clipboard = getClipboard(c);
                String name = (String) getValue(Action.NAME);

                Transferable trans = null;

                // any of these calls may throw IllegalStateException
                try {
                    if ((clipboard != null) && (th != null) && (name != null)) {
                        if ("cut".equals(name)) {
                            th.exportToClipboard(c, clipboard, MOVE);
                        } else if ("copy".equals(name)) {
                            th.exportToClipboard(c, clipboard, COPY);
                        } else if ("paste".equals(name)) {
                            trans = clipboard.getContents(null);
                        }
                    }
                } catch (IllegalStateException ise) {
                    // clipboard was unavailable
                    UIManager.getLookAndFeel().provideErrorFeedback(c);
                    return;
                }

                // this is a paste action, import data into the component
                if (trans != null) {
                    th.importData(new TransferSupport(c, trans));
                }
            }
        }

        /**
         * Returns the clipboard to use for cut/copy/paste.
         * <p>
         *  返回用于剪切/复制/粘贴的剪贴板。
         * 
         */
        private Clipboard getClipboard(JComponent c) {
            if (SwingUtilities2.canAccessSystemClipboard()) {
                return c.getToolkit().getSystemClipboard();
            }
            Clipboard clipboard = (Clipboard)sun.awt.AppContext.getAppContext().
                get(SandboxClipboardKey);
            if (clipboard == null) {
                clipboard = new Clipboard("Sandboxed Component Clipboard");
                sun.awt.AppContext.getAppContext().put(SandboxClipboardKey,
                                                       clipboard);
            }
            return clipboard;
        }

        /**
         * Key used in app context to lookup Clipboard to use if access to
         * System clipboard is denied.
         * <p>
         *  在应用程序上下文中使用的键,用于查看剪贴板在拒绝访问系统剪贴板时使用。
         */
        private static Object SandboxClipboardKey = new Object();

    }

}
