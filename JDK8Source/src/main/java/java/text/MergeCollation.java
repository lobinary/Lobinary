/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.util.ArrayList;

/**
 * Utility class for normalizing and merging patterns for collation.
 * Patterns are strings of the form <entry>*, where <entry> has the
 * form:
 * <pattern> := <entry>*
 * <entry> := <separator><chars>{"/"<extension>}
 * <separator> := "=", ",", ";", "<", "&"
 * <chars>, and <extension> are both arbitrary strings.
 * unquoted whitespaces are ignored.
 * 'xxx' can be used to quote characters
 * One difference from Collator is that & is used to reset to a current
 * point. Or, in other words, it introduces a new sequence which is to
 * be added to the old.
 * That is: "a < b < c < d" is the same as "a < b & b < c & c < d" OR
 * "a < b < d & b < c"
 * XXX: make '' be a single quote.
 * <p>
 *  用于归一化和合并排序规则的实用程序类。
 * 模式是<entry> *形式的字符串,其中<entry>具有以下形式：<pattern>：= <entry> * <entry>：= <separator> <chars> {" ：="=",",",";","<","&"<chars>和<extension>都是任意字符串。
 *  用于归一化和合并排序规则的实用程序类。无参考空格被忽略。 'xxx'可用于引用字符与Collat​​or的一个区别是&用于复位到当前点。或者,换句话说,它引入了将被添加到旧的新序列。
 * 也就是说："a <b <c <d"与"a <b&b <​​c&c <d"OR"a <b <d&b <c"XXX：make" 。
 * 
 * 
 * @see PatternEntry
 * @author             Mark Davis, Helena Shih
 */

final class MergeCollation {

    /**
     * Creates from a pattern
     * <p>
     *  从模式创建
     * 
     * 
     * @exception ParseException If the input pattern is incorrect.
     */
    public MergeCollation(String pattern) throws ParseException
    {
        for (int i = 0; i < statusArray.length; i++)
            statusArray[i] = 0;
        setPattern(pattern);
    }

    /**
     * recovers current pattern
     * <p>
     *  恢复电流模式
     * 
     */
    public String getPattern() {
        return getPattern(true);
    }

    /**
     * recovers current pattern.
     * <p>
     *  恢复电流模式。
     * 
     * 
     * @param withWhiteSpace puts spacing around the entries, and \n
     * before & and <
     */
    public String getPattern(boolean withWhiteSpace) {
        StringBuffer result = new StringBuffer();
        PatternEntry tmp = null;
        ArrayList<PatternEntry> extList = null;
        int i;
        for (i = 0; i < patterns.size(); ++i) {
            PatternEntry entry = patterns.get(i);
            if (entry.extension.length() != 0) {
                if (extList == null)
                    extList = new ArrayList<>();
                extList.add(entry);
            } else {
                if (extList != null) {
                    PatternEntry last = findLastWithNoExtension(i-1);
                    for (int j = extList.size() - 1; j >= 0 ; j--) {
                        tmp = extList.get(j);
                        tmp.addToBuffer(result, false, withWhiteSpace, last);
                    }
                    extList = null;
                }
                entry.addToBuffer(result, false, withWhiteSpace, null);
            }
        }
        if (extList != null) {
            PatternEntry last = findLastWithNoExtension(i-1);
            for (int j = extList.size() - 1; j >= 0 ; j--) {
                tmp = extList.get(j);
                tmp.addToBuffer(result, false, withWhiteSpace, last);
            }
            extList = null;
        }
        return result.toString();
    }

    private final PatternEntry findLastWithNoExtension(int i) {
        for (--i;i >= 0; --i) {
            PatternEntry entry = patterns.get(i);
            if (entry.extension.length() == 0) {
                return entry;
            }
        }
        return null;
    }

    /**
     * emits the pattern for collation builder.
     * <p>
     *  发出排序规则生成器的模式。
     * 
     * 
     * @return emits the string in the format understable to the collation
     * builder.
     */
    public String emitPattern() {
        return emitPattern(true);
    }

    /**
     * emits the pattern for collation builder.
     * <p>
     *  发出排序规则生成器的模式。
     * 
     * 
     * @param withWhiteSpace puts spacing around the entries, and \n
     * before & and <
     * @return emits the string in the format understable to the collation
     * builder.
     */
    public String emitPattern(boolean withWhiteSpace) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < patterns.size(); ++i)
        {
            PatternEntry entry = patterns.get(i);
            if (entry != null) {
                entry.addToBuffer(result, true, withWhiteSpace, null);
            }
        }
        return result.toString();
    }

    /**
     * sets the pattern.
     * <p>
     * 设置模式。
     * 
     */
    public void setPattern(String pattern) throws ParseException
    {
        patterns.clear();
        addPattern(pattern);
    }

    /**
     * adds a pattern to the current one.
     * <p>
     *  添加一个模式到当前的。
     * 
     * 
     * @param pattern the new pattern to be added
     */
    public void addPattern(String pattern) throws ParseException
    {
        if (pattern == null)
            return;

        PatternEntry.Parser parser = new PatternEntry.Parser(pattern);

        PatternEntry entry = parser.next();
        while (entry != null) {
            fixEntry(entry);
            entry = parser.next();
        }
    }

    /**
     * gets count of separate entries
     * <p>
     *  获取单独条目的计数
     * 
     * 
     * @return the size of pattern entries
     */
    public int getCount() {
        return patterns.size();
    }

    /**
     * gets count of separate entries
     * <p>
     *  获取单独条目的计数
     * 
     * 
     * @param index the offset of the desired pattern entry
     * @return the requested pattern entry
     */
    public PatternEntry getItemAt(int index) {
        return patterns.get(index);
    }

    //============================================================
    // privates
    //============================================================
    ArrayList<PatternEntry> patterns = new ArrayList<>(); // a list of PatternEntries

    private transient PatternEntry saveEntry = null;
    private transient PatternEntry lastEntry = null;

    // This is really used as a local variable inside fixEntry, but we cache
    // it here to avoid newing it up every time the method is called.
    private transient StringBuffer excess = new StringBuffer();

    //
    // When building a MergeCollation, we need to do lots of searches to see
    // whether a given entry is already in the table.  Since we're using an
    // array, this would make the algorithm O(N*N).  To speed things up, we
    // use this bit array to remember whether the array contains any entries
    // starting with each Unicode character.  If not, we can avoid the search.
    // Using BitSet would make this easier, but it's significantly slower.
    //
    private transient byte[] statusArray = new byte[8192];
    private final byte BITARRAYMASK = (byte)0x1;
    private final int  BYTEPOWER = 3;
    private final int  BYTEMASK = (1 << BYTEPOWER) - 1;

    /*
      If the strength is RESET, then just change the lastEntry to
      be the current. (If the current is not in patterns, signal an error).
      If not, then remove the current entry, and add it after lastEntry
      (which is usually at the end).
    /* <p>
    /*  如果强度为RESET,则只需将lastEntry更改为当前值即可。 (如果电流不在模式,信号错误)。如果没有,那么删除当前条目,并在lastEntry(通常在结尾)之后添加它。
      */
    private final void fixEntry(PatternEntry newEntry) throws ParseException
    {
        // check to see whether the new entry has the same characters as the previous
        // entry did (this can happen when a pattern declaring a difference between two
        // strings that are canonically equivalent is normalized).  If so, and the strength
        // is anything other than IDENTICAL or RESET, throw an exception (you can't
        // declare a string to be unequal to itself).       --rtg 5/24/99
        if (lastEntry != null && newEntry.chars.equals(lastEntry.chars)
                && newEntry.extension.equals(lastEntry.extension)) {
            if (newEntry.strength != Collator.IDENTICAL
                && newEntry.strength != PatternEntry.RESET) {
                    throw new ParseException("The entries " + lastEntry + " and "
                            + newEntry + " are adjacent in the rules, but have conflicting "
                            + "strengths: A character can't be unequal to itself.", -1);
            } else {
                // otherwise, just skip this entry and behave as though you never saw it
                return;
            }
        }

        boolean changeLastEntry = true;
        if (newEntry.strength != PatternEntry.RESET) {
            int oldIndex = -1;

            if ((newEntry.chars.length() == 1)) {

                char c = newEntry.chars.charAt(0);
                int statusIndex = c >> BYTEPOWER;
                byte bitClump = statusArray[statusIndex];
                byte setBit = (byte)(BITARRAYMASK << (c & BYTEMASK));

                if (bitClump != 0 && (bitClump & setBit) != 0) {
                    oldIndex = patterns.lastIndexOf(newEntry);
                } else {
                    // We're going to add an element that starts with this
                    // character, so go ahead and set its bit.
                    statusArray[statusIndex] = (byte)(bitClump | setBit);
                }
            } else {
                oldIndex = patterns.lastIndexOf(newEntry);
            }
            if (oldIndex != -1) {
                patterns.remove(oldIndex);
            }

            excess.setLength(0);
            int lastIndex = findLastEntry(lastEntry, excess);

            if (excess.length() != 0) {
                newEntry.extension = excess + newEntry.extension;
                if (lastIndex != patterns.size()) {
                    lastEntry = saveEntry;
                    changeLastEntry = false;
                }
            }
            if (lastIndex == patterns.size()) {
                patterns.add(newEntry);
                saveEntry = newEntry;
            } else {
                patterns.add(lastIndex, newEntry);
            }
        }
        if (changeLastEntry) {
            lastEntry = newEntry;
        }
    }

    private final int findLastEntry(PatternEntry entry,
                              StringBuffer excessChars) throws ParseException
    {
        if (entry == null)
            return 0;

        if (entry.strength != PatternEntry.RESET) {
            // Search backwards for string that contains this one;
            // most likely entry is last one

            int oldIndex = -1;
            if ((entry.chars.length() == 1)) {
                int index = entry.chars.charAt(0) >> BYTEPOWER;
                if ((statusArray[index] &
                    (BITARRAYMASK << (entry.chars.charAt(0) & BYTEMASK))) != 0) {
                    oldIndex = patterns.lastIndexOf(entry);
                }
            } else {
                oldIndex = patterns.lastIndexOf(entry);
            }
            if ((oldIndex == -1))
                throw new ParseException("couldn't find last entry: "
                                          + entry, oldIndex);
            return oldIndex + 1;
        } else {
            int i;
            for (i = patterns.size() - 1; i >= 0; --i) {
                PatternEntry e = patterns.get(i);
                if (e.chars.regionMatches(0,entry.chars,0,
                                              e.chars.length())) {
                    excessChars.append(entry.chars.substring(e.chars.length(),
                                                            entry.chars.length()));
                    break;
                }
            }
            if (i == -1)
                throw new ParseException("couldn't find: " + entry, i);
            return i + 1;
        }
    }
}
