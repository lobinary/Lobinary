/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.im.spi;

import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.font.TextHitInfo;
import java.awt.im.InputMethodRequests;
import java.text.AttributedCharacterIterator;
import javax.swing.JFrame;

/**
 * Provides methods that input methods
 * can use to communicate with their client components or to request
 * other services.  This interface is implemented by the input method
 * framework, and input methods call its methods on the instance they
 * receive through
 * {@link java.awt.im.spi.InputMethod#setInputMethodContext}.
 * There should be no other implementors or callers.
 *
 * <p>
 *  提供输入法可用于与其客户端组件通信或请求其他服务的方法。
 * 这个接口由输入法框架实现,input方法通过{@link java.awt.im.spi.InputMethod#setInputMethodContext}调用它接收的实例上的方法。
 * 应该没有其他实现者或调用者。
 * 
 * 
 * @since 1.3
 *
 * @author JavaSoft International
 */

public interface InputMethodContext extends InputMethodRequests {

    /**
     * Creates an input method event from the arguments given
     * and dispatches it to the client component. For arguments,
     * see {@link java.awt.event.InputMethodEvent#InputMethodEvent}.
     * <p>
     *  从提供的参数创建输入方法事件,并将其分派到客户端组件。有关参数,请参阅{@link java.awt.event.InputMethodEvent#InputMethodEvent}。
     * 
     */
    public void dispatchInputMethodEvent(int id,
                AttributedCharacterIterator text, int committedCharacterCount,
                TextHitInfo caret, TextHitInfo visiblePosition);

    /**
     * Creates a top-level window for use by the input method.
     * The intended behavior of this window is:
     * <ul>
     * <li>it floats above all document windows and dialogs
     * <li>it and all components that it contains do not receive the focus
     * <li>it has lightweight decorations, such as a reduced drag region without title
     * </ul>
     * However, the actual behavior with respect to these three items is platform dependent.
     * <p>
     * The title may or may not be displayed, depending on the actual type of window created.
     * <p>
     * If attachToInputContext is true, the new window will share the input context that
     * corresponds to this input method context, so that events for components in the window
     * are automatically dispatched to the input method.
     * Also, when the window is opened using setVisible(true), the input context will prevent
     * deactivate and activate calls to the input method that might otherwise be caused.
     * <p>
     * Input methods must call {@link java.awt.Window#dispose() Window.dispose} on the
     * returned input method window when it is no longer needed.
     * <p>
     * <p>
     *  创建用于输入法的顶级窗口。此窗口的预期行为是：
     * <ul>
     *  <li>它浮动在所有文档窗口和对话框之上<li>它包含的所有组件都不会收到焦点<li>它具有轻量级的装饰,例如缩小的拖动区域没有标题
     * </ul>
     *  然而,关于这三个项目的实际行为是平台相关的。
     * <p>
     *  根据创建的窗口的实际类型,标题可以显示也可以不显示。
     * <p>
     * 如果attachToInputContext为true,则新窗口将共享与此输入方法上下文对应的输入上下文,以便将窗口中组件的事件自动分派到输入法。
     * 此外,当使用setVisible(true)打开窗口时,输入上下文将阻止停用并激活对输入法的调用,否则可能会导致该调用。
     * <p>
     *  输入方法必须在不再需要时在返回的输入法窗口上调用{@link java.awt.Window#dispose()Window.dispose}。
     * <p>
     * 
     * @param title the title to be displayed in the window's title bar,
     *              if there is such a title bar.
     *              A <code>null</code> value is treated as an empty string, "".
     * @param attachToInputContext whether this window should share the input context
     *              that corresponds to this input method context
     * @return a window with special characteristics for use by input methods
     * @exception HeadlessException if <code>GraphicsEnvironment.isHeadless
     *              </code> returns <code>true</code>
     */
    public Window createInputMethodWindow(String title, boolean attachToInputContext);

    /**
     * Creates a top-level Swing JFrame for use by the input method.
     * The intended behavior of this window is:
     * <ul>
     * <li>it floats above all document windows and dialogs
     * <li>it and all components that it contains do not receive the focus
     * <li>it has lightweight decorations, such as a reduced drag region without title
     * </ul>
     * However, the actual behavior with respect to these three items is platform dependent.
     * <p>
     * The title may or may not be displayed, depending on the actual type of window created.
     * <p>
     * If attachToInputContext is true, the new window will share the input context that
     * corresponds to this input method context, so that events for components in the window
     * are automatically dispatched to the input method.
     * Also, when the window is opened using setVisible(true), the input context will prevent
     * deactivate and activate calls to the input method that might otherwise be caused.
     * <p>
     * Input methods must call {@link java.awt.Window#dispose() Window.dispose} on the
     * returned input method window when it is no longer needed.
     * <p>
     * <p>
     *  创建由输入法使用的顶级Swing JFrame。此窗口的预期行为是：
     * <ul>
     *  <li>它浮动在所有文档窗口和对话框之上<li>它包含的所有组件都不会收到焦点<li>它具有轻量级的装饰,例如缩小的拖动区域没有标题
     * </ul>
     *  然而,关于这三个项目的实际行为是平台相关的。
     * <p>
     *  根据创建的窗口的实际类型,标题可以显示也可以不显示。
     * <p>
     *  如果attachToInputContext为true,则新窗口将共享与此输入方法上下文对应的输入上下文,以便将窗口中组件的事件自动分派到输入法。
     * 此外,当使用setVisible(true)打开窗口时,输入上下文将阻止停用并激活对输入法的调用,否则可能会导致该调用。
     * 
     * @param title the title to be displayed in the window's title bar,
     *              if there is such a title bar.
     *              A <code>null</code> value is treated as an empty string, "".
     * @param attachToInputContext whether this window should share the input context
     *              that corresponds to this input method context
     * @return a JFrame with special characteristics for use by input methods
     * @exception HeadlessException if <code>GraphicsEnvironment.isHeadless
     *              </code> returns <code>true</code>
     *
     * @since 1.4
     */
    public JFrame createInputMethodJFrame(String title, boolean attachToInputContext);

    /**
     * Enables or disables notification of the current client window's
     * location and state for the specified input method. When
     * notification is enabled, the input method's {@link
     * java.awt.im.spi.InputMethod#notifyClientWindowChange
     * notifyClientWindowChange} method is called as described in that
     * method's specification. Notification is automatically disabled
     * when the input method is disposed.
     *
     * <p>
     * <p>
     * 输入方法必须在不再需要时在返回的输入法窗口上调用{@link java.awt.Window#dispose()Window.dispose}。
     * <p>
     * 
     * @param inputMethod the input method for which notifications are
     * enabled or disabled
     * @param enable true to enable, false to disable
     */
    public void enableClientWindowNotification(InputMethod inputMethod, boolean enable);
}
