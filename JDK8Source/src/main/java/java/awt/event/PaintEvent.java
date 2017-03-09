/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
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
import java.awt.Rectangle;

/**
 * The component-level paint event.
 * This event is a special type which is used to ensure that
 * paint/update method calls are serialized along with the other
 * events delivered from the event queue.  This event is not
 * designed to be used with the Event Listener model; programs
 * should continue to override paint/update methods in order
 * render themselves properly.
 * <p>
 * An unspecified behavior will be caused if the {@code id} parameter
 * of any particular {@code PaintEvent} instance is not
 * in the range from {@code PAINT_FIRST} to {@code PAINT_LAST}.
 *
 * <p>
 *  组件级paint事件。此事件是一种特殊类型,用于确保paint / update方法调用与从事件队列传递的其他事件一起被序列化。
 * 此事件不适用于事件监听器模型;程序应该继续覆盖paint / update方法,以便正确地呈现它们。
 * <p>
 *  如果任何特定{@code PaintEvent}实例的{@code id}参数不在{@code PAINT_FIRST}到{@code PAINT_LAST}的范围内,则会导致未指定的行为。
 * 
 * 
 * @author Amy Fowler
 * @since 1.1
 */
public class PaintEvent extends ComponentEvent {

    /**
     * Marks the first integer id for the range of paint event ids.
     * <p>
     *  标记paint事件标识的范围的第一个整数ID。
     * 
     */
    public static final int PAINT_FIRST         = 800;

    /**
     * Marks the last integer id for the range of paint event ids.
     * <p>
     *  标记paint事件标识的范围的最后整数ID。
     * 
     */
    public static final int PAINT_LAST          = 801;

    /**
     * The paint event type.
     * <p>
     *  paint事件类型。
     * 
     */
    public static final int PAINT = PAINT_FIRST;

    /**
     * The update event type.
     * <p>
     *  更新事件类型。
     * 
     */
    public static final int UPDATE = PAINT_FIRST + 1; //801

    /**
     * This is the rectangle that represents the area on the source
     * component that requires a repaint.
     * This rectangle should be non null.
     *
     * <p>
     *  这是表示源组件上需要重绘的区域的矩形。此矩形应为非空。
     * 
     * 
     * @serial
     * @see java.awt.Rectangle
     * @see #setUpdateRect(Rectangle)
     * @see #getUpdateRect()
     */
    Rectangle updateRect;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
    private static final long serialVersionUID = 1267492026433337593L;

    /**
     * Constructs a <code>PaintEvent</code> object with the specified
     * source component and type.
     * <p> This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     *  构造具有指定的源组件和类型的<code> PaintEvent </code>对象。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source     The object where the event originated
     * @param id           The integer that identifies the event type.
     *                     For information on allowable values, see
     *                     the class description for {@link PaintEvent}
     * @param updateRect The rectangle area which needs to be repainted
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getSource()
     * @see #getID()
     * @see #getUpdateRect()
     */
    public PaintEvent(Component source, int id, Rectangle updateRect) {
        super(source, id);
        this.updateRect = updateRect;
    }

    /**
     * Returns the rectangle representing the area which needs to be
     * repainted in response to this event.
     * <p>
     *  返回表示响应此事件需要重绘的区域的矩形。
     * 
     */
    public Rectangle getUpdateRect() {
        return updateRect;
    }

    /**
     * Sets the rectangle representing the area which needs to be
     * repainted in response to this event.
     * <p>
     *  设置表示需要重新绘制的区域的矩形以响应此事件。
     * 
     * @param updateRect the rectangle area which needs to be repainted
     */
    public void setUpdateRect(Rectangle updateRect) {
        this.updateRect = updateRect;
    }

    public String paramString() {
        String typeStr;
        switch(id) {
          case PAINT:
              typeStr = "PAINT";
              break;
          case UPDATE:
              typeStr = "UPDATE";
              break;
          default:
              typeStr = "unknown type";
        }
        return typeStr + ",updateRect="+(updateRect != null ? updateRect.toString() : "null");
    }
}
