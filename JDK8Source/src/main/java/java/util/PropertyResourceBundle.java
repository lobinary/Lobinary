/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.util;

import java.io.InputStream;
import java.io.Reader;
import java.io.IOException;
import sun.util.ResourceBundleEnumeration;

/**
 * <code>PropertyResourceBundle</code> is a concrete subclass of
 * <code>ResourceBundle</code> that manages resources for a locale
 * using a set of static strings from a property file. See
 * {@link ResourceBundle ResourceBundle} for more information about resource
 * bundles.
 *
 * <p>
 * Unlike other types of resource bundle, you don't subclass
 * <code>PropertyResourceBundle</code>.  Instead, you supply properties
 * files containing the resource data.  <code>ResourceBundle.getBundle</code>
 * will automatically look for the appropriate properties file and create a
 * <code>PropertyResourceBundle</code> that refers to it. See
 * {@link ResourceBundle#getBundle(java.lang.String, java.util.Locale, java.lang.ClassLoader) ResourceBundle.getBundle}
 * for a complete description of the search and instantiation strategy.
 *
 * <p>
 * The following <a name="sample">example</a> shows a member of a resource
 * bundle family with the base name "MyResources".
 * The text defines the bundle "MyResources_de",
 * the German member of the bundle family.
 * This member is based on <code>PropertyResourceBundle</code>, and the text
 * therefore is the content of the file "MyResources_de.properties"
 * (a related <a href="ListResourceBundle.html#sample">example</a> shows
 * how you can add bundles to this family that are implemented as subclasses
 * of <code>ListResourceBundle</code>).
 * The keys in this example are of the form "s1" etc. The actual
 * keys are entirely up to your choice, so long as they are the same as
 * the keys you use in your program to retrieve the objects from the bundle.
 * Keys are case-sensitive.
 * <blockquote>
 * <pre>
 * # MessageFormat pattern
 * s1=Die Platte \"{1}\" enth&auml;lt {0}.
 *
 * # location of {0} in pattern
 * s2=1
 *
 * # sample disk name
 * s3=Meine Platte
 *
 * # first ChoiceFormat choice
 * s4=keine Dateien
 *
 * # second ChoiceFormat choice
 * s5=eine Datei
 *
 * # third ChoiceFormat choice
 * s6={0,number} Dateien
 *
 * # sample date
 * s7=3. M&auml;rz 1996
 * </pre>
 * </blockquote>
 *
 * <p>
 * The implementation of a {@code PropertyResourceBundle} subclass must be
 * thread-safe if it's simultaneously used by multiple threads. The default
 * implementations of the non-abstract methods in this class are thread-safe.
 *
 * <p>
 * <strong>Note:</strong> PropertyResourceBundle can be constructed either
 * from an InputStream or a Reader, which represents a property file.
 * Constructing a PropertyResourceBundle instance from an InputStream requires
 * that the input stream be encoded in ISO-8859-1.  In that case, characters
 * that cannot be represented in ISO-8859-1 encoding must be represented by Unicode Escapes
 * as defined in section 3.3 of
 * <cite>The Java&trade; Language Specification</cite>
 * whereas the other constructor which takes a Reader does not have that limitation.
 *
 * <p>
 *  <code> PropertyResourceBundle </code>是<code> ResourceBundle </code>的一个具体子类,它使用属性文件中的一组静态字符串来管理语言环境的资
 * 源。
 * 有关资源束的更多信息,请参阅{@link ResourceBundle ResourceBundle}。
 * 
 * <p>
 *  与其他类型的资源束不同,您不需要子类<code> PropertyResourceBundle </code>。相反,您提供包含资源数据的属性文件。
 *  <code> ResourceBundle.getBundle </code>会自动寻找相应的属性文件并创建一个引用它的<code> PropertyResourceBundle </code>。
 * 有关搜索和实例化策略的完整描述,请参阅{@link ResourceBundle#getBundle(java.lang.String,java.util.Locale,java.lang.ClassLoader)ResourceBundle.getBundle}
 * 。
 *  <code> ResourceBundle.getBundle </code>会自动寻找相应的属性文件并创建一个引用它的<code> PropertyResourceBundle </code>。
 * 
 * <p>
 * 以下<a name="sample">示例</a>显示基本名称为"MyResources"的资源束系列的成员。文本定义了捆绑包"MyResources_de",捆绑包系列的德语成员。
 * 此成员基于<code> PropertyResourceBundle </code>,因此文本是文件"MyResources_de.properties"的内容(相关的<a href="ListResourceBundle.html#sample">
 * 示例</a>显示如何添加捆绑到这个家庭,作为<code> ListResourceBundle </code>的子类实现)。
 * 以下<a name="sample">示例</a>显示基本名称为"MyResources"的资源束系列的成员。文本定义了捆绑包"MyResources_de",捆绑包系列的德语成员。
 * 此示例中的键的格式为"s1"等。实际的键完全取决于您的选择,只要它们与您在程序中用于从捆绑包中检索对象的键相同即可。键区分大小写。
 * <blockquote>
 * <pre>
 *  #MessageFormat pattern s1 = Die Platte"{1}"enth&auml; lt {0}。
 * 
 *  模式s2中的{0}的#位置= 1
 * 
 *  #sample disk name s3 = Meine Platte
 * 
 *  #first ChoiceFormat choice s4 = keine Dateien
 * 
 *  #second ChoiceFormat choice s5 = eine Datei
 * 
 * 
 * @see ResourceBundle
 * @see ListResourceBundle
 * @see Properties
 * @since JDK1.1
 */
public class PropertyResourceBundle extends ResourceBundle {
    /**
     * Creates a property resource bundle from an {@link java.io.InputStream
     * InputStream}.  The property file read with this constructor
     * must be encoded in ISO-8859-1.
     *
     * <p>
     *  #third ChoiceFormat choice s6 = {0,number} Dateien
     * 
     *  #sample date s7 = 3。 M&auml; rz 1996
     * </pre>
     * </blockquote>
     * 
     * <p>
     *  {@code PropertyResourceBundle}子类的实现必须是线程安全的,如果它由多个线程同时使用。这个类中的非抽象方法的默认实现是线程安全的。
     * 
     * <p>
     * <strong>注意</strong>：PropertyResourceBundle可以通过InputStream或Reader来构造,它代表一个属性文件。
     * 从InputStream构造PropertyResourceBundle实例需要在ISO-8859-1中编码输入流。
     * 在这种情况下,不能在ISO-8859-1编码中表示的字符必须由Unicode转义表示,如第<cite>节"Java&trade;语言规范</cite>,而采用读者的其他构造函数不具有该限制。
     * 
     * 
     * @param stream an InputStream that represents a property file
     *        to read from.
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if <code>stream</code> is null
     * @throws IllegalArgumentException if {@code stream} contains a
     *     malformed Unicode escape sequence.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PropertyResourceBundle (InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        lookup = new HashMap(properties);
    }

    /**
     * Creates a property resource bundle from a {@link java.io.Reader
     * Reader}.  Unlike the constructor
     * {@link #PropertyResourceBundle(java.io.InputStream) PropertyResourceBundle(InputStream)},
     * there is no limitation as to the encoding of the input property file.
     *
     * <p>
     *  从{@link java.io.InputStream InputStream}创建属性资源包。使用此构造函数读取的属性文件必须在ISO-8859-1中编码。
     * 
     * 
     * @param reader a Reader that represents a property file to
     *        read from.
     * @throws IOException if an I/O error occurs
     * @throws NullPointerException if <code>reader</code> is null
     * @throws IllegalArgumentException if a malformed Unicode escape sequence appears
     *     from {@code reader}.
     * @since 1.6
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public PropertyResourceBundle (Reader reader) throws IOException {
        Properties properties = new Properties();
        properties.load(reader);
        lookup = new HashMap(properties);
    }

    // Implements java.util.ResourceBundle.handleGetObject; inherits javadoc specification.
    public Object handleGetObject(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return lookup.get(key);
    }

    /**
     * Returns an <code>Enumeration</code> of the keys contained in
     * this <code>ResourceBundle</code> and its parent bundles.
     *
     * <p>
     *  从{@link java.io.Reader Reader}创建属性资源包。
     * 与构造函数{@link #PropertyResourceBundle(java.io.InputStream)PropertyResourceBundle(InputStream)}不同,对输入属性文
     * 件的编码没有限制。
     *  从{@link java.io.Reader Reader}创建属性资源包。
     * 
     * 
     * @return an <code>Enumeration</code> of the keys contained in
     *         this <code>ResourceBundle</code> and its parent bundles.
     * @see #keySet()
     */
    public Enumeration<String> getKeys() {
        ResourceBundle parent = this.parent;
        return new ResourceBundleEnumeration(lookup.keySet(),
                (parent != null) ? parent.getKeys() : null);
    }

    /**
     * Returns a <code>Set</code> of the keys contained
     * <em>only</em> in this <code>ResourceBundle</code>.
     *
     * <p>
     *  返回<code> ResourceBundle </code>及其父bundle中包含的键的<code>枚举</code>。
     * 
     * 
     * @return a <code>Set</code> of the keys contained only in this
     *         <code>ResourceBundle</code>
     * @since 1.6
     * @see #keySet()
     */
    protected Set<String> handleKeySet() {
        return lookup.keySet();
    }

    // ==================privates====================

    private Map<String,Object> lookup;
}
