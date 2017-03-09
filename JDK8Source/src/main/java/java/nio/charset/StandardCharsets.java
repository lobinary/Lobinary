/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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
 * Constant definitions for the standard {@link Charset Charsets}. These
 * charsets are guaranteed to be available on every implementation of the Java
 * platform.
 *
 * <p>
 *  标准{@link Charset Charsets}的常量定义。这些字符集保证在Java平台的每个实现上都可用。
 * 
 * 
 * @see <a href="Charset#standard">Standard Charsets</a>
 * @since 1.7
 */
public final class StandardCharsets {

    private StandardCharsets() {
        throw new AssertionError("No java.nio.charset.StandardCharsets instances for you!");
    }
    /**
     * Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the
     * Unicode character set
     * <p>
     *  七位ASCII,a.k.a. ISO646-US,a.k.a. Unicode字符集的基本拉丁语
     * 
     */
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    /**
     * ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
     * <p>
     *  ISO拉丁字母第1号,a.k.a. ISO-LATIN-1
     * 
     */
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    /**
     * Eight-bit UCS Transformation Format
     * <p>
     *  八位UCS转换格式
     * 
     */
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    /**
     * Sixteen-bit UCS Transformation Format, big-endian byte order
     * <p>
     *  十六位UCS变换格式,大端字节序
     * 
     */
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    /**
     * Sixteen-bit UCS Transformation Format, little-endian byte order
     * <p>
     *  十六位UCS变换格式,小端字节序
     * 
     */
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    /**
     * Sixteen-bit UCS Transformation Format, byte order identified by an
     * optional byte-order mark
     * <p>
     *  十六位UCS变换格式,由可选的字节顺序标记标识的字节顺序
     */
    public static final Charset UTF_16 = Charset.forName("UTF-16");
}
