/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * A <code>BooleanControl</code> provides the ability to switch between
 * two possible settings that affect a line's audio.  The settings are boolean
 * values (<code>true</code> and <code>false</code>).  A graphical user interface
 * might represent the control by a two-state button, an on/off switch, two
 * mutually exclusive buttons, or a checkbox (among other possibilities).
 * For example, depressing a button might activate a
 * <code>{@link BooleanControl.Type#MUTE MUTE}</code> control to silence
 * the line's audio.
 * <p>
 * As with other <code>{@link Control}</code> subclasses, a method is
 * provided that returns string labels for the values, suitable for
 * display in the user interface.
 *
 * <p>
 *  <code> BooleanControl </code>提供了在影响线路音频的两种可能设置之间切换的能力。
 * 设置是布尔值(<code> true </code>和<code> false </code>)。图形用户界面可以表示通过两状态按钮,开/关开关,两个互斥按钮或复选框(以及其它可能性)的控制。
 * 例如,按下按钮可能会激活<code> {@ link BooleanControl.Type#MUTE MUTE} </code>控件以使该线路的音频静音。
 * <p>
 *  与其他<code> {@ link Control} </code>子类一样,提供了一种方法,返回值的字符串标签,适合在用户界面中显示。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class BooleanControl extends Control {


    // INSTANCE VARIABLES

    /**
     * The <code>true</code> state label, such as "true" or "on."
     * <p>
     *  <code> true </code>状态标签,例如"true"或"on"。
     * 
     */
    private final String trueStateLabel;

    /**
     * The <code>false</code> state label, such as "false" or "off."
     * <p>
     *  <code> false </code>状态标签,例如"false"或"off"。
     * 
     */
    private final String falseStateLabel;

    /**
     * The current value.
     * <p>
     *  当前值。
     * 
     */
    private boolean value;


    // CONSTRUCTORS


    /**
     * Constructs a new boolean control object with the given parameters.
     *
     * <p>
     *  使用给定的参数构造一个新的布尔控件对象。
     * 
     * 
     * @param type the type of control represented this float control object
     * @param initialValue the initial control value
     * @param trueStateLabel the label for the state represented by <code>true</code>,
     * such as "true" or "on."
     * @param falseStateLabel the label for the state represented by <code>false</code>,
     * such as "false" or "off."
     */
    protected BooleanControl(Type type, boolean initialValue, String trueStateLabel, String falseStateLabel) {

        super(type);
        this.value = initialValue;
        this.trueStateLabel = trueStateLabel;
        this.falseStateLabel = falseStateLabel;
    }


    /**
     * Constructs a new boolean control object with the given parameters.
     * The labels for the <code>true</code> and <code>false</code> states
     * default to "true" and "false."
     *
     * <p>
     *  使用给定的参数构造一个新的布尔控件对象。 <code> true </code>和<code> false </code>状态的标签默认为"true"和"false"。
     * 
     * 
     * @param type the type of control represented by this float control object
     * @param initialValue the initial control value
     */
    protected BooleanControl(Type type, boolean initialValue) {
        this(type, initialValue, "true", "false");
    }


    // METHODS


    /**
     * Sets the current value for the control.  The default
     * implementation simply sets the value as indicated.
     * Some controls require that their line be open before they can be affected
     * by setting a value.
     * <p>
     *  设置控件的当前值。默认实现只是按照指示设置值。一些控件要求在设置值之前,它们的行被打开。
     * 
     * 
     * @param value desired new value.
     */
    public void setValue(boolean value) {
        this.value = value;
    }



    /**
     * Obtains this control's current value.
     * <p>
     *  获取此控件的当前值。
     * 
     * 
     * @return current value.
     */
    public boolean getValue() {
        return value;
    }


    /**
     * Obtains the label for the specified state.
     * <p>
     *  获取指定状态的标签。
     * 
     * 
     * @param state the state whose label will be returned
     * @return the label for the specified state, such as "true" or "on"
     * for <code>true</code>, or "false" or "off" for <code>false</code>.
     */
    public String getStateLabel(boolean state) {
        return ((state == true) ? trueStateLabel : falseStateLabel);
    }



    // ABSTRACT METHOD IMPLEMENTATIONS: CONTROL


    /**
     * Provides a string representation of the control
     * <p>
     * 提供控件的字符串表示形式
     * 
     * 
     * @return a string description
     */
    public String toString() {
        return new String(super.toString() + " with current value: " + getStateLabel(getValue()));
    }


    // INNER CLASSES


    /**
     * An instance of the <code>BooleanControl.Type</code> class identifies one kind of
     * boolean control.  Static instances are provided for the
     * common types.
     *
     * <p>
     *  <code> BooleanControl.Type </code>类的实例标识一种布尔控件。为常见类型提供静态实例。
     * 
     * 
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Type extends Control.Type {


        // TYPE DEFINES


        /**
         * Represents a control for the mute status of a line.
         * Note that mute status does not affect gain.
         * <p>
         *  表示线路的静音状态的控制。注意,静音状态不会影响增益。
         * 
         */
        public static final Type MUTE                           = new Type("Mute");

        /**
         * Represents a control for whether reverberation is applied
         * to a line.  Note that the status of this control not affect
         * the reverberation settings for a line, but does affect whether
         * these settings are used.
         * <p>
         *  表示是否将混响应用于线的控制。请注意,此控件的状态不会影响线路的混响设置,但会影响是否使用这些设置。
         * 
         */
        public static final Type APPLY_REVERB           = new Type("Apply Reverb");


        // CONSTRUCTOR


        /**
         * Constructs a new boolean control type.
         * <p>
         *  构造一个新的布尔控件类型。
         * 
         * @param name  the name of the new boolean control type
         */
        protected Type(String name) {
            super(name);
        }
    } // class Type
}
