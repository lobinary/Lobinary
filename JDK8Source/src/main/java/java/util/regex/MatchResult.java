/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * The result of a match operation.
 *
 * <p>This interface contains query methods used to determine the
 * results of a match against a regular expression. The match boundaries,
 * groups and group boundaries can be seen but not modified through
 * a <code>MatchResult</code>.
 *
 * <p>
 *  匹配操作的结果。
 * 
 *  <p>此接口包含用于确定与正则表达式匹配的结果的查询方法。可以看到匹配边界,组和组边界,但不能通过<code> MatchResult </code>修改。
 * 
 * 
 * @author  Michael McCloskey
 * @see Matcher
 * @since 1.5
 */
public interface MatchResult {

    /**
     * Returns the start index of the match.
     *
     * <p>
     *  返回匹配的开始索引。
     * 
     * 
     * @return  The index of the first character matched
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     */
    public int start();

    /**
     * Returns the start index of the subsequence captured by the given group
     * during this match.
     *
     * <p> <a href="Pattern.html#cg">Capturing groups</a> are indexed from left
     * to right, starting at one.  Group zero denotes the entire pattern, so
     * the expression <i>m.</i><tt>start(0)</tt> is equivalent to
     * <i>m.</i><tt>start()</tt>.  </p>
     *
     * <p>
     *  返回此匹配期间给定组捕获的子序列的开始索引。
     * 
     *  <p> <a href="Pattern.html#cg">抓取组</a>从左到右编入索引,从一个开始。组0表示整个模式,因此表达式m </i> <tt> start(0)</tt>等效于<m>。
     * </i> <tt> start() tt>。 </p>。
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
    public int start(int group);

    /**
     * Returns the offset after the last character matched.
     *
     * <p>
     *  返回在匹配的最后一个字符之后的偏移量。
     * 
     * 
     * @return  The offset after the last character matched
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     */
    public int end();

    /**
     * Returns the offset after the last character of the subsequence
     * captured by the given group during this match.
     *
     * <p> <a href="Pattern.html#cg">Capturing groups</a> are indexed from left
     * to right, starting at one.  Group zero denotes the entire pattern, so
     * the expression <i>m.</i><tt>end(0)</tt> is equivalent to
     * <i>m.</i><tt>end()</tt>.  </p>
     *
     * <p>
     *  返回此匹配期间由给定组捕获的子序列的最后一个字符之后的偏移量。
     * 
     *  <p> <a href="Pattern.html#cg">抓取组</a>从左到右编入索引,从一个开始。
     * 组0表示整个模式,所以表达式m </i> <tt> end(0)</tt>等效于<m> </i> <tt> end tt>。 </p>。
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
    public int end(int group);

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
     *  返回与上一个匹配匹配的输入子序列。
     * 
     *  <p>对于具有输入序列<i> s </i>的匹配器m,表达式<i> m </i> <tt> group()</tt>和<i > s。</i> <tt> substring(</tt> <i> m。
     * </i> <tt> start(),</tt> end())</tt>是等价的。 </p>。
     * 
     * <p>请注意,某些模式(例如<tt> a * </tt>)与空字符串相匹配。当模式成功匹配输入中的空字符串时,此方法将返回空字符串。 </p>
     * 
     * 
     * @return The (possibly empty) subsequence matched by the previous match,
     *         in string form
     *
     * @throws  IllegalStateException
     *          If no match has yet been attempted,
     *          or if the previous match operation failed
     */
    public String group();

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
     *  返回在先前的匹配操作期间由给定组捕获的输入子序列。
     * 
     *  对于匹配器m,输入序列<i> s和组索引</i>,表达式<m> </i> tt> group(</tt> <i> g </i> <tt>)</tt>和<i> s。
     * </i> <tt> substring </tt> <i> i> <tt> start(</tt> <i> g </i> <tt>),</tt>&nbsp; m。
     * </i> <tt> end > g </i> <tt>))</tt>是等价的。 </p>。
     * 
     *  <p> <a href="Pattern.html#cg">抓取组</a>从左到右编入索引,从一个开始。
     * 组零表示整个模式,因此表达式<tt> m.group(0)</tt>等效于<tt> m.group()</tt>。
     * </p>
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
    public String group(int group);

    /**
     * Returns the number of capturing groups in this match result's pattern.
     *
     * <p> Group zero denotes the entire pattern by convention. It is not
     * included in this count.
     *
     * <p> Any non-negative integer smaller than or equal to the value
     * returned by this method is guaranteed to be a valid group index for
     * this matcher.  </p>
     *
     * <p>
     *  <p>如果匹配成功,但指定的组未能匹配输入序列的任何部分,则会返回<tt> null </tt>。请注意,一些组,例如<tt>(a *)</tt>匹配空字符串。
     * 当这样的组成功匹配输入中的空字符串时,此方法将返回空字符串。 </p>。
     * 
     * 
     * @return The number of capturing groups in this matcher's pattern
     */
    public int groupCount();

}
