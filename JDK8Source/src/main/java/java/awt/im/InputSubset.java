/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.im;


/**
 * Defines additional Unicode subsets for use by input methods.  Unlike the
 * UnicodeBlock subsets defined in the <code>{@link
 * java.lang.Character.UnicodeBlock}</code> class, these constants do not
 * directly correspond to Unicode code blocks.
 *
 * <p>
 *  定义供输入法使用的其他Unicode子集。
 * 与<code> {@ link java.lang.Character.UnicodeBlock} </code>类中定义的UnicodeBlock子集不同,这些常量不直接对应于Unicode代码块。
 * 
 * 
 * @since   1.2
 */

public final class InputSubset extends Character.Subset {

    private InputSubset(String name) {
        super(name);
    }

    /**
     * Constant for all Latin characters, including the characters
     * in the BASIC_LATIN, LATIN_1_SUPPLEMENT, LATIN_EXTENDED_A,
     * LATIN_EXTENDED_B Unicode character blocks.
     * <p>
     *  所有拉丁字符的常量,包括BASIC_LATIN,LATIN_1_SUPPLEMENT,LATIN_EXTENDED_A,LATIN_EXTENDED_B个Unicode字符块中的字符。
     * 
     */
    public static final InputSubset LATIN
        = new InputSubset("LATIN");

    /**
     * Constant for the digits included in the BASIC_LATIN Unicode character
     * block.
     * <p>
     *  包含在BASIC_LATIN Unicode字符块中的数字的常量。
     * 
     */
    public static final InputSubset LATIN_DIGITS
        = new InputSubset("LATIN_DIGITS");

    /**
     * Constant for all Han characters used in writing Traditional Chinese,
     * including a subset of the CJK unified ideographs as well as Traditional
     * Chinese Han characters that may be defined as surrogate characters.
     * <p>
     *  用于写作的所有汉字的常数繁体中文,包括CJK统一表意文字的子集以及可以被定义为替代字符的繁体中文汉字。
     * 
     */
    public static final InputSubset TRADITIONAL_HANZI
        = new InputSubset("TRADITIONAL_HANZI");

    /**
     * Constant for all Han characters used in writing Simplified Chinese,
     * including a subset of the CJK unified ideographs as well as Simplified
     * Chinese Han characters that may be defined as surrogate characters.
     * <p>
     *  用于书写简体中文的所有汉字的常数,包括CJK统一表意字符的子集以及可以定义为替代字符的简体中文汉字。
     * 
     */
    public static final InputSubset SIMPLIFIED_HANZI
        = new InputSubset("SIMPLIFIED_HANZI");

    /**
     * Constant for all Han characters used in writing Japanese, including a
     * subset of the CJK unified ideographs as well as Japanese Han characters
     * that may be defined as surrogate characters.
     * <p>
     *  用于书写日语的所有汉字的常数,包括CJK统一表意文字的子集以及可以被定义为替代字符的日语汉字。
     * 
     */
    public static final InputSubset KANJI
        = new InputSubset("KANJI");

    /**
     * Constant for all Han characters used in writing Korean, including a
     * subset of the CJK unified ideographs as well as Korean Han characters
     * that may be defined as surrogate characters.
     * <p>
     *  用于书写韩语的所有汉字的常数,包括CJK统一表意字符的子集以及可以被定义为替代字符的韩语汉字。
     * 
     */
    public static final InputSubset HANJA
        = new InputSubset("HANJA");

    /**
     * Constant for the halfwidth katakana subset of the Unicode halfwidth and
     * fullwidth forms character block.
     * <p>
     * Unicode半宽和全宽形式字符块的半宽片假名子集的常量。
     * 
     */
    public static final InputSubset HALFWIDTH_KATAKANA
        = new InputSubset("HALFWIDTH_KATAKANA");

    /**
     * Constant for the fullwidth ASCII variants subset of the Unicode halfwidth and
     * fullwidth forms character block.
     * <p>
     *  Unicode半宽和全宽表单字符块的全宽ASCII变体子集的常量。
     * 
     * 
     * @since 1.3
     */
    public static final InputSubset FULLWIDTH_LATIN
        = new InputSubset("FULLWIDTH_LATIN");

    /**
     * Constant for the fullwidth digits included in the Unicode halfwidth and
     * fullwidth forms character block.
     * <p>
     *  Unicode半角和全角字符块中包含的全宽数字的常量。
     * 
     * @since 1.3
     */
    public static final InputSubset FULLWIDTH_DIGITS
        = new InputSubset("FULLWIDTH_DIGITS");

}
