/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.print.attribute;

/**
 * Interface AttributeSet specifies the interface for a set of printing
 * attributes. A printing attribute is an object whose class implements
 * interface {@link Attribute Attribute}.
 * <P>
 * An attribute set contains a group of <I>attribute values,</I>
 * where duplicate values are not allowed in the set.
 * Furthermore, each value in an attribute set is
 * a member of some <I>category,</I> and at most one value in any particular
 * category is allowed in the set. For an attribute set, the values are {@link
 * Attribute Attribute} objects, and the categories are {@link java.lang.Class
 * Class} objects. An attribute's category is the class (or interface) at the
 * root of the class hierarchy for that kind of attribute. Note that an
 * attribute object's category may be a superclass of the attribute object's
 * class rather than the attribute object's class itself. An attribute
 * object's
 * category is determined by calling the {@link Attribute#getCategory()
 * getCategory()} method defined in interface {@link Attribute
 * Attribute}.
 * <P>
 * The interfaces of an AttributeSet resemble those of the Java Collections
 * API's java.util.Map interface, but is more restrictive in the types
 * it will accept, and combines keys and values into an Attribute.
 * <P>
 * Attribute sets are used in several places in the Print Service API. In
 * each context, only certain kinds of attributes are allowed to appear in the
 * attribute set, as determined by the tagging interfaces which the attribute
 * class implements -- {@link DocAttribute DocAttribute}, {@link
 * PrintRequestAttribute PrintRequestAttribute}, {@link PrintJobAttribute
 * PrintJobAttribute}, and {@link PrintServiceAttribute
 * PrintServiceAttribute}.
 * There are four specializations of an attribute set that are restricted to
 * contain just one of the four kinds of attribute -- {@link DocAttributeSet
 * DocAttributeSet}, {@link PrintRequestAttributeSet
 * PrintRequestAttributeSet},
 * {@link PrintJobAttributeSet PrintJobAttributeSet}, and {@link
 * PrintServiceAttributeSet PrintServiceAttributeSet}, respectively. Note that
 * many attribute classes implement more than one tagging interface and so may
 * appear in more than one context.
 * <UL>
 * <LI>
 * A {@link DocAttributeSet DocAttributeSet}, containing {@link DocAttribute
 * DocAttribute}s, specifies the characteristics of an individual doc and the
 * print job settings to be applied to an individual doc.
 * <P>
 * <LI>
 * A {@link PrintRequestAttributeSet PrintRequestAttributeSet}, containing
 * {@link PrintRequestAttribute PrintRequestAttribute}s, specifies the
 * settings
 * to be applied to a whole print job and to all the docs in the print job.
 * <P>
 * <LI>
 * A {@link PrintJobAttributeSet PrintJobAttributeSet}, containing {@link
 * PrintJobAttribute PrintJobAttribute}s, reports the status of a print job.
 * <P>
 * <LI>
 * A {@link PrintServiceAttributeSet PrintServiceAttributeSet}, containing
 * {@link PrintServiceAttribute PrintServiceAttribute}s, reports the status of
 *  a Print Service instance.
 * </UL>
 * <P>
 * In some contexts, the client is only allowed to examine an attribute set's
 * contents but not change them (the set is read-only). In other places, the
 * client is allowed both to examine and to change an attribute set's contents
 * (the set is read-write). For a read-only attribute set, calling a mutating
 * operation throws an UnmodifiableSetException.
 * <P>
 * The Print Service API provides one implementation of interface
 * AttributeSet, class {@link HashAttributeSet HashAttributeSet}.
 * A client can use class {@link
 * HashAttributeSet HashAttributeSet} or provide its own implementation of
 * interface AttributeSet. The Print Service API also provides
 * implementations of interface AttributeSet's subinterfaces -- classes {@link
 * HashDocAttributeSet HashDocAttributeSet},
 * {@link HashPrintRequestAttributeSet
 * HashPrintRequestAttributeSet}, {@link HashPrintJobAttributeSet
 * HashPrintJobAttributeSet}, and {@link HashPrintServiceAttributeSet
 * HashPrintServiceAttributeSet}.
 * <P>
 *
 * <p>
 *  Interface AttributeSet指定一组打印属性的接口。打印属性是其类实现接口{@link属性属性}的对象。
 * <P>
 *  属性集包含一组<I>属性值</I>,其中集合中不允许重复值。此外,属性集中的每个值是某些<I>类别的成员,</I>并且在集合中允许任何特定类别中的至多一个值。
 * 对于属性集,值为{@link Attribute Attribute}对象,类别为{@link java.lang.Class Class}对象。
 * 属性的类别是该类型属性的类层次结构根目录下的类(或接口)。注意,属性对象的类别可以是属性对象的类的超类,而不是属性对象的类本身。
 * 属性对象的类别通过调用在接口{@link Attribute Attribute}中定义的{@link Attribute#getCategory()getCategory()}方法确定。
 * <P>
 *  AttributeSet的接口类似于Java Collections API的java.util.Map接口的接口,但在它接受的类型中限制性更强,并且将键和值组合到属性中。
 * <P>
 * 属性集用于打印服务API中的多个位置。
 * 在每个上下文中,只有某些种类的属性被允许出现在属性集中,如属性类所实现的标记接口所确定的 -  {@link DocAttribute DocAttribute},{@link PrintRequestAttribute PrintRequestAttribute}
 * ,{@link PrintJobAttribute PrintJobAttribute }和{@link PrintServiceAttribute PrintServiceAttribute}。
 * 属性集用于打印服务API中的多个位置。
 * 属性集有四种特殊化,它们仅限于包含四种属性之一 -  {@link DocAttributeSet DocAttributeSet},{@link PrintRequestAttributeSet PrintRequestAttributeSet}
 * ,{@link PrintJobAttributeSet PrintJobAttributeSet}和{@link PrintServiceAttributeSet PrintServiceAttributeSet }
 * , 分别。
 * 属性集用于打印服务API中的多个位置。请注意,许多属性类实现了多个标记接口,因此可能出现在多个上下文中。
 * <UL>
 * <LI>
 *  包含{@link DocAttribute DocAttribute}的{@link DocAttributeSet DocAttributeSet}指定单个文档的特征以及要应用于单个文档的打印作业设
 * 置。
 * <P>
 * <LI>
 *  包含{@link PrintRequestAttribute PrintRequestAttribute}的{@link PrintRequestAttributeSet PrintRequestAttributeSet}
 * 指定要应用于整个打印作业和打印作业中的所有文档的设置。
 * <P>
 * <LI>
 *  包含{@link PrintJobAttribute PrintJobAttribute}的{@link PrintJobAttributeSet PrintJobAttributeSet}报告打印作
 * 业的状态。
 * <P>
 * <LI>
 * 包含{@link PrintServiceAttribute PrintServiceAttribute}的{@link PrintServiceAttributeSet PrintServiceAttributeSet}
 * 报告打印服务实例的状态。
 * </UL>
 * <P>
 *  在某些上下文中,客户端只允许检查属性集的内容,但不能更改它们(集是只读的)。在其他地方,允许客户端检查和更改属性集的内容(集是读写)。
 * 对于只读属性集,调用mutating操作会抛出UnmodifiableSetException异常。
 * <P>
 *  打印服务API提供接口AttributeSet,类{@link HashAttributeSet HashAttributeSet}的一个实现。
 * 客户端可以使用类{@link HashAttributeSet HashAttributeSet}或提供自己的接口AttributeSet的实现。
 * 打印服务API还提供了接口AttributeSet的子接口 - 类{@link HashDocAttributeSet HashDocAttributeSet},{@link HashPrintRequestAttributeSet HashPrintRequestAttributeSet}
 * ,{@link HashPrintJobAttributeSet HashPrintJobAttributeSet}和{@link HashPrintServiceAttributeSet HashPrintServiceAttributeSet}
 * 的实现。
 * 客户端可以使用类{@link HashAttributeSet HashAttributeSet}或提供自己的接口AttributeSet的实现。
 * <P>
 * 
 * 
 * @author  Alan Kaminsky
 */
public interface AttributeSet {


    /**
     * Returns the attribute value which this attribute set contains in the
     * given attribute category. Returns <tt>null</tt> if this attribute set
     * does not contain any attribute value in the given attribute category.
     *
     * <p>
     *  返回此属性集在给定属性类别中包含的属性值。如果此属性集不包含给定属性类别中的任何属性值,则返回<tt> null </tt>。
     * 
     * 
     * @param  category  Attribute category whose associated attribute value
     *                   is to be returned. It must be a
     *                   {@link java.lang.Class Class}
     *                   that implements interface {@link Attribute
     *                   Attribute}.
     *
     * @return  The attribute value in the given attribute category contained
     *          in this attribute set, or <tt>null</tt> if this attribute set
     *          does not contain any attribute value in the given attribute
     *          category.
     *
     * @throws  NullPointerException
     *     (unchecked exception) Thrown if the <CODE>category</CODE> is null.
     * @throws  ClassCastException
     *     (unchecked exception) Thrown if the <CODE>category</CODE> is not a
     *     {@link java.lang.Class Class} that implements interface {@link
     *     Attribute Attribute}.
     */
    public Attribute get(Class<?> category);

    /**
     * Adds the specified attribute to this attribute set if it is not
     * already present, first removing any existing value in the same
     * attribute category as the specified attribute value.
     *
     * <p>
     * 将指定的属性添加到此属性集(如果它尚未存在),首先删除与指定属性值相同的属性类别中的任何现有值。
     * 
     * 
     * @param  attribute  Attribute value to be added to this attribute set.
     *
     * @return  <tt>true</tt> if this attribute set changed as a result of the
     *          call, i.e., the given attribute value was not already a member
     *          of this attribute set.
     *
     * @throws  NullPointerException
     *     (unchecked exception) Thrown if the <CODE>attribute</CODE> is null.
     * @throws  UnmodifiableSetException
     *     (unchecked exception) Thrown if this attribute set does not support
     *     the <CODE>add()</CODE> operation.
     */
    public boolean add(Attribute attribute);


    /**
     * Removes any attribute for this category from this attribute set if
     * present. If <CODE>category</CODE> is null, then
     * <CODE>remove()</CODE> does nothing and returns <tt>false</tt>.
     *
     * <p>
     *  从此属性集中删除此类别的任何属性(如果存在)。
     * 如果<CODE> category </CODE>为null,则<CODE> remove()</CODE>不执行任何操作,并返回<tt> false </tt>。
     * 
     * 
     * @param  category Attribute category to be removed from this
     *                  attribute set.
     *
     * @return  <tt>true</tt> if this attribute set changed as a result of the
     *         call, i.e., the given attribute value had been a member of this
     *          attribute set.
     *
     * @throws  UnmodifiableSetException
     *     (unchecked exception) Thrown if this attribute set does not support
     *     the <CODE>remove()</CODE> operation.
     */
    public boolean remove(Class<?> category);

    /**
     * Removes the specified attribute from this attribute set if
     * present. If <CODE>attribute</CODE> is null, then
     * <CODE>remove()</CODE> does nothing and returns <tt>false</tt>.
     *
     * <p>
     *  从此属性集中删除指定的属性(如果存在)。如果<CODE>属性</CODE>为空,则<CODE> remove()</CODE>不执行任何操作,并返回<tt> false </tt>。
     * 
     * 
     * @param  attribute Attribute value to be removed from this attribute set.
     *
     * @return  <tt>true</tt> if this attribute set changed as a result of the
     *         call, i.e., the given attribute value had been a member of this
     *          attribute set.
     *
     * @throws  UnmodifiableSetException
     *     (unchecked exception) Thrown if this attribute set does not support
     *     the <CODE>remove()</CODE> operation.
     */
    public boolean remove(Attribute attribute);

    /**
     * Returns <tt>true</tt> if this attribute set contains an
     * attribute for the specified category.
     *
     * <p>
     *  如果此属性集包含指定类别的属性,则返回<tt> true </tt>。
     * 
     * 
     * @param  category whose presence in this attribute set is
     *            to be tested.
     *
     * @return  <tt>true</tt> if this attribute set contains an attribute
     *         value for the specified category.
     */
    public boolean containsKey(Class<?> category);

    /**
     * Returns <tt>true</tt> if this attribute set contains the given
     * attribute value.
     *
     * <p>
     *  如果此属性集包含给定的属性值,则返回<tt> true </tt>。
     * 
     * 
     * @param  attribute  Attribute value whose presence in this
     * attribute set is to be tested.
     *
     * @return  <tt>true</tt> if this attribute set contains the given
     *      attribute  value.
     */
    public boolean containsValue(Attribute attribute);

    /**
     * Adds all of the elements in the specified set to this attribute.
     * The outcome is the same as if the =
     * {@link #add(Attribute) add(Attribute)}
     * operation had been applied to this attribute set successively with each
     * element from the specified set.
     * The behavior of the <CODE>addAll(AttributeSet)</CODE>
     * operation is unspecified if the specified set is modified while
     * the operation is in progress.
     * <P>
     * If the <CODE>addAll(AttributeSet)</CODE> operation throws an exception,
     * the effect on this attribute set's state is implementation dependent;
     * elements from the specified set before the point of the exception may
     * or may not have been added to this attribute set.
     *
     * <p>
     *  将指定集合中的所有元素添加到此属性。结果是相同的,如果= {@link #add(Attribute)add(Attribute)}操作已经被应用于与来自指定集合的​​每个元素连续的该属性集。
     * 如果在操作正在进行时修改指定的集合,则未指定<CODE> addAll(AttributeSet)</CODE>操作的行为。
     * <P>
     *  如果<CODE> addAll(AttributeSet)</CODE>操作抛出异常,对此属性集状态的影响取决于实现;元素从指定集合之前的异常点可能或可能没有添加到此属性集。
     * 
     * 
     * @param  attributes  whose elements are to be added to this attribute
     *            set.
     *
     * @return  <tt>true</tt> if this attribute set changed as a result of the
     *          call.
     *
     * @throws  UnmodifiableSetException
     *     (Unchecked exception) Thrown if this attribute set does not support
     *     the <tt>addAll(AttributeSet)</tt> method.
     * @throws  NullPointerException
     *     (Unchecked exception) Thrown if some element in the specified
     *     set is null.
     *
     * @see #add(Attribute)
     */
    public boolean addAll(AttributeSet attributes);

    /**
     * Returns the number of attributes in this attribute set. If this
     * attribute set contains more than <tt>Integer.MAX_VALUE</tt> elements,
     * returns  <tt>Integer.MAX_VALUE</tt>.
     *
     * <p>
     * 返回此属性集中的属性数。如果此属性集包含多于<tt> Integer.MAX_VALUE </tt>个元素,则返回<tt> Integer.MAX_VALUE </tt>。
     * 
     * 
     * @return  The number of attributes in this attribute set.
     */
    public int size();

    /**
     * Returns an array of the attributes contained in this set.
     * <p>
     *  返回此集合中包含的属性的数组。
     * 
     * 
     * @return the Attributes contained in this set as an array, zero length
     * if the AttributeSet is empty.
     */
    public Attribute[] toArray();


    /**
     * Removes all attributes from this attribute set.
     *
     * <p>
     *  从此属性集中删除所有属性。
     * 
     * 
     * @throws  UnmodifiableSetException
     *   (unchecked exception) Thrown if this attribute set does not support
     *     the <CODE>clear()</CODE> operation.
     */
    public void clear();

    /**
     * Returns true if this attribute set contains no attributes.
     *
     * <p>
     *  如果此属性集不包含属性,则返回true。
     * 
     * 
     * @return true if this attribute set contains no attributes.
     */
    public boolean isEmpty();

    /**
     * Compares the specified object with this attribute set for equality.
     * Returns <tt>true</tt> if the given object is also an attribute set and
     * the two attribute sets contain the same attribute category-attribute
     * value mappings. This ensures that the
     * <tt>equals()</tt> method works properly across different
     * implementations of the AttributeSet interface.
     *
     * <p>
     *  将指定对象与为相等性设置的此属性进行比较。如果给定对象也是属性集,并且两个属性集包含相同的属性类别属性值映射,则返回<tt> true </tt>。
     * 这可以确保<tt> equals()</tt>方法在AttributeSet接口的不同实现中正常工作。
     * 
     * 
     * @param  object to be compared for equality with this attribute set.
     *
     * @return  <tt>true</tt> if the specified object is equal to this
     *       attribute   set.
     */
    public boolean equals(Object object);

    /**
     * Returns the hash code value for this attribute set. The hash code of an
     * attribute set is defined to be the sum of the hash codes of each entry
     * in the AttributeSet.
     * This ensures that <tt>t1.equals(t2)</tt> implies that
     * <tt>t1.hashCode()==t2.hashCode()</tt> for any two attribute sets
     * <tt>t1</tt> and <tt>t2</tt>, as required by the general contract of
     * {@link java.lang.Object#hashCode() Object.hashCode()}.
     *
     * <p>
     *  返回此属性集的哈希码值。属性集的哈希码被定义为属性集中每个条目的哈希码的总和。
     * 这确保对于任何两个属性集<tt> t1 </tt>,<tt> t1.equals(t2)</tt>意味着<tt> t1.hashCode()== t2.hashCode和{@link java.lang.Object#hashCode()Object.hashCode()}
     * 的一般合同所要求的<tt> t2 </tt>。
     * 
     * @return  The hash code value for this attribute set.
     */
    public int hashCode();

}
