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
 * An object representing the contents in the SOAP header part of the
 * SOAP envelope.
 * The immediate children of a <code>SOAPHeader</code> object can
 * be represented only as <code>SOAPHeaderElement</code> objects.
 * <P>
 * A <code>SOAPHeaderElement</code> object can have other
 * <code>SOAPElement</code> objects as its children.
 * <p>
 *  表示SOAP包络的SOAP标头部分中的内容的对象。 <code> SOAPHeader </code>对象的直接子对象只能表示为<code> SOAPHeaderElement </code>对象。
 * <P>
 *  <code> SOAPHeaderElement </code>对象可以有其他<code> SOAPElement </code>对象作为其子对象。
 * 
 */
public interface SOAPHeaderElement extends SOAPElement {

    /**
     * Sets the actor associated with this <code>SOAPHeaderElement</code>
     * object to the specified actor. The default value of an actor is:
     *          <code>SOAPConstants.URI_SOAP_ACTOR_NEXT</code>
     * <P>
     * If this <code>SOAPHeaderElement</code> supports SOAP 1.2 then this call is
     * equivalent to {@link #setRole(String)}
     *
     * <p>
     *  将与此<code> SOAPHeaderElement </code>对象关联的actor设置为指定的actor。
     *  actor的默认值为：<code> SOAPConstants.URI_SOAP_ACTOR_NEXT </code>。
     * <P>
     *  如果这个<code> SOAPHeaderElement </code>支持SOAP 1.2,那么这个调用相当于{@link #setRole(String)}
     * 
     * 
     * @param  actorURI a <code>String</code> giving the URI of the actor
     *           to set
     *
     * @exception IllegalArgumentException if there is a problem in
     * setting the actor.
     *
     * @see #getActor
     */
    public void setActor(String actorURI);

    /**
     * Sets the <code>Role</code> associated with this <code>SOAPHeaderElement</code>
     * object to the specified <code>Role</code>.
     *
     * <p>
     *  将与此<code> SOAPHeaderElement </code>对象关联的<code> Role </code>设置为指定的<code> Role </code>。
     * 
     * 
     * @param uri - the URI of the <code>Role</code>
     *
     * @throws SOAPException if there is an error in setting the role
     *
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Role.
     *
     * @since SAAJ 1.3
     */
    public void setRole(String uri) throws SOAPException;

    /**
     * Returns the uri of the <i>actor</i> attribute of this
     * <code>SOAPHeaderElement</code>.
     *<P>
     * If this <code>SOAPHeaderElement</code> supports SOAP 1.2 then this call is
     * equivalent to {@link #getRole()}
     * <p>
     *  返回此<code> SOAPHeaderElement </code>的<i> actor </i>属性的uri。
     * P>
     *  如果这个<code> SOAPHeaderElement </code>支持SOAP 1.2,那么这个调用相当于{@link #getRole()}
     * 
     * 
     * @return  a <code>String</code> giving the URI of the actor
     * @see #setActor
     */
    public String getActor();

    /**
     * Returns the value of the <i>Role</i> attribute of this
     * <code>SOAPHeaderElement</code>.
     *
     * <p>
     *  返回此<code> SOAPHeaderElement </code>的<i> Role </i>属性的值。
     * 
     * 
     * @return a <code>String</code> giving the URI of the <code>Role</code>
     *
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Fault Role.
     *
     * @since SAAJ 1.3
     */
    public String getRole();

    /**
     * Sets the mustUnderstand attribute for this <code>SOAPHeaderElement</code>
     * object to be either true or false.
     * <P>
     * If the mustUnderstand attribute is on, the actor who receives the
     * <code>SOAPHeaderElement</code> must process it correctly. This
     * ensures, for example, that if the <code>SOAPHeaderElement</code>
     * object modifies the message, that the message is being modified correctly.
     *
     * <p>
     *  将此<code> SOAPHeaderElement </code>对象的mustUnderstand属性设置为true或false。
     * <P>
     * 如果mustUnderstand属性打开,接收<code> SOAPHeaderElement </code>的actor必须正确处理它。
     * 这确保,例如,如果<code> SOAPHeaderElement </code>对象修改消息,消息正在被正确修改。
     * 
     * 
     * @param mustUnderstand <code>true</code> to set the mustUnderstand
     *        attribute to true; <code>false</code> to set it to false
     *
     * @exception IllegalArgumentException if there is a problem in
     * setting the mustUnderstand attribute
     * @see #getMustUnderstand
     * @see #setRelay
     */
    public void setMustUnderstand(boolean mustUnderstand);

    /**
     * Returns the boolean value of the mustUnderstand attribute for this
     * <code>SOAPHeaderElement</code>.
     *
     * <p>
     *  返回此<code> SOAPHeaderElement </code>的mustUnderstand属性的布尔值。
     * 
     * 
     * @return <code>true</code> if the mustUnderstand attribute of this
     *        <code>SOAPHeaderElement</code> object is turned on; <code>false</code>
     *         otherwise
     */
    public boolean getMustUnderstand();

    /**
     * Sets the <i>relay</i> attribute for this <code>SOAPHeaderElement</code> to be
     * either true or false.
     * <P>
     * The SOAP relay attribute is set to true to indicate that the SOAP header
     * block must be relayed by any node that is targeted by the header block
     * but not actually process it. This attribute is ignored on header blocks
     * whose mustUnderstand attribute is set to true or that are targeted at
     * the ultimate reciever (which is the default). The default value of this
     * attribute is <code>false</code>.
     *
     * <p>
     *  将<code> SOAPHeaderElement </code>的<i> relay </i>属性设置为true或false。
     * <P>
     *  SOAP中继属性设置为true,表示SOAP标头块必须由标题块定向但不实际处理它的任何节点中继。
     * 此属性在mustUnderstand属性设置为true或以最终接收方(这是默认值)为目标的报头块上被忽略。此属性的默认值为<code> false </code>。
     * 
     * @param relay the new value of the <i>relay</i> attribute
     *
     * @exception SOAPException if there is a problem in setting the
     * relay attribute.
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Relay attribute.
     *
     * @see #setMustUnderstand
     * @see #getRelay
     *
     * @since SAAJ 1.3
     */
    public void setRelay(boolean relay) throws SOAPException;

    /**
     * Returns the boolean value of the <i>relay</i> attribute for this
     * <code>SOAPHeaderElement</code>
     *
     * <p>
     * 
     * 
     * @return <code>true</code> if the relay attribute is turned on;
     * <code>false</code> otherwise
     *
     * @exception UnsupportedOperationException if this message does not
     *      support the SOAP 1.2 concept of Relay attribute.
     *
     * @see #getMustUnderstand
     * @see #setRelay
     *
     * @since SAAJ 1.3
     */
    public boolean getRelay();
}
