/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Component;

/**
 * Defines the requirements for an object responsible for
 * "rendering" (displaying) a value.
 *
 * <p>
 *  定义负责"呈现"(显示)值的对象的要求。
 * 
 * 
 * @author Arnaud Weber
 */
public interface Renderer {
    /**
     * Specifies the value to display and whether or not the
     * value should be portrayed as "currently selected".
     *
     * <p>
     *  指定要显示的值以及该值是否应被描述为"当前选择"。
     * 
     * 
     * @param aValue      an Object object
     * @param isSelected  a boolean
     */
    void setValue(Object aValue,boolean isSelected);
    /**
     * Returns the component used to render the value.
     *
     * <p>
     *  返回用于呈现值的组件。
     * 
     * @return the Component responsible for displaying the value
     */
    Component getComponent();
}
