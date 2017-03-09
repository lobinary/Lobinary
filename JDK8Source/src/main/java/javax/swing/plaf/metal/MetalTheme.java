/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.metal;

import javax.swing.plaf.*;
import javax.swing.*;

/**
 * {@code MetalTheme} provides the color palette and fonts used by
 * the Java Look and Feel.
 * <p>
 * {@code MetalTheme} is abstract, see {@code DefaultMetalTheme} and
 * {@code OceanTheme} for concrete implementations.
 * <p>
 * {@code MetalLookAndFeel} maintains the current theme that the
 * the {@code ComponentUI} implementations for metal use. Refer to
 * {@link MetalLookAndFeel#setCurrentTheme
 * MetalLookAndFeel.setCurrentTheme(MetalTheme)} for details on changing
 * the current theme.
 * <p>
 * {@code MetalTheme} provides a number of public methods for getting
 * colors. These methods are implemented in terms of a
 * handful of protected abstract methods. A subclass need only override
 * the protected abstract methods ({@code getPrimary1},
 * {@code getPrimary2}, {@code getPrimary3}, {@code getSecondary1},
 * {@code getSecondary2}, and {@code getSecondary3}); although a subclass
 * may override the other public methods for more control over the set of
 * colors that are used.
 * <p>
 * Concrete implementations of {@code MetalTheme} must return {@code non-null}
 * values from all methods. While the behavior of returning {@code null} is
 * not specified, returning {@code null} will result in incorrect behavior.
 * <p>
 * It is strongly recommended that subclasses return completely opaque colors.
 * To do otherwise may result in rendering problems, such as visual garbage.
 *
 * <p>
 *  {@code MetalTheme}提供Java Look and Feel使用的调色板和字体。
 * <p>
 *  {@code MetalTheme}是抽象的,具体实现见{@code DefaultMetalTheme}和{@code OceanTheme}。
 * <p>
 *  {@code MetalLookAndFeel}维护了{@code ComponentUI}金属实现的当前主题。
 * 有关更改当前主题的详细信息,请参阅{@link MetalLookAndFeel#setCurrentTheme MetalLookAndFeel.setCurrentTheme(MetalTheme)}
 * 。
 *  {@code MetalLookAndFeel}维护了{@code ComponentUI}金属实现的当前主题。
 * <p>
 *  {@code MetalTheme}提供了许多公共方法来获取颜色。这些方法是根据一些受保护的抽象方法来实现的。
 * 子类只需要覆盖受保护的抽象方法({@code getPrimary1},{@code getPrimary2},{@code getPrimary3},{@code getSecondary1},{@code getSecondary2}
 * 和{@code getSecondary3})。
 *  {@code MetalTheme}提供了许多公共方法来获取颜色。这些方法是根据一些受保护的抽象方法来实现的。尽管子类可以覆盖其他公共方法以更好地控制所使用的颜色集合。
 * <p>
 *  {@code MetalTheme}的具体实现必须从所有方法返回{@code non-null}值。虽然未指定返回{@code null}的行为,但返回{@code null}将导致不正确的行为。
 * <p>
 *  强烈建议子类返回完全不透明的颜色。否则可能导致渲染问题,如可视化垃圾。
 * 
 * 
 * @see DefaultMetalTheme
 * @see OceanTheme
 * @see MetalLookAndFeel#setCurrentTheme
 *
 * @author Steve Wilson
 */
public abstract class MetalTheme {

    // Contants identifying the various Fonts that are Theme can support
    static final int CONTROL_TEXT_FONT = 0;
    static final int SYSTEM_TEXT_FONT = 1;
    static final int USER_TEXT_FONT = 2;
    static final int MENU_TEXT_FONT = 3;
    static final int WINDOW_TITLE_FONT = 4;
    static final int SUB_TEXT_FONT = 5;

    static ColorUIResource white = new ColorUIResource( 255, 255, 255 );
    private static ColorUIResource black = new ColorUIResource( 0, 0, 0 );

    /**
     * Returns the name of this theme.
     *
     * <p>
     *  返回此主题的名称。
     * 
     * 
     * @return the name of this theme
     */
    public abstract String getName();

    /**
     * Returns the primary 1 color.
     *
     * <p>
     * 返回主1颜色。
     * 
     * 
     * @return the primary 1 color
     */
    protected abstract ColorUIResource getPrimary1();  // these are blue in Metal Default Theme

    /**
     * Returns the primary 2 color.
     *
     * <p>
     *  返回主2颜色。
     * 
     * 
     * @return the primary 2 color
     */
    protected abstract ColorUIResource getPrimary2();

    /**
     * Returns the primary 3 color.
     *
     * <p>
     *  返回主3颜色。
     * 
     * 
     * @return the primary 3 color
     */
    protected abstract ColorUIResource getPrimary3();

    /**
     * Returns the secondary 1 color.
     *
     * <p>
     *  返回辅助1颜色。
     * 
     * 
     * @return the secondary 1 color
     */
    protected abstract ColorUIResource getSecondary1();  // these are gray in Metal Default Theme

    /**
     * Returns the secondary 2 color.
     *
     * <p>
     *  返回辅助2颜色。
     * 
     * 
     * @return the secondary 2 color
     */
    protected abstract ColorUIResource getSecondary2();

    /**
     * Returns the secondary 3 color.
     *
     * <p>
     *  返回辅助3颜色。
     * 
     * 
     * @return the secondary 3 color
     */
    protected abstract ColorUIResource getSecondary3();

    /**
     * Returns the control text font.
     *
     * <p>
     *  返回控件文本字体。
     * 
     * 
     * @return the control text font
     */
    public abstract FontUIResource getControlTextFont();

    /**
     * Returns the system text font.
     *
     * <p>
     *  返回系统文本字体。
     * 
     * 
     * @return the system text font
     */
    public abstract FontUIResource getSystemTextFont();

    /**
     * Returns the user text font.
     *
     * <p>
     *  返回用户文本字体。
     * 
     * 
     * @return the user text font
     */
    public abstract FontUIResource getUserTextFont();

    /**
     * Returns the menu text font.
     *
     * <p>
     *  返回菜单文本字体。
     * 
     * 
     * @return the menu text font
     */
    public abstract FontUIResource getMenuTextFont();

    /**
     * Returns the window title font.
     *
     * <p>
     *  返回窗口标题字体。
     * 
     * 
     * @return the window title font
     */
    public abstract FontUIResource getWindowTitleFont();

    /**
     * Returns the sub-text font.
     *
     * <p>
     *  返回子文本字体。
     * 
     * 
     * @return the sub-text font
     */
    public abstract FontUIResource getSubTextFont();

    /**
     * Returns the white color. This returns opaque white
     * ({@code 0xFFFFFFFF}).
     *
     * <p>
     *  返回白色。这返回不透明白色({@code 0xFFFFFFFF})。
     * 
     * 
     * @return the white color
     */
    protected ColorUIResource getWhite() { return white; }

    /**
     * Returns the black color. This returns opaque black
     * ({@code 0xFF000000}).
     *
     * <p>
     *  返回黑色。这返回不透明的黑色({@code 0xFF000000})。
     * 
     * 
     * @return the black color
     */
    protected ColorUIResource getBlack() { return black; }

    /**
     * Returns the focus color. This returns the value of
     * {@code getPrimary2()}.
     *
     * <p>
     *  返回焦点颜色。这将返回{@code getPrimary2()}的值。
     * 
     * 
     * @return the focus color
     */
    public ColorUIResource getFocusColor() { return getPrimary2(); }

    /**
     * Returns the desktop color. This returns the value of
     * {@code getPrimary2()}.
     *
     * <p>
     *  返回桌面颜色。这将返回{@code getPrimary2()}的值。
     * 
     * 
     * @return the desktop color
     */
    public  ColorUIResource getDesktopColor() { return getPrimary2(); }

    /**
     * Returns the control color. This returns the value of
     * {@code getSecondary3()}.
     *
     * <p>
     *  返回控件颜色。这将返回{@code getSecondary3()}的值。
     * 
     * 
     * @return the control color
     */
    public ColorUIResource getControl() { return getSecondary3(); }

    /**
     * Returns the control shadow color. This returns
     * the value of {@code getSecondary2()}.
     *
     * <p>
     *  返回控件阴影颜色。这返回{@code getSecondary2()}的值。
     * 
     * 
     * @return the control shadow color
     */
    public ColorUIResource getControlShadow() { return getSecondary2(); }

    /**
     * Returns the control dark shadow color. This returns
     * the value of {@code getSecondary1()}.
     *
     * <p>
     *  返回控件的阴影颜色。这将返回{@code getSecondary1()}的值。
     * 
     * 
     * @return the control dark shadow color
     */
    public ColorUIResource getControlDarkShadow() { return getSecondary1(); }

    /**
     * Returns the control info color. This returns
     * the value of {@code getBlack()}.
     *
     * <p>
     *  返回控制信息颜色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the control info color
     */
    public ColorUIResource getControlInfo() { return getBlack(); }

    /**
     * Returns the control highlight color. This returns
     * the value of {@code getWhite()}.
     *
     * <p>
     *  返回控件高亮颜色。这将返回{@code getWhite()}的值。
     * 
     * 
     * @return the control highlight color
     */
    public ColorUIResource getControlHighlight() { return getWhite(); }

    /**
     * Returns the control disabled color. This returns
     * the value of {@code getSecondary2()}.
     *
     * <p>
     *  返回控件禁用的颜色。这返回{@code getSecondary2()}的值。
     * 
     * 
     * @return the control disabled color
     */
    public ColorUIResource getControlDisabled() { return getSecondary2(); }

    /**
     * Returns the primary control color. This returns
     * the value of {@code getPrimary3()}.
     *
     * <p>
     *  返回主控制颜色。这将返回{@code getPrimary3()}的值。
     * 
     * 
     * @return the primary control color
     */
    public ColorUIResource getPrimaryControl() { return getPrimary3(); }

    /**
     * Returns the primary control shadow color. This returns
     * the value of {@code getPrimary2()}.
     *
     * <p>
     *  返回主控制阴影颜色。这将返回{@code getPrimary2()}的值。
     * 
     * 
     * @return the primary control shadow color
     */
    public ColorUIResource getPrimaryControlShadow() { return getPrimary2(); }
    /**
     * Returns the primary control dark shadow color. This
     * returns the value of {@code getPrimary1()}.
     *
     * <p>
     *  返回主控件暗阴影颜色。这将返回{@code getPrimary1()}的值。
     * 
     * 
     * @return the primary control dark shadow color
     */
    public ColorUIResource getPrimaryControlDarkShadow() { return getPrimary1(); }

    /**
     * Returns the primary control info color. This
     * returns the value of {@code getBlack()}.
     *
     * <p>
     * 返回主控制信息颜色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the primary control info color
     */
    public ColorUIResource getPrimaryControlInfo() { return getBlack(); }

    /**
     * Returns the primary control highlight color. This
     * returns the value of {@code getWhite()}.
     *
     * <p>
     *  返回主控制高亮颜色。这将返回{@code getWhite()}的值。
     * 
     * 
     * @return the primary control highlight color
     */
    public ColorUIResource getPrimaryControlHighlight() { return getWhite(); }

    /**
     * Returns the system text color. This returns the value of
     * {@code getBlack()}.
     *
     * <p>
     *  返回系统文本颜色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the system text color
     */
    public ColorUIResource getSystemTextColor() { return getBlack(); }

    /**
     * Returns the control text color. This returns the value of
     * {@code getControlInfo()}.
     *
     * <p>
     *  返回控件文本颜色。这将返回{@code getControlInfo()}的值。
     * 
     * 
     * @return the control text color
     */
    public ColorUIResource getControlTextColor() { return getControlInfo(); }

    /**
     * Returns the inactive control text color. This returns the value of
     * {@code getControlDisabled()}.
     *
     * <p>
     *  返回非活动控件文本颜色。这将返回{@code getControlDisabled()}的值。
     * 
     * 
     * @return the inactive control text color
     */
    public ColorUIResource getInactiveControlTextColor() { return getControlDisabled(); }

    /**
     * Returns the inactive system text color. This returns the value of
     * {@code getSecondary2()}.
     *
     * <p>
     *  返回非活动系统文本颜色。这返回{@code getSecondary2()}的值。
     * 
     * 
     * @return the inactive system text color
     */
    public ColorUIResource getInactiveSystemTextColor() { return getSecondary2(); }

    /**
     * Returns the user text color. This returns the value of
     * {@code getBlack()}.
     *
     * <p>
     *  返回用户文本颜色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the user text color
     */
    public ColorUIResource getUserTextColor() { return getBlack(); }

    /**
     * Returns the text highlight color. This returns the value of
     * {@code getPrimary3()}.
     *
     * <p>
     *  返回文本突出显示颜色。这将返回{@code getPrimary3()}的值。
     * 
     * 
     * @return the text highlight color
     */
    public ColorUIResource getTextHighlightColor() { return getPrimary3(); }

    /**
     * Returns the highlighted text color. This returns the value of
     * {@code getControlTextColor()}.
     *
     * <p>
     *  返回突出显示的文本颜色。这将返回{@code getControlTextColor()}的值。
     * 
     * 
     * @return the highlighted text color
     */
    public ColorUIResource getHighlightedTextColor() { return getControlTextColor(); }

    /**
     * Returns the window background color. This returns the value of
     * {@code getWhite()}.
     *
     * <p>
     *  返回窗口背景颜色。这将返回{@code getWhite()}的值。
     * 
     * 
     * @return the window background color
     */
    public ColorUIResource getWindowBackground() { return getWhite(); }

    /**
     * Returns the window title background color. This returns the value of
     * {@code getPrimary3()}.
     *
     * <p>
     *  返回窗口标题背景颜色。这将返回{@code getPrimary3()}的值。
     * 
     * 
     * @return the window title background color
     */
    public ColorUIResource getWindowTitleBackground() { return getPrimary3(); }

    /**
     * Returns the window title foreground color. This returns the value of
     * {@code getBlack()}.
     *
     * <p>
     *  返回窗口标题前景色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the window title foreground color
     */
    public ColorUIResource getWindowTitleForeground() { return getBlack(); }

    /**
     * Returns the window title inactive background color. This
     * returns the value of {@code getSecondary3()}.
     *
     * <p>
     *  返回窗口标题无效的背景颜色。这将返回{@code getSecondary3()}的值。
     * 
     * 
     * @return the window title inactive background color
     */
    public ColorUIResource getWindowTitleInactiveBackground() { return getSecondary3(); }

    /**
     * Returns the window title inactive foreground color. This
     * returns the value of {@code getBlack()}.
     *
     * <p>
     *  返回窗口标题无效的前景颜色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the window title inactive foreground color
     */
    public ColorUIResource getWindowTitleInactiveForeground() { return getBlack(); }

    /**
     * Returns the menu background color. This
     * returns the value of {@code getSecondary3()}.
     *
     * <p>
     *  返回菜单背景颜色。这将返回{@code getSecondary3()}的值。
     * 
     * 
     * @return the menu background color
     */
    public ColorUIResource getMenuBackground() { return getSecondary3(); }

    /**
     * Returns the menu foreground color. This
     * returns the value of {@code getBlack()}.
     *
     * <p>
     *  返回菜单前景色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the menu foreground color
     */
    public ColorUIResource getMenuForeground() { return  getBlack(); }

    /**
     * Returns the menu selected background color. This
     * returns the value of {@code getPrimary2()}.
     *
     * <p>
     * 返回菜单选择的背景颜色。这将返回{@code getPrimary2()}的值。
     * 
     * 
     * @return the menu selected background color
     */
    public ColorUIResource getMenuSelectedBackground() { return getPrimary2(); }

    /**
     * Returns the menu selected foreground color. This
     * returns the value of {@code getBlack()}.
     *
     * <p>
     *  返回菜单选择的前景色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the menu selected foreground color
     */
    public ColorUIResource getMenuSelectedForeground() { return getBlack(); }

    /**
     * Returns the menu disabled foreground color. This
     * returns the value of {@code getSecondary2()}.
     *
     * <p>
     *  返回菜单禁用的前景色。这返回{@code getSecondary2()}的值。
     * 
     * 
     * @return the menu disabled foreground color
     */
    public ColorUIResource getMenuDisabledForeground() { return getSecondary2(); }

    /**
     * Returns the separator background color. This
     * returns the value of {@code getWhite()}.
     *
     * <p>
     *  返回分隔符背景颜色。这将返回{@code getWhite()}的值。
     * 
     * 
     * @return the separator background color
     */
    public ColorUIResource getSeparatorBackground() { return getWhite(); }

    /**
     * Returns the separator foreground color. This
     * returns the value of {@code getPrimary1()}.
     *
     * <p>
     *  返回分隔符前景颜色。这将返回{@code getPrimary1()}的值。
     * 
     * 
     * @return the separator foreground color
     */
    public ColorUIResource getSeparatorForeground() { return getPrimary1(); }

    /**
     * Returns the accelerator foreground color. This
     * returns the value of {@code getPrimary1()}.
     *
     * <p>
     *  返回加速器前景颜色。这将返回{@code getPrimary1()}的值。
     * 
     * 
     * @return the accelerator foreground color
     */
    public ColorUIResource getAcceleratorForeground() { return getPrimary1(); }

    /**
     * Returns the accelerator selected foreground color. This
     * returns the value of {@code getBlack()}.
     *
     * <p>
     *  返回加速器选择的前景色。这将返回{@code getBlack()}的值。
     * 
     * 
     * @return the accelerator selected foreground color
     */
    public ColorUIResource getAcceleratorSelectedForeground() { return getBlack(); }

    /**
     * Adds values specific to this theme to the defaults table. This method
     * is invoked when the look and feel defaults are obtained from
     * {@code MetalLookAndFeel}.
     * <p>
     * This implementation does nothing; it is provided for subclasses
     * that wish to customize the defaults table.
     *
     * <p>
     *  将特定于此主题的值添加到defaults表。当外观和感觉默认值从{@code MetalLookAndFeel}获取时,将调用此方法。
     * <p>
     *  这个实现什么也不做;它提供给希望定制默认表的子类。
     * 
     * 
     * @param table the {@code UIDefaults} to add the values to
     *
     * @see MetalLookAndFeel#getDefaults
     */
    public void addCustomEntriesToTable(UIDefaults table) {}

    /**
     * This is invoked when a MetalLookAndFeel is installed and about to
     * start using this theme. When we can add API this should be nuked
     * in favor of DefaultMetalTheme overriding addCustomEntriesToTable.
     * <p>
     *  当安装MetalLookAndFeel并即将开始使用此主题时,将调用此方法。
     * 当我们可以添加API时,这应该是有利于DefaultMetalTheme重写addCustomEntriesToTable。
     * 
     */
    void install() {
    }

    /**
     * Returns true if this is a theme provided by the core platform.
     * <p>
     */
    boolean isSystemTheme() {
        return false;
    }
}
