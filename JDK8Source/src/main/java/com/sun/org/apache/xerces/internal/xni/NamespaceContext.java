/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Represents an interface to query namespace information.
 * <p>
 * The prefix and namespace must be identical references for equal strings, thus
 * <p>
 *  表示用于查询命名空间信息的接口。
 * <p>
 *  前缀和命名空间必须是相等的字符串的相同引用,因此
 * 
 * 
 * each string should be internalized (@see String.intern())
 * or added to the <code>SymbolTable</code>
 *
 * @see <a href="../../../../../xerces2/com/sun/org/apache/xerces/internal/util/SymbolTable.html">
 * com.sun.org.apache.xerces.internal.util.SymbolTable</a>
 *
 * @author Andy Clark, IBM
 *
 */
public interface NamespaceContext {

    //
    // Constants
    //

    /**
     * The XML Namespace ("http://www.w3.org/XML/1998/namespace"). This is
     * the Namespace URI that is automatically mapped to the "xml" prefix.
     * <p>
     *  XML命名空间("http://www.w3.org/XML/1998/namespace")。这是自动映射到"xml"前缀的命名空间URI。
     * 
     */
    public final static String XML_URI = "http://www.w3.org/XML/1998/namespace".intern();

    /**
     * XML Information Set REC
     * all namespace attributes (including those named xmlns,
     * whose [prefix] property has no value) have a namespace URI of http://www.w3.org/2000/xmlns/
     * <p>
     *  XML信息集REC所有命名空间属性(包括那些名为xmlns,其[prefix]属性没有值)的命名空间URI的名称为http://www.w3.org/2000/xmlns/
     * 
     */
    public final static String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();

    //
    // NamespaceContext methods
    //

    /**
     * Start a new Namespace context.
     * <p>
     * A new context should be pushed at the beginning
     * of each XML element: the new context will automatically inherit
     * the declarations of its parent context, but it will also keep
     * track of which declarations were made within this context.
     * <p>
     *
     * <p>
     *  启动新的命名空间上下文。
     * <p>
     *  应该在每个XML元素的开始处推送新的上下文：新上下文将自动继承其父上下文的声明,但它还将跟踪在此上下文中做出的声明。
     * <p>
     * 
     * 
     * @see #popContext
     */
    public void pushContext();

   /**
     * Revert to the previous Namespace context.
     * <p>
     * The context should be popped at the end of each
     * XML element.  After popping the context, all Namespace prefix
     * mappings that were previously in force are restored.
     * <p>
     * Users must not attempt to declare additional Namespace
     * prefixes after popping a context, unless you push another
     * context first.
     *
     * <p>
     *  还原到以前的命名空间上下文。
     * <p>
     * 上下文应该弹出在每个XML元素的末尾。弹出上下文后,将恢复先前有效的所有命名空间前缀映射。
     * <p>
     *  用户不得在弹出上下文后尝试声明其他Namespace前缀,除非您先推送另一个上下文。
     * 
     * 
     * @see #pushContext
     */
    public void popContext();

    /**
     * Declare a Namespace prefix.
     * <p>
     * This method declares a prefix in the current Namespace
     * context; the prefix will remain in force until this context
     * is popped, unless it is shadowed in a descendant context.
     * <p>
     * Note that to declare a default Namespace, use the empty string.
     * The prefixes "xml" and "xmlns" can't be rebound.
     * <p>
     * Note that you must <em>not</em> declare a prefix after
     * you've pushed and popped another Namespace.
     *
     * <p>
     *  声明一个命名空间前缀。
     * <p>
     *  此方法在当前命名空间上下文中声明一个前缀;该前缀将保持有效,直到该上下文被弹出,除非它在后代上下文中被遮蔽。
     * <p>
     *  注意,要声明默认的Namespace,请使用空字符串。前缀"xml"和"xmlns"不能重新绑定。
     * <p>
     *  请注意,您必须<em>不要</em>在推送并弹出另一个命名空间后声明一个前缀。
     * 
     * 
     * @param prefix The prefix to declare, or null for the empty
     *        string.
     * @param uri The Namespace URI to associate with the prefix.
     *
     * @return true if the prefix was legal, false otherwise
     *
     * @see #getURI
     * @see #getDeclaredPrefixAt
     */
    public boolean declarePrefix(String prefix, String uri);


    /**
     * Look up a prefix and get the currently-mapped Namespace URI.
     * <p>
     * This method looks up the prefix in the current context. If no mapping
     * is found, this methods will continue lookup in the parent context(s).
     * Use the empty string ("") for the default Namespace.
     *
     * <p>
     *  查找前缀并获取当前映射的命名空间URI。
     * <p>
     *  此方法在当前上下文中查找前缀。如果没有找到映射,这些方法将继续在父上下文中查找。对默认的命名空间使用空字符串("")。
     * 
     * 
     * @param prefix The prefix to look up.
     *
     * @return The associated Namespace URI, or null if the prefix
     *         is undeclared in this context.
     */
    public String getURI(String prefix);

    /**
     * Look up a namespace URI and get one of the mapped prefix.
     * <p>
     * This method looks up the namespace URI in the current context.
     * If more than one prefix is currently mapped to the same URI,
     * this method will make an arbitrary selection
     * If no mapping is found, this methods will continue lookup in the
     * parent context(s).
     *
     * <p>
     *  查找命名空间URI并获取一个映射前缀。
     * <p>
     *  此方法在当前上下文中查找名称空间URI。如果多个前缀当前映射到同一个URI,则此方法将进行任意选择如果未找到映射,则此方法将继续在父上下文中查找。
     * 
     * 
     * @param uri The namespace URI to look up.
     *
     * @return One of the associated prefixes, or null if the uri
     *         does not map to any prefix.
     *
     * @see #getPrefix
     */
    public String getPrefix(String uri);


    /**
     * Return a count of locally declared prefixes, including
     * the default prefix if bound.
     * <p>
     *  返回本地声明的前缀的计数,包括默认前缀(如果绑定)。
     * 
     */
    public int getDeclaredPrefixCount();

    /**
     * Returns the prefix at the specified index in the current context.
     * <p>
     *  返回当前上下文中指定索引处的前缀。
     * 
     */
    public String getDeclaredPrefixAt(int index);

        /**
         * Return an enumeration of all prefixes whose declarations are active
     * in the current context. This includes declarations from parent contexts
     * that have not been overridden.
     * <p>
     * 返回其声明在当前上下文中处于活动状态的所有前缀的枚举。这包括来自父上下文的尚未被重写的声明。
     * 
     * 
         * @return Enumeration
         */
    public Enumeration getAllPrefixes();

    /**
     * Reset this Namespace support object for reuse.
     *
     * <p>It is necessary to invoke this method before reusing the
     * Namespace support object for a new session.</p>
     *
     * <p>Note that implementations of this method need to ensure that
     * the declaration of the prefixes "xmlns" and "xml" are available.</p>
     * <p>
     *  重置此命名空间支持对象以供重复使用。
     * 
     *  <p>在重新使用新会话的命名空间支持对象之前,必须调用此方法。</p>
     */
    public void reset();


} // interface NamespaceContext
