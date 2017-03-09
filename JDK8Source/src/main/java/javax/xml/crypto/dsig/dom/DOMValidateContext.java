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
/*
 * $Id: DOMValidateContext.java,v 1.8 2005/05/10 16:31:14 mullan Exp $
 * <p>
 *  $ Id：DOMValidateContext.java,v 1.8 2005/05/10 16:31:14 mullan Exp $
 * 
 */
package javax.xml.crypto.dsig.dom;

import javax.xml.crypto.KeySelector;
import javax.xml.crypto.dom.DOMCryptoContext;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.XMLValidateContext;
import java.security.Key;
import org.w3c.dom.Node;

/**
 * A DOM-specific {@link XMLValidateContext}. This class contains additional
 * methods to specify the location in a DOM tree where an {@link XMLSignature}
 * is to be unmarshalled and validated from.
 *
 * <p>Note that the behavior of an unmarshalled <code>XMLSignature</code>
 * is undefined if the contents of the underlying DOM tree are modified by the
 * caller after the <code>XMLSignature</code> is created.
 *
 * <p>Also, note that <code>DOMValidateContext</code> instances can contain
 * information and state specific to the XML signature structure it is
 * used with. The results are unpredictable if a
 * <code>DOMValidateContext</code> is used with different signature structures
 * (for example, you should not use the same <code>DOMValidateContext</code>
 * instance to validate two different {@link XMLSignature} objects).
 *
 * <p>
 *  DOM特定的{@link XMLValidateContext}。此类包含其他方法来指定DOM树中要从其中取消解组并验证{@link XMLSignature}的位置。
 * 
 *  <p>请注意,如果在创建<code> XMLSignature </code>之后调用者修改了基础DOM树的内容,则未定义的<code> XMLSignature </code>的行为是未定义的。
 * 
 *  <p>此外,请注意,<code> DOMValidateContext </code>实例可以包含特定于其所使用的XML签名结构的信息和状态。
 * 如果<code> DOMValidateContext </code>与不同的签名结构一起使用(例如,您不应该使用相同的<code> DOMValidateContext </code>实例验证两个不同
 * 的{@link XMLSignature}对象),结果是不可预测的, 。
 *  <p>此外,请注意,<code> DOMValidateContext </code>实例可以包含特定于其所使用的XML签名结构的信息和状态。
 * 
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 * @see XMLSignatureFactory#unmarshalXMLSignature(XMLValidateContext)
 */
public class DOMValidateContext extends DOMCryptoContext
    implements XMLValidateContext {

    private Node node;

    /**
     * Creates a <code>DOMValidateContext</code> containing the specified key
     * selector and node.
     *
     * <p>
     *  创建一个包含指定键选择器和节点的<code> DOMValidateContext </code>。
     * 
     * 
     * @param ks a key selector for finding a validation key
     * @param node the node
     * @throws NullPointerException if <code>ks</code> or <code>node</code> is
     *    <code>null</code>
     */
    public DOMValidateContext(KeySelector ks, Node node) {
        if (ks == null) {
            throw new NullPointerException("key selector is null");
        }
        init(node, ks);
    }

    /**
     * Creates a <code>DOMValidateContext</code> containing the specified key
     * and node. The validating key will be stored in a
     * {@link KeySelector#singletonKeySelector singleton KeySelector} that
     * is returned when the {@link #getKeySelector getKeySelector}
     * method is called.
     *
     * <p>
     *  创建一个包含指定键和节点的<code> DOMValidateContext </code>。
     * 验证键将存储在调用{@link #getKeySelector getKeySelector}方法时返回的{@link KeySelector#singletonKeySelector singleton KeySelector}
     * 中。
     *  创建一个包含指定键和节点的<code> DOMValidateContext </code>。
     * 
     * 
     * @param validatingKey the validating key
     * @param node the node
     * @throws NullPointerException if <code>validatingKey</code> or
     *    <code>node</code> is <code>null</code>
     */
    public DOMValidateContext(Key validatingKey, Node node) {
        if (validatingKey == null) {
            throw new NullPointerException("validatingKey is null");
        }
        init(node, KeySelector.singletonKeySelector(validatingKey));
    }

    private void init(Node node, KeySelector ks) {
        if (node == null) {
            throw new NullPointerException("node is null");
        }

        this.node = node;
        super.setKeySelector(ks);
        if (System.getSecurityManager() != null) {
            super.setProperty("org.jcp.xml.dsig.secureValidation",
                              Boolean.TRUE);
        }
    }

    /**
     * Sets the node.
     *
     * <p>
     *  设置节点。
     * 
     * 
     * @param node the node
     * @throws NullPointerException if <code>node</code> is <code>null</code>
     * @see #getNode
     */
    public void setNode(Node node) {
        if (node == null) {
            throw new NullPointerException();
        }
        this.node = node;
    }

    /**
     * Returns the node.
     *
     * <p>
     *  返回节点。
     * 
     * @return the node (never <code>null</code>)
     * @see #setNode(Node)
     */
    public Node getNode() {
        return node;
    }
}
