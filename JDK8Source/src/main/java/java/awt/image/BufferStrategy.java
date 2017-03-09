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

package java.awt.image;

import java.awt.BufferCapabilities;
import java.awt.Graphics;
import java.awt.Image;

/**
 * The <code>BufferStrategy</code> class represents the mechanism with which
 * to organize complex memory on a particular <code>Canvas</code> or
 * <code>Window</code>.  Hardware and software limitations determine whether and
 * how a particular buffer strategy can be implemented.  These limitations
 * are detectable through the capabilities of the
 * <code>GraphicsConfiguration</code> used when creating the
 * <code>Canvas</code> or <code>Window</code>.
 * <p>
 * It is worth noting that the terms <i>buffer</i> and <i>surface</i> are meant
 * to be synonymous: an area of contiguous memory, either in video device
 * memory or in system memory.
 * <p>
 * There are several types of complex buffer strategies, including
 * sequential ring buffering and blit buffering.
 * Sequential ring buffering (i.e., double or triple
 * buffering) is the most common; an application draws to a single <i>back
 * buffer</i> and then moves the contents to the front (display) in a single
 * step, either by copying the data or moving the video pointer.
 * Moving the video pointer exchanges the buffers so that the first buffer
 * drawn becomes the <i>front buffer</i>, or what is currently displayed on the
 * device; this is called <i>page flipping</i>.
 * <p>
 * Alternatively, the contents of the back buffer can be copied, or
 * <i>blitted</i> forward in a chain instead of moving the video pointer.
 * <pre>{@code
 * Double buffering:
 *
 *                    ***********         ***********
 *                    *         * ------> *         *
 * [To display] <---- * Front B *   Show  * Back B. * <---- Rendering
 *                    *         * <------ *         *
 *                    ***********         ***********
 *
 * Triple buffering:
 *
 * [To      ***********         ***********        ***********
 * display] *         * --------+---------+------> *         *
 *    <---- * Front B *   Show  * Mid. B. *        * Back B. * <---- Rendering
 *          *         * <------ *         * <----- *         *
 *          ***********         ***********        ***********
 *
 * }</pre>
 * <p>
 * Here is an example of how buffer strategies can be created and used:
 * <pre><code>
 *
 * // Check the capabilities of the GraphicsConfiguration
 * ...
 *
 * // Create our component
 * Window w = new Window(gc);
 *
 * // Show our window
 * w.setVisible(true);
 *
 * // Create a general double-buffering strategy
 * w.createBufferStrategy(2);
 * BufferStrategy strategy = w.getBufferStrategy();
 *
 * // Main loop
 * while (!done) {
 *     // Prepare for rendering the next frame
 *     // ...
 *
 *     // Render single frame
 *     do {
 *         // The following loop ensures that the contents of the drawing buffer
 *         // are consistent in case the underlying surface was recreated
 *         do {
 *             // Get a new graphics context every time through the loop
 *             // to make sure the strategy is validated
 *             Graphics graphics = strategy.getDrawGraphics();
 *
 *             // Render to graphics
 *             // ...
 *
 *             // Dispose the graphics
 *             graphics.dispose();
 *
 *             // Repeat the rendering if the drawing buffer contents
 *             // were restored
 *         } while (strategy.contentsRestored());
 *
 *         // Display the buffer
 *         strategy.show();
 *
 *         // Repeat the rendering if the drawing buffer was lost
 *     } while (strategy.contentsLost());
 * }
 *
 * // Dispose the window
 * w.setVisible(false);
 * w.dispose();
 * </code></pre>
 *
 * <p>
 *  <code> BufferStrategy </code>类表示在特定<code> Canvas </code>或<code> Window </code>上组织复杂内存的机制。
 * 硬件和软件限制决定了是否以及如何实现特定的缓冲区策略。
 * 这些限制可以通过创建<code> Canvas </code>或<code> Window </code>时使用的<code> GraphicsConfiguration </code>。
 * <p>
 *  值得注意的是,术语<i> buffer </i>和<i> surface </i>意味着同义：在视频设备存储器或系统存储器中的连续存储器的区域。
 * <p>
 *  存在几种类型的复杂缓冲器策略,包括顺序环形缓冲和blit缓冲。
 * 顺序环缓冲(即,双重或三重缓冲)是最常见的;应用程序绘制到单个后退缓冲器</i>,然后通过复制数据或移动视频指针,在单个步骤中将内容移动到前端(显示器)。
 * 移动视频指针交换缓冲器,使得绘制的第一个缓冲器变为前缓冲器</i>或当前在设备上显示的内容;这称为<i>翻页</i>。
 * <p>
 *  或者,后向缓冲器的内容可以被复制或者在链中前进,而不是移动视频指针。 <pre> {@ code Double buffering：
 * 
 * *********** *********** * * ------> * * [显示] <---- *前B *显示*后B 。
 * * <----渲染* * <------ * * *********** ***********。
 * 
 *  三重缓冲：
 * 
 *  [To *********** *********** ***********显示] * * -------- +  - ------- + ------> * * <---- *前B *显示*中。
 *  B. * *返回B. * <----渲染* * <------ * * <----- * * *********** ****** ***** ***********。
 * 
 *  } </pre>
 * <p>
 *  下面是一个如何创建和使用缓冲区策略的示例：<pre> <code>
 * 
 *  //检查GraphicsConfiguration的功能...
 * 
 *  //创建我们的组件窗口w = new Window(gc);
 * 
 *  //显示我们的窗口w.setVisible(true);
 * 
 *  //创建一个通用的双缓冲策略w.createBufferStrategy(2); BufferStrategy strategy = w.getBufferStrategy();
 * 
 *  // Main loop while(！done){//准备渲染下一帧// ...
 * 
 *  // Render single frame do {//以下循环确保绘图缓冲区的内容//在重新创建基础表面的情况下是一致的do {//每次通过循环获取新的图形上下文//以确保该策略验证Graphics graphics = strategy.getDrawGraphics();。
 * 
 * @see java.awt.Window
 * @see java.awt.Canvas
 * @see java.awt.GraphicsConfiguration
 * @see VolatileImage
 * @author Michael Martak
 * @since 1.4
 */
public abstract class BufferStrategy {

    /**
     * Returns the <code>BufferCapabilities</code> for this
     * <code>BufferStrategy</code>.
     *
     * <p>
     * 
     *  //渲染到图形// ...
     * 
     *  //处理图形graphics.dispose();
     * 
     *  //如果绘图缓冲区内容//被恢复,重复渲染} while(strategy.contentsRestored());
     * 
     *  //显示缓冲区strategy.show();
     * 
     * //如果绘图缓冲区丢失,重复渲染} while(strategy.contentsLost()); }}
     * 
     *  //处理窗口w.setVisible(false); w.dispose(); </code> </pre>
     * 
     * 
     * @return the buffering capabilities of this strategy
     */
    public abstract BufferCapabilities getCapabilities();

    /**
     * Creates a graphics context for the drawing buffer.  This method may not
     * be synchronized for performance reasons; use of this method by multiple
     * threads should be handled at the application level.  Disposal of the
     * graphics object obtained must be handled by the application.
     *
     * <p>
     *  返回<code> BufferStrategy </code>的<code> BufferCapabilities </code>。
     * 
     * 
     * @return a graphics context for the drawing buffer
     */
    public abstract Graphics getDrawGraphics();

    /**
     * Returns whether the drawing buffer was lost since the last call to
     * <code>getDrawGraphics</code>.  Since the buffers in a buffer strategy
     * are usually type <code>VolatileImage</code>, they may become lost.
     * For a discussion on lost buffers, see <code>VolatileImage</code>.
     *
     * <p>
     *  为绘图缓冲区创建图形上下文。由于性能原因,此方法可能不同步;多线程使用这种方法应该在应用程序级别处理。获取的图形对象的处理必须由应用程序处理。
     * 
     * 
     * @return Whether or not the drawing buffer was lost since the last call
     * to <code>getDrawGraphics</code>.
     * @see java.awt.image.VolatileImage
     */
    public abstract boolean contentsLost();

    /**
     * Returns whether the drawing buffer was recently restored from a lost
     * state and reinitialized to the default background color (white).
     * Since the buffers in a buffer strategy are usually type
     * <code>VolatileImage</code>, they may become lost.  If a surface has
     * been recently restored from a lost state since the last call to
     * <code>getDrawGraphics</code>, it may require repainting.
     * For a discussion on lost buffers, see <code>VolatileImage</code>.
     *
     * <p>
     *  返回上次调用<code> getDrawGraphics </code>后绘图缓冲区是否丢失。
     * 由于缓冲区策略中的缓冲区通常是<code> VolatileImage </code>类型,它们可能会丢失。有关丢失缓冲区的讨论,请参阅<code> VolatileImage </code>。
     * 
     * 
     * @return Whether or not the drawing buffer was restored since the last
     *         call to <code>getDrawGraphics</code>.
     * @see java.awt.image.VolatileImage
     */
    public abstract boolean contentsRestored();

    /**
     * Makes the next available buffer visible by either copying the memory
     * (blitting) or changing the display pointer (flipping).
     * <p>
     *  返回绘图缓冲区最近是否从丢失状态恢复,并重新初始化为默认背景颜色(白色)。由于缓冲区策略中的缓冲区通常是<code> VolatileImage </code>类型,它们可能会丢失。
     * 如果最近一次调用<code> getDrawGraphics </code>后,表面最近从丢失状态恢复,则可能需要重新绘制。
     * 有关丢失缓冲区的讨论,请参阅<code> VolatileImage </code>。
     * 
     */
    public abstract void show();

    /**
     * Releases system resources currently consumed by this
     * <code>BufferStrategy</code> and
     * removes it from the associated Component.  After invoking this
     * method, <code>getBufferStrategy</code> will return null.  Trying
     * to use a <code>BufferStrategy</code> after it has been disposed will
     * result in undefined behavior.
     *
     * <p>
     *  通过复制内存(blitting)或更改显示指针(翻转)使下一个可用缓冲区可见。
     * 
     * 
     * @see java.awt.Window#createBufferStrategy
     * @see java.awt.Canvas#createBufferStrategy
     * @see java.awt.Window#getBufferStrategy
     * @see java.awt.Canvas#getBufferStrategy
     * @since 1.6
     */
    public void dispose() {
    }
}
