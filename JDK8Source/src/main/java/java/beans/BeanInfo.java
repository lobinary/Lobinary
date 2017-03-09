/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Image;

/**
 * Use the {@code BeanInfo} interface
 * to create a {@code BeanInfo} class
 * and provide explicit information about the methods,
 * properties, events, and other features of your beans.
 * <p>
 * When developing your bean, you can implement
 * the bean features required for your application task
 * omitting the rest of the {@code BeanInfo} features.
 * They will be obtained through the automatic analysis
 * by using the low-level reflection of the bean methods
 * and applying standard design patterns.
 * You have an opportunity to provide additional bean information
 * through various descriptor classes.
 * <p>
 * See the {@link SimpleBeanInfo} class that is
 * a convenient basic class for {@code BeanInfo} classes.
 * You can override the methods and properties of
 * the {@code SimpleBeanInfo} class to define specific information.
 * <p>
 * See also the {@link Introspector} class to learn more about bean behavior.
 * <p>
 *  使用{@code BeanInfo}接口创建一个{@code BeanInfo}类,并提供有关bean的方法,属性,事件和其他功能的显式信息。
 * <p>
 *  当开发bean时,可以实现您的应用程序任务所需的bean特性,省略{@code BeanInfo}功能的其余部分。它们将通过使用bean方法的低级反射和应用标准设计模式的自动分析获得。
 * 您有机会通过各种描述符类提供附加的bean信息。
 * <p>
 *  参见{@link SimpleBeanInfo}类,它是{@code BeanInfo}类的一个方便的基本类。您可以覆盖{@code SimpleBeanInfo}类的方法和属性以定义特定信息。
 * <p>
 *  另请参见{@link Introspector}类以了解有关bean行为的更多信息。
 * 
 */
public interface BeanInfo {

    /**
     * Returns the bean descriptor
     * that provides overall information about the bean,
     * such as its display name or its customizer.
     *
     * <p>
     *  返回提供有关bean的整体信息(例如其显示名称或其自定义程序)的bean描述符。
     * 
     * 
     * @return  a {@link BeanDescriptor} object,
     *          or {@code null} if the information is to
     *          be obtained through the automatic analysis
     */
    BeanDescriptor getBeanDescriptor();

    /**
     * Returns the event descriptors of the bean
     * that define the types of events fired by this bean.
     *
     * <p>
     *  返回定义由此bean所触发的事件类型的bean的事件描述符。
     * 
     * 
     * @return  an array of {@link EventSetDescriptor} objects,
     *          or {@code null} if the information is to
     *          be obtained through the automatic analysis
     */
    EventSetDescriptor[] getEventSetDescriptors();

    /**
     * A bean may have a default event typically applied when this bean is used.
     *
     * <p>
     *  当使用这个bean时,bean通常会应用一个默认事件。
     * 
     * 
     * @return  index of the default event in the {@code EventSetDescriptor} array
     *          returned by the {@code getEventSetDescriptors} method,
     *          or -1 if there is no default event
     */
    int getDefaultEventIndex();

    /**
     * Returns descriptors for all properties of the bean.
     * <p>
     * If a property is indexed, then its entry in the result array
     * belongs to the {@link IndexedPropertyDescriptor} subclass
     * of the {@link PropertyDescriptor} class.
     * A client of the {@code getPropertyDescriptors} method
     * can use the {@code instanceof} operator to check
     * whether a given {@code PropertyDescriptor}
     * is an {@code IndexedPropertyDescriptor}.
     *
     * <p>
     *  返回bean的所有属性的描述符。
     * <p>
     * 如果属性被索引,则其在结果数组中的条目属于{@link PropertyDescriptor}类的{@link IndexedPropertyDescriptor}子类。
     *  {@code getPropertyDescriptors}方法的客户端可以使用{@code instanceof}运算符来检查给定的{@code PropertyDescriptor}是否为{@code IndexedPropertyDescriptor}
     * 。
     * 如果属性被索引,则其在结果数组中的条目属于{@link PropertyDescriptor}类的{@link IndexedPropertyDescriptor}子类。
     * 
     * 
     * @return  an array of {@code PropertyDescriptor} objects,
     *          or {@code null} if the information is to
     *          be obtained through the automatic analysis
     */
    PropertyDescriptor[] getPropertyDescriptors();

    /**
     * A bean may have a default property commonly updated when this bean is customized.
     *
     * <p>
     *  当定制这个bean时,bean可能具有通常更新的默认属性。
     * 
     * 
     * @return  index of the default property in the {@code PropertyDescriptor} array
     *          returned by the {@code getPropertyDescriptors} method,
     *          or -1 if there is no default property
     */
    int getDefaultPropertyIndex();

    /**
     * Returns the method descriptors of the bean
     * that define the externally visible methods supported by this bean.
     *
     * <p>
     *  返回定义该bean支持的外部可见方法的bean的方法描述符。
     * 
     * 
     * @return  an array of {@link MethodDescriptor} objects,
     *          or {@code null} if the information is to
     *          be obtained through the automatic analysis
     */
    MethodDescriptor[] getMethodDescriptors();

    /**
     * This method enables the current {@code BeanInfo} object
     * to return an arbitrary collection of other {@code BeanInfo} objects
     * that provide additional information about the current bean.
     * <p>
     * If there are conflicts or overlaps between the information
     * provided by different {@code BeanInfo} objects,
     * the current {@code BeanInfo} object takes priority
     * over the additional {@code BeanInfo} objects.
     * Array elements with higher indices take priority
     * over the elements with lower indices.
     *
     * <p>
     *  此方法使当前的{@code BeanInfo}对象返回其他{@code BeanInfo}对象的任意集合,这些对象提供关于当前bean的附加信息。
     * <p>
     *  如果不同{@code BeanInfo}对象提供的信息之间存在冲突或重叠,则当前{@code BeanInfo}对象优先于其他{@code BeanInfo}对象。
     * 具有较高索引的数组元素优先于具有较低索引的元素。
     * 
     * 
     * @return  an array of {@code BeanInfo} objects,
     *          or {@code null} if there are no additional {@code BeanInfo} objects
     */
    BeanInfo[] getAdditionalBeanInfo();

    /**
     * Returns an image that can be used to represent the bean in toolboxes or toolbars.
     * <p>
     * There are four possible types of icons:
     * 16 x 16 color, 32 x 32 color, 16 x 16 mono, and 32 x 32 mono.
     * If you implement a bean so that it supports a single icon,
     * it is recommended to use 16 x 16 color.
     * Another recommendation is to set a transparent background for the icons.
     *
     * <p>
     *  返回可用于在工具箱或工具栏中表示bean的图像。
     * <p>
     *  有四种可能类型的图标：16×16色,32×32色,16×16单色和32×32单色。如果实现一个bean以使它支持单个图标,则建议使用16 x 16颜色。另一个建议是为图标设置透明背景。
     * 
     * 
     * @param  iconKind  the kind of icon requested
     * @return           an image object representing the requested icon,
     *                   or {@code null} if no suitable icon is available
     *
     * @see #ICON_COLOR_16x16
     * @see #ICON_COLOR_32x32
     * @see #ICON_MONO_16x16
     * @see #ICON_MONO_32x32
     */
    Image getIcon(int iconKind);

    /**
     * Constant to indicate a 16 x 16 color icon.
     * <p>
     *  常量,表示16 x 16颜色图标。
     * 
     */
    final static int ICON_COLOR_16x16 = 1;

    /**
     * Constant to indicate a 32 x 32 color icon.
     * <p>
     *  常量,表示32 x 32颜色图标。
     * 
     */
    final static int ICON_COLOR_32x32 = 2;

    /**
     * Constant to indicate a 16 x 16 monochrome icon.
     * <p>
     * 常量,表示16 x 16单色图标。
     * 
     */
    final static int ICON_MONO_16x16 = 3;

    /**
     * Constant to indicate a 32 x 32 monochrome icon.
     * <p>
     *  常量,表示32 x 32单色图标。
     */
    final static int ICON_MONO_32x32 = 4;
}
