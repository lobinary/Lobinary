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
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

// jmx import
//
import javax.management.ObjectName;


/**
 * The <code>SimpleType</code> class is the <i>open type</i> class whose instances describe
 * all <i>open data</i> values which are neither arrays,
 * nor {@link CompositeData CompositeData} values,
 * nor {@link TabularData TabularData} values.
 * It predefines all its possible instances as static fields, and has no public constructor.
 * <p>
 * Given a <code>SimpleType</code> instance describing values whose Java class name is <i>className</i>,
 * the internal fields corresponding to the name and description of this <code>SimpleType</code> instance
 * are also set to <i>className</i>.
 * In other words, its methods <code>getClassName</code>, <code>getTypeName</code> and <code>getDescription</code>
 * all return the same string value <i>className</i>.
 *
 * <p>
 *  <code> SimpleType </code>类是<i>开放类型</i>类,其实例描述既不是数组的所有<i>开放数据</i>值,也不是{@link CompositeData CompositeData}
 * 也不是{@link TabularData TabularData}值。
 * 它将所有可能的实例预定义为静态字段,并且没有公共构造函数。
 * <p>
 *  给定描述其类别名为<i> className </i>的值的<code> SimpleType </code>实例,还设置对应于该<code> SimpleType </code>实例的名称和描述的内
 * 部字段到<i> className </i>。
 * 换句话说,其方法<code> getClassName </code>,<code> getTypeName </code>和<code> getDescription </code>都返回相同的字符串
 * 值<i> className </i>。
 * 
 * 
 * @since 1.5
 */
public final class SimpleType<T> extends OpenType<T> {

    /* Serial version */
    static final long serialVersionUID = 2215577471957694503L;

    // SimpleType instances.
    // IF YOU ADD A SimpleType, YOU MUST UPDATE OpenType and typeArray

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Void</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Void </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Void> VOID =
        new SimpleType<Void>(Void.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Boolean</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Boolean </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Boolean> BOOLEAN =
        new SimpleType<Boolean>(Boolean.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Character</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Character </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Character> CHARACTER =
        new SimpleType<Character>(Character.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Byte</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Byte </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Byte> BYTE =
        new SimpleType<Byte>(Byte.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Short</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Short </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Short> SHORT =
        new SimpleType<Short>(Short.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Integer</code>.
     * <p>
     * 描述其Java类名为<code> java.lang.Integer </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Integer> INTEGER =
        new SimpleType<Integer>(Integer.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Long</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Long </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Long> LONG =
        new SimpleType<Long>(Long.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Float</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Float </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Float> FLOAT =
        new SimpleType<Float>(Float.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.Double</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.Double </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Double> DOUBLE =
        new SimpleType<Double>(Double.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.lang.String</code>.
     * <p>
     *  描述其Java类名为<code> java.lang.String </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<String> STRING =
        new SimpleType<String>(String.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.math.BigDecimal</code>.
     * <p>
     *  描述其Java类名为<code> java.math.BigDecimal </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<BigDecimal> BIGDECIMAL =
        new SimpleType<BigDecimal>(BigDecimal.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.math.BigInteger</code>.
     * <p>
     *  描述其Java类名为<code> java.math.BigInteger </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<BigInteger> BIGINTEGER =
        new SimpleType<BigInteger>(BigInteger.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>java.util.Date</code>.
     * <p>
     *  描述其Java类名为<code> java.util.Date </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<Date> DATE =
        new SimpleType<Date>(Date.class);

    /**
     * The <code>SimpleType</code> instance describing values whose
     * Java class name is <code>javax.management.ObjectName</code>.
     * <p>
     *  描述其Java类名为<code> javax.management.ObjectName </code>的值的<code> SimpleType </code>实例。
     * 
     */
    public static final SimpleType<ObjectName> OBJECTNAME =
        new SimpleType<ObjectName>(ObjectName.class);

    private static final SimpleType<?>[] typeArray = {
        VOID, BOOLEAN, CHARACTER, BYTE, SHORT, INTEGER, LONG, FLOAT,
        DOUBLE, STRING, BIGDECIMAL, BIGINTEGER, DATE, OBJECTNAME,
    };


    private transient Integer myHashCode = null;        // As this instance is immutable, these two values
    private transient String  myToString = null;        // need only be calculated once.


    /* *** Constructor *** */

    private SimpleType(Class<T> valueClass) {
        super(valueClass.getName(), valueClass.getName(), valueClass.getName(),
              false);
    }


    /* *** SimpleType specific information methods *** */

    /**
     * Tests whether <var>obj</var> is a value for this
     * <code>SimpleType</code> instance.  <p> This method returns
     * <code>true</code> if and only if <var>obj</var> is not null and
     * <var>obj</var>'s class name is the same as the className field
     * defined for this <code>SimpleType</code> instance (ie the class
     * name returned by the {@link OpenType#getClassName()
     * getClassName} method).
     *
     * <p>
     *  测试<var> obj </var>是否是此<simple> SimpleType </code>实例的值。
     *  <p>当且仅当<var> obj </var>不为null且<var> obj </var>的类名与定义的className字段相同时,此方法返回<code> true </code>对于这个<code>
     *  SimpleType </code>实例(即由{@link OpenType#getClassName()getClassName}方法返回的类名)。
     *  测试<var> obj </var>是否是此<simple> SimpleType </code>实例的值。
     * 
     * 
     * @param obj the object to be tested.
     *
     * @return <code>true</code> if <var>obj</var> is a value for this
     * <code>SimpleType</code> instance.
     */
    public boolean isValue(Object obj) {

        // if obj is null, return false
        //
        if (obj == null) {
            return false;
        }

        // Test if obj's class name is the same as for this instance
        //
        return this.getClassName().equals(obj.getClass().getName());
    }


    /* *** Methods overriden from class Object *** */

    /**
     * Compares the specified <code>obj</code> parameter with this <code>SimpleType</code> instance for equality.
     * <p>
     * Two <code>SimpleType</code> instances are equal if and only if their
     * {@link OpenType#getClassName() getClassName} methods return the same value.
     *
     * <p>
     * 将指定的<code> obj </code>参数与此<code> SimpleType </code>实例进行比较以确保相等。
     * <p>
     *  当且仅当它们的{@link OpenType#getClassName()getClassName}方法返回相同的值时,两个<code> SimpleType </code>实例才相等。
     * 
     * 
     * @param  obj  the object to be compared for equality with this <code>SimpleType</code> instance;
     *              if <var>obj</var> is <code>null</code> or is not an instance of the class <code>SimpleType</code>,
     *              <code>equals</code> returns <code>false</code>.
     *
     * @return  <code>true</code> if the specified object is equal to this <code>SimpleType</code> instance.
     */
    public boolean equals(Object obj) {

        /* If it weren't for readReplace(), we could replace this method
           with just:
           return (this == obj);
        /* <p>
        /*  with just：return(this == obj);
        /* 
        */

        if (!(obj instanceof SimpleType<?>))
            return false;

        SimpleType<?> other = (SimpleType<?>) obj;

        // Test if other's className field is the same as for this instance
        //
        return this.getClassName().equals(other.getClassName());
    }

    /**
     * Returns the hash code value for this <code>SimpleType</code> instance.
     * The hash code of a <code>SimpleType</code> instance is the the hash code of
     * the string value returned by the {@link OpenType#getClassName() getClassName} method.
     * <p>
     * As <code>SimpleType</code> instances are immutable, the hash code for this instance is calculated once,
     * on the first call to <code>hashCode</code>, and then the same value is returned for subsequent calls.
     *
     * <p>
     *  返回此<> SimpleType </code>实例的哈希码值。
     *  <code> SimpleType </code>实例的哈希码是由{@link OpenType#getClassName()getClassName}方法返回的字符串值的哈希码。
     * <p>
     *  由于<code> SimpleType </code>实例是不可变的,所以在第一次调用<code> hashCode </code>时会计算一次该实例的哈希码,然后为后续调用返回相同的值。
     * 
     * 
     * @return  the hash code value for this <code>SimpleType</code> instance
     */
    public int hashCode() {

        // Calculate the hash code value if it has not yet been done (ie 1st call to hashCode())
        //
        if (myHashCode == null) {
            myHashCode = Integer.valueOf(this.getClassName().hashCode());
        }

        // return always the same hash code for this instance (immutable)
        //
        return myHashCode.intValue();
    }

    /**
     * Returns a string representation of this <code>SimpleType</code> instance.
     * <p>
     * The string representation consists of
     * the name of this class (ie <code>javax.management.openmbean.SimpleType</code>) and the type name
     * for this instance (which is the java class name of the values this <code>SimpleType</code> instance represents).
     * <p>
     * As <code>SimpleType</code> instances are immutable, the string representation for this instance is calculated once,
     * on the first call to <code>toString</code>, and then the same value is returned for subsequent calls.
     *
     * <p>
     *  返回此<> SimpleType </code>实例的字符串表示形式。
     * <p>
     *  字符串表示由该类的名称(即<code> javax.management.openmbean.SimpleType </code>)和此实例的类型名称(这是此类的java类名称this <code> 
     * SimpleType < / code> instance表示)。
     * <p>
     * 
     * @return  a string representation of this <code>SimpleType</code> instance
     */
    public String toString() {

        // Calculate the string representation if it has not yet been done (ie 1st call to toString())
        //
        if (myToString == null) {
            myToString = this.getClass().getName()+ "(name="+ getTypeName() +")";
        }

        // return always the same string representation for this instance (immutable)
        //
        return myToString;
    }

    private static final Map<SimpleType<?>,SimpleType<?>> canonicalTypes =
        new HashMap<SimpleType<?>,SimpleType<?>>();
    static {
        for (int i = 0; i < typeArray.length; i++) {
            final SimpleType<?> type = typeArray[i];
            canonicalTypes.put(type, type);
        }
    }

    /**
     * Replace an object read from an {@link
     * java.io.ObjectInputStream} with the unique instance for that
     * value.
     *
     * <p>
     *  因为<code> SimpleType </code>实例是不可变的,所以在第一次调用<code> toString </code>时,计算一次该实例的字符串表示,然后为后续调用返回相同的值。
     * 
     * 
     * @return the replacement object.
     *
     * @exception ObjectStreamException if the read object cannot be
     * resolved.
     */
    public Object readResolve() throws ObjectStreamException {
        final SimpleType<?> canonical = canonicalTypes.get(this);
        if (canonical == null) {
            // Should not happen
            throw new InvalidObjectException("Invalid SimpleType: " + this);
        }
        return canonical;
    }
}
