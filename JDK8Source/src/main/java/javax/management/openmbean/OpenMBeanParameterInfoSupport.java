/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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


package javax.management.openmbean;


// java import
//
import java.util.Set;
import javax.management.Descriptor;
import javax.management.DescriptorRead;  // for Javadoc
import javax.management.ImmutableDescriptor;
import javax.management.MBeanParameterInfo;

// OpenMBeanAttributeInfoSupport and this class are very similar
// but can't easily be refactored because there's no multiple inheritance.
// The best we can do for refactoring is to put a bunch of static methods
// in OpenMBeanAttributeInfoSupport and import them here.
import static javax.management.openmbean.OpenMBeanAttributeInfoSupport.*;

/**
 * Describes a parameter used in one or more operations or
 * constructors of an open MBean.
 *
 *
 * <p>
 *  描述在打开的MBean的一个或多个操作或构造函数中使用的参数。
 * 
 * 
 * @since 1.5
 */
public class OpenMBeanParameterInfoSupport
    extends MBeanParameterInfo
    implements OpenMBeanParameterInfo {

    /* Serial version */
    static final long serialVersionUID = -7235016873758443122L;

    /**
    /* <p>
    /* 
     * @serial The open mbean parameter's <i>open type</i>
     */
    private OpenType<?>    openType;

    /**
    /* <p>
    /* 
     * @serial The open mbean parameter's default value
     */
    private Object      defaultValue    = null;

    /**
    /* <p>
    /* 
     * @serial The open mbean parameter's legal values. This {@link
     * Set} is unmodifiable
     */
    private Set<?> legalValues     = null;  // to be constructed unmodifiable

    /**
    /* <p>
    /* 
     * @serial The open mbean parameter's min value
     */
    private Comparable<?> minValue        = null;

    /**
    /* <p>
    /* 
     * @serial The open mbean parameter's max value
     */
    private Comparable<?> maxValue        = null;


    // As this instance is immutable, these two values need only
    // be calculated once.
    private transient Integer myHashCode = null;        // As this instance is immutable, these two values
    private transient String  myToString = null;        // need only be calculated once.


    /**
     * Constructs an {@code OpenMBeanParameterInfoSupport} instance,
     * which describes the parameter used in one or more operations or
     * constructors of a class of open MBeans, with the specified
     * {@code name}, {@code openType} and {@code description}.
     *
     * <p>
     *  构造{@code OpenMBeanParameterInfoSupport}实例,该实例描述在指定的{@code name},{@code openType}和{@code description}
     * 的开放MBean类的一个或多个操作或构造函数中使用的参数。
     * 
     * 
     * @param name  cannot be a null or empty string.
     *
     * @param description  cannot be a null or empty string.
     *
     * @param openType  cannot be null.
     *
     * @throws IllegalArgumentException if {@code name} or {@code
     * description} are null or empty string, or {@code openType} is
     * null.
     */
    public OpenMBeanParameterInfoSupport(String name,
                                         String description,
                                         OpenType<?> openType) {
        this(name, description, openType, (Descriptor) null);
    }

    /**
     * Constructs an {@code OpenMBeanParameterInfoSupport} instance,
     * which describes the parameter used in one or more operations or
     * constructors of a class of open MBeans, with the specified
     * {@code name}, {@code openType}, {@code description},
     * and {@code descriptor}.
     *
     * <p>The {@code descriptor} can contain entries that will define
     * the values returned by certain methods of this class, as
     * explained in the <a href="package-summary.html#constraints">
     * package description</a>.
     *
     * <p>
     *  构造{@code OpenMBeanParameterInfoSupport}实例,该实例描述在指定的{@code name},{@code openType},{@code description}
     * 和一组开放MBean的一个或多个操作或构造函数中使用的参数{@code descriptor}。
     * 
     *  <p> {@code descriptor}可以包含定义此类的某些方法返回的值的条目,如<a href="package-summary.html#constraints">包描述</a>中所述。
     * 
     * 
     * @param name  cannot be a null or empty string.
     *
     * @param description  cannot be a null or empty string.
     *
     * @param openType  cannot be null.
     *
     * @param descriptor The descriptor for the parameter.  This may be null
     * which is equivalent to an empty descriptor.
     *
     * @throws IllegalArgumentException if {@code name} or {@code
     * description} are null or empty string, or {@code openType} is
     * null, or the descriptor entries are invalid as described in the
     * <a href="package-summary.html#constraints">package
     * description</a>.
     *
     * @since 1.6
     */
    public OpenMBeanParameterInfoSupport(String name,
                                         String description,
                                         OpenType<?> openType,
                                         Descriptor descriptor) {


        // Construct parent's state
        //
        super(name,
              (openType==null) ? null : openType.getClassName(),
              description,
              ImmutableDescriptor.union(descriptor,(openType==null)?null:
                openType.getDescriptor()));

        // Initialize this instance's specific state
        //
        this.openType = openType;

        descriptor = getDescriptor();  // replace null by empty
        this.defaultValue = valueFrom(descriptor, "defaultValue", openType);
        this.legalValues = valuesFrom(descriptor, "legalValues", openType);
        this.minValue = comparableValueFrom(descriptor, "minValue", openType);
        this.maxValue = comparableValueFrom(descriptor, "maxValue", openType);

        try {
            check(this);
        } catch (OpenDataException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }


    /**
     * Constructs an {@code OpenMBeanParameterInfoSupport} instance,
     * which describes the parameter used in one or more operations or
     * constructors of a class of open MBeans, with the specified
     * {@code name}, {@code openType}, {@code description} and {@code
     * defaultValue}.
     *
     * <p>
     *  构造一个{@code OpenMBeanParameterInfoSupport}实例,该实例描述在开放MBean类的一个或多个操作或构造函数中使用的参数,指定的{@code name},{@code openType}
     * ,{@code description}和{ @code defaultValue}。
     * 
     * 
     * @param name  cannot be a null or empty string.
     *
     * @param description  cannot be a null or empty string.
     *
     * @param openType  cannot be null.
     *
     * @param defaultValue must be a valid value for the {@code
     * openType} specified for this parameter; default value not
     * supported for {@code ArrayType} and {@code TabularType}; can be
     * null, in which case it means that no default value is set.
     *
     * @param <T> allows the compiler to check that the {@code defaultValue},
     * if non-null, has the correct Java type for the given {@code openType}.
     *
     * @throws IllegalArgumentException if {@code name} or {@code
     * description} are null or empty string, or {@code openType} is
     * null.
     *
     * @throws OpenDataException if {@code defaultValue} is not a
     * valid value for the specified {@code openType}, or {@code
     * defaultValue} is non null and {@code openType} is an {@code
     * ArrayType} or a {@code TabularType}.
     */
    public <T> OpenMBeanParameterInfoSupport(String   name,
                                             String   description,
                                             OpenType<T> openType,
                                             T        defaultValue)
            throws OpenDataException {
        this(name, description, openType, defaultValue, (T[]) null);
    }

    /**
     * <p>Constructs an {@code OpenMBeanParameterInfoSupport} instance,
     * which describes the parameter used in one or more operations or
     * constructors of a class of open MBeans, with the specified
     * {@code name}, {@code openType}, {@code description}, {@code
     * defaultValue} and {@code legalValues}.</p>
     *
     * <p>The contents of {@code legalValues} are copied, so subsequent
     * modifications of the array referenced by {@code legalValues}
     * have no impact on this {@code OpenMBeanParameterInfoSupport}
     * instance.</p>
     *
     * <p>
     *  <p>构造{@code OpenMBeanParameterInfoSupport}实例,该实例描述在开放MBean类的一个或多个操作或构造函数中使用的参数,具有指定的{@code name},{@code openType}
     * ,{@code description },{@code defaultValue}和{@code legalValues}。
     * </p>。
     * 
     * <p>复制{@code legalValues}的内容,因此对{@code legalValues}引用的数组的后续修改对此{@code OpenMBeanParameterInfoSupport}实例
     * 没有影响。
     * </p>。
     * 
     * 
     * @param name  cannot be a null or empty string.
     *
     * @param description  cannot be a null or empty string.
     *
     * @param openType  cannot be null.
     *
     * @param defaultValue must be a valid value for the {@code
     * openType} specified for this parameter; default value not
     * supported for {@code ArrayType} and {@code TabularType}; can be
     * null, in which case it means that no default value is set.
     *
     * @param legalValues each contained value must be valid for the
     * {@code openType} specified for this parameter; legal values not
     * supported for {@code ArrayType} and {@code TabularType}; can be
     * null or empty.
     *
     * @param <T> allows the compiler to check that the {@code
     * defaultValue} and {@code legalValues}, if non-null, have the
     * correct Java type for the given {@code openType}.
     *
     * @throws IllegalArgumentException if {@code name} or {@code
     * description} are null or empty string, or {@code openType} is
     * null.
     *
     * @throws OpenDataException if {@code defaultValue} is not a
     * valid value for the specified {@code openType}, or one value in
     * {@code legalValues} is not valid for the specified {@code
     * openType}, or {@code defaultValue} is non null and {@code
     * openType} is an {@code ArrayType} or a {@code TabularType}, or
     * {@code legalValues} is non null and non empty and {@code
     * openType} is an {@code ArrayType} or a {@code TabularType}, or
     * {@code legalValues} is non null and non empty and {@code
     * defaultValue} is not contained in {@code legalValues}.
     */
    public <T> OpenMBeanParameterInfoSupport(String   name,
                                             String   description,
                                             OpenType<T> openType,
                                             T        defaultValue,
                                             T[]      legalValues)
            throws OpenDataException {
        this(name, description, openType,
             defaultValue, legalValues, null, null);
    }


    /**
     * Constructs an {@code OpenMBeanParameterInfoSupport} instance,
     * which describes the parameter used in one or more operations or
     * constructors of a class of open MBeans, with the specified
     * {@code name}, {@code openType}, {@code description}, {@code
     * defaultValue}, {@code minValue} and {@code maxValue}.
     *
     * It is possible to specify minimal and maximal values only for
     * an open type whose values are {@code Comparable}.
     *
     * <p>
     *  构造一个{@code OpenMBeanParameterInfoSupport}实例,该实例描述在一个或多个操作或开放MBeans类的构造函数中使用的参数,指定的{@code name},{@code openType}
     * ,{@code description} @code defaultValue},{@code minValue}和{@code maxValue}。
     * 
     *  可以为值为{@code Comparable}的开放类型指定最小值和最大值。
     * 
     * 
     * @param name  cannot be a null or empty string.
     *
     * @param description  cannot be a null or empty string.
     *
     * @param openType  cannot be null.
     *
     * @param defaultValue must be a valid value for the {@code
     * openType} specified for this parameter; default value not
     * supported for {@code ArrayType} and {@code TabularType}; can be
     * null, in which case it means that no default value is set.
     *
     * @param minValue must be valid for the {@code openType}
     * specified for this parameter; can be null, in which case it
     * means that no minimal value is set.
     *
     * @param maxValue must be valid for the {@code openType}
     * specified for this parameter; can be null, in which case it
     * means that no maximal value is set.
     *
     * @param <T> allows the compiler to check that the {@code
     * defaultValue}, {@code minValue}, and {@code maxValue}, if
     * non-null, have the correct Java type for the given {@code
     * openType}.
     *
     * @throws IllegalArgumentException if {@code name} or {@code
     * description} are null or empty string, or {@code openType} is
     * null.
     *
     * @throws OpenDataException if {@code defaultValue}, {@code
     * minValue} or {@code maxValue} is not a valid value for the
     * specified {@code openType}, or {@code defaultValue} is non null
     * and {@code openType} is an {@code ArrayType} or a {@code
     * TabularType}, or both {@code minValue} and {@code maxValue} are
     * non-null and {@code minValue.compareTo(maxValue) > 0} is {@code
     * true}, or both {@code defaultValue} and {@code minValue} are
     * non-null and {@code minValue.compareTo(defaultValue) > 0} is
     * {@code true}, or both {@code defaultValue} and {@code maxValue}
     * are non-null and {@code defaultValue.compareTo(maxValue) > 0}
     * is {@code true}.
     */
    public <T> OpenMBeanParameterInfoSupport(String     name,
                                             String     description,
                                             OpenType<T>   openType,
                                             T          defaultValue,
                                             Comparable<T> minValue,
                                             Comparable<T> maxValue)
            throws OpenDataException {
        this(name, description, openType,
             defaultValue, null, minValue, maxValue);
    }

    private <T> OpenMBeanParameterInfoSupport(String name,
                                              String description,
                                              OpenType<T> openType,
                                              T defaultValue,
                                              T[] legalValues,
                                              Comparable<T> minValue,
                                              Comparable<T> maxValue)
            throws OpenDataException {
        super(name,
              (openType == null) ? null : openType.getClassName(),
              description,
              makeDescriptor(openType,
                             defaultValue, legalValues, minValue, maxValue));

        this.openType = openType;

        Descriptor d = getDescriptor();
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        // We already converted the array into an unmodifiable Set
        // in the descriptor.
        this.legalValues = (Set<?>) d.getFieldValue("legalValues");

        check(this);
    }

    /**
     * An object serialized in a version of the API before Descriptors were
     * added to this class will have an empty or null Descriptor.
     * For consistency with our
     * behavior in this version, we must replace the object with one
     * where the Descriptors reflect the same values of openType, defaultValue,
     * etc.
     * <p>
     *  在将描述符添加到此类之前,在API版本中序列化的对象将具有空或空描述符。
     * 为了与我们在这个版本中的行为一致,我们必须将对象替换为一个对象,其中描述符反映了openType,defaultValue等的相同值。
     * 
     * 
     **/
    private Object readResolve() {
        if (getDescriptor().getFieldNames().length == 0) {
            // This noise allows us to avoid "unchecked" warnings without
            // having to suppress them explicitly.
            OpenType<Object> xopenType = cast(openType);
            Set<Object> xlegalValues = cast(legalValues);
            Comparable<Object> xminValue = cast(minValue);
            Comparable<Object> xmaxValue = cast(maxValue);
            return new OpenMBeanParameterInfoSupport(
                    name, description, openType,
                    makeDescriptor(xopenType, defaultValue, xlegalValues,
                                   xminValue, xmaxValue));
        } else
            return this;
    }

    /**
     * Returns the open type for the values of the parameter described
     * by this {@code OpenMBeanParameterInfoSupport} instance.
     * <p>
     *  返回此{@code OpenMBeanParameterInfoSupport}实例描述的参数值的打开类型。
     * 
     */
    public OpenType<?> getOpenType() {
        return openType;
    }

    /**
     * Returns the default value for the parameter described by this
     * {@code OpenMBeanParameterInfoSupport} instance, if specified,
     * or {@code null} otherwise.
     * <p>
     *  返回此{@code OpenMBeanParameterInfoSupport}实例(如果指定)所描述的参数的默认值,否则返回{@code null}。
     * 
     */
    public Object getDefaultValue() {

        // Special case for ArrayType and TabularType
        // [JF] TODO: clone it so that it cannot be altered,
        // [JF] TODO: if we decide to support defaultValue as an array itself.
        // [JF] As of today (oct 2000) it is not supported so
        // defaultValue is null for arrays. Nothing to do.

        return defaultValue;
    }

    /**
     * Returns an unmodifiable Set of legal values for the parameter
     * described by this {@code OpenMBeanParameterInfoSupport}
     * instance, if specified, or {@code null} otherwise.
     * <p>
     *  返回此{@code OpenMBeanParameterInfoSupport}实例(如果指定)描述的参数的不可修改的合法值集合,否则返回{@code null}。
     * 
     */
    public Set<?> getLegalValues() {

        // Special case for ArrayType and TabularType
        // [JF] TODO: clone values so that they cannot be altered,
        // [JF] TODO: if we decide to support LegalValues as an array itself.
        // [JF] As of today (oct 2000) it is not supported so
        // legalValues is null for arrays. Nothing to do.

        // Returns our legalValues Set (set was constructed unmodifiable)
        return (legalValues);
    }

    /**
     * Returns the minimal value for the parameter described by this
     * {@code OpenMBeanParameterInfoSupport} instance, if specified,
     * or {@code null} otherwise.
     * <p>
     * 返回此{@code OpenMBeanParameterInfoSupport}实例(如果指定)描述的参数的最小值,否则返回{@code null}。
     * 
     */
    public Comparable<?> getMinValue() {

        // Note: only comparable values have a minValue, so that's not
        // the case of arrays and tabulars (always null).

        return minValue;
    }

    /**
     * Returns the maximal value for the parameter described by this
     * {@code OpenMBeanParameterInfoSupport} instance, if specified,
     * or {@code null} otherwise.
     * <p>
     *  返回此{@code OpenMBeanParameterInfoSupport}实例(如果指定)所描述的参数的最大值,否则返回{@code null}。
     * 
     */
    public Comparable<?> getMaxValue() {

        // Note: only comparable values have a maxValue, so that's not
        // the case of arrays and tabulars (always null).

        return maxValue;
    }

    /**
     * Returns {@code true} if this {@code
     * OpenMBeanParameterInfoSupport} instance specifies a non-null
     * default value for the described parameter, {@code false}
     * otherwise.
     * <p>
     *  如果此{@code OpenMBeanParameterInfoSupport}实例为所描述的参数指定非空默认值,则返回{@code true},否则返回{@code false}。
     * 
     */
    public boolean hasDefaultValue() {

        return (defaultValue != null);
    }

    /**
     * Returns {@code true} if this {@code
     * OpenMBeanParameterInfoSupport} instance specifies a non-null
     * set of legal values for the described parameter, {@code false}
     * otherwise.
     * <p>
     *  如果此{@code OpenMBeanParameterInfoSupport}实例为所描述的参数指定一组非法的合法值,则返回{@code true},否则返回{@code false}。
     * 
     */
    public boolean hasLegalValues() {

        return (legalValues != null);
    }

    /**
     * Returns {@code true} if this {@code
     * OpenMBeanParameterInfoSupport} instance specifies a non-null
     * minimal value for the described parameter, {@code false}
     * otherwise.
     * <p>
     *  如果此{@code OpenMBeanParameterInfoSupport}实例为所描述的参数指定非空的最小值,则返回{@code true},否则返回{@code false}。
     * 
     */
    public boolean hasMinValue() {

        return (minValue != null);
    }

    /**
     * Returns {@code true} if this {@code
     * OpenMBeanParameterInfoSupport} instance specifies a non-null
     * maximal value for the described parameter, {@code false}
     * otherwise.
     * <p>
     *  如果此{@code OpenMBeanParameterInfoSupport}实例为所描述的参数指定非空的最大值,则返回{@code true},否则返回{@code false}。
     * 
     */
    public boolean hasMaxValue() {

        return (maxValue != null);
    }


    /**
     * Tests whether {@code obj} is a valid value for the parameter
     * described by this {@code OpenMBeanParameterInfo} instance.
     *
     * <p>
     *  测试{@code obj}是否为此{@code OpenMBeanParameterInfo}实例描述的参数的有效值。
     * 
     * 
     * @param obj the object to be tested.
     *
     * @return {@code true} if {@code obj} is a valid value
     * for the parameter described by this
     * {@code OpenMBeanParameterInfo} instance,
     * {@code false} otherwise.
     */
    public boolean isValue(Object obj) {
        return OpenMBeanAttributeInfoSupport.isValue(this, obj);
        // compiler bug? should be able to omit class name here
        // also below in toString and hashCode
    }


    /* ***  Commodity methods from java.lang.Object  *** */


    /**
     * <p>Compares the specified {@code obj} parameter with this {@code
     * OpenMBeanParameterInfoSupport} instance for equality.</p>
     *
     * <p>Returns {@code true} if and only if all of the following
     * statements are true:
     *
     * <ul>
     * <li>{@code obj} is non null,</li>
     * <li>{@code obj} also implements the {@code OpenMBeanParameterInfo}
     * interface,</li>
     * <li>their names are equal</li>
     * <li>their open types are equal</li>
     * <li>their default, min, max and legal values are equal.</li>
     * </ul>
     * This ensures that this {@code equals} method works properly for
     * {@code obj} parameters which are different implementations of
     * the {@code OpenMBeanParameterInfo} interface.
     *
     * <p>If {@code obj} also implements {@link DescriptorRead}, then its
     * {@link DescriptorRead#getDescriptor() getDescriptor()} method must
     * also return the same value as for this object.</p>
     *
     * <p>
     *  <p>比较指定的{@code obj}参数与此{@code OpenMBeanParameterInfoSupport}实例的相等性。</p>
     * 
     *  <p>当且仅当所有以下语句都为真时返回{@code true}：
     * 
     * <ul>
     * <li> {@ code obj}非零,</li> <li> {@ code obj}也实现{@code OpenMBeanParameterInfo}接口,</li> <li> li>其开放类型相等</li>
     *  <li>其默认值,最小值,最大值和法定值相等。
     * </li>。
     * </ul>
     *  这确保了{@code equals}方法对于{@code obj}参数正常工作,这些参数是{@code OpenMBeanParameterInfo}接口的不同实现。
     * 
     *  <p>如果{@code obj}也实现了{@link DescriptorRead},那么它的{@link DescriptorRead#getDescriptor()getDescriptor()}
     * 方法也必须返回与此对象相同的值。
     * </p>。
     * 
     * 
     * @param obj the object to be compared for equality with this
     * {@code OpenMBeanParameterInfoSupport} instance.
     *
     * @return {@code true} if the specified object is equal to this
     * {@code OpenMBeanParameterInfoSupport} instance.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof OpenMBeanParameterInfo))
            return false;

        OpenMBeanParameterInfo other = (OpenMBeanParameterInfo) obj;

        return equal(this, other);
    }

    /**
     * <p>Returns the hash code value for this {@code
     * OpenMBeanParameterInfoSupport} instance.</p>
     *
     * <p>The hash code of an {@code OpenMBeanParameterInfoSupport}
     * instance is the sum of the hash codes of all elements of
     * information used in {@code equals} comparisons (ie: its name,
     * its <i>open type</i>, its default, min, max and legal
     * values, and its Descriptor).
     *
     * <p>This ensures that {@code t1.equals(t2)} implies that {@code
     * t1.hashCode()==t2.hashCode()} for any two {@code
     * OpenMBeanParameterInfoSupport} instances {@code t1} and {@code
     * t2}, as required by the general contract of the method {@link
     * Object#hashCode() Object.hashCode()}.
     *
     * <p>However, note that another instance of a class implementing
     * the {@code OpenMBeanParameterInfo} interface may be equal to
     * this {@code OpenMBeanParameterInfoSupport} instance as defined
     * by {@link #equals(java.lang.Object)}, but may have a different
     * hash code if it is calculated differently.
     *
     * <p>As {@code OpenMBeanParameterInfoSupport} instances are
     * immutable, the hash code for this instance is calculated once,
     * on the first call to {@code hashCode}, and then the same value
     * is returned for subsequent calls.
     *
     * <p>
     *  <p>返回此{@code OpenMBeanParameterInfoSupport}实例的哈希码值。</p>
     * 
     *  <p> {@code OpenMBeanParameterInfoSupport}实例的哈希码是{@code equals}比较中使用的所有信息元素的哈希码的总和(即：其名称,其<i>开放类型</i>
     *  ,其默认值,最小值,最大值和合法值,及其描述符)。
     * 
     *  <p>这确保{@code t1.equals(t2)}意味着任何两个{@code OpenMBeanParameterInfoSupport}实例{@code t1}的{@code t1.hashCode()== t2.hashCode @code t2}
     * ,根据方法{@link Object#hashCode()Object.hashCode()}的一般合同的要求。
     * 
     * <p>但是,请注意,实现{@code OpenMBeanParameterInfo}接口的类的另一个实例可能等于{@link OpenStateParameterInfoSupport}实例(由{@link #equals(java.lang.Object)}
     * 定义),但可能如果计算不同,则具有不同的散列码。
     * 
     * 
     * @return the hash code value for this {@code
     * OpenMBeanParameterInfoSupport} instance
     */
    public int hashCode() {

        // Calculate the hash code value if it has not yet been done
        // (ie 1st call to hashCode())
        //
        if (myHashCode == null)
            myHashCode = OpenMBeanAttributeInfoSupport.hashCode(this);

        // return always the same hash code for this instance (immutable)
        //
        return myHashCode.intValue();
    }

    /**
     * Returns a string representation of this
     * {@code OpenMBeanParameterInfoSupport} instance.
     * <p>
     * The string representation consists of the name of this class (i.e.
     * {@code javax.management.openmbean.OpenMBeanParameterInfoSupport}),
     * the string representation of the name and open type of the described
     * parameter, the string representation of its default, min, max and legal
     * values and the string representation of its descriptor.
     * <p>
     * As {@code OpenMBeanParameterInfoSupport} instances are immutable,
     * the string representation for this instance is calculated once,
     * on the first call to {@code toString}, and then the same value
     * is returned for subsequent calls.
     *
     * <p>
     *  <p>由于{@code OpenMBeanParameterInfoSupport}实例不可变,在第一次调用{@code hashCode}时,此实例的哈希码计算一次,然后为后续调用返回相同的值。
     * 
     * 
     * @return a string representation of this
     * {@code OpenMBeanParameterInfoSupport} instance.
     */
    public String toString() {

        // Calculate the string value if it has not yet been done (ie
        // 1st call to toString())
        //
        if (myToString == null)
            myToString = OpenMBeanAttributeInfoSupport.toString(this);

        // return always the same string representation for this
        // instance (immutable)
        //
        return myToString;
    }

}
