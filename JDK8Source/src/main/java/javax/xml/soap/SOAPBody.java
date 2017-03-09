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

import java.util.Locale;

import org.w3c.dom.Document;

import javax.xml.namespace.QName;

/**
 * An object that represents the contents of the SOAP body
 * element in a SOAP message. A SOAP body element consists of XML data
 * that affects the way the application-specific content is processed.
 * <P>
 * A <code>SOAPBody</code> object contains <code>SOAPBodyElement</code>
 * objects, which have the content for the SOAP body.
 * A <code>SOAPFault</code> object, which carries status and/or
 * error information, is an example of a <code>SOAPBodyElement</code> object.
 *
 * <p>
 *  表示SOAP消息中SOAP主体元素的内容的对象。 SOAP主体元素由影响应用程序特定内容处理方式的XML数据组成。
 * <P>
 *  <code> SOAPBody </code>对象包含<code> SOAPBodyElement </code>对象,它们具有SOAP主体的内容。
 * 携带状态和/或错误信息的<code> SOAPFault </code>对象是<code> SOAPBodyElement </code>对象的示例。
 * 
 * 
 * @see SOAPFault
 */
public interface SOAPBody extends SOAPElement {

    /**
     * Creates a new <code>SOAPFault</code> object and adds it to
     * this <code>SOAPBody</code> object. The new <code>SOAPFault</code> will
     * have default values set for the mandatory child elements. The type of
     * the <code>SOAPFault</code> will be a SOAP 1.1 or a SOAP 1.2 <code>SOAPFault</code>
     * depending on the <code>protocol</code> specified while creating the
     * <code>MessageFactory</code> instance.
     * <p>
     * A <code>SOAPBody</code> may contain at most one <code>SOAPFault</code>
     * child element.
     *
     * <p>
     *  创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * 新的<code> SOAPFault </code>将为必需的子元素设置默认值。
     * 根据在创建<code> MessageFactory </code>时指定的<code>协议</code>,<code> SOAPFault </code>的类型将是SOAP 1.1或SOAP 1.2 
     * <code> SOAPFault </code>代码>实例。
     * 新的<code> SOAPFault </code>将为必需的子元素设置默认值。
     * <p>
     *  <code> SOAPBody </code>最多可以包含一个<code> SOAPFault </code>子元素。
     * 
     * 
     * @return the new <code>SOAPFault</code> object
     * @exception SOAPException if there is a SOAP error
     */
    public SOAPFault addFault() throws SOAPException;


    /**
     * Creates a new <code>SOAPFault</code> object and adds it to
     * this <code>SOAPBody</code> object. The type of the
     * <code>SOAPFault</code> will be a SOAP 1.1  or a SOAP 1.2
     * <code>SOAPFault</code> depending on the <code>protocol</code>
     * specified while creating the <code>MessageFactory</code> instance.
     * <p>
     * For SOAP 1.2 the <code>faultCode</code> parameter is the value of the
     * <i>Fault/Code/Value</i> element  and the <code>faultString</code> parameter
     * is the value of the <i>Fault/Reason/Text</i> element. For SOAP 1.1
     * the <code>faultCode</code> parameter is the value of the <code>faultcode</code>
     * element and the <code>faultString</code> parameter is the value of the <code>faultstring</code>
     * element.
     * <p>
     * A <code>SOAPBody</code> may contain at most one <code>SOAPFault</code>
     * child element.
     *
     * <p>
     *  创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * 根据在创建<code> MessageFactory </code>时指定的<code>协议</code>,<code> SOAPFault </code>的类型将是SOAP 1.1或SOAP 1.2 
     * <code> SOAPFault </code>代码>实例。
     *  创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * <p>
     * 对于SOAP 1.2,<code> faultCode </code>参数是<i> Fault / Code / Value </i>元素的值,<code> faultString </code>参数是
     * <i>故障/原因/文本</i>元素。
     * 对于SOAP 1.1,<code> faultCode </code>参数是<code> faultcode </code>元素的值,<code> faultString </code>参数是<code>
     *  faultstring </code >元素。
     * <p>
     *  <code> SOAPBody </code>最多可以包含一个<code> SOAPFault </code>子元素。
     * 
     * 
     * @param faultCode a <code>Name</code> object giving the fault
     *         code to be set; must be one of the fault codes defined in the Version
     *         of SOAP specification in use
     * @param faultString a <code>String</code> giving an explanation of
     *         the fault
     * @param locale a {@link java.util.Locale} object indicating
     *         the native language of the <code>faultString</code>
     * @return the new <code>SOAPFault</code> object
     * @exception SOAPException if there is a SOAP error
     * @see SOAPFault#setFaultCode
     * @see SOAPFault#setFaultString
     * @since SAAJ 1.2
     */
    public SOAPFault addFault(Name faultCode, String faultString, Locale locale) throws SOAPException;

    /**
     * Creates a new <code>SOAPFault</code> object and adds it to this
     * <code>SOAPBody</code> object. The type of the <code>SOAPFault</code>
     * will be a SOAP 1.1 or a SOAP 1.2 <code>SOAPFault</code> depending on
     * the <code>protocol</code> specified while creating the <code>MessageFactory</code>
     * instance.
     * <p>
     * For SOAP 1.2 the <code>faultCode</code> parameter is the value of the
     * <i>Fault/Code/Value</i> element  and the <code>faultString</code> parameter
     * is the value of the <i>Fault/Reason/Text</i> element. For SOAP 1.1
     * the <code>faultCode</code> parameter is the value of the <code>faultcode</code>
     * element and the <code>faultString</code> parameter is the value of the <code>faultstring</code>
     * element.
     * <p>
     * A <code>SOAPBody</code> may contain at most one <code>SOAPFault</code>
     * child element.
     *
     * <p>
     *  创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * 根据在创建<code> MessageFactory </code>时指定的<code>协议</code>,<code> SOAPFault </code>的类型将是SOAP 1.1或SOAP 1.2 
     * <code> SOAPFault </code>代码>实例。
     *  创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * <p>
     *  对于SOAP 1.2,<code> faultCode </code>参数是<i> Fault / Code / Value </i>元素的值,<code> faultString </code>参数
     * 是<i>故障/原因/文本</i>元素。
     * 对于SOAP 1.1,<code> faultCode </code>参数是<code> faultcode </code>元素的值,<code> faultString </code>参数是<code>
     *  faultstring </code >元素。
     * <p>
     *  <code> SOAPBody </code>最多可以包含一个<code> SOAPFault </code>子元素。
     * 
     * 
     * @param faultCode
     *            a <code>QName</code> object giving the fault code to be
     *            set; must be one of the fault codes defined in the version
     *            of SOAP specification in use.
     * @param faultString
     *            a <code>String</code> giving an explanation of the fault
     * @param locale
     *            a {@link java.util.Locale Locale} object indicating the
     *            native language of the <code>faultString</code>
     * @return the new <code>SOAPFault</code> object
     * @exception SOAPException
     *                if there is a SOAP error
     * @see SOAPFault#setFaultCode
     * @see SOAPFault#setFaultString
     * @see SOAPBody#addFault(Name faultCode, String faultString, Locale locale)
     *
     * @since SAAJ 1.3
     */
    public SOAPFault addFault(QName faultCode, String faultString, Locale locale)
        throws SOAPException;

    /**
     * Creates a new  <code>SOAPFault</code> object and adds it to this
     * <code>SOAPBody</code> object. The type of the <code>SOAPFault</code>
     * will be a SOAP 1.1 or a SOAP 1.2 <code>SOAPFault</code> depending on
     * the <code>protocol</code> specified while creating the <code>MessageFactory</code>
     * instance.
     * <p>
     * For SOAP 1.2 the <code>faultCode</code> parameter is the value of the
     * <i>Fault/Code/Value</i> element  and the <code>faultString</code> parameter
     * is the value of the <i>Fault/Reason/Text</i> element. For SOAP 1.1
     * the <code>faultCode</code> parameter is the value of the <i>faultcode</i>
     * element and the <code>faultString</code> parameter is the value of the <i>faultstring</i>
     * element.
     * <p>
     * In case of a SOAP 1.2 fault, the default value for the mandatory <code>xml:lang</code>
     * attribute on the <i>Fault/Reason/Text</i> element will be set to
     * <code>java.util.Locale.getDefault()</code>
     * <p>
     * A <code>SOAPBody</code> may contain at most one <code>SOAPFault</code>
     * child element.
     *
     * <p>
     * 创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * 根据在创建<code> MessageFactory </code>时指定的<code>协议</code>,<code> SOAPFault </code>的类型将是SOAP 1.1或SOAP 1.2 
     * <code> SOAPFault </code>代码>实例。
     * 创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * <p>
     *  对于SOAP 1.2,<code> faultCode </code>参数是<i> Fault / Code / Value </i>元素的值,<code> faultString </code>参数
     * 是<i>故障/原因/文本</i>元素。
     * 对于SOAP 1.1,<code> faultCode </code>参数是<i> faultcode </i>元素的值,<code> faultString </code>参数是<i> faultst
     * ring </i >元素。
     * <p>
     *  在SOAP 1.2故障的情况下,<i> Fault / Reason / Text </i>元素上的强制性<code> xml：lang </code>属性的默认值将设置为<code> java.ut
     * il .Locale.getDefault()</code>。
     * <p>
     *  <code> SOAPBody </code>最多可以包含一个<code> SOAPFault </code>子元素。
     * 
     * 
     * @param faultCode
     *            a <code>Name</code> object giving the fault code to be set;
     *            must be one of the fault codes defined in the version of SOAP
     *            specification in use
     * @param faultString
     *            a <code>String</code> giving an explanation of the fault
     * @return the new <code>SOAPFault</code> object
     * @exception SOAPException
     *                if there is a SOAP error
     * @see SOAPFault#setFaultCode
     * @see SOAPFault#setFaultString
     * @since SAAJ 1.2
     */
    public SOAPFault addFault(Name faultCode, String faultString)
        throws SOAPException;

    /**
     * Creates a new <code>SOAPFault</code> object and adds it to this <code>SOAPBody</code>
     * object. The type of the <code>SOAPFault</code>
     * will be a SOAP 1.1 or a SOAP 1.2 <code>SOAPFault</code> depending on
     * the <code>protocol</code> specified while creating the <code>MessageFactory</code>
     * instance.
     * <p>
     * For SOAP 1.2 the <code>faultCode</code> parameter is the value of the
     * <i>Fault/Code/Value</i> element  and the <code>faultString</code> parameter
     * is the value of the <i>Fault/Reason/Text</i> element. For SOAP 1.1
     * the <code>faultCode</code> parameter is the value of the <i>faultcode</i>
     * element and the <code>faultString</code> parameter is the value of the <i>faultstring</i>
     * element.
     * <p>
     * In case of a SOAP 1.2 fault, the default value for the mandatory <code>xml:lang</code>
     * attribute on the <i>Fault/Reason/Text</i> element will be set to
     * <code>java.util.Locale.getDefault()</code>
     * <p>
     * A <code>SOAPBody</code> may contain at most one <code>SOAPFault</code>
     * child element
     *
     * <p>
     *  创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * 根据在创建<code> MessageFactory </code>时指定的<code>协议</code>,<code> SOAPFault </code>的类型将是SOAP 1.1或SOAP 1.2 
     * <code> SOAPFault </code>代码>实例。
     *  创建一个新的<code> SOAPFault </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * <p>
     * 对于SOAP 1.2,<code> faultCode </code>参数是<i> Fault / Code / Value </i>元素的值,<code> faultString </code>参数是
     * <i>故障/原因/文本</i>元素。
     * 对于SOAP 1.1,<code> faultCode </code>参数是<i> faultcode </i>元素的值,<code> faultString </code>参数是<i> faultst
     * ring </i >元素。
     * <p>
     *  在SOAP 1.2故障的情况下,<i> Fault / Reason / Text </i>元素上的强制性<code> xml：lang </code>属性的默认值将设置为<code> java.ut
     * il .Locale.getDefault()</code>。
     * <p>
     *  <code> SOAPBody </code>最多可以包含一个<code> SOAPFault </code>子元素
     * 
     * 
     * @param faultCode
     *            a <code>QName</code> object giving the fault code to be
     *            set; must be one of the fault codes defined in the version
     *            of  SOAP specification in use
     * @param faultString
     *            a <code>String</code> giving an explanation of the fault
     * @return the new <code>SOAPFault</code> object
     * @exception SOAPException
     *                if there is a SOAP error
     * @see SOAPFault#setFaultCode
     * @see SOAPFault#setFaultString
     * @see SOAPBody#addFault(Name faultCode, String faultString)
     * @since SAAJ 1.3
     */
    public SOAPFault addFault(QName faultCode, String faultString)
        throws SOAPException;

    /**
     * Indicates whether a <code>SOAPFault</code> object exists in this
     * <code>SOAPBody</code> object.
     *
     * <p>
     *  指示在此<code> SOAPBody </code>对象中是否存在<code> SOAPFault </code>对象。
     * 
     * 
     * @return <code>true</code> if a <code>SOAPFault</code> object exists
     *         in this <code>SOAPBody</code> object; <code>false</code>
     *         otherwise
     */
    public boolean hasFault();

    /**
     * Returns the <code>SOAPFault</code> object in this <code>SOAPBody</code>
     * object.
     *
     * <p>
     *  返回<code> SOAPBody </code>对象中的<code> SOAPFault </code>对象。
     * 
     * 
     * @return the <code>SOAPFault</code> object in this <code>SOAPBody</code>
     *         object if present, null otherwise.
     */
    public SOAPFault getFault();

    /**
     * Creates a new <code>SOAPBodyElement</code> object with the specified
     * name and adds it to this <code>SOAPBody</code> object.
     *
     * <p>
     *  创建具有指定名称的新<object> SOAPBodyElement </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * 
     * 
     * @param name
     *            a <code>Name</code> object with the name for the new <code>SOAPBodyElement</code>
     *            object
     * @return the new <code>SOAPBodyElement</code> object
     * @exception SOAPException
     *                if a SOAP error occurs
     * @see SOAPBody#addBodyElement(javax.xml.namespace.QName)
     */
    public SOAPBodyElement addBodyElement(Name name) throws SOAPException;


    /**
     * Creates a new <code>SOAPBodyElement</code> object with the specified
     * QName and adds it to this <code>SOAPBody</code> object.
     *
     * <p>
     *  使用指定的QName创建一个新的<code> SOAPBodyElement </code>对象,并将其添加到此<code> SOAPBody </code>对象。
     * 
     * 
     * @param qname
     *            a <code>QName</code> object with the qname for the new
     *            <code>SOAPBodyElement</code> object
     * @return the new <code>SOAPBodyElement</code> object
     * @exception SOAPException
     *                if a SOAP error occurs
     * @see SOAPBody#addBodyElement(Name)
     * @since SAAJ 1.3
     */
    public SOAPBodyElement addBodyElement(QName qname) throws SOAPException;

    /**
     * Adds the root node of the DOM <code>{@link org.w3c.dom.Document}</code>
     * to this <code>SOAPBody</code> object.
     * <p>
     * Calling this method invalidates the <code>document</code> parameter.
     * The client application should discard all references to this <code>Document</code>
     * and its contents upon calling <code>addDocument</code>. The behavior
     * of an application that continues to use such references is undefined.
     *
     * <p>
     *  将DOM <code> {@ link org.w3c.dom.Document} </code>的根节点添加到此<code> SOAPBody </code>对象。
     * <p>
     *  调用此方法会使<code> document </code>参数无效。
     * 客户端应用程序应该在调用<code> addDocument </code>时丢弃对此<code> Document </code>及其内容的所有引用。继续使用此类引用的应用程序的行为是未定义的。
     * 
     * 
     * @param document
     *            the <code>Document</code> object whose root node will be
     *            added to this <code>SOAPBody</code>.
     * @return the <code>SOAPBodyElement</code> that represents the root node
     *         that was added.
     * @exception SOAPException
     *                if the <code>Document</code> cannot be added
     * @since SAAJ 1.2
     */
    public SOAPBodyElement addDocument(org.w3c.dom.Document document)
        throws SOAPException;

    /**
     * Creates a new DOM <code>{@link org.w3c.dom.Document}</code> and sets
     * the first child of this <code>SOAPBody</code> as it's document
     * element. The child <code>SOAPElement</code> is removed as part of the
     * process.
     *
     * <p>
     * 
     * @return the <code>{@link org.w3c.dom.Document}</code> representation
     *         of the <code>SOAPBody</code> content.
     *
     * @exception SOAPException
     *                if there is not exactly one child <code>SOAPElement</code> of the <code>
     *              <code>SOAPBody</code>.
     *
     * @since SAAJ 1.3
     */
    public org.w3c.dom.Document extractContentAsDocument()
        throws SOAPException;
}
