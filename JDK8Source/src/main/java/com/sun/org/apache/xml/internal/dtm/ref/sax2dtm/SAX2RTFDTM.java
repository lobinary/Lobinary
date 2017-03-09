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
 * $Id: SAX2RTFDTM.java,v 1.2.4.1 2005/09/15 08:15:13 suresh_emailid Exp $
 * <p>
 *  $ Id：SAX2RTFDTM.java,v 1.2.4.1 2005/09/15 08:15:13 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref.sax2dtm;

import javax.xml.transform.Source;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xml.internal.dtm.DTMWSFilter;
import com.sun.org.apache.xml.internal.utils.IntStack;
import com.sun.org.apache.xml.internal.utils.IntVector;
import com.sun.org.apache.xml.internal.utils.StringVector;
import com.sun.org.apache.xml.internal.utils.XMLStringFactory;

import org.xml.sax.SAXException;

/**
 * This is a subclass of SAX2DTM which has been modified to meet the needs of
 * Result Tree Frameworks (RTFs). The differences are:
 *
 * 1) Multiple XML trees may be appended to the single DTM. This means
 * that the root node of each document is _not_ node 0. Some code has
 * had to be deoptimized to support this mode of operation, and an
 * explicit mechanism for obtaining the Node Handle of the root node
 * has been provided.
 *
 * 2) A stack of these documents is maintained, allowing us to "tail-prune" the
 * most recently added trees off the end of the DTM as stylesheet elements
 * (and thus variable contexts) are exited.
 *
 * PLEASE NOTE that this class may be _heavily_ dependent upon the
 * internals of the SAX2DTM superclass, and must be maintained in
 * parallel with that code.  Arguably, they should be conditionals
 * within a single class... but they have deen separated for
 * performance reasons. (In fact, one could even argue about which is
 * the superclass and which is the subclass; the current arrangement
 * is as much about preserving stability of existing code during
 * development as anything else.)
 *
 * %REVIEW% In fact, since the differences are so minor, I think it
 * may be possible/practical to fold them back into the base
 * SAX2DTM. Consider that as a future code-size optimization.
 * <p>
 *  这是SAX2DTM的子类,已经修改以满足结果树框架(RTF)的需要。差异是：
 * 
 *  1)多个XML树可以附加到单个DTM。这意味着每个文档的根节点是_not_节点0.一些代码不得不去最佳化以支持这种操作模式,并且已经提供了用于获得根节点的节点句柄的显式机制。
 * 
 *  2)维护这些文档的堆栈,使得当结束样式表元素(因此变量上下文)退出时,可以"尾剪除(tail-prune)"最后添加的树。
 * 
 * 请注意,这个类可以_heavily_依赖于SAX2DTM超类的内部,并且必须与该代码并行维护。可以说,它们应该是单个类中的条件...但是出于性能原因它们有deen分隔。
 *  (事实上​​,人们甚至可以争辩哪个是超类,哪个是子类;当前的安排是在​​开发过程中保持现有代码的稳定性,就像其他任何东西一样。)。
 * 
 *  ％REVIEW％事实上,由于差异很小,我认为将它们折回到基本SAX2DTM中是可能的/实际的。考虑作为未来的代码大小优化。
 * 
 * 
 * */
public class SAX2RTFDTM extends SAX2DTM
{
  /** Set true to monitor SAX events and similar diagnostic info. */
  private static final boolean DEBUG = false;

  /** Most recently started Document, or null if the DTM is empty.  */
  private int m_currentDocumentNode=NULL;

  /** Tail-pruning mark: Number of nodes in use */
  IntStack mark_size=new IntStack();
  /** Tail-pruning mark: Number of data items in use */
  IntStack mark_data_size=new IntStack();
  /** Tail-pruning mark: Number of size-of-data fields in use */
  IntStack mark_char_size=new IntStack();
  /** Tail-pruning mark: Number of dataOrQName slots in use */
  IntStack mark_doq_size=new IntStack();
  /** Tail-pruning mark: Number of namespace declaration sets in use
   * %REVIEW% I don't think number of NS sets is ever different from number
   * of NS elements. We can probabably reduce these to a single stack and save
   * some storage.
   * <p>
   *  ％REVIEW％我不认为NS集合的数量与NS元素的数量不同。我们可能可能将这些减少到单个堆栈,并节省一些存储。
   * 
   * 
   * */
  IntStack mark_nsdeclset_size=new IntStack();
  /** Tail-pruning mark: Number of naespace declaration elements in use
   * %REVIEW% I don't think number of NS sets is ever different from number
   * of NS elements. We can probabably reduce these to a single stack and save
   * some storage.
   * <p>
   *  ％REVIEW％我不认为NS集合的数量与NS元素的数量不同。我们可以将这些减少到单个堆栈,并节省一些存储。
   * 
   */
  IntStack mark_nsdeclelem_size=new IntStack();

  /**
   * Tail-pruning mark:  initial number of nodes in use
   * <p>
   *  尾修剪标记：正在使用的初始节点数
   * 
   */
  int m_emptyNodeCount;

  /**
   * Tail-pruning mark:  initial number of namespace declaration sets
   * <p>
   *  尾修剪标记：命名空间声明集的初始数
   * 
   */
  int m_emptyNSDeclSetCount;

  /**
   * Tail-pruning mark:  initial number of namespace declaration elements
   * <p>
   *  尾修剪标记：命名空间声明元素的初始数
   * 
   */
  int m_emptyNSDeclSetElemsCount;

  /**
   * Tail-pruning mark:  initial number of data items in use
   * <p>
   *  尾修剪标记：正在使用的数据项的初始数
   * 
   */
  int m_emptyDataCount;

  /**
   * Tail-pruning mark:  initial number of characters in use
   * <p>
   *  尾修剪标记：使用中的初始字符数
   * 
   */
  int m_emptyCharsCount;

  /**
   * Tail-pruning mark:  default initial number of dataOrQName slots in use
   * <p>
   *  尾修剪标记：正在使用的dataOrQName槽的默认初始数
   * 
   */
  int m_emptyDataQNCount;

  public SAX2RTFDTM(DTMManager mgr, Source source, int dtmIdentity,
                 DTMWSFilter whiteSpaceFilter,
                 XMLStringFactory xstringfactory,
                 boolean doIndexing)
  {
    super(mgr, source, dtmIdentity, whiteSpaceFilter,
          xstringfactory, doIndexing);

    // NEVER track source locators for RTFs; they aren't meaningful. I think.
    // (If we did track them, we'd need to tail-prune these too.)
    //com.sun.org.apache.xalan.internal.processor.TransformerFactoryImpl.m_source_location;
    m_useSourceLocationProperty=false;
    m_sourceSystemId = (m_useSourceLocationProperty) ? new StringVector()
                                                     : null;
    m_sourceLine = (m_useSourceLocationProperty) ? new IntVector() : null;
    m_sourceColumn = (m_useSourceLocationProperty) ? new IntVector() : null;

    // Record initial sizes of fields that are pushed and restored
    // for RTF tail-pruning.  More entries can be popped than pushed, so
    // we need this to mark the primordial state of the DTM.
    m_emptyNodeCount = m_size;
    m_emptyNSDeclSetCount = (m_namespaceDeclSets == null)
                                 ? 0 : m_namespaceDeclSets.size();
    m_emptyNSDeclSetElemsCount = (m_namespaceDeclSetElements == null)
                                      ? 0 : m_namespaceDeclSetElements.size();
    m_emptyDataCount = m_data.size();
    m_emptyCharsCount = m_chars.size();
    m_emptyDataQNCount = m_dataOrQName.size();
  }

  /**
   * Given a DTM, find the owning document node. In the case of
   * SAX2RTFDTM, which may contain multiple documents, this returns
   * the <b>most recently started</b> document, or null if the DTM is
   * empty or no document is currently under construction.
   *
   * %REVIEW% Should we continue to report the most recent after
   * construction has ended? I think not, given that it may have been
   * tail-pruned.
   *
   * <p>
   * 给定一个DTM,找到拥有的文档节点。在SAX2RTFDTM(可能包含多个文档)的情况下,返回<b>最近启动的</b>文档,如果DTM为空或当前没有文档正在构建,则返回null。
   * 
   *  ％REVIEW％在施工结束后,我们是否应该继续报告最新信息?我认为不是,因为它可能是尾部修剪。
   * 
   * 
   *  @return int Node handle of Document node, or null if this DTM does not
   *  contain an "active" document.
   * */
  public int getDocument()
  {
    return makeNodeHandle(m_currentDocumentNode);
  }

  /**
   * Given a node handle, find the owning document node, using DTM semantics
   * (Document owns itself) rather than DOM semantics (Document has no owner).
   *
   * (I'm counting on the fact that getOwnerDocument() is implemented on top
   * of this call, in the superclass, to avoid having to rewrite that one.
   * Be careful if that code changes!)
   *
   * <p>
   *  给定一个节点句柄,找到拥有文档节点,使用DTM语义(Document拥有自己)而不是DOM语义(Document没有所有者)。
   * 
   *  (我指望的事实,getOwnerDocument()是实现在这个调用之上,在超类中,以避免重写那个。请小心,如果该代码改变！)
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return int Node handle of owning document
   */
  public int getDocumentRoot(int nodeHandle)
  {
    for (int id=makeNodeIdentity(nodeHandle); id!=NULL; id=_parent(id)) {
      if (_type(id)==DTM.DOCUMENT_NODE) {
        return makeNodeHandle(id);
      }
    }

    return DTM.NULL; // Safety net; should never happen
  }

  /**
   * Given a node identifier, find the owning document node.  Unlike the DOM,
   * this considers the owningDocument of a Document to be itself. Note that
   * in shared DTMs this may not be zero.
   *
   * <p>
   *  给定一个节点标识符,找到拥有的文档节点。与DOM不同,这里认为document的owningDocument本身就是。请注意,在共享的DTM中,这可能不为零。
   * 
   * 
   * @param nodeIdentifier the id of the starting node.
   * @return int Node identifier of the root of this DTM tree
   */
  protected int _documentRoot(int nodeIdentifier)
  {
    if(nodeIdentifier==NULL) return NULL;

    for (int parent=_parent(nodeIdentifier);
         parent!=NULL;
         nodeIdentifier=parent,parent=_parent(nodeIdentifier))
      ;

    return nodeIdentifier;
  }

  /**
   * Receive notification of the beginning of a new RTF document.
   *
   * %REVIEW% Y'know, this isn't all that much of a deoptimization. We
   * might want to consider folding the start/endDocument changes back
   * into the main SAX2DTM so we don't have to expose so many fields
   * (even as Protected) and carry the additional code.
   *
   * <p>
   *  接收新RTF文档开头的通知。
   * 
   *  ％REVIEW％我知道,这不是所有的去优化。我们可能需要考虑将start / endDocument变化折叠到主SAX2DTM中,所以我们不必暴露这么多字段(即使是受保护的),并携带附加的代码。
   * 
   * 
   * @throws SAXException Any SAX exception, possibly
   *            wrapping another exception.
   * @see org.xml.sax.ContentHandler#startDocument
   * */
  public void startDocument() throws SAXException
  {
    // Re-initialize the tree append process
    m_endDocumentOccured = false;
    m_prefixMappings = new java.util.Vector();
    m_contextIndexes = new IntStack();
    m_parents = new IntStack();

    m_currentDocumentNode=m_size;
    super.startDocument();
  }

  /**
   * Receive notification of the end of the document.
   *
   * %REVIEW% Y'know, this isn't all that much of a deoptimization. We
   * might want to consider folding the start/endDocument changes back
   * into the main SAX2DTM so we don't have to expose so many fields
   * (even as Protected).
   *
   * <p>
   *  接收文档结束的通知。
   * 
   *  ％REVIEW％我知道,这不是所有的去优化。我们可能需要考虑将start / endDocument更改折叠到主SAX2DTM中,所以我们不必暴露这么多字段(即使是受保护的)。
   * 
   * 
   * @throws SAXException Any SAX exception, possibly
   *            wrapping another exception.
   * @see org.xml.sax.ContentHandler#endDocument
   * */
  public void endDocument() throws SAXException
  {
    charactersFlush();

    m_nextsib.setElementAt(NULL,m_currentDocumentNode);

    if (m_firstch.elementAt(m_currentDocumentNode) == NOTPROCESSED)
      m_firstch.setElementAt(NULL,m_currentDocumentNode);

    if (DTM.NULL != m_previous)
      m_nextsib.setElementAt(DTM.NULL,m_previous);

    m_parents = null;
    m_prefixMappings = null;
    m_contextIndexes = null;

    m_currentDocumentNode= NULL; // no longer open
    m_endDocumentOccured = true;
  }


  /** "Tail-pruning" support for RTFs.
   *
   * This function pushes information about the current size of the
   * DTM's data structures onto a stack, for use by popRewindMark()
   * (which see).
   *
   * %REVIEW% I have no idea how to rewind m_elemIndexes. However,
   * RTFs will not be indexed, so I can simply panic if that case
   * arises. Hey, it works...
   * <p>
   * 此函数将有关DTM的数据结构的当前大小的信息推送到堆栈中,以供popRewindMark()(使用)使用。
   * 
   *  ％REVIEW％我不知道如何后退m_elemIndexes。然而,RTF不会被索引,所以我可以简单地恐慌,如果这种情况出现。嘿,它工作...
   * 
   * 
   * */
  public void pushRewindMark()
  {
    if(m_indexing || m_elemIndexes!=null)
      throw new java.lang.NullPointerException("Coding error; Don't try to mark/rewind an indexed DTM");

    // Values from DTMDefaultBase
    // %REVIEW% Can the namespace stack sizes ever differ? If not, save space!
    mark_size.push(m_size);
    mark_nsdeclset_size.push((m_namespaceDeclSets==null)
                                   ? 0
                                   : m_namespaceDeclSets.size());
    mark_nsdeclelem_size.push((m_namespaceDeclSetElements==null)
                                   ? 0
                                   : m_namespaceDeclSetElements.size());

    // Values from SAX2DTM
    mark_data_size.push(m_data.size());
    mark_char_size.push(m_chars.size());
    mark_doq_size.push(m_dataOrQName.size());
  }

  /** "Tail-pruning" support for RTFs.
   *
   * This function pops the information previously saved by
   * pushRewindMark (which see) and uses it to discard all nodes added
   * to the DTM after that time. We expect that this will allow us to
   * reuse storage more effectively.
   *
   * This is _not_ intended to be called while a document is still being
   * constructed -- only between endDocument and the next startDocument
   *
   * %REVIEW% WARNING: This is the first use of some of the truncation
   * methods.  If Xalan blows up after this is called, that's a likely
   * place to check.
   *
   * %REVIEW% Our original design for DTMs permitted them to share
   * string pools.  If there any risk that this might be happening, we
   * can _not_ rewind and recover the string storage. One solution
   * might to assert that DTMs used for RTFs Must Not take advantage
   * of that feature, but this seems excessively fragile. Another, much
   * less attractive, would be to just let them leak... Nah.
   *
   * <p>
   *  此函数弹出先前由pushRewindMark保存的信息(参见),并使用它来丢弃在此时间后添加到DTM的所有节点。我们期望这将允许我们更有效地重用存储。
   * 
   *  这是_not_打算在文档仍在构建时调用 - 仅在endDocument和下一个startDocument之间调用
   * 
   *  ％REVIEW％警告：这是第一次使用某些截断方法。如果Xalan在被调用之后爆炸,这是一个可能的地方检查。
   * 
   * @return true if and only if the pop completely emptied the
   * RTF. That response is used when determining how to unspool
   * RTF-started-while-RTF-open situations.
   * */
  public boolean popRewindMark()
  {
    boolean top=mark_size.empty();

    m_size=top ? m_emptyNodeCount : mark_size.pop();
    m_exptype.setSize(m_size);
    m_firstch.setSize(m_size);
    m_nextsib.setSize(m_size);
    m_prevsib.setSize(m_size);
    m_parent.setSize(m_size);

    m_elemIndexes=null;

    int ds= top ? m_emptyNSDeclSetCount : mark_nsdeclset_size.pop();
    if (m_namespaceDeclSets!=null) {
      m_namespaceDeclSets.setSize(ds);
    }

    int ds1= top ? m_emptyNSDeclSetElemsCount : mark_nsdeclelem_size.pop();
    if (m_namespaceDeclSetElements!=null) {
      m_namespaceDeclSetElements.setSize(ds1);
    }

    // Values from SAX2DTM - m_data always has a reserved entry
    m_data.setSize(top ? m_emptyDataCount : mark_data_size.pop());
    m_chars.setLength(top ? m_emptyCharsCount : mark_char_size.pop());
    m_dataOrQName.setSize(top ? m_emptyDataQNCount : mark_doq_size.pop());

    // Return true iff DTM now empty
    return m_size==0;
  }

  /** @return true if a DTM tree is currently under construction.
  /* <p>
  /* 
  /*  ％REVIEW％我们对DTM的原始设计允许他们共享字符串池。如果有任何风险,这可能发生,我们可以_not_后退和恢复字符串存储。
  /* 一个解决方案可能断言用于RTF的DTM不得利用该功能,但这似乎过于脆弱。另一个,更不吸引人,只是让他们泄漏... Nah。
  /* 
   * */
  public boolean isTreeIncomplete()
  {
    return !m_endDocumentOccured;
  }
}
