/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2002,2004,2005 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;
import com.sun.org.apache.xerces.internal.util.URI;
import com.sun.org.apache.xerces.internal.impl.Constants;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.UserDataHandler;
import com.sun.org.apache.xerces.internal.util.XMLChar;
import com.sun.org.apache.xerces.internal.util.XML11Char;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * The Document interface represents the entire HTML or XML document.
 * Conceptually, it is the root of the document tree, and provides the
 * primary access to the document's data.
 * <P>
 * Since elements, text nodes, comments, processing instructions,
 * etc. cannot exist outside the context of a Document, the Document
 * interface also contains the factory methods needed to create these
 * objects. The Node objects created have a ownerDocument attribute
 * which associates them with the Document within whose context they
 * were created.
 * <p>
 * The CoreDocumentImpl class only implements the DOM Core. Additional modules
 * are supported by the more complete DocumentImpl subclass.
 * <p>
 * <b>Note:</b> When any node in the document is serialized, the
 * entire document is serialized along with it.
 *
 * @xerces.internal
 *
 * <p>
 * Document接口表示整个HTML或XML文档概念上,它是文档树的根,并提供对文档数据的主访问权
 * <P>
 *  由于元素,文本节点,注释,处理指令等不能存在于Document的上下文之外,因此Document接口还包含创建这些对象所需的工厂方法。
 * 创建的Node对象具有ownerDocument属性,它将它们与Document上下文。
 * <p>
 *  CoreDocumentImpl类仅实现DOM Core更加完整的DocumentImpl子类支持其他模块
 * <p>
 *  <b>注意：</b>当文档中的任何节点序列化时,整个文档将与其一起序列化
 * 
 *  @xercesinternal
 * 
 * 
 * @author Arnaud  Le Hors, IBM
 * @author Joe Kesselman, IBM
 * @author Andy Clark, IBM
 * @author Ralf Pfeiffer, IBM
 * @version $Id: CoreDocumentImpl.java,v 1.9 2010-11-01 04:39:37 joehw Exp $
 * @since  PR-DOM-Level-1-19980818.
 */


public class CoreDocumentImpl
extends ParentNode implements Document  {

        /**TODO::
         * 1. Change XML11Char method names similar to XMLChar. That will prevent lot
         * of dirty version checking code.
         *
         * 2. IMO during cloneNode qname/isXMLName check should not be made.
         * <p>
         * 1更改类似于XMLChar的XML11Char方法名称这将防止大量脏版本检查代码
         * 
         *  2在克隆过程中不应进行IMO qname / isXMLName检查
         * 
         */
    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 0;

    //
    // Data
    //

    // document information

    /** Document type. */
    protected DocumentTypeImpl docType;

    /** Document element. */
    protected ElementImpl docElement;

    /** NodeListCache free list */
    transient NodeListCache fFreeNLCache;

    /**Experimental DOM Level 3 feature: Document encoding */
    protected String encoding;

    /**Experimental DOM Level 3 feature: Document actualEncoding */
    protected String actualEncoding;

    /**Experimental DOM Level 3 feature: Document version */
    protected String version;

    /**Experimental DOM Level 3 feature: Document standalone */
    protected boolean standalone;

    /**Experimental DOM Level 3 feature: documentURI */
    protected String fDocumentURI;

        //Revisit :: change to a better data structure.
    /** Table for user data attached to this document nodes. */
    protected Hashtable userData;


    /** Identifiers. */
    protected Hashtable identifiers;

    // DOM Level 3: normalizeDocument
    transient DOMNormalizer domNormalizer = null;
    transient DOMConfigurationImpl fConfiguration = null;

    // support of XPath API
    transient Object fXPathEvaluator = null;

    /** Table for quick check of child insertion. */
    private final static int[] kidOK;

    /**
     * Number of alterations made to this document since its creation.
     * Serves as a "dirty bit" so that live objects such as NodeList can
     * recognize when an alteration has been made and discard its cached
     * state information.
     * <p>
     * Any method that alters the tree structure MUST cause or be
     * accompanied by a call to changed(), to inform it that any outstanding
     * NodeLists may have to be updated.
     * <p>
     * (Required because NodeList is simultaneously "live" and integer-
     * indexed -- a bad decision in the DOM's design.)
     * <p>
     * Note that changes which do not affect the tree's structure -- changing
     * the node's name, for example -- do _not_ have to call changed().
     * <p>
     * Alternative implementation would be to use a cryptographic
     * Digest value rather than a count. This would have the advantage that
     * "harmless" changes (those producing equal() trees) would not force
     * NodeList to resynchronize. Disadvantage is that it's slightly more prone
     * to "false negatives", though that's the difference between "wildly
     * unlikely" and "absurdly unlikely". IF we start maintaining digests,
     * we should consider taking advantage of them.
     *
     * Note: This used to be done a node basis, so that we knew what
     * subtree changed. But since only DeepNodeList really use this today,
     * the gain appears to be really small compared to the cost of having
     * an int on every (parent) node plus having to walk up the tree all the
     * way to the root to mark the branch as changed everytime a node is
     * changed.
     * So we now have a single counter global to the document. It means that
     * some objects may flush their cache more often than necessary, but this
     * makes nodes smaller and only the document needs to be marked as changed.
     * <p>
     *  自创建以来对此文档所做的更改数量作为"脏位",以便活动对象(如NodeList)可以识别何时进行了更改并丢弃其缓存的状态信息
     * <p>
     *  改变树结构的任何方法必须引起或伴随着对changed()的调用,以通知它任何未完成的NodeLists可能必须更新
     * <p>
     *  (因为NodeList同时是"live"和整数索引的 - 这是DOM设计中的一个坏决定)
     * <p>
     * 注意,不影响树结构的更改(例如更改节点名称),do _not_必须调用changed()
     * <p>
     *  替代实现将使用加密Digest值而不是计数。这将具有"无害"改变(产生等于()树的那些改变)不会迫使NodeList重新同步的优点。
     * 缺点是它更容易出现"假否定" ,尽管这是"极不可能"和"荒谬的不可能"之间的差异如果我们开始维护摘要,我们应该考虑利用它们。
     * 
     * 注意：这是做一个节点的基础,所以我们知道什么子树改变但是由于只有DeepNodeList真正使用今天,增益看起来是真的很小,在每个(父)节点有一个int的成本加上一直到树一直到根,以标记分支为每当一个节
     * 点改变时改变所以我们现在有一个单一的计数器全局的文档这意味着一些对象可能刷新他们的缓存更经常比必要,但这使得节点较小,只有文档需要标记为已更改。
     * 
     */
    protected int changes = 0;

    // experimental

    /** Allow grammar access. */
    protected boolean allowGrammarAccess;

    /** Bypass error checking. */
    protected boolean errorChecking = true;
    /** Ancestor checking */
    protected boolean ancestorChecking = true;

    //Did version change at any point when the document was created ?
    //this field helps us to optimize when normalizingDocument.
    protected boolean xmlVersionChanged = false ;

    /** The following are required for compareDocumentPosition
    /* <p>
     */
    // Document number.   Documents are ordered across the implementation using
    // positive integer values.  Documents are assigned numbers on demand.
    private int documentNumber=0;
    // Node counter and table.  Used to assign numbers to nodes for this
    // document.  Node number values are negative integers.  Nodes are
    // assigned numbers on demand.
    private int nodeCounter = 0;
    private Hashtable nodeTable;
    private boolean xml11Version = false; //by default 1.0
    //
    // Static initialization
    //

    static {

        kidOK = new int[13];

        kidOK[DOCUMENT_NODE] =
        1 << ELEMENT_NODE | 1 << PROCESSING_INSTRUCTION_NODE |
        1 << COMMENT_NODE | 1 << DOCUMENT_TYPE_NODE;

        kidOK[DOCUMENT_FRAGMENT_NODE] =
        kidOK[ENTITY_NODE] =
        kidOK[ENTITY_REFERENCE_NODE] =
        kidOK[ELEMENT_NODE] =
        1 << ELEMENT_NODE | 1 << PROCESSING_INSTRUCTION_NODE |
        1 << COMMENT_NODE | 1 << TEXT_NODE |
        1 << CDATA_SECTION_NODE | 1 << ENTITY_REFERENCE_NODE ;


        kidOK[ATTRIBUTE_NODE] =
        1 << TEXT_NODE | 1 << ENTITY_REFERENCE_NODE;

        kidOK[DOCUMENT_TYPE_NODE] =
        kidOK[PROCESSING_INSTRUCTION_NODE] =
        kidOK[COMMENT_NODE] =
        kidOK[TEXT_NODE] =
        kidOK[CDATA_SECTION_NODE] =
        kidOK[NOTATION_NODE] =
        0;

    } // static

    //
    // Constructors
    //

    /**
     * NON-DOM: Actually creating a Document is outside the DOM's spec,
     * since it has to operate in terms of a particular implementation.
     * <p>
     *  NON-DOM：实际创建一个文档是在DOM规范之外,因为它必须根据特定的实现操作
     * 
     */
    public CoreDocumentImpl() {
        this(false);
    }

    /** Constructor. */
    public CoreDocumentImpl(boolean grammarAccess) {
        super(null);
        ownerDocument = this;
        allowGrammarAccess = grammarAccess;
        String systemProp = SecuritySupport.getSystemProperty(Constants.SUN_DOM_PROPERTY_PREFIX+Constants.SUN_DOM_ANCESTOR_CHECCK);
        if (systemProp != null) {
            if (systemProp.equalsIgnoreCase("false")) {
                ancestorChecking = false;
            }
        }
    }

    /**
     * For DOM2 support.
     * The createDocument factory method is in DOMImplementation.
     * <p>
     *  对于DOM2支持createDocument工厂方法在DOMImplementation中
     * 
     */
    public CoreDocumentImpl(DocumentType doctype) {
        this(doctype, false);
    }

    /** For DOM2 support. */
    public CoreDocumentImpl(DocumentType doctype, boolean grammarAccess) {
        this(grammarAccess);
        if (doctype != null) {
            DocumentTypeImpl doctypeImpl;
            try {
                doctypeImpl = (DocumentTypeImpl) doctype;
            } catch (ClassCastException e) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null);
                throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, msg);
            }
            doctypeImpl.ownerDocument = this;
            appendChild(doctype);
        }
    }

    //
    // Node methods
    //

    // even though ownerDocument refers to this in this implementation
    // the DOM Level 2 spec says it must be null, so make it appear so
    final public Document getOwnerDocument() {
        return null;
    }

    /** Returns the node type. */
    public short getNodeType() {
        return Node.DOCUMENT_NODE;
    }

    /** Returns the node name. */
    public String getNodeName() {
        return "#document";
    }

    /**
     * Deep-clone a document, including fixing ownerDoc for the cloned
     * children. Note that this requires bypassing the WRONG_DOCUMENT_ERR
     * protection. I've chosen to implement it by calling importNode
     * which is DOM Level 2.
     *
     * <p>
     * 深层克隆文档,包括为克隆的孩子修复ownerDoc注意,这需要绕过WRONG_DOCUMENT_ERR保护,我已经选择通过调用importNode来实现它,它是DOM级别2
     * 
     * 
     * @return org.w3c.dom.Node
     * @param deep boolean, iff true replicate children
     */
    public Node cloneNode(boolean deep) {

        CoreDocumentImpl newdoc = new CoreDocumentImpl();
        callUserDataHandlers(this, newdoc, UserDataHandler.NODE_CLONED);
        cloneNode(newdoc, deep);

        return newdoc;

    } // cloneNode(boolean):Node


    /**
     * internal method to share code with subclass
     * <p>
     *  内部方法与子类共享代码
     * 
     * 
     **/
    protected void cloneNode(CoreDocumentImpl newdoc, boolean deep) {

        // clone the children by importing them
        if (needsSyncChildren()) {
            synchronizeChildren();
        }

        if (deep) {
            Hashtable reversedIdentifiers = null;

            if (identifiers != null) {
                // Build a reverse mapping from element to identifier.
                reversedIdentifiers = new Hashtable();
                Enumeration elementIds = identifiers.keys();
                while (elementIds.hasMoreElements()) {
                    Object elementId = elementIds.nextElement();
                    reversedIdentifiers.put(identifiers.get(elementId),
                    elementId);
                }
            }

            // Copy children into new document.
            for (ChildNode kid = firstChild; kid != null;
            kid = kid.nextSibling) {
                newdoc.appendChild(newdoc.importNode(kid, true, true,
                reversedIdentifiers));
            }
        }

        // experimental
        newdoc.allowGrammarAccess = allowGrammarAccess;
        newdoc.errorChecking = errorChecking;

    } // cloneNode(CoreDocumentImpl,boolean):void

    /**
     * Since a Document may contain at most one top-level Element child,
     * and at most one DocumentType declaraction, we need to subclass our
     * add-children methods to implement this constraint.
     * Since appendChild() is implemented as insertBefore(,null),
     * altering the latter fixes both.
     * <p>
     * While I'm doing so, I've taken advantage of the opportunity to
     * cache documentElement and docType so we don't have to
     * search for them.
     *
     * REVISIT: According to the spec it is not allowed to alter neither the
     * document element nor the document type in any way
     * <p>
     *  由于文档最多只能包含一个顶级Element子元素,并且最多包含一个DocumentType声明,因此我们需要对我们的add-children子类进行子类化以实现此约束。
     * 由于appendChild()实现为insertBefore(null),因此将后者修复两者。
     * <p>
     *  虽然我这样做,我已经利用了机会缓存documentElement和docType,所以我们不必搜索他们
     * 
     *  REVISIT：根据规范,不允许以任何方式改变文档元素或文档类型
     * 
     */
    public Node insertBefore(Node newChild, Node refChild)
    throws DOMException {

        // Only one such child permitted
        int type = newChild.getNodeType();
        if (errorChecking) {
            if((type == Node.ELEMENT_NODE && docElement != null) ||
            (type == Node.DOCUMENT_TYPE_NODE && docType != null)) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null);
                throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, msg);
            }
        }
        // Adopt orphan doctypes
        if (newChild.getOwnerDocument() == null &&
        newChild instanceof DocumentTypeImpl) {
            ((DocumentTypeImpl) newChild).ownerDocument = this;
        }
        super.insertBefore(newChild,refChild);

        // If insert succeeded, cache the kid appropriately
        if (type == Node.ELEMENT_NODE) {
            docElement = (ElementImpl)newChild;
        }
        else if (type == Node.DOCUMENT_TYPE_NODE) {
            docType = (DocumentTypeImpl)newChild;
        }

        return newChild;

    } // insertBefore(Node,Node):Node

    /**
     * Since insertBefore caches the docElement (and, currently, docType),
     * removeChild has to know how to undo the cache
     *
     * REVISIT: According to the spec it is not allowed to alter neither the
     * document element nor the document type in any way
     * <p>
     * 由于insertBefore缓存docElement(和当前的docType),removeChild必须知道如何撤消缓存
     * 
     *  REVISIT：根据规范,不允许以任何方式改变文档元素或文档类型
     * 
     */
    public Node removeChild(Node oldChild) throws DOMException {

        super.removeChild(oldChild);

        // If remove succeeded, un-cache the kid appropriately
        int type = oldChild.getNodeType();
        if(type == Node.ELEMENT_NODE) {
            docElement = null;
        }
        else if (type == Node.DOCUMENT_TYPE_NODE) {
            docType = null;
        }

        return oldChild;

    }   // removeChild(Node):Node

    /**
     * Since we cache the docElement (and, currently, docType),
     * replaceChild has to update the cache
     *
     * REVISIT: According to the spec it is not allowed to alter neither the
     * document element nor the document type in any way
     * <p>
     *  因为我们缓存了docElement(和当前的docType),所以replaceChild必须更新缓存
     * 
     *  REVISIT：根据规范,不允许以任何方式改变文档元素或文档类型
     * 
     */
    public Node replaceChild(Node newChild, Node oldChild)
    throws DOMException {

        // Adopt orphan doctypes
        if (newChild.getOwnerDocument() == null &&
        newChild instanceof DocumentTypeImpl) {
            ((DocumentTypeImpl) newChild).ownerDocument = this;
        }

        if (errorChecking &&((docType != null &&
            oldChild.getNodeType() != Node.DOCUMENT_TYPE_NODE &&
            newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE)
            || (docElement != null &&
            oldChild.getNodeType() != Node.ELEMENT_NODE &&
            newChild.getNodeType() == Node.ELEMENT_NODE))) {

            throw new DOMException(
                DOMException.HIERARCHY_REQUEST_ERR,
                DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
        }
        super.replaceChild(newChild, oldChild);

        int type = oldChild.getNodeType();
        if(type == Node.ELEMENT_NODE) {
            docElement = (ElementImpl)newChild;
        }
        else if (type == Node.DOCUMENT_TYPE_NODE) {
            docType = (DocumentTypeImpl)newChild;
        }
        return oldChild;
    }   // replaceChild(Node,Node):Node

    /*
     * Get Node text content
     * <p>
     *  获取节点文本内容
     * 
     * 
     * @since DOM Level 3
     */
    public String getTextContent() throws DOMException {
        return null;
    }

    /*
     * Set Node text content
     * <p>
     *  设置节点文本内容
     * 
     * 
     * @since DOM Level 3
     */
    public void setTextContent(String textContent)
    throws DOMException {
        // no-op
    }

    /**
    /* <p>
    /* 
     * @since DOM Level 3
     */
    public Object getFeature(String feature, String version) {

        boolean anyVersion = version == null || version.length() == 0;

        // if a plus sign "+" is prepended to any feature name, implementations
        // are considered in which the specified feature may not be directly
        // castable DOMImplementation.getFeature(feature, version). Without a
        // plus, only features whose interfaces are directly castable are
        // considered.
        if ((feature.equalsIgnoreCase("+XPath"))
            && (anyVersion || version.equals("3.0"))) {

            // If an XPathEvaluator was created previously
            // return it otherwise create a new one.
            if (fXPathEvaluator != null) {
                return fXPathEvaluator;
            }

            try {
                Class xpathClass = ObjectFactory.findProviderClass (
                        "com.sun.org.apache.xpath.internal.domapi.XPathEvaluatorImpl", true);
                Constructor xpathClassConstr =
                    xpathClass.getConstructor(new Class[] { Document.class });

                // Check if the DOM XPath implementation implements
                // the interface org.w3c.dom.XPathEvaluator
                Class interfaces[] = xpathClass.getInterfaces();
                for (int i = 0; i < interfaces.length; i++) {
                    if (interfaces[i].getName().equals(
                    "org.w3c.dom.xpath.XPathEvaluator")) {
                        fXPathEvaluator = xpathClassConstr.newInstance(new Object[] { this });
                        return fXPathEvaluator;
                    }
                }
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        return super.getFeature(feature, version);
    }

    //
    // Document methods
    //

    // factory methods

    /**
     * Factory method; creates an Attribute having this Document as its
     * OwnerDoc.
     *
     * <p>
     *  工厂方法;创建具有此文档作为其OwnerDoc的属性
     * 
     * 
     * @param name The name of the attribute. Note that the attribute's value is
     * _not_ established at the factory; remember to set it!
     *
     * @throws DOMException(INVALID_NAME_ERR)
     * if the attribute name is not acceptable.
     */
    public Attr createAttribute(String name)
        throws DOMException {

        if (errorChecking && !isXMLName(name,xml11Version)) {
            String msg =
                DOMMessageFormatter.formatMessage(
                    DOMMessageFormatter.DOM_DOMAIN,
                    "INVALID_CHARACTER_ERR",
                    null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
        return new AttrImpl(this, name);

    } // createAttribute(String):Attr

    /**
     * Factory method; creates a CDATASection having this Document as
     * its OwnerDoc.
     *
     * <p>
     *  工厂方法;创建具有此文档作为其OwnerDoc的CDATASection
     * 
     * 
     * @param data The initial contents of the CDATA
     *
     * @throws DOMException(NOT_SUPPORTED_ERR) for HTML documents. (HTML
     * not yet implemented.)
     */
    public CDATASection createCDATASection(String data)
    throws DOMException {
        return new CDATASectionImpl(this, data);
    }

    /**
     * Factory method; creates a Comment having this Document as its
     * OwnerDoc.
     *
     * <p>
     *  工厂方法;创建具有此文档作为其OwnerDoc的注释
     * 
     * 
     * @param data The initial contents of the Comment. */
    public Comment createComment(String data) {
        return new CommentImpl(this, data);
    }

    /**
     * Factory method; creates a DocumentFragment having this Document
     * as its OwnerDoc.
     * <p>
     * 工厂方法;创建具有此文档作为其OwnerDoc的DocumentFragment
     * 
     */
    public DocumentFragment createDocumentFragment() {
        return new DocumentFragmentImpl(this);
    }

    /**
     * Factory method; creates an Element having this Document
     * as its OwnerDoc.
     *
     * <p>
     *  工厂方法;创建一个具有此文档作为其OwnerDoc的元素
     * 
     * 
     * @param tagName The name of the element type to instantiate. For
     * XML, this is case-sensitive. For HTML, the tagName parameter may
     * be provided in any case, but it must be mapped to the canonical
     * uppercase form by the DOM implementation.
     *
     * @throws DOMException(INVALID_NAME_ERR) if the tag name is not
     * acceptable.
     */
    public Element createElement(String tagName)
    throws DOMException {

        if (errorChecking && !isXMLName(tagName,xml11Version)) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
        return new ElementImpl(this, tagName);

    } // createElement(String):Element

    /**
     * Factory method; creates an EntityReference having this Document
     * as its OwnerDoc.
     *
     * <p>
     *  工厂方法;创建具有此文档作为其OwnerDoc的EntityReference
     * 
     * 
     * @param name The name of the Entity we wish to refer to
     *
     * @throws DOMException(NOT_SUPPORTED_ERR) for HTML documents, where
     * nonstandard entities are not permitted. (HTML not yet
     * implemented.)
     */
    public EntityReference createEntityReference(String name)
    throws DOMException {

        if (errorChecking && !isXMLName(name,xml11Version)) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
        return new EntityReferenceImpl(this, name);

    } // createEntityReference(String):EntityReference

    /**
     * Factory method; creates a ProcessingInstruction having this Document
     * as its OwnerDoc.
     *
     * <p>
     *  工厂方法;创建具有此文档作为其OwnerDoc的ProcessingInstruction
     * 
     * 
     * @param target The target "processor channel"
     * @param data Parameter string to be passed to the target.
     *
     * @throws DOMException(INVALID_NAME_ERR) if the target name is not
     * acceptable.
     *
     * @throws DOMException(NOT_SUPPORTED_ERR) for HTML documents. (HTML
     * not yet implemented.)
     */
    public ProcessingInstruction createProcessingInstruction(String target,
    String data)
    throws DOMException {

        if (errorChecking && !isXMLName(target,xml11Version)) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
        return new ProcessingInstructionImpl(this, target, data);

    } // createProcessingInstruction(String,String):ProcessingInstruction

    /**
     * Factory method; creates a Text node having this Document as its
     * OwnerDoc.
     *
     * <p>
     *  工厂方法;创建一个具有此文档作为其OwnerDoc的Text节点
     * 
     * 
     * @param data The initial contents of the Text.
     */
    public Text createTextNode(String data) {
        return new TextImpl(this, data);
    }

    // other document methods

    /**
     * For XML, this provides access to the Document Type Definition.
     * For HTML documents, and XML documents which don't specify a DTD,
     * it will be null.
     * <p>
     *  对于XML,这提供对文档类型定义的访问对于HTML文档和不指定DTD的XML文档,它将为null
     * 
     */
    public DocumentType getDoctype() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return docType;
    }


    /**
     * Convenience method, allowing direct access to the child node
     * which is considered the root of the actual document content. For
     * HTML, where it is legal to have more than one Element at the top
     * level of the document, we pick the one with the tagName
     * "HTML". For XML there should be only one top-level
     *
     * (HTML not yet supported.)
     * <p>
     * 方便的方法,允许直接访问被认为是实际文档内容的根的子节点对于HTML,在文档的顶层有多个元素是合法的,我们选择一个带有tagName"HTML "对于XML,应该只有一个顶级
     * 
     *  (尚不支持HTML)
     * 
     */
    public Element getDocumentElement() {
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return docElement;
    }

    /**
     * Return a <em>live</em> collection of all descendent Elements (not just
     * immediate children) having the specified tag name.
     *
     * <p>
     *  返回具有指定标记名称的所有派生元素(不仅仅是直接子项)的<em> live </em>集合
     * 
     * 
     * @param tagname The type of Element we want to gather. "*" will be
     * taken as a wildcard, meaning "all elements in the document."
     *
     * @see DeepNodeListImpl
     */
    public NodeList getElementsByTagName(String tagname) {
        return new DeepNodeListImpl(this,tagname);
    }

    /**
     * Retrieve information describing the abilities of this particular
     * DOM implementation. Intended to support applications that may be
     * using DOMs retrieved from several different sources, potentially
     * with different underlying representations.
     * <p>
     *  检索描述此特定DOM实现的能力的信息旨在支持可能使用从几个不同来源检索的DOM的应用程序,可能具有不同的底层表示
     * 
     */
    public DOMImplementation getImplementation() {
        // Currently implemented as a singleton, since it's hardcoded
        // information anyway.
        return CoreDOMImplementationImpl.getDOMImplementation();
    }

    //
    // Public methods
    //

    // properties

    /**
     * Sets whether the DOM implementation performs error checking
     * upon operations. Turning off error checking only affects
     * the following DOM checks:
     * <ul>
     * <li>Checking strings to make sure that all characters are
     *     legal XML characters
     * <li>Hierarchy checking such as allowed children, checks for
     *     cycles, etc.
     * </ul>
     * <p>
     * Turning off error checking does <em>not</em> turn off the
     * following checks:
     * <ul>
     * <li>Read only checks
     * <li>Checks related to DOM events
     * </ul>
     * <p>
     * 设置DOM实现是否对操作执行错误检查关闭错误检查仅影响以下DOM检查：
     * <ul>
     *  <li>检查字符串以确保所有字符都是合法的XML字符<li>层次结构检查(如允许的子项,检查周期等)
     * </ul>
     * <p>
     *  关闭错误检查<em>不会</em>关闭以下检查：
     * <ul>
     *  <li>只读检查<li>与DOM事件相关的检查
     * </ul>
     */

    public void setErrorChecking(boolean check) {
        errorChecking = check;
    }

    /*
     * DOM Level 3 WD - Experimental.
     * <p>
     *  DOM 3级WD  - 实验
     * 
     */
    public void setStrictErrorChecking(boolean check) {
        errorChecking = check;
    }

    /**
     * Returns true if the DOM implementation performs error checking.
     * <p>
     *  如果DOM实现执行错误检查,则返回true
     * 
     */
    public boolean getErrorChecking() {
        return errorChecking;
    }

    /*
     * DOM Level 3 WD - Experimental.
     * <p>
     *  DOM 3级WD  - 实验
     * 
     */
    public boolean getStrictErrorChecking() {
        return errorChecking;
    }


    /**
     * DOM Level 3 CR - Experimental. (Was getActualEncoding)
     *
     * An attribute specifying the encoding used for this document
     * at the time of the parsing. This is <code>null</code> when
     * it is not known, such as when the <code>Document</code> was
     * created in memory.
     * <p>
     *  DOM级别3 CR  - 实验性(getActualEncoding)
     * 
     * 指定在解析时用于此文档的编码的属性当不知道时,这是<code> null </code>,例如当<code> Document </code>在内存中创建时
     * 
     * 
     * @since DOM Level 3
     */
    public String getInputEncoding() {
        return actualEncoding;
    }

    /**
     * DOM Internal
     * (Was a DOM L3 Core WD public interface method setActualEncoding )
     *
     * An attribute specifying the actual encoding of this document. This is
     * <code>null</code> otherwise.
     * <br> This attribute represents the property [character encoding scheme]
     * defined in .
     * <p>
     *  DOM内部(是一个DOM L3核心WD公共接口方法setActualEncoding)
     * 
     *  指定此文档的实际编码的属性这是<code> null </code>,否则<br>此属性表示定义的属性[字符编码方案]
     * 
     */
    public void setInputEncoding(String value) {
        actualEncoding = value;
    }

    /**
     * DOM Internal
     * (Was a DOM L3 Core WD public interface method setXMLEncoding )
     *
     * An attribute specifying, as part of the XML declaration,
     * the encoding of this document. This is null when unspecified.
     * <p>
     *  DOM内部(是一个DOM L3核心WD公共接口方法setXMLEncoding)
     * 
     *  作为XML声明的一部分指定此文档的编码的属性当未指定时,此属性为null
     * 
     */
    public void setXmlEncoding(String value) {
        encoding = value;
    }

    /**
    /* <p>
    /* 
     * @deprecated This method is internal and only exists for
     * compatibility with older applications. New applications
     * should never call this method.
     */
    public void setEncoding(String value) {
        setXmlEncoding(value);
    }

    /**
     * DOM Level 3 WD - Experimental.
     * The encoding of this document (part of XML Declaration)
     * <p>
     *  DOM Level 3 WD  -  Experimental此文档的编码(XML声明的一部分)
     * 
     */
    public String getXmlEncoding() {
        return encoding;
    }

    /**
    /* <p>
    /* 
     * @deprecated This method is internal and only exists for
     * compatibility with older applications. New applications
     * should never call this method.
     */
    public String getEncoding() {
        return getXmlEncoding();
    }

    /**
     * DOM Level 3 CR - Experimental.
     * version - An attribute specifying, as part of the XML declaration,
     * the version number of this document.
     * <p>
     * DOM Level 3 CR  - 实验版本 - 作为XML声明的一部分的属性,指定此文档的版本号
     * 
     */
    public void setXmlVersion(String value) {
        if(value.equals("1.0") || value.equals("1.1")){
            //we need to change the flag value only --
            // when the version set is different than already set.
            if(!getXmlVersion().equals(value)){
                xmlVersionChanged = true ;
                //change the normalization value back to false
                isNormalized(false);
                version = value;
            }
        }
        else{
            //NOT_SUPPORTED_ERR: Raised if the vesion is set to a value that is not supported by
            //this document
            //we dont support any other XML version
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);

        }
        if((getXmlVersion()).equals("1.1")){
            xml11Version = true;
        }
        else{
            xml11Version = false;
        }
    }

    /**
    /* <p>
    /* 
     * @deprecated This method is internal and only exists for
     * compatibility with older applications. New applications
     * should never call this method.
     */
    public void setVersion(String value) {
        setXmlVersion(value);
    }

    /**
     * DOM Level 3 WD - Experimental.
     * The version of this document (part of XML Declaration)
     * <p>
     *  DOM级别3 WD  - 实验性本文档的版本(XML声明的一部分)
     * 
     */

    public String getXmlVersion() {
        return (version == null)?"1.0":version;
    }

    /**
    /* <p>
    /* 
     * @deprecated This method is internal and only exists for
     * compatibility with older applications. New applications
     * should never call this method.
     */
    public String getVersion() {
        return getXmlVersion();
    }

    /**
     * DOM Level 3 CR - Experimental.
     *
     * Xmlstandalone - An attribute specifying, as part of the XML declaration,
     * whether this document is standalone
     * <p>
     *  DOM级别3 CR  - 实验
     * 
     *  Xmlstandalone  - 作为XML声明的一部分指定此文档是否为独立的属性
     * 
     * 
     * @exception DOMException
     *    NOT_SUPPORTED_ERR: Raised if this document does not support the
     *   "XML" feature.
     * @since DOM Level 3
     */
    public void setXmlStandalone(boolean value)
                                  throws DOMException {
            standalone = value;
    }

    /**
    /* <p>
    /* 
     * @deprecated This method is internal and only exists for
     * compatibility with older applications. New applications
     * should never call this method.
     */
    public void setStandalone(boolean value) {
        setXmlStandalone(value);
    }

    /**
     * DOM Level 3 WD - Experimental.
     * standalone that specifies whether this document is standalone
     * (part of XML Declaration)
     * <p>
     *  DOM级别3 WD  - 实验独立版本,指定此文档是独立的(XML声明的一部分)
     * 
     */
    public boolean getXmlStandalone() {
        return standalone;
    }

    /**
    /* <p>
    /* 
     * @deprecated This method is internal and only exists for
     * compatibility with older applications. New applications
     * should never call this method.
     */
    public boolean getStandalone() {
        return getXmlStandalone();
    }

    /**
     * DOM Level 3 WD - Experimental.
     * The location of the document or <code>null</code> if undefined.
     * <br>Beware that when the <code>Document</code> supports the feature
     * "HTML" , the href attribute of the HTML BASE element takes precedence
     * over this attribute.
     * <p>
     *  DOM级别3 WD  - 实验性文档的位置或<code> null </code>如果未定义<br>请注意,当<code> Document </code>支持特性"HTML"时,HTML的href属
     * 性BASE元素优先于此属性。
     * 
     * 
     * @since DOM Level 3
     */
    public String getDocumentURI(){
        return fDocumentURI;
    }


    /**
     * DOM Level 3 WD - Experimental.
     * Renaming node
     * <p>
     *  DOM级别3 WD  - 实验重命名节点
     * 
     */
    public Node renameNode(Node n,String namespaceURI,String name)
    throws DOMException{

        if (errorChecking && n.getOwnerDocument() != this && n != this) {
            String msg = DOMMessageFormatter.formatMessage(
                    DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null);
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, msg);
        }
        switch (n.getNodeType()) {
            case ELEMENT_NODE: {
                ElementImpl el = (ElementImpl) n;
                if (el instanceof ElementNSImpl) {
                    ((ElementNSImpl) el).rename(namespaceURI, name);

                    // fire user data NODE_RENAMED event
                    callUserDataHandlers(el, null, UserDataHandler.NODE_RENAMED);
                }
                else {
                    if (namespaceURI == null) {
                        if (errorChecking) {
                            int colon1 = name.indexOf(':');
                            if(colon1 != -1){
                                String msg =
                                    DOMMessageFormatter.formatMessage(
                                            DOMMessageFormatter.DOM_DOMAIN,
                                            "NAMESPACE_ERR",
                                            null);
                                throw new DOMException(DOMException.NAMESPACE_ERR, msg);
                            }
                            if (!isXMLName(name,xml11Version)) {
                                String msg = DOMMessageFormatter.formatMessage(
                                        DOMMessageFormatter.DOM_DOMAIN,
                                        "INVALID_CHARACTER_ERR", null);
                                throw new DOMException(DOMException.INVALID_CHARACTER_ERR,
                                        msg);
                            }
                        }
                        el.rename(name);

                        // fire user data NODE_RENAMED event
                        callUserDataHandlers(el, null,
                                UserDataHandler.NODE_RENAMED);
                    }
                    else {
                        // we need to create a new object
                        ElementNSImpl nel =
                            new ElementNSImpl(this, namespaceURI, name);

                        // register event listeners on new node
                        copyEventListeners(el, nel);

                        // remove user data from old node
                        Hashtable data = removeUserDataTable(el);

                        // remove old node from parent if any
                        Node parent = el.getParentNode();
                        Node nextSib = el.getNextSibling();
                        if (parent != null) {
                            parent.removeChild(el);
                        }
                        // move children to new node
                        Node child = el.getFirstChild();
                        while (child != null) {
                            el.removeChild(child);
                            nel.appendChild(child);
                            child = el.getFirstChild();
                        }
                        // move specified attributes to new node
                        nel.moveSpecifiedAttributes(el);

                        // attach user data to new node
                        setUserDataTable(nel, data);

                        // and fire user data NODE_RENAMED event
                        callUserDataHandlers(el, nel,
                                UserDataHandler.NODE_RENAMED);

                        // insert new node where old one was
                        if (parent != null) {
                            parent.insertBefore(nel, nextSib);
                        }
                        el = nel;
                    }
                }
                // fire ElementNameChanged event
                renamedElement((Element) n, el);
                return el;
            }
            case ATTRIBUTE_NODE: {
                AttrImpl at = (AttrImpl) n;

                // dettach attr from element
                Element el = at.getOwnerElement();
                if (el != null) {
                    el.removeAttributeNode(at);
                }
                if (n instanceof AttrNSImpl) {
                    ((AttrNSImpl) at).rename(namespaceURI, name);
                    // reattach attr to element
                    if (el != null) {
                        el.setAttributeNodeNS(at);
                    }

                    // fire user data NODE_RENAMED event
                    callUserDataHandlers(at, null, UserDataHandler.NODE_RENAMED);
                }
                else {
                    if (namespaceURI == null) {
                        at.rename(name);
                        // reattach attr to element
                        if (el != null) {
                            el.setAttributeNode(at);
                        }

                        // fire user data NODE_RENAMED event
                        callUserDataHandlers(at, null, UserDataHandler.NODE_RENAMED);
                    }
                    else {
                        // we need to create a new object
                        AttrNSImpl nat = new AttrNSImpl(this, namespaceURI, name);

                        // register event listeners on new node
                        copyEventListeners(at, nat);

                        // remove user data from old node
                        Hashtable data = removeUserDataTable(at);

                        // move children to new node
                        Node child = at.getFirstChild();
                        while (child != null) {
                            at.removeChild(child);
                            nat.appendChild(child);
                            child = at.getFirstChild();
                        }

                        // attach user data to new node
                        setUserDataTable(nat, data);

                        // and fire user data NODE_RENAMED event
                        callUserDataHandlers(at, nat, UserDataHandler.NODE_RENAMED);

                        // reattach attr to element
                        if (el != null) {
                            el.setAttributeNode(nat);
                        }
                        at = nat;
                    }
                }
                // fire AttributeNameChanged event
                renamedAttrNode((Attr) n, at);

                return at;
            }
            default: {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
                throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
            }
        }

    }


    /**
     *  DOM Level 3 WD - Experimental
     *  Normalize document.
     * <p>
     * DOM级别3 WD  - 实验规范化文档
     * 
     */
    public void normalizeDocument(){
        // No need to normalize if already normalized.
        if (isNormalized() && !isNormalizeDocRequired()) {
            return;
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }

        if (domNormalizer == null) {
            domNormalizer = new DOMNormalizer();
        }

        if (fConfiguration == null) {
            fConfiguration =  new DOMConfigurationImpl();
        }
        else {
            fConfiguration.reset();
        }

        domNormalizer.normalizeDocument(this, fConfiguration);
        isNormalized(true);
        //set the XMLversion changed value to false -- once we have finished
        //doing normalization
        xmlVersionChanged = false ;
    }


    /**
     * DOM Level 3 CR - Experimental
     *
     *  The configuration used when <code>Document.normalizeDocument</code> is
     * invoked.
     * <p>
     *  DOM级别3 CR  - 实验
     * 
     *  调用<code> DocumentnormalizeDocument </code>时使用的配置
     * 
     * 
     * @since DOM Level 3
     */
    public DOMConfiguration getDomConfig(){
        if (fConfiguration == null) {
            fConfiguration = new DOMConfigurationImpl();
        }
        return fConfiguration;
    }


    /**
     * Returns the absolute base URI of this node or null if the implementation
     * wasn't able to obtain an absolute URI. Note: If the URI is malformed, a
     * null is returned.
     *
     * <p>
     *  返回此节点的绝对基本URI,如果实现无法获取绝对URI,则返回null注意：如果URI格式不正确,则返回null
     * 
     * 
     * @return The absolute base URI of this node or null.
     * @since DOM Level 3
     */
    public String getBaseURI() {
        if (fDocumentURI != null && fDocumentURI.length() != 0 ) {// attribute value is always empty string
            try {
                return new URI(fDocumentURI).toString();
            }
            catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e){
                // REVISIT: what should happen in this case?
                return null;
            }
        }
        return fDocumentURI;
    }

    /**
     * DOM Level 3 WD - Experimental.
     * <p>
     *  DOM 3级WD  - 实验
     * 
     */
    public void setDocumentURI(String documentURI){
        fDocumentURI = documentURI;
    }


    //
    // DOM L3 LS
    //
    /**
     * DOM Level 3 WD - Experimental.
     * Indicates whether the method load should be synchronous or
     * asynchronous. When the async attribute is set to <code>true</code>
     * the load method returns control to the caller before the document has
     * completed loading. The default value of this property is
     * <code>false</code>.
     * <br>Setting the value of this attribute might throw NOT_SUPPORTED_ERR
     * if the implementation doesn't support the mode the attribute is being
     * set to. Should the DOM spec define the default value of this
     * property? What if implementing both async and sync IO is impractical
     * in some systems?  2001-09-14. default is <code>false</code> but we
     * need to check with Mozilla and IE.
     * <p>
     * DOM Level 3 WD  -  Experimental指示方法加载应该是同步还是异步当async属性设置为<code> true </code>时,load方法在文档完成加载之前将控制权返回给调
     * 用者此默认值属性是<code> false </code> <br>设置此属性的值可能会抛出NOT_SUPPORTED_ERR如果实现不支持该属性被设置为模式应该DOM规范定义此属性的默认值吗?如果在一
     * 些系统中实现异步和同步IO是不切实际的,怎么办? 2001-09-14默认是<code> false </code>,但我们需要检查Mozilla和IE。
     * 
     */
    public boolean getAsync() {
        return false;
    }

    /**
     * DOM Level 3 WD - Experimental.
     * Indicates whether the method load should be synchronous or
     * asynchronous. When the async attribute is set to <code>true</code>
     * the load method returns control to the caller before the document has
     * completed loading. The default value of this property is
     * <code>false</code>.
     * <br>Setting the value of this attribute might throw NOT_SUPPORTED_ERR
     * if the implementation doesn't support the mode the attribute is being
     * set to. Should the DOM spec define the default value of this
     * property? What if implementing both async and sync IO is impractical
     * in some systems?  2001-09-14. default is <code>false</code> but we
     * need to check with Mozilla and IE.
     * <p>
     * DOM Level 3 WD  -  Experimental指示方法加载应该是同步还是异步当async属性设置为<code> true </code>时,load方法在文档完成加载之前将控制权返回给调
     * 用者此默认值属性是<code> false </code> <br>设置此属性的值可能会抛出NOT_SUPPORTED_ERR如果实现不支持该属性被设置为模式应该DOM规范定义此属性的默认值吗?如果在一
     * 些系统中实现异步和同步IO是不切实际的,怎么办? 2001-09-14默认是<code> false </code>,但我们需要检查Mozilla和IE。
     * 
     */
    public void setAsync(boolean async) {
        if (async) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
        }
    }
    /**
     * DOM Level 3 WD - Experimental.
     * If the document is currently being loaded as a result of the method
     * <code>load</code> being invoked the loading and parsing is
     * immediately aborted. The possibly partial result of parsing the
     * document is discarded and the document is cleared.
     * <p>
     * DOM Level 3 WD  -  Experimental如果文档当前正在被调用的方法<code> load </code>被加载,加载和解析立即中止。解析文档的可能部分结果被丢弃,文档清除
     * 
     */
    public void abort() {
    }

    /**
     * DOM Level 3 WD - Experimental.
     *
     * Replaces the content of the document with the result of parsing the
     * given URI. Invoking this method will either block the caller or
     * return to the caller immediately depending on the value of the async
     * attribute. Once the document is fully loaded a "load" event (as
     * defined in [<a href='http://www.w3.org/TR/2003/WD-DOM-Level-3-Events-20030331'>DOM Level 3 Events</a>]
     * , except that the <code>Event.targetNode</code> will be the document,
     * not an element) will be dispatched on the document. If an error
     * occurs, an implementation dependent "error" event will be dispatched
     * on the document. If this method is called on a document that is
     * currently loading, the current load is interrupted and the new URI
     * load is initiated.
     * <br> When invoking this method the parameters used in the
     * <code>DOMParser</code> interface are assumed to have their default
     * values with the exception that the parameters <code>"entities"</code>
     * , <code>"normalize-characters"</code>,
     * <code>"check-character-normalization"</code> are set to
     * <code>"false"</code>.
     * <br> The result of a call to this method is the same the result of a
     * call to <code>DOMParser.parseWithContext</code> with an input stream
     * referencing the URI that was passed to this call, the document as the
     * context node, and the action <code>ACTION_REPLACE_CHILDREN</code>.
     * <p>
     *  DOM 3级WD  - 实验
     * 
     * 用解析给定URI的结果替换文档的内容调用此方法将根据async属性的值立即阻止调用者或返回到调用者一旦文档完全加载了一个"加载"事件(如定义的在[<a href='http://wwww3org/TR/2003/WD-DOM-Level-3-Events-20030331'>
     *  DOM 3级事件</a>]中,除了<code> EventtargetNode </code >将是文档,而不是元素)将被分派到文档上如果发生错误,将在文档上调度实现相关的"错误"事件如果对当前正在加
     * 载的文档调用此方法,当前负载并中断新的URI加载<br>当调用此方法时,假定<code> DOMParser </code>接口中使用的参数具有其默认值,但参数<code>"entities"</code>
     * ,<code> -characters"</code>,<code>"check-character-normalization"</code>设置为<code>"false"</code> <br>调
     * 用此方法的结果是相同的调用<code> DOMParserparseWithContext </code>的结果,该输入流引用传递给此调用的URI,作为上下文节点的文档和操作<code> ACTION_
     * REPLACE_CHILDREN </code>。
     * 
     * 
     * @param uri The URI reference for the XML file to be loaded. If this is
     *  a relative URI, the base URI used by the implementation is
     *  implementation dependent.
     * @return If async is set to <code>true</code> <code>load</code> returns
     *   <code>true</code> if the document load was successfully initiated.
     *   If an error occurred when initiating the document load,
     *   <code>load</code> returns <code>false</code>.If async is set to
     *   <code>false</code> <code>load</code> returns <code>true</code> if
     *   the document was successfully loaded and parsed. If an error
     *   occurred when either loading or parsing the URI, <code>load</code>
     *   returns <code>false</code>.
     */
    public boolean load(String uri) {
        return false;
    }

    /**
     * DOM Level 3 WD - Experimental.
     * Replace the content of the document with the result of parsing the
     * input string, this method is always synchronous.
     * <p>
     * DOM Level 3 WD  -  Experimental用解析输入字符串的结果替换文档的内容,此方法总是同步的
     * 
     * 
     * @param source A string containing an XML document.
     * @return <code>true</code> if parsing the input string succeeded
     *   without errors, otherwise <code>false</code>.
     */
    public boolean loadXML(String source) {
        return false;
    }

    /**
     * DOM Level 3 WD - Experimental.
     * Save the document or the given node and all its descendants to a string
     * (i.e. serialize the document or node).
     * <br>The parameters used in the <code>LSSerializer</code> interface are
     * assumed to have their default values when invoking this method.
     * <br> The result of a call to this method is the same the result of a
     * call to <code>LSSerializer.writeToString</code> with the document as
     * the node to write.
     * <p>
     *  DOM Level 3 WD  -  Experimental将文档或给定节点及其所有后代保存为字符串(即序列化文档或节点)<br>假设<code> LSSerializer </code>接口中使用
     * 的参数具有调用此方法时的默认值<br>调用此方法的结果与调用<code> LSserializerwriteToString </code>的结果相同,文档作为要写入的节点。
     * 
     * 
     * @param node Specifies what to serialize, if this parameter is
     *   <code>null</code> the whole document is serialized, if it's
     *   non-null the given node is serialized.
     * @return The serialized document or <code>null</code> in case an error
     *   occurred.
     * @exception DOMException
     *   WRONG_DOCUMENT_ERR: Raised if the node passed in as the node
     *   parameter is from an other document.
     */
    public String saveXML(Node node)
    throws DOMException {
        if ( errorChecking && node != null &&
            this != node.getOwnerDocument() ) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null);
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, msg);
        }
        DOMImplementationLS domImplLS = (DOMImplementationLS)DOMImplementationImpl.getDOMImplementation();
        LSSerializer xmlWriter = domImplLS.createLSSerializer();
        if (node == null) {
            node = this;
        }
        return xmlWriter.writeToString(node);
    }

    /**
     * Sets whether the DOM implementation generates mutation events
     * upon operations.
     * <p>
     *  设置DOM实现是否在操作时生成突变事件
     * 
     */
    void setMutationEvents(boolean set) {
        // does nothing by default - overidden in subclass
    }

    /**
     * Returns true if the DOM implementation generates mutation events.
     * <p>
     *  如果DOM实施生成突变事件,则返回true
     * 
     */
    boolean getMutationEvents() {
        // does nothing by default - overriden in subclass
        return false;
    }



    // non-DOM factory methods

    /**
     * NON-DOM
     * Factory method; creates a DocumentType having this Document
     * as its OwnerDoc. (REC-DOM-Level-1-19981001 left the process of building
     * DTD information unspecified.)
     *
     * <p>
     * NON-DOM工厂方法;创建具有此文档作为其OwnerDoc的DocumentType(REC-DOM-Level-1-19981001留下未指定的DTD信息的处理)
     * 
     * 
     * @param name The name of the Entity we wish to provide a value for.
     *
     * @throws DOMException(NOT_SUPPORTED_ERR) for HTML documents, where
     * DTDs are not permitted. (HTML not yet implemented.)
     */
    public DocumentType createDocumentType(String qualifiedName,
    String publicID,
    String systemID)
    throws DOMException {

        return new DocumentTypeImpl(this, qualifiedName, publicID, systemID);

    } // createDocumentType(String):DocumentType

    /**
     * NON-DOM
     * Factory method; creates an Entity having this Document
     * as its OwnerDoc. (REC-DOM-Level-1-19981001 left the process of building
     * DTD information unspecified.)
     *
     * <p>
     *  NON-DOM工厂方法;创建具有此文档作为其OwnerDoc的实体(REC-DOM-Level-1-19981001留下未指定的DTD信息的建立过程)
     * 
     * 
     * @param name The name of the Entity we wish to provide a value for.
     *
     * @throws DOMException(NOT_SUPPORTED_ERR) for HTML documents, where
     * nonstandard entities are not permitted. (HTML not yet
     * implemented.)
     */
    public Entity createEntity(String name)
    throws DOMException {


        if (errorChecking && !isXMLName(name,xml11Version)) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
        return new EntityImpl(this, name);

    } // createEntity(String):Entity

    /**
     * NON-DOM
     * Factory method; creates a Notation having this Document
     * as its OwnerDoc. (REC-DOM-Level-1-19981001 left the process of building
     * DTD information unspecified.)
     *
     * <p>
     *  NON-DOM工厂方法;创建一个将此文档作为其OwnerDoc的记法(REC-DOM-Level-1-19981001留下未指定的DTD信息的过程)
     * 
     * 
     * @param name The name of the Notation we wish to describe
     *
     * @throws DOMException(NOT_SUPPORTED_ERR) for HTML documents, where
     * notations are not permitted. (HTML not yet
     * implemented.)
     */
    public Notation createNotation(String name)
    throws DOMException {

        if (errorChecking && !isXMLName(name,xml11Version)) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
        return new NotationImpl(this, name);

    } // createNotation(String):Notation

    /**
     * NON-DOM Factory method: creates an element definition. Element
     * definitions hold default attribute values.
     * <p>
     *  NON-DOM工厂方法：创建元素定义元素定义保存默认属性值
     * 
     */
    public ElementDefinitionImpl createElementDefinition(String name)
    throws DOMException {

        if (errorChecking && !isXMLName(name,xml11Version)) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_CHARACTER_ERR", null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
        return new ElementDefinitionImpl(this, name);

    } // createElementDefinition(String):ElementDefinitionImpl

    // other non-DOM methods

    /** NON-DOM:  Get the number associated with this document.   Used to
     * order documents in the implementation.
     * <p>
     *  订单文档的实现
     * 
     */
    protected int getNodeNumber() {
        if (documentNumber==0) {

            CoreDOMImplementationImpl cd = (CoreDOMImplementationImpl)CoreDOMImplementationImpl.getDOMImplementation();
            documentNumber = cd.assignDocumentNumber();
        }
        return documentNumber;
    }


    /** NON-DOM:  Get a number associated with a node created with respect
     * to this document.   Needed for compareDocumentPosition when nodes
     * are disconnected.  This is only used on demand.
     * <p>
     *  到本文档节点断开时需要compareDocumentPosition这只能按需使用
     * 
     */
    protected int getNodeNumber(Node node) {

        // Check if the node is already in the hash
        // If so, retrieve the node number
        // If not, assign a number to the node
        // Node numbers are negative, from -1 to -n
        int num;
        if (nodeTable == null) {
            nodeTable = new Hashtable();
            num = --nodeCounter;
            nodeTable.put(node, new Integer(num));
        }
        else {
            Integer n = (Integer)nodeTable.get(node);
            if (n== null) {
                num = --nodeCounter;
                nodeTable.put(node, new Integer(num));
            }
            else
                num = n.intValue();
        }
        return num;
    }

    /**
     * Copies a node from another document to this document. The new nodes are
     * created using this document's factory methods and are populated with the
     * data from the source's accessor methods defined by the DOM interfaces.
     * Its behavior is otherwise similar to that of cloneNode.
     * <p>
     * According to the DOM specifications, document nodes cannot be imported
     * and a NOT_SUPPORTED_ERR exception is thrown if attempted.
     * <p>
     * 将节点从另一个文档复制到此文档使用此文档的工厂方法创建新节点,并使用源自DOM接口定义的访问器方法的数据填充其数据其行为类似于cloneNode
     * <p>
     *  根据DOM规范,不能导入文档节点,并且如果尝试则抛出NOT_SUPPORTED_ERR异常
     * 
     */
    public Node importNode(Node source, boolean deep)
    throws DOMException {
        return importNode(source, deep, false, null);
    } // importNode(Node,boolean):Node

    /**
     * Overloaded implementation of DOM's importNode method. This method
     * provides the core functionality for the public importNode and cloneNode
     * methods.
     *
     * The reversedIdentifiers parameter is provided for cloneNode to
     * preserve the document's identifiers. The Hashtable has Elements as the
     * keys and their identifiers as the values. When an element is being
     * imported, a check is done for an associated identifier. If one exists,
     * the identifier is registered with the new, imported element. If
     * reversedIdentifiers is null, the parameter is not applied.
     * <p>
     *  DOM的importNode方法的重载实现此方法为公共importNode和cloneNode方法提供核心功能
     * 
     * 为cloneNode提供invertedIdentifiers参数以保留文档的标识符Hashtable具有作为键的元素和作为值的标识符当导入元素时,将对相关标识符进行检查如果存在,则将标识符注册到new
     * ,imported元素如果reversedIdentifiers为null,则不应用参数。
     * 
     */
    private Node importNode(Node source, boolean deep, boolean cloningDoc,
    Hashtable reversedIdentifiers)
    throws DOMException {
        Node newnode=null;
                Hashtable userData = null;

        // Sigh. This doesn't work; too many nodes have private data that
        // would have to be manually tweaked. May be able to add local
        // shortcuts to each nodetype. Consider ?????
        // if(source instanceof NodeImpl &&
        //  !(source instanceof DocumentImpl))
        // {
        //  // Can't clone DocumentImpl since it invokes us...
        //  newnode=(NodeImpl)source.cloneNode(false);
        //  newnode.ownerDocument=this;
        // }
        // else
                if(source instanceof NodeImpl)
                        userData = ((NodeImpl)source).getUserDataRecord();
        int type = source.getNodeType();
        switch (type) {
            case ELEMENT_NODE: {
                Element newElement;
                boolean domLevel20 = source.getOwnerDocument().getImplementation().hasFeature("XML", "2.0");
                // Create element according to namespace support/qualification.
                if(domLevel20 == false || source.getLocalName() == null)
                    newElement = createElement(source.getNodeName());
                else
                    newElement = createElementNS(source.getNamespaceURI(),
                    source.getNodeName());

                // Copy element's attributes, if any.
                NamedNodeMap sourceAttrs = source.getAttributes();
                if (sourceAttrs != null) {
                    int length = sourceAttrs.getLength();
                    for (int index = 0; index < length; index++) {
                        Attr attr = (Attr)sourceAttrs.item(index);

                        // NOTE: this methods is used for both importingNode
                        // and cloning the document node. In case of the
                        // clonning default attributes should be copied.
                        // But for importNode defaults should be ignored.
                        if (attr.getSpecified() || cloningDoc) {
                            Attr newAttr = (Attr)importNode(attr, true, cloningDoc,
                            reversedIdentifiers);

                            // Attach attribute according to namespace
                            // support/qualification.
                            if (domLevel20 == false ||
                            attr.getLocalName() == null)
                                newElement.setAttributeNode(newAttr);
                            else
                                newElement.setAttributeNodeNS(newAttr);
                        }
                    }
                }

                // Register element identifier.
                if (reversedIdentifiers != null) {
                    // Does element have an associated identifier?
                    Object elementId = reversedIdentifiers.get(source);
                    if (elementId != null) {
                        if (identifiers == null)
                            identifiers = new Hashtable();

                        identifiers.put(elementId, newElement);
                    }
                }

                newnode = newElement;
                break;
            }

            case ATTRIBUTE_NODE: {

                if( source.getOwnerDocument().getImplementation().hasFeature("XML", "2.0") ){
                    if (source.getLocalName() == null) {
                        newnode = createAttribute(source.getNodeName());
                    } else {
                        newnode = createAttributeNS(source.getNamespaceURI(),
                        source.getNodeName());
                    }
                }
                else {
                    newnode = createAttribute(source.getNodeName());
                }
                // if source is an AttrImpl from this very same implementation
                // avoid creating the child nodes if possible
                if (source instanceof AttrImpl) {
                    AttrImpl attr = (AttrImpl) source;
                    if (attr.hasStringValue()) {
                        AttrImpl newattr = (AttrImpl) newnode;
                        newattr.setValue(attr.getValue());
                        deep = false;
                    }
                    else {
                        deep = true;
                    }
                }
                else {
                    // According to the DOM spec the kids carry the value.
                    // However, there are non compliant implementations out
                    // there that fail to do so. To avoid ending up with no
                    // value at all, in this case we simply copy the text value
                    // directly.
                    if (source.getFirstChild() == null) {
                        newnode.setNodeValue(source.getNodeValue());
                        deep = false;
                    } else {
                        deep = true;
                    }
                }
                break;
            }

            case TEXT_NODE: {
                newnode = createTextNode(source.getNodeValue());
                break;
            }

            case CDATA_SECTION_NODE: {
                newnode = createCDATASection(source.getNodeValue());
                break;
            }

            case ENTITY_REFERENCE_NODE: {
                newnode = createEntityReference(source.getNodeName());
                // the subtree is created according to this doc by the method
                // above, so avoid carrying over original subtree
                deep = false;
                break;
            }

            case ENTITY_NODE: {
                Entity srcentity = (Entity)source;
                EntityImpl newentity =
                (EntityImpl)createEntity(source.getNodeName());
                newentity.setPublicId(srcentity.getPublicId());
                newentity.setSystemId(srcentity.getSystemId());
                newentity.setNotationName(srcentity.getNotationName());
                // Kids carry additional value,
                // allow deep import temporarily
                newentity.isReadOnly(false);
                newnode = newentity;
                break;
            }

            case PROCESSING_INSTRUCTION_NODE: {
                newnode = createProcessingInstruction(source.getNodeName(),
                source.getNodeValue());
                break;
            }

            case COMMENT_NODE: {
                newnode = createComment(source.getNodeValue());
                break;
            }

            case DOCUMENT_TYPE_NODE: {
                // unless this is used as part of cloning a Document
                // forbid it for the sake of being compliant to the DOM spec
                if (!cloningDoc) {
                    String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
                    throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
                }
                DocumentType srcdoctype = (DocumentType)source;
                DocumentTypeImpl newdoctype = (DocumentTypeImpl)
                createDocumentType(srcdoctype.getNodeName(),
                srcdoctype.getPublicId(),
                srcdoctype.getSystemId());
                // Values are on NamedNodeMaps
                NamedNodeMap smap = srcdoctype.getEntities();
                NamedNodeMap tmap = newdoctype.getEntities();
                if(smap != null) {
                    for(int i = 0; i < smap.getLength(); i++) {
                        tmap.setNamedItem(importNode(smap.item(i), true, true,
                        reversedIdentifiers));
                    }
                }
                smap = srcdoctype.getNotations();
                tmap = newdoctype.getNotations();
                if (smap != null) {
                    for(int i = 0; i < smap.getLength(); i++) {
                        tmap.setNamedItem(importNode(smap.item(i), true, true,
                        reversedIdentifiers));
                    }
                }

                // NOTE: At this time, the DOM definition of DocumentType
                // doesn't cover Elements and their Attributes. domimpl's
                // extentions in that area will not be preserved, even if
                // copying from domimpl to domimpl. We could special-case
                // that here. Arguably we should. Consider. ?????
                newnode = newdoctype;
                break;
            }

            case DOCUMENT_FRAGMENT_NODE: {
                newnode = createDocumentFragment();
                // No name, kids carry value
                break;
            }

            case NOTATION_NODE: {
                Notation srcnotation = (Notation)source;
                NotationImpl newnotation =
                (NotationImpl)createNotation(source.getNodeName());
                newnotation.setPublicId(srcnotation.getPublicId());
                newnotation.setSystemId(srcnotation.getSystemId());
                // Kids carry additional value
                newnode = newnotation;
                // No name, no value
                break;
            }
            case DOCUMENT_NODE : // Can't import document nodes
            default: {           // Unknown node type
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
                throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
            }
        }

                if(userData != null)
                        callUserDataHandlers(source, newnode, UserDataHandler.NODE_IMPORTED,userData);

        // If deep, replicate and attach the kids.
        if (deep) {
            for (Node srckid = source.getFirstChild();
            srckid != null;
            srckid = srckid.getNextSibling()) {
                newnode.appendChild(importNode(srckid, true, cloningDoc,
                reversedIdentifiers));
            }
        }
        if (newnode.getNodeType() == Node.ENTITY_NODE) {
            ((NodeImpl)newnode).setReadOnly(true, true);
        }
        return newnode;

    } // importNode(Node,boolean,boolean,Hashtable):Node

    /**
     * DOM Level 3 WD - Experimental
     * Change the node's ownerDocument, and its subtree, to this Document
     *
     * <p>
     *  DOM Level 3 WD  -  Experimental将节点的ownerDocument及其子树更改为此文档
     * 
     * 
     * @param source The node to adopt.
     * @see #importNode
     **/
    public Node adoptNode(Node source) {
        NodeImpl node;
                Hashtable userData = null;
        try {
            node = (NodeImpl) source;
        } catch (ClassCastException e) {
            // source node comes from a different DOMImplementation
            return null;
        }

        // Return null if the source is null

        if (source == null ) {
                return null;
        } else if (source != null && source.getOwnerDocument() != null) {

            DOMImplementation thisImpl = this.getImplementation();
            DOMImplementation otherImpl = source.getOwnerDocument().getImplementation();

            // when the source node comes from a different implementation.
            if (thisImpl != otherImpl) {

                // Adopting from a DefferedDOM to DOM
                if (thisImpl instanceof com.sun.org.apache.xerces.internal.dom.DOMImplementationImpl &&
                        otherImpl instanceof com.sun.org.apache.xerces.internal.dom.DeferredDOMImplementationImpl) {
                    // traverse the DOM and expand deffered nodes and then allow adoption
                    undeferChildren (node);
                } else if ( thisImpl instanceof com.sun.org.apache.xerces.internal.dom.DeferredDOMImplementationImpl
                        && otherImpl instanceof com.sun.org.apache.xerces.internal.dom.DOMImplementationImpl) {
                    // Adopting from a DOM into a DefferedDOM, this should be okay
                } else {
                    // Adopting between two dissimilar DOM's is not allowed
                    return null;
                }
                }
        }

        switch (node.getNodeType()) {
            case ATTRIBUTE_NODE: {
                AttrImpl attr = (AttrImpl) node;
                // remove node from wherever it is
                if( attr.getOwnerElement() != null){
                    //1. owner element attribute is set to null
                    attr.getOwnerElement().removeAttributeNode(attr);
                }
                //2. specified flag is set to true
                attr.isSpecified(true);
                                userData = node.getUserDataRecord();

                //3. change ownership
                attr.setOwnerDocument(this);
                                if(userData != null )
                                        setUserDataTable(node,userData);
                break;
            }
            //entity, notation nodes are read only nodes.. so they can't be adopted.
            //runtime will fall through to NOTATION_NODE
            case ENTITY_NODE:
            case NOTATION_NODE:{
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);

            }
            //document, documentype nodes can't be adopted.
            //runtime will fall through to DocumentTypeNode
            case DOCUMENT_NODE:
            case DOCUMENT_TYPE_NODE: {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
                throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
            }
            case ENTITY_REFERENCE_NODE: {
                                userData = node.getUserDataRecord();
                // remove node from wherever it is
                Node parent = node.getParentNode();
                if (parent != null) {
                    parent.removeChild(source);
                }
                // discard its replacement value
                Node child;
                while ((child = node.getFirstChild()) != null) {
                    node.removeChild(child);
                }
                // change ownership
                node.setOwnerDocument(this);
                                if(userData != null)
                                        setUserDataTable(node,userData);
                // set its new replacement value if any
                if (docType == null) {
                    break;
                }
                NamedNodeMap entities = docType.getEntities();
                Node entityNode = entities.getNamedItem(node.getNodeName());
                if (entityNode == null) {
                    break;
                }
                for (child = entityNode.getFirstChild();
                child != null; child = child.getNextSibling()) {
                    Node childClone = child.cloneNode(true);
                    node.appendChild(childClone);
                }
                break;
            }
            case ELEMENT_NODE: {
                                userData = node.getUserDataRecord();
                // remove node from wherever it is
                Node parent = node.getParentNode();
                if (parent != null) {
                    parent.removeChild(source);
                }
                // change ownership
                node.setOwnerDocument(this);
                                if(userData != null)
                                        setUserDataTable(node,userData);
                // reconcile default attributes
                ((ElementImpl)node).reconcileDefaultAttributes();
                break;
            }
            default: {
                                userData = node.getUserDataRecord();
                // remove node from wherever it is
                Node parent = node.getParentNode();
                if (parent != null) {
                    parent.removeChild(source);
                }
                // change ownership
                node.setOwnerDocument(this);
                                if(userData != null)
                                        setUserDataTable(node,userData);
            }
        }

                //DOM L3 Core CR
                //http://www.w3.org/TR/2003/CR-DOM-Level-3-Core-20031107/core.html#UserDataHandler-ADOPTED
                if(userData != null)
                        callUserDataHandlers(source, null, UserDataHandler.NODE_ADOPTED,userData);

        return node;
    }

    /**
     * Traverses the DOM Tree and expands deferred nodes and their
     * children.
     *
     * <p>
     *  遍历DOM树并扩展延迟的节点及其子节点
     * 
     */
    protected void undeferChildren(Node node) {

        Node top = node;

        while (null != node) {

            if (((NodeImpl)node).needsSyncData()) {
                ((NodeImpl)node).synchronizeData();
            }

            NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                int length = attributes.getLength();
                for (int i = 0; i < length; ++i) {
                    undeferChildren(attributes.item(i));
                }
            }

            Node nextNode = null;
            nextNode = node.getFirstChild();

            while (null == nextNode) {

                if (top.equals(node))
                    break;

                nextNode = node.getNextSibling();

                if (null == nextNode) {
                    node = node.getParentNode();

                    if ((null == node) || (top.equals(node))) {
                        nextNode = null;
                        break;
                    }
                }
            }

            node = nextNode;
        }
    }

    // identifier maintenence
    /**
     * Introduced in DOM Level 2
     * Returns the Element whose ID is given by elementId. If no such element
     * exists, returns null. Behavior is not defined if more than one element
     * has this ID.
     * <p>
     * Note: The DOM implementation must have information that says which
     * attributes are of type ID. Attributes with the name "ID" are not of type
     * ID unless so defined. Implementations that do not know whether
     * attributes are of type ID or not are expected to return null.
     * <p>
     *  在DOM级别2中引入返回元素的ID由elementId指定如果没有这样的元素存在,则返回null如果多个元素具有此ID,则不定义行为
     * <p>
     * 注意：DOM实现必须具有说明哪些属性是ID类型的属性的信息,具有名称"ID"的属性不是类型ID,除非这样定义不知道属性是否为ID类型的实现应该返回null
     * 
     * 
     * @see #getIdentifier
     */
    public Element getElementById(String elementId) {
        return getIdentifier(elementId);
    }

    /**
     * Remove all identifiers from the ID table
     * <p>
     *  从ID表中删除所有标识符
     * 
     */
    protected final void clearIdentifiers(){
        if (identifiers != null){
            identifiers.clear();
        }
    }

    /**
     * Registers an identifier name with a specified element node.
     * If the identifier is already registered, the new element
     * node replaces the previous node. If the specified element
     * node is null, removeIdentifier() is called.
     *
     * <p>
     *  向指定的元素节点注册标识符名称如果标识符已注册,则新的元素节点将替换上一个节点如果指定的元素节点为null,则会调用removeIdentifier()
     * 
     * 
     * @see #getIdentifier
     * @see #removeIdentifier
     */
    public void putIdentifier(String idName, Element element) {

        if (element == null) {
            removeIdentifier(idName);
            return;
        }

        if (needsSyncData()) {
            synchronizeData();
        }

        if (identifiers == null) {
            identifiers = new Hashtable();
        }

        identifiers.put(idName, element);

    } // putIdentifier(String,Element)

    /**
     * Returns a previously registered element with the specified
     * identifier name, or null if no element is registered.
     *
     * <p>
     *  返回具有指定标识符名称的先前注册的元素,如果没有注册元素,则返回null
     * 
     * 
     * @see #putIdentifier
     * @see #removeIdentifier
     */
    public Element getIdentifier(String idName) {

        if (needsSyncData()) {
            synchronizeData();
        }

        if (identifiers == null) {
            return null;
        }
        Element elem = (Element) identifiers.get(idName);
        if (elem != null) {
            // check that the element is in the tree
            Node parent = elem.getParentNode();
            while (parent != null) {
                if (parent == this) {
                    return elem;
                }
                parent = parent.getParentNode();
            }
        }
        return null;
    } // getIdentifier(String):Element

    /**
     * Removes a previously registered element with the specified
     * identifier name.
     *
     * <p>
     *  删除具有指定标识符名称的先前注册的元素
     * 
     * 
     * @see #putIdentifier
     * @see #getIdentifier
     */
    public void removeIdentifier(String idName) {

        if (needsSyncData()) {
            synchronizeData();
        }

        if (identifiers == null) {
            return;
        }

        identifiers.remove(idName);

    } // removeIdentifier(String)

    /** Returns an enumeration registered of identifier names. */
    public Enumeration getIdentifiers() {

        if (needsSyncData()) {
            synchronizeData();
        }

        if (identifiers == null) {
            identifiers = new Hashtable();
        }

        return identifiers.keys();

    } // getIdentifiers():Enumeration

    //
    // DOM2: Namespace methods
    //

    /**
     * Introduced in DOM Level 2. <p>
     * Creates an element of the given qualified name and namespace URI.
     * If the given namespaceURI is null or an empty string and the
     * qualifiedName has a prefix that is "xml", the created element
     * is bound to the predefined namespace
     * "http://www.w3.org/XML/1998/namespace" [Namespaces].
     * <p>
     * 在DOM级别2中引入<p>创建给定限定名称和命名空间URI的元素如果给定的namespaceURI为null或空字符串,且qualifiedName的前缀为"xml",则创建的元素将绑定到预定义的命名空
     * 间"http：// wwww3org / XML / 1998 / namespace"[Namespaces]。
     * 
     * 
     * @param namespaceURI The namespace URI of the element to
     *                     create.
     * @param qualifiedName The qualified name of the element type to
     *                      instantiate.
     * @return Element A new Element object with the following attributes:
     * @throws DOMException INVALID_CHARACTER_ERR: Raised if the specified
     * name contains an invalid character.
     * @throws DOMException NAMESPACE_ERR: Raised if the qualifiedName has a
     *                      prefix that is "xml" and the namespaceURI is
     *                      neither null nor an empty string nor
     *                      "http://www.w3.org/XML/1998/namespace", or
     *                      if the qualifiedName has a prefix different
     *                      from "xml" and the namespaceURI is null or an
     *                      empty string.
     * @since WD-DOM-Level-2-19990923
     */
    public Element createElementNS(String namespaceURI, String qualifiedName)
    throws DOMException {
        return new ElementNSImpl(this, namespaceURI, qualifiedName);
    }

    /**
     * NON-DOM: a factory method used by the Xerces DOM parser
     * to create an element.
     *
     * <p>
     *  NON-DOM：Xerces DOM解析器用来创建元素的工厂方法
     * 
     * 
     * @param namespaceURI The namespace URI of the element to
     *                     create.
     * @param qualifiedName The qualified name of the element type to
     *                      instantiate.
     * @param localpart  The local name of the attribute to instantiate.
     *
     * @return Element A new Element object with the following attributes:
     * @exception DOMException INVALID_CHARACTER_ERR: Raised if the specified
     *                   name contains an invalid character.
     */
    public Element createElementNS(String namespaceURI, String qualifiedName,
    String localpart)
    throws DOMException {
        return new ElementNSImpl(this, namespaceURI, qualifiedName, localpart);
    }

    /**
     * Introduced in DOM Level 2. <p>
     * Creates an attribute of the given qualified name and namespace URI.
     * If the given namespaceURI is null or an empty string and the
     * qualifiedName has a prefix that is "xml", the created element
     * is bound to the predefined namespace
     * "http://www.w3.org/XML/1998/namespace" [Namespaces].
     *
     * <p>
     *  在DOM级别2中引入<p>创建给定限定名称和命名空间URI的属性如果给定的namespaceURI为null或空字符串,且qualifiedName的前缀为"xml",则创建的元素将绑定到预定义的命名
     * 空间"http：// wwww3org / XML / 1998 / namespace"[Namespaces]。
     * 
     * 
     * @param namespaceURI  The namespace URI of the attribute to
     *                      create. When it is null or an empty string,
     *                      this method behaves like createAttribute.
     * @param qualifiedName The qualified name of the attribute to
     *                      instantiate.
     * @return Attr         A new Attr object.
     * @throws DOMException INVALID_CHARACTER_ERR: Raised if the specified
     * name contains an invalid character.
     * @since WD-DOM-Level-2-19990923
     */
    public Attr createAttributeNS(String namespaceURI, String qualifiedName)
    throws DOMException {
        return new AttrNSImpl(this, namespaceURI, qualifiedName);
    }

    /**
     * NON-DOM: a factory method used by the Xerces DOM parser
     * to create an element.
     *
     * <p>
     *  NON-DOM：Xerces DOM解析器用来创建元素的工厂方法
     * 
     * 
     * @param namespaceURI  The namespace URI of the attribute to
     *                      create. When it is null or an empty string,
     *                      this method behaves like createAttribute.
     * @param qualifiedName The qualified name of the attribute to
     *                      instantiate.
     * @param localpart     The local name of the attribute to instantiate.
     *
     * @return Attr         A new Attr object.
     * @throws DOMException INVALID_CHARACTER_ERR: Raised if the specified
     * name contains an invalid character.
     */
    public Attr createAttributeNS(String namespaceURI, String qualifiedName,
    String localpart)
    throws DOMException {
        return new AttrNSImpl(this, namespaceURI, qualifiedName, localpart);
    }

    /**
     * Introduced in DOM Level 2. <p>
     * Returns a NodeList of all the Elements with a given local name and
     * namespace URI in the order in which they would be encountered in a
     * preorder traversal of the Document tree.
     * <p>
     * 在DOM级别2中引入<p>返回具有给定本地名称和命名空间URI的所有元素的NodeList,按照它们在文档树的预先遍历中遇到的顺序返回
     * 
     * 
     * @param namespaceURI  The namespace URI of the elements to match
     *                      on. The special value "*" matches all
     *                      namespaces. When it is null or an empty
     *                      string, this method behaves like
     *                      getElementsByTagName.
     * @param localName     The local name of the elements to match on.
     *                      The special value "*" matches all local names.
     * @return NodeList     A new NodeList object containing all the matched
     *                      Elements.
     * @since WD-DOM-Level-2-19990923
     */
    public NodeList getElementsByTagNameNS(String namespaceURI,
    String localName) {
        return new DeepNodeListImpl(this, namespaceURI, localName);
    }

    //
    // Object methods
    //

    /** Clone. */
    public Object clone() throws CloneNotSupportedException {
        CoreDocumentImpl newdoc = (CoreDocumentImpl) super.clone();
        newdoc.docType = null;
        newdoc.docElement = null;
        return newdoc;
    }

    //
    // Public static methods
    //

    /**
     * Check the string against XML's definition of acceptable names for
     * elements and attributes and so on using the XMLCharacterProperties
     * utility class
     * <p>
     *  使用XMLCharacterProperties实用程序类,根据XML的元素和属性的可接受名称的定义来检查字符串
     * 
     */

    public static final boolean isXMLName(String s, boolean xml11Version) {

        if (s == null) {
            return false;
        }
        if(!xml11Version)
            return XMLChar.isValidName(s);
        else
            return XML11Char.isXML11ValidName(s);

    } // isXMLName(String):boolean

    /**
     * Checks if the given qualified name is legal with respect
     * to the version of XML to which this document must conform.
     *
     * <p>
     *  检查给定的限定名称是否合法,相对于本文档必须符合的XML版本
     * 
     * 
     * @param prefix prefix of qualified name
     * @param local local part of qualified name
     */
    public static final boolean isValidQName(String prefix, String local, boolean xml11Version) {

        // check that both prefix and local part match NCName
        if (local == null) return false;
        boolean validNCName = false;

        if (!xml11Version) {
            validNCName = (prefix == null || XMLChar.isValidNCName(prefix))
                && XMLChar.isValidNCName(local);
        }
        else {
            validNCName = (prefix == null || XML11Char.isXML11ValidNCName(prefix))
                && XML11Char.isXML11ValidNCName(local);
        }

        return validNCName;
    }
    //
    // Protected methods
    //

    /**
     * Uses the kidOK lookup table to check whether the proposed
     * tree structure is legal.
     * <p>
     *  使用kidOK查询表检查提出的树结构是否合法
     * 
     */
    protected boolean isKidOK(Node parent, Node child) {
        if (allowGrammarAccess &&
        parent.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
            return child.getNodeType() == Node.ELEMENT_NODE;
        }
        return 0 != (kidOK[parent.getNodeType()] & 1 << child.getNodeType());
    }

    /**
     * Denotes that this node has changed.
     * <p>
     *  表示此节点已更改
     * 
     */
    protected void changed() {
        changes++;
    }

    /**
     * Returns the number of changes to this node.
     * <p>
     *  返回此节点的更改数
     * 
     */
    protected int changes() {
        return changes;
    }

    //  NodeListCache pool

    /**
     * Returns a NodeListCache for the given node.
     * <p>
     *  返回给定节点的NodeListCache
     * 
     */
    NodeListCache getNodeListCache(ParentNode owner) {
        if (fFreeNLCache == null) {
            return new NodeListCache(owner);
        }
        NodeListCache c = fFreeNLCache;
        fFreeNLCache = fFreeNLCache.next;
        c.fChild = null;
        c.fChildIndex = -1;
        c.fLength = -1;
        // revoke previous ownership
        if (c.fOwner != null) {
            c.fOwner.fNodeListCache = null;
        }
        c.fOwner = owner;
        // c.next = null; not necessary, except for confused people...
        return c;
    }

    /**
     * Puts the given NodeListCache in the free list.
     * Note: The owner node can keep using it until we reuse it
     * <p>
     *  将给定的NodeListCache置于空闲列表中注意：所有者节点可以继续使用它,直到我们重用它
     * 
     */
    void freeNodeListCache(NodeListCache c) {
        c.next = fFreeNLCache;
        fFreeNLCache = c;
    }



    /**
     * Associate an object to a key on this node. The object can later be
     * retrieved from this node by calling <code>getUserData</code> with the
     * same key.
     * <p>
     * 将对象与此节点上的键相关联稍后可以通过使用相同的键调用<code> getUserData </code>从此节点检索对象
     * 
     * 
     * @param n The node to associate the object to.
     * @param key The key to associate the object to.
     * @param data The object to associate to the given key, or
     *   <code>null</code> to remove any existing association to that key.
     * @param handler The handler to associate to that key, or
     *   <code>null</code>.
     * @return Returns the <code>DOMObject</code> previously associated to
     *   the given key on this node, or <code>null</code> if there was none.
     * @since DOM Level 3
     *
     * REVISIT: we could use a free list of UserDataRecord here
     */
    public Object setUserData(Node n, String key,
    Object data, UserDataHandler handler) {
        if (data == null) {
            if (userData != null) {
                Hashtable t = (Hashtable) userData.get(n);
                if (t != null) {
                    Object o = t.remove(key);
                    if (o != null) {
                        UserDataRecord r = (UserDataRecord) o;
                        return r.fData;
                    }
                }
            }
            return null;
        }
        else {
            Hashtable t;
            if (userData == null) {
                userData = new Hashtable();
                t = new Hashtable();
                userData.put(n, t);
            }
            else {
                t = (Hashtable) userData.get(n);
                if (t == null) {
                    t = new Hashtable();
                    userData.put(n, t);
                }
            }
            Object o = t.put(key, new UserDataRecord(data, handler));
            if (o != null) {
                UserDataRecord r = (UserDataRecord) o;
                return r.fData;
            }
            return null;
        }
    }


    /**
     * Retrieves the object associated to a key on a this node. The object
     * must first have been set to this node by calling
     * <code>setUserData</code> with the same key.
     * <p>
     *  检索与此节点上的键相关联的对象必须首先通过使用相同的键调用<code> setUserData </code>将该对象设置为此节点
     * 
     * 
     * @param n The node the object is associated to.
     * @param key The key the object is associated to.
     * @return Returns the <code>DOMObject</code> associated to the given key
     *   on this node, or <code>null</code> if there was none.
     * @since DOM Level 3
     */
    public Object getUserData(Node n, String key) {
        if (userData == null) {
            return null;
        }
        Hashtable t = (Hashtable) userData.get(n);
        if (t == null) {
            return null;
        }
        Object o = t.get(key);
        if (o != null) {
            UserDataRecord r = (UserDataRecord) o;
            return r.fData;
        }
        return null;
    }

        protected Hashtable getUserDataRecord(Node n){
        if (userData == null) {
            return null;
        }
        Hashtable t = (Hashtable) userData.get(n);
        if (t == null) {
            return null;
        }
                return t;
        }

        /**
     * Remove user data table for the given node.
     * <p>
     *  删除给定节点的用户数据表
     * 
     * 
     * @param n The node this operation applies to.
     * @return The removed table.
     */
    Hashtable removeUserDataTable(Node n) {
        if (userData == null) {
            return null;
        }
        return (Hashtable) userData.get(n);
    }

    /**
     * Set user data table for the given node.
     * <p>
     *  为给定节点设置用户数据表
     * 
     * 
     * @param n The node this operation applies to.
     * @param data The user data table.
     */
    void setUserDataTable(Node n, Hashtable data) {
                if (userData == null)
                        userData = new Hashtable();
        if (data != null) {
            userData.put(n, data);
        }
    }

    /**
     * Call user data handlers when a node is deleted (finalized)
     * <p>
     *  删除节点时删除(完成)时调用用户数据处理程序
     * 
     * 
     * @param n The node this operation applies to.
     * @param c The copy node or null.
     * @param operation The operation - import, clone, or delete.
     */
    void callUserDataHandlers(Node n, Node c, short operation) {
        if (userData == null) {
            return;
        }
        //Hashtable t = (Hashtable) userData.get(n);
                if(n instanceof NodeImpl){
                        Hashtable t = ((NodeImpl)n).getUserDataRecord();
                        if (t == null || t.isEmpty()) {
                                return;
                        }
                        callUserDataHandlers(n, c, operation,t);
                }
    }

        /**
     * Call user data handlers when a node is deleted (finalized)
     * <p>
     *  删除节点时删除(完成)时调用用户数据处理程序
     * 
     * 
     * @param n The node this operation applies to.
     * @param c The copy node or null.
     * @param operation The operation - import, clone, or delete.
         * @param handlers Data associated with n.
        */
        void callUserDataHandlers(Node n, Node c, short operation,Hashtable userData) {
        if (userData == null || userData.isEmpty()) {
            return;
        }
        Enumeration keys = userData.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            UserDataRecord r = (UserDataRecord) userData.get(key);
            if (r.fHandler != null) {
                r.fHandler.handle(operation, key, r.fData, n, c);
            }
        }
    }

        /**
     * Call user data handlers to let them know the nodes they are related to
     * are being deleted. The alternative would be to do that on Node but
     * because the nodes are used as the keys we have a reference to them that
     * prevents them from being gc'ed until the document is. At the same time,
     * doing it here has the advantage of avoiding a finalize() method on Node,
     * which would affect all nodes and not just the ones that have a user
     * data.
     * <p>
     * 调用用户数据处理程序,让他们知道它们相关的节点正在被删除另一种方法是在Node上执行,但是因为节点被用作键,我们有一个引用,阻止它们被gc'文档是在同一时间,在这里做它的优点是避免在Node上的fina
     * lize()方法,这将影响所有节点,而不仅仅是那些有用户数据。
     * 
     */
    // Temporarily comment out this method, because
    // 1. It seems that finalizers are not guaranteed to be called, so the
    //    functionality is not implemented.
    // 2. It affects the performance greatly in multi-thread environment.
    // -SG
    /*public void finalize() {
        if (userData == null) {
            return;
        }
        Enumeration nodes = userData.keys();
        while (nodes.hasMoreElements()) {
            Object node = nodes.nextElement();
            Hashtable t = (Hashtable) userData.get(node);
            if (t != null && !t.isEmpty()) {
                Enumeration keys = t.keys();
                while (keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    UserDataRecord r = (UserDataRecord) t.get(key);
                    if (r.fHandler != null) {
                        r.fHandler.handle(UserDataHandler.NODE_DELETED,
                                          key, r.fData, null, null);
                    }
                }
            }
        }
    /* <p>
    /* if(userData == null){return; }枚举nodes = userDatakeys(); while(nodeshasMoreElements()){Object node = nodesnextElement(); Hashtable t =(Hashtable)userDataget(node); if(t！= null &&！tisEmpty()){Enumeration keys = tkeys(); while(keyshasMoreElements()){String key =(String)keysnextElement(); UserDataRecord r =(UserDataRecord)tget(key); if(rfHandler！= null){rfHandlerhandle(UserDataHandlerNODE_DELETED,key,rfData,null,null); }
    /* }}}。
    /* 
    /* 
    }*/

    protected final void checkNamespaceWF( String qname, int colon1,
    int colon2) {

        if (!errorChecking) {
            return;
        }
        // it is an error for NCName to have more than one ':'
        // check if it is valid QName [Namespace in XML production 6]
        // :camera , nikon:camera:minolta, camera:
        if (colon1 == 0 || colon1 == qname.length() - 1 || colon2 != colon1) {
            String msg =
            DOMMessageFormatter.formatMessage(
            DOMMessageFormatter.DOM_DOMAIN,
            "NAMESPACE_ERR",
            null);
            throw new DOMException(DOMException.NAMESPACE_ERR, msg);
        }
    }
    protected final void checkDOMNSErr(String prefix,
    String namespace) {
        if (errorChecking) {
            if (namespace == null) {
                String msg =
                DOMMessageFormatter.formatMessage(
                DOMMessageFormatter.DOM_DOMAIN,
                "NAMESPACE_ERR",
                null);
                throw new DOMException(DOMException.NAMESPACE_ERR, msg);
            }
            else if (prefix.equals("xml")
            && !namespace.equals(NamespaceContext.XML_URI)) {
                String msg =
                DOMMessageFormatter.formatMessage(
                DOMMessageFormatter.DOM_DOMAIN,
                "NAMESPACE_ERR",
                null);
                throw new DOMException(DOMException.NAMESPACE_ERR, msg);
            }
            else if (
            prefix.equals("xmlns")
            && !namespace.equals(NamespaceContext.XMLNS_URI)
            || (!prefix.equals("xmlns")
            && namespace.equals(NamespaceContext.XMLNS_URI))) {
                String msg =
                DOMMessageFormatter.formatMessage(
                DOMMessageFormatter.DOM_DOMAIN,
                "NAMESPACE_ERR",
                null);
                throw new DOMException(DOMException.NAMESPACE_ERR, msg);
            }
        }
    }

    /**
     * Checks if the given qualified name is legal with respect
     * to the version of XML to which this document must conform.
     *
     * <p>
     *  检查给定的限定名称是否合法,相对于本文档必须符合的XML版本
     * 
     * 
     * @param prefix prefix of qualified name
     * @param local local part of qualified name
     */
    protected final void checkQName(String prefix, String local) {
        if (!errorChecking) {
            return;
        }

                // check that both prefix and local part match NCName
        boolean validNCName = false;
        if (!xml11Version) {
            validNCName = (prefix == null || XMLChar.isValidNCName(prefix))
                && XMLChar.isValidNCName(local);
        }
        else {
            validNCName = (prefix == null || XML11Char.isXML11ValidNCName(prefix))
                && XML11Char.isXML11ValidNCName(local);
        }

        if (!validNCName) {
            // REVISIT: add qname parameter to the message
            String msg =
            DOMMessageFormatter.formatMessage(
            DOMMessageFormatter.DOM_DOMAIN,
            "INVALID_CHARACTER_ERR",
            null);
            throw new DOMException(DOMException.INVALID_CHARACTER_ERR, msg);
        }
    }

    /**
     * We could have more xml versions in future , but for now we could
     * do with this to handle XML 1.0 and 1.1
     * <p>
     *  我们可以在将来有更多的xml版本,但现在我们可以做到这一点来处理XML 10和11
     * 
     */
    boolean isXML11Version(){
        return xml11Version;
    }

    boolean isNormalizeDocRequired(){
        // REVISIT: Implement to optimize when normalization
        // is required
        return true;
    }

    //we should be checking the (elements, attribute, entity etc.) names only when
    //version of the document is changed.
    boolean isXMLVersionChanged(){
        return xmlVersionChanged ;
    }
    /**
     * NON-DOM: kept for backward compatibility
     * Store user data related to a given node
     * This is a place where we could use weak references! Indeed, the node
     * here won't be GC'ed as long as some user data is attached to it, since
     * the userData table will have a reference to the node.
     * <p>
     * NON-DOM：保持向后兼容性存储与给定节点相关的用户数据这是一个我们可以使用弱引用的地方！实际上,这里的节点将不会被GC化,只要一些用户数据附加到它,因为userData表将具有对节点的引用
     * 
     */
    protected void setUserData(NodeImpl n, Object data) {
        setUserData(n, "XERCES1DOMUSERDATA", data, null);
    }

    /**
     * NON-DOM: kept for backward compatibility
     * Retreive user data related to a given node
     * <p>
     *  NON-DOM：保持向后兼容性检索与给定节点相关的用户数据
     * 
     */
    protected Object getUserData(NodeImpl n) {
        return getUserData(n, "XERCES1DOMUSERDATA");
    }


    // Event related methods overidden in subclass

    protected void addEventListener(NodeImpl node, String type,
    EventListener listener,
    boolean useCapture) {
        // does nothing by default - overidden in subclass
    }

    protected void removeEventListener(NodeImpl node, String type,
    EventListener listener,
    boolean useCapture) {
        // does nothing by default - overidden in subclass
    }

    protected void copyEventListeners(NodeImpl src, NodeImpl tgt) {
        // does nothing by default - overidden in subclass
    }

    protected boolean dispatchEvent(NodeImpl node, Event event) {
        // does nothing by default - overidden in subclass
        return false;
    }

    // Notification methods overidden in subclasses

    /**
     * A method to be called when some text was changed in a text node,
     * so that live objects can be notified.
     * <p>
     *  在文本节点中更改某些文本时调用的方法,以便可以通知活动对象
     * 
     */
    void replacedText(NodeImpl node) {
    }

    /**
     * A method to be called when some text was deleted from a text node,
     * so that live objects can be notified.
     * <p>
     *  当从文本节点删除某些文本时调用的方法,以便可以通知活动对象
     * 
     */
    void deletedText(NodeImpl node, int offset, int count) {
    }

    /**
     * A method to be called when some text was inserted into a text node,
     * so that live objects can be notified.
     * <p>
     *  当将某些文本插入到文本节点时调用的方法,以便可以通知活动对象
     * 
     */
    void insertedText(NodeImpl node, int offset, int count) {
    }

    /**
     * A method to be called when a character data node is about to be modified
     * <p>
     *  当字符数据节点即将被修改时要调用的方法
     * 
     */
    void modifyingCharacterData(NodeImpl node, boolean replace) {
    }

    /**
     * A method to be called when a character data node has been modified
     * <p>
     * 在字符数据节点已修改时调用的方法
     * 
     */
    void modifiedCharacterData(NodeImpl node, String oldvalue, String value, boolean replace) {
    }

    /**
     * A method to be called when a node is about to be inserted in the tree.
     * <p>
     *  当节点即将插入树中时要调用的方法
     * 
     */
    void insertingNode(NodeImpl node, boolean replace) {
    }

    /**
     * A method to be called when a node has been inserted in the tree.
     * <p>
     *  当节点已插入树中时调用的方法
     * 
     */
    void insertedNode(NodeImpl node, NodeImpl newInternal, boolean replace) {
    }

    /**
     * A method to be called when a node is about to be removed from the tree.
     * <p>
     *  当节点即将从树中删除时要调用的方法
     * 
     */
    void removingNode(NodeImpl node, NodeImpl oldChild, boolean replace) {
    }

    /**
     * A method to be called when a node has been removed from the tree.
     * <p>
     *  当节点已从树中删除时要调用的方法
     * 
     */
    void removedNode(NodeImpl node, boolean replace) {
    }

    /**
     * A method to be called when a node is about to be replaced in the tree.
     * <p>
     *  当树中要更换节点时要调用的方法
     * 
     */
    void replacingNode(NodeImpl node) {
    }

    /**
     * A method to be called when a node has been replaced in the tree.
     * <p>
     *  在树中已替换节点时调用的方法
     * 
     */
    void replacedNode(NodeImpl node) {
    }

    /**
     * A method to be called when a character data node is about to be replaced
     * <p>
     *  当字符数据节点即将被替换时被调用的方法
     * 
     */
    void replacingData(NodeImpl node) {
    }

    /**
     *  method to be called when a character data node has been replaced.
     * <p>
     *  方法在字符数据节点被替换时被调用
     * 
     */
    void replacedCharacterData(NodeImpl node, String oldvalue, String value) {
    }


    /**
     * A method to be called when an attribute value has been modified
     * <p>
     *  修改属性值时调用的方法
     * 
     */
    void modifiedAttrValue(AttrImpl attr, String oldvalue) {
    }

    /**
     * A method to be called when an attribute node has been set
     * <p>
     *  设置属性节点时调用的方法
     * 
     */
    void setAttrNode(AttrImpl attr, AttrImpl previous) {
    }

    /**
     * A method to be called when an attribute node has been removed
     * <p>
     * 删除属性节点时调用的方法
     * 
     */
    void removedAttrNode(AttrImpl attr, NodeImpl oldOwner, String name) {
    }

    /**
     * A method to be called when an attribute node has been renamed
     * <p>
     *  当属性节点已重命名时调用的方法
     * 
     */
    void renamedAttrNode(Attr oldAt, Attr newAt) {
    }

    /**
     * A method to be called when an element has been renamed
     * <p>
     *  当元素已重命名时调用的方法
     */
    void renamedElement(Element oldEl, Element newEl) {
    }

} // class CoreDocumentImpl
