/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import sun.reflect.misc.ReflectUtil;

/**
 * Static methods from the JMX API.  There are no instances of this class.
 *
 * <p>
 *  来自JMX API的静态方法。没有这个类的实例。
 * 
 * 
 * @since 1.6
 */
public class JMX {
    /* Code within this package can prove that by providing this instance of
     * this class.
     * <p>
     *  这个班。
     * 
     */
    static final JMX proof = new JMX();

    private JMX() {}

    /**
     * The name of the <a href="Descriptor.html#defaultValue">{@code
     * defaultValue}</a> field.
     * <p>
     *  <a href="Descriptor.html#defaultValue"> {@code defaultValue} </a>字段的名称。
     * 
     */
    public static final String DEFAULT_VALUE_FIELD = "defaultValue";

    /**
     * The name of the <a href="Descriptor.html#immutableInfo">{@code
     * immutableInfo}</a> field.
     * <p>
     *  <a href="Descriptor.html#immutableInfo"> {@code immutableInfo} </a>字段的名称。
     * 
     */
    public static final String IMMUTABLE_INFO_FIELD = "immutableInfo";

    /**
     * The name of the <a href="Descriptor.html#interfaceClassName">{@code
     * interfaceClassName}</a> field.
     * <p>
     *  <a href="Descriptor.html#interfaceClassName"> {@code interfaceClassName} </a>字段的名称。
     * 
     */
    public static final String INTERFACE_CLASS_NAME_FIELD = "interfaceClassName";

    /**
     * The name of the <a href="Descriptor.html#legalValues">{@code
     * legalValues}</a> field.
     * <p>
     *  <a href="Descriptor.html#legalValues"> {@code legalValues} </a>字段的名称。
     * 
     */
    public static final String LEGAL_VALUES_FIELD = "legalValues";

    /**
     * The name of the <a href="Descriptor.html#maxValue">{@code
     * maxValue}</a> field.
     * <p>
     *  字段的名称。<a href="Descriptor.html#maxValue"> {@code maxValue} </a>字段。
     * 
     */
    public static final String MAX_VALUE_FIELD = "maxValue";

    /**
     * The name of the <a href="Descriptor.html#minValue">{@code
     * minValue}</a> field.
     * <p>
     *  <a href="Descriptor.html#minValue"> {@code minValue} </a>字段的名称。
     * 
     */
    public static final String MIN_VALUE_FIELD = "minValue";

    /**
     * The name of the <a href="Descriptor.html#mxbean">{@code
     * mxbean}</a> field.
     * <p>
     *  <a href="Descriptor.html#mxbean"> {@code mxbean} </a>字段的名称。
     * 
     */
    public static final String MXBEAN_FIELD = "mxbean";

    /**
     * The name of the <a href="Descriptor.html#openType">{@code
     * openType}</a> field.
     * <p>
     *  <a href="Descriptor.html#openType"> {@code openType} </a>字段的名称。
     * 
     */
    public static final String OPEN_TYPE_FIELD = "openType";

    /**
     * The name of the <a href="Descriptor.html#originalType">{@code
     * originalType}</a> field.
     * <p>
     *  <a href="Descriptor.html#originalType"> {@code originalType} </a>字段的名称。
     * 
     */
    public static final String ORIGINAL_TYPE_FIELD = "originalType";

    /**
     * <p>Make a proxy for a Standard MBean in a local or remote
     * MBean Server.</p>
     *
     * <p>If you have an MBean Server {@code mbs} containing an MBean
     * with {@link ObjectName} {@code name}, and if the MBean's
     * management interface is described by the Java interface
     * {@code MyMBean}, you can construct a proxy for the MBean like
     * this:</p>
     *
     * <pre>
     * MyMBean proxy = JMX.newMBeanProxy(mbs, name, MyMBean.class);
     * </pre>
     *
     * <p>Suppose, for example, {@code MyMBean} looks like this:</p>
     *
     * <pre>
     * public interface MyMBean {
     *     public String getSomeAttribute();
     *     public void setSomeAttribute(String value);
     *     public void someOperation(String param1, int param2);
     * }
     * </pre>
     *
     * <p>Then you can execute:</p>
     *
     * <ul>
     *
     * <li>{@code proxy.getSomeAttribute()} which will result in a
     * call to {@code mbs.}{@link MBeanServerConnection#getAttribute
     * getAttribute}{@code (name, "SomeAttribute")}.
     *
     * <li>{@code proxy.setSomeAttribute("whatever")} which will result
     * in a call to {@code mbs.}{@link MBeanServerConnection#setAttribute
     * setAttribute}{@code (name, new Attribute("SomeAttribute", "whatever"))}.
     *
     * <li>{@code proxy.someOperation("param1", 2)} which will be
     * translated into a call to {@code mbs.}{@link
     * MBeanServerConnection#invoke invoke}{@code (name, "someOperation", <etc>)}.
     *
     * </ul>
     *
     * <p>The object returned by this method is a
     * {@link Proxy} whose {@code InvocationHandler} is an
     * {@link MBeanServerInvocationHandler}.</p>
     *
     * <p>This method is equivalent to {@link
     * #newMBeanProxy(MBeanServerConnection, ObjectName, Class,
     * boolean) newMBeanProxy(connection, objectName, interfaceClass,
     * false)}.</p>
     *
     * <p>
     *  <p>在本地或远程MBean Server中为标准MBean创建代理。</p>
     * 
     *  <p>如果您有一个MBean服务器{@code mbs}包含带有{@link ObjectName} {@code name}的MBean,并且如果MBean的管理界面由Java界面{@code MyMBean}
     * 描述,像这样的MBean的代理：</p>。
     * 
     * <pre>
     *  MyMBean proxy = JMX.newMBeanProxy(mbs,name,MyMBean.class);
     * </pre>
     * 
     *  <p>假设,例如,{@code MyMBean}看起来像这样：</p>
     * 
     * <pre>
     * public interface MyMBean {public String getSomeAttribute(); public void setSomeAttribute(String value); public void someOperation(String param1,int param2); }
     * }。
     * </pre>
     * 
     *  <p>然后您可以执行：</p>
     * 
     * <ul>
     * 
     *  <li> {@ code proxy.getSomeAttribute()}这会导致对{@code mbs.}{@link MBeanServerConnection#getAttribute getAttribute}
     *  {@ code(name,"SomeAttribute")}的调用。
     * 
     *  <li> {@ code proxy.setSomeAttribute("whatever")}这将导致对{@code mbs.}{@link MBeanServerConnection#setAttribute setAttribute}
     * 的调用{@ code(name,new Attribute("SomeAttribute","随你"))}。
     * 
     *  <li> {@ code proxy.someOperation("param1",2)}将转换为对{@code mbs.}{@link MBeanServerConnection#invoke invoke}
     * 的调用{@ code(name,"someOperation",< etc>)}。
     * 
     * </ul>
     * 
     *  <p>此方法返回的对象是{@link Proxy},其{@code InvocationHandler}是一个{@link MBeanServerInvocationHandler}。</p>
     * 
     *  <p>此方法相当于{@link #newMBeanProxy(MBeanServerConnection,ObjectName,Class,boolean)newMBeanProxy(connection,objectName,interfaceClass,false)}
     * 。
     * </p>。
     * 
     * 
     * @param connection the MBean server to forward to.
     * @param objectName the name of the MBean within
     * {@code connection} to forward to.
     * @param interfaceClass the management interface that the MBean
     * exports, which will also be implemented by the returned proxy.
     *
     * @param <T> allows the compiler to know that if the {@code
     * interfaceClass} parameter is {@code MyMBean.class}, for
     * example, then the return type is {@code MyMBean}.
     *
     * @return the new proxy instance.
     *
     * @throws IllegalArgumentException if {@code interfaceClass} is not
     * a <a href="package-summary.html#mgIface">compliant MBean
     * interface</a>
     */
    public static <T> T newMBeanProxy(MBeanServerConnection connection,
                                      ObjectName objectName,
                                      Class<T> interfaceClass) {
        return newMBeanProxy(connection, objectName, interfaceClass, false);
    }

    /**
     * <p>Make a proxy for a Standard MBean in a local or remote MBean
     * Server that may also support the methods of {@link
     * NotificationEmitter}.</p>
     *
     * <p>This method behaves the same as {@link
     * #newMBeanProxy(MBeanServerConnection, ObjectName, Class)}, but
     * additionally, if {@code notificationEmitter} is {@code
     * true}, then the MBean is assumed to be a {@link
     * NotificationBroadcaster} or {@link NotificationEmitter} and the
     * returned proxy will implement {@link NotificationEmitter} as
     * well as {@code interfaceClass}.  A call to {@link
     * NotificationBroadcaster#addNotificationListener} on the proxy
     * will result in a call to {@link
     * MBeanServerConnection#addNotificationListener(ObjectName,
     * NotificationListener, NotificationFilter, Object)}, and
     * likewise for the other methods of {@link
     * NotificationBroadcaster} and {@link NotificationEmitter}.</p>
     *
     * <p>
     *  <p>为本地或远程MBean服务器中的标准MBean代理,该服务器也可能支持{@link NotificationEmitter}的方法。</p>
     * 
     * <p>此方法的行为与{@link #newMBeanProxy(MBeanServerConnection,ObjectName,Class)}相同,但另外,如果{@code notificationEmitter}
     * 是{@code true},则MBean被假定为{@link NotificationBroadcaster}或{@link NotificationEmitter},返回的代理将实现{@link NotificationEmitter}
     * 以及{@code interfaceClass}。
     * 在代理上对{@link NotificationBroadcaster#addNotificationListener}的调用将导致对{@link MBeanServerConnection#addNotificationListener(ObjectName,NotificationListener,NotificationFilter,Object)}
     * 的调用,对于{@link NotificationBroadcaster}和{ @link NotificationEmitter}。
     * </p>。
     * 
     * 
     * @param connection the MBean server to forward to.
     * @param objectName the name of the MBean within
     * {@code connection} to forward to.
     * @param interfaceClass the management interface that the MBean
     * exports, which will also be implemented by the returned proxy.
     * @param notificationEmitter make the returned proxy
     * implement {@link NotificationEmitter} by forwarding its methods
     * via {@code connection}.
     *
     * @param <T> allows the compiler to know that if the {@code
     * interfaceClass} parameter is {@code MyMBean.class}, for
     * example, then the return type is {@code MyMBean}.
     *
     * @return the new proxy instance.
     *
     * @throws IllegalArgumentException if {@code interfaceClass} is not
     * a <a href="package-summary.html#mgIface">compliant MBean
     * interface</a>
     */
    public static <T> T newMBeanProxy(MBeanServerConnection connection,
                                      ObjectName objectName,
                                      Class<T> interfaceClass,
                                      boolean notificationEmitter) {
        return createProxy(connection, objectName, interfaceClass, notificationEmitter, false);
    }

    /**
     * Make a proxy for an MXBean in a local or remote MBean Server.
     *
     * <p>If you have an MBean Server {@code mbs} containing an
     * MXBean with {@link ObjectName} {@code name}, and if the
     * MXBean's management interface is described by the Java
     * interface {@code MyMXBean}, you can construct a proxy for
     * the MXBean like this:</p>
     *
     * <pre>
     * MyMXBean proxy = JMX.newMXBeanProxy(mbs, name, MyMXBean.class);
     * </pre>
     *
     * <p>Suppose, for example, {@code MyMXBean} looks like this:</p>
     *
     * <pre>
     * public interface MyMXBean {
     *     public String getSimpleAttribute();
     *     public void setSimpleAttribute(String value);
     *     public {@link java.lang.management.MemoryUsage} getMappedAttribute();
     *     public void setMappedAttribute(MemoryUsage memoryUsage);
     *     public MemoryUsage someOperation(String param1, MemoryUsage param2);
     * }
     * </pre>
     *
     * <p>Then:</p>
     *
     * <ul>
     *
     * <li><p>{@code proxy.getSimpleAttribute()} will result in a
     * call to {@code mbs.}{@link MBeanServerConnection#getAttribute
     * getAttribute}{@code (name, "SimpleAttribute")}.</p>
     *
     * <li><p>{@code proxy.setSimpleAttribute("whatever")} will result
     * in a call to {@code mbs.}{@link
     * MBeanServerConnection#setAttribute setAttribute}<code>(name,
     * new Attribute("SimpleAttribute", "whatever"))</code>.</p>
     *
     *     <p>Because {@code String} is a <em>simple type</em>, in the
     *     sense of {@link javax.management.openmbean.SimpleType}, it
     *     is not changed in the context of an MXBean.  The MXBean
     *     proxy behaves the same as a Standard MBean proxy (see
     *     {@link #newMBeanProxy(MBeanServerConnection, ObjectName,
     *     Class) newMBeanProxy}) for the attribute {@code
     *     SimpleAttribute}.</p>
     *
     * <li><p>{@code proxy.getMappedAttribute()} will result in a call
     * to {@code mbs.getAttribute("MappedAttribute")}.  The MXBean
     * mapping rules mean that the actual type of the attribute {@code
     * MappedAttribute} will be {@link
     * javax.management.openmbean.CompositeData CompositeData} and
     * that is what the {@code mbs.getAttribute} call will return.
     * The proxy will then convert the {@code CompositeData} back into
     * the expected type {@code MemoryUsage} using the MXBean mapping
     * rules.</p>
     *
     * <li><p>Similarly, {@code proxy.setMappedAttribute(memoryUsage)}
     * will convert the {@code MemoryUsage} argument into a {@code
     * CompositeData} before calling {@code mbs.setAttribute}.</p>
     *
     * <li><p>{@code proxy.someOperation("whatever", memoryUsage)}
     * will convert the {@code MemoryUsage} argument into a {@code
     * CompositeData} and call {@code mbs.invoke}.  The value returned
     * by {@code mbs.invoke} will be also be a {@code CompositeData},
     * and the proxy will convert this into the expected type {@code
     * MemoryUsage} using the MXBean mapping rules.</p>
     *
     * </ul>
     *
     * <p>The object returned by this method is a
     * {@link Proxy} whose {@code InvocationHandler} is an
     * {@link MBeanServerInvocationHandler}.</p>
     *
     * <p>This method is equivalent to {@link
     * #newMXBeanProxy(MBeanServerConnection, ObjectName, Class,
     * boolean) newMXBeanProxy(connection, objectName, interfaceClass,
     * false)}.</p>
     *
     * <p>
     *  在本地或远程MBean Server中为MXBean创建代理。
     * 
     *  <p>如果您有一个包含{@link ObjectName} {@code name}的MXBean的MBean Server {@code mbs},并且如果MXBean的管理界面由Java界面{@code MyMXBean}
     * 描述,您可以构造代理MXBean像这样：</p>。
     * 
     * <pre>
     *  MyMXBean proxy = JMX.newMXBeanProxy(mbs,name,MyMXBean.class);
     * </pre>
     * 
     *  <p>假设,例如,{@code MyMXBean}看起来像这样：</p>
     * 
     * <pre>
     *  public interface MyMXBean {public String getSimpleAttribute(); public void setSimpleAttribute(String value); public {@link java.lang.management.MemoryUsage}
     *  getMappedAttribute(); public void setMappedAttribute(MemoryUsage memoryUsage); public MemoryUsage so
     * meOperation(String param1,MemoryUsage param2); }}。
     * </pre>
     * 
     *  <p>然后：</p>
     * 
     * <ul>
     * 
     * <li> <p> {@ code proxy.getSimpleAttribute()}会导致对{@code mbs.}{@link MBeanServerConnection#getAttribute getAttribute}
     *  {@ code(name,"SimpleAttribute")}的调用。
     * </p >。
     * 
     *  <li> <p> {@ code proxy.setSimpleAttribute("whatever")}将导致对{@code mbs.}{@link MBeanServerConnection#setAttribute setAttribute}
     *  <code>(name,new Attribute("SimpleAttribute" ,"whatever"))</code>。
     * </p>。
     * 
     *  <p>由于{@code String}是一个<em>简单类型</em>,在{@link javax.management.openmbean.SimpleType}的意义上,它不会在MXBean的上下
     * 文中改变。
     * 对于属性{@code SimpleAttribute},MXBean代理的行为与标准MBean代理相同(请参阅{@link #newMBeanProxy(MBeanServerConnection,ObjectName,Class)newMBeanProxy}
     * )。
     * </p>。
     * 
     *  <li> <p> {@ code proxy.getMappedAttribute()}会导致对{@code mbs.getAttribute("MappedAttribute")}的调用。
     *  MXBean映射规则意味着属性{@code MappedAttribute}的实际类型将是{@link javax.management.openmbean.CompositeData CompositeData}
     * ,这是{@code mbs.getAttribute}调用将返回的。
     * 
     * @param connection the MBean server to forward to.
     * @param objectName the name of the MBean within
     * {@code connection} to forward to.
     * @param interfaceClass the MXBean interface,
     * which will also be implemented by the returned proxy.
     *
     * @param <T> allows the compiler to know that if the {@code
     * interfaceClass} parameter is {@code MyMXBean.class}, for
     * example, then the return type is {@code MyMXBean}.
     *
     * @return the new proxy instance.
     *
     * @throws IllegalArgumentException if {@code interfaceClass} is not
     * a {@link javax.management.MXBean compliant MXBean interface}
     */
    public static <T> T newMXBeanProxy(MBeanServerConnection connection,
                                       ObjectName objectName,
                                       Class<T> interfaceClass) {
        return newMXBeanProxy(connection, objectName, interfaceClass, false);
    }

    /**
     * <p>Make a proxy for an MXBean in a local or remote MBean
     * Server that may also support the methods of {@link
     * NotificationEmitter}.</p>
     *
     * <p>This method behaves the same as {@link
     * #newMXBeanProxy(MBeanServerConnection, ObjectName, Class)}, but
     * additionally, if {@code notificationEmitter} is {@code
     * true}, then the MXBean is assumed to be a {@link
     * NotificationBroadcaster} or {@link NotificationEmitter} and the
     * returned proxy will implement {@link NotificationEmitter} as
     * well as {@code interfaceClass}.  A call to {@link
     * NotificationBroadcaster#addNotificationListener} on the proxy
     * will result in a call to {@link
     * MBeanServerConnection#addNotificationListener(ObjectName,
     * NotificationListener, NotificationFilter, Object)}, and
     * likewise for the other methods of {@link
     * NotificationBroadcaster} and {@link NotificationEmitter}.</p>
     *
     * <p>
     *  <li> <p> {@ code proxy.getMappedAttribute()}会导致对{@code mbs.getAttribute("MappedAttribute")}的调用。
     * 然后,代理将使用MXBean映射规则将{@code CompositeData}转换回期望的类型{@code MemoryUsage}。</p>。
     * 
     *  </li> </p>同样,{@code proxy.setMappedAttribute(memoryUsage)}会将{@code MemoryUsage}参数转换为{@code CompositeData}
     * ,然后再调用{@code mbs.setAttribute}。
     * 
     * <li> <p> {@ code proxy.someOperation("whatever",memoryUsage)}会将{@code MemoryUsage}参数转换为{@code CompositeData}
     * 并调用{@code mbs.invoke}。
     *  {@code mbs.invoke}返回的值也将是一个{@code CompositeData},并且代理将使用MXBean映射规则将其转换为预期类型{@code MemoryUsage}。
     * </p>。
     * 
     * </ul>
     * 
     *  <p>此方法返回的对象是{@link Proxy},其{@code InvocationHandler}是一个{@link MBeanServerInvocationHandler}。</p>
     * 
     *  <p>此方法相当于{@link #newMXBeanProxy(MBeanServerConnection,ObjectName,Class,boolean)newMXBeanProxy(connection,objectName,interfaceClass,false)}
     * 。
     * </p>。
     * 
     * 
     * @param connection the MBean server to forward to.
     * @param objectName the name of the MBean within
     * {@code connection} to forward to.
     * @param interfaceClass the MXBean interface,
     * which will also be implemented by the returned proxy.
     * @param notificationEmitter make the returned proxy
     * implement {@link NotificationEmitter} by forwarding its methods
     * via {@code connection}.
     *
     * @param <T> allows the compiler to know that if the {@code
     * interfaceClass} parameter is {@code MyMXBean.class}, for
     * example, then the return type is {@code MyMXBean}.
     *
     * @return the new proxy instance.
     *
     * @throws IllegalArgumentException if {@code interfaceClass} is not
     * a {@link javax.management.MXBean compliant MXBean interface}
     */
    public static <T> T newMXBeanProxy(MBeanServerConnection connection,
                                       ObjectName objectName,
                                       Class<T> interfaceClass,
                                       boolean notificationEmitter) {
        return createProxy(connection, objectName, interfaceClass, notificationEmitter, true);
    }

    /**
     * <p>Test whether an interface is an MXBean interface.
     * An interface is an MXBean interface if it is public,
     * annotated {@link MXBean &#64;MXBean} or {@code @MXBean(true)}
     * or if it does not have an {@code @MXBean} annotation
     * and its name ends with "{@code MXBean}".</p>
     *
     * <p>
     * 
     * @param interfaceClass The candidate interface.
     *
     * @return true if {@code interfaceClass} is a
     * {@link javax.management.MXBean compliant MXBean interface}
     *
     * @throws NullPointerException if {@code interfaceClass} is null.
     */
    public static boolean isMXBeanInterface(Class<?> interfaceClass) {
        if (!interfaceClass.isInterface())
            return false;
        if (!Modifier.isPublic(interfaceClass.getModifiers()) &&
            !Introspector.ALLOW_NONPUBLIC_MBEAN) {
            return false;
        }
        MXBean a = interfaceClass.getAnnotation(MXBean.class);
        if (a != null)
            return a.value();
        return interfaceClass.getName().endsWith("MXBean");
        // We don't bother excluding the case where the name is
        // exactly the string "MXBean" since that would mean there
        // was no package name, which is pretty unlikely in practice.
    }

    /**
     * Centralised M(X)Bean proxy creation code
     * <p>
     *  <p>为本地或远程MBean Server中的MXBean创建代理,该代理也可能支持{@link NotificationEmitter}的方法。</p>
     * 
     * <p>此方法的行为与{@link #newMXBeanProxy(MBeanServerConnection,ObjectName,Class)}相同,但另外,如果{@code notificationEmitter}
     * 是{@code true},则MXBean被假定为{@link NotificationBroadcaster}或{@link NotificationEmitter},返回的代理将实现{@link NotificationEmitter}
     * 以及{@code interfaceClass}。
     * 在代理上对{@link NotificationBroadcaster#addNotificationListener}的调用将导致对{@link MBeanServerConnection#addNotificationListener(ObjectName,NotificationListener,NotificationFilter,Object)}
     * 的调用,对于{@link NotificationBroadcaster}和{ @link NotificationEmitter}。
     * </p>。
     * 
     * 
     * @param connection {@linkplain MBeanServerConnection} to use
     * @param objectName M(X)Bean object name
     * @param interfaceClass M(X)Bean interface class
     * @param notificationEmitter Is a notification emitter?
     * @param isMXBean Is an MXBean?
     * @return Returns an M(X)Bean proxy generated for the provided interface class
     * @throws SecurityException
     * @throws IllegalArgumentException
     */
    private static <T> T createProxy(MBeanServerConnection connection,
                                     ObjectName objectName,
                                     Class<T> interfaceClass,
                                     boolean notificationEmitter,
                                     boolean isMXBean) {

        try {
            if (isMXBean) {
                // Check interface for MXBean compliance
                Introspector.testComplianceMXBeanInterface(interfaceClass);
            } else {
                // Check interface for MBean compliance
                Introspector.testComplianceMBeanInterface(interfaceClass);
            }
        } catch (NotCompliantMBeanException e) {
            throw new IllegalArgumentException(e);
        }

        InvocationHandler handler = new MBeanServerInvocationHandler(
                connection, objectName, isMXBean);
        final Class<?>[] interfaces;
        if (notificationEmitter) {
            interfaces =
                new Class<?>[] {interfaceClass, NotificationEmitter.class};
        } else
            interfaces = new Class<?>[] {interfaceClass};

        Object proxy = Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                interfaces,
                handler);
        return interfaceClass.cast(proxy);
    }
}
