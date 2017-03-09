/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

import javax.management.Descriptor;
import javax.management.DescriptorKey;
import javax.management.DynamicMBean;
import javax.management.ImmutableDescriptor;
import javax.management.MBeanInfo;
import javax.management.NotCompliantMBeanException;

import com.sun.jmx.remote.util.EnvHelp;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import javax.management.AttributeNotFoundException;
import javax.management.openmbean.CompositeData;
import sun.reflect.misc.MethodUtil;
import sun.reflect.misc.ReflectUtil;

/**
 * This class contains the methods for performing all the tests needed to verify
 * that a class represents a JMX compliant MBean.
 *
 * <p>
 *  此类包含执行验证类是否表示符合JMX的MBean所需的所有测试的方法。
 * 
 * 
 * @since 1.5
 */
public class Introspector {
    final public static boolean ALLOW_NONPUBLIC_MBEAN;
    static {
        String val = AccessController.doPrivileged(new GetPropertyAction("jdk.jmx.mbeans.allowNonPublic"));
        ALLOW_NONPUBLIC_MBEAN = Boolean.parseBoolean(val);
    }

     /*
     * ------------------------------------------
     *  PRIVATE CONSTRUCTORS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私人建筑师------------------------------------
     * 
     */

    // private constructor defined to "hide" the default public constructor
    private Introspector() {

        // ------------------------------
        // ------------------------------

    }

    /*
     * ------------------------------------------
     *  PUBLIC METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------公共方法------------------------------------
     * 
     */

    /**
     * Tell whether a MBean of the given class is a Dynamic MBean.
     * This method does nothing more than returning
     * <pre>
     * javax.management.DynamicMBean.class.isAssignableFrom(c)
     * </pre>
     * This method does not check for any JMX MBean compliance:
     * <ul><li>If <code>true</code> is returned, then instances of
     *     <code>c</code> are DynamicMBean.</li>
     *     <li>If <code>false</code> is returned, then no further
     *     assumption can be made on instances of <code>c</code>.
     *     In particular, instances of <code>c</code> may, or may not
     *     be JMX standard MBeans.</li>
     * </ul>
     * <p>
     *  告诉给定类的MBean是否是动态MBean。这个方法没有什么比返回
     * <pre>
     *  javax.management.DynamicMBean.class.isAssignableFrom(c)
     * </pre>
     *  此方法不检查任何JMX MBean合规性：<ul> <li>如果返回<code> true </code>,则<code> c </code>的实例是DynamicMBean。
     * </li> <li>如果返回<code> false </code>,则不能对<code> c </code>的实例做进一步的假设。
     * 特别地,<code> c </code>的实例可以是或可以不是JMX标准MBean。</li>。
     * </ul>
     * 
     * @param c The class of the MBean under examination.
     * @return <code>true</code> if instances of <code>c</code> are
     *         Dynamic MBeans, <code>false</code> otherwise.
     *
     **/
    public static final boolean isDynamic(final Class<?> c) {
        // Check if the MBean implements the DynamicMBean interface
        return javax.management.DynamicMBean.class.isAssignableFrom(c);
    }

    /**
     * Basic method for testing that a MBean of a given class can be
     * instantiated by the MBean server.<p>
     * This method checks that:
     * <ul><li>The given class is a concrete class.</li>
     *     <li>The given class exposes at least one public constructor.</li>
     * </ul>
     * If these conditions are not met, throws a NotCompliantMBeanException.
     * <p>
     *  用于测试给定类的MBean可以由MBean服务器实例化的基本方法。<p>此方法检查：<ul> <li>给定类是一个具体类。</li> <li>类公开至少一个公共构造函数。</li>
     * </ul>
     *  如果这些条件不满足,则抛出NotCompliantMBeanException异常。
     * 
     * 
     * @param c The class of the MBean we want to create.
     * @exception NotCompliantMBeanException if the MBean class makes it
     *            impossible to instantiate the MBean from within the
     *            MBeanServer.
     *
     **/
    public static void testCreation(Class<?> c)
        throws NotCompliantMBeanException {
        // Check if the class is a concrete class
        final int mods = c.getModifiers();
        if (Modifier.isAbstract(mods) || Modifier.isInterface(mods)) {
            throw new NotCompliantMBeanException("MBean class must be concrete");
        }

        // Check if the MBean has a public constructor
        final Constructor<?>[] consList = c.getConstructors();
        if (consList.length == 0) {
            throw new NotCompliantMBeanException("MBean class must have public constructor");
        }
    }

    public static void checkCompliance(Class<?> mbeanClass)
    throws NotCompliantMBeanException {
        // Is DynamicMBean?
        //
        if (DynamicMBean.class.isAssignableFrom(mbeanClass))
            return;
        // Is Standard MBean?
        //
        final Exception mbeanException;
        try {
            getStandardMBeanInterface(mbeanClass);
            return;
        } catch (NotCompliantMBeanException e) {
            mbeanException = e;
        }
        // Is MXBean?
        //
        final Exception mxbeanException;
        try {
            getMXBeanInterface(mbeanClass);
            return;
        } catch (NotCompliantMBeanException e) {
            mxbeanException = e;
        }
        final String msg =
            "MBean class " + mbeanClass.getName() + " does not implement " +
            "DynamicMBean, and neither follows the Standard MBean conventions (" +
            mbeanException.toString() + ") nor the MXBean conventions (" +
            mxbeanException.toString() + ")";
        throw new NotCompliantMBeanException(msg);
    }

    public static <T> DynamicMBean makeDynamicMBean(T mbean)
        throws NotCompliantMBeanException {
        if (mbean instanceof DynamicMBean)
            return (DynamicMBean) mbean;
        final Class<?> mbeanClass = mbean.getClass();
        Class<? super T> c = null;
        try {
            c = Util.cast(getStandardMBeanInterface(mbeanClass));
        } catch (NotCompliantMBeanException e) {
            // Ignore exception - we need to check whether
            // mbean is an MXBean first.
        }
        if (c != null)
            return new StandardMBeanSupport(mbean, c);

        try {
            c = Util.cast(getMXBeanInterface(mbeanClass));
        } catch (NotCompliantMBeanException e) {
            // Ignore exception - we cannot decide whether mbean was supposed
            // to be an MBean or an MXBean. We will call checkCompliance()
            // to generate the appropriate exception.
        }
        if (c != null)
            return new MXBeanSupport(mbean, c);
        checkCompliance(mbeanClass);
        throw new NotCompliantMBeanException("Not compliant"); // not reached
    }

    /**
     * Basic method for testing if a given class is a JMX compliant MBean.
     *
     * <p>
     *  用于测试给定类是否是符合JMX的MBean的基本方法。
     * 
     * 
     * @param baseClass The class to be tested
     *
     * @return <code>null</code> if the MBean is a DynamicMBean,
     *         the computed {@link javax.management.MBeanInfo} otherwise.
     * @exception NotCompliantMBeanException The specified class is not a
     *            JMX compliant MBean
     */
    public static MBeanInfo testCompliance(Class<?> baseClass)
        throws NotCompliantMBeanException {

        // ------------------------------
        // ------------------------------

        // Check if the MBean implements the MBean or the Dynamic
        // MBean interface
        if (isDynamic(baseClass))
            return null;

        return testCompliance(baseClass, null);
    }

    /**
     * Tests the given interface class for being a compliant MXBean interface.
     * A compliant MXBean interface is any publicly accessible interface
     * following the {@link MXBean} conventions.
     * <p>
     * 测试给定的接口类是否是兼容的MXBean接口。兼容的MXBean接口是遵循{@link MXBean}约定的任何可公开访问的接口。
     * 
     * 
     * @param interfaceClass An interface class to test for the MXBean compliance
     * @throws NotCompliantMBeanException Thrown when the tested interface
     * is not public or contradicts the {@link MXBean} conventions.
     */
    public static void testComplianceMXBeanInterface(Class<?> interfaceClass)
            throws NotCompliantMBeanException {
        MXBeanIntrospector.getInstance().getAnalyzer(interfaceClass);
    }

    /**
     * Tests the given interface class for being a compliant MBean interface.
     * A compliant MBean interface is any publicly accessible interface
     * following the {@code MBean} conventions.
     * <p>
     *  测试给定的接口类是一个兼容的MBean接口。兼容的MBean接口是遵循{@code MBean}约定的任何可公开访问的接口。
     * 
     * 
     * @param interfaceClass An interface class to test for the MBean compliance
     * @throws NotCompliantMBeanException Thrown when the tested interface
     * is not public or contradicts the {@code MBean} conventions.
     */
    public static void testComplianceMBeanInterface(Class<?> interfaceClass)
            throws NotCompliantMBeanException{
        StandardMBeanIntrospector.getInstance().getAnalyzer(interfaceClass);
    }

    /**
     * Basic method for testing if a given class is a JMX compliant
     * Standard MBean.  This method is only called by the legacy code
     * in com.sun.management.jmx.
     *
     * <p>
     *  用于测试给定类是否是符合JMX的标准MBean的基本方法。此方法仅由com.sun.management.jmx中的旧代码调用。
     * 
     * 
     * @param baseClass The class to be tested.
     *
     * @param mbeanInterface the MBean interface that the class implements,
     * or null if the interface must be determined by introspection.
     *
     * @return the computed {@link javax.management.MBeanInfo}.
     * @exception NotCompliantMBeanException The specified class is not a
     *            JMX compliant Standard MBean
     */
    public static synchronized MBeanInfo
            testCompliance(final Class<?> baseClass,
                           Class<?> mbeanInterface)
            throws NotCompliantMBeanException {
        if (mbeanInterface == null)
            mbeanInterface = getStandardMBeanInterface(baseClass);
        ReflectUtil.checkPackageAccess(mbeanInterface);
        MBeanIntrospector<?> introspector = StandardMBeanIntrospector.getInstance();
        return getClassMBeanInfo(introspector, baseClass, mbeanInterface);
    }

    private static <M> MBeanInfo
            getClassMBeanInfo(MBeanIntrospector<M> introspector,
                              Class<?> baseClass, Class<?> mbeanInterface)
    throws NotCompliantMBeanException {
        PerInterface<M> perInterface = introspector.getPerInterface(mbeanInterface);
        return introspector.getClassMBeanInfo(baseClass, perInterface);
    }

    /**
     * Get the MBean interface implemented by a JMX Standard
     * MBean class. This method is only called by the legacy
     * code in "com.sun.management.jmx".
     *
     * <p>
     *  获取由JMX标准MBean类实现的MBean接口。此方法仅由"com.sun.management.jmx"中的旧代码调用。
     * 
     * 
     * @param baseClass The class to be tested.
     *
     * @return The MBean interface implemented by the MBean.
     *         Return <code>null</code> if the MBean is a DynamicMBean,
     *         or if no MBean interface is found.
     */
    public static Class<?> getMBeanInterface(Class<?> baseClass) {
        // Check if the given class implements the MBean interface
        // or the Dynamic MBean interface
        if (isDynamic(baseClass)) return null;
        try {
            return getStandardMBeanInterface(baseClass);
        } catch (NotCompliantMBeanException e) {
            return null;
        }
    }

    /**
     * Get the MBean interface implemented by a JMX Standard MBean class.
     *
     * <p>
     *  获取由JMX标准MBean类实现的MBean接口。
     * 
     * 
     * @param baseClass The class to be tested.
     *
     * @return The MBean interface implemented by the Standard MBean.
     *
     * @throws NotCompliantMBeanException The specified class is
     * not a JMX compliant Standard MBean.
     */
    public static <T> Class<? super T> getStandardMBeanInterface(Class<T> baseClass)
        throws NotCompliantMBeanException {
            Class<? super T> current = baseClass;
            Class<? super T> mbeanInterface = null;
            while (current != null) {
                mbeanInterface =
                    findMBeanInterface(current, current.getName());
                if (mbeanInterface != null) break;
                current = current.getSuperclass();
            }
                if (mbeanInterface != null) {
                    return mbeanInterface;
            } else {
            final String msg =
                "Class " + baseClass.getName() +
                " is not a JMX compliant Standard MBean";
            throw new NotCompliantMBeanException(msg);
        }
    }

    /**
     * Get the MXBean interface implemented by a JMX MXBean class.
     *
     * <p>
     *  获取由JMX MXBean类实现的MXBean接口。
     * 
     * 
     * @param baseClass The class to be tested.
     *
     * @return The MXBean interface implemented by the MXBean.
     *
     * @throws NotCompliantMBeanException The specified class is
     * not a JMX compliant MXBean.
     */
    public static <T> Class<? super T> getMXBeanInterface(Class<T> baseClass)
        throws NotCompliantMBeanException {
        try {
            return MXBeanSupport.findMXBeanInterface(baseClass);
        } catch (Exception e) {
            throw throwException(baseClass,e);
        }
    }

    /*
     * ------------------------------------------
     *  PRIVATE METHODS
     * ------------------------------------------
     * <p>
     *  ------------------------------------------私有方法------ ------------------------------------
     * 
     */


    /**
     * Try to find the MBean interface corresponding to the class aName
     * - i.e. <i>aName</i>MBean, from within aClass and its superclasses.
     * <p>
     *  尝试从aClass及其超类中查找与类aName(即<i> aName </i> MBean)相对应的MBean接口。
     * 
     * 
     **/
    private static <T> Class<? super T> findMBeanInterface(
            Class<T> aClass, String aName) {
        Class<? super T> current = aClass;
        while (current != null) {
            final Class<?>[] interfaces = current.getInterfaces();
            final int len = interfaces.length;
            for (int i=0;i<len;i++)  {
                Class<? super T> inter = Util.cast(interfaces[i]);
                inter = implementsMBean(inter, aName);
                if (inter != null) return inter;
            }
            current = current.getSuperclass();
        }
        return null;
    }

    public static Descriptor descriptorForElement(final AnnotatedElement elmt) {
        if (elmt == null)
            return ImmutableDescriptor.EMPTY_DESCRIPTOR;
        final Annotation[] annots = elmt.getAnnotations();
        return descriptorForAnnotations(annots);
    }

    public static Descriptor descriptorForAnnotations(Annotation[] annots) {
        if (annots.length == 0)
            return ImmutableDescriptor.EMPTY_DESCRIPTOR;
        Map<String, Object> descriptorMap = new HashMap<String, Object>();
        for (Annotation a : annots) {
            Class<? extends Annotation> c = a.annotationType();
            Method[] elements = c.getMethods();
            boolean packageAccess = false;
            for (Method element : elements) {
                DescriptorKey key = element.getAnnotation(DescriptorKey.class);
                if (key != null) {
                    String name = key.value();
                    Object value;
                    try {
                        // Avoid checking access more than once per annotation
                        if (!packageAccess) {
                            ReflectUtil.checkPackageAccess(c);
                            packageAccess = true;
                        }
                        value = MethodUtil.invoke(element, a, null);
                    } catch (RuntimeException e) {
                        // we don't expect this - except for possibly
                        // security exceptions?
                        // RuntimeExceptions shouldn't be "UndeclaredThrowable".
                        // anyway...
                        //
                        throw e;
                    } catch (Exception e) {
                        // we don't expect this
                        throw new UndeclaredThrowableException(e);
                    }
                    value = annotationToField(value);
                    Object oldValue = descriptorMap.put(name, value);
                    if (oldValue != null && !equals(oldValue, value)) {
                        final String msg =
                            "Inconsistent values for descriptor field " + name +
                            " from annotations: " + value + " :: " + oldValue;
                        throw new IllegalArgumentException(msg);
                    }
                }
            }
        }

        if (descriptorMap.isEmpty())
            return ImmutableDescriptor.EMPTY_DESCRIPTOR;
        else
            return new ImmutableDescriptor(descriptorMap);
    }

    /**
     * Throws a NotCompliantMBeanException or a SecurityException.
     * <p>
     *  抛出NotCompliantMBeanException或SecurityException。
     * 
     * 
     * @param notCompliant the class which was under examination
     * @param cause the raeson why NotCompliantMBeanException should
     *        be thrown.
     * @return nothing - this method always throw an exception.
     *         The return type makes it possible to write
     *         <pre> throw throwException(clazz,cause); </pre>
     * @throws SecurityException - if cause is a SecurityException
     * @throws NotCompliantMBeanException otherwise.
     **/
    static NotCompliantMBeanException throwException(Class<?> notCompliant,
            Throwable cause)
            throws NotCompliantMBeanException, SecurityException {
        if (cause instanceof SecurityException)
            throw (SecurityException) cause;
        if (cause instanceof NotCompliantMBeanException)
            throw (NotCompliantMBeanException)cause;
        final String classname =
                (notCompliant==null)?"null class":notCompliant.getName();
        final String reason =
                (cause==null)?"Not compliant":cause.getMessage();
        final NotCompliantMBeanException res =
                new NotCompliantMBeanException(classname+": "+reason);
        res.initCause(cause);
        throw res;
    }

    // Convert a value from an annotation element to a descriptor field value
    // E.g. with @interface Foo {class value()} an annotation @Foo(String.class)
    // will produce a Descriptor field value "java.lang.String"
    private static Object annotationToField(Object x) {
        // An annotation element cannot have a null value but never mind
        if (x == null)
            return null;
        if (x instanceof Number || x instanceof String ||
                x instanceof Character || x instanceof Boolean ||
                x instanceof String[])
            return x;
        // Remaining possibilities: array of primitive (e.g. int[]),
        // enum, class, array of enum or class.
        Class<?> c = x.getClass();
        if (c.isArray()) {
            if (c.getComponentType().isPrimitive())
                return x;
            Object[] xx = (Object[]) x;
            String[] ss = new String[xx.length];
            for (int i = 0; i < xx.length; i++)
                ss[i] = (String) annotationToField(xx[i]);
            return ss;
        }
        if (x instanceof Class<?>)
            return ((Class<?>) x).getName();
        if (x instanceof Enum<?>)
            return ((Enum<?>) x).name();
        // The only other possibility is that the value is another
        // annotation, or that the language has evolved since this code
        // was written.  We don't allow for either of those currently.
        // If it is indeed another annotation, then x will be a proxy
        // with an unhelpful name like $Proxy2.  So we extract the
        // proxy's interface to use that in the exception message.
        if (Proxy.isProxyClass(c))
            c = c.getInterfaces()[0];  // array "can't be empty"
        throw new IllegalArgumentException("Illegal type for annotation " +
                "element using @DescriptorKey: " + c.getName());
    }

    // This must be consistent with the check for duplicate field values in
    // ImmutableDescriptor.union.  But we don't expect to be called very
    // often so this inefficient check should be enough.
    private static boolean equals(Object x, Object y) {
        return Arrays.deepEquals(new Object[] {x}, new Object[] {y});
    }

    /**
     * Returns the XXMBean interface or null if no such interface exists
     *
     * <p>
     *  返回XXMBean接口,如果没有此类接口,则返回null
     * 
     * 
     * @param c The interface to be tested
     * @param clName The name of the class implementing this interface
     */
    private static <T> Class<? super T> implementsMBean(Class<T> c, String clName) {
        String clMBeanName = clName + "MBean";
        if (c.getName().equals(clMBeanName)) {
            return c;
        }
        Class<?>[] interfaces = c.getInterfaces();
        for (int i = 0;i < interfaces.length; i++) {
            if (interfaces[i].getName().equals(clMBeanName) &&
                (Modifier.isPublic(interfaces[i].getModifiers()) ||
                 ALLOW_NONPUBLIC_MBEAN)) {
                return Util.cast(interfaces[i]);
            }
        }

        return null;
    }

    public static Object elementFromComplex(Object complex, String element)
    throws AttributeNotFoundException {
        try {
            if (complex.getClass().isArray() && element.equals("length")) {
                return Array.getLength(complex);
            } else if (complex instanceof CompositeData) {
                return ((CompositeData) complex).get(element);
            } else {
                // Java Beans introspection
                //
                Class<?> clazz = complex.getClass();
                Method readMethod = null;
                if (BeansHelper.isAvailable()) {
                    Object bi = BeansHelper.getBeanInfo(clazz);
                    Object[] pds = BeansHelper.getPropertyDescriptors(bi);
                    for (Object pd: pds) {
                        if (BeansHelper.getPropertyName(pd).equals(element)) {
                            readMethod = BeansHelper.getReadMethod(pd);
                            break;
                        }
                    }
                } else {
                    // Java Beans not available so use simple introspection
                    // to locate method
                    readMethod = SimpleIntrospector.getReadMethod(clazz, element);
                }
                if (readMethod != null) {
                    ReflectUtil.checkPackageAccess(readMethod.getDeclaringClass());
                    return MethodUtil.invoke(readMethod, complex, new Class[0]);
                }

                throw new AttributeNotFoundException(
                    "Could not find the getter method for the property " +
                    element + " using the Java Beans introspector");
            }
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        } catch (AttributeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw EnvHelp.initCause(
                new AttributeNotFoundException(e.getMessage()), e);
        }
    }

    /**
     * A simple introspector that uses reflection to analyze a class and
     * identify its "getter" methods. This class is intended for use only when
     * Java Beans is not present (which implies that there isn't explicit
     * information about the bean available).
     * <p>
     *  一个简单的内省函数,使用反射来分析类并识别其"getter"方法。此类仅适用于Java Bean不存在(这意味着没有有关可用bean的显式信息)。
     * 
     */
    private static class SimpleIntrospector {
        private SimpleIntrospector() { }

        private static final String GET_METHOD_PREFIX = "get";
        private static final String IS_METHOD_PREFIX = "is";

        // cache to avoid repeated lookups
        private static final Map<Class<?>,SoftReference<List<Method>>> cache =
            Collections.synchronizedMap(
                new WeakHashMap<Class<?>,SoftReference<List<Method>>> ());

        /**
         * Returns the list of methods cached for the given class, or {@code null}
         * if not cached.
         * <p>
         *  返回为给定类缓存的方法列表,如果未缓存,则返回{@code null}。
         * 
         */
        private static List<Method> getCachedMethods(Class<?> clazz) {
            // return cached methods if possible
            SoftReference<List<Method>> ref = cache.get(clazz);
            if (ref != null) {
                List<Method> cached = ref.get();
                if (cached != null)
                    return cached;
            }
            return null;
        }

        /**
         * Returns {@code true} if the given method is a "getter" method (where
         * "getter" method is a public method of the form getXXX or "boolean
         * isXXX")
         * <p>
         * 如果给定的方法是"getter"方法(其中"getter"方法是getXXX或"boolean isXXX"形式的公共方法),则返回{@code true}
         * 
         */
        static boolean isReadMethod(Method method) {
            // ignore static methods
            int modifiers = method.getModifiers();
            if (Modifier.isStatic(modifiers))
                return false;

            String name = method.getName();
            Class<?>[] paramTypes = method.getParameterTypes();
            int paramCount = paramTypes.length;

            if (paramCount == 0 && name.length() > 2) {
                // boolean isXXX()
                if (name.startsWith(IS_METHOD_PREFIX))
                    return (method.getReturnType() == boolean.class);
                // getXXX()
                if (name.length() > 3 && name.startsWith(GET_METHOD_PREFIX))
                    return (method.getReturnType() != void.class);
            }
            return false;
        }

        /**
         * Returns the list of "getter" methods for the given class. The list
         * is ordered so that isXXX methods appear before getXXX methods - this
         * is for compatibility with the JavaBeans Introspector.
         * <p>
         *  返回给定类的"getter"方法的列表。列表是有序的,因此isXXX方法出现在getXXX方法之前 - 这是为了与JavaBeans Introspector兼容。
         * 
         */
        static List<Method> getReadMethods(Class<?> clazz) {
            // return cached result if available
            List<Method> cachedResult = getCachedMethods(clazz);
            if (cachedResult != null)
                return cachedResult;

            // get list of public methods, filtering out methods that have
            // been overridden to return a more specific type.
            List<Method> methods =
                StandardMBeanIntrospector.getInstance().getMethods(clazz);
            methods = MBeanAnalyzer.eliminateCovariantMethods(methods);

            // filter out the non-getter methods
            List<Method> result = new LinkedList<Method>();
            for (Method m: methods) {
                if (isReadMethod(m)) {
                    // favor isXXX over getXXX
                    if (m.getName().startsWith(IS_METHOD_PREFIX)) {
                        result.add(0, m);
                    } else {
                        result.add(m);
                    }
                }
            }

            // add result to cache
            cache.put(clazz, new SoftReference<List<Method>>(result));

            return result;
        }

        /**
         * Returns the "getter" to read the given property from the given class or
         * {@code null} if no method is found.
         * <p>
         *  返回"getter"从给定类或{@code null}中读取给定属性,如果没有找到方法。
         * 
         */
        static Method getReadMethod(Class<?> clazz, String property) {
            // first character in uppercase (compatibility with JavaBeans)
            property = property.substring(0, 1).toUpperCase(Locale.ENGLISH) +
                property.substring(1);
            String getMethod = GET_METHOD_PREFIX + property;
            String isMethod = IS_METHOD_PREFIX + property;
            for (Method m: getReadMethods(clazz)) {
                String name = m.getName();
                if (name.equals(isMethod) || name.equals(getMethod)) {
                    return m;
                }
            }
            return null;
        }
    }

    /**
     * A class that provides access to the JavaBeans Introspector and
     * PropertyDescriptors without creating a static dependency on java.beans.
     * <p>
     *  一个类,提供对JavaBeans Introspector和PropertyDescriptors的访问,而不对java.beans创建静态依赖关系。
     * 
     */
    private static class BeansHelper {
        private static final Class<?> introspectorClass =
            getClass("java.beans.Introspector");
        private static final Class<?> beanInfoClass =
            (introspectorClass == null) ? null : getClass("java.beans.BeanInfo");
        private static final Class<?> getPropertyDescriptorClass =
            (beanInfoClass == null) ? null : getClass("java.beans.PropertyDescriptor");

        private static final Method getBeanInfo =
            getMethod(introspectorClass, "getBeanInfo", Class.class);
        private static final Method getPropertyDescriptors =
            getMethod(beanInfoClass, "getPropertyDescriptors");
        private static final Method getPropertyName =
            getMethod(getPropertyDescriptorClass, "getName");
        private static final Method getReadMethod =
            getMethod(getPropertyDescriptorClass, "getReadMethod");

        private static Class<?> getClass(String name) {
            try {
                return Class.forName(name, true, null);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
        private static Method getMethod(Class<?> clazz,
                                        String name,
                                        Class<?>... paramTypes)
        {
            if (clazz != null) {
                try {
                    return clazz.getMethod(name, paramTypes);
                } catch (NoSuchMethodException e) {
                    throw new AssertionError(e);
                }
            } else {
                return null;
            }
        }

        private BeansHelper() { }

        /**
         * Returns {@code true} if java.beans is available.
         * <p>
         *  如果java.beans可用,返回{@code true}。
         * 
         */
        static boolean isAvailable() {
            return introspectorClass != null;
        }

        /**
         * Invokes java.beans.Introspector.getBeanInfo(Class)
         * <p>
         *  调用java.beans.Introspector.getBeanInfo(Class)
         * 
         */
        static Object getBeanInfo(Class<?> clazz) throws Exception {
            try {
                return getBeanInfo.invoke(null, clazz);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof Exception)
                    throw (Exception)cause;
                throw new AssertionError(e);
            } catch (IllegalAccessException iae) {
                throw new AssertionError(iae);
            }
        }

        /**
         * Invokes java.beans.BeanInfo.getPropertyDescriptors()
         * <p>
         *  调用java.beans.BeanInfo.getPropertyDescriptors()
         * 
         */
        static Object[] getPropertyDescriptors(Object bi) {
            try {
                return (Object[])getPropertyDescriptors.invoke(bi);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException)
                    throw (RuntimeException)cause;
                throw new AssertionError(e);
            } catch (IllegalAccessException iae) {
                throw new AssertionError(iae);
            }
        }

        /**
         * Invokes java.beans.PropertyDescriptor.getName()
         * <p>
         *  调用java.beans.PropertyDescriptor.getName()
         * 
         */
        static String getPropertyName(Object pd) {
            try {
                return (String)getPropertyName.invoke(pd);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException)
                    throw (RuntimeException)cause;
                throw new AssertionError(e);
            } catch (IllegalAccessException iae) {
                throw new AssertionError(iae);
            }
        }

        /**
         * Invokes java.beans.PropertyDescriptor.getReadMethod()
         * <p>
         *  调用java.beans.PropertyDescriptor.getReadMethod()
         */
        static Method getReadMethod(Object pd) {
            try {
                return (Method)getReadMethod.invoke(pd);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException)
                    throw (RuntimeException)cause;
                throw new AssertionError(e);
            } catch (IllegalAccessException iae) {
                throw new AssertionError(iae);
            }
        }
    }
}
