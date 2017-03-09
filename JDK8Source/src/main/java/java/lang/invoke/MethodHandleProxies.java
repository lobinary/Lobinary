/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

import java.lang.reflect.*;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.invoke.WrapperInstance;
import java.util.ArrayList;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;
import static java.lang.invoke.MethodHandleStatics.*;

/**
 * This class consists exclusively of static methods that help adapt
 * method handles to other JVM types, such as interfaces.
 * <p>
 *  此类仅由静态方法组成,有助于将方法句柄适配于其他JVM类型,例如接口。
 * 
 */
public class MethodHandleProxies {

    private MethodHandleProxies() { }  // do not instantiate

    /**
     * Produces an instance of the given single-method interface which redirects
     * its calls to the given method handle.
     * <p>
     * A single-method interface is an interface which declares a uniquely named method.
     * When determining the uniquely named method of a single-method interface,
     * the public {@code Object} methods ({@code toString}, {@code equals}, {@code hashCode})
     * are disregarded.  For example, {@link java.util.Comparator} is a single-method interface,
     * even though it re-declares the {@code Object.equals} method.
     * <p>
     * The interface must be public.  No additional access checks are performed.
     * <p>
     * The resulting instance of the required type will respond to
     * invocation of the type's uniquely named method by calling
     * the given target on the incoming arguments,
     * and returning or throwing whatever the target
     * returns or throws.  The invocation will be as if by
     * {@code target.invoke}.
     * The target's type will be checked before the
     * instance is created, as if by a call to {@code asType},
     * which may result in a {@code WrongMethodTypeException}.
     * <p>
     * The uniquely named method is allowed to be multiply declared,
     * with distinct type descriptors.  (E.g., it can be overloaded,
     * or can possess bridge methods.)  All such declarations are
     * connected directly to the target method handle.
     * Argument and return types are adjusted by {@code asType}
     * for each individual declaration.
     * <p>
     * The wrapper instance will implement the requested interface
     * and its super-types, but no other single-method interfaces.
     * This means that the instance will not unexpectedly
     * pass an {@code instanceof} test for any unrequested type.
     * <p style="font-size:smaller;">
     * <em>Implementation Note:</em>
     * Therefore, each instance must implement a unique single-method interface.
     * Implementations may not bundle together
     * multiple single-method interfaces onto single implementation classes
     * in the style of {@link java.awt.AWTEventMulticaster}.
     * <p>
     * The method handle may throw an <em>undeclared exception</em>,
     * which means any checked exception (or other checked throwable)
     * not declared by the requested type's single abstract method.
     * If this happens, the throwable will be wrapped in an instance of
     * {@link java.lang.reflect.UndeclaredThrowableException UndeclaredThrowableException}
     * and thrown in that wrapped form.
     * <p>
     * Like {@link java.lang.Integer#valueOf Integer.valueOf},
     * {@code asInterfaceInstance} is a factory method whose results are defined
     * by their behavior.
     * It is not guaranteed to return a new instance for every call.
     * <p>
     * Because of the possibility of {@linkplain java.lang.reflect.Method#isBridge bridge methods}
     * and other corner cases, the interface may also have several abstract methods
     * with the same name but having distinct descriptors (types of returns and parameters).
     * In this case, all the methods are bound in common to the one given target.
     * The type check and effective {@code asType} conversion is applied to each
     * method type descriptor, and all abstract methods are bound to the target in common.
     * Beyond this type check, no further checks are made to determine that the
     * abstract methods are related in any way.
     * <p>
     * Future versions of this API may accept additional types,
     * such as abstract classes with single abstract methods.
     * Future versions of this API may also equip wrapper instances
     * with one or more additional public "marker" interfaces.
     * <p>
     * If a security manager is installed, this method is caller sensitive.
     * During any invocation of the target method handle via the returned wrapper,
     * the original creator of the wrapper (the caller) will be visible
     * to context checks requested by the security manager.
     *
     * <p>
     *  生成给定的单一方法接口的实例,它将其调用重定向到给定的方法句柄。
     * <p>
     *  单方法接口是声明唯一命名方法的接口。
     * 当确定单一方法接口的唯一命名方法时,公共的{@code Object}方法({@code toString},{@code equals},{@code hashCode})被忽略。
     * 例如,{@link java.util.Comparator}是一个单方法接口,即使它重新声明了{@code Object.equals}方法。
     * <p>
     *  接口必须是公共的。不执行其他访问检查。
     * <p>
     *  所需类型的结果实例将响应调用类型的唯一命名方法,通过调用传入的参数上的给定目标,并返回或抛出任何目标返回或抛出。调用将像{@code target.invoke}一样。
     * 目标的类型将在创建实例之前检查,就像调用{@code asType}一样,这可能会导致{@code WrongMethodTypeException}。
     * <p>
     * 唯一命名的方法允许乘法声明,具有不同的类型描述符。 (例如,它可以被重载,或者可以拥有桥接方法。)所有这样的声明直接连接到目标方法句柄。
     * 参数和返回类型由{@code asType}为每个单独的声明调整。
     * <p>
     *  包装器实例将实现请求的接口及其超类型,但没有其他单方法接口。这意味着该实例不会意外地传递任何未请求类型的{@code instanceof}测试。
     * <p style="font-size:smaller;">
     *  <em>实现注意事项：</em>因此,每个实例必须实现唯一的单一方法接口。
     * 实现不能以{@link java.awt.AWTEventMulticaster}的样式将多个单一方法接口捆绑到单个实现类上。
     * <p>
     *  方法句柄可能会抛出一个未声明的异常</em>,这意味着任何未被请求类型的单一抽象方法声明的被检查的异常(或其他被检查的throwable)。
     * 如果发生这种情况,throwable将被包装在{@link java.lang.reflect.UndeclaredThrowableException UndeclaredThrowableException}
     * 的一个实例中,并以包装形式抛出。
     *  方法句柄可能会抛出一个未声明的异常</em>,这意味着任何未被请求类型的单一抽象方法声明的被检查的异常(或其他被检查的throwable)。
     * <p>
     * 
     * @param <T> the desired type of the wrapper, a single-method interface
     * @param intfc a class object representing {@code T}
     * @param target the method handle to invoke from the wrapper
     * @return a correctly-typed wrapper for the given target
     * @throws NullPointerException if either argument is null
     * @throws IllegalArgumentException if the {@code intfc} is not a
     *         valid argument to this method
     * @throws WrongMethodTypeException if the target cannot
     *         be converted to the type required by the requested interface
     */
    // Other notes to implementors:
    // <p>
    // No stable mapping is promised between the single-method interface and
    // the implementation class C.  Over time, several implementation
    // classes might be used for the same type.
    // <p>
    // If the implementation is able
    // to prove that a wrapper of the required type
    // has already been created for a given
    // method handle, or for another method handle with the
    // same behavior, the implementation may return that wrapper in place of
    // a new wrapper.
    // <p>
    // This method is designed to apply to common use cases
    // where a single method handle must interoperate with
    // an interface that implements a function-like
    // API.  Additional variations, such as single-abstract-method classes with
    // private constructors, or interfaces with multiple but related
    // entry points, must be covered by hand-written or automatically
    // generated adapter classes.
    //
    @CallerSensitive
    public static
    <T> T asInterfaceInstance(final Class<T> intfc, final MethodHandle target) {
        if (!intfc.isInterface() || !Modifier.isPublic(intfc.getModifiers()))
            throw newIllegalArgumentException("not a public interface", intfc.getName());
        final MethodHandle mh;
        if (System.getSecurityManager() != null) {
            final Class<?> caller = Reflection.getCallerClass();
            final ClassLoader ccl = caller != null ? caller.getClassLoader() : null;
            ReflectUtil.checkProxyPackageAccess(ccl, intfc);
            mh = ccl != null ? bindCaller(target, caller) : target;
        } else {
            mh = target;
        }
        ClassLoader proxyLoader = intfc.getClassLoader();
        if (proxyLoader == null) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader(); // avoid use of BCP
            proxyLoader = cl != null ? cl : ClassLoader.getSystemClassLoader();
        }
        final Method[] methods = getSingleNameMethods(intfc);
        if (methods == null)
            throw newIllegalArgumentException("not a single-method interface", intfc.getName());
        final MethodHandle[] vaTargets = new MethodHandle[methods.length];
        for (int i = 0; i < methods.length; i++) {
            Method sm = methods[i];
            MethodType smMT = MethodType.methodType(sm.getReturnType(), sm.getParameterTypes());
            MethodHandle checkTarget = mh.asType(smMT);  // make throw WMT
            checkTarget = checkTarget.asType(checkTarget.type().changeReturnType(Object.class));
            vaTargets[i] = checkTarget.asSpreader(Object[].class, smMT.parameterCount());
        }
        final InvocationHandler ih = new InvocationHandler() {
                private Object getArg(String name) {
                    if ((Object)name == "getWrapperInstanceTarget")  return target;
                    if ((Object)name == "getWrapperInstanceType")    return intfc;
                    throw new AssertionError();
                }
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    for (int i = 0; i < methods.length; i++) {
                        if (method.equals(methods[i]))
                            return vaTargets[i].invokeExact(args);
                    }
                    if (method.getDeclaringClass() == WrapperInstance.class)
                        return getArg(method.getName());
                    if (isObjectMethod(method))
                        return callObjectMethod(proxy, method, args);
                    throw newInternalError("bad proxy method: "+method);
                }
            };

        final Object proxy;
        if (System.getSecurityManager() != null) {
            // sun.invoke.WrapperInstance is a restricted interface not accessible
            // by any non-null class loader.
            final ClassLoader loader = proxyLoader;
            proxy = AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    return Proxy.newProxyInstance(
                            loader,
                            new Class<?>[]{ intfc, WrapperInstance.class },
                            ih);
                }
            });
        } else {
            proxy = Proxy.newProxyInstance(proxyLoader,
                                           new Class<?>[]{ intfc, WrapperInstance.class },
                                           ih);
        }
        return intfc.cast(proxy);
    }

    private static MethodHandle bindCaller(MethodHandle target, Class<?> hostClass) {
        MethodHandle cbmh = MethodHandleImpl.bindCaller(target, hostClass);
        if (target.isVarargsCollector()) {
            MethodType type = cbmh.type();
            int arity = type.parameterCount();
            return cbmh.asVarargsCollector(type.parameterType(arity-1));
        }
        return cbmh;
    }

    /**
     * Determines if the given object was produced by a call to {@link #asInterfaceInstance asInterfaceInstance}.
     * <p>
     *  像{@link java.lang.Integer#valueOf Integer.valueOf},{@code asInterfaceInstance}是一个工厂方法,其结果由其行为定义。
     * 它不能保证为每个调用返回一个新的实例。
     * <p>
     * 由于{@linkplain java.lang.reflect.Method#isBridge bridge methods}和其他角落情况的可能性,接口也可能有几个具有相同名称但具有不同描述符(返回类
     * 型和参数)的抽象方法。
     * 在这种情况下,所有的方法都被共同绑定到一个给定的目标。类型检查和有效的{@code asType}转换应用于每个方法类型描述符,并且所有抽象方法都与目标绑定。
     * 除此类型检查之外,不进行进一步检查以确定抽象方法以任何方式相关。
     * <p>
     *  此API的未来版本可以接受其他类型,例如具有单个抽象方法的抽象类。此API的未来版本还可以为包装器实例提供一个或多个附加的公共"标记"接口。
     * <p>
     *  如果安装了安全管理器,则此方法对呼叫者敏感。在通过返回的包装器调用目标方法句柄期间,包装器(调用者)的原始创建者对于安全管理器请求的上下文检查是可见的。
     * 
     * 
     * @param x any reference
     * @return true if the reference is not null and points to an object produced by {@code asInterfaceInstance}
     */
    public static
    boolean isWrapperInstance(Object x) {
        return x instanceof WrapperInstance;
    }

    private static WrapperInstance asWrapperInstance(Object x) {
        try {
            if (x != null)
                return (WrapperInstance) x;
        } catch (ClassCastException ex) {
        }
        throw newIllegalArgumentException("not a wrapper instance");
    }

    /**
     * Produces or recovers a target method handle which is behaviorally
     * equivalent to the unique method of this wrapper instance.
     * The object {@code x} must have been produced by a call to {@link #asInterfaceInstance asInterfaceInstance}.
     * This requirement may be tested via {@link #isWrapperInstance isWrapperInstance}.
     * <p>
     * 
     * @param x any reference
     * @return a method handle implementing the unique method
     * @throws IllegalArgumentException if the reference x is not to a wrapper instance
     */
    public static
    MethodHandle wrapperInstanceTarget(Object x) {
        return asWrapperInstance(x).getWrapperInstanceTarget();
    }

    /**
     * Recovers the unique single-method interface type for which this wrapper instance was created.
     * The object {@code x} must have been produced by a call to {@link #asInterfaceInstance asInterfaceInstance}.
     * This requirement may be tested via {@link #isWrapperInstance isWrapperInstance}.
     * <p>
     *  确定给定对象是否由调用{@link #asInterfaceInstance asInterfaceInstance}生成​​。
     * 
     * 
     * @param x any reference
     * @return the single-method interface type for which the wrapper was created
     * @throws IllegalArgumentException if the reference x is not to a wrapper instance
     */
    public static
    Class<?> wrapperInstanceType(Object x) {
        return asWrapperInstance(x).getWrapperInstanceType();
    }

    private static
    boolean isObjectMethod(Method m) {
        switch (m.getName()) {
        case "toString":
            return (m.getReturnType() == String.class
                    && m.getParameterTypes().length == 0);
        case "hashCode":
            return (m.getReturnType() == int.class
                    && m.getParameterTypes().length == 0);
        case "equals":
            return (m.getReturnType() == boolean.class
                    && m.getParameterTypes().length == 1
                    && m.getParameterTypes()[0] == Object.class);
        }
        return false;
    }

    private static
    Object callObjectMethod(Object self, Method m, Object[] args) {
        assert(isObjectMethod(m)) : m;
        switch (m.getName()) {
        case "toString":
            return self.getClass().getName() + "@" + Integer.toHexString(self.hashCode());
        case "hashCode":
            return System.identityHashCode(self);
        case "equals":
            return (self == args[0]);
        }
        return null;
    }

    private static
    Method[] getSingleNameMethods(Class<?> intfc) {
        ArrayList<Method> methods = new ArrayList<Method>();
        String uniqueName = null;
        for (Method m : intfc.getMethods()) {
            if (isObjectMethod(m))  continue;
            if (!Modifier.isAbstract(m.getModifiers()))  continue;
            String mname = m.getName();
            if (uniqueName == null)
                uniqueName = mname;
            else if (!uniqueName.equals(mname))
                return null;  // too many abstract methods
            methods.add(m);
        }
        if (uniqueName == null)  return null;
        return methods.toArray(new Method[methods.size()]);
    }
}
