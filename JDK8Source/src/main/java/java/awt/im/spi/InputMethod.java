/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Locale;
import java.awt.AWTEvent;
import java.awt.Rectangle;
import java.lang.Character.Subset;


/**
 * Defines the interface for an input method that supports complex text input.
 * Input methods traditionally support text input for languages that have
 * more characters than can be represented on a standard-size keyboard,
 * such as Chinese, Japanese, and Korean. However, they may also be used to
 * support phonetic text input for English or character reordering for Thai.
 * <p>
 * Subclasses of InputMethod can be loaded by the input method framework; they
 * can then be selected either through the API
 * ({@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod})
 * or the user interface (the input method selection menu).
 *
 * <p>
 *  定义支持复杂文本输入的输入法的接口。输入方法传统上支持具有比可以在标准尺寸键盘上表示的更多字符的语言的文本输入,例如中文,日语和韩语。
 * 然而,它们也可以用于支持用于英语的语音文本输入或用于泰语的字符重排序。
 * <p>
 *  InputMethod的子类可以通过输入法框架加载;它们可以通过API({@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod}
 * )或用户界面(输入法选择菜单)进行选择。
 * 
 * 
 * @since 1.3
 *
 * @author JavaSoft International
 */

public interface InputMethod {

    /**
     * Sets the input method context, which is used to dispatch input method
     * events to the client component and to request information from
     * the client component.
     * <p>
     * This method is called once immediately after instantiating this input
     * method.
     *
     * <p>
     *  设置输入方法上下文,用于将输入方法事件分派到客户端组件,以及从客户端组件请求信息。
     * <p>
     *  在实例化此输入法后立即调用此方法。
     * 
     * 
     * @param context the input method context for this input method
     * @exception NullPointerException if <code>context</code> is null
     */
    public void setInputMethodContext(InputMethodContext context);

    /**
     * Attempts to set the input locale. If the input method supports the
     * desired locale, it changes its behavior to support input for the locale
     * and returns true.
     * Otherwise, it returns false and does not change its behavior.
     * <p>
     * This method is called
     * <ul>
     * <li>by {@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod},
     * <li>when switching to this input method through the user interface if the user
     *     specified a locale or if the previously selected input method's
     *     {@link java.awt.im.spi.InputMethod#getLocale getLocale} method
     *     returns a non-null value.
     * </ul>
     *
     * <p>
     *  尝试设置输入区域设置。如果输入法支持所需的语言环境,它将更改其行为以支持语言环境的输入并返回true。否则,它返回false并且不改变其行为。
     * <p>
     *  这个方法被调用
     * <ul>
     * <li>通过{@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod},<li>通过用户界面切换到
     * 此输入法时,如果用户指定了语言环境,或者如果先前选择的输入法{@link java.awt.im.spi.InputMethod#getLocale getLocale}方法返回非空值。
     * </ul>
     * 
     * 
     * @param locale locale to input
     * @return whether the specified locale is supported
     * @exception NullPointerException if <code>locale</code> is null
     */
    public boolean setLocale(Locale locale);

    /**
     * Returns the current input locale. Might return null in exceptional cases.
     * <p>
     * This method is called
     * <ul>
     * <li>by {@link java.awt.im.InputContext#getLocale InputContext.getLocale} and
     * <li>when switching from this input method to a different one through the
     *     user interface.
     * </ul>
     *
     * <p>
     *  返回当前输入语言环境。在特殊情况下可能返回null。
     * <p>
     *  这个方法被调用
     * <ul>
     *  <li>通过{@link java.awt.im.InputContext#getLocale InputContext.getLocale}和<li>通过用户界面从此输入法切换到另一个输入法时。
     * </ul>
     * 
     * 
     * @return the current input locale, or null
     */
    public Locale getLocale();

    /**
     * Sets the subsets of the Unicode character set that this input method
     * is allowed to input. Null may be passed in to indicate that all
     * characters are allowed.
     * <p>
     * This method is called
     * <ul>
     * <li>immediately after instantiating this input method,
     * <li>when switching to this input method from a different one, and
     * <li>by {@link java.awt.im.InputContext#setCharacterSubsets InputContext.setCharacterSubsets}.
     * </ul>
     *
     * <p>
     *  设置此输入法允许输入的Unicode字符集的子集。可以传入Null以指示允许所有字符。
     * <p>
     *  这个方法被调用
     * <ul>
     *  <li>在实例化此输入法之后,<li>在从不同的输入法切换到此输入法时,<li>通过{@link java.awt.im.InputContext#setCharacterSubsets InputContext.setCharacterSubsets}
     * 。
     * </ul>
     * 
     * 
     * @param subsets the subsets of the Unicode character set from which
     * characters may be input
     */
    public void setCharacterSubsets(Subset[] subsets);

    /**
     * Enables or disables this input method for composition,
     * depending on the value of the parameter <code>enable</code>.
     * <p>
     * An input method that is enabled for composition interprets incoming
     * events for both composition and control purposes, while a
     * disabled input method does not interpret events for composition.
     * Note however that events are passed on to the input method regardless
     * whether it is enabled or not, and that an input method that is disabled
     * for composition may still interpret events for control purposes,
     * including to enable or disable itself for composition.
     * <p>
     * For input methods provided by host operating systems, it is not always possible to
     * determine whether this operation is supported. For example, an input method may enable
     * composition only for some locales, and do nothing for other locales. For such input
     * methods, it is possible that this method does not throw
     * {@link java.lang.UnsupportedOperationException UnsupportedOperationException},
     * but also does not affect whether composition is enabled.
     * <p>
     * This method is called
     * <ul>
     * <li>by {@link java.awt.im.InputContext#setCompositionEnabled InputContext.setCompositionEnabled},
     * <li>when switching to this input method from a different one using the
     *     user interface or
     *     {@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod},
     *     if the previously selected input method's
     *     {@link java.awt.im.spi.InputMethod#isCompositionEnabled isCompositionEnabled}
     *     method returns without throwing an exception.
     * </ul>
     *
     * <p>
     *  根据参数<code> enable </code>的值启用或禁用此输入法进行合成。
     * <p>
     * 为组合启用的输入方法对组合和控制目的的传入事件进行解释,而禁用的输入方法不解释组合的事件。
     * 请注意,无论事件是否启用,事件都会传递给输入法,并且为组合禁用的输入法仍然可以为了控制目的而解释事件,包括为组合启用或禁用自身。
     * <p>
     *  对于主机操作系统提供的输入方法,并不总是可以确定是否支持此操作。例如,输入法可以仅针对一些区域设置启用合成,并且对其他区域设置不进行任何操作。
     * 对于这样的输入方法,这种方法可能不会抛出{@link java.lang.UnsupportedOperationException UnsupportedOperationException},但也不
     * 影响是否启用合成。
     *  对于主机操作系统提供的输入方法,并不总是可以确定是否支持此操作。例如,输入法可以仅针对一些区域设置启用合成,并且对其他区域设置不进行任何操作。
     * <p>
     *  这个方法被调用
     * <ul>
     *  <li>通过{@link java.awt.im.InputContext#setCompositionEnabled InputContext.setCompositionEnabled},<li>
     * 切换到使用用户界面或{@link java.awt.im.InputContext# selectInputMethod InputContext.selectInputMethod},如果先前选择的输
     * 入法的{@link java.awt.im.spi.InputMethod#isCompositionEnabled isCompositionEnabled}方法返回而不抛出异常。
     * </ul>
     * 
     * 
     * @param enable whether to enable the input method for composition
     * @throws UnsupportedOperationException if this input method does not
     * support the enabling/disabling operation
     * @see #isCompositionEnabled
     */
    public void setCompositionEnabled(boolean enable);

    /**
     * Determines whether this input method is enabled.
     * An input method that is enabled for composition interprets incoming
     * events for both composition and control purposes, while a
     * disabled input method does not interpret events for composition.
     * <p>
     * This method is called
     * <ul>
     * <li>by {@link java.awt.im.InputContext#isCompositionEnabled InputContext.isCompositionEnabled} and
     * <li>when switching from this input method to a different one using the
     *     user interface or
     *     {@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod}.
     * </ul>
     *
     * <p>
     * 确定是否启用此输入法。为组合启用的输入方法对组合和控制目的的传入事件进行解释,而禁用的输入方法不解释组合的事件。
     * <p>
     *  这个方法被调用
     * <ul>
     *  <li>通过{@link java.awt.im.InputContext#isCompositionEnabled InputContext.isCompositionEnabled}和<li>从此
     * 输入法切换到另一个使用用户界面或{@link java.awt.im.InputContext# selectInputMethod InputContext.selectInputMethod}。
     * </ul>
     * 
     * 
     * @return <code>true</code> if this input method is enabled for
     * composition; <code>false</code> otherwise.
     * @throws UnsupportedOperationException if this input method does not
     * support checking whether it is enabled for composition
     * @see #setCompositionEnabled
     */
    public boolean isCompositionEnabled();

    /**
     * Starts the reconversion operation. The input method obtains the
     * text to be reconverted from the current client component using the
     * {@link java.awt.im.InputMethodRequests#getSelectedText InputMethodRequests.getSelectedText}
     * method. It can use other <code>InputMethodRequests</code>
     * methods to request additional information required for the
     * reconversion operation. The composed and committed text
     * produced by the operation is sent to the client component as a
     * sequence of <code>InputMethodEvent</code>s. If the given text
     * cannot be reconverted, the same text should be sent to the
     * client component as committed text.
     * <p>
     * This method is called by
     * {@link java.awt.im.InputContext#reconvert() InputContext.reconvert}.
     *
     * <p>
     *  开始重新转换操作。
     * 输入法使用{@link java.awt.im.InputMethodRequests#getSelectedText InputMethodRequests.getSelectedText}方法从当前
     * 客户端组件获取要重新转换的文本。
     *  开始重新转换操作。它可以使用其他<code> InputMethodRequests </code>方法来请求重新转换操作所需的附加信息。
     * 由操作产生的组合和提交的文本作为<code> InputMethodEvent </code>的序列发送到客户端组件。如果无法重新转换给定文本,则应将相同的文本作为提交文本发送到客户端组件。
     * <p>
     *  此方法由{@link java.awt.im.InputContext#reconvert()InputContext.reconvert}调用。
     * 
     * 
     * @throws UnsupportedOperationException if the input method does not
     * support the reconversion operation.
     */
    public void reconvert();

    /**
     * Dispatches the event to the input method. If input method support is
     * enabled for the focussed component, incoming events of certain types
     * are dispatched to the current input method for this component before
     * they are dispatched to the component's methods or event listeners.
     * The input method decides whether it needs to handle the event. If it
     * does, it also calls the event's <code>consume</code> method; this
     * causes the event to not get dispatched to the component's event
     * processing methods or event listeners.
     * <p>
     * Events are dispatched if they are instances of InputEvent or its
     * subclasses.
     * This includes instances of the AWT classes KeyEvent and MouseEvent.
     * <p>
     * This method is called by {@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent}.
     *
     * <p>
     * 将事件分派到输入法。如果为聚焦组件启用了输入法支持,则在将某些类型的传入事件分派到组件的方法或事件侦听器之前,会将这些事件分派到此组件的当前输入方法。输入法决定是否需要处理事件。
     * 如果是,它也调用事件的<code> consume </code>方法;这会导致事件不会被分派到组件的事件处理方法或事件侦听器。
     * <p>
     *  如果事件是InputEvent或其子类的实例,则调度它们。这包括AWT类KeyEvent和MouseEvent的实例。
     * <p>
     *  此方法由{@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent}调用。
     * 
     * 
     * @param event the event being dispatched to the input method
     * @exception NullPointerException if <code>event</code> is null
     */
    public void dispatchEvent(AWTEvent event);

    /**
     * Notifies this input method of changes in the client window
     * location or state. This method is called while this input
     * method is the current input method of its input context and
     * notifications for it are enabled (see {@link
     * InputMethodContext#enableClientWindowNotification
     * InputMethodContext.enableClientWindowNotification}). Calls
     * to this method are temporarily suspended if the input context's
     * {@link java.awt.im.InputContext#removeNotify removeNotify}
     * method is called, and resume when the input method is activated
     * for a new client component. It is called in the following
     * situations:
     * <ul>
     * <li>
     * when the window containing the current client component changes
     * in location, size, visibility, iconification state, or when the
     * window is closed.</li>
     * <li>
     * from <code> enableClientWindowNotification(inputMethod,
     * true)</code> if the current client component exists,</li>
     * <li>
     * when activating the input method for the first time after it
     * called
     * <code>enableClientWindowNotification(inputMethod,
     * true)</code> if during the call no current client component was
     * available,</li>
     * <li>
     * when activating the input method for a new client component
     * after the input context's removeNotify method has been
     * called.</li>
     * </ul>
     * <p>
     *  通知此输入法客户机窗口位置或状态的更改。
     * 此方法被调用,而此输入​​法是其输入上下文的当前输入法,并且它的通知被启用(参见{@link InputMethodContext#enableClientWindowNotification InputMethodContext.enableClientWindowNotification}
     * )。
     *  通知此输入法客户机窗口位置或状态的更改。
     * 如果调用了输入上下文的{@link java.awt.im.InputContext#removeNotify removeNotify}方法,则对此方法的调用将暂时挂起,并在为新客户端组件激活输入方法
     * 时继续。
     *  通知此输入法客户机窗口位置或状态的更改。在以下情况下调用它：。
     * <ul>
     * <li>
     *  当包含当前客户端组件的窗口在位置,大小,可见性,图标状态或窗口关闭时发生更改。</li>
     * <li>
     * 从<code> enableClientWindowNotification(inputMethod,true)</code>如果当前客户端组件存在,</li>
     * <li>
     *  在调用<code> enableClientWindowNotification(inputMethod,true)</code>之后第一次激活输入法时如果在调用期间没有当前客户端组件可用,</li>
     * 。
     * <li>
     *  在输入上下文的removeNotify方法被调用后激活新客户端组件的输入法。</li>
     * </ul>
     * 
     * @param bounds client window's {@link
     * java.awt.Component#getBounds bounds} on the screen; or null if
     * the client window is iconified or invisible
     */
    public void notifyClientWindowChange(Rectangle bounds);

    /**
     * Activates the input method for immediate input processing.
     * <p>
     * If an input method provides its own windows, it should make sure
     * at this point that all necessary windows are open and visible.
     * <p>
     * This method is called
     * <ul>
     * <li>by {@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent}
     *     when a client component receives a FOCUS_GAINED event,
     * <li>when switching to this input method from a different one using the
     *     user interface or
     *     {@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod}.
     * </ul>
     * The method is only called when the input method is inactive.
     * A newly instantiated input method is assumed to be inactive.
     * <p>
     *  激活立即输入处理的输入法。
     * <p>
     *  如果一个输入法提供了自己的窗口,它应该确保这一点所有必要的窗口是打开和可见的。
     * <p>
     *  这个方法被调用
     * <ul>
     *  当客户端组件接收到FOCUS_GAINED事件时,通过{@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent}
     * ,当使用用户界面或{@link)切换到不同的输入方法时,<li> java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod}
     * 。
     * </ul>
     *  仅当输入法无效时才调用该方法。新近实例化的输入法被假定为无效。
     * 
     */
    public void activate();

    /**
     * Deactivates the input method.
     * The isTemporary argument has the same meaning as in
     * {@link java.awt.event.FocusEvent#isTemporary FocusEvent.isTemporary}.
     * <p>
     * If an input method provides its own windows, only windows that relate
     * to the current composition (such as a lookup choice window) should be
     * closed at this point.
     * It is possible that the input method will be immediately activated again
     * for a different client component, and closing and reopening more
     * persistent windows (such as a control panel) would create unnecessary
     * screen flicker.
     * Before an instance of a different input method class is activated,
     * {@link #hideWindows} is called on the current input method.
     * <p>
     * This method is called
     * <ul>
     * <li>by {@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent}
     *     when a client component receives a FOCUS_LOST event,
     * <li>when switching from this input method to a different one using the
     *     user interface or
     *     {@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod},
     * <li>before {@link #removeNotify removeNotify} if the current client component is
     *     removed.
     * </ul>
     * The method is only called when the input method is active.
     *
     * <p>
     *  停用输入法。 isTemporary参数与{@link java.awt.event.FocusEvent#isTemporary FocusEvent.isTemporary}中的含义相同。
     * <p>
     * 如果输入法提供了自己的窗口,那么只有与当前组合相关的窗口(例如查找选项窗口)才应该关闭。
     * 可能的是,对于不同的客户端组件,输入方法将被立即再次激活,并且关闭和重新打开更多持久性窗口(例如控制面板)将产生不必要的屏幕闪烁。
     * 在激活不同输入法类的实例之前,对当前输入法调用{@link #hideWindows}。
     * <p>
     *  这个方法被调用
     * <ul>
     *  当客户端组件接收到FOCUS_LOST事件时,通过{@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent},当
     * 从此输入法切换到使用用户界面或{@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod},<li>
     * 在{@link #removeNotify removeNotify}之前删除当前客户端组件。
     * </ul>
     *  该方法仅在输入法激活时调用。
     * 
     * 
     * @param isTemporary whether the focus change is temporary
     */
    public void deactivate(boolean isTemporary);

    /**
     * Closes or hides all windows opened by this input method instance or
     * its class.
     * <p>
     * This method is called
     * <ul>
     * <li>before calling {@link #activate activate} on an instance of a different input
     *     method class,
     * <li>before calling {@link #dispose dispose} on this input method.
     * </ul>
     * The method is only called when the input method is inactive.
     * <p>
     *  关闭或隐藏由此输入法实例或其类打开的所有窗口。
     * <p>
     *  这个方法被调用
     * <ul>
     *  <li>在对此输入法调用{@link #dispose dispose}之前,在不同输入法类的实例<li>上调用{@link #activate activate}之前。
     * </ul>
     *  仅当输入法无效时才调用该方法。
     * 
     */
    public void hideWindows();

    /**
     * Notifies the input method that a client component has been
     * removed from its containment hierarchy, or that input method
     * support has been disabled for the component.
     * <p>
     * This method is called by {@link java.awt.im.InputContext#removeNotify InputContext.removeNotify}.
     * <p>
     * The method is only called when the input method is inactive.
     * <p>
     *  通知输入法,客户端组件已从其包含层次结构中删除,或者该组件的输入法支持已禁用。
     * <p>
     * 此方法由{@link java.awt.im.InputContext#removeNotify InputContext.removeNotify}调用。
     * <p>
     *  仅当输入法无效时才调用该方法。
     * 
     */
    public void removeNotify();

    /**
     * Ends any input composition that may currently be going on in this
     * context. Depending on the platform and possibly user preferences,
     * this may commit or delete uncommitted text. Any changes to the text
     * are communicated to the active component using an input method event.
     *
     * <p>
     * A text editing component may call this in a variety of situations,
     * for example, when the user moves the insertion point within the text
     * (but outside the composed text), or when the component's text is
     * saved to a file or copied to the clipboard.
     * <p>
     * This method is called
     * <ul>
     * <li>by {@link java.awt.im.InputContext#endComposition InputContext.endComposition},
     * <li>by {@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent}
     *     when switching to a different client component
     * <li>when switching from this input method to a different one using the
     *     user interface or
     *     {@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod}.
     * </ul>
     * <p>
     *  结束在此上下文中当前可能正在进行的任何输入组成。根据平台和可能的用户偏好,这可以提交或删除未提交的文本。对文本的任何更改都使用输入方法事件传达给活动组件。
     * 
     * <p>
     *  文本编辑组件可以在各种情况下调用它,例如,当用户在文本内移动插入点(但是在组合文本之外)时,或者当组件的文本被保存到文件或被复制到剪贴板时。
     * <p>
     *  这个方法被调用
     * <ul>
     *  <li>在切换到其他客户端组件时,由{@link java.awt.im.InputContext#endComposition InputContext.endComposition},<li>由{@link java.awt.im.InputContext#dispatchEvent InputContext.dispatchEvent}
     *  >当使用用户界面或{@link java.awt.im.InputContext#selectInputMethod InputContext.selectInputMethod}从此输入法切换到另一
     * 个输入法时。
     * </ul>
     */
    public void endComposition();

    /**
     * Releases the resources used by this input method.
     * In particular, the input method should dispose windows and close files that are no
     * longer needed.
     * <p>
     * This method is called by {@link java.awt.im.InputContext#dispose InputContext.dispose}.
     * <p>
     * The method is only called when the input method is inactive.
     * No method of this interface is called on this instance after dispose.
     * <p>
     *  释放此输入法使用的资源。特别地,输入法应该处理窗口和关闭不再需要的文件。
     * <p>
     *  此方法由{@link java.awt.im.InputContext#dispose InputContext.dispose}调用。
     * <p>
     *  仅当输入法无效时才调用该方法。在处理之后,在此实例上不调用此接口的任何方法。
     * 
     */
    public void dispose();

    /**
     * Returns a control object from this input method, or null. A
     * control object provides methods that control the behavior of the
     * input method or obtain information from the input method. The type
     * of the object is an input method specific class. Clients have to
     * compare the result against known input method control object
     * classes and cast to the appropriate class to invoke the methods
     * provided.
     * <p>
     * This method is called by
     * {@link java.awt.im.InputContext#getInputMethodControlObject InputContext.getInputMethodControlObject}.
     *
     * <p>
     * 从此输入法返回控件对象,或null。控制对象提供了控制输入法行为或从输入法获取信息的方法。对象的类型是特定于输入法的类。
     * 客户端必须将结果与已知的输入法控制对象类进行比较,并转换为适当的类来调用所提供的方法。
     * <p>
     * 
     * @return a control object from this input method, or null
     */
    public Object getControlObject();

}
