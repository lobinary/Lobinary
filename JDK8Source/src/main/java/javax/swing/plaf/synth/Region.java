/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.synth;

import sun.awt.AppContext;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.UIDefaults;

/**
 * A distinct rendering area of a Swing component.  A component may
 * support one or more regions.  Specific component regions are defined
 * by the typesafe enumeration in this class.
 * <p>
 * Regions are typically used as a way to identify the <code>Component</code>s
 * and areas a particular style is to apply to. Synth's file format allows you
 * to bind styles based on the name of a <code>Region</code>.
 * The name is derived from the field name of the constant:
 * <ol>
 *  <li>Map all characters to lowercase.
 *  <li>Map the first character to uppercase.
 *  <li>Map the first character after underscores to uppercase.
 *  <li>Remove all underscores.
 * </ol>
 * For example, to identify the <code>SPLIT_PANE</code>
 * <code>Region</code> you would use <code>SplitPane</code>.
 * The following shows a custom <code>SynthStyleFactory</code>
 * that returns a specific style for split panes:
 * <pre>
 *    public SynthStyle getStyle(JComponent c, Region id) {
 *        if (id == Region.SPLIT_PANE) {
 *            return splitPaneStyle;
 *        }
 *        ...
 *    }
 * </pre>
 * The following <a href="doc-files/synthFileFormat.html">xml</a>
 * accomplishes the same thing:
 * <pre>
 * &lt;style id="splitPaneStyle"&gt;
 *   ...
 * &lt;/style&gt;
 * &lt;bind style="splitPaneStyle" type="region" key="SplitPane"/&gt;
 * </pre>
 *
 * <p>
 *  Swing组件的独特呈现区域。组件可以支持一个或多个区域。特定组件区域由此类中的类型安全枚举定义。
 * <p>
 *  区域通常用作标识要应用于特定样式的<code>组件</code>和区域的方式。 Synth的文件格式允许您基于<code> Region </code>的名称绑定样式。名称派生自常量的字段名称：
 * <ol>
 *  <li>将所有字符映射为小写。 <li>将第一个字符映射为大写。 <li>将下划线后的第一个字符映射为大写。 <li>删除所有下划线。
 * </ol>
 *  例如,要识别<code> SPLIT_PANE </code> <code> Region </code>,您将使用<code> SplitPane </code>。
 * 下面显示了返回分割窗格的特定样式的自定义<code> SynthStyleFactory </code>：。
 * <pre>
 *  public SynthStyle getStyle(JComponent c,Region id){if(id == Region.SPLIT_PANE){return splitPaneStyle; }
 *  ...}。
 * </pre>
 *  以下<a href="doc-files/synthFileFormat.html"> xml </a>完成相同的操作：
 * <pre>
 *  &lt; style id ="splitPaneStyle"&gt; ...&lt; / style&gt; &lt; bind style ="splitPaneStyle"type ="regi
 * on"key ="SplitPane"/&gt;。
 * </pre>
 * 
 * 
 * @since 1.5
 * @author Scott Violet
 */
public class Region {
    private static final Object UI_TO_REGION_MAP_KEY = new Object();
    private static final Object LOWER_CASE_NAME_MAP_KEY = new Object();

    /**
     * ArrowButton's are special types of buttons that also render a
     * directional indicator, typically an arrow. ArrowButtons are used by
     * composite components, for example ScrollBar's contain ArrowButtons.
     * To bind a style to this <code>Region</code> use the name
     * <code>ArrowButton</code>.
     * <p>
     * ArrowButton是特殊类型的按钮,也提供方向指示器,通常是箭头。 ArrowButton由复合组件使用,例如ScrollBar的包含ArrowButtons。
     * 要将样式绑定到此<code> Region </code>,请使用名称<code> ArrowButton </code>。
     * 
     */
    public static final Region ARROW_BUTTON = new Region("ArrowButton", false);

    /**
     * Button region. To bind a style to this <code>Region</code> use the name
     * <code>Button</code>.
     * <p>
     *  按钮区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Button </code>。
     * 
     */
    public static final Region BUTTON = new Region("Button", false);

    /**
     * CheckBox region. To bind a style to this <code>Region</code> use the name
     * <code>CheckBox</code>.
     * <p>
     *  复选框区域。要将样式绑定到此<code> Region </code>,请使用名称<code>复选框</code>。
     * 
     */
    public static final Region CHECK_BOX = new Region("CheckBox", false);

    /**
     * CheckBoxMenuItem region. To bind a style to this <code>Region</code> use
     * the name <code>CheckBoxMenuItem</code>.
     * <p>
     *  CheckBoxMenuItem区域。要将样式绑定到此<code> Region </code>,请使用名称<code> CheckBoxMenuItem </code>。
     * 
     */
    public static final Region CHECK_BOX_MENU_ITEM = new Region("CheckBoxMenuItem", false);

    /**
     * ColorChooser region. To bind a style to this <code>Region</code> use
     * the name <code>ColorChooser</code>.
     * <p>
     *  ColorChooser区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ColorChooser </code>。
     * 
     */
    public static final Region COLOR_CHOOSER = new Region("ColorChooser", false);

    /**
     * ComboBox region. To bind a style to this <code>Region</code> use
     * the name <code>ComboBox</code>.
     * <p>
     *  ComboBox区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ComboBox </code>。
     * 
     */
    public static final Region COMBO_BOX = new Region("ComboBox", false);

    /**
     * DesktopPane region. To bind a style to this <code>Region</code> use
     * the name <code>DesktopPane</code>.
     * <p>
     *  DesktopPane区域。要将样式绑定到此<code> Region </code>,请使用名称<code> DesktopPane </code>。
     * 
     */
    public static final Region DESKTOP_PANE = new Region("DesktopPane", false);

    /**
     * DesktopIcon region. To bind a style to this <code>Region</code> use
     * the name <code>DesktopIcon</code>.
     * <p>
     *  DesktopIcon区域。要将样式绑定到此<code> Region </code>,请使用名称<code> DesktopIcon </code>。
     * 
     */
    public static final Region DESKTOP_ICON = new Region("DesktopIcon", false);

    /**
     * EditorPane region. To bind a style to this <code>Region</code> use
     * the name <code>EditorPane</code>.
     * <p>
     *  编辑器区域。要将样式绑定到此<code> Region </code>,请使用名称<code> EditorPane </code>。
     * 
     */
    public static final Region EDITOR_PANE = new Region("EditorPane", false);

    /**
     * FileChooser region. To bind a style to this <code>Region</code> use
     * the name <code>FileChooser</code>.
     * <p>
     *  FileChooser区域。要将样式绑定到此<code> Region </code>,请使用名称<code> FileChooser </code>。
     * 
     */
    public static final Region FILE_CHOOSER = new Region("FileChooser", false);

    /**
     * FormattedTextField region. To bind a style to this <code>Region</code> use
     * the name <code>FormattedTextField</code>.
     * <p>
     *  FormattedTextField region。要将样式绑定到此<code> Region </code>,请使用名称<code> FormattedTextField </code>。
     * 
     */
    public static final Region FORMATTED_TEXT_FIELD = new Region("FormattedTextField", false);

    /**
     * InternalFrame region. To bind a style to this <code>Region</code> use
     * the name <code>InternalFrame</code>.
     * <p>
     *  InternalFrame区域。要将样式绑定到此<code> Region </code>,请使用名称<code> InternalFrame </code>。
     * 
     */
    public static final Region INTERNAL_FRAME = new Region("InternalFrame", false);

    /**
     * TitlePane of an InternalFrame. The TitlePane typically
     * shows a menu, title, widgets to manipulate the internal frame.
     * To bind a style to this <code>Region</code> use the name
     * <code>InternalFrameTitlePane</code>.
     * <p>
     * InternalFrame的标题面板。 TitlePane通常显示一个菜单,标题,控制内部框架的小部件。
     * 要将样式绑定到此<code> Region </code>,请使用名称<code> InternalFrameTitlePane </code>。
     * 
     */
    public static final Region INTERNAL_FRAME_TITLE_PANE = new Region("InternalFrameTitlePane", false);

    /**
     * Label region. To bind a style to this <code>Region</code> use the name
     * <code>Label</code>.
     * <p>
     *  标签区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Label </code>。
     * 
     */
    public static final Region LABEL = new Region("Label", false);

    /**
     * List region. To bind a style to this <code>Region</code> use the name
     * <code>List</code>.
     * <p>
     *  列表区域。要将样式绑定到此<code> Region </code>,请使用名称<code> List </code>。
     * 
     */
    public static final Region LIST = new Region("List", false);

    /**
     * Menu region. To bind a style to this <code>Region</code> use the name
     * <code>Menu</code>.
     * <p>
     *  菜单区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Menu </code>。
     * 
     */
    public static final Region MENU = new Region("Menu", false);

    /**
     * MenuBar region. To bind a style to this <code>Region</code> use the name
     * <code>MenuBar</code>.
     * <p>
     *  MenuBar区域。要将样式绑定到此<code> Region </code>,请使用名称<code> MenuBar </code>。
     * 
     */
    public static final Region MENU_BAR = new Region("MenuBar", false);

    /**
     * MenuItem region. To bind a style to this <code>Region</code> use the name
     * <code>MenuItem</code>.
     * <p>
     *  MenuItem区域。要将样式绑定到此<code> Region </code>,请使用名称<code> MenuItem </code>。
     * 
     */
    public static final Region MENU_ITEM = new Region("MenuItem", false);

    /**
     * Accelerator region of a MenuItem. To bind a style to this
     * <code>Region</code> use the name <code>MenuItemAccelerator</code>.
     * <p>
     *  MenuItem的加速器区域。要将样式绑定到此<code> Region </code>,请使用名称<code> MenuItemAccelerator </code>。
     * 
     */
    public static final Region MENU_ITEM_ACCELERATOR = new Region("MenuItemAccelerator", true);

    /**
     * OptionPane region. To bind a style to this <code>Region</code> use
     * the name <code>OptionPane</code>.
     * <p>
     *  OptionPane区域。要将样式绑定到此<code> Region </code>,请使用名称<code> OptionPane </code>。
     * 
     */
    public static final Region OPTION_PANE = new Region("OptionPane", false);

    /**
     * Panel region. To bind a style to this <code>Region</code> use the name
     * <code>Panel</code>.
     * <p>
     *  面板区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Panel </code>。
     * 
     */
    public static final Region PANEL = new Region("Panel", false);

    /**
     * PasswordField region. To bind a style to this <code>Region</code> use
     * the name <code>PasswordField</code>.
     * <p>
     *  PasswordField区域。要将样式绑定到此<code> Region </code>,请使用名称<code> PasswordField </code>。
     * 
     */
    public static final Region PASSWORD_FIELD = new Region("PasswordField", false);

    /**
     * PopupMenu region. To bind a style to this <code>Region</code> use
     * the name <code>PopupMenu</code>.
     * <p>
     *  PopupMenu区域。要将样式绑定到此<code> Region </code>,请使用名称<code> PopupMenu </code>。
     * 
     */
    public static final Region POPUP_MENU = new Region("PopupMenu", false);

    /**
     * PopupMenuSeparator region. To bind a style to this <code>Region</code>
     * use the name <code>PopupMenuSeparator</code>.
     * <p>
     *  PopupMenuSeparator区域。要将样式绑定到此<code> Region </code>,请使用名称<code> PopupMenuSeparator </code>。
     * 
     */
    public static final Region POPUP_MENU_SEPARATOR = new Region("PopupMenuSeparator", false);

    /**
     * ProgressBar region. To bind a style to this <code>Region</code>
     * use the name <code>ProgressBar</code>.
     * <p>
     *  ProgressBar区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ProgressBar </code>。
     * 
     */
    public static final Region PROGRESS_BAR = new Region("ProgressBar", false);

    /**
     * RadioButton region. To bind a style to this <code>Region</code>
     * use the name <code>RadioButton</code>.
     * <p>
     * RadioButton区域。要将样式绑定到此<code> Region </code>,请使用名称<code> RadioButton </code>。
     * 
     */
    public static final Region RADIO_BUTTON = new Region("RadioButton", false);

    /**
     * RegionButtonMenuItem region. To bind a style to this <code>Region</code>
     * use the name <code>RadioButtonMenuItem</code>.
     * <p>
     *  RegionButtonMenuItem region。要将样式绑定到此<code> Region </code>,请使用名称<code> RadioButtonMenuItem </code>。
     * 
     */
    public static final Region RADIO_BUTTON_MENU_ITEM = new Region("RadioButtonMenuItem", false);

    /**
     * RootPane region. To bind a style to this <code>Region</code> use
     * the name <code>RootPane</code>.
     * <p>
     *  RootPane区域。要将样式绑定到此<code> Region </code>,请使用名称<code> RootPane </code>。
     * 
     */
    public static final Region ROOT_PANE = new Region("RootPane", false);

    /**
     * ScrollBar region. To bind a style to this <code>Region</code> use
     * the name <code>ScrollBar</code>.
     * <p>
     *  ScrollBar区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ScrollBar </code>。
     * 
     */
    public static final Region SCROLL_BAR = new Region("ScrollBar", false);

    /**
     * Track of the ScrollBar. To bind a style to this <code>Region</code> use
     * the name <code>ScrollBarTrack</code>.
     * <p>
     *  TrackBar的轨道。要将样式绑定到此<code> Region </code>,请使用名称<code> ScrollBarTrack </code>。
     * 
     */
    public static final Region SCROLL_BAR_TRACK = new Region("ScrollBarTrack", true);

    /**
     * Thumb of the ScrollBar. The thumb is the region of the ScrollBar
     * that gives a graphical depiction of what percentage of the View is
     * currently visible. To bind a style to this <code>Region</code> use
     * the name <code>ScrollBarThumb</code>.
     * <p>
     *  ScrollBar的缩略图。大拇指是ScrollBar的区域,它给出了当前可见的视图的百分比的图形描述。
     * 要将样式绑定到此<code> Region </code>,请使用名称<code> ScrollBarThumb </code>。
     * 
     */
    public static final Region SCROLL_BAR_THUMB = new Region("ScrollBarThumb", true);

    /**
     * ScrollPane region. To bind a style to this <code>Region</code> use
     * the name <code>ScrollPane</code>.
     * <p>
     *  滚动区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ScrollPane </code>。
     * 
     */
    public static final Region SCROLL_PANE = new Region("ScrollPane", false);

    /**
     * Separator region. To bind a style to this <code>Region</code> use
     * the name <code>Separator</code>.
     * <p>
     *  分隔符区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Separator </code>。
     * 
     */
    public static final Region SEPARATOR = new Region("Separator", false);

    /**
     * Slider region. To bind a style to this <code>Region</code> use
     * the name <code>Slider</code>.
     * <p>
     *  滑块区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Slider </code>。
     * 
     */
    public static final Region SLIDER = new Region("Slider", false);

    /**
     * Track of the Slider. To bind a style to this <code>Region</code> use
     * the name <code>SliderTrack</code>.
     * <p>
     *  滑块的轨道。要将样式绑定到此<code> Region </code>,请使用名称<code> SliderTrack </code>。
     * 
     */
    public static final Region SLIDER_TRACK = new Region("SliderTrack", true);

    /**
     * Thumb of the Slider. The thumb of the Slider identifies the current
     * value. To bind a style to this <code>Region</code> use the name
     * <code>SliderThumb</code>.
     * <p>
     *  滑块的拇指。滑块的缩略图标识当前值。要将样式绑定到此<code> Region </code>,请使用名称<code> SliderThumb </code>。
     * 
     */
    public static final Region SLIDER_THUMB = new Region("SliderThumb", true);

    /**
     * Spinner region. To bind a style to this <code>Region</code> use the name
     * <code>Spinner</code>.
     * <p>
     *  旋转地区。要将样式绑定到此<code> Region </code>,请使用名称<code> Spinner </code>。
     * 
     */
    public static final Region SPINNER = new Region("Spinner", false);

    /**
     * SplitPane region. To bind a style to this <code>Region</code> use the name
     * <code>SplitPane</code>.
     * <p>
     * SplitPane区域。要将样式绑定到此<code> Region </code>,请使用名称<code> SplitPane </code>。
     * 
     */
    public static final Region SPLIT_PANE = new Region("SplitPane", false);

    /**
     * Divider of the SplitPane. To bind a style to this <code>Region</code>
     * use the name <code>SplitPaneDivider</code>.
     * <p>
     *  SplitPane的分隔线。要将样式绑定到此<code> Region </code>,请使用名称<code> SplitPaneDivider </code>。
     * 
     */
    public static final Region SPLIT_PANE_DIVIDER = new Region("SplitPaneDivider", true);

    /**
     * TabbedPane region. To bind a style to this <code>Region</code> use
     * the name <code>TabbedPane</code>.
     * <p>
     *  TabbedPane区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TabbedPane </code>。
     * 
     */
    public static final Region TABBED_PANE = new Region("TabbedPane", false);

    /**
     * Region of a TabbedPane for one tab. To bind a style to this
     * <code>Region</code> use the name <code>TabbedPaneTab</code>.
     * <p>
     *  一个选项卡的TabbedPane的区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TabbedPaneTab </code>。
     * 
     */
    public static final Region TABBED_PANE_TAB = new Region("TabbedPaneTab", true);

    /**
     * Region of a TabbedPane containing the tabs. To bind a style to this
     * <code>Region</code> use the name <code>TabbedPaneTabArea</code>.
     * <p>
     *  包含标签的TabbedPane的区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TabbedPaneTabArea </code>。
     * 
     */
    public static final Region TABBED_PANE_TAB_AREA = new Region("TabbedPaneTabArea", true);

    /**
     * Region of a TabbedPane containing the content. To bind a style to this
     * <code>Region</code> use the name <code>TabbedPaneContent</code>.
     * <p>
     *  包含内容的TabbedPane的区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TabbedPaneContent </code>。
     * 
     */
    public static final Region TABBED_PANE_CONTENT = new Region("TabbedPaneContent", true);

    /**
     * Table region. To bind a style to this <code>Region</code> use
     * the name <code>Table</code>.
     * <p>
     *  表区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Table </code>。
     * 
     */
    public static final Region TABLE = new Region("Table", false);

    /**
     * TableHeader region. To bind a style to this <code>Region</code> use
     * the name <code>TableHeader</code>.
     * <p>
     *  TableHeader区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TableHeader </code>。
     * 
     */
    public static final Region TABLE_HEADER = new Region("TableHeader", false);

    /**
     * TextArea region. To bind a style to this <code>Region</code> use
     * the name <code>TextArea</code>.
     * <p>
     *  TextArea区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TextArea </code>。
     * 
     */
    public static final Region TEXT_AREA = new Region("TextArea", false);

    /**
     * TextField region. To bind a style to this <code>Region</code> use
     * the name <code>TextField</code>.
     * <p>
     *  TextField区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TextField </code>。
     * 
     */
    public static final Region TEXT_FIELD = new Region("TextField", false);

    /**
     * TextPane region. To bind a style to this <code>Region</code> use
     * the name <code>TextPane</code>.
     * <p>
     *  TextPane区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TextPane </code>。
     * 
     */
    public static final Region TEXT_PANE = new Region("TextPane", false);

    /**
     * ToggleButton region. To bind a style to this <code>Region</code> use
     * the name <code>ToggleButton</code>.
     * <p>
     *  ToggleButton区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ToggleButton </code>。
     * 
     */
    public static final Region TOGGLE_BUTTON = new Region("ToggleButton", false);

    /**
     * ToolBar region. To bind a style to this <code>Region</code> use
     * the name <code>ToolBar</code>.
     * <p>
     *  工具栏区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ToolBar </code>。
     * 
     */
    public static final Region TOOL_BAR = new Region("ToolBar", false);

    /**
     * Region of the ToolBar containing the content. To bind a style to this
     * <code>Region</code> use the name <code>ToolBarContent</code>.
     * <p>
     * 包含内容的ToolBar的区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ToolBarContent </code>。
     * 
     */
    public static final Region TOOL_BAR_CONTENT = new Region("ToolBarContent", true);

    /**
     * Region for the Window containing the ToolBar. To bind a style to this
     * <code>Region</code> use the name <code>ToolBarDragWindow</code>.
     * <p>
     *  包含ToolBar的窗口的区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ToolBarDragWindow </code>。
     * 
     */
    public static final Region TOOL_BAR_DRAG_WINDOW = new Region("ToolBarDragWindow", false);

    /**
     * ToolTip region. To bind a style to this <code>Region</code> use
     * the name <code>ToolTip</code>.
     * <p>
     *  工具提示区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ToolTip </code>。
     * 
     */
    public static final Region TOOL_TIP = new Region("ToolTip", false);

    /**
     * ToolBar separator region. To bind a style to this <code>Region</code> use
     * the name <code>ToolBarSeparator</code>.
     * <p>
     *  ToolBar分隔符区域。要将样式绑定到此<code> Region </code>,请使用名称<code> ToolBarSeparator </code>。
     * 
     */
    public static final Region TOOL_BAR_SEPARATOR = new Region("ToolBarSeparator", false);

    /**
     * Tree region. To bind a style to this <code>Region</code> use the name
     * <code>Tree</code>.
     * <p>
     *  树区。要将样式绑定到此<code> Region </code>,请使用名称<code> Tree </code>。
     * 
     */
    public static final Region TREE = new Region("Tree", false);

    /**
     * Region of the Tree for one cell. To bind a style to this
     * <code>Region</code> use the name <code>TreeCell</code>.
     * <p>
     *  一个单元格的树的区域。要将样式绑定到此<code> Region </code>,请使用名称<code> TreeCell </code>。
     * 
     */
    public static final Region TREE_CELL = new Region("TreeCell", true);

    /**
     * Viewport region. To bind a style to this <code>Region</code> use
     * the name <code>Viewport</code>.
     * <p>
     *  视口区域。要将样式绑定到此<code> Region </code>,请使用名称<code> Viewport </code>。
     * 
     */
    public static final Region VIEWPORT = new Region("Viewport", false);

    private static Map<String, Region> getUItoRegionMap() {
        AppContext context = AppContext.getAppContext();
        Map<String, Region> map = (Map<String, Region>) context.get(UI_TO_REGION_MAP_KEY);
        if (map == null) {
            map = new HashMap<String, Region>();
            map.put("ArrowButtonUI", ARROW_BUTTON);
            map.put("ButtonUI", BUTTON);
            map.put("CheckBoxUI", CHECK_BOX);
            map.put("CheckBoxMenuItemUI", CHECK_BOX_MENU_ITEM);
            map.put("ColorChooserUI", COLOR_CHOOSER);
            map.put("ComboBoxUI", COMBO_BOX);
            map.put("DesktopPaneUI", DESKTOP_PANE);
            map.put("DesktopIconUI", DESKTOP_ICON);
            map.put("EditorPaneUI", EDITOR_PANE);
            map.put("FileChooserUI", FILE_CHOOSER);
            map.put("FormattedTextFieldUI", FORMATTED_TEXT_FIELD);
            map.put("InternalFrameUI", INTERNAL_FRAME);
            map.put("InternalFrameTitlePaneUI", INTERNAL_FRAME_TITLE_PANE);
            map.put("LabelUI", LABEL);
            map.put("ListUI", LIST);
            map.put("MenuUI", MENU);
            map.put("MenuBarUI", MENU_BAR);
            map.put("MenuItemUI", MENU_ITEM);
            map.put("OptionPaneUI", OPTION_PANE);
            map.put("PanelUI", PANEL);
            map.put("PasswordFieldUI", PASSWORD_FIELD);
            map.put("PopupMenuUI", POPUP_MENU);
            map.put("PopupMenuSeparatorUI", POPUP_MENU_SEPARATOR);
            map.put("ProgressBarUI", PROGRESS_BAR);
            map.put("RadioButtonUI", RADIO_BUTTON);
            map.put("RadioButtonMenuItemUI", RADIO_BUTTON_MENU_ITEM);
            map.put("RootPaneUI", ROOT_PANE);
            map.put("ScrollBarUI", SCROLL_BAR);
            map.put("ScrollPaneUI", SCROLL_PANE);
            map.put("SeparatorUI", SEPARATOR);
            map.put("SliderUI", SLIDER);
            map.put("SpinnerUI", SPINNER);
            map.put("SplitPaneUI", SPLIT_PANE);
            map.put("TabbedPaneUI", TABBED_PANE);
            map.put("TableUI", TABLE);
            map.put("TableHeaderUI", TABLE_HEADER);
            map.put("TextAreaUI", TEXT_AREA);
            map.put("TextFieldUI", TEXT_FIELD);
            map.put("TextPaneUI", TEXT_PANE);
            map.put("ToggleButtonUI", TOGGLE_BUTTON);
            map.put("ToolBarUI", TOOL_BAR);
            map.put("ToolTipUI", TOOL_TIP);
            map.put("ToolBarSeparatorUI", TOOL_BAR_SEPARATOR);
            map.put("TreeUI", TREE);
            map.put("ViewportUI", VIEWPORT);
            context.put(UI_TO_REGION_MAP_KEY, map);
        }
        return map;
    }

    private static Map<Region, String> getLowerCaseNameMap() {
        AppContext context = AppContext.getAppContext();
        Map<Region, String> map = (Map<Region, String>) context.get(LOWER_CASE_NAME_MAP_KEY);
        if (map == null) {
            map = new HashMap<Region, String>();
            context.put(LOWER_CASE_NAME_MAP_KEY, map);
        }
        return map;
    }

    static Region getRegion(JComponent c) {
        return getUItoRegionMap().get(c.getUIClassID());
    }

    static void registerUIs(UIDefaults table) {
        for (Object key : getUItoRegionMap().keySet()) {
            table.put(key, "javax.swing.plaf.synth.SynthLookAndFeel");
        }
    }

    private final String name;
    private final boolean subregion;

    private Region(String name, boolean subregion) {
        if (name == null) {
            throw new NullPointerException("You must specify a non-null name");
        }
        this.name = name;
        this.subregion = subregion;
    }

    /**
     * Creates a Region with the specified name. This should only be
     * used if you are creating your own <code>JComponent</code> subclass
     * with a custom <code>ComponentUI</code> class.
     *
     * <p>
     *  创建具有指定名称的区域。只有在使用自定义<code> ComponentUI </code>类创建自己的<code> JComponent </code>子类时,才应使用此方法。
     * 
     * 
     * @param name Name of the region
     * @param ui String that will be returned from
     *           <code>component.getUIClassID</code>. This will be null
     *           if this is a subregion.
     * @param subregion Whether or not this is a subregion.
     */
    protected Region(String name, String ui, boolean subregion) {
        this(name, subregion);
        if (ui != null) {
            getUItoRegionMap().put(ui, this);
        }
    }

    /**
     * Returns true if the Region is a subregion of a Component, otherwise
     * false. For example, <code>Region.BUTTON</code> corresponds do a
     * <code>Component</code> so that <code>Region.BUTTON.isSubregion()</code>
     * returns false.
     *
     * <p>
     *  如果Region是Component的子区域,则返回true,否则返回false。
     * 例如,<code> Region.BUTTON </code>对应一个<code> Component </code>,这样<code> Region.BUTTON.isSubregion()</code>
     * 返回false。
     *  如果Region是Component的子区域,则返回true,否则返回false。
     * 
     * 
     * @return true if the Region is a subregion of a Component.
     */
    public boolean isSubregion() {
        return subregion;
    }

    /**
     * Returns the name of the region.
     *
     * <p>
     *  返回区域的名称。
     * 
     * 
     * @return name of the Region.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name, in lowercase.
     *
     * <p>
     *  返回小写的名称。
     * 
     * 
     * @return lower case representation of the name of the Region
     */
    String getLowerCaseName() {
        Map<Region, String> lowerCaseNameMap = getLowerCaseNameMap();
        String lowerCaseName = lowerCaseNameMap.get(this);
        if (lowerCaseName == null) {
            lowerCaseName = name.toLowerCase(Locale.ENGLISH);
            lowerCaseNameMap.put(this, lowerCaseName);
        }
        return lowerCaseName;
    }

    /**
     * Returns the name of the Region.
     *
     * <p>
     *  返回区域的名称。
     * 
     * @return name of the Region.
     */
    @Override
    public String toString() {
        return name;
    }
}
