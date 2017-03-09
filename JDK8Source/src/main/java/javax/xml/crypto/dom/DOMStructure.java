/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
/*
 * $Id: DOMStructure.java,v 1.6 2005/05/09 18:33:26 mullan Exp $
 * <p>
 *  $ Id：DOMStructure.java,v 1.6 2005/05/09 18:33:26 mullan Exp $
 * 
 */
package javax.xml.crypto.dom;

import org.w3c.dom.Node;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.XMLSignature;

/**
 * A DOM-specific {@link XMLStructure}. The purpose of this class is to
 * allow a DOM node to be used to represent extensible content (any elements
 * or mixed content) in XML Signature structures.
 *
 * <p>If a sequence of nodes is needed, the node contained in the
 * <code>DOMStructure</code> is the first node of the sequence and successive
 * nodes can be accessed by invoking {@link Node#getNextSibling}.
 *
 * <p>If the owner document of the <code>DOMStructure</code> is different than
 * the target document of an <code>XMLSignature</code>, the
 * {@link XMLSignature#sign(XMLSignContext)} method imports the node into the
 * target document before generating the signature.
 *
 * <p>
 *  特定于DOM的{@link XMLStructure}。此类的目的是允许DOM节点用于表示XML签名结构中的可扩展内容(任何元素或混合内容)。
 * 
 *  <p>如果需要节点序列,则包含在<code> DOMStructure </code>中的节点是序列的第一个节点,并且可以通过调用{@link Node#getNextSibling}来访问连续的节点
 * 。
 * 
 *  <p>如果<code> DOMStructure </code>的所有者文档与<code> XMLSignature </code>的目标文档不同,则{@link XMLSignature#sign(XMLSignContext)}
 * 方法将节点导入目标文档生成签名之前。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public class DOMStructure implements XMLStructure {

    private final Node node;

    /**
     * Creates a <code>DOMStructure</code> containing the specified node.
     *
     * <p>
     * 
     * @param node the node
     * @throws NullPointerException if <code>node</code> is <code>null</code>
     */
    public DOMStructure(Node node) {
        if (node == null) {
            throw new NullPointerException("node cannot be null");
        }
        this.node = node;
    }

    /**
     * Returns the node contained in this <code>DOMStructure</code>.
     *
     * <p>
     *  创建包含指定节点的<code> DOMStructure </code>。
     * 
     * 
     * @return the node
     */
    public Node getNode() {
        return node;
    }

    /**
    /* <p>
    /*  返回此<code> DOMStructure </code>中包含的节点。
    /* 
    /* 
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean isFeatureSupported(String feature) {
        if (feature == null) {
            throw new NullPointerException();
        } else {
            return false;
        }
    }
}
