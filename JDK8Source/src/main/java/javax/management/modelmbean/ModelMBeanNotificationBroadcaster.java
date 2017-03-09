/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.management.Attribute;
import javax.management.AttributeChangeNotification;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationListener;
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
 * 这些异常不必由实现抛出,除了在规范和javadoc中描述的情况。
 * 
 * 
 * @since 1.5
 */

public interface ModelMBeanNotificationBroadcaster extends NotificationBroadcaster
{

        /**
         * Sends a Notification which is passed in to the registered
         * Notification listeners on the ModelMBean as a
         * jmx.modelmbean.generic notification.
         *
         * <p>
         *  发送通知到ModelMBean上的注册通知侦听器的通知,作为jmx.modelmbean.generic通知。
         * 
         * 
         * @param ntfyObj The notification which is to be passed to
         * the 'handleNotification' method of the listener object.
         *
         * @exception MBeanException Wraps a distributed communication Exception.
         * @exception RuntimeOperationsException Wraps an IllegalArgumentException:
         *       The Notification object passed in parameter is null.
         *
         */

        public void sendNotification(Notification ntfyObj)
        throws MBeanException, RuntimeOperationsException;

        /**
         * Sends a Notification which contains the text string that is passed in
         * to the registered Notification listeners on the ModelMBean.
         *
         * <p>
         *  发送包含传递到ModelMBean上注册的通知侦听器的文本字符串的通知。
         * 
         * 
         * @param ntfyText The text which is to be passed in the Notification to the 'handleNotification'
         * method of the listener object.
         * the constructed Notification will be:
         *   type        "jmx.modelmbean.generic"
         *   source      this ModelMBean instance
         *   sequence    1
         *
         *
         * @exception MBeanException Wraps a distributed communication Exception.
         * @exception RuntimeOperationsException Wraps an IllegalArgumentException:
         *       The Notification text string passed in parameter is null.
         *
         */
        public void sendNotification(String ntfyText)
        throws MBeanException, RuntimeOperationsException;

        /**
         * Sends an attributeChangeNotification which is passed in to
         * the registered attributeChangeNotification listeners on the
         * ModelMBean.
         *
         * <p>
         *  发送一个传递给ModelMBean上注册的attributeChangeNotification侦听器的attributeChangeNotification。
         * 
         * 
         * @param notification The notification which is to be passed
         * to the 'handleNotification' method of the listener object.
         *
         * @exception MBeanException Wraps a distributed communication Exception.
         * @exception RuntimeOperationsException Wraps an IllegalArgumentException: The AttributeChangeNotification object passed in parameter is null.
         *
         */
        public void sendAttributeChangeNotification(AttributeChangeNotification notification)
        throws MBeanException, RuntimeOperationsException;


        /**
         * Sends an attributeChangeNotification which contains the old value and new value for the
         * attribute to the registered AttributeChangeNotification listeners on the ModelMBean.
         * <P>
         * <p>
         *  将包含属性的旧值和新值的attributeChangeNotification发送到ModelMBean上注册的AttributeChangeNotification侦听器。
         * <P>
         * 
         * @param oldValue The original value for the Attribute
         * @param newValue The current value for the Attribute
         * <PRE>
         * The constructed attributeChangeNotification will be:
         *   type        "jmx.attribute.change"
         *   source      this ModelMBean instance
         *   sequence    1
         *   attributeName oldValue.getName()
         *   attributeType oldValue's class
         *   attributeOldValue oldValue.getValue()
         *   attributeNewValue newValue.getValue()
         * </PRE>
         *
         * @exception MBeanException Wraps a distributed communication Exception.
         * @exception RuntimeOperationsException Wraps an IllegalArgumentException: An Attribute object passed in parameter is null
         * or the names of the two Attribute objects in parameter are not the same.
         */
        public void sendAttributeChangeNotification(Attribute oldValue, Attribute newValue)
        throws MBeanException, RuntimeOperationsException;


        /**
         * Registers an object which implements the NotificationListener interface as a listener.  This
         * object's 'handleNotification()' method will be invoked when any attributeChangeNotification is issued through
         * or by the ModelMBean.  This does not include other Notifications.  They must be registered
         * for independently. An AttributeChangeNotification will be generated for this attributeName.
         *
         * <p>
         *  将实现NotificationListener接口的对象注册为监听器。
         * 当通过或由ModelMBean发出任何attributeChangeNotification时,将调用此对象的"handleNotification()"方法。这不包括其他通知。他们必须独立注册。
         * 将为此attributeName生成AttributeChangeNotification。
         * 
         * 
         * @param listener The listener object which will handles notifications emitted by the registered MBean.
         * @param attributeName The name of the ModelMBean attribute for which to receive change notifications.
         *      If null, then all attribute changes will cause an attributeChangeNotification to be issued.
         * @param handback The context to be sent to the listener with the notification when a notification is emitted.
         *
         * @exception IllegalArgumentException The listener cannot be null.
         * @exception MBeanException Wraps a distributed communication Exception.
         * @exception RuntimeOperationsException Wraps an IllegalArgumentException The attribute name passed in parameter does not exist.
         *
         * @see #removeAttributeChangeNotificationListener
         */
        public void addAttributeChangeNotificationListener(NotificationListener listener,
                                                           String attributeName,
                                                           Object handback)
        throws MBeanException, RuntimeOperationsException, IllegalArgumentException;


        /**
         * Removes a listener for attributeChangeNotifications from the RequiredModelMBean.
         *
         * <p>
         * 
         * @param listener The listener name which was handling notifications emitted by the registered MBean.
         * This method will remove all information related to this listener.
         * @param attributeName The attribute for which the listener no longer wants to receive attributeChangeNotifications.
         * If null the listener will be removed for all attributeChangeNotifications.
         *
         * @exception ListenerNotFoundException The listener is not registered in the MBean or is null.
         * @exception MBeanException Wraps a distributed communication Exception.
         * @exception RuntimeOperationsException Wraps an IllegalArgumentException If the inAttributeName parameter does not
         * correspond to an attribute name.
         *
         * @see #addAttributeChangeNotificationListener
         */

        public void removeAttributeChangeNotificationListener(NotificationListener listener,
                                                              String attributeName)
        throws MBeanException, RuntimeOperationsException, ListenerNotFoundException;

}
