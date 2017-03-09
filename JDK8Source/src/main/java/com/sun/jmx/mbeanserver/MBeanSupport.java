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

package com.sun.jmx.mbeanserver;


import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import com.sun.jmx.mbeanserver.MXBeanMappingFactory;
import sun.reflect.misc.ReflectUtil;

/**
 * Base class for MBeans.  There is one instance of this class for
 * every Standard MBean and every MXBean.  We try to limit the amount
 * of information per instance so we can handle very large numbers of
 * MBeans comfortably.
 *
 * <p>
 *  MBeans的基类。每个标准MBean和每个MXBean都有一个此类的实例。我们尝试限制每个实例的信息量,以便我们可以舒适地处理非常大量的MBean。
 * 
 * 
 * @param <M> either Method or ConvertingMethod, for Standard MBeans
 * and MXBeans respectively.
 *
 * @since 1.6
 */
/*
 * We maintain a couple of caches to increase sharing between
 * different MBeans of the same type and also to reduce creation time
 * for the second and subsequent instances of the same type.
 *
 * The first cache maps from an MBean interface to a PerInterface
 * object containing information parsed out of the interface.  The
 * interface is either a Standard MBean interface or an MXBean
 * interface, and there is one cache for each case.
 *
 * The PerInterface includes an MBeanInfo.  This contains the
 * attributes and operations parsed out of the interface's methods,
 * plus a basic Descriptor for the interface containing at least the
 * interfaceClassName field and any fields derived from annotations on
 * the interface.  This MBeanInfo can never be the MBeanInfo for any
 * actual MBean, because an MBeanInfo's getClassName() is the name of
 * a concrete class and we don't know what the class will be.
 * Furthermore a real MBeanInfo may need to add constructors and/or
 * notifications to the MBeanInfo.
 *
 * The PerInterface also contains an MBeanDispatcher which is able to
 * route getAttribute, setAttribute, and invoke to the appropriate
 * method of the interface, including doing any necessary translation
 * of parameters and return values for MXBeans.
 *
 * The PerInterface also contains the original Class for the interface.
 *
 * We need to be careful about references.  When there are no MBeans
 * with a given interface, there must not be any strong references to
 * the interface Class.  Otherwise it could never be garbage collected,
 * and neither could its ClassLoader or any other classes loaded by
 * its ClassLoader.  Therefore the cache must wrap the PerInterface
 * in a WeakReference.  Each instance of MBeanSupport has a strong
 * reference to its PerInterface, which prevents PerInterface instances
 * from being garbage-collected prematurely.
 *
 * The second cache maps from a concrete class and an MBean interface
 * that that class implements to the MBeanInfo for that class and
 * interface.  (The ability to specify an interface separately comes
 * from the class StandardMBean.  MBeans registered directly in the
 * MBean Server will always have the same interface here.)
 *
 * The MBeanInfo in this second cache will be the MBeanInfo from the
 * PerInterface cache for the given itnerface, but with the
 * getClassName() having the concrete class's name, and the public
 * constructors based on the concrete class's constructors.  This
 * MBeanInfo can be shared between all instances of the concrete class
 * specifying the same interface, except instances that are
 * NotificationBroadcasters.  NotificationBroadcasters supply the
 * MBeanNotificationInfo[] in the MBeanInfo based on the instance
 * method NotificationBroadcaster.getNotificationInfo(), so two
 * instances of the same concrete class do not necessarily have the
 * same MBeanNotificationInfo[].  Currently we do not try to detect
 * when they do, although it would probably be worthwhile doing that
 * since it is a very common case.
 *
 * Standard MBeans additionally have the property that
 * getNotificationInfo() must in principle be called every time
 * getMBeanInfo() is called for the MBean, since the returned array is
 * allowed to change over time.  We attempt to reduce the cost of
 * doing this by detecting when the Standard MBean is a subclass of
 * NotificationBroadcasterSupport that does not override
 * getNotificationInfo(), meaning that the MBeanNotificationInfo[] is
 * the one that was supplied to the constructor.  MXBeans do not have
 * this problem because their getNotificationInfo() method is called
 * only once.
 *
 * <p>
 *  我们维护两个缓存以增加相同类型的不同MBean之间的共享,并且减少相同类型的第二个和后续实例的创建时间。
 * 
 *  第一个缓存从MBean接口映射到包含从接口中解析的信息的PerInterface对象。该接口是标准MBean接口或MXBean接口,每种情况都有一个缓存。
 * 
 *  PerInterface包括一个MBeanInfo。这包含从接口的方法中解析的属性和操作,以及包含至少interfaceClassName字段和从接口上的注释派生的任何字段的接口的基本描述符。
 * 此MBeanInfo永远不能是任何实际MBean的MBeanInfo,因为MBeanInfo的getClassName()是一个具体类的名称,我们不知道该类将是什么。
 * 此外,真实的MBeanInfo可能需要向MBeanInfo添加构造函数和/或通知。
 * 
 * PerInterface还包含一个MBeanDispatcher,它能够将getAttribute,setAttribute和invoke路由到接口的适当方法,包括为MXBeans执行任何必要的参数和返
 * 回值的翻译。
 * 
 *  PerInterface还包含接口的原始类。
 * 
 *  我们需要小心引用。当没有具有给定接口的MBean时,不能有对接口类的任何强引用。否则它永远不会被垃圾收集,它的ClassLoader或任何其他类加载它的ClassLoader也不能。
 * 因此,缓存必须在WeakReference中包装PerInterface。
 *  MBeanSupport的每个实例都具有对其PerInterface的强引用,这可以防止PerInterface实例过早地被垃圾回收。
 * 
 *  第二个缓存从具体类和该类实现的MBean接口映射到该类和接口的MBeanInfo。
 */
public abstract class MBeanSupport<M>
        implements DynamicMBean2, MBeanRegistration {

    <T> MBeanSupport(T resource, Class<T> mbeanInterfaceType)
            throws NotCompliantMBeanException {
        if (mbeanInterfaceType == null)
            throw new NotCompliantMBeanException("Null MBean interface");
        if (!mbeanInterfaceType.isInstance(resource)) {
            final String msg =
                "Resource class " + resource.getClass().getName() +
                " is not an instance of " + mbeanInterfaceType.getName();
            throw new NotCompliantMBeanException(msg);
        }
        ReflectUtil.checkPackageAccess(mbeanInterfaceType);
        this.resource = resource;
        MBeanIntrospector<M> introspector = getMBeanIntrospector();
        this.perInterface = introspector.getPerInterface(mbeanInterfaceType);
        this.mbeanInfo = introspector.getMBeanInfo(resource, perInterface);
    }

    /** Return the appropriate introspector for this type of MBean. */
    abstract MBeanIntrospector<M> getMBeanIntrospector();

    /**
     * Return a cookie for this MBean.  This cookie will be passed to
     * MBean method invocations where it can supply additional information
     * to the invocation.  For example, with MXBeans it can be used to
     * supply the MXBeanLookup context for resolving inter-MXBean references.
     * <p>
     *  (分别指定接口的能力来自StandardMBean类,直接在MBean Server中注册的MBean在这里总是有相同的接口。)。
     * 
     * 在第二个缓存中的MBeanInfo将是来自给定itnerface的PerInterface缓存的MBeanInfo,但是getClassName()具有具体类的名称,公共构造函数基于具体类的构造函数。
     * 这个MBeanInfo可以在指定相同接口的具体类的所有实例之间共享,除了是NotificationBroadcasters的实例。
     *  NotificationBroadcasters根据实例方法NotificationBroadcaster.getNotificationInfo()提供MBeanInfo中的MBeanNotific
     * ationInfo [],因此相同具体类的两个实例不一定具有相同的MBeanNotificationInfo []。
     * 这个MBeanInfo可以在指定相同接口的具体类的所有实例之间共享,除了是NotificationBroadcasters的实例。
     * 目前,我们不试图检测它们什么时候,尽管这可能是值得的,因为它是一个很常见的情况。
     * 
     */
    abstract Object getCookie();

    public final boolean isMXBean() {
        return perInterface.isMXBean();
    }

    // Methods that javax.management.StandardMBean should call from its
    // preRegister and postRegister, given that it is not supposed to
    // call the contained object's preRegister etc methods even if it has them
    public abstract void register(MBeanServer mbs, ObjectName name)
            throws Exception;
    public abstract void unregister();

    public final ObjectName preRegister(MBeanServer server, ObjectName name)
            throws Exception {
        if (resource instanceof MBeanRegistration)
            name = ((MBeanRegistration) resource).preRegister(server, name);
        return name;
    }

    public final void preRegister2(MBeanServer server, ObjectName name)
            throws Exception {
        register(server, name);
    }

    public final void registerFailed() {
        unregister();
    }

    public final void postRegister(Boolean registrationDone) {
        if (resource instanceof MBeanRegistration)
            ((MBeanRegistration) resource).postRegister(registrationDone);
    }

    public final void preDeregister() throws Exception {
        if (resource instanceof MBeanRegistration)
            ((MBeanRegistration) resource).preDeregister();
    }

    public final void postDeregister() {
        // Undo any work from registration.  We do this in postDeregister
        // not preDeregister, because if the user preDeregister throws an
        // exception then the MBean is not unregistered.
        try {
            unregister();
        } finally {
            if (resource instanceof MBeanRegistration)
                ((MBeanRegistration) resource).postDeregister();
        }
    }

    public final Object getAttribute(String attribute)
            throws AttributeNotFoundException,
                   MBeanException,
                   ReflectionException {
        return perInterface.getAttribute(resource, attribute, getCookie());
    }

    public final AttributeList getAttributes(String[] attributes) {
        final AttributeList result = new AttributeList(attributes.length);
        for (String attrName : attributes) {
            try {
                final Object attrValue = getAttribute(attrName);
                result.add(new Attribute(attrName, attrValue));
            } catch (Exception e) {
                // OK: attribute is not included in returned list, per spec
                // XXX: log the exception
            }
        }
        return result;
    }

    public final void setAttribute(Attribute attribute)
            throws AttributeNotFoundException,
                   InvalidAttributeValueException,
                   MBeanException,
                   ReflectionException {
        final String name = attribute.getName();
        final Object value = attribute.getValue();
        perInterface.setAttribute(resource, name, value, getCookie());
    }

    public final AttributeList setAttributes(AttributeList attributes) {
        final AttributeList result = new AttributeList(attributes.size());
        for (Object attrObj : attributes) {
            // We can't use AttributeList.asList because it has side-effects
            Attribute attr = (Attribute) attrObj;
            try {
                setAttribute(attr);
                result.add(new Attribute(attr.getName(), attr.getValue()));
            } catch (Exception e) {
                // OK: attribute is not included in returned list, per spec
                // XXX: log the exception
            }
        }
        return result;
    }

    public final Object invoke(String operation, Object[] params,
                         String[] signature)
            throws MBeanException, ReflectionException {
        return perInterface.invoke(resource, operation, params, signature,
                                   getCookie());
    }

    // Overridden by StandardMBeanSupport
    public MBeanInfo getMBeanInfo() {
        return mbeanInfo;
    }

    public final String getClassName() {
        return resource.getClass().getName();
    }

    public final Object getResource() {
        return resource;
    }

    public final Class<?> getMBeanInterface() {
        return perInterface.getMBeanInterface();
    }

    private final MBeanInfo mbeanInfo;
    private final Object resource;
    private final PerInterface<M> perInterface;
}
