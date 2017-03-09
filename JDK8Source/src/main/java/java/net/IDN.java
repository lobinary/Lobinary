/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.InputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

import sun.net.idn.StringPrep;
import sun.net.idn.Punycode;
import sun.text.normalizer.UCharacterIterator;

/**
 * Provides methods to convert internationalized domain names (IDNs) between
 * a normal Unicode representation and an ASCII Compatible Encoding (ACE) representation.
 * Internationalized domain names can use characters from the entire range of
 * Unicode, while traditional domain names are restricted to ASCII characters.
 * ACE is an encoding of Unicode strings that uses only ASCII characters and
 * can be used with software (such as the Domain Name System) that only
 * understands traditional domain names.
 *
 * <p>Internationalized domain names are defined in <a href="http://www.ietf.org/rfc/rfc3490.txt">RFC 3490</a>.
 * RFC 3490 defines two operations: ToASCII and ToUnicode. These 2 operations employ
 * <a href="http://www.ietf.org/rfc/rfc3491.txt">Nameprep</a> algorithm, which is a
 * profile of <a href="http://www.ietf.org/rfc/rfc3454.txt">Stringprep</a>, and
 * <a href="http://www.ietf.org/rfc/rfc3492.txt">Punycode</a> algorithm to convert
 * domain name string back and forth.
 *
 * <p>The behavior of aforementioned conversion process can be adjusted by various flags:
 *   <ul>
 *     <li>If the ALLOW_UNASSIGNED flag is used, the domain name string to be converted
 *         can contain code points that are unassigned in Unicode 3.2, which is the
 *         Unicode version on which IDN conversion is based. If the flag is not used,
 *         the presence of such unassigned code points is treated as an error.
 *     <li>If the USE_STD3_ASCII_RULES flag is used, ASCII strings are checked against <a href="http://www.ietf.org/rfc/rfc1122.txt">RFC 1122</a> and <a href="http://www.ietf.org/rfc/rfc1123.txt">RFC 1123</a>.
 *         It is an error if they don't meet the requirements.
 *   </ul>
 * These flags can be logically OR'ed together.
 *
 * <p>The security consideration is important with respect to internationalization
 * domain name support. For example, English domain names may be <i>homographed</i>
 * - maliciously misspelled by substitution of non-Latin letters.
 * <a href="http://www.unicode.org/reports/tr36/">Unicode Technical Report #36</a>
 * discusses security issues of IDN support as well as possible solutions.
 * Applications are responsible for taking adequate security measures when using
 * international domain names.
 *
 * <p>
 *  提供在正常Unicode表示和ASCII兼容编码(ACE)表示之间转换国际化域名(IDN)的方法。国际化域名可以使用整个Unicode范围内的字符,而传统的域名仅限于ASCII字符。
 *  ACE是仅使用ASCII字符的Unicode字符串的编码,可以与仅理解传统域名的软件(例如域名系统)一起使用。
 * 
 *  <p>国际化域名在<a href="http://www.ietf.org/rfc/rfc3490.txt"> RFC 3490 </a>中定义。
 *  RFC 3490定义了两个操作：ToASCII和ToUnicode。
 * 这两个操作使用<a href="http://www.ietf.org/rfc/rfc3491.txt"> Nameprep </a>算法,这是一个<a href ="http：//www.ietf的个人资料.org / rfc / rfc3454.txt">
 *  Stringprep </a>和<a href="http://www.ietf.org/rfc/rfc3492.txt"> Punycode </a>算法转换域名字符串来来回回。
 *  RFC 3490定义了两个操作：ToASCII和ToUnicode。
 * 
 *  <p>上述转换过程的行为可以通过各种标志来调整：
 * <ul>
 * <li>如果使用ALLOW_UNASSIGNED标志,则要转换的域名字符串可以包含在Unicode 3.2中未分配的代码点,这是基于IDN转换的Unicode版本。
 * 如果不使用该标志,则这样的未分配代码点的存在被视为错误。
 *  <li>如果使用USE_STD3_ASCII_RULES标记,则会针对<a href="http://www.ietf.org/rfc/rfc1122.txt"> RFC 1122 </a>和<a href ="http ：//www.ietf.org/rfc/rfc1123.txt">
 *  RFC 1123 </a>。
 * 如果不使用该标志,则这样的未分配代码点的存在被视为错误。如果它们不满足要求,则是错误。
 * </ul>
 *  这些标志可以逻辑或运算在一起。
 * 
 *  <p>安全性考虑对于国际化域名支持很重要。例如,英语域名可能被同性恋 - 通过替换非拉丁字母而恶意拼写。
 *  <a href="http://www.unicode.org/reports/tr36/"> Unicode技术报告#36 </a>讨论了IDN支持的安全问题以及可能的解决方案。
 * 应用程序负责在使用国际域名时采取适当的安全措施。
 * 
 * 
 * @author Edward Wang
 * @since 1.6
 *
 */
public final class IDN {
    /**
     * Flag to allow processing of unassigned code points
     * <p>
     *  允许处理未分配代码点的标志
     * 
     */
    public static final int ALLOW_UNASSIGNED = 0x01;

    /**
     * Flag to turn on the check against STD-3 ASCII rules
     * <p>
     *  标志打开对STD-3 ASCII规则的检查
     * 
     */
    public static final int USE_STD3_ASCII_RULES = 0x02;


    /**
     * Translates a string from Unicode to ASCII Compatible Encoding (ACE),
     * as defined by the ToASCII operation of <a href="http://www.ietf.org/rfc/rfc3490.txt">RFC 3490</a>.
     *
     * <p>ToASCII operation can fail. ToASCII fails if any step of it fails.
     * If ToASCII operation fails, an IllegalArgumentException will be thrown.
     * In this case, the input string should not be used in an internationalized domain name.
     *
     * <p> A label is an individual part of a domain name. The original ToASCII operation,
     * as defined in RFC 3490, only operates on a single label. This method can handle
     * both label and entire domain name, by assuming that labels in a domain name are
     * always separated by dots. The following characters are recognized as dots:
     * &#0092;u002E (full stop), &#0092;u3002 (ideographic full stop), &#0092;uFF0E (fullwidth full stop),
     * and &#0092;uFF61 (halfwidth ideographic full stop). if dots are
     * used as label separators, this method also changes all of them to &#0092;u002E (full stop)
     * in output translated string.
     *
     * <p>
     *  将字符串从Unicode转换为ASCII兼容编码(ACE),由TOASCII操作<a href="http://www.ietf.org/rfc/rfc3490.txt"> RFC 3490 </a>
     * 定义。
     * 
     * <p> ToASCII操作可能会失败。 ToASCII失败,如果任何步骤失败。如果ToASCII操作失败,将抛出IllegalArgumentException。
     * 在这种情况下,输入字符串不应在国际化域名中使用。
     * 
     *  <p>标签是域名的一个独立部分。原始的ToASCII操作(如RFC 3490中定义)仅操作在单个标签上。此方法可以处理标签域和整个域名,通过假设域名中的标签始终由点分隔。
     * 以下字符被识别为点：\ u002E(完全停止),\ u3002(表意全停),\ uFF0E(全宽全停)和\ uFF61(半宽表意全停)。
     * 如果将点用作标签分隔符,则此方法还会将所有字符更改为输出转换字符串中的\ u002E(全停止)。
     * 
     * 
     * @param input     the string to be processed
     * @param flag      process flag; can be 0 or any logical OR of possible flags
     *
     * @return          the translated {@code String}
     *
     * @throws IllegalArgumentException   if the input string doesn't conform to RFC 3490 specification
     */
    public static String toASCII(String input, int flag)
    {
        int p = 0, q = 0;
        StringBuffer out = new StringBuffer();

        if (isRootLabel(input)) {
            return ".";
        }

        while (p < input.length()) {
            q = searchDots(input, p);
            out.append(toASCIIInternal(input.substring(p, q),  flag));
            if (q != (input.length())) {
               // has more labels, or keep the trailing dot as at present
               out.append('.');
            }
            p = q + 1;
        }

        return out.toString();
    }


    /**
     * Translates a string from Unicode to ASCII Compatible Encoding (ACE),
     * as defined by the ToASCII operation of <a href="http://www.ietf.org/rfc/rfc3490.txt">RFC 3490</a>.
     *
     * <p> This convenience method works as if by invoking the
     * two-argument counterpart as follows:
     * <blockquote>
     * {@link #toASCII(String, int) toASCII}(input,&nbsp;0);
     * </blockquote>
     *
     * <p>
     *  将字符串从Unicode转换为ASCII兼容编码(ACE),由TOASCII操作<a href="http://www.ietf.org/rfc/rfc3490.txt"> RFC 3490 </a>
     * 定义。
     * 
     *  <p>这种方便的方法好像通过调用双参数对应方法如下：
     * <blockquote>
     *  {@link #toASCII(String,int)toASCII}(输入,&nbsp; 0);
     * </blockquote>
     * 
     * 
     * @param input     the string to be processed
     *
     * @return          the translated {@code String}
     *
     * @throws IllegalArgumentException   if the input string doesn't conform to RFC 3490 specification
     */
    public static String toASCII(String input) {
        return toASCII(input, 0);
    }


    /**
     * Translates a string from ASCII Compatible Encoding (ACE) to Unicode,
     * as defined by the ToUnicode operation of <a href="http://www.ietf.org/rfc/rfc3490.txt">RFC 3490</a>.
     *
     * <p>ToUnicode never fails. In case of any error, the input string is returned unmodified.
     *
     * <p> A label is an individual part of a domain name. The original ToUnicode operation,
     * as defined in RFC 3490, only operates on a single label. This method can handle
     * both label and entire domain name, by assuming that labels in a domain name are
     * always separated by dots. The following characters are recognized as dots:
     * &#0092;u002E (full stop), &#0092;u3002 (ideographic full stop), &#0092;uFF0E (fullwidth full stop),
     * and &#0092;uFF61 (halfwidth ideographic full stop).
     *
     * <p>
     *  将字符串从ASCII兼容编码(ACE)转换为Unicode,由<a href="http://www.ietf.org/rfc/rfc3490.txt"> RFC 3490 </a>的ToUnicod
     * e操作定义。
     * 
     *  <p> ToUnicode永远不会失败。如果发生任何错误,输入字符串将不被修改。
     * 
     * <p>标签是域名的一个独立部分。原始的ToUnicode操作(如RFC 3490中定义)仅对单个标签操作。此方法可以处理标签域和整个域名,通过假设域名中的标签始终由点分隔。
     * 以下字符被识别为点：\ u002E(完全停止),\ u3002(表意全停),\ uFF0E(全宽全停)和\ uFF61(半宽表意全停)。
     * 
     * 
     * @param input     the string to be processed
     * @param flag      process flag; can be 0 or any logical OR of possible flags
     *
     * @return          the translated {@code String}
     */
    public static String toUnicode(String input, int flag) {
        int p = 0, q = 0;
        StringBuffer out = new StringBuffer();

        if (isRootLabel(input)) {
            return ".";
        }

        while (p < input.length()) {
            q = searchDots(input, p);
            out.append(toUnicodeInternal(input.substring(p, q),  flag));
            if (q != (input.length())) {
               // has more labels, or keep the trailing dot as at present
               out.append('.');
            }
            p = q + 1;
        }

        return out.toString();
    }


    /**
     * Translates a string from ASCII Compatible Encoding (ACE) to Unicode,
     * as defined by the ToUnicode operation of <a href="http://www.ietf.org/rfc/rfc3490.txt">RFC 3490</a>.
     *
     * <p> This convenience method works as if by invoking the
     * two-argument counterpart as follows:
     * <blockquote>
     * {@link #toUnicode(String, int) toUnicode}(input,&nbsp;0);
     * </blockquote>
     *
     * <p>
     *  将字符串从ASCII兼容编码(ACE)转换为Unicode,由<a href="http://www.ietf.org/rfc/rfc3490.txt"> RFC 3490 </a>的ToUnicod
     * e操作定义。
     * 
     *  <p>这种方便的方法好像通过调用双参数对应方法如下：
     * 
     * @param input     the string to be processed
     *
     * @return          the translated {@code String}
     */
    public static String toUnicode(String input) {
        return toUnicode(input, 0);
    }


    /* ---------------- Private members -------------- */

    // ACE Prefix is "xn--"
    private static final String ACE_PREFIX = "xn--";
    private static final int ACE_PREFIX_LENGTH = ACE_PREFIX.length();

    private static final int MAX_LABEL_LENGTH   = 63;

    // single instance of nameprep
    private static StringPrep namePrep = null;

    static {
        InputStream stream = null;

        try {
            final String IDN_PROFILE = "uidna.spp";
            if (System.getSecurityManager() != null) {
                stream = AccessController.doPrivileged(new PrivilegedAction<InputStream>() {
                    public InputStream run() {
                        return StringPrep.class.getResourceAsStream(IDN_PROFILE);
                    }
                });
            } else {
                stream = StringPrep.class.getResourceAsStream(IDN_PROFILE);
            }

            namePrep = new StringPrep(stream);
            stream.close();
        } catch (IOException e) {
            // should never reach here
            assert false;
        }
    }


    /* ---------------- Private operations -------------- */


    //
    // to suppress the default zero-argument constructor
    //
    private IDN() {}

    //
    // toASCII operation; should only apply to a single label
    //
    private static String toASCIIInternal(String label, int flag)
    {
        // step 1
        // Check if the string contains code points outside the ASCII range 0..0x7c.
        boolean isASCII  = isAllASCII(label);
        StringBuffer dest;

        // step 2
        // perform the nameprep operation; flag ALLOW_UNASSIGNED is used here
        if (!isASCII) {
            UCharacterIterator iter = UCharacterIterator.getInstance(label);
            try {
                dest = namePrep.prepare(iter, flag);
            } catch (java.text.ParseException e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            dest = new StringBuffer(label);
        }

        // step 8, move forward to check the smallest number of the code points
        // the length must be inside 1..63
        if (dest.length() == 0) {
            throw new IllegalArgumentException(
                        "Empty label is not a legal name");
        }

        // step 3
        // Verify the absence of non-LDH ASCII code points
        //   0..0x2c, 0x2e..0x2f, 0x3a..0x40, 0x5b..0x60, 0x7b..0x7f
        // Verify the absence of leading and trailing hyphen
        boolean useSTD3ASCIIRules = ((flag & USE_STD3_ASCII_RULES) != 0);
        if (useSTD3ASCIIRules) {
            for (int i = 0; i < dest.length(); i++) {
                int c = dest.charAt(i);
                if (isNonLDHAsciiCodePoint(c)) {
                    throw new IllegalArgumentException(
                        "Contains non-LDH ASCII characters");
                }
            }

            if (dest.charAt(0) == '-' ||
                dest.charAt(dest.length() - 1) == '-') {

                throw new IllegalArgumentException(
                        "Has leading or trailing hyphen");
            }
        }

        if (!isASCII) {
            // step 4
            // If all code points are inside 0..0x7f, skip to step 8
            if (!isAllASCII(dest.toString())) {
                // step 5
                // verify the sequence does not begin with ACE prefix
                if(!startsWithACEPrefix(dest)){

                    // step 6
                    // encode the sequence with punycode
                    try {
                        dest = Punycode.encode(dest, null);
                    } catch (java.text.ParseException e) {
                        throw new IllegalArgumentException(e);
                    }

                    dest = toASCIILower(dest);

                    // step 7
                    // prepend the ACE prefix
                    dest.insert(0, ACE_PREFIX);
                } else {
                    throw new IllegalArgumentException("The input starts with the ACE Prefix");
                }

            }
        }

        // step 8
        // the length must be inside 1..63
        if (dest.length() > MAX_LABEL_LENGTH) {
            throw new IllegalArgumentException("The label in the input is too long");
        }

        return dest.toString();
    }

    //
    // toUnicode operation; should only apply to a single label
    //
    private static String toUnicodeInternal(String label, int flag) {
        boolean[] caseFlags = null;
        StringBuffer dest;

        // step 1
        // find out if all the codepoints in input are ASCII
        boolean isASCII = isAllASCII(label);

        if(!isASCII){
            // step 2
            // perform the nameprep operation; flag ALLOW_UNASSIGNED is used here
            try {
                UCharacterIterator iter = UCharacterIterator.getInstance(label);
                dest = namePrep.prepare(iter, flag);
            } catch (Exception e) {
                // toUnicode never fails; if any step fails, return the input string
                return label;
            }
        } else {
            dest = new StringBuffer(label);
        }

        // step 3
        // verify ACE Prefix
        if(startsWithACEPrefix(dest)) {

            // step 4
            // Remove the ACE Prefix
            String temp = dest.substring(ACE_PREFIX_LENGTH, dest.length());

            try {
                // step 5
                // Decode using punycode
                StringBuffer decodeOut = Punycode.decode(new StringBuffer(temp), null);

                // step 6
                // Apply toASCII
                String toASCIIOut = toASCII(decodeOut.toString(), flag);

                // step 7
                // verify
                if (toASCIIOut.equalsIgnoreCase(dest.toString())) {
                    // step 8
                    // return output of step 5
                    return decodeOut.toString();
                }
            } catch (Exception ignored) {
                // no-op
            }
        }

        // just return the input
        return label;
    }


    //
    // LDH stands for "letter/digit/hyphen", with characters restricted to the
    // 26-letter Latin alphabet <A-Z a-z>, the digits <0-9>, and the hyphen
    // <->.
    // Non LDH refers to characters in the ASCII range, but which are not
    // letters, digits or the hypen.
    //
    // non-LDH = 0..0x2C, 0x2E..0x2F, 0x3A..0x40, 0x5B..0x60, 0x7B..0x7F
    //
    private static boolean isNonLDHAsciiCodePoint(int ch){
        return (0x0000 <= ch && ch <= 0x002C) ||
               (0x002E <= ch && ch <= 0x002F) ||
               (0x003A <= ch && ch <= 0x0040) ||
               (0x005B <= ch && ch <= 0x0060) ||
               (0x007B <= ch && ch <= 0x007F);
    }

    //
    // search dots in a string and return the index of that character;
    // or if there is no dots, return the length of input string
    // dots might be: \u002E (full stop), \u3002 (ideographic full stop), \uFF0E (fullwidth full stop),
    // and \uFF61 (halfwidth ideographic full stop).
    //
    private static int searchDots(String s, int start) {
        int i;
        for (i = start; i < s.length(); i++) {
            if (isLabelSeparator(s.charAt(i))) {
                break;
            }
        }

        return i;
    }

    //
    // to check if a string is a root label, ".".
    //
    private static boolean isRootLabel(String s) {
        return (s.length() == 1 && isLabelSeparator(s.charAt(0)));
    }

    //
    // to check if a character is a label separator, i.e. a dot character.
    //
    private static boolean isLabelSeparator(char c) {
        return (c == '.' || c == '\u3002' || c == '\uFF0E' || c == '\uFF61');
    }

    //
    // to check if a string only contains US-ASCII code point
    //
    private static boolean isAllASCII(String input) {
        boolean isASCII = true;
        for (int i = 0; i < input.length(); i++) {
            int c = input.charAt(i);
            if (c > 0x7F) {
                isASCII = false;
                break;
            }
        }
        return isASCII;
    }

    //
    // to check if a string starts with ACE-prefix
    //
    private static boolean startsWithACEPrefix(StringBuffer input){
        boolean startsWithPrefix = true;

        if(input.length() < ACE_PREFIX_LENGTH){
            return false;
        }
        for(int i = 0; i < ACE_PREFIX_LENGTH; i++){
            if(toASCIILower(input.charAt(i)) != ACE_PREFIX.charAt(i)){
                startsWithPrefix = false;
            }
        }
        return startsWithPrefix;
    }

    private static char toASCIILower(char ch){
        if('A' <= ch && ch <= 'Z'){
            return (char)(ch + 'a' - 'A');
        }
        return ch;
    }

    private static StringBuffer toASCIILower(StringBuffer input){
        StringBuffer dest = new StringBuffer();
        for(int i = 0; i < input.length();i++){
            dest.append(toASCIILower(input.charAt(i)));
        }
        return dest;
    }
}
