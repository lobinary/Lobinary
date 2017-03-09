/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform.dom;

import javax.xml.transform.SourceLocator;

import org.w3c.dom.Node;


/**
 * Indicates the position of a node in a source DOM, intended
 * primarily for error reporting.  To use a DOMLocator, the receiver of an
 * error must downcast the {@link javax.xml.transform.SourceLocator}
 * object returned by an exception. A {@link javax.xml.transform.Transformer}
 * may use this object for purposes other than error reporting, for instance,
 * to indicate the source node that originated a result node.
 * <p>
 *  表示源DOM中节点的位置,主要用于错误报告。要使用DOMLocator,错误的接收者必须向下转换异常返回的{@link javax.xml.transform.SourceLocator}对象。
 *  {@link javax.xml.transform.Transformer}可以将此对象用于除错误报告之外的目的,例如,以指示始发结果节点的源节点。
 * 
 */
public interface DOMLocator extends SourceLocator {

    /**
     * Return the node where the event occurred.
     *
     * <p>
     * 
     * @return The node that is the location for the event.
     */
    public Node getOriginatingNode();
}
