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

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

/**
 * <code>SOAPFactory</code> is a factory for creating various objects
 * that exist in the SOAP XML tree.

 * <code>SOAPFactory</code> can be
 * used to create XML fragments that will eventually end up in the
 * SOAP part. These fragments can be inserted as children of the
 * {@link SOAPHeaderElement} or {@link SOAPBodyElement} or
 * {@link SOAPEnvelope} or other {@link SOAPElement} objects.
 *
 * <code>SOAPFactory</code> also has methods to create
 * <code>javax.xml.soap.Detail</code> objects as well as
 * <code>java.xml.soap.Name</code> objects.
 *
 * <p>
 *  <code> SOAPFactory </code>是一个用于创建SOAP XML树中存在的各种对象的工厂。
 * 
 *  <code> SOAPFactory </code>可用于创建最终将在SOAP部分中结束的XML片段。
 * 这些片段可以作为{@link SOAPHeaderElement}或{@link SOAPBodyElement}或{@link SOAPEnvelope}或其他{@link SOAPElement}对
 * 象的子代插入。
 *  <code> SOAPFactory </code>可用于创建最终将在SOAP部分中结束的XML片段。
 * 
 *  <code> SOAPFactory </code>也有创建<code> javax.xml.soap.Detail </code>对象以及<code> java.xml.soap.Name </code>
 * 对象的方法。
 * 
 */
public abstract class SOAPFactory {

    /**
     * A constant representing the property used to lookup the name of
     * a <code>SOAPFactory</code> implementation class.
     * <p>
     *  一个常量,表示用于查找<code> SOAPFactory </code>实现类的名称的属性。
     * 
     */
    static private final String SOAP_FACTORY_PROPERTY =
        "javax.xml.soap.SOAPFactory";

    /**
     * Class name of default <code>SOAPFactory</code> implementation.
     * <p>
     *  默认<code> SOAPFactory </code>实现的类名。
     * 
     */
    static final String DEFAULT_SOAP_FACTORY
        = "com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPFactory1_1Impl";

    /**
     * Creates a <code>SOAPElement</code> object from an existing DOM
     * <code>Element</code>. If the DOM <code>Element</code> that is passed in
     * as an argument is already a <code>SOAPElement</code> then this method
     * must return it unmodified without any further work. Otherwise, a new
     * <code>SOAPElement</code> is created and a deep copy is made of the
     * <code>domElement</code> argument. The concrete type of the return value
     * will depend on the name of the <code>domElement</code> argument. If any
     * part of the tree rooted in <code>domElement</code> violates SOAP rules, a
     * <code>SOAPException</code> will be thrown.
     *
     * <p>
     *  从现有DOM <code>元素</code>创建<code> SOAPElement </code>对象。
     * 如果作为参数传递的DOM <code> Element </code>已经是一个<code> SOAPElement </code>,那么这个方法必须返回它没有任何进一步的工作。
     * 否则,将创建一个新的<code> SOAPElement </code>,并创建一个深层副本<code> domElement </code>参数。
     * 返回值的具体类型取决于<code> domElement </code>参数的名称。
     * 如果根在<code> domElement </code>中的树的任何部分违反SOAP规则,则将抛出<code> SOAPException </code>。
     * 
     * 
     * @param domElement - the <code>Element</code> to be copied.
     *
     * @return a new <code>SOAPElement</code> that is a copy of <code>domElement</code>.
     *
     * @exception SOAPException if there is an error in creating the
     *            <code>SOAPElement</code> object
     *
     * @since SAAJ 1.3
     */
    public SOAPElement createElement(Element domElement) throws SOAPException {
        throw new UnsupportedOperationException("createElement(org.w3c.dom.Element) must be overridden by all subclasses of SOAPFactory.");
    }

    /**
     * Creates a <code>SOAPElement</code> object initialized with the
     * given <code>Name</code> object. The concrete type of the return value
     * will depend on the name given to the new <code>SOAPElement</code>. For
     * instance, a new <code>SOAPElement</code> with the name
     * "{http://www.w3.org/2003/05/soap-envelope}Envelope" would cause a
     * <code>SOAPEnvelope</code> that supports SOAP 1.2 behavior to be created.
     *
     * <p>
     * 创建用给定的<code> Name </code>对象初始化的<code> SOAPElement </code>对象。
     * 返回值的具体类型取决于赋予新<code> SOAPElement </code>的名称。
     * 例如,名称为"{http://www.w3.org/2003/05/soap-envelope}Envelope")的新的<code> SOAPElement </code>会导致<code> SOAP
     * Envelope </code>支持要创建的SOAP 1.2行为。
     * 返回值的具体类型取决于赋予新<code> SOAPElement </code>的名称。
     * 
     * 
     * @param name a <code>Name</code> object with the XML name for
     *             the new element
     *
     * @return the new <code>SOAPElement</code> object that was
     *         created
     *
     * @exception SOAPException if there is an error in creating the
     *            <code>SOAPElement</code> object
     * @see SOAPFactory#createElement(javax.xml.namespace.QName)
     */
    public abstract SOAPElement createElement(Name name) throws SOAPException;

    /**
     * Creates a <code>SOAPElement</code> object initialized with the
     * given <code>QName</code> object. The concrete type of the return value
     * will depend on the name given to the new <code>SOAPElement</code>. For
     * instance, a new <code>SOAPElement</code> with the name
     * "{http://www.w3.org/2003/05/soap-envelope}Envelope" would cause a
     * <code>SOAPEnvelope</code> that supports SOAP 1.2 behavior to be created.
     *
     * <p>
     *  创建用给定的<code> QName </code>对象初始化的<code> SOAPElement </code>对象。
     * 返回值的具体类型取决于赋予新<code> SOAPElement </code>的名称。
     * 例如,名称为"{http://www.w3.org/2003/05/soap-envelope}Envelope")的新的<code> SOAPElement </code>会导致<code> SOAP
     * Envelope </code>支持要创建的SOAP 1.2行为。
     * 返回值的具体类型取决于赋予新<code> SOAPElement </code>的名称。
     * 
     * 
     * @param qname a <code>QName</code> object with the XML name for
     *             the new element
     *
     * @return the new <code>SOAPElement</code> object that was
     *         created
     *
     * @exception SOAPException if there is an error in creating the
     *            <code>SOAPElement</code> object
     * @see SOAPFactory#createElement(Name)
     * @since SAAJ 1.3
     */
    public  SOAPElement createElement(QName qname) throws SOAPException {
        throw new UnsupportedOperationException("createElement(QName) must be overridden by all subclasses of SOAPFactory.");
    }

    /**
     * Creates a <code>SOAPElement</code> object initialized with the
     * given local name.
     *
     * <p>
     *  创建用给定本地名称初始化的<code> SOAPElement </code>对象。
     * 
     * 
     * @param localName a <code>String</code> giving the local name for
     *             the new element
     *
     * @return the new <code>SOAPElement</code> object that was
     *         created
     *
     * @exception SOAPException if there is an error in creating the
     *            <code>SOAPElement</code> object
     */
    public abstract SOAPElement createElement(String localName)
        throws SOAPException;


    /**
     * Creates a new <code>SOAPElement</code> object with the given
     * local name, prefix and uri. The concrete type of the return value
     * will depend on the name given to the new <code>SOAPElement</code>. For
     * instance, a new <code>SOAPElement</code> with the name
     * "{http://www.w3.org/2003/05/soap-envelope}Envelope" would cause a
     * <code>SOAPEnvelope</code> that supports SOAP 1.2 behavior to be created.
     *
     * <p>
     *  使用给定的本地名称,前缀和uri创建一个新的<code> SOAPElement </code>对象。返回值的具体类型取决于赋予新<code> SOAPElement </code>的名称。
     * 例如,名称为"{http://www.w3.org/2003/05/soap-envelope}Envelope")的新的<code> SOAPElement </code>会导致<code> SOAP
     * Envelope </code>支持要创建的SOAP 1.2行为。
     *  使用给定的本地名称,前缀和uri创建一个新的<code> SOAPElement </code>对象。返回值的具体类型取决于赋予新<code> SOAPElement </code>的名称。
     * 
     * 
     * @param localName a <code>String</code> giving the local name
     *                  for the new element
     * @param prefix the prefix for this <code>SOAPElement</code>
     * @param uri a <code>String</code> giving the URI of the
     *            namespace to which the new element belongs
     *
     * @exception SOAPException if there is an error in creating the
     *            <code>SOAPElement</code> object
     */
    public abstract SOAPElement createElement(
        String localName,
        String prefix,
        String uri)
        throws SOAPException;

    /**
     * Creates a new <code>Detail</code> object which serves as a container
     * for <code>DetailEntry</code> objects.
     * <P>
     * This factory method creates <code>Detail</code> objects for use in
     * situations where it is not practical to use the <code>SOAPFault</code>
     * abstraction.
     *
     * <p>
     *  创建一个新的<code> Detail </code>对象,作为<code> DetailEntry </code>对象的容器。
     * <P>
     * 此工厂方法创建<code> Detail </code>对象,以便在不切实际使用<code> SOAPFault </code>抽象的情况下使用。
     * 
     * 
     * @return a <code>Detail</code> object
     * @throws SOAPException if there is a SOAP error
     * @throws UnsupportedOperationException if the protocol specified
     *         for the SOAPFactory was <code>DYNAMIC_SOAP_PROTOCOL</code>
     */
    public abstract Detail createDetail() throws SOAPException;

    /**
     *Creates a new <code>SOAPFault</code> object initialized with the given <code>reasonText</code>
     *  and <code>faultCode</code>
     * <p>
     *  reates使用给定的<code> reasonText </code>和<code> faultCode </code>初始化的新的<code> SOAPFault <
     * 
     * 
     *@param reasonText the ReasonText/FaultString for the fault
     *@param faultCode the FaultCode for the fault
     *@return a <code>SOAPFault</code> object
     *@throws SOAPException if there is a SOAP error
     *@since SAAJ 1.3
     */
    public abstract SOAPFault createFault(String reasonText, QName faultCode) throws SOAPException;

    /**
     *Creates a new default <code>SOAPFault</code> object
     * <p>
     *  reates一个新的默认<code> SOAPFault </code>对象
     * 
     * 
     *@return a <code>SOAPFault</code> object
     *@throws SOAPException if there is a SOAP error
     *@since SAAJ 1.3
     */
    public abstract SOAPFault createFault() throws SOAPException;

    /**
     * Creates a new <code>Name</code> object initialized with the
     * given local name, namespace prefix, and namespace URI.
     * <P>
     * This factory method creates <code>Name</code> objects for use in
     * situations where it is not practical to use the <code>SOAPEnvelope</code>
     * abstraction.
     *
     * <p>
     *  创建使用给定本地名称,命名空间前缀和命名空间URI初始化的新<code> Name </code>对象。
     * <P>
     *  此工厂方法创建<code> Name </code>对象,以便在使用<code> SOAPEnvelope </code>抽象不切实际的情况下使用。
     * 
     * 
     * @param localName a <code>String</code> giving the local name
     * @param prefix a <code>String</code> giving the prefix of the namespace
     * @param uri a <code>String</code> giving the URI of the namespace
     * @return a <code>Name</code> object initialized with the given
     *         local name, namespace prefix, and namespace URI
     * @throws SOAPException if there is a SOAP error
     */
    public abstract Name createName(
        String localName,
        String prefix,
        String uri)
        throws SOAPException;

    /**
     * Creates a new <code>Name</code> object initialized with the
     * given local name.
     * <P>
     * This factory method creates <code>Name</code> objects for use in
     * situations where it is not practical to use the <code>SOAPEnvelope</code>
     * abstraction.
     *
     * <p>
     *  创建用给定本地名称初始化的新<code> Name </code>对象。
     * <P>
     *  此工厂方法创建<code> Name </code>对象,以便在使用<code> SOAPEnvelope </code>抽象不切实际的情况下使用。
     * 
     * 
     * @param localName a <code>String</code> giving the local name
     * @return a <code>Name</code> object initialized with the given
     *         local name
     * @throws SOAPException if there is a SOAP error
     */
    public abstract Name createName(String localName) throws SOAPException;

    /**
     * Creates a new <code>SOAPFactory</code> object that is an instance of
     * the default implementation (SOAP 1.1),
     *
     * This method uses the following ordered lookup procedure to determine the SOAPFactory implementation class to load:
     * <UL>
     *  <LI> Use the javax.xml.soap.SOAPFactory system property.
     *  <LI> Use the properties file "lib/jaxm.properties" in the JRE directory. This configuration file is in standard
     * java.util.Properties format and contains the fully qualified name of the implementation class with the key being the
     * system property defined above.
     *  <LI> Use the Services API (as detailed in the JAR specification), if available, to determine the classname. The Services API
     * will look for a classname in the file META-INF/services/javax.xml.soap.SOAPFactory in jars available to the runtime.
     *  <LI> Use the SAAJMetaFactory instance to locate the SOAPFactory implementation class.
     * </UL>
     *
     * <p>
     *  创建一个新的<code> SOAPFactory </code>对象,它是默认实现(SOAP 1.1)的一个实例,
     * 
     *  此方法使用以下有序查找过程来确定要加载的SOAPFactory实现类：
     * <UL>
     * <LI>使用javax.xml.soap.SOAPFactory系统属性。 <LI>使用JRE目录中的属性文件"lib / jaxm.properties"。
     * 此配置文件采用标准java.util.Properties格式,并包含实现类的完全限定名称,其中键是上面定义的系统属性。
     *  <LI>使用Services API(如JAR规范中所述)(如果可用)来确定类名。
     * 
     * @return a new instance of a <code>SOAPFactory</code>
     *
     * @exception SOAPException if there was an error creating the
     *            default <code>SOAPFactory</code>
     * @see SAAJMetaFactory
     */
    public static SOAPFactory newInstance()
        throws SOAPException
    {
        try {
            SOAPFactory factory = (SOAPFactory) FactoryFinder.find(
                    SOAP_FACTORY_PROPERTY, DEFAULT_SOAP_FACTORY, false);
            if (factory != null)
                return factory;
            return newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        } catch (Exception ex) {
            throw new SOAPException(
                "Unable to create SOAP Factory: " + ex.getMessage());
        }

    }

    /**
     * Creates a new <code>SOAPFactory</code> object that is an instance of
     * the specified implementation, this method uses the SAAJMetaFactory to
     * locate the implementation class and create the SOAPFactory instance.
     *
     * <p>
     *  Services API将在运行时可用的jars中的文件META-INF / services / javax.xml.soap.SOAPFactory中查找类名。
     *  <LI>使用SAAJMetaFactory实例来查找SOAPFactory实现类。
     * </UL>
     * 
     * 
     * @return a new instance of a <code>SOAPFactory</code>
     *
     * @param protocol  a string constant representing the protocol of the
     *                   specified SOAP factory implementation. May be
     *                   either <code>DYNAMIC_SOAP_PROTOCOL</code>,
     *                   <code>DEFAULT_SOAP_PROTOCOL</code> (which is the same
     *                   as) <code>SOAP_1_1_PROTOCOL</code>, or
     *                   <code>SOAP_1_2_PROTOCOL</code>.
     *
     * @exception SOAPException if there was an error creating the
     *            specified <code>SOAPFactory</code>
     * @see SAAJMetaFactory
     * @since SAAJ 1.3
     */
    public static SOAPFactory newInstance(String protocol)
        throws SOAPException {
            return SAAJMetaFactory.getInstance().newSOAPFactory(protocol);
    }
}
