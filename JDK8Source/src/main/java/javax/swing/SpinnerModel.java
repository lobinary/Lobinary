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

package javax.swing;

import java.awt.event.*;
import javax.swing.event.*;


/**
 * A model for a potentially unbounded sequence of object values.  This model
 * is similar to <code>ListModel</code> however there are some important differences:
 * <ul>
 * <li> The number of sequence elements isn't necessarily bounded.
 * <li> The model doesn't support indexed random access to sequence elements.
 *      Only three sequence values are accessible at a time: current, next and
 *      previous.
 * <li> The current sequence element, can be set.
 * </ul>
 * <p>
 * A <code>SpinnerModel</code> has three properties, only the first is read/write.
 * <dl>
 *   <dt><code>value</code>
 *   <dd>The current element of the sequence.
 *
 *   <dt><code>nextValue</code>
 *   <dd>The following element or null if <code>value</code> is the
 *     last element of the sequence.
 *
 *   <dt><code>previousValue</code>
 *   <dd>The preceding element or null if <code>value</code> is the
 *     first element of the sequence.
 * </dl>
 * When the the <code>value</code> property changes,
 * <code>ChangeListeners</code> are notified.  <code>SpinnerModel</code> may
 * choose to notify the <code>ChangeListeners</code> under other circumstances.
 *
 * <p>
 *  潜在无界序列对象值的模型。这个模型类似于<code> ListModel </code>,但是有一些重要的区别：
 * <ul>
 *  <li>序列元素的数量不一定有界。 <li>模型不支持对序列元素的索引随机访问。一次只能访问三个序列值：current,next和previous。 <li>当前序列元素,可以设置。
 * </ul>
 * <p>
 *  <code> SpinnerModel </code>有三个属性,只有第一个是读/写。
 * <dl>
 *  <dt> <code> value </code> <dd>序列的当前元素。
 * 
 *  <dt> <code> nextValue </code> <dd>以下元素或null如果<code> value </code>是序列的最后一个元素。
 * 
 *  <dt> <code> previousValue </code> <dd>前面的元素或null如果<code> value </code>是序列的第一个元素。
 * </dl>
 *  当<code> value </code>属性更改时,会通知<code> ChangeListeners </code>。
 *  <code> SpinnerModel </code>可以选择在其他情况下通知<code> ChangeListeners </code>。
 * 
 * 
 * @see JSpinner
 * @see AbstractSpinnerModel
 * @see SpinnerListModel
 * @see SpinnerNumberModel
 * @see SpinnerDateModel
 *
 * @author Hans Muller
 * @since 1.4
 */
public interface SpinnerModel
{
    /**
     * The <i>current element</i> of the sequence.  This element is usually
     * displayed by the <code>editor</code> part of a <code>JSpinner</code>.
     *
     * <p>
     *  <i>序列的<i>当前元素</i>。此元素通常由<code> JSpinner </code>的<code> editor </code>部分显示。
     * 
     * 
     * @return the current spinner value.
     * @see #setValue
     */
    Object getValue();


    /**
     * Changes current value of the model, typically this value is displayed
     * by the <code>editor</code> part of a  <code>JSpinner</code>.
     * If the <code>SpinnerModel</code> implementation doesn't support
     * the specified value then an <code>IllegalArgumentException</code>
     * is thrown.  For example a <code>SpinnerModel</code> for numbers might
     * only support values that are integer multiples of ten. In
     * that case, <code>model.setValue(new Number(11))</code>
     * would throw an exception.
     *
     * <p>
     * 更改模型的当前值,通常此值由<code> JSpinner </code>的<code> editor </code>部分显示。
     * 如果<code> SpinnerModel </code>实现不支持指定的值,那么会抛出<code> IllegalArgumentException </code>。
     * 例如,数字的<code> SpinnerModel </code>可能只支持十的整数倍的值。
     * 在这种情况下,<code> model.setValue(new Number(11))</code>会抛出异常。
     * 
     * 
     * @throws IllegalArgumentException if <code>value</code> isn't allowed
     * @see #getValue
     */
    void setValue(Object value);


    /**
     * Return the object in the sequence that comes after the object returned
     * by <code>getValue()</code>. If the end of the sequence has been reached
     * then return null.  Calling this method does not effect <code>value</code>.
     *
     * <p>
     *  返回<code> getValue()</code>返回的对象之后的序列中的对象。如果已经到达序列的结尾,则返回null。调用此方法不会影响<code> value </code>。
     * 
     * 
     * @return the next legal value or null if one doesn't exist
     * @see #getValue
     * @see #getPreviousValue
     */
    Object getNextValue();


    /**
     * Return the object in the sequence that comes before the object returned
     * by <code>getValue()</code>.  If the end of the sequence has been reached then
     * return null. Calling this method does not effect <code>value</code>.
     *
     * <p>
     *  返回<code> getValue()</code>返回的对象之前的序列中的对象。如果已经到达序列的结尾,则返回null。调用此方法不会影响<code> value </code>。
     * 
     * 
     * @return the previous legal value or null if one doesn't exist
     * @see #getValue
     * @see #getNextValue
     */
    Object getPreviousValue();


    /**
     * Adds a <code>ChangeListener</code> to the model's listener list.  The
     * <code>ChangeListeners</code> must be notified when models <code>value</code>
     * changes.
     *
     * <p>
     *  向模型的侦听器列表中添加<code> ChangeListener </code>。
     * 当模型<code> value </code>更改时,必须通知<code> ChangeListeners </code>。
     * 
     * 
     * @param l the ChangeListener to add
     * @see #removeChangeListener
     */
    void addChangeListener(ChangeListener l);


    /**
     * Removes a <code>ChangeListener</code> from the model's listener list.
     *
     * <p>
     * 
     * @param l the ChangeListener to remove
     * @see #addChangeListener
     */
    void removeChangeListener(ChangeListener l);
}
