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

import javax.xml.transform.Source;

/**
 * The container for the SOAP-specific portion of a <code>SOAPMessage</code>
 * object. All messages are required to have a SOAP part, so when a
 * <code>SOAPMessage</code> object is created, it will automatically
 * have a <code>SOAPPart</code> object.
 *<P>
 * A <code>SOAPPart</code> object is a MIME part and has the MIME headers
 * Content-Id, Content-Location, and Content-Type.  Because the value of
 * Content-Type must be "text/xml", a <code>SOAPPart</code> object automatically
 * has a MIME header of Content-Type with its value set to "text/xml".
 * The value must be "text/xml" because content in the SOAP part of a
 * message must be in XML format.  Content that is not of type "text/xml"
 * must be in an <code>AttachmentPart</code> object rather than in the
 * <code>SOAPPart</code> object.
 * <P>
 * When a message is sent, its SOAP part must have the MIME header Content-Type
 * set to "text/xml". Or, from the other perspective, the SOAP part of any
 * message that is received must have the MIME header Content-Type with a
 * value of "text/xml".
 * <P>
 * A client can access the <code>SOAPPart</code> object of a
 * <code>SOAPMessage</code> object by
 * calling the method <code>SOAPMessage.getSOAPPart</code>. The
 * following  line of code, in which <code>message</code> is a
 * <code>SOAPMessage</code> object, retrieves the SOAP part of a message.
 * <PRE>
 *   SOAPPart soapPart = message.getSOAPPart();
 * </PRE>
 * <P>
 * A <code>SOAPPart</code> object contains a <code>SOAPEnvelope</code> object,
 * which in turn contains a <code>SOAPBody</code> object and a
 * <code>SOAPHeader</code> object.
 * The <code>SOAPPart</code> method <code>getEnvelope</code> can be used
 * to retrieve the <code>SOAPEnvelope</code> object.
 * <P>
 * <p>
 *  用于<code> SOAPMessage </code>对象的SOAP特定部分的容器。
 * 所有消息都需要有一个SOAP部分,因此当创建一个<code> SOAPMessage </code>对象时,它将自动具有一个<code> SOAPPart </code>对象。
 * P>
 *  <code> SOAPPart </code>对象是MIME部分,具有MIME标题Content-Id,Content-Location和Content-Type。
 * 因为Content-Type的值必须是"text / xml",所以<code> SOAPPart </code>对象会自动具有Content-Type的MIME标题,其值设置为"text / xml"
 * 。
 *  <code> SOAPPart </code>对象是MIME部分,具有MIME标题Content-Id,Content-Location和Content-Type。
 * 该值必须为"text / xml",因为消息的SOAP部分中的内容必须是XML格式。
 * 不是"text / xml"类型的内容必须位于<code> AttachmentPart </code>对象中,而不是在<code> SOAPPart </code>对象中。
 * <P>
 *  发送消息时,其SOAP部分必须将MIME标题Content-Type设置为"text / xml"。
 * 或者,从另一个角度来看,接收的任何消息的SOAP部分必须具有值为"text / xml"的MIME头Con​​tent-Type。
 * <P>
 *  客户端可以通过调用<code> SOAPMessage.getSOAPPart </code>方法访问<code> SOAPMessage </code>对象的<code> SOAPPart </code>
 * 对象。
 * 以下代码行(其中<code> message </code>是<code> SOAPMessage </code>对象)检索消息的SOAP部分。
 * <PRE>
 *  SOAPPart soapPart = message.getSOAPPart();
 * </PRE>
 * <P>
 * 一个<code> SOAPPart </code>对象包含一个<code> SOAPEnvelope </code>对象,它又包含一个<code> SOAPBody </code>对象和一个<code>
 *  SOAPHeader </code>对象。
 * 可以使用<code> SOAPPart </code>方法<code> getEnvelope </code>来检索<code> SOAPEnvelope </code>对象。
 * <P>
 */
public abstract class SOAPPart implements org.w3c.dom.Document, Node {

    /**
     * Gets the <code>SOAPEnvelope</code> object associated with this
     * <code>SOAPPart</code> object. Once the SOAP envelope is obtained, it
     * can be used to get its contents.
     *
     * <p>
     *  获取与此<code> SOAPPart </code>对象关联的<code> SOAPEnvelope </code>对象。一旦获得SOAP包络,它就可以用于获取其内容。
     * 
     * 
     * @return the <code>SOAPEnvelope</code> object for this
     *           <code>SOAPPart</code> object
     * @exception SOAPException if there is a SOAP error
     */
    public abstract SOAPEnvelope getEnvelope() throws SOAPException;

    /**
     * Retrieves the value of the MIME header whose name is "Content-Id".
     *
     * <p>
     *  检索名称为"Content-Id"的MIME标题的值。
     * 
     * 
     * @return a <code>String</code> giving the value of the MIME header
     *         named "Content-Id"
     * @see #setContentId
     */
    public String getContentId() {
        String[] values = getMimeHeader("Content-Id");
        if (values != null && values.length > 0)
            return values[0];
        return null;
    }

    /**
     * Retrieves the value of the MIME header whose name is "Content-Location".
     *
     * <p>
     *  检索名称为"Content-Location"的MIME标题的值。
     * 
     * 
     * @return a <code>String</code> giving the value of the MIME header whose
     *          name is "Content-Location"
     * @see #setContentLocation
     */
    public String getContentLocation() {
        String[] values = getMimeHeader("Content-Location");
        if (values != null && values.length > 0)
            return values[0];
        return null;
    }

    /**
     * Sets the value of the MIME header named "Content-Id"
     * to the given <code>String</code>.
     *
     * <p>
     *  将名为"Content-Id"的MIME标头的值设置为给定的<code> String </code>。
     * 
     * 
     * @param contentId a <code>String</code> giving the value of the MIME
     *        header "Content-Id"
     *
     * @exception IllegalArgumentException if there is a problem in
     * setting the content id
     * @see #getContentId
     */
    public void setContentId(String contentId)
    {
        setMimeHeader("Content-Id", contentId);
    }
    /**
     * Sets the value of the MIME header "Content-Location"
     * to the given <code>String</code>.
     *
     * <p>
     *  将MIME标题"Content-Location"的值设置为给定的<code> String </code>。
     * 
     * 
     * @param contentLocation a <code>String</code> giving the value
     *        of the MIME
     *        header "Content-Location"
     * @exception IllegalArgumentException if there is a problem in
     *            setting the content location.
     * @see #getContentLocation
     */
    public void setContentLocation(String contentLocation)
    {
        setMimeHeader("Content-Location", contentLocation);
    }
    /**
     * Removes all MIME headers that match the given name.
     *
     * <p>
     *  删除与给定名称匹配的所有MIME标题。
     * 
     * 
     * @param header a <code>String</code> giving the name of the MIME header(s) to
     *               be removed
     */
    public abstract void removeMimeHeader(String header);

    /**
     * Removes all the <code>MimeHeader</code> objects for this
     * <code>SOAPEnvelope</code> object.
     * <p>
     *  删除此<code> SOAPEnvelope </code>对象的所有<code> MimeHeader </code>对象。
     * 
     */
    public abstract void removeAllMimeHeaders();

    /**
     * Gets all the values of the <code>MimeHeader</code> object
     * in this <code>SOAPPart</code> object that
     * is identified by the given <code>String</code>.
     *
     * <p>
     *  获取由给定的<code> String </code>标识的<code> SOAPPart </code>对象中的<code> MimeHeader </code>对象的所有值。
     * 
     * 
     * @param name the name of the header; example: "Content-Type"
     * @return a <code>String</code> array giving all the values for the
     *         specified header
     * @see #setMimeHeader
     */
    public abstract String[] getMimeHeader(String name);

    /**
     * Changes the first header entry that matches the given header name
     * so that its value is the given value, adding a new header with the
     * given name and value if no
     * existing header is a match. If there is a match, this method clears
     * all existing values for the first header that matches and sets the
     * given value instead. If more than one header has
     * the given name, this method removes all of the matching headers after
     * the first one.
     * <P>
     * Note that RFC822 headers can contain only US-ASCII characters.
     *
     * <p>
     *  更改与给定标题名称匹配的第一个标题条目,以使其值为给定值,如果没有现有标题匹配,则添加具有给定名称和值的新标题。如果存在匹配,此方法将清除匹配并设置给定值的第一个标头的所有现有值。
     * 如果多个头具有给定名称,则此方法将删除第一个头之后的所有匹配头。
     * <P>
     * 请注意,RFC822标头只能包含US-ASCII字符。
     * 
     * 
     * @param   name    a <code>String</code> giving the header name
     *                  for which to search
     * @param   value   a <code>String</code> giving the value to be set.
     *                  This value will be substituted for the current value(s)
     *                  of the first header that is a match if there is one.
     *                  If there is no match, this value will be the value for
     *                  a new <code>MimeHeader</code> object.
     *
     * @exception IllegalArgumentException if there was a problem with
     *            the specified mime header name or value
     * @see #getMimeHeader
     */
    public abstract void setMimeHeader(String name, String value);

    /**
     * Creates a <code>MimeHeader</code> object with the specified
     * name and value and adds it to this <code>SOAPPart</code> object.
     * If a <code>MimeHeader</code> with the specified name already
     * exists, this method adds the specified value to the already
     * existing value(s).
     * <P>
     * Note that RFC822 headers can contain only US-ASCII characters.
     *
     * <p>
     *  创建具有指定名称和值的<code> MimeHeader </code>对象,并将其添加到此<code> SOAPPart </code>对象。
     * 如果具有指定名称的<code> MimeHeader </code>已存在,则此方法将指定的值添加到已经存在的值。
     * <P>
     *  请注意,RFC822标头只能包含US-ASCII字符。
     * 
     * 
     * @param   name    a <code>String</code> giving the header name
     * @param   value   a <code>String</code> giving the value to be set
     *                  or added
     * @exception IllegalArgumentException if there was a problem with
     *            the specified mime header name or value
     */
    public abstract void addMimeHeader(String name, String value);

    /**
     * Retrieves all the headers for this <code>SOAPPart</code> object
     * as an iterator over the <code>MimeHeader</code> objects.
     *
     * <p>
     *  将<code> SOAPPart </code>对象的所有头作为迭代器检索到<code> MimeHeader </code>对象。
     * 
     * 
     * @return  an <code>Iterator</code> object with all of the Mime
     *          headers for this <code>SOAPPart</code> object
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
     *           given array, returned as an <code>Iterator</code> object
     */
    public abstract Iterator getMatchingMimeHeaders(String[] names);

    /**
     * Retrieves all <code>MimeHeader</code> objects whose name does
     * not match a name in the given array.
     *
     * <p>
     *  检索其名称与给定数组中的名称不匹配的所有<code> MimeHeader </code>对象。
     * 
     * 
     * @param names a <code>String</code> array with the name(s) of the
     *        MIME headers not to be returned
     * @return  all of the MIME headers in this <code>SOAPPart</code> object
     *          except those that match one of the names in the
     *           given array.  The nonmatching MIME headers are returned as an
     *           <code>Iterator</code> object.
     */
    public abstract Iterator getNonMatchingMimeHeaders(String[] names);

    /**
     * Sets the content of the <code>SOAPEnvelope</code> object with the data
     * from the given <code>Source</code> object. This <code>Source</code>
     * must contain a valid SOAP document.
     *
     * <p>
     *  使用给定的<code> Source </code>对象中的数据设置<code> SOAPEnvelope </code>对象的内容。
     * 此<code> Source </code>必须包含有效的SOAP文档。
     * 
     * 
     * @param source the <code>javax.xml.transform.Source</code> object with the
     *        data to be set
     *
     * @exception SOAPException if there is a problem in setting the source
     * @see #getContent
     */
    public abstract void setContent(Source source) throws SOAPException;

    /**
     * Returns the content of the SOAPEnvelope as a JAXP <code>Source</code>
     * object.
     *
     * <p>
     * 
     * @return the content as a <code>javax.xml.transform.Source</code> object
     *
     * @exception SOAPException if the implementation cannot convert
     *                          the specified <code>Source</code> object
     * @see #setContent
     */
    public abstract Source getContent() throws SOAPException;
}
