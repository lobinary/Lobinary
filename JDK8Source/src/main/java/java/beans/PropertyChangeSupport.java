/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;
import java.io.ObjectStreamField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map.Entry;

/**
 * This is a utility class that can be used by beans that support bound
 * properties.  It manages a list of listeners and dispatches
 * {@link PropertyChangeEvent}s to them.  You can use an instance of this class
 * as a member field of your bean and delegate these types of work to it.
 * The {@link PropertyChangeListener} can be registered for all properties
 * or for a property specified by name.
 * <p>
 * Here is an example of {@code PropertyChangeSupport} usage that follows
 * the rules and recommendations laid out in the JavaBeans&trade; specification:
 * <pre>
 * public class MyBean {
 *     private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
 *
 *     public void addPropertyChangeListener(PropertyChangeListener listener) {
 *         this.pcs.addPropertyChangeListener(listener);
 *     }
 *
 *     public void removePropertyChangeListener(PropertyChangeListener listener) {
 *         this.pcs.removePropertyChangeListener(listener);
 *     }
 *
 *     private String value;
 *
 *     public String getValue() {
 *         return this.value;
 *     }
 *
 *     public void setValue(String newValue) {
 *         String oldValue = this.value;
 *         this.value = newValue;
 *         this.pcs.firePropertyChange("value", oldValue, newValue);
 *     }
 *
 *     [...]
 * }
 * </pre>
 * <p>
 * A {@code PropertyChangeSupport} instance is thread-safe.
 * <p>
 * This class is serializable.  When it is serialized it will save
 * (and restore) any listeners that are themselves serializable.  Any
 * non-serializable listeners will be skipped during serialization.
 *
 * <p>
 *  这是一个可以由支持绑定属性的bean使用的实用程序类。它管理一个监听器列表,并向它们发送{@link PropertyChangeEvent}。
 * 您可以使用此类的实例作为您的bean的成员字段,并将这些类型的工作委托给它。可以为所有属性或由名称指定的属性注册{@link PropertyChangeListener}。
 * <p>
 *  下面是一个遵循JavaBeans&trade中规定的规则和建议的{@code PropertyChangeSupport}使用示例;规范：
 * <pre>
 *  public class MyBean {private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
 * 
 *  public void addPropertyChangeListener(PropertyChangeListener listener){this.pcs.addPropertyChangeListener(listener); }
 * }。
 * 
 *  public void removePropertyChangeListener(PropertyChangeListener listener){this.pcs.removePropertyChangeListener(listener); }
 * }。
 * 
 *  private String value;
 * 
 *  public String getValue(){return this.value; }}
 * 
 *  public void setValue(String newValue){String oldValue = this.value; this.value = newValue; this.pcs.firePropertyChange("value",oldValue,newValue); }
 * }。
 * 
 *  [...]
 * </pre>
 * <p>
 *  {@code PropertyChangeSupport}实例是线程安全的。
 * <p>
 * 这个类是可序列化的。当它被序列化时,它将保存(和恢复)任何本身可序列化的监听器。在序列化期间将跳过任何不可序列化的侦听器。
 * 
 * 
 * @see VetoableChangeSupport
 */
public class PropertyChangeSupport implements Serializable {
    private PropertyChangeListenerMap map = new PropertyChangeListenerMap();

    /**
     * Constructs a <code>PropertyChangeSupport</code> object.
     *
     * <p>
     *  构造一个<code> PropertyChangeSupport </code>对象。
     * 
     * 
     * @param sourceBean  The bean to be given as the source for any events.
     */
    public PropertyChangeSupport(Object sourceBean) {
        if (sourceBean == null) {
            throw new NullPointerException();
        }
        source = sourceBean;
    }

    /**
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * The same listener object may be added more than once, and will be called
     * as many times as it is added.
     * If <code>listener</code> is null, no exception is thrown and no action
     * is taken.
     *
     * <p>
     *  将PropertyChangeListener添加到侦听器列表。侦听器为所有属性注册。相同的侦听器对象可以多次添加,并且将被调用的次数与添加的次数相同。
     * 如果<code> listener </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param listener  The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (listener instanceof PropertyChangeListenerProxy) {
            PropertyChangeListenerProxy proxy =
                   (PropertyChangeListenerProxy)listener;
            // Call two argument add method.
            addPropertyChangeListener(proxy.getPropertyName(),
                                      proxy.getListener());
        } else {
            this.map.add(null, listener);
        }
    }

    /**
     * Remove a PropertyChangeListener from the listener list.
     * This removes a PropertyChangeListener that was registered
     * for all properties.
     * If <code>listener</code> was added more than once to the same event
     * source, it will be notified one less time after being removed.
     * If <code>listener</code> is null, or was never added, no exception is
     * thrown and no action is taken.
     *
     * <p>
     *  从侦听器列表中删除PropertyChangeListener。这将删除为所有属性注册的PropertyChangeListener。
     * 如果<code> listener </code>被多次添加到同一个事件源,它会在删除后少一点时间通知。
     * 如果<code> listener </code>为空,或从未添加,则不抛出任何异常,并且不执行任何操作。
     * 
     * 
     * @param listener  The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (listener instanceof PropertyChangeListenerProxy) {
            PropertyChangeListenerProxy proxy =
                    (PropertyChangeListenerProxy)listener;
            // Call two argument remove method.
            removePropertyChangeListener(proxy.getPropertyName(),
                                         proxy.getListener());
        } else {
            this.map.remove(null, listener);
        }
    }

    /**
     * Returns an array of all the listeners that were added to the
     * PropertyChangeSupport object with addPropertyChangeListener().
     * <p>
     * If some listeners have been added with a named property, then
     * the returned array will be a mixture of PropertyChangeListeners
     * and <code>PropertyChangeListenerProxy</code>s. If the calling
     * method is interested in distinguishing the listeners then it must
     * test each element to see if it's a
     * <code>PropertyChangeListenerProxy</code>, perform the cast, and examine
     * the parameter.
     *
     * <pre>{@code
     * PropertyChangeListener[] listeners = bean.getPropertyChangeListeners();
     * for (int i = 0; i < listeners.length; i++) {
     *   if (listeners[i] instanceof PropertyChangeListenerProxy) {
     *     PropertyChangeListenerProxy proxy =
     *                    (PropertyChangeListenerProxy)listeners[i];
     *     if (proxy.getPropertyName().equals("foo")) {
     *       // proxy is a PropertyChangeListener which was associated
     *       // with the property named "foo"
     *     }
     *   }
     * }
     * }</pre>
     *
     * <p>
     *  返回使用addPropertyChangeListener()添加到PropertyChangeSupport对象的所有侦听器的数组。
     * <p>
     *  如果一些侦听器已经添加了命名属性,则返回的数组将是PropertyChangeListeners和<code> PropertyChangeListenerProxy </code>的混合。
     * 如果调用方法有兴趣区分侦听器,那么它必须测试每个元素,看看它是否是一个<code> PropertyChangeListenerProxy </code>,执行转换,并检查参数。
     * 
     * <pre> {@ code PropertyChangeListener [] listeners = bean.getPropertyChangeListeners(); for(int i = 0; i <listeners.length; i ++){if(listeners [i] instanceof PropertyChangeListenerProxy){PropertyChangeListenerProxy proxy =(PropertyChangeListenerProxy)listeners [i]; if(proxy.getPropertyName()。
     * equals("foo")){//代理是一个PropertyChangeListener,它与名为"foo"的属性相关联}}}} </pre>。
     * 
     * 
     * @see PropertyChangeListenerProxy
     * @return all of the <code>PropertyChangeListeners</code> added or an
     *         empty array if no listeners have been added
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return this.map.getListeners();
    }

    /**
     * Add a PropertyChangeListener for a specific property.  The listener
     * will be invoked only when a call on firePropertyChange names that
     * specific property.
     * The same listener object may be added more than once.  For each
     * property,  the listener will be invoked the number of times it was added
     * for that property.
     * If <code>propertyName</code> or <code>listener</code> is null, no
     * exception is thrown and no action is taken.
     *
     * <p>
     *  为特定属性添加PropertyChangeListener。只有当对firePropertyChange的调用命名该特定属性时,才会调用侦听器。可以多次添加相同的侦听器对象。
     * 对于每个属性,侦听器将调用它为该属性添加的次数。
     * 如果<code> propertyName </code>或<code> listener </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param propertyName  The name of the property to listen on.
     * @param listener  The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(
                String propertyName,
                PropertyChangeListener listener) {
        if (listener == null || propertyName == null) {
            return;
        }
        listener = this.map.extract(listener);
        if (listener != null) {
            this.map.add(propertyName, listener);
        }
    }

    /**
     * Remove a PropertyChangeListener for a specific property.
     * If <code>listener</code> was added more than once to the same event
     * source for the specified property, it will be notified one less time
     * after being removed.
     * If <code>propertyName</code> is null,  no exception is thrown and no
     * action is taken.
     * If <code>listener</code> is null, or was never added for the specified
     * property, no exception is thrown and no action is taken.
     *
     * <p>
     *  删除特定属性的PropertyChangeListener。如果<code> listener </code>被多次添加到指定属性的同一个事件源中,则在删除后将少一点时间通知它。
     * 如果<code> propertyName </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 如果<code> listener </code>为null,或者从未为指定的属性添加,则不会抛出任何异常,因此不会执行任何操作。
     * 
     * 
     * @param propertyName  The name of the property that was listened on.
     * @param listener  The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(
                String propertyName,
                PropertyChangeListener listener) {
        if (listener == null || propertyName == null) {
            return;
        }
        listener = this.map.extract(listener);
        if (listener != null) {
            this.map.remove(propertyName, listener);
        }
    }

    /**
     * Returns an array of all the listeners which have been associated
     * with the named property.
     *
     * <p>
     *  返回与命名属性关联的所有侦听器的数组。
     * 
     * 
     * @param propertyName  The name of the property being listened to
     * @return all of the <code>PropertyChangeListeners</code> associated with
     *         the named property.  If no such listeners have been added,
     *         or if <code>propertyName</code> is null, an empty array is
     *         returned.
     * @since 1.4
     */
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return this.map.getListeners(propertyName);
    }

    /**
     * Reports a bound property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * No event is fired if old and new values are equal and non-null.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #firePropertyChange(PropertyChangeEvent)} method.
     *
     * <p>
     * 将绑定的属性更新报告给已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器。
     * <p>
     *  如果旧值和新值相等且非空,则不触发事件。
     * <p>
     *  这只是一个更通用的{@link #firePropertyChange(PropertyChangeEvent)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
            firePropertyChange(new PropertyChangeEvent(this.source, propertyName, oldValue, newValue));
        }
    }

    /**
     * Reports an integer bound property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * No event is fired if old and new values are equal.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #firePropertyChange(String, Object, Object)}  method.
     *
     * <p>
     *  向已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器报告整数绑定属性更新。
     * <p>
     *  如果旧值和新值相等,则不会触发事件。
     * <p>
     *  这只是一个更通用的{@link #firePropertyChange(String,Object,Object)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     */
    public void firePropertyChange(String propertyName, int oldValue, int newValue) {
        if (oldValue != newValue) {
            firePropertyChange(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
        }
    }

    /**
     * Reports a boolean bound property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * No event is fired if old and new values are equal.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #firePropertyChange(String, Object, Object)}  method.
     *
     * <p>
     *  向已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器报告布尔绑定属性更新。
     * <p>
     *  如果旧值和新值相等,则不会触发事件。
     * <p>
     *  这只是一个更通用的{@link #firePropertyChange(String,Object,Object)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     */
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
        if (oldValue != newValue) {
            firePropertyChange(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }

    /**
     * Fires a property change event to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * No event is fired if the given event's old and new values are equal and non-null.
     *
     * <p>
     *  对已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器触发属性更改事件。
     * <p>
     *  如果给定事件的旧值和新值相等且非空,则不会触发任何事件。
     * 
     * 
     * @param event  the {@code PropertyChangeEvent} to be fired
     */
    public void firePropertyChange(PropertyChangeEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
            String name = event.getPropertyName();

            PropertyChangeListener[] common = this.map.get(null);
            PropertyChangeListener[] named = (name != null)
                        ? this.map.get(name)
                        : null;

            fire(common, event);
            fire(named, event);
        }
    }

    private static void fire(PropertyChangeListener[] listeners, PropertyChangeEvent event) {
        if (listeners != null) {
            for (PropertyChangeListener listener : listeners) {
                listener.propertyChange(event);
            }
        }
    }

    /**
     * Reports a bound indexed property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * No event is fired if old and new values are equal and non-null.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #firePropertyChange(PropertyChangeEvent)} method.
     *
     * <p>
     *  将已绑定的索引属性更新报告给已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器。
     * <p>
     *  如果旧值和新值相等且非空,则不触发事件。
     * <p>
     * 这只是一个更通用的{@link #firePropertyChange(PropertyChangeEvent)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that was changed
     * @param index         the index of the property element that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     * @since 1.5
     */
    public void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
        if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
            firePropertyChange(new IndexedPropertyChangeEvent(source, propertyName, oldValue, newValue, index));
        }
    }

    /**
     * Reports an integer bound indexed property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * No event is fired if old and new values are equal.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #fireIndexedPropertyChange(String, int, Object, Object)} method.
     *
     * <p>
     *  向已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器报告整数绑定索引属性更新。
     * <p>
     *  如果旧值和新值相等,则不会触发事件。
     * <p>
     *  这只是一个更通用的{@link #fireIndexedPropertyChange(String,int,Object,Object)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that was changed
     * @param index         the index of the property element that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     * @since 1.5
     */
    public void fireIndexedPropertyChange(String propertyName, int index, int oldValue, int newValue) {
        if (oldValue != newValue) {
            fireIndexedPropertyChange(propertyName, index, Integer.valueOf(oldValue), Integer.valueOf(newValue));
        }
    }

    /**
     * Reports a boolean bound indexed property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * No event is fired if old and new values are equal.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #fireIndexedPropertyChange(String, int, Object, Object)} method.
     *
     * <p>
     *  向已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器报告布尔绑定索引属性更新。
     * <p>
     *  如果旧值和新值相等,则不会触发事件。
     * <p>
     *  这只是一个更通用的{@link #fireIndexedPropertyChange(String,int,Object,Object)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that was changed
     * @param index         the index of the property element that was changed
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     * @since 1.5
     */
    public void fireIndexedPropertyChange(String propertyName, int index, boolean oldValue, boolean newValue) {
        if (oldValue != newValue) {
            fireIndexedPropertyChange(propertyName, index, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }

    /**
     * Check if there are any listeners for a specific property, including
     * those registered on all properties.  If <code>propertyName</code>
     * is null, only check for listeners registered on all properties.
     *
     * <p>
     *  检查是否有特定属性的任何侦听器,包括在所有属性上注册的侦听器。如果<code> propertyName </code>为null,则只检查在所有属性上注册的侦听器。
     * 
     * 
     * @param propertyName  the property name.
     * @return true if there are one or more listeners for the given property
     */
    public boolean hasListeners(String propertyName) {
        return this.map.hasListeners(propertyName);
    }

    /**
    /* <p>
    /* 
     * @serialData Null terminated list of <code>PropertyChangeListeners</code>.
     * <p>
     * At serialization time we skip non-serializable listeners and
     * only serialize the serializable listeners.
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        Hashtable<String, PropertyChangeSupport> children = null;
        PropertyChangeListener[] listeners = null;
        synchronized (this.map) {
            for (Entry<String, PropertyChangeListener[]> entry : this.map.getEntries()) {
                String property = entry.getKey();
                if (property == null) {
                    listeners = entry.getValue();
                } else {
                    if (children == null) {
                        children = new Hashtable<>();
                    }
                    PropertyChangeSupport pcs = new PropertyChangeSupport(this.source);
                    pcs.map.set(null, entry.getValue());
                    children.put(property, pcs);
                }
            }
        }
        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("children", children);
        fields.put("source", this.source);
        fields.put("propertyChangeSupportSerializedDataVersion", 2);
        s.writeFields();

        if (listeners != null) {
            for (PropertyChangeListener l : listeners) {
                if (l instanceof Serializable) {
                    s.writeObject(l);
                }
            }
        }
        s.writeObject(null);
    }

    private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
        this.map = new PropertyChangeListenerMap();

        ObjectInputStream.GetField fields = s.readFields();

        @SuppressWarnings("unchecked")
        Hashtable<String, PropertyChangeSupport> children = (Hashtable<String, PropertyChangeSupport>) fields.get("children", null);
        this.source = fields.get("source", null);
        fields.get("propertyChangeSupportSerializedDataVersion", 2);

        Object listenerOrNull;
        while (null != (listenerOrNull = s.readObject())) {
            this.map.add(null, (PropertyChangeListener)listenerOrNull);
        }
        if (children != null) {
            for (Entry<String, PropertyChangeSupport> entry : children.entrySet()) {
                for (PropertyChangeListener listener : entry.getValue().getPropertyChangeListeners()) {
                    this.map.add(entry.getKey(), listener);
                }
            }
        }
    }

    /**
     * The object to be provided as the "source" for any generated events.
     * <p>
     *  要作为任何生成的事件的"源"提供的对象。
     * 
     */
    private Object source;

    /**
    /* <p>
    /* 
     * @serialField children                                   Hashtable
     * @serialField source                                     Object
     * @serialField propertyChangeSupportSerializedDataVersion int
     */
    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("children", Hashtable.class),
            new ObjectStreamField("source", Object.class),
            new ObjectStreamField("propertyChangeSupportSerializedDataVersion", Integer.TYPE)
    };

    /**
     * Serialization version ID, so we're compatible with JDK 1.1
     * <p>
     *  序列化版本ID,因此我们与JDK 1.1兼容
     * 
     */
    static final long serialVersionUID = 6401253773779951803L;

    /**
     * This is a {@link ChangeListenerMap ChangeListenerMap} implementation
     * that works with {@link PropertyChangeListener PropertyChangeListener} objects.
     * <p>
     *  这是一个与{@link PropertyChangeListener PropertyChangeListener}对象配合使用的{@link ChangeListenerMap ChangeListenerMap}
     * 实现。
     * 
     */
    private static final class PropertyChangeListenerMap extends ChangeListenerMap<PropertyChangeListener> {
        private static final PropertyChangeListener[] EMPTY = {};

        /**
         * Creates an array of {@link PropertyChangeListener PropertyChangeListener} objects.
         * This method uses the same instance of the empty array
         * when {@code length} equals {@code 0}.
         *
         * <p>
         *  创建一个{@link PropertyChangeListener PropertyChangeListener}对象的数组。
         * 当{@code length}等于{@code 0}时,此方法使用空数组的相同实例。
         * 
         * 
         * @param length  the array length
         * @return        an array with specified length
         */
        @Override
        protected PropertyChangeListener[] newArray(int length) {
            return (0 < length)
                    ? new PropertyChangeListener[length]
                    : EMPTY;
        }

        /**
         * Creates a {@link PropertyChangeListenerProxy PropertyChangeListenerProxy}
         * object for the specified property.
         *
         * <p>
         * 为指定的属性创建{@link PropertyChangeListenerProxy PropertyChangeListenerProxy}对象。
         * 
         * 
         * @param name      the name of the property to listen on
         * @param listener  the listener to process events
         * @return          a {@code PropertyChangeListenerProxy} object
         */
        @Override
        protected PropertyChangeListener newProxy(String name, PropertyChangeListener listener) {
            return new PropertyChangeListenerProxy(name, listener);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         */
        public final PropertyChangeListener extract(PropertyChangeListener listener) {
            while (listener instanceof PropertyChangeListenerProxy) {
                listener = ((PropertyChangeListenerProxy) listener).getListener();
            }
            return listener;
        }
    }
}
