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

package java.awt.dnd;

import java.awt.event.InputEvent;

/**
 * The <code>DragSourceDragEvent</code> is
 * delivered from the <code>DragSourceContextPeer</code>,
 * via the <code>DragSourceContext</code>, to the <code>DragSourceListener</code>
 * registered with that <code>DragSourceContext</code> and with its associated
 * <code>DragSource</code>.
 * <p>
 * The <code>DragSourceDragEvent</code> reports the <i>target drop action</i>
 * and the <i>user drop action</i> that reflect the current state of
 * the drag operation.
 * <p>
 * <i>Target drop action</i> is one of <code>DnDConstants</code> that represents
 * the drop action selected by the current drop target if this drop action is
 * supported by the drag source or <code>DnDConstants.ACTION_NONE</code> if this
 * drop action is not supported by the drag source.
 * <p>
 * <i>User drop action</i> depends on the drop actions supported by the drag
 * source and the drop action selected by the user. The user can select a drop
 * action by pressing modifier keys during the drag operation:
 * <pre>
 *   Ctrl + Shift -&gt; ACTION_LINK
 *   Ctrl         -&gt; ACTION_COPY
 *   Shift        -&gt; ACTION_MOVE
 * </pre>
 * If the user selects a drop action, the <i>user drop action</i> is one of
 * <code>DnDConstants</code> that represents the selected drop action if this
 * drop action is supported by the drag source or
 * <code>DnDConstants.ACTION_NONE</code> if this drop action is not supported
 * by the drag source.
 * <p>
 * If the user doesn't select a drop action, the set of
 * <code>DnDConstants</code> that represents the set of drop actions supported
 * by the drag source is searched for <code>DnDConstants.ACTION_MOVE</code>,
 * then for <code>DnDConstants.ACTION_COPY</code>, then for
 * <code>DnDConstants.ACTION_LINK</code> and the <i>user drop action</i> is the
 * first constant found. If no constant is found the <i>user drop action</i>
 * is <code>DnDConstants.ACTION_NONE</code>.
 *
 * <p>
 *  <code> DragSourceDragEvent </code>通过<code> DragSourceContext </code>从<code> DragSourceContextPeer </code>
 * 传递到注册了<code> DragSourceContext的<code> DragSourceListener </code> </code>及其关联的<code> DragSource </code>
 * 。
 * <p>
 *  <code> DragSourceDragEvent </code>报告反映拖动操作的当前状态的<i>目标放置操作</i>和<i>用户放置操作</i>。
 * <p>
 *  <i>目标拖放动作</i>是<code> DnDConstants </code>之一,表示当前拖放目标选择的拖放动作,如果拖放源或<code> DnDConstants.ACTION_NONE < / code>
 * 如果拖放源不支持此拖放操作。
 * <p>
 *  <i>用户放置操作</i>取决于拖动源支持的放下操作和用户选择的放下操作。用户可以在拖动操作期间通过按修改键来选择拖放动作：
 * <pre>
 *  Ctrl + Shift  - &gt; ACTION_LINK Ctrl  - &gt; ACTION_COPY Shift  - &gt; ACTION_MOVE
 * </pre>
 *  如果用户选择删除操作,则<i>用户删除操作</i>是代表所选删除操作的<code> DnDConstants </code>之一,如果此拖放操作由拖动源或<code> DnDConstants.ACT
 * ION_NONE </code>如果拖放源不支持此拖放操作。
 * <p>
 * 如果用户未选择删除操作,则会搜索代表拖动源支持的一组拖放操作的<code> DnDConstants </code>集合,然后搜索<code> DnDConstants.ACTION_MOVE </code>
 *  <code> DnDConstants.ACTION_COPY </code>,然后是<code> DnDConstants.ACTION_LINK </code>,而<i>用户删除操作</i>是找到
 * 的第一个常数。
 * 如果未找到常数,则<i>用户删除操作</i>是<code> DnDConstants.ACTION_NONE </code>。
 * 
 * 
 * @since 1.2
 *
 */

public class DragSourceDragEvent extends DragSourceEvent {

    private static final long serialVersionUID = 481346297933902471L;

    /**
     * Constructs a <code>DragSourceDragEvent</code>.
     * This class is typically
     * instantiated by the <code>DragSourceContextPeer</code>
     * rather than directly
     * by client code.
     * The coordinates for this <code>DragSourceDragEvent</code>
     * are not specified, so <code>getLocation</code> will return
     * <code>null</code> for this event.
     * <p>
     * The arguments <code>dropAction</code> and <code>action</code> should
     * be one of <code>DnDConstants</code> that represents a single action.
     * The argument <code>modifiers</code> should be either a bitwise mask
     * of old <code>java.awt.event.InputEvent.*_MASK</code> constants or a
     * bitwise mask of extended <code>java.awt.event.InputEvent.*_DOWN_MASK</code>
     * constants.
     * This constructor does not throw any exception for invalid <code>dropAction</code>,
     * <code>action</code> and <code>modifiers</code>.
     *
     * <p>
     *  构造一个<code> DragSourceDragEvent </code>。
     * 这个类通常由<code> DragSourceContextPeer </code>实例化,而不是直接由客户端代码实例化。
     * 未指定此<code> DragSourceDragEvent </code>的坐标,因此<code> getLocation </code>将为此事件返回<code> null </code>。
     * <p>
     *  参数<code> dropAction </code>和<code> action </code>应该是代表单个操作的<code> DnDConstants </code>之一。
     * 参数<code> modifiers </code>应该是旧的<code> java.awt.event.InputEvent。
     * * _ MASK </code>常量的位掩码或扩展的<code> java.awt.event .InputEvent。* _ DOWN_MASK </code>常量。
     * 对于无效的<code> dropAction </code>,<code> action </code>和<code>修饰符</code>,此构造函数不会抛出任何异常。
     * 
     * 
     * @param dsc the <code>DragSourceContext</code> that is to manage
     *            notifications for this event.
     * @param dropAction the user drop action.
     * @param action the target drop action.
     * @param modifiers the modifier keys down during event (shift, ctrl,
     *        alt, meta)
     *        Either extended _DOWN_MASK or old _MASK modifiers
     *        should be used, but both models should not be mixed
     *        in one event. Use of the extended modifiers is
     *        preferred.
     *
     * @throws IllegalArgumentException if <code>dsc</code> is <code>null</code>.
     *
     * @see java.awt.event.InputEvent
     * @see DragSourceEvent#getLocation
     */

    public DragSourceDragEvent(DragSourceContext dsc, int dropAction,
                               int action, int modifiers) {
        super(dsc);

        targetActions    = action;
        gestureModifiers = modifiers;
        this.dropAction  = dropAction;
        if ((modifiers & ~(JDK_1_3_MODIFIERS | JDK_1_4_MODIFIERS)) != 0) {
            invalidModifiers = true;
        } else if ((getGestureModifiers() != 0) && (getGestureModifiersEx() == 0)) {
            setNewModifiers();
        } else if ((getGestureModifiers() == 0) && (getGestureModifiersEx() != 0)) {
            setOldModifiers();
        } else {
            invalidModifiers = true;
        }
    }

    /**
     * Constructs a <code>DragSourceDragEvent</code> given the specified
     * <code>DragSourceContext</code>, user drop action, target drop action,
     * modifiers and coordinates.
     * <p>
     * The arguments <code>dropAction</code> and <code>action</code> should
     * be one of <code>DnDConstants</code> that represents a single action.
     * The argument <code>modifiers</code> should be either a bitwise mask
     * of old <code>java.awt.event.InputEvent.*_MASK</code> constants or a
     * bitwise mask of extended <code>java.awt.event.InputEvent.*_DOWN_MASK</code>
     * constants.
     * This constructor does not throw any exception for invalid <code>dropAction</code>,
     * <code>action</code> and <code>modifiers</code>.
     *
     * <p>
     *  在给定指定的<code> DragSourceContext </code>,用户放置操作,目标放置操作,修改符和坐标的情况下构造<code> DragSourceDragEvent </code>。
     * <p>
     * 参数<code> dropAction </code>和<code> action </code>应该是代表单个操作的<code> DnDConstants </code>之一。
     * 参数<code> modifiers </code>应该是旧的<code> java.awt.event.InputEvent。
     * * _ MASK </code>常量的位掩码或扩展的<code> java.awt.event .InputEvent。* _ DOWN_MASK </code>常量。
     * 对于无效的<code> dropAction </code>,<code> action </code>和<code>修饰符</code>,此构造函数不会抛出任何异常。
     * 
     * 
     * @param dsc the <code>DragSourceContext</code> associated with this
     *        event.
     * @param dropAction the user drop action.
     * @param action the target drop action.
     * @param modifiers the modifier keys down during event (shift, ctrl,
     *        alt, meta)
     *        Either extended _DOWN_MASK or old _MASK modifiers
     *        should be used, but both models should not be mixed
     *        in one event. Use of the extended modifiers is
     *        preferred.
     * @param x   the horizontal coordinate for the cursor location
     * @param y   the vertical coordinate for the cursor location
     *
     * @throws IllegalArgumentException if <code>dsc</code> is <code>null</code>.
     *
     * @see java.awt.event.InputEvent
     * @since 1.4
     */
    public DragSourceDragEvent(DragSourceContext dsc, int dropAction,
                               int action, int modifiers, int x, int y) {
        super(dsc, x, y);

        targetActions    = action;
        gestureModifiers = modifiers;
        this.dropAction  = dropAction;
        if ((modifiers & ~(JDK_1_3_MODIFIERS | JDK_1_4_MODIFIERS)) != 0) {
            invalidModifiers = true;
        } else if ((getGestureModifiers() != 0) && (getGestureModifiersEx() == 0)) {
            setNewModifiers();
        } else if ((getGestureModifiers() == 0) && (getGestureModifiersEx() != 0)) {
            setOldModifiers();
        } else {
            invalidModifiers = true;
        }
    }

    /**
     * This method returns the target drop action.
     *
     * <p>
     *  此方法返回目标拖放操作。
     * 
     * 
     * @return the target drop action.
     */
    public int getTargetActions() {
        return targetActions;
    }


    private static final int JDK_1_3_MODIFIERS = InputEvent.SHIFT_DOWN_MASK - 1;
    private static final int JDK_1_4_MODIFIERS =
            ((InputEvent.ALT_GRAPH_DOWN_MASK << 1) - 1) & ~JDK_1_3_MODIFIERS;

    /**
     * This method returns an <code>int</code> representing
     * the current state of the input device modifiers
     * associated with the user's gesture. Typically these
     * would be mouse buttons or keyboard modifiers.
     * <P>
     * If the <code>modifiers</code> passed to the constructor
     * are invalid, this method returns them unchanged.
     *
     * <p>
     *  该方法返回代表与用户手势相关联的输入设备修饰符的当前状态的<code> int </code>。通常这些将是鼠标按钮或键盘修改器。
     * <P>
     *  如果传递给构造函数的<code>修饰符</code>无效,此方法将不改变地返回它们。
     * 
     * 
     * @return the current state of the input device modifiers
     */

    public int getGestureModifiers() {
        return invalidModifiers ? gestureModifiers : gestureModifiers & JDK_1_3_MODIFIERS;
    }

    /**
     * This method returns an <code>int</code> representing
     * the current state of the input device extended modifiers
     * associated with the user's gesture.
     * See {@link InputEvent#getModifiersEx}
     * <P>
     * If the <code>modifiers</code> passed to the constructor
     * are invalid, this method returns them unchanged.
     *
     * <p>
     *  此方法返回代表与用户手势相关联的输入设备扩展修饰符的当前状态的<code> int </code>。请参阅{@link InputEvent#getModifiersEx}
     * <P>
     *  如果传递给构造函数的<code>修饰符</code>无效,此方法将不改变地返回它们。
     * 
     * 
     * @return the current state of the input device extended modifiers
     * @since 1.4
     */

    public int getGestureModifiersEx() {
        return invalidModifiers ? gestureModifiers : gestureModifiers & JDK_1_4_MODIFIERS;
    }

    /**
     * This method returns the user drop action.
     *
     * <p>
     *  此方法返回用户放置操作。
     * 
     * 
     * @return the user drop action.
     */
    public int getUserAction() { return dropAction; }

    /**
     * This method returns the logical intersection of
     * the target drop action and the set of drop actions supported by
     * the drag source.
     *
     * <p>
     *  此方法返回目标拖放操作和拖动源支持的拖放操作集的逻辑交集。
     * 
     * 
     * @return the logical intersection of the target drop action and
     *         the set of drop actions supported by the drag source.
     */
    public int getDropAction() {
        return targetActions & getDragSourceContext().getSourceActions();
    }

    /*
     * fields
     * <p>
     *  字段
     * 
     */

    /**
     * The target drop action.
     *
     * <p>
     *  目标下降动作。
     * 
     * 
     * @serial
     */
    private int     targetActions    = DnDConstants.ACTION_NONE;

    /**
     * The user drop action.
     *
     * <p>
     *  用户删除操作。
     * 
     * 
     * @serial
     */
    private int     dropAction       = DnDConstants.ACTION_NONE;

    /**
     * The state of the input device modifiers associated with the user
     * gesture.
     *
     * <p>
     *  与用户手势相关联的输入设备修改符的状态。
     * 
     * 
     * @serial
     */
    private int     gestureModifiers = 0;

    /**
     * Indicates whether the <code>gestureModifiers</code> are invalid.
     *
     * <p>
     * 指示<code> gestureModifiers </code>是否无效。
     * 
     * 
     * @serial
     */
    private boolean invalidModifiers;

    /**
     * Sets new modifiers by the old ones.
     * The mouse modifiers have higher priority than overlaying key
     * modifiers.
     * <p>
     *  使用旧的修饰符设置新的修饰符。鼠标修饰符的优先级高于覆盖键修饰符。
     * 
     */
    private void setNewModifiers() {
        if ((gestureModifiers & InputEvent.BUTTON1_MASK) != 0) {
            gestureModifiers |= InputEvent.BUTTON1_DOWN_MASK;
        }
        if ((gestureModifiers & InputEvent.BUTTON2_MASK) != 0) {
            gestureModifiers |= InputEvent.BUTTON2_DOWN_MASK;
        }
        if ((gestureModifiers & InputEvent.BUTTON3_MASK) != 0) {
            gestureModifiers |= InputEvent.BUTTON3_DOWN_MASK;
        }
        if ((gestureModifiers & InputEvent.SHIFT_MASK) != 0) {
            gestureModifiers |= InputEvent.SHIFT_DOWN_MASK;
        }
        if ((gestureModifiers & InputEvent.CTRL_MASK) != 0) {
            gestureModifiers |= InputEvent.CTRL_DOWN_MASK;
        }
        if ((gestureModifiers & InputEvent.ALT_GRAPH_MASK) != 0) {
            gestureModifiers |= InputEvent.ALT_GRAPH_DOWN_MASK;
        }
    }

    /**
     * Sets old modifiers by the new ones.
     * <p>
     *  通过新的修饰符设置旧的修饰符。
     */
    private void setOldModifiers() {
        if ((gestureModifiers & InputEvent.BUTTON1_DOWN_MASK) != 0) {
            gestureModifiers |= InputEvent.BUTTON1_MASK;
        }
        if ((gestureModifiers & InputEvent.BUTTON2_DOWN_MASK) != 0) {
            gestureModifiers |= InputEvent.BUTTON2_MASK;
        }
        if ((gestureModifiers & InputEvent.BUTTON3_DOWN_MASK) != 0) {
            gestureModifiers |= InputEvent.BUTTON3_MASK;
        }
        if ((gestureModifiers & InputEvent.SHIFT_DOWN_MASK) != 0) {
            gestureModifiers |= InputEvent.SHIFT_MASK;
        }
        if ((gestureModifiers & InputEvent.CTRL_DOWN_MASK) != 0) {
            gestureModifiers |= InputEvent.CTRL_MASK;
        }
        if ((gestureModifiers & InputEvent.ALT_GRAPH_DOWN_MASK) != 0) {
            gestureModifiers |= InputEvent.ALT_GRAPH_MASK;
        }
    }
}
