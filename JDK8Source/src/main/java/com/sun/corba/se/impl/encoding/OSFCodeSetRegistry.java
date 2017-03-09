/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.corba.se.impl.encoding;

/**
 *
 * Information from the OSF code set registry version 1.2g.
 *
 * Use the Entry corresponding to the desired code set.
 *
 * Consider rename to CodeSetRegistry since OSF is dead.
 * <p>
 *  信息从OSF代码集注册表版本1.2g。
 * 
 *  使用与所需代码集对应的条目。
 * 
 *  考虑重命名为CodeSetRegistry,因为OSF死了。
 * 
 */
public final class OSFCodeSetRegistry
{
    // Numbers from the OSF code set registry version 1.2g.
    //
    // Please see the individual Entry definitions for
    // more details.
    public static final int ISO_8859_1_VALUE = 0x00010001;
    public static final int UTF_16_VALUE = 0x00010109;
    public static final int UTF_8_VALUE = 0x05010001;
    public static final int UCS_2_VALUE = 0x00010100;
    public static final int ISO_646_VALUE = 0x00010020;

    private OSFCodeSetRegistry() {}

    /**
     * An entry in the OSF registry which allows users
     * to find out the equivalent Java character encoding
     * name as well as some other facts from the registry.
     * <p>
     *  OSF注册表中的一个条目,允许用户从注册表中找出等效的Java字符编码名称以及其他一些事实。
     * 
     */
    public final static class Entry
    {
        private String javaName;
        private int encodingNum;
        private boolean isFixedWidth;
        private int maxBytesPerChar;

        private Entry(String javaName,
                      int encodingNum,
                      boolean isFixedWidth,
                      int maxBytesPerChar) {
            this.javaName = javaName;
            this.encodingNum = encodingNum;
            this.isFixedWidth = isFixedWidth;
            this.maxBytesPerChar = maxBytesPerChar;
        }

        /**
         * Returns the Java equivalent name.  If the encoding has
         * an optional byte order marker, this name will map to the
         * Java encoding that includes the marker.
         * <p>
         *  返回Java等效名称。如果编码具有可选的字节顺序标记,则此名称将映射到包含标记的Java编码。
         * 
         */
        public String getName() {
            return javaName;
        }

        /**
         * Get the OSF registry number for this code set.
         * <p>
         *  获取此代码集的OSF注册表编号。
         * 
         */
        public int getNumber() {
            return encodingNum;
        }

        /**
         * Is this a fixed or variable width code set?  (In CORBA
         * terms, "non-byte-oriented" or a "byte-oriented"
         * code set, respectively)
         * <p>
         *  这是一个固定的还是可变宽度的代码集? (在CORBA术语中,分别是"非字节定向"或"面向字节"的代码集)
         * 
         */
        public boolean isFixedWidth() {
            return isFixedWidth;
        }

        public int getMaxBytesPerChar() {
            return maxBytesPerChar;
        }

        /**
         * First checks reference equality since it's expected
         * people will use the pre-defined constant Entries.
         * <p>
         *  首先检查引用相等性,因为人们将使用预定义的常量条目。
         * 
         */
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (!(obj instanceof OSFCodeSetRegistry.Entry))
                return false;

            OSFCodeSetRegistry.Entry other
                = (OSFCodeSetRegistry.Entry)obj;

            return (javaName.equals(other.javaName) &&
                    encodingNum == other.encodingNum &&
                    isFixedWidth == other.isFixedWidth &&
                    maxBytesPerChar == other.maxBytesPerChar);
        }

        /**
         * Uses the registry number as the hash code.
         * <p>
         *  使用注册表编号作为哈希码。
         * 
         */
        public int hashCode() {
            return encodingNum;
        }
    }

    /**
     * 8-bit encoding required for GIOP 1.0, and used as the char set
     * when nothing else is specified.
     * <p>
     *  GIOP 1.0需要8位编码,并且在没有指定其他值时用作字符集。
     * 
     */
    public static final Entry ISO_8859_1
        = new Entry("ISO-8859-1",
                    ISO_8859_1_VALUE,
                    true,
                    1);

    /**
     * UTF-16 as specified in the OSF registry has an optional
     * byte order marker.  UTF-16BE and UTF-16LE are not in the OSF
     * registry since it is no longer being developed.  When the OMG
     * switches to the IANA registry, these can be public.  Right
     * now, they're used internally by CodeSetConversion.
     * <p>
     *  在OSF注册表中指定的UTF-16有一个可选的字节顺序标记。 UTF-16BE和UTF-16LE不在OSF注册表中,因为它不再被开发。当OMG切换到IANA注册表时,这些可以是公共的。
     * 现在,它们由CodeSetConversion内部使用。
     * 
     */
    static final Entry UTF_16BE
        = new Entry("UTF-16BE",
                    -1,
                    true,
                    2);

    static final Entry UTF_16LE
        = new Entry("UTF-16LE",
                    -2,
                    true,
                    2);

    /**
     * Fallback wchar code set.
     *
     * In the resolution of issue 3405b, UTF-16 defaults to big endian, so
     * doesn't have to have a byte order marker.  Unfortunately, this has to be
     * a special case for compatibility.
     * <p>
     *  后备wchar代码集。
     * 
     * 在问题3405b的解决方案中,UTF-16默认为big endian,因此不必有字节顺序标记。不幸的是,这必须是兼容性的特殊情况。
     * 
     */
    public static final Entry UTF_16
        = new Entry("UTF-16",
                    UTF_16_VALUE,
                    true,
                    4);

    /**
     * Fallback char code set.  Also the code set for char data
     * in encapsulations.  However, since CORBA says chars are
     * only one octet, it is really the same as Latin-1.
     * <p>
     *  后备字符代码集。还有为封装中的char数据设置的代码。然而,由于CORBA说chars只有一个八位字节,它实际上与Latin-1相同。
     * 
     */
    public static final Entry UTF_8
        = new Entry("UTF-8",
                    UTF_8_VALUE,
                    false,
                    6);

    /*
     * At least in JDK 1.3, UCS-2 isn't one of the mandatory Java character
     * encodings.  However, our old ORBs require what they call UCS2, even
     * though they didn't necessarily do the correct encoding of it.
     *
     * This is a special case for our legacy ORBs, and put as the last thing
     * in our conversion list for wchar data.
     *
     * If a foreign ORB actually tries to speak UCS2 with us, it probably
     * won't work!  Beware!
     * <p>
     *  至少在JDK 1.3中,UCS-2不是必需的Java字符编码之一。然而,我们的旧ORB需要他们称之为UCS2,即使他们不一定做它的正确编码。
     * 
     *  这是我们的旧ORB的特殊情况,并且作为wchar数据的转换列表中的最后一项。
     * 
     *  如果外国ORB实际上试图与我们说UCS2,它可能不工作！谨防！
     * 
     */
    public static final Entry UCS_2
        = new Entry("UCS-2",
                    UCS_2_VALUE,
                    true,
                    2);

    /**
     * This is the encoding older JavaSoft ORBs advertised as their
     * CORBA char code set.  Actually, they took the lower byte of
     * the Java char.  This is a 7-bit encoding, so they
     * were really sending ISO8859-1.
     * <p>
     */
    public static final Entry ISO_646
        = new Entry("US-ASCII",
                    ISO_646_VALUE,
                    true,
                    1);

    /**
     * Given an OSF registry value, return the corresponding Entry.
     * Returns null if an Entry for that value is unavailable.
     * <p>
     *  这是编码较老的JavaSoft ORBs作为他们的CORBA字符代码集。实际上,他们拿了Java字符的低字节。这是一个7位编码,所以他们真的发送ISO8859-1。
     * 
     */
    public static Entry lookupEntry(int encodingValue) {
        switch(encodingValue) {
            case ISO_8859_1_VALUE:
                return OSFCodeSetRegistry.ISO_8859_1;
            case UTF_16_VALUE:
                return OSFCodeSetRegistry.UTF_16;
            case UTF_8_VALUE:
                return OSFCodeSetRegistry.UTF_8;
            case ISO_646_VALUE:
                return OSFCodeSetRegistry.ISO_646;
            case UCS_2_VALUE:
                return OSFCodeSetRegistry.UCS_2;
            default:
                return null;
        }
    }
}
