/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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


/**
 * Checked exception thrown to indicate that a string could not be parsed as a
 * URI reference.
 *
 * <p>
 *  检查异常抛出以指示无法将字符串解析为URI引用。
 * 
 * 
 * @author Mark Reinhold
 * @see URI
 * @since 1.4
 */

public class URISyntaxException
    extends Exception
{
    private static final long serialVersionUID = 2137979680897488891L;

    private String input;
    private int index;

    /**
     * Constructs an instance from the given input string, reason, and error
     * index.
     *
     * <p>
     *  根据给定的输入字符串,原因和错误索引构造实例。
     * 
     * 
     * @param  input   The input string
     * @param  reason  A string explaining why the input could not be parsed
     * @param  index   The index at which the parse error occurred,
     *                 or {@code -1} if the index is not known
     *
     * @throws  NullPointerException
     *          If either the input or reason strings are {@code null}
     *
     * @throws  IllegalArgumentException
     *          If the error index is less than {@code -1}
     */
    public URISyntaxException(String input, String reason, int index) {
        super(reason);
        if ((input == null) || (reason == null))
            throw new NullPointerException();
        if (index < -1)
            throw new IllegalArgumentException();
        this.input = input;
        this.index = index;
    }

    /**
     * Constructs an instance from the given input string and reason.  The
     * resulting object will have an error index of {@code -1}.
     *
     * <p>
     *  根据给定的输入字符串和原因构造一个实例。结果对象的错误索引为{@code -1}。
     * 
     * 
     * @param  input   The input string
     * @param  reason  A string explaining why the input could not be parsed
     *
     * @throws  NullPointerException
     *          If either the input or reason strings are {@code null}
     */
    public URISyntaxException(String input, String reason) {
        this(input, reason, -1);
    }

    /**
     * Returns the input string.
     *
     * <p>
     *  返回输入字符串。
     * 
     * 
     * @return  The input string
     */
    public String getInput() {
        return input;
    }

    /**
     * Returns a string explaining why the input string could not be parsed.
     *
     * <p>
     *  返回一个字符串,解释为什么不能解析输入字符串。
     * 
     * 
     * @return  The reason string
     */
    public String getReason() {
        return super.getMessage();
    }

    /**
     * Returns an index into the input string of the position at which the
     * parse error occurred, or {@code -1} if this position is not known.
     *
     * <p>
     *  将索引返回到发生解析错误的位置的输入字符串,如果此位置未知,则返回{@code -1}。
     * 
     * 
     * @return  The error index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns a string describing the parse error.  The resulting string
     * consists of the reason string followed by a colon character
     * ({@code ':'}), a space, and the input string.  If the error index is
     * defined then the string {@code " at index "} followed by the index, in
     * decimal, is inserted after the reason string and before the colon
     * character.
     *
     * <p>
     *  返回描述解析错误的字符串。结果字符串由原始字符串后跟冒号字符({@code'：'}),空格和输入字符串组成。
     * 如果定义了错误索引,那么在十进制中的索引之后的字符串{@code"at index"}插入到原因字符串之后,冒号字符之前。
     * 
     * @return  A string describing the parse error
     */
    public String getMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append(getReason());
        if (index > -1) {
            sb.append(" at index ");
            sb.append(index);
        }
        sb.append(": ");
        sb.append(input);
        return sb.toString();
    }

}
