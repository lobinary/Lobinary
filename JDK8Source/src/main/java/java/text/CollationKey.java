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
 * (C) Copyright Taligent, Inc. 1996 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996  - 保留所有权利(C)版权所有IBM Corp. 1996  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

/**
 * A <code>CollationKey</code> represents a <code>String</code> under the
 * rules of a specific <code>Collator</code> object. Comparing two
 * <code>CollationKey</code>s returns the relative order of the
 * <code>String</code>s they represent. Using <code>CollationKey</code>s
 * to compare <code>String</code>s is generally faster than using
 * <code>Collator.compare</code>. Thus, when the <code>String</code>s
 * must be compared multiple times, for example when sorting a list
 * of <code>String</code>s. It's more efficient to use <code>CollationKey</code>s.
 *
 * <p>
 * You can not create <code>CollationKey</code>s directly. Rather,
 * generate them by calling <code>Collator.getCollationKey</code>.
 * You can only compare <code>CollationKey</code>s generated from
 * the same <code>Collator</code> object.
 *
 * <p>
 * Generating a <code>CollationKey</code> for a <code>String</code>
 * involves examining the entire <code>String</code>
 * and converting it to series of bits that can be compared bitwise. This
 * allows fast comparisons once the keys are generated. The cost of generating
 * keys is recouped in faster comparisons when <code>String</code>s need
 * to be compared many times. On the other hand, the result of a comparison
 * is often determined by the first couple of characters of each <code>String</code>.
 * <code>Collator.compare</code> examines only as many characters as it needs which
 * allows it to be faster when doing single comparisons.
 * <p>
 * The following example shows how <code>CollationKey</code>s might be used
 * to sort a list of <code>String</code>s.
 * <blockquote>
 * <pre>{@code
 * // Create an array of CollationKeys for the Strings to be sorted.
 * Collator myCollator = Collator.getInstance();
 * CollationKey[] keys = new CollationKey[3];
 * keys[0] = myCollator.getCollationKey("Tom");
 * keys[1] = myCollator.getCollationKey("Dick");
 * keys[2] = myCollator.getCollationKey("Harry");
 * sort(keys);
 *
 * //...
 *
 * // Inside body of sort routine, compare keys this way
 * if (keys[i].compareTo(keys[j]) > 0)
 *    // swap keys[i] and keys[j]
 *
 * //...
 *
 * // Finally, when we've returned from sort.
 * System.out.println(keys[0].getSourceString());
 * System.out.println(keys[1].getSourceString());
 * System.out.println(keys[2].getSourceString());
 * }</pre>
 * </blockquote>
 *
 * <p>
 *  <code> Collat​​ionKey </code>表示特定<code> Collat​​or </code>对象的规则下的<code> String </code>。
 * 比较两个<code> Collat​​ionKey </code> s将返回它们所代表的<code> String </code>的相对顺序。
 * 使用<code> Collat​​ionKey </code> s比较<code> String </code> s通常比使用<code> Collat​​or.compare </code>更快。
 * 因此,当必须多次比较<code> String </code>时,例如当对<code> String </code>的列表排序时。
 * 使用<code> Collat​​ionKey </code>更有效率。
 * 
 * <p>
 *  您不能直接创建<code> Collat​​ionKey </code>。而是通过调用<code> Collat​​or.getCollat​​ionKey </code>来生成它们。
 * 您只能比较从相同的<code> Collat​​or </code>对象生成的<code> Collat​​ionKey </code>。
 * 
 * <p>
 * 为<code> String </code>生成<code> Collat​​ionKey </code>涉及检查整个<code> String </code>并将其转换为可以按位比较的一系列位。
 * 这允许一旦生成密钥就快速比较。当需要对<code> String </code>进行多次比较时,生成密钥的成本会更快地进行比较。
 * 另一方面,比较的结果通常由每个<code> String </code>的第一对字符确定。
 *  <code> Collat​​or.compare </code>只检查所需的字符数,这样可以在进行单一比较时更快。
 * <p>
 *  以下示例显示如何使用<code> Collat​​ionKey </code>来排序<code> String </code>的列表。
 * <blockquote>
 *  <pre> {@ code //为要排序的字符串创建Collat​​ionKeys数组。
 *  Collat​​or myCollat​​or = Collat​​or.getInstance(); Collat​​ionKey [] keys = new Collat​​ionKey [3];
 *  keys [0] = myCollat​​or.getCollat​​ionKey("Tom"); keys [1] = myCollat​​or.getCollat​​ionKey("Dick");
 *  keys [2] = myCollat​​or.getCollat​​ionKey("Harry"); sort(keys);。
 *  <pre> {@ code //为要排序的字符串创建Collat​​ionKeys数组。
 * 
 *  // ...
 * 
 * @see          Collator
 * @see          RuleBasedCollator
 * @author       Helena Shih
 */

public abstract class CollationKey implements Comparable<CollationKey> {
    /**
     * Compare this CollationKey to the target CollationKey. The collation rules of the
     * Collator object which created these keys are applied. <strong>Note:</strong>
     * CollationKeys created by different Collators can not be compared.
     * <p>
     * 
     *  //如果(keys [i] .compareTo(keys [j])> 0)//交换键[i]和键[j]
     * 
     *  // ...
     * 
     *  //最后,当我们从sort返回时。
     *  System.out.println(keys [0] .getSourceString()); System.out.println(keys [1] .getSourceString()); Sy
     * stem.out.println(keys [2] .getSourceString()); } </pre>。
     *  //最后,当我们从sort返回时。
     * </blockquote>
     * 
     * 
     * @param target target CollationKey
     * @return Returns an integer value. Value is less than zero if this is less
     * than target, value is zero if this and target are equal and value is greater than
     * zero if this is greater than target.
     * @see java.text.Collator#compare
     */
    abstract public int compareTo(CollationKey target);

    /**
     * Returns the String that this CollationKey represents.
     *
     * <p>
     * 将此Collat​​ionKey与目标Collat​​ionKey进行比较。应用创建这些键的Collat​​or对象的排序规则。
     *  <strong>注意：</strong>不能比较不同Collat​​or创建的Collat​​ionKey。
     * 
     * 
     * @return the source string of this CollationKey
     */
    public String getSourceString() {
        return source;
    }


    /**
     * Converts the CollationKey to a sequence of bits. If two CollationKeys
     * could be legitimately compared, then one could compare the byte arrays
     * for each of those keys to obtain the same result.  Byte arrays are
     * organized most significant byte first.
     *
     * <p>
     *  返回此Collat​​ionKey表示的字符串。
     * 
     * 
     * @return a byte array representation of the CollationKey
     */
    abstract public byte[] toByteArray();


  /**
   * CollationKey constructor.
   *
   * <p>
   *  将Collat​​ionKey转换为位序列。如果两个Collat​​ionKeys可以合法比较,则可以比较这些键中的每一个的字节数组以获得相同的结果。字节数组首先组织为最高有效字节。
   * 
   * 
   * @param source the source string
   * @exception NullPointerException if {@code source} is null
   * @since 1.6
   */
    protected CollationKey(String source) {
        if (source==null){
            throw new NullPointerException();
        }
        this.source = source;
    }

    final private String source;
}
