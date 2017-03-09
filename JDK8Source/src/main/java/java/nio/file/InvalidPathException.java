/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file;

/**
 * Unchecked exception thrown when path string cannot be converted into a
 * {@link Path} because the path string contains invalid characters, or
 * the path string is invalid for other file system specific reasons.
 * <p>
 *  由于路径字符串包含无效字符,或者路径字符串对其他文件系统特定原因无效,因此无法将路径字符串转换为{@link Path}时抛出未检查的异常。
 * 
 */

public class InvalidPathException
    extends IllegalArgumentException
{
    static final long serialVersionUID = 4355821422286746137L;

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
     * @param  input   the input string
     * @param  reason  a string explaining why the input was rejected
     * @param  index   the index at which the error occurred,
     *                 or <tt>-1</tt> if the index is not known
     *
     * @throws  NullPointerException
     *          if either the input or reason strings are <tt>null</tt>
     *
     * @throws  IllegalArgumentException
     *          if the error index is less than <tt>-1</tt>
     */
    public InvalidPathException(String input, String reason, int index) {
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
     * resulting object will have an error index of <tt>-1</tt>.
     *
     * <p>
     *  根据给定的输入字符串和原因构造一个实例。生成的对象的错误索引为<tt> -1 </tt>。
     * 
     * 
     * @param  input   the input string
     * @param  reason  a string explaining why the input was rejected
     *
     * @throws  NullPointerException
     *          if either the input or reason strings are <tt>null</tt>
     */
    public InvalidPathException(String input, String reason) {
        this(input, reason, -1);
    }

    /**
     * Returns the input string.
     *
     * <p>
     *  返回输入字符串。
     * 
     * 
     * @return  the input string
     */
    public String getInput() {
        return input;
    }

    /**
     * Returns a string explaining why the input string was rejected.
     *
     * <p>
     *  返回一个字符串,解释为什么输入字符串被拒绝。
     * 
     * 
     * @return  the reason string
     */
    public String getReason() {
        return super.getMessage();
    }

    /**
     * Returns an index into the input string of the position at which the
     * error occurred, or <tt>-1</tt> if this position is not known.
     *
     * <p>
     *  将索引返回到发生错误的位置的输入字符串,如果此位置未知,则返回<tt> -1 </tt>。
     * 
     * 
     * @return  the error index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Returns a string describing the error.  The resulting string
     * consists of the reason string followed by a colon character
     * (<tt>':'</tt>), a space, and the input string.  If the error index is
     * defined then the string <tt>" at index "</tt> followed by the index, in
     * decimal, is inserted after the reason string and before the colon
     * character.
     *
     * <p>
     *  返回描述错误的字符串。结果字符串由原始字符串后跟冒号字符(<tt>'：'</tt>),空格和输入字符串组成。
     * 如果定义了错误索引,则在原因字符串之后并在冒号字符之前插入字符串<tt>"at index"</tt>,后跟十进制的索引。
     * 
     * @return  a string describing the error
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
