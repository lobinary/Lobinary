/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.event;

import java.io.*;
import java.util.*;
import java.lang.reflect.Array;
import sun.reflect.misc.ReflectUtil;

/**
 * A class that holds a list of EventListeners.  A single instance
 * can be used to hold all listeners (of all types) for the instance
 * using the list.  It is the responsiblity of the class using the
 * EventListenerList to provide type-safe API (preferably conforming
 * to the JavaBeans spec) and methods which dispatch event notification
 * methods to appropriate Event Listeners on the list.
 *
 * The main benefits that this class provides are that it is relatively
 * cheap in the case of no listeners, and it provides serialization for
 * event-listener lists in a single place, as well as a degree of MT safety
 * (when used correctly).
 *
 * Usage example:
 *    Say one is defining a class that sends out FooEvents, and one wants
 * to allow users of the class to register FooListeners and receive
 * notification when FooEvents occur.  The following should be added
 * to the class definition:
 * <pre>
 * EventListenerList listenerList = new EventListenerList();
 * FooEvent fooEvent = null;
 *
 * public void addFooListener(FooListener l) {
 *     listenerList.add(FooListener.class, l);
 * }
 *
 * public void removeFooListener(FooListener l) {
 *     listenerList.remove(FooListener.class, l);
 * }
 *
 *
 * // Notify all listeners that have registered interest for
 * // notification on this event type.  The event instance
 * // is lazily created using the parameters passed into
 * // the fire method.
 *
 * protected void fireFooXXX() {
 *     // Guaranteed to return a non-null array
 *     Object[] listeners = listenerList.getListenerList();
 *     // Process the listeners last to first, notifying
 *     // those that are interested in this event
 *     for (int i = listeners.length-2; i&gt;=0; i-=2) {
 *         if (listeners[i]==FooListener.class) {
 *             // Lazily create the event:
 *             if (fooEvent == null)
 *                 fooEvent = new FooEvent(this);
 *             ((FooListener)listeners[i+1]).fooXXX(fooEvent);
 *         }
 *     }
 * }
 * </pre>
 * foo should be changed to the appropriate name, and fireFooXxx to the
 * appropriate method name.  One fire method should exist for each
 * notification method in the FooListener interface.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  一个包含EventListener列表的类。单个实例可用于保存使用列表的实例的所有侦听器(所有类型的)。
 * 使用EventListenerList来提供类型安全的API(最好符合JavaBeans规范)和将事件通知方法分派给列表上适当的事件监听器的方法是类的责任。
 * 
 *  这个类提供的主要好处是它在没有监听器的情况下相对便宜,并且在单个位置提供事件监听器列表的序列化,以及一定程度的MT安全性(当正确使用时)。
 * 
 *  使用示例：说一个是定义一个发送FooEvents的类,并且想要允许该类的用户注册FooListeners并在FooEvents发生时接收通知。以下应该添加到类定义：
 * <pre>
 *  EventListenerList listenerList = new EventListenerList(); FooEvent fooEvent = null;
 * 
 *  public void addFooListener(FooListener l){listenerList.add(FooListener.class,l); }}
 * 
 *  public void removeFooListener(FooListener l){listenerList.remove(FooListener.class,l); }}
 * 
 *  //通知所有对此事件类型的//通知感兴趣的侦听器。事件实例//使用传递到fire方法的参数进行延迟创建。
 * 
 * protected void fireFooXXX(){//保证返回一个非空数组Object [] listeners = listenerList.getListenerList(); //处理侦听器最后到第一个,通知//那些对此事件感兴趣的事件for(int i = listeners.length-2; i&gt; = 0; i- = 2){if(listeners [i] == FooListener .class){// Lazily创建事件：if(fooEvent == null)fooEvent = new FooEvent(this); ((FooListener)listeners [i + 1])fooXXX(fooEvent); }
 * }}。
 * </pre>
 *  foo应该更改为适当的名称,并将fireFooXxx更改为适当的方法名称。对于FooListener接口中的每个通知方法,应存在一个fire方法。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Georges Saab
 * @author Hans Muller
 * @author James Gosling
 */
@SuppressWarnings("serial")
public class EventListenerList implements Serializable {
    /* A null array to be shared by all empty listener lists*/
    private final static Object[] NULL_ARRAY = new Object[0];
    /* The list of ListenerType - Listener pairs */
    protected transient Object[] listenerList = NULL_ARRAY;

    /**
     * Passes back the event listener list as an array
     * of ListenerType-listener pairs.  Note that for
     * performance reasons, this implementation passes back
     * the actual data structure in which the listener data
     * is stored internally!
     * This method is guaranteed to pass back a non-null
     * array, so that no null-checking is required in
     * fire methods.  A zero-length array of Object should
     * be returned if there are currently no listeners.
     *
     * WARNING!!! Absolutely NO modification of
     * the data contained in this array should be made -- if
     * any such manipulation is necessary, it should be done
     * on a copy of the array returned rather than the array
     * itself.
     * <p>
     *  将事件侦听器列表作为ListenerType-listener对的数组传回。
     * 请注意,出于性能原因,此实现传回实际数据结构,其中监听器数据在内部存储！此方法保证传回非空数组,因此在fire方法中不需要null检查。如果当前没有侦听器,则应返回对象的零长度数组。
     * 
     * 警告！！！绝对不能修改包含在这个数组中的数据 - 如果需要这样的操作,它应该在返回的数组的副本而不是数组本身上进行。
     * 
     */
    public Object[] getListenerList() {
        return listenerList;
    }

    /**
     * Return an array of all the listeners of the given type.
     * <p>
     *  返回给定类型的所有侦听器的数组。
     * 
     * 
     * @return all of the listeners of the specified type.
     * @exception  ClassCastException if the supplied class
     *          is not assignable to EventListener
     *
     * @since 1.3
     */
    public <T extends EventListener> T[] getListeners(Class<T> t) {
        Object[] lList = listenerList;
        int n = getListenerCount(lList, t);
        T[] result = (T[])Array.newInstance(t, n);
        int j = 0;
        for (int i = lList.length-2; i>=0; i-=2) {
            if (lList[i] == t) {
                result[j++] = (T)lList[i+1];
            }
        }
        return result;
    }

    /**
     * Returns the total number of listeners for this listener list.
     * <p>
     *  返回此侦听器列表的侦听器的总数。
     * 
     */
    public int getListenerCount() {
        return listenerList.length/2;
    }

    /**
     * Returns the total number of listeners of the supplied type
     * for this listener list.
     * <p>
     *  返回此侦听器列表的提供类型的侦听器的总数。
     * 
     */
    public int getListenerCount(Class<?> t) {
        Object[] lList = listenerList;
        return getListenerCount(lList, t);
    }

    private int getListenerCount(Object[] list, Class t) {
        int count = 0;
        for (int i = 0; i < list.length; i+=2) {
            if (t == (Class)list[i])
                count++;
        }
        return count;
    }

    /**
     * Adds the listener as a listener of the specified type.
     * <p>
     *  将侦听器添加为指定类型的侦听器。
     * 
     * 
     * @param t the type of the listener to be added
     * @param l the listener to be added
     */
    public synchronized <T extends EventListener> void add(Class<T> t, T l) {
        if (l==null) {
            // In an ideal world, we would do an assertion here
            // to help developers know they are probably doing
            // something wrong
            return;
        }
        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l +
                                         " is not of type " + t);
        }
        if (listenerList == NULL_ARRAY) {
            // if this is the first listener added,
            // initialize the lists
            listenerList = new Object[] { t, l };
        } else {
            // Otherwise copy the array and add the new listener
            int i = listenerList.length;
            Object[] tmp = new Object[i+2];
            System.arraycopy(listenerList, 0, tmp, 0, i);

            tmp[i] = t;
            tmp[i+1] = l;

            listenerList = tmp;
        }
    }

    /**
     * Removes the listener as a listener of the specified type.
     * <p>
     *  删除作为指定类型的侦听器的侦听器。
     * 
     * 
     * @param t the type of the listener to be removed
     * @param l the listener to be removed
     */
    public synchronized <T extends EventListener> void remove(Class<T> t, T l) {
        if (l ==null) {
            // In an ideal world, we would do an assertion here
            // to help developers know they are probably doing
            // something wrong
            return;
        }
        if (!t.isInstance(l)) {
            throw new IllegalArgumentException("Listener " + l +
                                         " is not of type " + t);
        }
        // Is l on the list?
        int index = -1;
        for (int i = listenerList.length-2; i>=0; i-=2) {
            if ((listenerList[i]==t) && (listenerList[i+1].equals(l) == true)) {
                index = i;
                break;
            }
        }

        // If so,  remove it
        if (index != -1) {
            Object[] tmp = new Object[listenerList.length-2];
            // Copy the list up to index
            System.arraycopy(listenerList, 0, tmp, 0, index);
            // Copy from two past the index, up to
            // the end of tmp (which is two elements
            // shorter than the old list)
            if (index < tmp.length)
                System.arraycopy(listenerList, index+2, tmp, index,
                                 tmp.length - index);
            // set the listener array to the new array or null
            listenerList = (tmp.length == 0) ? NULL_ARRAY : tmp;
            }
    }

    // Serialization support.
    private void writeObject(ObjectOutputStream s) throws IOException {
        Object[] lList = listenerList;
        s.defaultWriteObject();

        // Save the non-null event listeners:
        for (int i = 0; i < lList.length; i+=2) {
            Class<?> t = (Class)lList[i];
            EventListener l = (EventListener)lList[i+1];
            if ((l!=null) && (l instanceof Serializable)) {
                s.writeObject(t.getName());
                s.writeObject(l);
            }
        }

        s.writeObject(null);
    }

    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        listenerList = NULL_ARRAY;
        s.defaultReadObject();
        Object listenerTypeOrNull;

        while (null != (listenerTypeOrNull = s.readObject())) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            EventListener l = (EventListener)s.readObject();
            String name = (String) listenerTypeOrNull;
            ReflectUtil.checkPackageAccess(name);
            add((Class<EventListener>)Class.forName(name, true, cl), l);
        }
    }

    /**
     * Returns a string representation of the EventListenerList.
     * <p>
     *  返回EventListenerList的字符串表示形式。
     */
    public String toString() {
        Object[] lList = listenerList;
        String s = "EventListenerList: ";
        s += lList.length/2 + " listeners: ";
        for (int i = 0 ; i <= lList.length-2 ; i+=2) {
            s += " type " + ((Class)lList[i]).getName();
            s += " listener " + lList[i+1];
        }
        return s;
    }
}
