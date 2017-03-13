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

package java.awt.dnd;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.FlavorMap;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.util.EventListener;
import sun.awt.dnd.SunDragSourceContextPeer;
import sun.security.action.GetIntegerAction;


/**
 * The <code>DragSource</code> is the entity responsible
 * for the initiation of the Drag
 * and Drop operation, and may be used in a number of scenarios:
 * <UL>
 * <LI>1 default instance per JVM for the lifetime of that JVM.
 * <LI>1 instance per class of potential Drag Initiator object (e.g
 * TextField). [implementation dependent]
 * <LI>1 per instance of a particular
 * <code>Component</code>, or application specific
 * object associated with a <code>Component</code>
 * instance in the GUI. [implementation dependent]
 * <LI>Some other arbitrary association. [implementation dependent]
 *</UL>
 *
 * Once the <code>DragSource</code> is
 * obtained, a <code>DragGestureRecognizer</code> should
 * also be obtained to associate the <code>DragSource</code>
 * with a particular
 * <code>Component</code>.
 * <P>
 * The initial interpretation of the user's gesture,
 * and the subsequent starting of the drag operation
 * are the responsibility of the implementing
 * <code>Component</code>, which is usually
 * implemented by a <code>DragGestureRecognizer</code>.
 *<P>
 * When a drag gesture occurs, the
 * <code>DragSource</code>'s
 * startDrag() method shall be
 * invoked in order to cause processing
 * of the user's navigational
 * gestures and delivery of Drag and Drop
 * protocol notifications. A
 * <code>DragSource</code> shall only
 * permit a single Drag and Drop operation to be
 * current at any one time, and shall
 * reject any further startDrag() requests
 * by throwing an <code>IllegalDnDOperationException</code>
 * until such time as the extant operation is complete.
 * <P>
 * The startDrag() method invokes the
 * createDragSourceContext() method to
 * instantiate an appropriate
 * <code>DragSourceContext</code>
 * and associate the <code>DragSourceContextPeer</code>
 * with that.
 * <P>
 * If the Drag and Drop System is
 * unable to initiate a drag operation for
 * some reason, the startDrag() method throws
 * a <code>java.awt.dnd.InvalidDnDOperationException</code>
 * to signal such a condition. Typically this
 * exception is thrown when the underlying platform
 * system is either not in a state to
 * initiate a drag, or the parameters specified are invalid.
 * <P>
 * Note that during the drag, the
 * set of operations exposed by the source
 * at the start of the drag operation may not change
 * until the operation is complete.
 * The operation(s) are constant for the
 * duration of the operation with respect to the
 * <code>DragSource</code>.
 *
 * <p>
 *  <code> DragSource </code>是负责启动拖放操作的实体,可以在多种情况下使用：
 * <UL>
 *  <LI>每个JVM的1个默认实例对于该JVM的生存期<LI> 1每个潜在的类的实例Drag Initiator对象(例如TextField)[实现相关] <LI>每个特定<code>组件</> >或与
 * GUI中的<code> Component </code>实例相关联的应用程序特定对象[取决于实现] <LI>某些其他任意关联[取决于实现]。
 * /UL>
 * 
 * 一旦获得<code> DragSource </code>,还应当获得<code> DragGestureRecognizer </code>以将<code> DragSource </code>与特定
 * <code> Component </code>。
 * <P>
 *  对用户手势的初始解释以及随后启动拖动操作是实现<code> Component </code>的责任,其通常由<code> DragGestureRecognizer </code>
 * P>
 * 当拖动手势发生时,将调用<code> DragSource </code>的startDrag()方法,以便引起用户的导航手势的处理和拖放协议通知的传递</code> DragSource </code >
 * 只允许单个拖放操作在任何一个时间是当前的,并且将通过抛出一个<code> IllegalDnDOperationException </code>来拒绝任何进一步的startDrag()请求,直到现存操
 * 作完成。
 * <P>
 *  startDrag()方法调用createDragSourceContext()方法来实例化一个适当的<code> DragSourceContext </code>,并将<code> DragSou
 * rceContextPeer </code>。
 * <P>
 * 如果拖放系统由于某种原因无法启动拖动操作,则startDrag()方法会抛出一个<code> javaawtdndInvalidDnDOperationException </code>来表示这种情况。
 * 通常,当底层平台系统不是在启动拖动的状态下,或指定的参数无效。
 * <P>
 *  注意,在拖动期间,在拖动操作开始时由源公开的操作集合可以不改变,直到操作完成。操作在关于<code> DragSource的操作期间是恒定的</code>
 * 
 * 
 * @since 1.2
 */

public class DragSource implements Serializable {

    private static final long serialVersionUID = 6236096958971414066L;

    /*
     * load a system default cursor
     * <p>
     *  加载系统默认游标
     * 
     */

    private static Cursor load(String name) {
        if (GraphicsEnvironment.isHeadless()) {
            return null;
        }

        try {
            return (Cursor)Toolkit.getDefaultToolkit().getDesktopProperty(name);
        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("failed to load system cursor: " + name + " : " + e.getMessage());
        }
    }


    /**
     * The default <code>Cursor</code> to use with a copy operation indicating
     * that a drop is currently allowed. <code>null</code> if
     * <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>.
     *
     * <p>
     * 如果<code> GraphicsEnvironmentisHeadless()</code>返回<code> true </code>,则</code>用于指示当前允许删除的复制操作的默认<code>
     *  Cursor </code> >。
     * 
     * 
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static final Cursor DefaultCopyDrop =
        load("DnD.Cursor.CopyDrop");

    /**
     * The default <code>Cursor</code> to use with a move operation indicating
     * that a drop is currently allowed. <code>null</code> if
     * <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>.
     *
     * <p>
     *  如果<code> GraphicsEnvironmentisHeadless()</code>返回<code> true </code>,则</code>用于指示当前允许删除的移动操作的默认<code>
     *  Cursor </code> >。
     * 
     * 
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static final Cursor DefaultMoveDrop =
        load("DnD.Cursor.MoveDrop");

    /**
     * The default <code>Cursor</code> to use with a link operation indicating
     * that a drop is currently allowed. <code>null</code> if
     * <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>.
     *
     * <p>
     *  默认<code> Cursor </code>用于链接操作,指示当前允许删除<code> null </code>如果<code> GraphicsEnvironmentisHeadless()</code>
     * 返回<code> true </code >。
     * 
     * 
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static final Cursor DefaultLinkDrop =
        load("DnD.Cursor.LinkDrop");

    /**
     * The default <code>Cursor</code> to use with a copy operation indicating
     * that a drop is currently not allowed. <code>null</code> if
     * <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>.
     *
     * <p>
     * 如果<code> GraphicsEnvironmentisHeadless()</code>返回<code> true </code>,则默认<code> Cursor </code>用于指示当前不允
     * 许删除的复制操作<代码>。
     * 
     * 
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static final Cursor DefaultCopyNoDrop =
        load("DnD.Cursor.CopyNoDrop");

    /**
     * The default <code>Cursor</code> to use with a move operation indicating
     * that a drop is currently not allowed. <code>null</code> if
     * <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>.
     *
     * <p>
     *  如果<code> GraphicsEnvironmentisHeadless()</code>返回<code> true </code>,则默认<code> Cursor </code>用于指示当前不
     * 允许删除的移动操作<代码>。
     * 
     * 
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static final Cursor DefaultMoveNoDrop =
        load("DnD.Cursor.MoveNoDrop");

    /**
     * The default <code>Cursor</code> to use with a link operation indicating
     * that a drop is currently not allowed. <code>null</code> if
     * <code>GraphicsEnvironment.isHeadless()</code> returns <code>true</code>.
     *
     * <p>
     *  默认<code> Cursor </code>用于链接操作,指示当前不允许删除<code> null </code>如果<code> GraphicsEnvironmentisHeadless()</code>
     * 返回<code> true <代码>。
     * 
     * 
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static final Cursor DefaultLinkNoDrop =
        load("DnD.Cursor.LinkNoDrop");

    private static final DragSource dflt =
        (GraphicsEnvironment.isHeadless()) ? null : new DragSource();

    /**
     * Internal constants for serialization.
     * <p>
     *  序列化的内部常量
     * 
     */
    static final String dragSourceListenerK = "dragSourceL";
    static final String dragSourceMotionListenerK = "dragSourceMotionL";

    /**
     * Gets the <code>DragSource</code> object associated with
     * the underlying platform.
     *
     * <p>
     *  获取与底层平台相关联的<code> DragSource </code>对象
     * 
     * 
     * @return the platform DragSource
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     *            returns true
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public static DragSource getDefaultDragSource() {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        } else {
            return dflt;
        }
    }

    /**
     * Reports
     * whether or not drag
     * <code>Image</code> support
     * is available on the underlying platform.
     * <P>
     * <p>
     * 报告是否在底层平台上拖动<code> Image </code>支持
     * <P>
     * 
     * @return if the Drag Image support is available on this platform
     */

    public static boolean isDragImageSupported() {
        Toolkit t = Toolkit.getDefaultToolkit();

        Boolean supported;

        try {
            supported = (Boolean)Toolkit.getDefaultToolkit().getDesktopProperty("DnD.isDragImageSupported");

            return supported.booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Creates a new <code>DragSource</code>.
     *
     * <p>
     *  创建新的<code> DragSource </code>
     * 
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     *            returns true
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public DragSource() throws HeadlessException {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException();
        }
    }

    /**
     * Start a drag, given the <code>DragGestureEvent</code>
     * that initiated the drag, the initial
     * <code>Cursor</code> to use,
     * the <code>Image</code> to drag,
     * the offset of the <code>Image</code> origin
     * from the hotspot of the <code>Cursor</code> at
     * the instant of the trigger,
     * the <code>Transferable</code> subject data
     * of the drag, the <code>DragSourceListener</code>,
     * and the <code>FlavorMap</code>.
     * <P>
     * <p>
     *  开始拖动,给定启动拖动的<code> DragGestureEvent </code>,使用初始<code> Cursor </code>,<code> Image </code>拖动<code> >
     *  </code>主体数据</code>拖动的<code>主体数据</code>中的<code> DragSourceListener </code> >和<code> FlavorMap </code>
     * 。
     * <P>
     * 
     * @param trigger        the <code>DragGestureEvent</code> that initiated the drag
     * @param dragCursor     the initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a>
     *                       for more details on the cursor handling mechanism during drag and drop
     * @param dragImage      the image to drag or {@code null}
     * @param imageOffset    the offset of the <code>Image</code> origin from the hotspot
     *                       of the <code>Cursor</code> at the instant of the trigger
     * @param transferable   the subject data of the drag
     * @param dsl            the <code>DragSourceListener</code>
     * @param flavorMap      the <code>FlavorMap</code> to use, or <code>null</code>
     * <P>
     * @throws java.awt.dnd.InvalidDnDOperationException
     *    if the Drag and Drop
     *    system is unable to initiate a drag operation, or if the user
     *    attempts to start a drag while an existing drag operation
     *    is still executing
     */

    public void startDrag(DragGestureEvent   trigger,
                          Cursor             dragCursor,
                          Image              dragImage,
                          Point              imageOffset,
                          Transferable       transferable,
                          DragSourceListener dsl,
                          FlavorMap          flavorMap) throws InvalidDnDOperationException {

        SunDragSourceContextPeer.setDragDropInProgress(true);

        try {
            if (flavorMap != null) this.flavorMap = flavorMap;

            DragSourceContextPeer dscp = Toolkit.getDefaultToolkit().createDragSourceContextPeer(trigger);

            DragSourceContext     dsc = createDragSourceContext(dscp,
                                                                trigger,
                                                                dragCursor,
                                                                dragImage,
                                                                imageOffset,
                                                                transferable,
                                                                dsl
                                                                );

            if (dsc == null) {
                throw new InvalidDnDOperationException();
            }

            dscp.startDrag(dsc, dsc.getCursor(), dragImage, imageOffset); // may throw
        } catch (RuntimeException e) {
            SunDragSourceContextPeer.setDragDropInProgress(false);
            throw e;
        }
    }

    /**
     * Start a drag, given the <code>DragGestureEvent</code>
     * that initiated the drag, the initial
     * <code>Cursor</code> to use,
     * the <code>Transferable</code> subject data
     * of the drag, the <code>DragSourceListener</code>,
     * and the <code>FlavorMap</code>.
     * <P>
     * <p>
     *  开始拖动,给出启动拖动的<code> DragGestureEvent </code>,使用的初始<code> Cursor </code>,拖动的<code>可转移</code>主题数据,<code >
     *  DragSourceListener </code>和<code> FlavorMap </code>。
     * <P>
     * 
     * @param trigger        the <code>DragGestureEvent</code> that
     * initiated the drag
     * @param dragCursor     the initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a>
     *                       for more details on the cursor handling mechanism during drag and drop
     * @param transferable   the subject data of the drag
     * @param dsl            the <code>DragSourceListener</code>
     * @param flavorMap      the <code>FlavorMap</code> to use or <code>null</code>
     * <P>
     * @throws java.awt.dnd.InvalidDnDOperationException
     *    if the Drag and Drop
     *    system is unable to initiate a drag operation, or if the user
     *    attempts to start a drag while an existing drag operation
     *    is still executing
     */

    public void startDrag(DragGestureEvent   trigger,
                          Cursor             dragCursor,
                          Transferable       transferable,
                          DragSourceListener dsl,
                          FlavorMap          flavorMap) throws InvalidDnDOperationException {
        startDrag(trigger, dragCursor, null, null, transferable, dsl, flavorMap);
    }

    /**
     * Start a drag, given the <code>DragGestureEvent</code>
     * that initiated the drag, the initial <code>Cursor</code>
     * to use,
     * the <code>Image</code> to drag,
     * the offset of the <code>Image</code> origin
     * from the hotspot of the <code>Cursor</code>
     * at the instant of the trigger,
     * the subject data of the drag, and
     * the <code>DragSourceListener</code>.
     * <P>
     * <p>
     * 开始拖动,给定启动拖动的<code> DragGestureEvent </code>,使用初始<code> Cursor </code>,<code> Image </code>拖动<code> >图
     * 像</code>源于触发时刻的<code> Cursor </code>的热点,拖动的主题数据和<code> DragSourceListener </code>。
     * <P>
     * 
     * @param trigger           the <code>DragGestureEvent</code> that initiated the drag
     * @param dragCursor     the initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a>
     *                       for more details on the cursor handling mechanism during drag and drop
     * @param dragImage         the <code>Image</code> to drag or <code>null</code>
     * @param dragOffset        the offset of the <code>Image</code> origin from the hotspot
     *                          of the <code>Cursor</code> at the instant of the trigger
     * @param transferable      the subject data of the drag
     * @param dsl               the <code>DragSourceListener</code>
     * <P>
     * @throws java.awt.dnd.InvalidDnDOperationException
     *    if the Drag and Drop
     *    system is unable to initiate a drag operation, or if the user
     *    attempts to start a drag while an existing drag operation
     *    is still executing
     */

    public void startDrag(DragGestureEvent   trigger,
                          Cursor             dragCursor,
                          Image              dragImage,
                          Point              dragOffset,
                          Transferable       transferable,
                          DragSourceListener dsl) throws InvalidDnDOperationException {
        startDrag(trigger, dragCursor, dragImage, dragOffset, transferable, dsl, null);
    }

    /**
     * Start a drag, given the <code>DragGestureEvent</code>
     * that initiated the drag, the initial
     * <code>Cursor</code> to
     * use,
     * the <code>Transferable</code> subject data
     * of the drag, and the <code>DragSourceListener</code>.
     * <P>
     * <p>
     *  开始拖动,给定启动拖动的<code> DragGestureEvent </code>,使用的初始<code> Cursor </code>,拖动的<code>可转移</code>主题数据,代码> D
     * ragSourceListener </code>。
     * <P>
     * 
     * @param trigger           the <code>DragGestureEvent</code> that initiated the drag
     * @param dragCursor     the initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a> class
     *                       for more details on the cursor handling mechanism during drag and drop
     * @param transferable      the subject data of the drag
     * @param dsl               the <code>DragSourceListener</code>
     * <P>
     * @throws java.awt.dnd.InvalidDnDOperationException
     *    if the Drag and Drop
     *    system is unable to initiate a drag operation, or if the user
     *    attempts to start a drag while an existing drag operation
     *    is still executing
     */

    public void startDrag(DragGestureEvent   trigger,
                          Cursor             dragCursor,
                          Transferable       transferable,
                          DragSourceListener dsl) throws InvalidDnDOperationException {
        startDrag(trigger, dragCursor, null, null, transferable, dsl, null);
    }

    /**
     * Creates the {@code DragSourceContext} to handle the current drag
     * operation.
     * <p>
     * To incorporate a new <code>DragSourceContext</code>
     * subclass, subclass <code>DragSource</code> and
     * override this method.
     * <p>
     * If <code>dragImage</code> is <code>null</code>, no image is used
     * to represent the drag over feedback for this drag operation, but
     * <code>NullPointerException</code> is not thrown.
     * <p>
     * If <code>dsl</code> is <code>null</code>, no drag source listener
     * is registered with the created <code>DragSourceContext</code>,
     * but <code>NullPointerException</code> is not thrown.
     *
     * <p>
     *  创建{@code DragSourceContext}以处理当前的拖动操作
     * <p>
     *  要合并一个新的<code> DragSourceContext </code>子类,子类<code> DragSource </code>并覆盖此方法
     * <p>
     * 如果<code> dragImage </code>是<code> null </code>,则不使用图像来表示对此拖动操作的反馈的拖动,但不会抛出<code> NullPointerException
     *  </code>。
     * <p>
     *  如果<code> dsl </code>是<code> null </code>,则不会向创建的<code> DragSourceContext </code>注册拖放源侦听器,但不会抛出<code>
     *  NullPointerException </code>。
     * 
     * 
     * @param dscp          The <code>DragSourceContextPeer</code> for this drag
     * @param dgl           The <code>DragGestureEvent</code> that triggered the
     *                      drag
     * @param dragCursor     The initial {@code Cursor} for this drag operation
     *                       or {@code null} for the default cursor handling;
     *                       see <a href="DragSourceContext.html#defaultCursor">DragSourceContext</a> class
     *                       for more details on the cursor handling mechanism during drag and drop
     * @param dragImage     The <code>Image</code> to drag or <code>null</code>
     * @param imageOffset   The offset of the <code>Image</code> origin from the
     *                      hotspot of the cursor at the instant of the trigger
     * @param t             The subject data of the drag
     * @param dsl           The <code>DragSourceListener</code>
     *
     * @return the <code>DragSourceContext</code>
     *
     * @throws NullPointerException if <code>dscp</code> is <code>null</code>
     * @throws NullPointerException if <code>dgl</code> is <code>null</code>
     * @throws NullPointerException if <code>dragImage</code> is not
     *    <code>null</code> and <code>imageOffset</code> is <code>null</code>
     * @throws NullPointerException if <code>t</code> is <code>null</code>
     * @throws IllegalArgumentException if the <code>Component</code>
     *         associated with the trigger event is <code>null</code>.
     * @throws IllegalArgumentException if the <code>DragSource</code> for the
     *         trigger event is <code>null</code>.
     * @throws IllegalArgumentException if the drag action for the
     *         trigger event is <code>DnDConstants.ACTION_NONE</code>.
     * @throws IllegalArgumentException if the source actions for the
     *         <code>DragGestureRecognizer</code> associated with the trigger
     *         event are equal to <code>DnDConstants.ACTION_NONE</code>.
     */

    protected DragSourceContext createDragSourceContext(DragSourceContextPeer dscp, DragGestureEvent dgl, Cursor dragCursor, Image dragImage, Point imageOffset, Transferable t, DragSourceListener dsl) {
        return new DragSourceContext(dscp, dgl, dragCursor, dragImage, imageOffset, t, dsl);
    }

    /**
     * This method returns the
     * <code>FlavorMap</code> for this <code>DragSource</code>.
     * <P>
     * <p>
     *  此方法为此<code> DragSource </code>返回<code> FlavorMap </code>
     * <P>
     * 
     * @return the <code>FlavorMap</code> for this <code>DragSource</code>
     */

    public FlavorMap getFlavorMap() { return flavorMap; }

    /**
     * Creates a new <code>DragGestureRecognizer</code>
     * that implements the specified
     * abstract subclass of
     * <code>DragGestureRecognizer</code>, and
     * sets the specified <code>Component</code>
     * and <code>DragGestureListener</code> on
     * the newly created object.
     * <P>
     * <p>
     *  创建一个实现<code> DragGestureRecognizer </code>的指定抽象子类的新的<code> DragGestureRecognizer </code>,并设置指定的<code>
     *  Component </code>和<code> DragGestureListener </code>新创建的对象。
     * <P>
     * 
     * @param recognizerAbstractClass the requested abstract type
     * @param actions                 the permitted source drag actions
     * @param c                       the <code>Component</code> target
     * @param dgl        the <code>DragGestureListener</code> to notify
     * <P>
     * @return the new <code>DragGestureRecognizer</code> or <code>null</code>
     *    if the <code>Toolkit.createDragGestureRecognizer</code> method
     *    has no implementation available for
     *    the requested <code>DragGestureRecognizer</code>
     *    subclass and returns <code>null</code>
     */

    public <T extends DragGestureRecognizer> T
        createDragGestureRecognizer(Class<T> recognizerAbstractClass,
                                    Component c, int actions,
                                    DragGestureListener dgl)
    {
        return Toolkit.getDefaultToolkit().createDragGestureRecognizer(recognizerAbstractClass, this, c, actions, dgl);
    }


    /**
     * Creates a new <code>DragGestureRecognizer</code>
     * that implements the default
     * abstract subclass of <code>DragGestureRecognizer</code>
     * for this <code>DragSource</code>,
     * and sets the specified <code>Component</code>
     * and <code>DragGestureListener</code> on the
     * newly created object.
     *
     * For this <code>DragSource</code>
     * the default is <code>MouseDragGestureRecognizer</code>.
     * <P>
     * <p>
     * 为此<code> DragSource </code>创建实现<code> DragGestureRecognizer </code>的默认抽象子类的新的<code> DragGestureRecogn
     * izer </code>,并设置指定的<code> Component </code> <code> DragGestureListener </code>对新创建的对象。
     * 
     *  对于这个<code> DragSource </code>,默认是<code> MouseDragGestureRecognizer </code>
     * <P>
     * 
     * @param c       the <code>Component</code> target for the recognizer
     * @param actions the permitted source actions
     * @param dgl     the <code>DragGestureListener</code> to notify
     * <P>
     * @return the new <code>DragGestureRecognizer</code> or <code>null</code>
     *    if the <code>Toolkit.createDragGestureRecognizer</code> method
     *    has no implementation available for
     *    the requested <code>DragGestureRecognizer</code>
     *    subclass and returns <code>null</code>
     */

    public DragGestureRecognizer createDefaultDragGestureRecognizer(Component c, int actions, DragGestureListener dgl) {
        return Toolkit.getDefaultToolkit().createDragGestureRecognizer(MouseDragGestureRecognizer.class, this, c, actions, dgl);
    }

    /**
     * Adds the specified <code>DragSourceListener</code> to this
     * <code>DragSource</code> to receive drag source events during drag
     * operations intiated with this <code>DragSource</code>.
     * If a <code>null</code> listener is specified, no action is taken and no
     * exception is thrown.
     *
     * <p>
     *  在拖动操作期间,如果<code> DragSource </code>,则向<code> DragSource </code>添加指定的<code> DragSourceListener </code>
     * 监听器被指定,不采取任何行动,并且不抛出异常。
     * 
     * 
     * @param dsl the <code>DragSourceListener</code> to add
     *
     * @see      #removeDragSourceListener
     * @see      #getDragSourceListeners
     * @since 1.4
     */
    public void addDragSourceListener(DragSourceListener dsl) {
        if (dsl != null) {
            synchronized (this) {
                listener = DnDEventMulticaster.add(listener, dsl);
            }
        }
    }

    /**
     * Removes the specified <code>DragSourceListener</code> from this
     * <code>DragSource</code>.
     * If a <code>null</code> listener is specified, no action is taken and no
     * exception is thrown.
     * If the listener specified by the argument was not previously added to
     * this <code>DragSource</code>, no action is taken and no exception
     * is thrown.
     *
     * <p>
     * 从此<code> DragSource </code>中删除指定的<code> DragSourceListener </code>如果指定了<code> null </code>侦听器,则不会执行任何
     * 操作,也不会抛出异常如果由参数先前未添加到此<code> DragSource </code>,不会执行任何操作,也不会抛出异常。
     * 
     * 
     * @param dsl the <code>DragSourceListener</code> to remove
     *
     * @see      #addDragSourceListener
     * @see      #getDragSourceListeners
     * @since 1.4
     */
    public void removeDragSourceListener(DragSourceListener dsl) {
        if (dsl != null) {
            synchronized (this) {
                listener = DnDEventMulticaster.remove(listener, dsl);
            }
        }
    }

    /**
     * Gets all the <code>DragSourceListener</code>s
     * registered with this <code>DragSource</code>.
     *
     * <p>
     *  获取向此<code> DragSource </code>注册的所有<code> DragSourceListener </code>
     * 
     * 
     * @return all of this <code>DragSource</code>'s
     *         <code>DragSourceListener</code>s or an empty array if no
     *         such listeners are currently registered
     *
     * @see      #addDragSourceListener
     * @see      #removeDragSourceListener
     * @since    1.4
     */
    public DragSourceListener[] getDragSourceListeners() {
        return getListeners(DragSourceListener.class);
    }

    /**
     * Adds the specified <code>DragSourceMotionListener</code> to this
     * <code>DragSource</code> to receive drag motion events during drag
     * operations intiated with this <code>DragSource</code>.
     * If a <code>null</code> listener is specified, no action is taken and no
     * exception is thrown.
     *
     * <p>
     *  在拖动操作期间,为此<code> DragSource </code>添加指定的<code> DragSourceMotionListener </code>,以接收拖动事件如果<code> null
     *  </code>监听器被指定,不采取任何行动,并且不抛出异常。
     * 
     * 
     * @param dsml the <code>DragSourceMotionListener</code> to add
     *
     * @see      #removeDragSourceMotionListener
     * @see      #getDragSourceMotionListeners
     * @since 1.4
     */
    public void addDragSourceMotionListener(DragSourceMotionListener dsml) {
        if (dsml != null) {
            synchronized (this) {
                motionListener = DnDEventMulticaster.add(motionListener, dsml);
            }
        }
    }

    /**
     * Removes the specified <code>DragSourceMotionListener</code> from this
     * <code>DragSource</code>.
     * If a <code>null</code> listener is specified, no action is taken and no
     * exception is thrown.
     * If the listener specified by the argument was not previously added to
     * this <code>DragSource</code>, no action is taken and no exception
     * is thrown.
     *
     * <p>
     * 从此<code> DragSource </code>中删除指定的<code> DragSourceMotionListener </code>如果指定了<code> null </code>侦听器,则
     * 不会执行任何操作,也不会抛出异常如果由参数先前未添加到此<code> DragSource </code>,不会执行任何操作,也不会抛出异常。
     * 
     * 
     * @param dsml the <code>DragSourceMotionListener</code> to remove
     *
     * @see      #addDragSourceMotionListener
     * @see      #getDragSourceMotionListeners
     * @since 1.4
     */
    public void removeDragSourceMotionListener(DragSourceMotionListener dsml) {
        if (dsml != null) {
            synchronized (this) {
                motionListener = DnDEventMulticaster.remove(motionListener, dsml);
            }
        }
    }

    /**
     * Gets all of the  <code>DragSourceMotionListener</code>s
     * registered with this <code>DragSource</code>.
     *
     * <p>
     *  获取向此<code> DragSource </code>注册的所有<code> DragSourceMotionListener </code>
     * 
     * 
     * @return all of this <code>DragSource</code>'s
     *         <code>DragSourceMotionListener</code>s or an empty array if no
     *         such listeners are currently registered
     *
     * @see      #addDragSourceMotionListener
     * @see      #removeDragSourceMotionListener
     * @since    1.4
     */
    public DragSourceMotionListener[] getDragSourceMotionListeners() {
        return getListeners(DragSourceMotionListener.class);
    }

    /**
     * Gets all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s upon this <code>DragSource</code>.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     *
     * <p>
     *  获取当前在此<code> DragSource </code> <code> <em> Foo </em>侦听器</code>上注册为<code> <em> Foo </em>侦听器</code> s
     * 使用<code> add <em> </em>侦听器</code>方法注册。
     * 
     * 
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s on this
     *          <code>DragSource</code>, or an empty array if no such listeners
     *          have been added
     * @exception ClassCastException if <code>listenerType</code>
     *          doesn't specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getDragSourceListeners
     * @see #getDragSourceMotionListeners
     * @since 1.4
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        EventListener l = null;
        if (listenerType == DragSourceListener.class) {
            l = listener;
        } else if (listenerType == DragSourceMotionListener.class) {
            l = motionListener;
        }
        return DnDEventMulticaster.getListeners(l, listenerType);
    }

    /**
     * This method calls <code>dragEnter</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     * 这个方法在<code> DragSource </code>注册的<code> DragSourceListener </code>上调用<code> dragEnter </code>,并传递指定的<code>
     *  DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    void processDragEnter(DragSourceDragEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragEnter(dsde);
        }
    }

    /**
     * This method calls <code>dragOver</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     *  此方法在<code> DragSource </code>注册的<code> DragSourceListener </code>上调用<code> dragOver </code>,并将指定的<code>
     *  DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    void processDragOver(DragSourceDragEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragOver(dsde);
        }
    }

    /**
     * This method calls <code>dropActionChanged</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     *  这个方法在<code> DragSource </code>注册的<code> DragSourceListener </code>上调用<code> dropActionChanged </code>
     * ,并传递指定的<code> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceDragEvent</code>
     */
    void processDropActionChanged(DragSourceDragEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dropActionChanged(dsde);
        }
    }

    /**
     * This method calls <code>dragExit</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceEvent</code>.
     *
     * <p>
     *  此方法在<code> DragSource </code>注册的<code> DragSourceListener </code>上调用<code> dragExit </code>,并将指定的<code>
     *  DragSourceEvent </code>。
     * 
     * 
     * @param dse the <code>DragSourceEvent</code>
     */
    void processDragExit(DragSourceEvent dse) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragExit(dse);
        }
    }

    /**
     * This method calls <code>dragDropEnd</code> on the
     * <code>DragSourceListener</code>s registered with this
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDropEvent</code>.
     *
     * <p>
     * 此方法在<code> DragSource </code>注册的<code> DragSourceListener </code>上调用<code> dragDropEnd </code>,并将指定的<code>
     *  DragSourceDropEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceEvent</code>
     */
    void processDragDropEnd(DragSourceDropEvent dsde) {
        DragSourceListener dsl = listener;
        if (dsl != null) {
            dsl.dragDropEnd(dsde);
        }
    }

    /**
     * This method calls <code>dragMouseMoved</code> on the
     * <code>DragSourceMotionListener</code>s registered with this
     * <code>DragSource</code>, and passes them the specified
     * <code>DragSourceDragEvent</code>.
     *
     * <p>
     *  此方法在<code> DragSource </code>注册的<code> DragSourceMotionListener </code>上调用<code> dragMouseMoved </code>
     * ,并将指定的<code> DragSourceDragEvent </code>。
     * 
     * 
     * @param dsde the <code>DragSourceEvent</code>
     */
    void processDragMouseMoved(DragSourceDragEvent dsde) {
        DragSourceMotionListener dsml = motionListener;
        if (dsml != null) {
            dsml.dragMouseMoved(dsde);
        }
    }

    /**
     * Serializes this <code>DragSource</code>. This method first performs
     * default serialization. Next, it writes out this object's
     * <code>FlavorMap</code> if and only if it can be serialized. If not,
     * <code>null</code> is written instead. Next, it writes out
     * <code>Serializable</code> listeners registered with this
     * object. Listeners are written in a <code>null</code>-terminated sequence
     * of 0 or more pairs. The pair consists of a <code>String</code> and an
     * <code>Object</code>; the <code>String</code> indicates the type of the
     * <code>Object</code> and is one of the following:
     * <ul>
     * <li><code>dragSourceListenerK</code> indicating a
     *     <code>DragSourceListener</code> object;
     * <li><code>dragSourceMotionListenerK</code> indicating a
     *     <code>DragSourceMotionListener</code> object.
     * </ul>
     *
     * <p>
     * 序列化这个<code> DragSource </code>这个方法首先执行默认序列化接下来,它写出这个对象的&lt; code&gt; FlavorMap </code>当且仅当它可以被序列化如果不是
     * ,<code> null </code>接下来,它写出<code>可序列化</code>在此对象中注册的侦听器侦听器写在一个<code> null </code>终止的0或更多对的序列中。
     * 对由<code> String </code>和<code> Object </code>; <code> String </code>表示<code> Object </code>的类型,并且是以下之
     * 一：。
     * <ul>
     *  <li> <code> dragSourceListenerK </code>表示<code> DragSourceListener </code>对象; <li> <code> dragSource
     * MotionListenerK </code>表示<code> DragSourceMotionListener </code>物件。
     * </ul>
     * 
     * 
     * @serialData Either a <code>FlavorMap</code> instance, or
     *      <code>null</code>, followed by a <code>null</code>-terminated
     *      sequence of 0 or more pairs; the pair consists of a
     *      <code>String</code> and an <code>Object</code>; the
     *      <code>String</code> indicates the type of the <code>Object</code>
     *      and is one of the following:
     *      <ul>
     *      <li><code>dragSourceListenerK</code> indicating a
     *          <code>DragSourceListener</code> object;
     *      <li><code>dragSourceMotionListenerK</code> indicating a
     *          <code>DragSourceMotionListener</code> object.
     *      </ul>.
     * @since 1.4
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        s.writeObject(SerializationTester.test(flavorMap) ? flavorMap : null);

        DnDEventMulticaster.save(s, dragSourceListenerK, listener);
        DnDEventMulticaster.save(s, dragSourceMotionListenerK, motionListener);
        s.writeObject(null);
    }

    /**
     * Deserializes this <code>DragSource</code>. This method first performs
     * default deserialization. Next, this object's <code>FlavorMap</code> is
     * deserialized by using the next object in the stream.
     * If the resulting <code>FlavorMap</code> is <code>null</code>, this
     * object's <code>FlavorMap</code> is set to the default FlavorMap for
     * this thread's <code>ClassLoader</code>.
     * Next, this object's listeners are deserialized by reading a
     * <code>null</code>-terminated sequence of 0 or more key/value pairs
     * from the stream:
     * <ul>
     * <li>If a key object is a <code>String</code> equal to
     * <code>dragSourceListenerK</code>, a <code>DragSourceListener</code> is
     * deserialized using the corresponding value object and added to this
     * <code>DragSource</code>.
     * <li>If a key object is a <code>String</code> equal to
     * <code>dragSourceMotionListenerK</code>, a
     * <code>DragSourceMotionListener</code> is deserialized using the
     * corresponding value object and added to this <code>DragSource</code>.
     * <li>Otherwise, the key/value pair is skipped.
     * </ul>
     *
     * <p>
     * 反序列化<code> DragSource </code>此方法首先执行默认反序列化。
     * 接下来,通过使用流中的下一个对象,反序列化此对象的<code> FlavorMap </code>如果生成的<code> FlavorMap </code> <code> null </code>,此对
     * 象的<code> FlavorMap </code>被设置为此线程的<code> ClassLoader </code>的默认FlavorMap。
     * 反序列化<code> DragSource </code>此方法首先执行默认反序列化。接下来,通过读取一个<code> null </code>  - 从流中终止的0个或多个键/值对的序列：。
     * <ul>
     * <li>如果键对象是<code> String </code>等于<code> dragSourceListenerK </code>,则会使用相应的值对象对<code> DragSourceListe
     * ner </code>进行反序列化, > DragSource </code> <li>如果键对象是<code> String </code>等于<code> dragSourceMotionListe
     * nerK </code>,则使用相应的值对象反序列化<code> DragSourceMotionListener </code>并添加到此<code> DragSource </code> <li>否
     * 则,将跳过键/值对。
     * </ul>
     * 
     * 
     * @see java.awt.datatransfer.SystemFlavorMap#getDefaultFlavorMap
     * @since 1.4
     */
    private void readObject(ObjectInputStream s)
      throws ClassNotFoundException, IOException {
        s.defaultReadObject();

        // 'flavorMap' was written explicitly
        flavorMap = (FlavorMap)s.readObject();

        // Implementation assumes 'flavorMap' is never null.
        if (flavorMap == null) {
            flavorMap = SystemFlavorMap.getDefaultFlavorMap();
        }

        Object keyOrNull;
        while (null != (keyOrNull = s.readObject())) {
            String key = ((String)keyOrNull).intern();

            if (dragSourceListenerK == key) {
                addDragSourceListener((DragSourceListener)(s.readObject()));
            } else if (dragSourceMotionListenerK == key) {
                addDragSourceMotionListener(
                    (DragSourceMotionListener)(s.readObject()));
            } else {
                // skip value for unrecognized key
                s.readObject();
            }
        }
    }

    /**
     * Returns the drag gesture motion threshold. The drag gesture motion threshold
     * defines the recommended behavior for {@link MouseDragGestureRecognizer}s.
     * <p>
     * If the system property <code>awt.dnd.drag.threshold</code> is set to
     * a positive integer, this method returns the value of the system property;
     * otherwise if a pertinent desktop property is available and supported by
     * the implementation of the Java platform, this method returns the value of
     * that property; otherwise this method returns some default value.
     * The pertinent desktop property can be queried using
     * <code>java.awt.Toolkit.getDesktopProperty("DnD.gestureMotionThreshold")</code>.
     *
     * <p>
     * 
     * @return the drag gesture motion threshold
     * @see MouseDragGestureRecognizer
     * @since 1.5
     */
    public static int getDragThreshold() {
        int ts = AccessController.doPrivileged(
                new GetIntegerAction("awt.dnd.drag.threshold", 0)).intValue();
        if (ts > 0) {
            return ts;
        } else {
            Integer td = (Integer)Toolkit.getDefaultToolkit().
                    getDesktopProperty("DnD.gestureMotionThreshold");
            if (td != null) {
                return td.intValue();
            }
        }
        return 5;
    }

    /*
     * fields
     * <p>
     *  返回拖动手势运动阈值拖动手势运动阈值定义{@link MouseDragGestureRecognizer}的推荐行为
     * <p>
     * 如果系统属性<code> awtdnddragthreshold </code>设置为正整数,则此方法返回system属性的值;否则如果相关的桌面属性可用并且由Java平台的实现支持,则此方法返回该属性
     * 的值;否则此方法返回一些默认值相关的桌面属性可以使用<code> javaawtToolkitgetDesktopProperty("DnDgestureMotionThreshold")</code>
     * 。
     */

    private transient FlavorMap flavorMap = SystemFlavorMap.getDefaultFlavorMap();

    private transient DragSourceListener listener;

    private transient DragSourceMotionListener motionListener;
}
