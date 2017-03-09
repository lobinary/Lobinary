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
 * A container for <code>DetailEntry</code> objects. <code>DetailEntry</code>
 * objects give detailed error information that is application-specific and
 * related to the <code>SOAPBody</code> object that contains it.
 *<P>
 * A <code>Detail</code> object, which is part of a <code>SOAPFault</code>
 * object, can be retrieved using the method <code>SOAPFault.getDetail</code>.
 * The <code>Detail</code> interface provides two methods. One creates a new
 * <code>DetailEntry</code> object and also automatically adds it to
 * the <code>Detail</code> object. The second method gets a list of the
 * <code>DetailEntry</code> objects contained in a <code>Detail</code>
 * object.
 * <P>
 * The following code fragment, in which <i>sf</i> is a <code>SOAPFault</code>
 * object, gets its <code>Detail</code> object (<i>d</i>), adds a new
 * <code>DetailEntry</code> object to <i>d</i>, and then gets a list of all the
 * <code>DetailEntry</code> objects in <i>d</i>. The code also creates a
 * <code>Name</code> object to pass to the method <code>addDetailEntry</code>.
 * The variable <i>se</i>, used to create the <code>Name</code> object,
 * is a <code>SOAPEnvelope</code> object.
 * <PRE>
 *    Detail d = sf.getDetail();
 *    Name name = se.createName("GetLastTradePrice", "WOMBAT",
 *                                "http://www.wombat.org/trader");
 *    d.addDetailEntry(name);
 *    Iterator it = d.getDetailEntries();
 * </PRE>
 * <p>
 *  一个用于<code> DetailEntry </code>对象的容器。
 *  <code> DetailEntry </code>对象提供了特定于应用程序并与包含它的<code> SOAPBody </code>对象相关的详细错误信息。
 * P>
 *  可以使用方法<code> SOAPFault.getDetail </code>来检索作为<code> SOAPFault </code>对象一部分的<code> Detail </code>对象。
 *  <code> Detail </code>接口提供了两种方法。
 * 一个创建一个新的<code> DetailEntry </code>对象,并自动将其添加到<code> Detail </code>对象。
 * 第二个方法获取包含在<code> Detail </code>对象中的<code> DetailEntry </code>对象的列表。
 * <P>
 *  以下代码段(其中<i> sf </i>是<code> SOAPFault </code>对象)获取其<code> Detail </code>对象(<i> d </i>向<d> </i>新的<code>
 *  DetailEntry </code>对象,然后获取<d> </i>中所有<code> DetailEntry </code>对象的列表。
 * 代码还创建要传递给方法<code> addDetailEntry </code>的<code> Name </code>对象。
 * 用于创建<code> Name </code>对象的变量<i> se </i>是一个<code> SOAPEnvelope </code>对象。
 * <PRE>
 */
public interface Detail extends SOAPFaultElement {

    /**
     * Creates a new <code>DetailEntry</code> object with the given
     * name and adds it to this <code>Detail</code> object.
     *
     * <p>
     *  详细d = sf.getDetail();名称= se.createName("GetLastTradePrice","WOMBAT","http://www.wombat.org/trader");
     *  d.addDetailEntry(name);迭代器it = d.getDetailEntries();。
     * </PRE>
     * 
     * @param name a <code>Name</code> object identifying the
     *         new <code>DetailEntry</code> object
     *
     * @exception SOAPException thrown when there is a problem in adding a
     * DetailEntry object to this Detail object.
     *
     * @see Detail#addDetailEntry(QName qname)
     */
    public DetailEntry addDetailEntry(Name name) throws SOAPException;

    /**
     * Creates a new <code>DetailEntry</code> object with the given
     * QName and adds it to this <code>Detail</code> object. This method
     * is the preferred over the one using Name.
     *
     * <p>
     * 使用给定的名称创建一个新的<code> DetailEntry </code>对象,并将其添加到此<code> Detail </code>对象。
     * 
     * 
     * @param qname a <code>QName</code> object identifying the
     *         new <code>DetailEntry</code> object
     *
     * @exception SOAPException thrown when there is a problem in adding a
     * DetailEntry object to this Detail object.
     *
     * @see Detail#addDetailEntry(Name name)
     * @since SAAJ 1.3
     */
    public DetailEntry addDetailEntry(QName qname) throws SOAPException;

    /**
     * Gets an Iterator over all of the <code>DetailEntry</code>s in this <code>Detail</code> object.
     *
     * <p>
     *  使用给定的QName创建一个新的<code> DetailEntry </code>对象,并将其添加到此<code> Detail </code>对象。此方法比使用Name的方法优先。
     * 
     * 
     * @return an <code>Iterator</code> object over the <code>DetailEntry</code>
     *             objects in this <code>Detail</code> object
     */
    public Iterator getDetailEntries();
}
