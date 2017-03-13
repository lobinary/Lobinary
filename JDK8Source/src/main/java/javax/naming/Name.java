/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming;

import java.util.Enumeration;

/**
 * The <tt>Name</tt> interface represents a generic name -- an ordered
 * sequence of components.  It can be a composite name (names that
 * span multiple namespaces), or a compound name (names that are
 * used within individual hierarchical naming systems).
 *
 * <p> There can be different implementations of <tt>Name</tt>; for example,
 * composite names, URLs, or namespace-specific compound names.
 *
 * <p> The components of a name are numbered.  The indexes of a name
 * with N components range from 0 up to, but not including, N.  This
 * range may be written as [0,N).
 * The most significant component is at index 0.
 * An empty name has no components.
 *
 * <p> None of the methods in this interface accept null as a valid
 * value for a parameter that is a name or a name component.
 * Likewise, methods that return a name or name component never return null.
 *
 * <p> An instance of a <tt>Name</tt> may not be synchronized against
 * concurrent multithreaded access if that access is not read-only.
 *
 * <p>
 *  <tt> Name </tt>接口表示通用名称 - 组件的有序序列它可以是复合名称(跨越多个命名空间的名称)或复合名称(在单个分层命名系统中使用的名称)
 * 
 *  <p>可以有<tt> Name </tt>的不同实现;例如,复合名称,URL或命名空间特定的复合名称
 * 
 *  <p>名称的组件被编号具有N个组件的名称的索引范围从0到但不包括N这个范围可以写为[0,N)最高有效组件位于索引0空名称没有组件
 * 
 * <p>此接口中的所有方法都不接受null作为名称或名称组件的参数的有效值。同样,返回名称或名称组件的方法不会返回null
 * 
 *  <p>如果该访问不是只读的,则<tt> Name </tt>的实例可能不会与并发多线程访问同步
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @author R. Vasudevan
 * @since 1.3
 */

public interface Name
    extends Cloneable, java.io.Serializable, Comparable<Object>
{

   /**
    * The class fingerprint that is set to indicate
    * serialization compatibility with a previous
    * version of the class.
    * <p>
    *  类指纹,设置为指示序列化与该类的先前版本的兼容性
    * 
    */
    static final long serialVersionUID = -3617482732056931635L;

    /**
     * Generates a new copy of this name.
     * Subsequent changes to the components of this name will not
     * affect the new copy, and vice versa.
     *
     * <p>
     *  生成此名称的新副本对此名称的组件的后续更改不会影响新副本,反之亦然
     * 
     * 
     * @return  a copy of this name
     *
     * @see Object#clone()
     */
    public Object clone();

    /**
     * Compares this name with another name for order.
     * Returns a negative integer, zero, or a positive integer as this
     * name is less than, equal to, or greater than the given name.
     *
     * <p> As with <tt>Object.equals()</tt>, the notion of ordering for names
     * depends on the class that implements this interface.
     * For example, the ordering may be
     * based on lexicographical ordering of the name components.
     * Specific attributes of the name, such as how it treats case,
     * may affect the ordering.  In general, two names of different
     * classes may not be compared.
     *
     * <p>
     *  将此名称与另一个名称进行比较返回一个负整数,零或正整数,因为此名称小于,等于或大于给定名称
     * 
     * <p>与<tt> Objectequals()</tt>一样,名称排序的概念取决于实现此接口的类。例如,排序可能基于名称组件的词典顺序名称的特定属性,例如它如何处理case,可能影响排序。
     * 一般来说,不同类的两个名称可能不能比较。
     * 
     * 
     * @param   obj the non-null object to compare against.
     * @return  a negative integer, zero, or a positive integer as this name
     *          is less than, equal to, or greater than the given name
     * @throws  ClassCastException if obj is not a <tt>Name</tt> of a
     *          type that may be compared with this name
     *
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Object obj);

    /**
     * Returns the number of components in this name.
     *
     * <p>
     *  返回此名称中的组件数
     * 
     * 
     * @return  the number of components in this name
     */
    public int size();

    /**
     * Determines whether this name is empty.
     * An empty name is one with zero components.
     *
     * <p>
     *  确定此名称是否为空空名称为零组件
     * 
     * 
     * @return  true if this name is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * Retrieves the components of this name as an enumeration
     * of strings.  The effect on the enumeration of updates to
     * this name is undefined.  If the name has zero components,
     * an empty (non-null) enumeration is returned.
     *
     * <p>
     *  检索此名称的组件作为字符串的枚举对此名称更新的枚举的影响未定义如果名称具有零个组件,则返回空(非空)枚举
     * 
     * 
     * @return  an enumeration of the components of this name, each a string
     */
    public Enumeration<String> getAll();

    /**
     * Retrieves a component of this name.
     *
     * <p>
     *  检索此名称的组件
     * 
     * 
     * @param posn
     *          the 0-based index of the component to retrieve.
     *          Must be in the range [0,size()).
     * @return  the component at index posn
     * @throws  ArrayIndexOutOfBoundsException
     *          if posn is outside the specified range
     */
    public String get(int posn);

    /**
     * Creates a name whose components consist of a prefix of the
     * components of this name.  Subsequent changes to
     * this name will not affect the name that is returned and vice versa.
     *
     * <p>
     * 创建其组件由此名称的组件的前缀组成的名称对此名称的后续更改不会影响返回的名称,反之亦然
     * 
     * 
     * @param posn
     *          the 0-based index of the component at which to stop.
     *          Must be in the range [0,size()].
     * @return  a name consisting of the components at indexes in
     *          the range [0,posn).
     * @throws  ArrayIndexOutOfBoundsException
     *          if posn is outside the specified range
     */
    public Name getPrefix(int posn);

    /**
     * Creates a name whose components consist of a suffix of the
     * components in this name.  Subsequent changes to
     * this name do not affect the name that is returned and vice versa.
     *
     * <p>
     *  创建一个名称,其组件由此名称中的组件的后缀组成此名称的后续更改不会影响返回的名称,反之亦然
     * 
     * 
     * @param posn
     *          the 0-based index of the component at which to start.
     *          Must be in the range [0,size()].
     * @return  a name consisting of the components at indexes in
     *          the range [posn,size()).  If posn is equal to
     *          size(), an empty name is returned.
     * @throws  ArrayIndexOutOfBoundsException
     *          if posn is outside the specified range
     */
    public Name getSuffix(int posn);

    /**
     * Determines whether this name starts with a specified prefix.
     * A name <tt>n</tt> is a prefix if it is equal to
     * <tt>getPrefix(n.size())</tt>.
     *
     * <p>
     *  确定此名称是否以指定的前缀开头如果名称<tt> n </tt>等于<tt> getPrefix(nsize())</tt>
     * 
     * 
     * @param n
     *          the name to check
     * @return  true if <tt>n</tt> is a prefix of this name, false otherwise
     */
    public boolean startsWith(Name n);

    /**
     * Determines whether this name ends with a specified suffix.
     * A name <tt>n</tt> is a suffix if it is equal to
     * <tt>getSuffix(size()-n.size())</tt>.
     *
     * <p>
     *  确定此名称是否以指定的后缀结尾如果名称<tt> n </tt>等于<tt> getSuffix(size() -  nsize())</tt>
     * 
     * 
     * @param n
     *          the name to check
     * @return  true if <tt>n</tt> is a suffix of this name, false otherwise
     */
    public boolean endsWith(Name n);

    /**
     * Adds the components of a name -- in order -- to the end of this name.
     *
     * <p>
     *  将名称的组件按顺序添加到此名称的结尾
     * 
     * 
     * @param suffix
     *          the components to add
     * @return  the updated name (not a new one)
     *
     * @throws  InvalidNameException if <tt>suffix</tt> is not a valid name,
     *          or if the addition of the components would violate the syntax
     *          rules of this name
     */
    public Name addAll(Name suffix) throws InvalidNameException;

    /**
     * Adds the components of a name -- in order -- at a specified position
     * within this name.
     * Components of this name at or after the index of the first new
     * component are shifted up (away from 0) to accommodate the new
     * components.
     *
     * <p>
     * 在此名称中的指定位置按顺序添加名称的组件在第一个新组件的索引处或之后,此名称的组件向上移动(远离0)以容纳新组件
     * 
     * 
     * @param n
     *          the components to add
     * @param posn
     *          the index in this name at which to add the new
     *          components.  Must be in the range [0,size()].
     * @return  the updated name (not a new one)
     *
     * @throws  ArrayIndexOutOfBoundsException
     *          if posn is outside the specified range
     * @throws  InvalidNameException if <tt>n</tt> is not a valid name,
     *          or if the addition of the components would violate the syntax
     *          rules of this name
     */
    public Name addAll(int posn, Name n) throws InvalidNameException;

    /**
     * Adds a single component to the end of this name.
     *
     * <p>
     *  将单个组件添加到此名称的末尾
     * 
     * 
     * @param comp
     *          the component to add
     * @return  the updated name (not a new one)
     *
     * @throws  InvalidNameException if adding <tt>comp</tt> would violate
     *          the syntax rules of this name
     */
    public Name add(String comp) throws InvalidNameException;

    /**
     * Adds a single component at a specified position within this name.
     * Components of this name at or after the index of the new component
     * are shifted up by one (away from index 0) to accommodate the new
     * component.
     *
     * <p>
     *  在此名称中的指定位置添加单个组件此名称的组件在新组件的索引处或之后向上移动一(远离索引0),以适应新组件
     * 
     * 
     * @param comp
     *          the component to add
     * @param posn
     *          the index at which to add the new component.
     *          Must be in the range [0,size()].
     * @return  the updated name (not a new one)
     *
     * @throws  ArrayIndexOutOfBoundsException
     *          if posn is outside the specified range
     * @throws  InvalidNameException if adding <tt>comp</tt> would violate
     *          the syntax rules of this name
     */
    public Name add(int posn, String comp) throws InvalidNameException;

    /**
     * Removes a component from this name.
     * The component of this name at the specified position is removed.
     * Components with indexes greater than this position
     * are shifted down (toward index 0) by one.
     *
     * <p>
     *  从此名称中删除组件在指定位置删除此名称的组件删除索引大于此位置的组件向下(向索引0)移动一
     * 
     * @param posn
     *          the index of the component to remove.
     *          Must be in the range [0,size()).
     * @return  the component removed (a String)
     *
     * @throws  ArrayIndexOutOfBoundsException
     *          if posn is outside the specified range
     * @throws  InvalidNameException if deleting the component
     *          would violate the syntax rules of the name
     */
    public Object remove(int posn) throws InvalidNameException;
}
