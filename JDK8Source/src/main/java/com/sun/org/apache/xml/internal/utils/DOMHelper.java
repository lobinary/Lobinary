/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: DOMHelper.java,v 1.2.4.1 2005/09/15 08:15:40 suresh_emailid Exp $
 * <p>
 *  $ Id：DOMHelper.java,v 1.2.4.1 2005/09/15 08:15:40 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.util.Hashtable;
import java.util.Vector;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeProxy;
import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
/* <p>
/* 
 * @deprecated Since the introduction of the DTM, this class will be removed.
 * This class provides a front-end to DOM implementations, providing
 * a number of utility functions that either aren't yet standardized
 * by the DOM spec or that are defined in optional DOM modules and
 * hence may not be present in all DOMs.
 */
public class DOMHelper
{

  /**
   * DOM Level 1 did not have a standard mechanism for creating a new
   * Document object. This function provides a DOM-implementation-independent
   * abstraction for that for that concept. It's typically used when
   * outputting a new DOM as the result of an operation.
   * <p>
   * TODO: This isn't directly compatable with DOM Level 2.
   * The Level 2 createDocument call also creates the root
   * element, and thus requires that you know what that element will be
   * before creating the Document. We should think about whether we want
   * to change this code, and the callers, so we can use the DOM's own
   * method. (It's also possible that DOM Level 3 may relax this
   * sequence, but you may give up some intelligence in the DOM by
   * doing so; the intent was that knowing the document type and root
   * element might let the DOM automatically switch to a specialized
   * subclass for particular kinds of documents.)
   *
   * <p>
   *  DOM Level 1没有用于创建新Document对象的标准机制。这个函数为那个概念提供了一个DOM实现无关的抽象。它通常在作为操作的结果输出新的DOM时使用。
   * <p>
   * TODO：这不直接与DOM Level 2兼容。Level 2 createDocument调用也创建根元素,因此要求您在创建文档之前知道该元素将是什么。
   * 我们应该考虑我们是否要改变这个代码和调用者,这样我们可以使用DOM自己的方法。
   *  (也有可能DOM Level 3可以放松这个序列,但是你可以通过这样做放弃DOM中的一些智能;意图是知道文档类型和根元素可能会让DOM自动切换到特定的子类种类的文件。)。
   * 
   * 
   * @param isSecureProcessing state of the secure processing feature.
   * @return The newly created DOM Document object, with no children, or
   * null if we can't find a DOM implementation that permits creating
   * new empty Documents.
   */
  public static Document createDocument(boolean isSecureProcessing)
  {

    try
    {

      // Use an implementation of the JAVA API for XML Parsing 1.0 to
      // create a DOM Document node to contain the result.
      DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();

      dfactory.setNamespaceAware(true);
      dfactory.setValidating(true);

      if (isSecureProcessing)
      {
        try
        {
          dfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        }
        catch (ParserConfigurationException pce) {}
      }

      DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
      Document outNode = docBuilder.newDocument();

      return outNode;
    }
    catch (ParserConfigurationException pce)
    {
      throw new RuntimeException(
        XMLMessages.createXMLMessage(
          XMLErrorResources.ER_CREATEDOCUMENT_NOT_SUPPORTED, null));  //"createDocument() not supported in XPathContext!");

      // return null;
    }
  }

  /**
   * DOM Level 1 did not have a standard mechanism for creating a new
   * Document object. This function provides a DOM-implementation-independent
   * abstraction for that for that concept. It's typically used when
   * outputting a new DOM as the result of an operation.
   *
   * <p>
   *  DOM Level 1没有用于创建新Document对象的标准机制。这个函数为那个概念提供了一个DOM实现无关的抽象。它通常在作为操作的结果输出新的DOM时使用。
   * 
   * 
   * @return The newly created DOM Document object, with no children, or
   * null if we can't find a DOM implementation that permits creating
   * new empty Documents.
   */
  public static Document createDocument()
  {
    return createDocument(false);
  }

  /**
   * Tells, through the combination of the default-space attribute
   * on xsl:stylesheet, xsl:strip-space, xsl:preserve-space, and the
   * xml:space attribute, whether or not extra whitespace should be stripped
   * from the node.  Literal elements from template elements should
   * <em>not</em> be tested with this function.
   * <p>
   *  通过xsl：stylesheet,xsl：strip-space,xsl：preserve-space和xml：space属性上的default-space属性的组合,告知是否应该从节点剥离多余的空格
   * 。
   * 来自模板元素的文字元素应<em>不</em>使用此函数进行测试。
   * 
   * 
   * @param textNode A text node from the source tree.
   * @return true if the text node should be stripped of extra whitespace.
   *
   * @throws javax.xml.transform.TransformerException
   * @xsl.usage advanced
   */
  public boolean shouldStripSourceNode(Node textNode)
          throws javax.xml.transform.TransformerException
  {

    // return (null == m_envSupport) ? false : m_envSupport.shouldStripSourceNode(textNode);
    return false;
  }

  /**
   * Supports the XPath function GenerateID by returning a unique
   * identifier string for any given DOM Node.
   * <p>
   * Warning: The base implementation uses the Node object's hashCode(),
   * which is NOT guaranteed to be unique. If that method hasn't been
   * overridden in this DOM ipmlementation, most Java implementions will
   * derive it from the object's address and should be OK... but if
   * your DOM uses a different definition of hashCode (eg hashing the
   * contents of the subtree), or if your DOM may have multiple objects
   * that represent a single Node in the data structure (eg via proxying),
   * you may need to find another way to assign a unique identifier.
   * <p>
   * Also, be aware that if nodes are destroyed and recreated, there is
   * an open issue regarding whether an ID may be reused. Currently
   * we're assuming that the input document is stable for the duration
   * of the XPath/XSLT operation, so this shouldn't arise in this context.
   * <p>
   * (DOM Level 3 is investigating providing a unique node "key", but
   * that won't help Level 1 and Level 2 implementations.)
   *
   * <p>
   *  通过返回任何给定DOM节点的唯一标识符字符串来支持XPath函数GenerateID。
   * <p>
   * 警告：基本实现使用Node对象的hashCode(),它不能保证是唯一的。
   * 如果这个方法没有被覆盖在这个DOM的ipmlementation,大多数Java实现将派生自对象的地址,应该是OK ...但如果你的DOM使用不同的hashCode的定义(例如散列子树的内容)或者如果您
   * 的DOM可能有多个对象表示数据结构中的单个节点(例如通过代理),您可能需要找到另一种方式来分配唯一标识符。
   * 警告：基本实现使用Node对象的hashCode(),它不能保证是唯一的。
   * <p>
   *  此外,请注意,如果节点被销毁和重新创建,有一个开放的问题,关于ID是否可以重复使用。目前,我们假设输入文档在XPath / XSLT操作期间是稳定的,因此在此上下文中不应出现。
   * <p>
   *  (DOM Level 3正在研究提供一个唯一的节点"key",但这不会帮助Level 1和Level 2的实现。)
   * 
   * 
   * @param node whose identifier you want to obtain
   *
   * @return a string which should be different for every Node object.
   */
  public String getUniqueID(Node node)
  {
    return "N" + Integer.toHexString(node.hashCode()).toUpperCase();
  }

  /**
   * Figure out whether node2 should be considered as being later
   * in the document than node1, in Document Order as defined
   * by the XPath model. This may not agree with the ordering defined
   * by other XML applications.
   * <p>
   * There are some cases where ordering isn't defined, and neither are
   * the results of this function -- though we'll generally return true.
   *
   * TODO: Make sure this does the right thing with attribute nodes!!!
   *
   * <p>
   *  确定在XPath模型中定义的文档顺序中node2是否应该被认为是文档的后面,而不是node1。这可能与其他XML应用程序定义的顺序不一致。
   * <p>
   *  有些情况下,没有定义顺序,并且这个函数的结果也没有定义,尽管我们通常返回true。
   * 
   *  TODO：确保这做正确的事情与属性节点！
   * 
   * 
   * @param node1 DOM Node to perform position comparison on.
   * @param node2 DOM Node to perform position comparison on .
   *
   * @return false if node2 comes before node1, otherwise return true.
   * You can think of this as
   * <code>(node1.documentOrderPosition &lt;= node2.documentOrderPosition)</code>.
   */
  public static boolean isNodeAfter(Node node1, Node node2)
  {
    if (node1 == node2 || isNodeTheSame(node1, node2))
      return true;

        // Default return value, if there is no defined ordering
    boolean isNodeAfter = true;

    Node parent1 = getParentOfNode(node1);
    Node parent2 = getParentOfNode(node2);

    // Optimize for most common case
    if (parent1 == parent2 || isNodeTheSame(parent1, parent2))  // then we know they are siblings
    {
      if (null != parent1)
        isNodeAfter = isNodeAfterSibling(parent1, node1, node2);
      else
      {
                  // If both parents are null, ordering is not defined.
                  // We're returning a value in lieu of throwing an exception.
                  // Not a case we expect to arise in XPath, but beware if you
                  // try to reuse this method.

                  // We can just fall through in this case, which allows us
                  // to hit the debugging code at the end of the function.
          //return isNodeAfter;
      }
    }
    else
    {

      // General strategy: Figure out the lengths of the two
      // ancestor chains, reconcile the lengths, and look for
          // the lowest common ancestor. If that ancestor is one of
          // the nodes being compared, it comes before the other.
      // Otherwise perform a sibling compare.
                //
                // NOTE: If no common ancestor is found, ordering is undefined
                // and we return the default value of isNodeAfter.

      // Count parents in each ancestor chain
      int nParents1 = 2, nParents2 = 2;  // include node & parent obtained above

      while (parent1 != null)
      {
        nParents1++;

        parent1 = getParentOfNode(parent1);
      }

      while (parent2 != null)
      {
        nParents2++;

        parent2 = getParentOfNode(parent2);
      }

          // Initially assume scan for common ancestor starts with
          // the input nodes.
      Node startNode1 = node1, startNode2 = node2;

      // If one ancestor chain is longer, adjust its start point
          // so we're comparing at the same depths
      if (nParents1 < nParents2)
      {
        // Adjust startNode2 to depth of startNode1
        int adjust = nParents2 - nParents1;

        for (int i = 0; i < adjust; i++)
        {
          startNode2 = getParentOfNode(startNode2);
        }
      }
      else if (nParents1 > nParents2)
      {
        // adjust startNode1 to depth of startNode2
        int adjust = nParents1 - nParents2;

        for (int i = 0; i < adjust; i++)
        {
          startNode1 = getParentOfNode(startNode1);
        }
      }

      Node prevChild1 = null, prevChild2 = null;  // so we can "back up"

      // Loop up the ancestor chain looking for common parent
      while (null != startNode1)
      {
        if (startNode1 == startNode2 || isNodeTheSame(startNode1, startNode2))  // common parent?
        {
          if (null == prevChild1)  // first time in loop?
          {

            // Edge condition: one is the ancestor of the other.
            isNodeAfter = (nParents1 < nParents2) ? true : false;

            break;  // from while loop
          }
          else
          {
                        // Compare ancestors below lowest-common as siblings
            isNodeAfter = isNodeAfterSibling(startNode1, prevChild1,
                                             prevChild2);

            break;  // from while loop
          }
        }  // end if(startNode1 == startNode2)

                // Move up one level and try again
        prevChild1 = startNode1;
        startNode1 = getParentOfNode(startNode1);
        prevChild2 = startNode2;
        startNode2 = getParentOfNode(startNode2);
      }  // end while(parents exist to examine)
    }  // end big else (not immediate siblings)

        // WARNING: The following diagnostic won't report the early
        // "same node" case. Fix if/when needed.

    /* -- please do not remove... very useful for diagnostics --
    System.out.println("node1 = "+node1.getNodeName()+"("+node1.getNodeType()+")"+
    ", node2 = "+node2.getNodeName()
    +"("+node2.getNodeType()+")"+
    /* <p>
    /*  System.out.println("node1 ="+ node1.getNodeName()+"("+ node1.getNodeType()+")"+",node2 ="+ node2.get
    /* NodeName()+"("+ node2.getNodeType ()+")"+。
    /* 
    /* 
    ", isNodeAfter = "+isNodeAfter); */
    return isNodeAfter;
  }  // end isNodeAfter(Node node1, Node node2)

  /**
   * Use DTMNodeProxy to determine whether two nodes are the same.
   *
   * <p>
   * 使用DTMNodeProxy确定两个节点是否相同。
   * 
   * 
   * @param node1 The first DOM node to compare.
   * @param node2 The second DOM node to compare.
   * @return true if the two nodes are the same.
   */
  public static boolean isNodeTheSame(Node node1, Node node2)
  {
    if (node1 instanceof DTMNodeProxy && node2 instanceof DTMNodeProxy)
      return ((DTMNodeProxy)node1).equals((DTMNodeProxy)node2);
    else
      return (node1 == node2);
  }

  /**
   * Figure out if child2 is after child1 in document order.
   * <p>
   * Warning: Some aspects of "document order" are not well defined.
   * For example, the order of attributes is considered
   * meaningless in XML, and the order reported by our model will
   * be consistant for a given invocation but may not
   * match that of either the source file or the serialized output.
   *
   * <p>
   *  确定child2是否在文档顺序中的child1之后。
   * <p>
   *  警告："文档顺序"的某些方面没有明确定义。例如,在XML中属性的顺序被认为是无意义的,并且我们的模型报告的顺序对于给定的调用将是一致的,但可能不匹配源文件或串行化输出的顺序。
   * 
   * 
   * @param parent Must be the parent of both child1 and child2.
   * @param child1 Must be the child of parent and not equal to child2.
   * @param child2 Must be the child of parent and not equal to child1.
   * @return true if child 2 is after child1 in document order.
   */
  private static boolean isNodeAfterSibling(Node parent, Node child1,
                                            Node child2)
  {

    boolean isNodeAfterSibling = false;
    short child1type = child1.getNodeType();
    short child2type = child2.getNodeType();

    if ((Node.ATTRIBUTE_NODE != child1type)
            && (Node.ATTRIBUTE_NODE == child2type))
    {

      // always sort attributes before non-attributes.
      isNodeAfterSibling = false;
    }
    else if ((Node.ATTRIBUTE_NODE == child1type)
             && (Node.ATTRIBUTE_NODE != child2type))
    {

      // always sort attributes before non-attributes.
      isNodeAfterSibling = true;
    }
    else if (Node.ATTRIBUTE_NODE == child1type)
    {
      NamedNodeMap children = parent.getAttributes();
      int nNodes = children.getLength();
      boolean found1 = false, found2 = false;

          // Count from the start until we find one or the other.
      for (int i = 0; i < nNodes; i++)
      {
        Node child = children.item(i);

        if (child1 == child || isNodeTheSame(child1, child))
        {
          if (found2)
          {
            isNodeAfterSibling = false;

            break;
          }

          found1 = true;
        }
        else if (child2 == child || isNodeTheSame(child2, child))
        {
          if (found1)
          {
            isNodeAfterSibling = true;

            break;
          }

          found2 = true;
        }
      }
    }
    else
    {
                // TODO: Check performance of alternate solution:
                // There are two choices here: Count from the start of
                // the document until we find one or the other, or count
                // from one until we find or fail to find the other.
                // Either can wind up scanning all the siblings in the worst
                // case, which on a wide document can be a lot of work but
                // is more typically is a short list.
                // Scanning from the start involves two tests per iteration,
                // but it isn't clear that scanning from the middle doesn't
                // yield more iterations on average.
                // We should run some testcases.
      Node child = parent.getFirstChild();
      boolean found1 = false, found2 = false;

      while (null != child)
      {

        // Node child = children.item(i);
        if (child1 == child || isNodeTheSame(child1, child))
        {
          if (found2)
          {
            isNodeAfterSibling = false;

            break;
          }

          found1 = true;
        }
        else if (child2 == child || isNodeTheSame(child2, child))
        {
          if (found1)
          {
            isNodeAfterSibling = true;

            break;
          }

          found2 = true;
        }

        child = child.getNextSibling();
      }
    }

    return isNodeAfterSibling;
  }  // end isNodeAfterSibling(Node parent, Node child1, Node child2)

  //==========================================================
  // SECTION: Namespace resolution
  //==========================================================

  /**
   * Get the depth level of this node in the tree (equals 1 for
   * a parentless node).
   *
   * <p>
   *  获取树中此节点的深度级别(对于无父节点,等于1)。
   * 
   * 
   * @param n Node to be examined.
   * @return the number of ancestors, plus one
   * @xsl.usage internal
   */
  public short getLevel(Node n)
  {

    short level = 1;

    while (null != (n = getParentOfNode(n)))
    {
      level++;
    }

    return level;
  }

  /**
   * Given an XML Namespace prefix and a context in which the prefix
   * is to be evaluated, return the Namespace Name this prefix was
   * bound to. Note that DOM Level 3 is expected to provide a version of
   * this which deals with the DOM's "early binding" behavior.
   *
   * Default handling:
   *
   * <p>
   *  给定一个XML命名空间前缀和要评估前缀的上下文,返回此前缀绑定到的命名空间名称。注意,DOM Level 3应该提供一个处理DOM的"早期绑定"行为的版本。
   * 
   *  默认处理：
   * 
   * 
   * @param prefix String containing namespace prefix to be resolved,
   * without the ':' which separates it from the localname when used
   * in a Node Name. The empty sting signifies the default namespace
   * at this point in the document.
   * @param namespaceContext Element which provides context for resolution.
   * (We could extend this to work for other nodes by first seeking their
   * nearest Element ancestor.)
   *
   * @return a String containing the Namespace URI which this prefix
   * represents in the specified context.
   */
  public String getNamespaceForPrefix(String prefix, Element namespaceContext)
  {

    int type;
    Node parent = namespaceContext;
    String namespace = null;

    if (prefix.equals("xml"))
    {
      namespace = QName.S_XMLNAMESPACEURI; // Hardcoded, per Namespace spec
    }
        else if(prefix.equals("xmlns"))
    {
          // Hardcoded in the DOM spec, expected to be adopted by
          // Namespace spec. NOTE: Namespace declarations _must_ use
          // the xmlns: prefix; other prefixes declared as belonging
          // to this namespace will not be recognized and should
          // probably be rejected by parsers as erroneous declarations.
      namespace = "http://www.w3.org/2000/xmlns/";
    }
    else
    {
          // Attribute name for this prefix's declaration
          String declname=(prefix=="")
                        ? "xmlns"
                        : "xmlns:"+prefix;

          // Scan until we run out of Elements or have resolved the namespace
      while ((null != parent) && (null == namespace)
             && (((type = parent.getNodeType()) == Node.ELEMENT_NODE)
                 || (type == Node.ENTITY_REFERENCE_NODE)))
      {
        if (type == Node.ELEMENT_NODE)
        {

                        // Look for the appropriate Namespace Declaration attribute,
                        // either "xmlns:prefix" or (if prefix is "") "xmlns".
                        // TODO: This does not handle "implicit declarations"
                        // which may be created when the DOM is edited. DOM Level
                        // 3 will define how those should be interpreted. But
                        // this issue won't arise in freshly-parsed DOMs.

                // NOTE: declname is set earlier, outside the loop.
                        Attr attr=((Element)parent).getAttributeNode(declname);
                        if(attr!=null)
                        {
                namespace = attr.getNodeValue();
                break;
                        }
                }

        parent = getParentOfNode(parent);
      }
    }

    return namespace;
  }

  /**
   * An experiment for the moment.
   * <p>
   *  一个实验的时刻。
   * 
   */
  Hashtable m_NSInfos = new Hashtable();

  /** Object to put into the m_NSInfos table that tells that a node has not been
  /* <p>
  /* 
   *  processed, but has xmlns namespace decls.  */
  protected static final NSInfo m_NSInfoUnProcWithXMLNS = new NSInfo(false,
                                                            true);

  /** Object to put into the m_NSInfos table that tells that a node has not been
  /* <p>
  /* 
   *  processed, but has no xmlns namespace decls.  */
  protected static final NSInfo m_NSInfoUnProcWithoutXMLNS = new NSInfo(false,
                                                               false);

  /** Object to put into the m_NSInfos table that tells that a node has not been
  /* <p>
  /* 
   *  processed, and has no xmlns namespace decls, and has no ancestor decls.  */
  protected static final NSInfo m_NSInfoUnProcNoAncestorXMLNS =
    new NSInfo(false, false, NSInfo.ANCESTORNOXMLNS);

  /** Object to put into the m_NSInfos table that tells that a node has been
  /* <p>
  /* 
   *  processed, and has xmlns namespace decls.  */
  protected static final NSInfo m_NSInfoNullWithXMLNS = new NSInfo(true,
                                                          true);

  /** Object to put into the m_NSInfos table that tells that a node has been
  /* <p>
  /* 
   *  processed, and has no xmlns namespace decls.  */
  protected static final NSInfo m_NSInfoNullWithoutXMLNS = new NSInfo(true,
                                                             false);

  /** Object to put into the m_NSInfos table that tells that a node has been
  /* <p>
  /* 
   *  processed, and has no xmlns namespace decls. and has no ancestor decls.  */
  protected static final NSInfo m_NSInfoNullNoAncestorXMLNS =
    new NSInfo(true, false, NSInfo.ANCESTORNOXMLNS);

  /** Vector of node (odd indexes) and NSInfos (even indexes) that tell if
  /* <p>
  /* 
   *  the given node is a candidate for ancestor namespace processing.  */
  protected Vector m_candidateNoAncestorXMLNS = new Vector();

  /**
   * Returns the namespace of the given node. Differs from simply getting
   * the node's prefix and using getNamespaceForPrefix in that it attempts
   * to cache some of the data in NSINFO objects, to avoid repeated lookup.
   * TODO: Should we consider moving that logic into getNamespaceForPrefix?
   *
   * <p>
   *  返回给定节点的命名空间。不同于简单地获取节点的前缀和使用getNamespaceForPrefix,它试图缓存NSINFO对象中的一些数据,以避免重复查找。
   *  TODO：我们应该考虑将这个逻辑转换为getNamespaceForPrefix吗?。
   * 
   * 
   * @param n Node to be examined.
   *
   * @return String containing the Namespace Name (uri) for this node.
   * Note that this is undefined for any nodes other than Elements and
   * Attributes.
   */
  public String getNamespaceOfNode(Node n)
  {

    String namespaceOfPrefix;
    boolean hasProcessedNS;
    NSInfo nsInfo;
    short ntype = n.getNodeType();

    if (Node.ATTRIBUTE_NODE != ntype)
    {
      Object nsObj = m_NSInfos.get(n);  // return value

      nsInfo = (nsObj == null) ? null : (NSInfo) nsObj;
      hasProcessedNS = (nsInfo == null) ? false : nsInfo.m_hasProcessedNS;
    }
    else
    {
      hasProcessedNS = false;
      nsInfo = null;
    }

    if (hasProcessedNS)
    {
      namespaceOfPrefix = nsInfo.m_namespace;
    }
    else
    {
      namespaceOfPrefix = null;

      String nodeName = n.getNodeName();
      int indexOfNSSep = nodeName.indexOf(':');
      String prefix;

      if (Node.ATTRIBUTE_NODE == ntype)
      {
        if (indexOfNSSep > 0)
        {
          prefix = nodeName.substring(0, indexOfNSSep);
        }
        else
        {

          // Attributes don't use the default namespace, so if
          // there isn't a prefix, we're done.
          return namespaceOfPrefix;
        }
      }
      else
      {
        prefix = (indexOfNSSep >= 0)
                 ? nodeName.substring(0, indexOfNSSep) : "";
      }

      boolean ancestorsHaveXMLNS = false;
      boolean nHasXMLNS = false;

      if (prefix.equals("xml"))
      {
        namespaceOfPrefix = QName.S_XMLNAMESPACEURI;
      }
      else
      {
        int parentType;
        Node parent = n;

        while ((null != parent) && (null == namespaceOfPrefix))
        {
          if ((null != nsInfo)
                  && (nsInfo.m_ancestorHasXMLNSAttrs
                      == NSInfo.ANCESTORNOXMLNS))
          {
            break;
          }

          parentType = parent.getNodeType();

          if ((null == nsInfo) || nsInfo.m_hasXMLNSAttrs)
          {
            boolean elementHasXMLNS = false;

            if (parentType == Node.ELEMENT_NODE)
            {
              NamedNodeMap nnm = parent.getAttributes();

              for (int i = 0; i < nnm.getLength(); i++)
              {
                Node attr = nnm.item(i);
                String aname = attr.getNodeName();

                if (aname.charAt(0) == 'x')
                {
                  boolean isPrefix = aname.startsWith("xmlns:");

                  if (aname.equals("xmlns") || isPrefix)
                  {
                    if (n == parent)
                      nHasXMLNS = true;

                    elementHasXMLNS = true;
                    ancestorsHaveXMLNS = true;

                    String p = isPrefix ? aname.substring(6) : "";

                    if (p.equals(prefix))
                    {
                      namespaceOfPrefix = attr.getNodeValue();

                      break;
                    }
                  }
                }
              }
            }

            if ((Node.ATTRIBUTE_NODE != parentType) && (null == nsInfo)
                    && (n != parent))
            {
              nsInfo = elementHasXMLNS
                       ? m_NSInfoUnProcWithXMLNS : m_NSInfoUnProcWithoutXMLNS;

              m_NSInfos.put(parent, nsInfo);
            }
          }

          if (Node.ATTRIBUTE_NODE == parentType)
          {
            parent = getParentOfNode(parent);
          }
          else
          {
            m_candidateNoAncestorXMLNS.addElement(parent);
            m_candidateNoAncestorXMLNS.addElement(nsInfo);

            parent = parent.getParentNode();
          }

          if (null != parent)
          {
            Object nsObj = m_NSInfos.get(parent);  // return value

            nsInfo = (nsObj == null) ? null : (NSInfo) nsObj;
          }
        }

        int nCandidates = m_candidateNoAncestorXMLNS.size();

        if (nCandidates > 0)
        {
          if ((false == ancestorsHaveXMLNS) && (null == parent))
          {
            for (int i = 0; i < nCandidates; i += 2)
            {
              Object candidateInfo = m_candidateNoAncestorXMLNS.elementAt(i
                                       + 1);

              if (candidateInfo == m_NSInfoUnProcWithoutXMLNS)
              {
                m_NSInfos.put(m_candidateNoAncestorXMLNS.elementAt(i),
                              m_NSInfoUnProcNoAncestorXMLNS);
              }
              else if (candidateInfo == m_NSInfoNullWithoutXMLNS)
              {
                m_NSInfos.put(m_candidateNoAncestorXMLNS.elementAt(i),
                              m_NSInfoNullNoAncestorXMLNS);
              }
            }
          }

          m_candidateNoAncestorXMLNS.removeAllElements();
        }
      }

      if (Node.ATTRIBUTE_NODE != ntype)
      {
        if (null == namespaceOfPrefix)
        {
          if (ancestorsHaveXMLNS)
          {
            if (nHasXMLNS)
              m_NSInfos.put(n, m_NSInfoNullWithXMLNS);
            else
              m_NSInfos.put(n, m_NSInfoNullWithoutXMLNS);
          }
          else
          {
            m_NSInfos.put(n, m_NSInfoNullNoAncestorXMLNS);
          }
        }
        else
        {
          m_NSInfos.put(n, new NSInfo(namespaceOfPrefix, nHasXMLNS));
        }
      }
    }

    return namespaceOfPrefix;
  }

  /**
   * Returns the local name of the given node. If the node's name begins
   * with a namespace prefix, this is the part after the colon; otherwise
   * it's the full node name.
   *
   * <p>
   *  返回给定节点的本地名称。如果节点的名称以命名空间前缀开头,则这是冒号之后的部分;否则它是完整的节点名称。
   * 
   * 
   * @param n the node to be examined.
   *
   * @return String containing the Local Name
   */
  public String getLocalNameOfNode(Node n)
  {

    String qname = n.getNodeName();
    int index = qname.indexOf(':');

    return (index < 0) ? qname : qname.substring(index + 1);
  }

  /**
   * Returns the element name with the namespace prefix (if any) replaced
   * by the Namespace URI it was bound to. This is not a standard
   * representation of a node name, but it allows convenient
   * single-string comparison of the "universal" names of two nodes.
   *
   * <p>
   *  返回具有命名空间前缀(如果有)的元素名称,该名称由绑定到的命名空间URI替换。这不是节点名称的标准表示,但它允许方便的单字符串比较两个节点的"通用"名称。
   * 
   * 
   * @param elem Element to be examined.
   *
   * @return String in the form "namespaceURI:localname" if the node
   * belongs to a namespace, or simply "localname" if it doesn't.
   * @see #getExpandedAttributeName
   */
  public String getExpandedElementName(Element elem)
  {

    String namespace = getNamespaceOfNode(elem);

    return (null != namespace)
           ? namespace + ":" + getLocalNameOfNode(elem)
           : getLocalNameOfNode(elem);
  }

  /**
   * Returns the attribute name with the namespace prefix (if any) replaced
   * by the Namespace URI it was bound to. This is not a standard
   * representation of a node name, but it allows convenient
   * single-string comparison of the "universal" names of two nodes.
   *
   * <p>
   * 返回带有命名空间前缀(如果有)的属性名称,该名称空间前缀由其绑定的命名空间URI替换。这不是节点名称的标准表示,但它允许方便的单字符串比较两个节点的"通用"名称。
   * 
   * 
   * @param attr Attr to be examined
   *
   * @return String in the form "namespaceURI:localname" if the node
   * belongs to a namespace, or simply "localname" if it doesn't.
   * @see #getExpandedElementName
   */
  public String getExpandedAttributeName(Attr attr)
  {

    String namespace = getNamespaceOfNode(attr);

    return (null != namespace)
           ? namespace + ":" + getLocalNameOfNode(attr)
           : getLocalNameOfNode(attr);
  }

  //==========================================================
  // SECTION: DOM Helper Functions
  //==========================================================

  /**
   * Tell if the node is ignorable whitespace. Note that this can
   * be determined only in the context of a DTD or other Schema,
   * and that DOM Level 2 has nostandardized DOM API which can
   * return that information.
   * <p>
   *  告诉节点是否是可忽略的空格。注意,这只能在DTD或其他模式的上下文中确定,并且DOM级别2没有可以返回该信息的标准化DOM API。
   * 
   * 
   * @deprecated
   *
   * @param node Node to be examined
   *
   * @return CURRENTLY HARDCODED TO FALSE, but should return true if
   * and only if the node is of type Text, contains only whitespace,
   * and does not appear as part of the #PCDATA content of an element.
   * (Note that determining this last may require allowing for
   * Entity References.)
   */
  public boolean isIgnorableWhitespace(Text node)
  {

    boolean isIgnorable = false;  // return value

    // TODO: I can probably do something to figure out if this
    // space is ignorable from just the information in
    // the DOM tree.
        // -- You need to be able to distinguish whitespace
        // that is #PCDATA from whitespace that isn't.  That requires
        // DTD support, which won't be standardized until DOM Level 3.
    return isIgnorable;
  }

  /**
   * Get the first unparented node in the ancestor chain.
   * <p>
   *  获取祖先链中的第一个非父对象节点。
   * 
   * 
   * @deprecated
   *
   * @param node Starting node, to specify which chain to chase
   *
   * @return the topmost ancestor.
   */
  public Node getRoot(Node node)
  {

    Node root = null;

    while (node != null)
    {
      root = node;
      node = getParentOfNode(node);
    }

    return root;
  }

  /**
   * Get the root node of the document tree, regardless of
   * whether or not the node passed in is a document node.
   * <p>
   * TODO: This doesn't handle DocumentFragments or "orphaned" subtrees
   * -- it's currently returning ownerDocument even when the tree is
   * not actually part of the main Document tree. We should either
   * rewrite the description to say that it finds the Document node,
   * or change the code to walk up the ancestor chain.

   *
   * <p>
   *  获取文档树的根节点,而不管传入的节点是否是文档节点。
   * <p>
   *  TODO：这不处理DocumentFragments或"孤立"子树 - 它目前返回ownerDocument,即使树实际上不是主要的Document树的一部分。
   * 我们应该重写描述以说明它找到Document节点,或者更改代码以遍历祖先链。
   * 
   * 
   * @param n Node to be examined
   *
   * @return the Document node. Note that this is not the correct answer
   * if n was (or was a child of) a DocumentFragment or an orphaned node,
   * as can arise if the DOM has been edited rather than being generated
   * by a parser.
   */
  public Node getRootNode(Node n)
  {
    int nt = n.getNodeType();
    return ( (Node.DOCUMENT_NODE == nt) || (Node.DOCUMENT_FRAGMENT_NODE == nt) )
           ? n : n.getOwnerDocument();
  }

  /**
   * Test whether the given node is a namespace decl node. In DOM Level 2
   * this can be done in a namespace-aware manner, but in Level 1 DOMs
   * it has to be done by testing the node name.
   *
   * <p>
   *  测试给定的节点是否是命名空间decl节点。在DOM Level 2中,这可以以命名空间感知的方式完成,但在Level 1 DOM中,必须通过测试节点名称来完成。
   * 
   * 
   * @param n Node to be examined.
   *
   * @return boolean -- true iff the node is an Attr whose name is
   * "xmlns" or has the "xmlns:" prefix.
   */
  public boolean isNamespaceNode(Node n)
  {

    if (Node.ATTRIBUTE_NODE == n.getNodeType())
    {
      String attrName = n.getNodeName();

      return (attrName.startsWith("xmlns:") || attrName.equals("xmlns"));
    }

    return false;
  }

  /**
   * Obtain the XPath-model parent of a DOM node -- ownerElement for Attrs,
   * parent for other nodes.
   * <p>
   * Background: The DOM believes that you must be your Parent's
   * Child, and thus Attrs don't have parents. XPath said that Attrs
   * do have their owning Element as their parent. This function
   * bridges the difference, either by using the DOM Level 2 ownerElement
   * function or by using a "silly and expensive function" in Level 1
   * DOMs.
   * <p>
   * (There's some discussion of future DOMs generalizing ownerElement
   * into ownerNode and making it work on all types of nodes. This
   * still wouldn't help the users of Level 1 or Level 2 DOMs)
   * <p>
   *
   * <p>
   *  获取DOM节点的XPath模型父对象 -  Attrs的ownerElement,其他节点的父对象。
   * <p>
   * 背景：DOM认为你必须是你的父母的孩子,因此Attrs没有父母。 XPath说Attrs确实有他们自己的Element作为他们的父。
   * 此函数通过使用DOM Level 2 ownerElement函数或在第1级DOM中使用"愚蠢和昂贵的函数"来桥接差异。
   * <p>
   *  (有一些关于未来DOM的讨论,将ownerElement泛化成ownerNode并使其在所有类型的节点上工作,这仍然不能帮助1级或2级DOM的用户)
   * <p>
   * 
   * 
   * @param node Node whose XPath parent we want to obtain
   *
   * @return the parent of the node, or the ownerElement if it's an
   * Attr node, or null if the node is an orphan.
   *
   * @throws RuntimeException if the Document has no root element.
   * This can't arise if the Document was created
   * via the DOM Level 2 factory methods, but is possible if other
   * mechanisms were used to obtain it
   */
  public static Node getParentOfNode(Node node) throws RuntimeException
  {
    Node parent;
    short nodeType = node.getNodeType();

    if (Node.ATTRIBUTE_NODE == nodeType)
    {
      Document doc = node.getOwnerDocument();
          /*
      TBD:
      if(null == doc)
      {
        throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_CHILD_HAS_NO_OWNER_DOCUMENT, null));//"Attribute child does not have an owner document!");
      }
          /* <p>
          /*  TBD：if(null == doc){throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_CHILD_HAS_NO_OWNER_DOCUMENT,null)); //"属性子项没有所有者文档！ }
          /* }。
          /* 
      */

          // Given how expensive the tree walk may be, we should first ask
          // whether this DOM can answer the question for us. The additional
          // test does slow down Level 1 DOMs slightly. DOMHelper2, which
          // is currently specialized for Xerces, assumes it can use the
          // Level 2 solution. We might want to have an intermediate stage,
          // which would assume DOM Level 2 but not assume Xerces.
          //
          // (Shouldn't have to check whether impl is null in a compliant DOM,
          // but let's be paranoid for a moment...)
          DOMImplementation impl=doc.getImplementation();
          if(impl!=null && impl.hasFeature("Core","2.0"))
          {
                  parent=((Attr)node).getOwnerElement();
                  return parent;
          }

          // DOM Level 1 solution, as fallback. Hugely expensive.

      Element rootElem = doc.getDocumentElement();

      if (null == rootElem)
      {
        throw new RuntimeException(
          XMLMessages.createXMLMessage(
            XMLErrorResources.ER_CHILD_HAS_NO_OWNER_DOCUMENT_ELEMENT,
            null));  //"Attribute child does not have an owner document element!");
      }

      parent = locateAttrParent(rootElem, node);

        }
    else
    {
      parent = node.getParentNode();

      // if((Node.DOCUMENT_NODE != nodeType) && (null == parent))
      // {
      //   throw new RuntimeException("Child does not have parent!");
      // }
    }

    return parent;
  }

  /**
   * Given an ID, return the element. This can work only if the document
   * is interpreted in the context of a DTD or Schema, since otherwise
   * we don't know which attributes are or aren't IDs.
   * <p>
   * Note that DOM Level 1 had no ability to retrieve this information.
   * DOM Level 2 introduced it but does not promise that it will be
   * supported in all DOMs; those which can't support it will always
   * return null.
   * <p>
   * TODO: getElementByID is currently unimplemented. Support DOM Level 2?
   *
   * <p>
   *  给定一个ID,返回该元素。这只有在文档在DTD或Schema的上下文中解释时才能工作,否则我们不知道哪些属性是ID或不是ID。
   * <p>
   *  请注意,DOM级别1无法检索此信息。 DOM级别2介绍了它,但不承诺它将在所有DOM支持;那些不能支持它的将总是返回null。
   * <p>
   *  TODO：getElementByID当前未实现。支持DOM 2级?
   * 
   * 
   * @param id The unique identifier to be searched for.
   * @param doc The document to search within.
   * @return CURRENTLY HARDCODED TO NULL, but it should be:
   * The node which has this unique identifier, or null if there
   * is no such node or this DOM can't reliably recognize it.
   */
  public Element getElementByID(String id, Document doc)
  {
    return null;
  }

  /**
   * The getUnparsedEntityURI function returns the URI of the unparsed
   * entity with the specified name in the same document as the context
   * node (see [3.3 Unparsed Entities]). It returns the empty string if
   * there is no such entity.
   * <p>
   * XML processors may choose to use the System Identifier (if one
   * is provided) to resolve the entity, rather than the URI in the
   * Public Identifier. The details are dependent on the processor, and
   * we would have to support some form of plug-in resolver to handle
   * this properly. Currently, we simply return the System Identifier if
   * present, and hope that it a usable URI or that our caller can
   * map it to one.
   * TODO: Resolve Public Identifiers... or consider changing function name.
   * <p>
   * If we find a relative URI
   * reference, XML expects it to be resolved in terms of the base URI
   * of the document. The DOM doesn't do that for us, and it isn't
   * entirely clear whether that should be done here; currently that's
   * pushed up to a higher levelof our application. (Note that DOM Level
   * 1 didn't store the document's base URI.)
   * TODO: Consider resolving Relative URIs.
   * <p>
   * (The DOM's statement that "An XML processor may choose to
   * completely expand entities before the structure model is passed
   * to the DOM" refers only to parsed entities, not unparsed, and hence
   * doesn't affect this function.)
   *
   * <p>
   *  getUnparsedEntityURI函数返回在与上下文节点相同的文档中具有指定名称的未解析实体的URI(参见[3.3 Unparsed Entities])。如果没有这样的实体,它返回空字符串。
   * <p>
   * XML处理器可以选择使用系统标识符(如果提供了一个)来解析实体,而不是公共标识符中的URI。细节取决于处理器,我们将不得不支持某种形式的插件解析器来正确处理这些。
   * 目前,我们只返回系统标识符(如果存在),并希望它是一个可用的URI,或者我们的调用者可以将它映射到一个。 TODO：Resolve Public Identifiers ...或考虑更改函数名称。
   * <p>
   *  如果我们找到一个相对URI引用,XML期望它根据文档的基本URI解析。 DOM不为我们这样做,并不完全清楚是否应该在这里做;目前已经推高了我们的应用程序。
   *  (请注意,DOM级别1不存储文档的基本URI。)TODO：考虑解析相对URI。
   * <p>
   *  (DOM的声明"一个XML处理器可能选择在结构模型被传递给DOM之前完全展开实体"仅指解析的实体,而不是解析的,因此不影响这个函数)。
   * 
   * 
   * @param name A string containing the Entity Name of the unparsed
   * entity.
   * @param doc Document node for the document to be searched.
   *
   * @return String containing the URI of the Unparsed Entity, or an
   * empty string if no such entity exists.
   */
  public String getUnparsedEntityURI(String name, Document doc)
  {

    String url = "";
    DocumentType doctype = doc.getDoctype();

    if (null != doctype)
    {
      NamedNodeMap entities = doctype.getEntities();
      if(null == entities)
        return url;
      Entity entity = (Entity) entities.getNamedItem(name);
      if(null == entity)
        return url;

      String notationName = entity.getNotationName();

      if (null != notationName)  // then it's unparsed
      {
        // The draft says: "The XSLT processor may use the public
        // identifier to generate a URI for the entity instead of the URI
        // specified in the system identifier. If the XSLT processor does
        // not use the public identifier to generate the URI, it must use
        // the system identifier; if the system identifier is a relative
        // URI, it must be resolved into an absolute URI using the URI of
        // the resource containing the entity declaration as the base
        // URI [RFC2396]."
        // So I'm falling a bit short here.
        url = entity.getSystemId();

        if (null == url)
        {
          url = entity.getPublicId();
        }
        else
        {
          // This should be resolved to an absolute URL, but that's hard
          // to do from here.
        }
      }
    }

    return url;
  }

  /**
   * Support for getParentOfNode; walks a DOM tree until it finds
   * the Element which owns the Attr. This is hugely expensive, and
   * if at all possible you should use the DOM Level 2 Attr.ownerElement()
   * method instead.
   *  <p>
   * The DOM Level 1 developers expected that folks would keep track
   * of the last Element they'd seen and could recover the info from
   * that source. Obviously that doesn't work very well if the only
   * information you've been presented with is the Attr. The DOM Level 2
   * getOwnerElement() method fixes that, but only for Level 2 and
   * later DOMs.
   *
   * <p>
   *  支持getParentOfNode;遍历DOM树,直到找到拥有Attr的Element。这是非常昂贵的,如果可能的话,你应该使用DOM Level 2 Attr.ownerElement()方法。
   * <p>
   * DOM 1级开发人员希望人们记住他们看到的最后一个元素,并且可以从该源中恢复信息。显然,如果你提供的唯一信息是Attr不工作的很好。
   *  DOM Level 2的getOwnerElement()方法修复了这一问题,但仅适用于2级和更高版本的DOM。
   * 
   * 
   * @param elem Element whose subtree is to be searched for this Attr
   * @param attr Attr whose owner is to be located.
   *
   * @return the first Element whose attribute list includes the provided
   * attr. In modern DOMs, this will also be the only such Element. (Early
   * DOMs had some hope that Attrs might be sharable, but this idea has
   * been abandoned.)
   */
  private static Node locateAttrParent(Element elem, Node attr)
  {

    Node parent = null;

        // This should only be called for Level 1 DOMs, so we don't have to
        // worry about namespace issues. In later levels, it's possible
        // for a DOM to have two Attrs with the same NodeName but
        // different namespaces, and we'd need to get getAttributeNodeNS...
        // but later levels also have Attr.getOwnerElement.
        Attr check=elem.getAttributeNode(attr.getNodeName());
        if(check==attr)
                parent = elem;

    if (null == parent)
    {
      for (Node node = elem.getFirstChild(); null != node;
              node = node.getNextSibling())
      {
        if (Node.ELEMENT_NODE == node.getNodeType())
        {
          parent = locateAttrParent((Element) node, attr);

          if (null != parent)
            break;
        }
      }
    }

    return parent;
  }

  /**
   * The factory object used for creating nodes
   * in the result tree.
   * <p>
   *  用于在结果树中创建节点的工厂对象。
   * 
   */
  protected Document m_DOMFactory = null;

  /**
   * Store the factory object required to create DOM nodes
   * in the result tree. In fact, that's just the result tree's
   * Document node...
   *
   * <p>
   *  存储在结果树中创建DOM节点所需的工厂对象。事实上,这只是结果树的Document节点...
   * 
   * 
   * @param domFactory The DOM Document Node within whose context
   * the result tree will be built.
   */
  public void setDOMFactory(Document domFactory)
  {
    this.m_DOMFactory = domFactory;
  }

  /**
   * Retrieve the factory object required to create DOM nodes
   * in the result tree.
   *
   * <p>
   *  检索在结果树中创建DOM节点所需的工厂对象。
   * 
   * 
   * @return The result tree's DOM Document Node.
   */
  public Document getDOMFactory()
  {

    if (null == this.m_DOMFactory)
    {
      this.m_DOMFactory = createDocument();
    }

    return this.m_DOMFactory;
  }

  /**
   * Get the textual contents of the node. See
   * getNodeData(Node,FastStringBuffer) for discussion of how
   * whitespace nodes are handled.
   *
   * <p>
   *  获取节点的文本内容。有关如何处理空白节点的讨论,请参阅getNodeData(Node,FastStringBuffer)。
   * 
   * 
   * @param node DOM Node to be examined
   * @return String containing a concatenation of all the
   * textual content within that node.
   * @see #getNodeData(Node,FastStringBuffer)
   *
   */
  public static String getNodeData(Node node)
  {

    FastStringBuffer buf = StringBufferPool.get();
    String s;

    try
    {
      getNodeData(node, buf);

      s = (buf.length() > 0) ? buf.toString() : "";
    }
    finally
    {
      StringBufferPool.free(buf);
    }

    return s;
  }

  /**
   * Retrieve the text content of a DOM subtree, appending it into a
   * user-supplied FastStringBuffer object. Note that attributes are
   * not considered part of the content of an element.
   * <p>
   * There are open questions regarding whitespace stripping.
   * Currently we make no special effort in that regard, since the standard
   * DOM doesn't yet provide DTD-based information to distinguish
   * whitespace-in-element-context from genuine #PCDATA. Note that we
   * should probably also consider xml:space if/when we address this.
   * DOM Level 3 may solve the problem for us.
   *
   * <p>
   *  检索DOM子树的文本内容,将其附加到用户提供的FastStringBuffer对象中。请注意,属性不被视为元素内容的一部分。
   * <p>
   *  有关于空白剥离的开放性问题。目前,我们在这方面没有做特别的努力,因为标准DOM还没有提供基于DTD的信息来区分元素上下文和真正的#PCDATA。
   * 
   * @param node Node whose subtree is to be walked, gathering the
   * contents of all Text or CDATASection nodes.
   * @param buf FastStringBuffer into which the contents of the text
   * nodes are to be concatenated.
   */
  public static void getNodeData(Node node, FastStringBuffer buf)
  {

    switch (node.getNodeType())
    {
    case Node.DOCUMENT_FRAGMENT_NODE :
    case Node.DOCUMENT_NODE :
    case Node.ELEMENT_NODE :
    {
      for (Node child = node.getFirstChild(); null != child;
              child = child.getNextSibling())
      {
        getNodeData(child, buf);
      }
    }
    break;
    case Node.TEXT_NODE :
    case Node.CDATA_SECTION_NODE :
      buf.append(node.getNodeValue());
      break;
    case Node.ATTRIBUTE_NODE :
      buf.append(node.getNodeValue());
      break;
    case Node.PROCESSING_INSTRUCTION_NODE :
      // warning(XPATHErrorResources.WG_PARSING_AND_PREPARING);
      break;
    default :
      // ignore
      break;
    }
  }
}
