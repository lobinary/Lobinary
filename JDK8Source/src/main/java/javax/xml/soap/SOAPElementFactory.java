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

/**
 * <code>SOAPElementFactory</code> is a factory for XML fragments that
 * will eventually end up in the SOAP part. These fragments
 * can be inserted as children of the <code>SOAPHeader</code> or
 * <code>SOAPBody</code> or <code>SOAPEnvelope</code>.
 *
 * <p>Elements created using this factory do not have the properties
 * of an element that lives inside a SOAP header document. These
 * elements are copied into the XML document tree when they are
 * inserted.
 * <p>
 *  <code> SOAPElementFactory </code>是一个XML片段的工厂,它最终将在SOAP部分。
 * 这些片段可以作为<code> SOAPHeader </code>或<code> SOAPBody </code>或<code> SOAPEnvelope </code>的子代插入。
 * 
 *  <p>使用此工厂创建的元素不具有位于SOAP头文档中的元素的属性。插入这些元素时,它们将被复制到XML文档树中。
 * 
 * 
 * @deprecated - Use <code>javax.xml.soap.SOAPFactory</code> for creating SOAPElements.
 * @see javax.xml.soap.SOAPFactory
 */
public class SOAPElementFactory {

    private SOAPFactory soapFactory;

    private SOAPElementFactory(SOAPFactory soapFactory) {
        this.soapFactory = soapFactory;
    }

    /**
     * Create a <code>SOAPElement</code> object initialized with the
     * given <code>Name</code> object.
     *
     * <p>
     *  创建用给定的<code> Name </code>对象初始化的<code> SOAPElement </code>对象。
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
     *
     * @deprecated Use
     * javax.xml.soap.SOAPFactory.createElement(javax.xml.soap.Name)
     * instead
     *
     * @see javax.xml.soap.SOAPFactory#createElement(javax.xml.soap.Name)
     * @see javax.xml.soap.SOAPFactory#createElement(javax.xml.namespace.QName)
     */
    public SOAPElement create(Name name) throws SOAPException {
        return soapFactory.createElement(name);
    }

    /**
     * Create a <code>SOAPElement</code> object initialized with the
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
     *
     * @deprecated Use
     * javax.xml.soap.SOAPFactory.createElement(String localName) instead
     *
     * @see javax.xml.soap.SOAPFactory#createElement(java.lang.String)
     */
    public SOAPElement create(String localName) throws SOAPException {
        return soapFactory.createElement(localName);
    }

    /**
     * Create a new <code>SOAPElement</code> object with the given
     * local name, prefix and uri.
     *
     * <p>
     *  使用给定的本地名称,前缀和uri创建一个新的<code> SOAPElement </code>对象。
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
     *
     * @deprecated Use
     * javax.xml.soap.SOAPFactory.createElement(String localName,
     *                      String prefix,
     *                      String uri)
     * instead
     *
     * @see javax.xml.soap.SOAPFactory#createElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public SOAPElement create(String localName, String prefix, String uri)
        throws SOAPException {
        return soapFactory.createElement(localName, prefix, uri);
    }

    /**
     * Creates a new instance of <code>SOAPElementFactory</code>.
     *
     * <p>
     *  创建<code> SOAPElementFactory </code>的新实例。
     * 
     * @return a new instance of a <code>SOAPElementFactory</code>
     *
     * @exception SOAPException if there was an error creating the
     *            default <code>SOAPElementFactory</code>
     */
    public static SOAPElementFactory newInstance() throws SOAPException {
        try {
            return new SOAPElementFactory(SOAPFactory.newInstance());
        } catch (Exception ex) {
            throw new SOAPException(
                "Unable to create SOAP Element Factory: " + ex.getMessage());
        }
    }
}
