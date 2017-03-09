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

import java.lang.reflect.Method;
import java.security.AccessController;

import com.sun.jmx.mbeanserver.GetPropertyAction;
import com.sun.jmx.mbeanserver.Introspector;
import java.util.Objects;


/**
 * Describes an MBean attribute exposed for management.  Instances of
 * this class are immutable.  Subclasses may be mutable but this is
 * not recommended.
 *
 * <p>
 *  描述公开用于管理的MBean属性。这个类的实例是不可变的。子类可以是可变的,但这不是推荐。
 * 
 * 
 * @since 1.5
 */
@SuppressWarnings("serial")  // serialVersionUID not constant
public class MBeanAttributeInfo extends MBeanFeatureInfo implements Cloneable {

    /* Serial version */
    private static final long serialVersionUID;
    static {
        /* For complicated reasons, the serialVersionUID changed
           between JMX 1.0 and JMX 1.1, even though JMX 1.1 did not
           have compatibility code for this class.  So the
           serialization produced by this class with JMX 1.2 and
           jmx.serial.form=1.0 is not the same as that produced by
           this class with JMX 1.1 and jmx.serial.form=1.0.  However,
           the serialization without that property is the same, and
           that is the only form required by JMX 1.2.
        /* <p>
        /*  在JMX 1.0和JMX 1.1之间,即使JMX 1.1没有此类的兼容性代码。
        /* 因此,这个类与JMX 1.2和jmx.serial.form = 1.0产生的序列化与此类与JMX 1.1和jmx.serial.form = 1.0生成的序列化不同。
        /* 但是,没有该属性的序列化是相同的,这是JMX 1.2所需的唯一形式。
        /* 
        */
        long uid = 8644704819898565848L;
        try {
            GetPropertyAction act = new GetPropertyAction("jmx.serial.form");
            String form = AccessController.doPrivileged(act);
            if ("1.0".equals(form))
                uid = 7043855487133450673L;
        } catch (Exception e) {
            // OK: exception means no compat with 1.0, too bad
        }
        serialVersionUID = uid;
    }

    static final MBeanAttributeInfo[] NO_ATTRIBUTES =
        new MBeanAttributeInfo[0];

    /**
    /* <p>
    /* 
     * @serial The actual attribute type.
     */
    private final String attributeType;

    /**
    /* <p>
    /* 
     * @serial The attribute write right.
     */
    private final boolean isWrite;

    /**
    /* <p>
    /* 
     * @serial The attribute read right.
     */
    private final boolean isRead;

    /**
    /* <p>
    /* 
     * @serial Indicates if this method is a "is"
     */
    private final boolean is;


    /**
     * Constructs an <CODE>MBeanAttributeInfo</CODE> object.
     *
     * <p>
     *  构造一个<CODE> MBeanAttributeInfo </CODE>对象。
     * 
     * 
     * @param name The name of the attribute.
     * @param type The type or class name of the attribute.
     * @param description A human readable description of the attribute.
     * @param isReadable True if the attribute has a getter method, false otherwise.
     * @param isWritable True if the attribute has a setter method, false otherwise.
     * @param isIs True if this attribute has an "is" getter, false otherwise.
     *
     * @throws IllegalArgumentException if {@code isIs} is true but
     * {@code isReadable} is not, or if {@code isIs} is true and
     * {@code type} is not {@code boolean} or {@code java.lang.Boolean}.
     * (New code should always use {@code boolean} rather than
     * {@code java.lang.Boolean}.)
     */
    public MBeanAttributeInfo(String name,
                              String type,
                              String description,
                              boolean isReadable,
                              boolean isWritable,
                              boolean isIs) {
        this(name, type, description, isReadable, isWritable, isIs,
             (Descriptor) null);
    }

    /**
     * Constructs an <CODE>MBeanAttributeInfo</CODE> object.
     *
     * <p>
     *  构造一个<CODE> MBeanAttributeInfo </CODE>对象。
     * 
     * 
     * @param name The name of the attribute.
     * @param type The type or class name of the attribute.
     * @param description A human readable description of the attribute.
     * @param isReadable True if the attribute has a getter method, false otherwise.
     * @param isWritable True if the attribute has a setter method, false otherwise.
     * @param isIs True if this attribute has an "is" getter, false otherwise.
     * @param descriptor The descriptor for the attribute.  This may be null
     * which is equivalent to an empty descriptor.
     *
     * @throws IllegalArgumentException if {@code isIs} is true but
     * {@code isReadable} is not, or if {@code isIs} is true and
     * {@code type} is not {@code boolean} or {@code java.lang.Boolean}.
     * (New code should always use {@code boolean} rather than
     * {@code java.lang.Boolean}.)
     *
     * @since 1.6
     */
    public MBeanAttributeInfo(String name,
                              String type,
                              String description,
                              boolean isReadable,
                              boolean isWritable,
                              boolean isIs,
                              Descriptor descriptor) {
        super(name, description, descriptor);

        this.attributeType = type;
        this.isRead = isReadable;
        this.isWrite = isWritable;
        if (isIs && !isReadable) {
            throw new IllegalArgumentException("Cannot have an \"is\" getter " +
                                               "for a non-readable attribute");
        }
        if (isIs && !type.equals("java.lang.Boolean") &&
                !type.equals("boolean")) {
            throw new IllegalArgumentException("Cannot have an \"is\" getter " +
                                               "for a non-boolean attribute");
        }
        this.is = isIs;
    }

    /**
     * <p>This constructor takes the name of a simple attribute, and Method
     * objects for reading and writing the attribute.  The {@link Descriptor}
     * of the constructed object will include fields contributed by any
     * annotations on the {@code Method} objects that contain the
     * {@link DescriptorKey} meta-annotation.
     *
     * <p>
     *  <p>此构造函数使用简单属性的名称,以及用于读取和写入属性的方法对象。
     * 构造对象的{@link描述符}将包含由包含{@link DescriptorKey}元注释的{@code Method}对象上的任何注释贡献的字段。
     * 
     * 
     * @param name The programmatic name of the attribute.
     * @param description A human readable description of the attribute.
     * @param getter The method used for reading the attribute value.
     *          May be null if the property is write-only.
     * @param setter The method used for writing the attribute value.
     *          May be null if the attribute is read-only.
     * @exception IntrospectionException There is a consistency
     * problem in the definition of this attribute.
     */
    public MBeanAttributeInfo(String name,
                              String description,
                              Method getter,
                              Method setter) throws IntrospectionException {
        this(name,
             attributeType(getter, setter),
             description,
             (getter != null),
             (setter != null),
             isIs(getter),
             ImmutableDescriptor.union(Introspector.descriptorForElement(getter),
                                   Introspector.descriptorForElement(setter)));
    }

    /**
     * <p>Returns a shallow clone of this instance.
     * The clone is obtained by simply calling <tt>super.clone()</tt>,
     * thus calling the default native shallow cloning mechanism
     * implemented by <tt>Object.clone()</tt>.
     * No deeper cloning of any internal field is made.</p>
     *
     * <p>Since this class is immutable, cloning is chiefly of
     * interest to subclasses.</p>
     * <p>
     *  <p>返回此实例的浅克隆。通过简单调用<tt> super.clone()</tt>获得克隆,从而调用由<tt> Object.clone()</tt>实现的默认本机浅克隆机制。
     * 不会对任何内部字段进行更深层次的克隆。</p>。
     * 
     *  <p>由于这个类是不可变的,克隆主要是子类的兴趣。</p>
     * 
     */
     public Object clone () {
         try {
             return super.clone() ;
         } catch (CloneNotSupportedException e) {
             // should not happen as this class is cloneable
             return null;
         }
     }

    /**
     * Returns the class name of the attribute.
     *
     * <p>
     *  返回属性的类名。
     * 
     * 
     * @return the class name.
     */
    public String getType() {
        return attributeType;
    }

    /**
     * Whether the value of the attribute can be read.
     *
     * <p>
     * 是否可以读取属性的值。
     * 
     * 
     * @return True if the attribute can be read, false otherwise.
     */
    public boolean isReadable() {
        return isRead;
    }

    /**
     * Whether new values can be written to the attribute.
     *
     * <p>
     *  是否可以将新值写入属性。
     * 
     * 
     * @return True if the attribute can be written to, false otherwise.
     */
    public boolean isWritable() {
        return isWrite;
    }

    /**
     * Indicates if this attribute has an "is" getter.
     *
     * <p>
     *  指示此属性是否具有"is"getter。
     * 
     * 
     * @return true if this attribute has an "is" getter.
     */
    public boolean isIs() {
        return is;
    }

    public String toString() {
        String access;
        if (isReadable()) {
            if (isWritable())
                access = "read/write";
            else
                access = "read-only";
        } else if (isWritable())
            access = "write-only";
        else
            access = "no-access";

        return
            getClass().getName() + "[" +
            "description=" + getDescription() + ", " +
            "name=" + getName() + ", " +
            "type=" + getType() + ", " +
            access + ", " +
            (isIs() ? "isIs, " : "") +
            "descriptor=" + getDescriptor() +
            "]";
    }

    /**
     * Compare this MBeanAttributeInfo to another.
     *
     * <p>
     *  将此MBeanAttributeInfo与另一个比较。
     * 
     * 
     * @param o the object to compare to.
     *
     * @return true if and only if <code>o</code> is an MBeanAttributeInfo such
     * that its {@link #getName()}, {@link #getType()}, {@link
     * #getDescription()}, {@link #isReadable()}, {@link
     * #isWritable()}, and {@link #isIs()} values are equal (not
     * necessarily identical) to those of this MBeanAttributeInfo.
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MBeanAttributeInfo))
            return false;
        MBeanAttributeInfo p = (MBeanAttributeInfo) o;
        return (Objects.equals(p.getName(), getName()) &&
                Objects.equals(p.getType(), getType()) &&
                Objects.equals(p.getDescription(), getDescription()) &&
                Objects.equals(p.getDescriptor(), getDescriptor()) &&
                p.isReadable() == isReadable() &&
                p.isWritable() == isWritable() &&
                p.isIs() == isIs());
    }

    /* We do not include everything in the hashcode.  We assume that
       if two operations are different they'll probably have different
       names or types.  The penalty we pay when this assumption is
       wrong should be less than the penalty we would pay if it were
       right and we needlessly hashed in the description and parameter
    /* <p>
    /*  如果两个操作不同,它们可能会有不同的名称或类型。当这个假设错误时,我们所支付的惩罚应该小于我们在正确的情况下支付的惩罚,并且我们不必要地在描述和参数中散列
    /* 
    /* 
       array.  */
    public int hashCode() {
        return Objects.hash(getName(), getType());
    }

    private static boolean isIs(Method getter) {
        return (getter != null &&
                getter.getName().startsWith("is") &&
                (getter.getReturnType().equals(Boolean.TYPE) ||
                 getter.getReturnType().equals(Boolean.class)));
    }

    /**
     * Finds the type of the attribute.
     * <p>
     *  查找属性的类型。
     */
    private static String attributeType(Method getter, Method setter)
            throws IntrospectionException {
        Class<?> type = null;

        if (getter != null) {
            if (getter.getParameterTypes().length != 0) {
                throw new IntrospectionException("bad getter arg count");
            }
            type = getter.getReturnType();
            if (type == Void.TYPE) {
                throw new IntrospectionException("getter " + getter.getName() +
                                                 " returns void");
            }
        }

        if (setter != null) {
            Class<?> params[] = setter.getParameterTypes();
            if (params.length != 1) {
                throw new IntrospectionException("bad setter arg count");
            }
            if (type == null)
                type = params[0];
            else if (type != params[0]) {
                throw new IntrospectionException("type mismatch between " +
                                                 "getter and setter");
            }
        }

        if (type == null) {
            throw new IntrospectionException("getter and setter cannot " +
                                             "both be null");
        }

        return type.getName();
    }

}
