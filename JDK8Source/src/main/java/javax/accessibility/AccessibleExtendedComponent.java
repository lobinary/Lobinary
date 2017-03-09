/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

/**
 * The AccessibleExtendedComponent interface should be supported by any object
 * that is rendered on the screen.  This interface provides the standard
 * mechanism for an assistive technology to determine the extended
 * graphical representation of an object.  Applications can determine
 * if an object supports the AccessibleExtendedComponent interface by first
 * obtaining its AccessibleContext
 * and then calling the
 * {@link AccessibleContext#getAccessibleComponent} method.
 * If the return value is not null and the type of the return value is
 * AccessibleExtendedComponent, the object supports this interface.
 *
 * <p>
 *  AccessibleExtendedComponent接口应该由在屏幕上呈现的任何对象支持。此接口为辅助技术提供标准机制,以确定对象的扩展图形表示。
 * 应用程序可以通过首先获取其AccessibleContext然后调用{@link AccessibleContext#getAccessibleComponent}方法来确定对象是否支持Accessib
 * leExtendedComponent接口。
 *  AccessibleExtendedComponent接口应该由在屏幕上呈现的任何对象支持。此接口为辅助技术提供标准机制,以确定对象的扩展图形表示。
 * 如果返回值不为null,并且返回值的类型为AccessibleExtendedComponent,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleComponent
 *
 * @author      Lynn Monsanto
 * @since 1.4
 */
public interface AccessibleExtendedComponent extends AccessibleComponent {

    /**
     * Returns the tool tip text
     *
     * <p>
     *  返回工具提示文本
     * 
     * 
     * @return the tool tip text, if supported, of the object;
     * otherwise, null
     */
    public String getToolTipText();

    /**
     * Returns the titled border text
     *
     * <p>
     *  返回标题边框文本
     * 
     * 
     * @return the titled border text, if supported, of the object;
     * otherwise, null
     */
    public String getTitledBorderText();

    /**
     * Returns key bindings associated with this object
     *
     * <p>
     *  返回与此对象关联的键绑定
     * 
     * @return the key bindings, if supported, of the object;
     * otherwise, null
     * @see AccessibleKeyBinding
     */
    public AccessibleKeyBinding getAccessibleKeyBinding();
}
