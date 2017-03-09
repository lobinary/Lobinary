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
 * A <code>CompoundControl</code>, such as a graphic equalizer, provides control
 * over two or more related properties, each of which is itself represented as
 * a <code>Control</code>.
 *
 * <p>
 *  诸如图形均衡器的<code> CompoundControl </code>提供对两个或更多相关属性的控制,每个属性本身表示为<code> Control </code>。
 * 
 * 
 * @author Kara Kytle
 * @since 1.3
 */
public abstract class CompoundControl extends Control {


    // TYPE DEFINES


    // INSTANCE VARIABLES


    /**
     * The set of member controls.
     * <p>
     *  成员控件的集合。
     * 
     */
    private Control[] controls;



    // CONSTRUCTORS


    /**
     * Constructs a new compound control object with the given parameters.
     *
     * <p>
     *  使用给定的参数构造一个新的复合控制对象。
     * 
     * 
     * @param type the type of control represented this compound control object
     * @param memberControls the set of member controls
     */
    protected CompoundControl(Type type, Control[] memberControls) {

        super(type);
        this.controls = memberControls;
    }



    // METHODS


    /**
     * Returns the set of member controls that comprise the compound control.
     * <p>
     *  返回构成复合控件的成员控件的集合。
     * 
     * 
     * @return the set of member controls.
     */
    public Control[] getMemberControls() {

        Control[] localArray = new Control[controls.length];

        for (int i = 0; i < controls.length; i++) {
            localArray[i] = controls[i];
        }

        return localArray;
    }


    // ABSTRACT METHOD IMPLEMENTATIONS: CONTROL


    /**
     * Provides a string representation of the control
     * <p>
     *  提供控件的字符串表示形式
     * 
     * 
     * @return a string description
     */
    public String toString() {

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < controls.length; i++) {
            if (i != 0) {
                buf.append(", ");
                if ((i + 1) == controls.length) {
                    buf.append("and ");
                }
            }
            buf.append(controls[i].getType());
        }

        return new String(getType() + " Control containing " + buf + " Controls.");
    }


    // INNER CLASSES


    /**
     * An instance of the <code>CompoundControl.Type</code> inner class identifies one kind of
     * compound control.  Static instances are provided for the
     * common types.
     *
     * <p>
     *  <code> CompoundControl.Type </code>内部类的实例标识一种复合控件。为常见类型提供静态实例。
     * 
     * 
     * @author Kara Kytle
     * @since 1.3
     */
    public static class Type extends Control.Type {


        // TYPE DEFINES

        // CONSTRUCTOR


        /**
         * Constructs a new compound control type.
         * <p>
         *  构造新的复合控件类型。
         * 
         * @param name  the name of the new compound control type
         */
        protected Type(String name) {
            super(name);
        }
    } // class Type

} // class CompoundControl
