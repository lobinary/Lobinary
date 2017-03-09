/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.ldap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Collections;

import javax.naming.InvalidNameException;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * This class represents a relative distinguished name, or RDN, which is a
 * component of a distinguished name as specified by
 * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>.
 * An example of an RDN is "OU=Sales+CN=J.Smith". In this example,
 * the RDN consist of multiple attribute type/value pairs. The
 * RDN is parsed as described in the class description for
 * {@link javax.naming.ldap.LdapName <tt>LdapName</tt>}.
 * <p>
 * The Rdn class represents an RDN as attribute type/value mappings,
 * which can be viewed using
 * {@link javax.naming.directory.Attributes Attributes}.
 * In addition, it contains convenience methods that allow easy retrieval
 * of type and value when the Rdn consist of a single type/value pair,
 * which is how it appears in a typical usage.
 * It also contains helper methods that allow escaping of the unformatted
 * attribute value and unescaping of the value formatted according to the
 * escaping syntax defined in RFC2253. For methods that take or return
 * attribute value as an Object, the value is either a String
 * (in unescaped form) or a byte array.
 * <p>
 * <code>Rdn</code> will properly parse all valid RDNs, but
 * does not attempt to detect all possible violations when parsing
 * invalid RDNs. It is "generous" in accepting invalid RDNs.
 * The "validity" of a name is determined ultimately when it
 * is supplied to an LDAP server, which may accept or
 * reject the name based on factors such as its schema information
 * and interoperability considerations.
 *
 * <p>
 * The following code example shows how to construct an Rdn using the
 * constructor that takes type and value as arguments:
 * <pre>
 *      Rdn rdn = new Rdn("cn", "Juicy, Fruit");
 *      System.out.println(rdn.toString());
 * </pre>
 * The last line will print <tt>cn=Juicy\, Fruit</tt>. The
 * {@link #unescapeValue(String) <tt>unescapeValue()</tt>} method can be
 * used to unescape the escaped comma resulting in the original
 * value <tt>"Juicy, Fruit"</tt>. The {@link #escapeValue(Object)
 * <tt>escapeValue()</tt>} method adds the escape back preceding the comma.
 * <p>
 * This class can be instantiated by a string representation
 * of the RDN defined in RFC 2253 as shown in the following code example:
 * <pre>
 *      Rdn rdn = new Rdn("cn=Juicy\\, Fruit");
 *      System.out.println(rdn.toString());
 * </pre>
 * The last line will print <tt>cn=Juicy\, Fruit</tt>.
 * <p>
 * Concurrent multithreaded read-only access of an instance of
 * <tt>Rdn</tt> need not be synchronized.
 * <p>
 * Unless otherwise noted, the behavior of passing a null argument
 * to a constructor or method in this class will cause NullPointerException
 * to be thrown.
 *
 * <p>
 *  此类表示相对可分辨名称或RDN,它是由<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>指定的可分辨名称的组成部分。 。
 *  RDN的示例是"OU = Sales + CN = J.Smith"。在此示例中,RDN由多个属性类型/值对组成。
 *  RDN按照{@link javax.naming.ldap.LdapName <tt> LdapName </tt>}的类描述中所述进行解析。
 * <p>
 *  Rdn类将RDN表示为属性类型/值映射,可以使用{@link javax.naming.directory.Attributes Attributes}查看。
 * 此外,它包含方便的方法,当Rdn由单个类型/值对组成时,允许轻松检索类型和值,这是它在典型用法中的显示方式。
 * 它还包含助手方法,允许转义未格式化的属性值,并根据RFC2253中定义的转义语法格式化的值的转义。对于接受或返回属性值作为对象的方法,该值为字符串(未转义的形式)或字节数组。
 * <p>
 * <code> Rdn </code>将正确解析所有有效的RDN,但在解析无效的RDN时不会尝试检测所有可能的违规。在接受无效的RDN时是"大方的"。
 * 当它被提供给LDAP服务器,它可以接受或拒绝基于各种因素的名称,如它的模式信息和互操作性考虑的一个名称的"有效性"最终确定。
 * 
 * <p>
 *  以下代码示例说明如何使用将类型和值作为参数的构造函数构造Rdn：
 * <pre>
 *  Rdn rdn = new Rdn("cn","Juicy,Fruit"); System.out.println(rdn.toString());
 * </pre>
 *  最后一行将打印<tt> cn = Juicy \,Fruit </tt>。
 *  {@link #unescapeValue(String)<tt> unescapeValue()</tt>}方法可用于取消转义导出原始值<tt>"Juicy,Fruit"</tt>的转义逗号。
 *  {@link #escapeValue(Object)<tt> escapeValue()</tt>}方法会在逗号之前添加回退。
 * <p>
 *  这个类可以通过RFC 2253中定义的RDN的字符串表示来实例化,如下面的代码示例所示：
 * <pre>
 *  Rdn rdn = new Rdn("cn = Juicy \\,Fruit"); System.out.println(rdn.toString());
 * </pre>
 *  最后一行将打印<tt> cn = Juicy \,Fruit </tt>。
 * <p>
 *  <tt> Rdn </tt>实例的并发多线程只读访问不需要同步。
 * <p>
 *  除非另有说明,否则将空参数传递给此类中的构造函数或方法的行为将导致抛出NullPointerException。
 * 
 * 
 * @since 1.5
 */

public class Rdn implements Serializable, Comparable<Object> {

    private transient ArrayList<RdnEntry> entries;

    // The common case.
    private static final int DEFAULT_SIZE = 1;

    private static final long serialVersionUID = -5994465067210009656L;

    /**
     * Constructs an Rdn from the given attribute set. See
     * {@link javax.naming.directory.Attributes Attributes}.
     * <p>
     * The string attribute values are not interpreted as
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>
     * formatted RDN strings. That is, the values are used
     * literally (not parsed) and assumed to be unescaped.
     *
     * <p>
     * 构造来自给定属性集的Rdn。请参阅{@link javax.naming.directory.Attributes Attributes}。
     * <p>
     *  字符串属性值不会解释为<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>格式化的RDN字符串。
     * 也就是说,值按字面使用(不解析),并假定为非转义。
     * 
     * 
     * @param attrSet The non-null and non-empty attributes containing
     * type/value mappings.
     * @throws InvalidNameException If contents of <tt>attrSet</tt> cannot
     *          be used to construct a valid RDN.
     */
    public Rdn(Attributes attrSet) throws InvalidNameException {
        if (attrSet.size() == 0) {
            throw new InvalidNameException("Attributes cannot be empty");
        }
        entries = new ArrayList<>(attrSet.size());
        NamingEnumeration<? extends Attribute> attrs = attrSet.getAll();
        try {
            for (int nEntries = 0; attrs.hasMore(); nEntries++) {
                RdnEntry entry = new RdnEntry();
                Attribute attr = attrs.next();
                entry.type = attr.getID();
                entry.value = attr.get();
                entries.add(nEntries, entry);
            }
        } catch (NamingException e) {
            InvalidNameException e2 = new InvalidNameException(
                                        e.getMessage());
            e2.initCause(e);
            throw e2;
        }
        sort(); // arrange entries for comparison
    }

    /**
     * Constructs an Rdn from the given string.
     * This constructor takes a string formatted according to the rules
     * defined in <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>
     * and described in the class description for
     * {@link javax.naming.ldap.LdapName}.
     *
     * <p>
     *  从给定字符串构造Rdn。
     * 此构造函数接受根据<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>中定义的规则格式化的字符串,并在{@link javax.naming.ldap.LdapName}
     * 。
     *  从给定字符串构造Rdn。
     * 
     * 
     * @param rdnString The non-null and non-empty RFC2253 formatted string.
     * @throws InvalidNameException If a syntax error occurs during
     *                  parsing of the rdnString.
     */
    public Rdn(String rdnString) throws InvalidNameException {
        entries = new ArrayList<>(DEFAULT_SIZE);
        (new Rfc2253Parser(rdnString)).parseRdn(this);
    }

    /**
     * Constructs an Rdn from the given <tt>rdn</tt>.
     * The contents of the <tt>rdn</tt> are simply copied into the newly
     * created Rdn.
     * <p>
     *  从给定的<tt> rdn </tt>构造Rdn。 <tt> rdn </tt>的内容只会复制到新创建的Rdn中。
     * 
     * 
     * @param rdn The non-null Rdn to be copied.
     */
    public Rdn(Rdn rdn) {
        entries = new ArrayList<>(rdn.entries.size());
        entries.addAll(rdn.entries);
    }

    /**
     * Constructs an Rdn from the given attribute type and
     * value.
     * The string attribute values are not interpreted as
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>
     * formatted RDN strings. That is, the values are used
     * literally (not parsed) and assumed to be unescaped.
     *
     * <p>
     *  根据给定的属性类型和值构造Rdn。字符串属性值不会解释为<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>格式化的RDN字符串。
     * 也就是说,值按字面使用(不解析),并假定为非转义。
     * 
     * 
     * @param type The non-null and non-empty string attribute type.
     * @param value The non-null and non-empty attribute value.
     * @throws InvalidNameException If type/value cannot be used to
     *                  construct a valid RDN.
     * @see #toString()
     */
    public Rdn(String type, Object value) throws InvalidNameException {
        if (value == null) {
            throw new NullPointerException("Cannot set value to null");
        }
        if (type.equals("") || isEmptyValue(value)) {
            throw new InvalidNameException(
                "type or value cannot be empty, type:" + type +
                " value:" + value);
        }
        entries = new ArrayList<>(DEFAULT_SIZE);
        put(type, value);
    }

    private boolean isEmptyValue(Object val) {
        return ((val instanceof String) && val.equals("")) ||
        ((val instanceof byte[]) && (((byte[]) val).length == 0));
    }

    // An empty constructor used by the parser
    Rdn() {
        entries = new ArrayList<>(DEFAULT_SIZE);
    }

    /*
     * Adds the given attribute type and value to this Rdn.
     * The string attribute values are not interpreted as
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>
     * formatted RDN strings. That is the values are used
     * literally (not parsed) and assumed to be unescaped.
     *
     * <p>
     *  将给定的属性类型和值添加到此Rdn。
     * 字符串属性值不会解释为<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>格式化的RDN字符串。
     * 这就是字面上使用的值(不解析),并假定为非转义。
     * 
     * 
     * @param type The non-null and non-empty string attribute type.
     * @param value The non-null and non-empty attribute value.
     * @return The updated Rdn, not a new one. Cannot be null.
     * @see #toString()
     */
    Rdn put(String type, Object value) {

        // create new Entry
        RdnEntry newEntry = new RdnEntry();
        newEntry.type =  type;
        if (value instanceof byte[]) {  // clone the byte array
            newEntry.value = ((byte[]) value).clone();
        } else {
            newEntry.value = value;
        }
        entries.add(newEntry);
        return this;
    }

    void sort() {
        if (entries.size() > 1) {
            Collections.sort(entries);
        }
    }

    /**
     * Retrieves one of this Rdn's value.
     * This is a convenience method for obtaining the value,
     * when the RDN contains a single type and value mapping,
     * which is the common RDN usage.
     * <p>
     * For a multi-valued RDN, this method returns value corresponding
     * to the type returned by {@link #getType() getType()} method.
     *
     * <p>
     *  检索此Rdn的值之一。当RDN包含单个类型和值映射(这是常见的RDN使用)时,这是获取值的一种方便的方法。
     * <p>
     * 对于多值RDN,此方法返回与{@link #getType()getType()}方法返回的类型相对应的值。
     * 
     * 
     * @return The non-null attribute value.
     */
    public Object getValue() {
        return entries.get(0).getValue();
    }

    /**
     * Retrieves one of this Rdn's type.
     * This is a convenience method for obtaining the type,
     * when the RDN contains a single type and value mapping,
     * which is the common RDN usage.
     * <p>
     * For a multi-valued RDN, the type/value pairs have
     * no specific order defined on them. In that case, this method
     * returns type of one of the type/value pairs.
     * The {@link #getValue() getValue()} method returns the
     * value corresponding to the type returned by this method.
     *
     * <p>
     *  检索此Rdn的类型之一。当RDN包含单个类型和值映射(这是常见的RDN使用)时,这是获取类型的简便方法。
     * <p>
     *  对于多值RDN,类型/值对没有在它们上定义的特定顺序。在这种情况下,此方法返回类型/值对之一的类型。 {@link #getValue()getValue()}方法返回与此方法返回的类型对应的值。
     * 
     * 
     * @return The non-null attribute type.
     */
    public String getType() {
        return entries.get(0).getType();
    }

    /**
     * Returns this Rdn as a string represented in a format defined by
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a> and described
     * in the class description for {@link javax.naming.ldap.LdapName LdapName}.
     *
     * <p>
     *  将此Rdn返回为由<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>定义的格式表示的字符串,并在{@link javax.naming.ldap.LdapName LdapName}
     * 。
     * 
     * 
     * @return The string representation of the Rdn.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int size = entries.size();
        if (size > 0) {
            builder.append(entries.get(0));
        }
        for (int next = 1; next < size; next++) {
            builder.append('+');
            builder.append(entries.get(next));
        }
        return builder.toString();
    }

    /**
     * Compares this Rdn with the specified Object for order.
     * Returns a negative integer, zero, or a positive integer as this
     * Rdn is less than, equal to, or greater than the given Object.
     * <p>
     * If obj is null or not an instance of Rdn, ClassCastException
     * is thrown.
     * <p>
     * The attribute type and value pairs of the RDNs are lined up
     * against each other and compared lexicographically. The order of
     * components in multi-valued Rdns (such as "ou=Sales+cn=Bob") is not
     * significant.
     *
     * <p>
     *  将此Rdn与指定的对象进行比较。返回一个负整数,零或正整数,因为此Rdn小于,等于或大于给定的对象。
     * <p>
     *  如果obj为null或不是Rdn的实例,则抛出ClassCastException。
     * <p>
     *  RDN的属性类型和值对彼此对齐并按字典顺序进行比较。多值Rdns中的组件的顺序(例如"ou = Sales + cn = Bob")不重要。
     * 
     * 
     * @param obj The non-null object to compare against.
     * @return  A negative integer, zero, or a positive integer as this Rdn
     *          is less than, equal to, or greater than the given Object.
     * @exception ClassCastException if obj is null or not a Rdn.
     */
    public int compareTo(Object obj) {
        if (!(obj instanceof Rdn)) {
            throw new ClassCastException("The obj is not a Rdn");
        }
        if (obj == this) {
            return 0;
        }
        Rdn that = (Rdn) obj;
        int minSize = Math.min(entries.size(), that.entries.size());
        for (int i = 0; i < minSize; i++) {

            // Compare a single pair of type/value pairs.
            int diff = entries.get(i).compareTo(that.entries.get(i));
            if (diff != 0) {
                return diff;
            }
        }
        return (entries.size() - that.entries.size());  // longer RDN wins
    }

    /**
     * Compares the specified Object with this Rdn for equality.
     * Returns true if the given object is also a Rdn and the two Rdns
     * represent the same attribute type and value mappings. The order of
     * components in multi-valued Rdns (such as "ou=Sales+cn=Bob") is not
     * significant.
     * <p>
     * Type and value equality matching is done as below:
     * <ul>
     * <li> The types are compared for equality with their case ignored.
     * <li> String values with different but equivalent usage of quoting,
     * escaping, or UTF8-hex-encoding are considered equal.
     * The case of the values is ignored during the comparison.
     * </ul>
     * <p>
     * If obj is null or not an instance of Rdn, false is returned.
     * <p>
     * <p>
     * 将指定的对象与此Rdn比较以确保相等。如果给定对象也是Rdn,并且两个Rdns表示相同的属性类型和值映射,则返回true。
     * 多值Rdns中的组件的顺序(例如"ou = Sales + cn = Bob")不重要。
     * <p>
     *  类型和值相等匹配完成如下：
     * <ul>
     *  <li>比较类型与忽略的情况的相等性。 <li>具有不同但等效的引用,转义或UTF8-hex编码的字符串值被视为相等。在比较期间忽略值的情况。
     * </ul>
     * <p>
     *  如果obj为null或不是Rdn的实例,则返回false。
     * <p>
     * 
     * @param obj object to be compared for equality with this Rdn.
     * @return true if the specified object is equal to this Rdn.
     * @see #hashCode()
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Rdn)) {
            return false;
        }
        Rdn that = (Rdn) obj;
        if (entries.size() != that.size()) {
            return false;
        }
        for (int i = 0; i < entries.size(); i++) {
            if (!entries.get(i).equals(that.entries.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the hash code of this RDN. Two RDNs that are
     * equal (according to the equals method) will have the same
     * hash code.
     *
     * <p>
     *  返回此RDN的哈希码。两个相等的RDN(根据equals方法)将具有相同的哈希码。
     * 
     * 
     * @return An int representing the hash code of this Rdn.
     * @see #equals
     */
    public int hashCode() {

        // Sum up the hash codes of the components.
        int hash = 0;

        // For each type/value pair...
        for (int i = 0; i < entries.size(); i++) {
            hash += entries.get(i).hashCode();
        }
        return hash;
    }

    /**
     * Retrieves the {@link javax.naming.directory.Attributes Attributes}
     * view of the type/value mappings contained in this Rdn.
     *
     * <p>
     *  检索此Rdn中包含的类型/值映射的{@link javax.naming.directory.Attributes Attributes}视图。
     * 
     * 
     * @return  The non-null attributes containing the type/value
     *          mappings of this Rdn.
     */
    public Attributes toAttributes() {
        Attributes attrs = new BasicAttributes(true);
        for (int i = 0; i < entries.size(); i++) {
            RdnEntry entry = entries.get(i);
            Attribute attr = attrs.put(entry.getType(), entry.getValue());
            if (attr != null) {
                attr.add(entry.getValue());
                attrs.put(attr);
            }
        }
        return attrs;
    }


    private static class RdnEntry implements Comparable<RdnEntry> {
        private String type;
        private Object value;

        // If non-null, a cannonical representation of the value suitable
        // for comparison using String.compareTo()
        private String comparable = null;

        String getType() {
            return type;
        }

        Object getValue() {
            return value;
        }

        public int compareTo(RdnEntry that) {
            int diff = type.compareToIgnoreCase(that.type);
            if (diff != 0) {
                return diff;
            }
            if (value.equals(that.value)) {     // try shortcut
                return 0;
            }
            return getValueComparable().compareTo(
                        that.getValueComparable());
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof RdnEntry)) {
                return false;
            }

            // Any change here must be reflected in hashCode()
            RdnEntry that = (RdnEntry) obj;
            return (type.equalsIgnoreCase(that.type)) &&
                        (getValueComparable().equals(
                        that.getValueComparable()));
        }

        public int hashCode() {
            return (type.toUpperCase(Locale.ENGLISH).hashCode() +
                getValueComparable().hashCode());
        }

        public String toString() {
            return type + "=" + escapeValue(value);
        }

        private String getValueComparable() {
            if (comparable != null) {
                return comparable;              // return cached result
            }

            // cache result
            if (value instanceof byte[]) {
                comparable = escapeBinaryValue((byte[]) value);
            } else {
                comparable = ((String) value).toUpperCase(Locale.ENGLISH);
            }
            return comparable;
        }
    }

    /**
     * Retrieves the number of attribute type/value pairs in this Rdn.
     * <p>
     *  检索此Rdn中的属性类型/值对的数量。
     * 
     * 
     * @return The non-negative number of type/value pairs in this Rdn.
     */
    public int size() {
        return entries.size();
    }

    /**
     * Given the value of an attribute, returns a string escaped according
     * to the rules specified in
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>.
     * <p>
     * For example, if the val is "Sue, Grabbit and Runn", the escaped
     * value returned by this method is "Sue\, Grabbit and Runn".
     * <p>
     * A string value is represented as a String and binary value
     * as a byte array.
     *
     * <p>
     *  给定属性的值,将返回根据<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>中指定的规则转义的字符串。
     * <p>
     *  例如,如果val是"Sue,Grabbit和Runn",则此方法返回的转义值为"Sue \,Grabbit和Runn"。
     * <p>
     *  字符串值表示为字符串和二进制值作为字节数组。
     * 
     * 
     * @param val The non-null object to be escaped.
     * @return Escaped string value.
     * @throws ClassCastException if val is is not a String or byte array.
     */
    public static String escapeValue(Object val) {
        return (val instanceof byte[])
                ? escapeBinaryValue((byte[])val)
                : escapeStringValue((String)val);
    }

    /*
     * Given the value of a string-valued attribute, returns a
     * string suitable for inclusion in a DN.  This is accomplished by
     * using backslash (\) to escape the following characters:
     *  leading and trailing whitespace
     *  , = + < > # ; " \
     * <p>
     * 给定字符串值属性的值,返回适合包含在DN中的字符串。这是通过使用反斜杠(\)来转义以下字符来实现的：前导和尾随空格,= + <>#; "\
     * 
     */
    private static final String escapees = ",=+<>#;\"\\";

    private static String escapeStringValue(String val) {

            char[] chars = val.toCharArray();
            StringBuilder builder = new StringBuilder(2 * val.length());

            // Find leading and trailing whitespace.
            int lead;   // index of first char that is not leading whitespace
            for (lead = 0; lead < chars.length; lead++) {
                if (!isWhitespace(chars[lead])) {
                    break;
                }
            }
            int trail;  // index of last char that is not trailing whitespace
            for (trail = chars.length - 1; trail >= 0; trail--) {
                if (!isWhitespace(chars[trail])) {
                    break;
                }
            }

            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if ((i < lead) || (i > trail) || (escapees.indexOf(c) >= 0)) {
                    builder.append('\\');
                }
                builder.append(c);
            }
            return builder.toString();
    }

    /*
     * Given the value of a binary attribute, returns a string
     * suitable for inclusion in a DN (such as "#CEB1DF80").
     * TBD: This method should actually generate the ber encoding
     * of the binary value
     * <p>
     *  给定二进制属性的值,返回一个适合包含在DN中的字符串(如"#CEB1DF80")。 TBD：这个方法实际上应该生成二进制值的ber编码
     * 
     */
    private static String escapeBinaryValue(byte[] val) {

        StringBuilder builder = new StringBuilder(1 + 2 * val.length);
        builder.append("#");

        for (int i = 0; i < val.length; i++) {
            byte b = val[i];
            builder.append(Character.forDigit(0xF & (b >>> 4), 16));
            builder.append(Character.forDigit(0xF & b, 16));
        }
        return builder.toString();
    }

    /**
     * Given an attribute value string formated according to the rules
     * specified in
     * <a href="http://www.ietf.org/rfc/rfc2253.txt">RFC 2253</a>,
     * returns the unformated value.  Escapes and quotes are
     * stripped away, and hex-encoded UTF-8 is converted to equivalent
     * UTF-16 characters. Returns a string value as a String, and a
     * binary value as a byte array.
     * <p>
     * Legal and illegal values are defined in RFC 2253.
     * This method is generous in accepting the values and does not
     * catch all illegal values.
     * Therefore, passing in an illegal value might not necessarily
     * trigger an <tt>IllegalArgumentException</tt>.
     *
     * <p>
     *  给定根据<a href="http://www.ietf.org/rfc/rfc2253.txt"> RFC 2253 </a>中指定的规则生成的属性值字符串,返回未格式化的值。
     * 转义和引号被去除,并且十六进制编码的UTF-8转换为等效的UTF-16字符。以字符串形式返回字符串值,以字节数组形式返回二进制值。
     * <p>
     *  在RFC 2253中定义了合法和非法值。此方法慷慨地接受值,并且不捕获所有非法值。因此,传入非法值可能不一定会触发<tt> IllegalArgumentException </tt>。
     * 
     * 
     * @param   val     The non-null string to be unescaped.
     * @return          Unescaped value.
     * @throws          IllegalArgumentException When an Illegal value
     *                  is provided.
     */
    public static Object unescapeValue(String val) {

            char[] chars = val.toCharArray();
            int beg = 0;
            int end = chars.length;

            // Trim off leading and trailing whitespace.
            while ((beg < end) && isWhitespace(chars[beg])) {
                ++beg;
            }

            while ((beg < end) && isWhitespace(chars[end - 1])) {
                --end;
            }

            // Add back the trailing whitespace with a preceding '\'
            // (escaped or unescaped) that was taken off in the above
            // loop. Whether or not to retain this whitespace is decided below.
            if (end != chars.length &&
                    (beg < end) &&
                    chars[end - 1] == '\\') {
                end++;
            }
            if (beg >= end) {
                return "";
            }

            if (chars[beg] == '#') {
                // Value is binary (eg: "#CEB1DF80").
                return decodeHexPairs(chars, ++beg, end);
            }

            // Trim off quotes.
            if ((chars[beg] == '\"') && (chars[end - 1] == '\"')) {
                ++beg;
                --end;
            }

            StringBuilder builder = new StringBuilder(end - beg);
            int esc = -1; // index of the last escaped character

            for (int i = beg; i < end; i++) {
                if ((chars[i] == '\\') && (i + 1 < end)) {
                    if (!Character.isLetterOrDigit(chars[i + 1])) {
                        ++i;                            // skip backslash
                        builder.append(chars[i]);       // snarf escaped char
                        esc = i;
                    } else {

                        // Convert hex-encoded UTF-8 to 16-bit chars.
                        byte[] utf8 = getUtf8Octets(chars, i, end);
                        if (utf8.length > 0) {
                            try {
                                builder.append(new String(utf8, "UTF8"));
                            } catch (java.io.UnsupportedEncodingException e) {
                                // shouldn't happen
                            }
                            i += utf8.length * 3 - 1;
                        } else { // no utf8 bytes available, invalid DN

                            // '/' has no meaning, throw exception
                            throw new IllegalArgumentException(
                                "Not a valid attribute string value:" +
                                val + ",improper usage of backslash");
                        }
                    }
                } else {
                    builder.append(chars[i]);   // snarf unescaped char
                }
            }

            // Get rid of the unescaped trailing whitespace with the
            // preceding '\' character that was previously added back.
            int len = builder.length();
            if (isWhitespace(builder.charAt(len - 1)) && esc != (end - 1)) {
                builder.setLength(len - 1);
            }
            return builder.toString();
        }


        /*
         * Given an array of chars (with starting and ending indexes into it)
         * representing bytes encoded as hex-pairs (such as "CEB1DF80"),
         * returns a byte array containing the decoded bytes.
         * <p>
         *  给定表示以十六进制编码的字节(例如"CEB1DF80")的字符数组(具有开始和结束索引),返回包含解码字节的字节数组。
         * 
         */
        private static byte[] decodeHexPairs(char[] chars, int beg, int end) {
            byte[] bytes = new byte[(end - beg) / 2];
            for (int i = 0; beg + 1 < end; i++) {
                int hi = Character.digit(chars[beg], 16);
                int lo = Character.digit(chars[beg + 1], 16);
                if (hi < 0 || lo < 0) {
                    break;
                }
                bytes[i] = (byte)((hi<<4) + lo);
                beg += 2;
            }
            if (beg != end) {
                throw new IllegalArgumentException(
                        "Illegal attribute value: " + new String(chars));
            }
            return bytes;
        }

        /*
         * Given an array of chars (with starting and ending indexes into it),
         * finds the largest prefix consisting of hex-encoded UTF-8 octets,
         * and returns a byte array containing the corresponding UTF-8 octets.
         *
         * Hex-encoded UTF-8 octets look like this:
         *      \03\B1\DF\80
         * <p>
         *  给定一个字符数组(其中包含起始和结尾索引),找到由十六进制编码的UTF-8八位字节组成的最大前缀,并返回包含对应的UTF-8八位字节的字节数组。
         * 
         *  十六进制编码的UTF-8八位字节如下所示：\ 03 \ B1 \ DF \ 80
         * 
         */
        private static byte[] getUtf8Octets(char[] chars, int beg, int end) {
            byte[] utf8 = new byte[(end - beg) / 3];    // allow enough room
            int len = 0;        // index of first unused byte in utf8

            while ((beg + 2 < end) &&
                   (chars[beg++] == '\\')) {
                int hi = Character.digit(chars[beg++], 16);
                int lo = Character.digit(chars[beg++], 16);
                if (hi < 0 || lo < 0) {
                   break;
                }
                utf8[len++] = (byte)((hi<<4) + lo);
            }
            if (len == utf8.length) {
                return utf8;
            } else {
                byte[] res = new byte[len];
                System.arraycopy(utf8, 0, res, 0, len);
                return res;
            }
        }

    /*
     * Best guess as to what RFC 2253 means by "whitespace".
     * <p>
     * 最好猜测什么RFC 2253意味着"空白"。
     * 
     */
    private static boolean isWhitespace(char c) {
        return (c == ' ' || c == '\r');
    }

    /**
     * Serializes only the unparsed RDN, for compactness and to avoid
     * any implementation dependency.
     *
     * <p>
     *  仅序列化未解析的RDN,以实现紧凑性,并避免任何实现依赖性。
     * 
     * @serialData      The RDN string
     */
    private void writeObject(ObjectOutputStream s)
            throws java.io.IOException {
        s.defaultWriteObject();
        s.writeObject(toString());
    }

    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        entries = new ArrayList<>(DEFAULT_SIZE);
        String unparsed = (String) s.readObject();
        try {
            (new Rfc2253Parser(unparsed)).parseRdn(this);
        } catch (InvalidNameException e) {
            // shouldn't happen
            throw new java.io.StreamCorruptedException(
                    "Invalid name: " + unparsed);
        }
    }
}
