/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.ByteArrayOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException ;
import java.util.BitSet;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.security.action.GetBooleanAction;
import sun.security.action.GetPropertyAction;

/**
 * Utility class for HTML form encoding. This class contains static methods
 * for converting a String to the <CODE>application/x-www-form-urlencoded</CODE> MIME
 * format. For more information about HTML form encoding, consult the HTML
 * <A HREF="http://www.w3.org/TR/html4/">specification</A>.
 *
 * <p>
 * When encoding a String, the following rules apply:
 *
 * <ul>
 * <li>The alphanumeric characters &quot;{@code a}&quot; through
 *     &quot;{@code z}&quot;, &quot;{@code A}&quot; through
 *     &quot;{@code Z}&quot; and &quot;{@code 0}&quot;
 *     through &quot;{@code 9}&quot; remain the same.
 * <li>The special characters &quot;{@code .}&quot;,
 *     &quot;{@code -}&quot;, &quot;{@code *}&quot;, and
 *     &quot;{@code _}&quot; remain the same.
 * <li>The space character &quot; &nbsp; &quot; is
 *     converted into a plus sign &quot;{@code +}&quot;.
 * <li>All other characters are unsafe and are first converted into
 *     one or more bytes using some encoding scheme. Then each byte is
 *     represented by the 3-character string
 *     &quot;<i>{@code %xy}</i>&quot;, where <i>xy</i> is the
 *     two-digit hexadecimal representation of the byte.
 *     The recommended encoding scheme to use is UTF-8. However,
 *     for compatibility reasons, if an encoding is not specified,
 *     then the default encoding of the platform is used.
 * </ul>
 *
 * <p>
 * For example using UTF-8 as the encoding scheme the string &quot;The
 * string &#252;@foo-bar&quot; would get converted to
 * &quot;The+string+%C3%BC%40foo-bar&quot; because in UTF-8 the character
 * &#252; is encoded as two bytes C3 (hex) and BC (hex), and the
 * character @ is encoded as one byte 40 (hex).
 *
 * <p>
 *  HTML表单编码的实用程序类。此类包含用于将字符串转换为<CODE>应用程序/ x-www形式 -  urlencoded </CODE> MIME格式的静态方法。
 * 有关HTML表单编码的详细信息,请参阅HTML <A HREF="http://www.w3.org/TR/html4/">规范</A>。
 * 
 * <p>
 *  当编码字符串时,以下规则适用：
 * 
 * <ul>
 *  <li>字母数字字符"{@ code a}"通过"{@ code z}","{@ code A}"通过"{@ code Z}"和"{@ code 0}"通过"{@ code 9}"保持不变。
 *  <li>特殊字符"{@ code。}","{@ code  - }","{@ code *}"和"{@ code _}"保持不变。
 *  <li>空格字符" &nbsp; ",被转换成加号"{@ code +}"。 <li>所有其他字符都是不安全的,并且会首先使用某种编码方案转换为一个或多个字节。
 * 然后,每个字节由3个字符的字符串"<i> {@ code％xy} </i>"表示,其中<xy> </i>是字节的两位十六进制表示。建议使用的编码方案是UTF-8。
 * 但是,出于兼容性原因,如果未指定编码,则使用平台的默认编码。
 * </ul>
 * 
 * <p>
 * 例如,使用UTF-8作为编码方案,字符串"The stringü@ foo-bar"将被转换为"+字符串+％C3％BC％40foo-bar"因为在UTF-8中,字符ü被编码为两个字节C3(十六进制)和B
 * C(十六进制),字符@被编码为一个字节40(十六进制)。
 * 
 * 
 * @author  Herb Jellinek
 * @since   JDK1.0
 */
public class URLEncoder {
    static BitSet dontNeedEncoding;
    static final int caseDiff = ('a' - 'A');
    static String dfltEncName = null;

    static {

        /* The list of characters that are not encoded has been
         * determined as follows:
         *
         * RFC 2396 states:
         * -----
         * Data characters that are allowed in a URI but do not have a
         * reserved purpose are called unreserved.  These include upper
         * and lower case letters, decimal digits, and a limited set of
         * punctuation marks and symbols.
         *
         * unreserved  = alphanum | mark
         *
         * mark        = "-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
         *
         * Unreserved characters can be escaped without changing the
         * semantics of the URI, but this should not be done unless the
         * URI is being used in a context that does not allow the
         * unescaped character to appear.
         * -----
         *
         * It appears that both Netscape and Internet Explorer escape
         * all special characters from this list with the exception
         * of "-", "_", ".", "*". While it is not clear why they are
         * escaping the other characters, perhaps it is safest to
         * assume that there might be contexts in which the others
         * are unsafe if not escaped. Therefore, we will use the same
         * list. It is also noteworthy that this is consistent with
         * O'Reilly's "HTML: The Definitive Guide" (page 164).
         *
         * As a last note, Intenet Explorer does not encode the "@"
         * character which is clearly not unreserved according to the
         * RFC. We are being consistent with the RFC in this matter,
         * as is Netscape.
         *
         * <p>
         *  确定如下：
         * 
         *  RFC 2396规定：-----在URI中允许但不具有保留目的的数据字符称为未保留。这些包括大写和小写字母,十进制数字和一组有限的标点符号和符号。
         * 
         *  unreserved = alphanum |标记
         * 
         *  mark =" - "| "_"| "。" | "！" | "〜"| "*"| "'"| "("|")"
         * 
         *  可以转义未保留字符而不更改URI的语义,但除非在不允许出现未转义字符的上下文中使用URI,否则不应该这样做。 -----
         * 
         *  看起来Netscape和Internet Explorer都会从该列表中除去" - ","_","。","*"之外的所有特殊字符。
         * 虽然不清楚为什么他们逃避其他字符,也许最安全的是假设可能存在其他人是不安全的,如果没有逃脱的上下文。因此,我们将使用相同的列表。
         * 还值得注意的是,这与O'Reilly的"HTML：The Definitive Guide"(第164页)一致。
         * 
         * 最后一点,Intenet Explorer不会对"@"字符进行编码,根据RFC,这个字符显然不是未预留的。我们在这个问题上与RFC一致,Netscape也是如此。
         * 
         */

        dontNeedEncoding = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding.set(i);
        }
        dontNeedEncoding.set(' '); /* encoding a space to a + is done
        dontNeedEncoding.set(' '); /* <p>
        dontNeedEncoding.set(' '); /* 
                                    * in the encode() method */
        dontNeedEncoding.set('-');
        dontNeedEncoding.set('_');
        dontNeedEncoding.set('.');
        dontNeedEncoding.set('*');

        dfltEncName = AccessController.doPrivileged(
            new GetPropertyAction("file.encoding")
        );
    }

    /**
     * You can't call the constructor.
     * <p>
     *  您不能调用构造函数。
     * 
     */
    private URLEncoder() { }

    /**
     * Translates a string into {@code x-www-form-urlencoded}
     * format. This method uses the platform's default encoding
     * as the encoding scheme to obtain the bytes for unsafe characters.
     *
     * <p>
     *  将字符串转换为{@code x-www-form-urlencoded}格式。此方法使用平台的默认编码作为编码方案来获取不安全字符的字节。
     * 
     * 
     * @param   s   {@code String} to be translated.
     * @deprecated The resulting string may vary depending on the platform's
     *             default encoding. Instead, use the encode(String,String)
     *             method to specify the encoding.
     * @return  the translated {@code String}.
     */
    @Deprecated
    public static String encode(String s) {

        String str = null;

        try {
            str = encode(s, dfltEncName);
        } catch (UnsupportedEncodingException e) {
            // The system should always have the platform default
        }

        return str;
    }

    /**
     * Translates a string into {@code application/x-www-form-urlencoded}
     * format using a specific encoding scheme. This method uses the
     * supplied encoding scheme to obtain the bytes for unsafe
     * characters.
     * <p>
     * <em><strong>Note:</strong> The <a href=
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     * World Wide Web Consortium Recommendation</a> states that
     * UTF-8 should be used. Not doing so may introduce
     * incompatibilities.</em>
     *
     * <p>
     *  使用特定的编码方案将字符串转换为{@code application / x-www-form-urlencoded}格式。此方法使用提供的编码方案来获取不安全字符的字节。
     * <p>
     *  <em> <strong>注意：</strong> <a href =
     * "http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">
     *  万维网联盟建议</a>指出应使用UTF-8。否则可能会导致不兼容。</em>
     * 
     * 
     * @param   s   {@code String} to be translated.
     * @param   enc   The name of a supported
     *    <a href="../lang/package-summary.html#charenc">character
     *    encoding</a>.
     * @return  the translated {@code String}.
     * @exception  UnsupportedEncodingException
     *             If the named encoding is not supported
     * @see URLDecoder#decode(java.lang.String, java.lang.String)
     * @since 1.4
     */
    public static String encode(String s, String enc)
        throws UnsupportedEncodingException {

        boolean needToChange = false;
        StringBuffer out = new StringBuffer(s.length());
        Charset charset;
        CharArrayWriter charArrayWriter = new CharArrayWriter();

        if (enc == null)
            throw new NullPointerException("charsetName");

        try {
            charset = Charset.forName(enc);
        } catch (IllegalCharsetNameException e) {
            throw new UnsupportedEncodingException(enc);
        } catch (UnsupportedCharsetException e) {
            throw new UnsupportedEncodingException(enc);
        }

        for (int i = 0; i < s.length();) {
            int c = (int) s.charAt(i);
            //System.out.println("Examining character: " + c);
            if (dontNeedEncoding.get(c)) {
                if (c == ' ') {
                    c = '+';
                    needToChange = true;
                }
                //System.out.println("Storing: " + c);
                out.append((char)c);
                i++;
            } else {
                // convert to external encoding before hex conversion
                do {
                    charArrayWriter.write(c);
                    /*
                     * If this character represents the start of a Unicode
                     * surrogate pair, then pass in two characters. It's not
                     * clear what should be done if a bytes reserved in the
                     * surrogate pairs range occurs outside of a legal
                     * surrogate pair. For now, just treat it as if it were
                     * any other character.
                     * <p>
                     *  如果此字符表示Unicode代理对的开始,则传入两个字符。不清楚如果在代理对范围中保留的字节在合法代理对之外发生应该做什么。现在,只是把它看作是任何其他字符。
                     * 
                     */
                    if (c >= 0xD800 && c <= 0xDBFF) {
                        /*
                          System.out.println(Integer.toHexString(c)
                          + " is high surrogate");
                        /* <p>
                        /*  System.out.println(Integer.toHexString(c)+"is high surrogate");
                        /* 
                        */
                        if ( (i+1) < s.length()) {
                            int d = (int) s.charAt(i+1);
                            /*
                              System.out.println("\tExamining "
                              + Integer.toHexString(d));
                            /* <p>
                            /*  System.out.println("\ tExamining"+ Integer.toHexString(d));
                            /* 
                            */
                            if (d >= 0xDC00 && d <= 0xDFFF) {
                                /*
                                  System.out.println("\t"
                                  + Integer.toHexString(d)
                                  + " is low surrogate");
                                /* <p>
                                /*  System.out.println("\ t"+ Integer.toHexString(d)+"is low surrogate");
                                */
                                charArrayWriter.write(d);
                                i++;
                            }
                        }
                    }
                    i++;
                } while (i < s.length() && !dontNeedEncoding.get((c = (int) s.charAt(i))));

                charArrayWriter.flush();
                String str = new String(charArrayWriter.toCharArray());
                byte[] ba = str.getBytes(charset);
                for (int j = 0; j < ba.length; j++) {
                    out.append('%');
                    char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                    ch = Character.forDigit(ba[j] & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                }
                charArrayWriter.reset();
                needToChange = true;
            }
        }

        return (needToChange? out.toString() : s);
    }
}
