/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.peer;

import java.awt.*;
import java.awt.event.PaintEvent;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;

import sun.awt.CausedFocusEvent;
import sun.java2d.pipe.Region;


/**
 * The peer interface for {@link Component}. This is the top level peer
 * interface for widgets and defines the bulk of methods for AWT component
 * peers. Most component peers have to implement this interface (via one
 * of the subinterfaces), except menu components, which implement
 * {@link MenuComponentPeer}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Component}的对等接口。这是小部件的顶级对等接口,并为AWT组件对等体定义了大量方法。
 * 大多数组件对等体必须实现这个接口(通过一个子接口),除了实现{@link MenuComponentPeer}的菜单组件。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface ComponentPeer {

    /**
     * Operation for {@link #setBounds(int, int, int, int, int)}, indicating
     * a change in the component location only.
     *
     * <p>
     *  {@link #setBounds(int,int,int,int,int)}的操作,仅指示组件位置的更改。
     * 
     * 
     * @see #setBounds(int, int, int, int, int)
     */
    public static final int SET_LOCATION = 1;

    /**
     * Operation for {@link #setBounds(int, int, int, int, int)}, indicating
     * a change in the component size only.
     *
     * <p>
     *  {@link #setBounds(int,int,int,int,int)}的操作,表示只改变组件大小。
     * 
     * 
     * @see #setBounds(int, int, int, int, int)
     */
    public static final int SET_SIZE = 2;

    /**
     * Operation for {@link #setBounds(int, int, int, int, int)}, indicating
     * a change in the component size and location.
     *
     * <p>
     *  {@link #setBounds(int,int,int,int,int)}的操作,表示组件大小和位置的变化。
     * 
     * 
     * @see #setBounds(int, int, int, int, int)
     */
    public static final int SET_BOUNDS = 3;

    /**
     * Operation for {@link #setBounds(int, int, int, int, int)}, indicating
     * a change in the component client size. This is used for setting
     * the 'inside' size of windows, without the border insets.
     *
     * <p>
     *  {@link #setBounds(int,int,int,int,int)}的操作,表示组件客户端大小的变化。这用于设置窗口的"内部"大小,没有边框插入。
     * 
     * 
     * @see #setBounds(int, int, int, int, int)
     */
    public static final int SET_CLIENT_SIZE = 4;

    /**
     * Resets the setBounds() operation to DEFAULT_OPERATION. This is not
     * passed into {@link #setBounds(int, int, int, int, int)}.
     *
     * TODO: This is only used internally and should probably be moved outside
     *       the peer interface.
     *
     * <p>
     *  将setBounds()操作重置为DEFAULT_OPERATION。这不会传递到{@link #setBounds(int,int,int,int,int)}。
     * 
     *  TODO：这只是在内部使用,应该移动到对等体接口之外。
     * 
     * 
     * @see Component#setBoundsOp
     */
    public static final int RESET_OPERATION = 5;

    /**
     * A flag that is used to suppress checks for embedded frames.
     *
     * TODO: This is only used internally and should probably be moved outside
     *       the peer interface.
     * <p>
     *  用于抑制嵌入帧检查的标志。
     * 
     * TODO：这只是在内部使用,应该移动到对等体接口之外。
     * 
     */
    public static final int NO_EMBEDDED_CHECK = (1 << 14);

    /**
     * The default operation, which is to set size and location.
     *
     * TODO: This is only used internally and should probably be moved outside
     *       the peer interface.
     *
     * <p>
     *  默认操作,即设置大小和位置。
     * 
     *  TODO：这只是在内部使用,应该移动到对等体接口之外。
     * 
     * 
     * @see Component#setBoundsOp
     */
    public static final int DEFAULT_OPERATION = SET_BOUNDS;

    /**
     * Determines if a component has been obscured, i.e. by an overlapping
     * window or similar. This is used by JViewport for optimizing performance.
     * This doesn't have to be implemented, when
     * {@link #canDetermineObscurity()} returns {@code false}.
     *
     * <p>
     *  确定组件是否已被遮盖,即通过重叠窗口或类似物。这被JViewport用于优化性能。这不需要实现,当{@link #canDetermineObscurity()}返回{@code false}。
     * 
     * 
     * @return {@code true} when the component has been obscured,
     *         {@code false} otherwise
     *
     * @see #canDetermineObscurity()
     * @see javax.swing.JViewport#needsRepaintAfterBlit
     */
    boolean isObscured();

    /**
     * Returns {@code true} when the peer can determine if a component
     * has been obscured, {@code false} false otherwise.
     *
     * <p>
     *  当对等体可以确定组件是否被遮挡时,返回{@code true},否则返回{@code false} false。
     * 
     * 
     * @return {@code true} when the peer can determine if a component
     *         has been obscured, {@code false} false otherwise
     *
     * @see #isObscured()
     * @see javax.swing.JViewport#needsRepaintAfterBlit
     */
    boolean canDetermineObscurity();

    /**
     * Makes a component visible or invisible.
     *
     * <p>
     *  使组件可见或不可见。
     * 
     * 
     * @param v {@code true} to make a component visible,
     *          {@code false} to make it invisible
     *
     * @see Component#setVisible(boolean)
     */
    void setVisible(boolean v);

    /**
     * Enables or disables a component. Disabled components are usually grayed
     * out and cannot be activated.
     *
     * <p>
     *  启用或禁用组件。禁用的组件通常为灰色,不能激活。
     * 
     * 
     * @param e {@code true} to enable the component, {@code false}
     *          to disable it
     *
     * @see Component#setEnabled(boolean)
     */
    void setEnabled(boolean e);

    /**
     * Paints the component to the specified graphics context. This is called
     * by {@link Component#paintAll(Graphics)} to paint the component.
     *
     * <p>
     *  将组件绘制到指定的图形上下文。这由{@link Component#paintAll(Graphics)}调用来绘制组件。
     * 
     * 
     * @param g the graphics context to paint to
     *
     * @see Component#paintAll(Graphics)
     */
    void paint(Graphics g);

    /**
     * Prints the component to the specified graphics context. This is called
     * by {@link Component#printAll(Graphics)} to print the component.
     *
     * <p>
     *  将组件打印到指定的图形上下文。这由{@link Component#printAll(Graphics)}调用来打印组件。
     * 
     * 
     * @param g the graphics context to print to
     *
     * @see Component#printAll(Graphics)
     */
    void print(Graphics g);

    /**
     * Sets the location or size or both of the component. The location is
     * specified relative to the component's parent. The {@code op}
     * parameter specifies which properties change. If it is
     * {@link #SET_LOCATION}, then only the location changes (and the size
     * parameters can be ignored). If {@code op} is {@link #SET_SIZE},
     * then only the size changes (and the location can be ignored). If
     * {@code op} is {@link #SET_BOUNDS}, then both change. There is a
     * special value {@link #SET_CLIENT_SIZE}, which is used only for
     * window-like components to set the size of the client (i.e. the 'inner'
     * size, without the insets of the window borders).
     *
     * <p>
     * 设置组件的位置或大小或两者。该位置是相对于组件的父项指定的。 {@code op}参数指定哪些属性更改。如果是{@link #SET_LOCATION},那么只有位置更改(并且大小参数可以忽略)。
     * 如果{@code op}为{@link #SET_SIZE},则只会更改大小(并且可以忽略该位置)。如果{@code op}是{@link #SET_BOUNDS},则两者都会更改。
     * 有一个特殊的值{@link #SET_CLIENT_SIZE},它只用于类窗口组件设置客户端的大小(即'内部'大小,没有窗口边框的插入)。
     * 
     * 
     * @param x the X location of the component
     * @param y the Y location of the component
     * @param width the width of the component
     * @param height the height of the component
     * @param op the operation flag
     *
     * @see #SET_BOUNDS
     * @see #SET_LOCATION
     * @see #SET_SIZE
     * @see #SET_CLIENT_SIZE
     */
    void setBounds(int x, int y, int width, int height, int op);

    /**
     * Called to let the component peer handle events.
     *
     * <p>
     *  调用让组件对等体处理事件。
     * 
     * 
     * @param e the AWT event to handle
     *
     * @see Component#dispatchEvent(AWTEvent)
     */
    void handleEvent(AWTEvent e);

    /**
     * Called to coalesce paint events.
     *
     * <p>
     *  被称为合成油漆事件。
     * 
     * 
     * @param e the paint event to consider to coalesce
     *
     * @see EventQueue#coalescePaintEvent
     */
    void coalescePaintEvent(PaintEvent e);

    /**
     * Determines the location of the component on the screen.
     *
     * <p>
     *  确定组件在屏幕上的位置。
     * 
     * 
     * @return the location of the component on the screen
     *
     * @see Component#getLocationOnScreen()
     */
    Point getLocationOnScreen();

    /**
     * Determines the preferred size of the component.
     *
     * <p>
     *  确定组件的首选大小。
     * 
     * 
     * @return the preferred size of the component
     *
     * @see Component#getPreferredSize()
     */
    Dimension getPreferredSize();

    /**
     * Determines the minimum size of the component.
     *
     * <p>
     *  确定组件的最小大小。
     * 
     * 
     * @return the minimum size of the component
     *
     * @see Component#getMinimumSize()
     */
    Dimension getMinimumSize();

    /**
     * Returns the color model used by the component.
     *
     * <p>
     *  返回组件使用的颜色模型。
     * 
     * 
     * @return the color model used by the component
     *
     * @see Component#getColorModel()
     */
    ColorModel getColorModel();

    /**
     * Returns a graphics object to paint on the component.
     *
     * <p>
     *  返回要在组件上绘制的图形对象。
     * 
     * 
     * @return a graphics object to paint on the component
     *
     * @see Component#getGraphics()
     */
    // TODO: Maybe change this to force Graphics2D, since many things will
    // break with plain Graphics nowadays.
    Graphics getGraphics();

    /**
     * Returns a font metrics object to determine the metrics properties of
     * the specified font.
     *
     * <p>
     *  返回字体度量对象以确定指定字体的度量属性。
     * 
     * 
     * @param font the font to determine the metrics for
     *
     * @return a font metrics object to determine the metrics properties of
     *         the specified font
     *
     * @see Component#getFontMetrics(Font)
     */
    FontMetrics getFontMetrics(Font font);

    /**
     * Disposes all resources held by the component peer. This is called
     * when the component has been disconnected from the component hierarchy
     * and is about to be garbage collected.
     *
     * <p>
     *  处置组件对等体持有的所有资源。当组件已经从组件层次结构中断开并且将要被垃圾回收时,这被调用。
     * 
     * 
     * @see Component#removeNotify()
     */
    void dispose();

    /**
     * Sets the foreground color of this component.
     *
     * <p>
     *  设置此组件的前景色。
     * 
     * 
     * @param c the foreground color to set
     *
     * @see Component#setForeground(Color)
     */
    void setForeground(Color c);

    /**
     * Sets the background color of this component.
     *
     * <p>
     *  设置此组件的背景颜色。
     * 
     * 
     * @param c the background color to set
     *
     * @see Component#setBackground(Color)
     */
    void setBackground(Color c);

    /**
     * Sets the font of this component.
     *
     * <p>
     *  设置此组件的字体。
     * 
     * 
     * @param f the font of this component
     *
     * @see Component#setFont(Font)
     */
    void setFont(Font f);

    /**
     * Updates the cursor of the component.
     *
     * <p>
     *  更新组件的光标。
     * 
     * 
     * @see Component#updateCursorImmediately
     */
    void updateCursorImmediately();

    /**
     * Requests focus on this component.
     *
     * <p>
     *  请求关注此组件。
     * 
     * 
     * @param lightweightChild the actual lightweight child that requests the
     *        focus
     * @param temporary {@code true} if the focus change is temporary,
     *        {@code false} otherwise
     * @param focusedWindowChangeAllowed {@code true} if changing the
     *        focus of the containing window is allowed or not
     * @param time the time of the focus change request
     * @param cause the cause of the focus change request
     *
     * @return {@code true} if the focus change is guaranteed to be
     *         granted, {@code false} otherwise
     */
    boolean requestFocus(Component lightweightChild, boolean temporary,
                         boolean focusedWindowChangeAllowed, long time,
                         CausedFocusEvent.Cause cause);

    /**
     * Returns {@code true} when the component takes part in the focus
     * traversal, {@code false} otherwise.
     *
     * <p>
     * 当组件参与焦点遍历时返回{@code true},否则返回{@code false}。
     * 
     * 
     * @return {@code true} when the component takes part in the focus
     *         traversal, {@code false} otherwise
     */
    boolean isFocusable();

    /**
     * Creates an image using the specified image producer.
     *
     * <p>
     *  使用指定的图像制作者创建图像。
     * 
     * 
     * @param producer the image producer from which the image pixels will be
     *        produced
     *
     * @return the created image
     *
     * @see Component#createImage(ImageProducer)
     */
    Image createImage(ImageProducer producer);

    /**
     * Creates an empty image with the specified width and height. This is
     * generally used as a non-accelerated backbuffer for drawing onto the
     * component (e.g. by Swing).
     *
     * <p>
     *  创建具有指定宽度和高度的空白图像。这通常用作用于绘制到组件上(例如通过Swing)的非加速后缓冲器。
     * 
     * 
     * @param width the width of the image
     * @param height the height of the image
     *
     * @return the created image
     *
     * @see Component#createImage(int, int)
     */
    // TODO: Maybe make that return a BufferedImage, because some stuff will
    // break if a different kind of image is returned.
    Image createImage(int width, int height);

    /**
     * Creates an empty volatile image with the specified width and height.
     * This is generally used as an accelerated backbuffer for drawing onto
     * the component (e.g. by Swing).
     *
     * <p>
     *  创建具有指定宽度和高度的空的易失性映像。这通常用作用于绘制到组件上(例如通过Swing)的加速后缓冲器。
     * 
     * 
     * @param width the width of the image
     * @param height the height of the image
     *
     * @return the created volatile image
     *
     * @see Component#createVolatileImage(int, int)
     */
    // TODO: Include capabilities here and fix Component#createVolatileImage
    VolatileImage createVolatileImage(int width, int height);

    /**
     * Prepare the specified image for rendering on this component. This should
     * start loading the image (if not already loaded) and create an
     * appropriate screen representation.
     *
     * <p>
     *  准备指定的图像以在此组件上呈现。这应该开始加载图像(如果尚未加载)并创建适当的屏幕表示。
     * 
     * 
     * @param img the image to prepare
     * @param w the width of the screen representation
     * @param h the height of the screen representation
     * @param o an image observer to observe the progress
     *
     * @return {@code true} if the image is already fully prepared,
     *         {@code false} otherwise
     *
     * @see Component#prepareImage(Image, int, int, ImageObserver)
     */
    boolean prepareImage(Image img, int w, int h, ImageObserver o);

    /**
     * Determines the status of the construction of the screen representaion
     * of the specified image.
     *
     * <p>
     *  确定指定图像的屏幕表示结构的状态。
     * 
     * 
     * @param img the image to check
     * @param w the target width
     * @param h the target height
     * @param o the image observer to notify
     *
     * @return the status as bitwise ORed ImageObserver flags
     *
     * @see Component#checkImage(Image, int, int, ImageObserver)
     */
    int checkImage(Image img, int w, int h, ImageObserver o);

    /**
     * Returns the graphics configuration that corresponds to this component.
     *
     * <p>
     *  返回与此组件对应的图形配置。
     * 
     * 
     * @return the graphics configuration that corresponds to this component
     *
     * @see Component#getGraphicsConfiguration()
     */
    GraphicsConfiguration getGraphicsConfiguration();

    /**
     * Determines if the component handles wheel scrolling itself. Otherwise
     * it is delegated to the component's parent.
     *
     * <p>
     *  确定组件是否处理滚轮滚动本身。否则,它被委托给组件的父级。
     * 
     * 
     * @return {@code true} if the component handles wheel scrolling,
     *         {@code false} otherwise
     *
     * @see Component#dispatchEventImpl(AWTEvent)
     */
    boolean handlesWheelScrolling();

    /**
     * Create {@code numBuffers} flipping buffers with the specified
     * buffer capabilities.
     *
     * <p>
     *  使用指定的缓冲区功能创建{@code numBuffers}翻转缓冲区。
     * 
     * 
     * @param numBuffers the number of buffers to create
     * @param caps the buffer capabilities
     *
     * @throws AWTException if flip buffering is not supported
     *
     * @see Component.FlipBufferStrategy#createBuffers
     */
    void createBuffers(int numBuffers, BufferCapabilities caps)
         throws AWTException;

    /**
     * Returns the back buffer as image.
     *
     * <p>
     *  将后缓冲区作为图像返回。
     * 
     * 
     * @return the back buffer as image
     *
     * @see Component.FlipBufferStrategy#getBackBuffer
     */
    Image getBackBuffer();

    /**
     * Move the back buffer to the front buffer.
     *
     * <p>
     *  将后台缓冲区移动到前缓冲区。
     * 
     * 
     * @param x1 the area to be flipped, upper left X coordinate
     * @param y1 the area to be flipped, upper left Y coordinate
     * @param x2 the area to be flipped, lower right X coordinate
     * @param y2 the area to be flipped, lower right Y coordinate
     * @param flipAction the flip action to perform
     *
     * @see Component.FlipBufferStrategy#flip
     */
    void flip(int x1, int y1, int x2, int y2, BufferCapabilities.FlipContents flipAction);

    /**
     * Destroys all created buffers.
     *
     * <p>
     *  销毁所有创建的缓冲区。
     * 
     * 
     * @see Component.FlipBufferStrategy#destroyBuffers
     */
    void destroyBuffers();

    /**
     * Reparents this peer to the new parent referenced by
     * {@code newContainer} peer. Implementation depends on toolkit and
     * container.
     *
     * <p>
     *  将此对等体重新表现为由{@code newContainer}对等体引用的新父对象。实现取决于工具包和容器。
     * 
     * 
     * @param newContainer peer of the new parent container
     *
     * @since 1.5
     */
    void reparent(ContainerPeer newContainer);

    /**
     * Returns whether this peer supports reparenting to another parent without
     * destroying the peer.
     *
     * <p>
     *  返回此对等体是否支持重新排列父另一个父对象,而不销毁对等体。
     * 
     * 
     * @return true if appropriate reparent is supported, false otherwise
     *
     * @since 1.5
     */
    boolean isReparentSupported();

    /**
     * Used by lightweight implementations to tell a ComponentPeer to layout
     * its sub-elements.  For instance, a lightweight Checkbox needs to layout
     * the box, as well as the text label.
     *
     * <p>
     * 由轻量级实现使用来告诉ComponentPeer布局其子元素。例如,一个轻量级的复选框需要布局框,以及文本标签。
     * 
     * 
     * @see Component#validate()
     */
    void layout();

    /**
     * Applies the shape to the native component window.
     * <p>
     *  将形状应用到本机组件窗口。
     * 
     * 
     * @since 1.7
     *
     * @see Component#applyCompoundShape
     */
    void applyShape(Region shape);

    /**
     * Lowers this component at the bottom of the above HW peer. If the above parameter
     * is null then the method places this component at the top of the Z-order.
     * <p>
     *  在上述HW对等体的底部降低此组件。如果上述参数为null,则该方法将此组件放置在Z顺序的顶部。
     * 
     */
    void setZOrder(ComponentPeer above);

    /**
     * Updates internal data structures related to the component's GC.
     *
     * <p>
     *  更新与组件的GC相关的内部数据结构。
     * 
     * @return if the peer needs to be recreated for the changes to take effect
     * @since 1.7
     */
    boolean updateGraphicsData(GraphicsConfiguration gc);
}
