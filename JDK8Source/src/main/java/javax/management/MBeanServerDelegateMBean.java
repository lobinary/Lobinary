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

package javax.management;


/**
 * Defines the management interface  of an object of class MBeanServerDelegate.
 *
 * <p>
 *  定义MBeanServerDelegate类的对象的管理接口。
 * 
 * 
 * @since 1.5
 */
public interface MBeanServerDelegateMBean   {

    /**
     * Returns the MBean server agent identity.
     *
     * <p>
     *  返回MBean服务器代理标识。
     * 
     * 
     * @return the agent identity.
     */
    public String getMBeanServerId();

    /**
     * Returns the full name of the JMX specification implemented
     * by this product.
     *
     * <p>
     *  返回此产品实现的JMX规范的全名。
     * 
     * 
     * @return the specification name.
     */
    public String getSpecificationName();

    /**
     * Returns the version of the JMX specification implemented
     * by this product.
     *
     * <p>
     *  返回此产品实现的JMX规范的版本。
     * 
     * 
     * @return the specification version.
     */
    public String getSpecificationVersion();

    /**
     * Returns the vendor of the JMX specification implemented
     * by this product.
     *
     * <p>
     *  返回此产品实现的JMX规范的供应商。
     * 
     * 
     * @return the specification vendor.
     */
    public String getSpecificationVendor();

    /**
     * Returns the JMX implementation name (the name of this product).
     *
     * <p>
     *  返回JMX实现名称(此产品的名称)。
     * 
     * 
     * @return the implementation name.
     */
    public String getImplementationName();

    /**
     * Returns the JMX implementation version (the version of this product).
     *
     * <p>
     *  返回JMX实现版本(此产品的版本)。
     * 
     * 
     * @return the implementation version.
     */
    public String getImplementationVersion();

    /**
     * Returns the JMX implementation vendor (the vendor of this product).
     *
     * <p>
     *  返回JMX实现供应商(此产品的供应商)。
     * 
     * @return the implementation vendor.
     */
    public String getImplementationVendor();

}
