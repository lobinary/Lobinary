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

// ContentHandler.java - handle main document content.
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the public domain.
// $Id: ContentHandler.java,v 1.2 2004/11/03 22:44:51 jsuttor Exp $

package org.xml.sax;


/**
 * Receive notification of the logical content of a document.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This is the main interface that most SAX applications
 * implement: if the application needs to be informed of basic parsing
 * events, it implements this interface and registers an instance with
 * the SAX parser using the {@link org.xml.sax.XMLReader#setContentHandler
 * setContentHandler} method.  The parser uses the instance to report
 * basic document-related events like the start and end of elements
 * and character data.</p>
 *
 * <p>The order of events in this interface is very important, and
 * mirrors the order of information in the document itself.  For
 * example, all of an element's content (character data, processing
 * instructions, and/or subelements) will appear, in order, between
 * the startElement event and the corresponding endElement event.</p>
 *
 * <p>This interface is similar to the now-deprecated SAX 1.0
 * DocumentHandler interface, but it adds support for Namespaces
 * and for reporting skipped entities (in non-validating XML
 * processors).</p>
 *
 * <p>Implementors should note that there is also a
 * <code>ContentHandler</code> class in the <code>java.net</code>
 * package; that means that it's probably a bad idea to do</p>
 *
 * <pre>import java.net.*;
 * import org.xml.sax.*;
 * </pre>
 *
 * <p>In fact, "import ...*" is usually a sign of sloppy programming
 * anyway, so the user should consider this a feature rather than a
 * bug.</p>
 *
 * <p>
 *  接收文档的逻辑内容的通知。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>这是大多数SAX应用程序实现的主要接口：如果应用程序需要通知基本解析事件,它将使用{@link org.xml.sax.XMLReader)实现此接口并向SAX解析器注册一个实例#setContentHandler setContentHandler}
 * 方法。
 * 解析器使用实例来报告基本的文档相关事件,如元素的开始和结束以及字符数据。</p>。
 * 
 *  <p>此界面中的事件顺序非常重要,并且反映文档本身中的信息顺序。例如,元素的所有内容(字符数据,处理指令和/或子元素)将按顺序出现在startElement事件和相应的endElement事件之间。
 * </p>。
 * 
 *  <p>此接口类似于现在已弃用的SAX 1.0 DocumentHandler接口,但它添加了对Namespaces的支持,并用于报告跳过的实体(在非验证XML处理器中)。</p>
 * 
 * <p>实现者应该注意,在<code> java.net </code>包中还有一个<code> ContentHandler </code>这意味着它可能是一个坏主意做</p>
 * 
 *  <pre> import java.net。*; import org.xml.sax。*;
 * </pre>
 * 
 *  <p>事实上,"import ... *"通常是一个粗糙的编程的标志,所以用户应该考虑这个功能而不是一个bug。</p>
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.XMLReader
 * @see org.xml.sax.DTDHandler
 * @see org.xml.sax.ErrorHandler
 */
public interface ContentHandler
{

    /**
     * Receive an object for locating the origin of SAX document events.
     *
     * <p>SAX parsers are strongly encouraged (though not absolutely
     * required) to supply a locator: if it does so, it must supply
     * the locator to the application by invoking this method before
     * invoking any of the other methods in the ContentHandler
     * interface.</p>
     *
     * <p>The locator allows the application to determine the end
     * position of any document-related event, even if the parser is
     * not reporting an error.  Typically, the application will
     * use this information for reporting its own errors (such as
     * character content that does not match an application's
     * business rules).  The information returned by the locator
     * is probably not sufficient for use with a search engine.</p>
     *
     * <p>Note that the locator will return correct information only
     * during the invocation SAX event callbacks after
     * {@link #startDocument startDocument} returns and before
     * {@link #endDocument endDocument} is called.  The
     * application should not attempt to use it at any other time.</p>
     *
     * <p>
     *  接收用于查找SAX文档事件的原点的对象。
     * 
     *  <p>强烈鼓励(虽然不是绝对需要)SAX解析器提供一个定位器：如果它这样做,它必须提供定位器到应用程序通过调用此方法,然后调用任何其他方法在ContentHandler接口。 p>
     * 
     *  <p>定位器允许应用程序确定任何文档相关事件的结束位置,即使解析器未报告错误。通常,应用程序将使用此信息来报告其自身的错误(例如,字符内容与应用程序的业务规则不匹配)。
     * 定位器返回的信息可能不足以用于搜索引擎。</p>。
     * 
     *  <p>请注意,只有在调用{@link #startDocument startDocument}返回之后和调用{@link #endDocument endDocument}之前的调用SAX事件回调期
     * 间,定位器才会返回正确的信息。
     * 该应用程序不应尝试在任何其他时间使用它。</p>。
     * 
     * 
     * @param locator an object that can return the location of
     *                any SAX document event
     * @see org.xml.sax.Locator
     */
    public void setDocumentLocator (Locator locator);


    /**
     * Receive notification of the beginning of a document.
     *
     * <p>The SAX parser will invoke this method only once, before any
     * other event callbacks (except for {@link #setDocumentLocator
     * setDocumentLocator}).</p>
     *
     * <p>
     *  接收文档开头的通知。
     * 
     * <p>在任何其他事件回调之前(除{@link #setDocumentLocator setDocumentLocator}),SAX解析器只会调用此方法一次。</p>
     * 
     * 
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     * @see #endDocument
     */
    public void startDocument ()
        throws SAXException;


    /**
     * Receive notification of the end of a document.
     *
     * <p><strong>There is an apparent contradiction between the
     * documentation for this method and the documentation for {@link
     * org.xml.sax.ErrorHandler#fatalError}.  Until this ambiguity is
     * resolved in a future major release, clients should make no
     * assumptions about whether endDocument() will or will not be
     * invoked when the parser has reported a fatalError() or thrown
     * an exception.</strong></p>
     *
     * <p>The SAX parser will invoke this method only once, and it will
     * be the last method invoked during the parse.  The parser shall
     * not invoke this method until it has either abandoned parsing
     * (because of an unrecoverable error) or reached the end of
     * input.</p>
     *
     * <p>
     *  接收文档结束的通知。
     * 
     *  <p> <strong>此方法的文档与{@link org.xml.sax.ErrorHandler#fatalError}的文档之间存在明显的矛盾。
     * 在未来的主要版本中解决这种模糊性之前,客户端不应该假设endDocument()在解析器报告了fatalError()或抛出异常时是否将被调用。</strong>。
     * 
     *  <p> SAX解析器将仅调用此方法一次,它将是解析期间调用的最后一个方法。解析器不应该调用此方法,直到它放弃了解析(因为一个不可恢复的错误)或到达输入的结束。</p>
     * 
     * 
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     * @see #startDocument
     */
    public void endDocument()
        throws SAXException;


    /**
     * Begin the scope of a prefix-URI Namespace mapping.
     *
     * <p>The information from this event is not necessary for
     * normal Namespace processing: the SAX XML reader will
     * automatically replace prefixes for element and attribute
     * names when the <code>http://xml.org/sax/features/namespaces</code>
     * feature is <var>true</var> (the default).</p>
     *
     * <p>There are cases, however, when applications need to
     * use prefixes in character data or in attribute values,
     * where they cannot safely be expanded automatically; the
     * start/endPrefixMapping event supplies the information
     * to the application to expand prefixes in those contexts
     * itself, if necessary.</p>
     *
     * <p>Note that start/endPrefixMapping events are not
     * guaranteed to be properly nested relative to each other:
     * all startPrefixMapping events will occur immediately before the
     * corresponding {@link #startElement startElement} event,
     * and all {@link #endPrefixMapping endPrefixMapping}
     * events will occur immediately after the corresponding
     * {@link #endElement endElement} event,
     * but their order is not otherwise
     * guaranteed.</p>
     *
     * <p>There should never be start/endPrefixMapping events for the
     * "xml" prefix, since it is predeclared and immutable.</p>
     *
     * <p>
     *  开始前缀URI范围的命名空间映射。
     * 
     *  <p>此事件的信息对于正常的命名空间处理不是必需的：当<code> http://xml.org/sax/features/namespaces </code时,SAX XML阅读器将自动替换元素和属性名称的前缀>
     *  feature是<var> true </var>(默认值)。
     * </p>。
     * 
     *  <p>然而,有些情况下,应用程序需要在字符数据或属性值中使用前缀,在那里它们不能安全地自动扩展; start / endPrefixMapping事件将信息提供给应用程序,以便在必要时在这些上下文中扩
     * 展前缀。
     * </p>。
     * 
     * <p>请注意,start / endPrefixMapping事件无法保证相对于彼此正确嵌套：所有startPrefixMapping事件都会在对应的{@link #startElement startElement}
     * 事件之前发生,所有{@link #endPrefixMapping endPrefixMapping}事件在对应的{@link #endElement endElement}事件之后立即发生,但其顺序不
     * 以其他方式保证。
     * </p>。
     * 
     *  <p>对于"xml"前缀,不应该有start / endPrefixMapping事件,因为它是预先声明的和不可变的。</p>
     * 
     * 
     * @param prefix the Namespace prefix being declared.
     *  An empty string is used for the default element namespace,
     *  which has no prefix.
     * @param uri the Namespace URI the prefix is mapped to
     * @throws org.xml.sax.SAXException the client may throw
     *            an exception during processing
     * @see #endPrefixMapping
     * @see #startElement
     */
    public void startPrefixMapping (String prefix, String uri)
        throws SAXException;


    /**
     * End the scope of a prefix-URI mapping.
     *
     * <p>See {@link #startPrefixMapping startPrefixMapping} for
     * details.  These events will always occur immediately after the
     * corresponding {@link #endElement endElement} event, but the order of
     * {@link #endPrefixMapping endPrefixMapping} events is not otherwise
     * guaranteed.</p>
     *
     * <p>
     *  结束前缀URI映射的范围。
     * 
     *  <p>有关详情,请参阅{@link #startPrefixMapping startPrefixMapping}。
     * 这些事件将始终在相应的{@link #endElement endElement}事件之后立即发生,但{@link #endPrefixMapping endPrefixMapping}事件的顺序不能保
     * 证。
     *  <p>有关详情,请参阅{@link #startPrefixMapping startPrefixMapping}。</p>。
     * 
     * 
     * @param prefix the prefix that was being mapped.
     *  This is the empty string when a default mapping scope ends.
     * @throws org.xml.sax.SAXException the client may throw
     *            an exception during processing
     * @see #startPrefixMapping
     * @see #endElement
     */
    public void endPrefixMapping (String prefix)
        throws SAXException;


    /**
     * Receive notification of the beginning of an element.
     *
     * <p>The Parser will invoke this method at the beginning of every
     * element in the XML document; there will be a corresponding
     * {@link #endElement endElement} event for every startElement event
     * (even when the element is empty). All of the element's content will be
     * reported, in order, before the corresponding endElement
     * event.</p>
     *
     * <p>This event allows up to three name components for each
     * element:</p>
     *
     * <ol>
     * <li>the Namespace URI;</li>
     * <li>the local name; and</li>
     * <li>the qualified (prefixed) name.</li>
     * </ol>
     *
     * <p>Any or all of these may be provided, depending on the
     * values of the <var>http://xml.org/sax/features/namespaces</var>
     * and the <var>http://xml.org/sax/features/namespace-prefixes</var>
     * properties:</p>
     *
     * <ul>
     * <li>the Namespace URI and local name are required when
     * the namespaces property is <var>true</var> (the default), and are
     * optional when the namespaces property is <var>false</var> (if one is
     * specified, both must be);</li>
     * <li>the qualified name is required when the namespace-prefixes property
     * is <var>true</var>, and is optional when the namespace-prefixes property
     * is <var>false</var> (the default).</li>
     * </ul>
     *
     * <p>Note that the attribute list provided will contain only
     * attributes with explicit values (specified or defaulted):
     * #IMPLIED attributes will be omitted.  The attribute list
     * will contain attributes used for Namespace declarations
     * (xmlns* attributes) only if the
     * <code>http://xml.org/sax/features/namespace-prefixes</code>
     * property is true (it is false by default, and support for a
     * true value is optional).</p>
     *
     * <p>Like {@link #characters characters()}, attribute values may have
     * characters that need more than one <code>char</code> value.  </p>
     *
     * <p>
     *  接收元素开头的通知。
     * 
     *  <p>解析器将在XML文档中的每个元素的开头调用此方法;每个startElement事件都会有一个相应的{@link #endElement endElement}事件(即使元素为空)。
     * 将在相应的endElement事件之前按顺序报告所有元素的内容。</p>。
     * 
     *  <p>此事件最多允许每个元素使用三个名称组件：</p>
     * 
     * <ol>
     *  <li>命名空间URI; </li> <li>本地名称;和</li> <li>限定(前缀)名称。</li>
     * </ol>
     * 
     * <p>根据<var> http://xml.org/sax/features/namespaces </var>和<var> http://xml.org的值,可以提供其中任何一个或全部/ sax / 
     * features / namespace-prefixes </var>属性：</p>。
     * 
     * <ul>
     *  <li>当namespaces属性为<var> true </var>(默认值)时,必须使用命名空间URI和本地名称;如果namespaces属性为<var> false </var> ,两个必须都是
     * ); </li> <li>当namespace-prefixes属性为<var> true </var>时,限定名称是必需的,当namespace-prefixes属性为<var> false < var>
     * (默认值)。
     * </li>。
     * </ul>
     * 
     *  <p>请注意,提供的属性列表将只包含具有显式值(指定或默认值)的属性：#IMPLIED属性将被省略。
     * 只有当<code> http://xml.org/sax/features/namespace-prefixes </code>属性为true时,属性列表才会包含用于命名空间声明(xmlns *属性)的
     * 属性(默认情况下为false,并支持真值是可选的)。
     *  <p>请注意,提供的属性列表将只包含具有显式值(指定或默认值)的属性：#IMPLIED属性将被省略。</p>。
     * 
     *  <p>与{@link #characters characters()}类似,属性值可能包含需要多个<code> char </code>值的字符。 </p>
     * 
     * 
     * @param uri the Namespace URI, or the empty string if the
     *        element has no Namespace URI or if Namespace
     *        processing is not being performed
     * @param localName the local name (without prefix), or the
     *        empty string if Namespace processing is not being
     *        performed
     * @param qName the qualified name (with prefix), or the
     *        empty string if qualified names are not available
     * @param atts the attributes attached to the element.  If
     *        there are no attributes, it shall be an empty
     *        Attributes object.  The value of this object after
     *        startElement returns is undefined
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     * @see #endElement
     * @see org.xml.sax.Attributes
     * @see org.xml.sax.helpers.AttributesImpl
     */
    public void startElement (String uri, String localName,
                              String qName, Attributes atts)
        throws SAXException;


    /**
     * Receive notification of the end of an element.
     *
     * <p>The SAX parser will invoke this method at the end of every
     * element in the XML document; there will be a corresponding
     * {@link #startElement startElement} event for every endElement
     * event (even when the element is empty).</p>
     *
     * <p>For information on the names, see startElement.</p>
     *
     * <p>
     *  接收元素结束的通知。
     * 
     *  <p> SAX解析器将在XML文档中每个元素的末尾调用此方法;每个endElement事件都会有一个相应的{@link #startElement startElement}事件(即使元素为空)。
     * </p>。
     * 
     *  <p>有关名称的信息,请参见startElement。</p>
     * 
     * 
     * @param uri the Namespace URI, or the empty string if the
     *        element has no Namespace URI or if Namespace
     *        processing is not being performed
     * @param localName the local name (without prefix), or the
     *        empty string if Namespace processing is not being
     *        performed
     * @param qName the qualified XML name (with prefix), or the
     *        empty string if qualified names are not available
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     */
    public void endElement (String uri, String localName,
                            String qName)
        throws SAXException;


    /**
     * Receive notification of character data.
     *
     * <p>The Parser will call this method to report each chunk of
     * character data.  SAX parsers may return all contiguous character
     * data in a single chunk, or they may split it into several
     * chunks; however, all of the characters in any single event
     * must come from the same external entity so that the Locator
     * provides useful information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * <p>Individual characters may consist of more than one Java
     * <code>char</code> value.  There are two important cases where this
     * happens, because characters can't be represented in just sixteen bits.
     * In one case, characters are represented in a <em>Surrogate Pair</em>,
     * using two special Unicode values. Such characters are in the so-called
     * "Astral Planes", with a code point above U+FFFF.  A second case involves
     * composite characters, such as a base character combining with one or
     * more accent characters. </p>
     *
     * <p> Your code should not assume that algorithms using
     * <code>char</code>-at-a-time idioms will be working in character
     * units; in some cases they will split characters.  This is relevant
     * wherever XML permits arbitrary characters, such as attribute values,
     * processing instruction data, and comments as well as in data reported
     * from this method.  It's also generally relevant whenever Java code
     * manipulates internationalized text; the issue isn't unique to XML.</p>
     *
     * <p>Note that some parsers will report whitespace in element
     * content using the {@link #ignorableWhitespace ignorableWhitespace}
     * method rather than this one (validating parsers <em>must</em>
     * do so).</p>
     *
     * <p>
     * 接收字符数据的通知。
     * 
     *  <p>解析器将调用此方法来报告每个字符数据块。
     *  SAX解析器可以返回单个块中的所有连续字符数据,或者它们可以将其拆分成几个块;但是,任何单个事件中的所有字符都必须来自同一外部实体,以便定位器提供有用的信息。</p>。
     * 
     *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
     * 
     *  <p>各个字符可以由多个Java <code> char </code>值组成。有两种情况发生,因为字符不能以16位表示。
     * 在一种情况下,使用两个特殊的Unicode值在<em>代理对</em>中表示字符。这样的字符在所谓的"星形平面"中,其码点在U + FFFF之上。
     * 第二种情况涉及复合字符,例如与一个或多个重音字符组合的基本字符。 </p>。
     * 
     *  <p>您的代码不应假设使用<code> char </code>一次性成语的算法将以字符单位工作;在某些情况下,它们会拆分字符。
     * 这在XML允许任意字符(如属性值,处理指令数据和注释以及从此方法报告的数据)的任何位置都是相关的。当Java代码操作国际化文本时,它通常也是相关的;该问题不是XML独有的。</p>。
     * 
     * <p>请注意,有些解析器会使用{@link #ignorableWhitespace ignorableWhitespace}方法(而不是此方法)来验证元素内容中的空格。
     * (<em>必须</em>)</p>。
     * 
     * 
     * @param ch the characters from the XML document
     * @param start the start position in the array
     * @param length the number of characters to read from the array
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     * @see #ignorableWhitespace
     * @see org.xml.sax.Locator
     */
    public void characters (char ch[], int start, int length)
        throws SAXException;


    /**
     * Receive notification of ignorable whitespace in element content.
     *
     * <p>Validating Parsers must use this method to report each chunk
     * of whitespace in element content (see the W3C XML 1.0
     * recommendation, section 2.10): non-validating parsers may also
     * use this method if they are capable of parsing and using
     * content models.</p>
     *
     * <p>SAX parsers may return all contiguous whitespace in a single
     * chunk, or they may split it into several chunks; however, all of
     * the characters in any single event must come from the same
     * external entity, so that the Locator provides useful
     * information.</p>
     *
     * <p>The application must not attempt to read from the array
     * outside of the specified range.</p>
     *
     * <p>
     *  在元素内容中接收可忽略的空格的通知。
     * 
     *  <p>验证Parsers必须使用此方法在元素内容中报告每个空白块(请参阅W3C XML 1.0建议,2.10节)：如果非验证解析器能够解析和使用内容模型,则它们也可以使用此方法。 </p>
     * 
     *  <p> SAX解析器可以返回单个块中的所有连续空格,或者它们可以将其拆分成几个块;然而,任何单个事件中的所有字符必须来自同一外部实体,以便定位器提供有用的信息。</p>
     * 
     *  <p>应用程序不得尝试从指定范围之外的数组读取。</p>
     * 
     * 
     * @param ch the characters from the XML document
     * @param start the start position in the array
     * @param length the number of characters to read from the array
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     * @see #characters
     */
    public void ignorableWhitespace (char ch[], int start, int length)
        throws SAXException;


    /**
     * Receive notification of a processing instruction.
     *
     * <p>The Parser will invoke this method once for each processing
     * instruction found: note that processing instructions may occur
     * before or after the main document element.</p>
     *
     * <p>A SAX parser must never report an XML declaration (XML 1.0,
     * section 2.8) or a text declaration (XML 1.0, section 4.3.1)
     * using this method.</p>
     *
     * <p>Like {@link #characters characters()}, processing instruction
     * data may have characters that need more than one <code>char</code>
     * value. </p>
     *
     * <p>
     *  接收处理指令的通知。
     * 
     *  <p>对于找到的每个处理指令,解析器将调用此方法一次：请注意,处理指令可能发生在主文档元素之前或之后。</p>
     * 
     *  <p> SAX解析器不得使用此方法报告XML声明(XML 1.0,第2.8节)或文本声明(XML 1.0,第4.3.1节)。</p>
     * 
     *  <p>与{@link #characters characters()}类似,处理指令数据可能包含需要多个<code> char </code>值的字符。 </p>
     * 
     * 
     * @param target the processing instruction target
     * @param data the processing instruction data, or null if
     *        none was supplied.  The data does not include any
     *        whitespace separating it from the target
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     */
    public void processingInstruction (String target, String data)
        throws SAXException;


    /**
     * Receive notification of a skipped entity.
     * This is not called for entity references within markup constructs
     * such as element start tags or markup declarations.  (The XML
     * recommendation requires reporting skipped external entities.
     * SAX also reports internal entity expansion/non-expansion, except
     * within markup constructs.)
     *
     * <p>The Parser will invoke this method each time the entity is
     * skipped.  Non-validating processors may skip entities if they
     * have not seen the declarations (because, for example, the
     * entity was declared in an external DTD subset).  All processors
     * may skip external entities, depending on the values of the
     * <code>http://xml.org/sax/features/external-general-entities</code>
     * and the
     * <code>http://xml.org/sax/features/external-parameter-entities</code>
     * properties.</p>
     *
     * <p>
     * 
     * @param name the name of the skipped entity.  If it is a
     *        parameter entity, the name will begin with '%', and if
     *        it is the external DTD subset, it will be the string
     *        "[dtd]"
     * @throws org.xml.sax.SAXException any SAX exception, possibly
     *            wrapping another exception
     */
    public void skippedEntity (String name)
        throws SAXException;
}

// end of ContentHandler.java
