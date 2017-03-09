/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2000, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright IBM Corp. 1996, 1997 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996,1997  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.lang.Character;

/**
 * Utility class for normalizing and merging patterns for collation.
 * This is to be used with MergeCollation for adding patterns to an
 * existing rule table.
 * <p>
 *  用于归一化和合并排序规则的实用程序类。这将与MergeCollat​​ion一起使用以将模式添加到现有规则表。
 * 
 * 
 * @see        MergeCollation
 * @author     Mark Davis, Helena Shih
 */

class PatternEntry {
    /**
     * Gets the current extension, quoted
     * <p>
     *  获取当前扩展名,引用
     * 
     */
    public void appendQuotedExtension(StringBuffer toAddTo) {
        appendQuoted(extension,toAddTo);
    }

    /**
     * Gets the current chars, quoted
     * <p>
     *  获取当前字符,引用
     * 
     */
    public void appendQuotedChars(StringBuffer toAddTo) {
        appendQuoted(chars,toAddTo);
    }

    /**
     * WARNING this is used for searching in a Vector.
     * Because Vector.indexOf doesn't take a comparator,
     * this method is ill-defined and ignores strength.
     * <p>
     *  警告这用于在向量中搜索。因为Vector.indexOf不使用比较器,所以这个方法是不明确的,忽略了强度。
     * 
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        PatternEntry other = (PatternEntry) obj;
        boolean result = chars.equals(other.chars);
        return result;
    }

    public int hashCode() {
        return chars.hashCode();
    }

    /**
     * For debugging.
     * <p>
     *  用于调试。
     * 
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        addToBuffer(result, true, false, null);
        return result.toString();
    }

    /**
     * Gets the strength of the entry.
     * <p>
     *  获得条目的强度。
     * 
     */
    final int getStrength() {
        return strength;
    }

    /**
     * Gets the expanding characters of the entry.
     * <p>
     *  获取条目的展开字符。
     * 
     */
    final String getExtension() {
        return extension;
    }

    /**
     * Gets the core characters of the entry.
     * <p>
     *  获取条目的核心字符。
     */
    final String getChars() {
        return chars;
    }

    // ===== privates =====

    void addToBuffer(StringBuffer toAddTo,
                     boolean showExtension,
                     boolean showWhiteSpace,
                     PatternEntry lastEntry)
    {
        if (showWhiteSpace && toAddTo.length() > 0)
            if (strength == Collator.PRIMARY || lastEntry != null)
                toAddTo.append('\n');
            else
                toAddTo.append(' ');
        if (lastEntry != null) {
            toAddTo.append('&');
            if (showWhiteSpace)
                toAddTo.append(' ');
            lastEntry.appendQuotedChars(toAddTo);
            appendQuotedExtension(toAddTo);
            if (showWhiteSpace)
                toAddTo.append(' ');
        }
        switch (strength) {
        case Collator.IDENTICAL: toAddTo.append('='); break;
        case Collator.TERTIARY:  toAddTo.append(','); break;
        case Collator.SECONDARY: toAddTo.append(';'); break;
        case Collator.PRIMARY:   toAddTo.append('<'); break;
        case RESET: toAddTo.append('&'); break;
        case UNSET: toAddTo.append('?'); break;
        }
        if (showWhiteSpace)
            toAddTo.append(' ');
        appendQuoted(chars,toAddTo);
        if (showExtension && extension.length() != 0) {
            toAddTo.append('/');
            appendQuoted(extension,toAddTo);
        }
    }

    static void appendQuoted(String chars, StringBuffer toAddTo) {
        boolean inQuote = false;
        char ch = chars.charAt(0);
        if (Character.isSpaceChar(ch)) {
            inQuote = true;
            toAddTo.append('\'');
        } else {
          if (PatternEntry.isSpecialChar(ch)) {
                inQuote = true;
                toAddTo.append('\'');
            } else {
                switch (ch) {
                    case 0x0010: case '\f': case '\r':
                    case '\t': case '\n':  case '@':
                    inQuote = true;
                    toAddTo.append('\'');
                    break;
                case '\'':
                    inQuote = true;
                    toAddTo.append('\'');
                    break;
                default:
                    if (inQuote) {
                        inQuote = false; toAddTo.append('\'');
                    }
                    break;
                }
           }
        }
        toAddTo.append(chars);
        if (inQuote)
            toAddTo.append('\'');
    }

    //========================================================================
    // Parsing a pattern into a list of PatternEntries....
    //========================================================================

    PatternEntry(int strength,
                 StringBuffer chars,
                 StringBuffer extension)
    {
        this.strength = strength;
        this.chars = chars.toString();
        this.extension = (extension.length() > 0) ? extension.toString()
                                                  : "";
    }

    static class Parser {
        private String pattern;
        private int i;

        public Parser(String pattern) {
            this.pattern = pattern;
            this.i = 0;
        }

        public PatternEntry next() throws ParseException {
            int newStrength = UNSET;

            newChars.setLength(0);
            newExtension.setLength(0);

            boolean inChars = true;
            boolean inQuote = false;
        mainLoop:
            while (i < pattern.length()) {
                char ch = pattern.charAt(i);
                if (inQuote) {
                    if (ch == '\'') {
                        inQuote = false;
                    } else {
                        if (newChars.length() == 0) newChars.append(ch);
                        else if (inChars) newChars.append(ch);
                        else newExtension.append(ch);
                    }
                } else switch (ch) {
                case '=': if (newStrength != UNSET) break mainLoop;
                    newStrength = Collator.IDENTICAL; break;
                case ',': if (newStrength != UNSET) break mainLoop;
                    newStrength = Collator.TERTIARY; break;
                case ';': if (newStrength != UNSET) break mainLoop;
                    newStrength = Collator.SECONDARY; break;
                case '<': if (newStrength != UNSET) break mainLoop;
                    newStrength = Collator.PRIMARY; break;
                case '&': if (newStrength != UNSET) break mainLoop;
                    newStrength = RESET; break;
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                case ' ': break; // skip whitespace TODO use Character
                case '/': inChars = false; break;
                case '\'':
                    inQuote = true;
                    ch = pattern.charAt(++i);
                    if (newChars.length() == 0) newChars.append(ch);
                    else if (inChars) newChars.append(ch);
                    else newExtension.append(ch);
                    break;
                default:
                    if (newStrength == UNSET) {
                        throw new ParseException
                            ("missing char (=,;<&) : " +
                             pattern.substring(i,
                                (i+10 < pattern.length()) ?
                                 i+10 : pattern.length()),
                             i);
                    }
                    if (PatternEntry.isSpecialChar(ch) && (inQuote == false))
                        throw new ParseException
                            ("Unquoted punctuation character : " + Integer.toString(ch, 16), i);
                    if (inChars) {
                        newChars.append(ch);
                    } else {
                        newExtension.append(ch);
                    }
                    break;
                }
                i++;
            }
            if (newStrength == UNSET)
                return null;
            if (newChars.length() == 0) {
                throw new ParseException
                    ("missing chars (=,;<&): " +
                      pattern.substring(i,
                          (i+10 < pattern.length()) ?
                           i+10 : pattern.length()),
                     i);
            }

            return new PatternEntry(newStrength, newChars, newExtension);
        }

        // We re-use these objects in order to improve performance
        private StringBuffer newChars = new StringBuffer();
        private StringBuffer newExtension = new StringBuffer();

    }

    static boolean isSpecialChar(char ch) {
        return ((ch == '\u0020') ||
                ((ch <= '\u002F') && (ch >= '\u0022')) ||
                ((ch <= '\u003F') && (ch >= '\u003A')) ||
                ((ch <= '\u0060') && (ch >= '\u005B')) ||
                ((ch <= '\u007E') && (ch >= '\u007B')));
    }


    static final int RESET = -2;
    static final int UNSET = -1;

    int strength = UNSET;
    String chars = "";
    String extension = "";
}
