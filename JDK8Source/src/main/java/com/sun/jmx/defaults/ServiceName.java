/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2008, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Used for storing default values used by JMX services.
 *
 * <p>
 *  用于存储JMX服务使用的默认值。
 * 
 * 
 * @since 1.5
 */
public class ServiceName {

    // private constructor defined to "hide" the default public constructor
    private ServiceName() {
    }

    /**
     * The object name of the MBeanServer delegate object
     * <BR>
     * The value is <CODE>JMImplementation:type=MBeanServerDelegate</CODE>.
     * <p>
     *  MBeanServer委托对象的对象名称
     * <BR>
     *  值为<CODE> JMImplementation：type = MBeanServerDelegate </CODE>。
     * 
     */
    public static final String DELEGATE =
        "JMImplementation:type=MBeanServerDelegate" ;

    /**
     * The default key properties for registering the class loader of the
     * MLet service.
     * <BR>
     * The value is <CODE>type=MLet</CODE>.
     * <p>
     *  用于注册MLet服务的类装入器的默认键属性。
     * <BR>
     *  值为<CODE> type = MLet </CODE>。
     * 
     */
    public static final String MLET = "type=MLet";

    /**
     * The default domain.
     * <BR>
     * The value is <CODE>DefaultDomain</CODE>.
     * <p>
     *  默认域。
     * <BR>
     *  值为<CODE> DefaultDomain </CODE>。
     * 
     */
    public static final String DOMAIN = "DefaultDomain";

    /**
     * The name of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>Java Management Extensions</CODE>.
     * <p>
     *  此产品实现的JMX规范的名称。
     * <BR>
     *  值为<CODE> Java管理扩展</CODE>。
     * 
     */
    public static final String JMX_SPEC_NAME = "Java Management Extensions";

    /**
     * The version of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>1.4</CODE>.
     * <p>
     *  由本产品实现的JMX规范的版本。
     * <BR>
     *  值为<CODE> 1.4 </CODE>。
     * 
     */
    public static final String JMX_SPEC_VERSION = "1.4";

    /**
     * The vendor of the JMX specification implemented by this product.
     * <BR>
     * The value is <CODE>Oracle Corporation</CODE>.
     * <p>
     *  本产品实现的JMX规范的供应商。
     * <BR>
     *  值为<CODE> Oracle Corporation </CODE>。
     * 
     */
    public static final String JMX_SPEC_VENDOR = "Oracle Corporation";

    /**
     * The name of this product implementing the  JMX specification.
     * <BR>
     * The value is <CODE>JMX</CODE>.
     * <p>
     *  实现JMX规范的产品的名称。
     * <BR>
     *  值为<CODE> JMX </CODE>。
     * 
     */
    public static final String JMX_IMPL_NAME = "JMX";

    /**
     * The name of the vendor of this product implementing the
     * JMX specification.
     * <BR>
     * The value is <CODE>Oracle Corporation</CODE>.
     * <p>
     *  实现JMX规范的此产品的供应商的名称。
     * <BR>
     */
    public static final String JMX_IMPL_VENDOR = "Oracle Corporation";
}
