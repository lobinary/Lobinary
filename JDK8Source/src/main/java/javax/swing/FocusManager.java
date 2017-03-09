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

import java.awt.*;


/**
 * This class has been obsoleted by the 1.4 focus APIs. While client code may
 * still use this class, developers are strongly encouraged to use
 * <code>java.awt.KeyboardFocusManager</code> and
 * <code>java.awt.DefaultKeyboardFocusManager</code> instead.
 * <p>
 * Please see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
 * How to Use the Focus Subsystem</a>,
 * a section in <em>The Java Tutorial</em>, and the
 * <a href="../../java/awt/doc-files/FocusSpec.html">Focus Specification</a>
 * for more information.
 *
 * <p>
 *  这个类已经被1.4焦点API废弃了。
 * 虽然客户端代码仍然可以使用此类,但强烈建议开发人员使用<code> java.awt.KeyboardFocusManager </code>和<code> java.awt.DefaultKeyboa
 * rdFocusManager </code>。
 *  这个类已经被1.4焦点API废弃了。
 * <p>
 *  请参见
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html">
 *  如何使用焦点子系统</a>,<em> Java教程</em>和<a href ="../../ java / awt / doc-files / FocusSpec.html" >聚焦规格</a>了解
 * 更多信息。
 * 
 * 
 * @see <a href="../../java/awt/doc-files/FocusSpec.html">Focus Specification</a>
 *
 * @author Arnaud Weber
 * @author David Mendenhall
 */
public abstract class FocusManager extends DefaultKeyboardFocusManager {

    /**
     * This field is obsolete, and its use is discouraged since its
     * specification is incompatible with the 1.4 focus APIs.
     * The current FocusManager is no longer a property of the UI.
     * Client code must query for the current FocusManager using
     * <code>KeyboardFocusManager.getCurrentKeyboardFocusManager()</code>.
     * See the Focus Specification for more information.
     *
     * <p>
     *  此字段已过时,并且不建议使用它,因为它的规范与1.4焦点API不兼容。当前FocusManager不再是UI的属性。
     * 客户端代码必须使用<code> KeyboardFocusManager.getCurrentKeyboardFocusManager()</code>查询当前的FocusManager。
     * 有关详细信息,请参阅聚焦规范。
     * 
     * 
     * @see java.awt.KeyboardFocusManager#getCurrentKeyboardFocusManager
     * @see <a href="../../java/awt/doc-files/FocusSpec.html">Focus Specification</a>
     */
    public static final String FOCUS_MANAGER_CLASS_PROPERTY =
        "FocusManagerClassName";

    private static boolean enabled = true;

    /**
     * Returns the current <code>KeyboardFocusManager</code> instance
     * for the calling thread's context.
     *
     * <p>
     *  返回当前<code> KeyboardFocusManager </code>实例的调用线程的上下文。
     * 
     * 
     * @return this thread's context's <code>KeyboardFocusManager</code>
     * @see #setCurrentManager
     */
    public static FocusManager getCurrentManager() {
        KeyboardFocusManager manager =
            KeyboardFocusManager.getCurrentKeyboardFocusManager();
        if (manager instanceof FocusManager) {
            return (FocusManager)manager;
        } else {
            return new DelegatingDefaultFocusManager(manager);
        }
    }

    /**
     * Sets the current <code>KeyboardFocusManager</code> instance
     * for the calling thread's context. If <code>null</code> is
     * specified, then the current <code>KeyboardFocusManager</code>
     * is replaced with a new instance of
     * <code>DefaultKeyboardFocusManager</code>.
     * <p>
     * If a <code>SecurityManager</code> is installed,
     * the calling thread must be granted the <code>AWTPermission</code>
     * "replaceKeyboardFocusManager" in order to replace the
     * the current <code>KeyboardFocusManager</code>.
     * If this permission is not granted,
     * this method will throw a <code>SecurityException</code>,
     * and the current <code>KeyboardFocusManager</code> will be unchanged.
     *
     * <p>
     *  为调用线程的上下文设置当前<code> KeyboardFocusManager </code>实例。
     * 如果指定<code> null </code>,则当前<code> KeyboardFocusManager </code>被替换为<code> DefaultKeyboardFocusManager 
     * </code>的新实例。
     *  为调用线程的上下文设置当前<code> KeyboardFocusManager </code>实例。
     * <p>
     * 如果安装了<code> SecurityManager </code>,调用线程必须授予<code> AWTPermission </code>"replaceKeyboardFocusManager"
     * ,以替换当前的<code> KeyboardFocusManager </code>。
     * 如果未授予此权限,此方法将抛出一个<code> SecurityException </code>,并且当前<code> KeyboardFocusManager </code>将保持不变。
     * 
     * @param aFocusManager the new <code>KeyboardFocusManager</code>
     *     for this thread's context
     * @see #getCurrentManager
     * @see java.awt.DefaultKeyboardFocusManager
     * @throws SecurityException if the calling thread does not have permission
     *         to replace the current <code>KeyboardFocusManager</code>
     */
    public static void setCurrentManager(FocusManager aFocusManager)
        throws SecurityException
    {
        // Note: This method is not backward-compatible with 1.3 and earlier
        // releases. It now throws a SecurityException in an applet, whereas
        // in previous releases, it did not. This issue was discussed at
        // length, and ultimately approved by Hans.
        KeyboardFocusManager toSet =
            (aFocusManager instanceof DelegatingDefaultFocusManager)
                ? ((DelegatingDefaultFocusManager)aFocusManager).getDelegate()
                : aFocusManager;
        KeyboardFocusManager.setCurrentKeyboardFocusManager(toSet);
    }

    /**
     * Changes the current <code>KeyboardFocusManager</code>'s default
     * <code>FocusTraversalPolicy</code> to
     * <code>DefaultFocusTraversalPolicy</code>.
     *
     * <p>
     * 
     * 
     * @see java.awt.DefaultFocusTraversalPolicy
     * @see java.awt.KeyboardFocusManager#setDefaultFocusTraversalPolicy
     * @deprecated as of 1.4, replaced by
     * <code>KeyboardFocusManager.setDefaultFocusTraversalPolicy(FocusTraversalPolicy)</code>
     */
    @Deprecated
    public static void disableSwingFocusManager() {
        if (enabled) {
            enabled = false;
            KeyboardFocusManager.getCurrentKeyboardFocusManager().
                setDefaultFocusTraversalPolicy(
                    new DefaultFocusTraversalPolicy());
        }
    }

    /**
     * Returns whether the application has invoked
     * <code>disableSwingFocusManager()</code>.
     *
     * <p>
     *  将当前<code> KeyboardFocusManager </code>的默认<code> FocusTraversalPolicy </code>更改为<code> DefaultFocusTr
     * aversalPolicy </code>。
     * 
     * 
     * @see #disableSwingFocusManager
     * @deprecated As of 1.4, replaced by
     *   <code>KeyboardFocusManager.getDefaultFocusTraversalPolicy()</code>
     */
    @Deprecated
    public static boolean isFocusManagerEnabled() {
        return enabled;
    }
}
