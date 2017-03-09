/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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

import com.sun.jmx.mbeanserver.MXBeanProxy;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.WeakHashMap;

/**
 * <p>{@link InvocationHandler} that forwards methods in an MBean's
 * management interface through the MBean server to the MBean.</p>
 *
 * <p>Given an {@link MBeanServerConnection}, the {@link ObjectName}
 * of an MBean within that MBean server, and a Java interface
 * <code>Intf</code> that describes the management interface of the
 * MBean using the patterns for a Standard MBean or an MXBean, this
 * class can be used to construct a proxy for the MBean.  The proxy
 * implements the interface <code>Intf</code> such that all of its
 * methods are forwarded through the MBean server to the MBean.</p>
 *
 * <p>If the {@code InvocationHandler} is for an MXBean, then the parameters of
 * a method are converted from the type declared in the MXBean
 * interface into the corresponding mapped type, and the return value
 * is converted from the mapped type into the declared type.  For
 * example, with the method<br>

 * {@code public List<String> reverse(List<String> list);}<br>

 * and given that the mapped type for {@code List<String>} is {@code
 * String[]}, a call to {@code proxy.reverse(someList)} will convert
 * {@code someList} from a {@code List<String>} to a {@code String[]},
 * call the MBean operation {@code reverse}, then convert the returned
 * {@code String[]} into a {@code List<String>}.</p>
 *
 * <p>The method Object.toString(), Object.hashCode(), or
 * Object.equals(Object), when invoked on a proxy using this
 * invocation handler, is forwarded to the MBean server as a method on
 * the proxied MBean only if it appears in one of the proxy's
 * interfaces.  For a proxy created with {@link
 * JMX#newMBeanProxy(MBeanServerConnection, ObjectName, Class)
 * JMX.newMBeanProxy} or {@link
 * JMX#newMXBeanProxy(MBeanServerConnection, ObjectName, Class)
 * JMX.newMXBeanProxy}, this means that the method must appear in the
 * Standard MBean or MXBean interface.  Otherwise these methods have
 * the following behavior:
 * <ul>
 * <li>toString() returns a string representation of the proxy
 * <li>hashCode() returns a hash code for the proxy such
 * that two equal proxies have the same hash code
 * <li>equals(Object)
 * returns true if and only if the Object argument is of the same
 * proxy class as this proxy, with an MBeanServerInvocationHandler
 * that has the same MBeanServerConnection and ObjectName; if one
 * of the {@code MBeanServerInvocationHandler}s was constructed with
 * a {@code Class} argument then the other must have been constructed
 * with the same {@code Class} for {@code equals} to return true.
 * </ul>
 *
 * <p>
 *  <p> {@ link InvocationHandler}将MBean的管理接口中的方法通过MBean服务器转发到MBean。</p>
 * 
 *  <p>给定一个{@link MBeanServerConnection},该MBean服务器中MBean的{@link ObjectName}和一个描述MBean的管理接口的Java接口<code> 
 * Intf </code>标准MBean或MXBean,此类可用于构建MBean的代理。
 * 代理实现接口<code> Intf </code>,使得它的所有方法都通过MBean服务器转发到MBean。</p>。
 * 
 *  <p>如果{@code InvocationHandler}是针对MXBean的,那么方法的参数将从MXBean接口中声明的类型转换为相应的映射类型,并将返回值从映射类型转换为声明类型。
 * 例如,使用方法<br>。
 * 
 *  {@code public List <String> reverse(List <String> list);} <br>
 * 
 *  并且给定{@code List <String>}的映射类型是{@code String []},对{@code proxy.reverse(someList)}的调用将{@code someList}
 * 从{@code List <String>}到{@code String []},调用MBean操作{@code reverse},然后将返回的{@code String []}转换为{@code List <String>}
 * 。
 * 
 * <p>当在代理上使用此调用处理程序调用时,Object.toString(),Object.hashCode()或Object.equals(Object)方法作为代理MBean上的方法转发到MBean
 * 服务器,它出现在代理的接口之一。
 * 对于使用{@link JMX#newMBeanProxy(MBeanServerConnection,ObjectName,Class)JMX.newMBeanProxy}或{@link JMX#newMXBeanProxy(MBeanServerConnection,ObjectName,Class)JMX.newMXBeanProxy}
 * 创建的代理,这意味着该方法必须出现在标准MBean或MXBean接口。
 * 否则这些方法具有以下行为：。
 * <ul>
 *  <li> toString()返回代理的字符串表示<li> hashCode()返回代理的哈希代码,使得两个相同的代理具有相同的哈希代码<li>当且仅当对象参数与​​此代理具有相同的代理类,其MBea
 * nServerInvocationHandler具有相同的MBeanServerConnection和ObjectName;如果{@code MBeanServerInvocationHandler}是
 * 用{@code Class}参数构造的,那么另一个必须用{@code equals}的{@code equals}构造成返回true。
 * </ul>
 * 
 * 
 * @since 1.5
 */
public class MBeanServerInvocationHandler implements InvocationHandler {
    /**
     * <p>Invocation handler that forwards methods through an MBean
     * server to a Standard MBean.  This constructor may be called
     * instead of relying on {@link
     * JMX#newMBeanProxy(MBeanServerConnection, ObjectName, Class)
     * JMX.newMBeanProxy}, for instance if you need to supply a
     * different {@link ClassLoader} to {@link Proxy#newProxyInstance
     * Proxy.newProxyInstance}.</p>
     *
     * <p>This constructor is not appropriate for an MXBean.  Use
     * {@link #MBeanServerInvocationHandler(MBeanServerConnection,
     * ObjectName, boolean)} for that.  This constructor is equivalent
     * to {@code new MBeanServerInvocationHandler(connection,
     * objectName, false)}.</p>
     *
     * <p>
     * <p>将方法通过MBean服务器转发到标准MBean的调用处理程序。
     * 可以调用此构造函数,而不是依赖{@link JMX#newMBeanProxy(MBeanServerConnection,ObjectName,Class)JMX.newMBeanProxy},例如,
     * 如果您需要向{@link Proxy#newProxyInstance Proxy提供不同的{@link ClassLoader} newProxyInstance}。
     * <p>将方法通过MBean服务器转发到标准MBean的调用处理程序。</p>。
     * 
     *  <p>此构造函数不适用于MXBean。
     * 使用{@link #MBeanServerInvocationHandler(MBeanServerConnection,ObjectName,boolean)}。
     * 这个构造函数等同于{@code new MBeanServerInvocationHandler(connection,objectName,false)}。</p>。
     * 
     * 
     * @param connection the MBean server connection through which all
     * methods of a proxy using this handler will be forwarded.
     *
     * @param objectName the name of the MBean within the MBean server
     * to which methods will be forwarded.
     */
    public MBeanServerInvocationHandler(MBeanServerConnection connection,
                                        ObjectName objectName) {

        this(connection, objectName, false);
    }

    /**
     * <p>Invocation handler that can forward methods through an MBean
     * server to a Standard MBean or MXBean.  This constructor may be called
     * instead of relying on {@link
     * JMX#newMXBeanProxy(MBeanServerConnection, ObjectName, Class)
     * JMX.newMXBeanProxy}, for instance if you need to supply a
     * different {@link ClassLoader} to {@link Proxy#newProxyInstance
     * Proxy.newProxyInstance}.</p>
     *
     * <p>
     *  <p>可以通过MBean服务器将方法转发到标准MBean或MXBean的调用处理程序。
     * 可以调用此构造函数而不是依赖{@link JMX#newMXBeanProxy(MBeanServerConnection,ObjectName,Class)JMX.newMXBeanProxy},例如
     * ,如果您需要向{@link Proxy#newProxyInstance Proxy提供一个不同的{@link ClassLoader} newProxyInstance}。
     *  <p>可以通过MBean服务器将方法转发到标准MBean或MXBean的调用处理程序。</p>。
     * 
     * 
     * @param connection the MBean server connection through which all
     * methods of a proxy using this handler will be forwarded.
     *
     * @param objectName the name of the MBean within the MBean server
     * to which methods will be forwarded.
     *
     * @param isMXBean if true, the proxy is for an {@link MXBean}, and
     * appropriate mappings will be applied to method parameters and return
     * values.
     *
     * @since 1.6
     */
    public MBeanServerInvocationHandler(MBeanServerConnection connection,
                                        ObjectName objectName,
                                        boolean isMXBean) {
        if (connection == null) {
            throw new IllegalArgumentException("Null connection");
        }
        if (objectName == null) {
            throw new IllegalArgumentException("Null object name");
        }
        this.connection = connection;
        this.objectName = objectName;
        this.isMXBean = isMXBean;
    }

    /**
     * <p>The MBean server connection through which the methods of
     * a proxy using this handler are forwarded.</p>
     *
     * <p>
     *  <p> MBean服务器连接,通过该连接,转发使用此处理程序的代理的方法。</p>
     * 
     * 
     * @return the MBean server connection.
     *
     * @since 1.6
     */
    public MBeanServerConnection getMBeanServerConnection() {
        return connection;
    }

    /**
     * <p>The name of the MBean within the MBean server to which methods
     * are forwarded.
     *
     * <p>
     *  <p> MBean服务器中要转发方法的MBean的名称。
     * 
     * 
     * @return the object name.
     *
     * @since 1.6
     */
    public ObjectName getObjectName() {
        return objectName;
    }

    /**
     * <p>If true, the proxy is for an MXBean, and appropriate mappings
     * are applied to method parameters and return values.
     *
     * <p>
     *  <p>如果为true,则代理针对MXBean,并将适当的映射应用于方法参数和返回值。
     * 
     * 
     * @return whether the proxy is for an MXBean.
     *
     * @since 1.6
     */
    public boolean isMXBean() {
        return isMXBean;
    }

    /**
     * <p>Return a proxy that implements the given interface by
     * forwarding its methods through the given MBean server to the
     * named MBean.  As of 1.6, the methods {@link
     * JMX#newMBeanProxy(MBeanServerConnection, ObjectName, Class)} and
     * {@link JMX#newMBeanProxy(MBeanServerConnection, ObjectName, Class,
     * boolean)} are preferred to this method.</p>
     *
     * <p>This method is equivalent to {@link Proxy#newProxyInstance
     * Proxy.newProxyInstance}<code>(interfaceClass.getClassLoader(),
     * interfaces, handler)</code>.  Here <code>handler</code> is the
     * result of {@link #MBeanServerInvocationHandler new
     * MBeanServerInvocationHandler(connection, objectName)}, and
     * <code>interfaces</code> is an array that has one element if
     * <code>notificationBroadcaster</code> is false and two if it is
     * true.  The first element of <code>interfaces</code> is
     * <code>interfaceClass</code> and the second, if present, is
     * <code>NotificationEmitter.class</code>.
     *
     * <p>
     * <p>通过将其方法通过给定的MBean服务器转发到指定的MBean,返回实现给定接口的代理。
     * 从1.6开始,此方法优先使用{@link JMX#newMBeanProxy(MBeanServerConnection,ObjectName,Class)}和{@link JMX#newMBeanProxy(MBeanServerConnection,ObjectName,Class,boolean)}
     * 。
     * <p>通过将其方法通过给定的MBean服务器转发到指定的MBean,返回实现给定接口的代理。</p>。
     * 
     *  <p>此方法相当于{@link Proxy#newProxyInstance Proxy.newProxyInstance} <code>(interfaceClass.getClassLoader(
     * ),interfaces,handler)</code>。
     * 这里<code> handler </code>是{@link #MBeanServerInvocationHandler new MBeanServerInvocationHandler(connection,objectName)}
     * 的结果,<code> interfaces </code>是一个数组,如果<code> notificationBroadcaster <代码>为false,如果为真则为2。
     *  <code> interfaces </code>的第一个元素是<code> interfaceClass </code>,第二个元素(如果存在)是<code> NotificationEmitter
     * .class </code>。
     * 
     * 
     * @param connection the MBean server to forward to.
     * @param objectName the name of the MBean within
     * <code>connection</code> to forward to.
     * @param interfaceClass the management interface that the MBean
     * exports, which will also be implemented by the returned proxy.
     * @param notificationBroadcaster make the returned proxy
     * implement {@link NotificationEmitter} by forwarding its methods
     * via <code>connection</code>. A call to {@link
     * NotificationBroadcaster#addNotificationListener} on the proxy will
     * result in a call to {@link
     * MBeanServerConnection#addNotificationListener(ObjectName,
     * NotificationListener, NotificationFilter, Object)}, and likewise
     * for the other methods of {@link NotificationBroadcaster} and {@link
     * NotificationEmitter}.
     *
     * @param <T> allows the compiler to know that if the {@code
     * interfaceClass} parameter is {@code MyMBean.class}, for example,
     * then the return type is {@code MyMBean}.
     *
     * @return the new proxy instance.
     *
     * @see JMX#newMBeanProxy(MBeanServerConnection, ObjectName, Class, boolean)
     */
    public static <T> T newProxyInstance(MBeanServerConnection connection,
                                         ObjectName objectName,
                                         Class<T> interfaceClass,
                                         boolean notificationBroadcaster) {
        return JMX.newMBeanProxy(connection, objectName, interfaceClass, notificationBroadcaster);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        final Class<?> methodClass = method.getDeclaringClass();

        if (methodClass.equals(NotificationBroadcaster.class)
            || methodClass.equals(NotificationEmitter.class))
            return invokeBroadcasterMethod(proxy, method, args);

        // local or not: equals, toString, hashCode
        if (shouldDoLocally(proxy, method))
            return doLocally(proxy, method, args);

        try {
            if (isMXBean()) {
                MXBeanProxy p = findMXBeanProxy(methodClass);
                return p.invoke(connection, objectName, method, args);
            } else {
                final String methodName = method.getName();
                final Class<?>[] paramTypes = method.getParameterTypes();
                final Class<?> returnType = method.getReturnType();

                /* Inexplicably, InvocationHandler specifies that args is null
                   when the method takes no arguments rather than a
                /* <p>
                /*  当方法不使用参数而不是a
                /* 
                /* 
                   zero-length array.  */
                final int nargs = (args == null) ? 0 : args.length;

                if (methodName.startsWith("get")
                    && methodName.length() > 3
                    && nargs == 0
                    && !returnType.equals(Void.TYPE)) {
                    return connection.getAttribute(objectName,
                        methodName.substring(3));
                }

                if (methodName.startsWith("is")
                    && methodName.length() > 2
                    && nargs == 0
                    && (returnType.equals(Boolean.TYPE)
                    || returnType.equals(Boolean.class))) {
                    return connection.getAttribute(objectName,
                        methodName.substring(2));
                }

                if (methodName.startsWith("set")
                    && methodName.length() > 3
                    && nargs == 1
                    && returnType.equals(Void.TYPE)) {
                    Attribute attr = new Attribute(methodName.substring(3), args[0]);
                    connection.setAttribute(objectName, attr);
                    return null;
                }

                final String[] signature = new String[paramTypes.length];
                for (int i = 0; i < paramTypes.length; i++)
                    signature[i] = paramTypes[i].getName();
                return connection.invoke(objectName, methodName,
                                         args, signature);
            }
        } catch (MBeanException e) {
            throw e.getTargetException();
        } catch (RuntimeMBeanException re) {
            throw re.getTargetException();
        } catch (RuntimeErrorException rre) {
            throw rre.getTargetError();
        }
        /* The invoke may fail because it can't get to the MBean, with
           one of the these exceptions declared by
           MBeanServerConnection.invoke:
           - RemoteException: can't talk to MBeanServer;
           - InstanceNotFoundException: objectName is not registered;
           - ReflectionException: objectName is registered but does not
             have the method being invoked.
           In all of these cases, the exception will be wrapped by the
           proxy mechanism in an UndeclaredThrowableException unless
           it happens to be declared in the "throws" clause of the
           method being invoked on the proxy.
        /* <p>
        /*  由MBeanServerConnection.invoke声明的这些异常之一： -  RemoteException：无法与MBeanServer通信; -  InstanceNotFoundExce
        /* ption：objectName未注册; -  ReflectionException：objectName已注册,但没有调用该方法。
        /* 在所有这些情况下,异常将由UndeclaredThrowableException中的代理机制包装,除非它恰好在代理上调用的方法的"throws"子句中声明。
        /* 
         */
    }

    private static MXBeanProxy findMXBeanProxy(Class<?> mxbeanInterface) {
        synchronized (mxbeanProxies) {
            WeakReference<MXBeanProxy> proxyRef =
                    mxbeanProxies.get(mxbeanInterface);
            MXBeanProxy p = (proxyRef == null) ? null : proxyRef.get();
            if (p == null) {
                try {
                    p = new MXBeanProxy(mxbeanInterface);
                } catch (IllegalArgumentException e) {
                    String msg = "Cannot make MXBean proxy for " +
                            mxbeanInterface.getName() + ": " + e.getMessage();
                    IllegalArgumentException iae =
                            new IllegalArgumentException(msg, e.getCause());
                    iae.setStackTrace(e.getStackTrace());
                    throw iae;
                }
                mxbeanProxies.put(mxbeanInterface,
                                  new WeakReference<MXBeanProxy>(p));
            }
            return p;
        }
    }
    private static final WeakHashMap<Class<?>, WeakReference<MXBeanProxy>>
            mxbeanProxies = new WeakHashMap<Class<?>, WeakReference<MXBeanProxy>>();

    private Object invokeBroadcasterMethod(Object proxy, Method method,
                                           Object[] args) throws Exception {
        final String methodName = method.getName();
        final int nargs = (args == null) ? 0 : args.length;

        if (methodName.equals("addNotificationListener")) {
            /* The various throws of IllegalArgumentException here
               should not happen, since we know what the methods in
               NotificationBroadcaster and NotificationEmitter
            /* <p>
            /* 不应该发生,因为我们知道NotificationBroadcaster和NotificationEmitter中的方法
            /* 
            /* 
               are.  */
            if (nargs != 3) {
                final String msg =
                    "Bad arg count to addNotificationListener: " + nargs;
                throw new IllegalArgumentException(msg);
            }
            /* Other inconsistencies will produce ClassCastException
            /* <p>
            /* 
               below.  */

            NotificationListener listener = (NotificationListener) args[0];
            NotificationFilter filter = (NotificationFilter) args[1];
            Object handback = args[2];
            connection.addNotificationListener(objectName,
                                               listener,
                                               filter,
                                               handback);
            return null;

        } else if (methodName.equals("removeNotificationListener")) {

            /* NullPointerException if method with no args, but that
            /* <p>
            /* 
               shouldn't happen because removeNL does have args.  */
            NotificationListener listener = (NotificationListener) args[0];

            switch (nargs) {
            case 1:
                connection.removeNotificationListener(objectName, listener);
                return null;

            case 3:
                NotificationFilter filter = (NotificationFilter) args[1];
                Object handback = args[2];
                connection.removeNotificationListener(objectName,
                                                      listener,
                                                      filter,
                                                      handback);
                return null;

            default:
                final String msg =
                    "Bad arg count to removeNotificationListener: " + nargs;
                throw new IllegalArgumentException(msg);
            }

        } else if (methodName.equals("getNotificationInfo")) {

            if (args != null) {
                throw new IllegalArgumentException("getNotificationInfo has " +
                                                   "args");
            }

            MBeanInfo info = connection.getMBeanInfo(objectName);
            return info.getNotifications();

        } else {
            throw new IllegalArgumentException("Bad method name: " +
                                               methodName);
        }
    }

    private boolean shouldDoLocally(Object proxy, Method method) {
        final String methodName = method.getName();
        if ((methodName.equals("hashCode") || methodName.equals("toString"))
            && method.getParameterTypes().length == 0
            && isLocal(proxy, method))
            return true;
        if (methodName.equals("equals")
            && Arrays.equals(method.getParameterTypes(),
                             new Class<?>[] {Object.class})
            && isLocal(proxy, method))
            return true;
        return false;
    }

    private Object doLocally(Object proxy, Method method, Object[] args) {
        final String methodName = method.getName();

        if (methodName.equals("equals")) {

            if (this == args[0]) {
                return true;
            }

            if (!(args[0] instanceof Proxy)) {
                return false;
            }

            final InvocationHandler ihandler =
                Proxy.getInvocationHandler(args[0]);

            if (ihandler == null ||
                !(ihandler instanceof MBeanServerInvocationHandler)) {
                return false;
            }

            final MBeanServerInvocationHandler handler =
                (MBeanServerInvocationHandler)ihandler;

            return connection.equals(handler.connection) &&
                objectName.equals(handler.objectName) &&
                proxy.getClass().equals(args[0].getClass());
        } else if (methodName.equals("toString")) {
            return (isMXBean() ? "MX" : "M") + "BeanProxy(" +
                connection + "[" + objectName + "])";
        } else if (methodName.equals("hashCode")) {
            return objectName.hashCode()+connection.hashCode();
        }

        throw new RuntimeException("Unexpected method name: " + methodName);
    }

    private static boolean isLocal(Object proxy, Method method) {
        final Class<?>[] interfaces = proxy.getClass().getInterfaces();
        if(interfaces == null) {
            return true;
        }

        final String methodName = method.getName();
        final Class<?>[] params = method.getParameterTypes();
        for (Class<?> intf : interfaces) {
            try {
                intf.getMethod(methodName, params);
                return false; // found method in one of our interfaces
            } catch (NoSuchMethodException nsme) {
                // OK.
            }
        }

        return true;  // did not find in any interface
    }

    private final MBeanServerConnection connection;
    private final ObjectName objectName;
    private final boolean isMXBean;
}
