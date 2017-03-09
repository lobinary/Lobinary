/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.directory;

import java.util.Vector;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.naming.NamingException;
import javax.naming.NamingEnumeration;
import javax.naming.OperationNotSupportedException;

/**
  * This interface represents an attribute associated with a named object.
  *<p>
  * In a directory, named objects can have associated with them
  * attributes.  The <tt>Attribute</tt> interface represents an attribute associated
  * with a named object.  An attribute contains 0 or more, possibly null, values.
  * The attribute values can be ordered or unordered (see <tt>isOrdered()</tt>).
  * If the values are unordered, no duplicates are allowed.
  * If the values are ordered, duplicates are allowed.
  *<p>
  * The content and representation of an attribute and its values is defined by
  * the attribute's <em>schema</em>. The schema contains information
  * about the attribute's syntax and other properties about the attribute.
  * See <tt>getAttributeDefinition()</tt> and
  * <tt>getAttributeSyntaxDefinition()</tt>
  * for details regarding how to get schema information about an attribute
  * if the underlying directory service supports schemas.
  *<p>
  * Equality of two attributes is determined by the implementation class.
  * A simple implementation can use <tt>Object.equals()</tt> to determine equality
  * of attribute values, while a more sophisticated implementation might
  * make use of schema information to determine equality.
  * Similarly, one implementation might provide a static storage
  * structure which simply returns the values passed to its
  * constructor, while another implementation might define <tt>get()</tt> and
  * <tt>getAll()</tt>.
  * to get the values dynamically from the directory.
  *<p>
  * Note that updates to <tt>Attribute</tt> (such as adding or removing a
  * value) do not affect the corresponding representation of the attribute
  * in the directory.  Updates to the directory can only be effected
  * using operations in the <tt>DirContext</tt> interface.
  *
  * <p>
  *  此接口表示与命名对象关联的属性。
  * p>
  *  在目录中,命名对象可以与其关联的属性。 <tt> Attribute </tt>接口表示与命名对象关联的属性。属性包含0个或多个(可能为空)值。
  * 属性值可以是有序的或无序的(参见<tt> isOrdered()</tt>)。如果值是无序的,则不允许重复。如果值是有序的,则允许重复。
  * p>
  *  属性及其值的内容和表示由属性的<em>模式</em>定义。模式包含关于属性的语法和有关属性的其他属性的信息。
  * 有关如何获取有关属性的架构信息的详细信息,请参阅<tt> getAttributeDefinition()</tt>和<tt> getAttributeSyntaxDefinition()</tt>(如
  * 果底层目录服务支持模式)。
  *  属性及其值的内容和表示由属性的<em>模式</em>定义。模式包含关于属性的语法和有关属性的其他属性的信息。
  * p>
  * 两个属性的相等性由实现类确定。一个简单的实现可以使用<tt> Object.equals()</tt>来确定属性值的相等性,而更复杂的实现可能利用模式信息来确定相等性。
  * 类似地,一个实现可以提供静态存储结构,其简单地返回传递给其构造函数的值,而另一个实现可以定义<tt> get()</tt>和<tt> getAll()</tt>。以从目录动态获取值。
  * p>
  *  请注意,更新<tt> Attribute </tt>(例如添加或删除值)不会影响目录中属性的相应表示。只能使用<tt> DirContext </tt>界面中的操作来更新目录。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see BasicAttribute
  * @since 1.3
  */
public interface Attribute extends Cloneable, java.io.Serializable {
    /**
      * Retrieves an enumeration of the attribute's values.
      * The behaviour of this enumeration is unspecified
      * if the attribute's values are added, changed,
      * or removed while the enumeration is in progress.
      * If the attribute values are ordered, the enumeration's items
      * will be ordered.
      *
      * <p>
      *  检索属性值的枚举。如果在枚举正在进行时添加,更改或删除属性的值,则此枚举的行为是未指定的。如果属性值是有序的,则枚举的项目将被排序。
      * 
      * 
      * @return A non-null enumeration of the attribute's values.
      * Each element of the enumeration is a possibly null Object. The object's
      * class is the class of the attribute value. The element is null
      * if the attribute's value is null.
      * If the attribute has zero values, an empty enumeration
      * is returned.
      * @exception NamingException
      *         If a naming exception was encountered while retrieving
      *         the values.
      * @see #isOrdered
      */
    NamingEnumeration<?> getAll() throws NamingException;

    /**
      * Retrieves one of this attribute's values.
      * If the attribute has more than one value and is unordered, any one of
      * the values is returned.
      * If the attribute has more than one value and is ordered, the
      * first value is returned.
      *
      * <p>
      *  检索此属性的值之一。如果属性具有多个值并且是无序的,则返回任何一个值。如果属性具有多个值并且已排序,则返回第一个值。
      * 
      * 
      * @return A possibly null object representing one of
      *        the attribute's value. It is null if the attribute's value
      *        is null.
      * @exception NamingException
      *         If a naming exception was encountered while retrieving
      *         the value.
      * @exception java.util.NoSuchElementException
      *         If this attribute has no values.
      */
    Object get() throws NamingException;

    /**
      * Retrieves the number of values in this attribute.
      *
      * <p>
      *  检索此属性中的值的数量。
      * 
      * 
      * @return The nonnegative number of values in this attribute.
      */
    int size();

    /**
      * Retrieves the id of this attribute.
      *
      * <p>
      *  检索此属性的ID。
      * 
      * 
      * @return The id of this attribute. It cannot be null.
      */
    String getID();

    /**
      * Determines whether a value is in the attribute.
      * Equality is determined by the implementation, which may use
      * <tt>Object.equals()</tt> or schema information to determine equality.
      *
      * <p>
      * 确定值是否在属性中。平等由实现决定,它可以使用<tt> Object.equals()</tt>或模式信息来确定等式。
      * 
      * 
      * @param attrVal The possibly null value to check. If null, check
      *  whether the attribute has an attribute value whose value is null.
      * @return true if attrVal is one of this attribute's values; false otherwise.
      * @see java.lang.Object#equals
      * @see BasicAttribute#equals
      */
    boolean contains(Object attrVal);
    /**
      * Adds a new value to the attribute.
      * If the attribute values are unordered and
      * <tt>attrVal</tt> is already in the attribute, this method does nothing.
      * If the attribute values are ordered, <tt>attrVal</tt> is added to the end of
      * the list of attribute values.
      *<p>
      * Equality is determined by the implementation, which may use
      * <tt>Object.equals()</tt> or schema information to determine equality.
      *
      * <p>
      *  向属性添加新值。如果属性值是无序的,并且属性中已经有<tt> attrVal </tt>,则此方法不执行任何操作。如果属性值有序,则<tt> attrVal </tt>将添加到属性值列表的末尾。
      * p>
      *  平等由实现决定,它可以使用<tt> Object.equals()</tt>或模式信息来确定等式。
      * 
      * 
      * @param attrVal The new possibly null value to add. If null, null
      *  is added as an attribute value.
      * @return true if a value was added; false otherwise.
      */
    boolean add(Object attrVal);

    /**
      * Removes a specified value from the attribute.
      * If <tt>attrval</tt> is not in the attribute, this method does nothing.
      * If the attribute values are ordered, the first occurrence of
      * <tt>attrVal</tt> is removed and attribute values at indices greater
      * than the removed
      * value are shifted up towards the head of the list (and their indices
      * decremented by one).
      *<p>
      * Equality is determined by the implementation, which may use
      * <tt>Object.equals()</tt> or schema information to determine equality.
      *
      * <p>
      *  从属性中删除指定的值。如果<tt> attrval </tt>不在属性中,则此方法不执行任何操作。
      * 如果属性值被排序,则删除<tt> attrVal </tt>的第一次出现,并且大于删除值的索引处的属性值向上移动到列表的头部(并且它们的索引递减1)。
      * p>
      *  平等由实现决定,它可以使用<tt> Object.equals()</tt>或模式信息来确定等式。
      * 
      * 
      * @param attrval The possibly null value to remove from this attribute.
      * If null, remove the attribute value that is null.
      * @return true if the value was removed; false otherwise.
      */
    boolean remove(Object attrval);

    /**
      * Removes all values from this attribute.
      * <p>
      *  从此属性中删除所有值。
      * 
      */
    void clear();

    /**
      * Retrieves the syntax definition associated with the attribute.
      * An attribute's syntax definition specifies the format
      * of the attribute's value(s). Note that this is different from
      * the attribute value's representation as a Java object. Syntax
      * definition refers to the directory's notion of <em>syntax</em>.
      *<p>
      * For example, even though a value might be
      * a Java String object, its directory syntax might be "Printable String"
      * or "Telephone Number". Or a value might be a byte array, and its
      * directory syntax is "JPEG" or "Certificate".
      * For example, if this attribute's syntax is "JPEG",
      * this method would return the syntax definition for "JPEG".
      * <p>
      * The information that you can retrieve from a syntax definition
      * is directory-dependent.
      *<p>
      * If an implementation does not support schemas, it should throw
      * OperationNotSupportedException. If an implementation does support
      * schemas, it should define this method to return the appropriate
      * information.
      * <p>
      *  检索与属性关联的语法定义。属性的语法定义指定属性值的格式。请注意,这不同于作为Java对象的属性值表示。语法定义是指目录的<em>语法概念</em>。
      * p>
      * 例如,即使值可能是Java字符串对象,其目录语法可能是"可打印字符串"或"电话号码"。或者一个值可能是一个字节数组,其目录语法是"JPEG"或"证书"。
      * 例如,如果此属性的语法是"JPEG",则此方法将返回"JPEG"的语法定义。
      * <p>
      *  您可以从语法定义检索的信息是与目录相关的。
      * p>
      *  如果实现不支持模式,它应该抛出OperationNotSupportedException。如果实现不支持模式,则应定义此方法以返回适当的信息。
      * 
      * 
      * @return The attribute's syntax definition. Null if the implementation
      *    supports schemas but this particular attribute does not have
      *    any schema information.
      * @exception OperationNotSupportedException If getting the schema
      *         is not supported.
      * @exception NamingException If a naming exception occurs while getting
      *         the schema.
      */

    DirContext getAttributeSyntaxDefinition() throws NamingException;

    /**
      * Retrieves the attribute's schema definition.
      * An attribute's schema definition contains information
      * such as whether the attribute is multivalued or single-valued,
      * the matching rules to use when comparing the attribute's values.
      *
      * The information that you can retrieve from an attribute definition
      * is directory-dependent.
      *
      *<p>
      * If an implementation does not support schemas, it should throw
      * OperationNotSupportedException. If an implementation does support
      * schemas, it should define this method to return the appropriate
      * information.
      * <p>
      *  检索属性的模式定义。属性的模式定义包含的信息包括属性是多值还是单值,比较属性值时使用的匹配规则。
      * 
      *  您可以从属性定义检索的信息与目录相关。
      * 
      * p>
      *  如果实现不支持模式,它应该抛出OperationNotSupportedException。如果实现不支持模式,则应定义此方法以返回适当的信息。
      * 
      * 
      * @return This attribute's schema definition. Null if the implementation
      *     supports schemas but this particular attribute does not have
      *     any schema information.
      * @exception OperationNotSupportedException If getting the schema
      *         is not supported.
      * @exception NamingException If a naming exception occurs while getting
      *         the schema.
      */
    DirContext getAttributeDefinition() throws NamingException;

    /**
      * Makes a copy of the attribute.
      * The copy contains the same attribute values as the original attribute:
      * the attribute values are not themselves cloned.
      * Changes to the copy will not affect the original and vice versa.
      *
      * <p>
      *  创建属性的副本。副本包含与原始属性相同的属性值：属性值本身不是克隆的。对副本的更改不会影响原始副本,反之亦然。
      * 
      * 
      * @return A non-null copy of the attribute.
      */
    Object clone();

    //----------- Methods to support ordered multivalued attributes

    /**
      * Determines whether this attribute's values are ordered.
      * If an attribute's values are ordered, duplicate values are allowed.
      * If an attribute's values are unordered, they are presented
      * in any order and there are no duplicate values.
      * <p>
      * 确定此属性的值是否有序。如果属性的值是有序的,则允许重复的值。如果属性的值是无序的,则它们以任何顺序出现,并且没有重复值。
      * 
      * 
      * @return true if this attribute's values are ordered; false otherwise.
      * @see #get(int)
      * @see #remove(int)
      * @see #add(int, java.lang.Object)
      * @see #set(int, java.lang.Object)
      */
    boolean isOrdered();

    /**
     * Retrieves the attribute value from the ordered list of attribute values.
     * This method returns the value at the <tt>ix</tt> index of the list of
     * attribute values.
     * If the attribute values are unordered,
     * this method returns the value that happens to be at that index.
     * <p>
     *  从属性值的有序列表中检索属性值。此方法返回属性值列表的<tt> ix </tt>索引处的值。如果属性值是无序的,则此方法将返回恰好在该索引处的值。
     * 
     * 
     * @param ix The index of the value in the ordered list of attribute values.
     * {@code 0 <= ix < size()}.
     * @return The possibly null attribute value at index <tt>ix</tt>;
     *   null if the attribute value is null.
     * @exception NamingException If a naming exception was encountered while
     * retrieving the value.
     * @exception IndexOutOfBoundsException If <tt>ix</tt> is outside the specified range.
     */
    Object get(int ix) throws NamingException;

    /**
     * Removes an attribute value from the ordered list of attribute values.
     * This method removes the value at the <tt>ix</tt> index of the list of
     * attribute values.
     * If the attribute values are unordered,
     * this method removes the value that happens to be at that index.
     * Values located at indices greater than <tt>ix</tt> are shifted up towards
     * the front of the list (and their indices decremented by one).
     *
     * <p>
     *  从属性值的有序列表中删除属性值。此方法删除属性值列表的<tt> ix </tt>索引处的值。如果属性值是无序的,则此方法将删除恰好在该索引处的值。
     * 位于大于<tt> ix </tt>的索引的值向上移动到列表的前面(并且它们的索引递减1)。
     * 
     * 
     * @param ix The index of the value to remove.
     * {@code 0 <= ix < size()}.
     * @return The possibly null attribute value at index <tt>ix</tt> that was removed;
     *   null if the attribute value is null.
     * @exception IndexOutOfBoundsException If <tt>ix</tt> is outside the specified range.
     */
    Object remove(int ix);

    /**
     * Adds an attribute value to the ordered list of attribute values.
     * This method adds <tt>attrVal</tt> to the list of attribute values at
     * index <tt>ix</tt>.
     * Values located at indices at or greater than <tt>ix</tt> are
     * shifted down towards the end of the list (and their indices incremented
     * by one).
     * If the attribute values are unordered and already have <tt>attrVal</tt>,
     * <tt>IllegalStateException</tt> is thrown.
     *
     * <p>
     *  将属性值添加到属性值的有序列表。此方法将<tt> attrVal </tt>添加到索引<tt> ix </tt>的属性值列表。
     * 位于或大于<tt> ix </tt>的索引的值向下移向列表的末尾(并且它们的索引递增1)。
     * 如果属性值是无序的,并且已经具有<tt> attrVal </tt>,则会抛出<tt> IllegalStateException </tt>。
     * 
     * 
     * @param ix The index in the ordered list of attribute values to add the new value.
     * {@code 0 <= ix <= size()}.
     * @param attrVal The possibly null attribute value to add; if null, null is
     * the value added.
     * @exception IndexOutOfBoundsException If <tt>ix</tt> is outside the specified range.
     * @exception IllegalStateException If the attribute values are unordered and
     * <tt>attrVal</tt> is one of those values.
     */
    void add(int ix, Object attrVal);


    /**
     * Sets an attribute value in the ordered list of attribute values.
     * This method sets the value at the <tt>ix</tt> index of the list of
     * attribute values to be <tt>attrVal</tt>. The old value is removed.
     * If the attribute values are unordered,
     * this method sets the value that happens to be at that index
     * to <tt>attrVal</tt>, unless <tt>attrVal</tt> is already one of the values.
     * In that case, <tt>IllegalStateException</tt> is thrown.
     *
     * <p>
     * 在属性值的有序列表中设置属性值。此方法将属性值列表的<tt> ix </tt>索引的值设置为<tt> attrVal </tt>。旧值被删除。
     * 如果属性值是无序的,则此方法将正在该索引处的值设置为<tt> attrVal </tt>,除非<tt> attrVal </tt>已经是其中一个值。
     * 在这种情况下,会抛出<tt> IllegalStateException </tt>。
     * 
     * 
     * @param ix The index of the value in the ordered list of attribute values.
     * {@code 0 <= ix < size()}.
     * @param attrVal The possibly null attribute value to use.
     * If null, 'null' replaces the old value.
     * @return The possibly null attribute value at index ix that was replaced.
     *   Null if the attribute value was null.
     * @exception IndexOutOfBoundsException If <tt>ix</tt> is outside the specified range.
     * @exception IllegalStateException If <tt>attrVal</tt> already exists and the
     *    attribute values are unordered.
     */
    Object set(int ix, Object attrVal);

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability.
     * <p>
     */
    static final long serialVersionUID = 8707690322213556804L;
}
