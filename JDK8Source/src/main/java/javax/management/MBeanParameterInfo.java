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

import java.util.Objects;


/**
 * Describes an argument of an operation exposed by an MBean.
 * Instances of this class are immutable.  Subclasses may be mutable
 * but this is not recommended.
 *
 * <p>
 *  描述MBean公开的操作的参数。这个类的实例是不可变的。子类可以是可变的,但这不是推荐。
 * 
 * 
 * @since 1.5
 */
public class MBeanParameterInfo extends MBeanFeatureInfo implements Cloneable {

    /* Serial version */
    static final long serialVersionUID = 7432616882776782338L;

    /* All zero-length arrays are interchangeable. */
    static final MBeanParameterInfo[] NO_PARAMS = new MBeanParameterInfo[0];

    /**
    /* <p>
    /* 
     * @serial The type or class name of the data.
     */
    private final String type;


    /**
     * Constructs an <CODE>MBeanParameterInfo</CODE> object.
     *
     * <p>
     *  构造一个<CODE> MBeanParameterInfo </CODE>对象。
     * 
     * 
     * @param name The name of the data
     * @param type The type or class name of the data
     * @param description A human readable description of the data. Optional.
     */
    public MBeanParameterInfo(String name,
                              String type,
                              String description) {
        this(name, type, description, (Descriptor) null);
    }

    /**
     * Constructs an <CODE>MBeanParameterInfo</CODE> object.
     *
     * <p>
     *  构造一个<CODE> MBeanParameterInfo </CODE>对象。
     * 
     * 
     * @param name The name of the data
     * @param type The type or class name of the data
     * @param description A human readable description of the data. Optional.
     * @param descriptor The descriptor for the operation.  This may be null
     * which is equivalent to an empty descriptor.
     *
     * @since 1.6
     */
    public MBeanParameterInfo(String name,
                              String type,
                              String description,
                              Descriptor descriptor) {
        super(name, description, descriptor);

        this.type = type;
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
     * Returns the type or class name of the data.
     *
     * <p>
     *  返回数据的类型或类名。
     * 
     * 
     * @return the type string.
     */
    public String getType() {
        return type;
    }

    public String toString() {
        return
            getClass().getName() + "[" +
            "description=" + getDescription() + ", " +
            "name=" + getName() + ", " +
            "type=" + getType() + ", " +
            "descriptor=" + getDescriptor() +
            "]";
    }

    /**
     * Compare this MBeanParameterInfo to another.
     *
     * <p>
     *  将此MBeanParameterInfo与另一个比较。
     * 
     * @param o the object to compare to.
     *
     * @return true if and only if <code>o</code> is an MBeanParameterInfo such
     * that its {@link #getName()}, {@link #getType()},
     * {@link #getDescriptor()}, and {@link
     * #getDescription()} values are equal (not necessarily identical)
     * to those of this MBeanParameterInfo.
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MBeanParameterInfo))
            return false;
        MBeanParameterInfo p = (MBeanParameterInfo) o;
        return (Objects.equals(p.getName(), getName()) &&
                Objects.equals(p.getType(), getType()) &&
                Objects.equals(p.getDescription(), getDescription()) &&
                Objects.equals(p.getDescriptor(), getDescriptor()));
    }

    public int hashCode() {
        return Objects.hash(getName(), getType());
    }
}
