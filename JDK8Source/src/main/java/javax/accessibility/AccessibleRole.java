/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <P>Class AccessibleRole determines the role of a component.  The role of a
 * component describes its generic function. (E.G.,
* "push button," "table," or "list.")
 * <p>The toDisplayString method allows you to obtain the localized string
 * for a locale independent key from a predefined ResourceBundle for the
 * keys defined in this class.
 * <p>The constants in this class present a strongly typed enumeration
 * of common object roles.  A public constructor for this class has been
 * purposely omitted and applications should use one of the constants
 * from this class.  If the constants in this class are not sufficient
 * to describe the role of an object, a subclass should be generated
 * from this class and it should provide constants in a similar manner.
 *
 * <p>
 *  <P> Class AccessibleRole确定组件的角色。组件的角色描述其通用功能。 (E.G.,"push button","table"或"list"。
 * )<p> toDisplayString方法允许从预定义的ResourceBundle中获取本类定义的键的本地化字符串。 <p>此类中的常量提供常见对象角色的强类型枚举。
 * 这个类的公共构造函数被有意地省略了,应用程序应该使用这个类中的一个常量。如果这个类中的常量不足以描述对象的角色,那么应该从这个类生成一个子类,并且它应该以类似的方式提供常量。
 * 
 * 
 * @author      Willie Walker
 * @author      Peter Korn
 * @author      Lynn Monsanto
 */
public class AccessibleRole extends AccessibleBundle {

// If you add or remove anything from here, make sure you
// update AccessibleResourceBundle.java.

    /**
     * Object is used to alert the user about something.
     * <p>
     *  对象用于警告用户某事。
     * 
     */
    public static final AccessibleRole ALERT
            = new AccessibleRole("alert");

    /**
     * The header for a column of data.
     * <p>
     *  数据列的标题。
     * 
     */
    public static final AccessibleRole COLUMN_HEADER
            = new AccessibleRole("columnheader");

    /**
     * Object that can be drawn into and is used to trap
     * events.
     * <p>
     *  可以绘制到并用于捕获事件的对象。
     * 
     * 
     * @see #FRAME
     * @see #GLASS_PANE
     * @see #LAYERED_PANE
     */
    public static final AccessibleRole CANVAS
            = new AccessibleRole("canvas");

    /**
     * A list of choices the user can select from.  Also optionally
     * allows the user to enter a choice of their own.
     * <p>
     *  用户可以选择的选项列表。还可选地允许用户输入他们自己的选择。
     * 
     */
    public static final AccessibleRole COMBO_BOX
            = new AccessibleRole("combobox");

    /**
     * An iconified internal frame in a DESKTOP_PANE.
     * <p>
     *  DESKTOP_PANE中的图标化内部框架。
     * 
     * 
     * @see #DESKTOP_PANE
     * @see #INTERNAL_FRAME
     */
    public static final AccessibleRole DESKTOP_ICON
            = new AccessibleRole("desktopicon");

    /**
     * An object containing a collection of <code>Accessibles</code> that
     * together represents <code>HTML</code> content.  The child
     * <code>Accessibles</code> would include objects implementing
     * <code>AccessibleText</code>, <code>AccessibleHypertext</code>,
     * <code>AccessibleIcon</code>, and other interfaces.
     * <p>
     *  一个包含<code> Accessibles </code>集合的对象,它们一起表示<code> HTML </code>内容。
     * 子<code> Accessibles </code>包括实现<code> AccessibleText </code>,<code> AccessibleHypertext </code>,<code>
     *  AccessibleIcon </code>和其他接口的对象。
     *  一个包含<code> Accessibles </code>集合的对象,它们一起表示<code> HTML </code>内容。
     * 
     * 
     * @see #HYPERLINK
     * @see AccessibleText
     * @see AccessibleHypertext
     * @see AccessibleHyperlink
     * @see AccessibleIcon
     * @since 1.6
     */
    public static final AccessibleRole HTML_CONTAINER
            = new AccessibleRole("htmlcontainer");

    /**
     * A frame-like object that is clipped by a desktop pane.  The
     * desktop pane, internal frame, and desktop icon objects are
     * often used to create multiple document interfaces within an
     * application.
     * <p>
     * 由桌面窗格裁剪的框架状对象。桌面窗格,内部框架和桌面图标对象通常用于在应用程序内创建多个文档界面。
     * 
     * 
     * @see #DESKTOP_ICON
     * @see #DESKTOP_PANE
     * @see #FRAME
     */
    public static final AccessibleRole INTERNAL_FRAME
            = new AccessibleRole("internalframe");

    /**
     * A pane that supports internal frames and
     * iconified versions of those internal frames.
     * <p>
     *  支持内部框架和这些内部框架的图标化版本的窗格。
     * 
     * 
     * @see #DESKTOP_ICON
     * @see #INTERNAL_FRAME
     */
    public static final AccessibleRole DESKTOP_PANE
            = new AccessibleRole("desktoppane");

    /**
     * A specialized pane whose primary use is inside a DIALOG
     * <p>
     *  主要用于DIALOG中的专用窗格
     * 
     * 
     * @see #DIALOG
     */
    public static final AccessibleRole OPTION_PANE
            = new AccessibleRole("optionpane");

    /**
     * A top level window with no title or border.
     * <p>
     *  没有标题或边框的顶级窗口。
     * 
     * 
     * @see #FRAME
     * @see #DIALOG
     */
    public static final AccessibleRole WINDOW
            = new AccessibleRole("window");

    /**
     * A top level window with a title bar, border, menu bar, etc.  It is
     * often used as the primary window for an application.
     * <p>
     *  具有标题栏,边框,菜单栏等的顶层窗口。它通常用作应用程序的主窗口。
     * 
     * 
     * @see #DIALOG
     * @see #CANVAS
     * @see #WINDOW
     */
    public static final AccessibleRole FRAME
            = new AccessibleRole("frame");

    /**
     * A top level window with title bar and a border.  A dialog is similar
     * to a frame, but it has fewer properties and is often used as a
     * secondary window for an application.
     * <p>
     *  顶部窗口带有标题栏和边框。对话框类似于框架,但它具有较少的属性,并且通常用作应用程序的辅助窗口。
     * 
     * 
     * @see #FRAME
     * @see #WINDOW
     */
    public static final AccessibleRole DIALOG
            = new AccessibleRole("dialog");

    /**
     * A specialized pane that lets the user choose a color.
     * <p>
     *  允许用户选择颜色的专用窗格。
     * 
     */
    public static final AccessibleRole COLOR_CHOOSER
            = new AccessibleRole("colorchooser");


    /**
     * A pane that allows the user to navigate through
     * and select the contents of a directory.  May be used
     * by a file chooser.
     * <p>
     *  允许用户浏览并选择目录内容的窗格。可以由文件选择器使用。
     * 
     * 
     * @see #FILE_CHOOSER
     */
    public static final AccessibleRole DIRECTORY_PANE
            = new AccessibleRole("directorypane");

    /**
     * A specialized dialog that displays the files in the directory
     * and lets the user select a file, browse a different directory,
     * or specify a filename.  May use the directory pane to show the
     * contents of a directory.
     * <p>
     *  显示目录中文件并允许用户选择文件,浏览不同目录或指定文件名的专用对话框。可以使用目录窗格显示目录的内容。
     * 
     * 
     * @see #DIRECTORY_PANE
     */
    public static final AccessibleRole FILE_CHOOSER
            = new AccessibleRole("filechooser");

    /**
     * An object that fills up space in a user interface.  It is often
     * used in interfaces to tweak the spacing between components,
     * but serves no other purpose.
     * <p>
     *  填充用户界面中的空间的对象。它通常用在接口中以调整组件之间的间距,但不用于其他目的。
     * 
     */
    public static final AccessibleRole FILLER
            = new AccessibleRole("filler");

    /**
     * A hypertext anchor
     * <p>
     *  超文本锚点
     * 
     */
    public static final AccessibleRole HYPERLINK
            = new AccessibleRole("hyperlink");

    /**
     * A small fixed size picture, typically used to decorate components.
     * <p>
     *  一个小的固定大小的图片,通常用于装饰组件。
     * 
     */
    public static final AccessibleRole ICON
            = new AccessibleRole("icon");

    /**
     * An object used to present an icon or short string in an interface.
     * <p>
     *  用于在界面中显示图标或短字符串的对象。
     * 
     */
    public static final AccessibleRole LABEL
            = new AccessibleRole("label");

    /**
     * A specialized pane that has a glass pane and a layered pane as its
     * children.
     * <p>
     *  一个专门的窗格,它有一个玻璃窗格和一个分层窗格作为其子窗体。
     * 
     * 
     * @see #GLASS_PANE
     * @see #LAYERED_PANE
     */
    public static final AccessibleRole ROOT_PANE
            = new AccessibleRole("rootpane");

    /**
     * A pane that is guaranteed to be painted on top
     * of all panes beneath it.
     * <p>
     * 确保在其下面的所有窗格的顶部上绘制的窗格。
     * 
     * 
     * @see #ROOT_PANE
     * @see #CANVAS
     */
    public static final AccessibleRole GLASS_PANE
            = new AccessibleRole("glasspane");

    /**
     * A specialized pane that allows its children to be drawn in layers,
     * providing a form of stacking order.  This is usually the pane that
     * holds the menu bar as well as the pane that contains most of the
     * visual components in a window.
     * <p>
     *  一个专门的窗格,允许它的孩子在层中绘制,提供一种形式的堆叠顺序。这通常是保存菜单栏的窗格以及窗口中包含大多数可视组件的窗格。
     * 
     * 
     * @see #GLASS_PANE
     * @see #ROOT_PANE
     */
    public static final AccessibleRole LAYERED_PANE
            = new AccessibleRole("layeredpane");

    /**
     * An object that presents a list of objects to the user and allows the
     * user to select one or more of them.  A list is usually contained
     * within a scroll pane.
     * <p>
     *  向用户呈现对象列表并允许用户选择其中一个或多个的对象。列表通常包含在滚动窗格中。
     * 
     * 
     * @see #SCROLL_PANE
     * @see #LIST_ITEM
     */
    public static final AccessibleRole LIST
            = new AccessibleRole("list");

    /**
     * An object that presents an element in a list.  A list is usually
     * contained within a scroll pane.
     * <p>
     *  在列表中呈现元素的对象。列表通常包含在滚动窗格中。
     * 
     * 
     * @see #SCROLL_PANE
     * @see #LIST
     */
    public static final AccessibleRole LIST_ITEM
            = new AccessibleRole("listitem");

    /**
     * An object usually drawn at the top of the primary dialog box of
     * an application that contains a list of menus the user can choose
     * from.  For example, a menu bar might contain menus for "File,"
     * "Edit," and "Help."
     * <p>
     *  通常在应用程序的主对话框的顶部绘制的对象,其中包含用户可以选择的菜单列表。例如,菜单栏可能包含"文件","编辑"和"帮助"的菜单。
     * 
     * 
     * @see #MENU
     * @see #POPUP_MENU
     * @see #LAYERED_PANE
     */
    public static final AccessibleRole MENU_BAR
            = new AccessibleRole("menubar");

    /**
     * A temporary window that is usually used to offer the user a
     * list of choices, and then hides when the user selects one of
     * those choices.
     * <p>
     *  一个临时窗口,通常用于向用户提供选择列表,然后在用户选择其中一个选项时隐藏。
     * 
     * 
     * @see #MENU
     * @see #MENU_ITEM
     */
    public static final AccessibleRole POPUP_MENU
            = new AccessibleRole("popupmenu");

    /**
     * An object usually found inside a menu bar that contains a list
     * of actions the user can choose from.  A menu can have any object
     * as its children, but most often they are menu items, other menus,
     * or rudimentary objects such as radio buttons, check boxes, or
     * separators.  For example, an application may have an "Edit" menu
     * that contains menu items for "Cut" and "Paste."
     * <p>
     *  通常在菜单栏中找到的一个对象,其中包含用户可以选择的操作列表。菜单可以有任何对象作为其子对象,但是最常见的是菜单项,其他菜单或基本对象,如单选按钮,复选框或分隔符。
     * 例如,应用可以具有包含用于"剪切"和"粘贴"的菜单项的"编辑"菜单。
     * 
     * 
     * @see #MENU_BAR
     * @see #MENU_ITEM
     * @see #SEPARATOR
     * @see #RADIO_BUTTON
     * @see #CHECK_BOX
     * @see #POPUP_MENU
     */
    public static final AccessibleRole MENU
            = new AccessibleRole("menu");

    /**
     * An object usually contained in a menu that presents an action
     * the user can choose.  For example, the "Cut" menu item in an
     * "Edit" menu would be an action the user can select to cut the
     * selected area of text in a document.
     * <p>
     * 通常包含在菜单中的对象,其呈现用户可以选择的动作。例如,"编辑"菜单中的"剪切"菜单项将是用户可以选择以剪切文档中的文本的所选区域的动作。
     * 
     * 
     * @see #MENU_BAR
     * @see #SEPARATOR
     * @see #POPUP_MENU
     */
    public static final AccessibleRole MENU_ITEM
            = new AccessibleRole("menuitem");

    /**
     * An object usually contained in a menu to provide a visual
     * and logical separation of the contents in a menu.  For example,
     * the "File" menu of an application might contain menu items for
     * "Open," "Close," and "Exit," and will place a separator between
     * "Close" and "Exit" menu items.
     * <p>
     *  通常包含在菜单中以提供菜单中的内容的视觉和逻辑分离的对象。例如,应用程序的"文件"菜单可能包含"打开","关闭"和"退出"的菜单项,并将在"关闭"和"退出"菜单项之间放置分隔符。
     * 
     * 
     * @see #MENU
     * @see #MENU_ITEM
     */
    public static final AccessibleRole SEPARATOR
            = new AccessibleRole("separator");

    /**
     * An object that presents a series of panels (or page tabs), one at a
     * time, through some mechanism provided by the object.  The most common
     * mechanism is a list of tabs at the top of the panel.  The children of
     * a page tab list are all page tabs.
     * <p>
     *  通过对象提供的某种机制,一次一个地呈现一系列面板(或页面选项卡)的对象。最常见的机制是面板顶部的选项卡列表。页面选项卡列表的子项都是页面选项卡。
     * 
     * 
     * @see #PAGE_TAB
     */
    public static final AccessibleRole PAGE_TAB_LIST
            = new AccessibleRole("pagetablist");

    /**
     * An object that is a child of a page tab list.  Its sole child is
     * the panel that is to be presented to the user when the user
     * selects the page tab from the list of tabs in the page tab list.
     * <p>
     *  作为页面选项卡列表的子项的对象。其唯一的孩子是当用户从页面选项卡列表中的选项卡列表中选择页面选项卡时要呈现给用户的面板。
     * 
     * 
     * @see #PAGE_TAB_LIST
     */
    public static final AccessibleRole PAGE_TAB
            = new AccessibleRole("pagetab");

    /**
     * A generic container that is often used to group objects.
     * <p>
     *  通常用于对对象进行分组的通用容器。
     * 
     */
    public static final AccessibleRole PANEL
            = new AccessibleRole("panel");

    /**
     * An object used to indicate how much of a task has been completed.
     * <p>
     *  用于指示任务已完成多少的对象。
     * 
     */
    public static final AccessibleRole PROGRESS_BAR
            = new AccessibleRole("progressbar");

    /**
     * A text object used for passwords, or other places where the
     * text contents is not shown visibly to the user
     * <p>
     *  用于密码的文本对象,或者文本内容未显示给用户的其他位置
     * 
     */
    public static final AccessibleRole PASSWORD_TEXT
            = new AccessibleRole("passwordtext");

    /**
     * An object the user can manipulate to tell the application to do
     * something.
     * <p>
     *  一个对象,用户可以操纵来告诉应用程序做某事。
     * 
     * 
     * @see #CHECK_BOX
     * @see #TOGGLE_BUTTON
     * @see #RADIO_BUTTON
     */
    public static final AccessibleRole PUSH_BUTTON
            = new AccessibleRole("pushbutton");

    /**
     * A specialized push button that can be checked or unchecked, but
     * does not provide a separate indicator for the current state.
     * <p>
     *  专门的按钮,可以选中或取消选中,但不提供当前状态的单独指示器。
     * 
     * 
     * @see #PUSH_BUTTON
     * @see #CHECK_BOX
     * @see #RADIO_BUTTON
     */
    public static final AccessibleRole TOGGLE_BUTTON
            = new AccessibleRole("togglebutton");

    /**
     * A choice that can be checked or unchecked and provides a
     * separate indicator for the current state.
     * <p>
     *  可以选中或取消选中的选项,并为当前状态提供单独的指示器。
     * 
     * 
     * @see #PUSH_BUTTON
     * @see #TOGGLE_BUTTON
     * @see #RADIO_BUTTON
     */
    public static final AccessibleRole CHECK_BOX
            = new AccessibleRole("checkbox");

    /**
     * A specialized check box that will cause other radio buttons in the
     * same group to become unchecked when this one is checked.
     * <p>
     * 当选中此复选框时,将导致同一组中的其他单选按钮变为未选中状态的专用复选框。
     * 
     * 
     * @see #PUSH_BUTTON
     * @see #TOGGLE_BUTTON
     * @see #CHECK_BOX
     */
    public static final AccessibleRole RADIO_BUTTON
            = new AccessibleRole("radiobutton");

    /**
     * The header for a row of data.
     * <p>
     *  一行数据的标题。
     * 
     */
    public static final AccessibleRole ROW_HEADER
            = new AccessibleRole("rowheader");

    /**
     * An object that allows a user to incrementally view a large amount
     * of information.  Its children can include scroll bars and a viewport.
     * <p>
     *  允许用户递增地查看大量信息的对象。它的孩子可以包括滚动条和视口。
     * 
     * 
     * @see #SCROLL_BAR
     * @see #VIEWPORT
     */
    public static final AccessibleRole SCROLL_PANE
            = new AccessibleRole("scrollpane");

    /**
     * An object usually used to allow a user to incrementally view a
     * large amount of data.  Usually used only by a scroll pane.
     * <p>
     *  通常用于允许用户逐步查看大量数据的对象。通常只由滚动窗格使用。
     * 
     * 
     * @see #SCROLL_PANE
     */
    public static final AccessibleRole SCROLL_BAR
            = new AccessibleRole("scrollbar");

    /**
     * An object usually used in a scroll pane.  It represents the portion
     * of the entire data that the user can see.  As the user manipulates
     * the scroll bars, the contents of the viewport can change.
     * <p>
     *  通常在滚动窗格中使用的对象。它表示用户可以看到的整个数据的部分。当用户操纵滚动条时,视口的内容可以改变。
     * 
     * 
     * @see #SCROLL_PANE
     */
    public static final AccessibleRole VIEWPORT
            = new AccessibleRole("viewport");

    /**
     * An object that allows the user to select from a bounded range.  For
     * example, a slider might be used to select a number between 0 and 100.
     * <p>
     *  允许用户从有界范围中进行选择的对象。例如,可以使用滑块来选择0和100之间的数字。
     * 
     */
    public static final AccessibleRole SLIDER
            = new AccessibleRole("slider");

    /**
     * A specialized panel that presents two other panels at the same time.
     * Between the two panels is a divider the user can manipulate to make
     * one panel larger and the other panel smaller.
     * <p>
     *  一个专门的面板,同时提供两个其他面板。在两个面板之间是分隔器,用户可以操纵以使一个面板更大而另一个面板更小。
     * 
     */
    public static final AccessibleRole SPLIT_PANE
            = new AccessibleRole("splitpane");

    /**
     * An object used to present information in terms of rows and columns.
     * An example might include a spreadsheet application.
     * <p>
     *  用于按行和列显示信息的对象。示例可能包括电子表格应用程序。
     * 
     */
    public static final AccessibleRole TABLE
            = new AccessibleRole("table");

    /**
     * An object that presents text to the user.  The text is usually
     * editable by the user as opposed to a label.
     * <p>
     *  向用户呈现文本的对象。文本通常由用户编辑,而不是标签。
     * 
     * 
     * @see #LABEL
     */
    public static final AccessibleRole TEXT
            = new AccessibleRole("text");

    /**
     * An object used to present hierarchical information to the user.
     * The individual nodes in the tree can be collapsed and expanded
     * to provide selective disclosure of the tree's contents.
     * <p>
     *  用于向用户呈现分层信息的对象。树中的各个节点可以被折叠和扩展以提供树的内容的选择性公开。
     * 
     */
    public static final AccessibleRole TREE
            = new AccessibleRole("tree");

    /**
     * A bar or palette usually composed of push buttons or toggle buttons.
     * It is often used to provide the most frequently used functions for an
     * application.
     * <p>
     *  通常由按钮或切换按钮组成的栏或调色板。它通常用于为应用程序提供最常用的函数。
     * 
     */
    public static final AccessibleRole TOOL_BAR
            = new AccessibleRole("toolbar");

    /**
     * An object that provides information about another object.  The
     * accessibleDescription property of the tool tip is often displayed
     * to the user in a small "help bubble" when the user causes the
     * mouse to hover over the object associated with the tool tip.
     * <p>
     * 提供有关另一个对象的信息的对象。当用户使鼠标悬停在与工具提示相关联的对象上时,通常在小的"帮助气泡"中向用户显示工具提示的accessibleDescription属性。
     * 
     */
    public static final AccessibleRole TOOL_TIP
            = new AccessibleRole("tooltip");

    /**
     * An AWT component, but nothing else is known about it.
     * <p>
     *  一个AWT组件,但没有其他的知道它。
     * 
     * 
     * @see #SWING_COMPONENT
     * @see #UNKNOWN
     */
    public static final AccessibleRole AWT_COMPONENT
            = new AccessibleRole("awtcomponent");

    /**
     * A Swing component, but nothing else is known about it.
     * <p>
     *  一个Swing组件,但没有什么是知道的。
     * 
     * 
     * @see #AWT_COMPONENT
     * @see #UNKNOWN
     */
    public static final AccessibleRole SWING_COMPONENT
            = new AccessibleRole("swingcomponent");

    /**
     * The object contains some Accessible information, but its role is
     * not known.
     * <p>
     *  该对象包含一些可访问的信息,但其角色未知。
     * 
     * 
     * @see #AWT_COMPONENT
     * @see #SWING_COMPONENT
     */
    public static final AccessibleRole UNKNOWN
            = new AccessibleRole("unknown");

    /**
     * A STATUS_BAR is an simple component that can contain
     * multiple labels of status information to the user.
     * <p>
     *  STATUS_BAR是一个简单的组件,可以为用户包含多个状态信息标签。
     * 
     */
    public static final AccessibleRole STATUS_BAR
        = new AccessibleRole("statusbar");

    /**
     * A DATE_EDITOR is a component that allows users to edit
     * java.util.Date and java.util.Time objects
     * <p>
     *  DATE_EDITOR是一个组件,允许用户编辑java.util.Date和java.util.Time对象
     * 
     */
    public static final AccessibleRole DATE_EDITOR
        = new AccessibleRole("dateeditor");

    /**
     * A SPIN_BOX is a simple spinner component and its main use
     * is for simple numbers.
     * <p>
     *  SPIN_BOX是一个简单的旋转器组件,它的主要用途是简单的数字。
     * 
     */
    public static final AccessibleRole SPIN_BOX
        = new AccessibleRole("spinbox");

    /**
     * A FONT_CHOOSER is a component that lets the user pick various
     * attributes for fonts.
     * <p>
     *  FONT_CHOOSER是一个组件,允许用户选择字体的各种属性。
     * 
     */
    public static final AccessibleRole FONT_CHOOSER
        = new AccessibleRole("fontchooser");

    /**
     * A GROUP_BOX is a simple container that contains a border
     * around it and contains components inside it.
     * <p>
     *  GROUP_BOX是一个简单的容器,其周围包含边框,并且包含其中的组件。
     * 
     */
    public static final AccessibleRole GROUP_BOX
        = new AccessibleRole("groupbox");

    /**
     * A text header
     *
     * <p>
     *  文本标题
     * 
     * 
     * @since 1.5
     */
    public static final AccessibleRole HEADER =
        new AccessibleRole("header");

    /**
     * A text footer
     *
     * <p>
     *  文本页脚
     * 
     * 
     * @since 1.5
     */
    public static final AccessibleRole FOOTER =
        new AccessibleRole("footer");

    /**
     * A text paragraph
     *
     * <p>
     *  文本段落
     * 
     * 
     * @since 1.5
     */
    public static final AccessibleRole PARAGRAPH =
        new AccessibleRole("paragraph");

    /**
     * A ruler is an object used to measure distance
     *
     * <p>
     *  标尺是用于测量距离的对象
     * 
     * 
     * @since 1.5
     */
    public static final AccessibleRole RULER =
        new AccessibleRole("ruler");

    /**
     * A role indicating the object acts as a formula for
     * calculating a value.  An example is a formula in
     * a spreadsheet cell.
     *
     * <p>
     *  指示对象的角色用作用于计算值的公式。示例是电子表格单元格中的公式。
     * 
     * 
     * @since 1.5
     */
    static public final AccessibleRole EDITBAR =
        new AccessibleRole("editbar");

    /**
     * A role indicating the object monitors the progress
     * of some operation.
     *
     * <p>
     *  指示对象的角色监视某些操作的进度。
     * 
     * 
     * @since 1.5
     */
    static public final AccessibleRole PROGRESS_MONITOR =
        new AccessibleRole("progressMonitor");


// The following are all under consideration for potential future use.

//    public static final AccessibleRole APPLICATION
//            = new AccessibleRole("application");

//    public static final AccessibleRole BORDER
//            = new AccessibleRole("border");

//    public static final AccessibleRole CHECK_BOX_MENU_ITEM
//            = new AccessibleRole("checkboxmenuitem");

//    public static final AccessibleRole CHOICE
//            = new AccessibleRole("choice");

//    public static final AccessibleRole COLUMN
//            = new AccessibleRole("column");

//    public static final AccessibleRole CURSOR
//            = new AccessibleRole("cursor");

//    public static final AccessibleRole DOCUMENT
//            = new AccessibleRole("document");

//    public static final AccessibleRole IMAGE
//            = new AccessibleRole("Image");

//    public static final AccessibleRole INDICATOR
//            = new AccessibleRole("indicator");

//    public static final AccessibleRole RADIO_BUTTON_MENU_ITEM
//            = new AccessibleRole("radiobuttonmenuitem");

//    public static final AccessibleRole ROW
//            = new AccessibleRole("row");

//    public static final AccessibleRole TABLE_CELL
//          = new AccessibleRole("tablecell");

//    public static final AccessibleRole TREE_NODE
//            = new AccessibleRole("treenode");

    /**
     * Creates a new AccessibleRole using the given locale independent key.
     * This should not be a public method.  Instead, it is used to create
     * the constants in this file to make it a strongly typed enumeration.
     * Subclasses of this class should enforce similar policy.
     * <p>
     * The key String should be a locale independent key for the role.
     * It is not intended to be used as the actual String to display
     * to the user.  To get the localized string, use toDisplayString.
     *
     * <p>
     *  使用给定的区域设置独立键创建一个新的AccessibleRole。这不应该是一个公共方法。相反,它用于在此文件中创建常量,使其成为强类型枚举。这个类的子类应该实施类似的策略。
     * <p>
     * 
     * @param key the locale independent name of the role.
     * @see AccessibleBundle#toDisplayString
     */
    protected AccessibleRole(String key) {
        this.key = key;
    }
}
