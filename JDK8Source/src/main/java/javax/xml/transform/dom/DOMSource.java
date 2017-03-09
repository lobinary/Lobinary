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

import javax.xml.transform.Source;

import org.w3c.dom.Node;

/**
 * <p>Acts as a holder for a transformation Source tree in the
 * form of a Document Object Model (DOM) tree.</p>
 *
 * <p>Note that XSLT requires namespace support. Attempting to transform a DOM
 * that was not contructed with a namespace-aware parser may result in errors.
 * Parsers can be made namespace aware by calling
 * {@link javax.xml.parsers.DocumentBuilderFactory#setNamespaceAware(boolean awareness)}.</p>
 *
 * <p>
 *  <p>作为文档对象模型(DOM)树形式的转换源树的持有者。</p>
 * 
 *  <p>请注意,XSLT需要命名空间支持。尝试转换未被命名空间感知解析器引起的DOM可能会导致错误。
 * 解析器可以通过调用{@link javax.xml.parsers.DocumentBuilderFactory#setNamespaceAware(boolean awareness)}命名空间感知。
 *  <p>请注意,XSLT需要命名空间支持。尝试转换未被命名空间感知解析器引起的DOM可能会导致错误。</p>。
 * 
 * 
 * @author <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @see <a href="http://www.w3.org/TR/DOM-Level-2">Document Object Model (DOM) Level 2 Specification</a>
 */
public class DOMSource implements Source {

    /**
     * <p><code>Node</code> to serve as DOM source.</p>
     * <p>
     *  <p> <code> Node </code>作为DOM源。</p>
     * 
     */
    private Node node;

    /**
     * <p>The base ID (URL or system ID) from where URLs
     * will be resolved.</p>
     * <p>
     *  <p>要解析网址的基本ID(网址或系统ID)。</p>
     * 
     */
    private String systemID;

    /** If {@link javax.xml.transform.TransformerFactory#getFeature}
     * returns true when passed this value as an argument,
     * the Transformer supports Source input of this type.
     * <p>
     *  当传递此值作为参数时返回true,Transformer支持此类型的Source输入。
     * 
     */
    public static final String FEATURE =
        "http://javax.xml.transform.dom.DOMSource/feature";

    /**
     * <p>Zero-argument default constructor.  If this constructor is used, and
     * no DOM source is set using {@link #setNode(Node node)} , then the
     * <code>Transformer</code> will
     * create an empty source {@link org.w3c.dom.Document} using
     * {@link javax.xml.parsers.DocumentBuilder#newDocument()}.</p>
     *
     * <p>
     *  <p>零参数默认构造函数。
     * 如果使用这个构造函数,并且没有使用{@link #setNode(Node node)}设置DOM源,那么<code> Transformer </code>将创建一个空源{@link org.w3c.dom.Document}
     * 使用{@link javax.xml.parsers.DocumentBuilder#newDocument()}。
     *  <p>零参数默认构造函数。</p>。
     * 
     * 
     * @see javax.xml.transform.Transformer#transform(Source xmlSource, Result outputTarget)
     */
    public DOMSource() { }

    /**
     * Create a new input source with a DOM node.  The operation
     * will be applied to the subtree rooted at this node.  In XSLT,
     * a "/" pattern still means the root of the tree (not the subtree),
     * and the evaluation of global variables and parameters is done
     * from the root node also.
     *
     * <p>
     *  使用DOM节点创建新的输入源。该操作将应用于以此节点为根的子树。在XSLT中,"/"模式仍然意味着树的根(而不是子树),并且从根节点也对全局变量和参数进行评估。
     * 
     * 
     * @param n The DOM node that will contain the Source tree.
     */
    public DOMSource(Node n) {
        setNode(n);
    }

    /**
     * Create a new input source with a DOM node, and with the
     * system ID also passed in as the base URI.
     *
     * <p>
     *  使用DOM节点创建新的输入源,并且系统ID也作为基本URI传递。
     * 
     * 
     * @param node The DOM node that will contain the Source tree.
     * @param systemID Specifies the base URI associated with node.
     */
    public DOMSource(Node node, String systemID) {
        setNode(node);
        setSystemId(systemID);
    }

    /**
     * Set the node that will represents a Source DOM tree.
     *
     * <p>
     *  设置将表示Source DOM树的节点。
     * 
     * 
     * @param node The node that is to be transformed.
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Get the node that represents a Source DOM tree.
     *
     * <p>
     * 获取表示Source DOM树的节点。
     * 
     * 
     * @return The node that is to be transformed.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Set the base ID (URL or system ID) from where URLs
     * will be resolved.
     *
     * <p>
     *  设置要解析URL的基本ID(URL或系统ID)。
     * 
     * 
     * @param systemID Base URL for this DOM tree.
     */
    public void setSystemId(String systemID) {
        this.systemID = systemID;
    }

    /**
     * Get the base ID (URL or system ID) from where URLs
     * will be resolved.
     *
     * <p>
     *  获取要解析网址的基本ID(网址或系统ID)。
     * 
     * @return Base URL for this DOM tree.
     */
    public String getSystemId() {
        return this.systemID;
    }
}
