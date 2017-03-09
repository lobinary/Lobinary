/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2012, Oracle and/or its affiliates. All rights reserved.
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
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996-1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.util.Vector;
import sun.text.UCompactIntArray;
import sun.text.IntHashtable;

/**
 * This class contains the static state of a RuleBasedCollator: The various
 * tables that are used by the collation routines.  Several RuleBasedCollators
 * can share a single RBCollationTables object, easing memory requirements and
 * improving performance.
 * <p>
 *  此类包含RuleBasedCollat​​or的静态状态：归类例程使用的各种表。
 * 几个RuleBasedCollat​​ors可以共享单个RBCollat​​ionTables对象,从而减少内存需求并提高性能。
 * 
 */
final class RBCollationTables {
    //===========================================================================================
    //  The following diagram shows the data structure of the RBCollationTables object.
    //  Suppose we have the rule, where 'o-umlaut' is the unicode char 0x00F6.
    //  "a, A < b, B < c, C, ch, cH, Ch, CH < d, D ... < o, O; 'o-umlaut'/E, 'O-umlaut'/E ...".
    //  What the rule says is, sorts 'ch'ligatures and 'c' only with tertiary difference and
    //  sorts 'o-umlaut' as if it's always expanded with 'e'.
    //
    // mapping table                     contracting list           expanding list
    // (contains all unicode char
    //  entries)                   ___    ____________       _________________________
    //  ________                +>|_*_|->|'c' |v('c') |  +>|v('o')|v('umlaut')|v('e')|
    // |_\u0001_|-> v('\u0001') | |_:_|  |------------|  | |-------------------------|
    // |_\u0002_|-> v('\u0002') | |_:_|  |'ch'|v('ch')|  | |             :           |
    // |____:___|               | |_:_|  |------------|  | |-------------------------|
    // |____:___|               |        |'cH'|v('cH')|  | |             :           |
    // |__'a'___|-> v('a')      |        |------------|  | |-------------------------|
    // |__'b'___|-> v('b')      |        |'Ch'|v('Ch')|  | |             :           |
    // |____:___|               |        |------------|  | |-------------------------|
    // |____:___|               |        |'CH'|v('CH')|  | |             :           |
    // |___'c'__|----------------         ------------   | |-------------------------|
    // |____:___|                                        | |             :           |
    // |o-umlaut|----------------------------------------  |_________________________|
    // |____:___|
    //
    // Noted by Helena Shih on 6/23/97
    //============================================================================================

    public RBCollationTables(String rules, int decmp) throws ParseException {
        this.rules = rules;

        RBTableBuilder builder = new RBTableBuilder(new BuildAPI());
        builder.build(rules, decmp); // this object is filled in through
                                            // the BuildAPI object
    }

    final class BuildAPI {
        /**
         * Private constructor.  Prevents anyone else besides RBTableBuilder
         * from gaining direct access to the internals of this class.
         * <p>
         *  私有构造函数。阻止除RBTableBuilder之外的任何人都能直接访问这个类的内部。
         * 
         */
        private BuildAPI() {
        }

        /**
         * This function is used by RBTableBuilder to fill in all the members of this
         * object.  (Effectively, the builder class functions as a "friend" of this
         * class, but to avoid changing too much of the logic, it carries around "shadow"
         * copies of all these variables until the end of the build process and then
         * copies them en masse into the actual tables object once all the construction
         * logic is complete.  This function does that "copying en masse".
         * <p>
         *  RBTableBuilder使用此函数来填充此对象的所有成员。
         *  (有效地,构建器类作为这个类的"朋友",但为了避免改变太多的逻辑,它携带所有这些变量的"阴影"副本,直到构建过程结束,然后复制它们一旦所有的构造逻辑完成,这个功能就是"复制"。
         * 
         * 
         * @param f2ary The value for frenchSec (the French-secondary flag)
         * @param swap The value for SE Asian swapping rule
         * @param map The collator's character-mapping table (the value for mapping)
         * @param cTbl The collator's contracting-character table (the value for contractTable)
         * @param eTbl The collator's expanding-character table (the value for expandTable)
         * @param cFlgs The hash table of characters that participate in contracting-
         *              character sequences (the value for contractFlags)
         * @param mso The value for maxSecOrder
         * @param mto The value for maxTerOrder
         */
        void fillInTables(boolean f2ary,
                          boolean swap,
                          UCompactIntArray map,
                          Vector<Vector<EntryPair>> cTbl,
                          Vector<int[]> eTbl,
                          IntHashtable cFlgs,
                          short mso,
                          short mto) {
            frenchSec = f2ary;
            seAsianSwapping = swap;
            mapping = map;
            contractTable = cTbl;
            expandTable = eTbl;
            contractFlags = cFlgs;
            maxSecOrder = mso;
            maxTerOrder = mto;
        }
    }

    /**
     * Gets the table-based rules for the collation object.
     * <p>
     * 获取排序规则对象的基于表的规则。
     * 
     * 
     * @return returns the collation rules that the table collation object
     * was created from.
     */
    public String getRules()
    {
        return rules;
    }

    public boolean isFrenchSec() {
        return frenchSec;
    }

    public boolean isSEAsianSwapping() {
        return seAsianSwapping;
    }

    // ==============================================================
    // internal (for use by CollationElementIterator)
    // ==============================================================

    /**
     *  Get the entry of hash table of the contracting string in the collation
     *  table.
     * <p>
     *  在排序规则表中获取合同字符串的哈希表的条目。
     * 
     * 
     *  @param ch the starting character of the contracting string
     */
    Vector<EntryPair> getContractValues(int ch)
    {
        int index = mapping.elementAt(ch);
        return getContractValuesImpl(index - CONTRACTCHARINDEX);
    }

    //get contract values from contractTable by index
    private Vector<EntryPair> getContractValuesImpl(int index)
    {
        if (index >= 0)
        {
            return contractTable.elementAt(index);
        }
        else // not found
        {
            return null;
        }
    }

    /**
     * Returns true if this character appears anywhere in a contracting
     * character sequence.  (Used by CollationElementIterator.setOffset().)
     * <p>
     *  如果此字符出现在合同字符序列中的任何位置,则返回true。 (由Collat​​ionElementIterator.setOffset()使用。)
     * 
     */
    boolean usedInContractSeq(int c) {
        return contractFlags.get(c) == 1;
    }

    /**
      * Return the maximum length of any expansion sequences that end
      * with the specified comparison order.
      *
      * <p>
      *  返回以指定比较顺序结束的任何扩展序列的最大长度。
      * 
      * 
      * @param order a collation order returned by previous or next.
      * @return the maximum length of any expansion seuences ending
      *         with the specified order.
      *
      * @see CollationElementIterator#getMaxExpansion
      */
    int getMaxExpansion(int order) {
        int result = 1;

        if (expandTable != null) {
            // Right now this does a linear search through the entire
            // expansion table.  If a collator had a large number of expansions,
            // this could cause a performance problem, but in practise that
            // rarely happens
            for (int i = 0; i < expandTable.size(); i++) {
                int[] valueList = expandTable.elementAt(i);
                int length = valueList.length;

                if (length > result && valueList[length-1] == order) {
                    result = length;
                }
            }
        }

        return result;
    }

    /**
     * Get the entry of hash table of the expanding string in the collation
     * table.
     * <p>
     *  获取排序规则表中扩展字符串的哈希表的条目。
     * 
     * 
     * @param idx the index of the expanding string value list
     */
    final int[] getExpandValueList(int idx) {
        return expandTable.elementAt(idx - EXPANDCHARINDEX);
    }

    /**
     * Get the comarison order of a character from the collation table.
     * <p>
     *  从排序表中获取字符的比较顺序。
     * 
     * 
     * @return the comparison order of a character.
     */
    int getUnicodeOrder(int ch) {
        return mapping.elementAt(ch);
    }

    short getMaxSecOrder() {
        return maxSecOrder;
    }

    short getMaxTerOrder() {
        return maxTerOrder;
    }

    /**
     * Reverse a string.
     * <p>
     *  颠倒字符串。
     */
    //shemran/Note: this is used for secondary order value reverse, no
    //              need to consider supplementary pair.
    static void reverse (StringBuffer result, int from, int to)
    {
        int i = from;
        char swap;

        int j = to - 1;
        while (i < j) {
            swap =  result.charAt(i);
            result.setCharAt(i, result.charAt(j));
            result.setCharAt(j, swap);
            i++;
            j--;
        }
    }

    final static int getEntry(Vector<EntryPair> list, String name, boolean fwd) {
        for (int i = 0; i < list.size(); i++) {
            EntryPair pair = list.elementAt(i);
            if (pair.fwd == fwd && pair.entryName.equals(name)) {
                return i;
            }
        }
        return UNMAPPED;
    }

    // ==============================================================
    // constants
    // ==============================================================
    //sherman/Todo: is the value big enough?????
    final static int EXPANDCHARINDEX = 0x7E000000; // Expand index follows
    final static int CONTRACTCHARINDEX = 0x7F000000;  // contract indexes follow
    final static int UNMAPPED = 0xFFFFFFFF;

    final static int PRIMARYORDERMASK = 0xffff0000;
    final static int SECONDARYORDERMASK = 0x0000ff00;
    final static int TERTIARYORDERMASK = 0x000000ff;
    final static int PRIMARYDIFFERENCEONLY = 0xffff0000;
    final static int SECONDARYDIFFERENCEONLY = 0xffffff00;
    final static int PRIMARYORDERSHIFT = 16;
    final static int SECONDARYORDERSHIFT = 8;

    // ==============================================================
    // instance variables
    // ==============================================================
    private String rules = null;
    private boolean frenchSec = false;
    private boolean seAsianSwapping = false;

    private UCompactIntArray mapping = null;
    private Vector<Vector<EntryPair>> contractTable = null;
    private Vector<int[]> expandTable = null;
    private IntHashtable contractFlags = null;

    private short maxSecOrder = 0;
    private short maxTerOrder = 0;
}
