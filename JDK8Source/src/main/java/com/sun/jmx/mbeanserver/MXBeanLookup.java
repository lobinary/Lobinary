/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2008, Oracle and/or its affiliates. All rights reserved.
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
import java.util.Map;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import javax.management.InstanceAlreadyExistsException;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.openmbean.OpenDataException;

/**
/* <p>
/* 
 * @since 1.6
 */

/*
 * This class handles the mapping between MXBean references and
 * ObjectNames.  Consider an MXBean interface like this:
 *
 * public interface ModuleMXBean {
 *     ProductMXBean getProduct();
 *     void setProduct(ProductMXBean product);
 * }
 *
 * This defines an attribute called "Product" whose originalType will
 * be ProductMXBean and whose openType will be ObjectName.  The
 * mapping happens as follows.
 *
 * When the MXBean's getProduct method is called, it is supposed to
 * return a reference to another MXBean, or a proxy for another
 * MXBean.  The MXBean layer has to convert this into an ObjectName.
 * If it's a reference to another MXBean, it needs to be able to look
 * up the name under which that MXBean has been registered in this
 * MBeanServer; this is the purpose of the mxbeanToObjectName map.  If
 * it's a proxy, it can check that the MBeanServer matches and if so
 * extract the ObjectName from the proxy.
 *
 * When the setProduct method is called on a proxy for this MXBean,
 * the argument can be either an MXBean reference (only really logical
 * if the proxy has a local MBeanServer) or another proxy.  So the
 * mapping logic is the same as for getProduct on the MXBean.
 *
 * When the MXBean's setProduct method is called, it needs to convert
 * the ObjectName into an object implementing the ProductMXBean
 * interface.  We could have a lookup table that reverses
 * mxbeanToObjectName, but this could violate the general JMX property
 * that you cannot obtain a reference to an MBean object.  So we
 * always use a proxy for this.  However we do have an
 * objectNameToProxy map that allows us to reuse proxy instances.
 *
 * When the getProduct method is called on a proxy for this MXBean, it
 * must convert the returned ObjectName into an instance of
 * ProductMXBean.  Again it can do this by making a proxy.
 *
 * From the above, it is clear that the logic for getX on an MXBean is
 * the same as for setX on a proxy, and vice versa.
 * <p>
 *  此类处理MXBean引用和ObjectNames之间的映射。考虑一个像这样的MXBean接口：
 * 
 *  public interface ModuleMXBean {ProductMXBean getProduct(); void setProduct(ProductMXBean product); }
 * }。
 * 
 *  这定义了一个称为"Product"的属性,其原始类型将是ProductMXBean,其openType将是ObjectName。映射如下进行。
 * 
 *  当调用MXBean的getProduct方法时,应该返回对另一个MXBean的引用,或者返回另一个MXBean的代理。 MXBean层必须将其转换为ObjectName。
 * 如果它是对另一个MXBean的引用,它需要能够查找MXBean在此MBeanServer中注册的名称;这是mxbeanToObjectName映射的目的。
 * 如果它是一个代理,它可以检查MBeanServer匹配,如果是,从代理提取ObjectName。
 * 
 *  当为此MXBean的代理调用setProduct方法时,参数可以是MXBean引用(如果代理具有本地MBeanServer,则只有真正的逻辑)或另一个代理。
 * 所以映射逻辑与MXBean上的getProduct相同。
 */
public class MXBeanLookup {
    private MXBeanLookup(MBeanServerConnection mbsc) {
        this.mbsc = mbsc;
    }

    static MXBeanLookup lookupFor(MBeanServerConnection mbsc) {
        synchronized (mbscToLookup) {
            WeakReference<MXBeanLookup> weakLookup = mbscToLookup.get(mbsc);
            MXBeanLookup lookup = (weakLookup == null) ? null : weakLookup.get();
            if (lookup == null) {
                lookup = new MXBeanLookup(mbsc);
                mbscToLookup.put(mbsc, new WeakReference<MXBeanLookup>(lookup));
            }
            return lookup;
        }
    }

    synchronized <T> T objectNameToMXBean(ObjectName name, Class<T> type) {
        WeakReference<Object> wr = objectNameToProxy.get(name);
        if (wr != null) {
            Object proxy = wr.get();
            if (type.isInstance(proxy))
                return type.cast(proxy);
        }
        T proxy = JMX.newMXBeanProxy(mbsc, name, type);
        objectNameToProxy.put(name, new WeakReference<Object>(proxy));
        return proxy;
    }

    synchronized ObjectName mxbeanToObjectName(Object mxbean)
    throws OpenDataException {
        String wrong;
        if (mxbean instanceof Proxy) {
            InvocationHandler ih = Proxy.getInvocationHandler(mxbean);
            if (ih instanceof MBeanServerInvocationHandler) {
                MBeanServerInvocationHandler mbsih =
                        (MBeanServerInvocationHandler) ih;
                if (mbsih.getMBeanServerConnection().equals(mbsc))
                    return mbsih.getObjectName();
                else
                    wrong = "proxy for a different MBeanServer";
            } else
                wrong = "not a JMX proxy";
        } else {
            ObjectName name = mxbeanToObjectName.get(mxbean);
            if (name != null)
                return name;
            wrong = "not an MXBean registered in this MBeanServer";
        }
        String s = (mxbean == null) ?
            "null" : "object of type " + mxbean.getClass().getName();
        throw new OpenDataException(
                "Could not convert " + s + " to an ObjectName: " + wrong);
        // Message will be strange if mxbean is null but it is not
        // supposed to be.
    }

    synchronized void addReference(ObjectName name, Object mxbean)
    throws InstanceAlreadyExistsException {
        ObjectName existing = mxbeanToObjectName.get(mxbean);
        if (existing != null) {
            String multiname = AccessController.doPrivileged(
                    new GetPropertyAction("jmx.mxbean.multiname"));
            if (!"true".equalsIgnoreCase(multiname)) {
                throw new InstanceAlreadyExistsException(
                        "MXBean already registered with name " + existing);
            }
        }
        mxbeanToObjectName.put(mxbean, name);
    }

    synchronized boolean removeReference(ObjectName name, Object mxbean) {
        if (name.equals(mxbeanToObjectName.get(mxbean))) {
            mxbeanToObjectName.remove(mxbean);
            return true;
        } else
            return false;
        /* removeReference can be called when the above condition fails,
         * notably if you try to register the same MXBean twice.
         * <p>
         * 
         * 当MXBean的setProduct方法被调用时,它需要将ObjectName转换为实现ProductMXBean接口的对象。
         * 我们可以有一个查找表反转mxbeanToObjectName,但这可能违反一般的JMX属性,你不能获得对MBean对象的引用。所以我们总是使用代理。
         * 但是我们有一个objectNameToProxy映射,允许我们重用代理实例。
         * 
         *  当为此MXBean的代理调用getProduct方法时,它必须将返回的ObjectName转换为ProductMXBean的实例。再次,它可以通过做一个代理。
         */
    }

    static MXBeanLookup getLookup() {
        return currentLookup.get();
    }

    static void setLookup(MXBeanLookup lookup) {
        currentLookup.set(lookup);
    }

    private static final ThreadLocal<MXBeanLookup> currentLookup =
            new ThreadLocal<MXBeanLookup>();

    private final MBeanServerConnection mbsc;
    private final WeakIdentityHashMap<Object, ObjectName>
        mxbeanToObjectName = WeakIdentityHashMap.make();
    private final Map<ObjectName, WeakReference<Object>>
        objectNameToProxy = newMap();
    private static final WeakIdentityHashMap<MBeanServerConnection,
                                             WeakReference<MXBeanLookup>>
        mbscToLookup = WeakIdentityHashMap.make();
}
