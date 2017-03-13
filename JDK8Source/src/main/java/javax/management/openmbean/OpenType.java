/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.jmx.mbeanserver.GetPropertyAction;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.management.Descriptor;
import javax.management.ImmutableDescriptor;

/**
 * The <code>OpenType</code> class is the parent abstract class of all classes which describe the actual <i>open type</i>
 * of open data values.
 * <p>
 * An <i>open type</i> is defined by:
 * <ul>
 *  <li>the fully qualified Java class name of the open data values this type describes;
 *      note that only a limited set of Java classes is allowed for open data values
 *      (see {@link #ALLOWED_CLASSNAMES_LIST ALLOWED_CLASSNAMES_LIST}),</li>
 *  <li>its name,</li>
 *  <li>its description.</li>
 * </ul>
 *
 * <p>
 *  <code> OpenType </code>类是描述实际<i>开放数据值</i>的所有类的父抽象类
 * <p>
 *  <i>打开类型</i>由以下定义：
 * <ul>
 *  <li>此类型描述的开放数据值的完全限定的Java类名;请注意,对于打开的数据值只允许有限的一组Java类(请参阅{@link #ALLOWED_CLASSNAMES_LIST ALLOWED_CLASSNAMES_LIST}
 * ),</li> <li>其名称,</li> <li>。
 * </ul>
 * 
 * 
 * @param <T> the Java type that instances described by this type must
 * have.  For example, {@link SimpleType#INTEGER} is a {@code
 * SimpleType<Integer>} which is a subclass of {@code OpenType<Integer>},
 * meaning that an attribute, parameter, or return value that is described
 * as a {@code SimpleType.INTEGER} must have Java type
 * {@link Integer}.
 *
 * @since 1.5
 */
public abstract class OpenType<T> implements Serializable {

    /* Serial version */
    static final long serialVersionUID = -9195195325186646468L;


    /**
     * List of the fully qualified names of the Java classes allowed for open
     * data values. A multidimensional array of any one of these classes or
     * their corresponding primitive types is also an allowed class for open
     * data values.
     *
       <pre>ALLOWED_CLASSNAMES_LIST = {
        "java.lang.Void",
        "java.lang.Boolean",
        "java.lang.Character",
        "java.lang.Byte",
        "java.lang.Short",
        "java.lang.Integer",
        "java.lang.Long",
        "java.lang.Float",
        "java.lang.Double",
        "java.lang.String",
        "java.math.BigDecimal",
        "java.math.BigInteger",
        "java.util.Date",
        "javax.management.ObjectName",
        CompositeData.class.getName(),
        TabularData.class.getName() } ;
       </pre>
     *
     * <p>
     * 允许打开数据值的Ja​​va类的完全限定名称列表这些类中的任何一个或其对应的基本类型的多维数组也是开放数据值的允许类
     * 
     *  <pre> ALLOWED_CLASSNAMES_LIST = {"javalangVoid","javalangBoolean","javalangCharacter,javalangByte,javalangShort,javalangInteger,javalangLong,javalangFloat,javalangDouble,javalangString,javamathBigDecimal, ","<pre>ALLOWED_CLASSNAMES_LIST = { \"javalangVoid\", \"javalangBoolean\", \"javalangCharacter\", \"javalangByte\", \"javalangShort\", \"javalangInteger\", \"javalangLong\", \"javalangFloat\", \"javalangDouble\", \"javalangString\", \"javamathBigDecimal\"javamathBigInteger","javautilDate","javaxmanagementObjectName",CompositeDataclassgetName(),TabularDataclassgetName()}
     * ;。
     * </pre>
     * 
     */
    public static final List<String> ALLOWED_CLASSNAMES_LIST =
      Collections.unmodifiableList(
        Arrays.asList(
          "java.lang.Void",
          "java.lang.Boolean",
          "java.lang.Character",
          "java.lang.Byte",
          "java.lang.Short",
          "java.lang.Integer",
          "java.lang.Long",
          "java.lang.Float",
          "java.lang.Double",
          "java.lang.String",
          "java.math.BigDecimal",
          "java.math.BigInteger",
          "java.util.Date",
          "javax.management.ObjectName",
          CompositeData.class.getName(),        // better refer to these two class names like this, rather than hardcoding a string,
          TabularData.class.getName()) );       // in case the package of these classes should change (who knows...)


    /**
    /* <p>
    /* 
     * @deprecated Use {@link #ALLOWED_CLASSNAMES_LIST ALLOWED_CLASSNAMES_LIST} instead.
     */
    @Deprecated
    public static final String[] ALLOWED_CLASSNAMES =
        ALLOWED_CLASSNAMES_LIST.toArray(new String[0]);


    /**
    /* <p>
    /* 
     * @serial The fully qualified Java class name of open data values this
     *         type describes.
     */
    private String className;

    /**
    /* <p>
    /* 
     * @serial The type description (should not be null or empty).
     */
    private String description;

    /**
    /* <p>
    /* 
     * @serial The name given to this type (should not be null or empty).
     */
    private String typeName;

    /**
     * Tells if this type describes an array (checked in constructor).
     * <p>
     *  告诉这个类型是否描述了一个数组(在构造函数中检查)
     * 
     */
    private transient boolean isArray = false;

    /**
     * Cached Descriptor for this OpenType, constructed on demand.
     * <p>
     *  此OpenType的缓存描述符,按需构建
     * 
     */
    private transient Descriptor descriptor;

    /* *** Constructor *** */

    /**
     * Constructs an <code>OpenType</code> instance (actually a subclass instance as <code>OpenType</code> is abstract),
     * checking for the validity of the given parameters.
     * The validity constraints are described below for each parameter.
     * <br>&nbsp;
     * <p>
     * 构造一个<code> OpenType </code>实例(实际上是<code> OpenType </code>的子类实例是抽象的),检查给定参数的有效性。
     * 下面描述每个参数的有效性约束<br>&nbsp ;。
     * 
     * 
     * @param  className  The fully qualified Java class name of the open data values this open type describes.
     *                    The valid Java class names allowed for open data values are listed in
     *                    {@link #ALLOWED_CLASSNAMES_LIST ALLOWED_CLASSNAMES_LIST}.
     *                    A multidimensional array of any one of these classes
     *                    or their corresponding primitive types is also an allowed class,
     *                    in which case the class name follows the rules defined by the method
     *                    {@link Class#getName() getName()} of <code>java.lang.Class</code>.
     *                    For example, a 3-dimensional array of Strings has for class name
     *                    &quot;<code>[[[Ljava.lang.String;</code>&quot; (without the quotes).
     * <br>&nbsp;
     * @param  typeName  The name given to the open type this instance represents; cannot be a null or empty string.
     * <br>&nbsp;
     * @param  description  The human readable description of the open type this instance represents;
     *                      cannot be a null or empty string.
     * <br>&nbsp;
     * @throws IllegalArgumentException  if <var>className</var>, <var>typeName</var> or <var>description</var>
     *                                   is a null or empty string
     * <br>&nbsp;
     * @throws OpenDataException  if <var>className</var> is not one of the allowed Java class names for open data
     */
    protected OpenType(String  className,
                       String  typeName,
                       String  description) throws OpenDataException {
        checkClassNameOverride();
        this.typeName = valid("typeName", typeName);
        this.description = valid("description", description);
        this.className = validClassName(className);
        this.isArray = (this.className != null && this.className.startsWith("["));
    }

    /* Package-private constructor for callers we trust to get it right. */
    OpenType(String className, String typeName, String description,
             boolean isArray) {
        this.className   = valid("className",className);
        this.typeName    = valid("typeName", typeName);
        this.description = valid("description", description);
        this.isArray     = isArray;
    }

    private void checkClassNameOverride() throws SecurityException {
        if (this.getClass().getClassLoader() == null)
            return;  // We trust bootstrap classes.
        if (overridesGetClassName(this.getClass())) {
            final GetPropertyAction getExtendOpenTypes =
                new GetPropertyAction("jmx.extend.open.types");
            if (AccessController.doPrivileged(getExtendOpenTypes) == null) {
                throw new SecurityException("Cannot override getClassName() " +
                        "unless -Djmx.extend.open.types");
            }
        }
    }

    private static boolean overridesGetClassName(final Class<?> c) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
                try {
                    return (c.getMethod("getClassName").getDeclaringClass() !=
                            OpenType.class);
                } catch (Exception e) {
                    return true;  // fail safe
                }
            }
        });
    }

    private static String validClassName(String className) throws OpenDataException {
        className   = valid("className", className);

        // Check if className describes an array class, and determines its elements' class name.
        // (eg: a 3-dimensional array of Strings has for class name: "[[[Ljava.lang.String;")
        //
        int n = 0;
        while (className.startsWith("[", n)) {
            n++;
        }
        String eltClassName; // class name of array elements
        boolean isPrimitiveArray = false;
        if (n > 0) {
            if (className.startsWith("L", n) && className.endsWith(";")) {
                // removes the n leading '[' + the 'L' characters
                // and the last ';' character
                eltClassName = className.substring(n+1, className.length()-1);
            } else if (n == className.length() - 1) {
                // removes the n leading '[' characters
                eltClassName = className.substring(n, className.length());
                isPrimitiveArray = true;
            } else {
                throw new OpenDataException("Argument className=\"" + className +
                        "\" is not a valid class name");
            }
        } else {
            // not an array
            eltClassName = className;
        }

        // Check that eltClassName's value is one of the allowed basic data types for open data
        //
        boolean ok = false;
        if (isPrimitiveArray) {
            ok = ArrayType.isPrimitiveContentType(eltClassName);
        } else {
            ok = ALLOWED_CLASSNAMES_LIST.contains(eltClassName);
        }
        if ( ! ok ) {
            throw new OpenDataException("Argument className=\""+ className +
                                        "\" is not one of the allowed Java class names for open data.");
        }

        return className;
    }

    /* Return argValue.trim() provided argValue is neither null nor empty;
    /* <p>
    /* 
       otherwise throw IllegalArgumentException.  */
    private static String valid(String argName, String argValue) {
        if (argValue == null || (argValue = argValue.trim()).equals(""))
            throw new IllegalArgumentException("Argument " + argName +
                                               " cannot be null or empty");
        return argValue;
    }

    /* Package-private access to a Descriptor containing this OpenType. */
    synchronized Descriptor getDescriptor() {
        if (descriptor == null) {
            descriptor = new ImmutableDescriptor(new String[] {"openType"},
                                                 new Object[] {this});
        }
        return descriptor;
    }

    /* *** Open type information methods *** */

    /**
     * Returns the fully qualified Java class name of the open data values
     * this open type describes.
     * The only possible Java class names for open data values are listed in
     * {@link #ALLOWED_CLASSNAMES_LIST ALLOWED_CLASSNAMES_LIST}.
     * A multidimensional array of any one of these classes or their
     * corresponding primitive types is also an allowed class,
     * in which case the class name follows the rules defined by the method
     * {@link Class#getName() getName()} of <code>java.lang.Class</code>.
     * For example, a 3-dimensional array of Strings has for class name
     * &quot;<code>[[[Ljava.lang.String;</code>&quot; (without the quotes),
     * a 3-dimensional array of Integers has for class name
     * &quot;<code>[[[Ljava.lang.Integer;</code>&quot; (without the quotes),
     * and a 3-dimensional array of int has for class name
     * &quot;<code>[[[I</code>&quot; (without the quotes)
     *
     * <p>
     * 返回此开放类型描述的打开数据值的标准Java类名称{@link #ALLOWED_CLASSNAMES_LIST ALLOWED_CLASSNAMES_LIST}列出了打开数据值的唯一可能的Java类名
     * 称。
     * 这些类中的任何一个或其对应的基本类型的多维数组也是允许的类,在这种情况下,类名遵循由<code> javalangClass </code>的方法{@link Class#getName()getName()}
     * 定义的规则。
     * 例如,一个3维数组对于类名"<code> [[[LjavalangString; </code>") (没有引号),一个3维的整数数组具有类名"<code> [[[Ljavalang整数; </code>
     *  (不带引号),并且int的3维数组具有类名"<code> [[[I </code> (不含引号)。
     * 
     * 
     * @return the class name.
     */
    public String getClassName() {
        return className;
    }

    // A version of getClassName() that can only be called from within this
    // package and that cannot be overridden.
    String safeGetClassName() {
        return className;
    }

    /**
     * Returns the name of this <code>OpenType</code> instance.
     *
     * <p>
     * 返回此<code> OpenType </code>实例的名称
     * 
     * 
     * @return the type name.
     */
    public String getTypeName() {

        return typeName;
    }

    /**
     * Returns the text description of this <code>OpenType</code> instance.
     *
     * <p>
     *  返回此<code> OpenType </code>实例的文本描述
     * 
     * 
     * @return the description.
     */
    public String getDescription() {

        return description;
    }

    /**
     * Returns <code>true</code> if the open data values this open
     * type describes are arrays, <code>false</code> otherwise.
     *
     * <p>
     *  如果此开放类型描述的开放数据值为数组,则返回<code> true </code>,否则返回<code> false </code>
     * 
     * 
     * @return true if this is an array type.
     */
    public boolean isArray() {

        return isArray;
    }

    /**
     * Tests whether <var>obj</var> is a value for this open type.
     *
     * <p>
     *  测试<var> obj </var>是否为此打开类型的值
     * 
     * 
     * @param obj the object to be tested for validity.
     *
     * @return <code>true</code> if <var>obj</var> is a value for this
     * open type, <code>false</code> otherwise.
     */
    public abstract boolean isValue(Object obj) ;

    /**
     * Tests whether values of the given type can be assigned to this open type.
     * The default implementation of this method returns true only if the
     * types are equal.
     *
     * <p>
     *  测试给定类型的值是否可以分配给此开放类型此方法的默认实现仅在类型相等时返回true
     * 
     * 
     * @param ot the type to be tested.
     *
     * @return true if {@code ot} is assignable to this open type.
     */
    boolean isAssignableFrom(OpenType<?> ot) {
        return this.equals(ot);
    }

    /* *** Methods overriden from class Object *** */

    /**
     * Compares the specified <code>obj</code> parameter with this
     * open type instance for equality.
     *
     * <p>
     *  将指定的<code> obj </code>参数与此开放类型实例进行比较以确保相等
     * 
     * 
     * @param obj the object to compare to.
     *
     * @return true if this object and <code>obj</code> are equal.
     */
    public abstract boolean equals(Object obj) ;

    public abstract int hashCode() ;

    /**
     * Returns a string representation of this open type instance.
     *
     * <p>
     *  返回此打开类型实例的字符串表示形式
     * 
     * 
     * @return the string representation.
     */
    public abstract String toString() ;

    /**
     * Deserializes an {@link OpenType} from an {@link java.io.ObjectInputStream}.
     * <p>
     *  从{@link javaioObjectInputStream}反序列化{@link OpenType}
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        checkClassNameOverride();
        ObjectInputStream.GetField fields = in.readFields();
        final String classNameField;
        final String descriptionField;
        final String typeNameField;
        try {
            classNameField =
                validClassName((String) fields.get("className", null));
            descriptionField =
                valid("description", (String) fields.get("description", null));
            typeNameField =
                valid("typeName", (String) fields.get("typeName", null));
        } catch (Exception e) {
            IOException e2 = new InvalidObjectException(e.getMessage());
            e2.initCause(e);
            throw e2;
        }
        className = classNameField;
        description = descriptionField;
        typeName = typeNameField;
        isArray = (className.startsWith("["));
    }
}
