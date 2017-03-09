/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
 *******************************************************************************
 * (C) Copyright IBM Corp. 1996-2005 - All Rights Reserved                     *
 *                                                                             *
 * The original version of this source code and documentation is copyrighted   *
 * and owned by IBM, These materials are provided under terms of a License     *
 * Agreement between IBM and Sun. This technology is protected by multiple     *
 * US and International patents. This notice and attribution to IBM may not    *
 * to removed.                                                                 *
 *******************************************************************************
 * <p>
 *  **************************************************** ***************************(C)版权所有IBM Corp. 199
 * 6-2005  - 保留所有权利* *此源代码的原始版本并且文档具有版权*,并且由IBM拥有。
 * 这些材料是根据IBM和Sun之间的许可*协议的条款提供的。该技术受多项*美国和国际专利保护。本通知和对IBM的归属不得*删除。
 *  * ************************************************* ****************************。
 * 
 */

package java.text;

import sun.text.normalizer.NormalizerBase;
import sun.text.normalizer.NormalizerImpl;

/**
 * This class provides the method <code>normalize</code> which transforms Unicode
 * text into an equivalent composed or decomposed form, allowing for easier
 * sorting and searching of text.
 * The <code>normalize</code> method supports the standard normalization forms
 * described in
 * <a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">
 * Unicode Standard Annex #15 &mdash; Unicode Normalization Forms</a>.
 * <p>
 * Characters with accents or other adornments can be encoded in
 * several different ways in Unicode.  For example, take the character A-acute.
 * In Unicode, this can be encoded as a single character (the "composed" form):
 *
 * <pre>
 *      U+00C1    LATIN CAPITAL LETTER A WITH ACUTE</pre>
 *
 * or as two separate characters (the "decomposed" form):
 *
 * <pre>
 *      U+0041    LATIN CAPITAL LETTER A
 *      U+0301    COMBINING ACUTE ACCENT</pre>
 *
 * To a user of your program, however, both of these sequences should be
 * treated as the same "user-level" character "A with acute accent".  When you
 * are searching or comparing text, you must ensure that these two sequences are
 * treated as equivalent.  In addition, you must handle characters with more than
 * one accent. Sometimes the order of a character's combining accents is
 * significant, while in other cases accent sequences in different orders are
 * really equivalent.
 * <p>
 * Similarly, the string "ffi" can be encoded as three separate letters:
 *
 * <pre>
 *      U+0066    LATIN SMALL LETTER F
 *      U+0066    LATIN SMALL LETTER F
 *      U+0069    LATIN SMALL LETTER I</pre>
 *
 * or as the single character
 *
 * <pre>
 *      U+FB03    LATIN SMALL LIGATURE FFI</pre>
 *
 * The ffi ligature is not a distinct semantic character, and strictly speaking
 * it shouldn't be in Unicode at all, but it was included for compatibility
 * with existing character sets that already provided it.  The Unicode standard
 * identifies such characters by giving them "compatibility" decompositions
 * into the corresponding semantic characters.  When sorting and searching, you
 * will often want to use these mappings.
 * <p>
 * The <code>normalize</code> method helps solve these problems by transforming
 * text into the canonical composed and decomposed forms as shown in the first
 * example above. In addition, you can have it perform compatibility
 * decompositions so that you can treat compatibility characters the same as
 * their equivalents.
 * Finally, the <code>normalize</code> method rearranges accents into the
 * proper canonical order, so that you do not have to worry about accent
 * rearrangement on your own.
 * <p>
 * The W3C generally recommends to exchange texts in NFC.
 * Note also that most legacy character encodings use only precomposed forms and
 * often do not encode any combining marks by themselves. For conversion to such
 * character encodings the Unicode text needs to be normalized to NFC.
 * For more usage examples, see the Unicode Standard Annex.
 *
 * <p>
 *  这个类提供了方法<code> normalize </code>,它将Unicode文本转换为等效的组合或分解形式,从而更容易对文本进行排序和搜索。
 *  <code> normalize </code>方法支持在中描述的标准归一化形式。
 * <a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">
 *  Unicode标准附件#15&mdash; Unicode正规化表单</a>。
 * <p>
 *  带有重音符号或其他装饰的字符可以在Unicode中以几种不同的方式编码。例如,取字符A-acute。在Unicode中,这可以编码为单个字符("组合"形式)：
 * 
 * <pre>
 *  U + 00C1拉丁资本信用A与ACUTE </pre>
 * 
 *  或作为两个单独的字符("已分解"形式)：
 * 
 * <pre>
 * U + 0041 LATIN CAPITAL LETTER A U + 0301 COMBINING ACUTE ACCENT </pre>
 * 
 *  但是,对于程序的用户,这两个序列应该被视为相同的"用户级"字符"A带尖锐重音符"。当您搜索或比较文本时,必须确保将这两个序列视为等效。此外,您必须处理具有多个重音的字符。
 * 有时,字符的组合重音的顺序是重要的,而在其他情况下,不同顺序的重音序列实际上是等价的。
 * <p>
 *  类似地,字符串"ffi"可以被编码为三个单独的字母：
 * 
 * <pre>
 *  U + 0066拉丁小写字母F U + 0066拉丁小写字母F U + 0069拉丁小写字母I </pre>
 * 
 *  或作为单个字符
 * 
 * <pre>
 *  U + FB03 LATIN SMALL LIGATURE FFI </pre>
 * 
 *  ffi连字不是一个截然不同的语义字符,严格来说它不应该是在Unicode中,但它是为了兼容现有的字符集已经提供它。 Unicode标准通过将它们的"兼容性"分解为对应的语义字符来识别这样的字符。
 * 当排序和搜索时,您经常要使用这些映射。
 * 
 * @since 1.6
 */
public final class Normalizer {

   private Normalizer() {};

    /**
     * This enum provides constants of the four Unicode normalization forms
     * that are described in
     * <a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">
     * Unicode Standard Annex #15 &mdash; Unicode Normalization Forms</a>
     * and two methods to access them.
     *
     * <p>
     * <p>
     * <code> normalize </code>方法通过将文本转换为规范的组合和分解形式来帮助解决这些问题,如上面的第一个例子所示。此外,您可以让它执行兼容性分解,以便您可以处理与其等效的兼容性字符。
     * 最后,<code> normalize </code>方法将重音符号重新排列成正确的规范顺序,以便您不必担心自己的重音重排。
     * <p>
     *  W3C通常建议在NFC中交换文本。还要注意,大多数遗留字符编码仅使用预组合形式,并且通常不会自己编码任何组合标记。对于转换到这样的字符编码,Unicode文本需要归一化为NFC。
     * 有关更多使用示例,请参阅Unicode标准附录。
     * 
     * 
     * @since 1.6
     */
    public static enum Form {

        /**
         * Canonical decomposition.
         * <p>
         *  此枚举提供了四个Unicode标准化形式的常量
         * <a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">
         *  Unicode标准附件#15&mdash; Unicode规范化表单</a>和两种方法来访问它们。
         * 
         */
        NFD,

        /**
         * Canonical decomposition, followed by canonical composition.
         * <p>
         *  规范分解。
         * 
         */
        NFC,

        /**
         * Compatibility decomposition.
         * <p>
         *  规范分解,其次是规范组成。
         * 
         */
        NFKD,

        /**
         * Compatibility decomposition, followed by canonical composition.
         * <p>
         *  兼容性分解。
         * 
         */
        NFKC
    }

    /**
     * Normalize a sequence of char values.
     * The sequence will be normalized according to the specified normalization
     * from.
     * <p>
     *  兼容性分解,其次是规范组成。
     * 
     * 
     * @param src        The sequence of char values to normalize.
     * @param form       The normalization form; one of
     *                   {@link java.text.Normalizer.Form#NFC},
     *                   {@link java.text.Normalizer.Form#NFD},
     *                   {@link java.text.Normalizer.Form#NFKC},
     *                   {@link java.text.Normalizer.Form#NFKD}
     * @return The normalized String
     * @throws NullPointerException If <code>src</code> or <code>form</code>
     * is null.
     */
    public static String normalize(CharSequence src, Form form) {
        return NormalizerBase.normalize(src.toString(), form);
    }

    /**
     * Determines if the given sequence of char values is normalized.
     * <p>
     *  规范化char值的序列。序列将根据指定的归一化归一化。
     * 
     * 
     * @param src        The sequence of char values to be checked.
     * @param form       The normalization form; one of
     *                   {@link java.text.Normalizer.Form#NFC},
     *                   {@link java.text.Normalizer.Form#NFD},
     *                   {@link java.text.Normalizer.Form#NFKC},
     *                   {@link java.text.Normalizer.Form#NFKD}
     * @return true if the sequence of char values is normalized;
     * false otherwise.
     * @throws NullPointerException If <code>src</code> or <code>form</code>
     * is null.
     */
    public static boolean isNormalized(CharSequence src, Form form) {
        return NormalizerBase.isNormalized(src.toString(), form);
    }
}
