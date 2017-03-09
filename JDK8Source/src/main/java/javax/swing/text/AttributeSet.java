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
package javax.swing.text;

import java.util.Enumeration;

/**
 * A collection of unique attributes.  This is a read-only,
 * immutable interface.  An attribute is basically a key and
 * a value assigned to the key.  The collection may represent
 * something like a style run, a logical style, etc.  These
 * are generally used to describe features that will contribute
 * to some graphical representation such as a font.  The
 * set of possible keys is unbounded and can be anything.
 * Typically View implementations will respond to attribute
 * definitions and render something to represent the attributes.
 * <p>
 * Attributes can potentially resolve in a hierarchy.  If a
 * key doesn't resolve locally, and a resolving parent
 * exists, the key will be resolved through the parent.
 *
 * <p>
 *  唯一属性的集合。这是一个只读的,不可变的接口。属性基本上是一个键和一个赋值给键的值。集合可以表示类似样式运行,逻辑样式等的东西。这些通常用于描述将有助于诸如字体的一些图形表示的特征。
 * 可能的键集是无限的,可以是任何东西。通常,View实现将响应属性定义并呈现某些东西以表示属性。
 * <p>
 *  属性可以在层次结构中解析。如果密钥不在本地解析,并且存在解析父代,则密钥将通过父代解析。
 * 
 * 
 * @author  Timothy Prinzing
 * @see MutableAttributeSet
 */
public interface AttributeSet {

    /**
     * This interface is the type signature that is expected
     * to be present on any attribute key that contributes to
     * the determination of what font to use to render some
     * text.  This is not considered to be a closed set, the
     * definition can change across version of the platform and can
     * be amended by additional user added entries that
     * correspond to logical settings that are specific to
     * some type of content.
     * <p>
     *  该接口是预期存在于任何属性键上的类型签名,该属性键有助于确定用于呈现一些文本的字体。
     * 这不被认为是封闭集合,定义可以跨平台的版本改变,并且可以通过对应于特定于某种类型的内容的逻辑设置的附加用户添加的条目来修改。
     * 
     */
    public interface FontAttribute {
    }

    /**
     * This interface is the type signature that is expected
     * to be present on any attribute key that contributes to
     * presentation of color.
     * <p>
     *  该接口是期望存在于有助于颜色呈现的任何属性键上的类型签名。
     * 
     */
    public interface ColorAttribute {
    }

    /**
     * This interface is the type signature that is expected
     * to be present on any attribute key that contributes to
     * character level presentation.  This would be any attribute
     * that applies to a so-called <i>run</i> of
     * style.
     * <p>
     * 该接口是期望存在于有助于字符级别呈现的任何属性键上的类型签名。这是适用于所谓的<i>运行</i>样式的任何属性。
     * 
     */
    public interface CharacterAttribute {
    }

    /**
     * This interface is the type signature that is expected
     * to be present on any attribute key that contributes to
     * the paragraph level presentation.
     * <p>
     *  该接口是期望在有助于段落级别呈现的任何属性键上存在的类型签名。
     * 
     */
    public interface ParagraphAttribute {
    }

    /**
     * Returns the number of attributes that are defined locally in this set.
     * Attributes that are defined in the parent set are not included.
     *
     * <p>
     *  返回在此集合中本地定义的属性数。不包括在父集中定义的属性。
     * 
     * 
     * @return the number of attributes &gt;= 0
     */
    public int getAttributeCount();

    /**
     * Checks whether the named attribute has a value specified in
     * the set without resolving through another attribute
     * set.
     *
     * <p>
     *  检查命名属性是否具有在集合中指定的值,而不通过另一个属性集解析。
     * 
     * 
     * @param attrName the attribute name
     * @return true if the attribute has a value specified
     */
    public boolean isDefined(Object attrName);

    /**
     * Determines if the two attribute sets are equivalent.
     *
     * <p>
     *  确定这两个属性集是否相等。
     * 
     * 
     * @param attr an attribute set
     * @return true if the sets are equivalent
     */
    public boolean isEqual(AttributeSet attr);

    /**
     * Returns an attribute set that is guaranteed not
     * to change over time.
     *
     * <p>
     *  返回保证不随时间更改的属性集。
     * 
     * 
     * @return a copy of the attribute set
     */
    public AttributeSet copyAttributes();

    /**
     * Fetches the value of the given attribute. If the value is not found
     * locally, the search is continued upward through the resolving
     * parent (if one exists) until the value is either
     * found or there are no more parents.  If the value is not found,
     * null is returned.
     *
     * <p>
     *  获取给定属性的值。如果在本地未找到该值,则通过解析父代(如果存在)继续向上搜索,直到找到该值或没有更多父代。如果未找到该值,则返回null。
     * 
     * 
     * @param key the non-null key of the attribute binding
     * @return the value of the attribute, or {@code null} if not found
     */
    public Object getAttribute(Object key);

    /**
     * Returns an enumeration over the names of the attributes that are
     * defined locally in the set. Names of attributes defined in the
     * resolving parent, if any, are not included. The values of the
     * <code>Enumeration</code> may be anything and are not constrained to
     * a particular <code>Object</code> type.
     * <p>
     * This method never returns {@code null}. For a set with no attributes, it
     * returns an empty {@code Enumeration}.
     *
     * <p>
     *  返回在本地在集合中定义的属性的名称的枚举。不包括在解析父代中定义的属性的名称(如果有)。
     *  <code>枚举</code>的值可以是任何值,并且不限于特定的<code> Object </code>类型。
     * <p>
     *  此方法从不返回{@code null}。对于没有属性的集合,它返回一个空的{@code枚举}。
     * 
     * 
     * @return the names
     */
    public Enumeration<?> getAttributeNames();

    /**
     * Returns {@code true} if this set defines an attribute with the same
     * name and an equal value. If such an attribute is not found locally,
     * it is searched through in the resolving parent hierarchy.
     *
     * <p>
     * 如果此集合定义具有相同名称和相等值的属性,则返回{@code true}。如果在本地未找到这样的属性,则在解析父层次结构中搜索它。
     * 
     * 
     * @param name the non-null attribute name
     * @param value the value
     * @return {@code true} if the set defines the attribute with an
     *     equal value, either locally or through its resolving parent
     * @throws NullPointerException if either {@code name} or
     *      {@code value} is {@code null}
     */
    public boolean containsAttribute(Object name, Object value);

    /**
     * Returns {@code true} if this set defines all the attributes from the
     * given set with equal values. If an attribute is not found locally,
     * it is searched through in the resolving parent hierarchy.
     *
     * <p>
     *  如果此集合使用相等的值定义来自给定集合的所有属性,则返回{@code true}。如果在本地未找到属性,则在解析父层次结构中搜索该属性。
     * 
     * 
     * @param attributes the set of attributes to check against
     * @return {@code true} if this set defines all the attributes with equal
     *              values, either locally or through its resolving parent
     * @throws NullPointerException if {@code attributes} is {@code null}
     */
    public boolean containsAttributes(AttributeSet attributes);

    /**
     * Gets the resolving parent.
     *
     * <p>
     *  获取解析父代。
     * 
     * 
     * @return the parent
     */
    public AttributeSet getResolveParent();

    /**
     * Attribute name used to name the collection of
     * attributes.
     * <p>
     *  用于命名属性集合的属性名称。
     * 
     */
    public static final Object NameAttribute = StyleConstants.NameAttribute;

    /**
     * Attribute name used to identify the resolving parent
     * set of attributes, if one is defined.
     * <p>
     *  用于标识解析父属性集的属性名(如果定义了属性集)。
     */
    public static final Object ResolveAttribute = StyleConstants.ResolveAttribute;

}
