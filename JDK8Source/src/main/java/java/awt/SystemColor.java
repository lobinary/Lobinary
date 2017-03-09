/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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

import sun.awt.AWTAccessor;

import java.io.ObjectStreamException;

import java.lang.annotation.Native;

/**
 * A class to encapsulate symbolic colors representing the color of
 * native GUI objects on a system.  For systems which support the dynamic
 * update of the system colors (when the user changes the colors)
 * the actual RGB values of these symbolic colors will also change
 * dynamically.  In order to compare the "current" RGB value of a
 * <code>SystemColor</code> object with a non-symbolic Color object,
 * <code>getRGB</code> should be used rather than <code>equals</code>.
 * <p>
 * Note that the way in which these system colors are applied to GUI objects
 * may vary slightly from platform to platform since GUI objects may be
 * rendered differently on each platform.
 * <p>
 * System color values may also be available through the <code>getDesktopProperty</code>
 * method on <code>java.awt.Toolkit</code>.
 *
 * <p>
 *  用于封装表示系统上本机GUI对象的颜色的符号颜色的类。对于支持系统颜色的动态更新的系统(当用户更改颜色时),这些符号颜色的实际RGB值也将动态更改。
 * 为了将<code> SystemColor </code>对象的"当前"RGB值与非符号Color对象进行比较,应当使用<code> getRGB </code>而不是<code> equals </code>
 *  。
 *  用于封装表示系统上本机GUI对象的颜色的符号颜色的类。对于支持系统颜色的动态更新的系统(当用户更改颜色时),这些符号颜色的实际RGB值也将动态更改。
 * <p>
 *  注意,将这些系统颜色应用于GUI对象的方式可以从平台到平台稍微改变,因为GUI对象可以在每个平台上被不同地呈现。
 * <p>
 *  系统颜色值也可以通过<code> java.awt.Toolkit </code>上的<code> getDesktopProperty </code>方法获得。
 * 
 * 
 * @see Toolkit#getDesktopProperty
 *
 * @author      Carl Quinn
 * @author      Amy Fowler
 */
public final class SystemColor extends Color implements java.io.Serializable {

   /**
     * The array index for the
     * {@link #desktop} system color.
     * <p>
     *  {@link #desktop}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#desktop
     */
    @Native public final static int DESKTOP = 0;

    /**
     * The array index for the
     * {@link #activeCaption} system color.
     * <p>
     *  系统颜色的{@link #activeCaption}数组索引。
     * 
     * 
     * @see SystemColor#activeCaption
     */
    @Native public final static int ACTIVE_CAPTION = 1;

    /**
     * The array index for the
     * {@link #activeCaptionText} system color.
     * <p>
     *  系统颜色的{@link #activeCaptionText}数组索引。
     * 
     * 
     * @see SystemColor#activeCaptionText
     */
    @Native public final static int ACTIVE_CAPTION_TEXT = 2;

    /**
     * The array index for the
     * {@link #activeCaptionBorder} system color.
     * <p>
     *  {@link #activeCaptionBorder}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#activeCaptionBorder
     */
    @Native public final static int ACTIVE_CAPTION_BORDER = 3;

    /**
     * The array index for the
     * {@link #inactiveCaption} system color.
     * <p>
     *  {@link #inactiveCaption}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#inactiveCaption
     */
    @Native public final static int INACTIVE_CAPTION = 4;

    /**
     * The array index for the
     * {@link #inactiveCaptionText} system color.
     * <p>
     *  系统颜色的{@link #inactiveCaptionText}数组索引。
     * 
     * 
     * @see SystemColor#inactiveCaptionText
     */
    @Native public final static int INACTIVE_CAPTION_TEXT = 5;

    /**
     * The array index for the
     * {@link #inactiveCaptionBorder} system color.
     * <p>
     *  系统颜色的{@link #inactiveCaptionBorder}数组索引。
     * 
     * 
     * @see SystemColor#inactiveCaptionBorder
     */
    @Native public final static int INACTIVE_CAPTION_BORDER = 6;

    /**
     * The array index for the
     * {@link #window} system color.
     * <p>
     *  {@link #window}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#window
     */
    @Native public final static int WINDOW = 7;

    /**
     * The array index for the
     * {@link #windowBorder} system color.
     * <p>
     *  {@link #windowBorder}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#windowBorder
     */
    @Native public final static int WINDOW_BORDER = 8;

    /**
     * The array index for the
     * {@link #windowText} system color.
     * <p>
     * 系统颜色的{@link #windowText}数组索引。
     * 
     * 
     * @see SystemColor#windowText
     */
    @Native public final static int WINDOW_TEXT = 9;

    /**
     * The array index for the
     * {@link #menu} system color.
     * <p>
     *  系统颜色的{@link #menu}数组索引。
     * 
     * 
     * @see SystemColor#menu
     */
    @Native public final static int MENU = 10;

    /**
     * The array index for the
     * {@link #menuText} system color.
     * <p>
     *  系统颜色的{@link #menuText}数组索引。
     * 
     * 
     * @see SystemColor#menuText
     */
    @Native public final static int MENU_TEXT = 11;

    /**
     * The array index for the
     * {@link #text} system color.
     * <p>
     *  系统颜色的数组索引{@link #text}。
     * 
     * 
     * @see SystemColor#text
     */
    @Native public final static int TEXT = 12;

    /**
     * The array index for the
     * {@link #textText} system color.
     * <p>
     *  系统颜色的{@link #textText}数组索引。
     * 
     * 
     * @see SystemColor#textText
     */
    @Native public final static int TEXT_TEXT = 13;

    /**
     * The array index for the
     * {@link #textHighlight} system color.
     * <p>
     *  {@link #textHighlight}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#textHighlight
     */
    @Native public final static int TEXT_HIGHLIGHT = 14;

    /**
     * The array index for the
     * {@link #textHighlightText} system color.
     * <p>
     *  系统颜色的{@link #textHighlightText}数组索引。
     * 
     * 
     * @see SystemColor#textHighlightText
     */
    @Native public final static int TEXT_HIGHLIGHT_TEXT = 15;

    /**
     * The array index for the
     * {@link #textInactiveText} system color.
     * <p>
     *  系统颜色的{@link #textInactiveText}数组索引。
     * 
     * 
     * @see SystemColor#textInactiveText
     */
    @Native public final static int TEXT_INACTIVE_TEXT = 16;

    /**
     * The array index for the
     * {@link #control} system color.
     * <p>
     *  {@link #control}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#control
     */
    @Native public final static int CONTROL = 17;

    /**
     * The array index for the
     * {@link #controlText} system color.
     * <p>
     *  系统颜色的{@link #controlText}数组索引。
     * 
     * 
     * @see SystemColor#controlText
     */
    @Native public final static int CONTROL_TEXT = 18;

    /**
     * The array index for the
     * {@link #controlHighlight} system color.
     * <p>
     *  {@link #controlHighlight}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#controlHighlight
     */
    @Native public final static int CONTROL_HIGHLIGHT = 19;

    /**
     * The array index for the
     * {@link #controlLtHighlight} system color.
     * <p>
     *  {@link #controlLtHighlight}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#controlLtHighlight
     */
    @Native public final static int CONTROL_LT_HIGHLIGHT = 20;

    /**
     * The array index for the
     * {@link #controlShadow} system color.
     * <p>
     *  {@link #controlShadow}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#controlShadow
     */
    @Native public final static int CONTROL_SHADOW = 21;

    /**
     * The array index for the
     * {@link #controlDkShadow} system color.
     * <p>
     *  {@link #controlDkShadow}系统颜色的数组索引。
     * 
     * 
     * @see SystemColor#controlDkShadow
     */
    @Native public final static int CONTROL_DK_SHADOW = 22;

    /**
     * The array index for the
     * {@link #scrollbar} system color.
     * <p>
     *  系统颜色{@link #scrollbar}的数组索引。
     * 
     * 
     * @see SystemColor#scrollbar
     */
    @Native public final static int SCROLLBAR = 23;

    /**
     * The array index for the
     * {@link #info} system color.
     * <p>
     *  系统颜色的数组索引{@link #info}。
     * 
     * 
     * @see SystemColor#info
     */
    @Native public final static int INFO = 24;

    /**
     * The array index for the
     * {@link #infoText} system color.
     * <p>
     *  系统颜色的{@link #infoText}数组索引。
     * 
     * 
     * @see SystemColor#infoText
     */
    @Native public final static int INFO_TEXT = 25;

    /**
     * The number of system colors in the array.
     * <p>
     *  数组中系统颜色的数量。
     * 
     */
    @Native public final static int NUM_COLORS = 26;

    /******************************************************************************************/

    /*
     * System colors with default initial values, overwritten by toolkit if
     * system values differ and are available.
     * Should put array initialization above first field that is using
     * SystemColor constructor to initialize.
     * <p>
     *  系统颜色具有默认初始值,如果系统值不同且可用,则由工具箱覆盖。应该把数组初始化上面的第一个字段,使用SystemColor构造函数来初始化。
     * 
     */
    private static int[] systemColors = {
        0xFF005C5C,  // desktop = new Color(0,92,92);
        0xFF000080,  // activeCaption = new Color(0,0,128);
        0xFFFFFFFF,  // activeCaptionText = Color.white;
        0xFFC0C0C0,  // activeCaptionBorder = Color.lightGray;
        0xFF808080,  // inactiveCaption = Color.gray;
        0xFFC0C0C0,  // inactiveCaptionText = Color.lightGray;
        0xFFC0C0C0,  // inactiveCaptionBorder = Color.lightGray;
        0xFFFFFFFF,  // window = Color.white;
        0xFF000000,  // windowBorder = Color.black;
        0xFF000000,  // windowText = Color.black;
        0xFFC0C0C0,  // menu = Color.lightGray;
        0xFF000000,  // menuText = Color.black;
        0xFFC0C0C0,  // text = Color.lightGray;
        0xFF000000,  // textText = Color.black;
        0xFF000080,  // textHighlight = new Color(0,0,128);
        0xFFFFFFFF,  // textHighlightText = Color.white;
        0xFF808080,  // textInactiveText = Color.gray;
        0xFFC0C0C0,  // control = Color.lightGray;
        0xFF000000,  // controlText = Color.black;
        0xFFFFFFFF,  // controlHighlight = Color.white;
        0xFFE0E0E0,  // controlLtHighlight = new Color(224,224,224);
        0xFF808080,  // controlShadow = Color.gray;
        0xFF000000,  // controlDkShadow = Color.black;
        0xFFE0E0E0,  // scrollbar = new Color(224,224,224);
        0xFFE0E000,  // info = new Color(224,224,0);
        0xFF000000,  // infoText = Color.black;
    };

   /**
     * The color rendered for the background of the desktop.
     * <p>
     *  为桌面背景呈现的颜色。
     * 
     */
    public final static SystemColor desktop = new SystemColor((byte)DESKTOP);

    /**
     * The color rendered for the window-title background of the currently active window.
     * <p>
     *  为当前活动窗口的窗口标题背景呈现的颜色。
     * 
     */
    public final static SystemColor activeCaption = new SystemColor((byte)ACTIVE_CAPTION);

    /**
     * The color rendered for the window-title text of the currently active window.
     * <p>
     *  为当前活动窗口的窗口标题文本呈现的颜色。
     * 
     */
    public final static SystemColor activeCaptionText = new SystemColor((byte)ACTIVE_CAPTION_TEXT);

    /**
     * The color rendered for the border around the currently active window.
     * <p>
     * 为当前活动窗口周围的边框呈现的颜色。
     * 
     */
    public final static SystemColor activeCaptionBorder = new SystemColor((byte)ACTIVE_CAPTION_BORDER);

    /**
     * The color rendered for the window-title background of inactive windows.
     * <p>
     *  为不活动窗口的窗口标题背景呈现的颜色。
     * 
     */
    public final static SystemColor inactiveCaption = new SystemColor((byte)INACTIVE_CAPTION);

    /**
     * The color rendered for the window-title text of inactive windows.
     * <p>
     *  为不活动窗口的窗口标题文本呈现的颜色。
     * 
     */
    public final static SystemColor inactiveCaptionText = new SystemColor((byte)INACTIVE_CAPTION_TEXT);

    /**
     * The color rendered for the border around inactive windows.
     * <p>
     *  为不活动窗口周围的边框呈现的颜色。
     * 
     */
    public final static SystemColor inactiveCaptionBorder = new SystemColor((byte)INACTIVE_CAPTION_BORDER);

    /**
     * The color rendered for the background of interior regions inside windows.
     * <p>
     *  为窗口内部区域的背景呈现的颜色。
     * 
     */
    public final static SystemColor window = new SystemColor((byte)WINDOW);

    /**
     * The color rendered for the border around interior regions inside windows.
     * <p>
     *  为窗口内部区域的边框呈现的颜色。
     * 
     */
    public final static SystemColor windowBorder = new SystemColor((byte)WINDOW_BORDER);

    /**
     * The color rendered for text of interior regions inside windows.
     * <p>
     *  为窗口内部区域的文本呈现的颜色。
     * 
     */
    public final static SystemColor windowText = new SystemColor((byte)WINDOW_TEXT);

    /**
     * The color rendered for the background of menus.
     * <p>
     *  为菜单背景呈现的颜色。
     * 
     */
    public final static SystemColor menu = new SystemColor((byte)MENU);

    /**
     * The color rendered for the text of menus.
     * <p>
     *  为菜单文本呈现的颜色。
     * 
     */
    public final static SystemColor menuText = new SystemColor((byte)MENU_TEXT);

    /**
     * The color rendered for the background of text control objects, such as
     * textfields and comboboxes.
     * <p>
     *  为文本控件对象的背景渲染的颜色,例如文本字段和组合框。
     * 
     */
    public final static SystemColor text = new SystemColor((byte)TEXT);

    /**
     * The color rendered for the text of text control objects, such as textfields
     * and comboboxes.
     * <p>
     *  为文本控件对象(例如文本字段和组合框)的文本呈现的颜色。
     * 
     */
    public final static SystemColor textText = new SystemColor((byte)TEXT_TEXT);

    /**
     * The color rendered for the background of selected items, such as in menus,
     * comboboxes, and text.
     * <p>
     *  为所选项目的背景呈现的颜色,例如在菜单,组合框和文本中。
     * 
     */
    public final static SystemColor textHighlight = new SystemColor((byte)TEXT_HIGHLIGHT);

    /**
     * The color rendered for the text of selected items, such as in menus, comboboxes,
     * and text.
     * <p>
     *  为所选项目的文本呈现的颜色,例如在菜单,组合框和文本中。
     * 
     */
    public final static SystemColor textHighlightText = new SystemColor((byte)TEXT_HIGHLIGHT_TEXT);

    /**
     * The color rendered for the text of inactive items, such as in menus.
     * <p>
     *  为非活动项目的文本(例如在菜单中)显示的颜色。
     * 
     */
    public final static SystemColor textInactiveText = new SystemColor((byte)TEXT_INACTIVE_TEXT);

    /**
     * The color rendered for the background of control panels and control objects,
     * such as pushbuttons.
     * <p>
     *  为控制面板和控制对象(例如按钮)的背景呈现的颜色。
     * 
     */
    public final static SystemColor control = new SystemColor((byte)CONTROL);

    /**
     * The color rendered for the text of control panels and control objects,
     * such as pushbuttons.
     * <p>
     *  为控制面板和控制对象(例如按钮)的文本呈现的颜色。
     * 
     */
    public final static SystemColor controlText = new SystemColor((byte)CONTROL_TEXT);

    /**
     * The color rendered for light areas of 3D control objects, such as pushbuttons.
     * This color is typically derived from the <code>control</code> background color
     * to provide a 3D effect.
     * <p>
     *  为3D控制对象的亮区(例如按钮)呈现的颜色。此颜色通常来自于<code> control </code>背景颜色,以提供3D效果。
     * 
     */
    public final static SystemColor controlHighlight = new SystemColor((byte)CONTROL_HIGHLIGHT);

    /**
     * The color rendered for highlight areas of 3D control objects, such as pushbuttons.
     * This color is typically derived from the <code>control</code> background color
     * to provide a 3D effect.
     * <p>
     * 为3D控制对象的突出显示区域渲染的颜色,例如按钮。此颜色通常来自于<code> control </code>背景颜色,以提供3D效果。
     * 
     */
    public final static SystemColor controlLtHighlight = new SystemColor((byte)CONTROL_LT_HIGHLIGHT);

    /**
     * The color rendered for shadow areas of 3D control objects, such as pushbuttons.
     * This color is typically derived from the <code>control</code> background color
     * to provide a 3D effect.
     * <p>
     *  为3D控制对象的阴影区域(例如按钮)呈现的颜色。此颜色通常来自于<code> control </code>背景颜色,以提供3D效果。
     * 
     */
    public final static SystemColor controlShadow = new SystemColor((byte)CONTROL_SHADOW);

    /**
     * The color rendered for dark shadow areas on 3D control objects, such as pushbuttons.
     * This color is typically derived from the <code>control</code> background color
     * to provide a 3D effect.
     * <p>
     *  为3D控制对象(例如按钮)上的阴影区域渲染的颜色。此颜色通常来自于<code> control </code>背景颜色,以提供3D效果。
     * 
     */
    public final static SystemColor controlDkShadow = new SystemColor((byte)CONTROL_DK_SHADOW);

    /**
     * The color rendered for the background of scrollbars.
     * <p>
     *  为滚动条的背景呈现的颜色。
     * 
     */
    public final static SystemColor scrollbar = new SystemColor((byte)SCROLLBAR);

    /**
     * The color rendered for the background of tooltips or spot help.
     * <p>
     *  为工具提示或spot帮助的背景呈现的颜色。
     * 
     */
    public final static SystemColor info = new SystemColor((byte)INFO);

    /**
     * The color rendered for the text of tooltips or spot help.
     * <p>
     *  为工具提示或spot帮助的文本呈现的颜色。
     * 
     */
    public final static SystemColor infoText = new SystemColor((byte)INFO_TEXT);

    /*
     * JDK 1.1 serialVersionUID.
     * <p>
     *  JDK 1.1 serialVersionUID。
     * 
     */
    private static final long serialVersionUID = 4503142729533789064L;

    /*
     * An index into either array of SystemColor objects or values.
     * <p>
     *  指向SystemColor对象或值的数组的索引。
     * 
     */
    private transient int index;

    private static SystemColor systemColorObjects [] = {
        SystemColor.desktop,
        SystemColor.activeCaption,
        SystemColor.activeCaptionText,
        SystemColor.activeCaptionBorder,
        SystemColor.inactiveCaption,
        SystemColor.inactiveCaptionText,
        SystemColor.inactiveCaptionBorder,
        SystemColor.window,
        SystemColor.windowBorder,
        SystemColor.windowText,
        SystemColor.menu,
        SystemColor.menuText,
        SystemColor.text,
        SystemColor.textText,
        SystemColor.textHighlight,
        SystemColor.textHighlightText,
        SystemColor.textInactiveText,
        SystemColor.control,
        SystemColor.controlText,
        SystemColor.controlHighlight,
        SystemColor.controlLtHighlight,
        SystemColor.controlShadow,
        SystemColor.controlDkShadow,
        SystemColor.scrollbar,
        SystemColor.info,
        SystemColor.infoText
    };

    static {
        AWTAccessor.setSystemColorAccessor(SystemColor::updateSystemColors);
        updateSystemColors();
    }

    /**
     * Called from {@code <init>} and toolkit to update the above systemColors cache.
     * <p>
     *  从{@code <init>}和工具包调用以更新上述systemColors缓存。
     * 
     */
    private static void updateSystemColors() {
        if (!GraphicsEnvironment.isHeadless()) {
            Toolkit.getDefaultToolkit().loadSystemColors(systemColors);
        }
        for (int i = 0; i < systemColors.length; i++) {
            systemColorObjects[i].value = systemColors[i];
        }
    }

    /**
     * Creates a symbolic color that represents an indexed entry into system
     * color cache. Used by above static system colors.
     * <p>
     *  创建表示到系统颜色缓存的索引条目的符号颜色。由上述静态系统颜色使用。
     * 
     */
    private SystemColor(byte index) {
        super(systemColors[index]);
        this.index = index;
    }

    /**
     * Returns a string representation of this <code>Color</code>'s values.
     * This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary between
     * implementations.
     * The returned string may be empty but may not be <code>null</code>.
     *
     * <p>
     *  返回此<c> Color </code>的值的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>Color</code>
     */
    public String toString() {
        return getClass().getName() + "[i=" + (index) + "]";
    }

    /**
     * The design of the {@code SystemColor} class assumes that
     * the {@code SystemColor} object instances stored in the
     * static final fields above are the only instances that can
     * be used by developers.
     * This method helps maintain those limits on instantiation
     * by using the index stored in the value field of the
     * serialized form of the object to replace the serialized
     * object with the equivalent static object constant field
     * of {@code SystemColor}.
     * See the {@link #writeReplace} method for more information
     * on the serialized form of these objects.
     * <p>
     * {@code SystemColor}类的设计假定存储在上面静态最终字段中的{@code SystemColor}对象实例是开发人员可以使用的唯一实例。
     * 此方法通过使用存储在对象的序列化形式的值字段中的索引来替换序列化对象与{@code SystemColor}的等效静态对象常量字段,来帮助维持对实例化的这些限制。
     * 有关这些对象的序列化形式的更多信息,请参阅{@link #writeReplace}方法。
     * 
     * 
     * @return one of the {@code SystemColor} static object
     *         fields that refers to the same system color.
     */
    private Object readResolve() {
        // The instances of SystemColor are tightly controlled and
        // only the canonical instances appearing above as static
        // constants are allowed.  The serial form of SystemColor
        // objects stores the color index as the value.  Here we
        // map that index back into the canonical instance.
        return systemColorObjects[value];
    }

    /**
     * Returns a specialized version of the {@code SystemColor}
     * object for writing to the serialized stream.
     * <p>
     * 
     * @serialData
     * The value field of a serialized {@code SystemColor} object
     * contains the array index of the system color instead of the
     * rgb data for the system color.
     * This index is used by the {@link #readResolve} method to
     * resolve the deserialized objects back to the original
     * static constant versions to ensure unique instances of
     * each {@code SystemColor} object.
     * @return a proxy {@code SystemColor} object with its value
     *         replaced by the corresponding system color index.
     */
    private Object writeReplace() throws ObjectStreamException
    {
        // we put an array index in the SystemColor.value while serialize
        // to keep compatibility.
        SystemColor color = new SystemColor((byte)index);
        color.value = index;
        return color;
    }
}
