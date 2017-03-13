/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权所有Taligent,Inc 1996,1997  - 保留所有权利(C)版权所有IBM Corp 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc拥有版权和所有权。
 * 这些资料根据Taligent和Sun之间的许可协议的条款提供此技术受多个美国和国际专利保护Taligent是Taligent的注册商标。Taligent是Taligent的注册商标。
 * 
 */

package java.text;

import java.io.InvalidObjectException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * <code>MessageFormat</code> provides a means to produce concatenated
 * messages in a language-neutral way. Use this to construct messages
 * displayed for end users.
 *
 * <p>
 * <code>MessageFormat</code> takes a set of objects, formats them, then
 * inserts the formatted strings into the pattern at the appropriate places.
 *
 * <p>
 * <strong>Note:</strong>
 * <code>MessageFormat</code> differs from the other <code>Format</code>
 * classes in that you create a <code>MessageFormat</code> object with one
 * of its constructors (not with a <code>getInstance</code> style factory
 * method). The factory methods aren't necessary because <code>MessageFormat</code>
 * itself doesn't implement locale specific behavior. Any locale specific
 * behavior is defined by the pattern that you provide as well as the
 * subformats used for inserted arguments.
 *
 * <h3><a name="patterns">Patterns and Their Interpretation</a></h3>
 *
 * <code>MessageFormat</code> uses patterns of the following form:
 * <blockquote><pre>
 * <i>MessageFormatPattern:</i>
 *         <i>String</i>
 *         <i>MessageFormatPattern</i> <i>FormatElement</i> <i>String</i>
 *
 * <i>FormatElement:</i>
 *         { <i>ArgumentIndex</i> }
 *         { <i>ArgumentIndex</i> , <i>FormatType</i> }
 *         { <i>ArgumentIndex</i> , <i>FormatType</i> , <i>FormatStyle</i> }
 *
 * <i>FormatType: one of </i>
 *         number date time choice
 *
 * <i>FormatStyle:</i>
 *         short
 *         medium
 *         long
 *         full
 *         integer
 *         currency
 *         percent
 *         <i>SubformatPattern</i>
 * </pre></blockquote>
 *
 * <p>Within a <i>String</i>, a pair of single quotes can be used to
 * quote any arbitrary characters except single quotes. For example,
 * pattern string <code>"'{0}'"</code> represents string
 * <code>"{0}"</code>, not a <i>FormatElement</i>. A single quote itself
 * must be represented by doubled single quotes {@code ''} throughout a
 * <i>String</i>.  For example, pattern string <code>"'{''}'"</code> is
 * interpreted as a sequence of <code>'{</code> (start of quoting and a
 * left curly brace), <code>''</code> (a single quote), and
 * <code>}'</code> (a right curly brace and end of quoting),
 * <em>not</em> <code>'{'</code> and <code>'}'</code> (quoted left and
 * right curly braces): representing string <code>"{'}"</code>,
 * <em>not</em> <code>"{}"</code>.
 *
 * <p>A <i>SubformatPattern</i> is interpreted by its corresponding
 * subformat, and subformat-dependent pattern rules apply. For example,
 * pattern string <code>"{1,number,<u>$'#',##</u>}"</code>
 * (<i>SubformatPattern</i> with underline) will produce a number format
 * with the pound-sign quoted, with a result such as: {@code
 * "$#31,45"}. Refer to each {@code Format} subclass documentation for
 * details.
 *
 * <p>Any unmatched quote is treated as closed at the end of the given
 * pattern. For example, pattern string {@code "'{0}"} is treated as
 * pattern {@code "'{0}'"}.
 *
 * <p>Any curly braces within an unquoted pattern must be balanced. For
 * example, <code>"ab {0} de"</code> and <code>"ab '}' de"</code> are
 * valid patterns, but <code>"ab {0'}' de"</code>, <code>"ab } de"</code>
 * and <code>"''{''"</code> are not.
 *
 * <dl><dt><b>Warning:</b><dd>The rules for using quotes within message
 * format patterns unfortunately have shown to be somewhat confusing.
 * In particular, it isn't always obvious to localizers whether single
 * quotes need to be doubled or not. Make sure to inform localizers about
 * the rules, and tell them (for example, by using comments in resource
 * bundle source files) which strings will be processed by {@code MessageFormat}.
 * Note that localizers may need to use single quotes in translated
 * strings where the original version doesn't have them.
 * </dl>
 * <p>
 * The <i>ArgumentIndex</i> value is a non-negative integer written
 * using the digits {@code '0'} through {@code '9'}, and represents an index into the
 * {@code arguments} array passed to the {@code format} methods
 * or the result array returned by the {@code parse} methods.
 * <p>
 * The <i>FormatType</i> and <i>FormatStyle</i> values are used to create
 * a {@code Format} instance for the format element. The following
 * table shows how the values map to {@code Format} instances. Combinations not
 * shown in the table are illegal. A <i>SubformatPattern</i> must
 * be a valid pattern string for the {@code Format} subclass used.
 *
 * <table border=1 summary="Shows how FormatType and FormatStyle values map to Format instances">
 *    <tr>
 *       <th id="ft" class="TableHeadingColor">FormatType
 *       <th id="fs" class="TableHeadingColor">FormatStyle
 *       <th id="sc" class="TableHeadingColor">Subformat Created
 *    <tr>
 *       <td headers="ft"><i>(none)</i>
 *       <td headers="fs"><i>(none)</i>
 *       <td headers="sc"><code>null</code>
 *    <tr>
 *       <td headers="ft" rowspan=5><code>number</code>
 *       <td headers="fs"><i>(none)</i>
 *       <td headers="sc">{@link NumberFormat#getInstance(Locale) NumberFormat.getInstance}{@code (getLocale())}
 *    <tr>
 *       <td headers="fs"><code>integer</code>
 *       <td headers="sc">{@link NumberFormat#getIntegerInstance(Locale) NumberFormat.getIntegerInstance}{@code (getLocale())}
 *    <tr>
 *       <td headers="fs"><code>currency</code>
 *       <td headers="sc">{@link NumberFormat#getCurrencyInstance(Locale) NumberFormat.getCurrencyInstance}{@code (getLocale())}
 *    <tr>
 *       <td headers="fs"><code>percent</code>
 *       <td headers="sc">{@link NumberFormat#getPercentInstance(Locale) NumberFormat.getPercentInstance}{@code (getLocale())}
 *    <tr>
 *       <td headers="fs"><i>SubformatPattern</i>
 *       <td headers="sc">{@code new} {@link DecimalFormat#DecimalFormat(String,DecimalFormatSymbols) DecimalFormat}{@code (subformatPattern,} {@link DecimalFormatSymbols#getInstance(Locale) DecimalFormatSymbols.getInstance}{@code (getLocale()))}
 *    <tr>
 *       <td headers="ft" rowspan=6><code>date</code>
 *       <td headers="fs"><i>(none)</i>
 *       <td headers="sc">{@link DateFormat#getDateInstance(int,Locale) DateFormat.getDateInstance}{@code (}{@link DateFormat#DEFAULT}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>short</code>
 *       <td headers="sc">{@link DateFormat#getDateInstance(int,Locale) DateFormat.getDateInstance}{@code (}{@link DateFormat#SHORT}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>medium</code>
 *       <td headers="sc">{@link DateFormat#getDateInstance(int,Locale) DateFormat.getDateInstance}{@code (}{@link DateFormat#DEFAULT}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>long</code>
 *       <td headers="sc">{@link DateFormat#getDateInstance(int,Locale) DateFormat.getDateInstance}{@code (}{@link DateFormat#LONG}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>full</code>
 *       <td headers="sc">{@link DateFormat#getDateInstance(int,Locale) DateFormat.getDateInstance}{@code (}{@link DateFormat#FULL}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><i>SubformatPattern</i>
 *       <td headers="sc">{@code new} {@link SimpleDateFormat#SimpleDateFormat(String,Locale) SimpleDateFormat}{@code (subformatPattern, getLocale())}
 *    <tr>
 *       <td headers="ft" rowspan=6><code>time</code>
 *       <td headers="fs"><i>(none)</i>
 *       <td headers="sc">{@link DateFormat#getTimeInstance(int,Locale) DateFormat.getTimeInstance}{@code (}{@link DateFormat#DEFAULT}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>short</code>
 *       <td headers="sc">{@link DateFormat#getTimeInstance(int,Locale) DateFormat.getTimeInstance}{@code (}{@link DateFormat#SHORT}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>medium</code>
 *       <td headers="sc">{@link DateFormat#getTimeInstance(int,Locale) DateFormat.getTimeInstance}{@code (}{@link DateFormat#DEFAULT}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>long</code>
 *       <td headers="sc">{@link DateFormat#getTimeInstance(int,Locale) DateFormat.getTimeInstance}{@code (}{@link DateFormat#LONG}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><code>full</code>
 *       <td headers="sc">{@link DateFormat#getTimeInstance(int,Locale) DateFormat.getTimeInstance}{@code (}{@link DateFormat#FULL}{@code , getLocale())}
 *    <tr>
 *       <td headers="fs"><i>SubformatPattern</i>
 *       <td headers="sc">{@code new} {@link SimpleDateFormat#SimpleDateFormat(String,Locale) SimpleDateFormat}{@code (subformatPattern, getLocale())}
 *    <tr>
 *       <td headers="ft"><code>choice</code>
 *       <td headers="fs"><i>SubformatPattern</i>
 *       <td headers="sc">{@code new} {@link ChoiceFormat#ChoiceFormat(String) ChoiceFormat}{@code (subformatPattern)}
 * </table>
 *
 * <h4>Usage Information</h4>
 *
 * <p>
 * Here are some examples of usage.
 * In real internationalized programs, the message format pattern and other
 * static strings will, of course, be obtained from resource bundles.
 * Other parameters will be dynamically determined at runtime.
 * <p>
 * The first example uses the static method <code>MessageFormat.format</code>,
 * which internally creates a <code>MessageFormat</code> for one-time use:
 * <blockquote><pre>
 * int planet = 7;
 * String event = "a disturbance in the Force";
 *
 * String result = MessageFormat.format(
 *     "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
 *     planet, new Date(), event);
 * </pre></blockquote>
 * The output is:
 * <blockquote><pre>
 * At 12:30 PM on Jul 3, 2053, there was a disturbance in the Force on planet 7.
 * </pre></blockquote>
 *
 * <p>
 * The following example creates a <code>MessageFormat</code> instance that
 * can be used repeatedly:
 * <blockquote><pre>
 * int fileCount = 1273;
 * String diskName = "MyDisk";
 * Object[] testArgs = {new Long(fileCount), diskName};
 *
 * MessageFormat form = new MessageFormat(
 *     "The disk \"{1}\" contains {0} file(s).");
 *
 * System.out.println(form.format(testArgs));
 * </pre></blockquote>
 * The output with different values for <code>fileCount</code>:
 * <blockquote><pre>
 * The disk "MyDisk" contains 0 file(s).
 * The disk "MyDisk" contains 1 file(s).
 * The disk "MyDisk" contains 1,273 file(s).
 * </pre></blockquote>
 *
 * <p>
 * For more sophisticated patterns, you can use a <code>ChoiceFormat</code>
 * to produce correct forms for singular and plural:
 * <blockquote><pre>
 * MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
 * double[] filelimits = {0,1,2};
 * String[] filepart = {"no files","one file","{0,number} files"};
 * ChoiceFormat fileform = new ChoiceFormat(filelimits, filepart);
 * form.setFormatByArgumentIndex(0, fileform);
 *
 * int fileCount = 1273;
 * String diskName = "MyDisk";
 * Object[] testArgs = {new Long(fileCount), diskName};
 *
 * System.out.println(form.format(testArgs));
 * </pre></blockquote>
 * The output with different values for <code>fileCount</code>:
 * <blockquote><pre>
 * The disk "MyDisk" contains no files.
 * The disk "MyDisk" contains one file.
 * The disk "MyDisk" contains 1,273 files.
 * </pre></blockquote>
 *
 * <p>
 * You can create the <code>ChoiceFormat</code> programmatically, as in the
 * above example, or by using a pattern. See {@link ChoiceFormat}
 * for more information.
 * <blockquote><pre>{@code
 * form.applyPattern(
 *    "There {0,choice,0#are no files|1#is one file|1<are {0,number,integer} files}.");
 * }</pre></blockquote>
 *
 * <p>
 * <strong>Note:</strong> As we see above, the string produced
 * by a <code>ChoiceFormat</code> in <code>MessageFormat</code> is treated as special;
 * occurrences of '{' are used to indicate subformats, and cause recursion.
 * If you create both a <code>MessageFormat</code> and <code>ChoiceFormat</code>
 * programmatically (instead of using the string patterns), then be careful not to
 * produce a format that recurses on itself, which will cause an infinite loop.
 * <p>
 * When a single argument is parsed more than once in the string, the last match
 * will be the final result of the parsing.  For example,
 * <blockquote><pre>
 * MessageFormat mf = new MessageFormat("{0,number,#.##}, {0,number,#.#}");
 * Object[] objs = {new Double(3.1415)};
 * String result = mf.format( objs );
 * // result now equals "3.14, 3.1"
 * objs = null;
 * objs = mf.parse(result, new ParsePosition(0));
 * // objs now equals {new Double(3.1)}
 * </pre></blockquote>
 *
 * <p>
 * Likewise, parsing with a {@code MessageFormat} object using patterns containing
 * multiple occurrences of the same argument would return the last match.  For
 * example,
 * <blockquote><pre>
 * MessageFormat mf = new MessageFormat("{0}, {0}, {0}");
 * String forParsing = "x, y, z";
 * Object[] objs = mf.parse(forParsing, new ParsePosition(0));
 * // result now equals {new String("z")}
 * </pre></blockquote>
 *
 * <h4><a name="synchronization">Synchronization</a></h4>
 *
 * <p>
 * Message formats are not synchronized.
 * It is recommended to create separate format instances for each thread.
 * If multiple threads access a format concurrently, it must be synchronized
 * externally.
 *
 * <p>
 * <code> MessageFormat </code>提供了一种以语言中立的方式生成连接消息的方法使用此方法构造为最终用户显示的消息
 * 
 * <p>
 *  <code> MessageFormat </code>获取一组对象,格式化它们,然后将格式化的字符串插入到适当位置的模式中
 * 
 * <p>
 * <strong>注意：</strong> <code> MessageFormat </code>与其他<code> Format </code>类别不同之处在于,您可以使用其中一个构造函数创建<code>
 *  MessageFormat </code>不带有<code> getInstance </code> style工厂方法)工厂方法不是必需的,因为<code> MessageFormat </code>
 * 本身不实现特定于语言环境的行为任何特定于语言环境的行为都由模式提供以及用于插入参数的子格式。
 * 
 *  <h3> <a name=\"patterns\">模式及其解释</a> </h3>
 * 
 *  <code> MessageFormat </code>使用以下格式的模式：<blockquote> <pre> <i> MessageFormatPattern：</i> <i> String </i>
 *  <i> MessageFormatPattern </i> <i> FormatElement </i> <i>字符串</i>。
 * 
 * <i> FormatElement：</i> {<i> ArgumentIndex </i>} {<i> ArgumentIndex </i>,<i> FormatType </i> > FormatType </i>,<i> FormatStyle </i>}
 * 。
 * 
 *  <i> FormatType：</i>号码日期时间选择之一
 * 
 *  <i> FormatStyle：</i> short medium long整数货币百分比<i> SubformatPattern </i> </pre> </blockquote>
 * 
 * <p>在<i> String </i>中,可以使用一对单引号来引用除单引号之外的任何任意字符。
 * 例如,pattern string <code>"'{0}'"</code>在<i> String </i>中,单引号本身必须由双重单引号{@code''}表示。
 * </i> >例如,模式字符串<code>"'{''}'"</code>被解释为<code>'{</code>(引用开始和左大括号) '</code>(单引号)和<code>}'</code>(右大括号和
 * 引号结尾),<em>不是</em> <code>'{'</code >和<code>'}'</code>(引用左右花括号)：代表字符串<code>"{'}"</code>,<em> "</code>。
 * 例如,pattern string <code>"'{0}'"</code>在<i> String </i>中,单引号本身必须由双重单引号{@code''}表示。
 * 
 * <p> A <i> SubformatPattern </i>由其对应的子格式解释,并且与子格式相关的模式规则适用例如,模式字符串<code>"{1,number,<u> $'#',## </code> </code>(<i> SubformatPattern </i>下划线)将产生带有井号符号的数字格式,结果如：{@code"$#31,45" }
 * 有关详细信息,请参阅每个{@code Format}子类文档。
 * 
 *  <p>任何不匹配的引用在给定模式末尾被视为关闭例如,模式字符串{@code""{0}"}被视为模式{@code""{0}""
 * 
 *  <p>无引号模式中的任何花括号必须是平衡的例如,<code>"ab {0} de"</code>和<code>"ab'}"de"</code> <code>"ab {0'}"de"</code>,<code>
 * "ab} de"</code>和<code>"''{''"</code>。
 * 
 * <dl> <dt> <b>警告：</b> <dd>不幸的是,在消息格式模式中使用引号的规则显示有些混乱。
 * 特别地,对于本地化者来说,单引号是否需要是否双重确保通知本地化程序有关规则,并告诉他们(例如,通过使用资源包源文件中的注释)哪些字符串将由{@code MessageFormat}处理请注意,本地化可能
 * 需要使用单引号翻译的字符串,其中原始版本没有它们。
 * <dl> <dt> <b>警告：</b> <dd>不幸的是,在消息格式模式中使用引号的规则显示有些混乱。
 * </dl>
 * <p>
 * <i> ArgumentIndex </i>值是使用数字{@code'0'}至{@code'9'}写入的非负整数,表示传递到{@code arguments} {@code format}方法或{@code parse}
 * 方法返回的结果数组。
 * <p>
 *  <i> FormatType </i>和<i> FormatStyle </i>值用于为格式元素创建{@code Format}实例下表显示值如何映射到{@code Format}实例组合表中未显示的
 * 字符串非法A <i> SubformatPattern </i>必须是所使用的{@code Format}子类的有效模式字符串。
 * 
 * <table border=1 summary="Shows how FormatType and FormatStyle values map to Format instances">
 * <tr>
 *  <th id ="ft"class ="TableHeadingColor"> FormatType <th id ="fs"class ="TableHeadingColor"> FormatSty
 * le <th id ="sc"class ="TableHeadingColor"> Subformat Created。
 * <tr>
 * <td headers ="ft"> <i>(none)</i> <td headers ="fs"> <i>(none)</i> <td headers ="sc"> <code> null <代码>
 * 。
 * <tr>
 *  <td header ="ft"rowspan = 5> <code> number </code> <td headers ="fs"> <i>(none)</i> <td headers ="sc">
 *  {@ link NumberFormat# getInstance(Locale)NumberFormatgetInstance} {@ code(getLocale())}。
 * <tr>
 *  <td headers ="fs"> <code> integer </code> <td headers ="sc"> {@ link NumberFormat#getIntegerInstance(Locale)NumberFormatgetIntegerInstance}
 *  {@ code(getLocale())}。
 * <tr>
 *  <td headers ="fs"> <code> currency </code> <td header ="sc"> {@ link NumberFormat#getCurrencyInstance(Locale)NumberFormatgetCurrencyInstance}
 *  {@ code(getLocale())}。
 * <tr>
 *  <td headers ="fs"> <code> percent </code> <td headers ="sc"> {@ link NumberFormat#getPercentInstance(Locale)NumberFormatgetPercentInstance}
 *  {@ code(getLocale())}。
 * <tr>
 * <td headers ="fs"> <i> SubformatPattern </i> <td headers ="sc"> {@ code new} {@link DecimalFormat#DecimalFormat(String,DecimalFormatSymbols)DecimalFormat}
 *  {@ code(subformatPattern, @link DecimalFormatSymbols#getInstance(Locale)DecimalFormatSymbolsgetInstance}
 *  {@ code(getLocale()))}。
 * <tr>
 *  <td headers ="ft"rowspan = 6> <code> date </code> <td headers ="fs"> <i>(none)</i> <td headers ="sc">
 *  {@ link DateFormat# getDateInstance(int,Locale)DateFormatgetDateInstance} {@ code(} {@ link DateFormat#DEFAULT}
 *  {@ code,getLocale())}。
 * <tr>
 *  <td headers ="fs"> <code> short </code> <td headers ="sc"> {@ link DateFormat#getDateInstance(int,Locale)DateFormatgetDateInstance}
 *  {@ code(} {@ link DateFormat#SHORT} { @code,getLocale())}。
 * <tr>
 * <td headers ="fs"> <code> </code> <td header ="sc"> {@ link DateFormat#getDateInstance(int,Locale)DateFormatgetDateInstance}
 *  {@ code(} {@ link DateFormat#DEFAULT} @code,getLocale())}。
 * <tr>
 *  <td headers ="fs"> <code> long </code> <td headers ="sc"> {@ link DateFormat#getDateInstance(int,Locale)DateFormatgetDateInstance}
 *  {@ code(} {@ link DateFormat#LONG} { @code,getLocale())}。
 * <tr>
 *  <td header ="fs"> <code> full </code> <td headers ="sc"> {@ link DateFormat#getDateInstance(int,Locale)DateFormatgetDateInstance}
 *  {@ code(} {@ link DateFormat#FULL} { @code,getLocale())}。
 * <tr>
 *  <td headers ="fs"> <i> SubformatPattern </i> <td headers ="sc"> {@ code new} {@link SimpleDateFormat#SimpleDateFormat(String,Locale)SimpleDateFormat}
 *  {@ code(subformatPattern,getLocale ))}。
 * <tr>
 * <td headers ="ft"rowspan = 6> <code> time </code> <td headers ="fs"> <i>(none)</i> <td headers ="sc">
 *  {@ link DateFormat# getTimeInstance(int,Locale)DateFormatgetTimeInstance} {@ code(} {@ link DateFormat#DEFAULT}
 *  {@ code,getLocale())}。
 * <tr>
 *  <td headers ="fs"> <code> short </code> <td headers ="sc"> {@ link DateFormat#getTimeInstance(int,Locale)DateFormatgetTimeInstance}
 *  {@ code(} {@ link DateFormat#SHORT} { @code,getLocale())}。
 * <tr>
 *  <td headers ="fs"> <code> medium </code> <td headers ="sc"> {@ link DateFormat#getTimeInstance(int,Locale)DateFormatgetTimeInstance}
 *  {@ code(} {@ link DateFormat#DEFAULT} @code,getLocale())}。
 * <tr>
 *  <td header ="fs"> <code> long </code> <td header ="sc"> {@ link DateFormat#getTimeInstance(int,Locale)DateFormatgetTimeInstance}
 *  {@ code(} {@ link DateFormat#LONG} { @code,getLocale())}。
 * <tr>
 * <td header ="fs"> <code> full </code> <td headers ="sc"> {@ link DateFormat#getTimeInstance(int,Locale)DateFormatgetTimeInstance}
 *  {@ code(} {@ link DateFormat#FULL} { @code,getLocale())}。
 * <tr>
 *  <td headers ="fs"> <i> SubformatPattern </i> <td headers ="sc"> {@ code new} {@link SimpleDateFormat#SimpleDateFormat(String,Locale)SimpleDateFormat}
 *  {@ code(subformatPattern,getLocale ))}。
 * <tr>
 *  <td headers ="ft"> <code> choice </code> <td headers ="fs"> <i> SubformatPattern </i> <td headers ="sc">
 *  {@ code Choice} ChoiceFormat(String)ChoiceFormat} {@ code(subformatPattern)}。
 * </table>
 * 
 *  <h4>使用信息</h4>
 * 
 * <p>
 * 下面是一些使用示例在真实的国际化程序中,消息格式模式和其他静态字符串当然将从资源束中获取其他参数将在运行时动态确定
 * <p>
 *  第一个例子使用静态方法<code> MessageFormatformat </code>,它在内部创建一次性使用的<code> MessageFormat </code>：<blockquote> 
 * <pre> int planet = 7;字符串事件="一个扰乱的力量";。
 * 
 *  String result = MessageFormatformat("{1,time}在{1,date}上,{0},{}},行星,新Date(),事件)。
 *  </pre> </blockquote>输出是：<blockquote> <pre>在2053年7月3日下午12:30,地球7上的力有一个扰动</pre> </blockquote>。
 * 
 * <p>
 * 以下示例创建一个可以重复使用的<code> MessageFormat </code>实例：<blockquote> <pre> int fileCount = 1273; String diskNam
 * e ="MyDisk"; Object [] testArgs = {new Long(fileCount),diskName};。
 * 
 *  MessageFormat form = new MessageFormat("磁盘"{1}"包含{0}文件");
 * 
 *  Systemoutprintln(formformat(testArgs)); </pre> </blockquote> <code> fileCount </code>不同值的输出：<blockquote>
 *  <pre>磁盘"MyDisk"包含0个文件磁盘"MyDisk"包含1个文件)磁盘"MyDisk"包含1,273个文件</pre> </blockquote>。
 * 
 * <p>
 * 对于更复杂的模式,您可以使用<code> ChoiceFormat </code>生成正确的单数和复数形式：<blockquote> <pre> MessageFormat form = new Mes
 * sageFormat("磁盘"{1} 0}"); double [] filelimits = {0,1,2}; String [] filepart = {"no files","one file","{0,number}
 *  files"}; ChoiceFormat fileform = new ChoiceFormat(filelimits,filepart); formsetFormatByArgumentIndex
 * (0,fileform);。
 * 
 *  int fileCount = 1273; String diskName ="MyDisk"; Object [] testArgs = {new Long(fileCount),diskName}
 * ;。
 * 
 *  Systemoutprintln(formformat(testArgs)); </> </blockquote> <code> fileCount </code>不同的值的输出：<blockquote>
 *  <pre>磁盘"MyDisk"不包含任何文件磁盘"MyDisk"包含一个文件磁盘"MyDisk"包含1,273个文件</pre> </blockquote>。
 * 
 * <p>
 * 您可以通过编程方式创建<code> ChoiceFormat </code>,如上面的示例所示,或者使用模式。
 * 有关详细信息,请参阅{@link ChoiceFormat} <blockquote> <pre> {@ code formapplyPattern("There {选择,0#没有文件| 1#是一个文件| 1 <are {0,number,integer}文件}"); } </pre>
 *  </blockquote>。
 * 您可以通过编程方式创建<code> ChoiceFormat </code>,如上面的示例所示,或者使用模式。
 * 
 * <p>
 *  <strong>注意：</strong>如上所述,<code> MessageFormat </code>中的<code> ChoiceFormat </code>生成的字符串被视为特殊字符串;出现的
 * '{'用于指示子格式,并导致递归如果您以编程方式(而不是使用字符串模式)创建<code> MessageFormat </code>和<code> ChoiceFormat </code>产生一个自身递归的格式,这将导致一个无限循环。
 * <p>
 * 当单个参数在字符串中被解析多次时,最后一个匹配将是解析的最终结果例如,<blockquote> <pre> MessageFormat mf = new MessageFormat("{0,number,###}
 *  {0,number,##}"); Object [] objs = {new Double(31415)}; String result = mfformat(objs); // result now
 *  equals"314,31"objs = null; objs = mfparse(result,new ParsePosition(0)); // objs now equals {new Double(31)}
 *  </pre> </blockquote>。
 * 
 * <p>
 * 同样,使用{@code MessageFormat}对象使用包含多个出现的相同参数的模式解析将返回最后一个匹配例如,<blockquote> <pre> MessageFormat mf = new M
 * essageFormat("{0},{0} 0}"); String forParsing ="x,y,z"; Object [] objs = mfparse(forParsing,new Parse
 * Position(0)); // result now equals {new String("z")} </pre> </blockquote>。
 * 
 *  <h4> <a name=\"synchronization\">同步</a> </h4>
 * 
 * <p>
 *  消息格式不同步建议为每个线程创建单独的格式实例如果多个线程并发访问格式,则必须在外部同步
 * 
 * 
 * @see          java.util.Locale
 * @see          Format
 * @see          NumberFormat
 * @see          DecimalFormat
 * @see          DecimalFormatSymbols
 * @see          ChoiceFormat
 * @see          DateFormat
 * @see          SimpleDateFormat
 *
 * @author       Mark Davis
 */

public class MessageFormat extends Format {

    private static final long serialVersionUID = 6479157306784022952L;

    /**
     * Constructs a MessageFormat for the default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale and the
     * specified pattern.
     * The constructor first sets the locale, then parses the pattern and
     * creates a list of subformats for the format elements contained in it.
     * Patterns and their interpretation are specified in the
     * <a href="#patterns">class description</a>.
     *
     * <p>
     * 构造默认{@link javautilLocaleCategory#FORMAT FORMAT}语言环境和指定模式的MessageFormat构造函数首先设置语言环境,然后解析模式并为其中包含的格式元素
     * 创建一个子格式的列表模式及其解释在<a href=\"#patterns\">课程描述</a>。
     * 
     * 
     * @param pattern the pattern for this message format
     * @exception IllegalArgumentException if the pattern is invalid
     */
    public MessageFormat(String pattern) {
        this.locale = Locale.getDefault(Locale.Category.FORMAT);
        applyPattern(pattern);
    }

    /**
     * Constructs a MessageFormat for the specified locale and
     * pattern.
     * The constructor first sets the locale, then parses the pattern and
     * creates a list of subformats for the format elements contained in it.
     * Patterns and their interpretation are specified in the
     * <a href="#patterns">class description</a>.
     *
     * <p>
     *  为指定的语言环境和模式构造MessageFormat构造函数首先设置语言环境,然后解析模式,并为其中包含的格式元素创建一个子格式的列表,并且它们的解释在<a href=\"#patterns\">中指
     * 定类描述</a>。
     * 
     * 
     * @param pattern the pattern for this message format
     * @param locale the locale for this message format
     * @exception IllegalArgumentException if the pattern is invalid
     * @since 1.4
     */
    public MessageFormat(String pattern, Locale locale) {
        this.locale = locale;
        applyPattern(pattern);
    }

    /**
     * Sets the locale to be used when creating or comparing subformats.
     * This affects subsequent calls
     * <ul>
     * <li>to the {@link #applyPattern applyPattern}
     *     and {@link #toPattern toPattern} methods if format elements specify
     *     a format type and therefore have the subformats created in the
     *     <code>applyPattern</code> method, as well as
     * <li>to the <code>format</code> and
     *     {@link #formatToCharacterIterator formatToCharacterIterator} methods
     *     if format elements do not specify a format type and therefore have
     *     the subformats created in the formatting methods.
     * </ul>
     * Subformats that have already been created are not affected.
     *
     * <p>
     *  设置在创建或比较子格式时使用的区域设置这会影响后续调用
     * <ul>
     * <li>到{@link #applyPattern applyPattern}和{@link #toPattern toPattern}方法,如果格式元素指定格式类型,因此具有在<code> apply
     * Pattern </code>方法中创建的子格式,如果格式元素没有指定格式类型,并且因此具有以格式化方法创建的子格式,则<li>到<code>格式</code>和{@link #formatToCharacterIterator formatToCharacterIterator}
     * 。
     * </ul>
     *  已创建的子格式不受影响
     * 
     * 
     * @param locale the locale to be used when creating or comparing subformats
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Gets the locale that's used when creating or comparing subformats.
     *
     * <p>
     *  获取在创建或比较子格式时使用的区域设置
     * 
     * 
     * @return the locale used when creating or comparing subformats
     */
    public Locale getLocale() {
        return locale;
    }


    /**
     * Sets the pattern used by this message format.
     * The method parses the pattern and creates a list of subformats
     * for the format elements contained in it.
     * Patterns and their interpretation are specified in the
     * <a href="#patterns">class description</a>.
     *
     * <p>
     * 设置此消息格式使用的模式方法解析模式并为其中包含的格式元素创建一个子格式的列表模式及其解释在<a href=\"#patterns\">类描述</a>中指定
     * 
     * 
     * @param pattern the pattern for this message format
     * @exception IllegalArgumentException if the pattern is invalid
     */
    @SuppressWarnings("fallthrough") // fallthrough in switch is expected, suppress it
    public void applyPattern(String pattern) {
            StringBuilder[] segments = new StringBuilder[4];
            // Allocate only segments[SEG_RAW] here. The rest are
            // allocated on demand.
            segments[SEG_RAW] = new StringBuilder();

            int part = SEG_RAW;
            int formatNumber = 0;
            boolean inQuote = false;
            int braceStack = 0;
            maxOffset = -1;
            for (int i = 0; i < pattern.length(); ++i) {
                char ch = pattern.charAt(i);
                if (part == SEG_RAW) {
                    if (ch == '\'') {
                        if (i + 1 < pattern.length()
                            && pattern.charAt(i+1) == '\'') {
                            segments[part].append(ch);  // handle doubles
                            ++i;
                        } else {
                            inQuote = !inQuote;
                        }
                    } else if (ch == '{' && !inQuote) {
                        part = SEG_INDEX;
                        if (segments[SEG_INDEX] == null) {
                            segments[SEG_INDEX] = new StringBuilder();
                        }
                    } else {
                        segments[part].append(ch);
                    }
                } else  {
                    if (inQuote) {              // just copy quotes in parts
                        segments[part].append(ch);
                        if (ch == '\'') {
                            inQuote = false;
                        }
                    } else {
                        switch (ch) {
                        case ',':
                            if (part < SEG_MODIFIER) {
                                if (segments[++part] == null) {
                                    segments[part] = new StringBuilder();
                                }
                            } else {
                                segments[part].append(ch);
                            }
                            break;
                        case '{':
                            ++braceStack;
                            segments[part].append(ch);
                            break;
                        case '}':
                            if (braceStack == 0) {
                                part = SEG_RAW;
                                makeFormat(i, formatNumber, segments);
                                formatNumber++;
                                // throw away other segments
                                segments[SEG_INDEX] = null;
                                segments[SEG_TYPE] = null;
                                segments[SEG_MODIFIER] = null;
                            } else {
                                --braceStack;
                                segments[part].append(ch);
                            }
                            break;
                        case ' ':
                            // Skip any leading space chars for SEG_TYPE.
                            if (part != SEG_TYPE || segments[SEG_TYPE].length() > 0) {
                                segments[part].append(ch);
                            }
                            break;
                        case '\'':
                            inQuote = true;
                            // fall through, so we keep quotes in other parts
                        default:
                            segments[part].append(ch);
                            break;
                        }
                    }
                }
            }
            if (braceStack == 0 && part != 0) {
                maxOffset = -1;
                throw new IllegalArgumentException("Unmatched braces in the pattern.");
            }
            this.pattern = segments[0].toString();
    }


    /**
     * Returns a pattern representing the current state of the message format.
     * The string is constructed from internal information and therefore
     * does not necessarily equal the previously applied pattern.
     *
     * <p>
     *  返回表示消息格式的当前状态的模式该字符串由内部信息构造,因此不一定等于先前应用的模式
     * 
     * 
     * @return a pattern representing the current state of the message format
     */
    public String toPattern() {
        // later, make this more extensible
        int lastOffset = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= maxOffset; ++i) {
            copyAndFixQuotes(pattern, lastOffset, offsets[i], result);
            lastOffset = offsets[i];
            result.append('{').append(argumentNumbers[i]);
            Format fmt = formats[i];
            if (fmt == null) {
                // do nothing, string format
            } else if (fmt instanceof NumberFormat) {
                if (fmt.equals(NumberFormat.getInstance(locale))) {
                    result.append(",number");
                } else if (fmt.equals(NumberFormat.getCurrencyInstance(locale))) {
                    result.append(",number,currency");
                } else if (fmt.equals(NumberFormat.getPercentInstance(locale))) {
                    result.append(",number,percent");
                } else if (fmt.equals(NumberFormat.getIntegerInstance(locale))) {
                    result.append(",number,integer");
                } else {
                    if (fmt instanceof DecimalFormat) {
                        result.append(",number,").append(((DecimalFormat)fmt).toPattern());
                    } else if (fmt instanceof ChoiceFormat) {
                        result.append(",choice,").append(((ChoiceFormat)fmt).toPattern());
                    } else {
                        // UNKNOWN
                    }
                }
            } else if (fmt instanceof DateFormat) {
                int index;
                for (index = MODIFIER_DEFAULT; index < DATE_TIME_MODIFIERS.length; index++) {
                    DateFormat df = DateFormat.getDateInstance(DATE_TIME_MODIFIERS[index],
                                                               locale);
                    if (fmt.equals(df)) {
                        result.append(",date");
                        break;
                    }
                    df = DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[index],
                                                    locale);
                    if (fmt.equals(df)) {
                        result.append(",time");
                        break;
                    }
                }
                if (index >= DATE_TIME_MODIFIERS.length) {
                    if (fmt instanceof SimpleDateFormat) {
                        result.append(",date,").append(((SimpleDateFormat)fmt).toPattern());
                    } else {
                        // UNKNOWN
                    }
                } else if (index != MODIFIER_DEFAULT) {
                    result.append(',').append(DATE_TIME_MODIFIER_KEYWORDS[index]);
                }
            } else {
                //result.append(", unknown");
            }
            result.append('}');
        }
        copyAndFixQuotes(pattern, lastOffset, pattern.length(), result);
        return result.toString();
    }

    /**
     * Sets the formats to use for the values passed into
     * <code>format</code> methods or returned from <code>parse</code>
     * methods. The indices of elements in <code>newFormats</code>
     * correspond to the argument indices used in the previously set
     * pattern string.
     * The order of formats in <code>newFormats</code> thus corresponds to
     * the order of elements in the <code>arguments</code> array passed
     * to the <code>format</code> methods or the result array returned
     * by the <code>parse</code> methods.
     * <p>
     * If an argument index is used for more than one format element
     * in the pattern string, then the corresponding new format is used
     * for all such format elements. If an argument index is not used
     * for any format element in the pattern string, then the
     * corresponding new format is ignored. If fewer formats are provided
     * than needed, then only the formats for argument indices less
     * than <code>newFormats.length</code> are replaced.
     *
     * <p>
     * 设置传递到<code> format </code>方法或从<code> parse </code>方法返回的值的格式<code> newFormats </code>中的元素索引对应于所使用的参数索引
     * 在<code> newFormats </code>中的格式顺序对应于传递给<code> format </code>方法的<code> arguments </code>数组中的元素顺序,由<code>
     *  parse </code>方法返回的结果数组。
     * <p>
     * 如果参数索引用于模式字符串中的多于一个格式元素,则对应的新格式用于所有这样的格式元素。
     * 如果参数索引不用于模式字符串中的任何格式元素,则相应的新格式被忽略如果提供比所需要的格式更少的格式,则仅替换小于<code> newFormatslength </code>的变元索引的格式被替换。
     * 
     * 
     * @param newFormats the new formats to use
     * @exception NullPointerException if <code>newFormats</code> is null
     * @since 1.4
     */
    public void setFormatsByArgumentIndex(Format[] newFormats) {
        for (int i = 0; i <= maxOffset; i++) {
            int j = argumentNumbers[i];
            if (j < newFormats.length) {
                formats[i] = newFormats[j];
            }
        }
    }

    /**
     * Sets the formats to use for the format elements in the
     * previously set pattern string.
     * The order of formats in <code>newFormats</code> corresponds to
     * the order of format elements in the pattern string.
     * <p>
     * If more formats are provided than needed by the pattern string,
     * the remaining ones are ignored. If fewer formats are provided
     * than needed, then only the first <code>newFormats.length</code>
     * formats are replaced.
     * <p>
     * Since the order of format elements in a pattern string often
     * changes during localization, it is generally better to use the
     * {@link #setFormatsByArgumentIndex setFormatsByArgumentIndex}
     * method, which assumes an order of formats corresponding to the
     * order of elements in the <code>arguments</code> array passed to
     * the <code>format</code> methods or the result array returned by
     * the <code>parse</code> methods.
     *
     * <p>
     *  设置要用于先前设置的模式字符串中的格式元素的格式<code> newFormats </code>中格式的顺序对应于模式字符串中格式元素的顺序
     * <p>
     * 如果提供的模式字符串所需的格式多于模式字符串所需的格式,则忽略剩余的格式。如果提供比所需更少的格式,则只替换第一个<code> newFormatslength </code>格式
     * <p>
     *  由于模式字符串中的格式元素的顺序在本地化过程中经常发生变化,因此通常最好使用{@link #setFormatsByArgumentIndex setFormatsByArgumentIndex}方法
     * ,该方法假定格式顺序对应于<code> / code>数组传递给<code> format </code>方法或由<code> parse </code>方法返回的结果数组。
     * 
     * 
     * @param newFormats the new formats to use
     * @exception NullPointerException if <code>newFormats</code> is null
     */
    public void setFormats(Format[] newFormats) {
        int runsToCopy = newFormats.length;
        if (runsToCopy > maxOffset + 1) {
            runsToCopy = maxOffset + 1;
        }
        for (int i = 0; i < runsToCopy; i++) {
            formats[i] = newFormats[i];
        }
    }

    /**
     * Sets the format to use for the format elements within the
     * previously set pattern string that use the given argument
     * index.
     * The argument index is part of the format element definition and
     * represents an index into the <code>arguments</code> array passed
     * to the <code>format</code> methods or the result array returned
     * by the <code>parse</code> methods.
     * <p>
     * If the argument index is used for more than one format element
     * in the pattern string, then the new format is used for all such
     * format elements. If the argument index is not used for any format
     * element in the pattern string, then the new format is ignored.
     *
     * <p>
     * 设置用于先前设置的模式字符串中使用给定参数的格式元素的格式索引参数索引是格式元素定义的一部分,并且表示到传递到< code> format </code>方法或由<code> parse </code>
     * 方法返回的结果数组。
     * <p>
     *  如果参数索引用于模式字符串中的多个格式元素,则新格式用于所有这样的格式元素如果参数索引不用于模式字符串中的任何格式元素,则新格式被忽略
     * 
     * 
     * @param argumentIndex the argument index for which to use the new format
     * @param newFormat the new format to use
     * @since 1.4
     */
    public void setFormatByArgumentIndex(int argumentIndex, Format newFormat) {
        for (int j = 0; j <= maxOffset; j++) {
            if (argumentNumbers[j] == argumentIndex) {
                formats[j] = newFormat;
            }
        }
    }

    /**
     * Sets the format to use for the format element with the given
     * format element index within the previously set pattern string.
     * The format element index is the zero-based number of the format
     * element counting from the start of the pattern string.
     * <p>
     * Since the order of format elements in a pattern string often
     * changes during localization, it is generally better to use the
     * {@link #setFormatByArgumentIndex setFormatByArgumentIndex}
     * method, which accesses format elements based on the argument
     * index they specify.
     *
     * <p>
     * 设置用于具有给定格式元素索引的格式元素的格式,该格式元素索引在先前设置的模式字符串内。格式元素索引是格式元素的从零开始的数字,从模式字符串的开始计数
     * <p>
     *  由于模式字符串中的格式元素的顺序在本地化过程中经常发生变化,因此通常最好使用{@link #setFormatByArgumentIndex setFormatByArgumentIndex}方法,该
     * 方法根据指定的参数索引访问格式元素。
     * 
     * 
     * @param formatElementIndex the index of a format element within the pattern
     * @param newFormat the format to use for the specified format element
     * @exception ArrayIndexOutOfBoundsException if {@code formatElementIndex} is equal to or
     *            larger than the number of format elements in the pattern string
     */
    public void setFormat(int formatElementIndex, Format newFormat) {
        formats[formatElementIndex] = newFormat;
    }

    /**
     * Gets the formats used for the values passed into
     * <code>format</code> methods or returned from <code>parse</code>
     * methods. The indices of elements in the returned array
     * correspond to the argument indices used in the previously set
     * pattern string.
     * The order of formats in the returned array thus corresponds to
     * the order of elements in the <code>arguments</code> array passed
     * to the <code>format</code> methods or the result array returned
     * by the <code>parse</code> methods.
     * <p>
     * If an argument index is used for more than one format element
     * in the pattern string, then the format used for the last such
     * format element is returned in the array. If an argument index
     * is not used for any format element in the pattern string, then
     * null is returned in the array.
     *
     * <p>
     * 获取传递到<code> format </code>方法或从<code> parse </code>方法返回的值的格式返回数组中的元素的索引对应于先前设置的模式字符串中使用的参数索引因此,返回数组中的格
     * 式顺序对应于传递给<code> format </code>方法的<code> arguments </code>数组中元素的顺序或由<code> parse <// code>方法。
     * <p>
     *  如果参数索引用于模式字符串中的多个格式元素,则在数组中返回用于最后一个这样的格式元素的格式。如果参数索引不用于模式字符串中的任何格式元素,则null在数组中返回
     * 
     * 
     * @return the formats used for the arguments within the pattern
     * @since 1.4
     */
    public Format[] getFormatsByArgumentIndex() {
        int maximumArgumentNumber = -1;
        for (int i = 0; i <= maxOffset; i++) {
            if (argumentNumbers[i] > maximumArgumentNumber) {
                maximumArgumentNumber = argumentNumbers[i];
            }
        }
        Format[] resultArray = new Format[maximumArgumentNumber + 1];
        for (int i = 0; i <= maxOffset; i++) {
            resultArray[argumentNumbers[i]] = formats[i];
        }
        return resultArray;
    }

    /**
     * Gets the formats used for the format elements in the
     * previously set pattern string.
     * The order of formats in the returned array corresponds to
     * the order of format elements in the pattern string.
     * <p>
     * Since the order of format elements in a pattern string often
     * changes during localization, it's generally better to use the
     * {@link #getFormatsByArgumentIndex getFormatsByArgumentIndex}
     * method, which assumes an order of formats corresponding to the
     * order of elements in the <code>arguments</code> array passed to
     * the <code>format</code> methods or the result array returned by
     * the <code>parse</code> methods.
     *
     * <p>
     * 获取用于先前设置的模式字符串中的格式元素的格式返回的数组中的格式的顺序对应于模式字符串中格式元素的顺序
     * <p>
     *  由于模式字符串中的格式元素的顺序在本地化过程中经常发生变化,因此通常最好使用{@link #getFormatsByArgumentIndex getFormatsByArgumentIndex}方法
     * ,该方法假定格式顺序对应于<code> arguments </>代码>数组传递给<code> format </code>方法或由<code> parse </code>方法返回的结果数组。
     * 
     * 
     * @return the formats used for the format elements in the pattern
     */
    public Format[] getFormats() {
        Format[] resultArray = new Format[maxOffset + 1];
        System.arraycopy(formats, 0, resultArray, 0, maxOffset + 1);
        return resultArray;
    }

    /**
     * Formats an array of objects and appends the <code>MessageFormat</code>'s
     * pattern, with format elements replaced by the formatted objects, to the
     * provided <code>StringBuffer</code>.
     * <p>
     * The text substituted for the individual format elements is derived from
     * the current subformat of the format element and the
     * <code>arguments</code> element at the format element's argument index
     * as indicated by the first matching line of the following table. An
     * argument is <i>unavailable</i> if <code>arguments</code> is
     * <code>null</code> or has fewer than argumentIndex+1 elements.
     *
     * <table border=1 summary="Examples of subformat,argument,and formatted text">
     *    <tr>
     *       <th>Subformat
     *       <th>Argument
     *       <th>Formatted Text
     *    <tr>
     *       <td><i>any</i>
     *       <td><i>unavailable</i>
     *       <td><code>"{" + argumentIndex + "}"</code>
     *    <tr>
     *       <td><i>any</i>
     *       <td><code>null</code>
     *       <td><code>"null"</code>
     *    <tr>
     *       <td><code>instanceof ChoiceFormat</code>
     *       <td><i>any</i>
     *       <td><code>subformat.format(argument).indexOf('{') &gt;= 0 ?<br>
     *           (new MessageFormat(subformat.format(argument), getLocale())).format(argument) :
     *           subformat.format(argument)</code>
     *    <tr>
     *       <td><code>!= null</code>
     *       <td><i>any</i>
     *       <td><code>subformat.format(argument)</code>
     *    <tr>
     *       <td><code>null</code>
     *       <td><code>instanceof Number</code>
     *       <td><code>NumberFormat.getInstance(getLocale()).format(argument)</code>
     *    <tr>
     *       <td><code>null</code>
     *       <td><code>instanceof Date</code>
     *       <td><code>DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, getLocale()).format(argument)</code>
     *    <tr>
     *       <td><code>null</code>
     *       <td><code>instanceof String</code>
     *       <td><code>argument</code>
     *    <tr>
     *       <td><code>null</code>
     *       <td><i>any</i>
     *       <td><code>argument.toString()</code>
     * </table>
     * <p>
     * If <code>pos</code> is non-null, and refers to
     * <code>Field.ARGUMENT</code>, the location of the first formatted
     * string will be returned.
     *
     * <p>
     *  格式化对象数组,并将<code> MessageFormat </code>的模式附加到提供的<code> StringBuffer </code>
     * <p>
     * 替换单个格式元素的文本从格式元素的当前子格式和格式元素的参数索引处的<code> arguments </code>元素派生,如下表的第一个匹配行所示：参数是< i>不可用</i>如果<code>参数</code>
     * 是<code> null </code>或少于argumentIndex + 1个元素。
     * 
     * <table border=1 summary="Examples of subformat,argument,and formatted text">
     * <tr>
     *  <th>子格式<th>参数<th>格式化文本
     * <tr>
     *  <td> <i>任何</i> <td> <i>不可用</i> <td> <code>"{"+ argumentIndex +"}"</code>
     * <tr>
     *  <td> <i>任何</i> <td> <code> null </code> <td> <code>"null"</code>
     * <tr>
     * <td> <code> instanceof ChoiceFormat </code> <td> <i>任何</i> <td> <code> subformatformat(argument)index
     * Of('{')&gt; = 0?<br>(new MessageFormat (subformatformat(argument),getLocale()))format(argument)：subformatformat(argument)</code>。
     * <tr>
     *  <td> <code>！= null </code> <td> <i> any </i> <td> <code> subformatformat(argument)</code>
     * <tr>
     *  <td> <code> null </code> <td> <code> instanceof Number </code> <td> <code> NumberFormatgetInstance(g
     * etLocale())format。
     * <tr>
     *  <td> <code> null </code> <td> <code> instanceof Date </code> <td> <code> DateFormatgetDateTimeInstan
     * ce(DateFormatSHORT,DateFormatSHORT,getLocale。
     * <tr>
     *  <td> <code> null </code> <td> <code> instanceof String </code> <td> <code>
     * <tr>
     *  <td> <code> null </code> <td> <i> any </i> <td> <code> argumenttoString()</code>
     * </table>
     * <p>
     * 如果<code> pos </code>为非空,并且引用<code> FieldARGUMENT </code>,则将返回第一个格式化字符串的位置
     * 
     * 
     * @param arguments an array of objects to be formatted and substituted.
     * @param result where text is appended.
     * @param pos On input: an alignment field, if desired.
     *            On output: the offsets of the alignment field.
     * @return the string buffer passed in as {@code result}, with formatted
     * text appended
     * @exception IllegalArgumentException if an argument in the
     *            <code>arguments</code> array is not of the type
     *            expected by the format element(s) that use it.
     */
    public final StringBuffer format(Object[] arguments, StringBuffer result,
                                     FieldPosition pos)
    {
        return subformat(arguments, result, pos, null);
    }

    /**
     * Creates a MessageFormat with the given pattern and uses it
     * to format the given arguments. This is equivalent to
     * <blockquote>
     *     <code>(new {@link #MessageFormat(String) MessageFormat}(pattern)).{@link #format(java.lang.Object[], java.lang.StringBuffer, java.text.FieldPosition) format}(arguments, new StringBuffer(), null).toString()</code>
     * </blockquote>
     *
     * <p>
     *  创建一个MessageFormat与给定的模式,并使用它格式化给定的参数这是等价的
     * <blockquote>
     *  <code>(new {@link #MessageFormat(String)MessageFormat}(pattern)){@ link #format(javalangObject [],javalangStringBuffer,javatextFieldPosition)format}
     * (arguments,new StringBuffer(),null)toString代码>。
     * </blockquote>
     * 
     * 
     * @param pattern   the pattern string
     * @param arguments object(s) to format
     * @return the formatted string
     * @exception IllegalArgumentException if the pattern is invalid,
     *            or if an argument in the <code>arguments</code> array
     *            is not of the type expected by the format element(s)
     *            that use it.
     */
    public static String format(String pattern, Object ... arguments) {
        MessageFormat temp = new MessageFormat(pattern);
        return temp.format(arguments);
    }

    // Overrides
    /**
     * Formats an array of objects and appends the <code>MessageFormat</code>'s
     * pattern, with format elements replaced by the formatted objects, to the
     * provided <code>StringBuffer</code>.
     * This is equivalent to
     * <blockquote>
     *     <code>{@link #format(java.lang.Object[], java.lang.StringBuffer, java.text.FieldPosition) format}((Object[]) arguments, result, pos)</code>
     * </blockquote>
     *
     * <p>
     *  格式化对象数组,并将<code> MessageFormat </code>的模式附加到提供的<code> StringBuffer </code>中,格式元素由格式化的对象替换。
     * <blockquote>
     * <code> {@ link #format(javalangObject [],javalangStringBuffer,javatextFieldPosition)format}((Object [
     * ])arguments,result,pos)</code>。
     * </blockquote>
     * 
     * 
     * @param arguments an array of objects to be formatted and substituted.
     * @param result where text is appended.
     * @param pos On input: an alignment field, if desired.
     *            On output: the offsets of the alignment field.
     * @exception IllegalArgumentException if an argument in the
     *            <code>arguments</code> array is not of the type
     *            expected by the format element(s) that use it.
     */
    public final StringBuffer format(Object arguments, StringBuffer result,
                                     FieldPosition pos)
    {
        return subformat((Object[]) arguments, result, pos, null);
    }

    /**
     * Formats an array of objects and inserts them into the
     * <code>MessageFormat</code>'s pattern, producing an
     * <code>AttributedCharacterIterator</code>.
     * You can use the returned <code>AttributedCharacterIterator</code>
     * to build the resulting String, as well as to determine information
     * about the resulting String.
     * <p>
     * The text of the returned <code>AttributedCharacterIterator</code> is
     * the same that would be returned by
     * <blockquote>
     *     <code>{@link #format(java.lang.Object[], java.lang.StringBuffer, java.text.FieldPosition) format}(arguments, new StringBuffer(), null).toString()</code>
     * </blockquote>
     * <p>
     * In addition, the <code>AttributedCharacterIterator</code> contains at
     * least attributes indicating where text was generated from an
     * argument in the <code>arguments</code> array. The keys of these attributes are of
     * type <code>MessageFormat.Field</code>, their values are
     * <code>Integer</code> objects indicating the index in the <code>arguments</code>
     * array of the argument from which the text was generated.
     * <p>
     * The attributes/value from the underlying <code>Format</code>
     * instances that <code>MessageFormat</code> uses will also be
     * placed in the resulting <code>AttributedCharacterIterator</code>.
     * This allows you to not only find where an argument is placed in the
     * resulting String, but also which fields it contains in turn.
     *
     * <p>
     *  格式化对象数组并将它们插入到<code> MessageFormat </code>的模式中,产生一个<code> AttributedCharacterIterator </code>你可以使用返回
     * 的<code> AttributedCharacterIterator </code> ,以及确定关于结果String的信息。
     * <p>
     *  返回的<code> AttributedCharacterIterator </code>文本与返回的文本相同
     * <blockquote>
     *  <code> {@ link #format(javalangObject [],javalangStringBuffer,javatextFieldPosition)format}(argument
     * s,new StringBuffer(),null)toString()</code>。
     * </blockquote>
     * <p>
     * 此外,<code> AttributedCharacterIterator </code>至少包含指示从<code> arguments </code>数组中的参数生成文本的属性。
     * 这些属性的键类型为<code> MessageFormatField </code >,它们的值是<code> Integer </code>对象,指示生成文本的参数的<code> arguments 
     * </code>数组中的索引。
     * 此外,<code> AttributedCharacterIterator </code>至少包含指示从<code> arguments </code>数组中的参数生成文本的属性。
     * <p>
     *  来自<code> MessageFormat </code>使用的底层<code> Format </code>实例的属性/值也将放置在结果<code> AttributedCharacterIter
     * ator </code>中。
     * 这允许您不仅可以找到参数放在生成的String中,而且依次包含它包含的字段。
     * 
     * 
     * @param arguments an array of objects to be formatted and substituted.
     * @return AttributedCharacterIterator describing the formatted value.
     * @exception NullPointerException if <code>arguments</code> is null.
     * @exception IllegalArgumentException if an argument in the
     *            <code>arguments</code> array is not of the type
     *            expected by the format element(s) that use it.
     * @since 1.4
     */
    public AttributedCharacterIterator formatToCharacterIterator(Object arguments) {
        StringBuffer result = new StringBuffer();
        ArrayList<AttributedCharacterIterator> iterators = new ArrayList<>();

        if (arguments == null) {
            throw new NullPointerException(
                   "formatToCharacterIterator must be passed non-null object");
        }
        subformat((Object[]) arguments, result, null, iterators);
        if (iterators.size() == 0) {
            return createAttributedCharacterIterator("");
        }
        return createAttributedCharacterIterator(
                     iterators.toArray(
                     new AttributedCharacterIterator[iterators.size()]));
    }

    /**
     * Parses the string.
     *
     * <p>Caveats: The parse may fail in a number of circumstances.
     * For example:
     * <ul>
     * <li>If one of the arguments does not occur in the pattern.
     * <li>If the format of an argument loses information, such as
     *     with a choice format where a large number formats to "many".
     * <li>Does not yet handle recursion (where
     *     the substituted strings contain {n} references.)
     * <li>Will not always find a match (or the correct match)
     *     if some part of the parse is ambiguous.
     *     For example, if the pattern "{1},{2}" is used with the
     *     string arguments {"a,b", "c"}, it will format as "a,b,c".
     *     When the result is parsed, it will return {"a", "b,c"}.
     * <li>If a single argument is parsed more than once in the string,
     *     then the later parse wins.
     * </ul>
     * When the parse fails, use ParsePosition.getErrorIndex() to find out
     * where in the string the parsing failed.  The returned error
     * index is the starting offset of the sub-patterns that the string
     * is comparing with.  For example, if the parsing string "AAA {0} BBB"
     * is comparing against the pattern "AAD {0} BBB", the error index is
     * 0. When an error occurs, the call to this method will return null.
     * If the source is null, return an empty array.
     *
     * <p>
     *  解析字符串
     * 
     * <p>注意：解析在多种情况下可能会失败例如：
     * <ul>
     *  <li>如果模式中没有出现参数之一<li>如果参数的格式丢失信息,例如使用选择格式,其中大数字格式为"many"<li>尚未处理递归其中替换字符串包含{n}个引用)<li>如果解析的某些部分不明确,则
     * 不总是找到匹配(或正确匹配)。
     * 例如,如果使用模式"{1},{2}"使用字符串参数{"a,b","c"},它将格式化为"a,b,c"当结果被解析时,它将返回{"a","b,c"} <li>如果单个参数在字符串中被多次解析,则稍后的解析
     * 将胜利。
     * </ul>
     * 当解析失败时,使用ParsePositiongetErrorIndex()来查找字符串中解析失败的位置。返回的错误索引是字符串正在比较的子模式的起始偏移。
     * 例如,如果解析字符串"AAA {0} BBB"与模式"AAD {0} BBB"进行比较,错误索引为0当发生错误时,对此方法的调用将返回null如果源为null,则返回一个空数组。
     * 
     * 
     * @param source the string to parse
     * @param pos    the parse position
     * @return an array of parsed objects
     */
    public Object[] parse(String source, ParsePosition pos) {
        if (source == null) {
            Object[] empty = {};
            return empty;
        }

        int maximumArgumentNumber = -1;
        for (int i = 0; i <= maxOffset; i++) {
            if (argumentNumbers[i] > maximumArgumentNumber) {
                maximumArgumentNumber = argumentNumbers[i];
            }
        }
        Object[] resultArray = new Object[maximumArgumentNumber + 1];

        int patternOffset = 0;
        int sourceOffset = pos.index;
        ParsePosition tempStatus = new ParsePosition(0);
        for (int i = 0; i <= maxOffset; ++i) {
            // match up to format
            int len = offsets[i] - patternOffset;
            if (len == 0 || pattern.regionMatches(patternOffset,
                                                  source, sourceOffset, len)) {
                sourceOffset += len;
                patternOffset += len;
            } else {
                pos.errorIndex = sourceOffset;
                return null; // leave index as is to signal error
            }

            // now use format
            if (formats[i] == null) {   // string format
                // if at end, use longest possible match
                // otherwise uses first match to intervening string
                // does NOT recursively try all possibilities
                int tempLength = (i != maxOffset) ? offsets[i+1] : pattern.length();

                int next;
                if (patternOffset >= tempLength) {
                    next = source.length();
                }else{
                    next = source.indexOf(pattern.substring(patternOffset, tempLength),
                                          sourceOffset);
                }

                if (next < 0) {
                    pos.errorIndex = sourceOffset;
                    return null; // leave index as is to signal error
                } else {
                    String strValue= source.substring(sourceOffset,next);
                    if (!strValue.equals("{"+argumentNumbers[i]+"}"))
                        resultArray[argumentNumbers[i]]
                            = source.substring(sourceOffset,next);
                    sourceOffset = next;
                }
            } else {
                tempStatus.index = sourceOffset;
                resultArray[argumentNumbers[i]]
                    = formats[i].parseObject(source,tempStatus);
                if (tempStatus.index == sourceOffset) {
                    pos.errorIndex = sourceOffset;
                    return null; // leave index as is to signal error
                }
                sourceOffset = tempStatus.index; // update
            }
        }
        int len = pattern.length() - patternOffset;
        if (len == 0 || pattern.regionMatches(patternOffset,
                                              source, sourceOffset, len)) {
            pos.index = sourceOffset + len;
        } else {
            pos.errorIndex = sourceOffset;
            return null; // leave index as is to signal error
        }
        return resultArray;
    }

    /**
     * Parses text from the beginning of the given string to produce an object
     * array.
     * The method may not use the entire text of the given string.
     * <p>
     * See the {@link #parse(String, ParsePosition)} method for more information
     * on message parsing.
     *
     * <p>
     *  从给定字符串的开头解析文本以产生对象数组该方法可能不使用给定字符串的整个文本
     * <p>
     *  有关消息解析的更多信息,请参阅{@link #parse(String,ParsePosition)}方法
     * 
     * 
     * @param source A <code>String</code> whose beginning should be parsed.
     * @return An <code>Object</code> array parsed from the string.
     * @exception ParseException if the beginning of the specified string
     *            cannot be parsed.
     */
    public Object[] parse(String source) throws ParseException {
        ParsePosition pos  = new ParsePosition(0);
        Object[] result = parse(source, pos);
        if (pos.index == 0)  // unchanged, returned object is null
            throw new ParseException("MessageFormat parse error!", pos.errorIndex);

        return result;
    }

    /**
     * Parses text from a string to produce an object array.
     * <p>
     * The method attempts to parse text starting at the index given by
     * <code>pos</code>.
     * If parsing succeeds, then the index of <code>pos</code> is updated
     * to the index after the last character used (parsing does not necessarily
     * use all characters up to the end of the string), and the parsed
     * object array is returned. The updated <code>pos</code> can be used to
     * indicate the starting point for the next call to this method.
     * If an error occurs, then the index of <code>pos</code> is not
     * changed, the error index of <code>pos</code> is set to the index of
     * the character where the error occurred, and null is returned.
     * <p>
     * See the {@link #parse(String, ParsePosition)} method for more information
     * on message parsing.
     *
     * <p>
     *  从字符串解析文本以生成对象数组
     * <p>
     * 该方法尝试解析从<code> pos </code>给出的索引开始的文本如果解析成功,则将<code> pos </code>的索引更新为使用最后一个字符后的索引(解析不会必须使用直到字符串结尾的所有字
     * 符),并返回已解析的对象数组更新的<code> pos </code>可用于指示下一次调用此方法的起点如果发生错误,那么<code> pos </code>的索引不改变,将<code> pos </code>
     * 的错误索引设置为发生错误的字符的索引,并返回null。
     * <p>
     *  有关消息解析的更多信息,请参阅{@link #parse(String,ParsePosition)}方法
     * 
     * 
     * @param source A <code>String</code>, part of which should be parsed.
     * @param pos A <code>ParsePosition</code> object with index and error
     *            index information as described above.
     * @return An <code>Object</code> array parsed from the string. In case of
     *         error, returns null.
     * @exception NullPointerException if <code>pos</code> is null.
     */
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Creates and returns a copy of this object.
     *
     * <p>
     *  创建并返回此对象的副本
     * 
     * 
     * @return a clone of this instance.
     */
    public Object clone() {
        MessageFormat other = (MessageFormat) super.clone();

        // clone arrays. Can't do with utility because of bug in Cloneable
        other.formats = formats.clone(); // shallow clone
        for (int i = 0; i < formats.length; ++i) {
            if (formats[i] != null)
                other.formats[i] = (Format)formats[i].clone();
        }
        // for primitives or immutables, shallow clone is enough
        other.offsets = offsets.clone();
        other.argumentNumbers = argumentNumbers.clone();

        return other;
    }

    /**
     * Equality comparison between two message format objects
     * <p>
     * 两个消息格式对象之间的平等比较
     * 
     */
    public boolean equals(Object obj) {
        if (this == obj)                      // quick check
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        MessageFormat other = (MessageFormat) obj;
        return (maxOffset == other.maxOffset
                && pattern.equals(other.pattern)
                && ((locale != null && locale.equals(other.locale))
                 || (locale == null && other.locale == null))
                && Arrays.equals(offsets,other.offsets)
                && Arrays.equals(argumentNumbers,other.argumentNumbers)
                && Arrays.equals(formats,other.formats));
    }

    /**
     * Generates a hash code for the message format object.
     * <p>
     *  为消息格式对象生成哈希码
     * 
     */
    public int hashCode() {
        return pattern.hashCode(); // enough for reasonable distribution
    }


    /**
     * Defines constants that are used as attribute keys in the
     * <code>AttributedCharacterIterator</code> returned
     * from <code>MessageFormat.formatToCharacterIterator</code>.
     *
     * <p>
     *  定义从<code> MessageFormatformatToCharacterIterator </code>返回的<code> AttributedCharacterIterator </code>
     * 中用作属性键的常量。
     * 
     * 
     * @since 1.4
     */
    public static class Field extends Format.Field {

        // Proclaim serial compatibility with 1.4 FCS
        private static final long serialVersionUID = 7899943957617360810L;

        /**
         * Creates a Field with the specified name.
         *
         * <p>
         *  创建具有指定名称的字段
         * 
         * 
         * @param name Name of the attribute
         */
        protected Field(String name) {
            super(name);
        }

        /**
         * Resolves instances being deserialized to the predefined constants.
         *
         * <p>
         *  解析反序列化为预定义常量的实例
         * 
         * 
         * @throws InvalidObjectException if the constant could not be
         *         resolved.
         * @return resolved MessageFormat.Field constant
         */
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != MessageFormat.Field.class) {
                throw new InvalidObjectException("subclass didn't correctly implement readResolve");
            }

            return ARGUMENT;
        }

        //
        // The constants
        //

        /**
         * Constant identifying a portion of a message that was generated
         * from an argument passed into <code>formatToCharacterIterator</code>.
         * The value associated with the key will be an <code>Integer</code>
         * indicating the index in the <code>arguments</code> array of the
         * argument from which the text was generated.
         * <p>
         *  常量标识从传递到<code> formatToCharacterIterator中的参数生成的消息的一部分</code>与键相关联的值将是<code> Integer </code>,表示<code>
         *  / code>生成文本的参数的数组。
         * 
         */
        public final static Field ARGUMENT =
                           new Field("message argument field");
    }

    // ===========================privates============================

    /**
     * The locale to use for formatting numbers and dates.
     * <p>
     *  用于格式化数字和日期的区域设置
     * 
     * 
     * @serial
     */
    private Locale locale;

    /**
     * The string that the formatted values are to be plugged into.  In other words, this
     * is the pattern supplied on construction with all of the {} expressions taken out.
     * <p>
     * 要插入格式化值的字符串换句话说,这是在构造时提供的所有{}表达式取出的模式
     * 
     * 
     * @serial
     */
    private String pattern = "";

    /** The initially expected number of subformats in the format */
    private static final int INITIAL_FORMATS = 10;

    /**
     * An array of formatters, which are used to format the arguments.
     * <p>
     *  格式化程序数组,用于格式化参数
     * 
     * 
     * @serial
     */
    private Format[] formats = new Format[INITIAL_FORMATS];

    /**
     * The positions where the results of formatting each argument are to be inserted
     * into the pattern.
     * <p>
     *  将每个参数的格式化结果插入到模式中的位置
     * 
     * 
     * @serial
     */
    private int[] offsets = new int[INITIAL_FORMATS];

    /**
     * The argument numbers corresponding to each formatter.  (The formatters are stored
     * in the order they occur in the pattern, not in the order in which the arguments
     * are specified.)
     * <p>
     *  与每个格式化程序相对应的参数号(格式化程序按照它们在模式中出现的顺序存储,而不是按指定参数的顺序存储)
     * 
     * 
     * @serial
     */
    private int[] argumentNumbers = new int[INITIAL_FORMATS];

    /**
     * One less than the number of entries in <code>offsets</code>.  Can also be thought of
     * as the index of the highest-numbered element in <code>offsets</code> that is being used.
     * All of these arrays should have the same number of elements being used as <code>offsets</code>
     * does, and so this variable suffices to tell us how many entries are in all of them.
     * <p>
     * 一个小于<code> offsets </code>中的条目数也可以被认为是正在使用的<code> offsets </code>中编号最大的元素的索引所有这些数组应该具有相同数量的元素被用作<code>
     *  offsets </code>,因此这个变量足以告诉我们在它们中有多少条目。
     * 
     * 
     * @serial
     */
    private int maxOffset = -1;

    /**
     * Internal routine used by format. If <code>characterIterators</code> is
     * non-null, AttributedCharacterIterator will be created from the
     * subformats as necessary. If <code>characterIterators</code> is null
     * and <code>fp</code> is non-null and identifies
     * <code>Field.MESSAGE_ARGUMENT</code>, the location of
     * the first replaced argument will be set in it.
     *
     * <p>
     *  格式使用的内部例程如果<code> characterIterators </code>是非空的,则将根据需要从子格式创建AttributedCharacterIterator如果<code> cha
     * racterIterators </code>为null,<code> fp </code> -null并标识<code> FieldMESSAGE_ARGUMENT </code>,则将在其中设置第一
     * 个替换的参数的位置。
     * 
     * 
     * @exception IllegalArgumentException if an argument in the
     *            <code>arguments</code> array is not of the type
     *            expected by the format element(s) that use it.
     */
    private StringBuffer subformat(Object[] arguments, StringBuffer result,
                                   FieldPosition fp, List<AttributedCharacterIterator> characterIterators) {
        // note: this implementation assumes a fast substring & index.
        // if this is not true, would be better to append chars one by one.
        int lastOffset = 0;
        int last = result.length();
        for (int i = 0; i <= maxOffset; ++i) {
            result.append(pattern.substring(lastOffset, offsets[i]));
            lastOffset = offsets[i];
            int argumentNumber = argumentNumbers[i];
            if (arguments == null || argumentNumber >= arguments.length) {
                result.append('{').append(argumentNumber).append('}');
                continue;
            }
            // int argRecursion = ((recursionProtection >> (argumentNumber*2)) & 0x3);
            if (false) { // if (argRecursion == 3){
                // prevent loop!!!
                result.append('\uFFFD');
            } else {
                Object obj = arguments[argumentNumber];
                String arg = null;
                Format subFormatter = null;
                if (obj == null) {
                    arg = "null";
                } else if (formats[i] != null) {
                    subFormatter = formats[i];
                    if (subFormatter instanceof ChoiceFormat) {
                        arg = formats[i].format(obj);
                        if (arg.indexOf('{') >= 0) {
                            subFormatter = new MessageFormat(arg, locale);
                            obj = arguments;
                            arg = null;
                        }
                    }
                } else if (obj instanceof Number) {
                    // format number if can
                    subFormatter = NumberFormat.getInstance(locale);
                } else if (obj instanceof Date) {
                    // format a Date if can
                    subFormatter = DateFormat.getDateTimeInstance(
                             DateFormat.SHORT, DateFormat.SHORT, locale);//fix
                } else if (obj instanceof String) {
                    arg = (String) obj;

                } else {
                    arg = obj.toString();
                    if (arg == null) arg = "null";
                }

                // At this point we are in two states, either subFormatter
                // is non-null indicating we should format obj using it,
                // or arg is non-null and we should use it as the value.

                if (characterIterators != null) {
                    // If characterIterators is non-null, it indicates we need
                    // to get the CharacterIterator from the child formatter.
                    if (last != result.length()) {
                        characterIterators.add(
                            createAttributedCharacterIterator(result.substring
                                                              (last)));
                        last = result.length();
                    }
                    if (subFormatter != null) {
                        AttributedCharacterIterator subIterator =
                                   subFormatter.formatToCharacterIterator(obj);

                        append(result, subIterator);
                        if (last != result.length()) {
                            characterIterators.add(
                                         createAttributedCharacterIterator(
                                         subIterator, Field.ARGUMENT,
                                         Integer.valueOf(argumentNumber)));
                            last = result.length();
                        }
                        arg = null;
                    }
                    if (arg != null && arg.length() > 0) {
                        result.append(arg);
                        characterIterators.add(
                                 createAttributedCharacterIterator(
                                 arg, Field.ARGUMENT,
                                 Integer.valueOf(argumentNumber)));
                        last = result.length();
                    }
                }
                else {
                    if (subFormatter != null) {
                        arg = subFormatter.format(obj);
                    }
                    last = result.length();
                    result.append(arg);
                    if (i == 0 && fp != null && Field.ARGUMENT.equals(
                                  fp.getFieldAttribute())) {
                        fp.setBeginIndex(last);
                        fp.setEndIndex(result.length());
                    }
                    last = result.length();
                }
            }
        }
        result.append(pattern.substring(lastOffset, pattern.length()));
        if (characterIterators != null && last != result.length()) {
            characterIterators.add(createAttributedCharacterIterator(
                                   result.substring(last)));
        }
        return result;
    }

    /**
     * Convenience method to append all the characters in
     * <code>iterator</code> to the StringBuffer <code>result</code>.
     * <p>
     * 方便方法将<code> iterator </code>中的所有字符附加到StringBuffer <code> result </code>
     * 
     */
    private void append(StringBuffer result, CharacterIterator iterator) {
        if (iterator.first() != CharacterIterator.DONE) {
            char aChar;

            result.append(iterator.first());
            while ((aChar = iterator.next()) != CharacterIterator.DONE) {
                result.append(aChar);
            }
        }
    }

    // Indices for segments
    private static final int SEG_RAW      = 0;
    private static final int SEG_INDEX    = 1;
    private static final int SEG_TYPE     = 2;
    private static final int SEG_MODIFIER = 3; // modifier or subformat

    // Indices for type keywords
    private static final int TYPE_NULL    = 0;
    private static final int TYPE_NUMBER  = 1;
    private static final int TYPE_DATE    = 2;
    private static final int TYPE_TIME    = 3;
    private static final int TYPE_CHOICE  = 4;

    private static final String[] TYPE_KEYWORDS = {
        "",
        "number",
        "date",
        "time",
        "choice"
    };

    // Indices for number modifiers
    private static final int MODIFIER_DEFAULT  = 0; // common in number and date-time
    private static final int MODIFIER_CURRENCY = 1;
    private static final int MODIFIER_PERCENT  = 2;
    private static final int MODIFIER_INTEGER  = 3;

    private static final String[] NUMBER_MODIFIER_KEYWORDS = {
        "",
        "currency",
        "percent",
        "integer"
    };

    // Indices for date-time modifiers
    private static final int MODIFIER_SHORT   = 1;
    private static final int MODIFIER_MEDIUM  = 2;
    private static final int MODIFIER_LONG    = 3;
    private static final int MODIFIER_FULL    = 4;

    private static final String[] DATE_TIME_MODIFIER_KEYWORDS = {
        "",
        "short",
        "medium",
        "long",
        "full"
    };

    // Date-time style values corresponding to the date-time modifiers.
    private static final int[] DATE_TIME_MODIFIERS = {
        DateFormat.DEFAULT,
        DateFormat.SHORT,
        DateFormat.MEDIUM,
        DateFormat.LONG,
        DateFormat.FULL,
    };

    private void makeFormat(int position, int offsetNumber,
                            StringBuilder[] textSegments)
    {
        String[] segments = new String[textSegments.length];
        for (int i = 0; i < textSegments.length; i++) {
            StringBuilder oneseg = textSegments[i];
            segments[i] = (oneseg != null) ? oneseg.toString() : "";
        }

        // get the argument number
        int argumentNumber;
        try {
            argumentNumber = Integer.parseInt(segments[SEG_INDEX]); // always unlocalized!
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("can't parse argument number: "
                                               + segments[SEG_INDEX], e);
        }
        if (argumentNumber < 0) {
            throw new IllegalArgumentException("negative argument number: "
                                               + argumentNumber);
        }

        // resize format information arrays if necessary
        if (offsetNumber >= formats.length) {
            int newLength = formats.length * 2;
            Format[] newFormats = new Format[newLength];
            int[] newOffsets = new int[newLength];
            int[] newArgumentNumbers = new int[newLength];
            System.arraycopy(formats, 0, newFormats, 0, maxOffset + 1);
            System.arraycopy(offsets, 0, newOffsets, 0, maxOffset + 1);
            System.arraycopy(argumentNumbers, 0, newArgumentNumbers, 0, maxOffset + 1);
            formats = newFormats;
            offsets = newOffsets;
            argumentNumbers = newArgumentNumbers;
        }
        int oldMaxOffset = maxOffset;
        maxOffset = offsetNumber;
        offsets[offsetNumber] = segments[SEG_RAW].length();
        argumentNumbers[offsetNumber] = argumentNumber;

        // now get the format
        Format newFormat = null;
        if (segments[SEG_TYPE].length() != 0) {
            int type = findKeyword(segments[SEG_TYPE], TYPE_KEYWORDS);
            switch (type) {
            case TYPE_NULL:
                // Type "" is allowed. e.g., "{0,}", "{0,,}", and "{0,,#}"
                // are treated as "{0}".
                break;

            case TYPE_NUMBER:
                switch (findKeyword(segments[SEG_MODIFIER], NUMBER_MODIFIER_KEYWORDS)) {
                case MODIFIER_DEFAULT:
                    newFormat = NumberFormat.getInstance(locale);
                    break;
                case MODIFIER_CURRENCY:
                    newFormat = NumberFormat.getCurrencyInstance(locale);
                    break;
                case MODIFIER_PERCENT:
                    newFormat = NumberFormat.getPercentInstance(locale);
                    break;
                case MODIFIER_INTEGER:
                    newFormat = NumberFormat.getIntegerInstance(locale);
                    break;
                default: // DecimalFormat pattern
                    try {
                        newFormat = new DecimalFormat(segments[SEG_MODIFIER],
                                                      DecimalFormatSymbols.getInstance(locale));
                    } catch (IllegalArgumentException e) {
                        maxOffset = oldMaxOffset;
                        throw e;
                    }
                    break;
                }
                break;

            case TYPE_DATE:
            case TYPE_TIME:
                int mod = findKeyword(segments[SEG_MODIFIER], DATE_TIME_MODIFIER_KEYWORDS);
                if (mod >= 0 && mod < DATE_TIME_MODIFIER_KEYWORDS.length) {
                    if (type == TYPE_DATE) {
                        newFormat = DateFormat.getDateInstance(DATE_TIME_MODIFIERS[mod],
                                                               locale);
                    } else {
                        newFormat = DateFormat.getTimeInstance(DATE_TIME_MODIFIERS[mod],
                                                               locale);
                    }
                } else {
                    // SimpleDateFormat pattern
                    try {
                        newFormat = new SimpleDateFormat(segments[SEG_MODIFIER], locale);
                    } catch (IllegalArgumentException e) {
                        maxOffset = oldMaxOffset;
                        throw e;
                    }
                }
                break;

            case TYPE_CHOICE:
                try {
                    // ChoiceFormat pattern
                    newFormat = new ChoiceFormat(segments[SEG_MODIFIER]);
                } catch (Exception e) {
                    maxOffset = oldMaxOffset;
                    throw new IllegalArgumentException("Choice Pattern incorrect: "
                                                       + segments[SEG_MODIFIER], e);
                }
                break;

            default:
                maxOffset = oldMaxOffset;
                throw new IllegalArgumentException("unknown format type: " +
                                                   segments[SEG_TYPE]);
            }
        }
        formats[offsetNumber] = newFormat;
    }

    private static final int findKeyword(String s, String[] list) {
        for (int i = 0; i < list.length; ++i) {
            if (s.equals(list[i]))
                return i;
        }

        // Try trimmed lowercase.
        String ls = s.trim().toLowerCase(Locale.ROOT);
        if (ls != s) {
            for (int i = 0; i < list.length; ++i) {
                if (ls.equals(list[i]))
                    return i;
            }
        }
        return -1;
    }

    private static final void copyAndFixQuotes(String source, int start, int end,
                                               StringBuilder target) {
        boolean quoted = false;

        for (int i = start; i < end; ++i) {
            char ch = source.charAt(i);
            if (ch == '{') {
                if (!quoted) {
                    target.append('\'');
                    quoted = true;
                }
                target.append(ch);
            } else if (ch == '\'') {
                target.append("''");
            } else {
                if (quoted) {
                    target.append('\'');
                    quoted = false;
                }
                target.append(ch);
            }
        }
        if (quoted) {
            target.append('\'');
        }
    }

    /**
     * After reading an object from the input stream, do a simple verification
     * to maintain class invariants.
     * <p>
     *  从输入流读取对象后,进行简单的验证以维护类不变量
     * 
     * @throws InvalidObjectException if the objects read from the stream is invalid.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        boolean isValid = maxOffset >= -1
                && formats.length > maxOffset
                && offsets.length > maxOffset
                && argumentNumbers.length > maxOffset;
        if (isValid) {
            int lastOffset = pattern.length() + 1;
            for (int i = maxOffset; i >= 0; --i) {
                if ((offsets[i] < 0) || (offsets[i] > lastOffset)) {
                    isValid = false;
                    break;
                } else {
                    lastOffset = offsets[i];
                }
            }
        }
        if (!isValid) {
            throw new InvalidObjectException("Could not reconstruct MessageFormat from corrupt stream.");
        }
    }
}
