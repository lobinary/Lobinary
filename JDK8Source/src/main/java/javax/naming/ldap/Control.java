/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2010, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.ldap;

/**
  * This interface represents an LDAPv3 control as defined in
  * <A HREF="http://www.ietf.org/rfc/rfc2251.txt">RFC 2251</A>.
  *<p>
  * The LDAPv3 protocol uses controls to send and receive additional data
  * to affect the behavior of predefined operations.
  * Controls can be sent along with any LDAP operation to the server.
  * These are referred to as <em>request controls</em>. For example, a
  * "sort" control can be sent with an LDAP search operation to
  * request that the results be returned in a particular order.
  * Solicited and unsolicited controls can also be returned with
  * responses from the server. Such controls are referred to as
  * <em>response controls</em>. For example, an LDAP server might
  * define a special control to return change notifications.
  *<p>
  * This interface is used to represent both request and response controls.
  *
  * <p>
  *  此接口表示在<A HREF="http://www.ietf.org/rfc/rfc2251.txt"> RFC 2251 </A>中定义的LDAPv3控件。
  * p>
  *  LDAPv3协议使用控件发送和接收附加数据,以影响预定义操作的行为。控件可以与任何LDAP操作一起发送到服务器。这些被称为<em>请求控件</em>。
  * 例如,可以使用LDAP搜索操作发送"排序"控件,以请求以特定顺序返回结果。请求的和非请求的控件也可以返回与服务器的响应。这样的控制被称为<em>响应控制</em>。
  * 例如,LDAP服务器可能定义一个特殊控件以返回更改通知。
  * p>
  *  此接口用于表示请求和响应控件。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  * @author Vincent Ryan
  *
  * @see ControlFactory
  * @since 1.3
  */
public interface Control extends java.io.Serializable {
    /**
      * Indicates a critical control.
      * The value of this constant is <tt>true</tt>.
      * <p>
      *  表示关键控件。此常数的值为<tt> true </tt>。
      * 
      */
    public static final boolean CRITICAL = true;

    /**
      * Indicates a non-critical control.
      * The value of this constant is <tt>false</tt>.
      * <p>
      *  表示非关键控件。此常数的值为<tt> false </tt>。
      * 
      */
    public static final boolean NONCRITICAL = false;

    /**
      * Retrieves the object identifier assigned for the LDAP control.
      *
      * <p>
      *  检索为LDAP控件分配的对象标识符。
      * 
      * 
      * @return The non-null object identifier string.
      */
    public String getID();

    /**
      * Determines the criticality of the LDAP control.
      * A critical control must not be ignored by the server.
      * In other words, if the server receives a critical control
      * that it does not support, regardless of whether the control
      * makes sense for the operation, the operation will not be performed
      * and an <tt>OperationNotSupportedException</tt> will be thrown.
      * <p>
      * 确定LDAP控制的关键性。服务器不能忽略关键控件。
      * 换句话说,如果服务器接收到它不支持的关键控制,则不管该控制对于该操作是否有意义,将不执行该操作,并且将抛出<tt> OperationNotSupportedException </tt>。
      * 
      * 
      * @return true if this control is critical; false otherwise.
      */
    public boolean isCritical();

    /**
      * Retrieves the ASN.1 BER encoded value of the LDAP control.
      * The result is the raw BER bytes including the tag and length of
      * the control's value. It does not include the controls OID or criticality.
      *
      * Null is returned if the value is absent.
      *
      * <p>
      *  检索LDAP控制的ASN.1 BER编码值。结果是原始BER字节包括标签和控件值的长度。它不包括控件OID或关键性。
      * 
      * 
      * @return A possibly null byte array representing the ASN.1 BER encoded
      *         value of the LDAP control.
      */
    public byte[] getEncodedValue();

    // static final long serialVersionUID = -591027748900004825L;
}
