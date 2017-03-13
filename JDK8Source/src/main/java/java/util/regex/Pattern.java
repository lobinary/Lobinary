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

import java.text.Normalizer;
import java.util.Locale;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * A compiled representation of a regular expression.
 *
 * <p> A regular expression, specified as a string, must first be compiled into
 * an instance of this class.  The resulting pattern can then be used to create
 * a {@link Matcher} object that can match arbitrary {@linkplain
 * java.lang.CharSequence character sequences} against the regular
 * expression.  All of the state involved in performing a match resides in the
 * matcher, so many matchers can share the same pattern.
 *
 * <p> A typical invocation sequence is thus
 *
 * <blockquote><pre>
 * Pattern p = Pattern.{@link #compile compile}("a*b");
 * Matcher m = p.{@link #matcher matcher}("aaaaab");
 * boolean b = m.{@link Matcher#matches matches}();</pre></blockquote>
 *
 * <p> A {@link #matches matches} method is defined by this class as a
 * convenience for when a regular expression is used just once.  This method
 * compiles an expression and matches an input sequence against it in a single
 * invocation.  The statement
 *
 * <blockquote><pre>
 * boolean b = Pattern.matches("a*b", "aaaaab");</pre></blockquote>
 *
 * is equivalent to the three statements above, though for repeated matches it
 * is less efficient since it does not allow the compiled pattern to be reused.
 *
 * <p> Instances of this class are immutable and are safe for use by multiple
 * concurrent threads.  Instances of the {@link Matcher} class are not safe for
 * such use.
 *
 *
 * <h3><a name="sum">Summary of regular-expression constructs</a></h3>
 *
 * <table border="0" cellpadding="1" cellspacing="0"
 *  summary="Regular expression constructs, and what they match">
 *
 * <tr align="left">
 * <th align="left" id="construct">Construct</th>
 * <th align="left" id="matches">Matches</th>
 * </tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="characters">Characters</th></tr>
 *
 * <tr><td valign="top" headers="construct characters"><i>x</i></td>
 *     <td headers="matches">The character <i>x</i></td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\\</tt></td>
 *     <td headers="matches">The backslash character</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\0</tt><i>n</i></td>
 *     <td headers="matches">The character with octal value <tt>0</tt><i>n</i>
 *         (0&nbsp;<tt>&lt;=</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;7)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\0</tt><i>nn</i></td>
 *     <td headers="matches">The character with octal value <tt>0</tt><i>nn</i>
 *         (0&nbsp;<tt>&lt;=</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;7)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\0</tt><i>mnn</i></td>
 *     <td headers="matches">The character with octal value <tt>0</tt><i>mnn</i>
 *         (0&nbsp;<tt>&lt;=</tt>&nbsp;<i>m</i>&nbsp;<tt>&lt;=</tt>&nbsp;3,
 *         0&nbsp;<tt>&lt;=</tt>&nbsp;<i>n</i>&nbsp;<tt>&lt;=</tt>&nbsp;7)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\x</tt><i>hh</i></td>
 *     <td headers="matches">The character with hexadecimal&nbsp;value&nbsp;<tt>0x</tt><i>hh</i></td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>&#92;u</tt><i>hhhh</i></td>
 *     <td headers="matches">The character with hexadecimal&nbsp;value&nbsp;<tt>0x</tt><i>hhhh</i></td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>&#92;x</tt><i>{h...h}</i></td>
 *     <td headers="matches">The character with hexadecimal&nbsp;value&nbsp;<tt>0x</tt><i>h...h</i>
 *         ({@link java.lang.Character#MIN_CODE_POINT Character.MIN_CODE_POINT}
 *         &nbsp;&lt;=&nbsp;<tt>0x</tt><i>h...h</i>&nbsp;&lt;=&nbsp;
 *          {@link java.lang.Character#MAX_CODE_POINT Character.MAX_CODE_POINT})</td></tr>
 * <tr><td valign="top" headers="matches"><tt>\t</tt></td>
 *     <td headers="matches">The tab character (<tt>'&#92;u0009'</tt>)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\n</tt></td>
 *     <td headers="matches">The newline (line feed) character (<tt>'&#92;u000A'</tt>)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\r</tt></td>
 *     <td headers="matches">The carriage-return character (<tt>'&#92;u000D'</tt>)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\f</tt></td>
 *     <td headers="matches">The form-feed character (<tt>'&#92;u000C'</tt>)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\a</tt></td>
 *     <td headers="matches">The alert (bell) character (<tt>'&#92;u0007'</tt>)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\e</tt></td>
 *     <td headers="matches">The escape character (<tt>'&#92;u001B'</tt>)</td></tr>
 * <tr><td valign="top" headers="construct characters"><tt>\c</tt><i>x</i></td>
 *     <td headers="matches">The control character corresponding to <i>x</i></td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="classes">Character classes</th></tr>
 *
 * <tr><td valign="top" headers="construct classes">{@code [abc]}</td>
 *     <td headers="matches">{@code a}, {@code b}, or {@code c} (simple class)</td></tr>
 * <tr><td valign="top" headers="construct classes">{@code [^abc]}</td>
 *     <td headers="matches">Any character except {@code a}, {@code b}, or {@code c} (negation)</td></tr>
 * <tr><td valign="top" headers="construct classes">{@code [a-zA-Z]}</td>
 *     <td headers="matches">{@code a} through {@code z}
 *         or {@code A} through {@code Z}, inclusive (range)</td></tr>
 * <tr><td valign="top" headers="construct classes">{@code [a-d[m-p]]}</td>
 *     <td headers="matches">{@code a} through {@code d},
 *      or {@code m} through {@code p}: {@code [a-dm-p]} (union)</td></tr>
 * <tr><td valign="top" headers="construct classes">{@code [a-z&&[def]]}</td>
 *     <td headers="matches">{@code d}, {@code e}, or {@code f} (intersection)</tr>
 * <tr><td valign="top" headers="construct classes">{@code [a-z&&[^bc]]}</td>
 *     <td headers="matches">{@code a} through {@code z},
 *         except for {@code b} and {@code c}: {@code [ad-z]} (subtraction)</td></tr>
 * <tr><td valign="top" headers="construct classes">{@code [a-z&&[^m-p]]}</td>
 *     <td headers="matches">{@code a} through {@code z},
 *          and not {@code m} through {@code p}: {@code [a-lq-z]}(subtraction)</td></tr>
 * <tr><th>&nbsp;</th></tr>
 *
 * <tr align="left"><th colspan="2" id="predef">Predefined character classes</th></tr>
 *
 * <tr><td valign="top" headers="construct predef"><tt>.</tt></td>
 *     <td headers="matches">Any character (may or may not match <a href="#lt">line terminators</a>)</td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\d</tt></td>
 *     <td headers="matches">A digit: <tt>[0-9]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\D</tt></td>
 *     <td headers="matches">A non-digit: <tt>[^0-9]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\h</tt></td>
 *     <td headers="matches">A horizontal whitespace character:
 *     <tt>[ \t\xA0&#92;u1680&#92;u180e&#92;u2000-&#92;u200a&#92;u202f&#92;u205f&#92;u3000]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\H</tt></td>
 *     <td headers="matches">A non-horizontal whitespace character: <tt>[^\h]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\s</tt></td>
 *     <td headers="matches">A whitespace character: <tt>[ \t\n\x0B\f\r]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\S</tt></td>
 *     <td headers="matches">A non-whitespace character: <tt>[^\s]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\v</tt></td>
 *     <td headers="matches">A vertical whitespace character: <tt>[\n\x0B\f\r\x85&#92;u2028&#92;u2029]</tt>
 *     </td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\V</tt></td>
 *     <td headers="matches">A non-vertical whitespace character: <tt>[^\v]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\w</tt></td>
 *     <td headers="matches">A word character: <tt>[a-zA-Z_0-9]</tt></td></tr>
 * <tr><td valign="top" headers="construct predef"><tt>\W</tt></td>
 *     <td headers="matches">A non-word character: <tt>[^\w]</tt></td></tr>
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="posix"><b>POSIX character classes (US-ASCII only)</b></th></tr>
 *
 * <tr><td valign="top" headers="construct posix">{@code \p{Lower}}</td>
 *     <td headers="matches">A lower-case alphabetic character: {@code [a-z]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Upper}}</td>
 *     <td headers="matches">An upper-case alphabetic character:{@code [A-Z]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{ASCII}}</td>
 *     <td headers="matches">All ASCII:{@code [\x00-\x7F]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Alpha}}</td>
 *     <td headers="matches">An alphabetic character:{@code [\p{Lower}\p{Upper}]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Digit}}</td>
 *     <td headers="matches">A decimal digit: {@code [0-9]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Alnum}}</td>
 *     <td headers="matches">An alphanumeric character:{@code [\p{Alpha}\p{Digit}]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Punct}}</td>
 *     <td headers="matches">Punctuation: One of {@code !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~}</td></tr>
 *     <!-- {@code [\!"#\$%&'\(\)\*\+,\-\./:;\<=\>\?@\[\\\]\^_`\{\|\}~]}
 *          {@code [\X21-\X2F\X31-\X40\X5B-\X60\X7B-\X7E]} -->
 * <tr><td valign="top" headers="construct posix">{@code \p{Graph}}</td>
 *     <td headers="matches">A visible character: {@code [\p{Alnum}\p{Punct}]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Print}}</td>
 *     <td headers="matches">A printable character: {@code [\p{Graph}\x20]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Blank}}</td>
 *     <td headers="matches">A space or a tab: {@code [ \t]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Cntrl}}</td>
 *     <td headers="matches">A control character: {@code [\x00-\x1F\x7F]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{XDigit}}</td>
 *     <td headers="matches">A hexadecimal digit: {@code [0-9a-fA-F]}</td></tr>
 * <tr><td valign="top" headers="construct posix">{@code \p{Space}}</td>
 *     <td headers="matches">A whitespace character: {@code [ \t\n\x0B\f\r]}</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2">java.lang.Character classes (simple <a href="#jcc">java character type</a>)</th></tr>
 *
 * <tr><td valign="top"><tt>\p{javaLowerCase}</tt></td>
 *     <td>Equivalent to java.lang.Character.isLowerCase()</td></tr>
 * <tr><td valign="top"><tt>\p{javaUpperCase}</tt></td>
 *     <td>Equivalent to java.lang.Character.isUpperCase()</td></tr>
 * <tr><td valign="top"><tt>\p{javaWhitespace}</tt></td>
 *     <td>Equivalent to java.lang.Character.isWhitespace()</td></tr>
 * <tr><td valign="top"><tt>\p{javaMirrored}</tt></td>
 *     <td>Equivalent to java.lang.Character.isMirrored()</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="unicode">Classes for Unicode scripts, blocks, categories and binary properties</th></tr>
 * <tr><td valign="top" headers="construct unicode">{@code \p{IsLatin}}</td>
 *     <td headers="matches">A Latin&nbsp;script character (<a href="#usc">script</a>)</td></tr>
 * <tr><td valign="top" headers="construct unicode">{@code \p{InGreek}}</td>
 *     <td headers="matches">A character in the Greek&nbsp;block (<a href="#ubc">block</a>)</td></tr>
 * <tr><td valign="top" headers="construct unicode">{@code \p{Lu}}</td>
 *     <td headers="matches">An uppercase letter (<a href="#ucc">category</a>)</td></tr>
 * <tr><td valign="top" headers="construct unicode">{@code \p{IsAlphabetic}}</td>
 *     <td headers="matches">An alphabetic character (<a href="#ubpc">binary property</a>)</td></tr>
 * <tr><td valign="top" headers="construct unicode">{@code \p{Sc}}</td>
 *     <td headers="matches">A currency symbol</td></tr>
 * <tr><td valign="top" headers="construct unicode">{@code \P{InGreek}}</td>
 *     <td headers="matches">Any character except one in the Greek block (negation)</td></tr>
 * <tr><td valign="top" headers="construct unicode">{@code [\p{L}&&[^\p{Lu}]]}</td>
 *     <td headers="matches">Any letter except an uppercase letter (subtraction)</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="bounds">Boundary matchers</th></tr>
 *
 * <tr><td valign="top" headers="construct bounds"><tt>^</tt></td>
 *     <td headers="matches">The beginning of a line</td></tr>
 * <tr><td valign="top" headers="construct bounds"><tt>$</tt></td>
 *     <td headers="matches">The end of a line</td></tr>
 * <tr><td valign="top" headers="construct bounds"><tt>\b</tt></td>
 *     <td headers="matches">A word boundary</td></tr>
 * <tr><td valign="top" headers="construct bounds"><tt>\B</tt></td>
 *     <td headers="matches">A non-word boundary</td></tr>
 * <tr><td valign="top" headers="construct bounds"><tt>\A</tt></td>
 *     <td headers="matches">The beginning of the input</td></tr>
 * <tr><td valign="top" headers="construct bounds"><tt>\G</tt></td>
 *     <td headers="matches">The end of the previous match</td></tr>
 * <tr><td valign="top" headers="construct bounds"><tt>\Z</tt></td>
 *     <td headers="matches">The end of the input but for the final
 *         <a href="#lt">terminator</a>, if&nbsp;any</td></tr>
 * <tr><td valign="top" headers="construct bounds"><tt>\z</tt></td>
 *     <td headers="matches">The end of the input</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="lineending">Linebreak matcher</th></tr>
 * <tr><td valign="top" headers="construct lineending"><tt>\R</tt></td>
 *     <td headers="matches">Any Unicode linebreak sequence, is equivalent to
 *     <tt>&#92;u000D&#92;u000A|[&#92;u000A&#92;u000B&#92;u000C&#92;u000D&#92;u0085&#92;u2028&#92;u2029]
 *     </tt></td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="greedy">Greedy quantifiers</th></tr>
 *
 * <tr><td valign="top" headers="construct greedy"><i>X</i><tt>?</tt></td>
 *     <td headers="matches"><i>X</i>, once or not at all</td></tr>
 * <tr><td valign="top" headers="construct greedy"><i>X</i><tt>*</tt></td>
 *     <td headers="matches"><i>X</i>, zero or more times</td></tr>
 * <tr><td valign="top" headers="construct greedy"><i>X</i><tt>+</tt></td>
 *     <td headers="matches"><i>X</i>, one or more times</td></tr>
 * <tr><td valign="top" headers="construct greedy"><i>X</i><tt>{</tt><i>n</i><tt>}</tt></td>
 *     <td headers="matches"><i>X</i>, exactly <i>n</i> times</td></tr>
 * <tr><td valign="top" headers="construct greedy"><i>X</i><tt>{</tt><i>n</i><tt>,}</tt></td>
 *     <td headers="matches"><i>X</i>, at least <i>n</i> times</td></tr>
 * <tr><td valign="top" headers="construct greedy"><i>X</i><tt>{</tt><i>n</i><tt>,</tt><i>m</i><tt>}</tt></td>
 *     <td headers="matches"><i>X</i>, at least <i>n</i> but not more than <i>m</i> times</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="reluc">Reluctant quantifiers</th></tr>
 *
 * <tr><td valign="top" headers="construct reluc"><i>X</i><tt>??</tt></td>
 *     <td headers="matches"><i>X</i>, once or not at all</td></tr>
 * <tr><td valign="top" headers="construct reluc"><i>X</i><tt>*?</tt></td>
 *     <td headers="matches"><i>X</i>, zero or more times</td></tr>
 * <tr><td valign="top" headers="construct reluc"><i>X</i><tt>+?</tt></td>
 *     <td headers="matches"><i>X</i>, one or more times</td></tr>
 * <tr><td valign="top" headers="construct reluc"><i>X</i><tt>{</tt><i>n</i><tt>}?</tt></td>
 *     <td headers="matches"><i>X</i>, exactly <i>n</i> times</td></tr>
 * <tr><td valign="top" headers="construct reluc"><i>X</i><tt>{</tt><i>n</i><tt>,}?</tt></td>
 *     <td headers="matches"><i>X</i>, at least <i>n</i> times</td></tr>
 * <tr><td valign="top" headers="construct reluc"><i>X</i><tt>{</tt><i>n</i><tt>,</tt><i>m</i><tt>}?</tt></td>
 *     <td headers="matches"><i>X</i>, at least <i>n</i> but not more than <i>m</i> times</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="poss">Possessive quantifiers</th></tr>
 *
 * <tr><td valign="top" headers="construct poss"><i>X</i><tt>?+</tt></td>
 *     <td headers="matches"><i>X</i>, once or not at all</td></tr>
 * <tr><td valign="top" headers="construct poss"><i>X</i><tt>*+</tt></td>
 *     <td headers="matches"><i>X</i>, zero or more times</td></tr>
 * <tr><td valign="top" headers="construct poss"><i>X</i><tt>++</tt></td>
 *     <td headers="matches"><i>X</i>, one or more times</td></tr>
 * <tr><td valign="top" headers="construct poss"><i>X</i><tt>{</tt><i>n</i><tt>}+</tt></td>
 *     <td headers="matches"><i>X</i>, exactly <i>n</i> times</td></tr>
 * <tr><td valign="top" headers="construct poss"><i>X</i><tt>{</tt><i>n</i><tt>,}+</tt></td>
 *     <td headers="matches"><i>X</i>, at least <i>n</i> times</td></tr>
 * <tr><td valign="top" headers="construct poss"><i>X</i><tt>{</tt><i>n</i><tt>,</tt><i>m</i><tt>}+</tt></td>
 *     <td headers="matches"><i>X</i>, at least <i>n</i> but not more than <i>m</i> times</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="logical">Logical operators</th></tr>
 *
 * <tr><td valign="top" headers="construct logical"><i>XY</i></td>
 *     <td headers="matches"><i>X</i> followed by <i>Y</i></td></tr>
 * <tr><td valign="top" headers="construct logical"><i>X</i><tt>|</tt><i>Y</i></td>
 *     <td headers="matches">Either <i>X</i> or <i>Y</i></td></tr>
 * <tr><td valign="top" headers="construct logical"><tt>(</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches">X, as a <a href="#cg">capturing group</a></td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="backref">Back references</th></tr>
 *
 * <tr><td valign="bottom" headers="construct backref"><tt>\</tt><i>n</i></td>
 *     <td valign="bottom" headers="matches">Whatever the <i>n</i><sup>th</sup>
 *     <a href="#cg">capturing group</a> matched</td></tr>
 *
 * <tr><td valign="bottom" headers="construct backref"><tt>\</tt><i>k</i>&lt;<i>name</i>&gt;</td>
 *     <td valign="bottom" headers="matches">Whatever the
 *     <a href="#groupname">named-capturing group</a> "name" matched</td></tr>
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="quot">Quotation</th></tr>
 *
 * <tr><td valign="top" headers="construct quot"><tt>\</tt></td>
 *     <td headers="matches">Nothing, but quotes the following character</td></tr>
 * <tr><td valign="top" headers="construct quot"><tt>\Q</tt></td>
 *     <td headers="matches">Nothing, but quotes all characters until <tt>\E</tt></td></tr>
 * <tr><td valign="top" headers="construct quot"><tt>\E</tt></td>
 *     <td headers="matches">Nothing, but ends quoting started by <tt>\Q</tt></td></tr>
 *     <!-- Metachars: !$()*+.<>?[\]^{|} -->
 *
 * <tr><th>&nbsp;</th></tr>
 * <tr align="left"><th colspan="2" id="special">Special constructs (named-capturing and non-capturing)</th></tr>
 *
 * <tr><td valign="top" headers="construct special"><tt>(?&lt;<a href="#groupname">name</a>&gt;</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches"><i>X</i>, as a named-capturing group</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?:</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches"><i>X</i>, as a non-capturing group</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?idmsuxU-idmsuxU)&nbsp;</tt></td>
 *     <td headers="matches">Nothing, but turns match flags <a href="#CASE_INSENSITIVE">i</a>
 * <a href="#UNIX_LINES">d</a> <a href="#MULTILINE">m</a> <a href="#DOTALL">s</a>
 * <a href="#UNICODE_CASE">u</a> <a href="#COMMENTS">x</a> <a href="#UNICODE_CHARACTER_CLASS">U</a>
 * on - off</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?idmsux-idmsux:</tt><i>X</i><tt>)</tt>&nbsp;&nbsp;</td>
 *     <td headers="matches"><i>X</i>, as a <a href="#cg">non-capturing group</a> with the
 *         given flags <a href="#CASE_INSENSITIVE">i</a> <a href="#UNIX_LINES">d</a>
 * <a href="#MULTILINE">m</a> <a href="#DOTALL">s</a> <a href="#UNICODE_CASE">u</a >
 * <a href="#COMMENTS">x</a> on - off</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?=</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches"><i>X</i>, via zero-width positive lookahead</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?!</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches"><i>X</i>, via zero-width negative lookahead</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?&lt;=</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches"><i>X</i>, via zero-width positive lookbehind</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?&lt;!</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches"><i>X</i>, via zero-width negative lookbehind</td></tr>
 * <tr><td valign="top" headers="construct special"><tt>(?&gt;</tt><i>X</i><tt>)</tt></td>
 *     <td headers="matches"><i>X</i>, as an independent, non-capturing group</td></tr>
 *
 * </table>
 *
 * <hr>
 *
 *
 * <h3><a name="bs">Backslashes, escapes, and quoting</a></h3>
 *
 * <p> The backslash character (<tt>'\'</tt>) serves to introduce escaped
 * constructs, as defined in the table above, as well as to quote characters
 * that otherwise would be interpreted as unescaped constructs.  Thus the
 * expression <tt>\\</tt> matches a single backslash and <tt>\{</tt> matches a
 * left brace.
 *
 * <p> It is an error to use a backslash prior to any alphabetic character that
 * does not denote an escaped construct; these are reserved for future
 * extensions to the regular-expression language.  A backslash may be used
 * prior to a non-alphabetic character regardless of whether that character is
 * part of an unescaped construct.
 *
 * <p> Backslashes within string literals in Java source code are interpreted
 * as required by
 * <cite>The Java&trade; Language Specification</cite>
 * as either Unicode escapes (section 3.3) or other character escapes (section 3.10.6)
 * It is therefore necessary to double backslashes in string
 * literals that represent regular expressions to protect them from
 * interpretation by the Java bytecode compiler.  The string literal
 * <tt>"&#92;b"</tt>, for example, matches a single backspace character when
 * interpreted as a regular expression, while <tt>"&#92;&#92;b"</tt> matches a
 * word boundary.  The string literal <tt>"&#92;(hello&#92;)"</tt> is illegal
 * and leads to a compile-time error; in order to match the string
 * <tt>(hello)</tt> the string literal <tt>"&#92;&#92;(hello&#92;&#92;)"</tt>
 * must be used.
 *
 * <h3><a name="cc">Character Classes</a></h3>
 *
 *    <p> Character classes may appear within other character classes, and
 *    may be composed by the union operator (implicit) and the intersection
 *    operator (<tt>&amp;&amp;</tt>).
 *    The union operator denotes a class that contains every character that is
 *    in at least one of its operand classes.  The intersection operator
 *    denotes a class that contains every character that is in both of its
 *    operand classes.
 *
 *    <p> The precedence of character-class operators is as follows, from
 *    highest to lowest:
 *
 *    <blockquote><table border="0" cellpadding="1" cellspacing="0"
 *                 summary="Precedence of character class operators.">
 *      <tr><th>1&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *        <td>Literal escape&nbsp;&nbsp;&nbsp;&nbsp;</td>
 *        <td><tt>\x</tt></td></tr>
 *     <tr><th>2&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *        <td>Grouping</td>
 *        <td><tt>[...]</tt></td></tr>
 *     <tr><th>3&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *        <td>Range</td>
 *        <td><tt>a-z</tt></td></tr>
 *      <tr><th>4&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *        <td>Union</td>
 *        <td><tt>[a-e][i-u]</tt></td></tr>
 *      <tr><th>5&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *        <td>Intersection</td>
 *        <td>{@code [a-z&&[aeiou]]}</td></tr>
 *    </table></blockquote>
 *
 *    <p> Note that a different set of metacharacters are in effect inside
 *    a character class than outside a character class. For instance, the
 *    regular expression <tt>.</tt> loses its special meaning inside a
 *    character class, while the expression <tt>-</tt> becomes a range
 *    forming metacharacter.
 *
 * <h3><a name="lt">Line terminators</a></h3>
 *
 * <p> A <i>line terminator</i> is a one- or two-character sequence that marks
 * the end of a line of the input character sequence.  The following are
 * recognized as line terminators:
 *
 * <ul>
 *
 *   <li> A newline (line feed) character&nbsp;(<tt>'\n'</tt>),
 *
 *   <li> A carriage-return character followed immediately by a newline
 *   character&nbsp;(<tt>"\r\n"</tt>),
 *
 *   <li> A standalone carriage-return character&nbsp;(<tt>'\r'</tt>),
 *
 *   <li> A next-line character&nbsp;(<tt>'&#92;u0085'</tt>),
 *
 *   <li> A line-separator character&nbsp;(<tt>'&#92;u2028'</tt>), or
 *
 *   <li> A paragraph-separator character&nbsp;(<tt>'&#92;u2029</tt>).
 *
 * </ul>
 * <p>If {@link #UNIX_LINES} mode is activated, then the only line terminators
 * recognized are newline characters.
 *
 * <p> The regular expression <tt>.</tt> matches any character except a line
 * terminator unless the {@link #DOTALL} flag is specified.
 *
 * <p> By default, the regular expressions <tt>^</tt> and <tt>$</tt> ignore
 * line terminators and only match at the beginning and the end, respectively,
 * of the entire input sequence. If {@link #MULTILINE} mode is activated then
 * <tt>^</tt> matches at the beginning of input and after any line terminator
 * except at the end of input. When in {@link #MULTILINE} mode <tt>$</tt>
 * matches just before a line terminator or the end of the input sequence.
 *
 * <h3><a name="cg">Groups and capturing</a></h3>
 *
 * <h4><a name="gnumber">Group number</a></h4>
 * <p> Capturing groups are numbered by counting their opening parentheses from
 * left to right.  In the expression <tt>((A)(B(C)))</tt>, for example, there
 * are four such groups: </p>
 *
 * <blockquote><table cellpadding=1 cellspacing=0 summary="Capturing group numberings">
 * <tr><th>1&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *     <td><tt>((A)(B(C)))</tt></td></tr>
 * <tr><th>2&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *     <td><tt>(A)</tt></td></tr>
 * <tr><th>3&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *     <td><tt>(B(C))</tt></td></tr>
 * <tr><th>4&nbsp;&nbsp;&nbsp;&nbsp;</th>
 *     <td><tt>(C)</tt></td></tr>
 * </table></blockquote>
 *
 * <p> Group zero always stands for the entire expression.
 *
 * <p> Capturing groups are so named because, during a match, each subsequence
 * of the input sequence that matches such a group is saved.  The captured
 * subsequence may be used later in the expression, via a back reference, and
 * may also be retrieved from the matcher once the match operation is complete.
 *
 * <h4><a name="groupname">Group name</a></h4>
 * <p>A capturing group can also be assigned a "name", a <tt>named-capturing group</tt>,
 * and then be back-referenced later by the "name". Group names are composed of
 * the following characters. The first character must be a <tt>letter</tt>.
 *
 * <ul>
 *   <li> The uppercase letters <tt>'A'</tt> through <tt>'Z'</tt>
 *        (<tt>'&#92;u0041'</tt>&nbsp;through&nbsp;<tt>'&#92;u005a'</tt>),
 *   <li> The lowercase letters <tt>'a'</tt> through <tt>'z'</tt>
 *        (<tt>'&#92;u0061'</tt>&nbsp;through&nbsp;<tt>'&#92;u007a'</tt>),
 *   <li> The digits <tt>'0'</tt> through <tt>'9'</tt>
 *        (<tt>'&#92;u0030'</tt>&nbsp;through&nbsp;<tt>'&#92;u0039'</tt>),
 * </ul>
 *
 * <p> A <tt>named-capturing group</tt> is still numbered as described in
 * <a href="#gnumber">Group number</a>.
 *
 * <p> The captured input associated with a group is always the subsequence
 * that the group most recently matched.  If a group is evaluated a second time
 * because of quantification then its previously-captured value, if any, will
 * be retained if the second evaluation fails.  Matching the string
 * <tt>"aba"</tt> against the expression <tt>(a(b)?)+</tt>, for example, leaves
 * group two set to <tt>"b"</tt>.  All captured input is discarded at the
 * beginning of each match.
 *
 * <p> Groups beginning with <tt>(?</tt> are either pure, <i>non-capturing</i> groups
 * that do not capture text and do not count towards the group total, or
 * <i>named-capturing</i> group.
 *
 * <h3> Unicode support </h3>
 *
 * <p> This class is in conformance with Level 1 of <a
 * href="http://www.unicode.org/reports/tr18/"><i>Unicode Technical
 * Standard #18: Unicode Regular Expression</i></a>, plus RL2.1
 * Canonical Equivalents.
 * <p>
 * <b>Unicode escape sequences</b> such as <tt>&#92;u2014</tt> in Java source code
 * are processed as described in section 3.3 of
 * <cite>The Java&trade; Language Specification</cite>.
 * Such escape sequences are also implemented directly by the regular-expression
 * parser so that Unicode escapes can be used in expressions that are read from
 * files or from the keyboard.  Thus the strings <tt>"&#92;u2014"</tt> and
 * <tt>"\\u2014"</tt>, while not equal, compile into the same pattern, which
 * matches the character with hexadecimal value <tt>0x2014</tt>.
 * <p>
 * A Unicode character can also be represented in a regular-expression by
 * using its <b>Hex notation</b>(hexadecimal code point value) directly as described in construct
 * <tt>&#92;x{...}</tt>, for example a supplementary character U+2011F
 * can be specified as <tt>&#92;x{2011F}</tt>, instead of two consecutive
 * Unicode escape sequences of the surrogate pair
 * <tt>&#92;uD840</tt><tt>&#92;uDD1F</tt>.
 * <p>
 * Unicode scripts, blocks, categories and binary properties are written with
 * the <tt>\p</tt> and <tt>\P</tt> constructs as in Perl.
 * <tt>\p{</tt><i>prop</i><tt>}</tt> matches if
 * the input has the property <i>prop</i>, while <tt>\P{</tt><i>prop</i><tt>}</tt>
 * does not match if the input has that property.
 * <p>
 * Scripts, blocks, categories and binary properties can be used both inside
 * and outside of a character class.
 *
 * <p>
 * <b><a name="usc">Scripts</a></b> are specified either with the prefix {@code Is}, as in
 * {@code IsHiragana}, or by using  the {@code script} keyword (or its short
 * form {@code sc})as in {@code script=Hiragana} or {@code sc=Hiragana}.
 * <p>
 * The script names supported by <code>Pattern</code> are the valid script names
 * accepted and defined by
 * {@link java.lang.Character.UnicodeScript#forName(String) UnicodeScript.forName}.
 *
 * <p>
 * <b><a name="ubc">Blocks</a></b> are specified with the prefix {@code In}, as in
 * {@code InMongolian}, or by using the keyword {@code block} (or its short
 * form {@code blk}) as in {@code block=Mongolian} or {@code blk=Mongolian}.
 * <p>
 * The block names supported by <code>Pattern</code> are the valid block names
 * accepted and defined by
 * {@link java.lang.Character.UnicodeBlock#forName(String) UnicodeBlock.forName}.
 * <p>
 *
 * <b><a name="ucc">Categories</a></b> may be specified with the optional prefix {@code Is}:
 * Both {@code \p{L}} and {@code \p{IsL}} denote the category of Unicode
 * letters. Same as scripts and blocks, categories can also be specified
 * by using the keyword {@code general_category} (or its short form
 * {@code gc}) as in {@code general_category=Lu} or {@code gc=Lu}.
 * <p>
 * The supported categories are those of
 * <a href="http://www.unicode.org/unicode/standard/standard.html">
 * <i>The Unicode Standard</i></a> in the version specified by the
 * {@link java.lang.Character Character} class. The category names are those
 * defined in the Standard, both normative and informative.
 * <p>
 *
 * <b><a name="ubpc">Binary properties</a></b> are specified with the prefix {@code Is}, as in
 * {@code IsAlphabetic}. The supported binary properties by <code>Pattern</code>
 * are
 * <ul>
 *   <li> Alphabetic
 *   <li> Ideographic
 *   <li> Letter
 *   <li> Lowercase
 *   <li> Uppercase
 *   <li> Titlecase
 *   <li> Punctuation
 *   <Li> Control
 *   <li> White_Space
 *   <li> Digit
 *   <li> Hex_Digit
 *   <li> Join_Control
 *   <li> Noncharacter_Code_Point
 *   <li> Assigned
 * </ul>
 * <p>
 * The following <b>Predefined Character classes</b> and <b>POSIX character classes</b>
 * are in conformance with the recommendation of <i>Annex C: Compatibility Properties</i>
 * of <a href="http://www.unicode.org/reports/tr18/"><i>Unicode Regular Expression
 * </i></a>, when {@link #UNICODE_CHARACTER_CLASS} flag is specified.
 *
 * <table border="0" cellpadding="1" cellspacing="0"
 *  summary="predefined and posix character classes in Unicode mode">
 * <tr align="left">
 * <th align="left" id="predef_classes">Classes</th>
 * <th align="left" id="predef_matches">Matches</th>
 *</tr>
 * <tr><td><tt>\p{Lower}</tt></td>
 *     <td>A lowercase character:<tt>\p{IsLowercase}</tt></td></tr>
 * <tr><td><tt>\p{Upper}</tt></td>
 *     <td>An uppercase character:<tt>\p{IsUppercase}</tt></td></tr>
 * <tr><td><tt>\p{ASCII}</tt></td>
 *     <td>All ASCII:<tt>[\x00-\x7F]</tt></td></tr>
 * <tr><td><tt>\p{Alpha}</tt></td>
 *     <td>An alphabetic character:<tt>\p{IsAlphabetic}</tt></td></tr>
 * <tr><td><tt>\p{Digit}</tt></td>
 *     <td>A decimal digit character:<tt>p{IsDigit}</tt></td></tr>
 * <tr><td><tt>\p{Alnum}</tt></td>
 *     <td>An alphanumeric character:<tt>[\p{IsAlphabetic}\p{IsDigit}]</tt></td></tr>
 * <tr><td><tt>\p{Punct}</tt></td>
 *     <td>A punctuation character:<tt>p{IsPunctuation}</tt></td></tr>
 * <tr><td><tt>\p{Graph}</tt></td>
 *     <td>A visible character: <tt>[^\p{IsWhite_Space}\p{gc=Cc}\p{gc=Cs}\p{gc=Cn}]</tt></td></tr>
 * <tr><td><tt>\p{Print}</tt></td>
 *     <td>A printable character: {@code [\p{Graph}\p{Blank}&&[^\p{Cntrl}]]}</td></tr>
 * <tr><td><tt>\p{Blank}</tt></td>
 *     <td>A space or a tab: {@code [\p{IsWhite_Space}&&[^\p{gc=Zl}\p{gc=Zp}\x0a\x0b\x0c\x0d\x85]]}</td></tr>
 * <tr><td><tt>\p{Cntrl}</tt></td>
 *     <td>A control character: <tt>\p{gc=Cc}</tt></td></tr>
 * <tr><td><tt>\p{XDigit}</tt></td>
 *     <td>A hexadecimal digit: <tt>[\p{gc=Nd}\p{IsHex_Digit}]</tt></td></tr>
 * <tr><td><tt>\p{Space}</tt></td>
 *     <td>A whitespace character:<tt>\p{IsWhite_Space}</tt></td></tr>
 * <tr><td><tt>\d</tt></td>
 *     <td>A digit: <tt>\p{IsDigit}</tt></td></tr>
 * <tr><td><tt>\D</tt></td>
 *     <td>A non-digit: <tt>[^\d]</tt></td></tr>
 * <tr><td><tt>\s</tt></td>
 *     <td>A whitespace character: <tt>\p{IsWhite_Space}</tt></td></tr>
 * <tr><td><tt>\S</tt></td>
 *     <td>A non-whitespace character: <tt>[^\s]</tt></td></tr>
 * <tr><td><tt>\w</tt></td>
 *     <td>A word character: <tt>[\p{Alpha}\p{gc=Mn}\p{gc=Me}\p{gc=Mc}\p{Digit}\p{gc=Pc}\p{IsJoin_Control}]</tt></td></tr>
 * <tr><td><tt>\W</tt></td>
 *     <td>A non-word character: <tt>[^\w]</tt></td></tr>
 * </table>
 * <p>
 * <a name="jcc">
 * Categories that behave like the java.lang.Character
 * boolean is<i>methodname</i> methods (except for the deprecated ones) are
 * available through the same <tt>\p{</tt><i>prop</i><tt>}</tt> syntax where
 * the specified property has the name <tt>java<i>methodname</i></tt></a>.
 *
 * <h3> Comparison to Perl 5 </h3>
 *
 * <p>The <code>Pattern</code> engine performs traditional NFA-based matching
 * with ordered alternation as occurs in Perl 5.
 *
 * <p> Perl constructs not supported by this class: </p>
 *
 * <ul>
 *    <li><p> Predefined character classes (Unicode character)
 *    <p><tt>\X&nbsp;&nbsp;&nbsp;&nbsp;</tt>Match Unicode
 *    <a href="http://www.unicode.org/reports/tr18/#Default_Grapheme_Clusters">
 *    <i>extended grapheme cluster</i></a>
 *    </p></li>
 *
 *    <li><p> The backreference constructs, <tt>\g{</tt><i>n</i><tt>}</tt> for
 *    the <i>n</i><sup>th</sup><a href="#cg">capturing group</a> and
 *    <tt>\g{</tt><i>name</i><tt>}</tt> for
 *    <a href="#groupname">named-capturing group</a>.
 *    </p></li>
 *
 *    <li><p> The named character construct, <tt>\N{</tt><i>name</i><tt>}</tt>
 *    for a Unicode character by its name.
 *    </p></li>
 *
 *    <li><p> The conditional constructs
 *    <tt>(?(</tt><i>condition</i><tt>)</tt><i>X</i><tt>)</tt> and
 *    <tt>(?(</tt><i>condition</i><tt>)</tt><i>X</i><tt>|</tt><i>Y</i><tt>)</tt>,
 *    </p></li>
 *
 *    <li><p> The embedded code constructs <tt>(?{</tt><i>code</i><tt>})</tt>
 *    and <tt>(??{</tt><i>code</i><tt>})</tt>,</p></li>
 *
 *    <li><p> The embedded comment syntax <tt>(?#comment)</tt>, and </p></li>
 *
 *    <li><p> The preprocessing operations <tt>\l</tt> <tt>&#92;u</tt>,
 *    <tt>\L</tt>, and <tt>\U</tt>.  </p></li>
 *
 * </ul>
 *
 * <p> Constructs supported by this class but not by Perl: </p>
 *
 * <ul>
 *
 *    <li><p> Character-class union and intersection as described
 *    <a href="#cc">above</a>.</p></li>
 *
 * </ul>
 *
 * <p> Notable differences from Perl: </p>
 *
 * <ul>
 *
 *    <li><p> In Perl, <tt>\1</tt> through <tt>\9</tt> are always interpreted
 *    as back references; a backslash-escaped number greater than <tt>9</tt> is
 *    treated as a back reference if at least that many subexpressions exist,
 *    otherwise it is interpreted, if possible, as an octal escape.  In this
 *    class octal escapes must always begin with a zero. In this class,
 *    <tt>\1</tt> through <tt>\9</tt> are always interpreted as back
 *    references, and a larger number is accepted as a back reference if at
 *    least that many subexpressions exist at that point in the regular
 *    expression, otherwise the parser will drop digits until the number is
 *    smaller or equal to the existing number of groups or it is one digit.
 *    </p></li>
 *
 *    <li><p> Perl uses the <tt>g</tt> flag to request a match that resumes
 *    where the last match left off.  This functionality is provided implicitly
 *    by the {@link Matcher} class: Repeated invocations of the {@link
 *    Matcher#find find} method will resume where the last match left off,
 *    unless the matcher is reset.  </p></li>
 *
 *    <li><p> In Perl, embedded flags at the top level of an expression affect
 *    the whole expression.  In this class, embedded flags always take effect
 *    at the point at which they appear, whether they are at the top level or
 *    within a group; in the latter case, flags are restored at the end of the
 *    group just as in Perl.  </p></li>
 *
 * </ul>
 *
 *
 * <p> For a more precise description of the behavior of regular expression
 * constructs, please see <a href="http://www.oreilly.com/catalog/regex3/">
 * <i>Mastering Regular Expressions, 3nd Edition</i>, Jeffrey E. F. Friedl,
 * O'Reilly and Associates, 2006.</a>
 * </p>
 *
 * <p>
 *  正则表达式的编译表示
 * 
 *  <p>指定为字符串的正则表达式必须首先编译为此类的实例。
 * 然后,生成的模式可用于创建{@link Matcher}对象,该对象可以匹配任意{@linkplain javalangCharSequence字符序列}正则表达式所有执行匹配的状态都驻留在匹配器中,因
 * 此许多匹配器可以共享相同的模式。
 *  <p>指定为字符串的正则表达式必须首先编译为此类的实例。
 * 
 *  因此,典型的调用序列
 * 
 * <blockquote> <pre> Pattern p = Pattern {@link #compile compile}("a * b"); Matcher m = p {@link #matcher matcher}
 * ("aaaaab"); boolean b = m {@link Matcher#matches matches}(); </pre> </blockquote>。
 * 
 *  <p> {@link #matches matches}方法由此类定义,以方便当正则表达式仅使用一次时此方法在单个调用中编译表达式并匹配输入序列。
 * 
 *  <blockquote> <pre> boolean b = Patternmatches("a * b","aaaaab"); </pre> </blockquote>
 * 
 *  等同于上面的三个语句,虽然对于重复匹配,它效率较低,因为它不允许重新使用编译模式
 * 
 * <p>此类的实例是不可变的,并且对于多个并发线程安全使用{@link Matcher}类的实例对此类使用不安全
 * 
 *  <h3> <a name=\"sum\">正则表达式结构摘要</a> </h3>
 * 
 *  <table border ="0"cellpadding ="1"cellspacing ="0"
 * summary="Regular expression constructs, and what they match">
 * 
 * <tr align="left">
 *  <th align ="left"id ="construct">构造</th> <th align ="left"id ="matches">匹配</th>
 * </tr>
 * 
 *  <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="characters">字符</th>
 * 
 * <tr> <td valign ="top"headers ="构造字符"> <i> x </i> </td> <td header ="matches">字符<i> x </i> </td > </td>
 *  </td> <td header ="matches">反斜杠字符</td> </td> </td> </tr> <tr> <td valign ="top"headers ="construct characters">
 *  <tt> \\ 0 </tt> <i> n </i> </td> <td headers ="matches">八进制值<tt> 0 </tt> <i> n </i>(0&lt; tt&gt;&lt;
 *  = </tt>&nbsp; n </i>&lt; tt&gt; ; = </tt>&nbsp; 7)</td> </tr> <tr> <td valign ="top"headers ="construct characters">
 *  <tt> \\ 0 </tt> <i> i> </td> <td headers ="matches">八进制值<tt> 0的字符</tt> <i> nn </i>(0&nbsp; <tt>&lt; 
 * = </tt> <i> n </i> <tt> <= </tt>&nbsp; 7)</td> </tr> <tr> <td valign ="top"headers ="construct characters" tt>
 *  \\ 0 </tt> <i> mnn </i> </td> <td headers ="matches"> Th具有八进制值<tt> 0 </tt> </i>(0 <tt> <= </tt> <i> 
 * m </i>&lt; tt&gt; ; = </tt>&nbsp; 3,0&nbsp; <tt>&lt; = </tt>&nbsp; <i> n </i>&nbsp; <tt>&lt; = </tt>&
 * nbsp; td> </tr> <tr> <td valign ="top"headers ="construct characters"> <tt> \\ x </tt> <i> hh </i> </td>
 *  <td headers ="matches ">十六进制值字符<tt> 0x </tt> <i> hh </i> </td> </tr> <tr> <td valign ="top"headers ="construct characters" <tt>
 *  \\ u </tt> <i> hhhh </i> </td> <td headers ="matches">带十六进制值的字符<tt> 0x </tt> <i> hhhh < i> </td> </t>
 *  <tr> <td valign ="top"headers ="construct characters"> <tt> \\ x </tt> <i> {hh} </i>({@link javalangCharacter#MIN_CODE_POINT CharacterMIN_CODE_POINT}
 * )的字符。
 * </tt> </tt> </tt> &nbsp;&lt; =&nbsp; <tt> 0x </tt> <i> hh </i>&lt; =&nbsp; {@link javalangCharacter#MAX_CODE_POINT CharacterMAX_CODE_POINT}
 * )</td> </tr> <tr> <td valign ="top"headers ="matches"> <tt> \\ t </tt> </td> <td headers ="matches"> 
 * <tt>'\\ u0009'</tt>)</td> </tr> <tr> <td valign ="top"headers ="construct characters"> <tt> \n</tt> /
 *  td> <td headers ="matches">换行符(换行符)字符(<tt>'\\ u000A'</tt>)</td> </tr> <tr> <td valign = ="构造字符"> <tt>
 *  \\ r </tt> </td> <td headers ="matches">回车符(<tt>'\\ u000D'</tt>)</td> < / tr> <tr> <td valign ="top"headers ="构造字符">
 *  <tt> \\ f </tt> </td> <td headers ="matches">换页字符'\\ u000C'</tt>)</td> </tr> <tr> <td valign ="top"headers ="construct characters">
 *  <tt> \\ a </tt> </td> <td headers ="matches">警报(铃)字符(<tt>'\\ u0007'</tt>)</td> </tr> <tr> <td valign =s ="construct characters">
 *  <tt> \\ e </tt> </td> <td headers ="matches">转义字符(<tt>'\\ u001B'</tt>)</td> tr> <tr> <td valign ="top"headers ="construct characters">
 *  <tt> \\ c </tt> <i> x </i> </td> <td headers ="matches">与<i> </i> </td> </tr>对应的字符h} </i>({@link javalangCharacter#MIN_CODE_POINT CharacterMIN_CODE_POINT}
 * )的字符。
 * </tt> </tt> </tt> &nbsp;&lt; =&nbsp; <tt> 0x </tt> <i> hh </i>&lt; =&nbsp; {@link javalangCharacter#MAX_CODE_POINT Character。
 * 
 * <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="classes">字符类</th>
 * 
 * <tr> <td valign ="top"headers ="construct classes"> {@ code [abc]} </td> <td headers ="matches"> {@ code a}
 * ,{@code b} @code c}(simple class)</td> </tr> <tr> <td valign ="top"headers ="construct classes"> {@ code [^ abc]}
 *  </td> <td headers = matches"> {@code a},{@code b}或{@code c}(否定)以外的任何字符</td> </tr> <tr> <td valign ="top"headers = ">
 *  {@ code [a-zA-Z]} </td> <td headers ="matches"> {@ code a}到{@code z}或{@code A}到{@code Z} (range)</td>
 *  </tr> <tr> <td valign ="top"headers ="construct classes"> {@ code [ad [mp]]} </td> <td headers ="matches">
 *  {@code a}到{@code d}或{@code m}到{@code p}：{@code [a-dm-p]}(union)</td> </tr> <td valign ="top"headers ="construct classes">
 *  {@ code [a-z && [def]]} </td> <td headers ="matches"> {@ code d},{@ code e}或{@code f}(intersection)</tr>
 *  <tr> <td valign ="top"headers ="construct classes"> {@ code [a-z && [^ bc]除了{@code b}和{@code c}：{@code [ad-z]}
 * (减法)之外,还包括{td> <td header ="matches"> {@ code a} td> </tr> <tr> <td valign ="top"headers ="construct classes">
 *  {@ code [a-z && [^ mp]]} </td> <td headers ="matches"代码a}到{@code z},而不是{@code m}到{@code p}：{@code [a-lq-z]}(减法)</td>
 *  </tr> <tr> th> </th> </tr>。
 * 
 * <tr align ="left"> <th colspan ="2"id ="predef">预定义字符类</th> </tr>
 * 
 * <tr> <td valign ="top"headers ="construct predef"> <tt></td> </td> <td headers ="matches">任何字符(可能不匹配<a href=\"#lt\">
 * 行终止符</a>)</td> </tr> <tr > <td valign ="top"headers ="construct predef"> <tt> \\ d </tt> </td> <td headers ="matches">
 * 一个数字：<tt> [0-9] </tt > </td> </td> <td> </td> <td header ="matches"> </td> </td> </td> </td> <td valign ="top"headers ="construct predef"数字：<tt>
 *  [^ 0-9] </tt> </td> </tr> <tr> <td valign ="top"headers ="construct predef"> <tt> \\ h </tt> / td> <td headers ="matches">
 * 水平空格字符：<tt> [\\ t \\ xA0 \\ u1680 \\ u180e \\ u2000- \\ u200a \\ u202f \\ u205f \\ u3000] </tt> </td>
 *  tr> <tr> <td valign ="top"headers ="construct predef"> <tt> \\ H </tt> </td> <td headers ="matches">
 * 非水平空白字符：<tt> [^ \\ h] </tt> </td> </tr> <tr> <td valign ="top"headers ="construct predef"> <tt> \\ s 
 * </tt><td headers ="matches">空格字符：<tt> [\\ t\n\\ x0B \\ f \\ r] </tt> </td> </tr> <tr> <td valign = headers ="construct predef">
 *  <tt> \\ S </tt> </td> <td headers ="matches">非空格字符：<tt> [^ \\ s] </tt> </td> </t> <tt> <td header ="matches">
 * 垂直空格字符：<tt> </t> [\n\\ x0B \\ f \\ r \\ x85 \\ u2028 \\ u2028] </tt> </td> </tr> <tr> <td valign ="top"headers ="construct predef">
 *  <tt> V </tt> </td> <td headers ="matches">非垂直空格字符：<tt> [^ \\ v] </tt> </td> </tr> <tr> <td valign ="top"headers ="construct predef">
 *  <tt> \\ w </tt> </td> <td headers ="matches">一个字符：<tt> [a-zA-Z_0-9] tt> </td> </tt> </td> </td> <td header ="matches">
 *  </td> </td>字符：<tt> [^ \\ w] </tt> </td> </tr> <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id = posix">
 *  <b> POSIX字符类(仅限于US-ASCII)</b> </th> </tr><tr> <td valign ="top"headers ="construct predef"> <tt>。
 * 
 * <tr> <td valign ="top"headers ="construct posix"> {@ code \\ p {Lower}} </td> <td headers ="matches">
 * 小写字母字符：{@code [az ]} </td> </td> <td header ="matches"> </td> </td> </td> <td valign ="top"headers ="construct posix" -case字母字符：{@ code [AZ]} </td>
 *  </tr> <tr> <td valign ="top"headers ="construct posix"> {@ code \\ p {ASCII}} </td> <td headers ="matches">
 * 所有ASCII：{@ code [\\ x00- \\ x7F]} </td> </tr> <tr> <td valign ="top"headers ="construct posix"> {@ code \\ p {Alpha}
 * } </td> <td headers ="matches">字母字符：{@ code [\\ p {Lower} \\ p {Upper}]} </td> </tr> <tr> < td valign ="top"headers ="construct posix">
 *  {@ code \\ p {Digit}} </td> <td headers ="matches">十进制数字：{@code [0-9]} </td > </tr> <tr> <td valign ="top"headers ="construct posix">
 *  {@ code \\ p {Alnum}} </td> <td headers ="matches">一个字母数字字符：{@ code [\\ p {Alpha} \\ p {Digit}]} </td>
 *  </tr> <tr> <td valign ="top"headers ="construct posix" > {@ code \\ p {Punct}} </td> <td headers ="matches">
 * 标点：{@code！"#$％&'()* +, - / :; <=>?@ [\\] ^ _`{|}〜} </td> </tr> <！ -  {@code [\\！ \\ +,\\  -  \\ /：; \\ <= \\> \\?@ \\ [\\\\\\] \\ ^ _`\\ {\\ | \\}
 * 〜]}。
 * {@code [\X21-\X2F\X31-\X40\X5B-\X60\X7B-\X7E]} -->
 * <tr> <td valign ="top"headers ="construct posix"> {@ code \\ p {Graph}} </td> <td headers ="matches">
 * 可见字符：{@code [\\ p {Alnum } \\ p {Punct}]} </td> </tr> <tr> <td valign ="top"headers ="construct posix">
 *  {@ code \\ p {Print}} </td> <td headers = "matches">可打印字符：{@code [\\ p {Graph} \\ x20]} </td> </tr> 
 * <tr> <td valign ="top"headers ="construct posix" p {Blank}} </td> <td headers ="matches">一个空格或制表符：{@code [\\ t]}
 *  </td> </tr> <tr> <td valign = ="construct posix"> {@ code \\ p {Cntrl}} </td> <td headers ="matches">
 * 控制字符：{@code [\\ x00- \\ x1F \\ x7F]} </td> tr> <tr> <td valign ="top"headers ="construct posix"> {@ code \\ p {XDigit}
 * } </td> <td headers ="matches">十六进制数字：{@code [ 9a-fA-F]} </td> </tr> <tr> <td valign ="top"headers ="construct posix">
 *  {@ code \\ p {Space}} </td> <tdheaders ="matches">空格字符：{@code [\\ t\n\\ x0B \\ f \\ r]} </td> </tr>。
 * 
 * <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"> javalangCharacter类(简单<a href=\"#jcc\">
 *  java字符类型< a>)</th> </tr>。
 * 
 *  <tr> <td valign ="top"> <tt> \\ p {javaLowerCase} </tt> </td> <td>等效于javalangCharacterisLowerCase()</td>
 *  </tr> <tr> <td valign = "top"> <tt> \\ p {javaUpperCase} </tt> </td> <td>等效于javalangCharacterisUpper
 * Case()</td> </tr> <tr> <td valign ="top"> <tt> \\ p {javaWhitespace} </tt> </td> <td>等效于javalangChara
 * cterisWhitespace()</td> </tr> <tr> <td valign ="top"> <tt> \\ p {javaMirrored} tt> </td> <td>等效于javal
 * angCharacterisMirrored()</td> </tr>。
 * 
 * Unicode脚本,块,类别和二进制属性的类</span> </span> </span> </span> </span> th> </tr> <tr> <td valign ="top"headers ="construct unicode">
 *  {@ code \\ p {IsLatin}} </td> <td headers ="matches">拉丁语脚本字符<a href=\"#usc\"> script </a>)</td> </tr>
 *  <tr> <td valign ="top"headers ="construct unicode"> {@ code \\ p {InGreek}} < / td> <td headers ="matches">
 * 希腊语块中的字符(<a href=\"#ubc\">块</a>)</td> </tr> <tr> <td valign = top"headers ="construct unicode"> {@ code \\ p {Lu}
 * } </td> <td headers ="matches">大写字母(<a href=\"#ucc\">类别</a>)< / td> </tr> <tr> <td valign ="top"headers ="construct unicode">
 *  {@ code \\ p {IsAlphabetic}} </td> <td headers ="matches">字母字符a href ="#ubpc">二进制属性</a>)</td> </tr> 
 * <tr> <td valign ="top"headers ="construct unicode"> {@ code \\ p {Sc}} </td> <td headers ="matches">货
 * 币符号</td > </tr> <tr> <td valign ="top"headers ="construct unicode"> {@ code \\ P {InGreek}} </td> <td headers ="matches">
 *  block(negation)</td> </tr> <tr> <td valign ="top"headers ="construct unicode"> {@ code [\\ p {L} && 
 * [^ \\ p {Lu} / td> <td headers ="matches">除大写字母以外的任何字母(减法)</td> </tr>。
 * 
 * <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="bounds">边界匹配</th>
 * 
 * <tr> <td valign ="top"headers ="construct bounds"> <tt> ^ </tt> </td> <td headers ="matches">一行的开头</td>
 *  <tr> <td valign ="top"headers ="construct bounds"> <tt> $ </tt> </td> <td headers ="matches">行尾</td>
 *  <tr> <td valign ="top"headers ="construct bounds"> <tt> \\ b </tt> </td> <td headers ="matches">字边界</td>
 *  </tr> <td valign ="top"headers ="construct bounds"> <tt> \\ B </tt> </td> <td headers ="matches">非字边
 * 界</td> <tr> <td valign ="top"headers ="construct bounds"> <tt> \\ A </tt> </td> <td headers ="matches">
 * 输入开始</td> </tr > <tr> <td valign ="top"headers ="construct bounds"> <tt> \\ G </tt> </td> <td headers ="matches">
 * 上一场比赛结束</td> / tr> <tr> <td valign ="top"headers ="construct bounds"> <tt> \\ Z </tt> </td> <td headers =s">
 * 输入结束但对于最终的<a href=\"#lt\">终止符</a>,如果任何</td> </tr> <tr> <td valign ="top"headers = "construct bounds">
 *  <tt> \\ z </tt> </td> <td headers ="matches">输入结束</td> </tr>。
 * 
 * <tr> <th> </th> </tr> <tr align ="left"> <th colspan ="2"id ="lineending"> Linebreak matcher </th> </tr>
 *  <td valign ="top"headers ="construct lineending"> <tt> \\ R </tt> </td> <td headers ="matches">任何Uni
 * code换行序列,相当于<tt> \\ u000D \\ u000A | [\\ u000A \\ u000B \\ u000C \\ u000D \\ u0085 \\ u2028 \\ u2029]
 *  </tt> </td> </tr>。
 * 
 *  <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="greedy">贪婪量词</th>
 * 
 * <tr> <td valign ="top"headers ="construct greedy"> <i> X </i> <tt>?</tt> </td> <td headers ="matches">
 *  < / i>,一次或根本不需要</td> </tr> <tr> <td valign ="top"headers ="construct greedy"> <i> X </i> <tt> * </tt>
 *  </td> </t> <tr> <td valign ="top"headers ="construct greedy"> </td> <td header ="matches"> <i> X </i>
 *  <i> X </i> <tt> + </tt> </td> <td headers ="matches"> <i> X </i>,一次或多次</td> </tr> tr> <td valign ="top"headers ="construct greedy">
 *  <i> X </i> <tt> {</tt> <i> n </i> <tt>} </tt> td> <td header ="matches"> <i> X </i>,完全<i> n </td> </tr>
 *  <tr> <td valign ="top"headers =构造贪婪"> <i> X </i> <tt> {</tt> <i> n </i> <tt>,} </tt> </td> <td headers ="matches" i>
 *  X </i>,至少<i> n次</td> </tr> <tr> <td valign ="top"headers ="construct greedy"> < i> <tt> {</tt> <i> n </t>,</tt>/ i> <tt>}
 *  </tt> </td> <td headers ="matches"> <i> X </i>,至少<i> n </i>但不超过<i> </i>次</td> </tr>。
 * 
 * <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="reluc">勉强量词</th>
 * 
 * <tr> <td valign ="top"headers ="construct reluc"> <i> X </i> <tt> ?? </tt> </td> <td headers ="matches">
 *  <i> X </i> </t> </t> <td valign ="top"headers ="construct reluc"> <i> X </i> <tt> tt> </td> <td headers ="matches">
 *  <i> X </i>,零次或多次</td> </tr> <tr> <td valign ="top"headers = "> </i> </td> </td> </td> </td> </td> tr
 * > <tr> <td valign ="top"headers ="construct reluc"> <i> </tt> {</tt> <i> n </i> <tt> tt> </td> <td headers ="matches"> <i> X </i>,恰好<i> n </i>次</td> </tr> <tr> <td valign = "headers ="construct reluc"> <i> </tt> </tt> </tt> </td> </td> <td header =至少<i> n </i>次</td> </tr> <tr> <td valign ="top"headers ="construct reluc"> </i> X </i> <tt> {</tt> </i> <tt>,</tt>i> <tt>}
 *  </tt> </td> <td headers ="matches"> <i> X </i>,至少<i> n </i>但不超过<i> </i>次</td> </tr>。
 * 
 * <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="poss"> Possessive quantifiers </th>
 * 。
 * 
 * <tr> <td valign ="top"headers ="construct poss"> <i> X </i> <tt>?</tt> </td> <td headers ="matches"> 
 * < </td> </tr> <tr> <td valign ="top"headers ="construct poss"> <i> X </i> <tt> * + </tt> </td> <td headers ="matches">
 *  <i> X </i>,零次或多次</td> </tr> <tr> <td valign ="top"headers = "> <i> X </i> <tt> ++ </tt> </td> <td headers ="matches">
 *  <i> X </i> tr> <tr> <td valign ="top"headers ="construct poss"> </i> <tt> {</tt> <i> n </i> <tt>} + 
 * tt> </td> <td headers ="matches"> <i> X </i>,恰好<i> n </i>次</td> </tr> <tr> <td valign = "headers ="construct poss">
 *  <i> X </i> <tt> {</tt> <i> n </i> <tt>,} + </tt> </td> <td headers = "matches"> <i> X </i>,至少<i> n </i>
 * 次</td> </tr> <tr> <td valign ="top"headers ="construct poss"> < i> X <tt> {</tt> <i> n <tt>,</tt> <i> m <tt>}
 *  + </tt> </td> <td headers ="matches"> <i> X </i>,至少<i> n </i>但不超过<m> </td> </tr>。
 * 
 * <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="logical">逻辑运算符</th>
 * 
 *  <tr> <td valign ="top"headers ="construct logical"> <i> XY </i> </td> <td headers ="matches"> <i> X 
 * </i> Y </i> </td> </t> </t> <tr> <td valign ="top"headers ="construct logical"> <i> X </i> <tt> | </tt>
 *  <i> Y </i> </td> <td header ="matches"> <i> X </i>或<i> Y </i> </td> </tr> <tr> <td valign = top"headers ="construct logical">
 *  <tt>(</tt> <i> X </i> <tt>)</tt> </td> <td headers ="matches"> X, a href ="#cg">捕获组</a> </td> </tr>。
 * 
 *  <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="backref">返回参考</th>
 * 
 * <tr> <td valign ="bottom"headers ="construct backref"> <tt> \\ </tt> <i> n </i> </td> <td valign ="bottom"headers ="matches">
 * 无论</i> </p> </p> </sup> <a href=\"#cg\">捕获组</a>匹配</td>。
 * 
 *  <tr> <td valign ="bottom"headers ="construct backref"> <tt> \\ </tt> <i> k </i>&lt; <i> name </i>&gt
 * ; </td valign ="bottom"headers ="matches">无论<a href=\"#groupname\">命名捕获组</a>"名称"是否匹配</td> </tr>。
 * 
 *  <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="quot">报价</th>
 * 
 * <tr> <td valign ="top"headers ="construct quot"> <tt> \\ </tt> </td> <td headers ="matches">没有,但引用以下字
 * 符</td> tr> <tr> <td valign ="top"headers ="construct quot"> <tt> \\ Q </tt> </td> <td headers ="matches">
 * 不引用所有字符, \\ E </tt> </td> </td> </td> <td header ="matches "> </td> </tr> <！ -  Metachars：！$()* + <>?
 * [\\] ^ {|} >。
 * 
 *  <tr> <th>&nbsp; </th> </tr> <tr align ="left"> <th colspan ="2"id ="special">特殊结构(命名捕获和非捕获) th> </tr>
 * 。
 * 
 * <tr> <td valign ="top"headers ="construct special"> <tt>(?&lt; <a href=\"#groupname\"> name </a>&gt; 
 * </tt> <i> X < i> <tt>)</tt> </td> <td headers ="matches"> <i> X </i>作为命名捕获群组</td> </tr> <tr> <td valign ="top"headers ="construct special">
 *  <tt>(?：</tt> <i> X </i> <tt>)</tt> </td> <td headers ="matches"> <i> X </i>作为非捕获组</td> </tr> <tr> <td valign ="top"headers ="construct special">
 *  <tt>(?idmsuxU-idmsuxU)&nbsp ; </tt> </td> <td headers ="matches">没有任何内容,但会显示<a href=\"#CASE_INSENSITIVE\">
 *  i </a> <a href=\"#UNIX_LINES\"> d </a > <a href=\"#MULTILINE\"> m </a> <a href=\"#DOTALL\"> s </a> <a href=\"#UNICODE_CASE\">
 *  u </a> <a href ="#评论"> x </a> <a href=\"#UNICODE_CHARACTER_CLASS\"> U </a>开 - 关</td> </tr> <tr> <td valign ="top"headers ="construct special">
 *  < tt>(?idmsux-idmsux：</tt> <i> X </i> <tt>)</tt>&nbsp;&nbsp; </td> <td headers ="matches"> <i> X </i>
 * ,作为<a href=\"#cg\">非捕获组</a>给定标志<a href=\"#CASE_INSENSITIVE\"> i </a> <a href=\"#UNIX_LINES\"> d </a> 
 * <a href=\"#MULTILINE\"> m </a> <a href ="# DOTALL"> </a> <a href=\"#UNICODE_CASE\"> u </a> <a href=\"#COMMENTS\">
 *  x </a>开 - 关</td> </tr> <tr> <td valign ="top"headers ="construct special"> <tt>(?= </tt> <i> X </i> 
 * <tt>)</tt> </td> <td headers ="matches "</td> </tr> <tr> <td valign ="top"headers ="construct special">
 *  <tt>(?！</tt> <i> X </i> <tt>)</tt> </td> <td headers ="matches"> <i> X </i> / tr> <tr> <td valign ="top"headers ="construct special">
 *  <tt>(?<= </tt> <i> X </i> <tt>)</tt> td> <td headers ="matches"> <i> X </i>,通过零宽度正向查找</td> </tr> <tr>
 * <td valign ="top"headers ="construct special"> <tt>(?&lt; / tt> <i> X </i> <tt>)</tt> </td> <td headers = "matches">
 *  <i> X </i>,通过零宽度阴影后端</td> </tr> <tr> <td valign ="top"headers ="construct special"> <tt> ; </tt> <i>
 *  X </i> <tt>)</tt> </td> <td headers ="matches"> <i> X </i> </td> </tr>。
 * 
 * </table>
 * 
 * <hr>
 * 
 * <h3> <a name=\"bs\">反斜杠,转义和引用</a> </h3>
 * 
 *  <p>反斜杠字符(<tt>'\\'</tt>)用于引入如上表中定义的转义结构,以及引用字符,否则将被解释为非转义结构。
 * 因此,表达式<tt > \\\\ </tt>匹配单个反斜杠,<tt> \\ {</tt>匹配左括号。
 * 
 *  <p>在任何不表示转义构造的字母字符之前使用反斜杠是一个错误;这些保留用于未来对正则表达式语言的扩展反斜杠可以在非字母字符之前使用,而不管该字符是否是非转义结构的一部分
 * 
 * <p> Java源代码中字符串文字中的反斜杠按<cite> Java&trade;语言规范</cite>作为Unicode转义(第33节)或其他字符转义(第3106节)因此,必须在表示正则表达式的字符串
 * 文字中加双反斜杠,以保护它们不被Java字节码编译器解释。
 * 字符串字面值<例如,当解释为正则表达式时匹配单个退格字符,而<tt>"\\\\ b"</tt>匹配单词边界字符串文字<tt>"</b> \\(hello \\)"</tt>是非法的,并导致编译时错误;为
 * 了匹配字符串<tt>(hello)</tt>,必须使用字符串文字<tt>"\\\\(hello \\\\)"</tt>。
 * 
 * <h3> <a name=\"cc\">字符类</a> </h3>
 * 
 *  <p>字符类可以出现在其他字符类中,并且可以由联合运算符(隐式)和交集运算符(<tt>&amp; </tt>)组成。联合运算符表示包含每个字符的类即在其操作数类中的至少一个中。
 * 交集运算符表示包含其操作数类中的每个字符的类。
 * 
 *  <p>字符类运算符的优先级如下,从最高到最低：
 * 
 *  <blockquote> <table border ="0"cellpadding ="1"cellspacing ="0"
 * summary="Precedence of character class operators.">
 * <tr> <th> 1&nbsp;&nbsp;&nbsp;&nbsp; </th> <td>字面转义&nbsp;&nbsp;&nbsp;&nbsp; </td> <td> <tt> \\ x </tt>
 *  </tr> <tr> <th> 2&nbsp;&nbsp;&nbsp;&nbsp; </th> <td>分组</td> <td> <tt> [] </tt> <tr> <th> 3&nbsp;&nbs
 * p;&nbsp;&nbsp; </th> <td>范围</td> <td> <tt> az </tt> </td> </tr> <tr> <th > 4&nbsp;&nbsp;&nbsp;&nbsp; 
 * </th> <td>联盟</td> <td> <tt> [ae] [iu] </tt> </td> </tr> > 5&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </th> <td>交
 * 集</td> <td> {@ code [a-z && [aeiou]]} </td> </tr> blockquote>。
 * 
 *  <p>请注意,一个不同的元字符集在字符类中比在一个字符类之外有效。例如,正则表达式<tt> </tt>在字符类中失去其特殊含义,而表达式<tt> -  </tt>成为范围形成元字符
 * 
 * <h3> <a name=\"lt\">行终止符</a> </h3>
 * 
 *  <p> </i>行终止符</i>是一个一个或两个字符的序列,标记输入字符序列的行的结尾。以下行被识别为行终止符：
 * 
 * <ul>
 * 
 *  <li>换行符(换行符)字符(<tt>'\n'</tt>),
 * 
 *  <li>回车字符后紧跟换行符(<tt>"\\ r\n"</tt>),
 * 
 *  <li>独立回车字元(<tt>'\\ r'</tt>),
 * 
 *  <li>下一个字符(<tt>'\\ u0085'</tt>),
 * 
 *  <li>分隔符字符(<tt>'\\ u2028'</tt>)或
 * 
 *  <li>段落分隔符字符(<tt>'\\ u2029 </tt>)
 * 
 * </ul>
 *  <p>如果{@link #UNIX_LINES}模式已激活,则唯一识别的行终止符是换行符
 * 
 * <p>正则表达式<tt> </tt>匹配除了行终止符之外的任何字符,除非指定了{@link #DOTALL}标志
 * 
 *  <p>默认情况下,正则表达式<tt> ^ </tt>和<tt> $ </tt>忽略行终止符,并且只分别匹配整个输入序列的开头和结尾如果{@link #MULTILINE}模式被激活,然后<tt> ^ 
 * </tt>匹配在输入开始和任何行终止符之后,除了在输入结束时在{@link #MULTILINE}模式<tt> $ </tt>匹配就在行终止符之前或输入序列的末尾。
 * 
 *  <h3> <a name=\"cg\">分组和捕获</a> </h3>
 * 
 * <h4> <a name=\"gnumber\">群组编号</a> </h4> <p>捕获组的编号方式是从左到右计算其左括号在表达式<tt>((A) C)))</tt>,例如,有四个这样的组：</p>。
 * 
 *  <blockquote> <table cellpadding = 1 cellspacing = 0 summary ="Capturing group numbersings"> <tr> <th>
 *  1&nbsp;&nbsp;&nbsp;&nbsp; </th> <td> <tt>((A) C)))</tt> </td> </tr> <tr> <th> 2&nbsp;&nbsp;&nbsp;&nb
 * sp; </th> <td> <tt>(A)</tt> </td > </tr> <tr> <th> 3&nbsp;&nbsp;&nbsp;&nbsp; </th> <td> <tt>(B(C))</tt>
 *  </td> <th> 4&nbsp;&nbsp;&nbsp;&nbsp; </th> <td> <tt>(C)</tt> </td> </tr> </table> </blockquote>。
 * 
 *  <p>组零始终代表整个表达式
 * 
 * <p>捕获组是这样命名的,因为在匹配期间,匹配该组的输入序列的每个子序列被保存。捕获的子序列可以在后面通过反向引用用于表达式,并且也可以从匹配操作完成后匹配
 * 
 *  <h4> <a name=\"groupname\">群组名称</a> </h4> <p>捕获群组也可以分配有"名称",<tt>命名捕获群组</tt>然后被后面引用的"名称"组名称由以下字符组成第一个
 * 字符必须是<tt>字母</tt>。
 * 
 * <ul>
 * <li>大写字母<tt>'A'</tt>至<tt>'Z'</tt>(<tt>'\\ u0041'</tt>&nbsp; through&nbsp; <tt>'\\ u005a' </tt>)</tt>(
 * <tt>'</tt>)</tt> tt>'\\ u007a'</tt>),<li>数字<tt>'0'</tt>至<tt>'9'</tt>(<tt>'\\ u0030'</tt> &nbsp; throu
 * gh&nbsp; <tt>'\\ u0039'</tt>),。
 * </ul>
 * 
 *  <p> A <tt>命名捕获组</tt>仍按<a href=\"#gnumber\">组号</a>中所述进行编号
 * 
 * <p>与组关联的捕获输入始终是组最近匹配的子序列如果组由于量化第二次计算,则如果第二个计算失败,则先前捕获的值(如果有)将被保留。
 * 例如,将第2组设置为<tt>"b"</tt>全部替换<tt>(a(b)?)+ </tt>的字符串<tt>"aba"</tt>捕获的输入在每次匹配的开始被丢弃。
 * 
 *  <p>以<tt>(?</tt>)开头的群组是纯粹的,<i>不捕获</i>群组,不捕获文字,不计入群组总计,捕获</i>组
 * 
 *  <h3> Unicode支持</h3>
 * 
 * <p>此课程符合<a href=\"http://wwwunicodeorg/reports/tr18/\"> Unicode技术标准#18：Unicode正则表达式</i> </a>的第1级,加上RL
 * 21规范等效。
 * <p>
 *  <b> Java转义序列</b>如Java源代码中的<tt> \\ u2014 </tt>按照<cite> Java&trade;语言规范</cite>此类转义序列也直接由正则表达式解析器实现,以便可
 * 以在从文件或从键盘读取的表达式中使用Unicode转义。
 * 因此,字符串<tt>"\\ u2014"</tt >和<tt>"\\\\ u2014"</tt>,而不等于,编译成相同的模式,匹配十六进制值<tt> 0x2014 </tt>。
 * <p>
 * Unicode字符也可以通过使用<b>十六进制符号</b>(十六进制代码点值)直接按照构造<tt> \\ x {} </tt>中的描述在正则表达式中表示,例如a补充字符U + 2011F可以指定为<tt>
 *  \\ x {2011F} </tt>,而不是替代对<2>连续的两个Unicode转义序列<tt> \\ uD840 </tt> <tt> \\ uDD1F </tt >。
 * <p>
 *  Unicode脚本,块,类别和二进制属性用<tt> \\ p </tt>和<tt> \\ P </tt>结构写成Perl <tt> \\ p {</tt> <i> </i> <tt>} </tt>匹配
 * 如果输入具有属性<i> prop </i>,而<tt> \\ P {</tt> <i> prop </i> <tt> } </tt>不匹配,如果输入具有该属性。
 * <p>
 * 脚本,块,类别和二进制属性可以在字符类的内部和外部使用
 * 
 * <p>
 *  <b> <a name=\"usc\">脚本</a> </b>使用前缀{@code Is}指定,如{@code IsHiragana}或使用{@code script}关键字(或其简写形式{@code sc}
 * ),如{@code script = Hiragana}或{@code sc = Hiragana}。
 * <p>
 *  <code> Pattern </code>支持的脚本名称是由{@link javalangCharacterUnicodeScript#forName(String)UnicodeScriptforName}
 * 接受和定义的有效脚本名称。
 * 
 * <p>
 *  <b> <a name=\"ubc\">块</a> </b>使用前缀{@code In}指定,如{@code InMongolian}中所述,或使用关键字{@code block}或其简写形式{@code blk}
 * ),如{@code block = Mongolian}或{@code blk = Mongolian}。
 * <p>
 * <code> Pattern </code>支持的块名称是由{@link javalangCharacterUnicodeBlock#forName(String)UnicodeBlockforName}
 * 接受和定义的有效块名称。
 * <p>
 * 
 *  可以使用可选前缀{@code Is}指定<b> <a name=\"ucc\">类别</a> </b>：{@code \\ p {L}}和{@code \\ p { IsL}}表示Unicode字符的
 * 类别与脚本和块相同,类别也可以使用关键字{@code general_category}(或其简写形式{@code gc})指定,如{@code general_category = Lu}或{@code gc = Lu}
 * 。
 * <p>
 *  支持的类别是
 * <a href="http://www.unicode.org/unicode/standard/standard.html">
 *  <@> {@link javalangCharacter Character}类指定的版本中的Unicode标准</i> </a>类别名称是标准中定义的,包括规范性和信息性
 * <p>
 * 
 * <b> <a name=\"ubpc\">二进制属性</a> </b>使用前缀{@code Is}指定,如{@code IsAlphabetic}中所支持的二进制属性<code> Pattern <代码>
 * 。
 * <ul>
 *  <li>字母<li>字母<li>字母<li>小写字母<li>大写字母<li>标题<li>标点符号<li>控制<li> White_Space <li>数字<li> Hex_Digit <li> Joi
 * n_Control <li > Noncharacter_Code_Point <li>已分配。
 * </ul>
 * <p>
 *  以下<b>预定义字符类</b>和<b> POSIX字符类</b>符合<a href ="http： // wwwunicodeorg / reports / tr18 /"> <i> Unicode正
 * 则表达式</i> </a>,当指定{@link #UNICODE_CHARACTER_CLASS}标志时。
 * 
 *  <table border ="0"cellpadding ="1"cellspacing ="0"
 * summary="predefined and posix character classes in Unicode mode">
 * <tr align="left">
 * <th align ="left"id ="predef_classes">类</th> <th align ="left"id ="predef_matches">匹配</th>
 * /tr>
 * <tr> <td> <tt> \\ p {Lower} </tt> </td> <td>小写字符：<tt> \\ p {IsLowercase} </tt> </td> </tr> <td> <tt> 
 * \\ p {Upper} </tt> </td> <td>大写字符：<tt> \\ p {IsUppercase} </tt> </td> </tr> <tr > <td> <tt> \\ p {ASCII}
 *  </tt> </td> <td>所有ASCII：<tt> [\\ x00- \\ x7F] </tt> </td> > <td> <tt> \\ p {Alpha} </tt> </td> <td>字
 * 母字符：<tt> \\ p {IsAlphabetic} </tt> </td> <td> <tt> \\ p {Digit} </tt> </td> <td>十进制数字字符：<tt> p {IsDigit}
 *  </tt> </td> </tr> <tr>字母数字字符：<tt> [\\ p {IsAlphabetic} \\ p {IsDigit}] </tt> </td> </tr> <tr> <td> <tt>
 *  \\ p {Punct} </tt> </td> <td>标点符号：<tt> p {IsPunctuation} </tt> <tr> <td> <tt> \\ p {Graph} </tt> </td>
 *  <td>一个可见字元：<tt> [^ \\ p {IsWhite_Space} \\ p {gc = Cc} \\ p {gc = Cs} \\ p {gc = Cn}] </tt> </td> </tr>
 *  <tr> <td> <tt> \\ p {Print} </tt> </td> <td>可打印字符：{@code [\\ p {Graph} \\ p {Blank} && [^ \\ p {Cntrl}
 * ]]} </td> </<tr> <td> <tt> \\ p {Blank} </tt> </td> <td>空格或制表符：{@code [\\ p {IsWhite_Space} && [^ \\ 
 * p {gc = Zl} \\ p {gc = Zp} \\ x0a \\ x0b \\ x0c \\ x0d \\ x85]]} </td> </tr> <tr> <td> <tt> \\ p {Cntrl}
 *  </tt> <td>控制字符：<tt> \\ p {gc = Cc} </tt> </td> </tr> <tr> <td> <tt> \\ p {XDigit} </tt> > <td>十六进制数字
 * ：<tt> [\\ p {gc = Nd} \\ p {IsHex_Digit}] </tt> </td> </tr> <tr> <td> <tt> \\ p {Space } </tt> </td> 
 * <td>空格字符：<tt> \\ p {IsWhite_Space} </tt> </td> </tr> <tr> <td> <tt> \\ d </tt > </td> <td>数字：<tt> \\ 
 * p {IsDigit} </tt> </td> </tr> <tr> <td> <tt> \\ D </tt> <td>非数字：<tt> [^ \\ d] </tt> </td> </tr> <tr> 
 * <td> <tt> \\ s </tt>空格字符：<tt> \\ p {IsWhite_Space} </tt> </td> </tr> <tr> <td> <tt> \\ S </tt> </td> 
 * <td>字符：<tt> [^ \\ s] </tt> </td> </tr> <tr> <td> <tt> \\ w </tt> </td> <td> \\ p {Alpha} \\ p {gc = Mn}
 *  \\ p {gc = Me} \\ p {gc = Mc} \\ p {Digit} \\ p {gc = Pc} \\ p {IsJoin_Control}] </tt> td> <tt> [^ \
 * \ w] </tt> </td> </td> </td> </tr>。
 * </table>
 * <p>
 * <a name="jcc">
 * 类似javalangCharacter布尔值的类别是<i> methodname </i>方法(除了已弃用的方法)可通过相同的<tt> \\ p {</tt> <i> prop </i> <tt> } 
 * </tt>语法,其中指定的属性名称为<tt> java <i> methodname </i> </tt> </a>。
 * 
 *  <h3>与Perl 5的比较</h3>
 * 
 *  <p> <code> Pattern </code>引擎执行传统的基于NFA的匹配与有序交替,如Perl 5
 * 
 *  <p>此类不支持的Perl构造：</p>
 * 
 * <ul>
 *  <li> <p>预定义字符类(Unicode字符)<p> <tt> \\ X&nbsp;&nbsp;&nbsp;&nbsp; </tt>
 * <a href="http://www.unicode.org/reports/tr18/#Default_Grapheme_Clusters">
 *  <i>扩展字形集</i> </a> </p> </li>
 * 
 * <li> <p>对于<i> n </i> <sup> th的反向构造,<tt> \\ g {</tt> <i> n </i> <tt>} </href=\"#cg\">捕获组</a>和<t ht> </ht>
 *  </ht> </ht> </tt> <i> </i> <tt> ="#groupname">命名捕获组</a> </p> </li>。
 * 
 *  <li> <p>以Unicode名称命名的字符结构<tt> \\ N {</tt> <i> name </i> <tt>} </tt> li>
 * 
 *  <li> <p>条件结构<tt>(?(</tt> <i>条件</i> <tt>)</tt> <i> X </i> <tt>)</tt >和<tt>(</tt> <i>条件</i> <tt>)</tt>
 *  <i> X </i> <tt> | </tt> i> <tt>)</tt>,</p> </li>。
 * 
 *  <li> <p>嵌入代码构造<tt>(?{</tt> <i>代码</i> <tt>})</tt>和<tt>(?? {</tt> i> code </i> <tt>})</tt>,</p> </li>。
 * 
 *  <li> <p>嵌入的注释语法<tt>(?#comment)</tt>和</p> </li>
 * 
 * <li> <p>预处理操作<tt> \\ l </tt> <tt> \\ u </tt>,<tt> \\ L </tt>和<tt> \\ U </tt> p> </li>
 * 
 * </ul>
 * 
 *  <p>此类别支持但不受Perl支持的构造：</p>
 * 
 * <ul>
 * 
 *  <li> <p> <a href=\"#cc\">上述</a>的字符类结合和交集</p> </li>
 * 
 * </ul>
 * 
 *  <p>与Perl的显着差异：</p>
 * 
 * <ul>
 * 
 * <li> <p>在Perl中,<tt> \\ 1 </tt>至<tt> \\ 9 </tt>始终被解释为反引用;如果至少存在多个子表达式,则大于<tt> 9 </tt>的反斜杠转义数将被视为反向引用,否
 * 则将其解释为八进制转义在此类中,八进制转义必须始终以a零在此类中,<tt> \\ 1 </tt>到<tt> \\ 9 </tt>始终被解释为反向引用,如果至少存在多个子表达式,则接受更大的数作为反向引用
 * 那么指向正则表达式,否则解析器将删除数字,直到数字小于或等于现有的组数,或者它是一个数字</p> </li>。
 * 
 * <li> <p> Perl使用<tt> g </tt>标志请求恢复最后一次匹配的匹配此功能由{@link Matcher}类隐式提供：重复调用{@除非匹配器被重置,否则链接匹配器#find find}方
 * 法将在最后一个匹配剩余的位置恢复。
 * </p> </li>。
 * 
 *  <li> <p>在Perl中,表达式顶层的嵌入式标志会影响整个表达式在此类中,嵌入式标志始终在它们出现的时刻生效,无论它们是在顶层还是在组内;在后一种情况下,标志在组的末尾恢复,如在Perl </p>
 *  </li>中。
 * 
 * </ul>
 * 
 * <p>有关正则表达式构造的行为的更准确描述,请参见<a href=\"http://wwworeillycom/catalog/regex3/\"> <i>掌握正则表达式,第3版</i>,Jeffrey
 *  EF Friedl,O'Reilly and Associates,2006 </a>。
 * </p>
 * 
 * 
 * @see java.lang.String#split(String, int)
 * @see java.lang.String#split(String)
 *
 * @author      Mike McCloskey
 * @author      Mark Reinhold
 * @author      JSR-51 Expert Group
 * @since       1.4
 * @spec        JSR-51
 */

public final class Pattern
    implements java.io.Serializable
{

    /**
     * Regular expression modifier values.  Instead of being passed as
     * arguments, they can also be passed as inline modifiers.
     * For example, the following statements have the same effect.
     * <pre>
     * RegExp r1 = RegExp.compile("abc", Pattern.I|Pattern.M);
     * RegExp r2 = RegExp.compile("(?im)abc", 0);
     * </pre>
     *
     * The flags are duplicated so that the familiar Perl match flag
     * names are available.
     * <p>
     *  正则表达式修饰符值除了作为参数传递之外,它们还可以作为内联修饰符传递。例如,以下语句具有相同的效果
     * <pre>
     *  RegExp r1 = RegExpcompile("abc",PatternI | PatternM); RegExp r2 = RegExpcompile("(?im)abc",0);
     * </pre>
     * 
     *  这些标志是重复的,以便熟悉的Perl匹配标志名称可用
     * 
     */

    /**
     * Enables Unix lines mode.
     *
     * <p> In this mode, only the <tt>'\n'</tt> line terminator is recognized
     * in the behavior of <tt>.</tt>, <tt>^</tt>, and <tt>$</tt>.
     *
     * <p> Unix lines mode can also be enabled via the embedded flag
     * expression&nbsp;<tt>(?d)</tt>.
     * <p>
     *  启用Unix行模式
     * 
     *  <p>在此模式下,只有<tt>'\n'</tt>行终止符在<tt> </tt>,<tt> ^ </tt>和<tt> $ </tt>
     * 
     * <p> Unix行模式也可以通过嵌入的标志表达式启用<tt>(?d)</tt>
     * 
     */
    public static final int UNIX_LINES = 0x01;

    /**
     * Enables case-insensitive matching.
     *
     * <p> By default, case-insensitive matching assumes that only characters
     * in the US-ASCII charset are being matched.  Unicode-aware
     * case-insensitive matching can be enabled by specifying the {@link
     * #UNICODE_CASE} flag in conjunction with this flag.
     *
     * <p> Case-insensitive matching can also be enabled via the embedded flag
     * expression&nbsp;<tt>(?i)</tt>.
     *
     * <p> Specifying this flag may impose a slight performance penalty.  </p>
     * <p>
     *  启用不区分大小写的匹配
     * 
     *  <p>默认情况下,不区分大小写的匹配假设只有US-ASCII字符集中的字符正在匹配可以通过与此标志结合指定{@link #UNICODE_CASE}标志来启用符合Unicode的大小写不敏感匹配
     * 
     *  <p>不区分大小写的匹配也可以通过嵌入的标记表达式启用<tt>(?i)</tt>
     * 
     *  <p>指定此标记可能会造成轻微的性能损失</p>
     * 
     */
    public static final int CASE_INSENSITIVE = 0x02;

    /**
     * Permits whitespace and comments in pattern.
     *
     * <p> In this mode, whitespace is ignored, and embedded comments starting
     * with <tt>#</tt> are ignored until the end of a line.
     *
     * <p> Comments mode can also be enabled via the embedded flag
     * expression&nbsp;<tt>(?x)</tt>.
     * <p>
     *  允许在模式中的空格和注释
     * 
     *  <p>在此模式下,忽略空格,并且忽略从<tt>#</tt>开始的嵌入注释,直到行结束
     * 
     * <p>评论模式也可以通过嵌入的标志表达式启用<tt>(?x)</tt>
     * 
     */
    public static final int COMMENTS = 0x04;

    /**
     * Enables multiline mode.
     *
     * <p> In multiline mode the expressions <tt>^</tt> and <tt>$</tt> match
     * just after or just before, respectively, a line terminator or the end of
     * the input sequence.  By default these expressions only match at the
     * beginning and the end of the entire input sequence.
     *
     * <p> Multiline mode can also be enabled via the embedded flag
     * expression&nbsp;<tt>(?m)</tt>.  </p>
     * <p>
     *  启用多行模式
     * 
     *  <p>在多行模式中,表达式<tt> ^ </tt>和<tt> $ </tt>分别匹配行终止符或输入序列的结尾之前或之前。默认情况下,这些表达式仅匹配在整个输入序列的开始和结束
     * 
     *  <p>多行模式也可以通过嵌入的标志表达式启用<tt>(?m)</tt> </p>
     * 
     */
    public static final int MULTILINE = 0x08;

    /**
     * Enables literal parsing of the pattern.
     *
     * <p> When this flag is specified then the input string that specifies
     * the pattern is treated as a sequence of literal characters.
     * Metacharacters or escape sequences in the input sequence will be
     * given no special meaning.
     *
     * <p>The flags CASE_INSENSITIVE and UNICODE_CASE retain their impact on
     * matching when used in conjunction with this flag. The other flags
     * become superfluous.
     *
     * <p> There is no embedded flag character for enabling literal parsing.
     * <p>
     *  启用模式的文字解析
     * 
     *  <p>当指定此标志时,指定模式的输入字符串将被视为字符字符序列。输入序列中的字符或转义序列将没有特殊含义
     * 
     * <p>标志CASE_INSENSITIVE和UNICODE_CASE在与此标志结合使用时保留对匹配的影响其他标志变得多余
     * 
     *  <p>没有用于启用文字解析的嵌入标志字符
     * 
     * 
     * @since 1.5
     */
    public static final int LITERAL = 0x10;

    /**
     * Enables dotall mode.
     *
     * <p> In dotall mode, the expression <tt>.</tt> matches any character,
     * including a line terminator.  By default this expression does not match
     * line terminators.
     *
     * <p> Dotall mode can also be enabled via the embedded flag
     * expression&nbsp;<tt>(?s)</tt>.  (The <tt>s</tt> is a mnemonic for
     * "single-line" mode, which is what this is called in Perl.)  </p>
     * <p>
     *  启用dotall模式
     * 
     *  <p>在dotall模式下,表达式<tt> </tt>匹配任何字符,包括行终止符默认情况下,此表达式不匹配行终止符
     * 
     *  <p> Dotall模式也可以通过嵌入的标志表达式启用<tt>(?s)</tt>(<tt> s </tt>是"单行"模式的助记符,这在Perl中调用)</p>
     * 
     */
    public static final int DOTALL = 0x20;

    /**
     * Enables Unicode-aware case folding.
     *
     * <p> When this flag is specified then case-insensitive matching, when
     * enabled by the {@link #CASE_INSENSITIVE} flag, is done in a manner
     * consistent with the Unicode Standard.  By default, case-insensitive
     * matching assumes that only characters in the US-ASCII charset are being
     * matched.
     *
     * <p> Unicode-aware case folding can also be enabled via the embedded flag
     * expression&nbsp;<tt>(?u)</tt>.
     *
     * <p> Specifying this flag may impose a performance penalty.  </p>
     * <p>
     *  启用支持Unicode的大小写折叠
     * 
     * <p>当指定此标志时,通过{@link #CASE_INSENSITIVE}标志启用时,不区分大小写的匹配以与Unicode标准一致的方式完成。
     * 默认情况下,不区分大小写的匹配假设只有美国-ASCII字符集正在匹配。
     * 
     *  <p>也可以通过嵌入的标志表达式启用Unicode感知的大小写折叠<tt>(?u)</tt>
     * 
     *  <p>指定此标志可能会造成性能损失</p>
     * 
     */
    public static final int UNICODE_CASE = 0x40;

    /**
     * Enables canonical equivalence.
     *
     * <p> When this flag is specified then two characters will be considered
     * to match if, and only if, their full canonical decompositions match.
     * The expression <tt>"a&#92;u030A"</tt>, for example, will match the
     * string <tt>"&#92;u00E5"</tt> when this flag is specified.  By default,
     * matching does not take canonical equivalence into account.
     *
     * <p> There is no embedded flag character for enabling canonical
     * equivalence.
     *
     * <p> Specifying this flag may impose a performance penalty.  </p>
     * <p>
     *  启用规范等价
     * 
     * <p>当指定此标志时,如果且仅当它们的完全规范分解匹配,那么两个字符将被视为匹配。
     * 例如,表达式<tt>"a \\ u030A"</tt>将匹配字符串< tt>"\\ u00E5"</tt>当指定此标志时默认情况下,匹配不考虑规范等效。
     * 
     *  <p>没有用于启用规范等效的嵌入标志字符
     * 
     *  <p>指定此标志可能会造成性能损失</p>
     * 
     */
    public static final int CANON_EQ = 0x80;

    /**
     * Enables the Unicode version of <i>Predefined character classes</i> and
     * <i>POSIX character classes</i>.
     *
     * <p> When this flag is specified then the (US-ASCII only)
     * <i>Predefined character classes</i> and <i>POSIX character classes</i>
     * are in conformance with
     * <a href="http://www.unicode.org/reports/tr18/"><i>Unicode Technical
     * Standard #18: Unicode Regular Expression</i></a>
     * <i>Annex C: Compatibility Properties</i>.
     * <p>
     * The UNICODE_CHARACTER_CLASS mode can also be enabled via the embedded
     * flag expression&nbsp;<tt>(?U)</tt>.
     * <p>
     * The flag implies UNICODE_CASE, that is, it enables Unicode-aware case
     * folding.
     * <p>
     * Specifying this flag may impose a performance penalty.  </p>
     * <p>
     *  启用<i>预定义字符类</i>和<i> POSIX字符类的Unicode版本</i>
     * 
     * <p>当指定此标志时,(仅限US-ASCII)<i>预定义字符类</i>和<i> POSIX字符类</i>符合<a href ="http：// wwwunicodeorg / reports / tr18 /">
     *  <i> Unicode技术标准#18：Unicode正则表达式</i> </a>附件C：兼容性属性</i>。
     * <p>
     *  UNICODE_CHARACTER_CLASS模式也可以通过嵌入的标志表达式启用<tt>(?U)</tt>
     * <p>
     *  该标志意味着UNICODE_CASE,也就是说,它启用Unicode感知的案例折叠
     * <p>
     *  指定此标志可能会造成性能损失</p>
     * 
     * 
     * @since 1.7
     */
    public static final int UNICODE_CHARACTER_CLASS = 0x100;

    /* Pattern has only two serialized components: The pattern string
     * and the flags, which are all that is needed to recompile the pattern
     * when it is deserialized.
     * <p>
     *  和标志,这些都是在反序列化时重新编译模式所需要的
     * 
     */

    /** use serialVersionUID from Merlin b59 for interoperability */
    private static final long serialVersionUID = 5073258162644648461L;

    /**
     * The original regular-expression pattern string.
     *
     * <p>
     *  原始的正则表达式模式字符串
     * 
     * 
     * @serial
     */
    private String pattern;

    /**
     * The original pattern flags.
     *
     * <p>
     *  原始模式标志
     * 
     * 
     * @serial
     */
    private int flags;

    /**
     * Boolean indicating this Pattern is compiled; this is necessary in order
     * to lazily compile deserialized Patterns.
     * <p>
     * 指示此Pattern的布尔值被编译;这是必要的,以便懒惰编译反序列化模式
     * 
     */
    private transient volatile boolean compiled = false;

    /**
     * The normalized pattern string.
     * <p>
     *  标准化模式字符串
     * 
     */
    private transient String normalizedPattern;

    /**
     * The starting point of state machine for the find operation.  This allows
     * a match to start anywhere in the input.
     * <p>
     *  查找操作的状态机的起始点这允许匹配在输入中的任何位置开始
     * 
     */
    transient Node root;

    /**
     * The root of object tree for a match operation.  The pattern is matched
     * at the beginning.  This may include a find that uses BnM or a First
     * node.
     * <p>
     *  匹配操作的对象树的根模式在开始时匹配可以包括使用BnM或第一个节点的find
     * 
     */
    transient Node matchRoot;

    /**
     * Temporary storage used by parsing pattern slice.
     * <p>
     *  解析模式切片使用的临时存储
     * 
     */
    transient int[] buffer;

    /**
     * Map the "name" of the "named capturing group" to its group id
     * node.
     * <p>
     *  将"命名捕获组"的"名称"映射到其组id节点
     * 
     */
    transient volatile Map<String, Integer> namedGroups;

    /**
     * Temporary storage used while parsing group references.
     * <p>
     *  解析组引用时使用的临时存储
     * 
     */
    transient GroupHead[] groupNodes;

    /**
     * Temporary null terminated code point array used by pattern compiling.
     * <p>
     *  模式编译使用的临时空终止代码点数组
     * 
     */
    private transient int[] temp;

    /**
     * The number of capturing groups in this Pattern. Used by matchers to
     * allocate storage needed to perform a match.
     * <p>
     *  此模式中捕获组的数量由匹配器用于分配执行匹配所需的存储空间
     * 
     */
    transient int capturingGroupCount;

    /**
     * The local variable count used by parsing tree. Used by matchers to
     * allocate storage needed to perform a match.
     * <p>
     * 解析树使用的本地变量计数由匹配器用于分配执行匹配所需的存储
     * 
     */
    transient int localCount;

    /**
     * Index into the pattern string that keeps track of how much has been
     * parsed.
     * <p>
     *  索引到模式字符串,跟踪已经解析的数量
     * 
     */
    private transient int cursor;

    /**
     * Holds the length of the pattern string.
     * <p>
     *  保留模式字符串的长度
     * 
     */
    private transient int patternLength;

    /**
     * If the Start node might possibly match supplementary characters.
     * It is set to true during compiling if
     * (1) There is supplementary char in pattern, or
     * (2) There is complement node of Category or Block
     * <p>
     *  如果Start节点可能匹配补充字符,则在编译期间设置为true(1)在模式中有补充字符,或(2)类别或块的补充节点
     * 
     */
    private transient boolean hasSupplementary;

    /**
     * Compiles the given regular expression into a pattern.
     *
     * <p>
     *  将给定的正则表达式编译为模式
     * 
     * 
     * @param  regex
     *         The expression to be compiled
     * @return the given regular expression compiled into a pattern
     * @throws  PatternSyntaxException
     *          If the expression's syntax is invalid
     */
    public static Pattern compile(String regex) {
        return new Pattern(regex, 0);
    }

    /**
     * Compiles the given regular expression into a pattern with the given
     * flags.
     *
     * <p>
     *  将给定的正则表达式编译为具有给定标志的模式
     * 
     * 
     * @param  regex
     *         The expression to be compiled
     *
     * @param  flags
     *         Match flags, a bit mask that may include
     *         {@link #CASE_INSENSITIVE}, {@link #MULTILINE}, {@link #DOTALL},
     *         {@link #UNICODE_CASE}, {@link #CANON_EQ}, {@link #UNIX_LINES},
     *         {@link #LITERAL}, {@link #UNICODE_CHARACTER_CLASS}
     *         and {@link #COMMENTS}
     *
     * @return the given regular expression compiled into a pattern with the given flags
     * @throws  IllegalArgumentException
     *          If bit values other than those corresponding to the defined
     *          match flags are set in <tt>flags</tt>
     *
     * @throws  PatternSyntaxException
     *          If the expression's syntax is invalid
     */
    public static Pattern compile(String regex, int flags) {
        return new Pattern(regex, flags);
    }

    /**
     * Returns the regular expression from which this pattern was compiled.
     *
     * <p>
     *  返回编译此模式的正则表达式
     * 
     * 
     * @return  The source of this pattern
     */
    public String pattern() {
        return pattern;
    }

    /**
     * <p>Returns the string representation of this pattern. This
     * is the regular expression from which this pattern was
     * compiled.</p>
     *
     * <p>
     *  <p>返回此模式的字符串表示形式这是编译此模式的正则表达式</p>
     * 
     * 
     * @return  The string representation of this pattern
     * @since 1.5
     */
    public String toString() {
        return pattern;
    }

    /**
     * Creates a matcher that will match the given input against this pattern.
     *
     * <p>
     * 创建匹配此模式的给定输入的匹配器
     * 
     * 
     * @param  input
     *         The character sequence to be matched
     *
     * @return  A new matcher for this pattern
     */
    public Matcher matcher(CharSequence input) {
        if (!compiled) {
            synchronized(this) {
                if (!compiled)
                    compile();
            }
        }
        Matcher m = new Matcher(this, input);
        return m;
    }

    /**
     * Returns this pattern's match flags.
     *
     * <p>
     *  返回此模式的匹配标志
     * 
     * 
     * @return  The match flags specified when this pattern was compiled
     */
    public int flags() {
        return flags;
    }

    /**
     * Compiles the given regular expression and attempts to match the given
     * input against it.
     *
     * <p> An invocation of this convenience method of the form
     *
     * <blockquote><pre>
     * Pattern.matches(regex, input);</pre></blockquote>
     *
     * behaves in exactly the same way as the expression
     *
     * <blockquote><pre>
     * Pattern.compile(regex).matcher(input).matches()</pre></blockquote>
     *
     * <p> If a pattern is to be used multiple times, compiling it once and reusing
     * it will be more efficient than invoking this method each time.  </p>
     *
     * <p>
     *  编译给定的正则表达式,并尝试匹配给定的输入
     * 
     *  <p>表单的这个方便方法的调用
     * 
     *  <blockquote> <pre> Patternmatches(regex,input); </pre> </blockquote>
     * 
     *  表现方式与表达式完全相同
     * 
     *  <blockquote> <pre> Patterncompile(regex)matcher(input)matches()</pre> </blockquote>
     * 
     *  <p>如果一个模式要被多次使用,编译一次并重用它将比每次调用此方法更有效</p>
     * 
     * 
     * @param  regex
     *         The expression to be compiled
     *
     * @param  input
     *         The character sequence to be matched
     * @return whether or not the regular expression matches on the input
     * @throws  PatternSyntaxException
     *          If the expression's syntax is invalid
     */
    public static boolean matches(String regex, CharSequence input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * Splits the given input sequence around matches of this pattern.
     *
     * <p> The array returned by this method contains each substring of the
     * input sequence that is terminated by another subsequence that matches
     * this pattern or is terminated by the end of the input sequence.  The
     * substrings in the array are in the order in which they occur in the
     * input. If this pattern does not match any subsequence of the input then
     * the resulting array has just one element, namely the input sequence in
     * string form.
     *
     * <p> When there is a positive-width match at the beginning of the input
     * sequence then an empty leading substring is included at the beginning
     * of the resulting array. A zero-width match at the beginning however
     * never produces such empty leading substring.
     *
     * <p> The <tt>limit</tt> parameter controls the number of times the
     * pattern is applied and therefore affects the length of the resulting
     * array.  If the limit <i>n</i> is greater than zero then the pattern
     * will be applied at most <i>n</i>&nbsp;-&nbsp;1 times, the array's
     * length will be no greater than <i>n</i>, and the array's last entry
     * will contain all input beyond the last matched delimiter.  If <i>n</i>
     * is non-positive then the pattern will be applied as many times as
     * possible and the array can have any length.  If <i>n</i> is zero then
     * the pattern will be applied as many times as possible, the array can
     * have any length, and trailing empty strings will be discarded.
     *
     * <p> The input <tt>"boo:and:foo"</tt>, for example, yields the following
     * results with these parameters:
     *
     * <blockquote><table cellpadding=1 cellspacing=0
     *              summary="Split examples showing regex, limit, and result">
     * <tr><th align="left"><i>Regex&nbsp;&nbsp;&nbsp;&nbsp;</i></th>
     *     <th align="left"><i>Limit&nbsp;&nbsp;&nbsp;&nbsp;</i></th>
     *     <th align="left"><i>Result&nbsp;&nbsp;&nbsp;&nbsp;</i></th></tr>
     * <tr><td align=center>:</td>
     *     <td align=center>2</td>
     *     <td><tt>{ "boo", "and:foo" }</tt></td></tr>
     * <tr><td align=center>:</td>
     *     <td align=center>5</td>
     *     <td><tt>{ "boo", "and", "foo" }</tt></td></tr>
     * <tr><td align=center>:</td>
     *     <td align=center>-2</td>
     *     <td><tt>{ "boo", "and", "foo" }</tt></td></tr>
     * <tr><td align=center>o</td>
     *     <td align=center>5</td>
     *     <td><tt>{ "b", "", ":and:f", "", "" }</tt></td></tr>
     * <tr><td align=center>o</td>
     *     <td align=center>-2</td>
     *     <td><tt>{ "b", "", ":and:f", "", "" }</tt></td></tr>
     * <tr><td align=center>o</td>
     *     <td align=center>0</td>
     *     <td><tt>{ "b", "", ":and:f" }</tt></td></tr>
     * </table></blockquote>
     *
     * <p>
     *  围绕该模式的匹配分割给定的输入序列
     * 
     * <p>此方法返回的数组包含输入序列的每个子字符串,由匹配此模式的另一个子序列终止,或由输入序列的末尾终止。
     * 数组中的子字符串按照它们出现的顺序输入如果此模式不匹配输入的任何子序列,则结果数组只有一个元素,即字符串形式的输入序列。
     * 
     *  <p>当在输入序列的开头有正宽度匹配时,在结果数组的开头包含一个空的前导子串。开头的零宽度匹配从不产生这样的空引导子串
     * 
     * <p> <tt> limit </tt>参数控制应用模式的次数,因此影响结果数组的长度如果limit <i> n </i>大于零,则模式将最多应用<i> </i>  -  1次,数组的长度不会大于<i>
     *  n,并且数组的最后一个条目将包含除最后匹配之外的所有输入分隔符如果<i> n </i>是非正的,则该模式将被应用尽可能多的次数,并且该阵列可以具有任何长度。
     * 如果<n> n <0>那么该模式将被应用为尽可能多次,该数组可以有任意长度,并且尾随的空字符串将被丢弃。
     * 
     *  <p>例如,输入<tt>"boo：and：foo"</tt>会使用以下参数生成以下结果：
     * 
     * <blockquote> <table cellpadding = 1 cellspacing = 0
     * summary="Split examples showing regex, limit, and result">
     * <tr> <th align ="left"> <i>正则表达式&nbsp;&nbsp;&nbsp;&nbsp; </i> </th> <th align ="left"> <i> Limit&nbsp
     * ;&nbsp;&nbsp;&nbsp; / i> </th> <th align ="left"> </i> </b> td> <td align = center> 2 </td> <td> <tt>
     *  {"boo","and：foo"} </tt> </td> </tr> <tr> <td align = center >：</td> <td align = center> 5 </td> <td>
     *  <tt> {"boo","and","foo"} </tt> </td> </tr> <tr > <td align = center>：</td> <td align = center> -2 </td>
     *  <td> <tt> {"boo","and","foo"} </tt> </td > </tr> <tr> <td align = center> o </td> <td align = center>
     *  5 </td> <td> <tt> {"b","","：and：f" ,"",""} </tt> </td> </tr> <tr> <td align = center> o </td> <td align = center>
     *  -2 </td> <td> <tt > {"b","","：and：f","",""} </tt> </td> </tr> <tr> <td align = center> o </td> <td align = center>
     *  0 </td> <td> <tt> {"b","","：and：f"} </tt> </td> <// blockquote>。
     * 
     * 
     * @param  input
     *         The character sequence to be split
     *
     * @param  limit
     *         The result threshold, as described above
     *
     * @return  The array of strings computed by splitting the input
     *          around matches of this pattern
     */
    public String[] split(CharSequence input, int limit) {
        int index = 0;
        boolean matchLimited = limit > 0;
        ArrayList<String> matchList = new ArrayList<>();
        Matcher m = matcher(input);

        // Add segments before each match found
        while(m.find()) {
            if (!matchLimited || matchList.size() < limit - 1) {
                if (index == 0 && index == m.start() && m.start() == m.end()) {
                    // no empty leading substring included for zero-width match
                    // at the beginning of the input char sequence.
                    continue;
                }
                String match = input.subSequence(index, m.start()).toString();
                matchList.add(match);
                index = m.end();
            } else if (matchList.size() == limit - 1) { // last one
                String match = input.subSequence(index,
                                                 input.length()).toString();
                matchList.add(match);
                index = m.end();
            }
        }

        // If no match was found, return this
        if (index == 0)
            return new String[] {input.toString()};

        // Add remaining segment
        if (!matchLimited || matchList.size() < limit)
            matchList.add(input.subSequence(index, input.length()).toString());

        // Construct result
        int resultSize = matchList.size();
        if (limit == 0)
            while (resultSize > 0 && matchList.get(resultSize-1).equals(""))
                resultSize--;
        String[] result = new String[resultSize];
        return matchList.subList(0, resultSize).toArray(result);
    }

    /**
     * Splits the given input sequence around matches of this pattern.
     *
     * <p> This method works as if by invoking the two-argument {@link
     * #split(java.lang.CharSequence, int) split} method with the given input
     * sequence and a limit argument of zero.  Trailing empty strings are
     * therefore not included in the resulting array. </p>
     *
     * <p> The input <tt>"boo:and:foo"</tt>, for example, yields the following
     * results with these expressions:
     *
     * <blockquote><table cellpadding=1 cellspacing=0
     *              summary="Split examples showing regex and result">
     * <tr><th align="left"><i>Regex&nbsp;&nbsp;&nbsp;&nbsp;</i></th>
     *     <th align="left"><i>Result</i></th></tr>
     * <tr><td align=center>:</td>
     *     <td><tt>{ "boo", "and", "foo" }</tt></td></tr>
     * <tr><td align=center>o</td>
     *     <td><tt>{ "b", "", ":and:f" }</tt></td></tr>
     * </table></blockquote>
     *
     *
     * <p>
     * 围绕该模式的匹配分割给定的输入序列
     * 
     *  <p>此方法的工作原理是通过调用具有给定输入序列的双参数{@link #split(javalangCharSequence,int)split}方法,并且限制参数为零。
     * 因此,尾随空字符串不包括在结果数组中< / p>。
     * 
     *  <p>例如,输入<tt>"boo：and：foo"</tt>会使用这些表达式产生以下结果：
     * 
     *  <blockquote> <table cellpadding = 1 cellspacing = 0
     * summary="Split examples showing regex and result">
     *  <tr> <th> <th> </i> </th> <th align ="left"> <i>正确格式</i> </</tr> <tr> <td align = center>：</td> <td>
     *  <tt> {"boo","and","foo"} </tt> </td> </tr> <td align = center> o </td> <td> <tt> {"b","","：and：f"} </tt>
     *  </td> </blockquote>。
     * 
     * 
     * @param  input
     *         The character sequence to be split
     *
     * @return  The array of strings computed by splitting the input
     *          around matches of this pattern
     */
    public String[] split(CharSequence input) {
        return split(input, 0);
    }

    /**
     * Returns a literal pattern <code>String</code> for the specified
     * <code>String</code>.
     *
     * <p>This method produces a <code>String</code> that can be used to
     * create a <code>Pattern</code> that would match the string
     * <code>s</code> as if it were a literal pattern.</p> Metacharacters
     * or escape sequences in the input sequence will be given no special
     * meaning.
     *
     * <p>
     * 返回指定<code> String </code>的文字模式<code> String </code>
     * 
     *  <p>此方法产生可用于创建<code> Pattern </code>的<code> String </code>,该<code> Pattern </code>匹配字符串<code> s </code>
     * ,如同它是一个文字模式</p>输入序列中的字符或转义序列没有特殊意义。
     * 
     * 
     * @param  s The string to be literalized
     * @return  A literal string replacement
     * @since 1.5
     */
    public static String quote(String s) {
        int slashEIndex = s.indexOf("\\E");
        if (slashEIndex == -1)
            return "\\Q" + s + "\\E";

        StringBuilder sb = new StringBuilder(s.length() * 2);
        sb.append("\\Q");
        slashEIndex = 0;
        int current = 0;
        while ((slashEIndex = s.indexOf("\\E", current)) != -1) {
            sb.append(s.substring(current, slashEIndex));
            current = slashEIndex + 2;
            sb.append("\\E\\\\E\\Q");
        }
        sb.append(s.substring(current, s.length()));
        sb.append("\\E");
        return sb.toString();
    }

    /**
     * Recompile the Pattern instance from a stream.  The original pattern
     * string is read in and the object tree is recompiled from it.
     * <p>
     *  从流重新编译Pattern实例读入原始模式字符串,并从中重新编译对象树
     * 
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {

        // Read in all fields
        s.defaultReadObject();

        // Initialize counts
        capturingGroupCount = 1;
        localCount = 0;

        // if length > 0, the Pattern is lazily compiled
        compiled = false;
        if (pattern.length() == 0) {
            root = new Start(lastAccept);
            matchRoot = lastAccept;
            compiled = true;
        }
    }

    /**
     * This private constructor is used to create all Patterns. The pattern
     * string and match flags are all that is needed to completely describe
     * a Pattern. An empty pattern string results in an object tree with
     * only a Start node and a LastNode node.
     * <p>
     *  这个私有构造函数用于创建所有模式模式字符串和匹配标志是完全描述模式所需要的所有模式一个空模式字符串导致一个只有一个Start节点和一个LastNode节点的对象树
     * 
     */
    private Pattern(String p, int f) {
        pattern = p;
        flags = f;

        // to use UNICODE_CASE if UNICODE_CHARACTER_CLASS present
        if ((flags & UNICODE_CHARACTER_CLASS) != 0)
            flags |= UNICODE_CASE;

        // Reset group index count
        capturingGroupCount = 1;
        localCount = 0;

        if (pattern.length() > 0) {
            compile();
        } else {
            root = new Start(lastAccept);
            matchRoot = lastAccept;
        }
    }

    /**
     * The pattern is converted to normalizedD form and then a pure group
     * is constructed to match canonical equivalences of the characters.
     * <p>
     * 将模式转换为归一化形式,然后构建纯组以匹配字符的规范等效
     * 
     */
    private void normalize() {
        boolean inCharClass = false;
        int lastCodePoint = -1;

        // Convert pattern into normalizedD form
        normalizedPattern = Normalizer.normalize(pattern, Normalizer.Form.NFD);
        patternLength = normalizedPattern.length();

        // Modify pattern to match canonical equivalences
        StringBuilder newPattern = new StringBuilder(patternLength);
        for(int i=0; i<patternLength; ) {
            int c = normalizedPattern.codePointAt(i);
            StringBuilder sequenceBuffer;
            if ((Character.getType(c) == Character.NON_SPACING_MARK)
                && (lastCodePoint != -1)) {
                sequenceBuffer = new StringBuilder();
                sequenceBuffer.appendCodePoint(lastCodePoint);
                sequenceBuffer.appendCodePoint(c);
                while(Character.getType(c) == Character.NON_SPACING_MARK) {
                    i += Character.charCount(c);
                    if (i >= patternLength)
                        break;
                    c = normalizedPattern.codePointAt(i);
                    sequenceBuffer.appendCodePoint(c);
                }
                String ea = produceEquivalentAlternation(
                                               sequenceBuffer.toString());
                newPattern.setLength(newPattern.length()-Character.charCount(lastCodePoint));
                newPattern.append("(?:").append(ea).append(")");
            } else if (c == '[' && lastCodePoint != '\\') {
                i = normalizeCharClass(newPattern, i);
            } else {
                newPattern.appendCodePoint(c);
            }
            lastCodePoint = c;
            i += Character.charCount(c);
        }
        normalizedPattern = newPattern.toString();
    }

    /**
     * Complete the character class being parsed and add a set
     * of alternations to it that will match the canonical equivalences
     * of the characters within the class.
     * <p>
     *  完成要解析的字符类,并向其中添加一组替换字符,以匹配类中字符的规范等价
     * 
     */
    private int normalizeCharClass(StringBuilder newPattern, int i) {
        StringBuilder charClass = new StringBuilder();
        StringBuilder eq = null;
        int lastCodePoint = -1;
        String result;

        i++;
        charClass.append("[");
        while(true) {
            int c = normalizedPattern.codePointAt(i);
            StringBuilder sequenceBuffer;

            if (c == ']' && lastCodePoint != '\\') {
                charClass.append((char)c);
                break;
            } else if (Character.getType(c) == Character.NON_SPACING_MARK) {
                sequenceBuffer = new StringBuilder();
                sequenceBuffer.appendCodePoint(lastCodePoint);
                while(Character.getType(c) == Character.NON_SPACING_MARK) {
                    sequenceBuffer.appendCodePoint(c);
                    i += Character.charCount(c);
                    if (i >= normalizedPattern.length())
                        break;
                    c = normalizedPattern.codePointAt(i);
                }
                String ea = produceEquivalentAlternation(
                                                  sequenceBuffer.toString());

                charClass.setLength(charClass.length()-Character.charCount(lastCodePoint));
                if (eq == null)
                    eq = new StringBuilder();
                eq.append('|');
                eq.append(ea);
            } else {
                charClass.appendCodePoint(c);
                i++;
            }
            if (i == normalizedPattern.length())
                throw error("Unclosed character class");
            lastCodePoint = c;
        }

        if (eq != null) {
            result = "(?:"+charClass.toString()+eq.toString()+")";
        } else {
            result = charClass.toString();
        }

        newPattern.append(result);
        return i;
    }

    /**
     * Given a specific sequence composed of a regular character and
     * combining marks that follow it, produce the alternation that will
     * match all canonical equivalences of that sequence.
     * <p>
     *  给定由正则字符和其后的组合标记组成的特定序列,产生将匹配该序列的所有规范等价的交替
     * 
     */
    private String produceEquivalentAlternation(String source) {
        int len = countChars(source, 0, 1);
        if (source.length() == len)
            // source has one character.
            return source;

        String base = source.substring(0,len);
        String combiningMarks = source.substring(len);

        String[] perms = producePermutations(combiningMarks);
        StringBuilder result = new StringBuilder(source);

        // Add combined permutations
        for(int x=0; x<perms.length; x++) {
            String next = base + perms[x];
            if (x>0)
                result.append("|"+next);
            next = composeOneStep(next);
            if (next != null)
                result.append("|"+produceEquivalentAlternation(next));
        }
        return result.toString();
    }

    /**
     * Returns an array of strings that have all the possible
     * permutations of the characters in the input string.
     * This is used to get a list of all possible orderings
     * of a set of combining marks. Note that some of the permutations
     * are invalid because of combining class collisions, and these
     * possibilities must be removed because they are not canonically
     * equivalent.
     * <p>
     * 返回一个字符串数组,其中包含输入字符串中所有可能的字符排列。这用于获取一组合并标记的所有可能排序的列表请注意,由于组合类冲突,一些排列无效,这些可能性必须被去除,因为它们不是典型地等同的
     * 
     */
    private String[] producePermutations(String input) {
        if (input.length() == countChars(input, 0, 1))
            return new String[] {input};

        if (input.length() == countChars(input, 0, 2)) {
            int c0 = Character.codePointAt(input, 0);
            int c1 = Character.codePointAt(input, Character.charCount(c0));
            if (getClass(c1) == getClass(c0)) {
                return new String[] {input};
            }
            String[] result = new String[2];
            result[0] = input;
            StringBuilder sb = new StringBuilder(2);
            sb.appendCodePoint(c1);
            sb.appendCodePoint(c0);
            result[1] = sb.toString();
            return result;
        }

        int length = 1;
        int nCodePoints = countCodePoints(input);
        for(int x=1; x<nCodePoints; x++)
            length = length * (x+1);

        String[] temp = new String[length];

        int combClass[] = new int[nCodePoints];
        for(int x=0, i=0; x<nCodePoints; x++) {
            int c = Character.codePointAt(input, i);
            combClass[x] = getClass(c);
            i +=  Character.charCount(c);
        }

        // For each char, take it out and add the permutations
        // of the remaining chars
        int index = 0;
        int len;
        // offset maintains the index in code units.
loop:   for(int x=0, offset=0; x<nCodePoints; x++, offset+=len) {
            len = countChars(input, offset, 1);
            boolean skip = false;
            for(int y=x-1; y>=0; y--) {
                if (combClass[y] == combClass[x]) {
                    continue loop;
                }
            }
            StringBuilder sb = new StringBuilder(input);
            String otherChars = sb.delete(offset, offset+len).toString();
            String[] subResult = producePermutations(otherChars);

            String prefix = input.substring(offset, offset+len);
            for(int y=0; y<subResult.length; y++)
                temp[index++] =  prefix + subResult[y];
        }
        String[] result = new String[index];
        for (int x=0; x<index; x++)
            result[x] = temp[x];
        return result;
    }

    private int getClass(int c) {
        return sun.text.Normalizer.getCombiningClass(c);
    }

    /**
     * Attempts to compose input by combining the first character
     * with the first combining mark following it. Returns a String
     * that is the composition of the leading character with its first
     * combining mark followed by the remaining combining marks. Returns
     * null if the first two characters cannot be further composed.
     * <p>
     *  尝试通过组合第一个字符与其后的第一个组合标记组合输入返回一个字符串,它是前面字符的组成,其前面有第一个组合标记,后面是剩余的组合标记如果前两个字符不能进一步组合,则返回null
     * 
     */
    private String composeOneStep(String input) {
        int len = countChars(input, 0, 2);
        String firstTwoCharacters = input.substring(0, len);
        String result = Normalizer.normalize(firstTwoCharacters, Normalizer.Form.NFC);

        if (result.equals(firstTwoCharacters))
            return null;
        else {
            String remainder = input.substring(len);
            return result + remainder;
        }
    }

    /**
     * Preprocess any \Q...\E sequences in `temp', meta-quoting them.
     * See the description of `quotemeta' in perlfunc(1).
     * <p>
     *  预处理'temp'中的任何\\ Q \\ E序列,元引用它们在perlfunc(1)中查看`quotemeta'
     * 
     */
    private void RemoveQEQuoting() {
        final int pLen = patternLength;
        int i = 0;
        while (i < pLen-1) {
            if (temp[i] != '\\')
                i += 1;
            else if (temp[i + 1] != 'Q')
                i += 2;
            else
                break;
        }
        if (i >= pLen - 1)    // No \Q sequence found
            return;
        int j = i;
        i += 2;
        int[] newtemp = new int[j + 3*(pLen-i) + 2];
        System.arraycopy(temp, 0, newtemp, 0, j);

        boolean inQuote = true;
        boolean beginQuote = true;
        while (i < pLen) {
            int c = temp[i++];
            if (!ASCII.isAscii(c) || ASCII.isAlpha(c)) {
                newtemp[j++] = c;
            } else if (ASCII.isDigit(c)) {
                if (beginQuote) {
                    /*
                     * A unicode escape \[0xu] could be before this quote,
                     * and we don't want this numeric char to processed as
                     * part of the escape.
                     * <p>
                     * unicode转义\\ [0xu]可能在此引号之前,我们不希望此数字char被处理为转义的一部分
                     * 
                     */
                    newtemp[j++] = '\\';
                    newtemp[j++] = 'x';
                    newtemp[j++] = '3';
                }
                newtemp[j++] = c;
            } else if (c != '\\') {
                if (inQuote) newtemp[j++] = '\\';
                newtemp[j++] = c;
            } else if (inQuote) {
                if (temp[i] == 'E') {
                    i++;
                    inQuote = false;
                } else {
                    newtemp[j++] = '\\';
                    newtemp[j++] = '\\';
                }
            } else {
                if (temp[i] == 'Q') {
                    i++;
                    inQuote = true;
                    beginQuote = true;
                    continue;
                } else {
                    newtemp[j++] = c;
                    if (i != pLen)
                        newtemp[j++] = temp[i++];
                }
            }

            beginQuote = false;
        }

        patternLength = j;
        temp = Arrays.copyOf(newtemp, j + 2); // double zero termination
    }

    /**
     * Copies regular expression to an int array and invokes the parsing
     * of the expression which will create the object tree.
     * <p>
     *  将正则表达式复制到int数组,并调用将创建对象树的表达式的解析
     * 
     */
    private void compile() {
        // Handle canonical equivalences
        if (has(CANON_EQ) && !has(LITERAL)) {
            normalize();
        } else {
            normalizedPattern = pattern;
        }
        patternLength = normalizedPattern.length();

        // Copy pattern to int array for convenience
        // Use double zero to terminate pattern
        temp = new int[patternLength + 2];

        hasSupplementary = false;
        int c, count = 0;
        // Convert all chars into code points
        for (int x = 0; x < patternLength; x += Character.charCount(c)) {
            c = normalizedPattern.codePointAt(x);
            if (isSupplementary(c)) {
                hasSupplementary = true;
            }
            temp[count++] = c;
        }

        patternLength = count;   // patternLength now in code points

        if (! has(LITERAL))
            RemoveQEQuoting();

        // Allocate all temporary objects here.
        buffer = new int[32];
        groupNodes = new GroupHead[10];
        namedGroups = null;

        if (has(LITERAL)) {
            // Literal pattern handling
            matchRoot = newSlice(temp, patternLength, hasSupplementary);
            matchRoot.next = lastAccept;
        } else {
            // Start recursive descent parsing
            matchRoot = expr(lastAccept);
            // Check extra pattern characters
            if (patternLength != cursor) {
                if (peek() == ')') {
                    throw error("Unmatched closing ')'");
                } else {
                    throw error("Unexpected internal error");
                }
            }
        }

        // Peephole optimization
        if (matchRoot instanceof Slice) {
            root = BnM.optimize(matchRoot);
            if (root == matchRoot) {
                root = hasSupplementary ? new StartS(matchRoot) : new Start(matchRoot);
            }
        } else if (matchRoot instanceof Begin || matchRoot instanceof First) {
            root = matchRoot;
        } else {
            root = hasSupplementary ? new StartS(matchRoot) : new Start(matchRoot);
        }

        // Release temporary storage
        temp = null;
        buffer = null;
        groupNodes = null;
        patternLength = 0;
        compiled = true;
    }

    Map<String, Integer> namedGroups() {
        if (namedGroups == null)
            namedGroups = new HashMap<>(2);
        return namedGroups;
    }

    /**
     * Used to print out a subtree of the Pattern to help with debugging.
     * <p>
     *  用于打印模式的子树以帮助调试
     * 
     */
    private static void printObjectTree(Node node) {
        while(node != null) {
            if (node instanceof Prolog) {
                System.out.println(node);
                printObjectTree(((Prolog)node).loop);
                System.out.println("**** end contents prolog loop");
            } else if (node instanceof Loop) {
                System.out.println(node);
                printObjectTree(((Loop)node).body);
                System.out.println("**** end contents Loop body");
            } else if (node instanceof Curly) {
                System.out.println(node);
                printObjectTree(((Curly)node).atom);
                System.out.println("**** end contents Curly body");
            } else if (node instanceof GroupCurly) {
                System.out.println(node);
                printObjectTree(((GroupCurly)node).atom);
                System.out.println("**** end contents GroupCurly body");
            } else if (node instanceof GroupTail) {
                System.out.println(node);
                System.out.println("Tail next is "+node.next);
                return;
            } else {
                System.out.println(node);
            }
            node = node.next;
            if (node != null)
                System.out.println("->next:");
            if (node == Pattern.accept) {
                System.out.println("Accept Node");
                node = null;
            }
       }
    }

    /**
     * Used to accumulate information about a subtree of the object graph
     * so that optimizations can be applied to the subtree.
     * <p>
     *  用于累积有关对象图的子树的信息,以便优化可以应用于子树
     * 
     */
    static final class TreeInfo {
        int minLength;
        int maxLength;
        boolean maxValid;
        boolean deterministic;

        TreeInfo() {
            reset();
        }
        void reset() {
            minLength = 0;
            maxLength = 0;
            maxValid = true;
            deterministic = true;
        }
    }

    /*
     * The following private methods are mainly used to improve the
     * readability of the code. In order to let the Java compiler easily
     * inline them, we should not put many assertions or error checks in them.
     * <p>
     *  以下私有方法主要用于提高代码的可读性为了让Java编译器轻松地将它们内联,我们不应该在其中放置许多断言或错误检查
     * 
     */

    /**
     * Indicates whether a particular flag is set or not.
     * <p>
     *  指示是否设置特定标志
     * 
     */
    private boolean has(int f) {
        return (flags & f) != 0;
    }

    /**
     * Match next character, signal error if failed.
     * <p>
     *  匹配下一个字符,如果失败则信号错误
     * 
     */
    private void accept(int ch, String s) {
        int testChar = temp[cursor++];
        if (has(COMMENTS))
            testChar = parsePastWhitespace(testChar);
        if (ch != testChar) {
            throw error(s);
        }
    }

    /**
     * Mark the end of pattern with a specific character.
     * <p>
     *  用特定字符标记模式的结尾
     * 
     */
    private void mark(int c) {
        temp[patternLength] = c;
    }

    /**
     * Peek the next character, and do not advance the cursor.
     * <p>
     * 查看下一个字符,而不要前进光标
     * 
     */
    private int peek() {
        int ch = temp[cursor];
        if (has(COMMENTS))
            ch = peekPastWhitespace(ch);
        return ch;
    }

    /**
     * Read the next character, and advance the cursor by one.
     * <p>
     *  读取下一个字符,并将光标向前移动一个
     * 
     */
    private int read() {
        int ch = temp[cursor++];
        if (has(COMMENTS))
            ch = parsePastWhitespace(ch);
        return ch;
    }

    /**
     * Read the next character, and advance the cursor by one,
     * ignoring the COMMENTS setting
     * <p>
     *  读取下一个字符,并将光标向前移动一个,忽略COMMENTS设置
     * 
     */
    private int readEscaped() {
        int ch = temp[cursor++];
        return ch;
    }

    /**
     * Advance the cursor by one, and peek the next character.
     * <p>
     *  将光标前进一个,并查看下一个字符
     * 
     */
    private int next() {
        int ch = temp[++cursor];
        if (has(COMMENTS))
            ch = peekPastWhitespace(ch);
        return ch;
    }

    /**
     * Advance the cursor by one, and peek the next character,
     * ignoring the COMMENTS setting
     * <p>
     *  将光标前进一个,并忽略下一个字符,忽略COMMENTS设置
     * 
     */
    private int nextEscaped() {
        int ch = temp[++cursor];
        return ch;
    }

    /**
     * If in xmode peek past whitespace and comments.
     * <p>
     *  如果在xmode偷看过去空格和注释
     * 
     */
    private int peekPastWhitespace(int ch) {
        while (ASCII.isSpace(ch) || ch == '#') {
            while (ASCII.isSpace(ch))
                ch = temp[++cursor];
            if (ch == '#') {
                ch = peekPastLine();
            }
        }
        return ch;
    }

    /**
     * If in xmode parse past whitespace and comments.
     * <p>
     *  如果在xmode中解析过去的空格和注释
     * 
     */
    private int parsePastWhitespace(int ch) {
        while (ASCII.isSpace(ch) || ch == '#') {
            while (ASCII.isSpace(ch))
                ch = temp[cursor++];
            if (ch == '#')
                ch = parsePastLine();
        }
        return ch;
    }

    /**
     * xmode parse past comment to end of line.
     * <p>
     *  xmode将过去的注释解析到行尾
     * 
     */
    private int parsePastLine() {
        int ch = temp[cursor++];
        while (ch != 0 && !isLineSeparator(ch))
            ch = temp[cursor++];
        return ch;
    }

    /**
     * xmode peek past comment to end of line.
     * <p>
     *  xmode偷看过去的评论到行尾
     * 
     */
    private int peekPastLine() {
        int ch = temp[++cursor];
        while (ch != 0 && !isLineSeparator(ch))
            ch = temp[++cursor];
        return ch;
    }

    /**
     * Determines if character is a line separator in the current mode
     * <p>
     *  确定字符在当前模式下是否为行分隔符
     * 
     */
    private boolean isLineSeparator(int ch) {
        if (has(UNIX_LINES)) {
            return ch == '\n';
        } else {
            return (ch == '\n' ||
                    ch == '\r' ||
                    (ch|1) == '\u2029' ||
                    ch == '\u0085');
        }
    }

    /**
     * Read the character after the next one, and advance the cursor by two.
     * <p>
     *  读取下一个字符后,将光标前移两位
     * 
     */
    private int skip() {
        int i = cursor;
        int ch = temp[i+1];
        cursor = i + 2;
        return ch;
    }

    /**
     * Unread one next character, and retreat cursor by one.
     * <p>
     *  未读取一个下一个字符,并将光标缩回一个
     * 
     */
    private void unread() {
        cursor--;
    }

    /**
     * Internal method used for handling all syntax errors. The pattern is
     * displayed with a pointer to aid in locating the syntax error.
     * <p>
     * 用于处理所有语法错误的内部方法模式将使用指针显示,以帮助定位语法错误
     * 
     */
    private PatternSyntaxException error(String s) {
        return new PatternSyntaxException(s, normalizedPattern,  cursor - 1);
    }

    /**
     * Determines if there is any supplementary character or unpaired
     * surrogate in the specified range.
     * <p>
     *  确定在指定范围内是否有任何补充字符或不成对的代理
     * 
     */
    private boolean findSupplementary(int start, int end) {
        for (int i = start; i < end; i++) {
            if (isSupplementary(temp[i]))
                return true;
        }
        return false;
    }

    /**
     * Determines if the specified code point is a supplementary
     * character or unpaired surrogate.
     * <p>
     *  确定指定的代码点是补充字符还是不成对的代理
     * 
     */
    private static final boolean isSupplementary(int ch) {
        return ch >= Character.MIN_SUPPLEMENTARY_CODE_POINT ||
               Character.isSurrogate((char)ch);
    }

    /**
     *  The following methods handle the main parsing. They are sorted
     *  according to their precedence order, the lowest one first.
     * <p>
     *  以下方法处理主解析它们根据它们的优先顺序排序,最低的一个首先
     * 
     */

    /**
     * The expression is parsed with branch nodes added for alternations.
     * This may be called recursively to parse sub expressions that may
     * contain alternations.
     * <p>
     *  该表达式用为交替添加的分支节点来解析。这可以递归地被解析以解析可能包含交替的子表达式
     * 
     */
    private Node expr(Node end) {
        Node prev = null;
        Node firstTail = null;
        Branch branch = null;
        Node branchConn = null;

        for (;;) {
            Node node = sequence(end);
            Node nodeTail = root;      //double return
            if (prev == null) {
                prev = node;
                firstTail = nodeTail;
            } else {
                // Branch
                if (branchConn == null) {
                    branchConn = new BranchConn();
                    branchConn.next = end;
                }
                if (node == end) {
                    // if the node returned from sequence() is "end"
                    // we have an empty expr, set a null atom into
                    // the branch to indicate to go "next" directly.
                    node = null;
                } else {
                    // the "tail.next" of each atom goes to branchConn
                    nodeTail.next = branchConn;
                }
                if (prev == branch) {
                    branch.add(node);
                } else {
                    if (prev == end) {
                        prev = null;
                    } else {
                        // replace the "end" with "branchConn" at its tail.next
                        // when put the "prev" into the branch as the first atom.
                        firstTail.next = branchConn;
                    }
                    prev = branch = new Branch(prev, node, branchConn);
                }
            }
            if (peek() != '|') {
                return prev;
            }
            next();
        }
    }

    @SuppressWarnings("fallthrough")
    /**
     * Parsing of sequences between alternations.
     * <p>
     *  序列在交替之间的解析
     * 
     */
    private Node sequence(Node end) {
        Node head = null;
        Node tail = null;
        Node node = null;
    LOOP:
        for (;;) {
            int ch = peek();
            switch (ch) {
            case '(':
                // Because group handles its own closure,
                // we need to treat it differently
                node = group0();
                // Check for comment or flag group
                if (node == null)
                    continue;
                if (head == null)
                    head = node;
                else
                    tail.next = node;
                // Double return: Tail was returned in root
                tail = root;
                continue;
            case '[':
                node = clazz(true);
                break;
            case '\\':
                ch = nextEscaped();
                if (ch == 'p' || ch == 'P') {
                    boolean oneLetter = true;
                    boolean comp = (ch == 'P');
                    ch = next(); // Consume { if present
                    if (ch != '{') {
                        unread();
                    } else {
                        oneLetter = false;
                    }
                    node = family(oneLetter, comp);
                } else {
                    unread();
                    node = atom();
                }
                break;
            case '^':
                next();
                if (has(MULTILINE)) {
                    if (has(UNIX_LINES))
                        node = new UnixCaret();
                    else
                        node = new Caret();
                } else {
                    node = new Begin();
                }
                break;
            case '$':
                next();
                if (has(UNIX_LINES))
                    node = new UnixDollar(has(MULTILINE));
                else
                    node = new Dollar(has(MULTILINE));
                break;
            case '.':
                next();
                if (has(DOTALL)) {
                    node = new All();
                } else {
                    if (has(UNIX_LINES))
                        node = new UnixDot();
                    else {
                        node = new Dot();
                    }
                }
                break;
            case '|':
            case ')':
                break LOOP;
            case ']': // Now interpreting dangling ] and } as literals
            case '}':
                node = atom();
                break;
            case '?':
            case '*':
            case '+':
                next();
                throw error("Dangling meta character '" + ((char)ch) + "'");
            case 0:
                if (cursor >= patternLength) {
                    break LOOP;
                }
                // Fall through
            default:
                node = atom();
                break;
            }

            node = closure(node);

            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }
        if (head == null) {
            return end;
        }
        tail.next = end;
        root = tail;      //double return
        return head;
    }

    @SuppressWarnings("fallthrough")
    /**
     * Parse and add a new Single or Slice.
     * <p>
     *  解析并添加一个新的单或切片
     * 
     */
    private Node atom() {
        int first = 0;
        int prev = -1;
        boolean hasSupplementary = false;
        int ch = peek();
        for (;;) {
            switch (ch) {
            case '*':
            case '+':
            case '?':
            case '{':
                if (first > 1) {
                    cursor = prev;    // Unwind one character
                    first--;
                }
                break;
            case '$':
            case '.':
            case '^':
            case '(':
            case '[':
            case '|':
            case ')':
                break;
            case '\\':
                ch = nextEscaped();
                if (ch == 'p' || ch == 'P') { // Property
                    if (first > 0) { // Slice is waiting; handle it first
                        unread();
                        break;
                    } else { // No slice; just return the family node
                        boolean comp = (ch == 'P');
                        boolean oneLetter = true;
                        ch = next(); // Consume { if present
                        if (ch != '{')
                            unread();
                        else
                            oneLetter = false;
                        return family(oneLetter, comp);
                    }
                }
                unread();
                prev = cursor;
                ch = escape(false, first == 0, false);
                if (ch >= 0) {
                    append(ch, first);
                    first++;
                    if (isSupplementary(ch)) {
                        hasSupplementary = true;
                    }
                    ch = peek();
                    continue;
                } else if (first == 0) {
                    return root;
                }
                // Unwind meta escape sequence
                cursor = prev;
                break;
            case 0:
                if (cursor >= patternLength) {
                    break;
                }
                // Fall through
            default:
                prev = cursor;
                append(ch, first);
                first++;
                if (isSupplementary(ch)) {
                    hasSupplementary = true;
                }
                ch = next();
                continue;
            }
            break;
        }
        if (first == 1) {
            return newSingle(buffer[0]);
        } else {
            return newSlice(buffer, first, hasSupplementary);
        }
    }

    private void append(int ch, int len) {
        if (len >= buffer.length) {
            int[] tmp = new int[len+len];
            System.arraycopy(buffer, 0, tmp, 0, len);
            buffer = tmp;
        }
        buffer[len] = ch;
    }

    /**
     * Parses a backref greedily, taking as many numbers as it
     * can. The first digit is always treated as a backref, but
     * multi digit numbers are only treated as a backref if at
     * least that many backrefs exist at this point in the regex.
     * <p>
     * 解析backref贪婪,取尽可能多的数字第一个数字总是被视为backref,但多数字数字只被视为一个backref如果至少那么多的backrefs存在于正则表达式的这一点
     * 
     */
    private Node ref(int refNum) {
        boolean done = false;
        while(!done) {
            int ch = peek();
            switch(ch) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                int newRefNum = (refNum * 10) + (ch - '0');
                // Add another number if it doesn't make a group
                // that doesn't exist
                if (capturingGroupCount - 1 < newRefNum) {
                    done = true;
                    break;
                }
                refNum = newRefNum;
                read();
                break;
            default:
                done = true;
                break;
            }
        }
        if (has(CASE_INSENSITIVE))
            return new CIBackRef(refNum, has(UNICODE_CASE));
        else
            return new BackRef(refNum);
    }

    /**
     * Parses an escape sequence to determine the actual value that needs
     * to be matched.
     * If -1 is returned and create was true a new object was added to the tree
     * to handle the escape sequence.
     * If the returned value is greater than zero, it is the value that
     * matches the escape sequence.
     * <p>
     *  解析转义序列以确定需要匹配的实际值如果返回-1并且create为true,则会向树中添加一个新对象以处理转义序列。如果返回的值大于零,则该值为匹配转义序列
     * 
     */
    private int escape(boolean inclass, boolean create, boolean isrange) {
        int ch = skip();
        switch (ch) {
        case '0':
            return o();
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            if (inclass) break;
            if (create) {
                root = ref((ch - '0'));
            }
            return -1;
        case 'A':
            if (inclass) break;
            if (create) root = new Begin();
            return -1;
        case 'B':
            if (inclass) break;
            if (create) root = new Bound(Bound.NONE, has(UNICODE_CHARACTER_CLASS));
            return -1;
        case 'C':
            break;
        case 'D':
            if (create) root = has(UNICODE_CHARACTER_CLASS)
                               ? new Utype(UnicodeProp.DIGIT).complement()
                               : new Ctype(ASCII.DIGIT).complement();
            return -1;
        case 'E':
        case 'F':
            break;
        case 'G':
            if (inclass) break;
            if (create) root = new LastMatch();
            return -1;
        case 'H':
            if (create) root = new HorizWS().complement();
            return -1;
        case 'I':
        case 'J':
        case 'K':
        case 'L':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
            break;
        case 'R':
            if (inclass) break;
            if (create) root = new LineEnding();
            return -1;
        case 'S':
            if (create) root = has(UNICODE_CHARACTER_CLASS)
                               ? new Utype(UnicodeProp.WHITE_SPACE).complement()
                               : new Ctype(ASCII.SPACE).complement();
            return -1;
        case 'T':
        case 'U':
            break;
        case 'V':
            if (create) root = new VertWS().complement();
            return -1;
        case 'W':
            if (create) root = has(UNICODE_CHARACTER_CLASS)
                               ? new Utype(UnicodeProp.WORD).complement()
                               : new Ctype(ASCII.WORD).complement();
            return -1;
        case 'X':
        case 'Y':
            break;
        case 'Z':
            if (inclass) break;
            if (create) {
                if (has(UNIX_LINES))
                    root = new UnixDollar(false);
                else
                    root = new Dollar(false);
            }
            return -1;
        case 'a':
            return '\007';
        case 'b':
            if (inclass) break;
            if (create) root = new Bound(Bound.BOTH, has(UNICODE_CHARACTER_CLASS));
            return -1;
        case 'c':
            return c();
        case 'd':
            if (create) root = has(UNICODE_CHARACTER_CLASS)
                               ? new Utype(UnicodeProp.DIGIT)
                               : new Ctype(ASCII.DIGIT);
            return -1;
        case 'e':
            return '\033';
        case 'f':
            return '\f';
        case 'g':
            break;
        case 'h':
            if (create) root = new HorizWS();
            return -1;
        case 'i':
        case 'j':
            break;
        case 'k':
            if (inclass)
                break;
            if (read() != '<')
                throw error("\\k is not followed by '<' for named capturing group");
            String name = groupname(read());
            if (!namedGroups().containsKey(name))
                throw error("(named capturing group <"+ name+"> does not exit");
            if (create) {
                if (has(CASE_INSENSITIVE))
                    root = new CIBackRef(namedGroups().get(name), has(UNICODE_CASE));
                else
                    root = new BackRef(namedGroups().get(name));
            }
            return -1;
        case 'l':
        case 'm':
            break;
        case 'n':
            return '\n';
        case 'o':
        case 'p':
        case 'q':
            break;
        case 'r':
            return '\r';
        case 's':
            if (create) root = has(UNICODE_CHARACTER_CLASS)
                               ? new Utype(UnicodeProp.WHITE_SPACE)
                               : new Ctype(ASCII.SPACE);
            return -1;
        case 't':
            return '\t';
        case 'u':
            return u();
        case 'v':
            // '\v' was implemented as VT/0x0B in releases < 1.8 (though
            // undocumented). In JDK8 '\v' is specified as a predefined
            // character class for all vertical whitespace characters.
            // So [-1, root=VertWS node] pair is returned (instead of a
            // single 0x0B). This breaks the range if '\v' is used as
            // the start or end value, such as [\v-...] or [...-\v], in
            // which a single definite value (0x0B) is expected. For
            // compatibility concern '\013'/0x0B is returned if isrange.
            if (isrange)
                return '\013';
            if (create) root = new VertWS();
            return -1;
        case 'w':
            if (create) root = has(UNICODE_CHARACTER_CLASS)
                               ? new Utype(UnicodeProp.WORD)
                               : new Ctype(ASCII.WORD);
            return -1;
        case 'x':
            return x();
        case 'y':
            break;
        case 'z':
            if (inclass) break;
            if (create) root = new End();
            return -1;
        default:
            return ch;
        }
        throw error("Illegal/unsupported escape sequence");
    }

    /**
     * Parse a character class, and return the node that matches it.
     *
     * Consumes a ] on the way out if consume is true. Usually consume
     * is true except for the case of [abc&&def] where def is a separate
     * right hand node with "understood" brackets.
     * <p>
     *  解析字符类,并返回与其匹配的节点
     * 
     *  如果consume为true,则在出口处使用a]通常消耗是真的,除了[abc && def]的情况,其中def是具有"理解"括号的单独的右手节点
     * 
     */
    private CharProperty clazz(boolean consume) {
        CharProperty prev = null;
        CharProperty node = null;
        BitClass bits = new BitClass();
        boolean include = true;
        boolean firstInClass = true;
        int ch = next();
        for (;;) {
            switch (ch) {
                case '^':
                    // Negates if first char in a class, otherwise literal
                    if (firstInClass) {
                        if (temp[cursor-1] != '[')
                            break;
                        ch = next();
                        include = !include;
                        continue;
                    } else {
                        // ^ not first in class, treat as literal
                        break;
                    }
                case '[':
                    firstInClass = false;
                    node = clazz(true);
                    if (prev == null)
                        prev = node;
                    else
                        prev = union(prev, node);
                    ch = peek();
                    continue;
                case '&':
                    firstInClass = false;
                    ch = next();
                    if (ch == '&') {
                        ch = next();
                        CharProperty rightNode = null;
                        while (ch != ']' && ch != '&') {
                            if (ch == '[') {
                                if (rightNode == null)
                                    rightNode = clazz(true);
                                else
                                    rightNode = union(rightNode, clazz(true));
                            } else { // abc&&def
                                unread();
                                rightNode = clazz(false);
                            }
                            ch = peek();
                        }
                        if (rightNode != null)
                            node = rightNode;
                        if (prev == null) {
                            if (rightNode == null)
                                throw error("Bad class syntax");
                            else
                                prev = rightNode;
                        } else {
                            prev = intersection(prev, node);
                        }
                    } else {
                        // treat as a literal &
                        unread();
                        break;
                    }
                    continue;
                case 0:
                    firstInClass = false;
                    if (cursor >= patternLength)
                        throw error("Unclosed character class");
                    break;
                case ']':
                    firstInClass = false;
                    if (prev != null) {
                        if (consume)
                            next();
                        return prev;
                    }
                    break;
                default:
                    firstInClass = false;
                    break;
            }
            node = range(bits);
            if (include) {
                if (prev == null) {
                    prev = node;
                } else {
                    if (prev != node)
                        prev = union(prev, node);
                }
            } else {
                if (prev == null) {
                    prev = node.complement();
                } else {
                    if (prev != node)
                        prev = setDifference(prev, node);
                }
            }
            ch = peek();
        }
    }

    private CharProperty bitsOrSingle(BitClass bits, int ch) {
        /* Bits can only handle codepoints in [u+0000-u+00ff] range.
           Use "single" node instead of bits when dealing with unicode
           case folding for codepoints listed below.
           (1)Uppercase out of range: u+00ff, u+00b5
              toUpperCase(u+00ff) -> u+0178
              toUpperCase(u+00b5) -> u+039c
           (2)LatinSmallLetterLongS u+17f
              toUpperCase(u+017f) -> u+0053
           (3)LatinSmallLetterDotlessI u+131
              toUpperCase(u+0131) -> u+0049
           (4)LatinCapitalLetterIWithDotAbove u+0130
              toLowerCase(u+0130) -> u+0069
           (5)KelvinSign u+212a
              toLowerCase(u+212a) ==> u+006B
           (6)AngstromSign u+212b
              toLowerCase(u+212b) ==> u+00e5
        /* <p>
        /* 在处理下面列出的代码点的unicode case折叠时,使用"single"节点代替bit(1)大写超出范围：u + 00ff,u + 00b5到UpperCase(u + 00ff) - > u + 
        /* 0178 toUpperCase(u + 00b5) - > u + 039c(2)LatinSmallLetterLongS u + 17f toUpperCase(u + 017f)→u + 005
        /* 3(3)LatinSmallLetterDotlessI u + 131 toUpperCase(u + 0131)→u + 0049(4)LatinCapitalLetterIWithDotAbove
        /*  u + 0130 toLowerCase u + 0130) - > u + 0069(5)KelvinSign u + 212a toLowerCase(u + 212a)==> u + 006B(
        /* 6)AngstromSign u + 212b toLowerCase(u + 212b)==> u + 00e5。
        /* 
        */
        int d;
        if (ch < 256 &&
            !(has(CASE_INSENSITIVE) && has(UNICODE_CASE) &&
              (ch == 0xff || ch == 0xb5 ||
               ch == 0x49 || ch == 0x69 ||  //I and i
               ch == 0x53 || ch == 0x73 ||  //S and s
               ch == 0x4b || ch == 0x6b ||  //K and k
               ch == 0xc5 || ch == 0xe5)))  //A+ring
            return bits.add(ch, flags());
        return newSingle(ch);
    }

    /**
     * Parse a single character or a character range in a character class
     * and return its representative node.
     * <p>
     *  解析字符类中的单个字符或字符范围,并返回其代表性节点
     * 
     */
    private CharProperty range(BitClass bits) {
        int ch = peek();
        if (ch == '\\') {
            ch = nextEscaped();
            if (ch == 'p' || ch == 'P') { // A property
                boolean comp = (ch == 'P');
                boolean oneLetter = true;
                // Consume { if present
                ch = next();
                if (ch != '{')
                    unread();
                else
                    oneLetter = false;
                return family(oneLetter, comp);
            } else { // ordinary escape
                boolean isrange = temp[cursor+1] == '-';
                unread();
                ch = escape(true, true, isrange);
                if (ch == -1)
                    return (CharProperty) root;
            }
        } else {
            next();
        }
        if (ch >= 0) {
            if (peek() == '-') {
                int endRange = temp[cursor+1];
                if (endRange == '[') {
                    return bitsOrSingle(bits, ch);
                }
                if (endRange != ']') {
                    next();
                    int m = peek();
                    if (m == '\\') {
                        m = escape(true, false, true);
                    } else {
                        next();
                    }
                    if (m < ch) {
                        throw error("Illegal character range");
                    }
                    if (has(CASE_INSENSITIVE))
                        return caseInsensitiveRangeFor(ch, m);
                    else
                        return rangeFor(ch, m);
                }
            }
            return bitsOrSingle(bits, ch);
        }
        throw error("Unexpected character '"+((char)ch)+"'");
    }

    /**
     * Parses a Unicode character family and returns its representative node.
     * <p>
     *  解析Unicode字符系列并返回其代表性节点
     * 
     */
    private CharProperty family(boolean singleLetter,
                                boolean maybeComplement)
    {
        next();
        String name;
        CharProperty node = null;

        if (singleLetter) {
            int c = temp[cursor];
            if (!Character.isSupplementaryCodePoint(c)) {
                name = String.valueOf((char)c);
            } else {
                name = new String(temp, cursor, 1);
            }
            read();
        } else {
            int i = cursor;
            mark('}');
            while(read() != '}') {
            }
            mark('\000');
            int j = cursor;
            if (j > patternLength)
                throw error("Unclosed character family");
            if (i + 1 >= j)
                throw error("Empty character family");
            name = new String(temp, i, j-i-1);
        }

        int i = name.indexOf('=');
        if (i != -1) {
            // property construct \p{name=value}
            String value = name.substring(i + 1);
            name = name.substring(0, i).toLowerCase(Locale.ENGLISH);
            if ("sc".equals(name) || "script".equals(name)) {
                node = unicodeScriptPropertyFor(value);
            } else if ("blk".equals(name) || "block".equals(name)) {
                node = unicodeBlockPropertyFor(value);
            } else if ("gc".equals(name) || "general_category".equals(name)) {
                node = charPropertyNodeFor(value);
            } else {
                throw error("Unknown Unicode property {name=<" + name + ">, "
                             + "value=<" + value + ">}");
            }
        } else {
            if (name.startsWith("In")) {
                // \p{inBlockName}
                node = unicodeBlockPropertyFor(name.substring(2));
            } else if (name.startsWith("Is")) {
                // \p{isGeneralCategory} and \p{isScriptName}
                name = name.substring(2);
                UnicodeProp uprop = UnicodeProp.forName(name);
                if (uprop != null)
                    node = new Utype(uprop);
                if (node == null)
                    node = CharPropertyNames.charPropertyFor(name);
                if (node == null)
                    node = unicodeScriptPropertyFor(name);
            } else {
                if (has(UNICODE_CHARACTER_CLASS)) {
                    UnicodeProp uprop = UnicodeProp.forPOSIXName(name);
                    if (uprop != null)
                        node = new Utype(uprop);
                }
                if (node == null)
                    node = charPropertyNodeFor(name);
            }
        }
        if (maybeComplement) {
            if (node instanceof Category || node instanceof Block)
                hasSupplementary = true;
            node = node.complement();
        }
        return node;
    }


    /**
     * Returns a CharProperty matching all characters belong to
     * a UnicodeScript.
     * <p>
     *  返回与属于UnicodeScript的所有字符匹配的CharProperty
     * 
     */
    private CharProperty unicodeScriptPropertyFor(String name) {
        final Character.UnicodeScript script;
        try {
            script = Character.UnicodeScript.forName(name);
        } catch (IllegalArgumentException iae) {
            throw error("Unknown character script name {" + name + "}");
        }
        return new Script(script);
    }

    /**
     * Returns a CharProperty matching all characters in a UnicodeBlock.
     * <p>
     * 返回与UnicodeBlock中的所有字符匹配的CharProperty
     * 
     */
    private CharProperty unicodeBlockPropertyFor(String name) {
        final Character.UnicodeBlock block;
        try {
            block = Character.UnicodeBlock.forName(name);
        } catch (IllegalArgumentException iae) {
            throw error("Unknown character block name {" + name + "}");
        }
        return new Block(block);
    }

    /**
     * Returns a CharProperty matching all characters in a named property.
     * <p>
     *  返回与指定属性中的所有字符匹配的CharProperty
     * 
     */
    private CharProperty charPropertyNodeFor(String name) {
        CharProperty p = CharPropertyNames.charPropertyFor(name);
        if (p == null)
            throw error("Unknown character property name {" + name + "}");
        return p;
    }

    /**
     * Parses and returns the name of a "named capturing group", the trailing
     * ">" is consumed after parsing.
     * <p>
     *  解析并返回"命名捕获组"的名称,解析后消耗尾随的">"
     * 
     */
    private String groupname(int ch) {
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toChars(ch));
        while (ASCII.isLower(ch=read()) || ASCII.isUpper(ch) ||
               ASCII.isDigit(ch)) {
            sb.append(Character.toChars(ch));
        }
        if (sb.length() == 0)
            throw error("named capturing group has 0 length name");
        if (ch != '>')
            throw error("named capturing group is missing trailing '>'");
        return sb.toString();
    }

    /**
     * Parses a group and returns the head node of a set of nodes that process
     * the group. Sometimes a double return system is used where the tail is
     * returned in root.
     * <p>
     *  解析一个组并返回一组处理组的节点的头节点有时使用双返回系统,其中尾在根中返回
     * 
     */
    private Node group0() {
        boolean capturingGroup = false;
        Node head = null;
        Node tail = null;
        int save = flags;
        root = null;
        int ch = next();
        if (ch == '?') {
            ch = skip();
            switch (ch) {
            case ':':   //  (?:xxx) pure group
                head = createGroup(true);
                tail = root;
                head.next = expr(tail);
                break;
            case '=':   // (?=xxx) and (?!xxx) lookahead
            case '!':
                head = createGroup(true);
                tail = root;
                head.next = expr(tail);
                if (ch == '=') {
                    head = tail = new Pos(head);
                } else {
                    head = tail = new Neg(head);
                }
                break;
            case '>':   // (?>xxx)  independent group
                head = createGroup(true);
                tail = root;
                head.next = expr(tail);
                head = tail = new Ques(head, INDEPENDENT);
                break;
            case '<':   // (?<xxx)  look behind
                ch = read();
                if (ASCII.isLower(ch) || ASCII.isUpper(ch)) {
                    // named captured group
                    String name = groupname(ch);
                    if (namedGroups().containsKey(name))
                        throw error("Named capturing group <" + name
                                    + "> is already defined");
                    capturingGroup = true;
                    head = createGroup(false);
                    tail = root;
                    namedGroups().put(name, capturingGroupCount-1);
                    head.next = expr(tail);
                    break;
                }
                int start = cursor;
                head = createGroup(true);
                tail = root;
                head.next = expr(tail);
                tail.next = lookbehindEnd;
                TreeInfo info = new TreeInfo();
                head.study(info);
                if (info.maxValid == false) {
                    throw error("Look-behind group does not have "
                                + "an obvious maximum length");
                }
                boolean hasSupplementary = findSupplementary(start, patternLength);
                if (ch == '=') {
                    head = tail = (hasSupplementary ?
                                   new BehindS(head, info.maxLength,
                                               info.minLength) :
                                   new Behind(head, info.maxLength,
                                              info.minLength));
                } else if (ch == '!') {
                    head = tail = (hasSupplementary ?
                                   new NotBehindS(head, info.maxLength,
                                                  info.minLength) :
                                   new NotBehind(head, info.maxLength,
                                                 info.minLength));
                } else {
                    throw error("Unknown look-behind group");
                }
                break;
            case '$':
            case '@':
                throw error("Unknown group type");
            default:    // (?xxx:) inlined match flags
                unread();
                addFlag();
                ch = read();
                if (ch == ')') {
                    return null;    // Inline modifier only
                }
                if (ch != ':') {
                    throw error("Unknown inline modifier");
                }
                head = createGroup(true);
                tail = root;
                head.next = expr(tail);
                break;
            }
        } else { // (xxx) a regular group
            capturingGroup = true;
            head = createGroup(false);
            tail = root;
            head.next = expr(tail);
        }

        accept(')', "Unclosed group");
        flags = save;

        // Check for quantifiers
        Node node = closure(head);
        if (node == head) { // No closure
            root = tail;
            return node;    // Dual return
        }
        if (head == tail) { // Zero length assertion
            root = node;
            return node;    // Dual return
        }

        if (node instanceof Ques) {
            Ques ques = (Ques) node;
            if (ques.type == POSSESSIVE) {
                root = node;
                return node;
            }
            tail.next = new BranchConn();
            tail = tail.next;
            if (ques.type == GREEDY) {
                head = new Branch(head, null, tail);
            } else { // Reluctant quantifier
                head = new Branch(null, head, tail);
            }
            root = tail;
            return head;
        } else if (node instanceof Curly) {
            Curly curly = (Curly) node;
            if (curly.type == POSSESSIVE) {
                root = node;
                return node;
            }
            // Discover if the group is deterministic
            TreeInfo info = new TreeInfo();
            if (head.study(info)) { // Deterministic
                GroupTail temp = (GroupTail) tail;
                head = root = new GroupCurly(head.next, curly.cmin,
                                   curly.cmax, curly.type,
                                   ((GroupTail)tail).localIndex,
                                   ((GroupTail)tail).groupIndex,
                                             capturingGroup);
                return head;
            } else { // Non-deterministic
                int temp = ((GroupHead) head).localIndex;
                Loop loop;
                if (curly.type == GREEDY)
                    loop = new Loop(this.localCount, temp);
                else  // Reluctant Curly
                    loop = new LazyLoop(this.localCount, temp);
                Prolog prolog = new Prolog(loop);
                this.localCount += 1;
                loop.cmin = curly.cmin;
                loop.cmax = curly.cmax;
                loop.body = head;
                tail.next = loop;
                root = loop;
                return prolog; // Dual return
            }
        }
        throw error("Internal logic error");
    }

    /**
     * Create group head and tail nodes using double return. If the group is
     * created with anonymous true then it is a pure group and should not
     * affect group counting.
     * <p>
     *  使用双返回创建组头和尾节点如果组是使用匿名true创建的,那么它是一个纯组,不应影响组计数
     * 
     */
    private Node createGroup(boolean anonymous) {
        int localIndex = localCount++;
        int groupIndex = 0;
        if (!anonymous)
            groupIndex = capturingGroupCount++;
        GroupHead head = new GroupHead(localIndex);
        root = new GroupTail(localIndex, groupIndex);
        if (!anonymous && groupIndex < 10)
            groupNodes[groupIndex] = head;
        return head;
    }

    @SuppressWarnings("fallthrough")
    /**
     * Parses inlined match flags and set them appropriately.
     * <p>
     *  解析匹配标记并适当地设置它们
     * 
     */
    private void addFlag() {
        int ch = peek();
        for (;;) {
            switch (ch) {
            case 'i':
                flags |= CASE_INSENSITIVE;
                break;
            case 'm':
                flags |= MULTILINE;
                break;
            case 's':
                flags |= DOTALL;
                break;
            case 'd':
                flags |= UNIX_LINES;
                break;
            case 'u':
                flags |= UNICODE_CASE;
                break;
            case 'c':
                flags |= CANON_EQ;
                break;
            case 'x':
                flags |= COMMENTS;
                break;
            case 'U':
                flags |= (UNICODE_CHARACTER_CLASS | UNICODE_CASE);
                break;
            case '-': // subFlag then fall through
                ch = next();
                subFlag();
            default:
                return;
            }
            ch = next();
        }
    }

    @SuppressWarnings("fallthrough")
    /**
     * Parses the second part of inlined match flags and turns off
     * flags appropriately.
     * <p>
     *  解析内联匹配标记的第二部分,并适当关闭标记
     * 
     */
    private void subFlag() {
        int ch = peek();
        for (;;) {
            switch (ch) {
            case 'i':
                flags &= ~CASE_INSENSITIVE;
                break;
            case 'm':
                flags &= ~MULTILINE;
                break;
            case 's':
                flags &= ~DOTALL;
                break;
            case 'd':
                flags &= ~UNIX_LINES;
                break;
            case 'u':
                flags &= ~UNICODE_CASE;
                break;
            case 'c':
                flags &= ~CANON_EQ;
                break;
            case 'x':
                flags &= ~COMMENTS;
                break;
            case 'U':
                flags &= ~(UNICODE_CHARACTER_CLASS | UNICODE_CASE);
            default:
                return;
            }
            ch = next();
        }
    }

    static final int MAX_REPS   = 0x7FFFFFFF;

    static final int GREEDY     = 0;

    static final int LAZY       = 1;

    static final int POSSESSIVE = 2;

    static final int INDEPENDENT = 3;

    /**
     * Processes repetition. If the next character peeked is a quantifier
     * then new nodes must be appended to handle the repetition.
     * Prev could be a single or a group, so it could be a chain of nodes.
     * <p>
     * 进程重复如果下一个被偷看的字符是一个量词,那么必须附加新的节点来处理重复。Prev可以是单个或一组,因此它可以是一个节点链
     * 
     */
    private Node closure(Node prev) {
        Node atom;
        int ch = peek();
        switch (ch) {
        case '?':
            ch = next();
            if (ch == '?') {
                next();
                return new Ques(prev, LAZY);
            } else if (ch == '+') {
                next();
                return new Ques(prev, POSSESSIVE);
            }
            return new Ques(prev, GREEDY);
        case '*':
            ch = next();
            if (ch == '?') {
                next();
                return new Curly(prev, 0, MAX_REPS, LAZY);
            } else if (ch == '+') {
                next();
                return new Curly(prev, 0, MAX_REPS, POSSESSIVE);
            }
            return new Curly(prev, 0, MAX_REPS, GREEDY);
        case '+':
            ch = next();
            if (ch == '?') {
                next();
                return new Curly(prev, 1, MAX_REPS, LAZY);
            } else if (ch == '+') {
                next();
                return new Curly(prev, 1, MAX_REPS, POSSESSIVE);
            }
            return new Curly(prev, 1, MAX_REPS, GREEDY);
        case '{':
            ch = temp[cursor+1];
            if (ASCII.isDigit(ch)) {
                skip();
                int cmin = 0;
                do {
                    cmin = cmin * 10 + (ch - '0');
                } while (ASCII.isDigit(ch = read()));
                int cmax = cmin;
                if (ch == ',') {
                    ch = read();
                    cmax = MAX_REPS;
                    if (ch != '}') {
                        cmax = 0;
                        while (ASCII.isDigit(ch)) {
                            cmax = cmax * 10 + (ch - '0');
                            ch = read();
                        }
                    }
                }
                if (ch != '}')
                    throw error("Unclosed counted closure");
                if (((cmin) | (cmax) | (cmax - cmin)) < 0)
                    throw error("Illegal repetition range");
                Curly curly;
                ch = peek();
                if (ch == '?') {
                    next();
                    curly = new Curly(prev, cmin, cmax, LAZY);
                } else if (ch == '+') {
                    next();
                    curly = new Curly(prev, cmin, cmax, POSSESSIVE);
                } else {
                    curly = new Curly(prev, cmin, cmax, GREEDY);
                }
                return curly;
            } else {
                throw error("Illegal repetition");
            }
        default:
            return prev;
        }
    }

    /**
     *  Utility method for parsing control escape sequences.
     * <p>
     *  解析控制转义序列的实用方法
     * 
     */
    private int c() {
        if (cursor < patternLength) {
            return read() ^ 64;
        }
        throw error("Illegal control escape sequence");
    }

    /**
     *  Utility method for parsing octal escape sequences.
     * <p>
     *  解析八进制转义序列的实用方法
     * 
     */
    private int o() {
        int n = read();
        if (((n-'0')|('7'-n)) >= 0) {
            int m = read();
            if (((m-'0')|('7'-m)) >= 0) {
                int o = read();
                if ((((o-'0')|('7'-o)) >= 0) && (((n-'0')|('3'-n)) >= 0)) {
                    return (n - '0') * 64 + (m - '0') * 8 + (o - '0');
                }
                unread();
                return (n - '0') * 8 + (m - '0');
            }
            unread();
            return (n - '0');
        }
        throw error("Illegal octal escape sequence");
    }

    /**
     *  Utility method for parsing hexadecimal escape sequences.
     * <p>
     *  解析十六进制转义序列的实用方法
     * 
     */
    private int x() {
        int n = read();
        if (ASCII.isHexDigit(n)) {
            int m = read();
            if (ASCII.isHexDigit(m)) {
                return ASCII.toDigit(n) * 16 + ASCII.toDigit(m);
            }
        } else if (n == '{' && ASCII.isHexDigit(peek())) {
            int ch = 0;
            while (ASCII.isHexDigit(n = read())) {
                ch = (ch << 4) + ASCII.toDigit(n);
                if (ch > Character.MAX_CODE_POINT)
                    throw error("Hexadecimal codepoint is too big");
            }
            if (n != '}')
                throw error("Unclosed hexadecimal escape sequence");
            return ch;
        }
        throw error("Illegal hexadecimal escape sequence");
    }

    /**
     *  Utility method for parsing unicode escape sequences.
     * <p>
     *  解析unicode转义序列的实用方法
     * 
     */
    private int cursor() {
        return cursor;
    }

    private void setcursor(int pos) {
        cursor = pos;
    }

    private int uxxxx() {
        int n = 0;
        for (int i = 0; i < 4; i++) {
            int ch = read();
            if (!ASCII.isHexDigit(ch)) {
                throw error("Illegal Unicode escape sequence");
            }
            n = n * 16 + ASCII.toDigit(ch);
        }
        return n;
    }

    private int u() {
        int n = uxxxx();
        if (Character.isHighSurrogate((char)n)) {
            int cur = cursor();
            if (read() == '\\' && read() == 'u') {
                int n2 = uxxxx();
                if (Character.isLowSurrogate((char)n2))
                    return Character.toCodePoint((char)n, (char)n2);
            }
            setcursor(cur);
        }
        return n;
    }

    //
    // Utility methods for code point support
    //

    private static final int countChars(CharSequence seq, int index,
                                        int lengthInCodePoints) {
        // optimization
        if (lengthInCodePoints == 1 && !Character.isHighSurrogate(seq.charAt(index))) {
            assert (index >= 0 && index < seq.length());
            return 1;
        }
        int length = seq.length();
        int x = index;
        if (lengthInCodePoints >= 0) {
            assert (index >= 0 && index < length);
            for (int i = 0; x < length && i < lengthInCodePoints; i++) {
                if (Character.isHighSurrogate(seq.charAt(x++))) {
                    if (x < length && Character.isLowSurrogate(seq.charAt(x))) {
                        x++;
                    }
                }
            }
            return x - index;
        }

        assert (index >= 0 && index <= length);
        if (index == 0) {
            return 0;
        }
        int len = -lengthInCodePoints;
        for (int i = 0; x > 0 && i < len; i++) {
            if (Character.isLowSurrogate(seq.charAt(--x))) {
                if (x > 0 && Character.isHighSurrogate(seq.charAt(x-1))) {
                    x--;
                }
            }
        }
        return index - x;
    }

    private static final int countCodePoints(CharSequence seq) {
        int length = seq.length();
        int n = 0;
        for (int i = 0; i < length; ) {
            n++;
            if (Character.isHighSurrogate(seq.charAt(i++))) {
                if (i < length && Character.isLowSurrogate(seq.charAt(i))) {
                    i++;
                }
            }
        }
        return n;
    }

    /**
     *  Creates a bit vector for matching Latin-1 values. A normal BitClass
     *  never matches values above Latin-1, and a complemented BitClass always
     *  matches values above Latin-1.
     * <p>
     *  创建用于匹配Latin-1值的位向量正常的BitClass从不匹配拉丁-1以上的值,并且补充的BitClass总是匹配拉丁文1以上的值
     * 
     */
    private static final class BitClass extends BmpCharProperty {
        final boolean[] bits;
        BitClass() { bits = new boolean[256]; }
        private BitClass(boolean[] bits) { this.bits = bits; }
        BitClass add(int c, int flags) {
            assert c >= 0 && c <= 255;
            if ((flags & CASE_INSENSITIVE) != 0) {
                if (ASCII.isAscii(c)) {
                    bits[ASCII.toUpper(c)] = true;
                    bits[ASCII.toLower(c)] = true;
                } else if ((flags & UNICODE_CASE) != 0) {
                    bits[Character.toLowerCase(c)] = true;
                    bits[Character.toUpperCase(c)] = true;
                }
            }
            bits[c] = true;
            return this;
        }
        boolean isSatisfiedBy(int ch) {
            return ch < 256 && bits[ch];
        }
    }

    /**
     *  Returns a suitably optimized, single character matcher.
     * <p>
     *  返回适当优化的单字符匹配器
     * 
     */
    private CharProperty newSingle(final int ch) {
        if (has(CASE_INSENSITIVE)) {
            int lower, upper;
            if (has(UNICODE_CASE)) {
                upper = Character.toUpperCase(ch);
                lower = Character.toLowerCase(upper);
                if (upper != lower)
                    return new SingleU(lower);
            } else if (ASCII.isAscii(ch)) {
                lower = ASCII.toLower(ch);
                upper = ASCII.toUpper(ch);
                if (lower != upper)
                    return new SingleI(lower, upper);
            }
        }
        if (isSupplementary(ch))
            return new SingleS(ch);    // Match a given Unicode character
        return new Single(ch);         // Match a given BMP character
    }

    /**
     *  Utility method for creating a string slice matcher.
     * <p>
     *  创建字符串切片匹配器的实用方法
     * 
     */
    private Node newSlice(int[] buf, int count, boolean hasSupplementary) {
        int[] tmp = new int[count];
        if (has(CASE_INSENSITIVE)) {
            if (has(UNICODE_CASE)) {
                for (int i = 0; i < count; i++) {
                    tmp[i] = Character.toLowerCase(
                                 Character.toUpperCase(buf[i]));
                }
                return hasSupplementary? new SliceUS(tmp) : new SliceU(tmp);
            }
            for (int i = 0; i < count; i++) {
                tmp[i] = ASCII.toLower(buf[i]);
            }
            return hasSupplementary? new SliceIS(tmp) : new SliceI(tmp);
        }
        for (int i = 0; i < count; i++) {
            tmp[i] = buf[i];
        }
        return hasSupplementary ? new SliceS(tmp) : new Slice(tmp);
    }

    /**
     * The following classes are the building components of the object
     * tree that represents a compiled regular expression. The object tree
     * is made of individual elements that handle constructs in the Pattern.
     * Each type of object knows how to match its equivalent construct with
     * the match() method.
     * <p>
     * 以下类是表示编译正则表达式的对象树的构建组件。对象树由处理模式中的构造的各个元素组成。每种类型的对象知道如何将其等效构造与match()方法匹配
     * 
     */

    /**
     * Base class for all node classes. Subclasses should override the match()
     * method as appropriate. This class is an accepting node, so its match()
     * always returns true.
     * <p>
     *  所有节点类的基类子类应该适当地覆盖match()方法此类是一个接受节点,因此其match()始终返回true
     * 
     */
    static class Node extends Object {
        Node next;
        Node() {
            next = Pattern.accept;
        }
        /**
         * This method implements the classic accept node.
         * <p>
         *  此方法实现经典的接受节点
         * 
         */
        boolean match(Matcher matcher, int i, CharSequence seq) {
            matcher.last = i;
            matcher.groups[0] = matcher.first;
            matcher.groups[1] = matcher.last;
            return true;
        }
        /**
         * This method is good for all zero length assertions.
         * <p>
         *  此方法适用于所有零长度断言
         * 
         */
        boolean study(TreeInfo info) {
            if (next != null) {
                return next.study(info);
            } else {
                return info.deterministic;
            }
        }
    }

    static class LastNode extends Node {
        /**
         * This method implements the classic accept node with
         * the addition of a check to see if the match occurred
         * using all of the input.
         * <p>
         *  此方法实现经典的接受节点,添加一个检查,以查看是否使用所有输入发生匹配
         * 
         */
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (matcher.acceptMode == Matcher.ENDANCHOR && i != matcher.to)
                return false;
            matcher.last = i;
            matcher.groups[0] = matcher.first;
            matcher.groups[1] = matcher.last;
            return true;
        }
    }

    /**
     * Used for REs that can start anywhere within the input string.
     * This basically tries to match repeatedly at each spot in the
     * input string, moving forward after each try. An anchored search
     * or a BnM will bypass this node completely.
     * <p>
     * 用于可以在输入字符串中的任何位置开始的RE这基本上尝试在输入字符串中的每个位置重复匹配,在每次尝试之后向前移动锚定搜索或BnM将完全绕过该节点
     * 
     */
    static class Start extends Node {
        int minLength;
        Start(Node node) {
            this.next = node;
            TreeInfo info = new TreeInfo();
            next.study(info);
            minLength = info.minLength;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (i > matcher.to - minLength) {
                matcher.hitEnd = true;
                return false;
            }
            int guard = matcher.to - minLength;
            for (; i <= guard; i++) {
                if (next.match(matcher, i, seq)) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
            }
            matcher.hitEnd = true;
            return false;
        }
        boolean study(TreeInfo info) {
            next.study(info);
            info.maxValid = false;
            info.deterministic = false;
            return false;
        }
    }

    /*
     * StartS supports supplementary characters, including unpaired surrogates.
     * <p>
     *  StartS支持补充字符,包括不成对的代理
     * 
     */
    static final class StartS extends Start {
        StartS(Node node) {
            super(node);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (i > matcher.to - minLength) {
                matcher.hitEnd = true;
                return false;
            }
            int guard = matcher.to - minLength;
            while (i <= guard) {
                //if ((ret = next.match(matcher, i, seq)) || i == guard)
                if (next.match(matcher, i, seq)) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
                if (i == guard)
                    break;
                // Optimization to move to the next character. This is
                // faster than countChars(seq, i, 1).
                if (Character.isHighSurrogate(seq.charAt(i++))) {
                    if (i < seq.length() &&
                        Character.isLowSurrogate(seq.charAt(i))) {
                        i++;
                    }
                }
            }
            matcher.hitEnd = true;
            return false;
        }
    }

    /**
     * Node to anchor at the beginning of input. This object implements the
     * match for a \A sequence, and the caret anchor will use this if not in
     * multiline mode.
     * <p>
     *  节点锚定在输入的开始此对象实现\\ A序列的匹配,插入符锚将使用此,如果不是在多线模式
     * 
     */
    static final class Begin extends Node {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int fromIndex = (matcher.anchoringBounds) ?
                matcher.from : 0;
            if (i == fromIndex && next.match(matcher, i, seq)) {
                matcher.first = i;
                matcher.groups[0] = i;
                matcher.groups[1] = matcher.last;
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Node to anchor at the end of input. This is the absolute end, so this
     * should not match at the last newline before the end as $ will.
     * <p>
     *  节点锚定在输入的结尾这是绝对结束,所以这应该不匹配在最后一个换行符之前的$将
     * 
     */
    static final class End extends Node {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int endIndex = (matcher.anchoringBounds) ?
                matcher.to : matcher.getTextLength();
            if (i == endIndex) {
                matcher.hitEnd = true;
                return next.match(matcher, i, seq);
            }
            return false;
        }
    }

    /**
     * Node to anchor at the beginning of a line. This is essentially the
     * object to match for the multiline ^.
     * <p>
     *  节点锚定在行的开始这基本上是与多行匹配的对象
     * 
     */
    static final class Caret extends Node {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int startIndex = matcher.from;
            int endIndex = matcher.to;
            if (!matcher.anchoringBounds) {
                startIndex = 0;
                endIndex = matcher.getTextLength();
            }
            // Perl does not match ^ at end of input even after newline
            if (i == endIndex) {
                matcher.hitEnd = true;
                return false;
            }
            if (i > startIndex) {
                char ch = seq.charAt(i-1);
                if (ch != '\n' && ch != '\r'
                    && (ch|1) != '\u2029'
                    && ch != '\u0085' ) {
                    return false;
                }
                // Should treat /r/n as one newline
                if (ch == '\r' && seq.charAt(i) == '\n')
                    return false;
            }
            return next.match(matcher, i, seq);
        }
    }

    /**
     * Node to anchor at the beginning of a line when in unixdot mode.
     * <p>
     *  在unixdot模式下,节点锚定在一行的开头
     * 
     */
    static final class UnixCaret extends Node {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int startIndex = matcher.from;
            int endIndex = matcher.to;
            if (!matcher.anchoringBounds) {
                startIndex = 0;
                endIndex = matcher.getTextLength();
            }
            // Perl does not match ^ at end of input even after newline
            if (i == endIndex) {
                matcher.hitEnd = true;
                return false;
            }
            if (i > startIndex) {
                char ch = seq.charAt(i-1);
                if (ch != '\n') {
                    return false;
                }
            }
            return next.match(matcher, i, seq);
        }
    }

    /**
     * Node to match the location where the last match ended.
     * This is used for the \G construct.
     * <p>
     * 节点匹配最后一个匹配结束的位置这用于\\ G结构
     * 
     */
    static final class LastMatch extends Node {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (i != matcher.oldLast)
                return false;
            return next.match(matcher, i, seq);
        }
    }

    /**
     * Node to anchor at the end of a line or the end of input based on the
     * multiline mode.
     *
     * When not in multiline mode, the $ can only match at the very end
     * of the input, unless the input ends in a line terminator in which
     * it matches right before the last line terminator.
     *
     * Note that \r\n is considered an atomic line terminator.
     *
     * Like ^ the $ operator matches at a position, it does not match the
     * line terminators themselves.
     * <p>
     *  节点锚定在线的末端或基于多行模式的输入结束
     * 
     *  当不处于多线模式时,$只能在输入的最后端匹配,除非输入结束于在最后一行终止符之前匹配的行终止符
     * 
     *  请注意,\\ r\n被认为是原子行终止符
     * 
     *  像^运算符在一个位置匹配,它不匹配行终止符本身
     * 
     */
    static final class Dollar extends Node {
        boolean multiline;
        Dollar(boolean mul) {
            multiline = mul;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int endIndex = (matcher.anchoringBounds) ?
                matcher.to : matcher.getTextLength();
            if (!multiline) {
                if (i < endIndex - 2)
                    return false;
                if (i == endIndex - 2) {
                    char ch = seq.charAt(i);
                    if (ch != '\r')
                        return false;
                    ch = seq.charAt(i + 1);
                    if (ch != '\n')
                        return false;
                }
            }
            // Matches before any line terminator; also matches at the
            // end of input
            // Before line terminator:
            // If multiline, we match here no matter what
            // If not multiline, fall through so that the end
            // is marked as hit; this must be a /r/n or a /n
            // at the very end so the end was hit; more input
            // could make this not match here
            if (i < endIndex) {
                char ch = seq.charAt(i);
                 if (ch == '\n') {
                     // No match between \r\n
                     if (i > 0 && seq.charAt(i-1) == '\r')
                         return false;
                     if (multiline)
                         return next.match(matcher, i, seq);
                 } else if (ch == '\r' || ch == '\u0085' ||
                            (ch|1) == '\u2029') {
                     if (multiline)
                         return next.match(matcher, i, seq);
                 } else { // No line terminator, no match
                     return false;
                 }
            }
            // Matched at current end so hit end
            matcher.hitEnd = true;
            // If a $ matches because of end of input, then more input
            // could cause it to fail!
            matcher.requireEnd = true;
            return next.match(matcher, i, seq);
        }
        boolean study(TreeInfo info) {
            next.study(info);
            return info.deterministic;
        }
    }

    /**
     * Node to anchor at the end of a line or the end of input based on the
     * multiline mode when in unix lines mode.
     * <p>
     *  在unix行模式下,节点锚定在行的结尾或基于多行模式的输入结束
     * 
     */
    static final class UnixDollar extends Node {
        boolean multiline;
        UnixDollar(boolean mul) {
            multiline = mul;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int endIndex = (matcher.anchoringBounds) ?
                matcher.to : matcher.getTextLength();
            if (i < endIndex) {
                char ch = seq.charAt(i);
                if (ch == '\n') {
                    // If not multiline, then only possible to
                    // match at very end or one before end
                    if (multiline == false && i != endIndex - 1)
                        return false;
                    // If multiline return next.match without setting
                    // matcher.hitEnd
                    if (multiline)
                        return next.match(matcher, i, seq);
                } else {
                    return false;
                }
            }
            // Matching because at the end or 1 before the end;
            // more input could change this so set hitEnd
            matcher.hitEnd = true;
            // If a $ matches because of end of input, then more input
            // could cause it to fail!
            matcher.requireEnd = true;
            return next.match(matcher, i, seq);
        }
        boolean study(TreeInfo info) {
            next.study(info);
            return info.deterministic;
        }
    }

    /**
     * Node class that matches a Unicode line ending '\R'
     * <p>
     *  匹配以'\\ R'结尾的Unicode行的节点类
     * 
     */
    static final class LineEnding extends Node {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            // (u+000Du+000A|[u+000Au+000Bu+000Cu+000Du+0085u+2028u+2029])
            if (i < matcher.to) {
                int ch = seq.charAt(i);
                if (ch == 0x0A || ch == 0x0B || ch == 0x0C ||
                    ch == 0x85 || ch == 0x2028 || ch == 0x2029)
                    return next.match(matcher, i + 1, seq);
                if (ch == 0x0D) {
                    i++;
                    if (i < matcher.to && seq.charAt(i) == 0x0A)
                        i++;
                    return next.match(matcher, i, seq);
                }
            } else {
                matcher.hitEnd = true;
            }
            return false;
        }
        boolean study(TreeInfo info) {
            info.minLength++;
            info.maxLength += 2;
            return next.study(info);
        }
    }

    /**
     * Abstract node class to match one character satisfying some
     * boolean property.
     * <p>
     *  抽象节点类匹配一个字符满足一些布尔属性
     * 
     */
    private static abstract class CharProperty extends Node {
        abstract boolean isSatisfiedBy(int ch);
        CharProperty complement() {
            return new CharProperty() {
                    boolean isSatisfiedBy(int ch) {
                        return ! CharProperty.this.isSatisfiedBy(ch);}};
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (i < matcher.to) {
                int ch = Character.codePointAt(seq, i);
                return isSatisfiedBy(ch)
                    && next.match(matcher, i+Character.charCount(ch), seq);
            } else {
                matcher.hitEnd = true;
                return false;
            }
        }
        boolean study(TreeInfo info) {
            info.minLength++;
            info.maxLength++;
            return next.study(info);
        }
    }

    /**
     * Optimized version of CharProperty that works only for
     * properties never satisfied by Supplementary characters.
     * <p>
     * CharProperty的优化版本,仅适用于补充字符不满足的属性
     * 
     */
    private static abstract class BmpCharProperty extends CharProperty {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (i < matcher.to) {
                return isSatisfiedBy(seq.charAt(i))
                    && next.match(matcher, i+1, seq);
            } else {
                matcher.hitEnd = true;
                return false;
            }
        }
    }

    /**
     * Node class that matches a Supplementary Unicode character
     * <p>
     *  与补充Unicode字符匹配的节点类
     * 
     */
    static final class SingleS extends CharProperty {
        final int c;
        SingleS(int c) { this.c = c; }
        boolean isSatisfiedBy(int ch) {
            return ch == c;
        }
    }

    /**
     * Optimization -- matches a given BMP character
     * <p>
     *  优化 - 匹配给定的BMP字符
     * 
     */
    static final class Single extends BmpCharProperty {
        final int c;
        Single(int c) { this.c = c; }
        boolean isSatisfiedBy(int ch) {
            return ch == c;
        }
    }

    /**
     * Case insensitive matches a given BMP character
     * <p>
     *  不区分大小写匹配给定的BMP字符
     * 
     */
    static final class SingleI extends BmpCharProperty {
        final int lower;
        final int upper;
        SingleI(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
        boolean isSatisfiedBy(int ch) {
            return ch == lower || ch == upper;
        }
    }

    /**
     * Unicode case insensitive matches a given Unicode character
     * <p>
     *  Unicode不区分大小写匹配给定的Unicode字符
     * 
     */
    static final class SingleU extends CharProperty {
        final int lower;
        SingleU(int lower) {
            this.lower = lower;
        }
        boolean isSatisfiedBy(int ch) {
            return lower == ch ||
                lower == Character.toLowerCase(Character.toUpperCase(ch));
        }
    }

    /**
     * Node class that matches a Unicode block.
     * <p>
     *  与Unicode块匹配的节点类
     * 
     */
    static final class Block extends CharProperty {
        final Character.UnicodeBlock block;
        Block(Character.UnicodeBlock block) {
            this.block = block;
        }
        boolean isSatisfiedBy(int ch) {
            return block == Character.UnicodeBlock.of(ch);
        }
    }

    /**
     * Node class that matches a Unicode script
     * <p>
     *  与Unicode脚本匹配的节点类
     * 
     */
    static final class Script extends CharProperty {
        final Character.UnicodeScript script;
        Script(Character.UnicodeScript script) {
            this.script = script;
        }
        boolean isSatisfiedBy(int ch) {
            return script == Character.UnicodeScript.of(ch);
        }
    }

    /**
     * Node class that matches a Unicode category.
     * <p>
     *  与Unicode类别匹配的节点类
     * 
     */
    static final class Category extends CharProperty {
        final int typeMask;
        Category(int typeMask) { this.typeMask = typeMask; }
        boolean isSatisfiedBy(int ch) {
            return (typeMask & (1 << Character.getType(ch))) != 0;
        }
    }

    /**
     * Node class that matches a Unicode "type"
     * <p>
     *  与Unicode"类型"匹配的节点类
     * 
     */
    static final class Utype extends CharProperty {
        final UnicodeProp uprop;
        Utype(UnicodeProp uprop) { this.uprop = uprop; }
        boolean isSatisfiedBy(int ch) {
            return uprop.is(ch);
        }
    }

    /**
     * Node class that matches a POSIX type.
     * <p>
     *  与POSIX类型匹配的节点类
     * 
     */
    static final class Ctype extends BmpCharProperty {
        final int ctype;
        Ctype(int ctype) { this.ctype = ctype; }
        boolean isSatisfiedBy(int ch) {
            return ch < 128 && ASCII.isType(ch, ctype);
        }
    }

    /**
     * Node class that matches a Perl vertical whitespace
     * <p>
     *  与Perl垂直空格匹配的节点类
     * 
     */
    static final class VertWS extends BmpCharProperty {
        boolean isSatisfiedBy(int cp) {
            return (cp >= 0x0A && cp <= 0x0D) ||
                   cp == 0x85 || cp == 0x2028 || cp == 0x2029;
        }
    }

    /**
     * Node class that matches a Perl horizontal whitespace
     * <p>
     *  与Perl水平空格匹配的节点类
     * 
     */
    static final class HorizWS extends BmpCharProperty {
        boolean isSatisfiedBy(int cp) {
            return cp == 0x09 || cp == 0x20 || cp == 0xa0 ||
                   cp == 0x1680 || cp == 0x180e ||
                   cp >= 0x2000 && cp <= 0x200a ||
                   cp == 0x202f || cp == 0x205f || cp == 0x3000;
        }
    }

    /**
     * Base class for all Slice nodes
     * <p>
     *  所有Slice节点的基类
     * 
     */
    static class SliceNode extends Node {
        int[] buffer;
        SliceNode(int[] buf) {
            buffer = buf;
        }
        boolean study(TreeInfo info) {
            info.minLength += buffer.length;
            info.maxLength += buffer.length;
            return next.study(info);
        }
    }

    /**
     * Node class for a case sensitive/BMP-only sequence of literal
     * characters.
     * <p>
     *  区分大小写的纯文本字符序列的节点类
     * 
     */
    static final class Slice extends SliceNode {
        Slice(int[] buf) {
            super(buf);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] buf = buffer;
            int len = buf.length;
            for (int j=0; j<len; j++) {
                if ((i+j) >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                if (buf[j] != seq.charAt(i+j))
                    return false;
            }
            return next.match(matcher, i+len, seq);
        }
    }

    /**
     * Node class for a case_insensitive/BMP-only sequence of literal
     * characters.
     * <p>
     * 用于case_insensitive / BMP纯文字字符序列的节点类
     * 
     */
    static class SliceI extends SliceNode {
        SliceI(int[] buf) {
            super(buf);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] buf = buffer;
            int len = buf.length;
            for (int j=0; j<len; j++) {
                if ((i+j) >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                int c = seq.charAt(i+j);
                if (buf[j] != c &&
                    buf[j] != ASCII.toLower(c))
                    return false;
            }
            return next.match(matcher, i+len, seq);
        }
    }

    /**
     * Node class for a unicode_case_insensitive/BMP-only sequence of
     * literal characters. Uses unicode case folding.
     * <p>
     *  unicode_case_insensitive / BMP纯文字字符序列的节点类使用Unicode字符大小写折叠
     * 
     */
    static final class SliceU extends SliceNode {
        SliceU(int[] buf) {
            super(buf);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] buf = buffer;
            int len = buf.length;
            for (int j=0; j<len; j++) {
                if ((i+j) >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                int c = seq.charAt(i+j);
                if (buf[j] != c &&
                    buf[j] != Character.toLowerCase(Character.toUpperCase(c)))
                    return false;
            }
            return next.match(matcher, i+len, seq);
        }
    }

    /**
     * Node class for a case sensitive sequence of literal characters
     * including supplementary characters.
     * <p>
     *  文字字符的区分大小写的序列的节点类,包括补充字符
     * 
     */
    static final class SliceS extends SliceNode {
        SliceS(int[] buf) {
            super(buf);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] buf = buffer;
            int x = i;
            for (int j = 0; j < buf.length; j++) {
                if (x >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                int c = Character.codePointAt(seq, x);
                if (buf[j] != c)
                    return false;
                x += Character.charCount(c);
                if (x > matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
            }
            return next.match(matcher, x, seq);
        }
    }

    /**
     * Node class for a case insensitive sequence of literal characters
     * including supplementary characters.
     * <p>
     *  包含补充字符的文字字符不区分大小写的序列的节点类
     * 
     */
    static class SliceIS extends SliceNode {
        SliceIS(int[] buf) {
            super(buf);
        }
        int toLower(int c) {
            return ASCII.toLower(c);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] buf = buffer;
            int x = i;
            for (int j = 0; j < buf.length; j++) {
                if (x >= matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                int c = Character.codePointAt(seq, x);
                if (buf[j] != c && buf[j] != toLower(c))
                    return false;
                x += Character.charCount(c);
                if (x > matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
            }
            return next.match(matcher, x, seq);
        }
    }

    /**
     * Node class for a case insensitive sequence of literal characters.
     * Uses unicode case folding.
     * <p>
     *  不区分大小写的字符序列的节点类使用Unicode字符大小写折叠
     * 
     */
    static final class SliceUS extends SliceIS {
        SliceUS(int[] buf) {
            super(buf);
        }
        int toLower(int c) {
            return Character.toLowerCase(Character.toUpperCase(c));
        }
    }

    private static boolean inRange(int lower, int ch, int upper) {
        return lower <= ch && ch <= upper;
    }

    /**
     * Returns node for matching characters within an explicit value range.
     * <p>
     *  返回显式值范围内匹配字符的节点
     * 
     */
    private static CharProperty rangeFor(final int lower,
                                         final int upper) {
        return new CharProperty() {
                boolean isSatisfiedBy(int ch) {
                    return inRange(lower, ch, upper);}};
    }

    /**
     * Returns node for matching characters within an explicit value
     * range in a case insensitive manner.
     * <p>
     *  以不区分大小写的方式返回显式值范围内匹配字符的节点
     * 
     */
    private CharProperty caseInsensitiveRangeFor(final int lower,
                                                 final int upper) {
        if (has(UNICODE_CASE))
            return new CharProperty() {
                boolean isSatisfiedBy(int ch) {
                    if (inRange(lower, ch, upper))
                        return true;
                    int up = Character.toUpperCase(ch);
                    return inRange(lower, up, upper) ||
                           inRange(lower, Character.toLowerCase(up), upper);}};
        return new CharProperty() {
            boolean isSatisfiedBy(int ch) {
                return inRange(lower, ch, upper) ||
                    ASCII.isAscii(ch) &&
                        (inRange(lower, ASCII.toUpper(ch), upper) ||
                         inRange(lower, ASCII.toLower(ch), upper));
            }};
    }

    /**
     * Implements the Unicode category ALL and the dot metacharacter when
     * in dotall mode.
     * <p>
     *  在dotall模式下,实现Unicode类别ALL和点元字符
     * 
     */
    static final class All extends CharProperty {
        boolean isSatisfiedBy(int ch) {
            return true;
        }
    }

    /**
     * Node class for the dot metacharacter when dotall is not enabled.
     * <p>
     * 未启用dotall时,点元字符的节点类
     * 
     */
    static final class Dot extends CharProperty {
        boolean isSatisfiedBy(int ch) {
            return (ch != '\n' && ch != '\r'
                    && (ch|1) != '\u2029'
                    && ch != '\u0085');
        }
    }

    /**
     * Node class for the dot metacharacter when dotall is not enabled
     * but UNIX_LINES is enabled.
     * <p>
     *  未启用dotall但启用了UNIX_LINES时,点元字符的节点类
     * 
     */
    static final class UnixDot extends CharProperty {
        boolean isSatisfiedBy(int ch) {
            return ch != '\n';
        }
    }

    /**
     * The 0 or 1 quantifier. This one class implements all three types.
     * <p>
     *  0或1量词这一个类实现所有三种类型
     * 
     */
    static final class Ques extends Node {
        Node atom;
        int type;
        Ques(Node node, int type) {
            this.atom = node;
            this.type = type;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            switch (type) {
            case GREEDY:
                return (atom.match(matcher, i, seq) && next.match(matcher, matcher.last, seq))
                    || next.match(matcher, i, seq);
            case LAZY:
                return next.match(matcher, i, seq)
                    || (atom.match(matcher, i, seq) && next.match(matcher, matcher.last, seq));
            case POSSESSIVE:
                if (atom.match(matcher, i, seq)) i = matcher.last;
                return next.match(matcher, i, seq);
            default:
                return atom.match(matcher, i, seq) && next.match(matcher, matcher.last, seq);
            }
        }
        boolean study(TreeInfo info) {
            if (type != INDEPENDENT) {
                int minL = info.minLength;
                atom.study(info);
                info.minLength = minL;
                info.deterministic = false;
                return next.study(info);
            } else {
                atom.study(info);
                return next.study(info);
            }
        }
    }

    /**
     * Handles the curly-brace style repetition with a specified minimum and
     * maximum occurrences. The * quantifier is handled as a special case.
     * This class handles the three types.
     * <p>
     *  处理具有指定的最小和最大出现的大括号风格重复。*量词作为特殊情况处理此类处理三种类型
     * 
     */
    static final class Curly extends Node {
        Node atom;
        int type;
        int cmin;
        int cmax;

        Curly(Node node, int cmin, int cmax, int type) {
            this.atom = node;
            this.type = type;
            this.cmin = cmin;
            this.cmax = cmax;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int j;
            for (j = 0; j < cmin; j++) {
                if (atom.match(matcher, i, seq)) {
                    i = matcher.last;
                    continue;
                }
                return false;
            }
            if (type == GREEDY)
                return match0(matcher, i, j, seq);
            else if (type == LAZY)
                return match1(matcher, i, j, seq);
            else
                return match2(matcher, i, j, seq);
        }
        // Greedy match.
        // i is the index to start matching at
        // j is the number of atoms that have matched
        boolean match0(Matcher matcher, int i, int j, CharSequence seq) {
            if (j >= cmax) {
                // We have matched the maximum... continue with the rest of
                // the regular expression
                return next.match(matcher, i, seq);
            }
            int backLimit = j;
            while (atom.match(matcher, i, seq)) {
                // k is the length of this match
                int k = matcher.last - i;
                if (k == 0) // Zero length match
                    break;
                // Move up index and number matched
                i = matcher.last;
                j++;
                // We are greedy so match as many as we can
                while (j < cmax) {
                    if (!atom.match(matcher, i, seq))
                        break;
                    if (i + k != matcher.last) {
                        if (match0(matcher, matcher.last, j+1, seq))
                            return true;
                        break;
                    }
                    i += k;
                    j++;
                }
                // Handle backing off if match fails
                while (j >= backLimit) {
                   if (next.match(matcher, i, seq))
                        return true;
                    i -= k;
                    j--;
                }
                return false;
            }
            return next.match(matcher, i, seq);
        }
        // Reluctant match. At this point, the minimum has been satisfied.
        // i is the index to start matching at
        // j is the number of atoms that have matched
        boolean match1(Matcher matcher, int i, int j, CharSequence seq) {
            for (;;) {
                // Try finishing match without consuming any more
                if (next.match(matcher, i, seq))
                    return true;
                // At the maximum, no match found
                if (j >= cmax)
                    return false;
                // Okay, must try one more atom
                if (!atom.match(matcher, i, seq))
                    return false;
                // If we haven't moved forward then must break out
                if (i == matcher.last)
                    return false;
                // Move up index and number matched
                i = matcher.last;
                j++;
            }
        }
        boolean match2(Matcher matcher, int i, int j, CharSequence seq) {
            for (; j < cmax; j++) {
                if (!atom.match(matcher, i, seq))
                    break;
                if (i == matcher.last)
                    break;
                i = matcher.last;
            }
            return next.match(matcher, i, seq);
        }
        boolean study(TreeInfo info) {
            // Save original info
            int minL = info.minLength;
            int maxL = info.maxLength;
            boolean maxV = info.maxValid;
            boolean detm = info.deterministic;
            info.reset();

            atom.study(info);

            int temp = info.minLength * cmin + minL;
            if (temp < minL) {
                temp = 0xFFFFFFF; // arbitrary large number
            }
            info.minLength = temp;

            if (maxV & info.maxValid) {
                temp = info.maxLength * cmax + maxL;
                info.maxLength = temp;
                if (temp < maxL) {
                    info.maxValid = false;
                }
            } else {
                info.maxValid = false;
            }

            if (info.deterministic && cmin == cmax)
                info.deterministic = detm;
            else
                info.deterministic = false;
            return next.study(info);
        }
    }

    /**
     * Handles the curly-brace style repetition with a specified minimum and
     * maximum occurrences in deterministic cases. This is an iterative
     * optimization over the Prolog and Loop system which would handle this
     * in a recursive way. The * quantifier is handled as a special case.
     * If capture is true then this class saves group settings and ensures
     * that groups are unset when backing off of a group match.
     * <p>
     *  在确定性情况下使用指定的最小和最大出现次数处理卷曲括号样式重复这是对Prolog和循环系统的迭代优化,它将以递归方式处理这个*量词作为特殊情况处理如果capture为true,此类保存组设置,并确保在
     * 退出组匹配时取消设置组。
     * 
     */
    static final class GroupCurly extends Node {
        Node atom;
        int type;
        int cmin;
        int cmax;
        int localIndex;
        int groupIndex;
        boolean capture;

        GroupCurly(Node node, int cmin, int cmax, int type, int local,
                   int group, boolean capture) {
            this.atom = node;
            this.type = type;
            this.cmin = cmin;
            this.cmax = cmax;
            this.localIndex = local;
            this.groupIndex = group;
            this.capture = capture;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] groups = matcher.groups;
            int[] locals = matcher.locals;
            int save0 = locals[localIndex];
            int save1 = 0;
            int save2 = 0;

            if (capture) {
                save1 = groups[groupIndex];
                save2 = groups[groupIndex+1];
            }

            // Notify GroupTail there is no need to setup group info
            // because it will be set here
            locals[localIndex] = -1;

            boolean ret = true;
            for (int j = 0; j < cmin; j++) {
                if (atom.match(matcher, i, seq)) {
                    if (capture) {
                        groups[groupIndex] = i;
                        groups[groupIndex+1] = matcher.last;
                    }
                    i = matcher.last;
                } else {
                    ret = false;
                    break;
                }
            }
            if (ret) {
                if (type == GREEDY) {
                    ret = match0(matcher, i, cmin, seq);
                } else if (type == LAZY) {
                    ret = match1(matcher, i, cmin, seq);
                } else {
                    ret = match2(matcher, i, cmin, seq);
                }
            }
            if (!ret) {
                locals[localIndex] = save0;
                if (capture) {
                    groups[groupIndex] = save1;
                    groups[groupIndex+1] = save2;
                }
            }
            return ret;
        }
        // Aggressive group match
        boolean match0(Matcher matcher, int i, int j, CharSequence seq) {
            // don't back off passing the starting "j"
            int min = j;
            int[] groups = matcher.groups;
            int save0 = 0;
            int save1 = 0;
            if (capture) {
                save0 = groups[groupIndex];
                save1 = groups[groupIndex+1];
            }
            for (;;) {
                if (j >= cmax)
                    break;
                if (!atom.match(matcher, i, seq))
                    break;
                int k = matcher.last - i;
                if (k <= 0) {
                    if (capture) {
                        groups[groupIndex] = i;
                        groups[groupIndex+1] = i + k;
                    }
                    i = i + k;
                    break;
                }
                for (;;) {
                    if (capture) {
                        groups[groupIndex] = i;
                        groups[groupIndex+1] = i + k;
                    }
                    i = i + k;
                    if (++j >= cmax)
                        break;
                    if (!atom.match(matcher, i, seq))
                        break;
                    if (i + k != matcher.last) {
                        if (match0(matcher, i, j, seq))
                            return true;
                        break;
                    }
                }
                while (j > min) {
                    if (next.match(matcher, i, seq)) {
                        if (capture) {
                            groups[groupIndex+1] = i;
                            groups[groupIndex] = i - k;
                        }
                        return true;
                    }
                    // backing off
                    i = i - k;
                    if (capture) {
                        groups[groupIndex+1] = i;
                        groups[groupIndex] = i - k;
                    }
                    j--;

                }
                break;
            }
            if (capture) {
                groups[groupIndex] = save0;
                groups[groupIndex+1] = save1;
            }
            return next.match(matcher, i, seq);
        }
        // Reluctant matching
        boolean match1(Matcher matcher, int i, int j, CharSequence seq) {
            for (;;) {
                if (next.match(matcher, i, seq))
                    return true;
                if (j >= cmax)
                    return false;
                if (!atom.match(matcher, i, seq))
                    return false;
                if (i == matcher.last)
                    return false;
                if (capture) {
                    matcher.groups[groupIndex] = i;
                    matcher.groups[groupIndex+1] = matcher.last;
                }
                i = matcher.last;
                j++;
            }
        }
        // Possessive matching
        boolean match2(Matcher matcher, int i, int j, CharSequence seq) {
            for (; j < cmax; j++) {
                if (!atom.match(matcher, i, seq)) {
                    break;
                }
                if (capture) {
                    matcher.groups[groupIndex] = i;
                    matcher.groups[groupIndex+1] = matcher.last;
                }
                if (i == matcher.last) {
                    break;
                }
                i = matcher.last;
            }
            return next.match(matcher, i, seq);
        }
        boolean study(TreeInfo info) {
            // Save original info
            int minL = info.minLength;
            int maxL = info.maxLength;
            boolean maxV = info.maxValid;
            boolean detm = info.deterministic;
            info.reset();

            atom.study(info);

            int temp = info.minLength * cmin + minL;
            if (temp < minL) {
                temp = 0xFFFFFFF; // Arbitrary large number
            }
            info.minLength = temp;

            if (maxV & info.maxValid) {
                temp = info.maxLength * cmax + maxL;
                info.maxLength = temp;
                if (temp < maxL) {
                    info.maxValid = false;
                }
            } else {
                info.maxValid = false;
            }

            if (info.deterministic && cmin == cmax) {
                info.deterministic = detm;
            } else {
                info.deterministic = false;
            }
            return next.study(info);
        }
    }

    /**
     * A Guard node at the end of each atom node in a Branch. It
     * serves the purpose of chaining the "match" operation to
     * "next" but not the "study", so we can collect the TreeInfo
     * of each atom node without including the TreeInfo of the
     * "next".
     * <p>
     * 分支中每个原子节点末尾的Guard节点用于将"匹配"操作链接到"下一个"而不是"研究",因此我们可以收集每个原子节点的TreeInfo,而不包括TreeInfo下一个"
     * 
     */
    static final class BranchConn extends Node {
        BranchConn() {};
        boolean match(Matcher matcher, int i, CharSequence seq) {
            return next.match(matcher, i, seq);
        }
        boolean study(TreeInfo info) {
            return info.deterministic;
        }
    }

    /**
     * Handles the branching of alternations. Note this is also used for
     * the ? quantifier to branch between the case where it matches once
     * and where it does not occur.
     * <p>
     *  处理交替的分支注意这也用于?量化器在其匹配一次和不发生的情况之间分支
     * 
     */
    static final class Branch extends Node {
        Node[] atoms = new Node[2];
        int size = 2;
        Node conn;
        Branch(Node first, Node second, Node branchConn) {
            conn = branchConn;
            atoms[0] = first;
            atoms[1] = second;
        }

        void add(Node node) {
            if (size >= atoms.length) {
                Node[] tmp = new Node[atoms.length*2];
                System.arraycopy(atoms, 0, tmp, 0, atoms.length);
                atoms = tmp;
            }
            atoms[size++] = node;
        }

        boolean match(Matcher matcher, int i, CharSequence seq) {
            for (int n = 0; n < size; n++) {
                if (atoms[n] == null) {
                    if (conn.next.match(matcher, i, seq))
                        return true;
                } else if (atoms[n].match(matcher, i, seq)) {
                    return true;
                }
            }
            return false;
        }

        boolean study(TreeInfo info) {
            int minL = info.minLength;
            int maxL = info.maxLength;
            boolean maxV = info.maxValid;

            int minL2 = Integer.MAX_VALUE; //arbitrary large enough num
            int maxL2 = -1;
            for (int n = 0; n < size; n++) {
                info.reset();
                if (atoms[n] != null)
                    atoms[n].study(info);
                minL2 = Math.min(minL2, info.minLength);
                maxL2 = Math.max(maxL2, info.maxLength);
                maxV = (maxV & info.maxValid);
            }

            minL += minL2;
            maxL += maxL2;

            info.reset();
            conn.next.study(info);

            info.minLength += minL;
            info.maxLength += maxL;
            info.maxValid &= maxV;
            info.deterministic = false;
            return false;
        }
    }

    /**
     * The GroupHead saves the location where the group begins in the locals
     * and restores them when the match is done.
     *
     * The matchRef is used when a reference to this group is accessed later
     * in the expression. The locals will have a negative value in them to
     * indicate that we do not want to unset the group if the reference
     * doesn't match.
     * <p>
     *  GroupHead保存组在本地开始的位置,并在匹配完成后恢复它们
     * 
     *  当在表达式中稍后访问对此组的引用时,使用matchRef。本地值在其中具有负值,以指示如果引用不匹配,我们不想取消设置组
     * 
     */
    static final class GroupHead extends Node {
        int localIndex;
        GroupHead(int localCount) {
            localIndex = localCount;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int save = matcher.locals[localIndex];
            matcher.locals[localIndex] = i;
            boolean ret = next.match(matcher, i, seq);
            matcher.locals[localIndex] = save;
            return ret;
        }
        boolean matchRef(Matcher matcher, int i, CharSequence seq) {
            int save = matcher.locals[localIndex];
            matcher.locals[localIndex] = ~i; // HACK
            boolean ret = next.match(matcher, i, seq);
            matcher.locals[localIndex] = save;
            return ret;
        }
    }

    /**
     * Recursive reference to a group in the regular expression. It calls
     * matchRef because if the reference fails to match we would not unset
     * the group.
     * <p>
     * 递归引用正则表达式中的组它调用matchRef,因为如果引用不匹配,我们不会取消设置组
     * 
     */
    static final class GroupRef extends Node {
        GroupHead head;
        GroupRef(GroupHead head) {
            this.head = head;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            return head.matchRef(matcher, i, seq)
                && next.match(matcher, matcher.last, seq);
        }
        boolean study(TreeInfo info) {
            info.maxValid = false;
            info.deterministic = false;
            return next.study(info);
        }
    }

    /**
     * The GroupTail handles the setting of group beginning and ending
     * locations when groups are successfully matched. It must also be able to
     * unset groups that have to be backed off of.
     *
     * The GroupTail node is also used when a previous group is referenced,
     * and in that case no group information needs to be set.
     * <p>
     *  当组成功匹配时,GroupTail处理组开始和结束位置的设置。它还必须能够取消设置必须回退的组
     * 
     *  当引用前一个组时,也会使用GroupTail节点,在这种情况下,不需要设置组信息
     * 
     */
    static final class GroupTail extends Node {
        int localIndex;
        int groupIndex;
        GroupTail(int localCount, int groupCount) {
            localIndex = localCount;
            groupIndex = groupCount + groupCount;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int tmp = matcher.locals[localIndex];
            if (tmp >= 0) { // This is the normal group case.
                // Save the group so we can unset it if it
                // backs off of a match.
                int groupStart = matcher.groups[groupIndex];
                int groupEnd = matcher.groups[groupIndex+1];

                matcher.groups[groupIndex] = tmp;
                matcher.groups[groupIndex+1] = i;
                if (next.match(matcher, i, seq)) {
                    return true;
                }
                matcher.groups[groupIndex] = groupStart;
                matcher.groups[groupIndex+1] = groupEnd;
                return false;
            } else {
                // This is a group reference case. We don't need to save any
                // group info because it isn't really a group.
                matcher.last = i;
                return true;
            }
        }
    }

    /**
     * This sets up a loop to handle a recursive quantifier structure.
     * <p>
     *  这设置了一个循环来处理递归的量词结构
     * 
     */
    static final class Prolog extends Node {
        Loop loop;
        Prolog(Loop loop) {
            this.loop = loop;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            return loop.matchInit(matcher, i, seq);
        }
        boolean study(TreeInfo info) {
            return loop.study(info);
        }
    }

    /**
     * Handles the repetition count for a greedy Curly. The matchInit
     * is called from the Prolog to save the index of where the group
     * beginning is stored. A zero length group check occurs in the
     * normal match but is skipped in the matchInit.
     * <p>
     *  处理贪心Curly的重复计数从Prolog中调用matchInit以保存存储组开头的索引零长度组检查在正常匹配中发生,但在matchInit中被跳过
     * 
     */
    static class Loop extends Node {
        Node body;
        int countIndex; // local count index in matcher locals
        int beginIndex; // group beginning index
        int cmin, cmax;
        Loop(int countIndex, int beginIndex) {
            this.countIndex = countIndex;
            this.beginIndex = beginIndex;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            // Avoid infinite loop in zero-length case.
            if (i > matcher.locals[beginIndex]) {
                int count = matcher.locals[countIndex];

                // This block is for before we reach the minimum
                // iterations required for the loop to match
                if (count < cmin) {
                    matcher.locals[countIndex] = count + 1;
                    boolean b = body.match(matcher, i, seq);
                    // If match failed we must backtrack, so
                    // the loop count should NOT be incremented
                    if (!b)
                        matcher.locals[countIndex] = count;
                    // Return success or failure since we are under
                    // minimum
                    return b;
                }
                // This block is for after we have the minimum
                // iterations required for the loop to match
                if (count < cmax) {
                    matcher.locals[countIndex] = count + 1;
                    boolean b = body.match(matcher, i, seq);
                    // If match failed we must backtrack, so
                    // the loop count should NOT be incremented
                    if (!b)
                        matcher.locals[countIndex] = count;
                    else
                        return true;
                }
            }
            return next.match(matcher, i, seq);
        }
        boolean matchInit(Matcher matcher, int i, CharSequence seq) {
            int save = matcher.locals[countIndex];
            boolean ret = false;
            if (0 < cmin) {
                matcher.locals[countIndex] = 1;
                ret = body.match(matcher, i, seq);
            } else if (0 < cmax) {
                matcher.locals[countIndex] = 1;
                ret = body.match(matcher, i, seq);
                if (ret == false)
                    ret = next.match(matcher, i, seq);
            } else {
                ret = next.match(matcher, i, seq);
            }
            matcher.locals[countIndex] = save;
            return ret;
        }
        boolean study(TreeInfo info) {
            info.maxValid = false;
            info.deterministic = false;
            return false;
        }
    }

    /**
     * Handles the repetition count for a reluctant Curly. The matchInit
     * is called from the Prolog to save the index of where the group
     * beginning is stored. A zero length group check occurs in the
     * normal match but is skipped in the matchInit.
     * <p>
     * 处理不愿意的Curly的重复计数从Prolog中调用matchInit以保存存储组开头的索引零长度组检查在正常匹配中发生,但在matchInit中被跳过
     * 
     */
    static final class LazyLoop extends Loop {
        LazyLoop(int countIndex, int beginIndex) {
            super(countIndex, beginIndex);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            // Check for zero length group
            if (i > matcher.locals[beginIndex]) {
                int count = matcher.locals[countIndex];
                if (count < cmin) {
                    matcher.locals[countIndex] = count + 1;
                    boolean result = body.match(matcher, i, seq);
                    // If match failed we must backtrack, so
                    // the loop count should NOT be incremented
                    if (!result)
                        matcher.locals[countIndex] = count;
                    return result;
                }
                if (next.match(matcher, i, seq))
                    return true;
                if (count < cmax) {
                    matcher.locals[countIndex] = count + 1;
                    boolean result = body.match(matcher, i, seq);
                    // If match failed we must backtrack, so
                    // the loop count should NOT be incremented
                    if (!result)
                        matcher.locals[countIndex] = count;
                    return result;
                }
                return false;
            }
            return next.match(matcher, i, seq);
        }
        boolean matchInit(Matcher matcher, int i, CharSequence seq) {
            int save = matcher.locals[countIndex];
            boolean ret = false;
            if (0 < cmin) {
                matcher.locals[countIndex] = 1;
                ret = body.match(matcher, i, seq);
            } else if (next.match(matcher, i, seq)) {
                ret = true;
            } else if (0 < cmax) {
                matcher.locals[countIndex] = 1;
                ret = body.match(matcher, i, seq);
            }
            matcher.locals[countIndex] = save;
            return ret;
        }
        boolean study(TreeInfo info) {
            info.maxValid = false;
            info.deterministic = false;
            return false;
        }
    }

    /**
     * Refers to a group in the regular expression. Attempts to match
     * whatever the group referred to last matched.
     * <p>
     *  引用正则表达式中的一个组尝试匹配上次匹配的引用的组
     * 
     */
    static class BackRef extends Node {
        int groupIndex;
        BackRef(int groupCount) {
            super();
            groupIndex = groupCount + groupCount;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int j = matcher.groups[groupIndex];
            int k = matcher.groups[groupIndex+1];

            int groupSize = k - j;
            // If the referenced group didn't match, neither can this
            if (j < 0)
                return false;

            // If there isn't enough input left no match
            if (i + groupSize > matcher.to) {
                matcher.hitEnd = true;
                return false;
            }
            // Check each new char to make sure it matches what the group
            // referenced matched last time around
            for (int index=0; index<groupSize; index++)
                if (seq.charAt(i+index) != seq.charAt(j+index))
                    return false;

            return next.match(matcher, i+groupSize, seq);
        }
        boolean study(TreeInfo info) {
            info.maxValid = false;
            return next.study(info);
        }
    }

    static class CIBackRef extends Node {
        int groupIndex;
        boolean doUnicodeCase;
        CIBackRef(int groupCount, boolean doUnicodeCase) {
            super();
            groupIndex = groupCount + groupCount;
            this.doUnicodeCase = doUnicodeCase;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int j = matcher.groups[groupIndex];
            int k = matcher.groups[groupIndex+1];

            int groupSize = k - j;

            // If the referenced group didn't match, neither can this
            if (j < 0)
                return false;

            // If there isn't enough input left no match
            if (i + groupSize > matcher.to) {
                matcher.hitEnd = true;
                return false;
            }

            // Check each new char to make sure it matches what the group
            // referenced matched last time around
            int x = i;
            for (int index=0; index<groupSize; index++) {
                int c1 = Character.codePointAt(seq, x);
                int c2 = Character.codePointAt(seq, j);
                if (c1 != c2) {
                    if (doUnicodeCase) {
                        int cc1 = Character.toUpperCase(c1);
                        int cc2 = Character.toUpperCase(c2);
                        if (cc1 != cc2 &&
                            Character.toLowerCase(cc1) !=
                            Character.toLowerCase(cc2))
                            return false;
                    } else {
                        if (ASCII.toLower(c1) != ASCII.toLower(c2))
                            return false;
                    }
                }
                x += Character.charCount(c1);
                j += Character.charCount(c2);
            }

            return next.match(matcher, i+groupSize, seq);
        }
        boolean study(TreeInfo info) {
            info.maxValid = false;
            return next.study(info);
        }
    }

    /**
     * Searches until the next instance of its atom. This is useful for
     * finding the atom efficiently without passing an instance of it
     * (greedy problem) and without a lot of wasted search time (reluctant
     * problem).
     * <p>
     *  搜索直到其原子的下一个实例这对于有效地查找原子而不通过其实例(贪婪问题)和没有大量​​浪费的搜索时间(不情愿的问题)是有用的,
     * 
     */
    static final class First extends Node {
        Node atom;
        First(Node node) {
            this.atom = BnM.optimize(node);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (atom instanceof BnM) {
                return atom.match(matcher, i, seq)
                    && next.match(matcher, matcher.last, seq);
            }
            for (;;) {
                if (i > matcher.to) {
                    matcher.hitEnd = true;
                    return false;
                }
                if (atom.match(matcher, i, seq)) {
                    return next.match(matcher, matcher.last, seq);
                }
                i += countChars(seq, i, 1);
                matcher.first++;
            }
        }
        boolean study(TreeInfo info) {
            atom.study(info);
            info.maxValid = false;
            info.deterministic = false;
            return next.study(info);
        }
    }

    static final class Conditional extends Node {
        Node cond, yes, not;
        Conditional(Node cond, Node yes, Node not) {
            this.cond = cond;
            this.yes = yes;
            this.not = not;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            if (cond.match(matcher, i, seq)) {
                return yes.match(matcher, i, seq);
            } else {
                return not.match(matcher, i, seq);
            }
        }
        boolean study(TreeInfo info) {
            int minL = info.minLength;
            int maxL = info.maxLength;
            boolean maxV = info.maxValid;
            info.reset();
            yes.study(info);

            int minL2 = info.minLength;
            int maxL2 = info.maxLength;
            boolean maxV2 = info.maxValid;
            info.reset();
            not.study(info);

            info.minLength = minL + Math.min(minL2, info.minLength);
            info.maxLength = maxL + Math.max(maxL2, info.maxLength);
            info.maxValid = (maxV & maxV2 & info.maxValid);
            info.deterministic = false;
            return next.study(info);
        }
    }

    /**
     * Zero width positive lookahead.
     * <p>
     *  零宽正前瞻
     * 
     */
    static final class Pos extends Node {
        Node cond;
        Pos(Node cond) {
            this.cond = cond;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int savedTo = matcher.to;
            boolean conditionMatched = false;

            // Relax transparent region boundaries for lookahead
            if (matcher.transparentBounds)
                matcher.to = matcher.getTextLength();
            try {
                conditionMatched = cond.match(matcher, i, seq);
            } finally {
                // Reinstate region boundaries
                matcher.to = savedTo;
            }
            return conditionMatched && next.match(matcher, i, seq);
        }
    }

    /**
     * Zero width negative lookahead.
     * <p>
     *  零宽度负前瞻
     * 
     */
    static final class Neg extends Node {
        Node cond;
        Neg(Node cond) {
            this.cond = cond;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int savedTo = matcher.to;
            boolean conditionMatched = false;

            // Relax transparent region boundaries for lookahead
            if (matcher.transparentBounds)
                matcher.to = matcher.getTextLength();
            try {
                if (i < matcher.to) {
                    conditionMatched = !cond.match(matcher, i, seq);
                } else {
                    // If a negative lookahead succeeds then more input
                    // could cause it to fail!
                    matcher.requireEnd = true;
                    conditionMatched = !cond.match(matcher, i, seq);
                }
            } finally {
                // Reinstate region boundaries
                matcher.to = savedTo;
            }
            return conditionMatched && next.match(matcher, i, seq);
        }
    }

    /**
     * For use with lookbehinds; matches the position where the lookbehind
     * was encountered.
     * <p>
     *  用于lookbehinds;匹配遇到lookbehind的位置
     * 
     */
    static Node lookbehindEnd = new Node() {
        boolean match(Matcher matcher, int i, CharSequence seq) {
            return i == matcher.lookbehindTo;
        }
    };

    /**
     * Zero width positive lookbehind.
     * <p>
     *  零宽度正后仰
     * 
     */
    static class Behind extends Node {
        Node cond;
        int rmax, rmin;
        Behind(Node cond, int rmax, int rmin) {
            this.cond = cond;
            this.rmax = rmax;
            this.rmin = rmin;
        }

        boolean match(Matcher matcher, int i, CharSequence seq) {
            int savedFrom = matcher.from;
            boolean conditionMatched = false;
            int startIndex = (!matcher.transparentBounds) ?
                             matcher.from : 0;
            int from = Math.max(i - rmax, startIndex);
            // Set end boundary
            int savedLBT = matcher.lookbehindTo;
            matcher.lookbehindTo = i;
            // Relax transparent region boundaries for lookbehind
            if (matcher.transparentBounds)
                matcher.from = 0;
            for (int j = i - rmin; !conditionMatched && j >= from; j--) {
                conditionMatched = cond.match(matcher, j, seq);
            }
            matcher.from = savedFrom;
            matcher.lookbehindTo = savedLBT;
            return conditionMatched && next.match(matcher, i, seq);
        }
    }

    /**
     * Zero width positive lookbehind, including supplementary
     * characters or unpaired surrogates.
     * <p>
     * 零宽度正后仰,包括补充字符或不成对的代理
     * 
     */
    static final class BehindS extends Behind {
        BehindS(Node cond, int rmax, int rmin) {
            super(cond, rmax, rmin);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int rmaxChars = countChars(seq, i, -rmax);
            int rminChars = countChars(seq, i, -rmin);
            int savedFrom = matcher.from;
            int startIndex = (!matcher.transparentBounds) ?
                             matcher.from : 0;
            boolean conditionMatched = false;
            int from = Math.max(i - rmaxChars, startIndex);
            // Set end boundary
            int savedLBT = matcher.lookbehindTo;
            matcher.lookbehindTo = i;
            // Relax transparent region boundaries for lookbehind
            if (matcher.transparentBounds)
                matcher.from = 0;

            for (int j = i - rminChars;
                 !conditionMatched && j >= from;
                 j -= j>from ? countChars(seq, j, -1) : 1) {
                conditionMatched = cond.match(matcher, j, seq);
            }
            matcher.from = savedFrom;
            matcher.lookbehindTo = savedLBT;
            return conditionMatched && next.match(matcher, i, seq);
        }
    }

    /**
     * Zero width negative lookbehind.
     * <p>
     *  零宽度负后备
     * 
     */
    static class NotBehind extends Node {
        Node cond;
        int rmax, rmin;
        NotBehind(Node cond, int rmax, int rmin) {
            this.cond = cond;
            this.rmax = rmax;
            this.rmin = rmin;
        }

        boolean match(Matcher matcher, int i, CharSequence seq) {
            int savedLBT = matcher.lookbehindTo;
            int savedFrom = matcher.from;
            boolean conditionMatched = false;
            int startIndex = (!matcher.transparentBounds) ?
                             matcher.from : 0;
            int from = Math.max(i - rmax, startIndex);
            matcher.lookbehindTo = i;
            // Relax transparent region boundaries for lookbehind
            if (matcher.transparentBounds)
                matcher.from = 0;
            for (int j = i - rmin; !conditionMatched && j >= from; j--) {
                conditionMatched = cond.match(matcher, j, seq);
            }
            // Reinstate region boundaries
            matcher.from = savedFrom;
            matcher.lookbehindTo = savedLBT;
            return !conditionMatched && next.match(matcher, i, seq);
        }
    }

    /**
     * Zero width negative lookbehind, including supplementary
     * characters or unpaired surrogates.
     * <p>
     *  零宽度负期望,包括补充字符或不成对的代理
     * 
     */
    static final class NotBehindS extends NotBehind {
        NotBehindS(Node cond, int rmax, int rmin) {
            super(cond, rmax, rmin);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int rmaxChars = countChars(seq, i, -rmax);
            int rminChars = countChars(seq, i, -rmin);
            int savedFrom = matcher.from;
            int savedLBT = matcher.lookbehindTo;
            boolean conditionMatched = false;
            int startIndex = (!matcher.transparentBounds) ?
                             matcher.from : 0;
            int from = Math.max(i - rmaxChars, startIndex);
            matcher.lookbehindTo = i;
            // Relax transparent region boundaries for lookbehind
            if (matcher.transparentBounds)
                matcher.from = 0;
            for (int j = i - rminChars;
                 !conditionMatched && j >= from;
                 j -= j>from ? countChars(seq, j, -1) : 1) {
                conditionMatched = cond.match(matcher, j, seq);
            }
            //Reinstate region boundaries
            matcher.from = savedFrom;
            matcher.lookbehindTo = savedLBT;
            return !conditionMatched && next.match(matcher, i, seq);
        }
    }

    /**
     * Returns the set union of two CharProperty nodes.
     * <p>
     *  返回两个CharProperty节点的集合
     * 
     */
    private static CharProperty union(final CharProperty lhs,
                                      final CharProperty rhs) {
        return new CharProperty() {
                boolean isSatisfiedBy(int ch) {
                    return lhs.isSatisfiedBy(ch) || rhs.isSatisfiedBy(ch);}};
    }

    /**
     * Returns the set intersection of two CharProperty nodes.
     * <p>
     *  返回两个CharProperty节点的交集
     * 
     */
    private static CharProperty intersection(final CharProperty lhs,
                                             final CharProperty rhs) {
        return new CharProperty() {
                boolean isSatisfiedBy(int ch) {
                    return lhs.isSatisfiedBy(ch) && rhs.isSatisfiedBy(ch);}};
    }

    /**
     * Returns the set difference of two CharProperty nodes.
     * <p>
     *  返回两个CharProperty节点的设置差异
     * 
     */
    private static CharProperty setDifference(final CharProperty lhs,
                                              final CharProperty rhs) {
        return new CharProperty() {
                boolean isSatisfiedBy(int ch) {
                    return ! rhs.isSatisfiedBy(ch) && lhs.isSatisfiedBy(ch);}};
    }

    /**
     * Handles word boundaries. Includes a field to allow this one class to
     * deal with the different types of word boundaries we can match. The word
     * characters include underscores, letters, and digits. Non spacing marks
     * can are also part of a word if they have a base character, otherwise
     * they are ignored for purposes of finding word boundaries.
     * <p>
     *  句柄字边界包括一个字段,允许这一类处理不同类型的字边界,我们可以匹配。字符包括下划线,字母和数字。非间距标记也可以是字的一部分,如果它们有基本字符,否则为了找到单词边界而被忽略
     * 
     */
    static final class Bound extends Node {
        static int LEFT = 0x1;
        static int RIGHT= 0x2;
        static int BOTH = 0x3;
        static int NONE = 0x4;
        int type;
        boolean useUWORD;
        Bound(int n, boolean useUWORD) {
            type = n;
            this.useUWORD = useUWORD;
        }

        boolean isWord(int ch) {
            return useUWORD ? UnicodeProp.WORD.is(ch)
                            : (ch == '_' || Character.isLetterOrDigit(ch));
        }

        int check(Matcher matcher, int i, CharSequence seq) {
            int ch;
            boolean left = false;
            int startIndex = matcher.from;
            int endIndex = matcher.to;
            if (matcher.transparentBounds) {
                startIndex = 0;
                endIndex = matcher.getTextLength();
            }
            if (i > startIndex) {
                ch = Character.codePointBefore(seq, i);
                left = (isWord(ch) ||
                    ((Character.getType(ch) == Character.NON_SPACING_MARK)
                     && hasBaseCharacter(matcher, i-1, seq)));
            }
            boolean right = false;
            if (i < endIndex) {
                ch = Character.codePointAt(seq, i);
                right = (isWord(ch) ||
                    ((Character.getType(ch) == Character.NON_SPACING_MARK)
                     && hasBaseCharacter(matcher, i, seq)));
            } else {
                // Tried to access char past the end
                matcher.hitEnd = true;
                // The addition of another char could wreck a boundary
                matcher.requireEnd = true;
            }
            return ((left ^ right) ? (right ? LEFT : RIGHT) : NONE);
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            return (check(matcher, i, seq) & type) > 0
                && next.match(matcher, i, seq);
        }
    }

    /**
     * Non spacing marks only count as word characters in bounds calculations
     * if they have a base character.
     * <p>
     * 如果具有基本字符,则非间距标记仅在边界计算中计为字字符
     * 
     */
    private static boolean hasBaseCharacter(Matcher matcher, int i,
                                            CharSequence seq)
    {
        int start = (!matcher.transparentBounds) ?
            matcher.from : 0;
        for (int x=i; x >= start; x--) {
            int ch = Character.codePointAt(seq, x);
            if (Character.isLetterOrDigit(ch))
                return true;
            if (Character.getType(ch) == Character.NON_SPACING_MARK)
                continue;
            return false;
        }
        return false;
    }

    /**
     * Attempts to match a slice in the input using the Boyer-Moore string
     * matching algorithm. The algorithm is based on the idea that the
     * pattern can be shifted farther ahead in the search text if it is
     * matched right to left.
     * <p>
     * The pattern is compared to the input one character at a time, from
     * the rightmost character in the pattern to the left. If the characters
     * all match the pattern has been found. If a character does not match,
     * the pattern is shifted right a distance that is the maximum of two
     * functions, the bad character shift and the good suffix shift. This
     * shift moves the attempted match position through the input more
     * quickly than a naive one position at a time check.
     * <p>
     * The bad character shift is based on the character from the text that
     * did not match. If the character does not appear in the pattern, the
     * pattern can be shifted completely beyond the bad character. If the
     * character does occur in the pattern, the pattern can be shifted to
     * line the pattern up with the next occurrence of that character.
     * <p>
     * The good suffix shift is based on the idea that some subset on the right
     * side of the pattern has matched. When a bad character is found, the
     * pattern can be shifted right by the pattern length if the subset does
     * not occur again in pattern, or by the amount of distance to the
     * next occurrence of the subset in the pattern.
     *
     * Boyer-Moore search methods adapted from code by Amy Yu.
     * <p>
     *  尝试使用Boyer-Moore字符串匹配算法匹配输入中的切片该算法基于如下思想：如果匹配从右到左匹配,则模式可以在搜索文本中向前移动得更远
     * <p>
     *  将模式与输入的一个字符(从模式中最右边的字符到左边)进行比较如果已找到所有匹配模式的字符,如果字符不匹配,则模式向右移动一个距离,即最多两个函数,错误的字符移位和良好的后缀移位这种移位在一个时间检查上
     * 比初始的一个位置更快地移动尝试的匹配位置通过输入。
     * <p>
     * 错误的字符移位基于不匹配的文本中的字符如果字符没有出现在模式中,则模式可以完全移动到错误字符之外。如果字符在模式中出现,则模式可以移位以将该模式与该字符的下一个出现对齐
     * <p>
     *  良好的后缀移位是基于这样的想法,即图案右侧的一些子集已经匹配。当发现坏的字符时,如果子集在图案中不再出现,则图案可以向右移动图案长度,或者到模式中子集的下一次出现的距离的量
     * 
     *  Boyer-Moore的搜索方法改编自Amy Yu的代码
     * 
     */
    static class BnM extends Node {
        int[] buffer;
        int[] lastOcc;
        int[] optoSft;

        /**
         * Pre calculates arrays needed to generate the bad character
         * shift and the good suffix shift. Only the last seven bits
         * are used to see if chars match; This keeps the tables small
         * and covers the heavily used ASCII range, but occasionally
         * results in an aliased match for the bad character shift.
         * <p>
         * Pre计算产生错误字符移位和良好后缀移位所需的数组只有最后7位用于查看chars是否匹配;这会使表格保持较小,并覆盖大量使用的ASCII范围,但偶尔会导致错误字符移位的别名匹配
         * 
         */
        static Node optimize(Node node) {
            if (!(node instanceof Slice)) {
                return node;
            }

            int[] src = ((Slice) node).buffer;
            int patternLength = src.length;
            // The BM algorithm requires a bit of overhead;
            // If the pattern is short don't use it, since
            // a shift larger than the pattern length cannot
            // be used anyway.
            if (patternLength < 4) {
                return node;
            }
            int i, j, k;
            int[] lastOcc = new int[128];
            int[] optoSft = new int[patternLength];
            // Precalculate part of the bad character shift
            // It is a table for where in the pattern each
            // lower 7-bit value occurs
            for (i = 0; i < patternLength; i++) {
                lastOcc[src[i]&0x7F] = i + 1;
            }
            // Precalculate the good suffix shift
            // i is the shift amount being considered
NEXT:       for (i = patternLength; i > 0; i--) {
                // j is the beginning index of suffix being considered
                for (j = patternLength - 1; j >= i; j--) {
                    // Testing for good suffix
                    if (src[j] == src[j-i]) {
                        // src[j..len] is a good suffix
                        optoSft[j-1] = i;
                    } else {
                        // No match. The array has already been
                        // filled up with correct values before.
                        continue NEXT;
                    }
                }
                // This fills up the remaining of optoSft
                // any suffix can not have larger shift amount
                // then its sub-suffix. Why???
                while (j > 0) {
                    optoSft[--j] = i;
                }
            }
            // Set the guard value because of unicode compression
            optoSft[patternLength-1] = 1;
            if (node instanceof SliceS)
                return new BnMS(src, lastOcc, optoSft, node.next);
            return new BnM(src, lastOcc, optoSft, node.next);
        }
        BnM(int[] src, int[] lastOcc, int[] optoSft, Node next) {
            this.buffer = src;
            this.lastOcc = lastOcc;
            this.optoSft = optoSft;
            this.next = next;
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] src = buffer;
            int patternLength = src.length;
            int last = matcher.to - patternLength;

            // Loop over all possible match positions in text
NEXT:       while (i <= last) {
                // Loop over pattern from right to left
                for (int j = patternLength - 1; j >= 0; j--) {
                    int ch = seq.charAt(i+j);
                    if (ch != src[j]) {
                        // Shift search to the right by the maximum of the
                        // bad character shift and the good suffix shift
                        i += Math.max(j + 1 - lastOcc[ch&0x7F], optoSft[j]);
                        continue NEXT;
                    }
                }
                // Entire pattern matched starting at i
                matcher.first = i;
                boolean ret = next.match(matcher, i + patternLength, seq);
                if (ret) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
                i++;
            }
            // BnM is only used as the leading node in the unanchored case,
            // and it replaced its Start() which always searches to the end
            // if it doesn't find what it's looking for, so hitEnd is true.
            matcher.hitEnd = true;
            return false;
        }
        boolean study(TreeInfo info) {
            info.minLength += buffer.length;
            info.maxValid = false;
            return next.study(info);
        }
    }

    /**
     * Supplementary support version of BnM(). Unpaired surrogates are
     * also handled by this class.
     * <p>
     *  BnM()的补充支持版本此类也处理未配对的代理
     * 
     */
    static final class BnMS extends BnM {
        int lengthInChars;

        BnMS(int[] src, int[] lastOcc, int[] optoSft, Node next) {
            super(src, lastOcc, optoSft, next);
            for (int x = 0; x < buffer.length; x++) {
                lengthInChars += Character.charCount(buffer[x]);
            }
        }
        boolean match(Matcher matcher, int i, CharSequence seq) {
            int[] src = buffer;
            int patternLength = src.length;
            int last = matcher.to - lengthInChars;

            // Loop over all possible match positions in text
NEXT:       while (i <= last) {
                // Loop over pattern from right to left
                int ch;
                for (int j = countChars(seq, i, patternLength), x = patternLength - 1;
                     j > 0; j -= Character.charCount(ch), x--) {
                    ch = Character.codePointBefore(seq, i+j);
                    if (ch != src[x]) {
                        // Shift search to the right by the maximum of the
                        // bad character shift and the good suffix shift
                        int n = Math.max(x + 1 - lastOcc[ch&0x7F], optoSft[x]);
                        i += countChars(seq, i, n);
                        continue NEXT;
                    }
                }
                // Entire pattern matched starting at i
                matcher.first = i;
                boolean ret = next.match(matcher, i + lengthInChars, seq);
                if (ret) {
                    matcher.first = i;
                    matcher.groups[0] = matcher.first;
                    matcher.groups[1] = matcher.last;
                    return true;
                }
                i += countChars(seq, i, 1);
            }
            matcher.hitEnd = true;
            return false;
        }
    }

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////

    /**
     *  This must be the very first initializer.
     * <p>
     *  这必须是第一个初始化程序
     * 
     */
    static Node accept = new Node();

    static Node lastAccept = new LastNode();

    private static class CharPropertyNames {

        static CharProperty charPropertyFor(String name) {
            CharPropertyFactory m = map.get(name);
            return m == null ? null : m.make();
        }

        private static abstract class CharPropertyFactory {
            abstract CharProperty make();
        }

        private static void defCategory(String name,
                                        final int typeMask) {
            map.put(name, new CharPropertyFactory() {
                    CharProperty make() { return new Category(typeMask);}});
        }

        private static void defRange(String name,
                                     final int lower, final int upper) {
            map.put(name, new CharPropertyFactory() {
                    CharProperty make() { return rangeFor(lower, upper);}});
        }

        private static void defCtype(String name,
                                     final int ctype) {
            map.put(name, new CharPropertyFactory() {
                    CharProperty make() { return new Ctype(ctype);}});
        }

        private static abstract class CloneableProperty
            extends CharProperty implements Cloneable
        {
            public CloneableProperty clone() {
                try {
                    return (CloneableProperty) super.clone();
                } catch (CloneNotSupportedException e) {
                    throw new AssertionError(e);
                }
            }
        }

        private static void defClone(String name,
                                     final CloneableProperty p) {
            map.put(name, new CharPropertyFactory() {
                    CharProperty make() { return p.clone();}});
        }

        private static final HashMap<String, CharPropertyFactory> map
            = new HashMap<>();

        static {
            // Unicode character property aliases, defined in
            // http://www.unicode.org/Public/UNIDATA/PropertyValueAliases.txt
            defCategory("Cn", 1<<Character.UNASSIGNED);
            defCategory("Lu", 1<<Character.UPPERCASE_LETTER);
            defCategory("Ll", 1<<Character.LOWERCASE_LETTER);
            defCategory("Lt", 1<<Character.TITLECASE_LETTER);
            defCategory("Lm", 1<<Character.MODIFIER_LETTER);
            defCategory("Lo", 1<<Character.OTHER_LETTER);
            defCategory("Mn", 1<<Character.NON_SPACING_MARK);
            defCategory("Me", 1<<Character.ENCLOSING_MARK);
            defCategory("Mc", 1<<Character.COMBINING_SPACING_MARK);
            defCategory("Nd", 1<<Character.DECIMAL_DIGIT_NUMBER);
            defCategory("Nl", 1<<Character.LETTER_NUMBER);
            defCategory("No", 1<<Character.OTHER_NUMBER);
            defCategory("Zs", 1<<Character.SPACE_SEPARATOR);
            defCategory("Zl", 1<<Character.LINE_SEPARATOR);
            defCategory("Zp", 1<<Character.PARAGRAPH_SEPARATOR);
            defCategory("Cc", 1<<Character.CONTROL);
            defCategory("Cf", 1<<Character.FORMAT);
            defCategory("Co", 1<<Character.PRIVATE_USE);
            defCategory("Cs", 1<<Character.SURROGATE);
            defCategory("Pd", 1<<Character.DASH_PUNCTUATION);
            defCategory("Ps", 1<<Character.START_PUNCTUATION);
            defCategory("Pe", 1<<Character.END_PUNCTUATION);
            defCategory("Pc", 1<<Character.CONNECTOR_PUNCTUATION);
            defCategory("Po", 1<<Character.OTHER_PUNCTUATION);
            defCategory("Sm", 1<<Character.MATH_SYMBOL);
            defCategory("Sc", 1<<Character.CURRENCY_SYMBOL);
            defCategory("Sk", 1<<Character.MODIFIER_SYMBOL);
            defCategory("So", 1<<Character.OTHER_SYMBOL);
            defCategory("Pi", 1<<Character.INITIAL_QUOTE_PUNCTUATION);
            defCategory("Pf", 1<<Character.FINAL_QUOTE_PUNCTUATION);
            defCategory("L", ((1<<Character.UPPERCASE_LETTER) |
                              (1<<Character.LOWERCASE_LETTER) |
                              (1<<Character.TITLECASE_LETTER) |
                              (1<<Character.MODIFIER_LETTER)  |
                              (1<<Character.OTHER_LETTER)));
            defCategory("M", ((1<<Character.NON_SPACING_MARK) |
                              (1<<Character.ENCLOSING_MARK)   |
                              (1<<Character.COMBINING_SPACING_MARK)));
            defCategory("N", ((1<<Character.DECIMAL_DIGIT_NUMBER) |
                              (1<<Character.LETTER_NUMBER)        |
                              (1<<Character.OTHER_NUMBER)));
            defCategory("Z", ((1<<Character.SPACE_SEPARATOR) |
                              (1<<Character.LINE_SEPARATOR)  |
                              (1<<Character.PARAGRAPH_SEPARATOR)));
            defCategory("C", ((1<<Character.CONTROL)     |
                              (1<<Character.FORMAT)      |
                              (1<<Character.PRIVATE_USE) |
                              (1<<Character.SURROGATE))); // Other
            defCategory("P", ((1<<Character.DASH_PUNCTUATION)      |
                              (1<<Character.START_PUNCTUATION)     |
                              (1<<Character.END_PUNCTUATION)       |
                              (1<<Character.CONNECTOR_PUNCTUATION) |
                              (1<<Character.OTHER_PUNCTUATION)     |
                              (1<<Character.INITIAL_QUOTE_PUNCTUATION) |
                              (1<<Character.FINAL_QUOTE_PUNCTUATION)));
            defCategory("S", ((1<<Character.MATH_SYMBOL)     |
                              (1<<Character.CURRENCY_SYMBOL) |
                              (1<<Character.MODIFIER_SYMBOL) |
                              (1<<Character.OTHER_SYMBOL)));
            defCategory("LC", ((1<<Character.UPPERCASE_LETTER) |
                               (1<<Character.LOWERCASE_LETTER) |
                               (1<<Character.TITLECASE_LETTER)));
            defCategory("LD", ((1<<Character.UPPERCASE_LETTER) |
                               (1<<Character.LOWERCASE_LETTER) |
                               (1<<Character.TITLECASE_LETTER) |
                               (1<<Character.MODIFIER_LETTER)  |
                               (1<<Character.OTHER_LETTER)     |
                               (1<<Character.DECIMAL_DIGIT_NUMBER)));
            defRange("L1", 0x00, 0xFF); // Latin-1
            map.put("all", new CharPropertyFactory() {
                    CharProperty make() { return new All(); }});

            // Posix regular expression character classes, defined in
            // http://www.unix.org/onlinepubs/009695399/basedefs/xbd_chap09.html
            defRange("ASCII", 0x00, 0x7F);   // ASCII
            defCtype("Alnum", ASCII.ALNUM);  // Alphanumeric characters
            defCtype("Alpha", ASCII.ALPHA);  // Alphabetic characters
            defCtype("Blank", ASCII.BLANK);  // Space and tab characters
            defCtype("Cntrl", ASCII.CNTRL);  // Control characters
            defRange("Digit", '0', '9');     // Numeric characters
            defCtype("Graph", ASCII.GRAPH);  // printable and visible
            defRange("Lower", 'a', 'z');     // Lower-case alphabetic
            defRange("Print", 0x20, 0x7E);   // Printable characters
            defCtype("Punct", ASCII.PUNCT);  // Punctuation characters
            defCtype("Space", ASCII.SPACE);  // Space characters
            defRange("Upper", 'A', 'Z');     // Upper-case alphabetic
            defCtype("XDigit",ASCII.XDIGIT); // hexadecimal digits

            // Java character properties, defined by methods in Character.java
            defClone("javaLowerCase", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isLowerCase(ch);}});
            defClone("javaUpperCase", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isUpperCase(ch);}});
            defClone("javaAlphabetic", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isAlphabetic(ch);}});
            defClone("javaIdeographic", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isIdeographic(ch);}});
            defClone("javaTitleCase", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isTitleCase(ch);}});
            defClone("javaDigit", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isDigit(ch);}});
            defClone("javaDefined", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isDefined(ch);}});
            defClone("javaLetter", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isLetter(ch);}});
            defClone("javaLetterOrDigit", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isLetterOrDigit(ch);}});
            defClone("javaJavaIdentifierStart", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isJavaIdentifierStart(ch);}});
            defClone("javaJavaIdentifierPart", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isJavaIdentifierPart(ch);}});
            defClone("javaUnicodeIdentifierStart", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isUnicodeIdentifierStart(ch);}});
            defClone("javaUnicodeIdentifierPart", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isUnicodeIdentifierPart(ch);}});
            defClone("javaIdentifierIgnorable", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isIdentifierIgnorable(ch);}});
            defClone("javaSpaceChar", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isSpaceChar(ch);}});
            defClone("javaWhitespace", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isWhitespace(ch);}});
            defClone("javaISOControl", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isISOControl(ch);}});
            defClone("javaMirrored", new CloneableProperty() {
                boolean isSatisfiedBy(int ch) {
                    return Character.isMirrored(ch);}});
        }
    }

    /**
     * Creates a predicate which can be used to match a string.
     *
     * <p>
     *  创建一个可用于匹配字符串的谓词
     * 
     * 
     * @return  The predicate which can be used for matching on a string
     * @since   1.8
     */
    public Predicate<String> asPredicate() {
        return s -> matcher(s).find();
    }

    /**
     * Creates a stream from the given input sequence around matches of this
     * pattern.
     *
     * <p> The stream returned by this method contains each substring of the
     * input sequence that is terminated by another subsequence that matches
     * this pattern or is terminated by the end of the input sequence.  The
     * substrings in the stream are in the order in which they occur in the
     * input. Trailing empty strings will be discarded and not encountered in
     * the stream.
     *
     * <p> If this pattern does not match any subsequence of the input then
     * the resulting stream has just one element, namely the input sequence in
     * string form.
     *
     * <p> When there is a positive-width match at the beginning of the input
     * sequence then an empty leading substring is included at the beginning
     * of the stream. A zero-width match at the beginning however never produces
     * such empty leading substring.
     *
     * <p> If the input sequence is mutable, it must remain constant during the
     * execution of the terminal stream operation.  Otherwise, the result of the
     * terminal stream operation is undefined.
     *
     * <p>
     *  从给定输入序列创建围绕此模式匹配的流
     * 
     * <p>此方法返回的流包含输入序列的每个子字符串,由匹配此模式的另一个子序列终止,或由输入序列的末尾终止。流中的子字符串按照它们出现的顺序输入Trailing空字符串将被丢弃,并且不会在流中遇到
     * 
     *  <p>如果此模式与输入的任何子序列不匹配,则生成的流只有一个元素,即字符串形式的输入序列
     * 
     * @param   input
     *          The character sequence to be split
     *
     * @return  The stream of strings computed by splitting the input
     *          around matches of this pattern
     * @see     #split(CharSequence)
     * @since   1.8
     */
    public Stream<String> splitAsStream(final CharSequence input) {
        class MatcherIterator implements Iterator<String> {
            private final Matcher matcher;
            // The start position of the next sub-sequence of input
            // when current == input.length there are no more elements
            private int current;
            // null if the next element, if any, needs to obtained
            private String nextElement;
            // > 0 if there are N next empty elements
            private int emptyElementCount;

            MatcherIterator() {
                this.matcher = matcher(input);
            }

            public String next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                if (emptyElementCount == 0) {
                    String n = nextElement;
                    nextElement = null;
                    return n;
                } else {
                    emptyElementCount--;
                    return "";
                }
            }

            public boolean hasNext() {
                if (nextElement != null || emptyElementCount > 0)
                    return true;

                if (current == input.length())
                    return false;

                // Consume the next matching element
                // Count sequence of matching empty elements
                while (matcher.find()) {
                    nextElement = input.subSequence(current, matcher.start()).toString();
                    current = matcher.end();
                    if (!nextElement.isEmpty()) {
                        return true;
                    } else if (current > 0) { // no empty leading substring for zero-width
                                              // match at the beginning of the input
                        emptyElementCount++;
                    }
                }

                // Consume last matching element
                nextElement = input.subSequence(current, input.length()).toString();
                current = input.length();
                if (!nextElement.isEmpty()) {
                    return true;
                } else {
                    // Ignore a terminal sequence of matching empty elements
                    emptyElementCount = 0;
                    nextElement = null;
                    return false;
                }
            }
        }
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                new MatcherIterator(), Spliterator.ORDERED | Spliterator.NONNULL), false);
    }
}
