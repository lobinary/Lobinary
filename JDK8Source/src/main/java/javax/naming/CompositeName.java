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

package javax.naming;

import java.util.Enumeration;
import java.util.Properties;

/**
 * This class represents a composite name -- a sequence of
 * component names spanning multiple namespaces.
 * Each component is a string name from the namespace of a
 * naming system. If the component comes from a hierarchical
 * namespace, that component can be further parsed into
 * its atomic parts by using the CompoundName class.
 *<p>
 * The components of a composite name are numbered.  The indexes of a
 * composite name with N components range from 0 up to, but not including, N.
 * This range may be written as [0,N).
 * The most significant component is at index 0.
 * An empty composite name has no components.
 *
 * <h1>JNDI Composite Name Syntax</h1>
 * JNDI defines a standard string representation for composite names. This
 * representation is the concatenation of the components of a composite name
 * from left to right using the component separator (a forward
 * slash character (/)) to separate each component.
 * The JNDI syntax defines the following meta characters:
 * <ul>
 * <li>escape (backward slash \),
 * <li>quote characters  (single (') and double quotes (")), and
 * <li>component separator (forward slash character (/)).
 * </ul>
 * Any occurrence of a leading quote, an escape preceding any meta character,
 * an escape at the end of a component, or a component separator character
 * in an unquoted component must be preceded by an escape character when
 * that component is being composed into a composite name string.
 * Alternatively, to avoid adding escape characters as described,
 * the entire component can be quoted using matching single quotes
 * or matching double quotes. A single quote occurring within a double-quoted
 * component is not considered a meta character (and need not be escaped),
 * and vice versa.
 *<p>
 * When two composite names are compared, the case of the characters
 * is significant.
 *<p>
 * A leading component separator (the composite name string begins with
 * a separator) denotes a leading empty component (a component consisting
 * of an empty string).
 * A trailing component separator (the composite name string ends with
 * a separator) denotes a trailing empty component.
 * Adjacent component separators denote an empty component.
 *
 *<h1>Composite Name Examples</h1>
 *This table shows examples of some composite names. Each row shows
 *the string form of a composite name and its corresponding structural form
 *(<tt>CompositeName</tt>).
 *
<table border="1" cellpadding=3 summary="examples showing string form of composite name and its corresponding structural form (CompositeName)">

<tr>
<th>String Name</th>
<th>CompositeName</th>
</tr>

<tr>
<td>
""
</td>
<td>{} (the empty name == new CompositeName("") == new CompositeName())
</td>
</tr>

<tr>
<td>
"x"
</td>
<td>{"x"}
</td>
</tr>

<tr>
<td>
"x/y"
</td>
<td>{"x", "y"}</td>
</tr>

<tr>
<td>"x/"</td>
<td>{"x", ""}</td>
</tr>

<tr>
<td>"/x"</td>
<td>{"", "x"}</td>
</tr>

<tr>
<td>"/"</td>
<td>{""}</td>
</tr>

<tr>
<td>"//"</td>
<td>{"", ""}</td>
</tr>

<tr><td>"/x/"</td>
<td>{"", "x", ""}</td>
</tr>

<tr><td>"x//y"</td>
<td>{"x", "", "y"}</td>
</tr>
</table>
 *
 *<h1>Composition Examples</h1>
 * Here are some composition examples.  The right column shows composing
 * string composite names while the left column shows composing the
 * corresponding <tt>CompositeName</tt>s.  Notice that composing the
 * string forms of two composite names simply involves concatenating
 * their string forms together.

<table border="1" cellpadding=3 summary="composition examples showing string names and composite names">

<tr>
<th>String Names</th>
<th>CompositeNames</th>
</tr>

<tr>
<td>
"x/y"           + "/"   = x/y/
</td>
<td>
{"x", "y"}      + {""}  = {"x", "y", ""}
</td>
</tr>

<tr>
<td>
""              + "x"   = "x"
</td>
<td>
{}              + {"x"} = {"x"}
</td>
</tr>

<tr>
<td>
"/"             + "x"   = "/x"
</td>
<td>
{""}            + {"x"} = {"", "x"}
</td>
</tr>

<tr>
<td>
"x"   + ""      + ""    = "x"
</td>
<td>
{"x"} + {}      + {}    = {"x"}
</td>
</tr>

</table>
 *
 *<h1>Multithreaded Access</h1>
 * A <tt>CompositeName</tt> instance is not synchronized against concurrent
 * multithreaded access. Multiple threads trying to access and modify a
 * <tt>CompositeName</tt> should lock the object.
 *
 * <p>
 *  此类表示复合名称 - 跨多个命名空间的组件名称序列每个组件都是来自命名系统的命名空间的字符串名称如果组件来自分层命名空间,则该组件可以进一步解析为其原子部分,方法是使用CompoundName类
 * p>
 *  复合名称的组成部分被编号具有N个分量的复合名称的索引范围从0到但不包括N这个范围可以写为[0,N]最高有效分量在索引0处空复合名称没有组件
 * 
 * <h1> JNDI复合名称语法</h1> JNDI定义复合名称的标准字符串表示形式此表示形式是复合名称的组件从左到右使用组件分隔符(正斜杠字符(/))连接到分隔每个组件JNDI语法定义以下元字符：
 * <ul>
 *  <li>转义(反斜线\\),<li>引号字符(单引号(')和双引号('))和<li>
 * </ul>
 * 任何出现的引导引用,任何元字符之前的转义,组件末尾的转义或非引用的组件中的组件分隔符字符必须在转义字符之前,当该组件被组成复合名称字符串或者,为了避免如上所述添加转义字符,可以使用匹配的单引号或匹配的双
 * 引号引用整个组件。
 * 双引号组件中出现的单引号不视为元字符(并且不需要转义),而反之亦然。
 * p>
 *  当比较两个复合名称时,字符的大小写很大
 * p>
 * 前导分量分隔符(复合名称字符串以分隔符开头)表示前导空分量(由空字符串组成的分量)尾部分量分隔符(复合名称字符串以分隔符结尾)表示尾部空分量邻近分量分隔符表示空组件
 * 
 *  h1>复合名称示例</h1> his表显示一些复合名称的示例每行显示复合名称的字符串形式及其对应的结构形式<tt> CompositeName </tt>)
 * 
 * <table border="1" cellpadding=3 summary="examples showing string form of composite name and its corresponding structural form (CompositeName)">
 * 
 * <tr>
 *  <th>字符串名称</th> <th> CompositeName </th>
 * </tr>
 * 
 * <tr>
 * <td>
 *  ""
 * </td>
 *  <td> {}(空名称== new CompositeName("")== new CompositeName())
 * </td>
 * </tr>
 * 
 * <tr>
 * <td>
 *  "X"
 * </td>
 *  <td> {"x"}
 * </td>
 * </tr>
 * 
 * <tr>
 * <td>
 *  "x / y"
 * </td>
 *  <td> {"x","y"} </td>
 * </tr>
 * 
 * <tr>
 *  <td>"x /"</td> <td> {"x",""} </td>
 * </tr>
 * 
 * <tr>
 *  <td>"/ x"</td> <td> {"","x"} </td>
 * </tr>
 * 
 * <tr>
 *  <td>"/"</td> <td> {""} </td>
 * </tr>
 * 
 * <tr>
 * <td>"//"</td> <td> {"",""} </td>
 * </tr>
 * 
 *  <tr> <td>"/ x /"</td> <td> {"","x",""} </td>
 * </tr>
 * 
 *  <tr> <td>"x // y"</td> <td> {"x","","y"} </td>
 * </tr>
 * </table>
 * 
 *  h1>组合示例</h1>这里是一些组合示例右列显示组合字符串组合名称,左列显示组成相应的<tt> CompositeName </tt>注意,组成两个组合名称的字符串形式仅涉及将它们的字符串形式连接在
 * 一起。
 * 
 * <table border="1" cellpadding=3 summary="composition examples showing string names and composite names">
 * 
 * <tr>
 *  <th>字符串名称</th> <th> CompositeNames </th>
 * </tr>
 * 
 * <tr>
 * <td>
 *  "x / y"+"/"= x / y /
 * </td>
 * <td>
 *  {"x","y"} + {""} = {"x","y",""}
 * </td>
 * </tr>
 * 
 * <tr>
 * <td>
 *  ""+"x"="x"
 * </td>
 * <td>
 *  {} + {"x"} = {"x"}
 * </td>
 * </tr>
 * 
 * <tr>
 * <td>
 *  "/"+"x"="/ x"
 * </td>
 * <td>
 *  {""} + {"x"} = {"","x"}
 * </td>
 * </tr>
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @since 1.3
 */


public class CompositeName implements Name {

    private transient NameImpl impl;
    /**
      * Constructs a new composite name instance using the components
      * specified by 'comps'. This protected method is intended to be
      * to be used by subclasses of CompositeName when they override
      * methods such as clone(), getPrefix(), getSuffix().
      *
      * <p>
      * 
      * <tr>
      * <td>
      *  "x"+""+""="x"
      * </td>
      * <td>
      *  {"x"} + {} + {} = {"x"}
      * </td>
      * </tr>
      * 
      * </table>
      * 
      * h1>多线程访问</h1> <tt> CompositeName </tt>实例未与并发多线程访问同步多个尝试访问和修改<tt> CompositeName </tt>的线程应锁定对象
      * 
      * 
      * @param comps A non-null enumeration containing the components for the new
      *              composite name. Each element is of class String.
      *               The enumeration will be consumed to extract its
      *               elements.
      */
    protected CompositeName(Enumeration<String> comps) {
        impl = new NameImpl(null, comps); // null means use default syntax
    }

    /**
      * Constructs a new composite name instance by parsing the string n
      * using the composite name syntax (left-to-right, slash separated).
      * The composite name syntax is described in detail in the class
      * description.
      *
      * <p>
      *  使用'comps'指定的组件构造新的复合名称实例此受保护的方法旨在由CompositeName的子类使用,当它们覆盖诸如clone(),getPrefix(),getSuffix()
      * 
      * 
      * @param  n       The non-null string to parse.
      * @exception InvalidNameException If n has invalid composite name syntax.
      */
    public CompositeName(String n) throws InvalidNameException {
        impl = new NameImpl(null, n);  // null means use default syntax
    }

    /**
      * Constructs a new empty composite name. Such a name returns true
      * when <code>isEmpty()</code> is invoked on it.
      * <p>
      *  通过使用复合名称语法(从左到右,斜杠分隔)解析字符串n构造新的复合名称实例复合名称语法在类描述中详细描述
      * 
      */
    public CompositeName() {
        impl = new NameImpl(null);  // null means use default syntax
    }

    /**
      * Generates the string representation of this composite name.
      * The string representation consists of enumerating in order
      * each component of the composite name and separating
      * each component by a forward slash character. Quoting and
      * escape characters are applied where necessary according to
      * the JNDI syntax, which is described in the class description.
      * An empty component is represented by an empty string.
      *
      * The string representation thus generated can be passed to
      * the CompositeName constructor to create a new equivalent
      * composite name.
      *
      * <p>
      *  构造一个新的空复合名称当<code> isEmpty()</code>被调用时,这样的名称返回true
      * 
      * 
      * @return A non-null string representation of this composite name.
      */
    public String toString() {
        return impl.toString();
    }

    /**
      * Determines whether two composite names are equal.
      * If obj is null or not a composite name, false is returned.
      * Two composite names are equal if each component in one is equal
      * to the corresponding component in the other. This implies
      * both have the same number of components, and each component's
      * equals() test against the corresponding component in the other name
      * returns true.
      *
      * <p>
      * 生成此复合名称的字符串表示形式字符串表示形式包括按顺序枚举复合名称中的每个组件,并使用正斜杠字符分隔每个组件。根据JNDI语法,在必要时应用引用和转义字符,该语法在类描述空组件由空字符串表示
      * 
      *  这样生成的字符串表示可以传递给CompositeName构造函数,以创建一个新的等效复合名称
      * 
      * 
      * @param  obj     The possibly null object to compare against.
      * @return true if obj is equal to this composite name, false otherwise.
      * @see #hashCode
      */
    public boolean equals(Object obj) {
        return (obj != null &&
                obj instanceof CompositeName &&
                impl.equals(((CompositeName)obj).impl));
    }

    /**
      * Computes the hash code of this composite name.
      * The hash code is the sum of the hash codes of individual components
      * of this composite name.
      *
      * <p>
      * 确定两个复合名称是否相等如果obj为null或不是复合名称,则返回false如果每个组件在一个组件中等于其他组件,则两个组合名称相等这意味着两个组件具有相同数量的组件,每个组件的equals()测试与其
      * 他名称中的相应组件返回true。
      * 
      * 
      * @return An int representing the hash code of this name.
      * @see #equals
      */
    public int hashCode() {
        return impl.hashCode();
    }


    /**
     * Compares this CompositeName with the specified Object for order.
     * Returns a
     * negative integer, zero, or a positive integer as this Name is less
     * than, equal to, or greater than the given Object.
     * <p>
     * If obj is null or not an instance of CompositeName, ClassCastException
     * is thrown.
     * <p>
     * See equals() for what it means for two composite names to be equal.
     * If two composite names are equal, 0 is returned.
     * <p>
     * Ordering of composite names follows the lexicographical rules for
     * string comparison, with the extension that this applies to all
     * the components in the composite name. The effect is as if all the
     * components were lined up in their specified ordered and the
     * lexicographical rules applied over the two line-ups.
     * If this composite name is "lexicographically" lesser than obj,
     * a negative number is returned.
     * If this composite name is "lexicographically" greater than obj,
     * a positive number is returned.
     * <p>
     *  计算此复合名称的哈希码哈希码是此复合名称的各个组件的哈希码的总和
     * 
     * 
     * @param obj The non-null object to compare against.
     *
     * @return  a negative integer, zero, or a positive integer as this Name
     *          is less than, equal to, or greater than the given Object.
     * @exception ClassCastException if obj is not a CompositeName.
     */
    public int compareTo(Object obj) {
        if (!(obj instanceof CompositeName)) {
            throw new ClassCastException("Not a CompositeName");
        }
        return impl.compareTo(((CompositeName)obj).impl);
    }

    /**
      * Generates a copy of this composite name.
      * Changes to the components of this composite name won't
      * affect the new copy and vice versa.
      *
      * <p>
      *  将此CompositeName与指定的对象进行比较返回一个负整数,零或正整数,因为此名称小于,等于或大于给定对象
      * <p>
      *  如果obj为null或不是CompositeName的实例,则抛出ClassCastException
      * <p>
      * 请参阅equals()了解两个复合名称是否相等如果两个复合名称相等,则返回0
      * <p>
      *  复合名称的排序遵循用于字符串比较的词典规则,其扩展名适用于复合名称中的所有组件。
      * 效果好像所有组件按照其指定顺序排列,并且通过两行应用词典规则-ups如果此复合名称"按字典顺序"小于obj,则返回负数如果此复合名称"按字典顺序"大于obj,则返回正数。
      * 
      * 
      * @return A non-null copy of this composite name.
      */
    public Object clone() {
        return (new CompositeName(getAll()));
    }

    /**
      * Retrieves the number of components in this composite name.
      *
      * <p>
      *  生成此复合名称的副本更改此复合名称的组件不会影响新副本,反之亦然
      * 
      * 
      * @return The nonnegative number of components in this composite name.
      */
    public int size() {
        return (impl.size());
    }

    /**
      * Determines whether this composite name is empty. A composite name
      * is empty if it has zero components.
      *
      * <p>
      * 检索此复合名称中的组件数
      * 
      * 
      * @return true if this composite name is empty, false otherwise.
      */
    public boolean isEmpty() {
        return (impl.isEmpty());
    }

    /**
      * Retrieves the components of this composite name as an enumeration
      * of strings.
      * The effects of updates to this composite name on this enumeration
      * is undefined.
      *
      * <p>
      *  确定此组合名称是否为空如果组合名称为零,则组合名称为空
      * 
      * 
      * @return A non-null enumeration of the components of
      *         this composite name. Each element of the enumeration is of
      *         class String.
      */
    public Enumeration<String> getAll() {
        return (impl.getAll());
    }

    /**
      * Retrieves a component of this composite name.
      *
      * <p>
      *  检索此复合名称的组件作为字符串的枚举对此枚举上的此复合名称的更新的影响未定义
      * 
      * 
      * @param  posn    The 0-based index of the component to retrieve.
      *                 Must be in the range [0,size()).
      * @return The non-null component at index posn.
      * @exception ArrayIndexOutOfBoundsException if posn is outside the
      *         specified range.
      */
    public String get(int posn) {
        return (impl.get(posn));
    }

    /**
      * Creates a composite name whose components consist of a prefix of the
      * components in this composite name. Subsequent changes to
      * this composite name does not affect the name that is returned.
      *
      * <p>
      *  检索此组合名称的组件
      * 
      * 
      * @param  posn    The 0-based index of the component at which to stop.
      *                 Must be in the range [0,size()].
      * @return A composite name consisting of the components at indexes in
      *         the range [0,posn).
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      */
    public Name getPrefix(int posn) {
        Enumeration<String> comps = impl.getPrefix(posn);
        return (new CompositeName(comps));
    }

    /**
      * Creates a composite name whose components consist of a suffix of the
      * components in this composite name. Subsequent changes to
      * this composite name does not affect the name that is returned.
      *
      * <p>
      *  创建组合名称,其组件由此组合名称中组件的前缀组成此组合名称的后续更改不会影响返回的名称
      * 
      * 
      * @param  posn    The 0-based index of the component at which to start.
      *                 Must be in the range [0,size()].
      * @return A composite name consisting of the components at indexes in
      *         the range [posn,size()).  If posn is equal to
      *         size(), an empty composite name is returned.
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      */
    public Name getSuffix(int posn) {
        Enumeration<String> comps = impl.getSuffix(posn);
        return (new CompositeName(comps));
    }

    /**
      * Determines whether a composite name is a prefix of this composite name.
      * A composite name 'n' is a prefix if it is equal to
      * getPrefix(n.size())--in other words, this composite name
      * starts with 'n'. If 'n' is null or not a composite name, false is returned.
      *
      * <p>
      *  创建组合名称,其组成部分由此组合名称中组件的后缀组成此组合名称的后续更改不会影响返回的名称
      * 
      * 
      * @param  n       The possibly null name to check.
      * @return true if n is a CompositeName and
      *         is a prefix of this composite name, false otherwise.
      */
    public boolean startsWith(Name n) {
        if (n instanceof CompositeName) {
            return (impl.startsWith(n.size(), n.getAll()));
        } else {
            return false;
        }
    }

    /**
      * Determines whether a composite name is a suffix of this composite name.
      * A composite name 'n' is a suffix if it it is equal to
      * getSuffix(size()-n.size())--in other words, this
      * composite name ends with 'n'.
      * If n is null or not a composite name, false is returned.
      *
      * <p>
      * 确定复合名称是否是此复合名称的前缀复合名称"n"是前缀,如果它等于getPrefix(nsize()) - 换句话说,此复合名称以"n"开头如果"n"是null或不是复合名称,返回false
      * 
      * 
      * @param  n       The possibly null name to check.
      * @return true if n is a CompositeName and
      *         is a suffix of this composite name, false otherwise.
      */
    public boolean endsWith(Name n) {
        if (n instanceof CompositeName) {
            return (impl.endsWith(n.size(), n.getAll()));
        } else {
            return false;
        }
    }

    /**
      * Adds the components of a composite name -- in order -- to the end of
      * this composite name.
      *
      * <p>
      *  确定复合名称是否是此复合名称的后缀复合名称"n"是一个后缀,如果它等于getSuffix(size() -  nsize()) - 换句话说,此复合名称以'n '如果n为null或不是复合名称,则返回
      * false。
      * 
      * 
      * @param suffix   The non-null components to add.
      * @return The updated CompositeName, not a new one. Cannot be null.
      * @exception InvalidNameException If suffix is not a composite name.
      */
    public Name addAll(Name suffix)
        throws InvalidNameException
    {
        if (suffix instanceof CompositeName) {
            impl.addAll(suffix.getAll());
            return this;
        } else {
            throw new InvalidNameException("Not a composite name: " +
                suffix.toString());
        }
    }

    /**
      * Adds the components of a composite name -- in order -- at a specified
      * position within this composite name.
      * Components of this composite name at or after the index of the first
      * new component are shifted up (away from index 0)
      * to accommodate the new components.
      *
      * <p>
      *  将组合名称的组件按顺序添加到此组合名称的末尾
      * 
      * 
      * @param n        The non-null components to add.
      * @param posn     The index in this name at which to add the new
      *                 components.  Must be in the range [0,size()].
      * @return The updated CompositeName, not a new one. Cannot be null.
      * @exception InvalidNameException If n is not a composite name.
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      */
    public Name addAll(int posn, Name n)
        throws InvalidNameException
    {
        if (n instanceof CompositeName) {
            impl.addAll(posn, n.getAll());
            return this;
        } else {
            throw new InvalidNameException("Not a composite name: " +
                n.toString());
        }
    }

    /**
      * Adds a single component to the end of this composite name.
      *
      * <p>
      * 在此复合名称中的指定位置处按顺序添加复合名称的组件在第一个新组件的索引处或之后,此复合名称的组件向上移动(远离索引0),以适应新组件
      * 
      * 
      * @param comp     The non-null component to add.
      * @return The updated CompositeName, not a new one. Cannot be null.
      * @exception InvalidNameException If adding comp at end of the name
      *                         would violate the name's syntax.
      */
    public Name add(String comp) throws InvalidNameException {
        impl.add(comp);
        return this;
    }

    /**
      * Adds a single component at a specified position within this
      * composite name.
      * Components of this composite name at or after the index of the new
      * component are shifted up by one (away from index 0) to accommodate
      * the new component.
      *
      * <p>
      *  将单个组件添加到此组合名称的末尾
      * 
      * 
      * @param  comp    The non-null component to add.
      * @param  posn    The index at which to add the new component.
      *                 Must be in the range [0,size()].
      * @return The updated CompositeName, not a new one. Cannot be null.
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      * @exception InvalidNameException If adding comp at the specified position
      *                         would violate the name's syntax.
      */
    public Name add(int posn, String comp)
        throws InvalidNameException
    {
        impl.add(posn, comp);
        return this;
    }

    /**
      * Deletes a component from this composite name.
      * The component of this composite name at position 'posn' is removed,
      * and components at indices greater than 'posn'
      * are shifted down (towards index 0) by one.
      *
      * <p>
      *  在此组合名称中的指定位置添加单个组件在新组件的索引处或之后的此组合名称的组件向上移位一(远离索引0)以容纳新组件
      * 
      * 
      * @param  posn    The index of the component to delete.
      *                 Must be in the range [0,size()).
      * @return The component removed (a String).
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range (includes case where
      *         composite name is empty).
      * @exception InvalidNameException If deleting the component
      *                         would violate the name's syntax.
      */
    public Object remove(int posn) throws InvalidNameException{
        return impl.remove(posn);
    }

    /**
     * Overridden to avoid implementation dependency.
     * <p>
     *  从此组合名称中删除组件除去位置"posn"处的组合名称的组件,并且大于"posn"的索引处的组件向下(朝向索引0)移动一
     * 
     * 
     * @serialData The number of components (an <tt>int</tt>) followed by
     * the individual components (each a <tt>String</tt>).
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        s.writeInt(size());
        Enumeration<String> comps = getAll();
        while (comps.hasMoreElements()) {
            s.writeObject(comps.nextElement());
        }
    }

    /**
     * Overridden to avoid implementation dependency.
     * <p>
     * 重写以避免实现依赖
     * 
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        impl = new NameImpl(null);  // null means use default syntax
        int n = s.readInt();    // number of components
        try {
            while (--n >= 0) {
                add((String)s.readObject());
            }
        } catch (InvalidNameException e) {
            throw (new java.io.StreamCorruptedException("Invalid name"));
        }
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  重写以避免实现依赖
     * 
     */
    private static final long serialVersionUID = 1667768148915813118L;

/*
    // %%% Test code for serialization.
    public static void main(String[] args) throws Exception {
        CompositeName c = new CompositeName("aaa/bbb");
        java.io.FileOutputStream f1 = new java.io.FileOutputStream("/tmp/ser");
        java.io.ObjectOutputStream s1 = new java.io.ObjectOutputStream(f1);
        s1.writeObject(c);
        s1.close();
        java.io.FileInputStream f2 = new java.io.FileInputStream("/tmp/ser");
        java.io.ObjectInputStream s2 = new java.io.ObjectInputStream(f2);
        c = (CompositeName)s2.readObject();

        System.out.println("Size: " + c.size());
        System.out.println("Size: " + c.snit);
    }
/* <p>
/*  使用JNDI 111的serialVersionUID实现互操作性
/* 
*/

/*
   %%% Testing code
    public static void main(String[] args) {
        try {
            for (int i = 0; i < args.length; i++) {
                Name name;
                Enumeration e;
                System.out.println("Given name: " + args[i]);
                name = new CompositeName(args[i]);
                e = name.getComponents();
                while (e.hasMoreElements()) {
                    System.out.println("Element: " + e.nextElement());
                }
                System.out.println("Constructed name: " + name.toString());
            }
        } catch (Exception ne) {
            ne.printStackTrace();
        }
    }
/* <p>
/*  // %%%测试代码序列化public static void main(String [] args)throws Exception {CompositeName c = new CompositeName("aaa / bbb"); javaioFileOutputStream f1 = new javaioFileOutputStream("/ tmp / ser"); javaioObjectOutputStream s1 = new javaioObjectOutputStream(f1); s1writeObject(c); s1close(); javaioFileInputStream f2 = new javaioFileInputStream("/ tmp / ser"); javaioObjectInputStream s2 = new javaioObjectInputStream(f2); c =(CompositeName)s2readObject();。
/* 
/*  Systemoutprintln("Size："+ csize()); Systemoutprintln("Size："+ csnit); }}
/* 
*/
}
