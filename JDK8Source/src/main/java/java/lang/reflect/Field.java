/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.reflect;

import sun.reflect.CallerSensitive;
import sun.reflect.FieldAccessor;
import sun.reflect.Reflection;
import sun.reflect.generics.repository.FieldRepository;
import sun.reflect.generics.factory.CoreReflectionFactory;
import sun.reflect.generics.factory.GenericsFactory;
import sun.reflect.generics.scope.ClassScope;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import sun.reflect.annotation.AnnotationParser;
import sun.reflect.annotation.AnnotationSupport;
import sun.reflect.annotation.TypeAnnotation;
import sun.reflect.annotation.TypeAnnotationParser;

/**
 * A {@code Field} provides information about, and dynamic access to, a
 * single field of a class or an interface.  The reflected field may
 * be a class (static) field or an instance field.
 *
 * <p>A {@code Field} permits widening conversions to occur during a get or
 * set access operation, but throws an {@code IllegalArgumentException} if a
 * narrowing conversion would occur.
 *
 * <p>
 *  {@code Field}提供有关类或接口的单个​​字段的信息以及动态访问。反射场可以是类(静态)场或实例场。
 * 
 *  <p> {@code Field}允许在获取或设置访问操作期间扩展转换,但如果发生缩小转换,则会抛出{@code IllegalArgumentException}。
 * 
 * 
 * @see Member
 * @see java.lang.Class
 * @see java.lang.Class#getFields()
 * @see java.lang.Class#getField(String)
 * @see java.lang.Class#getDeclaredFields()
 * @see java.lang.Class#getDeclaredField(String)
 *
 * @author Kenneth Russell
 * @author Nakul Saraiya
 */
public final
class Field extends AccessibleObject implements Member {

    private Class<?>            clazz;
    private int                 slot;
    // This is guaranteed to be interned by the VM in the 1.4
    // reflection implementation
    private String              name;
    private Class<?>            type;
    private int                 modifiers;
    // Generics and annotations support
    private transient String    signature;
    // generic info repository; lazily initialized
    private transient FieldRepository genericInfo;
    private byte[]              annotations;
    // Cached field accessor created without override
    private FieldAccessor fieldAccessor;
    // Cached field accessor created with override
    private FieldAccessor overrideFieldAccessor;
    // For sharing of FieldAccessors. This branching structure is
    // currently only two levels deep (i.e., one root Field and
    // potentially many Field objects pointing to it.)
    //
    // If this branching structure would ever contain cycles, deadlocks can
    // occur in annotation code.
    private Field               root;

    // Generics infrastructure

    private String getGenericSignature() {return signature;}

    // Accessor for factory
    private GenericsFactory getFactory() {
        Class<?> c = getDeclaringClass();
        // create scope and factory
        return CoreReflectionFactory.make(c, ClassScope.make(c));
    }

    // Accessor for generic info repository
    private FieldRepository getGenericInfo() {
        // lazily initialize repository if necessary
        if (genericInfo == null) {
            // create and cache generic info repository
            genericInfo = FieldRepository.make(getGenericSignature(),
                                               getFactory());
        }
        return genericInfo; //return cached repository
    }


    /**
     * Package-private constructor used by ReflectAccess to enable
     * instantiation of these objects in Java code from the java.lang
     * package via sun.reflect.LangReflectAccess.
     * <p>
     *  Package-private构造函数,由ReflectAccess用于通过sun.reflect.LangReflectAccess从java.lang包中启用Java代码中的这些对象的实例化。
     * 
     */
    Field(Class<?> declaringClass,
          String name,
          Class<?> type,
          int modifiers,
          int slot,
          String signature,
          byte[] annotations)
    {
        this.clazz = declaringClass;
        this.name = name;
        this.type = type;
        this.modifiers = modifiers;
        this.slot = slot;
        this.signature = signature;
        this.annotations = annotations;
    }

    /**
     * Package-private routine (exposed to java.lang.Class via
     * ReflectAccess) which returns a copy of this Field. The copy's
     * "root" field points to this Field.
     * <p>
     *  Package-private例程(通过ReflectAccess暴露给java.lang.Class),它返回此字段的副本。副本的"根"字段指向此字段。
     * 
     */
    Field copy() {
        // This routine enables sharing of FieldAccessor objects
        // among Field objects which refer to the same underlying
        // method in the VM. (All of this contortion is only necessary
        // because of the "accessibility" bit in AccessibleObject,
        // which implicitly requires that new java.lang.reflect
        // objects be fabricated for each reflective call on Class
        // objects.)
        if (this.root != null)
            throw new IllegalArgumentException("Can not copy a non-root Field");

        Field res = new Field(clazz, name, type, modifiers, slot, signature, annotations);
        res.root = this;
        // Might as well eagerly propagate this if already present
        res.fieldAccessor = fieldAccessor;
        res.overrideFieldAccessor = overrideFieldAccessor;

        return res;
    }

    /**
     * Returns the {@code Class} object representing the class or interface
     * that declares the field represented by this {@code Field} object.
     * <p>
     *  返回表示声明由此{@code Field}对象表示的字段的类或接口的{@code Class}对象。
     * 
     */
    public Class<?> getDeclaringClass() {
        return clazz;
    }

    /**
     * Returns the name of the field represented by this {@code Field} object.
     * <p>
     *  返回由此{@code Field}对象表示的字段的名称。
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Java language modifiers for the field represented
     * by this {@code Field} object, as an integer. The {@code Modifier} class should
     * be used to decode the modifiers.
     *
     * <p>
     *  返回由此{@code Field}对象表示的字段的Java语言修饰符作为整数。 {@code Modifier}类应该用于解码修饰符。
     * 
     * 
     * @see Modifier
     */
    public int getModifiers() {
        return modifiers;
    }

    /**
     * Returns {@code true} if this field represents an element of
     * an enumerated type; returns {@code false} otherwise.
     *
     * <p>
     *  如果此字段表示枚举类型的元素,则返回{@code true};否则返回{@code false}。
     * 
     * 
     * @return {@code true} if and only if this field represents an element of
     * an enumerated type.
     * @since 1.5
     */
    public boolean isEnumConstant() {
        return (getModifiers() & Modifier.ENUM) != 0;
    }

    /**
     * Returns {@code true} if this field is a synthetic
     * field; returns {@code false} otherwise.
     *
     * <p>
     *  如果此字段是合成字段,则返回{@code true};否则返回{@code false}。
     * 
     * 
     * @return true if and only if this field is a synthetic
     * field as defined by the Java Language Specification.
     * @since 1.5
     */
    public boolean isSynthetic() {
        return Modifier.isSynthetic(getModifiers());
    }

    /**
     * Returns a {@code Class} object that identifies the
     * declared type for the field represented by this
     * {@code Field} object.
     *
     * <p>
     * 返回一个{@code Class}对象,该对象标识由此{@code Field}对象表示的字段的声明类型。
     * 
     * 
     * @return a {@code Class} object identifying the declared
     * type of the field represented by this object
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Returns a {@code Type} object that represents the declared type for
     * the field represented by this {@code Field} object.
     *
     * <p>If the {@code Type} is a parameterized type, the
     * {@code Type} object returned must accurately reflect the
     * actual type parameters used in the source code.
     *
     * <p>If the type of the underlying field is a type variable or a
     * parameterized type, it is created. Otherwise, it is resolved.
     *
     * <p>
     *  返回一个{@code Type}对象,它表示由此{@code Field}对象表示的字段的声明类型。
     * 
     *  <p>如果{@code Type}是参数化类型,则返回的{@code Type}对象必须准确反映源代码中使用的实际类型参数。
     * 
     *  <p>如果底层字段的类型是类型变量或参数化类型,则会创建它。否则,它被解决。
     * 
     * 
     * @return a {@code Type} object that represents the declared type for
     *     the field represented by this {@code Field} object
     * @throws GenericSignatureFormatError if the generic field
     *     signature does not conform to the format specified in
     *     <cite>The Java&trade; Virtual Machine Specification</cite>
     * @throws TypeNotPresentException if the generic type
     *     signature of the underlying field refers to a non-existent
     *     type declaration
     * @throws MalformedParameterizedTypeException if the generic
     *     signature of the underlying field refers to a parameterized type
     *     that cannot be instantiated for any reason
     * @since 1.5
     */
    public Type getGenericType() {
        if (getGenericSignature() != null)
            return getGenericInfo().getGenericType();
        else
            return getType();
    }


    /**
     * Compares this {@code Field} against the specified object.  Returns
     * true if the objects are the same.  Two {@code Field} objects are the same if
     * they were declared by the same class and have the same name
     * and type.
     * <p>
     *  将此{@code Field}与指定的对象进行比较。如果对象相同,则返回true。如果两个{@code Field}对象由相同的类声明并具有相同的名称和类型,则它们是相同的。
     * 
     */
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Field) {
            Field other = (Field)obj;
            return (getDeclaringClass() == other.getDeclaringClass())
                && (getName() == other.getName())
                && (getType() == other.getType());
        }
        return false;
    }

    /**
     * Returns a hashcode for this {@code Field}.  This is computed as the
     * exclusive-or of the hashcodes for the underlying field's
     * declaring class name and its name.
     * <p>
     *  返回此{@code Field}的哈希码。这是计算为底层字段的声明类名称及其名称的哈希码的异或。
     * 
     */
    public int hashCode() {
        return getDeclaringClass().getName().hashCode() ^ getName().hashCode();
    }

    /**
     * Returns a string describing this {@code Field}.  The format is
     * the access modifiers for the field, if any, followed
     * by the field type, followed by a space, followed by
     * the fully-qualified name of the class declaring the field,
     * followed by a period, followed by the name of the field.
     * For example:
     * <pre>
     *    public static final int java.lang.Thread.MIN_PRIORITY
     *    private int java.io.FileDescriptor.fd
     * </pre>
     *
     * <p>The modifiers are placed in canonical order as specified by
     * "The Java Language Specification".  This is {@code public},
     * {@code protected} or {@code private} first, and then other
     * modifiers in the following order: {@code static}, {@code final},
     * {@code transient}, {@code volatile}.
     *
     * <p>
     *  返回描述此{@code Field}的字符串。格式是字段的访问修饰符(如果有),后跟字段类型,后跟一个空格,后跟一个声明该字段的类的完全限定名称,后跟一个句点,后跟一个字段名称。例如：
     * <pre>
     *  public static final int java.lang.Thread.MIN_PRIORITY private int java.io.FileDescriptor.fd
     * </pre>
     * 
     * <p>修饰符按照"Java语言规范"指定的规范顺序放置。
     * 这是{@code public},{@ code protected}或{@code private},然后是其他修饰符,顺序如下：{@code static},{@code final},{@code transient}
     * 代码volatile}。
     * <p>修饰符按照"Java语言规范"指定的规范顺序放置。
     * 
     * 
     * @return a string describing this {@code Field}
     * @jls 8.3.1 Field Modifiers
     */
    public String toString() {
        int mod = getModifiers();
        return (((mod == 0) ? "" : (Modifier.toString(mod) + " "))
            + getType().getTypeName() + " "
            + getDeclaringClass().getTypeName() + "."
            + getName());
    }

    /**
     * Returns a string describing this {@code Field}, including
     * its generic type.  The format is the access modifiers for the
     * field, if any, followed by the generic field type, followed by
     * a space, followed by the fully-qualified name of the class
     * declaring the field, followed by a period, followed by the name
     * of the field.
     *
     * <p>The modifiers are placed in canonical order as specified by
     * "The Java Language Specification".  This is {@code public},
     * {@code protected} or {@code private} first, and then other
     * modifiers in the following order: {@code static}, {@code final},
     * {@code transient}, {@code volatile}.
     *
     * <p>
     *  返回描述此{@code Field}的字符串,包括其通用类型。格式是字段的访问修饰符(如果有),后跟通用字段类型,后跟一个空格,后跟一个声明该字段的类的完全限定名称,后跟一个句点,后跟一个句点领域。
     * 
     *  <p>修饰符按照"Java语言规范"指定的规范顺序放置。
     * 这是{@code public},{@ code protected}或{@code private},然后是其他修饰符,顺序如下：{@code static},{@code final},{@code transient}
     * 代码volatile}。
     *  <p>修饰符按照"Java语言规范"指定的规范顺序放置。
     * 
     * 
     * @return a string describing this {@code Field}, including
     * its generic type
     *
     * @since 1.5
     * @jls 8.3.1 Field Modifiers
     */
    public String toGenericString() {
        int mod = getModifiers();
        Type fieldType = getGenericType();
        return (((mod == 0) ? "" : (Modifier.toString(mod) + " "))
            + fieldType.getTypeName() + " "
            + getDeclaringClass().getTypeName() + "."
            + getName());
    }

    /**
     * Returns the value of the field represented by this {@code Field}, on
     * the specified object. The value is automatically wrapped in an
     * object if it has a primitive type.
     *
     * <p>The underlying field's value is obtained as follows:
     *
     * <p>If the underlying field is a static field, the {@code obj} argument
     * is ignored; it may be null.
     *
     * <p>Otherwise, the underlying field is an instance field.  If the
     * specified {@code obj} argument is null, the method throws a
     * {@code NullPointerException}. If the specified object is not an
     * instance of the class or interface declaring the underlying
     * field, the method throws an {@code IllegalArgumentException}.
     *
     * <p>If this {@code Field} object is enforcing Java language access control, and
     * the underlying field is inaccessible, the method throws an
     * {@code IllegalAccessException}.
     * If the underlying field is static, the class that declared the
     * field is initialized if it has not already been initialized.
     *
     * <p>Otherwise, the value is retrieved from the underlying instance
     * or static field.  If the field has a primitive type, the value
     * is wrapped in an object before being returned, otherwise it is
     * returned as is.
     *
     * <p>If the field is hidden in the type of {@code obj},
     * the field's value is obtained according to the preceding rules.
     *
     * <p>
     *  返回指定对象上由此{@code Field}表示的字段的值。如果值具有原始类型,则该值会自动包装在对象中。
     * 
     *  <p>底层字段的值如下所示：
     * 
     *  <p>如果底层字段是静态字段,则{@code obj}参数将被忽略;它可以为null。
     * 
     * <p>否则,底层字段是一个实例字段。如果指定的{@code obj}参数为null,那么该方法会抛出一个{@code NullPointerException}。
     * 如果指定的对象不是声明基础字段的类或接口的实例,则该方法会抛出{@code IllegalArgumentException}。
     * 
     *  <p>如果{@code Field}对象强制实施Java语言访问控制,并且底层字段无法访问,则该方法会抛出{@code IllegalAccessException}。
     * 如果底层字段是静态的,那么声明该字段的类在未初始化的情况下被初始化。
     * 
     *  <p>否则,将从底层实例或静态字段检索该值。如果字段具有原始类型,则在返回之前将值包装在对象中,否则返回原样。
     * 
     *  <p>如果字段在{@code obj}类型中被隐藏,则根据前述规则获取字段的值。
     * 
     * 
     * @param obj object from which the represented field's value is
     * to be extracted
     * @return the value of the represented field in object
     * {@code obj}; primitive values are wrapped in an appropriate
     * object before being returned
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof).
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     */
    @CallerSensitive
    public Object get(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).get(obj);
    }

    /**
     * Gets the value of a static or instance {@code boolean} field.
     *
     * <p>
     *  获取静态或实例{@code boolean}字段的值。
     * 
     * 
     * @param obj the object to extract the {@code boolean} value
     * from
     * @return the value of the {@code boolean} field
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code boolean} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#get
     */
    @CallerSensitive
    public boolean getBoolean(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getBoolean(obj);
    }

    /**
     * Gets the value of a static or instance {@code byte} field.
     *
     * <p>
     *  获取静态或实例{@code byte}字段的值。
     * 
     * 
     * @param obj the object to extract the {@code byte} value
     * from
     * @return the value of the {@code byte} field
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code byte} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#get
     */
    @CallerSensitive
    public byte getByte(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getByte(obj);
    }

    /**
     * Gets the value of a static or instance field of type
     * {@code char} or of another primitive type convertible to
     * type {@code char} via a widening conversion.
     *
     * <p>
     *  通过扩展转换获取类型为{@code char}的静态或实例字段的值或可转换为类型为{@code char}的另一个基本类型的值。
     * 
     * 
     * @param obj the object to extract the {@code char} value
     * from
     * @return the value of the field converted to type {@code char}
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code char} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see Field#get
     */
    @CallerSensitive
    public char getChar(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getChar(obj);
    }

    /**
     * Gets the value of a static or instance field of type
     * {@code short} or of another primitive type convertible to
     * type {@code short} via a widening conversion.
     *
     * <p>
     *  获取类型为{@code short}的静态或实例字段的值或通过加宽转换可转换为类型{@code short}的另一个基本类型的值。
     * 
     * 
     * @param obj the object to extract the {@code short} value
     * from
     * @return the value of the field converted to type {@code short}
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code short} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#get
     */
    @CallerSensitive
    public short getShort(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getShort(obj);
    }

    /**
     * Gets the value of a static or instance field of type
     * {@code int} or of another primitive type convertible to
     * type {@code int} via a widening conversion.
     *
     * <p>
     * 获取类型为{@code int}的静态或实例字段的值或通过加宽转换可转换为类型{@code int}的另一个基本类型的值。
     * 
     * 
     * @param obj the object to extract the {@code int} value
     * from
     * @return the value of the field converted to type {@code int}
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code int} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#get
     */
    @CallerSensitive
    public int getInt(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getInt(obj);
    }

    /**
     * Gets the value of a static or instance field of type
     * {@code long} or of another primitive type convertible to
     * type {@code long} via a widening conversion.
     *
     * <p>
     *  获取类型为{@code long}的静态或实例字段的值,或通过加宽转换可转换为类型{@code long}的另一个基本类型的值。
     * 
     * 
     * @param obj the object to extract the {@code long} value
     * from
     * @return the value of the field converted to type {@code long}
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code long} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#get
     */
    @CallerSensitive
    public long getLong(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getLong(obj);
    }

    /**
     * Gets the value of a static or instance field of type
     * {@code float} or of another primitive type convertible to
     * type {@code float} via a widening conversion.
     *
     * <p>
     *  获取类型为{@code float}的静态或实例字段的值或通过加宽转换可转换为类型{@code float}的另一个基本类型的值。
     * 
     * 
     * @param obj the object to extract the {@code float} value
     * from
     * @return the value of the field converted to type {@code float}
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code float} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see Field#get
     */
    @CallerSensitive
    public float getFloat(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getFloat(obj);
    }

    /**
     * Gets the value of a static or instance field of type
     * {@code double} or of another primitive type convertible to
     * type {@code double} via a widening conversion.
     *
     * <p>
     *  获取类型为{@code double}的静态或实例字段的值或通过加宽转换可转换为类型{@code double}的另一个基本类型的值。
     * 
     * 
     * @param obj the object to extract the {@code double} value
     * from
     * @return the value of the field converted to type {@code double}
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is inaccessible.
     * @exception IllegalArgumentException  if the specified object is not
     *              an instance of the class or interface declaring the
     *              underlying field (or a subclass or implementor
     *              thereof), or if the field value cannot be
     *              converted to the type {@code double} by a
     *              widening conversion.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#get
     */
    @CallerSensitive
    public double getDouble(Object obj)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        return getFieldAccessor(obj).getDouble(obj);
    }

    /**
     * Sets the field represented by this {@code Field} object on the
     * specified object argument to the specified new value. The new
     * value is automatically unwrapped if the underlying field has a
     * primitive type.
     *
     * <p>The operation proceeds as follows:
     *
     * <p>If the underlying field is static, the {@code obj} argument is
     * ignored; it may be null.
     *
     * <p>Otherwise the underlying field is an instance field.  If the
     * specified object argument is null, the method throws a
     * {@code NullPointerException}.  If the specified object argument is not
     * an instance of the class or interface declaring the underlying
     * field, the method throws an {@code IllegalArgumentException}.
     *
     * <p>If this {@code Field} object is enforcing Java language access control, and
     * the underlying field is inaccessible, the method throws an
     * {@code IllegalAccessException}.
     *
     * <p>If the underlying field is final, the method throws an
     * {@code IllegalAccessException} unless {@code setAccessible(true)}
     * has succeeded for this {@code Field} object
     * and the field is non-static. Setting a final field in this way
     * is meaningful only during deserialization or reconstruction of
     * instances of classes with blank final fields, before they are
     * made available for access by other parts of a program. Use in
     * any other context may have unpredictable effects, including cases
     * in which other parts of a program continue to use the original
     * value of this field.
     *
     * <p>If the underlying field is of a primitive type, an unwrapping
     * conversion is attempted to convert the new value to a value of
     * a primitive type.  If this attempt fails, the method throws an
     * {@code IllegalArgumentException}.
     *
     * <p>If, after possible unwrapping, the new value cannot be
     * converted to the type of the underlying field by an identity or
     * widening conversion, the method throws an
     * {@code IllegalArgumentException}.
     *
     * <p>If the underlying field is static, the class that declared the
     * field is initialized if it has not already been initialized.
     *
     * <p>The field is set to the possibly unwrapped and widened new value.
     *
     * <p>If the field is hidden in the type of {@code obj},
     * the field's value is set according to the preceding rules.
     *
     * <p>
     *  将指定对象参数上的此{@code Field}对象所表示的字段设置为指定的新值。如果基础字段具有原始类型,则新值将自动展开。
     * 
     *  <p>操作如下：
     * 
     *  <p>如果底层字段是静态的,则忽略{@code obj}参数;它可以为null。
     * 
     *  <p>否则,底层字段是一个实例字段。如果指定的对象参数为null,则该方法会抛出一个{@code NullPointerException}。
     * 如果指定的对象参数不是声明基础字段的类或接口的实例,则该方法会抛出{@code IllegalArgumentException}。
     * 
     *  <p>如果{@code Field}对象强制实施Java语言访问控制,并且底层字段无法访问,则该方法会抛出{@code IllegalAccessException}。
     * 
     * <p>如果底层字段是final,该方法会抛出一个{@code IllegalAccessException},除非{@code setAccessible(true)}已成功处理此{@code Field}
     * 对象,并且该字段是非静态的。
     * 以这种方式设置最终字段仅在具有空白最终字段的类的实例的反序列化或重构期间是有意义的,然后它们才可用于由程序的其它部分访问。
     * 在任何其他上下文中使用可能具有不可预测的效果,包括程序的其他部分继续使用该字段的原始值的情况。
     * 
     *  <p>如果底层字段是原始类型,则尝试将新值转换为原始类型的值的展开转换。如果此尝试失败,该方法将抛出{@code IllegalArgumentException}。
     * 
     *  <p>如果在可能解开后,新值无法通过身份或扩展转换转换为基础字段的类型,则该方法会抛出{@code IllegalArgumentException}。
     * 
     *  <p>如果底层字段是静态的,则声明该字段的类在未初始化的情况下被初始化。
     * 
     *  <p>字段设置为可能未包装和扩展的新值。
     * 
     *  <p>如果字段在{@code obj}类型中被隐藏,则字段的值根据前面的规则设置。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param value the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     */
    @CallerSensitive
    public void set(Object obj, Object value)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).set(obj, value);
    }

    /**
     * Sets the value of a field as a {@code boolean} on the specified object.
     * This method is equivalent to
     * {@code set(obj, zObj)},
     * where {@code zObj} is a {@code Boolean} object and
     * {@code zObj.booleanValue() == z}.
     *
     * <p>
     * 将字段的值设置为指定对象上的{@code boolean}。
     * 这个方法等同于{@code set(obj,zObj)},其中{@code zObj}是一个{@code Boolean}对象和{@code zObj.booleanValue()== z}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param z   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setBoolean(Object obj, boolean z)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setBoolean(obj, z);
    }

    /**
     * Sets the value of a field as a {@code byte} on the specified object.
     * This method is equivalent to
     * {@code set(obj, bObj)},
     * where {@code bObj} is a {@code Byte} object and
     * {@code bObj.byteValue() == b}.
     *
     * <p>
     *  将字段的值设置为指定对象上的{@code byte}。
     * 这个方法等价于{@code set(obj,bObj)},其中{@code bObj}是一个{@code Byte}对象和{@code bObj.byteValue()== b}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param b   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setByte(Object obj, byte b)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setByte(obj, b);
    }

    /**
     * Sets the value of a field as a {@code char} on the specified object.
     * This method is equivalent to
     * {@code set(obj, cObj)},
     * where {@code cObj} is a {@code Character} object and
     * {@code cObj.charValue() == c}.
     *
     * <p>
     *  将字段的值设置为指定对象上的{@code char}。
     * 这个方法等价于{@code set(obj,cObj)},其中{@code cObj}是一个{@code Character}对象和{@code cObj.charValue()== c}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param c   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setChar(Object obj, char c)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setChar(obj, c);
    }

    /**
     * Sets the value of a field as a {@code short} on the specified object.
     * This method is equivalent to
     * {@code set(obj, sObj)},
     * where {@code sObj} is a {@code Short} object and
     * {@code sObj.shortValue() == s}.
     *
     * <p>
     *  将字段的值设置为指定对象上的{@code short}。
     * 这个方法相当于{@code set(obj,sObj)},其中{@code sObj}是一个{@code Short}对象和{@code sObj.shortValue()== s}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param s   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setShort(Object obj, short s)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setShort(obj, s);
    }

    /**
     * Sets the value of a field as an {@code int} on the specified object.
     * This method is equivalent to
     * {@code set(obj, iObj)},
     * where {@code iObj} is a {@code Integer} object and
     * {@code iObj.intValue() == i}.
     *
     * <p>
     *  将字段的值设置为指定对象上的{@code int}。
     * 这个方法等价于{@code set(obj,iObj)},其中{@code iObj}是一个{@code Integer}对象和{@code iObj.intValue()== i}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param i   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setInt(Object obj, int i)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setInt(obj, i);
    }

    /**
     * Sets the value of a field as a {@code long} on the specified object.
     * This method is equivalent to
     * {@code set(obj, lObj)},
     * where {@code lObj} is a {@code Long} object and
     * {@code lObj.longValue() == l}.
     *
     * <p>
     *  将字段的值设置为指定对象上的{@code long}。
     * 该方法等同于{@code set(obj,lObj)},其中{@code lObj}是{@code Long}对象和{@code lObj.longValue()== l}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param l   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setLong(Object obj, long l)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setLong(obj, l);
    }

    /**
     * Sets the value of a field as a {@code float} on the specified object.
     * This method is equivalent to
     * {@code set(obj, fObj)},
     * where {@code fObj} is a {@code Float} object and
     * {@code fObj.floatValue() == f}.
     *
     * <p>
     *  将字段的值设置为指定对象上的{@code float}。
     * 这个方法等价于{@code set(obj,fObj)},其中{@code fObj}是一个{@code Float}对象和{@code fObj.floatValue()== f}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param f   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setFloat(Object obj, float f)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setFloat(obj, f);
    }

    /**
     * Sets the value of a field as a {@code double} on the specified object.
     * This method is equivalent to
     * {@code set(obj, dObj)},
     * where {@code dObj} is a {@code Double} object and
     * {@code dObj.doubleValue() == d}.
     *
     * <p>
     * 将字段的值设置为指定对象上的{@code double}。
     * 这个方法等同于{@code set(obj,dObj)},其中{@code dObj}是一个{@code Double}对象和{@code dObj.doubleValue()== d}。
     * 
     * 
     * @param obj the object whose field should be modified
     * @param d   the new value for the field of {@code obj}
     * being modified
     *
     * @exception IllegalAccessException    if this {@code Field} object
     *              is enforcing Java language access control and the underlying
     *              field is either inaccessible or final.
     * @exception IllegalArgumentException  if the specified object is not an
     *              instance of the class or interface declaring the underlying
     *              field (or a subclass or implementor thereof),
     *              or if an unwrapping conversion fails.
     * @exception NullPointerException      if the specified object is null
     *              and the field is an instance field.
     * @exception ExceptionInInitializerError if the initialization provoked
     *              by this method fails.
     * @see       Field#set
     */
    @CallerSensitive
    public void setDouble(Object obj, double d)
        throws IllegalArgumentException, IllegalAccessException
    {
        if (!override) {
            if (!Reflection.quickCheckMemberAccess(clazz, modifiers)) {
                Class<?> caller = Reflection.getCallerClass();
                checkAccess(caller, clazz, obj, modifiers);
            }
        }
        getFieldAccessor(obj).setDouble(obj, d);
    }

    // security check is done before calling this method
    private FieldAccessor getFieldAccessor(Object obj)
        throws IllegalAccessException
    {
        boolean ov = override;
        FieldAccessor a = (ov) ? overrideFieldAccessor : fieldAccessor;
        return (a != null) ? a : acquireFieldAccessor(ov);
    }

    // NOTE that there is no synchronization used here. It is correct
    // (though not efficient) to generate more than one FieldAccessor
    // for a given Field. However, avoiding synchronization will
    // probably make the implementation more scalable.
    private FieldAccessor acquireFieldAccessor(boolean overrideFinalCheck) {
        // First check to see if one has been created yet, and take it
        // if so
        FieldAccessor tmp = null;
        if (root != null) tmp = root.getFieldAccessor(overrideFinalCheck);
        if (tmp != null) {
            if (overrideFinalCheck)
                overrideFieldAccessor = tmp;
            else
                fieldAccessor = tmp;
        } else {
            // Otherwise fabricate one and propagate it up to the root
            tmp = reflectionFactory.newFieldAccessor(this, overrideFinalCheck);
            setFieldAccessor(tmp, overrideFinalCheck);
        }

        return tmp;
    }

    // Returns FieldAccessor for this Field object, not looking up
    // the chain to the root
    private FieldAccessor getFieldAccessor(boolean overrideFinalCheck) {
        return (overrideFinalCheck)? overrideFieldAccessor : fieldAccessor;
    }

    // Sets the FieldAccessor for this Field object and
    // (recursively) its root
    private void setFieldAccessor(FieldAccessor accessor, boolean overrideFinalCheck) {
        if (overrideFinalCheck)
            overrideFieldAccessor = accessor;
        else
            fieldAccessor = accessor;
        // Propagate up
        if (root != null) {
            root.setFieldAccessor(accessor, overrideFinalCheck);
        }
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.5
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(declaredAnnotations().get(annotationClass));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);

        return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), annotationClass);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public Annotation[] getDeclaredAnnotations()  {
        return AnnotationParser.toArray(declaredAnnotations());
    }

    private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;

    private synchronized  Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
        if (declaredAnnotations == null) {
            Field root = this.root;
            if (root != null) {
                declaredAnnotations = root.declaredAnnotations();
            } else {
                declaredAnnotations = AnnotationParser.parseAnnotations(
                        annotations,
                        sun.misc.SharedSecrets.getJavaLangAccess().getConstantPool(getDeclaringClass()),
                        getDeclaringClass());
            }
        }
        return declaredAnnotations;
    }

    private native byte[] getTypeAnnotationBytes0();

    /**
     * Returns an AnnotatedType object that represents the use of a type to specify
     * the declared type of the field represented by this Field.
     * <p>
     *  返回一个AnnotatedType对象,它表示使用类型来指定此字段表示的字段的声明类型。
     * 
     * @return an object representing the declared type of the field
     * represented by this Field
     *
     * @since 1.8
     */
    public AnnotatedType getAnnotatedType() {
        return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(),
                                                       sun.misc.SharedSecrets.getJavaLangAccess().
                                                           getConstantPool(getDeclaringClass()),
                                                       this,
                                                       getDeclaringClass(),
                                                       getGenericType(),
                                                       TypeAnnotation.TypeAnnotationTarget.FIELD);
}
}
