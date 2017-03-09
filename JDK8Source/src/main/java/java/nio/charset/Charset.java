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

package java.nio.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.spi.CharsetProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;
import java.util.SortedMap;
import java.util.TreeMap;
import sun.misc.ASCIICaseInsensitiveComparator;
import sun.nio.cs.StandardCharsets;
import sun.nio.cs.ThreadLocalCoders;
import sun.security.action.GetPropertyAction;


/**
 * A named mapping between sequences of sixteen-bit Unicode <a
 * href="../../lang/Character.html#unicode">code units</a> and sequences of
 * bytes.  This class defines methods for creating decoders and encoders and
 * for retrieving the various names associated with a charset.  Instances of
 * this class are immutable.
 *
 * <p> This class also defines static methods for testing whether a particular
 * charset is supported, for locating charset instances by name, and for
 * constructing a map that contains every charset for which support is
 * available in the current Java virtual machine.  Support for new charsets can
 * be added via the service-provider interface defined in the {@link
 * java.nio.charset.spi.CharsetProvider} class.
 *
 * <p> All of the methods defined in this class are safe for use by multiple
 * concurrent threads.
 *
 *
 * <a name="names"></a><a name="charenc"></a>
 * <h2>Charset names</h2>
 *
 * <p> Charsets are named by strings composed of the following characters:
 *
 * <ul>
 *
 *   <li> The uppercase letters <tt>'A'</tt> through <tt>'Z'</tt>
 *        (<tt>'&#92;u0041'</tt>&nbsp;through&nbsp;<tt>'&#92;u005a'</tt>),
 *
 *   <li> The lowercase letters <tt>'a'</tt> through <tt>'z'</tt>
 *        (<tt>'&#92;u0061'</tt>&nbsp;through&nbsp;<tt>'&#92;u007a'</tt>),
 *
 *   <li> The digits <tt>'0'</tt> through <tt>'9'</tt>
 *        (<tt>'&#92;u0030'</tt>&nbsp;through&nbsp;<tt>'&#92;u0039'</tt>),
 *
 *   <li> The dash character <tt>'-'</tt>
 *        (<tt>'&#92;u002d'</tt>,&nbsp;<small>HYPHEN-MINUS</small>),
 *
 *   <li> The plus character <tt>'+'</tt>
 *        (<tt>'&#92;u002b'</tt>,&nbsp;<small>PLUS SIGN</small>),
 *
 *   <li> The period character <tt>'.'</tt>
 *        (<tt>'&#92;u002e'</tt>,&nbsp;<small>FULL STOP</small>),
 *
 *   <li> The colon character <tt>':'</tt>
 *        (<tt>'&#92;u003a'</tt>,&nbsp;<small>COLON</small>), and
 *
 *   <li> The underscore character <tt>'_'</tt>
 *        (<tt>'&#92;u005f'</tt>,&nbsp;<small>LOW&nbsp;LINE</small>).
 *
 * </ul>
 *
 * A charset name must begin with either a letter or a digit.  The empty string
 * is not a legal charset name.  Charset names are not case-sensitive; that is,
 * case is always ignored when comparing charset names.  Charset names
 * generally follow the conventions documented in <a
 * href="http://www.ietf.org/rfc/rfc2278.txt"><i>RFC&nbsp;2278:&nbsp;IANA Charset
 * Registration Procedures</i></a>.
 *
 * <p> Every charset has a <i>canonical name</i> and may also have one or more
 * <i>aliases</i>.  The canonical name is returned by the {@link #name() name} method
 * of this class.  Canonical names are, by convention, usually in upper case.
 * The aliases of a charset are returned by the {@link #aliases() aliases}
 * method.
 *
 * <p><a name="hn">Some charsets have an <i>historical name</i> that is defined for
 * compatibility with previous versions of the Java platform.</a>  A charset's
 * historical name is either its canonical name or one of its aliases.  The
 * historical name is returned by the <tt>getEncoding()</tt> methods of the
 * {@link java.io.InputStreamReader#getEncoding InputStreamReader} and {@link
 * java.io.OutputStreamWriter#getEncoding OutputStreamWriter} classes.
 *
 * <p><a name="iana"> </a>If a charset listed in the <a
 * href="http://www.iana.org/assignments/character-sets"><i>IANA Charset
 * Registry</i></a> is supported by an implementation of the Java platform then
 * its canonical name must be the name listed in the registry. Many charsets
 * are given more than one name in the registry, in which case the registry
 * identifies one of the names as <i>MIME-preferred</i>.  If a charset has more
 * than one registry name then its canonical name must be the MIME-preferred
 * name and the other names in the registry must be valid aliases.  If a
 * supported charset is not listed in the IANA registry then its canonical name
 * must begin with one of the strings <tt>"X-"</tt> or <tt>"x-"</tt>.
 *
 * <p> The IANA charset registry does change over time, and so the canonical
 * name and the aliases of a particular charset may also change over time.  To
 * ensure compatibility it is recommended that no alias ever be removed from a
 * charset, and that if the canonical name of a charset is changed then its
 * previous canonical name be made into an alias.
 *
 *
 * <h2>Standard charsets</h2>
 *
 *
 *
 * <p><a name="standard">Every implementation of the Java platform is required to support the
 * following standard charsets.</a>  Consult the release documentation for your
 * implementation to see if any other charsets are supported.  The behavior
 * of such optional charsets may differ between implementations.
 *
 * <blockquote><table width="80%" summary="Description of standard charsets">
 * <tr><th align="left">Charset</th><th align="left">Description</th></tr>
 * <tr><td valign=top><tt>US-ASCII</tt></td>
 *     <td>Seven-bit ASCII, a.k.a. <tt>ISO646-US</tt>,
 *         a.k.a. the Basic Latin block of the Unicode character set</td></tr>
 * <tr><td valign=top><tt>ISO-8859-1&nbsp;&nbsp;</tt></td>
 *     <td>ISO Latin Alphabet No. 1, a.k.a. <tt>ISO-LATIN-1</tt></td></tr>
 * <tr><td valign=top><tt>UTF-8</tt></td>
 *     <td>Eight-bit UCS Transformation Format</td></tr>
 * <tr><td valign=top><tt>UTF-16BE</tt></td>
 *     <td>Sixteen-bit UCS Transformation Format,
 *         big-endian byte&nbsp;order</td></tr>
 * <tr><td valign=top><tt>UTF-16LE</tt></td>
 *     <td>Sixteen-bit UCS Transformation Format,
 *         little-endian byte&nbsp;order</td></tr>
 * <tr><td valign=top><tt>UTF-16</tt></td>
 *     <td>Sixteen-bit UCS Transformation Format,
 *         byte&nbsp;order identified by an optional byte-order mark</td></tr>
 * </table></blockquote>
 *
 * <p> The <tt>UTF-8</tt> charset is specified by <a
 * href="http://www.ietf.org/rfc/rfc2279.txt"><i>RFC&nbsp;2279</i></a>; the
 * transformation format upon which it is based is specified in
 * Amendment&nbsp;2 of ISO&nbsp;10646-1 and is also described in the <a
 * href="http://www.unicode.org/unicode/standard/standard.html"><i>Unicode
 * Standard</i></a>.
 *
 * <p> The <tt>UTF-16</tt> charsets are specified by <a
 * href="http://www.ietf.org/rfc/rfc2781.txt"><i>RFC&nbsp;2781</i></a>; the
 * transformation formats upon which they are based are specified in
 * Amendment&nbsp;1 of ISO&nbsp;10646-1 and are also described in the <a
 * href="http://www.unicode.org/unicode/standard/standard.html"><i>Unicode
 * Standard</i></a>.
 *
 * <p> The <tt>UTF-16</tt> charsets use sixteen-bit quantities and are
 * therefore sensitive to byte order.  In these encodings the byte order of a
 * stream may be indicated by an initial <i>byte-order mark</i> represented by
 * the Unicode character <tt>'&#92;uFEFF'</tt>.  Byte-order marks are handled
 * as follows:
 *
 * <ul>
 *
 *   <li><p> When decoding, the <tt>UTF-16BE</tt> and <tt>UTF-16LE</tt>
 *   charsets interpret the initial byte-order marks as a <small>ZERO-WIDTH
 *   NON-BREAKING SPACE</small>; when encoding, they do not write
 *   byte-order marks. </p></li>

 *
 *   <li><p> When decoding, the <tt>UTF-16</tt> charset interprets the
 *   byte-order mark at the beginning of the input stream to indicate the
 *   byte-order of the stream but defaults to big-endian if there is no
 *   byte-order mark; when encoding, it uses big-endian byte order and writes
 *   a big-endian byte-order mark. </p></li>
 *
 * </ul>
 *
 * In any case, byte order marks occurring after the first element of an
 * input sequence are not omitted since the same code is used to represent
 * <small>ZERO-WIDTH NON-BREAKING SPACE</small>.
 *
 * <p> Every instance of the Java virtual machine has a default charset, which
 * may or may not be one of the standard charsets.  The default charset is
 * determined during virtual-machine startup and typically depends upon the
 * locale and charset being used by the underlying operating system. </p>
 *
 * <p>The {@link StandardCharsets} class defines constants for each of the
 * standard charsets.
 *
 * <h2>Terminology</h2>
 *
 * <p> The name of this class is taken from the terms used in
 * <a href="http://www.ietf.org/rfc/rfc2278.txt"><i>RFC&nbsp;2278</i></a>.
 * In that document a <i>charset</i> is defined as the combination of
 * one or more coded character sets and a character-encoding scheme.
 * (This definition is confusing; some other software systems define
 * <i>charset</i> as a synonym for <i>coded character set</i>.)
 *
 * <p> A <i>coded character set</i> is a mapping between a set of abstract
 * characters and a set of integers.  US-ASCII, ISO&nbsp;8859-1,
 * JIS&nbsp;X&nbsp;0201, and Unicode are examples of coded character sets.
 *
 * <p> Some standards have defined a <i>character set</i> to be simply a
 * set of abstract characters without an associated assigned numbering.
 * An alphabet is an example of such a character set.  However, the subtle
 * distinction between <i>character set</i> and <i>coded character set</i>
 * is rarely used in practice; the former has become a short form for the
 * latter, including in the Java API specification.
 *
 * <p> A <i>character-encoding scheme</i> is a mapping between one or more
 * coded character sets and a set of octet (eight-bit byte) sequences.
 * UTF-8, UTF-16, ISO&nbsp;2022, and EUC are examples of
 * character-encoding schemes.  Encoding schemes are often associated with
 * a particular coded character set; UTF-8, for example, is used only to
 * encode Unicode.  Some schemes, however, are associated with multiple
 * coded character sets; EUC, for example, can be used to encode
 * characters in a variety of Asian coded character sets.
 *
 * <p> When a coded character set is used exclusively with a single
 * character-encoding scheme then the corresponding charset is usually
 * named for the coded character set; otherwise a charset is usually named
 * for the encoding scheme and, possibly, the locale of the coded
 * character sets that it supports.  Hence <tt>US-ASCII</tt> is both the
 * name of a coded character set and of the charset that encodes it, while
 * <tt>EUC-JP</tt> is the name of the charset that encodes the
 * JIS&nbsp;X&nbsp;0201, JIS&nbsp;X&nbsp;0208, and JIS&nbsp;X&nbsp;0212
 * coded character sets for the Japanese language.
 *
 * <p> The native character encoding of the Java programming language is
 * UTF-16.  A charset in the Java platform therefore defines a mapping
 * between sequences of sixteen-bit UTF-16 code units (that is, sequences
 * of chars) and sequences of bytes. </p>
 *
 *
 * <p>
 *  在十六位Unicode <a href="../../lang/Character.html#unicode">代码单元</a>序列和字节序列之间的命名映射。
 * 该类定义了用于创建解码器和编码器以及用于检索与字符集相关联的各种名称的方法。这个类的实例是不可变的。
 * 
 *  <p>此类还定义了用于测试是否支持特定字符集的静态方法,用于按名称定位charset实例,以及用于构造包含当前Java虚拟机中可用支持的每个字符集的映射。
 * 可以通过{@link java.nio.charset.spi.CharsetProvider}类中定义的服务提供程序接口添加对新字符集的支持。
 * 
 *  <p>此类中定义的所有方法都可安全地用于多个并发线程。
 * 
 *  <a name="names"> </a> <a name="charenc"> </a> <h2>字符集名称</h2>
 * 
 *  <p>字符集由以下字符组成的字符串命名：
 * 
 * <ul>
 * 
 *  <li>大写字母<tt>'A'</tt>至<tt>'Z'</tt>(<tt>'\ u0041'</tt>&nbsp; through&nbsp; <tt>'\ u005a' </tt>),
 * 
 *  <li>小写字母<tt>'a'</tt>至<tt>'z'</tt>(<tt>'\ u0061'</tt>&nbsp;通过&lt; tt>'\ u007a' </tt>),
 * 
 *  <li>数字<tt>'0'</tt>至<tt>'9'</tt>(<tt>'\ u0030'</tt>&nbsp;通过<tt>'\ u0039' / tt>),
 * 
 * <li>短划字符<tt>' - '</tt>(<tt>'\ u002d'</tt>,&nbsp; <small> HYPHEN-MINUS </small>),
 * 
 *  <li>加号字符<tt>'+'</tt>(<tt>'\ u002b'</tt>,&nbsp; <small> PLUS SIGN </small>),
 * 
 *  <li>句点字符<tt>'。'</tt>(<tt>'\ u002e'</tt>,&nbsp; <small> FULL STOP </small>),
 * 
 *  <li>冒号<tt>'：'</tt>(<tt>'\ u003a'</tt>,&nbsp; <small> COLON </small>),以及
 * 
 *  <li>下划线字符<tt>'_'</tt>(<tt>'\ u005f'</tt>,&nbsp; <small> LOW&nbsp; LINE </small>)。
 * 
 * </ul>
 * 
 *  字符集名称必须以字母或数字开头。空字符串不是法定字符集名称。字符集名称不区分大小写;也就是说,比较字符集名称时总是忽略大小写。
 * 字符集名称通常遵循<a href="http://www.ietf.org/rfc/rfc2278.txt"> <i> RFC 2278：IANA字符集注册程序</i> </a >。
 * 
 *  <p>每个字符集都有一个<i>规范名称</i>,也可能有一个或多个<i>别名</i>。规范名称由此类的{@link #name()name}方法返回。按照惯例,规范名称通常为大写。
 * 字符集的别名由{@link #aliases()aliases}方法返回。
 * 
 * <p> <a name="hn">某些字符集具有为与先前版本的Java平台兼容而定义的<i>历史名称</i>。</a>字符集的历史名称是其规范名称或其别名之一。
 * 历史名称由{@link java.io.InputStreamReader#getEncoding InputStreamReader}和{@link java.io.OutputStreamWriter#getEncoding OutputStreamWriter}
 * 类的<tt> getEncoding()</tt>方法返回。
 * <p> <a name="hn">某些字符集具有为与先前版本的Java平台兼容而定义的<i>历史名称</i>。</a>字符集的历史名称是其规范名称或其别名之一。
 * 
 *  <p> <a name="iana"> </a>如果<a href="http://www.iana.org/assignments/character-sets"> <i> IANA Charset
 * 注册表中列出的字符集< / i> </a>由Java平台的实现支持,则其规范名称必须是注册表中列出的名称。
 * 许多字符集在注册表中被赋予多个名称,在这种情况下,注册表将其中一个名称标识为<i> MIME优选</i>。
 * 如果字符集有多个注册表名称,则其规范名称必须是MIME首选名称,注册表中的其他名称必须是有效的别名。
 * 如果支持的字符集未在IANA注册表中列出,则其规范名称必须以字符串<tt>"X  - "</tt>或<tt>"x  - "</tt>开头。
 * 
 * <p> IANA字符集注册表会随时间而变化,因此特定字符集的规范名称和别名也可能会随时间而变化。为了确保兼容性,建议不要从字符集中删除别名,如果字符集的规范名称已更改,那么其以前的规范名称将变为别名。
 * 
 *  <h2>标准字符集</h2>
 * 
 *  <p> <a name="standard">需要每个Java平台的实现来支持以下标准字符集。</a>请参阅发行版文档以了解您的实现,看看是否支持任何其他字符集。
 * 这些可选字符集的行为在实现之间可能不同。
 * 
 * <blockquote> <table width ="80％"summary ="标准字符集说明"> <tr> <th align ="left">字符集</th> <th align ="left">
 * 描述</th> / tr> <tr> <td valign = top> <tt> US-ASCII </tt> </td> <td> Seven-bit ASCII,aka <tt> ISO646-U
 * S </tt>块的Unicode字符集</td> </tr> <tr> <td valign = top> <tt> ISO-8859-1&nbsp;&nbsp; </tt> </td> <td> 1,
 * aka <tt> ISO-LATIN-1 </tt> </td> </tr> <tr> <td valign = top> <tt> UTF-8 </tt> </td> <td>八位UCS转换格式</td>
 *  </tr> <tr> <td valign = top> <tt> UTF-16BE </tt> </td> <td> 16位UCS转换格式,big-endian字节; order </td> </tr>
 *  <tr> <td valign = top> <tt> UTF-16LE </tt> </td> <td> 16位UCS转换格式,little-endian字节& / td> </tr> <tr> <td valign = top>
 *  <tt> UTF-16 </tt> </td> <td>十六位UCS变换格式,字节顺序由可选字节顺序标记</td> </tr> </table> </blockquote>。
 * 
 *  <p> <tt> UTF-8 </tt>字符集由<a href="http://www.ietf.org/rfc/rfc2279.txt"> <i> RFC&nbsp; 2279 </i> </a>;
 * 其所基于的转换格式在ISO 10646-1的修订2中规定,并且还在<a href="http://www.unicode.org/unicode/standard/standard.html">中进行了
 * 说明<i> Unicode标准</i> </a>。
 * 
 * <p> <tt> UTF-16 </tt>字符集由<a href="http://www.ietf.org/rfc/rfc2781.txt"> <i> RFC 2781 </i> </a>;它们所基于的
 * 转换格式在ISO 10646-1的修订1中规定,并且也在<a href="http://www.unicode.org/unicode/standard/standard.html"> <i> Unic
 * ode标准</i> </a>。
 * 
 *  <p> <tt> UTF-16 </tt>字符集使用十六位数量,因此对字节顺序很敏感。
 * 在这些编码中,流的字节顺序可以由Unicode字符<tt>'\ uFEFF'</tt>表示的初始字节顺序标记</i>来指示。字节顺序标记处理如下：。
 * 
 * <ul>
 * 
 *  <li> <p>解码时,<tt> UTF-16BE </tt>和<tt> UTF-16LE </tt>字符集将初始字节顺序标记解释为<small> ZERO-WIDTH NON-BREAKING SP
 * ACE </small>;当编码时,它们不写入字节顺序标记。
 *  </p> </li>。
 * 
 *  <li> <p>解码时,<tt> UTF-16 </tt>字符集解释输入流开头的字节顺序标记,表示流的字节顺序,但默认为big-endian if没有字节顺序标记;当编码时,它使用big-endian
 * 字节顺序并写一个big-endian字节顺序标记。
 *  </p> </li>。
 * 
 * </ul>
 * 
 *  在任何情况下,出现在输入序列的第一个元素之后的字节顺序标记不被省略,因为相同的代码用于表示<small> ZERO-WIDTH NON-BREAKING SPACE </small>。
 * 
 * <p> Java虚拟机的每个实例都有一个默认字符集,它可以是也可以不是标准字符集之一。默认字符集在虚拟机启动期间确定,并且通常取决于底层操作系统正在使用的区域设置和字符集。 </p>
 * 
 *  <p> {@link StandardCharsets}类定义了每个标准字符集的常量。
 * 
 *  <h2>术语</h2>
 * 
 *  <p>此类别的名称取自<a href="http://www.ietf.org/rfc/rfc2278.txt"> <i> RFC 2278 </i> </a中使用的条款>。
 * 在该文档中,字符集</i>被定义为一个或多个编码字符集和字符编码方案的组合。 (这个定义很混乱;一些其他软件系统将<i> charset </i>定义为<i>编码字符集</i>的同义词)。
 * 
 *  <p>编码字符集</i>是一组抽象字符和一组整数之间的映射。 US-ASCII,ISO&nbsp; 8859-1,JIS&nbsp; X&nbsp; 0201和Unicode是编码字符集的示例。
 * 
 *  <p>一些标准已将<i>字符集</i>定义为简单的一组抽象字符,而没有相关联的分配的编号。字母表是这种字符集的示例。
 * 然而,在字符集和编码字符集之间的细微区别在实践中很少使用;前者已经成为后者的简短形式,包括在Java API规范中。
 * 
 * </p>字符编码方案</i>是一个或多个编码字符集和一组八位字节(八位字节)序列之间的映射。 UTF-8,UTF-16,ISO 2022和EUC是字符编码方案的示例。
 * 编码方案通常与特定的编码字符集相关联;例如,UTF-8仅用于编码Unicode。然而,一些方案与多个编码字符集相关联;例如,EUC可以用于编码各种亚洲编码字符集中的字符。
 * 
 *  <p>当编码字符集专用于单个字符编码方案时,相应的字符集通常为编码字符集命名;否则字符集通常为编码方案命名,并且可能包含其支持的编码字符集的语言环境。
 * 因此,<tt> US-ASCII </tt>既是编码字符集的名称,也是对其进行编码的字符集的名称,<tt> EUC-JP </tt> X&nbsp; 0201,JIS&nbsp; X&020; 0208
 * 和JIS&nbsp; X&02; 0212编码字符集。
 *  <p>当编码字符集专用于单个字符编码方案时,相应的字符集通常为编码字符集命名;否则字符集通常为编码方案命名,并且可能包含其支持的编码字符集的语言环境。
 * 
 *  <p> Java编程语言的本机字符编码是UTF-16。因此,Java平台中的字符集定义了16位UTF-16代码单元(即字符序列)序列和字节序列之间的映射。 </p>
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 *
 * @see CharsetDecoder
 * @see CharsetEncoder
 * @see java.nio.charset.spi.CharsetProvider
 * @see java.lang.Character
 */

public abstract class Charset
    implements Comparable<Charset>
{

    /* -- Static methods -- */

    private static volatile String bugLevel = null;

    static boolean atBugLevel(String bl) {              // package-private
        String level = bugLevel;
        if (level == null) {
            if (!sun.misc.VM.isBooted())
                return false;
            bugLevel = level = AccessController.doPrivileged(
                new GetPropertyAction("sun.nio.cs.bugLevel", ""));
        }
        return level.equals(bl);
    }

    /**
     * Checks that the given string is a legal charset name. </p>
     *
     * <p>
     *  检查给定的字符串是否是合法的字符集名称。 </p>
     * 
     * 
     * @param  s
     *         A purported charset name
     *
     * @throws  IllegalCharsetNameException
     *          If the given name is not a legal charset name
     */
    private static void checkName(String s) {
        int n = s.length();
        if (!atBugLevel("1.4")) {
            if (n == 0)
                throw new IllegalCharsetNameException(s);
        }
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') continue;
            if (c >= 'a' && c <= 'z') continue;
            if (c >= '0' && c <= '9') continue;
            if (c == '-' && i != 0) continue;
            if (c == '+' && i != 0) continue;
            if (c == ':' && i != 0) continue;
            if (c == '_' && i != 0) continue;
            if (c == '.' && i != 0) continue;
            throw new IllegalCharsetNameException(s);
        }
    }

    /* The standard set of charsets */
    private static CharsetProvider standardProvider = new StandardCharsets();

    // Cache of the most-recently-returned charsets,
    // along with the names that were used to find them
    //
    private static volatile Object[] cache1 = null; // "Level 1" cache
    private static volatile Object[] cache2 = null; // "Level 2" cache

    private static void cache(String charsetName, Charset cs) {
        cache2 = cache1;
        cache1 = new Object[] { charsetName, cs };
    }

    // Creates an iterator that walks over the available providers, ignoring
    // those whose lookup or instantiation causes a security exception to be
    // thrown.  Should be invoked with full privileges.
    //
    private static Iterator<CharsetProvider> providers() {
        return new Iterator<CharsetProvider>() {

                ClassLoader cl = ClassLoader.getSystemClassLoader();
                ServiceLoader<CharsetProvider> sl =
                    ServiceLoader.load(CharsetProvider.class, cl);
                Iterator<CharsetProvider> i = sl.iterator();

                CharsetProvider next = null;

                private boolean getNext() {
                    while (next == null) {
                        try {
                            if (!i.hasNext())
                                return false;
                            next = i.next();
                        } catch (ServiceConfigurationError sce) {
                            if (sce.getCause() instanceof SecurityException) {
                                // Ignore security exceptions
                                continue;
                            }
                            throw sce;
                        }
                    }
                    return true;
                }

                public boolean hasNext() {
                    return getNext();
                }

                public CharsetProvider next() {
                    if (!getNext())
                        throw new NoSuchElementException();
                    CharsetProvider n = next;
                    next = null;
                    return n;
                }

                public void remove() {
                    throw new UnsupportedOperationException();
                }

            };
    }

    // Thread-local gate to prevent recursive provider lookups
    private static ThreadLocal<ThreadLocal<?>> gate =
            new ThreadLocal<ThreadLocal<?>>();

    private static Charset lookupViaProviders(final String charsetName) {

        // The runtime startup sequence looks up standard charsets as a
        // consequence of the VM's invocation of System.initializeSystemClass
        // in order to, e.g., set system properties and encode filenames.  At
        // that point the application class loader has not been initialized,
        // however, so we can't look for providers because doing so will cause
        // that loader to be prematurely initialized with incomplete
        // information.
        //
        if (!sun.misc.VM.isBooted())
            return null;

        if (gate.get() != null)
            // Avoid recursive provider lookups
            return null;
        try {
            gate.set(gate);

            return AccessController.doPrivileged(
                new PrivilegedAction<Charset>() {
                    public Charset run() {
                        for (Iterator<CharsetProvider> i = providers();
                             i.hasNext();) {
                            CharsetProvider cp = i.next();
                            Charset cs = cp.charsetForName(charsetName);
                            if (cs != null)
                                return cs;
                        }
                        return null;
                    }
                });

        } finally {
            gate.set(null);
        }
    }

    /* The extended set of charsets */
    private static class ExtendedProviderHolder {
        static final CharsetProvider extendedProvider = extendedProvider();
        // returns ExtendedProvider, if installed
        private static CharsetProvider extendedProvider() {
            return AccessController.doPrivileged(
                       new PrivilegedAction<CharsetProvider>() {
                           public CharsetProvider run() {
                                try {
                                    Class<?> epc
                                        = Class.forName("sun.nio.cs.ext.ExtendedCharsets");
                                    return (CharsetProvider)epc.newInstance();
                                } catch (ClassNotFoundException x) {
                                    // Extended charsets not available
                                    // (charsets.jar not present)
                                } catch (InstantiationException |
                                         IllegalAccessException x) {
                                  throw new Error(x);
                                }
                                return null;
                            }
                        });
        }
    }

    private static Charset lookupExtendedCharset(String charsetName) {
        CharsetProvider ecp = ExtendedProviderHolder.extendedProvider;
        return (ecp != null) ? ecp.charsetForName(charsetName) : null;
    }

    private static Charset lookup(String charsetName) {
        if (charsetName == null)
            throw new IllegalArgumentException("Null charset name");
        Object[] a;
        if ((a = cache1) != null && charsetName.equals(a[0]))
            return (Charset)a[1];
        // We expect most programs to use one Charset repeatedly.
        // We convey a hint to this effect to the VM by putting the
        // level 1 cache miss code in a separate method.
        return lookup2(charsetName);
    }

    private static Charset lookup2(String charsetName) {
        Object[] a;
        if ((a = cache2) != null && charsetName.equals(a[0])) {
            cache2 = cache1;
            cache1 = a;
            return (Charset)a[1];
        }
        Charset cs;
        if ((cs = standardProvider.charsetForName(charsetName)) != null ||
            (cs = lookupExtendedCharset(charsetName))           != null ||
            (cs = lookupViaProviders(charsetName))              != null)
        {
            cache(charsetName, cs);
            return cs;
        }

        /* Only need to check the name if we didn't find a charset for it */
        checkName(charsetName);
        return null;
    }

    /**
     * Tells whether the named charset is supported.
     *
     * <p>
     *  指示命名的字符集是否受支持。
     * 
     * 
     * @param  charsetName
     *         The name of the requested charset; may be either
     *         a canonical name or an alias
     *
     * @return  <tt>true</tt> if, and only if, support for the named charset
     *          is available in the current Java virtual machine
     *
     * @throws IllegalCharsetNameException
     *         If the given charset name is illegal
     *
     * @throws  IllegalArgumentException
     *          If the given <tt>charsetName</tt> is null
     */
    public static boolean isSupported(String charsetName) {
        return (lookup(charsetName) != null);
    }

    /**
     * Returns a charset object for the named charset.
     *
     * <p>
     * 返回指定的charset的charset对象。
     * 
     * 
     * @param  charsetName
     *         The name of the requested charset; may be either
     *         a canonical name or an alias
     *
     * @return  A charset object for the named charset
     *
     * @throws  IllegalCharsetNameException
     *          If the given charset name is illegal
     *
     * @throws  IllegalArgumentException
     *          If the given <tt>charsetName</tt> is null
     *
     * @throws  UnsupportedCharsetException
     *          If no support for the named charset is available
     *          in this instance of the Java virtual machine
     */
    public static Charset forName(String charsetName) {
        Charset cs = lookup(charsetName);
        if (cs != null)
            return cs;
        throw new UnsupportedCharsetException(charsetName);
    }

    // Fold charsets from the given iterator into the given map, ignoring
    // charsets whose names already have entries in the map.
    //
    private static void put(Iterator<Charset> i, Map<String,Charset> m) {
        while (i.hasNext()) {
            Charset cs = i.next();
            if (!m.containsKey(cs.name()))
                m.put(cs.name(), cs);
        }
    }

    /**
     * Constructs a sorted map from canonical charset names to charset objects.
     *
     * <p> The map returned by this method will have one entry for each charset
     * for which support is available in the current Java virtual machine.  If
     * two or more supported charsets have the same canonical name then the
     * resulting map will contain just one of them; which one it will contain
     * is not specified. </p>
     *
     * <p> The invocation of this method, and the subsequent use of the
     * resulting map, may cause time-consuming disk or network I/O operations
     * to occur.  This method is provided for applications that need to
     * enumerate all of the available charsets, for example to allow user
     * charset selection.  This method is not used by the {@link #forName
     * forName} method, which instead employs an efficient incremental lookup
     * algorithm.
     *
     * <p> This method may return different results at different times if new
     * charset providers are dynamically made available to the current Java
     * virtual machine.  In the absence of such changes, the charsets returned
     * by this method are exactly those that can be retrieved via the {@link
     * #forName forName} method.  </p>
     *
     * <p>
     *  构造从规范字符集名称到字符集对象的排序映射。
     * 
     *  <p>此方法返回的地图将为当前Java虚拟机中可用的每个字符集提供一个条目。如果两个或多个受支持的字符集具有相同的规范名称,则生成的地图将只包含其中一个;它将包含的一个没有指定。 </p>
     * 
     *  <p>调用此方法以及随后使用生成的映射可能会导致耗时的磁盘或网络I / O操作。此方法适用于需要枚举所有可用字符集的应用程序,例如允许选择用户字符集。
     *  {@link #forName forName}方法不使用此方法,而是使用高效的增量查找算法。
     * 
     *  <p>如果新的字符集提供程序动态变为可用于当前Java虚拟机,则此方法可能在不同时间返回不同的结果。
     * 在没有此类更改的情况下,此方法返回的字符集正是可以通过{@link #forName forName}方法检索的字符集。 </p>。
     * 
     * 
     * @return An immutable, case-insensitive map from canonical charset names
     *         to charset objects
     */
    public static SortedMap<String,Charset> availableCharsets() {
        return AccessController.doPrivileged(
            new PrivilegedAction<SortedMap<String,Charset>>() {
                public SortedMap<String,Charset> run() {
                    TreeMap<String,Charset> m =
                        new TreeMap<String,Charset>(
                            ASCIICaseInsensitiveComparator.CASE_INSENSITIVE_ORDER);
                    put(standardProvider.charsets(), m);
                    CharsetProvider ecp = ExtendedProviderHolder.extendedProvider;
                    if (ecp != null)
                        put(ecp.charsets(), m);
                    for (Iterator<CharsetProvider> i = providers(); i.hasNext();) {
                        CharsetProvider cp = i.next();
                        put(cp.charsets(), m);
                    }
                    return Collections.unmodifiableSortedMap(m);
                }
            });
    }

    private static volatile Charset defaultCharset;

    /**
     * Returns the default charset of this Java virtual machine.
     *
     * <p> The default charset is determined during virtual-machine startup and
     * typically depends upon the locale and charset of the underlying
     * operating system.
     *
     * <p>
     *  返回此Java虚拟机的默认字符集。
     * 
     *  <p>默认字符集是在虚拟机启动期间确定的,通常取决于底层操作系统的区域设置和字符集。
     * 
     * 
     * @return  A charset object for the default charset
     *
     * @since 1.5
     */
    public static Charset defaultCharset() {
        if (defaultCharset == null) {
            synchronized (Charset.class) {
                String csn = AccessController.doPrivileged(
                    new GetPropertyAction("file.encoding"));
                Charset cs = lookup(csn);
                if (cs != null)
                    defaultCharset = cs;
                else
                    defaultCharset = forName("UTF-8");
            }
        }
        return defaultCharset;
    }


    /* -- Instance fields and methods -- */

    private final String name;          // tickles a bug in oldjavac
    private final String[] aliases;     // tickles a bug in oldjavac
    private Set<String> aliasSet = null;

    /**
     * Initializes a new charset with the given canonical name and alias
     * set.
     *
     * <p>
     *  使用给定的规范名称和别名集来初始化新的字符集。
     * 
     * 
     * @param  canonicalName
     *         The canonical name of this charset
     *
     * @param  aliases
     *         An array of this charset's aliases, or null if it has no aliases
     *
     * @throws IllegalCharsetNameException
     *         If the canonical name or any of the aliases are illegal
     */
    protected Charset(String canonicalName, String[] aliases) {
        checkName(canonicalName);
        String[] as = (aliases == null) ? new String[0] : aliases;
        for (int i = 0; i < as.length; i++)
            checkName(as[i]);
        this.name = canonicalName;
        this.aliases = as;
    }

    /**
     * Returns this charset's canonical name.
     *
     * <p>
     *  返回此字符集的规范名称。
     * 
     * 
     * @return  The canonical name of this charset
     */
    public final String name() {
        return name;
    }

    /**
     * Returns a set containing this charset's aliases.
     *
     * <p>
     * 返回包含此字符集的别名的集合。
     * 
     * 
     * @return  An immutable set of this charset's aliases
     */
    public final Set<String> aliases() {
        if (aliasSet != null)
            return aliasSet;
        int n = aliases.length;
        HashSet<String> hs = new HashSet<String>(n);
        for (int i = 0; i < n; i++)
            hs.add(aliases[i]);
        aliasSet = Collections.unmodifiableSet(hs);
        return aliasSet;
    }

    /**
     * Returns this charset's human-readable name for the default locale.
     *
     * <p> The default implementation of this method simply returns this
     * charset's canonical name.  Concrete subclasses of this class may
     * override this method in order to provide a localized display name. </p>
     *
     * <p>
     *  返回默认语言环境的此字符集的人性化名称。
     * 
     *  <p>此方法的默认实现仅返回此字符集的规范名称。此类的具体子类可以覆盖此方法,以提供本地化的显示名称。 </p>
     * 
     * 
     * @return  The display name of this charset in the default locale
     */
    public String displayName() {
        return name;
    }

    /**
     * Tells whether or not this charset is registered in the <a
     * href="http://www.iana.org/assignments/character-sets">IANA Charset
     * Registry</a>.
     *
     * <p>
     *  指出此字符集是否已在<a href="http://www.iana.org/assignments/character-sets"> IANA字库集注册表</a>中注册。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this charset is known by its
     *          implementor to be registered with the IANA
     */
    public final boolean isRegistered() {
        return !name.startsWith("X-") && !name.startsWith("x-");
    }

    /**
     * Returns this charset's human-readable name for the given locale.
     *
     * <p> The default implementation of this method simply returns this
     * charset's canonical name.  Concrete subclasses of this class may
     * override this method in order to provide a localized display name. </p>
     *
     * <p>
     *  返回给定语言环境的这个字符集的人类可读的名称。
     * 
     *  <p>此方法的默认实现仅返回此字符集的规范名称。此类的具体子类可以覆盖此方法,以提供本地化的显示名称。 </p>
     * 
     * 
     * @param  locale
     *         The locale for which the display name is to be retrieved
     *
     * @return  The display name of this charset in the given locale
     */
    public String displayName(Locale locale) {
        return name;
    }

    /**
     * Tells whether or not this charset contains the given charset.
     *
     * <p> A charset <i>C</i> is said to <i>contain</i> a charset <i>D</i> if,
     * and only if, every character representable in <i>D</i> is also
     * representable in <i>C</i>.  If this relationship holds then it is
     * guaranteed that every string that can be encoded in <i>D</i> can also be
     * encoded in <i>C</i> without performing any replacements.
     *
     * <p> That <i>C</i> contains <i>D</i> does not imply that each character
     * representable in <i>C</i> by a particular byte sequence is represented
     * in <i>D</i> by the same byte sequence, although sometimes this is the
     * case.
     *
     * <p> Every charset contains itself.
     *
     * <p> This method computes an approximation of the containment relation:
     * If it returns <tt>true</tt> then the given charset is known to be
     * contained by this charset; if it returns <tt>false</tt>, however, then
     * it is not necessarily the case that the given charset is not contained
     * in this charset.
     *
     * <p>
     *  告诉这个字符集是否包含给定的字符集。
     * 
     *  <p>如果且仅当<D> </i>中可表示的每个字符,字符集<i> C </i>都包含</i>字符集< i>也可以在C中表示。
     * 如果这种关系成立,则保证可以在D中编码的每个字符串也可以在C中被编码,而不执行任何替换。
     * 
     *  &lt; p&gt; C&lt; i&gt;包含&lt; i&gt; D&lt; i&gt;并不意味着在特定字节序列的&lt; i&gt;中可表示的每个字符都表示在&lt; / i>由相同的字节序列,
     * 虽然有时是这种情况。
     * 
     *  <p>每个字符集都包含自身。
     * 
     * <p>此方法计算包含关系的近似值：如果返回<tt> true </tt>,则知道给定的字符集包含在此字符集中;如果它返回<tt> false </tt>,但是,并不一定是给定的字符集不包含在此字符集中的
     * 情况。
     * 
     * 
     * @param   cs
     *          The given charset
     *
     * @return  <tt>true</tt> if the given charset is contained in this charset
     */
    public abstract boolean contains(Charset cs);

    /**
     * Constructs a new decoder for this charset.
     *
     * <p>
     *  为此字符集构造一个新的解码器。
     * 
     * 
     * @return  A new decoder for this charset
     */
    public abstract CharsetDecoder newDecoder();

    /**
     * Constructs a new encoder for this charset.
     *
     * <p>
     *  为此字符集构造一个新的编码器。
     * 
     * 
     * @return  A new encoder for this charset
     *
     * @throws  UnsupportedOperationException
     *          If this charset does not support encoding
     */
    public abstract CharsetEncoder newEncoder();

    /**
     * Tells whether or not this charset supports encoding.
     *
     * <p> Nearly all charsets support encoding.  The primary exceptions are
     * special-purpose <i>auto-detect</i> charsets whose decoders can determine
     * which of several possible encoding schemes is in use by examining the
     * input byte sequence.  Such charsets do not support encoding because
     * there is no way to determine which encoding should be used on output.
     * Implementations of such charsets should override this method to return
     * <tt>false</tt>. </p>
     *
     * <p>
     *  告诉这个字符集是否支持编码。
     * 
     *  <p>几乎所有的字符集都支持编码。主要例外是特殊用途<i>自动检测</i>字符集,其解码器可以通过检查输入字节序列来确定正在使用几种可能的编码方案中的哪一种。
     * 这样的字符集不支持编码,因为没有办法确定应该在输出上使用哪个编码。此类字符集的实现应覆盖此方法以返回<tt> false </tt>。 </p>。
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this charset supports encoding
     */
    public boolean canEncode() {
        return true;
    }

    /**
     * Convenience method that decodes bytes in this charset into Unicode
     * characters.
     *
     * <p> An invocation of this method upon a charset <tt>cs</tt> returns the
     * same result as the expression
     *
     * <pre>
     *     cs.newDecoder()
     *       .onMalformedInput(CodingErrorAction.REPLACE)
     *       .onUnmappableCharacter(CodingErrorAction.REPLACE)
     *       .decode(bb); </pre>
     *
     * except that it is potentially more efficient because it can cache
     * decoders between successive invocations.
     *
     * <p> This method always replaces malformed-input and unmappable-character
     * sequences with this charset's default replacement byte array.  In order
     * to detect such sequences, use the {@link
     * CharsetDecoder#decode(java.nio.ByteBuffer)} method directly.  </p>
     *
     * <p>
     *  将此字符集中的字节解码为Unicode字符的便捷方法。
     * 
     *  <p>对字符集<tt> cs </tt>的此方法的调用返回与表达式
     * 
     * <pre>
     *  cs.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.
     * REPLACE).decode(bb); </pre>。
     * 
     *  除了它可能更有效,因为它可以在连续调用之间高速缓存解码器。
     * 
     * <p>此方法总是使用此字符集的默认替换字节数组替换格式不正确的输入和不可映射字符序列。
     * 为了检测这样的序列,直接使用{@link CharsetDecoder#decode(java.nio.ByteBuffer)}方法。 </p>。
     * 
     * 
     * @param  bb  The byte buffer to be decoded
     *
     * @return  A char buffer containing the decoded characters
     */
    public final CharBuffer decode(ByteBuffer bb) {
        try {
            return ThreadLocalCoders.decoderFor(this)
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE)
                .decode(bb);
        } catch (CharacterCodingException x) {
            throw new Error(x);         // Can't happen
        }
    }

    /**
     * Convenience method that encodes Unicode characters into bytes in this
     * charset.
     *
     * <p> An invocation of this method upon a charset <tt>cs</tt> returns the
     * same result as the expression
     *
     * <pre>
     *     cs.newEncoder()
     *       .onMalformedInput(CodingErrorAction.REPLACE)
     *       .onUnmappableCharacter(CodingErrorAction.REPLACE)
     *       .encode(bb); </pre>
     *
     * except that it is potentially more efficient because it can cache
     * encoders between successive invocations.
     *
     * <p> This method always replaces malformed-input and unmappable-character
     * sequences with this charset's default replacement string.  In order to
     * detect such sequences, use the {@link
     * CharsetEncoder#encode(java.nio.CharBuffer)} method directly.  </p>
     *
     * <p>
     *  在此字符集中将Unicode字符编码为字节的便捷方法。
     * 
     *  <p>对字符集<tt> cs </tt>的此方法的调用返回与表达式
     * 
     * <pre>
     *  cs.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.
     * REPLACE).encode(bb); </pre>。
     * 
     *  除了它可能更有效,因为它可以在连续调用之间缓存编码器。
     * 
     *  <p>此方法总是使用此字符集的默认替换字符串替换格式不正确的输入和不可映射字符序列。
     * 为了检测这样的序列,直接使用{@link CharsetEncoder#encode(java.nio.CharBuffer)}方法。 </p>。
     * 
     * 
     * @param  cb  The char buffer to be encoded
     *
     * @return  A byte buffer containing the encoded characters
     */
    public final ByteBuffer encode(CharBuffer cb) {
        try {
            return ThreadLocalCoders.encoderFor(this)
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE)
                .encode(cb);
        } catch (CharacterCodingException x) {
            throw new Error(x);         // Can't happen
        }
    }

    /**
     * Convenience method that encodes a string into bytes in this charset.
     *
     * <p> An invocation of this method upon a charset <tt>cs</tt> returns the
     * same result as the expression
     *
     * <pre>
     *     cs.encode(CharBuffer.wrap(s)); </pre>
     *
     * <p>
     *  在此字符集中将字符串编码为字节的便捷方法。
     * 
     *  <p>对字符集<tt> cs </tt>的此方法的调用返回与表达式
     * 
     * <pre>
     *  cs.encode(CharBuffer.wrap(s)); </pre>
     * 
     * 
     * @param  str  The string to be encoded
     *
     * @return  A byte buffer containing the encoded characters
     */
    public final ByteBuffer encode(String str) {
        return encode(CharBuffer.wrap(str));
    }

    /**
     * Compares this charset to another.
     *
     * <p> Charsets are ordered by their canonical names, without regard to
     * case. </p>
     *
     * <p>
     *  将此字符集与另一个比较。
     * 
     *  <p>字符集按照其规范名称排序,不考虑大小写。 </p>
     * 
     * 
     * @param  that
     *         The charset to which this charset is to be compared
     *
     * @return A negative integer, zero, or a positive integer as this charset
     *         is less than, equal to, or greater than the specified charset
     */
    public final int compareTo(Charset that) {
        return (name().compareToIgnoreCase(that.name()));
    }

    /**
     * Computes a hashcode for this charset.
     *
     * <p>
     *  计算此字符集的哈希码。
     * 
     * 
     * @return  An integer hashcode
     */
    public final int hashCode() {
        return name().hashCode();
    }

    /**
     * Tells whether or not this object is equal to another.
     *
     * <p> Two charsets are equal if, and only if, they have the same canonical
     * names.  A charset is never equal to any other type of object.  </p>
     *
     * <p>
     *  告诉这个对象是否等于另一个对象。
     * 
     *  <p>两个字符集是相等的,如果,只有,他们有相同的规范名称。字符集从不等于任何其他类型的对象。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this charset is equal to the
     *          given object
     */
    public final boolean equals(Object ob) {
        if (!(ob instanceof Charset))
            return false;
        if (this == ob)
            return true;
        return name.equals(((Charset)ob).name());
    }

    /**
     * Returns a string describing this charset.
     *
     * <p>
     * 
     * @return  A string describing this charset
     */
    public final String toString() {
        return name();
    }

}
