/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2009, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.event.KeyEvent;

/**
 * The <code>MenuShortcut</code>class represents a keyboard accelerator
 * for a MenuItem.
 * <p>
 * Menu shortcuts are created using virtual keycodes, not characters.
 * For example, a menu shortcut for Ctrl-a (assuming that Control is
 * the accelerator key) would be created with code like the following:
 * <p>
 * <code>MenuShortcut ms = new MenuShortcut(KeyEvent.VK_A, false);</code>
 * <p> or alternatively
 * <p>
 * <code>MenuShortcut ms = new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('A'), false);</code>
 * <p>
 * Menu shortcuts may also be constructed for a wider set of keycodes
 * using the <code>java.awt.event.KeyEvent.getExtendedKeyCodeForChar</code> call.
 * For example, a menu shortcut for "Ctrl+cyrillic ef" is created by
 * <p>
 * <code>MenuShortcut ms = new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('\u0444'), false);</code>
 * <p>
 * Note that shortcuts created with a keycode or an extended keycode defined as a constant in <code>KeyEvent</code>
 * work regardless of the current keyboard layout. However, a shortcut made of
 * an extended keycode not listed in <code>KeyEvent</code>
 * only work if the current keyboard layout produces a corresponding letter.
 * <p>
 * The accelerator key is platform-dependent and may be obtained
 * via {@link Toolkit#getMenuShortcutKeyMask}.
 *
 * <p>
 *  <code> MenuShortcut </code>类表示MenuItem的键盘加速器。
 * <p>
 *  菜单快捷方式是使用虚拟键盘而不是字符创建的。例如,Ctrl-a(假设Control是加速键)的菜单快捷方式将使用如下代码创建：
 * <p>
 *  <code> MenuShortcut ms = new MenuShortcut(KeyEvent.VK_A,false); </code> <p>或者
 * <p>
 *  <code> MenuShortcut ms = new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('A'),false); </code>
 * <p>
 *  也可以使用<code> java.awt.event.KeyEvent.getExtendedKeyCodeForChar </code>调用为更宽的一组键代码构建菜单快捷方式。
 * 例如,创建"Ctrl + cyrillic ef"的菜单快捷方式。
 * <p>
 *  <code> MenuShortcut ms = new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('\ u0444'),false); </code>
 * 。
 * <p>
 *  请注意,在<code> KeyEvent </code>中定义为常量的键码或扩展键码创建的快捷键无论当前的键盘布局如何工作。
 * 但是,由<code> KeyEvent </code>中未列出的扩展键代码生成的快捷方式仅在当前键盘布局生成相应字母时有效。
 * <p>
 *  加速键是平台相关的,可以通过{@link Toolkit#getMenuShortcutKeyMask}获得。
 * 
 * 
 * @author Thomas Ball
 * @since JDK1.1
 */
public class MenuShortcut implements java.io.Serializable
{
    /**
     * The virtual keycode for the menu shortcut.
     * This is the keycode with which the menu shortcut will be created.
     * Note that it is a virtual keycode, not a character,
     * e.g. KeyEvent.VK_A, not 'a'.
     * Note: in 1.1.x you must use setActionCommand() on a menu item
     * in order for its shortcut to work, otherwise it will fire a null
     * action command.
     *
     * <p>
     * 菜单快捷方式的虚拟键码。这是用于创建菜单快捷方式的键码。注意,它是一个虚拟键码,而不是一个字符,例如。 KeyEvent.VK_A,而不是'a'。
     * 注意：在1.1.x中,必须在菜单项上使用setActionCommand(),以使其快捷方式工作,否则它将触发空操作命令。
     * 
     * 
     * @serial
     * @see #getKey()
     * @see #usesShiftModifier()
     * @see java.awt.event.KeyEvent
     * @since JDK1.1
     */
    int key;

    /**
     * Indicates whether the shft key was pressed.
     * If true, the shift key was pressed.
     * If false, the shift key was not pressed
     *
     * <p>
     *  指示是否按下shft键。如果为true,则按下shift键。如果为false,则未按下shift键
     * 
     * 
     * @serial
     * @see #usesShiftModifier()
     * @since JDK1.1
     */
    boolean usesShift;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = 143448358473180225L;

    /**
     * Constructs a new MenuShortcut for the specified virtual keycode.
     * <p>
     *  为指定的虚拟键码构造新的MenuShortcut。
     * 
     * 
     * @param key the raw keycode for this MenuShortcut, as would be returned
     * in the keyCode field of a {@link java.awt.event.KeyEvent KeyEvent} if
     * this key were pressed.
     * @see java.awt.event.KeyEvent
     **/
    public MenuShortcut(int key) {
        this(key, false);
    }

    /**
     * Constructs a new MenuShortcut for the specified virtual keycode.
     * <p>
     *  为指定的虚拟键码构造新的MenuShortcut。
     * 
     * 
     * @param key the raw keycode for this MenuShortcut, as would be returned
     * in the keyCode field of a {@link java.awt.event.KeyEvent KeyEvent} if
     * this key were pressed.
     * @param useShiftModifier indicates whether this MenuShortcut is invoked
     * with the SHIFT key down.
     * @see java.awt.event.KeyEvent
     **/
    public MenuShortcut(int key, boolean useShiftModifier) {
        this.key = key;
        this.usesShift = useShiftModifier;
    }

    /**
     * Returns the raw keycode of this MenuShortcut.
     * <p>
     *  返回此MenuShortcut的原始键码。
     * 
     * 
     * @return the raw keycode of this MenuShortcut.
     * @see java.awt.event.KeyEvent
     * @since JDK1.1
     */
    public int getKey() {
        return key;
    }

    /**
     * Returns whether this MenuShortcut must be invoked using the SHIFT key.
     * <p>
     *  返回此MenuShortcut是否必须使用SHIFT键调用。
     * 
     * 
     * @return <code>true</code> if this MenuShortcut must be invoked using the
     * SHIFT key, <code>false</code> otherwise.
     * @since JDK1.1
     */
    public boolean usesShiftModifier() {
        return usesShift;
    }

    /**
     * Returns whether this MenuShortcut is the same as another:
     * equality is defined to mean that both MenuShortcuts use the same key
     * and both either use or don't use the SHIFT key.
     * <p>
     *  返回此MenuShortcut是否与另一个相同：等于定义为表示两个MenuShortcuts使用相同的键,并且都使用或不使用SHIFT键。
     * 
     * 
     * @param s the MenuShortcut to compare with this.
     * @return <code>true</code> if this MenuShortcut is the same as another,
     * <code>false</code> otherwise.
     * @since JDK1.1
     */
    public boolean equals(MenuShortcut s) {
        return (s != null && (s.getKey() == key) &&
                (s.usesShiftModifier() == usesShift));
    }

    /**
     * Returns whether this MenuShortcut is the same as another:
     * equality is defined to mean that both MenuShortcuts use the same key
     * and both either use or don't use the SHIFT key.
     * <p>
     *  返回此MenuShortcut是否与另一个相同：等于定义为表示两个MenuShortcuts使用相同的键,并且都使用或不使用SHIFT键。
     * 
     * 
     * @param obj the Object to compare with this.
     * @return <code>true</code> if this MenuShortcut is the same as another,
     * <code>false</code> otherwise.
     * @since 1.2
     */
    public boolean equals(Object obj) {
        if (obj instanceof MenuShortcut) {
            return equals( (MenuShortcut) obj );
        }
        return false;
    }

    /**
     * Returns the hashcode for this MenuShortcut.
     * <p>
     *  返回此MenuShortcut的哈希码。
     * 
     * 
     * @return the hashcode for this MenuShortcut.
     * @since 1.2
     */
    public int hashCode() {
        return (usesShift) ? (~key) : key;
    }

    /**
     * Returns an internationalized description of the MenuShortcut.
     * <p>
     *  返回MenuShortcut的国际化描述。
     * 
     * 
     * @return a string representation of this MenuShortcut.
     * @since JDK1.1
     */
    public String toString() {
        int modifiers = 0;
        if (!GraphicsEnvironment.isHeadless()) {
            modifiers = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        }
        if (usesShiftModifier()) {
            modifiers |= Event.SHIFT_MASK;
        }
        return KeyEvent.getKeyModifiersText(modifiers) + "+" +
               KeyEvent.getKeyText(key);
    }

    /**
     * Returns the parameter string representing the state of this
     * MenuShortcut. This string is useful for debugging.
     * <p>
     *  返回表示此MenuShortcut的状态的参数字符串。这个字符串对于调试很有用。
     * 
     * @return    the parameter string of this MenuShortcut.
     * @since JDK1.1
     */
    protected String paramString() {
        String str = "key=" + key;
        if (usesShiftModifier()) {
            str += ",usesShiftModifier";
        }
        return str;
    }
}
