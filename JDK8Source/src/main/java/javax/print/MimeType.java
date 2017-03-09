/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package javax.print;

import java.io.Serializable;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;

/**
 * Class MimeType encapsulates a Multipurpose Internet Mail Extensions (MIME)
 * media type as defined in <A HREF="http://www.ietf.org/rfc/rfc2045.txt">RFC
 * 2045</A> and <A HREF="http://www.ietf.org/rfc/rfc2046.txt">RFC 2046</A>. A
 * MIME type object is part of a {@link DocFlavor DocFlavor} object and
 * specifies the format of the print data.
 * <P>
 * Class MimeType is similar to the like-named
 * class in package {@link java.awt.datatransfer java.awt.datatransfer}. Class
 * java.awt.datatransfer.MimeType is not used in the Jini Print Service API
 * for two reasons:
 * <OL TYPE=1>
 * <LI>
 * Since not all Java profiles include the AWT, the Jini Print Service should
 * not depend on an AWT class.
 * <P>
 * <LI>
 * The implementation of class java.awt.datatransfer.MimeType does not
 * guarantee
 * that equivalent MIME types will have the same serialized representation.
 * Thus, since the Jini Lookup Service (JLUS) matches service attributes based
 * on equality of serialized representations, JLUS searches involving MIME
 * types encapsulated in class java.awt.datatransfer.MimeType may incorrectly
 * fail to match.
 * </OL>
 * <P>
 * Class MimeType's serialized representation is based on the following
 * canonical form of a MIME type string. Thus, two MIME types that are not
 * identical but that are equivalent (that have the same canonical form) will
 * be considered equal by the JLUS's matching algorithm.
 * <UL>
 * <LI> The media type, media subtype, and parameters are retained, but all
 *      comments and whitespace characters are discarded.
 * <LI> The media type, media subtype, and parameter names are converted to
 *      lowercase.
 * <LI> The parameter values retain their original case, except a charset
 *      parameter value for a text media type is converted to lowercase.
 * <LI> Quote characters surrounding parameter values are removed.
 * <LI> Quoting backslash characters inside parameter values are removed.
 * <LI> The parameters are arranged in ascending order of parameter name.
 * </UL>
 * <P>
 *
 * <p>
 *  类MimeType封装了<A HREF="http://www.ietf.org/rfc/rfc2045.txt"> RFC 2045 </A>和<A HREF ="中定义的多用途互联网邮件扩展(MIME)媒体类型http://www.ietf.org/rfc/rfc2046.txt">
 *  RFC 2046 </A>。
 *  MIME类型对象是{@link DocFlavor DocFlavor}对象的一部分,并指定打印数据的格式。
 * <P>
 *  类MimeType类似于{@link java.awt.datatransfer java.awt.datatransfer}中类似命名的类。
 *  java.awt.datatransfer.MimeType类未在Jini Print Service API中使用,原因如下：。
 * <OL TYPE=1>
 * <LI>
 *  由于并非所有Java配置文件都包含AWT,因此Jini打印服务不应依赖于AWT类。
 * <P>
 * <LI>
 *  类java.awt.datatransfer.MimeType的实现不保证等价的MIME类型将具有相同的序列化表示。
 * 因此,由于Jini查找服务(JLUS)基于序列化表示的等同性匹配服务属性,因此涉及封装在类java.awt.datatransfer.MimeType中的MIME类型的JLUS搜索可能不正确地匹配失败。
 *  类java.awt.datatransfer.MimeType的实现不保证等价的MIME类型将具有相同的序列化表示。
 * </OL>
 * <P>
 *  MimeType类的序列化表示基于MIME类型字符串的以下规范形式。因此,两个不相同但相同(具有相同的规范形式)的MIME类型将被JLUS的匹配算法视为相等。
 * <UL>
 * <LI>保留媒体类型,媒体子类型和参数,但是所有注释和空格字符都将被丢弃。 <LI>媒体类型,媒体子类型和参数名称将转换为小写。
 *  <LI>除了文本媒体类型的charset参数值转换为小写之外,参数值保留其原始大小写。 <LI>删除周围参数值的引用字符。 <LI>删除参数值中引号反斜杠字符。 <LI>参数按参数名称的升序排列。
 * </UL>
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
class MimeType implements Serializable, Cloneable {

    private static final long serialVersionUID = -2785720609362367683L;

    /**
     * Array of strings that hold pieces of this MIME type's canonical form.
     * If the MIME type has <I>n</I> parameters, <I>n</I> &gt;= 0, then the
     * strings in the array are:
     * <BR>Index 0 -- Media type.
     * <BR>Index 1 -- Media subtype.
     * <BR>Index 2<I>i</I>+2 -- Name of parameter <I>i</I>,
     * <I>i</I>=0,1,...,<I>n</I>-1.
     * <BR>Index 2<I>i</I>+3 -- Value of parameter <I>i</I>,
     * <I>i</I>=0,1,...,<I>n</I>-1.
     * <BR>Parameters are arranged in ascending order of parameter name.
     * <p>
     *  包含此MIME类型的规范形式的部分的字符串数组。如果MIME类型具有<I> n </I>参数,则<I> n </I>&gt; = 0,则数组中的字符串是：<BR>索引0-媒体类型。
     *  <BR>索引1  - 媒体子类型。 <BR>索引2 <i> i </i> +2  - 参数名称<i> i <i>,<i> i <= i, </I> -1。
     *  <BR> Index 2 <i> i </i> +3  - 参数的值<i> i <i>,<i> i = 0,1,..., </I> -1。 <BR>参数按参数名称的升序排列。
     * 
     * 
     * @serial
     */
    private String[] myPieces;

    /**
     * String value for this MIME type. Computed when needed and cached.
     * <p>
     *  此MIME类型的字符串值。计算时需要和缓存。
     * 
     */
    private transient String myStringValue = null;

    /**
     * Parameter map entry set. Computed when needed and cached.
     * <p>
     *  参数映射条目集。计算时需要和缓存。
     * 
     */
    private transient ParameterMapEntrySet myEntrySet = null;

    /**
     * Parameter map. Computed when needed and cached.
     * <p>
     *  参数图。计算时需要和缓存。
     * 
     */
    private transient ParameterMap myParameterMap = null;

    /**
     * Parameter map entry.
     * <p>
     *  参数映射条目。
     * 
     */
    private class ParameterMapEntry implements Map.Entry {
        private int myIndex;
        public ParameterMapEntry(int theIndex) {
            myIndex = theIndex;
        }
        public Object getKey(){
            return myPieces[myIndex];
        }
        public Object getValue(){
            return myPieces[myIndex+1];
        }
        public Object setValue (Object value) {
            throw new UnsupportedOperationException();
        }
        public boolean equals(Object o) {
            return (o != null &&
                    o instanceof Map.Entry &&
                    getKey().equals (((Map.Entry) o).getKey()) &&
                    getValue().equals(((Map.Entry) o).getValue()));
        }
        public int hashCode() {
            return getKey().hashCode() ^ getValue().hashCode();
        }
    }

    /**
     * Parameter map entry set iterator.
     * <p>
     *  参数映射条目集迭代器。
     * 
     */
    private class ParameterMapEntrySetIterator implements Iterator {
        private int myIndex = 2;
        public boolean hasNext() {
            return myIndex < myPieces.length;
        }
        public Object next() {
            if (hasNext()) {
                ParameterMapEntry result = new ParameterMapEntry (myIndex);
                myIndex += 2;
                return result;
            } else {
                throw new NoSuchElementException();
            }
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Parameter map entry set.
     * <p>
     *  参数映射条目集。
     * 
     */
    private class ParameterMapEntrySet extends AbstractSet {
        public Iterator iterator() {
            return new ParameterMapEntrySetIterator();
        }
        public int size() {
            return (myPieces.length - 2) / 2;
        }
    }

    /**
     * Parameter map.
     * <p>
     *  参数图。
     * 
     */
    private class ParameterMap extends AbstractMap {
        public Set entrySet() {
            if (myEntrySet == null) {
                myEntrySet = new ParameterMapEntrySet();
            }
            return myEntrySet;
        }
    }

    /**
     * Construct a new MIME type object from the given string. The given
     * string is converted into canonical form and stored internally.
     *
     * <p>
     *  从给定的字符串构造一个新的MIME类型对象。给定的字符串被转换为规范形式并在内部存储。
     * 
     * 
     * @param  s  MIME media type string.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>s</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>s</CODE> does not obey the
     *     syntax for a MIME media type string.
     */
    public MimeType(String s) {
        parse (s);
    }

    /**
     * Returns this MIME type object's MIME type string based on the canonical
     * form. Each parameter value is enclosed in quotes.
     * <p>
     * 基于规范形式返回此MIME类型对象的MIME类型字符串。每个参数值都用引号括起来。
     * 
     */
    public String getMimeType() {
        return getStringValue();
    }

    /**
     * Returns this MIME type object's media type.
     * <p>
     *  返回此MIME类型对象的媒体类型。
     * 
     */
    public String getMediaType() {
        return myPieces[0];
    }

    /**
     * Returns this MIME type object's media subtype.
     * <p>
     *  返回此MIME类型对象的媒体子类型。
     * 
     */
    public String getMediaSubtype() {
        return myPieces[1];
    }

    /**
     * Returns an unmodifiable map view of the parameters in this MIME type
     * object. Each entry in the parameter map view consists of a parameter
     * name String (key) mapping to a parameter value String. If this MIME
     * type object has no parameters, an empty map is returned.
     *
     * <p>
     *  返回此MIME类型对象中的参数的不可修改的地图视图。参数映射视图中的每个条目由参数名称String(键)映射到参数值String组成。如果此MIME类型对象没有参数,则返回空映射。
     * 
     * 
     * @return  Parameter map for this MIME type object.
     */
    public Map getParameterMap() {
        if (myParameterMap == null) {
            myParameterMap = new ParameterMap();
        }
        return myParameterMap;
    }

    /**
     * Converts this MIME type object to a string.
     *
     * <p>
     *  将此MIME类型对象转换为字符串。
     * 
     * 
     * @return  MIME type string based on the canonical form. Each parameter
     *          value is enclosed in quotes.
     */
    public String toString() {
        return getStringValue();
    }

    /**
     * Returns a hash code for this MIME type object.
     * <p>
     *  返回此MIME类型对象的哈希代码。
     * 
     */
    public int hashCode() {
        return getStringValue().hashCode();
    }

    /**
     * Determine if this MIME type object is equal to the given object. The two
     * are equal if the given object is not null, is an instance of class
     * net.jini.print.data.MimeType, and has the same canonical form as this
     * MIME type object (that is, has the same type, subtype, and parameters).
     * Thus, if two MIME type objects are the same except for comments, they are
     * considered equal. However, "text/plain" and "text/plain;
     * charset=us-ascii" are not considered equal, even though they represent
     * the same media type (because the default character set for plain text is
     * US-ASCII).
     *
     * <p>
     *  确定此MIME类型对象是否等于给定对象。
     * 如果给定对象不为null,那么这两个是相等的,是类net.jini.print.data.MimeType的实例,并且具有与此MIME类型对象相同的规范形式(即,具有相同的类型,子类型和参数)。
     * 因此,如果两个MIME类型对象是相同的,除了注释,它们被认为是相等的。
     * 但是,"text / plain"和"text / plain; charset = us-ascii"不被视为相等,即使它们表示相同的媒体类型(因为纯文本的默认字符集为US-ASCII)。
     * 
     * 
     * @param  obj  Object to test.
     *
     * @return  True if this MIME type object equals <CODE>obj</CODE>, false
     *          otherwise.
     */
    public boolean equals (Object obj) {
        return(obj != null &&
               obj instanceof MimeType &&
               getStringValue().equals(((MimeType) obj).getStringValue()));
    }

    /**
     * Returns this MIME type's string value in canonical form.
     * <p>
     *  以规范形式返回此MIME类型的字符串值。
     * 
     */
    private String getStringValue() {
        if (myStringValue == null) {
            StringBuffer result = new StringBuffer();
            result.append (myPieces[0]);
            result.append ('/');
            result.append (myPieces[1]);
            int n = myPieces.length;
            for (int i = 2; i < n; i += 2) {
                result.append(';');
                result.append(' ');
                result.append(myPieces[i]);
                result.append('=');
                result.append(addQuotes (myPieces[i+1]));
            }
            myStringValue = result.toString();
        }
        return myStringValue;
    }

// Hidden classes, constants, and operations for parsing a MIME media type
// string.

    // Lexeme types.
    private static final int TOKEN_LEXEME         = 0;
    private static final int QUOTED_STRING_LEXEME = 1;
    private static final int TSPECIAL_LEXEME      = 2;
    private static final int EOF_LEXEME           = 3;
    private static final int ILLEGAL_LEXEME       = 4;

    // Class for a lexical analyzer.
    private static class LexicalAnalyzer {
        protected String mySource;
        protected int mySourceLength;
        protected int myCurrentIndex;
        protected int myLexemeType;
        protected int myLexemeBeginIndex;
        protected int myLexemeEndIndex;

        public LexicalAnalyzer(String theSource) {
            mySource = theSource;
            mySourceLength = theSource.length();
            myCurrentIndex = 0;
            nextLexeme();
        }

        public int getLexemeType() {
            return myLexemeType;
        }

        public String getLexeme() {
            return(myLexemeBeginIndex >= mySourceLength ?
                   null :
                   mySource.substring(myLexemeBeginIndex, myLexemeEndIndex));
        }

        public char getLexemeFirstCharacter() {
            return(myLexemeBeginIndex >= mySourceLength ?
                   '\u0000' :
                   mySource.charAt(myLexemeBeginIndex));
        }

        public void nextLexeme() {
            int state = 0;
            int commentLevel = 0;
            char c;
            while (state >= 0) {
                switch (state) {
                    // Looking for a token, quoted string, or tspecial
                case 0:
                    if (myCurrentIndex >= mySourceLength) {
                        myLexemeType = EOF_LEXEME;
                        myLexemeBeginIndex = mySourceLength;
                        myLexemeEndIndex = mySourceLength;
                        state = -1;
                    } else if (Character.isWhitespace
                               (c = mySource.charAt (myCurrentIndex ++))) {
                        state = 0;
                    } else if (c == '\"') {
                        myLexemeType = QUOTED_STRING_LEXEME;
                        myLexemeBeginIndex = myCurrentIndex;
                        state = 1;
                    } else if (c == '(') {
                        ++ commentLevel;
                        state = 3;
                    } else if (c == '/'  || c == ';' || c == '=' ||
                               c == ')'  || c == '<' || c == '>' ||
                               c == '@'  || c == ',' || c == ':' ||
                               c == '\\' || c == '[' || c == ']' ||
                               c == '?') {
                        myLexemeType = TSPECIAL_LEXEME;
                        myLexemeBeginIndex = myCurrentIndex - 1;
                        myLexemeEndIndex = myCurrentIndex;
                        state = -1;
                    } else {
                        myLexemeType = TOKEN_LEXEME;
                        myLexemeBeginIndex = myCurrentIndex - 1;
                        state = 5;
                    }
                    break;
                    // In a quoted string
                case 1:
                    if (myCurrentIndex >= mySourceLength) {
                        myLexemeType = ILLEGAL_LEXEME;
                        myLexemeBeginIndex = mySourceLength;
                        myLexemeEndIndex = mySourceLength;
                        state = -1;
                    } else if ((c = mySource.charAt (myCurrentIndex ++)) == '\"') {
                        myLexemeEndIndex = myCurrentIndex - 1;
                        state = -1;
                    } else if (c == '\\') {
                        state = 2;
                    } else {
                        state = 1;
                    }
                    break;
                    // In a quoted string, backslash seen
                case 2:
                    if (myCurrentIndex >= mySourceLength) {
                        myLexemeType = ILLEGAL_LEXEME;
                        myLexemeBeginIndex = mySourceLength;
                        myLexemeEndIndex = mySourceLength;
                        state = -1;
                    } else {
                        ++ myCurrentIndex;
                        state = 1;
                    } break;
                    // In a comment
                case 3: if (myCurrentIndex >= mySourceLength) {
                    myLexemeType = ILLEGAL_LEXEME;
                    myLexemeBeginIndex = mySourceLength;
                    myLexemeEndIndex = mySourceLength;
                    state = -1;
                } else if ((c = mySource.charAt (myCurrentIndex ++)) == '(') {
                    ++ commentLevel;
                    state = 3;
                } else if (c == ')') {
                    -- commentLevel;
                    state = commentLevel == 0 ? 0 : 3;
                } else if (c == '\\') {
                    state = 4;
                } else { state = 3;
                }
                break;
                // In a comment, backslash seen
                case 4:
                    if (myCurrentIndex >= mySourceLength) {
                        myLexemeType = ILLEGAL_LEXEME;
                        myLexemeBeginIndex = mySourceLength;
                        myLexemeEndIndex = mySourceLength;
                        state = -1;
                    } else {
                        ++ myCurrentIndex;
                        state = 3;
                    }
                    break;
                    // In a token
                case 5:
                    if (myCurrentIndex >= mySourceLength) {
                        myLexemeEndIndex = myCurrentIndex;
                        state = -1;
                    } else if (Character.isWhitespace
                               (c = mySource.charAt (myCurrentIndex ++))) {
                        myLexemeEndIndex = myCurrentIndex - 1;
                        state = -1;
                    } else if (c == '\"' || c == '(' || c == '/' ||
                               c == ';'  || c == '=' || c == ')' ||
                               c == '<' || c == '>'  || c == '@' ||
                               c == ',' || c == ':' || c == '\\' ||
                               c == '[' || c == ']' || c == '?') {
                        -- myCurrentIndex;
                        myLexemeEndIndex = myCurrentIndex;
                        state = -1;
                    } else {
                        state = 5;
                    }
                    break;
                }
            }

        }

    }

    /**
     * Returns a lowercase version of the given string. The lowercase version
     * is constructed by applying Character.toLowerCase() to each character of
     * the given string, which maps characters to lowercase using the rules of
     * Unicode. This mapping is the same regardless of locale, whereas the
     * mapping of String.toLowerCase() may be different depending on the
     * default locale.
     * <p>
     * 返回给定字符串的小写版本。小写版本通过将Character.toLowerCase()应用于给定字符串的每个字符来构造,该字符使用Unicode的规则将字符映射为小写。
     * 无论区域设置如何,此映射都是相同的,而String.toLowerCase()的映射可能根据默认语言环境而不同。
     * 
     */
    private static String toUnicodeLowerCase(String s) {
        int n = s.length();
        char[] result = new char [n];
        for (int i = 0; i < n; ++ i) {
            result[i] = Character.toLowerCase (s.charAt (i));
        }
        return new String (result);
    }

    /**
     * Returns a version of the given string with backslashes removed.
     * <p>
     *  返回已删除反斜杠的给定字符串的版本。
     * 
     */
    private static String removeBackslashes(String s) {
        int n = s.length();
        char[] result = new char [n];
        int i;
        int j = 0;
        char c;
        for (i = 0; i < n; ++ i) {
            c = s.charAt (i);
            if (c == '\\') {
                c = s.charAt (++ i);
            }
            result[j++] = c;
        }
        return new String (result, 0, j);
    }

    /**
     * Returns a version of the string surrounded by quotes and with interior
     * quotes preceded by a backslash.
     * <p>
     *  返回由引号和前面带有反斜杠的内部引号括起来的字符串的版本。
     * 
     */
    private static String addQuotes(String s) {
        int n = s.length();
        int i;
        char c;
        StringBuffer result = new StringBuffer (n+2);
        result.append ('\"');
        for (i = 0; i < n; ++ i) {
            c = s.charAt (i);
            if (c == '\"') {
                result.append ('\\');
            }
            result.append (c);
        }
        result.append ('\"');
        return result.toString();
    }

    /**
     * Parses the given string into canonical pieces and stores the pieces in
     * {@link #myPieces <CODE>myPieces</CODE>}.
     * <P>
     * Special rules applied:
     * <UL>
     * <LI> If the media type is text, the value of a charset parameter is
     *      converted to lowercase.
     * </UL>
     *
     * <p>
     *  将给定的字符串解析为规范片段,并将片段存储在{@link #myPieces <CODE> myPieces </CODE>}中。
     * <P>
     *  应用特殊规则：
     * <UL>
     * 
     * @param  s  MIME media type string.
     *
     * @exception  NullPointerException
     *     (unchecked exception) Thrown if <CODE>s</CODE> is null.
     * @exception  IllegalArgumentException
     *     (unchecked exception) Thrown if <CODE>s</CODE> does not obey the
     *     syntax for a MIME media type string.
     */
    private void parse(String s) {
        // Initialize.
        if (s == null) {
            throw new NullPointerException();
        }
        LexicalAnalyzer theLexer = new LexicalAnalyzer (s);
        int theLexemeType;
        Vector thePieces = new Vector();
        boolean mediaTypeIsText = false;
        boolean parameterNameIsCharset = false;

        // Parse media type.
        if (theLexer.getLexemeType() == TOKEN_LEXEME) {
            String mt = toUnicodeLowerCase (theLexer.getLexeme());
            thePieces.add (mt);
            theLexer.nextLexeme();
            mediaTypeIsText = mt.equals ("text");
        } else {
            throw new IllegalArgumentException();
        }
        // Parse slash.
        if (theLexer.getLexemeType() == TSPECIAL_LEXEME &&
              theLexer.getLexemeFirstCharacter() == '/') {
            theLexer.nextLexeme();
        } else {
            throw new IllegalArgumentException();
        }
        if (theLexer.getLexemeType() == TOKEN_LEXEME) {
            thePieces.add (toUnicodeLowerCase (theLexer.getLexeme()));
            theLexer.nextLexeme();
        } else {
            throw new IllegalArgumentException();
        }
        // Parse zero or more parameters.
        while (theLexer.getLexemeType() == TSPECIAL_LEXEME &&
               theLexer.getLexemeFirstCharacter() == ';') {
            // Parse semicolon.
            theLexer.nextLexeme();

            // Parse parameter name.
            if (theLexer.getLexemeType() == TOKEN_LEXEME) {
                String pn = toUnicodeLowerCase (theLexer.getLexeme());
                thePieces.add (pn);
                theLexer.nextLexeme();
                parameterNameIsCharset = pn.equals ("charset");
            } else {
                throw new IllegalArgumentException();
            }

            // Parse equals.
            if (theLexer.getLexemeType() == TSPECIAL_LEXEME &&
                theLexer.getLexemeFirstCharacter() == '=') {
                theLexer.nextLexeme();
            } else {
                throw new IllegalArgumentException();
            }

            // Parse parameter value.
            if (theLexer.getLexemeType() == TOKEN_LEXEME) {
                String pv = theLexer.getLexeme();
                thePieces.add(mediaTypeIsText && parameterNameIsCharset ?
                              toUnicodeLowerCase (pv) :
                              pv);
                theLexer.nextLexeme();
            } else if (theLexer.getLexemeType() == QUOTED_STRING_LEXEME) {
                String pv = removeBackslashes (theLexer.getLexeme());
                thePieces.add(mediaTypeIsText && parameterNameIsCharset ?
                              toUnicodeLowerCase (pv) :
                              pv);
                theLexer.nextLexeme();
            } else {
                throw new IllegalArgumentException();
            }
        }

        // Make sure we've consumed everything.
        if (theLexer.getLexemeType() != EOF_LEXEME) {
            throw new IllegalArgumentException();
        }

        // Save the pieces. Parameters are not in ascending order yet.
        int n = thePieces.size();
        myPieces = (String[]) thePieces.toArray (new String [n]);

        // Sort the parameters into ascending order using an insertion sort.
        int i, j;
        String temp;
        for (i = 4; i < n; i += 2) {
            j = 2;
            while (j < i && myPieces[j].compareTo (myPieces[i]) <= 0) {
                j += 2;
            }
            while (j < i) {
                temp = myPieces[j];
                myPieces[j] = myPieces[i];
                myPieces[i] = temp;
                temp = myPieces[j+1];
                myPieces[j+1] = myPieces[i+1];
                myPieces[i+1] = temp;
                j += 2;
            }
        }
    }
}
