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

package javax.swing;

import javax.swing.event.*;


/**
 * Defines the data model used by components like <code>Slider</code>s
 * and <code>ProgressBar</code>s.
 * Defines four interrelated integer properties: minimum, maximum, extent
 * and value.  These four integers define two nested ranges like this:
 * <pre>
 * minimum &lt;= value &lt;= value+extent &lt;= maximum
 * </pre>
 * The outer range is <code>minimum,maximum</code> and the inner
 * range is <code>value,value+extent</code>.  The inner range
 * must lie within the outer one, i.e. <code>value</code> must be
 * less than or equal to <code>maximum</code> and <code>value+extent</code>
 * must greater than or equal to <code>minimum</code>, and <code>maximum</code>
 * must be greater than or equal to <code>minimum</code>.
 * There are a few features of this model that one might find a little
 * surprising.  These quirks exist for the convenience of the
 * Swing BoundedRangeModel clients, such as <code>Slider</code> and
 * <code>ScrollBar</code>.
 * <ul>
 * <li>
 *   The minimum and maximum set methods "correct" the other
 *   three properties to accommodate their new value argument.  For
 *   example setting the model's minimum may change its maximum, value,
 *   and extent properties (in that order), to maintain the constraints
 *   specified above.
 *
 * <li>
 *   The value and extent set methods "correct" their argument to
 *   fit within the limits defined by the other three properties.
 *   For example if <code>value == maximum</code>, <code>setExtent(10)</code>
 *   would change the extent (back) to zero.
 *
 * <li>
 *   The four BoundedRangeModel values are defined as Java Beans properties
 *   however Swing ChangeEvents are used to notify clients of changes rather
 *   than PropertyChangeEvents. This was done to keep the overhead of monitoring
 *   a BoundedRangeModel low. Changes are often reported at MouseDragged rates.
 * </ul>
 *
 * <p>
 *
 * For an example of specifying custom bounded range models used by sliders,
 * see <a
 href="http://www.oracle.com/technetwork/java/architecture-142923.html#separable">Separable model architecture</a>
 * in <em>A Swing Architecture Overview.</em>
 *
 * <p>
 *  定义由<code> Slider </code>和<code> ProgressBar </code>组件使用的数据模型。定义四个相关的整数属性：最小值,最大值,范围和值。
 * 这四个整数定义两个嵌套范围,如下所示：。
 * <pre>
 *  最小<=值<=值+范围<=最大
 * </pre>
 *  外部范围是<code> minimum,maximum </code>,内部范围是<code> value,value + extent </code>。
 * 内部范围必须在外部范围之内,即<code> value </code>必须小于或等于<code> maximum </code>和<code> value + extent </code>必须大于或等于
 * 到<code> minimum </code>,并且<code> maximum </code>必须大于或等于<code> minimum </code>。
 *  外部范围是<code> minimum,maximum </code>,内部范围是<code> value,value + extent </code>。
 * 这个模型的一些功能,人们可能会发现有点令人惊讶。
 * 这些怪癖存在为了方便Swing BoundedRangeModel客户端,例如<code> Slider </code>和<code> ScrollBar </code>。
 * <ul>
 * <li>
 *  最小和最大设置方法"正确"其他三个属性以适应其新的值参数。例如,设置模型的最小值可以更改其最大值,值和范围属性(以此顺序),以维持上述指定的约束。
 * 
 * <li>
 * 值和范围集方法"校正"他们的参数,以适合由其他三个属性定义的限制。
 * 例如,如果<code> value == maximum </code>,<code> setExtent(10)</code>会将范围(后退)更改为零。
 * 
 * <li>
 *  四个BoundedRangeModel值被定义为Java Bean属性,但是Swing ChangeEvents用于通知客户端更改而不是PropertyChangeEvents。
 * 这是为了保持监视BoundedRangeModel低的开销。更改通常以MouseDragged率报告。
 * </ul>
 * 
 * <p>
 * 
 *  有关指定滑块使用的自定义有界范围模型的示例,请参阅<a href="http://www.oracle.com/technetwork/java/architecture-142923.html#separable">
 * 可分离模型体系结构</a> <em> Swing架构概述</em>。
 * 
 * 
 * @author Hans Muller
 * @see DefaultBoundedRangeModel
 */
public interface BoundedRangeModel
{
    /**
     * Returns the minimum acceptable value.
     *
     * <p>
     *  返回最小可接受值。
     * 
     * 
     * @return the value of the minimum property
     * @see #setMinimum
     */
    int getMinimum();


    /**
     * Sets the model's minimum to <I>newMinimum</I>.   The
     * other three properties may be changed as well, to ensure
     * that:
     * <pre>
     * minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     * <p>
     * Notifies any listeners if the model changes.
     *
     * <p>
     *  将模型的最小值设置为<I> newMinimum </I>。其他三个属性也可以更改,以确保：
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     * <p>
     *  如果模型更改,则通知任何侦听器。
     * 
     * 
     * @param newMinimum the model's new minimum
     * @see #getMinimum
     * @see #addChangeListener
     */
    void setMinimum(int newMinimum);


    /**
     * Returns the model's maximum.  Note that the upper
     * limit on the model's value is (maximum - extent).
     *
     * <p>
     *  返回模型的最大值。注意,模型值的上限是(最大 - 范围)。
     * 
     * 
     * @return the value of the maximum property.
     * @see #setMaximum
     * @see #setExtent
     */
    int getMaximum();


    /**
     * Sets the model's maximum to <I>newMaximum</I>. The other
     * three properties may be changed as well, to ensure that
     * <pre>
     * minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     * <p>
     * Notifies any listeners if the model changes.
     *
     * <p>
     *  将模型的最大值设置为<I> newMaximum </I>。其他三个属性也可以更改,以确保
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     * <p>
     *  如果模型更改,则通知任何侦听器。
     * 
     * 
     * @param newMaximum the model's new maximum
     * @see #getMaximum
     * @see #addChangeListener
     */
    void setMaximum(int newMaximum);


    /**
     * Returns the model's current value.  Note that the upper
     * limit on the model's value is <code>maximum - extent</code>
     * and the lower limit is <code>minimum</code>.
     *
     * <p>
     * 返回模型的当前值。注意,模型值的上限是<code> maximum-extent </code>,下限是<code> minimum </code>。
     * 
     * 
     * @return  the model's value
     * @see     #setValue
     */
    int getValue();


    /**
     * Sets the model's current value to <code>newValue</code> if <code>newValue</code>
     * satisfies the model's constraints. Those constraints are:
     * <pre>
     * minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     * Otherwise, if <code>newValue</code> is less than <code>minimum</code>
     * it's set to <code>minimum</code>, if its greater than
     * <code>maximum</code> then it's set to <code>maximum</code>, and
     * if it's greater than <code>value+extent</code> then it's set to
     * <code>value+extent</code>.
     * <p>
     * When a BoundedRange model is used with a scrollbar the value
     * specifies the origin of the scrollbar knob (aka the "thumb" or
     * "elevator").  The value usually represents the origin of the
     * visible part of the object being scrolled.
     * <p>
     * Notifies any listeners if the model changes.
     *
     * <p>
     *  如果<code> newValue </code>满足模型的约束,则将模型的当前值设置为<code> newValue </code>。这些限制是：
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     *  否则,如果<code> newValue </code>小于<code> minimum </code>,则设置为<code> minimum </code>,如果大于<code> maximum </code>
     *  <code> maximum </code>,如果大于<code> value + extent </code>则设置为<code> value + extent </code>。
     * <p>
     *  当BoundedRange模型与滚动条一起使用时,该值指定滚动条旋钮(也称为"thumb"或"elevator")的原点。该值通常表示正在滚动的对象的可见部分的原点。
     * <p>
     *  如果模型更改,则通知任何侦听器。
     * 
     * 
     * @param newValue the model's new value
     * @see #getValue
     */
    void setValue(int newValue);


    /**
     * This attribute indicates that any upcoming changes to the value
     * of the model should be considered a single event. This attribute
     * will be set to true at the start of a series of changes to the value,
     * and will be set to false when the value has finished changing.  Normally
     * this allows a listener to only take action when the final value change in
     * committed, instead of having to do updates for all intermediate values.
     * <p>
     * Sliders and scrollbars use this property when a drag is underway.
     *
     * <p>
     *  此属性表示对模型值的任何即将发生的更改都应视为单个事件。此属性将在对值进行一系列更改开始时设置为true,并在值完成更改时设置为false。
     * 通常,这允许侦听器只在提交中的最终值更改时采取操作,而不必对所有中间值进行更新。
     * <p>
     *  当拖动正在进行时,滑块和滚动条使用此属性。
     * 
     * 
     * @param b true if the upcoming changes to the value property are part of a series
     */
    void setValueIsAdjusting(boolean b);


    /**
     * Returns true if the current changes to the value property are part
     * of a series of changes.
     *
     * <p>
     *  如果对value属性的当前更改是一系列更改的一部分,则返回true。
     * 
     * 
     * @return the valueIsAdjustingProperty.
     * @see #setValueIsAdjusting
     */
    boolean getValueIsAdjusting();


    /**
     * Returns the model's extent, the length of the inner range that
     * begins at the model's value.
     *
     * <p>
     * 返回模型的范围,以模型值开始的内部范围的长度。
     * 
     * 
     * @return  the value of the model's extent property
     * @see     #setExtent
     * @see     #setValue
     */
    int getExtent();


    /**
     * Sets the model's extent.  The <I>newExtent</I> is forced to
     * be greater than or equal to zero and less than or equal to
     * maximum - value.
     * <p>
     * When a BoundedRange model is used with a scrollbar the extent
     * defines the length of the scrollbar knob (aka the "thumb" or
     * "elevator").  The extent usually represents how much of the
     * object being scrolled is visible. When used with a slider,
     * the extent determines how much the value can "jump", for
     * example when the user presses PgUp or PgDn.
     * <p>
     * Notifies any listeners if the model changes.
     *
     * <p>
     *  设置模型的范围。 <I> newExtent </I>被强制为大于或等于零且小于或等于maximum-value。
     * <p>
     *  当BoundedRange模型与滚动条一起使用时,范围定义滚动条旋钮(也称为"缩略图"或"电梯")的长度。范围通常表示正在滚动的对象的多少是可见的。
     * 当与滑块一起使用时,范围确定该值可以"跳转"多少,例如,当用户按下PgUp或PgDn时。
     * <p>
     *  如果模型更改,则通知任何侦听器。
     * 
     * 
     * @param  newExtent the model's new extent
     * @see #getExtent
     * @see #setValue
     */
    void setExtent(int newExtent);



    /**
     * This method sets all of the model's data with a single method call.
     * The method results in a single change event being generated. This is
     * convenient when you need to adjust all the model data simultaneously and
     * do not want individual change events to occur.
     *
     * <p>
     *  此方法使用单个方法调用设置所有模型的数据。该方法导致生成单个更改事件。当需要同时调整所有模型数据并且不希望发生单个更改事件时,这非常方便。
     * 
     * 
     * @param value  an int giving the current value
     * @param extent an int giving the amount by which the value can "jump"
     * @param min    an int giving the minimum value
     * @param max    an int giving the maximum value
     * @param adjusting a boolean, true if a series of changes are in
     *                    progress
     *
     * @see #setValue
     * @see #setExtent
     * @see #setMinimum
     * @see #setMaximum
     * @see #setValueIsAdjusting
     */
    void setRangeProperties(int value, int extent, int min, int max, boolean adjusting);


    /**
     * Adds a ChangeListener to the model's listener list.
     *
     * <p>
     *  将ChangeListener添加到模型的侦听器列表。
     * 
     * 
     * @param x the ChangeListener to add
     * @see #removeChangeListener
     */
    void addChangeListener(ChangeListener x);


    /**
     * Removes a ChangeListener from the model's listener list.
     *
     * <p>
     *  从模型的侦听器列表中删除一个ChangeListener。
     * 
     * @param x the ChangeListener to remove
     * @see #addChangeListener
     */
    void removeChangeListener(ChangeListener x);

}
