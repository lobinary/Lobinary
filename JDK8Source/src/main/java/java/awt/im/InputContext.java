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

package java.awt.im;

import java.awt.Component;
import java.util.Locale;
import java.awt.AWTEvent;
import java.beans.Transient;
import java.lang.Character.Subset;
import sun.awt.im.InputMethodContext;

/**
 * Provides methods to control text input facilities such as input
 * methods and keyboard layouts.
 * Two methods handle both input methods and keyboard layouts: selectInputMethod
 * lets a client component select an input method or keyboard layout by locale,
 * getLocale lets a client component obtain the locale of the current input method
 * or keyboard layout.
 * The other methods more specifically support interaction with input methods:
 * They let client components control the behavior of input methods, and
 * dispatch events from the client component to the input method.
 *
 * <p>
 * By default, one InputContext instance is created per Window instance,
 * and this input context is shared by all components within the window's
 * container hierarchy. However, this means that only one text input
 * operation is possible at any one time within a window, and that the
 * text needs to be committed when moving the focus from one text component
 * to another. If this is not desired, text components can create their
 * own input context instances.
 *
 * <p>
 * The Java Platform supports input methods that have been developed in the Java
 * programming language, using the interfaces in the {@link java.awt.im.spi} package,
 * and installed into a Java SE Runtime Environment as extensions. Implementations
 * may also support using the native input methods of the platforms they run on;
 * however, not all platforms and locales provide input methods. Keyboard layouts
 * are provided by the host platform.
 *
 * <p>
 * Input methods are <em>unavailable</em> if (a) no input method written
 * in the Java programming language has been installed and (b) the Java Platform implementation
 * or the underlying platform does not support native input methods. In this case,
 * input contexts can still be created and used; their behavior is specified with
 * the individual methods below.
 *
 * <p>
 *  提供控制文本输入设施(如输入法和键盘布局)的方法两种方法处理输入法和键盘布局：selectInputMethod允许客户端组件通过语言环境选择输入法或键盘布局,getLocale允许客户端组件获取当前
 * 语言环境的语言环境输入法或键盘布局其他方法更具体地支持与输入法的交互：它们允许客户端组件控制输入方法的行为,并将事件从客户端组件分派到输入法。
 * 
 * <p>
 * 默认情况下,每个Window实例创建一个InputContext实例,并且此输入上下文由窗口的容器层次结构中的所有组件共享。
 * 但是,这意味着在窗口内的任何时间只有一个文本输入操作是可能的,需要在将焦点从一个文本组件移动到另一个文本组件时提交。如果不需要,文本组件可以创建自己的输入上下文实例。
 * 
 * <p>
 * Java平台支持使用Java编程语言开发的输入方法,使用{@link javaawtimspi}包中的接口,并作为扩展安装到Java SE运行时环境中实现也可以支持使用平台的本机输入方法他们跑;然而,并
 * 非所有平台和语言环境都提供输入法键盘布局由主机平台提供。
 * 
 * <p>
 *  如果(a)没有安装以Java编程语言编写的输入法和(b)Java平台实现或底层平台不支持本机输入法,则输入方法不可用</em>在这种情况下,输入上下文仍然可以创建和使用;它们的行为用以下各个方法指定。
 * 
 * 
 * @see java.awt.Component#getInputContext
 * @see java.awt.Component#enableInputMethods
 * @author JavaSoft Asia/Pacific
 * @since 1.2
 */

public class InputContext {

    /**
     * Constructs an InputContext.
     * This method is protected so clients cannot instantiate
     * InputContext directly. Input contexts are obtained by
     * calling {@link #getInstance}.
     * <p>
     * 构造InputContext此方法受保护,因此客户端无法直接实例化InputContext。通过调用{@link #getInstance}获取输入上下文
     * 
     */
    protected InputContext() {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Returns a new InputContext instance.
     * <p>
     *  返回一个新的InputContext实例
     * 
     */
    public static InputContext getInstance() {
        return new sun.awt.im.InputMethodContext();
    }

    /**
     * Attempts to select an input method or keyboard layout that
     * supports the given locale, and returns a value indicating whether such
     * an input method or keyboard layout has been successfully selected. The
     * following steps are taken until an input method has been selected:
     *
     * <ul>
     * <li>
     * If the currently selected input method or keyboard layout supports the
     * requested locale, it remains selected.</li>
     *
     * <li>
     * If there is no input method or keyboard layout available that supports
     * the requested locale, the current input method or keyboard layout remains
     * selected.</li>
     *
     * <li>
     * If the user has previously selected an input method or keyboard layout
     * for the requested locale from the user interface, then the most recently
     * selected such input method or keyboard layout is reselected.</li>
     *
     * <li>
     * Otherwise, an input method or keyboard layout that supports the requested
     * locale is selected in an implementation dependent way.</li>
     *
     * </ul>
     * Before switching away from an input method, any currently uncommitted text
     * is committed. If no input method or keyboard layout supporting the requested
     * locale is available, then false is returned.
     *
     * <p>
     * Not all host operating systems provide API to determine the locale of
     * the currently selected native input method or keyboard layout, and to
     * select a native input method or keyboard layout by locale.
     * For host operating systems that don't provide such API,
     * <code>selectInputMethod</code> assumes that native input methods or
     * keyboard layouts provided by the host operating system support only the
     * system's default locale.
     *
     * <p>
     * A text editing component may call this method, for example, when
     * the user changes the insertion point, so that the user can
     * immediately continue typing in the language of the surrounding text.
     *
     * <p>
     *  尝试选择支持给定区域设置的输入法或键盘布局,并返回指示是否已成功选择此类输入法或键盘布局的值。直到选择输入法之前,将执行以下步骤：
     * 
     * <ul>
     * <li>
     *  如果当前选择的输入法或键盘布局支持请求的区域设置,则它仍保持选中状态</li>
     * 
     * <li>
     *  如果没有可用的支持请求的区域设置的输入法或键盘布局,则当前输入法或键盘布局仍保持选中状态</li>
     * 
     * <li>
     * 如果用户先前从用户界面为所请求的地区选择了输入法或键盘布局,则重新选择最近选择的这种输入法或键盘布局</li>
     * 
     * <li>
     *  否则,将以实现相关方式选择支持请求的区域设置的输入法或键盘布局</li>
     * 
     * </ul>
     *  在切换离开输入法之前,提交任何当前未提交的文本如果没有支持请求的语言环境的输入法或键盘布局可用,则返回false
     * 
     * <p>
     * 并非所有主机操作系统都提供API来确定当前选择的本地输入法或键盘布局的区域设置,并且通过区域设置选择本机输入法或键盘布局对于不提供此API的主机操作系统,<code> selectInputMethod
     *  </code>假定主机操作系统提供的本机输入法或键盘布局仅支持系统的默认语言环境。
     * 
     * <p>
     *  文本编辑组件可以调用此方法,例如,当用户改变插入点时,使得用户可以立即继续以周围文本的语言键入
     * 
     * 
     * @param locale The desired new locale.
     * @return true if the input method or keyboard layout that's active after
     *         this call supports the desired locale.
     * @exception NullPointerException if <code>locale</code> is null
     */
    public boolean selectInputMethod(Locale locale) {
        // real implementation is in sun.awt.im.InputContext
        return false;
    }

    /**
     * Returns the current locale of the current input method or keyboard
     * layout.
     * Returns null if the input context does not have a current input method
     * or keyboard layout or if the current input method's
     * {@link java.awt.im.spi.InputMethod#getLocale()} method returns null.
     *
     * <p>
     * Not all host operating systems provide API to determine the locale of
     * the currently selected native input method or keyboard layout.
     * For host operating systems that don't provide such API,
     * <code>getLocale</code> assumes that the current locale of all native
     * input methods or keyboard layouts provided by the host operating system
     * is the system's default locale.
     *
     * <p>
     * 返回当前输入法或键盘布局的当前语言环境如果输入上下文没有当前输入法或键盘布局或如果当前​​输入法的{@link javaawtimspiInputMethod#getLocale()}方法返回null,
     * 则返回null。
     * 
     * <p>
     *  并非所有主机操作系统都提供API来确定当前选择的本地输入法或键盘布局的区域设置对于不提供此类API的主机操作系统,<code> getLocale </code>假设所有本机输入的当前语言环境主机操作
     * 系统提供的方法或键盘布局是系统的默认区域设置。
     * 
     * 
     * @return the current locale of the current input method or keyboard layout
     * @since 1.3
     */
    public Locale getLocale() {
        // real implementation is in sun.awt.im.InputContext
        return null;
    }

    /**
     * Sets the subsets of the Unicode character set that input methods of this input
     * context should be allowed to input. Null may be passed in to
     * indicate that all characters are allowed. The initial value
     * is null. The setting applies to the current input method as well
     * as input methods selected after this call is made. However,
     * applications cannot rely on this call having the desired effect,
     * since this setting cannot be passed on to all host input methods -
     * applications still need to apply their own character validation.
     * If no input methods are available, then this method has no effect.
     *
     * <p>
     * 设置应允许此输入上下文的输入方法输入的Unicode字符集的子集Null可以传入以指示允许所有字符初始值为null此设置适用于当前输入法以及输入法在此调用之后选择但是,应用程序不能依赖具有期望效果的此调
     * 用,因为此设置不能传递到所有主机输入方法 - 应用程序仍然需要应用自己的字符验证如果没有可用的输入方法,方法没有效果。
     * 
     * 
     * @param subsets The subsets of the Unicode character set from which characters may be input
     */
    public void setCharacterSubsets(Subset[] subsets) {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Enables or disables the current input method for composition,
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
     *
     * <p>
     *  根据参数<code> enable </code>的值启用或禁用组合的当前输入方法
     * <p>
     * 为组合启用的组合输入方法为组合和控制目的解释传入的事件,而禁用的输入方法不解释组合的事件。
     * 但是,无论事件是否启用,事件都传递到输入方法,被禁用以进行合成的输入法仍然可以解释用于控制目的的事件,包括为了合成而启用或禁用自身。
     * <p>
     * 对于由主机操作系统提供的输入方法,不总是能够确定是否支持该操作。例如,输入方法可以使得能够仅针对一些区域进行合成,并且对于其他区域不进行任何操作。
     * 对于这样的输入方法,可能的是,此方法不会抛出{@link javalangUnsupportedOperationException UnsupportedOperationException},但也不
     * 会影响是否启用合成。
     * 对于由主机操作系统提供的输入方法,不总是能够确定是否支持该操作。例如,输入方法可以使得能够仅针对一些区域进行合成,并且对于其他区域不进行任何操作。
     * 
     * 
     * @param enable whether to enable the current input method for composition
     * @throws UnsupportedOperationException if there is no current input
     * method available or the current input method does not support
     * the enabling/disabling operation
     * @see #isCompositionEnabled
     * @since 1.3
     */
    public void setCompositionEnabled(boolean enable) {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Determines whether the current input method is enabled for composition.
     * An input method that is enabled for composition interprets incoming
     * events for both composition and control purposes, while a
     * disabled input method does not interpret events for composition.
     *
     * <p>
     *  确定是否为组合启用当前输入法为组合启用的输入法为组合和控制目的解释传入事件,而禁用的输入法不解释组合的事件
     * 
     * 
     * @return <code>true</code> if the current input method is enabled for
     * composition; <code>false</code> otherwise
     * @throws UnsupportedOperationException if there is no current input
     * method available or the current input method does not support
     * checking whether it is enabled for composition
     * @see #setCompositionEnabled
     * @since 1.3
     */
    @Transient
    public boolean isCompositionEnabled() {
        // real implementation is in sun.awt.im.InputContext
        return false;
    }

    /**
     * Asks the current input method to reconvert text from the
     * current client component. The input method obtains the text to
     * be reconverted from the client component using the
     * {@link InputMethodRequests#getSelectedText InputMethodRequests.getSelectedText}
     * method. The other <code>InputMethodRequests</code> methods
     * must be prepared to deal with further information requests by
     * the input method. The composed and/or committed text will be
     * sent to the client component as a sequence of
     * <code>InputMethodEvent</code>s. If the input method cannot
     * reconvert the given text, the text is returned as committed
     * text in an <code>InputMethodEvent</code>.
     *
     * <p>
     * 要求当前输入方法从当前客户端组件重新转换文本输入方法使用{@link InputMethodRequests#getSelectedText InputMethodRequestsgetSelectedText}
     * 方法从客户端组件获取要重新转换的文本其他<code> InputMethodRequests </code>方法必须准备通过输入法处理进一步的信息请求组合和/或提交的文本将作为<code> InputM
     * ethodEvent </code>的序列发送到客户端组件如果输入方法不能重新转换给定的文本,文本作为<code> InputMethodEvent </code>中的提交文本返回。
     * 
     * 
     * @throws UnsupportedOperationException if there is no current input
     * method available or the current input method does not support
     * the reconversion operation.
     *
     * @since 1.3
     */
    public void reconvert() {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Dispatches an event to the active input method. Called by AWT.
     * If no input method is available, then the event will never be consumed.
     *
     * <p>
     *  将事件分派到活动输入法由AWT调用如果没有可用的输入法,则不会消耗该事件
     * 
     * 
     * @param event The event
     * @exception NullPointerException if <code>event</code> is null
     */
    public void dispatchEvent(AWTEvent event) {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Notifies the input context that a client component has been
     * removed from its containment hierarchy, or that input method
     * support has been disabled for the component. This method is
     * usually called from the client component's
     * {@link java.awt.Component#removeNotify() Component.removeNotify}
     * method. Potentially pending input from input methods
     * for this component is discarded.
     * If no input methods are available, then this method has no effect.
     *
     * <p>
     * 通知客户端组件已从其包含层次结构中删除的输入上下文,或者该组件的输入法支持已禁用此方法通常从客户端组件的{@link javaawtComponent#removeNotify()ComponentremoveNotify}
     * 方法调用潜在未决输入从此组件的输入方法被丢弃如果没有可用的输入方法,则此方法没有效果。
     * 
     * 
     * @param client Client component
     * @exception NullPointerException if <code>client</code> is null
     */
    public void removeNotify(Component client) {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Ends any input composition that may currently be going on in this
     * context. Depending on the platform and possibly user preferences,
     * this may commit or delete uncommitted text. Any changes to the text
     * are communicated to the active component using an input method event.
     * If no input methods are available, then this method has no effect.
     *
     * <p>
     * A text editing component may call this in a variety of situations,
     * for example, when the user moves the insertion point within the text
     * (but outside the composed text), or when the component's text is
     * saved to a file or copied to the clipboard.
     *
     * <p>
     *  结束在此上下文中可能正在进行的任何输入组合根据平台和可能的用户首选项,这可能提交或删除未提交的文本使用输入法事件将文本的任何更改传送到活动组件如果没有输入法可用,那么这个方法没有效果
     * 
     * <p>
     * 文本编辑组件可以在各种情况下调用它,例如,当用户在文本内移动插入点(但是在组合文本之外)时,或者当组件的文本被保存到文件或复制到剪贴板时
     * 
     */
    public void endComposition() {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Releases the resources used by this input context.
     * Called by AWT for the default input context of each Window.
     * If no input methods are available, then this method
     * has no effect.
     * <p>
     *  释放此输入上下文使用的资源由​​AWT调用每个窗口的默认输入上下文如果没有输入方法可用,则此方法没有效果
     * 
     */
    public void dispose() {
        // real implementation is in sun.awt.im.InputContext
    }

    /**
     * Returns a control object from the current input method, or null. A
     * control object provides methods that control the behavior of the
     * input method or obtain information from the input method. The type
     * of the object is an input method specific class. Clients have to
     * compare the result against known input method control object
     * classes and cast to the appropriate class to invoke the methods
     * provided.
     * <p>
     * If no input methods are available or the current input method does
     * not provide an input method control object, then null is returned.
     *
     * <p>
     * 从当前输入法返回控制对象或null控制对象提供控制输入法行为的方法或从输入法获取信息对象的类型是特定于输入法的类客户必须将结果与已知的输入法控制对象类,并转换为适当的类来调用所提供的方法
     * <p>
     * 
     * @return A control object from the current input method, or null.
     */
    public Object getInputMethodControlObject() {
        // real implementation is in sun.awt.im.InputContext
        return null;
    }

}
