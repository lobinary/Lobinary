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
 *  This interface represents an output destination for data.
 * <p> This interface allows an application to encapsulate information about
 * an output destination in a single object, which may include a URI, a byte
 * stream (possibly with a specified encoding), a base URI, and/or a
 * character stream.
 * <p> The exact definitions of a byte stream and a character stream are
 * binding dependent.
 * <p> The application is expected to provide objects that implement this
 * interface whenever such objects are needed. The application can either
 * provide its own objects that implement this interface, or it can use the
 * generic factory method <code>DOMImplementationLS.createLSOutput()</code>
 * to create objects that implement this interface.
 * <p> The <code>LSSerializer</code> will use the <code>LSOutput</code> object
 * to determine where to serialize the output to. The
 * <code>LSSerializer</code> will look at the different outputs specified in
 * the <code>LSOutput</code> in the following order to know which one to
 * output to, the first one that is not null and not an empty string will be
 * used:
 * <ol>
 * <li> <code>LSOutput.characterStream</code>
 * </li>
 * <li>
 * <code>LSOutput.byteStream</code>
 * </li>
 * <li> <code>LSOutput.systemId</code>
 * </li>
 * </ol>
 * <p> <code>LSOutput</code> objects belong to the application. The DOM
 * implementation will never modify them (though it may make copies and
 * modify the copies, if necessary).
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 * <p>
 * 此接口表示数据<p>的输出目的地。
 * 此接口允许应用程序将关于输出目的地的信息封装在单个对象中,该对象可以包括URI,字节流(可能具有指定的编码),基本URI,以及/或字符流<p>字节流和字符流的确切定义是绑定依赖</p>应用程序应提供实现
 * 此接口的对象,无论何时需要此类对象应用程序可以提供自己的对象实现此接口,或者可以使用通用工厂方法<code> DOMImplementationLScreateLSOutput()</code>创建实现
 * 此接口的对象<p> <code> LSSerializer </code>将使用<code> LSOutput </code>对象来确定将输出序列化到哪里。
 * 此接口表示数据<p>的输出目的地。
 * <code> LSSerializer </code>将查看<code> LSOutput </code>以下列顺序知道要输出哪个,第一个不为null且不是空字符串将被使用：。
 * <ol>
 * <li> <code> LSOutputcharacterStream </code>
 * </li>
 * <li>
 *  <code> LSOutputbyteStream </code>
 * </li>
 *  <li> <code> LSOutputsystemId </code>
 * </li>
 * </ol>
 *  <p> <code> LSOutput </code>对象属于应用程序DOM实现将永远不会修改它们(如果必要,它可以复制和修改副本)<p>另请参见<a href = // wwww3org / TR / 2004 / REC-DOM-Level-3-LS-20040407'>
 * 文档对象模型(DOM)3级加载和保存规范</a>。
 * 
 */
public interface LSOutput {
    /**
     *  An attribute of a language and binding dependent type that represents
     * a writable stream to which 16-bit units can be output.
     * <p>
     *  表示可以输出16位单位的可写流的语言属性和绑定依赖类型
     * 
     */
    public java.io.Writer getCharacterStream();
    /**
     *  An attribute of a language and binding dependent type that represents
     * a writable stream to which 16-bit units can be output.
     * <p>
     *  表示可以输出16位单位的可写流的语言属性和绑定依赖类型
     * 
     */
    public void setCharacterStream(java.io.Writer characterStream);

    /**
     *  An attribute of a language and binding dependent type that represents
     * a writable stream of bytes.
     * <p>
     *  表示字节的可写流的语言的属性和绑定依赖类型
     * 
     */
    public java.io.OutputStream getByteStream();
    /**
     *  An attribute of a language and binding dependent type that represents
     * a writable stream of bytes.
     * <p>
     * 表示字节的可写流的语言的属性和绑定依赖类型
     * 
     */
    public void setByteStream(java.io.OutputStream byteStream);

    /**
     *  The system identifier, a URI reference [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>], for this
     * output destination.
     * <br> If the system ID is a relative URI reference (see section 5 in [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]), the
     * behavior is implementation dependent.
     * <p>
     *  系统标识符,用于此输出目标的URI引用[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>] <br>如果系统标识是相对URI
     * 引用(请参阅[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]中的第5节),行为是实现相关的。
     * 
     */
    public String getSystemId();
    /**
     *  The system identifier, a URI reference [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>], for this
     * output destination.
     * <br> If the system ID is a relative URI reference (see section 5 in [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>]), the
     * behavior is implementation dependent.
     * <p>
     *  系统标识符,用于此输出目标的URI引用[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>] <br>如果系统标识是相对URI
     * 引用(请参阅[<a href='http://wwwietforg/rfc/rfc2396txt'> IETF RFC 2396 </a>]中的第5节),行为是实现相关的。
     * 
     */
    public void setSystemId(String systemId);

    /**
     *  The character encoding to use for the output. The encoding must be a
     * string acceptable for an XML encoding declaration ([<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>] section
     * 4.3.3 "Character Encoding in Entities"), it is recommended that
     * character encodings registered (as charsets) with the Internet
     * Assigned Numbers Authority [<a href='ftp://ftp.isi.edu/in-notes/iana/assignments/character-sets'>IANA-CHARSETS</a>]
     *  should be referred to using their registered names.
     * <p>
     * 要用于输出的字符编码encoding必须是XML编码声明可接受的字符串([<a href='http://wwww3org/TR/2004/REC-xml-20040204'> XML 10 </a> 
     * ]第433节"实体中的字符编码"),建议将字符编码(作为字符集)注册到Internet分配号码授权中心[<a href ='ftp：// ftpisiedu / in-notes / iana / sets'>
     *  IANA-CHARSETS </a>]应使用其注册名称引用。
     * 
     */
    public String getEncoding();
    /**
     *  The character encoding to use for the output. The encoding must be a
     * string acceptable for an XML encoding declaration ([<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>] section
     * 4.3.3 "Character Encoding in Entities"), it is recommended that
     * character encodings registered (as charsets) with the Internet
     * Assigned Numbers Authority [<a href='ftp://ftp.isi.edu/in-notes/iana/assignments/character-sets'>IANA-CHARSETS</a>]
     *  should be referred to using their registered names.
     * <p>
     * 要用于输出的字符编码encoding必须是XML编码声明可接受的字符串([<a href='http://wwww3org/TR/2004/REC-xml-20040204'> XML 10 </a> 
     * ]第433节"实体中的字符编码"),建议将字符编码(作为字符集)注册到Internet分配号码授权中心[<a href ='ftp：// ftpisiedu / in-notes / iana / sets'>
     */
    public void setEncoding(String encoding);

}
