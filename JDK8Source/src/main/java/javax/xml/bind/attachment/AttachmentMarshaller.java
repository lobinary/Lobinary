/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.attachment;

import javax.activation.DataHandler;
import javax.xml.bind.Marshaller;

/**
 * <p>Enable JAXB marshalling to optimize storage of binary data.</p>
 *
 * <p>This API enables an efficient cooperative creation of optimized
 * binary data formats between a JAXB marshalling process and a MIME-based package
 * processor. A JAXB implementation marshals the root body of a MIME-based package,
 * delegating the creation of referenceable MIME parts to
 * the MIME-based package processor that implements this abstraction.</p>
 *
 * <p>XOP processing is enabled when {@link #isXOPPackage()} is true.
 *    See {@link #addMtomAttachment(DataHandler, String, String)} for details.
 * </p>
 *
 * <p>WS-I Attachment Profile 1.0 is supported by
 * {@link #addSwaRefAttachment(DataHandler)} being called by the
 * marshaller for each JAXB property related to
 * {http://ws-i.org/profiles/basic/1.1/xsd}swaRef.</p>
 *
 *
 * <p>
 *  <p>启用JAXB编组以优化二进制数据的存储。</p>
 * 
 *  <p>此API支持在JAXB编组过程和基于MIME的包处理器之间高效协作地创建优化的二进制数据格式。
 *  JAXB实现调度基于MIME的包的根体,将可引用的MIME部分的创建委托给实现此抽象的基于MIME的包处理器。</p>。
 * 
 *  <p>当{@link #isXOPPackage()}为true时,将启用XOP处理。
 * 有关详情,请参见{@link #addMtomAttachment(DataHandler,String,String)}。
 * </p>
 * 
 *  <p>由编程者为与{http://ws-i.org/profiles/basic/1.1/xsd相关的每个JAXB属性调用的{@link #addSwaRefAttachment(DataHandler)}
 * 支持WS-I Attachment Profile 1.0 } swaRef。
 * </p>。
 * 
 * 
 * @author Marc Hadley
 * @author Kohsuke Kawaguchi
 * @author Joseph Fialli
 * @since JAXB 2.0
 *
 * @see Marshaller#setAttachmentMarshaller(AttachmentMarshaller)
 *
 * @see <a href="http://www.w3.org/TR/2005/REC-xop10-20050125/">XML-binary Optimized Packaging</a>
 * @see <a href="http://www.ws-i.org/Profiles/AttachmentsProfile-1.0-2004-08-24.html">WS-I Attachments Profile Version 1.0.</a>
 */
public abstract class AttachmentMarshaller {

    /**
     * <p>Consider MIME content <code>data</code> for optimized binary storage as an attachment.
     *
     * <p>
     * This method is called by JAXB marshal process when {@link #isXOPPackage()} is
     * <code>true</code>, for each element whose datatype is "base64Binary", as described in
     * Step 3 in
     * <a href="http://www.w3.org/TR/2005/REC-xop10-20050125/#creating_xop_packages">Creating XOP Packages</a>.
     *
     * <p>
     * The method implementor determines whether <code>data</code> shall be attached separately
     * or inlined as base64Binary data. If the implementation chooses to optimize the storage
     * of the binary data as a MIME part, it is responsible for attaching <code>data</code> to the
     * MIME-based package, and then assigning an unique content-id, cid, that identifies
     * the MIME part within the MIME message. This method returns the cid,
     * which enables the JAXB marshaller to marshal a XOP element that refers to that cid in place
     * of marshalling the binary data. When the method returns null, the JAXB marshaller
     * inlines <code>data</code> as base64binary data.
     *
     * <p>
     * The caller of this method is required to meet the following constraint.
     * If the element infoset item containing <code>data</code> has the attribute
     * <code>xmime:contentType</code> or if the JAXB property/field representing
     * <code>data</code>is annotated with a known MIME type,
     * <code>data.getContentType()</code> should be set to that MIME type.
     *
     * <p>
     * The <code>elementNamespace</code> and <code>elementLocalName</code>
     * parameters provide the
     * context that contains the binary data. This information could
     * be used by the MIME-based package processor to determine if the
     * binary data should be inlined or optimized as an attachment.
     *
     * <p>
     *  <p>考虑MIME内容<code> data </code>,将优化的二进制存储作为附件。
     * 
     * <p>
     *  对于数据类型为"base64Binary"的每个元素,{@link #isXOPPackage()}为<code> true </code>时,此方法由JAXB marshal进程调用,如<a href = //www.w3.org/TR/2005/REC-xop10-20050125/#creating_xop_packages">
     * 创建XOP套件</a>。
     * 
     * <p>
     * 方法实现者确定<code> data </code>是单独附加还是作为base64Binary数据内联。
     * 如果实现选择优化二进制数据作为MIME部分的存储,则它负责将<code> data </code>附加到基于MIME的包,然后分配唯一的content-id cid, MIME消息中的MIME部分。
     * 此方法返回cid,它使JAXB编组程序能够编组引用该cid的XOP元素,而不是编组二进制数据。
     * 当方法返回null时,JAXB marshaller将<code> data </code>嵌入为base64二进制数据。
     * 
     * <p>
     *  此方法的调用者需要满足以下约束。
     * 如果包含<code> data </code>的元素信息集项具有属性<code> xmime：contentType </code>或者如果表示<code> data </code>的JAXB属性/字段
     * 用已知MIME类型,<code> data.getContentType()</code>应设置为该MIME类型。
     *  此方法的调用者需要满足以下约束。
     * 
     * <p>
     *  <code> elementNamespace </code>和<code> elementLocalName </code>参数提供包含二进制数据的上下文。
     * 该信息可以由基于MIME的包处理器使用以确定二进制数据是否应当作为附件内联或优化。
     * 
     * 
     * @param data
     *       represents the data to be attached. Must be non-null.
     * @param elementNamespace
     *      the namespace URI of the element that encloses the base64Binary data.
     *      Can be empty but never null.
     * @param elementLocalName
     *      The local name of the element. Always a non-null valid string.
     *
     * @return
     *     a valid content-id URI (see <a href="http://www.w3.org/TR/xop10/#RFC2387">RFC 2387</a>) that identifies the attachment containing <code>data</code>.
     *     Otherwise, null if the attachment was not added and should instead be inlined in the message.
     *
     * @see <a href="http://www.w3.org/TR/2005/REC-xop10-20050125/">XML-binary Optimized Packaging</a>
     * @see <a href="http://www.w3.org/TR/xml-media-types/">Describing Media Content of Binary Data in XML</a>
     */
    public abstract String addMtomAttachment(DataHandler data, String elementNamespace, String elementLocalName);

    /**
     * <p>Consider binary <code>data</code> for optimized binary storage as an attachment.
     *
     * <p>Since content type is not known, the attachment's MIME content type must be set to "application/octet-stream".</p>
     *
     * <p>
     * The <code>elementNamespace</code> and <code>elementLocalName</code>
     * parameters provide the
     * context that contains the binary data. This information could
     * be used by the MIME-based package processor to determine if the
     * binary data should be inlined or optimized as an attachment.
     *
     * <p>
     *  <p>将二进制<code>数据</code>考虑为优化的二进制存储作为附件。
     * 
     *  <p>由于内容类型未知,附件的MIME内容类型必须设置为"application / octet-stream"。</p>
     * 
     * <p>
     * <code> elementNamespace </code>和<code> elementLocalName </code>参数提供包含二进制数据的上下文。
     * 该信息可以由基于MIME的包处理器使用以确定二进制数据是否应当作为附件内联或优化。
     * 
     * 
     * @param data
     *      represents the data to be attached. Must be non-null. The actual data region is
     *      specified by <tt>(data,offset,length)</tt> tuple.
     *
     * @param offset
     *       The offset within the array of the first byte to be read;
     *       must be non-negative and no larger than array.length
     *
     * @param length
     *       The number of bytes to be read from the given array;
     *       must be non-negative and no larger than array.length
     *
     * @param mimeType
     *      If the data has an associated MIME type known to JAXB, that is passed
     *      as this parameter. If none is known, "application/octet-stream".
     *      This parameter may never be null.
     *
     * @param elementNamespace
     *      the namespace URI of the element that encloses the base64Binary data.
     *      Can be empty but never null.
     *
     * @param elementLocalName
     *      The local name of the element. Always a non-null valid string.
     *
     * @return content-id URI, cid, to the attachment containing
     *         <code>data</code> or null if data should be inlined.
     *
     * @see #addMtomAttachment(DataHandler, String, String)
     */
    public abstract String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNamespace, String elementLocalName);

    /**
     * <p>Read-only property that returns true if JAXB marshaller should enable XOP creation.</p>
     *
     * <p>This value must not change during the marshalling process. When this
     * value is true, the <code>addMtomAttachment(...)</code> method
     * is invoked when the appropriate binary datatypes are encountered by
     * the marshal process.</p>
     *
     * <p>Marshaller.marshal() must throw IllegalStateException if this value is <code>true</code>
     * and the XML content to be marshalled violates Step 1 in
     * <a href="http://www.w3.org/TR/2005/REC-xop10-20050125/#creating_xop_packages">Creating XOP Pacakges</a>
     * http://www.w3.org/TR/2005/REC-xop10-20050125/#creating_xop_packages.
     * <i>"Ensure the Original XML Infoset contains no element information item with a
     * [namespace name] of "http://www.w3.org/2004/08/xop/include" and a [local name] of Include"</i>
     *
     * <p>When this method returns true and during the marshal process
     * at least one call to <code>addMtomAttachment(...)</code> returns
     * a content-id, the MIME-based package processor must label the
     * root part with the application/xop+xml media type as described in
     * Step 5 of
     * <a href="http://www.w3.org/TR/2005/REC-xop10-20050125/#creating_xop_packages">Creating XOP Pacakges</a>.<p>
     *
     * <p>
     *  <p>只读属性,如果JAXB marshaller应启用XOP创建,则返回true。</p>
     * 
     *  <p>此值在编组过程中不能更改。当此值为true时,当marshal进程遇到相应的二进制数据类型时,调用<code> addMtomAttachment(...)</code>方法。</p>
     * 
     *  <p>如果此值为<code> true </code>,则Marshaller.marshal()必须抛出IllegalStateException,并且要编组的XML内容违反了<a href ="http://www.w3.org/TR中的步骤1 / 2005 / REC-xop10-20050125 /#creating_xop_packages">
     * 创建XOP Pacakges </a> http://www.w3.org/TR/2005/REC-xop10-20050125/#creating_xop_packages。
     *  <i>"确保原始XML信息集不包含[名称空间名称]为"http://www.w3.org/2004/08/xop/include"的元素信息项,且包含[本地名称]为Include"< / i>。
     * 
     *  <p>当此方法返回true且在编组过程中,至少一次调用<code> addMtomAttachment(...)</code>返回一个content-id时,基于MIME的包处理器必须使用applic
     * ation / xop + xml媒体类型,如<a href="http://www.w3.org/TR/2005/REC-xop10-20050125/#creating_xop_packages">
     * 创建XOP Pacakges </a>的步骤5中所述。
     * 
     * @return true when MIME context is a XOP Package.
     */
    public boolean isXOPPackage() { return false; }

   /**
    * <p>Add MIME <code>data</code> as an attachment and return attachment's content-id, cid.</p>
    *
    * <p>
    * This method is called by JAXB marshal process for each element/attribute typed as
    * {http://ws-i.org/profiles/basic/1.1/xsd}swaRef. The MIME-based package processor
    * implementing this method is responsible for attaching the specified data to a
    * MIME attachment, and generating a content-id, cid, that uniquely identifies the attachment
    * within the MIME-based package.
    *
    * <p>Caller inserts the returned content-id, cid, into the XML content being marshalled.</p>
    *
    * <p>
    *  <p>。
    * 
    * 
    * @param data
    *       represents the data to be attached. Must be non-null.
    * @return
    *       must be a valid URI used as cid. Must satisfy Conformance Requirement R2928 from
    *       <a href="http://www.ws-i.org/Profiles/AttachmentsProfile-1.0-2004-08-24.html#Referencing_Attachments_from_the_SOAP_Envelope">WS-I Attachments Profile Version 1.0.</a>
    */
    public abstract String addSwaRefAttachment(DataHandler data);
}
