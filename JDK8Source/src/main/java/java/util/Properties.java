/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;

import sun.util.spi.XmlPropertiesProvider;

/**
 * The {@code Properties} class represents a persistent set of
 * properties. The {@code Properties} can be saved to a stream
 * or loaded from a stream. Each key and its corresponding value in
 * the property list is a string.
 * <p>
 * A property list can contain another property list as its
 * "defaults"; this second property list is searched if
 * the property key is not found in the original property list.
 * <p>
 * Because {@code Properties} inherits from {@code Hashtable}, the
 * {@code put} and {@code putAll} methods can be applied to a
 * {@code Properties} object.  Their use is strongly discouraged as they
 * allow the caller to insert entries whose keys or values are not
 * {@code Strings}.  The {@code setProperty} method should be used
 * instead.  If the {@code store} or {@code save} method is called
 * on a "compromised" {@code Properties} object that contains a
 * non-{@code String} key or value, the call will fail. Similarly,
 * the call to the {@code propertyNames} or {@code list} method
 * will fail if it is called on a "compromised" {@code Properties}
 * object that contains a non-{@code String} key.
 *
 * <p>
 * The {@link #load(java.io.Reader) load(Reader)} <tt>/</tt>
 * {@link #store(java.io.Writer, java.lang.String) store(Writer, String)}
 * methods load and store properties from and to a character based stream
 * in a simple line-oriented format specified below.
 *
 * The {@link #load(java.io.InputStream) load(InputStream)} <tt>/</tt>
 * {@link #store(java.io.OutputStream, java.lang.String) store(OutputStream, String)}
 * methods work the same way as the load(Reader)/store(Writer, String) pair, except
 * the input/output stream is encoded in ISO 8859-1 character encoding.
 * Characters that cannot be directly represented in this encoding can be written using
 * Unicode escapes as defined in section 3.3 of
 * <cite>The Java&trade; Language Specification</cite>;
 * only a single 'u' character is allowed in an escape
 * sequence. The native2ascii tool can be used to convert property files to and
 * from other character encodings.
 *
 * <p> The {@link #loadFromXML(InputStream)} and {@link
 * #storeToXML(OutputStream, String, String)} methods load and store properties
 * in a simple XML format.  By default the UTF-8 character encoding is used,
 * however a specific encoding may be specified if required. Implementations
 * are required to support UTF-8 and UTF-16 and may support other encodings.
 * An XML properties document has the following DOCTYPE declaration:
 *
 * <pre>
 * &lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
 * </pre>
 * Note that the system URI (http://java.sun.com/dtd/properties.dtd) is
 * <i>not</i> accessed when exporting or importing properties; it merely
 * serves as a string to uniquely identify the DTD, which is:
 * <pre>
 *    &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 *
 *    &lt;!-- DTD for properties --&gt;
 *
 *    &lt;!ELEMENT properties ( comment?, entry* ) &gt;
 *
 *    &lt;!ATTLIST properties version CDATA #FIXED "1.0"&gt;
 *
 *    &lt;!ELEMENT comment (#PCDATA) &gt;
 *
 *    &lt;!ELEMENT entry (#PCDATA) &gt;
 *
 *    &lt;!ATTLIST entry key CDATA #REQUIRED&gt;
 * </pre>
 *
 * <p>This class is thread-safe: multiple threads can share a single
 * <tt>Properties</tt> object without the need for external synchronization.
 *
 * <p>
 *  {@code属性}类表示一组持久的属性。 {@code属性}可以保存到流中或从流中加载。属性列表中的每个键及其对应的值是一个字符串。
 * <p>
 *  属性列表可以包含另一个属性列表作为其"默认值";如果在原始属性列表中找不到属性键,则搜索此第二属性列表。
 * <p>
 *  因为{@code属性}继承自{@code Hashtable},所以{@code put}和{@code putAll}方法可以应用于{@code Properties}对象。
 * 强烈建议不要使用它们,因为它们允许调用者插入其键或值不是{@code Strings}的条目。应该使用{@code setProperty}方法。
 * 如果在包含非 -  {@ code String}键或值的"受损"{@code属性}对象上调用{@code store}或{@code save}方法,调用将失败。
 * 同样,如果在包含非 -  {@ code String}键的"受影响的"{@code属性}对象上调用{@code propertyNames}或{@code list}方法,则会调用失败。
 * 
 * <p>
 *  {@link #load(java.io.Reader)load(Reader)} <tt> / </tt> {@link #store(java.io.Writer,java.lang.String)store(Writer,String) }
 * 方法以下面指定的简单的面向行的格式从字符流中加载和存储属性。
 * 
 * {@link #load(java.io.InputStream)load(InputStream)} <tt> / </tt> {@link #store(java.io.OutputStream,java.lang.String)store(OutputStream,String) }
 * 方法的工作方式与load(Reader)/ store(Writer,String)对相同,除了输入/输出流以ISO 8859-1字符编码进行编码。
 * 不能在此编码中直接表示的字符可以使用Unicode转义编写,如第<cite>节"Java&trade;语言规范</cite>;在转义序列中只允许单个"u"字符。
 *  native2ascii工具可以用于将属性文件转换为其他字符编码或从其他字符编码转换。
 * 
 *  <p> {@link #loadFromXML(InputStream)}和{@link #storeToXML(OutputStream,String,String)}方法以简单的XML格式加载和存
 * 储属性。
 * 默认情况下,使用UTF-8字符编码,但是如果需要,可以指定特定的编码。实现需要支持UTF-8和UTF-16,并且可以支持其他编码。 XML属性文档具有以下DOCTYPE声明：。
 * 
 * <pre>
 *  &lt;！DOCTYPE properties SYSTEM"http://java.sun.com/dtd/properties.dtd"&gt;
 * </pre>
 *  请注意,在导出或导入属性时,不会</i>访问系统URI(http://java.sun.com/dtd/properties.dtd);它只是作为一个字符串来唯一标识DTD,它是：
 * <pre>
 *  &lt;?xml version ="1.0"encoding ="UTF-8"?&gt;
 * 
 *  &lt;！ - 属性的DTD  - &gt;
 * 
 *  &lt;！ELEMENT属性(comment,entry *)&gt;
 * 
 * &lt;！ATTLIST属性版本CDATA #FIXED"1.0"&gt;
 * 
 *  &lt;！ELEMENT comment(#PCDATA)&gt;
 * 
 *  &lt;！ELEMENT entry(#PCDATA)&gt;
 * 
 *  &lt;！ATTLIST entry key CDATA#REQUIRED&gt;
 * </pre>
 * 
 *  <p>这个类是线程安全的：多个线程可以共享一个<tt> Properties </tt>对象,而不需要外部同步。
 * 
 * 
 * @see <a href="../../../technotes/tools/solaris/native2ascii.html">native2ascii tool for Solaris</a>
 * @see <a href="../../../technotes/tools/windows/native2ascii.html">native2ascii tool for Windows</a>
 *
 * @author  Arthur van Hoff
 * @author  Michael McCloskey
 * @author  Xueming Shen
 * @since   JDK1.0
 */
public
class Properties extends Hashtable<Object,Object> {
    /**
     * use serialVersionUID from JDK 1.1.X for interoperability
     * <p>
     *  使用JDK 1.1.X中的serialVersionUID实现互操作性
     * 
     */
     private static final long serialVersionUID = 4112578634029874840L;

    /**
     * A property list that contains default values for any keys not
     * found in this property list.
     *
     * <p>
     *  属性列表,包含此属性列表中未找到的任何键的默认值。
     * 
     * 
     * @serial
     */
    protected Properties defaults;

    /**
     * Creates an empty property list with no default values.
     * <p>
     *  创建一个没有默认值的空属性列表。
     * 
     */
    public Properties() {
        this(null);
    }

    /**
     * Creates an empty property list with the specified defaults.
     *
     * <p>
     *  创建具有指定默认值的空属性列表。
     * 
     * 
     * @param   defaults   the defaults.
     */
    public Properties(Properties defaults) {
        this.defaults = defaults;
    }

    /**
     * Calls the <tt>Hashtable</tt> method {@code put}. Provided for
     * parallelism with the <tt>getProperty</tt> method. Enforces use of
     * strings for property keys and values. The value returned is the
     * result of the <tt>Hashtable</tt> call to {@code put}.
     *
     * <p>
     *  调用<tt> Hashtable </tt>方法{@code put}。用于与<tt> getProperty </tt>方法并行。对属性键和值强制使用字符串。
     * 返回的值是<tt> Hashtable </tt>调用{@code put}的结果。
     * 
     * 
     * @param key the key to be placed into this property list.
     * @param value the value corresponding to <tt>key</tt>.
     * @return     the previous value of the specified key in this property
     *             list, or {@code null} if it did not have one.
     * @see #getProperty
     * @since    1.2
     */
    public synchronized Object setProperty(String key, String value) {
        return put(key, value);
    }


    /**
     * Reads a property list (key and element pairs) from the input
     * character stream in a simple line-oriented format.
     * <p>
     * Properties are processed in terms of lines. There are two
     * kinds of line, <i>natural lines</i> and <i>logical lines</i>.
     * A natural line is defined as a line of
     * characters that is terminated either by a set of line terminator
     * characters ({@code \n} or {@code \r} or {@code \r\n})
     * or by the end of the stream. A natural line may be either a blank line,
     * a comment line, or hold all or some of a key-element pair. A logical
     * line holds all the data of a key-element pair, which may be spread
     * out across several adjacent natural lines by escaping
     * the line terminator sequence with a backslash character
     * {@code \}.  Note that a comment line cannot be extended
     * in this manner; every natural line that is a comment must have
     * its own comment indicator, as described below. Lines are read from
     * input until the end of the stream is reached.
     *
     * <p>
     * A natural line that contains only white space characters is
     * considered blank and is ignored.  A comment line has an ASCII
     * {@code '#'} or {@code '!'} as its first non-white
     * space character; comment lines are also ignored and do not
     * encode key-element information.  In addition to line
     * terminators, this format considers the characters space
     * ({@code ' '}, {@code '\u005Cu0020'}), tab
     * ({@code '\t'}, {@code '\u005Cu0009'}), and form feed
     * ({@code '\f'}, {@code '\u005Cu000C'}) to be white
     * space.
     *
     * <p>
     * If a logical line is spread across several natural lines, the
     * backslash escaping the line terminator sequence, the line
     * terminator sequence, and any white space at the start of the
     * following line have no affect on the key or element values.
     * The remainder of the discussion of key and element parsing
     * (when loading) will assume all the characters constituting
     * the key and element appear on a single natural line after
     * line continuation characters have been removed.  Note that
     * it is <i>not</i> sufficient to only examine the character
     * preceding a line terminator sequence to decide if the line
     * terminator is escaped; there must be an odd number of
     * contiguous backslashes for the line terminator to be escaped.
     * Since the input is processed from left to right, a
     * non-zero even number of 2<i>n</i> contiguous backslashes
     * before a line terminator (or elsewhere) encodes <i>n</i>
     * backslashes after escape processing.
     *
     * <p>
     * The key contains all of the characters in the line starting
     * with the first non-white space character and up to, but not
     * including, the first unescaped {@code '='},
     * {@code ':'}, or white space character other than a line
     * terminator. All of these key termination characters may be
     * included in the key by escaping them with a preceding backslash
     * character; for example,<p>
     *
     * {@code \:\=}<p>
     *
     * would be the two-character key {@code ":="}.  Line
     * terminator characters can be included using {@code \r} and
     * {@code \n} escape sequences.  Any white space after the
     * key is skipped; if the first non-white space character after
     * the key is {@code '='} or {@code ':'}, then it is
     * ignored and any white space characters after it are also
     * skipped.  All remaining characters on the line become part of
     * the associated element string; if there are no remaining
     * characters, the element is the empty string
     * {@code ""}.  Once the raw character sequences
     * constituting the key and element are identified, escape
     * processing is performed as described above.
     *
     * <p>
     * As an example, each of the following three lines specifies the key
     * {@code "Truth"} and the associated element value
     * {@code "Beauty"}:
     * <pre>
     * Truth = Beauty
     *  Truth:Beauty
     * Truth                    :Beauty
     * </pre>
     * As another example, the following three lines specify a single
     * property:
     * <pre>
     * fruits                           apple, banana, pear, \
     *                                  cantaloupe, watermelon, \
     *                                  kiwi, mango
     * </pre>
     * The key is {@code "fruits"} and the associated element is:
     * <pre>"apple, banana, pear, cantaloupe, watermelon, kiwi, mango"</pre>
     * Note that a space appears before each {@code \} so that a space
     * will appear after each comma in the final result; the {@code \},
     * line terminator, and leading white space on the continuation line are
     * merely discarded and are <i>not</i> replaced by one or more other
     * characters.
     * <p>
     * As a third example, the line:
     * <pre>cheeses
     * </pre>
     * specifies that the key is {@code "cheeses"} and the associated
     * element is the empty string {@code ""}.
     * <p>
     * <a name="unicodeescapes"></a>
     * Characters in keys and elements can be represented in escape
     * sequences similar to those used for character and string literals
     * (see sections 3.3 and 3.10.6 of
     * <cite>The Java&trade; Language Specification</cite>).
     *
     * The differences from the character escape sequences and Unicode
     * escapes used for characters and strings are:
     *
     * <ul>
     * <li> Octal escapes are not recognized.
     *
     * <li> The character sequence {@code \b} does <i>not</i>
     * represent a backspace character.
     *
     * <li> The method does not treat a backslash character,
     * {@code \}, before a non-valid escape character as an
     * error; the backslash is silently dropped.  For example, in a
     * Java string the sequence {@code "\z"} would cause a
     * compile time error.  In contrast, this method silently drops
     * the backslash.  Therefore, this method treats the two character
     * sequence {@code "\b"} as equivalent to the single
     * character {@code 'b'}.
     *
     * <li> Escapes are not necessary for single and double quotes;
     * however, by the rule above, single and double quote characters
     * preceded by a backslash still yield single and double quote
     * characters, respectively.
     *
     * <li> Only a single 'u' character is allowed in a Unicode escape
     * sequence.
     *
     * </ul>
     * <p>
     * The specified stream remains open after this method returns.
     *
     * <p>
     *  以简单的面向行的格式从输入字符流读取属性列表(键和元素对)。
     * <p>
     * 属性按行处理。有两种类型的行,<i>自然行</i>和<i>逻辑行</i>。
     * 自然行被定义为由一组行终止符字符({@code \ n}或{@code \ r}或{@code \ r \ n})终止的一行字符或由流。自然线可以是空白线,注释线,或保持所有或一些关键元素对。
     * 逻辑行保存键元素对的所有数据,通过用反斜杠字符{@code \}转义行终止符序列,可以跨几个相邻的自然行分布。注意,注释行不能以这种方式扩展;每个自然线是一个注释必须有自己的注释指示符,如下所述。
     * 从输入读取行,直到到达流的结束。
     * 
     * <p>
     *  仅包含空格字符的自然行被视为空白,并被忽略。注释行具有ASCII {@code'#'}或{@code'！'}作为其第一个非空格字符;注释行也被忽略,并且不编码关键元素信息。
     * 除了行终止符之外,此格式还考虑字符空间({@code''},{@code'\ u005Cu0020'}),制表符({@code'\ t'},{@code'\ u005Cu0009'}) ,并将feed(
     * {@code'\ f'},{@code'\ u005Cu000C'})形成为空白。
     *  仅包含空格字符的自然行被视为空白,并被忽略。注释行具有ASCII {@code'#'}或{@code'！'}作为其第一个非空格字符;注释行也被忽略,并且不编码关键元素信息。
     * 
     * <p>
     * 如果逻辑行分散在多个自然行中,则转义行终止符序列,行终止符序列和下一行开始处的任何空格的反斜杠对键或元素值没有影响。
     * 关键字和元素解析(加载时)的剩余部分将假设构成键和元素的所有字符在删除行连续字符之后出现在一个自然的行上。
     * 注意,仅仅检查行终止符序列之前的字符以决定行终止符是否被转义并不是足够的;对于要转义的行终止符,必须有奇数个连续反斜杠。
     * 由于输入是从左到右处理的,在行终止符(或其他地方)之前的非零偶数的2n个连续反斜杠在转义处理之后编码n个反斜杠。
     * 
     * <p>
     *  键包含从第一个非空白字符开始,但不包括第一个非转义的{@code'='},{@code'：'}或空格字符的行中的所有字符而不是行终止符。
     * 所有这些关键终止字符可以通过用前面的反斜杠字符转义来包括在该键中;例如,<p>。
     * 
     *  {@code \：\ =} <p>
     * 
     * 将是双字符键{@code"：="}。可以使用{@code \ r}和{@code \ n}转义序列来包含行终止符字符。
     * 跳过键后的任何空格;如果键后面的第一个非空格字符是{@code'='}或{@code'：'},那么它将被忽略,并且其后的任何空格字符也将被跳过。
     * 行上的所有其余字符都成为相关联的元素字符串的一部分;如果没有剩余的字符,则该元素是空字符串{@code""}。一旦识别了构成密钥和元素的原始字符序列,则如上所述执行转义处理。
     * 
     * <p>
     *  作为示例,以下三行中的每一行指定键{@code"Truth"}和相关联的元素值{@code"Beauty"}：
     * <pre>
     *  真理：美丽真相：美丽真相：美丽
     * </pre>
     *  作为另一个示例,以下三行指定单个属性：
     * <pre>
     *  水果苹果,香蕉,梨,\哈密瓜,西瓜,\猕猴桃,芒果
     * </pre>
     *  关键是{@code"fruits"},相关联的元素是：<pre>"apple,banana,pear,cantaloupe,watermelon,kiwi,mango"注意每个{@code \}以便在
     * 最终结果中的每个逗号之后将出现一个空格;连续行上的{@code \},行终止符和前导空格仅被丢弃,并且不被一个或多个其他字符替代。
     * <p>
     *  作为第三个例子,行：<pre>奶酪
     * </pre>
     * 指定键是{@code"cheeses"},相关联的元素是空字符串{@code""}。
     * <p>
     *  <a name="unicodeescapes"> </a>键和元素中的字符可以用类似于用于字符和字符串文字的转义序列表示(请参见<cite> Java&trade;语言规范< / cite>)。
     * 
     *  与用于字符和字符串的字符转义序列和Unicode转义字符的区别是：
     * 
     * <ul>
     *  <li>无法识别八进制转义。
     * 
     *  <li>字符序列{@code \ b} </i>不</i>表示退格字符。
     * 
     *  <li>该方法不将反斜杠字符{@code \}视为无效的转义字符之前的错误;反斜杠被默默地丢弃。例如,在Java字符串中,序列{@code"\ z"}将导致编译时错误。
     * 相反,这种方法默默地丢弃反斜杠。因此,此方法将两个字符序列{@code"\ b"}视为等同于单个字符{@code'b'}。
     * 
     *  <li>单引号和双引号不需要转义;然而,通过上面的规则,单引号和双引号字符前面的反斜杠仍然分别产生单引号和双引号字符。
     * 
     *  <li>在Unicode转义序列中只允许使用单个"u"字符。
     * 
     * </ul>
     * <p>
     *  此方法返回后,指定的流保持打开状态。
     * 
     * 
     * @param   reader   the input character stream.
     * @throws  IOException  if an error occurred when reading from the
     *          input stream.
     * @throws  IllegalArgumentException if a malformed Unicode escape
     *          appears in the input.
     * @since   1.6
     */
    public synchronized void load(Reader reader) throws IOException {
        load0(new LineReader(reader));
    }

    /**
     * Reads a property list (key and element pairs) from the input
     * byte stream. The input stream is in a simple line-oriented
     * format as specified in
     * {@link #load(java.io.Reader) load(Reader)} and is assumed to use
     * the ISO 8859-1 character encoding; that is each byte is one Latin1
     * character. Characters not in Latin1, and certain special characters,
     * are represented in keys and elements using Unicode escapes as defined in
     * section 3.3 of
     * <cite>The Java&trade; Language Specification</cite>.
     * <p>
     * The specified stream remains open after this method returns.
     *
     * <p>
     * 从输入字节流读取属性列表(键和元素对)。
     * 输入流采用{@link #load(java.io.Reader)load(Reader)}中指定的简单的面向行的格式,并假设使用ISO 8859-1字符编码;即每个字节是一个Latin1字符。
     * 不在Latin1中的字符和某些特殊字符使用Unicode转义(在<cite> Java&trade;语言规范</cite>。
     * <p>
     *  此方法返回后,指定的流保持打开状态。
     * 
     * 
     * @param      inStream   the input stream.
     * @exception  IOException  if an error occurred when reading from the
     *             input stream.
     * @throws     IllegalArgumentException if the input stream contains a
     *             malformed Unicode escape sequence.
     * @since 1.2
     */
    public synchronized void load(InputStream inStream) throws IOException {
        load0(new LineReader(inStream));
    }

    private void load0 (LineReader lr) throws IOException {
        char[] convtBuf = new char[1024];
        int limit;
        int keyLen;
        int valueStart;
        char c;
        boolean hasSep;
        boolean precedingBackslash;

        while ((limit = lr.readLine()) >= 0) {
            c = 0;
            keyLen = 0;
            valueStart = limit;
            hasSep = false;

            //System.out.println("line=<" + new String(lineBuf, 0, limit) + ">");
            precedingBackslash = false;
            while (keyLen < limit) {
                c = lr.lineBuf[keyLen];
                //need check if escaped.
                if ((c == '=' ||  c == ':') && !precedingBackslash) {
                    valueStart = keyLen + 1;
                    hasSep = true;
                    break;
                } else if ((c == ' ' || c == '\t' ||  c == '\f') && !precedingBackslash) {
                    valueStart = keyLen + 1;
                    break;
                }
                if (c == '\\') {
                    precedingBackslash = !precedingBackslash;
                } else {
                    precedingBackslash = false;
                }
                keyLen++;
            }
            while (valueStart < limit) {
                c = lr.lineBuf[valueStart];
                if (c != ' ' && c != '\t' &&  c != '\f') {
                    if (!hasSep && (c == '=' ||  c == ':')) {
                        hasSep = true;
                    } else {
                        break;
                    }
                }
                valueStart++;
            }
            String key = loadConvert(lr.lineBuf, 0, keyLen, convtBuf);
            String value = loadConvert(lr.lineBuf, valueStart, limit - valueStart, convtBuf);
            put(key, value);
        }
    }

    /* Read in a "logical line" from an InputStream/Reader, skip all comment
     * and blank lines and filter out those leading whitespace characters
     * (\u0020, \u0009 and \u000c) from the beginning of a "natural line".
     * Method returns the char length of the "logical line" and stores
     * the line in "lineBuf".
     * <p>
     *  和空白行,并从"自然线"的开头过滤掉那些前导空白字符(\ u0020,\ u0009和\ u000c)。方法返回"逻辑行"的char长度并将行存储在"lineBuf"中。
     * 
     */
    class LineReader {
        public LineReader(InputStream inStream) {
            this.inStream = inStream;
            inByteBuf = new byte[8192];
        }

        public LineReader(Reader reader) {
            this.reader = reader;
            inCharBuf = new char[8192];
        }

        byte[] inByteBuf;
        char[] inCharBuf;
        char[] lineBuf = new char[1024];
        int inLimit = 0;
        int inOff = 0;
        InputStream inStream;
        Reader reader;

        int readLine() throws IOException {
            int len = 0;
            char c = 0;

            boolean skipWhiteSpace = true;
            boolean isCommentLine = false;
            boolean isNewLine = true;
            boolean appendedLineBegin = false;
            boolean precedingBackslash = false;
            boolean skipLF = false;

            while (true) {
                if (inOff >= inLimit) {
                    inLimit = (inStream==null)?reader.read(inCharBuf)
                                              :inStream.read(inByteBuf);
                    inOff = 0;
                    if (inLimit <= 0) {
                        if (len == 0 || isCommentLine) {
                            return -1;
                        }
                        if (precedingBackslash) {
                            len--;
                        }
                        return len;
                    }
                }
                if (inStream != null) {
                    //The line below is equivalent to calling a
                    //ISO8859-1 decoder.
                    c = (char) (0xff & inByteBuf[inOff++]);
                } else {
                    c = inCharBuf[inOff++];
                }
                if (skipLF) {
                    skipLF = false;
                    if (c == '\n') {
                        continue;
                    }
                }
                if (skipWhiteSpace) {
                    if (c == ' ' || c == '\t' || c == '\f') {
                        continue;
                    }
                    if (!appendedLineBegin && (c == '\r' || c == '\n')) {
                        continue;
                    }
                    skipWhiteSpace = false;
                    appendedLineBegin = false;
                }
                if (isNewLine) {
                    isNewLine = false;
                    if (c == '#' || c == '!') {
                        isCommentLine = true;
                        continue;
                    }
                }

                if (c != '\n' && c != '\r') {
                    lineBuf[len++] = c;
                    if (len == lineBuf.length) {
                        int newLength = lineBuf.length * 2;
                        if (newLength < 0) {
                            newLength = Integer.MAX_VALUE;
                        }
                        char[] buf = new char[newLength];
                        System.arraycopy(lineBuf, 0, buf, 0, lineBuf.length);
                        lineBuf = buf;
                    }
                    //flip the preceding backslash flag
                    if (c == '\\') {
                        precedingBackslash = !precedingBackslash;
                    } else {
                        precedingBackslash = false;
                    }
                }
                else {
                    // reached EOL
                    if (isCommentLine || len == 0) {
                        isCommentLine = false;
                        isNewLine = true;
                        skipWhiteSpace = true;
                        len = 0;
                        continue;
                    }
                    if (inOff >= inLimit) {
                        inLimit = (inStream==null)
                                  ?reader.read(inCharBuf)
                                  :inStream.read(inByteBuf);
                        inOff = 0;
                        if (inLimit <= 0) {
                            if (precedingBackslash) {
                                len--;
                            }
                            return len;
                        }
                    }
                    if (precedingBackslash) {
                        len -= 1;
                        //skip the leading whitespace characters in following line
                        skipWhiteSpace = true;
                        appendedLineBegin = true;
                        precedingBackslash = false;
                        if (c == '\r') {
                            skipLF = true;
                        }
                    } else {
                        return len;
                    }
                }
            }
        }
    }

    /*
     * Converts encoded &#92;uxxxx to unicode chars
     * and changes special saved chars to their original forms
     * <p>
     *  将编码的\ uxxxx转换为unicode字符,并将特殊保存的字符更改为其原始形式
     * 
     */
    private String loadConvert (char[] in, int off, int len, char[] convtBuf) {
        if (convtBuf.length < len) {
            int newLen = len * 2;
            if (newLen < 0) {
                newLen = Integer.MAX_VALUE;
            }
            convtBuf = new char[newLen];
        }
        char aChar;
        char[] out = convtBuf;
        int outLen = 0;
        int end = off + len;

        while (off < end) {
            aChar = in[off++];
            if (aChar == '\\') {
                aChar = in[off++];
                if(aChar == 'u') {
                    // Read the xxxx
                    int value=0;
                    for (int i=0; i<4; i++) {
                        aChar = in[off++];
                        switch (aChar) {
                          case '0': case '1': case '2': case '3': case '4':
                          case '5': case '6': case '7': case '8': case '9':
                             value = (value << 4) + aChar - '0';
                             break;
                          case 'a': case 'b': case 'c':
                          case 'd': case 'e': case 'f':
                             value = (value << 4) + 10 + aChar - 'a';
                             break;
                          case 'A': case 'B': case 'C':
                          case 'D': case 'E': case 'F':
                             value = (value << 4) + 10 + aChar - 'A';
                             break;
                          default:
                              throw new IllegalArgumentException(
                                           "Malformed \\uxxxx encoding.");
                        }
                     }
                    out[outLen++] = (char)value;
                } else {
                    if (aChar == 't') aChar = '\t';
                    else if (aChar == 'r') aChar = '\r';
                    else if (aChar == 'n') aChar = '\n';
                    else if (aChar == 'f') aChar = '\f';
                    out[outLen++] = aChar;
                }
            } else {
                out[outLen++] = aChar;
            }
        }
        return new String (out, 0, outLen);
    }

    /*
     * Converts unicodes to encoded &#92;uxxxx and escapes
     * special characters with a preceding slash
     * <p>
     *  将unicode转换为编码的\ uxxxx,并使用前面的斜线转义特殊字符
     * 
     */
    private String saveConvert(String theString,
                               boolean escapeSpace,
                               boolean escapeUnicode) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuffer outBuffer = new StringBuffer(bufLen);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\'); outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch(aChar) {
                case ' ':
                    if (x == 0 || escapeSpace)
                        outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                case '=': // Fall through
                case ':': // Fall through
                case '#': // Fall through
                case '!':
                    outBuffer.append('\\'); outBuffer.append(aChar);
                    break;
                default:
                    if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode ) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >>  8) & 0xF));
                        outBuffer.append(toHex((aChar >>  4) & 0xF));
                        outBuffer.append(toHex( aChar        & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    private static void writeComments(BufferedWriter bw, String comments)
        throws IOException {
        bw.write("#");
        int len = comments.length();
        int current = 0;
        int last = 0;
        char[] uu = new char[6];
        uu[0] = '\\';
        uu[1] = 'u';
        while (current < len) {
            char c = comments.charAt(current);
            if (c > '\u00ff' || c == '\n' || c == '\r') {
                if (last != current)
                    bw.write(comments.substring(last, current));
                if (c > '\u00ff') {
                    uu[2] = toHex((c >> 12) & 0xf);
                    uu[3] = toHex((c >>  8) & 0xf);
                    uu[4] = toHex((c >>  4) & 0xf);
                    uu[5] = toHex( c        & 0xf);
                    bw.write(new String(uu));
                } else {
                    bw.newLine();
                    if (c == '\r' &&
                        current != len - 1 &&
                        comments.charAt(current + 1) == '\n') {
                        current++;
                    }
                    if (current == len - 1 ||
                        (comments.charAt(current + 1) != '#' &&
                        comments.charAt(current + 1) != '!'))
                        bw.write("#");
                }
                last = current + 1;
            }
            current++;
        }
        if (last != current)
            bw.write(comments.substring(last, current));
        bw.newLine();
    }

    /**
     * Calls the {@code store(OutputStream out, String comments)} method
     * and suppresses IOExceptions that were thrown.
     *
     * <p>
     *  调用{@code store(OutputStream out,String comments)}方法并抑制抛出的IOExceptions。
     * 
     * 
     * @deprecated This method does not throw an IOException if an I/O error
     * occurs while saving the property list.  The preferred way to save a
     * properties list is via the {@code store(OutputStream out,
     * String comments)} method or the
     * {@code storeToXML(OutputStream os, String comment)} method.
     *
     * @param   out      an output stream.
     * @param   comments   a description of the property list.
     * @exception  ClassCastException  if this {@code Properties} object
     *             contains any keys or values that are not
     *             {@code Strings}.
     */
    @Deprecated
    public void save(OutputStream out, String comments)  {
        try {
            store(out, comments);
        } catch (IOException e) {
        }
    }

    /**
     * Writes this property list (key and element pairs) in this
     * {@code Properties} table to the output character stream in a
     * format suitable for using the {@link #load(java.io.Reader) load(Reader)}
     * method.
     * <p>
     * Properties from the defaults table of this {@code Properties}
     * table (if any) are <i>not</i> written out by this method.
     * <p>
     * If the comments argument is not null, then an ASCII {@code #}
     * character, the comments string, and a line separator are first written
     * to the output stream. Thus, the {@code comments} can serve as an
     * identifying comment. Any one of a line feed ('\n'), a carriage
     * return ('\r'), or a carriage return followed immediately by a line feed
     * in comments is replaced by a line separator generated by the {@code Writer}
     * and if the next character in comments is not character {@code #} or
     * character {@code !} then an ASCII {@code #} is written out
     * after that line separator.
     * <p>
     * Next, a comment line is always written, consisting of an ASCII
     * {@code #} character, the current date and time (as if produced
     * by the {@code toString} method of {@code Date} for the
     * current time), and a line separator as generated by the {@code Writer}.
     * <p>
     * Then every entry in this {@code Properties} table is
     * written out, one per line. For each entry the key string is
     * written, then an ASCII {@code =}, then the associated
     * element string. For the key, all space characters are
     * written with a preceding {@code \} character.  For the
     * element, leading space characters, but not embedded or trailing
     * space characters, are written with a preceding {@code \}
     * character. The key and element characters {@code #},
     * {@code !}, {@code =}, and {@code :} are written
     * with a preceding backslash to ensure that they are properly loaded.
     * <p>
     * After the entries have been written, the output stream is flushed.
     * The output stream remains open after this method returns.
     * <p>
     *
     * <p>
     *  使用适合使用{@link #load(java.io.Reader)load(Reader)}方法的格式将此{@code Properties}表中的此属性列表(键和元素对)写入输出字符流。
     * <p>
     *  此{@code属性}表(如果有)的默认表格的属性不是</i>由此方法写出。
     * <p>
     * 如果注释参数不为空,那么首先将ASCII {@code#}字符,注释字符串和行分隔符写入输出流。因此,{@code comments}可以作为标识注释。
     * 在换行符('\ n'),回车符('\ r')或回车符后紧接着换行符的任何一个换行符被替换为由{@code Writer}和如果注释中的下一个字符不是字符{@code#}或字符{@code！},则在该行分
     * 隔符之后写出一个ASCII {@code#}。
     * 如果注释参数不为空,那么首先将ASCII {@code#}字符,注释字符串和行分隔符写入输出流。因此,{@code comments}可以作为标识注释。
     * <p>
     *  接下来,始终会写入一条注释行,其中包含一个ASCII {@code#}字符,当前日期和时间(如同当前时间的{@code Date}的{@code toString}方法生成的)一个由{@code Writer}
     * 生成的行分隔符。
     * <p>
     *  然后,这个{@code属性}表中的每个条目都会写出,每行一个。对于每个条目,写入密钥字符串,然后是ASCII {@code =},然后是相关联的元素字符串。
     * 对于键,所有空格字符都使用前面的{@code \}字符写入。对于元素,前导空格字符,但不是嵌入或尾随空格字符,使用前面的{@code \}字符写入。
     * 键和元素字符{@code#},{@code！},{@code =}和{@code：}用前面的反斜杠写入,以确保它们正确加载。
     * <p>
     * 在写入条目之后,输出流被刷新。此方法返回后,输出流保持打开状态。
     * <p>
     * 
     * 
     * @param   writer      an output character stream writer.
     * @param   comments   a description of the property list.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this {@code Properties} object
     *             contains any keys or values that are not {@code Strings}.
     * @exception  NullPointerException  if {@code writer} is null.
     * @since 1.6
     */
    public void store(Writer writer, String comments)
        throws IOException
    {
        store0((writer instanceof BufferedWriter)?(BufferedWriter)writer
                                                 : new BufferedWriter(writer),
               comments,
               false);
    }

    /**
     * Writes this property list (key and element pairs) in this
     * {@code Properties} table to the output stream in a format suitable
     * for loading into a {@code Properties} table using the
     * {@link #load(InputStream) load(InputStream)} method.
     * <p>
     * Properties from the defaults table of this {@code Properties}
     * table (if any) are <i>not</i> written out by this method.
     * <p>
     * This method outputs the comments, properties keys and values in
     * the same format as specified in
     * {@link #store(java.io.Writer, java.lang.String) store(Writer)},
     * with the following differences:
     * <ul>
     * <li>The stream is written using the ISO 8859-1 character encoding.
     *
     * <li>Characters not in Latin-1 in the comments are written as
     * {@code \u005Cu}<i>xxxx</i> for their appropriate unicode
     * hexadecimal value <i>xxxx</i>.
     *
     * <li>Characters less than {@code \u005Cu0020} and characters greater
     * than {@code \u005Cu007E} in property keys or values are written
     * as {@code \u005Cu}<i>xxxx</i> for the appropriate hexadecimal
     * value <i>xxxx</i>.
     * </ul>
     * <p>
     * After the entries have been written, the output stream is flushed.
     * The output stream remains open after this method returns.
     * <p>
     * <p>
     *  将此{@code Properties}表中的此属性列表(键和元素对)以适合于使用{@link #load(InputStream)加载(InputStream)加载到{@code属性}表中的格式写入
     * 输出流, } 方法。
     * <p>
     *  此{@code属性}表(如果有)的默认表格的属性不是</i>由此方法写出。
     * <p>
     *  此方法以与{@link #store(java.io.Writer,java.lang.String)store(Writer)}中指定的格式相同的格式输出注释,属性键和值,具有以下差异：
     * <ul>
     *  <li>流使用ISO 8859-1字符编码写入。
     * 
     *  <li>注释中不是拉丁语-1的字符以{@code \ u005Cu} <i> xxxx </i>格式表示为适当的Unicode字符十六进制值<i> xxxx </i>。
     * 
     *  <li>在属性键或值中小于{@code \ u005Cu0020}的字符和大于{@code \ u005Cu007E}的字符将写为{@code \ u005Cu} <i> xxxx </i>,以获得适
     * 当的十六进制值< i> xxxx </i>。
     * </ul>
     * <p>
     *  在写入条目之后,输出流被刷新。此方法返回后,输出流保持打开状态。
     * <p>
     * 
     * @param   out      an output stream.
     * @param   comments   a description of the property list.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this {@code Properties} object
     *             contains any keys or values that are not {@code Strings}.
     * @exception  NullPointerException  if {@code out} is null.
     * @since 1.2
     */
    public void store(OutputStream out, String comments)
        throws IOException
    {
        store0(new BufferedWriter(new OutputStreamWriter(out, "8859_1")),
               comments,
               true);
    }

    private void store0(BufferedWriter bw, String comments, boolean escUnicode)
        throws IOException
    {
        if (comments != null) {
            writeComments(bw, comments);
        }
        bw.write("#" + new Date().toString());
        bw.newLine();
        synchronized (this) {
            for (Enumeration<?> e = keys(); e.hasMoreElements();) {
                String key = (String)e.nextElement();
                String val = (String)get(key);
                key = saveConvert(key, true, escUnicode);
                /* No need to escape embedded and trailing spaces for value, hence
                 * pass false to flag.
                 * <p>
                 *  传递false到标志。
                 * 
                 */
                val = saveConvert(val, false, escUnicode);
                bw.write(key + "=" + val);
                bw.newLine();
            }
        }
        bw.flush();
    }

    /**
     * Loads all of the properties represented by the XML document on the
     * specified input stream into this properties table.
     *
     * <p>The XML document must have the following DOCTYPE declaration:
     * <pre>
     * &lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
     * </pre>
     * Furthermore, the document must satisfy the properties DTD described
     * above.
     *
     * <p> An implementation is required to read XML documents that use the
     * "{@code UTF-8}" or "{@code UTF-16}" encoding. An implementation may
     * support additional encodings.
     *
     * <p>The specified stream is closed after this method returns.
     *
     * <p>
     *  将指定输入流上的XML文档所表示的所有属性加载到此属性表中。
     * 
     *  <p> XML文档必须具有以下DOCTYPE声明：
     * <pre>
     * &lt;！DOCTYPE properties SYSTEM"http://java.sun.com/dtd/properties.dtd"&gt;
     * </pre>
     *  此外,文件必须满足上述性质DTD。
     * 
     *  <p>需要使用实现来读取使用"{@code UTF-8}"或"{@code UTF-16}"编码的XML文档。实现可以支持额外的编码。
     * 
     *  <p>此方法返回后,指定的流将关闭。
     * 
     * 
     * @param in the input stream from which to read the XML document.
     * @throws IOException if reading from the specified input stream
     *         results in an <tt>IOException</tt>.
     * @throws java.io.UnsupportedEncodingException if the document's encoding
     *         declaration can be read and it specifies an encoding that is not
     *         supported
     * @throws InvalidPropertiesFormatException Data on input stream does not
     *         constitute a valid XML document with the mandated document type.
     * @throws NullPointerException if {@code in} is null.
     * @see    #storeToXML(OutputStream, String, String)
     * @see    <a href="http://www.w3.org/TR/REC-xml/#charencoding">Character
     *         Encoding in Entities</a>
     * @since 1.5
     */
    public synchronized void loadFromXML(InputStream in)
        throws IOException, InvalidPropertiesFormatException
    {
        XmlSupport.load(this, Objects.requireNonNull(in));
        in.close();
    }

    /**
     * Emits an XML document representing all of the properties contained
     * in this table.
     *
     * <p> An invocation of this method of the form <tt>props.storeToXML(os,
     * comment)</tt> behaves in exactly the same way as the invocation
     * <tt>props.storeToXML(os, comment, "UTF-8");</tt>.
     *
     * <p>
     *  发出表示此表中包含的所有属性的XML文档。
     * 
     *  <p>调用此方法的形式<tt> props.storeToXML(os,comment)</tt>的行为与调用<tt> props.storeToXML(os,comment,"UTF-8 "); </tt>
     * 。
     * 
     * 
     * @param os the output stream on which to emit the XML document.
     * @param comment a description of the property list, or {@code null}
     *        if no comment is desired.
     * @throws IOException if writing to the specified output stream
     *         results in an <tt>IOException</tt>.
     * @throws NullPointerException if {@code os} is null.
     * @throws ClassCastException  if this {@code Properties} object
     *         contains any keys or values that are not
     *         {@code Strings}.
     * @see    #loadFromXML(InputStream)
     * @since 1.5
     */
    public void storeToXML(OutputStream os, String comment)
        throws IOException
    {
        storeToXML(os, comment, "UTF-8");
    }

    /**
     * Emits an XML document representing all of the properties contained
     * in this table, using the specified encoding.
     *
     * <p>The XML document will have the following DOCTYPE declaration:
     * <pre>
     * &lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
     * </pre>
     *
     * <p>If the specified comment is {@code null} then no comment
     * will be stored in the document.
     *
     * <p> An implementation is required to support writing of XML documents
     * that use the "{@code UTF-8}" or "{@code UTF-16}" encoding. An
     * implementation may support additional encodings.
     *
     * <p>The specified stream remains open after this method returns.
     *
     * <p>
     *  使用指定的编码发出表示此表中包含的所有属性的XML文档。
     * 
     *  <p> XML文档将具有以下DOCTYPE声明：
     * <pre>
     *  &lt;！DOCTYPE properties SYSTEM"http://java.sun.com/dtd/properties.dtd"&gt;
     * </pre>
     * 
     *  <p>如果指定的注释是{@code null},则不会在文档中存储注释。
     * 
     *  <p>需要一个实现来支持使用"{@code UTF-8}"或"{@code UTF-16}"编码的XML文档的写入。实现可以支持额外的编码。
     * 
     *  <p>此方法返回后,指定的流保持打开状态。
     * 
     * 
     * @param os        the output stream on which to emit the XML document.
     * @param comment   a description of the property list, or {@code null}
     *                  if no comment is desired.
     * @param  encoding the name of a supported
     *                  <a href="../lang/package-summary.html#charenc">
     *                  character encoding</a>
     *
     * @throws IOException if writing to the specified output stream
     *         results in an <tt>IOException</tt>.
     * @throws java.io.UnsupportedEncodingException if the encoding is not
     *         supported by the implementation.
     * @throws NullPointerException if {@code os} is {@code null},
     *         or if {@code encoding} is {@code null}.
     * @throws ClassCastException  if this {@code Properties} object
     *         contains any keys or values that are not
     *         {@code Strings}.
     * @see    #loadFromXML(InputStream)
     * @see    <a href="http://www.w3.org/TR/REC-xml/#charencoding">Character
     *         Encoding in Entities</a>
     * @since 1.5
     */
    public void storeToXML(OutputStream os, String comment, String encoding)
        throws IOException
    {
        XmlSupport.save(this, Objects.requireNonNull(os), comment,
                        Objects.requireNonNull(encoding));
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns
     * {@code null} if the property is not found.
     *
     * <p>
     * 在此属性列表中使用指定的键搜索属性。如果在此属性列表中找不到键,则递归检查默认属性列表及其默认值。如果未找到该属性,该方法将返回{@code null}。
     * 
     * 
     * @param   key   the property key.
     * @return  the value in this property list with the specified key value.
     * @see     #setProperty
     * @see     #defaults
     */
    public String getProperty(String key) {
        Object oval = super.get(key);
        String sval = (oval instanceof String) ? (String)oval : null;
        return ((sval == null) && (defaults != null)) ? defaults.getProperty(key) : sval;
    }

    /**
     * Searches for the property with the specified key in this property list.
     * If the key is not found in this property list, the default property list,
     * and its defaults, recursively, are then checked. The method returns the
     * default value argument if the property is not found.
     *
     * <p>
     *  在此属性列表中使用指定的键搜索属性。如果在此属性列表中找不到键,则递归检查默认属性列表及其默认值。如果未找到属性,该方法将返回默认值参数。
     * 
     * 
     * @param   key            the hashtable key.
     * @param   defaultValue   a default value.
     *
     * @return  the value in this property list with the specified key value.
     * @see     #setProperty
     * @see     #defaults
     */
    public String getProperty(String key, String defaultValue) {
        String val = getProperty(key);
        return (val == null) ? defaultValue : val;
    }

    /**
     * Returns an enumeration of all the keys in this property list,
     * including distinct keys in the default property list if a key
     * of the same name has not already been found from the main
     * properties list.
     *
     * <p>
     *  返回此属性列表中所有键的枚举,如果尚未在主属性列表中找到相同名称的键,则包括默认属性列表中的不同键。
     * 
     * 
     * @return  an enumeration of all the keys in this property list, including
     *          the keys in the default property list.
     * @throws  ClassCastException if any key in this property list
     *          is not a string.
     * @see     java.util.Enumeration
     * @see     java.util.Properties#defaults
     * @see     #stringPropertyNames
     */
    public Enumeration<?> propertyNames() {
        Hashtable<String,Object> h = new Hashtable<>();
        enumerate(h);
        return h.keys();
    }

    /**
     * Returns a set of keys in this property list where
     * the key and its corresponding value are strings,
     * including distinct keys in the default property list if a key
     * of the same name has not already been found from the main
     * properties list.  Properties whose key or value is not
     * of type <tt>String</tt> are omitted.
     * <p>
     * The returned set is not backed by the <tt>Properties</tt> object.
     * Changes to this <tt>Properties</tt> are not reflected in the set,
     * or vice versa.
     *
     * <p>
     *  返回此属性列表中的一组键,其中键及其对应的值是字符串,如果尚未从主属性列表中找到相同名称的键,则在默认属性列表中包括不同键。省略键或值不是<tt> String </tt>类型的属性。
     * <p>
     *  返回的集合不受<tt>属性</tt>对象支持。对此<tt>属性</tt>的更改不会反映在集合中,反之亦然。
     * 
     * 
     * @return  a set of keys in this property list where
     *          the key and its corresponding value are strings,
     *          including the keys in the default property list.
     * @see     java.util.Properties#defaults
     * @since   1.6
     */
    public Set<String> stringPropertyNames() {
        Hashtable<String, String> h = new Hashtable<>();
        enumerateStringProperties(h);
        return h.keySet();
    }

    /**
     * Prints this property list out to the specified output stream.
     * This method is useful for debugging.
     *
     * <p>
     *  将此属性列表打印到指定的输出流。此方法对于调试非常有用。
     * 
     * 
     * @param   out   an output stream.
     * @throws  ClassCastException if any key in this property list
     *          is not a string.
     */
    public void list(PrintStream out) {
        out.println("-- listing properties --");
        Hashtable<String,Object> h = new Hashtable<>();
        enumerate(h);
        for (Enumeration<String> e = h.keys() ; e.hasMoreElements() ;) {
            String key = e.nextElement();
            String val = (String)h.get(key);
            if (val.length() > 40) {
                val = val.substring(0, 37) + "...";
            }
            out.println(key + "=" + val);
        }
    }

    /**
     * Prints this property list out to the specified output stream.
     * This method is useful for debugging.
     *
     * <p>
     *  将此属性列表打印到指定的输出流。此方法对于调试非常有用。
     * 
     * 
     * @param   out   an output stream.
     * @throws  ClassCastException if any key in this property list
     *          is not a string.
     * @since   JDK1.1
     */
    /*
     * Rather than use an anonymous inner class to share common code, this
     * method is duplicated in order to ensure that a non-1.1 compiler can
     * compile this file.
     * <p>
     * 不是使用匿名内部类来共享公共代码,而是复制此方法以确保非1.1编译器可以编译此文件。
     * 
     */
    public void list(PrintWriter out) {
        out.println("-- listing properties --");
        Hashtable<String,Object> h = new Hashtable<>();
        enumerate(h);
        for (Enumeration<String> e = h.keys() ; e.hasMoreElements() ;) {
            String key = e.nextElement();
            String val = (String)h.get(key);
            if (val.length() > 40) {
                val = val.substring(0, 37) + "...";
            }
            out.println(key + "=" + val);
        }
    }

    /**
     * Enumerates all key/value pairs in the specified hashtable.
     * <p>
     *  枚举指定散列表中的所有键/值对。
     * 
     * 
     * @param h the hashtable
     * @throws ClassCastException if any of the property keys
     *         is not of String type.
     */
    private synchronized void enumerate(Hashtable<String,Object> h) {
        if (defaults != null) {
            defaults.enumerate(h);
        }
        for (Enumeration<?> e = keys() ; e.hasMoreElements() ;) {
            String key = (String)e.nextElement();
            h.put(key, get(key));
        }
    }

    /**
     * Enumerates all key/value pairs in the specified hashtable
     * and omits the property if the key or value is not a string.
     * <p>
     *  枚举指定散列表中的所有键/值对,如果键或值不是字符串,则省略属性。
     * 
     * 
     * @param h the hashtable
     */
    private synchronized void enumerateStringProperties(Hashtable<String, String> h) {
        if (defaults != null) {
            defaults.enumerateStringProperties(h);
        }
        for (Enumeration<?> e = keys() ; e.hasMoreElements() ;) {
            Object k = e.nextElement();
            Object v = get(k);
            if (k instanceof String && v instanceof String) {
                h.put((String) k, (String) v);
            }
        }
    }

    /**
     * Convert a nibble to a hex character
     * <p>
     *  将半字节转换为十六进制字符
     * 
     * 
     * @param   nibble  the nibble to convert.
     */
    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit = {
        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };

    /**
     * Supporting class for loading/storing properties in XML format.
     *
     * <p> The {@code load} and {@code store} methods defined here delegate to a
     * system-wide {@code XmlPropertiesProvider}. On first invocation of either
     * method then the system-wide provider is located as follows: </p>
     *
     * <ol>
     *   <li> If the system property {@code sun.util.spi.XmlPropertiesProvider}
     *   is defined then it is taken to be the full-qualified name of a concrete
     *   provider class. The class is loaded with the system class loader as the
     *   initiating loader. If it cannot be loaded or instantiated using a zero
     *   argument constructor then an unspecified error is thrown. </li>
     *
     *   <li> If the system property is not defined then the service-provider
     *   loading facility defined by the {@link ServiceLoader} class is used to
     *   locate a provider with the system class loader as the initiating
     *   loader and {@code sun.util.spi.XmlPropertiesProvider} as the service
     *   type. If this process fails then an unspecified error is thrown. If
     *   there is more than one service provider installed then it is
     *   not specified as to which provider will be used. </li>
     *
     *   <li> If the provider is not found by the above means then a system
     *   default provider will be instantiated and used. </li>
     * </ol>
     * <p>
     *  支持以XML格式加载/存储属性的类。
     * 
     *  <p>这里定义的{@code load}和{@code store}方法委托给系统范围的{@code XmlPropertiesProvider}。
     * 在首次调用任一方法时,系统范围的提供程序位于以下位置：</p>。
     * 
     * <ol>
     *  <li>如果系统属性{@code sun.util.spi.XmlPropertiesProvider}已定义,那么它将被视为具体提供程序类的完全限定名称。类用系统类加载器作为启动加载器加载。
     * 如果不能使用零参数构造函数加载或实例化,那么会抛出未指定的错误。 </li>。
     */
    private static class XmlSupport {

        private static XmlPropertiesProvider loadProviderFromProperty(ClassLoader cl) {
            String cn = System.getProperty("sun.util.spi.XmlPropertiesProvider");
            if (cn == null)
                return null;
            try {
                Class<?> c = Class.forName(cn, true, cl);
                return (XmlPropertiesProvider)c.newInstance();
            } catch (ClassNotFoundException |
                     IllegalAccessException |
                     InstantiationException x) {
                throw new ServiceConfigurationError(null, x);
            }
        }

        private static XmlPropertiesProvider loadProviderAsService(ClassLoader cl) {
            Iterator<XmlPropertiesProvider> iterator =
                 ServiceLoader.load(XmlPropertiesProvider.class, cl).iterator();
            return iterator.hasNext() ? iterator.next() : null;
        }

        private static XmlPropertiesProvider loadProvider() {
            return AccessController.doPrivileged(
                new PrivilegedAction<XmlPropertiesProvider>() {
                    public XmlPropertiesProvider run() {
                        ClassLoader cl = ClassLoader.getSystemClassLoader();
                        XmlPropertiesProvider provider = loadProviderFromProperty(cl);
                        if (provider != null)
                            return provider;
                        provider = loadProviderAsService(cl);
                        if (provider != null)
                            return provider;
                        return new jdk.internal.util.xml.BasicXmlPropertiesProvider();
                }});
        }

        private static final XmlPropertiesProvider PROVIDER = loadProvider();

        static void load(Properties props, InputStream in)
            throws IOException, InvalidPropertiesFormatException
        {
            PROVIDER.load(props, in);
        }

        static void save(Properties props, OutputStream os, String comment,
                         String encoding)
            throws IOException
        {
            PROVIDER.store(props, os, comment, encoding);
        }
    }
}
