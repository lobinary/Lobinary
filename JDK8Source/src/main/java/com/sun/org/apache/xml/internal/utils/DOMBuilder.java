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
 * $Id: DOMBuilder.java,v 1.2.4.1 2005/09/15 08:15:39 suresh_emailid Exp $
 * <p>
 *  $ Id：DOMBuilder.java,v 1.2.4.1 2005/09/15 08:15:39 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.util.Stack;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.CDATASection;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.ext.LexicalHandler;
/**
 * This class takes SAX events (in addition to some extra events
 * that SAX doesn't handle yet) and adds the result to a document
 * or document fragment.
 * @xsl.usage general
 * <p>
 *  此类接受SAX事件(除了一些额外的事件,SAX不处理),并将结果添加到文档或文档片段。 @ xsl.usage general
 * 
 */
public class DOMBuilder
        implements ContentHandler, LexicalHandler
{

  /** Root document          */
  public Document m_doc;

  /** Current node           */
  protected Node m_currentNode = null;

  /** The root node          */
  protected Node m_root = null;

  /** The next sibling node  */
  protected Node m_nextSibling = null;

  /** First node of document fragment or null if not a DocumentFragment     */
  public DocumentFragment m_docFrag = null;

  /** Vector of element nodes          */
  protected Stack m_elemStack = new Stack();

  /**
   * DOMBuilder instance constructor... it will add the DOM nodes
   * to the document fragment.
   *
   * <p>
   *  DOMBuilder实例构造函数...它会将DOM节点添加到文档片段。
   * 
   * 
   * @param doc Root document
   * @param node Current node
   */
  public DOMBuilder(Document doc, Node node)
  {
    m_doc = doc;
    m_currentNode = m_root = node;

    if (node instanceof Element)
      m_elemStack.push(node);
  }

  /**
   * DOMBuilder instance constructor... it will add the DOM nodes
   * to the document fragment.
   *
   * <p>
   *  DOMBuilder实例构造函数...它会将DOM节点添加到文档片段。
   * 
   * 
   * @param doc Root document
   * @param docFrag Document fragment
   */
  public DOMBuilder(Document doc, DocumentFragment docFrag)
  {
    m_doc = doc;
    m_docFrag = docFrag;
  }

  /**
   * DOMBuilder instance constructor... it will add the DOM nodes
   * to the document.
   *
   * <p>
   *  DOMBuilder实例构造函数...它会将DOM节点添加到文档中。
   * 
   * 
   * @param doc Root document
   */
  public DOMBuilder(Document doc)
  {
    m_doc = doc;
  }

  /**
   * Get the root document or DocumentFragment of the DOM being created.
   *
   * <p>
   *  获取正在创建的DOM的根文档或DocumentFragment。
   * 
   * 
   * @return The root document or document fragment if not null
   */
  public Node getRootDocument()
  {
    return (null != m_docFrag) ? (Node) m_docFrag : (Node) m_doc;
  }

  /**
   * Get the root node of the DOM tree.
   * <p>
   *  获取DOM树的根节点。
   * 
   */
  public Node getRootNode()
  {
    return m_root;
  }

  /**
   * Get the node currently being processed.
   *
   * <p>
   *  获取当前正在处理的节点。
   * 
   * 
   * @return the current node being processed
   */
  public Node getCurrentNode()
  {
    return m_currentNode;
  }

  /**
   * Set the next sibling node, which is where the result nodes
   * should be inserted before.
   *
   * <p>
   *  设置下一个兄弟节点,这是之前应该插入结果节点的地方。
   * 
   * 
   * @param nextSibling the next sibling node.
   */
  public void setNextSibling(Node nextSibling)
  {
    m_nextSibling = nextSibling;
  }

  /**
   * Return the next sibling node.
   *
   * <p>
   *  返回下一个兄弟节点。
   * 
   * 
   * @return the next sibling node.
   */
  public Node getNextSibling()
  {
    return m_nextSibling;
  }

  /**
   * Return null since there is no Writer for this class.
   *
   * <p>
   * 返回null,因为这个类没有Writer。
   * 
   * 
   * @return null
   */
  public java.io.Writer getWriter()
  {
    return null;
  }

  /**
   * Append a node to the current container.
   *
   * <p>
   *  将节点附加到当前容器。
   * 
   * 
   * @param newNode New node to append
   */
  protected void append(Node newNode) throws org.xml.sax.SAXException
  {

    Node currentNode = m_currentNode;

    if (null != currentNode)
    {
      if (currentNode == m_root && m_nextSibling != null)
        currentNode.insertBefore(newNode, m_nextSibling);
      else
        currentNode.appendChild(newNode);

      // System.out.println(newNode.getNodeName());
    }
    else if (null != m_docFrag)
    {
      if (m_nextSibling != null)
        m_docFrag.insertBefore(newNode, m_nextSibling);
      else
        m_docFrag.appendChild(newNode);
    }
    else
    {
      boolean ok = true;
      short type = newNode.getNodeType();

      if (type == Node.TEXT_NODE)
      {
        String data = newNode.getNodeValue();

        if ((null != data) && (data.trim().length() > 0))
        {
          throw new org.xml.sax.SAXException(
            XMLMessages.createXMLMessage(
              XMLErrorResources.ER_CANT_OUTPUT_TEXT_BEFORE_DOC, null));  //"Warning: can't output text before document element!  Ignoring...");
        }

        ok = false;
      }
      else if (type == Node.ELEMENT_NODE)
      {
        if (m_doc.getDocumentElement() != null)
        {
          ok = false;

          throw new org.xml.sax.SAXException(
            XMLMessages.createXMLMessage(
              XMLErrorResources.ER_CANT_HAVE_MORE_THAN_ONE_ROOT, null));  //"Can't have more than one root on a DOM!");
        }
      }

      if (ok)
      {
        if (m_nextSibling != null)
          m_doc.insertBefore(newNode, m_nextSibling);
        else
          m_doc.appendChild(newNode);
      }
    }
  }

  /**
   * Receive an object for locating the origin of SAX document events.
   *
   * <p>SAX parsers are strongly encouraged (though not absolutely
   * required) to supply a locator: if it does so, it must supply
   * the locator to the application by invoking this method before
   * invoking any of the other methods in the ContentHandler
   * interface.</p>
   *
   * <p>The locator allows the application to determine the end
   * position of any document-related event, even if the parser is
   * not reporting an error.  Typically, the application will
   * use this information for reporting its own errors (such as
   * character content that does not match an application's
   * business rules).  The information returned by the locator
   * is probably not sufficient for use with a search engine.</p>
   *
   * <p>Note that the locator will return correct information only
   * during the invocation of the events in this interface.  The
   * application should not attempt to use it at any other time.</p>
   *
   * <p>
   *  接收用于查找SAX文档事件的原点的对象。
   * 
   *  <p>强烈鼓励(虽然不是绝对需要)SAX解析器提供一个定位器：如果它这样做,它必须提供定位器到应用程序通过调用此方法,然后调用任何其他方法在ContentHandler接口。 p>
   * 
   *  <p>定位器允许应用程序确定任何文档相关事件的结束位置,即使解析器未报告错误。通常,应用程序将使用此信息来报告其自身的错误(例如,字符内容与应用程序的业务规则不匹配)。
   * 定位器返回的信息可能不足以用于搜索引擎。</p>。
   * 
   *  <p>请注意,只有在此界面中调用事件时,定位器才会返回正确的信息。该应用程序不应尝试在任何其他时间使用它。</p>
   * 
   * 
   * @param locator An object that can return the location of
   *                any SAX document event.
   * @see org.xml.sax.Locator
   */
  public void setDocumentLocator(Locator locator)
  {

    // No action for the moment.
  }

  /**
   * Receive notification of the beginning of a document.
   *
   * <p>The SAX parser will invoke this method only once, before any
   * other methods in this interface or in DTDHandler (except for
   * setDocumentLocator).</p>
   * <p>
   *  接收文档开头的通知。
   * 
   *  <p> SAX解析器只会在此接口或DTDHandler中的任何其他方法(setDocumentLocator除外)之前调用此方法一次。</p>
   * 
   */
  public void startDocument() throws org.xml.sax.SAXException
  {

    // No action for the moment.
  }

  /**
   * Receive notification of the end of a document.
   *
   * <p>The SAX parser will invoke this method only once, and it will
   * be the last method invoked during the parse.  The parser shall
   * not invoke this method until it has either abandoned parsing
   * (because of an unrecoverable error) or reached the end of
   * input.</p>
   * <p>
   *  接收文档结束的通知。
   * 
   * <p> SAX解析器将仅调用此方法一次,它将是解析期间调用的最后一个方法。解析器不应该调用此方法,直到它放弃了解析(因为一个不可恢复的错误)或到达输入的结束。</p>
   * 
   */
  public void endDocument() throws org.xml.sax.SAXException
  {

    // No action for the moment.
  }

  /**
   * Receive notification of the beginning of an element.
   *
   * <p>The Parser will invoke this method at the beginning of every
   * element in the XML document; there will be a corresponding
   * endElement() event for every startElement() event (even when the
   * element is empty). All of the element's content will be
   * reported, in order, before the corresponding endElement()
   * event.</p>
   *
   * <p>If the element name has a namespace prefix, the prefix will
   * still be attached.  Note that the attribute list provided will
   * contain only attributes with explicit values (specified or
   * defaulted): #IMPLIED attributes will be omitted.</p>
   *
   *
   * <p>
   *  接收元素开头的通知。
   * 
   *  <p>解析器将在XML文档中的每个元素的开头调用此方法;每个startElement()事件都会有一个相应的endElement()事件(即使元素为空)。
   * 将在相应的endElement()事件之前按顺序报告所有元素的内容。</p>。
   * 
   *  <p>如果元素名称有命名空间前缀,则前缀仍将被附加。请注意,提供的属性列表将仅包含具有显式值(指定或默认值)的属性：#IMPLIED属性将被省略。</p>
   * 
   * 
   * @param ns The namespace of the node
   * @param localName The local part of the qualified name
   * @param name The element name.
   * @param atts The attributes attached to the element, if any.
   * @see #endElement
   * @see org.xml.sax.Attributes
   */
  public void startElement(
          String ns, String localName, String name, Attributes atts)
            throws org.xml.sax.SAXException
  {

    Element elem;

        // Note that the namespace-aware call must be used to correctly
        // construct a Level 2 DOM, even for non-namespaced nodes.
    if ((null == ns) || (ns.length() == 0))
      elem = m_doc.createElementNS(null,name);
    else
      elem = m_doc.createElementNS(ns, name);

    append(elem);

    try
    {
      int nAtts = atts.getLength();

      if (0 != nAtts)
      {
        for (int i = 0; i < nAtts; i++)
        {

          //System.out.println("type " + atts.getType(i) + " name " + atts.getLocalName(i) );
          // First handle a possible ID attribute
          if (atts.getType(i).equalsIgnoreCase("ID"))
            setIDAttribute(atts.getValue(i), elem);

          String attrNS = atts.getURI(i);

          if("".equals(attrNS))
            attrNS = null; // DOM represents no-namespace as null

          // System.out.println("attrNS: "+attrNS+", localName: "+atts.getQName(i)
          //                   +", qname: "+atts.getQName(i)+", value: "+atts.getValue(i));
          // Crimson won't let us set an xmlns: attribute on the DOM.
          String attrQName = atts.getQName(i);

          // In SAX, xmlns[:] attributes have an empty namespace, while in DOM they
          // should have the xmlns namespace
          if (attrQName.startsWith("xmlns:") || attrQName.equals("xmlns")) {
            attrNS = "http://www.w3.org/2000/xmlns/";
          }

          // ALWAYS use the DOM Level 2 call!
          elem.setAttributeNS(attrNS,attrQName, atts.getValue(i));
        }
      }

      // append(elem);

      m_elemStack.push(elem);

      m_currentNode = elem;

      // append(elem);
    }
    catch(java.lang.Exception de)
    {
      // de.printStackTrace();
      throw new org.xml.sax.SAXException(de);
    }

  }

  /**



   * Receive notification of the end of an element.
   *
   * <p>The SAX parser will invoke this method at the end of every
   * element in the XML document; there will be a corresponding
   * startElement() event for every endElement() event (even when the
   * element is empty).</p>
   *
   * <p>If the element name has a namespace prefix, the prefix will
   * still be attached to the name.</p>
   *
   *
   * <p>
   *  接收元素结束的通知。
   * 
   *  <p> SAX解析器将在XML文档中每个元素的末尾调用此方法;每个endElement()事件都会有一个相应的startElement()事件(即使元素为空)。</p>
   * 
   *  <p>如果元素名称有名称空间前缀,则前缀仍会附加到名称。</p>
   * 
   * 
   * @param ns the namespace of the element
   * @param localName The local part of the qualified name of the element
   * @param name The element name
   */
  public void endElement(String ns, String localName, String name)
          throws org.xml.sax.SAXException
  {
    m_elemStack.pop();
    m_currentNode = m_elemStack.isEmpty() ? null : (Node)m_elemStack.peek();
  }

  /**
   * Set an ID string to node association in the ID table.
   *
   * <p>
   *  在ID表中将ID字符串设置为节点关联。
   * 
   * 
   * @param id The ID string.
   * @param elem The associated ID.
   */
  public void setIDAttribute(String id, Element elem)
  {

    // Do nothing. This method is meant to be overiden.
  }

  /**
   * Receive notification of character data.
   *
   * <p>The Parser will call this method to report each chunk of
   * character data.  SAX parsers may return all contiguous character
   * data in a single chunk, or they may split it into several
   * chunks; however, all of the characters in any single event
   * must come from the same external entity, so that the Locator
   * provides useful information.</p>
   *
   * <p>The application must not attempt to read from the array
   * outside of the specified range.</p>
   *
   * <p>Note that some parsers will report whitespace using the
   * ignorableWhitespace() method rather than this one (validating
   * parsers must do so).</p>
   *
   * <p>
   *  接收字符数据的通知。
   * 
   * <p>解析器将调用此方法来报告每个字符数据块。
   *  SAX解析器可以返回单个块中的所有连续字符数据,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>。
   * 
   *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
   * 
   *  <p>请注意,一些解析器将使用ignorableWhitespace()方法而不是这一个(验证解析器必须这样做)报告空格。</p>
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @see #ignorableWhitespace
   * @see org.xml.sax.Locator
   */
  public void characters(char ch[], int start, int length) throws org.xml.sax.SAXException
  {
    if(isOutsideDocElem()
       && com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer.isWhiteSpace(ch, start, length))
      return;  // avoid DOM006 Hierarchy request error

    if (m_inCData)
    {
      cdata(ch, start, length);

      return;
    }

    String s = new String(ch, start, length);
    Node childNode;
    childNode =  m_currentNode != null ? m_currentNode.getLastChild(): null;
    if( childNode != null && childNode.getNodeType() == Node.TEXT_NODE ){
       ((Text)childNode).appendData(s);
    }
    else{
       Text text = m_doc.createTextNode(s);
       append(text);
    }
  }

  /**
   * If available, when the disable-output-escaping attribute is used,
   * output raw text without escaping.  A PI will be inserted in front
   * of the node with the name "lotusxsl-next-is-raw" and a value of
   * "formatter-to-dom".
   *
   * <p>
   *  如果可用,当使用disable-output-escaping属性时,输出原始文本而不转义。
   *  PI将被插入到节点前面,名称为"lotusxsl-next-is-raw"和值为"formatter-to-dom"。
   * 
   * 
   * @param ch Array containing the characters
   * @param start Index to start of characters in the array
   * @param length Number of characters in the array
   */
  public void charactersRaw(char ch[], int start, int length)
          throws org.xml.sax.SAXException
  {
    if(isOutsideDocElem()
       && com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer.isWhiteSpace(ch, start, length))
      return;  // avoid DOM006 Hierarchy request error


    String s = new String(ch, start, length);

    append(m_doc.createProcessingInstruction("xslt-next-is-raw",
                                             "formatter-to-dom"));
    append(m_doc.createTextNode(s));
  }

  /**
   * Report the beginning of an entity.
   *
   * The start and end of the document entity are not reported.
   * The start and end of the external DTD subset are reported
   * using the pseudo-name "[dtd]".  All other events must be
   * properly nested within start/end entity events.
   *
   * <p>
   *  报告实体的开始。
   * 
   *  不报告文档实体的开始和结束。使用伪名称"[dtd]"报告外部DTD子集的开始和结束。所有其他事件必须正确嵌套在开始/结束实体事件中。
   * 
   * 
   * @param name The name of the entity.  If it is a parameter
   *        entity, the name will begin with '%'.
   * @see #endEntity
   * @see org.xml.sax.ext.DeclHandler#internalEntityDecl
   * @see org.xml.sax.ext.DeclHandler#externalEntityDecl
   */
  public void startEntity(String name) throws org.xml.sax.SAXException
  {

    // Almost certainly the wrong behavior...
    // entityReference(name);
  }

  /**
   * Report the end of an entity.
   *
   * <p>
   *  报告实体的结束。
   * 
   * 
   * @param name The name of the entity that is ending.
   * @see #startEntity
   */
  public void endEntity(String name) throws org.xml.sax.SAXException{}

  /**
   * Receive notivication of a entityReference.
   *
   * <p>
   *  接收entityReference的通知。
   * 
   * 
   * @param name name of the entity reference
   */
  public void entityReference(String name) throws org.xml.sax.SAXException
  {
    append(m_doc.createEntityReference(name));
  }

  /**
   * Receive notification of ignorable whitespace in element content.
   *
   * <p>Validating Parsers must use this method to report each chunk
   * of ignorable whitespace (see the W3C XML 1.0 recommendation,
   * section 2.10): non-validating parsers may also use this method
   * if they are capable of parsing and using content models.</p>
   *
   * <p>SAX parsers may return all contiguous whitespace in a single
   * chunk, or they may split it into several chunks; however, all of
   * the characters in any single event must come from the same
   * external entity, so that the Locator provides useful
   * information.</p>
   *
   * <p>The application must not attempt to read from the array
   * outside of the specified range.</p>
   *
   * <p>
   *  在元素内容中接收可忽略的空格的通知。
   * 
   *  <p>验证Parsers必须使用此方法来报告每个可忽略的空格(请参阅W3C XML 1.0建议,第2.10节)：如果非验证解析器能够解析和使用内容模型,那么它们也可以使用此方法。 p>
   * 
   * <p> SAX解析器可以返回单个块中的所有连续空格,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>
   * 
   *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @see #characters
   */
  public void ignorableWhitespace(char ch[], int start, int length)
          throws org.xml.sax.SAXException
  {
    if(isOutsideDocElem())
      return;  // avoid DOM006 Hierarchy request error

    String s = new String(ch, start, length);

    append(m_doc.createTextNode(s));
  }

  /**
   * Tell if the current node is outside the document element.
   *
   * <p>
   *  告诉当前节点是否在文档元素之外。
   * 
   * 
   * @return true if the current node is outside the document element.
   */
   private boolean isOutsideDocElem()
   {
      return (null == m_docFrag) && m_elemStack.size() == 0 && (null == m_currentNode || m_currentNode.getNodeType() == Node.DOCUMENT_NODE);
   }

  /**
   * Receive notification of a processing instruction.
   *
   * <p>The Parser will invoke this method once for each processing
   * instruction found: note that processing instructions may occur
   * before or after the main document element.</p>
   *
   * <p>A SAX parser should never report an XML declaration (XML 1.0,
   * section 2.8) or a text declaration (XML 1.0, section 4.3.1)
   * using this method.</p>
   *
   * <p>
   *  接收处理指令的通知。
   * 
   *  <p>对于找到的每个处理指令,解析器将调用此方法一次：请注意,处理指令可能发生在主文档元素之前或之后。</p>
   * 
   *  <p> SAX解析器不应使用此方法报告XML声明(XML 1.0,第2.8节)或文本声明(XML 1.0,第4.3.1节)。</p>
   * 
   * 
   * @param target The processing instruction target.
   * @param data The processing instruction data, or null if
   *        none was supplied.
   */
  public void processingInstruction(String target, String data)
          throws org.xml.sax.SAXException
  {
    append(m_doc.createProcessingInstruction(target, data));
  }

  /**
   * Report an XML comment anywhere in the document.
   *
   * This callback will be used for comments inside or outside the
   * document element, including comments in the external DTD
   * subset (if read).
   *
   * <p>
   *  在文档中的任何位置报告XML注释。
   * 
   *  此回调将用于文档元素内部或外部的注释,包括外部DTD子集中的注释(如果已读取)。
   * 
   * 
   * @param ch An array holding the characters in the comment.
   * @param start The starting position in the array.
   * @param length The number of characters to use from the array.
   */
  public void comment(char ch[], int start, int length) throws org.xml.sax.SAXException
  {
    append(m_doc.createComment(new String(ch, start, length)));
  }

  /** Flag indicating that we are processing a CData section          */
  protected boolean m_inCData = false;

  /**
   * Report the start of a CDATA section.
   *
   * <p>
   *  报告CDATA部分的开始。
   * 
   * 
   * @see #endCDATA
   */
  public void startCDATA() throws org.xml.sax.SAXException
  {
    m_inCData = true;
    append(m_doc.createCDATASection(""));
  }

  /**
   * Report the end of a CDATA section.
   *
   * <p>
   *  报告CDATA部分的结尾。
   * 
   * 
   * @see #startCDATA
   */
  public void endCDATA() throws org.xml.sax.SAXException
  {
    m_inCData = false;
  }

  /**
   * Receive notification of cdata.
   *
   * <p>The Parser will call this method to report each chunk of
   * character data.  SAX parsers may return all contiguous character
   * data in a single chunk, or they may split it into several
   * chunks; however, all of the characters in any single event
   * must come from the same external entity, so that the Locator
   * provides useful information.</p>
   *
   * <p>The application must not attempt to read from the array
   * outside of the specified range.</p>
   *
   * <p>Note that some parsers will report whitespace using the
   * ignorableWhitespace() method rather than this one (validating
   * parsers must do so).</p>
   *
   * <p>
   *  接收cdata的通知。
   * 
   *  <p>解析器将调用此方法来报告每个字符数据块。
   *  SAX解析器可以返回单个块中的所有连续字符数据,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>。
   * 
   * <p>应用程序不得尝试从指定范围之外的数组读取。</p>
   * 
   *  <p>请注意,一些解析器将使用ignorableWhitespace()方法而不是这一个(验证解析器必须这样做)报告空格。</p>
   * 
   * 
   * @param ch The characters from the XML document.
   * @param start The start position in the array.
   * @param length The number of characters to read from the array.
   * @see #ignorableWhitespace
   * @see org.xml.sax.Locator
   */
  public void cdata(char ch[], int start, int length) throws org.xml.sax.SAXException
  {
    if(isOutsideDocElem()
       && com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer.isWhiteSpace(ch, start, length))
      return;  // avoid DOM006 Hierarchy request error

    String s = new String(ch, start, length);

    CDATASection section  =(CDATASection) m_currentNode.getLastChild();
    section.appendData(s);
  }

  /**
   * Report the start of DTD declarations, if any.
   *
   * Any declarations are assumed to be in the internal subset
   * unless otherwise indicated.
   *
   * <p>
   *  报告DTD声明的开始(如果有)。
   * 
   *  除非另有说明,否则假定任何声明都在内部子集中。
   * 
   * 
   * @param name The document type name.
   * @param publicId The declared public identifier for the
   *        external DTD subset, or null if none was declared.
   * @param systemId The declared system identifier for the
   *        external DTD subset, or null if none was declared.
   * @see #endDTD
   * @see #startEntity
   */
  public void startDTD(String name, String publicId, String systemId)
          throws org.xml.sax.SAXException
  {

    // Do nothing for now.
  }

  /**
   * Report the end of DTD declarations.
   *
   * <p>
   *  报告DTD声明的结束。
   * 
   * 
   * @see #startDTD
   */
  public void endDTD() throws org.xml.sax.SAXException
  {

    // Do nothing for now.
  }

  /**
   * Begin the scope of a prefix-URI Namespace mapping.
   *
   * <p>The information from this event is not necessary for
   * normal Namespace processing: the SAX XML reader will
   * automatically replace prefixes for element and attribute
   * names when the http://xml.org/sax/features/namespaces
   * feature is true (the default).</p>
   *
   * <p>There are cases, however, when applications need to
   * use prefixes in character data or in attribute values,
   * where they cannot safely be expanded automatically; the
   * start/endPrefixMapping event supplies the information
   * to the application to expand prefixes in those contexts
   * itself, if necessary.</p>
   *
   * <p>Note that start/endPrefixMapping events are not
   * guaranteed to be properly nested relative to each-other:
   * all startPrefixMapping events will occur before the
   * corresponding startElement event, and all endPrefixMapping
   * events will occur after the corresponding endElement event,
   * but their order is not guaranteed.</p>
   *
   * <p>
   *  开始前缀URI范围的命名空间映射。
   * 
   *  <p>此事件的信息对于正常的命名空间处理不是必需的：当http://xml.org/sax/features/namespaces功能为true时,SAX XML阅读器将自动替换元素和属性名称的前缀(
   * 默认)。
   * </p>。
   * 
   *  <p>然而,有些情况下,应用程序需要在字符数据或属性值中使用前缀,在那里它们不能安全地自动扩展; start / endPrefixMapping事件将信息提供给应用程序,以便在必要时在这些上下文中扩
   * 展前缀。
   * </p>。
   * 
   *  <p>请注意,start / endPrefixMapping事件不能保证相对于彼此正确嵌套：所有startPrefixMapping事件将在相应的startElement事件之前发生,并且所有end
   * PrefixMapping事件将在对应的endElement事件之后发生,但它们的顺序不是保证。
   * </p>。
   * 
   * 
   * @param prefix The Namespace prefix being declared.
   * @param uri The Namespace URI the prefix is mapped to.
   * @see #endPrefixMapping
   * @see #startElement
   */
  public void startPrefixMapping(String prefix, String uri)
          throws org.xml.sax.SAXException
  {

    /*
    // Not sure if this is needed or wanted
    // Also, it fails in the stree.
    if((null != m_currentNode)
       && (m_currentNode.getNodeType() == Node.ELEMENT_NODE))
    {
      String qname;
      if(((null != prefix) && (prefix.length() == 0))
         || (null == prefix))
        qname = "xmlns";
      else
        qname = "xmlns:"+prefix;

      Element elem = (Element)m_currentNode;
      String val = elem.getAttribute(qname); // Obsolete, should be DOM2...?
      if(val == null)
      {
        elem.setAttributeNS("http://www.w3.org/XML/1998/namespace",
                            qname, uri);
      }
    }
    /* <p>
    /* //不知道这是否需要或想要的//另外,它在stree中失败。
    /*  if((null！= m_currentNode)&&(m_currentNode.getNodeType()== Node.ELEMENT_NODE)){String qname; if((null！= prefix)&&(prefix.length()== 0))||(null == prefix))qname ="xmlns"; else qname ="xmlns："+ prefix;。
    /* //不知道这是否需要或想要的//另外,它在stree中失败。
    /* 
    /*  元素elem =(Element)m_currentNode; String val = elem.getAttribute(qname); // Obsolete,should be DOM2 ..
    /* .? if(val == null){elem.setAttributeNS("http://www.w3.org/XML/1998/namespace",qname,uri); }}。
    /* 
    */
  }

  /**
   * End the scope of a prefix-URI mapping.
   *
   * <p>See startPrefixMapping for details.  This event will
   * always occur after the corresponding endElement event,
   * but the order of endPrefixMapping events is not otherwise
   * guaranteed.</p>
   *
   * <p>
   *  结束前缀URI映射的范围。
   * 
   *  <p>有关详细信息,请参阅startPrefixMapping。此事件将始终发生在对应的endElement事件之后,但endPrefixMapping事件的顺序不会另外保证。</p>
   * 
   * 
   * @param prefix The prefix that was being mapping.
   * @see #startPrefixMapping
   * @see #endElement
   */
  public void endPrefixMapping(String prefix) throws org.xml.sax.SAXException{}

  /**
   * Receive notification of a skipped entity.
   *
   * <p>The Parser will invoke this method once for each entity
   * skipped.  Non-validating processors may skip entities if they
   * have not seen the declarations (because, for example, the
   * entity was declared in an external DTD subset).  All processors
   * may skip external entities, depending on the values of the
   * http://xml.org/sax/features/external-general-entities and the
   * http://xml.org/sax/features/external-parameter-entities
   * properties.</p>
   *
   * <p>
   *  接收跳过的实体的通知。
   * 
   *  <p>解析器将为每个跳过的实体调用此方法一次。非验证处理器可以跳过实体,如果他们没有看到声明(例如,实体被声明在外部DTD子集中)。
   * 所有处理器可能会跳过外部实体,具体取决于http://xml.org/sax/features/external-generalentities和http://xml.org/sax/features/
   * 
   * @param name The name of the skipped entity.  If it is a
   *        parameter entity, the name will begin with '%'.
   */
  public void skippedEntity(String name) throws org.xml.sax.SAXException{}
}
