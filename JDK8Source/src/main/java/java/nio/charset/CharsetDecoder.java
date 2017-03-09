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

// -- This file was mechanically generated: Do not edit! -- //

package java.nio.charset;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.lang.ref.WeakReference;
import java.nio.charset.CoderMalfunctionError;                  // javadoc
import java.util.Arrays;


/**
 * An engine that can transform a sequence of bytes in a specific charset into a sequence of
 * sixteen-bit Unicode characters.
 *
 * <a name="steps"></a>
 *
 * <p> The input byte sequence is provided in a byte buffer or a series
 * of such buffers.  The output character sequence is written to a character buffer
 * or a series of such buffers.  A decoder should always be used by making
 * the following sequence of method invocations, hereinafter referred to as a
 * <i>decoding operation</i>:
 *
 * <ol>
 *
 *   <li><p> Reset the decoder via the {@link #reset reset} method, unless it
 *   has not been used before; </p></li>
 *
 *   <li><p> Invoke the {@link #decode decode} method zero or more times, as
 *   long as additional input may be available, passing <tt>false</tt> for the
 *   <tt>endOfInput</tt> argument and filling the input buffer and flushing the
 *   output buffer between invocations; </p></li>
 *
 *   <li><p> Invoke the {@link #decode decode} method one final time, passing
 *   <tt>true</tt> for the <tt>endOfInput</tt> argument; and then </p></li>
 *
 *   <li><p> Invoke the {@link #flush flush} method so that the decoder can
 *   flush any internal state to the output buffer. </p></li>
 *
 * </ol>
 *
 * Each invocation of the {@link #decode decode} method will decode as many
 * bytes as possible from the input buffer, writing the resulting characters
 * to the output buffer.  The {@link #decode decode} method returns when more
 * input is required, when there is not enough room in the output buffer, or
 * when a decoding error has occurred.  In each case a {@link CoderResult}
 * object is returned to describe the reason for termination.  An invoker can
 * examine this object and fill the input buffer, flush the output buffer, or
 * attempt to recover from a decoding error, as appropriate, and try again.
 *
 * <a name="ce"></a>
 *
 * <p> There are two general types of decoding errors.  If the input byte
 * sequence is not legal for this charset then the input is considered <i>malformed</i>.  If
 * the input byte sequence is legal but cannot be mapped to a valid
 * Unicode character then an <i>unmappable character</i> has been encountered.
 *
 * <a name="cae"></a>
 *
 * <p> How a decoding error is handled depends upon the action requested for
 * that type of error, which is described by an instance of the {@link
 * CodingErrorAction} class.  The possible error actions are to {@linkplain
 * CodingErrorAction#IGNORE ignore} the erroneous input, {@linkplain
 * CodingErrorAction#REPORT report} the error to the invoker via
 * the returned {@link CoderResult} object, or {@linkplain CodingErrorAction#REPLACE
 * replace} the erroneous input with the current value of the
 * replacement string.  The replacement
 *





 * has the initial value <tt>"&#92;uFFFD"</tt>;

 *
 * its value may be changed via the {@link #replaceWith(java.lang.String)
 * replaceWith} method.
 *
 * <p> The default action for malformed-input and unmappable-character errors
 * is to {@linkplain CodingErrorAction#REPORT report} them.  The
 * malformed-input error action may be changed via the {@link
 * #onMalformedInput(CodingErrorAction) onMalformedInput} method; the
 * unmappable-character action may be changed via the {@link
 * #onUnmappableCharacter(CodingErrorAction) onUnmappableCharacter} method.
 *
 * <p> This class is designed to handle many of the details of the decoding
 * process, including the implementation of error actions.  A decoder for a
 * specific charset, which is a concrete subclass of this class, need only
 * implement the abstract {@link #decodeLoop decodeLoop} method, which
 * encapsulates the basic decoding loop.  A subclass that maintains internal
 * state should, additionally, override the {@link #implFlush implFlush} and
 * {@link #implReset implReset} methods.
 *
 * <p> Instances of this class are not safe for use by multiple concurrent
 * threads.  </p>
 *
 *
 * <p>
 *  一种引擎,可以将特定字符集中的字节序列转换为十六位Unicode字符序列。
 * 
 *  <a name="steps"> </a>
 * 
 *  <p>输入字节序列在字节缓冲器或一系列这样的缓冲器中提供。输出字符序列被写入字符缓冲器或一系列这样的缓冲器。通过进行以下的方法调用序列(下文中称为"解码操作")来始终使用解码器：
 * 
 * <ol>
 * 
 *  <li> <p>通过{@link #reset reset}方法重置解码器,除非之前未使用过; </p> </li>
 * 
 *  <li> <p>调用{@link #decode decode}方法零次或多次,只要可能有其他输入可用,对<tt> endOfInput </tt>参数传递<tt> false </tt>以及填充所述
 * 输入缓冲器并在调用之间刷新所述输出缓冲器; </p> </li>。
 * 
 *  <li> <p>最后调用{@link #decode decode}方法,对<tt> endOfInput </tt>参数传递<tt> true </tt>;然后</p> </li>
 * 
 *  <li> <p>调用{@link #flush flush}方法,以便解码器可以将任何内部状态刷新到输出缓冲区。 </p> </li>
 * 
 * </ol>
 * 
 * 每次调用{@link #decode decode}方法将从输入缓冲区解码尽可能多的字节,将生成的字符写入输出缓冲区。
 * 当需要更多输入时,输出缓冲区中没有足够空间或发生解码错误时,{@link #decode decode}方法返回。在每种情况下,返回一个{@link CoderResult}对象以描述终止的原因。
 * 调用者可以检查此对象并填充输入缓冲区,刷新输出缓冲区,或尝试从解码错误中恢复(如果合适),然后重试。
 * 
 *  <a name="ce"> </a>
 * 
 *  <p>有两种一般类型的解码错误。如果输入字节序列对于此字符集不合法,则输入被认为是<i>格式错误</i>。
 * 如果输入字节序列是合法的,但是不能映射到有效的Unicode字符,则遇到不可映射的字符</i>。
 * 
 *  <a name="cae"> </a>
 * 
 *  <p>如何处理解码错误取决于对{@link CodingErrorAction}类的实例描述的那种错误类型请求的操作。
 * 可能的错误操作是通过返回的{@link CoderResult}对象或{@linkplain CodingErrorAction#REPLACE replace}将错误的{@linkplain CodingErrorAction#IGNORE ignore}
 * 错误的输入{@linkplain CodingErrorAction#REPORT report}具有替换字符串的当前值的错误输入。
 *  <p>如何处理解码错误取决于对{@link CodingErrorAction}类的实例描述的那种错误类型请求的操作。更换。
 * 
 * 具有初始值<tt>"\ uFFFD"</tt>;
 * 
 *  它的值可以通过{@link #replaceWith(java.lang.String)replaceWith}方法更改。
 * 
 *  <p>格式错误输入和不可映射字符错误的默认操作是{@linkplain CodingErrorAction#REPORT report}。
 * 格式错误的输入错误操作可以通过{@link #onMalformedInput(CodingErrorAction)onMalformedInput}方法更改;可以通过{@link #onUnmappableCharacter(CodingErrorAction)onUnmappableCharacter}
 * 方法更改不可映射字符操作。
 *  <p>格式错误输入和不可映射字符错误的默认操作是{@linkplain CodingErrorAction#REPORT report}。
 * 
 *  <p>此类设计用于处理解码过程的许多细节,包括执行错误操作。
 * 用于特定字符集的解码器(其是该类的具体子类)仅需要实现抽象{@link #decodeLoop decodeLoop}方法,其封装了基本解码循环。
 * 另外,维护内部状态的子类应该覆盖{@link #implFlush implFlush}和{@link #implReset implReset}方法。
 * 
 *  <p>此类的实例不适合由多个并发线程使用。 </p>
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 *
 * @see ByteBuffer
 * @see CharBuffer
 * @see Charset
 * @see CharsetEncoder
 */

public abstract class CharsetDecoder {

    private final Charset charset;
    private final float averageCharsPerByte;
    private final float maxCharsPerByte;

    private String replacement;
    private CodingErrorAction malformedInputAction
        = CodingErrorAction.REPORT;
    private CodingErrorAction unmappableCharacterAction
        = CodingErrorAction.REPORT;

    // Internal states
    //
    private static final int ST_RESET   = 0;
    private static final int ST_CODING  = 1;
    private static final int ST_END     = 2;
    private static final int ST_FLUSHED = 3;

    private int state = ST_RESET;

    private static String stateNames[]
        = { "RESET", "CODING", "CODING_END", "FLUSHED" };


    /**
     * Initializes a new decoder.  The new decoder will have the given
     * chars-per-byte and replacement values.
     *
     * <p>
     *  初始化一个新的解码器。新的解码器将具有给定的字节每字节和替换值。
     * 
     * 
     * @param  cs
     *         The charset that created this decoder
     *
     * @param  averageCharsPerByte
     *         A positive float value indicating the expected number of
     *         characters that will be produced for each input byte
     *
     * @param  maxCharsPerByte
     *         A positive float value indicating the maximum number of
     *         characters that will be produced for each input byte
     *
     * @param  replacement
     *         The initial replacement; must not be <tt>null</tt>, must have
     *         non-zero length, must not be longer than maxCharsPerByte,
     *         and must be {@linkplain #isLegalReplacement legal}
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     */
    private
    CharsetDecoder(Charset cs,
                   float averageCharsPerByte,
                   float maxCharsPerByte,
                   String replacement)
    {
        this.charset = cs;
        if (averageCharsPerByte <= 0.0f)
            throw new IllegalArgumentException("Non-positive "
                                               + "averageCharsPerByte");
        if (maxCharsPerByte <= 0.0f)
            throw new IllegalArgumentException("Non-positive "
                                               + "maxCharsPerByte");
        if (!Charset.atBugLevel("1.4")) {
            if (averageCharsPerByte > maxCharsPerByte)
                throw new IllegalArgumentException("averageCharsPerByte"
                                                   + " exceeds "
                                                   + "maxCharsPerByte");
        }
        this.replacement = replacement;
        this.averageCharsPerByte = averageCharsPerByte;
        this.maxCharsPerByte = maxCharsPerByte;
        replaceWith(replacement);
    }

    /**
     * Initializes a new decoder.  The new decoder will have the given
     * chars-per-byte values and its replacement will be the
     * string <tt>"&#92;uFFFD"</tt>.
     *
     * <p>
     *  初始化一个新的解码器。新的解码器将具有给定的字节每字节值,并且其替换将是字符串<tt>"\ uFFFD"</tt>。
     * 
     * 
     * @param  cs
     *         The charset that created this decoder
     *
     * @param  averageCharsPerByte
     *         A positive float value indicating the expected number of
     *         characters that will be produced for each input byte
     *
     * @param  maxCharsPerByte
     *         A positive float value indicating the maximum number of
     *         characters that will be produced for each input byte
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     */
    protected CharsetDecoder(Charset cs,
                             float averageCharsPerByte,
                             float maxCharsPerByte)
    {
        this(cs,
             averageCharsPerByte, maxCharsPerByte,
             "\uFFFD");
    }

    /**
     * Returns the charset that created this decoder.
     *
     * <p>
     *  返回创建此解码器的字符集。
     * 
     * 
     * @return  This decoder's charset
     */
    public final Charset charset() {
        return charset;
    }

    /**
     * Returns this decoder's replacement value.
     *
     * <p>
     *  返回此解码器的替换值。
     * 
     * 
     * @return  This decoder's current replacement,
     *          which is never <tt>null</tt> and is never empty
     */
    public final String replacement() {

        return replacement;




    }

    /**
     * Changes this decoder's replacement value.
     *
     * <p> This method invokes the {@link #implReplaceWith implReplaceWith}
     * method, passing the new replacement, after checking that the new
     * replacement is acceptable.  </p>
     *
     * <p>
     *  更改此解码器的替换值。
     * 
     * <p>此方法调用{@link #implReplaceWith implReplaceWith}方法,在检查新替换是否可接受后,传递新替换。 </p>
     * 
     * 
     * @param  newReplacement  The replacement value
     *

     *         The new replacement; must not be <tt>null</tt>
     *         and must have non-zero length







     *
     * @return  This decoder
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameter do not hold
     */
    public final CharsetDecoder replaceWith(String newReplacement) {
        if (newReplacement == null)
            throw new IllegalArgumentException("Null replacement");
        int len = newReplacement.length();
        if (len == 0)
            throw new IllegalArgumentException("Empty replacement");
        if (len > maxCharsPerByte)
            throw new IllegalArgumentException("Replacement too long");

        this.replacement = newReplacement;






        implReplaceWith(this.replacement);
        return this;
    }

    /**
     * Reports a change to this decoder's replacement value.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by decoders that require notification of changes to
     * the replacement.  </p>
     *
     * <p>
     *  报告对此解码器的替换值的更改。
     * 
     *  <p>此方法的默认实现不执行任何操作。该方法应当由需要通知更换更换的解码器覆盖。 </p>
     * 
     * 
     * @param  newReplacement    The replacement value
     */
    protected void implReplaceWith(String newReplacement) {
    }









































    /**
     * Returns this decoder's current action for malformed-input errors.
     *
     * <p>
     *  返回此解码器对错误输入错误的当前操作。
     * 
     * 
     * @return The current malformed-input action, which is never <tt>null</tt>
     */
    public CodingErrorAction malformedInputAction() {
        return malformedInputAction;
    }

    /**
     * Changes this decoder's action for malformed-input errors.
     *
     * <p> This method invokes the {@link #implOnMalformedInput
     * implOnMalformedInput} method, passing the new action.  </p>
     *
     * <p>
     *  更改此解码器对错误输入错误的操作。
     * 
     *  <p>此方法调用{@link #implOnMalformedInput implOnMalformedInput}方法,传递新操作。 </p>
     * 
     * 
     * @param  newAction  The new action; must not be <tt>null</tt>
     *
     * @return  This decoder
     *
     * @throws IllegalArgumentException
     *         If the precondition on the parameter does not hold
     */
    public final CharsetDecoder onMalformedInput(CodingErrorAction newAction) {
        if (newAction == null)
            throw new IllegalArgumentException("Null action");
        malformedInputAction = newAction;
        implOnMalformedInput(newAction);
        return this;
    }

    /**
     * Reports a change to this decoder's malformed-input action.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by decoders that require notification of changes to
     * the malformed-input action.  </p>
     *
     * <p>
     *  报告对此解码器格式不正确的输入操作的更改。
     * 
     *  <p>此方法的默认实现不执行任何操作。该方法应当由需要通知对格式错误输入动作的改变的解码器覆盖。 </p>
     * 
     * 
     * @param  newAction  The new action
     */
    protected void implOnMalformedInput(CodingErrorAction newAction) { }

    /**
     * Returns this decoder's current action for unmappable-character errors.
     *
     * <p>
     *  返回此解码器的当前操作的不可映射字符错误。
     * 
     * 
     * @return The current unmappable-character action, which is never
     *         <tt>null</tt>
     */
    public CodingErrorAction unmappableCharacterAction() {
        return unmappableCharacterAction;
    }

    /**
     * Changes this decoder's action for unmappable-character errors.
     *
     * <p> This method invokes the {@link #implOnUnmappableCharacter
     * implOnUnmappableCharacter} method, passing the new action.  </p>
     *
     * <p>
     *  更改此解码器对不可映射字符错误的操作。
     * 
     *  <p>此方法调用{@link #implOnUnmappableCharacter implOnUnmappableCharacter}方法,传递新操作。 </p>
     * 
     * 
     * @param  newAction  The new action; must not be <tt>null</tt>
     *
     * @return  This decoder
     *
     * @throws IllegalArgumentException
     *         If the precondition on the parameter does not hold
     */
    public final CharsetDecoder onUnmappableCharacter(CodingErrorAction
                                                      newAction)
    {
        if (newAction == null)
            throw new IllegalArgumentException("Null action");
        unmappableCharacterAction = newAction;
        implOnUnmappableCharacter(newAction);
        return this;
    }

    /**
     * Reports a change to this decoder's unmappable-character action.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by decoders that require notification of changes to
     * the unmappable-character action.  </p>
     *
     * <p>
     *  报告对此解码器不可映射字符操作的更改。
     * 
     *  <p>此方法的默认实现不执行任何操作。此方法应该被需要通知对不可映射字符操作的改变的解码器覆盖。 </p>
     * 
     * 
     * @param  newAction  The new action
     */
    protected void implOnUnmappableCharacter(CodingErrorAction newAction) { }

    /**
     * Returns the average number of characters that will be produced for each
     * byte of input.  This heuristic value may be used to estimate the size
     * of the output buffer required for a given input sequence.
     *
     * <p>
     * 返回将为输入的每个字节生成的平均字符数。该启发式值可以用于估计给定输入序列所需的输出缓冲区的大小。
     * 
     * 
     * @return  The average number of characters produced
     *          per byte of input
     */
    public final float averageCharsPerByte() {
        return averageCharsPerByte;
    }

    /**
     * Returns the maximum number of characters that will be produced for each
     * byte of input.  This value may be used to compute the worst-case size
     * of the output buffer required for a given input sequence.
     *
     * <p>
     *  返回将为输入的每个字节生成的最大字符数。该值可用于计算给定输入序列所需的输出缓冲区的最坏情况大小。
     * 
     * 
     * @return  The maximum number of characters that will be produced per
     *          byte of input
     */
    public final float maxCharsPerByte() {
        return maxCharsPerByte;
    }

    /**
     * Decodes as many bytes as possible from the given input buffer,
     * writing the results to the given output buffer.
     *
     * <p> The buffers are read from, and written to, starting at their current
     * positions.  At most {@link Buffer#remaining in.remaining()} bytes
     * will be read and at most {@link Buffer#remaining out.remaining()}
     * characters will be written.  The buffers' positions will be advanced to
     * reflect the bytes read and the characters written, but their marks and
     * limits will not be modified.
     *
     * <p> In addition to reading bytes from the input buffer and writing
     * characters to the output buffer, this method returns a {@link CoderResult}
     * object to describe its reason for termination:
     *
     * <ul>
     *
     *   <li><p> {@link CoderResult#UNDERFLOW} indicates that as much of the
     *   input buffer as possible has been decoded.  If there is no further
     *   input then the invoker can proceed to the next step of the
     *   <a href="#steps">decoding operation</a>.  Otherwise this method
     *   should be invoked again with further input.  </p></li>
     *
     *   <li><p> {@link CoderResult#OVERFLOW} indicates that there is
     *   insufficient space in the output buffer to decode any more bytes.
     *   This method should be invoked again with an output buffer that has
     *   more {@linkplain Buffer#remaining remaining} characters. This is
     *   typically done by draining any decoded characters from the output
     *   buffer.  </p></li>
     *
     *   <li><p> A {@linkplain CoderResult#malformedForLength
     *   malformed-input} result indicates that a malformed-input
     *   error has been detected.  The malformed bytes begin at the input
     *   buffer's (possibly incremented) position; the number of malformed
     *   bytes may be determined by invoking the result object's {@link
     *   CoderResult#length() length} method.  This case applies only if the
     *   {@linkplain #onMalformedInput malformed action} of this decoder
     *   is {@link CodingErrorAction#REPORT}; otherwise the malformed input
     *   will be ignored or replaced, as requested.  </p></li>
     *
     *   <li><p> An {@linkplain CoderResult#unmappableForLength
     *   unmappable-character} result indicates that an
     *   unmappable-character error has been detected.  The bytes that
     *   decode the unmappable character begin at the input buffer's (possibly
     *   incremented) position; the number of such bytes may be determined
     *   by invoking the result object's {@link CoderResult#length() length}
     *   method.  This case applies only if the {@linkplain #onUnmappableCharacter
     *   unmappable action} of this decoder is {@link
     *   CodingErrorAction#REPORT}; otherwise the unmappable character will be
     *   ignored or replaced, as requested.  </p></li>
     *
     * </ul>
     *
     * In any case, if this method is to be reinvoked in the same decoding
     * operation then care should be taken to preserve any bytes remaining
     * in the input buffer so that they are available to the next invocation.
     *
     * <p> The <tt>endOfInput</tt> parameter advises this method as to whether
     * the invoker can provide further input beyond that contained in the given
     * input buffer.  If there is a possibility of providing additional input
     * then the invoker should pass <tt>false</tt> for this parameter; if there
     * is no possibility of providing further input then the invoker should
     * pass <tt>true</tt>.  It is not erroneous, and in fact it is quite
     * common, to pass <tt>false</tt> in one invocation and later discover that
     * no further input was actually available.  It is critical, however, that
     * the final invocation of this method in a sequence of invocations always
     * pass <tt>true</tt> so that any remaining undecoded input will be treated
     * as being malformed.
     *
     * <p> This method works by invoking the {@link #decodeLoop decodeLoop}
     * method, interpreting its results, handling error conditions, and
     * reinvoking it as necessary.  </p>
     *
     *
     * <p>
     *  从给定的输入缓冲区解码尽可能多的字节,将结果写入给定的输出缓冲区。
     * 
     *  <p>缓冲区从其当前位置开始读取和写入。
     * 最多只能读取{@link Buffer#remaining in.remaining()}字节,最多只能写入{@link Buffer#remaining out.remaining()}字符。
     * 缓冲区的位置将被提前以反映读取的字节和写入的字符,但不会修改它们的标记和限制。
     * 
     *  <p>除了从输入缓冲区读取字节并将字符写入输出缓冲区之外,此方法还返回一个{@link CoderResult}对象以描述其终止的原因：
     * 
     * <ul>
     * 
     *  <li> <p> {@link CoderResult#UNDERFLOW}表示尽可能多的输入缓冲区已解码。
     * 如果没有其他输入,则调用者可以继续进行<a href="#steps">解码操作</a>的下一步。否则,应该再次使用进一步的输入来调用此方法。 </p> </li>。
     * 
     * <li> <p> {@link CoderResult#OVERFLOW}表示输出缓冲区中没有足够的空间来解码任何更多字节。
     * 应该使用具有更多{@linkplain Buffer#remaining remaining}个字符的输出缓冲区再次调用此方法。这通常通过从输出缓冲器中排出任何解码的字符来完成。
     *  </p> </li>。
     * 
     *  <li> <p> {@linkplain CoderResult#malformedForLength格式错误的输入}结果表示检测到格式错误的输入错误。
     * 格式错误的字节从输入缓冲区(可能递增)的位置开始;可以通过调用结果对象的{@link CoderResult#length()length}方法来确定畸形字节的数量。
     * 此情况仅适用于此解码器的{@linkplain #onMalformedInput畸形操作}为{@link CodingErrorAction#REPORT};否则将根据请求忽略或替换格式错误的输入。
     *  </p> </li>。
     * 
     * <li> <p> {@linkplain CoderResult#unmappableForLength unmappable-character}结果表示检测到不可映射字符错误。
     * 解码不可映射字符的字节从输入缓冲区的(可能递增的)位置开始;可以通过调用结果对象的{@link CoderResult#length()length}方法来确定这样的字节的数量。
     * 此情况仅适用于此解码器的{@linkplain #onUnmappableCharacter unmappable action} {@link CodingErrorAction#REPORT};否则
     * 将根据请求忽略或替换不可映射的字符。
     * 解码不可映射字符的字节从输入缓冲区的(可能递增的)位置开始;可以通过调用结果对象的{@link CoderResult#length()length}方法来确定这样的字节的数量。
     *  </p> </li>。
     * 
     * </ul>
     * 
     *  在任何情况下,如果要在相同的解码操作中重新调用此方法,则应注意保留输入缓冲区中剩余的任何字节,以便它们可用于下一次调用。
     * 
     * <p> <tt> endOfInput </tt>参数建议此方法是否调用程序可以提供超出给定输入缓冲区中包含的输入的进一步输入。
     * 如果有可能提供额外的输入,那么调用者应该为此参数传递<tt> false </tt>;如果没有提供进一步输入的可能性,则调用者应该传递<tt> true </tt>。
     * 这不是错误的,事实上很常见,在一次调用中传递<tt> false </tt>,后来发现没有其他输入实际可用。
     * 然而,至关重要的是,在调用序列中最终调用此方法总是传递<tt> true </tt>,以便任何剩余的未解码输入将被视为格式错误。
     * 
     *  <p>此方法通过调用{@link #decodeLoop decodeLoop}方法,解释其结果,处理错误条件以及根据需要重新启动它来工作。 </p>
     * 
     * 
     * @param  in
     *         The input byte buffer
     *
     * @param  out
     *         The output character buffer
     *
     * @param  endOfInput
     *         <tt>true</tt> if, and only if, the invoker can provide no
     *         additional input bytes beyond those in the given buffer
     *
     * @return  A coder-result object describing the reason for termination
     *
     * @throws  IllegalStateException
     *          If a decoding operation is already in progress and the previous
     *          step was an invocation neither of the {@link #reset reset}
     *          method, nor of this method with a value of <tt>false</tt> for
     *          the <tt>endOfInput</tt> parameter, nor of this method with a
     *          value of <tt>true</tt> for the <tt>endOfInput</tt> parameter
     *          but a return value indicating an incomplete decoding operation
     *
     * @throws  CoderMalfunctionError
     *          If an invocation of the decodeLoop method threw
     *          an unexpected exception
     */
    public final CoderResult decode(ByteBuffer in, CharBuffer out,
                                    boolean endOfInput)
    {
        int newState = endOfInput ? ST_END : ST_CODING;
        if ((state != ST_RESET) && (state != ST_CODING)
            && !(endOfInput && (state == ST_END)))
            throwIllegalStateException(state, newState);
        state = newState;

        for (;;) {

            CoderResult cr;
            try {
                cr = decodeLoop(in, out);
            } catch (BufferUnderflowException x) {
                throw new CoderMalfunctionError(x);
            } catch (BufferOverflowException x) {
                throw new CoderMalfunctionError(x);
            }

            if (cr.isOverflow())
                return cr;

            if (cr.isUnderflow()) {
                if (endOfInput && in.hasRemaining()) {
                    cr = CoderResult.malformedForLength(in.remaining());
                    // Fall through to malformed-input case
                } else {
                    return cr;
                }
            }

            CodingErrorAction action = null;
            if (cr.isMalformed())
                action = malformedInputAction;
            else if (cr.isUnmappable())
                action = unmappableCharacterAction;
            else
                assert false : cr.toString();

            if (action == CodingErrorAction.REPORT)
                return cr;

            if (action == CodingErrorAction.REPLACE) {
                if (out.remaining() < replacement.length())
                    return CoderResult.OVERFLOW;
                out.put(replacement);
            }

            if ((action == CodingErrorAction.IGNORE)
                || (action == CodingErrorAction.REPLACE)) {
                // Skip erroneous input either way
                in.position(in.position() + cr.length());
                continue;
            }

            assert false;
        }

    }

    /**
     * Flushes this decoder.
     *
     * <p> Some decoders maintain internal state and may need to write some
     * final characters to the output buffer once the overall input sequence has
     * been read.
     *
     * <p> Any additional output is written to the output buffer beginning at
     * its current position.  At most {@link Buffer#remaining out.remaining()}
     * characters will be written.  The buffer's position will be advanced
     * appropriately, but its mark and limit will not be modified.
     *
     * <p> If this method completes successfully then it returns {@link
     * CoderResult#UNDERFLOW}.  If there is insufficient room in the output
     * buffer then it returns {@link CoderResult#OVERFLOW}.  If this happens
     * then this method must be invoked again, with an output buffer that has
     * more room, in order to complete the current <a href="#steps">decoding
     * operation</a>.
     *
     * <p> If this decoder has already been flushed then invoking this method
     * has no effect.
     *
     * <p> This method invokes the {@link #implFlush implFlush} method to
     * perform the actual flushing operation.  </p>
     *
     * <p>
     *  刷新此解码器。
     * 
     *  <p>一些解码器保持内部状态,一旦读取了整个输入序列,可能需要将一些最后字符写入输出缓冲器。
     * 
     *  <p>任何附加输出将从当前位置开始写入输出缓冲区。最多会写入{@link Buffer#remaining out.remaining()}个字符。
     * 缓冲区的位置将适当地前进,但其标记和限​​制不会被修改。
     * 
     * <p>如果此方法成功完成,则会返回{@link CoderResult#UNDERFLOW}。如果输出缓冲区中没有足够的空间,则返回{@link CoderResult#OVERFLOW}。
     * 如果发生这种情况,则必须再次调用此方法,并使用具有更多空间的输出缓冲区,以完成当前的<a href="#steps">解码操作</a>。
     * 
     *  <p>如果此解码器已经刷新,则调用此方法不起作用。
     * 
     *  <p>此方法调用{@link #implFlush implFlush}方法来执行实际的刷新操作。 </p>
     * 
     * 
     * @param  out
     *         The output character buffer
     *
     * @return  A coder-result object, either {@link CoderResult#UNDERFLOW} or
     *          {@link CoderResult#OVERFLOW}
     *
     * @throws  IllegalStateException
     *          If the previous step of the current decoding operation was an
     *          invocation neither of the {@link #flush flush} method nor of
     *          the three-argument {@link
     *          #decode(ByteBuffer,CharBuffer,boolean) decode} method
     *          with a value of <tt>true</tt> for the <tt>endOfInput</tt>
     *          parameter
     */
    public final CoderResult flush(CharBuffer out) {
        if (state == ST_END) {
            CoderResult cr = implFlush(out);
            if (cr.isUnderflow())
                state = ST_FLUSHED;
            return cr;
        }

        if (state != ST_FLUSHED)
            throwIllegalStateException(state, ST_FLUSHED);

        return CoderResult.UNDERFLOW; // Already flushed
    }

    /**
     * Flushes this decoder.
     *
     * <p> The default implementation of this method does nothing, and always
     * returns {@link CoderResult#UNDERFLOW}.  This method should be overridden
     * by decoders that may need to write final characters to the output buffer
     * once the entire input sequence has been read. </p>
     *
     * <p>
     *  刷新此解码器。
     * 
     *  <p>此方法的默认实现不执行任何操作,并始终返回{@link CoderResult#UNDERFLOW}。该方法应当被解码器覆盖,一旦读取了整个输入序列,解码器可能需要将最终字符写入输出缓冲器。
     *  </p>。
     * 
     * 
     * @param  out
     *         The output character buffer
     *
     * @return  A coder-result object, either {@link CoderResult#UNDERFLOW} or
     *          {@link CoderResult#OVERFLOW}
     */
    protected CoderResult implFlush(CharBuffer out) {
        return CoderResult.UNDERFLOW;
    }

    /**
     * Resets this decoder, clearing any internal state.
     *
     * <p> This method resets charset-independent state and also invokes the
     * {@link #implReset() implReset} method in order to perform any
     * charset-specific reset actions.  </p>
     *
     * <p>
     *  复位此解码器,清除任何内部状态。
     * 
     *  <p>此方法重置与字符集无关的状态,并调用{@link #implReset()implReset}方法,以执行任何字符集特定的重置操作。 </p>
     * 
     * 
     * @return  This decoder
     *
     */
    public final CharsetDecoder reset() {
        implReset();
        state = ST_RESET;
        return this;
    }

    /**
     * Resets this decoder, clearing any charset-specific internal state.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by decoders that maintain internal state.  </p>
     * <p>
     *  复位此解码器,清除任何字符集特定的内部状态。
     * 
     *  <p>此方法的默认实现不执行任何操作。这种方法应该被保持内部状态的解码器覆盖。 </p>
     * 
     */
    protected void implReset() { }

    /**
     * Decodes one or more bytes into one or more characters.
     *
     * <p> This method encapsulates the basic decoding loop, decoding as many
     * bytes as possible until it either runs out of input, runs out of room
     * in the output buffer, or encounters a decoding error.  This method is
     * invoked by the {@link #decode decode} method, which handles result
     * interpretation and error recovery.
     *
     * <p> The buffers are read from, and written to, starting at their current
     * positions.  At most {@link Buffer#remaining in.remaining()} bytes
     * will be read, and at most {@link Buffer#remaining out.remaining()}
     * characters will be written.  The buffers' positions will be advanced to
     * reflect the bytes read and the characters written, but their marks and
     * limits will not be modified.
     *
     * <p> This method returns a {@link CoderResult} object to describe its
     * reason for termination, in the same manner as the {@link #decode decode}
     * method.  Most implementations of this method will handle decoding errors
     * by returning an appropriate result object for interpretation by the
     * {@link #decode decode} method.  An optimized implementation may instead
     * examine the relevant error action and implement that action itself.
     *
     * <p> An implementation of this method may perform arbitrary lookahead by
     * returning {@link CoderResult#UNDERFLOW} until it receives sufficient
     * input.  </p>
     *
     * <p>
     *  将一个或多个字节解码为一个或多个字符。
     * 
     * <p>此方法封装基本解码循环,解码尽可能多的字节,直到它用完输出,在输出缓冲区中用完空间或遇到解码错误。此方法由{@link #decode decode}方法调用,该方法处理结果解释和错误恢复。
     * 
     *  <p>缓冲区从其当前位置开始读取和写入。
     * 最多只能读取{@link Buffer#remaining in.remaining()}字节,最多只能写入{@link Buffer#remaining out.remaining()}字符。
     * 缓冲区的位置将被提前以反映读取的字节和写入的字符,但不会修改它们的标记和限制。
     * 
     *  <p>此方法以与{@link #decode decode}方法相同的方式返回{@link CoderResult}对象,以描述其终止的原因。
     * 该方法的大多数实现将通过返回适当的结果对象以通过{@link #decode decode}方法解释来处理解码错误。优化的实现可以替代地检查相关的错误动作并且实现该动作本身。
     * 
     *  <p>此方法的实现可以通过返回{@link CoderResult#UNDERFLOW}来执行任意先行,直到它接收到足够的输入。 </p>
     * 
     * 
     * @param  in
     *         The input byte buffer
     *
     * @param  out
     *         The output character buffer
     *
     * @return  A coder-result object describing the reason for termination
     */
    protected abstract CoderResult decodeLoop(ByteBuffer in,
                                              CharBuffer out);

    /**
     * Convenience method that decodes the remaining content of a single input
     * byte buffer into a newly-allocated character buffer.
     *
     * <p> This method implements an entire <a href="#steps">decoding
     * operation</a>; that is, it resets this decoder, then it decodes the
     * bytes in the given byte buffer, and finally it flushes this
     * decoder.  This method should therefore not be invoked if a decoding
     * operation is already in progress.  </p>
     *
     * <p>
     *  便利方法将单个输入字节缓冲区的剩余内容解码为新分配的字符缓冲区。
     * 
     * <p>此方法实施整个<a href="#steps">解码操作</a>;即它重置该解码器,然后解码给定字节缓冲器中的字节,最后刷新该解码器。因此,如果解码操作已经在进行中,则不应该调用该方法。
     *  </p>。
     * 
     * 
     * @param  in
     *         The input byte buffer
     *
     * @return A newly-allocated character buffer containing the result of the
     *         decoding operation.  The buffer's position will be zero and its
     *         limit will follow the last character written.
     *
     * @throws  IllegalStateException
     *          If a decoding operation is already in progress
     *
     * @throws  MalformedInputException
     *          If the byte sequence starting at the input buffer's current
     *          position is not legal for this charset and the current malformed-input action
     *          is {@link CodingErrorAction#REPORT}
     *
     * @throws  UnmappableCharacterException
     *          If the byte sequence starting at the input buffer's current
     *          position cannot be mapped to an equivalent character sequence and
     *          the current unmappable-character action is {@link
     *          CodingErrorAction#REPORT}
     */
    public final CharBuffer decode(ByteBuffer in)
        throws CharacterCodingException
    {
        int n = (int)(in.remaining() * averageCharsPerByte());
        CharBuffer out = CharBuffer.allocate(n);

        if ((n == 0) && (in.remaining() == 0))
            return out;
        reset();
        for (;;) {
            CoderResult cr = in.hasRemaining() ?
                decode(in, out, true) : CoderResult.UNDERFLOW;
            if (cr.isUnderflow())
                cr = flush(out);

            if (cr.isUnderflow())
                break;
            if (cr.isOverflow()) {
                n = 2*n + 1;    // Ensure progress; n might be 0!
                CharBuffer o = CharBuffer.allocate(n);
                out.flip();
                o.put(out);
                out = o;
                continue;
            }
            cr.throwException();
        }
        out.flip();
        return out;
    }



    /**
     * Tells whether or not this decoder implements an auto-detecting charset.
     *
     * <p> The default implementation of this method always returns
     * <tt>false</tt>; it should be overridden by auto-detecting decoders to
     * return <tt>true</tt>.  </p>
     *
     * <p>
     *  告诉解码器是否实现自动检测字符集。
     * 
     *  <p>此方法的默认实现始终返回<tt> false </tt>;它应该被自动检测解码器覆盖以返回<tt> true </tt>。 </p>
     * 
     * 
     * @return  <tt>true</tt> if, and only if, this decoder implements an
     *          auto-detecting charset
     */
    public boolean isAutoDetecting() {
        return false;
    }

    /**
     * Tells whether or not this decoder has yet detected a
     * charset&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> If this decoder implements an auto-detecting charset then at a
     * single point during a decoding operation this method may start returning
     * <tt>true</tt> to indicate that a specific charset has been detected in
     * the input byte sequence.  Once this occurs, the {@link #detectedCharset
     * detectedCharset} method may be invoked to retrieve the detected charset.
     *
     * <p> That this method returns <tt>false</tt> does not imply that no bytes
     * have yet been decoded.  Some auto-detecting decoders are capable of
     * decoding some, or even all, of an input byte sequence without fixing on
     * a particular charset.
     *
     * <p> The default implementation of this method always throws an {@link
     * UnsupportedOperationException}; it should be overridden by
     * auto-detecting decoders to return <tt>true</tt> once the input charset
     * has been determined.  </p>
     *
     * <p>
     *  判断此解码器是否已检测到字符集</i>(可选操作)</i>。
     * 
     *  <p>如果此解码器在解码操作期间在单个点处实现自动检测字符集,则此方法可开始返回<tt> true </tt>以指示在输入字节序列中检测到特定字符集。
     * 发生这种情况后,可以调用{@link #detectedCharset detectedCharset}方法来检索检测到的字符集。
     * 
     *  <p>此方法返回<tt> false </tt>并不表示尚未解码任何字节。一些自动检测解码器能够解码一些或甚至全部输入字节序列,而不固定在特定字符集上。
     * 
     *  <p>此方法的默认实现始终会抛出{@link UnsupportedOperationException};一旦输入字符集被确定,自动检测解码器应该覆盖它以返回<tt> true </tt>。
     *  </p>。
     * 
     * @return  <tt>true</tt> if, and only if, this decoder has detected a
     *          specific charset
     *
     * @throws  UnsupportedOperationException
     *          If this decoder does not implement an auto-detecting charset
     */
    public boolean isCharsetDetected() {
        throw new UnsupportedOperationException();
    }

    /**
     * Retrieves the charset that was detected by this
     * decoder&nbsp;&nbsp;<i>(optional operation)</i>.
     *
     * <p> If this decoder implements an auto-detecting charset then this
     * method returns the actual charset once it has been detected.  After that
     * point, this method returns the same value for the duration of the
     * current decoding operation.  If not enough input bytes have yet been
     * read to determine the actual charset then this method throws an {@link
     * IllegalStateException}.
     *
     * <p> The default implementation of this method always throws an {@link
     * UnsupportedOperationException}; it should be overridden by
     * auto-detecting decoders to return the appropriate value.  </p>
     *
     * <p>
     * 
     * 
     * @return  The charset detected by this auto-detecting decoder,
     *          or <tt>null</tt> if the charset has not yet been determined
     *
     * @throws  IllegalStateException
     *          If insufficient bytes have been read to determine a charset
     *
     * @throws  UnsupportedOperationException
     *          If this decoder does not implement an auto-detecting charset
     */
    public Charset detectedCharset() {
        throw new UnsupportedOperationException();
    }
































































































    private void throwIllegalStateException(int from, int to) {
        throw new IllegalStateException("Current state = " + stateNames[from]
                                        + ", new state = " + stateNames[to]);
    }

}
