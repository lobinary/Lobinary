/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.text;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * An {@code AttributedCharacterIterator} allows iteration through both text and
 * related attribute information.
 *
 * <p>
 * An attribute is a key/value pair, identified by the key.  No two
 * attributes on a given character can have the same key.
 *
 * <p>The values for an attribute are immutable, or must not be mutated
 * by clients or storage.  They are always passed by reference, and not
 * cloned.
 *
 * <p>A <em>run with respect to an attribute</em> is a maximum text range for
 * which:
 * <ul>
 * <li>the attribute is undefined or {@code null} for the entire range, or
 * <li>the attribute value is defined and has the same non-{@code null} value for the
 *     entire range.
 * </ul>
 *
 * <p>A <em>run with respect to a set of attributes</em> is a maximum text range for
 * which this condition is met for each member attribute.
 *
 * <p>When getting a run with no explicit attributes specified (i.e.,
 * calling {@link #getRunStart()} and {@link #getRunLimit()}), any
 * contiguous text segments having the same attributes (the same set
 * of attribute/value pairs) are treated as separate runs if the
 * attributes have been given to those text segments separately.
 *
 * <p>The returned indexes are limited to the range of the iterator.
 *
 * <p>The returned attribute information is limited to runs that contain
 * the current character.
 *
 * <p>
 * Attribute keys are instances of {@link AttributedCharacterIterator.Attribute} and its
 * subclasses, such as {@link java.awt.font.TextAttribute}.
 *
 * <p>
 *  {@code AttributedCharacterIterator}允许通过文本和相关属性信息进行迭代。
 * 
 * <p>
 *  属性是由键标识的键/值对。给定字符上没有两个属性可以具有相同的键。
 * 
 *  <p>属性的值是不可变的,或者不能由客户端或存储器改变。它们总是通过引用传递,而不是克隆。
 * 
 *  <p>相对于属性</em>运行的<em>是以下的最大文本范围：
 * <ul>
 *  <li>属性未定义或{@code null}用于整个范围,或<li>定义属性值,并且整个范围具有相同的非 -  {@ code null}值。
 * </ul>
 * 
 *  <p>相对于一组属性</em>运行的<em>是每个成员属性满足此条件的最大文本范围。
 * 
 *  <p>当没有指定显式属性(即调用{@link #getRunStart()}和{@link #getRunLimit()})时,任何连续的文本片段具有相同的属性(相同的属性/值对)被视为单独的运行,如
 * 果属性已经分别给予那些文本段。
 * 
 *  <p>返回的索引被限制在迭代器的范围内。
 * 
 *  <p>返回的属性信息限于包含当前字符的运行。
 * 
 * <p>
 * 属性键是{@link AttributedCharacterIterator.Attribute}及其子类的实例,例如{@link java.awt.font.TextAttribute}。
 * 
 * 
 * @see AttributedCharacterIterator.Attribute
 * @see java.awt.font.TextAttribute
 * @see AttributedString
 * @see Annotation
 * @since 1.2
 */

public interface AttributedCharacterIterator extends CharacterIterator {

    /**
     * Defines attribute keys that are used to identify text attributes. These
     * keys are used in {@code AttributedCharacterIterator} and {@code AttributedString}.
     * <p>
     *  定义用于标识文本属性的属性键。这些键在{@code AttributedCharacterIterator}和{@code AttributedString}中使用。
     * 
     * 
     * @see AttributedCharacterIterator
     * @see AttributedString
     * @since 1.2
     */

    public static class Attribute implements Serializable {

        /**
         * The name of this {@code Attribute}. The name is used primarily by {@code readResolve}
         * to look up the corresponding predefined instance when deserializing
         * an instance.
         * <p>
         *  此{@code Attribute}的名称。该名称主要由{@code readResolve}用于在反序列化实例时查找相应的预定义实例。
         * 
         * 
         * @serial
         */
        private String name;

        // table of all instances in this class, used by readResolve
        private static final Map<String, Attribute> instanceMap = new HashMap<>(7);

        /**
         * Constructs an {@code Attribute} with the given name.
         *
         * <p>
         *  构造具有给定名称的{@code Attribute}。
         * 
         * 
         * @param name the name of {@code Attribute}
         */
        protected Attribute(String name) {
            this.name = name;
            if (this.getClass() == Attribute.class) {
                instanceMap.put(name, this);
            }
        }

        /**
         * Compares two objects for equality. This version only returns true
         * for {@code x.equals(y)} if {@code x} and {@code y} refer
         * to the same object, and guarantees this for all subclasses.
         * <p>
         *  比较两个对象是否相等。如果{@code x}和{@code y}引用同一个对象,并且对所有子类都保证这一点,则此版本仅对{@code x.equals(y)}返回true。
         * 
         */
        public final boolean equals(Object obj) {
            return super.equals(obj);
        }

        /**
         * Returns a hash code value for the object. This version is identical to
         * the one in {@code Object}, but is also final.
         * <p>
         *  返回对象的哈希码值。此版本与{@code Object}中的版本相同,但也是最终版本。
         * 
         */
        public final int hashCode() {
            return super.hashCode();
        }

        /**
         * Returns a string representation of the object. This version returns the
         * concatenation of class name, {@code "("}, a name identifying the attribute
         * and {@code ")"}.
         * <p>
         *  返回对象的字符串表示形式。此版本返回类名称{@code"("},标识属性的名称和{@code})"}的连接。
         * 
         */
        public String toString() {
            return getClass().getName() + "(" + name + ")";
        }

        /**
         * Returns the name of the attribute.
         *
         * <p>
         *  返回属性的名称。
         * 
         * 
         * @return the name of {@code Attribute}
         */
        protected String getName() {
            return name;
        }

        /**
         * Resolves instances being deserialized to the predefined constants.
         *
         * <p>
         *  解析反序列化为预定义常量的实例。
         * 
         * 
         * @return the resolved {@code Attribute} object
         * @throws InvalidObjectException if the object to resolve is not
         *                                an instance of {@code Attribute}
         */
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != Attribute.class) {
                throw new InvalidObjectException("subclass didn't correctly implement readResolve");
            }

            Attribute instance = instanceMap.get(getName());
            if (instance != null) {
                return instance;
            } else {
                throw new InvalidObjectException("unknown attribute name");
            }
        }

        /**
         * Attribute key for the language of some text.
         * <p> Values are instances of {@link java.util.Locale Locale}.
         * <p>
         *  一些文本的语言的属性键。 <p>值是{@link java.util.Locale Locale}的实例。
         * 
         * 
         * @see java.util.Locale
         */
        public static final Attribute LANGUAGE = new Attribute("language");

        /**
         * Attribute key for the reading of some text. In languages where the written form
         * and the pronunciation of a word are only loosely related (such as Japanese),
         * it is often necessary to store the reading (pronunciation) along with the
         * written form.
         * <p>Values are instances of {@link Annotation} holding instances of {@link String}.
         *
         * <p>
         * 用于读取某些文本的属性键。在书写形式和单词的发音仅松散相关的语言(例如日语)中,通常需要与书面形式一起存储阅读(发音)。
         *  <p>值是{@link Annotation}持有{@link String}实例的实例。
         * 
         * 
         * @see Annotation
         * @see java.lang.String
         */
        public static final Attribute READING = new Attribute("reading");

        /**
         * Attribute key for input method segments. Input methods often break
         * up text into segments, which usually correspond to words.
         * <p>Values are instances of {@link Annotation} holding a {@code null} reference.
         * <p>
         *  输入法段的属性键。输入法通常将文本分成段,这通常对应于单词。 <p>值是{@link Annotation}持有{@code null}引用的实例。
         * 
         * 
         * @see Annotation
         */
        public static final Attribute INPUT_METHOD_SEGMENT = new Attribute("input_method_segment");

        // make sure the serial version doesn't change between compiler versions
        private static final long serialVersionUID = -9142742483513960612L;

    };

    /**
     * Returns the index of the first character of the run
     * with respect to all attributes containing the current character.
     *
     * <p>Any contiguous text segments having the same attributes (the
     * same set of attribute/value pairs) are treated as separate runs
     * if the attributes have been given to those text segments separately.
     *
     * <p>
     *  返回关于包含当前字符的所有属性的运行的第一个字符的索引。
     * 
     *  <p>具有相同属性(相同的属性/值对)的任何连续文本片段如果已分别给予这些文本片段的属性,则被视为单独的运行。
     * 
     * 
     * @return the index of the first character of the run
     */
    public int getRunStart();

    /**
     * Returns the index of the first character of the run
     * with respect to the given {@code attribute} containing the current character.
     *
     * <p>
     *  返回相对于包含当前字符的给定{@code属性}的运行的第一个字符的索引。
     * 
     * 
     * @param attribute the desired attribute.
     * @return the index of the first character of the run
     */
    public int getRunStart(Attribute attribute);

    /**
     * Returns the index of the first character of the run
     * with respect to the given {@code attributes} containing the current character.
     *
     * <p>
     *  返回相对于包含当前字符的给定{@code attributes}的运行的第一个字符的索引。
     * 
     * 
     * @param attributes a set of the desired attributes.
     * @return the index of the first character of the run
     */
    public int getRunStart(Set<? extends Attribute> attributes);

    /**
     * Returns the index of the first character following the run
     * with respect to all attributes containing the current character.
     *
     * <p>Any contiguous text segments having the same attributes (the
     * same set of attribute/value pairs) are treated as separate runs
     * if the attributes have been given to those text segments separately.
     *
     * <p>
     *  返回运行后的第一个字符相对于包含当前字符的所有属性的索引。
     * 
     *  <p>具有相同属性(相同的属性/值对)的任何连续文本片段如果已分别给予这些文本片段的属性,则被视为单独的运行。
     * 
     * 
     * @return the index of the first character following the run
     */
    public int getRunLimit();

    /**
     * Returns the index of the first character following the run
     * with respect to the given {@code attribute} containing the current character.
     *
     * <p>
     * 相对于包含当前字符的给定{@code属性},返回运行后的第一个字符的索引。
     * 
     * 
     * @param attribute the desired attribute
     * @return the index of the first character following the run
     */
    public int getRunLimit(Attribute attribute);

    /**
     * Returns the index of the first character following the run
     * with respect to the given {@code attributes} containing the current character.
     *
     * <p>
     *  返回运行后的第一个字符相对于包含当前字符的给定{@code attributes}的索引。
     * 
     * 
     * @param attributes a set of the desired attributes
     * @return the index of the first character following the run
     */
    public int getRunLimit(Set<? extends Attribute> attributes);

    /**
     * Returns a map with the attributes defined on the current
     * character.
     *
     * <p>
     *  返回具有在当前字符上定义的属性的地图。
     * 
     * 
     * @return a map with the attributes defined on the current character
     */
    public Map<Attribute,Object> getAttributes();

    /**
     * Returns the value of the named {@code attribute} for the current character.
     * Returns {@code null} if the {@code attribute} is not defined.
     *
     * <p>
     *  返回当前字符的命名的{@code attribute}的值。如果未定义{@code属性},则返回{@code null}。
     * 
     * 
     * @param attribute the desired attribute
     * @return the value of the named {@code attribute} or {@code null}
     */
    public Object getAttribute(Attribute attribute);

    /**
     * Returns the keys of all attributes defined on the
     * iterator's text range. The set is empty if no
     * attributes are defined.
     *
     * <p>
     *  返回在迭代器文本范围上定义的所有属性的键。如果未定义属性,则该集为空。
     * 
     * @return the keys of all attributes
     */
    public Set<Attribute> getAllAttributeKeys();
};
