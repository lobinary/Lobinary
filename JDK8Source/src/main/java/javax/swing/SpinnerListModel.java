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
 * A simple implementation of <code>SpinnerModel</code> whose
 * values are defined by an array or a <code>List</code>.
 * For example to create a model defined by
 * an array of the names of the days of the week:
 * <pre>
 * String[] days = new DateFormatSymbols().getWeekdays();
 * SpinnerModel model = new SpinnerListModel(Arrays.asList(days).subList(1, 8));
 * </pre>
 * This class only stores a reference to the array or <code>List</code>
 * so if an element of the underlying sequence changes, it's up
 * to the application to notify the <code>ChangeListeners</code> by calling
 * <code>fireStateChanged</code>.
 * <p>
 * This model inherits a <code>ChangeListener</code>.
 * The <code>ChangeListener</code>s are notified whenever the
 * model's <code>value</code> or <code>list</code> properties changes.
 *
 * <p>
 *  <code> SpinnerModel </code>的一个简单实现,其值由数组或<code> List </code>定义。例如,创建一个由星期几的名称数组定义的模型：
 * <pre>
 *  String [] days = new DateFormatSymbols()。
 * getWeekdays(); SpinnerModel model = new SpinnerListModel(Arrays.asList(days).subList(1,8));。
 * </pre>
 *  这个类只存储对数组或<code> List </code>的引用,所以如果底层序列的元素改变,应用程序通过调用<code> fireStateChanged通知<code> ChangeListene
 * rs </code> </code>。
 * <p>
 *  这个模型继承一个<code> ChangeListener </code>。
 * 每当模型的<code>值</code>或<code>列表</code>属性更改时,都会通知<code> ChangeListener </code>。
 * 
 * 
 * @see JSpinner
 * @see SpinnerModel
 * @see AbstractSpinnerModel
 * @see SpinnerNumberModel
 * @see SpinnerDateModel
 *
 * @author Hans Muller
 * @since 1.4
 */
public class SpinnerListModel extends AbstractSpinnerModel implements Serializable
{
    private List list;
    private int index;


    /**
     * Constructs a <code>SpinnerModel</code> whose sequence of
     * values is defined by the specified <code>List</code>.
     * The initial value (<i>current element</i>)
     * of the model will be <code>values.get(0)</code>.
     * If <code>values</code> is <code>null</code> or has zero
     * size, an <code>IllegalArugmentException</code> is thrown.
     *
     * <p>
     *  构造一个<code> SpinnerModel </code>,其序列的值由指定的<code> List </code>定义。
     * 模型的初始值(<i> current element </i>)将是<code> values.get(0)</code>。
     * 如果<code> values </code>为<code> null </code>或为零大小,则抛出<code> IllegalArugmentException </code>。
     * 
     * 
     * @param values the sequence this model represents
     * @throws IllegalArgumentException if <code>values</code> is
     *    <code>null</code> or zero size
     */
    public SpinnerListModel(List<?> values) {
        if (values == null || values.size() == 0) {
            throw new IllegalArgumentException("SpinnerListModel(List) expects non-null non-empty List");
        }
        this.list = values;
        this.index = 0;
    }


    /**
     * Constructs a <code>SpinnerModel</code> whose sequence of values
     * is defined by the specified array.  The initial value of the model
     * will be <code>values[0]</code>.  If <code>values</code> is
     * <code>null</code> or has zero length, an
     * <code>IllegalArgumentException</code> is thrown.
     *
     * <p>
     *  构造一个<code> SpinnerModel </code>,其序列的值由指定的数组定义。模型的初始值为<code> values [0] </code>。
     * 如果<code> values </code>是<code> null </code>或长度为零,则抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param values the sequence this model represents
     * @throws IllegalArgumentException if <code>values</code> is
     *    <code>null</code> or zero length
     */
    public SpinnerListModel(Object[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("SpinnerListModel(Object[]) expects non-null non-empty Object[]");
        }
        this.list = Arrays.asList(values);
        this.index = 0;
    }


    /**
     * Constructs an effectively empty <code>SpinnerListModel</code>.
     * The model's list will contain a single
     * <code>"empty"</code> string element.
     * <p>
     * 构造一个有效的空的<code> SpinnerListModel </code>。模型的列表将包含一个<code>"empty"</code>字符串元素。
     * 
     */
    public SpinnerListModel() {
        this(new Object[]{"empty"});
    }


    /**
     * Returns the <code>List</code> that defines the sequence for this model.
     *
     * <p>
     *  返回定义此模型序列的<code> List </code>。
     * 
     * 
     * @return the value of the <code>list</code> property
     * @see #setList
     */
    public List<?> getList() {
        return list;
    }


    /**
     * Changes the list that defines this sequence and resets the index
     * of the models <code>value</code> to zero.  Note that <code>list</code>
     * is not copied, the model just stores a reference to it.
     * <p>
     * This method fires a <code>ChangeEvent</code> if <code>list</code> is
     * not equal to the current list.
     *
     * <p>
     *  更改定义此序列的列表,并将模型的索引<code>值</code>重置为零。注意,不复制<code> list </code>,模型只是存储对它的引用。
     * <p>
     *  如果<code> list </code>不等于当前列表,则此方法触发<code> ChangeEvent </code>。
     * 
     * 
     * @param list the sequence that this model represents
     * @throws IllegalArgumentException if <code>list</code> is
     *    <code>null</code> or zero length
     * @see #getList
     */
    public void setList(List<?> list) {
        if ((list == null) || (list.size() == 0)) {
            throw new IllegalArgumentException("invalid list");
        }
        if (!list.equals(this.list)) {
            this.list = list;
            index = 0;
            fireStateChanged();
        }
    }


    /**
     * Returns the current element of the sequence.
     *
     * <p>
     *  返回序列的当前元素。
     * 
     * 
     * @return the <code>value</code> property
     * @see SpinnerModel#getValue
     * @see #setValue
     */
    public Object getValue() {
        return list.get(index);
    }


    /**
     * Changes the current element of the sequence and notifies
     * <code>ChangeListeners</code>.  If the specified
     * value is not equal to an element of the underlying sequence
     * then an <code>IllegalArgumentException</code> is thrown.
     * In the following example the <code>setValue</code> call
     * would cause an exception to be thrown:
     * <pre>
     * String[] values = {"one", "two", "free", "four"};
     * SpinnerModel model = new SpinnerListModel(values);
     * model.setValue("TWO");
     * </pre>
     *
     * <p>
     *  更改序列的当前元素,并通知<code> ChangeListeners </code>。
     * 如果指定的值不等于基础序列的元素,那么会抛出<code> IllegalArgumentException </code>。
     * 在以下示例中,<code> setValue </code>调用将导致抛出异常：。
     * <pre>
     *  String [] values = {"one","two","free","four"}; SpinnerModel model = new SpinnerListModel(values); m
     * odel.setValue("TWO");。
     * </pre>
     * 
     * 
     * @param elt the sequence element that will be model's current value
     * @throws IllegalArgumentException if the specified value isn't allowed
     * @see SpinnerModel#setValue
     * @see #getValue
     */
    public void setValue(Object elt) {
        int index = list.indexOf(elt);
        if (index == -1) {
            throw new IllegalArgumentException("invalid sequence element");
        }
        else if (index != this.index) {
            this.index = index;
            fireStateChanged();
        }
    }


    /**
     * Returns the next legal value of the underlying sequence or
     * <code>null</code> if value is already the last element.
     *
     * <p>
     *  返回基础序列的下一个合法值,如果value已经是最后一个元素,则返回<code> null </code>。
     * 
     * 
     * @return the next legal value of the underlying sequence or
     *     <code>null</code> if value is already the last element
     * @see SpinnerModel#getNextValue
     * @see #getPreviousValue
     */
    public Object getNextValue() {
        return (index >= (list.size() - 1)) ? null : list.get(index + 1);
    }


    /**
     * Returns the previous element of the underlying sequence or
     * <code>null</code> if value is already the first element.
     *
     * <p>
     *  返回基础序列的上一个元素或<code> null </code>如果value已经是第一个元素。
     * 
     * 
     * @return the previous element of the underlying sequence or
     *     <code>null</code> if value is already the first element
     * @see SpinnerModel#getPreviousValue
     * @see #getNextValue
     */
    public Object getPreviousValue() {
        return (index <= 0) ? null : list.get(index - 1);
    }


    /**
     * Returns the next object that starts with <code>substring</code>.
     *
     * <p>
     *  返回以<code> substring </code>开头的下一个对象。
     * 
     * @param substring the string to be matched
     * @return the match
     */
    Object findNextMatch(String substring) {
        int max = list.size();

        if (max == 0) {
            return null;
        }
        int counter = index;

        do {
            Object value = list.get(counter);
            String string = value.toString();

            if (string != null && string.startsWith(substring)) {
                return value;
            }
            counter = (counter + 1) % max;
        } while (counter != index);
        return null;
    }
}
