/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Iterator;

import javax.xml.namespace.QName;

/**
 * A representation of the SOAP header
 * element. A SOAP header element consists of XML data that affects
 * the way the application-specific content is processed by the message
 * provider. For example, transaction semantics, authentication information,
 * and so on, can be specified as the content of a <code>SOAPHeader</code>
 * object.
 * <P>
 * A <code>SOAPEnvelope</code> object contains an empty
 * <code>SOAPHeader</code> object by default. If the <code>SOAPHeader</code>
 * object, which is optional, is not needed, it can be retrieved and deleted
 * with the following line of code. The variable <i>se</i> is a
 * <code>SOAPEnvelope</code> object.
 * <PRE>
 *      se.getHeader().detachNode();
 * </PRE>
 *
 * A <code>SOAPHeader</code> object is created with the <code>SOAPEnvelope</code>
 * method <code>addHeader</code>. This method, which creates a new header and adds it
 * to the envelope, may be called only after the existing header has been removed.
 *
 * <PRE>
 *      se.getHeader().detachNode();
 *      SOAPHeader sh = se.addHeader();
 * </PRE>
 * <P>
 * A <code>SOAPHeader</code> object can have only <code>SOAPHeaderElement</code>
 * objects as its immediate children. The method <code>addHeaderElement</code>
 * creates a new <code>HeaderElement</code> object and adds it to the
 * <code>SOAPHeader</code> object. In the following line of code, the
 * argument to the method <code>addHeaderElement</code> is a <code>Name</code>
 * object that is the name for the new <code>HeaderElement</code> object.
 * <PRE>
 *      SOAPHeaderElement shElement = sh.addHeaderElement(name);
 * </PRE>
 *
 * <p>
 *  SOAP标头元素的表示形式。 SOAP头元素由影响消息提供程序处理应用程序特定内容方式的XML数据组成。
 * 例如,事务语义,认证信息等可以被指定为<code> SOAPHeader </code>对象的内容。
 * <P>
 *  默认情况下,<code> SOAPEnvelope </code>对象包含一个空的<code> SOAPHeader </code>对象。
 * 如果不需要<code> SOAPHeader </code>对象(可选),那么可以使用以下代码行检索和删除它。变量<i> </i>是一个<code> SOAPEnvelope </code>对象。
 * <PRE>
 *  se.getHeader()。detachNode();
 * </PRE>
 * 
 *  使用<code> SOAPEnvelope </code>方法<code> addHeader </code>创建<code> SOAPHeader </code>对象。
 * 此方法创建一个新标题并将其添加到包络,只有在删除现有标题后才能调用。
 * 
 * <PRE>
 *  se.getHeader()。detachNode(); SOAPHeader sh = se.addHeader();
 * </PRE>
 * <P>
 * <code> SOAPHeader </code>对象只能有<code> SOAPHeaderElement </code>对象作为其直接子对象。
 * 方法<code> addHeaderElement </code>创建一个新的<code> HeaderElement </code>对象并将其添加到<code> SOAPHeader </code>对
 * 象。
 * <code> SOAPHeader </code>对象只能有<code> SOAPHeaderElement </code>对象作为其直接子对象。
 * 在下面的代码行中,方法<code> addHeaderElement </code>的参数是<code> Name </code>对象,它是新的<code> HeaderElement </code>对
 * 象的名称。
 * <code> SOAPHeader </code>对象只能有<code> SOAPHeaderElement </code>对象作为其直接子对象。
 * <PRE>
 *  SOAPHeaderElement shElement = sh.addHeaderElement(name);
 * </PRE>
 * 
 * 
 * @see SOAPHeaderElement
 */
public interface SOAPHeader extends SOAPElement {
    /**
     * Creates a new <code>SOAPHeaderElement</code> object initialized with the
     * specified name and adds it to this <code>SOAPHeader</code> object.
     *
     * <p>
     *  创建一个用指定名称初始化的新的<code> SOAPHeaderElement </code>对象,并将其添加到此<code> SOAPHeader </code>对象。
     * 
     * 
     * @param name a <code>Name</code> object with the name of the new
     *        <code>SOAPHeaderElement</code> object
     * @return the new <code>SOAPHeaderElement</code> object that was
     *          inserted into this <code>SOAPHeader</code> object
     * @exception SOAPException if a SOAP error occurs
     * @see SOAPHeader#addHeaderElement(javax.xml.namespace.QName)
     */
    public SOAPHeaderElement addHeaderElement(Name name)
        throws SOAPException;

    /**
     * Creates a new <code>SOAPHeaderElement</code> object initialized with the
     * specified qname and adds it to this <code>SOAPHeader</code> object.
     *
     * <p>
     *  创建一个用指定的qname初始化的新的<code> SOAPHeaderElement </code>对象,并将其添加到此<code> SOAPHeader </code>对象。
     * 
     * 
     * @param qname a <code>QName</code> object with the qname of the new
     *        <code>SOAPHeaderElement</code> object
     * @return the new <code>SOAPHeaderElement</code> object that was
     *          inserted into this <code>SOAPHeader</code> object
     * @exception SOAPException if a SOAP error occurs
     * @see SOAPHeader#addHeaderElement(Name)
     * @since SAAJ 1.3
     */
    public SOAPHeaderElement addHeaderElement(QName qname)
        throws SOAPException;

    /**
     * Returns an <code>Iterator</code> over all the <code>SOAPHeaderElement</code> objects
     * in this <code>SOAPHeader</code> object
     * that have the specified <i>actor</i> and that have a MustUnderstand attribute
     * whose value is equivalent to <code>true</code>.
     * <p>
     * In SOAP 1.2 the <i>env:actor</i> attribute is replaced by the <i>env:role</i>
     * attribute, but with essentially the same semantics.
     *
     * <p>
     *  在具有指定<i> actor </i>且具有MustUnderstand的<code> SOAPHeader </code>对象中的所有<code> SOAPHeaderElement </code>
     * 对象上返回<code>迭代器</code>属性,其值等于<code> true </code>。
     * <p>
     *  在SOAP 1.2中,<i> env：actor </i>属性由<en>：role </i>属性替代,但基本上具有相同的语义。
     * 
     * 
     * @param actor a <code>String</code> giving the URI of the <code>actor</code> / <code>role</code>
     *        for which to search
     * @return an <code>Iterator</code> object over all the
     *         <code>SOAPHeaderElement</code> objects that contain the specified
     *          <code>actor</code> / <code>role</code> and are marked as MustUnderstand
     * @see #examineHeaderElements
     * @see #extractHeaderElements
     * @see SOAPConstants#URI_SOAP_ACTOR_NEXT
     *
     * @since SAAJ 1.2
     */
    public Iterator examineMustUnderstandHeaderElements(String actor);

    /**
     * Returns an <code>Iterator</code> over all the <code>SOAPHeaderElement</code> objects
     * in this <code>SOAPHeader</code> object
     * that have the specified <i>actor</i>.
     *
     * An <i>actor</i> is a global attribute that indicates the intermediate
     * parties that should process a message before it reaches its ultimate
     * receiver. An actor receives the message and processes it before sending
     * it on to the next actor. The default actor is the ultimate intended
     * recipient for the message, so if no actor attribute is included in a
     * <code>SOAPHeader</code> object, it is sent to the ultimate receiver
     * along with the message body.
     * <p>
     * In SOAP 1.2 the <i>env:actor</i> attribute is replaced by the <i>env:role</i>
     * attribute, but with essentially the same semantics.
     *
     * <p>
     *  在具有指定<i> actor </i>的<code> SOAPHeader </code>对象中的所有<code> SOAPHeaderElement </code>对象上返回<code>迭代器</code>
     * 。
     * 
     * </i>是一个全局属性,指示在消息到达其最终接收者之前应该处理消息的中间方。 actor接收消息并在将消息发送到下一个actor之前对其进行处理。
     * 默认actor是消息的最终预期接收者,因此如果<code> SOAPHeader </code>对象中没有包含actor属性,它将与消息体一起发送到最终接收者。
     * <p>
     *  在SOAP 1.2中,<i> env：actor </i>属性由<en>：role </i>属性替代,但基本上具有相同的语义。
     * 
     * 
     * @param actor a <code>String</code> giving the URI of the <code>actor</code> / <code>role</code>
     *        for which to search
     * @return an <code>Iterator</code> object over all the
     *         <code>SOAPHeaderElement</code> objects that contain the specified
     *          <code>actor</code> / <code>role</code>
     * @see #extractHeaderElements
     * @see SOAPConstants#URI_SOAP_ACTOR_NEXT
     */
    public Iterator examineHeaderElements(String actor);

    /**
     * Returns an <code>Iterator</code> over all the <code>SOAPHeaderElement</code> objects
     * in this <code>SOAPHeader</code> object
     * that have the specified <i>actor</i> and detaches them
     * from this <code>SOAPHeader</code> object.
     * <P>
     * This method allows an actor to process the parts of the
     * <code>SOAPHeader</code> object that apply to it and to remove
     * them before passing the message on to the next actor.
     * <p>
     * In SOAP 1.2 the <i>env:actor</i> attribute is replaced by the <i>env:role</i>
     * attribute, but with essentially the same semantics.
     *
     * <p>
     *  在具有指定<i> actor </i>的<code> SOAPHeader </code>对象中的所有<code> SOAPHeaderElement </code>对象中返回<code>迭代器</code>
     *  <code> SOAPHeader </code>对象。
     * <P>
     *  这个方法允许一个actor处理应用于它的<code> SOAPHeader </code>对象的部分,并在将消息传递给下一个actor之前删除它们。
     * <p>
     *  在SOAP 1.2中,<i> env：actor </i>属性由<en>：role </i>属性替代,但基本上具有相同的语义。
     * 
     * 
     * @param actor a <code>String</code> giving the URI of the <code>actor</code> / <code>role</code>
     *        for which to search
     * @return an <code>Iterator</code> object over all the
     *         <code>SOAPHeaderElement</code> objects that contain the specified
     *          <code>actor</code> / <code>role</code>
     *
     * @see #examineHeaderElements
     * @see SOAPConstants#URI_SOAP_ACTOR_NEXT
     */
    public Iterator extractHeaderElements(String actor);

    /**
     * Creates a new NotUnderstood <code>SOAPHeaderElement</code> object initialized
     * with the specified name and adds it to this <code>SOAPHeader</code> object.
     * This operation is supported only by SOAP 1.2.
     *
     * <p>
     *  创建用指定名称初始化的新NotUnderstood <code> SOAPHeaderElement </code>对象,并将其添加到此<code> SOAPHeader </code>对象。
     * 此操作仅由SOAP 1.2支持。
     * 
     * 
     * @param name a <code>QName</code> object with the name of the
     *        <code>SOAPHeaderElement</code> object that was not understood.
     * @return the new <code>SOAPHeaderElement</code> object that was
     *          inserted into this <code>SOAPHeader</code> object
     * @exception SOAPException if a SOAP error occurs.
     * @exception UnsupportedOperationException if this is a SOAP 1.1 Header.
     * @since SAAJ 1.3
     */
    public SOAPHeaderElement addNotUnderstoodHeaderElement(QName name)
        throws SOAPException;

    /**
     * Creates a new Upgrade <code>SOAPHeaderElement</code> object initialized
     * with the specified List of supported SOAP URIs and adds it to this
     * <code>SOAPHeader</code> object.
     * This operation is supported on both SOAP 1.1 and SOAP 1.2 header.
     *
     * <p>
     * 创建一个新的升级<code> SOAPHeaderElement </code>对象,使用指定的支持的SOAP URI列表初始化,并将其添加到此<code> SOAPHeader </code>对象。
     *  SOAP 1.1和SOAP 1.2标头都支持此操作。
     * 
     * 
     * @param supportedSOAPURIs an <code>Iterator</code> object with the URIs of SOAP
     *          versions supported.
     * @return the new <code>SOAPHeaderElement</code> object that was
     *          inserted into this <code>SOAPHeader</code> object
     * @exception SOAPException if a SOAP error occurs.
     * @since SAAJ 1.3
     */
    public SOAPHeaderElement addUpgradeHeaderElement(Iterator supportedSOAPURIs)
        throws SOAPException;

    /**
     * Creates a new Upgrade <code>SOAPHeaderElement</code> object initialized
     * with the specified array of supported SOAP URIs and adds it to this
     * <code>SOAPHeader</code> object.
     * This operation is supported on both SOAP 1.1 and SOAP 1.2 header.
     *
     * <p>
     *  创建一个新的升级<code> SOAPHeaderElement </code>对象,使用指定的受支持SOAP URI数组初始化,并将其添加到此<code> SOAPHeader </code>对象。
     *  SOAP 1.1和SOAP 1.2标头都支持此操作。
     * 
     * 
     * @param  supportedSoapUris an array of the URIs of SOAP versions supported.
     * @return the new <code>SOAPHeaderElement</code> object that was
     *          inserted into this <code>SOAPHeader</code> object
     * @exception SOAPException if a SOAP error occurs.
     * @since SAAJ 1.3
     */
    public SOAPHeaderElement addUpgradeHeaderElement(String[] supportedSoapUris)
        throws SOAPException;

    /**
     * Creates a new Upgrade <code>SOAPHeaderElement</code> object initialized
     * with the specified supported SOAP URI and adds it to this
     * <code>SOAPHeader</code> object.
     * This operation is supported on both SOAP 1.1 and SOAP 1.2 header.
     *
     * <p>
     *  创建使用指定的受支持SOAP URI初始化的新的升级<code> SOAPHeaderElement </code>对象,并将其添加到此<code> SOAPHeader </code>对象。
     *  SOAP 1.1和SOAP 1.2头都支持此操作。
     * 
     * 
     * @param supportedSoapUri the URI of SOAP the version that is supported.
     * @return the new <code>SOAPHeaderElement</code> object that was
     *          inserted into this <code>SOAPHeader</code> object
     * @exception SOAPException if a SOAP error occurs.
     * @since SAAJ 1.3
     */
    public SOAPHeaderElement addUpgradeHeaderElement(String supportedSoapUri)
        throws SOAPException;

    /**
     * Returns an <code>Iterator</code> over all the <code>SOAPHeaderElement</code> objects
     * in this <code>SOAPHeader</code> object.
     *
     * <p>
     *  在此<code> SOAPHeader </code>对象中的所有<code> SOAPHeaderElement </code>对象上返回<code>迭代器</code>
     * 
     * 
     * @return an <code>Iterator</code> object over all the
     *          <code>SOAPHeaderElement</code> objects contained by this
     *          <code>SOAPHeader</code>
     * @see #extractAllHeaderElements
     *
     * @since SAAJ 1.2
     */
    public Iterator examineAllHeaderElements();

    /**
     * Returns an <code>Iterator</code> over all the <code>SOAPHeaderElement</code> objects
     * in this <code>SOAPHeader</code> object and detaches them
     * from this <code>SOAPHeader</code> object.
     *
     * <p>
     *  在<code> SOAPHeader </code>对象中的所有<code> SOAPHeaderElement </code>对象中返回<code>迭代器</code>,并将它们从此<code> S
     * OAPHeader </code>对象中分离出来。
     * 
     * @return an <code>Iterator</code> object over all the
     *          <code>SOAPHeaderElement</code> objects contained by this
     *          <code>SOAPHeader</code>
     *
     * @see #examineAllHeaderElements
     *
     * @since SAAJ 1.2
     */
    public Iterator extractAllHeaderElements();

}
