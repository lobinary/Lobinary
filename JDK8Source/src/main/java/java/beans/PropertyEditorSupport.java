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

package java.beans;

import java.beans.*;

/**
 * This is a support class to help build property editors.
 * <p>
 * It can be used either as a base class or as a delegate.
 * <p>
 *  这是一个支持类,以帮助构建属性编辑器。
 * <p>
 *  它可以用作基类或委托。
 * 
 */

public class PropertyEditorSupport implements PropertyEditor {

    /**
     * Constructs a <code>PropertyEditorSupport</code> object.
     *
     * <p>
     *  构造一个<code> PropertyEditorSupport </code>对象。
     * 
     * 
     * @since 1.5
     */
    public PropertyEditorSupport() {
        setSource(this);
    }

    /**
     * Constructs a <code>PropertyEditorSupport</code> object.
     *
     * <p>
     *  构造一个<code> PropertyEditorSupport </code>对象。
     * 
     * 
     * @param source the source used for event firing
     * @since 1.5
     */
    public PropertyEditorSupport(Object source) {
        if (source == null) {
           throw new NullPointerException();
        }
        setSource(source);
    }

    /**
     * Returns the bean that is used as the
     * source of events. If the source has not
     * been explicitly set then this instance of
     * <code>PropertyEditorSupport</code> is returned.
     *
     * <p>
     *  返回用作事件源的bean。如果源没有被显式地设置,则返回<code> PropertyEditorSupport </code>的这个实例。
     * 
     * 
     * @return the source object or this instance
     * @since 1.5
     */
    public Object getSource() {
        return source;
    }

    /**
     * Sets the source bean.
     * <p>
     * The source bean is used as the source of events
     * for the property changes. This source should be used for information
     * purposes only and should not be modified by the PropertyEditor.
     *
     * <p>
     *  设置源bean。
     * <p>
     *  源bean用作属性更改的事件源。此源仅用于信息目的,不应由PropertyEditor修改。
     * 
     * 
     * @param source source object to be used for events
     * @since 1.5
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /**
     * Set (or change) the object that is to be edited.
     *
     * <p>
     *  设置(或更改)要编辑的对象。
     * 
     * 
     * @param value The new target object to be edited.  Note that this
     *     object should not be modified by the PropertyEditor, rather
     *     the PropertyEditor should create a new object to hold any
     *     modified value.
     */
    public void setValue(Object value) {
        this.value = value;
        firePropertyChange();
    }

    /**
     * Gets the value of the property.
     *
     * <p>
     *  获取属性的值。
     * 
     * 
     * @return The value of the property.
     */
    public Object getValue() {
        return value;
    }

    //----------------------------------------------------------------------

    /**
     * Determines whether the class will honor the paintValue method.
     *
     * <p>
     *  确定类是否将遵守paintValue方法。
     * 
     * 
     * @return  True if the class will honor the paintValue method.
     */

    public boolean isPaintable() {
        return false;
    }

    /**
     * Paint a representation of the value into a given area of screen
     * real estate.  Note that the propertyEditor is responsible for doing
     * its own clipping so that it fits into the given rectangle.
     * <p>
     * If the PropertyEditor doesn't honor paint requests (see isPaintable)
     * this method should be a silent noop.
     *
     * <p>
     *  将值的表示绘制到屏幕空间的给定区域中。注意,propertyEditor负责进行自己的裁剪,使其适合给定的矩形。
     * <p>
     *  如果PropertyEditor不符合绘制请求(见isPaintable),这个方法应该是一个静默的noop。
     * 
     * 
     * @param gfx  Graphics object to paint into.
     * @param box  Rectangle within graphics object into which we should paint.
     */
    public void paintValue(java.awt.Graphics gfx, java.awt.Rectangle box) {
    }

    //----------------------------------------------------------------------

    /**
     * This method is intended for use when generating Java code to set
     * the value of the property.  It should return a fragment of Java code
     * that can be used to initialize a variable with the current property
     * value.
     * <p>
     * Example results are "2", "new Color(127,127,34)", "Color.orange", etc.
     *
     * <p>
     *  此方法旨在用于生成Java代码以设置属性的值。它应该返回一个Java代码片段,可以用来使用当前属性值初始化一个变量。
     * <p>
     *  示例结果为"2","new Color(127,127,34)","Color.orange"等。
     * 
     * 
     * @return A fragment of Java code representing an initializer for the
     *          current value.
     */
    public String getJavaInitializationString() {
        return "???";
    }

    //----------------------------------------------------------------------

    /**
     * Gets the property value as a string suitable for presentation
     * to a human to edit.
     *
     * <p>
     * 获取属性值作为适合于呈现给人类编辑的字符串。
     * 
     * 
     * @return The property value as a string suitable for presentation
     *       to a human to edit.
     * <p>   Returns null if the value can't be expressed as a string.
     * <p>   If a non-null value is returned, then the PropertyEditor should
     *       be prepared to parse that string back in setAsText().
     */
    public String getAsText() {
        return (this.value != null)
                ? this.value.toString()
                : null;
    }

    /**
     * Sets the property value by parsing a given String.  May raise
     * java.lang.IllegalArgumentException if either the String is
     * badly formatted or if this kind of property can't be expressed
     * as text.
     *
     * <p>
     *  通过解析给定的字符串来设置属性值。如果字符串格式不正确或者此类属性不能表示为文本,则可能引发java.lang.IllegalArgumentException。
     * 
     * 
     * @param text  The string to be parsed.
     */
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        if (value instanceof String) {
            setValue(text);
            return;
        }
        throw new java.lang.IllegalArgumentException(text);
    }

    //----------------------------------------------------------------------

    /**
     * If the property value must be one of a set of known tagged values,
     * then this method should return an array of the tag values.  This can
     * be used to represent (for example) enum values.  If a PropertyEditor
     * supports tags, then it should support the use of setAsText with
     * a tag value as a way of setting the value.
     *
     * <p>
     *  如果属性值必须是一组已知标记值中的一个,那么此方法应返回标记值的数组。这可以用于表示(例如)枚举值。
     * 如果PropertyEditor支持标签,那么它应该支持使用带有标签值的setAsText作为设置值的方法。
     * 
     * 
     * @return The tag values for this property.  May be null if this
     *   property cannot be represented as a tagged value.
     *
     */
    public String[] getTags() {
        return null;
    }

    //----------------------------------------------------------------------

    /**
     * A PropertyEditor may chose to make available a full custom Component
     * that edits its property value.  It is the responsibility of the
     * PropertyEditor to hook itself up to its editor Component itself and
     * to report property value changes by firing a PropertyChange event.
     * <P>
     * The higher-level code that calls getCustomEditor may either embed
     * the Component in some larger property sheet, or it may put it in
     * its own individual dialog, or ...
     *
     * <p>
     *  PropertyEditor可以选择提供编辑其属性值的完全自定义组件。 PropertyEditor负责将自己挂钩到其编辑器组件本身,并通过触发PropertyChange事件来报告属性值更改。
     * <P>
     *  调用getCustomEditor的高级代码可以将组件嵌入到一些较大的属性表中,也可以将其放在单独的对话框中,或者...
     * 
     * 
     * @return A java.awt.Component that will allow a human to directly
     *      edit the current property value.  May be null if this is
     *      not supported.
     */

    public java.awt.Component getCustomEditor() {
        return null;
    }

    /**
     * Determines whether the propertyEditor can provide a custom editor.
     *
     * <p>
     *  确定propertyEditor是否可以提供自定义编辑器。
     * 
     * 
     * @return  True if the propertyEditor can provide a custom editor.
     */
    public boolean supportsCustomEditor() {
        return false;
    }

    //----------------------------------------------------------------------

    /**
     * Adds a listener for the value change.
     * When the property editor changes its value
     * it should fire a {@link PropertyChangeEvent}
     * on all registered {@link PropertyChangeListener}s,
     * specifying the {@code null} value for the property name.
     * If the source property is set,
     * it should be used as the source of the event.
     * <p>
     * The same listener object may be added more than once,
     * and will be called as many times as it is added.
     * If {@code listener} is {@code null},
     * no exception is thrown and no action is taken.
     *
     * <p>
     *  为值更改添加侦听器。
     * 当属性编辑器更改其值时,应该在所有注册的{@link PropertyChangeListener}上触发{@link PropertyChangeEvent},指定属性名称的{@code null}值
     * 。
     *  为值更改添加侦听器。如果设置了source属性,则应将其用作事件的源。
     * <p>
     * 相同的侦听器对象可以多次添加,并且将被调用的次数与添加的次数相同。如果{@code listener}是{@code null},则不会抛出任何异常,并且不会采取任何操作。
     * 
     * 
     * @param listener  the {@link PropertyChangeListener} to add
     */
    public synchronized void addPropertyChangeListener(
                                PropertyChangeListener listener) {
        if (listeners == null) {
            listeners = new java.util.Vector<>();
        }
        listeners.addElement(listener);
    }

    /**
     * Removes a listener for the value change.
     * <p>
     * If the same listener was added more than once,
     * it will be notified one less time after being removed.
     * If {@code listener} is {@code null}, or was never added,
     * no exception is thrown and no action is taken.
     *
     * <p>
     *  删除值更改的侦听器。
     * <p>
     *  如果同一个监听器被添加了多次,它会被删除后一小段时间通知。如果{@code listener}是{@code null}或从未添加,则不会抛出任何异常,因此不会执行任何操作。
     * 
     * 
     * @param listener  the {@link PropertyChangeListener} to remove
     */
    public synchronized void removePropertyChangeListener(
                                PropertyChangeListener listener) {
        if (listeners == null) {
            return;
        }
        listeners.removeElement(listener);
    }

    /**
     * Report that we have been modified to any interested listeners.
     * <p>
     */
    public void firePropertyChange() {
        java.util.Vector<PropertyChangeListener> targets;
        synchronized (this) {
            if (listeners == null) {
                return;
            }
            targets = unsafeClone(listeners);
        }
        // Tell our listeners that "everything" has changed.
        PropertyChangeEvent evt = new PropertyChangeEvent(source, null, null, null);

        for (int i = 0; i < targets.size(); i++) {
            PropertyChangeListener target = targets.elementAt(i);
            target.propertyChange(evt);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> java.util.Vector<T> unsafeClone(java.util.Vector<T> v) {
        return (java.util.Vector<T>)v.clone();
    }

    //----------------------------------------------------------------------

    private Object value;
    private Object source;
    private java.util.Vector<PropertyChangeListener> listeners;
}
