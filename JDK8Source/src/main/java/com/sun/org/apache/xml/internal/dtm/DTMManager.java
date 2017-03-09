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
 * $Id: DTMManager.java,v 1.2.4.1 2005/09/15 08:14:54 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMManager.java,v 1.2.4.1 2005/09/15 08:14:54 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xml.internal.utils.XMLStringFactory;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;

/**
 * A DTMManager instance can be used to create DTM and
 * DTMIterator objects, and manage the DTM objects in the system.
 *
 * <p>The system property that determines which Factory implementation
 * to create is named "com.sun.org.apache.xml.internal.utils.DTMFactory". This
 * property names a concrete subclass of the DTMFactory abstract
 *  class. If the property is not defined, a platform default is be used.</p>
 *
 * <p>An instance of this class <emph>must</emph> be safe to use across
 * thread instances.  It is expected that a client will create a single instance
 * of a DTMManager to use across multiple threads.  This will allow sharing
 * of DTMs across multiple processes.</p>
 *
 * <p>Note: this class is incomplete right now.  It will be pretty much
 * modeled after javax.xml.transform.TransformerFactory in terms of its
 * factory support.</p>
 *
 * <p>State: In progress!!</p>
 * <p>
 *  DTMManager实例可用于创建DTM和DTMIterator对象,并管理系统中的DTM对象。
 * 
 *  <p>确定要创建的Factory实现的系统属性命名为"com.sun.org.apache.xml.internal.utils.DTMFactory"。
 * 此属性命名DTMFactory抽象类的一个具体子类。如果未定义属性,则使用平台默认值。</p>。
 * 
 *  <p>此类<emph>的实例必须</emph>在线程实例中安全使用。期望客户端将创建DTMManager的单个实例以跨多个线程使用。这将允许跨多个进程共享DTM。</p>
 * 
 * <p>注意：此类目前不完整。它将在javax.xml.transform.TransformerFactory的工厂支持之后几乎被模型化。</p>
 * 
 *  <p>状态：正在进行中！</p>
 * 
 */
public abstract class DTMManager
{

  /**
   * Factory for creating XMLString objects.
   *  %TBD% Make this set by the caller.
   * <p>
   *  用于创建XMLString对象的工厂。 ％TBD％由调用者设置。
   * 
   */
  protected XMLStringFactory m_xsf = null;

  private boolean _useServicesMechanism;
  /**
   * Default constructor is protected on purpose.
   * <p>
   *  默认构造函数是有目的的保护。
   * 
   */
  protected DTMManager(){}

  /**
   * Get the XMLStringFactory used for the DTMs.
   *
   *
   * <p>
   *  获取用于DTM的XMLStringFactory。
   * 
   * 
   * @return a valid XMLStringFactory object, or null if it hasn't been set yet.
   */
  public XMLStringFactory getXMLStringFactory()
  {
    return m_xsf;
  }

  /**
   * Set the XMLStringFactory used for the DTMs.
   *
   *
   * <p>
   *  设置用于DTM的XMLStringFactory。
   * 
   * 
   * @param xsf a valid XMLStringFactory object, should not be null.
   */
  public void setXMLStringFactory(XMLStringFactory xsf)
  {
    m_xsf = xsf;
  }

  /**
   * Obtain a new instance of a <code>DTMManager</code>.
   * This static method creates a new factory instance
   * using the default <code>DTMManager</code> implementation, which is
   * <code>com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault</code>.
   * </li>
   * </ul>
   *
   * Once an application has obtained a reference to a <code>
   * DTMManager</code> it can use the factory to configure
   * and obtain parser instances.
   *
   * <p>
   *  获取<code> DTMManager </code>的新实例。
   * 这个静态方法使用默认的<code> DTMManager </code>实现创建一个新的工厂实例,它是<code> com.sun.org.apache.xml.internal.dtm.ref.DTM
   * ManagerDefault </code>。
   *  获取<code> DTMManager </code>的新实例。
   * </li>
   * </ul>
   * 
   *  一旦应用程序获得对<code> DTMManager </code>的引用,它就可以使用工厂来配置和获取解析器实例。
   * 
   * 
   * @return new DTMManager instance, never null.
   *
   * @throws DTMConfigurationException
   * if the implementation is not available or cannot be instantiated.
   */
  public static DTMManager newInstance(XMLStringFactory xsf)
           throws DTMConfigurationException
  {
      final DTMManager factoryImpl = new com.sun.org.apache.xml.internal.dtm.ref.DTMManagerDefault();
      factoryImpl.setXMLStringFactory(xsf);

      return factoryImpl;
  }

  /**
   * Get an instance of a DTM, loaded with the content from the
   * specified source.  If the unique flag is true, a new instance will
   * always be returned.  Otherwise it is up to the DTMManager to return a
   * new instance or an instance that it already created and may be being used
   * by someone else.
   *
   * (More parameters may eventually need to be added for error handling
   * and entity resolution, and to better control selection of implementations.)
   *
   * <p>
   *  获取DTM的实例,加载来自指定源的内容。如果唯一标志为真,将始终返回一个新实例。否则,由DTMManager返回一个新的实例或者它已经创建的实例,并且可能被别人使用。
   * 
   *  (最终可能需要添加更多的参数用于错误处理和实体解析,以及更好地控制实现的选择。
   * 
   * 
   * @param source the specification of the source object, which may be null,
   *               in which case it is assumed that node construction will take
   *               by some other means.
   * @param unique true if the returned DTM must be unique, probably because it
   * is going to be mutated.
   * @param whiteSpaceFilter Enables filtering of whitespace nodes, and may
   *                         be null.
   * @param incremental true if the DTM should be built incrementally, if
   *                    possible.
   * @param doIndexing true if the caller considers it worth it to use
   *                   indexing schemes.
   *
   * @return a non-null DTM reference.
   */
  public abstract DTM getDTM(javax.xml.transform.Source source,
                             boolean unique, DTMWSFilter whiteSpaceFilter,
                             boolean incremental, boolean doIndexing);

  /**
   * Get the instance of DTM that "owns" a node handle.
   *
   * <p>
   *  获取"拥有"节点句柄的DTM的实例。
   * 
   * 
   * @param nodeHandle the nodeHandle.
   *
   * @return a non-null DTM reference.
   */
  public abstract DTM getDTM(int nodeHandle);

  /**
   * Given a W3C DOM node, try and return a DTM handle.
   * Note: calling this may be non-optimal.
   *
   * <p>
   *  给定一个W3C DOM节点,尝试并返回一个DTM句柄。注意：调用这可能是非最佳的。
   * 
   * 
   * @param node Non-null reference to a DOM node.
   *
   * @return a valid DTM handle.
   */
  public abstract int getDTMHandleFromNode(org.w3c.dom.Node node);

  /**
   * Creates a DTM representing an empty <code>DocumentFragment</code> object.
   * <p>
   *  创建表示空的<code> DocumentFragment </code>对象的DTM。
   * 
   * 
   * @return a non-null DTM reference.
   */
  public abstract DTM createDocumentFragment();

  /**
   * Release a DTM either to a lru pool, or completely remove reference.
   * DTMs without system IDs are always hard deleted.
   * State: experimental.
   *
   * <p>
   * 将DTM发布到lru池,或完全删除引用。无系统ID的DTM始终被硬删除。状态：实验。
   * 
   * 
   * @param dtm The DTM to be released.
   * @param shouldHardDelete True if the DTM should be removed no matter what.
   * @return true if the DTM was removed, false if it was put back in a lru pool.
   */
  public abstract boolean release(DTM dtm, boolean shouldHardDelete);

  /**
   * Create a new <code>DTMIterator</code> based on an XPath
   * <a href="http://www.w3.org/TR/xpath#NT-LocationPath>LocationPath</a> or
   * a <a href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
   *
   * <p>
   *  根据XPath <a href="http://www.w3.org/TR/xpath#NT-LocationPath> LocationPath </a>或<a href ="创建新的<code> 
   * DTMIterator </code> http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr </a>。
   * 
   * 
   * @param xpathCompiler ??? Somehow we need to pass in a subpart of the
   * expression.  I hate to do this with strings, since the larger expression
   * has already been parsed.
   *
   * @param pos The position in the expression.
   * @return The newly created <code>DTMIterator</code>.
   */
  public abstract DTMIterator createDTMIterator(Object xpathCompiler,
          int pos);

  /**
   * Create a new <code>DTMIterator</code> based on an XPath
   * <a href="http://www.w3.org/TR/xpath#NT-LocationPath>LocationPath</a> or
   * a <a href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
   *
   * <p>
   *  根据XPath <a href="http://www.w3.org/TR/xpath#NT-LocationPath> LocationPath </a>或<a href ="创建新的<code> 
   * DTMIterator </code> http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr </a>。
   * 
   * 
   * @param xpathString Must be a valid string expressing a
   * <a href="http://www.w3.org/TR/xpath#NT-LocationPath>LocationPath</a> or
   * a <a href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.
   *
   * @param presolver An object that can resolve prefixes to namespace URLs.
   *
   * @return The newly created <code>DTMIterator</code>.
   */
  public abstract DTMIterator createDTMIterator(String xpathString,
          PrefixResolver presolver);

  /**
   * Create a new <code>DTMIterator</code> based only on a whatToShow
   * and a DTMFilter.  The traversal semantics are defined as the
   * descendant access.
   * <p>
   * Note that DTMIterators may not be an exact match to DOM
   * NodeIterators. They are initialized and used in much the same way
   * as a NodeIterator, but their response to document mutation is not
   * currently defined.
   *
   * <p>
   *  仅基于whatToShow和DTMFilter创建一个新的<code> DTMIterator </code>。遍历语义被定义为后代访问。
   * <p>
   *  请注意,DTMIterators可能不是与DOM NodeIterators的完全匹配。它们以与NodeIterator大致相同的方式被初始化和使用,但是它们对文档变异的响应当前未被定义。
   * 
   * 
   * @param whatToShow This flag specifies which node types may appear in
   *   the logical view of the tree presented by the iterator. See the
   *   description of <code>NodeFilter</code> for the set of possible
   *   <code>SHOW_</code> values.These flags can be combined using
   *   <code>OR</code>.
   * @param filter The <code>NodeFilter</code> to be used with this
   *   <code>DTMFilter</code>, or <code>null</code> to indicate no filter.
   * @param entityReferenceExpansion The value of this flag determines
   *   whether entity reference nodes are expanded.
   *
   * @return The newly created <code>DTMIterator</code>.
   */
  public abstract DTMIterator createDTMIterator(int whatToShow,
          DTMFilter filter, boolean entityReferenceExpansion);

  /**
   * Create a new <code>DTMIterator</code> that holds exactly one node.
   *
   * <p>
   *  创建一个只包含一个节点的新<> DTMIterator </code>。
   * 
   * 
   * @param node The node handle that the DTMIterator will iterate to.
   *
   * @return The newly created <code>DTMIterator</code>.
   */
  public abstract DTMIterator createDTMIterator(int node);

  /* Flag indicating whether an incremental transform is desired */
  public boolean m_incremental = false;

  /*
   * Flag set by FEATURE_SOURCE_LOCATION.
   * This feature specifies whether the transformation phase should
   * keep track of line and column numbers for the input source
   * document.
   * <p>
   *  由FEATURE_SOURCE_LOCATION设置的标记。此功能指定转换阶段是否应跟踪输入源文档的行号和列号。
   * 
   */
  public boolean m_source_location = false;

  /**
   * Get a flag indicating whether an incremental transform is desired
   * <p>
   *  获取指示是否需要增量变换的标志
   * 
   * 
   * @return incremental boolean.
   *
   */
  public boolean getIncremental()
  {
    return m_incremental;
  }

  /**
   * Set a flag indicating whether an incremental transform is desired
   * This flag should have the same value as the FEATURE_INCREMENTAL feature
   * which is set by the TransformerFactory.setAttribut() method before a
   * DTMManager is created
   * <p>
   *  设置指示是否需要增量变换的标志此标志应具有与在创建DTMManager之前由TransformerFactory.setAttribut()方法设置的FEATURE_INCREMENTAL特征相同的值
   * 。
   * 
   * 
   * @param incremental boolean to use to set m_incremental.
   *
   */
  public void setIncremental(boolean incremental)
  {
    m_incremental = incremental;
  }

  /**
   * Get a flag indicating whether the transformation phase should
   * keep track of line and column numbers for the input source
   * document.
   * <p>
   * 获取指示转换阶段是否应记录输入源文档的行号和列号的标志。
   * 
   * 
   * @return source location boolean
   *
   */
  public boolean getSource_location()
  {
    return m_source_location;
  }

  /**
   * Set a flag indicating whether the transformation phase should
   * keep track of line and column numbers for the input source
   * document.
   * This flag should have the same value as the FEATURE_SOURCE_LOCATION feature
   * which is set by the TransformerFactory.setAttribut() method before a
   * DTMManager is created
   * <p>
   *  设置一个标志,指示变换阶段是否应跟踪输入源文档的行号和列号。
   * 此标志应具有与在创建DTMManager之前由TransformerFactory.setAttribut()方法设置的FEATURE_SOURCE_LOCATION特性相同的值。
   * 
   * 
   * @param sourceLocation boolean to use to set m_source_location
   */
  public void setSource_location(boolean sourceLocation){
    m_source_location = sourceLocation;
  }

    /**
     * Return the state of the services mechanism feature.
     * <p>
     *  返回服务机制功能的状态。
     * 
     */
    public boolean useServicesMechnism() {
        return _useServicesMechanism;
    }

    /**
     * Set the state of the services mechanism feature.
     * <p>
     *  设置服务机制功能的状态。
     * 
     */
    public void setServicesMechnism(boolean flag) {
        _useServicesMechanism = flag;
    }

  // -------------------- private methods --------------------

   /**
   * Temp debug code - this will be removed after we test everything
   * <p>
   *  Temp调试代码 - 这将在我们测试一切后删除
   * 
   */
  private static boolean debug;

  static
  {
    try
    {
      debug = SecuritySupport.getSystemProperty("dtm.debug") != null;
    }
    catch (SecurityException ex){}
  }

  /** This value, set at compile time, controls how many bits of the
   * DTM node identifier numbers are used to identify a node within a
   * document, and thus sets the maximum number of nodes per
   * document. The remaining bits are used to identify the DTM
   * document which contains this node.
   *
   * If you change IDENT_DTM_NODE_BITS, be sure to rebuild _ALL_ the
   * files which use it... including the IDKey testcases.
   *
   * (FuncGenerateKey currently uses the node identifier directly and
   * thus is affected when this changes. The IDKEY results will still be
   * _correct_ (presuming no other breakage), but simple equality
   * comparison against the previous "golden" files will probably
   * complain.)
   * <p>
   *  DTM节点标识符号用于标识文档内的节点,并且因此设置每个文档的最大节点数。其余位用于标识包含此节点的DTM文档。
   * 
   *  如果你改变IDENT_DTM_NODE_BITS,一定要重建_ALL_使用它的文件...包括IDKey测试用例。
   * 
   *  (FuncGenerateKey当前直接使用节点标识符,因此在更改时会受到影响。IDKEY结果仍然是_correct_(假设没有其他破坏),但是与以前的"黄金"文件的简单等式比较可能会抱怨。
   * 
   * 
   * */
  public static final int IDENT_DTM_NODE_BITS = 16;


  /** When this bitmask is ANDed with a DTM node handle number, the result
   * is the low bits of the node's index number within that DTM. To obtain
   * the high bits, add the DTM ID portion's offset as assigned in the DTM
   * Manager.
   * <p>
   *  是该DTM中节点索引号的低位。要获得高位,请添加DTM ID部分的偏移量,如DTM管理器中分配的。
   * 
   */
  public static final int IDENT_NODE_DEFAULT = (1<<IDENT_DTM_NODE_BITS)-1;


  /** When this bitmask is ANDed with a DTM node handle number, the result
   * is the DTM's document identity number.
   * <p>
   *  是DTM的文档标识号。
   * 
   */
  public static final int IDENT_DTM_DEFAULT = ~IDENT_NODE_DEFAULT;

  /** This is the maximum number of DTMs available.  The highest DTM is
    * one less than this.
    * <p>
    *  一个小于这个。
    * 
   */
  public static final int IDENT_MAX_DTMS = (IDENT_DTM_DEFAULT >>> IDENT_DTM_NODE_BITS) + 1;


  /**
   * %TBD% Doc
   *
   * <p>
   *  ％TBD％Doc
   * 
   * 
   * NEEDSDOC @param dtm
   *
   * NEEDSDOC ($objectName$) @return
   */
  public abstract int getDTMIdentity(DTM dtm);

  /**
   * %TBD% Doc
   *
   * <p>
   *  ％TBD％Doc
   * 
   * 
   * NEEDSDOC ($objectName$) @return
   */
  public int getDTMIdentityMask()
  {
    return IDENT_DTM_DEFAULT;
  }

  /**
   * %TBD% Doc
   *
   * <p>
   *  ％TBD％Doc
   * 
   * 
   * NEEDSDOC ($objectName$) @return
   */
  public int getNodeIdentityMask()
  {
    return IDENT_NODE_DEFAULT;
  }

    //
    // Classes
    //

    /**
     * A configuration error.
     * Originally in ObjectFactory. This is the only portion used in this package
     * <p>
     * 配置错误。最初在ObjectFactory。这是此包中使用的唯一部分
     * 
     */
    static class ConfigurationError
        extends Error {
                static final long serialVersionUID = 5122054096615067992L;
        //
        // Data
        //

        /** Exception. */
        private Exception exception;

        //
        // Constructors
        //

        /**
         * Construct a new instance with the specified detail string and
         * exception.
         * <p>
         *  使用指定的明细字符串和异常构造新实例。
         */
        ConfigurationError(String msg, Exception x) {
            super(msg);
            this.exception = x;
        } // <init>(String,Exception)

        //
        // Public methods
        //

        /** Returns the exception associated to this error. */
        Exception getException() {
            return exception;
        } // getException():Exception

    } // class ConfigurationError

}
