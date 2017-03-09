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

package java.nio.charset;


/**
 * Checked exception thrown when an input byte sequence is not legal for given
 * charset, or an input character sequence is not a legal sixteen-bit Unicode
 * sequence.
 *
 * <p>
 *  当输入字节序列对于给定字符集不合法时,或者输入字符序列不是合法的16位Unicode序列时,抛出此异常。
 * 
 * 
 * @since 1.4
 */

public class MalformedInputException
    extends CharacterCodingException
{

    private static final long serialVersionUID = -3438823399834806194L;

    private int inputLength;

    /**
     * Constructs an {@code MalformedInputException} with the given
     * length.
     * <p>
     *  构造具有给定长度的{@code MalformedInputException}。
     * 
     * 
     * @param inputLength the length of the input
     */
    public MalformedInputException(int inputLength) {
        this.inputLength = inputLength;
    }

    /**
     * Returns the length of the input.
     * <p>
     *  返回输入的长度。
     * 
     * 
     * @return the length of the input
     */
    public int getInputLength() {
        return inputLength;
    }

    /**
     * Returns the message.
     * <p>
     *  返回消息。
     * 
     * @return the message
     */
    public String getMessage() {
        return "Input length = " + inputLength;
    }

}
