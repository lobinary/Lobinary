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
 * $Id: CachedXPathAPI.java,v 1.2.4.1 2005/09/10 03:47:42 jeffsuttor Exp $
 * <p>
 *  $ Id：CachedXPathAPI.java,v 1.2.4.1 2005/09/10 03:47:42 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import javax.xml.transform.TransformerException;

import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
import com.sun.org.apache.xpath.internal.objects.XObject;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

/**
 * The methods in this class are convenience methods into the
 * low-level XPath API.
 *
 * These functions tend to be a little slow, since a number of objects must be
 * created for each evaluation.  A faster way is to precompile the
 * XPaths using the low-level API, and then just use the XPaths
 * over and over.
 *
 * This is an alternative for the old XPathAPI class, which provided
 * static methods for the purpose but had the drawback of
 * instantiating a new XPathContext (and thus building a new DTMManager,
 * and new DTMs) each time it was called. XPathAPIObject instead retains
 * its context as long as the object persists, reusing the DTMs. This
 * does have a downside: if you've changed your source document, you should
 * obtain a new XPathAPIObject to continue searching it, since trying to use
 * the old DTMs will probably yield bad results or malfunction outright... and
 * the cached DTMs may consume memory until this object and its context are
 * returned to the heap. Essentially, it's the caller's responsibility to
 * decide when to discard the cache.
 *
 * <p>
 *  这个类中的方法是进入低级XPath API的方便方法。
 * 
 *  这些函数往往有点慢,因为必须为每个评估创建大量对象。更快的方法是使用低级API预编译XPath,然后只使用XPaths一遍又一遍。
 * 
 * 这是旧的XPathAPI类的替代方法,它为该目的提供了静态方法,但缺点是每次调用时都实例化一个新的XPathContext(从而构建一个新的DTMManager和新的DTM)。
 * 只要对象持续存在,XPathAPIObject而是保留其上下文,重用DTM。
 * 这有一个缺点：如果您更改了源文档,您应该获得一个新的XPathAPIObject继续搜索它,因为尝试使用旧的DTM可能会产生不良的结果或故障直接...和缓存的DTM可能消耗内存,直到这个对象及其上下文返
 * 回到堆。
 * 只要对象持续存在,XPathAPIObject而是保留其上下文,重用DTM。基本上,调用者负责决定何时丢弃缓存。
 * 
 * 
 * @see <a href="http://www.w3.org/TR/xpath">XPath Specification</a>
 * */
public class CachedXPathAPI
{
  /** XPathContext, and thus the document model system (DTMs), persists through multiple
      calls to this object. This is set in the constructor.
  /* <p>
  /*  调用此对象。这是在构造函数中设置的。
  /* 
  */
  protected XPathContext xpathSupport;

  /**
   * <p>Default constructor. Establishes its own {@link XPathContext}, and hence
   * its own {@link com.sun.org.apache.xml.internal.dtm.DTMManager}.
   * Good choice for simple uses.</p>
   * <p>Note that any particular instance of {@link CachedXPathAPI} must not be
   * operated upon by multiple threads without synchronization; we do
   * not currently support multithreaded access to a single
   * {@link com.sun.org.apache.xml.internal.dtm.DTM}.</p>
   * <p>
   *  <p>默认构造函数。建立自己的{@link XPathContext},因此建立自己的{@link com.sun.org.apache.xml.internal.dtm.DTMManager}。
   *  </p> <p>请注意,{@link CachedXPathAPI}的任何特定实例不能在没有同步的情况下由多个线程操作;我们目前不支持对单个{@link com.sun.org.apache.xml.internal.dtm.DTM}
   * 的多线程访问。
   *  <p>默认构造函数。建立自己的{@link XPathContext},因此建立自己的{@link com.sun.org.apache.xml.internal.dtm.DTMManager}。
   * </p>。
   * 
   */
  public CachedXPathAPI()
  {
    xpathSupport = new XPathContext();
  }

  /**
   * <p>This constructor shares its {@link XPathContext} with a pre-existing
   * {@link CachedXPathAPI}.  That allows sharing document models
   * ({@link com.sun.org.apache.xml.internal.dtm.DTM}) and previously established location
   * state.</p>
   * <p>Note that the original {@link CachedXPathAPI} and the new one should
   * not be operated upon concurrently; we do not support multithreaded access
   * to a single {@link com.sun.org.apache.xml.internal.dtm.DTM} at this time.  Similarly,
   * any particular instance of {@link CachedXPathAPI} must not be operated
   * upon by multiple threads without synchronization.</p>
   * <p>%REVIEW% Should this instead do a clone-and-reset on the XPathSupport object?</p>
   *
   * <p>
   * <p>此构造函数与预先存在的{@link CachedXPathAPI}共享其{@link XPathContext}。
   * 这允许共享文档模型({@link com.sun.org.apache.xml.internal.dtm.DTM})和先前建立的位置状态。
   * </p> <p>请注意,原始的{@link CachedXPathAPI}和新的不应同时操作;我们目前不支持对单个{@link com.sun.org.apache.xml.internal.dtm.DTM}
   * 的多线程访问。
   * 这允许共享文档模型({@link com.sun.org.apache.xml.internal.dtm.DTM})和先前建立的位置状态。
   * 类似地,{@link CachedXPathAPI}的任何特定实例不能在没有同步的情况下由多个线程操作。</p> <p>％REVIEW％这应该在XPathSupport对象上进行克隆和重置吗? >。
   * 
   */
  public CachedXPathAPI(CachedXPathAPI priorXPathAPI)
  {
    xpathSupport = priorXPathAPI.xpathSupport;
  }


  /** Returns the XPathSupport object used in this CachedXPathAPI
   *
   * %REVIEW% I'm somewhat concerned about the loss of encapsulation
   * this causes, but the xml-security folks say they need it.
   * <p>
   *  ％REVIEW％我有点担心封装的丢失导致,但xml安全人员说他们需要它。
   * 
   * 
   * */
  public XPathContext getXPathContext()
  {
    return this.xpathSupport;
  }


  /**
   * Use an XPath string to select a single node. XPath namespace
   * prefixes are resolved from the context node, which may not
   * be what you want (see the next method).
   *
   * <p>
   *  使用XPath字符串选择单个节点。 XPath命名空间前缀从上下文节点解析,这可能不是您想要的(请参阅下一个方法)。
   * 
   * 
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @return The first node found that matches the XPath, or null.
   *
   * @throws TransformerException
   */
  public  Node selectSingleNode(Node contextNode, String str)
          throws TransformerException
  {
    return selectSingleNode(contextNode, str, contextNode);
  }

  /**
   * Use an XPath string to select a single node.
   * XPath namespace prefixes are resolved from the namespaceNode.
   *
   * <p>
   *  使用XPath字符串选择单个节点。 XPath命名空间前缀从namespaceNode解析。
   * 
   * 
   * @param contextNode The node to start searching from.
   * @param str A valid XPath string.
   * @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   * @return The first node found that matches the XPath, or null.
   *
   * @throws TransformerException
   */
  public  Node selectSingleNode(
          Node contextNode, String str, Node namespaceNode)
            throws TransformerException
  {

    // Have the XObject return its result as a NodeSetDTM.
    NodeIterator nl = selectNodeIterator(contextNode, str, namespaceNode);

    // Return the first node, or null
    return nl.nextNode();
  }

  /**
   *  Use an XPath string to select a nodelist.
   *  XPath namespace prefixes are resolved from the contextNode.
   *
   * <p>
   *  使用XPath字符串选择一个节点列表。 XPath命名空间前缀从contextNode解析。
   * 
   * 
   *  @param contextNode The node to start searching from.
   *  @param str A valid XPath string.
   *  @return A NodeIterator, should never be null.
   *
   * @throws TransformerException
   */
  public  NodeIterator selectNodeIterator(Node contextNode, String str)
          throws TransformerException
  {
    return selectNodeIterator(contextNode, str, contextNode);
  }

  /**
   *  Use an XPath string to select a nodelist.
   *  XPath namespace prefixes are resolved from the namespaceNode.
   *
   * <p>
   *  使用XPath字符串选择一个节点列表。 XPath命名空间前缀从namespaceNode解析。
   * 
   * 
   *  @param contextNode The node to start searching from.
   *  @param str A valid XPath string.
   *  @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   *  @return A NodeIterator, should never be null.
   *
   * @throws TransformerException
   */
  public  NodeIterator selectNodeIterator(
          Node contextNode, String str, Node namespaceNode)
            throws TransformerException
  {

    // Execute the XPath, and have it return the result
    XObject list = eval(contextNode, str, namespaceNode);

    // Have the XObject return its result as a NodeSetDTM.
    return list.nodeset();
  }

  /**
   *  Use an XPath string to select a nodelist.
   *  XPath namespace prefixes are resolved from the contextNode.
   *
   * <p>
   *  使用XPath字符串选择一个节点列表。 XPath命名空间前缀从contextNode解析。
   * 
   * 
   *  @param contextNode The node to start searching from.
   *  @param str A valid XPath string.
   *  @return A NodeIterator, should never be null.
   *
   * @throws TransformerException
   */
  public  NodeList selectNodeList(Node contextNode, String str)
          throws TransformerException
  {
    return selectNodeList(contextNode, str, contextNode);
  }

  /**
   *  Use an XPath string to select a nodelist.
   *  XPath namespace prefixes are resolved from the namespaceNode.
   *
   * <p>
   *  使用XPath字符串选择一个节点列表。 XPath命名空间前缀从namespaceNode解析。
   * 
   * 
   *  @param contextNode The node to start searching from.
   *  @param str A valid XPath string.
   *  @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   *  @return A NodeIterator, should never be null.
   *
   * @throws TransformerException
   */
  public  NodeList selectNodeList(
          Node contextNode, String str, Node namespaceNode)
            throws TransformerException
  {

    // Execute the XPath, and have it return the result
    XObject list = eval(contextNode, str, namespaceNode);

    // Return a NodeList.
    return list.nodelist();
  }

  /**
   *  Evaluate XPath string to an XObject.  Using this method,
   *  XPath namespace prefixes will be resolved from the namespaceNode.
   * <p>
   * 将XPath字符串评估为XObject。使用此方法,XPath命名空间前缀将从namespaceNode解析。
   * 
   * 
   *  @param contextNode The node to start searching from.
   *  @param str A valid XPath string.
   *  @return An XObject, which can be used to obtain a string, number, nodelist, etc, should never be null.
   *  @see com.sun.org.apache.xpath.internal.objects.XObject
   *  @see com.sun.org.apache.xpath.internal.objects.XNull
   *  @see com.sun.org.apache.xpath.internal.objects.XBoolean
   *  @see com.sun.org.apache.xpath.internal.objects.XNumber
   *  @see com.sun.org.apache.xpath.internal.objects.XString
   *  @see com.sun.org.apache.xpath.internal.objects.XRTreeFrag
   *
   * @throws TransformerException
   */
  public  XObject eval(Node contextNode, String str)
          throws TransformerException
  {
    return eval(contextNode, str, contextNode);
  }

  /**
   *  Evaluate XPath string to an XObject.
   *  XPath namespace prefixes are resolved from the namespaceNode.
   *  The implementation of this is a little slow, since it creates
   *  a number of objects each time it is called.  This could be optimized
   *  to keep the same objects around, but then thread-safety issues would arise.
   *
   * <p>
   *  将XPath字符串评估为XObject。 XPath命名空间前缀从namespaceNode解析。执行这是一个有点慢,因为它创建了一些对象每次被调用。
   * 这可以被优化以保持相同的对象,但是然后将出现线程安全问题。
   * 
   * 
   *  @param contextNode The node to start searching from.
   *  @param str A valid XPath string.
   *  @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
   *  @return An XObject, which can be used to obtain a string, number, nodelist, etc, should never be null.
   *  @see com.sun.org.apache.xpath.internal.objects.XObject
   *  @see com.sun.org.apache.xpath.internal.objects.XNull
   *  @see com.sun.org.apache.xpath.internal.objects.XBoolean
   *  @see com.sun.org.apache.xpath.internal.objects.XNumber
   *  @see com.sun.org.apache.xpath.internal.objects.XString
   *  @see com.sun.org.apache.xpath.internal.objects.XRTreeFrag
   *
   * @throws TransformerException
   */
  public  XObject eval(Node contextNode, String str, Node namespaceNode)
          throws TransformerException
  {

    // Since we don't have a XML Parser involved here, install some default support
    // for things like namespaces, etc.
    // (Changed from: XPathContext xpathSupport = new XPathContext();
    //    because XPathContext is weak in a number of areas... perhaps
    //    XPathContext should be done away with.)

    // Create an object to resolve namespace prefixes.
    // XPath namespaces are resolved from the input context node's document element
    // if it is a root node, or else the current context node (for lack of a better
    // resolution space, given the simplicity of this sample code).
    PrefixResolverDefault prefixResolver = new PrefixResolverDefault(
      (namespaceNode.getNodeType() == Node.DOCUMENT_NODE)
      ? ((Document) namespaceNode).getDocumentElement() : namespaceNode);

    // Create the XPath object.
    XPath xpath = new XPath(str, null, prefixResolver, XPath.SELECT, null);

    // Execute the XPath, and have it return the result
    // return xpath.execute(xpathSupport, contextNode, prefixResolver);
    int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);

    return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
  }

  /**
   *   Evaluate XPath string to an XObject.
   *   XPath namespace prefixes are resolved from the namespaceNode.
   *   The implementation of this is a little slow, since it creates
   *   a number of objects each time it is called.  This could be optimized
   *   to keep the same objects around, but then thread-safety issues would arise.
   *
   * <p>
   *  将XPath字符串评估为XObject。 XPath命名空间前缀从namespaceNode解析。执行这是一个有点慢,因为它创建了一些对象每次被调用。
   * 这可以被优化以保持相同的对象,但是然后将出现线程安全问题。
   * 
   *   @param contextNode The node to start searching from.
   *   @param str A valid XPath string.
   *   @param prefixResolver Will be called if the parser encounters namespace
   *                         prefixes, to resolve the prefixes to URLs.
   *   @return An XObject, which can be used to obtain a string, number, nodelist, etc, should never be null.
   *   @see com.sun.org.apache.xpath.internal.objects.XObject
   *   @see com.sun.org.apache.xpath.internal.objects.XNull
   *   @see com.sun.org.apache.xpath.internal.objects.XBoolean
   *   @see com.sun.org.apache.xpath.internal.objects.XNumber
   *   @see com.sun.org.apache.xpath.internal.objects.XString
   *   @see com.sun.org.apache.xpath.internal.objects.XRTreeFrag
   *
   * @throws TransformerException
   */
  public  XObject eval(
          Node contextNode, String str, PrefixResolver prefixResolver)
            throws TransformerException
  {

    // Since we don't have a XML Parser involved here, install some default support
    // for things like namespaces, etc.
    // (Changed from: XPathContext xpathSupport = new XPathContext();
    //    because XPathContext is weak in a number of areas... perhaps
    //    XPathContext should be done away with.)
    // Create the XPath object.
    XPath xpath = new XPath(str, null, prefixResolver, XPath.SELECT, null);

    // Execute the XPath, and have it return the result
    XPathContext xpathSupport = new XPathContext();
    int ctxtNode = xpathSupport.getDTMHandleFromNode(contextNode);

    return xpath.execute(xpathSupport, ctxtNode, prefixResolver);
  }
}
