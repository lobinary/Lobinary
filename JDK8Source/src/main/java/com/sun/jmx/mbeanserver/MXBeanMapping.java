/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2012, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.jmx.mbeanserver;

import java.io.InvalidObjectException;
import java.lang.reflect.Type;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;

/**
 * <p>A custom mapping between Java types and Open types for use in MXBeans.
 * To define such a mapping, subclass this class and define at least the
 * {@link #fromOpenValue fromOpenValue} and {@link #toOpenValue toOpenValue}
 * methods, and optionally the {@link #checkReconstructible} method.
 * Then either use an {@link MXBeanMappingClass} annotation on your custom
 * Java types, or include this MXBeanMapping in an
 * {@link MXBeanMappingFactory}.</p>
 *
 * <p>For example, suppose we have a class {@code MyLinkedList}, which looks
 * like this:</p>
 *
 * <pre>
 * public class MyLinkedList {
 *     public MyLinkedList(String name, MyLinkedList next) {...}
 *     public String getName() {...}
 *     public MyLinkedList getNext() {...}
 * }
 * </pre>
 *
 * <p>This is not a valid type for MXBeans, because it contains a
 * self-referential property "next" defined by the {@code getNext()}
 * method.  MXBeans do not support recursive types.  So we would like
 * to specify a mapping for {@code MyLinkedList} explicitly. When an
 * MXBean interface contains {@code MyLinkedList}, that will be mapped
 * into a {@code String[]}, which is a valid Open Type.</p>
 *
 * <p>To define this mapping, we first subclass {@code MXBeanMapping}:</p>
 *
 * <pre>
 * public class MyLinkedListMapping extends MXBeanMapping {
 *     public MyLinkedListMapping(Type type) throws OpenDataException {
 *         super(MyLinkedList.class, ArrayType.getArrayType(SimpleType.STRING));
 *         if (type != MyLinkedList.class)
 *             throw new OpenDataException("Mapping only valid for MyLinkedList");
 *     }
 *
 *     {@literal @Override}
 *     public Object fromOpenValue(Object openValue) throws InvalidObjectException {
 *         String[] array = (String[]) openValue;
 *         MyLinkedList list = null;
 *         for (int i = array.length - 1; i &gt;= 0; i--)
 *             list = new MyLinkedList(array[i], list);
 *         return list;
 *     }
 *
 *     {@literal @Override}
 *     public Object toOpenValue(Object javaValue) throws OpenDataException {
 *         ArrayList&lt;String&gt; array = new ArrayList&lt;String&gt;();
 *         for (MyLinkedList list = (MyLinkedList) javaValue; list != null;
 *              list = list.getNext())
 *             array.add(list.getName());
 *         return array.toArray(new String[0]);
 *     }
 * }
 * </pre>
 *
 * <p>The call to the superclass constructor specifies what the
 * original Java type is ({@code MyLinkedList.class}) and what Open
 * Type it is mapped to ({@code
 * ArrayType.getArrayType(SimpleType.STRING)}). The {@code
 * fromOpenValue} method says how we go from the Open Type ({@code
 * String[]}) to the Java type ({@code MyLinkedList}), and the {@code
 * toOpenValue} method says how we go from the Java type to the Open
 * Type.</p>
 *
 * <p>With this mapping defined, we can annotate the {@code MyLinkedList}
 * class appropriately:</p>
 *
 * <pre>
 * {@literal @MXBeanMappingClass}(MyLinkedListMapping.class)
 * public class MyLinkedList {...}
 * </pre>
 *
 * <p>Now we can use {@code MyLinkedList} in an MXBean interface and it
 * will work.</p>
 *
 * <p>If we are unable to modify the {@code MyLinkedList} class,
 * we can define an {@link MXBeanMappingFactory}.  See the documentation
 * of that class for further details.</p>
 *
 * <p>
 *  <p>用于MXBeans的Java类型和Open类型之间的自定义映射。
 * 要定义此类映射,请对此类进行子类化,并至少定义{@link #fromOpenValue fromOpenValue}和{@link #toOpenValue toOpenValue}方法,以及可选的{@link #checkReconstructible}
 * 方法。
 *  <p>用于MXBeans的Java类型和Open类型之间的自定义映射。
 * 然后对自定义Java类型使用{@link MXBeanMappingClass}注释,或将此MXBeanMapping包含在{@link MXBeanMappingFactory}中。</p>。
 * 
 *  <p>例如,假设我们有一个类{@code MyLinkedList},它看起来像这样：</p>
 * 
 * <pre>
 *  public String MyLinkedList(public MyLinkedList(String name,MyLinkedList next){...} public String get
 * Name(){...} public MyLinkedList getNext(){...}。
 * </pre>
 * 
 *  <p>这不是MXBeans的有效类型,因为它包含由{@code getNext()}方法定义的自引用属性"next"。 MXBeans不支持递归类型。
 * 因此,我们要明确指定{@code MyLinkedList}的映射。
 * 当MXBean接口包含{@code MyLinkedList}时,将映射到{@code String []},这是一个有效的打开类型。</p>。
 * 
 *  <p>要定义此映射,我们首先将{@code MXBeanMapping}子类化：</p>
 * 
 * <pre>
 * public class MyLinkedListMapping extends MXBeanMapping {public MyLinkedListMapping(Type type)throws OpenDataException {super(MyLinkedList.class,ArrayType.getArrayType(SimpleType.STRING)); if(type！= MyLinkedList.class)throw new OpenDataException("Mapping only valid for MyLinkedList"); }
 * }。
 * 
 *  {@literal @Override} public Object fromOpenValue(Object openValue)throws InvalidObjectException {String [] array =(String [])openValue; MyLinkedList list = null; for(int i = array.length-1; i&gt; = 0; i--)list = new MyLinkedList(array [i],list);返回列表; }
 * }。
 * 
 *  {@literal @Override} public Object toOpenValue(Object javaValue)throws OpenDataException {ArrayList&lt; String&gt; array = new ArrayList&lt; String&gt;(); for(MyLinkedList list =(MyLinkedList)javaValue; list！= null; list = list.getNext())array.add(list.getName()); return array.toArray(new String [0]); }
 * }。
 * </pre>
 * 
 *  <p>对超类构造函数的调用指定了原始Java类型({@code MyLinkedList.class})以及它映射到的Open Type({@code ArrayType.getArrayType(SimpleType.STRING)}
 * )。
 *  {@code fromOpenValue}方法说明我们如何从开放类型({@code String []})到Java类型({@code MyLinkedList}),而{@code toOpenValue}
 * 方法说明我们如何从Java类型转换为开放类型。
 * </p>。
 * 
 * @see <a href="../MXBean.html#custom">MXBean specification, section
 * "Custom MXBean type mappings"</a>
 */
public abstract class MXBeanMapping {
    private final Type javaType;
    private final OpenType<?> openType;
    private final Class<?> openClass;

    /**
     * <p>Construct a mapping between the given Java type and the given
     * Open Type.</p>
     *
     * <p>
     * 
     *  <p>通过定义此映射,我们可以适当地注释{@code MyLinkedList}类：</p>
     * 
     * <pre>
     *  {@literal @MXBeanMappingClass}(MyLinkedListMapping.class)public class MyLinkedList {...}
     * </pre>
     * 
     * <p>现在,我们可以在MXBean接口中使用{@code MyLinkedList},它会工作。</p>
     * 
     *  <p>如果我们无法修改{@code MyLinkedList}类,我们可以定义一个{@link MXBeanMappingFactory}。有关详细信息,请参阅该类的文档。</p>
     * 
     * 
     * @param javaType the Java type (for example, {@code MyLinkedList}).
     * @param openType the Open Type (for example, {@code
     * ArrayType.getArrayType(SimpleType.STRING)})
     *
     * @throws NullPointerException if either argument is null.
     */
    protected MXBeanMapping(Type javaType, OpenType<?> openType) {
        if (javaType == null || openType == null)
            throw new NullPointerException("Null argument");
        this.javaType = javaType;
        this.openType = openType;
        this.openClass = makeOpenClass(javaType, openType);
    }

    /**
     * <p>The Java type that was supplied to the constructor.</p>
     * <p>
     *  <p>在给定的Java类型和给定的打开类型之间构造映射。</p>
     * 
     * 
     * @return the Java type that was supplied to the constructor.
     */
    public final Type getJavaType() {
        return javaType;
    }

    /**
     * <p>The Open Type that was supplied to the constructor.</p>
     * <p>
     *  <p>提供给构造函数的Java类型。</p>
     * 
     * 
     * @return the Open Type that was supplied to the constructor.
     */
    public final OpenType<?> getOpenType() {
        return openType;
    }

    /**
     * <p>The Java class that corresponds to instances of the
     * {@linkplain #getOpenType() Open Type} for this mapping.</p>
     * <p>
     *  <p>提供给构造函数的打开类型。</p>
     * 
     * 
     * @return the Java class that corresponds to instances of the
     * Open Type for this mapping.
     * @see OpenType#getClassName
     */
    public final Class<?> getOpenClass() {
        return openClass;
    }

    private static Class<?> makeOpenClass(Type javaType, OpenType<?> openType) {
        if (javaType instanceof Class<?> && ((Class<?>) javaType).isPrimitive())
            return (Class<?>) javaType;
        try {
            String className = openType.getClassName();
            return Class.forName(className, false, MXBeanMapping.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);  // should not happen
        }
    }

    /**
     * <p>Convert an instance of the Open Type into the Java type.
     * <p>
     *  <p>对应于此映射的{@linkplain #getOpenType()Open Type}实例的Java类。</p>
     * 
     * 
     * @param openValue the value to be converted.
     * @return the converted value.
     * @throws InvalidObjectException if the value cannot be converted.
     */
    public abstract Object fromOpenValue(Object openValue)
    throws InvalidObjectException;

    /**
     * <p>Convert an instance of the Java type into the Open Type.
     * <p>
     *  <p>将开放类型的实例转换为Java类型。
     * 
     * 
     * @param javaValue the value to be converted.
     * @return the converted value.
     * @throws OpenDataException if the value cannot be converted.
     */
    public abstract Object toOpenValue(Object javaValue)
    throws OpenDataException;


    /**
     * <p>Throw an appropriate InvalidObjectException if we will not
     * be able to convert back from the open data to the original Java
     * object.  The {@link #fromOpenValue fromOpenValue} throws an
     * exception if a given open data value cannot be converted.  This
     * method throws an exception if <em>no</em> open data values can
     * be converted.  The default implementation of this method never
     * throws an exception.  Subclasses can override it as
     * appropriate.</p>
     * <p>
     *  <p>将Java类型的实例转换为打开类型。
     * 
     * 
     * @throws InvalidObjectException if {@code fromOpenValue} will throw
     * an exception no matter what its argument is.
     */
    public void checkReconstructible() throws InvalidObjectException {}
}
