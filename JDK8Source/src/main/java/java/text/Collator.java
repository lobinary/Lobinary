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
 * (C) Copyright Taligent, Inc. 1996-1998 -  All Rights Reserved
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
 *  (C)版权Taligent,Inc. 1996-1998  - 保留所有权利(C)版权所有IBM Corp. 1996-1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.lang.ref.SoftReference;
import java.text.spi.CollatorProvider;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleServiceProviderPool;


/**
 * The <code>Collator</code> class performs locale-sensitive
 * <code>String</code> comparison. You use this class to build
 * searching and sorting routines for natural language text.
 *
 * <p>
 * <code>Collator</code> is an abstract base class. Subclasses
 * implement specific collation strategies. One subclass,
 * <code>RuleBasedCollator</code>, is currently provided with
 * the Java Platform and is applicable to a wide set of languages. Other
 * subclasses may be created to handle more specialized needs.
 *
 * <p>
 * Like other locale-sensitive classes, you can use the static
 * factory method, <code>getInstance</code>, to obtain the appropriate
 * <code>Collator</code> object for a given locale. You will only need
 * to look at the subclasses of <code>Collator</code> if you need
 * to understand the details of a particular collation strategy or
 * if you need to modify that strategy.
 *
 * <p>
 * The following example shows how to compare two strings using
 * the <code>Collator</code> for the default locale.
 * <blockquote>
 * <pre>{@code
 * // Compare two strings in the default locale
 * Collator myCollator = Collator.getInstance();
 * if( myCollator.compare("abc", "ABC") < 0 )
 *     System.out.println("abc is less than ABC");
 * else
 *     System.out.println("abc is greater than or equal to ABC");
 * }</pre>
 * </blockquote>
 *
 * <p>
 * You can set a <code>Collator</code>'s <em>strength</em> property
 * to determine the level of difference considered significant in
 * comparisons. Four strengths are provided: <code>PRIMARY</code>,
 * <code>SECONDARY</code>, <code>TERTIARY</code>, and <code>IDENTICAL</code>.
 * The exact assignment of strengths to language features is
 * locale dependant.  For example, in Czech, "e" and "f" are considered
 * primary differences, while "e" and "&#283;" are secondary differences,
 * "e" and "E" are tertiary differences and "e" and "e" are identical.
 * The following shows how both case and accents could be ignored for
 * US English.
 * <blockquote>
 * <pre>
 * //Get the Collator for US English and set its strength to PRIMARY
 * Collator usCollator = Collator.getInstance(Locale.US);
 * usCollator.setStrength(Collator.PRIMARY);
 * if( usCollator.compare("abc", "ABC") == 0 ) {
 *     System.out.println("Strings are equivalent");
 * }
 * </pre>
 * </blockquote>
 * <p>
 * For comparing <code>String</code>s exactly once, the <code>compare</code>
 * method provides the best performance. When sorting a list of
 * <code>String</code>s however, it is generally necessary to compare each
 * <code>String</code> multiple times. In this case, <code>CollationKey</code>s
 * provide better performance. The <code>CollationKey</code> class converts
 * a <code>String</code> to a series of bits that can be compared bitwise
 * against other <code>CollationKey</code>s. A <code>CollationKey</code> is
 * created by a <code>Collator</code> object for a given <code>String</code>.
 * <br>
 * <strong>Note:</strong> <code>CollationKey</code>s from different
 * <code>Collator</code>s can not be compared. See the class description
 * for {@link CollationKey}
 * for an example using <code>CollationKey</code>s.
 *
 * <p>
 *  <code> Collat​​or </code>类执行区域设置敏感的<code> String </code>比较。你使用这个类来构建自然语言文本的搜索和排序例程。
 * 
 * <p>
 *  <code> Collat​​or </code>是一个抽象基类。子类实现特定的整理策略。当前提供了一个子类,<code> RuleBasedCollat​​or </code>,它适用于多种语言。
 * 可以创建其他子类来处理更专门的需求。
 * 
 * <p>
 * 与其他对locale敏感的类一样,您可以使用静态工厂方法<code> getInstance </code>为给定的语言环境获取适当的<code> Collat​​or </code>对象。
 * 如果您需要了解特定排序规则策略的详细信息或者需要修改该策略,则只需查看<code> Collat​​or </code>的子类。
 * 
 * <p>
 *  以下示例说明如何使用默认语言环境的<code> Collat​​or </code>比较两个字符串。
 * <blockquote>
 *  <pre> {@ code //比较默认语言环境中的两个字符串Collat​​or myCollat​​or = Collat​​or.getInstance(); if(myCollat​​or.compare("abc","ABC")<0)System.out.println("abc is less than ABC"); else System.out.println("abc大于或等于ABC"); } </pre>
 * 。
 * </blockquote>
 * 
 * <p>
 *  您可以设置<code> Collat​​or </code>的<em>力量</em>属性,以确定在比较中被认为重要的差异水平。
 * 提供了四个优点：<code> PRIMARY </code>,<code> SECONDARY </code>,<code> TERTIARY </code>和<code> IDENTICAL </code>
 * 。
 *  您可以设置<code> Collat​​or </code>的<em>力量</em>属性,以确定在比较中被认为重要的差异水平。对语言特征的强度的确切分配取决于语言环境。
 * 例如,在捷克语中,"e"和"f"被认为是初级差异,而​​"e"和"ě"是次级差异,"e"和"E"是三级差异,"e" 。下面显示了如何忽略美国英语的大小写和重音。
 * <blockquote>
 * <pre>
 * //获取美国英语的Collat​​or并将其强度设置为PRIMARY Collat​​or usCollat​​or = Collat​​or.getInstance(Locale.US); usCol
 * lat​​or.setStrength(Collat​​or.PRIMARY); if(usCollat​​or.compare("abc","ABC")== 0){System.out.println("Strings are equivalent"); }
 * }。
 * </pre>
 * </blockquote>
 * <p>
 *  为了比较<code> String </code>,一次,<code> compare </code>方法提供了最好的性能。
 * 然而,当排序<code> String </code>的列表时,通常需要多次比较每个<code> String </code>。
 * 在这种情况下,<code> Collat​​ionKey </code>可提供更好的性能。
 *  <code> Collat​​ionKey </code>类将<code> String </code>转换为可以与其他<code> Collat​​ionKey </code>进行位比较的一系列位。
 * 在这种情况下,<code> Collat​​ionKey </code>可提供更好的性能。
 * 对于给定的<code> String </code>,通过<code> Collat​​or </code>对象创建<code> Collat​​ionKey </code>。
 * <br>
 *  <strong>注意：</strong> </code>不能比较来自不同<code> Collat​​or </code>的Collat​​ionKey </code>。
 * 有关使用<code> Collat​​ionKey </code>的示例,请参见{@link Collat​​ionKey}的类描述。
 * 
 * 
 * @see         RuleBasedCollator
 * @see         CollationKey
 * @see         CollationElementIterator
 * @see         Locale
 * @author      Helena Shih, Laura Werner, Richard Gillam
 */

public abstract class Collator
    implements java.util.Comparator<Object>, Cloneable
{
    /**
     * Collator strength value.  When set, only PRIMARY differences are
     * considered significant during comparison. The assignment of strengths
     * to language features is locale dependant. A common example is for
     * different base letters ("a" vs "b") to be considered a PRIMARY difference.
     * <p>
     *  对照强度值。设置时,在比较期间仅将PRIMARY差异视为有效。对语言特征的强度分配取决于语言环境。一个常见的例子是不同的基本字母("a"和"b")被认为是主要差异。
     * 
     * 
     * @see java.text.Collator#setStrength
     * @see java.text.Collator#getStrength
     */
    public final static int PRIMARY = 0;
    /**
     * Collator strength value.  When set, only SECONDARY and above differences are
     * considered significant during comparison. The assignment of strengths
     * to language features is locale dependant. A common example is for
     * different accented forms of the same base letter ("a" vs "\u00E4") to be
     * considered a SECONDARY difference.
     * <p>
     * 对照强度值。当设置时,在比较期间仅认为SECONDARY和以上的差异是显着的。对语言特征的强度分配取决于语言环境。
     * 一个常见的例子是同一基本字母("a"对"\ u00E4")的不同重音形式被认为是二次差异。
     * 
     * 
     * @see java.text.Collator#setStrength
     * @see java.text.Collator#getStrength
     */
    public final static int SECONDARY = 1;
    /**
     * Collator strength value.  When set, only TERTIARY and above differences are
     * considered significant during comparison. The assignment of strengths
     * to language features is locale dependant. A common example is for
     * case differences ("a" vs "A") to be considered a TERTIARY difference.
     * <p>
     *  对照强度值。当设置时,在比较期间仅认为TERTIARY和以上的差异是显着的。对语言特征的强度分配取决于语言环境。一个常见的例子是将情况差异("a"对"A")视为TERTIARY差异。
     * 
     * 
     * @see java.text.Collator#setStrength
     * @see java.text.Collator#getStrength
     */
    public final static int TERTIARY = 2;

    /**
     * Collator strength value.  When set, all differences are
     * considered significant during comparison. The assignment of strengths
     * to language features is locale dependant. A common example is for control
     * characters ("&#092;u0001" vs "&#092;u0002") to be considered equal at the
     * PRIMARY, SECONDARY, and TERTIARY levels but different at the IDENTICAL
     * level.  Additionally, differences between pre-composed accents such as
     * "&#092;u00C0" (A-grave) and combining accents such as "A&#092;u0300"
     * (A, combining-grave) will be considered significant at the IDENTICAL
     * level if decomposition is set to NO_DECOMPOSITION.
     * <p>
     *  对照强度值。当设置时,在比较期间所有差异被认为是显着的。对语言特征的强度分配取决于语言环境。
     * 一个常见的例子是在PRIMARY,SECONDARY和TERTIARY级别将控制字符("\ u0001"vs"\ u0002")视为相等,但在IDENTICAL级别不同。
     * 此外,如果分解设置为NO_DECOMPOSITION,则在诸如"\ u00C0"(A-grave)之类的预组合重音和诸如"A \ u0300"(A,combining-grave)的组合重音之间的差异将
     * 被认为在IDENTICAL级别是显着的, 。
     * 一个常见的例子是在PRIMARY,SECONDARY和TERTIARY级别将控制字符("\ u0001"vs"\ u0002")视为相等,但在IDENTICAL级别不同。
     * 
     */
    public final static int IDENTICAL = 3;

    /**
     * Decomposition mode value. With NO_DECOMPOSITION
     * set, accented characters will not be decomposed for collation. This
     * is the default setting and provides the fastest collation but
     * will only produce correct results for languages that do not use accents.
     * <p>
     *  分解模式值。设置NO_DECOMPOSITION时,重音字符不会分解为排序规则。这是默认设置,并提供最快的排序规则,但只对不使用重音的语言生成正确的结果。
     * 
     * 
     * @see java.text.Collator#getDecomposition
     * @see java.text.Collator#setDecomposition
     */
    public final static int NO_DECOMPOSITION = 0;

    /**
     * Decomposition mode value. With CANONICAL_DECOMPOSITION
     * set, characters that are canonical variants according to Unicode
     * standard will be decomposed for collation. This should be used to get
     * correct collation of accented characters.
     * <p>
     * CANONICAL_DECOMPOSITION corresponds to Normalization Form D as
     * described in
     * <a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">Unicode
     * Technical Report #15</a>.
     * <p>
     * 分解模式值。设置CANONICAL_DECOMPOSITION后,根据Unicode标准的规范变体的字符将被分解以进行排序。这应该用于获得重音字符的正确排序。
     * <p>
     *  CANONICAL_DECOMPOSITION对应于规范化表单D,如<a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">
     *  Unicode技术报告#15 </a>中所述。
     * 
     * 
     * @see java.text.Collator#getDecomposition
     * @see java.text.Collator#setDecomposition
     */
    public final static int CANONICAL_DECOMPOSITION = 1;

    /**
     * Decomposition mode value. With FULL_DECOMPOSITION
     * set, both Unicode canonical variants and Unicode compatibility variants
     * will be decomposed for collation.  This causes not only accented
     * characters to be collated, but also characters that have special formats
     * to be collated with their norminal form. For example, the half-width and
     * full-width ASCII and Katakana characters are then collated together.
     * FULL_DECOMPOSITION is the most complete and therefore the slowest
     * decomposition mode.
     * <p>
     * FULL_DECOMPOSITION corresponds to Normalization Form KD as
     * described in
     * <a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html">Unicode
     * Technical Report #15</a>.
     * <p>
     *  分解模式值。设置FULL_DECOMPOSITION后,Unicode规范变体和Unicode兼容性变体将被分解以进行排序。
     * 这不仅导致重写的字符被整理,而且还导致具有特殊格式的字符与其正常形式进行整理。例如,半角和全角ASCII和片假名字符随后被整理在一起。
     *  FULL_DECOMPOSITION是最完整的,因此是最慢的分解模式。
     * <p>
     *  FULL_DECOMPOSITION对应于规范化表单KD,如<a href="http://www.unicode.org/unicode/reports/tr15/tr15-23.html"> Un
     * icode技术报告#15 </a>中所述。
     * 
     * 
     * @see java.text.Collator#getDecomposition
     * @see java.text.Collator#setDecomposition
     */
    public final static int FULL_DECOMPOSITION = 2;

    /**
     * Gets the Collator for the current default locale.
     * The default locale is determined by java.util.Locale.getDefault.
     * <p>
     *  获取当前默认语言环境的Collat​​or。默认语言环境由java.util.Locale.getDefault确定。
     * 
     * 
     * @return the Collator for the default locale.(for example, en_US)
     * @see java.util.Locale#getDefault
     */
    public static synchronized Collator getInstance() {
        return getInstance(Locale.getDefault());
    }

    /**
     * Gets the Collator for the desired locale.
     * <p>
     *  获取所需语言环境的"整理者"。
     * 
     * 
     * @param desiredLocale the desired locale.
     * @return the Collator for the desired locale.
     * @see java.util.Locale
     * @see java.util.ResourceBundle
     */
    public static Collator getInstance(Locale desiredLocale) {
        SoftReference<Collator> ref = cache.get(desiredLocale);
        Collator result = (ref != null) ? ref.get() : null;
        if (result == null) {
            LocaleProviderAdapter adapter;
            adapter = LocaleProviderAdapter.getAdapter(CollatorProvider.class,
                                                       desiredLocale);
            CollatorProvider provider = adapter.getCollatorProvider();
            result = provider.getInstance(desiredLocale);
            if (result == null) {
                result = LocaleProviderAdapter.forJRE()
                             .getCollatorProvider().getInstance(desiredLocale);
            }
            while (true) {
                if (ref != null) {
                    // Remove the empty SoftReference if any
                    cache.remove(desiredLocale, ref);
                }
                ref = cache.putIfAbsent(desiredLocale, new SoftReference<>(result));
                if (ref == null) {
                    break;
                }
                Collator cachedColl = ref.get();
                if (cachedColl != null) {
                    result = cachedColl;
                    break;
                }
            }
        }
        return (Collator) result.clone(); // make the world safe
    }

    /**
     * Compares the source string to the target string according to the
     * collation rules for this Collator.  Returns an integer less than,
     * equal to or greater than zero depending on whether the source String is
     * less than, equal to or greater than the target string.  See the Collator
     * class description for an example of use.
     * <p>
     * For a one time comparison, this method has the best performance. If a
     * given String will be involved in multiple comparisons, CollationKey.compareTo
     * has the best performance. See the Collator class description for an example
     * using CollationKeys.
     * <p>
     * 根据此Collat​​or的排序规则将源字符串与目标字符串进行比较。根据源String是小于,等于还是大于目标字符串,返回小于,等于或大于零的整数。有关使用示例,请参阅Collat​​or类的描述。
     * <p>
     *  对于一次性比较,此方法具有最佳性能。如果给定的字符串将涉及多个比较,Collat​​ionKey.compareTo具有最佳性能。
     * 有关使用Collat​​ionKeys的示例,请参阅Collat​​or类的描述。
     * 
     * 
     * @param source the source string.
     * @param target the target string.
     * @return Returns an integer value. Value is less than zero if source is less than
     * target, value is zero if source and target are equal, value is greater than zero
     * if source is greater than target.
     * @see java.text.CollationKey
     * @see java.text.Collator#getCollationKey
     */
    public abstract int compare(String source, String target);

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     * <p>
     * This implementation merely returns
     *  <code> compare((String)o1, (String)o2) </code>.
     *
     * <p>
     *  比较其两个参数的顺序。返回负整数,零或正整数,因为第一个参数小于,等于或大于秒。
     * <p>
     *  这个实现只返回<code> compare((String)o1,(String)o2)</code>。
     * 
     * 
     * @return a negative integer, zero, or a positive integer as the
     *         first argument is less than, equal to, or greater than the
     *         second.
     * @exception ClassCastException the arguments cannot be cast to Strings.
     * @see java.util.Comparator
     * @since   1.2
     */
    @Override
    public int compare(Object o1, Object o2) {
    return compare((String)o1, (String)o2);
    }

    /**
     * Transforms the String into a series of bits that can be compared bitwise
     * to other CollationKeys. CollationKeys provide better performance than
     * Collator.compare when Strings are involved in multiple comparisons.
     * See the Collator class description for an example using CollationKeys.
     * <p>
     *  将字符串转换为一系列位,可以按位与其他Collat​​ionKeys比较。 Collat​​ionKeys提供比Collat​​or.compare更好的性能,当字符串涉及多个比较。
     * 有关使用Collat​​ionKeys的示例,请参阅Collat​​or类的描述。
     * 
     * 
     * @param source the string to be transformed into a collation key.
     * @return the CollationKey for the given String based on this Collator's collation
     * rules. If the source String is null, a null CollationKey is returned.
     * @see java.text.CollationKey
     * @see java.text.Collator#compare
     */
    public abstract CollationKey getCollationKey(String source);

    /**
     * Convenience method for comparing the equality of two strings based on
     * this Collator's collation rules.
     * <p>
     *  基于此Collat​​or的排序规则比较两个字符串的相等性的方便方法。
     * 
     * 
     * @param source the source string to be compared with.
     * @param target the target string to be compared with.
     * @return true if the strings are equal according to the collation
     * rules.  false, otherwise.
     * @see java.text.Collator#compare
     */
    public boolean equals(String source, String target)
    {
        return (compare(source, target) == Collator.EQUAL);
    }

    /**
     * Returns this Collator's strength property.  The strength property determines
     * the minimum level of difference considered significant during comparison.
     * See the Collator class description for an example of use.
     * <p>
     *  返回此Collat​​or的strength属性。强度属性确定在比较期间认为显着的最小差异水平。有关使用示例,请参阅Collat​​or类的描述。
     * 
     * 
     * @return this Collator's current strength property.
     * @see java.text.Collator#setStrength
     * @see java.text.Collator#PRIMARY
     * @see java.text.Collator#SECONDARY
     * @see java.text.Collator#TERTIARY
     * @see java.text.Collator#IDENTICAL
     */
    public synchronized int getStrength()
    {
        return strength;
    }

    /**
     * Sets this Collator's strength property.  The strength property determines
     * the minimum level of difference considered significant during comparison.
     * See the Collator class description for an example of use.
     * <p>
     * 设置此Collat​​or的strength属性。强度属性确定在比较期间认为显着的最小差异水平。有关使用示例,请参阅Collat​​or类的描述。
     * 
     * 
     * @param newStrength  the new strength value.
     * @see java.text.Collator#getStrength
     * @see java.text.Collator#PRIMARY
     * @see java.text.Collator#SECONDARY
     * @see java.text.Collator#TERTIARY
     * @see java.text.Collator#IDENTICAL
     * @exception  IllegalArgumentException If the new strength value is not one of
     * PRIMARY, SECONDARY, TERTIARY or IDENTICAL.
     */
    public synchronized void setStrength(int newStrength) {
        if ((newStrength != PRIMARY) &&
            (newStrength != SECONDARY) &&
            (newStrength != TERTIARY) &&
            (newStrength != IDENTICAL)) {
            throw new IllegalArgumentException("Incorrect comparison level.");
        }
        strength = newStrength;
    }

    /**
     * Get the decomposition mode of this Collator. Decomposition mode
     * determines how Unicode composed characters are handled. Adjusting
     * decomposition mode allows the user to select between faster and more
     * complete collation behavior.
     * <p>The three values for decomposition mode are:
     * <UL>
     * <LI>NO_DECOMPOSITION,
     * <LI>CANONICAL_DECOMPOSITION
     * <LI>FULL_DECOMPOSITION.
     * </UL>
     * See the documentation for these three constants for a description
     * of their meaning.
     * <p>
     *  获取此Collat​​or的分解模式。分解模式确定如何处理Unicode组合字符。调整分解模式允许用户在更快和更完整的整理行为之间进行选择。 <p>分解模式的三个值是：
     * <UL>
     *  <LI> NO_DECOMPOSITION,<LI> CANONICAL_DECOMPOSITION <LI> FULL_DECOMPOSITION。
     * </UL>
     *  有关这三个常量的含义,请参阅这些常量的文档。
     * 
     * 
     * @return the decomposition mode
     * @see java.text.Collator#setDecomposition
     * @see java.text.Collator#NO_DECOMPOSITION
     * @see java.text.Collator#CANONICAL_DECOMPOSITION
     * @see java.text.Collator#FULL_DECOMPOSITION
     */
    public synchronized int getDecomposition()
    {
        return decmp;
    }
    /**
     * Set the decomposition mode of this Collator. See getDecomposition
     * for a description of decomposition mode.
     * <p>
     *  设置此Collat​​or的分解模式。有关分解模式的描述,请参阅getDecomposition。
     * 
     * 
     * @param decompositionMode  the new decomposition mode.
     * @see java.text.Collator#getDecomposition
     * @see java.text.Collator#NO_DECOMPOSITION
     * @see java.text.Collator#CANONICAL_DECOMPOSITION
     * @see java.text.Collator#FULL_DECOMPOSITION
     * @exception IllegalArgumentException If the given value is not a valid decomposition
     * mode.
     */
    public synchronized void setDecomposition(int decompositionMode) {
        if ((decompositionMode != NO_DECOMPOSITION) &&
            (decompositionMode != CANONICAL_DECOMPOSITION) &&
            (decompositionMode != FULL_DECOMPOSITION)) {
            throw new IllegalArgumentException("Wrong decomposition mode.");
        }
        decmp = decompositionMode;
    }

    /**
     * Returns an array of all locales for which the
     * <code>getInstance</code> methods of this class can return
     * localized instances.
     * The returned array represents the union of locales supported
     * by the Java runtime and by installed
     * {@link java.text.spi.CollatorProvider CollatorProvider} implementations.
     * It must contain at least a Locale instance equal to
     * {@link java.util.Locale#US Locale.US}.
     *
     * <p>
     *  返回所有语言环境的数组,其中此类的<code> getInstance </code>方法可以返回本地化实例。
     * 返回的数组表示Java运行时和安装的{@link java.text.spi.Collat​​orProvider Collat​​orProvider}实现支持的区域的联合。
     * 它必须至少包含一个等于{@link java.util.Locale#US Locale.US}的Locale实例。
     * 
     * 
     * @return An array of locales for which localized
     *         <code>Collator</code> instances are available.
     */
    public static synchronized Locale[] getAvailableLocales() {
        LocaleServiceProviderPool pool =
            LocaleServiceProviderPool.getPool(CollatorProvider.class);
        return pool.getAvailableLocales();
    }

    /**
     * Overrides Cloneable
     * <p>
     *  覆盖可克隆
     * 
     */
    @Override
    public Object clone()
    {
        try {
            return (Collator)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Compares the equality of two Collators.
     * <p>
     *  比较两个Collat​​or的相等性。
     * 
     * 
     * @param that the Collator to be compared with this.
     * @return true if this Collator is the same as that Collator;
     * false otherwise.
     */
    @Override
    public boolean equals(Object that)
    {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Collator other = (Collator) that;
        return ((strength == other.strength) &&
                (decmp == other.decmp));
    }

    /**
     * Generates the hash code for this Collator.
     * <p>
     *  生成此Collat​​or的哈希码。
     * 
     */
    @Override
    abstract public int hashCode();

    /**
     * Default constructor.  This constructor is
     * protected so subclasses can get access to it. Users typically create
     * a Collator sub-class by calling the factory method getInstance.
     * <p>
     *  默认构造函数。这个构造函数是受保护的,所以子类可以访问它。用户通常通过调用工厂方法getInstance创建一个Collat​​or子类。
     * 
     * 
     * @see java.text.Collator#getInstance
     */
    protected Collator()
    {
        strength = TERTIARY;
        decmp = CANONICAL_DECOMPOSITION;
    }

    private int strength = 0;
    private int decmp = 0;
    private static final ConcurrentMap<Locale, SoftReference<Collator>> cache
            = new ConcurrentHashMap<>();

    //
    // FIXME: These three constants should be removed.
    //
    /**
     * LESS is returned if source string is compared to be less than target
     * string in the compare() method.
     * <p>
     * 如果源字符串在compare()方法中比较小于目标字符串,则返回LESS。
     * 
     * 
     * @see java.text.Collator#compare
     */
    final static int LESS = -1;
    /**
     * EQUAL is returned if source string is compared to be equal to target
     * string in the compare() method.
     * <p>
     *  如果在compare()方法中比较源字符串等于目标字符串,则返回EQUAL。
     * 
     * 
     * @see java.text.Collator#compare
     */
    final static int EQUAL = 0;
    /**
     * GREATER is returned if source string is compared to be greater than
     * target string in the compare() method.
     * <p>
     *  如果在compare()方法中比较源字符串大于目标字符串,则返回GREATER。
     * 
     * @see java.text.Collator#compare
     */
    final static int GREATER = 1;
 }
