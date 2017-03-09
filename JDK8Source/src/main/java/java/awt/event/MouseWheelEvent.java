/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.event;

import java.awt.Component;

import java.lang.annotation.Native;

/**
 * An event which indicates that the mouse wheel was rotated in a component.
 * <P>
 * A wheel mouse is a mouse which has a wheel in place of the middle button.
 * This wheel can be rotated towards or away from the user.  Mouse wheels are
 * most often used for scrolling, though other uses are possible.
 * <P>
 * A MouseWheelEvent object is passed to every <code>MouseWheelListener</code>
 * object which registered to receive the "interesting" mouse events using the
 * component's <code>addMouseWheelListener</code> method.  Each such listener
 * object gets a <code>MouseEvent</code> containing the mouse event.
 * <P>
 * Due to the mouse wheel's special relationship to scrolling Components,
 * MouseWheelEvents are delivered somewhat differently than other MouseEvents.
 * This is because while other MouseEvents usually affect a change on
 * the Component directly under the mouse
 * cursor (for instance, when clicking a button), MouseWheelEvents often have
 * an effect away from the mouse cursor (moving the wheel while
 * over a Component inside a ScrollPane should scroll one of the
 * Scrollbars on the ScrollPane).
 * <P>
 * MouseWheelEvents start delivery from the Component underneath the
 * mouse cursor.  If MouseWheelEvents are not enabled on the
 * Component, the event is delivered to the first ancestor
 * Container with MouseWheelEvents enabled.  This will usually be
 * a ScrollPane with wheel scrolling enabled.  The source
 * Component and x,y coordinates will be relative to the event's
 * final destination (the ScrollPane).  This allows a complex
 * GUI to be installed without modification into a ScrollPane, and
 * for all MouseWheelEvents to be delivered to the ScrollPane for
 * scrolling.
 * <P>
 * Some AWT Components are implemented using native widgets which
 * display their own scrollbars and handle their own scrolling.
 * The particular Components for which this is true will vary from
 * platform to platform.  When the mouse wheel is
 * moved over one of these Components, the event is delivered straight to
 * the native widget, and not propagated to ancestors.
 * <P>
 * Platforms offer customization of the amount of scrolling that
 * should take place when the mouse wheel is moved.  The two most
 * common settings are to scroll a certain number of "units"
 * (commonly lines of text in a text-based component) or an entire "block"
 * (similar to page-up/page-down).  The MouseWheelEvent offers
 * methods for conforming to the underlying platform settings.  These
 * platform settings can be changed at any time by the user.  MouseWheelEvents
 * reflect the most recent settings.
 * <P>
 * The <code>MouseWheelEvent</code> class includes methods for
 * getting the number of "clicks" by which the mouse wheel is rotated.
 * The {@link #getWheelRotation} method returns the integer number
 * of "clicks" corresponding to the number of notches by which the wheel was
 * rotated. In addition to this method, the <code>MouseWheelEvent</code>
 * class provides the {@link #getPreciseWheelRotation} method which returns
 * a double number of "clicks" in case a partial rotation occurred.
 * The {@link #getPreciseWheelRotation} method is useful if a mouse supports
 * a high-resolution wheel, such as a freely rotating wheel with no
 * notches. Applications can benefit by using this method to process
 * mouse wheel events more precisely, and thus, making visual perception
 * smoother.
 *
 * <p>
 *  指示鼠标滚轮在组件中旋转的事件。
 * <P>
 *  轮鼠标是具有轮代替中间按钮的鼠标。该轮可以朝向或远离用户旋转。鼠标滚轮最常用于滚动,虽然其他用途是可能的。
 * <P>
 *  MouseWheelEvent对象被传递到每个使用组件的<code> addMouseWheelListener </code>方法注册接收"有趣"鼠标事件的每个<code> MouseWheelLi
 * stener </code>对象。
 * 每个这样的监听器对象获取一个包含鼠标事件的<code> MouseEvent </code>。
 * <P>
 *  由于鼠标滚轮与滚动组件的特殊关系,MouseWheelEvents与其他MouseEvents有所不同。
 * 这是因为虽然其他MouseEvents通常影响组件直接在鼠标光标下的更改(例如,当单击按钮),MouseWheelEvents通常有远离鼠标光标的效果(移动滚轮,而在ScrollPane内的组件滚动Sc
 * rollPane上的滚动条之一)。
 *  由于鼠标滚轮与滚动组件的特殊关系,MouseWheelEvents与其他MouseEvents有所不同。
 * <P>
 * MouseWheelEvents从组件的鼠标光标下方开始传递。如果组件上未启用MouseWheelEvents,则事件将传递到启用了MouseWheelEvents的第一个祖先容器。
 * 这通常是启用滚轮滚动的ScrollPane。源组件和x,y坐标将相对于事件的最终目的地(ScrollPane)。
 * 这允许复杂的GUI被安装而不修改到ScrollPane中,并且所有MouseWheelEvents被传递到ScrollPane用于滚动。
 * <P>
 *  一些AWT组件是使用本地窗口小部件实现的,它们显示自己的滚动条并处理自己的滚动。这是真实的特定组件将随平台而变化。当鼠标滚轮在其中一个组件上移动时,事件会直接传递到本地窗口小部件,而不会传播到祖先。
 * <P>
 *  平台提供定制鼠标滚轮移动时应进行的滚动量。两个最常见的设置是滚动一定数量的"单位"(通常是基于文本的组件中的文本行)或整个"块"(类似于向上翻页/向下翻页)。
 *  MouseWheelEvent提供了符合底层平台设置的方法。这些平台设置可以随时由用户更改。 MouseWheelEvents反映最近的设置。
 * <P>
 * <code> MouseWheelEvent </code>类包括获取鼠标滚轮旋转的"点击"数量的方法。
 *  {@link #getWheelRotation}方法返回与轮子旋转的刻度数对应的"点击次数"的整数。
 * 除了此方法,<code> MouseWheelEvent </code>类提供了{@link #getPreciseWheelRotation}方法,该方法在发生部分轮播的情况下返回双倍的"点击次数"。
 *  {@link #getWheelRotation}方法返回与轮子旋转的刻度数对应的"点击次数"的整数。
 * 如果鼠标支持高分辨率轮,如{@link #getPreciseWheelRotation}方法可以自由旋转,没有凹口的轮。
 * 应用程序可以通过使用此方法更精确地处理鼠标滚轮事件,从而使视觉感知更平滑,从中受益。
 * 
 * 
 * @author Brent Christian
 * @see MouseWheelListener
 * @see java.awt.ScrollPane
 * @see java.awt.ScrollPane#setWheelScrollingEnabled(boolean)
 * @see javax.swing.JScrollPane
 * @see javax.swing.JScrollPane#setWheelScrollingEnabled(boolean)
 * @since 1.4
 */

public class MouseWheelEvent extends MouseEvent {

    /**
     * Constant representing scrolling by "units" (like scrolling with the
     * arrow keys)
     *
     * <p>
     *  常量表示按"单位"滚动(如使用箭头键滚动)
     * 
     * 
     * @see #getScrollType
     */
    @Native public static final int WHEEL_UNIT_SCROLL = 0;

    /**
     * Constant representing scrolling by a "block" (like scrolling
     * with page-up, page-down keys)
     *
     * <p>
     *  常量表示通过"块"滚动(例如,使用向上翻页,向下翻页键滚动)
     * 
     * 
     * @see #getScrollType
     */
    @Native public static final int WHEEL_BLOCK_SCROLL = 1;

    /**
     * Indicates what sort of scrolling should take place in response to this
     * event, based on platform settings.  Legal values are:
     * <ul>
     * <li> WHEEL_UNIT_SCROLL
     * <li> WHEEL_BLOCK_SCROLL
     * </ul>
     *
     * <p>
     *  根据平台设置指示应对此事件进行哪种滚动。法律价值包括：
     * <ul>
     *  <li> WHEEL_UNIT_SCROLL <li> WHEEL_BLOCK_SCROLL
     * </ul>
     * 
     * 
     * @see #getScrollType
     */
    int scrollType;

    /**
     * Only valid for scrollType WHEEL_UNIT_SCROLL.
     * Indicates number of units that should be scrolled per
     * click of mouse wheel rotation, based on platform settings.
     *
     * <p>
     *  仅适用于scrollType WHEEL_UNIT_SCROLL。表示根据平台设置,每次鼠标滚轮旋转时应滚动的单位数。
     * 
     * 
     * @see #getScrollAmount
     * @see #getScrollType
     */
    int scrollAmount;

    /**
     * Indicates how far the mouse wheel was rotated.
     *
     * <p>
     *  指示鼠标滚轮旋转的距离。
     * 
     * 
     * @see #getWheelRotation
     */
    int wheelRotation;

    /**
     * Indicates how far the mouse wheel was rotated.
     *
     * <p>
     *  指示鼠标滚轮旋转的距离。
     * 
     * 
     * @see #getPreciseWheelRotation
     */
    double preciseWheelRotation;

    /*
     * serialVersionUID
     * <p>
     *  serialVersionUID
     * 
     */

    private static final long serialVersionUID = 6459879390515399677L;

    /**
     * Constructs a <code>MouseWheelEvent</code> object with the
     * specified source component, type, modifiers, coordinates,
     * scroll type, scroll amount, and wheel rotation.
     * <p>Absolute coordinates xAbs and yAbs are set to source's location on screen plus
     * relative coordinates x and y. xAbs and yAbs are set to zero if the source is not showing.
     * <p>Note that passing in an invalid <code>id</code> results in
     * unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     * 构造具有指定源组件,类型,修饰符,坐标,滚动类型,滚动量和滚轮旋转的<code> MouseWheelEvent </code>对象。
     *  <p>绝对坐标xAbs和yAbs设置为源在屏幕上的位置加上相对坐标x和y。如果源不显示,xAbs和yAbs设置为零。 <p>请注意,传入无效的<code> id </code>会导致未指定的行为。
     * 如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source         the <code>Component</code> that originated
     *                       the event
     * @param id             the integer that identifies the event
     * @param when           a long that gives the time the event occurred
     * @param modifiers      the modifier keys down during event
     *                       (shift, ctrl, alt, meta)
     * @param x              the horizontal x coordinate for the mouse location
     * @param y              the vertical y coordinate for the mouse location
     * @param clickCount     the number of mouse clicks associated with event
     * @param popupTrigger   a boolean, true if this event is a trigger for a
     *                       popup-menu
     * @param scrollType     the type of scrolling which should take place in
     *                       response to this event;  valid values are
     *                       <code>WHEEL_UNIT_SCROLL</code> and
     *                       <code>WHEEL_BLOCK_SCROLL</code>
     * @param  scrollAmount  for scrollType <code>WHEEL_UNIT_SCROLL</code>,
     *                       the number of units to be scrolled
     * @param wheelRotation  the integer number of "clicks" by which the mouse
     *                       wheel was rotated
     *
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see MouseEvent#MouseEvent(java.awt.Component, int, long, int, int, int, int, boolean)
     * @see MouseEvent#MouseEvent(java.awt.Component, int, long, int, int, int, int, int, int, boolean, int)
     */
    public MouseWheelEvent (Component source, int id, long when, int modifiers,
                      int x, int y, int clickCount, boolean popupTrigger,
                      int scrollType, int scrollAmount, int wheelRotation) {

        this(source, id, when, modifiers, x, y, 0, 0, clickCount,
             popupTrigger, scrollType, scrollAmount, wheelRotation);
    }

    /**
     * Constructs a <code>MouseWheelEvent</code> object with the
     * specified source component, type, modifiers, coordinates,
     * absolute coordinates, scroll type, scroll amount, and wheel rotation.
     * <p>Note that passing in an invalid <code>id</code> results in
     * unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.<p>
     * Even if inconsistent values for relative and absolute coordinates are
     * passed to the constructor, the MouseWheelEvent instance is still
     * created and no exception is thrown.
     *
     * <p>
     *  构造具有指定源组件,类型,修饰符,坐标,绝对坐标,滚动类型,滚动量和滚轮旋转的<code> MouseWheelEvent </code>对象。
     *  <p>请注意,传入无效的<code> id </code>会导致未指定的行为。
     * 如果<code> source </code>是<code> null </code>,则此方法会抛出<code> IllegalArgumentException </code>。
     * <p>即使将相对和绝对坐标的不一致值传递给构造函数,仍然创建MouseWheelEvent实例,并且不抛出异常。
     * 
     * 
     * @param source         the <code>Component</code> that originated
     *                       the event
     * @param id             the integer that identifies the event
     * @param when           a long that gives the time the event occurred
     * @param modifiers      the modifier keys down during event
     *                       (shift, ctrl, alt, meta)
     * @param x              the horizontal x coordinate for the mouse location
     * @param y              the vertical y coordinate for the mouse location
     * @param xAbs           the absolute horizontal x coordinate for the mouse location
     * @param yAbs           the absolute vertical y coordinate for the mouse location
     * @param clickCount     the number of mouse clicks associated with event
     * @param popupTrigger   a boolean, true if this event is a trigger for a
     *                       popup-menu
     * @param scrollType     the type of scrolling which should take place in
     *                       response to this event;  valid values are
     *                       <code>WHEEL_UNIT_SCROLL</code> and
     *                       <code>WHEEL_BLOCK_SCROLL</code>
     * @param  scrollAmount  for scrollType <code>WHEEL_UNIT_SCROLL</code>,
     *                       the number of units to be scrolled
     * @param wheelRotation  the integer number of "clicks" by which the mouse
     *                       wheel was rotated
     *
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see MouseEvent#MouseEvent(java.awt.Component, int, long, int, int, int, int, boolean)
     * @see MouseEvent#MouseEvent(java.awt.Component, int, long, int, int, int, int, int, int, boolean, int)
     * @since 1.6
     */
    public MouseWheelEvent (Component source, int id, long when, int modifiers,
                            int x, int y, int xAbs, int yAbs, int clickCount, boolean popupTrigger,
                            int scrollType, int scrollAmount, int wheelRotation) {

        this(source, id, when, modifiers, x, y, xAbs, yAbs, clickCount, popupTrigger,
             scrollType, scrollAmount, wheelRotation, wheelRotation);

    }


    /**
     * Constructs a <code>MouseWheelEvent</code> object with the specified
     * source component, type, modifiers, coordinates, absolute coordinates,
     * scroll type, scroll amount, and wheel rotation.
     * <p>Note that passing in an invalid <code>id</code> parameter results
     * in unspecified behavior. This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code> equals
     * <code>null</code>.
     * <p>Even if inconsistent values for relative and absolute coordinates
     * are passed to the constructor, a <code>MouseWheelEvent</code> instance
     * is still created and no exception is thrown.
     *
     * <p>
     * 构造具有指定源组件,类型,修饰符,坐标,绝对坐标,滚动类型,滚动量和滚轮旋转的<code> MouseWheelEvent </code>对象。
     *  <p>请注意,传递无效的<code> id </code>参数会导致未指定的行为。
     * 如果<code> source </code>等于<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     *  <p>即使将相对和绝对坐标的不一致值传递给构造函数,仍会创建一个<code> MouseWheelEvent </code>实例,并且不会抛出任何异常。
     * 
     * 
     * @param source         the <code>Component</code> that originated the event
     * @param id             the integer value that identifies the event
     * @param when           a long value that gives the time when the event occurred
     * @param modifiers      the modifier keys down during event
     *                       (shift, ctrl, alt, meta)
     * @param x              the horizontal <code>x</code> coordinate for the
     *                       mouse location
     * @param y              the vertical <code>y</code> coordinate for the
     *                       mouse location
     * @param xAbs           the absolute horizontal <code>x</code> coordinate for
     *                       the mouse location
     * @param yAbs           the absolute vertical <code>y</code> coordinate for
     *                       the mouse location
     * @param clickCount     the number of mouse clicks associated with the event
     * @param popupTrigger   a boolean value, <code>true</code> if this event is a trigger
     *                       for a popup-menu
     * @param scrollType     the type of scrolling which should take place in
     *                       response to this event;  valid values are
     *                       <code>WHEEL_UNIT_SCROLL</code> and
     *                       <code>WHEEL_BLOCK_SCROLL</code>
     * @param  scrollAmount  for scrollType <code>WHEEL_UNIT_SCROLL</code>,
     *                       the number of units to be scrolled
     * @param wheelRotation  the integer number of "clicks" by which the mouse wheel
     *                       was rotated
     * @param preciseWheelRotation the double number of "clicks" by which the mouse wheel
     *                       was rotated
     *
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see MouseEvent#MouseEvent(java.awt.Component, int, long, int, int, int, int, boolean)
     * @see MouseEvent#MouseEvent(java.awt.Component, int, long, int, int, int, int, int, int, boolean, int)
     * @since 1.7
     */
    public MouseWheelEvent (Component source, int id, long when, int modifiers,
                            int x, int y, int xAbs, int yAbs, int clickCount, boolean popupTrigger,
                            int scrollType, int scrollAmount, int wheelRotation, double preciseWheelRotation) {

        super(source, id, when, modifiers, x, y, xAbs, yAbs, clickCount,
              popupTrigger, MouseEvent.NOBUTTON);

        this.scrollType = scrollType;
        this.scrollAmount = scrollAmount;
        this.wheelRotation = wheelRotation;
        this.preciseWheelRotation = preciseWheelRotation;

    }

    /**
     * Returns the type of scrolling that should take place in response to this
     * event.  This is determined by the native platform.  Legal values are:
     * <ul>
     * <li> MouseWheelEvent.WHEEL_UNIT_SCROLL
     * <li> MouseWheelEvent.WHEEL_BLOCK_SCROLL
     * </ul>
     *
     * <p>
     *  返回响应此事件时应进行的滚动类型。这由本机平台决定。法律价值包括：
     * <ul>
     *  <li> MouseWheelEvent.WHEEL_UNIT_SCROLL <li> MouseWheelEvent.WHEEL_BLOCK_SCROLL
     * </ul>
     * 
     * 
     * @return either MouseWheelEvent.WHEEL_UNIT_SCROLL or
     *  MouseWheelEvent.WHEEL_BLOCK_SCROLL, depending on the configuration of
     *  the native platform.
     * @see java.awt.Adjustable#getUnitIncrement
     * @see java.awt.Adjustable#getBlockIncrement
     * @see javax.swing.Scrollable#getScrollableUnitIncrement
     * @see javax.swing.Scrollable#getScrollableBlockIncrement
     */
    public int getScrollType() {
        return scrollType;
    }

    /**
     * Returns the number of units that should be scrolled per
     * click of mouse wheel rotation.
     * Only valid if <code>getScrollType</code> returns
     * <code>MouseWheelEvent.WHEEL_UNIT_SCROLL</code>
     *
     * <p>
     *  返回每次单击鼠标滚轮旋转时应滚动的单位数。
     * 仅在<code> getScrollType </code>返回<code> MouseWheelEvent.WHEEL_UNIT_SCROLL </code>时有效。
     * 
     * 
     * @return number of units to scroll, or an undefined value if
     *  <code>getScrollType</code> returns
     *  <code>MouseWheelEvent.WHEEL_BLOCK_SCROLL</code>
     * @see #getScrollType
     */
    public int getScrollAmount() {
        return scrollAmount;
    }

    /**
     * Returns the number of "clicks" the mouse wheel was rotated, as an integer.
     * A partial rotation may occur if the mouse supports a high-resolution wheel.
     * In this case, the method returns zero until a full "click" has been accumulated.
     *
     * <p>
     *  返回鼠标滚轮旋转的"点击次数"的整数。如果鼠标支持高分辨率轮,可能会发生部分旋转。在这种情况下,该方法返回零,直到累积完整的"点击"。
     * 
     * 
     * @return negative values if the mouse wheel was rotated up/away from
     * the user, and positive values if the mouse wheel was rotated down/
     * towards the user
     * @see #getPreciseWheelRotation
     */
    public int getWheelRotation() {
        return wheelRotation;
    }

    /**
     * Returns the number of "clicks" the mouse wheel was rotated, as a double.
     * A partial rotation may occur if the mouse supports a high-resolution wheel.
     * In this case, the return value will include a fractional "click".
     *
     * <p>
     *  返回鼠标滚轮旋转的"点击次数"作为双精度值。如果鼠标支持高分辨率轮,可能会发生部分旋转。在这种情况下,返回值将包括小数点击。
     * 
     * 
     * @return negative values if the mouse wheel was rotated up or away from
     * the user, and positive values if the mouse wheel was rotated down or
     * towards the user
     * @see #getWheelRotation
     * @since 1.7
     */
    public double getPreciseWheelRotation() {
        return preciseWheelRotation;
    }

    /**
     * This is a convenience method to aid in the implementation of
     * the common-case MouseWheelListener - to scroll a ScrollPane or
     * JScrollPane by an amount which conforms to the platform settings.
     * (Note, however, that <code>ScrollPane</code> and
     * <code>JScrollPane</code> already have this functionality built in.)
     * <P>
     * This method returns the number of units to scroll when scroll type is
     * MouseWheelEvent.WHEEL_UNIT_SCROLL, and should only be called if
     * <code>getScrollType</code> returns MouseWheelEvent.WHEEL_UNIT_SCROLL.
     * <P>
     * Direction of scroll, amount of wheel movement,
     * and platform settings for wheel scrolling are all accounted for.
     * This method does not and cannot take into account value of the
     * Adjustable/Scrollable unit increment, as this will vary among
     * scrolling components.
     * <P>
     * A simplified example of how this method might be used in a
     * listener:
     * <pre>
     *  mouseWheelMoved(MouseWheelEvent event) {
     *      ScrollPane sp = getScrollPaneFromSomewhere();
     *      Adjustable adj = sp.getVAdjustable()
     *      if (MouseWheelEvent.getScrollType() == WHEEL_UNIT_SCROLL) {
     *          int totalScrollAmount =
     *              event.getUnitsToScroll() *
     *              adj.getUnitIncrement();
     *          adj.setValue(adj.getValue() + totalScrollAmount);
     *      }
     *  }
     * </pre>
     *
     * <p>
     * 这是一个方便的方法来帮助实现常见的MouseWheelListener  - 滚动ScrollPane或JScrollPane一个符合平台设置的金额。
     *  (但请注意,<code> ScrollPane </code>和<code> JScrollPane </code>已内置此功能)。
     * <P>
     *  当滚动类型为MouseWheelEvent.WHEEL_UNIT_SCROLL时,此方法返回要滚动的单位数,并且应该仅在<code> getScrollType </code>返回MouseWheel
     * Event.WHEEL_UNIT_SCROLL时调用。
     * <P>
     *  所有考虑滚动方向,车轮移动量和用于车轮滚动的平台设置。此方法不会,也不能考虑可调整/可滚动单位增量的值,因为这将在滚动组件之间变化。
     * <P>
     *  关于如何在侦听器中使用此方法的简化示例：
     * 
     * @return the number of units to scroll based on the direction and amount
     *  of mouse wheel rotation, and on the wheel scrolling settings of the
     *  native platform
     * @see #getScrollType
     * @see #getScrollAmount
     * @see MouseWheelListener
     * @see java.awt.Adjustable
     * @see java.awt.Adjustable#getUnitIncrement
     * @see javax.swing.Scrollable
     * @see javax.swing.Scrollable#getScrollableUnitIncrement
     * @see java.awt.ScrollPane
     * @see java.awt.ScrollPane#setWheelScrollingEnabled
     * @see javax.swing.JScrollPane
     * @see javax.swing.JScrollPane#setWheelScrollingEnabled
     */
    public int getUnitsToScroll() {
        return scrollAmount * wheelRotation;
    }

    /**
     * Returns a parameter string identifying this event.
     * This method is useful for event-logging and for debugging.
     *
     * <p>
     * <pre>
     *  mouseWheelMoved(MouseWheelEvent event){ScrollPane sp = getScrollPaneFromSomewhere();可调整adj = sp.getVAdjustable()if(MouseWheelEvent.getScrollType()== WHEEL_UNIT_SCROLL){int totalScrollAmount = event.getUnitsToScroll()* adj.getUnitIncrement(); adj.setValue(adj.getValue()+ totalScrollAmount); }
     * }。
     * </pre>
     * 
     * @return a string identifying the event and its attributes
     */
    public String paramString() {
        String scrollTypeStr = null;

        if (getScrollType() == WHEEL_UNIT_SCROLL) {
            scrollTypeStr = "WHEEL_UNIT_SCROLL";
        }
        else if (getScrollType() == WHEEL_BLOCK_SCROLL) {
            scrollTypeStr = "WHEEL_BLOCK_SCROLL";
        }
        else {
            scrollTypeStr = "unknown scroll type";
        }
        return super.paramString()+",scrollType="+scrollTypeStr+
         ",scrollAmount="+getScrollAmount()+",wheelRotation="+
         getWheelRotation()+",preciseWheelRotation="+getPreciseWheelRotation();
    }
}
