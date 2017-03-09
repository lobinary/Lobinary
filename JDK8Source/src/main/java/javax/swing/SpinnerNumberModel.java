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

import java.util.*;
import java.io.Serializable;


/**
 * A <code>SpinnerModel</code> for sequences of numbers.
 * The upper and lower bounds of the sequence are defined
 * by properties called <code>minimum</code> and
 * <code>maximum</code>. The size of the increase or decrease
 * computed by the <code>nextValue</code> and
 * <code>previousValue</code> methods is defined by a property called
 * <code>stepSize</code>.  The <code>minimum</code> and
 * <code>maximum</code> properties can be <code>null</code>
 * to indicate that the sequence has no lower or upper limit.
 * All of the properties in this class are defined in terms of two
 * generic types: <code>Number</code> and
 * <code>Comparable</code>, so that all Java numeric types
 * may be accommodated.  Internally, there's only support for
 * values whose type is one of the primitive <code>Number</code> types:
 * <code>Double</code>, <code>Float</code>, <code>Long</code>,
 * <code>Integer</code>, <code>Short</code>, or <code>Byte</code>.
 * <p>
 * To create a <code>SpinnerNumberModel</code> for the integer
 * range zero to one hundred, with
 * fifty as the initial value, one could write:
 * <pre>
 * Integer value = new Integer(50);
 * Integer min = new Integer(0);
 * Integer max = new Integer(100);
 * Integer step = new Integer(1);
 * SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, step);
 * int fifty = model.getNumber().intValue();
 * </pre>
 * <p>
 * Spinners for integers and doubles are common, so special constructors
 * for these cases are provided.  For example to create the model in
 * the previous example, one could also write:
 * <pre>
 * SpinnerNumberModel model = new SpinnerNumberModel(50, 0, 100, 1);
 * </pre>
 * <p>
 * This model inherits a <code>ChangeListener</code>.
 * The <code>ChangeListeners</code> are notified
 * whenever the model's <code>value</code>, <code>stepSize</code>,
 * <code>minimum</code>, or <code>maximum</code> properties changes.
 *
 * <p>
 *  用于数字序列的<code> SpinnerModel </code>。序列的上限和下限由称为<code> minimum </code>和<code> maximum </code>的属性定义。
 * 由<code> nextValue </code>和<code> previousValue </code>方法计算的增加或减少的大小由称为<code> stepSize </code>的属性定义。
 *  <code> minimum </code>和<code> maximum </code>属性可以是<code> null </code>,表示序列没有下限或上限。
 * 该类中的所有属性都是根据两种通用类型定义的：<code> Number </code>和<code> Comparable </code>,以便可以容纳所有Java数值类型。
 * 在内部,只支持类型为原始<code> Number </code>类型之一的值：<code> Double </code>,<code> Float </code>,<code> Long </code>
 *  ,<code> Integer </code>,<code> Short </code>或<code> Byte </code>。
 * 该类中的所有属性都是根据两种通用类型定义的：<code> Number </code>和<code> Comparable </code>,以便可以容纳所有Java数值类型。
 * <p>
 *  要为0到100的整数范围创建一个<code> SpinnerNumberModel </code>,将50作为初始值,可以写为：
 * <pre>
 *  整数值= new整数(50); Integer min = new Integer(0); Integer max = new Integer(100);整数step = new整数(1); Spin
 * nerNumberModel model = new SpinnerNumberModel(value,min,max,step); int fifty = model.getNumber()。
 * intValue();。
 * </pre>
 * <p>
 * 整数和双精度的旋转是常见的,因此提供了这些情况的特殊构造函数。例如,要在上一个示例中创建模型,还可以写为：
 * <pre>
 *  SpinnerNumberModel model = new SpinnerNumberModel(50,0,100,1);
 * </pre>
 * <p>
 *  这个模型继承一个<code> ChangeListener </code>。
 * 当模型的<code>值</code>,<code> stepSize </code>,<code> minimum </code>或<code> maximum </code>时,通知<code> Ch
 * angeListeners </code>属性变化。
 *  这个模型继承一个<code> ChangeListener </code>。
 * 
 * 
 * @see JSpinner
 * @see SpinnerModel
 * @see AbstractSpinnerModel
 * @see SpinnerListModel
 * @see SpinnerDateModel
 *
 * @author Hans Muller
 * @since 1.4
*/
public class SpinnerNumberModel extends AbstractSpinnerModel implements Serializable
{
    private Number stepSize, value;
    private Comparable minimum, maximum;


    /**
     * Constructs a <code>SpinnerModel</code> that represents
     * a closed sequence of
     * numbers from <code>minimum</code> to <code>maximum</code>.  The
     * <code>nextValue</code> and <code>previousValue</code> methods
     * compute elements of the sequence by adding or subtracting
     * <code>stepSize</code> respectively.  All of the parameters
     * must be mutually <code>Comparable</code>, <code>value</code>
     * and <code>stepSize</code> must be instances of <code>Integer</code>
     * <code>Long</code>, <code>Float</code>, or <code>Double</code>.
     * <p>
     * The <code>minimum</code> and <code>maximum</code> parameters
     * can be <code>null</code> to indicate that the range doesn't
     * have an upper or lower bound.
     * If <code>value</code> or <code>stepSize</code> is <code>null</code>,
     * or if both <code>minimum</code> and <code>maximum</code>
     * are specified and <code>minimum &gt; maximum</code> then an
     * <code>IllegalArgumentException</code> is thrown.
     * Similarly if <code>(minimum &lt;= value &lt;= maximum</code>) is false,
     * an <code>IllegalArgumentException</code> is thrown.
     *
     * <p>
     *  构造一个代表从<code>最小</code>到<code>最大</code>的封闭数字序列的<code> SpinnerModel </code>。
     *  <code> nextValue </code>和<code> previousValue </code>方法分别通过添加或减去<code> stepSize </code>来计算序列的元素。
     * 所有的参数必须是<code> Comparable </code>,<code> value </code>和<code> stepSize </code>必须是<code> Integer </code>
     *  / code>,<code> Float </code>或<code> Double </code>。
     *  <code> nextValue </code>和<code> previousValue </code>方法分别通过添加或减去<code> stepSize </code>来计算序列的元素。
     * <p>
     *  <code> minimum </code>和<code> maximum </code>参数可以是<code> null </code>以指示范围没有上限或下限。
     * 如果<code> value </code>或<code> stepSize </code>是<code> null </code>,或者如果指定了<code> minimum </code>和<code>
     * 和<code> minimum&gt;最大</code>,然后抛出<code> IllegalArgumentException </code>。
     *  <code> minimum </code>和<code> maximum </code>参数可以是<code> null </code>以指示范围没有上限或下限。
     * 类似地,如果<code>(最小值&lt; =值&lt; =最大值&lt; / code&gt;)为假,则抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param value the current (non <code>null</code>) value of the model
     * @param minimum the first number in the sequence or <code>null</code>
     * @param maximum the last number in the sequence or <code>null</code>
     * @param stepSize the difference between elements of the sequence
     *
     * @throws IllegalArgumentException if stepSize or value is
     *     <code>null</code> or if the following expression is false:
     *     <code>minimum &lt;= value &lt;= maximum</code>
     */
    public SpinnerNumberModel(Number value, Comparable minimum, Comparable maximum, Number stepSize) {
        if ((value == null) || (stepSize == null)) {
            throw new IllegalArgumentException("value and stepSize must be non-null");
        }
        if (!(((minimum == null) || (minimum.compareTo(value) <= 0)) &&
              ((maximum == null) || (maximum.compareTo(value) >= 0)))) {
            throw new IllegalArgumentException("(minimum <= value <= maximum) is false");
        }
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.stepSize = stepSize;
    }


    /**
     * Constructs a <code>SpinnerNumberModel</code> with the specified
     * <code>value</code>, <code>minimum</code>/<code>maximum</code> bounds,
     * and <code>stepSize</code>.
     *
     * <p>
     * 用指定的<code>值</code>,<code>最小</code> / <code>最大</code>边界和<code> stepSize </code>构造一个<code> SpinnerNumbe
     * rModel </code> 。
     * 
     * 
     * @param value the current value of the model
     * @param minimum the first number in the sequence
     * @param maximum the last number in the sequence
     * @param stepSize the difference between elements of the sequence
     * @throws IllegalArgumentException if the following expression is false:
     *     <code>minimum &lt;= value &lt;= maximum</code>
     */
    public SpinnerNumberModel(int value, int minimum, int maximum, int stepSize) {
        this(Integer.valueOf(value), Integer.valueOf(minimum), Integer.valueOf(maximum), Integer.valueOf(stepSize));
    }


    /**
     * Constructs a <code>SpinnerNumberModel</code> with the specified
     * <code>value</code>, <code>minimum</code>/<code>maximum</code> bounds,
     * and <code>stepSize</code>.
     *
     * <p>
     *  用指定的<code>值</code>,<code>最小</code> / <code>最大</code>边界和<code> stepSize </code>构造一个<code> SpinnerNumb
     * erModel </code> 。
     * 
     * 
     * @param value the current value of the model
     * @param minimum the first number in the sequence
     * @param maximum the last number in the sequence
     * @param stepSize the difference between elements of the sequence
     * @throws IllegalArgumentException   if the following expression is false:
     *     <code>minimum &lt;= value &lt;= maximum</code>
     */
    public SpinnerNumberModel(double value, double minimum, double maximum, double stepSize) {
        this(new Double(value), new Double(minimum), new Double(maximum), new Double(stepSize));
    }


    /**
     * Constructs a <code>SpinnerNumberModel</code> with no
     * <code>minimum</code> or <code>maximum</code> value,
     * <code>stepSize</code> equal to one, and an initial value of zero.
     * <p>
     *  构造一个<code> SpinnerNumberModel </code>,不带<code>最小</code>或<code>最大</code>值,<code> stepSize </code>等于1,
     * 初始值为零。
     * 
     */
    public SpinnerNumberModel() {
        this(Integer.valueOf(0), null, null, Integer.valueOf(1));
    }


    /**
     * Changes the lower bound for numbers in this sequence.
     * If <code>minimum</code> is <code>null</code>,
     * then there is no lower bound.  No bounds checking is done here;
     * the new <code>minimum</code> value may invalidate the
     * <code>(minimum &lt;= value &lt;= maximum)</code>
     * invariant enforced by the constructors.  This is to simplify updating
     * the model, naturally one should ensure that the invariant is true
     * before calling the <code>getNextValue</code>,
     * <code>getPreviousValue</code>, or <code>setValue</code> methods.
     * <p>
     * Typically this property is a <code>Number</code> of the same type
     * as the <code>value</code> however it's possible to use any
     * <code>Comparable</code> with a <code>compareTo</code>
     * method for a <code>Number</code> with the same type as the value.
     * For example if value was a <code>Long</code>,
     * <code>minimum</code> might be a Date subclass defined like this:
     * <pre>
     * MyDate extends Date {  // Date already implements Comparable
     *     public int compareTo(Long o) {
     *         long t = getTime();
     *         return (t &lt; o.longValue() ? -1 : (t == o.longValue() ? 0 : 1));
     *     }
     * }
     * </pre>
     * <p>
     * This method fires a <code>ChangeEvent</code>
     * if the <code>minimum</code> has changed.
     *
     * <p>
     *  更改此序列中数字的下限。如果<code> minimum </code>是<code> null </code>,那么没有下限。
     * 这里不进行边界检查;新的<code> minimum </code>值可以使由构造函数强制执行的<code>(最小<=值<=最大)</code>不变量无效。
     * 这是为了简化更新模型,自然应该在调用<code> getNextValue </code>,<code> getPreviousValue </code>或<code> setValue </code>
     * 方法之前确保不变量为真。
     * 这里不进行边界检查;新的<code> minimum </code>值可以使由构造函数强制执行的<code>(最小<=值<=最大)</code>不变量无效。
     * <p>
     *  通常,此属性是与<code> value </code>相同类型的<code> Number </code>,但是可以使用任何<code> Comparable </code>与<code> comp
     * areTo </code >方法用于与该值相同类型的<code> Number </code>。
     * 例如,如果值是一个<code> Long </code>,<code> minimum </code>可能是一个Date子类定义如下：。
     * <pre>
     * MyDate extends Date {// Date already implements Comparable public int compareTo(Long o){long t = getTime(); return(t <o.longValue()?-1：(t == o.longValue()?0：1)); }}。
     * </pre>
     * <p>
     *  如果<code> minimum </code>已更改,此方法会触发<code> ChangeEvent </code>。
     * 
     * 
     * @param minimum a <code>Comparable</code> that has a
     *     <code>compareTo</code> method for <code>Number</code>s with
     *     the same type as <code>value</code>
     * @see #getMinimum
     * @see #setMaximum
     * @see SpinnerModel#addChangeListener
     */
    public void setMinimum(Comparable minimum) {
        if ((minimum == null) ? (this.minimum != null) : !minimum.equals(this.minimum)) {
            this.minimum = minimum;
            fireStateChanged();
        }
    }


    /**
     * Returns the first number in this sequence.
     *
     * <p>
     *  返回此序列中的第一个数字。
     * 
     * 
     * @return the value of the <code>minimum</code> property
     * @see #setMinimum
     */
    public Comparable getMinimum() {
        return minimum;
    }


    /**
     * Changes the upper bound for numbers in this sequence.
     * If <code>maximum</code> is <code>null</code>, then there
     * is no upper bound.  No bounds checking is done here; the new
     * <code>maximum</code> value may invalidate the
     * <code>(minimum &lt;= value &lt; maximum)</code>
     * invariant enforced by the constructors.  This is to simplify updating
     * the model, naturally one should ensure that the invariant is true
     * before calling the <code>next</code>, <code>previous</code>,
     * or <code>setValue</code> methods.
     * <p>
     * Typically this property is a <code>Number</code> of the same type
     * as the <code>value</code> however it's possible to use any
     * <code>Comparable</code> with a <code>compareTo</code>
     * method for a <code>Number</code> with the same type as the value.
     * See <a href="#setMinimum(java.lang.Comparable)">
     * <code>setMinimum</code></a> for an example.
     * <p>
     * This method fires a <code>ChangeEvent</code> if the
     * <code>maximum</code> has changed.
     *
     * <p>
     *  更改此序列中数字的上限。如果<code> maximum </code>是<code> null </code>,那么没有上限。
     * 这里不进行边界检查;新的<code> maximum </code>值可以使由构造函数强制执行的<code>(最小<=值<最大)</code>不变量无效。
     * 这是为了简化更新模型,自然应该确保在调用<code> next </code>,<code> previous </code>或<code> setValue </code>方法之前不变量为true。
     * <p>
     *  通常,此属性是与<code> value </code>相同类型的<code> Number </code>,但是可以使用任何<code> Comparable </code>与<code> comp
     * areTo </code >方法用于与该值相同类型的<code> Number </code>。
     * 有关示例,请参见<a href="#setMinimum(java.lang.Comparable)"> <code> setMinimum </code> </a>。
     * <p>
     *  如果<code> maximum </code>已更改,此方法会触发<code> ChangeEvent </code>。
     * 
     * 
     * @param maximum a <code>Comparable</code> that has a
     *     <code>compareTo</code> method for <code>Number</code>s with
     *     the same type as <code>value</code>
     * @see #getMaximum
     * @see #setMinimum
     * @see SpinnerModel#addChangeListener
     */
    public void setMaximum(Comparable maximum) {
        if ((maximum == null) ? (this.maximum != null) : !maximum.equals(this.maximum)) {
            this.maximum = maximum;
            fireStateChanged();
        }
    }


    /**
     * Returns the last number in the sequence.
     *
     * <p>
     *  返回序列中的最后一个数字。
     * 
     * 
     * @return the value of the <code>maximum</code> property
     * @see #setMaximum
     */
    public Comparable getMaximum() {
        return maximum;
    }


    /**
     * Changes the size of the value change computed by the
     * <code>getNextValue</code> and <code>getPreviousValue</code>
     * methods.  An <code>IllegalArgumentException</code>
     * is thrown if <code>stepSize</code> is <code>null</code>.
     * <p>
     * This method fires a <code>ChangeEvent</code> if the
     * <code>stepSize</code> has changed.
     *
     * <p>
     * 更改由<code> getNextValue </code>和<code> getPreviousValue </code>方法计算的值更改的大小。
     * 如果<code> stepSize </code>是<code> null </code>,则抛出<code> IllegalArgumentException </code>。
     * <p>
     *  如果<code> stepSize </code>已更改,此方法会触发<code> ChangeEvent </code>。
     * 
     * 
     * @param stepSize the size of the value change computed by the
     *     <code>getNextValue</code> and <code>getPreviousValue</code> methods
     * @see #getNextValue
     * @see #getPreviousValue
     * @see #getStepSize
     * @see SpinnerModel#addChangeListener
     */
    public void setStepSize(Number stepSize) {
        if (stepSize == null) {
            throw new IllegalArgumentException("null stepSize");
        }
        if (!stepSize.equals(this.stepSize)) {
            this.stepSize = stepSize;
            fireStateChanged();
        }
    }


    /**
     * Returns the size of the value change computed by the
     * <code>getNextValue</code>
     * and <code>getPreviousValue</code> methods.
     *
     * <p>
     *  返回由<code> getNextValue </code>和<code> getPreviousValue </code>方法计算的值更改的大小。
     * 
     * 
     * @return the value of the <code>stepSize</code> property
     * @see #setStepSize
     */
    public Number getStepSize() {
        return stepSize;
    }


    private Number incrValue(int dir)
    {
        Number newValue;
        if ((value instanceof Float) || (value instanceof Double)) {
            double v = value.doubleValue() + (stepSize.doubleValue() * (double)dir);
            if (value instanceof Double) {
                newValue = new Double(v);
            }
            else {
                newValue = new Float(v);
            }
        }
        else {
            long v = value.longValue() + (stepSize.longValue() * (long)dir);

            if (value instanceof Long) {
                newValue = Long.valueOf(v);
            }
            else if (value instanceof Integer) {
                newValue = Integer.valueOf((int)v);
            }
            else if (value instanceof Short) {
                newValue = Short.valueOf((short)v);
            }
            else {
                newValue = Byte.valueOf((byte)v);
            }
        }

        if ((maximum != null) && (maximum.compareTo(newValue) < 0)) {
            return null;
        }
        if ((minimum != null) && (minimum.compareTo(newValue) > 0)) {
            return null;
        }
        else {
            return newValue;
        }
    }


    /**
     * Returns the next number in the sequence.
     *
     * <p>
     *  返回序列中的下一个数字。
     * 
     * 
     * @return <code>value + stepSize</code> or <code>null</code> if the sum
     *     exceeds <code>maximum</code>.
     *
     * @see SpinnerModel#getNextValue
     * @see #getPreviousValue
     * @see #setStepSize
     */
    public Object getNextValue() {
        return incrValue(+1);
    }


    /**
     * Returns the previous number in the sequence.
     *
     * <p>
     *  返回序列中的上一个数字。
     * 
     * 
     * @return <code>value - stepSize</code>, or
     *     <code>null</code> if the sum is less
     *     than <code>minimum</code>.
     *
     * @see SpinnerModel#getPreviousValue
     * @see #getNextValue
     * @see #setStepSize
     */
    public Object getPreviousValue() {
        return incrValue(-1);
    }


    /**
     * Returns the value of the current element of the sequence.
     *
     * <p>
     *  返回序列的当前元素的值。
     * 
     * 
     * @return the value property
     * @see #setValue
     */
    public Number getNumber() {
        return value;
    }


    /**
     * Returns the value of the current element of the sequence.
     *
     * <p>
     *  返回序列的当前元素的值。
     * 
     * 
     * @return the value property
     * @see #setValue
     * @see #getNumber
     */
    public Object getValue() {
        return value;
    }


    /**
     * Sets the current value for this sequence.  If <code>value</code> is
     * <code>null</code>, or not a <code>Number</code>, an
     * <code>IllegalArgumentException</code> is thrown.  No
     * bounds checking is done here; the new value may invalidate the
     * <code>(minimum &lt;= value &lt;= maximum)</code>
     * invariant enforced by the constructors.   It's also possible to set
     * the value to be something that wouldn't naturally occur in the sequence,
     * i.e. a value that's not modulo the <code>stepSize</code>.
     * This is to simplify updating the model, and to accommodate
     * spinners that don't want to restrict values that have been
     * directly entered by the user. Naturally, one should ensure that the
     * <code>(minimum &lt;= value &lt;= maximum)</code> invariant is true
     * before calling the <code>next</code>, <code>previous</code>, or
     * <code>setValue</code> methods.
     * <p>
     * This method fires a <code>ChangeEvent</code> if the value has changed.
     *
     * <p>
     *  设置此序列的当前值。
     * 如果<code> value </code>是<code> null </code>或不是<code> Number </code>,则会抛出<code> IllegalArgumentExceptio
     * n </code>。
     *  设置此序列的当前值。这里不进行边界检查;新值可以使由构造函数强制执行的<code>(最小<=值<=最大)</>不变量无效。
     * 还可以将值设置为在序列中不会自然出现的某个值,即不会对<code> stepSize </code>取模的值。这是为了简化更新模型,以及适应不想限制用户直接输入的值的纺纱者。
     * 
     * @param value the current (non <code>null</code>) <code>Number</code>
     *         for this sequence
     * @throws IllegalArgumentException if <code>value</code> is
     *         <code>null</code> or not a <code>Number</code>
     * @see #getNumber
     * @see #getValue
     * @see SpinnerModel#addChangeListener
     */
    public void setValue(Object value) {
        if ((value == null) || !(value instanceof Number)) {
            throw new IllegalArgumentException("illegal value");
        }
        if (!value.equals(this.value)) {
            this.value = (Number)value;
            fireStateChanged();
        }
    }
}
