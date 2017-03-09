/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.*;
import java.lang.reflect.*;
import java.util.Objects;
import sun.reflect.misc.*;


/**
 * The <code>DefaultPersistenceDelegate</code> is a concrete implementation of
 * the abstract <code>PersistenceDelegate</code> class and
 * is the delegate used by default for classes about
 * which no information is available. The <code>DefaultPersistenceDelegate</code>
 * provides, version resilient, public API-based persistence for
 * classes that follow the JavaBeans&trade; conventions without any class specific
 * configuration.
 * <p>
 * The key assumptions are that the class has a nullary constructor
 * and that its state is accurately represented by matching pairs
 * of "setter" and "getter" methods in the order they are returned
 * by the Introspector.
 * In addition to providing code-free persistence for JavaBeans,
 * the <code>DefaultPersistenceDelegate</code> provides a convenient means
 * to effect persistent storage for classes that have a constructor
 * that, while not nullary, simply requires some property values
 * as arguments.
 *
 * <p>
 *  <code> DefaultPersistenceDelegate </code>是抽象<code> PersistenceDelegate </code>类的具体实现,是默认情况下对于没有可用信息的
 * 类使用的委托。
 *  <code> DefaultPersistenceDelegate </code>为JavaBeans&trade之后的类提供了基于API的基于公共API的持久性;没有任何类特定配置的约定。
 * <p>
 *  关键假设是类有一个nullary构造函数,并且它的状态通过匹配的"setter"和"getter"方法的对按照Introspector返回的顺序精确表示。
 * 除了为JavaBean提供无代码持久化之外,<code> DefaultPersistenceDelegate </code>提供了一种方便的方法来实现具有构造函数的类的持久存储,该构造函数虽然不是nu
 * llary,只需要一些属性值作为参数。
 *  关键假设是类有一个nullary构造函数,并且它的状态通过匹配的"setter"和"getter"方法的对按照Introspector返回的顺序精确表示。
 * 
 * 
 * @see #DefaultPersistenceDelegate(String[])
 * @see java.beans.Introspector
 *
 * @since 1.4
 *
 * @author Philip Milne
 */

public class DefaultPersistenceDelegate extends PersistenceDelegate {
    private static final String[] EMPTY = {};
    private final String[] constructor;
    private Boolean definesEquals;

    /**
     * Creates a persistence delegate for a class with a nullary constructor.
     *
     * <p>
     *  为具有nullary构造函数的类创建持久委托。
     * 
     * 
     * @see #DefaultPersistenceDelegate(java.lang.String[])
     */
    public DefaultPersistenceDelegate() {
        this.constructor = EMPTY;
    }

    /**
     * Creates a default persistence delegate for a class with a
     * constructor whose arguments are the values of the property
     * names as specified by <code>constructorPropertyNames</code>.
     * The constructor arguments are created by
     * evaluating the property names in the order they are supplied.
     * To use this class to specify a single preferred constructor for use
     * in the serialization of a particular type, we state the
     * names of the properties that make up the constructor's
     * arguments. For example, the <code>Font</code> class which
     * does not define a nullary constructor can be handled
     * with the following persistence delegate:
     *
     * <pre>
     *     new DefaultPersistenceDelegate(new String[]{"name", "style", "size"});
     * </pre>
     *
     * <p>
     * 为具有构造函数的类创建默认持久委托,该构造函数的参数是由<code> constructorPropertyNames </code>指定的属性名称的值。
     * 构造函数参数是通过按提供的顺序评估属性名称来创建的。要使用这个类来指定一个用于序列化特定类型的首选构造函数,我们声明构成构造函数参数的属性的名称。
     * 例如,不定义nullary构造函数的<code> Font </code>类可以使用以下persistence委托来处理：。
     * 
     * <pre>
     *  new DefaultPersistenceDelegate(new String [] {"name","style","size"});
     * </pre>
     * 
     * 
     * @param  constructorPropertyNames The property names for the arguments of this constructor.
     *
     * @see #instantiate
     */
    public DefaultPersistenceDelegate(String[] constructorPropertyNames) {
        this.constructor = (constructorPropertyNames == null) ? EMPTY : constructorPropertyNames.clone();
    }

    private static boolean definesEquals(Class<?> type) {
        try {
            return type == type.getMethod("equals", Object.class).getDeclaringClass();
        }
        catch(NoSuchMethodException e) {
            return false;
        }
    }

    private boolean definesEquals(Object instance) {
        if (definesEquals != null) {
            return (definesEquals == Boolean.TRUE);
        }
        else {
            boolean result = definesEquals(instance.getClass());
            definesEquals = result ? Boolean.TRUE : Boolean.FALSE;
            return result;
        }
    }

    /**
     * If the number of arguments in the specified constructor is non-zero and
     * the class of <code>oldInstance</code> explicitly declares an "equals" method
     * this method returns the value of <code>oldInstance.equals(newInstance)</code>.
     * Otherwise, this method uses the superclass's definition which returns true if the
     * classes of the two instances are equal.
     *
     * <p>
     *  如果指定的构造函数中的参数数量不为零,并且<code> oldInstance </code>类明确声明了一个"equals"方法,则此方法返回<code> oldInstance.equals(ne
     * wInstance)</code >。
     * 否则,此方法使用超类的定义,如果两个实例的类相等,则返回true。
     * 
     * 
     * @param oldInstance The instance to be copied.
     * @param newInstance The instance that is to be modified.
     * @return True if an equivalent copy of <code>newInstance</code> may be
     *         created by applying a series of mutations to <code>oldInstance</code>.
     *
     * @see #DefaultPersistenceDelegate(String[])
     */
    protected boolean mutatesTo(Object oldInstance, Object newInstance) {
        // Assume the instance is either mutable or a singleton
        // if it has a nullary constructor.
        return (constructor.length == 0) || !definesEquals(oldInstance) ?
            super.mutatesTo(oldInstance, newInstance) :
            oldInstance.equals(newInstance);
    }

    /**
     * This default implementation of the <code>instantiate</code> method returns
     * an expression containing the predefined method name "new" which denotes a
     * call to a constructor with the arguments as specified in
     * the <code>DefaultPersistenceDelegate</code>'s constructor.
     *
     * <p>
     *  <code> instantiate </code>方法的默认实现返回一个包含预定义方法名"new"的表达式,它表示对构造函数的调用,该构造函数具有<code> DefaultPersistenceD
     * elegate </code>的构造函数中指定的参数。
     * 
     * 
     * @param  oldInstance The instance to be instantiated.
     * @param  out The code output stream.
     * @return An expression whose value is <code>oldInstance</code>.
     *
     * @throws NullPointerException if {@code out} is {@code null}
     *                              and this value is used in the method
     *
     * @see #DefaultPersistenceDelegate(String[])
     */
    protected Expression instantiate(Object oldInstance, Encoder out) {
        int nArgs = constructor.length;
        Class<?> type = oldInstance.getClass();
        Object[] constructorArgs = new Object[nArgs];
        for(int i = 0; i < nArgs; i++) {
            try {
                Method method = findMethod(type, this.constructor[i]);
                constructorArgs[i] = MethodUtil.invoke(method, oldInstance, new Object[0]);
            }
            catch (Exception e) {
                out.getExceptionListener().exceptionThrown(e);
            }
        }
        return new Expression(oldInstance, oldInstance.getClass(), "new", constructorArgs);
    }

    private Method findMethod(Class<?> type, String property) {
        if (property == null) {
            throw new IllegalArgumentException("Property name is null");
        }
        PropertyDescriptor pd = getPropertyDescriptor(type, property);
        if (pd == null) {
            throw new IllegalStateException("Could not find property by the name " + property);
        }
        Method method = pd.getReadMethod();
        if (method == null) {
            throw new IllegalStateException("Could not find getter for the property " + property);
        }
        return method;
    }

    private void doProperty(Class<?> type, PropertyDescriptor pd, Object oldInstance, Object newInstance, Encoder out) throws Exception {
        Method getter = pd.getReadMethod();
        Method setter = pd.getWriteMethod();

        if (getter != null && setter != null) {
            Expression oldGetExp = new Expression(oldInstance, getter.getName(), new Object[]{});
            Expression newGetExp = new Expression(newInstance, getter.getName(), new Object[]{});
            Object oldValue = oldGetExp.getValue();
            Object newValue = newGetExp.getValue();
            out.writeExpression(oldGetExp);
            if (!Objects.equals(newValue, out.get(oldValue))) {
                // Search for a static constant with this value;
                Object e = (Object[])pd.getValue("enumerationValues");
                if (e instanceof Object[] && Array.getLength(e) % 3 == 0) {
                    Object[] a = (Object[])e;
                    for(int i = 0; i < a.length; i = i + 3) {
                        try {
                           Field f = type.getField((String)a[i]);
                           if (f.get(null).equals(oldValue)) {
                               out.remove(oldValue);
                               out.writeExpression(new Expression(oldValue, f, "get", new Object[]{null}));
                           }
                        }
                        catch (Exception ex) {}
                    }
                }
                invokeStatement(oldInstance, setter.getName(), new Object[]{oldValue}, out);
            }
        }
    }

    static void invokeStatement(Object instance, String methodName, Object[] args, Encoder out) {
        out.writeStatement(new Statement(instance, methodName, args));
    }

    // Write out the properties of this instance.
    private void initBean(Class<?> type, Object oldInstance, Object newInstance, Encoder out) {
        for (Field field : type.getFields()) {
            if (!ReflectUtil.isPackageAccessible(field.getDeclaringClass())) {
                continue;
            }
            int mod = field.getModifiers();
            if (Modifier.isFinal(mod) || Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
                continue;
            }
            try {
                Expression oldGetExp = new Expression(field, "get", new Object[] { oldInstance });
                Expression newGetExp = new Expression(field, "get", new Object[] { newInstance });
                Object oldValue = oldGetExp.getValue();
                Object newValue = newGetExp.getValue();
                out.writeExpression(oldGetExp);
                if (!Objects.equals(newValue, out.get(oldValue))) {
                    out.writeStatement(new Statement(field, "set", new Object[] { oldInstance, oldValue }));
                }
            }
            catch (Exception exception) {
                out.getExceptionListener().exceptionThrown(exception);
            }
        }
        BeanInfo info;
        try {
            info = Introspector.getBeanInfo(type);
        } catch (IntrospectionException exception) {
            return;
        }
        // Properties
        for (PropertyDescriptor d : info.getPropertyDescriptors()) {
            if (d.isTransient()) {
                continue;
            }
            try {
                doProperty(type, d, oldInstance, newInstance, out);
            }
            catch (Exception e) {
                out.getExceptionListener().exceptionThrown(e);
            }
        }

        // Listeners
        /*
        Pending(milne). There is a general problem with the archival of
        listeners which is unresolved as of 1.4. Many of the methods
        which install one object inside another (typically "add" methods
        or setters) automatically install a listener on the "child" object
        so that its "parent" may respond to changes that are made to it.
        For example the JTable:setModel() method automatically adds a
        TableModelListener (the JTable itself in this case) to the supplied
        table model.

        We do not need to explicitly add these listeners to the model in an
        archive as they will be added automatically by, in the above case,
        the JTable's "setModel" method. In some cases, we must specifically
        avoid trying to do this since the listener may be an inner class
        that cannot be instantiated using public API.

        No general mechanism currently
        exists for differentiating between these kind of listeners and
        those which were added explicitly by the user. A mechanism must
        be created to provide a general means to differentiate these
        special cases so as to provide reliable persistence of listeners
        for the general case.
        /* <p>
        /* 待定(milne)。有一个普遍的问题,存档的听众是未解决的1.4。
        /* 许多将一个对象安装在另一个对象(通常是"添加"方法或设置器)中的方法自动在"子"对象上安装一个监听器,使得它的"父"可以响应对它做出的更改。
        /* 例如,JTable：setModel()方法在提供的表模型中自动添加一个TableModelListener(在这种情况下是JTable本身)。
        /* 
        /*  我们不需要显式地将这些监听器添加到归档中的模型中,因为它们将通过JTable的"setModel"方法自动添加。
        /* 在某些情况下,我们必须特别避免尝试这样做,因为侦听器可能是一个内部类,不能使用公共API实例化。
        /* 
        /*  当前不存在用于区分这些类型的监听器和用户明确添加的那些的通用机制。必须创建一种机制来提供一般手段来区分这些特殊情况,以便为一般情况提供可靠的持久性监听器。
        /* 
        */
        if (!java.awt.Component.class.isAssignableFrom(type)) {
            return; // Just handle the listeners of Components for now.
        }
        for (EventSetDescriptor d : info.getEventSetDescriptors()) {
            if (d.isTransient()) {
                continue;
            }
            Class<?> listenerType = d.getListenerType();


            // The ComponentListener is added automatically, when
            // Contatiner:add is called on the parent.
            if (listenerType == java.awt.event.ComponentListener.class) {
                continue;
            }

            // JMenuItems have a change listener added to them in
            // their "add" methods to enable accessibility support -
            // see the add method in JMenuItem for details. We cannot
            // instantiate this instance as it is a private inner class
            // and do not need to do this anyway since it will be created
            // and installed by the "add" method. Special case this for now,
            // ignoring all change listeners on JMenuItems.
            if (listenerType == javax.swing.event.ChangeListener.class &&
                type == javax.swing.JMenuItem.class) {
                continue;
            }

            EventListener[] oldL = new EventListener[0];
            EventListener[] newL = new EventListener[0];
            try {
                Method m = d.getGetListenerMethod();
                oldL = (EventListener[])MethodUtil.invoke(m, oldInstance, new Object[]{});
                newL = (EventListener[])MethodUtil.invoke(m, newInstance, new Object[]{});
            }
            catch (Exception e2) {
                try {
                    Method m = type.getMethod("getListeners", new Class<?>[]{Class.class});
                    oldL = (EventListener[])MethodUtil.invoke(m, oldInstance, new Object[]{listenerType});
                    newL = (EventListener[])MethodUtil.invoke(m, newInstance, new Object[]{listenerType});
                }
                catch (Exception e3) {
                    return;
                }
            }

            // Asssume the listeners are in the same order and that there are no gaps.
            // Eventually, this may need to do true differencing.
            String addListenerMethodName = d.getAddListenerMethod().getName();
            for (int i = newL.length; i < oldL.length; i++) {
                // System.out.println("Adding listener: " + addListenerMethodName + oldL[i]);
                invokeStatement(oldInstance, addListenerMethodName, new Object[]{oldL[i]}, out);
            }

            String removeListenerMethodName = d.getRemoveListenerMethod().getName();
            for (int i = oldL.length; i < newL.length; i++) {
                invokeStatement(oldInstance, removeListenerMethodName, new Object[]{newL[i]}, out);
            }
        }
    }

    /**
     * This default implementation of the <code>initialize</code> method assumes
     * all state held in objects of this type is exposed via the
     * matching pairs of "setter" and "getter" methods in the order
     * they are returned by the Introspector. If a property descriptor
     * defines a "transient" attribute with a value equal to
     * <code>Boolean.TRUE</code> the property is ignored by this
     * default implementation. Note that this use of the word
     * "transient" is quite independent of the field modifier
     * that is used by the <code>ObjectOutputStream</code>.
     * <p>
     * For each non-transient property, an expression is created
     * in which the nullary "getter" method is applied
     * to the <code>oldInstance</code>. The value of this
     * expression is the value of the property in the instance that is
     * being serialized. If the value of this expression
     * in the cloned environment <code>mutatesTo</code> the
     * target value, the new value is initialized to make it
     * equivalent to the old value. In this case, because
     * the property value has not changed there is no need to
     * call the corresponding "setter" method and no statement
     * is emitted. If not however, the expression for this value
     * is replaced with another expression (normally a constructor)
     * and the corresponding "setter" method is called to install
     * the new property value in the object. This scheme removes
     * default information from the output produced by streams
     * using this delegate.
     * <p>
     * In passing these statements to the output stream, where they
     * will be executed, side effects are made to the <code>newInstance</code>.
     * In most cases this allows the problem of properties
     * whose values depend on each other to actually help the
     * serialization process by making the number of statements
     * that need to be written to the output smaller. In general,
     * the problem of handling interdependent properties is reduced to
     * that of finding an order for the properties in
     * a class such that no property value depends on the value of
     * a subsequent property.
     *
     * <p>
     * <code> initialize </code>方法的默认实现假设所有在此类型的对象中保持的状态通过匹配的"setter"和"getter"方法对按照Introspector返回的顺序显示。
     * 如果属性描述符定义了一个值等于<code> Boolean.TRUE </code>的"transient"属性,则此默认实现将忽略该属性。
     * 注意,这个词"transient"的使用完全独立于<code> ObjectOutputStream </code>使用的字段修饰符。
     * <p>
     *  对于每个非瞬态属性,创建一个表达式,其中将空值"getter"方法应用于<code> oldInstance </code>。此表达式的值是正在序列化的实例中的属性的值。
     * 如果克隆环境中此表达式的值<code> mutatesTo </code>目标值,则会初始化新值以使其等同于旧值。
     * 
     * @param type the type of the instances
     * @param oldInstance The instance to be copied.
     * @param newInstance The instance that is to be modified.
     * @param out The stream to which any initialization statements should be written.
     *
     * @throws NullPointerException if {@code out} is {@code null}
     *
     * @see java.beans.Introspector#getBeanInfo
     * @see java.beans.PropertyDescriptor
     */
    protected void initialize(Class<?> type,
                              Object oldInstance, Object newInstance,
                              Encoder out)
    {
        // System.out.println("DefulatPD:initialize" + type);
        super.initialize(type, oldInstance, newInstance, out);
        if (oldInstance.getClass() == type) { // !type.isInterface()) {
            initBean(type, oldInstance, newInstance, out);
        }
    }

    private static PropertyDescriptor getPropertyDescriptor(Class<?> type, String property) {
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(type).getPropertyDescriptors()) {
                if (property.equals(pd.getName()))
                    return pd;
            }
        } catch (IntrospectionException exception) {
        }
        return null;
    }
}
