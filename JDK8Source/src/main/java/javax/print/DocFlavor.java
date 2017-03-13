/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Class <code>DocFlavor</code> encapsulates an object that specifies the
 * format in which print data is supplied to a {@link DocPrintJob}.
 * "Doc" is a short, easy-to-pronounce term that means "a piece of print data."
 * The print data format, or "doc flavor", consists of two things:
 * <UL>
 * <LI>
 * <B>MIME type.</B> This is a Multipurpose Internet Mail Extensions (MIME)
 * media type (as defined in <A HREF="http://www.ietf.org/rfc/rfc2045.txt">RFC
 * 2045</A> and <A HREF="http://www.ietf.org/rfc/rfc2046.txt">RFC 2046</A>)
 * that specifies how the print data is to be interpreted.
 * The charset of text data should be the IANA MIME-preferred name, or its
 * canonical name if no preferred name is specified. Additionally a few
 * historical names supported by earlier versions of the Java platform may
 * be recognized.
 * See <a href="../../java/lang/package-summary.html#charenc">
 * character encodings</a> for more information on the character encodings
 * supported on the Java platform.
 * <P>
 * <LI>
 * <B>Representation class name.</B> This specifies the fully-qualified name of
 * the class of the object from which the actual print data comes, as returned
 * by the {@link java.lang.Class#getName() Class.getName()} method.
 * (Thus the class name for <CODE>byte[]</CODE> is <CODE>"[B"</CODE>, for
 * <CODE>char[]</CODE> it is <CODE>"[C"</CODE>.)
 * </UL>
 * <P>
 * A <code>DocPrintJob</code> obtains its print data by means of interface
 * {@link Doc Doc}. A <code>Doc</code> object lets the <code>DocPrintJob</code>
 * determine the doc flavor the client can supply.  A <code>Doc</code> object
 * also lets the <code>DocPrintJob</code> obtain an instance of the doc flavor's
 * representation class, from which the <code>DocPrintJob</code> then obtains
 * the actual print data.
 * <P>
 * <HR>
 * <H3>Client Formatted Print Data</H3>
 * There are two broad categories of print data, client formatted print data
 * and service formatted print data.
 * <P>
 * For <B>client formatted print data</B>, the client determines or knows the
 * print data format.
 * For example the client may have a JPEG encoded image, a URL for
 * HTML code, or a disk file containing plain text in some encoding,
 * possibly obtained from an external source, and
 * requires a way to describe the data format to the print service.
 * <p>
 * The doc flavor's representation class is a conduit for the JPS
 * <code>DocPrintJob</code> to obtain a sequence of characters or
 * bytes from the client. The
 * doc flavor's MIME type is one of the standard media types telling how to
 * interpret the sequence of characters or bytes. For a list of standard media
 * types, see the Internet Assigned Numbers Authority's (IANA's) <A
 * HREF="http://www.iana.org/assignments/media-types/">Media Types
 * Directory</A>. Interface {@link Doc Doc} provides two utility operations,
 * {@link Doc#getReaderForText() getReaderForText} and
 * {@link Doc#getStreamForBytes() getStreamForBytes()}, to help a
 * <code>Doc</code> object's client extract client formatted print data.
 * <P>
 * For client formatted print data, the print data representation class is
 * typically one of the following (although other representation classes are
 * permitted):
 * <UL>
 * <LI>
 * Character array (<CODE>char[]</CODE>) -- The print data consists of the
 * Unicode characters in the array.
 * <P>
 * <LI>
 * <code>String</code>  --
 * The print data consists of the Unicode characters in the string.
 * <P>
 * <LI>
 * Character stream ({@link java.io.Reader java.io.Reader})
 * -- The print data consists of the Unicode characters read from the stream
 * up to the end-of-stream.
 * <P>
 * <LI>
 * Byte array (<CODE>byte[]</CODE>) -- The print data consists of the bytes in
 * the array. The bytes are encoded in the character set specified by the doc
 * flavor's MIME type. If the MIME type does not specify a character set, the
 * default character set is US-ASCII.
 * <P>
 * <LI>
 * Byte stream ({@link java.io.InputStream java.io.InputStream}) --
 * The print data consists of the bytes read from the stream up to the
 * end-of-stream. The bytes are encoded in the character set specified by the
 * doc flavor's MIME type. If the MIME type does not specify a character set,
 * the default character set is US-ASCII.

 * <LI>
 * Uniform Resource Locator ({@link java.net.URL URL})
 * -- The print data consists of the bytes read from the URL location.
 * The bytes are encoded in the character set specified by the doc flavor's
 * MIME type. If the MIME type does not specify a character set, the default
 * character set is US-ASCII.
 * <P>
 * When the representation class is a URL, the print service itself accesses
 * and downloads the document directly from its URL address, without involving
 * the client. The service may be some form of network print service which
 * is executing in a different environment.
 * This means you should not use a URL print data flavor to print a
 * document at a restricted URL that the client can see but the printer cannot
 * see. This also means you should not use a URL print data flavor to print a
 * document stored in a local file that is not available at a URL
 * accessible independently of the client.
 * For example, a file that is not served up by an HTTP server or FTP server.
 * To print such documents, let the client open an input stream on the URL
 * or file and use an input stream data flavor.
 * </UL>
 * <p>
 * <HR>
 * <h3>Default and Platform Encodings</h3>
 * <P>
 * For byte print data where the doc flavor's MIME type does not include a
 * <CODE>charset</CODE> parameter, the Java Print Service instance assumes the
 * US-ASCII character set by default. This is in accordance with
 * <A HREF="http://www.ietf.org/rfc/rfc2046.txt">RFC 2046</A>, which says the
 * default character set is US-ASCII. Note that US-ASCII is a subset of
 * UTF-8, so in the future this may be widened if a future RFC endorses
 * UTF-8 as the default in a compatible manner.
 * <p>
 * Also note that this is different than the behaviour of the Java runtime
 * when interpreting a stream of bytes as text data. That assumes the
 * default encoding for the user's locale. Thus, when spooling a file in local
 * encoding to a Java Print Service it is important to correctly specify
 * the encoding. Developers working in the English locales should
 * be particularly conscious of this, as their platform encoding corresponds
 * to the default mime charset. By this coincidence that particular
 * case may work without specifying the encoding of platform data.
 * <p>
 * Every instance of the Java virtual machine has a default character encoding
 * determined during virtual-machine startup and typically depends upon the
 * locale and charset being used by the underlying operating system.
 * In a distributed environment there is no guarantee that two VM share
 * the same default encoding. Thus clients which want to stream platform
 * encoded text data from the host platform to a Java Print Service instance
 * must explicitly declare the charset and not rely on defaults.
 * <p>
 * The preferred form is the official IANA primary name for an encoding.
 * Applications which stream text data should always specify the charset
 * in the mime type, which necessitates obtaining the encoding of the host
 * platform for data (eg files) stored in that platform's encoding.
 * A CharSet which corresponds to this and is suitable for use in a
 * mime-type for a DocFlavor can be obtained
 * from {@link DocFlavor#hostEncoding DocFlavor.hostEncoding}
 * This may not always be the primary IANA name but is guaranteed to be
 * understood by this VM.
 * For common flavors, the pre-defined *HOST DocFlavors may be used.
 * <p>
 * <p>
 * See <a href="../../java/lang/package-summary.html#charenc">
 * character encodings</a> for more information on the character encodings
 * supported on the Java platform.
 * <p>
 * <HR>
 * <h3>Recommended DocFlavors</h3>
 * <P>
 * The Java Print Service API does not define any mandatorily supported
 * DocFlavors.
 * However, here are some examples of MIME types that a Java Print Service
 * instance might support for client formatted print data.
 * Nested classes inside class DocFlavor declare predefined static
 * constant DocFlavor objects for these example doc flavors; class DocFlavor's
 * constructor can be used to create an arbitrary doc flavor.
 * <UL>
 * <LI>Preformatted text
 * <P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH>MIME-Type</TH><TH>Description</TH>
 * </TR>
 * <TR>
 * <TD><CODE>"text/plain"</CODE></TD>
 * <TD>Plain text in the default character set (US-ASCII)</TD>
 * </TR>
 * <TR>
 * <TD><CODE>"text/plain; charset=<I>xxx</I>"</CODE></TD>
 * <TD>Plain text in character set <I>xxx</I></TD>
 * </TR>
 * <TR>
 * <TD><CODE>"text/html"</CODE></TD>
 * <TD>HyperText Markup Language in the default character set (US-ASCII)</TD>
 * </TR>
 * <TR>
 * <TD><CODE>"text/html; charset=<I>xxx</I>"</CODE></TD>
 * <TD>HyperText Markup Language in character set <I>xxx</I></TD>
 * </TR>
 * </TABLE>
 * <P>
 * In general, preformatted text print data is provided either in a character
 * oriented representation class (character array, String, Reader) or in a
 * byte oriented representation class (byte array, InputStream, URL).
 * <P>
 *  <LI>Preformatted page description language (PDL) documents
 *<P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH>MIME-Type</TH><TH>Description</TH>
 * </TR>
 *<TR>
 * <TD><CODE>"application/pdf"</CODE></TD>
 * <TD>Portable Document Format document</TD>
 * </TR>
 * <TR>
 * <TD><CODE>"application/postscript"</CODE></TD>
 * <TD>PostScript document</TD>
 * </TR>
 * <TR>
 * <TD><CODE>"application/vnd.hp-PCL"</CODE></TD>
 * <TD>Printer Control Language document</TD>
 * </TR>
 * </TABLE>
 * <P>
 * In general, preformatted PDL print data is provided in a byte oriented
 * representation class (byte array, InputStream, URL).
 * <P>
 *  <LI>Preformatted images
 *<P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH>MIME-Type</TH><TH>Description</TH>
 * </TR>
 *
 * <TR>
 * <TD><CODE>"image/gif"</CODE></TD>
 * <TD>Graphics Interchange Format image</TD>
 * </TR>
 * <TR>
 * <TD><CODE>"image/jpeg"</CODE></TD>
 * <TD>Joint Photographic Experts Group image</TD>
 * </TR>
 * <TR>
 * <TD><CODE>"image/png"</CODE></TD>
 * <TD>Portable Network Graphics image</TD>
 * </TR>
 * </TABLE>
 * <P>
 * In general, preformatted image print data is provided in a byte oriented
 * representation class (byte array, InputStream, URL).
 * <P>
 *  <LI>Preformatted autosense print data
 *   <P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH>MIME-Type</TH><TH>Description</TH>
 * </TR>
 *
 * <TR>
 * <TD><CODE>"application/octet-stream"</CODE></TD>
 * <TD>The print data format is unspecified (just an octet stream)</TD>
 * </TABLE>
 * <P>
 * The printer decides how to interpret the print data; the way this
 * "autosensing" works is implementation dependent. In general, preformatted
 * autosense print data is provided in a byte oriented representation class
 * (byte array, InputStream, URL).
 * </UL>
 * <P>
 * <HR>
 * <H3>Service Formatted Print Data</H3>
 * <P>
 * For <B>service formatted print data</B>, the Java Print Service instance
 * determines the print data format. The doc flavor's representation class
 * denotes an interface whose methods the <code>DocPrintJob</code> invokes to
 * determine the content to be printed -- such as a renderable image
 * interface or a Java printable interface.
 * The doc flavor's MIME type is the special value
 * <CODE>"application/x-java-jvm-local-objectref"</CODE> indicating the client
 * will supply a reference to a Java object that implements the interface
 * named as the representation class.
 * This MIME type is just a placeholder; what's
 * important is the print data representation class.
 * <P>
 * For service formatted print data, the print data representation class is
 * typically one of the following (although other representation classes are
 * permitted). Nested classes inside class DocFlavor declare predefined static
 * constant DocFlavor objects for these example doc flavors; class DocFlavor's
 * constructor can be used to create an arbitrary doc flavor.
 * <UL>
 * <LI>
 * Renderable image object -- The client supplies an object that implements
 * interface
 * {@link java.awt.image.renderable.RenderableImage RenderableImage}. The
 * printer calls methods
 * in that interface to obtain the image to be printed.
 * <P>
 * <LI>
 * Printable object -- The client supplies an object that implements interface
 * {@link java.awt.print.Printable Printable}.
 * The printer calls methods in that interface to obtain the pages to be
 * printed, one by one.
 * For each page, the printer supplies a graphics context, and whatever the
 * client draws in that graphics context gets printed.
 * <P>
 * <LI>
 * Pageable object -- The client supplies an object that implements interface
 * {@link java.awt.print.Pageable Pageable}. The printer calls
 * methods in that interface to obtain the pages to be printed, one by one.
 * For each page, the printer supplies a graphics context, and whatever
 * the client draws in that graphics context gets printed.
 * </UL>
 * <P>
 * <HR>
 * <P>
 * <HR>
 * <H3>Pre-defined Doc Flavors</H3>
 * A Java Print Service instance is not <B><I>required</I></B> to support the
 * following print data formats and print data representation classes.  In
 * fact, a developer using this class should <b>never</b> assume that a
 * particular print service supports the document types corresponding to
 * these pre-defined doc flavors.  Always query the print service
 * to determine what doc flavors it supports.  However,
 * developers who have print services that support these doc flavors are
 * encouraged to refer to the predefined singleton instances created here.
 * <UL>
 * <LI>
 * Plain text print data provided through a byte stream. Specifically, the
 * following doc flavors are recommended to be supported:
 * <BR>&#183;&nbsp;&nbsp;
 * <CODE>("text/plain", "java.io.InputStream")</CODE>
 * <BR>&#183;&nbsp;&nbsp;
 * <CODE>("text/plain; charset=us-ascii", "java.io.InputStream")</CODE>
 * <BR>&#183;&nbsp;&nbsp;
 * <CODE>("text/plain; charset=utf-8", "java.io.InputStream")</CODE>
 * <P>
 * <LI>
 * Renderable image objects. Specifically, the following doc flavor is
 * recommended to be supported:
 * <BR>&#183;&nbsp;&nbsp;
 * <CODE>("application/x-java-jvm-local-objectref", "java.awt.image.renderable.RenderableImage")</CODE>
 * </UL>
 * <P>
 * A Java Print Service instance is allowed to support any other doc flavors
 * (or none) in addition to the above mandatory ones, at the implementation's
 * choice.
 * <P>
 * Support for the above doc flavors is desirable so a printing client can rely
 * on being able to print on any JPS printer, regardless of which doc flavors
 * the printer supports. If the printer doesn't support the client's preferred
 * doc flavor, the client can at least print plain text, or the client can
 * convert its data to a renderable image and print the image.
 * <P>
 * Furthermore, every Java Print Service instance must fulfill these
 * requirements for processing plain text print data:
 * <UL>
 * <LI>
 * The character pair carriage return-line feed (CR-LF) means
 * "go to column 1 of the next line."
 * <LI>
 * A carriage return (CR) character standing by itself means
 * "go to column 1 of the next line."
 * <LI>
 * A line feed (LF) character standing by itself means
 * "go to column 1 of the next line."
 * <LI>
 * </UL>
 * <P>
 * The client must itself perform all plain text print data formatting not
 * addressed by the above requirements.
 * <P>
 * <H3>Design Rationale</H3>
 * <P>
 * Class DocFlavor in package javax.print.data is similar to class
 * {@link java.awt.datatransfer.DataFlavor DataFlavor}. Class
 * <code>DataFlavor</code>
 * is not used in the Java Print Service (JPS) API
 * for three reasons which are all rooted in allowing the JPS API to be
 * shared by other print services APIs which may need to run on Java profiles
 * which do not include all of the Java Platform, Standard Edition.
 * <OL TYPE=1>
 * <LI>
 * The JPS API is designed to be used in Java profiles which do not support
 * AWT.
 * <P>
 * <LI>
 * The implementation of class <code>java.awt.datatransfer.DataFlavor</code>
 * does not guarantee that equivalent data flavors will have the same
 * serialized representation. DocFlavor does, and can be used in services
 * which need this.
 * <P>
 * <LI>
 * The implementation of class <code>java.awt.datatransfer.DataFlavor</code>
 * includes a human presentable name as part of the serialized representation.
 * This is not appropriate as part of a service matching constraint.
 * </OL>
 * <P>
 * Class DocFlavor's serialized representation uses the following
 * canonical form of a MIME type string. Thus, two doc flavors with MIME types
 * that are not identical but that are equivalent (that have the same
 * canonical form) may be considered equal.
 * <UL>
 * <LI> The media type, media subtype, and parameters are retained, but all
 *      comments and whitespace characters are discarded.
 * <LI> The media type, media subtype, and parameter names are converted to
 *      lowercase.
 * <LI> The parameter values retain their original case, except a charset
 *      parameter value for a text media type is converted to lowercase.
 * <LI> Quote characters surrounding parameter values are removed.
 * <LI> Quoting backslash characters inside parameter values are removed.
 * <LI> The parameters are arranged in ascending order of parameter name.
 * </UL>
 * <P>
 * Class DocFlavor's serialized representation also contains the
 * fully-qualified class <I>name</I> of the representation class
 * (a String object), rather than the representation class itself
 * (a Class object). This allows a client to examine the doc flavors a
 * Java Print Service instance supports without having
 * to load the representation classes, which may be problematic for
 * limited-resource clients.
 * <P>
 *
 * <p>
 *  类<code> DocFlavor </code>封装了一个对象,该对象指定将打印数据提供给{@link DocPrintJob}的格式。
 * "Doc"是一个简短易读的术语,意思是" "打印数据格式,或"doc风格",包括两件事：。
 * <UL>
 * <LI>
 * <B> MIME类型</B>这是多用途互联网邮件扩展(MIME)媒体类型(如<A HREF=\"http://wwwietforg/rfc/rfc2045txt\"> RFC 2045 </A>和<A HREF ="http：// wwwietforg / rfc / rfc2046txt">
 *  RFC 2046 </A>)指定如何解释打印数据文本数据的字符集应为IANA MIME首选名称,或其规范名称if未指定首选名称此外,可以识别早期版本的Java平台支持的几个历史名称有关详细信息,请参阅
 * <a href=\"//java/lang/package-summaryhtml#charenc\">字符编码</a> Java平台上支持的字符编码。
 * <P>
 * <LI>
 * <B>表示类名</B>这指定了实际打印数据来自的对象的类的完全限定名,由{@link javalangClass#getName()ClassgetName()}方法返回对于<CODE> char []
 *  </CODE>,<CODE> byte [] </CODE>的类名为<CODE>"[B"</CODE> >)。
 * </UL>
 * <P>
 *  <code> DocPrintJob </code>通过接口{@link Doc Doc}获取其打印数据。
 * <code> Doc </code>对象允许<code> DocPrintJob </code>提供一个<code> Doc </code>对象还允许<code> DocPrintJob </code>
 * 获取doc flavor的表示类的实例,然后从<code> DocPrintJob </code>获取实际的打印数据。
 *  <code> DocPrintJob </code>通过接口{@link Doc Doc}获取其打印数据。
 * <P>
 * <HR>
 * <H3>客户机格式化打印数据</H3>打印数据有两种类型,客户机格式的打印数据和服务格式化的打印数据
 * <P>
 *  对于客户机格式化的打印数据</B>,客户机确定或知道打印数据格式。
 * 例如,客户机可以具有JPEG编码图像,HTML代码的URL或在某些编码中包含纯文本的磁盘文件,可能从外部源获得,并且需要一种用于向打印服务描述数据格式的方法。
 * <p>
 * doc flavor的表示类是JPS <code> DocPrintJob </code>从客户端获取字符或字节序列的渠道。
 * doc flavor的MIME类型是标准媒体类型之一,告诉如何解释字符序列或字节有关标准媒体类型的列表,请参阅互联网号码分配机构(IANA)<A HREF=\"http://wwwianaorg/assignments/media-types/\">
 * 媒体类型目录</A>界面{@link Doc Doc}提供两个实用程序操作{@link Doc#getReaderForText()getReaderForText}和{@link Doc#getStreamForBytes()getStreamForBytes()}
 * ,以帮助<code> Doc </code>对象的客户端提取客户端格式的打印数据。
 * doc flavor的表示类是JPS <code> DocPrintJob </code>从客户端获取字符或字节序列的渠道。
 * <P>
 * 对于客户机格式的打印数据,打印数据表示类通常是以下之一(尽管允许其他表示类)：
 * <UL>
 * <LI>
 *  字符数组(<CODE> char [] </CODE>) - 打印数据由数组中的Unicode字符组成
 * <P>
 * <LI>
 *  <code> String </code>  - 打印数据由字符串中的Unicode字符组成
 * <P>
 * <LI>
 *  字符流({@link javaioReader javaioReader}) - 打印数据包括从流读取到流结束的Unicode字符
 * <P>
 * <LI>
 *  字节数组(<CODE> byte [] </CODE>) - 打印数据由数组中的字节组成字节以由doc flavor MIME类型指定的字符集编码如果MIME类型没有指定字符设置,默认字符集为US-A
 * SCII。
 * <P>
 * <LI>
 * 字节流({@link javaioInputStream javaioInputStream}) - 打印数据包括从流读取的字节直到流的结束字节编码在由doc风味的MIME类型指定的字符集如果MIME类
 * 型不指定字符集,默认字符集为US-ASCII。
 * 
 * <LI>
 *  统一资源定位器({@link javanetURL URL}) - 打印数据由从URL位置读取的字节组成字节按照doc flavor MIME类型指定的字符集编码如果MIME类型没有指定字符集,默认字
 * 符集为US-ASCII。
 * <P>
 * 当表示类是一个URL时,打印服务本身直接从其URL地址访问和下载文档,而不涉及客户端。服务可能是在不同环境中执行的某种形式的网络打印服务。
 * 这意味着您不应使用URL打印数据风格以在客户端可以看到但是打印机看不到的受限URL上打印文档这也意味着您不应该使用URL打印数据风格来打印存储在本地文件中的文档,可独立于客户端访问的URL例如,不由HT
 * TP服务器或FTP服务器提供的文件要打印此类文档,请让客户端在URL或文件上打开输入流,并使用输入流数据风格。
 * 当表示类是一个URL时,打印服务本身直接从其URL地址访问和下载文档,而不涉及客户端。服务可能是在不同环境中执行的某种形式的网络打印服务。
 * </UL>
 * <p>
 * <HR>
 * <h3>默认和平台编码</h3>
 * <P>
 *  对于doc数据类型的MIME类型不包含<CODE> charset </CODE>参数的字节打印数据,Java Print Service实例默认使用US-ASCII字符集。
 * 这符合<A HREF ="http： // wwwietforg / rfc / rfc2046txt"> RFC 2046 </A>,其中默认字符集为US-ASCII请注意,US-ASCII是UTF-
 * 8的子集,因此,如果将来RFC以兼容方式认可UTF-8作为默认值。
 *  对于doc数据类型的MIME类型不包含<CODE> charset </CODE>参数的字节打印数据,Java Print Service实例默认使用US-ASCII字符集。
 * <p>
 * 还要注意,这不同于将字节流解释为文本数据时Java运行时的行为假设用户的语言环境的默认编码因此,在将本地编码中的文件假脱机到Java打印服务时,正确的指定编码在英语区域中工作的开发人员应该特别注意这一点
 * ,因为他们的平台编码对应于默认的mime字符集通过这种巧合,特定情况下可以工作而不指定平台数据的编码。
 * <p>
 * Java虚拟机的每个实例都具有在虚拟机启动期间确定的默认字符编码,并且通常取决于底层操作系统正在使用的语言环境和字符集。在分布式环境中,不能保证两个VM共享相同的默认编码。
 * 要将平台编码文本数据从主机平台流式传输到Java打印服务实例的客户端必须显式声明该字符集,而不依赖于默认值。
 * <p>
 * 首选形式是用于编码的官方IANA主名称应用程序,流文本数据应始终指定mime类型中的字符集,这需要获取存储在该平台编码中的数据(例如文件)的主机平台的编码A CharSet对应于此并适用于MIME类型的
 * DocFlavor可以从{@link DocFlavor#hostEncoding DocFlavorhostEncoding}获得这可能不总是主要的IANA名称,但是保证被此VM理解对于常见的口味,可
 * 以使用预定义的* HOST DocFlavors。
 * <p>
 * <p>
 *  有关Java平台支持的字符编码的详细信息,请参见<a href=\"//java/lang/package-summaryhtml#charenc\">字符编码</a>
 * <p>
 * <HR>
 *  <h3>推荐DocFlavors </h3>
 * <P>
 * Java打印服务API没有定义任何强制支持的DocFlavors然而,这里有一些Java打印服务实例可能支持客户端格式化打印数据的MIME类型的示例类DocFlavor中的嵌套类声明了这些示例doc f
 * lavor的预定义静态常量DocFlavor对象; DocFlavor类的构造函数可以用来创建一个任意的文档风格。
 * <UL>
 *  <LI>预格式化文本
 * <P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH> MIME类型</TH> <TH>说明</TH>
 * </TR>
 * <TR>
 *  <TD> <CODE>"text / plain"</CODE> </TD> <TD>默认字符集中的纯文本
 * </TR>
 * <TR>
 *  <TD> <CODE>"text / plain; charset = <I> xxx </I>"</CODE> </TD> <TD>字符集中的纯文本<
 * </TR>
 * <TR>
 *  默认字符集(US-ASCII)中的<TD> <CODE>"text / html"</CODE> </TD> <TD>超文本标记语言</TD>
 * </TR>
 * <TR>
 * <TD> <CODE>"text / html; charset = <I> xxx </I>"</CODE> </TD> <TD>超文本标记语言字符集<I> xxx </I> </TD >
 * </TR>
 * </TABLE>
 * <P>
 *  通常,以面向字符的表示类(字符数组,字符串,读取器)或面向字节的表示类(字节数组,输入流,URL)提供预格式文本打印数据,
 * <P>
 *  <LI>预格式化页面描述语言(PDL)文档
 * P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH> MIME类型</TH> <TH>说明</TH>
 * </TR>
 * TR>
 *  <TD> <CODE>"application / pdf"</CODE> </TD> <TD>可移植文档格式文档</TD>
 * </TR>
 * <TR>
 *  <TD> <CODE>"application / postscript"</CODE> </TD> <TD> PostScript文档</TD>
 * </TR>
 * <TR>
 *  <TD> <CODE>"application / vndhp-PCL"</CODE> </TD> <TD>打印机控制语言文档</TD>
 * </TR>
 * </TABLE>
 * <P>
 * 通常,以字节定向的表示类(字节数组,InputStream,URL)提供预格式化的PDL打印数据,
 * <P>
 *  <LI>预格式化的图片
 * P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH> MIME类型</TH> <TH>说明</TH>
 * </TR>
 * 
 * <TR>
 *  <TD> <CODE>"image / gif"</CODE> </TD> <TD>图形交换格式图像</TD>
 * </TR>
 * <TR>
 *  <TD> <CODE>"image / jpeg"</CODE> </TD> <TD> Joint Photographic Experts Group image </TD>
 * </TR>
 * <TR>
 *  <TD> <CODE>"image / png"</CODE> </TD> <TD>便携式网络图形图像</TD>
 * </TR>
 * </TABLE>
 * <P>
 *  通常,以字节定向的表示类(字节数组,InputStream,URL)提供预格式化的图像打印数据,
 * <P>
 *  <LI>预格式化自动感应打印数据
 * <P>
 * <TABLE BORDER=1 CELLPADDING=0 CELLSPACING=0 SUMMARY="MIME-Types and their descriptions">
 * <TR>
 *  <TH> MIME类型</TH> <TH>说明</TH>
 * </TR>
 * 
 * <TR>
 *  <TD> <CODE>"application / octet-stream"</CODE> </TD> <TD>打印数据格式未指定(只是一个八位字节流)</TD>
 * </TABLE>
 * <P>
 * 打印机决定如何解释打印数据;该"自动感测"工作的方式是依赖于实现的。通常,以面向字节的表示类(字节数组,InputStream,URL)提供预格式化的自动感测打印数据,
 * </UL>
 * <P>
 * <HR>
 *  <H3>服务格式化打印数据</H3>
 * <P>
 * 对于<B>服务格式的打印数据</B>,Java打印服务实例确定打印数据格式doc flavor的表示类表示一个接口,其方法由<code> DocPrintJob </code>调用以确定要打印的内容 -
 *  如可渲染图像接口或Java可打印接口doc flavor的MIME类型是特殊值<CODE>"application / x-java-jvm-local-objectref"</CODE>,表示客户端
 * 将提供一个引用实现名为表示类接口的Java对象此MIME类型只是一个占位符;重要的是打印数据表示类。
 * <P>
 * 对于服务格式的打印数据,打印数据表示类通常是以下之一(尽管允许其他表示类)DocFlavor类中的嵌套类为这些示例doc类型声明了预定义的静态常量DocFlavor对象; DocFlavor类的构造函数
 * 可以用来创建一个任意的文档风格。
 * <UL>
 * <LI>
 *  可渲染图像对象 - 客户端提供实现接口的对象{@link javaawtimagerenderableRenderableImage RenderableImage}打印机调用该接口中的方法以获取要打
 * 印的图像。
 * <P>
 * <LI>
 * 可打印对象 - 客户端提供实现接口的对象{@link javaawtprintPrintable Printable}打印机调用该接口中的方法逐个获取要打印的页面对于每个页面,打印机都提供一个图形上下文
 * ,客户端绘制图形上下文被打印。
 * <P>
 * <LI>
 *  Pageable对象 - 客户端提供实现接口的对象{@link javaawtprintPageable Pageable}打印机调用该接口中的方法逐个获取要打印的页面对于每一页,打印机都提供一个图形
 * 上下文,客户端绘制图形上下文被打印。
 * </UL>
 * <P>
 * <HR>
 * <P>
 * <HR>
 * <H3>预定义的文档风味</H3> Java打印服务实例不是<B> <I>必需</I> </B>以支持以下打印数据格式和打印数据表示类实际上,开发人员使用此类应该<b>从不</b>假定特定的打印服务支持
 * 与这些预定义的doc风格对应的文档类型始终查询打印服务以确定它支持哪些doc风格但是,具有打印服务支持这些doc风格鼓励参考在这里创建的预定义单例实例。
 * <UL>
 * <LI>
 * 通过字节流提供的纯文本打印数据具体来说,建议支持以下doc类型：<BR>·&nbsp;&nbsp; <CODE>("text / plain","javaioInputStream")</CODE> <BR>
 * ·&nbsp;&nbsp; <CODE>("text / plain; charset = us-ascii","javaioInputStream")</CODE> <BR>·&nbsp;&nbsp;
 *  <CODE>("text / plain; charset = utf-8","javaioInputStream")</CODE>。
 * <P>
 * <LI>
 *  可渲染的图像对象具体来说,建议支持以下doc风格：<BR>·&nbsp;&nbsp; <CODE>("application / x-java-jvm-local-objectref","javaaw
 * timagerenderableRenderableImage")</CODE>。
 * </UL>
 * <P>
 *  允许Java打印服务实例除了上述强制性的以外,在实现的选择上支持任何其他文档风格(或没有)
 * <P>
 * 支持上述doc风格是所希望的,因此打印客户端可以依赖于能够在任何JPS打印机上打印,而不管打印机支持哪种文档。
 * 如果打印机不支持客户端的首选文档风格,则客户端可以至少打印纯文本,或者客户端可以将其数据转换为可渲染图像并打印图像。
 * <P>
 *  此外,每个Java Print Service实例必须满足处理纯文本打印数据的这些要求：
 * <UL>
 * <LI>
 *  字符对回车换行(CR-LF)表示"转到下一行的第1列"
 * <LI>
 *  回车(CR)字符本身意味着"转到下一行的第1列"
 * <LI>
 *  换行(LF)字符本身意味着"转到下一行的第1列"
 * <LI>
 * </UL>
 * <P>
 * 客户端必须自己执行上述要求未解决的所有纯文本打印数据格式
 * <P>
 *  <H3>设计理由</H3>
 * <P>
 *  javaxprintdata包中的DocFlavor类与类{@link javaawtdatatransferDataFlavor DataFlavor}类似。
 * 类别<code> DataFlavor </code>在Java打印服务(JPS)API中未使用,原因有三个,其中的原因是允许JPS API由其他打印服务API共享,这些API可能需要在不包含所有Jav
 * a平台标准版的Java配置文件上运行。
 *  javaxprintdata包中的DocFlavor类与类{@link javaawtdatatransferDataFlavor DataFlavor}类似。
 * <OL TYPE=1>
 * <LI>
 *  JPS API设计用于不支持AWT的Java配置文件
 * <P>
 * <LI>
 * 类<code> javaawtdatatransferDataFlavor </code>类的实现不保证等价数据类型将具有与DocFlavor相同的序列化表示,并且可以在需要该类型的服务中使用
 * <P>
 * <LI>
 *  类<code> javaawtdatatransferDataFlavor </code>的实现包括一个人类可呈现的名称作为序列化表示的一部分。这不适合作为服务匹配约束的一部分
 * </OL>
 * <P>
 *  类DocFlavor的序列化表示使用MIME类型字符串的以下规范形式因此,具有不相同但等价(具有相同规范形式)的MIME类型的两个doc风格可以被视为相等
 * <UL>
 * 
 * </UL>
 * <P>
 * 类DocFlavor的序列化表示还包含表示类(一个String对象)的完全限定类<I> name </I>,而不是表示类本身(一个Class对象)。
 * 这允许客户端检查doc风格a Java Print Service实例支持而不必加载表示类,这对有限资源客户端可能有问题。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public class DocFlavor implements Serializable, Cloneable {

    private static final long serialVersionUID = -4512080796965449721L;

    /**
     * A String representing the host operating system encoding.
     * This will follow the conventions documented in
     * <a href="http://www.ietf.org/rfc/rfc2278.txt">
     * <i>RFC&nbsp;2278:&nbsp;IANA Charset Registration Procedures</i></a>
     * except where historical names are returned for compatibility with
     * previous versions of the Java platform.
     * The value returned from method is valid only for the VM which
     * returns it, for use in a DocFlavor.
     * This is the charset for all the "HOST" pre-defined DocFlavors in
     * the executing VM.
     * <p>
     *  代表主机操作系统编码的字符串这将遵循中所述的约定
     * <a href="http://www.ietf.org/rfc/rfc2278.txt">
     * <i> RFC 2278：IANA字符集注册程序</i> </a>,除非返回历史名称以与以前版本的Java平台兼容。
     * 方法返回的值仅对返回的VM有效,用于DocFlavor这是用于执行VM中的所有"HOST"预定义DocFlavors的字符集。
     * 
     */
    public static final String hostEncoding;

    static {
        hostEncoding =
            (String)java.security.AccessController.doPrivileged(
                  new sun.security.action.GetPropertyAction("file.encoding"));
    }

    /**
     * MIME type.
     * <p>
     *  MIME类型
     * 
     */
    private transient MimeType myMimeType;

    /**
     * Representation class name.
     * <p>
     *  表示类名称
     * 
     * 
     * @serial
     */
    private String myClassName;

    /**
     * String value for this doc flavor. Computed when needed and cached.
     * <p>
     *  此文档flavor的字符串值在需要和缓存时计算
     * 
     */
    private transient String myStringValue = null;


    /**
     * Constructs a new doc flavor object from the given MIME type and
     * representation class name. The given MIME type is converted into
     * canonical form and stored internally.
     *
     * <p>
     *  从给定的MIME类型和表示类名构造一个新的doc flavor对象给定的MIME类型被转换为规范形式并在内部存储
     * 
     * 
     * @param  mimeType   MIME media type string.
     * @param  className  Fully-qualified representation class name.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>mimeType</CODE> is null or
     *     <CODE>className</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>mimeType</CODE> does not
     *     obey the syntax for a MIME media type string.
     */
    public DocFlavor(String mimeType, String className) {
        if (className == null) {
            throw new NullPointerException();
        }
        myMimeType = new MimeType (mimeType);
        myClassName = className;
    }

    /**
     * Returns this doc flavor object's MIME type string based on the
     * canonical form. Each parameter value is enclosed in quotes.
     * <p>
     *  基于规范形式返回此doc对象的MIME类型字符串每个参数值都用引号括起来
     * 
     * 
     * @return the mime type
     */
    public String getMimeType() {
        return myMimeType.getMimeType();
    }

    /**
     * Returns this doc flavor object's media type (from the MIME type).
     * <p>
     * 返回此doc文档flavor对象的媒体类型(从MIME类型)
     * 
     * 
     * @return the media type
     */
    public String getMediaType() {
        return myMimeType.getMediaType();
    }

    /**
     * Returns this doc flavor object's media subtype (from the MIME type).
     * <p>
     *  返回此doc文档flavor对象的媒体子类型(从MIME类型)
     * 
     * 
     * @return the media sub-type
     */
    public String getMediaSubtype() {
        return myMimeType.getMediaSubtype();
    }

    /**
     * Returns a <code>String</code> representing a MIME
     * parameter.
     * Mime types may include parameters which are usually optional.
     * The charset for text types is a commonly useful example.
     * This convenience method will return the value of the specified
     * parameter if one was specified in the mime type for this flavor.
     * <p>
     * <p>
     *  返回表示MIME参数的<code> String </code> Mime类型可以包括通常是可选的参数。
     * 文本类型的charset是一个常用的示例此方便方法将返回指定参数的值,如果在mime类型为这种味道。
     * <p>
     * 
     * @param paramName the name of the paramater. This name is internally
     * converted to the canonical lower case format before performing
     * the match.
     * @return String representing a mime parameter, or
     * null if that parameter is not in the mime type string.
     * @exception NullPointerException if paramName is null.
     */
    public String getParameter(String paramName) {
        return
            (String)myMimeType.getParameterMap().get(paramName.toLowerCase());
    }

    /**
     * Returns the name of this doc flavor object's representation class.
     * <p>
     *  返回此doc对象的表示类的名称
     * 
     * 
     * @return the name of the representation class.
     */
    public String getRepresentationClassName() {
        return myClassName;
    }

    /**
     * Converts this <code>DocFlavor</code> to a string.
     *
     * <p>
     *  将此<code> DocFlavor </code>转换为字符串
     * 
     * 
     * @return  MIME type string based on the canonical form. Each parameter
     *          value is enclosed in quotes.
     *          A "class=" parameter is appended to the
     *          MIME type string to indicate the representation class name.
     */
    public String toString() {
        return getStringValue();
    }

    /**
     * Returns a hash code for this doc flavor object.
     * <p>
     *  返回此doc文档flavor对象的哈希代码
     * 
     */
    public int hashCode() {
        return getStringValue().hashCode();
    }

    /**
     * Determines if this doc flavor object is equal to the given object.
     * The two are equal if the given object is not null, is an instance
     * of <code>DocFlavor</code>, has a MIME type equivalent to this doc
     * flavor object's MIME type (that is, the MIME types have the same media
     * type, media subtype, and parameters), and has the same representation
     * class name as this doc flavor object. Thus, if two doc flavor objects'
     * MIME types are the same except for comments, they are considered equal.
     * However, two doc flavor objects with MIME types of "text/plain" and
     * "text/plain; charset=US-ASCII" are not considered equal, even though
     * they represent the same media type (because the default character
     * set for plain text is US-ASCII).
     *
     * <p>
     * 确定这个doc flavor对象是否等于给定的对象如果给定的对象不为null,它是等于的,是<code> DocFlavor </code>的一个实例,具有等同于这个doc flavor对象的MIME类
     * 型的MIME类型即MIME类型具有相同的媒体类型,媒体子类型和参数),并且具有与此doc flavor对象相同的表示类名。
     * 因此,如果两个doc flavor对象的MIME类型除注释外相同,则它们是认为平等但是,MIME类型为"text / plain"和"text / plain; charset = US-ASCII"的
     * 两个doc flavor对象不被视为相等,即使它们表示相同的媒体类型(因为默认字符集为plain文本是US-ASCII)。
     * 
     * 
     * @param  obj  Object to test.
     *
     * @return  True if this doc flavor object equals <CODE>obj</CODE>, false
     *          otherwise.
     */
    public boolean equals(Object obj) {
        return
            obj != null &&
            obj instanceof DocFlavor &&
            getStringValue().equals (((DocFlavor) obj).getStringValue());
    }

    /**
     * Returns this doc flavor object's string value.
     * <p>
     *  返回此doc对象的字符串值
     * 
     */
    private String getStringValue() {
        if (myStringValue == null) {
            myStringValue = myMimeType + "; class=\"" + myClassName + "\"";
        }
        return myStringValue;
    }

    /**
     * Write the instance to a stream (ie serialize the object).
     * <p>
     * 将实例写入流(即序列化对象)
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {

        s.defaultWriteObject();
        s.writeObject(myMimeType.getMimeType());
    }

    /**
     * Reconstitute an instance from a stream (that is, deserialize it).
     *
     * <p>
     *  从流重构实例(即,反序列化它)
     * 
     * 
     * @serialData
     * The serialised form of a DocFlavor is the String naming the
     * representation class followed by the String representing the canonical
     * form of the mime type.
     */
    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException {

        s.defaultReadObject();
        myMimeType = new MimeType((String)s.readObject());
    }

    /**
     * Class DocFlavor.BYTE_ARRAY provides predefined static constant
     * DocFlavor objects for example doc flavors using a byte array
     * (<CODE>byte[]</CODE>) as the print data representation class.
     * <P>
     *
     * <p>
     *  DocFlavorBYTE_ARRAY类提供了预定义的静态常量DocFlavor对象,例如使用字节数组(<CODE> byte [] </CODE>)作为打印数据表示类
     * <P>
     * 
     * 
     * @author  Alan Kaminsky
     */
    public static class BYTE_ARRAY extends DocFlavor {

        private static final long serialVersionUID = -9065578006593857475L;

        /**
         * Constructs a new doc flavor with the given MIME type and a print
         * data representation class name of <CODE>"[B"</CODE> (byte array).
         *
         * <p>
         *  使用给定的MIME类型和打印数据表示类名称<CODE>"[B"</CODE>(字节数组)构造新的文档风格
         * 
         * 
         * @param  mimeType   MIME media type string.
         *
         * @exception  NullPointerException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> is null.
         * @exception  IllegalArgumentException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> does not
         *     obey the syntax for a MIME media type string.
         */
        public BYTE_ARRAY (String mimeType) {
            super (mimeType, "[B");
        }

        /**
         * Doc flavor with MIME type = <CODE>"text/plain"</CODE>,
         * encoded in the host platform encoding.
         * See {@link DocFlavor#hostEncoding hostEncoding}
         * Print data representation class name =
         * <CODE>"[B"</CODE> (byte array).
         * <p>
         *  文档风格MIME类型= <CODE>"text / plain"</CODE>,编码在主机平台编码中参见{@link DocFlavor#hostEncoding hostEncoding}打印数据表
         * 示类名= <CODE>"[B" >(字节数组)。
         * 
         */
        public static final BYTE_ARRAY TEXT_PLAIN_HOST =
            new BYTE_ARRAY ("text/plain; charset="+hostEncoding);

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-8"</CODE>,
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-8"</CODE>,打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY TEXT_PLAIN_UTF_8 =
            new BYTE_ARRAY ("text/plain; charset=utf-8");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16"</CODE>,
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         * Doc类型= <CODE>"text / plain; charset = utf-16"</CODE>,打印数据表示类名= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY TEXT_PLAIN_UTF_16 =
            new BYTE_ARRAY ("text/plain; charset=utf-16");


        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16be"</CODE>
         * (big-endian byte ordering),
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-16be"</CODE>(大端字节排序),打印数据表示类名称= <CODE>"[B"</CODE>字节数组)
         * 
         */
        public static final BYTE_ARRAY TEXT_PLAIN_UTF_16BE =
            new BYTE_ARRAY ("text/plain; charset=utf-16be");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16le"</CODE>
         * (little-endian byte ordering),
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-16le"</CODE>(小端字节排序),打印数据表示类名称= <CODE>"[B"</CODE>字节数组)
         * 
         */
        public static final BYTE_ARRAY TEXT_PLAIN_UTF_16LE =
            new BYTE_ARRAY ("text/plain; charset=utf-16le");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=us-ascii"</CODE>,
         * print data representation class name =
         * <CODE>"[B"</CODE> (byte array).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = us-ascii"</CODE>打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY TEXT_PLAIN_US_ASCII =
            new BYTE_ARRAY ("text/plain; charset=us-ascii");


        /**
         * Doc flavor with MIME type = <CODE>"text/html"</CODE>,
         * encoded in the host platform encoding.
         * See {@link DocFlavor#hostEncoding hostEncoding}
         * Print data representation class name =
         * <CODE>"[B"</CODE> (byte array).
         * <p>
         * 文档风格MIME类型= <CODE>"text / html"</CODE>,编码在主机平台编码中参见{@link DocFlavor#hostEncoding hostEncoding}打印数据表示类
         * 名= <CODE>"[B" >(字节数组)。
         * 
         */
        public static final BYTE_ARRAY TEXT_HTML_HOST =
            new BYTE_ARRAY ("text/html; charset="+hostEncoding);

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-8"</CODE>,
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = utf-8"</CODE>,打印数据表示类名= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY TEXT_HTML_UTF_8 =
            new BYTE_ARRAY ("text/html; charset=utf-8");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16"</CODE>,
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = utf-16"</CODE>打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY TEXT_HTML_UTF_16 =
            new BYTE_ARRAY ("text/html; charset=utf-16");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16be"</CODE>
         * (big-endian byte ordering),
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = utf-16be"</CODE>(big-endian字节排序),打印数据表示类名称= <CODE>"[B"</CODE>字节
         * 数组)。
         * 
         */
        public static final BYTE_ARRAY TEXT_HTML_UTF_16BE =
            new BYTE_ARRAY ("text/html; charset=utf-16be");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16le"</CODE>
         * (little-endian byte ordering),
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         * Doc类型= <CODE>"text / html; charset = utf-16le"</CODE>(小端字节排序),打印数据表示类名称= <CODE>"[B"</CODE>字节数组)
         * 
         */
        public static final BYTE_ARRAY TEXT_HTML_UTF_16LE =
            new BYTE_ARRAY ("text/html; charset=utf-16le");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=us-ascii"</CODE>,
         * print data representation class name =
         * <CODE>"[B"</CODE> (byte array).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = us-ascii"打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY TEXT_HTML_US_ASCII =
            new BYTE_ARRAY ("text/html; charset=us-ascii");


        /**
         * Doc flavor with MIME type = <CODE>"application/pdf"</CODE>, print
         * data representation class name = <CODE>"[B"</CODE> (byte array).
         * <p>
         *  Doc类型= <CODE>"application / pdf"</CODE>打印数据表示类名= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY PDF = new BYTE_ARRAY ("application/pdf");

        /**
         * Doc flavor with MIME type = <CODE>"application/postscript"</CODE>,
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  Doc类型= <CODE>"application / postscript"</CODE>,打印数据表示类名= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY POSTSCRIPT =
            new BYTE_ARRAY ("application/postscript");

        /**
         * Doc flavor with MIME type = <CODE>"application/vnd.hp-PCL"</CODE>,
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array).
         * <p>
         *  MIME类型= <CODE>"application / vndhp-PCL"</CODE>打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY PCL =
            new BYTE_ARRAY ("application/vnd.hp-PCL");

        /**
         * Doc flavor with MIME type = <CODE>"image/gif"</CODE>, print data
         * representation class name = <CODE>"[B"</CODE> (byte array).
         * <p>
         * Doc类型= <CODE>"image / gif"</CODE>打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY GIF = new BYTE_ARRAY ("image/gif");

        /**
         * Doc flavor with MIME type = <CODE>"image/jpeg"</CODE>, print data
         * representation class name = <CODE>"[B"</CODE> (byte array).
         * <p>
         *  Doc类型= <CODE>"image / jpeg"</CODE>打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY JPEG = new BYTE_ARRAY ("image/jpeg");

        /**
         * Doc flavor with MIME type = <CODE>"image/png"</CODE>, print data
         * representation class name = <CODE>"[B"</CODE> (byte array).
         * <p>
         *  MIME类型= <CODE>"image / png"</CODE>打印数据表示类名称= <CODE>"[B"</CODE>
         * 
         */
        public static final BYTE_ARRAY PNG = new BYTE_ARRAY ("image/png");

        /**
         * Doc flavor with MIME type =
         * <CODE>"application/octet-stream"</CODE>,
         * print data representation class name = <CODE>"[B"</CODE> (byte
         * array). The client must determine that data described
         * using this DocFlavor is valid for the printer.
         * <p>
         *  Doc类型= <CODE>"application / octet-stream"</CODE>打印数据表示类名称= <CODE>"[B"</CODE>(字节数组)客户端必须确定使用此DocFlavo
         * r对打印机有效。
         * 
         */
        public static final BYTE_ARRAY AUTOSENSE =
            new BYTE_ARRAY ("application/octet-stream");

    }

    /**
     * Class DocFlavor.INPUT_STREAM provides predefined static constant
     * DocFlavor objects for example doc flavors using a byte stream ({@link
     * java.io.InputStream java.io.InputStream}) as the print
     * data representation class.
     * <P>
     *
     * <p>
     * DocFlavorINPUT_STREAM类提供了预定义的静态常量DocFlavor对象,例如使用字节流({@link javaioInputStream javaioInputStream})作为打印
     * 数据表示类。
     * <P>
     * 
     * 
     * @author  Alan Kaminsky
     */
    public static class INPUT_STREAM extends DocFlavor {

        private static final long serialVersionUID = -7045842700749194127L;

        /**
         * Constructs a new doc flavor with the given MIME type and a print
         * data representation class name of
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         *
         * <p>
         *  使用给定的MIME类型和打印数据表示类名称<CODE>"javaioInputStream"</CODE>(字节流)构造新的文档风格
         * 
         * 
         * @param  mimeType   MIME media type string.
         *
         * @exception  NullPointerException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> is null.
         * @exception  IllegalArgumentException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> does not
         *     obey the syntax for a MIME media type string.
         */
        public INPUT_STREAM (String mimeType) {
            super (mimeType, "java.io.InputStream");
        }

        /**
         * Doc flavor with MIME type = <CODE>"text/plain"</CODE>,
         * encoded in the host platform encoding.
         * See {@link DocFlavor#hostEncoding hostEncoding}
         * Print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  主机平台编码中编码的MIME类型= <CODE>"text / plain"</CODE>的文档风格请参阅{@link DocFlavor#hostEncoding hostEncoding}打印数据
         * 表示类名= <CODE>"javaioInputStream" (字节流)。
         * 
         */
        public static final INPUT_STREAM TEXT_PLAIN_HOST =
            new INPUT_STREAM ("text/plain; charset="+hostEncoding);

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-8"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / plain; charset = utf-8"</CODE>,打印数据表示类名称= <CODE>"javaioInputStream"</CODE>
         * 。
         * 
         */
        public static final INPUT_STREAM TEXT_PLAIN_UTF_8 =
            new INPUT_STREAM ("text/plain; charset=utf-8");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         * 文档风格MIME类型= <CODE>"text / plain; charset = utf-16"</CODE>,打印数据表示类名= <CODE>"javaioInputStream"</CODE>。
         * 
         */
        public static final INPUT_STREAM TEXT_PLAIN_UTF_16 =
            new INPUT_STREAM ("text/plain; charset=utf-16");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16be"</CODE>
         * (big-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / plain; charset = utf-16be"</CODE>(大端字节排序),打印数据表示类名= <CODE>"javaioInputStre
         * am"</CODE>流)。
         * 
         */
        public static final INPUT_STREAM TEXT_PLAIN_UTF_16BE =
            new INPUT_STREAM ("text/plain; charset=utf-16be");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16le"</CODE>
         * (little-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / plain; charset = utf-16le"</CODE>(小端字节排序),打印数据表示类名称= <CODE>"javaioInputStr
         * eam"</CODE>流)。
         * 
         */
        public static final INPUT_STREAM TEXT_PLAIN_UTF_16LE =
            new INPUT_STREAM ("text/plain; charset=utf-16le");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=us-ascii"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / plain; charset = us-ascii"</CODE>,打印数据表示类名称= <CODE>"javaioInputStream"</CODE>
         * 。
         * 
         */
        public static final INPUT_STREAM TEXT_PLAIN_US_ASCII =
                new INPUT_STREAM ("text/plain; charset=us-ascii");

        /**
         * Doc flavor with MIME type = <CODE>"text/html"</CODE>,
         * encoded in the host platform encoding.
         * See {@link DocFlavor#hostEncoding hostEncoding}
         * Print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         * 文档风格MIME类型= <CODE>"text / html"</CODE>,编码在主机平台编码中参见{@link DocFlavor#hostEncoding hostEncoding}打印数据表示类
         * 名= <CODE>"javaioInputStream" (字节流)。
         * 
         */
        public static final INPUT_STREAM TEXT_HTML_HOST =
            new INPUT_STREAM ("text/html; charset="+hostEncoding);

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-8"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / html; charset = utf-8"</CODE>,打印数据表示类名称= <CODE>"javaioInputStream"</CODE>。
         * 
         */
        public static final INPUT_STREAM TEXT_HTML_UTF_8 =
            new INPUT_STREAM ("text/html; charset=utf-8");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / html; charset = utf-16"</CODE>,打印数据表示类名称= <CODE>"javaioInputStream"</CODE>
         * 。
         * 
         */
        public static final INPUT_STREAM TEXT_HTML_UTF_16 =
            new INPUT_STREAM ("text/html; charset=utf-16");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16be"</CODE>
         * (big-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / html; charset = utf-16be"</CODE>(big-endian字节排序),打印数据表示类名= <CODE>"javaioIn
         * putStream"</CODE>流)。
         * 
         */
        public static final INPUT_STREAM TEXT_HTML_UTF_16BE =
            new INPUT_STREAM ("text/html; charset=utf-16be");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16le"</CODE>
         * (little-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         * Doc类型= <CODE>"text / html; charset = utf-16le"</CODE>(小端字节排序),打印数据表示类名称= <CODE>"javaioInputStream"</CODE>
         * 流)。
         * 
         */
        public static final INPUT_STREAM TEXT_HTML_UTF_16LE =
            new INPUT_STREAM ("text/html; charset=utf-16le");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=us-ascii"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / html; charset = us-ascii"</CODE>,打印数据表示类名称= <CODE>"javaioInputStream"</CODE>
         * 。
         * 
         */
        public static final INPUT_STREAM TEXT_HTML_US_ASCII =
            new INPUT_STREAM ("text/html; charset=us-ascii");


        /**
         * Doc flavor with MIME type = <CODE>"application/pdf"</CODE>, print
         * data representation class name = <CODE>"java.io.InputStream"</CODE>
         * (byte stream).
         * <p>
         *  MIME类型= <CODE>"application / pdf"</CODE>打印数据表示类名称= <CODE>"javaioInputStream"</CODE>
         * 
         */
        public static final INPUT_STREAM PDF = new INPUT_STREAM ("application/pdf");

        /**
         * Doc flavor with MIME type = <CODE>"application/postscript"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  Docume风格MIME类型= <CODE>"application / postscript"</CODE>,打印数据表示类名= <CODE>"javaioInputStream"</CODE>
         * 
         */
        public static final INPUT_STREAM POSTSCRIPT =
            new INPUT_STREAM ("application/postscript");

        /**
         * Doc flavor with MIME type = <CODE>"application/vnd.hp-PCL"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         * Docion风格MIME类型= <CODE>"application / vndhp-PCL"</CODE>,打印数据表示类名= <CODE>"javaioInputStream"</CODE>
         * 
         */
        public static final INPUT_STREAM PCL =
            new INPUT_STREAM ("application/vnd.hp-PCL");

        /**
         * Doc flavor with MIME type = <CODE>"image/gif"</CODE>, print data
         * representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  Docion风格MIME类型= <CODE>"image / gif"</CODE>,打印数据表示类名= <CODE>"javaioInputStream"</CODE>
         * 
         */
        public static final INPUT_STREAM GIF = new INPUT_STREAM ("image/gif");

        /**
         * Doc flavor with MIME type = <CODE>"image/jpeg"</CODE>, print data
         * representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"image / jpeg"</CODE>,打印数据表示类名= <CODE>"javaioInputStream"</CODE>
         * 
         */
        public static final INPUT_STREAM JPEG = new INPUT_STREAM ("image/jpeg");

        /**
         * Doc flavor with MIME type = <CODE>"image/png"</CODE>, print data
         * representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"image / png"</CODE>,打印数据表示类名= <CODE>"javaioInputStream"</CODE>
         * 
         */
        public static final INPUT_STREAM PNG = new INPUT_STREAM ("image/png");

        /**
         * Doc flavor with MIME type =
         * <CODE>"application/octet-stream"</CODE>,
         * print data representation class name =
         * <CODE>"java.io.InputStream"</CODE> (byte stream).
         * The client must determine that data described
         * using this DocFlavor is valid for the printer.
         * <p>
         * 文档风格MIME类型= <CODE>"application / octet-stream"</CODE>,打印数据表示类名称= <CODE>"javaioInputStream"</CODE>(字节流
         * )客户端必须确定使用此DocFlavor对打印机有效。
         * 
         */
        public static final INPUT_STREAM AUTOSENSE =
            new INPUT_STREAM ("application/octet-stream");

    }

    /**
     * Class DocFlavor.URL provides predefined static constant DocFlavor
     * objects.
     * For example doc flavors using a Uniform Resource Locator ({@link
     * java.net.URL java.net.URL}) as the print data
     * representation  class.
     * <P>
     *
     * <p>
     *  DocFlavorURL类提供了预定义的静态常量DocFlavor对象。例如,使用统一资源定位器({@link javanetURL javanetURL})作为打印数据表示类的doc风格
     * <P>
     * 
     * 
     * @author  Alan Kaminsky
     */
    public static class URL extends DocFlavor {

        /**
         * Constructs a new doc flavor with the given MIME type and a print
         * data representation class name of <CODE>"java.net.URL"</CODE>.
         *
         * <p>
         *  构造具有给定MIME类型和打印数据表示类名为<CODE>"javanetURL"的新文档风格</CODE>
         * 
         * 
         * @param  mimeType   MIME media type string.
         *
         * @exception  NullPointerException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> is null.
         * @exception  IllegalArgumentException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> does not
         *     obey the syntax for a MIME media type string.
         */
        public URL (String mimeType) {
            super (mimeType, "java.net.URL");
        }

        /**
         * Doc flavor with MIME type = <CODE>"text/plain"</CODE>,
         * encoded in the host platform encoding.
         * See {@link DocFlavor#hostEncoding hostEncoding}
         * Print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         * 主机平台编码中编码的MIME类型= <CODE>"text / plain"</CODE>的文档风格请参阅{@link DocFlavor#hostEncoding hostEncoding}打印数据表
         * 示类名= <CODE>"javanetURL"</CODE> (字节流)。
         * 
         */
        public static final URL TEXT_PLAIN_HOST =
            new URL ("text/plain; charset="+hostEncoding);

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-8"</CODE>,
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-8"</CODE>,打印数据表示类名= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL TEXT_PLAIN_UTF_8 =
            new URL ("text/plain; charset=utf-8");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16"</CODE>,
         * print data representation class name =
         * <CODE>java.net.URL""</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / plain; charset = utf-16"</CODE>,打印数据表示类名称= <CODE> javanetURL""</CODE>
         * 
         */
        public static final URL TEXT_PLAIN_UTF_16 =
            new URL ("text/plain; charset=utf-16");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16be"</CODE>
         * (big-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-16be"</CODE>(大端字节排序),打印数据表示类名称= <CODE>"javanetURL"</CODE>流
         * )。
         * 
         */
        public static final URL TEXT_PLAIN_UTF_16BE =
            new URL ("text/plain; charset=utf-16be");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=utf-16le"</CODE>
         * (little-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         * Doc类型= <CODE>"text / plain; charset = utf-16le"</CODE>(小端字节排序),打印数据表示类名称= <CODE>"javanetURL"</CODE>流)
         * 。
         * 
         */
        public static final URL TEXT_PLAIN_UTF_16LE =
            new URL ("text/plain; charset=utf-16le");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/plain; charset=us-ascii"</CODE>,
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = us-ascii"</CODE>,打印数据表示类名= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL TEXT_PLAIN_US_ASCII =
            new URL ("text/plain; charset=us-ascii");

        /**
         * Doc flavor with MIME type = <CODE>"text/html"</CODE>,
         * encoded in the host platform encoding.
         * See {@link DocFlavor#hostEncoding hostEncoding}
         * Print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  文档风格MIME类型= <CODE>"text / html"</CODE>,编码在主机平台编码中参见{@link DocFlavor#hostEncoding hostEncoding}打印数据表示
         * 类名= <CODE>"javanetURL" (字节流)。
         * 
         */
        public static final URL TEXT_HTML_HOST =
            new URL ("text/html; charset="+hostEncoding);

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-8"</CODE>,
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = utf-8"</CODE>,打印数据表示类名= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL TEXT_HTML_UTF_8 =
            new URL ("text/html; charset=utf-8");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16"</CODE>,
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         * 文档风格MIME类型= <CODE>"text / html; charset = utf-16"</CODE>,打印数据表示类名= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL TEXT_HTML_UTF_16 =
            new URL ("text/html; charset=utf-16");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16be"</CODE>
         * (big-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = utf-16be"</CODE>(大端字节排序),打印数据表示类名称= <CODE>"javanetURL"</CODE>流)
         * 。
         * 
         */
        public static final URL TEXT_HTML_UTF_16BE =
            new URL ("text/html; charset=utf-16be");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=utf-16le"</CODE>
         * (little-endian byte ordering),
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = utf-16le"</CODE>(小端字节排序),打印数据表示类名称= <CODE>"javanetURL"</CODE>流)
         * 。
         * 
         */
        public static final URL TEXT_HTML_UTF_16LE =
            new URL ("text/html; charset=utf-16le");

        /**
         * Doc flavor with MIME type =
         * <CODE>"text/html; charset=us-ascii"</CODE>,
         * print data representation class name =
         * <CODE>"java.net.URL"</CODE> (byte stream).
         * <p>
         *  Doc类型= <CODE>"text / html; charset = us-ascii"</CODE>打印数据表示类名称= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL TEXT_HTML_US_ASCII =
            new URL ("text/html; charset=us-ascii");


        /**
         * Doc flavor with MIME type = <CODE>"application/pdf"</CODE>, print
         * data representation class name = <CODE>"java.net.URL"</CODE>.
         * <p>
         * Doc类型= <CODE>"application / pdf"</CODE>打印数据表示类名= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL PDF = new URL ("application/pdf");

        /**
         * Doc flavor with MIME type = <CODE>"application/postscript"</CODE>,
         * print data representation class name = <CODE>"java.net.URL"</CODE>.
         * <p>
         *  MIME类型= <CODE>"application / postscript"</CODE>打印数据表示类名称= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL POSTSCRIPT = new URL ("application/postscript");

        /**
         * Doc flavor with MIME type = <CODE>"application/vnd.hp-PCL"</CODE>,
         * print data representation class name = <CODE>"java.net.URL"</CODE>.
         * <p>
         *  文档风格MIME类型= <CODE>"application / vndhp-PCL"</CODE>,打印数据表示类名= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL PCL = new URL ("application/vnd.hp-PCL");

        /**
         * Doc flavor with MIME type = <CODE>"image/gif"</CODE>, print data
         * representation class name = <CODE>"java.net.URL"</CODE>.
         * <p>
         *  MIME类型= <CODE>"image / gif"</CODE>打印数据表示类名称= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL GIF = new URL ("image/gif");

        /**
         * Doc flavor with MIME type = <CODE>"image/jpeg"</CODE>, print data
         * representation class name = <CODE>"java.net.URL"</CODE>.
         * <p>
         *  Doc类型= <CODE>"image / jpeg"</CODE>打印数据表示类名称= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL JPEG = new URL ("image/jpeg");

        /**
         * Doc flavor with MIME type = <CODE>"image/png"</CODE>, print data
         * representation class name = <CODE>"java.net.URL"</CODE>.
         * <p>
         *  MIME类型= <CODE>"image / png"</CODE>打印数据表示类名称= <CODE>"javanetURL"</CODE>
         * 
         */
        public static final URL PNG = new URL ("image/png");

        /**
         * Doc flavor with MIME type =
         * <CODE>"application/octet-stream"</CODE>,
         * print data representation class name = <CODE>"java.net.URL"</CODE>.
         *  The client must determine that data described
         * using this DocFlavor is valid for the printer.
         * <p>
         * Doc类型= <CODE>"application / octet-stream"</CODE>打印数据表示类名称= <CODE>"javanetURL"</CODE>客户端必须确定使用此DocFlav
         * or描述的数据对于打印机。
         * 
         */
        public static final URL AUTOSENSE = new URL ("application/octet-stream");

    }

    /**
     * Class DocFlavor.CHAR_ARRAY provides predefined static constant
     * DocFlavor objects for example doc flavors using a character array
     * (<CODE>char[]</CODE>) as the print data representation class. As such,
     * the character set is Unicode.
     * <P>
     *
     * <p>
     *  DocFlavorCHAR_ARRAY类提供了使用字符数组(<CODE> char [] </CODE>)作为打印数据表示类的示例doc风格的预定义静态常量DocFlavor对象。
     * 因此,字符集为Unicode。
     * <P>
     * 
     * 
     * @author  Alan Kaminsky
     */
    public static class CHAR_ARRAY extends DocFlavor {

        private static final long serialVersionUID = -8720590903724405128L;

        /**
         * Constructs a new doc flavor with the given MIME type and a print
         * data representation class name of
         * <CODE>"[C"</CODE> (character array).
         *
         * <p>
         *  使用给定的MIME类型和打印数据表示类名<CODE>"[C"</CODE>(字符数组)构造一个新的文档风格
         * 
         * 
         * @param  mimeType  MIME media type string. If it is a text media
         *                      type, it is assumed to contain a
         *                      <CODE>"charset=utf-16"</CODE> parameter.
         *
         * @exception  NullPointerException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> is null.
         * @exception  IllegalArgumentException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> does not
         *     obey the syntax for a MIME media type string.
         */
        public CHAR_ARRAY (String mimeType) {
            super (mimeType, "[C");
        }

        /**
         * Doc flavor with MIME type = <CODE>"text/plain;
         * charset=utf-16"</CODE>, print data representation class name =
         * <CODE>"[C"</CODE> (character array).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-16"</CODE>,打印数据表示类名称= <CODE>"[C"</CODE>
         * 
         */
        public static final CHAR_ARRAY TEXT_PLAIN =
            new CHAR_ARRAY ("text/plain; charset=utf-16");

        /**
         * Doc flavor with MIME type = <CODE>"text/html;
         * charset=utf-16"</CODE>, print data representation class name =
         * <CODE>"[C"</CODE> (character array).
         * <p>
         * Doc类型= <CODE>"text / html; charset = utf-16"</CODE>,打印数据表示类名称= <CODE>"[C"</CODE>
         * 
         */
        public static final CHAR_ARRAY TEXT_HTML =
            new CHAR_ARRAY ("text/html; charset=utf-16");

    }

    /**
     * Class DocFlavor.STRING provides predefined static constant DocFlavor
     * objects for example doc flavors using a string ({@link java.lang.String
     * java.lang.String}) as the print data representation class.
     * As such, the character set is Unicode.
     * <P>
     *
     * <p>
     *  DocFlavorSTRING类提供了预定义的静态常量DocFlavor对象,例如使用字符串({@link javalangString javalangString})作为打印数据表示类的doc风格
     * 。
     * 因此,字符集是Unicode。
     * <P>
     * 
     * 
     * @author  Alan Kaminsky
     */
    public static class STRING extends DocFlavor {

        private static final long serialVersionUID = 4414407504887034035L;

        /**
         * Constructs a new doc flavor with the given MIME type and a print
         * data representation class name of <CODE>"java.lang.String"</CODE>.
         *
         * <p>
         *  构造具有给定MIME类型和打印数据表示类名为<CODE>"javalangString"的新文档风格</CODE>
         * 
         * 
         * @param  mimeType  MIME media type string. If it is a text media
         *                      type, it is assumed to contain a
         *                      <CODE>"charset=utf-16"</CODE> parameter.
         *
         * @exception  NullPointerException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> is null.
         * @exception  IllegalArgumentException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> does not
         *     obey the syntax for a MIME media type string.
         */
        public STRING (String mimeType) {
            super (mimeType, "java.lang.String");
        }

        /**
         * Doc flavor with MIME type = <CODE>"text/plain;
         * charset=utf-16"</CODE>, print data representation class name =
         * <CODE>"java.lang.String"</CODE>.
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-16"</CODE>打印数据表示类名称= <CODE>"javalangString"</CODE>
         * 
         */
        public static final STRING TEXT_PLAIN =
            new STRING ("text/plain; charset=utf-16");

        /**
         * Doc flavor with MIME type = <CODE>"text/html;
         * charset=utf-16"</CODE>, print data representation class name =
         * <CODE>"java.lang.String"</CODE>.
         * <p>
         * Doc类型= <CODE>"text / html; charset = utf-16"</CODE>打印数据表示类名称= <CODE>"javalangString"</CODE>
         * 
         */
        public static final STRING TEXT_HTML =
            new STRING ("text/html; charset=utf-16");
    }

    /**
     * Class DocFlavor.READER provides predefined static constant DocFlavor
     * objects for example doc flavors using a character stream ({@link
     * java.io.Reader java.io.Reader}) as the print data
     * representation class. As such, the character set is Unicode.
     * <P>
     *
     * <p>
     *  DocFlavorREADER类提供了预定义的静态常量DocFlavor对象,例如使用字符流({@link javaioReader javaioReader})作为打印数据表示类的doc字符。
     * 因此,字符集是Unicode。
     * <P>
     * 
     * 
     * @author  Alan Kaminsky
     */
    public static class READER extends DocFlavor {

        private static final long serialVersionUID = 7100295812579351567L;

        /**
         * Constructs a new doc flavor with the given MIME type and a print
         * data representation class name of\
         * <CODE>"java.io.Reader"</CODE> (character stream).
         *
         * <p>
         *  使用给定的MIME类型和打印数据表示类名称\\ <CODE>"javaioReader"</CODE>(字符流)构造新的文档风格
         * 
         * 
         * @param  mimeType  MIME media type string. If it is a text media
         *                      type, it is assumed to contain a
         *                      <CODE>"charset=utf-16"</CODE> parameter.
         *
         * @exception  NullPointerException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> is null.
         * @exception  IllegalArgumentException
         *     (unchecked exception) Thrown if <CODE>mimeType</CODE> does not
         *     obey the syntax for a MIME media type string.
         */
        public READER (String mimeType) {
            super (mimeType, "java.io.Reader");
        }

        /**
         * Doc flavor with MIME type = <CODE>"text/plain;
         * charset=utf-16"</CODE>, print data representation class name =
         * <CODE>"java.io.Reader"</CODE> (character stream).
         * <p>
         *  Doc类型= <CODE>"text / plain; charset = utf-16"</CODE>,打印数据表示类名称= <CODE>"javaioReader"</CODE>
         * 
         */
        public static final READER TEXT_PLAIN =
            new READER ("text/plain; charset=utf-16");

        /**
         * Doc flavor with MIME type = <CODE>"text/html;
         * charset=utf-16"</CODE>, print data representation class name =
         * <CODE>"java.io.Reader"</CODE> (character stream).
         * <p>
         * Doc类型= <CODE>"text / html; charset = utf-16"</CODE>,打印数据表示类名称= <CODE>"javaioReader"</CODE>
         * 
         */
        public static final READER TEXT_HTML =
            new READER ("text/html; charset=utf-16");

    }

    /**
     * Class DocFlavor.SERVICE_FORMATTED provides predefined static constant
     * DocFlavor objects for example doc flavors for service formatted print
     * data.
     * <P>
     *
     * <p>
     *  DocFlavorSERVICE_FORMATTED类提供了预定义的静态常量DocFlavor对象,例如用于服务格式化打印数据的doc风格
     * <P>
     * 
     * 
     * @author  Alan Kaminsky
     */
    public static class SERVICE_FORMATTED extends DocFlavor {

        private static final long serialVersionUID = 6181337766266637256L;

        /**
         * Constructs a new doc flavor with a MIME type of
         * <CODE>"application/x-java-jvm-local-objectref"</CODE> indicating
         * service formatted print data and the given print data
         * representation class name.
         *
         * <p>
         *  使用MIME类型<CODE>"application / x-java-jvm-local-objectref"</CODE>构造新的文档风格,指示服务格式化的打印数据和给定的打印数据表示类名称
         * 
         * 
         * @param  className  Fully-qualified representation class name.
         *
         * @exception  NullPointerException
         *     (unchecked exception) Thrown if <CODE>className</CODE> is
         *     null.
         */
        public SERVICE_FORMATTED (String className) {
            super ("application/x-java-jvm-local-objectref", className);
        }

        /**
         * Service formatted print data doc flavor with print data
         * representation class name =
         * <CODE>"java.awt.image.renderable.RenderableImage"</CODE>
         * (renderable image object).
         * <p>
         *  服务格式化打印数据doc风格与打印数据表示类名称= <CODE>"javaawtimagerenderableRenderableImage"</CODE>(可渲染图像对象)
         * 
         */
        public static final SERVICE_FORMATTED RENDERABLE_IMAGE =
            new SERVICE_FORMATTED("java.awt.image.renderable.RenderableImage");

        /**
         * Service formatted print data doc flavor with print data
         * representation class name = <CODE>"java.awt.print.Printable"</CODE>
         * (printable object).
         * <p>
         * 服务格式化打印数据doc风格与打印数据表示类名称= <CODE>"javaawtprintPrintable"</CODE>(可打印对象)
         * 
         */
        public static final SERVICE_FORMATTED PRINTABLE =
            new SERVICE_FORMATTED ("java.awt.print.Printable");

        /**
         * Service formatted print data doc flavor with print data
         * representation class name = <CODE>"java.awt.print.Pageable"</CODE>
         * (pageable object).
         * <p>
         *  服务格式化的打印数据doc风格与打印数据表示类名称= <CODE>"javaawtprintPageable"</CODE>(可分页对象)
         */
        public static final SERVICE_FORMATTED PAGEABLE =
            new SERVICE_FORMATTED ("java.awt.print.Pageable");

        }

}
