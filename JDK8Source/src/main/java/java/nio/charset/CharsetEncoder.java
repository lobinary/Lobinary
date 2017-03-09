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
 * An engine that can transform a sequence of sixteen-bit Unicode characters into a sequence of
 * bytes in a specific charset.
 *
 * <a name="steps"></a>
 *
 * <p> The input character sequence is provided in a character buffer or a series
 * of such buffers.  The output byte sequence is written to a byte buffer
 * or a series of such buffers.  An encoder should always be used by making
 * the following sequence of method invocations, hereinafter referred to as an
 * <i>encoding operation</i>:
 *
 * <ol>
 *
 *   <li><p> Reset the encoder via the {@link #reset reset} method, unless it
 *   has not been used before; </p></li>
 *
 *   <li><p> Invoke the {@link #encode encode} method zero or more times, as
 *   long as additional input may be available, passing <tt>false</tt> for the
 *   <tt>endOfInput</tt> argument and filling the input buffer and flushing the
 *   output buffer between invocations; </p></li>
 *
 *   <li><p> Invoke the {@link #encode encode} method one final time, passing
 *   <tt>true</tt> for the <tt>endOfInput</tt> argument; and then </p></li>
 *
 *   <li><p> Invoke the {@link #flush flush} method so that the encoder can
 *   flush any internal state to the output buffer. </p></li>
 *
 * </ol>
 *
 * Each invocation of the {@link #encode encode} method will encode as many
 * characters as possible from the input buffer, writing the resulting bytes
 * to the output buffer.  The {@link #encode encode} method returns when more
 * input is required, when there is not enough room in the output buffer, or
 * when an encoding error has occurred.  In each case a {@link CoderResult}
 * object is returned to describe the reason for termination.  An invoker can
 * examine this object and fill the input buffer, flush the output buffer, or
 * attempt to recover from an encoding error, as appropriate, and try again.
 *
 * <a name="ce"></a>
 *
 * <p> There are two general types of encoding errors.  If the input character
 * sequence is not a legal sixteen-bit Unicode sequence then the input is considered <i>malformed</i>.  If
 * the input character sequence is legal but cannot be mapped to a valid
 * byte sequence in the given charset then an <i>unmappable character</i> has been encountered.
 *
 * <a name="cae"></a>
 *
 * <p> How an encoding error is handled depends upon the action requested for
 * that type of error, which is described by an instance of the {@link
 * CodingErrorAction} class.  The possible error actions are to {@linkplain
 * CodingErrorAction#IGNORE ignore} the erroneous input, {@linkplain
 * CodingErrorAction#REPORT report} the error to the invoker via
 * the returned {@link CoderResult} object, or {@linkplain CodingErrorAction#REPLACE
 * replace} the erroneous input with the current value of the
 * replacement byte array.  The replacement
 *

 * is initially set to the encoder's default replacement, which often
 * (but not always) has the initial value&nbsp;<tt>{</tt>&nbsp;<tt>(byte)'?'</tt>&nbsp;<tt>}</tt>;




 *
 * its value may be changed via the {@link #replaceWith(byte[])
 * replaceWith} method.
 *
 * <p> The default action for malformed-input and unmappable-character errors
 * is to {@linkplain CodingErrorAction#REPORT report} them.  The
 * malformed-input error action may be changed via the {@link
 * #onMalformedInput(CodingErrorAction) onMalformedInput} method; the
 * unmappable-character action may be changed via the {@link
 * #onUnmappableCharacter(CodingErrorAction) onUnmappableCharacter} method.
 *
 * <p> This class is designed to handle many of the details of the encoding
 * process, including the implementation of error actions.  An encoder for a
 * specific charset, which is a concrete subclass of this class, need only
 * implement the abstract {@link #encodeLoop encodeLoop} method, which
 * encapsulates the basic encoding loop.  A subclass that maintains internal
 * state should, additionally, override the {@link #implFlush implFlush} and
 * {@link #implReset implReset} methods.
 *
 * <p> Instances of this class are not safe for use by multiple concurrent
 * threads.  </p>
 *
 *
 * <p>
 *  一种可以将十六位Unicode字符序列转换为特定字符集中的字节序列的引擎。
 * 
 *  <a name="steps"> </a>
 * 
 *  <p>输入字符序列在字符缓冲器或一系列这样的缓冲器中提供。输出字节序列被写入字节缓冲器或一系列这样的缓冲器。通过进行以下的方法调用序列(下文中称为编码操作)来始终使用编码器：</i>：
 * 
 * <ol>
 * 
 *  <li> <p>通过{@link #reset reset}方法重置编码器,除非之前没有使用; </p> </li>
 * 
 *  <li> <p>调用{@link #encode encode}方法零次或多次,只要可能有其他输入可用,对<tt> endOfInput </tt>参数传递<tt> false </tt>以及填充所述
 * 输入缓冲器并在调用之间刷新所述输出缓冲器; </p> </li>。
 * 
 *  <li> <p>最后一次调用{@link #encode encode}方法,对<tt> endOfInput </tt>参数传递<tt> true </tt>然后</p> </li>
 * 
 *  <li> <p>调用{@link #flush flush}方法,以便编码器可以将任何内部状态刷新到输出缓冲区。 </p> </li>
 * 
 * </ol>
 * 
 * 每次调用{@link #encode encode}方法将从输入缓冲区尽可能多地编码字符,将产生的字节写入输出缓冲区。
 * 当需要更多输入时,输出缓冲区中没有足够空间或发生编码错误时,{@link #encode encode}方法返回。在每种情况下,返回一个{@link CoderResult}对象来描述终止的原因。
 * 调用者可以检查此对象并填充输入缓冲区,刷新输出缓冲区或尝试从编码错误中恢复(如果适用),然后重试。
 * 
 *  <a name="ce"> </a>
 * 
 *  <p>编码错误有两种常见类型。如果输入字符序列不是合法的16位Unicode序列,则认为输入是错误的</i>。
 * 如果输入字符序列是合法的,但不能映射到给定字符集中的有效字节序列,则遇到不可映射字符</i>。
 * 
 *  <a name="cae"> </a>
 * 
 * <p>如何处理编码错误取决于请求的该类型错误的操作,这由{@link CodingErrorAction}类的实例描述。
 * 可能的错误操作是通过返回的{@link CoderResult}对象或{@linkplain CodingErrorAction#REPLACE replace}将错误的{@linkplain CodingErrorAction#IGNORE ignore}
 * 错误输入{@linkplain CodingErrorAction#REPORT report}错误输入与替换字节数组的当前值。
 * <p>如何处理编码错误取决于请求的该类型错误的操作,这由{@link CodingErrorAction}类的实例描述。更换。
 * 
 *  最初设置为编码器的默认替换,这通常(但不总是)具有初始值<tt> {</tt>&nbsp; <tt>(byte)'?'</tt>&nbsp; <tt>} </tt>;
 * 
 *  它的值可以通过{@link #replaceWith(byte [])replaceWith}方法更改。
 * 
 *  <p>格式错误输入和不可映射字符错误的默认操作是{@linkplain CodingErrorAction#REPORT report}。
 * 格式错误的输入错误操作可以通过{@link #onMalformedInput(CodingErrorAction)onMalformedInput}方法更改;可以通过{@link #onUnmappableCharacter(CodingErrorAction)onUnmappableCharacter}
 * 方法更改不可映射字符操作。
 *  <p>格式错误输入和不可映射字符错误的默认操作是{@linkplain CodingErrorAction#REPORT report}。
 * 
 * <p>此类设计用于处理编码过程的许多细节,包括执行错误操作。
 * 用于特定字符集的编码器,其是该类的具体子类,仅需要实现抽象{@link #encodeLoop encodeLoop}方法,该方法封装基本编码循环。
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
 * @see CharsetDecoder
 */

public abstract class CharsetEncoder {

    private final Charset charset;
    private final float averageBytesPerChar;
    private final float maxBytesPerChar;

    private byte[] replacement;
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
     * Initializes a new encoder.  The new encoder will have the given
     * bytes-per-char and replacement values.
     *
     * <p>
     *  初始化新编码器。新编码器将具有给定的字节每字符和替换值。
     * 
     * 
     * @param  cs
     *         The charset that created this encoder
     *
     * @param  averageBytesPerChar
     *         A positive float value indicating the expected number of
     *         bytes that will be produced for each input character
     *
     * @param  maxBytesPerChar
     *         A positive float value indicating the maximum number of
     *         bytes that will be produced for each input character
     *
     * @param  replacement
     *         The initial replacement; must not be <tt>null</tt>, must have
     *         non-zero length, must not be longer than maxBytesPerChar,
     *         and must be {@linkplain #isLegalReplacement legal}
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     */
    protected
    CharsetEncoder(Charset cs,
                   float averageBytesPerChar,
                   float maxBytesPerChar,
                   byte[] replacement)
    {
        this.charset = cs;
        if (averageBytesPerChar <= 0.0f)
            throw new IllegalArgumentException("Non-positive "
                                               + "averageBytesPerChar");
        if (maxBytesPerChar <= 0.0f)
            throw new IllegalArgumentException("Non-positive "
                                               + "maxBytesPerChar");
        if (!Charset.atBugLevel("1.4")) {
            if (averageBytesPerChar > maxBytesPerChar)
                throw new IllegalArgumentException("averageBytesPerChar"
                                                   + " exceeds "
                                                   + "maxBytesPerChar");
        }
        this.replacement = replacement;
        this.averageBytesPerChar = averageBytesPerChar;
        this.maxBytesPerChar = maxBytesPerChar;
        replaceWith(replacement);
    }

    /**
     * Initializes a new encoder.  The new encoder will have the given
     * bytes-per-char values and its replacement will be the
     * byte array <tt>{</tt>&nbsp;<tt>(byte)'?'</tt>&nbsp;<tt>}</tt>.
     *
     * <p>
     *  初始化新编码器。新编码器将具有给定的每字节字节值,并且其替换将是字节数组<tt> {</tt>&nbsp; <tt>(byte)'?'</tt>&nbsp; <tt>} </tt>。
     * 
     * 
     * @param  cs
     *         The charset that created this encoder
     *
     * @param  averageBytesPerChar
     *         A positive float value indicating the expected number of
     *         bytes that will be produced for each input character
     *
     * @param  maxBytesPerChar
     *         A positive float value indicating the maximum number of
     *         bytes that will be produced for each input character
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameters do not hold
     */
    protected CharsetEncoder(Charset cs,
                             float averageBytesPerChar,
                             float maxBytesPerChar)
    {
        this(cs,
             averageBytesPerChar, maxBytesPerChar,
             new byte[] { (byte)'?' });
    }

    /**
     * Returns the charset that created this encoder.
     *
     * <p>
     *  返回创建此编码器的字符集。
     * 
     * 
     * @return  This encoder's charset
     */
    public final Charset charset() {
        return charset;
    }

    /**
     * Returns this encoder's replacement value.
     *
     * <p>
     *  返回此编码器的替换值。
     * 
     * 
     * @return  This encoder's current replacement,
     *          which is never <tt>null</tt> and is never empty
     */
    public final byte[] replacement() {




        return Arrays.copyOf(replacement, replacement.length);

    }

    /**
     * Changes this encoder's replacement value.
     *
     * <p> This method invokes the {@link #implReplaceWith implReplaceWith}
     * method, passing the new replacement, after checking that the new
     * replacement is acceptable.  </p>
     *
     * <p>
     *  更改此编码器的替换值。
     * 
     *  <p>此方法调用{@link #implReplaceWith implReplaceWith}方法,在检查新替换是否可接受后,传递新替换。 </p>
     * 
     * 
     * @param  newReplacement  The replacement value
     *





     *         The new replacement; must not be <tt>null</tt>, must have
     *         non-zero length, must not be longer than the value returned by
     *         the {@link #maxBytesPerChar() maxBytesPerChar} method, and
     *         must be {@link #isLegalReplacement legal}

     *
     * @return  This encoder
     *
     * @throws  IllegalArgumentException
     *          If the preconditions on the parameter do not hold
     */
    public final CharsetEncoder replaceWith(byte[] newReplacement) {
        if (newReplacement == null)
            throw new IllegalArgumentException("Null replacement");
        int len = newReplacement.length;
        if (len == 0)
            throw new IllegalArgumentException("Empty replacement");
        if (len > maxBytesPerChar)
            throw new IllegalArgumentException("Replacement too long");




        if (!isLegalReplacement(newReplacement))
            throw new IllegalArgumentException("Illegal replacement");
        this.replacement = Arrays.copyOf(newReplacement, newReplacement.length);

        implReplaceWith(this.replacement);
        return this;
    }

    /**
     * Reports a change to this encoder's replacement value.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by encoders that require notification of changes to
     * the replacement.  </p>
     *
     * <p>
     *  报告对此编码器更换值的更改。
     * 
     *  <p>此方法的默认实现不执行任何操作。该方法应当由需要通知更换更换的编码器覆盖。 </p>
     * 
     * 
     * @param  newReplacement    The replacement value
     */
    protected void implReplaceWith(byte[] newReplacement) {
    }



    private WeakReference<CharsetDecoder> cachedDecoder = null;

    /**
     * Tells whether or not the given byte array is a legal replacement value
     * for this encoder.
     *
     * <p> A replacement is legal if, and only if, it is a legal sequence of
     * bytes in this encoder's charset; that is, it must be possible to decode
     * the replacement into one or more sixteen-bit Unicode characters.
     *
     * <p> The default implementation of this method is not very efficient; it
     * should generally be overridden to improve performance.  </p>
     *
     * <p>
     *  告诉给定的字节数组是否是这个编码器的合法替换值。
     * 
     * <p>当且仅当这是编码器字符集中的字节的法定序列时,替换是合法的;也就是说,必须能够将替换解码为一个或多个十六位Unicode字符。
     * 
     *  <p>此方法的默认实现效率不高;它通常应该被覆盖以提高性能。 </p>
     * 
     * 
     * @param  repl  The byte array to be tested
     *
     * @return  <tt>true</tt> if, and only if, the given byte array
     *          is a legal replacement value for this encoder
     */
    public boolean isLegalReplacement(byte[] repl) {
        WeakReference<CharsetDecoder> wr = cachedDecoder;
        CharsetDecoder dec = null;
        if ((wr == null) || ((dec = wr.get()) == null)) {
            dec = charset().newDecoder();
            dec.onMalformedInput(CodingErrorAction.REPORT);
            dec.onUnmappableCharacter(CodingErrorAction.REPORT);
            cachedDecoder = new WeakReference<CharsetDecoder>(dec);
        } else {
            dec.reset();
        }
        ByteBuffer bb = ByteBuffer.wrap(repl);
        CharBuffer cb = CharBuffer.allocate((int)(bb.remaining()
                                                  * dec.maxCharsPerByte()));
        CoderResult cr = dec.decode(bb, cb, true);
        return !cr.isError();
    }



    /**
     * Returns this encoder's current action for malformed-input errors.
     *
     * <p>
     *  返回此编码器对错误输入错误的当前操作。
     * 
     * 
     * @return The current malformed-input action, which is never <tt>null</tt>
     */
    public CodingErrorAction malformedInputAction() {
        return malformedInputAction;
    }

    /**
     * Changes this encoder's action for malformed-input errors.
     *
     * <p> This method invokes the {@link #implOnMalformedInput
     * implOnMalformedInput} method, passing the new action.  </p>
     *
     * <p>
     *  更改此编码器对错误输入错误的操作。
     * 
     *  <p>此方法调用{@link #implOnMalformedInput implOnMalformedInput}方法,传递新操作。 </p>
     * 
     * 
     * @param  newAction  The new action; must not be <tt>null</tt>
     *
     * @return  This encoder
     *
     * @throws IllegalArgumentException
     *         If the precondition on the parameter does not hold
     */
    public final CharsetEncoder onMalformedInput(CodingErrorAction newAction) {
        if (newAction == null)
            throw new IllegalArgumentException("Null action");
        malformedInputAction = newAction;
        implOnMalformedInput(newAction);
        return this;
    }

    /**
     * Reports a change to this encoder's malformed-input action.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by encoders that require notification of changes to
     * the malformed-input action.  </p>
     *
     * <p>
     *  报告对此编码器格式不正确的输入操作的更改。
     * 
     *  <p>此方法的默认实现不执行任何操作。这种方法应该被需要通知变形输入动作的变化的编码器所覆盖。 </p>
     * 
     * 
     * @param  newAction  The new action
     */
    protected void implOnMalformedInput(CodingErrorAction newAction) { }

    /**
     * Returns this encoder's current action for unmappable-character errors.
     *
     * <p>
     *  返回此编码器的当前动作的不可映射字符错误。
     * 
     * 
     * @return The current unmappable-character action, which is never
     *         <tt>null</tt>
     */
    public CodingErrorAction unmappableCharacterAction() {
        return unmappableCharacterAction;
    }

    /**
     * Changes this encoder's action for unmappable-character errors.
     *
     * <p> This method invokes the {@link #implOnUnmappableCharacter
     * implOnUnmappableCharacter} method, passing the new action.  </p>
     *
     * <p>
     *  更改此编码器对不可映射字符错误的操作。
     * 
     *  <p>此方法调用{@link #implOnUnmappableCharacter implOnUnmappableCharacter}方法,传递新操作。 </p>
     * 
     * 
     * @param  newAction  The new action; must not be <tt>null</tt>
     *
     * @return  This encoder
     *
     * @throws IllegalArgumentException
     *         If the precondition on the parameter does not hold
     */
    public final CharsetEncoder onUnmappableCharacter(CodingErrorAction
                                                      newAction)
    {
        if (newAction == null)
            throw new IllegalArgumentException("Null action");
        unmappableCharacterAction = newAction;
        implOnUnmappableCharacter(newAction);
        return this;
    }

    /**
     * Reports a change to this encoder's unmappable-character action.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by encoders that require notification of changes to
     * the unmappable-character action.  </p>
     *
     * <p>
     *  报告对此编码器不可映射字符操作的更改。
     * 
     *  <p>此方法的默认实现不执行任何操作。该方法应该被需要通知不可映射字符操作的改变的编码器覆盖。 </p>
     * 
     * 
     * @param  newAction  The new action
     */
    protected void implOnUnmappableCharacter(CodingErrorAction newAction) { }

    /**
     * Returns the average number of bytes that will be produced for each
     * character of input.  This heuristic value may be used to estimate the size
     * of the output buffer required for a given input sequence.
     *
     * <p>
     * 返回将为每个输入字符生成的平均字节数。该启发式值可以用于估计给定输入序列所需的输出缓冲区的大小。
     * 
     * 
     * @return  The average number of bytes produced
     *          per character of input
     */
    public final float averageBytesPerChar() {
        return averageBytesPerChar;
    }

    /**
     * Returns the maximum number of bytes that will be produced for each
     * character of input.  This value may be used to compute the worst-case size
     * of the output buffer required for a given input sequence.
     *
     * <p>
     *  返回将为每个输入字符生成的最大字节数。该值可用于计算给定输入序列所需的输出缓冲区的最坏情况大小。
     * 
     * 
     * @return  The maximum number of bytes that will be produced per
     *          character of input
     */
    public final float maxBytesPerChar() {
        return maxBytesPerChar;
    }

    /**
     * Encodes as many characters as possible from the given input buffer,
     * writing the results to the given output buffer.
     *
     * <p> The buffers are read from, and written to, starting at their current
     * positions.  At most {@link Buffer#remaining in.remaining()} characters
     * will be read and at most {@link Buffer#remaining out.remaining()}
     * bytes will be written.  The buffers' positions will be advanced to
     * reflect the characters read and the bytes written, but their marks and
     * limits will not be modified.
     *
     * <p> In addition to reading characters from the input buffer and writing
     * bytes to the output buffer, this method returns a {@link CoderResult}
     * object to describe its reason for termination:
     *
     * <ul>
     *
     *   <li><p> {@link CoderResult#UNDERFLOW} indicates that as much of the
     *   input buffer as possible has been encoded.  If there is no further
     *   input then the invoker can proceed to the next step of the
     *   <a href="#steps">encoding operation</a>.  Otherwise this method
     *   should be invoked again with further input.  </p></li>
     *
     *   <li><p> {@link CoderResult#OVERFLOW} indicates that there is
     *   insufficient space in the output buffer to encode any more characters.
     *   This method should be invoked again with an output buffer that has
     *   more {@linkplain Buffer#remaining remaining} bytes. This is
     *   typically done by draining any encoded bytes from the output
     *   buffer.  </p></li>
     *
     *   <li><p> A {@linkplain CoderResult#malformedForLength
     *   malformed-input} result indicates that a malformed-input
     *   error has been detected.  The malformed characters begin at the input
     *   buffer's (possibly incremented) position; the number of malformed
     *   characters may be determined by invoking the result object's {@link
     *   CoderResult#length() length} method.  This case applies only if the
     *   {@linkplain #onMalformedInput malformed action} of this encoder
     *   is {@link CodingErrorAction#REPORT}; otherwise the malformed input
     *   will be ignored or replaced, as requested.  </p></li>
     *
     *   <li><p> An {@linkplain CoderResult#unmappableForLength
     *   unmappable-character} result indicates that an
     *   unmappable-character error has been detected.  The characters that
     *   encode the unmappable character begin at the input buffer's (possibly
     *   incremented) position; the number of such characters may be determined
     *   by invoking the result object's {@link CoderResult#length() length}
     *   method.  This case applies only if the {@linkplain #onUnmappableCharacter
     *   unmappable action} of this encoder is {@link
     *   CodingErrorAction#REPORT}; otherwise the unmappable character will be
     *   ignored or replaced, as requested.  </p></li>
     *
     * </ul>
     *
     * In any case, if this method is to be reinvoked in the same encoding
     * operation then care should be taken to preserve any characters remaining
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
     * pass <tt>true</tt> so that any remaining unencoded input will be treated
     * as being malformed.
     *
     * <p> This method works by invoking the {@link #encodeLoop encodeLoop}
     * method, interpreting its results, handling error conditions, and
     * reinvoking it as necessary.  </p>
     *
     *
     * <p>
     *  从给定的输入缓冲区编码尽可能多的字符,将结果写入给定的输出缓冲区。
     * 
     *  <p>缓冲区从其当前位置开始读取和写入。
     * 最多只能读取{@link Buffer#remaining in.remaining()}字符,最多只能写入{@link Buffer#remaining out.remaining()}字节。
     * 缓冲区的位置将被提前以反映读取的字符和写入的字节,但是它们的标记和限制不会被修改。
     * 
     *  <p>除了从输入缓冲区读取字符并将字节写入输出缓冲区之外,此方法还返回一个{@link CoderResult}对象以描述其终止的原因：
     * 
     * <ul>
     * 
     *  <li> <p> {@link CoderResult#UNDERFLOW}表示尽可能多的输入缓冲区已经编码。
     * 如果没有其他输入,则调用者可以继续进行<a href="#steps">编码操作</a>的下一步。否则,应该再次使用进一步的输入来调用此方法。 </p> </li>。
     * 
     * <li> <p> {@link CoderResult#OVERFLOW}表示输出缓冲区中没有足够的空间来编码任何其他字符。
     * 应该使用具有更多{@linkplain Buffer#remaining remaining}字节的输出缓冲区再次调用此方法。这通常通过从输出缓冲器中消耗任何编码字节来完成。 </p> </li>。
     * 
     *  <li> <p> {@linkplain CoderResult#malformedForLength格式错误的输入}结果表示检测到格式错误的输入错误。
     * 格式错误的字符从输入缓冲区(可能递增)的位置开始;可以通过调用结果对象的{@link CoderResult#length()length}方法来确定畸形字符的数量。
     * 此情况仅适用于此编码器的{@linkplain #onMalformedInput畸形操作}为{@link CodingErrorAction#REPORT};否则将根据请求忽略或替换格式错误的输入。
     *  </p> </li>。
     * 
     * <li> <p> {@linkplain CoderResult#unmappableForLength unmappable-character}结果表示检测到不可映射字符错误。
     * 编码不可映射字符的字符从输入缓冲区(可能递增)的位置开始;可以通过调用结果对象的{@link CoderResult#length()length}方法来确定这些字符的数量。
     * 此情况仅适用于此编码器的{@linkplain #onUnmappableCharacter unmappable action} {@link CodingErrorAction#REPORT};否则
     * 将根据请求忽略或替换不可映射的字符。
     * 编码不可映射字符的字符从输入缓冲区(可能递增)的位置开始;可以通过调用结果对象的{@link CoderResult#length()length}方法来确定这些字符的数量。 </p> </li>。
     * 
     * </ul>
     * 
     *  在任何情况下,如果此方法要在相同的编码操作中重新调用,则应注意保留输入缓冲区中剩余的任何字符,以便它们可用于下一次调用。
     * 
     * <p> <tt> endOfInput </tt>参数建议此方法是否调用程序可以提供超出给定输入缓冲区中包含的输入的进一步输入。
     * 如果有可能提供额外的输入,那么调用者应该为此参数传递<tt> false </tt>;如果没有提供进一步输入的可能性,则调用者应该传递<tt> true </tt>。
     * 这不是错误的,事实上很常见,在一次调用中传递<tt> false </tt>,后来发现没有其他输入实际可用。
     * 然而,至关重要的是,在调用序列中最终调用此方法总是传递<tt> true </tt>,以便任何剩余的未编码输入将被视为格式不正确。
     * 
     *  <p>此方法通过调用{@link #encodeLoop encodeLoop}方法,解释其结果,处理错误条件,以及根据需要重新启动它来工作。 </p>
     * 
     * 
     * @param  in
     *         The input character buffer
     *
     * @param  out
     *         The output byte buffer
     *
     * @param  endOfInput
     *         <tt>true</tt> if, and only if, the invoker can provide no
     *         additional input characters beyond those in the given buffer
     *
     * @return  A coder-result object describing the reason for termination
     *
     * @throws  IllegalStateException
     *          If an encoding operation is already in progress and the previous
     *          step was an invocation neither of the {@link #reset reset}
     *          method, nor of this method with a value of <tt>false</tt> for
     *          the <tt>endOfInput</tt> parameter, nor of this method with a
     *          value of <tt>true</tt> for the <tt>endOfInput</tt> parameter
     *          but a return value indicating an incomplete encoding operation
     *
     * @throws  CoderMalfunctionError
     *          If an invocation of the encodeLoop method threw
     *          an unexpected exception
     */
    public final CoderResult encode(CharBuffer in, ByteBuffer out,
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
                cr = encodeLoop(in, out);
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
                if (out.remaining() < replacement.length)
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
     * Flushes this encoder.
     *
     * <p> Some encoders maintain internal state and may need to write some
     * final bytes to the output buffer once the overall input sequence has
     * been read.
     *
     * <p> Any additional output is written to the output buffer beginning at
     * its current position.  At most {@link Buffer#remaining out.remaining()}
     * bytes will be written.  The buffer's position will be advanced
     * appropriately, but its mark and limit will not be modified.
     *
     * <p> If this method completes successfully then it returns {@link
     * CoderResult#UNDERFLOW}.  If there is insufficient room in the output
     * buffer then it returns {@link CoderResult#OVERFLOW}.  If this happens
     * then this method must be invoked again, with an output buffer that has
     * more room, in order to complete the current <a href="#steps">encoding
     * operation</a>.
     *
     * <p> If this encoder has already been flushed then invoking this method
     * has no effect.
     *
     * <p> This method invokes the {@link #implFlush implFlush} method to
     * perform the actual flushing operation.  </p>
     *
     * <p>
     *  冲洗此编码器。
     * 
     *  <p>一些编码器保持内部状态,并且一旦读取了整个输入序列,可能需要将一些最终字节写入输出缓冲器。
     * 
     *  <p>任何附加输出将从当前位置开始写入输出缓冲区。最多会写入{@link Buffer#remaining out.remaining()}字节。
     * 缓冲区的位置将适当地前进,但其标记和限​​制不会被修改。
     * 
     * <p>如果此方法成功完成,则会返回{@link CoderResult#UNDERFLOW}。如果输出缓冲区中没有足够的空间,则返回{@link CoderResult#OVERFLOW}。
     * 如果发生这种情况,则必须再次调用此方法,并使用具有更多空间的输出缓冲区,以完成当前的<a href="#steps">编码操作</a>。
     * 
     *  <p>如果此编码器已经刷新,则调用此方法无效。
     * 
     *  <p>此方法调用{@link #implFlush implFlush}方法来执行实际的刷新操作。 </p>
     * 
     * 
     * @param  out
     *         The output byte buffer
     *
     * @return  A coder-result object, either {@link CoderResult#UNDERFLOW} or
     *          {@link CoderResult#OVERFLOW}
     *
     * @throws  IllegalStateException
     *          If the previous step of the current encoding operation was an
     *          invocation neither of the {@link #flush flush} method nor of
     *          the three-argument {@link
     *          #encode(CharBuffer,ByteBuffer,boolean) encode} method
     *          with a value of <tt>true</tt> for the <tt>endOfInput</tt>
     *          parameter
     */
    public final CoderResult flush(ByteBuffer out) {
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
     * Flushes this encoder.
     *
     * <p> The default implementation of this method does nothing, and always
     * returns {@link CoderResult#UNDERFLOW}.  This method should be overridden
     * by encoders that may need to write final bytes to the output buffer
     * once the entire input sequence has been read. </p>
     *
     * <p>
     *  冲洗此编码器。
     * 
     *  <p>此方法的默认实现不执行任何操作,并始终返回{@link CoderResult#UNDERFLOW}。这种方法应该被编码器覆盖,编码器可能需要在读取整个输入序列后将最终字节写入输出缓冲器。
     *  </p>。
     * 
     * 
     * @param  out
     *         The output byte buffer
     *
     * @return  A coder-result object, either {@link CoderResult#UNDERFLOW} or
     *          {@link CoderResult#OVERFLOW}
     */
    protected CoderResult implFlush(ByteBuffer out) {
        return CoderResult.UNDERFLOW;
    }

    /**
     * Resets this encoder, clearing any internal state.
     *
     * <p> This method resets charset-independent state and also invokes the
     * {@link #implReset() implReset} method in order to perform any
     * charset-specific reset actions.  </p>
     *
     * <p>
     *  复位该编码器,清除任何内部状态。
     * 
     *  <p>此方法重置与字符集无关的状态,并调用{@link #implReset()implReset}方法,以执行任何字符集特定的重置操作。 </p>
     * 
     * 
     * @return  This encoder
     *
     */
    public final CharsetEncoder reset() {
        implReset();
        state = ST_RESET;
        return this;
    }

    /**
     * Resets this encoder, clearing any charset-specific internal state.
     *
     * <p> The default implementation of this method does nothing.  This method
     * should be overridden by encoders that maintain internal state.  </p>
     * <p>
     *  复位此编码器,清除任何字符集特定的内部状态。
     * 
     *  <p>此方法的默认实现不执行任何操作。这种方法应该被保持内部状态的编码器覆盖。 </p>
     * 
     */
    protected void implReset() { }

    /**
     * Encodes one or more characters into one or more bytes.
     *
     * <p> This method encapsulates the basic encoding loop, encoding as many
     * characters as possible until it either runs out of input, runs out of room
     * in the output buffer, or encounters an encoding error.  This method is
     * invoked by the {@link #encode encode} method, which handles result
     * interpretation and error recovery.
     *
     * <p> The buffers are read from, and written to, starting at their current
     * positions.  At most {@link Buffer#remaining in.remaining()} characters
     * will be read, and at most {@link Buffer#remaining out.remaining()}
     * bytes will be written.  The buffers' positions will be advanced to
     * reflect the characters read and the bytes written, but their marks and
     * limits will not be modified.
     *
     * <p> This method returns a {@link CoderResult} object to describe its
     * reason for termination, in the same manner as the {@link #encode encode}
     * method.  Most implementations of this method will handle encoding errors
     * by returning an appropriate result object for interpretation by the
     * {@link #encode encode} method.  An optimized implementation may instead
     * examine the relevant error action and implement that action itself.
     *
     * <p> An implementation of this method may perform arbitrary lookahead by
     * returning {@link CoderResult#UNDERFLOW} until it receives sufficient
     * input.  </p>
     *
     * <p>
     *  将一个或多个字符编码为一个或多个字节。
     * 
     * <p>此方法封装基本编码循环,对尽可能多的字符进行编码,直到输出用尽,输出缓冲区用尽,或遇到编码错误。此方法由{@link #encode encode}方法调用,该方法处理结果解释和错误恢复。
     * 
     *  <p>缓冲区从其当前位置开始读取和写入。
     * 最多只能读取{@link Buffer#remaining in.remaining()}字符,最多只能写入{@link Buffer#remaining out.remaining()}字节。
     * 缓冲区的位置将被提前以反映读取的字符和写入的字节,但是它们的标记和限制不会被修改。
     * 
     *  <p>此方法以与{@link #encode encode}方法相同的方式返回{@link CoderResult}对象以描述其终止的原因。
     * 该方法的大多数实现将通过返回适当的结果对象来处理编码错误,以便由{@link #encode encode}方法解释。优化的实现可以替代地检查相关的错误动作并且实现该动作本身。
     * 
     *  <p>此方法的实现可以通过返回{@link CoderResult#UNDERFLOW}来执行任意先行,直到它接收到足够的输入。 </p>
     * 
     * 
     * @param  in
     *         The input character buffer
     *
     * @param  out
     *         The output byte buffer
     *
     * @return  A coder-result object describing the reason for termination
     */
    protected abstract CoderResult encodeLoop(CharBuffer in,
                                              ByteBuffer out);

    /**
     * Convenience method that encodes the remaining content of a single input
     * character buffer into a newly-allocated byte buffer.
     *
     * <p> This method implements an entire <a href="#steps">encoding
     * operation</a>; that is, it resets this encoder, then it encodes the
     * characters in the given character buffer, and finally it flushes this
     * encoder.  This method should therefore not be invoked if an encoding
     * operation is already in progress.  </p>
     *
     * <p>
     *  便利方法将单个输入字符缓冲区的剩余内容编码为新分配的字节缓冲区。
     * 
     * <p>此方法实施整个<a href="#steps">编码操作</a>;也就是说,它复位这个编码器,然后对给定字符缓冲区中的字符进行编码,最后刷新该编码器。
     * 因此,如果编码操作已在进行,则不应调用此方法。 </p>。
     * 
     * 
     * @param  in
     *         The input character buffer
     *
     * @return A newly-allocated byte buffer containing the result of the
     *         encoding operation.  The buffer's position will be zero and its
     *         limit will follow the last byte written.
     *
     * @throws  IllegalStateException
     *          If an encoding operation is already in progress
     *
     * @throws  MalformedInputException
     *          If the character sequence starting at the input buffer's current
     *          position is not a legal sixteen-bit Unicode sequence and the current malformed-input action
     *          is {@link CodingErrorAction#REPORT}
     *
     * @throws  UnmappableCharacterException
     *          If the character sequence starting at the input buffer's current
     *          position cannot be mapped to an equivalent byte sequence and
     *          the current unmappable-character action is {@link
     *          CodingErrorAction#REPORT}
     */
    public final ByteBuffer encode(CharBuffer in)
        throws CharacterCodingException
    {
        int n = (int)(in.remaining() * averageBytesPerChar());
        ByteBuffer out = ByteBuffer.allocate(n);

        if ((n == 0) && (in.remaining() == 0))
            return out;
        reset();
        for (;;) {
            CoderResult cr = in.hasRemaining() ?
                encode(in, out, true) : CoderResult.UNDERFLOW;
            if (cr.isUnderflow())
                cr = flush(out);

            if (cr.isUnderflow())
                break;
            if (cr.isOverflow()) {
                n = 2*n + 1;    // Ensure progress; n might be 0!
                ByteBuffer o = ByteBuffer.allocate(n);
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















































































    private boolean canEncode(CharBuffer cb) {
        if (state == ST_FLUSHED)
            reset();
        else if (state != ST_RESET)
            throwIllegalStateException(state, ST_CODING);
        CodingErrorAction ma = malformedInputAction();
        CodingErrorAction ua = unmappableCharacterAction();
        try {
            onMalformedInput(CodingErrorAction.REPORT);
            onUnmappableCharacter(CodingErrorAction.REPORT);
            encode(cb);
        } catch (CharacterCodingException x) {
            return false;
        } finally {
            onMalformedInput(ma);
            onUnmappableCharacter(ua);
            reset();
        }
        return true;
    }

    /**
     * Tells whether or not this encoder can encode the given character.
     *
     * <p> This method returns <tt>false</tt> if the given character is a
     * surrogate character; such characters can be interpreted only when they
     * are members of a pair consisting of a high surrogate followed by a low
     * surrogate.  The {@link #canEncode(java.lang.CharSequence)
     * canEncode(CharSequence)} method may be used to test whether or not a
     * character sequence can be encoded.
     *
     * <p> This method may modify this encoder's state; it should therefore not
     * be invoked if an <a href="#steps">encoding operation</a> is already in
     * progress.
     *
     * <p> The default implementation of this method is not very efficient; it
     * should generally be overridden to improve performance.  </p>
     *
     * <p>
     *  告诉这个编码器是否可以编码给定的字符。
     * 
     *  <p>如果给定字符是替代字符,此方法返回<tt> false </tt>;这样的字符只有当它们是由高代理物和低代理物组成的对的成员时才可以被解释。
     *  {@link #canEncode(java.lang.CharSequence)canEncode(CharSequence)}方法可用于测试字符序列是否可以编码。
     * 
     *  <p>此方法可能会修改此编码器的状态;因此如果已执行<a href="#steps">编码操作</a>,则不应调用它。
     * 
     *  <p>此方法的默认实现效率不高;它通常应该被覆盖以提高性能。 </p>
     * 
     * 
     * @param   c
     *          The given character
     *
     * @return  <tt>true</tt> if, and only if, this encoder can encode
     *          the given character
     *
     * @throws  IllegalStateException
     *          If an encoding operation is already in progress
     */
    public boolean canEncode(char c) {
        CharBuffer cb = CharBuffer.allocate(1);
        cb.put(c);
        cb.flip();
        return canEncode(cb);
    }

    /**
     * Tells whether or not this encoder can encode the given character
     * sequence.
     *
     * <p> If this method returns <tt>false</tt> for a particular character
     * sequence then more information about why the sequence cannot be encoded
     * may be obtained by performing a full <a href="#steps">encoding
     * operation</a>.
     *
     * <p> This method may modify this encoder's state; it should therefore not
     * be invoked if an encoding operation is already in progress.
     *
     * <p> The default implementation of this method is not very efficient; it
     * should generally be overridden to improve performance.  </p>
     *
     * <p>
     * 
     * @param   cs
     *          The given character sequence
     *
     * @return  <tt>true</tt> if, and only if, this encoder can encode
     *          the given character without throwing any exceptions and without
     *          performing any replacements
     *
     * @throws  IllegalStateException
     *          If an encoding operation is already in progress
     */
    public boolean canEncode(CharSequence cs) {
        CharBuffer cb;
        if (cs instanceof CharBuffer)
            cb = ((CharBuffer)cs).duplicate();
        else
            cb = CharBuffer.wrap(cs.toString());
        return canEncode(cb);
    }




    private void throwIllegalStateException(int from, int to) {
        throw new IllegalStateException("Current state = " + stateNames[from]
                                        + ", new state = " + stateNames[to]);
    }

}
