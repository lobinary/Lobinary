/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究信息学和数学联合会,庆应大学)保留所有权利本作品根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证
 * 
 *  [1] http：// wwww3org / Consortium / Legal / 2002 / copyright-software-20021231
 * 
 */

package org.w3c.dom.ls;

/**
 *  This interface represents an input source for data.
 * <p> This interface allows an application to encapsulate information about
 * an input source in a single object, which may include a public
 * identifier, a system identifier, a byte stream (possibly with a specified
 * encoding), a base URI, and/or a character stream.
 * <p> The exact definitions of a byte stream and a character stream are
 * binding dependent.
 * <p> The application is expected to provide objects that implement this
 * interface whenever such objects are needed. The application can either
 * provide its own objects that implement this interface, or it can use the
 * generic factory method <code>DOMImplementationLS.createLSInput()</code>
 * to create objects that implement this interface.
 * <p> The <code>LSParser</code> will use the <code>LSInput</code> object to
 * determine how to read data. The <code>LSParser</code> will look at the
 * different inputs specified in the <code>LSInput</code> in the following
 * order to know which one to read from, the first one that is not null and
 * not an empty string will be used:
 * <ol>
 * <li> <code>LSInput.characterStream</code>
 * </li>
 * <li>
 * <code>LSInput.byteStream</code>
 * </li>
 * <li> <code>LSInput.stringData</code>
 * </li>
 * <li>
 * <code>LSInput.systemId</code>
 * </li>
 * <li> <code>LSInput.publicId</code>
 * </li>
 * </ol>
 * <p> If all inputs are null, the <code>LSParser</code> will report a
 * <code>DOMError</code> with its <code>DOMError.type</code> set to
 * <code>"no-input-specified"</code> and its <code>DOMError.severity</code>
 * set to <code>DOMError.SEVERITY_FATAL_ERROR</code>.
 * <p> <code>LSInput</code> objects belong to the application. The DOM
 * implementation will never modify them (though it may make copies and
 * modify the copies, if necessary).
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 * <p>
 * 该接口表示数据的输入源。该接口允许应用将关于输入源的信息封装在单个对象中,该单个对象可以包括公共标识符,系统标识符,字节流(可能具有指定的编码)基本URI和/或字符流<p>。
 * 字节流和字符流的确切定义是绑定依赖</p>应用程序期望提供在需要这样的对象时实现该接口的对象。
 * 应用程序可以提供实现此接口的自己的对象,或者它可以使用通用工厂方法<code> DOMImplementationLScreateLSInput()</code>创建实现此接口的对象<p> LSPars
 * er </code>将使用<code> LSInput </code>对象来确定如何读取数据<code> LSParser </code>会查看<code> > LSInput </code>以下列顺序
 * 知道要从哪个读取,第一个不为null并且不是空字符串将被使用：。
 * 字节流和字符流的确切定义是绑定依赖</p>应用程序期望提供在需要这样的对象时实现该接口的对象。
 * <ol>
 * <li> <code> LSInputcharacterStream </code>
 * </li>
 * <li>
 *  <code> LSInputbyteStream </code>
 * </li>
 *  <li> <code> LSInputstringData </code>
 * </li>
 * <li>
 *  <code> LSInputsystemId </code>
 * </li>
 *  <li> <code> LSInputpublicId </code>
 * </li>
 * </ol>
 *  <p>如果所有输入为null,则<code> LSParser </code>将报告<code> DOMError </code>,其<code> DOMErrortype </code>设置为<code>
 * "no-input-specified "</code>和它的<code> DOMErrorseverity </code>设置为<code> DOMErrorSEVERITY_FATAL_ERROR 
 * </code> <p> <code> LSInput </code>对象属于应用程序DOM实现永远不会修改它们如有必要,可以复制和修改副本)<p>另请参阅<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-LS-20040407'>
 * 文档对象模型( DOM)3级加载并保存规范</a>。
 * 
 */
public interface LSInput {
    /**
     *  An attribute of a language and binding dependent type that represents
     * a stream of 16-bit units. The application must encode the stream
     * using UTF-16 (defined in [Unicode] and in [ISO/IEC 10646]). It is not a requirement to have an XML declaration when
     * using character streams. If an XML declaration is present, the value
     * of the encoding attribute will be ignored.
     * <p>
     * 表示16位单位流的语言和绑定依赖类型的属性应用程序必须使用UTF-16(在[Unicode]和[ISO / IEC 10646]中定义)对流进行编码。
     * 不要求使用字符流时的XML声明如果存在XML声明,则将忽略encoding属性的值。
     * 
     */
    public java.io.Reader getCharacterStream();
    /**
     *  An attribute of a language and binding dependent type that represents
     * a stream of 16-bit units. The application must encode the stream
     * using UTF-16 (defined in [Unicode] and in [ISO/IEC 10646]). It is not a requirement to have an XML declaration when
     * using character streams. If an XML declaration is present, the value
     * of the encoding attribute will be ignored.
     * <p>
     *  表示16位单位流的语言和绑定依赖类型的属性应用程序必须使用UTF-16(在[Unicode]和[ISO / IEC 10646]中定义)对流进行编码。
     * 不要求使用字符流时的XML声明如果存在XML声明,则将忽略encoding属性的值。
     * 
     */
    public void setCharacterStream(java.io.Reader characterStream);

    /**
     *  An attribute of a language and binding dependent type that represents
     * a stream of bytes.
     * <br> If the application knows the character encoding of the byte
     * stream, it should set the encoding attribute. Setting the encoding in
     * this way will override any encoding specified in an XML declaration
     * in the data.
     * <p>
     * 表示字节流的语言和绑定依赖类型的属性<br>如果应用程序知道字节流的字符编码,则应设置encoding属性以这种方式设置编码将覆盖XML中指定的任何编码声明在数据中
     * 
     */
    public java.io.InputStream getByteStream();
    /**
     *  An attribute of a language and binding dependent type that represents
     * a stream of bytes.
     * <br> If the application knows the character encoding of the byte
     * stream, it should set the encoding attribute. Setting the encoding in
     * this way will override any encoding specified in an XML declaration
     * in the data.
     * <p>
     *  表示字节流的语言和绑定依赖类型的属性<br>如果应用程序知道字节流的字符编码,则应设置encoding属性以这种方式设置编码将覆盖XML中指定的任何编码声明在数据中
     * 
     */
    public void setByteStream(java.io.InputStream byteStream);

    /**
     *  String data to parse. If provided, this will always be treated as a
     * sequence of 16-bit units (UTF-16 encoded characters). It is not a
     * requirement to have an XML declaration when using
     * <code>stringData</code>. If an XML declaration is present, the value
     * of the encoding attribute will be ignored.
     * <p>
     * 要解析的字符串数据如果提供,这将始终被视为16位单位(UTF-16编码字符)的序列。使用<code> stringData </code>时不需要有XML声明如果XML声明,则编码属性的值将被忽略
     * 
     */
    public String getStringData();
    /**
     *  String data to parse. If provided, this will always be treated as a
     * sequence of 16-bit units (UTF-16 encoded characters). It is not a
     * requirement to have an XML declaration when using
     * <code>stringData</code>. If an XML declaration is present, the value
     * of the encoding attribute will be ignored.
     * <p>
     *  要解析的字符串数据如果提供,这将始终被视为16位单位(UTF-16编码字符)的序列。使用<code> stringData </code>时不需要有XML声明如果XML声明,则编码属性的值将被忽略
     * 
     */
    public void setStringData(String stringData);

    /**
     *  The system identifier, a URI reference [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>], for this
     * input source. The system identifier is optional if there is a byte
     * stream, a character stream, or string data. It is still useful to
     * provide one, since the application will use it to resolve any
     * relative URIs and can include it in error messages and warnings. (The
     * LSParser will only attempt to fetch the resource identified by the
     * URI reference if there is no other input available in the input
     * source.)
     * <br> If the application knows the character encoding of the object
     * pointed to by the system identifier, it can set the encoding using
     * the <code>encoding</code> attribute.
     * <br> If the specified system ID is a relative URI reference (see
     * section 5 in [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]), the DOM
     * implementation will attempt to resolve the relative URI with the
     * <code>baseURI</code> as the base, if that fails, the behavior is
     * implementation dependent.
     * <p>
     * 系统标识符,用于此输入源的URI引用[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]如果存在字节流,则系统标识符是可选的字
     * 符流或字符串数​​据。
     * 提供一个仍然有用,因为应用程序将使用它来解析任何相关的URI,并将其包含在错误消息和警告中(LSParser只会尝试获取由URI引用标识的资源if在输入源中没有其他可用输入)<br>如果应用程序知道系统
     * 标识符指向的对象的字符编码,则可以使用<code> encoding </code>属性设置编码<br>如果指定的系统ID是相对URI引用(请参阅[<a href ='http：// wwwietforg / rfc / rfc2396txt'>
     *  IETF RFC 2396 </a>]),DOM实现将尝试使用<code> baseURI </code>作为基础解析相对URI,如果失败,行为是依赖于实现。
     * 
     */
    public String getSystemId();
    /**
     *  The system identifier, a URI reference [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>], for this
     * input source. The system identifier is optional if there is a byte
     * stream, a character stream, or string data. It is still useful to
     * provide one, since the application will use it to resolve any
     * relative URIs and can include it in error messages and warnings. (The
     * LSParser will only attempt to fetch the resource identified by the
     * URI reference if there is no other input available in the input
     * source.)
     * <br> If the application knows the character encoding of the object
     * pointed to by the system identifier, it can set the encoding using
     * the <code>encoding</code> attribute.
     * <br> If the specified system ID is a relative URI reference (see
     * section 5 in [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]), the DOM
     * implementation will attempt to resolve the relative URI with the
     * <code>baseURI</code> as the base, if that fails, the behavior is
     * implementation dependent.
     * <p>
     * 系统标识符,用于此输入源的URI引用[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]如果存在字节流,则系统标识符是可选的字
     * 符流或字符串数​​据。
     * 提供一个仍然有用,因为应用程序将使用它来解析任何相关的URI,并将其包含在错误消息和警告中(LSParser只会尝试获取由URI引用标识的资源if在输入源中没有其他可用输入)<br>如果应用程序知道系统
     * 标识符指向的对象的字符编码,则可以使用<code> encoding </code>属性设置编码<br>如果指定的系统ID是相对URI引用(请参阅[<a href ='http：// wwwietforg / rfc / rfc2396txt'>
     *  IETF RFC 2396 </a>]),DOM实现将尝试使用<code> baseURI </code>作为基础解析相对URI,如果失败,行为是依赖于实现。
     * 
     */
    public void setSystemId(String systemId);

    /**
     *  The public identifier for this input source. This may be mapped to an
     * input source using an implementation dependent mechanism (such as
     * catalogues or other mappings). The public identifier, if specified,
     * may also be reported as part of the location information when errors
     * are reported.
     * <p>
     * 此输入源的公共标识符这可以使用实现相关机制(例如目录或其他映射)映射到输入源。如果指定,公共标识符也可以在报告错误时报告为位置信息的一部分
     * 
     */
    public String getPublicId();
    /**
     *  The public identifier for this input source. This may be mapped to an
     * input source using an implementation dependent mechanism (such as
     * catalogues or other mappings). The public identifier, if specified,
     * may also be reported as part of the location information when errors
     * are reported.
     * <p>
     *  此输入源的公共标识符这可以使用实现相关机制(例如目录或其他映射)映射到输入源。如果指定,公共标识符也可以在报告错误时报告为位置信息的一部分
     * 
     */
    public void setPublicId(String publicId);

    /**
     *  The base URI to be used (see section 5.1.4 in [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]) for
     * resolving a relative <code>systemId</code> to an absolute URI.
     * <br> If, when used, the base URI is itself a relative URI, an empty
     * string, or null, the behavior is implementation dependent.
     * <p>
     * 要使用的基本URI(请参阅[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]中的第514节),用于将相对的<code> sy
     * stemId </code>解析为绝对URI <br>如果在使用时,基本URI本身是相对URI,空字符串或null,则行为是实现相关的。
     * 
     */
    public String getBaseURI();
    /**
     *  The base URI to be used (see section 5.1.4 in [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]) for
     * resolving a relative <code>systemId</code> to an absolute URI.
     * <br> If, when used, the base URI is itself a relative URI, an empty
     * string, or null, the behavior is implementation dependent.
     * <p>
     *  要使用的基本URI(请参阅[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]中的第514节),用于将相对的<code> s
     * ystemId </code>解析为绝对URI <br>如果在使用时,基本URI本身是相对URI,空字符串或null,则行为是实现相关的。
     * 
     */
    public void setBaseURI(String baseURI);

    /**
     *  The character encoding, if known. The encoding must be a string
     * acceptable for an XML encoding declaration ([<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>] section
     * 4.3.3 "Character Encoding in Entities").
     * <br> This attribute has no effect when the application provides a
     * character stream or string data. For other sources of input, an
     * encoding specified by means of this attribute will override any
     * encoding specified in the XML declaration or the Text declaration, or
     * an encoding obtained from a higher level protocol, such as HTTP [<a href='http://www.ietf.org/rfc/rfc2616.txt'>IETF RFC 2616</a>].
     * <p>
     * 字符编码(如果已知)编码必须是XML编码声明可接受的字符串([<a href='http://wwww3org/TR/2004/REC-xml-20040204'> XML 10 </a>]部分433"
     * 实体中的字符编码")<br>当应用程序提供字符流或字符串数​​据时,此属性无效。
     * 对于其他输入源,通过此属性指定的编码将覆盖XML声明中指定的任何编码,或文本声明或从更高级协议获得的编码,例如HTTP [<a href='http://wwwietforg/rfc/rfc2616txt'>
     *  IETF RFC 2616 </a>]。
     * 
     */
    public String getEncoding();
    /**
     *  The character encoding, if known. The encoding must be a string
     * acceptable for an XML encoding declaration ([<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>] section
     * 4.3.3 "Character Encoding in Entities").
     * <br> This attribute has no effect when the application provides a
     * character stream or string data. For other sources of input, an
     * encoding specified by means of this attribute will override any
     * encoding specified in the XML declaration or the Text declaration, or
     * an encoding obtained from a higher level protocol, such as HTTP [<a href='http://www.ietf.org/rfc/rfc2616.txt'>IETF RFC 2616</a>].
     * <p>
     * 字符编码(如果已知)编码必须是XML编码声明可接受的字符串([<a href='http://wwww3org/TR/2004/REC-xml-20040204'> XML 10 </a>]部分433"
     * 实体中的字符编码")<br>当应用程序提供字符流或字符串数​​据时,此属性无效。
     * 对于其他输入源,通过此属性指定的编码将覆盖XML声明中指定的任何编码,或文本声明或从更高级协议获得的编码,例如HTTP [<a href='http://wwwietforg/rfc/rfc2616txt'>
     *  IETF RFC 2616 </a>]。
     * 
     */
    public void setEncoding(String encoding);

    /**
     *  If set to true, assume that the input is certified (see section 2.13
     * in [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]) when
     * parsing [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>].
     * <p>
     * 如果设置为true,则假设输入已通过认证(请参阅[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'> XML 11 </a>]中的第213节),
     * 然后解析[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'> XML 11 </a>]。
     * 
     */
    public boolean getCertifiedText();
    /**
     *  If set to true, assume that the input is certified (see section 2.13
     * in [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>]) when
     * parsing [<a href='http://www.w3.org/TR/2004/REC-xml11-20040204/'>XML 1.1</a>].
     * <p>
     *  如果设置为true,则假设输入已通过认证(请参阅[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'> XML 11 </a>]中的第213节)
     * ,然后解析[<a href='http://wwww3org/TR/2004/REC-xml11-20040204/'> XML 11 </a>]。
     */
    public void setCertifiedText(boolean certifiedText);

}
