/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;

import java.lang.ref.Reference;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * An EventSetDescriptor describes a group of events that a given Java
 * bean fires.
 * <P>
 * The given group of events are all delivered as method calls on a single
 * event listener interface, and an event listener object can be registered
 * via a call on a registration method supplied by the event source.
 * <p>
 *  EventSetDescriptor描述给定Java bean触发的一组事件。
 * <P>
 *  给定的事件组都作为单个事件监听器接口上的方法调用来传递,并且可以通过对由事件源提供的注册方法的调用来注册事件监听器对象。
 * 
 */
public class EventSetDescriptor extends FeatureDescriptor {

    private MethodDescriptor[] listenerMethodDescriptors;
    private MethodDescriptor addMethodDescriptor;
    private MethodDescriptor removeMethodDescriptor;
    private MethodDescriptor getMethodDescriptor;

    private Reference<Method[]> listenerMethodsRef;
    private Reference<? extends Class<?>> listenerTypeRef;

    private boolean unicast;
    private boolean inDefaultEventSet = true;

    /**
     * Creates an <TT>EventSetDescriptor</TT> assuming that you are
     * following the most simple standard design pattern where a named
     * event &quot;fred&quot; is (1) delivered as a call on the single method of
     * interface FredListener, (2) has a single argument of type FredEvent,
     * and (3) where the FredListener may be registered with a call on an
     * addFredListener method of the source component and removed with a
     * call on a removeFredListener method.
     *
     * <p>
     *  创建一个<TT> EventSetDescriptor </TT>,假设您遵循最简单的标准设计模式,其中命名事件"fred"是(1)作为对接口FredListener的单个方法的调用而传递的,(2)具
     * 有类型FredEvent的单个参数,并且(3)其中FredListener可以对对源组件的addFredListener方法的调用进行注册,与对removeFredListener方法的调用。
     * 
     * 
     * @param sourceClass  The class firing the event.
     * @param eventSetName  The programmatic name of the event.  E.g. &quot;fred&quot;.
     *          Note that this should normally start with a lower-case character.
     * @param listenerType  The target interface that events
     *          will get delivered to.
     * @param listenerMethodName  The method that will get called when the event gets
     *          delivered to its target listener interface.
     * @exception IntrospectionException if an exception occurs during
     *              introspection.
     */
    public EventSetDescriptor(Class<?> sourceClass, String eventSetName,
                Class<?> listenerType, String listenerMethodName)
                throws IntrospectionException {
        this(sourceClass, eventSetName, listenerType,
             new String[] { listenerMethodName },
             Introspector.ADD_PREFIX + getListenerClassName(listenerType),
             Introspector.REMOVE_PREFIX + getListenerClassName(listenerType),
             Introspector.GET_PREFIX + getListenerClassName(listenerType) + "s");

        String eventName = NameGenerator.capitalize(eventSetName) + "Event";
        Method[] listenerMethods = getListenerMethods();
        if (listenerMethods.length > 0) {
            Class[] args = getParameterTypes(getClass0(), listenerMethods[0]);
            // Check for EventSet compliance. Special case for vetoableChange. See 4529996
            if (!"vetoableChange".equals(eventSetName) && !args[0].getName().endsWith(eventName)) {
                throw new IntrospectionException("Method \"" + listenerMethodName +
                                                 "\" should have argument \"" +
                                                 eventName + "\"");
            }
        }
    }

    private static String getListenerClassName(Class<?> cls) {
        String className = cls.getName();
        return className.substring(className.lastIndexOf('.') + 1);
    }

    /**
     * Creates an <TT>EventSetDescriptor</TT> from scratch using
     * string names.
     *
     * <p>
     *  使用字符串名称从头开始创建<TT> EventSetDescriptor </TT>。
     * 
     * 
     * @param sourceClass  The class firing the event.
     * @param eventSetName The programmatic name of the event set.
     *          Note that this should normally start with a lower-case character.
     * @param listenerType  The Class of the target interface that events
     *          will get delivered to.
     * @param listenerMethodNames The names of the methods that will get called
     *          when the event gets delivered to its target listener interface.
     * @param addListenerMethodName  The name of the method on the event source
     *          that can be used to register an event listener object.
     * @param removeListenerMethodName  The name of the method on the event source
     *          that can be used to de-register an event listener object.
     * @exception IntrospectionException if an exception occurs during
     *              introspection.
     */
    public EventSetDescriptor(Class<?> sourceClass,
                String eventSetName,
                Class<?> listenerType,
                String listenerMethodNames[],
                String addListenerMethodName,
                String removeListenerMethodName)
                throws IntrospectionException {
        this(sourceClass, eventSetName, listenerType,
             listenerMethodNames, addListenerMethodName,
             removeListenerMethodName, null);
    }

    /**
     * This constructor creates an EventSetDescriptor from scratch using
     * string names.
     *
     * <p>
     *  此构造函数使用字符串名称从头开始创建一个EventSetDescriptor。
     * 
     * 
     * @param sourceClass  The class firing the event.
     * @param eventSetName The programmatic name of the event set.
     *          Note that this should normally start with a lower-case character.
     * @param listenerType  The Class of the target interface that events
     *          will get delivered to.
     * @param listenerMethodNames The names of the methods that will get called
     *          when the event gets delivered to its target listener interface.
     * @param addListenerMethodName  The name of the method on the event source
     *          that can be used to register an event listener object.
     * @param removeListenerMethodName  The name of the method on the event source
     *          that can be used to de-register an event listener object.
     * @param getListenerMethodName The method on the event source that
     *          can be used to access the array of event listener objects.
     * @exception IntrospectionException if an exception occurs during
     *              introspection.
     * @since 1.4
     */
    public EventSetDescriptor(Class<?> sourceClass,
                String eventSetName,
                Class<?> listenerType,
                String listenerMethodNames[],
                String addListenerMethodName,
                String removeListenerMethodName,
                String getListenerMethodName)
                throws IntrospectionException {
        if (sourceClass == null || eventSetName == null || listenerType == null) {
            throw new NullPointerException();
        }
        setName(eventSetName);
        setClass0(sourceClass);
        setListenerType(listenerType);

        Method[] listenerMethods = new Method[listenerMethodNames.length];
        for (int i = 0; i < listenerMethodNames.length; i++) {
            // Check for null names
            if (listenerMethodNames[i] == null) {
                throw new NullPointerException();
            }
            listenerMethods[i] = getMethod(listenerType, listenerMethodNames[i], 1);
        }
        setListenerMethods(listenerMethods);

        setAddListenerMethod(getMethod(sourceClass, addListenerMethodName, 1));
        setRemoveListenerMethod(getMethod(sourceClass, removeListenerMethodName, 1));

        // Be more forgiving of not finding the getListener method.
        Method method = Introspector.findMethod(sourceClass, getListenerMethodName, 0);
        if (method != null) {
            setGetListenerMethod(method);
        }
    }

    private static Method getMethod(Class<?> cls, String name, int args)
        throws IntrospectionException {
        if (name == null) {
            return null;
        }
        Method method = Introspector.findMethod(cls, name, args);
        if ((method == null) || Modifier.isStatic(method.getModifiers())) {
            throw new IntrospectionException("Method not found: " + name +
                                             " on class " + cls.getName());
        }
        return method;
    }

    /**
     * Creates an <TT>EventSetDescriptor</TT> from scratch using
     * <TT>java.lang.reflect.Method</TT> and <TT>java.lang.Class</TT> objects.
     *
     * <p>
     *  使用<TT> java.lang.reflect.Method </TT>和<TT> java.lang.Class </TT>对象从头开始创建<TT> EventSetDescriptor </TT>
     * 。
     * 
     * 
     * @param eventSetName The programmatic name of the event set.
     * @param listenerType The Class for the listener interface.
     * @param listenerMethods  An array of Method objects describing each
     *          of the event handling methods in the target listener.
     * @param addListenerMethod  The method on the event source
     *          that can be used to register an event listener object.
     * @param removeListenerMethod  The method on the event source
     *          that can be used to de-register an event listener object.
     * @exception IntrospectionException if an exception occurs during
     *              introspection.
     */
    public EventSetDescriptor(String eventSetName,
                Class<?> listenerType,
                Method listenerMethods[],
                Method addListenerMethod,
                Method removeListenerMethod)
                throws IntrospectionException {
        this(eventSetName, listenerType, listenerMethods,
             addListenerMethod, removeListenerMethod, null);
    }

    /**
     * This constructor creates an EventSetDescriptor from scratch using
     * java.lang.reflect.Method and java.lang.Class objects.
     *
     * <p>
     *  此构造函数使用java.lang.reflect.Method和java.lang.Class对象从头开始创建一个EventSetDescriptor。
     * 
     * 
     * @param eventSetName The programmatic name of the event set.
     * @param listenerType The Class for the listener interface.
     * @param listenerMethods  An array of Method objects describing each
     *          of the event handling methods in the target listener.
     * @param addListenerMethod  The method on the event source
     *          that can be used to register an event listener object.
     * @param removeListenerMethod  The method on the event source
     *          that can be used to de-register an event listener object.
     * @param getListenerMethod The method on the event source
     *          that can be used to access the array of event listener objects.
     * @exception IntrospectionException if an exception occurs during
     *              introspection.
     * @since 1.4
     */
    public EventSetDescriptor(String eventSetName,
                Class<?> listenerType,
                Method listenerMethods[],
                Method addListenerMethod,
                Method removeListenerMethod,
                Method getListenerMethod)
                throws IntrospectionException {
        setName(eventSetName);
        setListenerMethods(listenerMethods);
        setAddListenerMethod(addListenerMethod);
        setRemoveListenerMethod( removeListenerMethod);
        setGetListenerMethod(getListenerMethod);
        setListenerType(listenerType);
    }

    /**
     * Creates an <TT>EventSetDescriptor</TT> from scratch using
     * <TT>java.lang.reflect.MethodDescriptor</TT> and <TT>java.lang.Class</TT>
     *  objects.
     *
     * <p>
     *  使用<TT> java.lang.reflect.MethodDescriptor </TT>和<TT> java.lang.Class </TT>对象从头开始创建<TT> EventSetDescr
     * iptor </TT>。
     * 
     * 
     * @param eventSetName The programmatic name of the event set.
     * @param listenerType The Class for the listener interface.
     * @param listenerMethodDescriptors  An array of MethodDescriptor objects
     *           describing each of the event handling methods in the
     *           target listener.
     * @param addListenerMethod  The method on the event source
     *          that can be used to register an event listener object.
     * @param removeListenerMethod  The method on the event source
     *          that can be used to de-register an event listener object.
     * @exception IntrospectionException if an exception occurs during
     *              introspection.
     */
    public EventSetDescriptor(String eventSetName,
                Class<?> listenerType,
                MethodDescriptor listenerMethodDescriptors[],
                Method addListenerMethod,
                Method removeListenerMethod)
                throws IntrospectionException {
        setName(eventSetName);
        this.listenerMethodDescriptors = (listenerMethodDescriptors != null)
                ? listenerMethodDescriptors.clone()
                : null;
        setAddListenerMethod(addListenerMethod);
        setRemoveListenerMethod(removeListenerMethod);
        setListenerType(listenerType);
    }

    /**
     * Gets the <TT>Class</TT> object for the target interface.
     *
     * <p>
     *  获取目标接口的<TT>类</TT>对象。
     * 
     * 
     * @return The Class object for the target interface that will
     * get invoked when the event is fired.
     */
    public Class<?> getListenerType() {
        return (this.listenerTypeRef != null)
                ? this.listenerTypeRef.get()
                : null;
    }

    private void setListenerType(Class<?> cls) {
        this.listenerTypeRef = getWeakReference(cls);
    }

    /**
     * Gets the methods of the target listener interface.
     *
     * <p>
     * 获取目标侦听器接口的方法。
     * 
     * 
     * @return An array of <TT>Method</TT> objects for the target methods
     * within the target listener interface that will get called when
     * events are fired.
     */
    public synchronized Method[] getListenerMethods() {
        Method[] methods = getListenerMethods0();
        if (methods == null) {
            if (listenerMethodDescriptors != null) {
                methods = new Method[listenerMethodDescriptors.length];
                for (int i = 0; i < methods.length; i++) {
                    methods[i] = listenerMethodDescriptors[i].getMethod();
                }
            }
            setListenerMethods(methods);
        }
        return methods;
    }

    private void setListenerMethods(Method[] methods) {
        if (methods == null) {
            return;
        }
        if (listenerMethodDescriptors == null) {
            listenerMethodDescriptors = new MethodDescriptor[methods.length];
            for (int i = 0; i < methods.length; i++) {
                listenerMethodDescriptors[i] = new MethodDescriptor(methods[i]);
            }
        }
        this.listenerMethodsRef = getSoftReference(methods);
    }

    private Method[] getListenerMethods0() {
        return (this.listenerMethodsRef != null)
                ? this.listenerMethodsRef.get()
                : null;
    }

    /**
     * Gets the <code>MethodDescriptor</code>s of the target listener interface.
     *
     * <p>
     *  获取目标侦听器接口的<code> MethodDescriptor </code>。
     * 
     * 
     * @return An array of <code>MethodDescriptor</code> objects for the target methods
     * within the target listener interface that will get called when
     * events are fired.
     */
    public synchronized MethodDescriptor[] getListenerMethodDescriptors() {
        return (this.listenerMethodDescriptors != null)
                ? this.listenerMethodDescriptors.clone()
                : null;
    }

    /**
     * Gets the method used to add event listeners.
     *
     * <p>
     *  获取用于添加事件侦听器的方法。
     * 
     * 
     * @return The method used to register a listener at the event source.
     */
    public synchronized Method getAddListenerMethod() {
        return getMethod(this.addMethodDescriptor);
    }

    private synchronized void setAddListenerMethod(Method method) {
        if (method == null) {
            return;
        }
        if (getClass0() == null) {
            setClass0(method.getDeclaringClass());
        }
        addMethodDescriptor = new MethodDescriptor(method);
        setTransient(method.getAnnotation(Transient.class));
    }

    /**
     * Gets the method used to remove event listeners.
     *
     * <p>
     *  获取用于删除事件侦听器的方法。
     * 
     * 
     * @return The method used to remove a listener at the event source.
     */
    public synchronized Method getRemoveListenerMethod() {
        return getMethod(this.removeMethodDescriptor);
    }

    private synchronized void setRemoveListenerMethod(Method method) {
        if (method == null) {
            return;
        }
        if (getClass0() == null) {
            setClass0(method.getDeclaringClass());
        }
        removeMethodDescriptor = new MethodDescriptor(method);
        setTransient(method.getAnnotation(Transient.class));
    }

    /**
     * Gets the method used to access the registered event listeners.
     *
     * <p>
     *  获取用于访问已注册事件侦听器的方法。
     * 
     * 
     * @return The method used to access the array of listeners at the event
     *         source or null if it doesn't exist.
     * @since 1.4
     */
    public synchronized Method getGetListenerMethod() {
        return getMethod(this.getMethodDescriptor);
    }

    private synchronized void setGetListenerMethod(Method method) {
        if (method == null) {
            return;
        }
        if (getClass0() == null) {
            setClass0(method.getDeclaringClass());
        }
        getMethodDescriptor = new MethodDescriptor(method);
        setTransient(method.getAnnotation(Transient.class));
    }

    /**
     * Mark an event set as unicast (or not).
     *
     * <p>
     *  将事件设置为单播(或不是)。
     * 
     * 
     * @param unicast  True if the event set is unicast.
     */
    public void setUnicast(boolean unicast) {
        this.unicast = unicast;
    }

    /**
     * Normally event sources are multicast.  However there are some
     * exceptions that are strictly unicast.
     *
     * <p>
     *  通常事件源是多播。然而,有一些严格单播的例外。
     * 
     * 
     * @return  <TT>true</TT> if the event set is unicast.
     *          Defaults to <TT>false</TT>.
     */
    public boolean isUnicast() {
        return unicast;
    }

    /**
     * Marks an event set as being in the &quot;default&quot; set (or not).
     * By default this is <TT>true</TT>.
     *
     * <p>
     *  将事件集标记为处于"默认"设置(或不)。默认情况下,这是<TT> true </TT>。
     * 
     * 
     * @param inDefaultEventSet <code>true</code> if the event set is in
     *                          the &quot;default&quot; set,
     *                          <code>false</code> if not
     */
    public void setInDefaultEventSet(boolean inDefaultEventSet) {
        this.inDefaultEventSet = inDefaultEventSet;
    }

    /**
     * Reports if an event set is in the &quot;default&quot; set.
     *
     * <p>
     *  报告事件集是否处于"默认"组。
     * 
     * 
     * @return  <TT>true</TT> if the event set is in
     *          the &quot;default&quot; set.  Defaults to <TT>true</TT>.
     */
    public boolean isInDefaultEventSet() {
        return inDefaultEventSet;
    }

    /*
     * Package-private constructor
     * Merge two event set descriptors.  Where they conflict, give the
     * second argument (y) priority over the first argument (x).
     *
     * <p>
     *  Package-private构造函数合并两个事件集描述符。在它们冲突的地方,给第二个参数(y)优先于第一个参数(x)。
     * 
     * 
     * @param x  The first (lower priority) EventSetDescriptor
     * @param y  The second (higher priority) EventSetDescriptor
     */
    EventSetDescriptor(EventSetDescriptor x, EventSetDescriptor y) {
        super(x,y);
        listenerMethodDescriptors = x.listenerMethodDescriptors;
        if (y.listenerMethodDescriptors != null) {
            listenerMethodDescriptors = y.listenerMethodDescriptors;
        }

        listenerTypeRef = x.listenerTypeRef;
        if (y.listenerTypeRef != null) {
            listenerTypeRef = y.listenerTypeRef;
        }

        addMethodDescriptor = x.addMethodDescriptor;
        if (y.addMethodDescriptor != null) {
            addMethodDescriptor = y.addMethodDescriptor;
        }

        removeMethodDescriptor = x.removeMethodDescriptor;
        if (y.removeMethodDescriptor != null) {
            removeMethodDescriptor = y.removeMethodDescriptor;
        }

        getMethodDescriptor = x.getMethodDescriptor;
        if (y.getMethodDescriptor != null) {
            getMethodDescriptor = y.getMethodDescriptor;
        }

        unicast = y.unicast;
        if (!x.inDefaultEventSet || !y.inDefaultEventSet) {
            inDefaultEventSet = false;
        }
    }

    /*
     * Package-private dup constructor
     * This must isolate the new object from any changes to the old object.
     * <p>
     *  Package-private dup constructor这必须将新对象与对旧对象的任何更改隔离开来。
     */
    EventSetDescriptor(EventSetDescriptor old) {
        super(old);
        if (old.listenerMethodDescriptors != null) {
            int len = old.listenerMethodDescriptors.length;
            listenerMethodDescriptors = new MethodDescriptor[len];
            for (int i = 0; i < len; i++) {
                listenerMethodDescriptors[i] = new MethodDescriptor(
                                        old.listenerMethodDescriptors[i]);
            }
        }
        listenerTypeRef = old.listenerTypeRef;

        addMethodDescriptor = old.addMethodDescriptor;
        removeMethodDescriptor = old.removeMethodDescriptor;
        getMethodDescriptor = old.getMethodDescriptor;

        unicast = old.unicast;
        inDefaultEventSet = old.inDefaultEventSet;
    }

    void appendTo(StringBuilder sb) {
        appendTo(sb, "unicast", this.unicast);
        appendTo(sb, "inDefaultEventSet", this.inDefaultEventSet);
        appendTo(sb, "listenerType", this.listenerTypeRef);
        appendTo(sb, "getListenerMethod", getMethod(this.getMethodDescriptor));
        appendTo(sb, "addListenerMethod", getMethod(this.addMethodDescriptor));
        appendTo(sb, "removeListenerMethod", getMethod(this.removeMethodDescriptor));
    }

    private static Method getMethod(MethodDescriptor descriptor) {
        return (descriptor != null)
                ? descriptor.getMethod()
                : null;
    }
}
