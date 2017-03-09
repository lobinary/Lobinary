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
 * A typesafe enumeration for coding-error actions.
 *
 * <p> Instances of this class are used to specify how malformed-input and
 * unmappable-character errors are to be handled by charset <a
 * href="CharsetDecoder.html#cae">decoders</a> and <a
 * href="CharsetEncoder.html#cae">encoders</a>.  </p>
 *
 *
 * <p>
 *  编码错误操作的类型安全枚举。
 * 
 *  <p>此类的实例用于指定charset <a href="CharsetDecoder.html#cae">解码器</a>和<a href ="CharsetEncoder如何处理错误输入和不可映射字符错误的方式.html#cae">
 * 编码器</a>。
 *  </p>。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public class CodingErrorAction {

    private String name;

    private CodingErrorAction(String name) {
        this.name = name;
    }

    /**
     * Action indicating that a coding error is to be handled by dropping the
     * erroneous input and resuming the coding operation.
     * <p>
     *  通过丢弃错误输入并恢复编码操作来指示要处理编码错误的动作。
     * 
     */
    public static final CodingErrorAction IGNORE
        = new CodingErrorAction("IGNORE");

    /**
     * Action indicating that a coding error is to be handled by dropping the
     * erroneous input, appending the coder's replacement value to the output
     * buffer, and resuming the coding operation.
     * <p>
     *  指示通过丢弃错误输入来处理编码错误的动作,将编码器的替换值附加到输出缓冲器,以及恢复编码操作。
     * 
     */
    public static final CodingErrorAction REPLACE
        = new CodingErrorAction("REPLACE");

    /**
     * Action indicating that a coding error is to be reported, either by
     * returning a {@link CoderResult} object or by throwing a {@link
     * CharacterCodingException}, whichever is appropriate for the method
     * implementing the coding process.
     * <p>
     *  指示要报告编码错误的动作,通过返回{@link CoderResult}对象或抛出{@link CharacterCodingException}(适用于实施编码过程的方法)。
     * 
     */
    public static final CodingErrorAction REPORT
        = new CodingErrorAction("REPORT");

    /**
     * Returns a string describing this action.
     *
     * <p>
     *  返回描述此操作的字符串。
     * 
     * @return  A descriptive string
     */
    public String toString() {
        return name;
    }

}
