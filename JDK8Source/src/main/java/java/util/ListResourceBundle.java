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
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.util;

import sun.util.ResourceBundleEnumeration;

/**
 * <code>ListResourceBundle</code> is an abstract subclass of
 * <code>ResourceBundle</code> that manages resources for a locale
 * in a convenient and easy to use list. See <code>ResourceBundle</code> for
 * more information about resource bundles in general.
 *
 * <P>
 * Subclasses must override <code>getContents</code> and provide an array,
 * where each item in the array is a pair of objects.
 * The first element of each pair is the key, which must be a
 * <code>String</code>, and the second element is the value associated with
 * that key.
 *
 * <p>
 * The following <a name="sample">example</a> shows two members of a resource
 * bundle family with the base name "MyResources".
 * "MyResources" is the default member of the bundle family, and
 * "MyResources_fr" is the French member.
 * These members are based on <code>ListResourceBundle</code>
 * (a related <a href="PropertyResourceBundle.html#sample">example</a> shows
 * how you can add a bundle to this family that's based on a properties file).
 * The keys in this example are of the form "s1" etc. The actual
 * keys are entirely up to your choice, so long as they are the same as
 * the keys you use in your program to retrieve the objects from the bundle.
 * Keys are case-sensitive.
 * <blockquote>
 * <pre>
 *
 * public class MyResources extends ListResourceBundle {
 *     protected Object[][] getContents() {
 *         return new Object[][] {
 *         // LOCALIZE THIS
 *             {"s1", "The disk \"{1}\" contains {0}."},  // MessageFormat pattern
 *             {"s2", "1"},                               // location of {0} in pattern
 *             {"s3", "My Disk"},                         // sample disk name
 *             {"s4", "no files"},                        // first ChoiceFormat choice
 *             {"s5", "one file"},                        // second ChoiceFormat choice
 *             {"s6", "{0,number} files"},                // third ChoiceFormat choice
 *             {"s7", "3 Mar 96"},                        // sample date
 *             {"s8", new Dimension(1,5)}                 // real object, not just string
 *         // END OF MATERIAL TO LOCALIZE
 *         };
 *     }
 * }
 *
 * public class MyResources_fr extends ListResourceBundle {
 *     protected Object[][] getContents() {
 *         return new Object[][] {
 *         // LOCALIZE THIS
 *             {"s1", "Le disque \"{1}\" {0}."},          // MessageFormat pattern
 *             {"s2", "1"},                               // location of {0} in pattern
 *             {"s3", "Mon disque"},                      // sample disk name
 *             {"s4", "ne contient pas de fichiers"},     // first ChoiceFormat choice
 *             {"s5", "contient un fichier"},             // second ChoiceFormat choice
 *             {"s6", "contient {0,number} fichiers"},    // third ChoiceFormat choice
 *             {"s7", "3 mars 1996"},                     // sample date
 *             {"s8", new Dimension(1,3)}                 // real object, not just string
 *         // END OF MATERIAL TO LOCALIZE
 *         };
 *     }
 * }
 * </pre>
 * </blockquote>
 *
 * <p>
 * The implementation of a {@code ListResourceBundle} subclass must be thread-safe
 * if it's simultaneously used by multiple threads. The default implementations
 * of the methods in this class are thread-safe.
 *
 * <p>
 *  <code> ListResourceBundle </code>是一个抽象的子类<code> ResourceBundle </code>,它在一个方便,易于使用的列表中管理语言环境的资源。
 * 有关资源束的更多信息,请参阅<code> ResourceBundle </code>。
 * 
 * <P>
 *  子类必须重载<code> getContents </code>并提供一个数组,其中数组中的每个项都是一对对象。
 * 每对的第一个元素是键,它必须是<code> String </code>,第二个元素是与该键相关联的值。
 * 
 * <p>
 * 以下<a name="sample">示例</a>显示基本名称为"MyResources"的资源束系列的两个成员。
 *  "MyResources"是束系列的默认成员,"MyResources_fr"是法语成员。
 * 这些成员基于<code> ListResourceBundle </code>(相关的<a href="PropertyResourceBundle.html#sample">示例</a>显示了如何向此
 * 系列添加基于属性文件的包) 。
 *  "MyResources"是束系列的默认成员,"MyResources_fr"是法语成员。此示例中的键的格式为"s1"等。
 * 实际的键完全取决于您的选择,只要它们与您在程序中用于从捆绑包中检索对象的键相同即可。键区分大小写。
 * <blockquote>
 * <pre>
 * 
 *  public class MyResources extends ListResourceBundle {protected Object [] [] getContents(){return new Object [] [] {// LOCALIZE THIS {"s1","The disk \"{1}
 *  \"contains {0} ,//消息格式模式{"s2","1"},//模式{s3","我的磁盘"}中的{0}的位置//样本磁盘名称{"s4","无文件"} ,//第一ChoiceFormat选择{"s5","one file"}
 * ,//第二ChoiceFormat选择{"s6","{0,number} files" 96"},// sample date {"s8",new Dimension(1,5)} //真实对象,而不仅仅
 * 是字符串// END OF MATERIAL TO LOCALIZE}; }}。
 * 
 * public class MyResources_fr extends ListResourceBundle {protected Object [] [] getContents(){return new Object [] [] {// LOCALIZE THIS {"s1","Le disque"{1}
 * "{0} //消息格式模式{"s2","1"},//模式{"s3","Mon disque"}中的{0}的位置,//样本磁盘名{"s4","ne contient pas de fichiers "},
 * //第一ChoiceFormat选择{"s5","contient un fichier"},//第二ChoiceFormat选择{"s6","contient {0,number} fichiers"
 * 
 * @see ResourceBundle
 * @see PropertyResourceBundle
 * @since JDK1.1
 */
public abstract class ListResourceBundle extends ResourceBundle {
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  ,"3 mars 1996"},// sample date {"s8",new Dimension(1,3)} //真实对象,而不仅仅是字符串// END OF MATERIAL TO LOCALI
     * ZE}; }}。
     * </pre>
     * </blockquote>
     * 
     * <p>
     *  {@code ListResourceBundle}子类的实现必须是线程安全的,如果它由多个线程同时使用。这个类中的方法的默认实现是线程安全的。
     * 
     */
    public ListResourceBundle() {
    }

    // Implements java.util.ResourceBundle.handleGetObject; inherits javadoc specification.
    public final Object handleGetObject(String key) {
        // lazily load the lookup hashtable.
        if (lookup == null) {
            loadLookup();
        }
        if (key == null) {
            throw new NullPointerException();
        }
        return lookup.get(key); // this class ignores locales
    }

    /**
     * Returns an <code>Enumeration</code> of the keys contained in
     * this <code>ResourceBundle</code> and its parent bundles.
     *
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     * 
     * @return an <code>Enumeration</code> of the keys contained in
     *         this <code>ResourceBundle</code> and its parent bundles.
     * @see #keySet()
     */
    public Enumeration<String> getKeys() {
        // lazily load the lookup hashtable.
        if (lookup == null) {
            loadLookup();
        }

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
        if (lookup == null) {
            loadLookup();
        }
        return lookup.keySet();
    }

    /**
     * Returns an array in which each item is a pair of objects in an
     * <code>Object</code> array. The first element of each pair is
     * the key, which must be a <code>String</code>, and the second
     * element is the value associated with that key.  See the class
     * description for details.
     *
     * <p>
     *  在此<code> ResourceBundle </code>中返回<em> </em>的<code> </code>
     * 
     * 
     * @return an array of an <code>Object</code> array representing a
     * key-value pair.
     */
    abstract protected Object[][] getContents();

    // ==================privates====================

    /**
     * We lazily load the lookup hashtable.  This function does the
     * loading.
     * <p>
     * 返回一个数组,其中每个项目都是一个<code> Object </code>数组中的一对对象。每对的第一个元素是键,它必须是<code> String </code>,第二个元素是与该键相关联的值。
     * 有关详细信息,请参阅类描述。
     * 
     */
    private synchronized void loadLookup() {
        if (lookup != null)
            return;

        Object[][] contents = getContents();
        HashMap<String,Object> temp = new HashMap<>(contents.length);
        for (int i = 0; i < contents.length; ++i) {
            // key must be non-null String, value must be non-null
            String key = (String) contents[i][0];
            Object value = contents[i][1];
            if (key == null || value == null) {
                throw new NullPointerException();
            }
            temp.put(key, value);
        }
        lookup = temp;
    }

    private Map<String,Object> lookup = null;
}
