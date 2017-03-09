/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.*;

/**
 * Utility class for HTML form decoding. This class contains static methods
 * for decoding a String from the <CODE>application/x-www-form-urlencoded</CODE>
 * MIME format.
 * <p>
 * The conversion process is the reverse of that used by the URLEncoder class. It is assumed
 * that all characters in the encoded string are one of the following:
 * &quot;{@code a}&quot; through &quot;{@code z}&quot;,
 * &quot;{@code A}&quot; through &quot;{@code Z}&quot;,
 * &quot;{@code 0}&quot; through &quot;{@code 9}&quot;, and
 * &quot;{@code -}&quot;, &quot;{@code _}&quot;,
 * &quot;{@code .}&quot;, and &quot;{@code *}&quot;. The
 * character &quot;{@code %}&quot; is allowed but is interpreted
 * as the start of a special escaped sequence.
 * <p>
 * The following rules are applied in the conversion:
 *
 * <ul>
 * <li>The alphanumeric characters &quot;{@code a}&quot; through
 *     &quot;{@code z}&quot;, &quot;{@code A}&quot; through
 *     &quot;{@code Z}&quot; and &quot;{@code 0}&quot;
 *     through &quot;{@code 9}&quot; remain the same.
 * <li>The special characters &quot;{@code .}&quot;,
 *     &quot;{@code -}&quot;, &quot;{@code *}&quot;, and
 *     &quot;{@code _}&quot; remain the same.
 * <li>The plus sign &quot;{@code +}&quot; is converted into a
 *     space character &quot; &nbsp; &quot; .
 * <li>A sequence of the form "<i>{@code %xy}</i>" will be
 *     treated as representing a byte where <i>xy</i> is the two-digit
 *     hexadecimal representation of the 8 bits. Then, all substrings
 *     that contain one or more of these byte sequences consecutively
 *     will be replaced by the character(s) whose encoding would result
 *     in those consecutive bytes.
 *     The encoding scheme used to decode these characters may be specified,
 *     or if unspecified, the default encoding of the platform will be used.
 * </ul>
 * <p>
 * There are two possible ways in which this decoder could deal with
 * illegal strings.  It could either leave illegal characters alone or
 * it could throw an {@link java.lang.IllegalArgumentException}.
 * Which approach the decoder takes is left to the
 * implementation.
 *
 * <p>
 *  HTML表单解码的实用程序类。此类包含用于解码来自<CODE>应用程序/ x-www形式 -  urlencoded </CODE> MIME格式的String的静态方法。
 * <p>
 *  转换过程与URLEncoder类使用的过程相反。
 * 假设编码字符串中的所有字符都是以下之一："{@ code a}"通过"{@ code z}","{@ code A}"通过"{@ code Z}","{@ code 0}" {@ code}}和"{@ code}
 * ","{@ code}","{@ code}"和"{@ code}}" "。
 *  转换过程与URLEncoder类使用的过程相反。字符"{@ code％}"是允许的,但被解释为特殊转义序列的开始。
 * <p>
 *  在转换中应用以下规则：
 * 
 * <ul>
 * <li>字母数字字符"{@ code a}"通过"{@ code z}","{@ code A}"通过"{@ code Z}"和"{@ code 0}"通过"{@ code 9}"保持不变。
 *  <li>特殊字符"{@ code。}","{@ code  - }","{@ code *}"和"{@ code _}"保持不变。
 *  <li>加号"{@ code +}"被转换成空格字符" &nbsp; ", 。
 *  <li>"<i> {@ code％xy} </i>"形式的序列将被视为表示一个字节,其中<i> xy </i>是8位的两位十六进制表示形式。
 * 然后,连续包含这些字节序列中的一个或多个的所有子字符串将被其编码将导致这些连续字节的字符替换。可以指定用于解码这些字符的编码方案,或者如果未指定,则将使用平台的默认编码。
 * </ul>
 * <p>
 * 
 * @author  Mark Chamness
 * @author  Michael McCloskey
 * @since   1.2
 */

public class URLDecoder {

    // The platform default encoding
    static String dfltEncName = URLEncoder.dfltEncName;

    /**
     * Decodes a {@code x-www-form-urlencoded} string.
     * The platform's default encoding is used to determine what characters
     * are represented by any consecutive sequences of the form
     * "<i>{@code %xy}</i>".
     * <p>
     *  有两种可能的方法,其中该解码器可以处理非法字符串。它可能会留下非法字符单独或它可以抛出一个{@link java.lang.IllegalArgumentException}。
     * 解码器采用哪种方法留给实现。
     * 
     * 
     * @param s the {@code String} to decode
     * @deprecated The resulting string may vary depending on the platform's
     *          default encoding. Instead, use the decode(String,String) method
     *          to specify the encoding.
     * @return the newly decoded {@code String}
     */
    @Deprecated
    public static String decode(String s) {

        String str = null;

        try {
            str = decode(s, dfltEncName);
        } catch (UnsupportedEncodingException e) {
            // The system should always have the platform default
        }

        return str;
    }

    /**
     * Decodes a {@code application/x-www-form-urlencoded} string using a specific
     * encoding scheme.
     * The supplied encoding is used to determine
     * what characters are represented by any consecutive sequences of the
     * form "<i>{@code %xy}</i>".
     * <p>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce
     * incompatibilities.</em>
     *
     * <p>
     *  解码一个{@code x-www-form-urlencoded}字符串。平台的默认编码用于确定由"<i> {@ code％xy} </i>"形式的任何连续序列表示的字符。
     * 
     * 
     * @param s the {@code String} to decode
     * @param enc   The name of a supported
     *    <a href="../lang/package-summary.html#charenc">character
     *    encoding</a>.
     * @return the newly decoded {@code String}
     * @exception  UnsupportedEncodingException
     *             If character encoding needs to be consulted, but
     *             named character encoding is not supported
     * @see URLEncoder#encode(java.lang.String, java.lang.String)
     * @since 1.4
     */
    public static String decode(String s, String enc)
        throws UnsupportedEncodingException{

        boolean needToChange = false;
        int numChars = s.length();
        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;

        if (enc.length() == 0) {
            throw new UnsupportedEncodingException ("URLDecoder: empty string enc parameter");
        }

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = s.charAt(i);
            switch (c) {
            case '+':
                sb.append(' ');
                i++;
                needToChange = true;
                break;
            case '%':
                /*
                 * Starting with this instance of %, process all
                 * consecutive substrings of the form %xy. Each
                 * substring %xy will yield a byte. Convert all
                 * consecutive  bytes obtained this way to whatever
                 * character(s) they represent in the provided
                 * encoding.
                 * <p>
                 * 使用特定的编码方案解码{@code application / x-www-form-urlencoded}字符串。
                 * 提供的编码用于确定由"<i> {@ code％xy} </i>"形式的任何连续序列表示的字符。
                 * <p>
                 *  <em> <strong>注意：</strong> <a href =
                 * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
                 */

                try {

                    // (numChars-i)/3 is an upper bound for the number
                    // of remaining bytes
                    if (bytes == null)
                        bytes = new byte[(numChars-i)/3];
                    int pos = 0;

                    while ( ((i+2) < numChars) &&
                            (c=='%')) {
                        int v = Integer.parseInt(s.substring(i+1,i+3),16);
                        if (v < 0)
                            throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                        bytes[pos++] = (byte) v;
                        i+= 3;
                        if (i < numChars)
                            c = s.charAt(i);
                    }

                    // A trailing, incomplete byte encoding such as
                    // "%x" will cause an exception to be thrown

                    if ((i < numChars) && (c=='%'))
                        throw new IllegalArgumentException(
                         "URLDecoder: Incomplete trailing escape (%) pattern");

                    sb.append(new String(bytes, 0, pos, enc));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                    "URLDecoder: Illegal hex characters in escape (%) pattern - "
                    + e.getMessage());
                }
                needToChange = true;
                break;
            default:
                sb.append(c);
                i++;
                break;
            }
        }

        return (needToChange? sb.toString() : s);
    }
}
