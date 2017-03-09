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
 * A <code>SOAPBodyElement</code> object represents the contents in
 * a <code>SOAPBody</code> object.  The <code>SOAPFault</code> interface
 * is a <code>SOAPBodyElement</code> object that has been defined.
 * <P>
 * A new <code>SOAPBodyElement</code> object can be created and added
 * to a <code>SOAPBody</code> object with the <code>SOAPBody</code>
 * method <code>addBodyElement</code>. In the following line of code,
 * <code>sb</code> is a <code>SOAPBody</code> object, and
 * <code>myName</code> is a <code>Name</code> object.
 * <PRE>
 *    SOAPBodyElement sbe = sb.addBodyElement(myName);
 * </PRE>
 * <p>
 *  <code> SOAPBodyElement </code>对象表示<code> SOAPBody </code>对象中的内容。
 *  <code> SOAPFault </code>接口是已定义的<code> SOAPBodyElement </code>对象。
 * <P>
 *  可以使用<code> SOAPBody </code>方法<code> addBodyElement </code>创建一个新的<code> SOAPBodyElement </code>对象并将其添
 * 加到<code> SOAPBody </code>对象中。
 */
public interface SOAPBodyElement extends SOAPElement {
}
