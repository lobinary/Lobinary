/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Adjustable;
import java.awt.AWTEvent;
import java.lang.annotation.Native;


/**
 * The adjustment event emitted by Adjustable objects like
 * {@link java.awt.Scrollbar} and {@link java.awt.ScrollPane}.
 * When the user changes the value of the scrolling component,
 * it receives an instance of {@code AdjustmentEvent}.
 * <p>
 * An unspecified behavior will be caused if the {@code id} parameter
 * of any particular {@code AdjustmentEvent} instance is not
 * in the range from {@code ADJUSTMENT_FIRST} to {@code ADJUSTMENT_LAST}.
 * <p>
 * The {@code type} of any {@code AdjustmentEvent} instance takes one of the following
 * values:
 *                     <ul>
 *                     <li> {@code UNIT_INCREMENT}
 *                     <li> {@code UNIT_DECREMENT}
 *                     <li> {@code BLOCK_INCREMENT}
 *                     <li> {@code BLOCK_DECREMENT}
 *                     <li> {@code TRACK}
 *                     </ul>
 * Assigning the value different from listed above will cause an unspecified behavior.
 * <p>
 *  由{@link java.awt.Scrollbar}和{@link java.awt.ScrollPane}等可调整对象发出的调整事件。
 * 当用户更改滚动组件的值时,它接收{@code AdjustmentEvent}的实例。
 * <p>
 *  如果任何特定{@code AdjustmentEvent}实例的{@code id}参数不在{@code ADJUSTMENT_FIRST}到{@code ADJUSTMENT_LAST}的范围内,则
 * 会导致未指定的行为。
 * <p>
 *  任何{@code AdjustmentEvent}实例的{@code type}都会使用以下值之一：
 * <ul>
 *  <li> {@code UNIT_INCREMENT} <li> {@code UNIT_DECREMENT} <li> {@code BLOCK_INCREMENT} <li> {@code BLOCK_DECREMENT}
 *  <li> {@code TRACK}。
 * </ul>
 *  分配与上述不同的值将导致未指定的行为。
 * 
 * 
 * @see java.awt.Adjustable
 * @see AdjustmentListener
 *
 * @author Amy Fowler
 * @since 1.1
 */
public class AdjustmentEvent extends AWTEvent {

    /**
     * Marks the first integer id for the range of adjustment event ids.
     * <p>
     *  标记调整事件ID范围的第一个整数ID。
     * 
     */
    public static final int ADJUSTMENT_FIRST    = 601;

    /**
     * Marks the last integer id for the range of adjustment event ids.
     * <p>
     *  标记调整事件ID范围的最后一个整数ID。
     * 
     */
    public static final int ADJUSTMENT_LAST     = 601;

    /**
     * The adjustment value changed event.
     * <p>
     *  调整值更改事件。
     * 
     */
    public static final int ADJUSTMENT_VALUE_CHANGED = ADJUSTMENT_FIRST; //Event.SCROLL_LINE_UP

    /**
     * The unit increment adjustment type.
     * <p>
     *  单位增量调整类型。
     * 
     */
    @Native public static final int UNIT_INCREMENT      = 1;

    /**
     * The unit decrement adjustment type.
     * <p>
     *  单位递减调整类型。
     * 
     */
    @Native public static final int UNIT_DECREMENT      = 2;

    /**
     * The block decrement adjustment type.
     * <p>
     *  块减量调整类型。
     * 
     */
    @Native public static final int BLOCK_DECREMENT     = 3;

    /**
     * The block increment adjustment type.
     * <p>
     *  块增量调整类型。
     * 
     */
    @Native public static final int BLOCK_INCREMENT     = 4;

    /**
     * The absolute tracking adjustment type.
     * <p>
     *  绝对跟踪调整类型。
     * 
     */
    @Native public static final int TRACK               = 5;

    /**
     * The adjustable object that fired the event.
     *
     * <p>
     *  触发事件的可调节对象。
     * 
     * 
     * @serial
     * @see #getAdjustable
     */
    Adjustable adjustable;

    /**
     * <code>value</code> will contain the new value of the
     * adjustable object.  This value will always be  in a
     * range associated adjustable object.
     *
     * <p>
     *  <code> value </code>将包含可调对象的新值。此值始终位于与可调节对象相关的范围内。
     * 
     * 
     * @serial
     * @see #getValue
     */
    int value;

    /**
     * The <code>adjustmentType</code> describes how the adjustable
     * object value has changed.
     * This value can be increased/decreased by a block or unit amount
     * where the block is associated with page increments/decrements,
     * and a unit is associated with line increments/decrements.
     *
     * <p>
     * <code> adjustmentType </code>描述可调整对象值的更改方式。该值可以按块或单位量增加/减少,其中块与页增量/减量相关联,并且单位与行增量/减量相关联。
     * 
     * 
     * @serial
     * @see #getAdjustmentType
     */
    int adjustmentType;


    /**
     * The <code>isAdjusting</code> is true if the event is one
     * of the series of multiple adjustment events.
     *
     * <p>
     *  如果事件是多个调整事件系列中的一个,则<code> isAdjusting </code>为true。
     * 
     * 
     * @since 1.4
     * @serial
     * @see #getValueIsAdjusting
     */
    boolean isAdjusting;


    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = 5700290645205279921L;


    /**
     * Constructs an <code>AdjustmentEvent</code> object with the
     * specified <code>Adjustable</code> source, event type,
     * adjustment type, and value.
     * <p> This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     *  构造具有指定的<code> Adjustable </code>源,事件类型,调整类型和值的<code> AdjustmentEvent </code>对象。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source The <code>Adjustable</code> object where the
     *               event originated
     * @param id     An integer indicating the type of event.
     *                     For information on allowable values, see
     *                     the class description for {@link AdjustmentEvent}
     * @param type   An integer indicating the adjustment type.
     *                     For information on allowable values, see
     *                     the class description for {@link AdjustmentEvent}
     * @param value  The current value of the adjustment
     * @throws IllegalArgumentException if <code>source</code> is null
     * @see #getSource()
     * @see #getID()
     * @see #getAdjustmentType()
     * @see #getValue()
     */
    public AdjustmentEvent(Adjustable source, int id, int type, int value) {
        this(source, id, type, value, false);
    }

    /**
     * Constructs an <code>AdjustmentEvent</code> object with the
     * specified Adjustable source, event type, adjustment type, and value.
     * <p> This method throws an
     * <code>IllegalArgumentException</code> if <code>source</code>
     * is <code>null</code>.
     *
     * <p>
     *  构造具有指定的可调整源,事件类型,调整类型和值的<code> AdjustmentEvent </code>对象。
     *  <p>如果<code> source </code>是<code> null </code>,此方法会抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param source The <code>Adjustable</code> object where the
     *               event originated
     * @param id     An integer indicating the type of event.
     *                     For information on allowable values, see
     *                     the class description for {@link AdjustmentEvent}
     * @param type   An integer indicating the adjustment type.
     *                     For information on allowable values, see
     *                     the class description for {@link AdjustmentEvent}
     * @param value  The current value of the adjustment
     * @param isAdjusting A boolean that equals <code>true</code> if the event is one
     *               of a series of multiple adjusting events,
     *               otherwise <code>false</code>
     * @throws IllegalArgumentException if <code>source</code> is null
     * @since 1.4
     * @see #getSource()
     * @see #getID()
     * @see #getAdjustmentType()
     * @see #getValue()
     * @see #getValueIsAdjusting()
     */
    public AdjustmentEvent(Adjustable source, int id, int type, int value, boolean isAdjusting) {
        super(source, id);
        adjustable = source;
        this.adjustmentType = type;
        this.value = value;
        this.isAdjusting = isAdjusting;
    }

    /**
     * Returns the <code>Adjustable</code> object where this event originated.
     *
     * <p>
     *  返回此事件所源自的<code>可调整</code>对象。
     * 
     * 
     * @return the <code>Adjustable</code> object where this event originated
     */
    public Adjustable getAdjustable() {
        return adjustable;
    }

    /**
     * Returns the current value in the adjustment event.
     *
     * <p>
     *  返回调整事件中的当前值。
     * 
     * 
     * @return the current value in the adjustment event
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the type of adjustment which caused the value changed
     * event.  It will have one of the following values:
     * <ul>
     * <li>{@link #UNIT_INCREMENT}
     * <li>{@link #UNIT_DECREMENT}
     * <li>{@link #BLOCK_INCREMENT}
     * <li>{@link #BLOCK_DECREMENT}
     * <li>{@link #TRACK}
     * </ul>
     * <p>
     *  返回导致值更改事件的调整类型。它将具有以下值之一：
     * <ul>
     *  <li> {@ link #UNIT_INCREMENT} <li> {@ link #UNIT_DECREMENT} <li> {@ link #BLOCK_INCREMENT} <li> {@ link #BLOCK_DECREMENT}
     *  <li> {@ link #TRACK}。
     * 
     * @return one of the adjustment values listed above
     */
    public int getAdjustmentType() {
        return adjustmentType;
    }

    /**
     * Returns <code>true</code> if this is one of multiple
     * adjustment events.
     *
     * <p>
     * </ul>
     * 
     * @return <code>true</code> if this is one of multiple
     *         adjustment events, otherwise returns <code>false</code>
     * @since 1.4
     */
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }

    public String paramString() {
        String typeStr;
        switch(id) {
          case ADJUSTMENT_VALUE_CHANGED:
              typeStr = "ADJUSTMENT_VALUE_CHANGED";
              break;
          default:
              typeStr = "unknown type";
        }
        String adjTypeStr;
        switch(adjustmentType) {
          case UNIT_INCREMENT:
              adjTypeStr = "UNIT_INCREMENT";
              break;
          case UNIT_DECREMENT:
              adjTypeStr = "UNIT_DECREMENT";
              break;
          case BLOCK_INCREMENT:
              adjTypeStr = "BLOCK_INCREMENT";
              break;
          case BLOCK_DECREMENT:
              adjTypeStr = "BLOCK_DECREMENT";
              break;
          case TRACK:
              adjTypeStr = "TRACK";
              break;
          default:
              adjTypeStr = "unknown type";
        }
        return typeStr
            + ",adjType="+adjTypeStr
            + ",value="+value
            + ",isAdjusting="+isAdjusting;
    }
}
