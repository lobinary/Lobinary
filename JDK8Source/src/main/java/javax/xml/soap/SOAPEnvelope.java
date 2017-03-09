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
 * The container for the SOAPHeader and SOAPBody portions of a
 * <code>SOAPPart</code> object. By default, a <code>SOAPMessage</code>
 * object is created with a <code>SOAPPart</code> object that has a
 * <code>SOAPEnvelope</code> object. The <code>SOAPEnvelope</code> object
 * by default has an empty <code>SOAPBody</code> object and an empty
 * <code>SOAPHeader</code> object.  The <code>SOAPBody</code> object is
 * required, and the <code>SOAPHeader</code> object, though
 * optional, is used in the majority of cases. If the
 * <code>SOAPHeader</code> object is not needed, it can be deleted,
 * which is shown later.
 * <P>
 * A client can access the <code>SOAPHeader</code> and <code>SOAPBody</code>
 * objects by calling the methods <code>SOAPEnvelope.getHeader</code> and
 * <code>SOAPEnvelope.getBody</code>. The
 * following  lines of code use these two methods after starting with
 * the <code>SOAPMessage</code>
 * object <i>message</i> to get the <code>SOAPPart</code> object <i>sp</i>,
 * which is then used to get the <code>SOAPEnvelope</code> object <i>se</i>.
 *
 * <PRE>
 *     SOAPPart sp = message.getSOAPPart();
 *     SOAPEnvelope se = sp.getEnvelope();
 *     SOAPHeader sh = se.getHeader();
 *     SOAPBody sb = se.getBody();
 * </PRE>
 * <P>
 * It is possible to change the body or header of a <code>SOAPEnvelope</code>
 * object by retrieving the current one, deleting it, and then adding
 * a new body or header. The <code>javax.xml.soap.Node</code> method
 * <code>deleteNode</code> deletes the XML element (node) on which it is
 * called.  For example, the following line of code deletes the
 * <code>SOAPBody</code> object that is retrieved by the method <code>getBody</code>.
 * <PRE>
 *      se.getBody().detachNode();
 * </PRE>
 * To create a <code>SOAPHeader</code> object to replace the one that was removed,
 * a client uses
 * the method <code>SOAPEnvelope.addHeader</code>, which creates a new header and
 * adds it to the <code>SOAPEnvelope</code> object. Similarly, the method
 * <code>addBody</code> creates a new <code>SOAPBody</code> object and adds
 * it to the <code>SOAPEnvelope</code> object. The following code fragment
 * retrieves the current header, removes it, and adds a new one. Then
 * it retrieves the current body, removes it, and adds a new one.
 *
 * <PRE>
 *     SOAPPart sp = message.getSOAPPart();
 *     SOAPEnvelope se = sp.getEnvelope();
 *     se.getHeader().detachNode();
 *     SOAPHeader sh = se.addHeader();
 *     se.getBody().detachNode();
 *     SOAPBody sb = se.addBody();
 * </PRE>
 * It is an error to add a <code>SOAPBody</code> or <code>SOAPHeader</code>
 * object if one already exists.
 * <P>
 * The <code>SOAPEnvelope</code> interface provides three methods for creating
 * <code>Name</code> objects. One method creates <code>Name</code> objects with
 * a local name, a namespace prefix, and a namesapce URI. The second method creates
 * <code>Name</code> objects with a local name and a namespace prefix, and the third
 * creates <code>Name</code> objects with just a local name.  The following line of
 * code, in which <i>se</i> is a <code>SOAPEnvelope</code> object, creates a new
 * <code>Name</code> object with all three.
 * <PRE>
 *     Name name = se.createName("GetLastTradePrice", "WOMBAT",
 *                                "http://www.wombat.org/trader");
 * </PRE>
 * <p>
 *  SOAPPead </code>对象的SOAPHeader和SOAPBody部分的容器。
 * 默认情况下,使用具有<code> SOAPEnvelope </code>对象的<code> SOAPPart </code>对象创建<code> SOAPMessage </code>对象。
 * 默认情况下,<code> SOAPEnvelope </code>对象有一个空的<code> SOAPBody </code>对象和一个空的<code> SOAPHeader </code>对象。
 * 需要<code> SOAPBody </code>对象,并且在大多数情况下使用<code> SOAPHeader </code>对象(尽管是可选的)。
 * 如果不需要<code> SOAPHeader </code>对象,它可以被删除,稍后会显示。
 * <P>
 *  客户端可以通过调用<code> SOAPEnvelope.getHeader </code>和<code> SOAPEnvelope.getBody </code>方法访问<code> SOAPHea
 * der </code>和<code> SOAPBody </code>对象。
 * 以下代码行在开始使用<code> SOAPMessage </code>对象<i>消息</i>之后使用这两个方法来获取<code> SOAPPart </code>对象<i> ,然后用于获取<code>
 *  SOAPEnvelope </code>对象<i> </i>。
 * 
 * <PRE>
 *  SOAPPart sp = message.getSOAPPart(); SOAPEnvelope se = sp.getEnvelope(); SOAPHeader sh = se.getHeade
 * r(); SOAPBody sb = se.getBody();。
 * </PRE>
 * <P>
 * 可以通过检索当前的对象,删除它,然后添加一个新的主体或头来更改<code> SOAPEnvelope </code>对象的主体或头。
 *  <code> javax.xml.soap.Node </code>方法<code> deleteNode </code>删除被调用的XML元素(节点)。
 * 例如,以下代码行删除由方法<code> getBody </code>检索的<code> SOAPBody </code>对象。
 * <PRE>
 *  se.getBody()。detachNode();
 * </PRE>
 *  要创建<code> SOAPHeader </code>对象以替换已删除的对象,客户端将使用方法<code> SOAPEnvelope.addHeader </code>创建一个新标头并将其添加到<code>
 *  SOAPEnvelope </code>对象。
 * 类似地,方法<code> addBody </code>创建一个新的<code> SOAPBody </code>对象并将其添加到<code> SOAPEnvelope </code>对象。
 * 以下代码片段检索当前头,删除它,并添加一个新头。然后它检索当前实体,删除它,并添加一个新的。
 * 
 * <PRE>
 *  SOAPPart sp = message.getSOAPPart(); SOAPEnvelope se = sp.getEnvelope(); se.getHeader()。
 * detachNode(); SOAPHeader sh = se.addHeader(); se.getBody()。
 * detachNode(); SOAPBody sb = se.addBody();。
 * </PRE>
 *  添加<code> SOAPBody </code>或<code> SOAPHeader </code>对象(如果已存在)是一个错误。
 * <P>
 * <code> SOAPEnvelope </code>接口提供了三种方法来创建<code> Name </code>对象。
 * 一种方法创建具有本地名称,命名空间前缀和名称映射URI的<code> Name </code>对象。
 * 第二种方法创建具有本地名称和命名空间前缀的<code> Name </code>对象,第三种方法创建只具有本地名称的<code> Name </code>对象。
 * 下面的代码行,其中<i> </i>是<code> SOAPEnvelope </code>对象,创建一个新的<code> Name </code>对象。
 */
public interface SOAPEnvelope extends SOAPElement {

    /**
     * Creates a new <code>Name</code> object initialized with the
     * given local name, namespace prefix, and namespace URI.
     * <P>
     * This factory method creates <code>Name</code> objects for use in
     * the SOAP/XML document.
     *
     * <p>
     * <PRE>
     *  名称= se.createName("GetLastTradePrice","WOMBAT","http://www.wombat.org/trader");
     * </PRE>
     * 
     * @param localName a <code>String</code> giving the local name
     * @param prefix a <code>String</code> giving the prefix of the namespace
     * @param uri a <code>String</code> giving the URI of the namespace
     * @return a <code>Name</code> object initialized with the given
     *         local name, namespace prefix, and namespace URI
     * @throws SOAPException if there is a SOAP error
     */
    public abstract Name createName(String localName, String prefix,
                                    String uri)
        throws SOAPException;

    /**
     * Creates a new <code>Name</code> object initialized with the
     * given local name.
     * <P>
     * This factory method creates <code>Name</code> objects for use in
     * the SOAP/XML document.
     *
     * <p>
     *  创建使用给定本地名称,命名空间前缀和命名空间URI初始化的新<code> Name </code>对象。
     * <P>
     *  此工厂方法创建<code> Name </code>对象以在SOAP / XML文档中使用。
     * 
     * 
     * @param localName a <code>String</code> giving the local name
     * @return a <code>Name</code> object initialized with the given
     *         local name
     * @throws SOAPException if there is a SOAP error
     */
    public abstract Name createName(String localName)
        throws SOAPException;

    /**
     * Returns the <code>SOAPHeader</code> object for
     * this <code>SOAPEnvelope</code> object.
     * <P>
     * A new <code>SOAPMessage</code> object is by default created with a
     * <code>SOAPEnvelope</code> object that contains an empty
     * <code>SOAPHeader</code> object.  As a result, the method
     * <code>getHeader</code> will always return a <code>SOAPHeader</code>
     * object unless the header has been removed and a new one has not
     * been added.
     *
     * <p>
     *  创建用给定本地名称初始化的新<code> Name </code>对象。
     * <P>
     *  此工厂方法创建<code> Name </code>对象以在SOAP / XML文档中使用。
     * 
     * 
     * @return the <code>SOAPHeader</code> object or <code>null</code> if
     *         there is none
     * @exception SOAPException if there is a problem obtaining the
     *            <code>SOAPHeader</code> object
     */
    public SOAPHeader getHeader() throws SOAPException;

    /**
     * Returns the <code>SOAPBody</code> object associated with this
     * <code>SOAPEnvelope</code> object.
     * <P>
     * A new <code>SOAPMessage</code> object is by default created with a
     * <code>SOAPEnvelope</code> object that contains an empty
     * <code>SOAPBody</code> object.  As a result, the method
     * <code>getBody</code> will always return a <code>SOAPBody</code>
     * object unless the body has been removed and a new one has not
     * been added.
     *
     * <p>
     *  返回此<> SOAPEnvelope </code>对象的<code> SOAPHeader </code>对象。
     * <P>
     *  默认情况下,新的<code> SOAPMessage </code>对象使用包含空的<code> SOAPHeader </code>对象的<code> SOAPEnvelope </code>对象创
     * 建。
     * 因此,方法<code> getHeader </code>将始终返回一个<code> SOAPHeader </code>对象,除非标头已被删除,并且没有添加新的。
     * 
     * 
     * @return the <code>SOAPBody</code> object for this
     *         <code>SOAPEnvelope</code> object or <code>null</code>
     *         if there is none
     * @exception SOAPException if there is a problem obtaining the
     *            <code>SOAPBody</code> object
     */
    public SOAPBody getBody() throws SOAPException;
    /**
     * Creates a <code>SOAPHeader</code> object and sets it as the
     * <code>SOAPHeader</code> object for this <code>SOAPEnvelope</code>
     * object.
     * <P>
     * It is illegal to add a header when the envelope already
     * contains a header.  Therefore, this method should be called
     * only after the existing header has been removed.
     *
     * <p>
     *  返回与此<code> SOAPEnvelope </code>对象关联的<code> SOAPBody </code>对象。
     * <P>
     * 默认情况下,使用包含空的<code> SOAPBody </code>对象的<code> SOAPEnvelope </code>对象创建一个新的<code> SOAPMessage </code>对象
     * 。
     * 因此,方法<code> getBody </code>将始终返回<code> SOAPBody </code>对象,除非正文已被删除,并且没有添加新的。
     * 
     * 
     * @return the new <code>SOAPHeader</code> object
     *
     * @exception SOAPException if this
     *            <code>SOAPEnvelope</code> object already contains a
     *            valid <code>SOAPHeader</code> object
     */
    public SOAPHeader addHeader() throws SOAPException;
    /**
     * Creates a <code>SOAPBody</code> object and sets it as the
     * <code>SOAPBody</code> object for this <code>SOAPEnvelope</code>
     * object.
     * <P>
     * It is illegal to add a body when the envelope already
     * contains a body. Therefore, this method should be called
     * only after the existing body has been removed.
     *
     * <p>
     *  创建一个<code> SOAPHeader </code>对象,并将其设置为<code> SOAPEnvelope </code>对象的<code> SOAPHeader </code>对象。
     * <P>
     *  当信封已经包含标题时添加标题是非法的。因此,只有在删除现有头后才应调用此方法。
     * 
     * 
     * @return the new <code>SOAPBody</code> object
     *
     * @exception SOAPException if this
     *            <code>SOAPEnvelope</code> object already contains a
     *            valid <code>SOAPBody</code> object
     */
    public SOAPBody addBody() throws SOAPException;
}
