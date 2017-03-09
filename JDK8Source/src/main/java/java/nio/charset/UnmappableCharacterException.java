/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Checked exception thrown when an input character (or byte) sequence
 * is valid but cannot be mapped to an output byte (or character)
 * sequence.
 *
 * <p>
 *  当输入字符(或字节)序列有效但无法映射到输出字节(或字符)序列时抛出的检查异常。
 * 
 * 
 * @since 1.4
 */

public class UnmappableCharacterException
    extends CharacterCodingException
{

    private static final long serialVersionUID = -7026962371537706123L;

    private int inputLength;

    /**
     * Constructs an {@code UnmappableCharacterException} with the
     * given length.
     * <p>
     *  构造具有给定长度的{@code UnmappableCharacterException}。
     * 
     * 
     * @param inputLength the length of the input
     */
    public UnmappableCharacterException(int inputLength) {
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
