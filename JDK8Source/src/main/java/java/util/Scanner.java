/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.regex.*;
import java.io.*;
import java.math.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.text.*;
import java.util.Locale;

import sun.misc.LRUCache;

/**
 * A simple text scanner which can parse primitive types and strings using
 * regular expressions.
 *
 * <p>A <code>Scanner</code> breaks its input into tokens using a
 * delimiter pattern, which by default matches whitespace. The resulting
 * tokens may then be converted into values of different types using the
 * various <tt>next</tt> methods.
 *
 * <p>For example, this code allows a user to read a number from
 * <tt>System.in</tt>:
 * <blockquote><pre>{@code
 *     Scanner sc = new Scanner(System.in);
 *     int i = sc.nextInt();
 * }</pre></blockquote>
 *
 * <p>As another example, this code allows <code>long</code> types to be
 * assigned from entries in a file <code>myNumbers</code>:
 * <blockquote><pre>{@code
 *      Scanner sc = new Scanner(new File("myNumbers"));
 *      while (sc.hasNextLong()) {
 *          long aLong = sc.nextLong();
 *      }
 * }</pre></blockquote>
 *
 * <p>The scanner can also use delimiters other than whitespace. This
 * example reads several items in from a string:
 * <blockquote><pre>{@code
 *     String input = "1 fish 2 fish red fish blue fish";
 *     Scanner s = new Scanner(input).useDelimiter("\\s*fish\\s*");
 *     System.out.println(s.nextInt());
 *     System.out.println(s.nextInt());
 *     System.out.println(s.next());
 *     System.out.println(s.next());
 *     s.close();
 * }</pre></blockquote>
 * <p>
 * prints the following output:
 * <blockquote><pre>{@code
 *     1
 *     2
 *     red
 *     blue
 * }</pre></blockquote>
 *
 * <p>The same output can be generated with this code, which uses a regular
 * expression to parse all four tokens at once:
 * <blockquote><pre>{@code
 *     String input = "1 fish 2 fish red fish blue fish";
 *     Scanner s = new Scanner(input);
 *     s.findInLine("(\\d+) fish (\\d+) fish (\\w+) fish (\\w+)");
 *     MatchResult result = s.match();
 *     for (int i=1; i<=result.groupCount(); i++)
 *         System.out.println(result.group(i));
 *     s.close();
 * }</pre></blockquote>
 *
 * <p>The <a name="default-delimiter">default whitespace delimiter</a> used
 * by a scanner is as recognized by {@link java.lang.Character}.{@link
 * java.lang.Character#isWhitespace(char) isWhitespace}. The {@link #reset}
 * method will reset the value of the scanner's delimiter to the default
 * whitespace delimiter regardless of whether it was previously changed.
 *
 * <p>A scanning operation may block waiting for input.
 *
 * <p>The {@link #next} and {@link #hasNext} methods and their
 * primitive-type companion methods (such as {@link #nextInt} and
 * {@link #hasNextInt}) first skip any input that matches the delimiter
 * pattern, and then attempt to return the next token. Both <tt>hasNext</tt>
 * and <tt>next</tt> methods may block waiting for further input.  Whether a
 * <tt>hasNext</tt> method blocks has no connection to whether or not its
 * associated <tt>next</tt> method will block.
 *
 * <p> The {@link #findInLine}, {@link #findWithinHorizon}, and {@link #skip}
 * methods operate independently of the delimiter pattern. These methods will
 * attempt to match the specified pattern with no regard to delimiters in the
 * input and thus can be used in special circumstances where delimiters are
 * not relevant. These methods may block waiting for more input.
 *
 * <p>When a scanner throws an {@link InputMismatchException}, the scanner
 * will not pass the token that caused the exception, so that it may be
 * retrieved or skipped via some other method.
 *
 * <p>Depending upon the type of delimiting pattern, empty tokens may be
 * returned. For example, the pattern <tt>"\\s+"</tt> will return no empty
 * tokens since it matches multiple instances of the delimiter. The delimiting
 * pattern <tt>"\\s"</tt> could return empty tokens since it only passes one
 * space at a time.
 *
 * <p> A scanner can read text from any object which implements the {@link
 * java.lang.Readable} interface.  If an invocation of the underlying
 * readable's {@link java.lang.Readable#read} method throws an {@link
 * java.io.IOException} then the scanner assumes that the end of the input
 * has been reached.  The most recent <tt>IOException</tt> thrown by the
 * underlying readable can be retrieved via the {@link #ioException} method.
 *
 * <p>When a <code>Scanner</code> is closed, it will close its input source
 * if the source implements the {@link java.io.Closeable} interface.
 *
 * <p>A <code>Scanner</code> is not safe for multithreaded use without
 * external synchronization.
 *
 * <p>Unless otherwise mentioned, passing a <code>null</code> parameter into
 * any method of a <code>Scanner</code> will cause a
 * <code>NullPointerException</code> to be thrown.
 *
 * <p>A scanner will default to interpreting numbers as decimal unless a
 * different radix has been set by using the {@link #useRadix} method. The
 * {@link #reset} method will reset the value of the scanner's radix to
 * <code>10</code> regardless of whether it was previously changed.
 *
 * <h3> <a name="localized-numbers">Localized numbers</a> </h3>
 *
 * <p> An instance of this class is capable of scanning numbers in the standard
 * formats as well as in the formats of the scanner's locale. A scanner's
 * <a name="initial-locale">initial locale </a>is the value returned by the {@link
 * java.util.Locale#getDefault(Locale.Category)
 * Locale.getDefault(Locale.Category.FORMAT)} method; it may be changed via the {@link
 * #useLocale} method. The {@link #reset} method will reset the value of the
 * scanner's locale to the initial locale regardless of whether it was
 * previously changed.
 *
 * <p>The localized formats are defined in terms of the following parameters,
 * which for a particular locale are taken from that locale's {@link
 * java.text.DecimalFormat DecimalFormat} object, <tt>df</tt>, and its and
 * {@link java.text.DecimalFormatSymbols DecimalFormatSymbols} object,
 * <tt>dfs</tt>.
 *
 * <blockquote><dl>
 *     <dt><i>LocalGroupSeparator&nbsp;&nbsp;</i>
 *         <dd>The character used to separate thousands groups,
 *         <i>i.e.,</i>&nbsp;<tt>dfs.</tt>{@link
 *         java.text.DecimalFormatSymbols#getGroupingSeparator
 *         getGroupingSeparator()}
 *     <dt><i>LocalDecimalSeparator&nbsp;&nbsp;</i>
 *         <dd>The character used for the decimal point,
 *     <i>i.e.,</i>&nbsp;<tt>dfs.</tt>{@link
 *     java.text.DecimalFormatSymbols#getDecimalSeparator
 *     getDecimalSeparator()}
 *     <dt><i>LocalPositivePrefix&nbsp;&nbsp;</i>
 *         <dd>The string that appears before a positive number (may
 *         be empty), <i>i.e.,</i>&nbsp;<tt>df.</tt>{@link
 *         java.text.DecimalFormat#getPositivePrefix
 *         getPositivePrefix()}
 *     <dt><i>LocalPositiveSuffix&nbsp;&nbsp;</i>
 *         <dd>The string that appears after a positive number (may be
 *         empty), <i>i.e.,</i>&nbsp;<tt>df.</tt>{@link
 *         java.text.DecimalFormat#getPositiveSuffix
 *         getPositiveSuffix()}
 *     <dt><i>LocalNegativePrefix&nbsp;&nbsp;</i>
 *         <dd>The string that appears before a negative number (may
 *         be empty), <i>i.e.,</i>&nbsp;<tt>df.</tt>{@link
 *         java.text.DecimalFormat#getNegativePrefix
 *         getNegativePrefix()}
 *     <dt><i>LocalNegativeSuffix&nbsp;&nbsp;</i>
 *         <dd>The string that appears after a negative number (may be
 *         empty), <i>i.e.,</i>&nbsp;<tt>df.</tt>{@link
 *     java.text.DecimalFormat#getNegativeSuffix
 *     getNegativeSuffix()}
 *     <dt><i>LocalNaN&nbsp;&nbsp;</i>
 *         <dd>The string that represents not-a-number for
 *         floating-point values,
 *         <i>i.e.,</i>&nbsp;<tt>dfs.</tt>{@link
 *         java.text.DecimalFormatSymbols#getNaN
 *         getNaN()}
 *     <dt><i>LocalInfinity&nbsp;&nbsp;</i>
 *         <dd>The string that represents infinity for floating-point
 *         values, <i>i.e.,</i>&nbsp;<tt>dfs.</tt>{@link
 *         java.text.DecimalFormatSymbols#getInfinity
 *         getInfinity()}
 * </dl></blockquote>
 *
 * <h4> <a name="number-syntax">Number syntax</a> </h4>
 *
 * <p> The strings that can be parsed as numbers by an instance of this class
 * are specified in terms of the following regular-expression grammar, where
 * Rmax is the highest digit in the radix being used (for example, Rmax is 9 in base 10).
 *
 * <dl>
 *   <dt><i>NonAsciiDigit</i>:
 *       <dd>A non-ASCII character c for which
 *            {@link java.lang.Character#isDigit Character.isDigit}<tt>(c)</tt>
 *                        returns&nbsp;true
 *
 *   <dt><i>Non0Digit</i>:
 *       <dd><tt>[1-</tt><i>Rmax</i><tt>] | </tt><i>NonASCIIDigit</i>
 *
 *   <dt><i>Digit</i>:
 *       <dd><tt>[0-</tt><i>Rmax</i><tt>] | </tt><i>NonASCIIDigit</i>
 *
 *   <dt><i>GroupedNumeral</i>:
 *       <dd><tt>(&nbsp;</tt><i>Non0Digit</i>
 *                   <i>Digit</i><tt>?
 *                   </tt><i>Digit</i><tt>?</tt>
 *       <dd>&nbsp;&nbsp;&nbsp;&nbsp;<tt>(&nbsp;</tt><i>LocalGroupSeparator</i>
 *                         <i>Digit</i>
 *                         <i>Digit</i>
 *                         <i>Digit</i><tt> )+ )</tt>
 *
 *   <dt><i>Numeral</i>:
 *       <dd><tt>( ( </tt><i>Digit</i><tt>+ )
 *               | </tt><i>GroupedNumeral</i><tt> )</tt>
 *
 *   <dt><a name="Integer-regex"><i>Integer</i>:</a>
 *       <dd><tt>( [-+]? ( </tt><i>Numeral</i><tt>
 *                               ) )</tt>
 *       <dd><tt>| </tt><i>LocalPositivePrefix</i> <i>Numeral</i>
 *                      <i>LocalPositiveSuffix</i>
 *       <dd><tt>| </tt><i>LocalNegativePrefix</i> <i>Numeral</i>
 *                 <i>LocalNegativeSuffix</i>
 *
 *   <dt><i>DecimalNumeral</i>:
 *       <dd><i>Numeral</i>
 *       <dd><tt>| </tt><i>Numeral</i>
 *                 <i>LocalDecimalSeparator</i>
 *                 <i>Digit</i><tt>*</tt>
 *       <dd><tt>| </tt><i>LocalDecimalSeparator</i>
 *                 <i>Digit</i><tt>+</tt>
 *
 *   <dt><i>Exponent</i>:
 *       <dd><tt>( [eE] [+-]? </tt><i>Digit</i><tt>+ )</tt>
 *
 *   <dt><a name="Decimal-regex"><i>Decimal</i>:</a>
 *       <dd><tt>( [-+]? </tt><i>DecimalNumeral</i>
 *                         <i>Exponent</i><tt>? )</tt>
 *       <dd><tt>| </tt><i>LocalPositivePrefix</i>
 *                 <i>DecimalNumeral</i>
 *                 <i>LocalPositiveSuffix</i>
 *                 <i>Exponent</i><tt>?</tt>
 *       <dd><tt>| </tt><i>LocalNegativePrefix</i>
 *                 <i>DecimalNumeral</i>
 *                 <i>LocalNegativeSuffix</i>
 *                 <i>Exponent</i><tt>?</tt>
 *
 *   <dt><i>HexFloat</i>:
 *       <dd><tt>[-+]? 0[xX][0-9a-fA-F]*\.[0-9a-fA-F]+
 *                 ([pP][-+]?[0-9]+)?</tt>
 *
 *   <dt><i>NonNumber</i>:
 *       <dd><tt>NaN
 *                          | </tt><i>LocalNan</i><tt>
 *                          | Infinity
 *                          | </tt><i>LocalInfinity</i>
 *
 *   <dt><i>SignedNonNumber</i>:
 *       <dd><tt>( [-+]? </tt><i>NonNumber</i><tt> )</tt>
 *       <dd><tt>| </tt><i>LocalPositivePrefix</i>
 *                 <i>NonNumber</i>
 *                 <i>LocalPositiveSuffix</i>
 *       <dd><tt>| </tt><i>LocalNegativePrefix</i>
 *                 <i>NonNumber</i>
 *                 <i>LocalNegativeSuffix</i>
 *
 *   <dt><a name="Float-regex"><i>Float</i></a>:
 *       <dd><i>Decimal</i>
 *           <tt>| </tt><i>HexFloat</i>
 *           <tt>| </tt><i>SignedNonNumber</i>
 *
 * </dl>
 * <p>Whitespace is not significant in the above regular expressions.
 *
 * <p>
 *  一个简单的文本扫描器,可以使用正则表达式解析基本类型和字符串
 * 
 *  <p> <code> Scanner </code>使用定界符模式将其输入分成符号,默认情况下匹配空格。然后可以使用各种<tt>下一个</tt>将所得到的符号转换为不同类型的值方法
 * 
 *  <p>例如,此代码允许用户从<tt> Systemin </tt>：<blockquote> <pre> {@ code Scanner sc = new Scanner(Systemin); int i = scnextInt(); }
 *  </pre> </blockquote>。
 * 
 * <p>作为另一个例子,此代码允许从文件中的条目<code> myNumbers </code>中分配<code> long </code>类型：<blockquote> <pre> {@ code Scanner sc = new Scanner (new File("myNumbers")); while(schasNextLong()){long aLong = scnextLong(); }
 * } </pre> </blockquote>。
 * 
 *  <p>扫描器也可以使用除空格之外的分隔符。
 * 此示例从字符串中读取几个项目：<blockquote> <pre> {@ code String input ="1 fish 2 fish red fish blue fish"; Scanner s = new Scanner(input)useDelimiter("\\\\ s * fish \\\\ s *"); Systemoutprintln(snextInt()); Systemoutprintln(snextInt()); systemoutprintln(snext()); Systemoutprintln(snext()); sclose(); }
 *  </pre> </blockquote>。
 *  <p>扫描器也可以使用除空格之外的分隔符。
 * <p>
 *  打印以下输出：<blockquote> <pre> {@ code 1 2 red blue} </pre> </blockquote>
 * 
 * <p>使用此代码可以生成相同的输出,它使用正则表达式一次解析所有四个标记：<blockquote> <pre> {@ code String input ="1 fish 2 fish red fish blue fish";扫描仪s =新扫描仪(输入); sfindInLine("(\\\\ d +)fish(\\\\ d +)fish(\\\\ w +)fish(\\\\ w +)"); MatchResult result = smatch(); for(int i = 1; i <= resultgroupCount(); i ++)Systemoutprintln(resultgroup(i)); sclose(); } </pre>
 *  </blockquote>。
 * 
 *  <p>扫描程序使用的<a name=\"default-delimiter\">默认空格分隔符</a>由{@link javalangCharacter} {@ link javalangCharacter}
 * 识别。
 * {@ link javalangCharacter#isWhitespace(char)isWhitespace} {@link# reset}方法将把扫描器的分隔符的值重置为默认的空白分隔符,而不管它
 * 以前是否被更改过。
 * 
 * <p>扫描操作可能会阻止等待输入
 * 
 *  <p> {@link #next}和{@link #hasNext}方法及其原始类型随播广告方法(例如{@link #nextInt}和{@link #hasNextInt})首先跳过任何与分隔符相符
 * 的输入模式,然后尝试返回下一个令牌<tt> hasNext </tt>和<tt>下一个</tt>方法可能会阻塞等待进一步的输入<tt> hasNext </tt>到其相关的<tt>下一个</tt>方法是
 * 否会阻塞。
 * 
 * <p> {@link #findInLine},{@link #findWithinHorizo​​n}和{@link #skip}方法独立于分隔符模式运行这些方法将尝试匹配指定的模式,不考虑输入中的分
 * 隔符,因此可以在分隔符不相关的特殊情况下使用这些方法可能阻止等待更多的输入。
 * 
 *  <p>当扫描程序抛出{@link InputMismatchException}时,扫描程序将不会传递导致异常的令牌,因此可能会通过其他方法检索或跳过此异常
 * 
 * <p>根据分隔模式的类型,可能会返回空令牌。
 * 例如,模式<tt>"\\\\ s +"</tt>将不返回空令牌,因为它匹配分隔符的多个实例定界模式< tt>"\\\\ s"</tt>可能返回空令牌,因为它一次只传递一个空格。
 * 
 *  <p>扫描器可以从任何实现{@link javalangReadable}接口的对象读取文本如果对底层可读的{@link javalangReadable#read}方法的调用引发{@link javaioIOException}
 * ,则扫描器假定结束输入已达到最底层可读的<tt> IOException </tt>可以通过{@link #ioException}方法检索。
 * 
 * <p>当<code> Scanner </code>关闭时,如果源代码实现{@link javaioCloseable}接口,它将关闭其输入源
 * 
 *  <p> <code>扫描程序</code>对于没有外部同步的多线程使用是不安全的
 * 
 *  <p>除非另有说明,否则将<code> null </code>参数传递给<code> Scanner </code>的任何方法都会导致抛出<code> NullPointerException </code>
 * 。
 * 
 *  <p>除非使用{@link #useRadix}方法设置了不同的基数,否则扫描仪将默认将数字解释为十进制。
 * {@link #reset}方法会将扫描仪的基数值重置为<code> 10 </code>,无论它以前是否被更改过。
 * 
 *  <h3> <a name=\"localized-numbers\">本地化号码</a> </h3>
 * 
 * <p>此类的实例能够扫描标准格式的数字以及扫描程序的区域设置的格式扫描程序的<a name=\"initial-locale\">初始语言环境</a>是返回的值{@link javautilLocale#getDefault(LocaleCategory)LocalegetDefault(LocaleCategoryFORMAT)}
 * 方法;它可以通过{@link #useLocale}方法更改。
 * {@link #reset}方法会将扫描程序的区域设置的值重置为初始区域设置,而不管之前是否更改过。
 * 
 * <p>本地化格式是根据以下参数定义的,对于特定区域设置,从该区域设置的{@link javatextDecimalFormat DecimalFormat}对象<tt> df </tt>中获取,以及它的
 * 和{@link javatextDecimalFormatSymbols DecimalFormatSymbols }对象,<tt> dfs </tt>。
 * 
 * <blockquote> <dl> <dt> <i> LocalGroupSeparator&nbsp; </i> <dd>用于分隔数千个群组的字符,<i>,</i>&nbsp; <tt> dfs </tt>
 *  {@link javatextDecimalFormatSymbols#getGroupingSeparator getGroupingSeparator()} <dt> <i> LocalDecim
 * alSeparator&nbsp; </i> <dd>用于小数点的字符,即<i> </i>&nbsp; <tt> </t> {@ link javatextDecimalFormatSymbols#getDecimalSeparator getDecimalSeparator()}
 *  <dt> <i> LocalPositivePrefix&nbsp; </i> <dd>出现在正数之前的字符串(可能为空),<i> </i> <tt> df </tt> {@ link javatextDecimalFormat#getPositivePrefix getPositivePrefix()}
 *  <dt> <i> LocalPositiveSuffix&nbsp; </i> <dd>在正数后出现的字符串可以为空),<i> ie </i>&nbsp; <tt> df </tt> {@ link javatextDecimalFormat#getPositiveSuffix getPositiveSuffix()}
 *  <dt> <i> LocalNegativePrefix&nbsp;&nbsp; </i> <dd>在负数之前出现的字符串(可能为空),<i>,</i> tt> df </tt> {@ link javatextDecimalFormat#getNegativePrefix getNegativePrefix()}
 *  <dt> <i> LocalNegativeSuffix&nbsp;&nbsp; </i> <dd>出现在负数(可能为空) > </i> </i> </i> </i> </i> </i> </i> </i>
 *  </i> <dt </i> <dt </tt> {@ link javatextDecimalFormat#getNegativeSuffix getNegativeSuffix()浮点值的浮点数,<i> </i> <tt> dfs </tt> {@ link javatextDecimalFormatSymbols#getNaN getNaN()}
 *  <dt> <i> LocalInfinity& i> <dd>表示浮点值的无穷大的字符串,<i> </i> <tt> dfs </tt> {@ link javatextDecimalFormatSymbols#getInfinity getInfinity()}
 *  </dl> </blockquote>。
 * 
 * <h4> <a name=\"number-syntax\">数字语法</a> </h4>
 * 
 *  <p>可以通过此类的实例解析为数字的字符串按照以下正则表达式语法来指定,其中Rmax是所使用的基数中的最高位(例如,Rmax是基数10中的9 )
 * 
 * <dl>
 *  <dt> <i> NonAsciiDigit </i>：<dd>一个非ASCII字符c,其中{@link javalangCharacter#isDigit CharacterisDigit} <tt>
 * (c)</tt>返回true。
 * 
 *  <dt> <i> Non0Digit </i>：<dd> <tt> [1  -  </tt> Rmax <tt>] | </tt> <i> NonASCIIDigit </i>
 * 
 *  <dt> <i>数字</i>：<dd> <tt> [0- </tt> <Rmax <tt>] | </tt> <i> NonASCIIDigit </i>
 * 
 * <dt> <i> GroupedNumeral </i>：<dd> <tt>(&nbsp; </tt> <i> Non0Digit </i> <i> Digit </i> <tt>?</tt> i> D
 * igit </i> <tt>?</tt> <dd>&nbsp;&nbsp;&nbsp;&nbsp; <tt>(&nbsp; </tt> <i> LocalGroupSeparator </i> i> <i>
 *  Digit </i> <i> Digit </i> <tt>)+)</tt>。
 * 
 *  <dt> <i>数字</i>：<dd> <tt>((</tt> <i> Digit </i> <tt> +)| </tt> <i> GroupedNumeral </i> <tt>)</tt>
 * 
 *  <dt> <a name=\"Integer-regex\"> <i>整数</i>：</a> <dd> <tt>([ -  +]?(</tt> <i>数字</i > <tt>))</tt> <dd> 
 * <tt> | </tt> <i> LocalPositivePrefix </i> <i>数字</i> <i> LocalPositiveSuffix </i> <dd> <tt> | </tt> <i>
 *  LocalNegativePrefix </i> <i>数字</i> <i> LocalNegativeSuffix </i>。
 * 
 *  <dt> <i>十进制数</i>：<dd> <i>数字</i> <dd> <tt> </tt> <i>数字</i> <i> LocalDecimalSeparator </i> <i>数字</i> <tt>
 *  * </tt> <dd> <tt> </tt> <i> LocalDecimalSeparator </i> <i>数字</i> <tt> + </tt>。
 * 
 * <dt> <i>指数</i>：<dd> <tt>([eE] [+  - ]?</tt> <i> Digit </i> <tt> +)</tt>
 * 
 *  <dt> <a name=\"Decimal-regex\"> <i>小数</i>：</a> <dd> <tt>([ -  +]?</tt> <i> <i>指数</i> <tt>?)</tt> <dd>
 *  <tt> | </tt> <i> LocalPositivePrefix </i> <i> DecimalNumeral </i> </i> LocalPositiveSuffix </i> <i> 
 * Exponent </i> <tt>?</tt> <dd> <tt> | </tt> <i> LocalNegativePrefix </i> <i> DecimalNumeral </i> <i> L
 * ocalNegativeSuffix </i> <i> Exponent </i> <tt>?</tt>。
 * 
 *  <dt> <i> HexFloat </i>：<dd> <tt> [ -  +]? 0 [x-x] [0-9a-fA-F] * \\ [0-9a-fA-F] +([pP] [ -  +]?[0-9] 
 * +)。
 * 
 *  <dt> <i> NonNumber </i>：<dd> <tt> NaN | </tt> <i> LocalNan </i> <tt> |无限| </tt> <i> LocalInfinity </i>
 * 。
 * 
 * <dt> <i> SignedNonNumber </i>：<dd> <tt>([ -  +]?</tt> <i> NonNumber </i> <tt>)</tt> <dd> <tt> | </tt>
 *  <i> LocalPositivePrefix </i> <i> NonNumber </i> <i> LocalPositiveSuffix </i> <dd> <tt> | </tt> <i> L
 * ocalNegativePrefix </i> <i> NonNumber </i> <i> LocalNegativeSuffix </i>。
 * 
 *  <dt> <a name=\"Float-regex\"> <i>浮动</i> </a>：<dd> <i>小数点</i> <tt> | </tt> <i> HexFloat </i> <tt> | </tt>
 *  <i> SignedNonNumber </i>。
 * 
 * </dl>
 *  <p>空格在上述正则表达式中不重要
 * 
 * 
 * @since   1.5
 */
public final class Scanner implements Iterator<String>, Closeable {

    // Internal buffer used to hold input
    private CharBuffer buf;

    // Size of internal character buffer
    private static final int BUFFER_SIZE = 1024; // change to 1024;

    // The index into the buffer currently held by the Scanner
    private int position;

    // Internal matcher used for finding delimiters
    private Matcher matcher;

    // Pattern used to delimit tokens
    private Pattern delimPattern;

    // Pattern found in last hasNext operation
    private Pattern hasNextPattern;

    // Position after last hasNext operation
    private int hasNextPosition;

    // Result after last hasNext operation
    private String hasNextResult;

    // The input source
    private Readable source;

    // Boolean is true if source is done
    private boolean sourceClosed = false;

    // Boolean indicating more input is required
    private boolean needInput = false;

    // Boolean indicating if a delim has been skipped this operation
    private boolean skipped = false;

    // A store of a position that the scanner may fall back to
    private int savedScannerPosition = -1;

    // A cache of the last primitive type scanned
    private Object typeCache = null;

    // Boolean indicating if a match result is available
    private boolean matchValid = false;

    // Boolean indicating if this scanner has been closed
    private boolean closed = false;

    // The current radix used by this scanner
    private int radix = 10;

    // The default radix for this scanner
    private int defaultRadix = 10;

    // The locale used by this scanner
    private Locale locale = null;

    // A cache of the last few recently used Patterns
    private LRUCache<String,Pattern> patternCache =
    new LRUCache<String,Pattern>(7) {
        protected Pattern create(String s) {
            return Pattern.compile(s);
        }
        protected boolean hasName(Pattern p, String s) {
            return p.pattern().equals(s);
        }
    };

    // A holder of the last IOException encountered
    private IOException lastException;

    // A pattern for java whitespace
    private static Pattern WHITESPACE_PATTERN = Pattern.compile(
                                                "\\p{javaWhitespace}+");

    // A pattern for any token
    private static Pattern FIND_ANY_PATTERN = Pattern.compile("(?s).*");

    // A pattern for non-ASCII digits
    private static Pattern NON_ASCII_DIGIT = Pattern.compile(
        "[\\p{javaDigit}&&[^0-9]]");

    // Fields and methods to support scanning primitive types

    /**
     * Locale dependent values used to scan numbers
     * <p>
     *  用于扫描数字的区域设置相关值
     * 
     */
    private String groupSeparator = "\\,";
    private String decimalSeparator = "\\.";
    private String nanString = "NaN";
    private String infinityString = "Infinity";
    private String positivePrefix = "";
    private String negativePrefix = "\\-";
    private String positiveSuffix = "";
    private String negativeSuffix = "";

    /**
     * Fields and an accessor method to match booleans
     * <p>
     *  字段和一个存取器方法来匹配布尔
     * 
     */
    private static volatile Pattern boolPattern;
    private static final String BOOLEAN_PATTERN = "true|false";
    private static Pattern boolPattern() {
        Pattern bp = boolPattern;
        if (bp == null)
            boolPattern = bp = Pattern.compile(BOOLEAN_PATTERN,
                                          Pattern.CASE_INSENSITIVE);
        return bp;
    }

    /**
     * Fields and methods to match bytes, shorts, ints, and longs
     * <p>
     *  匹配字节,短整型,整数和长整型的字段和方法
     * 
     */
    private Pattern integerPattern;
    private String digits = "0123456789abcdefghijklmnopqrstuvwxyz";
    private String non0Digit = "[\\p{javaDigit}&&[^0]]";
    private int SIMPLE_GROUP_INDEX = 5;
    private String buildIntegerPatternString() {
        String radixDigits = digits.substring(0, radix);
        // \\p{javaDigit} is not guaranteed to be appropriate
        // here but what can we do? The final authority will be
        // whatever parse method is invoked, so ultimately the
        // Scanner will do the right thing
        String digit = "((?i)["+radixDigits+"]|\\p{javaDigit})";
        String groupedNumeral = "("+non0Digit+digit+"?"+digit+"?("+
                                groupSeparator+digit+digit+digit+")+)";
        // digit++ is the possessive form which is necessary for reducing
        // backtracking that would otherwise cause unacceptable performance
        String numeral = "(("+ digit+"++)|"+groupedNumeral+")";
        String javaStyleInteger = "([-+]?(" + numeral + "))";
        String negativeInteger = negativePrefix + numeral + negativeSuffix;
        String positiveInteger = positivePrefix + numeral + positiveSuffix;
        return "("+ javaStyleInteger + ")|(" +
            positiveInteger + ")|(" +
            negativeInteger + ")";
    }
    private Pattern integerPattern() {
        if (integerPattern == null) {
            integerPattern = patternCache.forName(buildIntegerPatternString());
        }
        return integerPattern;
    }

    /**
     * Fields and an accessor method to match line separators
     * <p>
     *  字段和一个访问器方法来匹配行分隔符
     * 
     */
    private static volatile Pattern separatorPattern;
    private static volatile Pattern linePattern;
    private static final String LINE_SEPARATOR_PATTERN =
                                           "\r\n|[\n\r\u2028\u2029\u0085]";
    private static final String LINE_PATTERN = ".*("+LINE_SEPARATOR_PATTERN+")|.+$";

    private static Pattern separatorPattern() {
        Pattern sp = separatorPattern;
        if (sp == null)
            separatorPattern = sp = Pattern.compile(LINE_SEPARATOR_PATTERN);
        return sp;
    }

    private static Pattern linePattern() {
        Pattern lp = linePattern;
        if (lp == null)
            linePattern = lp = Pattern.compile(LINE_PATTERN);
        return lp;
    }

    /**
     * Fields and methods to match floats and doubles
     * <p>
     *  匹配浮点和双精度的字段和方法
     * 
     */
    private Pattern floatPattern;
    private Pattern decimalPattern;
    private void buildFloatAndDecimalPattern() {
        // \\p{javaDigit} may not be perfect, see above
        String digit = "([0-9]|(\\p{javaDigit}))";
        String exponent = "([eE][+-]?"+digit+"+)?";
        String groupedNumeral = "("+non0Digit+digit+"?"+digit+"?("+
                                groupSeparator+digit+digit+digit+")+)";
        // Once again digit++ is used for performance, as above
        String numeral = "(("+digit+"++)|"+groupedNumeral+")";
        String decimalNumeral = "("+numeral+"|"+numeral +
            decimalSeparator + digit + "*+|"+ decimalSeparator +
            digit + "++)";
        String nonNumber = "(NaN|"+nanString+"|Infinity|"+
                               infinityString+")";
        String positiveFloat = "(" + positivePrefix + decimalNumeral +
                            positiveSuffix + exponent + ")";
        String negativeFloat = "(" + negativePrefix + decimalNumeral +
                            negativeSuffix + exponent + ")";
        String decimal = "(([-+]?" + decimalNumeral + exponent + ")|"+
            positiveFloat + "|" + negativeFloat + ")";
        String hexFloat =
            "[-+]?0[xX][0-9a-fA-F]*\\.[0-9a-fA-F]+([pP][-+]?[0-9]+)?";
        String positiveNonNumber = "(" + positivePrefix + nonNumber +
                            positiveSuffix + ")";
        String negativeNonNumber = "(" + negativePrefix + nonNumber +
                            negativeSuffix + ")";
        String signedNonNumber = "(([-+]?"+nonNumber+")|" +
                                 positiveNonNumber + "|" +
                                 negativeNonNumber + ")";
        floatPattern = Pattern.compile(decimal + "|" + hexFloat + "|" +
                                       signedNonNumber);
        decimalPattern = Pattern.compile(decimal);
    }
    private Pattern floatPattern() {
        if (floatPattern == null) {
            buildFloatAndDecimalPattern();
        }
        return floatPattern;
    }
    private Pattern decimalPattern() {
        if (decimalPattern == null) {
            buildFloatAndDecimalPattern();
        }
        return decimalPattern;
    }

    // Constructors

    /**
     * Constructs a <code>Scanner</code> that returns values scanned
     * from the specified source delimited by the specified pattern.
     *
     * <p>
     * 构造一个<code> Scanner </code>,它返回从指定模式所分隔的指定源扫描的值
     * 
     * 
     * @param source A character source implementing the Readable interface
     * @param pattern A delimiting pattern
     */
    private Scanner(Readable source, Pattern pattern) {
        assert source != null : "source should not be null";
        assert pattern != null : "pattern should not be null";
        this.source = source;
        delimPattern = pattern;
        buf = CharBuffer.allocate(BUFFER_SIZE);
        buf.limit(0);
        matcher = delimPattern.matcher(buf);
        matcher.useTransparentBounds(true);
        matcher.useAnchoringBounds(false);
        useLocale(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified source.
     *
     * <p>
     *  构造新的<code>扫描程序</code>,生成从指定源扫描的值
     * 
     * 
     * @param  source A character source implementing the {@link Readable}
     *         interface
     */
    public Scanner(Readable source) {
        this(Objects.requireNonNull(source, "source"), WHITESPACE_PATTERN);
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified input stream. Bytes from the stream are converted
     * into characters using the underlying platform's
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}.
     *
     * <p>
     *  构造一个生成从指定输入流扫描的值的新<代码>扫描程序</code>使用基础平台的{@linkplain javaniocharsetCharset#defaultCharset()default charset}
     * 将流中的字节转换为字符。
     * 
     * 
     * @param  source An input stream to be scanned
     */
    public Scanner(InputStream source) {
        this(new InputStreamReader(source), WHITESPACE_PATTERN);
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified input stream. Bytes from the stream are converted
     * into characters using the specified charset.
     *
     * <p>
     *  构造新的<code> Scanner </code>,生成从指定输入流扫描的值使用指定的字符集将流中的字节转换为字符
     * 
     * 
     * @param  source An input stream to be scanned
     * @param charsetName The encoding type used to convert bytes from the
     *        stream into characters to be scanned
     * @throws IllegalArgumentException if the specified character set
     *         does not exist
     */
    public Scanner(InputStream source, String charsetName) {
        this(makeReadable(Objects.requireNonNull(source, "source"), toCharset(charsetName)),
             WHITESPACE_PATTERN);
    }

    /**
     * Returns a charset object for the given charset name.
     * <p>
     *  返回给定字符集名称的charset对象
     * 
     * 
     * @throws NullPointerException          is csn is null
     * @throws IllegalArgumentException      if the charset is not supported
     */
    private static Charset toCharset(String csn) {
        Objects.requireNonNull(csn, "charsetName");
        try {
            return Charset.forName(csn);
        } catch (IllegalCharsetNameException|UnsupportedCharsetException e) {
            // IllegalArgumentException should be thrown
            throw new IllegalArgumentException(e);
        }
    }

    private static Readable makeReadable(InputStream source, Charset charset) {
        return new InputStreamReader(source, charset);
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified file. Bytes from the file are converted into
     * characters using the underlying platform's
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}.
     *
     * <p>
     * 构造新的<code> Scanner </code>,生成从指定文件扫描的值使用基础平台的{@linkplain javaniocharsetCharset#defaultCharset()default charset}
     * 将文件中的字节转换为字符。
     * 
     * 
     * @param  source A file to be scanned
     * @throws FileNotFoundException if source is not found
     */
    public Scanner(File source) throws FileNotFoundException {
        this((ReadableByteChannel)(new FileInputStream(source).getChannel()));
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified file. Bytes from the file are converted into
     * characters using the specified charset.
     *
     * <p>
     *  构造新的<code>扫描程序</code>,生成从指定文件扫描的值使用指定的字符集将文件中的字节转换为字符
     * 
     * 
     * @param  source A file to be scanned
     * @param charsetName The encoding type used to convert bytes from the file
     *        into characters to be scanned
     * @throws FileNotFoundException if source is not found
     * @throws IllegalArgumentException if the specified encoding is
     *         not found
     */
    public Scanner(File source, String charsetName)
        throws FileNotFoundException
    {
        this(Objects.requireNonNull(source), toDecoder(charsetName));
    }

    private Scanner(File source, CharsetDecoder dec)
        throws FileNotFoundException
    {
        this(makeReadable((ReadableByteChannel)(new FileInputStream(source).getChannel()), dec));
    }

    private static CharsetDecoder toDecoder(String charsetName) {
        Objects.requireNonNull(charsetName, "charsetName");
        try {
            return Charset.forName(charsetName).newDecoder();
        } catch (IllegalCharsetNameException|UnsupportedCharsetException unused) {
            throw new IllegalArgumentException(charsetName);
        }
    }

    private static Readable makeReadable(ReadableByteChannel source,
                                         CharsetDecoder dec) {
        return Channels.newReader(source, dec, -1);
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified file. Bytes from the file are converted into
     * characters using the underlying platform's
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}.
     *
     * <p>
     *  构造新的<code> Scanner </code>,生成从指定文件扫描的值使用基础平台的{@linkplain javaniocharsetCharset#defaultCharset()default charset}
     * 将文件中的字节转换为字符。
     * 
     * 
     * @param   source
     *          the path to the file to be scanned
     * @throws  IOException
     *          if an I/O error occurs opening source
     *
     * @since   1.7
     */
    public Scanner(Path source)
        throws IOException
    {
        this(Files.newInputStream(source));
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified file. Bytes from the file are converted into
     * characters using the specified charset.
     *
     * <p>
     * 构造新的<code>扫描程序</code>,生成从指定文件扫描的值使用指定的字符集将文件中的字节转换为字符
     * 
     * 
     * @param   source
     *          the path to the file to be scanned
     * @param   charsetName
     *          The encoding type used to convert bytes from the file
     *          into characters to be scanned
     * @throws  IOException
     *          if an I/O error occurs opening source
     * @throws  IllegalArgumentException
     *          if the specified encoding is not found
     * @since   1.7
     */
    public Scanner(Path source, String charsetName) throws IOException {
        this(Objects.requireNonNull(source), toCharset(charsetName));
    }

    private Scanner(Path source, Charset charset)  throws IOException {
        this(makeReadable(Files.newInputStream(source), charset));
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified string.
     *
     * <p>
     *  构造一个新的<code> Scanner </code>,用于生成从指定字符串扫描的值
     * 
     * 
     * @param  source A string to scan
     */
    public Scanner(String source) {
        this(new StringReader(source), WHITESPACE_PATTERN);
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified channel. Bytes from the source are converted into
     * characters using the underlying platform's
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}.
     *
     * <p>
     *  构造一个生成从指定通道扫描的值的新<代码>扫描程序</code>使用基础平台的{@linkplain javaniocharsetCharset#defaultCharset()default charset}
     * 将源中的字节转换为字符。
     * 
     * 
     * @param  source A channel to scan
     */
    public Scanner(ReadableByteChannel source) {
        this(makeReadable(Objects.requireNonNull(source, "source")),
             WHITESPACE_PATTERN);
    }

    private static Readable makeReadable(ReadableByteChannel source) {
        return makeReadable(source, Charset.defaultCharset().newDecoder());
    }

    /**
     * Constructs a new <code>Scanner</code> that produces values scanned
     * from the specified channel. Bytes from the source are converted into
     * characters using the specified charset.
     *
     * <p>
     *  构造一个新的<code> Scanner </code>,用于生成从指定通道扫描的值使用指定的字符集将源中的字节转换为字符
     * 
     * 
     * @param  source A channel to scan
     * @param charsetName The encoding type used to convert bytes from the
     *        channel into characters to be scanned
     * @throws IllegalArgumentException if the specified character set
     *         does not exist
     */
    public Scanner(ReadableByteChannel source, String charsetName) {
        this(makeReadable(Objects.requireNonNull(source, "source"), toDecoder(charsetName)),
             WHITESPACE_PATTERN);
    }

    // Private primitives used to support scanning

    private void saveState() {
        savedScannerPosition = position;
    }

    private void revertState() {
        this.position = savedScannerPosition;
        savedScannerPosition = -1;
        skipped = false;
    }

    private boolean revertState(boolean b) {
        this.position = savedScannerPosition;
        savedScannerPosition = -1;
        skipped = false;
        return b;
    }

    private void cacheResult() {
        hasNextResult = matcher.group();
        hasNextPosition = matcher.end();
        hasNextPattern = matcher.pattern();
    }

    private void cacheResult(String result) {
        hasNextResult = result;
        hasNextPosition = matcher.end();
        hasNextPattern = matcher.pattern();
    }

    // Clears both regular cache and type cache
    private void clearCaches() {
        hasNextPattern = null;
        typeCache = null;
    }

    // Also clears both the regular cache and the type cache
    private String getCachedResult() {
        position = hasNextPosition;
        hasNextPattern = null;
        typeCache = null;
        return hasNextResult;
    }

    // Also clears both the regular cache and the type cache
    private void useTypeCache() {
        if (closed)
            throw new IllegalStateException("Scanner closed");
        position = hasNextPosition;
        hasNextPattern = null;
        typeCache = null;
    }

    // Tries to read more input. May block.
    private void readInput() {
        if (buf.limit() == buf.capacity())
            makeSpace();

        // Prepare to receive data
        int p = buf.position();
        buf.position(buf.limit());
        buf.limit(buf.capacity());

        int n = 0;
        try {
            n = source.read(buf);
        } catch (IOException ioe) {
            lastException = ioe;
            n = -1;
        }

        if (n == -1) {
            sourceClosed = true;
            needInput = false;
        }

        if (n > 0)
            needInput = false;

        // Restore current position and limit for reading
        buf.limit(buf.position());
        buf.position(p);
    }

    // After this method is called there will either be an exception
    // or else there will be space in the buffer
    private boolean makeSpace() {
        clearCaches();
        int offset = savedScannerPosition == -1 ?
            position : savedScannerPosition;
        buf.position(offset);
        // Gain space by compacting buffer
        if (offset > 0) {
            buf.compact();
            translateSavedIndexes(offset);
            position -= offset;
            buf.flip();
            return true;
        }
        // Gain space by growing buffer
        int newSize = buf.capacity() * 2;
        CharBuffer newBuf = CharBuffer.allocate(newSize);
        newBuf.put(buf);
        newBuf.flip();
        translateSavedIndexes(offset);
        position -= offset;
        buf = newBuf;
        matcher.reset(buf);
        return true;
    }

    // When a buffer compaction/reallocation occurs the saved indexes must
    // be modified appropriately
    private void translateSavedIndexes(int offset) {
        if (savedScannerPosition != -1)
            savedScannerPosition -= offset;
    }

    // If we are at the end of input then NoSuchElement;
    // If there is still input left then InputMismatch
    private void throwFor() {
        skipped = false;
        if ((sourceClosed) && (position == buf.limit()))
            throw new NoSuchElementException();
        else
            throw new InputMismatchException();
    }

    // Returns true if a complete token or partial token is in the buffer.
    // It is not necessary to find a complete token since a partial token
    // means that there will be another token with or without more input.
    private boolean hasTokenInBuffer() {
        matchValid = false;
        matcher.usePattern(delimPattern);
        matcher.region(position, buf.limit());

        // Skip delims first
        if (matcher.lookingAt())
            position = matcher.end();

        // If we are sitting at the end, no more tokens in buffer
        if (position == buf.limit())
            return false;

        return true;
    }

    /*
     * Returns a "complete token" that matches the specified pattern
     *
     * A token is complete if surrounded by delims; a partial token
     * is prefixed by delims but not postfixed by them
     *
     * The position is advanced to the end of that complete token
     *
     * Pattern == null means accept any token at all
     *
     * Triple return:
     * 1. valid string means it was found
     * 2. null with needInput=false means we won't ever find it
     * 3. null with needInput=true means try again after readInput
     * <p>
     *  返回与指定模式匹配的"完整令牌"
     * 
     * 如果被delims包围,令牌是完整的;部分令牌由delims前缀,但不由它们后缀
     * 
     *  位置前进到完整令牌的结束
     * 
     *  Pattern == null表示接受任何令牌
     * 
     *  三重返回：1有效字符串意味着它找到2 null with needInput = false意味着我们不会找到它3 null与needInput = true意味着再次尝试后readInput
     * 
     */
    private String getCompleteTokenInBuffer(Pattern pattern) {
        matchValid = false;

        // Skip delims first
        matcher.usePattern(delimPattern);
        if (!skipped) { // Enforcing only one skip of leading delims
            matcher.region(position, buf.limit());
            if (matcher.lookingAt()) {
                // If more input could extend the delimiters then we must wait
                // for more input
                if (matcher.hitEnd() && !sourceClosed) {
                    needInput = true;
                    return null;
                }
                // The delims were whole and the matcher should skip them
                skipped = true;
                position = matcher.end();
            }
        }

        // If we are sitting at the end, no more tokens in buffer
        if (position == buf.limit()) {
            if (sourceClosed)
                return null;
            needInput = true;
            return null;
        }

        // Must look for next delims. Simply attempting to match the
        // pattern at this point may find a match but it might not be
        // the first longest match because of missing input, or it might
        // match a partial token instead of the whole thing.

        // Then look for next delims
        matcher.region(position, buf.limit());
        boolean foundNextDelim = matcher.find();
        if (foundNextDelim && (matcher.end() == position)) {
            // Zero length delimiter match; we should find the next one
            // using the automatic advance past a zero length match;
            // Otherwise we have just found the same one we just skipped
            foundNextDelim = matcher.find();
        }
        if (foundNextDelim) {
            // In the rare case that more input could cause the match
            // to be lost and there is more input coming we must wait
            // for more input. Note that hitting the end is okay as long
            // as the match cannot go away. It is the beginning of the
            // next delims we want to be sure about, we don't care if
            // they potentially extend further.
            if (matcher.requireEnd() && !sourceClosed) {
                needInput = true;
                return null;
            }
            int tokenEnd = matcher.start();
            // There is a complete token.
            if (pattern == null) {
                // Must continue with match to provide valid MatchResult
                pattern = FIND_ANY_PATTERN;
            }
            //  Attempt to match against the desired pattern
            matcher.usePattern(pattern);
            matcher.region(position, tokenEnd);
            if (matcher.matches()) {
                String s = matcher.group();
                position = matcher.end();
                return s;
            } else { // Complete token but it does not match
                return null;
            }
        }

        // If we can't find the next delims but no more input is coming,
        // then we can treat the remainder as a whole token
        if (sourceClosed) {
            if (pattern == null) {
                // Must continue with match to provide valid MatchResult
                pattern = FIND_ANY_PATTERN;
            }
            // Last token; Match the pattern here or throw
            matcher.usePattern(pattern);
            matcher.region(position, buf.limit());
            if (matcher.matches()) {
                String s = matcher.group();
                position = matcher.end();
                return s;
            }
            // Last piece does not match
            return null;
        }

        // There is a partial token in the buffer; must read more
        // to complete it
        needInput = true;
        return null;
    }

    // Finds the specified pattern in the buffer up to horizon.
    // Returns a match for the specified input pattern.
    private String findPatternInBuffer(Pattern pattern, int horizon) {
        matchValid = false;
        matcher.usePattern(pattern);
        int bufferLimit = buf.limit();
        int horizonLimit = -1;
        int searchLimit = bufferLimit;
        if (horizon > 0) {
            horizonLimit = position + horizon;
            if (horizonLimit < bufferLimit)
                searchLimit = horizonLimit;
        }
        matcher.region(position, searchLimit);
        if (matcher.find()) {
            if (matcher.hitEnd() && (!sourceClosed)) {
                // The match may be longer if didn't hit horizon or real end
                if (searchLimit != horizonLimit) {
                     // Hit an artificial end; try to extend the match
                    needInput = true;
                    return null;
                }
                // The match could go away depending on what is next
                if ((searchLimit == horizonLimit) && matcher.requireEnd()) {
                    // Rare case: we hit the end of input and it happens
                    // that it is at the horizon and the end of input is
                    // required for the match.
                    needInput = true;
                    return null;
                }
            }
            // Did not hit end, or hit real end, or hit horizon
            position = matcher.end();
            return matcher.group();
        }

        if (sourceClosed)
            return null;

        // If there is no specified horizon, or if we have not searched
        // to the specified horizon yet, get more input
        if ((horizon == 0) || (searchLimit != horizonLimit))
            needInput = true;
        return null;
    }

    // Returns a match for the specified input pattern anchored at
    // the current position
    private String matchPatternInBuffer(Pattern pattern) {
        matchValid = false;
        matcher.usePattern(pattern);
        matcher.region(position, buf.limit());
        if (matcher.lookingAt()) {
            if (matcher.hitEnd() && (!sourceClosed)) {
                // Get more input and try again
                needInput = true;
                return null;
            }
            position = matcher.end();
            return matcher.group();
        }

        if (sourceClosed)
            return null;

        // Read more to find pattern
        needInput = true;
        return null;
    }

    // Throws if the scanner is closed
    private void ensureOpen() {
        if (closed)
            throw new IllegalStateException("Scanner closed");
    }

    // Public methods

    /**
     * Closes this scanner.
     *
     * <p> If this scanner has not yet been closed then if its underlying
     * {@linkplain java.lang.Readable readable} also implements the {@link
     * java.io.Closeable} interface then the readable's <tt>close</tt> method
     * will be invoked.  If this scanner is already closed then invoking this
     * method will have no effect.
     *
     * <p>Attempting to perform search operations after a scanner has
     * been closed will result in an {@link IllegalStateException}.
     *
     * <p>
     *  关闭此扫描仪
     * 
     *  <p>如果此扫描程序尚未关闭,则如果其底层的{@linkplain javalangReadable readable}也实现了{@link javaioCloseable}接口,那么将调用可读的<tt>
     * 关闭</tt>方法。
     * 如果此扫描程序已经关闭然后调用这个方法将没有效果。
     * 
     * <p>尝试在扫描程序关闭后执行搜索操作将导致{@link IllegalStateException}
     * 
     */
    public void close() {
        if (closed)
            return;
        if (source instanceof Closeable) {
            try {
                ((Closeable)source).close();
            } catch (IOException ioe) {
                lastException = ioe;
            }
        }
        sourceClosed = true;
        source = null;
        closed = true;
    }

    /**
     * Returns the <code>IOException</code> last thrown by this
     * <code>Scanner</code>'s underlying <code>Readable</code>. This method
     * returns <code>null</code> if no such exception exists.
     *
     * <p>
     *  返回<code>最后抛出的<code> IOException </code> </>> </>> </>>此方法返回<code> null </code>
     * 
     * 
     * @return the last exception thrown by this scanner's readable
     */
    public IOException ioException() {
        return lastException;
    }

    /**
     * Returns the <code>Pattern</code> this <code>Scanner</code> is currently
     * using to match delimiters.
     *
     * <p>
     *  返回此代码<code> </code>当前正在使用<code> Pattern </code>匹配分隔符
     * 
     * 
     * @return this scanner's delimiting pattern.
     */
    public Pattern delimiter() {
        return delimPattern;
    }

    /**
     * Sets this scanner's delimiting pattern to the specified pattern.
     *
     * <p>
     *  将此扫描仪的分隔图案设置为指定的图案
     * 
     * 
     * @param pattern A delimiting pattern
     * @return this scanner
     */
    public Scanner useDelimiter(Pattern pattern) {
        delimPattern = pattern;
        return this;
    }

    /**
     * Sets this scanner's delimiting pattern to a pattern constructed from
     * the specified <code>String</code>.
     *
     * <p> An invocation of this method of the form
     * <tt>useDelimiter(pattern)</tt> behaves in exactly the same way as the
     * invocation <tt>useDelimiter(Pattern.compile(pattern))</tt>.
     *
     * <p> Invoking the {@link #reset} method will set the scanner's delimiter
     * to the <a href= "#default-delimiter">default</a>.
     *
     * <p>
     *  将此扫描器的分隔模式设置为从指定的<code> String </code>构造的模式
     * 
     *  <p>调用此方法的形式<tt> useDelimiter(pattern)</tt>的行为与调用<tt> useDelimiter(Patterncompile(pattern))完全相同</tt>。
     * 
     * <p>调用{@link #reset}方法会将扫描程序的分隔符设置为<a href= \"#default-delimiter\">默认值</a>
     * 
     * 
     * @param pattern A string specifying a delimiting pattern
     * @return this scanner
     */
    public Scanner useDelimiter(String pattern) {
        delimPattern = patternCache.forName(pattern);
        return this;
    }

    /**
     * Returns this scanner's locale.
     *
     * <p>A scanner's locale affects many elements of its default
     * primitive matching regular expressions; see
     * <a href= "#localized-numbers">localized numbers</a> above.
     *
     * <p>
     *  返回此扫描程序的区域设置
     * 
     *  <p>扫描器的区域设置影响其默认基元与其匹配的正则表达式的许多元素;请参阅上面的<a href= \"#localized-numbers\">本地化数字</a>
     * 
     * 
     * @return this scanner's locale
     */
    public Locale locale() {
        return this.locale;
    }

    /**
     * Sets this scanner's locale to the specified locale.
     *
     * <p>A scanner's locale affects many elements of its default
     * primitive matching regular expressions; see
     * <a href= "#localized-numbers">localized numbers</a> above.
     *
     * <p>Invoking the {@link #reset} method will set the scanner's locale to
     * the <a href= "#initial-locale">initial locale</a>.
     *
     * <p>
     *  将此扫描仪的区域设置设置为指定的区域设置
     * 
     *  <p>扫描器的区域设置会影响其默认基元与其匹配的正则表达式的许多元素;请参阅上面的<a href= \"#localized-numbers\">本地化数字</a>
     * 
     *  <p>调用{@link #reset}方法会将扫描器的区域设置设置为<a href= \"#initial-locale\">初始区域设置</a>
     * 
     * 
     * @param locale A string specifying the locale to use
     * @return this scanner
     */
    public Scanner useLocale(Locale locale) {
        if (locale.equals(this.locale))
            return this;

        this.locale = locale;
        DecimalFormat df =
            (DecimalFormat)NumberFormat.getNumberInstance(locale);
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);

        // These must be literalized to avoid collision with regex
        // metacharacters such as dot or parenthesis
        groupSeparator =   "\\" + dfs.getGroupingSeparator();
        decimalSeparator = "\\" + dfs.getDecimalSeparator();

        // Quoting the nonzero length locale-specific things
        // to avoid potential conflict with metacharacters
        nanString = "\\Q" + dfs.getNaN() + "\\E";
        infinityString = "\\Q" + dfs.getInfinity() + "\\E";
        positivePrefix = df.getPositivePrefix();
        if (positivePrefix.length() > 0)
            positivePrefix = "\\Q" + positivePrefix + "\\E";
        negativePrefix = df.getNegativePrefix();
        if (negativePrefix.length() > 0)
            negativePrefix = "\\Q" + negativePrefix + "\\E";
        positiveSuffix = df.getPositiveSuffix();
        if (positiveSuffix.length() > 0)
            positiveSuffix = "\\Q" + positiveSuffix + "\\E";
        negativeSuffix = df.getNegativeSuffix();
        if (negativeSuffix.length() > 0)
            negativeSuffix = "\\Q" + negativeSuffix + "\\E";

        // Force rebuilding and recompilation of locale dependent
        // primitive patterns
        integerPattern = null;
        floatPattern = null;

        return this;
    }

    /**
     * Returns this scanner's default radix.
     *
     * <p>A scanner's radix affects elements of its default
     * number matching regular expressions; see
     * <a href= "#localized-numbers">localized numbers</a> above.
     *
     * <p>
     *  返回此扫描仪的默认基数
     * 
     * <p>扫描器的基数影响与默认数字匹配的正则表达式的元素;请参阅上面的<a href= \"#localized-numbers\">本地化数字</a>
     * 
     * 
     * @return the default radix of this scanner
     */
    public int radix() {
        return this.defaultRadix;
    }

    /**
     * Sets this scanner's default radix to the specified radix.
     *
     * <p>A scanner's radix affects elements of its default
     * number matching regular expressions; see
     * <a href= "#localized-numbers">localized numbers</a> above.
     *
     * <p>If the radix is less than <code>Character.MIN_RADIX</code>
     * or greater than <code>Character.MAX_RADIX</code>, then an
     * <code>IllegalArgumentException</code> is thrown.
     *
     * <p>Invoking the {@link #reset} method will set the scanner's radix to
     * <code>10</code>.
     *
     * <p>
     *  将此扫描仪的默认基数设置为指定的基数
     * 
     *  <p>扫描器的基数影响与默认数字匹配的正则表达式的元素;请参阅上面的<a href= \"#localized-numbers\">本地化数字</a>
     * 
     *  <p>如果基数小于<code> CharacterMIN_RADIX </code>或大于<code> CharacterMAX_RADIX </code>,则会抛出<code> IllegalArg
     * umentException </code>。
     * 
     *  <p>调用{@link #reset}方法会将扫描程序的基数设置为<code> 10 </code>
     * 
     * 
     * @param radix The radix to use when scanning numbers
     * @return this scanner
     * @throws IllegalArgumentException if radix is out of range
     */
    public Scanner useRadix(int radix) {
        if ((radix < Character.MIN_RADIX) || (radix > Character.MAX_RADIX))
            throw new IllegalArgumentException("radix:"+radix);

        if (this.defaultRadix == radix)
            return this;
        this.defaultRadix = radix;
        // Force rebuilding and recompilation of radix dependent patterns
        integerPattern = null;
        return this;
    }

    // The next operation should occur in the specified radix but
    // the default is left untouched.
    private void setRadix(int radix) {
        if (this.radix != radix) {
            // Force rebuilding and recompilation of radix dependent patterns
            integerPattern = null;
            this.radix = radix;
        }
    }

    /**
     * Returns the match result of the last scanning operation performed
     * by this scanner. This method throws <code>IllegalStateException</code>
     * if no match has been performed, or if the last match was
     * not successful.
     *
     * <p>The various <code>next</code>methods of <code>Scanner</code>
     * make a match result available if they complete without throwing an
     * exception. For instance, after an invocation of the {@link #nextInt}
     * method that returned an int, this method returns a
     * <code>MatchResult</code> for the search of the
     * <a href="#Integer-regex"><i>Integer</i></a> regular expression
     * defined above. Similarly the {@link #findInLine},
     * {@link #findWithinHorizon}, and {@link #skip} methods will make a
     * match available if they succeed.
     *
     * <p>
     * 返回此扫描程序执行的最后一次扫描操作的匹配结果如果未执行匹配,或上一次匹配未成功,此方法将抛出<code> IllegalStateException </code>
     * 
     *  <p> <code> Scanner </code>的各种<code> next </code>方法使匹配结果可用,如果它们完成而不抛出异常例如,在调用{@link #nextInt}方法返回一个in
     * t,此方法返回<code> MatchResult </code>,用于搜索上面定义的<a href=\"#Integer-regex\"> <i> Integer </i> </a>正则表达式如果成功
     * ,{@link #findInLine},{@link #findWithinHorizo​​n}和{@link #skip}方法将提供匹配。
     * 
     * 
     * @return a match result for the last match operation
     * @throws IllegalStateException  If no match result is available
     */
    public MatchResult match() {
        if (!matchValid)
            throw new IllegalStateException("No match result available");
        return matcher.toMatchResult();
    }

    /**
     * <p>Returns the string representation of this <code>Scanner</code>. The
     * string representation of a <code>Scanner</code> contains information
     * that may be useful for debugging. The exact format is unspecified.
     *
     * <p>
     * <p>返回<code> Scanner的字符串表示</code> <code> Scanner </code>的字符串表示包含可能对调试有用的信息确切的格式未指定
     * 
     * 
     * @return  The string representation of this scanner
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("java.util.Scanner");
        sb.append("[delimiters=" + delimPattern + "]");
        sb.append("[position=" + position + "]");
        sb.append("[match valid=" + matchValid + "]");
        sb.append("[need input=" + needInput + "]");
        sb.append("[source closed=" + sourceClosed + "]");
        sb.append("[skipped=" + skipped + "]");
        sb.append("[group separator=" + groupSeparator + "]");
        sb.append("[decimal separator=" + decimalSeparator + "]");
        sb.append("[positive prefix=" + positivePrefix + "]");
        sb.append("[negative prefix=" + negativePrefix + "]");
        sb.append("[positive suffix=" + positiveSuffix + "]");
        sb.append("[negative suffix=" + negativeSuffix + "]");
        sb.append("[NaN string=" + nanString + "]");
        sb.append("[infinity string=" + infinityString + "]");
        return sb.toString();
    }

    /**
     * Returns true if this scanner has another token in its input.
     * This method may block while waiting for input to scan.
     * The scanner does not advance past any input.
     *
     * <p>
     *  如果此扫描程序在其输入中有另一个标记,则返回true此方法可能在等待输入进行扫描时阻止扫描程序不会超过任何输入
     * 
     * 
     * @return true if and only if this scanner has another token
     * @throws IllegalStateException if this scanner is closed
     * @see java.util.Iterator
     */
    public boolean hasNext() {
        ensureOpen();
        saveState();
        while (!sourceClosed) {
            if (hasTokenInBuffer())
                return revertState(true);
            readInput();
        }
        boolean result = hasTokenInBuffer();
        return revertState(result);
    }

    /**
     * Finds and returns the next complete token from this scanner.
     * A complete token is preceded and followed by input that matches
     * the delimiter pattern. This method may block while waiting for input
     * to scan, even if a previous invocation of {@link #hasNext} returned
     * <code>true</code>.
     *
     * <p>
     *  查找并返回来自此扫描程序的下一个完整令牌完整令牌前面和后面是与分隔符模式匹配的输入此方法可能会在等待输入扫描时阻塞,即使以前对{@link #hasNext}的调用返回<code > true </code>
     * 。
     * 
     * 
     * @return the next token
     * @throws NoSuchElementException if no more tokens are available
     * @throws IllegalStateException if this scanner is closed
     * @see java.util.Iterator
     */
    public String next() {
        ensureOpen();
        clearCaches();

        while (true) {
            String token = getCompleteTokenInBuffer(null);
            if (token != null) {
                matchValid = true;
                skipped = false;
                return token;
            }
            if (needInput)
                readInput();
            else
                throwFor();
        }
    }

    /**
     * The remove operation is not supported by this implementation of
     * <code>Iterator</code>.
     *
     * <p>
     *  <code> Iterator </code>的实现不支持删除操作
     * 
     * 
     * @throws UnsupportedOperationException if this method is invoked.
     * @see java.util.Iterator
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns true if the next token matches the pattern constructed from the
     * specified string. The scanner does not advance past any input.
     *
     * <p> An invocation of this method of the form <tt>hasNext(pattern)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>hasNext(Pattern.compile(pattern))</tt>.
     *
     * <p>
     * 如果下一个标记与从指定字符串构造的模式匹配,则返回true扫描程序不超过任何输入
     * 
     *  <p>以<tt> hasNext(pattern)</tt>的形式调用此方法的行为与调用<tt> hasNext(Patterncompile(pattern))完全相同</tt>
     * 
     * 
     * @param pattern a string specifying the pattern to scan
     * @return true if and only if this scanner has another token matching
     *         the specified pattern
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNext(String pattern)  {
        return hasNext(patternCache.forName(pattern));
    }

    /**
     * Returns the next token if it matches the pattern constructed from the
     * specified string.  If the match is successful, the scanner advances
     * past the input that matched the pattern.
     *
     * <p> An invocation of this method of the form <tt>next(pattern)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>next(Pattern.compile(pattern))</tt>.
     *
     * <p>
     *  如果匹配从指定字符串构造的模式,则返回下一个标记如果匹配成功,则扫描器前进超过与模式匹配的输入
     * 
     *  <p>以<tt> next(pattern)</tt>格式调用此方法的行为与调用<tt> next(Patterncompile(pattern))</tt>
     * 
     * 
     * @param pattern a string specifying the pattern to scan
     * @return the next token
     * @throws NoSuchElementException if no such tokens are available
     * @throws IllegalStateException if this scanner is closed
     */
    public String next(String pattern)  {
        return next(patternCache.forName(pattern));
    }

    /**
     * Returns true if the next complete token matches the specified pattern.
     * A complete token is prefixed and postfixed by input that matches
     * the delimiter pattern. This method may block while waiting for input.
     * The scanner does not advance past any input.
     *
     * <p>
     * 如果下一个完整令牌与指定模式匹配,则返回true。完整令牌由与分隔符模式匹配的输入作为前缀和后缀。此方法在等待输入时可能会阻塞扫描器不会超过任何输入
     * 
     * 
     * @param pattern the pattern to scan for
     * @return true if and only if this scanner has another token matching
     *         the specified pattern
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNext(Pattern pattern) {
        ensureOpen();
        if (pattern == null)
            throw new NullPointerException();
        hasNextPattern = null;
        saveState();

        while (true) {
            if (getCompleteTokenInBuffer(pattern) != null) {
                matchValid = true;
                cacheResult();
                return revertState(true);
            }
            if (needInput)
                readInput();
            else
                return revertState(false);
        }
    }

    /**
     * Returns the next token if it matches the specified pattern. This
     * method may block while waiting for input to scan, even if a previous
     * invocation of {@link #hasNext(Pattern)} returned <code>true</code>.
     * If the match is successful, the scanner advances past the input that
     * matched the pattern.
     *
     * <p>
     *  返回下一个令牌,如果它匹配指定的模式此方法可能会阻塞,等待输入扫描,即使以前的调用{@link #hasNext(Pattern)}返回<code> true </code>如果匹配成功,扫描器前进经
     * 过匹配图案的输入。
     * 
     * 
     * @param pattern the pattern to scan for
     * @return the next token
     * @throws NoSuchElementException if no more tokens are available
     * @throws IllegalStateException if this scanner is closed
     */
    public String next(Pattern pattern) {
        ensureOpen();
        if (pattern == null)
            throw new NullPointerException();

        // Did we already find this pattern?
        if (hasNextPattern == pattern)
            return getCachedResult();
        clearCaches();

        // Search for the pattern
        while (true) {
            String token = getCompleteTokenInBuffer(pattern);
            if (token != null) {
                matchValid = true;
                skipped = false;
                return token;
            }
            if (needInput)
                readInput();
            else
                throwFor();
        }
    }

    /**
     * Returns true if there is another line in the input of this scanner.
     * This method may block while waiting for input. The scanner does not
     * advance past any input.
     *
     * <p>
     *  如果此扫描程序的输入中有另一行,则返回true此方法可能在等待输入时阻止扫描程序不超过任何输入
     * 
     * 
     * @return true if and only if this scanner has another line of input
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextLine() {
        saveState();

        String result = findWithinHorizon(linePattern(), 0);
        if (result != null) {
            MatchResult mr = this.match();
            String lineSep = mr.group(1);
            if (lineSep != null) {
                result = result.substring(0, result.length() -
                                          lineSep.length());
                cacheResult(result);

            } else {
                cacheResult();
            }
        }
        revertState();
        return (result != null);
    }

    /**
     * Advances this scanner past the current line and returns the input
     * that was skipped.
     *
     * This method returns the rest of the current line, excluding any line
     * separator at the end. The position is set to the beginning of the next
     * line.
     *
     * <p>Since this method continues to search through the input looking
     * for a line separator, it may buffer all of the input searching for
     * the line to skip if no line separators are present.
     *
     * <p>
     *  使扫描仪前进到当前行,并返回被跳过的输入
     * 
     * 此方法返回当前行的其余部分,不包括末尾的任何行分隔符。位置设置为下一行的开始
     * 
     *  <p>由于此方法继续搜索查找分隔符的输入,如果没有行分隔符,它可以缓冲所有要跳过的行的输入搜索
     * 
     * 
     * @return the line that was skipped
     * @throws NoSuchElementException if no line was found
     * @throws IllegalStateException if this scanner is closed
     */
    public String nextLine() {
        if (hasNextPattern == linePattern())
            return getCachedResult();
        clearCaches();

        String result = findWithinHorizon(linePattern, 0);
        if (result == null)
            throw new NoSuchElementException("No line found");
        MatchResult mr = this.match();
        String lineSep = mr.group(1);
        if (lineSep != null)
            result = result.substring(0, result.length() - lineSep.length());
        if (result == null)
            throw new NoSuchElementException();
        else
            return result;
    }

    // Public methods that ignore delimiters

    /**
     * Attempts to find the next occurrence of a pattern constructed from the
     * specified string, ignoring delimiters.
     *
     * <p>An invocation of this method of the form <tt>findInLine(pattern)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>findInLine(Pattern.compile(pattern))</tt>.
     *
     * <p>
     *  尝试查找从指定字符串构造的模式的下一个出现,忽略定界符
     * 
     *  <p>调用<tt> findInLine(pattern)</tt>形式的此方法的行为与调用<tt> findInLine(Patterncompile(pattern))完全相同</tt>
     * 
     * 
     * @param pattern a string specifying the pattern to search for
     * @return the text that matched the specified pattern
     * @throws IllegalStateException if this scanner is closed
     */
    public String findInLine(String pattern) {
        return findInLine(patternCache.forName(pattern));
    }

    /**
     * Attempts to find the next occurrence of the specified pattern ignoring
     * delimiters. If the pattern is found before the next line separator, the
     * scanner advances past the input that matched and returns the string that
     * matched the pattern.
     * If no such pattern is detected in the input up to the next line
     * separator, then <code>null</code> is returned and the scanner's
     * position is unchanged. This method may block waiting for input that
     * matches the pattern.
     *
     * <p>Since this method continues to search through the input looking
     * for the specified pattern, it may buffer all of the input searching for
     * the desired token if no line separators are present.
     *
     * <p>
     * 尝试查找指定模式的下一个出现忽略分隔符如果在下一个行分隔符之前找到该模式,则扫描器前进超过匹配的输入,并返回与该模式匹配的字符串如果在输入中未检测到此类模式,下一行分隔符,则返回<code> null 
     * </code>,并且扫描器的位置不变。
     * 该方法可以阻止等待与模式匹配的输入。
     * 
     *  <p>由于此方法继续搜索通过输入查找指定的模式,如果没有行分隔符,它可以缓冲所有输入搜索所需的令牌
     * 
     * 
     * @param pattern the pattern to scan for
     * @return the text that matched the specified pattern
     * @throws IllegalStateException if this scanner is closed
     */
    public String findInLine(Pattern pattern) {
        ensureOpen();
        if (pattern == null)
            throw new NullPointerException();
        clearCaches();
        // Expand buffer to include the next newline or end of input
        int endPosition = 0;
        saveState();
        while (true) {
            String token = findPatternInBuffer(separatorPattern(), 0);
            if (token != null) {
                endPosition = matcher.start();
                break; // up to next newline
            }
            if (needInput) {
                readInput();
            } else {
                endPosition = buf.limit();
                break; // up to end of input
            }
        }
        revertState();
        int horizonForLine = endPosition - position;
        // If there is nothing between the current pos and the next
        // newline simply return null, invoking findWithinHorizon
        // with "horizon=0" will scan beyond the line bound.
        if (horizonForLine == 0)
            return null;
        // Search for the pattern
        return findWithinHorizon(pattern, horizonForLine);
    }

    /**
     * Attempts to find the next occurrence of a pattern constructed from the
     * specified string, ignoring delimiters.
     *
     * <p>An invocation of this method of the form
     * <tt>findWithinHorizon(pattern)</tt> behaves in exactly the same way as
     * the invocation
     * <tt>findWithinHorizon(Pattern.compile(pattern, horizon))</tt>.
     *
     * <p>
     *  尝试查找从指定字符串构造的模式的下一个出现,忽略定界符
     * 
     * <p>对调用<tt> findWithinHorizo​​n(pattern)</tt>形式的此方法的行为与调用<tt> findWithinHorizo​​n(Patterncompile(patte
     * rn,horizo​​n))完全相同</tt>。
     * 
     * 
     * @param pattern a string specifying the pattern to search for
     * @param horizon the search horizon
     * @return the text that matched the specified pattern
     * @throws IllegalStateException if this scanner is closed
     * @throws IllegalArgumentException if horizon is negative
     */
    public String findWithinHorizon(String pattern, int horizon) {
        return findWithinHorizon(patternCache.forName(pattern), horizon);
    }

    /**
     * Attempts to find the next occurrence of the specified pattern.
     *
     * <p>This method searches through the input up to the specified
     * search horizon, ignoring delimiters. If the pattern is found the
     * scanner advances past the input that matched and returns the string
     * that matched the pattern. If no such pattern is detected then the
     * null is returned and the scanner's position remains unchanged. This
     * method may block waiting for input that matches the pattern.
     *
     * <p>A scanner will never search more than <code>horizon</code> code
     * points beyond its current position. Note that a match may be clipped
     * by the horizon; that is, an arbitrary match result may have been
     * different if the horizon had been larger. The scanner treats the
     * horizon as a transparent, non-anchoring bound (see {@link
     * Matcher#useTransparentBounds} and {@link Matcher#useAnchoringBounds}).
     *
     * <p>If horizon is <code>0</code>, then the horizon is ignored and
     * this method continues to search through the input looking for the
     * specified pattern without bound. In this case it may buffer all of
     * the input searching for the pattern.
     *
     * <p>If horizon is negative, then an IllegalArgumentException is
     * thrown.
     *
     * <p>
     *  尝试查找指定模式的下一个出现
     * 
     *  <p>此方法搜索输入直到指定的搜索范围,忽略定界符如果找到模式,则扫描器超过匹配的输入并返回与模式匹配的字符串。如果未检测到此类模式,则返回null并且扫描仪的位置保持不变。
     * 此方法可能阻止等待与模式匹配的输入。
     * 
     * <p>扫描器永远不会搜索超过当前位置的<code>地平线</code>代码点注意匹配可能会被地平线剪切;如果地平线已经较大,则任意匹配结果可能不同。
     * 扫描仪将地平线视为透明的非锚定边界(参见{@link Matcher#useTransparentBounds}和{@link Matcher#useAnchoringBounds})。
     * 
     *  <p>如果地平线是<code> 0 </code>,则地平线被忽略,并且此方法继续搜索输入,寻找指定的模式,无边界。在这种情况下,它可以缓冲所有输入搜索模式
     * 
     *  <p>如果地平线为负,则抛出IllegalArgumentException
     * 
     * 
     * @param pattern the pattern to scan for
     * @param horizon the search horizon
     * @return the text that matched the specified pattern
     * @throws IllegalStateException if this scanner is closed
     * @throws IllegalArgumentException if horizon is negative
     */
    public String findWithinHorizon(Pattern pattern, int horizon) {
        ensureOpen();
        if (pattern == null)
            throw new NullPointerException();
        if (horizon < 0)
            throw new IllegalArgumentException("horizon < 0");
        clearCaches();

        // Search for the pattern
        while (true) {
            String token = findPatternInBuffer(pattern, horizon);
            if (token != null) {
                matchValid = true;
                return token;
            }
            if (needInput)
                readInput();
            else
                break; // up to end of input
        }
        return null;
    }

    /**
     * Skips input that matches the specified pattern, ignoring delimiters.
     * This method will skip input if an anchored match of the specified
     * pattern succeeds.
     *
     * <p>If a match to the specified pattern is not found at the
     * current position, then no input is skipped and a
     * <tt>NoSuchElementException</tt> is thrown.
     *
     * <p>Since this method seeks to match the specified pattern starting at
     * the scanner's current position, patterns that can match a lot of
     * input (".*", for example) may cause the scanner to buffer a large
     * amount of input.
     *
     * <p>Note that it is possible to skip something without risking a
     * <code>NoSuchElementException</code> by using a pattern that can
     * match nothing, e.g., <code>sc.skip("[ \t]*")</code>.
     *
     * <p>
     * 忽略与指定模式匹配的输入,忽略定界符如果指定模式的定位匹配成功,此方法将跳过输入
     * 
     *  <p>如果在当前位置找不到与指定模式的匹配,则不跳过任何输入,并且会抛出<tt> NoSuchElementException </tt>
     * 
     *  <p>由于此方法寻求在扫描仪的当前位置开始匹配指定的模式,因此可以匹配大量输入(例如"*")的模式可能会导致扫描仪缓冲大量输入
     * 
     *  <p>请注意,通过使用可以匹配任何内容的模式,例如<code> scskip("[\\ t] *")</code>,可以跳过某些没有冒险的<code> NoSuchElementException </code>
     * 。
     * 
     * 
     * @param pattern a string specifying the pattern to skip over
     * @return this scanner
     * @throws NoSuchElementException if the specified pattern is not found
     * @throws IllegalStateException if this scanner is closed
     */
    public Scanner skip(Pattern pattern) {
        ensureOpen();
        if (pattern == null)
            throw new NullPointerException();
        clearCaches();

        // Search for the pattern
        while (true) {
            String token = matchPatternInBuffer(pattern);
            if (token != null) {
                matchValid = true;
                position = matcher.end();
                return this;
            }
            if (needInput)
                readInput();
            else
                throw new NoSuchElementException();
        }
    }

    /**
     * Skips input that matches a pattern constructed from the specified
     * string.
     *
     * <p> An invocation of this method of the form <tt>skip(pattern)</tt>
     * behaves in exactly the same way as the invocation
     * <tt>skip(Pattern.compile(pattern))</tt>.
     *
     * <p>
     *  跳过与从指定字符串构造的模式匹配的输入
     * 
     * <p>以<tt> skip(pattern)</tt>的形式调用此方法的行为与调用<tt> skip(Patterncompile(pattern))完全相同</tt>
     * 
     * 
     * @param pattern a string specifying the pattern to skip over
     * @return this scanner
     * @throws IllegalStateException if this scanner is closed
     */
    public Scanner skip(String pattern) {
        return skip(patternCache.forName(pattern));
    }

    // Convenience methods for scanning primitives

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a boolean value using a case insensitive pattern
     * created from the string "true|false".  The scanner does not
     * advance past the input that matched.
     *
     * <p>
     *  如果此扫描程序的输入中的下一个标记可以使用从字符串"true | false"创建的不区分大小写的模式解释为布尔值,则返回true扫描程序不会超过匹配的输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         boolean value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextBoolean()  {
        return hasNext(boolPattern());
    }

    /**
     * Scans the next token of the input into a boolean value and returns
     * that value. This method will throw <code>InputMismatchException</code>
     * if the next token cannot be translated into a valid boolean value.
     * If the match is successful, the scanner advances past the input that
     * matched.
     *
     * <p>
     *  将输入的下一个标记扫描为布尔值并返回该值如果下一个标记无法转换为有效的布尔值,则此方法将抛出<code> InputMismatchException </code>如果匹配成功,则扫描仪前进输入匹配
     * 。
     * 
     * 
     * @return the boolean scanned from the input
     * @throws InputMismatchException if the next token is not a valid boolean
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean nextBoolean()  {
        clearCaches();
        return Boolean.parseBoolean(next(boolPattern()));
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a byte value in the default radix using the
     * {@link #nextByte} method. The scanner does not advance past any input.
     *
     * <p>
     * 如果此扫描程序输入中的下一个标记可以使用{@link #nextByte}方法在默认基数中解释为字节值,则返回true。扫描程序不超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         byte value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextByte() {
        return hasNextByte(defaultRadix);
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a byte value in the specified radix using the
     * {@link #nextByte} method. The scanner does not advance past any input.
     *
     * <p>
     *  如果此扫描程序输入中的下一个标记可以使用{@link #nextByte}方法解释为指定基数中的字节值,则返回true扫描程序不超过任何输入
     * 
     * 
     * @param radix the radix used to interpret the token as a byte value
     * @return true if and only if this scanner's next token is a valid
     *         byte value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextByte(int radix) {
        setRadix(radix);
        boolean result = hasNext(integerPattern());
        if (result) { // Cache it
            try {
                String s = (matcher.group(SIMPLE_GROUP_INDEX) == null) ?
                    processIntegerToken(hasNextResult) :
                    hasNextResult;
                typeCache = Byte.parseByte(s, radix);
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Scans the next token of the input as a <tt>byte</tt>.
     *
     * <p> An invocation of this method of the form
     * <tt>nextByte()</tt> behaves in exactly the same way as the
     * invocation <tt>nextByte(radix)</tt>, where <code>radix</code>
     * is the default radix of this scanner.
     *
     * <p>
     *  将输入的下一个令牌作为<tt>字节</tt>进行扫描
     * 
     *  <p>以<tt> nextByte()</tt>的形式调用此方法的行为与调用<tt> nextByte(radix)</tt>完全相同,其中<code> radix </code >是此扫描仪的默认基
     * 数。
     * 
     * 
     * @return the <tt>byte</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public byte nextByte() {
         return nextByte(defaultRadix);
    }

    /**
     * Scans the next token of the input as a <tt>byte</tt>.
     * This method will throw <code>InputMismatchException</code>
     * if the next token cannot be translated into a valid byte value as
     * described below. If the translation is successful, the scanner advances
     * past the input that matched.
     *
     * <p> If the next token matches the <a
     * href="#Integer-regex"><i>Integer</i></a> regular expression defined
     * above then the token is converted into a <tt>byte</tt> value as if by
     * removing all locale specific prefixes, group separators, and locale
     * specific suffixes, then mapping non-ASCII digits into ASCII
     * digits via {@link Character#digit Character.digit}, prepending a
     * negative sign (-) if the locale specific negative prefixes and suffixes
     * were present, and passing the resulting string to
     * {@link Byte#parseByte(String, int) Byte.parseByte} with the
     * specified radix.
     *
     * <p>
     * 将输入的下一个令牌作为<tt>字节进行扫描</tt>如果下一个令牌无法转换为有效的字节值,则此方法将抛出<code> InputMismatchException </code>,如下所述如果翻译成功,
     * 扫描仪前进超过匹配的输入。
     * 
     * <p>如果下一个令牌与上面定义的<a href=\"#Integer-regex\"> <i> Integer </i> </a>正则表达式匹配,则令牌将转换为<tt>字节</tt >值,如果删除所有特
     * 定于语言环境的前缀,组分隔符和区域设置特定的后缀,则通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,如果语言环境特定的否定前缀和后
     * 缀,并将结果字符串传递给{@link Byte#parseByte(String,int)ByteparseByte}和指定的基数。
     * 
     * 
     * @param radix the radix used to interpret the token as a byte value
     * @return the <tt>byte</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public byte nextByte(int radix) {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof Byte)
            && this.radix == radix) {
            byte val = ((Byte)typeCache).byteValue();
            useTypeCache();
            return val;
        }
        setRadix(radix);
        clearCaches();
        // Search for next byte
        try {
            String s = next(integerPattern());
            if (matcher.group(SIMPLE_GROUP_INDEX) == null)
                s = processIntegerToken(s);
            return Byte.parseByte(s, radix);
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a short value in the default radix using the
     * {@link #nextShort} method. The scanner does not advance past any input.
     *
     * <p>
     *  如果此扫描程序输入中的下一个标记可以使用{@link #nextShort}方法在默认基数中解释为短值,则返回true扫描程序不会超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         short value in the default radix
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextShort() {
        return hasNextShort(defaultRadix);
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a short value in the specified radix using the
     * {@link #nextShort} method. The scanner does not advance past any input.
     *
     * <p>
     * 如果此扫描程序输入中的下一个标记可以使用{@link #nextShort}方法解释为指定基数中的短值,则返回true扫描程序不会超过任何输入
     * 
     * 
     * @param radix the radix used to interpret the token as a short value
     * @return true if and only if this scanner's next token is a valid
     *         short value in the specified radix
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextShort(int radix) {
        setRadix(radix);
        boolean result = hasNext(integerPattern());
        if (result) { // Cache it
            try {
                String s = (matcher.group(SIMPLE_GROUP_INDEX) == null) ?
                    processIntegerToken(hasNextResult) :
                    hasNextResult;
                typeCache = Short.parseShort(s, radix);
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Scans the next token of the input as a <tt>short</tt>.
     *
     * <p> An invocation of this method of the form
     * <tt>nextShort()</tt> behaves in exactly the same way as the
     * invocation <tt>nextShort(radix)</tt>, where <code>radix</code>
     * is the default radix of this scanner.
     *
     * <p>
     *  将输入的下一个令牌作为<tt> short </tt>进行扫描
     * 
     *  <p>以<tt> nextShort()</tt>的形式调用此方法的行为与调用<tt> nextShort(radix)</tt>完全相同,其中<code> radix </code >是此扫描仪的默
     * 认基数。
     * 
     * 
     * @return the <tt>short</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public short nextShort() {
        return nextShort(defaultRadix);
    }

    /**
     * Scans the next token of the input as a <tt>short</tt>.
     * This method will throw <code>InputMismatchException</code>
     * if the next token cannot be translated into a valid short value as
     * described below. If the translation is successful, the scanner advances
     * past the input that matched.
     *
     * <p> If the next token matches the <a
     * href="#Integer-regex"><i>Integer</i></a> regular expression defined
     * above then the token is converted into a <tt>short</tt> value as if by
     * removing all locale specific prefixes, group separators, and locale
     * specific suffixes, then mapping non-ASCII digits into ASCII
     * digits via {@link Character#digit Character.digit}, prepending a
     * negative sign (-) if the locale specific negative prefixes and suffixes
     * were present, and passing the resulting string to
     * {@link Short#parseShort(String, int) Short.parseShort} with the
     * specified radix.
     *
     * <p>
     *  将输入的下一个标记作为<tt> short </tt>进行扫描如果下一个标记无法转换为有效的短值,则此方法将抛出<code> InputMismatchException </code>如果翻译成功,
     * 扫描仪前进超过匹配的输入。
     * 
     * <p>如果下一个令牌与上面定义的<a href=\"#Integer-regex\"> <i> Integer </i> </a>正则表达式匹配,则令牌将转换为<tt> short </tt >值,如果
     * 删除所有特定于语言环境的前缀,组分隔符和区域设置特定的后缀,则通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,如果语言环境特定的否
     * 定前缀和后缀,并将结果字符串传递给具有指定基数的{@link Short#parseShort(String,int)ShortparseShort}。
     * 
     * 
     * @param radix the radix used to interpret the token as a short value
     * @return the <tt>short</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public short nextShort(int radix) {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof Short)
            && this.radix == radix) {
            short val = ((Short)typeCache).shortValue();
            useTypeCache();
            return val;
        }
        setRadix(radix);
        clearCaches();
        // Search for next short
        try {
            String s = next(integerPattern());
            if (matcher.group(SIMPLE_GROUP_INDEX) == null)
                s = processIntegerToken(s);
            return Short.parseShort(s, radix);
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as an int value in the default radix using the
     * {@link #nextInt} method. The scanner does not advance past any input.
     *
     * <p>
     *  如果此扫描程序输入中的下一个标记可以使用{@link #nextInt}方法在默认基数中解释为int值,则返回true扫描程序不会超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         int value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextInt() {
        return hasNextInt(defaultRadix);
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as an int value in the specified radix using the
     * {@link #nextInt} method. The scanner does not advance past any input.
     *
     * <p>
     * 如果此扫描程序的输入中的下一个标记可以使用{@link #nextInt}方法解释为指定基数中的int值,则返回true扫描程序不超过任何输入
     * 
     * 
     * @param radix the radix used to interpret the token as an int value
     * @return true if and only if this scanner's next token is a valid
     *         int value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextInt(int radix) {
        setRadix(radix);
        boolean result = hasNext(integerPattern());
        if (result) { // Cache it
            try {
                String s = (matcher.group(SIMPLE_GROUP_INDEX) == null) ?
                    processIntegerToken(hasNextResult) :
                    hasNextResult;
                typeCache = Integer.parseInt(s, radix);
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * The integer token must be stripped of prefixes, group separators,
     * and suffixes, non ascii digits must be converted into ascii digits
     * before parse will accept it.
     * <p>
     *  整数标记必须去除前缀,组分隔符和后缀,非ASCII数字必须在解析接受之前转换为ASCII数字
     * 
     */
    private String processIntegerToken(String token) {
        String result = token.replaceAll(""+groupSeparator, "");
        boolean isNegative = false;
        int preLen = negativePrefix.length();
        if ((preLen > 0) && result.startsWith(negativePrefix)) {
            isNegative = true;
            result = result.substring(preLen);
        }
        int sufLen = negativeSuffix.length();
        if ((sufLen > 0) && result.endsWith(negativeSuffix)) {
            isNegative = true;
            result = result.substring(result.length() - sufLen,
                                      result.length());
        }
        if (isNegative)
            result = "-" + result;
        return result;
    }

    /**
     * Scans the next token of the input as an <tt>int</tt>.
     *
     * <p> An invocation of this method of the form
     * <tt>nextInt()</tt> behaves in exactly the same way as the
     * invocation <tt>nextInt(radix)</tt>, where <code>radix</code>
     * is the default radix of this scanner.
     *
     * <p>
     *  将输入的下一个标记作为<tt> int </tt>进行扫描
     * 
     *  <p>调用<tt> nextInt()</tt>形式的方法与调用<tt> nextInt(radix)</tt>的方式完全相同,其中<code> radix </code >是此扫描仪的默认基数
     * 
     * 
     * @return the <tt>int</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public int nextInt() {
        return nextInt(defaultRadix);
    }

    /**
     * Scans the next token of the input as an <tt>int</tt>.
     * This method will throw <code>InputMismatchException</code>
     * if the next token cannot be translated into a valid int value as
     * described below. If the translation is successful, the scanner advances
     * past the input that matched.
     *
     * <p> If the next token matches the <a
     * href="#Integer-regex"><i>Integer</i></a> regular expression defined
     * above then the token is converted into an <tt>int</tt> value as if by
     * removing all locale specific prefixes, group separators, and locale
     * specific suffixes, then mapping non-ASCII digits into ASCII
     * digits via {@link Character#digit Character.digit}, prepending a
     * negative sign (-) if the locale specific negative prefixes and suffixes
     * were present, and passing the resulting string to
     * {@link Integer#parseInt(String, int) Integer.parseInt} with the
     * specified radix.
     *
     * <p>
     * 将输入的下一个标记作为<tt> int </tt>进行扫描如果下一个标记无法转换为有效的int值,则此方法将抛出<code> InputMismatchException </code>,如下所述如果翻
     * 译成功,扫描仪前进超过匹配的输入。
     * 
     * <p>如果下一个令牌与上面定义的<a href=\"#Integer-regex\"> <i> Integer </i> </a>正则表达式匹配,则令牌将转换为<tt> int </tt >值,如果删除
     * 所有特定于语言环境的前缀,组分隔符和区域设置特定的后缀,则通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,如果语言环境特定的否定前
     * 缀和后缀,并将结果字符串传递给具有指定基数的{@link Integer#parseInt(String,int)IntegerparseInt}。
     * 
     * 
     * @param radix the radix used to interpret the token as an int value
     * @return the <tt>int</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public int nextInt(int radix) {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof Integer)
            && this.radix == radix) {
            int val = ((Integer)typeCache).intValue();
            useTypeCache();
            return val;
        }
        setRadix(radix);
        clearCaches();
        // Search for next int
        try {
            String s = next(integerPattern());
            if (matcher.group(SIMPLE_GROUP_INDEX) == null)
                s = processIntegerToken(s);
            return Integer.parseInt(s, radix);
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a long value in the default radix using the
     * {@link #nextLong} method. The scanner does not advance past any input.
     *
     * <p>
     *  如果此扫描程序输入中的下一个标记可以使用{@link #nextLong}方法在默认基数中解释为长整型值,则返回true扫描程序不超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         long value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextLong() {
        return hasNextLong(defaultRadix);
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a long value in the specified radix using the
     * {@link #nextLong} method. The scanner does not advance past any input.
     *
     * <p>
     * 如果此扫描程序输入中的下一个标记可以使用{@link #nextLong}方法在指定基数中解释为长整型值,则返回true扫描程序不会超过任何输入
     * 
     * 
     * @param radix the radix used to interpret the token as a long value
     * @return true if and only if this scanner's next token is a valid
     *         long value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextLong(int radix) {
        setRadix(radix);
        boolean result = hasNext(integerPattern());
        if (result) { // Cache it
            try {
                String s = (matcher.group(SIMPLE_GROUP_INDEX) == null) ?
                    processIntegerToken(hasNextResult) :
                    hasNextResult;
                typeCache = Long.parseLong(s, radix);
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Scans the next token of the input as a <tt>long</tt>.
     *
     * <p> An invocation of this method of the form
     * <tt>nextLong()</tt> behaves in exactly the same way as the
     * invocation <tt>nextLong(radix)</tt>, where <code>radix</code>
     * is the default radix of this scanner.
     *
     * <p>
     *  将输入的下一个标记扫描为<tt>长</tt>
     * 
     *  <p>以<tt> nextLong()</tt>的形式调用此方法的行为与调用<tt> nextLong(radix)</tt>完全相同,其中<code> radix </code >是此扫描仪的默认基
     * 数。
     * 
     * 
     * @return the <tt>long</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public long nextLong() {
        return nextLong(defaultRadix);
    }

    /**
     * Scans the next token of the input as a <tt>long</tt>.
     * This method will throw <code>InputMismatchException</code>
     * if the next token cannot be translated into a valid long value as
     * described below. If the translation is successful, the scanner advances
     * past the input that matched.
     *
     * <p> If the next token matches the <a
     * href="#Integer-regex"><i>Integer</i></a> regular expression defined
     * above then the token is converted into a <tt>long</tt> value as if by
     * removing all locale specific prefixes, group separators, and locale
     * specific suffixes, then mapping non-ASCII digits into ASCII
     * digits via {@link Character#digit Character.digit}, prepending a
     * negative sign (-) if the locale specific negative prefixes and suffixes
     * were present, and passing the resulting string to
     * {@link Long#parseLong(String, int) Long.parseLong} with the
     * specified radix.
     *
     * <p>
     *  将输入的下一个标记作为<tt> long </tt>进行扫描如果下一个标记无法转换为有效的长整型值,则此方法将抛出<code> InputMismatchException </code>如果翻译成功
     * ,扫描仪前进超过匹配的输入。
     * 
     * <p>如果下一个令牌与上面定义的<a href=\"#Integer-regex\"> <i> Integer </i> </a>正则表达式匹配,则令牌将转换为<tt> long </tt >值,如果删
     * 除所有特定于语言环境的前缀,组分隔符和区域设置特定的后缀,则通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,如果语言环境特定的否定
     * 前缀和后缀,并将结果字符串传递给具有指定基数的{@link Long#parseLong(String,int)LongparseLong}。
     * 
     * 
     * @param radix the radix used to interpret the token as an int value
     * @return the <tt>long</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public long nextLong(int radix) {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof Long)
            && this.radix == radix) {
            long val = ((Long)typeCache).longValue();
            useTypeCache();
            return val;
        }
        setRadix(radix);
        clearCaches();
        try {
            String s = next(integerPattern());
            if (matcher.group(SIMPLE_GROUP_INDEX) == null)
                s = processIntegerToken(s);
            return Long.parseLong(s, radix);
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    /**
     * The float token must be stripped of prefixes, group separators,
     * and suffixes, non ascii digits must be converted into ascii digits
     * before parseFloat will accept it.
     *
     * If there are non-ascii digits in the token these digits must
     * be processed before the token is passed to parseFloat.
     * <p>
     *  浮点代码必须删除前缀,组分隔符和后缀,非ASCII数字必须在parseFloat接受之前转换为ascii数字
     * 
     * 如果令牌中有非ASCII数字,则这些数字必须在令牌传递给parseFloat之前进行处理
     * 
     */
    private String processFloatToken(String token) {
        String result = token.replaceAll(groupSeparator, "");
        if (!decimalSeparator.equals("\\."))
            result = result.replaceAll(decimalSeparator, ".");
        boolean isNegative = false;
        int preLen = negativePrefix.length();
        if ((preLen > 0) && result.startsWith(negativePrefix)) {
            isNegative = true;
            result = result.substring(preLen);
        }
        int sufLen = negativeSuffix.length();
        if ((sufLen > 0) && result.endsWith(negativeSuffix)) {
            isNegative = true;
            result = result.substring(result.length() - sufLen,
                                      result.length());
        }
        if (result.equals(nanString))
            result = "NaN";
        if (result.equals(infinityString))
            result = "Infinity";
        if (isNegative)
            result = "-" + result;

        // Translate non-ASCII digits
        Matcher m = NON_ASCII_DIGIT.matcher(result);
        if (m.find()) {
            StringBuilder inASCII = new StringBuilder();
            for (int i=0; i<result.length(); i++) {
                char nextChar = result.charAt(i);
                if (Character.isDigit(nextChar)) {
                    int d = Character.digit(nextChar, 10);
                    if (d != -1)
                        inASCII.append(d);
                    else
                        inASCII.append(nextChar);
                } else {
                    inASCII.append(nextChar);
                }
            }
            result = inASCII.toString();
        }

        return result;
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a float value using the {@link #nextFloat}
     * method. The scanner does not advance past any input.
     *
     * <p>
     *  如果此扫描程序输入中的下一个标记可以使用{@link #nextFloat}方法解释为浮点值,则返回true扫描程序不超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         float value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextFloat() {
        setRadix(10);
        boolean result = hasNext(floatPattern());
        if (result) { // Cache it
            try {
                String s = processFloatToken(hasNextResult);
                typeCache = Float.valueOf(Float.parseFloat(s));
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Scans the next token of the input as a <tt>float</tt>.
     * This method will throw <code>InputMismatchException</code>
     * if the next token cannot be translated into a valid float value as
     * described below. If the translation is successful, the scanner advances
     * past the input that matched.
     *
     * <p> If the next token matches the <a
     * href="#Float-regex"><i>Float</i></a> regular expression defined above
     * then the token is converted into a <tt>float</tt> value as if by
     * removing all locale specific prefixes, group separators, and locale
     * specific suffixes, then mapping non-ASCII digits into ASCII
     * digits via {@link Character#digit Character.digit}, prepending a
     * negative sign (-) if the locale specific negative prefixes and suffixes
     * were present, and passing the resulting string to
     * {@link Float#parseFloat Float.parseFloat}. If the token matches
     * the localized NaN or infinity strings, then either "Nan" or "Infinity"
     * is passed to {@link Float#parseFloat(String) Float.parseFloat} as
     * appropriate.
     *
     * <p>
     *  将输入的下一个令牌作为<tt> float </tt>进行扫描如果下一个令牌无法转换为有效的浮点值,则此方法将抛出<code> InputMismatchException </code>如果翻译成功
     * ,扫描仪前进超过匹配的输入。
     * 
     * <p>如果下一个令牌与上面定义的<a href=\"#Float-regex\"> <i> Float </i> </a>正则表达式匹配,则令牌将转换为<tt> float </tt >值,如果删除所有
     * 特定于语言环境的前缀,组分隔符和区域设置特定的后缀,则通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,如果语言环境特定的否定前缀和
     * 后缀,并将结果字符串传递给{@link Float#parseFloat FloatparseFloat}如果令牌匹配本地化的NaN或无穷大字符串,那么"Nan"或"Infinity"将传递到{@link Float#parseFloat String)FloatparseFloat}
     * 。
     * 
     * 
     * @return the <tt>float</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Float</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public float nextFloat() {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof Float)) {
            float val = ((Float)typeCache).floatValue();
            useTypeCache();
            return val;
        }
        setRadix(10);
        clearCaches();
        try {
            return Float.parseFloat(processFloatToken(next(floatPattern())));
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a double value using the {@link #nextDouble}
     * method. The scanner does not advance past any input.
     *
     * <p>
     * 如果此扫描程序输入中的下一个标记可以使用{@link #nextDouble}方法解释为双精度值,则返回true扫描程序不超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         double value
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextDouble() {
        setRadix(10);
        boolean result = hasNext(floatPattern());
        if (result) { // Cache it
            try {
                String s = processFloatToken(hasNextResult);
                typeCache = Double.valueOf(Double.parseDouble(s));
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Scans the next token of the input as a <tt>double</tt>.
     * This method will throw <code>InputMismatchException</code>
     * if the next token cannot be translated into a valid double value.
     * If the translation is successful, the scanner advances past the input
     * that matched.
     *
     * <p> If the next token matches the <a
     * href="#Float-regex"><i>Float</i></a> regular expression defined above
     * then the token is converted into a <tt>double</tt> value as if by
     * removing all locale specific prefixes, group separators, and locale
     * specific suffixes, then mapping non-ASCII digits into ASCII
     * digits via {@link Character#digit Character.digit}, prepending a
     * negative sign (-) if the locale specific negative prefixes and suffixes
     * were present, and passing the resulting string to
     * {@link Double#parseDouble Double.parseDouble}. If the token matches
     * the localized NaN or infinity strings, then either "Nan" or "Infinity"
     * is passed to {@link Double#parseDouble(String) Double.parseDouble} as
     * appropriate.
     *
     * <p>
     *  将输入的下一个标记作为<tt> double </tt>进行扫描如果下一个标记无法转换为有效的双精度值,则此方法将抛出<code> InputMismatchException </code>如果翻译
     * 成功,过去匹配的输入。
     * 
     * <p>如果下一个令牌与上面定义的<a href=\"#Float-regex\"> <i> Float </i> </a>正则表达式匹配,则令牌将转换为<tt> double </tt >值,如果删除所
     * 有特定于语言环境的前缀,组分隔符和区域设置特定的后缀,则通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,如果语言环境特定的否定前缀
     * 和后缀,并将结果字符串传递给{@link Double#parseDouble DoubleparseDouble}如果令牌匹配本地化的NaN或无穷大字符串,那么"Nan"或"Infinity"将传递到
     * {@link Double#parseDouble String)DoubleparseDouble}。
     * 
     * 
     * @return the <tt>double</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Float</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if the input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public double nextDouble() {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof Double)) {
            double val = ((Double)typeCache).doubleValue();
            useTypeCache();
            return val;
        }
        setRadix(10);
        clearCaches();
        // Search for next float
        try {
            return Double.parseDouble(processFloatToken(next(floatPattern())));
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    // Convenience methods for scanning multi precision numbers

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a <code>BigInteger</code> in the default radix using the
     * {@link #nextBigInteger} method. The scanner does not advance past any
     * input.
     *
     * <p>
     * 如果此扫描程序输入中的下一个标记可以使用{@link #nextBigInteger}方法在默认基数中解释为<code> BigInteger </code>,则返回true扫描程序不会超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         <code>BigInteger</code>
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextBigInteger() {
        return hasNextBigInteger(defaultRadix);
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a <code>BigInteger</code> in the specified radix using
     * the {@link #nextBigInteger} method. The scanner does not advance past
     * any input.
     *
     * <p>
     *  如果此扫描程序输入中的下一个标记可以使用{@link #nextBigInteger}方法解释为指定基数中的<code> BigInteger </code>,则返回true扫描程序不会超过任何输入。
     * 
     * 
     * @param radix the radix used to interpret the token as an integer
     * @return true if and only if this scanner's next token is a valid
     *         <code>BigInteger</code>
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextBigInteger(int radix) {
        setRadix(radix);
        boolean result = hasNext(integerPattern());
        if (result) { // Cache it
            try {
                String s = (matcher.group(SIMPLE_GROUP_INDEX) == null) ?
                    processIntegerToken(hasNextResult) :
                    hasNextResult;
                typeCache = new BigInteger(s, radix);
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Scans the next token of the input as a {@link java.math.BigInteger
     * BigInteger}.
     *
     * <p> An invocation of this method of the form
     * <tt>nextBigInteger()</tt> behaves in exactly the same way as the
     * invocation <tt>nextBigInteger(radix)</tt>, where <code>radix</code>
     * is the default radix of this scanner.
     *
     * <p>
     *  将输入的下一个标记作为{@link javamathBigInteger BigInteger}
     * 
     *  <p>以<tt> nextBigInteger()</tt>的形式调用此方法的行为与调用<tt> nextBigInteger(radix)</tt>完全相同,其中<code> radix </code >
     * 是此扫描仪的默认基数。
     * 
     * 
     * @return the <tt>BigInteger</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if the input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public BigInteger nextBigInteger() {
        return nextBigInteger(defaultRadix);
    }

    /**
     * Scans the next token of the input as a {@link java.math.BigInteger
     * BigInteger}.
     *
     * <p> If the next token matches the <a
     * href="#Integer-regex"><i>Integer</i></a> regular expression defined
     * above then the token is converted into a <tt>BigInteger</tt> value as if
     * by removing all group separators, mapping non-ASCII digits into ASCII
     * digits via the {@link Character#digit Character.digit}, and passing the
     * resulting string to the {@link
     * java.math.BigInteger#BigInteger(java.lang.String)
     * BigInteger(String, int)} constructor with the specified radix.
     *
     * <p>
     * 将输入的下一个标记作为{@link javamathBigInteger BigInteger}
     * 
     *  <p>如果下一个令牌与上面定义的<a href=\"#Integer-regex\"> <i> Integer </i> </a>正则表达式匹配,则令牌将转换为<tt> BigInteger </tt >
     * 值,如同通过删除所有组分隔符,通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,并将生成的字符串传递给{@link javamathBigInteger#BigInteger(javalangString)BigInteger )}
     * 构造函数与指定的基数。
     * 
     * 
     * @param radix the radix used to interpret the token
     * @return the <tt>BigInteger</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Integer</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if the input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public BigInteger nextBigInteger(int radix) {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof BigInteger)
            && this.radix == radix) {
            BigInteger val = (BigInteger)typeCache;
            useTypeCache();
            return val;
        }
        setRadix(radix);
        clearCaches();
        // Search for next int
        try {
            String s = next(integerPattern());
            if (matcher.group(SIMPLE_GROUP_INDEX) == null)
                s = processIntegerToken(s);
            return new BigInteger(s, radix);
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    /**
     * Returns true if the next token in this scanner's input can be
     * interpreted as a <code>BigDecimal</code> using the
     * {@link #nextBigDecimal} method. The scanner does not advance past any
     * input.
     *
     * <p>
     *  如果此扫描程序输入中的下一个标记可以使用{@link #nextBigDecimal}方法解释为<code> BigDecimal </code>,则返回true扫描程序不会超过任何输入
     * 
     * 
     * @return true if and only if this scanner's next token is a valid
     *         <code>BigDecimal</code>
     * @throws IllegalStateException if this scanner is closed
     */
    public boolean hasNextBigDecimal() {
        setRadix(10);
        boolean result = hasNext(decimalPattern());
        if (result) { // Cache it
            try {
                String s = processFloatToken(hasNextResult);
                typeCache = new BigDecimal(s);
            } catch (NumberFormatException nfe) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Scans the next token of the input as a {@link java.math.BigDecimal
     * BigDecimal}.
     *
     * <p> If the next token matches the <a
     * href="#Decimal-regex"><i>Decimal</i></a> regular expression defined
     * above then the token is converted into a <tt>BigDecimal</tt> value as if
     * by removing all group separators, mapping non-ASCII digits into ASCII
     * digits via the {@link Character#digit Character.digit}, and passing the
     * resulting string to the {@link
     * java.math.BigDecimal#BigDecimal(java.lang.String) BigDecimal(String)}
     * constructor.
     *
     * <p>
     * 将输入的下一个标记扫描为{@link javamathBigDecimal BigDecimal}
     * 
     *  <p>如果下一个令牌与上面定义的<a href=\"#Decimal-regex\"> <i> Decimal </i> </a>正则表达式匹配,则令牌将转换为<tt> BigDecimal </tt >
     * 值,如同通过删除所有组分隔符,通过{@link Character#digit Characterdigit}将非ASCII数字映射为ASCII数字,并将结果字符串传递到{@link javamathBigDecimal#BigDecimal(javalangString)BigDecimal(String)}
     * 构造函数。
     * 
     * 
     * @return the <tt>BigDecimal</tt> scanned from the input
     * @throws InputMismatchException
     *         if the next token does not match the <i>Decimal</i>
     *         regular expression, or is out of range
     * @throws NoSuchElementException if the input is exhausted
     * @throws IllegalStateException if this scanner is closed
     */
    public BigDecimal nextBigDecimal() {
        // Check cached result
        if ((typeCache != null) && (typeCache instanceof BigDecimal)) {
            BigDecimal val = (BigDecimal)typeCache;
            useTypeCache();
            return val;
        }
        setRadix(10);
        clearCaches();
        // Search for next float
        try {
            String s = processFloatToken(next(decimalPattern()));
            return new BigDecimal(s);
        } catch (NumberFormatException nfe) {
            position = matcher.start(); // don't skip bad token
            throw new InputMismatchException(nfe.getMessage());
        }
    }

    /**
     * Resets this scanner.
     *
     * <p> Resetting a scanner discards all of its explicit state
     * information which may have been changed by invocations of {@link
     * #useDelimiter}, {@link #useLocale}, or {@link #useRadix}.
     *
     * <p> An invocation of this method of the form
     * <tt>scanner.reset()</tt> behaves in exactly the same way as the
     * invocation
     *
     * <blockquote><pre>{@code
     *   scanner.useDelimiter("\\p{javaWhitespace}+")
     *          .useLocale(Locale.getDefault(Locale.Category.FORMAT))
     *          .useRadix(10);
     * }</pre></blockquote>
     *
     * <p>
     *  重置此扫描仪
     * 
     *  <p>重置扫描程序会丢弃所有通过调用{@link #useDelimiter},{@link #useLocale}或{@link #useRadix}更改的所有显式状态信息。
     * 
     * <p>以<tt> scannerreset()</tt>的形式调用此方法的行为与调用的方式完全相同
     * 
     * @return this scanner
     *
     * @since 1.6
     */
    public Scanner reset() {
        delimPattern = WHITESPACE_PATTERN;
        useLocale(Locale.getDefault(Locale.Category.FORMAT));
        useRadix(10);
        clearCaches();
        return this;
    }
}
