/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: SAXImpl.java,v 1.5 2005/09/28 13:48:37 pvedula Exp $
 * <p>
 *  $ Id：SAXImpl.java,v 1.5 2005/09/28 13:48:37 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.Enumeration;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.DOMEnhancedForDTM;
import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.Axis;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIterNodeList;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBase;
import com.sun.org.apache.xml.internal.dtm.ref.EmptyIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;
import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import com.sun.org.apache.xml.internal.serializer.ToXMLSAXHandler;
import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
import com.sun.org.apache.xml.internal.utils.SystemIDResolver;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Entity;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * SAXImpl is the core model for SAX input source. SAXImpl objects are
 * usually created from an XSLTCDTMManager.
 *
 * <p>DOMSource inputs are handled using DOM2SAX + SAXImpl. SAXImpl has a
 * few specific fields (e.g. _node2Ids, _document) to keep DOM-related
 * information. They are used when the processing behavior between DOM and
 * SAX has to be different. Examples of these include id function and
 * unparsed entity.
 *
 * <p>SAXImpl extends SAX2DTM2 instead of SAX2DTM for better performance.
 * <p>
 *  SAXImpl是SAX输入源的核心模型。 SAXImpl对象通常是从XSLTCDTMManager创建的。
 * 
 *  <p> DOMSource输入使用DOM2SAX + SAXImpl处理。 SAXImpl有几个特定的​​字段(例如_node2Ids,_document)来保存DOM相关的信息。
 * 当DOM和SAX之间的处理行为必须不同时,使用它们。这些例子包括id函数和未解析的实体。
 * 
 *  <p> SAXImpl扩展了SAX2DTM2,而不是SAX2DTM,以获得更好的性能。
 * 
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 * @author Douglas Sellers <douglasjsellers@hotmail.com>
 */
public final class SAXImpl extends SAX2DTM2
                           implements DOMEnhancedForDTM, DOMBuilder
{

    /* ------------------------------------------------------------------- */
    /* DOMBuilder fields BEGIN                                             */
    /* ------------------------------------------------------------------- */

    // Namespace prefix-to-uri mapping stuff
    private int       _uriCount     = 0;
    private int       _prefixCount  = 0;

    // Stack used to keep track of what whitespace text nodes are protected
    // by xml:space="preserve" attributes and which nodes that are not.
    private int[]   _xmlSpaceStack;
    private int     _idx = 1;
    private boolean _preserve = false;

    private static final String XML_STRING = "xml:";
    private static final String XML_PREFIX   = "xml";
    private static final String XMLSPACE_STRING = "xml:space";
    private static final String PRESERVE_STRING = "preserve";
    private static final String XMLNS_PREFIX = "xmlns";
    private static final String XML_URI = "http://www.w3.org/XML/1998/namespace";

    private boolean _escaping = true;
    private boolean _disableEscaping = false;
    private int _textNodeToProcess = DTM.NULL;

    /* ------------------------------------------------------------------- */
    /* DOMBuilder fields END                                               */
    /* ------------------------------------------------------------------- */

    // empty String for null attribute values
    private final static String EMPTYSTRING = "";

    // empty iterator to be returned when there are no children
    private final static DTMAxisIterator EMPTYITERATOR = EmptyIterator.getInstance();
    // The number of expanded names
    private int _namesSize = -1;

    // Namespace related stuff
    private Hashtable _nsIndex = new Hashtable();

    // The initial size of the text buffer
    private int _size = 0;

    // Tracks which textnodes are not escaped
    private BitArray  _dontEscape = null;

    // The URI to this document
    private String    _documentURI = null;
    static private int _documentURIIndex = 0;

    // The owner Document when the input source is DOMSource.
    private Document _document;

    // The hashtable for org.w3c.dom.Node to node id mapping.
    // This is only used when the input is a DOMSource and the
    // buildIdIndex flag is true.
    private Hashtable _node2Ids = null;

    // True if the input source is a DOMSource.
    private boolean _hasDOMSource = false;

    // The DTMManager
    private XSLTCDTMManager _dtmManager;

    // Support for access/navigation through org.w3c.dom API
    private Node[] _nodes;
    private NodeList[] _nodeLists;
    private final static String XML_LANG_ATTRIBUTE =
        "http://www.w3.org/XML/1998/namespace:@lang";

    /**
     * Define the origin of the document from which the tree was built
     * <p>
     *  定义构建树的文档的来源
     * 
     */
    public void setDocumentURI(String uri) {
        if (uri != null) {
            setDocumentBaseURI(SystemIDResolver.getAbsoluteURI(uri));
        }
    }

    /**
     * Returns the origin of the document from which the tree was built
     * <p>
     *  返回构建树的文档的原点
     * 
     */
    public String getDocumentURI() {
        String baseURI = getDocumentBaseURI();
        return (baseURI != null) ? baseURI : "rtf" + _documentURIIndex++;
    }

    public String getDocumentURI(int node) {
        return getDocumentURI();
    }

    public void setupMapping(String[] names, String[] urisArray,
                             int[] typesArray, String[] namespaces) {
        // This method only has a function in DOM adapters
    }

    /**
     * Lookup a namespace URI from a prefix starting at node. This method
     * is used in the execution of xsl:element when the prefix is not known
     * at compile time.
     * <p>
     * 从节点开始的前缀中查找命名空间URI。当前缀在编译时未知时,此方法用于执行xsl：element。
     * 
     */
    public String lookupNamespace(int node, String prefix)
        throws TransletException
    {
        int anode, nsnode;
        final AncestorIterator ancestors = new AncestorIterator();

        if (isElement(node)) {
            ancestors.includeSelf();
        }

        ancestors.setStartNode(node);
        while ((anode = ancestors.next()) != DTM.NULL) {
            final NamespaceIterator namespaces = new NamespaceIterator();

            namespaces.setStartNode(anode);
            while ((nsnode = namespaces.next()) != DTM.NULL) {
                if (getLocalName(nsnode).equals(prefix)) {
                    return getNodeValue(nsnode);
                }
            }
        }

        BasisLibrary.runTimeError(BasisLibrary.NAMESPACE_PREFIX_ERR, prefix);
        return null;
    }

    /**
     * Returns 'true' if a specific node is an element (of any type)
     * <p>
     *  如果特定节点是元素(任何类型),则返回'true'
     * 
     */
    public boolean isElement(final int node) {
        return getNodeType(node) == DTM.ELEMENT_NODE;
    }

    /**
     * Returns 'true' if a specific node is an attribute (of any type)
     * <p>
     *  如果特定节点是属性(任何类型),则返回'true'
     * 
     */
    public boolean isAttribute(final int node) {
        return getNodeType(node) == DTM.ATTRIBUTE_NODE;
    }

    /**
     * Returns the number of nodes in the tree (used for indexing)
     * <p>
     *  返回树中的节点数(用于建立索引)
     * 
     */
    public int getSize() {
        return getNumberOfNodes();
    }

    /**
     * Part of the DOM interface - no function here.
     * <p>
     *  部分DOM接口 - 这里没有功能。
     * 
     */
    public void setFilter(StripFilter filter) {
    }


    /**
     * Returns true if node1 comes before node2 in document order
     * <p>
     *  如果node1在文档顺序中位于node2之前,则返回true
     * 
     */
    public boolean lessThan(int node1, int node2) {
        if (node1 == DTM.NULL) {
            return false;
        }

        if (node2 == DTM.NULL) {
            return true;
        }

        return (node1 < node2);
    }

    /**
     * Create an org.w3c.dom.Node from a node in the tree
     * <p>
     *  从树中的节点创建org.w3c.dom.Node
     * 
     */
    public Node makeNode(int index) {
        if (_nodes == null) {
            _nodes = new Node[_namesSize];
        }

        int nodeID = makeNodeIdentity(index);
        if (nodeID < 0) {
            return null;
        }
        else if (nodeID < _nodes.length) {
            return (_nodes[nodeID] != null) ? _nodes[nodeID]
                : (_nodes[nodeID] = new DTMNodeProxy((DTM)this, index));
        }
        else {
            return new DTMNodeProxy((DTM)this, index);
        }
    }

    /**
     * Create an org.w3c.dom.Node from a node in an iterator
     * The iterator most be started before this method is called
     * <p>
     *  从迭代器中的节点创建org.w3c.dom.Node迭代器最多在调用此方法之前启动
     * 
     */
    public Node makeNode(DTMAxisIterator iter) {
        return makeNode(iter.next());
    }

    /**
     * Create an org.w3c.dom.NodeList from a node in the tree
     * <p>
     *  从树中的节点创建org.w3c.dom.NodeList
     * 
     */
    public NodeList makeNodeList(int index) {
        if (_nodeLists == null) {
            _nodeLists = new NodeList[_namesSize];
        }

        int nodeID = makeNodeIdentity(index);
        if (nodeID < 0) {
            return null;
        }
        else if (nodeID < _nodeLists.length) {
            return (_nodeLists[nodeID] != null) ? _nodeLists[nodeID]
                   : (_nodeLists[nodeID] = new DTMAxisIterNodeList(this,
                                                 new SingletonIterator(index)));
    }
        else {
            return new DTMAxisIterNodeList(this, new SingletonIterator(index));
        }
    }

    /**
     * Create an org.w3c.dom.NodeList from a node iterator
     * The iterator most be started before this method is called
     * <p>
     *  从节点iterator创建org.w3c.dom.NodeList迭代器最多在调用此方法之前启动
     * 
     */
    public NodeList makeNodeList(DTMAxisIterator iter) {
        return new DTMAxisIterNodeList(this, iter);
    }

    /**
     * Iterator that returns the namespace nodes as defined by the XPath data
     * model for a given node, filtered by extended type ID.
     * <p>
     *  Iterator返回由扩展类型ID过滤的给定节点的XPath数据模型定义的命名空间节点。
     * 
     */
    public class TypedNamespaceIterator extends NamespaceIterator {

        private  String _nsPrefix;

        /**
         * Constructor TypedChildrenIterator
         *
         *
         * <p>
         *  构造函数TypedChildrenIterator
         * 
         * 
         * @param nodeType The extended type ID being requested.
         */
        public TypedNamespaceIterator(int nodeType) {
            super();
            if(m_expandedNameTable != null){
                _nsPrefix = m_expandedNameTable.getLocalName(nodeType);
            }
        }

       /**
        * Get the next node in the iteration.
        *
        * <p>
        *  获取迭代中的下一个节点。
        * 
        * 
        * @return The next node handle in the iteration, or END.
        */
        public int next() {
            if ((_nsPrefix == null) ||(_nsPrefix.length() == 0) ){
                return (END);
            }
            int node = END;
            for (node = super.next(); node != END; node = super.next()) {
                if (_nsPrefix.compareTo(getLocalName(node))== 0) {
                    return returnNode(node);
                }
            }
            return (END);
        }
    }  // end of TypedNamespaceIterator



    /**************************************************************
     * This is a specialised iterator for predicates comparing node or
     * attribute values to variable or parameter values.
     * <p>
     *  这是用于谓词的专用迭代器,用于将节点或属性值与变量或参数值进行比较。
     * 
     */
    private final class NodeValueIterator extends InternalAxisIteratorBase
    {

        private DTMAxisIterator _source;
        private String _value;
        private boolean _op;
        private final boolean _isReverse;
        private int _returnType = RETURN_PARENT;

        public NodeValueIterator(DTMAxisIterator source, int returnType,
                                 String value, boolean op)
        {
            _source = source;
            _returnType = returnType;
            _value = value;
            _op = op;
            _isReverse = source.isReverse();
        }

        public boolean isReverse()
        {
            return _isReverse;
        }

        public DTMAxisIterator cloneIterator()
        {
            try {
                NodeValueIterator clone = (NodeValueIterator)super.clone();
                clone._isRestartable = false;
                clone._source = _source.cloneIterator();
                clone._value = _value;
                clone._op = _op;
                return clone.reset();
            }
            catch (CloneNotSupportedException e) {
                BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                          e.toString());
                return null;
            }
        }

        public void setRestartable(boolean isRestartable)
        {
            _isRestartable = isRestartable;
            _source.setRestartable(isRestartable);
        }

        public DTMAxisIterator reset()
        {
            _source.reset();
            return resetPosition();
        }

        public int next()
        {
            int node;
            while ((node = _source.next()) != END) {
                String val = getStringValueX(node);
                if (_value.equals(val) == _op) {
                    if (_returnType == RETURN_CURRENT) {
                        return returnNode(node);
                    }
                    else {
                        return returnNode(getParent(node));
                    }
                }
            }
            return END;
        }

        public DTMAxisIterator setStartNode(int node)
        {
            if (_isRestartable) {
                _source.setStartNode(_startNode = node);
                return resetPosition();
            }
            return this;
        }

        public void setMark()
        {
            _source.setMark();
        }

        public void gotoMark()
        {
            _source.gotoMark();
        }
    } // end NodeValueIterator

    public DTMAxisIterator getNodeValueIterator(DTMAxisIterator iterator, int type,
                                             String value, boolean op)
    {
        return(DTMAxisIterator)(new NodeValueIterator(iterator, type, value, op));
    }

    /**
     * Encapsulates an iterator in an OrderedIterator to ensure node order
     * <p>
     *  在OrderedIterator中封装迭代器以确保节点顺序
     * 
     */
    public DTMAxisIterator orderNodes(DTMAxisIterator source, int node)
    {
        return new DupFilterIterator(source);
    }

    /**
     * Returns singleton iterator containg the document root
     * Works for them main document (mark == 0).  It cannot be made
     * to point to any other node through setStartNode().
     * <p>
     *  返回包含文档根的单例迭代器为它们工作主文档(mark == 0)。它不能通过setStartNode()指向任何其他节点。
     * 
     */
    public DTMAxisIterator getIterator()
    {
        return new SingletonIterator(getDocument(), true);
    }

     /**
     * Get mapping from DOM namespace types to external namespace types
     * <p>
     *  获取从DOM命名空间类型到外部命名空间类型的映射
     * 
     */
    public int getNSType(int node)
    {
        String s = getNamespaceURI(node);
        if (s == null) {
            return 0;
        }
        int eType = getIdForNamespace(s);
        return ((Integer)_nsIndex.get(new Integer(eType))).intValue();
    }



    /**
     * Returns the namespace type of a specific node
     * <p>
     *  返回特定节点的命名空间类型
     * 
     */
    public int getNamespaceType(final int node)
    {
        return super.getNamespaceType(node);
    }

    /**
     * Sets up a translet-to-dom type mapping table
     * <p>
     *  设置translet-to-dom类型映射表
     * 
     */
    private int[] setupMapping(String[] names, String[] uris, int[] types, int nNames) {
        // Padding with number of names, because they
        // may need to be added, i.e for RTFs. See copy03
        final int[] result = new int[m_expandedNameTable.getSize()];
        for (int i = 0; i < nNames; i++)      {
            //int type = getGeneralizedType(namesArray[i]);
            int type = m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], false);
            result[type] = type;
        }
        return result;
    }

    /**
     * Returns the internal type associated with an expanded QName
     * <p>
     * 返回与扩展的QName关联的内部类型
     * 
     */
    public int getGeneralizedType(final String name) {
        return getGeneralizedType(name, true);
    }

    /**
     * Returns the internal type associated with an expanded QName
     * <p>
     *  返回与扩展的QName关联的内部类型
     * 
     */
    public int getGeneralizedType(final String name, boolean searchOnly) {
        String lName, ns = null;
        int index = -1;
        int code;

        // Is there a prefix?
        if ((index = name.lastIndexOf(":"))> -1) {
            ns = name.substring(0, index);
        }

        // Local part of name is after colon.  lastIndexOf returns -1 if
        // there is no colon, so lNameStartIdx will be zero in that case.
        int lNameStartIdx = index+1;

        // Distinguish attribute and element names.  Attribute has @ before
        // local part of name.
        if (name.charAt(lNameStartIdx) == '@') {
            code = DTM.ATTRIBUTE_NODE;
            lNameStartIdx++;
        }
        else {
            code = DTM.ELEMENT_NODE;
        }

        // Extract local name
        lName = (lNameStartIdx == 0) ? name : name.substring(lNameStartIdx);

        return m_expandedNameTable.getExpandedTypeID(ns, lName, code, searchOnly);
    }

    /**
     * Get mapping from DOM element/attribute types to external types
     * <p>
     *  从DOM元素/属性类型到外部类型的映射
     * 
     */
    public short[] getMapping(String[] names, String[] uris, int[] types)
    {
        // Delegate the work to getMapping2 if the document is not fully built.
        // Some of the processing has to be different in this case.
        if (_namesSize < 0) {
            return getMapping2(names, uris, types);
        }

        int i;
        final int namesLength = names.length;
        final int exLength = m_expandedNameTable.getSize();

        final short[] result = new short[exLength];

        // primitive types map to themselves
        for (i = 0; i < DTM.NTYPES; i++) {
            result[i] = (short)i;
        }

        for (i = NTYPES; i < exLength; i++) {
            result[i] = m_expandedNameTable.getType(i);
        }

        // actual mapping of caller requested names
        for (i = 0; i < namesLength; i++) {
            int genType = m_expandedNameTable.getExpandedTypeID(uris[i],
                                                                names[i],
                                                                types[i],
                                                                true);
            if (genType >= 0 && genType < exLength) {
                result[genType] = (short)(i + DTM.NTYPES);
            }
        }

        return result;
    }

    /**
     * Get mapping from external element/attribute types to DOM types
     * <p>
     *  获取从外部元素/属性类型到DOM类型的映射
     * 
     */
    public int[] getReverseMapping(String[] names, String[] uris, int[] types)
    {
        int i;
        final int[] result = new int[names.length + DTM.NTYPES];

        // primitive types map to themselves
        for (i = 0; i < DTM.NTYPES; i++) {
            result[i] = i;
        }

        // caller's types map into appropriate dom types
        for (i = 0; i < names.length; i++) {
            int type = m_expandedNameTable.getExpandedTypeID(uris[i], names[i], types[i], true);
            result[i+DTM.NTYPES] = type;
        }
        return(result);
    }

    /**
     * Get mapping from DOM element/attribute types to external types.
     * This method is used when the document is not fully built.
     * <p>
     *  从DOM元素/属性类型到外部类型的映射。当文档未完全构建时使用此方法。
     * 
     */
    private short[] getMapping2(String[] names, String[] uris, int[] types)
    {
        int i;
        final int namesLength = names.length;
        final int exLength = m_expandedNameTable.getSize();
        int[] generalizedTypes = null;
        if (namesLength > 0) {
            generalizedTypes = new int[namesLength];
        }

        int resultLength = exLength;

        for (i = 0; i < namesLength; i++) {
            // When the document is not fully built, the searchOnly
            // flag should be set to false. That means we should add
            // the type if it is not already in the expanded name table.
            //generalizedTypes[i] = getGeneralizedType(names[i], false);
            generalizedTypes[i] =
                m_expandedNameTable.getExpandedTypeID(uris[i],
                                                      names[i],
                                                      types[i],
                                                      false);
            if (_namesSize < 0 && generalizedTypes[i] >= resultLength) {
                resultLength = generalizedTypes[i] + 1;
            }
        }

        final short[] result = new short[resultLength];

        // primitive types map to themselves
        for (i = 0; i < DTM.NTYPES; i++) {
            result[i] = (short)i;
        }

        for (i = NTYPES; i < exLength; i++) {
            result[i] = m_expandedNameTable.getType(i);
        }

        // actual mapping of caller requested names
        for (i = 0; i < namesLength; i++) {
            int genType = generalizedTypes[i];
            if (genType >= 0 && genType < resultLength) {
                result[genType] = (short)(i + DTM.NTYPES);
            }
        }

        return(result);
    }
    /**
     * Get mapping from DOM namespace types to external namespace types
     * <p>
     *  获取从DOM命名空间类型到外部命名空间类型的映射
     * 
     */
    public short[] getNamespaceMapping(String[] namespaces)
    {
        int i;
        final int nsLength = namespaces.length;
        final int mappingLength = _uriCount;

        final short[] result = new short[mappingLength];

        // Initialize all entries to -1
        for (i=0; i<mappingLength; i++) {
            result[i] = (short)(-1);
        }

        for (i=0; i<nsLength; i++) {
            int eType = getIdForNamespace(namespaces[i]);
            Integer type = (Integer)_nsIndex.get(new Integer(eType));
            if (type != null) {
                result[type.intValue()] = (short)i;
            }
        }

        return(result);
    }

    /**
     * Get mapping from external namespace types to DOM namespace types
     * <p>
     *  获取从外部命名空间类型到DOM命名空间类型的映射
     * 
     */
    public short[] getReverseNamespaceMapping(String[] namespaces)
    {
        int i;
        final int length = namespaces.length;
        final short[] result = new short[length];

        for (i = 0; i < length; i++) {
            int eType = getIdForNamespace(namespaces[i]);
            Integer type = (Integer)_nsIndex.get(new Integer(eType));
            result[i] = (type == null) ? -1 : type.shortValue();
        }

        return result;
    }

    /**
     * Construct a SAXImpl object using the default block size.
     * <p>
     *  使用默认块大小构造SAXImpl对象。
     * 
     */
    public SAXImpl(XSLTCDTMManager mgr, Source source,
                   int dtmIdentity, DTMWSFilter whiteSpaceFilter,
                   XMLStringFactory xstringfactory,
                   boolean doIndexing, boolean buildIdIndex)
    {
        this(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory,
            doIndexing, DEFAULT_BLOCKSIZE, buildIdIndex, false);
    }

    /**
     * Construct a SAXImpl object using the given block size.
     * <p>
     *  使用给定的块大小构造一个SAXImpl对象。
     * 
     */
    public SAXImpl(XSLTCDTMManager mgr, Source source,
                   int dtmIdentity, DTMWSFilter whiteSpaceFilter,
                   XMLStringFactory xstringfactory,
                   boolean doIndexing, int blocksize,
                   boolean buildIdIndex,
                   boolean newNameTable)
    {
        super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory,
            doIndexing, blocksize, false, buildIdIndex, newNameTable);

        _dtmManager = mgr;
        _size = blocksize;

        // Use a smaller size for the space stack if the blocksize is small
        _xmlSpaceStack = new int[blocksize <= 64 ? 4 : 64];

        /* From DOMBuilder */
        _xmlSpaceStack[0] = DTMDefaultBase.ROOTNODE;

        // If the input source is DOMSource, set the _document field and
        // create the node2Ids table.
        if (source instanceof DOMSource) {
            _hasDOMSource = true;
            DOMSource domsrc = (DOMSource)source;
            Node node = domsrc.getNode();
            if (node instanceof Document) {
                _document = (Document)node;
            }
            else {
                _document = node.getOwnerDocument();
            }
            _node2Ids = new Hashtable();
        }
    }

    /**
    * Migrate a DTM built with an old DTMManager to a new DTMManager.
    * After the migration, the new DTMManager will treat the DTM as
    * one that is built by itself.
    * This is used to support DTM sharing between multiple transformations.
    * <p>
    *  将使用旧DTMManager构建的DTM迁移到新的DTMManager。迁移后,新的DTMManager会将DTM视为由其自身构建的DTM。这用于支持多个转换之间的DTM共享。
    * 
    * 
    * @param manager the DTMManager
    */
    public void migrateTo(DTMManager manager) {
        super.migrateTo(manager);
        if (manager instanceof XSLTCDTMManager) {
            _dtmManager = (XSLTCDTMManager)manager;
        }
    }

    /**
     * Return the node identity for a given id String
     *
     * <p>
     *  返回给定id字符串的节点标识
     * 
     * 
     * @param idString The id String
     * @return The identity of the node whose id is the given String.
     */
    public int getElementById(String idString)
    {
        Node node = _document.getElementById(idString);
        if (node != null) {
            Integer id = (Integer)_node2Ids.get(node);
            return (id != null) ? id.intValue() : DTM.NULL;
        }
        else {
            return DTM.NULL;
        }
    }

    /**
     * Return true if the input source is DOMSource.
     * <p>
     *  如果输入源是DOMSource,则返回true。
     * 
     */
    public boolean hasDOMSource()
    {
        return _hasDOMSource;
    }

    /*---------------------------------------------------------------------------*/
    /* DOMBuilder methods begin                                                  */
    /*---------------------------------------------------------------------------*/

    /**
     * Call this when an xml:space attribute is encountered to
     * define the whitespace strip/preserve settings.
     * <p>
     *  当遇到xml：space属性以定义空白条/保留设置时调用此方法。
     * 
     */
    private void xmlSpaceDefine(String val, final int node)
    {
        final boolean setting = val.equals(PRESERVE_STRING);
        if (setting != _preserve) {
            _xmlSpaceStack[_idx++] = node;
            _preserve = setting;
        }
    }

    /**
     * Call this from endElement() to revert strip/preserve setting
     * to whatever it was before the corresponding startElement().
     * <p>
     *  从endElement()调用此函数将条/保存设置恢复到相应的startElement()之前的任何值。
     * 
     */
    private void xmlSpaceRevert(final int node)
    {
        if (node == _xmlSpaceStack[_idx - 1]) {
            _idx--;
            _preserve = !_preserve;
        }
    }

    /**
     * Find out whether or not to strip whitespace nodes.
     *
     *
     * <p>
     *  找出是否去除空白节点。
     * 
     * 
     * @return whether or not to strip whitespace nodes.
     */
    protected boolean getShouldStripWhitespace()
    {
        return _preserve ? false : super.getShouldStripWhitespace();
    }

    /**
     * Creates a text-node and checks if it is a whitespace node.
     * <p>
     *  创建文本节点并检查它是否是空格节点。
     * 
     */
    private void handleTextEscaping() {
        if (_disableEscaping && _textNodeToProcess != DTM.NULL
            && _type(_textNodeToProcess) == DTM.TEXT_NODE) {
            if (_dontEscape == null) {
                _dontEscape = new BitArray(_size);
            }

            // Resize the _dontEscape BitArray if necessary.
            if (_textNodeToProcess >= _dontEscape.size()) {
                _dontEscape.resize(_dontEscape.size() * 2);
            }

            _dontEscape.setBit(_textNodeToProcess);
            _disableEscaping = false;
        }
        _textNodeToProcess = DTM.NULL;
    }


    /****************************************************************/
    /*               SAX Interface Starts Here                      */
    /****************************************************************/

    /**
     * SAX2: Receive notification of character data.
     * <p>
     *  SAX2：接收字符数据的通知。
     * 
     */
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);

        _disableEscaping = !_escaping;
        _textNodeToProcess = getNumberOfNodes();
    }

    /**
     * SAX2: Receive notification of the beginning of a document.
     * <p>
     *  SAX2：接收文档开头的通知。
     * 
     */
    public void startDocument() throws SAXException
    {
        super.startDocument();

        _nsIndex.put(new Integer(0), new Integer(_uriCount++));
        definePrefixAndUri(XML_PREFIX, XML_URI);
    }

    /**
     * SAX2: Receive notification of the end of a document.
     * <p>
     *  SAX2：接收文档结束的通知。
     * 
     */
    public void endDocument() throws SAXException
    {
        super.endDocument();

        handleTextEscaping();
        _namesSize = m_expandedNameTable.getSize();
    }

    /**
     * Specialized interface used by DOM2SAX. This one has an extra Node
     * parameter to build the Node -> id map.
     * <p>
     * DOM2SAX使用的专用接口。这个有一个额外的Node参数来构建Node  - > id map。
     * 
     */
    public void startElement(String uri, String localName,
                             String qname, Attributes attributes,
                             Node node)
        throws SAXException
    {
        this.startElement(uri, localName, qname, attributes);

        if (m_buildIdIndex) {
            _node2Ids.put(node, new Integer(m_parents.peek()));
        }
    }

    /**
     * SAX2: Receive notification of the beginning of an element.
     * <p>
     *  SAX2：接收元素开头的通知。
     * 
     */
    public void startElement(String uri, String localName,
                 String qname, Attributes attributes)
        throws SAXException
    {
        super.startElement(uri, localName, qname, attributes);

        handleTextEscaping();

        if (m_wsfilter != null) {
            // Look for any xml:space attributes
            // Depending on the implementation of attributes, this
            // might be faster than looping through all attributes. ILENE
            final int index = attributes.getIndex(XMLSPACE_STRING);
            if (index >= 0) {
                xmlSpaceDefine(attributes.getValue(index), m_parents.peek());
            }
        }
    }

    /**
     * SAX2: Receive notification of the end of an element.
     * <p>
     *  SAX2：接收元素结束的通知。
     * 
     */
    public void endElement(String namespaceURI, String localName, String qname)
        throws SAXException
    {
        super.endElement(namespaceURI, localName, qname);

        handleTextEscaping();

        // Revert to strip/preserve-space setting from before this element
        if (m_wsfilter != null) {
            xmlSpaceRevert(m_previous);
        }
    }

    /**
     * SAX2: Receive notification of a processing instruction.
     * <p>
     *  SAX2：接收处理指令的通知。
     * 
     */
    public void processingInstruction(String target, String data)
        throws SAXException
    {
        super.processingInstruction(target, data);
        handleTextEscaping();
    }

    /**
     * SAX2: Receive notification of ignorable whitespace in element
     * content. Similar to characters(char[], int, int).
     * <p>
     *  SAX2：在元素内容中接收可忽略的空格的通知。类似于字符(char [],int,int)。
     * 
     */
    public void ignorableWhitespace(char[] ch, int start, int length)
        throws SAXException
    {
        super.ignorableWhitespace(ch, start, length);
        _textNodeToProcess = getNumberOfNodes();
    }

    /**
     * SAX2: Begin the scope of a prefix-URI Namespace mapping.
     * <p>
     *  SAX2：开始前缀-URI命名空间映射的范围。
     * 
     */
    public void startPrefixMapping(String prefix, String uri)
        throws SAXException
    {
        super.startPrefixMapping(prefix, uri);
        handleTextEscaping();

        definePrefixAndUri(prefix, uri);
    }

    private void definePrefixAndUri(String prefix, String uri)
        throws SAXException
    {
        // Check if the URI already exists before pushing on stack
        Integer eType = new Integer(getIdForNamespace(uri));
        if ((Integer)_nsIndex.get(eType) == null) {
            _nsIndex.put(eType, new Integer(_uriCount++));
        }
    }

    /**
     * SAX2: Report an XML comment anywhere in the document.
     * <p>
     *  SAX2：在文档中的任何位置报告XML注释。
     * 
     */
    public void comment(char[] ch, int start, int length)
        throws SAXException
    {
        super.comment(ch, start, length);
        handleTextEscaping();
    }

    public boolean setEscaping(boolean value) {
        final boolean temp = _escaping;
        _escaping = value;
        return temp;
    }

   /*---------------------------------------------------------------------------*/
   /* DOMBuilder methods end                                                    */
   /*---------------------------------------------------------------------------*/

    /**
     * Prints the whole tree to standard output
     * <p>
     *  将整个树打印到标准输出
     * 
     */
    public void print(int node, int level)
    {
        switch(getNodeType(node))
        {
            case DTM.ROOT_NODE:
            case DTM.DOCUMENT_NODE:
                print(getFirstChild(node), level);
                break;
            case DTM.TEXT_NODE:
            case DTM.COMMENT_NODE:
            case DTM.PROCESSING_INSTRUCTION_NODE:
                System.out.print(getStringValueX(node));
                break;
            default:
                final String name = getNodeName(node);
                System.out.print("<" + name);
                for (int a = getFirstAttribute(node); a != DTM.NULL; a = getNextAttribute(a))
                {
                    System.out.print("\n" + getNodeName(a) + "=\"" + getStringValueX(a) + "\"");
                }
                System.out.print('>');
                for (int child = getFirstChild(node); child != DTM.NULL;
                    child = getNextSibling(child)) {
                    print(child, level + 1);
                }
                System.out.println("</" + name + '>');
                break;
        }
    }

    /**
     * Returns the name of a node (attribute or element).
     * <p>
     *  返回节点(属性或元素)的名称。
     * 
     */
    public String getNodeName(final int node)
    {
        // Get the node type and make sure that it is within limits
        int nodeh = node;
        final short type = getNodeType(nodeh);
        switch(type)
        {
            case DTM.ROOT_NODE:
            case DTM.DOCUMENT_NODE:
            case DTM.TEXT_NODE:
            case DTM.COMMENT_NODE:
                return EMPTYSTRING;
            case DTM.NAMESPACE_NODE:
                return this.getLocalName(nodeh);
            default:
                return super.getNodeName(nodeh);
        }
    }

    /**
     * Returns the namespace URI to which a node belongs
     * <p>
     *  返回节点所属的命名空间URI
     * 
     */
    public String getNamespaceName(final int node)
    {
        if (node == DTM.NULL) {
            return "";
        }

        String s;
        return (s = getNamespaceURI(node)) == null ? EMPTYSTRING : s;
    }


    /**
     * Returns the attribute node of a given type (if any) for an element
     * <p>
     *  返回元素的给定类型(如果有)的属性节点
     * 
     */
    public int getAttributeNode(final int type, final int element)
    {
        for (int attr = getFirstAttribute(element);
           attr != DTM.NULL;
           attr = getNextAttribute(attr))
        {
            if (getExpandedTypeID(attr) == type) return attr;
        }
        return DTM.NULL;
    }

    /**
     * Returns the value of a given attribute type of a given element
     * <p>
     *  返回给定元素的给定属性类型的值
     * 
     */
    public String getAttributeValue(final int type, final int element)
    {
        final int attr = getAttributeNode(type, element);
        return (attr != DTM.NULL) ? getStringValueX(attr) : EMPTYSTRING;
    }

    /**
     * This method is for testing/debugging only
     * <p>
     *  此方法仅用于测试/调试
     * 
     */
    public String getAttributeValue(final String name, final int element)
    {
        return getAttributeValue(getGeneralizedType(name), element);
    }

    /**
     * Returns an iterator with all the children of a given node
     * <p>
     *  返回具有给定节点的所有子项的迭代器
     * 
     */
    public DTMAxisIterator getChildren(final int node)
    {
        return (new ChildrenIterator()).setStartNode(node);
    }

    /**
     * Returns an iterator with all children of a specific type
     * for a given node (element)
     * <p>
     *  返回具有给定节点(元素)的特定类型的所有子元素的迭代器,
     * 
     */
    public DTMAxisIterator getTypedChildren(final int type)
    {
        return(new TypedChildrenIterator(type));
    }

    /**
     * This is a shortcut to the iterators that implement the
     * supported XPath axes (only namespace::) is not supported.
     * Returns a bare-bones iterator that must be initialized
     * with a start node (using iterator.setStartNode()).
     * <p>
     *  这是实现支持的XPath轴的迭代器的快捷方式(仅命名空间：:)不受支持。返回一个裸体迭代器,它必须使用一个起始节点初始化(使用iterator.setStartNode())。
     * 
     */
    public DTMAxisIterator getAxisIterator(final int axis)
    {
        switch (axis)
        {
            case Axis.SELF:
                return new SingletonIterator();
            case Axis.CHILD:
                return new ChildrenIterator();
            case Axis.PARENT:
                return new ParentIterator();
            case Axis.ANCESTOR:
                return new AncestorIterator();
            case Axis.ANCESTORORSELF:
                return (new AncestorIterator()).includeSelf();
            case Axis.ATTRIBUTE:
                return new AttributeIterator();
            case Axis.DESCENDANT:
                return new DescendantIterator();
            case Axis.DESCENDANTORSELF:
                return (new DescendantIterator()).includeSelf();
            case Axis.FOLLOWING:
                return new FollowingIterator();
            case Axis.PRECEDING:
                return new PrecedingIterator();
            case Axis.FOLLOWINGSIBLING:
                return new FollowingSiblingIterator();
            case Axis.PRECEDINGSIBLING:
                return new PrecedingSiblingIterator();
            case Axis.NAMESPACE:
                return new NamespaceIterator();
            case Axis.ROOT:
                return new RootIterator();
            default:
                BasisLibrary.runTimeError(BasisLibrary.AXIS_SUPPORT_ERR,
                        Axis.getNames(axis));
        }
        return null;
    }

    /**
     * Similar to getAxisIterator, but this one returns an iterator
     * containing nodes of a typed axis (ex.: child::foo)
     * <p>
     *  与getAxisIterator类似,但是这个方法返回一个包含类型轴节点的迭代器(例如：child :: foo)
     * 
     */
    public DTMAxisIterator getTypedAxisIterator(int axis, int type)
    {
        // Most common case handled first
        if (axis == Axis.CHILD) {
            return new TypedChildrenIterator(type);
        }

        if (type == NO_TYPE) {
            return(EMPTYITERATOR);
        }

        switch (axis)
        {
            case Axis.SELF:
                return new TypedSingletonIterator(type);
            case Axis.CHILD:
                return new TypedChildrenIterator(type);
            case Axis.PARENT:
                return new ParentIterator().setNodeType(type);
            case Axis.ANCESTOR:
                return new TypedAncestorIterator(type);
            case Axis.ANCESTORORSELF:
                return (new TypedAncestorIterator(type)).includeSelf();
            case Axis.ATTRIBUTE:
                return new TypedAttributeIterator(type);
            case Axis.DESCENDANT:
                return new TypedDescendantIterator(type);
            case Axis.DESCENDANTORSELF:
                return (new TypedDescendantIterator(type)).includeSelf();
            case Axis.FOLLOWING:
                return new TypedFollowingIterator(type);
            case Axis.PRECEDING:
                return new TypedPrecedingIterator(type);
            case Axis.FOLLOWINGSIBLING:
                return new TypedFollowingSiblingIterator(type);
            case Axis.PRECEDINGSIBLING:
                return new TypedPrecedingSiblingIterator(type);
            case Axis.NAMESPACE:
                return  new TypedNamespaceIterator(type);
            case Axis.ROOT:
                return new TypedRootIterator(type);
            default:
                BasisLibrary.runTimeError(BasisLibrary.TYPED_AXIS_SUPPORT_ERR,
                        Axis.getNames(axis));
        }
        return null;
    }

    /**
     * Do not think that this returns an iterator for the namespace axis.
     * It returns an iterator with nodes that belong in a certain namespace,
     * such as with <xsl:apply-templates select="blob/foo:*"/>
     * The 'axis' specifies the axis for the base iterator from which the
     * nodes are taken, while 'ns' specifies the namespace URI type.
     * <p>
     * 不要认为这会为命名空间轴返回一个迭代器。
     * 它返回一个具有属于某个命名空间的节点的迭代器,例如<xsl：apply-templates select ="blob / foo：*"/>'axis'指定从中获取节点的基本迭代器的轴,而'ns'指定命
     * 名空间URI类型。
     * 不要认为这会为命名空间轴返回一个迭代器。
     * 
     */
    public DTMAxisIterator getNamespaceAxisIterator(int axis, int ns)
    {

        DTMAxisIterator iterator = null;

        if (ns == NO_TYPE) {
            return EMPTYITERATOR;
        }
        else {
            switch (axis) {
                case Axis.CHILD:
                    return new NamespaceChildrenIterator(ns);
                case Axis.ATTRIBUTE:
                    return new NamespaceAttributeIterator(ns);
                default:
                    return new NamespaceWildcardIterator(axis, ns);
            }
        }
    }

    /**
     * Iterator that handles node tests that test for a namespace, but have
     * a wild card for the local name of the node, i.e., node tests of the
     * form <axis>::<prefix>:*
     * <p>
     *  迭代器,用于处理测试命名空间的节点测试,但是具有用于节点的本地名称的通配符,即<axis> :: <prefix>：形式的节点测试：
     * 
     */
    public final class NamespaceWildcardIterator
        extends InternalAxisIteratorBase
    {
        /**
         * The namespace type index.
         * <p>
         *  命名空间类型索引。
         * 
         */
        protected int m_nsType;

        /**
         * A nested typed axis iterator that retrieves nodes of the principal
         * node kind for that axis.
         * <p>
         *  嵌套类型轴迭代器,检索该轴的主节点类型的节点。
         * 
         */
        protected DTMAxisIterator m_baseIterator;

        /**
         * Constructor NamespaceWildcard
         *
         * <p>
         *  构造函数名称空间
         * 
         * 
         * @param axis The axis that this iterator will traverse
         * @param nsType The namespace type index
         */
        public NamespaceWildcardIterator(int axis, int nsType) {
            m_nsType = nsType;

            // Create a nested iterator that will select nodes of
            // the principal node kind for the selected axis.
            switch (axis) {
                case Axis.ATTRIBUTE: {
                    // For "attribute::p:*", the principal node kind is
                    // attribute
                    m_baseIterator = getAxisIterator(axis);
                }
                case Axis.NAMESPACE: {
                    // This covers "namespace::p:*".  It is syntactically
                    // correct, though it doesn't make much sense.
                    m_baseIterator = getAxisIterator(axis);
                }
                default: {
                    // In all other cases, the principal node kind is
                    // element
                    m_baseIterator = getTypedAxisIterator(axis,
                                                          DTM.ELEMENT_NODE);
                }
            }
        }

        /**
         * Set start to END should 'close' the iterator,
         * i.e. subsequent call to next() should return END.
         *
         * <p>
         *  将start设置为END应该'关闭'迭代器,即对next()的后续调用应该返回END。
         * 
         * 
         * @param node Sets the root of the iteration.
         *
         * @return A DTMAxisIterator set to the start of the iteration.
         */
        public DTMAxisIterator setStartNode(int node) {
            if (_isRestartable) {
                _startNode = node;
                m_baseIterator.setStartNode(node);
                resetPosition();
            }
            return this;
        }

        /**
         * Get the next node in the iteration.
         *
         * <p>
         *  获取迭代中的下一个节点。
         * 
         * 
         * @return The next node handle in the iteration, or END.
         */
        public int next() {
            int node;

            while ((node = m_baseIterator.next()) != END) {
                // Return only nodes that are in the selected namespace
                if (getNSType(node) == m_nsType) {
                    return returnNode(node);
                }
            }

            return END;
        }

        /**
         * Returns a deep copy of this iterator.  The cloned iterator is not
         * reset.
         *
         * <p>
         *  返回此迭代器的深度副本。克隆的迭代器不会重置。
         * 
         * 
         * @return a deep copy of this iterator.
         */
        public DTMAxisIterator cloneIterator() {
            try {
                DTMAxisIterator nestedClone = m_baseIterator.cloneIterator();
                NamespaceWildcardIterator clone =
                    (NamespaceWildcardIterator) super.clone();

                clone.m_baseIterator = nestedClone;
                clone.m_nsType = m_nsType;
                clone._isRestartable = false;

                return clone;
            } catch (CloneNotSupportedException e) {
                BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                          e.toString());
                return null;
            }
        }

        /**
         * True if this iterator has a reversed axis.
         *
         * <p>
         *  如果此迭代器具有反转轴,则为true。
         * 
         * 
         * @return <code>true</code> if this iterator is a reversed axis.
         */
        public boolean isReverse() {
            return m_baseIterator.isReverse();
        }

        public void setMark() {
            m_baseIterator.setMark();
        }

        public void gotoMark() {
            m_baseIterator.gotoMark();
        }
    }

    /**
     * Iterator that returns children within a given namespace for a
     * given node. The functionality chould be achieved by putting a
     * filter on top of a basic child iterator, but a specialised
     * iterator is used for efficiency (both speed and size of translet).
     * <p>
     *  Iterator返回给定节点的给定命名空间内的子节点。该功能可以通过在基本子迭代器的顶部放置一个过滤器来实现,但是一个专门的迭代器用于效率(速度和translet的速度和大小)。
     * 
     */
    public final class NamespaceChildrenIterator
        extends InternalAxisIteratorBase
    {

        /** The extended type ID being requested. */
        private final int _nsType;

        /**
         * Constructor NamespaceChildrenIterator
         *
         *
         * <p>
         *  构造函数NamespaceChildrenIterator
         * 
         * 
         * @param type The extended type ID being requested.
         */
        public NamespaceChildrenIterator(final int type) {
            _nsType = type;
        }

        /**
         * Set start to END should 'close' the iterator,
         * i.e. subsequent call to next() should return END.
         *
         * <p>
         *  将start设置为END应该'关闭'迭代器,即对next()的后续调用应该返回END。
         * 
         * 
         * @param node Sets the root of the iteration.
         *
         * @return A DTMAxisIterator set to the start of the iteration.
         */
        public DTMAxisIterator setStartNode(int node) {
            //%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
            if (node == DTMDefaultBase.ROOTNODE) {
                node = getDocument();
            }

            if (_isRestartable) {
                _startNode = node;
                _currentNode = (node == DTM.NULL) ? DTM.NULL : NOTPROCESSED;

                return resetPosition();
            }

            return this;
        }

        /**
         * Get the next node in the iteration.
         *
         * <p>
         *  获取迭代中的下一个节点。
         * 
         * 
         * @return The next node handle in the iteration, or END.
         */
        public int next() {
            if (_currentNode != DTM.NULL) {
                for (int node = (NOTPROCESSED == _currentNode)
                                     ? _firstch(makeNodeIdentity(_startNode))
                                     : _nextsib(_currentNode);
                     node != END;
                     node = _nextsib(node)) {
                    int nodeHandle = makeNodeHandle(node);

                    if (getNSType(nodeHandle) == _nsType) {
                        _currentNode = node;

                        return returnNode(nodeHandle);
                    }
                }
            }

            return END;
        }
    }  // end of NamespaceChildrenIterator

    /**
     * Iterator that returns attributes within a given namespace for a node.
     * <p>
     *  返回节点的给定命名空间内的属性的迭代器。
     * 
     */
    public final class NamespaceAttributeIterator
            extends InternalAxisIteratorBase
    {

        /** The extended type ID being requested. */
        private final int _nsType;

        /**
         * Constructor NamespaceAttributeIterator
         *
         *
         * <p>
         *  构造函数NamespaceAttributeIterator
         * 
         * 
         * @param nsType The extended type ID being requested.
         */
        public NamespaceAttributeIterator(int nsType) {
            super();

            _nsType = nsType;
        }

        /**
         * Set start to END should 'close' the iterator,
         * i.e. subsequent call to next() should return END.
         *
         * <p>
         * 将start设置为END应该'关闭'迭代器,即对next()的后续调用应该返回END。
         * 
         * 
         * @param node Sets the root of the iteration.
         *
         * @return A DTMAxisIterator set to the start of the iteration.
         */
        public DTMAxisIterator setStartNode(int node) {
            //%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
            if (node == DTMDefaultBase.ROOTNODE) {
                node = getDocument();
            }

            if (_isRestartable) {
                int nsType = _nsType;

                _startNode = node;

                for (node = getFirstAttribute(node);
                     node != END;
                     node = getNextAttribute(node)) {
                    if (getNSType(node) == nsType) {
                        break;
                    }
                }

                _currentNode = node;
                return resetPosition();
            }

            return this;
        }

        /**
         * Get the next node in the iteration.
         *
         * <p>
         *  获取迭代中的下一个节点。
         * 
         * 
         * @return The next node handle in the iteration, or END.
         */
        public int next() {
            int node = _currentNode;
            int nsType = _nsType;
            int nextNode;

            if (node == END) {
                return END;
            }

            for (nextNode = getNextAttribute(node);
                 nextNode != END;
                 nextNode = getNextAttribute(nextNode)) {
                if (getNSType(nextNode) == nsType) {
                    break;
                }
            }

            _currentNode = nextNode;

            return returnNode(node);
        }
    }  // end of NamespaceAttributeIterator

    /**
     * Returns an iterator with all descendants of a node that are of
     * a given type.
     * <p>
     *  返回具有给定类型的节点的所有后代的迭代器。
     * 
     */
    public DTMAxisIterator getTypedDescendantIterator(int type)
    {
        return new TypedDescendantIterator(type);
    }

    /**
     * Returns the nth descendant of a node
     * <p>
     *  返回节点的第n个子节点
     * 
     */
    public DTMAxisIterator getNthDescendant(int type, int n, boolean includeself)
    {
        DTMAxisIterator source = (DTMAxisIterator) new TypedDescendantIterator(type);
        return new NthDescendantIterator(n);
    }

    /**
     * Copy the string value of a node directly to an output handler
     * <p>
     *  将节点的字符串值直接复制到输出处理程序
     * 
     */
    public void characters(final int node, SerializationHandler handler)
        throws TransletException
    {
        if (node != DTM.NULL) {
            try {
                dispatchCharactersEvents(node, handler, false);
            } catch (SAXException e) {
                throw new TransletException(e);
            }
        }
    }

    /**
     * Copy a node-set to an output handler
     * <p>
     *  将节点集复制到输出处理程序
     * 
     */
    public void copy(DTMAxisIterator nodes, SerializationHandler handler)
        throws TransletException
    {
        int node;
        while ((node = nodes.next()) != DTM.NULL) {
            copy(node, handler);
        }
    }

    /**
     * Copy the whole tree to an output handler
     * <p>
     *  将整个树复制到输出处理程序
     * 
     */
    public void copy(SerializationHandler handler) throws TransletException
    {
        copy(getDocument(), handler);
    }

    /**
     * Performs a deep copy (ref. XSLs copy-of())
     *
     * TODO: Copy namespace declarations. Can't be done until we
     *       add namespace nodes and keep track of NS prefixes
     * TODO: Copy comment nodes
     * <p>
     *  执行深度复制(参考XSLs copy-of())
     * 
     *  TODO：复制命名空间声明。直到我们添加命名空间节点并跟踪NS前缀才能完成TODO：复制注释节点
     * 
     */
    public void copy(final int node, SerializationHandler handler)
        throws TransletException
    {
        copy(node, handler, false );
    }


 private final void copy(final int node, SerializationHandler handler, boolean isChild)
        throws TransletException
    {
     int nodeID = makeNodeIdentity(node);
        int eType = _exptype2(nodeID);
        int type = _exptype2Type(eType);

        try {
            switch(type)
            {
                case DTM.ROOT_NODE:
                case DTM.DOCUMENT_NODE:
                    for(int c = _firstch2(nodeID); c != DTM.NULL; c = _nextsib2(c)) {
                        copy(makeNodeHandle(c), handler, true);
                    }
                    break;
                case DTM.PROCESSING_INSTRUCTION_NODE:
                    copyPI(node, handler);
                    break;
                case DTM.COMMENT_NODE:
                    handler.comment(getStringValueX(node));
                    break;
                case DTM.TEXT_NODE:
                    boolean oldEscapeSetting = false;
                    boolean escapeBit = false;

                    if (_dontEscape != null) {
                        escapeBit = _dontEscape.getBit(getNodeIdent(node));
                        if (escapeBit) {
                            oldEscapeSetting = handler.setEscaping(false);
                        }
                    }

                    copyTextNode(nodeID, handler);

                    if (escapeBit) {
                        handler.setEscaping(oldEscapeSetting);
                    }
                    break;
                case DTM.ATTRIBUTE_NODE:
                    copyAttribute(nodeID, eType, handler);
                    break;
                case DTM.NAMESPACE_NODE:
                    handler.namespaceAfterStartElement(getNodeNameX(node), getNodeValue(node));
                    break;
                default:
                    if (type == DTM.ELEMENT_NODE)
                    {
                        // Start element definition
                        final String name = copyElement(nodeID, eType, handler);
                        //if(isChild) => not to copy any namespaces  from parents
                        // else copy all namespaces in scope
                        copyNS(nodeID, handler,!isChild);
                        copyAttributes(nodeID, handler);
                        // Copy element children
                        for (int c = _firstch2(nodeID); c != DTM.NULL; c = _nextsib2(c)) {
                            copy(makeNodeHandle(c), handler, true);
                        }

                        // Close element definition
                        handler.endElement(name);
                    }
                    // Shallow copy of attribute to output handler
                    else {
                        final String uri = getNamespaceName(node);
                        if (uri.length() != 0) {
                            final String prefix = getPrefix(node);
                            handler.namespaceAfterStartElement(prefix, uri);
                        }
                        handler.addAttribute(getNodeName(node), getNodeValue(node));
                    }
                    break;
            }
        }
        catch (Exception e) {
            throw new TransletException(e);
        }

    }
    /**
     * Copies a processing instruction node to an output handler
     * <p>
     *  将处理指令节点复制到输出处理程序
     * 
     */
    private void copyPI(final int node, SerializationHandler handler)
        throws TransletException
    {
        final String target = getNodeName(node);
        final String value = getStringValueX(node);

        try {
            handler.processingInstruction(target, value);
        } catch (Exception e) {
            throw new TransletException(e);
        }
    }

    /**
     * Performs a shallow copy (ref. XSLs copy())
     * <p>
     *  执行浅拷贝(ref。XSLs copy())
     * 
     */
    public String shallowCopy(final int node, SerializationHandler handler)
        throws TransletException
    {
        int nodeID = makeNodeIdentity(node);
        int exptype = _exptype2(nodeID);
        int type = _exptype2Type(exptype);

        try {
            switch(type)
            {
                case DTM.ELEMENT_NODE:
                    final String name = copyElement(nodeID, exptype, handler);
                    copyNS(nodeID, handler, true);
                    return name;
                case DTM.ROOT_NODE:
                case DTM.DOCUMENT_NODE:
                    return EMPTYSTRING;
                case DTM.TEXT_NODE:
                    copyTextNode(nodeID, handler);
                    return null;
                case DTM.PROCESSING_INSTRUCTION_NODE:
                    copyPI(node, handler);
                    return null;
                case DTM.COMMENT_NODE:
                    handler.comment(getStringValueX(node));
                    return null;
                case DTM.NAMESPACE_NODE:
                    handler.namespaceAfterStartElement(getNodeNameX(node), getNodeValue(node));
                    return null;
                case DTM.ATTRIBUTE_NODE:
                    copyAttribute(nodeID, exptype, handler);
                    return null;
                default:
                    final String uri1 = getNamespaceName(node);
                    if (uri1.length() != 0) {
                        final String prefix = getPrefix(node);
                        handler.namespaceAfterStartElement(prefix, uri1);
                    }
                    handler.addAttribute(getNodeName(node), getNodeValue(node));
                    return null;
            }
        } catch (Exception e) {
            throw new TransletException(e);
        }
    }

    /**
     * Returns a node' defined language for a node (if any)
     * <p>
     *  返回节点的定义语言(如果有)
     * 
     */
    public String getLanguage(int node)
    {
        int parent = node;
        while (DTM.NULL != parent) {
            if (DTM.ELEMENT_NODE == getNodeType(parent)) {
                int langAttr = getAttributeNode(parent, "http://www.w3.org/XML/1998/namespace", "lang");

                if (DTM.NULL != langAttr) {
                    return getNodeValue(langAttr);
                }
            }

            parent = getParent(parent);
        }
        return(null);
    }

    /**
     * Returns an instance of the DOMBuilder inner class
     * This class will consume the input document through a SAX2
     * interface and populate the tree.
     * <p>
     *  返回DOMBuilder内部类的实例此类将通过SAX2接口使用输入文档并填充树。
     * 
     */
    public DOMBuilder getBuilder()
    {
        return this;
    }

    /**
     * Return a SerializationHandler for output handling.
     * This method is used by Result Tree Fragments.
     * <p>
     *  返回SerializationHandler以进行输出处理。此方法由结果树片段使用。
     * 
     */
    public SerializationHandler getOutputDomBuilder()
    {
        return new ToXMLSAXHandler(this, "UTF-8");
    }

    /**
     * Return a instance of a DOM class to be used as an RTF
     * <p>
     *  返回一个要用作RTF的DOM类的实例
     * 
     */
    public DOM getResultTreeFrag(int initSize, int rtfType)
    {
        return getResultTreeFrag(initSize, rtfType, true);
    }

    /**
     * Return a instance of a DOM class to be used as an RTF
     *
     * <p>
     *  返回一个要用作RTF的DOM类的实例
     * 
     * 
     * @param initSize The initial size of the DOM.
     * @param rtfType The type of the RTF
     * @param addToManager true if the RTF should be registered with the DTMManager.
     * @return The DOM object which represents the RTF.
     */
    public DOM getResultTreeFrag(int initSize, int rtfType, boolean addToManager)
    {
        if (rtfType == DOM.SIMPLE_RTF) {
            if (addToManager) {
                int dtmPos = _dtmManager.getFirstFreeDTMID();
                SimpleResultTreeImpl rtf = new SimpleResultTreeImpl(_dtmManager,
                                           dtmPos << DTMManager.IDENT_DTM_NODE_BITS);
                _dtmManager.addDTM(rtf, dtmPos, 0);
                return rtf;
            }
            else {
                return new SimpleResultTreeImpl(_dtmManager, 0);
            }
        }
        else if (rtfType == DOM.ADAPTIVE_RTF) {
            if (addToManager) {
                int dtmPos = _dtmManager.getFirstFreeDTMID();
                AdaptiveResultTreeImpl rtf = new AdaptiveResultTreeImpl(_dtmManager,
                                       dtmPos << DTMManager.IDENT_DTM_NODE_BITS,
                                       m_wsfilter, initSize, m_buildIdIndex);
                _dtmManager.addDTM(rtf, dtmPos, 0);
                return rtf;

            }
            else {
                return new AdaptiveResultTreeImpl(_dtmManager, 0,
                                       m_wsfilter, initSize, m_buildIdIndex);
            }
        }
        else {
            return (DOM) _dtmManager.getDTM(null, true, m_wsfilter,
                                            true, false, false,
                                            initSize, m_buildIdIndex);
        }
    }

    /**
     * %HZ% Need Javadoc
     * <p>
     *  ％HZ％需要Javadoc
     * 
     */
    public Hashtable getElementsWithIDs() {
        if (m_idAttributes == null) {
            return null;
        }

        // Convert a java.util.Hashtable to an xsltc.runtime.Hashtable
        Enumeration idValues = m_idAttributes.keys();
        if (!idValues.hasMoreElements()) {
            return null;
        }

        Hashtable idAttrsTable = new Hashtable();

        while (idValues.hasMoreElements()) {
            Object idValue = idValues.nextElement();

            idAttrsTable.put(idValue, m_idAttributes.get(idValue));
        }

        return idAttrsTable;
    }

    /**
     * The getUnparsedEntityURI function returns the URI of the unparsed
     * entity with the specified name in the same document as the context
     * node (see [3.3 Unparsed Entities]). It returns the empty string if
     * there is no such entity.
     * <p>
     *  getUnparsedEntityURI函数返回在与上下文节点相同的文档中具有指定名称的未解析实体的URI(参见[3.3 Unparsed Entities])。如果没有这样的实体,它返回空字符串。
     */
    public String getUnparsedEntityURI(String name)
    {
        // Special handling for DOM input
        if (_document != null) {
            String uri = "";
            DocumentType doctype = _document.getDoctype();
            if (doctype != null) {
                NamedNodeMap entities = doctype.getEntities();

                if (entities == null) {
                    return uri;
                }

                Entity entity = (Entity) entities.getNamedItem(name);

                if (entity == null) {
                    return uri;
                }

                String notationName = entity.getNotationName();
                if (notationName != null) {
                    uri = entity.getSystemId();
                    if (uri == null) {
                        uri = entity.getPublicId();
                    }
                }
            }
            return uri;
        }
        else {
            return super.getUnparsedEntityURI(name);
        }
    }

}
