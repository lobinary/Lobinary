/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2002, Oracle and/or its affiliates. All rights reserved.
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
 * {@link Line Lines} often have a set of controls, such as gain and pan, that affect
 * the audio signal passing through the line.  Java Sound's <code>Line</code> objects
 * let you obtain a particular control object by passing its class as the
 * argument to a {@link Line#getControl(Control.Type) getControl} method.
 * <p>
 * Because the various types of controls have different purposes and features,
 * all of their functionality is accessed from the subclasses that define
 * each kind of control.
 *
 * <p>
 *  {@link Line Lines}通常有一组控制,例如增益和平移,影响通过该线的音频信号。
 *  Java Sound的<code> Line </code>对象允许您通过将其类作为参数传递给{@link Line#getControl(Control.Type)getControl}方法来获取特
 * 定的控件对象。
 *  {@link Line Lines}通常有一组控制,例如增益和平移,影响通过该线的音频信号。
 * <p>
 *  因为各种类型的控件具有不同的目的和功能,所有的功能都从定义每种控件的子类访问。
 * 
 * 
 * @author Kara Kytle
 *
 * @see Line#getControls
 * @see Line#isControlSupported
 * @since 1.3
 */
public abstract class Control {


    // INSTANCE VARIABLES

    /**
     * The control type.
     * <p>
     *  控制类型。
     * 
     */
    private final Type type;



    // CONSTRUCTORS

    /**
     * Constructs a Control with the specified type.
     * <p>
     *  构造具有指定类型的控件。
     * 
     * 
     * @param type the kind of control desired
     */
    protected Control(Type type) {
        this.type = type;
    }


    // METHODS

    /**
     * Obtains the control's type.
     * <p>
     *  获取控件的类型。
     * 
     * 
     * @return the control's type.
     */
    public Type getType() {
        return type;
    }


    // ABSTRACT METHODS

    /**
     * Obtains a String describing the control type and its current state.
     * <p>
     *  获取描述控件类型及其当前状态的字符串。
     * 
     * 
     * @return a String representation of the Control.
     */
    public String toString() {
        return new String(getType() + " Control");
    }


    /**
     * An instance of the <code>Type</code> class represents the type of
     * the control.  Static instances are provided for the
     * common types.
     * <p>
     *  <code> Type </code>类的实例表示控件的类型。为常见类型提供静态实例。
     * 
     */
    public static class Type {

        // CONTROL TYPE DEFINES

        // INSTANCE VARIABLES

        /**
         * Type name.
         * <p>
         *  类型名称。
         * 
         */
        private String name;


        // CONSTRUCTOR

        /**
         * Constructs a new control type with the name specified.
         * The name should be a descriptive string appropriate for
         * labelling the control in an application, such as "Gain" or "Balance."
         * <p>
         *  构造具有指定名称的新控件类型。名称应为适用于在应用程序中标记控件的描述性字符串,例如"增益"或"平衡"。
         * 
         * 
         * @param name  the name of the new control type.
         */
        protected Type(String name) {
            this.name = name;
        }


        // METHODS

        /**
         * Finalizes the equals method
         * <p>
         *  完成equals方法
         * 
         */
        public final boolean equals(Object obj) {
            return super.equals(obj);
        }

        /**
         * Finalizes the hashCode method
         * <p>
         *  完成hashCode方法
         * 
         */
        public final int hashCode() {
            return super.hashCode();
        }

        /**
         * Provides the <code>String</code> representation of the control type.  This <code>String</code> is
         * the same name that was passed to the constructor.
         *
         * <p>
         *  提供控件类型的<code> String </code>表示形式。此<code> String </code>与传递给构造函数的名称相同。
         * 
         * @return the control type name
         */
        public final String toString() {
            return name;
        }
    } // class Type

} // class Control
