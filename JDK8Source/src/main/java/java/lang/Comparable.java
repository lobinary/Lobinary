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

package java.lang;
import java.util.*;

/**
 * This interface imposes a total ordering on the objects of each class that
 * implements it.  This ordering is referred to as the class's <i>natural
 * ordering</i>, and the class's <tt>compareTo</tt> method is referred to as
 * its <i>natural comparison method</i>.<p>
 *
 * Lists (and arrays) of objects that implement this interface can be sorted
 * automatically by {@link Collections#sort(List) Collections.sort} (and
 * {@link Arrays#sort(Object[]) Arrays.sort}).  Objects that implement this
 * interface can be used as keys in a {@linkplain SortedMap sorted map} or as
 * elements in a {@linkplain SortedSet sorted set}, without the need to
 * specify a {@linkplain Comparator comparator}.<p>
 *
 * The natural ordering for a class <tt>C</tt> is said to be <i>consistent
 * with equals</i> if and only if <tt>e1.compareTo(e2) == 0</tt> has
 * the same boolean value as <tt>e1.equals(e2)</tt> for every
 * <tt>e1</tt> and <tt>e2</tt> of class <tt>C</tt>.  Note that <tt>null</tt>
 * is not an instance of any class, and <tt>e.compareTo(null)</tt> should
 * throw a <tt>NullPointerException</tt> even though <tt>e.equals(null)</tt>
 * returns <tt>false</tt>.<p>
 *
 * It is strongly recommended (though not required) that natural orderings be
 * consistent with equals.  This is so because sorted sets (and sorted maps)
 * without explicit comparators behave "strangely" when they are used with
 * elements (or keys) whose natural ordering is inconsistent with equals.  In
 * particular, such a sorted set (or sorted map) violates the general contract
 * for set (or map), which is defined in terms of the <tt>equals</tt>
 * method.<p>
 *
 * For example, if one adds two keys <tt>a</tt> and <tt>b</tt> such that
 * {@code (!a.equals(b) && a.compareTo(b) == 0)} to a sorted
 * set that does not use an explicit comparator, the second <tt>add</tt>
 * operation returns false (and the size of the sorted set does not increase)
 * because <tt>a</tt> and <tt>b</tt> are equivalent from the sorted set's
 * perspective.<p>
 *
 * Virtually all Java core classes that implement <tt>Comparable</tt> have natural
 * orderings that are consistent with equals.  One exception is
 * <tt>java.math.BigDecimal</tt>, whose natural ordering equates
 * <tt>BigDecimal</tt> objects with equal values and different precisions
 * (such as 4.0 and 4.00).<p>
 *
 * For the mathematically inclined, the <i>relation</i> that defines
 * the natural ordering on a given class C is:<pre>
 *       {(x, y) such that x.compareTo(y) &lt;= 0}.
 * </pre> The <i>quotient</i> for this total order is: <pre>
 *       {(x, y) such that x.compareTo(y) == 0}.
 * </pre>
 *
 * It follows immediately from the contract for <tt>compareTo</tt> that the
 * quotient is an <i>equivalence relation</i> on <tt>C</tt>, and that the
 * natural ordering is a <i>total order</i> on <tt>C</tt>.  When we say that a
 * class's natural ordering is <i>consistent with equals</i>, we mean that the
 * quotient for the natural ordering is the equivalence relation defined by
 * the class's {@link Object#equals(Object) equals(Object)} method:<pre>
 *     {(x, y) such that x.equals(y)}. </pre><p>
 *
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * <p>
 *  此接口对实现它的每个类的对象施加总排序。这种排序被称为类的<i>自然排序</i>,并且类的<tt> compareTo </tt>方法被称为其<i>自然比较方法</i>。<p>
 * 
 *  实现此接口的对象的列表(和数组)可以通过{@link Collections#sort(List)Collections.sort}(和{@link Arrays#sort(Object [])Arrays.sort}
 * )自动排序。
 * 实现此接口的对象可以用作{@linkplain SortedMap sorted map}中的键或作为{@linkplain SortedSet sorted set}中的元素,而无需指定{@linkplain比较器比较器}
 * 。
 * <p>。
 * 
 *  当且仅当<tt> e1.compareTo(e2)== 0 </tt>具有<tt> C </tt>时,类<tt> C </tt>的自然排序被认为与</i>与类<tt> C </tt>的每个<tt> e
 * 1 </tt>和<tt> e2 </tt>相同的布尔值<tt> e1.equals(e2)</tt>。
 * 请注意,<tt> null </tt>不是任何类的实例,<tt> e.compareTo(null)</tt>应该抛出一个<tt> NullPointerException </tt> equals(n
 * ull)</tt>返回<tt> false </tt>。
 * <p>。
 * 
 * 强烈推荐(虽然不是必需的)自然排序与等号一致。这是因为没有显式比较器的排序集(和排序映射)在使用其自然排序与equals不一致的元素(或键)时表现为"奇怪"。
 * 特别地,这样的排序集合(或排序映射)违反了集合(或映射)的一般契约,其根据<tt>等于</tt>方法定义。<p>。
 * 
 *  例如,如果添加两个键<tt> a </tt>和<tt> b </tt>,使得{@code(！a.equals(b)&& a.compareTo(b)== 0)}对于不使用显式比较器的排序集,第二个<tt>
 *  add </tt>操作返回false(并且排序集的大小不增加),因为<tt> a </tt>和<tt> b </tt>等价于排序集的透视图。
 * <p>。
 * 
 *  事实上,实现<tt> Comparable </tt>的所有Java核心类都具有与equals一致的自然排序。
 * 一个例外是<tt> java.math.BigDecimal </tt>,其自然排序等于<tt> BigDecimal </tt>对象具有相等的值和不同的精度(如4.0和4.00)。<p>。
 * 
 *  对于数学倾斜,定义给定类C上的自然排序的<i>关系</i>是：<pre> {(x,y),使得x.compareTo(y)<= 0}。
 *  </pre> </pre> </i>对于这个总订单：<pre> {(x,y),使得x.compareTo(y)== 0}。
 * </pre>
 * 
 * 
 * @param <T> the type of objects that this object may be compared to
 *
 * @author  Josh Bloch
 * @see java.util.Comparator
 * @since 1.2
 */
public interface Comparable<T> {
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * <p>
     * 它紧跟从<tt> compareTo </tt>的合同开始,商是<tt> C </tt>上的<i>等价关系</i>,并且自然排序是<i> order </i> on <tt> C </tt>。
     * 当我们说一个类的自然排序<i>与equals </i>一致时,我们的意思是自然排序的商是由类的{@link Object#equals(Object)equals(Object) }方法：<pre> {(x,y),使得x.equals(y)}
     * 。
     * 它紧跟从<tt> compareTo </tt>的合同开始,商是<tt> C </tt>上的<i>等价关系</i>,并且自然排序是<i> order </i> on <tt> C </tt>。
     *  </pre> <p>。
     * 
     *  这个接口是成员
     * <a href="{@docRoot}/../technotes/guides/collections/index.html">
     *  Java集合框架</a>。
     * 
     * 
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */
    public int compareTo(T o);
}
