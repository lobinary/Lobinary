/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

import java.util.Arrays;

/**
 * The {@code StreamTokenizer} class takes an input stream and
 * parses it into "tokens", allowing the tokens to be
 * read one at a time. The parsing process is controlled by a table
 * and a number of flags that can be set to various states. The
 * stream tokenizer can recognize identifiers, numbers, quoted
 * strings, and various comment styles.
 * <p>
 * Each byte read from the input stream is regarded as a character
 * in the range {@code '\u005Cu0000'} through {@code '\u005Cu00FF'}.
 * The character value is used to look up five possible attributes of
 * the character: <i>white space</i>, <i>alphabetic</i>,
 * <i>numeric</i>, <i>string quote</i>, and <i>comment character</i>.
 * Each character can have zero or more of these attributes.
 * <p>
 * In addition, an instance has four flags. These flags indicate:
 * <ul>
 * <li>Whether line terminators are to be returned as tokens or treated
 *     as white space that merely separates tokens.
 * <li>Whether C-style comments are to be recognized and skipped.
 * <li>Whether C++-style comments are to be recognized and skipped.
 * <li>Whether the characters of identifiers are converted to lowercase.
 * </ul>
 * <p>
 * A typical application first constructs an instance of this class,
 * sets up the syntax tables, and then repeatedly loops calling the
 * {@code nextToken} method in each iteration of the loop until
 * it returns the value {@code TT_EOF}.
 *
 * <p>
 *  {@code StreamTokenizer}类接受一个输入流并将其解析为"令牌",允许一次读取一个令牌。解析过程由表和可设置为各种状态的多个标志控制。
 * 流标记器可以识别标识符,数字,引用的字符串和各种注释样式。
 * <p>
 *  从输入流读取的每个字节被视为范围{@code'\ u005Cu0000'}到{@code'\ u005Cu00FF'}中的字符。
 * 字符值用于查找字符的五个可能属性：<i>空格</i>,<i>字母</i>,<i>数字</i> i>和<i>注释字符</i>。每个字符可以具有零个或多个这些属性。
 * <p>
 *  此外,一个实例有四个标志。这些标志指示：
 * <ul>
 *  <li>行终止符是作为令牌返回还是作为仅分隔令牌的空白处理。 <li>是否识别和跳过C风格的评论。 <li>是否要识别和跳过C ++样式的注释。 <li>标识符的字符是否转换为小写。
 * </ul>
 * <p>
 *  典型的应用程序首先构造这个类的实例,设置语法表,然后在循环的每次迭代中重复循环调用{@code nextToken}方法,直到它返回值{@code TT_EOF}。
 * 
 * 
 * @author  James Gosling
 * @see     java.io.StreamTokenizer#nextToken()
 * @see     java.io.StreamTokenizer#TT_EOF
 * @since   JDK1.0
 */

public class StreamTokenizer {

    /* Only one of these will be non-null */
    private Reader reader = null;
    private InputStream input = null;

    private char buf[] = new char[20];

    /**
     * The next character to be considered by the nextToken method.  May also
     * be NEED_CHAR to indicate that a new character should be read, or SKIP_LF
     * to indicate that a new character should be read and, if it is a '\n'
     * character, it should be discarded and a second new character should be
     * read.
     * <p>
     * nextToken方法要考虑的下一个字符。也可以是NEED_CHAR以指示应当读取新字符,或者SKIP_LF以指示应当读取新字符,并且如果其是"\ n"字符,则应该丢弃该字符,并且应当读取第二新字符。
     * 
     */
    private int peekc = NEED_CHAR;

    private static final int NEED_CHAR = Integer.MAX_VALUE;
    private static final int SKIP_LF = Integer.MAX_VALUE - 1;

    private boolean pushedBack;
    private boolean forceLower;
    /** The line number of the last token read */
    private int LINENO = 1;

    private boolean eolIsSignificantP = false;
    private boolean slashSlashCommentsP = false;
    private boolean slashStarCommentsP = false;

    private byte ctype[] = new byte[256];
    private static final byte CT_WHITESPACE = 1;
    private static final byte CT_DIGIT = 2;
    private static final byte CT_ALPHA = 4;
    private static final byte CT_QUOTE = 8;
    private static final byte CT_COMMENT = 16;

    /**
     * After a call to the {@code nextToken} method, this field
     * contains the type of the token just read. For a single character
     * token, its value is the single character, converted to an integer.
     * For a quoted string token, its value is the quote character.
     * Otherwise, its value is one of the following:
     * <ul>
     * <li>{@code TT_WORD} indicates that the token is a word.
     * <li>{@code TT_NUMBER} indicates that the token is a number.
     * <li>{@code TT_EOL} indicates that the end of line has been read.
     *     The field can only have this value if the
     *     {@code eolIsSignificant} method has been called with the
     *     argument {@code true}.
     * <li>{@code TT_EOF} indicates that the end of the input stream
     *     has been reached.
     * </ul>
     * <p>
     * The initial value of this field is -4.
     *
     * <p>
     *  调用{@code nextToken}方法后,此字段包含刚读取的令牌的类型。对于单个字符令牌,其值为单个字符,转换为整数。对于带引号的字符串令牌,其值是引号字符。否则,其值为以下之一：
     * <ul>
     *  <li> {@ code TT_WORD}表示该令牌是一个字。 <li> {@ code TT_NUMBER}表示该令牌是一个数字。 <li> {@ code TT_EOL}表示行末行已读取。
     * 如果使用参数{@code true}调用{@code eolIsSignificant}方法,该字段只能有此值。 <li> {@ code TT_EOF}表示已达到输入流的结尾。
     * </ul>
     * <p>
     *  此字段的初始值为-4。
     * 
     * 
     * @see     java.io.StreamTokenizer#eolIsSignificant(boolean)
     * @see     java.io.StreamTokenizer#nextToken()
     * @see     java.io.StreamTokenizer#quoteChar(int)
     * @see     java.io.StreamTokenizer#TT_EOF
     * @see     java.io.StreamTokenizer#TT_EOL
     * @see     java.io.StreamTokenizer#TT_NUMBER
     * @see     java.io.StreamTokenizer#TT_WORD
     */
    public int ttype = TT_NOTHING;

    /**
     * A constant indicating that the end of the stream has been read.
     * <p>
     *  指示流的结尾已被读取的常量。
     * 
     */
    public static final int TT_EOF = -1;

    /**
     * A constant indicating that the end of the line has been read.
     * <p>
     *  一个常数,表示行的结束已被读取。
     * 
     */
    public static final int TT_EOL = '\n';

    /**
     * A constant indicating that a number token has been read.
     * <p>
     *  指示已读取数字令牌的常量。
     * 
     */
    public static final int TT_NUMBER = -2;

    /**
     * A constant indicating that a word token has been read.
     * <p>
     *  指示已读取字令牌的常量。
     * 
     */
    public static final int TT_WORD = -3;

    /* A constant indicating that no token has been read, used for
     * initializing ttype.  FIXME This could be made public and
     * made available as the part of the API in a future release.
     * <p>
     *  初始化ttype。 FIXME这可以被公开,并在未来的版本中作为API的一部分。
     * 
     */
    private static final int TT_NOTHING = -4;

    /**
     * If the current token is a word token, this field contains a
     * string giving the characters of the word token. When the current
     * token is a quoted string token, this field contains the body of
     * the string.
     * <p>
     * The current token is a word when the value of the
     * {@code ttype} field is {@code TT_WORD}. The current token is
     * a quoted string token when the value of the {@code ttype} field is
     * a quote character.
     * <p>
     * The initial value of this field is null.
     *
     * <p>
     * 如果当前令牌是字令牌,则此字段包含给出字令牌的字符的字符串。当当前令牌是带引号的字符串令牌时,此字段包含字符串的主体。
     * <p>
     *  当{@code ttype}字段的值为{@code TT_WORD}时,当前令牌是一个字。当{@code ttype}字段的值是引号字符时,当前令牌是带引号的字符串令牌。
     * <p>
     *  此字段的初始值为null。
     * 
     * 
     * @see     java.io.StreamTokenizer#quoteChar(int)
     * @see     java.io.StreamTokenizer#TT_WORD
     * @see     java.io.StreamTokenizer#ttype
     */
    public String sval;

    /**
     * If the current token is a number, this field contains the value
     * of that number. The current token is a number when the value of
     * the {@code ttype} field is {@code TT_NUMBER}.
     * <p>
     * The initial value of this field is 0.0.
     *
     * <p>
     *  如果当前令牌是数字,则此字段包含该数字的值。当{@code ttype}字段的值为{@code TT_NUMBER}时,当前令牌是一个数字。
     * <p>
     *  此字段的初始值为0.0。
     * 
     * 
     * @see     java.io.StreamTokenizer#TT_NUMBER
     * @see     java.io.StreamTokenizer#ttype
     */
    public double nval;

    /** Private constructor that initializes everything except the streams. */
    private StreamTokenizer() {
        wordChars('a', 'z');
        wordChars('A', 'Z');
        wordChars(128 + 32, 255);
        whitespaceChars(0, ' ');
        commentChar('/');
        quoteChar('"');
        quoteChar('\'');
        parseNumbers();
    }

    /**
     * Creates a stream tokenizer that parses the specified input
     * stream. The stream tokenizer is initialized to the following
     * default state:
     * <ul>
     * <li>All byte values {@code 'A'} through {@code 'Z'},
     *     {@code 'a'} through {@code 'z'}, and
     *     {@code '\u005Cu00A0'} through {@code '\u005Cu00FF'} are
     *     considered to be alphabetic.
     * <li>All byte values {@code '\u005Cu0000'} through
     *     {@code '\u005Cu0020'} are considered to be white space.
     * <li>{@code '/'} is a comment character.
     * <li>Single quote {@code '\u005C''} and double quote {@code '"'}
     *     are string quote characters.
     * <li>Numbers are parsed.
     * <li>Ends of lines are treated as white space, not as separate tokens.
     * <li>C-style and C++-style comments are not recognized.
     * </ul>
     *
     * <p>
     *  创建解析指定输入流的流标记器。流标记器初始化为以下默认状态：
     * <ul>
     *  <li>所有字节值{@code'A'}到{@code'Z'},{@code'a'}到{@code'z'}和{@code'\ u005Cu00A0'}到{@代码'\ u005Cu00FF'}被认为是字母
     * 的。
     *  <li>所有字节值{@code'\ u005Cu0000'}到{@code'\ u005Cu0020'}被视为空白。 <li> {@ code'/'}是一个注释字符。
     *  <li>单引号{@code'\ u005C''}和双引号{@code'"''是字符串引号字符。<li>解析数字。<li>行的结尾被视为空格,而不是单独的标记。
     * <li>无法识别C风格和C ++风格的评论。
     * </ul>
     * 
     * 
     * @deprecated As of JDK version 1.1, the preferred way to tokenize an
     * input stream is to convert it into a character stream, for example:
     * <blockquote><pre>
     *   Reader r = new BufferedReader(new InputStreamReader(is));
     *   StreamTokenizer st = new StreamTokenizer(r);
     * </pre></blockquote>
     *
     * @param      is        an input stream.
     * @see        java.io.BufferedReader
     * @see        java.io.InputStreamReader
     * @see        java.io.StreamTokenizer#StreamTokenizer(java.io.Reader)
     */
    @Deprecated
    public StreamTokenizer(InputStream is) {
        this();
        if (is == null) {
            throw new NullPointerException();
        }
        input = is;
    }

    /**
     * Create a tokenizer that parses the given character stream.
     *
     * <p>
     *  创建一个分词给定字符流的分词器。
     * 
     * 
     * @param r  a Reader object providing the input stream.
     * @since   JDK1.1
     */
    public StreamTokenizer(Reader r) {
        this();
        if (r == null) {
            throw new NullPointerException();
        }
        reader = r;
    }

    /**
     * Resets this tokenizer's syntax table so that all characters are
     * "ordinary." See the {@code ordinaryChar} method
     * for more information on a character being ordinary.
     *
     * <p>
     * 重置此标记器的语法表,以使所有字符为"普通"。有关普通字符的更多信息,请参阅{@code ordinaryChar}方法。
     * 
     * 
     * @see     java.io.StreamTokenizer#ordinaryChar(int)
     */
    public void resetSyntax() {
        for (int i = ctype.length; --i >= 0;)
            ctype[i] = 0;
    }

    /**
     * Specifies that all characters <i>c</i> in the range
     * <code>low&nbsp;&lt;=&nbsp;<i>c</i>&nbsp;&lt;=&nbsp;high</code>
     * are word constituents. A word token consists of a word constituent
     * followed by zero or more word constituents or number constituents.
     *
     * <p>
     *  指定<code> low&lt; =&nbsp;&lt; = c&gt;&lt; =&nbsp; high&lt; / code&gt;范围内的所有字符<i> c </i>词令牌由词组成,后面跟着零个
     * 或多个词成分或数字成分组成。
     * 
     * 
     * @param   low   the low end of the range.
     * @param   hi    the high end of the range.
     */
    public void wordChars(int low, int hi) {
        if (low < 0)
            low = 0;
        if (hi >= ctype.length)
            hi = ctype.length - 1;
        while (low <= hi)
            ctype[low++] |= CT_ALPHA;
    }

    /**
     * Specifies that all characters <i>c</i> in the range
     * <code>low&nbsp;&lt;=&nbsp;<i>c</i>&nbsp;&lt;=&nbsp;high</code>
     * are white space characters. White space characters serve only to
     * separate tokens in the input stream.
     *
     * <p>Any other attribute settings for the characters in the specified
     * range are cleared.
     *
     * <p>
     *  指定<code> low&lt; =&nbsp;&lt; = c&gt;&lt; =&nbsp; high&lt; / code&gt;范围内的所有字符<i> c </i>都是空白字符。
     * 空白字符仅用于分隔输入流中的标记。
     * 
     *  <p>清除指定范围内的字符的任何其他属性设置。
     * 
     * 
     * @param   low   the low end of the range.
     * @param   hi    the high end of the range.
     */
    public void whitespaceChars(int low, int hi) {
        if (low < 0)
            low = 0;
        if (hi >= ctype.length)
            hi = ctype.length - 1;
        while (low <= hi)
            ctype[low++] = CT_WHITESPACE;
    }

    /**
     * Specifies that all characters <i>c</i> in the range
     * <code>low&nbsp;&lt;=&nbsp;<i>c</i>&nbsp;&lt;=&nbsp;high</code>
     * are "ordinary" in this tokenizer. See the
     * {@code ordinaryChar} method for more information on a
     * character being ordinary.
     *
     * <p>
     *  指定<code> low&lt; =&nbsp; <i> c </i>&lt; =&nbsp; high </code>范围内的所有字符<i> c </i>这个分词器。
     * 有关普通字符的更多信息,请参阅{@code ordinaryChar}方法。
     * 
     * 
     * @param   low   the low end of the range.
     * @param   hi    the high end of the range.
     * @see     java.io.StreamTokenizer#ordinaryChar(int)
     */
    public void ordinaryChars(int low, int hi) {
        if (low < 0)
            low = 0;
        if (hi >= ctype.length)
            hi = ctype.length - 1;
        while (low <= hi)
            ctype[low++] = 0;
    }

    /**
     * Specifies that the character argument is "ordinary"
     * in this tokenizer. It removes any special significance the
     * character has as a comment character, word component, string
     * delimiter, white space, or number character. When such a character
     * is encountered by the parser, the parser treats it as a
     * single-character token and sets {@code ttype} field to the
     * character value.
     *
     * <p>Making a line terminator character "ordinary" may interfere
     * with the ability of a {@code StreamTokenizer} to count
     * lines. The {@code lineno} method may no longer reflect
     * the presence of such terminator characters in its line count.
     *
     * <p>
     *  指定此标记器中的字符参数是"普通"。它删除了字符作为注释字符,字组件,字符串分隔符,空格或数字字符的任何特殊含义。
     * 当解析器遇到这样的字符时,解析器将其视为单字符标记,并将{@code ttype}字段设置为字符值。
     * 
     * <p>将"终止符"字符设置为"普通"可能会干扰{@code StreamTokenizer}计算行数的能力。 {@code lineno}方法可能不再反映在其行计数中存在这样的终止符字符。
     * 
     * 
     * @param   ch   the character.
     * @see     java.io.StreamTokenizer#ttype
     */
    public void ordinaryChar(int ch) {
        if (ch >= 0 && ch < ctype.length)
            ctype[ch] = 0;
    }

    /**
     * Specified that the character argument starts a single-line
     * comment. All characters from the comment character to the end of
     * the line are ignored by this stream tokenizer.
     *
     * <p>Any other attribute settings for the specified character are cleared.
     *
     * <p>
     *  指定字符参数启动单行注释。此流标记器忽略从注释字符到行尾的所有字符。
     * 
     *  <p>清除指定字符的任何其他属性设置。
     * 
     * 
     * @param   ch   the character.
     */
    public void commentChar(int ch) {
        if (ch >= 0 && ch < ctype.length)
            ctype[ch] = CT_COMMENT;
    }

    /**
     * Specifies that matching pairs of this character delimit string
     * constants in this tokenizer.
     * <p>
     * When the {@code nextToken} method encounters a string
     * constant, the {@code ttype} field is set to the string
     * delimiter and the {@code sval} field is set to the body of
     * the string.
     * <p>
     * If a string quote character is encountered, then a string is
     * recognized, consisting of all characters after (but not including)
     * the string quote character, up to (but not including) the next
     * occurrence of that same string quote character, or a line
     * terminator, or end of file. The usual escape sequences such as
     * {@code "\u005Cn"} and {@code "\u005Ct"} are recognized and
     * converted to single characters as the string is parsed.
     *
     * <p>Any other attribute settings for the specified character are cleared.
     *
     * <p>
     *  指定此字符的匹配对在此标记器中分隔字符串常量。
     * <p>
     *  当{@code nextToken}方法遇到字符串常量时,{@code ttype}字段设置为字符串分隔符,而{@code sval}字段设置为字符串的正文。
     * <p>
     *  如果遇到字符串引用字符,则识别字符串,包括字符串引号字符之后(但不包括)之后的所有字符,直到(但不包括)该相同字符串引号字符的下一个出现,或者行终止符,或文件结尾。
     * 在解析字符串时,通常的转义序列,例如{@code"\ u005Cn"}和{@code"\ u005Ct"}被识别并转换为单个字符。
     * 
     *  <p>清除指定字符的任何其他属性设置。
     * 
     * 
     * @param   ch   the character.
     * @see     java.io.StreamTokenizer#nextToken()
     * @see     java.io.StreamTokenizer#sval
     * @see     java.io.StreamTokenizer#ttype
     */
    public void quoteChar(int ch) {
        if (ch >= 0 && ch < ctype.length)
            ctype[ch] = CT_QUOTE;
    }

    /**
     * Specifies that numbers should be parsed by this tokenizer. The
     * syntax table of this tokenizer is modified so that each of the twelve
     * characters:
     * <blockquote><pre>
     *      0 1 2 3 4 5 6 7 8 9 . -
     * </pre></blockquote>
     * <p>
     * has the "numeric" attribute.
     * <p>
     * When the parser encounters a word token that has the format of a
     * double precision floating-point number, it treats the token as a
     * number rather than a word, by setting the {@code ttype}
     * field to the value {@code TT_NUMBER} and putting the numeric
     * value of the token into the {@code nval} field.
     *
     * <p>
     *  指定数字应由此分词器解析。修改此分词器的语法表,以使十二个字符中的每一个：<blockquote> <pre> 0 1 2 3 4 5 6 7 8 9。
     *  -  </pre> </blockquote>。
     * <p>
     *  具有"numeric"属性。
     * <p>
     * 当解析器遇到具有双精度浮点数格式的字令牌时,通过将{@code ttype}字段设置为值{@code TT_NUMBER},将令牌视为数字而不是字词将令牌的数值放入{@code nval}字段。
     * 
     * 
     * @see     java.io.StreamTokenizer#nval
     * @see     java.io.StreamTokenizer#TT_NUMBER
     * @see     java.io.StreamTokenizer#ttype
     */
    public void parseNumbers() {
        for (int i = '0'; i <= '9'; i++)
            ctype[i] |= CT_DIGIT;
        ctype['.'] |= CT_DIGIT;
        ctype['-'] |= CT_DIGIT;
    }

    /**
     * Determines whether or not ends of line are treated as tokens.
     * If the flag argument is true, this tokenizer treats end of lines
     * as tokens; the {@code nextToken} method returns
     * {@code TT_EOL} and also sets the {@code ttype} field to
     * this value when an end of line is read.
     * <p>
     * A line is a sequence of characters ending with either a
     * carriage-return character ({@code '\u005Cr'}) or a newline
     * character ({@code '\u005Cn'}). In addition, a carriage-return
     * character followed immediately by a newline character is treated
     * as a single end-of-line token.
     * <p>
     * If the {@code flag} is false, end-of-line characters are
     * treated as white space and serve only to separate tokens.
     *
     * <p>
     *  确定行尾是否被视为令牌。
     * 如果flag参数为真,则这个分词器将行的结束当作令牌; {@code nextToken}方法返回{@code TT_EOL},并且在读取行尾时将{@code ttype}字段设置为此值。
     * <p>
     *  行是以回车符({@code'\ u005Cr'})或换行符({@code'\ u005Cn'})结尾的字符序列。此外,紧接着换行符后面的回车字符被视为单个行尾符号。
     * <p>
     *  如果{@code flag}为false,行尾字符将被视为空格,并且仅用于分隔令牌。
     * 
     * 
     * @param   flag   {@code true} indicates that end-of-line characters
     *                 are separate tokens; {@code false} indicates that
     *                 end-of-line characters are white space.
     * @see     java.io.StreamTokenizer#nextToken()
     * @see     java.io.StreamTokenizer#ttype
     * @see     java.io.StreamTokenizer#TT_EOL
     */
    public void eolIsSignificant(boolean flag) {
        eolIsSignificantP = flag;
    }

    /**
     * Determines whether or not the tokenizer recognizes C-style comments.
     * If the flag argument is {@code true}, this stream tokenizer
     * recognizes C-style comments. All text between successive
     * occurrences of {@code /*} and <code>*&#47;</code> are discarded.
     * <p>
     * If the flag argument is {@code false}, then C-style comments
     * are not treated specially.
     *
     * <p>
     *  确定标记器是否识别C样式注释。如果flag参数是{@code true},这个流标记器识别C风格的注释。
     * 连续出现的{@code / *}和<code> * / </code>之间的所有文本都将被丢弃。
     * <p>
     *  如果flag参数是{@code false},那么C风格的注释不会被特别处理。
     * 
     * 
     * @param   flag   {@code true} indicates to recognize and ignore
     *                 C-style comments.
     */
    public void slashStarComments(boolean flag) {
        slashStarCommentsP = flag;
    }

    /**
     * Determines whether or not the tokenizer recognizes C++-style comments.
     * If the flag argument is {@code true}, this stream tokenizer
     * recognizes C++-style comments. Any occurrence of two consecutive
     * slash characters ({@code '/'}) is treated as the beginning of
     * a comment that extends to the end of the line.
     * <p>
     * If the flag argument is {@code false}, then C++-style
     * comments are not treated specially.
     *
     * <p>
     * 确定标记器是否识别C ++样式的注释。如果flag参数是{@code true},这个流标记器识别C ++风格的注释。任何出现两个连续斜杠字符({@code'/'})将被视为延伸到行尾的注释的开始。
     * <p>
     *  如果flag参数是{@code false},那么不会特别处理C ++样式的注释。
     * 
     * 
     * @param   flag   {@code true} indicates to recognize and ignore
     *                 C++-style comments.
     */
    public void slashSlashComments(boolean flag) {
        slashSlashCommentsP = flag;
    }

    /**
     * Determines whether or not word token are automatically lowercased.
     * If the flag argument is {@code true}, then the value in the
     * {@code sval} field is lowercased whenever a word token is
     * returned (the {@code ttype} field has the
     * value {@code TT_WORD} by the {@code nextToken} method
     * of this tokenizer.
     * <p>
     * If the flag argument is {@code false}, then the
     * {@code sval} field is not modified.
     *
     * <p>
     *  确定字令牌是否自动缩小。
     * 如果flag参数是{@code true},那么每当返回一个字符({@code ttype}字段的值为{@code TT_WORD})时,{@code sval}代码nextToken}方法。
     * <p>
     *  如果flag参数是{@code false},那么{@code sval}字段不会被修改。
     * 
     * 
     * @param   fl   {@code true} indicates that all word tokens should
     *               be lowercased.
     * @see     java.io.StreamTokenizer#nextToken()
     * @see     java.io.StreamTokenizer#ttype
     * @see     java.io.StreamTokenizer#TT_WORD
     */
    public void lowerCaseMode(boolean fl) {
        forceLower = fl;
    }

    /** Read the next character */
    private int read() throws IOException {
        if (reader != null)
            return reader.read();
        else if (input != null)
            return input.read();
        else
            throw new IllegalStateException();
    }

    /**
     * Parses the next token from the input stream of this tokenizer.
     * The type of the next token is returned in the {@code ttype}
     * field. Additional information about the token may be in the
     * {@code nval} field or the {@code sval} field of this
     * tokenizer.
     * <p>
     * Typical clients of this
     * class first set up the syntax tables and then sit in a loop
     * calling nextToken to parse successive tokens until TT_EOF
     * is returned.
     *
     * <p>
     *  从此分词器的输入流中解析下一个令牌。下一个令牌的类型在{@code ttype}字段中返回。
     * 有关令牌的其他信息可能在此tokenizer的{@code nval}字段或{@code sval}字段中。
     * <p>
     *  这个类的典型客户端首先设置语法表,然后在循环中调用nextToken来解析连续的令牌,直到返回TT_EOF。
     * 
     * 
     * @return     the value of the {@code ttype} field.
     * @exception  IOException  if an I/O error occurs.
     * @see        java.io.StreamTokenizer#nval
     * @see        java.io.StreamTokenizer#sval
     * @see        java.io.StreamTokenizer#ttype
     */
    public int nextToken() throws IOException {
        if (pushedBack) {
            pushedBack = false;
            return ttype;
        }
        byte ct[] = ctype;
        sval = null;

        int c = peekc;
        if (c < 0)
            c = NEED_CHAR;
        if (c == SKIP_LF) {
            c = read();
            if (c < 0)
                return ttype = TT_EOF;
            if (c == '\n')
                c = NEED_CHAR;
        }
        if (c == NEED_CHAR) {
            c = read();
            if (c < 0)
                return ttype = TT_EOF;
        }
        ttype = c;              /* Just to be safe */

        /* Set peekc so that the next invocation of nextToken will read
         * another character unless peekc is reset in this invocation
         * <p>
         *  另一个字符,除非peekc在此调用中重置
         * 
         */
        peekc = NEED_CHAR;

        int ctype = c < 256 ? ct[c] : CT_ALPHA;
        while ((ctype & CT_WHITESPACE) != 0) {
            if (c == '\r') {
                LINENO++;
                if (eolIsSignificantP) {
                    peekc = SKIP_LF;
                    return ttype = TT_EOL;
                }
                c = read();
                if (c == '\n')
                    c = read();
            } else {
                if (c == '\n') {
                    LINENO++;
                    if (eolIsSignificantP) {
                        return ttype = TT_EOL;
                    }
                }
                c = read();
            }
            if (c < 0)
                return ttype = TT_EOF;
            ctype = c < 256 ? ct[c] : CT_ALPHA;
        }

        if ((ctype & CT_DIGIT) != 0) {
            boolean neg = false;
            if (c == '-') {
                c = read();
                if (c != '.' && (c < '0' || c > '9')) {
                    peekc = c;
                    return ttype = '-';
                }
                neg = true;
            }
            double v = 0;
            int decexp = 0;
            int seendot = 0;
            while (true) {
                if (c == '.' && seendot == 0)
                    seendot = 1;
                else if ('0' <= c && c <= '9') {
                    v = v * 10 + (c - '0');
                    decexp += seendot;
                } else
                    break;
                c = read();
            }
            peekc = c;
            if (decexp != 0) {
                double denom = 10;
                decexp--;
                while (decexp > 0) {
                    denom *= 10;
                    decexp--;
                }
                /* Do one division of a likely-to-be-more-accurate number */
                v = v / denom;
            }
            nval = neg ? -v : v;
            return ttype = TT_NUMBER;
        }

        if ((ctype & CT_ALPHA) != 0) {
            int i = 0;
            do {
                if (i >= buf.length) {
                    buf = Arrays.copyOf(buf, buf.length * 2);
                }
                buf[i++] = (char) c;
                c = read();
                ctype = c < 0 ? CT_WHITESPACE : c < 256 ? ct[c] : CT_ALPHA;
            } while ((ctype & (CT_ALPHA | CT_DIGIT)) != 0);
            peekc = c;
            sval = String.copyValueOf(buf, 0, i);
            if (forceLower)
                sval = sval.toLowerCase();
            return ttype = TT_WORD;
        }

        if ((ctype & CT_QUOTE) != 0) {
            ttype = c;
            int i = 0;
            /* Invariants (because \Octal needs a lookahead):
             *   (i)  c contains char value
             *   (ii) d contains the lookahead
             * <p>
             *  (i)c包含char值(ii)d包含lookahead
             * 
             */
            int d = read();
            while (d >= 0 && d != ttype && d != '\n' && d != '\r') {
                if (d == '\\') {
                    c = read();
                    int first = c;   /* To allow \377, but not \477 */
                    if (c >= '0' && c <= '7') {
                        c = c - '0';
                        int c2 = read();
                        if ('0' <= c2 && c2 <= '7') {
                            c = (c << 3) + (c2 - '0');
                            c2 = read();
                            if ('0' <= c2 && c2 <= '7' && first <= '3') {
                                c = (c << 3) + (c2 - '0');
                                d = read();
                            } else
                                d = c2;
                        } else
                          d = c2;
                    } else {
                        switch (c) {
                        case 'a':
                            c = 0x7;
                            break;
                        case 'b':
                            c = '\b';
                            break;
                        case 'f':
                            c = 0xC;
                            break;
                        case 'n':
                            c = '\n';
                            break;
                        case 'r':
                            c = '\r';
                            break;
                        case 't':
                            c = '\t';
                            break;
                        case 'v':
                            c = 0xB;
                            break;
                        }
                        d = read();
                    }
                } else {
                    c = d;
                    d = read();
                }
                if (i >= buf.length) {
                    buf = Arrays.copyOf(buf, buf.length * 2);
                }
                buf[i++] = (char)c;
            }

            /* If we broke out of the loop because we found a matching quote
             * character then arrange to read a new character next time
             * around; otherwise, save the character.
             * <p>
             *  字符,然后安排下次读取一个新字符;否则,保存字符。
             * 
             */
            peekc = (d == ttype) ? NEED_CHAR : d;

            sval = String.copyValueOf(buf, 0, i);
            return ttype;
        }

        if (c == '/' && (slashSlashCommentsP || slashStarCommentsP)) {
            c = read();
            if (c == '*' && slashStarCommentsP) {
                int prevc = 0;
                while ((c = read()) != '/' || prevc != '*') {
                    if (c == '\r') {
                        LINENO++;
                        c = read();
                        if (c == '\n') {
                            c = read();
                        }
                    } else {
                        if (c == '\n') {
                            LINENO++;
                            c = read();
                        }
                    }
                    if (c < 0)
                        return ttype = TT_EOF;
                    prevc = c;
                }
                return nextToken();
            } else if (c == '/' && slashSlashCommentsP) {
                while ((c = read()) != '\n' && c != '\r' && c >= 0);
                peekc = c;
                return nextToken();
            } else {
                /* Now see if it is still a single line comment */
                if ((ct['/'] & CT_COMMENT) != 0) {
                    while ((c = read()) != '\n' && c != '\r' && c >= 0);
                    peekc = c;
                    return nextToken();
                } else {
                    peekc = c;
                    return ttype = '/';
                }
            }
        }

        if ((ctype & CT_COMMENT) != 0) {
            while ((c = read()) != '\n' && c != '\r' && c >= 0);
            peekc = c;
            return nextToken();
        }

        return ttype = c;
    }

    /**
     * Causes the next call to the {@code nextToken} method of this
     * tokenizer to return the current value in the {@code ttype}
     * field, and not to modify the value in the {@code nval} or
     * {@code sval} field.
     *
     * <p>
     * 导致对此记号化器的{@code nextToken}方法的下一次调用返回{@code ttype}字段中的当前值,而不修改{@code nval}或{@code sval}字段中的值。
     * 
     * 
     * @see     java.io.StreamTokenizer#nextToken()
     * @see     java.io.StreamTokenizer#nval
     * @see     java.io.StreamTokenizer#sval
     * @see     java.io.StreamTokenizer#ttype
     */
    public void pushBack() {
        if (ttype != TT_NOTHING)   /* No-op if nextToken() not called */
            pushedBack = true;
    }

    /**
     * Return the current line number.
     *
     * <p>
     *  返回当前行号。
     * 
     * 
     * @return  the current line number of this stream tokenizer.
     */
    public int lineno() {
        return LINENO;
    }

    /**
     * Returns the string representation of the current stream token and
     * the line number it occurs on.
     *
     * <p>The precise string returned is unspecified, although the following
     * example can be considered typical:
     *
     * <blockquote><pre>Token['a'], line 10</pre></blockquote>
     *
     * <p>
     *  返回当前流标记的字符串表示形式以及它发生的行号。
     * 
     *  <p>返回的精确字符串未指定,但以下示例可被视为典型：
     * 
     *  <blockquote> <pre> Token ['a'],第10行</pre> </blockquote>
     * 
     * @return  a string representation of the token
     * @see     java.io.StreamTokenizer#nval
     * @see     java.io.StreamTokenizer#sval
     * @see     java.io.StreamTokenizer#ttype
     */
    public String toString() {
        String ret;
        switch (ttype) {
          case TT_EOF:
            ret = "EOF";
            break;
          case TT_EOL:
            ret = "EOL";
            break;
          case TT_WORD:
            ret = sval;
            break;
          case TT_NUMBER:
            ret = "n=" + nval;
            break;
          case TT_NOTHING:
            ret = "NOTHING";
            break;
          default: {
                /*
                 * ttype is the first character of either a quoted string or
                 * is an ordinary character. ttype can definitely not be less
                 * than 0, since those are reserved values used in the previous
                 * case statements
                 * <p>
                 * 
                 */
                if (ttype < 256 &&
                    ((ctype[ttype] & CT_QUOTE) != 0)) {
                    ret = sval;
                    break;
                }

                char s[] = new char[3];
                s[0] = s[2] = '\'';
                s[1] = (char) ttype;
                ret = new String(s);
                break;
            }
        }
        return "Token[" + ret + "], line " + LINENO;
    }

}
