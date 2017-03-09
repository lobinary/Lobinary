/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.sound.sampled;

/**
 * A <code>EnumControl</code> provides control over a set of
 * discrete possible values, each represented by an object.  In a
 * graphical user interface, such a control might be represented
 * by a set of buttons, each of which chooses one value or setting.  For
 * example, a reverb control might provide several preset reverberation
 * settings, instead of providing continuously adjustable parameters
 * of the sort that would be represented by <code>{@link FloatControl}</code>
 * objects.
 * <p>
 * Controls that provide a choice between only two settings can often be implemented
 * instead as a <code>{@link BooleanControl}</code>, and controls that provide
 * a set of values along some quantifiable dimension might be implemented
 * instead as a <code>FloatControl</code> with a coarse resolution.
 * However, a key feature of <code>EnumControl</code> is that the returned values
 * are arbitrary objects, rather than numerical or boolean values.  This means that each
 * returned object can provide further information.  As an example, the settings
 * of a <code>{@link EnumControl.Type#REVERB REVERB}</code> control are instances of
 * <code>{@link ReverbType}</code> that can be queried for the parameter values
 * used for each setting.
 *
 * <p>
 *  <code> EnumControl </code>提供对一组离散可能值的控制,每个离散可能值由对象表示。在图形用户界面中,这样的控制可以由一组按钮表示,每个按钮选择一个值或设置。
 * 例如,混响控制可以提供几个预设混响设置,而不是提供由<code> {@ link FloatControl} </code>对象表示的排序的连续可调参数。
 * <p>
 *  提供仅在两个设置之间选择的控件通常可以实现为<code> {@ link BooleanControl} </code>,并且沿某些可量化维提供一组值的控件可以实现为<code> FloatContr
 * ol </code>。
 * 然而,<code> EnumControl </code>的一个关键特性是返回的值是任意对象,而不是数值或布尔值。这意味着每个返回的对象可以提供进一步的信息。
 * 作为示例,<code> {@ link EnumControl.Type#REVERB REVERB} </code>控件的设置是<code> {@ link ReverbType} </code>的实
 * 例,可以查询所使用的参数值为每个设置。
 * 然而,<code> EnumControl </code>的一个关键特性是返回的值是任意对象,而不是数值或布尔值。这意味着每个返回的对象可以提供进一步的信息。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class EnumControl extends Control {


    // TYPE DEFINES


    // INSTANCE VARIABLES


    /**
     * The set of possible values.
     * <p>
     *  可能值的集合。
     * 
     */
    private Object[] values;


    /**
     * The current value.
     * <p>
     *  当前值。
     * 
     */
    private Object value;



    // CONSTRUCTORS


    /**
     * Constructs a new enumerated control object with the given parameters.
     *
     * <p>
     *  使用给定的参数构造一个新的枚举控件对象。
     * 
     * 
     * @param type the type of control represented this enumerated control object
     * @param values the set of possible values for the control
     * @param value the initial control value
     */
    protected EnumControl(Type type, Object[] values, Object value) {

        super(type);

        this.values = values;
        this.value = value;
    }



    // METHODS


    /**
     * Sets the current value for the control.  The default implementation
     * simply sets the value as indicated.  If the value indicated is not
     * supported, an IllegalArgumentException is thrown.
     * Some controls require that their line be open before they can be affected
     * by setting a value.
     * <p>
     * 设置控件的当前值。默认实现只是按照指示设置值。如果指定的值不受支持,则抛出IllegalArgumentException。一些控件要求在设置值之前,它们的行被打开。
     * 
     * 
     * @param value the desired new value
     * @throws IllegalArgumentException if the value indicated does not fall
     * within the allowable range
     */
    public void setValue(Object value) {
        if (!isValueSupported(value)) {
            throw new IllegalArgumentException("Requested value " + value + " is not supported.");
        }

        this.value = value;
    }


    /**
     * Obtains this control's current value.
     * <p>
     *  获取此控件的当前值。
     * 
     * 
     * @return the current value
     */
    public Object getValue() {
        return value;
    }


    /**
     * Returns the set of possible values for this control.
     * <p>
     *  返回此控件的可能值的集合。
     * 
     * 
     * @return the set of possible values
     */
    public Object[] getValues() {

        Object[] localArray = new Object[values.length];

        for (int i = 0; i < values.length; i++) {
            localArray[i] = values[i];
        }

        return localArray;
    }


    /**
     * Indicates whether the value specified is supported.
     * <p>
     *  指示是否支持指定的值。
     * 
     * 
     * @param value the value for which support is queried
     * @return <code>true</code> if the value is supported,
     * otherwise <code>false</code>
     */
    private boolean isValueSupported(Object value) {

        for (int i = 0; i < values.length; i++) {
            //$$fb 2001-07-20: Fix for bug 4400392: setValue() in ReverbControl always throws Exception
            //if (values.equals(values[i])) {
            if (value.equals(values[i])) {
                return true;
            }
        }

        return false;
    }



    // ABSTRACT METHOD IMPLEMENTATIONS: CONTROL


    /**
     * Provides a string representation of the control.
     * <p>
     *  提供控件的字符串表示形式。
     * 
     * 
     * @return a string description
     */
    public String toString() {
        return new String(getType() + " with current value: " + getValue());
    }


    // INNER CLASSES


    /**
     * An instance of the <code>EnumControl.Type</code> inner class identifies one kind of
     * enumerated control.  Static instances are provided for the
     * common types.
     *
     * <p>
     *  <code> EnumControl.Type </code>内部类的实例标识一种枚举控件。为常见类型提供静态实例。
     * 
     * 
     * @see EnumControl
     *
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Type extends Control.Type {


        // TYPE DEFINES

        /**
         * Represents a control over a set of possible reverberation settings.
         * Each reverberation setting is described by an instance of the
         * {@link ReverbType} class.  (To access these settings,
         * invoke <code>{@link EnumControl#getValues}</code> on an
         * enumerated control of type <code>REVERB</code>.)
         * <p>
         *  表示对一组可能的混响设置的控制。每个混响设置由{@link ReverbType}类的实例描述。
         *  (要访问这些设置,请在类型为<code> REVERB </code>的枚举控件上调用<code> {@ link EnumControl#getValues} </code>。
         * 
         */
        public static final Type REVERB         = new Type("Reverb");


        // CONSTRUCTOR


        /**
         * Constructs a new enumerated control type.
         * <p>
         * 
         * @param name  the name of the new enumerated control type
         */
        protected Type(String name) {
            super(name);
        }
    } // class Type

} // class EnumControl
