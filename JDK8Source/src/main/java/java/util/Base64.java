/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * This class consists exclusively of static methods for obtaining
 * encoders and decoders for the Base64 encoding scheme. The
 * implementation of this class supports the following types of Base64
 * as specified in
 * <a href="http://www.ietf.org/rfc/rfc4648.txt">RFC 4648</a> and
 * <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>.
 *
 * <ul>
 * <li><a name="basic"><b>Basic</b></a>
 * <p> Uses "The Base64 Alphabet" as specified in Table 1 of
 *     RFC 4648 and RFC 2045 for encoding and decoding operation.
 *     The encoder does not add any line feed (line separator)
 *     character. The decoder rejects data that contains characters
 *     outside the base64 alphabet.</p></li>
 *
 * <li><a name="url"><b>URL and Filename safe</b></a>
 * <p> Uses the "URL and Filename safe Base64 Alphabet" as specified
 *     in Table 2 of RFC 4648 for encoding and decoding. The
 *     encoder does not add any line feed (line separator) character.
 *     The decoder rejects data that contains characters outside the
 *     base64 alphabet.</p></li>
 *
 * <li><a name="mime"><b>MIME</b></a>
 * <p> Uses the "The Base64 Alphabet" as specified in Table 1 of
 *     RFC 2045 for encoding and decoding operation. The encoded output
 *     must be represented in lines of no more than 76 characters each
 *     and uses a carriage return {@code '\r'} followed immediately by
 *     a linefeed {@code '\n'} as the line separator. No line separator
 *     is added to the end of the encoded output. All line separators
 *     or other characters not found in the base64 alphabet table are
 *     ignored in decoding operation.</p></li>
 * </ul>
 *
 * <p> Unless otherwise noted, passing a {@code null} argument to a
 * method of this class will cause a {@link java.lang.NullPointerException
 * NullPointerException} to be thrown.
 *
 * <p>
 *  该类仅由用于获得用于Base64编码方案的编码器和解码器的静态方法组成。
 * 此类的实现支持以下类型的Base64,如<a href="http://www.ietf.org/rfc/rfc4648.txt"> RFC 4648 </a>和<a href ="http： //www.ietf.org/rfc/rfc2045.txt">
 *  RFC 2045 </a>。
 *  该类仅由用于获得用于Base64编码方案的编码器和解码器的静态方法组成。
 * 
 * <ul>
 *  <li> <a name="basic"> <b>基本</b> </a> <p>使用RFC 4648和RFC 2045的表1中规定的用于编码和解码操作的"Base64字母表"。
 * 编码器不添加任何换行(行分隔符)字符。解码器拒绝包含base64字母外的字符的数据。</p> </li>。
 * 
 *  <li> <a name="url"> <b>网址和文件名安全</b> </a> <p>使用RFC 4648表2中指定的"网址和文件名安全Base64字母表"进行编码和解码。
 * 编码器不添加任何换行(行分隔符)字符。解码器拒绝包含base64字母外的字符的数据。</p> </li>。
 * 
 * <li> <a name="mime"> <b> MIME </b> </a> <p>使用RFC 2045表1中指定的"Base64字母表"进行编码和解码操作。
 * 编码输出必须以不超过76个字符的行表示,并使用回车符{@code'\ r'},然后紧跟换行符{@code'\ n'}作为行分隔符。没有行分隔符添加到编码输出的末尾。
 * 所有行分隔符或base64字母表中未找到的其他字符在解码操作中将被忽略。</p> </li>。
 * </ul>
 * 
 *  <p>除非另有说明,否则将{@code null}参数传递给此类的方法将导致抛出{@link java.lang.NullPointerException NullPointerException}。
 * 
 * 
 * @author  Xueming Shen
 * @since   1.8
 */

public class Base64 {

    private Base64() {}

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#basic">Basic</a> type base64 encoding scheme.
     *
     * <p>
     *  返回使用<a href="#basic">基本</a>类型base64编码方案进行编码的{@link Encoder}。
     * 
     * 
     * @return  A Base64 encoder.
     */
    public static Encoder getEncoder() {
         return Encoder.RFC4648;
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#url">URL and Filename safe</a> type base64
     * encoding scheme.
     *
     * <p>
     *  返回使用<a href="#url">网址和文件名安全</a>类型base64编码方案进行编码的{@link Encoder}。
     * 
     * 
     * @return  A Base64 encoder.
     */
    public static Encoder getUrlEncoder() {
         return Encoder.RFC4648_URLSAFE;
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#mime">MIME</a> type base64 encoding scheme.
     *
     * <p>
     *  返回使用<a href="#mime"> MIME </a>类型base64编码方案进行编码的{@link Encoder}。
     * 
     * 
     * @return  A Base64 encoder.
     */
    public static Encoder getMimeEncoder() {
        return Encoder.RFC2045;
    }

    /**
     * Returns a {@link Encoder} that encodes using the
     * <a href="#mime">MIME</a> type base64 encoding scheme
     * with specified line length and line separators.
     *
     * <p>
     *  返回使用<a href="#mime"> MIME </a>类型base64编码方案(使用指定的行长和行分隔符)进行编码的{@link Encoder}。
     * 
     * 
     * @param   lineLength
     *          the length of each output line (rounded down to nearest multiple
     *          of 4). If {@code lineLength <= 0} the output will not be separated
     *          in lines
     * @param   lineSeparator
     *          the line separator for each output line
     *
     * @return  A Base64 encoder.
     *
     * @throws  IllegalArgumentException if {@code lineSeparator} includes any
     *          character of "The Base64 Alphabet" as specified in Table 1 of
     *          RFC 2045.
     */
    public static Encoder getMimeEncoder(int lineLength, byte[] lineSeparator) {
         Objects.requireNonNull(lineSeparator);
         int[] base64 = Decoder.fromBase64;
         for (byte b : lineSeparator) {
             if (base64[b & 0xff] != -1)
                 throw new IllegalArgumentException(
                     "Illegal base64 line separator character 0x" + Integer.toString(b, 16));
         }
         if (lineLength <= 0) {
             return Encoder.RFC4648;
         }
         return new Encoder(false, lineSeparator, lineLength >> 2 << 2, true);
    }

    /**
     * Returns a {@link Decoder} that decodes using the
     * <a href="#basic">Basic</a> type base64 encoding scheme.
     *
     * <p>
     *  返回使用<a href="#basic"> Basic </a>类型base64编码方案解码的{@link解码器}。
     * 
     * 
     * @return  A Base64 decoder.
     */
    public static Decoder getDecoder() {
         return Decoder.RFC4648;
    }

    /**
     * Returns a {@link Decoder} that decodes using the
     * <a href="#url">URL and Filename safe</a> type base64
     * encoding scheme.
     *
     * <p>
     *  返回使用<a href="#url"> URL和文件名安全</a>类型base64编码方案解码的{@link解码器}。
     * 
     * 
     * @return  A Base64 decoder.
     */
    public static Decoder getUrlDecoder() {
         return Decoder.RFC4648_URLSAFE;
    }

    /**
     * Returns a {@link Decoder} that decodes using the
     * <a href="#mime">MIME</a> type base64 decoding scheme.
     *
     * <p>
     * 返回{@link}解码器解码使用<a href="#mime"> MIME类型</A>的base64解码方案。
     * 
     * 
     * @return  A Base64 decoder.
     */
    public static Decoder getMimeDecoder() {
         return Decoder.RFC2045;
    }

    /**
     * This class implements an encoder for encoding byte data using
     * the Base64 encoding scheme as specified in RFC 4648 and RFC 2045.
     *
     * <p> Instances of {@link Encoder} class are safe for use by
     * multiple concurrent threads.
     *
     * <p> Unless otherwise noted, passing a {@code null} argument to
     * a method of this class will cause a
     * {@link java.lang.NullPointerException NullPointerException} to
     * be thrown.
     *
     * <p>
     *  在RFC 4648和RFC 2045规定此类实现了使用Base64编码方案编码字节数据的编码器。
     * 
     *  <p> {@link Encoder}类的实例可安全地用于多个并发线程。
     * 
     *  <p>除非另有说明,否则将{@code null}参数传递给此类的方法将导致抛出{@link java.lang.NullPointerException NullPointerException}。
     * 
     * 
     * @see     Decoder
     * @since   1.8
     */
    public static class Encoder {

        private final byte[] newline;
        private final int linemax;
        private final boolean isURL;
        private final boolean doPadding;

        private Encoder(boolean isURL, byte[] newline, int linemax, boolean doPadding) {
            this.isURL = isURL;
            this.newline = newline;
            this.linemax = linemax;
            this.doPadding = doPadding;
        }

        /**
         * This array is a lookup table that translates 6-bit positive integer
         * index values into their "Base64 Alphabet" equivalents as specified
         * in "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
         * <p>
         *  该数组是将6位正整数索引值转换为RFC 2045(和RFC 4648)的"表1：Base64字母表"中规定的它们的"Base64 Alphabet"等效值的查找表。
         * 
         */
        private static final char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        };

        /**
         * It's the lookup table for "URL and Filename safe Base64" as specified
         * in Table 2 of the RFC 4648, with the '+' and '/' changed to '-' and
         * '_'. This table is used when BASE64_URL is specified.
         * <p>
         *  它是RFC 4648的表2中指定的"URL和文件名安全Base64"的查找表,"+"和"/"更改为" - "和"_"。指定BASE64_URL时使用此表。
         * 
         */
        private static final char[] toBase64URL = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
        };

        private static final int MIMELINEMAX = 76;
        private static final byte[] CRLF = new byte[] {'\r', '\n'};

        static final Encoder RFC4648 = new Encoder(false, null, -1, true);
        static final Encoder RFC4648_URLSAFE = new Encoder(true, null, -1, true);
        static final Encoder RFC2045 = new Encoder(false, CRLF, MIMELINEMAX, true);

        private final int outLength(int srclen) {
            int len = 0;
            if (doPadding) {
                len = 4 * ((srclen + 2) / 3);
            } else {
                int n = srclen % 3;
                len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
            }
            if (linemax > 0)                                  // line separators
                len += (len - 1) / linemax * newline.length;
            return len;
        }

        /**
         * Encodes all bytes from the specified byte array into a newly-allocated
         * byte array using the {@link Base64} encoding scheme. The returned byte
         * array is of the length of the resulting bytes.
         *
         * <p>
         *  使用{@link Base64}编码方案将指定字节数组中的所有字节编码为新分配的字节数组。返回的字节数组是结果字节的长度。
         * 
         * 
         * @param   src
         *          the byte array to encode
         * @return  A newly-allocated byte array containing the resulting
         *          encoded bytes.
         */
        public byte[] encode(byte[] src) {
            int len = outLength(src.length);          // dst array size
            byte[] dst = new byte[len];
            int ret = encode0(src, 0, src.length, dst);
            if (ret != dst.length)
                 return Arrays.copyOf(dst, ret);
            return dst;
        }

        /**
         * Encodes all bytes from the specified byte array using the
         * {@link Base64} encoding scheme, writing the resulting bytes to the
         * given output byte array, starting at offset 0.
         *
         * <p> It is the responsibility of the invoker of this method to make
         * sure the output byte array {@code dst} has enough space for encoding
         * all bytes from the input byte array. No bytes will be written to the
         * output byte array if the output byte array is not big enough.
         *
         * <p>
         *  使用{@link Base64}编码方案对来自指定字节数组的所有字节进行编码,将结果字节写入给定输出字节数组,从偏移0开始。
         * 
         * <p>这种方法的调用者的责任是确保输出字节数组{@code dst}有足够的空间来对来自输入字节数组的所有字节进行编码。如果输出字节数组不够大,则不会有字节写入输出字节数组。
         * 
         * 
         * @param   src
         *          the byte array to encode
         * @param   dst
         *          the output byte array
         * @return  The number of bytes written to the output byte array
         *
         * @throws  IllegalArgumentException if {@code dst} does not have enough
         *          space for encoding all input bytes.
         */
        public int encode(byte[] src, byte[] dst) {
            int len = outLength(src.length);         // dst array size
            if (dst.length < len)
                throw new IllegalArgumentException(
                    "Output byte array is too small for encoding all input bytes");
            return encode0(src, 0, src.length, dst);
        }

        /**
         * Encodes the specified byte array into a String using the {@link Base64}
         * encoding scheme.
         *
         * <p> This method first encodes all input bytes into a base64 encoded
         * byte array and then constructs a new String by using the encoded byte
         * array and the {@link java.nio.charset.StandardCharsets#ISO_8859_1
         * ISO-8859-1} charset.
         *
         * <p> In other words, an invocation of this method has exactly the same
         * effect as invoking
         * {@code new String(encode(src), StandardCharsets.ISO_8859_1)}.
         *
         * <p>
         *  使用{@link Base64}编码方案将指定的字节数组编码为字符串。
         * 
         *  <p>此方法首先将所有输入字节编码为base64编码字节数组,然后使用编码的字节数组和{@link java.nio.charset.StandardCharsets#ISO_8859_1 ISO-8859-1}
         * 字符集构造一个新的字符串。
         * 
         *  <p>换句话说,调用此方法与调用{@code new String(encode(src),StandardCharsets.ISO_8859_1)}具有完全相同的效果。
         * 
         * 
         * @param   src
         *          the byte array to encode
         * @return  A String containing the resulting Base64 encoded characters
         */
        @SuppressWarnings("deprecation")
        public String encodeToString(byte[] src) {
            byte[] encoded = encode(src);
            return new String(encoded, 0, 0, encoded.length);
        }

        /**
         * Encodes all remaining bytes from the specified byte buffer into
         * a newly-allocated ByteBuffer using the {@link Base64} encoding
         * scheme.
         *
         * Upon return, the source buffer's position will be updated to
         * its limit; its limit will not have been changed. The returned
         * output buffer's position will be zero and its limit will be the
         * number of resulting encoded bytes.
         *
         * <p>
         *  使用{@link Base64}编码方案将来自指定字节缓冲区的所有剩余字节编码为新分配的ByteBuffer。
         * 
         *  返回时,源缓冲区的位置将更新到其限制;其极限不会改变。返回的输出缓冲区的位置将为零,其限制将是生成的编码字节数。
         * 
         * 
         * @param   buffer
         *          the source ByteBuffer to encode
         * @return  A newly-allocated byte buffer containing the encoded bytes.
         */
        public ByteBuffer encode(ByteBuffer buffer) {
            int len = outLength(buffer.remaining());
            byte[] dst = new byte[len];
            int ret = 0;
            if (buffer.hasArray()) {
                ret = encode0(buffer.array(),
                              buffer.arrayOffset() + buffer.position(),
                              buffer.arrayOffset() + buffer.limit(),
                              dst);
                buffer.position(buffer.limit());
            } else {
                byte[] src = new byte[buffer.remaining()];
                buffer.get(src);
                ret = encode0(src, 0, src.length, dst);
            }
            if (ret != dst.length)
                 dst = Arrays.copyOf(dst, ret);
            return ByteBuffer.wrap(dst);
        }

        /**
         * Wraps an output stream for encoding byte data using the {@link Base64}
         * encoding scheme.
         *
         * <p> It is recommended to promptly close the returned output stream after
         * use, during which it will flush all possible leftover bytes to the underlying
         * output stream. Closing the returned output stream will close the underlying
         * output stream.
         *
         * <p>
         *  使用{@link Base64}编码方案包装用于编码字节数据的输出流。
         * 
         *  <p>建议在使用后立即关闭返回的输出流,在此期间,它会将所有可能的剩余字节刷新到底层输出流。关闭返回的输出流将关闭底层输出流。
         * 
         * 
         * @param   os
         *          the output stream.
         * @return  the output stream for encoding the byte data into the
         *          specified Base64 encoded format
         */
        public OutputStream wrap(OutputStream os) {
            Objects.requireNonNull(os);
            return new EncOutputStream(os, isURL ? toBase64URL : toBase64,
                                       newline, linemax, doPadding);
        }

        /**
         * Returns an encoder instance that encodes equivalently to this one,
         * but without adding any padding character at the end of the encoded
         * byte data.
         *
         * <p> The encoding scheme of this encoder instance is unaffected by
         * this invocation. The returned encoder instance should be used for
         * non-padding encoding operation.
         *
         * <p>
         * 返回与此等效编码的编码器实例,但不会在编码字节数据的末尾添加任何填充字符。
         * 
         *  <p>此编码器实例的编码方案不受此调用的影响。返回的编码器实例应该用于非填充编码操作。
         * 
         * 
         * @return an equivalent encoder that encodes without adding any
         *         padding character at the end
         */
        public Encoder withoutPadding() {
            if (!doPadding)
                return this;
            return new Encoder(isURL, newline, linemax, false);
        }

        private int encode0(byte[] src, int off, int end, byte[] dst) {
            char[] base64 = isURL ? toBase64URL : toBase64;
            int sp = off;
            int slen = (end - off) / 3 * 3;
            int sl = off + slen;
            if (linemax > 0 && slen  > linemax / 4 * 3)
                slen = linemax / 4 * 3;
            int dp = 0;
            while (sp < sl) {
                int sl0 = Math.min(sp + slen, sl);
                for (int sp0 = sp, dp0 = dp ; sp0 < sl0; ) {
                    int bits = (src[sp0++] & 0xff) << 16 |
                               (src[sp0++] & 0xff) <<  8 |
                               (src[sp0++] & 0xff);
                    dst[dp0++] = (byte)base64[(bits >>> 18) & 0x3f];
                    dst[dp0++] = (byte)base64[(bits >>> 12) & 0x3f];
                    dst[dp0++] = (byte)base64[(bits >>> 6)  & 0x3f];
                    dst[dp0++] = (byte)base64[bits & 0x3f];
                }
                int dlen = (sl0 - sp) / 3 * 4;
                dp += dlen;
                sp = sl0;
                if (dlen == linemax && sp < end) {
                    for (byte b : newline){
                        dst[dp++] = b;
                    }
                }
            }
            if (sp < end) {               // 1 or 2 leftover bytes
                int b0 = src[sp++] & 0xff;
                dst[dp++] = (byte)base64[b0 >> 2];
                if (sp == end) {
                    dst[dp++] = (byte)base64[(b0 << 4) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                        dst[dp++] = '=';
                    }
                } else {
                    int b1 = src[sp++] & 0xff;
                    dst[dp++] = (byte)base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                    dst[dp++] = (byte)base64[(b1 << 2) & 0x3f];
                    if (doPadding) {
                        dst[dp++] = '=';
                    }
                }
            }
            return dp;
        }
    }

    /**
     * This class implements a decoder for decoding byte data using the
     * Base64 encoding scheme as specified in RFC 4648 and RFC 2045.
     *
     * <p> The Base64 padding character {@code '='} is accepted and
     * interpreted as the end of the encoded byte data, but is not
     * required. So if the final unit of the encoded byte data only has
     * two or three Base64 characters (without the corresponding padding
     * character(s) padded), they are decoded as if followed by padding
     * character(s). If there is a padding character present in the
     * final unit, the correct number of padding character(s) must be
     * present, otherwise {@code IllegalArgumentException} (
     * {@code IOException} when reading from a Base64 stream) is thrown
     * during decoding.
     *
     * <p> Instances of {@link Decoder} class are safe for use by
     * multiple concurrent threads.
     *
     * <p> Unless otherwise noted, passing a {@code null} argument to
     * a method of this class will cause a
     * {@link java.lang.NullPointerException NullPointerException} to
     * be thrown.
     *
     * <p>
     *  这个类实现了一个解码器,用于使用RFC 4648和RFC 2045中规定的Base64编码方案解码字节数据。
     * 
     *  <p> Base64填充字符{@code'='}被接受并解释为编码字节数据的结尾,但不是必需的。
     * 因此,如果编码字节数据的最后单元只有两个或三个Base64字符(没有填充相应的填充字符),则它们被解码,就好像后面跟着填充字符一样。
     * 如果在最后单元中存在填充字符,则必须存在正确数量的填充字符,否则在解码期间抛出{@code IllegalArgumentException}(从Base64流读取时为{@code IOException}
     * )。
     * 因此,如果编码字节数据的最后单元只有两个或三个Base64字符(没有填充相应的填充字符),则它们被解码,就好像后面跟着填充字符一样。
     * 
     *  <p> {@link Decoder}类的实例可安全地用于多个并发线程。
     * 
     *  <p>除非另有说明,否则将{@code null}参数传递给此类的方法将导致抛出{@link java.lang.NullPointerException NullPointerException}。
     * 
     * 
     * @see     Encoder
     * @since   1.8
     */
    public static class Decoder {

        private final boolean isURL;
        private final boolean isMIME;

        private Decoder(boolean isURL, boolean isMIME) {
            this.isURL = isURL;
            this.isMIME = isMIME;
        }

        /**
         * Lookup table for decoding unicode characters drawn from the
         * "Base64 Alphabet" (as specified in Table 1 of RFC 2045) into
         * their 6-bit positive integer equivalents.  Characters that
         * are not in the Base64 alphabet but fall within the bounds of
         * the array are encoded to -1.
         *
         * <p>
         * 用于将从"Base64字母表"(如RFC 2045的表1中所指定)的Unicode字符解码为其6位正整数等效值的查找表。不在Base64字母表中但落在数组边界内的字符将编码为-1。
         * 
         */
        private static final int[] fromBase64 = new int[256];
        static {
            Arrays.fill(fromBase64, -1);
            for (int i = 0; i < Encoder.toBase64.length; i++)
                fromBase64[Encoder.toBase64[i]] = i;
            fromBase64['='] = -2;
        }

        /**
         * Lookup table for decoding "URL and Filename safe Base64 Alphabet"
         * as specified in Table2 of the RFC 4648.
         * <p>
         *  用于解码"URL和文件名安全Base64字母表"的查找表,如RFC 4648的Table2中所指定。
         * 
         */
        private static final int[] fromBase64URL = new int[256];

        static {
            Arrays.fill(fromBase64URL, -1);
            for (int i = 0; i < Encoder.toBase64URL.length; i++)
                fromBase64URL[Encoder.toBase64URL[i]] = i;
            fromBase64URL['='] = -2;
        }

        static final Decoder RFC4648         = new Decoder(false, false);
        static final Decoder RFC4648_URLSAFE = new Decoder(true, false);
        static final Decoder RFC2045         = new Decoder(false, true);

        /**
         * Decodes all bytes from the input byte array using the {@link Base64}
         * encoding scheme, writing the results into a newly-allocated output
         * byte array. The returned byte array is of the length of the resulting
         * bytes.
         *
         * <p>
         *  使用{@link Base64}编码方案解码来自输入字节数组的所有字节,将结果写入新分配的输出字节数组。返回的字节数组是结果字节的长度。
         * 
         * 
         * @param   src
         *          the byte array to decode
         *
         * @return  A newly-allocated byte array containing the decoded bytes.
         *
         * @throws  IllegalArgumentException
         *          if {@code src} is not in valid Base64 scheme
         */
        public byte[] decode(byte[] src) {
            byte[] dst = new byte[outLength(src, 0, src.length)];
            int ret = decode0(src, 0, src.length, dst);
            if (ret != dst.length) {
                dst = Arrays.copyOf(dst, ret);
            }
            return dst;
        }

        /**
         * Decodes a Base64 encoded String into a newly-allocated byte array
         * using the {@link Base64} encoding scheme.
         *
         * <p> An invocation of this method has exactly the same effect as invoking
         * {@code decode(src.getBytes(StandardCharsets.ISO_8859_1))}
         *
         * <p>
         *  使用{@link Base64}编码方案将Base64编码字符串解码为新分配的字节数组。
         * 
         *  <p>调用此方法与调用{@code decode(src.getBytes(StandardCharsets.ISO_8859_1))}具有完全相同的效果。
         * 
         * 
         * @param   src
         *          the string to decode
         *
         * @return  A newly-allocated byte array containing the decoded bytes.
         *
         * @throws  IllegalArgumentException
         *          if {@code src} is not in valid Base64 scheme
         */
        public byte[] decode(String src) {
            return decode(src.getBytes(StandardCharsets.ISO_8859_1));
        }

        /**
         * Decodes all bytes from the input byte array using the {@link Base64}
         * encoding scheme, writing the results into the given output byte array,
         * starting at offset 0.
         *
         * <p> It is the responsibility of the invoker of this method to make
         * sure the output byte array {@code dst} has enough space for decoding
         * all bytes from the input byte array. No bytes will be be written to
         * the output byte array if the output byte array is not big enough.
         *
         * <p> If the input byte array is not in valid Base64 encoding scheme
         * then some bytes may have been written to the output byte array before
         * IllegalargumentException is thrown.
         *
         * <p>
         *  使用{@link Base64}编码方案从输入字节数组解码所有字节,将结果写入给定输出字节数组,从偏移0开始。
         * 
         *  <p>这种方法的调用者的责任是确保输出字节数组{@code dst}有足够的空间来解码来自输入字节数组的所有字节。如果输出字节数组不够大,则不会有字节写入输出字节数组。
         * 
         *  <p>如果输入字节数组不是有效的Base64编码方案,则在抛出IllegalargumentException之前,某些字节可能已被写入输出字节数组。
         * 
         * 
         * @param   src
         *          the byte array to decode
         * @param   dst
         *          the output byte array
         *
         * @return  The number of bytes written to the output byte array
         *
         * @throws  IllegalArgumentException
         *          if {@code src} is not in valid Base64 scheme, or {@code dst}
         *          does not have enough space for decoding all input bytes.
         */
        public int decode(byte[] src, byte[] dst) {
            int len = outLength(src, 0, src.length);
            if (dst.length < len)
                throw new IllegalArgumentException(
                    "Output byte array is too small for decoding all input bytes");
            return decode0(src, 0, src.length, dst);
        }

        /**
         * Decodes all bytes from the input byte buffer using the {@link Base64}
         * encoding scheme, writing the results into a newly-allocated ByteBuffer.
         *
         * <p> Upon return, the source buffer's position will be updated to
         * its limit; its limit will not have been changed. The returned
         * output buffer's position will be zero and its limit will be the
         * number of resulting decoded bytes
         *
         * <p> {@code IllegalArgumentException} is thrown if the input buffer
         * is not in valid Base64 encoding scheme. The position of the input
         * buffer will not be advanced in this case.
         *
         * <p>
         * 使用{@link Base64}编码方案解码来自输入字节缓冲区的所有字节,将结果写入新分配的ByteBuffer。
         * 
         *  <p>返回后,源缓冲区的位置将更新到其限制;其极限不会改变。返回的输出缓冲区的位置将为零,其限制将是生成的解码字节数
         * 
         *  <p>如果输入缓冲区不是有效的Base64编码方案,则会抛出{@code IllegalArgumentException}。在这种情况下,输入缓冲区的位置不会提前。
         * 
         * 
         * @param   buffer
         *          the ByteBuffer to decode
         *
         * @return  A newly-allocated byte buffer containing the decoded bytes
         *
         * @throws  IllegalArgumentException
         *          if {@code src} is not in valid Base64 scheme.
         */
        public ByteBuffer decode(ByteBuffer buffer) {
            int pos0 = buffer.position();
            try {
                byte[] src;
                int sp, sl;
                if (buffer.hasArray()) {
                    src = buffer.array();
                    sp = buffer.arrayOffset() + buffer.position();
                    sl = buffer.arrayOffset() + buffer.limit();
                    buffer.position(buffer.limit());
                } else {
                    src = new byte[buffer.remaining()];
                    buffer.get(src);
                    sp = 0;
                    sl = src.length;
                }
                byte[] dst = new byte[outLength(src, sp, sl)];
                return ByteBuffer.wrap(dst, 0, decode0(src, sp, sl, dst));
            } catch (IllegalArgumentException iae) {
                buffer.position(pos0);
                throw iae;
            }
        }

        /**
         * Returns an input stream for decoding {@link Base64} encoded byte stream.
         *
         * <p> The {@code read}  methods of the returned {@code InputStream} will
         * throw {@code IOException} when reading bytes that cannot be decoded.
         *
         * <p> Closing the returned input stream will close the underlying
         * input stream.
         *
         * <p>
         *  返回用于解码{@link Base64}编码字节流的输入流。
         * 
         *  <p>返回的{@code InputStream}的{@code read}方法在读取无法解码的字节时将抛出{@code IOException}。
         * 
         *  <p>关闭返回的输入流将关闭底层输入流。
         * 
         * 
         * @param   is
         *          the input stream
         *
         * @return  the input stream for decoding the specified Base64 encoded
         *          byte stream
         */
        public InputStream wrap(InputStream is) {
            Objects.requireNonNull(is);
            return new DecInputStream(is, isURL ? fromBase64URL : fromBase64, isMIME);
        }

        private int outLength(byte[] src, int sp, int sl) {
            int[] base64 = isURL ? fromBase64URL : fromBase64;
            int paddings = 0;
            int len = sl - sp;
            if (len == 0)
                return 0;
            if (len < 2) {
                if (isMIME && base64[0] == -1)
                    return 0;
                throw new IllegalArgumentException(
                    "Input byte[] should at least have 2 bytes for base64 bytes");
            }
            if (isMIME) {
                // scan all bytes to fill out all non-alphabet. a performance
                // trade-off of pre-scan or Arrays.copyOf
                int n = 0;
                while (sp < sl) {
                    int b = src[sp++] & 0xff;
                    if (b == '=') {
                        len -= (sl - sp + 1);
                        break;
                    }
                    if ((b = base64[b]) == -1)
                        n++;
                }
                len -= n;
            } else {
                if (src[sl - 1] == '=') {
                    paddings++;
                    if (src[sl - 2] == '=')
                        paddings++;
                }
            }
            if (paddings == 0 && (len & 0x3) !=  0)
                paddings = 4 - (len & 0x3);
            return 3 * ((len + 3) / 4) - paddings;
        }

        private int decode0(byte[] src, int sp, int sl, byte[] dst) {
            int[] base64 = isURL ? fromBase64URL : fromBase64;
            int dp = 0;
            int bits = 0;
            int shiftto = 18;       // pos of first byte of 4-byte atom
            while (sp < sl) {
                int b = src[sp++] & 0xff;
                if ((b = base64[b]) < 0) {
                    if (b == -2) {         // padding byte '='
                        // =     shiftto==18 unnecessary padding
                        // x=    shiftto==12 a dangling single x
                        // x     to be handled together with non-padding case
                        // xx=   shiftto==6&&sp==sl missing last =
                        // xx=y  shiftto==6 last is not =
                        if (shiftto == 6 && (sp == sl || src[sp++] != '=') ||
                            shiftto == 18) {
                            throw new IllegalArgumentException(
                                "Input byte array has wrong 4-byte ending unit");
                        }
                        break;
                    }
                    if (isMIME)    // skip if for rfc2045
                        continue;
                    else
                        throw new IllegalArgumentException(
                            "Illegal base64 character " +
                            Integer.toString(src[sp - 1], 16));
                }
                bits |= (b << shiftto);
                shiftto -= 6;
                if (shiftto < 0) {
                    dst[dp++] = (byte)(bits >> 16);
                    dst[dp++] = (byte)(bits >>  8);
                    dst[dp++] = (byte)(bits);
                    shiftto = 18;
                    bits = 0;
                }
            }
            // reached end of byte array or hit padding '=' characters.
            if (shiftto == 6) {
                dst[dp++] = (byte)(bits >> 16);
            } else if (shiftto == 0) {
                dst[dp++] = (byte)(bits >> 16);
                dst[dp++] = (byte)(bits >>  8);
            } else if (shiftto == 12) {
                // dangling single "x", incorrectly encoded.
                throw new IllegalArgumentException(
                    "Last unit does not have enough valid bits");
            }
            // anything left is invalid, if is not MIME.
            // if MIME, ignore all non-base64 character
            while (sp < sl) {
                if (isMIME && base64[src[sp++]] < 0)
                    continue;
                throw new IllegalArgumentException(
                    "Input byte array has incorrect ending byte at " + sp);
            }
            return dp;
        }
    }

    /*
     * An output stream for encoding bytes into the Base64.
     * <p>
     */
    private static class EncOutputStream extends FilterOutputStream {

        private int leftover = 0;
        private int b0, b1, b2;
        private boolean closed = false;

        private final char[] base64;    // byte->base64 mapping
        private final byte[] newline;   // line separator, if needed
        private final int linemax;
        private final boolean doPadding;// whether or not to pad
        private int linepos = 0;

        EncOutputStream(OutputStream os, char[] base64,
                        byte[] newline, int linemax, boolean doPadding) {
            super(os);
            this.base64 = base64;
            this.newline = newline;
            this.linemax = linemax;
            this.doPadding = doPadding;
        }

        @Override
        public void write(int b) throws IOException {
            byte[] buf = new byte[1];
            buf[0] = (byte)(b & 0xff);
            write(buf, 0, 1);
        }

        private void checkNewline() throws IOException {
            if (linepos == linemax) {
                out.write(newline);
                linepos = 0;
            }
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            if (closed)
                throw new IOException("Stream is closed");
            if (off < 0 || len < 0 || off + len > b.length)
                throw new ArrayIndexOutOfBoundsException();
            if (len == 0)
                return;
            if (leftover != 0) {
                if (leftover == 1) {
                    b1 = b[off++] & 0xff;
                    len--;
                    if (len == 0) {
                        leftover++;
                        return;
                    }
                }
                b2 = b[off++] & 0xff;
                len--;
                checkNewline();
                out.write(base64[b0 >> 2]);
                out.write(base64[(b0 << 4) & 0x3f | (b1 >> 4)]);
                out.write(base64[(b1 << 2) & 0x3f | (b2 >> 6)]);
                out.write(base64[b2 & 0x3f]);
                linepos += 4;
            }
            int nBits24 = len / 3;
            leftover = len - (nBits24 * 3);
            while (nBits24-- > 0) {
                checkNewline();
                int bits = (b[off++] & 0xff) << 16 |
                           (b[off++] & 0xff) <<  8 |
                           (b[off++] & 0xff);
                out.write(base64[(bits >>> 18) & 0x3f]);
                out.write(base64[(bits >>> 12) & 0x3f]);
                out.write(base64[(bits >>> 6)  & 0x3f]);
                out.write(base64[bits & 0x3f]);
                linepos += 4;
           }
            if (leftover == 1) {
                b0 = b[off++] & 0xff;
            } else if (leftover == 2) {
                b0 = b[off++] & 0xff;
                b1 = b[off++] & 0xff;
            }
        }

        @Override
        public void close() throws IOException {
            if (!closed) {
                closed = true;
                if (leftover == 1) {
                    checkNewline();
                    out.write(base64[b0 >> 2]);
                    out.write(base64[(b0 << 4) & 0x3f]);
                    if (doPadding) {
                        out.write('=');
                        out.write('=');
                    }
                } else if (leftover == 2) {
                    checkNewline();
                    out.write(base64[b0 >> 2]);
                    out.write(base64[(b0 << 4) & 0x3f | (b1 >> 4)]);
                    out.write(base64[(b1 << 2) & 0x3f]);
                    if (doPadding) {
                       out.write('=');
                    }
                }
                leftover = 0;
                out.close();
            }
        }
    }

    /*
     * An input stream for decoding Base64 bytes
     * <p>
     *  用于将字节编码到Base64中的输出流。
     * 
     */
    private static class DecInputStream extends InputStream {

        private final InputStream is;
        private final boolean isMIME;
        private final int[] base64;      // base64 -> byte mapping
        private int bits = 0;            // 24-bit buffer for decoding
        private int nextin = 18;         // next available "off" in "bits" for input;
                                         // -> 18, 12, 6, 0
        private int nextout = -8;        // next available "off" in "bits" for output;
                                         // -> 8, 0, -8 (no byte for output)
        private boolean eof = false;
        private boolean closed = false;

        DecInputStream(InputStream is, int[] base64, boolean isMIME) {
            this.is = is;
            this.base64 = base64;
            this.isMIME = isMIME;
        }

        private byte[] sbBuf = new byte[1];

        @Override
        public int read() throws IOException {
            return read(sbBuf, 0, 1) == -1 ? -1 : sbBuf[0] & 0xff;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (closed)
                throw new IOException("Stream is closed");
            if (eof && nextout < 0)    // eof and no leftover
                return -1;
            if (off < 0 || len < 0 || len > b.length - off)
                throw new IndexOutOfBoundsException();
            int oldOff = off;
            if (nextout >= 0) {       // leftover output byte(s) in bits buf
                do {
                    if (len == 0)
                        return off - oldOff;
                    b[off++] = (byte)(bits >> nextout);
                    len--;
                    nextout -= 8;
                } while (nextout >= 0);
                bits = 0;
            }
            while (len > 0) {
                int v = is.read();
                if (v == -1) {
                    eof = true;
                    if (nextin != 18) {
                        if (nextin == 12)
                            throw new IOException("Base64 stream has one un-decoded dangling byte.");
                        // treat ending xx/xxx without padding character legal.
                        // same logic as v == '=' below
                        b[off++] = (byte)(bits >> (16));
                        len--;
                        if (nextin == 0) {           // only one padding byte
                            if (len == 0) {          // no enough output space
                                bits >>= 8;          // shift to lowest byte
                                nextout = 0;
                            } else {
                                b[off++] = (byte) (bits >>  8);
                            }
                        }
                    }
                    if (off == oldOff)
                        return -1;
                    else
                        return off - oldOff;
                }
                if (v == '=') {                  // padding byte(s)
                    // =     shiftto==18 unnecessary padding
                    // x=    shiftto==12 dangling x, invalid unit
                    // xx=   shiftto==6 && missing last '='
                    // xx=y  or last is not '='
                    if (nextin == 18 || nextin == 12 ||
                        nextin == 6 && is.read() != '=') {
                        throw new IOException("Illegal base64 ending sequence:" + nextin);
                    }
                    b[off++] = (byte)(bits >> (16));
                    len--;
                    if (nextin == 0) {           // only one padding byte
                        if (len == 0) {          // no enough output space
                            bits >>= 8;          // shift to lowest byte
                            nextout = 0;
                        } else {
                            b[off++] = (byte) (bits >>  8);
                        }
                    }
                    eof = true;
                    break;
                }
                if ((v = base64[v]) == -1) {
                    if (isMIME)                 // skip if for rfc2045
                        continue;
                    else
                        throw new IOException("Illegal base64 character " +
                            Integer.toString(v, 16));
                }
                bits |= (v << nextin);
                if (nextin == 0) {
                    nextin = 18;    // clear for next
                    nextout = 16;
                    while (nextout >= 0) {
                        b[off++] = (byte)(bits >> nextout);
                        len--;
                        nextout -= 8;
                        if (len == 0 && nextout >= 0) {  // don't clean "bits"
                            return off - oldOff;
                        }
                    }
                    bits = 0;
                } else {
                    nextin -= 6;
                }
            }
            return off - oldOff;
        }

        @Override
        public int available() throws IOException {
            if (closed)
                throw new IOException("Stream is closed");
            return is.available();   // TBD:
        }

        @Override
        public void close() throws IOException {
            if (!closed) {
                closed = true;
                is.close();
            }
        }
    }
}
