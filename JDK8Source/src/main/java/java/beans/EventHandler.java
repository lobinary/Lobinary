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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

import sun.reflect.misc.MethodUtil;
import sun.reflect.misc.ReflectUtil;

/**
 * The <code>EventHandler</code> class provides
 * support for dynamically generating event listeners whose methods
 * execute a simple statement involving an incoming event object
 * and a target object.
 * <p>
 * The <code>EventHandler</code> class is intended to be used by interactive tools, such as
 * application builders, that allow developers to make connections between
 * beans. Typically connections are made from a user interface bean
 * (the event <em>source</em>)
 * to an application logic bean (the <em>target</em>). The most effective
 * connections of this kind isolate the application logic from the user
 * interface.  For example, the <code>EventHandler</code> for a
 * connection from a <code>JCheckBox</code> to a method
 * that accepts a boolean value can deal with extracting the state
 * of the check box and passing it directly to the method so that
 * the method is isolated from the user interface layer.
 * <p>
 * Inner classes are another, more general way to handle events from
 * user interfaces.  The <code>EventHandler</code> class
 * handles only a subset of what is possible using inner
 * classes. However, <code>EventHandler</code> works better
 * with the long-term persistence scheme than inner classes.
 * Also, using <code>EventHandler</code> in large applications in
 * which the same interface is implemented many times can
 * reduce the disk and memory footprint of the application.
 * <p>
 * The reason that listeners created with <code>EventHandler</code>
 * have such a small
 * footprint is that the <code>Proxy</code> class, on which
 * the <code>EventHandler</code> relies, shares implementations
 * of identical
 * interfaces. For example, if you use
 * the <code>EventHandler</code> <code>create</code> methods to make
 * all the <code>ActionListener</code>s in an application,
 * all the action listeners will be instances of a single class
 * (one created by the <code>Proxy</code> class).
 * In general, listeners based on
 * the <code>Proxy</code> class require one listener class
 * to be created per <em>listener type</em> (interface),
 * whereas the inner class
 * approach requires one class to be created per <em>listener</em>
 * (object that implements the interface).
 *
 * <p>
 * You don't generally deal directly with <code>EventHandler</code>
 * instances.
 * Instead, you use one of the <code>EventHandler</code>
 * <code>create</code> methods to create
 * an object that implements a given listener interface.
 * This listener object uses an <code>EventHandler</code> object
 * behind the scenes to encapsulate information about the
 * event, the object to be sent a message when the event occurs,
 * the message (method) to be sent, and any argument
 * to the method.
 * The following section gives examples of how to create listener
 * objects using the <code>create</code> methods.
 *
 * <h2>Examples of Using EventHandler</h2>
 *
 * The simplest use of <code>EventHandler</code> is to install
 * a listener that calls a method on the target object with no arguments.
 * In the following example we create an <code>ActionListener</code>
 * that invokes the <code>toFront</code> method on an instance
 * of <code>javax.swing.JFrame</code>.
 *
 * <blockquote>
 *<pre>
 *myButton.addActionListener(
 *    (ActionListener)EventHandler.create(ActionListener.class, frame, "toFront"));
 *</pre>
 * </blockquote>
 *
 * When <code>myButton</code> is pressed, the statement
 * <code>frame.toFront()</code> will be executed.  One could get
 * the same effect, with some additional compile-time type safety,
 * by defining a new implementation of the <code>ActionListener</code>
 * interface and adding an instance of it to the button:
 *
 * <blockquote>
 *<pre>
//Equivalent code using an inner class instead of EventHandler.
 *myButton.addActionListener(new ActionListener() {
 *    public void actionPerformed(ActionEvent e) {
 *        frame.toFront();
 *    }
 *});
 *</pre>
 * </blockquote>
 *
 * The next simplest use of <code>EventHandler</code> is
 * to extract a property value from the first argument
 * of the method in the listener interface (typically an event object)
 * and use it to set the value of a property in the target object.
 * In the following example we create an <code>ActionListener</code> that
 * sets the <code>nextFocusableComponent</code> property of the target
 * (myButton) object to the value of the "source" property of the event.
 *
 * <blockquote>
 *<pre>
 *EventHandler.create(ActionListener.class, myButton, "nextFocusableComponent", "source")
 *</pre>
 * </blockquote>
 *
 * This would correspond to the following inner class implementation:
 *
 * <blockquote>
 *<pre>
//Equivalent code using an inner class instead of EventHandler.
 *new ActionListener() {
 *    public void actionPerformed(ActionEvent e) {
 *        myButton.setNextFocusableComponent((Component)e.getSource());
 *    }
 *}
 *</pre>
 * </blockquote>
 *
 * It's also possible to create an <code>EventHandler</code> that
 * just passes the incoming event object to the target's action.
 * If the fourth <code>EventHandler.create</code> argument is
 * an empty string, then the event is just passed along:
 *
 * <blockquote>
 *<pre>
 *EventHandler.create(ActionListener.class, target, "doActionEvent", "")
 *</pre>
 * </blockquote>
 *
 * This would correspond to the following inner class implementation:
 *
 * <blockquote>
 *<pre>
//Equivalent code using an inner class instead of EventHandler.
 *new ActionListener() {
 *    public void actionPerformed(ActionEvent e) {
 *        target.doActionEvent(e);
 *    }
 *}
 *</pre>
 * </blockquote>
 *
 * Probably the most common use of <code>EventHandler</code>
 * is to extract a property value from the
 * <em>source</em> of the event object and set this value as
 * the value of a property of the target object.
 * In the following example we create an <code>ActionListener</code> that
 * sets the "label" property of the target
 * object to the value of the "text" property of the
 * source (the value of the "source" property) of the event.
 *
 * <blockquote>
 *<pre>
 *EventHandler.create(ActionListener.class, myButton, "label", "source.text")
 *</pre>
 * </blockquote>
 *
 * This would correspond to the following inner class implementation:
 *
 * <blockquote>
 *<pre>
//Equivalent code using an inner class instead of EventHandler.
 *new ActionListener {
 *    public void actionPerformed(ActionEvent e) {
 *        myButton.setLabel(((JTextField)e.getSource()).getText());
 *    }
 *}
 *</pre>
 * </blockquote>
 *
 * The event property may be "qualified" with an arbitrary number
 * of property prefixes delimited with the "." character. The "qualifying"
 * names that appear before the "." characters are taken as the names of
 * properties that should be applied, left-most first, to
 * the event object.
 * <p>
 * For example, the following action listener
 *
 * <blockquote>
 *<pre>
 *EventHandler.create(ActionListener.class, target, "a", "b.c.d")
 *</pre>
 * </blockquote>
 *
 * might be written as the following inner class
 * (assuming all the properties had canonical getter methods and
 * returned the appropriate types):
 *
 * <blockquote>
 *<pre>
//Equivalent code using an inner class instead of EventHandler.
 *new ActionListener {
 *    public void actionPerformed(ActionEvent e) {
 *        target.setA(e.getB().getC().isD());
 *    }
 *}
 *</pre>
 * </blockquote>
 * The target property may also be "qualified" with an arbitrary number
 * of property prefixs delimited with the "." character.  For example, the
 * following action listener:
 * <pre>
 *   EventHandler.create(ActionListener.class, target, "a.b", "c.d")
 * </pre>
 * might be written as the following inner class
 * (assuming all the properties had canonical getter methods and
 * returned the appropriate types):
 * <pre>
 *   //Equivalent code using an inner class instead of EventHandler.
 *   new ActionListener {
 *     public void actionPerformed(ActionEvent e) {
 *         target.getA().setB(e.getC().isD());
 *    }
 *}
 *</pre>
 * <p>
 * As <code>EventHandler</code> ultimately relies on reflection to invoke
 * a method we recommend against targeting an overloaded method.  For example,
 * if the target is an instance of the class <code>MyTarget</code> which is
 * defined as:
 * <pre>
 *   public class MyTarget {
 *     public void doIt(String);
 *     public void doIt(Object);
 *   }
 * </pre>
 * Then the method <code>doIt</code> is overloaded.  EventHandler will invoke
 * the method that is appropriate based on the source.  If the source is
 * null, then either method is appropriate and the one that is invoked is
 * undefined.  For that reason we recommend against targeting overloaded
 * methods.
 *
 * <p>
 *  <code> EventHandler </code>类提供了对动态生成事件监听器的支持,这些事件监听器的方法执行一个涉及传入事件对象和目标对象的简单语句。
 * <p>
 *  <code> EventHandler </code>类旨在由交互式工具(如应用程序构建器)使用,允许开发人员在bean之间建立连接。
 * 通常,从用户界面bean(事件<em>源</em>)到应用程序逻辑bean(<em> target </em>)进行连接。这种最有效的连接将应用逻辑与用户界面隔离。
 * 例如,从<code> JCheckBox </code>到接受布尔值的方法的连接的<code> EventHandler </code>可以处理提取复选框的状态并将其直接传递给方法使得该方法与用户界面层
 * 隔离。
 * 通常,从用户界面bean(事件<em>源</em>)到应用程序逻辑bean(<em> target </em>)进行连接。这种最有效的连接将应用逻辑与用户界面隔离。
 * <p>
 *  内部类是另一种更通用的方法来处理来自用户界面的事件。 <code> EventHandler </code>类只处理可能使用内部类的一个子集。
 * 然而,<code> EventHandler </code>对于长期持久化方案比内部类更好。
 * 此外,在同一接口被实施多次的大型应用程序中使用<code> EventHandler </code>可以减少应用程序的磁盘和内存占用。
 * <p>
 * 使用<code> EventHandler </code>创建的监听器具有这么小的占用空间的原因是<code> Proxy </code>类</code>在<code> EventHandler </code>
 *  。
 * 例如,如果您使用<code> EventHandler </code> <code> create </code>方法在应用程序中创建所有<code> ActionListener </code>,所有
 * 操作侦听器都将是单个类(由<code> Proxy </code>类创建的类)。
 * 一般来说,基于<code> Proxy </code>类的侦听器需要为每个侦听器类型</em>(接口)创建一个侦听器类,而内部类方法需要根据< em> listener </em>(实现接口的对象)。
 * 
 * <p>
 *  您通常不直接处理<code> EventHandler </code>实例。
 * 相反,您使用<code> EventHandler </code> <code> create </code>方法之一来创建实现给定侦听器接口的对象。
 * 这个监听器对象在后台使用一个<code> EventHandler </code>对象来封装有关事件的信息,当事件发生时要发送的对象,要发送的消息(方法)和任何参数方法。
 * 以下部分提供了如何使用<code> create </code>方法创建侦听器对象的示例。
 * 
 *  <h2>使用EventHandler </h2>的示例
 * 
 * <code> EventHandler </code>的最简单的用法是安装一个监听器,该监听器在没有参数的目标对象上调用一个方法。
 * 在下面的示例中,我们创建了一个<code> ActionListener </code>,调用<code> javax.swing.JFrame </code>实例上的<code> toFront </code>
 * 方法。
 * <code> EventHandler </code>的最简单的用法是安装一个监听器,该监听器在没有参数的目标对象上调用一个方法。
 * 
 * <blockquote>
 * pre>
 *  yButton.addActionListener((ActionListener)EventHandler.create(ActionListener.class,frame,"toFront"))
 * ;。
 * /pre>
 * </blockquote>
 * 
 *  当按下<code> myButton </code>时,语句<code> frame.toFront()</code>将被执行。
 * 通过定义<code> ActionListener </code>接口的新实现并将其实例添加到按钮中,可以获得相同的效果,具有一些额外的编译时类型安全性：。
 * 
 * <blockquote>
 * pre>
 *  //使用内部类而不是EventHandler的等效代码。
 *  yButton.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){frame.toFront();}
 * );。
 *  //使用内部类而不是EventHandler的等效代码。
 * /pre>
 * </blockquote>
 * 
 *  下一个最简单的使用<code> EventHandler </code>是从侦听器接口(通常是事件对象)中的方法的第一个参数中提取属性值,并使用它来设置目标对象中的属性的值。
 * 在下面的示例中,我们创建一个<code> ActionListener </code>,将目标(myButton)对象的<code> nextFocusableComponent </code>属性设置
 * 为事件的"source"属性的值。
 *  下一个最简单的使用<code> EventHandler </code>是从侦听器接口(通常是事件对象)中的方法的第一个参数中提取属性值,并使用它来设置目标对象中的属性的值。
 * 
 * <blockquote>
 * pre>
 *  ventHandler.create(ActionListener.class,myButton,"nextFocusableComponent","source")
 * /pre>
 * </blockquote>
 * 
 *  这将对应于以下内部类实现：
 * 
 * <blockquote>
 * pre>
 * //使用内部类而不是EventHandler的等效代码。
 *  ew ActionListener(){public void actionPerformed(ActionEvent e){myButton.setNextFocusableComponent((Component)e.getSource()); }
 * }。
 * //使用内部类而不是EventHandler的等效代码。
 * 
 * /pre>
 * </blockquote>
 * 
 *  还可以创建一个<code> EventHandler </code>,它只是将传入的事件对象传递给目标的操作。
 * 如果第四个<code> EventHandler.create </code>参数是一个空字符串,那么事件只是沿着传递：。
 * 
 * <blockquote>
 * pre>
 *  ventHandler.create(ActionListener.class,target,"doActionEvent","")
 * /pre>
 * </blockquote>
 * 
 *  这将对应于以下内部类实现：
 * 
 * <blockquote>
 * pre>
 *  //使用内部类而不是EventHandler的等效代码。
 *  ew ActionListener(){public void actionPerformed(ActionEvent e){target.doActionEvent(e); }}。
 * 
 * /pre>
 * </blockquote>
 * 
 *  可能最常见的使用<code> EventHandler </code>是从事件对象的<em> source </em>中提取属性值,并将此值设置为目标对象的属性值。
 * 在下面的示例中,我们创建一个<code> ActionListener </code>,将目标对象的"label"属性设置为源的"text"属性的值("source"属性的值)事件。
 * 
 * <blockquote>
 * pre>
 *  ventHandler.create(ActionListener.class,myButton,"label","source.text")
 * /pre>
 * </blockquote>
 * 
 *  这将对应于以下内部类实现：
 * 
 * <blockquote>
 * pre>
 *  //使用内部类而不是EventHandler的等效代码。
 *  ew ActionListener {public void actionPerformed(ActionEvent e){myButton.setLabel((JTextField)e.getSource())。
 *  //使用内部类而不是EventHandler的等效代码。getText()); }}。
 * 
 * /pre>
 * </blockquote>
 * 
 * 事件属性可以是"限定的"具有用"。"分隔的任意数量的属性前缀。字符。出现在"。"之前的"限定"名称。字符被视为应该应用于(最左边第一个)事件对象的属性的名称。
 * <p>
 *  例如,以下动作侦听器
 * 
 * <blockquote>
 * pre>
 *  ventHandler.create(ActionListener.class,target,"a","b.c.d")
 * /pre>
 * </blockquote>
 * 
 *  可能被写为以下内部类(假设所有属性都有规范的getter方法并返回适当的类型)：
 * 
 * <blockquote>
 * pre>
 *  //使用内部类而不是EventHandler的等效代码。
 * 
 * @see java.lang.reflect.Proxy
 * @see java.util.EventObject
 *
 * @since 1.4
 *
 * @author Mark Davidson
 * @author Philip Milne
 * @author Hans Muller
 *
 */
public class EventHandler implements InvocationHandler {
    private Object target;
    private String action;
    private final String eventPropertyName;
    private final String listenerMethodName;
    private final AccessControlContext acc = AccessController.getContext();

    /**
     * Creates a new <code>EventHandler</code> object;
     * you generally use one of the <code>create</code> methods
     * instead of invoking this constructor directly.  Refer to
     * {@link java.beans.EventHandler#create(Class, Object, String, String)
     * the general version of create} for a complete description of
     * the <code>eventPropertyName</code> and <code>listenerMethodName</code>
     * parameter.
     *
     * <p>
     *  ew ActionListener {public void actionPerformed(ActionEvent e){target.setA(e.getB()。getC()。
     * isD()); }}。
     * 
     * /pre>
     * </blockquote>
     *  目标属性也可以被"限定"为具有由""限定的任意数量的属性前缀。字符。例如,以下动作侦听器：
     * <pre>
     *  EventHandler.create(ActionListener.class,target,"a.b","c.d")
     * </pre>
     *  可能被写为以下内部类(假设所有属性都有规范的getter方法并返回适当的类型)：
     * <pre>
     *  //使用内部类而不是EventHandler的等效代码。
     *  new ActionListener {public void actionPerformed(ActionEvent e){target.getA()。setB(e.getC()。
     * isD()); }}。
     * 
     * /pre>
     * <p>
     *  由于<code> EventHandler </code>最终依靠反射来调用一个方法,我们建议不要针对一个重载的方法。
     * 例如,如果目标是类<code> MyTarget </code>的实例,其定义为：。
     * <pre>
     * public class MyTarget {public void doIt(String); public void doIt(Object); }}
     * </pre>
     *  然后方法<code> doIt </code>被重载。 EventHandler将根据源调用适当的方法。如果源为null,那么任一方法都是适当的,并且被调用的方法是未定义的。
     * 因此,我们建议不要定位重载方法。
     * 
     * 
     * @param target the object that will perform the action
     * @param action the name of a (possibly qualified) property or method on
     *        the target
     * @param eventPropertyName the (possibly qualified) name of a readable property of the incoming event
     * @param listenerMethodName the name of the method in the listener interface that should trigger the action
     *
     * @throws NullPointerException if <code>target</code> is null
     * @throws NullPointerException if <code>action</code> is null
     *
     * @see EventHandler
     * @see #create(Class, Object, String, String, String)
     * @see #getTarget
     * @see #getAction
     * @see #getEventPropertyName
     * @see #getListenerMethodName
     */
    @ConstructorProperties({"target", "action", "eventPropertyName", "listenerMethodName"})
    public EventHandler(Object target, String action, String eventPropertyName, String listenerMethodName) {
        this.target = target;
        this.action = action;
        if (target == null) {
            throw new NullPointerException("target must be non-null");
        }
        if (action == null) {
            throw new NullPointerException("action must be non-null");
        }
        this.eventPropertyName = eventPropertyName;
        this.listenerMethodName = listenerMethodName;
    }

    /**
     * Returns the object to which this event handler will send a message.
     *
     * <p>
     *  创建一个新的<code> EventHandler </code>对象;您通常使用<code> create </code>方法之一,而不是直接调用此构造函数。
     * 有关<code> eventPropertyName </code>和<code> listenerMethodName </code>的完整说明,请参阅{@link java.beans.EventHandler#create(Class,Object,String,String)参数。
     *  创建一个新的<code> EventHandler </code>对象;您通常使用<code> create </code>方法之一,而不是直接调用此构造函数。
     * 
     * 
     * @return the target of this event handler
     * @see #EventHandler(Object, String, String, String)
     */
    public Object getTarget()  {
        return target;
    }

    /**
     * Returns the name of the target's writable property
     * that this event handler will set,
     * or the name of the method that this event handler
     * will invoke on the target.
     *
     * <p>
     *  返回此事件处理程序将向其发送消息的对象。
     * 
     * 
     * @return the action of this event handler
     * @see #EventHandler(Object, String, String, String)
     */
    public String getAction()  {
        return action;
    }

    /**
     * Returns the property of the event that should be
     * used in the action applied to the target.
     *
     * <p>
     *  返回此事件处理程序将设置的目标的writable属性的名称,或此事件处理程序将在目标上调用的方法的名称。
     * 
     * 
     * @return the property of the event
     *
     * @see #EventHandler(Object, String, String, String)
     */
    public String getEventPropertyName()  {
        return eventPropertyName;
    }

    /**
     * Returns the name of the method that will trigger the action.
     * A return value of <code>null</code> signifies that all methods in the
     * listener interface trigger the action.
     *
     * <p>
     *  返回应用于应用于目标的操作中应使用的事件的属性。
     * 
     * 
     * @return the name of the method that will trigger the action
     *
     * @see #EventHandler(Object, String, String, String)
     */
    public String getListenerMethodName()  {
        return listenerMethodName;
    }

    private Object applyGetters(Object target, String getters) {
        if (getters == null || getters.equals("")) {
            return target;
        }
        int firstDot = getters.indexOf('.');
        if (firstDot == -1) {
            firstDot = getters.length();
        }
        String first = getters.substring(0, firstDot);
        String rest = getters.substring(Math.min(firstDot + 1, getters.length()));

        try {
            Method getter = null;
            if (target != null) {
                getter = Statement.getMethod(target.getClass(),
                                      "get" + NameGenerator.capitalize(first),
                                      new Class<?>[]{});
                if (getter == null) {
                    getter = Statement.getMethod(target.getClass(),
                                   "is" + NameGenerator.capitalize(first),
                                   new Class<?>[]{});
                }
                if (getter == null) {
                    getter = Statement.getMethod(target.getClass(), first, new Class<?>[]{});
                }
            }
            if (getter == null) {
                throw new RuntimeException("No method called: " + first +
                                           " defined on " + target);
            }
            Object newTarget = MethodUtil.invoke(getter, target, new Object[]{});
            return applyGetters(newTarget, rest);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to call method: " + first +
                                       " on " + target, e);
        }
    }

    /**
     * Extract the appropriate property value from the event and
     * pass it to the action associated with
     * this <code>EventHandler</code>.
     *
     * <p>
     *  返回将触发操作的方法的名称。返回值<code> null </code>表示侦听器接口中的所有方法都触发操作。
     * 
     * 
     * @param proxy the proxy object
     * @param method the method in the listener interface
     * @return the result of applying the action to the target
     *
     * @see EventHandler
     */
    public Object invoke(final Object proxy, final Method method, final Object[] arguments) {
        AccessControlContext acc = this.acc;
        if ((acc == null) && (System.getSecurityManager() != null)) {
            throw new SecurityException("AccessControlContext is not set");
        }
        return AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                return invokeInternal(proxy, method, arguments);
            }
        }, acc);
    }

    private Object invokeInternal(Object proxy, Method method, Object[] arguments) {
        String methodName = method.getName();
        if (method.getDeclaringClass() == Object.class)  {
            // Handle the Object public methods.
            if (methodName.equals("hashCode"))  {
                return new Integer(System.identityHashCode(proxy));
            } else if (methodName.equals("equals")) {
                return (proxy == arguments[0] ? Boolean.TRUE : Boolean.FALSE);
            } else if (methodName.equals("toString")) {
                return proxy.getClass().getName() + '@' + Integer.toHexString(proxy.hashCode());
            }
        }

        if (listenerMethodName == null || listenerMethodName.equals(methodName)) {
            Class[] argTypes = null;
            Object[] newArgs = null;

            if (eventPropertyName == null) {     // Nullary method.
                newArgs = new Object[]{};
                argTypes = new Class<?>[]{};
            }
            else {
                Object input = applyGetters(arguments[0], getEventPropertyName());
                newArgs = new Object[]{input};
                argTypes = new Class<?>[]{input == null ? null :
                                       input.getClass()};
            }
            try {
                int lastDot = action.lastIndexOf('.');
                if (lastDot != -1) {
                    target = applyGetters(target, action.substring(0, lastDot));
                    action = action.substring(lastDot + 1);
                }
                Method targetMethod = Statement.getMethod(
                             target.getClass(), action, argTypes);
                if (targetMethod == null) {
                    targetMethod = Statement.getMethod(target.getClass(),
                             "set" + NameGenerator.capitalize(action), argTypes);
                }
                if (targetMethod == null) {
                    String argTypeString = (argTypes.length == 0)
                        ? " with no arguments"
                        : " with argument " + argTypes[0];
                    throw new RuntimeException(
                        "No method called " + action + " on " +
                        target.getClass() + argTypeString);
                }
                return MethodUtil.invoke(targetMethod, target, newArgs);
            }
            catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
            catch (InvocationTargetException ex) {
                Throwable th = ex.getTargetException();
                throw (th instanceof RuntimeException)
                        ? (RuntimeException) th
                        : new RuntimeException(th);
            }
        }
        return null;
    }

    /**
     * Creates an implementation of <code>listenerInterface</code> in which
     * <em>all</em> of the methods in the listener interface apply
     * the handler's <code>action</code> to the <code>target</code>. This
     * method is implemented by calling the other, more general,
     * implementation of the <code>create</code> method with both
     * the <code>eventPropertyName</code> and the <code>listenerMethodName</code>
     * taking the value <code>null</code>. Refer to
     * {@link java.beans.EventHandler#create(Class, Object, String, String)
     * the general version of create} for a complete description of
     * the <code>action</code> parameter.
     * <p>
     * To create an <code>ActionListener</code> that shows a
     * <code>JDialog</code> with <code>dialog.show()</code>,
     * one can write:
     *
     *<blockquote>
     *<pre>
     *EventHandler.create(ActionListener.class, dialog, "show")
     *</pre>
     *</blockquote>
     *
     * <p>
     *  从事件中提取适当的属性值,并将其传递给与此<code> EventHandler </code>关联的操作。
     * 
     * 
     * @param <T> the type to create
     * @param listenerInterface the listener interface to create a proxy for
     * @param target the object that will perform the action
     * @param action the name of a (possibly qualified) property or method on
     *        the target
     * @return an object that implements <code>listenerInterface</code>
     *
     * @throws NullPointerException if <code>listenerInterface</code> is null
     * @throws NullPointerException if <code>target</code> is null
     * @throws NullPointerException if <code>action</code> is null
     *
     * @see #create(Class, Object, String, String)
     */
    public static <T> T create(Class<T> listenerInterface,
                               Object target, String action)
    {
        return create(listenerInterface, target, action, null, null);
    }

    /**
    /**
     * Creates an implementation of <code>listenerInterface</code> in which
     * <em>all</em> of the methods pass the value of the event
     * expression, <code>eventPropertyName</code>, to the final method in the
     * statement, <code>action</code>, which is applied to the <code>target</code>.
     * This method is implemented by calling the
     * more general, implementation of the <code>create</code> method with
     * the <code>listenerMethodName</code> taking the value <code>null</code>.
     * Refer to
     * {@link java.beans.EventHandler#create(Class, Object, String, String)
     * the general version of create} for a complete description of
     * the <code>action</code> and <code>eventPropertyName</code> parameters.
     * <p>
     * To create an <code>ActionListener</code> that sets the
     * the text of a <code>JLabel</code> to the text value of
     * the <code>JTextField</code> source of the incoming event,
     * you can use the following code:
     *
     *<blockquote>
     *<pre>
     *EventHandler.create(ActionListener.class, label, "text", "source.text");
     *</pre>
     *</blockquote>
     *
     * This is equivalent to the following code:
     *<blockquote>
     *<pre>
//Equivalent code using an inner class instead of EventHandler.
     *new ActionListener() {
     *    public void actionPerformed(ActionEvent event) {
     *        label.setText(((JTextField)(event.getSource())).getText());
     *     }
     *};
     *</pre>
     *</blockquote>
     *
     * <p>
     * 创建<code> listenerInterface </code>的实现,其中侦听器接口中的所有</em>方法将处理程序的<code> action </code>应用于<code> target </code>
     *  。
     * 该方法通过调用<code> create </code>方法的另一个更一般的实现来实现,其中<code> eventPropertyName </code>和<code> listenerMethodN
     * ame </code> > null </code>。
     * 有关<code> action </code>参数的完整说明,请参阅{@link java.beans.EventHandler#create(Class,Object,String,String)通用版本的create}
     * 。
     * <p>
     *  要创建一个与<code> dialog.show()</code>一起显示<code> JDialog </code>的<code> ActionListener </code>,可以这样写：
     * 
     * blockquote>
     * pre>
     *  ventHandler.create(ActionListener.class,dialog,"show")
     * /pre>
     * /blockquote>
     * 
     * 
     * @param <T> the type to create
     * @param listenerInterface the listener interface to create a proxy for
     * @param target the object that will perform the action
     * @param action the name of a (possibly qualified) property or method on
     *        the target
     * @param eventPropertyName the (possibly qualified) name of a readable property of the incoming event
     *
     * @return an object that implements <code>listenerInterface</code>
     *
     * @throws NullPointerException if <code>listenerInterface</code> is null
     * @throws NullPointerException if <code>target</code> is null
     * @throws NullPointerException if <code>action</code> is null
     *
     * @see #create(Class, Object, String, String, String)
     */
    public static <T> T create(Class<T> listenerInterface,
                               Object target, String action,
                               String eventPropertyName)
    {
        return create(listenerInterface, target, action, eventPropertyName, null);
    }

    /**
     * Creates an implementation of <code>listenerInterface</code> in which
     * the method named <code>listenerMethodName</code>
     * passes the value of the event expression, <code>eventPropertyName</code>,
     * to the final method in the statement, <code>action</code>, which
     * is applied to the <code>target</code>. All of the other listener
     * methods do nothing.
     * <p>
     * The <code>eventPropertyName</code> string is used to extract a value
     * from the incoming event object that is passed to the target
     * method.  The common case is the target method takes no arguments, in
     * which case a value of null should be used for the
     * <code>eventPropertyName</code>.  Alternatively if you want
     * the incoming event object passed directly to the target method use
     * the empty string.
     * The format of the <code>eventPropertyName</code> string is a sequence of
     * methods or properties where each method or
     * property is applied to the value returned by the preceding method
     * starting from the incoming event object.
     * The syntax is: <code>propertyName{.propertyName}*</code>
     * where <code>propertyName</code> matches a method or
     * property.  For example, to extract the <code>point</code>
     * property from a <code>MouseEvent</code>, you could use either
     * <code>"point"</code> or <code>"getPoint"</code> as the
     * <code>eventPropertyName</code>.  To extract the "text" property from
     * a <code>MouseEvent</code> with a <code>JLabel</code> source use any
     * of the following as <code>eventPropertyName</code>:
     * <code>"source.text"</code>,
     * <code>"getSource.text"</code> <code>"getSource.getText"</code> or
     * <code>"source.getText"</code>.  If a method can not be found, or an
     * exception is generated as part of invoking a method a
     * <code>RuntimeException</code> will be thrown at dispatch time.  For
     * example, if the incoming event object is null, and
     * <code>eventPropertyName</code> is non-null and not empty, a
     * <code>RuntimeException</code> will be thrown.
     * <p>
     * The <code>action</code> argument is of the same format as the
     * <code>eventPropertyName</code> argument where the last property name
     * identifies either a method name or writable property.
     * <p>
     * If the <code>listenerMethodName</code> is <code>null</code>
     * <em>all</em> methods in the interface trigger the <code>action</code> to be
     * executed on the <code>target</code>.
     * <p>
     * For example, to create a <code>MouseListener</code> that sets the target
     * object's <code>origin</code> property to the incoming <code>MouseEvent</code>'s
     * location (that's the value of <code>mouseEvent.getPoint()</code>) each
     * time a mouse button is pressed, one would write:
     *<blockquote>
     *<pre>
     *EventHandler.create(MouseListener.class, target, "origin", "point", "mousePressed");
     *</pre>
     *</blockquote>
     *
     * This is comparable to writing a <code>MouseListener</code> in which all
     * of the methods except <code>mousePressed</code> are no-ops:
     *
     *<blockquote>
     *<pre>
//Equivalent code using an inner class instead of EventHandler.
     *new MouseAdapter() {
     *    public void mousePressed(MouseEvent e) {
     *        target.setOrigin(e.getPoint());
     *    }
     *};
     * </pre>
     *</blockquote>
     *
     * <p>
     *  / **创建<code> listenerInterface </code>的实现,其中<em>所有</em>方法将事件表达式<code> eventPropertyName </code>的值传递给
     * 最终方法应用于<code>目标</code>的语句<code> action </code>。
     * 该方法通过使用<code> listenerMethodName </code>取值<code> null </code>的<code> create </code>方法的更一般的实现方式来实现。
     * 有关<code> action </code>和<code> eventPropertyName </code>的完整说明,请参阅{@link java.beans.EventHandler#create(Class,Object,String,String)参数。
     * 该方法通过使用<code> listenerMethodName </code>取值<code> null </code>的<code> create </code>方法的更一般的实现方式来实现。
     * <p>
     * 要创建将<code> JLabel </code>的文本设置为传入事件的<code> JTextField </code>源的文本值的<code> ActionListener </code>,您可以使
     * 用下面的代码：。
     * 
     * blockquote>
     * pre>
     *  ventHandler.create(ActionListener.class,label,"text","source.text");
     * /pre>
     * /blockquote>
     * 
     *  这相当于下面的代码：
     * blockquote>
     * pre>
     *  //使用内部类而不是EventHandler的等效代码。
     *  ew ActionListener(){public void actionPerformed(ActionEvent event){label.setText((JTextField)(event.getSource()))。
     *  //使用内部类而不是EventHandler的等效代码。getText()); };。
     * /pre>
     * /blockquote>
     * 
     * 
     * @param <T> the type to create
     * @param listenerInterface the listener interface to create a proxy for
     * @param target the object that will perform the action
     * @param action the name of a (possibly qualified) property or method on
     *        the target
     * @param eventPropertyName the (possibly qualified) name of a readable property of the incoming event
     * @param listenerMethodName the name of the method in the listener interface that should trigger the action
     *
     * @return an object that implements <code>listenerInterface</code>
     *
     * @throws NullPointerException if <code>listenerInterface</code> is null
     * @throws NullPointerException if <code>target</code> is null
     * @throws NullPointerException if <code>action</code> is null
     *
     * @see EventHandler
     */
    public static <T> T create(Class<T> listenerInterface,
                               Object target, String action,
                               String eventPropertyName,
                               String listenerMethodName)
    {
        // Create this first to verify target/action are non-null
        final EventHandler handler = new EventHandler(target, action,
                                                     eventPropertyName,
                                                     listenerMethodName);
        if (listenerInterface == null) {
            throw new NullPointerException(
                          "listenerInterface must be non-null");
        }
        final ClassLoader loader = getClassLoader(listenerInterface);
        final Class<?>[] interfaces = {listenerInterface};
        return AccessController.doPrivileged(new PrivilegedAction<T>() {
            @SuppressWarnings("unchecked")
            public T run() {
                return (T) Proxy.newProxyInstance(loader, interfaces, handler);
            }
        });
    }

    private static ClassLoader getClassLoader(Class<?> type) {
        ReflectUtil.checkPackageAccess(type);
        ClassLoader loader = type.getClassLoader();
        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader(); // avoid use of BCP
            if (loader == null) {
                loader = ClassLoader.getSystemClassLoader();
            }
        }
        return loader;
    }
}
