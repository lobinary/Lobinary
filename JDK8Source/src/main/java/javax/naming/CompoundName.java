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
 * This class represents a compound name -- a name from
 * a hierarchical name space.
 * Each component in a compound name is an atomic name.
 * <p>
 * The components of a compound name are numbered.  The indexes of a
 * compound name with N components range from 0 up to, but not including, N.
 * This range may be written as [0,N).
 * The most significant component is at index 0.
 * An empty compound name has no components.
 *
 * <h1>Compound Name Syntax</h1>
 * The syntax of a compound name is specified using a set of properties:
 *<dl>
 *  <dt>jndi.syntax.direction
 *  <dd>Direction for parsing ("right_to_left", "left_to_right", "flat").
 *      If unspecified, defaults to "flat", which means the namespace is flat
 *      with no hierarchical structure.
 *
 *  <dt>jndi.syntax.separator
 *  <dd>Separator between atomic name components.
 *      Required unless direction is "flat".
 *
 *  <dt>jndi.syntax.ignorecase
 *  <dd>If present, "true" means ignore the case when comparing name
 *      components. If its value is not "true", or if the property is not
 *      present, case is considered when comparing name components.
 *
 *  <dt>jndi.syntax.escape
 *  <dd>If present, specifies the escape string for overriding separator,
 *      escapes and quotes.
 *
 *  <dt>jndi.syntax.beginquote
 *  <dd>If present, specifies the string delimiting start of a quoted string.
 *
 *  <dt>jndi.syntax.endquote
 *  <dd>String delimiting end of quoted string.
 *      If present, specifies the string delimiting the end of a quoted string.
 *      If not present, use syntax.beginquote as end quote.
 *  <dt>jndi.syntax.beginquote2
 *  <dd>Alternative set of begin/end quotes.
 *
 *  <dt>jndi.syntax.endquote2
 *  <dd>Alternative set of begin/end quotes.
 *
 *  <dt>jndi.syntax.trimblanks
 *  <dd>If present, "true" means trim any leading and trailing whitespaces
 *      in a name component for comparison purposes. If its value is not
 *      "true", or if the property is not present, blanks are significant.
 *  <dt>jndi.syntax.separator.ava
 *  <dd>If present, specifies the string that separates
 *      attribute-value-assertions when specifying multiple attribute/value
 *      pairs. (e.g. ","  in age=65,gender=male).
 *  <dt>jndi.syntax.separator.typeval
 *  <dd>If present, specifies the string that separators attribute
 *              from value (e.g. "=" in "age=65")
 *</dl>
 * These properties are interpreted according to the following rules:
 *<ol>
 *<li>
 * In a string without quotes or escapes, any instance of the
 * separator delimits two atomic names. Each atomic name is referred
 * to as a <em>component</em>.
 *<li>
 * A separator, quote or escape is escaped if preceded immediately
 * (on the left) by the escape.
 *<li>
 * If there are two sets of quotes, a specific begin-quote must be matched
 * by its corresponding end-quote.
 *<li>
 * A non-escaped begin-quote which precedes a component must be
 * matched by a non-escaped end-quote at the end of the component.
 * A component thus quoted is referred to as a
 * <em>quoted component</em>. It is parsed by
 * removing the being- and end- quotes, and by treating the intervening
 * characters as ordinary characters unless one of the rules involving
 * quoted components listed below applies.
 *<li>
 * Quotes embedded in non-quoted components are treated as ordinary strings
 * and need not be matched.
 *<li>
 * A separator that is escaped or appears between non-escaped
 * quotes is treated as an ordinary string and not a separator.
 *<li>
 * An escape string within a quoted component acts as an escape only when
 * followed by the corresponding end-quote string.
 * This can be used to embed an escaped quote within a quoted component.
 *<li>
 * An escaped escape string is not treated as an escape string.
 *<li>
 * An escape string that does not precede a meta string (quotes or separator)
 * and is not at the end of a component is treated as an ordinary string.
 *<li>
 * A leading separator (the compound name string begins with
 * a separator) denotes a leading empty atomic component (consisting
 * of an empty string).
 * A trailing separator (the compound name string ends with
 * a separator) denotes a trailing empty atomic component.
 * Adjacent separators denote an empty atomic component.
 *</ol>
 * <p>
 * The string form of the compound name follows the syntax described above.
 * When the components of the compound name are turned into their
 * string representation, the reserved syntax rules described above are
 * applied (e.g. embedded separators are escaped or quoted)
 * so that when the same string is parsed, it will yield the same components
 * of the original compound name.
 *
 *<h1>Multithreaded Access</h1>
 * A <tt>CompoundName</tt> instance is not synchronized against concurrent
 * multithreaded access. Multiple threads trying to access and modify a
 * <tt>CompoundName</tt> should lock the object.
 *
 * <p>
 *  此类表示复合名称 - 来自分层名称空间的名称复合名称中的每个组件都是原子名称
 * <p>
 *  化合物名称的组分编号化合物名称的索引,其中N个组分的范围从0到但不包括N这个范围可以写为[0,N)最高有效组分在索引0空化合物名称没有组件
 * 
 *  <h1>化合物名称语法</h1>化合物名称的语法使用一组属性指定：
 * dl>
 * <dt> jndisyntaxdirection <dd>解析方向("right_to_left","left_to_right","flat")如果未指定,则默认为"flat",这意味着命名空间是平的
 * ,没有层次结构。
 * 
 *  <dt> jndisyntaxseparator <dd>原子名称组件之间的分隔符除非方向是"flat"
 * 
 *  <dt> jndisyntaxignorecase <dd>如果存在,"true"表示忽略比较名称组件时的情况如果其值不是"true",或者如果属性不存在,则比较名称组件
 * 
 *  <dt> jndisyntaxescape <dd>如果存在,指定用于覆盖分隔符,转义和引号的转义字符串
 * 
 *  <dt> jndisyntaxbeginquote <dd>如果存在,指定引用字符串的字符串分隔开始
 * 
 * <dt> jndisyntaxendquote <dd>字符串分隔引用字符串的结束如果存在,指定分隔引号字符串结尾的字符串如果不存在,请使用syntaxbeginquote作为结束引号<dt> jndi
 * syntaxbeginquote2 <dd>开始/。
 * 
 *  <dt> jndisyntaxendquote2 <dd>开始/结束引号的备选集
 * 
 * <dt> jndisyntaxtrimblanks <dd>如果存在,"true"表示修剪名称组件中的任何前导和尾随空格,以进行比较。
 * 如果值不为"true",或者如果属性不存在,则空格有效<dt> jndisyntaxseparatorava <dd>如果存在,指定在指定多个属性/值对时分隔属性值断言的字符串(例如,年龄= 65,性别
 * =男性中的",")<dt> jndisyntaxseparatortypeval <dd>如果存在,分隔符属性值的字符串(例如"age = 65"中的"=")。
 * <dt> jndisyntaxtrimblanks <dd>如果存在,"true"表示修剪名称组件中的任何前导和尾随空格,以进行比较。
 * /dl>
 *  这些属性根据以下规则进行解释：
 * ol>
 * li>
 *  在不带引号或转义的字符串中,分隔符的任何实例分隔两个原子名称每个原子名称被称为<em>组件</em>
 * li>
 * 如果立即(在左边)通过转义前缀,分隔符,引号或转义将被转义
 * li>
 *  如果有两组引号,一个特定的begin-quote必须与其相应的end-quote匹配
 * li>
 *  在组件之前的非转义begin-quote必须通过在组件末尾的非转义结束引用来匹配。因此引用的组件被称为<em>引用组件</em>。
 * 它被解析删除正文和结尾,以及将居间字符视为普通字符,除非涉及以下列出的组件的规则之一适用。
 * li>
 *  嵌入在非引用组件中的引用被视为普通字符串,不需要匹配
 * li>
 * 在非转义引号之间转义或显示的分隔符被视为普通字符串,而不是分隔符
 * li>
 *  带引号的组件中的转义字符串仅作为转义,后跟相应的结束引用字符串。这可以用于在引用的组件中嵌入转义引号
 * li>
 *  转义的转义字符串不会被视为转义字符串
 * li>
 *  不在元字符串(引号或分隔符)之前并且不在组件末尾的转义字符串被视为普通字符串
 * li>
 * 前导分隔符(复合名称字符串以分隔符开头)表示前导空原子分量(由空字符串组成)尾随分隔符(复合名称字符串以分隔符结尾)表示尾随空原子分量相邻分隔符表示空原子分量
 * /ol>
 * <p>
 *  化合物名称的字符串形式遵循上述语法当化合物名称的组件转换为其字符串表示形式时,将应用上述保留的语法规则(例如,嵌入的分隔符被转义或引用),以便当相同的字符串解析后,它将产生与原始化合物名称相同的组件。
 * 
 * h1>多线程访问</h1> A <tt> CompoundName </tt>实例未与并发多线程访问同步多个尝试访问和修改<tt> CompoundName </tt>的线程应锁定对象
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 * @since 1.3
 */

public class CompoundName implements Name {

    /**
      * Implementation of this compound name.
      * This field is initialized by the constructors and cannot be null.
      * It should be treated as a read-only variable by subclasses.
      * <p>
      *  此化合物名称的实现此字段由构造函数初始化,并且不能为null应将其视为子类的只读变量
      * 
      */
    protected transient NameImpl impl;
    /**
      * Syntax properties for this compound name.
      * This field is initialized by the constructors and cannot be null.
      * It should be treated as a read-only variable by subclasses.
      * Any necessary changes to mySyntax should be made within constructors
      * and not after the compound name has been instantiated.
      * <p>
      *  此化合物名称的语法属性此字段由构造函数初始化,不能为null应由子类处理为只读变量对mySyntax的任何必要更改都应在构造函数中进行,而不是在化合物名称实例化之后
      * 
      */
    protected transient Properties mySyntax;

    /**
      * Constructs a new compound name instance using the components
      * specified in comps and syntax. This protected method is intended to be
      * to be used by subclasses of CompoundName when they override
      * methods such as clone(), getPrefix(), getSuffix().
      *
      * <p>
      * 使用comps和语法中指定的组件构造新的化合物名称实例此受保护的方法旨在由CompoundName的子类使用,当它们覆盖诸如clone(),getPrefix(),getSuffix()
      * 
      * 
      * @param comps  A non-null enumeration of the components to add.
      *   Each element of the enumeration is of class String.
      *               The enumeration will be consumed to extract its
      *               elements.
      * @param syntax   A non-null properties that specify the syntax of
      *                 this compound name. See class description for
      *                 contents of properties.
      */
    protected CompoundName(Enumeration<String> comps, Properties syntax) {
        if (syntax == null) {
            throw new NullPointerException();
        }
        mySyntax = syntax;
        impl = new NameImpl(syntax, comps);
    }

    /**
      * Constructs a new compound name instance by parsing the string n
      * using the syntax specified by the syntax properties supplied.
      *
      * <p>
      *  通过使用提供的语法属性指定的语法解析字符串n来构造新的复合名称实例
      * 
      * 
      * @param  n       The non-null string to parse.
      * @param syntax   A non-null list of properties that specify the syntax of
      *                 this compound name.  See class description for
      *                 contents of properties.
      * @exception      InvalidNameException If 'n' violates the syntax specified
      *                 by <code>syntax</code>.
      */
    public CompoundName(String n, Properties syntax) throws InvalidNameException {
        if (syntax == null) {
            throw new NullPointerException();
        }
        mySyntax = syntax;
        impl = new NameImpl(syntax, n);
    }

    /**
      * Generates the string representation of this compound name, using
      * the syntax rules of the compound name. The syntax rules
      * are described in the class description.
      * An empty component is represented by an empty string.
      *
      * The string representation thus generated can be passed to
      * the CompoundName constructor with the same syntax properties
      * to create a new equivalent compound name.
      *
      * <p>
      *  使用化合物名称的语法规则生成此化合物名称的字符串表示形式语法规则在类描述中描述空组件由空字符串表示
      * 
      *  这样生成的字符串表示可以传递给具有相同语法属性的CompoundName构造函数,以创建新的等效化合物名称
      * 
      * 
      * @return A non-null string representation of this compound name.
      */
    public String toString() {
        return (impl.toString());
    }

    /**
      * Determines whether obj is syntactically equal to this compound name.
      * If obj is null or not a CompoundName, false is returned.
      * Two compound names are equal if each component in one is "equal"
      * to the corresponding component in the other.
      *<p>
      * Equality is also defined in terms of the syntax of this compound name.
      * The default implementation of CompoundName uses the syntax properties
      * jndi.syntax.ignorecase and jndi.syntax.trimblanks when comparing
      * two components for equality.  If case is ignored, two strings
      * with the same sequence of characters but with different cases
      * are considered equal. If blanks are being trimmed, leading and trailing
      * blanks are ignored for the purpose of the comparison.
      *<p>
      * Both compound names must have the same number of components.
      *<p>
      * Implementation note: Currently the syntax properties of the two compound
      * names are not compared for equality. They might be in the future.
      *
      * <p>
      * 确定obj在语法上是否等于此化合物名称如果obj为null或不是CompoundName,则返回false如果一个组件中的每个组件都与其他组件中的相应组件"相等"
      * p>
      *  平等也根据此化合物名称的语法定义CompoundName的默认实现在比较两个组件的相等性时使用语法属性jndisyntaxignorecase和jndisyntaxtrimblanks如果case被忽
      * 略,则考虑具有相同字符序列但具有不同情况的两个字符串equal如果正在修剪空白,为了比较的目的,将忽略前导和尾随空白。
      * p>
      *  两个化合物名称必须具有相同数目的组分
      * p>
      * 实现注意：目前,两个复合名称的语法属性没有进行比较,他们可能在未来
      * 
      * 
      * @param  obj     The possibly null object to compare against.
      * @return true if obj is equal to this compound name, false otherwise.
      * @see #compareTo(java.lang.Object obj)
      */
    public boolean equals(Object obj) {
        // %%% check syntax too?
        return (obj != null &&
                obj instanceof CompoundName &&
                impl.equals(((CompoundName)obj).impl));
    }

    /**
      * Computes the hash code of this compound name.
      * The hash code is the sum of the hash codes of the "canonicalized"
      * forms of individual components of this compound name.
      * Each component is "canonicalized" according to the
      * compound name's syntax before its hash code is computed.
      * For a case-insensitive name, for example, the uppercased form of
      * a name has the same hash code as its lowercased equivalent.
      *
      * <p>
      *  计算此化合物名称的哈希码哈希码是此化合物名称的各个组件的"规范化"形式的哈希码的总和每个组件在计算其哈希码之前根据化合物名称的语法"规范化"。
      * 不区分大小写的名称,例如,名称的大写形式具有与其小写等效的相同的哈希码。
      * 
      * 
      * @return An int representing the hash code of this name.
      */
    public int hashCode() {
        return impl.hashCode();
    }

    /**
      * Creates a copy of this compound name.
      * Changes to the components of this compound name won't
      * affect the new copy and vice versa.
      * The clone and this compound name share the same syntax.
      *
      * <p>
      *  创建此化合物名称的副本更改此化合物名称的组件不会影响新副本,反之亦然克隆和此化合物名称共享相同的语法
      * 
      * 
      * @return A non-null copy of this compound name.
      */
    public Object clone() {
        return (new CompoundName(getAll(), mySyntax));
    }

    /**
     * Compares this CompoundName with the specified Object for order.
     * Returns a
     * negative integer, zero, or a positive integer as this Name is less
     * than, equal to, or greater than the given Object.
     * <p>
     * If obj is null or not an instance of CompoundName, ClassCastException
     * is thrown.
     * <p>
     * See equals() for what it means for two compound names to be equal.
     * If two compound names are equal, 0 is returned.
     *<p>
     * Ordering of compound names depend on the syntax of the compound name.
     * By default, they follow lexicographical rules for string comparison
     * with the extension that this applies to all the components in the
     * compound name and that comparison of individual components is
     * affected by the jndi.syntax.ignorecase and jndi.syntax.trimblanks
     * properties, identical to how they affect equals().
     * If this compound name is "lexicographically" lesser than obj,
     * a negative number is returned.
     * If this compound name is "lexicographically" greater than obj,
     * a positive number is returned.
     *<p>
     * Implementation note: Currently the syntax properties of the two compound
     * names are not compared when checking order. They might be in the future.
     * <p>
     * 将此CompoundName与指定的对象进行比较返回一个负整数,零或正整数,因为此名称小于,等于或大于给定对象
     * <p>
     *  如果obj为null或不是CompoundName的实例,则抛出ClassCastException
     * <p>
     *  对于两个化合物名称相等的含义,请参见equals()。如果两个化合物名称相等,则返回0
     * p>
     * 化合物名称的排序取决于化合物名称的语法默认情况下,它们遵循字符串与字符串比较的字典规则,该扩展适用于化合物名称中的所有组分,并且单个组分的比较受jndisyntaxignorecase和jndisynt
     * axtrimblanks属性的影响,等同于它们如何影响equals()如果此复合名称"按字典顺序"小于obj,则返回负数如果此复合名称"按字典顺序"大于obj,则返回正数。
     * p>
     *  实现注意：当前检查顺序时,不会比较两个化合物名称的语法属性他们可能在未来
     * 
     * 
     * @param   obj     The non-null object to compare against.
     * @return  a negative integer, zero, or a positive integer as this Name
     *          is less than, equal to, or greater than the given Object.
     * @exception ClassCastException if obj is not a CompoundName.
     * @see #equals(java.lang.Object)
     */
    public int compareTo(Object obj) {
        if (!(obj instanceof CompoundName)) {
            throw new ClassCastException("Not a CompoundName");
        }
        return impl.compareTo(((CompoundName)obj).impl);
    }

    /**
      * Retrieves the number of components in this compound name.
      *
      * <p>
      *  检索此化合物名称中的组件数
      * 
      * 
      * @return The nonnegative number of components in this compound name.
      */
    public int size() {
        return (impl.size());
    }

    /**
      * Determines whether this compound name is empty.
      * A compound name is empty if it has zero components.
      *
      * <p>
      * 确定此化合物名称是否为空如果化合物名称具有零个组分,则该化合物名称为空
      * 
      * 
      * @return true if this compound name is empty, false otherwise.
      */
    public boolean isEmpty() {
        return (impl.isEmpty());
    }

    /**
      * Retrieves the components of this compound name as an enumeration
      * of strings.
      * The effects of updates to this compound name on this enumeration
      * is undefined.
      *
      * <p>
      *  作为字符串枚举检索此化合物名称的组件此枚举的此化合物名称更新的影响未定义
      * 
      * 
      * @return A non-null enumeration of the components of this
      * compound name. Each element of the enumeration is of class String.
      */
    public Enumeration<String> getAll() {
        return (impl.getAll());
    }

    /**
      * Retrieves a component of this compound name.
      *
      * <p>
      *  检索此化合物名称的组件
      * 
      * 
      * @param  posn    The 0-based index of the component to retrieve.
      *                 Must be in the range [0,size()).
      * @return The component at index posn.
      * @exception ArrayIndexOutOfBoundsException if posn is outside the
      *         specified range.
      */
    public String get(int posn) {
        return (impl.get(posn));
    }

    /**
      * Creates a compound name whose components consist of a prefix of the
      * components in this compound name.
      * The result and this compound name share the same syntax.
      * Subsequent changes to
      * this compound name does not affect the name that is returned and
      * vice versa.
      *
      * <p>
      *  创建一个化合物名称,其组分由该化合物名称中组分的前缀组成结果和该化合物名称具有相同的语法对该化合物名称的后续更改不影响返回的名称,反之亦然
      * 
      * 
      * @param  posn    The 0-based index of the component at which to stop.
      *                 Must be in the range [0,size()].
      * @return A compound name consisting of the components at indexes in
      *         the range [0,posn).
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      */
    public Name getPrefix(int posn) {
        Enumeration<String> comps = impl.getPrefix(posn);
        return (new CompoundName(comps, mySyntax));
    }

    /**
      * Creates a compound name whose components consist of a suffix of the
      * components in this compound name.
      * The result and this compound name share the same syntax.
      * Subsequent changes to
      * this compound name does not affect the name that is returned.
      *
      * <p>
      * 创建一个化合物名称,其组分由该化合物名称中的组分的后缀组成结果和此化合物名称具有相同的语法此化合物名称的后续更改不会影响返回的名称
      * 
      * 
      * @param  posn    The 0-based index of the component at which to start.
      *                 Must be in the range [0,size()].
      * @return A compound name consisting of the components at indexes in
      *         the range [posn,size()).  If posn is equal to
      *         size(), an empty compound name is returned.
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      */
    public Name getSuffix(int posn) {
        Enumeration<String> comps = impl.getSuffix(posn);
        return (new CompoundName(comps, mySyntax));
    }

    /**
      * Determines whether a compound name is a prefix of this compound name.
      * A compound name 'n' is a prefix if it is equal to
      * getPrefix(n.size())--in other words, this compound name
      * starts with 'n'.
      * If n is null or not a compound name, false is returned.
      *<p>
      * Implementation note: Currently the syntax properties of n
      *  are not used when doing the comparison. They might be in the future.
      * <p>
      *  确定化合物名称是否为此化合物名称的前缀化合物名称'n'是前缀,如果它等于getPrefix(nsize()) - 换句话说,此化合物名称以"n"开头如果n为null或不是复合名称,返回false
      * p>
      *  实现注意：目前,在进行比较时不使用n的语法属性他们可能在未来
      * 
      * 
      * @param  n       The possibly null compound name to check.
      * @return true if n is a CompoundName and
      *                 is a prefix of this compound name, false otherwise.
      */
    public boolean startsWith(Name n) {
        if (n instanceof CompoundName) {
            return (impl.startsWith(n.size(), n.getAll()));
        } else {
            return false;
        }
    }

    /**
      * Determines whether a compound name is a suffix of this compound name.
      * A compound name 'n' is a suffix if it it is equal to
      * getSuffix(size()-n.size())--in other words, this
      * compound name ends with 'n'.
      * If n is null or not a compound name, false is returned.
      *<p>
      * Implementation note: Currently the syntax properties of n
      *  are not used when doing the comparison. They might be in the future.
      * <p>
      * 确定化合物名称是否为此化合物名称的后缀如果化合物名称"n"等于getSuffix(size() -  nsize()) - 即换句话说,此化合物名称以'n '如果n为null或不是复合名称,则返回fal
      * se。
      * p>
      *  实现注意：目前,在进行比较时不使用n的语法属性他们可能在未来
      * 
      * 
      * @param  n       The possibly null compound name to check.
      * @return true if n is a CompoundName and
      *         is a suffix of this compound name, false otherwise.
      */
    public boolean endsWith(Name n) {
        if (n instanceof CompoundName) {
            return (impl.endsWith(n.size(), n.getAll()));
        } else {
            return false;
        }
    }

    /**
      * Adds the components of a compound name -- in order -- to the end of
      * this compound name.
      *<p>
      * Implementation note: Currently the syntax properties of suffix
      *  is not used or checked. They might be in the future.
      * <p>
      *  将化合物名称的组分(按顺序)添加到此化合物名称的末尾
      * p>
      *  实现注意：当前不使用或检查后缀的语法属性他们可能在未来
      * 
      * 
      * @param suffix   The non-null components to add.
      * @return The updated CompoundName, not a new one. Cannot be null.
      * @exception InvalidNameException If suffix is not a compound name,
      *            or if the addition of the components violates the syntax
      *            of this compound name (e.g. exceeding number of components).
      */
    public Name addAll(Name suffix) throws InvalidNameException {
        if (suffix instanceof CompoundName) {
            impl.addAll(suffix.getAll());
            return this;
        } else {
            throw new InvalidNameException("Not a compound name: " +
                suffix.toString());
        }
    }

    /**
      * Adds the components of a compound name -- in order -- at a specified
      * position within this compound name.
      * Components of this compound name at or after the index of the first
      * new component are shifted up (away from index 0)
      * to accommodate the new components.
      *<p>
      * Implementation note: Currently the syntax properties of suffix
      *  is not used or checked. They might be in the future.
      *
      * <p>
      * 在该化合物名称中的指定位置处按顺序添加化合物名称的组分在第一个新组分的索引处或之后的该化合物名称的组分向上移位(远离索引0)以容纳新组分
      * p>
      *  实现注意：当前不使用或检查后缀的语法属性他们可能在未来
      * 
      * 
      * @param n        The non-null components to add.
      * @param posn     The index in this name at which to add the new
      *                 components.  Must be in the range [0,size()].
      * @return The updated CompoundName, not a new one. Cannot be null.
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      * @exception InvalidNameException If n is not a compound name,
      *            or if the addition of the components violates the syntax
      *            of this compound name (e.g. exceeding number of components).
      */
    public Name addAll(int posn, Name n) throws InvalidNameException {
        if (n instanceof CompoundName) {
            impl.addAll(posn, n.getAll());
            return this;
        } else {
            throw new InvalidNameException("Not a compound name: " +
                n.toString());
        }
    }

    /**
      * Adds a single component to the end of this compound name.
      *
      * <p>
      *  将单个组件添加到此化合物名称的末尾
      * 
      * 
      * @param comp     The non-null component to add.
      * @return The updated CompoundName, not a new one. Cannot be null.
      * @exception InvalidNameException If adding comp at end of the name
      *                         would violate the compound name's syntax.
      */
    public Name add(String comp) throws InvalidNameException{
        impl.add(comp);
        return this;
    }

    /**
      * Adds a single component at a specified position within this
      * compound name.
      * Components of this compound name at or after the index of the new
      * component are shifted up by one (away from index 0)
      * to accommodate the new component.
      *
      * <p>
      *  在此化合物名称中的指定位置添加单个组件在新组件的索引处或之后的此化合物名称的组件向上移位一(远离索引0)以容纳新组件
      * 
      * 
      * @param  comp    The non-null component to add.
      * @param  posn    The index at which to add the new component.
      *                 Must be in the range [0,size()].
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range.
      * @return The updated CompoundName, not a new one. Cannot be null.
      * @exception InvalidNameException If adding comp at the specified position
      *                         would violate the compound name's syntax.
      */
    public Name add(int posn, String comp) throws InvalidNameException{
        impl.add(posn, comp);
        return this;
    }

    /**
      * Deletes a component from this compound name.
      * The component of this compound name at position 'posn' is removed,
      * and components at indices greater than 'posn'
      * are shifted down (towards index 0) by one.
      *
      * <p>
      * 从此化合物名称中删除组件除去位置"posn"处的此化合物名称的组件,并且索引大于"posn"的组件向下移动一个(朝向索引0)一个
      * 
      * 
      * @param  posn    The index of the component to delete.
      *                 Must be in the range [0,size()).
      * @return The component removed (a String).
      * @exception ArrayIndexOutOfBoundsException
      *         If posn is outside the specified range (includes case where
      *         compound name is empty).
      * @exception InvalidNameException If deleting the component
      *                         would violate the compound name's syntax.
      */
    public Object remove(int posn) throws InvalidNameException {
        return impl.remove(posn);
    }

    /**
     * Overridden to avoid implementation dependency.
     * <p>
     *  重写以避免实现依赖
     * 
     * 
     * @serialData The syntax <tt>Properties</tt>, followed by
     * the number of components (an <tt>int</tt>), and the individual
     * components (each a <tt>String</tt>).
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        s.writeObject(mySyntax);
        s.writeInt(size());
        Enumeration<String> comps = getAll();
        while (comps.hasMoreElements()) {
            s.writeObject(comps.nextElement());
        }
    }

    /**
     * Overridden to avoid implementation dependency.
     * <p>
     *  重写以避免实现依赖
     * 
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        mySyntax = (Properties)s.readObject();
        impl = new NameImpl(mySyntax);
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
     *  使用JNDI 111的serialVersionUID实现互操作性
     * 
     */
    private static final long serialVersionUID = 3513100557083972036L;

/*
//   For testing

    public static void main(String[] args) {
        Properties dotSyntax = new Properties();
        dotSyntax.put("jndi.syntax.direction", "right_to_left");
        dotSyntax.put("jndi.syntax.separator", ".");
        dotSyntax.put("jndi.syntax.ignorecase", "true");
        dotSyntax.put("jndi.syntax.escape", "\\");
//      dotSyntax.put("jndi.syntax.beginquote", "\"");
//      dotSyntax.put("jndi.syntax.beginquote2", "'");

        Name first = null;
        try {
            for (int i = 0; i < args.length; i++) {
                Name name;
                Enumeration e;
                System.out.println("Given name: " + args[i]);
                name = new CompoundName(args[i], dotSyntax);
                if (first == null) {
                    first = name;
                }
                e = name.getComponents();
                while (e.hasMoreElements()) {
                    System.out.println("Element: " + e.nextElement());
                }
                System.out.println("Constructed name: " + name.toString());

                System.out.println("Compare " + first.toString() + " with "
                    + name.toString() + " = " + first.compareTo(name));
            }
        } catch (Exception ne) {
            ne.printStackTrace();
        }
    }
/* <p>
/*  //用于测试
/* 
/*  public static void main(String [] args){Properties dotSyntax = new Properties(); dotSyntaxput("jndisyntaxdirection","right_to_left"); dotSyntaxput("jndisyntaxseparator",""); dotSyntaxput("jndisyntaxignorecase","true"); dotSyntaxput("jndisyntaxescape","\\\\"); // dotSyntaxput("jndisyntaxbeginquote","\\""); // dotSyntaxput("jndisyntaxbeginquote2","'");。
/* 
/* 名称first = null; try {for(int i = 0; i <argslength; i ++){Name name;枚举e; Systemoutprintln("Given name："+ args [i]); name = new CompoundName(args [i],dotSyntax); if(first == null){first = name; } e = namegetComponents(); while(ehasMoreElements()){Systemoutprintln("Element："+ enextElement()); } Systemoutprintln("Constructed name："+ nametoString());。
*/
}
