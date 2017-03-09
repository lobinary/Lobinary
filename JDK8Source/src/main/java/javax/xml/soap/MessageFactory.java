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


import java.io.IOException;
import java.io.InputStream;

/**
 * A factory for creating <code>SOAPMessage</code> objects.
 * <P>
 * A SAAJ client can create a <code>MessageFactory</code> object
 * using the method <code>newInstance</code>, as shown in the following
 * lines of code.
 * <PRE>
 *       MessageFactory mf = MessageFactory.newInstance();
 *       MessageFactory mf12 = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
 * </PRE>
 * <P>
 * All <code>MessageFactory</code> objects, regardless of how they are
 * created, will produce <code>SOAPMessage</code> objects that
 * have the following elements by default:
 * <UL>
 *  <LI>A <code>SOAPPart</code> object
 *  <LI>A <code>SOAPEnvelope</code> object
 *  <LI>A <code>SOAPBody</code> object
 *  <LI>A <code>SOAPHeader</code> object
 * </UL>
 * In some cases, specialized MessageFactory objects may be obtained that produce messages
 * prepopulated with additional entries in the <code>SOAPHeader</code> object and the
 * <code>SOAPBody</code> object.
 * The content of a new <code>SOAPMessage</code> object depends on which of the two
 * <code>MessageFactory</code> methods is used to create it.
 * <UL>
 *  <LI><code>createMessage()</code> <BR>
 *      This is the method clients would normally use to create a request message.
 *  <LI><code>createMessage(MimeHeaders, java.io.InputStream)</code> -- message has
 *       content from the <code>InputStream</code> object and headers from the
 *       <code>MimeHeaders</code> object <BR>
 *        This method can be used internally by a service implementation to
 *        create a message that is a response to a request.
 * </UL>
 * <p>
 *  创建<code> SOAPMessage </code>对象的工厂。
 * <P>
 *  SAAJ客户端可以使用方法<code> newInstance </code>创建<code> MessageFactory </code>对象,如以下代码行所示。
 * <PRE>
 *  MessageFactory mf = MessageFactory.newInstance(); MessageFactory mf12 = MessageFactory.newInstance(S
 * OAPConstants.SOAP_1_2_PROTOCOL);。
 * </PRE>
 * <P>
 *  默认情况下,所有<code> MessageFactory </code>对象都将产生<code> SOAPMessage </code>对象,它们具有以下元素：
 * <UL>
 *  <LI> A <code> SOAPPart </code>对象<LI> A <code> SOAPEnvelope </code>对象</li> A <code> SOAPBody </code> 
 * >对象。
 * </UL>
 *  在一些情况下,可以获得专门的MessageFactory对象,其产生预填充有<code> SOAPHeader </code>对象和<code> SOAPBody </code>对象中的附加条目的消息
 * 。
 * 新的<code> SOAPMessage </code>对象的内容取决于用于创建它的两个<code> MessageFactory </code>方法中的哪一个。
 * <UL>
 * <LI> <code> createMessage()</code> <BR>这是客户端通常用来创建请求消息的方法。
 *  <li> <code> createMessage(MimeHeaders,java.io.InputStream)</code>  -  message具有来自<code> InputStream 
 * </code>对象的内容和来自<code> MimeHeaders </code> BR>此方法可由服务实现在内部使用以创建作为对请求的响应的消息。
 * <LI> <code> createMessage()</code> <BR>这是客户端通常用来创建请求消息的方法。
 * </UL>
 */
public abstract class MessageFactory {

    static final String DEFAULT_MESSAGE_FACTORY
        = "com.sun.xml.internal.messaging.saaj.soap.ver1_1.SOAPMessageFactory1_1Impl";

    static private final String MESSAGE_FACTORY_PROPERTY
        = "javax.xml.soap.MessageFactory";

    /**
     * Creates a new <code>MessageFactory</code> object that is an instance
     * of the default implementation (SOAP 1.1),
     *
     * This method uses the following ordered lookup procedure to determine the MessageFactory implementation class to load:
     * <UL>
     *  <LI> Use the javax.xml.soap.MessageFactory system property.
     *  <LI> Use the properties file "lib/jaxm.properties" in the JRE directory. This configuration file is in standard
     * java.util.Properties format and contains the fully qualified name of the implementation class with the key being the
     * system property defined above.
     *  <LI> Use the Services API (as detailed in the JAR specification), if available, to determine the classname. The Services API
     * will look for a classname in the file META-INF/services/javax.xml.soap.MessageFactory in jars available to the runtime.
     *  <LI> Use the SAAJMetaFactory instance to locate the MessageFactory implementation class.
     * </UL>

     *
     * <p>
     *  创建一个新的<code> MessageFactory </code>对象,它是默认实现(SOAP 1.1)的一个实例,
     * 
     *  此方法使用以下有序查找过程来确定要加载的MessageFactory实现类：
     * <UL>
     *  <LI>使用javax.xml.soap.MessageFactory系统属性。 <LI>使用JRE目录中的属性文件"lib / jaxm.properties"。
     * 此配置文件采用标准java.util.Properties格式,并包含实现类的完全限定名称,其中键是上面定义的系统属性。
     *  <LI>使用Services API(如JAR规范中所述)(如果可用)来确定类名。
     *  Services API将在运行时可用的jars中的文件META-INF / services / javax.xml.soap.MessageFactory中查找类名。
     *  <LI>使用SAAJMetaFactory实例来定位MessageFactory实现类。
     * </UL>
     * 
     * 
     * @return a new instance of a <code>MessageFactory</code>
     *
     * @exception SOAPException if there was an error in creating the
     *            default implementation of the
     *            <code>MessageFactory</code>.
     * @see SAAJMetaFactory
     */

    public static MessageFactory newInstance() throws SOAPException {


        try {
            MessageFactory factory = (MessageFactory) FactoryFinder.find(
                    MESSAGE_FACTORY_PROPERTY,
                    DEFAULT_MESSAGE_FACTORY,
                    false);

            if (factory != null) {
                return factory;
            }
            return newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);

        } catch (Exception ex) {
            throw new SOAPException(
                    "Unable to create message factory for SOAP: "
                                    +ex.getMessage());
        }

    }

    /**
     * Creates a new <code>MessageFactory</code> object that is an instance
     * of the specified implementation.  May be a dynamic message factory,
     * a SOAP 1.1 message factory, or a SOAP 1.2 message factory. A dynamic
     * message factory creates messages based on the MIME headers specified
     * as arguments to the <code>createMessage</code> method.
     *
     * This method uses the SAAJMetaFactory to locate the implementation class
     * and create the MessageFactory instance.
     *
     * <p>
     * 创建一个新的<code> MessageFactory </code>对象,它是指定实现的实例。可以是动态消息工厂,SOAP 1.1消息工厂或SOAP 1.2消息工厂。
     * 动态消息工厂基于指定为<code> createMessage </code>方法的参数的MIME头创建消息。
     * 
     *  此方法使用SAAJMetaFactory来定位实现类并创建MessageFactory实例。
     * 
     * 
     * @return a new instance of a <code>MessageFactory</code>
     *
     * @param protocol  a string constant representing the class of the
     *                   specified message factory implementation. May be
     *                   either <code>DYNAMIC_SOAP_PROTOCOL</code>,
     *                   <code>DEFAULT_SOAP_PROTOCOL</code> (which is the same
     *                   as) <code>SOAP_1_1_PROTOCOL</code>, or
     *                   <code>SOAP_1_2_PROTOCOL</code>.
     *
     * @exception SOAPException if there was an error in creating the
     *            specified implementation of  <code>MessageFactory</code>.
     * @see SAAJMetaFactory
     * @since SAAJ 1.3
     */
    public static MessageFactory newInstance(String protocol) throws SOAPException {
        return SAAJMetaFactory.getInstance().newMessageFactory(protocol);
    }

    /**
     * Creates a new <code>SOAPMessage</code> object with the default
     * <code>SOAPPart</code>, <code>SOAPEnvelope</code>, <code>SOAPBody</code>,
     * and <code>SOAPHeader</code> objects. Profile-specific message factories
     * can choose to prepopulate the <code>SOAPMessage</code> object with
     * profile-specific headers.
     * <P>
     * Content can be added to this message's <code>SOAPPart</code> object, and
     * the message can be sent "as is" when a message containing only a SOAP part
     * is sufficient. Otherwise, the <code>SOAPMessage</code> object needs
     * to create one or more <code>AttachmentPart</code> objects and
     * add them to itself. Any content that is not in XML format must be
     * in an <code>AttachmentPart</code> object.
     *
     * <p>
     *  使用默认<code> SOAPPart </code>,<code> SOAPEnvelope </code>,<code> SOAPBody </code>和<code> SOAPHeader </code创建新的<code>
     *  SOAPMessage </code> >对象。
     * 配置文件特定的消息工厂可以选择使用配置文件特定的头来预填充<code> SOAPMessage </code>对象。
     * <P>
     *  可以将内容添加到此消息的<code> SOAPPart </code>对象中,并且当仅包含SOAP部分的消息足够时,可以"按原样"发送消息。
     * 否则,<code> SOAPMessage </code>对象需要创建一个或多个<code> AttachmentPart </code>对象,并将它们添加到自身。
     * 
     * @return a new <code>SOAPMessage</code> object
     * @exception SOAPException if a SOAP error occurs
     * @exception UnsupportedOperationException if the protocol of this
     *      <code>MessageFactory</code> instance is <code>DYNAMIC_SOAP_PROTOCOL</code>
     */
    public abstract SOAPMessage createMessage()
        throws SOAPException;

    /**
     * Internalizes the contents of the given <code>InputStream</code> object into a
     * new <code>SOAPMessage</code> object and returns the <code>SOAPMessage</code>
     * object.
     *
     * <p>
     * 任何不是XML格式的内容必须位于<code> AttachmentPart </code>对象中。
     * 
     * 
     * @param in the <code>InputStream</code> object that contains the data
     *           for a message
     * @param headers the transport-specific headers passed to the
     *        message in a transport-independent fashion for creation of the
     *        message
     * @return a new <code>SOAPMessage</code> object containing the data from
     *         the given <code>InputStream</code> object
     *
     * @exception IOException if there is a problem in reading data from
     *            the input stream
     *
     * @exception SOAPException may be thrown if the message is invalid
     *
     * @exception IllegalArgumentException if the <code>MessageFactory</code>
     *      requires one or more MIME headers to be present in the
     *      <code>headers</code> parameter and they are missing.
     *      <code>MessageFactory</code> implementations for
     *      <code>SOAP_1_1_PROTOCOL</code> or
     *      <code>SOAP_1_2_PROTOCOL</code> must not throw
     *      <code>IllegalArgumentException</code> for this reason.
     */
    public abstract SOAPMessage createMessage(MimeHeaders headers,
                                              InputStream in)
        throws IOException, SOAPException;
}
