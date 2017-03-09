/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2008, Oracle and/or its affiliates. All rights reserved.
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

import javax.management.openmbean.*;
import com.sun.jmx.mbeanserver.MXBeanMapping;
import com.sun.jmx.mbeanserver.DefaultMXBeanMappingFactory;
import java.lang.reflect.Type;

/**
 * <p>Defines how types are mapped for a given MXBean or set of MXBeans.
 * An {@code MXBeanMappingFactory} can be specified either through the
 * {@link MXBeanMappingFactoryClass} annotation, or through the
 * {@link javax.management.JMX.MBeanOptions JMX.MBeanOptions} argument to a
 * {@link javax.management.StandardMBean StandardMBean} constructor or MXBean
 * proxy.</p>
 *
 * <p>An {@code MXBeanMappingFactory} must return an {@code MXBeanMapping}
 * for any Java type that appears in the MXBeans that the factory is being
 * used for.  Usually it does that by handling any custom types, and
 * forwarding everything else to the {@linkplain #DEFAULT default mapping
 * factory}.</p>
 *
 * <p>Consider the {@code MyLinkedList} example from the {@link MXBeanMapping}
 * documentation.  If we are unable to change the {@code MyLinkedList} class
 * to add an {@link MXBeanMappingClass} annotation, we could achieve the same
 * effect by defining {@code MyLinkedListMappingFactory} as follows:</p>
 *
 * <pre>
 * public class MyLinkedListMappingFactory extends MXBeanMappingFactory {
 *     public MyLinkedListMappingFactory() {}
 *
 *     public MXBeanMapping mappingForType(Type t, MXBeanMappingFactory f)
 *     throws OpenDataException {
 *         if (t == MyLinkedList.class)
 *             return new MyLinkedListMapping(t);
 *         else
 *             return MXBeanMappingFactory.DEFAULT.mappingForType(t, f);
 *     }
 * }
 * </pre>
 *
 * <p>The mapping factory handles only the {@code MyLinkedList} class.
 * Every other type is forwarded to the default mapping factory.
 * This includes types such as {@code MyLinkedList[]} and
 * {@code List<MyLinkedList>}; the default mapping factory will recursively
 * invoke {@code MyLinkedListMappingFactory} to map the contained
 * {@code MyLinkedList} type.</p>
 *
 * <p>Once we have defined {@code MyLinkedListMappingFactory}, we can use
 * it in an MXBean interface like this:</p>
 *
 * <pre>
 * {@literal @MXBeanMappingFactoryClass}(MyLinkedListMappingFactory.class)
 * public interface SomethingMXBean {
 *     public MyLinkedList getSomething();
 * }
 * </pre>
 *
 * <p>Alternatively we can annotate the package that {@code SomethingMXBean}
 * appears in, or we can supply the factory to a {@link
 * javax.management.StandardMBean StandardMBean} constructor or MXBean
 * proxy.</p>
 *
 * <p>
 *  <p>定义为给定MXBean或MXBean集合映射类型的方式。
 *  {@code MXBeanMappingFactory}可以通过{@link MXBeanMappingFactoryClass}注释或通过{@link javax.management.StandardMBean StandardMBean}
 * 构造函数的{@link javax.management.JMX.MBeanOptions JMX.MBeanOptions}参数指定或MXBean代理。
 *  <p>定义为给定MXBean或MXBean集合映射类型的方式。</p>。
 * 
 *  <p> {@code MXBeanMappingFactory}必须针对工厂正在使用的MXBeans中显示的任何Java类型返回一个{@code MXBeanMapping}。
 * 通常,它通过处理任何自定义类型,并将其他一切转发到{@linkplain #DEFAULT默认映射工厂}。</p>。
 * 
 *  <p>请参阅{@link MXBeanMapping}文档中的{@code MyLinkedList}示例。
 * 如果我们无法更改{@code MyLinkedList}类添加一个{@link MXBeanMappingClass}注释,我们可以通过如下定义{@code MyLinkedListMappingFactory}
 * 来实现相同的效果：</p>。
 *  <p>请参阅{@link MXBeanMapping}文档中的{@code MyLinkedList}示例。
 * 
 * <pre>
 *  public class MyLinkedListMappingFactory extends MXBeanMappingFactory {public MyLinkedListMappingFactory(){}
 * 。
 * 
 *  public MXBeanMapping mappingForType(Type t,MXBeanMappingFactory f)throws OpenDataException {if(t == MyLinkedList.class)return new MyLinkedListMapping(t); else return MXBeanMappingFactory.DEFAULT.mappingForType(t,f); }
 * }。
 * </pre>
 * 
 * <p>映射工厂只处理{@code MyLinkedList}类。每个其他类型都转发到默认映射工厂。
 * 这包括类型,例如{@code MyLinkedList []}和{@code List <MyLinkedList>};默认映射工厂将递归调用{@code MyLinkedListMappingFactory}
 * 
 * @see <a href="../MXBean.html#custom">MXBean specification, section
 * "Custom MXBean type mappings"</a>
 */
public abstract class MXBeanMappingFactory {
    /**
     * <p>Construct an instance of this class.</p>
     * <p>
     * 来映射所包含的{@code MyLinkedList}类型。
     * <p>映射工厂只处理{@code MyLinkedList}类。每个其他类型都转发到默认映射工厂。</p>。
     * 
     *  <P>一旦我们定义{@code MyLinkedListMappingFactory},我们可以在这样一个MXBean接口使用它：</P>
     * 
     * <pre>
     *  {@literal @MXBeanMappingFactoryClass}(MyLinkedListMappingFactory.class)公共接口{的Something公共MyLinkedList getSomething(); }
     * }。
     * </pre>
     * 
     *  <P>另外,我们可以注释是@code {}的Something出现在包中,或者我们可以提供工厂到{@link javax.management.StandardMBean StandardMBean}
     * 构造函数或MXBean代理。
     */
    protected MXBeanMappingFactory() {}

    /**
     * <p>Mapping factory that applies the default rules for MXBean
     * mappings, as described in the <a
     * href="../MXBean.html#MXBean-spec">MXBean specification</a>.</p>
     * <p>
     * </P>。
     * 
     */
    public static final MXBeanMappingFactory DEFAULT =
            new DefaultMXBeanMappingFactory();

    /**
     * <p>Return the mapping for the given Java type.  Typically, a
     * mapping factory will return mappings for types it handles, and
     * forward other types to another mapping factory, most often
     * the {@linkplain #DEFAULT default one}.</p>
     * <p>
     *  <p>构造此类的实例。</p>
     * 
     * 
     * @param t the Java type to be mapped.
     * @param f the original mapping factory that was consulted to do
     * the mapping.  A mapping factory should pass this parameter intact
     * if it forwards a type to another mapping factory.  In the example,
     * this is how {@code MyLinkedListMappingFactory} works for types
     * like {@code MyLinkedList[]} and {@code List<MyLinkedList>}.
     * @return the mapping for the given type.
     * @throws OpenDataException if this type cannot be mapped.  This
     * exception is appropriate if the factory is supposed to handle
     * all types of this sort (for example, all linked lists), but
     * cannot handle this particular type.
     */
    public abstract MXBeanMapping mappingForType(Type t, MXBeanMappingFactory f)
    throws OpenDataException;
}
