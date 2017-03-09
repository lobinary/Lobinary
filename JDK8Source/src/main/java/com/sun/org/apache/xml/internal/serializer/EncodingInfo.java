/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: EncodingInfo.java,v 1.2.4.2 2005/09/15 12:01:24 suresh_emailid Exp $
 * <p>
 *  $ Id：EncodingInfo.java,v 1.2.4.2 2005/09/15 12:01:24 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.UnsupportedEncodingException;

/**
 * Holds information about a given encoding, which is the Java name for the
 * encoding, the equivalent ISO name.
 * <p>
 * An object of this type has two useful methods
 * <pre>
 * isInEncoding(char ch);
 * </pre>
 * which can be called if the character is not the high one in
 * a surrogate pair and:
 * <pre>
 * isInEncoding(char high, char low);
 * </pre>
 * which can be called if the two characters from a high/low surrogate pair.
 * <p>
 * An EncodingInfo object is a node in a binary search tree. Such a node
 * will answer if a character is in the encoding, and do so for a given
 * range of unicode values (<code>m_first</code> to
 * <code>m_last</code>). It will handle a certain range of values
 * explicitly (<code>m_explFirst</code> to <code>m_explLast</code>).
 * If the unicode point is before that explicit range, that is it
 * is in the range <code>m_first <= value < m_explFirst</code>, then it will delegate to another EncodingInfo object for The root
 * of such a tree, m_before.  Likewise for values in the range
 * <code>m_explLast < value <= m_last</code>, but delgating to <code>m_after</code>
 * <p>
 * Actually figuring out if a code point is in the encoding is expensive. So the
 * purpose of this tree is to cache such determinations, and not to build the
 * entire tree of information at the start, but only build up as much of the
 * tree as is used during the transformation.
 * <p>
 * This Class is not a public API, and should only be used internally within
 * the serializer.
 *
 * @xsl.usage internal
 * <p>
 *  保存有关给定编码的信息,这是编码的Java名称,等效的ISO名称。
 * <p>
 *  这种类型的对象具有两个有用的方法
 * <pre>
 *  isInEncoding(char ch);
 * </pre>
 *  如果字符不是代理对中的高字符,则可以调用它：
 * <pre>
 *  isInEncoding(char high,char low);
 * </pre>
 *  如果来自高/低代理对的两个字符,它可以被调用。
 * <p>
 * EncodingInfo对象是二叉搜索树中的一个节点。
 * 如果一个字符在编码中,并且对于给定范围的unicode值(<code> m_first </code>到<code> m_last </code>),这样的节点将回答。
 * 它将显式处理一定范围的值(<code> m_explFirst </code>到<code> m_explLast </code>)。
 * 如果unicode点在该显式范围之前,那就是它在<code> m_first <= value <m_explFirst </code>的范围内,那么它将委托给这样一个树的根的另一个EncodingIn
 * fo对象m_before。
 * 它将显式处理一定范围的值(<code> m_explFirst </code>到<code> m_explLast </code>)。
 * 同样,对于<code> m_explLast <value <= m_last </code>范围内的值,但是改为<code> m_after </code>。
 * <p>
 *  实际上弄清楚如果代码点在编码是昂贵的。因此,这个树的目的是缓存这样的确定,而不是在开始时构建整个信息树,而是仅仅构建与在转换期间使用的树一样多的树。
 * <p>
 *  此类不是公共API,只应在序列化程序内部使用。
 * 
 *  @ xsl.usage internal
 * 
 */
public final class EncodingInfo extends Object
{

    /**
     * The ISO encoding name.
     * <p>
     *  ISO编码名称。
     * 
     */
    final String name;

    /**
     * The name used by the Java convertor.
     * <p>
     *  Java转换器使用的名称。
     * 
     */
    final String javaName;

    /**
     * A helper object that we can ask if a
     * single char, or a surrogate UTF-16 pair
     * of chars that form a single character,
     * is in this encoding.
     * <p>
     *  一个帮助器对象,我们可以询问一个单一的字符,或一个替代的UTF-16字符对形成一个单一的字符,是在这个编码。
     * 
     */
    private InEncoding m_encoding;

    /**
     * This is not a public API. It returns true if the
     * char in question is in the encoding.
     * <p>
     *  这不是一个公共API。如果所讨论的字符在编码中,则返回true。
     * 
     * 
     * @param ch the char in question.
     * @xsl.usage internal
     */
    public boolean isInEncoding(char ch) {
        if (m_encoding == null) {
            m_encoding = new EncodingImpl();

            // One could put alternate logic in here to
            // instantiate another object that implements the
            // InEncoding interface. For example if the JRE is 1.4 or up
            // we could have an object that uses JRE 1.4 methods
        }
        return m_encoding.isInEncoding(ch);
    }

    /**
     * This is not a public API. It returns true if the
     * character formed by the high/low pair is in the encoding.
     * <p>
     *  这不是一个公共API。如果由高/低对形成的字符在编码中,则返回真。
     * 
     * 
     * @param high a char that the a high char of a high/low surrogate pair.
     * @param low a char that is the low char of a high/low surrogate pair.
     * @xsl.usage internal
     */
    public boolean isInEncoding(char high, char low) {
        if (m_encoding == null) {
            m_encoding = new EncodingImpl();

            // One could put alternate logic in here to
            // instantiate another object that implements the
            // InEncoding interface. For example if the JRE is 1.4 or up
            // we could have an object that uses JRE 1.4 methods
        }
        return m_encoding.isInEncoding(high, low);
    }

    /**
     * Create an EncodingInfo object based on the ISO name and Java name.
     * If both parameters are null any character will be considered to
     * be in the encoding. This is useful for when the serializer is in
     * temporary output state, and has no assciated encoding.
     *
     * <p>
     * 基于ISO名称和Java名称创建EncodingInfo对象。如果两个参数都为null,则任何字符都将被视为在编码中。当序列化器处于临时输出状态,并且没有合成编码时,这是有用的。
     * 
     * 
     * @param name reference to the ISO name.
     * @param javaName reference to the Java encoding name.
     */
    public EncodingInfo(String name, String javaName)
    {

        this.name = name;
        this.javaName = javaName;
    }



    /**
     * A simple interface to isolate the implementation.
     * We could also use some new JRE 1.4 methods in another implementation
     * provided we use reflection with them.
     * <p>
     * This interface is not a public API,
     * and should only be used internally within the serializer.
     * @xsl.usage internal
     * <p>
     *  一个简单的接口来隔离实现。我们也可以在另一个实现中使用一些新的JRE 1.4方法,只要我们使用反射。
     * <p>
     *  此接口不是公共API,只应在序列化程序内部使用。 @ xsl.usage internal
     * 
     */
    private interface InEncoding {
        /**
         * Returns true if the char is in the encoding
         * <p>
         *  如果char在编码中,则返回true
         * 
         */
        public boolean isInEncoding(char ch);
        /**
         * Returns true if the high/low surrogate pair forms
         * a character that is in the encoding.
         * <p>
         *  如果高/低代理对形成编码中的字符,则返回true。
         * 
         */
        public boolean isInEncoding(char high, char low);
    }

    /**
     * This class implements the
     * <p>
     *  这个类实现了
     * 
     */
    private class EncodingImpl implements InEncoding {



        public boolean isInEncoding(char ch1) {
            final boolean ret;
            int codePoint = Encodings.toCodePoint(ch1);
            if (codePoint < m_explFirst) {
                // The unicode value is before the range
                // that we explictly manage, so we delegate the answer.

                // If we don't have an m_before object to delegate to, make one.
                if (m_before == null)
                    m_before =
                        new EncodingImpl(
                            m_encoding,
                            m_first,
                            m_explFirst - 1,
                            codePoint);
                ret = m_before.isInEncoding(ch1);
            } else if (m_explLast < codePoint) {
                // The unicode value is after the range
                // that we explictly manage, so we delegate the answer.

                // If we don't have an m_after object to delegate to, make one.
                if (m_after == null)
                    m_after =
                        new EncodingImpl(
                            m_encoding,
                            m_explLast + 1,
                            m_last,
                            codePoint);
                ret = m_after.isInEncoding(ch1);
            } else {
                // The unicode value is in the range we explitly handle
                final int idx = codePoint - m_explFirst;

                // If we already know the answer, just return it.
                if (m_alreadyKnown[idx])
                    ret = m_isInEncoding[idx];
                else {
                    // We don't know the answer, so find out,
                    // which may be expensive, then cache the answer
                    ret = inEncoding(ch1, m_encoding);
                    m_alreadyKnown[idx] = true;
                    m_isInEncoding[idx] = ret;
                }
            }
            return ret;
        }

        public boolean isInEncoding(char high, char low) {
            final boolean ret;
            int codePoint = Encodings.toCodePoint(high,low);
            if (codePoint < m_explFirst) {
                // The unicode value is before the range
                // that we explictly manage, so we delegate the answer.

                // If we don't have an m_before object to delegate to, make one.
                if (m_before == null)
                    m_before =
                        new EncodingImpl(
                            m_encoding,
                            m_first,
                            m_explFirst - 1,
                            codePoint);
                ret = m_before.isInEncoding(high,low);
            } else if (m_explLast < codePoint) {
                // The unicode value is after the range
                // that we explictly manage, so we delegate the answer.

                // If we don't have an m_after object to delegate to, make one.
                if (m_after == null)
                    m_after =
                        new EncodingImpl(
                            m_encoding,
                            m_explLast + 1,
                            m_last,
                            codePoint);
                ret = m_after.isInEncoding(high,low);
            } else {
                // The unicode value is in the range we explitly handle
                final int idx = codePoint - m_explFirst;

                // If we already know the answer, just return it.
                if (m_alreadyKnown[idx])
                    ret = m_isInEncoding[idx];
                else {
                    // We don't know the answer, so find out,
                    // which may be expensive, then cache the answer
                    ret = inEncoding(high, low, m_encoding);
                    m_alreadyKnown[idx] = true;
                    m_isInEncoding[idx] = ret;
                }
            }
            return ret;
        }

        /**
         * The encoding.
         * <p>
         *  编码。
         * 
         */
        final private String m_encoding;
        /**
         * m_first through m_last is the range of unicode
         * values that this object will return an answer on.
         * It may delegate to a similar object with a different
         * range
         * <p>
         *  m_first到m_last是此对象将返回答案的unicode值的范围。它可以委托给具有不同范围的类似对象
         * 
         */
        final private int m_first;

        /**
         * m_explFirst through m_explLast is the range of unicode
         * value that this object handles explicitly and does not
         * delegate to a similar object.
         * <p>
         *  m_explFirst through m_explLast是此对象显式处理的unicode值的范围,不会委派给类似的对象。
         * 
         */
        final private int m_explFirst;
        final private int m_explLast;
        final private int m_last;

        /**
         * The object, of the same type as this one,
         * that handles unicode values in a range before
         * the range explictly handled by this object, and
         * to which this object may delegate.
         * <p>
         *  与此对象具有相同类型的对象,用于处理该对象明确处理的范围之前的范围内的unicode值,并且此对象可以委派给该对象。
         * 
         */
        private InEncoding m_before;
        /**
         * The object, of the same type as this one,
         * that handles unicode values in a range after
         * the range explictly handled by this object, and
         * to which this object may delegate.
         * <p>
         *  与此对象具有相同类型的对象,用于处理由此对象明确处理的范围中的一个范围内的unicode值,并且此对象可以委派给该对象。
         * 
         */
        private InEncoding m_after;

        /**
         * The number of unicode values explicitly handled
         * by a single EncodingInfo object. This value is
         * tuneable, but is set to 128 because that covers the
         * entire low range of ASCII type chars within a single
         * object.
         * <p>
         * 由单个EncodingInfo对象显式处理的unicode值的数量。此值是可调的,但设置为128,因为它覆盖单个对象中的ASCII类型字符的整个低范围。
         * 
         */
        private static final int RANGE = 128;

        /**
         * A flag to record if we already know the answer
         * for the given unicode value.
         * <p>
         *  如果我们已经知道给定的unicode值的答案,则记录一个标志。
         * 
         */
        final private boolean m_alreadyKnown[] = new boolean[RANGE];
        /**
         * A table holding the answer on whether the given unicode
         * value is in the encoding.
         * <p>
         *  一个表,包含给定的unicode值是否在编码中的答案。
         * 
         */
        final private boolean m_isInEncoding[] = new boolean[RANGE];

        private EncodingImpl() {
            // This object will answer whether any unicode value
            // is in the encoding, it handles values 0 through Integer.MAX_VALUE
            this(javaName, 0, Integer.MAX_VALUE, (char) 0);
        }

        private EncodingImpl(String encoding, int first, int last, int codePoint) {
            // Set the range of unicode values that this object manages
            // either explicitly or implicitly.
            m_first = first;
            m_last = last;

            // Set the range of unicode values that this object
            // explicitly manages. Align the explicitly managed values
            // to RANGE so multiple EncodingImpl objects dont manage the same
            // values.
            m_explFirst = codePoint / RANGE * RANGE;
            m_explLast = m_explFirst + (RANGE-1);

            m_encoding = encoding;

            if (javaName != null)
            {
                // Some optimization.
                if (0 <= m_explFirst && m_explFirst <= 127) {
                    // This particular EncodingImpl explicitly handles
                    // characters in the low range.
                    if ("UTF8".equals(javaName)
                        || "UTF-16".equals(javaName)
                        || "ASCII".equals(javaName)
                        || "US-ASCII".equals(javaName)
                        || "Unicode".equals(javaName)
                        || "UNICODE".equals(javaName)
                        || javaName.startsWith("ISO8859")) {

                        // Not only does this EncodingImpl object explicitly
                        // handle chracters in the low range, it is
                        // also one that we know something about, without
                        // needing to call inEncoding(char ch, String encoding)
                        // for this low range
                        //
                        // By initializing the table ahead of time
                        // for these low values, we prevent the expensive
                        // inEncoding(char ch, String encoding)
                        // from being called, at least for these common
                        // encodings.
                        for (int unicode = 1; unicode < 127; unicode++) {
                            final int idx = unicode - m_explFirst;
                            if (0 <= idx && idx < RANGE) {
                                m_alreadyKnown[idx] = true;
                                m_isInEncoding[idx] = true;
                            }
                        }
                    }
                }

                /* A little bit more than optimization.
                 *
                 * We will say that any character is in the encoding if
                 * we don't have an encoding.
                 * This is meaningful when the serializer is being used
                 * in temporary output state, where we are not writing to
                 * the final output tree.  It is when writing to the
                 * final output tree that we need to worry about the output
                 * encoding
                 * <p>
                 *  我们会说如果没有编码,任何字符都在编码中。当序列化器在临时输出状态下使用时,这是有意义的,我们不会写入最终的输出树。它是当写到最终输出树,我们需要担心输出编码
                 * 
                 */
                if (javaName == null) {
                    for (int idx = 0; idx < m_alreadyKnown.length; idx++) {
                        m_alreadyKnown[idx] = true;
                        m_isInEncoding[idx] = true;
                    }
                }
            }
        }
    }

    /**
     * This is heart of the code that determines if a given character
     * is in the given encoding. This method is probably expensive,
     * and the answer should be cached.
     * <p>
     * This method is not a public API,
     * and should only be used internally within the serializer.
     * <p>
     *  这是确定给定字符是否在给定编码中的代码的核心。这种方法可能很昂贵,答案应该被缓存。
     * <p>
     *  此方法不是公共API,只应在序列化程序内部使用。
     * 
     * 
     * @param ch the char in question, that is not a high char of
     * a high/low surrogate pair.
     * @param encoding the Java name of the enocding.
     *
     * @xsl.usage internal
     *
     */
    private static boolean inEncoding(char ch, String encoding) {
        boolean isInEncoding;
        try {
            char cArray[] = new char[1];
            cArray[0] = ch;
            // Construct a String from the char
            String s = new String(cArray);
            // Encode the String into a sequence of bytes
            // using the given, named charset.
            byte[] bArray = s.getBytes(encoding);
            isInEncoding = inEncoding(ch, bArray);

        } catch (Exception e) {
            isInEncoding = false;

            // If for some reason the encoding is null, e.g.
            // for a temporary result tree, we should just
            // say that every character is in the encoding.
            if (encoding == null)
                isInEncoding = true;
        }
        return isInEncoding;
    }

    /**
     * This is heart of the code that determines if a given high/low
     * surrogate pair forms a character that is in the given encoding.
     * This method is probably expensive, and the answer should be cached.
     * <p>
     * This method is not a public API,
     * and should only be used internally within the serializer.
     * <p>
     *  这是确定给定的高/低代理对是否形成给定编码中的字符的代码的核心。这种方法可能很昂贵,答案应该被缓存。
     * <p>
     *  此方法不是公共API,只应在序列化程序内部使用。
     * 
     * 
     * @param high the high char of
     * a high/low surrogate pair.
     * @param low the low char of a high/low surrogate pair.
     * @param encoding the Java name of the encoding.
     *
     * @xsl.usage internal
     *
     */
    private static boolean inEncoding(char high, char low, String encoding) {
        boolean isInEncoding;
        try {
            char cArray[] = new char[2];
            cArray[0] = high;
            cArray[1] = low;
            // Construct a String from the char
            String s = new String(cArray);
            // Encode the String into a sequence of bytes
            // using the given, named charset.
            byte[] bArray = s.getBytes(encoding);
            isInEncoding = inEncoding(high,bArray);
        } catch (Exception e) {
            isInEncoding = false;
        }

        return isInEncoding;
    }

    /**
     * This method is the core of determining if character
     * is in the encoding. The method is not foolproof, because
     * s.getBytes(encoding) has specified behavior only if the
     * characters are in the specified encoding. However this
     * method tries it's best.
     * <p>
     *  这种方法是确定字符是否在编码中的核心。该方法不是万无一失的,因为s.getBytes(encoding)仅当字符在指定的编码中时才指定行为。但是这种方法尝试是最好的。
     * 
     * 
     * @param ch the char that was converted using getBytes, or
     * the first char of a high/low pair that was converted.
     * @param data the bytes written out by the call to s.getBytes(encoding);
     * @return true if the character is in the encoding.
     */
    private static boolean inEncoding(char ch, byte[] data) {
        final boolean isInEncoding;
        // If the string written out as data is not in the encoding,
        // the output is not specified according to the documentation
        // on the String.getBytes(encoding) method,
        // but we do our best here.
        if (data==null || data.length == 0) {
            isInEncoding = false;
        }
        else {
            if (data[0] == 0)
                isInEncoding = false;
            else if (data[0] == '?' && ch != '?')
                isInEncoding = false;
            /*
             * else if (isJapanese) {
             *   // isJapanese is really
             *   //   (    "EUC-JP".equals(javaName)
             *   //    ||  "EUC_JP".equals(javaName)
             *  //     ||  "SJIS".equals(javaName)   )
             *
             *   // Work around some bugs in JRE for Japanese
             *   if(data[0] == 0x21)
             *     isInEncoding = false;
             *   else if (ch == 0xA5)
             *     isInEncoding = false;
             *   else
             *     isInEncoding = true;
             * }
             * <p>
             * else if(isJapanese){// isJapanese is really //("EUC-JP".equals(javaName)// ||"EUC_JP".equals(javaName)// ||"SJIS".equals(javaName))。
             * 
             *  //解决日语JRE中的一些错误if(data [0] == 0x21)isInEncoding = false; else if(ch == 0xA5)isInEncoding = false; el
             */

            else {
                // We don't know for sure, but it looks like it is in the encoding
                isInEncoding = true;
            }
        }
        return isInEncoding;
    }

}
