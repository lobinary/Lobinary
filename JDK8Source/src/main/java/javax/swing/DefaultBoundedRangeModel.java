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
import java.io.Serializable;
import java.util.EventListener;

/**
 * A generic implementation of BoundedRangeModel.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  BoundedRangeModel的通用实现
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将不与未来的Swing版本兼容当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 支持长期存储所有JavaBeans&trade;已添加到<code> javabeans </code>包中请参见{@link javabeansXMLEncoder}。
 * 
 * 
 * @author David Kloba
 * @author Hans Muller
 * @see BoundedRangeModel
 */
public class DefaultBoundedRangeModel implements BoundedRangeModel, Serializable
{
    /**
     * Only one <code>ChangeEvent</code> is needed per model instance since the
     * event's only (read-only) state is the source property.  The source
     * of events generated here is always "this".
     * <p>
     *  由于事件的唯一(只读)状态是源属性,因此每个模型实例只需要一个<code> ChangeEvent </code>。此处生成的事件源始终为"this"
     * 
     */
    protected transient ChangeEvent changeEvent = null;

    /** The listeners waiting for model changes. */
    protected EventListenerList listenerList = new EventListenerList();

    private int value = 0;
    private int extent = 0;
    private int min = 0;
    private int max = 100;
    private boolean isAdjusting = false;


    /**
     * Initializes all of the properties with default values.
     * Those values are:
     * <ul>
     * <li><code>value</code> = 0
     * <li><code>extent</code> = 0
     * <li><code>minimum</code> = 0
     * <li><code>maximum</code> = 100
     * <li><code>adjusting</code> = false
     * </ul>
     * <p>
     * 使用默认值初始化所有属性这些值为：
     * <ul>
     *  <li> <code> value </code> = 0 <li> <code> extent </code> = 0 <li> <code> = 100 <li> <code>调整</code> 
     * = false。
     * </ul>
     */
    public DefaultBoundedRangeModel() {
    }


    /**
     * Initializes value, extent, minimum and maximum. Adjusting is false.
     * Throws an <code>IllegalArgumentException</code> if the following
     * constraints aren't satisfied:
     * <pre>
     * min &lt;= value &lt;= value+extent &lt;= max
     * </pre>
     * <p>
     *  初始化值,范围,最小值和最大值调整为false如果不满足以下约束,则抛出<code> IllegalArgumentException </code>：
     * <pre>
     *  min <= value <= value + extent <= max
     * </pre>
     */
    public DefaultBoundedRangeModel(int value, int extent, int min, int max)
    {
        if ((max >= min) &&
            (value >= min) &&
            ((value + extent) >= value) &&
            ((value + extent) <= max)) {
            this.value = value;
            this.extent = extent;
            this.min = min;
            this.max = max;
        }
        else {
            throw new IllegalArgumentException("invalid range properties");
        }
    }


    /**
     * Returns the model's current value.
     * <p>
     *  返回模型的当前值
     * 
     * 
     * @return the model's current value
     * @see #setValue
     * @see BoundedRangeModel#getValue
     */
    public int getValue() {
      return value;
    }


    /**
     * Returns the model's extent.
     * <p>
     *  返回模型的范围
     * 
     * 
     * @return the model's extent
     * @see #setExtent
     * @see BoundedRangeModel#getExtent
     */
    public int getExtent() {
      return extent;
    }


    /**
     * Returns the model's minimum.
     * <p>
     *  返回模型的最小值
     * 
     * 
     * @return the model's minimum
     * @see #setMinimum
     * @see BoundedRangeModel#getMinimum
     */
    public int getMinimum() {
      return min;
    }


    /**
     * Returns the model's maximum.
     * <p>
     *  返回模型的最大值
     * 
     * 
     * @return  the model's maximum
     * @see #setMaximum
     * @see BoundedRangeModel#getMaximum
     */
    public int getMaximum() {
        return max;
    }


    /**
     * Sets the current value of the model. For a slider, that
     * determines where the knob appears. Ensures that the new
     * value, <I>n</I> falls within the model's constraints:
     * <pre>
     *     minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     *
     * <p>
     *  设置模型的当前值对于确定旋钮显示位置的滑块,确保新值<I> n </I>落在模型的约束内：
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     * 
     * 
     * @see BoundedRangeModel#setValue
     */
    public void setValue(int n) {
        n = Math.min(n, Integer.MAX_VALUE - extent);

        int newValue = Math.max(n, min);
        if (newValue + extent > max) {
            newValue = max - extent;
        }
        setRangeProperties(newValue, extent, min, max, isAdjusting);
    }


    /**
     * Sets the extent to <I>n</I> after ensuring that <I>n</I>
     * is greater than or equal to zero and falls within the model's
     * constraints:
     * <pre>
     *     minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     * <p>
     * 在确保<I> n </I>大于或等于零并落入模型的约束条件后,将范围设置为<I> n </I>
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     * 
     * @see BoundedRangeModel#setExtent
     */
    public void setExtent(int n) {
        int newExtent = Math.max(0, n);
        if(value + newExtent > max) {
            newExtent = max - value;
        }
        setRangeProperties(value, newExtent, min, max, isAdjusting);
    }


    /**
     * Sets the minimum to <I>n</I> after ensuring that <I>n</I>
     * that the other three properties obey the model's constraints:
     * <pre>
     *     minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     * <p>
     *  确保<I> n </I>之后,其他三个属性遵守模型的约束,将最小值设置为<I> n </I>
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     * 
     * @see #getMinimum
     * @see BoundedRangeModel#setMinimum
     */
    public void setMinimum(int n) {
        int newMax = Math.max(n, max);
        int newValue = Math.max(n, value);
        int newExtent = Math.min(newMax - newValue, extent);
        setRangeProperties(newValue, newExtent, n, newMax, isAdjusting);
    }


    /**
     * Sets the maximum to <I>n</I> after ensuring that <I>n</I>
     * that the other three properties obey the model's constraints:
     * <pre>
     *     minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     * <p>
     *  在确保<I> n </I>之后,其他三个属性遵守模型的约束,将最大值设置为<I> n </I>
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     * 
     * @see BoundedRangeModel#setMaximum
     */
    public void setMaximum(int n) {
        int newMin = Math.min(n, min);
        int newExtent = Math.min(n - newMin, extent);
        int newValue = Math.min(n - newExtent, value);
        setRangeProperties(newValue, newExtent, newMin, n, isAdjusting);
    }


    /**
     * Sets the <code>valueIsAdjusting</code> property.
     *
     * <p>
     *  设置<code> valueIsAdjusting </code>属性
     * 
     * 
     * @see #getValueIsAdjusting
     * @see #setValue
     * @see BoundedRangeModel#setValueIsAdjusting
     */
    public void setValueIsAdjusting(boolean b) {
        setRangeProperties(value, extent, min, max, b);
    }


    /**
     * Returns true if the value is in the process of changing
     * as a result of actions being taken by the user.
     *
     * <p>
     *  如果值处于由用户执行的操作的结果而发生更改的过程中,则返回true
     * 
     * 
     * @return the value of the <code>valueIsAdjusting</code> property
     * @see #setValue
     * @see BoundedRangeModel#getValueIsAdjusting
     */
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }


    /**
     * Sets all of the <code>BoundedRangeModel</code> properties after forcing
     * the arguments to obey the usual constraints:
     * <pre>
     *     minimum &lt;= value &lt;= value+extent &lt;= maximum
     * </pre>
     * <p>
     * At most, one <code>ChangeEvent</code> is generated.
     *
     * <p>
     * 在强制参数遵守通常的约束后,设置所有<code> BoundedRangeModel </code>属性：
     * <pre>
     *  最小<=值<=值+范围<=最大
     * </pre>
     * <p>
     *  最多会生成一个<code> ChangeEvent </code>
     * 
     * 
     * @see BoundedRangeModel#setRangeProperties
     * @see #setValue
     * @see #setExtent
     * @see #setMinimum
     * @see #setMaximum
     * @see #setValueIsAdjusting
     */
    public void setRangeProperties(int newValue, int newExtent, int newMin, int newMax, boolean adjusting)
    {
        if (newMin > newMax) {
            newMin = newMax;
        }
        if (newValue > newMax) {
            newMax = newValue;
        }
        if (newValue < newMin) {
            newMin = newValue;
        }

        /* Convert the addends to long so that extent can be
         * Integer.MAX_VALUE without rolling over the sum.
         * A JCK test covers this, see bug 4097718.
         * <p>
         *  IntegerMAX_VALUE无需滚动总和JCK测试涵盖了这一点,请参见错误4097718
         * 
         */
        if (((long)newExtent + (long)newValue) > newMax) {
            newExtent = newMax - newValue;
        }

        if (newExtent < 0) {
            newExtent = 0;
        }

        boolean isChange =
            (newValue != value) ||
            (newExtent != extent) ||
            (newMin != min) ||
            (newMax != max) ||
            (adjusting != isAdjusting);

        if (isChange) {
            value = newValue;
            extent = newExtent;
            min = newMin;
            max = newMax;
            isAdjusting = adjusting;

            fireStateChanged();
        }
    }


    /**
     * Adds a <code>ChangeListener</code>.  The change listeners are run each
     * time any one of the Bounded Range model properties changes.
     *
     * <p>
     *  添加一个<code> ChangeListener </code>每次有边界范围模型属性更改时,都会运行更改侦听器
     * 
     * 
     * @param l the ChangeListener to add
     * @see #removeChangeListener
     * @see BoundedRangeModel#addChangeListener
     */
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }


    /**
     * Removes a <code>ChangeListener</code>.
     *
     * <p>
     *  删除<code> ChangeListener </code>
     * 
     * 
     * @param l the <code>ChangeListener</code> to remove
     * @see #addChangeListener
     * @see BoundedRangeModel#removeChangeListener
     */
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }


    /**
     * Returns an array of all the change listeners
     * registered on this <code>DefaultBoundedRangeModel</code>.
     *
     * <p>
     *  返回在此<code> DefaultBoundedRangeModel </code>上注册的所有更改侦听器的数组
     * 
     * 
     * @return all of this model's <code>ChangeListener</code>s
     *         or an empty
     *         array if no change listeners are currently registered
     *
     * @see #addChangeListener
     * @see #removeChangeListener
     *
     * @since 1.4
     */
    public ChangeListener[] getChangeListeners() {
        return listenerList.getListeners(ChangeListener.class);
    }


    /**
     * Runs each <code>ChangeListener</code>'s <code>stateChanged</code> method.
     *
     * <p>
     *  运行每个<code> ChangeListener </code>的<code> stateChanged </code>方法
     * 
     * 
     * @see #setRangeProperties
     * @see EventListenerList
     */
    protected void fireStateChanged()
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -=2 ) {
            if (listeners[i] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }
        }
    }


    /**
     * Returns a string that displays all of the
     * <code>BoundedRangeModel</code> properties.
     * <p>
     *  返回显示所有<code> BoundedRangeModel </code>属性的字符串
     * 
     */
    public String toString()  {
        String modelString =
            "value=" + getValue() + ", " +
            "extent=" + getExtent() + ", " +
            "min=" + getMinimum() + ", " +
            "max=" + getMaximum() + ", " +
            "adj=" + getValueIsAdjusting();

        return getClass().getName() + "[" + modelString + "]";
    }

    /**
     * Returns an array of all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s
     * upon this model.
     * <code><em>Foo</em>Listener</code>s
     * are registered using the <code>add<em>Foo</em>Listener</code> method.
     * <p>
     * You can specify the <code>listenerType</code> argument
     * with a class literal, such as <code><em>Foo</em>Listener.class</code>.
     * For example, you can query a <code>DefaultBoundedRangeModel</code>
     * instance <code>m</code>
     * for its change listeners
     * with the following code:
     *
     * <pre>ChangeListener[] cls = (ChangeListener[])(m.getListeners(ChangeListener.class));</pre>
     *
     * If no such listeners exist,
     * this method returns an empty array.
     *
     * <p>
     * 返回当前注册为<code> <em> Foo </em>侦听器</code>的所有对象的数组</code>在此模型<code> <em> </em> <code>添加<em> </em>侦听器</code>
     * 方法。
     * <p>
     *  您可以使用类文字指定<code> listenerType </code>参数,例如<code> <em> Foo </em> Listenerclass </code>例如,可以查询<code> D
     * efaultBoundedRangeModel </code > instance <code> m </code>为其更改监听器使用以下代码：。
     * 
     * @param listenerType  the type of listeners requested;
     *          this parameter should specify an interface
     *          that descends from <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *          <code><em>Foo</em>Listener</code>s
     *          on this model,
     *          or an empty array if no such
     *          listeners have been added
     * @exception ClassCastException if <code>listenerType</code> doesn't
     *          specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @see #getChangeListeners
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }
}
