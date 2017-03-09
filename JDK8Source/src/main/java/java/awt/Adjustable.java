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

package java.awt;

import java.awt.event.*;

import java.lang.annotation.Native;

/**
 * The interface for objects which have an adjustable numeric value
 * contained within a bounded range of values.
 *
 * <p>
 *  具有可调整数值的对象的接口包含在值的有界范围内。
 * 
 * 
 * @author Amy Fowler
 * @author Tim Prinzing
 */
public interface Adjustable {

    /**
     * Indicates that the <code>Adjustable</code> has horizontal orientation.
     * <p>
     *  表示<code> Adjustable </code>具有水平方向。
     * 
     */
    @Native public static final int HORIZONTAL = 0;

    /**
     * Indicates that the <code>Adjustable</code> has vertical orientation.
     * <p>
     *  表示<code>可调整</code>具有垂直方向。
     * 
     */
    @Native public static final int VERTICAL = 1;

    /**
     * Indicates that the <code>Adjustable</code> has no orientation.
     * <p>
     *  表示<code>可调整</code>没有方向。
     * 
     */
    @Native public static final int NO_ORIENTATION = 2;

    /**
     * Gets the orientation of the adjustable object.
     * <p>
     *  获取可调节对象的方向。
     * 
     * 
     * @return the orientation of the adjustable object;
     *   either <code>HORIZONTAL</code>, <code>VERTICAL</code>,
     *   or <code>NO_ORIENTATION</code>
     */
    int getOrientation();

    /**
     * Sets the minimum value of the adjustable object.
     * <p>
     *  设置可调节对象的最小值。
     * 
     * 
     * @param min the minimum value
     */
    void setMinimum(int min);

    /**
     * Gets the minimum value of the adjustable object.
     * <p>
     *  获取可调节对象的最小值。
     * 
     * 
     * @return the minimum value of the adjustable object
     */
    int getMinimum();

    /**
     * Sets the maximum value of the adjustable object.
     * <p>
     *  设置可调节对象的最大值。
     * 
     * 
     * @param max the maximum value
     */
    void setMaximum(int max);

    /**
     * Gets the maximum value of the adjustable object.
     * <p>
     *  获取可调节对象的最大值。
     * 
     * 
     * @return the maximum value of the adjustable object
     */
    int getMaximum();

    /**
     * Sets the unit value increment for the adjustable object.
     * <p>
     *  设置可调对象的单位值增量。
     * 
     * 
     * @param u the unit increment
     */
    void setUnitIncrement(int u);

    /**
     * Gets the unit value increment for the adjustable object.
     * <p>
     *  获取可调节对象的单位值增量。
     * 
     * 
     * @return the unit value increment for the adjustable object
     */
    int getUnitIncrement();

    /**
     * Sets the block value increment for the adjustable object.
     * <p>
     *  设置可调节对象的块值增量。
     * 
     * 
     * @param b the block increment
     */
    void setBlockIncrement(int b);

    /**
     * Gets the block value increment for the adjustable object.
     * <p>
     *  获取可调节对象的块值增量。
     * 
     * 
     * @return the block value increment for the adjustable object
     */
    int getBlockIncrement();

    /**
     * Sets the length of the proportional indicator of the
     * adjustable object.
     * <p>
     *  设置可调节对象的比例指示器的长度。
     * 
     * 
     * @param v the length of the indicator
     */
    void setVisibleAmount(int v);

    /**
     * Gets the length of the proportional indicator.
     * <p>
     *  获取比例指示器的长度。
     * 
     * 
     * @return the length of the proportional indicator
     */
    int getVisibleAmount();

    /**
     * Sets the current value of the adjustable object. If
     * the value supplied is less than <code>minimum</code>
     * or greater than <code>maximum</code> - <code>visibleAmount</code>,
     * then one of those values is substituted, as appropriate.
     * <p>
     * Calling this method does not fire an
     * <code>AdjustmentEvent</code>.
     *
     * <p>
     *  设置可调对象的当前值。
     * 如果提供的值小于<code> minimum </code>或大于<code> maximum </code>  -  <code> visibleAmount </code>,那么将适当替换这些值之一
     * 。
     *  设置可调对象的当前值。
     * <p>
     *  调用此方法不会触发<code> AdjustmentEvent </code>。
     * 
     * 
     * @param v the current value, between <code>minimum</code>
     *    and <code>maximum</code> - <code>visibleAmount</code>
     */
    void setValue(int v);

    /**
     * Gets the current value of the adjustable object.
     * <p>
     *  获取可调节对象的当前值。
     * 
     * 
     * @return the current value of the adjustable object
     */
    int getValue();

    /**
     * Adds a listener to receive adjustment events when the value of
     * the adjustable object changes.
     * <p>
     * 添加监听器以在可调对象的值更改时接收调整事件。
     * 
     * 
     * @param l the listener to receive events
     * @see AdjustmentEvent
     */
    void addAdjustmentListener(AdjustmentListener l);

    /**
     * Removes an adjustment listener.
     * <p>
     *  删除调整侦听器。
     * 
     * @param l the listener being removed
     * @see AdjustmentEvent
     */
    void removeAdjustmentListener(AdjustmentListener l);

}
