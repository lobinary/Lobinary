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
 * $Id: NamespaceSupport2.java,v 1.3 2005/09/28 13:49:20 pvedula Exp $
 * <p>
 *  $ Id：NamespaceSupport2.java,v 1.3 2005/09/28 13:49:20 pvedula Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Encapsulate Namespace tracking logic for use by SAX drivers.
 *
 * <p>This class is an attempt to rewrite the SAX NamespaceSupport
 * "helper" class for improved efficiency. It can be used to track the
 * namespace declarations currently in scope, providing lookup
 * routines to map prefixes to URIs and vice versa.</p>
 *
 * <p>ISSUE: For testing purposes, I've extended NamespaceSupport even
 * though I'm completely reasserting all behaviors and fields.
 * Wasteful.... But SAX did not put an interface under that object and
 * we seem to have written that SAX class into our APIs... and I don't
 * want to argue with it right now. </p>
 *
 * <p>
 *  封装SAX驱动程序使用的命名空间跟踪逻辑。
 * 
 *  <p>此类试图重写SAX NamespaceSupport"帮助器"类,以提高效率。它可以用于跟踪当前在作用域中的命名空间声明,提供查找例程以将前缀映射到URI,反之亦然。</p>
 * 
 *  <p>问题：为了测试的目的,我已经扩展了NamespaceSupport,尽管我完全重申所有的行为和领域。
 * 浪费....但是SAX没有在该对象下面放置一个接口,我们似乎已经将SAX类写入我们的API ...我现在不想争论。 </p>。
 * 
 * 
 * @see org.xml.sax.helpers.NamespaceSupport
 * */
public class NamespaceSupport2
    extends org.xml.sax.helpers.NamespaceSupport
{
    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    private Context2 currentContext; // Current point on the double-linked stack


    ////////////////////////////////////////////////////////////////////
    // Constants.
    ////////////////////////////////////////////////////////////////////


    /**
     * The XML Namespace as a constant.
     *
     * <p>This is the Namespace URI that is automatically mapped
     * to the "xml" prefix.</p>
     * <p>
     *  XML命名空间作为常量。
     * 
     * <p>这是自动映射到"xml"前缀的命名空间URI。</p>
     * 
     */
    public final static String XMLNS =
        "http://www.w3.org/XML/1998/namespace";


    ////////////////////////////////////////////////////////////////////
    // Constructor.
    ////////////////////////////////////////////////////////////////////


    /**
     * Create a new Namespace support object.
     * <p>
     *  创建一个新的命名空间支持对象。
     * 
     */
    public NamespaceSupport2 ()
    {
        reset();
    }


    ////////////////////////////////////////////////////////////////////
    // Context management.
    ////////////////////////////////////////////////////////////////////


    /**
     * Reset this Namespace support object for reuse.
     *
     * <p>It is necessary to invoke this method before reusing the
     * Namespace support object for a new session.</p>
     * <p>
     *  重置此命名空间支持对象以供重用。
     * 
     *  <p>在重新使用新会话的命名空间支持对象之前,必须调用此方法。</p>
     * 
     */
    public void reset ()
    {
        // Discarding the whole stack doesn't save us a lot versus
        // creating a new NamespaceSupport. Do we care, or should we
        // change this to just reset the root context?
        currentContext = new Context2(null);
        currentContext.declarePrefix("xml", XMLNS);
    }


    /**
     * Start a new Namespace context.
     *
     * <p>Normally, you should push a new context at the beginning
     * of each XML element: the new context will automatically inherit
     * the declarations of its parent context, but it will also keep
     * track of which declarations were made within this context.</p>
     *
     * <p>The Namespace support object always starts with a base context
     * already in force: in this context, only the "xml" prefix is
     * declared.</p>
     *
     * <p>
     *  启动新的命名空间上下文。
     * 
     *  <p>通常,您应该在每个XML元素的开头部分推送一个新的上下文：新的上下文将自动继承其父上下文的声明,但它还将跟踪在此上下文中作出的声明。</p >
     * 
     *  <p>命名空间支持对象始终以一个已经生效的基本上下文开始：在此上下文中,只声明了"xml"前缀。</p>
     * 
     * 
     * @see #popContext
     */
    public void pushContext ()
    {
        // JJK: Context has a parent pointer.
        // That means we don't need a stack to pop.
        // We may want to retain for reuse, but that can be done via
        // a child pointer.

        Context2 parentContext=currentContext;
        currentContext = parentContext.getChild();
        if (currentContext == null){
                currentContext = new Context2(parentContext);
            }
        else{
            // JJK: This will wipe out any leftover data
            // if we're reusing a previously allocated Context.
            currentContext.setParent(parentContext);
        }
    }


    /**
     * Revert to the previous Namespace context.
     *
     * <p>Normally, you should pop the context at the end of each
     * XML element.  After popping the context, all Namespace prefix
     * mappings that were previously in force are restored.</p>
     *
     * <p>You must not attempt to declare additional Namespace
     * prefixes after popping a context, unless you push another
     * context first.</p>
     *
     * <p>
     *  还原到以前的命名空间上下文。
     * 
     *  <p>通常,您应该在每个XML元素的结尾处弹出上下文。弹出上下文后,将恢复先前有效的所有命名空间前缀映射。</p>
     * 
     *  <p>您不得在弹出上下文后尝试声明其他命名空间前缀,除非您先推送另一个上下文。</p>
     * 
     * 
     * @see #pushContext
     */
    public void popContext ()
    {
        Context2 parentContext=currentContext.getParent();
        if(parentContext==null)
            throw new EmptyStackException();
        else
            currentContext = parentContext;
    }



    ////////////////////////////////////////////////////////////////////
    // Operations within a context.
    ////////////////////////////////////////////////////////////////////


    /**
     * Declare a Namespace prefix.
     *
     * <p>This method declares a prefix in the current Namespace
     * context; the prefix will remain in force until this context
     * is popped, unless it is shadowed in a descendant context.</p>
     *
     * <p>To declare a default Namespace, use the empty string.  The
     * prefix must not be "xml" or "xmlns".</p>
     *
     * <p>Note that you must <em>not</em> declare a prefix after
     * you've pushed and popped another Namespace.</p>
     *
     * <p>Note that there is an asymmetry in this library: while {@link
     * #getPrefix getPrefix} will not return the default "" prefix,
     * even if you have declared one; to check for a default prefix,
     * you have to look it up explicitly using {@link #getURI getURI}.
     * This asymmetry exists to make it easier to look up prefixes
     * for attribute names, where the default prefix is not allowed.</p>
     *
     * <p>
     *  声明一个命名空间前缀。
     * 
     *  <p>此方法在当前命名空间上下文中声明一个前缀;该前缀将一直有效,直到该上下文被弹出,除非它在后代上下文中被隐藏。</p>
     * 
     *  <p>要声明默认的命名空间,请使用空字符串。前缀不能是"xml"或"xmlns"。</p>
     * 
     *  <p>请注意,在您推送并弹出其他命名空间后,您必须<em>不要</em>声明一个前缀。</p>
     * 
     * <p>请注意,此库中存在不对称性：while {@link #getPrefix getPrefix}不会返回默认的""前缀,即使您已声明了前缀;要检查默认前缀,您必须使用{@link #getURI getURI}
     * 明确查找。
     * 这种不对称性使得更容易查找属性名称的前缀,其中不允许使用默认前缀。</p>。
     * 
     * 
     * @param prefix The prefix to declare, or null for the empty
     *        string.
     * @param uri The Namespace URI to associate with the prefix.
     * @return true if the prefix was legal, false otherwise
     * @see #processName
     * @see #getURI
     * @see #getPrefix
     */
    public boolean declarePrefix (String prefix, String uri)
    {
        if (prefix.equals("xml") || prefix.equals("xmlns")) {
            return false;
        } else {
            currentContext.declarePrefix(prefix, uri);
            return true;
        }
    }


    /**
     * Process a raw XML 1.0 name.
     *
     * <p>This method processes a raw XML 1.0 name in the current
     * context by removing the prefix and looking it up among the
     * prefixes currently declared.  The return value will be the
     * array supplied by the caller, filled in as follows:</p>
     *
     * <dl>
     * <dt>parts[0]</dt>
     * <dd>The Namespace URI, or an empty string if none is
     *  in use.</dd>
     * <dt>parts[1]</dt>
     * <dd>The local name (without prefix).</dd>
     * <dt>parts[2]</dt>
     * <dd>The original raw name.</dd>
     * </dl>
     *
     * <p>All of the strings in the array will be internalized.  If
     * the raw name has a prefix that has not been declared, then
     * the return value will be null.</p>
     *
     * <p>Note that attribute names are processed differently than
     * element names: an unprefixed element name will received the
     * default Namespace (if any), while an unprefixed element name
     * will not.</p>
     *
     * <p>
     *  处理原始XML 1.0名称。
     * 
     *  <p>此方法通过删除前缀并在当前声明的前缀中查找,在当前上下文中处理原始XML 1.0名称。返回值将是调用者提供的数组,填充如下：</p>
     * 
     * <dl>
     *  <dt> parts [0] </dt> <dd>命名空间URI或空字符串,如果没有使用。</dd> <dt> parts [1]无前缀)。
     * </dd> <dt> parts [2] </dt> <dd>原始原始名称。</dd>。
     * </dl>
     * 
     *  <p>数组中的所有字符串都将被内化。如果原始名称具有尚未声明的前缀,则返回值将为null。</p>
     * 
     *  <p>请注意,属性名称的处理方式与元素名称不同：不带前缀的元素名称将接收默认名称空间(如果有),而不带前缀的元素名称则不会。</p>
     * 
     * 
     * @param qName The raw XML 1.0 name to be processed.
     * @param parts A string array supplied by the caller, capable of
     *        holding at least three members.
     * @param isAttribute A flag indicating whether this is an
     *        attribute name (true) or an element name (false).
     * @return The supplied array holding three internalized strings
     *        representing the Namespace URI (or empty string), the
     *        local name, and the raw XML 1.0 name; or null if there
     *        is an undeclared prefix.
     * @see #declarePrefix
     * @see java.lang.String#intern */
    public String [] processName (String qName, String[] parts,
                                  boolean isAttribute)
    {
        String[] name=currentContext.processName(qName, isAttribute);
        if(name==null)
            return null;

        // JJK: This recopying is required because processName may return
        // a cached result. I Don't Like It. *****
        System.arraycopy(name,0,parts,0,3);
        return parts;
    }


    /**
     * Look up a prefix and get the currently-mapped Namespace URI.
     *
     * <p>This method looks up the prefix in the current context.
     * Use the empty string ("") for the default Namespace.</p>
     *
     * <p>
     *  查找前缀并获取当前映射的命名空间URI。
     * 
     *  <p>此方法在当前上下文中查找前缀。对默认命名空间使用空字符串("")。</p>
     * 
     * 
     * @param prefix The prefix to look up.
     * @return The associated Namespace URI, or null if the prefix
     *         is undeclared in this context.
     * @see #getPrefix
     * @see #getPrefixes
     */
    public String getURI (String prefix)
    {
        return currentContext.getURI(prefix);
    }


    /**
     * Return an enumeration of all prefixes currently declared.
     *
     * <p><strong>Note:</strong> if there is a default prefix, it will not be
     * returned in this enumeration; check for the default prefix
     * using the {@link #getURI getURI} with an argument of "".</p>
     *
     * <p>
     *  返回当前声明的所有前缀的枚举。
     * 
     * <p> <strong>注意：</strong>如果有默认前缀,则不会在此枚举中返回;请使用带有参数""的{@link #getURI getURI}检查默认前缀。</p>
     * 
     * 
     * @return An enumeration of all prefixes declared in the
     *         current context except for the empty (default)
     *         prefix.
     * @see #getDeclaredPrefixes
     * @see #getURI
     */
    public Enumeration getPrefixes ()
    {
        return currentContext.getPrefixes();
    }


    /**
     * Return one of the prefixes mapped to a Namespace URI.
     *
     * <p>If more than one prefix is currently mapped to the same
     * URI, this method will make an arbitrary selection; if you
     * want all of the prefixes, use the {@link #getPrefixes}
     * method instead.</p>
     *
     * <p><strong>Note:</strong> this will never return the empty
     * (default) prefix; to check for a default prefix, use the {@link
     * #getURI getURI} method with an argument of "".</p>
     *
     * <p>
     *  返回映射到命名空间URI的前缀之一。
     * 
     *  <p>如果当前有多个前缀映射到同一个URI,此方法将进行任意选择;如果您想要所有的前缀,请改用{@link #getPrefixes}方法。</p>
     * 
     *  <p> <strong>注意：</strong>这永远不会返回空(默认)前缀;要检查默认前缀,请使用参数为""的{@link #getURI getURI}方法。</p>
     * 
     * 
     * @param uri The Namespace URI.
     * @return One of the prefixes currently mapped to the URI supplied,
     *         or null if none is mapped or if the URI is assigned to
     *         the default Namespace.
     * @see #getPrefixes(java.lang.String)
     * @see #getURI */
    public String getPrefix (String uri)
    {
        return currentContext.getPrefix(uri);
    }


    /**
     * Return an enumeration of all prefixes currently declared for a URI.
     *
     * <p>This method returns prefixes mapped to a specific Namespace
     * URI.  The xml: prefix will be included.  If you want only one
     * prefix that's mapped to the Namespace URI, and you don't care
     * which one you get, use the {@link #getPrefix getPrefix}
     *  method instead.</p>
     *
     * <p><strong>Note:</strong> the empty (default) prefix is
     * <em>never</em> included in this enumeration; to check for the
     * presence of a default Namespace, use the {@link #getURI getURI}
     * method with an argument of "".</p>
     *
     * <p>
     *  返回当前为URI声明的所有前缀的枚举。
     * 
     *  <p>此方法返回映射到特定命名空间URI的前缀。将包括xml：前缀。
     * 如果您只想要将一个前缀映射到命名空间URI,而不关心您获得哪个前缀,请改用{@link #getPrefix getPrefix}方法。</p>。
     * 
     *  <p> <strong>请注意</strong>：此枚举中包含空(默认)前缀<em>从不</em>;要检查默认命名空间的存在,请使用参数为""的{@link #getURI getURI}方法。
     * </p>。
     * 
     * 
     * @param uri The Namespace URI.
     * @return An enumeration of all prefixes declared in the
     *         current context.
     * @see #getPrefix
     * @see #getDeclaredPrefixes
     * @see #getURI */
    public Enumeration getPrefixes (String uri)
    {
        // JJK: The old code involved creating a vector, filling it
        // with all the matching prefixes, and then getting its
        // elements enumerator. Wastes storage, wastes cycles if we
        // don't actually need them all. Better to either implement
        // a specific enumerator for these prefixes... or a filter
        // around the all-prefixes enumerator, which comes out to
        // roughly the same thing.
        //
        // **** Currently a filter. That may not be most efficient
        // when I'm done restructuring storage!
        return new PrefixForUriEnumerator(this,uri,getPrefixes());
    }


    /**
     * Return an enumeration of all prefixes declared in this context.
     *
     * <p>The empty (default) prefix will be included in this
     * enumeration; note that this behaviour differs from that of
     * {@link #getPrefix} and {@link #getPrefixes}.</p>
     *
     * <p>
     *  返回在此上下文中声明的所有前缀的枚举。
     * 
     *  <p>空(默认)前缀将包含在此枚举中;请注意,此行为与{@link #getPrefix}和{@link #getPrefixes}的行为不同。</p>
     * 
     * 
     * @return An enumeration of all prefixes declared in this
     *         context.
     * @see #getPrefixes
     * @see #getURI
     */
    public Enumeration getDeclaredPrefixes ()
    {
        return currentContext.getDeclaredPrefixes();
    }



}

////////////////////////////////////////////////////////////////////
// Local classes.
// These were _internal_ classes... but in fact they don't have to be,
// and may be more efficient if they aren't.
////////////////////////////////////////////////////////////////////

/**
 * Implementation of Enumeration filter, wrapped
 * aroung the get-all-prefixes version of the operation. This is NOT
 * necessarily the most efficient approach; finding the URI and then asking
 * what prefixes apply to it might make much more sense.
 * <p>
 * 实现枚举过滤器,包装了一个get-all-prefixes版本的操作。这不一定是最有效的方法;找到URI,然后询问什么前缀适用于它可能会更有意义。
 * 
 */
class PrefixForUriEnumerator implements Enumeration
{
    private Enumeration allPrefixes;
    private String uri;
    private String lookahead=null;
    private NamespaceSupport2 nsup;

    // Kluge: Since one can't do a constructor on an
    // anonymous class (as far as I know)...
    PrefixForUriEnumerator(NamespaceSupport2 nsup,String uri, Enumeration allPrefixes)
    {
        this.nsup=nsup;
        this.uri=uri;
        this.allPrefixes=allPrefixes;
    }

    public boolean hasMoreElements()
    {
        if(lookahead!=null)
            return true;

        while(allPrefixes.hasMoreElements())
            {
                String prefix=(String)allPrefixes.nextElement();
                if(uri.equals(nsup.getURI(prefix)))
                    {
                        lookahead=prefix;
                        return true;
                    }
            }
        return false;
    }

    public Object nextElement()
    {
        if(hasMoreElements())
            {
                String tmp=lookahead;
                lookahead=null;
                return tmp;
            }
        else
            throw new java.util.NoSuchElementException();
    }
}

/**
 * Internal class for a single Namespace context.
 *
 * <p>This module caches and reuses Namespace contexts, so the number allocated
 * will be equal to the element depth of the document, not to the total
 * number of elements (i.e. 5-10 rather than tens of thousands).</p>
 * <p>
 *  单个命名空间上下文的内部类。
 * 
 *  <p>此模块缓存和重用命名空间上下文,因此分配的数字将等于文档的元素深度,而不是元素的总数(即5-10而不是数万)。</p>
 * 
 */
final class Context2 {

    ////////////////////////////////////////////////////////////////
    // Manefest Constants
    ////////////////////////////////////////////////////////////////

    /**
     * An empty enumeration.
     * <p>
     *  空枚举。
     * 
     */
    private final static Enumeration EMPTY_ENUMERATION =
        new Vector().elements();

    ////////////////////////////////////////////////////////////////
    // Protected state.
    ////////////////////////////////////////////////////////////////

    Hashtable prefixTable;
    Hashtable uriTable;
    Hashtable elementNameTable;
    Hashtable attributeNameTable;
    String defaultNS = null;

    ////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////

    private Vector declarations = null;
    private boolean tablesDirty = false;
    private Context2 parent = null;
    private Context2 child = null;

    /**
     * Create a new Namespace context.
     * <p>
     *  创建一个新的命名空间上下文。
     * 
     */
    Context2 (Context2 parent)
    {
        if(parent==null)
            {
                prefixTable = new Hashtable();
                uriTable = new Hashtable();
                elementNameTable=null;
                attributeNameTable=null;
            }
        else
            setParent(parent);
    }


    /**
    /* <p>
    /* 
     * @returns The child Namespace context object, or null if this
     * is the last currently on the chain.
     */
    Context2 getChild()
    {
        return child;
    }

    /**
    /* <p>
    /* 
     * @returns The parent Namespace context object, or null if this
     * is the root.
     */
    Context2 getParent()
    {
        return parent;
    }

    /**
     * (Re)set the parent of this Namespace context.
     * This is separate from the c'tor because it's re-applied
     * when a Context2 is reused by push-after-pop.
     *
     * <p>
     *  (Re)设置此Namespace上下文的父级。这与c'tor是分开的,因为当Context2被push-after-pop重用时,它被重新应用。
     * 
     * 
     * @param parent The parent Namespace context object.
     */
    void setParent (Context2 parent)
    {
        this.parent = parent;
        parent.child = this;        // JJK: Doubly-linked
        declarations = null;
        prefixTable = parent.prefixTable;
        uriTable = parent.uriTable;
        elementNameTable = parent.elementNameTable;
        attributeNameTable = parent.attributeNameTable;
        defaultNS = parent.defaultNS;
        tablesDirty = false;
    }


    /**
     * Declare a Namespace prefix for this context.
     *
     * <p>
     *  声明此上下文的命名空间前缀。
     * 
     * 
     * @param prefix The prefix to declare.
     * @param uri The associated Namespace URI.
     * @see org.xml.sax.helpers.NamespaceSupport2#declarePrefix
     */
    void declarePrefix (String prefix, String uri)
    {
                                // Lazy processing...
        if (!tablesDirty) {
            copyTables();
        }
        if (declarations == null) {
            declarations = new Vector();
        }

        prefix = prefix.intern();
        uri = uri.intern();
        if ("".equals(prefix)) {
            if ("".equals(uri)) {
                defaultNS = null;
            } else {
                defaultNS = uri;
            }
        } else {
            prefixTable.put(prefix, uri);
            uriTable.put(uri, prefix); // may wipe out another prefix
        }
        declarations.addElement(prefix);
    }


    /**
     * Process a raw XML 1.0 name in this context.
     *
     * <p>
     *  在此上下文中处理原始XML 1.0名称。
     * 
     * 
     * @param qName The raw XML 1.0 name.
     * @param isAttribute true if this is an attribute name.
     * @return An array of three strings containing the
     *         URI part (or empty string), the local part,
     *         and the raw name, all internalized, or null
     *         if there is an undeclared prefix.
     * @see org.xml.sax.helpers.NamespaceSupport2#processName
     */
    String [] processName (String qName, boolean isAttribute)
    {
        String name[];
        Hashtable table;

                                // Select the appropriate table.
        if (isAttribute) {
            if(elementNameTable==null)
                elementNameTable=new Hashtable();
            table = elementNameTable;
        } else {
            if(attributeNameTable==null)
                attributeNameTable=new Hashtable();
            table = attributeNameTable;
        }

                                // Start by looking in the cache, and
                                // return immediately if the name
                                // is already known in this content
        name = (String[])table.get(qName);
        if (name != null) {
            return name;
        }

                                // We haven't seen this name in this
                                // context before.
        name = new String[3];
        int index = qName.indexOf(':');


                                // No prefix.
        if (index == -1) {
            if (isAttribute || defaultNS == null) {
                name[0] = "";
            } else {
                name[0] = defaultNS;
            }
            name[1] = qName.intern();
            name[2] = name[1];
        }

                                // Prefix
        else {
            String prefix = qName.substring(0, index);
            String local = qName.substring(index+1);
            String uri;
            if ("".equals(prefix)) {
                uri = defaultNS;
            } else {
                uri = (String)prefixTable.get(prefix);
            }
            if (uri == null) {
                return null;
            }
            name[0] = uri;
            name[1] = local.intern();
            name[2] = qName.intern();
        }

                                // Save in the cache for future use.
        table.put(name[2], name);
        tablesDirty = true;
        return name;
    }


    /**
     * Look up the URI associated with a prefix in this context.
     *
     * <p>
     *  在此上下文中查找与前缀关联的URI。
     * 
     * 
     * @param prefix The prefix to look up.
     * @return The associated Namespace URI, or null if none is
     *         declared.
     * @see org.xml.sax.helpers.NamespaceSupport2#getURI
     */
    String getURI (String prefix)
    {
        if ("".equals(prefix)) {
            return defaultNS;
        } else if (prefixTable == null) {
            return null;
        } else {
            return (String)prefixTable.get(prefix);
        }
    }


    /**
     * Look up one of the prefixes associated with a URI in this context.
     *
     * <p>Since many prefixes may be mapped to the same URI,
     * the return value may be unreliable.</p>
     *
     * <p>
     *  在此上下文中查找与URI相关联的前缀之一。
     * 
     *  <p>由于许多前缀可能映射到同一个URI,返回值可能不可靠。</p>
     * 
     * 
     * @param uri The URI to look up.
     * @return The associated prefix, or null if none is declared.
     * @see org.xml.sax.helpers.NamespaceSupport2#getPrefix
     */
    String getPrefix (String uri)
    {
        if (uriTable == null) {
            return null;
        } else {
            return (String)uriTable.get(uri);
        }
    }


    /**
     * Return an enumeration of prefixes declared in this context.
     *
     * <p>
     *  返回在此上下文中声明的前缀的枚举。
     * 
     * 
     * @return An enumeration of prefixes (possibly empty).
     * @see org.xml.sax.helpers.NamespaceSupport2#getDeclaredPrefixes
     */
    Enumeration getDeclaredPrefixes ()
    {
        if (declarations == null) {
            return EMPTY_ENUMERATION;
        } else {
            return declarations.elements();
        }
    }


    /**
     * Return an enumeration of all prefixes currently in force.
     *
     * <p>The default prefix, if in force, is <em>not</em>
     * returned, and will have to be checked for separately.</p>
     *
     * <p>
     *  返回当前有效的所有前缀的枚举。
     * 
     *  <p>默认前缀(如果有效)不是</em>返回,必须单独检查。</p>
     * 
     * 
     * @return An enumeration of prefixes (never empty).
     * @see org.xml.sax.helpers.NamespaceSupport2#getPrefixes
     */
    Enumeration getPrefixes ()
    {
        if (prefixTable == null) {
            return EMPTY_ENUMERATION;
        } else {
            return prefixTable.keys();
        }
    }

    ////////////////////////////////////////////////////////////////
    // Internal methods.
    ////////////////////////////////////////////////////////////////

    /**
     * Copy on write for the internal tables in this context.
     *
     * <p>This class is optimized for the normal case where most
     * elements do not contain Namespace declarations. In that case,
     * the Context2 will share data structures with its parent.
     * New tables are obtained only when new declarations are issued,
     * so they can be popped off the stack.</p>
     *
     * <p> JJK: **** Alternative: each Context2 might declare
     *  _only_ its local bindings, and delegate upward if not found.</p>
     * <p>
     *  在此上下文中为内部表写入时复制。
     * 
     * <p>此类针对大多数元素不包含命名空间声明的正常情况进行了优化。在这种情况下,Context2将与其父级共享数据结构。只有在发布新声明时才会获得新表,因此可以从堆栈中弹出它们。</p>
     */
    private void copyTables ()
    {
        // Start by copying our parent's bindings
        prefixTable = (Hashtable)prefixTable.clone();
        uriTable = (Hashtable)uriTable.clone();

        // Replace the caches with empty ones, rather than
        // trying to determine which bindings should be flushed.
        // As far as I can tell, these caches are never actually
        // used in Xalan... More efficient to remove the whole
        // cache system? ****
        if(elementNameTable!=null)
            elementNameTable=new Hashtable();
        if(attributeNameTable!=null)
            attributeNameTable=new Hashtable();
        tablesDirty = true;
    }

}


// end of NamespaceSupport2.java
