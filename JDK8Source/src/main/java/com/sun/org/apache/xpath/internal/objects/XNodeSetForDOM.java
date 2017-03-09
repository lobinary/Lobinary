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
 * $Id: XNodeSetForDOM.java,v 1.2.4.1 2005/09/14 20:34:46 jeffsuttor Exp $
 * <p>
 *  $ Id：XNodeSetForDOM.java,v 1.2.4.1 2005/09/14 20:34:46 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.objects;

import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xpath.internal.NodeSetDTM;
import com.sun.org.apache.xpath.internal.XPathContext;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

/**
 * This class overrides the XNodeSet#object() method to provide the original
 * Node object, NodeList object, or NodeIterator.
 * <p>
 *  此类覆盖XNodeSet#object()方法以提供原始的Node对象,NodeList对象或NodeIterator。
 * 
 */
public class XNodeSetForDOM extends XNodeSet
{
    static final long serialVersionUID = -8396190713754624640L;
  Object m_origObj;

  public XNodeSetForDOM(Node node, DTMManager dtmMgr)
  {
    m_dtmMgr = dtmMgr;
    m_origObj = node;
    int dtmHandle = dtmMgr.getDTMHandleFromNode(node);
    setObject(new NodeSetDTM(dtmMgr));
    ((NodeSetDTM) m_obj).addNode(dtmHandle);
  }

  /**
   * Construct a XNodeSet object.
   *
   * <p>
   *  构造一个XNodeSet对象。
   * 
   * 
   * @param val Value of the XNodeSet object
   */
  public XNodeSetForDOM(XNodeSet val)
  {
        super(val);
        if(val instanceof XNodeSetForDOM)
        m_origObj = ((XNodeSetForDOM)val).m_origObj;
  }

  public XNodeSetForDOM(NodeList nodeList, XPathContext xctxt)
  {
    m_dtmMgr = xctxt.getDTMManager();
    m_origObj = nodeList;

    // JKESS 20020514: Longer-term solution is to force
    // folks to request length through an accessor, so we can defer this
    // retrieval... but that requires an API change.
    // m_obj=new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeList, xctxt);
    com.sun.org.apache.xpath.internal.NodeSetDTM nsdtm=new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeList, xctxt);
    m_last=nsdtm.getLength();
    setObject(nsdtm);
  }

  public XNodeSetForDOM(NodeIterator nodeIter, XPathContext xctxt)
  {
    m_dtmMgr = xctxt.getDTMManager();
    m_origObj = nodeIter;

    // JKESS 20020514: Longer-term solution is to force
    // folks to request length through an accessor, so we can defer this
    // retrieval... but that requires an API change.
    // m_obj = new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeIter, xctxt);
    com.sun.org.apache.xpath.internal.NodeSetDTM nsdtm=new com.sun.org.apache.xpath.internal.NodeSetDTM(nodeIter, xctxt);
    m_last=nsdtm.getLength();
    setObject(nsdtm);
  }

  /**
   * Return the original DOM object that the user passed in.  For use primarily
   * by the extension mechanism.
   *
   * <p>
   *  返回用户传递的原始DOM对象。主要由扩展机制使用。
   * 
   * 
   * @return The object that this class wraps
   */
  public Object object()
  {
    return m_origObj;
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * <p>
   *  将结果对象转换为节点列表。始终发出错误。
   * 
   * 
   * @return null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public NodeIterator nodeset() throws javax.xml.transform.TransformerException
  {
    return (m_origObj instanceof NodeIterator)
                   ? (NodeIterator)m_origObj : super.nodeset();
  }

  /**
   * Cast result object to a nodelist. Always issues an error.
   *
   * <p>
   *  将结果对象转换为节点列表。始终发出错误。
   * 
   * @return null
   *
   * @throws javax.xml.transform.TransformerException
   */
  public NodeList nodelist() throws javax.xml.transform.TransformerException
  {
    return (m_origObj instanceof NodeList)
                   ? (NodeList)m_origObj : super.nodelist();
  }



}
