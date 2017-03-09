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
import java.io.OutputStream;
import java.io.IOException;

import java.util.Iterator;

import javax.activation.DataHandler;

/**
 * The root class for all SOAP messages. As transmitted on the "wire", a SOAP
 * message is an XML document or a MIME message whose first body part is an
 * XML/SOAP document.
 * <P>
 * A <code>SOAPMessage</code> object consists of a SOAP part and optionally
 * one or more attachment parts. The SOAP part for a <code>SOAPMessage</code>
 * object is a <code>SOAPPart</code> object, which contains information used
 * for message routing and identification, and which can contain
 * application-specific content. All data in the SOAP Part of a message must be
 * in XML format.
 * <P>
 * A new <code>SOAPMessage</code> object contains the following by default:
 * <UL>
 *   <LI>A <code>SOAPPart</code> object
 *   <LI>A <code>SOAPEnvelope</code> object
 *   <LI>A <code>SOAPBody</code> object
 *   <LI>A <code>SOAPHeader</code> object
 * </UL>
 * The SOAP part of a message can be retrieved by calling the method <code>SOAPMessage.getSOAPPart()</code>.
 * The <code>SOAPEnvelope</code> object is retrieved from the <code>SOAPPart</code>
 * object, and the <code>SOAPEnvelope</code> object is used to retrieve the
 * <code>SOAPBody</code> and <code>SOAPHeader</code> objects.
 *
 * <PRE>
 *     SOAPPart sp = message.getSOAPPart();
 *     SOAPEnvelope se = sp.getEnvelope();
 *     SOAPBody sb = se.getBody();
 *     SOAPHeader sh = se.getHeader();
 * </PRE>
 *
 * <P>
 * In addition to the mandatory <code>SOAPPart</code> object, a <code>SOAPMessage</code>
 * object may contain zero or more <code>AttachmentPart</code> objects, each
 * of which contains application-specific data. The <code>SOAPMessage</code>
 * interface provides methods for creating <code>AttachmentPart</code>
 * objects and also for adding them to a <code>SOAPMessage</code> object. A
 * party that has received a <code>SOAPMessage</code> object can examine its
 * contents by retrieving individual attachment parts.
 * <P>
 * Unlike the rest of a SOAP message, an attachment is not required to be in
 * XML format and can therefore be anything from simple text to an image file.
 * Consequently, any message content that is not in XML format must be in an
 * <code>AttachmentPart</code> object.
 * <P>
 * A <code>MessageFactory</code> object may create <code>SOAPMessage</code>
 * objects with behavior that is specialized to a particular implementation or
 * application of SAAJ. For instance, a <code>MessageFactory</code> object
 * may produce <code>SOAPMessage</code> objects that conform to a particular
 * Profile such as ebXML. In this case a <code>MessageFactory</code> object
 * might produce <code>SOAPMessage</code> objects that are initialized with
 * ebXML headers.
 * <P>
 * In order to ensure backward source compatibility, methods that are added to
 * this class after version 1.1 of the SAAJ specification are all concrete
 * instead of abstract and they all have default implementations. Unless
 * otherwise noted in the JavaDocs for those methods the default
 * implementations simply throw an <code>UnsupportedOperationException</code>
 * and the SAAJ implementation code must override them with methods that
 * provide the specified behavior. Legacy client code does not have this
 * restriction, however, so long as there is no claim made that it conforms to
 * some later version of the specification than it was originally written for.
 * A legacy class that extends the SOAPMessage class can be compiled and/or run
 * against succeeding versions of the SAAJ API without modification. If such a
 * class was correctly implemented then it will continue to behave correctly
 * relative to the version of the specification against which it was written.
 *
 * <p>
 *  所有SOAP消息的根类。如在"wire"上传输的,SOAP消息是XML文档或MIME消息,其第一主体部分是XML / SOAP文档。
 * <P>
 *  <code> SOAPMessage </code>对象由SOAP部分和可选的一个或多个附件部分组成。
 * 用于<code> SOAPMessage </code>对象的SOAP部分是一个<code> SOAPPart </code>对象,它包含用于消息路由和标识的信息,并且可以包含特定于应用程序的内容。
 * 消息的SOAP部分中的所有数据必须为XML格式。
 * <P>
 *  默认情况下,新的<code> SOAPMessage </code>对象包含以下内容：
 * <UL>
 *  <LI> A <code> SOAPPart </code>对象<LI> A <code> SOAPEnvelope </code>对象</li> A <code> SOAPBody </code> 
 * >对象。
 * </UL>
 *  可以通过调用<code> SOAPMessage.getSOAPPart()</code>方法检索消息的SOAP部分。
 * 从<code> SOAPPart </code>对象检索<code> SOAPEnvelope </code>对象,<code> SOAPEnvelope </code>对象用于检索<code> SOA
 * PBody </code>代码> SOAPHeader </code>对象。
 *  可以通过调用<code> SOAPMessage.getSOAPPart()</code>方法检索消息的SOAP部分。
 * 
 * <PRE>
 *  SOAPPart sp = message.getSOAPPart(); SOAPEnvelope se = sp.getEnvelope(); SOAPBody sb = se.getBody();
 *  SOAPHeader sh = se.getHeader();。
 * </PRE>
 * 
 * <P>
 * 除了强制的<code> SOAPPart </code>对象,<code> SOAPMessage </code>对象可以包含零个或多个<code> AttachmentPart </code>对象,每
 * 个对象包含特定于应用程序的数据。
 *  <code> SOAPMessage </code>接口提供了创建<code> AttachmentPart </code>对象以及将它们添加到<code> SOAPMessage </code>对象
 * 的方法。
 * 接收到<code> SOAPMessage </code>对象的方可以通过检索单个附件部分来检查其内容。
 * <P>
 *  与SOAP消息的其余部分不同,附件不需要是XML格式,因此可以是从简单文本到图像文件的任何内容。
 * 因此,任何不是XML格式的消息内容必须位于<code> AttachmentPart </code>对象中。
 * <P>
 *  <code> MessageFactory </code>对象可以创建具有专用于SAAJ的特定实现或应用的行为的<code> SOAPMessage </code>对象。
 * 例如,<code> MessageFactory </code>对象可以产生符合特定简档(例如ebXML)的<code> SOAPMessage </code>对象。
 * 在这种情况下,一个<code> MessageFactory </code>对象可以产生用ebXML头部初始化的<code> SOAPMessage </code>对象。
 * <P>
 * 为了确保向后源兼容性,在SAAJ规范的1.1版之后添加到此类的方法都是具体的而不是抽象的,并且它们都具有默认实现。
 * 除非在JavaDocs中为这些方法另有说明,默认实现只是抛出一个<code> UnsupportedOperationException </code>,SAAJ实现代码必须用提供指定行为的方法覆盖它们
 * 。
 * 为了确保向后源兼容性,在SAAJ规范的1.1版之后添加到此类的方法都是具体的而不是抽象的,并且它们都具有默认实现。
 * 然而,旧客户端代码没有此限制,只要没有声明它符合规范的某些较新版本,而不是它最初编写的。扩展SOAPMessage类的传统类可以在不修改的情况下针对后续版本的SAAJ API进行编译和/或运行。
 * 如果这样的类被正确实现,那么它将继续相对于其被写入的规范的版本正确地行为。
 * 
 * 
 * @see MessageFactory
 * @see AttachmentPart
 */
public abstract class SOAPMessage {
    /**
         * Specifies the character type encoding for the SOAP Message. Valid values
         * include "utf-8" and "utf-16". See vendor documentation for additional
         * supported values. The default is "utf-8".
         *
         * <p>
         *  指定SOAP消息的字符类型编码。有效值包括"utf-8"和"utf-16"。有关其他受支持的值,默认值为"utf-8"。
         * 
         * 
         * @see SOAPMessage#setProperty(String, Object) SOAPMessage.setProperty
         * @since SAAJ 1.2
         */
    public static final String CHARACTER_SET_ENCODING =
        "javax.xml.soap.character-set-encoding";

    /**
     * Specifies whether the SOAP Message will contain an XML declaration when
     * it is sent. The only valid values are "true" and "false". The default is
     * "false".
     *
     * <p>
     *  指定SOAP消息在发送时是否包含XML声明。唯一有效的值是"true"和"false"。默认值为"false"。
     * 
     * 
     * @see SOAPMessage#setProperty(String, Object) SOAPMessage.setProperty
     * @since SAAJ 1.2
     */
    public static final String WRITE_XML_DECLARATION =
        "javax.xml.soap.write-xml-declaration";

    /**
     * Sets the description of this <code>SOAPMessage</code> object's
     * content with the given description.
     *
     * <p>
     *  使用给定的描述设置此<<code> SOAPMessage </code>对象的内容的描述。
     * 
     * 
     * @param description a <code>String</code> describing the content of this
     *         message
     * @see #getContentDescription
     */
    public abstract void setContentDescription(String description);

    /**
     * Retrieves a description of this <code>SOAPMessage</code> object's
     * content.
     *
     * <p>
     *  检索此<code> SOAPMessage </code>对象的内容的描述。
     * 
     * 
     * @return a <code>String</code> describing the content of this
     *         message or <code>null</code> if no description has been set
     * @see #setContentDescription
     */
    public abstract String getContentDescription();

    /**
         * Gets the SOAP part of this <code>SOAPMessage</code> object.
         * <P>
         * <code>SOAPMessage</code> object contains one or more attachments, the
         * SOAP Part must be the first MIME body part in the message.
         *
         * <p>
         * 获取此<code> SOAPMessage </code>对象的SOAP部分。
         * <P>
         *  <code> SOAPMessage </code>对象包含一个或多个附件,SOAP部分必须是消息中的第一个MIME主体部分。
         * 
         * 
         * @return the <code>SOAPPart</code> object for this <code>SOAPMessage</code>
         *         object
         */
    public abstract SOAPPart getSOAPPart();

    /**
         * Gets the SOAP Body contained in this <code>SOAPMessage</code> object.
         * <p>
         *
         * <p>
         *  获取此<code> SOAPMessage </code>对象中包含的SOAP主体。
         * <p>
         * 
         * 
         * @return the <code>SOAPBody</code> object contained by this <code>SOAPMessage</code>
         *         object
         * @exception SOAPException
         *               if the SOAP Body does not exist or cannot be retrieved
         * @since SAAJ 1.2
         */
    public SOAPBody getSOAPBody() throws SOAPException {
        throw new UnsupportedOperationException("getSOAPBody must be overridden by all subclasses of SOAPMessage");
    }

    /**
         * Gets the SOAP Header contained in this <code>SOAPMessage</code>
         * object.
         * <p>
         *
         * <p>
         *  获取此<code> SOAPMessage </code>对象中包含的SOAP标头。
         * <p>
         * 
         * 
         * @return the <code>SOAPHeader</code> object contained by this <code>SOAPMessage</code>
         *         object
         * @exception SOAPException
         *               if the SOAP Header does not exist or cannot be retrieved
         * @since SAAJ 1.2
         */
    public SOAPHeader getSOAPHeader() throws SOAPException {
        throw new UnsupportedOperationException("getSOAPHeader must be overridden by all subclasses of SOAPMessage");
    }

    /**
         * Removes all <code>AttachmentPart</code> objects that have been added
         * to this <code>SOAPMessage</code> object.
         * <P>
         * This method does not touch the SOAP part.
         * <p>
         *  删除已添加到此<code> SOAPMessage </code>对象的所有<code> AttachmentPart </code>对象。
         * <P>
         *  此方法不接触SOAP部分。
         * 
         */
    public abstract void removeAllAttachments();

    /**
         * Gets a count of the number of attachments in this message. This count
         * does not include the SOAP part.
         *
         * <p>
         *  获取此邮件中附件数的计数。此计数不包括SOAP部分。
         * 
         * 
         * @return the number of <code>AttachmentPart</code> objects that are
         *         part of this <code>SOAPMessage</code> object
         */
    public abstract int countAttachments();

    /**
         * Retrieves all the <code>AttachmentPart</code> objects that are part of
         * this <code>SOAPMessage</code> object.
         *
         * <p>
         *  检索属于此<code> SOAPMessage </code>对象一部分的所有<code> AttachmentPart </code>对象。
         * 
         * 
         * @return an iterator over all the attachments in this message
         */
    public abstract Iterator getAttachments();

    /**
         * Retrieves all the <code>AttachmentPart</code> objects that have header
         * entries that match the specified headers. Note that a returned
         * attachment could have headers in addition to those specified.
         *
         * <p>
         *  检索具有与指定标头匹配的标头条目的所有<code> AttachmentPart </code>对象。请注意,返回的附件除了指定的附件之外还可以包含标头。
         * 
         * 
         * @param headers
         *           a <code>MimeHeaders</code> object containing the MIME
         *           headers for which to search
         * @return an iterator over all attachments that have a header that matches
         *         one of the given headers
         */
    public abstract Iterator getAttachments(MimeHeaders headers);

    /**
     * Removes all the <code>AttachmentPart</code> objects that have header
     * entries that match the specified headers. Note that the removed
     * attachment could have headers in addition to those specified.
     *
     * <p>
     *  删除具有与指定标头匹配的标头条目的所有<code> AttachmentPart </code>对象。请注意,除了指定的附件之外,删除的附件还可以包含标题。
     * 
     * 
     * @param headers
     *           a <code>MimeHeaders</code> object containing the MIME
     *           headers for which to search
     * @since SAAJ 1.3
     */
    public abstract void removeAttachments(MimeHeaders headers);


    /**
     * Returns an <code>AttachmentPart</code> object that is associated with an
     * attachment that is referenced by this <code>SOAPElement</code> or
     * <code>null</code> if no such attachment exists. References can be made
     * via an <code>href</code> attribute as described in
     * {@link <a href="http://www.w3.org/TR/SOAP-attachments#SOAPReferenceToAttachements">SOAP Messages with Attachments</a>},
     * or via a single <code>Text</code> child node containing a URI as
     * described in the WS-I Attachments Profile 1.0 for elements of schema
     * type {@link <a href="http://www.ws-i.org/Profiles/AttachmentsProfile-1.0-2004-08-24.html">ref:swaRef</a>}.  These two mechanisms must be supported.
     * The support for references via <code>href</code> attribute also implies that
     * this method should also be supported on an element that is an
     * <i>xop:Include</i> element (
     * {@link <a  href="http://www.w3.org/2000/xp/Group/3/06/Attachments/XOP.html">XOP</a>}).
     * other reference mechanisms may be supported by individual
     * implementations of this standard. Contact your vendor for details.
     *
     * <p>
     * 如果没有此类附件存在,则返回与此<code> SOAPElement </code>或<code> null </code>引用的附件关联的<code> AttachmentPart </code>对象
     * 。
     * 可以通过<code> href </code>属性进行引用,如{@link <a href="http://www.w3.org/TR/SOAP-attachments#SOAPReferenceToAttachements"> SOAP消息附件< / a>}
     * ,或通过包含URI的单个<code> Text </code>子节点,如WS-I Attachment Profile 1.0中所述的模式类型{@link <a href ="http：// www .ws-i.org / Profiles / AttachmentsProfile-1.0-2004-08-24.html"> ref：swaRef </a>}
     * 。
     * 这两个机制必须得到支持。
     * 通过<code> href </code>属性支持引用也意味着这个方法也应该支持一个<i> xop：Include </i>元素({@link <a href ="http ：//www.w3.org/2000/xp/Group/3/06/Attachments/XOP.html"> XOP </a>}
     * )。
     * 这两个机制必须得到支持。但是本标准的各个实现可以支持其他参考机制。有关详细信息,请与供应商联系。
     * 
     * 
     * @param  element The <code>SOAPElement</code> containing the reference to an Attachment
     * @return the referenced <code>AttachmentPart</code> or null if no such
     *          <code>AttachmentPart</code> exists or no reference can be
     *          found in this <code>SOAPElement</code>.
     * @throws SOAPException if there is an error in the attempt to access the
     *          attachment
     *
     * @since SAAJ 1.3
     */
    public abstract AttachmentPart getAttachment(SOAPElement element) throws SOAPException;


    /**
     * Adds the given <code>AttachmentPart</code> object to this <code>SOAPMessage</code>
     * object. An <code>AttachmentPart</code> object must be created before
     * it can be added to a message.
     *
     * <p>
     *  将给定的<code> AttachmentPart </code>对象添加到此<code> SOAPMessage </code>对象。
     * 必须先创建一个<code> AttachmentPart </code>对象,然后才能将其添加到消息中。
     * 
     * 
     * @param AttachmentPart
     *           an <code>AttachmentPart</code> object that is to become part
     *           of this <code>SOAPMessage</code> object
     * @exception IllegalArgumentException
     */
    public abstract void addAttachmentPart(AttachmentPart AttachmentPart);

    /**
     * Creates a new empty <code>AttachmentPart</code> object. Note that the
     * method <code>addAttachmentPart</code> must be called with this new
     * <code>AttachmentPart</code> object as the parameter in order for it to
     * become an attachment to this <code>SOAPMessage</code> object.
     *
     * <p>
     *  创建一个新的空的<code> AttachmentPart </code>对象。
     * 请注意,必须使用此新的<code> AttachmentPart </code>对象作为参数调用<code> addAttachmentPart </code>方法,以使其成为此<code> SOAPM
     * essage </code>对象的附件。
     *  创建一个新的空的<code> AttachmentPart </code>对象。
     * 
     * 
     * @return a new <code>AttachmentPart</code> object that can be populated
     *         and added to this <code>SOAPMessage</code> object
     */
    public abstract AttachmentPart createAttachmentPart();

    /**
     * Creates an <code>AttachmentPart</code> object and populates it using
     * the given <code>DataHandler</code> object.
     *
     * <p>
     * 创建一个<code> AttachmentPart </code>对象,并使用给定的<code> DataHandler </code>对象填充它。
     * 
     * 
     * @param dataHandler
     *           the <code>javax.activation.DataHandler</code> object that
     *           will generate the content for this <code>SOAPMessage</code>
     *           object
     * @return a new <code>AttachmentPart</code> object that contains data
     *         generated by the given <code>DataHandler</code> object
     * @exception IllegalArgumentException
     *               if there was a problem with the specified <code>DataHandler</code>
     *               object
     * @see javax.activation.DataHandler
     * @see javax.activation.DataContentHandler
     */
    public AttachmentPart createAttachmentPart(DataHandler dataHandler) {
        AttachmentPart attachment = createAttachmentPart();
        attachment.setDataHandler(dataHandler);
        return attachment;
    }

    /**
     * Returns all the transport-specific MIME headers for this <code>SOAPMessage</code>
     * object in a transport-independent fashion.
     *
     * <p>
     *  以传输独立方式返回此<code> SOAPMessage </code>对象的所有传输专用MIME头。
     * 
     * 
     * @return a <code>MimeHeaders</code> object containing the <code>MimeHeader</code>
     *         objects
     */
    public abstract MimeHeaders getMimeHeaders();

    /**
     * Creates an <code>AttachmentPart</code> object and populates it with
     * the specified data of the specified content type. The type of the
     * <code>Object</code> should correspond to the value given for the
     * <code>Content-Type</code>.
     *
     * <p>
     *  创建<code> AttachmentPart </code>对象,并使用指定内容类型的指定数据填充该对象。
     *  <code> Object </code>的类型应该对应于<code> Content-Type </code>给定的值。
     * 
     * 
     * @param content
     *           an <code>Object</code> containing the content for the
     *           <code>AttachmentPart</code> object to be created
     * @param contentType
     *           a <code>String</code> object giving the type of content;
     *           examples are "text/xml", "text/plain", and "image/jpeg"
     * @return a new <code>AttachmentPart</code> object that contains the
     *         given data
     * @exception IllegalArgumentException
     *               may be thrown if the contentType does not match the type
     *               of the content object, or if there was no
     *               <code>DataContentHandler</code> object for the given
     *               content object
     * @see javax.activation.DataHandler
     * @see javax.activation.DataContentHandler
     */
    public AttachmentPart createAttachmentPart(
        Object content,
        String contentType) {
        AttachmentPart attachment = createAttachmentPart();
        attachment.setContent(content, contentType);
        return attachment;
    }

    /**
     * Updates this <code>SOAPMessage</code> object with all the changes that
     * have been made to it. This method is called automatically when
     * {@link SOAPMessage#writeTo(OutputStream)} is  called. However, if
     * changes are made to a message that was received or to one that has
     * already been sent, the method <code>saveChanges</code> needs to be
     * called explicitly in order to save the changes. The method <code>saveChanges</code>
     * also generates any changes that can be read back (for example, a
     * MessageId in profiles that support a message id). All MIME headers in a
     * message that is created for sending purposes are guaranteed to have
     * valid values only after <code>saveChanges</code> has been called.
     * <P>
     * In addition, this method marks the point at which the data from all
     * constituent <code>AttachmentPart</code> objects are pulled into the
     * message.
     * <P>
     *
     * <p>
     *  更新此<code> SOAPMessage </code>对象及其所做的所有更改。当调用{@link SOAPMessage#writeTo(OutputStream)}时,将自动调用此方法。
     * 但是,如果对已接收的消息或已发送的消息进行更改,则需要显式调用<code> saveChanges </code>方法以保存更改。
     * 方法<code> saveChanges </code>还生成可以读回的任何更改(例如,在支持消息标识的概要文件中的MessageId)。
     * 为发送目的而创建的消息中的所有MIME头都必须在调用<code> saveChanges </code>之后才具有有效值。
     * <P>
     *  此外,该方法标记来自所有组成<code> AttachmentPart </code>对象的数据被拉入消息的点。
     * <P>
     * 
     * 
     * @exception <code>SOAPException</code> if there was a problem saving
     *               changes to this message.
     */
    public abstract void saveChanges() throws SOAPException;

    /**
     * Indicates whether this <code>SOAPMessage</code> object needs to have
     * the method <code>saveChanges</code> called on it.
     *
     * <p>
     *  指示此<> SOAPMessage </code>对象是否需要调用方法<code> saveChanges </code>。
     * 
     * 
     * @return <code>true</code> if <code>saveChanges</code> needs to be
     *         called; <code>false</code> otherwise.
     */
    public abstract boolean saveRequired();

    /**
     * Writes this <code>SOAPMessage</code> object to the given output
     * stream. The externalization format is as defined by the SOAP 1.1 with
     * Attachments specification.
     * <P>
     * If there are no attachments, just an XML stream is written out. For
     * those messages that have attachments, <code>writeTo</code> writes a
     * MIME-encoded byte stream.
     * <P>
     * Note that this method does not write the transport-specific MIME Headers
     * of the Message
     *
     * <p>
     * 将此<code> SOAPMessage </code>对象写入给定的输出流。外部化格式由具有附件规范的SOAP 1.1定义。
     * <P>
     *  如果没有附件,只会写出一个XML流。对于那些有附件的邮件,<code> writeTo </code>写入一个MIME编码的字节流。
     * <P>
     *  请注意,此方法不会写入消息的特定于传输的MIME标头
     * 
     * 
     * @param out
     *           the <code>OutputStream</code> object to which this <code>SOAPMessage</code>
     *           object will be written
     * @exception IOException
     *               if an I/O error occurs
     * @exception SOAPException
     *               if there was a problem in externalizing this SOAP message
     */
    public abstract void writeTo(OutputStream out)
        throws SOAPException, IOException;

    /**
     * Associates the specified value with the specified property. If there was
     * already a value associated with this property, the old value is
     * replaced.
     * <p>
     * The valid property names include
     * {@link SOAPMessage#WRITE_XML_DECLARATION}  and
     * {@link SOAPMessage#CHARACTER_SET_ENCODING}. All of these standard SAAJ
     * properties are prefixed by "javax.xml.soap". Vendors may also add
     * implementation specific properties. These properties must be prefixed
     * with package names that are unique to the vendor.
     * <p>
     * Setting the property <code>WRITE_XML_DECLARATION</code> to <code>"true"</code>
     * will cause an XML Declaration to be written out at the start of the SOAP
     * message. The default value of "false" suppresses this declaration.
     * <p>
     * The property <code>CHARACTER_SET_ENCODING</code> defaults to the value
     * <code>"utf-8"</code> which causes the SOAP message to be encoded using
     * UTF-8. Setting <code>CHARACTER_SET_ENCODING</code> to <code>"utf-16"</code>
     * causes the SOAP message to be encoded using UTF-16.
     * <p>
     * Some implementations may allow encodings in addition to UTF-8 and
     * UTF-16. Refer to your vendor's documentation for details.
     *
     * <p>
     *  将指定的值与指定的属性关联。如果已有与此属性关联的值,则替换旧值。
     * <p>
     *  有效的属性名称包括{@link SOAPMessage#WRITE_XML_DECLARATION}和{@link SOAPMessage#CHARACTER_SET_ENCODING}。
     * 所有这些标准SAAJ属性都以"javax.xml.soap"作为前缀。供应商还可以添加实现特定的属性。这些属性必须以供应商特有的软件包名称作为前缀。
     * <p>
     *  将属性<code> WRITE_XML_DECLARATION </code>设置为<code>"true"</code>会导致在SOAP消息开始时写出XML声明。默认值"false"禁止此声明。
     * <p>
     *  属性<code> CHARACTER_SET_ENCODING </code>的默认值为<code>"utf-8"</code>,这会导致SOAP消息使用UTF-8编码。
     * 
     * @param property
     *           the property with which the specified value is to be
     *           associated.
     * @param value
     *           the value to be associated with the specified property
     * @exception SOAPException
     *               if the property name is not recognized.
     * @since SAAJ 1.2
     */
    public void setProperty(String property, Object value)
        throws SOAPException {
            throw new UnsupportedOperationException("setProperty must be overridden by all subclasses of SOAPMessage");
    }

    /**
     * Retrieves value of the specified property.
     *
     * <p>
     * 将<code> CHARACTER_SET_ENCODING </code>设置为<code>"utf-16"</code>会导致使用UTF-16编码SOAP消息。
     * <p>
     * 一些实现可以允许除了UTF-8和UTF-16之外的编码。有关详细信息,请参阅供应商文档。
     * 
     * 
     * @param property
     *           the name of the property to retrieve
     * @return the value associated with the named property or <code>null</code>
     *         if no such property exists.
     * @exception SOAPException
     *               if the property name is not recognized.
     * @since SAAJ 1.2
     */
    public Object getProperty(String property) throws SOAPException {
        throw new UnsupportedOperationException("getProperty must be overridden by all subclasses of SOAPMessage");
    }
}
