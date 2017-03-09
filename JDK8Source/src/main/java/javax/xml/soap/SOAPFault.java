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
import java.util.Locale;

import javax.xml.namespace.QName;

/**
 * An element in the <code>SOAPBody</code> object that contains
 * error and/or status information. This information may relate to
 * errors in the <code>SOAPMessage</code> object or to problems
 * that are not related to the content in the message itself. Problems
 * not related to the message itself are generally errors in
 * processing, such as the inability to communicate with an upstream
 * server.
 * <P>
 * Depending on the <code>protocol</code> specified while creating the
 * <code>MessageFactory</code> instance,  a <code>SOAPFault</code> has
 * sub-elements as defined in the SOAP 1.1/SOAP 1.2 specification.
 * <p>
 *  包含错误和/或状态信息的<code> SOAPBody </code>对象中的元素。此信息可能与<code> SOAPMessage </code>对象中的错误或与消息本身中的内容无关的问题有关。
 * 与消息本身无关的问题通常是处理中的错误,例如不能与上游服务器通信。
 * <P>
 *  根据在创建<code> MessageFactory </code>实例时指定的<code>协议</code>,<code> SOAPFault </code>具有SOAP 1.1 / SOAP 1.
 * 2规范中定义的子元素。
 * 
 */
public interface SOAPFault extends SOAPBodyElement {

    /**
     * Sets this <code>SOAPFault</code> object with the given fault code.
     *
     * <P> Fault codes, which give information about the fault, are defined
     * in the SOAP 1.1 specification. A fault code is mandatory and must
     * be of type <code>Name</code>. This method provides a convenient
     * way to set a fault code. For example,
     *
     * <PRE>
     * SOAPEnvelope se = ...;
     * // Create a qualified name in the SOAP namespace with a localName
     * // of "Client". Note that prefix parameter is optional and is null
     * // here which causes the implementation to use an appropriate prefix.
     * Name qname = se.createName("Client", null,
     *                            SOAPConstants.URI_NS_SOAP_ENVELOPE);
     * SOAPFault fault = ...;
     * fault.setFaultCode(qname);
     * </PRE>
     * It is preferable to use this method over {@link #setFaultCode(String)}.
     *
     * <p>
     *  使用给定的故障代码设置此<code> SOAPFault </code>对象。
     * 
     *  <p>提供故障信息的故障代码在SOAP 1.1规范中定义。故障代码是必需的,并且必须是类型<code> Name </code>。这种方法提供了一种方便的方法来设置故障代码。例如,
     * 
     * <PRE>
     *  SOAPEnvelope se = ...; //在SOAP命名空间中使用localName //"Client"创建限定名。注意,prefix参数是可选的,并且为空//这里使得实现使用适当的前缀。
     * 名称qname = se.createName("Client",null,SOAPConstants.URI_NS_SOAP_ENVELOPE); SOAPFault fault = ...; fau
     * lt.setFaultCode(qname);。
     * </PRE>
     *  最好在{@link #setFaultCode(String)}上使用此方法。
     * 
     * 
     * @param faultCodeQName a <code>Name</code> object giving the fault
     * code to be set. It must be namespace qualified.
     * @see #getFaultCodeAsName
     *
     * @exception SOAPException if there was an error in adding the
     *            <i>faultcode</i> element to the underlying XML tree.
     *
     * @since SAAJ 1.2
     */
    public void setFaultCode(Name faultCodeQName) throws SOAPException;

    /**
     * Sets this <code>SOAPFault</code> object with the given fault code.
     *
     * It is preferable to use this method over {@link #setFaultCode(Name)}.
     *
     * <p>
     * 使用给定的故障代码设置此<code> SOAPFault </code>对象。
     * 
     *  最好在{@link #setFaultCode(Name)}上使用此方法。
     * 
     * 
     * @param faultCodeQName a <code>QName</code> object giving the fault
     * code to be set. It must be namespace qualified.
     * @see #getFaultCodeAsQName
     *
     * @exception SOAPException if there was an error in adding the
     *            <code>faultcode</code> element to the underlying XML tree.
     *
     * @see #setFaultCode(Name)
     * @see #getFaultCodeAsQName()
     *
     * @since SAAJ 1.3
     */
    public void setFaultCode(QName faultCodeQName) throws SOAPException;

    /**
     * Sets this <code>SOAPFault</code> object with the give fault code.
     * <P>
     * Fault codes, which given information about the fault, are defined in
     * the SOAP 1.1 specification. This element is mandatory in SOAP 1.1.
     * Because the fault code is required to be a QName it is preferable to
     * use the {@link #setFaultCode(Name)} form of this method.
     *
     * <p>
     *  使用给出的故障代码设置此<code> SOAPFault </code>对象。
     * <P>
     *  给出关于故障的信息的故障代码在SOAP 1.1规范中定义。此元素在SOAP 1.1中是必需的。
     * 因为故障代码需要是QName,所以最好使用此方法的{@link #setFaultCode(Name)}形式。
     * 
     * 
     * @param faultCode a <code>String</code> giving the fault code to be set.
     *         It must be of the form "prefix:localName" where the prefix has
     *         been defined in a namespace declaration.
     * @see #setFaultCode(Name)
     * @see #getFaultCode
     * @see SOAPElement#addNamespaceDeclaration
     *
     * @exception SOAPException if there was an error in adding the
     *            <code>faultCode</code> to the underlying XML tree.
     */
    public void setFaultCode(String faultCode) throws SOAPException;

    /**
     * Gets the mandatory SOAP 1.1 fault code for this
     * <code>SOAPFault</code> object as a SAAJ <code>Name</code> object.
     * The SOAP 1.1 specification requires the value of the "faultcode"
     * element to be of type QName. This method returns the content of the
     * element as a QName in the form of a SAAJ Name object. This method
     * should be used instead of the <code>getFaultCode</code> method since
     * it allows applications to easily access the namespace name without
     * additional parsing.
     *
     * <p>
     *  将此<<code> SOAPFault </code>对象的强制性SOAP 1.1错误代码作为SAAJ <code> Name </code>对象获取。
     *  SOAP 1.1规范要求"faultcode"元素的值为类型QName。此方法以SAAJ Name对象的形式返回元素的内容作为QName。
     * 应该使用此方法而不是<code> getFaultCode </code>方法,因为它允许应用程序轻松访问命名空间名称,而无需额外的解析。
     * 
     * 
     * @return a <code>Name</code> representing the faultcode
     * @see #setFaultCode(Name)
     *
     * @since SAAJ 1.2
     */
    public Name getFaultCodeAsName();


    /**
     * Gets the fault code for this
     * <code>SOAPFault</code> object as a <code>QName</code> object.
     *
     * <p>
     *  将<code> SOAPFault </code>对象的故障代码作为<code> QName </code>对象获取。
     * 
     * 
     * @return a <code>QName</code> representing the faultcode
     *
     * @see #setFaultCode(QName)
     *
     * @since SAAJ 1.3
     */
    public QName getFaultCodeAsQName();

    /**
     * Gets the Subcodes for this <code>SOAPFault</code> as an iterator over
     * <code>QNames</code>.
     *
     * <p>
     *  获取<code> SOAPFault </code>的子代码作为<code> QNames </code>的迭代器。
     * 
     * 
     * @return an <code>Iterator</code> that accesses a sequence of
     *      <code>QNames</code>. This <code>Iterator</code> should not support
     *      the optional <code>remove</code> method. The order in which the
     *      Subcodes are returned reflects the hierarchy of Subcodes present
     *      in the fault from top to bottom.
     *
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Subcode.
     *
     * @since SAAJ 1.3
     */
    public Iterator getFaultSubcodes();

    /**
     * Removes any Subcodes that may be contained by this
     * <code>SOAPFault</code>. Subsequent calls to
     * <code>getFaultSubcodes</code> will return an empty iterator until a call
     * to <code>appendFaultSubcode</code> is made.
     *
     * <p>
     *  删除此<code> SOAPFault </code>可能包含的任何子代码。
     * 对<code> getFaultSubcodes </code>的后续调用将返回一个空迭代器,直到调用<code> appendFaultSubcode </code>。
     * 
     * 
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Subcode.
     *
     * @since SAAJ 1.3
     */
    public void removeAllFaultSubcodes();

    /**
     * Adds a Subcode to the end of the sequence of Subcodes contained by this
     * <code>SOAPFault</code>. Subcodes, which were introduced in SOAP 1.2, are
     * represented by a recursive sequence of subelements rooted in the
     * mandatory Code subelement of a SOAP Fault.
     *
     * <p>
     * 在此<code> SOAPFault </code>包含的子代码序列的末尾添加一个子代码。在SOAP 1.2中引入的子代码由基于SOAP故障的强制性代码子元素的子元素的递归序列表示。
     * 
     * 
     * @param subcode a QName containing the Value of the Subcode.
     *
     * @exception SOAPException if there was an error in setting the Subcode
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Subcode.
     *
     * @since SAAJ 1.3
     */
    public void appendFaultSubcode(QName subcode) throws SOAPException;

    /**
     * Gets the fault code for this <code>SOAPFault</code> object.
     *
     * <p>
     *  获取此<> SOAPFault </code>对象的故障代码。
     * 
     * 
     * @return a <code>String</code> with the fault code
     * @see #getFaultCodeAsName
     * @see #setFaultCode
     */
    public String getFaultCode();

    /**
     * Sets this <code>SOAPFault</code> object with the given fault actor.
     * <P>
     * The fault actor is the recipient in the message path who caused the
     * fault to happen.
     * <P>
     * If this <code>SOAPFault</code> supports SOAP 1.2 then this call is
     * equivalent to {@link #setFaultRole(String)}
     *
     * <p>
     *  使用给定的fault actor设置此<code> SOAPFault </code>对象。
     * <P>
     *  故障代理是导致故障发生的消息路径中的接收者。
     * <P>
     *  如果这个<code> SOAPFault </code>支持SOAP 1.2,那么这个调用相当于{@link #setFaultRole(String)}
     * 
     * 
     * @param faultActor a <code>String</code> identifying the actor that
     *        caused this <code>SOAPFault</code> object
     * @see #getFaultActor
     *
     * @exception SOAPException if there was an error in adding the
     *            <code>faultActor</code> to the underlying XML tree.
     */
    public void setFaultActor(String faultActor) throws SOAPException;

    /**
     * Gets the fault actor for this <code>SOAPFault</code> object.
     * <P>
     * If this <code>SOAPFault</code> supports SOAP 1.2 then this call is
     * equivalent to {@link #getFaultRole()}
     *
     * <p>
     *  获取此<> SOAPFault </code>对象的故障代理。
     * <P>
     *  如果这个<code> SOAPFault </code>支持SOAP 1.2,那么这个调用相当于{@link #getFaultRole()}
     * 
     * 
     * @return a <code>String</code> giving the actor in the message path
     *         that caused this <code>SOAPFault</code> object
     * @see #setFaultActor
     */
    public String getFaultActor();

    /**
     * Sets the fault string for this <code>SOAPFault</code> object
     * to the given string.
     * <P>
     * If this
     * <code>SOAPFault</code> is part of a message that supports SOAP 1.2 then
     * this call is equivalent to:
     * <pre>
     *      addFaultReasonText(faultString, Locale.getDefault());
     * </pre>
     *
     * <p>
     *  将<code> SOAPFault </code>对象的故障字符串设置为给定字符串。
     * <P>
     *  如果这个<code> SOAPFault </code>是支持SOAP 1.2的消息的一部分,则此调用等效于：
     * <pre>
     *  addFaultReasonText(faultString,Locale.getDefault());
     * </pre>
     * 
     * 
     * @param faultString a <code>String</code> giving an explanation of
     *        the fault
     * @see #getFaultString
     *
     * @exception SOAPException if there was an error in adding the
     *            <code>faultString</code> to the underlying XML tree.
     */
    public void setFaultString(String faultString) throws SOAPException;

    /**
     * Sets the fault string for this <code>SOAPFault</code> object
     * to the given string and localized to the given locale.
     * <P>
     * If this
     * <code>SOAPFault</code> is part of a message that supports SOAP 1.2 then
     * this call is equivalent to:
     * <pre>
     *      addFaultReasonText(faultString, locale);
     * </pre>
     *
     * <p>
     *  将<code> SOAPFault </code>对象的故障字符串设置为给定字符串并本地化到给定的语言环境。
     * <P>
     *  如果这个<code> SOAPFault </code>是支持SOAP 1.2的消息的一部分,则此调用等效于：
     * <pre>
     *  addFaultReasonText(faultString,locale);
     * </pre>
     * 
     * 
     * @param faultString a <code>String</code> giving an explanation of
     *         the fault
     * @param locale a {@link java.util.Locale Locale} object indicating
     *         the native language of the <code>faultString</code>
     * @see #getFaultString
     *
     * @exception SOAPException if there was an error in adding the
     *            <code>faultString</code> to the underlying XML tree.
     *
     * @since SAAJ 1.2
     */
    public void setFaultString(String faultString, Locale locale)
        throws SOAPException;

    /**
     * Gets the fault string for this <code>SOAPFault</code> object.
     * <P>
     * If this
     * <code>SOAPFault</code> is part of a message that supports SOAP 1.2 then
     * this call is equivalent to:
     * <pre>
     *    String reason = null;
     *    try {
     *        reason = (String) getFaultReasonTexts().next();
     *    } catch (SOAPException e) {}
     *    return reason;
     * </pre>
     *
     * <p>
     *  获取此<> SOAPFault </code>对象的故障字符串。
     * <P>
     *  如果这个<code> SOAPFault </code>是支持SOAP 1.2的消息的一部分,则此调用等效于：
     * <pre>
     * String reason = null; try {reason =(String)getFaultReasonTexts()。
     * next(); } catch(SOAPException e){} return reason;。
     * </pre>
     * 
     * 
     * @return a <code>String</code> giving an explanation of
     *        the fault
     * @see #setFaultString(String)
     * @see #setFaultString(String, Locale)
     */
    public String getFaultString();

    /**
     * Gets the locale of the fault string for this <code>SOAPFault</code>
     * object.
     * <P>
     * If this
     * <code>SOAPFault</code> is part of a message that supports SOAP 1.2 then
     * this call is equivalent to:
     * <pre>
     *    Locale locale = null;
     *    try {
     *        locale = (Locale) getFaultReasonLocales().next();
     *    } catch (SOAPException e) {}
     *    return locale;
     * </pre>
     *
     * <p>
     *  获取此<<code> SOAPFault </code>对象的故障字符串的区域设置。
     * <P>
     *  如果这个<code> SOAPFault </code>是支持SOAP 1.2的消息的一部分,则此调用等效于：
     * <pre>
     *  Locale locale = null; try {locale =(Locale)getFaultReasonLocales()。
     * next(); } catch(SOAPException e){} return locale;。
     * </pre>
     * 
     * 
     * @return a <code>Locale</code> object indicating the native language of
     *          the fault string or <code>null</code> if no locale was specified
     * @see #setFaultString(String, Locale)
     *
     * @since SAAJ 1.2
     */
    public Locale getFaultStringLocale();

    /**
     * Returns true if this <code>SOAPFault</code> has a <code>Detail</code>
     * subelement and false otherwise. Equivalent to
     * <code>(getDetail()!=null)</code>.
     *
     * <p>
     *  如果此<code> SOAPFault </code>具有<code> Detail </code>子元素,则返回true,否则返回false。
     * 等同于<code>(getDetail()！= null)</code>。
     * 
     * 
     * @return true if this <code>SOAPFault</code> has a <code>Detail</code>
     * subelement and false otherwise.
     *
     * @since SAAJ 1.3
     */
    public boolean hasDetail();

    /**
     * Returns the optional detail element for this <code>SOAPFault</code>
     * object.
     * <P>
     * A <code>Detail</code> object carries application-specific error
     * information, the scope of the error information is restricted to
     * faults in the <code>SOAPBodyElement</code> objects if this is a
     * SOAP 1.1 Fault.
     *
     * <p>
     *  返回此<> SOAPFault </code>对象的可选详细信息元素。
     * <P>
     *  <code> Detail </code>对象包含应用程序特定的错误信息,如果这是SOAP 1.1 Fault,则错误信息的范围限制在<code> SOAPBodyElement </code>对象中
     * 的错误。
     * 
     * 
     * @return a <code>Detail</code> object with application-specific
     *         error information if present, null otherwise
     */
    public Detail getDetail();

    /**
     * Creates an optional <code>Detail</code> object and sets it as the
     * <code>Detail</code> object for this <code>SOAPFault</code>
     * object.
     * <P>
     * It is illegal to add a detail when the fault already
     * contains a detail. Therefore, this method should be called
     * only after the existing detail has been removed.
     *
     * <p>
     *  创建一个可选的<code> Detail </code>对象,并将其设置为<code> SOAPFault </code>对象的<code> Detail </code>对象。
     * <P>
     *  当故障已包含详细信息时添加详细信息是非法的。因此,只有在删除现有细节之后才应调用此方法。
     * 
     * 
     * @return the new <code>Detail</code> object
     *
     * @exception SOAPException if this
     *            <code>SOAPFault</code> object already contains a
     *            valid <code>Detail</code> object
     */
    public Detail addDetail() throws SOAPException;

    /**
     * Returns an <code>Iterator</code> over a distinct sequence of
     * <code>Locale</code>s for which there are associated Reason Text items.
     * Any of these <code>Locale</code>s can be used in a call to
     * <code>getFaultReasonText</code> in order to obtain a localized version
     * of the Reason Text string.
     *
     * <p>
     *  在具有相关联的原因文本项的<code> Locale </code>的不同序列上返回<code>迭代器</code>。
     * 这些<code> Locale </code>中的任何一个都可以用于调用<code> getFaultReasonText </code>,以获取原因文本字符串的本地化版本。
     * 
     * 
     * @return an <code>Iterator</code> over a sequence of <code>Locale</code>
     *      objects for which there are associated Reason Text items.
     *
     * @exception SOAPException if there was an error in retrieving
     * the  fault Reason locales.
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Reason.
     *
     * @since SAAJ 1.3
     */
    public Iterator getFaultReasonLocales() throws SOAPException;

    /**
     * Returns an <code>Iterator</code> over a sequence of
     * <code>String</code> objects containing all of the Reason Text items for
     * this <code>SOAPFault</code>.
     *
     * <p>
     * 在包含此<code> SOAPFault </code>的所有原因文本项的<code> String </code>对象序列中返回<code>迭代器</code>。
     * 
     * 
     * @return an <code>Iterator</code> over env:Fault/env:Reason/env:Text items.
     *
     * @exception SOAPException if there was an error in retrieving
     * the  fault Reason texts.
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Reason.
     *
     * @since SAAJ 1.3
     */
    public Iterator getFaultReasonTexts() throws SOAPException;

    /**
     * Returns the Reason Text associated with the given <code>Locale</code>.
     * If more than one such Reason Text exists the first matching Text is
     * returned
     *
     * <p>
     *  返回与给定的<code> Locale </code>关联的原因文本。如果存在多于一个这样的原因文本,则返回第一匹配的文本
     * 
     * 
     * @param locale -- the <code>Locale</code> for which a localized
     *      Reason Text is desired
     *
     * @return the Reason Text associated with <code>locale</code>
     *
     * @see #getFaultString
     *
     * @exception SOAPException if there was an error in retrieving
     * the  fault Reason text for the specified locale .
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Reason.
     *
     * @since SAAJ 1.3
     */
    public String getFaultReasonText(Locale locale) throws SOAPException;

    /**
     * Appends or replaces a Reason Text item containing the specified
     * text message and an <i>xml:lang</i> derived from
     * <code>locale</code>. If a Reason Text item with this
     * <i>xml:lang</i> already exists its text value will be replaced
     * with <code>text</code>.
     * The <code>locale</code> parameter should not be <code>null</code>
     * <P>
     * Code sample:
     *
     * <PRE>
     * SOAPFault fault = ...;
     * fault.addFaultReasonText("Version Mismatch", Locale.ENGLISH);
     * </PRE>
     *
     * <p>
     *  附加或替换包含指定文本消息和源自<code> locale </code>的<i> xml：lang </i>的原因文本项。
     * 如果包含此<i> xml：lang </i>的原因文本项目已存在,则其文本值将替换为<code> text </code>。
     *  <code> locale </code>参数不应为<code> null </code>。
     * <P>
     *  代码示例：
     * 
     * <PRE>
     *  SOAPFault fault = ...; fault.addFaultReasonText("Version Mismatch",Locale.ENGLISH);
     * </PRE>
     * 
     * 
     * @param text -- reason message string
     * @param locale -- Locale object representing the locale of the message
     *
     * @exception SOAPException if there was an error in adding the Reason text
     * or the <code>locale</code> passed was <code>null</code>.
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Reason.
     *
     * @since SAAJ 1.3
     */
    public void addFaultReasonText(String text, java.util.Locale locale)
        throws SOAPException;

    /**
     * Returns the optional Node element value for this
     * <code>SOAPFault</code> object. The Node element is
     * optional in SOAP 1.2.
     *
     * <p>
     *  返回此<> SOAPFault </code>对象的可选Node元素值。 Node元素在SOAP 1.2中是可选的。
     * 
     * 
     * @return Content of the env:Fault/env:Node element as a String
     * or <code>null</code> if none
     *
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Node.
     *
     * @since SAAJ 1.3
     */
    public String getFaultNode();

    /**
     * Creates or replaces any existing Node element value for
     * this <code>SOAPFault</code> object. The Node element
     * is optional in SOAP 1.2.
     *
     * <p>
     *  为此<code> SOAPFault </code>对象创建或替换任何现有的Node元素值。 Node元素在SOAP 1.2中是可选的。
     * 
     * 
     * @exception SOAPException  if there was an error in setting the
     *            Node for this  <code>SOAPFault</code> object.
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Node.
     *
     *
     * @since SAAJ 1.3
     */
    public void setFaultNode(String uri) throws SOAPException;

    /**
     * Returns the optional Role element value for this
     * <code>SOAPFault</code> object. The Role element is
     * optional in SOAP 1.2.
     *
     * <p>
     *  返回此<> SOAPFault </code>对象的可选Role元素值。 Role元素在SOAP 1.2中是可选的。
     * 
     * 
     * @return Content of the env:Fault/env:Role element as a String
     * or <code>null</code> if none
     *
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Role.
     *
     * @since SAAJ 1.3
     */
    public String getFaultRole();

    /**
     * Creates or replaces any existing Role element value for
     * this <code>SOAPFault</code> object. The Role element
     * is optional in SOAP 1.2.
     *
     * <p>
     *  为此<code> SOAPFault </code>对象创建或替换任何现有的Role元素值。 Role元素在SOAP 1.2中是可选的。
     * 
     * @param uri - the URI of the Role
     *
     * @exception SOAPException  if there was an error in setting the
     *            Role for this  <code>SOAPFault</code> object.
     *
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Role.
     *
     * @since SAAJ 1.3
     */
    public void setFaultRole(String uri) throws SOAPException;

}
