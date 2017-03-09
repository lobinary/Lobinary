/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.peer;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;

/**
 * The peer interface for {@link Checkbox}.
 *
 * The peer interfaces are intended only for use in porting
 * the AWT. They are not intended for use by application
 * developers, and developers should not implement peers
 * nor invoke any of the peer methods directly on the peer
 * instances.
 * <p>
 *  {@link Checkbox}的对等接口。
 * 
 *  对等接口仅用于移植AWT。它们不是供应用程序开发人员使用的,并且开发人员不应实现对等体,也不应直接在对等实例上调用任何对等方法。
 * 
 */
public interface CheckboxPeer extends ComponentPeer {

    /**
     * Sets the state of the checkbox to be checked {@code true} or
     * unchecked {@code false}.
     *
     * <p>
     *  设置要检查的复选框的状态{@code true}或取消选中{@code false}。
     * 
     * 
     * @param state the state to set on the checkbox
     *
     * @see Checkbox#setState(boolean)
     */
    void setState(boolean state);

    /**
     * Sets the checkbox group for this checkbox. Checkboxes in one checkbox
     * group can only be selected exclusively (like radio buttons). A value
     * of {@code null} removes this checkbox from any checkbox group.
     *
     * <p>
     *  设置此复选框的复选框组。复选框组中的复选框只能单独选择(如单选按钮)。值为{@code null}会从任何复选框组中删除此复选框。
     * 
     * 
     * @param g the checkbox group to set, or {@code null} when this
     *          checkbox should not be placed in any group
     *
     * @see Checkbox#setCheckboxGroup(CheckboxGroup)
     */
    void setCheckboxGroup(CheckboxGroup g);

    /**
     * Sets the label that should be displayed on the checkbox. A value of
     * {@code null} means that no label should be displayed.
     *
     * <p>
     *  设置应在复选框上显示的标签。值为{@code null}表示不应显示标签。
     * 
     * @param label the label to be displayed on the checkbox, or
     *              {@code null} when no label should be displayed.
     */
    void setLabel(String label);

}
