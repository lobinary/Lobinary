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

import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;

import javax.activation.DataHandler;

/**
 * A single attachment to a <code>SOAPMessage</code> object. A <code>SOAPMessage</code>
 * object may contain zero, one, or many <code>AttachmentPart</code> objects.
 * Each <code>AttachmentPart</code> object consists of two parts,
 * application-specific content and associated MIME headers. The
 * MIME headers consists of name/value pairs that can be used to
 * identify and describe the content.
 * <p>
 * An <code>AttachmentPart</code> object must conform to certain standards.
 * <OL>
 * <LI>It must conform to <a href="http://www.ietf.org/rfc/rfc2045.txt">
 *     MIME [RFC2045] standards</a>
 * <LI>It MUST contain content
 * <LI>The header portion MUST include the following header:
 *  <UL>
 *   <LI><code>Content-Type</code><br>
 *       This header identifies the type of data in the content of an
 *       <code>AttachmentPart</code> object and MUST conform to [RFC2045].
 *       The following is an example of a Content-Type header:
 *       <PRE>
 *       Content-Type:  application/xml
 *       </PRE>
 *       The following line of code, in which <code>ap</code> is an
 *       <code>AttachmentPart</code> object, sets the header shown in
 *       the previous example.
 *       <PRE>
 *       ap.setMimeHeader("Content-Type", "application/xml");
 *       </PRE>
 * <p>
 *  </UL>
 * </OL>
 * <p>
 * There are no restrictions on the content portion of an <code>
 * AttachmentPart</code> object. The content may be anything from a
 * simple plain text object to a complex XML document or image file.
 *
 * <p>
 * An <code>AttachmentPart</code> object is created with the method
 * <code>SOAPMessage.createAttachmentPart</code>. After setting its MIME headers,
 *  the <code>AttachmentPart</code> object is added to the message
 * that created it with the method <code>SOAPMessage.addAttachmentPart</code>.
 *
 * <p>
 * The following code fragment, in which <code>m</code> is a
 * <code>SOAPMessage</code> object and <code>contentStringl</code> is a
 * <code>String</code>, creates an instance of <code>AttachmentPart</code>,
 * sets the <code>AttachmentPart</code> object with some content and
 * header information, and adds the <code>AttachmentPart</code> object to
 * the <code>SOAPMessage</code> object.
 * <PRE>
 *     AttachmentPart ap1 = m.createAttachmentPart();
 *     ap1.setContent(contentString1, "text/plain");
 *     m.addAttachmentPart(ap1);
 * </PRE>
 *
 *
 * <p>
 * The following code fragment creates and adds a second
 * <code>AttachmentPart</code> instance to the same message. <code>jpegData</code>
 * is a binary byte buffer representing the jpeg file.
 * <PRE>
 *     AttachmentPart ap2 = m.createAttachmentPart();
 *     byte[] jpegData =  ...;
 *     ap2.setContent(new ByteArrayInputStream(jpegData), "image/jpeg");
 *     m.addAttachmentPart(ap2);
 * </PRE>
 * <p>
 * The <code>getContent</code> method retrieves the contents and header from
 * an <code>AttachmentPart</code> object. Depending on the
 * <code>DataContentHandler</code> objects present, the returned
 * <code>Object</code> can either be a typed Java object corresponding
 * to the MIME type or an <code>InputStream</code> object that contains the
 * content as bytes.
 * <PRE>
 *     String content1 = ap1.getContent();
 *     java.io.InputStream content2 = ap2.getContent();
 * </PRE>
 *
 * The method <code>clearContent</code> removes all the content from an
 * <code>AttachmentPart</code> object but does not affect its header information.
 * <PRE>
 *     ap1.clearContent();
 * </PRE>
 * <p>
 *  对<code> SOAPMessage </code>对象的单个附件。
 *  <code> SOAPMessage </code>对象可以包含零个,一个或多个<code> AttachmentPart </code>对象。
 * 每个<code> AttachmentPart </code>对象由两部分组成,应用程序特定的内容和相关的MIME头。 MIME标头包含可用于标识和描述内容的名称/值对。
 * <p>
 *  <code> AttachmentPart </code>对象必须符合某些标准。
 * <OL>
 *  <LI>它必须符合<a href="http://www.ietf.org/rfc/rfc2045.txt"> MIME [RFC2045]标准</a> <LI>它必须包含内容<LI>标题部分必须包括
 * 以下标题：。
 * <UL>
 *  <LI> <code> Content-Type </code> <br>此标头标识<code> AttachmentPart </code>对象的内容中的数据类型,并且必须符合[RFC2045]。
 * 以下是Content-Type头的示例：。
 * <PRE>
 *  Content-Type：application / xml
 * </PRE>
 *  下面的代码行,其中<code> ap </code>是一个<code> AttachmentPart </code>对象,设置上一个示例中显示的标题。
 * <PRE>
 *  ap.setMimeHeader("Content-Type","application / xml");
 * </PRE>
 * <p>
 * </UL>
 * </OL>
 * <p>
 *  对<code> AttachmentPart </code>对象的内容部分没有限制。内容可以是从简单纯文本对象到复杂XML文档或图像文件的任何内容。
 * 
 * <p>
 * 使用方法<code> SOAPMessage.createAttachmentPart </code>创建<code> AttachmentPart </code>对象。
 * 设置其MIME头后,将<code> AttachmentPart </code>对象添加到使用方法<code> SOAPMessage.addAttachmentPart </code>创建它的消息。
 * 
 * <p>
 *  以下代码片段中<code> m </code>是<code> SOAPMessage </code>对象,<code> contentStringl </code>是<code> String </code>
 *  <code> AttachmentPart </code>,设置具有一些内容和头信息的<code> AttachmentPart </code>对象,并将<code> AttachmentPart </code>
 * 对象添加到<code> SOAPMessage </code>对象。
 * <PRE>
 *  AttachmentPart ap1 = m.createAttachmentPart(); ap1.setContent(contentString1,"text / plain"); m.addA
 * ttachmentPart(ap1);。
 * </PRE>
 * 
 * <p>
 *  以下代码片段创建并向同一消息添加第二个<code> AttachmentPart </code>实例。 <code> jpegData </code>是一个二进制字节缓冲区,表示jpeg文件。
 * <PRE>
 *  AttachmentPart ap2 = m.createAttachmentPart(); byte [] jpegData = ...; ap2.setContent(new ByteArrayI
 * nputStream(jpegData),"image / jpeg"); m.addAttachmentPart(ap2);。
 * </PRE>
 * <p>
 *  <code> getContent </code>方法从<code> AttachmentPart </code>对象中检索内容和头。
 * 根据存在的<code> DataContentHandler </code>对象,返回的<code> Object </code>可以是与MIME类型相对应的类型化的Java对象,也可以是一个<code>
 *  InputStream </code>内容为字节。
 *  <code> getContent </code>方法从<code> AttachmentPart </code>对象中检索内容和头。
 * <PRE>
 * String content1 = ap1.getContent(); java.io.InputStream content2 = ap2.getContent();
 * </PRE>
 * 
 *  方法<code> clearContent </code>从<code> AttachmentPart </code>对象中删除所有内容,但不影响其头信息。
 * <PRE>
 *  ap1.clearContent();
 * </PRE>
 */

public abstract class AttachmentPart {
    /**
     * Returns the number of bytes in this <code>AttachmentPart</code>
     * object.
     *
     * <p>
     *  返回此<code> AttachmentPart </code>对象中的字节数。
     * 
     * 
     * @return the size of this <code>AttachmentPart</code> object in bytes
     *         or -1 if the size cannot be determined
     * @exception SOAPException if the content of this attachment is
     *            corrupted of if there was an exception while trying
     *            to determine the size.
     */
    public abstract int getSize() throws SOAPException;

    /**
     * Clears out the content of this <code>AttachmentPart</code> object.
     * The MIME header portion is left untouched.
     * <p>
     *  清除此<code> AttachmentPart </code>对象的内容。 MIME头部分保持不变。
     * 
     */
    public abstract void clearContent();

    /**
     * Gets the content of this <code>AttachmentPart</code> object as a Java
     * object. The type of the returned Java object depends on (1) the
     * <code>DataContentHandler</code> object that is used to interpret the bytes
     * and (2) the <code>Content-Type</code> given in the header.
     * <p>
     * For the MIME content types "text/plain", "text/html" and "text/xml", the
     * <code>DataContentHandler</code> object does the conversions to and
     * from the Java types corresponding to the MIME types.
     * For other MIME types,the <code>DataContentHandler</code> object
     * can return an <code>InputStream</code> object that contains the content data
     * as raw bytes.
     * <p>
     * A SAAJ-compliant implementation must, as a minimum, return a
     * <code>java.lang.String</code> object corresponding to any content
     * stream with a <code>Content-Type</code> value of
     * <code>text/plain</code>, a
     * <code>javax.xml.transform.stream.StreamSource</code> object corresponding to a
     * content stream with a <code>Content-Type</code> value of
     * <code>text/xml</code>, a <code>java.awt.Image</code> object
     * corresponding to a content stream with a
     * <code>Content-Type</code> value of <code>image/gif</code> or
     * <code>image/jpeg</code>.  For those content types that an
     * installed <code>DataContentHandler</code> object does not understand, the
     * <code>DataContentHandler</code> object is required to return a
     * <code>java.io.InputStream</code> object with the raw bytes.
     *
     * <p>
     *  将此<code> AttachmentPart </code>对象的内容作为Java对象。
     * 返回的Java对象的类型取决于(1)用于解释字节的<code> DataContentHandler </code>对象和(2)头中给定的<code> Content-Type </code>。
     * <p>
     *  对于MIME内容类型"text / plain","text / html"和"text / xml",<code> DataContentHandler </code>对象执行与MIME类型对应的J
     * ava类型的转换。
     * 对于其他MIME类型,<code> DataContentHandler </code>对象可以返回一个包含内容数据作为原始字节的<code> InputStream </code>对象。
     * <p>
     * 符合SAAJ的实现必须至少返回对应于具有<code> Content-Type </code>值<code>的任何内容流的<code> java.lang.String </code>对应于具有<code>
     *  Content-Type </code>值为<code> text / xml </code>的内容流的</code> / code>,与<code> Content-Type </code>值为<code>
     *  image / gif </code>或<code>的内容流相对应的<code> java.awt.Image </code> > image / jpeg </code>。
     * 对于安装的<code> DataContentHandler </code>对象不理解的那些内容类型,需要<code> DataContentHandler </code>对象返回一个<code> ja
     * va.io.InputStream </code>原始字节。
     * 
     * 
     * @return a Java object with the content of this <code>AttachmentPart</code>
     *         object
     *
     * @exception SOAPException if there is no content set into this
     *            <code>AttachmentPart</code> object or if there was a data
     *            transformation error
     */
    public abstract Object getContent() throws SOAPException;

    /**
     * Gets the content of this <code>AttachmentPart</code> object as an
     * InputStream as if a call had been made to <code>getContent</code> and no
     * <code>DataContentHandler</code> had been registered for the
     * <code>content-type</code> of this <code>AttachmentPart</code>.
     *<p>
     * Note that reading from the returned InputStream would result in consuming
     * the data in the stream. It is the responsibility of the caller to reset
     * the InputStream appropriately before calling a Subsequent API. If a copy
     * of the raw attachment content is required then the {@link #getRawContentBytes} API
     * should be used instead.
     *
     * <p>
     *  将此<code> AttachmentPart </code>对象的内容视为InputStream,就好像已对<code> getContent </code>进行调用,并且未向<code> Data
     * ContentHandler </code>注册<code > content-type </code>。
     * <code> AttachmentPart </code>。
     * p>
     *  注意,从返回的InputStream读取将导致消耗流中的数据。在调用后续API之前,调用者应负责重置InputStream。
     * 如果需要原始附件内容的副本,则应使用{@link #getRawContentBytes} API。
     * 
     * 
     * @return an <code>InputStream</code> from which the raw data contained by
     *      the <code>AttachmentPart</code> can be accessed.
     *
     * @throws SOAPException if there is no content set into this
     *      <code>AttachmentPart</code> object or if there was a data
     *      transformation error.
     *
     * @since SAAJ 1.3
     * @see #getRawContentBytes
     */
    public abstract InputStream getRawContent() throws SOAPException;

    /**
     * Gets the content of this <code>AttachmentPart</code> object as a
     * byte[] array as if a call had been made to <code>getContent</code> and no
     * <code>DataContentHandler</code> had been registered for the
     * <code>content-type</code> of this <code>AttachmentPart</code>.
     *
     * <p>
     * 将此<code> AttachmentPart </code>对象的内容作为byte []数组,如同对<code> getContent </code>进行调用并且未注册<code> DataConte
     * ntHandler </code>此<code> AttachmentPart </code>的<code> content-type </code>。
     * 
     * 
     * @return a <code>byte[]</code> array containing the raw data of the
     *      <code>AttachmentPart</code>.
     *
     * @throws SOAPException if there is no content set into this
     *      <code>AttachmentPart</code> object or if there was a data
     *      transformation error.
     *
     * @since SAAJ 1.3
     */
    public abstract byte[] getRawContentBytes() throws SOAPException;

    /**
     * Returns an <code>InputStream</code> which can be used to obtain the
     * content of <code>AttachmentPart</code>  as Base64 encoded
     * character data, this method would base64 encode the raw bytes
     * of the attachment and return.
     *
     * <p>
     *  返回一个<code> InputStream </code>,可用于获取<code> AttachmentPart </code>作为Base64编码字符数据的内容,此方法将base64编码附件的原始
     * 字节并返回。
     * 
     * 
     * @return an <code>InputStream</code> from which the Base64 encoded
     *       <code>AttachmentPart</code> can be read.
     *
     * @throws SOAPException if there is no content set into this
     *      <code>AttachmentPart</code> object or if there was a data
     *      transformation error.
     *
     * @since SAAJ 1.3
     */
    public abstract InputStream getBase64Content() throws SOAPException;

    /**
     * Sets the content of this attachment part to that of the given
     * <code>Object</code> and sets the value of the <code>Content-Type</code>
     * header to the given type. The type of the
     * <code>Object</code> should correspond to the value given for the
     * <code>Content-Type</code>. This depends on the particular
     * set of <code>DataContentHandler</code> objects in use.
     *
     *
     * <p>
     *  将此附件部分的内容设置为给定<code> Object </code>的内容,并将<code> Content-Type </code>头的值设置为给定类型。
     *  <code> Object </code>的类型应该对应于<code> Content-Type </code>给定的值。
     * 这取决于正在使用的<code> DataContentHandler </code>对象的特定集合。
     * 
     * 
     * @param object the Java object that makes up the content for
     *               this attachment part
     * @param contentType the MIME string that specifies the type of
     *                  the content
     *
     * @exception IllegalArgumentException may be thrown if the contentType
     *            does not match the type of the content object, or if there
     *            was no <code>DataContentHandler</code> object for this
     *            content object
     *
     * @see #getContent
     */
    public abstract void setContent(Object object, String contentType);

    /**
     * Sets the content of this attachment part to that contained by the
     * <code>InputStream</code> <code>content</code> and sets the value of the
     * <code>Content-Type</code> header to the value contained in
     * <code>contentType</code>.
     * <P>
     *  A subsequent call to getSize() may not be an exact measure
     *  of the content size.
     *
     * <p>
     *  将此附件部分的内容设置为<code> InputStream </code> <code> content </code>中包含的内容,并将<code> Content-Type </code> <code>
     *  contentType </code>。
     * <P>
     *  对getSize()的后续调用可能不是内容大小的精确度量。
     * 
     * 
     * @param content the raw data to add to the attachment part
     * @param contentType the value to set into the <code>Content-Type</code>
     * header
     *
     * @exception SOAPException if an there is an error in setting the content
     * @exception NullPointerException if <code>content</code> is null
     * @since SAAJ 1.3
     */
    public abstract void setRawContent(InputStream content, String contentType) throws SOAPException;

    /**
     * Sets the content of this attachment part to that contained by the
     * <code>byte[]</code> array <code>content</code> and sets the value of the
     * <code>Content-Type</code> header to the value contained in
     * <code>contentType</code>.
     *
     * <p>
     *  将此附件部分的内容设置为<code> byte [] </code> array <code> content </code>包含的内容,并将<code> Content-Type </code>值包
     * 含在<code> contentType </code>中。
     * 
     * 
     * @param content the raw data to add to the attachment part
     * @param contentType the value to set into the <code>Content-Type</code>
     * header
     * @param offset the offset in the byte array of the content
     * @param len the number of bytes that form the content
     *
     * @exception SOAPException if an there is an error in setting the content
     * or content is null
     * @since SAAJ 1.3
     */
    public abstract void setRawContentBytes(
        byte[] content, int offset, int len,  String contentType)
        throws SOAPException;


    /**
     * Sets the content of this attachment part from the Base64 source
     * <code>InputStream</code>  and sets the value of the
     * <code>Content-Type</code> header to the value contained in
     * <code>contentType</code>, This method would first decode the base64
     * input and write the resulting raw bytes to the attachment.
     * <P>
     *  A subsequent call to getSize() may not be an exact measure
     *  of the content size.
     *
     * <p>
     * 从Base64源<code> InputStream </code>设置此附件部分的内容,并将<code> Content-Type </code>头的值设置为<code> contentType </code>
     * 中包含的值,这个方法首先解码base64输入,然后将生成的原始字节写入附件。
     * <P>
     *  对getSize()的后续调用可能不是内容大小的精确度量。
     * 
     * 
     * @param content the base64 encoded data to add to the attachment part
     * @param contentType the value to set into the <code>Content-Type</code>
     * header
     *
     * @exception SOAPException if an there is an error in setting the content
     * @exception NullPointerException if <code>content</code> is null
     *
     * @since SAAJ 1.3
     */
    public abstract void setBase64Content(
        InputStream content, String contentType) throws SOAPException;


    /**
     * Gets the <code>DataHandler</code> object for this <code>AttachmentPart</code>
     * object.
     *
     * <p>
     *  获取此<code> AttachmentPart </code>对象的<code> DataHandler </code>对象。
     * 
     * 
     * @return the <code>DataHandler</code> object associated with this
     *         <code>AttachmentPart</code> object
     *
     * @exception SOAPException if there is no data in
     * this <code>AttachmentPart</code> object
     */
    public abstract DataHandler getDataHandler()
        throws SOAPException;

    /**
     * Sets the given <code>DataHandler</code> object as the data handler
     * for this <code>AttachmentPart</code> object. Typically, on an incoming
     * message, the data handler is automatically set. When
     * a message is being created and populated with content, the
     * <code>setDataHandler</code> method can be used to get data from
     * various data sources into the message.
     *
     * <p>
     *  将给定的<code> DataHandler </code>对象设置为此<code> AttachmentPart </code>对象的数据处理程序。通常,在传入消息上,数据处理程序将自动设置。
     * 当正在创建消息并使用内容填充时,可以使用<code> setDataHandler </code>方法从各种数据源获取数据到消息中。
     * 
     * 
     * @param dataHandler the <code>DataHandler</code> object to be set
     *
     * @exception IllegalArgumentException if there was a problem with
     *            the specified <code>DataHandler</code> object
     */
    public abstract void setDataHandler(DataHandler dataHandler);


    /**
     * Gets the value of the MIME header whose name is "Content-ID".
     *
     * <p>
     *  获取名称为"Content-ID"的MIME标题的值。
     * 
     * 
     * @return a <code>String</code> giving the value of the
     *          "Content-ID" header or <code>null</code> if there
     *          is none
     * @see #setContentId
     */
    public String getContentId() {
        String[] values = getMimeHeader("Content-ID");
        if (values != null && values.length > 0)
            return values[0];
        return null;
    }

    /**
     * Gets the value of the MIME header whose name is "Content-Location".
     *
     * <p>
     *  获取名称为"Content-Location"的MIME标题的值。
     * 
     * 
     * @return a <code>String</code> giving the value of the
     *          "Content-Location" header or <code>null</code> if there
     *          is none
     */
    public String getContentLocation() {
        String[] values = getMimeHeader("Content-Location");
        if (values != null && values.length > 0)
            return values[0];
        return null;
    }

    /**
     * Gets the value of the MIME header whose name is "Content-Type".
     *
     * <p>
     *  获取名称为"Content-Type"的MIME标题的值。
     * 
     * 
     * @return a <code>String</code> giving the value of the
     *          "Content-Type" header or <code>null</code> if there
     *          is none
     */
    public String getContentType() {
        String[] values = getMimeHeader("Content-Type");
        if (values != null && values.length > 0)
            return values[0];
        return null;
    }

    /**
     * Sets the MIME header whose name is "Content-ID" with the given value.
     *
     * <p>
     *  设置名称为"Content-ID"且具有给定值的MIME标题。
     * 
     * 
     * @param contentId a <code>String</code> giving the value of the
     *          "Content-ID" header
     *
     * @exception IllegalArgumentException if there was a problem with
     *            the specified <code>contentId</code> value
     * @see #getContentId
     */
    public void setContentId(String contentId)
    {
        setMimeHeader("Content-ID", contentId);
    }


    /**
     * Sets the MIME header whose name is "Content-Location" with the given value.
     *
     *
     * <p>
     *  设置名称为"Content-Location"且具有给定值的MIME标题。
     * 
     * 
     * @param contentLocation a <code>String</code> giving the value of the
     *          "Content-Location" header
     * @exception IllegalArgumentException if there was a problem with
     *            the specified content location
     */
    public void setContentLocation(String contentLocation)
    {
        setMimeHeader("Content-Location", contentLocation);
    }

    /**
     * Sets the MIME header whose name is "Content-Type" with the given value.
     *
     * <p>
     *  使用给定值设置名称为"Content-Type"的MIME标题。
     * 
     * 
     * @param contentType a <code>String</code> giving the value of the
     *          "Content-Type" header
     *
     * @exception IllegalArgumentException if there was a problem with
     *            the specified content type
     */
    public void setContentType(String contentType)
    {
        setMimeHeader("Content-Type", contentType);
    }

    /**
     * Removes all MIME headers that match the given name.
     *
     * <p>
     *  删除与给定名称匹配的所有MIME标题。
     * 
     * 
     * @param header the string name of the MIME header/s to
     *               be removed
     */
    public abstract void removeMimeHeader(String header);

    /**
     * Removes all the MIME header entries.
     * <p>
     *  删除所有MIME标头条目。
     * 
     */
    public abstract void removeAllMimeHeaders();


    /**
     * Gets all the values of the header identified by the given
     * <code>String</code>.
     *
     * <p>
     *  获取由给定的<code> String </code>标识的头的所有值。
     * 
     * 
     * @param name the name of the header; example: "Content-Type"
     * @return a <code>String</code> array giving the value for the
     *         specified header
     * @see #setMimeHeader
     */
    public abstract String[] getMimeHeader(String name);


    /**
     * Changes the first header entry that matches the given name
     * to the given value, adding a new header if no existing header
     * matches. This method also removes all matching headers but the first. <p>
     *
     * Note that RFC822 headers can only contain US-ASCII characters.
     *
     * <p>
     * 将与给定名称匹配的第一个报头条目更改为给定值,如果没有现有报头匹配,则添加新报头。此方法也删除所有匹配的标头,但第一个。 <p>
     * 
     *  请注意,RFC822标头只能包含US-ASCII字符。
     * 
     * 
     * @param   name    a <code>String</code> giving the name of the header
     *                  for which to search
     * @param   value   a <code>String</code> giving the value to be set for
     *                  the header whose name matches the given name
     *
     * @exception IllegalArgumentException if there was a problem with
     *            the specified mime header name or value
     */
    public abstract void setMimeHeader(String name, String value);


    /**
     * Adds a MIME header with the specified name and value to this
     * <code>AttachmentPart</code> object.
     * <p>
     * Note that RFC822 headers can contain only US-ASCII characters.
     *
     * <p>
     *  将具有指定名称和值的MIME标头添加到此<code> AttachmentPart </code>对象。
     * <p>
     *  请注意,RFC822标头只能包含US-ASCII字符。
     * 
     * 
     * @param   name    a <code>String</code> giving the name of the header
     *                  to be added
     * @param   value   a <code>String</code> giving the value of the header
     *                  to be added
     *
     * @exception IllegalArgumentException if there was a problem with
     *            the specified mime header name or value
     */
    public abstract void addMimeHeader(String name, String value);

    /**
     * Retrieves all the headers for this <code>AttachmentPart</code> object
     * as an iterator over the <code>MimeHeader</code> objects.
     *
     * <p>
     *  将<code> AttachmentPart </code>对象的所有头作为迭代器检索到<code> MimeHeader </code>对象。
     * 
     * 
     * @return  an <code>Iterator</code> object with all of the Mime
     *          headers for this <code>AttachmentPart</code> object
     */
    public abstract Iterator getAllMimeHeaders();

    /**
     * Retrieves all <code>MimeHeader</code> objects that match a name in
     * the given array.
     *
     * <p>
     *  检索与给定数组中的名称匹配的所有<code> MimeHeader </code>对象。
     * 
     * 
     * @param names a <code>String</code> array with the name(s) of the
     *        MIME headers to be returned
     * @return  all of the MIME headers that match one of the names in the
     *           given array as an <code>Iterator</code> object
     */
    public abstract Iterator getMatchingMimeHeaders(String[] names);

    /**
     * Retrieves all <code>MimeHeader</code> objects whose name does
     * not match a name in the given array.
     *
     * <p>
     *  检索其名称与给定数组中的名称不匹配的所有<code> MimeHeader </code>对象。
     * 
     * @param names a <code>String</code> array with the name(s) of the
     *        MIME headers not to be returned
     * @return  all of the MIME headers in this <code>AttachmentPart</code> object
     *          except those that match one of the names in the
     *           given array.  The nonmatching MIME headers are returned as an
     *           <code>Iterator</code> object.
     */
    public abstract Iterator getNonMatchingMimeHeaders(String[] names);
}
