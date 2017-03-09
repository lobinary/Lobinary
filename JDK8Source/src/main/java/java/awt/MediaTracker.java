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

package java.awt;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.ImageObserver;
import sun.awt.image.MultiResolutionToolkitImage;

/**
 * The <code>MediaTracker</code> class is a utility class to track
 * the status of a number of media objects. Media objects could
 * include audio clips as well as images, though currently only
 * images are supported.
 * <p>
 * To use a media tracker, create an instance of
 * <code>MediaTracker</code> and call its <code>addImage</code>
 * method for each image to be tracked. In addition, each image can
 * be assigned a unique identifier. This identifier controls the
 * priority order in which the images are fetched. It can also be used
 * to identify unique subsets of the images that can be waited on
 * independently. Images with a lower ID are loaded in preference to
 * those with a higher ID number.
 *
 * <p>
 *
 * Tracking an animated image
 * might not always be useful
 * due to the multi-part nature of animated image
 * loading and painting,
 * but it is supported.
 * <code>MediaTracker</code> treats an animated image
 * as completely loaded
 * when the first frame is completely loaded.
 * At that point, the <code>MediaTracker</code>
 * signals any waiters
 * that the image is completely loaded.
 * If no <code>ImageObserver</code>s are observing the image
 * when the first frame has finished loading,
 * the image might flush itself
 * to conserve resources
 * (see {@link Image#flush()}).
 *
 * <p>
 * Here is an example of using <code>MediaTracker</code>:
 * <p>
 * <hr><blockquote><pre>{@code
 * import java.applet.Applet;
 * import java.awt.Color;
 * import java.awt.Image;
 * import java.awt.Graphics;
 * import java.awt.MediaTracker;
 *
 * public class ImageBlaster extends Applet implements Runnable {
 *      MediaTracker tracker;
 *      Image bg;
 *      Image anim[] = new Image[5];
 *      int index;
 *      Thread animator;
 *
 *      // Get the images for the background (id == 0)
 *      // and the animation frames (id == 1)
 *      // and add them to the MediaTracker
 *      public void init() {
 *          tracker = new MediaTracker(this);
 *          bg = getImage(getDocumentBase(),
 *                  "images/background.gif");
 *          tracker.addImage(bg, 0);
 *          for (int i = 0; i < 5; i++) {
 *              anim[i] = getImage(getDocumentBase(),
 *                      "images/anim"+i+".gif");
 *              tracker.addImage(anim[i], 1);
 *          }
 *      }
 *
 *      // Start the animation thread.
 *      public void start() {
 *          animator = new Thread(this);
 *          animator.start();
 *      }
 *
 *      // Stop the animation thread.
 *      public void stop() {
 *          animator = null;
 *      }
 *
 *      // Run the animation thread.
 *      // First wait for the background image to fully load
 *      // and paint.  Then wait for all of the animation
 *      // frames to finish loading. Finally, loop and
 *      // increment the animation frame index.
 *      public void run() {
 *          try {
 *              tracker.waitForID(0);
 *              tracker.waitForID(1);
 *          } catch (InterruptedException e) {
 *              return;
 *          }
 *          Thread me = Thread.currentThread();
 *          while (animator == me) {
 *              try {
 *                  Thread.sleep(100);
 *              } catch (InterruptedException e) {
 *                  break;
 *              }
 *              synchronized (this) {
 *                  index++;
 *                  if (index >= anim.length) {
 *                      index = 0;
 *                  }
 *              }
 *              repaint();
 *          }
 *      }
 *
 *      // The background image fills the frame so we
 *      // don't need to clear the applet on repaints.
 *      // Just call the paint method.
 *      public void update(Graphics g) {
 *          paint(g);
 *      }
 *
 *      // Paint a large red rectangle if there are any errors
 *      // loading the images.  Otherwise always paint the
 *      // background so that it appears incrementally as it
 *      // is loading.  Finally, only paint the current animation
 *      // frame if all of the frames (id == 1) are done loading,
 *      // so that we don't get partial animations.
 *      public void paint(Graphics g) {
 *          if ((tracker.statusAll(false) & MediaTracker.ERRORED) != 0) {
 *              g.setColor(Color.red);
 *              g.fillRect(0, 0, size().width, size().height);
 *              return;
 *          }
 *          g.drawImage(bg, 0, 0, this);
 *          if (tracker.statusID(1, false) == MediaTracker.COMPLETE) {
 *              g.drawImage(anim[index], 10, 10, this);
 *          }
 *      }
 * }
 * } </pre></blockquote><hr>
 *
 * <p>
 *  <code> MediaTracker </code>类是一个实用程序类,用于跟踪多个媒体对象的状态。媒体对象可以包括音频剪辑以及图像,尽管当前仅支持图像。
 * <p>
 *  要使用媒体跟踪器,请创建<code> MediaTracker </code>的实例,并为要跟踪的每个图像调用其<code> addImage </code>方法。
 * 此外,每个图像可以分配唯一的标识符。此标识符控制提取图像的优先级顺序。它还可以用于识别可以独立等待的图像的唯一子集。具有较低ID的图像优先于具有较高ID的图像。
 * 
 * <p>
 * 
 *  跟踪动画图像可能不总是有用的,因为动画图像加载和绘画的多部分性质,但它是支持。 <code> MediaTracker </code>将第一帧完全加载后的动画图像视为完全加载。
 * 此时,<code> MediaTracker </code>会通知任何服务器图像已完全加载。
 * 如果没有<code> ImageObserver </code>在第一帧完成加载时观察图像,则图像可能会自动刷新以节省资源(请参阅{@link Image#flush()})。
 * 
 * <p>
 *  下面是使用<code> MediaTracker </code>的示例：
 * <p>
 * <hr> <blockquote> <pre> {@ code import java.applet.Applet; import java.awt.Color; import java.awt.Image; import java.awt.Graphics; import java.awt.MediaTracker;。
 * 
 *  public class ImageBlaster extends Applet implements Runnable {MediaTracker tracker;图像bg; Image anim [] = new Image [5]; int index;线程动画;。
 * 
 *  //获取背景的图像(id == 0)//和动画帧(id == 1)//并将它们添加到MediaTracker public void init(){tracker = new MediaTracker(this); bg = getImage(getDocumentBase(),"images / background.gif"); tracker.addImage(bg,0); for(int i = 0; i <5; i ++){anim [i] = getImage(getDocumentBase(),"images / anim"+ i +"。
 *  tracker.addImage(anim [i],1); }}。
 * 
 *  //启动动画线程。 public void start(){animator = new Thread(this); animator.start(); }}
 * 
 *  //停止动画线程。 public void stop(){animator = null; }}
 * 
 *  //运行动画线程。 //首先等待背景图像完全加载//并绘制。然后等待所有的动画//帧完成加载。最后,循环和//递增动画帧索引。
 *  public void run(){try {tracker.waitForID(0); tracker.waitForID(1); } catch(InterruptedException e){return; }
 *  Thread me = Thread.currentThread(); while(animator == me){try {Thread.sleep(100); } catch(Interrupte
 * dException e){break; } synchronized(this){index ++; if(index> = anim.length){index = 0; }} repaint();
 *  }}。
 *  //运行动画线程。 //首先等待背景图像完全加载//并绘制。然后等待所有的动画//帧完成加载。最后,循环和//递增动画帧索引。
 * 
 * //背景图像填充框架,所以我们//不需要清除重绘的applet。 //只需调用paint方法。 public void update(Graphics g){paint(g); }}
 * 
 *  //绘制一个大的红色矩形,如果有任何错误//加载图像。否则总是绘制//背景,以便它在加载时以递增方式显示。
 * 最后,只有当所有的帧(id == 1)完成加载时,//绘制当前动画//帧,以便我们不会得到部分动画。
 *  public void paint(Graphics g){if((tracker.statusAll(false)&MediaTracker.ERRORED)！= 0){g.setColor(Color.red); g.fillRect(0,0,size()。
 * 最后,只有当所有的帧(id == 1)完成加载时,//绘制当前动画//帧,以便我们不会得到部分动画。width,size()。
 * height);返回; } g.drawImage(bg,0,0,this); if(tracker.statusID(1,false)== MediaTracker.COMPLETE){g.drawImage(anim [index],10,10,this); }
 * }}} </pre> </blockquote> <hr>。
 * 最后,只有当所有的帧(id == 1)完成加载时,//绘制当前动画//帧,以便我们不会得到部分动画。width,size()。
 * 
 * 
 * @author      Jim Graham
 * @since       JDK1.0
 */
public class MediaTracker implements java.io.Serializable {

    /**
     * A given <code>Component</code> that will be
     * tracked by a media tracker where the image will
     * eventually be drawn.
     *
     * <p>
     *  给定的<code> Component </code>将由媒体跟踪器跟踪,最终将绘制图像。
     * 
     * 
     * @serial
     * @see #MediaTracker(Component)
     */
    Component target;
    /**
     * The head of the list of <code>Images</code> that is being
     * tracked by the <code>MediaTracker</code>.
     *
     * <p>
     *  由<code> MediaTracker </code>跟踪的<code> Images </code>列表的头部。
     * 
     * 
     * @serial
     * @see #addImage(Image, int)
     * @see #removeImage(Image)
     */
    MediaEntry head;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = -483174189758638095L;

    /**
     * Creates a media tracker to track images for a given component.
     * <p>
     *  创建媒体跟踪器以跟踪给定组件的图像。
     * 
     * 
     * @param     comp the component on which the images
     *                     will eventually be drawn
     */
    public MediaTracker(Component comp) {
        target = comp;
    }

    /**
     * Adds an image to the list of images being tracked by this media
     * tracker. The image will eventually be rendered at its default
     * (unscaled) size.
     * <p>
     *  将图像添加到此媒体跟踪器正在跟踪的图片列表。图像最终将以默认(非标度)大小渲染。
     * 
     * 
     * @param     image   the image to be tracked
     * @param     id      an identifier used to track this image
     */
    public void addImage(Image image, int id) {
        addImage(image, id, -1, -1);
    }

    /**
     * Adds a scaled image to the list of images being tracked
     * by this media tracker. The image will eventually be
     * rendered at the indicated width and height.
     *
     * <p>
     *  将缩放的图像添加到此媒体跟踪器正在跟踪的图像列表。图像最终将以指定的宽度和高度渲染。
     * 
     * 
     * @param     image   the image to be tracked
     * @param     id   an identifier that can be used to track this image
     * @param     w    the width at which the image is rendered
     * @param     h    the height at which the image is rendered
     */
    public synchronized void addImage(Image image, int id, int w, int h) {
        addImageImpl(image, id, w, h);
        Image rvImage = getResolutionVariant(image);
        if (rvImage != null) {
            addImageImpl(rvImage, id,
                    w == -1 ? -1 : 2 * w,
                    h == -1 ? -1 : 2 * h);
        }
    }

    private void addImageImpl(Image image, int id, int w, int h) {
        head = MediaEntry.insert(head,
                                 new ImageMediaEntry(this, image, id, w, h));
    }
    /**
     * Flag indicating that media is currently being loaded.
     * <p>
     *  表示媒体当前正在加载的标志。
     * 
     * 
     * @see         java.awt.MediaTracker#statusAll
     * @see         java.awt.MediaTracker#statusID
     */
    public static final int LOADING = 1;

    /**
     * Flag indicating that the downloading of media was aborted.
     * <p>
     * 标志表示下载媒体已中止。
     * 
     * 
     * @see         java.awt.MediaTracker#statusAll
     * @see         java.awt.MediaTracker#statusID
     */
    public static final int ABORTED = 2;

    /**
     * Flag indicating that the downloading of media encountered
     * an error.
     * <p>
     *  标志表示下载媒体时遇到错误。
     * 
     * 
     * @see         java.awt.MediaTracker#statusAll
     * @see         java.awt.MediaTracker#statusID
     */
    public static final int ERRORED = 4;

    /**
     * Flag indicating that the downloading of media was completed
     * successfully.
     * <p>
     *  表示介质下载已成功完成的标志。
     * 
     * 
     * @see         java.awt.MediaTracker#statusAll
     * @see         java.awt.MediaTracker#statusID
     */
    public static final int COMPLETE = 8;

    static final int DONE = (ABORTED | ERRORED | COMPLETE);

    /**
     * Checks to see if all images being tracked by this media tracker
     * have finished loading.
     * <p>
     * This method does not start loading the images if they are not
     * already loading.
     * <p>
     * If there is an error while loading or scaling an image, then that
     * image is considered to have finished loading. Use the
     * <code>isErrorAny</code> or <code>isErrorID</code> methods to
     * check for errors.
     * <p>
     *  检查此媒体跟踪器正在跟踪的所有图片是否已完成加载。
     * <p>
     *  如果尚未加载图像,此方法不会开始加载图像。
     * <p>
     *  如果在加载或缩放图像时出现错误,则该图像将被视为已完成加载。使用<code> isErrorAny </code>或<code> isErrorID </code>方法检查错误。
     * 
     * 
     * @return      <code>true</code> if all images have finished loading,
     *                       have been aborted, or have encountered
     *                       an error; <code>false</code> otherwise
     * @see         java.awt.MediaTracker#checkAll(boolean)
     * @see         java.awt.MediaTracker#checkID
     * @see         java.awt.MediaTracker#isErrorAny
     * @see         java.awt.MediaTracker#isErrorID
     */
    public boolean checkAll() {
        return checkAll(false, true);
    }

    /**
     * Checks to see if all images being tracked by this media tracker
     * have finished loading.
     * <p>
     * If the value of the <code>load</code> flag is <code>true</code>,
     * then this method starts loading any images that are not yet
     * being loaded.
     * <p>
     * If there is an error while loading or scaling an image, that
     * image is considered to have finished loading. Use the
     * <code>isErrorAny</code> and <code>isErrorID</code> methods to
     * check for errors.
     * <p>
     *  检查此媒体跟踪器正在跟踪的所有图片是否已完成加载。
     * <p>
     *  如果<code> load </code>标志的值为<code> true </code>,那么此方法将开始加载尚未加载的任何图像。
     * <p>
     *  如果在加载或缩放图像时出错,该图像将被视为已完成加载。使用<code> isErrorAny </code>和<code> isErrorID </code>方法检查错误。
     * 
     * 
     * @param       load   if <code>true</code>, start loading any
     *                       images that are not yet being loaded
     * @return      <code>true</code> if all images have finished loading,
     *                       have been aborted, or have encountered
     *                       an error; <code>false</code> otherwise
     * @see         java.awt.MediaTracker#checkID
     * @see         java.awt.MediaTracker#checkAll()
     * @see         java.awt.MediaTracker#isErrorAny()
     * @see         java.awt.MediaTracker#isErrorID(int)
     */
    public boolean checkAll(boolean load) {
        return checkAll(load, true);
    }

    private synchronized boolean checkAll(boolean load, boolean verify) {
        MediaEntry cur = head;
        boolean done = true;
        while (cur != null) {
            if ((cur.getStatus(load, verify) & DONE) == 0) {
                done = false;
            }
            cur = cur.next;
        }
        return done;
    }

    /**
     * Checks the error status of all of the images.
     * <p>
     *  检查所有图像的错误状态。
     * 
     * 
     * @return   <code>true</code> if any of the images tracked
     *                  by this media tracker had an error during
     *                  loading; <code>false</code> otherwise
     * @see      java.awt.MediaTracker#isErrorID
     * @see      java.awt.MediaTracker#getErrorsAny
     */
    public synchronized boolean isErrorAny() {
        MediaEntry cur = head;
        while (cur != null) {
            if ((cur.getStatus(false, true) & ERRORED) != 0) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * Returns a list of all media that have encountered an error.
     * <p>
     *  返回遇到错误的所有介质的列表。
     * 
     * 
     * @return       an array of media objects tracked by this
     *                        media tracker that have encountered
     *                        an error, or <code>null</code> if
     *                        there are none with errors
     * @see          java.awt.MediaTracker#isErrorAny
     * @see          java.awt.MediaTracker#getErrorsID
     */
    public synchronized Object[] getErrorsAny() {
        MediaEntry cur = head;
        int numerrors = 0;
        while (cur != null) {
            if ((cur.getStatus(false, true) & ERRORED) != 0) {
                numerrors++;
            }
            cur = cur.next;
        }
        if (numerrors == 0) {
            return null;
        }
        Object errors[] = new Object[numerrors];
        cur = head;
        numerrors = 0;
        while (cur != null) {
            if ((cur.getStatus(false, false) & ERRORED) != 0) {
                errors[numerrors++] = cur.getMedia();
            }
            cur = cur.next;
        }
        return errors;
    }

    /**
     * Starts loading all images tracked by this media tracker. This
     * method waits until all the images being tracked have finished
     * loading.
     * <p>
     * If there is an error while loading or scaling an image, then that
     * image is considered to have finished loading. Use the
     * <code>isErrorAny</code> or <code>isErrorID</code> methods to
     * check for errors.
     * <p>
     *  开始加载此媒体跟踪器跟踪的所有图像。此方法等待,直到正在跟踪的所有图像都已完成加载。
     * <p>
     *  如果在加载或缩放图像时出现错误,则该图像将被视为已完成加载。使用<code> isErrorAny </code>或<code> isErrorID </code>方法检查错误。
     * 
     * 
     * @see         java.awt.MediaTracker#waitForID(int)
     * @see         java.awt.MediaTracker#waitForAll(long)
     * @see         java.awt.MediaTracker#isErrorAny
     * @see         java.awt.MediaTracker#isErrorID
     * @exception   InterruptedException  if any thread has
     *                                     interrupted this thread
     */
    public void waitForAll() throws InterruptedException {
        waitForAll(0);
    }

    /**
     * Starts loading all images tracked by this media tracker. This
     * method waits until all the images being tracked have finished
     * loading, or until the length of time specified in milliseconds
     * by the <code>ms</code> argument has passed.
     * <p>
     * If there is an error while loading or scaling an image, then
     * that image is considered to have finished loading. Use the
     * <code>isErrorAny</code> or <code>isErrorID</code> methods to
     * check for errors.
     * <p>
     * 开始加载此媒体跟踪器跟踪的所有图像。此方法等待直到所有正在跟踪的映像已完成加载,或直到通过<code> ms </code>参数指定的以毫秒为单位的时间长度。
     * <p>
     *  如果在加载或缩放图像时出现错误,则该图像将被视为已完成加载。使用<code> isErrorAny </code>或<code> isErrorID </code>方法检查错误。
     * 
     * 
     * @param       ms       the number of milliseconds to wait
     *                       for the loading to complete
     * @return      <code>true</code> if all images were successfully
     *                       loaded; <code>false</code> otherwise
     * @see         java.awt.MediaTracker#waitForID(int)
     * @see         java.awt.MediaTracker#waitForAll(long)
     * @see         java.awt.MediaTracker#isErrorAny
     * @see         java.awt.MediaTracker#isErrorID
     * @exception   InterruptedException  if any thread has
     *                                     interrupted this thread.
     */
    public synchronized boolean waitForAll(long ms)
        throws InterruptedException
    {
        long end = System.currentTimeMillis() + ms;
        boolean first = true;
        while (true) {
            int status = statusAll(first, first);
            if ((status & LOADING) == 0) {
                return (status == COMPLETE);
            }
            first = false;
            long timeout;
            if (ms == 0) {
                timeout = 0;
            } else {
                timeout = end - System.currentTimeMillis();
                if (timeout <= 0) {
                    return false;
                }
            }
            wait(timeout);
        }
    }

    /**
     * Calculates and returns the bitwise inclusive <b>OR</b> of the
     * status of all media that are tracked by this media tracker.
     * <p>
     * Possible flags defined by the
     * <code>MediaTracker</code> class are <code>LOADING</code>,
     * <code>ABORTED</code>, <code>ERRORED</code>, and
     * <code>COMPLETE</code>. An image that hasn't started
     * loading has zero as its status.
     * <p>
     * If the value of <code>load</code> is <code>true</code>, then
     * this method starts loading any images that are not yet being loaded.
     *
     * <p>
     *  计算并返回此媒体跟踪器跟踪的所有媒体的状态的按位包含<b> OR </b>。
     * <p>
     *  <code> MediaTracker </code>类定义的可能标志是<code> LOADING </code>,<code> ABORTED </code>,<code> ERRORED </code>
     * 和<code> COMPLETE </code >。
     * 未开始加载的图像的状态为零。
     * <p>
     *  如果<code> load </code>的值为<code> true </code>,则此方法将开始加载尚未加载的任何图像。
     * 
     * 
     * @param        load   if <code>true</code>, start loading
     *                            any images that are not yet being loaded
     * @return       the bitwise inclusive <b>OR</b> of the status of
     *                            all of the media being tracked
     * @see          java.awt.MediaTracker#statusID(int, boolean)
     * @see          java.awt.MediaTracker#LOADING
     * @see          java.awt.MediaTracker#ABORTED
     * @see          java.awt.MediaTracker#ERRORED
     * @see          java.awt.MediaTracker#COMPLETE
     */
    public int statusAll(boolean load) {
        return statusAll(load, true);
    }

    private synchronized int statusAll(boolean load, boolean verify) {
        MediaEntry cur = head;
        int status = 0;
        while (cur != null) {
            status = status | cur.getStatus(load, verify);
            cur = cur.next;
        }
        return status;
    }

    /**
     * Checks to see if all images tracked by this media tracker that
     * are tagged with the specified identifier have finished loading.
     * <p>
     * This method does not start loading the images if they are not
     * already loading.
     * <p>
     * If there is an error while loading or scaling an image, then that
     * image is considered to have finished loading. Use the
     * <code>isErrorAny</code> or <code>isErrorID</code> methods to
     * check for errors.
     * <p>
     *  检查此媒体跟踪器跟踪的用指定标识符标记的所有图像是否已完成加载。
     * <p>
     *  如果尚未加载图像,此方法不会开始加载图像。
     * <p>
     *  如果在加载或缩放图像时出现错误,则该图像将被视为已完成加载。使用<code> isErrorAny </code>或<code> isErrorID </code>方法检查错误。
     * 
     * 
     * @param       id   the identifier of the images to check
     * @return      <code>true</code> if all images have finished loading,
     *                       have been aborted, or have encountered
     *                       an error; <code>false</code> otherwise
     * @see         java.awt.MediaTracker#checkID(int, boolean)
     * @see         java.awt.MediaTracker#checkAll()
     * @see         java.awt.MediaTracker#isErrorAny()
     * @see         java.awt.MediaTracker#isErrorID(int)
     */
    public boolean checkID(int id) {
        return checkID(id, false, true);
    }

    /**
     * Checks to see if all images tracked by this media tracker that
     * are tagged with the specified identifier have finished loading.
     * <p>
     * If the value of the <code>load</code> flag is <code>true</code>,
     * then this method starts loading any images that are not yet
     * being loaded.
     * <p>
     * If there is an error while loading or scaling an image, then that
     * image is considered to have finished loading. Use the
     * <code>isErrorAny</code> or <code>isErrorID</code> methods to
     * check for errors.
     * <p>
     *  检查此媒体跟踪器跟踪的用指定标识符标记的所有图像是否已完成加载。
     * <p>
     * 如果<code> load </code>标志的值为<code> true </code>,那么此方法将开始加载尚未加载的任何图像。
     * <p>
     *  如果在加载或缩放图像时出现错误,则该图像将被视为已完成加载。使用<code> isErrorAny </code>或<code> isErrorID </code>方法检查错误。
     * 
     * 
     * @param       id       the identifier of the images to check
     * @param       load     if <code>true</code>, start loading any
     *                       images that are not yet being loaded
     * @return      <code>true</code> if all images have finished loading,
     *                       have been aborted, or have encountered
     *                       an error; <code>false</code> otherwise
     * @see         java.awt.MediaTracker#checkID(int, boolean)
     * @see         java.awt.MediaTracker#checkAll()
     * @see         java.awt.MediaTracker#isErrorAny()
     * @see         java.awt.MediaTracker#isErrorID(int)
     */
    public boolean checkID(int id, boolean load) {
        return checkID(id, load, true);
    }

    private synchronized boolean checkID(int id, boolean load, boolean verify)
    {
        MediaEntry cur = head;
        boolean done = true;
        while (cur != null) {
            if (cur.getID() == id
                && (cur.getStatus(load, verify) & DONE) == 0)
            {
                done = false;
            }
            cur = cur.next;
        }
        return done;
    }

    /**
     * Checks the error status of all of the images tracked by this
     * media tracker with the specified identifier.
     * <p>
     *  使用指定的标识符检查此媒体跟踪器跟踪的所有图像的错误状态。
     * 
     * 
     * @param        id   the identifier of the images to check
     * @return       <code>true</code> if any of the images with the
     *                          specified identifier had an error during
     *                          loading; <code>false</code> otherwise
     * @see          java.awt.MediaTracker#isErrorAny
     * @see          java.awt.MediaTracker#getErrorsID
     */
    public synchronized boolean isErrorID(int id) {
        MediaEntry cur = head;
        while (cur != null) {
            if (cur.getID() == id
                && (cur.getStatus(false, true) & ERRORED) != 0)
            {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * Returns a list of media with the specified ID that
     * have encountered an error.
     * <p>
     *  返回具有指定ID且已遇到错误的介质列表。
     * 
     * 
     * @param       id   the identifier of the images to check
     * @return      an array of media objects tracked by this media
     *                       tracker with the specified identifier
     *                       that have encountered an error, or
     *                       <code>null</code> if there are none with errors
     * @see         java.awt.MediaTracker#isErrorID
     * @see         java.awt.MediaTracker#isErrorAny
     * @see         java.awt.MediaTracker#getErrorsAny
     */
    public synchronized Object[] getErrorsID(int id) {
        MediaEntry cur = head;
        int numerrors = 0;
        while (cur != null) {
            if (cur.getID() == id
                && (cur.getStatus(false, true) & ERRORED) != 0)
            {
                numerrors++;
            }
            cur = cur.next;
        }
        if (numerrors == 0) {
            return null;
        }
        Object errors[] = new Object[numerrors];
        cur = head;
        numerrors = 0;
        while (cur != null) {
            if (cur.getID() == id
                && (cur.getStatus(false, false) & ERRORED) != 0)
            {
                errors[numerrors++] = cur.getMedia();
            }
            cur = cur.next;
        }
        return errors;
    }

    /**
     * Starts loading all images tracked by this media tracker with the
     * specified identifier. This method waits until all the images with
     * the specified identifier have finished loading.
     * <p>
     * If there is an error while loading or scaling an image, then that
     * image is considered to have finished loading. Use the
     * <code>isErrorAny</code> and <code>isErrorID</code> methods to
     * check for errors.
     * <p>
     *  开始使用指定的标识符加载此媒体跟踪器跟踪的所有图像。此方法等待直到具有指定标识符的所有图像都已完成加载。
     * <p>
     *  如果在加载或缩放图像时出现错误,则该图像将被视为已完成加载。使用<code> isErrorAny </code>和<code> isErrorID </code>方法检查错误。
     * 
     * 
     * @param         id   the identifier of the images to check
     * @see           java.awt.MediaTracker#waitForAll
     * @see           java.awt.MediaTracker#isErrorAny()
     * @see           java.awt.MediaTracker#isErrorID(int)
     * @exception     InterruptedException  if any thread has
     *                          interrupted this thread.
     */
    public void waitForID(int id) throws InterruptedException {
        waitForID(id, 0);
    }

    /**
     * Starts loading all images tracked by this media tracker with the
     * specified identifier. This method waits until all the images with
     * the specified identifier have finished loading, or until the
     * length of time specified in milliseconds by the <code>ms</code>
     * argument has passed.
     * <p>
     * If there is an error while loading or scaling an image, then that
     * image is considered to have finished loading. Use the
     * <code>statusID</code>, <code>isErrorID</code>, and
     * <code>isErrorAny</code> methods to check for errors.
     * <p>
     *  开始使用指定的标识符加载此媒体跟踪器跟踪的所有图像。此方法等待,直到所有具有指定标识符的映像已完成加载,或直到通过<code> ms </code>参数指定的以毫秒为单位的时间长度。
     * <p>
     *  如果在加载或缩放图像时出现错误,则该图像将被视为已完成加载。
     * 使用<code> statusID </code>,<code> isErrorID </code>和<code> isErrorAny </code>方法检查错误。
     * 
     * 
     * @param         id   the identifier of the images to check
     * @param         ms   the length of time, in milliseconds, to wait
     *                           for the loading to complete
     * @see           java.awt.MediaTracker#waitForAll
     * @see           java.awt.MediaTracker#waitForID(int)
     * @see           java.awt.MediaTracker#statusID
     * @see           java.awt.MediaTracker#isErrorAny()
     * @see           java.awt.MediaTracker#isErrorID(int)
     * @exception     InterruptedException  if any thread has
     *                          interrupted this thread.
     */
    public synchronized boolean waitForID(int id, long ms)
        throws InterruptedException
    {
        long end = System.currentTimeMillis() + ms;
        boolean first = true;
        while (true) {
            int status = statusID(id, first, first);
            if ((status & LOADING) == 0) {
                return (status == COMPLETE);
            }
            first = false;
            long timeout;
            if (ms == 0) {
                timeout = 0;
            } else {
                timeout = end - System.currentTimeMillis();
                if (timeout <= 0) {
                    return false;
                }
            }
            wait(timeout);
        }
    }

    /**
     * Calculates and returns the bitwise inclusive <b>OR</b> of the
     * status of all media with the specified identifier that are
     * tracked by this media tracker.
     * <p>
     * Possible flags defined by the
     * <code>MediaTracker</code> class are <code>LOADING</code>,
     * <code>ABORTED</code>, <code>ERRORED</code>, and
     * <code>COMPLETE</code>. An image that hasn't started
     * loading has zero as its status.
     * <p>
     * If the value of <code>load</code> is <code>true</code>, then
     * this method starts loading any images that are not yet being loaded.
     * <p>
     * 计算并返回包含此媒体跟踪器所跟踪的指定标识符的所有媒体的状态的按位包含<b> OR </b>。
     * <p>
     *  <code> MediaTracker </code>类定义的可能标志是<code> LOADING </code>,<code> ABORTED </code>,<code> ERRORED </code>
     * 和<code> COMPLETE </code >。
     * 未开始加载的图像的状态为零。
     * <p>
     *  如果<code> load </code>的值为<code> true </code>,则此方法将开始加载尚未加载的任何图像。
     * 
     * 
     * @param        id   the identifier of the images to check
     * @param        load   if <code>true</code>, start loading
     *                            any images that are not yet being loaded
     * @return       the bitwise inclusive <b>OR</b> of the status of
     *                            all of the media with the specified
     *                            identifier that are being tracked
     * @see          java.awt.MediaTracker#statusAll(boolean)
     * @see          java.awt.MediaTracker#LOADING
     * @see          java.awt.MediaTracker#ABORTED
     * @see          java.awt.MediaTracker#ERRORED
     * @see          java.awt.MediaTracker#COMPLETE
     */
    public int statusID(int id, boolean load) {
        return statusID(id, load, true);
    }

    private synchronized int statusID(int id, boolean load, boolean verify) {
        MediaEntry cur = head;
        int status = 0;
        while (cur != null) {
            if (cur.getID() == id) {
                status = status | cur.getStatus(load, verify);
            }
            cur = cur.next;
        }
        return status;
    }

    /**
     * Removes the specified image from this media tracker.
     * All instances of the specified image are removed,
     * regardless of scale or ID.
     * <p>
     *  从此媒体跟踪器中删除指定的图像。将删除指定图像的所有实例,而不考虑比例或ID。
     * 
     * 
     * @param   image     the image to be removed
     * @see     java.awt.MediaTracker#removeImage(java.awt.Image, int)
     * @see     java.awt.MediaTracker#removeImage(java.awt.Image, int, int, int)
     * @since   JDK1.1
     */
    public synchronized void removeImage(Image image) {
        removeImageImpl(image);
        Image rvImage = getResolutionVariant(image);
        if (rvImage != null) {
            removeImageImpl(rvImage);
        }
        notifyAll();    // Notify in case remaining images are "done".
    }

    private void removeImageImpl(Image image) {
        MediaEntry cur = head;
        MediaEntry prev = null;
        while (cur != null) {
            MediaEntry next = cur.next;
            if (cur.getMedia() == image) {
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                cur.cancel();
            } else {
                prev = cur;
            }
            cur = next;
        }
    }

    /**
     * Removes the specified image from the specified tracking
     * ID of this media tracker.
     * All instances of <code>Image</code> being tracked
     * under the specified ID are removed regardless of scale.
     * <p>
     *  从此媒体跟踪器的指定跟踪ID中删除指定的图片。将删除在指定ID下跟踪的<code> Image </code>的所有实例,而不考虑比例。
     * 
     * 
     * @param      image the image to be removed
     * @param      id the tracking ID from which to remove the image
     * @see        java.awt.MediaTracker#removeImage(java.awt.Image)
     * @see        java.awt.MediaTracker#removeImage(java.awt.Image, int, int, int)
     * @since      JDK1.1
     */
    public synchronized void removeImage(Image image, int id) {
        removeImageImpl(image, id);
        Image rvImage = getResolutionVariant(image);
        if (rvImage != null) {
            removeImageImpl(rvImage, id);
        }
        notifyAll();    // Notify in case remaining images are "done".
    }

    private void removeImageImpl(Image image, int id) {
        MediaEntry cur = head;
        MediaEntry prev = null;
        while (cur != null) {
            MediaEntry next = cur.next;
            if (cur.getID() == id && cur.getMedia() == image) {
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                cur.cancel();
            } else {
                prev = cur;
            }
            cur = next;
        }
    }

    /**
     * Removes the specified image with the specified
     * width, height, and ID from this media tracker.
     * Only the specified instance (with any duplicates) is removed.
     * <p>
     *  从此媒体跟踪器中删除指定宽度,高度和ID的指定图像。只有指定的实例(包含任何重复项)被删除。
     * 
     * 
     * @param   image the image to be removed
     * @param   id the tracking ID from which to remove the image
     * @param   width the width to remove (-1 for unscaled)
     * @param   height the height to remove (-1 for unscaled)
     * @see     java.awt.MediaTracker#removeImage(java.awt.Image)
     * @see     java.awt.MediaTracker#removeImage(java.awt.Image, int)
     * @since   JDK1.1
     */
    public synchronized void removeImage(Image image, int id,
                                         int width, int height) {
        removeImageImpl(image, id, width, height);
        Image rvImage = getResolutionVariant(image);
        if (rvImage != null) {
            removeImageImpl(rvImage, id,
                    width == -1 ? -1 : 2 * width,
                    height == -1 ? -1 : 2 * height);
        }
        notifyAll();    // Notify in case remaining images are "done".
    }

    private void removeImageImpl(Image image, int id, int width, int height) {
        MediaEntry cur = head;
        MediaEntry prev = null;
        while (cur != null) {
            MediaEntry next = cur.next;
            if (cur.getID() == id && cur instanceof ImageMediaEntry
                && ((ImageMediaEntry) cur).matches(image, width, height))
            {
                if (prev == null) {
                    head = next;
                } else {
                    prev.next = next;
                }
                cur.cancel();
            } else {
                prev = cur;
            }
            cur = next;
        }
    }

    synchronized void setDone() {
        notifyAll();
    }

    private static Image getResolutionVariant(Image image) {
        if (image instanceof MultiResolutionToolkitImage) {
            return ((MultiResolutionToolkitImage) image).getResolutionVariant();
        }
        return null;
    }
}

abstract class MediaEntry {
    MediaTracker tracker;
    int ID;
    MediaEntry next;

    int status;
    boolean cancelled;

    MediaEntry(MediaTracker mt, int id) {
        tracker = mt;
        ID = id;
    }

    abstract Object getMedia();

    static MediaEntry insert(MediaEntry head, MediaEntry me) {
        MediaEntry cur = head;
        MediaEntry prev = null;
        while (cur != null) {
            if (cur.ID > me.ID) {
                break;
            }
            prev = cur;
            cur = cur.next;
        }
        me.next = cur;
        if (prev == null) {
            head = me;
        } else {
            prev.next = me;
        }
        return head;
    }

    int getID() {
        return ID;
    }

    abstract void startLoad();

    void cancel() {
        cancelled = true;
    }

    static final int LOADING = MediaTracker.LOADING;
    static final int ABORTED = MediaTracker.ABORTED;
    static final int ERRORED = MediaTracker.ERRORED;
    static final int COMPLETE = MediaTracker.COMPLETE;

    static final int LOADSTARTED = (LOADING | ERRORED | COMPLETE);
    static final int DONE = (ABORTED | ERRORED | COMPLETE);

    synchronized int getStatus(boolean doLoad, boolean doVerify) {
        if (doLoad && ((status & LOADSTARTED) == 0)) {
            status = (status & ~ABORTED) | LOADING;
            startLoad();
        }
        return status;
    }

    void setStatus(int flag) {
        synchronized (this) {
            status = flag;
        }
        tracker.setDone();
    }
}

class ImageMediaEntry extends MediaEntry implements ImageObserver,
java.io.Serializable {
    Image image;
    int width;
    int height;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     */
    private static final long serialVersionUID = 4739377000350280650L;

    ImageMediaEntry(MediaTracker mt, Image img, int c, int w, int h) {
        super(mt, c);
        image = img;
        width = w;
        height = h;
    }

    boolean matches(Image img, int w, int h) {
        return (image == img && width == w && height == h);
    }

    Object getMedia() {
        return image;
    }

    synchronized int getStatus(boolean doLoad, boolean doVerify) {
        if (doVerify) {
            int flags = tracker.target.checkImage(image, width, height, null);
            int s = parseflags(flags);
            if (s == 0) {
                if ((status & (ERRORED | COMPLETE)) != 0) {
                    setStatus(ABORTED);
                }
            } else if (s != status) {
                setStatus(s);
            }
        }
        return super.getStatus(doLoad, doVerify);
    }

    void startLoad() {
        if (tracker.target.prepareImage(image, width, height, this)) {
            setStatus(COMPLETE);
        }
    }

    int parseflags(int infoflags) {
        if ((infoflags & ERROR) != 0) {
            return ERRORED;
        } else if ((infoflags & ABORT) != 0) {
            return ABORTED;
        } else if ((infoflags & (ALLBITS | FRAMEBITS)) != 0) {
            return COMPLETE;
        }
        return 0;
    }

    public boolean imageUpdate(Image img, int infoflags,
                               int x, int y, int w, int h) {
        if (cancelled) {
            return false;
        }
        int s = parseflags(infoflags);
        if (s != 0 && s != status) {
            setStatus(s);
        }
        return ((status & LOADING) != 0);
    }
}
