/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A customizer class provides a complete custom GUI for customizing
 * a target Java Bean.
 * <P>
 * Each customizer should inherit from the java.awt.Component class so
 * it can be instantiated inside an AWT dialog or panel.
 * <P>
 * Each customizer should have a null constructor.
 * <p>
 *  定制器类为定制目标Java Bean提供了一个完整的定制GUI。
 * <P>
 *  每个自定义程序应该继承java.awt.Component类,以便可以在AWT对话框或面板中实例化它。
 * <P>
 *  每个自定义程序应该有一个null构造函数。
 * 
 */

public interface Customizer {

    /**
     * Set the object to be customized.  This method should be called only
     * once, before the Customizer has been added to any parent AWT container.
     * <p>
     *  设置要自定义的对象。在将Customizer添加到任何父AWT容器之前,应该只调用此方法一次。
     * 
     * 
     * @param bean  The object to be customized.
     */
    void setObject(Object bean);

    /**
     * Register a listener for the PropertyChange event.  The customizer
     * should fire a PropertyChange event whenever it changes the target
     * bean in a way that might require the displayed properties to be
     * refreshed.
     *
     * <p>
     *  为PropertyChange事件注册侦听器。自定义程序应在每次以可能需要刷新显示的属性的方式更改目标bean时触发PropertyChange事件。
     * 
     * 
     * @param listener  An object to be invoked when a PropertyChange
     *          event is fired.
     */
     void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Remove a listener for the PropertyChange event.
     *
     * <p>
     *  删除PropertyChange事件的侦听器。
     * 
     * @param listener  The PropertyChange listener to be removed.
     */
    void removePropertyChangeListener(PropertyChangeListener listener);

}
