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

package javax.swing;


/**
 * Constants used to control the window-closing operation.
 * The <code>setDefaultCloseOperation</code> and
 * <code>getDefaultCloseOperation</code> methods
 * provided by <code>JFrame</code>,
 * <code>JInternalFrame</code>, and
 * <code>JDialog</code>
 * use these constants.
 * For examples of setting the default window-closing operation, see
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html#windowevents">Responding to Window-Closing Events</a>,
 * a section in <em>The Java Tutorial</em>.
 * <p>
 *  常数用于控制窗口关闭操作。
 *  <code> JFrame </code>,<code> JInternalFrame </code>和<code> JDialog </code>提供的<code> setDefaultCloseO
 * peration </code>和<code> getDefaultCloseOperation </code>常数。
 *  常数用于控制窗口关闭操作。
 * 有关设置默认关闭窗口操作的示例,请参见<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html#windowevents">
 * 响应窗口关闭事件</b> a>,Java教程</em>中的一个部分。
 *  常数用于控制窗口关闭操作。
 * 
 * 
 * @see JFrame#setDefaultCloseOperation(int)
 * @see JDialog#setDefaultCloseOperation(int)
 * @see JInternalFrame#setDefaultCloseOperation(int)
 *
 *
 * @author Amy Fowler
 */
public interface WindowConstants
{
    /**
     * The do-nothing default window close operation.
     * <p>
     *  do-nothing默认窗口关闭操作。
     * 
     */
    public static final int DO_NOTHING_ON_CLOSE = 0;

    /**
     * The hide-window default window close operation
     * <p>
     *  hide-window默认窗口关闭操作
     * 
     */
    public static final int HIDE_ON_CLOSE = 1;

    /**
     * The dispose-window default window close operation.
     * <p>
     * <b>Note</b>: When the last displayable window
     * within the Java virtual machine (VM) is disposed of, the VM may
     * terminate.  See <a href="../../java/awt/doc-files/AWTThreadIssues.html">
     * AWT Threading Issues</a> for more information.
     * <p>
     *  dispose-window默认窗口关闭操作。
     * <p>
     *  <b>注意</b>：当处理Java虚拟机(VM)中的最后一个可显示窗口时,VM可能终止。
     * 有关详细信息,请参见<a href="../../java/awt/doc-files/AWTThreadIssues.html"> AWT线程问题</a>。
     * 
     * 
     * @see java.awt.Window#dispose()
     * @see JInternalFrame#dispose()
     */
    public static final int DISPOSE_ON_CLOSE = 2;

    /**
     * The exit application default window close operation. Attempting
     * to set this on Windows that support this, such as
     * <code>JFrame</code>, may throw a <code>SecurityException</code> based
     * on the <code>SecurityManager</code>.
     * It is recommended you only use this in an application.
     *
     * <p>
     *  退出应用程序默认窗口关闭操作。
     * 尝试在支持此功能的Windows上设置此属性,例如<code> JFrame </code>,可能会基于<code> SecurityManager </code>引发<code> SecurityEx
     * ception </code>。
     * 
     * @since 1.4
     * @see JFrame#setDefaultCloseOperation
     */
    public static final int EXIT_ON_CLOSE = 3;

}
