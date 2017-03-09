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

package javax.naming;

import java.util.Vector;
import java.util.Enumeration;

/**
  * This class represents a reference to an object that is found outside of
  * the naming/directory system.
  *<p>
  * Reference provides a way of recording address information about
  * objects which themselves are not directly bound to the naming/directory system.
  *<p>
  * A Reference consists of an ordered list of addresses and class information
  * about the object being referenced.
  * Each address in the list identifies a communications endpoint
  * for the same conceptual object.  The "communications endpoint"
  * is information that indicates how to contact the object. It could
  * be, for example, a network address, a location in memory on the
  * local machine, another process on the same machine, etc.
  * The order of the addresses in the list may be of significance
  * to object factories that interpret the reference.
  *<p>
  * Multiple addresses may arise for
  * various reasons, such as replication or the object offering interfaces
  * over more than one communication mechanism.  The addresses are indexed
  * starting with zero.
  *<p>
  * A Reference also contains information to assist in creating an instance
  * of the object to which this Reference refers.  It contains the class name
  * of that object, and the class name and location of the factory to be used
  * to create the object.
  * The class factory location is a space-separated list of URLs representing
  * the class path used to load the factory.  When the factory class (or
  * any class or resource upon which it depends) needs to be loaded,
  * each URL is used (in order) to attempt to load the class.
  *<p>
  * A Reference instance is not synchronized against concurrent access by multiple
  * threads. Threads that need to access a single Reference concurrently should
  * synchronize amongst themselves and provide the necessary locking.
  *
  * <p>
  *  此类表示对在命名/目录系统外部找到的对象的引用。
  * p>
  *  参考提供了一种记录关于对象的地址信息的方法,这些对象本身不直接绑定到命名/目录系统。
  * p>
  *  引用由有关被引用对象的地址和类信息的有序列表组成。列表中的每个地址标识同一概念对象的通信端点。 "通信端点"是指示如何联系对象的信息。
  * 它可以是例如网络地址,本地机器上的存储器中的位置,同一机器上的另一进程等。列表中的地址的顺序对于解释引用的对象工厂可能是重要的。
  * p>
  *  多个地址可能由于各种原因而出现,诸如复制或对象通过多于一个通信机制提供接口。地址从零开始编入索引。
  * p>
  * 参考还包含有助于创建本参考引用的对象实例的信息。它包含该对象的类名,以及用于创建对象的工厂的类名和位置。类工厂位置是以空格分隔的URL列表,表示用于加载工厂的类路径。
  * 当需要加载工厂类(或它所依赖的任何类或资源)时,将使用每个URL(按顺序)来尝试加载类。
  * p>
  *  引用实例未与多个线程的并发访问同步。需要同时访问单个引用的线程应在它们之间同步并提供必要的锁定。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see RefAddr
  * @see StringRefAddr
  * @see BinaryRefAddr
  * @since 1.3
  */

  /*<p>
  * The serialized form of a Reference object consists of the class
  * name of the object being referenced (a String), a Vector of the
  * addresses (each a RefAddr), the name of the class factory (a
  * String), and the location of the class factory (a String).
  * <p>
  *  引用对象的序列化形式包括被引用对象的类名(一个String),地址的Vector(每个RefAddr),类工厂的名称(String)和类的位置工厂(a String)。
  * 
*/


public class Reference implements Cloneable, java.io.Serializable {
    /**
     * Contains the fully-qualified name of the class of the object to which
     * this Reference refers.
     * <p>
     *  包含此引用所参考的对象的类的完全限定名称。
     * 
     * 
     * @serial
     * @see java.lang.Class#getName
     */
    protected String className;
    /**
     * Contains the addresses contained in this Reference.
     * Initialized by constructor.
     * <p>
     *  包含本参考中包含的地址。由构造函数初始化。
     * 
     * 
     * @serial
     */
    protected Vector<RefAddr> addrs = null;

    /**
     * Contains the name of the factory class for creating
     * an instance of the object to which this Reference refers.
     * Initialized to null.
     * <p>
     *  包含用于创建此引用所参考的对象的实例的工厂类的名称。初始化为null。
     * 
     * 
     * @serial
     */
    protected String classFactory = null;

    /**
     * Contains the location of the factory class.
     * Initialized to null.
     * <p>
     *  包含工厂类的位置。初始化为null。
     * 
     * 
     * @serial
     */
    protected String classFactoryLocation = null;

    /**
      * Constructs a new reference for an object with class name 'className'.
      * Class factory and class factory location are set to null.
      * The newly created reference contains zero addresses.
      *
      * <p>
      * 为类名为'className'的对象构造一个新的引用。类工厂和类工厂位置设置为null。新创建的引用包含零个地址。
      * 
      * 
      * @param className The non-null class name of the object to which
      * this reference refers.
      */
    public Reference(String className) {
        this.className  = className;
        addrs = new Vector<>();
    }

    /**
      * Constructs a new reference for an object with class name 'className' and
      * an address.
      * Class factory and class factory location are set to null.
      *
      * <p>
      *  为类名为'className'和地址的对象构造一个新的引用。类工厂和类工厂位置设置为null。
      * 
      * 
      * @param className The non-null class name of the object to
      * which this reference refers.
      * @param addr The non-null address of the object.
      */
    public Reference(String className, RefAddr addr) {
        this.className = className;
        addrs = new Vector<>();
        addrs.addElement(addr);
    }

    /**
      * Constructs a new reference for an object with class name 'className',
      * and the class name and location of the object's factory.
      *
      * <p>
      *  为类名为'className'的对象构造一个新的引用,以及对象工厂的类名和位置。
      * 
      * 
      * @param className The non-null class name of the object to which
      *                         this reference refers.
      * @param factory  The possibly null class name of the object's factory.
      * @param factoryLocation
      *         The possibly null location from which to load
      *         the factory (e.g. URL)
      * @see javax.naming.spi.ObjectFactory
      * @see javax.naming.spi.NamingManager#getObjectInstance
      */
    public Reference(String className, String factory, String factoryLocation) {
        this(className);
        classFactory = factory;
        classFactoryLocation = factoryLocation;
    }

    /**
      * Constructs a new reference for an object with class name 'className',
      * the class name and location of the object's factory, and the address for
      * the object.
      *
      * <p>
      *  为类名为'className'的对象构造一个新的引用,类名称和对象工厂的位置以及对象的地址。
      * 
      * 
      * @param className The non-null class name of the object to
      *         which this reference refers.
      * @param factory  The possibly null class name of the object's factory.
      * @param factoryLocation  The possibly null location from which
      *                         to load the factory (e.g. URL)
      * @param addr     The non-null address of the object.
      * @see javax.naming.spi.ObjectFactory
      * @see javax.naming.spi.NamingManager#getObjectInstance
      */
    public Reference(String className, RefAddr addr,
                     String factory, String factoryLocation) {
        this(className, addr);
        classFactory = factory;
        classFactoryLocation = factoryLocation;
    }

    /**
      * Retrieves the class name of the object to which this reference refers.
      *
      * <p>
      *  检索此引用所引用的对象的类名。
      * 
      * 
      * @return The non-null fully-qualified class name of the object.
      *         (e.g. "java.lang.String")
      */
    public String getClassName() {
        return className;
    }

    /**
      * Retrieves the class name of the factory of the object
      * to which this reference refers.
      *
      * <p>
      *  检索此引用所引用的对象的工厂的类名。
      * 
      * 
      * @return The possibly null fully-qualified class name of the factory.
      *         (e.g. "java.lang.String")
      */
    public String getFactoryClassName() {
        return classFactory;
    }

    /**
      * Retrieves the location of the factory of the object
      * to which this reference refers.
      * If it is a codebase, then it is an ordered list of URLs,
      * separated by spaces, listing locations from where the factory
      * class definition should be loaded.
      *
      * <p>
      *  检索此引用所针对的对象的工厂的位置。如果它是一个代码库,那么它是一个URL的有序列表,用空格分隔,列出应该加载工厂类定义的位置。
      * 
      * 
      * @return The possibly null string containing the
      *                 location for loading in the factory's class.
      */
    public String getFactoryClassLocation() {
        return classFactoryLocation;
    }

    /**
      * Retrieves the first address that has the address type 'addrType'.
      * String.compareTo() is used to test the equality of the address types.
      *
      * <p>
      *  检索地址类型为"addrType"的第一个地址。 String.compareTo()用于测试地址类型的相等性。
      * 
      * 
      * @param addrType The non-null address type for which to find the address.
      * @return The address in this reference with address type 'addrType;
      *         null if no such address exist.
      */
    public RefAddr get(String addrType) {
        int len = addrs.size();
        RefAddr addr;
        for (int i = 0; i < len; i++) {
            addr = addrs.elementAt(i);
            if (addr.getType().compareTo(addrType) == 0)
                return addr;
        }
        return null;
    }

    /**
      * Retrieves the address at index posn.
      * <p>
      *  检索索引posn处的地址。
      * 
      * 
      * @param posn The index of the address to retrieve.
      * @return The address at the 0-based index posn. It must be in the
      *         range [0,getAddressCount()).
      * @exception ArrayIndexOutOfBoundsException If posn not in the specified
      *         range.
      */
    public RefAddr get(int posn) {
        return addrs.elementAt(posn);
    }

    /**
      * Retrieves an enumeration of the addresses in this reference.
      * When addresses are added, changed or removed from this reference,
      * its effects on this enumeration are undefined.
      *
      * <p>
      *  检索此引用中的地址的枚举。当地址被添加,更改或从此引用中删除时,其对此枚举的影响未定义。
      * 
      * 
      * @return An non-null enumeration of the addresses
      *         (<tt>RefAddr</tt>) in this reference.
      *         If this reference has zero addresses, an enumeration with
      *         zero elements is returned.
      */
    public Enumeration<RefAddr> getAll() {
        return addrs.elements();
    }

    /**
      * Retrieves the number of addresses in this reference.
      *
      * <p>
      *  检索此引用中的地址数。
      * 
      * 
      * @return The nonnegative number of addresses in this reference.
      */
    public int size() {
        return addrs.size();
    }

    /**
      * Adds an address to the end of the list of addresses.
      *
      * <p>
      *  将地址添加到地址列表的末尾。
      * 
      * 
      * @param addr The non-null address to add.
      */
    public void add(RefAddr addr) {
        addrs.addElement(addr);
    }

    /**
      * Adds an address to the list of addresses at index posn.
      * All addresses at index posn or greater are shifted up
      * the list by one (away from index 0).
      *
      * <p>
      * 将地址添加到索引posn处的地址列表。索引posn或更大的所有地址在列表上向上移动一个(远离索引0)。
      * 
      * 
      * @param posn The 0-based index of the list to insert addr.
      * @param addr The non-null address to add.
      * @exception ArrayIndexOutOfBoundsException If posn not in the specified
      *         range.
      */
    public void add(int posn, RefAddr addr) {
        addrs.insertElementAt(addr, posn);
    }

    /**
      * Deletes the address at index posn from the list of addresses.
      * All addresses at index greater than posn are shifted down
      * the list by one (towards index 0).
      *
      * <p>
      *  从地址列表中删除索引posn处的地址。索引大于posn的所有地址在列表中向下移动一个(朝向索引0)。
      * 
      * 
      * @param posn The 0-based index of in address to delete.
      * @return The address removed.
      * @exception ArrayIndexOutOfBoundsException If posn not in the specified
      *         range.
      */
    public Object remove(int posn) {
        Object r = addrs.elementAt(posn);
        addrs.removeElementAt(posn);
        return r;
    }

    /**
      * Deletes all addresses from this reference.
      * <p>
      *  从此引用中删除所有地址。
      * 
      */
    public void clear() {
        addrs.setSize(0);
    }

    /**
      * Determines whether obj is a reference with the same addresses
      * (in same order) as this reference.
      * The addresses are checked using RefAddr.equals().
      * In addition to having the same addresses, the Reference also needs to
      * have the same class name as this reference.
      * The class factory and class factory location are not checked.
      * If obj is null or not an instance of Reference, null is returned.
      *
      * <p>
      *  确定obj是否是与此引用具有相同地址(按相同顺序)的引用。使用RefAddr.equals()检查地址。除了具有相同的地址之外,引用还需要具有与该引用相同的类名。不检查类工厂和类工厂位置。
      * 如果obj为null或不是Reference的实例,则返回null。
      * 
      * 
      * @param obj The possibly null object to check.
      * @return true if obj is equal to this reference; false otherwise.
      */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof Reference)) {
            Reference target = (Reference)obj;
            // ignore factory information
            if (target.className.equals(this.className) &&
                target.size() ==  this.size()) {
                Enumeration<RefAddr> mycomps = getAll();
                Enumeration<RefAddr> comps = target.getAll();
                while (mycomps.hasMoreElements())
                    if (!(mycomps.nextElement().equals(comps.nextElement())))
                        return false;
                return true;
            }
        }
        return false;
    }

    /**
      * Computes the hash code of this reference.
      * The hash code is the sum of the hash code of its addresses.
      *
      * <p>
      *  计算此引用的哈希码。哈希码是其地址的哈希码的总和。
      * 
      * 
      * @return A hash code of this reference as an int.
      */
    public int hashCode() {
        int hash = className.hashCode();
        for (Enumeration<RefAddr> e = getAll(); e.hasMoreElements();)
            hash += e.nextElement().hashCode();
        return hash;
    }

    /**
      * Generates the string representation of this reference.
      * The string consists of the class name to which this reference refers,
      * and the string representation of each of its addresses.
      * This representation is intended for display only and not to be parsed.
      *
      * <p>
      *  生成此引用的字符串表示形式。该字符串由该引用引用的类名称和其每个地址的字符串表示形式组成。此表示仅用于显示,不用于解析。
      * 
      * 
      * @return The non-null string representation of this reference.
      */
    public String toString() {
        StringBuffer buf = new StringBuffer("Reference Class Name: " +
                                            className + "\n");
        int len = addrs.size();
        for (int i = 0; i < len; i++)
            buf.append(get(i).toString());

        return buf.toString();
    }

    /**
     * Makes a copy of this reference using its class name
     * list of addresses, class factory name and class factory location.
     * Changes to the newly created copy does not affect this Reference
     * and vice versa.
     * <p>
     *  使用其类名称地址列表,类工厂名称和类工厂位置创建此引用的副本。对新创建的副本的更改不会影响此引用,反之亦然。
     * 
     */
    public Object clone() {
        Reference r = new Reference(className, classFactory, classFactoryLocation);
        Enumeration<RefAddr> a = getAll();
        r.addrs = new Vector<>();

        while (a.hasMoreElements())
            r.addrs.addElement(a.nextElement());
        return r;
    }
    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -1673475790065791735L;
};
