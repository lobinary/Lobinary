/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

/**
* The access point for the implementation classes of the factories defined in the
* SAAJ API. All of the <code>newInstance</code> methods defined on factories in
* SAAJ 1.3 defer to instances of this class to do the actual object creation.
* The implementations of <code>newInstance()</code> methods (in SOAPFactory and MessageFactory)
* that existed in SAAJ 1.2 have been updated to also delegate to the SAAJMetaFactory when the SAAJ 1.2
* defined lookup fails to locate the Factory implementation class name.
*
* <p>
* SAAJMetaFactory is a service provider interface. There are no public methods on this
* class.
*
* <p>
*  SAAJ API中定义的工厂的实现类的访问点。在SAAJ 1.3工厂中定义的所有<code> newInstance </code>方法都会延迟这个类的实例来做实际的对象创建。
* 当SAAJ 1.2定义的查找无法定位Factory实现类名称时,SAAJ 1.2中存在的<code> newInstance()</code>方法(在SOAPFactory和MessageFactory
* 中)的实现已更新为委派给SAAJMetaFactory。
*  SAAJ API中定义的工厂的实现类的访问点。在SAAJ 1.3工厂中定义的所有<code> newInstance </code>方法都会延迟这个类的实例来做实际的对象创建。
* 
* <p>
*  SAAJMetaFactory是一个服务提供程序接口。这个类没有公共方法。
* 
* 
* @author SAAJ RI Development Team
* @since SAAJ 1.3
*/

public abstract class SAAJMetaFactory {
    static private final String META_FACTORY_CLASS_PROPERTY =
        "javax.xml.soap.MetaFactory";
    static final String DEFAULT_META_FACTORY_CLASS =
        "com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl";

    /**
     * Creates a new instance of a concrete <code>SAAJMetaFactory</code> object.
     * The SAAJMetaFactory is an SPI, it pulls the creation of the other factories together into a
     * single place. Changing out the SAAJMetaFactory has the effect of changing out the entire SAAJ
     * implementation. Service providers provide the name of their <code>SAAJMetaFactory</code>
     * implementation.
     *
     * This method uses the following ordered lookup procedure to determine the SAAJMetaFactory implementation class to load:
     * <UL>
     *  <LI> Use the javax.xml.soap.MetaFactory system property.
     *  <LI> Use the properties file "lib/jaxm.properties" in the JRE directory. This configuration file is in standard
     * java.util.Properties format and contains the fully qualified name of the implementation class with the key being the
     * system property defined above.
     *  <LI> Use the Services API (as detailed in the JAR specification), if available, to determine the classname. The Services API
     * will look for a classname in the file META-INF/services/javax.xml.soap.MetaFactory in jars available to the runtime.
     *  <LI> Default to com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl.
     * </UL>
     *
     * <p>
     *  创建具体<code> SAAJMetaFactory </code>对象的新实例。 SAAJMetaFactory是一个SPI,它把其他工厂的创建放在一个地方。
     * 更改SAAJMetaFactory具有更改整个SAAJ实现的效果。服务提供者提供他们的<code> SAAJMetaFactory </code>实现的名称。
     * 
     *  此方法使用以下有序查找过程来确定要加载的SAAJMetaFactory实现类：
     * <UL>
     * <LI>使用javax.xml.soap.MetaFactory系统属性。 <LI>使用JRE目录中的属性文件"lib / jaxm.properties"。
     * 此配置文件采用标准java.util.Properties格式,并包含实现类的完全限定名称,其中键是上面定义的系统属性。
     *  <LI>使用Services API(如JAR规范中所述)(如果可用)来确定类名。
     *  Services API将在运行时可用的jars中的文件META-INF / services / javax.xml.soap.MetaFactory中查找类名。
     * 
     * @return a concrete <code>SAAJMetaFactory</code> object
     * @exception SOAPException if there is an error in creating the <code>SAAJMetaFactory</code>
     */
    static SAAJMetaFactory getInstance() throws SOAPException {
            try {
                SAAJMetaFactory instance =
                    (SAAJMetaFactory) FactoryFinder.find(
                        META_FACTORY_CLASS_PROPERTY,
                        DEFAULT_META_FACTORY_CLASS);
                return instance;
            } catch (Exception e) {
                throw new SOAPException(
                    "Unable to create SAAJ meta-factory" + e.getMessage());
            }
    }

    protected SAAJMetaFactory() { }

     /**
      * Creates a <code>MessageFactory</code> object for
      * the given <code>String</code> protocol.
      *
      * <p>
      *  <LI>默认为com.sun.xml.internal.messaging.saaj.soap.SAAJMetaFactoryImpl。
      * </UL>
      * 
      * 
      * @param protocol a <code>String</code> indicating the protocol
      * @exception SOAPException if there is an error in creating the
      *            MessageFactory
      * @see SOAPConstants#SOAP_1_1_PROTOCOL
      * @see SOAPConstants#SOAP_1_2_PROTOCOL
      * @see SOAPConstants#DYNAMIC_SOAP_PROTOCOL
      */
    protected abstract MessageFactory newMessageFactory(String protocol)
        throws SOAPException;

     /**
      * Creates a <code>SOAPFactory</code> object for
      * the given <code>String</code> protocol.
      *
      * <p>
      *  为给定的<code> String </code>协议创建<code> MessageFactory </code>对象。
      * 
      * 
      * @param protocol a <code>String</code> indicating the protocol
      * @exception SOAPException if there is an error in creating the
      *            SOAPFactory
      * @see SOAPConstants#SOAP_1_1_PROTOCOL
      * @see SOAPConstants#SOAP_1_2_PROTOCOL
      * @see SOAPConstants#DYNAMIC_SOAP_PROTOCOL
      */
    protected abstract SOAPFactory newSOAPFactory(String protocol)
        throws SOAPException;
}
