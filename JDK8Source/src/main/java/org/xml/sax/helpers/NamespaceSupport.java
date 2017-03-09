/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// NamespaceSupport.java - generic Namespace support for SAX.
// http://www.saxproject.org
// Written by David Megginson
// This class is in the Public Domain.  NO WARRANTY!
// $Id: NamespaceSupport.java,v 1.5 2004/11/03 22:53:09 jsuttor Exp $

package org.xml.sax.helpers;

import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


/**
 * Encapsulate Namespace logic for use by applications using SAX,
 * or internally by SAX drivers.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class encapsulates the logic of Namespace processing: it
 * tracks the declarations currently in force for each context and
 * automatically processes qualified XML names into their Namespace
 * parts; it can also be used in reverse for generating XML qnames
 * from Namespaces.</p>
 *
 * <p>Namespace support objects are reusable, but the reset method
 * must be invoked between each session.</p>
 *
 * <p>Here is a simple session:</p>
 *
 * <pre>
 * String parts[] = new String[3];
 * NamespaceSupport support = new NamespaceSupport();
 *
 * support.pushContext();
 * support.declarePrefix("", "http://www.w3.org/1999/xhtml");
 * support.declarePrefix("dc", "http://www.purl.org/dc#");
 *
 * parts = support.processName("p", parts, false);
 * System.out.println("Namespace URI: " + parts[0]);
 * System.out.println("Local name: " + parts[1]);
 * System.out.println("Raw name: " + parts[2]);
 *
 * parts = support.processName("dc:title", parts, false);
 * System.out.println("Namespace URI: " + parts[0]);
 * System.out.println("Local name: " + parts[1]);
 * System.out.println("Raw name: " + parts[2]);
 *
 * support.popContext();
 * </pre>
 *
 * <p>Note that this class is optimized for the use case where most
 * elements do not contain Namespace declarations: if the same
 * prefix/URI mapping is repeated for each context (for example), this
 * class will be somewhat less efficient.</p>
 *
 * <p>Although SAX drivers (parsers) may choose to use this class to
 * implement namespace handling, they are not required to do so.
 * Applications must track namespace information themselves if they
 * want to use namespace information.
 *
 * <p>
 *  封装用于由使用SAX的应用程序使用或由SAX驱动程序内部使用的命名空间逻辑。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保修</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此类封装了命名空间处理的逻辑：它跟踪当前对每个上下文有效的声明,并自动将有限的XML名称处理到其命名空间部分中;它也可以反向用于从命名空间生成XML qnames。</p>
 * 
 *  <p>命名空间支持对象是可重用的,但必须在每个会话之间调用重置方法。</p>
 * 
 *  <p>这是一个简单的会话：</p>
 * 
 * <pre>
 *  String parts [] = new String [3]; NamespaceSupport支持=新的NamespaceSupport();
 * 
 *  support.pushContext(); support.declarePrefix("","http://www.w3.org/1999/xhtml"); support.declarePref
 * ix("dc","http://www.purl.org/dc#");。
 * 
 *  parts = support.processName("p",parts,false); System.out.println("Namespace URI："+ parts [0]); Syste
 * m.out.println("Local name："+ parts [1]); System.out.println("Raw name："+ parts [2]);。
 * 
 *  parts = support.processName("dc：title",parts,false); System.out.println("Namespace URI："+ parts [0])
 * ; System.out.println("Local name："+ parts [1]); System.out.println("Raw name："+ parts [2]);。
 * 
 * support.popContext();
 * </pre>
 * 
 *  <p>请注意,此类是针对大多数元素不包含命名空间声明的用例进行优化的：如果为每个上下文重复相同的前缀/ URI映射(例如),此类将有些效率较低。</p >
 * 
 *  <p>虽然SAX驱动程序(解析器)可以选择使用这个类来实现命名空间处理,但是它们不是必需的。如果想要使用命名空间信息,应用程序必须自己跟踪命名空间信息。
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson
 */
public class NamespaceSupport
{


    ////////////////////////////////////////////////////////////////////
    // Constants.
    ////////////////////////////////////////////////////////////////////


    /**
     * The XML Namespace URI as a constant.
     * The value is <code>http://www.w3.org/XML/1998/namespace</code>
     * as defined in the "Namespaces in XML" * recommendation.
     *
     * <p>This is the Namespace URI that is automatically mapped
     * to the "xml" prefix.</p>
     * <p>
     *  XML命名空间URI作为常量。值为"XML中的命名空间"*建议中定义的<code> http://www.w3.org/XML/1998/namespace </code>。
     * 
     *  <p>这是自动映射到"xml"前缀的命名空间URI。</p>
     * 
     */
    public final static String XMLNS =
        "http://www.w3.org/XML/1998/namespace";


    /**
     * The namespace declaration URI as a constant.
     * The value is <code>http://www.w3.org/xmlns/2000/</code>, as defined
     * in a backwards-incompatible erratum to the "Namespaces in XML"
     * recommendation.  Because that erratum postdated SAX2, SAX2 defaults
     * to the original recommendation, and does not normally use this URI.
     *
     *
     * <p>This is the Namespace URI that is optionally applied to
     * <em>xmlns</em> and <em>xmlns:*</em> attributes, which are used to
     * declare namespaces.  </p>
     *
     * <p>
     *  命名空间声明URI作为常量。该值为<code> http://www.w3.org/xmlns/2000/ </code>,如"向XML中的命名空间"建议的向后不兼容的勘误中所定义。
     * 因为该勘误后期SAX2,SAX2默认为原始推荐,并且通常不使用此URI。
     * 
     *  <p>这是可选地应用于<em> xmlns </em>和<em> xmlns：* </em>属性的命名空间URI,用于声明命名空间。 </p>
     * 
     * 
     * @since SAX 2.1alpha
     * @see #setNamespaceDeclUris
     * @see #isNamespaceDeclUris
     */
    public final static String NSDECL =
        "http://www.w3.org/xmlns/2000/";


    /**
     * An empty enumeration.
     * <p>
     *  空枚举。
     * 
     */
    private final static Enumeration EMPTY_ENUMERATION =
        new Vector().elements();


    ////////////////////////////////////////////////////////////////////
    // Constructor.
    ////////////////////////////////////////////////////////////////////


    /**
     * Create a new Namespace support object.
     * <p>
     *  创建一个新的命名空间支持对象。
     * 
     */
    public NamespaceSupport ()
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
     * Namespace support object for a new session.  If namespace
     * declaration URIs are to be supported, that flag must also
     * be set to a non-default value.
     * </p>
     *
     * <p>
     *  重置此命名空间支持对象以供重用。
     * 
     * <p>在重新使用新会话的命名空间支持对象之前,必须调用此方法。如果要支持命名空间声明URI,那么该标志也必须设置为非默认值。
     * </p>
     * 
     * 
     * @see #setNamespaceDeclUris
     */
    public void reset ()
    {
        contexts = new Context[32];
        namespaceDeclUris = false;
        contextPos = 0;
        contexts[contextPos] = currentContext = new Context();
        currentContext.declarePrefix("xml", XMLNS);
    }


    /**
     * Start a new Namespace context.
     * The new context will automatically inherit
     * the declarations of its parent context, but it will also keep
     * track of which declarations were made within this context.
     *
     * <p>Event callback code should start a new context once per element.
     * This means being ready to call this in either of two places.
     * For elements that don't include namespace declarations, the
     * <em>ContentHandler.startElement()</em> callback is the right place.
     * For elements with such a declaration, it'd done in the first
     * <em>ContentHandler.startPrefixMapping()</em> callback.
     * A boolean flag can be used to
     * track whether a context has been started yet.  When either of
     * those methods is called, it checks the flag to see if a new context
     * needs to be started.  If so, it starts the context and sets the
     * flag.  After <em>ContentHandler.startElement()</em>
     * does that, it always clears the flag.
     *
     * <p>Normally, SAX drivers would push a new context at the beginning
     * of each XML element.  Then they perform a first pass over the
     * attributes to process all namespace declarations, making
     * <em>ContentHandler.startPrefixMapping()</em> callbacks.
     * Then a second pass is made, to determine the namespace-qualified
     * names for all attributes and for the element name.
     * Finally all the information for the
     * <em>ContentHandler.startElement()</em> callback is available,
     * so it can then be made.
     *
     * <p>The Namespace support object always starts with a base context
     * already in force: in this context, only the "xml" prefix is
     * declared.</p>
     *
     * <p>
     *  启动新的命名空间上下文。新上下文将自动继承其父上下文的声明,但它还将跟踪在此上下文中做出的声明。
     * 
     *  <p>事件回调代码应该每个元素启动一个新的上下文。这意味着准备在两个地方调用它。
     * 对于不包含命名空间声明的元素,<em> ContentHandler.startElement()</em>回调是正确的位置。
     * 对于具有这样声明的元素,它会在第一个<em> ContentHandler.startPrefixMapping()</em>回调中完成。布尔标志可以用于跟踪上下文是否已经启动。
     * 当这些方法中的任何一个被调用时,它检查该标志以查看是否需要启动新的上下文。如果是这样,它启动上下文并设置标志。
     * 在<em> ContentHandler.startElement()</em>之后,它总是清除标志。
     * 
     * <p>通常,SAX驱动程序会在每个XML元素的开头推送一个新的上下文。
     * 然后,他们执行对属性的第一遍,以处理所有命名空间声明,使得ContentHandler.startPrefixMapping()</em>回调。
     * 然后进行第二次传递,以确定所有属性和元素名称的命名空间限定名称。最后,<em> ContentHandler.startElement()</em>回调的所有信息都可用,因此可以进行。
     * 
     *  <p>命名空间支持对象始终以一个已经生效的基本上下文开始：在此上下文中,只声明了"xml"前缀。</p>
     * 
     * 
     * @see org.xml.sax.ContentHandler
     * @see #popContext
     */
    public void pushContext ()
    {
        int max = contexts.length;

        contextPos++;

                                // Extend the array if necessary
        if (contextPos >= max) {
            Context newContexts[] = new Context[max*2];
            System.arraycopy(contexts, 0, newContexts, 0, max);
            max *= 2;
            contexts = newContexts;
        }

                                // Allocate the context if necessary.
        currentContext = contexts[contextPos];
        if (currentContext == null) {
            contexts[contextPos] = currentContext = new Context();
        }

                                // Set the parent, if any.
        if (contextPos > 0) {
            currentContext.setParent(contexts[contextPos - 1]);
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
        contexts[contextPos].clear();
        contextPos--;
        if (contextPos < 0) {
            throw new EmptyStackException();
        }
        currentContext = contexts[contextPos];
    }



    ////////////////////////////////////////////////////////////////////
    // Operations within a context.
    ////////////////////////////////////////////////////////////////////


    /**
     * Declare a Namespace prefix.  All prefixes must be declared
     * before they are referenced.  For example, a SAX driver (parser)
     * would scan an element's attributes
     * in two passes:  first for namespace declarations,
     * then a second pass using {@link #processName processName()} to
     * interpret prefixes against (potentially redefined) prefixes.
     *
     * <p>This method declares a prefix in the current Namespace
     * context; the prefix will remain in force until this context
     * is popped, unless it is shadowed in a descendant context.</p>
     *
     * <p>To declare the default element Namespace, use the empty string as
     * the prefix.</p>
     *
     * <p>Note that there is an asymmetry in this library: {@link
     * #getPrefix getPrefix} will not return the "" prefix,
     * even if you have declared a default element namespace.
     * To check for a default namespace,
     * you have to look it up explicitly using {@link #getURI getURI}.
     * This asymmetry exists to make it easier to look up prefixes
     * for attribute names, where the default prefix is not allowed.</p>
     *
     * <p>
     *  声明一个命名空间前缀。所有前缀必须在引用之前声明。
     * 例如,SAX驱动程序(解析器)将扫描元素的属性两次：首先是命名空间声明,然后是第二次使用{@link #processName processName()}来解释前缀(可能重新定义)前缀。
     * 
     *  <p>此方法在当前命名空间上下文中声明一个前缀;该前缀将一直有效,直到该上下文被弹出,除非它在后代上下文中被隐藏。</p>
     * 
     * <p>要声明默认元素Namespace,请使用空字符串作为前缀。</p>
     * 
     *  <p>请注意,此库中有一个不对称：{@link #getPrefix getPrefix}不会返回""前缀,即使您已声明了一个默认元素命名空间。
     * 要检查默认命名空间,您必须使用{@link #getURI getURI}明确查找它。这种不对称性使得更容易查找属性名称的前缀,其中不允许使用默认前缀。</p>。
     * 
     * 
     * @param prefix The prefix to declare, or the empty string to
     *  indicate the default element namespace.  This may never have
     *  the value "xml" or "xmlns".
     * @param uri The Namespace URI to associate with the prefix.
     * @return true if the prefix was legal, false otherwise
     *
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
     * Process a raw XML qualified name, after all declarations in the
     * current context have been handled by {@link #declarePrefix
     * declarePrefix()}.
     *
     * <p>This method processes a raw XML qualified name in the
     * current context by removing the prefix and looking it up among
     * the prefixes currently declared.  The return value will be the
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
     * element names: an unprefixed element name will receive the
     * default Namespace (if any), while an unprefixed attribute name
     * will not.</p>
     *
     * <p>
     *  在当前上下文中的所有声明都已由{@link #declarePrefix declarePrefix()}处理后,处理原始XML限定名称。
     * 
     *  <p>此方法通过删除前缀并在当前声明的前缀中查找在当前上下文中处理原始XML限定名称。返回值将是调用者提供的数组,填充如下：</p>
     * 
     * <dl>
     *  <dt> parts [0] </dt> <dd>命名空间URI或空字符串,如果没有使用。</dd> <dt> parts [1]无前缀)。
     * </dd> <dt> parts [2] </dt> <dd>原始原始名称。</dd>。
     * </dl>
     * 
     *  <p>数组中的所有字符串都将被内化。如果原始名称具有尚未声明的前缀,则返回值将为null。</p>
     * 
     *  <p>请注意,属性名称的处理方式与元素名称不同：不带前缀的元素名称将接收默认名称空间(如果有),而不带前缀的属性名称则不会。</p>
     * 
     * 
     * @param qName The XML qualified name to be processed.
     * @param parts An array supplied by the caller, capable of
     *        holding at least three members.
     * @param isAttribute A flag indicating whether this is an
     *        attribute name (true) or an element name (false).
     * @return The supplied array holding three internalized strings
     *        representing the Namespace URI (or empty string), the
     *        local name, and the XML qualified name; or null if there
     *        is an undeclared prefix.
     * @see #declarePrefix
     * @see java.lang.String#intern */
    public String [] processName (String qName, String parts[],
                                  boolean isAttribute)
    {
        String myParts[] = currentContext.processName(qName, isAttribute);
        if (myParts == null) {
            return null;
        } else {
            parts[0] = myParts[0];
            parts[1] = myParts[1];
            parts[2] = myParts[2];
            return parts;
        }
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
     * <p>此方法在当前上下文中查找前缀。对默认命名空间使用空字符串("")。</p>
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
     * Return an enumeration of all prefixes whose declarations are
     * active in the current context.
     * This includes declarations from parent contexts that have
     * not been overridden.
     *
     * <p><strong>Note:</strong> if there is a default prefix, it will not be
     * returned in this enumeration; check for the default prefix
     * using the {@link #getURI getURI} with an argument of "".</p>
     *
     * <p>
     *  返回其声明在当前上下文中处于活动状态的所有前缀的枚举。这包括来自父上下文的未被覆盖的声明。
     * 
     *  <p> <strong>注意：</strong>如果有默认前缀,则不会在此枚举中返回;请使用带有参数""的{@link #getURI getURI}检查默认前缀。</p>
     * 
     * 
     * @return An enumeration of prefixes (never empty).
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
     * <p><strong>Note:</strong> this will never return the empty (default) prefix;
     * to check for a default prefix, use the {@link #getURI getURI}
     * method with an argument of "".</p>
     *
     * <p>
     *  返回映射到命名空间URI的前缀之一。
     * 
     *  <p>如果当前有多个前缀映射到同一个URI,此方法将进行任意选择;如果您想要所有的前缀,请改用{@link #getPrefixes}方法。</p>
     * 
     *  <p> <strong>注意：</strong>这永远不会返回空(默认)前缀;要检查默认前缀,请使用参数为""的{@link #getURI getURI}方法。</p>
     * 
     * 
     * @param uri the namespace URI
     * @return one of the prefixes currently mapped to the URI supplied,
     *         or null if none is mapped or if the URI is assigned to
     *         the default namespace
     * @see #getPrefixes(java.lang.String)
     * @see #getURI
     */
    public String getPrefix (String uri)
    {
        return currentContext.getPrefix(uri);
    }


    /**
     * Return an enumeration of all prefixes for a given URI whose
     * declarations are active in the current context.
     * This includes declarations from parent contexts that have
     * not been overridden.
     *
     * <p>This method returns prefixes mapped to a specific Namespace
     * URI.  The xml: prefix will be included.  If you want only one
     * prefix that's mapped to the Namespace URI, and you don't care
     * which one you get, use the {@link #getPrefix getPrefix}
     *  method instead.</p>
     *
     * <p><strong>Note:</strong> the empty (default) prefix is <em>never</em> included
     * in this enumeration; to check for the presence of a default
     * Namespace, use the {@link #getURI getURI} method with an
     * argument of "".</p>
     *
     * <p>
     *  返回其声明在当前上下文中处于活动状态的给定URI的所有前缀的枚举。这包括来自父上下文的未被覆盖的声明。
     * 
     *  <p>此方法返回映射到特定命名空间URI的前缀。将包括xml：前缀。
     * 如果您只想要将一个前缀映射到命名空间URI,而不关心您获得哪个前缀,请改用{@link #getPrefix getPrefix}方法。</p>。
     * 
     * <p> <strong>请注意</strong>：此枚举中包含空(默认)前缀<em>从不</em>;要检查默认命名空间的存在,请使用参数为""的{@link #getURI getURI}方法。
     * </p>。
     * 
     * 
     * @param uri The Namespace URI.
     * @return An enumeration of prefixes (never empty).
     * @see #getPrefix
     * @see #getDeclaredPrefixes
     * @see #getURI
     */
    public Enumeration getPrefixes (String uri)
    {
        Vector prefixes = new Vector();
        Enumeration allPrefixes = getPrefixes();
        while (allPrefixes.hasMoreElements()) {
            String prefix = (String)allPrefixes.nextElement();
            if (uri.equals(getURI(prefix))) {
                prefixes.addElement(prefix);
            }
        }
        return prefixes.elements();
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

    /**
     * Controls whether namespace declaration attributes are placed
     * into the {@link #NSDECL NSDECL} namespace
     * by {@link #processName processName()}.  This may only be
     * changed before any contexts have been pushed.
     *
     * <p>
     *  控制{@link #processName processName()}是否将命名空间声明属性放置在{@link #NSDECL NSDECL}命名空间中。这只能在任何上下文被推送之前改变。
     * 
     * 
     * @since SAX 2.1alpha
     *
     * @exception IllegalStateException when attempting to set this
     *  after any context has been pushed.
     */
    public void setNamespaceDeclUris (boolean value)
    {
        if (contextPos != 0)
            throw new IllegalStateException ();
        if (value == namespaceDeclUris)
            return;
        namespaceDeclUris = value;
        if (value)
            currentContext.declarePrefix ("xmlns", NSDECL);
        else {
            contexts[contextPos] = currentContext = new Context();
            currentContext.declarePrefix("xml", XMLNS);
        }
    }

    /**
     * Returns true if namespace declaration attributes are placed into
     * a namespace.  This behavior is not the default.
     *
     * <p>
     *  如果将命名空间声明属性放入命名空间,则返回true。此行为不是默认行为。
     * 
     * 
     * @since SAX 2.1alpha
     */
    public boolean isNamespaceDeclUris ()
        { return namespaceDeclUris; }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    private Context contexts[];
    private Context currentContext;
    private int contextPos;
    private boolean namespaceDeclUris;


    ////////////////////////////////////////////////////////////////////
    // Internal classes.
    ////////////////////////////////////////////////////////////////////

    /**
     * Internal class for a single Namespace context.
     *
     * <p>This module caches and reuses Namespace contexts,
     * so the number allocated
     * will be equal to the element depth of the document, not to the total
     * number of elements (i.e. 5-10 rather than tens of thousands).
     * Also, data structures used to represent contexts are shared when
     * possible (child contexts without declarations) to further reduce
     * the amount of memory that's consumed.
     * </p>
     * <p>
     *  单个命名空间上下文的内部类。
     * 
     *  <p>此模块缓存和重用命名空间上下文,因此分配的数量将等于文档的元素深度,而不是元素的总数(即5-10而不是数万)。
     * 此外,用于表示上下文的数据结构在可能时(没有声明的子上下文)被共享,以进一步减少消耗的内存量。
     * </p>
     */
    final class Context {

        /**
         * Create the root-level Namespace context.
         * <p>
         *  创建根级别的命名空间上下文。
         * 
         */
        Context ()
        {
            copyTables();
        }


        /**
         * (Re)set the parent of this Namespace context.
         * The context must either have been freshly constructed,
         * or must have been cleared.
         *
         * <p>
         *  (Re)设置此Namespace上下文的父级。上下文必须是新建的,或者必须已被清除。
         * 
         * 
         * @param context The parent Namespace context object.
         */
        void setParent (Context parent)
        {
            this.parent = parent;
            declarations = null;
            prefixTable = parent.prefixTable;
            uriTable = parent.uriTable;
            elementNameTable = parent.elementNameTable;
            attributeNameTable = parent.attributeNameTable;
            defaultNS = parent.defaultNS;
            declSeen = false;
        }

        /**
         * Makes associated state become collectible,
         * invalidating this context.
         * {@link #setParent} must be called before
         * this context may be used again.
         * <p>
         * 使关联状态变为可收集,使此上下文失效。必须先调用{@link #setParent},然后才能再次使用此上下文。
         * 
         */
        void clear ()
        {
            parent = null;
            prefixTable = null;
            uriTable = null;
            elementNameTable = null;
            attributeNameTable = null;
            defaultNS = null;
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
         * @see org.xml.sax.helpers.NamespaceSupport#declarePrefix
         */
        void declarePrefix (String prefix, String uri)
        {
                                // Lazy processing...
//          if (!declsOK)
//              throw new IllegalStateException (
//                  "can't declare any more prefixes in this context");
            if (!declSeen) {
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
         * Process an XML qualified name in this context.
         *
         * <p>
         *  在此上下文中处理XML限定名称。
         * 
         * 
         * @param qName The XML qualified name.
         * @param isAttribute true if this is an attribute name.
         * @return An array of three strings containing the
         *         URI part (or empty string), the local part,
         *         and the raw name, all internalized, or null
         *         if there is an undeclared prefix.
         * @see org.xml.sax.helpers.NamespaceSupport#processName
         */
        String [] processName (String qName, boolean isAttribute)
        {
            String name[];
            Hashtable table;

                                // Select the appropriate table.
            if (isAttribute) {
                table = attributeNameTable;
            } else {
                table = elementNameTable;
            }

                                // Start by looking in the cache, and
                                // return immediately if the name
                                // is already known in this content
            name = (String[])table.get(qName);
            if (name != null) {
                return name;
            }

                                // We haven't seen this name in this
                                // context before.  Maybe in the parent
                                // context, but we can't assume prefix
                                // bindings are the same.
            name = new String[3];
            name[2] = qName.intern();
            int index = qName.indexOf(':');


                                // No prefix.
            if (index == -1) {
                if (isAttribute) {
                    if (qName == "xmlns" && namespaceDeclUris)
                        name[0] = NSDECL;
                    else
                        name[0] = "";
                } else if (defaultNS == null) {
                    name[0] = "";
                } else {
                    name[0] = defaultNS;
                }
                name[1] = name[2];
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
                if (uri == null
                        || (!isAttribute && "xmlns".equals (prefix))) {
                    return null;
                }
                name[0] = uri;
                name[1] = local.intern();
            }

                                // Save in the cache for future use.
                                // (Could be shared with parent context...)
            table.put(name[2], name);
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
         * @see org.xml.sax.helpers.NamespaceSupport#getURI
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
         * @see org.xml.sax.helpers.NamespaceSupport#getPrefix
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
         * @see org.xml.sax.helpers.NamespaceSupport#getDeclaredPrefixes
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
         * @see org.xml.sax.helpers.NamespaceSupport#getPrefixes
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
         * elements do not contain Namespace declarations.</p>
         * <p>
         *  在此上下文中为内部表写入时复制。
         * 
         */
        private void copyTables ()
        {
            if (prefixTable != null) {
                prefixTable = (Hashtable)prefixTable.clone();
            } else {
                prefixTable = new Hashtable();
            }
            if (uriTable != null) {
                uriTable = (Hashtable)uriTable.clone();
            } else {
                uriTable = new Hashtable();
            }
            elementNameTable = new Hashtable();
            attributeNameTable = new Hashtable();
            declSeen = true;
        }



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
        private boolean declSeen = false;
        private Context parent = null;
    }
}

// end of NamespaceSupport.java
