/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.namespace;

import java.util.Iterator;

/**
 * <p>Interface for read only XML Namespace context processing.</p>
 *
 * <p>An XML Namespace has the properties:</p>
 * <ul>
 *   <li>Namespace URI:
 *       Namespace name expressed as a URI to which the prefix is bound</li>
 *   <li>prefix: syntactically, this is the part of the attribute name
 *       following the <code>XMLConstants.XMLNS_ATTRIBUTE</code>
 *       ("xmlns") in the Namespace declaration</li>
 * </ul>
 * <p>example:
 * <code>&lt;element xmlns:prefix="http://Namespace-name-URI"&gt;</code></p>
 *
 * <p>All <code>get*(*)</code> methods operate in the current scope
 * for Namespace URI and prefix resolution.</p>
 *
 * <p>Note that a Namespace URI can be bound to
 * <strong>multiple</strong> prefixes in the current scope.  This can
 * occur when multiple <code>XMLConstants.XMLNS_ATTRIBUTE</code>
 * ("xmlns") Namespace declarations occur in the same Start-Tag and
 * refer to the same Namespace URI. e.g.<br />
 * <pre>
 * &lt;element xmlns:prefix1="http://Namespace-name-URI"
 *          xmlns:prefix2="http://Namespace-name-URI"&gt;
 * </pre>
 * This can also occur when the same Namespace URI is used in multiple
 * <code>XMLConstants.XMLNS_ATTRIBUTE</code> ("xmlns") Namespace
 * declarations in the logical parent element hierarchy.  e.g.<br />
 * <pre>
 * &lt;parent xmlns:prefix1="http://Namespace-name-URI">
 *   &lt;child xmlns:prefix2="http://Namespace-name-URI"&gt;
 *     ...
 *   &lt;/child&gt;
 * &lt;/parent&gt;
 * </pre></p>
 *
 * <p>A prefix can only be bound to a <strong>single</strong>
 * Namespace URI in the current scope.</p>
 *
 * <p>
 *  <p>只读XML命名空间上下文处理的接口。</p>
 * 
 *  <p> XML命名空间具有以下属性：</p>
 * <ul>
 *  <li>名称空间URI：表示为前缀绑定到的URI的名称空间名称</li> <li> prefix：语法上,这是属性名称的一部分,位于<code> XMLConstants.XMLNS_ATTRIBUT
 * E </code> "xmlns")在命名空间声明</li>中。
 * </ul>
 *  <p> example：<code>&lt; element xmlns：prefix ="http：// Namespace-name-URI"&gt; </code> </p>
 * 
 *  <p>所有<code> get *(*)</code>方法在当前作用域中操作,用于命名空间URI和前缀解析。</p>
 * 
 *  <p>请注意,命名空间URI可以绑定到当前范围中的<strong>多个</strong>前缀。
 * 当多个<code> XMLConstants.XMLNS_ATTRIBUTE </code>("xmlns")命名空间声明出现在同一个Start-Tag并引用同一个命名空间URI时,就会发生这种情况。
 * 例如<br />。
 * <pre>
 *  &lt; element xmlns：prefix1 ="http：// Namespace-name-URI"xmlns：prefix2 ="http：// Namespace-name-URI"&
 * gt;。
 * </pre>
 *  当在逻辑父元素层次结构中的多个<code> XMLConstants.XMLNS_ATTRIBUTE </code>("xmlns")命名空间声明中使用相同的命名空间URI时,也会发生这种情况。
 * 例如<br />。
 * <pre>
 * &lt;parent xmlns:prefix1="http://Namespace-name-URI">
 *  &lt; child xmlns：prefix2 ="http：// Namespace-name-URI"&gt; ...&lt; / child&gt; &lt; / parent&gt; </pre>
 *  </p>。
 * 
 *  <p>前缀只能绑定到当前作用域中的<strong>单个</strong>命名空间URI。</p>
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @see javax.xml.XMLConstants
 *   javax.xml.XMLConstants for declarations of common XML values
 * @see <a href="http://www.w3.org/TR/xmlschema-2/#QName">
 *   XML Schema Part2: Datatypes</a>
 * @see <a href="http://www.w3.org/TR/REC-xml-names/#ns-qualnames">
 *   Namespaces in XML</a>
 * @see <a href="http://www.w3.org/XML/xml-names-19990114-errata">
 *   Namespaces in XML Errata</a>
 * @since 1.5
 */

public interface NamespaceContext {

    /**
     * <p>Get Namespace URI bound to a prefix in the current scope.</p>
     *
     * <p>When requesting a Namespace URI by prefix, the following
     * table describes the returned Namespace URI value for all
     * possible prefix values:</p>
     *
     * <table border="2" rules="all" cellpadding="4">
     *   <thead>
     *     <tr>
     *       <td align="center" colspan="2">
     *         <code>getNamespaceURI(prefix)</code>
     *         return value for specified prefixes
     *       </td>
     *     </tr>
     *     <tr>
     *       <td>prefix parameter</td>
     *       <td>Namespace URI return value</td>
     *     </tr>
     *   </thead>
     *   <tbody>
     *     <tr>
     *       <td><code>DEFAULT_NS_PREFIX</code> ("")</td>
     *       <td>default Namespace URI in the current scope or
     *         <code>{@link
     *         javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI("")}
     *         </code>
     *         when there is no default Namespace URI in the current scope</td>
     *     </tr>
     *     <tr>
     *       <td>bound prefix</td>
     *       <td>Namespace URI bound to prefix in current scope</td>
     *     </tr>
     *     <tr>
     *       <td>unbound prefix</td>
     *       <td>
     *         <code>{@link
     *         javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI("")}
     *         </code>
     *       </td>
     *     </tr>
     *     <tr>
     *       <td><code>XMLConstants.XML_NS_PREFIX</code> ("xml")</td>
     *       <td><code>XMLConstants.XML_NS_URI</code>
     *           ("http://www.w3.org/XML/1998/namespace")</td>
     *     </tr>
     *     <tr>
     *       <td><code>XMLConstants.XMLNS_ATTRIBUTE</code> ("xmlns")</td>
     *       <td><code>XMLConstants.XMLNS_ATTRIBUTE_NS_URI</code>
     *         ("http://www.w3.org/2000/xmlns/")</td>
     *     </tr>
     *     <tr>
     *       <td><code>null</code></td>
     *       <td><code>IllegalArgumentException</code> is thrown</td>
     *     </tr>
     *    </tbody>
     * </table>
     *
     * <p>
     * <p>获取绑定到当前作用域中的前缀的命名空间URI。</p>
     * 
     *  <p>当按前缀请求命名空间URI时,下表描述了所有可能的前缀值的返回的命名空间URI值：</p>
     * 
     * <table border="2" rules="all" cellpadding="4">
     * <thead>
     * <tr>
     * <td align="center" colspan="2">
     *  <code> getNamespaceURI(prefix)</code>返回指定前缀的值
     * </td>
     * </tr>
     * <tr>
     *  <td> prefix参数</td> <td>命名空间URI返回值</td>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     *  <td> <code> DEFAULT_NS_PREFIX </code>("")</td> <td>当前作用域中的默认命名空间URI或<code> {@ link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI(" }
     * }。
     * </code>
     *  当当前范围中没有默认命名空间URI时</td>
     * </tr>
     * <tr>
     *  <td>绑定前缀</td> <td>绑定到当前作用域中的前缀的命名空间URI </td>
     * </tr>
     * <tr>
     *  <td>未绑定前缀</td>
     * <td>
     *  <code> {@ link javax.xml.XMLConstants#NULL_NS_URI XMLConstants.NULL_NS_URI("")}
     * </code>
     * </td>
     * </tr>
     * <tr>
     *  <td> <code> XMLConstants.XML_NS_PREFIX </code>("xml")</td> <td> <code> XMLConstants.XML_NS_URI </code>
     * ("http://www.w3.org/XML/1998 / namespace")</td>。
     * </tr>
     * <tr>
     *  <td> <code> XMLConstants.XMLNS_ATTRIBUTE </code>("xmlns")</td> <td> <code> XMLConstants.XMLNS_ATTRIB
     * UTE_NS_URI </code>("http://www.w3.org/2000/xmlns /")</td>。
     * </tr>
     * <tr>
     *  <td> <code> null </code> </td> <td> <code>抛出IllegalArgumentException </code> </td>
     * </tr>
     * </tbody>
     * </table>
     * 
     * 
     * @param prefix prefix to look up
     *
     * @return Namespace URI bound to prefix in the current scope
     *
     * @throws IllegalArgumentException When <code>prefix</code> is
     *   <code>null</code>
     */
    String getNamespaceURI(String prefix);

    /**
     * <p>Get prefix bound to Namespace URI in the current scope.</p>
     *
     * <p>To get all prefixes bound to a Namespace URI in the current
     * scope, use {@link #getPrefixes(String namespaceURI)}.</p>
     *
     * <p>When requesting a prefix by Namespace URI, the following
     * table describes the returned prefix value for all Namespace URI
     * values:</p>
     *
     * <table border="2" rules="all" cellpadding="4">
     *   <thead>
     *     <tr>
     *       <th align="center" colspan="2">
     *         <code>getPrefix(namespaceURI)</code> return value for
     *         specified Namespace URIs
     *       </th>
     *     </tr>
     *     <tr>
     *       <th>Namespace URI parameter</th>
     *       <th>prefix value returned</th>
     *     </tr>
     *   </thead>
     *   <tbody>
     *     <tr>
     *       <td>&lt;default Namespace URI&gt;</td>
     *       <td><code>XMLConstants.DEFAULT_NS_PREFIX</code> ("")
     *       </td>
     *     </tr>
     *     <tr>
     *       <td>bound Namespace URI</td>
     *       <td>prefix bound to Namespace URI in the current scope,
     *           if multiple prefixes are bound to the Namespace URI in
     *           the current scope, a single arbitrary prefix, whose
     *           choice is implementation dependent, is returned</td>
     *     </tr>
     *     <tr>
     *       <td>unbound Namespace URI</td>
     *       <td><code>null</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>XMLConstants.XML_NS_URI</code>
     *           ("http://www.w3.org/XML/1998/namespace")</td>
     *       <td><code>XMLConstants.XML_NS_PREFIX</code> ("xml")</td>
     *     </tr>
     *     <tr>
     *       <td><code>XMLConstants.XMLNS_ATTRIBUTE_NS_URI</code>
     *           ("http://www.w3.org/2000/xmlns/")</td>
     *       <td><code>XMLConstants.XMLNS_ATTRIBUTE</code> ("xmlns")</td>
     *     </tr>
     *     <tr>
     *       <td><code>null</code></td>
     *       <td><code>IllegalArgumentException</code> is thrown</td>
     *     </tr>
     *   </tbody>
     * </table>
     *
     * <p>
     *  <p>获取绑定到当前作用域中的Namespace URI的前缀。</p>
     * 
     *  <p>要获取绑定到当前作用域中的命名空间URI的所有前缀,请使用{@link #getPrefixes(String namespaceURI)}。</p>
     * 
     *  <p>当通过命名空间URI请求前缀时,下表描述了所有Namespace URI值的返回前缀值：</p>
     * 
     * <table border="2" rules="all" cellpadding="4">
     * <thead>
     * <tr>
     * <th align="center" colspan="2">
     * <code> getPrefix(namespaceURI)</code>返回指定名称空间URI的值
     * </th>
     * </tr>
     * <tr>
     *  <th>命名空间URI参数</th> <th>返回的前缀值</th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     *  <td>&lt; default Namespace URI&gt; </td> <td> <code> XMLConstants.DEFAULT_NS_PREFIX </code>("")
     * </td>
     * </tr>
     * <tr>
     *  <td>绑定到当前作用域中的Namespace URI的命名空间URI </td> <td>前缀,如果多个前缀绑定到当前作用域中的命名空间URI,则返回一个任意前缀(其选择取决于实现) </td>
     * </tr>
     * <tr>
     *  <td>未绑定的命名空间URI </td> <td> <code> null </code> </td>
     * </tr>
     * <tr>
     *  <td> <code> XMLConstants.XML_NS_URI </code>("http://www.w3.org/XML/1998/namespace")</td> <td> <code>
     *  XMLConstants.XML_NS_PREFIX </code> "xml")</td>。
     * </tr>
     * <tr>
     *  <td> <code> XMLConstants.XMLNS_ATTRIBUTE_NS_URI </code>("http://www.w3.org/2000/xmlns/")</td> <td> <code>
     *  XMLConstants.XMLNS_ATTRIBUTE </code> xmlns")</td>。
     * </tr>
     * <tr>
     *  <td> <code> null </code> </td> <td> <code>抛出IllegalArgumentException </code> </td>
     * </tr>
     * </tbody>
     * </table>
     * 
     * 
     * @param namespaceURI URI of Namespace to lookup
     *
     * @return prefix bound to Namespace URI in current context
     *
     * @throws IllegalArgumentException When <code>namespaceURI</code> is
     *   <code>null</code>
     */
    String getPrefix(String namespaceURI);

    /**
     * <p>Get all prefixes bound to a Namespace URI in the current
     * scope.</p>
     *
     * <p>An Iterator over String elements is returned in an arbitrary,
     * <strong>implementation dependent</strong>, order.</p>
     *
     * <p><strong>The <code>Iterator</code> is
     * <em>not</em> modifiable.  e.g. the
     * <code>remove()</code> method will throw
     * <code>UnsupportedOperationException</code>.</strong></p>
     *
     * <p>When requesting prefixes by Namespace URI, the following
     * table describes the returned prefixes value for all Namespace
     * URI values:</p>
     *
     * <table border="2" rules="all" cellpadding="4">
     *   <thead>
     *     <tr>
     *       <th align="center" colspan="2"><code>
     *         getPrefixes(namespaceURI)</code> return value for
     *         specified Namespace URIs</th>
     *     </tr>
     *     <tr>
     *       <th>Namespace URI parameter</th>
     *       <th>prefixes value returned</th>
     *     </tr>
     *   </thead>
     *   <tbody>
     *     <tr>
     *       <td>bound Namespace URI,
     *         including the &lt;default Namespace URI&gt;</td>
     *       <td>
     *         <code>Iterator</code> over prefixes bound to Namespace URI in
     *         the current scope in an arbitrary,
     *         <strong>implementation dependent</strong>,
     *         order
     *       </td>
     *     </tr>
     *     <tr>
     *       <td>unbound Namespace URI</td>
     *       <td>empty <code>Iterator</code></td>
     *     </tr>
     *     <tr>
     *       <td><code>XMLConstants.XML_NS_URI</code>
     *           ("http://www.w3.org/XML/1998/namespace")</td>
     *       <td><code>Iterator</code> with one element set to
     *         <code>XMLConstants.XML_NS_PREFIX</code> ("xml")</td>
     *     </tr>
     *     <tr>
     *       <td><code>XMLConstants.XMLNS_ATTRIBUTE_NS_URI</code>
     *           ("http://www.w3.org/2000/xmlns/")</td>
     *       <td><code>Iterator</code> with one element set to
     *         <code>XMLConstants.XMLNS_ATTRIBUTE</code> ("xmlns")</td>
     *     </tr>
     *     <tr>
     *       <td><code>null</code></td>
     *       <td><code>IllegalArgumentException</code> is thrown</td>
     *     </tr>
     *   </tbody>
     * </table>
     *
     * <p>
     *  <p>获取绑定到当前作用域中的命名空间URI的所有前缀。</p>
     * 
     *  <p>字符串元素的迭代器按任意<strong>实现相关</strong>顺序返回。</p>
     * 
     *  <p> <strong> <code>迭代器</code> <em>不是</em>可修改。
     * 例如<code> remove()</code>方法会引发<code> UnsupportedOperationException </code>。</strong> </p>。
     * 
     *  <p>当通过命名空间URI请求前缀时,下表描述了所有Namespace URI值的返回前缀值：</p>
     * 
     * <table border="2" rules="all" cellpadding="4">
     * <thead>
     * <tr>
     * <th align ="center"colspan ="2"> <code> getPrefixes(namespaceURI)</code>为指定的命名空间URI返回值</th>
     * </tr>
     * <tr>
     *  <th>命名空间URI参数</th> <th>返回的前缀值</th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     *  <td>绑定的命名空间URI,包括&lt;默认命名空间URI&gt; </td>
     * 
     * @param namespaceURI URI of Namespace to lookup
     *
     * @return <code>Iterator</code> for all prefixes bound to the
     *   Namespace URI in the current scope
     *
     * @throws IllegalArgumentException When <code>namespaceURI</code> is
     *   <code>null</code>
     */
    Iterator getPrefixes(String namespaceURI);
}
