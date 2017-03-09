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
 * $Id: DOM2Helper.java,v 1.2.4.1 2005/09/15 08:15:37 suresh_emailid Exp $
 * <p>
 *  $ Id：DOM2Helper.java,v 1.2.4.1 2005/09/15 08:15:37 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.InputSource;

/**
/* <p>
/* 
 * @deprecated Since the introduction of the DTM, this class will be removed.
 * This class provides a DOM level 2 "helper", which provides services currently
 * not provided be the DOM standard.
 */
public class DOM2Helper extends DOMHelper
{

  /**
   * Construct an instance.
   * <p>
   *  构造一个实例。
   * 
   */
  public DOM2Helper(){}

  /**
   * Check node to see if it was created by a DOM implementation
   * that this helper is intended to support. This is currently
   * disabled, and assumes all nodes are acceptable rather than checking
   * that they implement com.sun.org.apache.xerces.internal.dom.NodeImpl.
   *
   * <p>
   *  检查节点以查看它是否由此辅助程序旨在支持的DOM实施创建。
   * 这当前被禁用,并假定所有节点都是可以接受的,而不是检查它们是否实现com.sun.org.apache.xerces.internal.dom.NodeImpl。
   * 
   * 
   * @param node The node to be tested.
   *
   * @throws TransformerException if the node is not one which this
   * DOM2Helper can support. If we return without throwing the exception,
   * the node is compatable.
   * @xsl.usage internal
   */
  public void checkNode(Node node) throws TransformerException
  {

    // if(!(node instanceof com.sun.org.apache.xerces.internal.dom.NodeImpl))
    //  throw new TransformerException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_XERCES_CANNOT_HANDLE_NODES, new Object[]{((Object)node).getClass()})); //"DOM2Helper can not handle nodes of type"
    //+((Object)node).getClass());
  }

  /**
   * Returns true if the DOM implementation handled by this helper
   * supports the SAX ContentHandler interface.
   *
   * <p>
   *  如果此辅助程序处理的DOM实施支持SAX ContentHandler接口,则返回true。
   * 
   * 
   * @return true (since Xerces does).
   */
  public boolean supportsSAX()
  {
    return true;
  }

  /** Field m_doc: Document Node for the document this helper is currently
   * accessing or building
   * <p>
   *  访问或建设
   * 
   * 
   * @see #setDocument
   * @see #getDocument
   *  */
  private Document m_doc;

  /**
   * Specify which document this helper is currently operating on.
   *
   * <p>
   *  指定此助手当前正在操作的文档。
   * 
   * 
   * @param doc The DOM Document node for this document.
   * @see #getDocument
   */
  public void setDocument(Document doc)
  {
    m_doc = doc;
  }

  /**
   * Query which document this helper is currently operating on.
   *
   * <p>
   *  查询此助手当前正在操作的文档。
   * 
   * 
   * @return The DOM Document node for this document.
   * @see #setDocument
   */
  public Document getDocument()
  {
    return m_doc;
  }

  /**
   * Parse an XML document.
   *
   * <p>Right now the Xerces DOMParser class is used.  This needs
   * fixing, either via jaxp, or via some other, standard method.</p>
   *
   * <p>The application can use this method to instruct the SAX parser
   * to begin parsing an XML document from any valid input
   * source (a character stream, a byte stream, or a URI).</p>
   *
   * <p>Applications may not invoke this method while a parse is in
   * progress (they should create a new Parser instead for each
   * additional XML document).  Once a parse is complete, an
   * application may reuse the same Parser object, possibly with a
   * different input source.</p>
   *
   * <p>
   *  解析XML文档。
   * 
   *  <p>现在使用Xerces DOMParser类。这需要通过jaxp或其他一些标准方法进行修复。</p>
   * 
   * <p>应用程序可以使用此方法指示SAX解析器开始从任何有效的输入源(字符流,字节流或URI)解析XML文档。</p>
   * 
   *  <p>在解析正在进行时,应用程序不能调用此方法(它们应为每个其他XML文档创建一个新的解析器)。一旦解析完成,应用程序可以重用相同的解析器对象,可能使用不同的输入源。</p>
   * 
   * 
   * @param source The input source for the top-level of the
   *        XML document.
   *
   * @throws TransformerException if any checked exception is thrown.
   * @xsl.usage internal
   */
  public void parse(InputSource source) throws TransformerException
  {

    try
    {

      // I guess I should use JAXP factory here... when it's legal.
      // com.sun.org.apache.xerces.internal.parsers.DOMParser parser
      //  = new com.sun.org.apache.xerces.internal.parsers.DOMParser();
      DocumentBuilderFactory builderFactory =
        DocumentBuilderFactory.newInstance();

      builderFactory.setNamespaceAware(true);
      builderFactory.setValidating(true);

      DocumentBuilder parser = builderFactory.newDocumentBuilder();

      /*
      // domParser.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", getShouldExpandEntityRefs()? false : true);
      if(m_useDOM2getNamespaceURI)
      {
      parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", true);
      parser.setFeature("http://xml.org/sax/features/namespaces", true);
      }
      else
      {
      parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
      }

      parser.setFeature("http://apache.org/xml/features/allow-java-encodings", true);
      /* <p>
      /*  // domParser.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes",getShouldExpand
      /* EntityRefs()?false：true); if(m_useDOM2getNamespaceURI){parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion",true); parser.setFeature("http://xml.org/sax/features/namespaces",true); }
      /*  else {parser.setFeature("http://apache.org/xml/features/dom/defer-node-expansion",false); }}。
      /* 
      /*  parser.setFeature("http://apache.org/xml/features/allow-java-encodings",true);
      /* 
      */

      parser.setErrorHandler(
        new com.sun.org.apache.xml.internal.utils.DefaultErrorHandler());

      // if(null != m_entityResolver)
      // {
      // System.out.println("Setting the entity resolver.");
      //  parser.setEntityResolver(m_entityResolver);
      // }
      setDocument(parser.parse(source));
    }
    catch (org.xml.sax.SAXException se)
    {
      throw new TransformerException(se);
    }
    catch (ParserConfigurationException pce)
    {
      throw new TransformerException(pce);
    }
    catch (IOException ioe)
    {
      throw new TransformerException(ioe);
    }

    // setDocument(((com.sun.org.apache.xerces.internal.parsers.DOMParser)parser).getDocument());
  }

  /**
   * Given an XML ID, return the element. This requires assistance from the
   * DOM and parser, and is meaningful only in the context of a DTD
   * or schema which declares attributes as being of type ID. This
   * information may or may not be available in all parsers, may or
   * may not be available for specific documents, and may or may not
   * be available when validation is not turned on.
   *
   * <p>
   *  给定一个XML ID,返回该元素。这需要DOM和解析器的帮助,并且仅在将属性声明为ID类型的DTD或模式的上下文中才有意义。
   * 此信息可能或可能不在所有解析器中可用,可能或不可用于特定文档,并且可能或不可用,当验证未打开时。
   * 
   * 
   * @param id The ID to search for, as a String.
   * @param doc The document to search within, as a DOM Document node.
   * @return DOM Element node with an attribute of type ID whose value
   * uniquely matches the requested id string, or null if there isn't
   * such an element or if the DOM can't answer the question for other
   * reasons.
   */
  public Element getElementByID(String id, Document doc)
  {
    return doc.getElementById(id);
  }

  /**
   * Figure out whether node2 should be considered as being later
   * in the document than node1, in Document Order as defined
   * by the XPath model. This may not agree with the ordering defined
   * by other XML applications.
   * <p>
   * There are some cases where ordering isn't defined, and neither are
   * the results of this function -- though we'll generally return true.
   * <p>
   * TODO: Make sure this does the right thing with attribute nodes!!!
   *
   * <p>
   * 确定在XPath模型中定义的文档顺序中node2是否应该被认为是文档的后面,而不是node1。这可能与其他XML应用程序定义的顺序不一致。
   * <p>
   *  有些情况下,没有定义顺序,并且这个函数的结果也没有定义,尽管我们通常返回true。
   * <p>
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

    // Assume first that the nodes are DTM nodes, since discovering node
    // order is massivly faster for the DTM.
    if(node1 instanceof DOMOrder && node2 instanceof DOMOrder)
    {
      int index1 = ((DOMOrder) node1).getUid();
      int index2 = ((DOMOrder) node2).getUid();

      return index1 <= index2;
    }
    else
    {

      // isNodeAfter will return true if node is after countedNode
      // in document order. The base isNodeAfter is sloooow (relatively).
      return DOMHelper.isNodeAfter(node1, node2);
    }
  }

  /**
   * Get the XPath-model parent of a node.  This version takes advantage
   * of the DOM Level 2 Attr.ownerElement() method; the base version we
   * would otherwise inherit is prepared to fall back on exhaustively
   * walking the document to find an Attr's parent.
   *
   * <p>
   *  获取节点的XPath模型父代。此版本利用DOM Level 2 Attr.ownerElement()方法;我们将继承的基本版本准备回退到彻底地走文档以找到一个Attr的父级。
   * 
   * 
   * @param node Node to be examined
   *
   * @return the DOM parent of the input node, if there is one, or the
   * ownerElement if the input node is an Attr, or null if the node is
   * a Document, a DocumentFragment, or an orphan.
   */
  public static Node getParentOfNode(Node node)
  {
          Node parent=node.getParentNode();
          if(parent==null && (Node.ATTRIBUTE_NODE == node.getNodeType()) )
           parent=((Attr) node).getOwnerElement();
          return parent;
  }

  /**
   * Returns the local name of the given node, as defined by the
   * XML Namespaces specification. This is prepared to handle documents
   * built using DOM Level 1 methods by falling back upon explicitly
   * parsing the node name.
   *
   * <p>
   *  返回给定节点的本地名称,由XML命名空间规范定义。这是准备处理使用DOM 1级方法构建的文档,通过回退显式解析节点名称。
   * 
   * 
   * @param n Node to be examined
   *
   * @return String containing the local name, or null if the node
   * was not assigned a Namespace.
   */
  public String getLocalNameOfNode(Node n)
  {

    String name = n.getLocalName();

    return (null == name) ? super.getLocalNameOfNode(n) : name;
  }

  /**
   * Returns the Namespace Name (Namespace URI) for the given node.
   * In a Level 2 DOM, you can ask the node itself. Note, however, that
   * doing so conflicts with our decision in getLocalNameOfNode not
   * to trust the that the DOM was indeed created using the Level 2
   * methods. If Level 1 methods were used, these two functions will
   * disagree with each other.
   * <p>
   * TODO: Reconcile with getLocalNameOfNode.
   *
   * <p>
   *  返回给定节点的命名空间名称(命名空间URI)。在Level 2 DOM中,您可以询问节点本身。
   * 但请注意,这样做与我们在getLocalNameOfNode中的决定不相信DOM确实是使用Level 2方法创建的。如果使用1级方法,这两个功能将彼此不同意。
   * <p>
   *  TODO：与getLocalNameOfNode进行协调。
   * 
   * @param n Node to be examined
   *
   * @return String containing the Namespace URI bound to this DOM node
   * at the time the Node was created.
   */
  public String getNamespaceOfNode(Node n)
  {
    return n.getNamespaceURI();
  }

  /** Field m_useDOM2getNamespaceURI is a compile-time flag which
   *  gates some of the parser options used to build a DOM -- but
   * that code is commented out at this time and nobody else
   * <p>
   * 
   * 
   * references it, so I've commented this out as well. */
  //private boolean m_useDOM2getNamespaceURI = false;
}
