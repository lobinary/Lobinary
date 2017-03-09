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

import com.sun.jmx.mbeanserver.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * Describes a management operation exposed by an MBean.  Instances of
 * this class are immutable.  Subclasses may be mutable but this is
 * not recommended.
 *
 * <p>
 *  描述MBean公开的管理操作。这个类的实例是不可变的。子类可以是可变的,但这不是推荐。
 * 
 * 
 * @since 1.5
 */
public class MBeanOperationInfo extends MBeanFeatureInfo implements Cloneable {

    /* Serial version */
    static final long serialVersionUID = -6178860474881375330L;

    static final MBeanOperationInfo[] NO_OPERATIONS =
        new MBeanOperationInfo[0];

    /**
     * Indicates that the operation is read-like:
     * it returns information but does not change any state.
     * <p>
     *  表示操作类似于read：它返回信息,但不改变任何状态。
     * 
     */
    public static final int INFO = 0;

    /**
     * Indicates that the operation is write-like: it has an effect but does
     * not return any information from the MBean.
     * <p>
     *  表示操作类似于写操作：它具有效果,但不返回MBean中的任何信息。
     * 
     */
    public static final int ACTION = 1;

    /**
     * Indicates that the operation is both read-like and write-like:
     * it has an effect, and it also returns information from the MBean.
     * <p>
     *  表示操作是类似读和类似操作：它有一个效果,它也从MBean返回信息。
     * 
     */
    public static final int ACTION_INFO = 2;

    /**
     * Indicates that the impact of the operation is unknown or cannot be
     * expressed using one of the other values.
     * <p>
     *  表示操作的影响未知或不能使用其他值之一表示。
     * 
     */
    public static final int UNKNOWN = 3;

    /**
    /* <p>
    /* 
     * @serial The method's return value.
     */
    private final String type;

    /**
    /* <p>
    /* 
     * @serial The signature of the method, that is, the class names
     * of the arguments.
     */
    private final MBeanParameterInfo[] signature;

    /**
    /* <p>
    /* 
     * @serial The impact of the method, one of
     *         <CODE>INFO</CODE>,
     *         <CODE>ACTION</CODE>,
     *         <CODE>ACTION_INFO</CODE>,
     *         <CODE>UNKNOWN</CODE>
     */
    private final int impact;

    /** @see MBeanInfo#arrayGettersSafe */
    private final transient boolean arrayGettersSafe;


    /**
     * Constructs an <CODE>MBeanOperationInfo</CODE> object.  The
     * {@link Descriptor} of the constructed object will include
     * fields contributed by any annotations on the {@code Method}
     * object that contain the {@link DescriptorKey} meta-annotation.
     *
     * <p>
     *  构造一个<CODE> MBeanOperationInfo </CODE>对象。
     * 构造对象的{@link描述符}将包含由包含{@link DescriptorKey}元注释的{@code Method}对象上的任何注释贡献的字段。
     * 
     * 
     * @param method The <CODE>java.lang.reflect.Method</CODE> object
     * describing the MBean operation.
     * @param description A human readable description of the operation.
     */
    public MBeanOperationInfo(String description, Method method) {
        this(method.getName(),
             description,
             methodSignature(method),
             method.getReturnType().getName(),
             UNKNOWN,
             Introspector.descriptorForElement(method));
    }

    /**
     * Constructs an <CODE>MBeanOperationInfo</CODE> object.
     *
     * <p>
     *  构造一个<CODE> MBeanOperationInfo </CODE>对象。
     * 
     * 
     * @param name The name of the method.
     * @param description A human readable description of the operation.
     * @param signature <CODE>MBeanParameterInfo</CODE> objects
     * describing the parameters(arguments) of the method.  This may be
     * null with the same effect as a zero-length array.
     * @param type The type of the method's return value.
     * @param impact The impact of the method, one of
     * {@link #INFO}, {@link #ACTION}, {@link #ACTION_INFO},
     * {@link #UNKNOWN}.
     */
    public MBeanOperationInfo(String name,
                              String description,
                              MBeanParameterInfo[] signature,
                              String type,
                              int impact) {
        this(name, description, signature, type, impact, (Descriptor) null);
    }

    /**
     * Constructs an <CODE>MBeanOperationInfo</CODE> object.
     *
     * <p>
     *  构造一个<CODE> MBeanOperationInfo </CODE>对象。
     * 
     * 
     * @param name The name of the method.
     * @param description A human readable description of the operation.
     * @param signature <CODE>MBeanParameterInfo</CODE> objects
     * describing the parameters(arguments) of the method.  This may be
     * null with the same effect as a zero-length array.
     * @param type The type of the method's return value.
     * @param impact The impact of the method, one of
     * {@link #INFO}, {@link #ACTION}, {@link #ACTION_INFO},
     * {@link #UNKNOWN}.
     * @param descriptor The descriptor for the operation.  This may be null
     * which is equivalent to an empty descriptor.
     *
     * @since 1.6
     */
    public MBeanOperationInfo(String name,
                              String description,
                              MBeanParameterInfo[] signature,
                              String type,
                              int impact,
                              Descriptor descriptor) {

        super(name, description, descriptor);

        if (signature == null || signature.length == 0)
            signature = MBeanParameterInfo.NO_PARAMS;
        else
            signature = signature.clone();
        this.signature = signature;
        this.type = type;
        this.impact = impact;
        this.arrayGettersSafe =
            MBeanInfo.arrayGettersSafe(this.getClass(),
                                       MBeanOperationInfo.class);
    }

    /**
     * <p>Returns a shallow clone of this instance.
     * The clone is obtained by simply calling <tt>super.clone()</tt>,
     * thus calling the default native shallow cloning mechanism
     * implemented by <tt>Object.clone()</tt>.
     * No deeper cloning of any internal field is made.</p>
     *
     * <p>Since this class is immutable, cloning is chiefly of interest
     * to subclasses.</p>
     * <p>
     *  <p>返回此实例的浅克隆。通过简单调用<tt> super.clone()</tt>获得克隆,从而调用由<tt> Object.clone()</tt>实现的默认本机浅克隆机制。
     * 不会对任何内部字段进行更深层次的克隆。</p>。
     * 
     *  <p>由于这个类是不可变的,克隆主要是子类的兴趣。</p>
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
     * Returns the type of the method's return value.
     *
     * <p>
     *  返回方法的返回值的类型。
     * 
     * 
     * @return the return type.
     */
    public String getReturnType() {
        return type;
    }

    /**
     * <p>Returns the list of parameters for this operation.  Each
     * parameter is described by an <CODE>MBeanParameterInfo</CODE>
     * object.</p>
     *
     * <p>The returned array is a shallow copy of the internal array,
     * which means that it is a copy of the internal array of
     * references to the <CODE>MBeanParameterInfo</CODE> objects but
     * that each referenced <CODE>MBeanParameterInfo</CODE> object is
     * not copied.</p>
     *
     * <p>
     * <p>返回此操作的参数列表。每个参数由<CODE> MBeanParameterInfo </CODE>对象描述。</p>
     * 
     *  <p>返回的数组是内部数组的浅拷贝,这意味着它是对<CODE> MBeanParameterInfo </CODE>对象的引用的内部数组的副本,但每个引用<CODE> MBeanParameterIn
     * fo </CODE >对象未被复制。
     * </p>。
     * 
     * 
     * @return  An array of <CODE>MBeanParameterInfo</CODE> objects.
     */
    public MBeanParameterInfo[] getSignature() {
        // If MBeanOperationInfo was created in our implementation,
        // signature cannot be null - because our constructors replace
        // null with MBeanParameterInfo.NO_PARAMS;
        //
        // However, signature could be null if an  MBeanOperationInfo is
        // deserialized from a byte array produced by another implementation.
        // This is not very likely but possible, since the serial form says
        // nothing against it. (see 6373150)
        //
        if (signature == null)
            // if signature is null simply return an empty array .
            //
            return MBeanParameterInfo.NO_PARAMS;
        else if (signature.length == 0)
            return signature;
        else
            return signature.clone();
    }

    private MBeanParameterInfo[] fastGetSignature() {
        if (arrayGettersSafe) {
            // if signature is null simply return an empty array .
            // see getSignature() above.
            //
            if (signature == null)
                return MBeanParameterInfo.NO_PARAMS;
            else return signature;
        } else return getSignature();
    }

    /**
     * Returns the impact of the method, one of
     * <CODE>INFO</CODE>, <CODE>ACTION</CODE>, <CODE>ACTION_INFO</CODE>, <CODE>UNKNOWN</CODE>.
     *
     * <p>
     *  返回方法对<CODE> INFO </CODE>,<CODE> ACTION </CODE>,<CODE> ACTION_INFO </CODE>,<CODE> UNKNOWN </CODE>之一的影
     * 响。
     * 
     * 
     * @return the impact code.
     */
    public int getImpact() {
        return impact;
    }

    @Override
    public String toString() {
        String impactString;
        switch (getImpact()) {
        case ACTION: impactString = "action"; break;
        case ACTION_INFO: impactString = "action/info"; break;
        case INFO: impactString = "info"; break;
        case UNKNOWN: impactString = "unknown"; break;
        default: impactString = "(" + getImpact() + ")";
        }
        return getClass().getName() + "[" +
            "description=" + getDescription() + ", " +
            "name=" + getName() + ", " +
            "returnType=" + getReturnType() + ", " +
            "signature=" + Arrays.asList(fastGetSignature()) + ", " +
            "impact=" + impactString + ", " +
            "descriptor=" + getDescriptor() +
            "]";
    }

    /**
     * Compare this MBeanOperationInfo to another.
     *
     * <p>
     *  将此MBeanOperationInfo与另一个比较。
     * 
     * 
     * @param o the object to compare to.
     *
     * @return true if and only if <code>o</code> is an MBeanOperationInfo such
     * that its {@link #getName()}, {@link #getReturnType()}, {@link
     * #getDescription()}, {@link #getImpact()}, {@link #getDescriptor()}
     * and {@link #getSignature()} values are equal (not necessarily identical)
     * to those of this MBeanConstructorInfo.  Two signature arrays
     * are equal if their elements are pairwise equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MBeanOperationInfo))
            return false;
        MBeanOperationInfo p = (MBeanOperationInfo) o;
        return (Objects.equals(p.getName(), getName()) &&
                Objects.equals(p.getReturnType(), getReturnType()) &&
                Objects.equals(p.getDescription(), getDescription()) &&
                p.getImpact() == getImpact() &&
                Arrays.equals(p.fastGetSignature(), fastGetSignature()) &&
                Objects.equals(p.getDescriptor(), getDescriptor()));
    }

    /* We do not include everything in the hashcode.  We assume that
       if two operations are different they'll probably have different
       names or types.  The penalty we pay when this assumption is
       wrong should be less than the penalty we would pay if it were
       right and we needlessly hashed in the description and the
    /* <p>
    /*  如果两个操作不同,它们可能会有不同的名称或类型。当这个假设错误时,我们所支付的罚款应该少于我们在正确的情况下支付的罚金,而且我们在描述和
    /* 
       parameter array.  */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getReturnType());
    }

    private static MBeanParameterInfo[] methodSignature(Method method) {
        final Class<?>[] classes = method.getParameterTypes();
        final Annotation[][] annots = method.getParameterAnnotations();
        return parameters(classes, annots);
    }

    static MBeanParameterInfo[] parameters(Class<?>[] classes,
                                           Annotation[][] annots) {
        final MBeanParameterInfo[] params =
            new MBeanParameterInfo[classes.length];
        assert(classes.length == annots.length);

        for (int i = 0; i < classes.length; i++) {
            Descriptor d = Introspector.descriptorForAnnotations(annots[i]);
            final String pn = "p" + (i + 1);
            params[i] =
                new MBeanParameterInfo(pn, classes[i].getName(), "", d);
        }

        return params;
    }
}
