/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.defaults;

import java.util.logging.Logger;

/**
 * This contains the property list defined for this
 * JMX implementation.
 *
 *
 * <p>
 *  这包含为此JMX实现定义的属性列表。
 * 
 * 
 * @since 1.5
 */
public class JmxProperties {

    // private constructor defined to "hide" the default public constructor
    private JmxProperties() {
    }

    // PUBLIC STATIC CONSTANTS
    //------------------------

    /**
     * References the property that specifies the directory where
     * the native libraries will be stored before the MLet Service
     * loads them into memory.
     * <p>
     * Property Name: <B>jmx.mlet.library.dir</B>
     * <p>
     *  引用指定在MLet服务将它们加载到内存之前本地库将被存储的目录的属性。
     * <p>
     *  属性名称：<B> jmx.mlet.library.dir </B>
     * 
     */
    public static final String JMX_INITIAL_BUILDER =
            "javax.management.builder.initial";

    /**
     * References the property that specifies the directory where
     * the native libraries will be stored before the MLet Service
     * loads them into memory.
     * <p>
     * Property Name: <B>jmx.mlet.library.dir</B>
     * <p>
     *  引用指定在MLet服务将它们加载到内存之前本地库将被存储的目录的属性。
     * <p>
     *  属性名称：<B> jmx.mlet.library.dir </B>
     * 
     */
    public static final String MLET_LIB_DIR = "jmx.mlet.library.dir";

    /**
     * References the property that specifies the full name of the JMX
     * specification implemented by this product.
     * <p>
     * Property Name: <B>jmx.specification.name</B>
     * <p>
     *  引用指定由此产品实现的JMX规范的全名的属性。
     * <p>
     *  属性名称：<B> jmx.specification.name </B>
     * 
     */
    public static final String JMX_SPEC_NAME = "jmx.specification.name";

    /**
     * References the property that specifies the version of the JMX
     * specification implemented by this product.
     * <p>
     * Property Name: <B>jmx.specification.version</B>
     * <p>
     *  引用指定由此产品实现的JMX规范的版本的属性。
     * <p>
     *  属性名称：<B> jmx.specification.version </B>
     * 
     */
    public static final String JMX_SPEC_VERSION = "jmx.specification.version";

    /**
     * References the property that specifies the vendor of the JMX
     * specification implemented by this product.
     * <p>
     * Property Name: <B>jmx.specification.vendor</B>
     * <p>
     *  引用指定由此产品实现的JMX规范的供应商的属性。
     * <p>
     *  属性名称：<B> jmx.specification.vendor </B>
     * 
     */
    public static final String JMX_SPEC_VENDOR = "jmx.specification.vendor";

    /**
     * References the property that specifies the full name of this product
     * implementing the  JMX specification.
     * <p>
     * Property Name: <B>jmx.implementation.name</B>
     * <p>
     *  引用指定实现JMX规范的此产品的完整名称的属性。
     * <p>
     *  属性名称：<B> jmx.implementation.name </B>
     * 
     */
    public static final String JMX_IMPL_NAME = "jmx.implementation.name";

    /**
     * References the property that specifies the name of the vendor of this
     * product implementing the  JMX specification.
     * <p>
     * Property Name: <B>jmx.implementation.vendor</B>
     * <p>
     *  引用指定实现JMX规范的此产品的供应商的名称的属性。
     * <p>
     *  属性名称：<B> jmx.implementation.vendor </B>
     * 
     */
    public static final String JMX_IMPL_VENDOR = "jmx.implementation.vendor";

    /**
     * References the property that specifies the version of this product
     * implementing the  JMX specification.
     * <p>
     * Property Name: <B>jmx.implementation.version</B>
     * <p>
     *  引用指定实现JMX规范的此产品的版本的属性。
     * <p>
     * 属性名称：<B> jmx.implementation.version </B>
     * 
     */
    public static final String JMX_IMPL_VERSION = "jmx.implementation.version";

    /**
     * Logger name for MBean Server information.
     * <p>
     *  MBean服务器信息的记录器名称。
     * 
     */
    public static final String MBEANSERVER_LOGGER_NAME =
            "javax.management.mbeanserver";

    /**
     * Logger for MBean Server information.
     * <p>
     *  MBean服务器信息的记录器。
     * 
     */
    public static final Logger MBEANSERVER_LOGGER =
            Logger.getLogger(MBEANSERVER_LOGGER_NAME);

    /**
     * Logger name for MLet service information.
     * <p>
     *  MLet服务信息的记录器名称。
     * 
     */
    public static final String MLET_LOGGER_NAME =
            "javax.management.mlet";

    /**
     * Logger for MLet service information.
     * <p>
     *  记录MLet服务信息。
     * 
     */
    public static final Logger MLET_LOGGER =
            Logger.getLogger(MLET_LOGGER_NAME);

    /**
     * Logger name for Monitor information.
     * <p>
     *  Monitor信息的记录器名称。
     * 
     */
    public static final String MONITOR_LOGGER_NAME =
            "javax.management.monitor";

    /**
     * Logger for Monitor information.
     * <p>
     *  监视器信息的记录器。
     * 
     */
    public static final Logger MONITOR_LOGGER =
            Logger.getLogger(MONITOR_LOGGER_NAME);

    /**
     * Logger name for Timer information.
     * <p>
     *  定时器信息的记录器名称。
     * 
     */
    public static final String TIMER_LOGGER_NAME =
            "javax.management.timer";

    /**
     * Logger for Timer information.
     * <p>
     *  定时器信息记录器。
     * 
     */
    public static final Logger TIMER_LOGGER =
            Logger.getLogger(TIMER_LOGGER_NAME);

    /**
     * Logger name for Event Management information.
     * <p>
     *  事件管理信息的记录器名称。
     * 
     */
    public static final String NOTIFICATION_LOGGER_NAME =
            "javax.management.notification";

    /**
     * Logger for Event Management information.
     * <p>
     *  事件管理信息的记录器。
     * 
     */
    public static final Logger NOTIFICATION_LOGGER =
            Logger.getLogger(NOTIFICATION_LOGGER_NAME);

    /**
     * Logger name for Relation Service.
     * <p>
     *  关系服务的记录器名称。
     * 
     */
    public static final String RELATION_LOGGER_NAME =
            "javax.management.relation";

    /**
     * Logger for Relation Service.
     * <p>
     *  关系服务的记录器。
     * 
     */
    public static final Logger RELATION_LOGGER =
            Logger.getLogger(RELATION_LOGGER_NAME);

    /**
     * Logger name for Model MBean.
     * <p>
     *  模型MBean的记录器名称。
     * 
     */
    public static final String MODELMBEAN_LOGGER_NAME =
            "javax.management.modelmbean";

    /**
     * Logger for Model MBean.
     * <p>
     *  用于模型MBean的记录器。
     * 
     */
    public static final Logger MODELMBEAN_LOGGER =
            Logger.getLogger(MODELMBEAN_LOGGER_NAME);

    /**
     * Logger name for all other JMX classes.
     * <p>
     *  所有其他JMX类的记录器名称。
     * 
     */
    public static final String MISC_LOGGER_NAME =
            "javax.management.misc";

    /**
     * Logger for all other JMX classes.
     * <p>
     *  所有其他JMX类的记录器。
     * 
     */
    public static final Logger MISC_LOGGER =
            Logger.getLogger(MISC_LOGGER_NAME);

    /**
     * Logger name for SNMP.
     * <p>
     *  SNMP的记录器名称。
     * 
     */
    public static final String SNMP_LOGGER_NAME =
            "javax.management.snmp";

    /**
     * Logger for SNMP.
     * <p>
     *  SNMP的记录器。
     * 
     */
    public static final Logger SNMP_LOGGER =
            Logger.getLogger(SNMP_LOGGER_NAME);

    /**
     * Logger name for SNMP Adaptor.
     * <p>
     *  SNMP适配器的记录器名称。
     * 
     */
    public static final String SNMP_ADAPTOR_LOGGER_NAME =
            "javax.management.snmp.daemon";

    /**
     * Logger for SNMP Adaptor.
     * <p>
     *  SNMP适配器的记录器。
     */
    public static final Logger SNMP_ADAPTOR_LOGGER =
            Logger.getLogger(SNMP_ADAPTOR_LOGGER_NAME);
}
