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


import static com.sun.jmx.mbeanserver.Util.*;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

import javax.management.Descriptor;
import javax.management.ImmutableDescriptor;
import javax.management.IntrospectionException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationBroadcaster;
import javax.management.ReflectionException;
import sun.reflect.misc.ReflectUtil;

/**
 * An introspector for MBeans of a certain type.  There is one instance
 * of this class for Standard MBeans, and one for every MXBeanMappingFactory;
 * these two cases correspond to the two concrete subclasses of this abstract
 * class.
 *
 * <p>
 *  某种类型的MBeans的内省。这个类有一个实例用于标准MBean,每个MXBeanMappingFactory有一个实例;这两种情况对应于这个抽象类的两个具体子类。
 * 
 * 
 * @param <M> the representation of methods for this kind of MBean:
 * Method for Standard MBeans, ConvertingMethod for MXBeans.
 *
 * @since 1.6
 */
/*
 * Using a type parameter <M> allows us to deal with the fact that
 * Method and ConvertingMethod have no useful common ancestor, on
 * which we could call getName, getGenericReturnType, etc.  A simpler approach
 * would be to wrap every Method in an object that does have a common
 * ancestor with ConvertingMethod.  But that would mean an extra object
 * for every Method in every Standard MBean interface.
 * <p>
 *  使用类型参数<M>允许我们处理Method和ConvertingMethod没有有用的公共祖先的事实,我们可以在其上调用getName,getGenericReturnType等。
 * 一个更简单的方法是将每个方法包装在一个对象中,与ConvertingMethod的公共祖先。但这将意味着每个标准MBean接口中的每个方法都有一个额外的对象。
 * 
 */
abstract class MBeanIntrospector<M> {
    static final class PerInterfaceMap<M>
            extends WeakHashMap<Class<?>, WeakReference<PerInterface<M>>> {}

    /** The map from interface to PerInterface for this type of MBean. */
    abstract PerInterfaceMap<M> getPerInterfaceMap();
    /**
     * The map from concrete implementation class and interface to
     * MBeanInfo for this type of MBean.
     * <p>
     *  从具体实现类和接口到MBean类型的MBeanInfo的映射。
     * 
     */
    abstract MBeanInfoMap getMBeanInfoMap();

    /** Make an interface analyzer for this type of MBean. */
    abstract MBeanAnalyzer<M> getAnalyzer(Class<?> mbeanInterface)
    throws NotCompliantMBeanException;

    /** True if MBeans with this kind of introspector are MXBeans. */
    abstract boolean isMXBean();

    /** Find the M corresponding to the given Method. */
    abstract M mFrom(Method m);

    /** Get the name of this method. */
    abstract String getName(M m);

    /**
     * Get the return type of this method.  This is the return type
     * of a method in a Java interface, so for MXBeans it is the
     * declared Java type, not the mapped Open Type.
     * <p>
     *  获取此方法的返回类型。这是Java接口中方法的返回类型,因此对于MXBeans,它是声明的Java类型,而不是映射的打开类型。
     * 
     */
    abstract Type getGenericReturnType(M m);

    /**
     * Get the parameter types of this method in the Java interface
     * it came from.
     * <p>
     *  在它来自的Java接口中获取此方法的参数类型。
     * 
     */
    abstract Type[] getGenericParameterTypes(M m);

    /**
     * Get the signature of this method as a caller would have to supply
     * it in MBeanServer.invoke.  For MXBeans, the named types will be
     * the mapped Open Types for the parameters.
     * <p>
     *  获取这个方法的签名,因为调用者必须在MBeanServer.invoke中提供它。对于MXBeans,命名类型将是参数的映射Open Types。
     * 
     */
    abstract String[] getSignature(M m);

    /**
     * Check that this method is valid.  For example, a method in an
     * MXBean interface is not valid if one of its parameters cannot be
     * mapped to an Open Type.
     * <p>
     *  检查此方法是否有效。例如,如果MXBean接口中的一个参数无法映射到打开类型,则该方法无效。
     * 
     */
    abstract void checkMethod(M m);

    /**
     * Invoke the method with the given target and arguments.
     *
     * <p>
     *  调用具有给定目标和参数的方法。
     * 
     * 
     * @param cookie Additional information about the target.  For an
     * MXBean, this is the MXBeanLookup associated with the MXBean.
     */
    /*
     * It would be cleaner if the type of the cookie were a
     * type parameter to this class, but that would involve a lot of
     * messy type parameter propagation just to avoid a couple of casts.
     * <p>
     * 如果cookie的类型是这个类的类型参数,这将是更干净,但这将涉及大量的凌乱类型参数传播,只是为了避免几个强制转换。
     * 
     */
    abstract Object invokeM2(M m, Object target, Object[] args, Object cookie)
    throws InvocationTargetException, IllegalAccessException,
            MBeanException;

    /**
     * Test whether the given value is valid for the given parameter of this
     * M.
     * <p>
     *  测试给定值对于该M的给定参数是否有效。
     * 
     */
    abstract boolean validParameter(M m, Object value, int paramNo,
            Object cookie);

    /**
     * Construct an MBeanAttributeInfo for the given attribute based on the
     * given getter and setter.  One but not both of the getter and setter
     * may be null.
     * <p>
     *  根据给定的getter和setter,为给定的属性构造一个MBeanAttributeInfo。一个但不是两个getter和setter可以为null。
     * 
     */
    abstract MBeanAttributeInfo getMBeanAttributeInfo(String attributeName,
            M getter, M setter);
    /**
     * Construct an MBeanOperationInfo for the given operation based on
     * the M it was derived from.
     * <p>
     *  根据派生操作的M构造给定操作的MBeanOperationInfo。
     * 
     */
    abstract MBeanOperationInfo getMBeanOperationInfo(String operationName,
            M operation);

    /**
     * Get a Descriptor containing fields that MBeans of this kind will
     * always have.  For example, MXBeans will always have "mxbean=true".
     * <p>
     *  获取包含此类MBean将始终具有的字段的描述符。例如,MXBeans将始终具有"mxbean = true"。
     * 
     */
    abstract Descriptor getBasicMBeanDescriptor();

    /**
     * Get a Descriptor containing additional fields beyond the ones
     * from getBasicMBeanDescriptor that MBeans whose concrete class
     * is resourceClass will always have.
     * <p>
     *  获取一个包含额外字段的描述符,除了来自getBasicMBeanDescriptor的,其具体类是resourceClass的MBeans将始终具有的。
     * 
     */
    abstract Descriptor getMBeanDescriptor(Class<?> resourceClass);

    /**
     * Get the methods to be analyzed to build the MBean interface.
     * <p>
     *  获取要分析的方法以构建MBean接口。
     * 
     */
    final List<Method> getMethods(final Class<?> mbeanType) {
        ReflectUtil.checkPackageAccess(mbeanType);
        return Arrays.asList(mbeanType.getMethods());
    }

    final PerInterface<M> getPerInterface(Class<?> mbeanInterface)
    throws NotCompliantMBeanException {
        PerInterfaceMap<M> map = getPerInterfaceMap();
        synchronized (map) {
            WeakReference<PerInterface<M>> wr = map.get(mbeanInterface);
            PerInterface<M> pi = (wr == null) ? null : wr.get();
            if (pi == null) {
                try {
                    MBeanAnalyzer<M> analyzer = getAnalyzer(mbeanInterface);
                    MBeanInfo mbeanInfo =
                            makeInterfaceMBeanInfo(mbeanInterface, analyzer);
                    pi = new PerInterface<M>(mbeanInterface, this, analyzer,
                            mbeanInfo);
                    wr = new WeakReference<PerInterface<M>>(pi);
                    map.put(mbeanInterface, wr);
                } catch (Exception x) {
                    throw Introspector.throwException(mbeanInterface,x);
                }
            }
            return pi;
        }
    }

    /**
     * Make the MBeanInfo skeleton for the given MBean interface using
     * the given analyzer.  This will never be the MBeanInfo of any real
     * MBean (because the getClassName() must be a concrete class), but
     * its MBeanAttributeInfo[] and MBeanOperationInfo[] can be inserted
     * into such an MBeanInfo, and its Descriptor can be the basis for
     * the MBeanInfo's Descriptor.
     * <p>
     *  使用给定的分析器为给定的MBean接口创建MBeanInfo骨架。
     * 这将永远不会是任何真正的MBean的MBeanInfo(因为getClassName()必须是一个具体类),但它的MBeanAttributeInfo []和MBeanOperationInfo []可
     * 以插入到这样的MBeanInfo,它的描述符可以作为MBeanInfo的基础描述符。
     *  使用给定的分析器为给定的MBean接口创建MBeanInfo骨架。
     * 
     */
    private MBeanInfo makeInterfaceMBeanInfo(Class<?> mbeanInterface,
            MBeanAnalyzer<M> analyzer) {
        final MBeanInfoMaker maker = new MBeanInfoMaker();
        analyzer.visit(maker);
        final String description =
                "Information on the management interface of the MBean";
        return maker.makeMBeanInfo(mbeanInterface, description);
    }

    /** True if the given getter and setter are consistent. */
    final boolean consistent(M getter, M setter) {
        return (getter == null || setter == null ||
                getGenericReturnType(getter).equals(getGenericParameterTypes(setter)[0]));
    }

    /**
     * Invoke the given M on the given target with the given args and cookie.
     * Wrap exceptions appropriately.
     * <p>
     *  使用给定的args和cookie在给定目标上调用给定的M.适当地包装异常。
     * 
     */
    final Object invokeM(M m, Object target, Object[] args, Object cookie)
    throws MBeanException, ReflectionException {
        try {
            return invokeM2(m, target, args, cookie);
        } catch (InvocationTargetException e) {
            unwrapInvocationTargetException(e);
            throw new RuntimeException(e); // not reached
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e, e.toString());
        }
        /* We do not catch and wrap RuntimeException or Error,
         * because we're in a DynamicMBean, so the logic for DynamicMBeans
         * will do the wrapping.
         * <p>
         *  因为我们在一个DynamicMBean,所以DynamicMBeans的逻辑将做包装。
         * 
         */
    }

    /**
     * Invoke the given setter on the given target with the given argument
     * and cookie.  Wrap exceptions appropriately.
     * <p>
     * 使用给定的参数和cookie在给定目标上调用给定的setter。适当地包装异常。
     * 
     */
    /* If the value is of the wrong type for the method we are about to
     * invoke, we are supposed to throw an InvalidAttributeValueException.
     * Rather than making the check always, we invoke the method, then
     * if it throws an exception we check the type to see if that was
     * what caused the exception.  The assumption is that an exception
     * from an invalid type will arise before any user method is ever
     * called (either in reflection or in OpenConverter).
     * <p>
     *  invoke,我们应该抛出一个InvalidAttributeValueException。而不是总是进行检查,我们调用该方法,然后如果它抛出异常,我们检查类型,看看是否是造成异常的原因。
     * 假设在任何用户方法被调用之前(无论是在反射还是在OpenConverter中),无效类型的异常都会出现。
     * 
     */
    final void invokeSetter(String name, M setter, Object target, Object arg,
            Object cookie)
            throws MBeanException, ReflectionException,
            InvalidAttributeValueException {
        try {
            invokeM2(setter, target, new Object[] {arg}, cookie);
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e, e.toString());
        } catch (RuntimeException e) {
            maybeInvalidParameter(name, setter, arg, cookie);
            throw e;
        } catch (InvocationTargetException e) {
            maybeInvalidParameter(name, setter, arg, cookie);
            unwrapInvocationTargetException(e);
        }
    }

    private void maybeInvalidParameter(String name, M setter, Object arg,
            Object cookie)
            throws InvalidAttributeValueException {
        if (!validParameter(setter, arg, 0, cookie)) {
            final String msg =
                    "Invalid value for attribute " + name + ": " + arg;
            throw new InvalidAttributeValueException(msg);
        }
    }

    static boolean isValidParameter(Method m, Object value, int paramNo) {
        Class<?> c = m.getParameterTypes()[paramNo];
        try {
            // Following is expensive but we only call this method to determine
            // if an exception is due to an incompatible parameter type.
            // Plain old c.isInstance doesn't work for primitive types.
            Object a = Array.newInstance(c, 1);
            Array.set(a, 0, value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static void
            unwrapInvocationTargetException(InvocationTargetException e)
            throws MBeanException {
        Throwable t = e.getCause();
        if (t instanceof RuntimeException)
            throw (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new MBeanException((Exception) t,
                    (t == null ? null : t.toString()));
    }

    /** A visitor that constructs the per-interface MBeanInfo. */
    private class MBeanInfoMaker
            implements MBeanAnalyzer.MBeanVisitor<M> {

        public void visitAttribute(String attributeName,
                M getter,
                M setter) {
            MBeanAttributeInfo mbai =
                    getMBeanAttributeInfo(attributeName, getter, setter);

            attrs.add(mbai);
        }

        public void visitOperation(String operationName,
                M operation) {
            MBeanOperationInfo mboi =
                    getMBeanOperationInfo(operationName, operation);

            ops.add(mboi);
        }

        /** Make an MBeanInfo based on the attributes and operations
        /* <p>
        /* 
         *  found in the interface. */
        MBeanInfo makeMBeanInfo(Class<?> mbeanInterface,
                String description) {
            final MBeanAttributeInfo[] attrArray =
                    attrs.toArray(new MBeanAttributeInfo[0]);
            final MBeanOperationInfo[] opArray =
                    ops.toArray(new MBeanOperationInfo[0]);
            final String interfaceClassName =
                    "interfaceClassName=" + mbeanInterface.getName();
            final Descriptor classNameDescriptor =
                    new ImmutableDescriptor(interfaceClassName);
            final Descriptor mbeanDescriptor = getBasicMBeanDescriptor();
            final Descriptor annotatedDescriptor =
                    Introspector.descriptorForElement(mbeanInterface);
            final Descriptor descriptor =
                DescriptorCache.getInstance().union(
                    classNameDescriptor,
                    mbeanDescriptor,
                    annotatedDescriptor);

            return new MBeanInfo(mbeanInterface.getName(),
                    description,
                    attrArray,
                    null,
                    opArray,
                    null,
                    descriptor);
        }

        private final List<MBeanAttributeInfo> attrs = newList();
        private final List<MBeanOperationInfo> ops = newList();
    }

    /*
     * Looking up the MBeanInfo for a given base class (implementation class)
     * is complicated by the fact that we may use the same base class with
     * several different explicit MBean interfaces via the
     * javax.management.StandardMBean class.  It is further complicated
     * by the fact that we have to be careful not to retain a strong reference
     * to any Class object for fear we would prevent a ClassLoader from being
     * garbage-collected.  So we have a first lookup from the base class
     * to a map for each interface that base class might specify giving
     * the MBeanInfo constructed for that base class and interface.
     * <p>
     *  查找给定基类(实现类)的MBeanInfo很复杂,因为我们可以通过javax.management.StandardMBean类使用具有几个不同显式MBean接口的相同基类。
     * 更复杂的是,我们必须小心不要保留对任何Class对象的强引用,因为我们会阻止ClassLoader被垃圾回收。
     * 因此,我们有一个从基类的第一次查找到基础类可能指定的每个接口的映射,给出为该基类和接口构造的MBeanInfo。
     * 
     */
    static class MBeanInfoMap
            extends WeakHashMap<Class<?>, WeakHashMap<Class<?>, MBeanInfo>> {
    }

    /**
     * Return the MBeanInfo for the given resource, based on the given
     * per-interface data.
     * <p>
     *  基于给定的每个接口数据,返回给定资源的MBeanInfo。
     * 
     */
    final MBeanInfo getMBeanInfo(Object resource, PerInterface<M> perInterface) {
        MBeanInfo mbi =
                getClassMBeanInfo(resource.getClass(), perInterface);
        MBeanNotificationInfo[] notifs = findNotifications(resource);
        if (notifs == null || notifs.length == 0)
            return mbi;
        else {
            return new MBeanInfo(mbi.getClassName(),
                    mbi.getDescription(),
                    mbi.getAttributes(),
                    mbi.getConstructors(),
                    mbi.getOperations(),
                    notifs,
                    mbi.getDescriptor());
        }
    }

    /**
     * Return the basic MBeanInfo for resources of the given class and
     * per-interface data.  This MBeanInfo might not be the final MBeanInfo
     * for instances of the class, because if the class is a
     * NotificationBroadcaster then each instance gets to decide what
     * MBeanNotificationInfo[] to put in its own MBeanInfo.
     * <p>
     *  返回给定类和每个接口数据的资源的基本MBeanInfo。
     * 这个MBeanInfo可能不是该类的实例的最后的MBeanInfo,因为如果该类是一个NotificationBroadcaster,那么每个实例得到决定什么MBeanNotificationInfo 
     * []放在自己的MBeanInfo。
     */
    final MBeanInfo getClassMBeanInfo(Class<?> resourceClass,
            PerInterface<M> perInterface) {
        MBeanInfoMap map = getMBeanInfoMap();
        synchronized (map) {
            WeakHashMap<Class<?>, MBeanInfo> intfMap = map.get(resourceClass);
            if (intfMap == null) {
                intfMap = new WeakHashMap<Class<?>, MBeanInfo>();
                map.put(resourceClass, intfMap);
            }
            Class<?> intfClass = perInterface.getMBeanInterface();
            MBeanInfo mbi = intfMap.get(intfClass);
            if (mbi == null) {
                MBeanInfo imbi = perInterface.getMBeanInfo();
                Descriptor descriptor =
                        ImmutableDescriptor.union(imbi.getDescriptor(),
                        getMBeanDescriptor(resourceClass));
                mbi = new MBeanInfo(resourceClass.getName(),
                        imbi.getDescription(),
                        imbi.getAttributes(),
                        findConstructors(resourceClass),
                        imbi.getOperations(),
                        (MBeanNotificationInfo[]) null,
                        descriptor);
                intfMap.put(intfClass, mbi);
            }
            return mbi;
        }
    }

    static MBeanNotificationInfo[] findNotifications(Object moi) {
        if (!(moi instanceof NotificationBroadcaster))
            return null;
        MBeanNotificationInfo[] mbn =
                ((NotificationBroadcaster) moi).getNotificationInfo();
        if (mbn == null)
            return null;
        MBeanNotificationInfo[] result =
                new MBeanNotificationInfo[mbn.length];
        for (int i = 0; i < mbn.length; i++) {
            MBeanNotificationInfo ni = mbn[i];
            if (ni.getClass() != MBeanNotificationInfo.class)
                ni = (MBeanNotificationInfo) ni.clone();
            result[i] = ni;
        }
        return result;
    }

    private static MBeanConstructorInfo[] findConstructors(Class<?> c) {
        Constructor<?>[] cons = c.getConstructors();
        MBeanConstructorInfo[] mbc = new MBeanConstructorInfo[cons.length];
        for (int i = 0; i < cons.length; i++) {
            final String descr = "Public constructor of the MBean";
            mbc[i] = new MBeanConstructorInfo(descr, cons[i]);
        }
        return mbc;
    }

}
