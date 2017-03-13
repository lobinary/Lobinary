/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.regex;

import java.util.Objects;

/**
 * An engine that performs match operations on a {@linkplain java.lang.CharSequence
 * character sequence} by interpreting a {@link Pattern}.
 *
 * <p> A matcher is created from a pattern by invoking the pattern's {@link
 * Pattern#matcher matcher} method.  Once created, a matcher can be used to
 * perform three different kinds of match operations:
 *
 * <ul>
 *
 *   <li><p> The {@link #matches matches} method attempts to match the entire
 *   input sequence against the pattern.  </p></li>
 *
 *   <li><p> The {@link #lookingAt lookingAt} method attempts to match the
 *   input sequence, starting at the beginning, against the pattern.  </p></li>
 *
 *   <li><p> The {@link #find find} method scans the input sequence looking for
 *   the next subsequence that matches the pattern.  </p></li>
 *
 * </ul>
 *
 * <p> Each of these methods returns a boolean indicating success or failure.
 * More information about a successful match can be obtained by querying the
 * state of the matcher.
 *
 * <p> A matcher finds matches in a subset of its input called the
 * <i>region</i>. By default, the region contains all of the matcher's input.
 * The region can be modified via the{@link #region region} method and queried
 * via the {@link #regionStart regionStart} and {@link #regionEnd regionEnd}
 * methods. The way that the region boundaries interact with some pattern
 * constructs can be changed. See {@link #useAnchoringBounds
 * useAnchoringBounds} and {@link #useTransparentBounds useTransparentBounds}
 * for more details.
 *
 * <p> This class also defines methods for replacing matched subsequences with
 * new strings whose contents can, if desired, be computed from the match
 * result.  The {@link #appendReplacement appendReplacement} and {@link
 * #appendTail appendTail} methods can be used in tandem in order to collect
 * the result into an existing string buffer, or the more convenient {@link
 * #replaceAll replaceAll} method can be used to create a string in which every
 * matching subsequence in the input sequence is replaced.
 *
 * <p> The explicit state of a matcher includes the start and end indices of
 * the most recent successful match.  It also includes the start and end
 * indices of the input subsequence captured by each <a
 * href="Pattern.html#cg">capturing group</a> in the pattern as well as a total
 * count of such subsequences.  As a convenience, methods are also provided for
 * returning these captured subsequences in string form.
 *
 * <p> The explicit state of a matcher is initially undefined; attempting to
 * query any part of it before a successful match will cause an {@link
 * IllegalStateException} to be thrown.  The explicit state of a matcher is
 * recomputed by every match operation.
 *
 * <p> The implicit state of a matcher includes the input character sequence as
 * well as the <i>append position</i>, which is initially zero and is updated
 * by the {@link #appendReplacement appendReplacement} method.
 *
 * <p> A matcher may be reset explicitly by invoking its {@link #reset()}
 * method or, if a new input sequence is desired, its {@link
 * #reset(java.lang.CharSequence) reset(CharSequence)} method.  Resetting a
 * matcher discards its explicit state information and sets the append position
 * to zero.
 *
 * <p> Instances of this class are not safe for use by multiple concurrent
 * threads. </p>
 *
 *
 * <p>
 *  通过解释{@link Pattern}对{@linkplain javalangCharSequence字符序列}执行匹配操作的引擎,
 * 
 *  <p>通过调用模式的{@link Pattern#matcher matcher}方法从模式创建匹配器一旦创建,匹配器可用于执行三种不同类型的匹配操作：
 * 
 * <ul>
 * 
 *  <li> <p> {@link #matches matches}方法尝试将整个输入序列与模式匹配</p> </li>
 * 
 *  <li> <p> {@link #lookingAt lookingAt}方法尝试从开头开始匹配输入序列</p> </li>
 * 
 * <li> <p> {@link #find find}方法会扫描输入序列,寻找与模式匹配的下一个子序列</p> </li>
 * 
 * </ul>
 * 
 *  <p>这些方法中的每一个都返回一个表示成功或失败的布尔值。通过查询匹配器的状态可以获得关于成功匹配的更多信息
 * 
 * <p>匹配器在其输入的一个子集中查找匹配,称为<i> region </i>。默认情况下,该区域包含所有匹配器的输入。
 * 该区域可以通过{@link #region region}通过{@link #regionStart regionStart}和{@link #regionEnd regionEnd}方法查询区域边界与
 * 某些模式构造交互的方式可以更改请参阅{@link #useAnchoringBounds useAnchoringBounds}和{@link #useTransparentBounds useTransparentBounds}
 * 更多细节。
 * <p>匹配器在其输入的一个子集中查找匹配,称为<i> region </i>。默认情况下,该区域包含所有匹配器的输入。
 * 
 * <p>此类还定义了用新字符串替换匹配子序列的方法,如果需要,可以从匹配结果计算其内容。
 * {@link #appendReplacement appendReplacement}和{@link #appendTail appendTail}方法可以串联使用以便将结果收集到现有的字符串缓冲器中
 * ,或者更方便的{@link #replaceAll replaceAll}方法可以用于创建其中输入序列中的每个匹配子序列被替换的字符串。
 * <p>此类还定义了用新字符串替换匹配子序列的方法,如果需要,可以从匹配结果计算其内容。
 * 
 * <p>匹配器的显式状态包括最近成功匹配的开始和结束索引。它还包括每个<a href=\"Patternhtml#cg\">捕获组捕获的输入子序列的开始和结束索引< a>以及这些子序列的总计数。
 * 为了方便,还提供了用于以字符串形式返回这些捕获的子序列的方法。
 * 
 *  <p>匹配器的显式状态最初未定义;在成功匹配之前尝试查询它的任何部分将导致{@link IllegalStateException}被抛出匹配器的显式状态由每个匹配操作重新计算
 * 
 * <p>匹配器的隐式状态包括输入字符序列以及<i>附加位置</i>,它最初为零,并由{@link #appendReplacement appendReplacement}方法更新
 * 
 *  <p>匹配器可以通过调用其{@link #reset()}方法显式地重置,或者如果需要新的输入序列,它的{@link #reset(javalangCharSequence)reset(CharSequence)}
 * 方法重置匹配器丢弃其显式状态信息并将追加位置设置为零。
 * 
 *  <p>此类别的实例不适用于多个并行主题</p>
 * 
 * 
 * @author      Mike McCloskey
 * @author      Mark Reinhold
 * @author      JSR-51 Expert Group
 * @since       1.4
 * @spec        JSR-51
 */

public final class Matcher implements MatchResult {

    /**
     * The Pattern object that created this Matcher.
     * <p>
     *  创建此匹配器的Pattern对象
     * 
     */
    Pattern parentPattern;

    /**
     * The storage used by groups. They may contain invalid values if
     * a group was skipped during the matching.
     * <p>
     *  组使用的存储如果在匹配期间跳过组,则它们可能包含无效值
     * 
     */
    int[] groups;

    /**
     * The range within the sequence that is to be matched. Anchors
     * will match at these "hard" boundaries. Changing the region
     * changes these values.
     * <p>
     * 要匹配的序列内的范围锚在这些"硬"边界处匹配。更改区域会更改这些值
     * 
     */
    int from, to;

    /**
     * Lookbehind uses this value to ensure that the subexpression
     * match ends at the point where the lookbehind was encountered.
     * <p>
     *  Lookbehind使用此值确保子表达式匹配在遇到lookbehind的点结束
     * 
     */
    int lookbehindTo;

    /**
     * The original string being matched.
     * <p>
     *  正在匹配的原始字符串
     * 
     */
    CharSequence text;

    /**
     * Matcher state used by the last node. NOANCHOR is used when a
     * match does not have to consume all of the input. ENDANCHOR is
     * the mode used for matching all the input.
     * <p>
     *  匹配不需要消耗所有输入时使用最后一个节点使用的匹配器状态NOANCHOR ENDANCHOR是用于匹配所有输入的模式
     * 
     */
    static final int ENDANCHOR = 1;
    static final int NOANCHOR = 0;
    int acceptMode = NOANCHOR;

    /**
     * The range of string that last matched the pattern. If the last
     * match failed then first is -1; last initially holds 0 then it
     * holds the index of the end of the last match (which is where the
     * next search starts).
     * <p>
     *  最后匹配模式的字符串范围如果最后一个匹配失败,那么第一个是-1;最后保持0,则其保持最后匹配的结束的索引(这是下一搜索开始的地方)
     * 
     */
    int first = -1, last = 0;

    /**
     * The end index of what matched in the last match operation.
     * <p>
     *  在最后一次匹配操作中匹配的结束索引
     * 
     */
    int oldLast = -1;

    /**
     * The index of the last position appended in a substitution.
     * <p>
     *  替换中附加的最后一个位置的索引
     * 
     */
    int lastAppendPosition = 0;

    /**
     * Storage used by nodes to tell what repetition they are on in
     * a pattern, and where groups begin. The nodes themselves are stateless,
     * so they rely on this field to hold state during a match.
     * <p>
     * 节点使用的存储,用于告诉它们在模式中的重复,以及组开始节点本身是无状态的,因此它们依赖此字段在匹配期间保持状态
     * 
     */
    int[] locals;

    /**
     * Boolean indicating whether or not more input could change
     * the results of the last match.
     *
     * If hitEnd is true, and a match was found, then more input
     * might cause a different match to be found.
     * If hitEnd is true and a match was not found, then more
     * input could cause a match to be found.
     * If hitEnd is false and a match was found, then more input
     * will not change the match.
     * If hitEnd is false and a match was not found, then more
     * input will not cause a match to be found.
     * <p>
     *  布尔值,指示更多输入是否可以更改上次匹配的结果
     * 
     *  如果hitEnd为true,并且找到匹配,则更多的输入可能导致找到不同的匹配如果hitEnd为真,并且没有找到匹配,则更多的输入可能导致匹配被找到如果hitEnd为假并且匹配被发现,则更多的输入将不会
     * 改变匹配如果hitEnd为假,并且没有找到匹配,则更多的输入将不会导致找到匹配。
     * 
     */
    boolean hitEnd;

    /**
     * Boolean indicating whether or not more input could change
     * a positive match into a negative one.
     *
     * If requireEnd is true, and a match was found, then more
     * input could cause the match to be lost.
     * If requireEnd is false and a match was found, then more
     * input might change the match but the match won't be lost.
     * If a match was not found, then requireEnd has no meaning.
     * <p>
     *  布尔值,指示更多输入是否可以将正匹配更改为负匹配
     * 
     * 如果requireEnd为true,并且找到匹配,则更多的输入可能导致匹配丢失如果requireEnd为假并且找到匹配,则更多的输入可能改变匹配,但匹配不会丢失如果匹配没有找到,那么requireEnd
     * 没有意义。
     * 
     */
    boolean requireEnd;

    /**
     * If transparentBounds is true then the boundaries of this
     * matcher's region are transparent to lookahead, lookbehind,
     * and boundary matching constructs that try to see beyond them.
     * <p>
     *  如果transparentBounds为true,那么这个匹配器区域的边界对于lookahead,lookbehind和边界匹配结构是透明的,它们试图看到它们
     * 
     */
    boolean transparentBounds = false;

    /**
     * If anchoringBounds is true then the boundaries of this
     * matcher's region match anchors such as ^ and $.
     * <p>
     *  如果anchoringBounds为true,那么这个匹配器区域的边界匹配锚如^和$
     * 
     */
    boolean anchoringBounds = true;

    /**
     * No default constructor.
     * <p>
     *  没有默认构造函数
     * 
     */
    Matcher() {
    }

    /**
     * All matchers have the state used by Pattern during a match.
     * <p>
     *  所有匹配器都具有匹配期间Pattern使用的状态
     * 
     */
    Matcher(Pattern parent, CharSequence text) {
        this.parentPattern = parent;
        this.text = text;

        // Allocate state storage
        int parentGroupCount = Math.max(parent.capturingGroupCount, 10);
        groups = new int[parentGroupCount * 2];
        locals = new int[parent.localCount];

        // Put fields into initial states
        reset();
    }

    /**
     * Returns the pattern that is interpreted by this matcher.
     *
     * <p>
     *  返回此匹配器解释的模式
     * 
     * 
     * @return  The pattern for which this matcher was created
     */
    public Pattern pattern() {
        return parentPattern;
    }

    /**
     * Returns the match state of this matcher as a {@link MatchResult}.
     * The result is unaffected by subsequent operations performed upon this
     * matcher.
     *
     * <p>
     * 返回此匹配器的匹配状态为{@link MatchResult}结果不受此匹配器上执行的后续操作的影响
     * 
     * 
     * @return  a <code>MatchResult</code> with the state of this matcher
     * @since 1.5
     */
    public MatchResult toMatchResult() {
        Matcher result = new Matcher(this.parentPattern, text.toString());
        result.first = this.first;
        result.last = this.last;
        result.groups = this.groups.clone();
        return result;
    }

    /**
      * Changes the <tt>Pattern</tt> that this <tt>Matcher</tt> uses to
      * find matches with.
      *
      * <p> This method causes this matcher to lose information
      * about the groups of the last match that occurred. The
      * matcher's position in the input is maintained and its
      * last append position is unaffected.</p>
      *
      * <p>
      *  更改<tt>模式</tt>(此<tt>匹配器</tt>用于查找匹配项)
      * 
      *  <p>此方法导致此匹配器丢失关于发生的最后匹配的组的信息。输入中的匹配器的位置被保持,并且其最后附加位置不受影响。</p>
      * 
      * 
      * @param  newPattern
      *         The new pattern used by this matcher
      * @return  This matcher
      * @throws  IllegalArgumentException
      *          If newPattern is <tt>null</tt>
      * @since 1.5
      */
    public Matcher usePattern(Pattern newPattern) {
        if (newPattern == null)
            throw new IllegalArgumentException("Pattern cannot be null");
        parentPattern = newPattern;

        // Reallocate state storage
        int parentGroupCount = Math.max(newPattern.capturingGroupCount, 10);
        groups = new int[parentGroupCount * 2];
        locals = new int[newPattern.localCount];
        for (int i = 0; i < groups.length; i++)
            groups[i] = -1;
        for (int i = 0; i < locals.length; i++)
            locals[i] = -1;
        return this;
    }

    /**
     * Resets this matcher.
     *
     * <p> Resetting a matcher discards all of its explicit state information
     * and sets its append position to zero. The matcher's region is set to the
     * default region, which is its entire character sequence. The anchoring
     * and transparency of this matcher's region boundaries are unaffected.
     *
     * <p>
     *  重置此匹配器
     * 
     *  <p>重置匹配器会丢弃所有显式状态信息,并将其附加位置设置为零。匹配器的区域设置为默认区域,即其整个字符序列。此匹配器区域边界的锚定和透明度不受影响
     * 
     * 
     * @return  This matcher
     */
    public Matcher reset() {
        first = -1;
        last = 0;
        oldLast = -1;
        for(int i=0; i<groups.length; i++)
            groups[i] = -1;
        for(int i=0; i<locals.length; i++)
            locals[i] = -1;
        lastAppendPosition = 0;
        from = 0;
        to = getTextLength();
        return this;
    }

    /**
     * Resets this matcher with a new input sequence.
     *
     * <p> Resetting a matcher discards all of its explicit state information
     * and sets its append position to zero.  The matcher's region is set to
     * the default region, which is its entire character sequence.  The
     * anchoring and transparency of this matcher's region boundaries are
     * unaffected.
     *
     * <p>
     *  使用新的输入序列重置此匹配器
     * 
     * <p>重置匹配器会丢弃所有显式状态信息,并将其附加位置设置为零。匹配器的区域设置为默认区域,即其整个字符序列。此匹配器区域边界的锚定和透明度不受影响
     * 
     * 
     * @param  input
     *         The new input character sequence
     *
     * @return  This matcher
     */
    public Matcher reset(CharSequence input) {
        text = input;
        return reset();
    }

    /**
     * Returns the start index of the previous match.
     *
     * <p>
     *  返回上一个匹配的开始索引
     * 
     * 
     * @return  The index of the first character matched
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     */
    public int start() {
        if (first < 0)
            throw new IllegalStateException("No match available");
        return first;
    }

    /**
     * Returns the start index of the subsequence captured by the given group
     * during the previous match operation.
     *
     * <p> <a href="Pattern.html#cg">Capturing groups</a> are indexed from left
     * to right, starting at one.  Group zero denotes the entire pattern, so
     * the expression <i>m.</i><tt>start(0)</tt> is equivalent to
     * <i>m.</i><tt>start()</tt>.  </p>
     *
     * <p>
     *  返回在上一个匹配操作期间由给定组捕获的子序列的开始索引
     * 
     *  <p> <a href=\"Patternhtml#cg\">捕获组</a>从左到右进行索引,从一个组开始,组0表示整个模式,因此表达式<i> m </i> <tt> start(0)</tt>等效于
     * <i> m </i> <tt> start()</tt> </p>。
     * 
     * 
     * @param  group
     *         The index of a capturing group in this matcher's pattern
     *
     * @return  The index of the first character captured by the group,
     *          or <tt>-1</tt> if the match was successful but the group
     *          itself did not match anything
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     *
     * @throws  IndexOutOfBoundsException
     *          If there is no capturing group in the pattern
     *          with the given index
     */
    public int start(int group) {
        if (first < 0)
            throw new IllegalStateException("No match available");
        if (group < 0 || group > groupCount())
            throw new IndexOutOfBoundsException("No group " + group);
        return groups[group * 2];
    }

    /**
     * Returns the start index of the subsequence captured by the given
     * <a href="Pattern.html#groupname">named-capturing group</a> during the
     * previous match operation.
     *
     * <p>
     * 返回在先前匹配操作期间由给定<a href=\"Patternhtml#groupname\">命名捕获组</a>捕获的子序列的开始索引
     * 
     * 
     * @param  name
     *         The name of a named-capturing group in this matcher's pattern
     *
     * @return  The index of the first character captured by the group,
     *          or {@code -1} if the match was successful but the group
     *          itself did not match anything
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     *
     * @throws  IllegalArgumentException
     *          If there is no capturing group in the pattern
     *          with the given name
     * @since 1.8
     */
    public int start(String name) {
        return groups[getMatchedGroupIndex(name) * 2];
    }

    /**
     * Returns the offset after the last character matched.
     *
     * <p>
     *  返回在匹配的最后一个字符之后的偏移量
     * 
     * 
     * @return  The offset after the last character matched
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     */
    public int end() {
        if (first < 0)
            throw new IllegalStateException("No match available");
        return last;
    }

    /**
     * Returns the offset after the last character of the subsequence
     * captured by the given group during the previous match operation.
     *
     * <p> <a href="Pattern.html#cg">Capturing groups</a> are indexed from left
     * to right, starting at one.  Group zero denotes the entire pattern, so
     * the expression <i>m.</i><tt>end(0)</tt> is equivalent to
     * <i>m.</i><tt>end()</tt>.  </p>
     *
     * <p>
     *  返回在上一个匹配操作期间由给定组捕获的子序列的最后一个字符之后的偏移量
     * 
     *  <p> <a href=\"Patternhtml#cg\">捕获组</a>从左到右进行索引,从一个组开始,组0表示整个模式,因此表达式<i> m </i> <tt> end(0)</tt>等效于<i>
     *  m </i> <tt> end()</tt> </p>。
     * 
     * 
     * @param  group
     *         The index of a capturing group in this matcher's pattern
     *
     * @return  The offset after the last character captured by the group,
     *          or <tt>-1</tt> if the match was successful
     *          but the group itself did not match anything
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     *
     * @throws  IndexOutOfBoundsException
     *          If there is no capturing group in the pattern
     *          with the given index
     */
    public int end(int group) {
        if (first < 0)
            throw new IllegalStateException("No match available");
        if (group < 0 || group > groupCount())
            throw new IndexOutOfBoundsException("No group " + group);
        return groups[group * 2 + 1];
    }

    /**
     * Returns the offset after the last character of the subsequence
     * captured by the given <a href="Pattern.html#groupname">named-capturing
     * group</a> during the previous match operation.
     *
     * <p>
     *  返回之前匹配操作期间由给定<a href=\"Patternhtml#groupname\">命名捕获组</a>捕获的子序列的最后一个字符后的偏移量
     * 
     * 
     * @param  name
     *         The name of a named-capturing group in this matcher's pattern
     *
     * @return  The offset after the last character captured by the group,
     *          or {@code -1} if the match was successful
     *          but the group itself did not match anything
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     *
     * @throws  IllegalArgumentException
     *          If there is no capturing group in the pattern
     *          with the given name
     * @since 1.8
     */
    public int end(String name) {
        return groups[getMatchedGroupIndex(name) * 2 + 1];
    }

    /**
     * Returns the input subsequence matched by the previous match.
     *
     * <p> For a matcher <i>m</i> with input sequence <i>s</i>,
     * the expressions <i>m.</i><tt>group()</tt> and
     * <i>s.</i><tt>substring(</tt><i>m.</i><tt>start(),</tt>&nbsp;<i>m.</i><tt>end())</tt>
     * are equivalent.  </p>
     *
     * <p> Note that some patterns, for example <tt>a*</tt>, match the empty
     * string.  This method will return the empty string when the pattern
     * successfully matches the empty string in the input.  </p>
     *
     * <p>
     * 返回与上一个匹配匹配的输入子序列
     * 
     *  <p>对于具有输入序列<i> s </i>的匹配器m,表达式<i> m <tt> group()</tt>和<i> s <tt>子串(</tt> <i> m </i> <tt> start(),</tt>
     * &nbsp; m </i> <tt> end </tt>是等效的</p>。
     * 
     *  <p>请注意,某些模式(例如<tt> a * </tt>)与空字符串匹配此方法将在模式成功匹配输入中的空字符串时返回空字符串</p>
     * 
     * 
     * @return The (possibly empty) subsequence matched by the previous match,
     *         in string form
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     */
    public String group() {
        return group(0);
    }

    /**
     * Returns the input subsequence captured by the given group during the
     * previous match operation.
     *
     * <p> For a matcher <i>m</i>, input sequence <i>s</i>, and group index
     * <i>g</i>, the expressions <i>m.</i><tt>group(</tt><i>g</i><tt>)</tt> and
     * <i>s.</i><tt>substring(</tt><i>m.</i><tt>start(</tt><i>g</i><tt>),</tt>&nbsp;<i>m.</i><tt>end(</tt><i>g</i><tt>))</tt>
     * are equivalent.  </p>
     *
     * <p> <a href="Pattern.html#cg">Capturing groups</a> are indexed from left
     * to right, starting at one.  Group zero denotes the entire pattern, so
     * the expression <tt>m.group(0)</tt> is equivalent to <tt>m.group()</tt>.
     * </p>
     *
     * <p> If the match was successful but the group specified failed to match
     * any part of the input sequence, then <tt>null</tt> is returned. Note
     * that some groups, for example <tt>(a*)</tt>, match the empty string.
     * This method will return the empty string when such a group successfully
     * matches the empty string in the input.  </p>
     *
     * <p>
     *  返回在先前的匹配操作期间由给定组捕获的输入子序列
     * 
     * 对于匹配器m,输入序列<i> s和组索引</i>,表达式<m> <tt > </tt>(</tt> <i> <tt>)</tt>和<i> </tt> start(</tt> <i> g </i> <tt>
     * ),</tt>&nbsp; m </i> <tt> end(</tt> i> <tt>))</tt>等效</p>。
     * 
     *  <p> <a href=\"Patternhtml#cg\">捕获组</a>从左到右进行索引,从一个组开始,组0表示整个模式,因此表达式<tt> mgroup(0)</tt>等效于<tt> mgrou
     * p()</tt>。
     * </p>
     * 
     * <p>如果匹配成功,但指定的组未能匹配输入序列的任何部分,则返回<tt> null </tt>注意,有些组,例如<tt>(a *)</tt >,匹配空字符串当此组成功匹配输入</p>中的空字符串时,此方
     * 法将返回空字符串。
     * 
     * 
     * @param  group
     *         The index of a capturing group in this matcher's pattern
     *
     * @return  The (possibly empty) subsequence captured by the group
     *          during the previous match, or <tt>null</tt> if the group
     *          failed to match part of the input
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     *
     * @throws  IndexOutOfBoundsException
     *          If there is no capturing group in the pattern
     *          with the given index
     */
    public String group(int group) {
        if (first < 0)
            throw new IllegalStateException("No match found");
        if (group < 0 || group > groupCount())
            throw new IndexOutOfBoundsException("No group " + group);
        if ((groups[group*2] == -1) || (groups[group*2+1] == -1))
            return null;
        return getSubSequence(groups[group * 2], groups[group * 2 + 1]).toString();
    }

    /**
     * Returns the input subsequence captured by the given
     * <a href="Pattern.html#groupname">named-capturing group</a> during the previous
     * match operation.
     *
     * <p> If the match was successful but the group specified failed to match
     * any part of the input sequence, then <tt>null</tt> is returned. Note
     * that some groups, for example <tt>(a*)</tt>, match the empty string.
     * This method will return the empty string when such a group successfully
     * matches the empty string in the input.  </p>
     *
     * <p>
     *  返回在上一次匹配操作期间由给定<a href=\"Patternhtml#groupname\">命名捕获组</a>捕获的输入子序列
     * 
     * <p>如果匹配成功,但指定的组未能匹配输入序列的任何部分,则返回<tt> null </tt>注意,有些组,例如<tt>(a *)</tt >,匹配空字符串当此组成功匹配输入</p>中的空字符串时,此方
     * 法将返回空字符串。
     * 
     * 
     * @param  name
     *         The name of a named-capturing group in this matcher's pattern
     *
     * @return  The (possibly empty) subsequence captured by the named group
     *          during the previous match, or <tt>null</tt> if the group
     *          failed to match part of the input
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     *
     * @throws  IllegalArgumentException
     *          If there is no capturing group in the pattern
     *          with the given name
     * @since 1.7
     */
    public String group(String name) {
        int group = getMatchedGroupIndex(name);
        if ((groups[group*2] == -1) || (groups[group*2+1] == -1))
            return null;
        return getSubSequence(groups[group * 2], groups[group * 2 + 1]).toString();
    }

    /**
     * Returns the number of capturing groups in this matcher's pattern.
     *
     * <p> Group zero denotes the entire pattern by convention. It is not
     * included in this count.
     *
     * <p> Any non-negative integer smaller than or equal to the value
     * returned by this method is guaranteed to be a valid group index for
     * this matcher.  </p>
     *
     * <p>
     *  返回此匹配器模式中捕获组的数量
     * 
     *  <p>组0按照惯例表示整个模式它不包括在此计数中
     * 
     *  <p>小于或等于此方法返回的值的任何非负整数保证为此匹配器的有效组索引</p>
     * 
     * 
     * @return The number of capturing groups in this matcher's pattern
     */
    public int groupCount() {
        return parentPattern.capturingGroupCount - 1;
    }

    /**
     * Attempts to match the entire region against the pattern.
     *
     * <p> If the match succeeds then more information can be obtained via the
     * <tt>start</tt>, <tt>end</tt>, and <tt>group</tt> methods.  </p>
     *
     * <p>
     *  尝试将整个区域与模式匹配
     * 
     * <p>如果匹配成功,则可以通过<tt>开始</tt>,<tt>结束</tt>和<tt>组</tt>方法</p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, the entire region sequence
     *          matches this matcher's pattern
     */
    public boolean matches() {
        return match(from, ENDANCHOR);
    }

    /**
     * Attempts to find the next subsequence of the input sequence that matches
     * the pattern.
     *
     * <p> This method starts at the beginning of this matcher's region, or, if
     * a previous invocation of the method was successful and the matcher has
     * not since been reset, at the first character not matched by the previous
     * match.
     *
     * <p> If the match succeeds then more information can be obtained via the
     * <tt>start</tt>, <tt>end</tt>, and <tt>group</tt> methods.  </p>
     *
     * <p>
     *  尝试查找与模式匹配的输入序列的下一个子序列
     * 
     *  <p>此方法在此匹配器区域的开始处开始,或者如果方法的先前调用成功并且匹配器之前未被重置,则在与先前匹配不匹配的第一个字符
     * 
     *  <p>如果匹配成功,则可以通过<tt>开始</tt>,<tt>结束</tt>和<tt>组</tt>方法</p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, a subsequence of the input
     *          sequence matches this matcher's pattern
     */
    public boolean find() {
        int nextSearchIndex = last;
        if (nextSearchIndex == first)
            nextSearchIndex++;

        // If next search starts before region, start it at region
        if (nextSearchIndex < from)
            nextSearchIndex = from;

        // If next search starts beyond region then it fails
        if (nextSearchIndex > to) {
            for (int i = 0; i < groups.length; i++)
                groups[i] = -1;
            return false;
        }
        return search(nextSearchIndex);
    }

    /**
     * Resets this matcher and then attempts to find the next subsequence of
     * the input sequence that matches the pattern, starting at the specified
     * index.
     *
     * <p> If the match succeeds then more information can be obtained via the
     * <tt>start</tt>, <tt>end</tt>, and <tt>group</tt> methods, and subsequent
     * invocations of the {@link #find()} method will start at the first
     * character not matched by this match.  </p>
     *
     * <p>
     *  重置此匹配器,然后尝试查找与指定索引处开始的模式匹配的输入序列的下一个子序列
     * 
     * <p>如果匹配成功,则可以通过<tt>开始</tt>,<tt>结束</tt>和<tt>组</tt>方法获得更多信息, @link #find()}方法将从不匹配此匹配的第一个字符开始。</p>
     * 
     * 
     * @param start the index to start searching for a match
     * @throws  IndexOutOfBoundsException
     *          If start is less than zero or if start is greater than the
     *          length of the input sequence.
     *
     * @return  <tt>true</tt> if, and only if, a subsequence of the input
     *          sequence starting at the given index matches this matcher's
     *          pattern
     */
    public boolean find(int start) {
        int limit = getTextLength();
        if ((start < 0) || (start > limit))
            throw new IndexOutOfBoundsException("Illegal start index");
        reset();
        return search(start);
    }

    /**
     * Attempts to match the input sequence, starting at the beginning of the
     * region, against the pattern.
     *
     * <p> Like the {@link #matches matches} method, this method always starts
     * at the beginning of the region; unlike that method, it does not
     * require that the entire region be matched.
     *
     * <p> If the match succeeds then more information can be obtained via the
     * <tt>start</tt>, <tt>end</tt>, and <tt>group</tt> methods.  </p>
     *
     * <p>
     *  尝试将输入序列(从区域开头开始)与模式匹配
     * 
     *  <p>与{@link #matches matches}方法一样,此方法始终从区域的开头开始;不像该方法,它不需要整个区域匹配
     * 
     *  <p>如果匹配成功,则可以通过<tt>开始</tt>,<tt>结束</tt>和<tt>组</tt>方法</p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, a prefix of the input
     *          sequence matches this matcher's pattern
     */
    public boolean lookingAt() {
        return match(from, NOANCHOR);
    }

    /**
     * Returns a literal replacement <code>String</code> for the specified
     * <code>String</code>.
     *
     * This method produces a <code>String</code> that will work
     * as a literal replacement <code>s</code> in the
     * <code>appendReplacement</code> method of the {@link Matcher} class.
     * The <code>String</code> produced will match the sequence of characters
     * in <code>s</code> treated as a literal sequence. Slashes ('\') and
     * dollar signs ('$') will be given no special meaning.
     *
     * <p>
     *  返回指定<code> String </code>的字面替换<code> String </code>
     * 
     * 这个方法产生一个在{@link Matcher}类的<code> appendReplacement </code>方法中用作文字替换<code> s </code>的<code> String </code>
     * 字符串</code>将匹配<code> s </code>中处理为字面序列的字符序列。
     * 斜线('\\')和美元符号('$')将没有特殊含义。
     * 
     * 
     * @param  s The string to be literalized
     * @return  A literal string replacement
     * @since 1.5
     */
    public static String quoteReplacement(String s) {
        if ((s.indexOf('\\') == -1) && (s.indexOf('$') == -1))
            return s;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' || c == '$') {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Implements a non-terminal append-and-replace step.
     *
     * <p> This method performs the following actions: </p>
     *
     * <ol>
     *
     *   <li><p> It reads characters from the input sequence, starting at the
     *   append position, and appends them to the given string buffer.  It
     *   stops after reading the last character preceding the previous match,
     *   that is, the character at index {@link
     *   #start()}&nbsp;<tt>-</tt>&nbsp;<tt>1</tt>.  </p></li>
     *
     *   <li><p> It appends the given replacement string to the string buffer.
     *   </p></li>
     *
     *   <li><p> It sets the append position of this matcher to the index of
     *   the last character matched, plus one, that is, to {@link #end()}.
     *   </p></li>
     *
     * </ol>
     *
     * <p> The replacement string may contain references to subsequences
     * captured during the previous match: Each occurrence of
     * <tt>${</tt><i>name</i><tt>}</tt> or <tt>$</tt><i>g</i>
     * will be replaced by the result of evaluating the corresponding
     * {@link #group(String) group(name)} or {@link #group(int) group(g)}
     * respectively. For  <tt>$</tt><i>g</i>,
     * the first number after the <tt>$</tt> is always treated as part of
     * the group reference. Subsequent numbers are incorporated into g if
     * they would form a legal group reference. Only the numerals '0'
     * through '9' are considered as potential components of the group
     * reference. If the second group matched the string <tt>"foo"</tt>, for
     * example, then passing the replacement string <tt>"$2bar"</tt> would
     * cause <tt>"foobar"</tt> to be appended to the string buffer. A dollar
     * sign (<tt>$</tt>) may be included as a literal in the replacement
     * string by preceding it with a backslash (<tt>\$</tt>).
     *
     * <p> Note that backslashes (<tt>\</tt>) and dollar signs (<tt>$</tt>) in
     * the replacement string may cause the results to be different than if it
     * were being treated as a literal replacement string. Dollar signs may be
     * treated as references to captured subsequences as described above, and
     * backslashes are used to escape literal characters in the replacement
     * string.
     *
     * <p> This method is intended to be used in a loop together with the
     * {@link #appendTail appendTail} and {@link #find find} methods.  The
     * following code, for example, writes <tt>one dog two dogs in the
     * yard</tt> to the standard-output stream: </p>
     *
     * <blockquote><pre>
     * Pattern p = Pattern.compile("cat");
     * Matcher m = p.matcher("one cat two cats in the yard");
     * StringBuffer sb = new StringBuffer();
     * while (m.find()) {
     *     m.appendReplacement(sb, "dog");
     * }
     * m.appendTail(sb);
     * System.out.println(sb.toString());</pre></blockquote>
     *
     * <p>
     *  实现非终端追加和替换步骤
     * 
     *  <p>此方法执行以下操作：</p>
     * 
     * <ol>
     * 
     *  <li> <p>它从输入序列读取字符,从追加位置开始,并将它们附加到给定的字符串缓冲区。
     * 在读取前一个匹配之前的最后一个字符时停止,也就是索引{@link #start()}&nbsp; <tt>  -  </tt>&nbsp; <tt> 1 </tt> </p> </li>。
     * 
     * <li> <p>它将给定的替换字符串附加到字符串缓冲区</p> </li>
     * 
     *  <li> <p>它将此匹配器的附加位置设置为匹配的最后一个字符的索引,加上一个,即{@link #end()} </p> </li>
     * 
     * </ol>
     * 
     * <p>替换字符串可能包含对上一次匹配过程中捕获的子序列的引用：<tt> $ {</tt> <i> name </i> <tt>} </tt>或<tt> $ </tt> <i> g </i>将被分别评估相
     * 应的{@link #group(String)group(name)}或{@link #group(int)group对于<tt> $ </tt> <i> g </i>,<tt> $ </tt>之后的第一个数字始终被视为组参考的一部分。
     * 法定组参考只有数字'0'到'9'被认为是组参考的电位分量例如,如果第二组匹配字符串<tt>"foo"</tt>,则传递替换字符串<tt>"$ 2bar"</tt>会导致<foo> </tt>附加到字符串
     * 缓冲区美元符号(<tt> $ </tt>)可以作为替换字符串中的文字包含在其前面的反斜杠(<tt> \\ $ </tt>)。
     * 
     * <p>请注意,替换字符串中的反斜杠(<tt> \\ </tt>)和美元符号(<tt> $ </tt>)可能会导致结果与被视为替换字符串字符串美元符号可以被视为对上述捕获的子序列的引用,反斜杠用于替换替换
     * 字符串中的文字字符。
     * 
     *  <p>此方法旨在与{@link #appendTail appendTail}和{@link #find find}方法一起使用在以下代码中,例如,在院子里写入<tt>一只狗两只狗</tt>到标准输出
     * 流：</p>。
     * 
     * <blockquote> <pre> Pattern p = Patterncompile("cat"); Matcher m = pmatcher("one cat two cats in the y
     * ard"); StringBuffer sb = new StringBuffer(); while(mfind()){mappendReplacement(sb,"dog"); } mappendTa
     * il(sb); Systemoutprintln(sbtoString()); </pre> </blockquote>。
     * 
     * 
     * @param  sb
     *         The target string buffer
     *
     * @param  replacement
     *         The replacement string
     *
     * @return  This matcher
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     *
     * @throws  IllegalArgumentException
     *          If the replacement string refers to a named-capturing
     *          group that does not exist in the pattern
     *
     * @throws  IndexOutOfBoundsException
     *          If the replacement string refers to a capturing group
     *          that does not exist in the pattern
     */
    public Matcher appendReplacement(StringBuffer sb, String replacement) {

        // If no match, return error
        if (first < 0)
            throw new IllegalStateException("No match available");

        // Process substitution string to replace group references with groups
        int cursor = 0;
        StringBuilder result = new StringBuilder();

        while (cursor < replacement.length()) {
            char nextChar = replacement.charAt(cursor);
            if (nextChar == '\\') {
                cursor++;
                if (cursor == replacement.length())
                    throw new IllegalArgumentException(
                        "character to be escaped is missing");
                nextChar = replacement.charAt(cursor);
                result.append(nextChar);
                cursor++;
            } else if (nextChar == '$') {
                // Skip past $
                cursor++;
                // Throw IAE if this "$" is the last character in replacement
                if (cursor == replacement.length())
                   throw new IllegalArgumentException(
                        "Illegal group reference: group index is missing");
                nextChar = replacement.charAt(cursor);
                int refNum = -1;
                if (nextChar == '{') {
                    cursor++;
                    StringBuilder gsb = new StringBuilder();
                    while (cursor < replacement.length()) {
                        nextChar = replacement.charAt(cursor);
                        if (ASCII.isLower(nextChar) ||
                            ASCII.isUpper(nextChar) ||
                            ASCII.isDigit(nextChar)) {
                            gsb.append(nextChar);
                            cursor++;
                        } else {
                            break;
                        }
                    }
                    if (gsb.length() == 0)
                        throw new IllegalArgumentException(
                            "named capturing group has 0 length name");
                    if (nextChar != '}')
                        throw new IllegalArgumentException(
                            "named capturing group is missing trailing '}'");
                    String gname = gsb.toString();
                    if (ASCII.isDigit(gname.charAt(0)))
                        throw new IllegalArgumentException(
                            "capturing group name {" + gname +
                            "} starts with digit character");
                    if (!parentPattern.namedGroups().containsKey(gname))
                        throw new IllegalArgumentException(
                            "No group with name {" + gname + "}");
                    refNum = parentPattern.namedGroups().get(gname);
                    cursor++;
                } else {
                    // The first number is always a group
                    refNum = (int)nextChar - '0';
                    if ((refNum < 0)||(refNum > 9))
                        throw new IllegalArgumentException(
                            "Illegal group reference");
                    cursor++;
                    // Capture the largest legal group string
                    boolean done = false;
                    while (!done) {
                        if (cursor >= replacement.length()) {
                            break;
                        }
                        int nextDigit = replacement.charAt(cursor) - '0';
                        if ((nextDigit < 0)||(nextDigit > 9)) { // not a number
                            break;
                        }
                        int newRefNum = (refNum * 10) + nextDigit;
                        if (groupCount() < newRefNum) {
                            done = true;
                        } else {
                            refNum = newRefNum;
                            cursor++;
                        }
                    }
                }
                // Append group
                if (start(refNum) != -1 && end(refNum) != -1)
                    result.append(text, start(refNum), end(refNum));
            } else {
                result.append(nextChar);
                cursor++;
            }
        }
        // Append the intervening text
        sb.append(text, lastAppendPosition, first);
        // Append the match substitution
        sb.append(result);

        lastAppendPosition = last;
        return this;
    }

    /**
     * Implements a terminal append-and-replace step.
     *
     * <p> This method reads characters from the input sequence, starting at
     * the append position, and appends them to the given string buffer.  It is
     * intended to be invoked after one or more invocations of the {@link
     * #appendReplacement appendReplacement} method in order to copy the
     * remainder of the input sequence.  </p>
     *
     * <p>
     *  实施终端追加和替换步骤
     * 
     *  <p>此方法从输入序列中读取字符,从追加位置开始,并将它们附加到给定的字符串缓冲区。
     * 它旨在在{@link #appendReplacement appendReplacement}方法的一次或多次调用后调用,以复制输入序列的剩余部分</p>。
     * 
     * 
     * @param  sb
     *         The target string buffer
     *
     * @return  The target string buffer
     */
    public StringBuffer appendTail(StringBuffer sb) {
        sb.append(text, lastAppendPosition, getTextLength());
        return sb;
    }

    /**
     * Replaces every subsequence of the input sequence that matches the
     * pattern with the given replacement string.
     *
     * <p> This method first resets this matcher.  It then scans the input
     * sequence looking for matches of the pattern.  Characters that are not
     * part of any match are appended directly to the result string; each match
     * is replaced in the result by the replacement string.  The replacement
     * string may contain references to captured subsequences as in the {@link
     * #appendReplacement appendReplacement} method.
     *
     * <p> Note that backslashes (<tt>\</tt>) and dollar signs (<tt>$</tt>) in
     * the replacement string may cause the results to be different than if it
     * were being treated as a literal replacement string. Dollar signs may be
     * treated as references to captured subsequences as described above, and
     * backslashes are used to escape literal characters in the replacement
     * string.
     *
     * <p> Given the regular expression <tt>a*b</tt>, the input
     * <tt>"aabfooaabfooabfoob"</tt>, and the replacement string
     * <tt>"-"</tt>, an invocation of this method on a matcher for that
     * expression would yield the string <tt>"-foo-foo-foo-"</tt>.
     *
     * <p> Invoking this method changes this matcher's state.  If the matcher
     * is to be used in further matching operations then it should first be
     * reset.  </p>
     *
     * <p>
     *  用给定的替换字符串替换与模式匹配的输入序列的每个子序列
     * 
     * <p>此方法首先重置此匹配器然后扫描输入序列查找模式的匹配不是任何匹配的一部分的字符直接附加到结果字符串;每个匹配在替换字符串的结果中替换替换字符串可以包含对捕获的子序列的引用,如{@link #appendReplacement appendReplacement}
     * 方法。
     * 
     *  <p>请注意,替换字符串中的反斜杠(<tt> \\ </tt>)和美元符号(<tt> $ </tt>)可能会导致结果与被视为替换字符串字符串美元符号可以被视为对上述捕获的子序列的引用,反斜杠用于替换替
     * 换字符串中的文字字符。
     * 
     * <p>给定正则表达式<tt> a * b </tt>,输入<tt>"aabfooaabfooabfoob"</tt>和替换字符串<tt>" - "</tt>方法对该表达式的匹配器将产生字符串<tt>" 
     * -  foo-foo-foo  - "</tt>。
     * 
     *  <p>调用此方法将更改此匹配器的状态如果匹配器将用于进一步匹配操作,则应首先重置</p>
     * 
     * 
     * @param  replacement
     *         The replacement string
     *
     * @return  The string constructed by replacing each matching subsequence
     *          by the replacement string, substituting captured subsequences
     *          as needed
     */
    public String replaceAll(String replacement) {
        reset();
        boolean result = find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {
                appendReplacement(sb, replacement);
                result = find();
            } while (result);
            appendTail(sb);
            return sb.toString();
        }
        return text.toString();
    }

    /**
     * Replaces the first subsequence of the input sequence that matches the
     * pattern with the given replacement string.
     *
     * <p> This method first resets this matcher.  It then scans the input
     * sequence looking for a match of the pattern.  Characters that are not
     * part of the match are appended directly to the result string; the match
     * is replaced in the result by the replacement string.  The replacement
     * string may contain references to captured subsequences as in the {@link
     * #appendReplacement appendReplacement} method.
     *
     * <p>Note that backslashes (<tt>\</tt>) and dollar signs (<tt>$</tt>) in
     * the replacement string may cause the results to be different than if it
     * were being treated as a literal replacement string. Dollar signs may be
     * treated as references to captured subsequences as described above, and
     * backslashes are used to escape literal characters in the replacement
     * string.
     *
     * <p> Given the regular expression <tt>dog</tt>, the input
     * <tt>"zzzdogzzzdogzzz"</tt>, and the replacement string
     * <tt>"cat"</tt>, an invocation of this method on a matcher for that
     * expression would yield the string <tt>"zzzcatzzzdogzzz"</tt>.  </p>
     *
     * <p> Invoking this method changes this matcher's state.  If the matcher
     * is to be used in further matching operations then it should first be
     * reset.  </p>
     *
     * <p>
     *  将与模式匹配的输入序列的第一个子序列替换为给定的替换字符串
     * 
     * <p>此方法首先重置此匹配器然后扫描输入序列查找匹配的模式不是匹配的字符的一部分直接附加到结果字符串;匹配在替换字符串的结果中替换替换字符串可以包含对捕获的子序列的引用,如{@link #appendReplacement appendReplacement}
     * 方法。
     * 
     *  <p>请注意,替换字符串中的反斜杠(<tt> \\ </tt>)和美元符号(<tt> $ </tt>)可能会导致结果与被视为替换字符串字符串美元符号可以被视为对上述捕获的子序列的引用,反斜杠用于替换替
     * 换字符串中的文字字符。
     * 
     * <p>给定正则表达式<tt> dog </tt>,输入<tt>"zzzdogzzzdogzzz"</tt>和替换字符串<tt>"cat"</tt>该表达式的匹配器将产生字符串<tt>"zzzcatzzz
     * dogzzz"</tt> </p>。
     * 
     *  <p>调用此方法将更改此匹配器的状态如果匹配器将用于进一步匹配操作,则应首先重置</p>
     * 
     * 
     * @param  replacement
     *         The replacement string
     * @return  The string constructed by replacing the first matching
     *          subsequence by the replacement string, substituting captured
     *          subsequences as needed
     */
    public String replaceFirst(String replacement) {
        if (replacement == null)
            throw new NullPointerException("replacement");
        reset();
        if (!find())
            return text.toString();
        StringBuffer sb = new StringBuffer();
        appendReplacement(sb, replacement);
        appendTail(sb);
        return sb.toString();
    }

    /**
     * Sets the limits of this matcher's region. The region is the part of the
     * input sequence that will be searched to find a match. Invoking this
     * method resets the matcher, and then sets the region to start at the
     * index specified by the <code>start</code> parameter and end at the
     * index specified by the <code>end</code> parameter.
     *
     * <p>Depending on the transparency and anchoring being used (see
     * {@link #useTransparentBounds useTransparentBounds} and
     * {@link #useAnchoringBounds useAnchoringBounds}), certain constructs such
     * as anchors may behave differently at or around the boundaries of the
     * region.
     *
     * <p>
     *  设置此匹配器区域的限制区域是将被搜索以找到匹配的输入序列的一部分。
     * 调用此方法重置匹配器,然后将该区域设置为从由<code> start </span>指定的索引开始, code>参数,并在由<code> end </code>参数指定的索引处结束。
     * 
     * <p>根据所使用的透明度和锚定(请参阅{@link #useTransparentBounds useTransparentBounds}和{@link #useAnchoringBounds useAnchoringBounds}
     * ),某些结构(例如锚点)可能在区域边界处或周围的行为不同。
     * 
     * 
     * @param  start
     *         The index to start searching at (inclusive)
     * @param  end
     *         The index to end searching at (exclusive)
     * @throws  IndexOutOfBoundsException
     *          If start or end is less than zero, if
     *          start is greater than the length of the input sequence, if
     *          end is greater than the length of the input sequence, or if
     *          start is greater than end.
     * @return  this matcher
     * @since 1.5
     */
    public Matcher region(int start, int end) {
        if ((start < 0) || (start > getTextLength()))
            throw new IndexOutOfBoundsException("start");
        if ((end < 0) || (end > getTextLength()))
            throw new IndexOutOfBoundsException("end");
        if (start > end)
            throw new IndexOutOfBoundsException("start > end");
        reset();
        from = start;
        to = end;
        return this;
    }

    /**
     * Reports the start index of this matcher's region. The
     * searches this matcher conducts are limited to finding matches
     * within {@link #regionStart regionStart} (inclusive) and
     * {@link #regionEnd regionEnd} (exclusive).
     *
     * <p>
     *  报告此匹配器区域的开始索引此匹配器执行的搜索仅限于在{@link #regionStart regionStart}(包括)和{@link #regionEnd regionEnd}(独占)中查找匹配
     * 项。
     * 
     * 
     * @return  The starting point of this matcher's region
     * @since 1.5
     */
    public int regionStart() {
        return from;
    }

    /**
     * Reports the end index (exclusive) of this matcher's region.
     * The searches this matcher conducts are limited to finding matches
     * within {@link #regionStart regionStart} (inclusive) and
     * {@link #regionEnd regionEnd} (exclusive).
     *
     * <p>
     *  报告此匹配器区域的结束索引(独占)此匹配器执行的搜索仅限于在{@link #regionStart regionStart}(包括)和{@link #regionEnd regionEnd}(独占)中
     * 查找匹配项。
     * 
     * 
     * @return  the ending point of this matcher's region
     * @since 1.5
     */
    public int regionEnd() {
        return to;
    }

    /**
     * Queries the transparency of region bounds for this matcher.
     *
     * <p> This method returns <tt>true</tt> if this matcher uses
     * <i>transparent</i> bounds, <tt>false</tt> if it uses <i>opaque</i>
     * bounds.
     *
     * <p> See {@link #useTransparentBounds useTransparentBounds} for a
     * description of transparent and opaque bounds.
     *
     * <p> By default, a matcher uses opaque region boundaries.
     *
     * <p>
     *  查询此匹配器的区域边界的透明度
     * 
     * <p>如果此匹配器使用<i>透明</i>边界,则返回<tt> true </tt>,如果使用<i> opaque </i>
     * 
     *  <p>有关透明和不透明边界的说明,请参阅{@link #useTransparentBounds useTransparentBounds}
     * 
     *  <p>默认情况下,匹配器使用不透明区域边界
     * 
     * 
     * @return <tt>true</tt> iff this matcher is using transparent bounds,
     *         <tt>false</tt> otherwise.
     * @see java.util.regex.Matcher#useTransparentBounds(boolean)
     * @since 1.5
     */
    public boolean hasTransparentBounds() {
        return transparentBounds;
    }

    /**
     * Sets the transparency of region bounds for this matcher.
     *
     * <p> Invoking this method with an argument of <tt>true</tt> will set this
     * matcher to use <i>transparent</i> bounds. If the boolean
     * argument is <tt>false</tt>, then <i>opaque</i> bounds will be used.
     *
     * <p> Using transparent bounds, the boundaries of this
     * matcher's region are transparent to lookahead, lookbehind,
     * and boundary matching constructs. Those constructs can see beyond the
     * boundaries of the region to see if a match is appropriate.
     *
     * <p> Using opaque bounds, the boundaries of this matcher's
     * region are opaque to lookahead, lookbehind, and boundary matching
     * constructs that may try to see beyond them. Those constructs cannot
     * look past the boundaries so they will fail to match anything outside
     * of the region.
     *
     * <p> By default, a matcher uses opaque bounds.
     *
     * <p>
     *  设置此匹配器的区域边界的透明度
     * 
     *  <p>使用<tt> true </tt>参数调用此方法会将此匹配器设置为使用<i>透明</i>边界如果布尔参数为<tt> false </tt>,则<i > opaque </i> bounds
     * 
     * <p>使用透明边界,此匹配器区域的边界对于前瞻,后瞻和边界匹配结构是透明的。这些结构可以看到超出该区域的边界,以查看匹配是否合适
     * 
     *  <p>使用不透明边界,这个匹配器区域的边界对于lookahead,lookbehind和边界匹配结构是不透明的,它们可能试图超越它们。这些结构不能超过边界,所以它们将不能匹配区域外的任何东西
     * 
     *  <p>默认情况下,匹配器使用不透明边界
     * 
     * 
     * @param  b a boolean indicating whether to use opaque or transparent
     *         regions
     * @return this matcher
     * @see java.util.regex.Matcher#hasTransparentBounds
     * @since 1.5
     */
    public Matcher useTransparentBounds(boolean b) {
        transparentBounds = b;
        return this;
    }

    /**
     * Queries the anchoring of region bounds for this matcher.
     *
     * <p> This method returns <tt>true</tt> if this matcher uses
     * <i>anchoring</i> bounds, <tt>false</tt> otherwise.
     *
     * <p> See {@link #useAnchoringBounds useAnchoringBounds} for a
     * description of anchoring bounds.
     *
     * <p> By default, a matcher uses anchoring region boundaries.
     *
     * <p>
     *  查询此匹配器的区域边界的锚定
     * 
     *  <p>如果此匹配器使用<i>锚定</i>边界,<tt> false </tt>,则此方法返回<tt> true </tt>
     * 
     * <p>有关锚定界限的说明,请参阅{@link #useAnchoringBounds useAnchoringBounds}
     * 
     *  <p>默认情况下,匹配器使用锚定区域边界
     * 
     * 
     * @return <tt>true</tt> iff this matcher is using anchoring bounds,
     *         <tt>false</tt> otherwise.
     * @see java.util.regex.Matcher#useAnchoringBounds(boolean)
     * @since 1.5
     */
    public boolean hasAnchoringBounds() {
        return anchoringBounds;
    }

    /**
     * Sets the anchoring of region bounds for this matcher.
     *
     * <p> Invoking this method with an argument of <tt>true</tt> will set this
     * matcher to use <i>anchoring</i> bounds. If the boolean
     * argument is <tt>false</tt>, then <i>non-anchoring</i> bounds will be
     * used.
     *
     * <p> Using anchoring bounds, the boundaries of this
     * matcher's region match anchors such as ^ and $.
     *
     * <p> Without anchoring bounds, the boundaries of this
     * matcher's region will not match anchors such as ^ and $.
     *
     * <p> By default, a matcher uses anchoring region boundaries.
     *
     * <p>
     *  设置此匹配器的区域边界的锚定
     * 
     *  <p>使用<tt> true </tt>参数调用此方法会将此匹配器设置为使用<i> anchor </i> bounds如果布尔参数为<tt> false </tt>,则<i >非锚定</i>边界
     * 
     *  <p>使用锚定边界,此匹配器区域的边界匹配锚如^和$
     * 
     *  <p>没有锚定界限,此匹配器区域的边界将不匹配锚如^和$
     * 
     *  <p>默认情况下,匹配器使用锚定区域边界
     * 
     * 
     * @param  b a boolean indicating whether or not to use anchoring bounds.
     * @return this matcher
     * @see java.util.regex.Matcher#hasAnchoringBounds
     * @since 1.5
     */
    public Matcher useAnchoringBounds(boolean b) {
        anchoringBounds = b;
        return this;
    }

    /**
     * <p>Returns the string representation of this matcher. The
     * string representation of a <code>Matcher</code> contains information
     * that may be useful for debugging. The exact format is unspecified.
     *
     * <p>
     * <p>返回此匹配器的字符串表示形式<code> Matcher </code>的字符串表示形式包含可能对调试有用的信息准确的格式未指定
     * 
     * 
     * @return  The string representation of this matcher
     * @since 1.5
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("java.util.regex.Matcher");
        sb.append("[pattern=" + pattern());
        sb.append(" region=");
        sb.append(regionStart() + "," + regionEnd());
        sb.append(" lastmatch=");
        if ((first >= 0) && (group() != null)) {
            sb.append(group());
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * <p>Returns true if the end of input was hit by the search engine in
     * the last match operation performed by this matcher.
     *
     * <p>When this method returns true, then it is possible that more input
     * would have changed the result of the last search.
     *
     * <p>
     *  <p>如果在此匹配器执行的最后一次匹配操作中搜索引擎触发输入结束,则返回true
     * 
     *  <p>当此方法返回true时,有可能更多的输入将更改上次搜索的结果
     * 
     * 
     * @return  true iff the end of input was hit in the last match; false
     *          otherwise
     * @since 1.5
     */
    public boolean hitEnd() {
        return hitEnd;
    }

    /**
     * <p>Returns true if more input could change a positive match into a
     * negative one.
     *
     * <p>If this method returns true, and a match was found, then more
     * input could cause the match to be lost. If this method returns false
     * and a match was found, then more input might change the match but the
     * match won't be lost. If a match was not found, then requireEnd has no
     * meaning.
     *
     * <p>
     *  <p>如果更多输入可能将正匹配更改为负匹配,则返回true
     * 
     * <p>如果此方法返回true,并且找到匹配,则更多输入可能导致匹配丢失如果此方法返回false并且找到匹配,则更多输入可能会更改匹配,但匹配不会lost如果没有找到匹配,那么requireEnd没有意义
     * 。
     * 
     * 
     * @return  true iff more input could change a positive match into a
     *          negative one.
     * @since 1.5
     */
    public boolean requireEnd() {
        return requireEnd;
    }

    /**
     * Initiates a search to find a Pattern within the given bounds.
     * The groups are filled with default values and the match of the root
     * of the state machine is called. The state machine will hold the state
     * of the match as it proceeds in this matcher.
     *
     * Matcher.from is not set here, because it is the "hard" boundary
     * of the start of the search which anchors will set to. The from param
     * is the "soft" boundary of the start of the search, meaning that the
     * regex tries to match at that index but ^ won't match there. Subsequent
     * calls to the search methods start at a new "soft" boundary which is
     * the end of the previous match.
     * <p>
     *  启动搜索以在给定的边界内查找模式这些组用默认值填充,并且状态机的根的匹配被称为状态机将保持匹配的状态,因为它在该匹配器中继续进行
     * 
     * Matcherfrom在这里没有设置,因为它是搜索开始的"硬"边界,锚点将设置为从。
     * param是搜索开始的"软"边界,这意味着正则表达式尝试匹配索引,但^将不匹配那里对搜索方法的后续调用从新的"软"边界开始,这是上一次匹配的结束。
     * 
     */
    boolean search(int from) {
        this.hitEnd = false;
        this.requireEnd = false;
        from        = from < 0 ? 0 : from;
        this.first  = from;
        this.oldLast = oldLast < 0 ? from : oldLast;
        for (int i = 0; i < groups.length; i++)
            groups[i] = -1;
        acceptMode = NOANCHOR;
        boolean result = parentPattern.root.match(this, from, text);
        if (!result)
            this.first = -1;
        this.oldLast = this.last;
        return result;
    }

    /**
     * Initiates a search for an anchored match to a Pattern within the given
     * bounds. The groups are filled with default values and the match of the
     * root of the state machine is called. The state machine will hold the
     * state of the match as it proceeds in this matcher.
     * <p>
     *  在给定范围内启动对模式的锚定匹配的搜索使用默认值填充组,并且状态机的根的匹配被称为状态机将保持匹配的状态,如在该匹配器中继续
     * 
     */
    boolean match(int from, int anchor) {
        this.hitEnd = false;
        this.requireEnd = false;
        from        = from < 0 ? 0 : from;
        this.first  = from;
        this.oldLast = oldLast < 0 ? from : oldLast;
        for (int i = 0; i < groups.length; i++)
            groups[i] = -1;
        acceptMode = anchor;
        boolean result = parentPattern.matchRoot.match(this, from, text);
        if (!result)
            this.first = -1;
        this.oldLast = this.last;
        return result;
    }

    /**
     * Returns the end index of the text.
     *
     * <p>
     *  返回文本的结束索引
     * 
     * 
     * @return the index after the last character in the text
     */
    int getTextLength() {
        return text.length();
    }

    /**
     * Generates a String from this Matcher's input in the specified range.
     *
     * <p>
     *  从指定范围内的此匹配器输入生成一个字符串
     * 
     * 
     * @param  beginIndex   the beginning index, inclusive
     * @param  endIndex     the ending index, exclusive
     * @return A String generated from this Matcher's input
     */
    CharSequence getSubSequence(int beginIndex, int endIndex) {
        return text.subSequence(beginIndex, endIndex);
    }

    /**
     * Returns this Matcher's input character at index i.
     *
     * <p>
     *  返回索引i处的该匹配器的输入字符
     * 
     * 
     * @return A char from the specified index
     */
    char charAt(int i) {
        return text.charAt(i);
    }

    /**
     * Returns the group index of the matched capturing group.
     *
     * <p>
     * 返回匹配捕获组的组索引
     * 
     * @return the index of the named-capturing group
     */
    int getMatchedGroupIndex(String name) {
        Objects.requireNonNull(name, "Group name");
        if (first < 0)
            throw new IllegalStateException("No match found");
        if (!parentPattern.namedGroups().containsKey(name))
            throw new IllegalArgumentException("No group with name <" + name + ">");
        return parentPattern.namedGroups().get(name);
    }
}
