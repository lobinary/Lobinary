/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Locale;

import javax.naming.NamingException;
import javax.naming.NamingEnumeration;

/**
  * This class provides a basic implementation
  * of the Attributes interface.
  *<p>
  * BasicAttributes is either case-sensitive or case-insensitive (case-ignore).
  * This property is determined at the time the BasicAttributes constructor
  * is called.
  * In a case-insensitive BasicAttributes, the case of its attribute identifiers
  * is ignored when searching for an attribute, or adding attributes.
  * In a case-sensitive BasicAttributes, the case is significant.
  *<p>
  * When the BasicAttributes class needs to create an Attribute, it
  * uses BasicAttribute. There is no other dependency on BasicAttribute.
  *<p>
  * Note that updates to BasicAttributes (such as adding or removing an attribute)
  * does not affect the corresponding representation in the directory.
  * Updates to the directory can only be effected
  * using operations in the DirContext interface.
  *<p>
  * A BasicAttributes instance is not synchronized against concurrent
  * multithreaded access. Multiple threads trying to access and modify
  * a single BasicAttributes instance should lock the object.
  *
  * <p>
  *  这个类提供了Attributes接口的基本实现。
  * p>
  *  BasicAttributes是区分大小写或不区分大小写(大小写忽略)。此属性在调用BasicAttributes构造函数时确定。
  * 在不区分大小写的BasicAttributes中,在搜索属性或添加属性时忽略其属性标识符的情况。在区分大小写的BasicAttributes中,这种情况很重要。
  * p>
  *  当BasicAttributes类需要创建一个属性时,它使用BasicAttribute。没有对BasicAttribute的其他依赖。
  * p>
  *  请注意,BasicAttributes的更新(例如添加或删除属性)不会影响目录中的相应表示。只有使用DirContext接口中的操作才能更新目录。
  * p>
  *  BasicAttributes实例不会与并发多线程访问同步。尝试访问和修改单个BasicAttributes实例的多个线程应锁定该对象。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see DirContext#getAttributes
  * @see DirContext#modifyAttributes
  * @see DirContext#bind
  * @see DirContext#rebind
  * @see DirContext#createSubcontext
  * @see DirContext#search
  * @since 1.3
  */

public class BasicAttributes implements Attributes {
    /**
     * Indicates whether case of attribute ids is ignored.
     * <p>
     *  指示属性ID的大小写是否被忽略。
     * 
     * 
     * @serial
     */
    private boolean ignoreCase = false;

    // The 'key' in attrs is stored in the 'right case'.
    // If ignoreCase is true, key is aways lowercase.
    // If ignoreCase is false, key is stored as supplied by put().
    // %%% Not declared "private" due to bug 4064984.
    transient Hashtable<String,Attribute> attrs = new Hashtable<>(11);

    /**
      * Constructs a new instance of Attributes.
      * The character case of attribute identifiers
      * is significant when subsequently retrieving or adding attributes.
      * <p>
      *  构造属性的新实例。当随后检索或添加属性时,属性标识符的字符大小是重要的。
      * 
      */
    public BasicAttributes() {
    }

    /**
      * Constructs a new instance of Attributes.
      * If <code>ignoreCase</code> is true, the character case of attribute
      * identifiers is ignored; otherwise the case is significant.
      * <p>
      * 构造属性的新实例。如果<code> ignoreCase </code>为true,则忽略属性标识符的字符大小写;否则情况重大。
      * 
      * 
      * @param ignoreCase true means this attribute set will ignore
      *                   the case of its attribute identifiers
      *                   when retrieving or adding attributes;
      *                   false means case is respected.
      */
    public BasicAttributes(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    /**
      * Constructs a new instance of Attributes with one attribute.
      * The attribute specified by attrID and val are added to the newly
      * created attribute.
      * The character case of attribute identifiers
      * is significant when subsequently retrieving or adding attributes.
      * <p>
      *  构造具有一个属性的属性的新实例。由attrID和val指定的属性将添加到新创建的属性。当随后检索或添加属性时,属性标识符的字符大小是重要的。
      * 
      * 
      * @param attrID   non-null The id of the attribute to add.
      * @param val The value of the attribute to add. If null, a null
      *        value is added to the attribute.
      */
    public BasicAttributes(String attrID, Object val) {
        this();
        this.put(new BasicAttribute(attrID, val));
    }

    /**
      * Constructs a new instance of Attributes with one attribute.
      * The attribute specified by attrID and val are added to the newly
      * created attribute.
      * If <code>ignoreCase</code> is true, the character case of attribute
      * identifiers is ignored; otherwise the case is significant.
      * <p>
      *  构造具有一个属性的属性的新实例。由attrID和val指定的属性将添加到新创建的属性。如果<code> ignoreCase </code>为true,则忽略属性标识符的字符大小写;否则情况重大。
      * 
      * 
      * @param attrID   non-null The id of the attribute to add.
      *           If this attribute set ignores the character
      *           case of its attribute ids, the case of attrID
      *           is ignored.
      * @param val The value of the attribute to add. If null, a null
      *        value is added to the attribute.
      * @param ignoreCase true means this attribute set will ignore
      *                   the case of its attribute identifiers
      *                   when retrieving or adding attributes;
      *                   false means case is respected.
      */
    public BasicAttributes(String attrID, Object val, boolean ignoreCase) {
        this(ignoreCase);
        this.put(new BasicAttribute(attrID, val));
    }

    @SuppressWarnings("unchecked")
    public Object clone() {
        BasicAttributes attrset;
        try {
            attrset = (BasicAttributes)super.clone();
        } catch (CloneNotSupportedException e) {
            attrset = new BasicAttributes(ignoreCase);
        }
        attrset.attrs = (Hashtable<String,Attribute>)attrs.clone();
        return attrset;
    }

    public boolean isCaseIgnored() {
        return ignoreCase;
    }

    public int size() {
        return attrs.size();
    }

    public Attribute get(String attrID) {
        Attribute attr = attrs.get(
                ignoreCase ? attrID.toLowerCase(Locale.ENGLISH) : attrID);
        return (attr);
    }

    public NamingEnumeration<Attribute> getAll() {
        return new AttrEnumImpl();
    }

    public NamingEnumeration<String> getIDs() {
        return new IDEnumImpl();
    }

    public Attribute put(String attrID, Object val) {
        return this.put(new BasicAttribute(attrID, val));
    }

    public Attribute put(Attribute attr) {
        String id = attr.getID();
        if (ignoreCase) {
            id = id.toLowerCase(Locale.ENGLISH);
        }
        return attrs.put(id, attr);
    }

    public Attribute remove(String attrID) {
        String id = (ignoreCase ? attrID.toLowerCase(Locale.ENGLISH) : attrID);
        return attrs.remove(id);
    }

    /**
     * Generates the string representation of this attribute set.
     * The string consists of each attribute identifier and the contents
     * of each attribute. The contents of this string is useful
     * for debugging and is not meant to be interpreted programmatically.
     *
     * <p>
     *  生成此属性集的字符串表示形式。该字符串由每个属性标识符和每个属性的内容组成。这个字符串的内容对于调试是有用的,并不意味着以编程方式解释。
     * 
     * 
     * @return A non-null string listing the contents of this attribute set.
     */
    public String toString() {
        if (attrs.size() == 0) {
            return("No attributes");
        } else {
            return attrs.toString();
        }
    }

    /**
     * Determines whether this <tt>BasicAttributes</tt> is equal to another
     * <tt>Attributes</tt>
     * Two <tt>Attributes</tt> are equal if they are both instances of
     * <tt>Attributes</tt>,
     * treat the case of attribute IDs the same way, and contain the
     * same attributes. Each <tt>Attribute</tt> in this <tt>BasicAttributes</tt>
     * is checked for equality using <tt>Object.equals()</tt>, which may have
     * be overridden by implementations of <tt>Attribute</tt>).
     * If a subclass overrides <tt>equals()</tt>,
     * it should override <tt>hashCode()</tt>
     * as well so that two <tt>Attributes</tt> instances that are equal
     * have the same hash code.
     * <p>
     * 确定此<tt> BasicAttributes </tt>是否等于另一个<tt>属性</tt>两个<tt>属性</tt>相等(如果它们都是<tt>属性</tt>的实例)属性ID的情况相同,并且包含相同的
     * 属性。
     * 使用<tt> Object.equals()</tt>检查此<tt> BasicAttributes </tt>中的每个<tt> Attribute </tt>是否相等,其可能被<tt> Attribu
     * te < / tt>)。
     * 如果子类覆盖<tt> equals()</tt>,它应该重写<tt> hashCode()</tt>,以使两个相等的实例具有相同的哈希码。
     * 
     * 
     * @param obj the possibly null object to compare against.
     *
     * @return true If obj is equal to this BasicAttributes.
     * @see #hashCode
     */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof Attributes)) {
            Attributes target = (Attributes)obj;

            // Check case first
            if (ignoreCase != target.isCaseIgnored()) {
                return false;
            }

            if (size() == target.size()) {
                Attribute their, mine;
                try {
                    NamingEnumeration<?> theirs = target.getAll();
                    while (theirs.hasMore()) {
                        their = (Attribute)theirs.next();
                        mine = get(their.getID());
                        if (!their.equals(mine)) {
                            return false;
                        }
                    }
                } catch (NamingException e) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the hash code of this BasicAttributes.
     *<p>
     * The hash code is computed by adding the hash code of
     * the attributes of this object. If this BasicAttributes
     * ignores case of its attribute IDs, one is added to the hash code.
     * If a subclass overrides <tt>hashCode()</tt>,
     * it should override <tt>equals()</tt>
     * as well so that two <tt>Attributes</tt> instances that are equal
     * have the same hash code.
     *
     * <p>
     *  计算此BasicAttributes的哈希码。
     * p>
     *  通过添加该对象的属性的哈希码来计算哈希码。如果此BasicAttributes忽略其属性ID的情况,则会将一个添加到散列码。
     * 如果子类覆盖<tt> hashCode()</tt>,它应该重写<tt> equals()</tt>,以使两个相等的实例具有相同的哈希码。
     * 
     * 
     * @return an int representing the hash code of this BasicAttributes instance.
     * @see #equals
     */
    public int hashCode() {
        int hash = (ignoreCase ? 1 : 0);
        try {
            NamingEnumeration<?> all = getAll();
            while (all.hasMore()) {
                hash += all.next().hashCode();
            }
        } catch (NamingException e) {}
        return hash;
    }

    /**
     * Overridden to avoid exposing implementation details.
     * <p>
     *  覆盖以避免暴露实施详细信息。
     * 
     * 
     * @serialData Default field (ignoreCase flag -- a boolean), followed by
     * the number of attributes in the set
     * (an int), and then the individual Attribute objects.
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {
        s.defaultWriteObject(); // write out the ignoreCase flag
        s.writeInt(attrs.size());
        Enumeration<Attribute> attrEnum = attrs.elements();
        while (attrEnum.hasMoreElements()) {
            s.writeObject(attrEnum.nextElement());
        }
    }

    /**
     * Overridden to avoid exposing implementation details.
     * <p>
     *  覆盖以避免暴露实施详细信息。
     * 
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        s.defaultReadObject();  // read in the ignoreCase flag
        int n = s.readInt();    // number of attributes
        attrs = (n >= 1)
            ? new Hashtable<String,Attribute>(n * 2)
            : new Hashtable<String,Attribute>(2); // can't have initial size of 0 (grrr...)
        while (--n >= 0) {
            put((Attribute)s.readObject());
        }
    }


class AttrEnumImpl implements NamingEnumeration<Attribute> {

    Enumeration<Attribute> elements;

    public AttrEnumImpl() {
        this.elements = attrs.elements();
    }

    public boolean hasMoreElements() {
        return elements.hasMoreElements();
    }

    public Attribute nextElement() {
        return elements.nextElement();
    }

    public boolean hasMore() throws NamingException {
        return hasMoreElements();
    }

    public Attribute next() throws NamingException {
        return nextElement();
    }

    public void close() throws NamingException {
        elements = null;
    }
}

class IDEnumImpl implements NamingEnumeration<String> {

    Enumeration<Attribute> elements;

    public IDEnumImpl() {
        // Walking through the elements, rather than the keys, gives
        // us attribute IDs that have not been converted to lowercase.
        this.elements = attrs.elements();
    }

    public boolean hasMoreElements() {
        return elements.hasMoreElements();
    }

    public String nextElement() {
        Attribute attr = elements.nextElement();
        return attr.getID();
    }

    public boolean hasMore() throws NamingException {
        return hasMoreElements();
    }

    public String next() throws NamingException {
        return nextElement();
    }

    public void close() throws NamingException {
        elements = null;
    }
}

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability.
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性。
     */
    private static final long serialVersionUID = 4980164073184639448L;
}
