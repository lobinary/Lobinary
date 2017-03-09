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
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.lang.ref.SoftReference;
import java.text.spi.BreakIteratorProvider;
import java.util.Locale;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleServiceProviderPool;


/**
 * The <code>BreakIterator</code> class implements methods for finding
 * the location of boundaries in text. Instances of <code>BreakIterator</code>
 * maintain a current position and scan over text
 * returning the index of characters where boundaries occur.
 * Internally, <code>BreakIterator</code> scans text using a
 * <code>CharacterIterator</code>, and is thus able to scan text held
 * by any object implementing that protocol. A <code>StringCharacterIterator</code>
 * is used to scan <code>String</code> objects passed to <code>setText</code>.
 *
 * <p>
 * You use the factory methods provided by this class to create
 * instances of various types of break iterators. In particular,
 * use <code>getWordInstance</code>, <code>getLineInstance</code>,
 * <code>getSentenceInstance</code>, and <code>getCharacterInstance</code>
 * to create <code>BreakIterator</code>s that perform
 * word, line, sentence, and character boundary analysis respectively.
 * A single <code>BreakIterator</code> can work only on one unit
 * (word, line, sentence, and so on). You must use a different iterator
 * for each unit boundary analysis you wish to perform.
 *
 * <p><a name="line"></a>
 * Line boundary analysis determines where a text string can be
 * broken when line-wrapping. The mechanism correctly handles
 * punctuation and hyphenated words. Actual line breaking needs
 * to also consider the available line width and is handled by
 * higher-level software.
 *
 * <p><a name="sentence"></a>
 * Sentence boundary analysis allows selection with correct interpretation
 * of periods within numbers and abbreviations, and trailing punctuation
 * marks such as quotation marks and parentheses.
 *
 * <p><a name="word"></a>
 * Word boundary analysis is used by search and replace functions, as
 * well as within text editing applications that allow the user to
 * select words with a double click. Word selection provides correct
 * interpretation of punctuation marks within and following
 * words. Characters that are not part of a word, such as symbols
 * or punctuation marks, have word-breaks on both sides.
 *
 * <p><a name="character"></a>
 * Character boundary analysis allows users to interact with characters
 * as they expect to, for example, when moving the cursor through a text
 * string. Character boundary analysis provides correct navigation
 * through character strings, regardless of how the character is stored.
 * The boundaries returned may be those of supplementary characters,
 * combining character sequences, or ligature clusters.
 * For example, an accented character might be stored as a base character
 * and a diacritical mark. What users consider to be a character can
 * differ between languages.
 *
 * <p>
 * The <code>BreakIterator</code> instances returned by the factory methods
 * of this class are intended for use with natural languages only, not for
 * programming language text. It is however possible to define subclasses
 * that tokenize a programming language.
 *
 * <P>
 * <strong>Examples</strong>:<P>
 * Creating and using text boundaries:
 * <blockquote>
 * <pre>
 * public static void main(String args[]) {
 *      if (args.length == 1) {
 *          String stringToExamine = args[0];
 *          //print each word in order
 *          BreakIterator boundary = BreakIterator.getWordInstance();
 *          boundary.setText(stringToExamine);
 *          printEachForward(boundary, stringToExamine);
 *          //print each sentence in reverse order
 *          boundary = BreakIterator.getSentenceInstance(Locale.US);
 *          boundary.setText(stringToExamine);
 *          printEachBackward(boundary, stringToExamine);
 *          printFirst(boundary, stringToExamine);
 *          printLast(boundary, stringToExamine);
 *      }
 * }
 * </pre>
 * </blockquote>
 *
 * Print each element in order:
 * <blockquote>
 * <pre>
 * public static void printEachForward(BreakIterator boundary, String source) {
 *     int start = boundary.first();
 *     for (int end = boundary.next();
 *          end != BreakIterator.DONE;
 *          start = end, end = boundary.next()) {
 *          System.out.println(source.substring(start,end));
 *     }
 * }
 * </pre>
 * </blockquote>
 *
 * Print each element in reverse order:
 * <blockquote>
 * <pre>
 * public static void printEachBackward(BreakIterator boundary, String source) {
 *     int end = boundary.last();
 *     for (int start = boundary.previous();
 *          start != BreakIterator.DONE;
 *          end = start, start = boundary.previous()) {
 *         System.out.println(source.substring(start,end));
 *     }
 * }
 * </pre>
 * </blockquote>
 *
 * Print first element:
 * <blockquote>
 * <pre>
 * public static void printFirst(BreakIterator boundary, String source) {
 *     int start = boundary.first();
 *     int end = boundary.next();
 *     System.out.println(source.substring(start,end));
 * }
 * </pre>
 * </blockquote>
 *
 * Print last element:
 * <blockquote>
 * <pre>
 * public static void printLast(BreakIterator boundary, String source) {
 *     int end = boundary.last();
 *     int start = boundary.previous();
 *     System.out.println(source.substring(start,end));
 * }
 * </pre>
 * </blockquote>
 *
 * Print the element at a specified position:
 * <blockquote>
 * <pre>
 * public static void printAt(BreakIterator boundary, int pos, String source) {
 *     int end = boundary.following(pos);
 *     int start = boundary.previous();
 *     System.out.println(source.substring(start,end));
 * }
 * </pre>
 * </blockquote>
 *
 * Find the next word:
 * <blockquote>
 * <pre>{@code
 * public static int nextWordStartAfter(int pos, String text) {
 *     BreakIterator wb = BreakIterator.getWordInstance();
 *     wb.setText(text);
 *     int last = wb.following(pos);
 *     int current = wb.next();
 *     while (current != BreakIterator.DONE) {
 *         for (int p = last; p < current; p++) {
 *             if (Character.isLetter(text.codePointAt(p)))
 *                 return last;
 *         }
 *         last = current;
 *         current = wb.next();
 *     }
 *     return BreakIterator.DONE;
 * }
 * }</pre>
 * (The iterator returned by BreakIterator.getWordInstance() is unique in that
 * the break positions it returns don't represent both the start and end of the
 * thing being iterated over.  That is, a sentence-break iterator returns breaks
 * that each represent the end of one sentence and the beginning of the next.
 * With the word-break iterator, the characters between two boundaries might be a
 * word, or they might be the punctuation or whitespace between two words.  The
 * above code uses a simple heuristic to determine which boundary is the beginning
 * of a word: If the characters between this boundary and the next boundary
 * include at least one letter (this can be an alphabetical letter, a CJK ideograph,
 * a Hangul syllable, a Kana character, etc.), then the text between this boundary
 * and the next is a word; otherwise, it's the material between words.)
 * </blockquote>
 *
 * <p>
 *  <code> BreakIterator </code>类实现了用于查找文本中边界位置的方法。
 *  <code> BreakIterator </code>的实例维护当前位置并扫描返回发生边界的字符索引的文本。
 * 在内部,<code> BreakIterator </code>使用<code> CharacterIterator </code>扫描文本,因此能够扫描实现该协议的任何对象持有的文本。
 *  <code> StringCharacterIterator </code>用于扫描传递给<code> setText </code>的<code> String </code>对象。
 * 
 * <p>
 * 您使用此类提供的工厂方法来创建各种类型的break迭代器的实例。
 * 特别是使用<code> getWordInstance </code>,<code> getLineInstance </code>,<code> getSentenceInstance </code>
 * 和<code> getCharacterInstance </code>创建<code> BreakIterator </code>分别执行词,行,句子和字符边界分析。
 * 您使用此类提供的工厂方法来创建各种类型的break迭代器的实例。单个<code> BreakIterator </code>只能在一个单元(字,行,句子等)上工作。
 * 对于要执行的每个单位边界分析,必须使用不同的迭代器。
 * 
 *  <p> <a name="line"> </a>行边界分析确定在换行时文本字符串可以被破坏的位置。该机制正确处理标点符号和连字符词。实际线路断开还需要考虑可用的线路宽度,并由更高级别的软件处理。
 * 
 *  <p> <a name="sentence"> </a>句子边界分析允许选择对数字和缩写内的句点以及尾标点符号(如引号和括号)的正确解释。
 * 
 *  <p> <a name="word"> </a>字边界分析用于搜索和替换功能,以及允许用户双击选择字词的文本编辑应用程序中。词选择提供在词内和词后的标点符号的正确解释。
 * 不是单词的一部分的字符(例如符号或标点符号)在两侧都有分句。
 * 
 * <p> <a name="character"> </a>字符边界分析允许用户按照他们期望的方式与字符交互,例如,当将光标移动到文本字符串时。
 * 字符边界分析提供对字符串的正确导航,而不管字符是如何存储的。返回的边界可以是补充字符的边界,组合字符序列或连字簇。例如,重音字符可以存储为基本字符和变音符号。
 * 用户认为什么是字符,在不同语言之间可能不同。
 * 
 * <p>
 *  该类的工厂方法返回的<code> BreakIterator </code>实例仅用于自然语言,而不是用于编程语言文本。然而,可以定义对编程语言进行标记化的子类。
 * 
 * <P>
 *  <strong>示例</strong>：<P>创建和使用文本边界：
 * <blockquote>
 * <pre>
 *  public static void main(String args []){if(args.length == 1){String stringToExamine = args [0]; //按顺序打印每个字BreakIterator boundary = BreakIterator.getWordInstance(); boundary.setText(stringToExamine); printEachForward(boundary,stringToExamine); //以相反的顺序打印每个句子boundary = BreakIterator.getSentenceInstance(Locale.US); boundary.setText(stringToExamine); printEachBackward(boundary,stringToExamine); printFirst(boundary,stringToExamine); printLast(boundary,stringToExamine); }
 * }。
 * </pre>
 * </blockquote>
 * 
 *  按顺序打印每个元素：
 * <blockquote>
 * <pre>
 * public static void printEachForward(BreakIterator boundary,String source){int start = boundary.first(); for(int end = boundary.next(); end！= BreakIterator.DONE; start = end,end = boundary.next()){System.out.println(source.substring(start,end) }
 * }。
 * </pre>
 * </blockquote>
 * 
 *  以相反的顺序打印每个元素：
 * <blockquote>
 * <pre>
 *  public static void printEachBackward(BreakIterator boundary,String source){int end = boundary.last(); for(int start = boundary.previous(); start！= BreakIterator.DONE; end = start,start = boundary.previous()){System.out.println(source.substring(start,end)); }
 * }。
 * </pre>
 * </blockquote>
 * 
 *  打印第一个元素：
 * <blockquote>
 * <pre>
 *  public static void printFirst(BreakIterator boundary,String source){int start = boundary.first(); int end = boundary.next(); System.out.println(source.substring(start,end)); }
 * }。
 * </pre>
 * </blockquote>
 * 
 *  打印最后一个元素：
 * <blockquote>
 * <pre>
 *  public static void printLast(BreakIterator boundary,String source){int end = boundary.last(); int start = boundary.previous(); System.out.println(source.substring(start,end)); }
 * }。
 * </pre>
 * </blockquote>
 * 
 *  在指定位置打印元素：
 * <blockquote>
 * <pre>
 *  public static void printAt(BreakIterator boundary,int pos,String source){int end = boundary.following(pos); int start = boundary.previous(); System.out.println(source.substring(start,end)); }
 * }。
 * </pre>
 * </blockquote>
 * 
 *  查找下一个字词：
 * <blockquote>
 * <pre> {@ code public static int nextWordStartAfter(int pos,String text){BreakIterator wb = BreakIterator.getWordInstance(); wb.setText(text); int last = wb.following(pos); int current = wb.next(); while(current！= BreakIterator.DONE){for(int p = last; p <current; p ++){if(Character.isLetter(text.codePointAt(p)))return last; } last = current; current = wb.next(); } return BreakIterator.DONE; }} </pre>
 * (BreakIterator.getWordInstance()返回的迭代器是唯一的,因为它返回的断点位置并不代表被迭代的东西的开始和结束,也就是说,使用单词断词迭代器,两个边界之间的字符可能是一个单词
 * ,或者它们可能是两个单词之间的标点符号或空格。
 * 上面的代码使用一个简单启发法来确定哪个边界是词的开始：如果该边界和下一个边界之间的字符包括至少一个字母(这可以是字母表字母,CJK表意字符,韩文音节,假名字符等)。
 *  ),那么这个边界和下一个边界之间的文本是一个单词;否则,它是单词之间的材料。
 * </blockquote>
 * 
 * 
 * @see CharacterIterator
 *
 */

public abstract class BreakIterator implements Cloneable
{
    /**
     * Constructor. BreakIterator is stateless and has no default behavior.
     * <p>
     */
    protected BreakIterator()
    {
    }

    /**
     * Create a copy of this iterator
     * <p>
     *  构造函数。 BreakIterator是无状态的,没有默认行为。
     * 
     * 
     * @return A copy of this
     */
    @Override
    public Object clone()
    {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * DONE is returned by previous(), next(), next(int), preceding(int)
     * and following(int) when either the first or last text boundary has been
     * reached.
     * <p>
     *  创建此迭代器的副本
     * 
     */
    public static final int DONE = -1;

    /**
     * Returns the first boundary. The iterator's current position is set
     * to the first text boundary.
     * <p>
     * 当达到第一个或最后一个文本边界时,DONE由previous(),next(),next(int),preceding(int)和following(int)返回。
     * 
     * 
     * @return The character index of the first text boundary.
     */
    public abstract int first();

    /**
     * Returns the last boundary. The iterator's current position is set
     * to the last text boundary.
     * <p>
     *  返回第一个边界。迭代器的当前位置设置为第一个文本边界。
     * 
     * 
     * @return The character index of the last text boundary.
     */
    public abstract int last();

    /**
     * Returns the nth boundary from the current boundary. If either
     * the first or last text boundary has been reached, it returns
     * <code>BreakIterator.DONE</code> and the current position is set to either
     * the first or last text boundary depending on which one is reached. Otherwise,
     * the iterator's current position is set to the new boundary.
     * For example, if the iterator's current position is the mth text boundary
     * and three more boundaries exist from the current boundary to the last text
     * boundary, the next(2) call will return m + 2. The new text position is set
     * to the (m + 2)th text boundary. A next(4) call would return
     * <code>BreakIterator.DONE</code> and the last text boundary would become the
     * new text position.
     * <p>
     *  返回最后一个边界。迭代器的当前位置设置为最后一个文本边界。
     * 
     * 
     * @param n which boundary to return.  A value of 0
     * does nothing.  Negative values move to previous boundaries
     * and positive values move to later boundaries.
     * @return The character index of the nth boundary from the current position
     * or <code>BreakIterator.DONE</code> if either first or last text boundary
     * has been reached.
     */
    public abstract int next(int n);

    /**
     * Returns the boundary following the current boundary. If the current boundary
     * is the last text boundary, it returns <code>BreakIterator.DONE</code> and
     * the iterator's current position is unchanged. Otherwise, the iterator's
     * current position is set to the boundary following the current boundary.
     * <p>
     *  从当前边界返回第n个边界。如果已达到第一个或最后一个文本边界,则返回<code> BreakIterator.DONE </code>,并且根据到达哪一个,将当前位置设置为第一个或最后一个文本边界。
     * 否则,迭代器的当前位置设置为新边界。例如,如果迭代器的当前位置是第m个文本边界,并且从当前边界到最后一个文本边界存在另外三个边界,则next(2)调用将返回m + 2。
     * 新文本位置设置为(m + 2)个文本边界。 next(4)调用将返回<code> BreakIterator.DONE </code>,最后一个文本边界将成为新的文本位置。
     * 
     * 
     * @return The character index of the next text boundary or
     * <code>BreakIterator.DONE</code> if the current boundary is the last text
     * boundary.
     * Equivalent to next(1).
     * @see #next(int)
     */
    public abstract int next();

    /**
     * Returns the boundary preceding the current boundary. If the current boundary
     * is the first text boundary, it returns <code>BreakIterator.DONE</code> and
     * the iterator's current position is unchanged. Otherwise, the iterator's
     * current position is set to the boundary preceding the current boundary.
     * <p>
     *  返回当前边界之后的边界。如果当前边界是最后一个文本边界,则返回<code> BreakIterator.DONE </code>,迭代器的当前位置不变。
     * 否则,迭代器的当前位置设置为当前边界之后的边界。
     * 
     * 
     * @return The character index of the previous text boundary or
     * <code>BreakIterator.DONE</code> if the current boundary is the first text
     * boundary.
     */
    public abstract int previous();

    /**
     * Returns the first boundary following the specified character offset. If the
     * specified offset equals to the last text boundary, it returns
     * <code>BreakIterator.DONE</code> and the iterator's current position is unchanged.
     * Otherwise, the iterator's current position is set to the returned boundary.
     * The value returned is always greater than the offset or the value
     * <code>BreakIterator.DONE</code>.
     * <p>
     * 返回当前边界之前的边界。如果当前边界是第一个文本边界,则返回<code> BreakIterator.DONE </code>,迭代器的当前位置不变。否则,迭代器的当前位置设置为当前边界之前的边界。
     * 
     * 
     * @param offset the character offset to begin scanning.
     * @return The first boundary after the specified offset or
     * <code>BreakIterator.DONE</code> if the last text boundary is passed in
     * as the offset.
     * @exception  IllegalArgumentException if the specified offset is less than
     * the first text boundary or greater than the last text boundary.
     */
    public abstract int following(int offset);

    /**
     * Returns the last boundary preceding the specified character offset. If the
     * specified offset equals to the first text boundary, it returns
     * <code>BreakIterator.DONE</code> and the iterator's current position is unchanged.
     * Otherwise, the iterator's current position is set to the returned boundary.
     * The value returned is always less than the offset or the value
     * <code>BreakIterator.DONE</code>.
     * <p>
     *  返回指定字符偏移量后的第一个边界。如果指定的偏移量等于最后一个文本边界,它返回<code> BreakIterator.DONE </code>,迭代器的当前位置不变。
     * 否则,迭代器的当前位置设置为返回的边界。返回的值总是大于偏移量或值<code> BreakIterator.DONE </code>。
     * 
     * 
     * @param offset the character offset to begin scanning.
     * @return The last boundary before the specified offset or
     * <code>BreakIterator.DONE</code> if the first text boundary is passed in
     * as the offset.
     * @exception   IllegalArgumentException if the specified offset is less than
     * the first text boundary or greater than the last text boundary.
     * @since 1.2
     */
    public int preceding(int offset) {
        // NOTE:  This implementation is here solely because we can't add new
        // abstract methods to an existing class.  There is almost ALWAYS a
        // better, faster way to do this.
        int pos = following(offset);
        while (pos >= offset && pos != DONE) {
            pos = previous();
        }
        return pos;
    }

    /**
     * Returns true if the specified character offset is a text boundary.
     * <p>
     *  返回指定字符偏移之前的最后一个边界。如果指定的偏移等于第一个文本边界,它返回<code> BreakIterator.DONE </code>,迭代器的当前位置不变。
     * 否则,迭代器的当前位置设置为返回的边界。返回的值总是小于偏移量或值<code> BreakIterator.DONE </code>。
     * 
     * 
     * @param offset the character offset to check.
     * @return <code>true</code> if "offset" is a boundary position,
     * <code>false</code> otherwise.
     * @exception   IllegalArgumentException if the specified offset is less than
     * the first text boundary or greater than the last text boundary.
     * @since 1.2
     */
    public boolean isBoundary(int offset) {
        // NOTE: This implementation probably is wrong for most situations
        // because it fails to take into account the possibility that a
        // CharacterIterator passed to setText() may not have a begin offset
        // of 0.  But since the abstract BreakIterator doesn't have that
        // knowledge, it assumes the begin offset is 0.  If you subclass
        // BreakIterator, copy the SimpleTextBoundary implementation of this
        // function into your subclass.  [This should have been abstract at
        // this level, but it's too late to fix that now.]
        if (offset == 0) {
            return true;
        }
        int boundary = following(offset - 1);
        if (boundary == DONE) {
            throw new IllegalArgumentException();
        }
        return boundary == offset;
    }

    /**
     * Returns character index of the text boundary that was most
     * recently returned by next(), next(int), previous(), first(), last(),
     * following(int) or preceding(int). If any of these methods returns
     * <code>BreakIterator.DONE</code> because either first or last text boundary
     * has been reached, it returns the first or last text boundary depending on
     * which one is reached.
     * <p>
     *  如果指定的字符偏移量是文本边界,则返回true。
     * 
     * 
     * @return The text boundary returned from the above methods, first or last
     * text boundary.
     * @see #next()
     * @see #next(int)
     * @see #previous()
     * @see #first()
     * @see #last()
     * @see #following(int)
     * @see #preceding(int)
     */
    public abstract int current();

    /**
     * Get the text being scanned
     * <p>
     * 返回最近由next(),next(int),previous(),first(),last(),following(int)或preceding(int)返回的文本边界的字符索引。
     * 如果任何这些方法返回<code> BreakIterator.DONE </code>,因为已达到第一个或最后一个文本边界,它将返回第一个或最后一个文本边界,具体取决于到达哪一个。
     * 
     * 
     * @return the text being scanned
     */
    public abstract CharacterIterator getText();

    /**
     * Set a new text string to be scanned.  The current scan
     * position is reset to first().
     * <p>
     *  获取正在扫描的文本
     * 
     * 
     * @param newText new text to scan.
     */
    public void setText(String newText)
    {
        setText(new StringCharacterIterator(newText));
    }

    /**
     * Set a new text for scanning.  The current scan
     * position is reset to first().
     * <p>
     *  设置要扫描的新文本字符串。当前扫描位置复位为first()。
     * 
     * 
     * @param newText new text to scan.
     */
    public abstract void setText(CharacterIterator newText);

    private static final int CHARACTER_INDEX = 0;
    private static final int WORD_INDEX = 1;
    private static final int LINE_INDEX = 2;
    private static final int SENTENCE_INDEX = 3;

    @SuppressWarnings("unchecked")
    private static final SoftReference<BreakIteratorCache>[] iterCache = (SoftReference<BreakIteratorCache>[]) new SoftReference<?>[4];

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#word">word breaks</a>
     * for the {@linkplain Locale#getDefault() default locale}.
     * <p>
     *  设置要扫描的新文本。当前扫描位置复位为first()。
     * 
     * 
     * @return A break iterator for word breaks
     */
    public static BreakIterator getWordInstance()
    {
        return getWordInstance(Locale.getDefault());
    }

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#word">word breaks</a>
     * for the given locale.
     * <p>
     *  为{@linkplain Locale#getDefault()默认语言环境}的<a href="BreakIterator.html#word">换句话题</a>返回新的<code> BreakIt
     * erator </code>实例。
     * 
     * 
     * @param locale the desired locale
     * @return A break iterator for word breaks
     * @exception NullPointerException if <code>locale</code> is null
     */
    public static BreakIterator getWordInstance(Locale locale)
    {
        return getBreakInstance(locale, WORD_INDEX);
    }

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#line">line breaks</a>
     * for the {@linkplain Locale#getDefault() default locale}.
     * <p>
     *  为给定语言区域的<a href="BreakIterator.html#word">换句话题</a>返回新的<code> BreakIterator </code>实例。
     * 
     * 
     * @return A break iterator for line breaks
     */
    public static BreakIterator getLineInstance()
    {
        return getLineInstance(Locale.getDefault());
    }

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#line">line breaks</a>
     * for the given locale.
     * <p>
     *  为{@linkplain Locale#getDefault()默认语言环境}的<a href="BreakIterator.html#line">换行符</a>返回新的<code> BreakIte
     * rator </code>实例。
     * 
     * 
     * @param locale the desired locale
     * @return A break iterator for line breaks
     * @exception NullPointerException if <code>locale</code> is null
     */
    public static BreakIterator getLineInstance(Locale locale)
    {
        return getBreakInstance(locale, LINE_INDEX);
    }

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#character">character breaks</a>
     * for the {@linkplain Locale#getDefault() default locale}.
     * <p>
     *  为给定区域设置的<a href="BreakIterator.html#line">换行符</a>返回新的<code> BreakIterator </code>实例。
     * 
     * 
     * @return A break iterator for character breaks
     */
    public static BreakIterator getCharacterInstance()
    {
        return getCharacterInstance(Locale.getDefault());
    }

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#character">character breaks</a>
     * for the given locale.
     * <p>
     *  为{@linkplain Locale#getDefault()默认语言环境}的<a href="BreakIterator.html#character">字符断开</a>返回新的<code> Br
     * eakIterator </code>实例。
     * 
     * 
     * @param locale the desired locale
     * @return A break iterator for character breaks
     * @exception NullPointerException if <code>locale</code> is null
     */
    public static BreakIterator getCharacterInstance(Locale locale)
    {
        return getBreakInstance(locale, CHARACTER_INDEX);
    }

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#sentence">sentence breaks</a>
     * for the {@linkplain Locale#getDefault() default locale}.
     * <p>
     *  针对给定区域设置的<a href="BreakIterator.html#character">字符分隔符</a>返回新的<code> BreakIterator </code>实例。
     * 
     * 
     * @return A break iterator for sentence breaks
     */
    public static BreakIterator getSentenceInstance()
    {
        return getSentenceInstance(Locale.getDefault());
    }

    /**
     * Returns a new <code>BreakIterator</code> instance
     * for <a href="BreakIterator.html#sentence">sentence breaks</a>
     * for the given locale.
     * <p>
     * 为{@linkplain Locale#getDefault()默认语言环境}的<a href="BreakIterator.html#sentence">句子断句</a>返回新的<code> Brea
     * kIterator </code>实例。
     * 
     * 
     * @param locale the desired locale
     * @return A break iterator for sentence breaks
     * @exception NullPointerException if <code>locale</code> is null
     */
    public static BreakIterator getSentenceInstance(Locale locale)
    {
        return getBreakInstance(locale, SENTENCE_INDEX);
    }

    private static BreakIterator getBreakInstance(Locale locale, int type) {
        if (iterCache[type] != null) {
            BreakIteratorCache cache = iterCache[type].get();
            if (cache != null) {
                if (cache.getLocale().equals(locale)) {
                    return cache.createBreakInstance();
                }
            }
        }

        BreakIterator result = createBreakInstance(locale, type);
        BreakIteratorCache cache = new BreakIteratorCache(locale, result);
        iterCache[type] = new SoftReference<>(cache);
        return result;
    }

    private static BreakIterator createBreakInstance(Locale locale,
                                                     int type) {
        LocaleProviderAdapter adapter = LocaleProviderAdapter.getAdapter(BreakIteratorProvider.class, locale);
        BreakIterator iterator = createBreakInstance(adapter, locale, type);
        if (iterator == null) {
            iterator = createBreakInstance(LocaleProviderAdapter.forJRE(), locale, type);
        }
        return iterator;
    }

    private static BreakIterator createBreakInstance(LocaleProviderAdapter adapter, Locale locale, int type) {
        BreakIteratorProvider breakIteratorProvider = adapter.getBreakIteratorProvider();
        BreakIterator iterator = null;
        switch (type) {
        case CHARACTER_INDEX:
            iterator = breakIteratorProvider.getCharacterInstance(locale);
            break;
        case WORD_INDEX:
            iterator = breakIteratorProvider.getWordInstance(locale);
            break;
        case LINE_INDEX:
            iterator = breakIteratorProvider.getLineInstance(locale);
            break;
        case SENTENCE_INDEX:
            iterator = breakIteratorProvider.getSentenceInstance(locale);
            break;
        }
        return iterator;
    }

    /**
     * Returns an array of all locales for which the
     * <code>get*Instance</code> methods of this class can return
     * localized instances.
     * The returned array represents the union of locales supported by the Java
     * runtime and by installed
     * {@link java.text.spi.BreakIteratorProvider BreakIteratorProvider} implementations.
     * It must contain at least a <code>Locale</code>
     * instance equal to {@link java.util.Locale#US Locale.US}.
     *
     * <p>
     *  为给定语言环境的<a href="BreakIterator.html#sentence">句子断句</a>返回新的<code> BreakIterator </code>实例。
     * 
     * 
     * @return An array of locales for which localized
     *         <code>BreakIterator</code> instances are available.
     */
    public static synchronized Locale[] getAvailableLocales()
    {
        LocaleServiceProviderPool pool =
            LocaleServiceProviderPool.getPool(BreakIteratorProvider.class);
        return pool.getAvailableLocales();
    }

    private static final class BreakIteratorCache {

        private BreakIterator iter;
        private Locale locale;

        BreakIteratorCache(Locale locale, BreakIterator iter) {
            this.locale = locale;
            this.iter = (BreakIterator) iter.clone();
        }

        Locale getLocale() {
            return locale;
        }

        BreakIterator createBreakInstance() {
            return (BreakIterator) iter.clone();
        }
    }
}
