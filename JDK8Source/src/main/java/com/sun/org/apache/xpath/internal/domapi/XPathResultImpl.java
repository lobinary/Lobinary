/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2005 The Apache Software Foundation.
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
 *  版权所有2002-2005 Apache软件基金会
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XPathResultImpl.java,v 1.2.4.1 2005/09/10 04:18:54 jeffsuttor Exp $
 * <p>
 *  $ Id：XPathResultImpl.java,v 1.2.4.1 2005/09/10 04:18:54 jeffsuttor Exp $
 * 
 */


package com.sun.org.apache.xpath.internal.domapi;

import javax.xml.transform.TransformerException;

import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;
import com.sun.org.apache.xpath.internal.res.XPATHMessages;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.xpath.XPathException;
import org.w3c.dom.xpath.XPathResult;

/**
 *
 * The class provides an implementation XPathResult according
 * to the DOM L3 XPath Specification, Working Group Note 26 February 2004.
 *
 * <p>See also the <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document Object Model (DOM) Level 3 XPath Specification</a>.</p>
 *
 * <p>The <code>XPathResult</code> interface represents the result of the
 * evaluation of an XPath expression within the context of a particular
 * node. Since evaluation of an XPath expression can result in various
 * result types, this object makes it possible to discover and manipulate
 * the type and value of the result.</p>
 *
 * <p>This implementation wraps an <code>XObject</code>.
 *
 * <p>
 *  该类根据DOM L3 XPath规范提供了一个实现XPathResult,工作组注2004年2月26日。
 * 
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>文档对象模型(DOM)3级XPath规范< a>。
 * </p>。
 * 
 *  <p> <code> XPathResult </code>接口表示在特定节点的上下文中评估XPath表达式的结果。
 * 由于XPath表达式的求值可能导致各种结果类型,因此该对象可以发现和操作结果的类型和值。</p>。
 * 
 *  <p>这个实现包装一个<code> XObject </code>。
 * 
 * 
 * @see com.sun.org.apache.xpath.internal.objects.XObject
 * @see org.w3c.dom.xpath.XPathResult
 *
 * @xsl.usage internal
 */
class XPathResultImpl implements XPathResult, EventListener {

        /**
     *  The wrapped XObject
     * <p>
     *  包装的XObject
     * 
     */
        final private XObject m_resultObj;

        /**
         * The xpath object that wraps the expression used for this result.
         * <p>
         * 包装用于此结果的表达式的xpath对象。
         * 
         */
        final private XPath m_xpath;

        /**
     *  This the type specified by the user during construction.  Typically
         *  the constructor will be called by com.sun.org.apache.xpath.internal.XPath.evaluate().
         * <p>
         *  这是用户在构建期间指定的类型。通常,构造函数将由com.sun.org.apache.xpath.internal.XPath.evaluate()调用。
         * 
     */
        final private short m_resultType;

        private boolean m_isInvalidIteratorState = false;

    /**
     * Only used to attach a mutation event handler when specified
     * type is an iterator type.
     * <p>
     *  仅当指定的类型是迭代器类型时,用于附加一个突变事件处理程序。
     * 
     */
        final private Node m_contextNode;

        /**
     *  The iterator, if this is an iterator type.
     * <p>
     *  迭代器,如果这是一个迭代器类型。
     * 
     */
        private NodeIterator m_iterator = null;;

        /**
     *  The list, if this is a snapshot type.
     * <p>
     *  列表,如果这是快照类型。
     * 
     */
        private NodeList m_list = null;


        /**
         * Constructor for XPathResultImpl.
     *
     * For internal use only.
     * <p>
     *  XPathResultImpl的构造函数。
     * 
     *  仅限内部使用。
     * 
         */
         XPathResultImpl(short type, XObject result, Node contextNode, XPath xpath) {
                // Check that the type is valid
                if (!isValidType(type)) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INVALID_XPATH_TYPE, new Object[] {new Integer(type)});
            throw new XPathException(XPathException.TYPE_ERR,fmsg); // Invalid XPath type argument: {0}
                }

        // Result object should never be null!
        if (null == result) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_EMPTY_XPATH_RESULT, null);
            throw new XPathException(XPathException.INVALID_EXPRESSION_ERR,fmsg); // Empty XPath result object
        }

        this.m_resultObj = result;
        this.m_contextNode = contextNode;
        this.m_xpath = xpath;

        // If specified result was ANY_TYPE, determine XObject type
        if (type == ANY_TYPE) {
            this.m_resultType = getTypeFromXObject(result);
        } else {
            this.m_resultType = type;
        }

        // If the context node supports DOM Events and the type is one of the iterator
        // types register this result as an event listener
        if (((m_resultType == XPathResult.ORDERED_NODE_ITERATOR_TYPE) ||
            (m_resultType == XPathResult.UNORDERED_NODE_ITERATOR_TYPE))) {
                addEventListener();

        }// else can we handle iterator types if contextNode doesn't support EventTarget??

        // If this is an iterator type get the iterator
        if ((m_resultType == ORDERED_NODE_ITERATOR_TYPE) ||
            (m_resultType == UNORDERED_NODE_ITERATOR_TYPE) ||
            (m_resultType == ANY_UNORDERED_NODE_TYPE) ||
            (m_resultType == FIRST_ORDERED_NODE_TYPE))  {

            try {
                m_iterator = m_resultObj.nodeset();
            } catch (TransformerException te) {
                // probably not a node type
                                String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INCOMPATIBLE_TYPES, new Object[] {m_xpath.getPatternString(), getTypeString(getTypeFromXObject(m_resultObj)),getTypeString(m_resultType)});
                            throw new XPathException(XPathException.TYPE_ERR, fmsg);  // "The XPathResult of XPath expression {0} has an XPathResultType of {1} which cannot be coerced into the specified XPathResultType of {2}."},
           }

                // If user requested ordered nodeset and result is unordered
                // need to sort...TODO
    //            if ((m_resultType == ORDERED_NODE_ITERATOR_TYPE) &&
    //                (!(((DTMNodeIterator)m_iterator).getDTMIterator().isDocOrdered()))) {
    //
    //            }

        // If it's a snapshot type, get the nodelist
        } else if ((m_resultType == UNORDERED_NODE_SNAPSHOT_TYPE) ||
                   (m_resultType == ORDERED_NODE_SNAPSHOT_TYPE)) {
            try {
                   m_list = m_resultObj.nodelist();
            } catch (TransformerException te) {
                        // probably not a node type
                                String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_INCOMPATIBLE_TYPES, new Object[] {m_xpath.getPatternString(), getTypeString(getTypeFromXObject(m_resultObj)),getTypeString(m_resultType)});
                                throw new XPathException(XPathException.TYPE_ERR, fmsg); // "The XPathResult of XPath expression {0} has an XPathResultType of {1} which cannot be coerced into the specified XPathResultType of {2}."},
            }
        }
        }

        /**
        /* <p>
        /* 
         * @see org.w3c.dom.xpath.XPathResult#getResultType()
         */
        public short getResultType() {
                return m_resultType;
        }

        /**
         *  The value of this number result.
         * <p>
         *  此数字的值结果。
         * 
         * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>NUMBER_TYPE</code>.
         * @see org.w3c.dom.xpath.XPathResult#getNumberValue()
         */
        public double getNumberValue() throws XPathException {
                if (getResultType() != NUMBER_TYPE) {
                        String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_CONVERT_XPATHRESULTTYPE_TO_NUMBER, new Object[] {m_xpath.getPatternString(), getTypeString(m_resultType)});
                        throw new XPathException(XPathException.TYPE_ERR,fmsg);
//              "The XPathResult of XPath expression {0} has an XPathResultType of {1} which cannot be converted to a number"
                } else {
                        try {
                           return m_resultObj.num();
                        } catch (Exception e) {
                                // Type check above should prevent this exception from occurring.
                                throw new XPathException(XPathException.TYPE_ERR,e.getMessage());
                        }
                }
        }

        /**
         * The value of this string result.
         * <p>
         *  此字符串的值result。
         * 
         * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>STRING_TYPE</code>.
     *
         * @see org.w3c.dom.xpath.XPathResult#getStringValue()
         */
        public String getStringValue() throws XPathException {
                if (getResultType() != STRING_TYPE) {
                        String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_CONVERT_TO_STRING, new Object[] {m_xpath.getPatternString(), m_resultObj.getTypeString()});
                        throw new XPathException(XPathException.TYPE_ERR,fmsg);
//              "The XPathResult of XPath expression {0} has an XPathResultType of {1} which cannot be converted to a string."
                } else {
                        try {
                           return m_resultObj.str();
                        } catch (Exception e) {
                                // Type check above should prevent this exception from occurring.
                                throw new XPathException(XPathException.TYPE_ERR,e.getMessage());
                        }
                }
        }

        /**
        /* <p>
        /* 
         * @see org.w3c.dom.xpath.XPathResult#getBooleanValue()
         */
        public boolean getBooleanValue() throws XPathException {
                if (getResultType() != BOOLEAN_TYPE) {
                        String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_CONVERT_TO_BOOLEAN, new Object[] {m_xpath.getPatternString(), getTypeString(m_resultType)});
                        throw new XPathException(XPathException.TYPE_ERR,fmsg);
//              "The XPathResult of XPath expression {0} has an XPathResultType of {1} which cannot be converted to a boolean."
                } else {
                        try {
                           return m_resultObj.bool();
                        } catch (TransformerException e) {
                                // Type check above should prevent this exception from occurring.
                                throw new XPathException(XPathException.TYPE_ERR,e.getMessage());
                        }
                }
        }

        /**
         * The value of this single node result, which may be <code>null</code>.
         * <p>
         *  这个单节点结果的值,可以是<code> null </code>。
         * 
         * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>ANY_UNORDERED_NODE_TYPE</code> or
     *   <code>FIRST_ORDERED_NODE_TYPE</code>.
     *
         * @see org.w3c.dom.xpath.XPathResult#getSingleNodeValue()
         */
        public Node getSingleNodeValue() throws XPathException {

                if ((m_resultType != ANY_UNORDERED_NODE_TYPE) &&
                    (m_resultType != FIRST_ORDERED_NODE_TYPE)) {
                                String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_CONVERT_TO_SINGLENODE, new Object[] {m_xpath.getPatternString(), getTypeString(m_resultType)});
                                throw new XPathException(XPathException.TYPE_ERR,fmsg);
//                              "The XPathResult of XPath expression {0} has an XPathResultType of {1} which cannot be converted to a single node.
//                               This method applies only to types ANY_UNORDERED_NODE_TYPE and FIRST_ORDERED_NODE_TYPE."
            }

                NodeIterator result = null;
                try {
                        result = m_resultObj.nodeset();
                } catch (TransformerException te) {
                        throw new XPathException(XPathException.TYPE_ERR,te.getMessage());
                }

        if (null == result) return null;

        Node node = result.nextNode();

        // Wrap "namespace node" in an XPathNamespace
        if (isNamespaceNode(node)) {
            return new XPathNamespaceImpl(node);
        } else {
            return node;
        }
        }

        /**
        /* <p>
        /* 
         * @see org.w3c.dom.xpath.XPathResult#getInvalidIteratorState()
         */
        public boolean getInvalidIteratorState() {
                return m_isInvalidIteratorState;
        }

        /**
         * The number of nodes in the result snapshot. Valid values for
     * snapshotItem indices are <code>0</code> to
     * <code>snapshotLength-1</code> inclusive.
     * <p>
     *  结果快照中的节点数。 snapshotItem索引的有效值为<code> 0 </code>至<code> snapshotLength-1 </code>(含)。
     * 
     * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>UNORDERED_NODE_SNAPSHOT_TYPE</code> or
     *   <code>ORDERED_NODE_SNAPSHOT_TYPE</code>.
     *
         * @see org.w3c.dom.xpath.XPathResult#getSnapshotLength()
         */
        public int getSnapshotLength() throws XPathException {

                if ((m_resultType != UNORDERED_NODE_SNAPSHOT_TYPE) &&
                    (m_resultType != ORDERED_NODE_SNAPSHOT_TYPE)) {
                                String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_CANT_GET_SNAPSHOT_LENGTH, new Object[] {m_xpath.getPatternString(), getTypeString(m_resultType)});
                                throw new XPathException(XPathException.TYPE_ERR,fmsg);
//                              "The method getSnapshotLength cannot be called on the XPathResult of XPath expression {0} because its XPathResultType is {1}.
            }

                return m_list.getLength();
        }

        /**
         * Iterates and returns the next node from the node set or
     * <code>null</code>if there are no more nodes.
     * <p>
     *  如果没有更多节点,则迭代并返回节点集合中的下一个节点或<code> null </code>。
     * 
     * 
     * @return Returns the next node.
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>UNORDERED_NODE_ITERATOR_TYPE</code> or
     *   <code>ORDERED_NODE_ITERATOR_TYPE</code>.
     * @exception DOMException
     *   INVALID_STATE_ERR: The document has been mutated since the result was
     *   returned.
         * @see org.w3c.dom.xpath.XPathResult#iterateNext()
         */
        public Node iterateNext() throws XPathException, DOMException {
                if ((m_resultType != UNORDERED_NODE_ITERATOR_TYPE) &&
                    (m_resultType != ORDERED_NODE_ITERATOR_TYPE)) {
          String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NON_ITERATOR_TYPE, new Object[] {m_xpath.getPatternString(), getTypeString(m_resultType)});
                  throw new XPathException(XPathException.TYPE_ERR, fmsg);
//                "The method iterateNext cannot be called on the XPathResult of XPath expression {0} because its XPathResultType is {1}.
//                This method applies only to types UNORDERED_NODE_ITERATOR_TYPE and ORDERED_NODE_ITERATOR_TYPE."},
            }

                if (getInvalidIteratorState()) {
          String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_DOC_MUTATED, null);
                  throw new DOMException(DOMException.INVALID_STATE_ERR,fmsg);  // Document mutated since result was returned. Iterator is invalid.
                }

        Node node = m_iterator.nextNode();
        if(null == node)
                removeEventListener(); // JIRA 1673
        // Wrap "namespace node" in an XPathNamespace
        if (isNamespaceNode(node)) {
            return new XPathNamespaceImpl(node);
        } else {
            return node;
        }
        }

    /**
     * Returns the <code>index</code>th item in the snapshot collection. If
     * <code>index</code> is greater than or equal to the number of nodes in
     * the list, this method returns <code>null</code>. Unlike the iterator
     * result, the snapshot does not become invalid, but may not correspond
     * to the current document if it is mutated.
     * <p>
     *  返回快照集合中的<code> index </code> th项。如果<code> index </code>大于或等于列表中的节点数,则此方法返回<code> null </code>。
     * 与迭代器结果不同,快照不会变得无效,但如果变异,则可能不对应当前文档。
     * 
     * 
     * @param index Index into the snapshot collection.
     * @return The node at the <code>index</code>th position in the
     *   <code>NodeList</code>, or <code>null</code> if that is not a valid
     *   index.
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>UNORDERED_NODE_SNAPSHOT_TYPE</code> or
     *   <code>ORDERED_NODE_SNAPSHOT_TYPE</code>.
     *
         * @see org.w3c.dom.xpath.XPathResult#snapshotItem(int)
         */
        public Node snapshotItem(int index) throws XPathException {

                if ((m_resultType != UNORDERED_NODE_SNAPSHOT_TYPE) &&
                    (m_resultType != ORDERED_NODE_SNAPSHOT_TYPE)) {
           String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NON_SNAPSHOT_TYPE, new Object[] {m_xpath.getPatternString(), getTypeString(m_resultType)});
           throw new XPathException(XPathException.TYPE_ERR, fmsg);
//              "The method snapshotItem cannot be called on the XPathResult of XPath expression {0} because its XPathResultType is {1}.
//              This method applies only to types UNORDERED_NODE_SNAPSHOT_TYPE and ORDERED_NODE_SNAPSHOT_TYPE."},
            }

        Node node = m_list.item(index);

        // Wrap "namespace node" in an XPathNamespace
        if (isNamespaceNode(node)) {
            return new XPathNamespaceImpl(node);
        } else {
            return node;
        }
        }


        /**
         * Check if the specified type is one of the supported types.
         * <p>
         *  检查指定的类型是否是支持的类型之一。
         * 
         * 
         * @param type The specified type
         *
         * @return true If the specified type is supported; otherwise, returns false.
         */
        static boolean isValidType( short type ) {
                switch (type) {
                        case ANY_TYPE:
                        case NUMBER_TYPE:
                        case STRING_TYPE:
                        case BOOLEAN_TYPE:
                        case UNORDERED_NODE_ITERATOR_TYPE:
                        case ORDERED_NODE_ITERATOR_TYPE:
                        case UNORDERED_NODE_SNAPSHOT_TYPE:
                        case ORDERED_NODE_SNAPSHOT_TYPE:
                        case ANY_UNORDERED_NODE_TYPE:
                        case FIRST_ORDERED_NODE_TYPE: return true;
                        default: return false;
                }
        }

        /**
        /* <p>
        /* 
         * @see org.w3c.dom.events.EventListener#handleEvent(Event)
         */
        public void handleEvent(Event event) {

                if (event.getType().equals("DOMSubtreeModified")) {
                        // invalidate the iterator
                        m_isInvalidIteratorState = true;

                        // deregister as a listener to reduce computational load
                        removeEventListener();
                }
        }

  /**
   * Given a request type, return the equivalent string.
   * For diagnostic purposes.
   *
   * <p>
   *  给定一个请求类型,返回等效的字符串。用于诊断目的。
   * 
   * 
   * @return type string
   */
  private String getTypeString(int type)
  {
     switch (type) {
      case ANY_TYPE: return "ANY_TYPE";
      case ANY_UNORDERED_NODE_TYPE: return "ANY_UNORDERED_NODE_TYPE";
      case BOOLEAN_TYPE: return "BOOLEAN";
      case FIRST_ORDERED_NODE_TYPE: return "FIRST_ORDERED_NODE_TYPE";
      case NUMBER_TYPE: return "NUMBER_TYPE";
      case ORDERED_NODE_ITERATOR_TYPE: return "ORDERED_NODE_ITERATOR_TYPE";
      case ORDERED_NODE_SNAPSHOT_TYPE: return "ORDERED_NODE_SNAPSHOT_TYPE";
      case STRING_TYPE: return "STRING_TYPE";
      case UNORDERED_NODE_ITERATOR_TYPE: return "UNORDERED_NODE_ITERATOR_TYPE";
      case UNORDERED_NODE_SNAPSHOT_TYPE: return "UNORDERED_NODE_SNAPSHOT_TYPE";
      default: return "#UNKNOWN";
    }
  }

  /**
   * Given an XObject, determine the corresponding DOM XPath type
   *
   * <p>
   *  给定一个XObject,确定相应的DOM XPath类型
   * 
   * 
   * @return type string
   */
  private short getTypeFromXObject(XObject object) {
      switch (object.getType()) {
        case XObject.CLASS_BOOLEAN: return BOOLEAN_TYPE;
        case XObject.CLASS_NODESET: return UNORDERED_NODE_ITERATOR_TYPE;
        case XObject.CLASS_NUMBER: return NUMBER_TYPE;
        case XObject.CLASS_STRING: return STRING_TYPE;
        // XPath 2.0 types
//          case XObject.CLASS_DATE:
//          case XObject.CLASS_DATETIME:
//          case XObject.CLASS_DTDURATION:
//          case XObject.CLASS_GDAY:
//          case XObject.CLASS_GMONTH:
//          case XObject.CLASS_GMONTHDAY:
//          case XObject.CLASS_GYEAR:
//          case XObject.CLASS_GYEARMONTH:
//          case XObject.CLASS_TIME:
//          case XObject.CLASS_YMDURATION: return STRING_TYPE; // treat all date types as strings?

        case XObject.CLASS_RTREEFRAG: return UNORDERED_NODE_ITERATOR_TYPE;
        case XObject.CLASS_NULL: return ANY_TYPE; // throw exception ?
        default: return ANY_TYPE; // throw exception ?
    }

  }

/**
 * Given a node, determine if it is a namespace node.
 *
 * <p>
 *  给定一个节点,确定它是否是一个命名空间节点。
 * 
 * 
 * @param node
 *
 * @return boolean Returns true if this is a namespace node; otherwise, returns false.
 */
  private boolean isNamespaceNode(Node node) {

     if ((null != node) &&
         (node.getNodeType() == Node.ATTRIBUTE_NODE) &&
         (node.getNodeName().startsWith("xmlns:") || node.getNodeName().equals("xmlns"))) {
        return true;
     } else {
        return false;
     }
  }

/**
 * Add m_contextNode to Event Listner to listen for Mutations Events
 *
 * <p>
 *  将m_contextNode添加到事件Listner以侦听Mutations事件
 * 
 */
  private void addEventListener(){
        if(m_contextNode instanceof EventTarget)
                ((EventTarget)m_contextNode).addEventListener("DOMSubtreeModified",this,true);

  }


/**
 * Remove m_contextNode to Event Listner to listen for Mutations Events
 *
 * <p>
 * 删除m_contextNode到事件侦听器以侦听Mutations事件
 */
private void removeEventListener(){
        if(m_contextNode instanceof EventTarget)
                ((EventTarget)m_contextNode).removeEventListener("DOMSubtreeModified",this,true);
}

}
