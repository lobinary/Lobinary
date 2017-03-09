/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javadoc;


/**
 * Represents a java class or interface and provides access to
 * information about the class, the class's comment and tags, and the
 * members of the class.  A ClassDoc only exists if it was
 * processed in this run of javadoc.  References to classes
 * which may or may not have been processed in this run are
 * referred to using Type (which can be converted to ClassDoc,
 * if possible).
 *
 * <p>
 *  表示一个java类或接口,并提供对类的信息,类的注释和标签以及类的成员的访问。仅当在此javadoc运行中处理时,ClassDoc才存在。
 * 对在本次运行中可能已经处理或可能未处理的类的引用使用类型(如果可能,可以转换为ClassDoc)。
 * 
 * 
 * @see Type
 *
 * @since 1.2
 * @author Kaiyang Liu (original)
 * @author Robert Field (rewrite)
 */
public interface ClassDoc extends ProgramElementDoc, Type {

    /**
     * Return true if this class is abstract.  Return true
     * for all interfaces.
     * <p>
     *  如果此类是抽象的,则返回true。对所有接口返回true。
     * 
     */
    boolean isAbstract();

    /**
     * Return true if this class implements or interface extends
     * <code>java.io.Serializable</code>.
     *
     * Since <code>java.io.Externalizable</code> extends
     * <code>java.io.Serializable</code>,
     * Externalizable objects are also Serializable.
     * <p>
     *  如果此类实现或接口扩展<code> java.io.Serializable </code>,则返回true。
     * 
     *  由于<code> java.io.Externalizable </code> extends <code> java.io.Serializable </code>,Externalizable对象
     * 也是Serializable。
     * 
     */
    boolean isSerializable();

    /**
     * Return true if this class implements or interface extends
     * <code>java.io.Externalizable</code>.
     * <p>
     *  如果此类实现或接口扩展<code> java.io.Externalizable </code>,则返回true。
     * 
     */
    boolean isExternalizable();

    /**
     * Return the serialization methods for this class or
     * interface.
     *
     * <p>
     *  返回此类或接口的序列化方法。
     * 
     * 
     * @return an array of MethodDoc objects that represents
     *         the serialization methods for this class or interface.
     */
    MethodDoc[] serializationMethods();

    /**
     * Return the Serializable fields of this class or interface.
     * <p>
     * Return either a list of default fields documented by
     * <code>serial</code> tag<br>
     * or return a single <code>FieldDoc</code> for
     * <code>serialPersistentField</code> member.
     * There should be a <code>serialField</code> tag for
     * each Serializable field defined by an <code>ObjectStreamField</code>
     * array component of <code>serialPersistentField</code>.
     *
     * <p>
     *  返回此类或接口的Serializable字段。
     * <p>
     *  返回由<code> serial </code>标记<br>记录的默认字段列表,或者为<code> serialPersistentField </code>成员返回单个<code> FieldDoc
     *  </code>。
     * 对于<code> serialPersistentField </code>的<code> ObjectStreamField </code>数组组件定义的每个可序列化字段,应该有一个<code> se
     * rialField </code>。
     * 
     * 
     * @return an array of <code>FieldDoc</code> objects for the Serializable
     *         fields of this class or interface.
     *
     * @see #definesSerializableFields()
     * @see SerialFieldTag
     */
    FieldDoc[] serializableFields();

    /**
     *  Return true if Serializable fields are explicitly defined with
     *  the special class member <code>serialPersistentFields</code>.
     *
     * <p>
     * 如果可序列化字段使用特殊类成员<code> serialPersistentFields </code>显式定义,则返回true。
     * 
     * 
     * @see #serializableFields()
     * @see SerialFieldTag
     */
    boolean definesSerializableFields();

    /**
     * Return the superclass of this class.  Return null if this is an
     * interface.
     *
     * <p> <i>This method cannot accommodate certain generic type constructs.
     * The <code>superclassType</code> method should be used instead.</i>
     *
     * <p>
     *  返回此类的超类。如果这是一个接口,则返回null。
     * 
     *  <p> <i>此方法无法容纳某些通用类型结构。应该使用<code> superclassType </code>方法。</i>
     * 
     * 
     * @return the ClassDoc for the superclass of this class, null if
     *         there is no superclass.
     * @see #superclassType
     */
    ClassDoc superclass();

    /**
     * Return the superclass of this class.  Return null if this is an
     * interface.  A superclass is represented by either a
     * <code>ClassDoc</code> or a <code>ParametrizedType</code>.
     *
     * <p>
     *  返回此类的超类。如果这是一个接口,则返回null。超类由<code> ClassDoc </code>或<code> ParametrizedType </code>表示。
     * 
     * 
     * @return the superclass of this class, or null if there is no superclass.
     * @since 1.5
     */
    Type superclassType();

    /**
     * Test whether this class is a subclass of the specified class.
     * If this is an interface, return false for all classes except
     * <code>java.lang.Object</code> (we must keep this unexpected
     * behavior for compatibility reasons).
     *
     * <p>
     *  测试这个类是否是指定类的子类。如果这是一个接口,对除<code> java.lang.Object </code>之外的所有类返回false(出于兼容性原因,我们必须保持此意外行为)。
     * 
     * 
     * @param cd the candidate superclass.
     * @return true if cd is a superclass of this class.
     */
    boolean subclassOf(ClassDoc cd);

    /**
     * Return interfaces implemented by this class or interfaces extended
     * by this interface. Includes only directly-declared interfaces, not
     * inherited interfaces.
     * Return an empty array if there are no interfaces.
     *
     * <p> <i>This method cannot accommodate certain generic type constructs.
     * The <code>interfaceTypes</code> method should be used instead.</i>
     *
     * <p>
     *  返回由此类实现的接口或由此接口扩展的接口。仅包含直接声明的接口,而不包括继承的接口。如果没有接口,则返回一个空数组。
     * 
     *  <p> <i>此方法无法容纳某些通用类型结构。应该使用<code> interfaceTypes </code>方法。</i>
     * 
     * 
     * @return an array of ClassDoc objects representing the interfaces.
     * @see #interfaceTypes
     */
    ClassDoc[] interfaces();

    /**
     * Return interfaces implemented by this class or interfaces extended
     * by this interface. Includes only directly-declared interfaces, not
     * inherited interfaces.
     * Return an empty array if there are no interfaces.
     *
     * <p>
     *  返回由此类实现的接口或由此接口扩展的接口。仅包含直接声明的接口,而不包括继承的接口。如果没有接口,则返回一个空数组。
     * 
     * 
     * @return an array of interfaces, each represented by a
     *         <code>ClassDoc</code> or a <code>ParametrizedType</code>.
     * @since 1.5
     */
    Type[] interfaceTypes();

    /**
     * Return the formal type parameters of this class or interface.
     * Return an empty array if there are none.
     *
     * <p>
     *  返回此类或接口的形式类型参数。如果没有,返回一个空数组。
     * 
     * 
     * @return the formal type parameters of this class or interface.
     * @since 1.5
     */
    TypeVariable[] typeParameters();

    /**
     * Return the type parameter tags of this class or interface.
     * Return an empty array if there are none.
     *
     * <p>
     *  返回此类或接口的类型参数标签。如果没有,返回一个空数组。
     * 
     * 
     * @return the type parameter tags of this class or interface.
     * @since 1.5
     */
    ParamTag[] typeParamTags();

    /**
     * Return
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">included</a>
     * fields in this class or interface.
     * Excludes enum constants if this is an enum type.
     *
     * <p>
     * 返回此类或界面中的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">包含的</a>字段。
     * 如果这是枚举类型,则排除枚举常量。
     * 
     * 
     * @return an array of FieldDoc objects representing the included
     *         fields in this class or interface.
     */
    FieldDoc[] fields();

    /**
     * Return fields in this class or interface, filtered to the specified
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">access
     * modifier option</a>.
     * Excludes enum constants if this is an enum type.
     *
     * <p>
     *  返回此类或界面中的字段,并过滤到指定的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">访问权限修饰符选项</a>。
     * 如果这是枚举类型,则排除枚举常量。
     * 
     * 
     * @param filter Specify true to filter according to the specified access
     *               modifier option.
     *               Specify false to include all fields regardless of
     *               access modifier option.
     * @return       an array of FieldDoc objects representing the included
     *               fields in this class or interface.
     */
    FieldDoc[] fields(boolean filter);

    /**
     * Return the enum constants if this is an enum type.
     * Return an empty array if there are no enum constants, or if
     * this is not an enum type.
     *
     * <p>
     *  如果这是枚举类型,则返回枚举常量。如果没有枚举常量,或者这不是枚举类型,则返回一个空数组。
     * 
     * 
     * @return the enum constants if this is an enum type.
     */
    FieldDoc[] enumConstants();

    /**
     * Return
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">included</a>
     * methods in this class or interface.
     * Same as <code>methods(true)</code>.
     *
     * <p>
     *  返回此类或界面中的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">包含的</a>方法。
     * 与<code>方法(true)</code>相同。
     * 
     * 
     * @return an array of MethodDoc objects representing the included
     *         methods in this class or interface.  Does not include
     *         constructors or annotation type elements.
     */
    MethodDoc[] methods();

    /**
     * Return methods in this class or interface, filtered to the specified
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">access
     * modifier option</a>.  Does not include constructors or annotation
     *          type elements.
     *
     * <p>
     *  返回此类或界面中的过滤到指定的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">访问权限修饰符选项</a>的方法。
     * 不包括构造函数或注释类型元素。
     * 
     * 
     * @param filter Specify true to filter according to the specified access
     *               modifier option.
     *               Specify false to include all methods regardless of
     *               access modifier option.
     * @return       an array of MethodDoc objects representing the included
     *               methods in this class or interface.
     */
    MethodDoc[] methods(boolean filter);

    /**
     * Return
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">included</a>
     * constructors in this class.  An array containing the default
     * no-arg constructor is returned if no other constructors exist.
     * Return empty array if this is an interface.
     *
     * <p>
     *  返回此类中的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">包含的</a>构造函数。
     * 如果没有其他构造函数存在,则返回包含默认无参构造函数的数组。如果这是一个接口,返回空数组。
     * 
     * 
     * @return an array of ConstructorDoc objects representing the included
     *         constructors in this class.
     */
    ConstructorDoc[] constructors();

    /**
     * Return constructors in this class, filtered to the specified
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">access
     * modifier option</a>.  Return an array containing the default
     * no-arg constructor if no other constructors exist.
     *
     * <p>
     *  返回此类中的构造函数,并过滤到指定的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">访问权限修饰符选项</a>。
     * 如果没有其他构造函数存在,返回一个包含默认no-arg构造函数的数组。
     * 
     * 
     * @param filter Specify true to filter according to the specified access
     *               modifier option.
     *               Specify false to include all constructors regardless of
     *               access modifier option.
     * @return       an array of ConstructorDoc objects representing the included
     *               constructors in this class.
     */
    ConstructorDoc[] constructors(boolean filter);


    /**
     * Return
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">included</a>
     * nested classes and interfaces within this class or interface.
     * This includes both static and non-static nested classes.
     * (This method should have been named <code>nestedClasses()</code>,
     * as inner classes are technically non-static.)  Anonymous and local classes
     * or interfaces are not included.
     *
     * <p>
     * 返回此类或界面中的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">包含的</a>嵌套类和接口。
     * 这包括静态和非静态嵌套类。 (这个方法应该被命名为<code> nestedClasses()</code>,因为内部类在技术上是非静态的。)不包括​​匿名和本地类或接口。
     * 
     * 
     * @return an array of ClassDoc objects representing the included classes
     *         and interfaces defined in this class or interface.
     */
    ClassDoc[] innerClasses();

    /**
     * Return nested classes and interfaces within this class or interface
     * filtered to the specified
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">access
     * modifier option</a>.
     * This includes both static and non-static nested classes.
     * Anonymous and local classes are not included.
     *
     * <p>
     *  返回此类或接口中的嵌套类和接口,并将其过滤到指定的<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">访问权限修饰符选
     * 项</a>。
     * 这包括静态和非静态嵌套类。不包括匿名和本地类。
     * 
     * 
     * @param filter Specify true to filter according to the specified access
     *               modifier option.
     *               Specify false to include all nested classes regardless of
     *               access modifier option.
     * @return       a filtered array of ClassDoc objects representing the included
     *               classes and interfaces defined in this class or interface.
     */
    ClassDoc[] innerClasses(boolean filter);

    /**
     * Find the specified class or interface within the context of this class doc.
     * Search order: 1) qualified name, 2) nested in this class or interface,
     * 3) in this package, 4) in the class imports, 5) in the package imports.
     * Return the ClassDoc if found, null if not found.
     * <p>
     *  在此类doc的上下文中查找指定的类或接口。搜索顺序：1)限定名,2)嵌套在此类或接口中,3)在这个包中,4)在类中导入,5)在包导入。返回ClassDoc如果找到,null如果没有找到。
     * 
     */
    ClassDoc findClass(String className);

    /**
     * Get the list of classes and interfaces declared as imported.
     * These are called "single-type-import declarations" in
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  获取声明为导入的类和接口的列表。这些在<cite> Java&trade;中称为"单一类型的导入声明"语言规范</cite>。
     * 
     * 
     * @return an array of ClassDoc representing the imported classes.
     *
     * @deprecated  Import declarations are implementation details that
     *          should not be exposed here.  In addition, not all imported
     *          classes are imported through single-type-import declarations.
     */
    @Deprecated
    ClassDoc[] importedClasses();

    /**
     * Get the list of packages declared as imported.
     * These are called "type-import-on-demand declarations" in
     * <cite>The Java&trade; Language Specification</cite>.
     *
     * <p>
     *  获取声明为导入的包的列表。这些在<cite> Java&trade;中称为"type-import-on-demand declarations"语言规范</cite>。
     * 
     * @return an array of PackageDoc representing the imported packages.
     *
     * @deprecated  Import declarations are implementation details that
     *          should not be exposed here.  In addition, this method's
     *          return type does not allow for all type-import-on-demand
     *          declarations to be returned.
     */
    @Deprecated
    PackageDoc[] importedPackages();
}
