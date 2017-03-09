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
/*
/* <p>
/* 
 * @author    IBM Corp.
 *
 * Copyright IBM Corp. 1999-2000.  All rights reserved.
 */

package javax.management.modelmbean;

import javax.management.DynamicMBean;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.PersistentMBean;
import javax.management.RuntimeOperationsException;

/**
 * This interface must be implemented by the ModelMBeans. An implementation of this interface
 * must be shipped with every JMX Agent.
 * <P>
 * Java resources wishing to be manageable instantiate the ModelMBean using the MBeanServer's
 * createMBean method.  The resource then sets the ModelMBeanInfo (with Descriptors) for the ModelMBean
 * instance. The attributes and operations exposed via the ModelMBeanInfo for the ModelMBean are accessible
 * from MBeans, connectors/adaptors like other MBeans. Through the ModelMBeanInfo Descriptors, values and methods in
 * the managed application can be defined and mapped to attributes and operations of the ModelMBean.
 * This mapping can be defined during development in an XML formatted file or dynamically and
 * programmatically at runtime.
 * <P>
 * Every ModelMBean which is instantiated in the MBeanServer becomes manageable:
 * its attributes and operations
 * become remotely accessible through the connectors/adaptors connected to that MBeanServer.
 * A Java object cannot be registered in the MBeanServer unless it is a JMX compliant MBean.
 * By instantiating a ModelMBean, resources are guaranteed that the MBean is valid.
 * <P>
 * MBeanException and RuntimeOperationsException must be thrown on every public method.  This allows
 * for wrapping exceptions from distributed communications (RMI, EJB, etc.).  These exceptions do
 * not have to be thrown by the implementation except in the scenarios described in the specification
 * and javadoc.
 *
 * <p>
 *  此接口必须由ModelMBeans实现。每个JMX代理都必须附带此接口的实现。
 * <P>
 *  希望可管理的Java资源使用MBeanServer的createMBean方法实例化ModelMBean。
 * 然后,资源为ModelMBean实例设置ModelMBeanInfo(带有Descriptors)。
 * 通过ModelMBean的ModelMBeanInfo公开的属性和操作可以从MBean,连接器/适配器(如其他MBean)访问。
 * 通过ModelMBeanInfo描述符,可以定义托管应用程序中的值和方法,并将其映射到ModelMBean的属性和操作。
 * 此映射可以在开发期间在XML格式的文件中定义,也可以在运行时以动态和编程方式定义。
 * <P>
 *  在MBeanServer中实例化的每个ModelMBean变得易于管理：其属性和操作可通过连接到该MBeanServer的连接器/适配器远程访问。
 * 不能在MBeanServer中注册Java对象,除非它是符合JMX的MBean。通过实例化ModelMBean,保证MBean有效的资源。
 * <P>
 * 必须在每个公共方法上抛出MBeanException和RuntimeOperationsException。这允许包装来自分布式通信(RMI,EJB等)的异常。
 * 这些异常不必由实现抛出,除非在规范和javadoc中描述的情况。
 * 
 * @since 1.5
 */

public interface ModelMBean extends
         DynamicMBean,
         PersistentMBean,
         ModelMBeanNotificationBroadcaster
{

        /**
         * Initializes a ModelMBean object using ModelMBeanInfo passed in.
         * This method makes it possible to set a customized ModelMBeanInfo on
         * the ModelMBean as long as it is not registered with the MBeanServer.
         * <br>
         * Once the ModelMBean's ModelMBeanInfo (with Descriptors) are
         * customized and set on the ModelMBean, the  ModelMBean can be
         * registered with the MBeanServer.
         * <P>
         * If the ModelMBean is currently registered, this method throws
         * a {@link javax.management.RuntimeOperationsException} wrapping an
         * {@link IllegalStateException}
         *
         * <p>
         * 
         * 
         * @param inModelMBeanInfo The ModelMBeanInfo object to be used
         *        by the ModelMBean.
         *
         * @exception MBeanException Wraps a distributed communication
         *        Exception.
         * @exception RuntimeOperationsException
         * <ul><li>Wraps an {@link IllegalArgumentException} if
         *         the MBeanInfo passed in parameter is null.</li>
         *     <li>Wraps an {@link IllegalStateException} if the ModelMBean
         *         is currently registered in the MBeanServer.</li>
         * </ul>
         *
         **/
        public void setModelMBeanInfo(ModelMBeanInfo inModelMBeanInfo)
            throws MBeanException, RuntimeOperationsException;

        /**
         * Sets the instance handle of the object against which to
         * execute all methods in this ModelMBean management interface
         * (MBeanInfo and Descriptors).
         *
         * <p>
         *  使用传入的ModelMBeanInfo初始化ModelMBean对象。只要未向MBeanServer注册,就可以在ModelMBean上设置自定义的ModelMBeanInfo。
         * <br>
         *  一旦在ModelMBean上自定义并设置了ModelMBean的ModelMBeanInfo(带有Descriptors),就可以向MBeanServer注册ModelMBean。
         * <P>
         *  如果ModelMBean当前已注册,则此方法会抛出一个{@link javax.management.RuntimeOperationsException}包装一个{@link IllegalStateException}
         * 
         * @param mr Object that is the managed resource
         * @param mr_type The type of reference for the managed resource.  Can be: ObjectReference,
         *               Handle, IOR, EJBHandle, RMIReference.
         *               If the MBeanServer cannot process the mr_type passed in, an InvalidTargetTypeException
         *               will be thrown.
         *
         *
         * @exception MBeanException The initializer of the object has thrown an exception.
         * @exception RuntimeOperationsException Wraps an IllegalArgumentException:
         *       The managed resource type passed in parameter is null.
         * @exception InstanceNotFoundException The managed resource object could not be found
         * @exception InvalidTargetObjectTypeException The managed resource type cannot be processed by the
         * ModelMBean or JMX Agent.
         */
        public void setManagedResource(Object mr, String mr_type)
        throws MBeanException, RuntimeOperationsException,
                 InstanceNotFoundException, InvalidTargetObjectTypeException ;

}
