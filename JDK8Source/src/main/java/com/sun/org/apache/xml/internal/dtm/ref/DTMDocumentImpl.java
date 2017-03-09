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
 * $Id: DTMDocumentImpl.java,v 1.2.4.1 2005/09/15 08:15:01 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMDocumentImpl.java,v 1.2.4.1 2005/09/15 08:15:01 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import javax.xml.transform.SourceLocator;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
import com.sun.org.apache.xml.internal.utils.FastStringBuffer;
import com.sun.org.apache.xml.internal.utils.XMLString;
import com.sun.org.apache.xml.internal.utils.XMLStringFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.ext.LexicalHandler;

/**
 * This is the implementation of the DTM document interface.  It receives
 * requests from an XML content handler similar to that of an XML DOM or SAX parser
 * to store information from the xml document in an array based
 * dtm table structure.  This informtion is used later for document navigation,
 * query, and SAX event dispatch functions. The DTM can also be used directly as a
 * document composition model for an application.  The requests received are:
 * <ul>
 * <li>initiating DTM to set the doc handle</li>
 * <li>resetting DTM for data structure reuse</li>
 * <li>hinting the end of document to adjust the end of data structure pointers</li>
 * <li>createnodes (element, comment, text, attribute, ....)</li>
 * <li>hinting the end of an element to patch parent and siblings<li>
 * <li>setting application provided symbol name stringpool data structures</li>
 * </ul>
 * <p>State: In progress!!</p>
 *
 * %REVIEW% I _think_ the SAX convention is that "no namespace" is expressed
 * as "" rather than as null (which is the DOM's convention). What should
 * DTM expect? What should it do with the other?
 *
 * <p>Origin: the implemention is a composite logic based on the DTM of XalanJ1 and
 *     DocImpl, DocumentImpl, ElementImpl, TextImpl, etc. of XalanJ2</p>
 * <p>
 *  这是DTM文档接口的实现。它从类似于XML DOM或SAX解析器的XML内容处理器接收请求,以将来自xml文档的信息存储在基于数组的dtm表结构中。此信息稍后用于文档导航,查询和SAX事件分派功能。
 *  DTM也可以直接用作应用程序的文档合成模型。收到的请求是：。
 * <ul>
 * <li>启动DTM以设置文档句柄</li> <li>重置用于数据结构重用的DTM </li> <li>提示文档结束以调整数据结构指针的结束< <li> <li>设置应用程序提供的符号名称stringpo
 * ol数据结构</li> </li> <li> >。
 * </ul>
 *  <p>状态：正在进行中！</p>
 * 
 *  ％REVIEW％I _think_ SAX约定是"无命名空间"表示为""而不是作为null(这是DOM的约定)。 DTM应该怎么做?它应该怎么做对方?
 * 
 *  <p>原点：实现是基于XalanJ1的DTM和XalanJ2的DocImpl,DocumentImpl,ElementImpl,TextImpl等的复合逻辑。</p>
 * 
 */
public class DTMDocumentImpl
implements DTM, org.xml.sax.ContentHandler, org.xml.sax.ext.LexicalHandler
{

        // Number of lower bits used to represent node index.
        protected static final byte DOCHANDLE_SHIFT = 22;
        // Masks the lower order of node handle.
        // Same as {@link DTMConstructor.IDENT_NODE_DEFAULT}
        protected static final int NODEHANDLE_MASK = (1 << (DOCHANDLE_SHIFT + 1)) - 1;
        // Masks the higher order Document handle
        // Same as {@link DTMConstructor.IDENT_DOC_DEFAULT}
        protected static final int DOCHANDLE_MASK = -1 - NODEHANDLE_MASK;

        int m_docHandle = NULL;          // masked document handle for this dtm document
        int m_docElement = NULL;         // nodeHandle to the root of the actual dtm doc content

        // Context for parse-and-append operations
        int currentParent = 0;                  // current parent - default is document root
        int previousSibling = 0;                // previous sibling - no previous sibling
        protected int m_currentNode = -1;               // current node

        // The tree under construction can itself be used as
        // the element stack, so m_elemStack isn't needed.
        //protected Stack m_elemStack = new Stack();     // element stack

        private boolean previousSiblingWasParent = false;
        // Local cache for record-at-a-time fetch
        int gotslot[] = new int[4];

        // endDocument recieved?
        private boolean done = false;
        boolean m_isError = false;

        private final boolean DEBUG = false;

        /** The document base URI. */
        protected String m_documentBaseURI;

  /** If we're building the model incrementally on demand, we need to
   * be able to tell the source when to send us more data.
   *
   * Note that if this has not been set, and you attempt to read ahead
   * of the current build point, we'll probably throw a null-pointer
   * exception. We could try to wait-and-retry instead, as a very poor
   * fallback, but that has all the known problems with multithreading
   * on multiprocessors and we Don't Want to Go There.
   *
   * <p>
   *  能够告诉源何时向我们发送更多数据。
   * 
   *  注意,如果这没有设置,并且你尝试读取当前构建点,我们可能会抛出一个空指针异常。我们可以尝试等待重试,作为一个非常糟糕的后备,但是具有多线程在多处理器上的所有已知的问题,我们不想去那里。
   * 
   * 
   * @see setIncrementalSAXSource
   */
  private IncrementalSAXSource m_incrSAXSource=null;


        // ========= DTM data structure declarations. ==============

        // nodes array: integer array blocks to hold the first level reference of the nodes,
        // each reference slot is addressed by a nodeHandle index value.
        // Assumes indices are not larger than {@link NODEHANDLE_MASK}
        // ({@link DOCHANDLE_SHIFT} bits).
        ChunkedIntArray nodes = new ChunkedIntArray(4);

        // text/comment table: string buffer to hold the text string values of the document,
        // each of which is addressed by the absolute offset and length in the buffer
        private FastStringBuffer m_char = new FastStringBuffer();
        // Start of string currently being accumulated into m_char;
        // needed because the string may be appended in several chunks.
        private int m_char_current_start=0;

        // %TBD% INITIALIZATION/STARTUP ISSUES
        // -- Should we really be creating these, or should they be
        // passed in from outside? Scott want to be able to share
        // pools across multiple documents, so setting them here is
        // probably not the right default.
        private DTMStringPool m_localNames = new DTMStringPool();
        private DTMStringPool m_nsNames = new DTMStringPool();
        private DTMStringPool m_prefixNames = new DTMStringPool();

        // %TBD% If we use the current ExpandedNameTable mapper, it
        // needs to be bound to the NS and local name pools. Which
        // means it needs to attach to them AFTER we've resolved their
        // startup. Or it needs to attach to this document and
        // retrieve them each time. Or this needs to be
        // an interface _implemented_ by this class... which might be simplest!
        private ExpandedNameTable m_expandedNames=
                new ExpandedNameTable();

        private XMLStringFactory m_xsf;


        /**
         * Construct a DTM.
         *
         * <p>
         *  构造DTM。
         * 
         * 
         * @param documentNumber the ID number assigned to this document.
         * It will be shifted up into the high bits and returned as part of
         * all node ID numbers, so those IDs indicate which document they
         * came from as well as a location within the document. It is the
         * DTMManager's responsibility to assign a unique number to each
         * document.
         */
        public DTMDocumentImpl(DTMManager mgr, int documentNumber,
                               DTMWSFilter whiteSpaceFilter,
                               XMLStringFactory xstringfactory){
                initDocument(documentNumber);    // clear nodes and document handle
                m_xsf = xstringfactory;
        }

  /** Bind a IncrementalSAXSource to this DTM. If we discover we need nodes
   * that have not yet been built, we will ask this object to send us more
   * events, and it will manage interactions with its data sources.
   *
   * Note that we do not actually build the IncrementalSAXSource, since we don't
   * know what source it's reading from, what thread that source will run in,
   * or when it will run.
   *
   * <p>
   *  尚未构建,我们将要求此对象向我们发送更多事件,它将管理与其数据源的交互。
   * 
   *  注意,我们并不实际构建IncrementalSAXSource,因为我们不知道它读取什么源,源代码将运行什么线程,或什么时候运行。
   * 
   * 
   * @param source The IncrementalSAXSource that we want to recieve events from
   * on demand.
   */
  public void setIncrementalSAXSource(IncrementalSAXSource source)
  {
    m_incrSAXSource=source;

    // Establish SAX-stream link so we can receive the requested data
    source.setContentHandler(this);
    source.setLexicalHandler(this);

    // Are the following really needed? IncrementalSAXSource doesn't yet
    // support them, and they're mostly no-ops here...
    //source.setErrorHandler(this);
    //source.setDTDHandler(this);
    //source.setDeclHandler(this);
  }

        /**
         * Wrapper for ChunkedIntArray.append, to automatically update the
         * previous sibling's "next" reference (if necessary) and periodically
         * wake a reader who may have encountered incomplete data and entered
         * a wait state.
         * <p>
         * ChunkedIntArray.append的包装器,自动更新先前的兄弟的"下一个"引用(如果需要),并定期唤醒可能遇到不完整数据并进入等待状态的读者。
         * 
         * 
         * @param w0 int As in ChunkedIntArray.append
         * @param w1 int As in ChunkedIntArray.append
         * @param w2 int As in ChunkedIntArray.append
         * @param w3 int As in ChunkedIntArray.append
         * @return int As in ChunkedIntArray.append
         * @see ChunkedIntArray.append
         */
        private final int appendNode(int w0, int w1, int w2, int w3)
        {
                // A decent compiler may inline this.
                int slotnumber = nodes.appendSlot(w0, w1, w2, w3);

                if (DEBUG) System.out.println(slotnumber+": "+w0+" "+w1+" "+w2+" "+w3);

                if (previousSiblingWasParent)
                        nodes.writeEntry(previousSibling,2,slotnumber);

                previousSiblingWasParent = false;       // Set the default; endElement overrides

                return slotnumber;
        }

        // ========= DTM Implementation Control Functions. ==============

        /**
         * Set an implementation dependent feature.
         * <p>
         * %REVIEW% Do we really expect to set features on DTMs?
         *
         * <p>
         *  设置实现相关的功能。
         * <p>
         *  ％REVIEW％我们真的希望在DTM上设置功能吗?
         * 
         * 
         * @param featureId A feature URL.
         * @param state true if this feature should be on, false otherwise.
         */
        public void setFeature(String featureId, boolean state) {};

        /**
         * Set a reference pointer to the element name symbol table.
         * %REVIEW% Should this really be Public? Changing it while
         * DTM is in use would be a disaster.
         *
         * <p>
         *  设置指向元素名称符号表的引用指针。 ％REVIEW％这应该是公开吗?改变它,而DTM正在使用将是一场灾难。
         * 
         * 
         * @param poolRef DTMStringPool reference to an instance of table.
         */
        public void setLocalNameTable(DTMStringPool poolRef) {
                m_localNames = poolRef;
        }

        /**
         * Get a reference pointer to the element name symbol table.
         *
         * <p>
         *  获取元素名称符号表的引用指针。
         * 
         * 
         * @return DTMStringPool reference to an instance of table.
         */
        public DTMStringPool getLocalNameTable() {
                 return m_localNames;
         }

        /**
         * Set a reference pointer to the namespace URI symbol table.
         * %REVIEW% Should this really be Public? Changing it while
         * DTM is in use would be a disaster.
         *
         * <p>
         *  设置指向命名空间URI符号表的引用指针。 ％REVIEW％这应该是公开吗?改变它,而DTM正在使用将是一场灾难。
         * 
         * 
         * @param poolRef DTMStringPool reference to an instance of table.
         */
        public void setNsNameTable(DTMStringPool poolRef) {
                m_nsNames = poolRef;
        }

        /**
         * Get a reference pointer to the namespace URI symbol table.
         *
         * <p>
         *  获取指向命名空间URI符号表的引用指针。
         * 
         * 
         * @return DTMStringPool reference to an instance of table.
         */
        public DTMStringPool getNsNameTable() {
                 return m_nsNames;
         }

        /**
         * Set a reference pointer to the prefix name symbol table.
         * %REVIEW% Should this really be Public? Changing it while
         * DTM is in use would be a disaster.
         *
         * <p>
         *  设置指向前缀名称符号表的引用指针。 ％REVIEW％这应该是公开吗?改变它,而DTM正在使用将是一场灾难。
         * 
         * 
         * @param poolRef DTMStringPool reference to an instance of table.
         */
        public void setPrefixNameTable(DTMStringPool poolRef) {
                m_prefixNames = poolRef;
        }

        /**
         * Get a reference pointer to the prefix name symbol table.
         *
         * <p>
         *  获取前缀名称符号表的引用指针。
         * 
         * 
         * @return DTMStringPool reference to an instance of table.
         */
        public DTMStringPool getPrefixNameTable() {
                return m_prefixNames;
        }

         /**
          * Set a reference pointer to the content-text repository
          *
          * <p>
          *  设置指向内容文本库的引用指针
          * 
          * 
          * @param buffer FastStringBuffer reference to an instance of
          * buffer
          */
         void setContentBuffer(FastStringBuffer buffer) {
                 m_char = buffer;
         }

         /**
          * Get a reference pointer to the content-text repository
          *
          * <p>
          *  获取内容文本库的引用指针
          * 
          * 
          * @return FastStringBuffer reference to an instance of buffer
          */
         FastStringBuffer getContentBuffer() {
                 return m_char;
         }

  /** getContentHandler returns "our SAX builder" -- the thing that
   * someone else should send SAX events to in order to extend this
   * DTM model.
   *
   * <p>
   *  别人应该发送SAX事件为了扩展这个DTM模型。
   * 
   * 
   * @return null if this model doesn't respond to SAX events,
   * "this" if the DTM object has a built-in SAX ContentHandler,
   * the IncrementalSAXSource if we're bound to one and should receive
   * the SAX stream via it for incremental build purposes...
   * */
  public org.xml.sax.ContentHandler getContentHandler()
  {
    if (m_incrSAXSource instanceof IncrementalSAXSource_Filter)
      return (ContentHandler) m_incrSAXSource;
    else
      return this;
  }

  /**
   * Return this DTM's lexical handler.
   *
   * %REVIEW% Should this return null if constrution already done/begun?
   *
   * <p>
   *  返回此DTM的词法处理程序。
   * 
   *  ％REVIEW％如果解析已经完成/开始,这应该返回null吗?
   * 
   * 
   * @return null if this model doesn't respond to lexical SAX events,
   * "this" if the DTM object has a built-in SAX ContentHandler,
   * the IncrementalSAXSource if we're bound to one and should receive
   * the SAX stream via it for incremental build purposes...
   */
  public LexicalHandler getLexicalHandler()
  {

    if (m_incrSAXSource instanceof IncrementalSAXSource_Filter)
      return (LexicalHandler) m_incrSAXSource;
    else
      return this;
  }

  /**
   * Return this DTM's EntityResolver.
   *
   * <p>
   *  返回此DTM的EntityResolver。
   * 
   * 
   * @return null if this model doesn't respond to SAX entity ref events.
   */
  public org.xml.sax.EntityResolver getEntityResolver()
  {

    return null;
  }

  /**
   * Return this DTM's DTDHandler.
   *
   * <p>
   *  返回此DTM的DTDHandler。
   * 
   * 
   * @return null if this model doesn't respond to SAX dtd events.
   */
  public org.xml.sax.DTDHandler getDTDHandler()
  {

    return null;
  }

  /**
   * Return this DTM's ErrorHandler.
   *
   * <p>
   *  返回此DTM的ErrorHandler。
   * 
   * 
   * @return null if this model doesn't respond to SAX error events.
   */
  public org.xml.sax.ErrorHandler getErrorHandler()
  {

    return null;
  }

  /**
   * Return this DTM's DeclHandler.
   *
   * <p>
   *  返回这个DTM的DeclHandler。
   * 
   * 
   * @return null if this model doesn't respond to SAX Decl events.
   */
  public org.xml.sax.ext.DeclHandler getDeclHandler()
  {

    return null;
  }

  /** @return true iff we're building this model incrementally (eg
   * we're partnered with a IncrementalSAXSource) and thus require that the
   * transformation and the parse run simultaneously. Guidance to the
   * DTMManager.
   * <p>
   * 我们与IncrementalSAXSource合作),因此要求转换和解析同时运行。指导DTMManager。
   * 
   * 
   * */
  public boolean needsTwoThreads()
  {
    return null!=m_incrSAXSource;
  }

  //================================================================
  // ========= SAX2 ContentHandler methods =========
  // Accept SAX events, use them to build/extend the DTM tree.
  // Replaces the deprecated DocumentHandler interface.

  public void characters(char[] ch, int start, int length)
       throws org.xml.sax.SAXException
  {
    // Actually creating the text node is handled by
    // processAccumulatedText(); here we just accumulate the
    // characters into the buffer.
    m_char.append(ch,start,length);
  }

  // Flush string accumulation into a text node
  private void processAccumulatedText()
  {
    int len=m_char.length();
    if(len!=m_char_current_start)
      {
        // The FastStringBuffer has been previously agreed upon
        appendTextChild(m_char_current_start,len-m_char_current_start);
        m_char_current_start=len;
      }
  }
  public void endDocument()
       throws org.xml.sax.SAXException
  {
    // May need to tell the low-level builder code to pop up a level.
    // There _should't_ be any significant pending text at this point.
    appendEndDocument();
  }
  public void endElement(java.lang.String namespaceURI, java.lang.String localName,
      java.lang.String qName)
       throws org.xml.sax.SAXException
  {
    processAccumulatedText();
    // No args but we do need to tell the low-level builder code to
    // pop up a level.
    appendEndElement();
  }
  public void endPrefixMapping(java.lang.String prefix)
       throws org.xml.sax.SAXException
  {
    // No-op
  }
  public void ignorableWhitespace(char[] ch, int start, int length)
       throws org.xml.sax.SAXException
  {
    // %TBD% I believe ignorable text isn't part of the DTM model...?
  }
  public void processingInstruction(java.lang.String target, java.lang.String data)
       throws org.xml.sax.SAXException
  {
    processAccumulatedText();
    // %TBD% Which pools do target and data go into?
  }
  public void setDocumentLocator(Locator locator)
  {
    // No-op for DTM
  }
  public void skippedEntity(java.lang.String name)
       throws org.xml.sax.SAXException
  {
    processAccumulatedText();
    //%TBD%
  }
  public void startDocument()
       throws org.xml.sax.SAXException
  {
    appendStartDocument();
  }
  public void startElement(java.lang.String namespaceURI, java.lang.String localName,
      java.lang.String qName, Attributes atts)
       throws org.xml.sax.SAXException
  {
    processAccumulatedText();

    // %TBD% Split prefix off qname
    String prefix=null;
    int colon=qName.indexOf(':');
    if(colon>0)
      prefix=qName.substring(0,colon);

    // %TBD% Where do we pool expandedName, or is it just the union, or...
    /**/System.out.println("Prefix="+prefix+" index="+m_prefixNames.stringToIndex(prefix));
    appendStartElement(m_nsNames.stringToIndex(namespaceURI),
                     m_localNames.stringToIndex(localName),
                     m_prefixNames.stringToIndex(prefix)); /////// %TBD%

    // %TBD% I'm assuming that DTM will require resequencing of
    // NS decls before other attrs, hence two passes are taken.
    // %TBD% Is there an easier way to test for NSDecl?
    int nAtts=(atts==null) ? 0 : atts.getLength();
    // %TBD% Countdown is more efficient if nobody cares about sequence.
    for(int i=nAtts-1;i>=0;--i)
      {
        qName=atts.getQName(i);
        if(qName.startsWith("xmlns:") || "xmlns".equals(qName))
          {
            prefix=null;
            colon=qName.indexOf(':');
            if(colon>0)
              {
                prefix=qName.substring(0,colon);
              }
            else
              {
                // %REVEIW% Null or ""?
                prefix=null; // Default prefix
              }


            appendNSDeclaration(
                                    m_prefixNames.stringToIndex(prefix),
                                    m_nsNames.stringToIndex(atts.getValue(i)),
                                    atts.getType(i).equalsIgnoreCase("ID"));
          }
      }

    for(int i=nAtts-1;i>=0;--i)
      {
        qName=atts.getQName(i);
        if(!(qName.startsWith("xmlns:") || "xmlns".equals(qName)))
          {
            // %TBD% I hate having to extract the prefix into a new
            // string when we may never use it. Consider pooling whole
            // qNames, which are already strings?
            prefix=null;
            colon=qName.indexOf(':');
            if(colon>0)
              {
                prefix=qName.substring(0,colon);
                localName=qName.substring(colon+1);
              }
            else
              {
                prefix=""; // Default prefix
                localName=qName;
              }


            m_char.append(atts.getValue(i)); // Single-string value
            int contentEnd=m_char.length();

            if(!("xmlns".equals(prefix) || "xmlns".equals(qName)))
              appendAttribute(m_nsNames.stringToIndex(atts.getURI(i)),
                                  m_localNames.stringToIndex(localName),
                                  m_prefixNames.stringToIndex(prefix),
                                  atts.getType(i).equalsIgnoreCase("ID"),
                                  m_char_current_start, contentEnd-m_char_current_start);
            m_char_current_start=contentEnd;
          }
      }
  }
  public void startPrefixMapping(java.lang.String prefix, java.lang.String uri)
       throws org.xml.sax.SAXException
  {
    // No-op in DTM, handled during element/attr processing?
  }

  //
  // LexicalHandler support. Not all SAX2 parsers support these events
  // but we may want to pass them through when they exist...
  //
  public void comment(char[] ch, int start, int length)
       throws org.xml.sax.SAXException
  {
    processAccumulatedText();

    m_char.append(ch,start,length); // Single-string value
    appendComment(m_char_current_start,length);
    m_char_current_start+=length;
  }
  public void endCDATA()
       throws org.xml.sax.SAXException
  {
    // No-op in DTM
  }
  public void endDTD()
       throws org.xml.sax.SAXException
  {
    // No-op in DTM
  }
  public void endEntity(java.lang.String name)
       throws org.xml.sax.SAXException
  {
    // No-op in DTM
  }
  public void startCDATA()
       throws org.xml.sax.SAXException
  {
    // No-op in DTM
  }
  public void startDTD(java.lang.String name, java.lang.String publicId,
      java.lang.String systemId)
       throws org.xml.sax.SAXException
  {
    // No-op in DTM
  }
  public void startEntity(java.lang.String name)
       throws org.xml.sax.SAXException
  {
    // No-op in DTM
  }


  //================================================================
  // ========= Document Handler Functions =========
  // %REVIEW% jjk -- DocumentHandler is  SAX Level 1, and deprecated....
  // and this wasn't a fully compliant or declared implementation of that API
  // in any case. Phase out in favor of SAX2 ContentHandler/LexicalHandler

        /**
         * Reset a dtm document to its initial (empty) state.
         *
         * The DTMManager will invoke this method when the dtm is created.
         *
         * <p>
         *  appendStartElement(m_nsNames.stringToIndex(namespaceURI),m_localNames.stringToIndex(localName),m_pre
         * fixNames.stringToIndex(prefix)); ///////％TBD％。
         * 
         *  //％TBD％我假设DTM将需要在其他attrs之前重新排序// NS解析,因此采用两个遍。
         *  //％TBD％有更简单的方法来测试NSDecl吗? int nAtts =(atts == null)? 0：atts.getLength(); //％TBD％如果没有人关心序列,倒计时会更有效率。
         *  for(int i = nAtts-1; i> = 0;  -  i){qName = atts.getQName(i); if(qName.startsWith("xmlns：")||"xmlns".equals(qName)){prefix = null; colon = qName.indexOf('：'); if(colon> 0){prefix = qName.substring(0,colon); }
         *  else {//％REVEIW％Null或""? prefix = null; //默认前缀}。
         *  //％TBD％有更简单的方法来测试NSDecl吗? int nAtts =(atts == null)? 0：atts.getLength(); //％TBD％如果没有人关心序列,倒计时会更有效率。
         * 
         *  appendNSDeclaration(m_prefixNames.stringToIndex(prefix),m_nsNames.stringToIndex(atts.getValue(i)),at
         * ts.getType(i).equalsIgnoreCase("ID")); }}。
         * 
         *  for(int i = nAtts-1; i> = 0;  -  i){qName = atts.getQName(i); if(！(qName.startsWith("xmlns：")||"xmlns".equals(qName))){//％TBD％我讨厌必须提取前缀到一个新的//字符串, 。
         * 考虑合并整个// qNames,它们已经是字符串? prefix = null; colon = qName.indexOf('：'); if(colon> 0){prefix = qName.substring(0,colon); localName = qName.substring(colon + 1); }
         *  else {prefix =""; //默认前缀localName = qName; }}。
         * 
         * m_char.append(atts.getValue(i)); //单字符串值int contentEnd = m_char.length();
         * 
         *  appendAttribute(m_nsNames.stringToIndex(atts.getURI(i)),m_localNames.stringToIndex(localName),m_pref
         * ixNames.stringToIndex(prefix() ),atts.getType(i).equalsIgnoreCase("ID"),m_char_current_start,contentE
         * nd-m_char_current_start); m_char_current_start = contentEnd; }}} public void startPrefixMapping(java.
         * lang.String prefix,java.lang.String uri)throws org.xml.sax.SAXException {// DTM中的无操作,在元素/ attr处理期间处理? }
         * }。
         * 
         *  // // LexicalHandler支持。
         * 不是所有的SAX2解析器支持这些事件//但我们可能希望传递他们通过当它们存在... // public void comment(char [] ch,int start,int length)thro
         * ws org.xml.sax.SAXException {processAccumulatedText ();。
         *  // // LexicalHandler支持。
         * 
         * m_char.append(ch,start,length); //单字符串值appendComment(m_char_current_start,length); m_char_current_sta
         * rt + = length; } public void endCDATA()throws org.xml.sax.SAXException {// DTM中的无操作public void endDTD()throws org.xml.sax.SAXException {// DTM中的无操作}
         *  public void endEntity .lang.String name)throws org.xml.sax.SAXException {// DTM中的无操作public void startCDATA()throws org.xml.sax.SAXException {// DTM中的无操作public void startDTD(java .lang.String name,java.lang.String publicId,java.lang.String systemId)throws org.xml.sax.SAXException {// DTM中的No-op}
         *  public void startEntity(java.lang.String name)throws org .xml.sax.SAXException {// DTM中的无操作。
         * 
         *  // ================================================ ================ // =========文档处理函数========= //％
         * REVIEW％jjk  -  DocumentHandler is SAX级别1,已弃用... //这在任何情况下都不是完全符合或声明的API //的实现。
         * 淘汰支持SAX2 ContentHandler / LexicalHandler。
         * 
         *  / **将dtm文档重置为其初始(空)状态。
         * 
         *  创建dtm时,DTMManager将调用此方法。
         * 
         * 
         * @param documentNumber the handle for the DTM document.
         */
        final void initDocument(int documentNumber)
        {
                // save masked DTM document handle
                m_docHandle = documentNumber<<DOCHANDLE_SHIFT;

                // Initialize the doc -- no parent, no next-sib
                nodes.writeSlot(0,DOCUMENT_NODE,-1,-1,0);
                // wait for the first startElement to create the doc root node
                done = false;
        }

//      /**
//       * Receive hint of the end of a document.
//       *
//       * <p>The content handler will invoke this method only once, and it will
//       * be the last method invoked during the parse.  The handler shall not
//       * not invoke this method until it has either abandoned parsing
//       * (because of an unrecoverable error) or reached the end of
//       * input.</p>
//       * <p>
//       * // *接收文档结束的提示。 // * // * <p>内容处理程序将仅调用此方法一次,它将是解析过程中调用的最后一个方法。
//       * 处理程序不应该// *不调用此方法,直到它放弃了解析// *(因为一个不可恢复的错误)或到达// *输入的结尾。</p>。
//       * 
//       * 
//       */
//      public void documentEnd()
//      {
//              done = true;
//              // %TBD% may need to notice the last slot number and slot count to avoid
//              // residual data from provious use of this DTM
//      }

//      /**
//       * Receive notification of the beginning of a document.
//       *
//       * <p>The SAX parser will invoke this method only once, before any
//       * other methods in this interface.</p>
//       * <p>
//       *  // *接收文档开头的通知。 // * // * <p> SAX解析器将只调用此方法一次,在此接口中的任何其他方法之前。</p>
//       * 
//       * 
//       */
//      public void reset()
//      {

//              // %TBD% reset slot 0 to indicate ChunkedIntArray reuse or wait for
//              //       the next initDocument().
//              m_docElement = NULL;     // reset nodeHandle to the root of the actual dtm doc content
//              initDocument(0);
//      }

//      /**
//       * Factory method; creates an Element node in this document.
//       *
//       * The node created will be chained according to its natural order of request
//       * received.  %TBD% It can be rechained later via the optional DTM writable interface.
//       *
//       * <p>The XML content handler will invoke endElement() method after all
//       * of the element's content are processed in order to give DTM the indication
//       * to prepare and patch up parent and sibling node pointers.</p>
//       *
//       * <p>The following interface for createElement will use an index value corresponds
//       * to the symbol entry in the DTMDStringPool based symbol tables.</p>
//       *
//       * <p>
//       *  // *工厂方法;在此文档中创建一个Element节点。 // * // *创建的节点将根据其收到的请求的自然顺序进行链接。 ％TBD％以后可以通过可选的DTM可写界面进行重新链接。
//       *  // * // * <p> XML内容处理程序将在处理元素内容的所有// *之后调用endElement()方法,以便向DTM指示// *准备和修补父节点和兄弟节点指针。
//       * </p> // * // * <p> createElement的以下接口将使用与基于DTMDStringPool的符号表中的符号条目对应的索引值// *。</p> // *。
//       * 
//       * 
//       * @param nsIndex The namespace of the node
//       * @param nameIndex The element name.
//       * @see #endElement
//       * @see org.xml.sax.Attributes
//       * @return nodeHandle int of the element created
//       */
//      public int createElement(int nsIndex, int nameIndex, Attributes atts)
//      {
//              // do document root node creation here on the first element, create nodes for
//              // this element and its attributes, store the element, namespace, and attritute
//              // name indexes to the nodes array, keep track of the current node and parent
//              // element used

//              // W0  High:  Namespace  Low:  Node Type
//              int w0 = (nsIndex << 16) | ELEMENT_NODE;
//              // W1: Parent
//              int w1 = currentParent;
//              // W2: Next  (initialized as 0)
//              int w2 = 0;
//              // W3: Tagname
//              int w3 = nameIndex;
//              //int ourslot = nodes.appendSlot(w0, w1, w2, w3);
//              int ourslot = appendNode(w0, w1, w2, w3);
//              currentParent = ourslot;
//              previousSibling = 0;
//              setAttributes(atts);

//              // set the root element pointer when creating the first element node
//              if (m_docElement == NULL)
//                      m_docElement = ourslot;
//              return (m_docHandle | ourslot);
//      }

//      // Factory method to create an Element node not associated with a given name space
//      // using String value parameters passed in from a content handler or application
//      /**
//       * Factory method; creates an Element node not associated with a given name space in this document.
//       *
//       * The node created will be chained according to its natural order of request
//       * received.  %TBD% It can be rechained later via the optional DTM writable interface.
//       *
//       * <p>The XML content handler or application will invoke endElement() method after all
//       * of the element's content are processed in order to give DTM the indication
//       * to prepare and patch up parent and sibling node pointers.</p>
//       *
//       * <p>The following parameters for createElement contains raw string values for name
//       * symbols used in an Element node.</p>
//       *
//       * <p>
//       * // *工厂方法;创建一个与本文档中的给定名称空间不相关联的Element节点。 // * // *创建的节点将根据其收到的请求的自然顺序进行链接。
//       *  ％TBD％以后可以通过可选的DTM可写界面进行重新链接。
//       *  // * // * <p> XML内容处理程序或应用程序将在处理元素内容的所有// *之后调用endElement()方法,以便向DTM指示// *准备和修补父节点和兄弟节点节点指针。
//       * </p> // * // * <p> createElement的以下参数包含在Element节点中使用的name // *符号的原始字符串值。</p> // *。
//       * 
//       * 
//       * @param name String the element name, including the prefix if any.
//       * @param atts The attributes attached to the element, if any.
//       * @see #endElement
//       * @see org.xml.sax.Attributes
//       */
//      public int createElement(String name, Attributes atts)
//      {
//              // This method wraps around the index valued interface of the createElement interface.
//              // The raw string values are stored into the current DTM name symbol tables.  The method
//              // method will then use the index values returned to invoke the other createElement()
//              // onverted to index values modified to match a
//              // method.
//              int nsIndex = NULL;
//              int nameIndex = m_localNames.stringToIndex(name);
//              // note - there should be no prefix separator in the name because it is not associated
//              // with a name space

//              return createElement(nsIndex, nameIndex, atts);
//      }

//      // Factory method to create an Element node associated with a given name space
//      // using String value parameters passed in from a content handler or application
//      /**
//       * Factory method; creates an Element node associated with a given name space in this document.
//       *
//       * The node created will be chained according to its natural order of request
//       * received.  %TBD% It can be rechained later via the optional DTM writable interface.
//       *
//       * <p>The XML content handler or application will invoke endElement() method after all
//       * of the element's content are processed in order to give DTM the indication
//       * to prepare and patch up parent and sibling node pointers.</p>
//       *
//       * <p>The following parameters for createElementNS contains raw string values for name
//       * symbols used in an Element node.</p>
//       *
//       * <p>
//       *  // *工厂方法;创建与此文档中的给定名称空间相关联的Element节点。 // * // *创建的节点将根据其收到的请求的自然顺序进行链接。
//       *  ％TBD％以后可以通过可选的DTM可写界面进行重新链接。
//       *  // * // * <p> XML内容处理程序或应用程序将在处理元素内容的所有// *之后调用endElement()方法,以便向DTM指示// *准备和修补父节点和兄弟节点节点指针。
//       * </p> // * // * <p> createElementNS的以下参数包含在元素节点中使用的名称// *符号的原始字符串值。</p> // *。
//       * 
//       * 
//       * @param ns String the namespace of the node
//       * @param name String the element name, including the prefix if any.
//       * @param atts The attributes attached to the element, if any.
//       * @see #endElement
//       * @see org.xml.sax.Attributes
//       */
//      public int createElementNS(String ns, String name, Attributes atts)
//      {
//              // This method wraps around the index valued interface of the createElement interface.
//              // The raw string values are stored into the current DTM name symbol tables.  The method
//              // method will then use the index values returned to invoke the other createElement()
//              // onverted to index values modified to match a
//              // method.
//              int nsIndex = m_nsNames.stringToIndex(ns);
//              int nameIndex = m_localNames.stringToIndex(name);
//              // The prefixIndex is not needed by the indexed interface of the createElement method
//              int prefixSep = name.indexOf(":");
//              int prefixIndex = m_prefixNames.stringToIndex(name.substring(0, prefixSep));
//              return createElement(nsIndex, nameIndex, atts);
//      }

//      /**
//       * Receive an indication for the end of an element.
//       *
//       * <p>The XML content handler will invoke this method at the end of every
//       * element in the XML document to give hint its time to pop up the current
//       * element and parent and patch up parent and sibling pointers if necessary
//       *
//       * <p>%tbd% The following interface may need to be modified to match a
//       * coordinated access to the DTMDStringPool based symbol tables.</p>
//               *
//               * <p>
//               * // *接收元素结束的指示。
//               *  // * // * <p> XML内容处理程序将在XML文档中每个// *元素的末尾调用此方法,以提示其时间弹出当前的// *元素和父代和补丁父代如果必要的话,还有兄弟指针// * // * <p>％
//               * tbd％以下接口可能需要修改,以匹配基于DTMDStringPool的符号表的// *协调访问。
//               * // *接收元素结束的指示。</p> // *。
//               * 
//               * 
//       * @param ns the namespace of the element
//       * @param name The element name
//       */
//      public void endElement(String ns, String name)
//      {
//              // pop up the stacks

//              //
//              if (previousSiblingWasParent)
//                      nodes.writeEntry(previousSibling, 2, NULL);

//              // Pop parentage
//              previousSibling = currentParent;
//              nodes.readSlot(currentParent, gotslot);
//              currentParent = gotslot[1] & 0xFFFF;

//              // The element just being finished will be
//              // the previous sibling for the next operation
//              previousSiblingWasParent = true;

//              // Pop a level of namespace table
//              // namespaceTable.removeLastElem();
//      }

//      /**
//       * Creates attributes for the current node.
//       *
//       * <p>
//       *  // *为当前节点创建属性。 // *
//       * 
//       * 
//       * @param atts Attributes to be created.
//       */
//      void setAttributes(Attributes atts) {
//              int atLength = (null == atts) ? 0 : atts.getLength();
//              for (int i=0; i < atLength; i++) {
//                      String qname = atts.getQName(i);
//                      createAttribute(atts.getQName(i), atts.getValue(i));
//              }
//      }

//      /**
//       * Appends an attribute to the document.
//       * <p>
//       *  // *将属性附加到文档。
//       * 
//       * 
//       * @param qname Qualified Name of the attribute
//       * @param value Value of the attribute
//       * @return Handle of node
//       */
//      public int createAttribute(String qname, String value) {
//              int colonpos = qname.indexOf(":");
//              String attName = qname.substring(colonpos+1);
//              int w0 = 0;
//              if (colonpos > 0) {
//                      String prefix = qname.substring(0, colonpos);
//                      if (prefix.equals("xml")) {
//                              //w0 = ATTRIBUTE_NODE |
//                              //      (com.sun.org.apache.xalan.internal.templates.Constants.S_XMLNAMESPACEURI << 16);
//                      } else {
//                              //w0 = ATTRIBUTE_NODE |
//                      }
//              } else {
//                      w0 = ATTRIBUTE_NODE;
//              }
//              // W1:  Parent
//              int w1 = currentParent;
//              // W2:  Next (not yet resolved)
//              int w2 = 0;
//              // W3:  Tag name
//              int w3 = m_localNames.stringToIndex(attName);
//              // Add node
//              int ourslot = appendNode(w0, w1, w2, w3);
//              previousSibling = ourslot;      // Should attributes be previous siblings

//              // W0: Node Type
//              w0 = TEXT_NODE;
//              // W1: Parent
//              w1 = ourslot;
//              // W2: Start Position within buffer
//              w2 = m_char.length();
//              m_char.append(value);
//              // W3: Length
//              w3 = m_char.length() - w2;
//              appendNode(w0, w1, w2, w3);
//              charStringStart=m_char.length();
//              charStringLength = 0;
//              //previousSibling = ourslot;
//              // Attrs are Parents
//              previousSiblingWasParent = true;
//              return (m_docHandle | ourslot);
//      }

//      /**
//       * Factory method; creates a Text node in this document.
//       *
//       * The node created will be chained according to its natural order of request
//       * received.  %TBD% It can be rechained later via the optional DTM writable interface.
//       *
//       * <p>
//       *  // *工厂方法;在此文档中创建一个Text节点。 // * // *创建的节点将根据其收到的请求的自然顺序进行链接。 ％TBD％以后可以通过可选的DTM可写界面进行重新链接。 // *
//       * 
//       * 
//       * @param text String The characters text string from the XML document.
//       * @return int DTM node-number of the text node created
//       */
//      public int createTextNode(String text)
//      throws DTMException
//      {
//              // wraps around the index value based createTextNode method
//              return createTextNode(text.toCharArray(), 0, text.length());
//      }

//      /**
//       * Factory method; creates a Text node in this document.
//       *
//       * The node created will be chained according to its natural order of request
//       * received.  %TBD% It can be rechained later via the optional DTM writable interface.
//       *
//       * %REVIEW% for text normalization issues, unless we are willing to
//       * insist that all adjacent text must be merged before this method
//       * is called.
//       *
//       * <p>
//       *  // *工厂方法;在此文档中创建一个Text节点。 // * // *创建的节点将根据其收到的请求的自然顺序进行链接。 ％TBD％以后可以通过可选的DTM可写界面进行重新链接。
//       *  // * // *％REVIEW％用于文本规范化问题,除非我们愿意// *坚持在调用该方法// *之前必须合并所有相邻文本。 // *。
//       * 
//       * 
//       * @param ch The characters from the XML document.
//       * @param start The start position in the array.
//       * @param length The number of characters to read from the array.
//       */
//      public int createTextNode(char ch[], int start, int length)
//      throws DTMException
//      {
//              m_char.append(ch, start, length);               // store the chunk to the text/comment string table

//              // create a Text Node
//              // %TBD% may be possible to combine with appendNode()to replace the next chunk of code
//              int w0 = TEXT_NODE;
//              // W1: Parent
//              int w1 = currentParent;
//              // W2: Start position within m_char
//              int w2 = charStringStart;
//              // W3: Length of the full string
//              int w3 = length;
//              int ourslot = appendNode(w0, w1, w2, w3);
//              previousSibling = ourslot;

//              charStringStart=m_char.length();
//              charStringLength = 0;
//              return (m_docHandle | ourslot);
//      }

//      /**
//       * Factory method; creates a Comment node in this document.
//       *
//       * The node created will be chained according to its natural order of request
//       * received.  %TBD% It can be rechained later via the optional DTM writable interface.
//       *
//       * <p>
//       * // *工厂方法;在本文档中创建一个注释节点。 // * // *创建的节点将根据其收到的请求的自然顺序进行链接。 ％TBD％以后可以通过可选的DTM可写界面进行重新链接。 // *
//       * 
//       * 
//       * @param text String The characters text string from the XML document.
//       * @return int DTM node-number of the text node created
//       */
//      public int createComment(String text)
//      throws DTMException
//      {
//              // wraps around the index value based createTextNode method
//              return createComment(text.toCharArray(), 0, text.length());
//      }

//      /**
//       * Factory method; creates a Comment node in this document.
//       *
//       * The node created will be chained according to its natural order of request
//       * received.  %TBD% It can be rechained later via the optional DTM writable interface.
//       *
//       * <p>
//       *  // *工厂方法;在本文档中创建一个注释节点。 // * // *创建的节点将根据其收到的请求的自然顺序进行链接。 ％TBD％以后可以通过可选的DTM可写界面进行重新链接。 // *
//       * 
//       * 
//       * @param ch An array holding the characters in the comment.
//       * @param start The starting position in the array.
//       * @param length The number of characters to use from the array.
//       * @see DTMException
//       */
//      public int createComment(char ch[], int start, int length)
//      throws DTMException
//      {
//              m_char.append(ch, start, length);               // store the comment string to the text/comment string table

//              // create a Comment Node
//              // %TBD% may be possible to combine with appendNode()to replace the next chunk of code
//              int w0 = COMMENT_NODE;
//              // W1: Parent
//              int w1 = currentParent;
//              // W2: Start position within m_char
//              int w2 = charStringStart;
//              // W3: Length of the full string
//              int w3 = length;
//              int ourslot = appendNode(w0, w1, w2, w3);
//              previousSibling = ourslot;

//              charStringStart=m_char.length();
//              charStringLength = 0;
//              return (m_docHandle | ourslot);
//      }

//      // Counters to keep track of the current text string being accumulated with respect
//      // to the text/comment string table: charStringStart should point to the starting
//      // offset of the string in the table and charStringLength the acccumulated length when
//      // appendAccumulatedText starts, and reset to the end of the table and 0 at the end
//      // of appendAccumulatedText for the next set of characters receives
//      int charStringStart=0,charStringLength=0;

        // ========= Document Navigation Functions =========

        /** Given a node handle, test if it has child nodes.
         * <p> %REVIEW% This is obviously useful at the DOM layer, where it
         * would permit testing this without having to create a proxy
         * node. It's less useful in the DTM API, where
         * (dtm.getFirstChild(nodeHandle)!=DTM.NULL) is just as fast and
         * almost as self-evident. But it's a convenience, and eases porting
         * of DOM code to DTM.  </p>
         *
         * <p>
         *  <p>％REVIEW％这在DOM层显然有用,它允许在不必创建代理节点的情况下进行测试。
         * 它在DTM API中不太有用,其中(dtm.getFirstChild(nodeHandle)！= DTM.NULL)也同样快速,几乎是不言而喻的。但它是一个方便,并简化了将DOM代码移植到DTM。
         *  </p>。
         * 
         * 
         * @param nodeHandle int Handle of the node.
         * @return int true if the given node has child nodes.
         */
        public boolean hasChildNodes(int nodeHandle) {
                return(getFirstChild(nodeHandle) != NULL);
        }

        /**
         * Given a node handle, get the handle of the node's first child.
         * If not yet resolved, waits for more nodes to be added to the document and
         * tries again.
         *
         * <p>
         *  给定一个节点句柄,获取节点的第一个孩子的句柄。如果尚未解析,则等待更多节点添加到文档并再次尝试。
         * 
         * 
         * @param nodeHandle int Handle of the node.
         * @return int DTM node-number of first child, or DTM.NULL to indicate none exists.
         */
        public int getFirstChild(int nodeHandle) {

                // ###shs worry about tracing/debug later
                nodeHandle &= NODEHANDLE_MASK;
                // Read node into variable
                nodes.readSlot(nodeHandle, gotslot);

                // type is the last half of first slot
                short type = (short) (gotslot[0] & 0xFFFF);

                // Check to see if Element or Document node
                if ((type == ELEMENT_NODE) || (type == DOCUMENT_NODE) ||
                                (type == ENTITY_REFERENCE_NODE)) {

                        // In case when Document root is given
                        //      if (nodeHandle == 0) nodeHandle = 1;
                        // %TBD% Probably was a mistake.
                        // If someone explicitly asks for first child
                        // of Document, I would expect them to want
                        // that and only that.

                        int kid = nodeHandle + 1;
                        nodes.readSlot(kid, gotslot);
                        while (ATTRIBUTE_NODE == (gotslot[0] & 0xFFFF)) {
                                // points to next sibling
                                kid = gotslot[2];
                                // Return NULL if node has only attributes
                                if (kid == NULL) return NULL;
                                nodes.readSlot(kid, gotslot);
                        }
                        // If parent slot matches given parent, return kid
                        if (gotslot[1] == nodeHandle)
                        {
                          int firstChild = kid | m_docHandle;

                          return firstChild;
                        }
                }
                // No child found

                return NULL;
        }

        /**
        * Given a node handle, advance to its last child.
        * If not yet resolved, waits for more nodes to be added to the document and
        * tries again.
        *
        * <p>
        *  给定一个节点句柄,前进到它的最后一个孩子。如果尚未解析,则等待更多节点添加到文档并再次尝试。
        * 
        * 
        * @param nodeHandle int Handle of the node.
        * @return int Node-number of last child,
        * or DTM.NULL to indicate none exists.
        */
        public int getLastChild(int nodeHandle) {
                // ###shs put trace/debug later
                nodeHandle &= NODEHANDLE_MASK;
                // do not need to test node type since getFirstChild does that
                int lastChild = NULL;
                for (int nextkid = getFirstChild(nodeHandle); nextkid != NULL;
                                nextkid = getNextSibling(nextkid)) {
                        lastChild = nextkid;
                }
                return lastChild | m_docHandle;
        }

        /**
         * Retrieves an attribute node by by qualified name and namespace URI.
         *
         * <p>
         *  按限定名称和命名空间URI检索属性节点。
         * 
         * 
         * @param nodeHandle int Handle of the node upon which to look up this attribute.
         * @param namespaceURI The namespace URI of the attribute to
         *   retrieve, or null.
         * @param name The local name of the attribute to
         *   retrieve.
         * @return The attribute node handle with the specified name (
         *   <code>nodeName</code>) or <code>DTM.NULL</code> if there is no such
         *   attribute.
         */
        public int getAttributeNode(int nodeHandle, String namespaceURI, String name) {
                int nsIndex = m_nsNames.stringToIndex(namespaceURI),
                                                                        nameIndex = m_localNames.stringToIndex(name);
                nodeHandle &= NODEHANDLE_MASK;
                nodes.readSlot(nodeHandle, gotslot);
                short type = (short) (gotslot[0] & 0xFFFF);
                // If nodeHandle points to element next slot would be first attribute
                if (type == ELEMENT_NODE)
                        nodeHandle++;
                // Iterate through Attribute Nodes
                while (type == ATTRIBUTE_NODE) {
                        if ((nsIndex == (gotslot[0] << 16)) && (gotslot[3] == nameIndex))
                                return nodeHandle | m_docHandle;
                        // Goto next sibling
                        nodeHandle = gotslot[2];
                        nodes.readSlot(nodeHandle, gotslot);
                }
                return NULL;
        }

        /**
         * Given a node handle, get the index of the node's first attribute.
         *
         * <p>
         *  给定一个节点句柄,获取节点的第一个属性的索引。
         * 
         * 
         * @param nodeHandle int Handle of the Element node.
         * @return Handle of first attribute, or DTM.NULL to indicate none exists.
         */
        public int getFirstAttribute(int nodeHandle) {
                nodeHandle &= NODEHANDLE_MASK;

                // %REVIEW% jjk: Just a quick observation: If you're going to
                // call readEntry repeatedly on the same node, it may be
                // more efficiently to do a readSlot to get the data locally,
                // reducing the addressing and call-and-return overhead.

                // Should we check if handle is element (do we want sanity checks?)
                if (ELEMENT_NODE != (nodes.readEntry(nodeHandle, 0) & 0xFFFF))
                        return NULL;
                // First Attribute (if any) should be at next position in table
                nodeHandle++;
                return(ATTRIBUTE_NODE == (nodes.readEntry(nodeHandle, 0) & 0xFFFF)) ?
                nodeHandle | m_docHandle : NULL;
        }

        /**
         * Given a node handle, get the index of the node's first child.
         * If not yet resolved, waits for more nodes to be added to the document and
         * tries again
         *
         * <p>
         *  给定一个节点句柄,获取节点的第一个孩子的索引。如果尚未解析,则等待更多节点添加到文档并再次尝试
         * 
         * 
         * @param nodeHandle handle to node, which should probably be an element
         *                   node, but need not be.
         *
         * @param inScope    true if all namespaces in scope should be returned,
         *                   false if only the namespace declarations should be
         *                   returned.
         * @return handle of first namespace, or DTM.NULL to indicate none exists.
         */
        public int getFirstNamespaceNode(int nodeHandle, boolean inScope) {

                return NULL;
        }

        /**
         * Given a node handle, advance to its next sibling.
         *
         * %TBD% This currently uses the DTM-internal definition of
         * sibling; eg, the last attr's next sib is the first
         * child. In the old DTM, the DOM proxy layer provided the
         * additional logic for the public view.  If we're rewriting
         * for XPath emulation, that test must be done here.
         *
         * %TBD% CODE INTERACTION WITH INCREMENTAL PARSE - If not yet
         * resolved, should wait for more nodes to be added to the document
         * and tries again.
         *
         * <p>
         *  给定一个节点句柄,前进到下一个兄弟节点。
         * 
         * ％TBD％这目前使用DTM内部的兄弟姐妹定义;例如,最后一个attr的下一个sib是第一个孩子。在旧的DTM中,DOM代理层为公共视图提供了附加逻辑。
         * 如果我们改写XPath仿真,那么测试必须在这里完成。
         * 
         *  ％TBD％CODE INTERACTION WITH INCREMENTAL PARSE  - 如果尚未解决,应等待更多节点添加到文档并再次尝试。
         * 
         * 
         * @param nodeHandle int Handle of the node.
         * @return int Node-number of next sibling,
         * or DTM.NULL to indicate none exists.
         * */
        public int getNextSibling(int nodeHandle) {
                nodeHandle &= NODEHANDLE_MASK;
                // Document root has no next sibling
                if (nodeHandle == 0)
                        return NULL;

                short type = (short) (nodes.readEntry(nodeHandle, 0) & 0xFFFF);
                if ((type == ELEMENT_NODE) || (type == ATTRIBUTE_NODE) ||
                                (type == ENTITY_REFERENCE_NODE)) {
                        int nextSib = nodes.readEntry(nodeHandle, 2);
                        if (nextSib == NULL)
                                return NULL;
                        if (nextSib != 0)
                                return (m_docHandle | nextSib);
                        // ###shs should cycle/wait if nextSib is 0? Working on threading next
                }
                // Next Sibling is in the next position if it shares the same parent
                int thisParent = nodes.readEntry(nodeHandle, 1);

                if (nodes.readEntry(++nodeHandle, 1) == thisParent)
                        return (m_docHandle | nodeHandle);

                return NULL;
        }

        /**
         * Given a node handle, find its preceeding sibling.
         * WARNING: DTM is asymmetric; this operation is resolved by search, and is
         * relatively expensive.
         *
         * <p>
         *  给定一个节点句柄,找到它的前面的兄弟。警告：DTM是不对称的;这个操作通过搜索解决,并且相对昂贵。
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return int Node-number of the previous sib,
         * or DTM.NULL to indicate none exists.
         */
        public int getPreviousSibling(int nodeHandle) {
                nodeHandle &= NODEHANDLE_MASK;
                // Document root has no previous sibling
                if (nodeHandle == 0)
                        return NULL;

                int parent = nodes.readEntry(nodeHandle, 1);
                int kid = NULL;
                for (int nextkid = getFirstChild(parent); nextkid != nodeHandle;
                                nextkid = getNextSibling(nextkid)) {
                        kid = nextkid;
                }
                return kid | m_docHandle;
        }

        /**
         * Given a node handle, advance to the next attribute. If an
         * element, we advance to its first attribute; if an attr, we advance to
         * the next attr on the same node.
         *
         * <p>
         *  给定一个节点句柄,前进到下一个属性。如果一个元素,我们前进到它的第一个属性;如果一个attr,我们前进到同一节点上的下一个attr。
         * 
         * 
         * @param nodeHandle int Handle of the node.
         * @return int DTM node-number of the resolved attr,
         * or DTM.NULL to indicate none exists.
         */
        public int getNextAttribute(int nodeHandle) {
                nodeHandle &= NODEHANDLE_MASK;
                nodes.readSlot(nodeHandle, gotslot);

                //%REVIEW% Why are we using short here? There's no storage
                //reduction for an automatic variable, especially one used
                //so briefly, and it typically costs more cycles to process
                //than an int would.
                short type = (short) (gotslot[0] & 0xFFFF);

                if (type == ELEMENT_NODE) {
                        return getFirstAttribute(nodeHandle);
                } else if (type == ATTRIBUTE_NODE) {
                        if (gotslot[2] != NULL)
                                return (m_docHandle | gotslot[2]);
                }
                return NULL;
        }

        /**
         * Given a namespace handle, advance to the next namespace.
         *
         * %TBD% THIS METHOD DOES NOT MATCH THE CURRENT SIGNATURE IN
         * THE DTM INTERFACE.  FIX IT, OR JUSTIFY CHANGING THE DTM
         * API.
         *
         * <p>
         *  给定一个命名空间句柄,前进到下一个命名空间。
         * 
         *  ％TBD％此方法不匹配DTM界面中的当前签名。修复,或者改变DTM API。
         * 
         * 
         * @param namespaceHandle handle to node which must be of type NAMESPACE_NODE.
         * @return handle of next namespace, or DTM.NULL to indicate none exists.
         */
        public int getNextNamespaceNode(int baseHandle,int namespaceHandle, boolean inScope) {
                // ###shs need to work on namespace
                return NULL;
        }

        /**
         * Given a node handle, advance to its next descendant.
         * If not yet resolved, waits for more nodes to be added to the document and
         * tries again.
         *
         * <p>
         *  给定一个节点句柄,前进到它的下一个后代。如果尚未解析,则等待更多节点添加到文档并再次尝试。
         * 
         * 
         * @param subtreeRootHandle
         * @param nodeHandle int Handle of the node.
         * @return handle of next descendant,
         * or DTM.NULL to indicate none exists.
         */
        public int getNextDescendant(int subtreeRootHandle, int nodeHandle) {
                subtreeRootHandle &= NODEHANDLE_MASK;
                nodeHandle &= NODEHANDLE_MASK;
                // Document root [Document Node? -- jjk] - no next-sib
                if (nodeHandle == 0)
                        return NULL;
                while (!m_isError) {
                        // Document done and node out of bounds
                        if (done && (nodeHandle > nodes.slotsUsed()))
                                break;
                        if (nodeHandle > subtreeRootHandle) {
                                nodes.readSlot(nodeHandle+1, gotslot);
                                if (gotslot[2] != 0) {
                                        short type = (short) (gotslot[0] & 0xFFFF);
                                        if (type == ATTRIBUTE_NODE) {
                                                nodeHandle +=2;
                                        } else {
                                                int nextParentPos = gotslot[1];
                                                if (nextParentPos >= subtreeRootHandle)
                                                        return (m_docHandle | (nodeHandle+1));
                                                else
                                                        break;
                                        }
                                } else if (!done) {
                                        // Add wait logic here
                                } else
                                        break;
                        } else {
                                nodeHandle++;
                        }
                }
                // Probably should throw error here like original instead of returning
                return NULL;
        }

        /**
         * Given a node handle, advance to the next node on the following axis.
         *
         * <p>
         *  给定一个节点句柄,前进到跟随轴上的下一个节点。
         * 
         * 
         * @param axisContextHandle the start of the axis that is being traversed.
         * @param nodeHandle
         * @return handle of next sibling,
         * or DTM.NULL to indicate none exists.
         */
        public int getNextFollowing(int axisContextHandle, int nodeHandle) {
                //###shs still working on
                return NULL;
        }

        /**
         * Given a node handle, advance to the next node on the preceding axis.
         *
         * <p>
         *  给定一个节点句柄,前进到前一个轴上的下一个节点。
         * 
         * 
         * @param axisContextHandle the start of the axis that is being traversed.
         * @param nodeHandle the id of the node.
         * @return int Node-number of preceding sibling,
         * or DTM.NULL to indicate none exists.
         */
        public int getNextPreceding(int axisContextHandle, int nodeHandle) {
                // ###shs copied from Xalan 1, what is this suppose to do?
                nodeHandle &= NODEHANDLE_MASK;
                while (nodeHandle > 1) {
                        nodeHandle--;
                        if (ATTRIBUTE_NODE == (nodes.readEntry(nodeHandle, 0) & 0xFFFF))
                                continue;

                        // if nodeHandle is _not_ an ancestor of
                        // axisContextHandle, specialFind will return it.
                        // If it _is_ an ancestor, specialFind will return -1

                        // %REVIEW% unconditional return defeats the
                        // purpose of the while loop -- does this
                        // logic make any sense?

                        return (m_docHandle | nodes.specialFind(axisContextHandle, nodeHandle));
                }
                return NULL;
        }

        /**
         * Given a node handle, find its parent node.
         *
         * <p>
         *  给定一个节点句柄,找到它的父节点。
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return int Node-number of parent,
         * or DTM.NULL to indicate none exists.
         */
        public int getParent(int nodeHandle) {
                // Should check to see within range?

                // Document Root should not have to be handled differently
                return (m_docHandle | nodes.readEntry(nodeHandle, 1));
        }

        /**
         * Returns the root element of the document.
         * <p>
         *  返回文档的根元素。
         * 
         * 
         * @return nodeHandle to the Document Root.
         */
        public int getDocumentRoot() {
                return (m_docHandle | m_docElement);
        }

        /**
         * Given a node handle, find the owning document node.
         *
         * <p>
         *  给定一个节点句柄,找到拥有的文档节点。
         * 
         * 
         * @return int Node handle of document, which should always be valid.
         */
        public int getDocument() {
                return m_docHandle;
        }

        /**
         * Given a node handle, find the owning document node.  This has the exact
         * same semantics as the DOM Document method of the same name, in that if
         * the nodeHandle is a document node, it will return NULL.
         *
         * <p>%REVIEW% Since this is DOM-specific, it may belong at the DOM
         * binding layer. Included here as a convenience function and to
         * aid porting of DOM code to DTM.</p>
         *
         * <p>
         * 给定一个节点句柄,找到拥有的文档节点。这具有与相同名称的DOM文档方法完全相同的语义,因为如果nodeHandle是文档节点,它将返回NULL。
         * 
         *  <p>％REVIEW％因为这是DOM特定的,它可能属于DOM绑定层。此处包括方便功能,并帮助将DOM代码移植到DTM。</p>
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return int Node handle of owning document, or NULL if the nodeHandle is
         *             a document.
         */
        public int getOwnerDocument(int nodeHandle) {
                // Assumption that Document Node is always in 0 slot
                if ((nodeHandle & NODEHANDLE_MASK) == 0)
                        return NULL;
                return (nodeHandle & DOCHANDLE_MASK);
        }

        /**
         * Given a node handle, find the owning document node.  This has the DTM
         * semantics; a Document node is its own owner.
         *
         * <p>%REVIEW% Since this is DOM-specific, it may belong at the DOM
         * binding layer. Included here as a convenience function and to
         * aid porting of DOM code to DTM.</p>
         *
         * <p>
         *  给定一个节点句柄,找到拥有的文档节点。这有DTM语义;文档节点是其自己的所有者。
         * 
         *  <p>％REVIEW％因为这是DOM特定的,它可能属于DOM绑定层。这里包括一个方便的功能,并帮助将DOM代码移植到DTM。</p>
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return int Node handle of owning document, or NULL if the nodeHandle is
         *             a document.
         */
        public int getDocumentRoot(int nodeHandle) {
                // Assumption that Document Node is always in 0 slot
                if ((nodeHandle & NODEHANDLE_MASK) == 0)
                        return NULL;
                return (nodeHandle & DOCHANDLE_MASK);
        }

        /**
         * Get the string-value of a node as a String object
         * (see http://www.w3.org/TR/xpath#data-model
         * for the definition of a node's string-value).
         *
         * <p>
         *  将节点的字符串值作为String对象获取(有关节点的字符串值的定义,请参阅http://www.w3.org/TR/xpath#data-model)。
         * 
         * 
         * @param nodeHandle The node ID.
         *
         * @return A string object that represents the string-value of the given node.
         */
        public XMLString getStringValue(int nodeHandle) {
        // ###zaj - researching
        nodes.readSlot(nodeHandle, gotslot);
        int nodetype=gotslot[0] & 0xFF;
        String value=null;

        switch (nodetype) {
        case TEXT_NODE:
        case COMMENT_NODE:
        case CDATA_SECTION_NODE:
                value= m_char.getString(gotslot[2], gotslot[3]);
                break;
        case PROCESSING_INSTRUCTION_NODE:
        case ATTRIBUTE_NODE:
        case ELEMENT_NODE:
        case ENTITY_REFERENCE_NODE:
        default:
                break;
        }
        return m_xsf.newstr( value );

        }

        /**
         * Get number of character array chunks in
         * the string-value of a node.
         * (see http://www.w3.org/TR/xpath#data-model
         * for the definition of a node's string-value).
         * Note that a single text node may have multiple text chunks.
         *
         * EXPLANATION: This method is an artifact of the fact that the
         * underlying m_chars object may not store characters in a
         * single contiguous array -- for example,the current
         * FastStringBuffer may split a single node's text across
         * multiple allocation units.  This call tells us how many
         * separate accesses will be required to retrieve the entire
         * content. PLEASE NOTE that this may not be the same as the
         * number of SAX characters() events that caused the text node
         * to be built in the first place, since m_chars buffering may
         * be on different boundaries than the parser's buffers.
         *
         * <p>
         *  获取节点的字符串值中的字符数组块数。 (有关节点的字符串值的定义,请参见http://www.w3.org/TR/xpath#data-model)。注意,单个文本节点可以具有多个文本块。
         * 
         * 说明：此方法是一个事实,即基础m_chars对象可能不存储在单个连续数组中的字符 - 例如,当前的FastStringBuffer可能分割单个节点的文本跨多个分配单元。
         * 此调用告诉我们需要多少个单独的访问来检索整个内容。请注意,这可能不同于导致首先构建文本节点的SAX字符()事件的数目,因为m_chars缓冲可能在与解析器的缓冲区不同的边界上。
         * 
         * 
         * @param nodeHandle The node ID.
         *
         * @return number of character array chunks in
         *         the string-value of a node.
         * */
        //###zaj - tbd
        public int getStringValueChunkCount(int nodeHandle)
        {
                //###zaj    return value
                return 0;
        }

        /**
         * Get a character array chunk in the string-value of a node.
         * (see http://www.w3.org/TR/xpath#data-model
         * for the definition of a node's string-value).
         * Note that a single text node may have multiple text chunks.
         *
         * EXPLANATION: This method is an artifact of the fact that
         * the underlying m_chars object may not store characters in a
         * single contiguous array -- for example,the current
         * FastStringBuffer may split a single node's text across
         * multiple allocation units.  This call retrieves a single
         * contiguous portion of the text -- as much as m-chars was
         * able to store in a single allocation unit.  PLEASE NOTE
         * that this may not be the same granularityas the SAX
         * characters() events that caused the text node to be built
         * in the first place, since m_chars buffering may be on
         * different boundaries than the parser's buffers.
         *
         * <p>
         *  在节点的字符串值中获取字符数组块。 (有关节点的字符串值的定义,请参见http://www.w3.org/TR/xpath#data-model)。注意,单个文本节点可以具有多个文本块。
         * 
         *  说明：此方法是一个事实,即基础m_chars对象可能不存储在单个连续数组中的字符 - 例如,当前的FastStringBuffer可能分割单个节点的文本跨多个分配单元。
         * 此调用检索文本的单个连续部分 - 就像m个字符能够存储在单个分配单元中一样多。
         * 请注意,这可能不是与导致首先构建文本节点的SAX字符()事件相同的粒度,因为m_chars缓冲可能在与解析器的缓冲区不同的边界上。
         * 
         * 
         * @param nodeHandle The node ID.
         * @param chunkIndex Which chunk to get.
         * @param startAndLen An array of 2 where the start position and length of
         *                    the chunk will be returned.
         *
         * @return The character array reference where the chunk occurs.  */
        //###zaj - tbd
        public char[] getStringValueChunk(int nodeHandle, int chunkIndex,
                                                                                                                                                int[] startAndLen) {return new char[0];}

        /**
         * Given a node handle, return an ID that represents the node's expanded name.
         *
         * <p>
         *  给定一个节点句柄,返回一个表示节点扩展名的ID。
         * 
         * 
         * @param nodeHandle The handle to the node in question.
         *
         * @return the expanded-name id of the node.
         */
        public int getExpandedTypeID(int nodeHandle) {
           nodes.readSlot(nodeHandle, gotslot);
           String qName = m_localNames.indexToString(gotslot[3]);
           // Remove prefix from qName
           // %TBD% jjk This is assuming the elementName is the qName.
           int colonpos = qName.indexOf(":");
           String localName = qName.substring(colonpos+1);
           // Get NS
           String namespace = m_nsNames.indexToString(gotslot[0] << 16);
           // Create expanded name
           String expandedName = namespace + ":" + localName;
           int expandedNameID = m_nsNames.stringToIndex(expandedName);

        return expandedNameID;
        }


        /**
         * Given an expanded name, return an ID.  If the expanded-name does not
         * exist in the internal tables, the entry will be created, and the ID will
         * be returned.  Any additional nodes that are created that have this
         * expanded name will use this ID.
         *
         * <p>
         * 给定扩展名称,返回ID。如果扩展名不存在于内部表中,则将创建该条目,并返回ID。创建的具有此扩展名称的任何其他节点将使用此标识。
         * 
         * 
         * @return the expanded-name id of the node.
         */
        public int getExpandedTypeID(String namespace, String localName, int type) {
           // Create expanded name
          // %TBD% jjk Expanded name is bitfield-encoded as
          // typeID[6]nsuriID[10]localID[16]. Switch to that form, and to
          // accessing the ns/local via their tables rather than confusing
          // nsnames and expandednames.
           String expandedName = namespace + ":" + localName;
           int expandedNameID = m_nsNames.stringToIndex(expandedName);

           return expandedNameID;
        }


        /**
         * Given an expanded-name ID, return the local name part.
         *
         * <p>
         *  给定扩展名称ID,返回本地名称部分。
         * 
         * 
         * @param ExpandedNameID an ID that represents an expanded-name.
         * @return String Local name of this node.
         */
        public String getLocalNameFromExpandedNameID(int ExpandedNameID) {

           // Get expanded name
           String expandedName = m_localNames.indexToString(ExpandedNameID);
           // Remove prefix from expanded name
           int colonpos = expandedName.indexOf(":");
           String localName = expandedName.substring(colonpos+1);
           return localName;
        }


        /**
         * Given an expanded-name ID, return the namespace URI part.
         *
         * <p>
         *  给定扩展名称ID,返回名称空间URI部分。
         * 
         * 
         * @param ExpandedNameID an ID that represents an expanded-name.
         * @return String URI value of this node's namespace, or null if no
         * namespace was resolved.
        */
        public String getNamespaceFromExpandedNameID(int ExpandedNameID) {

           String expandedName = m_localNames.indexToString(ExpandedNameID);
           // Remove local name from expanded name
           int colonpos = expandedName.indexOf(":");
           String nsName = expandedName.substring(0, colonpos);

        return nsName;
        }


        /**
         * fixednames
         * <p>
         *  固定名
         * 
        */
        private static final String[] fixednames=
        {
                null,null,                                                      // nothing, Element
                null,"#text",                                           // Attr, Text
                "#cdata_section",null,  // CDATA, EntityReference
                null,null,                                                      // Entity, PI
                "#comment","#document", // Comment, Document
                null,"#document-fragment", // Doctype, DocumentFragment
                null};                                                                  // Notation

        /**
         * Given a node handle, return its DOM-style node name. This will
         * include names such as #text or #document.
         *
         * <p>
         *  给定一个节点句柄,返回其DOM样式的节点名称。这将包括诸如#text或#document的名称。
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return String Name of this node, which may be an empty string.
         * %REVIEW% Document when empty string is possible...
         */
        public String getNodeName(int nodeHandle) {
                nodes.readSlot(nodeHandle, gotslot);
                short type = (short) (gotslot[0] & 0xFFFF);
                String name = fixednames[type];
                if (null == name) {
                  int i=gotslot[3];
                  /**/System.out.println("got i="+i+" "+(i>>16)+"/"+(i&0xffff));

                  name=m_localNames.indexToString(i & 0xFFFF);
                  String prefix=m_prefixNames.indexToString(i >>16);
                  if(prefix!=null && prefix.length()>0)
                    name=prefix+":"+name;
                }
                return name;
        }

        /**
         * Given a node handle, return the XPath node name.  This should be
         * the name as described by the XPath data model, NOT the DOM-style
         * name.
         *
         * <p>
         *  name = m_localNames.indexToString(i&0xFFFF); String prefix = m_prefixNames.indexToString(i >> 16); i
         * f(prefix！= null && prefix.length()> 0)name = prefix +"："+ name; } return name; }}。
         * 
         *  / **给定一个节点句柄,返回XPath节点名称。这应该是XPath数据模型描述的名称,而不是DOM风格的名称。
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return String Name of this node.
         */
        public String getNodeNameX(int nodeHandle) {return null;}

        /**
         * Given a node handle, return its DOM-style localname.
         * (As defined in Namespaces, this is the portion of the name after any
         * colon character)
         *
         * %REVIEW% What's the local name of something other than Element/Attr?
         * Should this be DOM-style (undefined unless namespaced), or other?
         *
         * <p>
         *  给定一个节点句柄,返回其DOM样式的本地名。 (如命名空间中定义,这是任何冒号字符后的名称部分)
         * 
         *  ％REVIEW％除Element / Attr之外的本地名称是什么?这应该是DOM风格(未定义除非命名空间),还是其他?
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return String Local name of this node.
         */
        public String getLocalName(int nodeHandle) {
                nodes.readSlot(nodeHandle, gotslot);
                short type = (short) (gotslot[0] & 0xFFFF);
                String name = "";
                if ((type==ELEMENT_NODE) || (type==ATTRIBUTE_NODE)) {
                  int i=gotslot[3];
                  name=m_localNames.indexToString(i & 0xFFFF);
                  if(name==null) name="";
                }
                return name;
        }

        /**
         * Given a namespace handle, return the prefix that the namespace decl is
         * mapping.
         * Given a node handle, return the prefix used to map to the namespace.
         *
         * <p> %REVIEW% Are you sure you want "" for no prefix?  </p>
         *
         * %REVIEW%  Should this be DOM-style (undefined unless namespaced),
         * or other?
         *
         * <p>
         *  给定一个命名空间句柄,返回命名空间decl正在映射的前缀。给定一个节点句柄,返回用于映射到命名空间的前缀。
         * 
         *  <p>％REVIEW％您确定要""没有前缀吗? </p>
         * 
         *  ％REVIEW％这应该是DOM风格(未定义除非命名空间),还是其他?
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return String prefix of this node's name, or "" if no explicit
         * namespace prefix was given.
         */
        public String getPrefix(int nodeHandle) {
                nodes.readSlot(nodeHandle, gotslot);
                short type = (short) (gotslot[0] & 0xFFFF);
                String name = "";
                if((type==ELEMENT_NODE) || (type==ATTRIBUTE_NODE)) {
                  int i=gotslot[3];
                  name=m_prefixNames.indexToString(i >>16);
                  if(name==null) name="";
                }
                return name;
        }

        /**
         * Given a node handle, return its DOM-style namespace URI
         * (As defined in Namespaces, this is the declared URI which this node's
         * prefix -- or default in lieu thereof -- was mapped to.)
         *
         * <p>
         * 给定一个节点句柄,返回它的DOM风格的命名空间URI(如命名空间中定义的,这是这个节点的前缀 - 或默认替代)被声明的URI。
         * 
         * 
         * @param nodeHandle the id of the node.
         * @return String URI value of this node's namespace, or null if no
         * namespace was resolved.
         */
        public String getNamespaceURI(int nodeHandle) {return null;}

        /**
         * Given a node handle, return its node value. This is mostly
         * as defined by the DOM, but may ignore some conveniences.
         * <p>
         *
         * <p>
         *  给定一个节点句柄,返回其节点值。这主要是由DOM定义的,但可能忽略一些方便。
         * <p>
         * 
         * 
         * @param nodeHandle The node id.
         * @return String Value of this node, or null if not
         * meaningful for this node type.
         */
        public String getNodeValue(int nodeHandle)
        {
                nodes.readSlot(nodeHandle, gotslot);
                int nodetype=gotslot[0] & 0xFF;         // ###zaj use mask to get node type
                String value=null;

                switch (nodetype) {                     // ###zaj todo - document nodetypes
                case ATTRIBUTE_NODE:
                        nodes.readSlot(nodeHandle+1, gotslot);
                case TEXT_NODE:
                case COMMENT_NODE:
                case CDATA_SECTION_NODE:
                        value=m_char.getString(gotslot[2], gotslot[3]);         //###zaj
                        break;
                case PROCESSING_INSTRUCTION_NODE:
                case ELEMENT_NODE:
                case ENTITY_REFERENCE_NODE:
                default:
                        break;
                }
                return value;
        }

        /**
         * Given a node handle, return its DOM-style node type.
         * <p>
         * %REVIEW% Generally, returning short is false economy. Return int?
         *
         * <p>
         *  给定一个节点句柄,返回其DOM样式的节点类型。
         * <p>
         *  ％REVIEW％一般来说,回报短是假经济。返回int?
         * 
         * 
         * @param nodeHandle The node id.
         * @return int Node type, as per the DOM's Node._NODE constants.
         */
        public short getNodeType(int nodeHandle) {
                return(short) (nodes.readEntry(nodeHandle, 0) & 0xFFFF);
        }

        /**
         * Get the depth level of this node in the tree (equals 1 for
         * a parentless node).
         *
         * <p>
         *  获取树中此节点的深度级别(对于无父节点,等于1)。
         * 
         * 
         * @param nodeHandle The node id.
         * @return the number of ancestors, plus one
         * @xsl.usage internal
         */
        public short getLevel(int nodeHandle) {
                short count = 0;
                while (nodeHandle != 0) {
                        count++;
                        nodeHandle = nodes.readEntry(nodeHandle, 1);
                }
                return count;
        }

        // ============== Document query functions ==============

        /**
         * Tests whether DTM DOM implementation implements a specific feature and
         * that feature is supported by this node.
         *
         * <p>
         *  测试DTM DOM实现是否实现特定功能,并且此节点支持该功能。
         * 
         * 
         * @param feature The name of the feature to test.
         * @param version This is the version number of the feature to test.
         *   If the version is not
         *   specified, supporting any version of the feature will cause the
         *   method to return <code>true</code>.
         * @return Returns <code>true</code> if the specified feature is
         *   supported on this node, <code>false</code> otherwise.
         */
        public boolean isSupported(String feature, String version) {return false;}

        /**
         * Return the base URI of the document entity. If it is not known
         * (because the document was parsed from a socket connection or from
         * standard input, for example), the value of this property is unknown.
         *
         * <p>
         *  返回文档实体的基本URI。如果不知道(因为文档是从套接字连接或标准输入解析的),此属性的值是未知的。
         * 
         * 
         * @return the document base URI String object or null if unknown.
         */
        public String getDocumentBaseURI()
        {

          return m_documentBaseURI;
        }

        /**
         * Set the base URI of the document entity.
         *
         * <p>
         *  设置文档实体的基本URI。
         * 
         * 
         * @param baseURI the document base URI String object or null if unknown.
         */
        public void setDocumentBaseURI(String baseURI)
        {

          m_documentBaseURI = baseURI;
        }

        /**
         * Return the system identifier of the document entity. If
         * it is not known, the value of this property is unknown.
         *
         * <p>
         *  返回文档实体的系统标识符。如果不知道,此属性的值是未知的。
         * 
         * 
         * @param nodeHandle The node id, which can be any valid node handle.
         * @return the system identifier String object or null if unknown.
         */
        public String getDocumentSystemIdentifier(int nodeHandle) {return null;}

        /**
         * Return the name of the character encoding scheme
         *        in which the document entity is expressed.
         *
         * <p>
         *  返回表示文档实体的字符编码方案的名称。
         * 
         * 
         * @param nodeHandle The node id, which can be any valid node handle.
         * @return the document encoding String object.
         */
        public String getDocumentEncoding(int nodeHandle) {return null;}

        /**
         * Return an indication of the standalone status of the document,
         *        either "yes" or "no". This property is derived from the optional
         *        standalone document declaration in the XML declaration at the
         *        beginning of the document entity, and has no value if there is no
         *        standalone document declaration.
         *
         * <p>
         *  返回文档的独立状态的指示,"是"或"否"。此属性派生自文档实体开头处的XML声明中的可选独立文档声明,如果没有独立文档声明,则没有值。
         * 
         * 
         * @param nodeHandle The node id, which can be any valid node handle.
         * @return the document standalone String object, either "yes", "no", or null.
         */
        public String getDocumentStandalone(int nodeHandle) {return null;}

        /**
         * Return a string representing the XML version of the document. This
         * property is derived from the XML declaration optionally present at the
         * beginning of the document entity, and has no value if there is no XML
         * declaration.
         *
         * <p>
         * 返回表示文档的XML版本的字符串。此属性派生自可选地存在于文档实体开头的XML声明,如果没有XML声明,则没有值。
         * 
         * 
         * @param documentHandle the document handle
         *
         * @return the document version String object
         */
        public String getDocumentVersion(int documentHandle) {return null;}

        /**
         * Return an indication of
         * whether the processor has read the complete DTD. Its value is a
         * boolean. If it is false, then certain properties (indicated in their
         * descriptions below) may be unknown. If it is true, those properties
         * are never unknown.
         *
         * <p>
         *  返回处理器是否已读取完整DTD的指示。它的值是一个布尔值。如果它是假的,那么某些属性(在下面的描述中指示)可能是未知的。如果它是真的,那些属性永远不会是未知的。
         * 
         * 
         * @return <code>true</code> if all declarations were processed {};
         *         <code>false</code> otherwise.
         */
        public boolean getDocumentAllDeclarationsProcessed() {return false;}

        /**
         *   A document type declaration information item has the following properties:
         *
         *     1. [system identifier] The system identifier of the external subset, if
         *        it exists. Otherwise this property has no value.
         *
         * <p>
         *  文档类型声明信息项具有以下属性：
         * 
         *  1. [系统标识符]外部子集的系统标识符(如果存在)。否则此属性没有值。
         * 
         * 
         * @return the system identifier String object, or null if there is none.
         */
        public String getDocumentTypeDeclarationSystemIdentifier() {return null;}

        /**
         * Return the public identifier of the external subset,
         * normalized as described in 4.2.2 External Entities [XML]. If there is
         * no external subset or if it has no public identifier, this property
         * has no value.
         *
         * <p>
         *  返回外部子集的公共标识符,如4.2.2外部实体[XML]中所述进行规范化。如果没有外部子集或者没有公共标识符,则此属性没有值。
         * 
         * 
         * @return the public identifier String object, or null if there is none.
         */
        public String getDocumentTypeDeclarationPublicIdentifier() {return null;}

        /**
         * Returns the <code>Element</code> whose <code>ID</code> is given by
         * <code>elementId</code>. If no such element exists, returns
         * <code>DTM.NULL</code>. Behavior is not defined if more than one element
         * has this <code>ID</code>. Attributes (including those
         * with the name "ID") are not of type ID unless so defined by DTD/Schema
         * information available to the DTM implementation.
         * Implementations that do not know whether attributes are of type ID or
         * not are expected to return <code>DTM.NULL</code>.
         *
         * <p>%REVIEW% Presumably IDs are still scoped to a single document,
         * and this operation searches only within a single document, right?
         * Wouldn't want collisions between DTMs in the same process.</p>
         *
         * <p>
         *  返回<code> Element </code>,其<code> ID </code>由<code> elementId </code>给出。
         * 如果没有这样的元素,返回<code> DTM.NULL </code>。如果多个元素具有此<code> ID </code>,则不定义行为。
         * 属性(包括名称为"ID"的属性)不是类型ID,除非由DTM实现的DTD /模式信息如此定义。不知道属性是否为ID类型的实现应该返回<code> DTM.NULL </code>。
         * 
         * <p>％REVIEW％推测ID仍然限于单个文档,此操作仅在单个文档中搜索,对吗?不希望在同一过程中DTM之间发生冲突。</p>
         * 
         * 
         * @param elementId The unique <code>id</code> value for an element.
         * @return The handle of the matching element.
         */
        public int getElementById(String elementId) {return 0;}

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
         * pushed up to a higher level of our application. (Note that DOM Level
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
         *  XML处理器可以选择使用系统标识符(如果提供了一个)来解析实体,而不是公共标识符中的URI。细节取决于处理器,我们将不得不支持某种形式的插件解析器来正确处理这些。
         * 目前,我们只返回系统标识符(如果存在),并希望它是一个可用的URI,或者我们的调用者可以将它映射到一个。 TODO：Resolve Public Identifiers ...或考虑更改函数名称。
         * <p>
         *  如果我们找到一个相对URI引用,XML期望它根据文档的基本URI解析。 DOM不为我们这样做,并不完全清楚是否应该在这里做;目前已经上升到我们的应用程序的更高水平。
         *  (请注意,DOM级别1不存储文档的基本URI。)TODO：考虑解析相对URI。
         * <p>
         *  (DOM的声明"一个XML处理器可能选择在结构模型被传递给DOM之前完全展开实体"仅指解析的实体,而不是解析的,因此不影响这个函数)。
         * 
         * 
         * @param name A string containing the Entity Name of the unparsed
         * entity.
         *
         * @return String containing the URI of the Unparsed Entity, or an
         * empty string if no such entity exists.
         */
        public String getUnparsedEntityURI(String name) {return null;}


        // ============== Boolean methods ================

        /**
         * Return true if the xsl:strip-space or xsl:preserve-space was processed
         * during construction of the DTM document.
         *
         * <p>%REVEIW% Presumes a 1:1 mapping from DTM to Document, since
         * we aren't saying which Document to query...?</p>
         * <p>
         * 如果在构建DTM文档期间处理了xsl：strip-space或xsl：preserve-space,则返回true。
         * 
         *  <p>％REVEIW％确定从DTM到文档的1：1映射,因为我们不是说要查询哪个文档...?</p>
         * 
         */
        public boolean supportsPreStripping() {return false;}

        /**
         * Figure out whether nodeHandle2 should be considered as being later
         * in the document than nodeHandle1, in Document Order as defined
         * by the XPath model. This may not agree with the ordering defined
         * by other XML applications.
         * <p>
         * There are some cases where ordering isn't defined, and neither are
         * the results of this function -- though we'll generally return true.
         *
         * TODO: Make sure this does the right thing with attribute nodes!!!
         *
         * <p>
         *  在XPath模型定义的文档顺序中,确定nodeHandle2是否应该被认为是文档中的晚于nodeHandle1。这可能与其他XML应用程序定义的顺序不一致。
         * <p>
         *  有些情况下,没有定义顺序,并且这个函数的结果也没有定义,尽管我们通常返回true。
         * 
         *  TODO：确保这做正确的事情与属性节点！
         * 
         * 
         * @param nodeHandle1 DOM Node to perform position comparison on.
         * @param nodeHandle2 DOM Node to perform position comparison on .
         *
         * @return false if node2 comes before node1, otherwise return true.
         * You can think of this as
         * <code>(node1.documentOrderPosition &lt;= node2.documentOrderPosition)</code>.
         */
        public boolean isNodeAfter(int nodeHandle1, int nodeHandle2) {return false;}

        /**
         *     2. [element content whitespace] A boolean indicating whether the
         *        character is white space appearing within element content (see [XML],
         *        2.10 "White Space Handling"). Note that validating XML processors are
         *        required by XML 1.0 to provide this information. If there is no
         *        declaration for the containing element, this property has no value for
         *        white space characters. If no declaration has been read, but the [all
         *        declarations processed] property of the document information item is
         *        false (so there may be an unread declaration), then the value of this
         *        property is unknown for white space characters. It is always false for
         *        characters that are not white space.
         *
         * <p>
         *  2. [元素内容空白]指示元素内容中是否出现空格的布尔(参见[XML],2.10"空白处理")。请注意,XML 1.0需要验证XML处理器以提供此信息。
         * 如果对于contains元素没有声明,则此属性没有空格字符的值。
         * 如果没有读取任何声明,但文档信息项的[all declarations processed]属性为false(因此可能存在未读取的声明),则此属性的值对于空格字符是未知的。
         * 对于不是空格的字符,它总是false。
         * 
         * 
         * @param nodeHandle the node ID.
         * @return <code>true</code> if the character data is whitespace;
         *         <code>false</code> otherwise.
         */
        public boolean isCharacterElementContentWhitespace(int nodeHandle) {return false;}

        /**
         *    10. [all declarations processed] This property is not strictly speaking
         *        part of the infoset of the document. Rather it is an indication of
         *        whether the processor has read the complete DTD. Its value is a
         *        boolean. If it is false, then certain properties (indicated in their
         *        descriptions below) may be unknown. If it is true, those properties
         *        are never unknown.
         *
         * <p>
         * 10. [所有声明已处理]此属性不严格地说是文档信息集的一部分。而是它是处理器是否已读取完整DTD的指示。它的值是一个布尔值。如果它是假的,那么某些属性(在下面的描述中指示)可能是未知的。
         * 如果它是真的,那些属性永远不会是未知的。
         * 
         * 
         * @param documentHandle A node handle that must identify a document.
         * @return <code>true</code> if all declarations were processed;
         *         <code>false</code> otherwise.
         */
        public boolean isDocumentAllDeclarationsProcessed(int documentHandle) {return false;}

        /**
         *     5. [specified] A flag indicating whether this attribute was actually
         *        specified in the start-tag of its element, or was defaulted from the
         *        DTD.
         *
         * <p>
         *  5. [specified]指示此属性是实际上在其元素的开始标签中指定的标志,还是来自DTD的标志。
         * 
         * 
         * @param attributeHandle the attribute handle
         * @return <code>true</code> if the attribute was specified;
         *         <code>false</code> if it was defaulted.
         */
        public boolean isAttributeSpecified(int attributeHandle) {return false;}

        // ========== Direct SAX Dispatch, for optimization purposes ========

        /**
         * Directly call the
         * characters method on the passed ContentHandler for the
         * string-value of the given node (see http://www.w3.org/TR/xpath#data-model
         * for the definition of a node's string-value). Multiple calls to the
         * ContentHandler's characters methods may well occur for a single call to
         * this method.
         *
         * <p>
         *  在传递的ContentHandler上直接调用给定节点的字符串值的字符方法(有关节点的字符串值的定义,请参阅http://www.w3.org/TR/xpath#data-model)。
         * 对ContentHandler的字符方法的多次调用很可能发生在对此方法的单个调用中。
         * 
         * 
         * @param nodeHandle The node ID.
         * @param ch A non-null reference to a ContentHandler.
         *
         * @throws org.xml.sax.SAXException
         */
        public void dispatchCharactersEvents(
                                                                                                                                                        int nodeHandle, org.xml.sax.ContentHandler ch, boolean normalize)
        throws org.xml.sax.SAXException {}

        /**
         * Directly create SAX parser events from a subtree.
         *
         * <p>
         *  从子树直接创建SAX解析器事件。
         * 
         * 
         * @param nodeHandle The node ID.
         * @param ch A non-null reference to a ContentHandler.
         *
         * @throws org.xml.sax.SAXException
         */

        public void dispatchToEvents(int nodeHandle, org.xml.sax.ContentHandler ch)
        throws org.xml.sax.SAXException {}

        /**
         * Return an DOM node for the given node.
         *
         * <p>
         *  返回给定节点的DOM节点。
         * 
         * 
         * @param nodeHandle The node ID.
         *
         * @return A node representation of the DTM node.
         */
        public org.w3c.dom.Node getNode(int nodeHandle)
        {
          return null;
        }

        // ==== Construction methods (may not be supported by some implementations!) =====
        // %REVIEW% jjk: These probably aren't the right API. At the very least
        // they need to deal with current-insertion-location and end-element
        // issues.

        /**
         * Append a child to the end of the child list of the current node. Please note that the node
         * is always cloned if it is owned by another document.
         *
         * <p>%REVIEW% "End of the document" needs to be defined more clearly.
         * Does it become the last child of the Document? Of the root element?</p>
         *
         * <p>
         *  将一个子节点附加到当前节点的子节点列表的末尾。请注意,如果节点由另一个文档所有,则它始终被克隆。
         * 
         *  <p>％REVIEW％"文档结束"需要更清晰地定义。它成为文档的最后一个孩子吗?根元素?</p>
         * 
         * 
         * @param newChild Must be a valid new node handle.
         * @param clone true if the child should be cloned into the document.
         * @param cloneDepth if the clone argument is true, specifies that the
         *                   clone should include all it's children.
         */
        public void appendChild(int newChild, boolean clone, boolean cloneDepth) {
                boolean sameDoc = ((newChild & DOCHANDLE_MASK) == m_docHandle);
                if (clone || !sameDoc) {

                } else {

                }
        }

        /**
         * Append a text node child that will be constructed from a string,
         * to the end of the document.
         *
         * <p>%REVIEW% "End of the document" needs to be defined more clearly.
         * Does it become the last child of the Document? Of the root element?</p>
         *
         * <p>
         *  将将从字符串构造的文本节点子句追加到文档的结尾。
         * 
         *  <p>％REVIEW％"文档结束"需要更清晰地定义。它成为文档的最后一个孩子吗?根元素?</p>
         * 
         * 
         * @param str Non-null reference to a string.
         */
        public void appendTextChild(String str) {
                // ###shs Think more about how this differs from createTextNode
          //%TBD%
        }


  //================================================================
  // ==== BUILDER methods ====
  // %TBD% jjk: SHOULD PROBABLY BE INLINED, unless we want to support
  // both SAX1 and SAX2 and share this logic between them.

  /** Append a text child at the current insertion point. Assumes that the
   * actual content of the text has previously been appended to the m_char
   * buffer (shared with the builder).
   *
   * <p>
   * 文本的实际内容先前已附加到m_char缓冲区(与构建器共享)。
   * 
   * 
   * @param m_char_current_start int Starting offset of node's content in m_char.
   * @param contentLength int Length of node's content in m_char.
   * */
  void appendTextChild(int m_char_current_start,int contentLength)
  {
    // create a Text Node
    // %TBD% may be possible to combine with appendNode()to replace the next chunk of code
    int w0 = TEXT_NODE;
    // W1: Parent
    int w1 = currentParent;
    // W2: Start position within m_char
    int w2 = m_char_current_start;
    // W3: Length of the full string
    int w3 = contentLength;

    int ourslot = appendNode(w0, w1, w2, w3);
    previousSibling = ourslot;
  }

  /** Append a comment child at the current insertion point. Assumes that the
   * actual content of the comment has previously been appended to the m_char
   * buffer (shared with the builder).
   *
   * <p>
   *  注释的实际内容以前已附加到m_char缓冲区(与构建器共享)。
   * 
   * 
   * @param m_char_current_start int Starting offset of node's content in m_char.
   * @param contentLength int Length of node's content in m_char.
   * */
  void appendComment(int m_char_current_start,int contentLength)
  {
    // create a Comment Node
    // %TBD% may be possible to combine with appendNode()to replace the next chunk of code
    int w0 = COMMENT_NODE;
    // W1: Parent
    int w1 = currentParent;
    // W2: Start position within m_char
    int w2 = m_char_current_start;
    // W3: Length of the full string
    int w3 = contentLength;

    int ourslot = appendNode(w0, w1, w2, w3);
    previousSibling = ourslot;
  }


  /** Append an Element child at the current insertion point. This
   * Element then _becomes_ the insertion point; subsequent appends
   * become its lastChild until an appendEndElement() call is made.
   *
   * Assumes that the symbols (local name, namespace URI and prefix)
   * have already been added to the pools
   *
   * Note that this _only_ handles the Element node itself. Attrs and
   * namespace nodes are unbundled in the ContentHandler layer
   * and appended separately.
   *
   * <p>
   *  然后元素_becomes_插入点;后续追加成为其lastChild,直到进行appendEndElement()调用。
   * 
   *  假定符号(本地名称,命名空间URI和前缀)已添加到池中
   * 
   *  注意,这个_only_处理Element节点本身。 attrs和命名空间节点在ContentHandler层中未绑定,并单独附加。
   * 
   * 
   * @param namespaceIndex: Index within the namespaceURI string pool
   * @param localNameIndex Index within the local name string pool
   * @param prefixIndex: Index within the prefix string pool
   * */
  void appendStartElement(int namespaceIndex,int localNameIndex, int prefixIndex)
  {
                // do document root node creation here on the first element, create nodes for
                // this element and its attributes, store the element, namespace, and attritute
                // name indexes to the nodes array, keep track of the current node and parent
                // element used

                // W0  High:  Namespace  Low:  Node Type
                int w0 = (namespaceIndex << 16) | ELEMENT_NODE;
                // W1: Parent
                int w1 = currentParent;
                // W2: Next  (initialized as 0)
                int w2 = 0;
                // W3: Tagname high: prefix Low: local name
                int w3 = localNameIndex | prefixIndex<<16;
                /**/System.out.println("set w3="+w3+" "+(w3>>16)+"/"+(w3&0xffff));

                //int ourslot = nodes.appendSlot(w0, w1, w2, w3);
                int ourslot = appendNode(w0, w1, w2, w3);
                currentParent = ourslot;
                previousSibling = 0;

                // set the root element pointer when creating the first element node
                if (m_docElement == NULL)
                        m_docElement = ourslot;
  }

  /** Append a Namespace Declaration child at the current insertion point.
   * Assumes that the symbols (namespace URI and prefix) have already been
   * added to the pools
   *
   * <p>
   *  // int ourslot = nodes.appendSlot(w0,w1,w2,w3); int ourslot = appendNode(w0,w1,w2,w3); currentParent
   *  = ourlot; previousSibling = 0;。
   * 
   *  //在创建第一个元素节点时设置根元素指针if(m_docElement == NULL)m_docElement = ourslot; }}
   * 
   *  / **在当前插入点处附加一个命名空间声明子节点。假定符号(命名空间URI和前缀)已经添加到池中
   * 
   * 
   * @param prefixIndex: Index within the prefix string pool
   * @param namespaceIndex: Index within the namespaceURI string pool
   * @param isID: If someone really insists on writing a bad DTD, it is
   * theoretically possible for a namespace declaration to also be declared
   * as being a node ID. I don't really want to support that stupidity,
   * but I'm not sure we can refuse to accept it.
   * */
  void appendNSDeclaration(int prefixIndex, int namespaceIndex,
                           boolean isID)
  {
    // %REVIEW% I'm assigning this node the "namespace for namespaces"
    // which the DOM defined. It is expected that the Namespace spec will
    // adopt this as official. It isn't strictly needed since it's implied
    // by the nodetype, but for now...

    // %REVIEW% Prefix need not be recorded; it's implied too. But
    // recording it might simplify the design.

    // %TBD% isID is not currently honored.

    final int namespaceForNamespaces=m_nsNames.stringToIndex("http://www.w3.org/2000/xmlns/");

    // W0  High:  Namespace  Low:  Node Type
    int w0 = NAMESPACE_NODE | (m_nsNames.stringToIndex("http://www.w3.org/2000/xmlns/")<<16);

    // W1:  Parent
    int w1 = currentParent;
    // W2:  CURRENTLY UNUSED -- It's next-sib in attrs, but we have no kids.
    int w2 = 0;
    // W3:  namespace name
    int w3 = namespaceIndex;
    // Add node
    int ourslot = appendNode(w0, w1, w2, w3);
    previousSibling = ourslot;  // Should attributes be previous siblings
    previousSiblingWasParent = false;
    return ;//(m_docHandle | ourslot);
  }

  /** Append an Attribute child at the current insertion
   * point.  Assumes that the symbols (namespace URI, local name, and
   * prefix) have already been added to the pools, and that the content has
   * already been appended to m_char. Note that the attribute's content has
   * been flattened into a single string; DTM does _NOT_ attempt to model
   * the details of entity references within attribute values.
   *
   * <p>
   *  点。假定符号(名称空间URI,本地名称和前缀)已添加到池中,并且内容已附加到m_char。请注意,属性的内容已经展平为单个字符串; DTM会_NOT_尝试对属性值中的实体引用的详细信息进行建模。
   * 
   * 
   * @param namespaceIndex int Index within the namespaceURI string pool
   * @param localNameIndex int Index within the local name string pool
   * @param prefixIndex int Index within the prefix string pool
   * @param isID boolean True if this attribute was declared as an ID
   * (for use in supporting getElementByID).
   * @param m_char_current_start int Starting offset of node's content in m_char.
   * @param contentLength int Length of node's content in m_char.
   * */
  void appendAttribute(int namespaceIndex, int localNameIndex, int prefixIndex,
                       boolean isID,
                       int m_char_current_start, int contentLength)
  {
    // %TBD% isID is not currently honored.

    // W0  High:  Namespace  Low:  Node Type
    int w0 = ATTRIBUTE_NODE | namespaceIndex<<16;

    // W1:  Parent
    int w1 = currentParent;
    // W2:  Next (not yet resolved)
    int w2 = 0;
    // W3:  Tagname high: prefix Low: local name
    int w3 = localNameIndex | prefixIndex<<16;
    /**/System.out.println("set w3="+w3+" "+(w3>>16)+"/"+(w3&0xffff));
    // Add node
    int ourslot = appendNode(w0, w1, w2, w3);
    previousSibling = ourslot;  // Should attributes be previous siblings

    // Attribute's content is currently appended as a Text Node

    // W0: Node Type
    w0 = TEXT_NODE;
    // W1: Parent
    w1 = ourslot;
    // W2: Start Position within buffer
    w2 = m_char_current_start;
    // W3: Length
    w3 = contentLength;
    appendNode(w0, w1, w2, w3);

    // Attrs are Parents
    previousSiblingWasParent = true;
    return ;//(m_docHandle | ourslot);
  }

  /**
   * This returns a stateless "traverser", that can navigate over an
   * XPath axis, though not in document order.
   *
   * <p>
   *  //添加节点int ourslot = appendNode(w0,w1,w2,w3); previousSibling = ourslot; //应该是以前的兄弟姐妹
   * 
   * //属性的内容当前作为文本节点附加
   * 
   *  // W0：节点类型w0 = TEXT_NODE; // W1：Parent w1 = ourslot; // W2：缓冲区中的起始位置w2 = m_char_current_start; // W3
   * ：Length w3 = contentLength; appendNode(w0,w1,w2,w3);。
   * 
   *  // Attrs are Parents previousSiblingWasParent = true; return; //(m_docHandle | ourslot); }}
   * 
   *  / **这返回一个无状态的"遍历器",可以在XPath轴上导航,虽然不是按照文档顺序。
   * 
   * 
   * @param axis One of Axes.ANCESTORORSELF, etc.
   *
   * @return A DTMAxisIterator, or null if the given axis isn't supported.
   */
  public DTMAxisTraverser getAxisTraverser(final int axis)
  {
    return null;
  }

  /**
   * This is a shortcut to the iterators that implement the
   * supported XPath axes (only namespace::) is not supported.
   * Returns a bare-bones iterator that must be initialized
   * with a start node (using iterator.setStartNode()).
   *
   * <p>
   *  这是实现支持的XPath轴的迭代器的快捷方式(仅命名空间：:)不受支持。返回一个裸体迭代器,它必须使用一个起始节点初始化(使用iterator.setStartNode())。
   * 
   * 
   * @param axis One of Axes.ANCESTORORSELF, etc.
   *
   * @return A DTMAxisIterator, or null if the given axis isn't supported.
   */
  public DTMAxisIterator getAxisIterator(final int axis)
  {
    // %TBD%
    return null;
  }

  /**
   * Get an iterator that can navigate over an XPath Axis, predicated by
   * the extended type ID.
   *
   *
   * <p>
   *  获取一个可以在XPath Axis上导航的迭代器,由扩展类型ID预测。
   * 
   * 
   * @param axis
   * @param type An extended type ID.
   *
   * @return A DTMAxisIterator, or null if the given axis isn't supported.
   */
  public DTMAxisIterator getTypedAxisIterator(final int axis, final int type)
  {
    // %TBD%
    return null;
  }


  /** Terminate the element currently acting as an insertion point. Subsequent
   * insertions will occur as the last child of this element's parent.
   * <p>
   *  插入将作为此元素父元素的最后一个子元素。
   * 
   * 
   * */
  void appendEndElement()
  {
    // pop up the stacks

    if (previousSiblingWasParent)
      nodes.writeEntry(previousSibling, 2, NULL);

    // Pop parentage
    previousSibling = currentParent;
    nodes.readSlot(currentParent, gotslot);
    currentParent = gotslot[1] & 0xFFFF;

    // The element just being finished will be
    // the previous sibling for the next operation
    previousSiblingWasParent = true;

    // Pop a level of namespace table
    // namespaceTable.removeLastElem();
  }

  /**  Starting a new document. Perform any resets/initialization
   * not already handled.
   * <p>
   *  尚未处理。
   * 
   * 
   * */
  void appendStartDocument()
  {

    // %TBD% reset slot 0 to indicate ChunkedIntArray reuse or wait for
    //       the next initDocument().
    m_docElement = NULL;         // reset nodeHandle to the root of the actual dtm doc content
    initDocument(0);
  }

  /**  All appends to this document have finished; do whatever final
   * cleanup is needed.
   * <p>
   *  需要清理。
   * 
   * 
   * */
  void appendEndDocument()
  {
    done = true;
    // %TBD% may need to notice the last slot number and slot count to avoid
    // residual data from provious use of this DTM
  }

  /**
   * For the moment all the run time properties are ignored by this
   * class.
   *
   * <p>
   *  目前所有的运行时属性都被这个类忽略。
   * 
   * 
   * @param property a <code>String</code> value
   * @param value an <code>Object</code> value
   */
  public void setProperty(String property, Object value)
  {
  }

  /**
   * Source information is not handled yet, so return
   * <code>null</code> here.
   *
   * <p>
   *  源信息尚未处理,因此在此返回<code> null </code>。
   * 
   * 
   * @param node an <code>int</code> value
   * @return null
   */
  public SourceLocator getSourceLocatorFor(int node)
  {
    return null;
  }


  /**
   * A dummy routine to satisify the abstract interface. If the DTM
   * implememtation that extends the default base requires notification
   * of registration, they can override this method.
   * <p>
   *  满足抽象接口的虚拟程序。如果扩展默认基数的DTM实现需要注册通知,则它们可以覆盖此方法。
   * 
   */
   public void documentRegistration()
   {
   }

  /**
   * A dummy routine to satisify the abstract interface. If the DTM
   * implememtation that extends the default base requires notification
   * when the document is being released, they can override this method
   * <p>
   *  满足抽象接口的虚拟程序。如果扩展默认基础的DTM实现需要在文档释放时通知,则它们可以覆盖此方法
   * 
   */
   public void documentRelease()
   {
   }

   /**
    * Migrate a DTM built with an old DTMManager to a new DTMManager.
    * After the migration, the new DTMManager will treat the DTM as
    * one that is built by itself.
    * This is used to support DTM sharing between multiple transformations.
    * <p>
    * 将使用旧DTMManager构建的DTM迁移到新的DTMManager。迁移后,新的DTMManager会将DTM视为由其自身构建的DTM。这用于支持多个转换之间的DTM共享。
    * 
    * @param manager the DTMManager
    */
   public void migrateTo(DTMManager manager)
   {
   }

}
