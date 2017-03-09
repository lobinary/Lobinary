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
 * $Id: IncrementalSAXSource.java,v 1.2.4.1 2005/09/15 08:15:06 suresh_emailid Exp $
 * <p>
 *  $ Id：IncrementalSAXSource.java,v 1.2.4.1 2005/09/15 08:15:06 suresh_emailid Exp $
 * 
 */

package com.sun.org.apache.xml.internal.dtm.ref;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/** <p>IncrementalSAXSource is an API that delivers a small number of
 * SAX events each time a request is made from a "controller"
 * coroutine.  See IncrementalSAXFilter and IncrementalSAXFilter_Xerces
 * for examples.
 *
 * Note that interaction is via the deliverMoreNodes
 * method, and therefore coroutine support is not exposed
 * here.</p>
 * <p>
 *  SAX事件每次从"控制器"协同进行请求。有关示例,请参见IncrementalSAXFilter和IncrementalSAXFilter_Xerces。
 * 
 *  请注意,交互是通过deliverMoreNodes方法,因此协同程序支持不在此显示。</p>
 * 
 * 
 * */
public interface IncrementalSAXSource
{
  // ------------------------------------------------------------------
  // SAX Output API
  // ------------------------------------------------------------------

  /** Register a SAX-style content handler for us to output to
  /* <p>
   */
  public void setContentHandler(ContentHandler handler);

  /**  Register a SAX-style lexical handler for us to output to
  /* <p>
   */
  public void setLexicalHandler(org.xml.sax.ext.LexicalHandler handler);

  /**  Register a SAX-style DTD handler for us to output to
  /* <p>
   */
  public void setDTDHandler(org.xml.sax.DTDHandler handler);

  // ------------------------------------------------------------------
  // Command Input API
  // ------------------------------------------------------------------

  /** deliverMoreNodes() is a simple API which tells the thread in which the
   * IncrementalSAXSource is running to deliver more events (true),
   * or stop delivering events and close out its input (false).
   *
   * This is intended to be called from one of our partner coroutines,
   * and serves to encapsulate the coroutine communication protocol.
   *
   * <p>
   *  IncrementalSAXSource正在运行以传递更多事件(true),或停止传递事件并关闭其输入(false)。
   * 
   *  这意味着从我们的合作伙伴协程中调用,并用于封装协同通信协议。
   * 
   * 
   * @param parsemore If true, tells the incremental SAX stream to deliver
   * another chunk of events. If false, finishes out the stream.
   *
   * @return Boolean.TRUE if the IncrementalSAXSource believes more data
   * may be available for further parsing. Boolean.FALSE if parsing
   * ran to completion, or was ended by deliverMoreNodes(false).
   * */
  public Object deliverMoreNodes (boolean parsemore);

  // ------------------------------------------------------------------
  // Parse Thread Convenience API
  // ------------------------------------------------------------------

  /** Launch an XMLReader's parsing operation, feeding events to this
   * IncrementalSAXSource. In some implementations, this may launch a
   * thread which runs the previously supplied XMLReader's parse() operation.
   * In others, it may do other forms of initialization.
   *
   * <p>
   * 
   * @throws SAXException is parse thread is already in progress
   * or parsing can not be started.
   * */
  public void startParse(InputSource source) throws SAXException;

} // class IncrementalSAXSource
