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

package javax.accessibility;

/**
 * The AccessibleValue interface should be supported by any object
 * that supports a numerical value (e.g., a scroll bar).  This interface
 * provides the standard mechanism for an assistive technology to determine
 * and set the numerical value as well as get the minimum and maximum values.
 * Applications can determine
 * if an object supports the AccessibleValue interface by first
 * obtaining its AccessibleContext (see
 * {@link Accessible}) and then calling the
 * {@link AccessibleContext#getAccessibleValue} method.
 * If the return value is not null, the object supports this interface.
 *
 * <p>
 *  支持数值的任何对象都应支持AccessibleValue接口(例如,滚动条)。此接口为辅助技术提供了标准机制,用于确定和设置数值以及获取最小值和最大值。
 * 应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用{@link AccessibleContext#getAccessibleValue}方
 * 法来确定对象是否支持AccessibleValue接口。
 *  支持数值的任何对象都应支持AccessibleValue接口(例如,滚动条)。此接口为辅助技术提供了标准机制,用于确定和设置数值以及获取最小值和最大值。如果返回值不为null,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleValue
 *
 * @author      Peter Korn
 * @author      Hans Muller
 * @author      Willie Walker
 */
public interface AccessibleValue {

    /**
     * Get the value of this object as a Number.  If the value has not been
     * set, the return value will be null.
     *
     * <p>
     *  获取此对象的值作为数字。如果未设置该值,则返回值将为null。
     * 
     * 
     * @return value of the object
     * @see #setCurrentAccessibleValue
     */
    public Number getCurrentAccessibleValue();

    /**
     * Set the value of this object as a Number.
     *
     * <p>
     *  将此对象的值设置为Number。
     * 
     * 
     * @param n the number to use for the value
     * @return True if the value was set; else False
     * @see #getCurrentAccessibleValue
     */
    public boolean setCurrentAccessibleValue(Number n);

//    /**
//     * Get the description of the value of this object.
//     *
//     * <p>
//     *  // *获取此对象的值的描述。 // *
//     * 
//     * 
//     * @return description of the value of the object
//     */
//    public String getAccessibleValueDescription();

    /**
     * Get the minimum value of this object as a Number.
     *
     * <p>
     *  获取此对象的最小值作为数字。
     * 
     * 
     * @return Minimum value of the object; null if this object does not
     * have a minimum value
     * @see #getMaximumAccessibleValue
     */
    public Number getMinimumAccessibleValue();

    /**
     * Get the maximum value of this object as a Number.
     *
     * <p>
     *  获取此对象的最大值作为数字。
     * 
     * @return Maximum value of the object; null if this object does not
     * have a maximum value
     * @see #getMinimumAccessibleValue
     */
    public Number getMaximumAccessibleValue();
}
