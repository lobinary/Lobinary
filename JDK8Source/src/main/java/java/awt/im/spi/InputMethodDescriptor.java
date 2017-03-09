/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.AWTException;
import java.awt.Image;
import java.util.Locale;

/**
 * Defines methods that provide sufficient information about an input method
 * to enable selection and loading of that input method.
 * The input method itself is only loaded when it is actually used.
 *
 * <p>
 *  定义提供有关输入法的足够信息以启用选择和加载该输入法的方法。输入法本身只在实际使用时加载。
 * 
 * 
 * @since 1.3
 */

public interface InputMethodDescriptor {

    /**
     * Returns the locales supported by the corresponding input method.
     * The locale may describe just the language, or may also include
     * country and variant information if needed.
     * The information is used to select input methods by locale
     * ({@link java.awt.im.InputContext#selectInputMethod(Locale)}). It may also
     * be used to sort input methods by locale in a user-visible
     * list of input methods.
     * <p>
     * Only the input method's primary locales should be returned.
     * For example, if a Japanese input method also has a pass-through
     * mode for Roman characters, typically still only Japanese would
     * be returned. Thus, the list of locales returned is typically
     * a subset of the locales for which the corresponding input method's
     * implementation of {@link java.awt.im.spi.InputMethod#setLocale} returns true.
     * <p>
     * If {@link #hasDynamicLocaleList} returns true, this method is
     * called each time the information is needed. This
     * gives input methods that depend on network resources the chance
     * to add or remove locales as resources become available or
     * unavailable.
     *
     * <p>
     *  返回相应输入法支持的语言环境。区域设置可以仅描述语言,或者如果需要也可以包括国家和变体信息。
     * 该信息用于通过语言环境({@link java.awt.im.InputContext#selectInputMethod(Locale)})选择输入方法。
     * 它还可以用于通过用户可见的输入方法列表中的区域设置来对输入方法进行排序。
     * <p>
     *  只应返回输入法的主语言环境。例如,如果日语输入法还具有罗马字符的传递模式,通常仍然只返回日语。
     * 因此,返回的语言环境列表通常是语言环境的子集,对应的输入法的{@link java.awt.im.spi.InputMethod#setLocale}实现返回true。
     * <p>
     *  如果{@link #hasDynamicLocaleList}返回true,则每次需要信息时调用此方法。这使得依赖于网络资源的输入方法在资源变得可用或不可用时添加或移除区域设置的机会。
     * 
     * 
     * @return the locales supported by the input method
     * @exception AWTException if it can be determined that the input method
     * is inoperable, for example, because of incomplete installation.
     */
    Locale[] getAvailableLocales() throws AWTException;

    /**
     * Returns whether the list of available locales can change
     * at runtime. This may be the case, for example, for adapters
     * that access real input methods over the network.
     * <p>
     * 返回可用语言环境的列表是否可以在运行时更改。这可以是例如对于通过网络访问实际输入方法的适配器的情况。
     * 
     */
    boolean hasDynamicLocaleList();

    /**
     * Returns the user-visible name of the corresponding
     * input method for the given input locale in the language in which
     * the name will be displayed.
     * <p>
     * The inputLocale parameter specifies the locale for which text
     * is input.
     * This parameter can only take values obtained from this descriptor's
     * {@link #getAvailableLocales} method or null. If it is null, an
     * input locale independent name for the input method should be
     * returned.
     * <p>
     * If a name for the desired display language is not available, the
     * method may fall back to some other language.
     *
     * <p>
     *  返回用于显示名称的语言中给定输入区域设置的相应输入法的用户可见名称。
     * <p>
     *  inputLocale参数指定输入文本的语言环境。此参数只能使用从此描述符的{@link #getAvailableLocales}方法获取的值或null。
     * 如果它为null,则应返回输入法的输入区域设置独立名称。
     * <p>
     *  如果所需的显示语言的名称不可用,则该方法可以回退到一些其他语言。
     * 
     * 
     * @param inputLocale the locale for which text input is supported, or null
     * @param displayLanguage the language in which the name will be displayed
     */
    String getInputMethodDisplayName(Locale inputLocale, Locale displayLanguage);

    /**
     * Returns an icon for the corresponding input method.
     * The icon may be used by a user interface for selecting input methods.
     * <p>
     * The inputLocale parameter specifies the locale for which text
     * is input.
     * This parameter can only take values obtained from this descriptor's
     * {@link #getAvailableLocales} method or null. If it is null, an
     * input locale independent icon for the input method should be
     * returned.
     * <p>
     * The icon's size should be 16&times;16 pixels.
     *
     * <p>
     *  返回相应输入法的图标。该图标可以由用户界面用于选择输入方法。
     * <p>
     *  inputLocale参数指定输入文本的语言环境。此参数只能使用从此描述符的{@link #getAvailableLocales}方法获取的值或null。
     * 如果为null,则应返回输入法的输入区域设置独立图标。
     * <p>
     * 
     * @param inputLocale the locale for which text input is supported, or null
     * @return an icon for the corresponding input method, or null
     */
    Image getInputMethodIcon(Locale inputLocale);

    /**
     * Creates a new instance of the corresponding input method.
     *
     * <p>
     *  图标的大小应为16&times; 16像素。
     * 
     * 
     * @return a new instance of the corresponding input method
     * @exception Exception any exception that may occur while creating the
     * input method instance
     */
    InputMethod createInputMethod() throws Exception;
}
