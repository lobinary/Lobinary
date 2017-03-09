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
 * An object representing an element of a SOAP message that is allowed but not
 * specifically prescribed by a SOAP specification. This interface serves as the
 * base interface for those objects that are specifically prescribed by a SOAP
 * specification.
 * <p>
 * Methods in this interface that are required to return SAAJ specific objects
 * may "silently" replace nodes in the tree as required to successfully return
 * objects of the correct type. See {@link #getChildElements()} and
 * {@link <a HREF="package-summary.html#package_description">javax.xml.soap<a>}
 * for details.
 * <p>
 *  表示SOAP消息的元素的对象,SOAP消息的元素允许但不是由SOAP规范特别规定。此接口用作SOAP规范特别规定的那些对象的基本接口。
 * <p>
 *  返回SAAJ特定对象所需的此接口中的方法可以根据需要"静默"替换树中的节点,以成功返回正确类型的对象。
 * 有关详情,请参阅{@link #getChildElements()}和{@link <a HREF="package-summary.html#package_description"> javax.xml.soap <a>}
 * 。
 *  返回SAAJ特定对象所需的此接口中的方法可以根据需要"静默"替换树中的节点,以成功返回正确类型的对象。
 * 
 */
public interface SOAPElement extends Node, org.w3c.dom.Element {

    /**
     * Creates a new <code>SOAPElement</code> object initialized with the
     * given <code>Name</code> object and adds the new element to this
     * <code>SOAPElement</code> object.
     * <P>
     * This method may be deprecated in a future release of SAAJ in favor of
     * addChildElement(javax.xml.namespace.QName)
     *
     * <p>
     *  创建用给定的<code> Name </code>对象初始化的新的<code> SOAPElement </code>对象,并将新的元素添加到此<code> SOAPElement </code>对象
     * 。
     * <P>
     *  此方法可能在SAAJ的未来版本中被弃用,有利于addChildElement(javax.xml.namespace.QName)
     * 
     * 
     * @param name a <code>Name</code> object with the XML name for the
     *        new element
     *
     * @return the new <code>SOAPElement</code> object that was created
     * @exception SOAPException if there is an error in creating the
     *                          <code>SOAPElement</code> object
     * @see SOAPElement#addChildElement(javax.xml.namespace.QName)
     */
    public SOAPElement addChildElement(Name name) throws SOAPException;

    /**
     * Creates a new <code>SOAPElement</code> object initialized with the given
     * <code>QName</code> object and adds the new element to this <code>SOAPElement</code>
     *  object. The  <i>namespace</i>, <i>localname</i> and <i>prefix</i> of the new
     * <code>SOAPElement</code> are all taken  from the <code>qname</code> argument.
     *
     * <p>
     *  创建使用给定的<code> QName </code>对象初始化的新<code> SOAPElement </code>对象,并将新的元素添加到此<code> SOAPElement </code>对
     * 象。
     * 新的<code> SOAPElement </code>的<i>命名空间</i>,<i> localname </i>和<i>前缀</i>都取自<code> qname </code >参数。
     * 
     * 
     * @param qname a <code>QName</code> object with the XML name for the
     *        new element
     *
     * @return the new <code>SOAPElement</code> object that was created
     * @exception SOAPException if there is an error in creating the
     *                          <code>SOAPElement</code> object
     * @see SOAPElement#addChildElement(Name)
     * @since SAAJ 1.3
     */
    public SOAPElement addChildElement(QName qname) throws SOAPException;

    /**
     * Creates a new <code>SOAPElement</code> object initialized with the
     * specified local name and adds the new element to this
     * <code>SOAPElement</code> object.
     * The new  <code>SOAPElement</code> inherits any in-scope default namespace.
     *
     * <p>
     * 创建使用指定的本地名称初始化的新的<code> SOAPElement </code>对象,并将新的元素添加到此<code> SOAPElement </code>对象。
     * 新的<code> SOAPElement </code>继承任何范围内默认命名空间。
     * 
     * 
     * @param localName a <code>String</code> giving the local name for
     *          the element
     * @return the new <code>SOAPElement</code> object that was created
     * @exception SOAPException if there is an error in creating the
     *                          <code>SOAPElement</code> object
     */
    public SOAPElement addChildElement(String localName) throws SOAPException;

    /**
     * Creates a new <code>SOAPElement</code> object initialized with the
     * specified local name and prefix and adds the new element to this
     * <code>SOAPElement</code> object.
     *
     * <p>
     *  创建使用指定的本地名称和前缀初始化的新<> SOAPElement </code>对象,并将新的元素添加到此<code> SOAPElement </code>对象。
     * 
     * 
     * @param localName a <code>String</code> giving the local name for
     *        the new element
     * @param prefix a <code>String</code> giving the namespace prefix for
     *        the new element
     *
     * @return the new <code>SOAPElement</code> object that was created
     * @exception SOAPException if the <code>prefix</code> is not valid in the
     *         context of this <code>SOAPElement</code> or  if there is an error in creating the
     *                          <code>SOAPElement</code> object
     */
    public SOAPElement addChildElement(String localName, String prefix)
        throws SOAPException;

    /**
     * Creates a new <code>SOAPElement</code> object initialized with the
     * specified local name, prefix, and URI and adds the new element to this
     * <code>SOAPElement</code> object.
     *
     * <p>
     *  创建使用指定的本地名称,前缀和URI初始化的新的<code> SOAPElement </code>对象,并将新的元素添加到此<code> SOAPElement </code>对象。
     * 
     * 
     * @param localName a <code>String</code> giving the local name for
     *        the new element
     * @param prefix a <code>String</code> giving the namespace prefix for
     *        the new element
     * @param uri a <code>String</code> giving the URI of the namespace
     *        to which the new element belongs
     *
     * @return the new <code>SOAPElement</code> object that was created
     * @exception SOAPException if there is an error in creating the
     *                          <code>SOAPElement</code> object
     */
    public SOAPElement addChildElement(String localName, String prefix,
                                       String uri)
        throws SOAPException;

    /**
     * Add a <code>SOAPElement</code> as a child of this
     * <code>SOAPElement</code> instance. The <code>SOAPElement</code>
     * is expected to be created by a
     * <code>SOAPFactory</code>. Callers should not rely on the
     * element instance being added as is into the XML
     * tree. Implementations could end up copying the content
     * of the <code>SOAPElement</code> passed into an instance of
     * a different <code>SOAPElement</code> implementation. For
     * instance if <code>addChildElement()</code> is called on a
     * <code>SOAPHeader</code>, <code>element</code> will be copied
     * into an instance of a <code>SOAPHeaderElement</code>.
     *
     * <P>The fragment rooted in <code>element</code> is either added
     * as a whole or not at all, if there was an error.
     *
     * <P>The fragment rooted in <code>element</code> cannot contain
     * elements named "Envelope", "Header" or "Body" and in the SOAP
     * namespace. Any namespace prefixes present in the fragment
     * should be fully resolved using appropriate namespace
     * declarations within the fragment itself.
     *
     * <p>
     *  将<code> SOAPElement </code>添加为此<> SOAPElement </code>实例的子级。
     *  <code> SOAPElement </code>应该由<code> SOAPFactory </code>创建。调用者不应该依赖被添加到XML树中的元素实例。
     * 实现可能最终复制传递到不同<code> SOAPElement </code>实现的实例的<code> SOAPElement </code>内容。
     * 例如,如果<code> SOAPHeader </code>调用<code> addChildElement()</code>,则<code>元素</code>将被复制到<code> SOAPHeade
     * rElement </code> 。
     * 实现可能最终复制传递到不同<code> SOAPElement </code>实现的实例的<code> SOAPElement </code>内容。
     * 
     *  <P>根源于<code>元素</code>中的片段或者作为一个整体添加,或者根本不添加,如果有错误。
     * 
     * <p> <code>元素</code>中的片段不能包含名为"Envelope","Header"或"Body"和SOAP命名空间中的元素。
     * 片段中存在的任何命名空间前缀应该使用片段本身中适当的命名空间声明完全解析。
     * 
     * 
     * @param element the <code>SOAPElement</code> to be added as a
     *                new child
     *
     * @exception SOAPException if there was an error in adding this
     *                          element as a child
     *
     * @return an instance representing the new SOAP element that was
     *         actually added to the tree.
     */
    public SOAPElement addChildElement(SOAPElement element)
        throws SOAPException;

    /**
     * Detaches all children of this <code>SOAPElement</code>.
     * <p>
     * This method is useful for rolling back the construction of partially
     * completed <code>SOAPHeaders</code> and <code>SOAPBodys</code> in
     * preparation for sending a fault when an error condition is detected. It
     * is also useful for recycling portions of a document within a SOAP
     * message.
     *
     * <p>
     *  分离此<code> SOAPElement </code>的所有子级。
     * <p>
     *  此方法对于回滚部分完成的<code> SOAPHeaders </code>和<code> SOAPBodys </code>的结构有用,以准备在检测到错误条件时发送故障。
     * 它还可用于在SOAP消息中回收文档的部分。
     * 
     * 
     * @since SAAJ 1.2
     */
    public abstract void removeContents();

    /**
     * Creates a new <code>Text</code> object initialized with the given
     * <code>String</code> and adds it to this <code>SOAPElement</code> object.
     *
     * <p>
     *  创建用给定<code> String </code>初始化的一个新的<code> Text </code>对象,并将其添加到此<code> SOAPElement </code>对象。
     * 
     * 
     * @param text a <code>String</code> object with the textual content to be added
     *
     * @return the <code>SOAPElement</code> object into which
     *         the new <code>Text</code> object was inserted
     * @exception SOAPException if there is an error in creating the
     *                    new <code>Text</code> object or if it is not legal to
     *                      attach it as a child to this
     *                      <code>SOAPElement</code>
     */
    public SOAPElement addTextNode(String text) throws SOAPException;

    /**
     * Adds an attribute with the specified name and value to this
     * <code>SOAPElement</code> object.
     *
     * <p>
     *  将具有指定名称和值的属性添加到此<code> SOAPElement </code>对象。
     * 
     * 
     * @param name a <code>Name</code> object with the name of the attribute
     * @param value a <code>String</code> giving the value of the attribute
     * @return the <code>SOAPElement</code> object into which the attribute was
     *         inserted
     *
     * @exception SOAPException if there is an error in creating the
     *                          Attribute, or it is invalid to set
                                an attribute with <code>Name</code>
                                 <code>name</code> on this SOAPElement.
     * @see SOAPElement#addAttribute(javax.xml.namespace.QName, String)
     */
    public SOAPElement addAttribute(Name name, String value)
        throws SOAPException;

    /**
     * Adds an attribute with the specified name and value to this
     * <code>SOAPElement</code> object.
     *
     * <p>
     *  将具有指定名称和值的属性添加到此<code> SOAPElement </code>对象。
     * 
     * 
     * @param qname a <code>QName</code> object with the name of the attribute
     * @param value a <code>String</code> giving the value of the attribute
     * @return the <code>SOAPElement</code> object into which the attribute was
     *         inserted
     *
     * @exception SOAPException if there is an error in creating the
     *                          Attribute, or it is invalid to set
                                an attribute with <code>QName</code>
                                <code>qname</code> on this SOAPElement.
     * @see SOAPElement#addAttribute(Name, String)
     * @since SAAJ 1.3
     */
    public SOAPElement addAttribute(QName qname, String value)
        throws SOAPException;

    /**
     * Adds a namespace declaration with the specified prefix and URI to this
     * <code>SOAPElement</code> object.
     *
     * <p>
     *  将具有指定前缀和URI的命名空间声明添加到此<code> SOAPElement </code>对象。
     * 
     * 
     * @param prefix a <code>String</code> giving the prefix of the namespace
     * @param uri a <code>String</code> giving the uri of the namespace
     * @return the <code>SOAPElement</code> object into which this
     *          namespace declaration was inserted.
     *
     * @exception SOAPException if there is an error in creating the
     *                          namespace
     */
    public SOAPElement addNamespaceDeclaration(String prefix, String uri)
        throws SOAPException;

    /**
     * Returns the value of the attribute with the specified name.
     *
     * <p>
     *  返回具有指定名称的属性的值。
     * 
     * 
     * @param name a <code>Name</code> object with the name of the attribute
     * @return a <code>String</code> giving the value of the specified
     *         attribute, Null if there is no such attribute
     * @see SOAPElement#getAttributeValue(javax.xml.namespace.QName)
     */
    public String getAttributeValue(Name name);

    /**
     * Returns the value of the attribute with the specified qname.
     *
     * <p>
     *  返回具有指定qname的属性的值。
     * 
     * 
     * @param qname a <code>QName</code> object with the qname of the attribute
     * @return a <code>String</code> giving the value of the specified
     *         attribute, Null if there is no such attribute
     * @see SOAPElement#getAttributeValue(Name)
     * @since SAAJ 1.3
     */
    public String getAttributeValue(QName qname);

    /**
     * Returns an <code>Iterator</code> over all of the attribute
     * <code>Name</code> objects in this
     * <code>SOAPElement</code> object. The iterator can be used to get
     * the attribute names, which can then be passed to the method
     * <code>getAttributeValue</code> to retrieve the value of each
     * attribute.
     *
     * <p>
     *  在此<code> SOAPElement </code>对象中的所有属性<code> Name </code>上返回<code>迭代器</code>。
     * 迭代器可以用于获取属性名称,然后可以将其传递给方法<code> getAttributeValue </code>来检索每个属性的值。
     * 
     * 
     * @see SOAPElement#getAllAttributesAsQNames()
     * @return an iterator over the names of the attributes
     */
    public Iterator getAllAttributes();

    /**
     * Returns an <code>Iterator</code> over all of the attributes
     * in this <code>SOAPElement</code>  as <code>QName</code> objects.
     * The iterator can be used to get the attribute QName, which can then
     * be passed to the method <code>getAttributeValue</code> to retrieve
     * the value of each attribute.
     *
     * <p>
     * 对<code> SOAPElement </code>中的所有属性返回<code> Iterator </code>作为<code> QName </code>对象。
     * 迭代器可以用于获取属性QName,然后可以将其传递给方法<code> getAttributeValue </code>以检索每个属性的值。
     * 
     * 
     * @return an iterator over the QNames of the attributes
     * @see SOAPElement#getAllAttributes()
     * @since SAAJ 1.3
     */
    public Iterator getAllAttributesAsQNames();


    /**
     * Returns the URI of the namespace that has the given prefix.
     *
     * <p>
     *  返回具有给定前缀的命名空间的URI。
     * 
     * 
     * @param prefix a <code>String</code> giving the prefix of the namespace
     *        for which to search
     * @return a <code>String</code> with the uri of the namespace that has
     *        the given prefix
     */
    public String getNamespaceURI(String prefix);

    /**
     * Returns an <code>Iterator</code> over the namespace prefix
     * <code>String</code>s declared by this element. The prefixes returned by
     * this iterator can be passed to the method
     * <code>getNamespaceURI</code> to retrieve the URI of each namespace.
     *
     * <p>
     *  在此元素声明的命名空间前缀<code> String </code>之上返回<code>迭代器</code>。
     * 此迭代器返回的前缀可以传递到方法<code> getNamespaceURI </code>以检索每个命名空间的URI。
     * 
     * 
     * @return an iterator over the namespace prefixes in this
     *         <code>SOAPElement</code> object
     */
    public Iterator getNamespacePrefixes();

    /**
     * Returns an <code>Iterator</code> over the namespace prefix
     * <code>String</code>s visible to this element. The prefixes returned by
     * this iterator can be passed to the method
     * <code>getNamespaceURI</code> to retrieve the URI of each namespace.
     *
     * <p>
     *  在此元素可见的命名空间前缀<code> String </code>之上返回<code>迭代器</code>。
     * 此迭代器返回的前缀可以传递到方法<code> getNamespaceURI </code>以检索每个命名空间的URI。
     * 
     * 
     * @return an iterator over the namespace prefixes are within scope of this
     *         <code>SOAPElement</code> object
     *
     * @since SAAJ 1.2
     */
    public Iterator getVisibleNamespacePrefixes();

    /**
     * Creates a <code>QName</code> whose namespace URI is the one associated
     * with the parameter, <code>prefix</code>, in the context of this
     * <code>SOAPElement</code>. The remaining elements of the new
     * <code>QName</code> are taken directly from the parameters,
     * <code>localName</code> and <code>prefix</code>.
     *
     * <p>
     *  在此<code> SOAPElement </code>的上下文中创建一个<code> QName </code>,其名称空间URI是与参数<code> prefix </code>新<code> Q
     * Name </code>的其余元素直接取自参数<code> localName </code>和<code>前缀</code>。
     * 
     * 
     * @param localName
     *          a <code>String</code> containing the local part of the name.
     * @param prefix
     *          a <code>String</code> containing the prefix for the name.
     *
     * @return a <code>QName</code> with the specified <code>localName</code>
     *          and <code>prefix</code>, and with a namespace that is associated
     *          with the <code>prefix</code> in the context of this
     *          <code>SOAPElement</code>. This namespace will be the same as
     *          the one that would be returned by
     *          <code>{@link #getNamespaceURI(String)}</code> if it were given
     *          <code>prefix</code> as it's parameter.
     *
     * @exception SOAPException if the <code>QName</code> cannot be created.
     *
     * @since SAAJ 1.3
     */
    public QName createQName(String localName, String prefix)
        throws SOAPException;
    /**
     * Returns the name of this <code>SOAPElement</code> object.
     *
     * <p>
     *  返回此<> SOAPElement </code>对象的名称。
     * 
     * 
     * @return a <code>Name</code> object with the name of this
     *         <code>SOAPElement</code> object
     */
    public Name getElementName();

    /**
     * Returns the qname of this <code>SOAPElement</code> object.
     *
     * <p>
     *  返回此<code> SOAPElement </code>对象的qname。
     * 
     * 
     * @return a <code>QName</code> object with the qname of this
     *         <code>SOAPElement</code> object
     * @see SOAPElement#getElementName()
     * @since SAAJ 1.3
     */
    public QName getElementQName();

    /**
    * Changes the name of this <code>Element</code> to <code>newName</code> if
    * possible. SOAP Defined elements such as SOAPEnvelope, SOAPHeader, SOAPBody
    * etc. cannot have their names changed using this method. Any attempt to do
    * so will result in a  SOAPException being thrown.
    *<P>
    * Callers should not rely on the element instance being renamed as is.
    * Implementations could end up copying the content of the
    * <code>SOAPElement</code> to a renamed instance.
    *
    * <p>
    * 如果可能,请将此<code>元素</code>的名称更改为<code> newName </code>。
    *  SOAP定义的元素(例如SOAPEnvelope,SOAPHeader,SOAPBody等)不能使用此方法更改其名称。任何尝试这样做都会导致抛出SOAPException异常。
    * P>
    *  调用者不应该依赖于被重命名的元素实例。实现最终可能会将<code> SOAPElement </code>的内容复制到重命名的实例。
    * 
    * 
    * @param newName the new name for the <code>Element</code>.
    *
    * @exception SOAPException if changing the name of this <code>Element</code>
    *                          is not allowed.
    * @return The renamed Node
    *
    * @since SAAJ 1.3
    */
   public SOAPElement setElementQName(QName newName) throws SOAPException;

   /**
     * Removes the attribute with the specified name.
     *
     * <p>
     *  删除具有指定名称的属性。
     * 
     * 
     * @param name the <code>Name</code> object with the name of the
     *        attribute to be removed
     * @return <code>true</code> if the attribute was
     *         removed successfully; <code>false</code> if it was not
     * @see SOAPElement#removeAttribute(javax.xml.namespace.QName)
     */
    public boolean removeAttribute(Name name);

    /**
     * Removes the attribute with the specified qname.
     *
     * <p>
     *  删除具有指定qname的属性。
     * 
     * 
     * @param qname the <code>QName</code> object with the qname of the
     *        attribute to be removed
     * @return <code>true</code> if the attribute was
     *         removed successfully; <code>false</code> if it was not
     * @see SOAPElement#removeAttribute(Name)
     * @since SAAJ 1.3
     */
    public boolean removeAttribute(QName qname);

    /**
     * Removes the namespace declaration corresponding to the given prefix.
     *
     * <p>
     *  删除与给定前缀对应的命名空间声明。
     * 
     * 
     * @param prefix a <code>String</code> giving the prefix for which
     *        to search
     * @return <code>true</code> if the namespace declaration was
     *         removed successfully; <code>false</code> if it was not
     */
    public boolean removeNamespaceDeclaration(String prefix);

    /**
     * Returns an <code>Iterator</code> over all the immediate child
     * {@link Node}s of this element. This includes <code>javax.xml.soap.Text</code>
     * objects as well as <code>SOAPElement</code> objects.
     * <p>
     * Calling this method may cause child <code>Element</code>,
     * <code>SOAPElement</code> and <code>org.w3c.dom.Text</code> nodes to be
     * replaced by <code>SOAPElement</code>, <code>SOAPHeaderElement</code>,
     * <code>SOAPBodyElement</code> or <code>javax.xml.soap.Text</code> nodes as
     * appropriate for the type of this parent node. As a result the calling
     * application must treat any existing references to these child nodes that
     * have been obtained through DOM APIs as invalid and either discard them or
     * refresh them with the values returned by this <code>Iterator</code>. This
     * behavior can be avoided by calling the equivalent DOM APIs. See
     * {@link <a HREF="package-summary.html#package_description">javax.xml.soap<a>}
     * for more details.
     *
     * <p>
     *  在此元素的所有直接子{@link Node}上返回<code>迭代器</code>。
     * 这包括<code> javax.xml.soap.Text </code>对象以及<code> SOAPElement </code>对象。
     * <p>
     * 调用此方法可能会导致<code> Element </code>,<code> SOAPElement </code>和<code> org.w3c.dom.Text </code>节点被<code> 
     * SOAPElement </code >,<code> SOAPHeaderElement </code>,<code> SOAPBodyElement </code>或<code> javax.xml
     * .soap.Text </code>节点。
     * 因此,调用应用程序必须将通过DOM API获得的对这些子节点的任何现有引用视为无效,并丢弃它们或使用此<code> Iterator </code>返回的值刷新它们。
     * 通过调用等效DOM API可以避免此行为。
     * 有关详情,请参见{@link <a HREF="package-summary.html#package_description"> javax.xml.soap <a>}。
     * 
     * 
     * @return an iterator with the content of this <code>SOAPElement</code>
     *         object
     */
    public Iterator getChildElements();

    /**
     * Returns an <code>Iterator</code> over all the immediate child
     * {@link Node}s of this element with the specified name. All of these
     * children will be <code>SOAPElement</code> nodes.
     * <p>
     * Calling this method may cause child <code>Element</code>,
     * <code>SOAPElement</code> and <code>org.w3c.dom.Text</code> nodes to be
     * replaced by <code>SOAPElement</code>, <code>SOAPHeaderElement</code>,
     * <code>SOAPBodyElement</code> or <code>javax.xml.soap.Text</code> nodes as
     * appropriate for the type of this parent node. As a result the calling
     * application must treat any existing references to these child nodes that
     * have been obtained through DOM APIs as invalid and either discard them or
     * refresh them with the values returned by this <code>Iterator</code>. This
     * behavior can be avoided by calling the equivalent DOM APIs. See
     * {@link <a HREF="package-summary.html#package_description">javax.xml.soap<a>}
     * for more details.
     *
     * <p>
     *  在具有指定名称的此元素的所有直接子{{link}}上返回<code>迭代器</code>。所有这些孩子都将是<code> SOAPElement </code>节点。
     * <p>
     * 调用此方法可能会导致<code> Element </code>,<code> SOAPElement </code>和<code> org.w3c.dom.Text </code>节点被<code> 
     * SOAPElement </code >,<code> SOAPHeaderElement </code>,<code> SOAPBodyElement </code>或<code> javax.xml
     * .soap.Text </code>节点。
     * 因此,调用应用程序必须将通过DOM API获得的对这些子节点的任何现有引用视为无效,并丢弃它们或使用此<code> Iterator </code>返回的值刷新它们。
     * 通过调用等效DOM API可以避免此行为。
     * 有关详情,请参见{@link <a HREF="package-summary.html#package_description"> javax.xml.soap <a>}。
     * 
     * 
     * @param name a <code>Name</code> object with the name of the child
     *        elements to be returned
     *
     * @return an <code>Iterator</code> object over all the elements
     *         in this <code>SOAPElement</code> object with the
     *         specified name
     * @see SOAPElement#getChildElements(javax.xml.namespace.QName)
     */
    public Iterator getChildElements(Name name);

    /**
     * Returns an <code>Iterator</code> over all the immediate child
     * {@link Node}s of this element with the specified qname. All of these
     * children will be <code>SOAPElement</code> nodes.
     * <p>
     * Calling this method may cause child <code>Element</code>,
     * <code>SOAPElement</code> and <code>org.w3c.dom.Text</code> nodes to be
     * replaced by <code>SOAPElement</code>, <code>SOAPHeaderElement</code>,
     * <code>SOAPBodyElement</code> or <code>javax.xml.soap.Text</code> nodes as
     * appropriate for the type of this parent node. As a result the calling
     * application must treat any existing references to these child nodes that
     * have been obtained through DOM APIs as invalid and either discard them or
     * refresh them with the values returned by this <code>Iterator</code>. This
     * behavior can be avoided by calling the equivalent DOM APIs. See
     * {@link <a HREF="package-summary.html#package_description">javax.xml.soap<a>}
     * for more details.
     *
     * <p>
     *  在具有指定qname的此元素的所有直接子元素{@link Node}中返回<code>迭代器</code>。所有这些孩子都将是<code> SOAPElement </code>节点。
     * <p>
     * 调用此方法可能会导致<code> Element </code>,<code> SOAPElement </code>和<code> org.w3c.dom.Text </code>节点被<code> 
     * SOAPElement </code >,<code> SOAPHeaderElement </code>,<code> SOAPBodyElement </code>或<code> javax.xml
     * .soap.Text </code>节点。
     * 因此,调用应用程序必须将通过DOM API获得的对这些子节点的任何现有引用视为无效,并丢弃它们或使用此<code> Iterator </code>返回的值刷新它们。
     * 通过调用等效DOM API可以避免此行为。
     * 有关详情,请参见{@link <a HREF="package-summary.html#package_description"> javax.xml.soap <a>}。
     * 
     * @param qname a <code>QName</code> object with the qname of the child
     *        elements to be returned
     *
     * @return an <code>Iterator</code> object over all the elements
     *         in this <code>SOAPElement</code> object with the
     *         specified qname
     * @see SOAPElement#getChildElements(Name)
     * @since SAAJ 1.3
     */
    public Iterator getChildElements(QName qname);

    /**
     * Sets the encoding style for this <code>SOAPElement</code> object
     * to one specified.
     *
     * <p>
     * 
     * 
     * @param encodingStyle a <code>String</code> giving the encoding style
     *
     * @exception IllegalArgumentException if there was a problem in the
     *            encoding style being set.
     * @exception SOAPException if setting the encodingStyle is invalid for this SOAPElement.
     * @see #getEncodingStyle
     */
    public void setEncodingStyle(String encodingStyle)
        throws SOAPException;
    /**
     * Returns the encoding style for this <code>SOAPElement</code> object.
     *
     * <p>
     *  将<code> SOAPElement </code>对象的编码风格设置为指定的一种。
     * 
     * 
     * @return a <code>String</code> giving the encoding style
     *
     * @see #setEncodingStyle
     */
    public String getEncodingStyle();
}
