/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright IBM Corp. 1996-1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc 1996,1997  - 保留所有权利(C)版权所有IBM Corp 1996-1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc拥有版权和所有权。
 * 这些资料根据Taligent和Sun之间的许可协议的条款提供此技术受多个美国和国际专利保护Taligent是Taligent的注册商标。Taligent是Taligent的注册商标。
 * 
 */

package java.text;

import java.text.Normalizer;
import java.util.Vector;
import java.util.Locale;

/**
 * The <code>RuleBasedCollator</code> class is a concrete subclass of
 * <code>Collator</code> that provides a simple, data-driven, table
 * collator.  With this class you can create a customized table-based
 * <code>Collator</code>.  <code>RuleBasedCollator</code> maps
 * characters to sort keys.
 *
 * <p>
 * <code>RuleBasedCollator</code> has the following restrictions
 * for efficiency (other subclasses may be used for more complex languages) :
 * <ol>
 * <li>If a special collation rule controlled by a &lt;modifier&gt; is
      specified it applies to the whole collator object.
 * <li>All non-mentioned characters are at the end of the
 *     collation order.
 * </ol>
 *
 * <p>
 * The collation table is composed of a list of collation rules, where each
 * rule is of one of three forms:
 * <pre>
 *    &lt;modifier&gt;
 *    &lt;relation&gt; &lt;text-argument&gt;
 *    &lt;reset&gt; &lt;text-argument&gt;
 * </pre>
 * The definitions of the rule elements is as follows:
 * <UL>
 *    <LI><strong>Text-Argument</strong>: A text-argument is any sequence of
 *        characters, excluding special characters (that is, common
 *        whitespace characters [0009-000D, 0020] and rule syntax characters
 *        [0021-002F, 003A-0040, 005B-0060, 007B-007E]). If those
 *        characters are desired, you can put them in single quotes
 *        (e.g. ampersand =&gt; '&amp;'). Note that unquoted white space characters
 *        are ignored; e.g. <code>b c</code> is treated as <code>bc</code>.
 *    <LI><strong>Modifier</strong>: There are currently two modifiers that
 *        turn on special collation rules.
 *        <UL>
 *            <LI>'@' : Turns on backwards sorting of accents (secondary
 *                      differences), as in French.
 *            <LI>'!' : Turns on Thai/Lao vowel-consonant swapping.  If this
 *                      rule is in force when a Thai vowel of the range
 *                      &#92;U0E40-&#92;U0E44 precedes a Thai consonant of the range
 *                      &#92;U0E01-&#92;U0E2E OR a Lao vowel of the range &#92;U0EC0-&#92;U0EC4
 *                      precedes a Lao consonant of the range &#92;U0E81-&#92;U0EAE then
 *                      the vowel is placed after the consonant for collation
 *                      purposes.
 *        </UL>
 *        <p>'@' : Indicates that accents are sorted backwards, as in French.
 *    <LI><strong>Relation</strong>: The relations are the following:
 *        <UL>
 *            <LI>'&lt;' : Greater, as a letter difference (primary)
 *            <LI>';' : Greater, as an accent difference (secondary)
 *            <LI>',' : Greater, as a case difference (tertiary)
 *            <LI>'=' : Equal
 *        </UL>
 *    <LI><strong>Reset</strong>: There is a single reset
 *        which is used primarily for contractions and expansions, but which
 *        can also be used to add a modification at the end of a set of rules.
 *        <p>'&amp;' : Indicates that the next rule follows the position to where
 *            the reset text-argument would be sorted.
 * </UL>
 *
 * <p>
 * This sounds more complicated than it is in practice. For example, the
 * following are equivalent ways of expressing the same thing:
 * <blockquote>
 * <pre>
 * a &lt; b &lt; c
 * a &lt; b &amp; b &lt; c
 * a &lt; c &amp; a &lt; b
 * </pre>
 * </blockquote>
 * Notice that the order is important, as the subsequent item goes immediately
 * after the text-argument. The following are not equivalent:
 * <blockquote>
 * <pre>
 * a &lt; b &amp; a &lt; c
 * a &lt; c &amp; a &lt; b
 * </pre>
 * </blockquote>
 * Either the text-argument must already be present in the sequence, or some
 * initial substring of the text-argument must be present. (e.g. "a &lt; b &amp; ae &lt;
 * e" is valid since "a" is present in the sequence before "ae" is reset). In
 * this latter case, "ae" is not entered and treated as a single character;
 * instead, "e" is sorted as if it were expanded to two characters: "a"
 * followed by an "e". This difference appears in natural languages: in
 * traditional Spanish "ch" is treated as though it contracts to a single
 * character (expressed as "c &lt; ch &lt; d"), while in traditional German
 * a-umlaut is treated as though it expanded to two characters
 * (expressed as "a,A &lt; b,B ... &amp;ae;&#92;u00e3&amp;AE;&#92;u00c3").
 * [&#92;u00e3 and &#92;u00c3 are, of course, the escape sequences for a-umlaut.]
 * <p>
 * <strong>Ignorable Characters</strong>
 * <p>
 * For ignorable characters, the first rule must start with a relation (the
 * examples we have used above are really fragments; "a &lt; b" really should be
 * "&lt; a &lt; b"). If, however, the first relation is not "&lt;", then all the all
 * text-arguments up to the first "&lt;" are ignorable. For example, ", - &lt; a &lt; b"
 * makes "-" an ignorable character, as we saw earlier in the word
 * "black-birds". In the samples for different languages, you see that most
 * accents are ignorable.
 *
 * <p><strong>Normalization and Accents</strong>
 * <p>
 * <code>RuleBasedCollator</code> automatically processes its rule table to
 * include both pre-composed and combining-character versions of
 * accented characters.  Even if the provided rule string contains only
 * base characters and separate combining accent characters, the pre-composed
 * accented characters matching all canonical combinations of characters from
 * the rule string will be entered in the table.
 * <p>
 * This allows you to use a RuleBasedCollator to compare accented strings
 * even when the collator is set to NO_DECOMPOSITION.  There are two caveats,
 * however.  First, if the strings to be collated contain combining
 * sequences that may not be in canonical order, you should set the collator to
 * CANONICAL_DECOMPOSITION or FULL_DECOMPOSITION to enable sorting of
 * combining sequences.  Second, if the strings contain characters with
 * compatibility decompositions (such as full-width and half-width forms),
 * you must use FULL_DECOMPOSITION, since the rule tables only include
 * canonical mappings.
 *
 * <p><strong>Errors</strong>
 * <p>
 * The following are errors:
 * <UL>
 *     <LI>A text-argument contains unquoted punctuation symbols
 *        (e.g. "a &lt; b-c &lt; d").
 *     <LI>A relation or reset character not followed by a text-argument
 *        (e.g. "a &lt; ,b").
 *     <LI>A reset where the text-argument (or an initial substring of the
 *         text-argument) is not already in the sequence.
 *         (e.g. "a &lt; b &amp; e &lt; f")
 * </UL>
 * If you produce one of these errors, a <code>RuleBasedCollator</code> throws
 * a <code>ParseException</code>.
 *
 * <p><strong>Examples</strong>
 * <p>Simple:     "&lt; a &lt; b &lt; c &lt; d"
 * <p>Norwegian:  "&lt; a, A &lt; b, B &lt; c, C &lt; d, D &lt; e, E &lt; f, F
 *                 &lt; g, G &lt; h, H &lt; i, I &lt; j, J &lt; k, K &lt; l, L
 *                 &lt; m, M &lt; n, N &lt; o, O &lt; p, P &lt; q, Q &lt; r, R
 *                 &lt; s, S &lt; t, T &lt; u, U &lt; v, V &lt; w, W &lt; x, X
 *                 &lt; y, Y &lt; z, Z
 *                 &lt; &#92;u00E6, &#92;u00C6
 *                 &lt; &#92;u00F8, &#92;u00D8
 *                 &lt; &#92;u00E5 = a&#92;u030A, &#92;u00C5 = A&#92;u030A;
 *                      aa, AA"
 *
 * <p>
 * To create a <code>RuleBasedCollator</code> object with specialized
 * rules tailored to your needs, you construct the <code>RuleBasedCollator</code>
 * with the rules contained in a <code>String</code> object. For example:
 * <blockquote>
 * <pre>
 * String simple = "&lt; a&lt; b&lt; c&lt; d";
 * RuleBasedCollator mySimple = new RuleBasedCollator(simple);
 * </pre>
 * </blockquote>
 * Or:
 * <blockquote>
 * <pre>
 * String Norwegian = "&lt; a, A &lt; b, B &lt; c, C &lt; d, D &lt; e, E &lt; f, F &lt; g, G &lt; h, H &lt; i, I" +
 *                    "&lt; j, J &lt; k, K &lt; l, L &lt; m, M &lt; n, N &lt; o, O &lt; p, P &lt; q, Q &lt; r, R" +
 *                    "&lt; s, S &lt; t, T &lt; u, U &lt; v, V &lt; w, W &lt; x, X &lt; y, Y &lt; z, Z" +
 *                    "&lt; &#92;u00E6, &#92;u00C6" +     // Latin letter ae &amp; AE
 *                    "&lt; &#92;u00F8, &#92;u00D8" +     // Latin letter o &amp; O with stroke
 *                    "&lt; &#92;u00E5 = a&#92;u030A," +  // Latin letter a with ring above
 *                    "  &#92;u00C5 = A&#92;u030A;" +  // Latin letter A with ring above
 *                    "  aa, AA";
 * RuleBasedCollator myNorwegian = new RuleBasedCollator(Norwegian);
 * </pre>
 * </blockquote>
 *
 * <p>
 * A new collation rules string can be created by concatenating rules
 * strings. For example, the rules returned by {@link #getRules()} could
 * be concatenated to combine multiple <code>RuleBasedCollator</code>s.
 *
 * <p>
 * The following example demonstrates how to change the order of
 * non-spacing accents,
 * <blockquote>
 * <pre>
 * // old rule
 * String oldRules = "=&#92;u0301;&#92;u0300;&#92;u0302;&#92;u0308"    // main accents
 *                 + ";&#92;u0327;&#92;u0303;&#92;u0304;&#92;u0305"    // main accents
 *                 + ";&#92;u0306;&#92;u0307;&#92;u0309;&#92;u030A"    // main accents
 *                 + ";&#92;u030B;&#92;u030C;&#92;u030D;&#92;u030E"    // main accents
 *                 + ";&#92;u030F;&#92;u0310;&#92;u0311;&#92;u0312"    // main accents
 *                 + "&lt; a , A ; ae, AE ; &#92;u00e6 , &#92;u00c6"
 *                 + "&lt; b , B &lt; c, C &lt; e, E &amp; C &lt; d, D";
 * // change the order of accent characters
 * String addOn = "&amp; &#92;u0300 ; &#92;u0308 ; &#92;u0302";
 * RuleBasedCollator myCollator = new RuleBasedCollator(oldRules + addOn);
 * </pre>
 * </blockquote>
 *
 * <p>
 * <code> RuleBasedCollat​​or </code>类是<code> Collat​​or </code>的一个具体子类,提供了一个简单的,数据驱动的表整理器。
 * 使用这个类,你可以创建一个自定义的基于表的<code> Collat​​or < / code> <code> RuleBasedCollat​​or </code>将字符映射到排序键。
 * 
 * <p>
 *  <code> RuleBasedCollat​​or </code>对效率有以下限制(其他子类可用于更复杂的语言)：
 * <ol>
 *  <li>如果由&lt; modifier&gt;控制的特殊归类规则指定它适用于整个collat​​or对象<li>所有未提到的字符都在排序规则顺序的结尾
 * </ol>
 * 
 * <p>
 *  排序规则表由排序规则列表组成,其中每个规则是以下三种形式之一：
 * <pre>
 * &lt; modifier&gt; &lt;关系&gt; &lt; text-argument&gt; &lt; reset&gt; &lt; text-argument&gt;
 * </pre>
 *  规则元素的定义如下：
 * <UL>
 *  <LI> <strong>文本参数</strong>：文本参数是任何字符序列,不包括特殊字符(即常用空格字符[0009-000D,0020])和规则语法字符[0021-002F,如果需要这些字符,可以
 * 将它们放在单引号中(例如&符号&gt;'&amp;')注意,不带引号的空格字符将被忽略;例如<code> b c </code>被视为<code> bc </code> <LI> <strong>修饰符
 * </strong>：目前有两个修饰符打开特殊排序规则。
 * <UL>
 * <LI>'@'：打开重音的反向排序(次要差异),如法语<LI>"！ ：打开泰语/老挝元音辅音交换如果范围\\ U0E40- \\ U0E44范围内的泰国元音在范围\\ U0E01- \\ U0E2E范围
 * 之前的泰语辅音或者范围\\ U0EC0- \\ U0EC4在范围\\ U0E81- \\ U0EAE的老挝辅音之前,则元音被放置在辅音之后用于整理目的。
 * </UL>
 *  <p>'@'：表示口音按照法语<LI> <strong>关系</strong>向后排序：关系如下：
 * <UL>
 *  <LI>'&lt;' ：大,作为字母差异(主)<LI>'; ：较大,作为重音差(次要)<LI>','：较大,作为大小写差异(第三)<LI>'='：等于
 * </UL>
 * <LI> <strong>重置</strong>：有一个主要用于收缩和展开的重置,但也可用于在一组规则的末尾添加修改<p>'&amp; '' ：表示下一个规则在复位文本参数将被排序的位置之后
 * </UL>
 * 
 * <p>
 *  这听起来比在实践中更复杂例如,以下是表达相同事物的等效方式：
 * <blockquote>
 * <pre>
 *  例如, b < c a < b&amp; b < c a < c&amp;例如, b
 * </pre>
 * </blockquote>
 *  请注意,顺序很重要,因为后续项紧接在text参数之后。以下内容不等效：
 * <blockquote>
 * <pre>
 *  例如, b&amp;例如, c a < c&amp;例如, b
 * </pre>
 * </blockquote>
 * 文本参数必须已经存在于序列中,或者文本参数的一些初始子串必须存在(例如"a&lt; b&amp; ae&lt; e"是有效的,因为在序列中存在"a"在"ae"被重置之前)在后一种情况下,不输入"ae"并
 * 将其视为单个字符;相反,"e"被排序,好像它被扩展为两个字符："a",后面是"e"。
 * 这种差异出现在自然语言中：在传统的西班牙语中"ch"被看作好象它收缩到一个单一的字符作为"c <ch <d"),而在传统的德语中,a-umlaut被视为扩展为两个字符(表示为"a,A <b,B&amp; ae; \\ u00e3&amp; u00c3")[\\ u00e3和\\ u00c3当然是a-umlaut的转义序列]。
 * <p>
 * <strong>可忽略字符</strong>
 * <p>
 *  对于可忽略的字符,第一个规则必须以关系开头(我们上面使用的例子是真正的片段;"a&lt; b"应该是"&lt; a&lt; b")。
 *  "&lt;",则所有所有文本参数直到第一个"&lt;"是可忽略的例如,", - &lt; a&lt; b"使" - "一个可忽略的字符,我们在前面看到"黑鸟"一词在不同语言的样本中,你看到大多数口音是
 * 可忽略的。
 *  对于可忽略的字符,第一个规则必须以关系开头(我们上面使用的例子是真正的片段;"a&lt; b"应该是"&lt; a&lt; b")。
 * 
 *  <p> <strong>规范化和重音</strong>
 * <p>
 * <code> RuleBasedCollat​​or </code>自动处理其规则表以包括重音字符的预组合和组合字符版本即使提供的规则字符串仅包含基本字符和单独的组合重音字符,预组成的重音字符匹配将在表
 * 中输入来自规则字符串的所有规范字符组合。
 * <p>
 * 这允许您使用RuleBasedCollat​​or来比较重音字符串,即使将collat​​or设置为NO_DECOMPOSITION有两个注意事项,但是首先,如果要整理的字符串包含可能不是按照规范顺序的
 * 组合序列,您应该将collat​​or设置为CANONICAL_DECOMPOSITION或FULL_DECOMPOSITION以启用组合序列的排序其次,如果字符串包含具有兼容性分解的字符(例如全角和半
 * 角形式),则必须使用FULL_DECOMPOSITION,因为规则表只包括规范映射。
 * 
 *  <p> <strong>错误</strong>
 * <p>
 *  以下是错误：
 * <UL>
 * <LI>文本参数包含无引号的标点符号(例如"a <bc <d")<LI>没有文本参数(例如"a <,b")<LI>的关系或复位字符>其中文本参数(或文本参数的初始子串)尚未在序列中的重置(例如"a <b&amp; e <f")。
 * </UL>
 *  如果产生其中一个错误,<code> RuleBasedCollat​​or </code>会抛出<code> ParseException </code>
 * 
 * <P> <STRONG>例子</STRONG> <p>简单：其中,A&LT; B&LT; C&LT; d <P>挪威：其中,A,A&LT; B,B&LT; C, C&LT; D,D&LT; E,E&LT
 * ; F,F&LT;克,&LT; H,H&LT; I,I LT;,j将&LT; K- K&LT; L,L&LT;男, M&LT; N,N&LT; O,O-&LT; p,p&LT;问中,q,R,R&LT; 
 * S,S&LT; T,T&LT; U,U&LT; V,V&LT; W, W&所述; X中,X,Y,Y LT; Z,z,其中; \\ u00E6 \\ u00C6&下; \\ u00F8 \\ u00D8&
 * 
 * @see        Collator
 * @see        CollationElementIterator
 * @author     Helena Shih, Laura Werner, Richard Gillam
 */
public class RuleBasedCollator extends Collator{
    // IMPLEMENTATION NOTES:  The implementation of the collation algorithm is
    // divided across three classes: RuleBasedCollator, RBCollationTables, and
    // CollationElementIterator.  RuleBasedCollator contains the collator's
    // transient state and includes the code that uses the other classes to
    // implement comparison and sort-key building.  RuleBasedCollator also
    // contains the logic to handle French secondary accent sorting.
    // A RuleBasedCollator has two CollationElementIterators.  State doesn't
    // need to be preserved in these objects between calls to compare() or
    // getCollationKey(), but the objects persist anyway to avoid wasting extra
    // creation time.  compare() and getCollationKey() are synchronized to ensure
    // thread safety with this scheme.  The CollationElementIterator is responsible
    // for generating collation elements from strings and returning one element at
    // a time (sometimes there's a one-to-many or many-to-one mapping between
    // characters and collation elements-- this class handles that).
    // CollationElementIterator depends on RBCollationTables, which contains the
    // collator's static state.  RBCollationTables contains the actual data
    // tables specifying the collation order of characters for a particular locale
    // or use.  It also contains the base logic that CollationElementIterator
    // uses to map from characters to collation elements.  A single RBCollationTables
    // object is shared among all RuleBasedCollators for the same locale, and
    // thus by all the CollationElementIterators they create.

    /**
     * RuleBasedCollator constructor.  This takes the table rules and builds
     * a collation table out of them.  Please see RuleBasedCollator class
     * description for more details on the collation rule syntax.
     * <p>
     * 下; \\ u00E5 =一个\\ u030A \\ u00C5 =一个\\ u030A; AA,AA'。
     * 
     * <p>
     *  要创建一个<代码> RuleBasedCollat​​or </code>的使用为您量身定做专门的规则对象,构造的<code> RuleBasedCollat​​or </代码>与包含在<代码>规则的
     * 字符串</code>对象。
     * 例如：。
     * <blockquote>
     * <pre>
     *  简单的字符串="&LT; A&LT; B&LT; C&LT; D组; RuleBasedCollat​​or mySimple =新RuleBasedCollat​​or(简单);
     * </pre>
     * </blockquote>
     *  或者：
     * <blockquote>
     * <pre>
     * 串挪威="&LT; A,A&LT; B,B&LT; C C&LT; D,D&LT; E,E&LT; F,F&LT;克,&LT; H,H&LT;我,我+&LT;,j将&LT; K- K&LT; L,L&L
     * T; M,M&LT; N,N&LT; O,O-&LT; p,p&LT; Q,Q&LT; R,R + &LT; S,S&LT; T,T&LT; U,U&LT; V,V&LT; W,W&LT; X中,X,Y
     * ,Y&LT; Z,Z +&LT; \\ u00E6 \\ u00C6 + //拉丁字母AE&安培; AE&LT; \\ u00F8 \\ u00D8 + //拉丁字母的O&amp;并与笔划LT; \\ 
     * u00E5 = A \\ u030A \\ u00C5 = A \\ u030A"与环以上+ //拉丁字母A";"与上述环AA,AA + //拉丁字母A; RuleBasedCollat​​or myN
     * orwegian =新RuleBasedCollat​​or(挪威);。
     * </pre>
     * </blockquote>
     * 
     * <p>
     * 可以通过连接规则字符串创建新的排序规则字符串例如,{@link #getRules()}返回的规则可以连接以组合多个<code> RuleBasedCollat​​or </code>
     * 
     * <p>
     *  以下示例演示如何更改非间距重音符的顺序,
     * <blockquote>
     * <pre>
     * //旧规则字符串oldRules ="= \\ u0301; \\ u0300; \\ u0302; \\ u0308"// main accents +"; \\ u0327; \\ u0303; \
     * \ u0304; \\ u0305"// main accents +"; \\ u0306; \\ u0307; \\ u0309; \\ u030A"// main accents +"; \\ u
     * 030B; \\ u030C; \\ u030D; \\ u030E"// main accents +"; \\ u030F; \\ u0310; \\ u0311; \\ u0312"// main
     *  a","a","a","a","b","c","c","e"和"c" //改变重音字符的顺序String addOn ="&amp; \\ u0300; \\ u0308; \\ u0302"; Ru
     * leBasedCollat​​or myCollat​​or = new RuleBasedCollat​​or(oldRules + addOn);。
     * </pre>
     * </blockquote>
     * 
     * 
     * @see java.util.Locale
     * @param rules the collation rules to build the collation table from.
     * @exception ParseException A format exception
     * will be thrown if the build process of the rules fails. For
     * example, build rule "a &lt; ? &lt; d" will cause the constructor to
     * throw the ParseException because the '?' is not quoted.
     */
    public RuleBasedCollator(String rules) throws ParseException {
        this(rules, Collator.CANONICAL_DECOMPOSITION);
    }

    /**
     * RuleBasedCollator constructor.  This takes the table rules and builds
     * a collation table out of them.  Please see RuleBasedCollator class
     * description for more details on the collation rule syntax.
     * <p>
     * 
     * @see java.util.Locale
     * @param rules the collation rules to build the collation table from.
     * @param decomp the decomposition strength used to build the
     * collation table and to perform comparisons.
     * @exception ParseException A format exception
     * will be thrown if the build process of the rules fails. For
     * example, build rule "a < ? < d" will cause the constructor to
     * throw the ParseException because the '?' is not quoted.
     */
    RuleBasedCollator(String rules, int decomp) throws ParseException {
        setStrength(Collator.TERTIARY);
        setDecomposition(decomp);
        tables = new RBCollationTables(rules, decomp);
    }

    /**
     * "Copy constructor."  Used in clone() for performance.
     * <p>
     * RuleBasedCollat​​or构造函数这需要表规则并构建一个排序规则表。有关排序规则语法的更多详细信息,请参阅RuleBasedCollat​​or类描述
     * 
     */
    private RuleBasedCollator(RuleBasedCollator that) {
        setStrength(that.getStrength());
        setDecomposition(that.getDecomposition());
        tables = that.tables;
    }

    /**
     * Gets the table-based rules for the collation object.
     * <p>
     *  RuleBasedCollat​​or构造函数这需要表规则并构建一个排序规则表。有关排序规则语法的更多详细信息,请参阅RuleBasedCollat​​or类描述
     * 
     * 
     * @return returns the collation rules that the table collation object
     * was created from.
     */
    public String getRules()
    {
        return tables.getRules();
    }

    /**
     * Returns a CollationElementIterator for the given String.
     *
     * <p>
     *  "复制构造函数"用于clone()中的性能
     * 
     * 
     * @param source the string to be collated
     * @return a {@code CollationElementIterator} object
     * @see java.text.CollationElementIterator
     */
    public CollationElementIterator getCollationElementIterator(String source) {
        return new CollationElementIterator( source, this );
    }

    /**
     * Returns a CollationElementIterator for the given CharacterIterator.
     *
     * <p>
     *  获取排序规则对象的基于表的规则
     * 
     * 
     * @param source the character iterator to be collated
     * @return a {@code CollationElementIterator} object
     * @see java.text.CollationElementIterator
     * @since 1.2
     */
    public CollationElementIterator getCollationElementIterator(
                                                CharacterIterator source) {
        return new CollationElementIterator( source, this );
    }

    /**
     * Compares the character data stored in two different strings based on the
     * collation rules.  Returns information about whether a string is less
     * than, greater than or equal to another string in a language.
     * This can be overriden in a subclass.
     *
     * <p>
     *  返回给定String的Collat​​ionElementIterator
     * 
     * 
     * @exception NullPointerException if <code>source</code> or <code>target</code> is null.
     */
    public synchronized int compare(String source, String target)
    {
        if (source == null || target == null) {
            throw new NullPointerException();
        }

        // The basic algorithm here is that we use CollationElementIterators
        // to step through both the source and target strings.  We compare each
        // collation element in the source string against the corresponding one
        // in the target, checking for differences.
        //
        // If a difference is found, we set <result> to LESS or GREATER to
        // indicate whether the source string is less or greater than the target.
        //
        // However, it's not that simple.  If we find a tertiary difference
        // (e.g. 'A' vs. 'a') near the beginning of a string, it can be
        // overridden by a primary difference (e.g. "A" vs. "B") later in
        // the string.  For example, "AA" < "aB", even though 'A' > 'a'.
        //
        // To keep track of this, we use strengthResult to keep track of the
        // strength of the most significant difference that has been found
        // so far.  When we find a difference whose strength is greater than
        // strengthResult, it overrides the last difference (if any) that
        // was found.

        int result = Collator.EQUAL;

        if (sourceCursor == null) {
            sourceCursor = getCollationElementIterator(source);
        } else {
            sourceCursor.setText(source);
        }
        if (targetCursor == null) {
            targetCursor = getCollationElementIterator(target);
        } else {
            targetCursor.setText(target);
        }

        int sOrder = 0, tOrder = 0;

        boolean initialCheckSecTer = getStrength() >= Collator.SECONDARY;
        boolean checkSecTer = initialCheckSecTer;
        boolean checkTertiary = getStrength() >= Collator.TERTIARY;

        boolean gets = true, gett = true;

        while(true) {
            // Get the next collation element in each of the strings, unless
            // we've been requested to skip it.
            if (gets) sOrder = sourceCursor.next(); else gets = true;
            if (gett) tOrder = targetCursor.next(); else gett = true;

            // If we've hit the end of one of the strings, jump out of the loop
            if ((sOrder == CollationElementIterator.NULLORDER)||
                (tOrder == CollationElementIterator.NULLORDER))
                break;

            int pSOrder = CollationElementIterator.primaryOrder(sOrder);
            int pTOrder = CollationElementIterator.primaryOrder(tOrder);

            // If there's no difference at this position, we can skip it
            if (sOrder == tOrder) {
                if (tables.isFrenchSec() && pSOrder != 0) {
                    if (!checkSecTer) {
                        // in french, a secondary difference more to the right is stronger,
                        // so accents have to be checked with each base element
                        checkSecTer = initialCheckSecTer;
                        // but tertiary differences are less important than the first
                        // secondary difference, so checking tertiary remains disabled
                        checkTertiary = false;
                    }
                }
                continue;
            }

            // Compare primary differences first.
            if ( pSOrder != pTOrder )
            {
                if (sOrder == 0) {
                    // The entire source element is ignorable.
                    // Skip to the next source element, but don't fetch another target element.
                    gett = false;
                    continue;
                }
                if (tOrder == 0) {
                    gets = false;
                    continue;
                }

                // The source and target elements aren't ignorable, but it's still possible
                // for the primary component of one of the elements to be ignorable....

                if (pSOrder == 0)  // primary order in source is ignorable
                {
                    // The source's primary is ignorable, but the target's isn't.  We treat ignorables
                    // as a secondary difference, so remember that we found one.
                    if (checkSecTer) {
                        result = Collator.GREATER;  // (strength is SECONDARY)
                        checkSecTer = false;
                    }
                    // Skip to the next source element, but don't fetch another target element.
                    gett = false;
                }
                else if (pTOrder == 0)
                {
                    // record differences - see the comment above.
                    if (checkSecTer) {
                        result = Collator.LESS;  // (strength is SECONDARY)
                        checkSecTer = false;
                    }
                    // Skip to the next source element, but don't fetch another target element.
                    gets = false;
                } else {
                    // Neither of the orders is ignorable, and we already know that the primary
                    // orders are different because of the (pSOrder != pTOrder) test above.
                    // Record the difference and stop the comparison.
                    if (pSOrder < pTOrder) {
                        return Collator.LESS;  // (strength is PRIMARY)
                    } else {
                        return Collator.GREATER;  // (strength is PRIMARY)
                    }
                }
            } else { // else of if ( pSOrder != pTOrder )
                // primary order is the same, but complete order is different. So there
                // are no base elements at this point, only ignorables (Since the strings are
                // normalized)

                if (checkSecTer) {
                    // a secondary or tertiary difference may still matter
                    short secSOrder = CollationElementIterator.secondaryOrder(sOrder);
                    short secTOrder = CollationElementIterator.secondaryOrder(tOrder);
                    if (secSOrder != secTOrder) {
                        // there is a secondary difference
                        result = (secSOrder < secTOrder) ? Collator.LESS : Collator.GREATER;
                                                // (strength is SECONDARY)
                        checkSecTer = false;
                        // (even in french, only the first secondary difference within
                        //  a base character matters)
                    } else {
                        if (checkTertiary) {
                            // a tertiary difference may still matter
                            short terSOrder = CollationElementIterator.tertiaryOrder(sOrder);
                            short terTOrder = CollationElementIterator.tertiaryOrder(tOrder);
                            if (terSOrder != terTOrder) {
                                // there is a tertiary difference
                                result = (terSOrder < terTOrder) ? Collator.LESS : Collator.GREATER;
                                                // (strength is TERTIARY)
                                checkTertiary = false;
                            }
                        }
                    }
                } // if (checkSecTer)

            }  // if ( pSOrder != pTOrder )
        } // while()

        if (sOrder != CollationElementIterator.NULLORDER) {
            // (tOrder must be CollationElementIterator::NULLORDER,
            //  since this point is only reached when sOrder or tOrder is NULLORDER.)
            // The source string has more elements, but the target string hasn't.
            do {
                if (CollationElementIterator.primaryOrder(sOrder) != 0) {
                    // We found an additional non-ignorable base character in the source string.
                    // This is a primary difference, so the source is greater
                    return Collator.GREATER; // (strength is PRIMARY)
                }
                else if (CollationElementIterator.secondaryOrder(sOrder) != 0) {
                    // Additional secondary elements mean the source string is greater
                    if (checkSecTer) {
                        result = Collator.GREATER;  // (strength is SECONDARY)
                        checkSecTer = false;
                    }
                }
            } while ((sOrder = sourceCursor.next()) != CollationElementIterator.NULLORDER);
        }
        else if (tOrder != CollationElementIterator.NULLORDER) {
            // The target string has more elements, but the source string hasn't.
            do {
                if (CollationElementIterator.primaryOrder(tOrder) != 0)
                    // We found an additional non-ignorable base character in the target string.
                    // This is a primary difference, so the source is less
                    return Collator.LESS; // (strength is PRIMARY)
                else if (CollationElementIterator.secondaryOrder(tOrder) != 0) {
                    // Additional secondary elements in the target mean the source string is less
                    if (checkSecTer) {
                        result = Collator.LESS;  // (strength is SECONDARY)
                        checkSecTer = false;
                    }
                }
            } while ((tOrder = targetCursor.next()) != CollationElementIterator.NULLORDER);
        }

        // For IDENTICAL comparisons, we use a bitwise character comparison
        // as a tiebreaker if all else is equal
        if (result == 0 && getStrength() == IDENTICAL) {
            int mode = getDecomposition();
            Normalizer.Form form;
            if (mode == CANONICAL_DECOMPOSITION) {
                form = Normalizer.Form.NFD;
            } else if (mode == FULL_DECOMPOSITION) {
                form = Normalizer.Form.NFKD;
            } else {
                return source.compareTo(target);
            }

            String sourceDecomposition = Normalizer.normalize(source, form);
            String targetDecomposition = Normalizer.normalize(target, form);
            return sourceDecomposition.compareTo(targetDecomposition);
        }
        return result;
    }

    /**
     * Transforms the string into a series of characters that can be compared
     * with CollationKey.compareTo. This overrides java.text.Collator.getCollationKey.
     * It can be overriden in a subclass.
     * <p>
     *  返回给定CharacterIterator的Collat​​ionElementIterator
     * 
     */
    public synchronized CollationKey getCollationKey(String source)
    {
        //
        // The basic algorithm here is to find all of the collation elements for each
        // character in the source string, convert them to a char representation,
        // and put them into the collation key.  But it's trickier than that.
        // Each collation element in a string has three components: primary (A vs B),
        // secondary (A vs A-acute), and tertiary (A' vs a); and a primary difference
        // at the end of a string takes precedence over a secondary or tertiary
        // difference earlier in the string.
        //
        // To account for this, we put all of the primary orders at the beginning of the
        // string, followed by the secondary and tertiary orders, separated by nulls.
        //
        // Here's a hypothetical example, with the collation element represented as
        // a three-digit number, one digit for primary, one for secondary, etc.
        //
        // String:              A     a     B   \u00e9 <--(e-acute)
        // Collation Elements: 101   100   201  510
        //
        // Collation Key:      1125<null>0001<null>1010
        //
        // To make things even trickier, secondary differences (accent marks) are compared
        // starting at the *end* of the string in languages with French secondary ordering.
        // But when comparing the accent marks on a single base character, they are compared
        // from the beginning.  To handle this, we reverse all of the accents that belong
        // to each base character, then we reverse the entire string of secondary orderings
        // at the end.  Taking the same example above, a French collator might return
        // this instead:
        //
        // Collation Key:      1125<null>1000<null>1010
        //
        if (source == null)
            return null;

        if (primResult == null) {
            primResult = new StringBuffer();
            secResult = new StringBuffer();
            terResult = new StringBuffer();
        } else {
            primResult.setLength(0);
            secResult.setLength(0);
            terResult.setLength(0);
        }
        int order = 0;
        boolean compareSec = (getStrength() >= Collator.SECONDARY);
        boolean compareTer = (getStrength() >= Collator.TERTIARY);
        int secOrder = CollationElementIterator.NULLORDER;
        int terOrder = CollationElementIterator.NULLORDER;
        int preSecIgnore = 0;

        if (sourceCursor == null) {
            sourceCursor = getCollationElementIterator(source);
        } else {
            sourceCursor.setText(source);
        }

        // walk through each character
        while ((order = sourceCursor.next()) !=
               CollationElementIterator.NULLORDER)
        {
            secOrder = CollationElementIterator.secondaryOrder(order);
            terOrder = CollationElementIterator.tertiaryOrder(order);
            if (!CollationElementIterator.isIgnorable(order))
            {
                primResult.append((char) (CollationElementIterator.primaryOrder(order)
                                    + COLLATIONKEYOFFSET));

                if (compareSec) {
                    //
                    // accumulate all of the ignorable/secondary characters attached
                    // to a given base character
                    //
                    if (tables.isFrenchSec() && preSecIgnore < secResult.length()) {
                        //
                        // We're doing reversed secondary ordering and we've hit a base
                        // (non-ignorable) character.  Reverse any secondary orderings
                        // that applied to the last base character.  (see block comment above.)
                        //
                        RBCollationTables.reverse(secResult, preSecIgnore, secResult.length());
                    }
                    // Remember where we are in the secondary orderings - this is how far
                    // back to go if we need to reverse them later.
                    secResult.append((char)(secOrder+ COLLATIONKEYOFFSET));
                    preSecIgnore = secResult.length();
                }
                if (compareTer) {
                    terResult.append((char)(terOrder+ COLLATIONKEYOFFSET));
                }
            }
            else
            {
                if (compareSec && secOrder != 0)
                    secResult.append((char)
                        (secOrder + tables.getMaxSecOrder() + COLLATIONKEYOFFSET));
                if (compareTer && terOrder != 0)
                    terResult.append((char)
                        (terOrder + tables.getMaxTerOrder() + COLLATIONKEYOFFSET));
            }
        }
        if (tables.isFrenchSec())
        {
            if (preSecIgnore < secResult.length()) {
                // If we've accumulated any secondary characters after the last base character,
                // reverse them.
                RBCollationTables.reverse(secResult, preSecIgnore, secResult.length());
            }
            // And now reverse the entire secResult to get French secondary ordering.
            RBCollationTables.reverse(secResult, 0, secResult.length());
        }
        primResult.append((char)0);
        secResult.append((char)0);
        secResult.append(terResult.toString());
        primResult.append(secResult.toString());

        if (getStrength() == IDENTICAL) {
            primResult.append((char)0);
            int mode = getDecomposition();
            if (mode == CANONICAL_DECOMPOSITION) {
                primResult.append(Normalizer.normalize(source, Normalizer.Form.NFD));
            } else if (mode == FULL_DECOMPOSITION) {
                primResult.append(Normalizer.normalize(source, Normalizer.Form.NFKD));
            } else {
                primResult.append(source);
            }
        }
        return new RuleBasedCollationKey(source, primResult.toString());
    }

    /**
     * Standard override; no change in semantics.
     * <p>
     * 比较基于排序规则存储在两个不同字符串中的字符数据返回有关字符串是小于,大于还是等于语言中的另一个字符串的信息这可以在子类中覆盖
     * 
     */
    public Object clone() {
        // if we know we're not actually a subclass of RuleBasedCollator
        // (this class really should have been made final), bypass
        // Object.clone() and use our "copy constructor".  This is faster.
        if (getClass() == RuleBasedCollator.class) {
            return new RuleBasedCollator(this);
        }
        else {
            RuleBasedCollator result = (RuleBasedCollator) super.clone();
            result.primResult = null;
            result.secResult = null;
            result.terResult = null;
            result.sourceCursor = null;
            result.targetCursor = null;
            return result;
        }
    }

    /**
     * Compares the equality of two collation objects.
     * <p>
     *  将字符串转换为一系列字符,可以与Collat​​ionKeycompareTo进行比较这将覆盖javatextCollat​​orgetCollat​​ionKey它可以在子类中被覆盖
     * 
     * 
     * @param obj the table-based collation object to be compared with this.
     * @return true if the current table-based collation object is the same
     * as the table-based collation object obj; false otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!super.equals(obj)) return false;  // super does class check
        RuleBasedCollator other = (RuleBasedCollator) obj;
        // all other non-transient information is also contained in rules.
        return (getRules().equals(other.getRules()));
    }

    /**
     * Generates the hash code for the table-based collation object
     * <p>
     *  标准覆盖;没有语义的变化
     * 
     */
    public int hashCode() {
        return getRules().hashCode();
    }

    /**
     * Allows CollationElementIterator access to the tables object
     * <p>
     *  比较两个排序规则对象的相等性
     * 
     */
    RBCollationTables getTables() {
        return tables;
    }

    // ==============================================================
    // private
    // ==============================================================

    final static int CHARINDEX = 0x70000000;  // need look up in .commit()
    final static int EXPANDCHARINDEX = 0x7E000000; // Expand index follows
    final static int CONTRACTCHARINDEX = 0x7F000000;  // contract indexes follow
    final static int UNMAPPED = 0xFFFFFFFF;

    private final static int COLLATIONKEYOFFSET = 1;

    private RBCollationTables tables = null;

    // Internal objects that are cached across calls so that they don't have to
    // be created/destroyed on every call to compare() and getCollationKey()
    private StringBuffer primResult = null;
    private StringBuffer secResult = null;
    private StringBuffer terResult = null;
    private CollationElementIterator sourceCursor = null;
    private CollationElementIterator targetCursor = null;
}
