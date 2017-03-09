/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind;
import  javax.xml.namespace.QName;

/**
 * Provide access to JAXB xml binding data for a JAXB object.
 *
 * <p>
 * Intially, the intent of this class is to just conceptualize how
 * a JAXB application developer can access xml binding information,
 * independent if binding model is java to schema or schema to java.
 * Since accessing the XML element name related to a JAXB element is
 * a highly requested feature, demonstrate access to this
 * binding information.
 *
 * The factory method to get a <code>JAXBIntrospector</code> instance is
 * {@link JAXBContext#createJAXBIntrospector()}.
 *
 * <p>
 *  提供对JAXB对象的JAXB xml绑定数据的访问。
 * 
 * <p>
 *  目前,这个类的意图是只是概念化JAXB应用程序开发人员如何访问XML绑定信息,如果绑定模型是java到模式或模式到java。
 * 由于访问与JAXB元素相关的XML元素名称是高度请求的功能,因此请演示对此绑定信息的访问。
 * 
 *  获取<code> JAXBIntrospector </code>实例的工厂方法是{@link JAXBContext#createJAXBIntrospector()}。
 * 
 * 
 * @see JAXBContext#createJAXBIntrospector()
 * @since JAXB2.0
 */
public abstract class JAXBIntrospector {

    /**
     * <p>Return true if <code>object</code> represents a JAXB element.</p>
     * <p>Parameter <code>object</code> is a JAXB element for following cases:
     * <ol>
     *   <li>It is an instance of <code>javax.xml.bind.JAXBElement</code>.</li>
     *   <li>The class of <code>object</code> is annotated with
     *       <code>&#64XmlRootElement</code>.
     *   </li>
     * </ol>
     *
     * <p>
     *  <p>如果<code> object </code>代表一个JAXB元素,则返回true。</p> <p>参数<code> object </code>是以下情况的JAXB元素：
     * <ol>
     *  <li>它是<code> javax.xml.bind.JAXBElement </code>的一个实例。
     * </li> <li> <code> object </code>类注释为<code>&#64XmlRootElement </code>。
     * </li>
     * </ol>
     * 
     * 
     * @see #getElementName(Object)
     */
    public abstract boolean isElement(Object object);

    /**
     * <p>Get xml element qname for <code>jaxbElement</code>.</p>
     *
     * <p>
     *  <p>获取<code> jaxbElement </code>的xml元素qname。</p>
     * 
     * 
     * @param jaxbElement is an object that {@link #isElement(Object)} returned true.
     *
     * @return xml element qname associated with jaxbElement;
     *         null if <code>jaxbElement</code> is not a JAXB Element.
     */
    public abstract QName getElementName(Object jaxbElement);

    /**
     * <p>Get the element value of a JAXB element.</p>
     *
     * <p>Convenience method to abstract whether working with either
     *    a javax.xml.bind.JAXBElement instance or an instance of
     *    <tt>&#64XmlRootElement</tt> annotated Java class.</p>
     *
     * <p>
     *  <p>获取JAXB元素的元素值。</p>
     * 
     * 
     * @param jaxbElement  object that #isElement(Object) returns true.
     *
     * @return The element value of the <code>jaxbElement</code>.
     */
    public static Object getValue(Object jaxbElement) {
        if (jaxbElement instanceof JAXBElement) {
            return ((JAXBElement)jaxbElement).getValue();
        } else {
            // assume that class of this instance is
            // annotated with @XmlRootElement.
            return jaxbElement;
        }
    }
}
