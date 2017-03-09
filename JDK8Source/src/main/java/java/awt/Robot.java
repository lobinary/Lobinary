/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2014, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.peer.RobotPeer;
import java.lang.reflect.InvocationTargetException;
import sun.awt.ComponentFactory;
import sun.awt.SunToolkit;
import sun.awt.image.SunWritableRaster;
import sun.security.util.SecurityConstants;

/**
 * This class is used to generate native system input events
 * for the purposes of test automation, self-running demos, and
 * other applications where control of the mouse and keyboard
 * is needed. The primary purpose of Robot is to facilitate
 * automated testing of Java platform implementations.
 * <p>
 * Using the class to generate input events differs from posting
 * events to the AWT event queue or AWT components in that the
 * events are generated in the platform's native input
 * queue. For example, <code>Robot.mouseMove</code> will actually move
 * the mouse cursor instead of just generating mouse move events.
 * <p>
 * Note that some platforms require special privileges or extensions
 * to access low-level input control. If the current platform configuration
 * does not allow input control, an <code>AWTException</code> will be thrown
 * when trying to construct Robot objects. For example, X-Window systems
 * will throw the exception if the XTEST 2.2 standard extension is not supported
 * (or not enabled) by the X server.
 * <p>
 * Applications that use Robot for purposes other than self-testing should
 * handle these error conditions gracefully.
 *
 * <p>
 *  该类用于生成本地系统输入事件,用于测试自动化,自运行演示以及需要控制鼠标和键盘的其他应用程序。 Robot的主要目的是促进Java平台实现的自动化测试。
 * <p>
 *  使用类来生成输入事件不同于将事件发布到AWT事件队列或AWT组件,因为事件是在平台的本机输入队列中生成的。
 * 例如,<code> Robot.mouseMove </code>实际上会移动鼠标光标,而不仅仅是生成鼠标移动事件。
 * <p>
 *  注意,一些平台需要特殊的权限或扩展来访问低级输入控制。如果当前平台配置不允许输入控制,则当尝试构造Robot对象时将抛出<code> AWTException </code>。
 * 例如,如果X服务器不支持(或未启用)XTEST 2.2标准扩展,X-Window系统将抛出异常。
 * <p>
 *  使用Robot用于自我测试以外的目的应用程序应该正常处理这些错误条件。
 * 
 * 
 * @author      Robi Khan
 * @since       1.3
 */
public class Robot {
    private static final int MAX_DELAY = 60000;
    private RobotPeer peer;
    private boolean isAutoWaitForIdle = false;
    private int autoDelay = 0;
    private static int LEGAL_BUTTON_MASK = 0;

    private DirectColorModel screenCapCM = null;

    /**
     * Constructs a Robot object in the coordinate system of the primary screen.
     * <p>
     *
     * <p>
     *  在主屏幕的坐标系中构建Robot对象。
     * <p>
     * 
     * 
     * @throws  AWTException if the platform configuration does not allow
     * low-level input control.  This exception is always thrown when
     * GraphicsEnvironment.isHeadless() returns true
     * @throws  SecurityException if <code>createRobot</code> permission is not granted
     * @see     java.awt.GraphicsEnvironment#isHeadless
     * @see     SecurityManager#checkPermission
     * @see     AWTPermission
     */
    public Robot() throws AWTException {
        if (GraphicsEnvironment.isHeadless()) {
            throw new AWTException("headless environment");
        }
        init(GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice());
    }

    /**
     * Creates a Robot for the given screen device. Coordinates passed
     * to Robot method calls like mouseMove and createScreenCapture will
     * be interpreted as being in the same coordinate system as the
     * specified screen. Note that depending on the platform configuration,
     * multiple screens may either:
     * <ul>
     * <li>share the same coordinate system to form a combined virtual screen</li>
     * <li>use different coordinate systems to act as independent screens</li>
     * </ul>
     * This constructor is meant for the latter case.
     * <p>
     * If screen devices are reconfigured such that the coordinate system is
     * affected, the behavior of existing Robot objects is undefined.
     *
     * <p>
     * 为给定的屏幕设备创建机器人。传递给robot方法调用的坐标(如mouseMove和createScreenCapture)将被解释为与指定屏幕位于同一坐标系中。注意,根据平台配置,多个屏幕可以：
     * <ul>
     *  <li>共享相同的坐标系统以形成组合的虚拟屏幕</li> <li>使用不同的坐标系统作为独立的屏幕</li>
     * </ul>
     *  这个构造函数用于后一种情况。
     * <p>
     *  如果重新配置屏幕设备以使坐标系受影响,则未定义现有机器人对象的行为。
     * 
     * 
     * @param screen    A screen GraphicsDevice indicating the coordinate
     *                  system the Robot will operate in.
     * @throws  AWTException if the platform configuration does not allow
     * low-level input control.  This exception is always thrown when
     * GraphicsEnvironment.isHeadless() returns true.
     * @throws  IllegalArgumentException if <code>screen</code> is not a screen
     *          GraphicsDevice.
     * @throws  SecurityException if <code>createRobot</code> permission is not granted
     * @see     java.awt.GraphicsEnvironment#isHeadless
     * @see     GraphicsDevice
     * @see     SecurityManager#checkPermission
     * @see     AWTPermission
     */
    public Robot(GraphicsDevice screen) throws AWTException {
        checkIsScreenDevice(screen);
        init(screen);
    }

    private void init(GraphicsDevice screen) throws AWTException {
        checkRobotAllowed();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        if (toolkit instanceof ComponentFactory) {
            peer = ((ComponentFactory)toolkit).createRobot(this, screen);
            disposer = new RobotDisposer(peer);
            sun.java2d.Disposer.addRecord(anchor, disposer);
        }
        initLegalButtonMask();
    }

    private static synchronized void initLegalButtonMask() {
        if (LEGAL_BUTTON_MASK != 0) return;

        int tmpMask = 0;
        if (Toolkit.getDefaultToolkit().areExtraMouseButtonsEnabled()){
            if (Toolkit.getDefaultToolkit() instanceof SunToolkit) {
                final int buttonsNumber = ((SunToolkit)(Toolkit.getDefaultToolkit())).getNumberOfButtons();
                for (int i = 0; i < buttonsNumber; i++){
                    tmpMask |= InputEvent.getMaskForButton(i+1);
                }
            }
        }
        tmpMask |= InputEvent.BUTTON1_MASK|
            InputEvent.BUTTON2_MASK|
            InputEvent.BUTTON3_MASK|
            InputEvent.BUTTON1_DOWN_MASK|
            InputEvent.BUTTON2_DOWN_MASK|
            InputEvent.BUTTON3_DOWN_MASK;
        LEGAL_BUTTON_MASK = tmpMask;
    }

    /* determine if the security policy allows Robot's to be created */
    private void checkRobotAllowed() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(SecurityConstants.AWT.CREATE_ROBOT_PERMISSION);
        }
    }

    /* check if the given device is a screen device */
    private void checkIsScreenDevice(GraphicsDevice device) {
        if (device == null || device.getType() != GraphicsDevice.TYPE_RASTER_SCREEN) {
            throw new IllegalArgumentException("not a valid screen device");
        }
    }

    private transient Object anchor = new Object();

    static class RobotDisposer implements sun.java2d.DisposerRecord {
        private final RobotPeer peer;
        public RobotDisposer(RobotPeer peer) {
            this.peer = peer;
        }
        public void dispose() {
            if (peer != null) {
                peer.dispose();
            }
        }
    }

    private transient RobotDisposer disposer;

    /**
     * Moves mouse pointer to given screen coordinates.
     * <p>
     *  将鼠标指针移动到给定的屏幕坐标。
     * 
     * 
     * @param x         X position
     * @param y         Y position
     */
    public synchronized void mouseMove(int x, int y) {
        peer.mouseMove(x, y);
        afterEvent();
    }

    /**
     * Presses one or more mouse buttons.  The mouse buttons should
     * be released using the {@link #mouseRelease(int)} method.
     *
     * <p>
     *  按一个或多个鼠标按钮。鼠标按钮应该使用{@link #mouseRelease(int)}方法释放。
     * 
     * 
     * @param buttons the Button mask; a combination of one or more
     * mouse button masks.
     * <p>
     * It is allowed to use only a combination of valid values as a {@code buttons} parameter.
     * A valid combination consists of {@code InputEvent.BUTTON1_DOWN_MASK},
     * {@code InputEvent.BUTTON2_DOWN_MASK}, {@code InputEvent.BUTTON3_DOWN_MASK}
     * and values returned by the
     * {@link InputEvent#getMaskForButton(int) InputEvent.getMaskForButton(button)} method.
     *
     * The valid combination also depends on a
     * {@link Toolkit#areExtraMouseButtonsEnabled() Toolkit.areExtraMouseButtonsEnabled()} value as follows:
     * <ul>
     * <li> If support for extended mouse buttons is
     * {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
     * then it is allowed to use only the following standard button masks:
     * {@code InputEvent.BUTTON1_DOWN_MASK}, {@code InputEvent.BUTTON2_DOWN_MASK},
     * {@code InputEvent.BUTTON3_DOWN_MASK}.
     * <li> If support for extended mouse buttons is
     * {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
     * then it is allowed to use the standard button masks
     * and masks for existing extended mouse buttons, if the mouse has more then three buttons.
     * In that way, it is allowed to use the button masks corresponding to the buttons
     * in the range from 1 to {@link java.awt.MouseInfo#getNumberOfButtons() MouseInfo.getNumberOfButtons()}.
     * <br>
     * It is recommended to use the {@link InputEvent#getMaskForButton(int) InputEvent.getMaskForButton(button)}
     * method to obtain the mask for any mouse button by its number.
     * </ul>
     * <p>
     * The following standard button masks are also accepted:
     * <ul>
     * <li>{@code InputEvent.BUTTON1_MASK}
     * <li>{@code InputEvent.BUTTON2_MASK}
     * <li>{@code InputEvent.BUTTON3_MASK}
     * </ul>
     * However, it is recommended to use {@code InputEvent.BUTTON1_DOWN_MASK},
     * {@code InputEvent.BUTTON2_DOWN_MASK},  {@code InputEvent.BUTTON3_DOWN_MASK} instead.
     * Either extended {@code _DOWN_MASK} or old {@code _MASK} values
     * should be used, but both those models should not be mixed.
     * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button
     *         and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
     * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button
     *         that does not exist on the mouse and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
     * @see #mouseRelease(int)
     * @see InputEvent#getMaskForButton(int)
     * @see Toolkit#areExtraMouseButtonsEnabled()
     * @see java.awt.MouseInfo#getNumberOfButtons()
     * @see java.awt.event.MouseEvent
     */
    public synchronized void mousePress(int buttons) {
        checkButtonsArgument(buttons);
        peer.mousePress(buttons);
        afterEvent();
    }

    /**
     * Releases one or more mouse buttons.
     *
     * <p>
     *  释放一个或多个鼠标按钮。
     * 
     * 
     * @param buttons the Button mask; a combination of one or more
     * mouse button masks.
     * <p>
     * It is allowed to use only a combination of valid values as a {@code buttons} parameter.
     * A valid combination consists of {@code InputEvent.BUTTON1_DOWN_MASK},
     * {@code InputEvent.BUTTON2_DOWN_MASK}, {@code InputEvent.BUTTON3_DOWN_MASK}
     * and values returned by the
     * {@link InputEvent#getMaskForButton(int) InputEvent.getMaskForButton(button)} method.
     *
     * The valid combination also depends on a
     * {@link Toolkit#areExtraMouseButtonsEnabled() Toolkit.areExtraMouseButtonsEnabled()} value as follows:
     * <ul>
     * <li> If the support for extended mouse buttons is
     * {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
     * then it is allowed to use only the following standard button masks:
     * {@code InputEvent.BUTTON1_DOWN_MASK}, {@code InputEvent.BUTTON2_DOWN_MASK},
     * {@code InputEvent.BUTTON3_DOWN_MASK}.
     * <li> If the support for extended mouse buttons is
     * {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
     * then it is allowed to use the standard button masks
     * and masks for existing extended mouse buttons, if the mouse has more then three buttons.
     * In that way, it is allowed to use the button masks corresponding to the buttons
     * in the range from 1 to {@link java.awt.MouseInfo#getNumberOfButtons() MouseInfo.getNumberOfButtons()}.
     * <br>
     * It is recommended to use the {@link InputEvent#getMaskForButton(int) InputEvent.getMaskForButton(button)}
     * method to obtain the mask for any mouse button by its number.
     * </ul>
     * <p>
     * The following standard button masks are also accepted:
     * <ul>
     * <li>{@code InputEvent.BUTTON1_MASK}
     * <li>{@code InputEvent.BUTTON2_MASK}
     * <li>{@code InputEvent.BUTTON3_MASK}
     * </ul>
     * However, it is recommended to use {@code InputEvent.BUTTON1_DOWN_MASK},
     * {@code InputEvent.BUTTON2_DOWN_MASK},  {@code InputEvent.BUTTON3_DOWN_MASK} instead.
     * Either extended {@code _DOWN_MASK} or old {@code _MASK} values
     * should be used, but both those models should not be mixed.
     * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button
     *         and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() disabled} by Java
     * @throws IllegalArgumentException if the {@code buttons} mask contains the mask for extra mouse button
     *         that does not exist on the mouse and support for extended mouse buttons is {@link Toolkit#areExtraMouseButtonsEnabled() enabled} by Java
     * @see #mousePress(int)
     * @see InputEvent#getMaskForButton(int)
     * @see Toolkit#areExtraMouseButtonsEnabled()
     * @see java.awt.MouseInfo#getNumberOfButtons()
     * @see java.awt.event.MouseEvent
     */
    public synchronized void mouseRelease(int buttons) {
        checkButtonsArgument(buttons);
        peer.mouseRelease(buttons);
        afterEvent();
    }

    private void checkButtonsArgument(int buttons) {
        if ( (buttons|LEGAL_BUTTON_MASK) != LEGAL_BUTTON_MASK ) {
            throw new IllegalArgumentException("Invalid combination of button flags");
        }
    }

    /**
     * Rotates the scroll wheel on wheel-equipped mice.
     *
     * <p>
     *  旋转装有轮子的鼠标上的滚轮。
     * 
     * 
     * @param wheelAmt  number of "notches" to move the mouse wheel
     *                  Negative values indicate movement up/away from the user,
     *                  positive values indicate movement down/towards the user.
     *
     * @since 1.4
     */
    public synchronized void mouseWheel(int wheelAmt) {
        peer.mouseWheel(wheelAmt);
        afterEvent();
    }

    /**
     * Presses a given key.  The key should be released using the
     * <code>keyRelease</code> method.
     * <p>
     * Key codes that have more than one physical key associated with them
     * (e.g. <code>KeyEvent.VK_SHIFT</code> could mean either the
     * left or right shift key) will map to the left key.
     *
     * <p>
     *  按给定键。键应该使用<code> keyRelease </code>方法释放。
     * <p>
     *  具有多个与其相关联的物理键的键代码(例如,<code> KeyEvent.VK_SHIFT </code>可以表示左或右Shift键)将映射到左键。
     * 
     * 
     * @param   keycode Key to press (e.g. <code>KeyEvent.VK_A</code>)
     * @throws  IllegalArgumentException if <code>keycode</code> is not
     *          a valid key
     * @see     #keyRelease(int)
     * @see     java.awt.event.KeyEvent
     */
    public synchronized void keyPress(int keycode) {
        checkKeycodeArgument(keycode);
        peer.keyPress(keycode);
        afterEvent();
    }

    /**
     * Releases a given key.
     * <p>
     * Key codes that have more than one physical key associated with them
     * (e.g. <code>KeyEvent.VK_SHIFT</code> could mean either the
     * left or right shift key) will map to the left key.
     *
     * <p>
     *  释放给定的键。
     * <p>
     *  具有多个与其相关联的物理键的键代码(例如,<code> KeyEvent.VK_SHIFT </code>可以表示左或右Shift键)将映射到左键。
     * 
     * 
     * @param   keycode Key to release (e.g. <code>KeyEvent.VK_A</code>)
     * @throws  IllegalArgumentException if <code>keycode</code> is not a
     *          valid key
     * @see  #keyPress(int)
     * @see     java.awt.event.KeyEvent
     */
    public synchronized void keyRelease(int keycode) {
        checkKeycodeArgument(keycode);
        peer.keyRelease(keycode);
        afterEvent();
    }

    private void checkKeycodeArgument(int keycode) {
        // rather than build a big table or switch statement here, we'll
        // just check that the key isn't VK_UNDEFINED and assume that the
        // peer implementations will throw an exception for other bogus
        // values e.g. -1, 999999
        if (keycode == KeyEvent.VK_UNDEFINED) {
            throw new IllegalArgumentException("Invalid key code");
        }
    }

    /**
     * Returns the color of a pixel at the given screen coordinates.
     * <p>
     *  返回给定屏幕坐标处像素的颜色。
     * 
     * 
     * @param   x       X position of pixel
     * @param   y       Y position of pixel
     * @return  Color of the pixel
     */
    public synchronized Color getPixelColor(int x, int y) {
        Color color = new Color(peer.getRGBPixel(x, y));
        return color;
    }

    /**
     * Creates an image containing pixels read from the screen.  This image does
     * not include the mouse cursor.
     * <p>
     * 创建包含从屏幕读取的像素的图像。此图像不包括鼠标光标。
     * 
     * 
     * @param   screenRect      Rect to capture in screen coordinates
     * @return  The captured image
     * @throws  IllegalArgumentException if <code>screenRect</code> width and height are not greater than zero
     * @throws  SecurityException if <code>readDisplayPixels</code> permission is not granted
     * @see     SecurityManager#checkPermission
     * @see     AWTPermission
     */
    public synchronized BufferedImage createScreenCapture(Rectangle screenRect) {
        checkScreenCaptureAllowed();

        checkValidRect(screenRect);

        BufferedImage image;
        DataBufferInt buffer;
        WritableRaster raster;

        if (screenCapCM == null) {
            /*
             * Fix for 4285201
             * Create a DirectColorModel equivalent to the default RGB ColorModel,
             * except with no Alpha component.
             * <p>
             *  修正为4285201创建一个DirectColorModel等效于默认的RGB ColorModel,除了没有Alpha组件。
             * 
             */

            screenCapCM = new DirectColorModel(24,
                                               /* red mask */    0x00FF0000,
                                               /* <p>
                                               /* 
                                               /* green mask */  0x0000FF00,
                                               /* <p>
                                               /* 
                                               /* blue mask */   0x000000FF);
        }

        // need to sync the toolkit prior to grabbing the pixels since in some
        // cases rendering to the screen may be delayed
        Toolkit.getDefaultToolkit().sync();

        int pixels[];
        int[] bandmasks = new int[3];

        pixels = peer.getRGBPixels(screenRect);
        buffer = new DataBufferInt(pixels, pixels.length);

        bandmasks[0] = screenCapCM.getRedMask();
        bandmasks[1] = screenCapCM.getGreenMask();
        bandmasks[2] = screenCapCM.getBlueMask();

        raster = Raster.createPackedRaster(buffer, screenRect.width, screenRect.height, screenRect.width, bandmasks, null);
        SunWritableRaster.makeTrackable(buffer);

        image = new BufferedImage(screenCapCM, raster, false, null);

        return image;
    }

    private static void checkValidRect(Rectangle rect) {
        if (rect.width <= 0 || rect.height <= 0) {
            throw new IllegalArgumentException("Rectangle width and height must be > 0");
        }
    }

    private static void checkScreenCaptureAllowed() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(
                SecurityConstants.AWT.READ_DISPLAY_PIXELS_PERMISSION);
        }
    }

    /*
     * Called after an event is generated
     * <p>
     *  }}
     * 
     *  //需要在抓取像素之前同步工具包,因为在某些情况下,渲染到屏幕可能会被延迟Toolkit.getDefaultToolkit()。sync();
     * 
     *  int pixels []; int [] bandmasks = new int [3];
     * 
     *  pixels = peer.getRGBPixels(screenRect); buffer = new DataBufferInt(pixels,pixels.length);
     * 
     *  bandmasks [0] = screenCapCM.getRedMask(); bandmasks [1] = screenCapCM.getGreenMask(); bandmasks [2] 
     * = screenCapCM.getBlueMask();。
     * 
     *  raster = Raster.createPackedRaster(buffer,screenRect.width,screenRect.height,screenRect.width,bandma
     * sks,null); SunWritableRaster.makeTrackable(buffer);。
     * 
     *  image = new BufferedImage(screenCapCM,raster,false,null);
     * 
     *  返回图像; }}
     * 
     *  private Rectangle(Rectangle rect){if(rect.width <= 0 || rect.height <= 0){throw new IllegalArgumentException("Rectangle width and height must be> 0"); }
     * }。
     * 
     *  private static void checkScreenCaptureAllowed(){SecurityManager security = System.getSecurityManager(); if(security！= null){security.checkPermission(SecurityConstants.AWT.READ_DISPLAY_PIXELS_PERMISSION); }
     * }。
     * 
     *  / *在生成事件后调用
     * 
     */
    private void afterEvent() {
        autoWaitForIdle();
        autoDelay();
    }

    /**
     * Returns whether this Robot automatically invokes <code>waitForIdle</code>
     * after generating an event.
     * <p>
     *  返回此Robot是否在生成事件后自动调用<code> waitForIdle </code>。
     * 
     * 
     * @return Whether <code>waitForIdle</code> is automatically called
     */
    public synchronized boolean isAutoWaitForIdle() {
        return isAutoWaitForIdle;
    }

    /**
     * Sets whether this Robot automatically invokes <code>waitForIdle</code>
     * after generating an event.
     * <p>
     * 设置此Robot在生成事件后是否自动调用<code> waitForIdle </code>。
     * 
     * 
     * @param   isOn    Whether <code>waitForIdle</code> is automatically invoked
     */
    public synchronized void setAutoWaitForIdle(boolean isOn) {
        isAutoWaitForIdle = isOn;
    }

    /*
     * Calls waitForIdle after every event if so desired.
     * <p>
     *  如果需要,在每个事件后调用waitForIdle。
     * 
     */
    private void autoWaitForIdle() {
        if (isAutoWaitForIdle) {
            waitForIdle();
        }
    }

    /**
     * Returns the number of milliseconds this Robot sleeps after generating an event.
     * <p>
     *  返回此机器人在生成事件后休眠的毫秒数。
     * 
     */
    public synchronized int getAutoDelay() {
        return autoDelay;
    }

    /**
     * Sets the number of milliseconds this Robot sleeps after generating an event.
     * <p>
     *  设置机器人在生成事件后休眠的毫秒数。
     * 
     * 
     * @throws  IllegalArgumentException If <code>ms</code> is not between 0 and 60,000 milliseconds inclusive
     */
    public synchronized void setAutoDelay(int ms) {
        checkDelayArgument(ms);
        autoDelay = ms;
    }

    /*
     * Automatically sleeps for the specified interval after event generated.
     * <p>
     *  在事件生成后自动休眠指定的时间间隔。
     * 
     */
    private void autoDelay() {
        delay(autoDelay);
    }

    /**
     * Sleeps for the specified time.
     * To catch any <code>InterruptedException</code>s that occur,
     * <code>Thread.sleep()</code> may be used instead.
     * <p>
     *  睡眠指定的时间。要捕获发生的任何<code> InterruptedException </code>,可以改用<code> Thread.sleep()</code>。
     * 
     * 
     * @param   ms      time to sleep in milliseconds
     * @throws  IllegalArgumentException if <code>ms</code> is not between 0 and 60,000 milliseconds inclusive
     * @see     java.lang.Thread#sleep
     */
    public synchronized void delay(int ms) {
        checkDelayArgument(ms);
        try {
            Thread.sleep(ms);
        } catch(InterruptedException ite) {
            ite.printStackTrace();
        }
    }

    private void checkDelayArgument(int ms) {
        if (ms < 0 || ms > MAX_DELAY) {
            throw new IllegalArgumentException("Delay must be to 0 to 60,000ms");
        }
    }

    /**
     * Waits until all events currently on the event queue have been processed.
     * <p>
     *  等待直到事件队列上的所有事件都已处理完毕。
     * 
     * 
     * @throws  IllegalThreadStateException if called on the AWT event dispatching thread
     */
    public synchronized void waitForIdle() {
        checkNotDispatchThread();
        // post a dummy event to the queue so we know when
        // all the events before it have been processed
        try {
            SunToolkit.flushPendingEvents();
            EventQueue.invokeAndWait( new Runnable() {
                                            public void run() {
                                                // dummy implementation
                                            }
                                        } );
        } catch(InterruptedException ite) {
            System.err.println("Robot.waitForIdle, non-fatal exception caught:");
            ite.printStackTrace();
        } catch(InvocationTargetException ine) {
            System.err.println("Robot.waitForIdle, non-fatal exception caught:");
            ine.printStackTrace();
        }
    }

    private void checkNotDispatchThread() {
        if (EventQueue.isDispatchThread()) {
            throw new IllegalThreadStateException("Cannot call method from the event dispatcher thread");
        }
    }

    /**
     * Returns a string representation of this Robot.
     *
     * <p>
     *  返回此Robot的字符串表示形式。
     * 
     * @return  the string representation.
     */
    public synchronized String toString() {
        String params = "autoDelay = "+getAutoDelay()+", "+"autoWaitForIdle = "+isAutoWaitForIdle();
        return getClass().getName() + "[ " + params + " ]";
    }
}
