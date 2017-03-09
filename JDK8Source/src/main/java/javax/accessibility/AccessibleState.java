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

package javax.accessibility;

import java.util.Vector;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <P>Class AccessibleState describes a component's particular state.  The actual
 * state of the component is defined as an AccessibleStateSet, which is a
 * composed set of AccessibleStates.
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
 *  <P> Class AccessibleState描述组件的特定状态。组件的实际状态被定义为AccessibleStateSet,它是一组组合的AccessibleStates。
 *  <p> toDisplayString方法允许您从此类中定义的键的预定义ResourceBundle中获取本地化的字符串。 <p>此类中的常量提供常见对象角色的强类型枚举。
 * 这个类的公共构造函数被有意地省略了,应用程序应该使用这个类中的一个常量。如果这个类中的常量不足以描述对象的角色,那么应该从这个类生成一个子类,并且它应该以类似的方式提供常量。
 * 
 * 
 * @author      Willie Walker
 * @author      Peter Korn
 */
public class AccessibleState extends AccessibleBundle {

    // If you add or remove anything from here, make sure you
    // update AccessibleResourceBundle.java.

    /**
     * Indicates a window is currently the active window.  This includes
     * windows, dialogs, frames, etc.  In addition, this state is used
     * to indicate the currently active child of a component such as a
     * list, table, or tree.  For example, the active child of a list
     * is the child that is drawn with a rectangle around it.
     * <p>
     *  表示窗口当前是活动窗口。这包括窗口,对话框,框架等。此外,此状态用于指示组件的当前活动子项,例如列表,表或树。例如,列表的活动子项是用它周围的矩形绘制的子项。
     * 
     * 
     * @see AccessibleRole#WINDOW
     * @see AccessibleRole#FRAME
     * @see AccessibleRole#DIALOG
     */
    public static final AccessibleState ACTIVE
            = new AccessibleState("active");

    /**
     * Indicates this object is currently pressed.  This is usually
     * associated with buttons and indicates the user has pressed a
     * mouse button while the pointer was over the button and has
     * not yet released the mouse button.
     * <p>
     *  表示此对象当前被按下。这通常与按钮相关联,并指示用户在指针在按钮上方并且尚未释放鼠标按钮的同时按下鼠标按钮。
     * 
     * 
     * @see AccessibleRole#PUSH_BUTTON
     */
    public static final AccessibleState PRESSED
            = new AccessibleState("pressed");

    /**
     * Indicates that the object is armed.  This is usually used on buttons
     * that have been pressed but not yet released, and the mouse pointer
     * is still over the button.
     * <p>
     * 表示对象已布防。这通常用于已按下但尚未释放的按钮,鼠标指针仍在按钮上方。
     * 
     * 
     * @see AccessibleRole#PUSH_BUTTON
     */
    public static final AccessibleState ARMED
            = new AccessibleState("armed");

    /**
     * Indicates the current object is busy.  This is usually used on objects
     * such as progress bars, sliders, or scroll bars to indicate they are
     * in a state of transition.
     * <p>
     *  表示当前对象正忙。这通常用于对象,如进度条,滑块或滚动条,以指示它们处于过渡状态。
     * 
     * 
     * @see AccessibleRole#PROGRESS_BAR
     * @see AccessibleRole#SCROLL_BAR
     * @see AccessibleRole#SLIDER
     */
    public static final AccessibleState BUSY
            = new AccessibleState("busy");

    /**
     * Indicates this object is currently checked.  This is usually used on
     * objects such as toggle buttons, radio buttons, and check boxes.
     * <p>
     *  表示当前选中此对象。这通常用于对象,如切换按钮,单选按钮和复选框。
     * 
     * 
     * @see AccessibleRole#TOGGLE_BUTTON
     * @see AccessibleRole#RADIO_BUTTON
     * @see AccessibleRole#CHECK_BOX
     */
    public static final AccessibleState CHECKED
            = new AccessibleState("checked");

    /**
     * Indicates the user can change the contents of this object.  This
     * is usually used primarily for objects that allow the user to
     * enter text.  Other objects, such as scroll bars and sliders,
     * are automatically editable if they are enabled.
     * <p>
     *  表示用户可以更改此对象的内容。这通常主要用于允许用户输入文本的对象。其他对象(如滚动条和滑块)在启用时可自动编辑。
     * 
     * 
     * @see #ENABLED
     */
    public static final AccessibleState EDITABLE
            = new AccessibleState("editable");

    /**
     * Indicates this object allows progressive disclosure of its children.
     * This is usually used with hierarchical objects such as trees and
     * is often paired with the EXPANDED or COLLAPSED states.
     * <p>
     *  表示此对象允许渐进式披露其子项。这通常与分层对象(如树)一起使用,并且通常与EXPANDED或COLLAPSED状态配对。
     * 
     * 
     * @see #EXPANDED
     * @see #COLLAPSED
     * @see AccessibleRole#TREE
     */
    public static final AccessibleState EXPANDABLE
            = new AccessibleState("expandable");

    /**
     * Indicates this object is collapsed.  This is usually paired with the
     * EXPANDABLE state and is used on objects that provide progressive
     * disclosure such as trees.
     * <p>
     *  表示此对象已折叠。这通常与EXPANDABLE状态配对,并用于提供渐进式披露的对象(如树)。
     * 
     * 
     * @see #EXPANDABLE
     * @see #EXPANDED
     * @see AccessibleRole#TREE
     */
    public static final AccessibleState COLLAPSED
            = new AccessibleState("collapsed");

    /**
     * Indicates this object is expanded.  This is usually paired with the
     * EXPANDABLE state and is used on objects that provide progressive
     * disclosure such as trees.
     * <p>
     *  表示此对象已展开。这通常与EXPANDABLE状态配对,并用于提供渐进式披露的对象(如树)。
     * 
     * 
     * @see #EXPANDABLE
     * @see #COLLAPSED
     * @see AccessibleRole#TREE
     */
    public static final AccessibleState EXPANDED
            = new AccessibleState("expanded");

    /**
     * Indicates this object is enabled.  The absence of this state from an
     * object's state set indicates this object is not enabled.  An object
     * that is not enabled cannot be manipulated by the user.  In a graphical
     * display, it is usually grayed out.
     * <p>
     *  表示此对象已启用。对象状态集中缺少此状态表示此对象未启用。未启用的对象不能由用户操作。在图形显示中,通常显示为灰色。
     * 
     */
    public static final AccessibleState ENABLED
            = new AccessibleState("enabled");

    /**
     * Indicates this object can accept keyboard focus, which means all
     * events resulting from typing on the keyboard will normally be
     * passed to it when it has focus.
     * <p>
     * 表示此对象可以接受键盘焦点,这意味着在键盘上键入时产生的所有事件通常会在它有焦点时传递给它。
     * 
     * 
     * @see #FOCUSED
     */
    public static final AccessibleState FOCUSABLE
            = new AccessibleState("focusable");

    /**
     * Indicates this object currently has the keyboard focus.
     * <p>
     *  表示此对象当前具有键盘焦点。
     * 
     * 
     * @see #FOCUSABLE
     */
    public static final AccessibleState FOCUSED
            = new AccessibleState("focused");

    /**
     * Indicates this object is minimized and is represented only by an
     * icon.  This is usually only associated with frames and internal
     * frames.
     * <p>
     *  表示此对象已最小化,仅由图标表示。这通常只与帧和内部帧相关联。
     * 
     * 
     * @see AccessibleRole#FRAME
     * @see AccessibleRole#INTERNAL_FRAME
     */
    public static final AccessibleState ICONIFIED
            = new AccessibleState("iconified");

    /**
     * Indicates something must be done with this object before the
     * user can interact with an object in a different window.  This
     * is usually associated only with dialogs.
     * <p>
     *  指示在用户可以与不同窗口中的对象交互之前,必须对此对象执行某项操作。这通常只与对话框相关联。
     * 
     * 
     * @see AccessibleRole#DIALOG
     */
    public static final AccessibleState MODAL
            = new AccessibleState("modal");

    /**
     * Indicates this object paints every pixel within its
     * rectangular region. A non-opaque component paints only some of
     * its pixels, allowing the pixels underneath it to "show through".
     * A component that does not fully paint its pixels therefore
     * provides a degree of transparency.
     * <p>
     *  指示此对象绘制其矩形区域内的每个像素。非不透明组件仅绘制其某些像素,从而允许其下方的像素"透过"。因此,不完全涂覆其像素的组件提供一定程度的透明度。
     * 
     * 
     * @see Accessible#getAccessibleContext
     * @see AccessibleContext#getAccessibleComponent
     * @see AccessibleComponent#getBounds
     */
    public static final AccessibleState OPAQUE
            = new AccessibleState("opaque");

    /**
     * Indicates the size of this object is not fixed.
     * <p>
     *  表示此对象的大小不固定。
     * 
     * 
     * @see Accessible#getAccessibleContext
     * @see AccessibleContext#getAccessibleComponent
     * @see AccessibleComponent#getSize
     * @see AccessibleComponent#setSize
     */
    public static final AccessibleState RESIZABLE
            = new AccessibleState("resizable");


    /**
     * Indicates this object allows more than one of its children to
     * be selected at the same time.
     * <p>
     *  表示此对象允许同时选择其多个子代。
     * 
     * 
     * @see Accessible#getAccessibleContext
     * @see AccessibleContext#getAccessibleSelection
     * @see AccessibleSelection
     */
    public static final AccessibleState MULTISELECTABLE
            = new AccessibleState("multiselectable");

    /**
     * Indicates this object is the child of an object that allows its
     * children to be selected, and that this child is one of those
     * children that can be selected.
     * <p>
     *  表示此对象是允许选择其子项的对象的子项,并且此子项是可以选择的那些子项之一。
     * 
     * 
     * @see #SELECTED
     * @see Accessible#getAccessibleContext
     * @see AccessibleContext#getAccessibleSelection
     * @see AccessibleSelection
     */
    public static final AccessibleState SELECTABLE
            = new AccessibleState("selectable");

    /**
     * Indicates this object is the child of an object that allows its
     * children to be selected, and that this child is one of those
     * children that has been selected.
     * <p>
     *  表示此对象是允许选择其子项的对象的子项,并且此子项是已选择的那些子项之一。
     * 
     * 
     * @see #SELECTABLE
     * @see Accessible#getAccessibleContext
     * @see AccessibleContext#getAccessibleSelection
     * @see AccessibleSelection
     */
    public static final AccessibleState SELECTED
            = new AccessibleState("selected");

    /**
     * Indicates this object, the object's parent, the object's parent's
     * parent, and so on, are all visible.  Note that this does not
     * necessarily mean the object is painted on the screen.  It might
     * be occluded by some other showing object.
     * <p>
     *  表示此对象,对象的父对象,对象的父对象的父对象等都是可见的。注意,这并不一定意味着对象被绘制在屏幕上。它可能被一些其他显示对象遮挡。
     * 
     * 
     * @see #VISIBLE
     */
    public static final AccessibleState SHOWING
            = new AccessibleState("showing");

    /**
     * Indicates this object is visible.  Note: this means that the
     * object intends to be visible; however, it may not in fact be
     * showing on the screen because one of the objects that this object
     * is contained by is not visible.
     * <p>
     * 表示此对象可见。注意：这意味着对象是可见的;然而,它可能实际上不在屏幕上显示,因为包含该对象的对象之一是不可见的。
     * 
     * 
     * @see #SHOWING
     */
    public static final AccessibleState VISIBLE
            = new AccessibleState("visible");

    /**
     * Indicates the orientation of this object is vertical.  This is
     * usually associated with objects such as scrollbars, sliders, and
     * progress bars.
     * <p>
     *  表示此对象的方向是垂直的。这通常与诸如滚动条,滑块和进度条之类的对象相关联。
     * 
     * 
     * @see #VERTICAL
     * @see AccessibleRole#SCROLL_BAR
     * @see AccessibleRole#SLIDER
     * @see AccessibleRole#PROGRESS_BAR
     */
    public static final AccessibleState VERTICAL
            = new AccessibleState("vertical");

    /**
     * Indicates the orientation of this object is horizontal.  This is
     * usually associated with objects such as scrollbars, sliders, and
     * progress bars.
     * <p>
     *  表示此对象的方向为水平。这通常与诸如滚动条,滑块和进度条之类的对象相关联。
     * 
     * 
     * @see #HORIZONTAL
     * @see AccessibleRole#SCROLL_BAR
     * @see AccessibleRole#SLIDER
     * @see AccessibleRole#PROGRESS_BAR
     */
    public static final AccessibleState HORIZONTAL
            = new AccessibleState("horizontal");

    /**
     * Indicates this (text) object can contain only a single line of text
     * <p>
     *  表示此(文本)对象只能包含一行文本
     * 
     */
    public static final AccessibleState SINGLE_LINE
            = new AccessibleState("singleline");

    /**
     * Indicates this (text) object can contain multiple lines of text
     * <p>
     *  表示此(文本)对象可以包含多行文本
     * 
     */
    public static final AccessibleState MULTI_LINE
            = new AccessibleState("multiline");

    /**
     * Indicates this object is transient.  An assistive technology should
     * not add a PropertyChange listener to an object with transient state,
     * as that object will never generate any events.  Transient objects
     * are typically created to answer Java Accessibility method queries,
     * but otherwise do not remain linked to the underlying object (for
     * example, those objects underneath lists, tables, and trees in Swing,
     * where only one actual UI Component does shared rendering duty for
     * all of the data objects underneath the actual list/table/tree elements).
     *
     * <p>
     *  表示此对象是临时的。辅助技术不应该向具有过渡状态的对象添加PropertyChange监听器,因为该对象永远不会生成任何事件。
     * 临时对象通常被创建为回答Java辅助功能方法查询,但是不会保持链接到基础对象(例如,在Swing中的列表,表和树下面的那些对象,其中只有一个实际的UI组件共享所有的实际列表/表/树元素下面的数据对象)。
     *  表示此对象是临时的。辅助技术不应该向具有过渡状态的对象添加PropertyChange监听器,因为该对象永远不会生成任何事件。
     * 
     * 
     * @since 1.5
     *
     */
    public static final AccessibleState TRANSIENT
            = new AccessibleState("transient");

    /**
     * Indicates this object is responsible for managing its
     * subcomponents.  This is typically used for trees and tables
     * that have a large number of subcomponents and where the
     * objects are created only when needed and otherwise remain virtual.
     * The application should not manage the subcomponents directly.
     *
     * <p>
     *  表示此对象负责管理其子组件。这通常用于具有大量子组件的树和表,并且其中仅当需要时创建对象,否则保持虚拟。应用程序不应该直接管理子组件。
     * 
     * 
     * @since 1.5
     */
    public static final AccessibleState MANAGES_DESCENDANTS
            = new AccessibleState ("managesDescendants");

    /**
     * Indicates that the object state is indeterminate.  An example
     * is selected text that is partially bold and partially not
     * bold. In this case the attributes associated with the selected
     * text are indeterminate.
     *
     * <p>
     * 表示对象状态为不确定。示例是所选文本部分粗体,部分不粗体。在这种情况下,与所选文本相关联的属性是不确定的。
     * 
     * 
     * @since 1.5
     */
    public static final AccessibleState INDETERMINATE
           = new AccessibleState ("indeterminate");

    /**
     * A state indicating that text is truncated by a bounding rectangle
     * and that some of the text is not displayed on the screen.  An example
     * is text in a spreadsheet cell that is truncated by the bounds of
     * the cell.
     *
     * <p>
     *  指示文本被边界矩形截断并且某些文本未显示在屏幕上的状态。示例是电子表格单元格中由单元格的边界截断的文本。
     * 
     * 
     * @since 1.5
     */
    static public final AccessibleState TRUNCATED
           =  new AccessibleState("truncated");

    /**
     * Creates a new AccessibleState using the given locale independent key.
     * This should not be a public method.  Instead, it is used to create
     * the constants in this file to make it a strongly typed enumeration.
     * Subclasses of this class should enforce similar policy.
     * <p>
     * The key String should be a locale independent key for the state.
     * It is not intended to be used as the actual String to display
     * to the user.  To get the localized string, use toDisplayString.
     *
     * <p>
     *  使用给定的区域设置独立键创建一个新的AccessibleState。这不应该是一个公共方法。相反,它用于在此文件中创建常量,使其成为强类型枚举。这个类的子类应该实施类似的策略。
     * <p>
     * 
     * @param key the locale independent name of the state.
     * @see AccessibleBundle#toDisplayString
     */
    protected AccessibleState(String key) {
        this.key = key;
    }
}
