/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.lang.*;

/**
 * The string tokenizer class allows an application to break a
 * string into tokens. The tokenization method is much simpler than
 * the one used by the <code>StreamTokenizer</code> class. The
 * <code>StringTokenizer</code> methods do not distinguish among
 * identifiers, numbers, and quoted strings, nor do they recognize
 * and skip comments.
 * <p>
 * The set of delimiters (the characters that separate tokens) may
 * be specified either at creation time or on a per-token basis.
 * <p>
 * An instance of <code>StringTokenizer</code> behaves in one of two
 * ways, depending on whether it was created with the
 * <code>returnDelims</code> flag having the value <code>true</code>
 * or <code>false</code>:
 * <ul>
 * <li>If the flag is <code>false</code>, delimiter characters serve to
 *     separate tokens. A token is a maximal sequence of consecutive
 *     characters that are not delimiters.
 * <li>If the flag is <code>true</code>, delimiter characters are themselves
 *     considered to be tokens. A token is thus either one delimiter
 *     character, or a maximal sequence of consecutive characters that are
 *     not delimiters.
 * </ul><p>
 * A <tt>StringTokenizer</tt> object internally maintains a current
 * position within the string to be tokenized. Some operations advance this
 * current position past the characters processed.<p>
 * A token is returned by taking a substring of the string that was used to
 * create the <tt>StringTokenizer</tt> object.
 * <p>
 * The following is one example of the use of the tokenizer. The code:
 * <blockquote><pre>
 *     StringTokenizer st = new StringTokenizer("this is a test");
 *     while (st.hasMoreTokens()) {
 *         System.out.println(st.nextToken());
 *     }
 * </pre></blockquote>
 * <p>
 * prints the following output:
 * <blockquote><pre>
 *     this
 *     is
 *     a
 *     test
 * </pre></blockquote>
 *
 * <p>
 * <tt>StringTokenizer</tt> is a legacy class that is retained for
 * compatibility reasons although its use is discouraged in new code. It is
 * recommended that anyone seeking this functionality use the <tt>split</tt>
 * method of <tt>String</tt> or the java.util.regex package instead.
 * <p>
 * The following example illustrates how the <tt>String.split</tt>
 * method can be used to break up a string into its basic tokens:
 * <blockquote><pre>
 *     String[] result = "this is a test".split("\\s");
 *     for (int x=0; x&lt;result.length; x++)
 *         System.out.println(result[x]);
 * </pre></blockquote>
 * <p>
 * prints the following output:
 * <blockquote><pre>
 *     this
 *     is
 *     a
 *     test
 * </pre></blockquote>
 *
 * <p>
 *  字符串tokenizer类允许应用程序将字符串拆分为令牌。标记化方法比由<code> StreamTokenizer </code>类使用的方法简单得多。
 *  <code> StringTokenizer </code>方法不区分标识符,数字和带引号的字符串,也不识别和跳过注释。
 * <p>
 *  可以在创建时或基于每个令牌指定分隔符集(分隔令牌的字符)。
 * <p>
 *  &lt; code&gt;&gt; StringTokenizer&lt; / code&gt;的实例以两种方式之一进行,取决于它是用<code> returnDelims </code> false
 *  </code>：。
 * <ul>
 * <li>如果标记是<code> false </code>,分隔符字符用于分隔标记。令牌是不是定界符的连续字符的最大序列。
 *  <li>如果标记是<code> true </code>,则分隔符字符本身被视为标记。因此,令牌是一个定界符字符,或者是不是定界符的连续字符的最大序列。
 *  </ul> <p> <tt> StringTokenizer </tt>对象在内部维护要进行标记化的字符串中的当前位置。有些操作将当前位置推进到处理的字符之前。
 * <p>通过获取用于创建<tt> StringTokenizer </tt>对象的字符串的子字符串返回一个标记。
 * <p>
 *  以下是使用分词器的一个示例。
 * 代码：<blockquote> <pre> StringTokenizer st = new StringTokenizer("this is a test"); while(st.hasMoreTok
 * ens()){System.out.println(st.nextToken()); } </pre> </blockquote>。
 *  以下是使用分词器的一个示例。
 * <p>
 *  打印以下输出：<blockquote> <pre>这是一个测试</pre> </blockquote>
 * 
 * <p>
 *  <tt> StringTokenizer </tt>是为了兼容性原因而保留的旧类,尽管在新代码中不建议使用它。
 * 建议尝试此功能的任何人使用<tt> String </tt>或java.util.regex包的<tt> split </tt>方法。
 * <p>
 * 以下示例说明如何使用<tt> String.split </tt>方法将字符串拆分为其基本标记：<blockquote> <pre> String [] result ="this is a test"
 * .split "\\ s"); for(int x = 0; x&lt; result.length; x ++)System.out.println(result [x]); </pre> </blockquote>
 * 。
 * <p>
 *  打印以下输出：<blockquote> <pre>这是一个测试</pre> </blockquote>
 * 
 * 
 * @author  unascribed
 * @see     java.io.StreamTokenizer
 * @since   JDK1.0
 */
public
class StringTokenizer implements Enumeration<Object> {
    private int currentPosition;
    private int newPosition;
    private int maxPosition;
    private String str;
    private String delimiters;
    private boolean retDelims;
    private boolean delimsChanged;

    /**
     * maxDelimCodePoint stores the value of the delimiter character with the
     * highest value. It is used to optimize the detection of delimiter
     * characters.
     *
     * It is unlikely to provide any optimization benefit in the
     * hasSurrogates case because most string characters will be
     * smaller than the limit, but we keep it so that the two code
     * paths remain similar.
     * <p>
     *  maxDelimCodePoint存储具有最高值的定界符字符的值。它用于优化分隔符字符的检测。
     * 
     *  它不可能在hasSurrogates的情况下提供任何优化的好处,因为大多数字符串字符将小于限制,但我们保留,以使两个代码路径保持类似。
     * 
     */
    private int maxDelimCodePoint;

    /**
     * If delimiters include any surrogates (including surrogate
     * pairs), hasSurrogates is true and the tokenizer uses the
     * different code path. This is because String.indexOf(int)
     * doesn't handle unpaired surrogates as a single character.
     * <p>
     *  如果分隔符包括任何代理(包括代理对),hasSurrogates为true,并且令牌器使用不同的代码路径。这是因为String.indexOf(int)不会将不成对的代理作为单个字符处理。
     * 
     */
    private boolean hasSurrogates = false;

    /**
     * When hasSurrogates is true, delimiters are converted to code
     * points and isDelimiter(int) is used to determine if the given
     * codepoint is a delimiter.
     * <p>
     *  当hasSurrogates为true时,分隔符将转换为代码点,isDelimiter(int)用于确定给定的代码点是否为分隔符。
     * 
     */
    private int[] delimiterCodePoints;

    /**
     * Set maxDelimCodePoint to the highest char in the delimiter set.
     * <p>
     *  将maxDelimCodePoint设置为分隔符集中的最高字符。
     * 
     */
    private void setMaxDelimCodePoint() {
        if (delimiters == null) {
            maxDelimCodePoint = 0;
            return;
        }

        int m = 0;
        int c;
        int count = 0;
        for (int i = 0; i < delimiters.length(); i += Character.charCount(c)) {
            c = delimiters.charAt(i);
            if (c >= Character.MIN_HIGH_SURROGATE && c <= Character.MAX_LOW_SURROGATE) {
                c = delimiters.codePointAt(i);
                hasSurrogates = true;
            }
            if (m < c)
                m = c;
            count++;
        }
        maxDelimCodePoint = m;

        if (hasSurrogates) {
            delimiterCodePoints = new int[count];
            for (int i = 0, j = 0; i < count; i++, j += Character.charCount(c)) {
                c = delimiters.codePointAt(j);
                delimiterCodePoints[i] = c;
            }
        }
    }

    /**
     * Constructs a string tokenizer for the specified string. All
     * characters in the <code>delim</code> argument are the delimiters
     * for separating tokens.
     * <p>
     * If the <code>returnDelims</code> flag is <code>true</code>, then
     * the delimiter characters are also returned as tokens. Each
     * delimiter is returned as a string of length one. If the flag is
     * <code>false</code>, the delimiter characters are skipped and only
     * serve as separators between tokens.
     * <p>
     * Note that if <tt>delim</tt> is <tt>null</tt>, this constructor does
     * not throw an exception. However, trying to invoke other methods on the
     * resulting <tt>StringTokenizer</tt> may result in a
     * <tt>NullPointerException</tt>.
     *
     * <p>
     *  为指定的字符串构造一个字符串tokenizer。 <code> delim </code>参数中的所有字符都是用于分隔令牌的分隔符。
     * <p>
     * 如果<code> returnDelims </code>标志是<code> true </code>,那么分隔符字符也将作为标志返回。每个定界符返回为长度为1的字符串。
     * 如果标志是<code> false </code>,那么将跳过分隔符字符,并且仅用作令牌之间的分隔符。
     * <p>
     *  请注意,如果<tt> delim </tt>是<tt> null </tt>,则此构造函数不会抛出异常。
     * 但是,尝试在生成的<tt> StringTokenizer </tt>上调用其他方法可能会导致<tt> NullPointerException </tt>。
     * 
     * 
     * @param   str            a string to be parsed.
     * @param   delim          the delimiters.
     * @param   returnDelims   flag indicating whether to return the delimiters
     *                         as tokens.
     * @exception NullPointerException if str is <CODE>null</CODE>
     */
    public StringTokenizer(String str, String delim, boolean returnDelims) {
        currentPosition = 0;
        newPosition = -1;
        delimsChanged = false;
        this.str = str;
        maxPosition = str.length();
        delimiters = delim;
        retDelims = returnDelims;
        setMaxDelimCodePoint();
    }

    /**
     * Constructs a string tokenizer for the specified string. The
     * characters in the <code>delim</code> argument are the delimiters
     * for separating tokens. Delimiter characters themselves will not
     * be treated as tokens.
     * <p>
     * Note that if <tt>delim</tt> is <tt>null</tt>, this constructor does
     * not throw an exception. However, trying to invoke other methods on the
     * resulting <tt>StringTokenizer</tt> may result in a
     * <tt>NullPointerException</tt>.
     *
     * <p>
     *  为指定的字符串构造一个字符串tokenizer。 <code> delim </code>参数中的字符是用于分隔令牌的分隔符。分隔符字符本身不会被视为令牌。
     * <p>
     *  请注意,如果<tt> delim </tt>是<tt> null </tt>,则此构造函数不会抛出异常。
     * 但是,尝试在生成的<tt> StringTokenizer </tt>上调用其他方法可能会导致<tt> NullPointerException </tt>。
     * 
     * 
     * @param   str     a string to be parsed.
     * @param   delim   the delimiters.
     * @exception NullPointerException if str is <CODE>null</CODE>
     */
    public StringTokenizer(String str, String delim) {
        this(str, delim, false);
    }

    /**
     * Constructs a string tokenizer for the specified string. The
     * tokenizer uses the default delimiter set, which is
     * <code>"&nbsp;&#92;t&#92;n&#92;r&#92;f"</code>: the space character,
     * the tab character, the newline character, the carriage-return character,
     * and the form-feed character. Delimiter characters themselves will
     * not be treated as tokens.
     *
     * <p>
     *  为指定的字符串构造一个字符串tokenizer。分词器使用默认分隔符集,即<code>"&nbsp; \ t \ n \ r \ f"</code>：空格字符,制表符字符,换行符,回车符,换页字符。
     * 分隔符字符本身不会被视为令牌。
     * 
     * 
     * @param   str   a string to be parsed.
     * @exception NullPointerException if str is <CODE>null</CODE>
     */
    public StringTokenizer(String str) {
        this(str, " \t\n\r\f", false);
    }

    /**
     * Skips delimiters starting from the specified position. If retDelims
     * is false, returns the index of the first non-delimiter character at or
     * after startPos. If retDelims is true, startPos is returned.
     * <p>
     *  跳过从指定位置开始的分隔符。如果retDelims为false,则返回startPos处或之后的第一个非分隔符字符的索引。如果retDelims为true,则返回startPos。
     * 
     */
    private int skipDelimiters(int startPos) {
        if (delimiters == null)
            throw new NullPointerException();

        int position = startPos;
        while (!retDelims && position < maxPosition) {
            if (!hasSurrogates) {
                char c = str.charAt(position);
                if ((c > maxDelimCodePoint) || (delimiters.indexOf(c) < 0))
                    break;
                position++;
            } else {
                int c = str.codePointAt(position);
                if ((c > maxDelimCodePoint) || !isDelimiter(c)) {
                    break;
                }
                position += Character.charCount(c);
            }
        }
        return position;
    }

    /**
     * Skips ahead from startPos and returns the index of the next delimiter
     * character encountered, or maxPosition if no such delimiter is found.
     * <p>
     * 从startPos向前跳过并返回遇到的下一个定界符的索引,如果未找到此定界符,则返回maxPosition。
     * 
     */
    private int scanToken(int startPos) {
        int position = startPos;
        while (position < maxPosition) {
            if (!hasSurrogates) {
                char c = str.charAt(position);
                if ((c <= maxDelimCodePoint) && (delimiters.indexOf(c) >= 0))
                    break;
                position++;
            } else {
                int c = str.codePointAt(position);
                if ((c <= maxDelimCodePoint) && isDelimiter(c))
                    break;
                position += Character.charCount(c);
            }
        }
        if (retDelims && (startPos == position)) {
            if (!hasSurrogates) {
                char c = str.charAt(position);
                if ((c <= maxDelimCodePoint) && (delimiters.indexOf(c) >= 0))
                    position++;
            } else {
                int c = str.codePointAt(position);
                if ((c <= maxDelimCodePoint) && isDelimiter(c))
                    position += Character.charCount(c);
            }
        }
        return position;
    }

    private boolean isDelimiter(int codePoint) {
        for (int i = 0; i < delimiterCodePoints.length; i++) {
            if (delimiterCodePoints[i] == codePoint) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests if there are more tokens available from this tokenizer's string.
     * If this method returns <tt>true</tt>, then a subsequent call to
     * <tt>nextToken</tt> with no argument will successfully return a token.
     *
     * <p>
     *  测试此分词器字符串是否有更多令牌可用。如果此方法返回<tt> true </tt>,那么对没有参数的<tt> nextToken </tt>的后续调用将成功返回一个令牌。
     * 
     * 
     * @return  <code>true</code> if and only if there is at least one token
     *          in the string after the current position; <code>false</code>
     *          otherwise.
     */
    public boolean hasMoreTokens() {
        /*
         * Temporarily store this position and use it in the following
         * nextToken() method only if the delimiters haven't been changed in
         * that nextToken() invocation.
         * <p>
         *  临时存储此位置,并仅在未在nextToken()调用中更改分隔符的情况下,才在以下nextToken()方法中使用它。
         * 
         */
        newPosition = skipDelimiters(currentPosition);
        return (newPosition < maxPosition);
    }

    /**
     * Returns the next token from this string tokenizer.
     *
     * <p>
     *  返回此字符串tokenizer中的下一个标记。
     * 
     * 
     * @return     the next token from this string tokenizer.
     * @exception  NoSuchElementException  if there are no more tokens in this
     *               tokenizer's string.
     */
    public String nextToken() {
        /*
         * If next position already computed in hasMoreElements() and
         * delimiters have changed between the computation and this invocation,
         * then use the computed value.
         * <p>
         *  如果已经在hasMoreElements()和分隔符中计算的下一个位置在计算和此调用之间更改,则使用计算的值。
         * 
         */

        currentPosition = (newPosition >= 0 && !delimsChanged) ?
            newPosition : skipDelimiters(currentPosition);

        /* Reset these anyway */
        delimsChanged = false;
        newPosition = -1;

        if (currentPosition >= maxPosition)
            throw new NoSuchElementException();
        int start = currentPosition;
        currentPosition = scanToken(currentPosition);
        return str.substring(start, currentPosition);
    }

    /**
     * Returns the next token in this string tokenizer's string. First,
     * the set of characters considered to be delimiters by this
     * <tt>StringTokenizer</tt> object is changed to be the characters in
     * the string <tt>delim</tt>. Then the next token in the string
     * after the current position is returned. The current position is
     * advanced beyond the recognized token.  The new delimiter set
     * remains the default after this call.
     *
     * <p>
     *  返回此字符串中的下一个标记tokenizer的字符串。首先,将此<tt> StringTokenizer </tt>对象视为定界符的字符集更改为字符串<tt> delim </tt>中的字符。
     * 然后返回当前位置之后的字符串中的下一个标记。当前位置超出了识别的令牌。新的分隔符集在此调用后仍保留默认值。
     * 
     * 
     * @param      delim   the new delimiters.
     * @return     the next token, after switching to the new delimiter set.
     * @exception  NoSuchElementException  if there are no more tokens in this
     *               tokenizer's string.
     * @exception NullPointerException if delim is <CODE>null</CODE>
     */
    public String nextToken(String delim) {
        delimiters = delim;

        /* delimiter string specified, so set the appropriate flag. */
        delimsChanged = true;

        setMaxDelimCodePoint();
        return nextToken();
    }

    /**
     * Returns the same value as the <code>hasMoreTokens</code>
     * method. It exists so that this class can implement the
     * <code>Enumeration</code> interface.
     *
     * <p>
     *  返回与<code> hasMoreTokens </code>方法相同的值。它存在,使这个类可以实现<code>枚举</code>接口。
     * 
     * 
     * @return  <code>true</code> if there are more tokens;
     *          <code>false</code> otherwise.
     * @see     java.util.Enumeration
     * @see     java.util.StringTokenizer#hasMoreTokens()
     */
    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    /**
     * Returns the same value as the <code>nextToken</code> method,
     * except that its declared return value is <code>Object</code> rather than
     * <code>String</code>. It exists so that this class can implement the
     * <code>Enumeration</code> interface.
     *
     * <p>
     * 返回与<code> nextToken </code>方法相同的值,除了其声明的返回值为<code> Object </code>,而不是<code> String </code>。
     * 它存在,使这个类可以实现<code>枚举</code>接口。
     * 
     * 
     * @return     the next token in the string.
     * @exception  NoSuchElementException  if there are no more tokens in this
     *               tokenizer's string.
     * @see        java.util.Enumeration
     * @see        java.util.StringTokenizer#nextToken()
     */
    public Object nextElement() {
        return nextToken();
    }

    /**
     * Calculates the number of times that this tokenizer's
     * <code>nextToken</code> method can be called before it generates an
     * exception. The current position is not advanced.
     *
     * <p>
     * 
     * @return  the number of tokens remaining in the string using the current
     *          delimiter set.
     * @see     java.util.StringTokenizer#nextToken()
     */
    public int countTokens() {
        int count = 0;
        int currpos = currentPosition;
        while (currpos < maxPosition) {
            currpos = skipDelimiters(currpos);
            if (currpos >= maxPosition)
                break;
            currpos = scanToken(currpos);
            count++;
        }
        return count;
    }
}
