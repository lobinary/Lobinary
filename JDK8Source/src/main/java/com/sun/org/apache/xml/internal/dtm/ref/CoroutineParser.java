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
 * $Id: CoroutineParser.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * <p>
 *  $ Id：CoroutineParser.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * 
 */

package com.sun.org.apache.xml.internal.dtm.ref;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/** <p>CoroutineParser is an API for parser threads that operate as
 * coroutines. See CoroutineSAXParser and CoroutineSAXParser_Xerces
 * for examples.</p>
 *
 * <p>&lt;grumble&gt; I'd like the interface to require a specific form
 * for either the base constructor or a static factory method. Java
 * doesn't allow us to specify either, so I'll just document them
 * here:
 *
 * <ul>
 * <li>public CoroutineParser(CoroutineManager co, int appCoroutine);</li>
 * <li>public CoroutineParser createCoroutineParser(CoroutineManager co, int appCoroutine);</li>
 * </ul>
 *
 * &lt;/grumble&gt;</p>
 *
 * <p>
 *  协同。有关示例,请参阅CoroutineSAXParser和CoroutineSAXParser_Xerces。</p>
 * 
 *  <p>&lt; grumble&gt;我想接口要求一个特定的形式为基础构造函数或静态工厂方法。 Java不允许我们指定,所以我在这里记录它们：
 * 
 * <ul>
 *  <li> public CoroutineParser(CoroutineManager co,int appCoroutine); </li> <li> public CoroutineParser
 *  createCoroutineParser(CoroutineManager co,int appCoroutine); </li>。
 * </ul>
 * 
 *  &lt; / grumble&gt; </p>
 * 
 * 
 * @deprecated Since the ability to start a parse via the
 * coroutine protocol was not being used and was complicating design.
 * See {@link IncrementalSAXSource}.
 * */
public interface CoroutineParser {

    /** @return the coroutine ID number for this CoroutineParser object.
     * Note that this isn't useful unless you know which CoroutineManager
     * you're talking to. Also note that the do...() methods encapsulate
     * the common transactions with the CoroutineParser, so you shouldn't
     * need this in most cases.
     * <p>
     *  注意,这不是有用的,除非你知道你正在与哪个CoroutineManager交谈。还要注意do ...()方法封装与CoroutineParser的常见事务,所以你不应该需要这在大多数情况下。
     * 
     * 
     * */
    public int getParserCoroutineID();

    /** @return the CoroutineManager for this CoroutineParser object.
     * If you're using the do...() methods, applications should only
     * need to talk to the CoroutineManager once, to obtain the
     * application's Coroutine ID.
     * <p>
     * 如果你使用do ...()方法,应用程序应该只需要与CoroutineManager交谈一次,以获取应用程序的Coroutine ID。
     * 
     * 
     * */
    public CoroutineManager getCoroutineManager();

  /** Register a SAX-style content handler for us to output to */
  public void setContentHandler(ContentHandler handler);

  /**  Register a SAX-style lexical handler for us to output to
   *  Not all parsers support this...
   *
   * %REVIEW% Not called setLexicalHandler because Xalan uses that name
   * internally, which causes subclassing nuisances.
   * <p>
   *  不是所有的解析器都支持这个...
   * 
   *  ％REVIEW％未调用setLexicalHandler,因为Xalan在内部使用该名称,这会导致子类化烦扰。
   * 
   */
  public void setLexHandler(org.xml.sax.ext.LexicalHandler handler);

  /* The run() method is required in CoroutineParsers that run as
   * threads (of course)... but it isn't part of our API, and
   * shouldn't be declared here.
   * <p>
   *  线程(当然)...但它不是我们的API的一部分,不应该在这里声明。
   * 
   * 
   * */

  //================================================================
  /** doParse() is a simple API which tells the coroutine parser
   * to begin reading from a file.  This is intended to be called from one
   * of our partner coroutines, and serves both to encapsulate the
   * communication protocol and to avoid having to explicitly use the
   * CoroutineParser's coroutine ID number.
   *
   * %REVIEW% Can/should this unify with doMore? (if URI hasn't changed,
   * parse more from same file, else end and restart parsing...?
   *
   * <p>
   *  开始从文件读取。这旨在从我们的合作伙伴协程中调用,并且用于封装通信协议并且避免必须显式地使用CoroutineParser的协同ID号。
   * 
   *  ％REVIEW％可以/应该与doMore统一? (如果URI没有改变,从同一文件解析更多,否则结束并重新开始解析...?
   * 
   * 
   * @param source The InputSource to parse from.
   * @param appCoroutine The coroutine ID number of the coroutine invoking
   * this method, so it can be resumed after the parser has responded to the
   * request.
   * @return Boolean.TRUE if the CoroutineParser believes more data may be available
   * for further parsing. Boolean.FALSE if parsing ran to completion.
   * Exception if the parser objected for some reason.
   * */
  public Object doParse(InputSource source, int appCoroutine);

  /** doMore() is a simple API which tells the coroutine parser
   * that we need more nodes.  This is intended to be called from one
   * of our partner coroutines, and serves both to encapsulate the
   * communication protocol and to avoid having to explicitly use the
   * CoroutineParser's coroutine ID number.
   *
   * <p>
   *  我们需要更多的节点。这旨在从我们的合作伙伴协程中调用,并且用于封装通信协议并且避免必须显式地使用CoroutineParser的协同ID号。
   * 
   * 
   * @param parsemore If true, tells the incremental parser to generate
   * another chunk of output. If false, tells the parser that we're
   * satisfied and it can terminate parsing of this document.
   * @param appCoroutine The coroutine ID number of the coroutine invoking
   * this method, so it can be resumed after the parser has responded to the
   * request.
   * @return Boolean.TRUE if the CoroutineParser believes more data may be available
   * for further parsing. Boolean.FALSE if parsing ran to completion.
   * Exception if the parser objected for some reason.
   * */
  public Object doMore (boolean parsemore, int appCoroutine);

  /** doTerminate() is a simple API which tells the coroutine
   * parser to terminate itself.  This is intended to be called from
   * one of our partner coroutines, and serves both to encapsulate the
   * communication protocol and to avoid having to explicitly use the
   * CoroutineParser's coroutine ID number.
   *
   * Returns only after the CoroutineParser has acknowledged the request.
   *
   * <p>
   *  解析器终止自身。这旨在从我们的合作伙伴协程中调用,并且用于封装通信协议并且避免必须显式地使用CoroutineParser的协同ID号。
   * 
   *  仅在CoroutineParser已确认请求后返回。
   * 
   * 
   * @param appCoroutine The coroutine ID number of the coroutine invoking
   * this method, so it can be resumed after the parser has responded to the
   * request.
   * */
  public void doTerminate(int appCoroutine);

  /**
   * Initialize the coroutine parser. Same parameters could be passed
   * in a non-default constructor, or by using using context ClassLoader
   * and newInstance and then calling init()
   * <p>
   */
  public void init( CoroutineManager co, int appCoroutineID, XMLReader parser );

} // class CoroutineParser
