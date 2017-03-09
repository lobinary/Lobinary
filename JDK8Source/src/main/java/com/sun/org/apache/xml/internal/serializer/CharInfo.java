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
 * $Id: CharInfo.java,v 1.2.4.1 2005/09/15 08:15:14 suresh_emailid Exp $
 * <p>
 *  $ Id：CharInfo.java,v 1.2.4.1 2005/09/15 08:15:14 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import com.sun.org.apache.xml.internal.serializer.utils.MsgKey;
import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import com.sun.org.apache.xml.internal.serializer.utils.WrappedRuntimeException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.xml.transform.TransformerException;

/**
 * This class provides services that tell if a character should have
 * special treatement, such as entity reference substitution or normalization
 * of a newline character.  It also provides character to entity reference
 * lookup.
 *
 * DEVELOPERS: See Known Issue in the constructor.
 *
 * @xsl.usage internal
 * <p>
 *  这个类提供了服务,告诉一个字符是否应该有特殊的处理,例如实体引用替换或换行符的规范化。它还提供字符到实体引用查找。
 * 
 *  开发人员：请参阅构造函数中的已知问题。
 * 
 *  @ xsl.usage internal
 * 
 */
final class CharInfo
{
    /** Given a character, lookup a String to output (e.g. a decorated entity reference). */
    private HashMap m_charToString = new HashMap();

    /**
     * The name of the HTML entities file.
     * If specified, the file will be resource loaded with the default class loader.
     * <p>
     *  HTML实体文件的名称。如果指定,文件将使用默认类加载器加载资源。
     * 
     */
    public static final String HTML_ENTITIES_RESOURCE =
                "com.sun.org.apache.xml.internal.serializer.HTMLEntities";

    /**
     * The name of the XML entities file.
     * If specified, the file will be resource loaded with the default class loader.
     * <p>
     *  XML实体文件的名称。如果指定,文件将使用默认类加载器加载资源。
     * 
     */
    public static final String XML_ENTITIES_RESOURCE =
                "com.sun.org.apache.xml.internal.serializer.XMLEntities";

    /** The horizontal tab character, which the parser should always normalize. */
    public static final char S_HORIZONAL_TAB = 0x09;

    /** The linefeed character, which the parser should always normalize. */
    public static final char S_LINEFEED = 0x0A;

    /** The carriage return character, which the parser should always normalize. */
    public static final char S_CARRIAGERETURN = 0x0D;

    /** This flag is an optimization for HTML entities. It false if entities
     * other than quot (34), amp (38), lt (60) and gt (62) are defined
     * in the range 0 to 127.
     * @xsl.usage internal
     * <p>
     *  除了(34),amp(38),lt(60)和gt(62)之外的其他定义在0到127的范围内。@ xsl.usage internal
     * 
     */
    final boolean onlyQuotAmpLtGt;

    /** Copy the first 0,1 ... ASCII_MAX values into an array */
    private static final int ASCII_MAX = 128;

    /** Array of values is faster access than a set of bits
     * to quickly check ASCII characters in attribute values.
     * <p>
     *  快速检查属性值中的ASCII字符。
     * 
     */
    private boolean[] isSpecialAttrASCII = new boolean[ASCII_MAX];

    /** Array of values is faster access than a set of bits
     * to quickly check ASCII characters in text nodes.
     * <p>
     * 以快速检查文本节点中的ASCII字符。
     * 
     */
    private boolean[] isSpecialTextASCII = new boolean[ASCII_MAX];

    private boolean[] isCleanTextASCII = new boolean[ASCII_MAX];

    /** An array of bits to record if the character is in the set.
     * Although information in this array is complete, the
     * isSpecialAttrASCII array is used first because access to its values
     * is common and faster.
     * <p>
     *  虽然此数组中的信息已完成,但先使用isSpecialAttrASCII数组,因为对其值的访问是常见且快速的。
     * 
     */
    private int array_of_bits[] = createEmptySetOfIntegers(65535);


    // 5 for 32 bit words,  6 for 64 bit words ...
    /*
     * This constant is used to shift an integer to quickly
     * calculate which element its bit is stored in.
     * 5 for 32 bit words (int) ,  6 for 64 bit words (long)
     * <p>
     *  该常数用于移位整数以快速计算其位存储在哪个元素中.5对于32位字(int),6对于64位字(长)
     * 
     */
    private static final int SHIFT_PER_WORD = 5;

    /*
     * A mask to get the low order bits which are used to
     * calculate the value of the bit within a given word,
     * that will represent the presence of the integer in the
     * set.
     *
     * 0x1F for 32 bit words (int),
     * or 0x3F for 64 bit words (long)
     * <p>
     *  获得用于计算给定字内的位的值的低阶位的掩码,其将表示集合中的整数的存在。
     * 
     *  32位字(int)为0x1F,64位字(长)为0x3F,
     * 
     */
    private static final int LOW_ORDER_BITMASK = 0x1f;

    /*
     * This is used for optimizing the lookup of bits representing
     * the integers in the set. It is the index of the first element
     * in the array array_of_bits[] that is not used.
     * <p>
     *  这用于优化表示集合中的整数的位的查找。它是数组array_of_bits []中未使用的第一个元素的索引。
     * 
     */
    private int firstWordNotUsed;


    /**
     * Constructor that reads in a resource file that describes the mapping of
     * characters to entity references.
     * This constructor is private, just to force the use
     * of the getCharInfo(entitiesResource) factory
     *
     * Resource files must be encoded in UTF-8 and can either be properties
     * files with a .properties extension assumed.  Alternatively, they can
     * have the following form, with no particular extension assumed:
     *
     * <pre>
     * # First char # is a comment
     * Entity numericValue
     * quot 34
     * amp 38
     * </pre>
     *
     * <p>
     *  构造器,用于读取描述字符到实体引用映射的资源文件。这个构造函数是私有的,只是强制使用getCharInfo(entitiesResource)工厂
     * 
     *  资源文件必须以UTF-8编码,并且可以是假定具有.properties扩展名的属性文件。或者,它们可以具有以下形式,不假定特定扩展：
     * 
     * <pre>
     *  #First char#is a comment实体numericValue quot 34 amp 38
     * </pre>
     * 
     * 
     * @param entitiesResource Name of properties or resource file that should
     * be loaded, which describes that mapping of characters to entity
     * references.
     */
    private CharInfo(String entitiesResource, String method)
    {
        this(entitiesResource, method, false);
    }

    private CharInfo(String entitiesResource, String method, boolean internal)
    {
        ResourceBundle entities = null;
        boolean noExtraEntities = true;

        // Make various attempts to interpret the parameter as a properties
        // file or resource file, as follows:
        //
        //   1) attempt to load .properties file using ResourceBundle
        //   2) try using the class loader to find the specified file a resource
        //      file
        //   3) try treating the resource a URI

        try {
            if (internal) {
                // Load entity property files by using PropertyResourceBundle,
                // cause of security issure for applets
                entities = PropertyResourceBundle.getBundle(entitiesResource);
            } else {
                ClassLoader cl = SecuritySupport.getContextClassLoader();
                if (cl != null) {
                    entities = PropertyResourceBundle.getBundle(entitiesResource,
                            Locale.getDefault(), cl);
                }
            }
        } catch (Exception e) {}

        if (entities != null) {
            Enumeration keys = entities.getKeys();
            while (keys.hasMoreElements()){
                String name = (String) keys.nextElement();
                String value = entities.getString(name);
                int code = Integer.parseInt(value);
                defineEntity(name, (char) code);
                if (extraEntity(code))
                    noExtraEntities = false;
            }
            set(S_LINEFEED);
            set(S_CARRIAGERETURN);
        } else {
            InputStream is = null;
            String err = null;

            // Load user specified resource file by using URL loading, it
            // requires a valid URI as parameter
            try {
                if (internal) {
                    is = CharInfo.class.getResourceAsStream(entitiesResource);
                } else {
                    ClassLoader cl = SecuritySupport.getContextClassLoader();
                    if (cl != null) {
                        try {
                            is = cl.getResourceAsStream(entitiesResource);
                        } catch (Exception e) {
                            err = e.getMessage();
                        }
                    }

                    if (is == null) {
                        try {
                            URL url = new URL(entitiesResource);
                            is = url.openStream();
                        } catch (Exception e) {
                            err = e.getMessage();
                        }
                    }
                }

                if (is == null) {
                    throw new RuntimeException(
                        Utils.messages.createMessage(
                            MsgKey.ER_RESOURCE_COULD_NOT_FIND,
                            new Object[] {entitiesResource, err}));
                }

                // Fix Bugzilla#4000: force reading in UTF-8
                //  This creates the de facto standard that Xalan's resource
                //  files must be encoded in UTF-8. This should work in all
                // JVMs.
                //
                // %REVIEW% KNOWN ISSUE: IT FAILS IN MICROSOFT VJ++, which
                // didn't implement the UTF-8 encoding. Theoretically, we should
                // simply let it fail in that case, since the JVM is obviously
                // broken if it doesn't support such a basic standard.  But
                // since there are still some users attempting to use VJ++ for
                // development, we have dropped in a fallback which makes a
                // second attempt using the platform's default encoding. In VJ++
                // this is apparently ASCII, which is subset of UTF-8... and
                // since the strings we'll be reading here are also primarily
                // limited to the 7-bit ASCII range (at least, in English
                // versions of Xalan), this should work well enough to keep us
                // on the air until we're ready to officially decommit from
                // VJ++.

                BufferedReader reader;
                try {
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    reader = new BufferedReader(new InputStreamReader(is));
                }

                String line = reader.readLine();

                while (line != null) {
                    if (line.length() == 0 || line.charAt(0) == '#') {
                        line = reader.readLine();

                        continue;
                    }

                    int index = line.indexOf(' ');

                    if (index > 1) {
                        String name = line.substring(0, index);

                        ++index;

                        if (index < line.length()) {
                            String value = line.substring(index);
                            index = value.indexOf(' ');

                            if (index > 0) {
                                value = value.substring(0, index);
                            }

                            int code = Integer.parseInt(value);

                            defineEntity(name, (char) code);
                            if (extraEntity(code))
                                noExtraEntities = false;
                        }
                    }

                    line = reader.readLine();
                }

                is.close();
                set(S_LINEFEED);
                set(S_CARRIAGERETURN);
            } catch (Exception e) {
                throw new RuntimeException(
                    Utils.messages.createMessage(
                        MsgKey.ER_RESOURCE_COULD_NOT_LOAD,
                        new Object[] { entitiesResource,
                                       e.toString(),
                                       entitiesResource,
                                       e.toString()}));
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception except) {}
                }
            }
        }

        /* initialize the array isCleanTextASCII[] with a cache of values
         * for use by ToStream.character(char[], int , int)
         * and the array isSpecialTextASCII[] with the opposite values
         * (all in the name of performance!)
         * <p>
         *  供ToStream.character(char [],int,int)和数组isSpecialTextASCII []使用,具有相反的值(所有在性能名称中！)
         * 
         */
        for (int ch = 0; ch <ASCII_MAX; ch++)
        if((((0x20 <= ch || (0x0A == ch || 0x0D == ch || 0x09 == ch)))
             && (!get(ch))) || ('"' == ch))
        {
            isCleanTextASCII[ch] = true;
            isSpecialTextASCII[ch] = false;
        }
        else {
            isCleanTextASCII[ch] = false;
            isSpecialTextASCII[ch] = true;
        }



        onlyQuotAmpLtGt = noExtraEntities;

        // initialize the array with a cache of the BitSet values
        for (int i=0; i<ASCII_MAX; i++)
            isSpecialAttrASCII[i] = get(i);

        /* Now that we've used get(ch) just above to initialize the
         * two arrays we will change by adding a tab to the set of
         * special chars for XML (but not HTML!).
         * We do this because a tab is always a
         * special character in an XML attribute,
         * but only a special character in XML text
         * if it has an entity defined for it.
         * This is the reason for this delay.
         * <p>
         * 两个数组,我们将通过添加一个选项卡到XML的特殊字符集(但不是HTML！)来更改。
         * 我们这样做是因为一个标签在XML属性中始终是一个特殊字符,但是如果它有一个为其定义的实体,则只有XML文本中的一个特殊字符。这是这种延迟的原因。
         * 
         */
        if (Method.XML.equals(method))
        {
            isSpecialAttrASCII[S_HORIZONAL_TAB] = true;
        }
    }

    /**
     * Defines a new character reference. The reference's name and value are
     * supplied. Nothing happens if the character reference is already defined.
     * <p>Unlike internal entities, character references are a string to single
     * character mapping. They are used to map non-ASCII characters both on
     * parsing and printing, primarily for HTML documents. '&lt;amp;' is an
     * example of a character reference.</p>
     *
     * <p>
     *  定义新的字符引用。提供了引用的名称和值。如果字符引用已定义,则不会发生任何事情。 <p>与内部实体不同,字符引用是一个字符串到单个字符映射。
     * 它们用于在解析和打印时映射非ASCII字符,主要用于HTML文档。 '&lt; amp;'是字符引用的示例。</p>。
     * 
     * 
     * @param name The entity's name
     * @param value The entity's value
     */
    private void defineEntity(String name, char value)
    {
        StringBuilder sb = new StringBuilder("&");
        sb.append(name);
        sb.append(';');
        String entityString = sb.toString();

        defineChar2StringMapping(entityString, value);
    }

    /**
     * Map a character to a String. For example given
     * the character '>' this method would return the fully decorated
     * entity name "&lt;".
     * Strings for entity references are loaded from a properties file,
     * but additional mappings defined through calls to defineChar2String()
     * are possible. Such entity reference mappings could be over-ridden.
     *
     * This is reusing a stored key object, in an effort to avoid
     * heap activity. Unfortunately, that introduces a threading risk.
     * Simplest fix for now is to make it a synchronized method, or to give
     * up the reuse; I see very little performance difference between them.
     * Long-term solution would be to replace the hashtable with a sparse array
     * keyed directly from the character's integer value; see DTM's
     * string pool for a related solution.
     *
     * <p>
     *  将字符映射到字符串。例如,给定字符">",此方法将返回完全装饰的实体名称"&lt;"。实体引用的字符串是从属性文件加载的,但是通过调用defineChar2String()定义的附加映射是可能的。
     * 这样的实体参考映射可以被覆盖。
     * 
     *  这是重用一个存储的密钥对象,以避免堆活动。不幸的是,这引入了线程风险。现在最简单的修复是使它成为一种同步方法,或放弃重用;我看到他们之间的性能差异很小。
     * 长期解决方案是用直接从字符的整数值键入的稀疏数组替换散列表;请参阅DTM的字符串池以获取相关解决方案。
     * 
     * 
     * @param value The character that should be resolved to
     * a String, e.g. resolve '>' to  "&lt;".
     *
     * @return The String that the character is mapped to, or null if not found.
     * @xsl.usage internal
     */
    String getOutputStringForChar(char value)
    {
        CharKey charKey = new CharKey();
        charKey.setChar(value);
        return (String) m_charToString.get(charKey);
    }

    /**
     * Tell if the character argument that is from
     * an attribute value should have special treatment.
     *
     * <p>
     * 告诉来自属性值的字符参数是否应该有特殊的处理。
     * 
     * 
     * @param value the value of a character that is in an attribute value
     * @return true if the character should have any special treatment,
     * such as when writing out attribute values,
     * or entity references.
     * @xsl.usage internal
     */
    final boolean isSpecialAttrChar(int value)
    {
        // for performance try the values in the boolean array first,
        // this is faster access than the BitSet for common ASCII values

        if (value < ASCII_MAX)
            return isSpecialAttrASCII[value];

        // rather than java.util.BitSet, our private
        // implementation is faster (and less general).
        return get(value);
    }

    /**
     * Tell if the character argument that is from a
     * text node should have special treatment.
     *
     * <p>
     *  告诉来自文本节点的字符参数是否应该有特殊的处理。
     * 
     * 
     * @param value the value of a character that is in a text node
     * @return true if the character should have any special treatment,
     * such as when writing out attribute values,
     * or entity references.
     * @xsl.usage internal
     */
    final boolean isSpecialTextChar(int value)
    {
        // for performance try the values in the boolean array first,
        // this is faster access than the BitSet for common ASCII values

        if (value < ASCII_MAX)
            return isSpecialTextASCII[value];

        // rather than java.util.BitSet, our private
        // implementation is faster (and less general).
        return get(value);
    }

    /**
     * This method is used to determine if an ASCII character in
     * a text node (not an attribute value) is "clean".
     * <p>
     *  此方法用于确定文本节点(不是属性值)中的ASCII字符是否为"干净"。
     * 
     * 
     * @param value the character to check (0 to 127).
     * @return true if the character can go to the writer as-is
     * @xsl.usage internal
     */
    final boolean isTextASCIIClean(int value)
    {
        return isCleanTextASCII[value];
    }


    /**
     * Read an internal resource file that describes the mapping of
     * characters to entity references; Construct a CharInfo object.
     *
     * <p>
     *  读取描述字符到实体引用的映射的内部资源文件;构造一个CharInfo对象。
     * 
     * 
     * @param entitiesFileName Name of entities resource file that should
     * be loaded, which describes the mapping of characters to entity references.
     * @param method the output method type, which should be one of "xml", "html", and "text".
     * @return an instance of CharInfo
     *
     * @xsl.usage internal
     */
    static CharInfo getCharInfoInternal(String entitiesFileName, String method)
    {
        CharInfo charInfo = (CharInfo) m_getCharInfoCache.get(entitiesFileName);
        if (charInfo != null) {
            return charInfo;
        }

        charInfo = new CharInfo(entitiesFileName, method, true);
        m_getCharInfoCache.put(entitiesFileName, charInfo);
        return charInfo;
    }

    /**
     * Constructs a CharInfo object using the following process to try reading
     * the entitiesFileName parameter:
     *
     *   1) attempt to load it as a ResourceBundle
     *   2) try using the class loader to find the specified file
     *   3) try opening it as an URI
     *
     * In case of 2 and 3, the resource file must be encoded in UTF-8 and have the
     * following format:
     * <pre>
     * # First char # is a comment
     * Entity numericValue
     * quot 34
     * amp 38
     * </pre>
     *
     * <p>
     *  使用以下过程构造CharInfo对象,以尝试读取entitiesFileName参数：
     * 
     *  1)尝试加载它作为ResourceBundle 2)尝试使用类加载器来找到指定的文件3)尝试打开它作为URI
     * 
     *  在2和3的情况下,资源文件必须以UTF-8编码,并具有以下格式：
     * <pre>
     *  #First char#is a comment实体numericValue quot 34 amp 38
     * </pre>
     * 
     * 
     * @param entitiesFileName Name of entities resource file that should
     * be loaded, which describes the mapping of characters to entity references.
     * @param method the output method type, which should be one of "xml", "html", and "text".
     * @return an instance of CharInfo
     */
    static CharInfo getCharInfo(String entitiesFileName, String method)
    {
        try {
            return new CharInfo(entitiesFileName, method, false);
        } catch (Exception e) {}

        String absoluteEntitiesFileName;

        if (entitiesFileName.indexOf(':') < 0) {
            absoluteEntitiesFileName =
                SystemIDResolver.getAbsoluteURIFromRelative(entitiesFileName);
        } else {
            try {
                absoluteEntitiesFileName =
                    SystemIDResolver.getAbsoluteURI(entitiesFileName, null);
            } catch (TransformerException te) {
                throw new WrappedRuntimeException(te);
            }
        }

        return new CharInfo(absoluteEntitiesFileName, method, false);
    }

    /** Table of user-specified char infos. */
    private static HashMap m_getCharInfoCache = new HashMap();

    /**
     * Returns the array element holding the bit value for the
     * given integer
     * <p>
     *  返回保存给定整数的位值的数组元素
     * 
     * 
     * @param i the integer that might be in the set of integers
     *
     */
    private static int arrayIndex(int i) {
        return (i >> SHIFT_PER_WORD);
    }

    /**
     * For a given integer in the set it returns the single bit
     * value used within a given word that represents whether
     * the integer is in the set or not.
     * <p>
     *  对于集合中的给定整数,它返回在给定字中使用的表示整数是否在集合中的单个比特值。
     * 
     */
    private static int bit(int i) {
        int ret = (1 << (i & LOW_ORDER_BITMASK));
        return ret;
    }

    /**
     * Creates a new empty set of integers (characters)
     * <p>
     *  创建一个新的空集合(字符)
     * 
     * 
     * @param max the maximum integer to be in the set.
     */
    private int[] createEmptySetOfIntegers(int max) {
        firstWordNotUsed = 0; // an optimization

        int[] arr = new int[arrayIndex(max - 1) + 1];
            return arr;

    }

    /**
     * Adds the integer (character) to the set of integers.
     * <p>
     *  将整数(字符)添加到整数集。
     * 
     * 
     * @param i the integer to add to the set, valid values are
     * 0, 1, 2 ... up to the maximum that was specified at
     * the creation of the set.
     */
    private final void set(int i) {
        setASCIIdirty(i);

        int j = (i >> SHIFT_PER_WORD); // this word is used
        int k = j + 1;

        if(firstWordNotUsed < k) // for optimization purposes.
            firstWordNotUsed = k;

        array_of_bits[j] |= (1 << (i & LOW_ORDER_BITMASK));
    }


    /**
     * Return true if the integer (character)is in the set of integers.
     *
     * This implementation uses an array of integers with 32 bits per
     * integer.  If a bit is set to 1 the corresponding integer is
     * in the set of integers.
     *
     * <p>
     *  如果整数(字符)在整数集合中,则返回true。
     * 
     *  此实现使用每个整数32位的整数数组。如果一个位被设置为1,则相应的整数在整数集合中。
     * 
     * 
     * @param i an integer that is tested to see if it is the
     * set of integers, or not.
     */
    private final boolean get(int i) {

        boolean in_the_set = false;
        int j = (i >> SHIFT_PER_WORD); // wordIndex(i)
        // an optimization here, ... a quick test to see
        // if this integer is beyond any of the words in use
        if(j < firstWordNotUsed)
            in_the_set = (array_of_bits[j] &
                          (1 << (i & LOW_ORDER_BITMASK))
            ) != 0;  // 0L for 64 bit words
        return in_the_set;
    }

    // record if there are any entities other than
    // quot, amp, lt, gt  (probably user defined)
    /**
    /* <p>
    /* 
     * @return true if the entity
     * @param code The value of the character that has an entity defined
     * for it.
     */
    private boolean extraEntity(int entityValue)
    {
        boolean extra = false;
        if (entityValue < 128)
        {
            switch (entityValue)
            {
                case 34 : // quot
                case 38 : // amp
                case 60 : // lt
                case 62 : // gt
                    break;
                default : // other entity in range 0 to 127
                    extra = true;
            }
        }
        return extra;
    }

    /**
     * If the character is a printable ASCII character then
     * mark it as not clean and needing replacement with
     * a String on output.
     * <p>
     *  如果字符是可打印的ASCII字符,则将其标记为不干净,需要在输出上替换为字符串。
     * 
     * 
     * @param ch
     */
    private void setASCIIdirty(int j)
    {
        if (0 <= j && j < ASCII_MAX)
        {
            isCleanTextASCII[j] = false;
            isSpecialTextASCII[j] = true;
        }
    }

    /**
     * If the character is a printable ASCII character then
     * mark it as and not needing replacement with
     * a String on output.
     * <p>
     * 如果字符是可打印的ASCII字符,则将其标记为,并且不需要在输出上替换为字符串。
     * 
     * 
     * @param ch
     */
    private void setASCIIclean(int j)
    {
        if (0 <= j && j < ASCII_MAX)
        {
            isCleanTextASCII[j] = true;
            isSpecialTextASCII[j] = false;
        }
    }

    private void defineChar2StringMapping(String outputString, char inputChar)
    {
        CharKey character = new CharKey(inputChar);
        m_charToString.put(character, outputString);
        set(inputChar);
    }

    /**
     * Simple class for fast lookup of char values, when used with
     * hashtables.  You can set the char, then use it as a key.
     *
     * This class is a copy of the one in com.sun.org.apache.xml.internal.utils.
     * It exists to cut the serializers dependancy on that package.
     *
     * @xsl.usage internal
     * <p>
     *  简单类,用于快速查找char值,当与哈希表一起使用时。您可以设置字符,然后将其用作键。
     * 
     *  这个类是com.sun.org.apache.xml.internal.utils中的一个副本。它存在于减少序列化程序对该包的依赖。
     * 
     *  @ xsl.usage internal
     * 
     */
    private static class CharKey extends Object
    {

      /** String value          */
      private char m_char;

      /**
       * Constructor CharKey
       *
       * <p>
       *  构造函数CharKey
       * 
       * 
       * @param key char value of this object.
       */
      public CharKey(char key)
      {
        m_char = key;
      }

      /**
       * Default constructor for a CharKey.
       *
       * <p>
       *  CharKey的默认构造函数。
       * 
       * 
       * @param key char value of this object.
       */
      public CharKey()
      {
      }

      /**
       * Get the hash value of the character.
       *
       * <p>
       *  获取字符的哈希值。
       * 
       * 
       * @return hash value of the character.
       */
      public final void setChar(char c)
      {
        m_char = c;
      }



      /**
       * Get the hash value of the character.
       *
       * <p>
       *  获取字符的哈希值。
       * 
       * 
       * @return hash value of the character.
       */
      public final int hashCode()
      {
        return (int)m_char;
      }

      /**
       * Override of equals() for this object
       *
       * <p>
       *  覆盖此对象的equals()
       * 
       * @param obj to compare to
       *
       * @return True if this object equals this string value
       */
      public final boolean equals(Object obj)
      {
        return ((CharKey)obj).m_char == m_char;
      }
    }


}
