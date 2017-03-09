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

import java.awt.AWTKeyStroke;
import java.awt.event.KeyEvent;

/**
 * A KeyStroke represents a key action on the keyboard, or equivalent input
 * device. KeyStrokes can correspond to only a press or release of a particular
 * key, just as KEY_PRESSED and KEY_RELEASED KeyEvents do; alternately, they
 * can correspond to typing a specific Java character, just as KEY_TYPED
 * KeyEvents do. In all cases, KeyStrokes can specify modifiers (alt, shift,
 * control, meta, altGraph, or a combination thereof) which must be present during the
 * action for an exact match.
 * <p>
 * KeyStrokes are used to define high-level (semantic) action events. Instead
 * of trapping every keystroke and throwing away the ones you are not
 * interested in, those keystrokes you care about automatically initiate
 * actions on the Components with which they are registered.
 * <p>
 * KeyStrokes are immutable, and are intended to be unique. Client code cannot
 * create a KeyStroke; a variant of <code>getKeyStroke</code> must be used
 * instead. These factory methods allow the KeyStroke implementation to cache
 * and share instances efficiently.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  KeyStroke表示键盘或等效输入设备上的键操作。
 *  KeyStrokes只能对应一个特定键的按下或释放,就像KEY_PRESSED和KEY_RELEASED KeyEvents一样;或者,它们可以对应于键入特定的Java字符,正如KEY_TYPED K
 * eyEvents所做的那样。
 *  KeyStroke表示键盘或等效输入设备上的键操作。
 * 在所有情况下,KeyStrokes可以指定在动作期间必须出现以用于精确匹配的修饰符(alt,shift,control,meta,altGraph或其组合)。
 * <p>
 *  KeyStrokes用于定义高级(语义)动作事件。而不是捕获每个击键,并抛弃那些你不感兴趣,你关心的那些击键自动启动他们注册的组件的操作。
 * <p>
 *  KeyStrokes是不可变的,并且旨在是唯一的。客户端代码无法创建KeyStroke;必须使用<code> getKeyStroke </code>的变体。
 * 这些工厂方法允许KeyStroke实现高效地缓存和共享实例。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see javax.swing.text.Keymap
 * @see #getKeyStroke
 *
 * @author Arnaud Weber
 * @author David Mendenhall
 */
public class KeyStroke extends AWTKeyStroke {

    /**
     * Serial Version ID.
     * <p>
     *  串行版本ID。
     * 
     */
    private static final long serialVersionUID = -9060180771037902530L;

    private KeyStroke() {
    }
    private KeyStroke(char keyChar, int keyCode, int modifiers,
                      boolean onKeyRelease) {
        super(keyChar, keyCode, modifiers, onKeyRelease);
    }

    /**
     * Returns a shared instance of a <code>KeyStroke</code>
     * that represents a <code>KEY_TYPED</code> event for the
     * specified character.
     *
     * <p>
     *  返回代表指定字符的<code> KEY_TYPED </code>事件的<code> KeyStroke </code>的共享实例。
     * 
     * 
     * @param keyChar the character value for a keyboard key
     * @return a KeyStroke object for that key
     */
    public static KeyStroke getKeyStroke(char keyChar) {
        synchronized (AWTKeyStroke.class) {
            registerSubclass(KeyStroke.class);
            return (KeyStroke)getAWTKeyStroke(keyChar);
        }
    }

    /**
     * Returns an instance of a KeyStroke, specifying whether the key is
     * considered to be activated when it is pressed or released. Unlike all
     * other factory methods in this class, the instances returned by this
     * method are not necessarily cached or shared.
     *
     * <p>
     *  返回一个KeyStroke的实例,指定在按下或释放时该键是否被激活。与此类中的所有其他工厂方法不同,此方法返回的实例不一定被缓存或共享。
     * 
     * 
     * @param keyChar the character value for a keyboard key
     * @param onKeyRelease <code>true</code> if this KeyStroke corresponds to a
     *        key release; <code>false</code> otherwise.
     * @return a KeyStroke object for that key
     * @deprecated use getKeyStroke(char)
     */
    @Deprecated
    public static KeyStroke getKeyStroke(char keyChar, boolean onKeyRelease) {
        return new KeyStroke(keyChar, KeyEvent.VK_UNDEFINED, 0, onKeyRelease);
    }

    /**
     * Returns a shared instance of a {@code KeyStroke}
     * that represents a {@code KEY_TYPED} event for the
     * specified Character object and a
      * set of modifiers. Note that the first parameter is of type Character
     * rather than char. This is to avoid inadvertent clashes with calls to
     * <code>getKeyStroke(int keyCode, int modifiers)</code>.
     *
     * The modifiers consist of any combination of following:<ul>
     * <li>java.awt.event.InputEvent.SHIFT_DOWN_MASK
     * <li>java.awt.event.InputEvent.CTRL_DOWN_MASK
     * <li>java.awt.event.InputEvent.META_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK
     * </ul>
     * The old modifiers listed below also can be used, but they are
     * mapped to _DOWN_ modifiers. <ul>
     * <li>java.awt.event.InputEvent.SHIFT_MASK
     * <li>java.awt.event.InputEvent.CTRL_MASK
     * <li>java.awt.event.InputEvent.META_MASK
     * <li>java.awt.event.InputEvent.ALT_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_MASK
     * </ul>
     * also can be used, but they are mapped to _DOWN_ modifiers.
     *
     * Since these numbers are all different powers of two, any combination of
     * them is an integer in which each bit represents a different modifier
     * key. Use 0 to specify no modifiers.
     *
     * <p>
     *  返回{@code KeyStroke}的共享实例,该实例表示指定Character对象和一组修饰符的{@code KEY_TYPED}事件。注意,第一个参数是Character类型,而不是char。
     * 这是为了避免无意中与调用<code> getKeyStroke(int keyCode,int modifiers)</code>发生冲突。
     * 
     *  修饰符由以下内容的任意组合组成：<ul> <li> java.awt.event.InputEvent.SHIFT_DOWN_MASK <li> java.awt.event.InputEvent.C
     * TRL_DOWN_MASK <li> java.awt.event.InputEvent.META_DOWN_MASK <li > java.awt.event.InputEvent.ALT_DOWN_
     * MASK <li> java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK。
     * </ul>
     * 下面列出的旧修饰符也可以使用,但它们映射到_DOWN_修饰符。
     *  <ul> <li> java.awt.event.InputEvent.SHIFT_MASK <li> java.awt.event.InputEvent.CTRL_MASK <li> java.aw
     * t.event.InputEvent.META_MASK <li> java.awt.event.InputEvent。
     * 下面列出的旧修饰符也可以使用,但它们映射到_DOWN_修饰符。 ALT_MASK <li> java.awt.event.InputEvent.ALT_GRAPH_MASK。
     * </ul>
     *  也可以使用,但是它们被映射到_DOWN_修饰符。
     * 
     *  由于这些数字都是2的不同次幂,它们的任何组合是一个整数,其中每个位表示不同的修饰键。使用0指定无修饰符。
     * 
     * 
     * @param keyChar the Character object for a keyboard character
     * @param modifiers a bitwise-ored combination of any modifiers
     * @return an KeyStroke object for that key
     * @throws IllegalArgumentException if keyChar is null
     *
     * @see java.awt.event.InputEvent
     * @since 1.3
     */
    public static KeyStroke getKeyStroke(Character keyChar, int modifiers) {
        synchronized (AWTKeyStroke.class) {
            registerSubclass(KeyStroke.class);
            return (KeyStroke)getAWTKeyStroke(keyChar, modifiers);
        }
    }

    /**
     * Returns a shared instance of a KeyStroke, given a numeric key code and a
     * set of modifiers, specifying whether the key is activated when it is
     * pressed or released.
     * <p>
     * The "virtual key" constants defined in java.awt.event.KeyEvent can be
     * used to specify the key code. For example:<ul>
     * <li>java.awt.event.KeyEvent.VK_ENTER
     * <li>java.awt.event.KeyEvent.VK_TAB
     * <li>java.awt.event.KeyEvent.VK_SPACE
     * </ul>
     * Alternatively, the key code may be obtained by calling
     * <code>java.awt.event.KeyEvent.getExtendedKeyCodeForChar</code>.
     *
     * The modifiers consist of any combination of:<ul>
     * <li>java.awt.event.InputEvent.SHIFT_DOWN_MASK
     * <li>java.awt.event.InputEvent.CTRL_DOWN_MASK
     * <li>java.awt.event.InputEvent.META_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK
     * </ul>
     * The old modifiers <ul>
     * <li>java.awt.event.InputEvent.SHIFT_MASK
     * <li>java.awt.event.InputEvent.CTRL_MASK
     * <li>java.awt.event.InputEvent.META_MASK
     * <li>java.awt.event.InputEvent.ALT_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_MASK
     * </ul>
     * also can be used, but they are mapped to _DOWN_ modifiers.
     *
     * Since these numbers are all different powers of two, any combination of
     * them is an integer in which each bit represents a different modifier
     * key. Use 0 to specify no modifiers.
     *
     * <p>
     *  返回一个KeyStroke的共享实例,给定一个数字键代码和一组修饰符,指定当键被按下或释放时键是否被激活。
     * <p>
     *  java.awt.event.KeyEvent中定义的"虚拟键"常量可用于指定键代码。
     * 例如：<ul> <li> java.awt.event.KeyEvent.VK_ENTER <li> java.awt.event.KeyEvent.VK_TAB <li> java.awt.event
     * .KeyEvent.VK_SPACE。
     *  java.awt.event.KeyEvent中定义的"虚拟键"常量可用于指定键代码。
     * </ul>
     *  或者,可以通过调用<code> java.awt.event.KeyEvent.getExtendedKeyCodeForChar </code>获得键代码。
     * 
     *  修饰符由以下各项的任意组合组成：<ul> <li> java.awt.event.InputEvent.SHIFT_DOWN_MASK <li> java.awt.event.InputEvent.C
     * TRL_DOWN_MASK <li> java.awt.event.InputEvent.META_DOWN_MASK <li> java.awt.event.InputEvent.ALT_DOWN_M
     * ASK <li> java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK。
     * </ul>
     * 旧的修饰符<ul> <li> java.awt.event.InputEvent.SHIFT_MASK <li> java.awt.event.InputEvent.CTRL_MASK <li> jav
     * a.awt.event.InputEvent.META_MASK <li> java.awt.event .InputEvent.ALT_MASK <li> java.awt.event.InputEv
     * ent.ALT_GRAPH_MASK。
     * </ul>
     *  也可以使用,但是它们被映射到_DOWN_修饰符。
     * 
     *  由于这些数字都是2的不同次幂,它们的任何组合是一个整数,其中每个位表示不同的修饰键。使用0指定无修饰符。
     * 
     * 
     * @param keyCode an int specifying the numeric code for a keyboard key
     * @param modifiers a bitwise-ored combination of any modifiers
     * @param onKeyRelease <code>true</code> if the KeyStroke should represent
     *        a key release; <code>false</code> otherwise.
     * @return a KeyStroke object for that key
     *
     * @see java.awt.event.KeyEvent
     * @see java.awt.event.InputEvent
     */
    public static KeyStroke getKeyStroke(int keyCode, int modifiers,
                                         boolean onKeyRelease) {
        synchronized (AWTKeyStroke.class) {
            registerSubclass(KeyStroke.class);
            return (KeyStroke)getAWTKeyStroke(keyCode, modifiers,
                                              onKeyRelease);
        }
    }

    /**
     * Returns a shared instance of a KeyStroke, given a numeric key code and a
     * set of modifiers. The returned KeyStroke will correspond to a key press.
     * <p>
     * The "virtual key" constants defined in java.awt.event.KeyEvent can be
     * used to specify the key code. For example:<ul>
     * <li>java.awt.event.KeyEvent.VK_ENTER
     * <li>java.awt.event.KeyEvent.VK_TAB
     * <li>java.awt.event.KeyEvent.VK_SPACE
     * </ul>
     * Alternatively, the key code may be obtained by calling
     * <code>java.awt.event.KeyEvent.getExtendedKeyCodeForChar</code>.
     *
     * The modifiers consist of any combination of:<ul>
     * <li>java.awt.event.InputEvent.SHIFT_DOWN_MASK
     * <li>java.awt.event.InputEvent.CTRL_DOWN_MASK
     * <li>java.awt.event.InputEvent.META_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_DOWN_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK
     * </ul>
     * The old modifiers <ul>
     * <li>java.awt.event.InputEvent.SHIFT_MASK
     * <li>java.awt.event.InputEvent.CTRL_MASK
     * <li>java.awt.event.InputEvent.META_MASK
     * <li>java.awt.event.InputEvent.ALT_MASK
     * <li>java.awt.event.InputEvent.ALT_GRAPH_MASK
     * </ul>
     * also can be used, but they are mapped to _DOWN_ modifiers.
     *
     * Since these numbers are all different powers of two, any combination of
     * them is an integer in which each bit represents a different modifier
     * key. Use 0 to specify no modifiers.
     *
     * <p>
     *  返回一个KeyStroke的共享实例,给定一个数字键代码和一组修饰符。返回的KeyStroke将对应于按键。
     * <p>
     *  java.awt.event.KeyEvent中定义的"虚拟键"常量可用于指定键代码。
     * 例如：<ul> <li> java.awt.event.KeyEvent.VK_ENTER <li> java.awt.event.KeyEvent.VK_TAB <li> java.awt.event
     * .KeyEvent.VK_SPACE。
     *  java.awt.event.KeyEvent中定义的"虚拟键"常量可用于指定键代码。
     * </ul>
     *  或者,可以通过调用<code> java.awt.event.KeyEvent.getExtendedKeyCodeForChar </code>获得键代码。
     * 
     *  修饰符由以下各项的任意组合组成：<ul> <li> java.awt.event.InputEvent.SHIFT_DOWN_MASK <li> java.awt.event.InputEvent.C
     * TRL_DOWN_MASK <li> java.awt.event.InputEvent.META_DOWN_MASK <li> java.awt.event.InputEvent.ALT_DOWN_M
     * ASK <li> java.awt.event.InputEvent.ALT_GRAPH_DOWN_MASK。
     * </ul>
     *  旧的修饰符<ul> <li> java.awt.event.InputEvent.SHIFT_MASK <li> java.awt.event.InputEvent.CTRL_MASK <li> ja
     * va.awt.event.InputEvent.META_MASK <li> java.awt.event .InputEvent.ALT_MASK <li> java.awt.event.InputE
     * vent.ALT_GRAPH_MASK。
     * </ul>
     * 也可以使用,但是它们被映射到_DOWN_修饰符。
     * 
     *  由于这些数字都是2的不同次幂,它们的任何组合是一个整数,其中每个位表示不同的修饰键。使用0指定无修饰符。
     * 
     * 
     * @param keyCode an int specifying the numeric code for a keyboard key
     * @param modifiers a bitwise-ored combination of any modifiers
     * @return a KeyStroke object for that key
     *
     * @see java.awt.event.KeyEvent
     * @see java.awt.event.InputEvent
     */
    public static KeyStroke getKeyStroke(int keyCode, int modifiers) {
        synchronized (AWTKeyStroke.class) {
            registerSubclass(KeyStroke.class);
            return (KeyStroke)getAWTKeyStroke(keyCode, modifiers);
        }
    }

    /**
     * Returns a KeyStroke which represents the stroke which generated a given
     * KeyEvent.
     * <p>
     * This method obtains the keyChar from a KeyTyped event, and the keyCode
     * from a KeyPressed or KeyReleased event. The KeyEvent modifiers are
     * obtained for all three types of KeyEvent.
     *
     * <p>
     *  返回一个KeyStroke,它表示生成给定KeyEvent的笔划。
     * <p>
     *  此方法从KeyTyped事件获取keyChar,并从KeyPressed或KeyReleased事件获取keyCode。为所有三种类型的KeyEvent获取KeyEvent修饰符。
     * 
     * 
     * @param anEvent the KeyEvent from which to obtain the KeyStroke
     * @throws NullPointerException if <code>anEvent</code> is null
     * @return the KeyStroke that precipitated the event
     */
    public static KeyStroke getKeyStrokeForEvent(KeyEvent anEvent) {
        synchronized (AWTKeyStroke.class) {
            registerSubclass(KeyStroke.class);
            return (KeyStroke)getAWTKeyStrokeForEvent(anEvent);
        }
    }

    /**
     * Parses a string and returns a <code>KeyStroke</code>.
     * The string must have the following syntax:
     * <pre>
     *    &lt;modifiers&gt;* (&lt;typedID&gt; | &lt;pressedReleasedID&gt;)
     *
     *    modifiers := shift | control | ctrl | meta | alt | altGraph
     *    typedID := typed &lt;typedKey&gt;
     *    typedKey := string of length 1 giving Unicode character.
     *    pressedReleasedID := (pressed | released) key
     *    key := KeyEvent key code name, i.e. the name following "VK_".
     * </pre>
     * If typed, pressed or released is not specified, pressed is assumed. Here
     * are some examples:
     * <pre>
     *     "INSERT" =&gt; getKeyStroke(KeyEvent.VK_INSERT, 0);
     *     "control DELETE" =&gt; getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_MASK);
     *     "alt shift X" =&gt; getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK);
     *     "alt shift released X" =&gt; getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_MASK | InputEvent.SHIFT_MASK, true);
     *     "typed a" =&gt; getKeyStroke('a');
     * </pre>
     *
     * In order to maintain backward-compatibility, specifying a null String,
     * or a String which is formatted incorrectly, returns null.
     *
     * <p>
     *  解析一个字符串并返回一个<code> KeyStroke </code>。字符串必须具有以下语法：
     * <pre>
     *  &lt; modifiers&gt; *(&lt; typedID&gt; |&lt; pressedReleasedID&gt;)
     * 
     *  修饰符：= shift |控制| ctrl |元| alt | altGraph typedID：= typed&lt; typedKey&gt; typedKey：=长度为1的字符串,给出Unico
     * de字符。
     *  pressReleasedID：=(按下|释放)键键：= KeyEvent键代码名称,即"VK_"后面的名称。
     * </pre>
     *  如果没有指定打字,按下或释放,则按下。这里有些例子：
     * 
     * @param s a String formatted as described above
     * @return a KeyStroke object for that String, or null if the specified
     *         String is null, or is formatted incorrectly
     *
     * @see java.awt.event.KeyEvent
     */
    public static KeyStroke getKeyStroke(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        synchronized (AWTKeyStroke.class) {
            registerSubclass(KeyStroke.class);
            try {
                return (KeyStroke)getAWTKeyStroke(s);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }
}
