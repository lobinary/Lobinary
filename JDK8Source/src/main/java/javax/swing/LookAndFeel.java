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

import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Toolkit;
import sun.awt.SunToolkit;

import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.plaf.*;

import java.net.URL;
import sun.swing.SwingUtilities2;
import sun.swing.DefaultLayoutStyle;
import sun.swing.ImageIconUIResource;

import java.util.StringTokenizer;


/**
 * {@code LookAndFeel}, as the name implies, encapsulates a look and
 * feel. Beyond installing a look and feel most developers never need to
 * interact directly with {@code LookAndFeel}. In general only developers
 * creating a custom look and feel need to concern themselves with this class.
 * <p>
 * Swing is built upon the foundation that each {@code JComponent}
 * subclass has an implementation of a specific {@code ComponentUI}
 * subclass. The {@code ComponentUI} is often referred to as "the ui",
 * "component ui", or "look and feel delegate". The {@code ComponentUI}
 * subclass is responsible for providing the look and feel specific
 * functionality of the component. For example, {@code JTree} requires
 * an implementation of the {@code ComponentUI} subclass {@code
 * TreeUI}. The implementation of the specific {@code
 * ComponentUI} subclass is provided by the {@code LookAndFeel}. Each
 * {@code JComponent} subclass identifies the {@code ComponentUI}
 * subclass it requires by way of the {@code JComponent} method {@code
 * getUIClassID}.
 * <p>
 * Each {@code LookAndFeel} implementation must provide
 * an implementation of the appropriate {@code ComponentUI} subclass by
 * specifying a value for each of Swing's ui class ids in the {@code
 * UIDefaults} object returned from {@code getDefaults}. For example,
 * {@code BasicLookAndFeel} uses {@code BasicTreeUI} as the concrete
 * implementation for {@code TreeUI}. This is accomplished by {@code
 * BasicLookAndFeel} providing the key-value pair {@code
 * "TreeUI"-"javax.swing.plaf.basic.BasicTreeUI"}, in the
 * {@code UIDefaults} returned from {@code getDefaults}. Refer to
 * {@link UIDefaults#getUI(JComponent)} for details on how the implementation
 * of the {@code ComponentUI} subclass is obtained.
 * <p>
 * When a {@code LookAndFeel} is installed the {@code UIManager} does
 * not check that an entry exists for all ui class ids. As such,
 * random exceptions will occur if the current look and feel has not
 * provided a value for a particular ui class id and an instance of
 * the {@code JComponent} subclass is created.
 *
 * <h2>Recommendations for Look and Feels</h2>
 *
 * As noted in {@code UIManager} each {@code LookAndFeel} has the opportunity
 * to provide a set of defaults that are layered in with developer and
 * system defaults. Some of Swing's components require the look and feel
 * to provide a specific set of defaults. These are documented in the
 * classes that require the specific default.
 *
 * <h3><a name="defaultRecommendation">ComponentUIs and defaults</a></h3>
 *
 * All {@code ComponentUIs} typically need to set various properties
 * on the {@code JComponent} the {@code ComponentUI} is providing the
 * look and feel for. This is typically done when the {@code
 * ComponentUI} is installed on the {@code JComponent}. Setting a
 * property should only be done if the developer has not set the
 * property. For non-primitive values it is recommended that the
 * {@code ComponentUI} only change the property on the {@code
 * JComponent} if the current value is {@code null} or implements
 * {@code UIResource}. If the current value is {@code null} or
 * implements {@code UIResource} it indicates the property has not
 * been set by the developer, and the ui is free to change it.  For
 * example, {@code BasicButtonUI.installDefaults} only changes the
 * font on the {@code JButton} if the return value from {@code
 * button.getFont()} is {@code null} or implements {@code
 * UIResource}. On the other hand if {@code button.getFont()} returned
 * a {@code non-null} value that did not implement {@code UIResource}
 * then {@code BasicButtonUI.installDefaults} would not change the
 * {@code JButton}'s font.
 * <p>
 * For primitive values, such as {@code opaque}, the method {@code
 * installProperty} should be invoked.  {@code installProperty} only changes
 * the corresponding property if the value has not been changed by the
 * developer.
 * <p>
 * {@code ComponentUI} implementations should use the various install methods
 * provided by this class as they handle the necessary checking and install
 * the property using the recommended guidelines.
 *
 * <h3><a name="exceptions"></a>Exceptions</h3>
 *
 * All of the install methods provided by {@code LookAndFeel} need to
 * access the defaults if the value of the property being changed is
 * {@code null} or a {@code UIResource}. For example, installing the
 * font does the following:
 * <pre>
 *   JComponent c;
 *   Font font = c.getFont();
 *   if (font == null || (font instanceof UIResource)) {
 *       c.setFont(UIManager.getFont("fontKey"));
 *   }
 * </pre>
 * If the font is {@code null} or a {@code UIResource}, the
 * defaults table is queried with the key {@code fontKey}. All of
 * {@code UIDefault's} get methods throw a {@code
 * NullPointerException} if passed in {@code null}. As such, unless
 * otherwise noted each of the various install methods of {@code
 * LookAndFeel} throw a {@code NullPointerException} if the current
 * value is {@code null} or a {@code UIResource} and the supplied
 * defaults key is {@code null}. In addition, unless otherwise specified
 * all of the {@code install} methods throw a {@code NullPointerException} if
 * a {@code null} component is passed in.
 *
 * <p>
 *  {@code LookAndFeel},顾名思义,封装了外观和感觉。除了安装外观,大多数开发人员不需要直接与{@code LookAndFeel}进行交互。
 * 一般来说,只有开发者创建自定义的外观和感觉需要关心这个类。
 * <p>
 *  Swing建立在每个{@code JComponent}子类具有特定{@code ComponentUI}子类的实现的基础之上。
 *  {@code ComponentUI}通常被称为"ui","组件ui"或"观感委托"。 {@code ComponentUI}子类负责提供组件的外观和感觉特定功能。
 * 例如,{@code JTree}需要实现{@code ComponentUI}子类{@code TreeUI}。
 * 特定{@code ComponentUI}子类的实现由{@code LookAndFeel}提供。
 * 每个{@code JComponent}子类通过{@code JComponent}方法{@code getUIClassID}标识它需要的{@code ComponentUI}子类。
 * <p>
 * 每个{@code LookAndFeel}实现必须通过为从{@code getDefaults}返回的{@code UIDefaults}对象中的每个Swing的ui类id指定一个值,提供适当的{@code ComponentUI}
 * 子类的实现。
 * 例如,{@code BasicLookAndFeel}使用{@code BasicTreeUI}作为{@code TreeUI}的具体实现。
 * 这是通过{@code BasicLookAndFeel}在{@code getDefaults}返回的{@code UIDefaults}中提供键值对{@code"TreeUI" - "javax.swing.plaf.basic.BasicTreeUI"}
 * 来实现的。
 * 例如,{@code BasicLookAndFeel}使用{@code BasicTreeUI}作为{@code TreeUI}的具体实现。
 * 有关如何获取{@code ComponentUI}子类的实现的详细信息,请参阅{@link UIDefaults#getUI(JComponent)}。
 * <p>
 *  当安装了{@code LookAndFeel}时,{@code UIManager}不会检查是否存在所有ui类id的条目。
 * 因此,如果当前的外观和感觉没有为特定的ui类id提供值,并且创建了{@code JComponent}子类的实例,则会发生随机异常。
 * 
 *  <h2>外观和感觉的建议</h2>
 * 
 *  如{@code UIManager}中所述,每个{@code LookAndFeel}都有机会提供一组默认值,这些默认值与开发者和系统默认值分层。
 * 一些Swing的组件需要外观和感觉来提供一组特定的默认值。这些在需要特定默认的类中记录。
 * 
 *  <h3> <a name="defaultRecommendation"> ComponentUI和默认值</a> </h3>
 * 
 * 所有{@code ComponentUIs}通常需要在{@code JComponent}上设置各种属性,{@code ComponentUI}提供了外观和感觉。
 * 这通常在{@code JComponent}上安装{@code ComponentUI}时完成。只有在开发人员未设置属性时,才应设置属性。
 * 对于非原始值,如果当前值为{@code null}或实现{@code UIResource},则建议{@code ComponentUI}仅更改{@code JComponent}上的属性。
 * 如果当前值是{@code null}或implements {@code UIResource},则表示开发人员尚未设置属性,并且ui可以自由更改它。
 * 例如,如果{@code button.getFont()}的返回值是{@code null}或实现{@code UIResource},{@code BasicButtonUI.installDefaults}
 * 只会更改{@code JButton}上的字体。
 * 如果当前值是{@code null}或implements {@code UIResource},则表示开发人员尚未设置属性,并且ui可以自由更改它。
 * 另一方面,如果{@code button.getFont()}返回了一个没有实现{@code UIResource}的{@code non-null}值,那么{@code BasicButtonUI.installDefaults}
 * 不会改变{@code JButton}的字体。
 * 如果当前值是{@code null}或implements {@code UIResource},则表示开发人员尚未设置属性,并且ui可以自由更改它。
 * <p>
 *  对于原始值,例如{@code opaque},应调用{@code installProperty}方法。 {@code installProperty}只有在开发人员未更改该值时才会更改相应的属性。
 * <p>
 * {@code ComponentUI}实现应该使用此类提供的各种安装方法,因为它们处理必要的检查并使用推荐的准则安装属性。
 * 
 *  <h3> <a name="exceptions"> </a>例外</h3>
 * 
 *  如果要更改的属性的值为{@code null}或{@code UIResource},{@code LookAndFeel}提供的所有安装方法都需要访问默认值。例如,安装该字体会执行以下操作：
 * <pre>
 *  JComponent c; Font font = c.getFont(); if(font == null ||(font instanceof UIResource)){c.setFont(UIManager.getFont("fontKey")); }
 * }。
 * </pre>
 *  如果字体是{@code null}或{@code UIResource},则使用键{@code fontKey}查询defaults表。
 * 所有{@code UIDefault的} get方法在{@code null}中传递时会抛出一个{@code NullPointerException}。
 * 因此,除非另有说明,如果当前值为{@code null}或{@code UIResource}并且提供的默认值为{@code LookAndFeel},{@code LookAndFeel}的各种安装方
 * 法都会抛出{@code NullPointerException}代码null}。
 * 所有{@code UIDefault的} get方法在{@code null}中传递时会抛出一个{@code NullPointerException}。
 * 此外,除非另有规定,否则所有{@code install}方法在传递{@code null}组件时会抛出{@code NullPointerException}。
 * 
 * 
 * @author Tom Ball
 * @author Hans Muller
 */
public abstract class LookAndFeel
{

    /**
     * Convenience method for setting a component's foreground
     * and background color properties with values from the
     * defaults.  The properties are only set if the current
     * value is either {@code null} or a {@code UIResource}.
     *
     * <p>
     *  方便方法用于使用默认值设置组件的前景和背景颜色属性。仅当当前值为{@code null}或{@code UIResource}时,才设置属性。
     * 
     * 
     * @param c component to set the colors on
     * @param defaultBgName key for the background
     * @param defaultFgName key for the foreground
     *
     * @see #installColorsAndFont
     * @see UIManager#getColor
     * @throws NullPointerException as described in
     *         <a href="#exceptions">exceptions</a>
     */
    public static void installColors(JComponent c,
                                     String defaultBgName,
                                     String defaultFgName)
    {
        Color bg = c.getBackground();
        if (bg == null || bg instanceof UIResource) {
            c.setBackground(UIManager.getColor(defaultBgName));
        }

        Color fg = c.getForeground();
        if (fg == null || fg instanceof UIResource) {
            c.setForeground(UIManager.getColor(defaultFgName));
        }
    }


    /**
     * Convenience method for setting a component's foreground,
     * background and font properties with values from the
     * defaults.  The properties are only set if the current
     * value is either {@code null} or a {@code UIResource}.
     *
     * <p>
     * 方便方法用于使用默认值设置组件的前景,背景和字体属性。仅当当前值为{@code null}或{@code UIResource}时,才设置属性。
     * 
     * 
     * @param c component set to the colors and font on
     * @param defaultBgName key for the background
     * @param defaultFgName key for the foreground
     * @param defaultFontName key for the font
     * @throws NullPointerException as described in
     *         <a href="#exceptions">exceptions</a>
     *
     * @see #installColors
     * @see UIManager#getColor
     * @see UIManager#getFont
     */
    public static void installColorsAndFont(JComponent c,
                                         String defaultBgName,
                                         String defaultFgName,
                                         String defaultFontName) {
        Font f = c.getFont();
        if (f == null || f instanceof UIResource) {
            c.setFont(UIManager.getFont(defaultFontName));
        }

        installColors(c, defaultBgName, defaultFgName);
    }


    /**
     * Convenience method for setting a component's border property with
     * a value from the defaults. The border is only set if the border is
     * {@code null} or an instance of {@code UIResource}.
     *
     * <p>
     *  方便方法用于使用默认值设置组件的border属性。仅当边框为{@code null}或{@code UIResource}的实例时,才设置边框。
     * 
     * 
     * @param c component to set the border on
     * @param defaultBorderName key specifying the border
     * @throws NullPointerException as described in
     *         <a href="#exceptions">exceptions</a>
     */
    public static void installBorder(JComponent c, String defaultBorderName) {
        Border b = c.getBorder();
        if (b == null || b instanceof UIResource) {
            c.setBorder(UIManager.getBorder(defaultBorderName));
        }
    }


    /**
     * Convenience method for uninstalling a border. If the border of
     * the component is a {@code UIResource}, it is set to {@code
     * null}.
     *
     * <p>
     *  方便卸载边框的方法。如果组件的边框是{@code UIResource},则设置为{@code null}。
     * 
     * 
     * @param c component to uninstall the border on
     * @throws NullPointerException if {@code c} is {@code null}
     */
    public static void uninstallBorder(JComponent c) {
        if (c.getBorder() instanceof UIResource) {
            c.setBorder(null);
        }
    }

    /**
     * Convenience method for installing a property with the specified name
     * and value on a component if that property has not already been set
     * by the developer.  This method is intended to be used by
     * ui delegate instances that need to specify a default value for a
     * property of primitive type (boolean, int, ..), but do not wish
     * to override a value set by the client.  Since primitive property
     * values cannot be wrapped with the {@code UIResource} marker, this method
     * uses private state to determine whether the property has been set
     * by the client.
     *
     * <p>
     *  方便方法用于在组件上安装具有指定名称和值的属性(如果开发人员尚未设置该属性)。此方法旨在由需要为基本类型(boolean,int,..)的属性指定默认值但不希望覆盖客户端设置的值的ui委托实例使用。
     * 由于原始属性值不能用{@code UIResource}标记包装,所以此方法使用私有状态来确定属性是否已由客户机设置。
     * 
     * 
     * @throws IllegalArgumentException if the specified property is not
     *         one which can be set using this method
     * @throws ClassCastException if the property value has not been set
     *         by the developer and the type does not match the property's type
     * @throws NullPointerException if {@code c} is {@code null}, or the
     *         named property has not been set by the developer and
     *         {@code propertyValue} is {@code null}
     * @param c target component to set the property on
     * @param propertyName name of the property to set
     * @param propertyValue value of the property
     * @since 1.5
     */
    public static void installProperty(JComponent c,
                                       String propertyName, Object propertyValue) {
        // this is a special case because the JPasswordField's ancestor hierarchy
        // includes a class outside of javax.swing, thus we cannot call setUIProperty
        // directly.
        if (SunToolkit.isInstanceOf(c, "javax.swing.JPasswordField")) {
            if (!((JPasswordField)c).customSetUIProperty(propertyName, propertyValue)) {
                c.setUIProperty(propertyName, propertyValue);
            }
        } else {
            c.setUIProperty(propertyName, propertyValue);
        }
    }

    /**
     * Convenience method for building an array of {@code
     * KeyBindings}. While this method is not deprecated, developers
     * should instead use {@code ActionMap} and {@code InputMap} for
     * supplying key bindings.
     * <p>
     * This method returns an array of {@code KeyBindings}, one for each
     * alternating {@code key-action} pair in {@code keyBindingList}.
     * A {@code key} can either be a {@code String} in the format
     * specified by the <code>KeyStroke.getKeyStroke</code> method, or
     * a {@code KeyStroke}. The {@code action} part of the pair is a
     * {@code String} that corresponds to the name of the {@code
     * Action}.
     * <p>
     * The following example illustrates creating a {@code KeyBinding} array
     * from six alternating {@code key-action} pairs:
     * <pre>
     *  JTextComponent.KeyBinding[] multilineBindings = makeKeyBindings( new Object[] {
     *          "UP", DefaultEditorKit.upAction,
     *        "DOWN", DefaultEditorKit.downAction,
     *     "PAGE_UP", DefaultEditorKit.pageUpAction,
     *   "PAGE_DOWN", DefaultEditorKit.pageDownAction,
     *       "ENTER", DefaultEditorKit.insertBreakAction,
     *         "TAB", DefaultEditorKit.insertTabAction
     *  });
     * </pre>
     * If {@code keyBindingList's} length is odd, the last element is
     * ignored.
     * <p>
     * Supplying a {@code null} value for either the {@code key} or
     * {@code action} part of the {@code key-action} pair results in
     * creating a {@code KeyBinding} with the corresponding value
     * {@code null}. As other parts of Swing's expect {@code non-null} values
     * in a {@code KeyBinding}, you should avoid supplying {@code null} as
     * either the {@code key} or {@code action} part of the {@code key-action}
     * pair.
     *
     * <p>
     *  构建{@code KeyBindings}数组的便捷方法。虽然这个方法不被弃用,但开发人员应该使用{@code ActionMap}和{@code InputMap}来提供键绑定。
     * <p>
     * 此方法返回{@code KeyBindings}数组,每个{@code keyBactionList}中的每个交替{@code key-action}对都有一个。
     *  {@code key}可以是以<code> KeyStroke.getKeyStroke </code>方法指定的格式的{@code String},也可以是{@code KeyStroke}。
     * 对的{@code action}部分是{@code String},对应于{@code Action}的名称。
     * <p>
     *  以下示例说明了从六个交替的{@code key-action}对创建{@code KeyBinding}数组：
     * <pre>
     *  JTextComponent.KeyBinding [] multilineBindings = makeKeyBindings(new Object [] {"UP",DefaultEditorKit.upAction,"DOWN",DefaultEditorKit.downAction,"PAGE_UP",DefaultEditorKit.pageUpAction,"PAGE_DOWN",DefaultEditorKit.pageDownAction,"ENTER" DefaultEditorKit.insertBreakAction,"TAB",DefaultEditorKit.insertTabAction}
     * );。
     * </pre>
     *  如果{@code keyBindingList's}的长度是奇数,最后一个元素被忽略。
     * <p>
     *  为{@code key-action}对的{@code key}或{@code action}部分提供{@code null}值会导致创建具有相应值的{@code KeyBinding} {@code null }
     * 。
     * 由于Swing的其他部分期望在{@code KeyBinding}中使用{@code non-null}值,因此您应避免将{@code null}提供为​​{@code key}或{@code action}
     * 代码键 - 动作}对。
     * 
     * 
     * @param keyBindingList an array of {@code key-action} pairs
     * @return an array of {@code KeyBindings}
     * @throws NullPointerException if {@code keyBindingList} is {@code null}
     * @throws ClassCastException if the {@code key} part of the pair is
     *         not a {@code KeyStroke} or {@code String}, or the
     *         {@code action} part of the pair is not a {@code String}
     * @see ActionMap
     * @see InputMap
     * @see KeyStroke#getKeyStroke
     */
    public static JTextComponent.KeyBinding[] makeKeyBindings(Object[] keyBindingList)
    {
        JTextComponent.KeyBinding[] rv = new JTextComponent.KeyBinding[keyBindingList.length / 2];

        for(int i = 0; i < rv.length; i ++) {
            Object o = keyBindingList[2 * i];
            KeyStroke keystroke = (o instanceof KeyStroke)
                ? (KeyStroke) o
                : KeyStroke.getKeyStroke((String) o);
            String action = (String) keyBindingList[2 * i + 1];
            rv[i] = new JTextComponent.KeyBinding(keystroke, action);
        }

        return rv;
    }

    /**
     * Creates a {@code InputMapUIResource} from <code>keys</code>. This is
     * a convenience method for creating a new {@code InputMapUIResource},
     * invoking {@code loadKeyBindings(map, keys)}, and returning the
     * {@code InputMapUIResource}.
     *
     * <p>
     * 从<code>键</code>创建{@code InputMapUIResource}。
     * 这是创建新的{@code InputMapUIResource},调用{@code loadKeyBindings(map,keys)}并返回{@code InputMapUIResource}的简便方
     * 法。
     * 从<code>键</code>创建{@code InputMapUIResource}。
     * 
     * 
     * @param keys alternating pairs of {@code keystroke-action key}
     *        pairs as described in {@link #loadKeyBindings}
     * @return newly created and populated {@code InputMapUIResource}
     * @see #loadKeyBindings
     *
     * @since 1.3
     */
    public static InputMap makeInputMap(Object[] keys) {
        InputMap retMap = new InputMapUIResource();
        loadKeyBindings(retMap, keys);
        return retMap;
    }

    /**
     * Creates a {@code ComponentInputMapUIResource} from
     * <code>keys</code>. This is a convenience method for creating a
     * new {@code ComponentInputMapUIResource}, invoking {@code
     * loadKeyBindings(map, keys)}, and returning the {@code
     * ComponentInputMapUIResource}.
     *
     * <p>
     *  从<code>键</code>创建{@code ComponentInputMapUIResource}。
     * 这是创建一个新的{@code ComponentInputMapUIResource},调用{@code loadKeyBindings(map,keys)}并返回{@code ComponentInputMapUIResource}
     * 的简便方法。
     *  从<code>键</code>创建{@code ComponentInputMapUIResource}。
     * 
     * 
     * @param c component to create the {@code ComponentInputMapUIResource}
     *          with
     * @param keys alternating pairs of {@code keystroke-action key}
     *        pairs as described in {@link #loadKeyBindings}
     * @return newly created and populated {@code InputMapUIResource}
     * @throws IllegalArgumentException if {@code c} is {@code null}
     *
     * @see #loadKeyBindings
     * @see ComponentInputMapUIResource
     *
     * @since 1.3
     */
    public static ComponentInputMap makeComponentInputMap(JComponent c,
                                                          Object[] keys) {
        ComponentInputMap retMap = new ComponentInputMapUIResource(c);
        loadKeyBindings(retMap, keys);
        return retMap;
    }


    /**
     * Populates an {@code InputMap} with the specified bindings.
     * The bindings are supplied as a list of alternating
     * {@code keystroke-action key} pairs. The {@code keystroke} is either
     * an instance of {@code KeyStroke}, or a {@code String}
     * that identifies the {@code KeyStroke} for the binding. Refer
     * to {@code KeyStroke.getKeyStroke(String)} for the specific
     * format. The {@code action key} part of the pair is the key
     * registered in the {@code InputMap} for the {@code KeyStroke}.
     * <p>
     * The following illustrates loading an {@code InputMap} with two
     * {@code key-action} pairs:
     * <pre>
     *   LookAndFeel.loadKeyBindings(inputMap, new Object[] {
     *     "control X", "cut",
     *     "control V", "paste"
     *   });
     * </pre>
     * <p>
     * Supplying a {@code null} list of bindings ({@code keys}) does not
     * change {@code retMap} in any way.
     * <p>
     * Specifying a {@code null} {@code action key} results in
     * removing the {@code keystroke's} entry from the {@code InputMap}.
     * A {@code null} {@code keystroke} is ignored.
     *
     * <p>
     *  使用指定的绑定填充{@code InputMap}。绑定作为交替的{@code keystroke-action key}对的列表提供。
     *  {@code keystroke}既可以是{@code KeyStroke}的实例,也可以是{@code String},用于标识绑定的{@code KeyStroke}。
     * 有关特定格式,请参阅{@code KeyStroke.getKeyStroke(String)}。
     * 该对的{@code action key}部分是在{@code KeyStroke}的{@code InputMap}中注册的键。
     * <p>
     *  以下说明了加载{@code InputMap}与两个{@code key-action}对：
     * <pre>
     *  LookAndFeel.loadKeyBindings(inputMap,new Object [] {"control X","cut","control V","paste"});
     * </pre>
     * <p>
     *  提供{@code null}绑定列表({@code keys})不会以任何方式更改{@code retMap}。
     * <p>
     *  指定{@code null} {@code action key}会导致从{@code InputMap}中删除{@code keystroke's}条目。
     * 将忽略{@code null} {@code keystroke}。
     * 
     * 
     * @param retMap {@code InputMap} to add the {@code key-action}
     *               pairs to
     * @param keys bindings to add to {@code retMap}
     * @throws NullPointerException if {@code keys} is
     *         {@code non-null}, not empty, and {@code retMap} is
     *         {@code null}
     *
     * @see KeyStroke#getKeyStroke(String)
     * @see InputMap
     *
     * @since 1.3
     */
    public static void loadKeyBindings(InputMap retMap, Object[] keys) {
        if (keys != null) {
            for (int counter = 0, maxCounter = keys.length;
                 counter < maxCounter; counter++) {
                Object keyStrokeO = keys[counter++];
                KeyStroke ks = (keyStrokeO instanceof KeyStroke) ?
                                (KeyStroke)keyStrokeO :
                                KeyStroke.getKeyStroke((String)keyStrokeO);
                retMap.put(ks, keys[counter]);
            }
        }
    }

    /**
     * Creates and returns a {@code UIDefault.LazyValue} that loads an
     * image. The returned value is an implementation of {@code
     * UIDefaults.LazyValue}. When {@code createValue} is invoked on
     * the returned object, the image is loaded. If the image is {@code
     * non-null}, it is then wrapped in an {@code Icon} that implements {@code
     * UIResource}. The image is loaded using {@code
     * Class.getResourceAsStream(gifFile)}.
     * <p>
     * This method does not check the arguments in any way. It is
     * strongly recommended that {@code non-null} values are supplied else
     * exceptions may occur when {@code createValue} is invoked on the
     * returned object.
     *
     * <p>
     * 创建并返回加载图片的{@code UIDefault.LazyValue}。返回的值是{@code UIDefaults.LazyValue}的实现。
     * 当对返回的对象调用{@code createValue}时,将加载图像。如果图像是{@code non-null},那么它被包装在实现{@code UIResource}的{@code Icon}中。
     * 使用{@code Class.getResourceAsStream(gifFile)}加载图像。
     * <p>
     *  此方法不以任何方式检查参数。强烈建议提供{@code non-null}值,否则在对返回的对象调用{@code createValue}时可能会出现异常。
     * 
     * 
     * @param baseClass {@code Class} used to load the resource
     * @param gifFile path to the image to load
     * @return a {@code UIDefaults.LazyValue}; when resolved the
     *         {@code LazyValue} loads the specified image
     * @see UIDefaults.LazyValue
     * @see Icon
     * @see Class#getResourceAsStream(String)
     */
    public static Object makeIcon(final Class<?> baseClass, final String gifFile) {
        return SwingUtilities2.makeIcon(baseClass, baseClass, gifFile);
    }

    /**
     * Returns the <code>LayoutStyle</code> for this look
     * and feel.  This never returns {@code null}.
     * <p>
     * You generally don't use the <code>LayoutStyle</code> from
     * the look and feel, instead use the <code>LayoutStyle</code>
     * method <code>getInstance</code>.
     *
     * <p>
     *  返回此代码的<code> LayoutStyle </code>。这永远不会返回{@code null}。
     * <p>
     *  通常不要使用<code> LayoutStyle </code>的外观,而是使用<code> LayoutStyle </code>方法<code> getInstance </code>。
     * 
     * 
     * @see LayoutStyle#getInstance
     * @return the <code>LayoutStyle</code> for this look and feel
     * @since 1.6
     */
    public LayoutStyle getLayoutStyle() {
        return DefaultLayoutStyle.getInstance();
    }

    /**
     * Invoked when the user attempts an invalid operation,
     * such as pasting into an uneditable <code>JTextField</code>
     * that has focus. The default implementation beeps. Subclasses
     * that wish different behavior should override this and provide
     * the additional feedback.
     *
     * <p>
     *  当用户尝试无效操作时调用,例如粘贴到具有焦点的不可编辑的<code> JTextField </code>中。默认实现蜂鸣声。希望不同行为的子类应该覆盖此并提供额外的反馈。
     * 
     * 
     * @param component the <code>Component</code> the error occurred in,
     *                  may be <code>null</code>
     *                  indicating the error condition is not directly
     *                  associated with a <code>Component</code>
     * @since 1.4
     */
    public void provideErrorFeedback(Component component) {
        Toolkit toolkit = null;
        if (component != null) {
            toolkit = component.getToolkit();
        } else {
            toolkit = Toolkit.getDefaultToolkit();
        }
        toolkit.beep();
    } // provideErrorFeedback()

    /**
     * Returns the value of the specified system desktop property by
     * invoking <code>Toolkit.getDefaultToolkit().getDesktopProperty()</code>.
     * If the value of the specified property is {@code null},
     * {@code fallbackValue} is returned.
     *
     * <p>
     *  通过调用<code> Toolkit.getDefaultToolkit()。getDesktopProperty()</code>返回指定的系统桌面属性的值。
     * 如果指定的属性的值是{@code null},则返回{@code fallbackValue}。
     * 
     * 
     * @param systemPropertyName the name of the system desktop property being queried
     * @param fallbackValue the object to be returned as the value if the system value is null
     * @return the current value of the desktop property
     *
     * @see java.awt.Toolkit#getDesktopProperty
     *
     * @since 1.4
     */
    public static Object getDesktopPropertyValue(String systemPropertyName, Object fallbackValue) {
        Object value = Toolkit.getDefaultToolkit().getDesktopProperty(systemPropertyName);
        if (value == null) {
            return fallbackValue;
        } else if (value instanceof Color) {
            return new ColorUIResource((Color)value);
        } else if (value instanceof Font) {
            return new FontUIResource((Font)value);
        }
        return value;
    }

    /**
     * Returns an <code>Icon</code> with a disabled appearance.
     * This method is used to generate a disabled <code>Icon</code> when
     * one has not been specified.  For example, if you create a
     * <code>JButton</code> and only specify an <code>Icon</code> via
     * <code>setIcon</code> this method will be called to generate the
     * disabled <code>Icon</code>. If {@code null} is passed as
     * <code>icon</code> this method returns {@code null}.
     * <p>
     * Some look and feels might not render the disabled {@code Icon}, in which
     * case they will ignore this.
     *
     * <p>
     * 返回具有已禁用外观的<code>图标</code>。当没有指定时,此方法用于生成禁用的<code> Icon </code>。
     * 例如,如果您创建一个<code> JButton </code>并且只通过<code> setIcon </code>指定<code> Icon </code>,那么将调用此方法来生成禁用的<code>
     *  Icon < / code>。
     * 返回具有已禁用外观的<code>图标</code>。当没有指定时,此方法用于生成禁用的<code> Icon </code>。
     * 如果{@code null}作为<code>图标</code>传递,此方法返回{@code null}。
     * <p>
     *  某些外观和感觉可能无法呈现已停用的{@code Icon},在这种情况下,他们会忽略此操作。
     * 
     * 
     * @param component {@code JComponent} that will display the {@code Icon},
     *         may be {@code null}
     * @param icon {@code Icon} to generate the disabled icon from
     * @return disabled {@code Icon}, or {@code null} if a suitable
     *         {@code Icon} can not be generated
     * @since 1.5
     */
    public Icon getDisabledIcon(JComponent component, Icon icon) {
        if (icon instanceof ImageIcon) {
            return new ImageIconUIResource(GrayFilter.
                   createDisabledImage(((ImageIcon)icon).getImage()));
        }
        return null;
    }

    /**
     * Returns an <code>Icon</code> for use by disabled
     * components that are also selected. This method is used to generate an
     * <code>Icon</code> for components that are in both the disabled and
     * selected states but do not have a specific <code>Icon</code> for this
     * state.  For example, if you create a <code>JButton</code> and only
     * specify an <code>Icon</code> via <code>setIcon</code> this method
     * will be called to generate the disabled and selected
     * <code>Icon</code>. If {@code null} is passed as <code>icon</code> this
     * methods returns {@code null}.
     * <p>
     * Some look and feels might not render the disabled and selected
     * {@code Icon}, in which case they will ignore this.
     *
     * <p>
     *  返回一个<code> Icon </code>,以供同时被选择的禁用组件使用。
     * 此方法用于为处于禁用和选定状态的组件生成<code> Icon </code>,但对于此状态没有特定的<code> Icon </code>。
     * 例如,如果您创建一个<code> JButton </code>并且只通过<code> setIcon </code>指定<code> Icon </code>,那么将调用此方法生成禁用和选择的<code>
     * 图标</code>。
     * 此方法用于为处于禁用和选定状态的组件生成<code> Icon </code>,但对于此状态没有特定的<code> Icon </code>。
     * 如果{@code null}作为<code>图标</code>传递,此方法返回{@code null}。
     * <p>
     *  某些外观和感觉可能不会呈现禁用和选择的{@code Icon},在这种情况下,他们会忽略此。
     * 
     * 
     * @param component {@code JComponent} that will display the {@code Icon},
     *        may be {@code null}
     * @param icon {@code Icon} to generate disabled and selected icon from
     * @return disabled and selected icon, or {@code null} if a suitable
     *         {@code Icon} can not be generated.
     * @since 1.5
     */
    public Icon getDisabledSelectedIcon(JComponent component, Icon icon) {
        return getDisabledIcon(component, icon);
    }

    /**
     * Return a short string that identifies this look and feel, e.g.
     * "CDE/Motif".  This string should be appropriate for a menu item.
     * Distinct look and feels should have different names, e.g.
     * a subclass of MotifLookAndFeel that changes the way a few components
     * are rendered should be called "CDE/Motif My Way"; something
     * that would be useful to a user trying to select a L&amp;F from a list
     * of names.
     *
     * <p>
     * 返回一个标识此外观的短字符串,例如"CDE / Motif"。此字符串应该适合于菜单项。不同的外观和感觉应该有不同的名称,例如。
     * 改变几个组件渲染方式的MotifLookAndFeel的子类应该被称为"CDE / Motif My Way";这对于试图从名称列表中选择L&amp; F的用户将是有用的。
     * 
     * 
     * @return short identifier for the look and feel
     */
    public abstract String getName();


    /**
     * Return a string that identifies this look and feel.  This string
     * will be used by applications/services that want to recognize
     * well known look and feel implementations.  Presently
     * the well known names are "Motif", "Windows", "Mac", "Metal".  Note
     * that a LookAndFeel derived from a well known superclass
     * that doesn't make any fundamental changes to the look or feel
     * shouldn't override this method.
     *
     * <p>
     *  返回一个标识此外观的字符串。这个字符串将被想要识别众所周知的外观和感觉实现的应用程序/服务使用。目前众所周知的名字是"Motif","Windows","Mac","Metal"。
     * 注意,从不对外观或感觉进行任何根本改变的公知超类派生的LookAndFeel不应该覆盖此方法。
     * 
     * 
     * @return identifier for the look and feel
     */
    public abstract String getID();


    /**
     * Return a one line description of this look and feel implementation,
     * e.g. "The CDE/Motif Look and Feel".   This string is intended for
     * the user, e.g. in the title of a window or in a ToolTip message.
     *
     * <p>
     *  返回此外观实现的一行说明,例如"CDE / Motif外观和感觉"。该字符串用于用户,例如。在窗口的标题或在ToolTip消息中。
     * 
     * 
     * @return short description for the look and feel
     */
    public abstract String getDescription();


    /**
     * Returns {@code true} if the <code>LookAndFeel</code> returned
     * <code>RootPaneUI</code> instances support providing {@code Window}
     * decorations in a <code>JRootPane</code>.
     * <p>
     * The default implementation returns {@code false}, subclasses that
     * support {@code Window} decorations should override this and return
     * {@code true}.
     *
     * <p>
     *  如果<code> LookAndFeel </code>返回<code> RootPaneUI </code>实例支持在<code> JRootPane </code>中提供{@code Window}
     * 装饰,则返回{@code true}。
     * <p>
     *  默认实现返回{@code false},支持{@code Window}装饰的子类应该覆盖此并返回{@code true}。
     * 
     * 
     * @return {@code true} if the {@code RootPaneUI} instances created by
     *         this look and feel support client side decorations
     * @see JDialog#setDefaultLookAndFeelDecorated
     * @see JFrame#setDefaultLookAndFeelDecorated
     * @see JRootPane#setWindowDecorationStyle
     * @since 1.4
     */
    public boolean getSupportsWindowDecorations() {
        return false;
    }

    /**
     * If the underlying platform has a "native" look and feel, and
     * this is an implementation of it, return {@code true}.  For
     * example, when the underlying platform is Solaris running CDE
     * a CDE/Motif look and feel implementation would return {@code
     * true}.
     *
     * <p>
     * 如果底层平台有一个"原生"的外观和感觉,这是它的一个实现,返回{@code true}。例如,当底层平台是Solaris运行CDE时,CDE / Motif外观实现将返回{@code true}。
     * 
     * 
     * @return {@code true} if this look and feel represents the underlying
     *         platform look and feel
     */
    public abstract boolean isNativeLookAndFeel();


    /**
     * Return {@code true} if the underlying platform supports and or permits
     * this look and feel.  This method returns {@code false} if the look
     * and feel depends on special resources or legal agreements that
     * aren't defined for the current platform.
     *
     *
     * <p>
     *  如果底层平台支持和/或允许这种外观,返回{@code true}。如果外观和感觉取决于没有为当前平台定义的特殊资源或法律协议,则此方法返回{@code false}。
     * 
     * 
     * @return {@code true} if this is a supported look and feel
     * @see UIManager#setLookAndFeel
     */
    public abstract boolean isSupportedLookAndFeel();


    /**
     * Initializes the look and feel. While this method is public,
     * it should only be invoked by the {@code UIManager} when a
     * look and feel is installed as the current look and feel. This
     * method is invoked before the {@code UIManager} invokes
     * {@code getDefaults}. This method is intended to perform any
     * initialization for the look and feel. Subclasses
     * should do any one-time setup they need here, rather than
     * in a static initializer, because look and feel class objects
     * may be loaded just to discover that {@code isSupportedLookAndFeel()}
     * returns {@code false}.
     *
     * <p>
     *  初始化外观和感觉。虽然这个方法是公开的,但是只有在将外观和感觉安装为当前外观时,才能由{@code UIManager}调用。
     * 此方法在{@code UIManager}调用{@code getDefaults}之前被调用。此方法旨在对外观和感觉执行任何初始化。
     * 子类应该在这里进行任何一次性设置,而不是在静态初始化器中,因为可以加载外观和感觉类对象来发现{@code isSupportedLookAndFeel()}返回{@code false}。
     * 
     * 
     * @see #uninitialize
     * @see UIManager#setLookAndFeel
     */
    public void initialize() {
    }


    /**
     * Uninitializes the look and feel. While this method is public,
     * it should only be invoked by the {@code UIManager} when
     * the look and feel is uninstalled. For example,
     * {@code UIManager.setLookAndFeel} invokes this when the look and
     * feel is changed.
     * <p>
     * Subclasses may choose to free up some resources here.
     *
     * <p>
     *  未初始化的外观和感觉。虽然这个方法是公开的,但只有在卸载外观时,才能由{@code UIManager}调用。
     * 例如,{@code UIManager.setLookAndFeel}在外观和感觉改变时调用它。
     * <p>
     *  子类可以选择释放一些资源。
     * 
     * 
     * @see #initialize
     * @see UIManager#setLookAndFeel
     */
    public void uninitialize() {
    }

    /**
     * Returns the look and feel defaults. While this method is public,
     * it should only be invoked by the {@code UIManager} when the
     * look and feel is set as the current look and feel and after
     * {@code initialize} has been invoked.
     *
     * <p>
     * 返回外观和感觉默认值。虽然此方法是公开的,但只有当外观和感觉设置为当前外观和调用{@code initialize}后,才应由{@code UIManager}调用。
     * 
     * 
     * @return the look and feel defaults
     * @see #initialize
     * @see #uninitialize
     * @see UIManager#setLookAndFeel
     */
    public UIDefaults getDefaults() {
        return null;
    }

    /**
     * Returns a string that displays and identifies this
     * object's properties.
     *
     * <p>
     *  返回显示和标识此对象属性的字符串。
     * 
     * @return a String representation of this object
     */
    public String toString() {
        return "[" + getDescription() + " - " + getClass().getName() + "]";
    }
}
