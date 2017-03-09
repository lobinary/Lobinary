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

package javax.management;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;

import static javax.management.ImmutableDescriptor.nonNullDescriptor;

/**
 * <p>Describes the management interface exposed by an MBean; that is,
 * the set of attributes and operations which are available for
 * management operations.  Instances of this class are immutable.
 * Subclasses may be mutable but this is not recommended.</p>
 *
 * <p id="info-changed">Usually the {@code MBeanInfo} for any given MBean does
 * not change over the lifetime of that MBean.  Dynamic MBeans can change their
 * {@code MBeanInfo} and in that case it is recommended that they emit a {@link
 * Notification} with a {@linkplain Notification#getType() type} of {@code
 * "jmx.mbean.info.changed"} and a {@linkplain Notification#getUserData()
 * userData} that is the new {@code MBeanInfo}.  This is not required, but
 * provides a conventional way for clients of the MBean to discover the change.
 * See also the <a href="Descriptor.html#immutableInfo">immutableInfo</a> and
 * <a href="Descriptor.html#infoTimeout">infoTimeout</a> fields in the {@code
 * MBeanInfo} {@link Descriptor}.</p>
 *
 * <p>The contents of the <code>MBeanInfo</code> for a Dynamic MBean
 * are determined by its {@link DynamicMBean#getMBeanInfo
 * getMBeanInfo()} method.  This includes Open MBeans and Model
 * MBeans, which are kinds of Dynamic MBeans.</p>
 *
 * <p>The contents of the <code>MBeanInfo</code> for a Standard MBean
 * are determined by the MBean server as follows:</p>
 *
 * <ul>
 *
 * <li>{@link #getClassName()} returns the Java class name of the MBean
 * object;
 *
 * <li>{@link #getConstructors()} returns the list of all public
 * constructors in that object;
 *
 * <li>{@link #getAttributes()} returns the list of all attributes
 * whose existence is deduced from the presence in the MBean interface
 * of a <code>get<i>Name</i></code>, <code>is<i>Name</i></code>, or
 * <code>set<i>Name</i></code> method that conforms to the conventions
 * for Standard MBeans;
 *
 * <li>{@link #getOperations()} returns the list of all methods in
 * the MBean interface that do not represent attributes;
 *
 * <li>{@link #getNotifications()} returns an empty array if the MBean
 * does not implement the {@link NotificationBroadcaster} interface,
 * otherwise the result of calling {@link
 * NotificationBroadcaster#getNotificationInfo()} on it;
 *
 * <li>{@link #getDescriptor()} returns a descriptor containing the contents
 * of any descriptor annotations in the MBean interface (see
 * {@link DescriptorKey &#64;DescriptorKey}).
 *
 * </ul>
 *
 * <p>The description returned by {@link #getDescription()} and the
 * descriptions of the contained attributes and operations are not specified.</p>
 *
 * <p>The remaining details of the <code>MBeanInfo</code> for a
 * Standard MBean are not specified.  This includes the description of
 * any contained constructors, and notifications; the names
 * of parameters to constructors and operations; and the descriptions of
 * constructor parameters.</p>
 *
 * <p>
 *  <p>描述MBean公开的管理接口;即,可用于管理操作的属性和操作的集合。这个类的实例是不可变的。子类可以是可变的,但不推荐这样做。</p>
 * 
 *  <p id ="info-changed">通常,任何给定MBean的{@code MBeanInfo}在该MBean的生命周期内不会更改。
 * 动态MBean可以更改其{@code MBeanInfo},在这种情况下,建议它们发出{@link Notification} {@linkplain Notification#getType()type}
 *  {@code} jmx.mbean.info.changed "}和{@linkplain Notification#getUserData()userData},它是新的{@code MBeanInfo}
 * 。
 *  <p id ="info-changed">通常,任何给定MBean的{@code MBeanInfo}在该MBean的生命周期内不会更改。
 * 这不是必需的,但为MBean的客户端提供发现改变的常规方式。
 * 另请参阅{@code MBeanInfo} {@link描述符}中的<a href="Descriptor.html#immableableInfo"> immutableInfo </a>和<a href="Descriptor.html#infoTimeout">
 *  infoTimeout </a>字段}。
 * 这不是必需的,但为MBean的客户端提供发现改变的常规方式。</p>。
 * 
 *  <p>动态MBean的<code> MBeanInfo </code>的内容由其{@link DynamicMBean#getMBeanInfo getMBeanInfo()}方法确定。
 * 这包括Open MBean和Model MBean,它们是动态MBean的种类。</p>。
 * 
 *  <p>标准MBean的<code> MBeanInfo </code>的内容由MBean服务器确定如下：</p>
 * 
 * <ul>
 * 
 * <li> {@ link #getClassName()}返回MBean对象的Java类名称;
 * 
 *  <li> {@ link #getConstructors()}返回该对象中所有公共构造函数的列表;
 * 
 *  <li> {@ link #getAttributes()}返回从<code> get <i> Name </i> </code>,<code>中的MBean接口中出现存在的所有属性的列表。
 * 是符合标准MBean约定的<i> Name </i> </code>或<code> set <i> Name </i> </code>。
 * 
 *  <li> {@ link #getOperations()}返回MBean界面中不表示属性的所有方法的列表;
 * 
 *  <li>如果MBean未实施{@link NotificationBroadcaster}接口,或者调用{@link NotificationBroadcaster#getNotificationInfo()}
 * 的结果,{@ link #getNotifications()}将返回一个空数组;。
 * 
 *  <li> {@ link #getDescriptor()}返回一个包含MBean接口中任何描述符注释内容的描述符(请参阅{@link DescriptorKey @DescriptorKey})。
 * 
 * </ul>
 * 
 *  <p> {@link #getDescription()}返回的描述和包含的属性和操作的说明未指定。</p>
 * 
 *  <p>未指定标准MBean的<code> MBeanInfo </code>的其余详细信息。这包括任何包含的构造函数和通知的描述;构造函数和操作的参数名称;和构造函数参数的描述。</p>
 * 
 * 
 * @since 1.5
 */
public class MBeanInfo implements Cloneable, Serializable, DescriptorRead {

    /* Serial version */
    static final long serialVersionUID = -6451021435135161911L;

    /**
    /* <p>
    /* 
     * @serial The Descriptor for the MBean.  This field
     * can be null, which is equivalent to an empty Descriptor.
     */
    private transient Descriptor descriptor;

    /**
    /* <p>
    /* 
     * @serial The human readable description of the class.
     */
    private final String description;

    /**
    /* <p>
    /* 
     * @serial The MBean qualified name.
     */
    private final String className;

    /**
    /* <p>
    /* 
     * @serial The MBean attribute descriptors.
     */
    private final MBeanAttributeInfo[] attributes;

    /**
    /* <p>
    /* 
     * @serial The MBean operation descriptors.
     */
    private final MBeanOperationInfo[] operations;

     /**
     /* <p>
     /* 
     * @serial The MBean constructor descriptors.
     */
    private final MBeanConstructorInfo[] constructors;

    /**
    /* <p>
    /* 
     * @serial The MBean notification descriptors.
     */
    private final MBeanNotificationInfo[] notifications;

    private transient int hashCode;

    /**
     * <p>True if this class is known not to override the array-valued
     * getters of MBeanInfo.  Obviously true for MBeanInfo itself, and true
     * for a subclass where we succeed in reflecting on the methods
     * and discover they are not overridden.</p>
     *
     * <p>The purpose of this variable is to avoid cloning the arrays
     * when doing operations like {@link #equals} where we know they
     * will not be changed.  If a subclass overrides a getter, we
     * cannot access the corresponding array directly.</p>
     * <p>
     * <p>如果已知此类不会覆盖MBeanInfo的数组值getter,则为true。显然对于MBeanInfo本身是真的,对于一个子类,我们成功地反映了方法,发现它们没有被覆盖。</p>
     * 
     *  <p>此变量的目的是避免在执行像{@link #equals}这样的操作时克隆数组,我们知道它们不会被改变。如果一个子类覆盖了一个getter,我们不能直接访问相应的数组。</p>
     * 
     */
    private final transient boolean arrayGettersSafe;

    /**
     * Constructs an <CODE>MBeanInfo</CODE>.
     *
     * <p>
     *  构造一个<CODE> MBeanInfo </CODE>。
     * 
     * 
     * @param className The name of the Java class of the MBean described
     * by this <CODE>MBeanInfo</CODE>.  This value may be any
     * syntactically legal Java class name.  It does not have to be a
     * Java class known to the MBean server or to the MBean's
     * ClassLoader.  If it is a Java class known to the MBean's
     * ClassLoader, it is recommended but not required that the
     * class's public methods include those that would appear in a
     * Standard MBean implementing the attributes and operations in
     * this MBeanInfo.
     * @param description A human readable description of the MBean (optional).
     * @param attributes The list of exposed attributes of the MBean.
     * This may be null with the same effect as a zero-length array.
     * @param constructors The list of public constructors of the
     * MBean.  This may be null with the same effect as a zero-length
     * array.
     * @param operations The list of operations of the MBean.  This
     * may be null with the same effect as a zero-length array.
     * @param notifications The list of notifications emitted.  This
     * may be null with the same effect as a zero-length array.
     */
    public MBeanInfo(String className,
                     String description,
                     MBeanAttributeInfo[] attributes,
                     MBeanConstructorInfo[] constructors,
                     MBeanOperationInfo[] operations,
                     MBeanNotificationInfo[] notifications)
            throws IllegalArgumentException {
        this(className, description, attributes, constructors, operations,
             notifications, null);
    }

    /**
     * Constructs an <CODE>MBeanInfo</CODE>.
     *
     * <p>
     *  构造一个<CODE> MBeanInfo </CODE>。
     * 
     * 
     * @param className The name of the Java class of the MBean described
     * by this <CODE>MBeanInfo</CODE>.  This value may be any
     * syntactically legal Java class name.  It does not have to be a
     * Java class known to the MBean server or to the MBean's
     * ClassLoader.  If it is a Java class known to the MBean's
     * ClassLoader, it is recommended but not required that the
     * class's public methods include those that would appear in a
     * Standard MBean implementing the attributes and operations in
     * this MBeanInfo.
     * @param description A human readable description of the MBean (optional).
     * @param attributes The list of exposed attributes of the MBean.
     * This may be null with the same effect as a zero-length array.
     * @param constructors The list of public constructors of the
     * MBean.  This may be null with the same effect as a zero-length
     * array.
     * @param operations The list of operations of the MBean.  This
     * may be null with the same effect as a zero-length array.
     * @param notifications The list of notifications emitted.  This
     * may be null with the same effect as a zero-length array.
     * @param descriptor The descriptor for the MBean.  This may be null
     * which is equivalent to an empty descriptor.
     *
     * @since 1.6
     */
    public MBeanInfo(String className,
                     String description,
                     MBeanAttributeInfo[] attributes,
                     MBeanConstructorInfo[] constructors,
                     MBeanOperationInfo[] operations,
                     MBeanNotificationInfo[] notifications,
                     Descriptor descriptor)
            throws IllegalArgumentException {

        this.className = className;

        this.description = description;

        if (attributes == null)
            attributes = MBeanAttributeInfo.NO_ATTRIBUTES;
        this.attributes = attributes;

        if (operations == null)
            operations = MBeanOperationInfo.NO_OPERATIONS;
        this.operations = operations;

        if (constructors == null)
            constructors = MBeanConstructorInfo.NO_CONSTRUCTORS;
        this.constructors = constructors;

        if (notifications == null)
            notifications = MBeanNotificationInfo.NO_NOTIFICATIONS;
        this.notifications = notifications;

        if (descriptor == null)
            descriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR;
        this.descriptor = descriptor;

        this.arrayGettersSafe =
                arrayGettersSafe(this.getClass(), MBeanInfo.class);
    }

    /**
     * <p>Returns a shallow clone of this instance.
     * The clone is obtained by simply calling <tt>super.clone()</tt>,
     * thus calling the default native shallow cloning mechanism
     * implemented by <tt>Object.clone()</tt>.
     * No deeper cloning of any internal field is made.</p>
     *
     * <p>Since this class is immutable, the clone method is chiefly of
     * interest to subclasses.</p>
     * <p>
     *  <p>返回此实例的浅克隆。通过简单调用<tt> super.clone()</tt>获得克隆,从而调用由<tt> Object.clone()</tt>实现的默认本机浅克隆机制。
     * 不会对任何内部字段进行更深层次的克隆。</p>。
     * 
     *  <p>由于这个类是不可变的,克隆方法主要是子类的兴趣。</p>
     * 
     */
     @Override
     public Object clone () {
         try {
             return super.clone() ;
         } catch (CloneNotSupportedException e) {
             // should not happen as this class is cloneable
             return null;
         }
     }


    /**
     * Returns the name of the Java class of the MBean described by
     * this <CODE>MBeanInfo</CODE>.
     *
     * <p>
     *  返回此<CODE> MBeanInfo </CODE>描述的MBean的Java类的名称。
     * 
     * 
     * @return the class name.
     */
    public String getClassName()  {
        return className;
    }

    /**
     * Returns a human readable description of the MBean.
     *
     * <p>
     *  返回MBean的人性化描述。
     * 
     * 
     * @return the description.
     */
    public String getDescription()  {
        return description;
    }

    /**
     * Returns the list of attributes exposed for management.
     * Each attribute is described by an <CODE>MBeanAttributeInfo</CODE> object.
     *
     * The returned array is a shallow copy of the internal array,
     * which means that it is a copy of the internal array of
     * references to the <CODE>MBeanAttributeInfo</CODE> objects
     * but that each referenced <CODE>MBeanAttributeInfo</CODE> object is not copied.
     *
     * <p>
     *  返回公开用于管理的属性列表。每个属性由<CODE> MBeanAttributeInfo </CODE>对象描述。
     * 
     *  返回的数组是内部数组的浅拷贝,这意味着它是引用到<CODE> MBeanAttributeInfo </CODE>对象的内部数组的副本,但每个引用的<CODE> MBeanAttributeInfo 
     * </CODE>对象不复制。
     * 
     * 
     * @return  An array of <CODE>MBeanAttributeInfo</CODE> objects.
     */
    public MBeanAttributeInfo[] getAttributes()   {
        MBeanAttributeInfo[] as = nonNullAttributes();
        if (as.length == 0)
            return as;
        else
            return as.clone();
    }

    private MBeanAttributeInfo[] fastGetAttributes() {
        if (arrayGettersSafe)
            return nonNullAttributes();
        else
            return getAttributes();
    }

    /**
     * Return the value of the attributes field, or an empty array if
     * the field is null.  This can't happen with a
     * normally-constructed instance of this class, but can if the
     * instance was deserialized from another implementation that
     * allows the field to be null.  It would be simpler if we enforced
     * the class invariant that these fields cannot be null by writing
     * a readObject() method, but that would require us to define the
     * various array fields as non-final, which is annoying because
     * conceptually they are indeed final.
     * <p>
     * 返回属性字段的值,如果字段为空,则返回空数组。这不能发生在这个类的正常构造的实例,但是如果实例被反序列化从允许字段为null的另一个实现。
     * 如果我们通过写一个readObject()方法强制执行类不变量这些字段不能为null,但是这将要求我们将各种数组字段定义为非final,这是令人讨厌的,因为在概念上它们确实是final。
     * 
     */
    private MBeanAttributeInfo[] nonNullAttributes() {
        return (attributes == null) ?
            MBeanAttributeInfo.NO_ATTRIBUTES : attributes;
    }

    /**
     * Returns the list of operations  of the MBean.
     * Each operation is described by an <CODE>MBeanOperationInfo</CODE> object.
     *
     * The returned array is a shallow copy of the internal array,
     * which means that it is a copy of the internal array of
     * references to the <CODE>MBeanOperationInfo</CODE> objects
     * but that each referenced <CODE>MBeanOperationInfo</CODE> object is not copied.
     *
     * <p>
     *  返回MBean的操作列表。每个操作由<CODE> MBeanOperationInfo </CODE>对象描述。
     * 
     *  返回的数组是内部数组的浅拷贝,这意味着它是引用到<CODE> MBeanOperationInfo </CODE>对象的内部数组的副本,但每个引用的<CODE> MBeanOperationInfo 
     * </CODE>对象不复制。
     * 
     * 
     * @return  An array of <CODE>MBeanOperationInfo</CODE> objects.
     */
    public MBeanOperationInfo[] getOperations()  {
        MBeanOperationInfo[] os = nonNullOperations();
        if (os.length == 0)
            return os;
        else
            return os.clone();
    }

    private MBeanOperationInfo[] fastGetOperations() {
        if (arrayGettersSafe)
            return nonNullOperations();
        else
            return getOperations();
    }

    private MBeanOperationInfo[] nonNullOperations() {
        return (operations == null) ?
            MBeanOperationInfo.NO_OPERATIONS : operations;
    }

    /**
     * <p>Returns the list of the public constructors of the MBean.
     * Each constructor is described by an
     * <CODE>MBeanConstructorInfo</CODE> object.</p>
     *
     * <p>The returned array is a shallow copy of the internal array,
     * which means that it is a copy of the internal array of
     * references to the <CODE>MBeanConstructorInfo</CODE> objects but
     * that each referenced <CODE>MBeanConstructorInfo</CODE> object
     * is not copied.</p>
     *
     * <p>The returned list is not necessarily exhaustive.  That is,
     * the MBean may have a public constructor that is not in the
     * list.  In this case, the MBean server can construct another
     * instance of this MBean's class using that constructor, even
     * though it is not listed here.</p>
     *
     * <p>
     *  <p>返回MBean的公共构造函数的列表。每个构造函数由<CODE> MBeanConstructorInfo </CODE>对象描述。</p>
     * 
     *  <p>返回的数组是内部数组的浅拷贝,这意味着它是引用到<CODE> MBeanConstructorInfo </CODE>对象的内部数组的副本,但每个引用<CODE> MBeanConstructo
     * rInfo </CODE >对象未被复制。
     * </p>。
     * 
     * <p>返回的列表不一定是详尽的。也就是说,MBean可能有一个不在列表中的公共构造函数。在这种情况下,MBean服务器可以使用该构造函数构造此MBean类的另一个实例,即使此处未列出。</p>
     * 
     * 
     * @return  An array of <CODE>MBeanConstructorInfo</CODE> objects.
     */
    public MBeanConstructorInfo[] getConstructors()  {
        MBeanConstructorInfo[] cs = nonNullConstructors();
        if (cs.length == 0)
            return cs;
        else
            return cs.clone();
    }

    private MBeanConstructorInfo[] fastGetConstructors() {
        if (arrayGettersSafe)
            return nonNullConstructors();
        else
            return getConstructors();
    }

    private MBeanConstructorInfo[] nonNullConstructors() {
        return (constructors == null) ?
            MBeanConstructorInfo.NO_CONSTRUCTORS : constructors;
    }

    /**
     * Returns the list of the notifications emitted by the MBean.
     * Each notification is described by an <CODE>MBeanNotificationInfo</CODE> object.
     *
     * The returned array is a shallow copy of the internal array,
     * which means that it is a copy of the internal array of
     * references to the <CODE>MBeanNotificationInfo</CODE> objects
     * but that each referenced <CODE>MBeanNotificationInfo</CODE> object is not copied.
     *
     * <p>
     *  返回MBean发出的通知的列表。每个通知由<CODE> MBeanNotificationInfo </CODE>对象描述。
     * 
     *  返回的数组是内部数组的浅拷贝,这意味着它是引用到<CODE> MBeanNotificationInfo </CODE>对象的内部数组的副本,但每个引用的<CODE> MBeanNotificatio
     * nInfo </CODE>对象不复制。
     * 
     * 
     * @return  An array of <CODE>MBeanNotificationInfo</CODE> objects.
     */
    public MBeanNotificationInfo[] getNotifications()  {
        MBeanNotificationInfo[] ns = nonNullNotifications();
        if (ns.length == 0)
            return ns;
        else
            return ns.clone();
    }

    private MBeanNotificationInfo[] fastGetNotifications() {
        if (arrayGettersSafe)
            return nonNullNotifications();
        else
            return getNotifications();
    }

    private MBeanNotificationInfo[] nonNullNotifications() {
        return (notifications == null) ?
            MBeanNotificationInfo.NO_NOTIFICATIONS : notifications;
    }

    /**
     * Get the descriptor of this MBeanInfo.  Changing the returned value
     * will have no affect on the original descriptor.
     *
     * <p>
     *  获取此MBeanInfo的描述符。更改返回值将不会影响原始描述符。
     * 
     * 
     * @return a descriptor that is either immutable or a copy of the original.
     *
     * @since 1.6
     */
    public Descriptor getDescriptor() {
        return (Descriptor) nonNullDescriptor(descriptor).clone();
    }

    @Override
    public String toString() {
        return
            getClass().getName() + "[" +
            "description=" + getDescription() + ", " +
            "attributes=" + Arrays.asList(fastGetAttributes()) + ", " +
            "constructors=" + Arrays.asList(fastGetConstructors()) + ", " +
            "operations=" + Arrays.asList(fastGetOperations()) + ", " +
            "notifications=" + Arrays.asList(fastGetNotifications()) + ", " +
            "descriptor=" + getDescriptor() +
            "]";
    }

    /**
     * <p>Compare this MBeanInfo to another.  Two MBeanInfo objects
     * are equal if and only if they return equal values for {@link
     * #getClassName()}, for {@link #getDescription()}, and for
     * {@link #getDescriptor()}, and the
     * arrays returned by the two objects for {@link
     * #getAttributes()}, {@link #getOperations()}, {@link
     * #getConstructors()}, and {@link #getNotifications()} are
     * pairwise equal.  Here "equal" means {@link
     * Object#equals(Object)}, not identity.</p>
     *
     * <p>If two MBeanInfo objects return the same values in one of
     * their arrays but in a different order then they are not equal.</p>
     *
     * <p>
     *  <p>将此MBeanInfo与另一个进行比较。
     * 当且仅当它们为{@link #getClassName()},{@link #getDescription()}和{@link #getDescriptor()}返回相同的值时,两个MBeanInfo对
     * 象是相等的, {@link #getAttributes()},{@link #getOperations()},{@link #getConstructors()}和{@link #getNotifications()}
     * 的两个对象成对相等。
     *  <p>将此MBeanInfo与另一个进行比较。这里"等于"表示{@link Object#equals(Object)},而不是身份。</p>。
     * 
     *  <p>如果两个MBeanInfo对象在其数组中返回相同的值,但顺序不同,则它们不相等。</p>
     * 
     * 
     * @param o the object to compare to.
     *
     * @return true if and only if <code>o</code> is an MBeanInfo that is equal
     * to this one according to the rules above.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MBeanInfo))
            return false;
        MBeanInfo p = (MBeanInfo) o;
        if (!isEqual(getClassName(),  p.getClassName()) ||
                !isEqual(getDescription(), p.getDescription()) ||
                !getDescriptor().equals(p.getDescriptor())) {
            return false;
        }

        return
            (Arrays.equals(p.fastGetAttributes(), fastGetAttributes()) &&
             Arrays.equals(p.fastGetOperations(), fastGetOperations()) &&
             Arrays.equals(p.fastGetConstructors(), fastGetConstructors()) &&
             Arrays.equals(p.fastGetNotifications(), fastGetNotifications()));
    }

    @Override
    public int hashCode() {
        /* Since computing the hashCode is quite expensive, we cache it.
           If by some terrible misfortune the computed value is 0, the
           caching won't work and we will recompute it every time.

           We don't bother synchronizing, because, at worst, n different
        /* <p>
        /*  如果由于某些可怕的不幸,计算值为0,缓存将不工作,我们将每次重新计算。
        /* 
        /* 我们不打扰同步,因为,在最坏的情况下,n不同
        /* 
        /* 
           threads will compute the same hashCode at the same time.  */
        if (hashCode != 0)
            return hashCode;

        hashCode = Objects.hash(getClassName(), getDescriptor())
                ^ Arrays.hashCode(fastGetAttributes())
                ^ Arrays.hashCode(fastGetOperations())
                ^ Arrays.hashCode(fastGetConstructors())
                ^ Arrays.hashCode(fastGetNotifications());

        return hashCode;
    }

    /**
     * Cached results of previous calls to arrayGettersSafe.  This is
     * a WeakHashMap so that we don't prevent a class from being
     * garbage collected just because we know whether it's immutable.
     * <p>
     *  先前调用arrayGettersSafe的缓存结果。这是一个WeakHashMap,所以我们不会阻止一个类被垃圾回收只是因为我们知道它是不可变的。
     * 
     */
    private static final Map<Class<?>, Boolean> arrayGettersSafeMap =
        new WeakHashMap<Class<?>, Boolean>();

    /**
     * Return true if <code>subclass</code> is known to preserve the
     * immutability of <code>immutableClass</code>.  The class
     * <code>immutableClass</code> is a reference class that is known
     * to be immutable.  The subclass <code>subclass</code> is
     * considered immutable if it does not override any public method
     * of <code>immutableClass</code> whose name begins with "get".
     * This is obviously not an infallible test for immutability,
     * but it works for the public interfaces of the MBean*Info classes.
     * <p>
     *  如果已知<code>子类</code>保留<code> immutableClass </code>的不变性,则返回true。
     * 类<code> immutableClass </code>是一个已知是不可变的引用类。
     * 如果子类<code>子类</code>不覆盖其名称以"get"开头的<code> immutableClass </code>的任何公共方法,则被认为是不可变的。
     * 这显然不是一个不可变性的可靠测试,但它适用于MBean * Info类的公共接口。
     * 
    */
    static boolean arrayGettersSafe(Class<?> subclass, Class<?> immutableClass) {
        if (subclass == immutableClass)
            return true;
        synchronized (arrayGettersSafeMap) {
            Boolean safe = arrayGettersSafeMap.get(subclass);
            if (safe == null) {
                try {
                    ArrayGettersSafeAction action =
                        new ArrayGettersSafeAction(subclass, immutableClass);
                    safe = AccessController.doPrivileged(action);
                } catch (Exception e) { // e.g. SecurityException
                    /* We don't know, so we assume it isn't.  */
                    safe = false;
                }
                arrayGettersSafeMap.put(subclass, safe);
            }
            return safe;
        }
    }

    /*
     * The PrivilegedAction stuff is probably overkill.  We can be
     * pretty sure the caller does have the required privileges -- a
     * JMX user that can't do reflection can't even use Standard
     * MBeans!  But there's probably a performance gain by not having
     * to check the whole call stack.
     * <p>
     *  PrivilegedAction的东西可能是杀手。我们可以很确定调用方具有所需的特权 - 一个不能做反射的JMX用户甚至不能使用标准MBean！但是可能通过不必检查整个调用堆栈而获得性能提升。
     * 
     */
    private static class ArrayGettersSafeAction
            implements PrivilegedAction<Boolean> {

        private final Class<?> subclass;
        private final Class<?> immutableClass;

        ArrayGettersSafeAction(Class<?> subclass, Class<?> immutableClass) {
            this.subclass = subclass;
            this.immutableClass = immutableClass;
        }

        public Boolean run() {
            Method[] methods = immutableClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                String methodName = method.getName();
                if (methodName.startsWith("get") &&
                        method.getParameterTypes().length == 0 &&
                        method.getReturnType().isArray()) {
                    try {
                        Method submethod =
                            subclass.getMethod(methodName);
                        if (!submethod.equals(method))
                            return false;
                    } catch (NoSuchMethodException e) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static boolean isEqual(String s1, String s2) {
        boolean ret;

        if (s1 == null) {
            ret = (s2 == null);
        } else {
            ret = s1.equals(s2);
        }

        return ret;
    }

    /**
     * Serializes an {@link MBeanInfo} to an {@link ObjectOutputStream}.
     * <p>
     *  将{@link MBeanInfo}序列化为{@link ObjectOutputStream}。
     * 
     * 
     * @serialData
     * For compatibility reasons, an object of this class is serialized as follows.
     * <p>
     * The method {@link ObjectOutputStream#defaultWriteObject defaultWriteObject()}
     * is called first to serialize the object except the field {@code descriptor}
     * which is declared as transient. The field {@code descriptor} is serialized
     * as follows:
     *     <ul>
     *     <li> If {@code descriptor} is an instance of the class
     *        {@link ImmutableDescriptor}, the method {@link ObjectOutputStream#write
     *        write(int val)} is called to write a byte with the value {@code 1},
     *        then the method {@link ObjectOutputStream#writeObject writeObject(Object obj)}
     *        is called twice to serialize the field names and the field values of the
     *        {@code descriptor}, respectively as a {@code String[]} and an
     *        {@code Object[]};</li>
     *     <li> Otherwise, the method {@link ObjectOutputStream#write write(int val)}
     *        is called to write a byte with the value {@code 0}, then the method
     *        {@link ObjectOutputStream#writeObject writeObject(Object obj)} is called
     *        to serialize the field {@code descriptor} directly.
     *     </ul>
     *
     * @since 1.6
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        if (descriptor.getClass() == ImmutableDescriptor.class) {
            out.write(1);

            final String[] names = descriptor.getFieldNames();

            out.writeObject(names);
            out.writeObject(descriptor.getFieldValues(names));
        } else {
            out.write(0);

            out.writeObject(descriptor);
        }
    }

    /**
     * Deserializes an {@link MBeanInfo} from an {@link ObjectInputStream}.
     * <p>
     *  从{@link ObjectInputStream}反序列化{@link MBeanInfo}。
     * 
     * @serialData
     * For compatibility reasons, an object of this class is deserialized as follows.
     * <p>
     * The method {@link ObjectInputStream#defaultReadObject defaultReadObject()}
     * is called first to deserialize the object except the field
     * {@code descriptor}, which is not serialized in the default way. Then the method
     * {@link ObjectInputStream#read read()} is called to read a byte, the field
     * {@code descriptor} is deserialized according to the value of the byte value:
     *    <ul>
     *    <li>1. The method {@link ObjectInputStream#readObject readObject()}
     *       is called twice to obtain the field names (a {@code String[]}) and
     *       the field values (a {@code Object[]}) of the {@code descriptor}.
     *       The two obtained values then are used to construct
     *       an {@link ImmutableDescriptor} instance for the field
     *       {@code descriptor};</li>
     *    <li>0. The value for the field {@code descriptor} is obtained directly
     *       by calling the method {@link ObjectInputStream#readObject readObject()}.
     *       If the obtained value is null, the field {@code descriptor} is set to
     *       {@link ImmutableDescriptor#EMPTY_DESCRIPTOR EMPTY_DESCRIPTOR};</li>
     *    <li>-1. This means that there is no byte to read and that the object is from
     *       an earlier version of the JMX API. The field {@code descriptor} is set to
     *       {@link ImmutableDescriptor#EMPTY_DESCRIPTOR EMPTY_DESCRIPTOR}.</li>
     *    <li>Any other value. A {@link StreamCorruptedException} is thrown.</li>
     *    </ul>
     *
     * @since 1.6
     */

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {

        in.defaultReadObject();

        switch (in.read()) {
        case 1:
            final String[] names = (String[])in.readObject();

            final Object[] values = (Object[]) in.readObject();
            descriptor = (names.length == 0) ?
                ImmutableDescriptor.EMPTY_DESCRIPTOR :
                new ImmutableDescriptor(names, values);

            break;
        case 0:
            descriptor = (Descriptor)in.readObject();

            if (descriptor == null) {
                descriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR;
            }

            break;
        case -1: // from an earlier version of the JMX API
            descriptor = ImmutableDescriptor.EMPTY_DESCRIPTOR;

            break;
        default:
            throw new StreamCorruptedException("Got unexpected byte.");
        }
    }
}
