/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
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
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: NamespaceMappings.java,v 1.2.4.1 2005/09/15 08:15:19 suresh_emailid Exp $
 * <p>
 *  $ Id：NamespaceMappings.java,v 1.2.4.1 2005/09/15 08:15:19 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * This class keeps track of the currently defined namespaces. Conceptually the
 * prefix/uri/depth triplets are pushed on a stack pushed on a stack. The depth
 * indicates the nesting depth of the element for which the mapping was made.
 *
 * <p>For example:
 * <pre>
 * <chapter xmlns:p1="def">
 *   <paragraph xmlns:p2="ghi">
 *      <sentance xmlns:p3="jkl">
 *      </sentance>
 *    </paragraph>
 *    <paragraph xlmns:p4="mno">
 *    </paragraph>
 * </chapter>
 * </pre>
 *
 * When the <chapter> element is encounted the prefix "p1" associated with uri
 * "def" is pushed on the stack with depth 1.
 * When the first <paragraph> is encountered "p2" and "ghi" are pushed with
 * depth 2.
 * When the <sentance> is encountered "p3" and "jkl" are pushed with depth 3.
 * When </sentance> occurs the popNamespaces(3) will pop "p3"/"jkl" off the
 * stack.  Of course popNamespaces(2) would pop anything with depth 2 or
 * greater.
 *
 * So prefix/uri pairs are pushed and poped off the stack as elements are
 * processed.  At any given moment of processing the currently visible prefixes
 * are on the stack and a prefix can be found given a uri, or a uri can be found
 * given a prefix.
 *
 * This class is public only because it is used by Xalan. It is not a public API
 *
 * @xsl.usage internal
 * <p>
 *  此类跟踪当前定义的命名空间。从概念上讲,前缀/ uri /深度三元组被推送到栈上推栈。深度指示进行映射的元素的嵌套深度。
 * 
 *  <p>例如：
 * <pre>
 * <chapter xmlns:p1="def">
 * <paragraph xmlns:p2="ghi">
 * <sentance xmlns:p3="jkl">
 * </sentance>
 * </paragraph>
 * <paragraph xlmns:p4="mno">
 * </paragraph>
 * </chapter>
 * </pre>
 * 
 *  当<chapter>元素被计数时,与uri"def"相关联的前缀"p1"被推入具有深度1的堆栈。当遇到第一<段落>时,以深度2推送"p2"和"ghi"。
 * 遇到"p3"和"jkl"的深度为3.当发生</sentance>时,popNamespaces(3)会从堆栈中弹出"p3"/"jkl"。
 * 当然popNamespaces(2)会弹出深度2或更大的任何东西。
 * 
 * 因此,前缀/ uri对被推送并且在处理元素时从栈中弹出。在任何给定的处理时刻,当前可见的前缀在堆栈上,并且给定前缀可以找到前缀,或者给出前缀的uri。
 * 
 *  这个类是public的,因为它被Xalan使用。它不是一个公共API
 * 
 *  @ xsl.usage internal
 * 
 */
public class NamespaceMappings
{
    /**
     * This member is continually incremented when new prefixes need to be
     * generated. ("ns0"  "ns1" ...)
     * <p>
     *  当需要生成新的前缀时,该成员不断增加。 ("ns0""ns1"...)
     * 
     */
    private int count;

    /**
     * Each entry (prefix) in this hashmap points to a Stack of URIs
     * This maps a prefix (String) to a Stack of prefix mappings.
     * All mappings in that retrieved stack have the same prefix,
     * though possibly different URI's or depths. Such a stack must have
     * mappings at deeper depths push later on such a stack.  Mappings pushed
     * earlier on the stack will have smaller values for MappingRecord.m_declarationDepth.
     * <p>
     *  此hashmap中的每个条目(前缀)指向一堆URI。这将前缀(String)映射到一组前缀映射。在检索的堆栈中的所有映射具有相同的前缀,尽管可能不同的URI或深度。
     * 这样的堆栈必须具有在较深的深度的映射,然后在这样的堆栈上推送。先前在堆栈上推送的映射将具有较小的MappingRecord.m_declarationDepth值。
     * 
     */
    private HashMap m_namespaces = new HashMap();

    /**
     * The top of this stack contains the MapRecord
     * of the last declared a namespace.
     * Used to know how many prefix mappings to pop when leaving
     * the current element depth.
     * For every prefix mapping the current element depth is
     * pushed on this stack.
     * That way all prefixes pushed at the current depth can be
     * removed at the same time.
     * Used to ensure prefix/uri map scopes are closed correctly
     *
     * <p>
     *  这个堆栈的顶部包含最后一个声明的命名空间的MapRecord。用于知道当离开当前元素深度时弹出多少前缀映射。对于每个前缀映射,当前元素深度被推送到该堆栈上。
     * 这样,在当前深度推送的所有前缀可以同时被移除。用于确保前缀/ uri映射范围正确关闭。
     * 
     */
    private Stack m_nodeStack = new Stack();

    private static final String EMPTYSTRING = "";
    private static final String XML_PREFIX = "xml"; // was "xmlns"

    /**
     * Default constructor
     * <p>
     *  默认构造函数
     * 
     * 
     * @see java.lang.Object#Object()
     */
    public NamespaceMappings()
    {
        initNamespaces();
    }

    /**
     * This method initializes the namespace object with appropriate stacks
     * and predefines a few prefix/uri pairs which always exist.
     * <p>
     *  此方法使用适当的堆栈初始化命名空间对象,并预定义一些始终存在的前缀/ uri对。
     * 
     */
    private void initNamespaces()
    {


        // Define the default namespace (initially maps to "" uri)
        Stack stack;
        m_namespaces.put(EMPTYSTRING, stack = new Stack());
        stack.push(new MappingRecord(EMPTYSTRING,EMPTYSTRING,0));

        m_namespaces.put(XML_PREFIX, stack = new Stack());
        stack.push(new MappingRecord( XML_PREFIX,
            "http://www.w3.org/XML/1998/namespace",0));

        m_nodeStack.push(new MappingRecord(null,null,-1));

    }

    /**
     * Use a namespace prefix to lookup a namespace URI.
     *
     * <p>
     *  使用命名空间前缀来查找命名空间URI。
     * 
     * 
     * @param prefix String the prefix of the namespace
     * @return the URI corresponding to the prefix
     */
    public String lookupNamespace(String prefix)
    {
        final Stack stack = (Stack) m_namespaces.get(prefix);
        return stack != null && !stack.isEmpty() ?
            ((MappingRecord) stack.peek()).m_uri : null;
    }

    MappingRecord getMappingFromPrefix(String prefix) {
        final Stack stack = (Stack) m_namespaces.get(prefix);
        return stack != null && !stack.isEmpty() ?
            ((MappingRecord) stack.peek()) : null;
    }

    /**
     * Given a namespace uri, and the namespaces mappings for the
     * current element, return the current prefix for that uri.
     *
     * <p>
     * 给定一个命名空间uri和当前元素的命名空间映射,返回该uri的当前前缀。
     * 
     * 
     * @param uri the namespace URI to be search for
     * @return an existing prefix that maps to the given URI, null if no prefix
     * maps to the given namespace URI.
     */
    public String lookupPrefix(String uri)
    {
        String foundPrefix = null;
        Iterator<String> itr = m_namespaces.keySet().iterator();
        while (itr.hasNext()) {
            String prefix = itr.next();
            String uri2 = lookupNamespace(prefix);
            if (uri2 != null && uri2.equals(uri))
            {
                foundPrefix = prefix;
                break;
            }
        }
        return foundPrefix;
    }

    MappingRecord getMappingFromURI(String uri)
    {
        MappingRecord foundMap = null;
        Iterator<String> itr = m_namespaces.keySet().iterator();
        while (itr.hasNext())
        {
            String prefix = itr.next();
            MappingRecord map2 = getMappingFromPrefix(prefix);
            if (map2 != null && (map2.m_uri).equals(uri))
            {
                foundMap = map2;
                break;
            }
        }
        return foundMap;
    }

    /**
     * Undeclare the namespace that is currently pointed to by a given prefix
     * <p>
     *  Undeclare由给定前缀当前指向的命名空间
     * 
     */
    boolean popNamespace(String prefix)
    {
        // Prefixes "xml" and "xmlns" cannot be redefined
        if (prefix.startsWith(XML_PREFIX))
        {
            return false;
        }

        Stack stack;
        if ((stack = (Stack) m_namespaces.get(prefix)) != null)
        {
            stack.pop();
            return true;
        }
        return false;
    }

    /**
     * Declare a mapping of a prefix to namespace URI at the given element depth.
     * <p>
     *  在给定元素深度处声明前缀到命名空间URI的映射。
     * 
     * 
     * @param prefix a String with the prefix for a qualified name
     * @param uri a String with the uri to which the prefix is to map
     * @param elemDepth the depth of current declaration
     */
    boolean pushNamespace(String prefix, String uri, int elemDepth)
    {
        // Prefixes "xml" and "xmlns" cannot be redefined
        if (prefix.startsWith(XML_PREFIX))
        {
            return false;
        }

        Stack stack;
        // Get the stack that contains URIs for the specified prefix
        if ((stack = (Stack) m_namespaces.get(prefix)) == null)
        {
            m_namespaces.put(prefix, stack = new Stack());
        }

        if (!stack.empty() && uri.equals(((MappingRecord)stack.peek()).m_uri))
        {
            return false;
        }
        MappingRecord map = new MappingRecord(prefix,uri,elemDepth);
        stack.push(map);
        m_nodeStack.push(map);
        return true;
    }

    /**
     * Pop, or undeclare all namespace definitions that are currently
     * declared at the given element depth, or deepter.
     * <p>
     *  弹出或取消声明当前在给定元素depth或deepter处声明的所有命名空间定义。
     * 
     * 
     * @param elemDepth the element depth for which mappings declared at this
     * depth or deeper will no longer be valid
     * @param saxHandler The ContentHandler to notify of any endPrefixMapping()
     * calls.  This parameter can be null.
     */
    void popNamespaces(int elemDepth, ContentHandler saxHandler)
    {
        while (true)
        {
            if (m_nodeStack.isEmpty())
                return;
            MappingRecord map = (MappingRecord)(m_nodeStack.peek());
            int depth = map.m_declarationDepth;
            if (depth < elemDepth)
                return;
            /* the depth of the declared mapping is elemDepth or deeper
             * so get rid of it
             * <p>
             *  所以摆脱它
             * 
             */

            map = (MappingRecord) m_nodeStack.pop();
            final String prefix = map.m_prefix;
            popNamespace(prefix);
            if (saxHandler != null)
            {
                try
                {
                    saxHandler.endPrefixMapping(prefix);
                }
                catch (SAXException e)
                {
                    // not much we can do if they aren't willing to listen
                }
            }

        }
    }

    /**
     * Generate a new namespace prefix ( ns0, ns1 ...) not used before
     * <p>
     *  生成之前未使用的新命名空间前缀(ns0,ns1 ...)
     * 
     * 
     * @return String a new namespace prefix ( ns0, ns1, ns2 ...)
     */
    public String generateNextPrefix()
    {
        return "ns" + (count++);
    }


    /**
     * This method makes a clone of this object.
     *
     * <p>
     *  此方法将克隆此对象。
     */
    public Object clone() throws CloneNotSupportedException {
        NamespaceMappings clone = new NamespaceMappings();
        clone.m_nodeStack = (Stack) m_nodeStack.clone();
        clone.m_namespaces = (HashMap) m_namespaces.clone();
        clone.count = count;
        return clone;

    }

    final void reset()
    {
        this.count = 0;
        this.m_namespaces.clear();
        this.m_nodeStack.clear();
        initNamespaces();
    }

    class MappingRecord {
        final String m_prefix;  // the prefix
        final String m_uri;     // the uri
        // the depth of the element where declartion was made
        final int m_declarationDepth;
        MappingRecord(String prefix, String uri, int depth) {
            m_prefix = prefix;
            m_uri = uri;
            m_declarationDepth = depth;

        }
    }

}
