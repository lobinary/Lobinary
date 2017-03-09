/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.event.*;
import java.io.*;

/**
 * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
 * available only for backwards compatibility.  It has been replaced
 * by the <code>AWTEvent</code> class and its subclasses.
 * <p>
 * <code>Event</code> is a platform-independent class that
 * encapsulates events from the platform's Graphical User
 * Interface in the Java&nbsp;1.0 event model. In Java&nbsp;1.1
 * and later versions, the <code>Event</code> class is maintained
 * only for backwards compatibility. The information in this
 * class description is provided to assist programmers in
 * converting Java&nbsp;1.0 programs to the new event model.
 * <p>
 * In the Java&nbsp;1.0 event model, an event contains an
 * {@link Event#id} field
 * that indicates what type of event it is and which other
 * <code>Event</code> variables are relevant for the event.
 * <p>
 * For keyboard events, {@link Event#key}
 * contains a value indicating which key was activated, and
 * {@link Event#modifiers} contains the
 * modifiers for that event.  For the KEY_PRESS and KEY_RELEASE
 * event ids, the value of <code>key</code> is the unicode
 * character code for the key. For KEY_ACTION and
 * KEY_ACTION_RELEASE, the value of <code>key</code> is
 * one of the defined action-key identifiers in the
 * <code>Event</code> class (<code>PGUP</code>,
 * <code>PGDN</code>, <code>F1</code>, <code>F2</code>, etc).
 *
 * <p>
 *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
 * <p>
 *  <code> Event </code>是一个独立于平台的类,用于封装来自Java 1.0事件模型中平台的图形用户界面的事件。
 * 在Java 1.1及更高版本中,仅保留<code> Event </code>类以实现向后兼容性。此类描述中的信息用于帮助程序员将Java&nbsp; 1.0程序转换为新的事件模型。
 * <p>
 *  在Java&nbsp; 1.0事件模型中,事件包含一个{@link Event#id}字段,该字段指示它是什么类型的事件以及与事件相关的其他<code> Event </code>变量。
 * <p>
 *  对于键盘事件,{@link Event#key}包含一个值,指示哪个键已激活,{@link Event#modifiers}包含该事件的修饰符。
 * 对于KEY_PRESS和KEY_RELEASE事件id,<code> key </code>的值是键的Unicode字符代码。
 * 对于KEY_ACTION和KEY_ACTION_RELEASE,<code> key </code>的值是<code> Event </code>类(<code> PGUP </code>,<code>
 *  PGDN)中定义的操作键标识符之一</code>,<code> F1 </code>,<code> F2 </code>等)。
 * 对于KEY_PRESS和KEY_RELEASE事件id,<code> key </code>的值是键的Unicode字符代码。
 * 
 * 
 * @author     Sami Shaio
 * @since      JDK1.0
 */
public class Event implements java.io.Serializable {
    private transient long data;

    /* Modifier constants */

    /**
     * This flag indicates that the Shift key was down when the event
     * occurred.
     * <p>
     * 此标志表示在事件发生时Shift键已关闭。
     * 
     */
    public static final int SHIFT_MASK          = 1 << 0;

    /**
     * This flag indicates that the Control key was down when the event
     * occurred.
     * <p>
     *  此标志表示事件发生时控制键已关闭。
     * 
     */
    public static final int CTRL_MASK           = 1 << 1;

    /**
     * This flag indicates that the Meta key was down when the event
     * occurred. For mouse events, this flag indicates that the right
     * button was pressed or released.
     * <p>
     *  此标志指示事件发生时Meta键已关闭。对于鼠标事件,此标志指示右按钮被按下或释放。
     * 
     */
    public static final int META_MASK           = 1 << 2;

    /**
     * This flag indicates that the Alt key was down when
     * the event occurred. For mouse events, this flag indicates that the
     * middle mouse button was pressed or released.
     * <p>
     *  此标志表示在事件发生时Alt键关闭。对于鼠标事件,此标志表示鼠标中键被按下或释放。
     * 
     */
    public static final int ALT_MASK            = 1 << 3;

    /* Action keys */

    /**
     * The Home key, a non-ASCII action key.
     * <p>
     *  Home键,一个非ASCII动作键。
     * 
     */
    public static final int HOME                = 1000;

    /**
     * The End key, a non-ASCII action key.
     * <p>
     *  结束键,非ASCII动作键。
     * 
     */
    public static final int END                 = 1001;

    /**
     * The Page Up key, a non-ASCII action key.
     * <p>
     *  Page Up键,非ASCII动作键。
     * 
     */
    public static final int PGUP                = 1002;

    /**
     * The Page Down key, a non-ASCII action key.
     * <p>
     *  Page Down键,非ASCII动作键。
     * 
     */
    public static final int PGDN                = 1003;

    /**
     * The Up Arrow key, a non-ASCII action key.
     * <p>
     *  向上箭头键,一个非ASCII动作键。
     * 
     */
    public static final int UP                  = 1004;

    /**
     * The Down Arrow key, a non-ASCII action key.
     * <p>
     *  向下箭头键,非ASCII动作键。
     * 
     */
    public static final int DOWN                = 1005;

    /**
     * The Left Arrow key, a non-ASCII action key.
     * <p>
     *  左箭头键,非ASCII动作键。
     * 
     */
    public static final int LEFT                = 1006;

    /**
     * The Right Arrow key, a non-ASCII action key.
     * <p>
     *  右箭头键,非ASCII动作键。
     * 
     */
    public static final int RIGHT               = 1007;

    /**
     * The F1 function key, a non-ASCII action key.
     * <p>
     *  F1功能键,非ASCII动作键。
     * 
     */
    public static final int F1                  = 1008;

    /**
     * The F2 function key, a non-ASCII action key.
     * <p>
     *  F2功能键,非ASCII动作键。
     * 
     */
    public static final int F2                  = 1009;

    /**
     * The F3 function key, a non-ASCII action key.
     * <p>
     *  F3功能键,非ASCII动作键。
     * 
     */
    public static final int F3                  = 1010;

    /**
     * The F4 function key, a non-ASCII action key.
     * <p>
     *  F4功能键,非ASCII动作键。
     * 
     */
    public static final int F4                  = 1011;

    /**
     * The F5 function key, a non-ASCII action key.
     * <p>
     *  F5功能键,非ASCII动作键。
     * 
     */
    public static final int F5                  = 1012;

    /**
     * The F6 function key, a non-ASCII action key.
     * <p>
     *  F6功能键,非ASCII动作键。
     * 
     */
    public static final int F6                  = 1013;

    /**
     * The F7 function key, a non-ASCII action key.
     * <p>
     *  F7功能键,非ASCII动作键。
     * 
     */
    public static final int F7                  = 1014;

    /**
     * The F8 function key, a non-ASCII action key.
     * <p>
     *  F8功能键,非ASCII动作键。
     * 
     */
    public static final int F8                  = 1015;

    /**
     * The F9 function key, a non-ASCII action key.
     * <p>
     *  F9功能键,非ASCII动作键。
     * 
     */
    public static final int F9                  = 1016;

    /**
     * The F10 function key, a non-ASCII action key.
     * <p>
     *  F10功能键,非ASCII动作键。
     * 
     */
    public static final int F10                 = 1017;

    /**
     * The F11 function key, a non-ASCII action key.
     * <p>
     *  F11功能键,非ASCII动作键。
     * 
     */
    public static final int F11                 = 1018;

    /**
     * The F12 function key, a non-ASCII action key.
     * <p>
     *  F12功能键,非ASCII动作键。
     * 
     */
    public static final int F12                 = 1019;

    /**
     * The Print Screen key, a non-ASCII action key.
     * <p>
     *  打印屏幕键,非ASCII动作键。
     * 
     */
    public static final int PRINT_SCREEN        = 1020;

    /**
     * The Scroll Lock key, a non-ASCII action key.
     * <p>
     *  Scroll Lock键,非ASCII动作键。
     * 
     */
    public static final int SCROLL_LOCK         = 1021;

    /**
     * The Caps Lock key, a non-ASCII action key.
     * <p>
     * Caps Lock键,非ASCII动作键。
     * 
     */
    public static final int CAPS_LOCK           = 1022;

    /**
     * The Num Lock key, a non-ASCII action key.
     * <p>
     *  Num Lock键,非ASCII动作键。
     * 
     */
    public static final int NUM_LOCK            = 1023;

    /**
     * The Pause key, a non-ASCII action key.
     * <p>
     *  暂停键,非ASCII动作键。
     * 
     */
    public static final int PAUSE               = 1024;

    /**
     * The Insert key, a non-ASCII action key.
     * <p>
     *  Insert键,非ASCII动作键。
     * 
     */
    public static final int INSERT              = 1025;

    /* Non-action keys */

    /**
     * The Enter key.
     * <p>
     *  Enter键。
     * 
     */
    public static final int ENTER               = '\n';

    /**
     * The BackSpace key.
     * <p>
     *  BackSpace键。
     * 
     */
    public static final int BACK_SPACE          = '\b';

    /**
     * The Tab key.
     * <p>
     *  Tab键。
     * 
     */
    public static final int TAB                 = '\t';

    /**
     * The Escape key.
     * <p>
     *  退出键。
     * 
     */
    public static final int ESCAPE              = 27;

    /**
     * The Delete key.
     * <p>
     *  删除键。
     * 
     */
    public static final int DELETE              = 127;


    /* Base for all window events. */
    private static final int WINDOW_EVENT       = 200;

    /**
     * The user has asked the window manager to kill the window.
     * <p>
     *  用户请求窗口管理器杀死窗口。
     * 
     */
    public static final int WINDOW_DESTROY      = 1 + WINDOW_EVENT;

    /**
     * The user has asked the window manager to expose the window.
     * <p>
     *  用户已要求窗口管理器公开窗口。
     * 
     */
    public static final int WINDOW_EXPOSE       = 2 + WINDOW_EVENT;

    /**
     * The user has asked the window manager to iconify the window.
     * <p>
     *  用户已要求窗口管理器将窗口图标化。
     * 
     */
    public static final int WINDOW_ICONIFY      = 3 + WINDOW_EVENT;

    /**
     * The user has asked the window manager to de-iconify the window.
     * <p>
     *  用户已要求窗口管理器取消图标窗口。
     * 
     */
    public static final int WINDOW_DEICONIFY    = 4 + WINDOW_EVENT;

    /**
     * The user has asked the window manager to move the window.
     * <p>
     *  用户请求窗口管理器移动窗口。
     * 
     */
    public static final int WINDOW_MOVED        = 5 + WINDOW_EVENT;

    /* Base for all keyboard events. */
    private static final int KEY_EVENT          = 400;

    /**
     * The user has pressed a normal key.
     * <p>
     *  用户按了正常键。
     * 
     */
    public static final int KEY_PRESS           = 1 + KEY_EVENT;

    /**
     * The user has released a normal key.
     * <p>
     *  用户已释放正常键。
     * 
     */
    public static final int KEY_RELEASE         = 2 + KEY_EVENT;

    /**
     * The user has pressed a non-ASCII <em>action</em> key.
     * The <code>key</code> field contains a value that indicates
     * that the event occurred on one of the action keys, which
     * comprise the 12 function keys, the arrow (cursor) keys,
     * Page Up, Page Down, Home, End, Print Screen, Scroll Lock,
     * Caps Lock, Num Lock, Pause, and Insert.
     * <p>
     *  用户已按下非ASCII <em> </em>键。
     *  <code> key </code>字段包含一个值,表示事件发生在其中一个操作键上,包括12个功能键,箭头(光标)键,Page Up,Page Down,Home,End,打印屏幕,滚动锁定,大写锁定
     * ,数字锁定,暂停和插入。
     *  用户已按下非ASCII <em> </em>键。
     * 
     */
    public static final int KEY_ACTION          = 3 + KEY_EVENT;

    /**
     * The user has released a non-ASCII <em>action</em> key.
     * The <code>key</code> field contains a value that indicates
     * that the event occurred on one of the action keys, which
     * comprise the 12 function keys, the arrow (cursor) keys,
     * Page Up, Page Down, Home, End, Print Screen, Scroll Lock,
     * Caps Lock, Num Lock, Pause, and Insert.
     * <p>
     *  用户已发布非ASCII <em>操作</em>键。
     *  <code> key </code>字段包含一个值,表示事件发生在其中一个操作键上,包括12个功能键,箭头(光标)键,Page Up,Page Down,Home,End,打印屏幕,滚动锁定,大写锁定
     * ,数字锁定,暂停和插入。
     *  用户已发布非ASCII <em>操作</em>键。
     * 
     */
    public static final int KEY_ACTION_RELEASE  = 4 + KEY_EVENT;

    /* Base for all mouse events. */
    private static final int MOUSE_EVENT        = 500;

    /**
     * The user has pressed the mouse button. The <code>ALT_MASK</code>
     * flag indicates that the middle button has been pressed.
     * The <code>META_MASK</code>flag indicates that the
     * right button has been pressed.
     * <p>
     *  用户已按下鼠标按钮。 <code> ALT_MASK </code>标志表示已按下中间按钮。 <code> META_MASK </code>标志表示已按下右按钮。
     * 
     * 
     * @see     java.awt.Event#ALT_MASK
     * @see     java.awt.Event#META_MASK
     */
    public static final int MOUSE_DOWN          = 1 + MOUSE_EVENT;

    /**
     * The user has released the mouse button. The <code>ALT_MASK</code>
     * flag indicates that the middle button has been released.
     * The <code>META_MASK</code>flag indicates that the
     * right button has been released.
     * <p>
     * 用户已释放鼠标按钮。 <code> ALT_MASK </code>标志表示中间按钮已释放。 <code> META_MASK </code>标志表示右侧按钮已释放。
     * 
     * 
     * @see     java.awt.Event#ALT_MASK
     * @see     java.awt.Event#META_MASK
     */
    public static final int MOUSE_UP            = 2 + MOUSE_EVENT;

    /**
     * The mouse has moved with no button pressed.
     * <p>
     *  鼠标移动时没有按下任何按钮。
     * 
     */
    public static final int MOUSE_MOVE          = 3 + MOUSE_EVENT;

    /**
     * The mouse has entered a component.
     * <p>
     *  鼠标已输入组件。
     * 
     */
    public static final int MOUSE_ENTER         = 4 + MOUSE_EVENT;

    /**
     * The mouse has exited a component.
     * <p>
     *  鼠标已退出组件。
     * 
     */
    public static final int MOUSE_EXIT          = 5 + MOUSE_EVENT;

    /**
     * The user has moved the mouse with a button pressed. The
     * <code>ALT_MASK</code> flag indicates that the middle
     * button is being pressed. The <code>META_MASK</code> flag indicates
     * that the right button is being pressed.
     * <p>
     *  用户已按下按钮移动鼠标。 <code> ALT_MASK </code>标志表示正在按中间按钮。 <code> META_MASK </code>标志表示正在按下右按钮。
     * 
     * 
     * @see     java.awt.Event#ALT_MASK
     * @see     java.awt.Event#META_MASK
     */
    public static final int MOUSE_DRAG          = 6 + MOUSE_EVENT;


    /* Scrolling events */
    private static final int SCROLL_EVENT       = 600;

    /**
     * The user has activated the <em>line up</em>
     * area of a scroll bar.
     * <p>
     *  用户已激活滚动条的<em>行向上</em>区域。
     * 
     */
    public static final int SCROLL_LINE_UP      = 1 + SCROLL_EVENT;

    /**
     * The user has activated the <em>line down</em>
     * area of a scroll bar.
     * <p>
     *  用户已激活滚动条的<em>行向下</em>区域。
     * 
     */
    public static final int SCROLL_LINE_DOWN    = 2 + SCROLL_EVENT;

    /**
     * The user has activated the <em>page up</em>
     * area of a scroll bar.
     * <p>
     *  用户已激活滚动条的<em>上翻页</em>区域。
     * 
     */
    public static final int SCROLL_PAGE_UP      = 3 + SCROLL_EVENT;

    /**
     * The user has activated the <em>page down</em>
     * area of a scroll bar.
     * <p>
     *  用户已激活滚动条的<strong>向下</em>区域。
     * 
     */
    public static final int SCROLL_PAGE_DOWN    = 4 + SCROLL_EVENT;

    /**
     * The user has moved the bubble (thumb) in a scroll bar,
     * moving to an "absolute" position, rather than to
     * an offset from the last position.
     * <p>
     *  用户已经在滚动条中移动气泡(拇指),移动到"绝对"位置,而不是从最后位置偏移。
     * 
     */
    public static final int SCROLL_ABSOLUTE     = 5 + SCROLL_EVENT;

    /**
     * The scroll begin event.
     * <p>
     *  滚动开始事件。
     * 
     */
    public static final int SCROLL_BEGIN        = 6 + SCROLL_EVENT;

    /**
     * The scroll end event.
     * <p>
     *  滚动结束事件。
     * 
     */
    public static final int SCROLL_END          = 7 + SCROLL_EVENT;

    /* List Events */
    private static final int LIST_EVENT         = 700;

    /**
     * An item in a list has been selected.
     * <p>
     *  已选择列表中的项目。
     * 
     */
    public static final int LIST_SELECT         = 1 + LIST_EVENT;

    /**
     * An item in a list has been deselected.
     * <p>
     *  列表中的项目已取消选择。
     * 
     */
    public static final int LIST_DESELECT       = 2 + LIST_EVENT;

    /* Misc Event */
    private static final int MISC_EVENT         = 1000;

    /**
     * This event indicates that the user wants some action to occur.
     * <p>
     *  此事件表示用户想要执行某些操作。
     * 
     */
    public static final int ACTION_EVENT        = 1 + MISC_EVENT;

    /**
     * A file loading event.
     * <p>
     *  文件加载事件。
     * 
     */
    public static final int LOAD_FILE           = 2 + MISC_EVENT;

    /**
     * A file saving event.
     * <p>
     *  文件保存事件。
     * 
     */
    public static final int SAVE_FILE           = 3 + MISC_EVENT;

    /**
     * A component gained the focus.
     * <p>
     *  一个组件获得了焦点。
     * 
     */
    public static final int GOT_FOCUS           = 4 + MISC_EVENT;

    /**
     * A component lost the focus.
     * <p>
     *  组件丢失了焦点。
     * 
     */
    public static final int LOST_FOCUS          = 5 + MISC_EVENT;

    /**
     * The target component. This indicates the component over which the
     * event occurred or with which the event is associated.
     * This object has been replaced by AWTEvent.getSource()
     *
     * <p>
     *  目标组件。这指示事件发生或与事件相关联的组件。此对象已替换为AWTEvent.getSource()
     * 
     * 
     * @serial
     * @see java.awt.AWTEvent#getSource()
     */
    public Object target;

    /**
     * The time stamp.
     * Replaced by InputEvent.getWhen().
     *
     * <p>
     *  时间戳。替换为InputEvent.getWhen()。
     * 
     * 
     * @serial
     * @see java.awt.event.InputEvent#getWhen()
     */
    public long when;

    /**
     * Indicates which type of event the event is, and which
     * other <code>Event</code> variables are relevant for the event.
     * This has been replaced by AWTEvent.getID()
     *
     * <p>
     * 指示事件的事件类型,以及与事件相关的其他<code> Event </code>变量。这已被AWTEvent.getID()取代,
     * 
     * 
     * @serial
     * @see java.awt.AWTEvent#getID()
     */
    public int id;

    /**
     * The <i>x</i> coordinate of the event.
     * Replaced by MouseEvent.getX()
     *
     * <p>
     *  事件的<i> x </i>坐标。替换为MouseEvent.getX()
     * 
     * 
     * @serial
     * @see java.awt.event.MouseEvent#getX()
     */
    public int x;

    /**
     * The <i>y</i> coordinate of the event.
     * Replaced by MouseEvent.getY()
     *
     * <p>
     *  事件的<y> y </i>坐标。替换为MouseEvent.getY()
     * 
     * 
     * @serial
     * @see java.awt.event.MouseEvent#getY()
     */
    public int y;

    /**
     * The key code of the key that was pressed in a keyboard event.
     * This has been replaced by KeyEvent.getKeyCode()
     *
     * <p>
     *  在键盘事件中按下的键的键代码。这已被KeyEvent.getKeyCode()
     * 
     * 
     * @serial
     * @see java.awt.event.KeyEvent#getKeyCode()
     */
    public int key;

    /**
     * The key character that was pressed in a keyboard event.
     * <p>
     *  在键盘事件中按下的键字符。
     * 
     */
//    public char keyChar;

    /**
     * The state of the modifier keys.
     * This is replaced with InputEvent.getModifiers()
     * In java 1.1 MouseEvent and KeyEvent are subclasses
     * of InputEvent.
     *
     * <p>
     *  修饰键的状态。这被替换为InputEvent.getModifiers()在java 1.1 MouseEvent和KeyEvent是InputEvent的子类。
     * 
     * 
     * @serial
     * @see java.awt.event.InputEvent#getModifiers()
     */
    public int modifiers;

    /**
     * For <code>MOUSE_DOWN</code> events, this field indicates the
     * number of consecutive clicks. For other events, its value is
     * <code>0</code>.
     * This field has been replaced by MouseEvent.getClickCount().
     *
     * <p>
     *  对于<code> MOUSE_DOWN </code>事件,此字段表示连续点击的次数。对于其他事件,其值为<code> 0 </code>。
     * 此字段已替换为MouseEvent.getClickCount()。
     * 
     * 
     * @serial
     * @see java.awt.event.MouseEvent#getClickCount()
     */
    public int clickCount;

    /**
     * An arbitrary argument of the event. The value of this field
     * depends on the type of event.
     * <code>arg</code> has been replaced by event specific property.
     *
     * <p>
     *  该事件的任意参数。此字段的值取决于事件的类型。 <code> arg </code>已替换为特定于事件的属性。
     * 
     * 
     * @serial
     */
    public Object arg;

    /**
     * The next event. This field is set when putting events into a
     * linked list.
     * This has been replaced by EventQueue.
     *
     * <p>
     *  下一个事件。将事件放入链接列表时,将设置此字段。这已被EventQueue替换。
     * 
     * 
     * @serial
     * @see java.awt.EventQueue
     */
    public Event evt;

    /* table for mapping old Event action keys to KeyEvent virtual keys. */
    private static final int actionKeyCodes[][] = {
    /*    virtual key              action key   */
        { KeyEvent.VK_HOME,        Event.HOME         },
        { KeyEvent.VK_END,         Event.END          },
        { KeyEvent.VK_PAGE_UP,     Event.PGUP         },
        { KeyEvent.VK_PAGE_DOWN,   Event.PGDN         },
        { KeyEvent.VK_UP,          Event.UP           },
        { KeyEvent.VK_DOWN,        Event.DOWN         },
        { KeyEvent.VK_LEFT,        Event.LEFT         },
        { KeyEvent.VK_RIGHT,       Event.RIGHT        },
        { KeyEvent.VK_F1,          Event.F1           },
        { KeyEvent.VK_F2,          Event.F2           },
        { KeyEvent.VK_F3,          Event.F3           },
        { KeyEvent.VK_F4,          Event.F4           },
        { KeyEvent.VK_F5,          Event.F5           },
        { KeyEvent.VK_F6,          Event.F6           },
        { KeyEvent.VK_F7,          Event.F7           },
        { KeyEvent.VK_F8,          Event.F8           },
        { KeyEvent.VK_F9,          Event.F9           },
        { KeyEvent.VK_F10,         Event.F10          },
        { KeyEvent.VK_F11,         Event.F11          },
        { KeyEvent.VK_F12,         Event.F12          },
        { KeyEvent.VK_PRINTSCREEN, Event.PRINT_SCREEN },
        { KeyEvent.VK_SCROLL_LOCK, Event.SCROLL_LOCK  },
        { KeyEvent.VK_CAPS_LOCK,   Event.CAPS_LOCK    },
        { KeyEvent.VK_NUM_LOCK,    Event.NUM_LOCK     },
        { KeyEvent.VK_PAUSE,       Event.PAUSE        },
        { KeyEvent.VK_INSERT,      Event.INSERT       }
    };

    /**
     * This field controls whether or not the event is sent back
     * down to the peer once the target has processed it -
     * false means it's sent to the peer, true means it's not.
     *
     * <p>
     *  此字段控制事件是否发送回对等体一旦目标已处理它 - 假的意味着它发送到对等体,真的意味着它不是。
     * 
     * 
     * @serial
     * @see #isConsumed()
     */
    private boolean consumed = false;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 5488922509400504703L;

    static {
        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Initialize JNI field and method IDs for fields that may be
       accessed from C.
     * <p>
     *  初始化可从C访问的字段的JNI字段和方法ID。
     * 
     */
    private static native void initIDs();

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Creates an instance of <code>Event</code> with the specified target
     * component, time stamp, event type, <i>x</i> and <i>y</i>
     * coordinates, keyboard key, state of the modifier keys, and
     * argument.
     * <p>
     * <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  创建具有指定的目标组件,时间戳,事件类型,<i> x </i>和<i> y </i>坐标,键盘键,修改器状态的<code> Event </code>键和参数。
     * 
     * 
     * @param     target     the target component.
     * @param     when       the time stamp.
     * @param     id         the event type.
     * @param     x          the <i>x</i> coordinate.
     * @param     y          the <i>y</i> coordinate.
     * @param     key        the key pressed in a keyboard event.
     * @param     modifiers  the state of the modifier keys.
     * @param     arg        the specified argument.
     */
    public Event(Object target, long when, int id, int x, int y, int key,
                 int modifiers, Object arg) {
        this.target = target;
        this.when = when;
        this.id = id;
        this.x = x;
        this.y = y;
        this.key = key;
        this.modifiers = modifiers;
        this.arg = arg;
        this.data = 0;
        this.clickCount = 0;
        switch(id) {
          case ACTION_EVENT:
          case WINDOW_DESTROY:
          case WINDOW_ICONIFY:
          case WINDOW_DEICONIFY:
          case WINDOW_MOVED:
          case SCROLL_LINE_UP:
          case SCROLL_LINE_DOWN:
          case SCROLL_PAGE_UP:
          case SCROLL_PAGE_DOWN:
          case SCROLL_ABSOLUTE:
          case SCROLL_BEGIN:
          case SCROLL_END:
          case LIST_SELECT:
          case LIST_DESELECT:
            consumed = true; // these types are not passed back to peer
            break;
          default:
        }
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Creates an instance of <code>Event</code>, with the specified target
     * component, time stamp, event type, <i>x</i> and <i>y</i>
     * coordinates, keyboard key, state of the modifier keys, and an
     * argument set to <code>null</code>.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  创建具有指定的目标组件,时间戳,事件类型,<i> x </i>和<i> y </i>坐标,键盘键,状态的<code> Event </code>修饰符键,以及设置为<code> null </code>
     * 的参数。
     * 
     * 
     * @param     target     the target component.
     * @param     when       the time stamp.
     * @param     id         the event type.
     * @param     x          the <i>x</i> coordinate.
     * @param     y          the <i>y</i> coordinate.
     * @param     key        the key pressed in a keyboard event.
     * @param     modifiers  the state of the modifier keys.
     */
    public Event(Object target, long when, int id, int x, int y, int key, int modifiers) {
        this(target, when, id, x, y, key, modifiers, null);
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Creates an instance of <code>Event</code> with the specified
     * target component, event type, and argument.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  使用指定的目标组件,事件类型和参数创建<code> Event </code>的实例。
     * 
     * 
     * @param     target     the target component.
     * @param     id         the event type.
     * @param     arg        the specified argument.
     */
    public Event(Object target, int id, Object arg) {
        this(target, 0, id, 0, 0, 0, 0, arg);
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Translates this event so that its <i>x</i> and <i>y</i>
     * coordinates are increased by <i>dx</i> and <i>dy</i>,
     * respectively.
     * <p>
     * This method translates an event relative to the given component.
     * This involves, at a minimum, translating the coordinates into the
     * local coordinate system of the given component. It may also involve
     * translating a region in the case of an expose event.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  翻译此事件,使其<i> x </i>和<y> y </i>坐标分别增加<d> dx </i>和<dy>
     * <p>
     * 此方法转换相对于给定组件的事件。这至少涉及将坐标转换成给定分量的局部坐标系。它还可以涉及在暴露事件的情况下翻译区域。
     * 
     * 
     * @param     dx     the distance to translate the <i>x</i> coordinate.
     * @param     dy     the distance to translate the <i>y</i> coordinate.
     */
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Checks if the Shift key is down.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  检查Shift键是否关闭。
     * 
     * 
     * @return    <code>true</code> if the key is down;
     *            <code>false</code> otherwise.
     * @see       java.awt.Event#modifiers
     * @see       java.awt.Event#controlDown
     * @see       java.awt.Event#metaDown
     */
    public boolean shiftDown() {
        return (modifiers & SHIFT_MASK) != 0;
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Checks if the Control key is down.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  检查控制键是否关闭。
     * 
     * 
     * @return    <code>true</code> if the key is down;
     *            <code>false</code> otherwise.
     * @see       java.awt.Event#modifiers
     * @see       java.awt.Event#shiftDown
     * @see       java.awt.Event#metaDown
     */
    public boolean controlDown() {
        return (modifiers & CTRL_MASK) != 0;
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Checks if the Meta key is down.
     *
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  检查Meta键是否关闭。
     * 
     * 
     * @return    <code>true</code> if the key is down;
     *            <code>false</code> otherwise.
     * @see       java.awt.Event#modifiers
     * @see       java.awt.Event#shiftDown
     * @see       java.awt.Event#controlDown
     */
    public boolean metaDown() {
        return (modifiers & META_MASK) != 0;
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * 
     */
    void consume() {
        switch(id) {
          case KEY_PRESS:
          case KEY_RELEASE:
          case KEY_ACTION:
          case KEY_ACTION_RELEASE:
              consumed = true;
              break;
          default:
              // event type cannot be consumed
        }
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * 
     */
    boolean isConsumed() {
        return consumed;
    }

    /*
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Returns the integer key-code associated with the key in this event,
     * as described in java.awt.Event.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     * 返回与此事件中的键相关联的整数键代码,如java.awt.Event中所述。
     * 
     */
    static int getOldEventKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (int i = 0; i < actionKeyCodes.length; i++) {
            if (actionKeyCodes[i][0] == keyCode) {
                return actionKeyCodes[i][1];
            }
        }
        return (int)e.getKeyChar();
    }

    /*
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Returns a new KeyEvent char which corresponds to the int key
     * of this old event.
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  返回与此旧事件的int键对应的新的KeyEvent字符。
     * 
     */
    char getKeyEventChar() {
       for (int i = 0; i < actionKeyCodes.length; i++) {
            if (actionKeyCodes[i][1] == key) {
                return KeyEvent.CHAR_UNDEFINED;
            }
       }
       return (char)key;
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Returns a string representing the state of this <code>Event</code>.
     * This method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * <p>
     *  <b>注意：</b> <code> Event </code>类已过时,仅供向后兼容使用。它已被<code> AWTEvent </code>类及其子类替换。
     * <p>
     *  返回表示此<code> Event </code>的状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return    the parameter string of this event
     */
    protected String paramString() {
        String str = "id=" + id + ",x=" + x + ",y=" + y;
        if (key != 0) {
            str += ",key=" + key;
        }
        if (shiftDown()) {
            str += ",shift";
        }
        if (controlDown()) {
            str += ",control";
        }
        if (metaDown()) {
            str += ",meta";
        }
        if (target != null) {
            str += ",target=" + target;
        }
        if (arg != null) {
            str += ",arg=" + arg;
        }
        return str;
    }

    /**
     * <b>NOTE:</b> The <code>Event</code> class is obsolete and is
     * available only for backwards compatibility.  It has been replaced
     * by the <code>AWTEvent</code> class and its subclasses.
     * <p>
     * Returns a representation of this event's values as a string.
     * <p>
     * 
     * @return    a string that represents the event and the values
     *                 of its member fields.
     * @see       java.awt.Event#paramString
     * @since     JDK1.1
     */
    public String toString() {
        return getClass().getName() + "[" + paramString() + "]";
    }
}
