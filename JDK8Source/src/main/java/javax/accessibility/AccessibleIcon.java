/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * The AccessibleIcon interface should be supported by any object
 * that has an associated icon (e.g., buttons). This interface
 * provides the standard mechanism for an assistive technology
 * to get descriptive information about icons.
 * Applications can determine
 * if an object supports the AccessibleIcon interface by first
 * obtaining its AccessibleContext (see
 * {@link Accessible}) and then calling the
 * {@link AccessibleContext#getAccessibleIcon} method.
 * If the return value is not null, the object supports this interface.
 *
 * <p>
 *  任何具有相关图标的对象(例如按钮)都应该支持AccessibleIcon接口。此接口为辅助技术提供标准机制,以获取有关图标的描述性信息。
 * 应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用{@link AccessibleContext#getAccessibleIcon}方法
 * 来确定对象是否支持AccessibleIcon接口。
 *  任何具有相关图标的对象(例如按钮)都应该支持AccessibleIcon接口。此接口为辅助技术提供标准机制,以获取有关图标的描述性信息。如果返回值不为null,则对象支持此接口。
 * 
 * 
 * @see Accessible
 * @see AccessibleContext
 *
 * @author      Lynn Monsanto
 * @since 1.3
 */
public interface AccessibleIcon {

    /**
     * Gets the description of the icon.  This is meant to be a brief
     * textual description of the object.  For example, it might be
     * presented to a blind user to give an indication of the purpose
     * of the icon.
     *
     * <p>
     *  获取图标的描述。这意味着对象的简短文本描述。例如,它可以被呈现给盲人用户以给出图标的目的的指示。
     * 
     * 
     * @return the description of the icon
     */
    public String getAccessibleIconDescription();

    /**
     * Sets the description of the icon.  This is meant to be a brief
     * textual description of the object.  For example, it might be
     * presented to a blind user to give an indication of the purpose
     * of the icon.
     *
     * <p>
     *  设置图标的说明。这意味着对象的简短文本描述。例如,它可以被呈现给盲人用户以给出图标的目的的指示。
     * 
     * 
     * @param description the description of the icon
     */
    public void setAccessibleIconDescription(String description);

    /**
     * Gets the width of the icon
     *
     * <p>
     *  获取图标的宽度
     * 
     * 
     * @return the width of the icon.
     */
    public int getAccessibleIconWidth();

    /**
     * Gets the height of the icon
     *
     * <p>
     *  获取图标的高度
     * 
     * @return the height of the icon.
     */
    public int getAccessibleIconHeight();

}
