/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind.annotation;

import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

/**
 * Converts an element (and its descendants)
 * from/to DOM (or similar) representation.
 *
 * <p>
 * Implementations of this interface will be used in conjunction with
 * {@link XmlAnyElement} annotation to map an element of XML into a representation
 * of infoset such as W3C DOM.
 *
 * <p>
 * Implementations hide how a portion of XML is converted into/from such
 * DOM-like representation, allowing JAXB providers to work with arbitrary
 * such library.
 *
 * <P>
 * This interface is intended to be implemented by library writers
 * and consumed by JAXB providers. None of those methods are intended to
 * be called from applications.
 *
 * <p>
 *  将元素(及其后代)从/转换为DOM(或类似)表示。
 * 
 * <p>
 *  此接口的实现将与{@link XmlAnyElement}注释结合使用,将XML的元素映射为信息集(如W3C DOM)的表示形式。
 * 
 * <p>
 *  实现隐藏了XML的一部分如何转换为/类似于DOM的表示,允许JAXB提供程序使用任意这样的库。
 * 
 * <P>
 *  此接口旨在由库编写器实现并由JAXB提供程序使用。这些方法都不打算从应用程序中调用。
 * 
 * 
 * @author Kohsuke Kawaguchi
 * @since JAXB2.0
 */
public interface DomHandler<ElementT,ResultT extends Result> {
    /**
     * When a JAXB provider needs to unmarshal a part of a document into an
     * infoset representation, it first calls this method to create a
     * {@link Result} object.
     *
     * <p>
     * A JAXB provider will then send a portion of the XML
     * into the given result. Such a portion always form a subtree
     * of the whole XML document rooted at an element.
     *
     * <p>
     *  当JAXB提供程序需要将文档的一部分解组到信息集表示中时,它首先调用此方法来创建{@link Result}对象。
     * 
     * <p>
     *  然后JAXB提供程序将XML的一部分发送到给定的结果。这样的部分总是形成以元素为根的整个XML文档的子树。
     * 
     * 
     * @param errorHandler
     *      if any error happens between the invocation of this method
     *      and the invocation of {@link #getElement(Result)}, they
     *      must be reported to this handler.
     *
     *      The caller must provide a non-null error handler.
     *
     *      The {@link Result} object created from this method
     *      may hold a reference to this error handler.
     *
     * @return
     *      null if the operation fails. The error must have been reported
     *      to the error handler.
     */
    ResultT createUnmarshaller( ValidationEventHandler errorHandler );

    /**
     * Once the portion is sent to the {@link Result}. This method is called
     * by a JAXB provider to obtain the unmarshalled element representation.
     *
     * <p>
     * Multiple invocations of this method may return different objects.
     * This method can be invoked only when the whole sub-tree are fed
     * to the {@link Result} object.
     *
     * <p>
     *  一旦该部分发送到{@link Result}。此方法由JAXB提供程序调用以获取未组合的元素表示。
     * 
     * <p>
     *  此方法的多次调用可能返回不同的对象。只有当整个子树被馈送到{@link Result}对象时,才能调用此方法。
     * 
     * 
     * @param rt
     *      The {@link Result} object created by {@link #createUnmarshaller(ValidationEventHandler)}.
     *
     * @return
     *      null if the operation fails. The error must have been reported
     *      to the error handler.
     */
    ElementT getElement(ResultT rt);

    /**
     * This method is called when a JAXB provider needs to marshal an element
     * to XML.
     *
     * <p>
     * If non-null, the returned {@link Source} must contain a whole document
     * rooted at one element, which will then be weaved into a bigger document
     * that the JAXB provider is marshalling.
     *
     * <p>
     *  当JAXB提供程序需要将元素编组为XML时,将调用此方法。
     * 
     * <p>
     * 
     * @param errorHandler
     *      Receives any errors happened during the process of converting
     *      an element into a {@link Source}.
     *
     *      The caller must provide a non-null error handler.
     *
     * @return
     *      null if there was an error. The error should have been reported
     *      to the handler.
     */
    Source marshal( ElementT n, ValidationEventHandler errorHandler );
}
